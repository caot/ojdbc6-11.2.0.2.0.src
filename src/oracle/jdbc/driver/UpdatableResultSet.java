package oracle.jdbc.driver;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Map;
import oracle.jdbc.OracleResultSet.AuthorizationIndicator;
import oracle.jdbc.internal.OracleConnection;
import oracle.sql.ARRAY;
import oracle.sql.BFILE;
import oracle.sql.BLOB;
import oracle.sql.CHAR;
import oracle.sql.CLOB;
import oracle.sql.CustomDatum;
import oracle.sql.CustomDatumFactory;
import oracle.sql.DATE;
import oracle.sql.Datum;
import oracle.sql.INTERVALDS;
import oracle.sql.INTERVALYM;
import oracle.sql.NCLOB;
import oracle.sql.NUMBER;
import oracle.sql.OPAQUE;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.RAW;
import oracle.sql.REF;
import oracle.sql.ROWID;
import oracle.sql.STRUCT;
import oracle.sql.TIMESTAMP;
import oracle.sql.TIMESTAMPLTZ;
import oracle.sql.TIMESTAMPTZ;

class UpdatableResultSet extends BaseResultSet
{
  static final int concurrencyType = 1008;
  static final int BEGIN_COLUMN_INDEX = 1;
  static final int MAX_CHAR_BUFFER_SIZE = 1024;
  static final int MAX_BYTE_BUFFER_SIZE = 1024;
  PhysicalConnection connection;
  OracleResultSet resultSet;
  boolean isCachedRset;
  ScrollRsetStatement scrollStmt;
  ResultSetMetaData rsetMetaData;
  private int rsetType;
  private int columnCount;
  private OraclePreparedStatement deleteStmt;
  private OraclePreparedStatement insertStmt;
  private OraclePreparedStatement updateStmt;
  private int[] indexColsChanged;
  private Object[] rowBuffer;
  private boolean[] m_nullIndicator;
  private int[][] typeInfo;
  private boolean isInserting;
  private boolean isUpdating;
  private int wasNull;
  private static final int VALUE_NULL = 1;
  private static final int VALUE_NOT_NULL = 2;
  private static final int VALUE_UNKNOWN = 3;
  private static final int VALUE_IN_RSET = 4;
  private static final int ASCII_STREAM = 1;
  private static final int BINARY_STREAM = 2;
  private static final int UNICODE_STREAM = 3;
  private static int _MIN_STREAM_SIZE = 4000;

  ArrayList tempClobsToFree = null;
  ArrayList tempBlobsToFree = null;

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  UpdatableResultSet(ScrollRsetStatement paramScrollRsetStatement, ScrollableResultSet paramScrollableResultSet, int paramInt1, int paramInt2)
    throws SQLException
  {
    init(paramScrollRsetStatement, paramScrollableResultSet, paramInt1, paramInt2);

    paramScrollableResultSet.resetBeginColumnIndex();
    getInternalMetadata();

    this.isCachedRset = true;
  }

  UpdatableResultSet(ScrollRsetStatement paramScrollRsetStatement, OracleResultSetImpl paramOracleResultSetImpl, int paramInt1, int paramInt2)
    throws SQLException
  {
    init(paramScrollRsetStatement, paramOracleResultSetImpl, paramInt1, paramInt2);
    getInternalMetadata();

    this.isCachedRset = false;
  }

  private void init(ScrollRsetStatement paramScrollRsetStatement, OracleResultSet paramOracleResultSet, int paramInt1, int paramInt2)
    throws SQLException
  {
    if ((paramScrollRsetStatement == null) || (paramOracleResultSet == null) || (paramInt2 != 1008))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.connection = ((OracleStatement)paramScrollRsetStatement).connection;
    this.resultSet = paramOracleResultSet;
    this.scrollStmt = paramScrollRsetStatement;
    this.rsetType = paramInt1;
    this.deleteStmt = null;
    this.insertStmt = null;
    this.updateStmt = null;
    this.indexColsChanged = null;
    this.rowBuffer = null;
    this.m_nullIndicator = null;
    this.typeInfo = ((int[][])null);
    this.isInserting = false;
    this.isUpdating = false;
    this.wasNull = -1;
    this.rsetMetaData = null;
    this.columnCount = 0;
  }

  void ensureOpen()
    throws SQLException
  {
    SQLException localSQLException;
    if (this.closed)
    {
      localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 10);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if ((this.resultSet == null) || (this.scrollStmt == null) || (((OracleStatement)this.scrollStmt).closed))
    {
      localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  public void close()
    throws SQLException
  {
    if (this.closed) return;
    synchronized (this.connection)
    {
      super.close();

      if (this.resultSet != null)
        this.resultSet.close();
      if (this.insertStmt != null)
        this.insertStmt.close();
      if (this.updateStmt != null)
        this.updateStmt.close();
      if (this.deleteStmt != null)
        this.deleteStmt.close();
      if (this.scrollStmt != null) {
        this.scrollStmt.notifyCloseRset();
      }
      cancelRowInserts();

      this.connection = LogicalConnection.closedConnection;
      this.resultSet = null;
      this.scrollStmt = null;
      this.rsetMetaData = null;
      this.scrollStmt = null;
      this.deleteStmt = null;
      this.insertStmt = null;
      this.updateStmt = null;
      this.indexColsChanged = null;
      this.rowBuffer = null;
      this.m_nullIndicator = null;
      this.typeInfo = ((int[][])null);
    }
  }

  public boolean wasNull()
    throws SQLException
  {
    synchronized (this.connection)
    {
      ensureOpen();
      switch (this.wasNull)
      {
      case 1:
        return true;
      case 2:
        return false;
      case 4:
        return this.resultSet.wasNull();
      case 3:
      }

      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 24);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  int getFirstUserColumnIndex()
  {
    return 1;
  }

  public Statement getStatement()
    throws SQLException
  {
    synchronized (this.connection)
    {
      return (Statement)this.scrollStmt;
    }
  }

  public SQLWarning getWarnings()
    throws SQLException
  {
    SQLWarning localSQLWarning1 = this.resultSet.getWarnings();

    if (this.sqlWarning == null) {
      return localSQLWarning1;
    }

    SQLWarning localSQLWarning2 = this.sqlWarning;

    while (localSQLWarning2.getNextWarning() != null) {
      localSQLWarning2 = localSQLWarning2.getNextWarning();
    }
    localSQLWarning2.setNextWarning(localSQLWarning1);

    return this.sqlWarning;
  }

  public void clearWarnings()
    throws SQLException
  {
    this.sqlWarning = null;

    this.resultSet.clearWarnings();
  }

  public boolean next()
    throws SQLException
  {
    synchronized (this.connection)
    {
      ensureOpen();
      cancelRowChanges();

      return this.resultSet.next();
    }
  }

  public boolean isBeforeFirst()
    throws SQLException
  {
    synchronized (this.connection)
    {
      ensureOpen();
      return this.resultSet.isBeforeFirst();
    }
  }

  public boolean isAfterLast()
    throws SQLException
  {
    synchronized (this.connection)
    {
      ensureOpen();
      return this.resultSet.isAfterLast();
    }
  }

  public boolean isFirst()
    throws SQLException
  {
    synchronized (this.connection)
    {
      ensureOpen();
      return this.resultSet.isFirst();
    }
  }

  public boolean isLast()
    throws SQLException
  {
    synchronized (this.connection)
    {
      ensureOpen();
      return this.resultSet.isLast();
    }
  }

  public void beforeFirst()
    throws SQLException
  {
    synchronized (this.connection)
    {
      ensureOpen();
      cancelRowChanges();
      this.resultSet.beforeFirst();
    }
  }

  public void afterLast()
    throws SQLException
  {
    synchronized (this.connection)
    {
      ensureOpen();
      cancelRowChanges();
      this.resultSet.afterLast();
    }
  }

  public boolean first()
    throws SQLException
  {
    synchronized (this.connection)
    {
      ensureOpen();
      cancelRowChanges();

      return this.resultSet.first();
    }
  }

  public boolean last()
    throws SQLException
  {
    synchronized (this.connection)
    {
      ensureOpen();
      cancelRowChanges();

      return this.resultSet.last();
    }
  }

  public int getRow()
    throws SQLException
  {
    synchronized (this.connection)
    {
      ensureOpen();
      return this.resultSet.getRow();
    }
  }

  public boolean absolute(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      cancelRowChanges();

      return this.resultSet.absolute(paramInt);
    }
  }

  public boolean relative(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      ensureOpen();
      cancelRowChanges();

      return this.resultSet.relative(paramInt);
    }
  }

  public boolean previous()
    throws SQLException
  {
    synchronized (this.connection)
    {
      ensureOpen();
      cancelRowChanges();

      return this.resultSet.previous();
    }
  }

  public Datum getOracleObject(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      ensureOpen();
      Datum localDatum = null;

      setIsNull(3);

      if ((isOnInsertRow()) || ((isUpdatingRow()) && (isRowBufferUpdatedAt(paramInt))))
      {
        setIsNull(localDatum == null);

        localDatum = getRowBufferDatumAt(paramInt);
      }
      else
      {
        setIsNull(4);

        localDatum = this.resultSet.getOracleObject(paramInt + 1);
      }

      return localDatum;
    }
  }

  public String getString(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      ensureOpen();
      String str = null;

      setIsNull(3);

      if ((isOnInsertRow()) || ((isUpdatingRow()) && (isRowBufferUpdatedAt(paramInt))))
      {
        Datum localDatum = getRowBufferDatumAt(paramInt);

        setIsNull(localDatum == null);

        if (localDatum != null)
          str = localDatum.stringValue(this.connection);
      }
      else
      {
        setIsNull(4);

        str = this.resultSet.getString(paramInt + 1);
      }

      return str;
    }
  }

  public boolean getBoolean(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      ensureOpen();
      boolean bool = false;

      setIsNull(3);

      if ((isOnInsertRow()) || ((isUpdatingRow()) && (isRowBufferUpdatedAt(paramInt))))
      {
        Datum localDatum = getRowBufferDatumAt(paramInt);

        setIsNull(localDatum == null);

        if (localDatum != null)
          bool = localDatum.booleanValue();
      }
      else
      {
        setIsNull(4);

        bool = this.resultSet.getBoolean(paramInt + 1);
      }

      return bool;
    }
  }

  public OracleResultSet.AuthorizationIndicator getAuthorizationIndicator(int paramInt)
    throws SQLException
  {
    return this.resultSet.getAuthorizationIndicator(paramInt + 1);
  }

  public byte getByte(int paramInt) throws SQLException
  {
    synchronized (this.connection)
    {
      ensureOpen();
      byte b = 0;

      setIsNull(3);

      if ((isOnInsertRow()) || ((isUpdatingRow()) && (isRowBufferUpdatedAt(paramInt))))
      {
        Datum localDatum = getRowBufferDatumAt(paramInt);

        setIsNull(localDatum == null);

        if (localDatum != null)
          b = localDatum.byteValue();
      }
      else
      {
        setIsNull(4);

        b = this.resultSet.getByte(paramInt + 1);
      }

      return b;
    }
  }

  public short getShort(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      ensureOpen();
      short s = 0;

      setIsNull(3);

      if ((isOnInsertRow()) || ((isUpdatingRow()) && (isRowBufferUpdatedAt(paramInt))))
      {
        long l = getLong(paramInt);

        if ((l > 65537L) || (l < -65538L))
        {
          SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 26, "getShort");
          localSQLException.fillInStackTrace();
          throw localSQLException;
        }

        s = (short)(int)l;
      }
      else
      {
        setIsNull(4);

        s = this.resultSet.getShort(paramInt + 1);
      }

      return s;
    }
  }

  public int getInt(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      ensureOpen();
      int i = 0;

      setIsNull(3);

      if ((isOnInsertRow()) || ((isUpdatingRow()) && (isRowBufferUpdatedAt(paramInt))))
      {
        Datum localDatum = getRowBufferDatumAt(paramInt);

        setIsNull(localDatum == null);

        if (localDatum != null)
          i = localDatum.intValue();
      }
      else
      {
        setIsNull(4);

        i = this.resultSet.getInt(paramInt + 1);
      }

      return i;
    }
  }

  public long getLong(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      ensureOpen();
      long l = 0L;

      setIsNull(3);

      if ((isOnInsertRow()) || ((isUpdatingRow()) && (isRowBufferUpdatedAt(paramInt))))
      {
        Datum localDatum = getRowBufferDatumAt(paramInt);

        setIsNull(localDatum == null);

        if (localDatum != null)
          l = localDatum.longValue();
      }
      else
      {
        setIsNull(4);

        l = this.resultSet.getLong(paramInt + 1);
      }

      return l;
    }
  }

  public float getFloat(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      ensureOpen();
      float f = 0.0F;

      setIsNull(3);

      if ((isOnInsertRow()) || ((isUpdatingRow()) && (isRowBufferUpdatedAt(paramInt))))
      {
        Datum localDatum = getRowBufferDatumAt(paramInt);

        setIsNull(localDatum == null);

        if (localDatum != null)
          f = localDatum.floatValue();
      }
      else
      {
        setIsNull(4);

        f = this.resultSet.getFloat(paramInt + 1);
      }

      return f;
    }
  }

  public double getDouble(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      ensureOpen();
      double d = 0.0D;

      setIsNull(3);

      if ((isOnInsertRow()) || ((isUpdatingRow()) && (isRowBufferUpdatedAt(paramInt))))
      {
        Datum localDatum = getRowBufferDatumAt(paramInt);

        setIsNull(localDatum == null);

        if (localDatum != null)
          d = localDatum.doubleValue();
      }
      else
      {
        setIsNull(4);

        d = this.resultSet.getDouble(paramInt + 1);
      }

      return d;
    }
  }

  public BigDecimal getBigDecimal(int paramInt1, int paramInt2)
    throws SQLException
  {
    synchronized (this.connection)
    {
      ensureOpen();
      BigDecimal localBigDecimal = null;

      setIsNull(3);

      if ((isOnInsertRow()) || ((isUpdatingRow()) && (isRowBufferUpdatedAt(paramInt1))))
      {
        Datum localDatum = getRowBufferDatumAt(paramInt1);

        setIsNull(localDatum == null);

        if (localDatum != null)
          localBigDecimal = localDatum.bigDecimalValue();
      }
      else
      {
        setIsNull(4);

        localBigDecimal = this.resultSet.getBigDecimal(paramInt1 + 1);
      }

      return localBigDecimal;
    }
  }

  public byte[] getBytes(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      ensureOpen();
      byte[] arrayOfByte = null;

      setIsNull(3);

      if ((isOnInsertRow()) || ((isUpdatingRow()) && (isRowBufferUpdatedAt(paramInt))))
      {
        Datum localDatum = getRowBufferDatumAt(paramInt);

        setIsNull(localDatum == null);

        if (localDatum != null)
          arrayOfByte = localDatum.getBytes();
      }
      else
      {
        setIsNull(4);

        arrayOfByte = this.resultSet.getBytes(paramInt + 1);
      }

      return arrayOfByte;
    }
  }

  public Date getDate(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      ensureOpen();
      Date localDate = null;

      setIsNull(3);

      if ((isOnInsertRow()) || ((isUpdatingRow()) && (isRowBufferUpdatedAt(paramInt))))
      {
        Datum localDatum = getRowBufferDatumAt(paramInt);

        setIsNull(localDatum == null);

        if (localDatum != null)
          localDate = localDatum.dateValue();
      }
      else
      {
        setIsNull(4);

        localDate = this.resultSet.getDate(paramInt + 1);
      }

      return localDate;
    }
  }

  public Time getTime(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      ensureOpen();
      Time localTime = null;

      setIsNull(3);

      if ((isOnInsertRow()) || ((isUpdatingRow()) && (isRowBufferUpdatedAt(paramInt))))
      {
        Datum localDatum = getRowBufferDatumAt(paramInt);

        setIsNull(localDatum == null);

        if (localDatum != null)
          localTime = localDatum.timeValue();
      }
      else
      {
        setIsNull(4);

        localTime = this.resultSet.getTime(paramInt + 1);
      }

      return localTime;
    }
  }

  public Timestamp getTimestamp(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      ensureOpen();
      Timestamp localTimestamp = null;

      setIsNull(3);

      if ((isOnInsertRow()) || ((isUpdatingRow()) && (isRowBufferUpdatedAt(paramInt))))
      {
        Datum localDatum = getRowBufferDatumAt(paramInt);

        setIsNull(localDatum == null);

        if (localDatum != null)
          localTimestamp = localDatum.timestampValue();
      }
      else
      {
        setIsNull(4);

        localTimestamp = this.resultSet.getTimestamp(paramInt + 1);
      }

      return localTimestamp;
    }
  }

  public InputStream getAsciiStream(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      ensureOpen();
      InputStream localInputStream = null;

      setIsNull(3);

      if ((isOnInsertRow()) || ((isUpdatingRow()) && (isRowBufferUpdatedAt(paramInt))))
      {
        Object localObject1 = getRowBufferAt(paramInt);

        setIsNull(localObject1 == null);

        if (localObject1 != null)
        {
          if ((localObject1 instanceof InputStream))
          {
            localInputStream = (InputStream)localObject1;
          }
          else
          {
            Datum localDatum = getRowBufferDatumAt(paramInt);

            localInputStream = localDatum.asciiStreamValue();
          }
        }
      }
      else
      {
        setIsNull(4);

        localInputStream = this.resultSet.getAsciiStream(paramInt + 1);
      }

      return localInputStream;
    }
  }

  public InputStream getUnicodeStream(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      ensureOpen();
      InputStream localInputStream = null;

      setIsNull(3);

      if ((isOnInsertRow()) || ((isUpdatingRow()) && (isRowBufferUpdatedAt(paramInt))))
      {
        Object localObject1 = getRowBufferAt(paramInt);

        setIsNull(localObject1 == null);

        if (localObject1 != null)
        {
          if ((localObject1 instanceof InputStream))
          {
            localInputStream = (InputStream)localObject1;
          }
          else
          {
            Datum localDatum = getRowBufferDatumAt(paramInt);
            DBConversion localDBConversion = this.connection.conversion;
            byte[] arrayOfByte = localDatum.shareBytes();

            if ((localDatum instanceof RAW))
            {
              localInputStream = localDBConversion.ConvertStream(new ByteArrayInputStream(arrayOfByte), 3);
            }
            else if ((localDatum instanceof CHAR))
            {
              localInputStream = localDBConversion.ConvertStream(new ByteArrayInputStream(arrayOfByte), 1);
            }
            else
            {
              SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4, "getUnicodeStream");
              localSQLException.fillInStackTrace();
              throw localSQLException;
            }
          }
        }
      }
      else
      {
        setIsNull(4);

        localInputStream = this.resultSet.getUnicodeStream(paramInt + 1);
      }

      return localInputStream;
    }
  }

  public InputStream getBinaryStream(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      ensureOpen();
      InputStream localInputStream = null;

      setIsNull(3);

      if ((isOnInsertRow()) || ((isUpdatingRow()) && (isRowBufferUpdatedAt(paramInt))))
      {
        Object localObject1 = getRowBufferAt(paramInt);

        setIsNull(localObject1 == null);

        if (localObject1 != null)
        {
          if ((localObject1 instanceof InputStream))
          {
            localInputStream = (InputStream)localObject1;
          }
          else
          {
            Datum localDatum = getRowBufferDatumAt(paramInt);

            localInputStream = localDatum.binaryStreamValue();
          }
        }
      }
      else
      {
        setIsNull(4);

        localInputStream = this.resultSet.getBinaryStream(paramInt + 1);
      }

      return localInputStream;
    }
  }

  public Object getObject(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      ensureOpen();
      Object localObject1 = null;

      setIsNull(3);

      if ((isOnInsertRow()) || ((isUpdatingRow()) && (isRowBufferUpdatedAt(paramInt))))
      {
        Datum localDatum = getOracleObject(paramInt);

        setIsNull(localDatum == null);

        if (localDatum != null)
          localObject1 = localDatum.toJdbc();
      }
      else
      {
        setIsNull(4);

        localObject1 = this.resultSet.getObject(paramInt + 1);
      }

      return localObject1;
    }
  }

  public Reader getCharacterStream(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      ensureOpen();
      Reader localReader = null;

      setIsNull(3);

      if ((isOnInsertRow()) || ((isUpdatingRow()) && (isRowBufferUpdatedAt(paramInt))))
      {
        Object localObject1 = getRowBufferAt(paramInt);

        setIsNull(localObject1 == null);

        if (localObject1 != null)
        {
          if ((localObject1 instanceof Reader))
          {
            localReader = (Reader)localObject1;
          }
          else
          {
            Datum localDatum = getRowBufferDatumAt(paramInt);

            localReader = localDatum.characterStreamValue();
          }
        }
      }
      else
      {
        setIsNull(4);

        localReader = this.resultSet.getCharacterStream(paramInt + 1);
      }

      return localReader;
    }
  }

  public BigDecimal getBigDecimal(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      ensureOpen();
      BigDecimal localBigDecimal = null;

      setIsNull(3);

      if ((isOnInsertRow()) || ((isUpdatingRow()) && (isRowBufferUpdatedAt(paramInt))))
      {
        Datum localDatum = getRowBufferDatumAt(paramInt);

        setIsNull(localDatum == null);

        if (localDatum != null)
          localBigDecimal = localDatum.bigDecimalValue();
      }
      else
      {
        setIsNull(4);

        localBigDecimal = this.resultSet.getBigDecimal(paramInt + 1);
      }

      return localBigDecimal;
    }
  }

  public Object getObject(int paramInt, Map paramMap)
    throws SQLException
  {
    synchronized (this.connection)
    {
      ensureOpen();
      Object localObject1 = null;

      setIsNull(3);

      if ((isOnInsertRow()) || ((isUpdatingRow()) && (isRowBufferUpdatedAt(paramInt))))
      {
        Datum localDatum = getOracleObject(paramInt);

        setIsNull(localDatum == null);

        if (localDatum != null)
        {
          if ((localDatum instanceof STRUCT))
            localObject1 = ((STRUCT)localDatum).toJdbc(paramMap);
          else
            localObject1 = localDatum.toJdbc();
        }
      }
      else
      {
        setIsNull(4);

        localObject1 = this.resultSet.getObject(paramInt + 1, paramMap);
      }

      return localObject1;
    }
  }

  public Ref getRef(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      ensureOpen();
      return getREF(paramInt);
    }
  }

  public Blob getBlob(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      ensureOpen();
      return getBLOB(paramInt);
    }
  }

  public Clob getClob(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      ensureOpen();
      return getCLOB(paramInt);
    }
  }

  public Array getArray(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      ensureOpen();
      return getARRAY(paramInt);
    }
  }

  public Date getDate(int paramInt, Calendar paramCalendar)
    throws SQLException
  {
    synchronized (this.connection)
    {
      ensureOpen();
      Date localDate = null;

      setIsNull(3);

      if ((isOnInsertRow()) || ((isUpdatingRow()) && (isRowBufferUpdatedAt(paramInt))))
      {
        Datum localDatum = getOracleObject(paramInt);

        setIsNull(localDatum == null);

        if (localDatum != null)
        {
          if ((localDatum instanceof DATE)) {
            localDate = ((DATE)localDatum).dateValue(paramCalendar);
          }
          else
          {
            Object localObject1;
            if ((localDatum instanceof TIMESTAMP))
            {
              localObject1 = ((TIMESTAMP)localDatum).timestampValue(paramCalendar);
              long l = ((Timestamp)localObject1).getTime();
              localDate = new Date(l);
            }
            else
            {
              localObject1 = new DATE(localDatum.stringValue(this.connection));

              if (localObject1 != null)
                localDate = ((DATE)localObject1).dateValue(paramCalendar);
            }
          }
        }
      }
      else {
        setIsNull(4);

        localDate = this.resultSet.getDate(paramInt + 1, paramCalendar);
      }

      return localDate;
    }
  }

  public Time getTime(int paramInt, Calendar paramCalendar)
    throws SQLException
  {
    synchronized (this.connection)
    {
      ensureOpen();
      Time localTime = null;

      setIsNull(3);

      if ((isOnInsertRow()) || ((isUpdatingRow()) && (isRowBufferUpdatedAt(paramInt))))
      {
        Datum localDatum = getOracleObject(paramInt);

        setIsNull(localDatum == null);

        if (localDatum != null)
        {
          if ((localDatum instanceof DATE)) {
            localTime = ((DATE)localDatum).timeValue(paramCalendar);
          }
          else
          {
            Object localObject1;
            if ((localDatum instanceof TIMESTAMP))
            {
              localObject1 = ((TIMESTAMP)localDatum).timestampValue(paramCalendar);
              long l = ((Timestamp)localObject1).getTime();
              localTime = new Time(l);
            }
            else
            {
              localObject1 = new DATE(localDatum.stringValue(this.connection));

              if (localObject1 != null)
                localTime = ((DATE)localObject1).timeValue(paramCalendar);
            }
          }
        }
      }
      else {
        setIsNull(4);

        localTime = this.resultSet.getTime(paramInt + 1, paramCalendar);
      }

      return localTime;
    }
  }

  public Timestamp getTimestamp(int paramInt, Calendar paramCalendar)
    throws SQLException
  {
    synchronized (this.connection)
    {
      ensureOpen();
      Timestamp localTimestamp = null;

      setIsNull(3);

      if ((isOnInsertRow()) || ((isUpdatingRow()) && (isRowBufferUpdatedAt(paramInt))))
      {
        Datum localDatum = getOracleObject(paramInt);

        setIsNull(localDatum == null);

        if (localDatum != null)
        {
          if ((localDatum instanceof DATE)) {
            localTimestamp = ((DATE)localDatum).timestampValue(paramCalendar);
          } else if ((localDatum instanceof TIMESTAMP)) {
            localTimestamp = ((TIMESTAMP)localDatum).timestampValue(paramCalendar);
          }
          else {
            DATE localDATE = new DATE(localDatum.stringValue(this.connection));

            if (localDATE != null)
              localTimestamp = localDATE.timestampValue(paramCalendar);
          }
        }
      }
      else
      {
        setIsNull(4);

        localTimestamp = this.resultSet.getTimestamp(paramInt + 1, paramCalendar);
      }

      return localTimestamp;
    }
  }

  public URL getURL(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      ensureOpen();

      URL localURL = null;

      int i = getInternalMetadata().getColumnType(paramInt + 1);
      int j = SQLUtil.getInternalType(i);

      if ((j == 96) || (j == 1) || (j == 8))
      {
        try
        {
          String str = getString(paramInt);
          if (str == null) localURL = null; else {
            localURL = new URL(str);
          }
        }
        catch (MalformedURLException localMalformedURLException)
        {
          SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 136);
          localSQLException2.fillInStackTrace();
          throw localSQLException2;
        }

      }
      else
      {
        SQLException localSQLException1 = DatabaseError.createUnsupportedFeatureSqlException();
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      return localURL;
    }
  }

  public ResultSet getCursor(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      ensureOpen();
      ResultSet localResultSet = null;

      setIsNull(3);

      if ((isOnInsertRow()) || ((isUpdatingRow()) && (isRowBufferUpdatedAt(paramInt))))
      {
        Datum localDatum = getOracleObject(paramInt);

        setIsNull(localDatum == null);

        SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4, "getCursor");
        localSQLException.fillInStackTrace();
        throw localSQLException;
      }

      setIsNull(4);

      localResultSet = this.resultSet.getCursor(paramInt + 1);

      return localResultSet;
    }
  }

  public ROWID getROWID(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      ensureOpen();
      ROWID localROWID = null;

      setIsNull(3);

      if ((isOnInsertRow()) || ((isUpdatingRow()) && (isRowBufferUpdatedAt(paramInt))))
      {
        Datum localDatum = getRowBufferDatumAt(paramInt);

        setIsNull(localDatum == null);

        if ((localDatum != null) && (!(localDatum instanceof ROWID)))
        {
          SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4, "getROWID");
          localSQLException.fillInStackTrace();
          throw localSQLException;
        }

        localROWID = (ROWID)localDatum;
      }
      else
      {
        setIsNull(4);

        localROWID = this.resultSet.getROWID(paramInt + 1);
      }

      return localROWID;
    }
  }

  public NUMBER getNUMBER(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      ensureOpen();
      NUMBER localNUMBER = null;

      setIsNull(3);

      if ((isOnInsertRow()) || ((isUpdatingRow()) && (isRowBufferUpdatedAt(paramInt))))
      {
        Datum localDatum = getRowBufferDatumAt(paramInt);

        setIsNull(localDatum == null);

        if ((localDatum != null) && (!(localDatum instanceof NUMBER)))
        {
          SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4, "getNUMBER");
          localSQLException.fillInStackTrace();
          throw localSQLException;
        }

        localNUMBER = (NUMBER)localDatum;
      }
      else
      {
        setIsNull(4);

        localNUMBER = this.resultSet.getNUMBER(paramInt + 1);
      }

      return localNUMBER;
    }
  }

  public DATE getDATE(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      ensureOpen();
      DATE localDATE = null;

      setIsNull(3);

      if ((isOnInsertRow()) || ((isUpdatingRow()) && (isRowBufferUpdatedAt(paramInt))))
      {
        Datum localDatum = getRowBufferDatumAt(paramInt);

        if (localDatum != null)
        {
          if ((localDatum instanceof DATE)) { localDATE = (DATE)localDatum; }
          else
          {
            Object localObject1;
            if ((localDatum instanceof TIMESTAMP))
            {
              localObject1 = ((TIMESTAMP)localDatum).timestampValue();
              localDATE = new DATE((Timestamp)localObject1);
            }
            else
            {
              SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4, "getDATE");
              sqlexception.fillInStackTrace();
              throw sqlexception;
            }
          }
        }
        else
        {
          setIsNull(localDatum == null);
        }

      }
      else
      {
        setIsNull(4);

        localDATE = this.resultSet.getDATE(paramInt + 1);
      }

      return localDATE;
    }
  }

  public TIMESTAMP getTIMESTAMP(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      ensureOpen();
      TIMESTAMP localTIMESTAMP = null;

      setIsNull(3);

      if ((isOnInsertRow()) || ((isUpdatingRow()) && (isRowBufferUpdatedAt(paramInt))))
      {
        Datum localDatum = getRowBufferDatumAt(paramInt);

        setIsNull(localDatum == null);

        if ((localDatum != null) && (!(localDatum instanceof TIMESTAMP)))
        {
          SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4, "getTIMESTAMP");
          localSQLException.fillInStackTrace();
          throw localSQLException;
        }

        localTIMESTAMP = (TIMESTAMP)localDatum;
      }
      else
      {
        setIsNull(4);

        localTIMESTAMP = this.resultSet.getTIMESTAMP(paramInt + 1);
      }

      return localTIMESTAMP;
    }
  }

  public TIMESTAMPTZ getTIMESTAMPTZ(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      ensureOpen();
      TIMESTAMPTZ localTIMESTAMPTZ = null;

      setIsNull(3);

      if ((isOnInsertRow()) || ((isUpdatingRow()) && (isRowBufferUpdatedAt(paramInt))))
      {
        Datum localDatum = getRowBufferDatumAt(paramInt);

        setIsNull(localDatum == null);

        if ((localDatum != null) && (!(localDatum instanceof TIMESTAMPTZ)))
        {
          SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4, "getTIMESTAMPTZ");
          localSQLException.fillInStackTrace();
          throw localSQLException;
        }

        localTIMESTAMPTZ = (TIMESTAMPTZ)localDatum;
      }
      else
      {
        setIsNull(4);

        localTIMESTAMPTZ = this.resultSet.getTIMESTAMPTZ(paramInt + 1);
      }

      return localTIMESTAMPTZ;
    }
  }

  public TIMESTAMPLTZ getTIMESTAMPLTZ(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      ensureOpen();
      TIMESTAMPLTZ localTIMESTAMPLTZ = null;

      setIsNull(3);

      if ((isOnInsertRow()) || ((isUpdatingRow()) && (isRowBufferUpdatedAt(paramInt))))
      {
        Datum localDatum = getRowBufferDatumAt(paramInt);

        setIsNull(localDatum == null);

        if ((localDatum != null) && (!(localDatum instanceof TIMESTAMPLTZ)))
        {
          SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4, "getTIMESTAMPLTZ");
          localSQLException.fillInStackTrace();
          throw localSQLException;
        }

        localTIMESTAMPLTZ = (TIMESTAMPLTZ)localDatum;
      }
      else
      {
        setIsNull(4);

        localTIMESTAMPLTZ = this.resultSet.getTIMESTAMPLTZ(paramInt + 1);
      }

      return localTIMESTAMPLTZ;
    }
  }

  public INTERVALDS getINTERVALDS(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      ensureOpen();
      INTERVALDS localINTERVALDS = null;

      setIsNull(3);

      if ((isOnInsertRow()) || ((isUpdatingRow()) && (isRowBufferUpdatedAt(paramInt))))
      {
        Datum localDatum = getRowBufferDatumAt(paramInt);

        setIsNull(localDatum == null);

        if ((localDatum != null) && (!(localDatum instanceof INTERVALDS)))
        {
          SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4, "getINTERVALDS");
          localSQLException.fillInStackTrace();
          throw localSQLException;
        }

        localINTERVALDS = (INTERVALDS)localDatum;
      }
      else
      {
        setIsNull(4);

        localINTERVALDS = this.resultSet.getINTERVALDS(paramInt + 1);
      }

      return localINTERVALDS;
    }
  }

  public INTERVALYM getINTERVALYM(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      ensureOpen();
      INTERVALYM localINTERVALYM = null;

      setIsNull(3);

      if ((isOnInsertRow()) || ((isUpdatingRow()) && (isRowBufferUpdatedAt(paramInt))))
      {
        Datum localDatum = getRowBufferDatumAt(paramInt);

        setIsNull(localDatum == null);

        if ((localDatum != null) && (!(localDatum instanceof INTERVALYM)))
        {
          SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4, "getINTERVALYM");
          localSQLException.fillInStackTrace();
          throw localSQLException;
        }

        localINTERVALYM = (INTERVALYM)localDatum;
      }
      else
      {
        setIsNull(4);

        localINTERVALYM = this.resultSet.getINTERVALYM(paramInt + 1);
      }

      return localINTERVALYM;
    }
  }

  public ARRAY getARRAY(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      ensureOpen();
      ARRAY localARRAY = null;

      setIsNull(3);

      if ((isOnInsertRow()) || ((isUpdatingRow()) && (isRowBufferUpdatedAt(paramInt))))
      {
        Datum localDatum = getRowBufferDatumAt(paramInt);

        setIsNull(localDatum == null);

        if ((localDatum != null) && (!(localDatum instanceof ARRAY)))
        {
          SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4, "getARRAY");
          localSQLException.fillInStackTrace();
          throw localSQLException;
        }

        localARRAY = (ARRAY)localDatum;
      }
      else
      {
        setIsNull(4);

        localARRAY = this.resultSet.getARRAY(paramInt + 1);
      }

      return localARRAY;
    }
  }

  public STRUCT getSTRUCT(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      ensureOpen();
      STRUCT localSTRUCT = null;

      setIsNull(3);

      if ((isOnInsertRow()) || ((isUpdatingRow()) && (isRowBufferUpdatedAt(paramInt))))
      {
        Datum localDatum = getRowBufferDatumAt(paramInt);

        setIsNull(localDatum == null);

        if ((localDatum != null) && (!(localDatum instanceof STRUCT)))
        {
          SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4, "getSTRUCT");
          localSQLException.fillInStackTrace();
          throw localSQLException;
        }

        localSTRUCT = (STRUCT)localDatum;
      }
      else
      {
        setIsNull(4);

        localSTRUCT = this.resultSet.getSTRUCT(paramInt + 1);
      }

      return localSTRUCT;
    }
  }

  public OPAQUE getOPAQUE(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      ensureOpen();
      OPAQUE localOPAQUE = null;

      setIsNull(3);

      if ((isOnInsertRow()) || ((isUpdatingRow()) && (isRowBufferUpdatedAt(paramInt))))
      {
        Datum localDatum = getRowBufferDatumAt(paramInt);

        setIsNull(localDatum == null);

        if ((localDatum != null) && (!(localDatum instanceof OPAQUE)))
        {
          SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4, "getOPAQUE");
          localSQLException.fillInStackTrace();
          throw localSQLException;
        }

        localOPAQUE = (OPAQUE)localDatum;
      }
      else
      {
        setIsNull(4);

        localOPAQUE = this.resultSet.getOPAQUE(paramInt + 1);
      }

      return localOPAQUE;
    }
  }

  public REF getREF(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      ensureOpen();
      REF localREF = null;

      setIsNull(3);

      if ((isOnInsertRow()) || ((isUpdatingRow()) && (isRowBufferUpdatedAt(paramInt))))
      {
        Datum localDatum = getRowBufferDatumAt(paramInt);

        setIsNull(localDatum == null);

        if ((localDatum != null) && (!(localDatum instanceof REF)))
        {
          SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4, "getREF");
          localSQLException.fillInStackTrace();
          throw localSQLException;
        }

        localREF = (REF)localDatum;
      }
      else
      {
        setIsNull(4);

        localREF = this.resultSet.getREF(paramInt + 1);
      }

      return localREF;
    }
  }

  public CHAR getCHAR(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      ensureOpen();
      CHAR localCHAR = null;

      setIsNull(3);

      if ((isOnInsertRow()) || ((isUpdatingRow()) && (isRowBufferUpdatedAt(paramInt))))
      {
        Datum localDatum = getRowBufferDatumAt(paramInt);

        setIsNull(localDatum == null);

        if ((localDatum != null) && (!(localDatum instanceof CHAR)))
        {
          SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4, "getCHAR");
          localSQLException.fillInStackTrace();
          throw localSQLException;
        }

        localCHAR = (CHAR)localDatum;
      }
      else
      {
        setIsNull(4);

        localCHAR = this.resultSet.getCHAR(paramInt + 1);
      }

      return localCHAR;
    }
  }

  public RAW getRAW(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      ensureOpen();
      RAW localRAW = null;

      setIsNull(3);

      if ((isOnInsertRow()) || ((isUpdatingRow()) && (isRowBufferUpdatedAt(paramInt))))
      {
        Datum localDatum = getRowBufferDatumAt(paramInt);

        setIsNull(localDatum == null);

        if ((localDatum != null) && (!(localDatum instanceof RAW)))
        {
          SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4, "getRAW");
          localSQLException.fillInStackTrace();
          throw localSQLException;
        }

        localRAW = (RAW)localDatum;
      }
      else
      {
        setIsNull(4);

        localRAW = this.resultSet.getRAW(paramInt + 1);
      }

      return localRAW;
    }
  }

  public BLOB getBLOB(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      ensureOpen();
      BLOB localBLOB = null;

      setIsNull(3);

      if ((isOnInsertRow()) || ((isUpdatingRow()) && (isRowBufferUpdatedAt(paramInt))))
      {
        Datum localDatum = getRowBufferDatumAt(paramInt);

        setIsNull(localDatum == null);

        if ((localDatum != null) && (!(localDatum instanceof BLOB)))
        {
          SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4, "getBLOB");
          localSQLException.fillInStackTrace();
          throw localSQLException;
        }

        localBLOB = (BLOB)localDatum;
      }
      else
      {
        setIsNull(4);

        localBLOB = this.resultSet.getBLOB(paramInt + 1);
      }

      return localBLOB;
    }
  }

  public NCLOB getNCLOB(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      ensureOpen();
      NCLOB localNCLOB = null;

      setIsNull(3);

      if ((isOnInsertRow()) || ((isUpdatingRow()) && (isRowBufferUpdatedAt(paramInt))))
      {
        Datum localDatum = getRowBufferDatumAt(paramInt);

        setIsNull(localDatum == null);

        if ((localDatum != null) && (!(localDatum instanceof NCLOB)))
        {
          SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4, "getCLOB");
          localSQLException.fillInStackTrace();
          throw localSQLException;
        }

        localNCLOB = (NCLOB)localDatum;
      }
      else
      {
        setIsNull(4);

        localNCLOB = (NCLOB)this.resultSet.getNClob(paramInt + 1);
      }

      return localNCLOB;
    }
  }

  public CLOB getCLOB(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      ensureOpen();
      CLOB localCLOB = null;

      setIsNull(3);

      if ((isOnInsertRow()) || ((isUpdatingRow()) && (isRowBufferUpdatedAt(paramInt))))
      {
        Datum localDatum = getRowBufferDatumAt(paramInt);

        setIsNull(localDatum == null);

        if ((localDatum != null) && (!(localDatum instanceof CLOB)))
        {
          SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4, "getCLOB");
          localSQLException.fillInStackTrace();
          throw localSQLException;
        }

        localCLOB = (CLOB)localDatum;
      }
      else
      {
        setIsNull(4);

        localCLOB = this.resultSet.getCLOB(paramInt + 1);
      }

      return localCLOB;
    }
  }

  public BFILE getBFILE(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      ensureOpen();
      BFILE localBFILE = null;

      setIsNull(3);

      if ((isOnInsertRow()) || ((isUpdatingRow()) && (isRowBufferUpdatedAt(paramInt))))
      {
        Datum localDatum = getRowBufferDatumAt(paramInt);

        setIsNull(localDatum == null);

        if ((localDatum != null) && (!(localDatum instanceof BFILE)))
        {
          SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4, "getBFILE");
          localSQLException.fillInStackTrace();
          throw localSQLException;
        }

        localBFILE = (BFILE)localDatum;
      }
      else
      {
        setIsNull(4);

        localBFILE = this.resultSet.getBFILE(paramInt + 1);
      }

      return localBFILE;
    }
  }

  public BFILE getBfile(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      ensureOpen();
      return getBFILE(paramInt);
    }
  }

  public CustomDatum getCustomDatum(int paramInt, CustomDatumFactory paramCustomDatumFactory)
    throws SQLException
  {
    synchronized (this.connection)
    {
      ensureOpen();
      if (paramCustomDatumFactory == null)
      {
        SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68);
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }

      CustomDatum localObject1 = null;

      setIsNull(3);

      if ((isOnInsertRow()) || ((isUpdatingRow()) && (isRowBufferUpdatedAt(paramInt))))
      {
        Datum localDatum = getRowBufferDatumAt(paramInt);

        setIsNull(localDatum == null);

        localObject1 = paramCustomDatumFactory.create(localDatum, 0);
      }
      else
      {
        setIsNull(4);

        localObject1 = this.resultSet.getCustomDatum(paramInt + 1, paramCustomDatumFactory);
      }

      return localObject1;
    }
  }

  public ORAData getORAData(int paramInt, ORADataFactory paramORADataFactory)
    throws SQLException
  {
    synchronized (this.connection)
    {
      ensureOpen();
      if (paramORADataFactory == null)
      {
        SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68);
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }

      ORAData localObject1 = null;

      setIsNull(3);

      if ((isOnInsertRow()) || ((isUpdatingRow()) && (isRowBufferUpdatedAt(paramInt))))
      {
        Datum localDatum = getRowBufferDatumAt(paramInt);

        setIsNull(localDatum == null);

        localObject1 = paramORADataFactory.create(localDatum, 0);
      }
      else
      {
        setIsNull(4);

        localObject1 = this.resultSet.getORAData(paramInt + 1, paramORADataFactory);
      }

      return localObject1;
    }
  }

  public NClob getNClob(int paramInt)
    throws SQLException
  {
    ensureOpen();
    NCLOB localNCLOB = getNCLOB(paramInt);

    if (localNCLOB == null) return null;

    if (!(localNCLOB instanceof NClob))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 184);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    return localNCLOB;
  }

  public String getNString(int paramInt)
    throws SQLException
  {
    ensureOpen();
    return getString(paramInt);
  }

  public Reader getNCharacterStream(int paramInt)
    throws SQLException
  {
    ensureOpen();
    return getCharacterStream(paramInt);
  }

  public RowId getRowId(int paramInt)
    throws SQLException
  {
    ensureOpen();
    return getROWID(paramInt);
  }

  public SQLXML getSQLXML(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      ensureOpen();
      SQLXML localSQLXML = null;

      setIsNull(3);

      if ((isOnInsertRow()) || ((isUpdatingRow()) && (isRowBufferUpdatedAt(paramInt))))
      {
        Datum localDatum = getRowBufferDatumAt(paramInt);

        setIsNull(localDatum == null);

        if ((localDatum != null) && (!(localDatum instanceof SQLXML)))
        {
          SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4, "getSQLXML");
          localSQLException.fillInStackTrace();
          throw localSQLException;
        }

        localSQLXML = (SQLXML)localDatum;
      }
      else
      {
        setIsNull(4);

        localSQLXML = this.resultSet.getSQLXML(paramInt + 1);
      }

      return localSQLXML;
    }
  }

  public void updateRowId(int paramInt, RowId paramRowId)
    throws SQLException
  {
    updateROWID(paramInt, (ROWID)paramRowId);
  }

  public void updateNCharacterStream(int paramInt, Reader paramReader, long paramLong)
    throws SQLException
  {
    updateCharacterStream(paramInt, paramReader, paramLong);
  }

  public void updateNCharacterStream(int paramInt, Reader paramReader)
    throws SQLException
  {
    updateCharacterStream(paramInt, paramReader);
  }

  public void updateSQLXML(int paramInt, SQLXML paramSQLXML)
    throws SQLException
  {
    updateOracleObject(paramInt, (Datum)paramSQLXML);
  }

  public void updateNString(int paramInt, String paramString)
    throws SQLException
  {
    updateString(paramInt, paramString);
  }

  public void updateNClob(int paramInt, NClob paramNClob)
    throws SQLException
  {
    updateClob(paramInt, paramNClob);
  }

  public void updateAsciiStream(int paramInt, InputStream paramInputStream, long paramLong)
    throws SQLException
  {
    updateAsciiStream(paramInt, paramInputStream, (int)paramLong);
  }

  public void updateAsciiStream(int paramInt, InputStream paramInputStream)
    throws SQLException
  {
    updateAsciiStream(paramInt, paramInputStream, 2147483647);
  }

  public void updateBinaryStream(int paramInt, InputStream paramInputStream, long paramLong)
    throws SQLException
  {
    updateBinaryStream(paramInt, paramInputStream, (int)paramLong);
  }

  public void updateBinaryStream(int paramInt, InputStream paramInputStream)
    throws SQLException
  {
    updateBinaryStream(paramInt, paramInputStream, 2147483647);
  }

  public void updateCharacterStream(int paramInt, Reader paramReader, long paramLong)
    throws SQLException
  {
    updateCharacterStream(paramInt, paramReader, (int)paramLong);
  }

  public void updateCharacterStream(int paramInt, Reader paramReader)
    throws SQLException
  {
    updateCharacterStream(paramInt, paramReader, 2147483647);
  }

  public void updateBlob(int paramInt, InputStream paramInputStream)
    throws SQLException
  {
    Blob localBlob = this.connection.createBlob();
    addToTempLobsToFree(localBlob);
    int i = ((BLOB)localBlob).getBufferSize();
    OutputStream localOutputStream = localBlob.setBinaryStream(1L);
    byte[] arrayOfByte = new byte[i];
    try
    {
      while (true)
      {
        int j = paramInputStream.read(arrayOfByte);
        if (j == -1) break;
        localOutputStream.write(arrayOfByte, 0, j);
      }
      localOutputStream.close();
      updateBlob(paramInt, localBlob);
    }
    catch (IOException localIOException)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localIOException);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  public void updateBlob(int paramInt, InputStream paramInputStream, long paramLong)
    throws SQLException
  {
    Blob localBlob = this.connection.createBlob();

    addToTempLobsToFree(localBlob);
    int i = ((BLOB)localBlob).getBufferSize();
    OutputStream localOutputStream = localBlob.setBinaryStream(1L);
    byte[] arrayOfByte = new byte[i];
    long l = paramLong;
    try
    {
      while (l > 0L)
      {
        int j = paramInputStream.read(arrayOfByte, 0, Math.min(i, (int)l));
        if (j == -1) break;
        localOutputStream.write(arrayOfByte, 0, j);

        l -= j;
      }
      localOutputStream.close();
      updateBlob(paramInt, localBlob);
    }
    catch (IOException localIOException)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localIOException);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  public void updateClob(int paramInt, Reader paramReader, long paramLong)
    throws SQLException
  {
    Clob localClob = this.connection.createClob();

    addToTempLobsToFree(localClob);
    int i = ((CLOB)localClob).getBufferSize();
    Writer localWriter = localClob.setCharacterStream(1L);
    char[] arrayOfChar = new char[i];
    long l = paramLong;
    try
    {
      while (l > 0L)
      {
        int j = paramReader.read(arrayOfChar, 0, Math.min(i, (int)l));
        if (j == -1) break;
        localWriter.write(arrayOfChar, 0, j);

        l -= j;
      }
      localWriter.close();
      updateClob(paramInt, localClob);
    }
    catch (IOException localIOException)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localIOException);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  public void updateClob(int paramInt, Reader paramReader)
    throws SQLException
  {
    Clob localClob = this.connection.createClob();
    addToTempLobsToFree(localClob);
    int i = ((CLOB)localClob).getBufferSize();
    Writer localWriter = localClob.setCharacterStream(1L);
    char[] arrayOfChar = new char[i];
    try
    {
      while (true)
      {
        int j = paramReader.read(arrayOfChar);
        if (j == -1) break;
        localWriter.write(arrayOfChar, 0, j);
      }
      localWriter.close();
      updateClob(paramInt, localClob);
    }
    catch (IOException localIOException)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localIOException);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  void updateClob(int paramInt1, InputStream paramInputStream, int paramInt2)
    throws SQLException
  {
    Clob localClob = this.connection.createClob();

    addToTempLobsToFree(localClob);
    int i = ((CLOB)localClob).getBufferSize();
    OutputStream localOutputStream = localClob.setAsciiStream(1L);
    byte[] arrayOfByte = new byte[i];
    long l = paramInt2;
    try
    {
      while (l > 0L)
      {
        int j = paramInputStream.read(arrayOfByte, 0, Math.min(i, (int)l));
        if (j == -1) break;
        localOutputStream.write(arrayOfByte, 0, j);

        l -= j;
      }
      localOutputStream.close();
      updateClob(paramInt1, localClob);
    }
    catch (IOException localIOException)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localIOException);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  void updateNClob(int paramInt1, InputStream paramInputStream, int paramInt2)
    throws SQLException
  {
    NClob localNClob = this.connection.createNClob();
    addToTempLobsToFree(localNClob);
    int i = ((NCLOB)localNClob).getBufferSize();
    OutputStream localOutputStream = localNClob.setAsciiStream(1L);
    byte[] arrayOfByte = new byte[i];
    long l = paramInt2;
    try
    {
      while (l > 0L)
      {
        int j = paramInputStream.read(arrayOfByte, 0, Math.min(i, (int)l));
        if (j == -1) break;
        localOutputStream.write(arrayOfByte, 0, j);

        l -= j;
      }
      localOutputStream.close();
      updateNClob(paramInt1, localNClob);
    }
    catch (IOException localIOException)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localIOException);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  public void updateNClob(int paramInt, Reader paramReader, long paramLong)
    throws SQLException
  {
    NClob localNClob = this.connection.createNClob();
    addToTempLobsToFree(localNClob);
    int i = ((CLOB)localNClob).getBufferSize();
    Writer localWriter = localNClob.setCharacterStream(1L);
    char[] arrayOfChar = new char[i];
    long l = paramLong;
    try
    {
      while (l > 0L)
      {
        int j = paramReader.read(arrayOfChar, 0, Math.min(i, (int)l));
        if (j == -1) break;
        localWriter.write(arrayOfChar, 0, j);

        l -= j;
      }
      localWriter.close();
      updateNClob(paramInt, localNClob);
    }
    catch (IOException localIOException)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localIOException);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  public void updateNClob(int paramInt, Reader paramReader)
    throws SQLException
  {
    NClob localNClob = this.connection.createNClob();
    addToTempLobsToFree(localNClob);
    int i = ((CLOB)localNClob).getBufferSize();
    Writer localWriter = localNClob.setCharacterStream(1L);
    char[] arrayOfChar = new char[i];
    try
    {
      while (true)
      {
        int j = paramReader.read(arrayOfChar);
        if (j == -1) break;
        localWriter.write(arrayOfChar, 0, j);
      }
      localWriter.close();
      updateNClob(paramInt, localNClob);
    }
    catch (IOException localIOException)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localIOException);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  void addToTempLobsToFree(Clob paramClob)
  {
    if (this.tempClobsToFree == null)
      this.tempClobsToFree = new ArrayList();
    this.tempClobsToFree.add(paramClob);
  }

  void addToTempLobsToFree(Blob paramBlob)
  {
    if (this.tempBlobsToFree == null)
      this.tempBlobsToFree = new ArrayList();
    this.tempBlobsToFree.add(paramBlob);
  }

  void cleanTempLobs()
  {
    cleanTempClobs(this.tempClobsToFree);
    cleanTempBlobs(this.tempBlobsToFree);
    this.tempClobsToFree = null;
    this.tempBlobsToFree = null;
  }

  void cleanTempBlobs(ArrayList paramArrayList)
  {
    if (paramArrayList != null)
    {
      Iterator localIterator = paramArrayList.iterator();

      while (localIterator.hasNext())
      {
        try
        {
          ((BLOB)localIterator.next()).freeTemporary();
        }
        catch (SQLException localSQLException)
        {
        }
      }
    }
  }

  void cleanTempClobs(ArrayList paramArrayList)
  {
    if (paramArrayList != null)
    {
      Iterator localIterator = paramArrayList.iterator();

      while (localIterator.hasNext())
      {
        try
        {
          ((CLOB)localIterator.next()).freeTemporary();
        }
        catch (SQLException localSQLException)
        {
        }
      }
    }
  }

  public ResultSetMetaData getMetaData()
    throws SQLException
  {
    if (((OracleStatement)this.scrollStmt).closed)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9, "getMetaData");
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    synchronized (this.connection) {
      return new OracleResultSetMetaData(this.connection, (OracleStatement)this.scrollStmt, 1);
    }
  }

  public int findColumn(String paramString)
    throws SQLException
  {
    synchronized (this.connection)
    {
      ensureOpen();
      return this.resultSet.findColumn(paramString) - 1;
    }
  }

  public void setFetchDirection(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      ensureOpen();
      this.resultSet.setFetchDirection(paramInt);
    }
  }

  public int getFetchDirection()
    throws SQLException
  {
    synchronized (this.connection)
    {
      ensureOpen();
      return this.resultSet.getFetchDirection();
    }
  }

  public void setFetchSize(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      ensureOpen();
      this.resultSet.setFetchSize(paramInt);
    }
  }

  public int getFetchSize()
    throws SQLException
  {
    synchronized (this.connection)
    {
      ensureOpen();
      return this.resultSet.getFetchSize();
    }
  }

  public int getType()
    throws SQLException
  {
    return this.rsetType;
  }

  public int getConcurrency()
    throws SQLException
  {
    return 1008;
  }

  public boolean rowUpdated()
    throws SQLException
  {
    return false;
  }

  public boolean rowInserted()
    throws SQLException
  {
    return false;
  }

  public boolean rowDeleted()
    throws SQLException
  {
    return false;
  }

  public void insertRow()
    throws SQLException
  {
    synchronized (this.connection)
    {
      ensureOpen();
      if (!isOnInsertRow())
      {
        SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 83);
        localSQLException.fillInStackTrace();
        throw localSQLException;
      }

      prepareInsertRowStatement();
      prepareInsertRowBinds();
      executeInsertRow();
    }
  }

  public void updateRow()
    throws SQLException
  {
    synchronized (this.connection)
    {
      ensureOpen();

      if (isOnInsertRow())
      {
        SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 84);
        localSQLException.fillInStackTrace();
        throw localSQLException;
      }

      int i = getNumColumnsChanged();

      if (i > 0)
      {
        prepareUpdateRowStatement(i);
        prepareUpdateRowBinds(i);
        executeUpdateRow();
      }
    }
  }

  public void deleteRow()
    throws SQLException
  {
    synchronized (this.connection)
    {
      ensureOpen();

      if (isOnInsertRow())
      {
        SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 84);
        localSQLException.fillInStackTrace();
        throw localSQLException;
      }

      prepareDeleteRowStatement();
      prepareDeleteRowBinds();
      executeDeleteRow();
    }
  }

  public void refreshRow()
    throws SQLException
  {
    synchronized (this.connection)
    {
      ensureOpen();

      if (isOnInsertRow())
      {
        SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 84);
        localSQLException.fillInStackTrace();
        throw localSQLException;
      }

      this.resultSet.refreshRow();
    }
  }

  public void cancelRowUpdates()
    throws SQLException
  {
    synchronized (this.connection)
    {
      ensureOpen();
      if (this.isUpdating)
      {
        this.isUpdating = false;

        clearRowBuffer();
      }
    }
  }

  public void moveToInsertRow()
    throws SQLException
  {
    synchronized (this.connection)
    {
      ensureOpen();
      if (isOnInsertRow()) {
        return;
      }
      this.isInserting = true;

      if (this.rowBuffer == null) {
        this.rowBuffer = new Object[getColumnCount()];
      }
      if (this.m_nullIndicator == null) {
        this.m_nullIndicator = new boolean[getColumnCount()];
      }
      clearRowBuffer();
    }
  }

  public void moveToCurrentRow()
    throws SQLException
  {
    synchronized (this.connection)
    {
      ensureOpen();
      cancelRowInserts();
    }
  }

  public void updateString(int paramInt, String paramString)
    throws SQLException
  {
    synchronized (this.connection)
    {
      if ((paramString == null) || (paramString.length() == 0))
        updateNull(paramInt);
      else
        updateObject(paramInt, paramString);
    }
  }

  public void updateNull(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      setRowBufferAt(paramInt, null);
    }
  }

  public void updateBoolean(int paramInt, boolean paramBoolean)
    throws SQLException
  {
    updateObject(paramInt, Boolean.valueOf(paramBoolean));
  }

  public void updateByte(int paramInt, byte paramByte)
    throws SQLException
  {
    updateObject(paramInt, Integer.valueOf(paramByte));
  }

  public void updateShort(int paramInt, short paramShort)
    throws SQLException
  {
    updateObject(paramInt, Integer.valueOf(paramShort));
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

  public void updateBytes(int paramInt, byte[] paramArrayOfByte)
    throws SQLException
  {
    updateObject(paramInt, paramArrayOfByte);
  }

  public void updateDate(int paramInt, Date paramDate)
    throws SQLException
  {
    updateObject(paramInt, paramDate);
  }

  public void updateTime(int paramInt, Time paramTime)
    throws SQLException
  {
    updateObject(paramInt, paramTime);
  }

  public void updateTimestamp(int paramInt, Timestamp paramTimestamp)
    throws SQLException
  {
    updateObject(paramInt, paramTimestamp);
  }

  public void updateAsciiStream(int paramInt1, InputStream paramInputStream, int paramInt2)
    throws SQLException
  {
    ensureOpen();
    int i = getInternalMetadata().getColumnType(1 + paramInt1);

    if ((paramInputStream != null) && (paramInt2 > 0))
    {
      switch (i)
      {
      case 2005:
        updateClob(paramInt1, paramInputStream, paramInt2);
        break;
      case 2004:
        updateBlob(paramInt1, paramInputStream, paramInt2);
        break;
      case -1:
        int[] arrayOfInt = { paramInt2, 1 };

        setRowBufferAt(paramInt1, paramInputStream, arrayOfInt);
        break;
      default:
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
          SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localIOException);
          localSQLException.fillInStackTrace();
          throw localSQLException;
        }
      }

    }
    else
    {
      setRowBufferAt(paramInt1, null);
    }
  }

  public void updateBinaryStream(int paramInt1, InputStream paramInputStream, int paramInt2)
    throws SQLException
  {
    ensureOpen();
    int i = getInternalMetadata().getColumnType(1 + paramInt1);

    if ((paramInputStream != null) && (paramInt2 > 0))
    {
      switch (i)
      {
      case 2004:
        updateBlob(paramInt1, paramInputStream, paramInt2);
        break;
      case -4:
        int[] arrayOfInt = { paramInt2, 2 };

        setRowBufferAt(paramInt1, paramInputStream, arrayOfInt);
        break;
      default:
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
          SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localIOException);
          localSQLException.fillInStackTrace();
          throw localSQLException;
        }

      }

    }
    else
    {
      setRowBufferAt(paramInt1, null);
    }
  }

  public void updateCharacterStream(int paramInt1, Reader paramReader, int paramInt2)
    throws SQLException
  {
    int i = 0; int j = paramInt2;

    ensureOpen();
    int k = getInternalMetadata().getColumnType(1 + paramInt1);

    if ((paramReader != null) && (paramInt2 > 0))
    {
      switch (k)
      {
      case 2005:
        updateClob(paramInt1, paramReader, paramInt2);
        break;
      case 2011:
        updateNClob(paramInt1, paramReader, paramInt2);
        break;
      case -1:
        int[] arrayOfInt = { paramInt2 };

        setRowBufferAt(paramInt1, paramReader, arrayOfInt);
        break;
      default:
        try
        {
          char[] arrayOfChar = new char[1024];
          StringBuilder localObject = new StringBuilder(1024);

          while (j > 0)
          {
            if (j >= 1024)
              i = paramReader.read(arrayOfChar);
            else {
              i = paramReader.read(arrayOfChar, 0, j);
            }

            if (i == -1) {
              break;
            }
            ((StringBuilder)localObject).append(arrayOfChar, 0, i);
            j -= i;
          }

          paramReader.close();
          if (j == paramInt2)
          {
            updateNull(paramInt1);
            return;
          }

          updateString(paramInt1, ((StringBuilder)localObject).toString());
        }
        catch (IOException localIOException)
        {
          SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localIOException);
          sqlexception.fillInStackTrace();
          throw sqlexception;
        }

      }

    }
    else
    {
      setRowBufferAt(paramInt1, null);
    }
  }

  public void updateObject(int paramInt1, Object paramObject, int paramInt2)
    throws SQLException
  {
    updateObject(paramInt1, paramObject);
  }

  public void updateObject(int paramInt, Object paramObject)
    throws SQLException
  {
    synchronized (this.connection)
    {
      ensureOpen();
      Datum localDatum = null;

      if (paramObject != null)
      {
        if ((paramObject instanceof Datum)) {
          localDatum = (Datum)paramObject;
        }
        else {
          OracleResultSetMetaData localOracleResultSetMetaData = (OracleResultSetMetaData)getInternalMetadata();
          int i = paramInt + 1;
          if (localOracleResultSetMetaData.getColumnType(i) == 96) {
            int j = localOracleResultSetMetaData.getColumnDisplaySize(i);
            String str = (String)paramObject;
            for (int k = str.length(); k < j; k++) str = new StringBuilder().append(str).append(" ").toString();
          }
          localDatum = SQLUtil.makeOracleDatum(this.connection, paramObject, localOracleResultSetMetaData.getColumnType(i), null, localOracleResultSetMetaData.isNCHAR(i));
        }

      }

      setRowBufferAt(paramInt, localDatum);
    }
  }

  public void updateOracleObject(int paramInt, Datum paramDatum)
    throws SQLException
  {
    synchronized (this.connection)
    {
      setRowBufferAt(paramInt, paramDatum);
    }
  }

  public void updateROWID(int paramInt, ROWID paramROWID)
    throws SQLException
  {
    updateOracleObject(paramInt, paramROWID);
  }

  public void updateNUMBER(int paramInt, NUMBER paramNUMBER)
    throws SQLException
  {
    updateOracleObject(paramInt, paramNUMBER);
  }

  public void updateDATE(int paramInt, DATE paramDATE)
    throws SQLException
  {
    updateOracleObject(paramInt, paramDATE);
  }

  public void updateINTERVALYM(int paramInt, INTERVALYM paramINTERVALYM)
    throws SQLException
  {
    updateOracleObject(paramInt, paramINTERVALYM);
  }

  public void updateINTERVALDS(int paramInt, INTERVALDS paramINTERVALDS)
    throws SQLException
  {
    updateOracleObject(paramInt, paramINTERVALDS);
  }

  public void updateTIMESTAMP(int paramInt, TIMESTAMP paramTIMESTAMP)
    throws SQLException
  {
    updateOracleObject(paramInt, paramTIMESTAMP);
  }

  public void updateTIMESTAMPTZ(int paramInt, TIMESTAMPTZ paramTIMESTAMPTZ)
    throws SQLException
  {
    updateOracleObject(paramInt, paramTIMESTAMPTZ);
  }

  public void updateTIMESTAMPLTZ(int paramInt, TIMESTAMPLTZ paramTIMESTAMPLTZ)
    throws SQLException
  {
    updateOracleObject(paramInt, paramTIMESTAMPLTZ);
  }

  public void updateARRAY(int paramInt, ARRAY paramARRAY)
    throws SQLException
  {
    updateOracleObject(paramInt, paramARRAY);
  }

  public void updateSTRUCT(int paramInt, STRUCT paramSTRUCT)
    throws SQLException
  {
    updateOracleObject(paramInt, paramSTRUCT);
  }

  public void updateOPAQUE(int paramInt, OPAQUE paramOPAQUE)
    throws SQLException
  {
    updateOracleObject(paramInt, paramOPAQUE);
  }

  public void updateREF(int paramInt, REF paramREF)
    throws SQLException
  {
    updateOracleObject(paramInt, paramREF);
  }

  public void updateCHAR(int paramInt, CHAR paramCHAR)
    throws SQLException
  {
    updateOracleObject(paramInt, paramCHAR);
  }

  public void updateRAW(int paramInt, RAW paramRAW)
    throws SQLException
  {
    updateOracleObject(paramInt, paramRAW);
  }

  public void updateBLOB(int paramInt, BLOB paramBLOB)
    throws SQLException
  {
    updateOracleObject(paramInt, paramBLOB);
  }

  public void updateCLOB(int paramInt, CLOB paramCLOB)
    throws SQLException
  {
    updateOracleObject(paramInt, paramCLOB);
  }

  public void updateBFILE(int paramInt, BFILE paramBFILE)
    throws SQLException
  {
    updateOracleObject(paramInt, paramBFILE);
  }

  public void updateBfile(int paramInt, BFILE paramBFILE)
    throws SQLException
  {
    updateOracleObject(paramInt, paramBFILE);
  }

  public void updateCustomDatum(int paramInt, CustomDatum paramCustomDatum)
    throws SQLException
  {
    throw new Error("wanna do datum = ((CustomDatum) x).toDatum(m_comm)");
  }

  public void updateORAData(int paramInt, ORAData paramORAData)
    throws SQLException
  {
    Datum localDatum = paramORAData.toDatum(this.connection);

    updateOracleObject(paramInt, localDatum);
  }

  public void updateRef(int paramInt, Ref paramRef)
    throws SQLException
  {
    updateREF(paramInt, (REF)paramRef);
  }

  public void updateBlob(int paramInt, Blob paramBlob)
    throws SQLException
  {
    updateBLOB(paramInt, (BLOB)paramBlob);
  }

  public void updateClob(int paramInt, Clob paramClob)
    throws SQLException
  {
    updateCLOB(paramInt, (CLOB)paramClob);
  }

  public void updateArray(int paramInt, Array paramArray)
    throws SQLException
  {
    updateARRAY(paramInt, (ARRAY)paramArray);
  }

  int getColumnCount()
    throws SQLException
  {
    if (this.columnCount == 0)
    {
      if ((this.resultSet instanceof OracleResultSetImpl))
      {
        if (((OracleResultSetImpl)this.resultSet).statement.accessors != null) {
          this.columnCount = ((OracleResultSetImpl)this.resultSet).statement.numberOfDefinePositions;
        }
        else
          this.columnCount = getInternalMetadata().getColumnCount();
      }
      else {
        this.columnCount = ((ScrollableResultSet)this.resultSet).getColumnCount();
      }
    }
    return this.columnCount;
  }

  ResultSetMetaData getInternalMetadata()
    throws SQLException
  {
    if (this.rsetMetaData == null) {
      this.rsetMetaData = this.resultSet.getMetaData();
    }
    return this.rsetMetaData;
  }

  private void cancelRowChanges()
    throws SQLException
  {
    synchronized (this.connection)
    {
      if (this.isInserting) {
        cancelRowInserts();
      }
      if (this.isUpdating)
        cancelRowUpdates();
    }
  }

  boolean isOnInsertRow()
  {
    return this.isInserting;
  }

  private void cancelRowInserts()
  {
    if (this.isInserting)
    {
      this.isInserting = false;

      clearRowBuffer();
    }
  }

  boolean isUpdatingRow()
  {
    return this.isUpdating;
  }

  private void clearRowBuffer()
  {
    int i;
    if (this.rowBuffer != null)
    {
      for (i = 0; i < this.rowBuffer.length; i++) {
        this.rowBuffer[i] = null;
      }
    }
    if (this.m_nullIndicator != null)
    {
      for (i = 0; i < this.m_nullIndicator.length; i++) {
        this.m_nullIndicator[i] = false;
      }
    }
    if (this.typeInfo != null)
    {
      for (i = 0; i < this.typeInfo.length; i++) {
        if (this.typeInfo[i] != null) {
          for (int j = 0; j < this.typeInfo[i].length; j++) {
            this.typeInfo[i][j] = 0;
          }
        }
      }
    }

    cleanTempLobs();
  }

  private void setRowBufferAt(int paramInt, Datum paramDatum)
    throws SQLException
  {
    setRowBufferAt(paramInt, paramDatum, (int[])null);
  }

  private void setRowBufferAt(int paramInt, Object paramObject, int[] paramArrayOfInt)
    throws SQLException
  {
    SQLException localSQLException;
    if (!this.isInserting)
    {
      if ((isBeforeFirst()) || (isAfterLast()) || (getRow() == 0))
      {
        localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 82);
        localSQLException.fillInStackTrace();
        throw localSQLException;
      }

      this.isUpdating = true;
    }

    if ((paramInt < 1) || (paramInt > getColumnCount() - 1))
    {
      localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68, "setRowBufferAt");
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if (this.rowBuffer == null) {
      this.rowBuffer = new Object[getColumnCount()];
    }
    if (this.m_nullIndicator == null)
    {
      this.m_nullIndicator = new boolean[getColumnCount()];

      for (int i = 0; i < getColumnCount(); i++) {
        this.m_nullIndicator[i] = false;
      }
    }
    if (paramArrayOfInt != null)
    {
      if (this.typeInfo == null)
      {
        this.typeInfo = new int[getColumnCount()][];
      }

      this.typeInfo[paramInt] = paramArrayOfInt;
    }

    this.rowBuffer[paramInt] = paramObject;
    this.m_nullIndicator[paramInt] = paramObject == null;
  }

  private Datum getRowBufferDatumAt(int paramInt)
    throws SQLException
  {
    if ((paramInt < 1) || (paramInt > getColumnCount() - 1))
    {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68, "getRowBufferDatumAt");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Datum localObject1 = null;

    if (this.rowBuffer != null)
    {
      Object localObject2 = this.rowBuffer[paramInt];

      if (localObject2 != null)
      {
        if ((localObject2 instanceof Datum))
        {
          localObject1 = (Datum)localObject2;
        }
        else
        {
          OracleResultSetMetaData localOracleResultSetMetaData = (OracleResultSetMetaData)getInternalMetadata();
          int i = paramInt + 1;
          localObject1 = SQLUtil.makeOracleDatum(this.connection, localObject2, localOracleResultSetMetaData.getColumnType(i), null, localOracleResultSetMetaData.isNCHAR(i));

          this.rowBuffer[paramInt] = localObject1;
        }
      }
    }

    return localObject1;
  }

  private Object getRowBufferAt(int paramInt)
    throws SQLException
  {
    if ((paramInt < 1) || (paramInt > getColumnCount() - 1))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68, "getRowBufferDatumAt");
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if (this.rowBuffer != null)
    {
      return this.rowBuffer[paramInt];
    }

    return null;
  }

  private boolean isRowBufferUpdatedAt(int paramInt)
  {
    if (this.rowBuffer == null) {
      return false;
    }
    return this.rowBuffer[paramInt] != null || this.m_nullIndicator[paramInt];
  }

  private void prepareInsertRowStatement()
    throws SQLException
  {
    if (this.insertStmt == null)
    {
      PreparedStatement localPreparedStatement = this.connection.prepareStatement(((OracleStatement)this.scrollStmt).sqlObject.getInsertSqlForUpdatableResultSet(this));

      this.insertStmt = ((OraclePreparedStatement)((OraclePreparedStatementWrapper)localPreparedStatement).preparedStatement);
      this.insertStmt.setQueryTimeout(((Statement)this.scrollStmt).getQueryTimeout());
      if (((OracleStatement)this.scrollStmt).sqlObject.generatedSqlNeedEscapeProcessing())
        this.insertStmt.setEscapeProcessing(true);
    }
  }

  private void prepareInsertRowBinds()
    throws SQLException
  {
    int i = 1;

    i = prepareSubqueryBinds(this.insertStmt, i);

    OracleResultSetMetaData localOracleResultSetMetaData = (OracleResultSetMetaData)getInternalMetadata();

    for (int j = 1; j < getColumnCount(); j++)
    {
      Object localObject = getRowBufferAt(j);

      if (localObject != null)
      {
        if ((localObject instanceof Reader))
        {
          if (localOracleResultSetMetaData.isNCHAR(j + 1))
            this.insertStmt.setFormOfUse(i, (short)2);
          this.insertStmt.setCharacterStream(i + j - 1, (Reader)localObject, this.typeInfo[j][0]);
        }
        else if ((localObject instanceof InputStream))
        {
          if (this.typeInfo[j][1] == 2) {
            this.insertStmt.setBinaryStream(i + j - 1, (InputStream)localObject, this.typeInfo[j][0]);
          }
          else if (this.typeInfo[j][1] == 1) {
            this.insertStmt.setAsciiStream(i + j - 1, (InputStream)localObject, this.typeInfo[j][0]);
          }

        }
        else
        {
          Datum localDatum = getRowBufferDatumAt(j);

          if (localOracleResultSetMetaData.isNCHAR(j + 1))
            this.insertStmt.setFormOfUse(i + j - 1, (short)2);
          this.insertStmt.setOracleObject(i + j - 1, localDatum);
        }

      }
      else
      {
        int k = getInternalMetadata().getColumnType(j + 1);

        if ((k == 2006) || (k == 2002) || (k == 2008) || (k == 2007) || (k == 2003))
        {
          this.insertStmt.setNull(i + j - 1, k, getInternalMetadata().getColumnTypeName(j + 1));
        }
        else
        {
          this.insertStmt.setNull(i + j - 1, k);
        }
      }
    }
  }

  private void executeInsertRow()
    throws SQLException
  {
    if (this.insertStmt.executeUpdate() != 1)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 85);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  private int getNumColumnsChanged()
    throws SQLException
  {
    int i = 0;

    if (this.indexColsChanged == null) {
      this.indexColsChanged = new int[getColumnCount()];
    }
    if (this.rowBuffer != null)
    {
      for (int j = 1; j < getColumnCount(); j++)
      {
        if (this.rowBuffer[j] != null || (this.rowBuffer[j] == null && this.m_nullIndicator[j]))
        {
          this.indexColsChanged[(i++)] = j;
        }
      }
    }

    return i;
  }

  private void prepareUpdateRowStatement(int paramInt)
    throws SQLException
  {
    if (this.updateStmt != null) {
      this.updateStmt.close();
    }
    PreparedStatement localPreparedStatement = this.connection.prepareStatement(((OracleStatement)this.scrollStmt).sqlObject.getUpdateSqlForUpdatableResultSet(this, paramInt, this.rowBuffer, this.indexColsChanged));

    this.updateStmt = ((OraclePreparedStatement)((OraclePreparedStatementWrapper)localPreparedStatement).preparedStatement);
    this.updateStmt.setQueryTimeout(((Statement)this.scrollStmt).getQueryTimeout());
    if (((OracleStatement)this.scrollStmt).sqlObject.generatedSqlNeedEscapeProcessing())
      this.updateStmt.setEscapeProcessing(true);
  }

  private void prepareUpdateRowBinds(int paramInt)
    throws SQLException
  {
    int i = 1;

    i = prepareSubqueryBinds(this.updateStmt, i);

    OracleResultSetMetaData localOracleResultSetMetaData = (OracleResultSetMetaData)getInternalMetadata();

    for (int j = 0; j < paramInt; j++)
    {
      int k = this.indexColsChanged[j];
      Object localObject = getRowBufferAt(k);

      if (localObject != null)
      {
        if ((localObject instanceof Reader))
        {
          if (localOracleResultSetMetaData.isNCHAR(k + 1))
            this.updateStmt.setFormOfUse(i, (short)2);
          this.updateStmt.setCharacterStream(i++, (Reader)localObject, this.typeInfo[k][0]);
        }
        else if ((localObject instanceof InputStream))
        {
          if (this.typeInfo[k][1] == 2)
          {
            this.updateStmt.setBinaryStream(i++, (InputStream)localObject, this.typeInfo[k][0]);
          }
          else if (this.typeInfo[k][1] == 1)
          {
            this.updateStmt.setAsciiStream(i++, (InputStream)localObject, this.typeInfo[k][0]);
          }
        }
        else
        {
          Datum localDatum = getRowBufferDatumAt(k);

          if (localOracleResultSetMetaData.isNCHAR(k + 1))
            this.updateStmt.setFormOfUse(i, (short)2);
          this.updateStmt.setOracleObject(i++, localDatum);
        }

      }
      else
      {
        int m = getInternalMetadata().getColumnType(k + 1);

        if ((m == 2006) || (m == 2002) || (m == 2008) || (m == 2007) || (m == 2003))
        {
          this.updateStmt.setNull(i++, m, getInternalMetadata().getColumnTypeName(k + 1));
        }
        else
        {
          if (localOracleResultSetMetaData.isNCHAR(k + 1))
            this.updateStmt.setFormOfUse(i, (short)2);
          this.updateStmt.setNull(i++, m);
        }
      }

    }

    prepareCompareSelfBinds(this.updateStmt, i);
  }

  private void executeUpdateRow()
    throws SQLException
  {
    try
    {
      if (this.updateStmt.executeUpdate() == 0)
      {
        SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 85);
        localSQLException.fillInStackTrace();
        throw localSQLException;
      }

      if (this.isCachedRset)
      {
        ((ScrollableResultSet)this.resultSet).refreshRowsInCache(getRow(), 1, 1000);

        cancelRowUpdates();
      }

    }
    finally
    {
      if (this.updateStmt != null)
      {
        this.updateStmt.close();

        this.updateStmt = null;
      }
    }
  }

  private void prepareDeleteRowStatement()
    throws SQLException
  {
    if (this.deleteStmt == null)
    {
      PreparedStatement localPreparedStatement = this.connection.prepareStatement(((OracleStatement)this.scrollStmt).sqlObject.getDeleteSqlForUpdatableResultSet(this));

      this.deleteStmt = ((OraclePreparedStatement)((OraclePreparedStatementWrapper)localPreparedStatement).preparedStatement);
      this.deleteStmt.setQueryTimeout(((Statement)this.scrollStmt).getQueryTimeout());
      if (((OracleStatement)this.scrollStmt).sqlObject.generatedSqlNeedEscapeProcessing())
        this.deleteStmt.setEscapeProcessing(true);
    }
  }

  private void prepareDeleteRowBinds()
    throws SQLException
  {
    int i = 1;

    i = prepareSubqueryBinds(this.deleteStmt, i);

    prepareCompareSelfBinds(this.deleteStmt, i);
  }

  private void executeDeleteRow()
    throws SQLException
  {
    if (this.deleteStmt.executeUpdate() == 0)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 85);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if (this.isCachedRset)
      ((ScrollableResultSet)this.resultSet).removeRowInCache(getRow());
  }

  private int prepareCompareSelfBinds(OraclePreparedStatement paramOraclePreparedStatement, int paramInt)
    throws SQLException
  {
    paramOraclePreparedStatement.setOracleObject(paramInt, this.resultSet.getOracleObject(1));

    return paramInt + 1;
  }

  private int prepareSubqueryBinds(OraclePreparedStatement paramOraclePreparedStatement, int paramInt)
    throws SQLException
  {
    int i = this.scrollStmt.copyBinds(paramOraclePreparedStatement, paramInt - 1);

    return i + 1;
  }

  private void setIsNull(int paramInt)
  {
    this.wasNull = paramInt;
  }

  private void setIsNull(boolean paramBoolean)
  {
    if (paramBoolean)
      this.wasNull = 1;
    else
      this.wasNull = 2;
  }

  public String getCursorName()
    throws SQLException
  {
    synchronized (this.connection)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 23, "getCursorName");
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  protected OracleConnection getConnectionDuringExceptionHandling()
  {
    return this.connection;
  }

  OracleStatement getOracleStatement()
    throws SQLException
  {
    return this.resultSet == null ? null : this.resultSet.getOracleStatement();
  }
}