package oracle.jdbc.rowset;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.NClob;
import java.sql.PreparedStatement;
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
import java.util.Map;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.sql.RowSet;
import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetWarning;
import oracle.jdbc.OracleResultSet;
import oracle.jdbc.OracleSavepoint;
import oracle.jdbc.driver.DatabaseError;
import oracle.jdbc.driver.OracleDriver;
import oracle.jdbc.internal.OraclePreparedStatement;

public class OracleJDBCRowSet extends OracleRowSet
  implements RowSet, JdbcRowSet
{
  private Connection connection;
  private static boolean driverManagerInitialized;
  private PreparedStatement preparedStatement;
  private ResultSet resultSet;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  public OracleJDBCRowSet()
    throws SQLException
  {
    driverManagerInitialized = false;
  }

  public OracleJDBCRowSet(Connection paramConnection)
    throws SQLException
  {
    this();

    this.connection = paramConnection;
  }

  public void execute()
    throws SQLException
  {
    this.connection = getConnection(this);
    try
    {
      this.connection.setTransactionIsolation(getTransactionIsolation());
    }
    catch (Exception localException)
    {
    }

    this.connection.setTypeMap(getTypeMap());

    if (this.preparedStatement == null) {
      this.preparedStatement = this.connection.prepareStatement(getCommand(), getType(), getConcurrency());
    }
    this.preparedStatement.setFetchSize(getFetchSize());
    this.preparedStatement.setFetchDirection(getFetchDirection());
    this.preparedStatement.setMaxFieldSize(getMaxFieldSize());
    this.preparedStatement.setMaxRows(getMaxRows());
    this.preparedStatement.setQueryTimeout(getQueryTimeout());
    this.preparedStatement.setEscapeProcessing(getEscapeProcessing());

    this.resultSet = this.preparedStatement.executeQuery();
    notifyRowSetChanged();
  }

  public void close()
    throws SQLException
  {
    if (this.resultSet != null) {
      this.resultSet.close();
    }
    if (this.preparedStatement != null) {
      this.preparedStatement.close();
    }
    if ((this.connection != null) && (!this.connection.isClosed()))
    {
      this.connection.commit();
      this.connection.close();
    }
    notifyRowSetChanged();

    this.isClosed = true;
  }

  private Connection getConnection(RowSet paramRowSet)
    throws SQLException
  {
    Connection localConnection = null;

    if ((this.connection != null) && (!this.connection.isClosed()))
    {
      localConnection = this.connection;
    }
    else
    {
      Object localObject;
      if (paramRowSet.getDataSourceName() != null)
      {
        try
        {
          InitialContext localInitialContext = new InitialContext();
          localObject = (DataSource)localInitialContext.lookup(paramRowSet.getDataSourceName());

          if ((paramRowSet.getUsername() == null) || (paramRowSet.getPassword() == null))
          {
            localConnection = ((DataSource)localObject).getConnection();
          }
          else
          {
            localConnection = ((DataSource)localObject).getConnection(paramRowSet.getUsername(), paramRowSet.getPassword());
          }

        }
        catch (NamingException localNamingException)
        {
          SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 300, localNamingException.getMessage());
          sqlexception.fillInStackTrace();
          throw sqlexception;
        }

      }
      else if (paramRowSet.getUrl() != null)
      {
        if (!driverManagerInitialized)
        {
          DriverManager.registerDriver(new OracleDriver());
          driverManagerInitialized = true;
        }
        String str1 = paramRowSet.getUrl();
        localObject = paramRowSet.getUsername();
        String str2 = paramRowSet.getPassword();

        if ((str1.equals("")) || (((String)localObject).equals("")) || (str2.equals("")))
        {
          SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 301);
          localSQLException.fillInStackTrace();
          throw localSQLException;
        }

        localConnection = DriverManager.getConnection(str1, (String)localObject, str2);
      }
    }
    return localConnection;
  }

  public boolean wasNull()
    throws SQLException
  {
    return this.resultSet.wasNull();
  }

  public SQLWarning getWarnings()
    throws SQLException
  {
    return this.resultSet.getWarnings();
  }

  public void clearWarnings()
    throws SQLException
  {
    this.resultSet.clearWarnings();
  }

  public String getCursorName()
    throws SQLException
  {
    return this.resultSet.getCursorName();
  }

  public ResultSetMetaData getMetaData()
    throws SQLException
  {
    return new OracleRowSetMetaData(this.resultSet.getMetaData());
  }

  public int findColumn(String paramString)
    throws SQLException
  {
    return this.resultSet.findColumn(paramString);
  }

  public void clearParameters()
    throws SQLException
  {
    this.preparedStatement.clearParameters();
  }

  public Statement getStatement()
    throws SQLException
  {
    return this.resultSet.getStatement();
  }

  public void setCommand(String paramString)
    throws SQLException
  {
    super.setCommand(paramString);

    if ((this.connection == null) || (this.connection.isClosed())) {
      this.connection = getConnection(this);
    }
    if (this.preparedStatement != null)
    {
      try
      {
        this.preparedStatement.close();
        this.preparedStatement = null;
      } catch (SQLException localSQLException) {
      }
    }
    this.preparedStatement = this.connection.prepareStatement(paramString, getType(), getConcurrency());
  }

  public void setReadOnly(boolean paramBoolean)
    throws SQLException
  {
    super.setReadOnly(paramBoolean);

    if (this.connection != null)
    {
      this.connection.setReadOnly(paramBoolean);
    }
    else
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 302);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  public void setFetchDirection(int paramInt)
    throws SQLException
  {
    super.setFetchDirection(paramInt);

    this.resultSet.setFetchDirection(this.fetchDirection);
  }

  public void setShowDeleted(boolean paramBoolean)
    throws SQLException
  {
    if (paramBoolean)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 303);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    super.setShowDeleted(paramBoolean);
  }

  public boolean next()
    throws SQLException
  {
    boolean bool = this.resultSet.next();

    if (bool) {
      notifyCursorMoved();
    }
    return bool;
  }

  public boolean previous()
    throws SQLException
  {
    boolean bool = this.resultSet.previous();

    if (bool) {
      notifyCursorMoved();
    }
    return bool;
  }

  public void beforeFirst()
    throws SQLException
  {
    if (!isBeforeFirst())
    {
      this.resultSet.beforeFirst();
      notifyCursorMoved();
    }
  }

  public void afterLast()
    throws SQLException
  {
    if (!isAfterLast())
    {
      this.resultSet.afterLast();
      notifyCursorMoved();
    }
  }

  public boolean first()
    throws SQLException
  {
    boolean bool = this.resultSet.first();

    if (bool) {
      notifyCursorMoved();
    }
    return bool;
  }

  public boolean last()
    throws SQLException
  {
    boolean bool = this.resultSet.last();

    if (bool) {
      notifyCursorMoved();
    }
    return bool;
  }

  public boolean absolute(int paramInt)
    throws SQLException
  {
    boolean bool = this.resultSet.absolute(paramInt);

    if (bool) {
      notifyCursorMoved();
    }
    return bool;
  }

  public boolean relative(int paramInt)
    throws SQLException
  {
    boolean bool = this.resultSet.relative(paramInt);

    if (bool) {
      notifyCursorMoved();
    }
    return bool;
  }

  public boolean isBeforeFirst()
    throws SQLException
  {
    return this.resultSet.isBeforeFirst();
  }

  public boolean isAfterLast()
    throws SQLException
  {
    return this.resultSet.isAfterLast();
  }

  public boolean isFirst()
    throws SQLException
  {
    return this.resultSet.isFirst();
  }

  public boolean isLast()
    throws SQLException
  {
    return this.resultSet.isLast();
  }

  public void insertRow()
    throws SQLException
  {
    if (isReadOnly())
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 309);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.resultSet.insertRow();
    notifyRowChanged();
  }

  public void updateRow()
    throws SQLException
  {
    if (isReadOnly())
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 309);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.resultSet.updateRow();
    notifyRowChanged();
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

    this.resultSet.deleteRow();
    notifyRowChanged();
  }

  public void refreshRow()
    throws SQLException
  {
    this.resultSet.refreshRow();
  }

  public void cancelRowUpdates()
    throws SQLException
  {
    this.resultSet.cancelRowUpdates();
    notifyRowChanged();
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

    this.resultSet.moveToInsertRow();
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

    this.resultSet.moveToCurrentRow();
  }

  public int getRow()
    throws SQLException
  {
    return this.resultSet.getRow();
  }

  public boolean rowUpdated()
    throws SQLException
  {
    return this.resultSet.rowUpdated();
  }

  public boolean rowInserted()
    throws SQLException
  {
    return this.resultSet.rowInserted();
  }

  public boolean rowDeleted()
    throws SQLException
  {
    return this.resultSet.rowDeleted();
  }

  public void setNull(int paramInt1, int paramInt2)
    throws SQLException
  {
    this.preparedStatement.setNull(paramInt1, paramInt2);
  }

  public void setNull(int paramInt1, int paramInt2, String paramString)
    throws SQLException
  {
    this.preparedStatement.setNull(paramInt1, paramInt2, paramString);
  }

  public void setBoolean(int paramInt, boolean paramBoolean)
    throws SQLException
  {
    this.preparedStatement.setBoolean(paramInt, paramBoolean);
  }

  public void setByte(int paramInt, byte paramByte)
    throws SQLException
  {
    this.preparedStatement.setByte(paramInt, paramByte);
  }

  public void setShort(int paramInt, short paramShort)
    throws SQLException
  {
    this.preparedStatement.setShort(paramInt, paramShort);
  }

  public void setInt(int paramInt1, int paramInt2)
    throws SQLException
  {
    this.preparedStatement.setInt(paramInt1, paramInt2);
  }

  public void setLong(int paramInt, long paramLong)
    throws SQLException
  {
    this.preparedStatement.setLong(paramInt, paramLong);
  }

  public void setFloat(int paramInt, float paramFloat)
    throws SQLException
  {
    this.preparedStatement.setFloat(paramInt, paramFloat);
  }

  public void setDouble(int paramInt, double paramDouble)
    throws SQLException
  {
    this.preparedStatement.setDouble(paramInt, paramDouble);
  }

  public void setBigDecimal(int paramInt, BigDecimal paramBigDecimal)
    throws SQLException
  {
    this.preparedStatement.setBigDecimal(paramInt, paramBigDecimal);
  }

  public void setString(int paramInt, String paramString)
    throws SQLException
  {
    this.preparedStatement.setString(paramInt, paramString);
  }

  public void setBytes(int paramInt, byte[] paramArrayOfByte)
    throws SQLException
  {
    this.preparedStatement.setBytes(paramInt, paramArrayOfByte);
  }

  public void setDate(int paramInt, Date paramDate)
    throws SQLException
  {
    this.preparedStatement.setDate(paramInt, paramDate);
  }

  public void setTime(int paramInt, Time paramTime)
    throws SQLException
  {
    this.preparedStatement.setTime(paramInt, paramTime);
  }

  public void setObject(int paramInt, Object paramObject)
    throws SQLException
  {
    this.preparedStatement.setObject(paramInt, paramObject);
  }

  public void setRef(int paramInt, Ref paramRef)
    throws SQLException
  {
    this.preparedStatement.setRef(paramInt, paramRef);
  }

  public void setBlob(int paramInt, Blob paramBlob)
    throws SQLException
  {
    this.preparedStatement.setBlob(paramInt, paramBlob);
  }

  public void setClob(int paramInt, Clob paramClob)
    throws SQLException
  {
    this.preparedStatement.setClob(paramInt, paramClob);
  }

  public void setArray(int paramInt, Array paramArray)
    throws SQLException
  {
    this.preparedStatement.setArray(paramInt, paramArray);
  }

  public void setBinaryStream(int paramInt1, InputStream paramInputStream, int paramInt2)
    throws SQLException
  {
    this.preparedStatement.setBinaryStream(paramInt1, paramInputStream, paramInt2);
  }

  public void setTime(int paramInt, Time paramTime, Calendar paramCalendar)
    throws SQLException
  {
    this.preparedStatement.setTime(paramInt, paramTime, paramCalendar);
  }

  public void setTimestamp(int paramInt, Timestamp paramTimestamp, Calendar paramCalendar)
    throws SQLException
  {
    this.preparedStatement.setTimestamp(paramInt, paramTimestamp, paramCalendar);
  }

  public void setTimestamp(int paramInt, Timestamp paramTimestamp)
    throws SQLException
  {
    this.preparedStatement.setTimestamp(paramInt, paramTimestamp);
  }

  public void setAsciiStream(int paramInt1, InputStream paramInputStream, int paramInt2)
    throws SQLException
  {
    this.preparedStatement.setAsciiStream(paramInt1, paramInputStream, paramInt2);
  }

  public void setCharacterStream(int paramInt1, Reader paramReader, int paramInt2)
    throws SQLException
  {
    this.preparedStatement.setCharacterStream(paramInt1, paramReader, paramInt2);
  }

  public void setObject(int paramInt1, Object paramObject, int paramInt2, int paramInt3)
    throws SQLException
  {
    this.preparedStatement.setObject(paramInt1, paramObject, paramInt2, paramInt3);
  }

  public void setObject(int paramInt1, Object paramObject, int paramInt2)
    throws SQLException
  {
    this.preparedStatement.setObject(paramInt1, paramObject, paramInt2);
  }

  public void setDate(int paramInt, Date paramDate, Calendar paramCalendar)
    throws SQLException
  {
    this.preparedStatement.setDate(paramInt, paramDate, paramCalendar);
  }

  public Object getObject(int paramInt, Map paramMap)
    throws SQLException
  {
    return this.resultSet.getObject(paramInt, paramMap);
  }

  public BigDecimal getBigDecimal(int paramInt)
    throws SQLException
  {
    return this.resultSet.getBigDecimal(paramInt);
  }

  public Ref getRef(int paramInt)
    throws SQLException
  {
    return this.resultSet.getRef(paramInt);
  }

  public Blob getBlob(int paramInt)
    throws SQLException
  {
    return this.resultSet.getBlob(paramInt);
  }

  public Clob getClob(int paramInt)
    throws SQLException
  {
    return this.resultSet.getClob(paramInt);
  }

  public Array getArray(int paramInt)
    throws SQLException
  {
    return this.resultSet.getArray(paramInt);
  }

  public Date getDate(int paramInt, Calendar paramCalendar)
    throws SQLException
  {
    return this.resultSet.getDate(paramInt, paramCalendar);
  }

  public Reader getCharacterStream(int paramInt)
    throws SQLException
  {
    return this.resultSet.getCharacterStream(paramInt);
  }

  public Time getTime(int paramInt, Calendar paramCalendar)
    throws SQLException
  {
    return this.resultSet.getTime(paramInt, paramCalendar);
  }

  public InputStream getBinaryStream(int paramInt)
    throws SQLException
  {
    return this.resultSet.getBinaryStream(paramInt);
  }

  public Timestamp getTimestamp(int paramInt, Calendar paramCalendar)
    throws SQLException
  {
    return this.resultSet.getTimestamp(paramInt, paramCalendar);
  }

  public String getString(int paramInt)
    throws SQLException
  {
    return this.resultSet.getString(paramInt);
  }

  public boolean getBoolean(int paramInt)
    throws SQLException
  {
    return this.resultSet.getBoolean(paramInt);
  }

  public byte getByte(int paramInt)
    throws SQLException
  {
    return this.resultSet.getByte(paramInt);
  }

  public short getShort(int paramInt)
    throws SQLException
  {
    return this.resultSet.getShort(paramInt);
  }

  public long getLong(int paramInt)
    throws SQLException
  {
    return this.resultSet.getLong(paramInt);
  }

  public float getFloat(int paramInt)
    throws SQLException
  {
    return this.resultSet.getFloat(paramInt);
  }

  public double getDouble(int paramInt)
    throws SQLException
  {
    return this.resultSet.getDouble(paramInt);
  }

  public BigDecimal getBigDecimal(int paramInt1, int paramInt2)
    throws SQLException
  {
    return this.resultSet.getBigDecimal(paramInt1, paramInt2);
  }

  public byte[] getBytes(int paramInt)
    throws SQLException
  {
    return this.resultSet.getBytes(paramInt);
  }

  public Date getDate(int paramInt)
    throws SQLException
  {
    return this.resultSet.getDate(paramInt);
  }

  public Time getTime(int paramInt)
    throws SQLException
  {
    return this.resultSet.getTime(paramInt);
  }

  public Timestamp getTimestamp(int paramInt)
    throws SQLException
  {
    return this.resultSet.getTimestamp(paramInt);
  }

  public InputStream getAsciiStream(int paramInt)
    throws SQLException
  {
    return this.resultSet.getAsciiStream(paramInt);
  }

  public InputStream getUnicodeStream(int paramInt)
    throws SQLException
  {
    return this.resultSet.getUnicodeStream(paramInt);
  }

  public int getInt(int paramInt)
    throws SQLException
  {
    return this.resultSet.getInt(paramInt);
  }

  public Object getObject(int paramInt)
    throws SQLException
  {
    return this.resultSet.getObject(paramInt);
  }

  public int getInt(String paramString)
    throws SQLException
  {
    return this.resultSet.getInt(paramString);
  }

  public long getLong(String paramString)
    throws SQLException
  {
    return this.resultSet.getLong(paramString);
  }

  public float getFloat(String paramString)
    throws SQLException
  {
    return this.resultSet.getFloat(paramString);
  }

  public double getDouble(String paramString)
    throws SQLException
  {
    return this.resultSet.getDouble(paramString);
  }

  public BigDecimal getBigDecimal(String paramString, int paramInt)
    throws SQLException
  {
    return this.resultSet.getBigDecimal(paramString, paramInt);
  }

  public byte[] getBytes(String paramString)
    throws SQLException
  {
    return this.resultSet.getBytes(paramString);
  }

  public Date getDate(String paramString)
    throws SQLException
  {
    return this.resultSet.getDate(paramString);
  }

  public Time getTime(String paramString)
    throws SQLException
  {
    return this.resultSet.getTime(paramString);
  }

  public Timestamp getTimestamp(String paramString)
    throws SQLException
  {
    return this.resultSet.getTimestamp(paramString);
  }

  public InputStream getAsciiStream(String paramString)
    throws SQLException
  {
    return this.resultSet.getAsciiStream(paramString);
  }

  public InputStream getUnicodeStream(String paramString)
    throws SQLException
  {
    return this.resultSet.getUnicodeStream(paramString);
  }

  public Object getObject(String paramString)
    throws SQLException
  {
    return this.resultSet.getObject(paramString);
  }

  public Reader getCharacterStream(String paramString)
    throws SQLException
  {
    return this.resultSet.getCharacterStream(paramString);
  }

  public Object getObject(String paramString, Map paramMap)
    throws SQLException
  {
    return this.resultSet.getObject(paramString, paramMap);
  }

  public Ref getRef(String paramString)
    throws SQLException
  {
    return this.resultSet.getRef(paramString);
  }

  public Blob getBlob(String paramString)
    throws SQLException
  {
    return this.resultSet.getBlob(paramString);
  }

  public Clob getClob(String paramString)
    throws SQLException
  {
    return this.resultSet.getClob(paramString);
  }

  public Array getArray(String paramString)
    throws SQLException
  {
    return this.resultSet.getArray(paramString);
  }

  public BigDecimal getBigDecimal(String paramString)
    throws SQLException
  {
    return this.resultSet.getBigDecimal(paramString);
  }

  public Date getDate(String paramString, Calendar paramCalendar)
    throws SQLException
  {
    return this.resultSet.getDate(paramString, paramCalendar);
  }

  public Time getTime(String paramString, Calendar paramCalendar)
    throws SQLException
  {
    return this.resultSet.getTime(paramString, paramCalendar);
  }

  public InputStream getBinaryStream(String paramString)
    throws SQLException
  {
    return this.resultSet.getBinaryStream(paramString);
  }

  public Timestamp getTimestamp(String paramString, Calendar paramCalendar)
    throws SQLException
  {
    return this.resultSet.getTimestamp(paramString, paramCalendar);
  }

  public String getString(String paramString)
    throws SQLException
  {
    return this.resultSet.getString(paramString);
  }

  public boolean getBoolean(String paramString)
    throws SQLException
  {
    return this.resultSet.getBoolean(paramString);
  }

  public byte getByte(String paramString)
    throws SQLException
  {
    return this.resultSet.getByte(paramString);
  }

  public short getShort(String paramString)
    throws SQLException
  {
    return this.resultSet.getShort(paramString);
  }

  public void updateNull(int paramInt)
    throws SQLException
  {
    if (isReadOnly())
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 309);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.resultSet.updateNull(paramInt);
  }

  public void updateCharacterStream(int paramInt1, Reader paramReader, int paramInt2)
    throws SQLException
  {
    if (isReadOnly())
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 309);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.resultSet.updateCharacterStream(paramInt1, paramReader, paramInt2);
  }

  public void updateTimestamp(int paramInt, Timestamp paramTimestamp)
    throws SQLException
  {
    if (isReadOnly())
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 309);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.resultSet.updateTimestamp(paramInt, paramTimestamp);
  }

  public void updateBinaryStream(int paramInt1, InputStream paramInputStream, int paramInt2)
    throws SQLException
  {
    if (isReadOnly())
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 309);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.resultSet.updateBinaryStream(paramInt1, paramInputStream, paramInt2);
  }

  public void updateAsciiStream(int paramInt1, InputStream paramInputStream, int paramInt2)
    throws SQLException
  {
    if (isReadOnly())
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 309);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.resultSet.updateAsciiStream(paramInt1, paramInputStream, paramInt2);
  }

  public void updateBoolean(int paramInt, boolean paramBoolean)
    throws SQLException
  {
    if (isReadOnly())
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 309);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.resultSet.updateBoolean(paramInt, paramBoolean);
  }

  public void updateByte(int paramInt, byte paramByte)
    throws SQLException
  {
    if (isReadOnly())
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 309);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.resultSet.updateByte(paramInt, paramByte);
  }

  public void updateShort(int paramInt, short paramShort)
    throws SQLException
  {
    if (isReadOnly())
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 309);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.resultSet.updateShort(paramInt, paramShort);
  }

  public void updateInt(int paramInt1, int paramInt2)
    throws SQLException
  {
    if (isReadOnly())
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 309);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.resultSet.updateInt(paramInt1, paramInt2);
  }

  public void updateLong(int paramInt, long paramLong)
    throws SQLException
  {
    if (isReadOnly())
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 309);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.resultSet.updateLong(paramInt, paramLong);
  }

  public void updateFloat(int paramInt, float paramFloat)
    throws SQLException
  {
    if (isReadOnly())
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 309);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.resultSet.updateFloat(paramInt, paramFloat);
  }

  public void updateDouble(int paramInt, double paramDouble)
    throws SQLException
  {
    if (isReadOnly())
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 309);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.resultSet.updateDouble(paramInt, paramDouble);
  }

  public void updateBigDecimal(int paramInt, BigDecimal paramBigDecimal)
    throws SQLException
  {
    if (isReadOnly())
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 309);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.resultSet.updateBigDecimal(paramInt, paramBigDecimal);
  }

  public void updateString(int paramInt, String paramString)
    throws SQLException
  {
    if (isReadOnly())
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 309);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.resultSet.updateString(paramInt, paramString);
  }

  public void updateBytes(int paramInt, byte[] paramArrayOfByte)
    throws SQLException
  {
    if (isReadOnly())
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 309);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.resultSet.updateBytes(paramInt, paramArrayOfByte);
  }

  public void updateDate(int paramInt, Date paramDate)
    throws SQLException
  {
    if (isReadOnly())
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 309);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.resultSet.updateDate(paramInt, paramDate);
  }

  public void updateTime(int paramInt, Time paramTime)
    throws SQLException
  {
    if (isReadOnly())
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 309);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.resultSet.updateTime(paramInt, paramTime);
  }

  public void updateObject(int paramInt, Object paramObject)
    throws SQLException
  {
    if (isReadOnly())
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 309);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.resultSet.updateObject(paramInt, paramObject);
  }

  public void updateObject(int paramInt1, Object paramObject, int paramInt2)
    throws SQLException
  {
    if (isReadOnly())
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 309);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.resultSet.updateObject(paramInt1, paramObject, paramInt2);
  }

  public void updateNull(String paramString)
    throws SQLException
  {
    if (isReadOnly())
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 309);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.resultSet.updateNull(paramString);
  }

  public void updateBoolean(String paramString, boolean paramBoolean)
    throws SQLException
  {
    if (isReadOnly())
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 309);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.resultSet.updateBoolean(paramString, paramBoolean);
  }

  public void updateByte(String paramString, byte paramByte)
    throws SQLException
  {
    if (isReadOnly())
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 309);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.resultSet.updateByte(paramString, paramByte);
  }

  public void updateShort(String paramString, short paramShort)
    throws SQLException
  {
    if (isReadOnly())
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 309);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.resultSet.updateShort(paramString, paramShort);
  }

  public void updateInt(String paramString, int paramInt)
    throws SQLException
  {
    if (isReadOnly())
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 309);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.resultSet.updateInt(paramString, paramInt);
  }

  public void updateLong(String paramString, long paramLong)
    throws SQLException
  {
    if (isReadOnly())
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 309);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.resultSet.updateLong(paramString, paramLong);
  }

  public void updateFloat(String paramString, float paramFloat)
    throws SQLException
  {
    if (isReadOnly())
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 309);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.resultSet.updateFloat(paramString, paramFloat);
  }

  public void updateDouble(String paramString, double paramDouble)
    throws SQLException
  {
    if (isReadOnly())
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 309);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.resultSet.updateDouble(paramString, paramDouble);
  }

  public void updateBigDecimal(String paramString, BigDecimal paramBigDecimal)
    throws SQLException
  {
    if (isReadOnly())
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 309);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.resultSet.updateBigDecimal(paramString, paramBigDecimal);
  }

  public void updateString(String paramString1, String paramString2)
    throws SQLException
  {
    if (isReadOnly())
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 309);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.resultSet.updateString(paramString1, paramString2);
  }

  public void updateBytes(String paramString, byte[] paramArrayOfByte)
    throws SQLException
  {
    if (isReadOnly())
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 309);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.resultSet.updateBytes(paramString, paramArrayOfByte);
  }

  public void updateDate(String paramString, Date paramDate)
    throws SQLException
  {
    if (isReadOnly())
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 309);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.resultSet.updateDate(paramString, paramDate);
  }

  public void updateTime(String paramString, Time paramTime)
    throws SQLException
  {
    if (isReadOnly())
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 309);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.resultSet.updateTime(paramString, paramTime);
  }

  public void updateObject(String paramString, Object paramObject)
    throws SQLException
  {
    if (isReadOnly())
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 309);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.resultSet.updateObject(paramString, paramObject);
  }

  public void updateObject(String paramString, Object paramObject, int paramInt)
    throws SQLException
  {
    if (isReadOnly())
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 309);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.resultSet.updateObject(paramString, paramObject, paramInt);
  }

  public void updateBinaryStream(String paramString, InputStream paramInputStream, int paramInt)
    throws SQLException
  {
    if (isReadOnly())
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 309);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.resultSet.updateBinaryStream(paramString, paramInputStream, paramInt);
  }

  public void updateAsciiStream(String paramString, InputStream paramInputStream, int paramInt)
    throws SQLException
  {
    if (isReadOnly())
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 309);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.resultSet.updateAsciiStream(paramString, paramInputStream, paramInt);
  }

  public void updateTimestamp(String paramString, Timestamp paramTimestamp)
    throws SQLException
  {
    if (isReadOnly())
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 309);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.resultSet.updateTimestamp(paramString, paramTimestamp);
  }

  public void updateCharacterStream(String paramString, Reader paramReader, int paramInt)
    throws SQLException
  {
    if (isReadOnly())
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 309);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.resultSet.updateCharacterStream(paramString, paramReader, paramInt);
  }

  public URL getURL(int paramInt)
    throws SQLException
  {
    return ((OracleResultSet)this.resultSet).getURL(paramInt);
  }

  public URL getURL(String paramString)
    throws SQLException
  {
    return ((OracleResultSet)this.resultSet).getURL(paramString);
  }

  public void updateRef(int paramInt, Ref paramRef)
    throws SQLException
  {
    if (isReadOnly())
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 309);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    ((OracleResultSet)this.resultSet).updateRef(paramInt, paramRef);
  }

  public void updateRef(String paramString, Ref paramRef)
    throws SQLException
  {
    if (isReadOnly())
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 309);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    ((OracleResultSet)this.resultSet).updateRef(paramString, paramRef);
  }

  public void updateBlob(int paramInt, Blob paramBlob)
    throws SQLException
  {
    if (isReadOnly())
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 309);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    ((OracleResultSet)this.resultSet).updateBlob(paramInt, paramBlob);
  }

  public void updateBlob(String paramString, Blob paramBlob)
    throws SQLException
  {
    if (isReadOnly())
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 309);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    ((OracleResultSet)this.resultSet).updateBlob(paramString, paramBlob);
  }

  public void updateClob(int paramInt, Clob paramClob)
    throws SQLException
  {
    if (isReadOnly())
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 309);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    ((OracleResultSet)this.resultSet).updateClob(paramInt, paramClob);
  }

  public void updateClob(String paramString, Clob paramClob)
    throws SQLException
  {
    if (isReadOnly())
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 309);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    ((OracleResultSet)this.resultSet).updateClob(paramString, paramClob);
  }

  public void updateArray(int paramInt, Array paramArray)
    throws SQLException
  {
    if (isReadOnly())
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 309);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    ((OracleResultSet)this.resultSet).updateArray(paramInt, paramArray);
  }

  public void updateArray(String paramString, Array paramArray)
    throws SQLException
  {
    if (isReadOnly())
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 309);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    ((OracleResultSet)this.resultSet).updateArray(paramString, paramArray);
  }

  public void commit()
    throws SQLException
  {
    if (this.connection != null)
    {
      this.connection.commit();
    }
    else
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 302);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  public void rollback()
    throws SQLException
  {
    if (this.connection != null)
    {
      this.connection.rollback();
    }
    else
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 302);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  public void rollback(Savepoint paramSavepoint)
    throws SQLException
  {
    if (this.connection != null)
    {
      this.connection.rollback(paramSavepoint);
    }
    else
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 302);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  public void oracleRollback(OracleSavepoint paramOracleSavepoint)
    throws SQLException
  {
    if (this.connection != null)
    {
      ((oracle.jdbc.OracleConnection)this.connection).oracleRollback(paramOracleSavepoint);
    }
    else
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 302);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  public boolean getAutoCommit()
    throws SQLException
  {
    if (this.connection != null)
    {
      return this.connection.getAutoCommit();
    }

    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 302);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setAutoCommit(boolean paramBoolean)
    throws SQLException
  {
    if (this.connection != null)
    {
      this.connection.setAutoCommit(paramBoolean);
    }
    else
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 302);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  public RowSetWarning getRowSetWarnings()
    throws SQLException
  {
    return null;
  }

  String getTableName()
    throws SQLException
  {
    return getMetaData().getTableName(getMatchColumnIndexes()[0]);
  }

  public Reader getNCharacterStream(int paramInt)
    throws SQLException
  {
    return this.resultSet.getNCharacterStream(paramInt);
  }

  public NClob getNClob(int paramInt)
    throws SQLException
  {
    return this.resultSet.getNClob(paramInt);
  }

  public NClob getNClob(String paramString)
    throws SQLException
  {
    return this.resultSet.getNClob(paramString);
  }

  public String getNString(int paramInt)
    throws SQLException
  {
    return this.resultSet.getNString(paramInt);
  }

  public RowId getRowId(int paramInt)
    throws SQLException
  {
    return this.resultSet.getRowId(paramInt);
  }

  public SQLXML getSQLXML(int paramInt)
    throws SQLException
  {
    return this.resultSet.getSQLXML(paramInt);
  }

  public Reader getNCharacterStream(String paramString)
    throws SQLException
  {
    return this.resultSet.getNCharacterStream(paramString);
  }

  public String getNString(String paramString)
    throws SQLException
  {
    return this.resultSet.getNString(paramString);
  }

  public RowId getRowId(String paramString)
    throws SQLException
  {
    return this.resultSet.getRowId(paramString);
  }

  public SQLXML getSQLXML(String paramString)
    throws SQLException
  {
    return this.resultSet.getSQLXML(paramString);
  }

  public void setAsciiStream(int paramInt, InputStream paramInputStream)
    throws SQLException
  {
    this.preparedStatement.setAsciiStream(paramInt, paramInputStream);
  }

  public void setBinaryStream(int paramInt, InputStream paramInputStream)
    throws SQLException
  {
    this.preparedStatement.setBinaryStream(paramInt, paramInputStream);
  }

  public void setBlob(int paramInt, InputStream paramInputStream)
    throws SQLException
  {
    this.preparedStatement.setBlob(paramInt, paramInputStream);
  }

  public void setBlob(int paramInt, InputStream paramInputStream, long paramLong)
    throws SQLException
  {
    this.preparedStatement.setBlob(paramInt, paramInputStream, paramLong);
  }

  public void setCharacterStream(int paramInt, Reader paramReader)
    throws SQLException
  {
    this.preparedStatement.setCharacterStream(paramInt, paramReader);
  }

  public void setClob(int paramInt, Reader paramReader)
    throws SQLException
  {
    this.preparedStatement.setClob(paramInt, paramReader);
  }

  public void setClob(int paramInt, Reader paramReader, long paramLong)
    throws SQLException
  {
    this.preparedStatement.setClob(paramInt, paramReader, paramLong);
  }

  public void setNCharacterStream(int paramInt, Reader paramReader)
    throws SQLException
  {
    this.preparedStatement.setNCharacterStream(paramInt, paramReader);
  }

  public void setNCharacterStream(int paramInt, Reader paramReader, long paramLong)
    throws SQLException
  {
    this.preparedStatement.setNCharacterStream(paramInt, paramReader, paramLong);
  }

  public void setNClob(int paramInt, NClob paramNClob)
    throws SQLException
  {
    this.preparedStatement.setNClob(paramInt, paramNClob);
  }

  public void setNClob(int paramInt, Reader paramReader)
    throws SQLException
  {
    this.preparedStatement.setNClob(paramInt, paramReader);
  }

  public void setNClob(int paramInt, Reader paramReader, long paramLong)
    throws SQLException
  {
    this.preparedStatement.setNClob(paramInt, paramReader);
  }

  public void setNString(int paramInt, String paramString)
    throws SQLException
  {
    this.preparedStatement.setNString(paramInt, paramString);
  }

  public void setRowId(int paramInt, RowId paramRowId)
    throws SQLException
  {
    this.preparedStatement.setRowId(paramInt, paramRowId);
  }

  public void setSQLXML(int paramInt, SQLXML paramSQLXML)
    throws SQLException
  {
    this.preparedStatement.setSQLXML(paramInt, paramSQLXML);
  }

  public void setURL(int paramInt, URL paramURL)
    throws SQLException
  {
    this.preparedStatement.setURL(paramInt, paramURL);
  }

  public void setArray(String paramString, Array paramArray)
    throws SQLException
  {
    ((OraclePreparedStatement)this.preparedStatement).setArrayAtName(paramString, paramArray);
  }

  public void setBigDecimal(String paramString, BigDecimal paramBigDecimal)
    throws SQLException
  {
    ((OraclePreparedStatement)this.preparedStatement).setBigDecimalAtName(paramString, paramBigDecimal);
  }

  public void setBlob(String paramString, Blob paramBlob)
    throws SQLException
  {
    ((OraclePreparedStatement)this.preparedStatement).setBlobAtName(paramString, paramBlob);
  }

  public void setBoolean(String paramString, boolean paramBoolean)
    throws SQLException
  {
    ((OraclePreparedStatement)this.preparedStatement).setBooleanAtName(paramString, paramBoolean);
  }

  public void setByte(String paramString, byte paramByte)
    throws SQLException
  {
    ((OraclePreparedStatement)this.preparedStatement).setByteAtName(paramString, paramByte);
  }

  public void setBytes(String paramString, byte[] paramArrayOfByte)
    throws SQLException
  {
    ((OraclePreparedStatement)this.preparedStatement).setBytesAtName(paramString, paramArrayOfByte);
  }

  public void setClob(String paramString, Clob paramClob)
    throws SQLException
  {
    ((OraclePreparedStatement)this.preparedStatement).setClobAtName(paramString, paramClob);
  }

  public void setDate(String paramString, Date paramDate)
    throws SQLException
  {
    ((OraclePreparedStatement)this.preparedStatement).setDateAtName(paramString, paramDate);
  }

  public void setDate(String paramString, Date paramDate, Calendar paramCalendar)
    throws SQLException
  {
    ((OraclePreparedStatement)this.preparedStatement).setDateAtName(paramString, paramDate, paramCalendar);
  }

  public void setDouble(String paramString, double paramDouble)
    throws SQLException
  {
    ((OraclePreparedStatement)this.preparedStatement).setDoubleAtName(paramString, paramDouble);
  }

  public void setFloat(String paramString, float paramFloat)
    throws SQLException
  {
    ((OraclePreparedStatement)this.preparedStatement).setFloatAtName(paramString, paramFloat);
  }

  public void setInt(String paramString, int paramInt)
    throws SQLException
  {
    ((OraclePreparedStatement)this.preparedStatement).setIntAtName(paramString, paramInt);
  }

  public void setLong(String paramString, long paramLong)
    throws SQLException
  {
    ((OraclePreparedStatement)this.preparedStatement).setLongAtName(paramString, paramLong);
  }

  public void setNClob(String paramString, NClob paramNClob)
    throws SQLException
  {
    ((OraclePreparedStatement)this.preparedStatement).setNClobAtName(paramString, paramNClob);
  }

  public void setNString(String paramString1, String paramString2)
    throws SQLException
  {
    ((OraclePreparedStatement)this.preparedStatement).setNStringAtName(paramString1, paramString2);
  }

  public void setObject(String paramString, Object paramObject)
    throws SQLException
  {
    ((OraclePreparedStatement)this.preparedStatement).setObjectAtName(paramString, paramObject);
  }

  public void setObject(String paramString, Object paramObject, int paramInt)
    throws SQLException
  {
    ((OraclePreparedStatement)this.preparedStatement).setObjectAtName(paramString, paramObject, paramInt);
  }

  public void setRef(String paramString, Ref paramRef)
    throws SQLException
  {
    ((OraclePreparedStatement)this.preparedStatement).setRefAtName(paramString, paramRef);
  }

  public void setRowId(String paramString, RowId paramRowId)
    throws SQLException
  {
    ((OraclePreparedStatement)this.preparedStatement).setRowIdAtName(paramString, paramRowId);
  }

  public void setShort(String paramString, short paramShort)
    throws SQLException
  {
    ((OraclePreparedStatement)this.preparedStatement).setShortAtName(paramString, paramShort);
  }

  public void setSQLXML(String paramString, SQLXML paramSQLXML)
    throws SQLException
  {
    ((OraclePreparedStatement)this.preparedStatement).setSQLXMLAtName(paramString, paramSQLXML);
  }

  public void setString(String paramString1, String paramString2)
    throws SQLException
  {
    ((OraclePreparedStatement)this.preparedStatement).setStringAtName(paramString1, paramString2);
  }

  public void setTime(String paramString, Time paramTime)
    throws SQLException
  {
    ((OraclePreparedStatement)this.preparedStatement).setTimeAtName(paramString, paramTime);
  }

  public void setTime(String paramString, Time paramTime, Calendar paramCalendar)
    throws SQLException
  {
    ((OraclePreparedStatement)this.preparedStatement).setTimeAtName(paramString, paramTime, paramCalendar);
  }

  public void setTimestamp(String paramString, Timestamp paramTimestamp)
    throws SQLException
  {
    ((OraclePreparedStatement)this.preparedStatement).setTimestampAtName(paramString, paramTimestamp);
  }

  public void setTimestamp(String paramString, Timestamp paramTimestamp, Calendar paramCalendar)
    throws SQLException
  {
    ((OraclePreparedStatement)this.preparedStatement).setTimestampAtName(paramString, paramTimestamp, paramCalendar);
  }

  public void setURL(String paramString, URL paramURL)
    throws SQLException
  {
    ((OraclePreparedStatement)this.preparedStatement).setURLAtName(paramString, paramURL);
  }

  public void setBlob(String paramString, InputStream paramInputStream)
    throws SQLException
  {
    ((OraclePreparedStatement)this.preparedStatement).setBlobAtName(paramString, paramInputStream);
  }

  public void setBlob(String paramString, InputStream paramInputStream, long paramLong)
    throws SQLException
  {
    ((OraclePreparedStatement)this.preparedStatement).setBlobAtName(paramString, paramInputStream, paramLong);
  }

  public void setClob(String paramString, Reader paramReader)
    throws SQLException
  {
    ((OraclePreparedStatement)this.preparedStatement).setClobAtName(paramString, paramReader);
  }

  public void setClob(String paramString, Reader paramReader, long paramLong)
    throws SQLException
  {
    ((OraclePreparedStatement)this.preparedStatement).setClobAtName(paramString, paramReader, paramLong);
  }

  public void setNClob(String paramString, Reader paramReader)
    throws SQLException
  {
    ((OraclePreparedStatement)this.preparedStatement).setNClobAtName(paramString, paramReader);
  }

  public void setNClob(String paramString, Reader paramReader, long paramLong)
    throws SQLException
  {
    ((OraclePreparedStatement)this.preparedStatement).setNClobAtName(paramString, paramReader, paramLong);
  }

  public void setAsciiStream(String paramString, InputStream paramInputStream)
    throws SQLException
  {
    ((OraclePreparedStatement)this.preparedStatement).setAsciiStreamAtName(paramString, paramInputStream);
  }

  public void setAsciiStream(String paramString, InputStream paramInputStream, int paramInt)
    throws SQLException
  {
    ((OraclePreparedStatement)this.preparedStatement).setAsciiStreamAtName(paramString, paramInputStream, paramInt);
  }

  public void setAsciiStream(String paramString, InputStream paramInputStream, long paramLong)
    throws SQLException
  {
    ((OraclePreparedStatement)this.preparedStatement).setAsciiStreamAtName(paramString, paramInputStream, paramLong);
  }

  public void setBinaryStream(String paramString, InputStream paramInputStream)
    throws SQLException
  {
    ((OraclePreparedStatement)this.preparedStatement).setBinaryStreamAtName(paramString, paramInputStream);
  }

  public void setBinaryStream(String paramString, InputStream paramInputStream, int paramInt)
    throws SQLException
  {
    ((OraclePreparedStatement)this.preparedStatement).setBinaryStreamAtName(paramString, paramInputStream, paramInt);
  }

  public void setBinaryStream(String paramString, InputStream paramInputStream, long paramLong)
    throws SQLException
  {
    ((OraclePreparedStatement)this.preparedStatement).setBinaryStreamAtName(paramString, paramInputStream, paramLong);
  }

  public void setCharacterStream(String paramString, Reader paramReader)
    throws SQLException
  {
    ((OraclePreparedStatement)this.preparedStatement).setCharacterStreamAtName(paramString, paramReader);
  }

  public void setCharacterStream(String paramString, Reader paramReader, int paramInt)
    throws SQLException
  {
    ((OraclePreparedStatement)this.preparedStatement).setCharacterStreamAtName(paramString, paramReader, paramInt);
  }

  public void setCharacterStream(String paramString, Reader paramReader, long paramLong)
    throws SQLException
  {
    ((OraclePreparedStatement)this.preparedStatement).setCharacterStreamAtName(paramString, paramReader, paramLong);
  }

  public void setNCharacterStream(String paramString, Reader paramReader)
    throws SQLException
  {
    ((OraclePreparedStatement)this.preparedStatement).setNCharacterStreamAtName(paramString, paramReader);
  }

  public void setNCharacterStream(String paramString, Reader paramReader, long paramLong)
    throws SQLException
  {
    ((OraclePreparedStatement)this.preparedStatement).setNCharacterStreamAtName(paramString, paramReader, paramLong);
  }

  public void setUnicodeStream(String paramString, InputStream paramInputStream, int paramInt)
    throws SQLException
  {
    ((OraclePreparedStatement)this.preparedStatement).setUnicodeStreamAtName(paramString, paramInputStream, paramInt);
  }

  public void setNull(String paramString, int paramInt)
    throws SQLException
  {
    ((OraclePreparedStatement)this.preparedStatement).setNullAtName(paramString, paramInt);
  }

  public void setNull(String paramString1, int paramInt, String paramString2)
    throws SQLException
  {
    ((OraclePreparedStatement)this.preparedStatement).setNullAtName(paramString1, paramInt, paramString2);
  }

  public void setObject(String paramString, Object paramObject, int paramInt1, int paramInt2)
    throws SQLException
  {
    ((OraclePreparedStatement)this.preparedStatement).setObjectAtName(paramString, paramObject, paramInt1, paramInt2);
  }

  public void updateAsciiStream(int paramInt, InputStream paramInputStream)
    throws SQLException
  {
    if (isReadOnly())
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 309);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.resultSet.updateAsciiStream(paramInt, paramInputStream);
  }

  public void updateAsciiStream(int paramInt, InputStream paramInputStream, long paramLong)
    throws SQLException
  {
    if (isReadOnly())
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 309);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.resultSet.updateAsciiStream(paramInt, paramInputStream, paramLong);
  }

  public void updateAsciiStream(String paramString, InputStream paramInputStream)
    throws SQLException
  {
    if (isReadOnly())
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 309);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.resultSet.updateAsciiStream(paramString, paramInputStream);
  }

  public void updateAsciiStream(String paramString, InputStream paramInputStream, long paramLong)
    throws SQLException
  {
    if (isReadOnly())
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 309);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.resultSet.updateAsciiStream(paramString, paramInputStream, paramLong);
  }

  public void updateBinaryStream(int paramInt, InputStream paramInputStream)
    throws SQLException
  {
    if (isReadOnly())
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 309);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.resultSet.updateBinaryStream(paramInt, paramInputStream);
  }

  public void updateBinaryStream(int paramInt, InputStream paramInputStream, long paramLong)
    throws SQLException
  {
    if (isReadOnly())
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 309);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.resultSet.updateBinaryStream(paramInt, paramInputStream, paramLong);
  }

  public void updateBinaryStream(String paramString, InputStream paramInputStream)
    throws SQLException
  {
    if (isReadOnly())
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 309);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.resultSet.updateBinaryStream(paramString, paramInputStream);
  }

  public void updateBinaryStream(String paramString, InputStream paramInputStream, long paramLong)
    throws SQLException
  {
    if (isReadOnly())
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 309);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.resultSet.updateBinaryStream(paramString, paramInputStream, paramLong);
  }

  public void updateBlob(int paramInt, InputStream paramInputStream)
    throws SQLException
  {
    if (isReadOnly())
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 309);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.resultSet.updateBlob(paramInt, paramInputStream);
  }

  public void updateBlob(int paramInt, InputStream paramInputStream, long paramLong)
    throws SQLException
  {
    if (isReadOnly())
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 309);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.resultSet.updateBlob(paramInt, paramInputStream, paramLong);
  }

  public void updateBlob(String paramString, InputStream paramInputStream)
    throws SQLException
  {
    if (isReadOnly())
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 309);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.resultSet.updateBlob(paramString, paramInputStream);
  }

  public void updateBlob(String paramString, InputStream paramInputStream, long paramLong)
    throws SQLException
  {
    if (isReadOnly())
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 309);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.resultSet.updateBlob(paramString, paramInputStream, paramLong);
  }

  public void updateCharacterStream(int paramInt, Reader paramReader)
    throws SQLException
  {
    if (isReadOnly())
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 309);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.resultSet.updateCharacterStream(paramInt, paramReader);
  }

  public void updateCharacterStream(int paramInt, Reader paramReader, long paramLong)
    throws SQLException
  {
    if (isReadOnly())
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 309);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.resultSet.updateCharacterStream(paramInt, paramReader, paramLong);
  }

  public void updateCharacterStream(String paramString, Reader paramReader)
    throws SQLException
  {
    if (isReadOnly())
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 309);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.resultSet.updateCharacterStream(paramString, paramReader);
  }

  public void updateCharacterStream(String paramString, Reader paramReader, long paramLong)
    throws SQLException
  {
    if (isReadOnly())
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 309);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.resultSet.updateCharacterStream(paramString, paramReader, paramLong);
  }

  public void updateClob(int paramInt, Reader paramReader)
    throws SQLException
  {
    if (isReadOnly())
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 309);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.resultSet.updateClob(paramInt, paramReader);
  }

  public void updateClob(int paramInt, Reader paramReader, long paramLong)
    throws SQLException
  {
    if (isReadOnly())
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 309);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.resultSet.updateClob(paramInt, paramReader, paramLong);
  }

  public void updateClob(String paramString, Reader paramReader)
    throws SQLException
  {
    if (isReadOnly())
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 309);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.resultSet.updateClob(paramString, paramReader);
  }

  public void updateClob(String paramString, Reader paramReader, long paramLong)
    throws SQLException
  {
    if (isReadOnly())
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 309);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.resultSet.updateClob(paramString, paramReader, paramLong);
  }

  public void updateNCharacterStream(int paramInt, Reader paramReader)
    throws SQLException
  {
    if (isReadOnly())
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 309);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.resultSet.updateNCharacterStream(paramInt, paramReader);
  }

  public void updateNCharacterStream(int paramInt, Reader paramReader, long paramLong)
    throws SQLException
  {
    if (isReadOnly())
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 309);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.resultSet.updateNCharacterStream(paramInt, paramReader, paramLong);
  }

  public void updateNCharacterStream(String paramString, Reader paramReader)
    throws SQLException
  {
    if (isReadOnly())
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 309);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.resultSet.updateNCharacterStream(paramString, paramReader);
  }

  public void updateNCharacterStream(String paramString, Reader paramReader, long paramLong)
    throws SQLException
  {
    if (isReadOnly())
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 309);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.resultSet.updateNCharacterStream(paramString, paramReader, paramLong);
  }

  public void updateNClob(int paramInt, NClob paramNClob)
    throws SQLException
  {
    if (isReadOnly())
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 309);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.resultSet.updateNClob(paramInt, paramNClob);
  }

  public void updateNClob(int paramInt, Reader paramReader)
    throws SQLException
  {
    if (isReadOnly())
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 309);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.resultSet.updateNClob(paramInt, paramReader);
  }

  public void updateNClob(int paramInt, Reader paramReader, long paramLong)
    throws SQLException
  {
    if (isReadOnly())
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 309);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.resultSet.updateNClob(paramInt, paramReader, paramLong);
  }

  public void updateNClob(String paramString, NClob paramNClob)
    throws SQLException
  {
    if (isReadOnly())
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 309);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.resultSet.updateNClob(paramString, paramNClob);
  }

  public void updateNClob(String paramString, Reader paramReader)
    throws SQLException
  {
    if (isReadOnly())
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 309);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.resultSet.updateNClob(paramString, paramReader);
  }

  public void updateNClob(String paramString, Reader paramReader, long paramLong)
    throws SQLException
  {
    if (isReadOnly())
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 309);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.resultSet.updateNClob(paramString, paramReader, paramLong);
  }

  public void updateNString(int paramInt, String paramString)
    throws SQLException
  {
    if (isReadOnly())
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 309);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.resultSet.updateNString(paramInt, paramString);
  }

  public void updateNString(String paramString1, String paramString2)
    throws SQLException
  {
    if (isReadOnly())
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 309);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.resultSet.updateNString(paramString1, paramString2);
  }

  public void updateRowId(int paramInt, RowId paramRowId)
    throws SQLException
  {
    if (isReadOnly())
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 309);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.resultSet.updateRowId(paramInt, paramRowId);
  }

  public void updateRowId(String paramString, RowId paramRowId)
    throws SQLException
  {
    if (isReadOnly())
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 309);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.resultSet.updateRowId(paramString, paramRowId);
  }

  public void updateSQLXML(int paramInt, SQLXML paramSQLXML)
    throws SQLException
  {
    if (isReadOnly())
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 309);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.resultSet.updateSQLXML(paramInt, paramSQLXML);
  }

  public void updateSQLXML(String paramString, SQLXML paramSQLXML)
    throws SQLException
  {
    if (isReadOnly())
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 309);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.resultSet.updateSQLXML(paramString, paramSQLXML);
  }

  protected oracle.jdbc.internal.OracleConnection getConnectionDuringExceptionHandling()
  {
    try
    {
      return (oracle.jdbc.internal.OracleConnection)this.connection;
    }
    catch (Exception localException) {
    }
    return null;
  }
}