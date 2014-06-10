package oracle.jdbc.rowset;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.CharArrayReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.Reader;
import java.io.Serializable;
import java.io.StringBufferInputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;
import java.util.TreeMap;
import java.util.Vector;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.sql.RowSet;
import javax.sql.RowSetEvent;
import javax.sql.RowSetInternal;
import javax.sql.RowSetMetaData;
import javax.sql.RowSetReader;
import javax.sql.RowSetWriter;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetWarning;
import javax.sql.rowset.spi.SyncFactory;
import javax.sql.rowset.spi.SyncFactoryException;
import javax.sql.rowset.spi.SyncProvider;
import javax.sql.rowset.spi.SyncProviderException;
import oracle.jdbc.OracleConnection;
import oracle.jdbc.OracleSavepoint;
import oracle.jdbc.driver.DBConversion;
import oracle.jdbc.driver.DatabaseError;
import oracle.jdbc.driver.OracleDriver;
import oracle.sql.BLOB;
import oracle.sql.CLOB;
import oracle.sql.ROWID;
import oracle.sql.TIMESTAMP;
import oracle.sql.TIMESTAMPLTZ;
import oracle.sql.TIMESTAMPTZ;

public class OracleCachedRowSet extends OracleRowSet
  implements RowSet, RowSetInternal, Serializable, Cloneable, CachedRowSet
{
  static final long serialVersionUID = -2066958142885801470L;
  private SQLWarning sqlWarning;
  private RowSetWarning rowsetWarning;
  protected int presentRow;
  private int currentPage;
  private boolean isPopulateDone;
  private boolean previousColumnWasNull;
  private OracleRow insertRow;
  private int insertRowPosition;
  private boolean insertRowFlag;
  private int updateRowPosition;
  private boolean updateRowFlag;
  protected ResultSetMetaData rowsetMetaData;
  private transient ResultSet resultSet;
  private transient Connection connection;
  private transient boolean isConnectionStayingOpenForTxnControl = false;

  private transient OracleSqlForRowSet osql = null;
  protected Vector rows;
  private Vector param;
  private String[] metaData;
  protected int colCount;
  protected int rowCount;
  private RowSetReader reader;
  private RowSetWriter writer;
  private int[] keyColumns;
  private int pageSize;
  private SyncProvider syncProvider;
  private static final String DEFAULT_SYNCPROVIDER = "com.sun.rowset.providers.RIOptimisticProvider";
  private String tableName;
  private boolean executeCalled = false;

  private boolean driverManagerInitialized = false;
  private static final int MAX_CHAR_BUFFER_SIZE = 1024;
  private static final int MAX_BYTE_BUFFER_SIZE = 1024;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  public OracleCachedRowSet()
    throws SQLException
  {
    this.insertRowFlag = false;
    this.updateRowFlag = false;

    this.presentRow = 0;
    this.previousColumnWasNull = false;

    this.param = new Vector();

    this.rows = new Vector();

    this.sqlWarning = new SQLWarning();
    try
    {
      this.syncProvider = SyncFactory.getInstance("com.sun.rowset.providers.RIOptimisticProvider");
    }
    catch (SyncFactoryException localSyncFactoryException)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 304);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    setReader(new OracleCachedRowSetReader());
    setWriter(new OracleCachedRowSetWriter());

    this.currentPage = 0;
    this.pageSize = 0;
    this.isPopulateDone = false;

    this.keyColumns = null;
    this.tableName = null;
  }

  public Connection getConnection()
    throws SQLException
  {
    return getConnectionInternal();
  }

  Connection getConnectionInternal()
    throws SQLException
  {
    if ((this.connection == null) || (this.connection.isClosed()))
    {
      String str1 = getUsername();
      String str2 = getPassword();
      Object localObject2;
      if (getDataSourceName() != null)
      {
        try
        {
          InitialContext localInitialContext = null;
          try
          {
            Properties localProperties = System.getProperties();
            localInitialContext = new InitialContext(localProperties);
          }
          catch (SecurityException localSecurityException)
          {
          }
          if (localInitialContext == null)
            localInitialContext = new InitialContext();
          localObject2 = (DataSource)localInitialContext.lookup(getDataSourceName());

          if ((this.username == null) || (str2 == null))
            this.connection = ((DataSource)localObject2).getConnection();
          else {
            this.connection = ((DataSource)localObject2).getConnection(this.username, str2);
          }
        }
        catch (NamingException localNamingException)
        {
          localObject2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 300, localNamingException.getMessage());
          ((SQLException)localObject2).fillInStackTrace();
          throw ((Throwable)localObject2);
        }
      }
      else
      {
        Object localObject1;
        if (getUrl() != null)
        {
          if (!this.driverManagerInitialized)
          {
            DriverManager.registerDriver(new OracleDriver());
            this.driverManagerInitialized = true;
          }
          localObject1 = getUrl();

          if ((((String)localObject1).equals("")) || (str1.equals("")) || (str2.equals("")))
          {
            localObject2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 301);
            ((SQLException)localObject2).fillInStackTrace();
            throw ((Throwable)localObject2);
          }

          this.connection = DriverManager.getConnection((String)localObject1, str1, str2);
        }
        else
        {
          localObject1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 301);
          ((SQLException)localObject1).fillInStackTrace();
          throw ((Throwable)localObject1);
        }
      }
    }

    return this.connection;
  }

  public Statement getStatement()
    throws SQLException
  {
    if (this.resultSet == null)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 305);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    return this.resultSet.getStatement();
  }

  public RowSetReader getReader()
  {
    return this.reader;
  }

  public RowSetWriter getWriter()
  {
    return this.writer;
  }

  public void setFetchDirection(int paramInt)
    throws SQLException
  {
    SQLException localSQLException;
    if (this.rowsetType == 1005)
    {
      localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 306);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    switch (paramInt)
    {
    case 1000:
    case 1002:
      this.presentRow = 0;
      break;
    case 1001:
      if (this.rowsetType == 1003)
      {
        localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 307);
        localSQLException.fillInStackTrace();
        throw localSQLException;
      }
      this.presentRow = (this.rowCount + 1);
      break;
    default:
      localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 308);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    super.setFetchDirection(paramInt);
  }

  public void setCommand(String paramString)
    throws SQLException
  {
    super.setCommand(paramString);
    if ((paramString == null) || (paramString.equals("")))
      this.osql = null;
    else
      this.osql = new OracleSqlForRowSet(paramString);
  }

  public void setReader(RowSetReader paramRowSetReader)
  {
    this.reader = paramRowSetReader;
  }

  public void setWriter(RowSetWriter paramRowSetWriter)
  {
    this.writer = paramRowSetWriter;
  }

  private final int getColumnIndex(String paramString)
    throws SQLException
  {
    if ((paramString == null) || (paramString.equals("")))
    {
      SQLException localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 6, paramString);
      localSQLException1.fillInStackTrace();
      throw localSQLException1;
    }

    paramString = paramString.toUpperCase();
    for (int i = 0; 
      i < this.metaData.length; i++)
    {
      if (paramString.equals(this.metaData[i]))
      {
        break;
      }
    }

    if (i >= this.metaData.length)
    {
      SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 6, paramString);
      localSQLException2.fillInStackTrace();
      throw localSQLException2;
    }

    return i + 1;
  }

  private final void checkColumnIndex(int paramInt)
    throws SQLException
  {
    SQLException localSQLException;
    if (this.readOnly)
    {
      localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 309);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
    if ((paramInt < 1) || (paramInt > this.colCount))
    {
      localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3, new StringBuilder().append("").append(paramInt).toString());
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  private final boolean isUpdated(int paramInt)
    throws SQLException
  {
    if ((paramInt < 1) || (paramInt > this.colCount))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3, new StringBuilder().append("").append(paramInt).toString());
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    return getCurrentRow().isColumnChanged(paramInt);
  }

  private final void checkParamIndex(int paramInt)
    throws SQLException
  {
    if (paramInt < 1)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 310, new StringBuilder().append("").append(paramInt).toString());
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  private final void populateInit(ResultSet paramResultSet)
    throws SQLException
  {
    this.resultSet = paramResultSet;
    Statement localStatement = paramResultSet.getStatement();

    this.maxFieldSize = localStatement.getMaxFieldSize();

    this.fetchSize = localStatement.getFetchSize();
    this.queryTimeout = localStatement.getQueryTimeout();

    this.connection = localStatement.getConnection();

    this.transactionIsolation = this.connection.getTransactionIsolation();

    this.typeMap = this.connection.getTypeMap();
    DatabaseMetaData localDatabaseMetaData = this.connection.getMetaData();

    this.url = localDatabaseMetaData.getURL();
    this.username = localDatabaseMetaData.getUserName();

    this.presentRow = 0;
  }

  private synchronized InputStream getStream(int paramInt)
    throws SQLException
  {
    Object localObject1 = getObject(paramInt);

    if (localObject1 == null) {
      return null;
    }
    if ((localObject1 instanceof InputStream)) {
      return (InputStream)localObject1;
    }
    if ((localObject1 instanceof String))
    {
      return new ByteArrayInputStream(((String)localObject1).getBytes());
    }
    if ((localObject1 instanceof byte[]))
    {
      return new ByteArrayInputStream((byte[])localObject1);
    }
    if ((localObject1 instanceof OracleSerialClob))
      return ((OracleSerialClob)localObject1).getAsciiStream();
    if ((localObject1 instanceof OracleSerialBlob))
      return ((OracleSerialBlob)localObject1).getBinaryStream();
    if ((localObject1 instanceof Reader))
    {
      localObject2 = null;
      PipedOutputStream localPipedOutputStream = null;
      try
      {
        localObject2 = new BufferedReader((Reader)localObject1);
        int i = 0;
        localObject3 = new PipedInputStream();
        localPipedOutputStream = new PipedOutputStream((PipedInputStream)localObject3);
        while ((i = ((Reader)localObject2).read()) != -1)
          localPipedOutputStream.write(i);
        SQLException localSQLException1;
        return localObject3;
      }
      catch (IOException localIOException1)
      {
        Object localObject3 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 311, localIOException1.getMessage());
        ((SQLException)localObject3).fillInStackTrace();
        throw ((Throwable)localObject3);
      }
      finally {
        SQLException localSQLException2;
        try {
          if (localObject2 != null)
            ((Reader)localObject2).close();
        }
        catch (IOException localIOException4)
        {
          localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 311, localIOException4.getMessage());
          localSQLException2.fillInStackTrace();
          throw localSQLException2;
        }
        try
        {
          if (localPipedOutputStream != null)
            localPipedOutputStream.close();
        }
        catch (IOException localIOException5)
        {
          localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 311, localIOException5.getMessage());
          localSQLException2.fillInStackTrace();
          throw localSQLException2;
        }

      }

    }

    Object localObject2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 312);
    ((SQLException)localObject2).fillInStackTrace();
    throw ((Throwable)localObject2);
  }

  private final Calendar getSessionCalendar(Connection paramConnection)
  {
    String str = ((OracleConnection)paramConnection).getSessionTimeZone();
    Calendar localCalendar;
    if (str == null) {
      localCalendar = Calendar.getInstance();
    }
    else {
      TimeZone localTimeZone = TimeZone.getDefault();
      localTimeZone.setID(str);
      localCalendar = Calendar.getInstance(localTimeZone);
    }

    return localCalendar;
  }

  private boolean isStreamType(int paramInt)
  {
    return (paramInt == 2004) || (paramInt == 2005) || (paramInt == -4) || (paramInt == -1);
  }

  protected synchronized void notifyCursorMoved()
  {
    if (this.insertRowFlag)
    {
      this.insertRowFlag = false;
      this.insertRow.setRowUpdated(false);
      this.sqlWarning.setNextWarning(new SQLWarning("Cancelling insertion, due to cursor movement."));
    }
    else if (this.updateRowFlag)
    {
      try
      {
        this.updateRowFlag = false;
        int i = this.presentRow;
        this.presentRow = this.updateRowPosition;
        getCurrentRow().setRowUpdated(false);
        this.presentRow = i;
        this.sqlWarning.setNextWarning(new SQLWarning("Cancelling all updates, due to cursor movement."));
      }
      catch (SQLException localSQLException)
      {
      }

    }

    super.notifyCursorMoved();
  }

  protected void checkAndFilterObject(int paramInt, Object paramObject)
    throws SQLException
  {
  }

  OracleRow getCurrentRow()
    throws SQLException
  {
    int i = this.presentRow - 1;

    if ((this.presentRow < 1) || (this.presentRow > this.rowCount))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 313);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    return (OracleRow)this.rows.elementAt(this.presentRow - 1);
  }

  boolean isExecuteCalled()
  {
    return this.executeCalled;
  }

  int getCurrentPage()
  {
    return this.currentPage;
  }

  boolean isConnectionStayingOpen()
  {
    return this.isConnectionStayingOpenForTxnControl;
  }

  void setOriginal()
    throws SQLException
  {
    int i = 1;
    do
    {
      boolean bool = setOriginalRowInternal(i);

      if (!bool)
      {
        i++;
      }
    }
    while (i <= this.rowCount);

    notifyRowSetChanged();

    this.presentRow = 0;
  }

  boolean setOriginalRowInternal(int paramInt)
    throws SQLException
  {
    if ((paramInt < 1) || (paramInt > this.rowCount))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 313);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    boolean bool = false;

    OracleRow localOracleRow = (OracleRow)this.rows.elementAt(paramInt - 1);

    if (localOracleRow.isRowDeleted())
    {
      this.rows.remove(paramInt - 1);
      this.rowCount -= 1;
      bool = true;
    }
    else
    {
      if (localOracleRow.isRowInserted())
      {
        localOracleRow.setInsertedFlag(false);
      }

      if (localOracleRow.isRowUpdated())
      {
        localOracleRow.makeUpdatesOriginal();
      }

    }

    return bool;
  }

  public boolean next()
    throws SQLException
  {
    if (this.rowCount < 0)
    {
      return false;
    }

    if ((this.fetchDirection == 1000) || (this.fetchDirection == 1002))
    {
      if (this.presentRow + 1 <= this.rowCount)
      {
        this.presentRow += 1;
        if ((!this.showDeleted) && (getCurrentRow().isRowDeleted())) {
          return next();
        }
        notifyCursorMoved();
        return true;
      }

      this.presentRow = (this.rowCount + 1);
      return false;
    }

    if (this.fetchDirection == 1001)
    {
      if (this.presentRow - 1 > 0)
      {
        this.presentRow -= 1;
        if ((!this.showDeleted) && (getCurrentRow().isRowDeleted())) {
          return next();
        }
        notifyCursorMoved();
        return true;
      }

      this.presentRow = 0;
      return false;
    }

    return false;
  }

  public boolean previous()
    throws SQLException
  {
    if (this.rowCount < 0)
    {
      return false;
    }

    if (this.fetchDirection == 1001)
    {
      if (this.presentRow + 1 <= this.rowCount)
      {
        this.presentRow += 1;
        if ((!this.showDeleted) && (getCurrentRow().isRowDeleted())) {
          return previous();
        }
        notifyCursorMoved();
        return true;
      }

      this.presentRow = (this.rowCount + 1);
      return false;
    }

    if ((this.fetchDirection == 1000) || (this.fetchDirection == 1002))
    {
      if (this.presentRow - 1 > 0)
      {
        this.presentRow -= 1;
        if ((!this.showDeleted) && (getCurrentRow().isRowDeleted())) {
          return previous();
        }
        notifyCursorMoved();
        return true;
      }

      this.presentRow = 0;
      return false;
    }

    return false;
  }

  public boolean isBeforeFirst()
    throws SQLException
  {
    return (this.rowCount > 0) && (this.presentRow == 0);
  }

  public boolean isAfterLast()
    throws SQLException
  {
    return (this.rowCount > 0) && (this.presentRow == this.rowCount + 1);
  }

  public boolean isFirst()
    throws SQLException
  {
    return this.presentRow == 1;
  }

  public boolean isLast()
    throws SQLException
  {
    return this.presentRow == this.rowCount;
  }

  public void beforeFirst()
    throws SQLException
  {
    this.presentRow = 0;
  }

  public void afterLast()
    throws SQLException
  {
    this.presentRow = (this.rowCount + 1);
  }

  public boolean first()
    throws SQLException
  {
    return absolute(1);
  }

  public boolean last()
    throws SQLException
  {
    return absolute(-1);
  }

  public boolean absolute(int paramInt)
    throws SQLException
  {
    if (this.rowsetType == 1003)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 314);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
    if ((paramInt == 0) || (Math.abs(paramInt) > this.rowCount))
      return false;
    this.presentRow = (paramInt < 0 ? this.rowCount + paramInt + 1 : paramInt);
    notifyCursorMoved();

    return true;
  }

  public boolean relative(int paramInt)
    throws SQLException
  {
    return absolute(this.presentRow + paramInt);
  }

  public synchronized void populate(ResultSet paramResultSet)
    throws SQLException
  {
    if (this.rows == null)
    {
      this.rows = new Vector(50, 10);
    }
    else
      this.rows.clear();
    this.rowsetMetaData = new OracleRowSetMetaData(paramResultSet.getMetaData());
    this.metaData = new String[this.colCount = this.rowsetMetaData.getColumnCount()];
    for (int i = 0; i < this.colCount; i++) {
      this.metaData[i] = this.rowsetMetaData.getColumnName(i + 1);
    }

    if (!(paramResultSet instanceof OracleCachedRowSet)) {
      populateInit(paramResultSet);
    }

    i = (this.fetchDirection == 1000) || (this.fetchDirection == 1002) ? 1 : 0;

    this.rowCount = 0;
    OracleRow localOracleRow = null;
    int j;
    if ((this.maxRows == 0) && (this.pageSize == 0))
    {
      j = 2147483647;
    }
    else if ((this.maxRows == 0) || (this.pageSize == 0))
    {
      j = Math.max(this.maxRows, this.pageSize);
    }
    else
    {
      j = Math.min(this.maxRows, this.pageSize);
    }

    if ((paramResultSet.getType() != 1003) && (this.rows.size() == 0))
    {
      if (i == 0)
      {
        paramResultSet.afterLast();
      }
    }

    int k = 0;

    while (this.rowCount < j)
    {
      if (i != 0)
      {
        if (!paramResultSet.next())
        {
          k = 1;
          break;
        }

      }
      else if (!paramResultSet.previous())
      {
        k = 1;
        break;
      }

      localOracleRow = new OracleRow(this.colCount);
      for (int m = 1; m <= this.colCount; m++)
      {
        Object localObject = null;
        try
        {
          localObject = paramResultSet.getObject(m, this.typeMap);
        }
        catch (Exception localException)
        {
          localObject = paramResultSet.getObject(m);
        }
        catch (AbstractMethodError localAbstractMethodError)
        {
          localObject = paramResultSet.getObject(m);
        }

        if (((localObject instanceof Clob)) || ((localObject instanceof CLOB))) {
          localOracleRow.setColumnValue(m, new OracleSerialClob((Clob)localObject));
        }
        else if (((localObject instanceof Blob)) || ((localObject instanceof BLOB))) {
          localOracleRow.setColumnValue(m, new OracleSerialBlob((Blob)localObject));
        }
        else {
          localOracleRow.setColumnValue(m, localObject);
        }
        localOracleRow.markOriginalNull(m, paramResultSet.wasNull());
      }

      if (i != 0)
      {
        this.rows.add(localOracleRow);
      }
      else
      {
        this.rows.add(1, localOracleRow);
      }

      this.rowCount += 1;
    }

    if ((k != 0) || ((i != 0) && (paramResultSet.isAfterLast())) || ((i == 0) && (paramResultSet.isBeforeFirst())))
    {
      this.isPopulateDone = true;
    }

    this.connection = null;

    notifyRowSetChanged();
  }

  public String getCursorName()
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 23);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public synchronized void clearParameters()
    throws SQLException
  {
    this.param = null;
    this.param = new Vector();
  }

  public boolean wasNull()
    throws SQLException
  {
    return this.previousColumnWasNull;
  }

  public void close()
    throws SQLException
  {
    release();

    this.isClosed = true;
  }

  public SQLWarning getWarnings()
    throws SQLException
  {
    return this.sqlWarning;
  }

  public void clearWarnings()
    throws SQLException
  {
    this.sqlWarning = null;
  }

  public ResultSetMetaData getMetaData()
    throws SQLException
  {
    return this.rowsetMetaData;
  }

  public int findColumn(String paramString)
    throws SQLException
  {
    return getColumnIndex(paramString);
  }

  public Object[] getParams()
    throws SQLException
  {
    return this.param.toArray();
  }

  public void setMetaData(RowSetMetaData paramRowSetMetaData)
    throws SQLException
  {
    this.rowsetMetaData = paramRowSetMetaData;

    if (paramRowSetMetaData != null)
    {
      this.colCount = paramRowSetMetaData.getColumnCount();
    }
  }

  public synchronized void execute()
    throws SQLException
  {
    this.isConnectionStayingOpenForTxnControl = false;
    getReader().readData(this);

    this.executeCalled = true;
  }

  public void acceptChanges()
    throws SyncProviderException
  {
    try
    {
      getWriter().writeData(this);
    }
    catch (SQLException localSQLException)
    {
      throw new SyncProviderException(localSQLException.getMessage());
    }
  }

  public void acceptChanges(Connection paramConnection)
    throws SyncProviderException
  {
    this.connection = paramConnection;

    this.isConnectionStayingOpenForTxnControl = true;
    acceptChanges();
  }

  public Object clone()
    throws CloneNotSupportedException
  {
    try
    {
      return createCopy();
    }
    catch (SQLException localSQLException)
    {
      throw new CloneNotSupportedException(new StringBuilder().append("SQL Error occured while cloning: ").append(localSQLException.getMessage()).toString());
    }
  }

  public CachedRowSet createCopy()
    throws SQLException
  {
    OracleCachedRowSet localOracleCachedRowSet = (OracleCachedRowSet)createShared();
    int i = this.rows.size();
    localOracleCachedRowSet.rows = new Vector(i);
    for (int j = 0; j < i; j++) {
      localOracleCachedRowSet.rows.add(((OracleRow)this.rows.elementAt(j)).createCopy());
    }
    return localOracleCachedRowSet;
  }

  public RowSet createShared()
    throws SQLException
  {
    OracleCachedRowSet localOracleCachedRowSet = new OracleCachedRowSet();

    localOracleCachedRowSet.rows = this.rows;

    localOracleCachedRowSet.setDataSource(getDataSource());
    localOracleCachedRowSet.setDataSourceName(getDataSourceName());
    localOracleCachedRowSet.setUsername(getUsername());
    localOracleCachedRowSet.setPassword(getPassword());
    localOracleCachedRowSet.setUrl(getUrl());
    localOracleCachedRowSet.setTypeMap(getTypeMap());
    localOracleCachedRowSet.setMaxFieldSize(getMaxFieldSize());
    localOracleCachedRowSet.setMaxRows(getMaxRows());
    localOracleCachedRowSet.setQueryTimeout(getQueryTimeout());
    localOracleCachedRowSet.setFetchSize(getFetchSize());
    localOracleCachedRowSet.setEscapeProcessing(getEscapeProcessing());
    localOracleCachedRowSet.setConcurrency(getConcurrency());
    localOracleCachedRowSet.setReadOnly(this.readOnly);

    this.rowsetType = getType();
    this.fetchDirection = getFetchDirection();
    localOracleCachedRowSet.setCommand(getCommand());
    localOracleCachedRowSet.setTransactionIsolation(getTransactionIsolation());

    localOracleCachedRowSet.presentRow = this.presentRow;
    localOracleCachedRowSet.colCount = this.colCount;
    localOracleCachedRowSet.rowCount = this.rowCount;
    localOracleCachedRowSet.showDeleted = this.showDeleted;

    localOracleCachedRowSet.syncProvider = this.syncProvider;
    localOracleCachedRowSet.currentPage = this.currentPage;
    localOracleCachedRowSet.pageSize = this.pageSize;
    localOracleCachedRowSet.tableName = (this.tableName == null ? null : this.tableName);
    localOracleCachedRowSet.keyColumns = (this.keyColumns == null ? null : (int[])this.keyColumns.clone());

    int i = this.listener.size();
    for (int j = 0; j < i; j++) {
      localOracleCachedRowSet.listener.add(this.listener.elementAt(j));
    }

    localOracleCachedRowSet.rowsetMetaData = new OracleRowSetMetaData(this.rowsetMetaData);

    i = this.param.size();
    for (j = 0; j < i; j++) {
      localOracleCachedRowSet.param.add(this.param.elementAt(j));
    }
    localOracleCachedRowSet.metaData = new String[this.metaData.length];
    System.arraycopy(this.metaData, 0, localOracleCachedRowSet.metaData, 0, this.metaData.length);

    return localOracleCachedRowSet;
  }

  public void release()
    throws SQLException
  {
    this.rows = null;
    this.rows = new Vector();
    if ((this.connection != null) && (!this.connection.isClosed()))
      this.connection.close();
    this.rowCount = 0;
    this.presentRow = 0;
    notifyRowSetChanged();
  }

  public void restoreOriginal()
    throws SQLException
  {
    int i = 0;
    for (int j = 0; j < this.rowCount; j++)
    {
      OracleRow localOracleRow = (OracleRow)this.rows.elementAt(j);
      if (localOracleRow.isRowInserted())
      {
        this.rows.remove(j);
        this.rowCount -= 1;

        j--;
        i = 1;
      }
      else if (localOracleRow.isRowUpdated())
      {
        localOracleRow.setRowUpdated(false);
        i = 1;
      }
      else if (localOracleRow.isRowDeleted())
      {
        localOracleRow.setRowDeleted(false);
        i = 1;
      }

    }

    if (i == 0)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 315);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    notifyRowSetChanged();

    this.presentRow = 0;
  }

  public Collection toCollection()
    throws SQLException
  {
    Map localMap = Collections.synchronizedMap(new TreeMap());
    try
    {
      for (int i = 0; i < this.rowCount; i++)
      {
        localMap.put(Integer.valueOf(i), ((OracleRow)this.rows.elementAt(i)).toCollection());
      }

    }
    catch (Exception localException)
    {
      localMap = null;

      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 316);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    return localMap.values();
  }

  public Collection toCollection(int paramInt)
    throws SQLException
  {
    if ((paramInt < 1) || (paramInt > this.colCount))
    {
      localObject1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3, new StringBuilder().append("").append(paramInt).toString());
      ((SQLException)localObject1).fillInStackTrace();
      throw ((Throwable)localObject1);
    }

    Object localObject1 = new Vector(this.rowCount);
    for (int i = 0; i < this.rowCount; i++)
    {
      OracleRow localOracleRow = (OracleRow)this.rows.elementAt(i);
      Object localObject2 = localOracleRow.isColumnChanged(paramInt) ? localOracleRow.getModifiedColumn(paramInt) : localOracleRow.getColumn(paramInt);

      ((Vector)localObject1).add(localObject2);
    }

    return localObject1;
  }

  public Collection toCollection(String paramString)
    throws SQLException
  {
    return toCollection(getColumnIndex(paramString));
  }

  public int getRow()
    throws SQLException
  {
    if (this.presentRow > this.rowCount) {
      return this.rowCount;
    }

    return this.presentRow;
  }

  public void cancelRowInsert()
    throws SQLException
  {
    if (getCurrentRow().isRowInserted())
    {
      this.rows.remove(--this.presentRow);
      this.rowCount -= 1;
      notifyRowChanged();
    }
    else
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 317);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  public void cancelRowDelete()
    throws SQLException
  {
    if (getCurrentRow().isRowDeleted())
    {
      getCurrentRow().setRowDeleted(false);
      notifyRowChanged();
    }
    else
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 318);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  public void cancelRowUpdates()
    throws SQLException
  {
    if (getCurrentRow().isRowUpdated())
    {
      this.updateRowFlag = false;
      getCurrentRow().setRowUpdated(false);
      notifyRowChanged();
    }
    else
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 319);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  public void insertRow()
    throws SQLException
  {
    SQLException localSQLException;
    if (isReadOnly())
    {
      localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 309);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if (!this.insertRowFlag)
    {
      localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 317);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if (!this.insertRow.isRowFullyPopulated())
    {
      localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 320);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.insertRow.insertRow();
    this.rows.insertElementAt(this.insertRow, this.insertRowPosition - 1);
    this.insertRowFlag = false;
    this.rowCount += 1;
    notifyRowChanged();
  }

  public void updateRow()
    throws SQLException
  {
    SQLException localSQLException;
    if (isReadOnly())
    {
      localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 309);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if (this.updateRowFlag)
    {
      this.updateRowFlag = false;
      getCurrentRow().setRowUpdated(true);
      notifyRowChanged();
    }
    else
    {
      localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 319);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  public void deleteRow()
    throws SQLException
  {
    if (isReadOnly())
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 309);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    getCurrentRow().setRowDeleted(true);
    notifyRowChanged();
  }

  public void refreshRow()
    throws SQLException
  {
    OracleRow localOracleRow = getCurrentRow();
    if (localOracleRow.isRowUpdated())
      localOracleRow.cancelRowUpdates();
  }

  public void moveToInsertRow()
    throws SQLException
  {
    if (isReadOnly())
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 309);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.insertRow = new OracleRow(this.colCount, true);
    this.insertRowFlag = true;

    if (isAfterLast())
      this.insertRowPosition = this.presentRow;
    else
      this.insertRowPosition = (this.presentRow + 1);
  }

  public void moveToCurrentRow()
    throws SQLException
  {
    if (isReadOnly())
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 309);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.insertRowFlag = false;
    this.updateRowFlag = false;
    absolute(this.presentRow);
  }

  public boolean rowUpdated()
    throws SQLException
  {
    return getCurrentRow().isRowUpdated();
  }

  public boolean rowInserted()
    throws SQLException
  {
    return getCurrentRow().isRowInserted();
  }

  public boolean rowDeleted()
    throws SQLException
  {
    return getCurrentRow().isRowDeleted();
  }

  public ResultSet getOriginalRow()
    throws SQLException
  {
    OracleCachedRowSet localOracleCachedRowSet = new OracleCachedRowSet();
    localOracleCachedRowSet.rowsetMetaData = this.rowsetMetaData;
    localOracleCachedRowSet.rowCount = 1;
    localOracleCachedRowSet.colCount = this.colCount;
    localOracleCachedRowSet.presentRow = 0;
    localOracleCachedRowSet.setReader(null);
    localOracleCachedRowSet.setWriter(null);
    OracleRow localOracleRow = new OracleRow(this.rowsetMetaData.getColumnCount(), getCurrentRow().getOriginalRow());

    localOracleCachedRowSet.rows.add(localOracleRow);

    return localOracleCachedRowSet;
  }

  public ResultSet getOriginal()
    throws SQLException
  {
    OracleCachedRowSet localOracleCachedRowSet = new OracleCachedRowSet();
    localOracleCachedRowSet.rowsetMetaData = this.rowsetMetaData;
    localOracleCachedRowSet.rowCount = this.rowCount;
    localOracleCachedRowSet.colCount = this.colCount;

    localOracleCachedRowSet.presentRow = 0;

    localOracleCachedRowSet.setType(1004);
    localOracleCachedRowSet.setConcurrency(1008);

    localOracleCachedRowSet.setReader(null);
    localOracleCachedRowSet.setWriter(null);
    int i = this.rowsetMetaData.getColumnCount();
    OracleRow localOracleRow = null;

    Iterator localIterator = this.rows.iterator();

    for (; localIterator.hasNext(); 
      localOracleCachedRowSet.rows.add(localOracleRow)) {
      localOracleRow = new OracleRow(i, ((OracleRow)localIterator.next()).getOriginalRow());
    }
    return localOracleCachedRowSet;
  }

  public void setNull(int paramInt1, int paramInt2)
    throws SQLException
  {
    checkParamIndex(paramInt1);

    this.param.add(paramInt1 - 1, null);
  }

  public void setNull(int paramInt1, int paramInt2, String paramString)
    throws SQLException
  {
    checkParamIndex(paramInt1);
    Object[] arrayOfObject = { Integer.valueOf(paramInt2), paramString };
    this.param.add(paramInt1 - 1, arrayOfObject);
  }

  public void setBoolean(int paramInt, boolean paramBoolean)
    throws SQLException
  {
    checkParamIndex(paramInt);
    this.param.add(paramInt - 1, Boolean.valueOf(paramBoolean));
  }

  public void setByte(int paramInt, byte paramByte)
    throws SQLException
  {
    checkParamIndex(paramInt);
    this.param.add(paramInt - 1, new Byte(paramByte));
  }

  public void setShort(int paramInt, short paramShort)
    throws SQLException
  {
    checkParamIndex(paramInt);
    this.param.add(paramInt - 1, Short.valueOf(paramShort));
  }

  public void setInt(int paramInt1, int paramInt2)
    throws SQLException
  {
    checkParamIndex(paramInt1);
    this.param.add(paramInt1 - 1, Integer.valueOf(paramInt2));
  }

  public void setLong(int paramInt, long paramLong)
    throws SQLException
  {
    checkParamIndex(paramInt);
    this.param.add(paramInt - 1, Long.valueOf(paramLong));
  }

  public void setFloat(int paramInt, float paramFloat)
    throws SQLException
  {
    checkParamIndex(paramInt);
    this.param.add(paramInt - 1, Float.valueOf(paramFloat));
  }

  public void setDouble(int paramInt, double paramDouble)
    throws SQLException
  {
    checkParamIndex(paramInt);
    this.param.add(paramInt - 1, Double.valueOf(paramDouble));
  }

  public void setBigDecimal(int paramInt, BigDecimal paramBigDecimal)
    throws SQLException
  {
    checkParamIndex(paramInt);
    this.param.add(paramInt - 1, paramBigDecimal);
  }

  public void setString(int paramInt, String paramString)
    throws SQLException
  {
    checkParamIndex(paramInt);
    this.param.add(paramInt - 1, paramString);
  }

  public void setBytes(int paramInt, byte[] paramArrayOfByte)
    throws SQLException
  {
    checkParamIndex(paramInt);
    this.param.add(paramInt - 1, paramArrayOfByte);
  }

  public void setDate(int paramInt, java.sql.Date paramDate)
    throws SQLException
  {
    checkParamIndex(paramInt);
    this.param.add(paramInt - 1, paramDate);
  }

  public void setTime(int paramInt, Time paramTime)
    throws SQLException
  {
    checkParamIndex(paramInt);
    this.param.add(paramInt - 1, paramTime);
  }

  public void setObject(int paramInt, Object paramObject)
    throws SQLException
  {
    checkParamIndex(paramInt);
    this.param.add(paramInt - 1, paramObject);
  }

  public void setRef(int paramInt, Ref paramRef)
    throws SQLException
  {
    checkParamIndex(paramInt);
    this.param.add(paramInt - 1, paramRef);
  }

  public void setBlob(int paramInt, Blob paramBlob)
    throws SQLException
  {
    checkParamIndex(paramInt);
    this.param.add(paramInt - 1, paramBlob);
  }

  public void setClob(int paramInt, Clob paramClob)
    throws SQLException
  {
    checkParamIndex(paramInt);
    this.param.add(paramInt - 1, paramClob);
  }

  public void setArray(int paramInt, Array paramArray)
    throws SQLException
  {
    checkParamIndex(paramInt);
    this.param.add(paramInt - 1, paramArray);
  }

  public void setBinaryStream(int paramInt1, InputStream paramInputStream, int paramInt2)
    throws SQLException
  {
    checkParamIndex(paramInt1);
    Object[] arrayOfObject = { paramInputStream, Integer.valueOf(paramInt2), Integer.valueOf(2) };

    this.param.add(paramInt1 - 1, arrayOfObject);
  }

  public void setTime(int paramInt, Time paramTime, Calendar paramCalendar)
    throws SQLException
  {
    checkParamIndex(paramInt);
    Object[] arrayOfObject = { paramTime, paramCalendar };
    this.param.add(paramInt - 1, arrayOfObject);
  }

  public void setTimestamp(int paramInt, Timestamp paramTimestamp, Calendar paramCalendar)
    throws SQLException
  {
    checkParamIndex(paramInt);
    Object[] arrayOfObject = { paramTimestamp, paramCalendar };
    this.param.add(paramInt - 1, arrayOfObject);
  }

  public void setTimestamp(int paramInt, Timestamp paramTimestamp)
    throws SQLException
  {
    checkParamIndex(paramInt);
    this.param.add(paramInt - 1, paramTimestamp);
  }

  public void setAsciiStream(int paramInt1, InputStream paramInputStream, int paramInt2)
    throws SQLException
  {
    checkParamIndex(paramInt1);
    Object[] arrayOfObject = { paramInputStream, Integer.valueOf(paramInt2), Integer.valueOf(3) };

    this.param.add(paramInt1 - 1, arrayOfObject);
  }

  public void setUnicodeStream(int paramInt1, InputStream paramInputStream, int paramInt2)
    throws SQLException
  {
    checkParamIndex(paramInt1);
    Object[] arrayOfObject = { paramInputStream, Integer.valueOf(paramInt2), Integer.valueOf(1) };

    this.param.add(paramInt1 - 1, arrayOfObject);
  }

  public void setCharacterStream(int paramInt1, Reader paramReader, int paramInt2)
    throws SQLException
  {
    checkParamIndex(paramInt1);
    Object[] arrayOfObject = { paramReader, Integer.valueOf(paramInt2), Integer.valueOf(4) };

    this.param.add(paramInt1 - 1, arrayOfObject);
  }

  public void setObject(int paramInt1, Object paramObject, int paramInt2, int paramInt3)
    throws SQLException
  {
    checkParamIndex(paramInt1);
    Object[] arrayOfObject = { paramObject, Integer.valueOf(paramInt2), Integer.valueOf(paramInt3) };
    this.param.add(paramInt1 - 1, arrayOfObject);
  }

  public void setObject(int paramInt1, Object paramObject, int paramInt2)
    throws SQLException
  {
    checkParamIndex(paramInt1);
    Object[] arrayOfObject = { paramObject, Integer.valueOf(paramInt2) };
    this.param.add(paramInt1 - 1, arrayOfObject);
  }

  public void setDate(int paramInt, java.sql.Date paramDate, Calendar paramCalendar)
    throws SQLException
  {
    checkParamIndex(paramInt);
    Object[] arrayOfObject = { paramDate, paramCalendar };
    this.param.add(paramInt - 1, arrayOfObject);
  }

  public void setURL(int paramInt, URL paramURL)
    throws SQLException
  {
    checkParamIndex(paramInt);
    this.param.add(paramInt - 1, paramURL.toString());
  }

  public synchronized Object getObject(int paramInt)
    throws SQLException
  {
    int i = this.presentRow * this.colCount + paramInt - 1;
    Object localObject = null;

    if (!isUpdated(paramInt))
      localObject = getCurrentRow().getColumn(paramInt);
    else
      localObject = getCurrentRow().getModifiedColumn(paramInt);
    this.previousColumnWasNull = (localObject == null);

    return localObject;
  }

  private synchronized Number getNumber(int paramInt)
    throws SQLException
  {
    Object localObject = getObject(paramInt);

    if ((localObject == null) || ((localObject instanceof BigDecimal)) || ((localObject instanceof Number)))
    {
      return (Number)localObject;
    }
    if ((localObject instanceof Boolean)) {
      return Integer.valueOf(((Boolean)localObject).booleanValue() ? 1 : 0);
    }
    if ((localObject instanceof String))
    {
      try
      {
        return new BigDecimal((String)localObject);
      }
      catch (NumberFormatException localNumberFormatException)
      {
        SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 59);
        localSQLException2.fillInStackTrace();
        throw localSQLException2;
      }

    }

    SQLException localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 59);
    localSQLException1.fillInStackTrace();
    throw localSQLException1;
  }

  public Object getObject(int paramInt, Map paramMap)
    throws SQLException
  {
    return getObject(paramInt);
  }

  public boolean getBoolean(int paramInt)
    throws SQLException
  {
    Object localObject = getObject(paramInt);

    if (localObject == null) {
      return false;
    }
    if ((localObject instanceof Boolean)) {
      return ((Boolean)localObject).booleanValue();
    }
    if ((localObject instanceof Number)) {
      return ((Number)localObject).doubleValue() != 0.0D;
    }

    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 59);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public byte getByte(int paramInt)
    throws SQLException
  {
    Object localObject1 = getObject(paramInt);

    if (localObject1 == null) {
      return 0;
    }
    if ((localObject1 instanceof BigDecimal))
    {
      localObject2 = (BigDecimal)localObject1;
      if ((((BigDecimal)localObject2).compareTo(new BigDecimal(127)) == 1) || (((BigDecimal)localObject2).compareTo(new BigDecimal(-128)) == -1))
      {
        SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 26);
        localSQLException.fillInStackTrace();
        throw localSQLException;
      }

      return ((BigDecimal)localObject2).byteValue();
    }

    if ((localObject1 instanceof Number)) {
      return ((Number)localObject1).byteValue();
    }
    if ((localObject1 instanceof String)) {
      return ((String)localObject1).getBytes()[0];
    }
    if ((localObject1 instanceof OracleSerialBlob))
    {
      localObject2 = (OracleSerialBlob)localObject1;
      return localObject2.getBytes(1L, 1)[0];
    }
    if ((localObject1 instanceof OracleSerialClob))
    {
      localObject2 = (OracleSerialClob)localObject1;
      return localObject2.getSubString(1L, 1).getBytes()[0];
    }

    Object localObject2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 59);
    ((SQLException)localObject2).fillInStackTrace();
    throw ((Throwable)localObject2);
  }

  public short getShort(int paramInt)
    throws SQLException
  {
    Number localNumber = getNumber(paramInt);

    return localNumber == null ? 0 : localNumber.shortValue();
  }

  public int getInt(int paramInt)
    throws SQLException
  {
    Number localNumber = getNumber(paramInt);

    return localNumber == null ? 0 : localNumber.intValue();
  }

  public long getLong(int paramInt)
    throws SQLException
  {
    Number localNumber = getNumber(paramInt);

    return localNumber == null ? 0L : localNumber.longValue();
  }

  public float getFloat(int paramInt)
    throws SQLException
  {
    Number localNumber = getNumber(paramInt);

    return localNumber == null ? 0.0F : localNumber.floatValue();
  }

  public double getDouble(int paramInt)
    throws SQLException
  {
    Number localNumber = getNumber(paramInt);

    return localNumber == null ? 0.0D : localNumber.doubleValue();
  }

  public BigDecimal getBigDecimal(int paramInt)
    throws SQLException
  {
    Number localNumber = getNumber(paramInt);

    if ((localNumber == null) || ((localNumber instanceof BigDecimal))) {
      return (BigDecimal)localNumber;
    }
    return new BigDecimal(localNumber.doubleValue());
  }

  public BigDecimal getBigDecimal(int paramInt1, int paramInt2)
    throws SQLException
  {
    return getBigDecimal(paramInt1);
  }

  public java.sql.Date getDate(int paramInt)
    throws SQLException
  {
    Object localObject1 = getObject(paramInt);

    if (localObject1 == null) {
      return (java.sql.Date)localObject1;
    }
    if ((localObject1 instanceof Time))
    {
      localObject2 = (Time)localObject1;
      return new java.sql.Date(((Time)localObject2).getTime());
    }

    if ((localObject1 instanceof java.util.Date))
    {
      localObject2 = (java.util.Date)localObject1;
      return new java.sql.Date(((java.util.Date)localObject2).getYear(), ((java.util.Date)localObject2).getMonth(), ((java.util.Date)localObject2).getDate());
    }

    if ((localObject1 instanceof TIMESTAMP)) {
      return ((TIMESTAMP)localObject1).dateValue();
    }
    if ((localObject1 instanceof TIMESTAMPTZ)) {
      return ((TIMESTAMPTZ)localObject1).dateValue(getConnectionInternal());
    }
    if ((localObject1 instanceof TIMESTAMPLTZ))
    {
      localObject2 = getConnectionInternal();

      return ((TIMESTAMPLTZ)localObject1).dateValue((Connection)localObject2, getSessionCalendar((Connection)localObject2));
    }

    Object localObject2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4);
    ((SQLException)localObject2).fillInStackTrace();
    throw ((Throwable)localObject2);
  }

  public java.sql.Date getDate(int paramInt, Calendar paramCalendar)
    throws SQLException
  {
    return getDate(paramInt);
  }

  public Time getTime(int paramInt)
    throws SQLException
  {
    Object localObject1 = getObject(paramInt);

    if (localObject1 == null) {
      return (Time)localObject1;
    }
    if ((localObject1 instanceof java.util.Date))
    {
      localObject2 = (java.util.Date)localObject1;
      return new Time(((java.util.Date)localObject2).getHours(), ((java.util.Date)localObject2).getMinutes(), ((java.util.Date)localObject2).getSeconds());
    }

    if ((localObject1 instanceof TIMESTAMP)) {
      return ((TIMESTAMP)localObject1).timeValue();
    }
    if ((localObject1 instanceof TIMESTAMPTZ)) {
      return ((TIMESTAMPTZ)localObject1).timeValue(getConnectionInternal());
    }
    if ((localObject1 instanceof TIMESTAMPLTZ))
    {
      localObject2 = getConnectionInternal();

      return ((TIMESTAMPLTZ)localObject1).timeValue((Connection)localObject2, getSessionCalendar((Connection)localObject2));
    }

    Object localObject2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4);
    ((SQLException)localObject2).fillInStackTrace();
    throw ((Throwable)localObject2);
  }

  public Time getTime(int paramInt, Calendar paramCalendar)
    throws SQLException
  {
    return getTime(paramInt);
  }

  public Timestamp getTimestamp(int paramInt)
    throws SQLException
  {
    Object localObject1 = getObject(paramInt);

    if ((localObject1 == null) || ((localObject1 instanceof Timestamp))) {
      return (Timestamp)localObject1;
    }
    if ((localObject1 instanceof java.util.Date)) {
      return new Timestamp(((java.util.Date)localObject1).getTime());
    }
    if ((localObject1 instanceof TIMESTAMP)) {
      return ((TIMESTAMP)localObject1).timestampValue();
    }
    if ((localObject1 instanceof TIMESTAMPTZ)) {
      return ((TIMESTAMPTZ)localObject1).timestampValue(getConnectionInternal());
    }
    if ((localObject1 instanceof TIMESTAMPLTZ))
    {
      localObject2 = getConnectionInternal();

      return ((TIMESTAMPLTZ)localObject1).timestampValue((Connection)localObject2, getSessionCalendar((Connection)localObject2));
    }

    Object localObject2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4);
    ((SQLException)localObject2).fillInStackTrace();
    throw ((Throwable)localObject2);
  }

  public Timestamp getTimestamp(int paramInt, Calendar paramCalendar)
    throws SQLException
  {
    return getTimestamp(paramInt);
  }

  public byte[] getBytes(int paramInt)
    throws SQLException
  {
    Object localObject1 = getObject(paramInt);

    if (localObject1 == null) {
      return (byte[])localObject1;
    }
    if ((localObject1 instanceof byte[])) {
      return (byte[])localObject1;
    }
    if ((localObject1 instanceof String)) {
      return (byte[])((String)localObject1).getBytes();
    }
    if ((localObject1 instanceof Number)) {
      return (byte[])((Number)localObject1).toString().getBytes();
    }
    if ((localObject1 instanceof BigDecimal)) {
      return (byte[])((BigDecimal)localObject1).toString().getBytes();
    }
    if ((localObject1 instanceof OracleSerialBlob))
    {
      localObject2 = (OracleSerialBlob)localObject1;
      return ((OracleSerialBlob)localObject2).getBytes(1L, (int)((OracleSerialBlob)localObject2).length());
    }
    if ((localObject1 instanceof OracleSerialClob))
    {
      localObject2 = (OracleSerialClob)localObject1;
      return ((OracleSerialClob)localObject2).getSubString(1L, (int)((OracleSerialClob)localObject2).length()).getBytes();
    }

    Object localObject2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 59);
    ((SQLException)localObject2).fillInStackTrace();
    throw ((Throwable)localObject2);
  }

  public Ref getRef(int paramInt)
    throws SQLException
  {
    Object localObject = getObject(paramInt);

    if ((localObject == null) || ((localObject instanceof Ref))) {
      return (Ref)localObject;
    }

    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public Blob getBlob(int paramInt)
    throws SQLException
  {
    Object localObject = getObject(paramInt);

    if ((localObject == null) || ((localObject instanceof OracleSerialBlob))) {
      return (Blob)localObject;
    }

    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public Clob getClob(int paramInt)
    throws SQLException
  {
    Object localObject = getObject(paramInt);

    if ((localObject == null) || ((localObject instanceof OracleSerialClob))) {
      return (Clob)localObject;
    }

    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public Array getArray(int paramInt)
    throws SQLException
  {
    Object localObject = getObject(paramInt);

    if ((localObject == null) || ((localObject instanceof Array))) {
      return (Array)localObject;
    }

    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public String getString(int paramInt)
    throws SQLException
  {
    Object localObject1 = getObject(paramInt);

    if (localObject1 == null) {
      return (String)localObject1;
    }
    if ((localObject1 instanceof String)) {
      return (String)localObject1;
    }
    if (((localObject1 instanceof Number)) || ((localObject1 instanceof BigDecimal))) {
      return localObject1.toString();
    }
    if ((localObject1 instanceof java.sql.Date)) {
      return localObject1.toString();
    }
    if ((localObject1 instanceof Timestamp)) {
      return localObject1.toString();
    }
    if ((localObject1 instanceof byte[]))
      return new String((byte[])localObject1);
    Object localObject2;
    if ((localObject1 instanceof OracleSerialClob))
    {
      localObject2 = (OracleSerialClob)localObject1;
      return ((OracleSerialClob)localObject2).getSubString(1L, (int)((OracleSerialClob)localObject2).length());
    }

    if ((localObject1 instanceof OracleSerialBlob))
    {
      localObject2 = (OracleSerialBlob)localObject1;
      return new String(((OracleSerialBlob)localObject2).getBytes(1L, (int)((OracleSerialBlob)localObject2).length()));
    }

    if ((localObject1 instanceof URL)) {
      return ((URL)localObject1).toString();
    }
    if ((localObject1 instanceof ROWID)) {
      return ((ROWID)localObject1).stringValue();
    }
    if ((localObject1 instanceof Reader))
    {
      try
      {
        localObject2 = (Reader)localObject1;
        localObject3 = new char[1024];
        int i = 0;
        StringBuffer localStringBuffer = new StringBuffer(1024);
        while ((i = ((Reader)localObject2).read((char[])localObject3)) > 0)
          localStringBuffer.append((char[])localObject3, 0, i);
        return localStringBuffer.substring(0, localStringBuffer.length());
      }
      catch (IOException localIOException)
      {
        Object localObject3 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 321, localIOException.getMessage());
        ((SQLException)localObject3).fillInStackTrace();
        throw ((Throwable)localObject3);
      }

    }

    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 59);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public InputStream getAsciiStream(int paramInt)
    throws SQLException
  {
    InputStream localInputStream = getStream(paramInt);

    return localInputStream == null ? null : localInputStream;
  }

  public InputStream getUnicodeStream(int paramInt)
    throws SQLException
  {
    Object localObject = getObject(paramInt);

    if (localObject == null) {
      return (InputStream)localObject;
    }

    if ((localObject instanceof String)) {
      return new StringBufferInputStream((String)localObject);
    }

    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 59);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public InputStream getBinaryStream(int paramInt)
    throws SQLException
  {
    InputStream localInputStream = getStream(paramInt);

    return localInputStream == null ? null : localInputStream;
  }

  public synchronized Reader getCharacterStream(int paramInt)
    throws SQLException
  {
    Object localObject;
    try
    {
      InputStream localInputStream = getAsciiStream(paramInt);

      if (localInputStream == null) {
        return null;
      }
      localObject = new StringBuffer();
      int i = 0;
      while ((i = localInputStream.read()) != -1) {
        ((StringBuffer)localObject).append((char)i);
      }

      char[] arrayOfChar = new char[((StringBuffer)localObject).length()];
      ((StringBuffer)localObject).getChars(0, ((StringBuffer)localObject).length(), arrayOfChar, 0);
      CharArrayReader localCharArrayReader = new CharArrayReader(arrayOfChar);
      arrayOfChar = null;

      return localCharArrayReader;
    }
    catch (IOException localIOException)
    {
      localObject = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 322);
      ((SQLException)localObject).fillInStackTrace();
    }throw ((Throwable)localObject);
  }

  public synchronized Object getObject(String paramString)
    throws SQLException
  {
    return getObject(getColumnIndex(paramString));
  }

  public boolean getBoolean(String paramString)
    throws SQLException
  {
    return getBoolean(getColumnIndex(paramString));
  }

  public byte getByte(String paramString)
    throws SQLException
  {
    return getByte(getColumnIndex(paramString));
  }

  public short getShort(String paramString)
    throws SQLException
  {
    return getShort(getColumnIndex(paramString));
  }

  public int getInt(String paramString)
    throws SQLException
  {
    return getInt(getColumnIndex(paramString));
  }

  public long getLong(String paramString)
    throws SQLException
  {
    return getLong(getColumnIndex(paramString));
  }

  public float getFloat(String paramString)
    throws SQLException
  {
    return getFloat(getColumnIndex(paramString));
  }

  public double getDouble(String paramString)
    throws SQLException
  {
    return getDouble(getColumnIndex(paramString));
  }

  public BigDecimal getBigDecimal(String paramString, int paramInt)
    throws SQLException
  {
    return getBigDecimal(getColumnIndex(paramString), paramInt);
  }

  public byte[] getBytes(String paramString)
    throws SQLException
  {
    return getBytes(getColumnIndex(paramString));
  }

  public java.sql.Date getDate(String paramString)
    throws SQLException
  {
    return getDate(getColumnIndex(paramString));
  }

  public Time getTime(String paramString)
    throws SQLException
  {
    return getTime(getColumnIndex(paramString));
  }

  public Timestamp getTimestamp(String paramString)
    throws SQLException
  {
    return getTimestamp(getColumnIndex(paramString));
  }

  public Time getTime(String paramString, Calendar paramCalendar)
    throws SQLException
  {
    return getTime(getColumnIndex(paramString), paramCalendar);
  }

  public java.sql.Date getDate(String paramString, Calendar paramCalendar)
    throws SQLException
  {
    return getDate(getColumnIndex(paramString), paramCalendar);
  }

  public InputStream getAsciiStream(String paramString)
    throws SQLException
  {
    return getAsciiStream(getColumnIndex(paramString));
  }

  public InputStream getUnicodeStream(String paramString)
    throws SQLException
  {
    return getUnicodeStream(getColumnIndex(paramString));
  }

  public String getString(String paramString)
    throws SQLException
  {
    return getString(getColumnIndex(paramString));
  }

  public InputStream getBinaryStream(String paramString)
    throws SQLException
  {
    return getBinaryStream(getColumnIndex(paramString));
  }

  public Reader getCharacterStream(String paramString)
    throws SQLException
  {
    return getCharacterStream(getColumnIndex(paramString));
  }

  public BigDecimal getBigDecimal(String paramString)
    throws SQLException
  {
    return getBigDecimal(getColumnIndex(paramString));
  }

  public Timestamp getTimestamp(String paramString, Calendar paramCalendar)
    throws SQLException
  {
    return getTimestamp(getColumnIndex(paramString), paramCalendar);
  }

  public Object getObject(String paramString, Map paramMap)
    throws SQLException
  {
    return getObject(getColumnIndex(paramString), paramMap);
  }

  public Ref getRef(String paramString)
    throws SQLException
  {
    return getRef(getColumnIndex(paramString));
  }

  public Blob getBlob(String paramString)
    throws SQLException
  {
    return getBlob(getColumnIndex(paramString));
  }

  public Clob getClob(String paramString)
    throws SQLException
  {
    return getClob(getColumnIndex(paramString));
  }

  public Array getArray(String paramString)
    throws SQLException
  {
    return getArray(getColumnIndex(paramString));
  }

  public void updateObject(int paramInt, Object paramObject)
    throws SQLException
  {
    updateObject(paramInt, paramObject, (int[])null);
  }

  protected synchronized void updateObject(int paramInt, Object paramObject, int[] paramArrayOfInt)
    throws SQLException
  {
    checkColumnIndex(paramInt);
    if (this.insertRowFlag)
    {
      checkAndFilterObject(paramInt, paramObject);
      this.insertRow.updateObject(paramInt, paramObject, paramArrayOfInt);
    }
    else if ((!isBeforeFirst()) && (!isAfterLast()))
    {
      this.updateRowFlag = true;
      this.updateRowPosition = this.presentRow;
      getCurrentRow().updateObject(paramInt, paramObject, paramArrayOfInt);
    }
    else {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 90);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  public void updateNull(int paramInt)
    throws SQLException
  {
    updateObject(paramInt, null);
  }

  public synchronized void updateCharacterStream(int paramInt1, Reader paramReader, int paramInt2)
    throws SQLException
  {
    updateCharacterStreamInternal(paramInt1, paramReader, paramInt2, false);
  }

  void updateCharacterStreamInternal(int paramInt1, Reader paramReader, int paramInt2, boolean paramBoolean)
    throws SQLException
  {
    checkColumnIndex(paramInt1);

    if (paramInt2 < 0)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    int i = getMetaData().getColumnType(paramInt1);
    Object localObject;
    if (!isStreamType(i))
    {
      try
      {
        int j = 0;
        int m = paramInt2;
        char[] arrayOfChar = new char[1024];
        StringBuilder localStringBuilder = new StringBuilder(1024);

        while (m > 0)
        {
          if (m >= 1024)
            j = paramReader.read(arrayOfChar);
          else {
            j = paramReader.read(arrayOfChar, 0, m);
          }

          if (j == -1) {
            break;
          }
          localStringBuilder.append(arrayOfChar, 0, j);
          m -= j;
        }

        paramReader.close();
        if (m == paramInt2)
        {
          updateNull(paramInt1);
          return;
        }

        updateString(paramInt1, localStringBuilder.toString());
      }
      catch (IOException localIOException)
      {
        localObject = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localIOException);
        ((SQLException)localObject).fillInStackTrace();
        throw ((Throwable)localObject);
      }

    }
    else
    {
      int k = paramBoolean ? 4 : 3;

      localObject = new int[] { paramInt2, k };

      updateObject(paramInt1, paramReader, (int[])localObject);
    }
  }

  public void updateCharacterStream(String paramString, Reader paramReader, int paramInt)
    throws SQLException
  {
    updateCharacterStream(getColumnIndex(paramString), paramReader, paramInt);
  }

  public void updateTimestamp(String paramString, Timestamp paramTimestamp)
    throws SQLException
  {
    updateTimestamp(getColumnIndex(paramString), paramTimestamp);
  }

  public void updateBinaryStream(String paramString, InputStream paramInputStream, int paramInt)
    throws SQLException
  {
    updateBinaryStream(getColumnIndex(paramString), paramInputStream, paramInt);
  }

  public synchronized void updateBinaryStream(int paramInt1, InputStream paramInputStream, int paramInt2)
    throws SQLException
  {
    checkColumnIndex(paramInt1);

    if (paramInt2 < 0)
    {
      SQLException localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68);
      localSQLException1.fillInStackTrace();
      throw localSQLException1;
    }

    int i = getMetaData().getColumnType(paramInt1);
    if (!isStreamType(i))
    {
      try
      {
        int j = 0;
        int k = paramInt2;
        byte[] arrayOfByte = new byte[1024];
        ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream(1024);

        while (k > 0)
        {
          if (k >= 1024)
            j = paramInputStream.read(arrayOfByte);
          else {
            j = paramInputStream.read(arrayOfByte, 0, k);
          }

          if (j == -1) {
            break;
          }
          localByteArrayOutputStream.write(arrayOfByte, 0, j);
          k -= j;
        }

        paramInputStream.close();
        if (k == paramInt2)
        {
          updateNull(paramInt1);
          return;
        }

        updateBytes(paramInt1, localByteArrayOutputStream.toByteArray());
      }
      catch (IOException localIOException)
      {
        SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localIOException);
        localSQLException2.fillInStackTrace();
        throw localSQLException2;
      }

    }
    else
    {
      int[] arrayOfInt = { paramInt2, 2 };

      updateObject(paramInt1, paramInputStream, arrayOfInt);
    }
  }

  public synchronized void updateAsciiStream(int paramInt1, InputStream paramInputStream, int paramInt2)
    throws SQLException
  {
    checkColumnIndex(paramInt1);

    if (paramInt2 < 0)
    {
      SQLException localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68);
      localSQLException1.fillInStackTrace();
      throw localSQLException1;
    }

    int i = getMetaData().getColumnType(paramInt1);
    if (!isStreamType(i))
    {
      try
      {
        int j = 0;
        int k = paramInt2;
        byte[] arrayOfByte = new byte[1024];
        char[] arrayOfChar = new char[1024];
        StringBuilder localStringBuilder = new StringBuilder(1024);

        while (k > 0)
        {
          if (k >= 1024)
            j = paramInputStream.read(arrayOfByte);
          else {
            j = paramInputStream.read(arrayOfByte, 0, k);
          }

          if (j == -1) {
            break;
          }
          DBConversion.asciiBytesToJavaChars(arrayOfByte, j, arrayOfChar);

          localStringBuilder.append(arrayOfChar, 0, j);
          k -= j;
        }

        paramInputStream.close();
        if (k == paramInt2)
        {
          updateNull(paramInt1);
          return;
        }

        updateString(paramInt1, localStringBuilder.toString());
      }
      catch (IOException localIOException)
      {
        SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localIOException);
        localSQLException2.fillInStackTrace();
        throw localSQLException2;
      }

    }
    else
    {
      int[] arrayOfInt = { paramInt2, 1 };

      updateObject(paramInt1, paramInputStream, arrayOfInt);
    }
  }

  public void updateTimestamp(int paramInt, Timestamp paramTimestamp)
    throws SQLException
  {
    updateObject(paramInt, paramTimestamp);
  }

  public void updateBoolean(int paramInt, boolean paramBoolean)
    throws SQLException
  {
    updateObject(paramInt, Boolean.valueOf(paramBoolean));
  }

  public void updateByte(int paramInt, byte paramByte)
    throws SQLException
  {
    updateObject(paramInt, new Byte(paramByte));
  }

  public void updateShort(int paramInt, short paramShort)
    throws SQLException
  {
    updateObject(paramInt, Short.valueOf(paramShort));
  }

  public void updateInt(int paramInt1, int paramInt2)
    throws SQLException
  {
    updateObject(paramInt1, Integer.valueOf(paramInt2));
  }

  public void updateLong(int paramInt, long paramLong)
    throws SQLException
  {
    updateObject(paramInt, Long.valueOf(paramLong));
  }

  public void updateFloat(int paramInt, float paramFloat)
    throws SQLException
  {
    updateObject(paramInt, Float.valueOf(paramFloat));
  }

  public void updateDouble(int paramInt, double paramDouble)
    throws SQLException
  {
    updateObject(paramInt, Double.valueOf(paramDouble));
  }

  public void updateBigDecimal(int paramInt, BigDecimal paramBigDecimal)
    throws SQLException
  {
    updateObject(paramInt, paramBigDecimal);
  }

  public void updateString(int paramInt, String paramString)
    throws SQLException
  {
    updateObject(paramInt, paramString);
  }

  public void updateBytes(int paramInt, byte[] paramArrayOfByte)
    throws SQLException
  {
    updateObject(paramInt, paramArrayOfByte);
  }

  public void updateDate(int paramInt, java.sql.Date paramDate)
    throws SQLException
  {
    updateObject(paramInt, paramDate);
  }

  public void updateTime(int paramInt, Time paramTime)
    throws SQLException
  {
    updateObject(paramInt, new Timestamp(0, 0, 0, paramTime.getHours(), paramTime.getMinutes(), paramTime.getSeconds(), 0));
  }

  public void updateObject(int paramInt1, Object paramObject, int paramInt2)
    throws SQLException
  {
    if (!(paramObject instanceof Number))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 323);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    updateObject(paramInt1, new BigDecimal(new BigInteger(((Number)paramObject).toString()), paramInt2));
  }

  public void updateNull(String paramString)
    throws SQLException
  {
    updateNull(getColumnIndex(paramString));
  }

  public void updateAsciiStream(String paramString, InputStream paramInputStream, int paramInt)
    throws SQLException
  {
    updateAsciiStream(getColumnIndex(paramString), paramInputStream, paramInt);
  }

  public void updateBoolean(String paramString, boolean paramBoolean)
    throws SQLException
  {
    updateBoolean(getColumnIndex(paramString), paramBoolean);
  }

  public void updateByte(String paramString, byte paramByte)
    throws SQLException
  {
    updateByte(getColumnIndex(paramString), paramByte);
  }

  public void updateShort(String paramString, short paramShort)
    throws SQLException
  {
    updateShort(getColumnIndex(paramString), paramShort);
  }

  public void updateInt(String paramString, int paramInt)
    throws SQLException
  {
    updateInt(getColumnIndex(paramString), paramInt);
  }

  public void updateLong(String paramString, long paramLong)
    throws SQLException
  {
    updateLong(getColumnIndex(paramString), paramLong);
  }

  public void updateFloat(String paramString, float paramFloat)
    throws SQLException
  {
    updateFloat(getColumnIndex(paramString), paramFloat);
  }

  public void updateDouble(String paramString, double paramDouble)
    throws SQLException
  {
    updateDouble(getColumnIndex(paramString), paramDouble);
  }

  public void updateBigDecimal(String paramString, BigDecimal paramBigDecimal)
    throws SQLException
  {
    updateBigDecimal(getColumnIndex(paramString), paramBigDecimal);
  }

  public void updateString(String paramString1, String paramString2)
    throws SQLException
  {
    updateString(getColumnIndex(paramString1), paramString2);
  }

  public void updateBytes(String paramString, byte[] paramArrayOfByte)
    throws SQLException
  {
    updateBytes(getColumnIndex(paramString), paramArrayOfByte);
  }

  public void updateDate(String paramString, java.sql.Date paramDate)
    throws SQLException
  {
    updateDate(getColumnIndex(paramString), paramDate);
  }

  public void updateTime(String paramString, Time paramTime)
    throws SQLException
  {
    updateTime(getColumnIndex(paramString), paramTime);
  }

  public void updateObject(String paramString, Object paramObject)
    throws SQLException
  {
    updateObject(getColumnIndex(paramString), paramObject);
  }

  public void updateObject(String paramString, Object paramObject, int paramInt)
    throws SQLException
  {
    updateObject(getColumnIndex(paramString), paramObject, paramInt);
  }

  public URL getURL(int paramInt)
    throws SQLException
  {
    Object localObject = getObject(paramInt);

    if (localObject == null) {
      return (URL)localObject;
    }
    if ((localObject instanceof URL)) {
      return (URL)localObject;
    }
    if ((localObject instanceof String))
    {
      try
      {
        return new URL((String)localObject);
      }
      catch (MalformedURLException localMalformedURLException)
      {
        SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 136);
        localSQLException2.fillInStackTrace();
        throw localSQLException2;
      }

    }

    SQLException localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4);
    localSQLException1.fillInStackTrace();
    throw localSQLException1;
  }

  public URL getURL(String paramString)
    throws SQLException
  {
    return getURL(getColumnIndex(paramString));
  }

  public void updateRef(int paramInt, Ref paramRef)
    throws SQLException
  {
    updateObject(paramInt, paramRef);
  }

  public void updateRef(String paramString, Ref paramRef)
    throws SQLException
  {
    updateRef(getColumnIndex(paramString), paramRef);
  }

  public void updateBlob(int paramInt, Blob paramBlob)
    throws SQLException
  {
    updateObject(paramInt, paramBlob);
  }

  public void updateBlob(String paramString, Blob paramBlob)
    throws SQLException
  {
    updateBlob(getColumnIndex(paramString), paramBlob);
  }

  public void updateClob(int paramInt, Clob paramClob)
    throws SQLException
  {
    updateObject(paramInt, paramClob);
  }

  public void updateClob(String paramString, Clob paramClob)
    throws SQLException
  {
    updateClob(getColumnIndex(paramString), paramClob);
  }

  public void updateArray(int paramInt, Array paramArray)
    throws SQLException
  {
    updateObject(paramInt, paramArray);
  }

  public void updateArray(String paramString, Array paramArray)
    throws SQLException
  {
    updateArray(getColumnIndex(paramString), paramArray);
  }

  public int[] getKeyColumns()
    throws SQLException
  {
    return this.keyColumns;
  }

  public void setKeyColumns(int[] paramArrayOfInt)
    throws SQLException
  {
    int i = 0;

    if (this.rowsetMetaData != null)
    {
      i = this.rowsetMetaData.getColumnCount();
      if ((paramArrayOfInt == null) || (paramArrayOfInt.length > i))
      {
        SQLException localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 324);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

    }

    int j = paramArrayOfInt.length;
    this.keyColumns = new int[j];

    for (int k = 0; k < j; k++)
    {
      if ((paramArrayOfInt[k] <= 0) || (paramArrayOfInt[k] > i))
      {
        SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3, new StringBuilder().append("").append(paramArrayOfInt[k]).toString());
        localSQLException2.fillInStackTrace();
        throw localSQLException2;
      }

      this.keyColumns[k] = paramArrayOfInt[k];
    }
  }

  public int getPageSize()
  {
    return this.pageSize;
  }

  public void setPageSize(int paramInt)
    throws SQLException
  {
    if ((paramInt < 0) || ((this.maxRows > 0) && (paramInt > this.maxRows)))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 325);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.pageSize = paramInt;
  }

  public SyncProvider getSyncProvider()
    throws SQLException
  {
    return this.syncProvider;
  }

  public void setSyncProvider(String paramString)
    throws SQLException
  {
    this.syncProvider = SyncFactory.getInstance(paramString);
    this.reader = this.syncProvider.getRowSetReader();
    this.writer = this.syncProvider.getRowSetWriter();
  }

  public String getTableName()
    throws SQLException
  {
    return this.tableName;
  }

  public void setTableName(String paramString)
    throws SQLException
  {
    this.tableName = paramString;
  }

  public CachedRowSet createCopyNoConstraints()
    throws SQLException
  {
    OracleCachedRowSet localOracleCachedRowSet = (OracleCachedRowSet)createCopy();

    localOracleCachedRowSet.initializeProperties();

    localOracleCachedRowSet.listener = new Vector();
    try
    {
      localOracleCachedRowSet.unsetMatchColumn(localOracleCachedRowSet.getMatchColumnIndexes());
      localOracleCachedRowSet.unsetMatchColumn(localOracleCachedRowSet.getMatchColumnNames());
    }
    catch (SQLException localSQLException)
    {
    }

    return localOracleCachedRowSet;
  }

  public CachedRowSet createCopySchema()
    throws SQLException
  {
    OracleCachedRowSet localOracleCachedRowSet = (OracleCachedRowSet)createCopy();

    localOracleCachedRowSet.rows = null;
    localOracleCachedRowSet.rowCount = 0;
    localOracleCachedRowSet.currentPage = 0;

    return localOracleCachedRowSet;
  }

  public boolean columnUpdated(int paramInt)
    throws SQLException
  {
    if (this.insertRowFlag)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 326);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    return getCurrentRow().isColumnChanged(paramInt);
  }

  public boolean columnUpdated(String paramString)
    throws SQLException
  {
    return columnUpdated(getColumnIndex(paramString));
  }

  public synchronized void execute(Connection paramConnection)
    throws SQLException
  {
    this.connection = paramConnection;
    execute();
  }

  public void commit()
    throws SQLException
  {
    getConnectionInternal().commit();
  }

  public void rollback()
    throws SQLException
  {
    getConnectionInternal().rollback();
  }

  public void rollback(Savepoint paramSavepoint)
    throws SQLException
  {
    Connection localConnection = getConnectionInternal();
    boolean bool = localConnection.getAutoCommit();
    try
    {
      localConnection.setAutoCommit(false);
      localConnection.rollback(paramSavepoint);
    }
    finally {
      localConnection.setAutoCommit(bool);
    }
  }

  public void oracleRollback(OracleSavepoint paramOracleSavepoint)
    throws SQLException
  {
    ((OracleConnection)getConnectionInternal()).oracleRollback(paramOracleSavepoint);
  }

  public void setOriginalRow()
    throws SQLException
  {
    if (this.insertRowFlag)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 327);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    setOriginalRowInternal(this.presentRow);
  }

  public int size()
  {
    return this.rowCount;
  }

  public boolean nextPage()
    throws SQLException
  {
    SQLException localSQLException;
    if ((this.fetchDirection == 1001) && (this.resultSet != null) && (this.resultSet.getType() == 1003))
    {
      localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 328);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if ((this.rows.size() == 0) && (!this.isPopulateDone))
    {
      localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 329);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if (((isExecuteCalled()) || (this.isPopulateDone)) && (this.rowCount < this.pageSize)) {
      return false;
    }
    if (isExecuteCalled())
    {
      this.currentPage += 1;
      execute();
    }
    else
    {
      populate(this.resultSet);
      this.currentPage += 1;
    }

    return !this.isPopulateDone;
  }

  public boolean previousPage()
    throws SQLException
  {
    SQLException localSQLException;
    if ((this.resultSet != null) && (this.resultSet.getType() == 1003))
    {
      localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 328);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if ((this.rows.size() == 0) && (!this.isPopulateDone))
    {
      localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 329);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if (this.currentPage == 0) {
      return false;
    }

    if (this.fetchDirection == 1001)
    {
      this.resultSet.relative(this.pageSize * 2);
    }
    else
    {
      this.resultSet.relative(-2 * this.pageSize);
    }

    populate(this.resultSet);

    if (this.currentPage > 0)
    {
      this.currentPage -= 1;
    }

    return this.currentPage != 0;
  }

  public void rowSetPopulated(RowSetEvent paramRowSetEvent, int paramInt)
    throws SQLException
  {
    if ((paramInt <= 0) || (paramInt < this.fetchSize))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 330);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if (this.rowCount % paramInt == 0)
    {
      this.rowsetEvent = new RowSetEvent(this);
      notifyRowSetChanged();
    }
  }

  public RowSetWarning getRowSetWarnings()
    throws SQLException
  {
    return this.rowsetWarning;
  }

  public void populate(ResultSet paramResultSet, int paramInt)
    throws SQLException
  {
    SQLException localSQLException1;
    if (paramInt < 0)
    {
      localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 331);
      localSQLException1.fillInStackTrace();
      throw localSQLException1;
    }

    if (paramResultSet == null)
    {
      localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 332);
      localSQLException1.fillInStackTrace();
      throw localSQLException1;
    }

    int i = paramResultSet.getType();

    if (i == 1003)
    {
      int j = 0;
      int k = 0;
      while (j < paramInt)
      {
        if (!paramResultSet.next())
        {
          k = 1;
          break;
        }
        j++;
      }

      if ((j < paramInt) && (paramInt > 0) && (k != 0))
      {
        SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 333);
        localSQLException2.fillInStackTrace();
        throw localSQLException2;
      }

    }
    else if (paramInt == 0) {
      paramResultSet.beforeFirst();
    } else {
      paramResultSet.absolute(paramInt);
    }

    populate(paramResultSet);
  }

  public void undoDelete()
    throws SQLException
  {
    cancelRowDelete();
  }

  public void undoInsert()
    throws SQLException
  {
    cancelRowInsert();
  }

  public void undoUpdate()
    throws SQLException
  {
    cancelRowUpdates();
  }

  public Reader getNCharacterStream(int paramInt)
    throws SQLException
  {
    return getCharacterStream(paramInt);
  }

  public NClob getNClob(int paramInt)
    throws SQLException
  {
    Object localObject = getObject(paramInt);

    if ((localObject == null) || ((localObject instanceof OracleSerialClob))) {
      return (NClob)localObject;
    }

    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public String getNString(int paramInt)
    throws SQLException
  {
    return getString(paramInt);
  }

  public RowId getRowId(int paramInt)
    throws SQLException
  {
    Object localObject = getObject(paramInt);

    if ((localObject instanceof RowId)) {
      return localObject == null ? null : (RowId)localObject;
    }

    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public SQLXML getSQLXML(int paramInt)
    throws SQLException
  {
    Object localObject = getObject(paramInt);
    if ((localObject == null) || ((localObject instanceof SQLXML))) {
      return (SQLXML)localObject;
    }

    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public Reader getNCharacterStream(String paramString)
    throws SQLException
  {
    return getNCharacterStream(getColumnIndex(paramString));
  }

  public NClob getNClob(String paramString)
    throws SQLException
  {
    return getNClob(getColumnIndex(paramString));
  }

  public String getNString(String paramString)
    throws SQLException
  {
    return getNString(getColumnIndex(paramString));
  }

  public RowId getRowId(String paramString)
    throws SQLException
  {
    return getRowId(getColumnIndex(paramString));
  }

  public SQLXML getSQLXML(String paramString)
    throws SQLException
  {
    return getSQLXML(getColumnIndex(paramString));
  }

  public void setAsciiStream(int paramInt, InputStream paramInputStream)
    throws SQLException
  {
    checkParamIndex(paramInt);
    Object[] arrayOfObject = { paramInputStream, Integer.valueOf(7) };

    this.param.add(paramInt - 1, arrayOfObject);
  }

  public void setAsciiStream(int paramInt, InputStream paramInputStream, long paramLong)
    throws SQLException
  {
    checkParamIndex(paramInt);
    Object[] arrayOfObject = { paramInputStream, Long.valueOf(paramLong), Integer.valueOf(8) };

    this.param.add(paramInt - 1, arrayOfObject);
  }

  public void setBinaryStream(int paramInt, InputStream paramInputStream)
    throws SQLException
  {
    checkParamIndex(paramInt);
    Object[] arrayOfObject = { paramInputStream, Integer.valueOf(5) };

    this.param.add(paramInt - 1, arrayOfObject);
  }

  public void setBinaryStream(int paramInt, InputStream paramInputStream, long paramLong)
    throws SQLException
  {
    checkParamIndex(paramInt);
    Object[] arrayOfObject = { paramInputStream, Long.valueOf(paramLong), Integer.valueOf(6) };

    this.param.add(paramInt - 1, arrayOfObject);
  }

  public void setBlob(int paramInt, InputStream paramInputStream)
    throws SQLException
  {
    checkParamIndex(paramInt);
    Object[] arrayOfObject = { paramInputStream, Integer.valueOf(13) };

    this.param.add(paramInt - 1, arrayOfObject);
  }

  public void setBlob(int paramInt, InputStream paramInputStream, long paramLong)
    throws SQLException
  {
    checkParamIndex(paramInt);
    Object[] arrayOfObject = { paramInputStream, Long.valueOf(paramLong), Integer.valueOf(14) };

    this.param.add(paramInt - 1, arrayOfObject);
  }

  public void setCharacterStream(int paramInt, Reader paramReader)
    throws SQLException
  {
    checkParamIndex(paramInt);
    Object[] arrayOfObject = { paramReader, Integer.valueOf(9) };

    this.param.add(paramInt - 1, arrayOfObject);
  }

  public void setCharacterStream(int paramInt, Reader paramReader, long paramLong)
    throws SQLException
  {
    checkParamIndex(paramInt);
    Object[] arrayOfObject = { paramReader, Long.valueOf(paramLong), Integer.valueOf(10) };

    this.param.add(paramInt - 1, arrayOfObject);
  }

  public void setClob(int paramInt, Reader paramReader)
    throws SQLException
  {
    checkParamIndex(paramInt);
    Object[] arrayOfObject = { paramReader, Integer.valueOf(15) };

    this.param.add(paramInt - 1, arrayOfObject);
  }

  public void setClob(int paramInt, Reader paramReader, long paramLong)
    throws SQLException
  {
    checkParamIndex(paramInt);
    Object[] arrayOfObject = { this.reader, Long.valueOf(paramLong), Integer.valueOf(16) };

    this.param.add(paramInt - 1, arrayOfObject);
  }

  public void setNCharacterStream(int paramInt, Reader paramReader)
    throws SQLException
  {
    checkParamIndex(paramInt);
    Object[] arrayOfObject = { paramReader, Integer.valueOf(11) };

    this.param.add(paramInt - 1, arrayOfObject);
  }

  public void setNCharacterStream(int paramInt, Reader paramReader, long paramLong)
    throws SQLException
  {
    checkParamIndex(paramInt);
    Object[] arrayOfObject = { paramReader, Long.valueOf(paramLong), Integer.valueOf(12) };

    this.param.add(paramInt - 1, arrayOfObject);
  }

  public void setNClob(int paramInt, NClob paramNClob)
    throws SQLException
  {
    checkParamIndex(paramInt);
    this.param.add(paramInt - 1, paramNClob);
  }

  public void setNClob(int paramInt, Reader paramReader)
    throws SQLException
  {
    checkParamIndex(paramInt);
    Object[] arrayOfObject = { this.reader, Integer.valueOf(17) };

    this.param.add(paramInt - 1, arrayOfObject);
  }

  public void setNClob(int paramInt, Reader paramReader, long paramLong)
    throws SQLException
  {
    checkParamIndex(paramInt);
    Object[] arrayOfObject = { this.reader, Long.valueOf(paramLong), Integer.valueOf(18) };

    this.param.add(paramInt - 1, arrayOfObject);
  }

  public void setNString(int paramInt, String paramString)
    throws SQLException
  {
    checkParamIndex(paramInt);
    this.param.add(paramInt - 1, paramString);
  }

  public void setRowId(int paramInt, RowId paramRowId)
    throws SQLException
  {
    checkParamIndex(paramInt);
    this.param.add(paramInt - 1, paramRowId);
  }

  public void setSQLXML(int paramInt, SQLXML paramSQLXML)
    throws SQLException
  {
    checkParamIndex(paramInt);
    this.param.add(paramInt - 1, paramSQLXML);
  }

  public void setArray(String paramString, Array paramArray)
    throws SQLException
  {
    String str = paramString.intern();
    String[] arrayOfString = this.osql.getParameterList();
    int i = 0;
    int j = Math.min(this.osql.getParameterCount(), arrayOfString.length);

    for (int k = 0; k < j; k++) {
      if (arrayOfString[k] == str)
      {
        setArray(k + 1, paramArray);

        i = 1;
      }
    }
    if (i == 0)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 147, paramString);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  public void setBigDecimal(String paramString, BigDecimal paramBigDecimal)
    throws SQLException
  {
    String str = paramString.intern();
    String[] arrayOfString = this.osql.getParameterList();
    int i = 0;
    int j = Math.min(this.osql.getParameterCount(), arrayOfString.length);

    for (int k = 0; k < j; k++) {
      if (arrayOfString[k] == str)
      {
        setBigDecimal(k + 1, paramBigDecimal);

        i = 1;
      }
    }
    if (i == 0)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 147, paramString);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  public void setBlob(String paramString, Blob paramBlob)
    throws SQLException
  {
    String str = paramString.intern();
    String[] arrayOfString = this.osql.getParameterList();
    int i = 0;
    int j = Math.min(this.osql.getParameterCount(), arrayOfString.length);

    for (int k = 0; k < j; k++) {
      if (arrayOfString[k] == str)
      {
        setBlob(k + 1, paramBlob);

        i = 1;
      }
    }
    if (i == 0)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 147, paramString);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  public void setBoolean(String paramString, boolean paramBoolean)
    throws SQLException
  {
    String str = paramString.intern();
    String[] arrayOfString = this.osql.getParameterList();
    int i = 0;
    int j = Math.min(this.osql.getParameterCount(), arrayOfString.length);

    for (int k = 0; k < j; k++) {
      if (arrayOfString[k] == str)
      {
        setBoolean(k + 1, paramBoolean);

        i = 1;
      }
    }
    if (i == 0)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 147, paramString);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  public void setByte(String paramString, byte paramByte)
    throws SQLException
  {
    String str = paramString.intern();
    String[] arrayOfString = this.osql.getParameterList();
    int i = 0;
    int j = Math.min(this.osql.getParameterCount(), arrayOfString.length);

    for (int k = 0; k < j; k++) {
      if (arrayOfString[k] == str)
      {
        setByte(k + 1, paramByte);

        i = 1;
      }
    }
    if (i == 0)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 147, paramString);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  public void setBytes(String paramString, byte[] paramArrayOfByte)
    throws SQLException
  {
    String str = paramString.intern();
    String[] arrayOfString = this.osql.getParameterList();
    int i = 0;
    int j = Math.min(this.osql.getParameterCount(), arrayOfString.length);

    for (int k = 0; k < j; k++) {
      if (arrayOfString[k] == str)
      {
        setBytes(k + 1, paramArrayOfByte);

        i = 1;
      }
    }
    if (i == 0)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 147, paramString);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  public void setClob(String paramString, Clob paramClob)
    throws SQLException
  {
    String str = paramString.intern();
    String[] arrayOfString = this.osql.getParameterList();
    int i = 0;
    int j = Math.min(this.osql.getParameterCount(), arrayOfString.length);

    for (int k = 0; k < j; k++) {
      if (arrayOfString[k] == str)
      {
        setClob(k + 1, paramClob);

        i = 1;
      }
    }
    if (i == 0)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 147, paramString);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  public void setDate(String paramString, java.sql.Date paramDate)
    throws SQLException
  {
    String str = paramString.intern();
    String[] arrayOfString = this.osql.getParameterList();
    int i = 0;
    int j = Math.min(this.osql.getParameterCount(), arrayOfString.length);

    for (int k = 0; k < j; k++) {
      if (arrayOfString[k] == str)
      {
        setDate(k + 1, paramDate);

        i = 1;
      }
    }
    if (i == 0)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 147, paramString);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  public void setDate(String paramString, java.sql.Date paramDate, Calendar paramCalendar)
    throws SQLException
  {
    String str = paramString.intern();
    String[] arrayOfString = this.osql.getParameterList();
    int i = 0;
    int j = Math.min(this.osql.getParameterCount(), arrayOfString.length);

    for (int k = 0; k < j; k++) {
      if (arrayOfString[k] == str)
      {
        setDate(k + 1, paramDate, paramCalendar);

        i = 1;
      }
    }
    if (i == 0)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 147, paramString);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  public void setDouble(String paramString, double paramDouble)
    throws SQLException
  {
    String str = paramString.intern();
    String[] arrayOfString = this.osql.getParameterList();
    int i = 0;
    int j = Math.min(this.osql.getParameterCount(), arrayOfString.length);

    for (int k = 0; k < j; k++) {
      if (arrayOfString[k] == str)
      {
        setDouble(k + 1, paramDouble);

        i = 1;
      }
    }
    if (i == 0)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 147, paramString);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  public void setFloat(String paramString, float paramFloat)
    throws SQLException
  {
    String str = paramString.intern();
    String[] arrayOfString = this.osql.getParameterList();
    int i = 0;
    int j = Math.min(this.osql.getParameterCount(), arrayOfString.length);

    for (int k = 0; k < j; k++) {
      if (arrayOfString[k] == str)
      {
        setFloat(k + 1, paramFloat);

        i = 1;
      }
    }
    if (i == 0)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 147, paramString);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  public void setInt(String paramString, int paramInt)
    throws SQLException
  {
    String str = paramString.intern();
    String[] arrayOfString = this.osql.getParameterList();
    int i = 0;
    int j = Math.min(this.osql.getParameterCount(), arrayOfString.length);

    for (int k = 0; k < j; k++) {
      if (arrayOfString[k] == str)
      {
        setInt(k + 1, paramInt);

        i = 1;
      }
    }
    if (i == 0)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 147, paramString);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  public void setLong(String paramString, long paramLong)
    throws SQLException
  {
    String str = paramString.intern();
    String[] arrayOfString = this.osql.getParameterList();
    int i = 0;
    int j = Math.min(this.osql.getParameterCount(), arrayOfString.length);

    for (int k = 0; k < j; k++) {
      if (arrayOfString[k] == str)
      {
        setLong(k + 1, paramLong);

        i = 1;
      }
    }
    if (i == 0)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 147, paramString);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  public void setNClob(String paramString, NClob paramNClob)
    throws SQLException
  {
    String str = paramString.intern();
    String[] arrayOfString = this.osql.getParameterList();
    int i = 0;
    int j = Math.min(this.osql.getParameterCount(), arrayOfString.length);

    for (int k = 0; k < j; k++) {
      if (arrayOfString[k] == str)
      {
        setNClob(k + 1, paramNClob);

        i = 1;
      }
    }
    if (i == 0)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 147, paramString);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  public void setNString(String paramString1, String paramString2)
    throws SQLException
  {
    String str = paramString1.intern();
    String[] arrayOfString = this.osql.getParameterList();
    int i = 0;
    int j = Math.min(this.osql.getParameterCount(), arrayOfString.length);

    for (int k = 0; k < j; k++) {
      if (arrayOfString[k] == str)
      {
        setNString(k + 1, paramString2);

        i = 1;
      }
    }
    if (i == 0)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 147, paramString1);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  public void setObject(String paramString, Object paramObject)
    throws SQLException
  {
    String str = paramString.intern();
    String[] arrayOfString = this.osql.getParameterList();
    int i = 0;
    int j = Math.min(this.osql.getParameterCount(), arrayOfString.length);

    for (int k = 0; k < j; k++) {
      if (arrayOfString[k] == str)
      {
        setObject(k + 1, paramObject);

        i = 1;
      }
    }
    if (i == 0)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 147, paramString);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  public void setObject(String paramString, Object paramObject, int paramInt)
    throws SQLException
  {
    String str = paramString.intern();
    String[] arrayOfString = this.osql.getParameterList();
    int i = 0;
    int j = Math.min(this.osql.getParameterCount(), arrayOfString.length);

    for (int k = 0; k < j; k++) {
      if (arrayOfString[k] == str)
      {
        setObject(k + 1, paramObject, paramInt);

        i = 1;
      }
    }
    if (i == 0)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 147, paramString);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  public void setRef(String paramString, Ref paramRef)
    throws SQLException
  {
    String str = paramString.intern();
    String[] arrayOfString = this.osql.getParameterList();
    int i = 0;
    int j = Math.min(this.osql.getParameterCount(), arrayOfString.length);

    for (int k = 0; k < j; k++) {
      if (arrayOfString[k] == str)
      {
        setRef(k + 1, paramRef);

        i = 1;
      }
    }
    if (i == 0)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 147, paramString);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  public void setRowId(String paramString, RowId paramRowId)
    throws SQLException
  {
    String str = paramString.intern();
    String[] arrayOfString = this.osql.getParameterList();
    int i = 0;
    int j = Math.min(this.osql.getParameterCount(), arrayOfString.length);

    for (int k = 0; k < j; k++) {
      if (arrayOfString[k] == str)
      {
        setRowId(k + 1, paramRowId);

        i = 1;
      }
    }
    if (i == 0)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 147, paramString);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  public void setShort(String paramString, short paramShort)
    throws SQLException
  {
    String str = paramString.intern();
    String[] arrayOfString = this.osql.getParameterList();
    int i = 0;
    int j = Math.min(this.osql.getParameterCount(), arrayOfString.length);

    for (int k = 0; k < j; k++) {
      if (arrayOfString[k] == str)
      {
        setShort(k + 1, paramShort);

        i = 1;
      }
    }
    if (i == 0)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 147, paramString);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  public void setSQLXML(String paramString, SQLXML paramSQLXML)
    throws SQLException
  {
    String str = paramString.intern();
    String[] arrayOfString = this.osql.getParameterList();
    int i = 0;
    int j = Math.min(this.osql.getParameterCount(), arrayOfString.length);

    for (int k = 0; k < j; k++) {
      if (arrayOfString[k] == str)
      {
        setSQLXML(k + 1, paramSQLXML);

        i = 1;
      }
    }
    if (i == 0)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 147, paramString);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  public void setString(String paramString1, String paramString2)
    throws SQLException
  {
    String str = paramString1.intern();
    String[] arrayOfString = this.osql.getParameterList();
    int i = 0;
    int j = Math.min(this.osql.getParameterCount(), arrayOfString.length);

    for (int k = 0; k < j; k++) {
      if (arrayOfString[k] == str)
      {
        setString(k + 1, paramString2);

        i = 1;
      }
    }
    if (i == 0)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 147, paramString1);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  public void setTime(String paramString, Time paramTime)
    throws SQLException
  {
    String str = paramString.intern();
    String[] arrayOfString = this.osql.getParameterList();
    int i = 0;
    int j = Math.min(this.osql.getParameterCount(), arrayOfString.length);

    for (int k = 0; k < j; k++) {
      if (arrayOfString[k] == str)
      {
        setTime(k + 1, paramTime);

        i = 1;
      }
    }
    if (i == 0)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 147, paramString);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  public void setTime(String paramString, Time paramTime, Calendar paramCalendar)
    throws SQLException
  {
    String str = paramString.intern();
    String[] arrayOfString = this.osql.getParameterList();
    int i = 0;
    int j = Math.min(this.osql.getParameterCount(), arrayOfString.length);

    for (int k = 0; k < j; k++) {
      if (arrayOfString[k] == str)
      {
        setTime(k + 1, paramTime, paramCalendar);

        i = 1;
      }
    }
    if (i == 0)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 147, paramString);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  public void setTimestamp(String paramString, Timestamp paramTimestamp)
    throws SQLException
  {
    String str = paramString.intern();
    String[] arrayOfString = this.osql.getParameterList();
    int i = 0;
    int j = Math.min(this.osql.getParameterCount(), arrayOfString.length);

    for (int k = 0; k < j; k++) {
      if (arrayOfString[k] == str)
      {
        setTimestamp(k + 1, paramTimestamp);

        i = 1;
      }
    }
    if (i == 0)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 147, paramString);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  public void setTimestamp(String paramString, Timestamp paramTimestamp, Calendar paramCalendar)
    throws SQLException
  {
    String str = paramString.intern();
    String[] arrayOfString = this.osql.getParameterList();
    int i = 0;
    int j = Math.min(this.osql.getParameterCount(), arrayOfString.length);

    for (int k = 0; k < j; k++) {
      if (arrayOfString[k] == str)
      {
        setTimestamp(k + 1, paramTimestamp, paramCalendar);

        i = 1;
      }
    }
    if (i == 0)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 147, paramString);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  public void setURL(String paramString, URL paramURL)
    throws SQLException
  {
    String str = paramString.intern();
    String[] arrayOfString = this.osql.getParameterList();
    int i = 0;
    int j = Math.min(this.osql.getParameterCount(), arrayOfString.length);

    for (int k = 0; k < j; k++) {
      if (arrayOfString[k] == str)
      {
        setURL(k + 1, paramURL);

        i = 1;
      }
    }
    if (i == 0)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 147, paramString);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  public void setBlob(String paramString, InputStream paramInputStream)
    throws SQLException
  {
    String str = paramString.intern();
    String[] arrayOfString = this.osql.getParameterList();
    int i = 0;
    int j = Math.min(this.osql.getParameterCount(), arrayOfString.length);

    for (int k = 0; k < j; k++) {
      if (arrayOfString[k] == str)
      {
        setBlob(k + 1, paramInputStream);

        i = 1;
      }
    }
    if (i == 0)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 147, paramString);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  public void setBlob(String paramString, InputStream paramInputStream, long paramLong)
    throws SQLException
  {
    String str = paramString.intern();
    String[] arrayOfString = this.osql.getParameterList();
    int i = 0;
    int j = Math.min(this.osql.getParameterCount(), arrayOfString.length);

    for (int k = 0; k < j; k++) {
      if (arrayOfString[k] == str)
      {
        setBlob(k + 1, paramInputStream, paramLong);

        i = 1;
      }
    }
    if (i == 0)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 147, paramString);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  public void setClob(String paramString, Reader paramReader)
    throws SQLException
  {
    String str = paramString.intern();
    String[] arrayOfString = this.osql.getParameterList();
    int i = 0;
    int j = Math.min(this.osql.getParameterCount(), arrayOfString.length);

    for (int k = 0; k < j; k++) {
      if (arrayOfString[k] == str)
      {
        setClob(k + 1, paramReader);

        i = 1;
      }
    }
    if (i == 0)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 147, paramString);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  public void setClob(String paramString, Reader paramReader, long paramLong)
    throws SQLException
  {
    String str = paramString.intern();
    String[] arrayOfString = this.osql.getParameterList();
    int i = 0;
    int j = Math.min(this.osql.getParameterCount(), arrayOfString.length);

    for (int k = 0; k < j; k++) {
      if (arrayOfString[k] == str)
      {
        setClob(k + 1, paramReader, paramLong);

        i = 1;
      }
    }
    if (i == 0)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 147, paramString);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  public void setNClob(String paramString, Reader paramReader)
    throws SQLException
  {
    String str = paramString.intern();
    String[] arrayOfString = this.osql.getParameterList();
    int i = 0;
    int j = Math.min(this.osql.getParameterCount(), arrayOfString.length);

    for (int k = 0; k < j; k++) {
      if (arrayOfString[k] == str)
      {
        setNClob(k + 1, paramReader);

        i = 1;
      }
    }
    if (i == 0)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 147, paramString);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  public void setNClob(String paramString, Reader paramReader, long paramLong)
    throws SQLException
  {
    String str = paramString.intern();
    String[] arrayOfString = this.osql.getParameterList();
    int i = 0;
    int j = Math.min(this.osql.getParameterCount(), arrayOfString.length);

    for (int k = 0; k < j; k++) {
      if (arrayOfString[k] == str)
      {
        setNClob(k + 1, paramReader, paramLong);

        i = 1;
      }
    }
    if (i == 0)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 147, paramString);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  public void setAsciiStream(String paramString, InputStream paramInputStream)
    throws SQLException
  {
    String str = paramString.intern();
    String[] arrayOfString = this.osql.getParameterList();
    int i = 0;
    int j = Math.min(this.osql.getParameterCount(), arrayOfString.length);

    for (int k = 0; k < j; k++) {
      if (arrayOfString[k] == str)
      {
        setAsciiStream(k + 1, paramInputStream);

        i = 1;
      }
    }
    if (i == 0)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 147, paramString);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  public void setAsciiStream(String paramString, InputStream paramInputStream, int paramInt)
    throws SQLException
  {
    String str = paramString.intern();
    String[] arrayOfString = this.osql.getParameterList();
    int i = 0;
    int j = Math.min(this.osql.getParameterCount(), arrayOfString.length);

    for (int k = 0; k < j; k++) {
      if (arrayOfString[k] == str)
      {
        setAsciiStream(k + 1, paramInputStream, paramInt);

        i = 1;
      }
    }
    if (i == 0)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 147, paramString);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  public void setAsciiStream(String paramString, InputStream paramInputStream, long paramLong)
    throws SQLException
  {
    String str = paramString.intern();
    String[] arrayOfString = this.osql.getParameterList();
    int i = 0;
    int j = Math.min(this.osql.getParameterCount(), arrayOfString.length);

    for (int k = 0; k < j; k++) {
      if (arrayOfString[k] == str)
      {
        setAsciiStream(k + 1, paramInputStream, paramLong);

        i = 1;
      }
    }
    if (i == 0)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 147, paramString);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  public void setBinaryStream(String paramString, InputStream paramInputStream)
    throws SQLException
  {
    String str = paramString.intern();
    String[] arrayOfString = this.osql.getParameterList();
    int i = 0;
    int j = Math.min(this.osql.getParameterCount(), arrayOfString.length);

    for (int k = 0; k < j; k++) {
      if (arrayOfString[k] == str)
      {
        setBinaryStream(k + 1, paramInputStream);

        i = 1;
      }
    }
    if (i == 0)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 147, paramString);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  public void setBinaryStream(String paramString, InputStream paramInputStream, int paramInt)
    throws SQLException
  {
    String str = paramString.intern();
    String[] arrayOfString = this.osql.getParameterList();
    int i = 0;
    int j = Math.min(this.osql.getParameterCount(), arrayOfString.length);

    for (int k = 0; k < j; k++) {
      if (arrayOfString[k] == str)
      {
        setBinaryStream(k + 1, paramInputStream, paramInt);

        i = 1;
      }
    }
    if (i == 0)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 147, paramString);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  public void setBinaryStream(String paramString, InputStream paramInputStream, long paramLong)
    throws SQLException
  {
    String str = paramString.intern();
    String[] arrayOfString = this.osql.getParameterList();
    int i = 0;
    int j = Math.min(this.osql.getParameterCount(), arrayOfString.length);

    for (int k = 0; k < j; k++) {
      if (arrayOfString[k] == str)
      {
        setBinaryStream(k + 1, paramInputStream, paramLong);

        i = 1;
      }
    }
    if (i == 0)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 147, paramString);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  public void setCharacterStream(String paramString, Reader paramReader)
    throws SQLException
  {
    String str = paramString.intern();
    String[] arrayOfString = this.osql.getParameterList();
    int i = 0;
    int j = Math.min(this.osql.getParameterCount(), arrayOfString.length);

    for (int k = 0; k < j; k++) {
      if (arrayOfString[k] == str)
      {
        setCharacterStream(k + 1, paramReader);

        i = 1;
      }
    }
    if (i == 0)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 147, paramString);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  public void setCharacterStream(String paramString, Reader paramReader, int paramInt)
    throws SQLException
  {
    String str = paramString.intern();
    String[] arrayOfString = this.osql.getParameterList();
    int i = 0;
    int j = Math.min(this.osql.getParameterCount(), arrayOfString.length);

    for (int k = 0; k < j; k++) {
      if (arrayOfString[k] == str)
      {
        setCharacterStream(k + 1, paramReader, paramInt);

        i = 1;
      }
    }
    if (i == 0)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 147, paramString);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  public void setCharacterStream(String paramString, Reader paramReader, long paramLong)
    throws SQLException
  {
    String str = paramString.intern();
    String[] arrayOfString = this.osql.getParameterList();
    int i = 0;
    int j = Math.min(this.osql.getParameterCount(), arrayOfString.length);

    for (int k = 0; k < j; k++) {
      if (arrayOfString[k] == str)
      {
        setCharacterStream(k + 1, paramReader, paramLong);

        i = 1;
      }
    }
    if (i == 0)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 147, paramString);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  public void setNCharacterStream(String paramString, Reader paramReader)
    throws SQLException
  {
    String str = paramString.intern();
    String[] arrayOfString = this.osql.getParameterList();
    int i = 0;
    int j = Math.min(this.osql.getParameterCount(), arrayOfString.length);

    for (int k = 0; k < j; k++) {
      if (arrayOfString[k] == str)
      {
        setNCharacterStream(k + 1, paramReader);

        i = 1;
      }
    }
    if (i == 0)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 147, paramString);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  public void setNCharacterStream(String paramString, Reader paramReader, long paramLong)
    throws SQLException
  {
    String str = paramString.intern();
    String[] arrayOfString = this.osql.getParameterList();
    int i = 0;
    int j = Math.min(this.osql.getParameterCount(), arrayOfString.length);

    for (int k = 0; k < j; k++) {
      if (arrayOfString[k] == str)
      {
        setNCharacterStream(k + 1, paramReader, paramLong);

        i = 1;
      }
    }
    if (i == 0)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 147, paramString);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  public void setUnicodeStream(String paramString, InputStream paramInputStream, int paramInt)
    throws SQLException
  {
    String str = paramString.intern();
    String[] arrayOfString = this.osql.getParameterList();
    int i = 0;
    int j = Math.min(this.osql.getParameterCount(), arrayOfString.length);

    for (int k = 0; k < j; k++) {
      if (arrayOfString[k] == str)
      {
        setUnicodeStream(k + 1, paramInputStream, paramInt);

        i = 1;
      }
    }
    if (i == 0)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 147, paramString);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  public void setNull(String paramString, int paramInt)
    throws SQLException
  {
    String str = paramString.intern();
    String[] arrayOfString = this.osql.getParameterList();
    int i = 0;
    int j = Math.min(this.osql.getParameterCount(), arrayOfString.length);

    for (int k = 0; k < j; k++) {
      if (arrayOfString[k] == str)
      {
        setNull(k + 1, paramInt);

        i = 1;
      }
    }
    if (i == 0)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 147, paramString);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  public void setNull(String paramString1, int paramInt, String paramString2)
    throws SQLException
  {
    String str = paramString1.intern();
    String[] arrayOfString = this.osql.getParameterList();
    int i = 0;
    int j = Math.min(this.osql.getParameterCount(), arrayOfString.length);

    for (int k = 0; k < j; k++) {
      if (arrayOfString[k] == str)
      {
        setNull(k + 1, paramInt, paramString2);

        i = 1;
      }
    }
    if (i == 0)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 147, paramString1);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  public void setObject(String paramString, Object paramObject, int paramInt1, int paramInt2)
    throws SQLException
  {
    String str = paramString.intern();
    String[] arrayOfString = this.osql.getParameterList();
    int i = 0;
    int j = Math.min(this.osql.getParameterCount(), arrayOfString.length);

    for (int k = 0; k < j; k++) {
      if (arrayOfString[k] == str)
      {
        setObject(k + 1, paramObject, paramInt1, paramInt2);

        i = 1;
      }
    }
    if (i == 0)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 147, paramString);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  public void updateAsciiStream(int paramInt, InputStream paramInputStream)
    throws SQLException
  {
    updateAsciiStream(paramInt, paramInputStream, 2147483647);
  }

  public void updateAsciiStream(int paramInt, InputStream paramInputStream, long paramLong)
    throws SQLException
  {
    updateAsciiStream(paramInt, paramInputStream, (int)paramLong);
  }

  public void updateAsciiStream(String paramString, InputStream paramInputStream)
    throws SQLException
  {
    updateAsciiStream(getColumnIndex(paramString), paramInputStream);
  }

  public void updateAsciiStream(String paramString, InputStream paramInputStream, long paramLong)
    throws SQLException
  {
    updateAsciiStream(getColumnIndex(paramString), paramInputStream, paramLong);
  }

  public void updateBinaryStream(int paramInt, InputStream paramInputStream)
    throws SQLException
  {
    updateBinaryStream(paramInt, paramInputStream, 2147483647);
  }

  public void updateBinaryStream(int paramInt, InputStream paramInputStream, long paramLong)
    throws SQLException
  {
    updateBinaryStream(paramInt, paramInputStream, (int)paramLong);
  }

  public void updateBinaryStream(String paramString, InputStream paramInputStream)
    throws SQLException
  {
    updateBinaryStream(getColumnIndex(paramString), paramInputStream);
  }

  public void updateBinaryStream(String paramString, InputStream paramInputStream, long paramLong)
    throws SQLException
  {
    updateBinaryStream(getColumnIndex(paramString), paramInputStream, paramLong);
  }

  public void updateBlob(int paramInt, InputStream paramInputStream)
    throws SQLException
  {
    updateBinaryStream(paramInt, paramInputStream);
  }

  public void updateBlob(int paramInt, InputStream paramInputStream, long paramLong)
    throws SQLException
  {
    updateBinaryStream(paramInt, paramInputStream, paramLong);
  }

  public void updateBlob(String paramString, InputStream paramInputStream)
    throws SQLException
  {
    updateBlob(getColumnIndex(paramString), paramInputStream);
  }

  public void updateBlob(String paramString, InputStream paramInputStream, long paramLong)
    throws SQLException
  {
    updateBlob(getColumnIndex(paramString), paramInputStream, paramLong);
  }

  public void updateCharacterStream(int paramInt, Reader paramReader)
    throws SQLException
  {
    updateCharacterStream(paramInt, paramReader, 2147483647);
  }

  public void updateCharacterStream(int paramInt, Reader paramReader, long paramLong)
    throws SQLException
  {
    updateCharacterStream(paramInt, paramReader, (int)paramLong);
  }

  public void updateCharacterStream(String paramString, Reader paramReader)
    throws SQLException
  {
    updateCharacterStream(getColumnIndex(paramString), paramReader);
  }

  public void updateCharacterStream(String paramString, Reader paramReader, long paramLong)
    throws SQLException
  {
    updateCharacterStream(getColumnIndex(paramString), paramReader, paramLong);
  }

  public void updateClob(int paramInt, Reader paramReader)
    throws SQLException
  {
    updateCharacterStream(paramInt, paramReader);
  }

  public void updateClob(int paramInt, Reader paramReader, long paramLong)
    throws SQLException
  {
    updateCharacterStream(paramInt, paramReader, paramLong);
  }

  public void updateClob(String paramString, Reader paramReader)
    throws SQLException
  {
    updateClob(getColumnIndex(paramString), paramReader);
  }

  public void updateClob(String paramString, Reader paramReader, long paramLong)
    throws SQLException
  {
    updateClob(getColumnIndex(paramString), paramReader, paramLong);
  }

  public void updateNCharacterStream(int paramInt, Reader paramReader)
    throws SQLException
  {
    updateNCharacterStream(paramInt, paramReader, 2147483647L);
  }

  public void updateNCharacterStream(int paramInt, Reader paramReader, long paramLong)
    throws SQLException
  {
    updateCharacterStreamInternal(paramInt, paramReader, (int)paramLong, true);
  }

  public void updateNCharacterStream(String paramString, Reader paramReader)
    throws SQLException
  {
    updateNCharacterStream(getColumnIndex(paramString), paramReader);
  }

  public void updateNCharacterStream(String paramString, Reader paramReader, long paramLong)
    throws SQLException
  {
    updateNCharacterStream(getColumnIndex(paramString), paramReader, paramLong);
  }

  public void updateNClob(int paramInt, NClob paramNClob)
    throws SQLException
  {
    updateObject(paramInt, new OracleSerialClob(paramNClob));
  }

  public void updateNClob(int paramInt, Reader paramReader)
    throws SQLException
  {
    updateClob(paramInt, new OracleSerialClob(paramReader));
  }

  public void updateNClob(int paramInt, Reader paramReader, long paramLong)
    throws SQLException
  {
    updateClob(paramInt, new OracleSerialClob(paramReader, paramLong));
  }

  public void updateNClob(String paramString, NClob paramNClob)
    throws SQLException
  {
    updateNClob(getColumnIndex(paramString), paramNClob);
  }

  public void updateNClob(String paramString, Reader paramReader)
    throws SQLException
  {
    updateNClob(getColumnIndex(paramString), paramReader);
  }

  public void updateNClob(String paramString, Reader paramReader, long paramLong)
    throws SQLException
  {
    updateNClob(getColumnIndex(paramString), paramReader, paramLong);
  }

  public void updateNString(int paramInt, String paramString)
    throws SQLException
  {
    updateObject(paramInt, paramString);
  }

  public void updateNString(String paramString1, String paramString2)
    throws SQLException
  {
    updateNString(getColumnIndex(paramString1), paramString2);
  }

  public void updateRowId(int paramInt, RowId paramRowId)
    throws SQLException
  {
    updateObject(paramInt, paramRowId);
  }

  public void updateRowId(String paramString, RowId paramRowId)
    throws SQLException
  {
    updateRowId(getColumnIndex(paramString), paramRowId);
  }

  public void updateSQLXML(int paramInt, SQLXML paramSQLXML)
    throws SQLException
  {
    updateObject(paramInt, paramSQLXML);
  }

  public void updateSQLXML(String paramString, SQLXML paramSQLXML)
    throws SQLException
  {
    updateSQLXML(getColumnIndex(paramString), paramSQLXML);
  }
}