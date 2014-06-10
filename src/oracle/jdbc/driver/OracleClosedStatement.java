package oracle.jdbc.driver;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.NClob;
import java.sql.ParameterMetaData;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;
import oracle.jdbc.OracleParameterMetaData;
import oracle.jdbc.OracleResultSetCache;
import oracle.jdbc.dcn.DatabaseChangeRegistration;
import oracle.jdbc.internal.OracleCallableStatement;
import oracle.jdbc.internal.OracleConnection;
import oracle.sql.ARRAY;
import oracle.sql.BFILE;
import oracle.sql.BINARY_DOUBLE;
import oracle.sql.BINARY_FLOAT;
import oracle.sql.BLOB;
import oracle.sql.CHAR;
import oracle.sql.CLOB;
import oracle.sql.CustomDatum;
import oracle.sql.CustomDatumFactory;
import oracle.sql.DATE;
import oracle.sql.Datum;
import oracle.sql.INTERVALDS;
import oracle.sql.INTERVALYM;
import oracle.sql.NUMBER;
import oracle.sql.OPAQUE;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.RAW;
import oracle.sql.REF;
import oracle.sql.ROWID;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;
import oracle.sql.TIMESTAMP;
import oracle.sql.TIMESTAMPLTZ;
import oracle.sql.TIMESTAMPTZ;

class OracleClosedStatement
  implements OracleCallableStatement
{
  private OracleStatement wrapper;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  public void setArray(int paramInt, Array paramArray)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setArrayAtName(String paramString, Array paramArray)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setArray(String paramString, Array paramArray)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setBigDecimal(int paramInt, BigDecimal paramBigDecimal)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setBigDecimalAtName(String paramString, BigDecimal paramBigDecimal)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setBigDecimal(String paramString, BigDecimal paramBigDecimal)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setBlob(int paramInt, Blob paramBlob)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setBlobAtName(String paramString, Blob paramBlob)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setBlob(String paramString, Blob paramBlob)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setBoolean(int paramInt, boolean paramBoolean)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setBooleanAtName(String paramString, boolean paramBoolean)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setBoolean(String paramString, boolean paramBoolean)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setByte(int paramInt, byte paramByte)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setByteAtName(String paramString, byte paramByte)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setByte(String paramString, byte paramByte)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setBytes(int paramInt, byte[] paramArrayOfByte)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setBytesAtName(String paramString, byte[] paramArrayOfByte)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setBytes(String paramString, byte[] paramArrayOfByte)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setClob(int paramInt, Clob paramClob)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setClobAtName(String paramString, Clob paramClob)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setClob(String paramString, Clob paramClob)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setDate(int paramInt, Date paramDate)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setDateAtName(String paramString, Date paramDate)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setDate(String paramString, Date paramDate)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setDate(int paramInt, Date paramDate, Calendar paramCalendar)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setDateAtName(String paramString, Date paramDate, Calendar paramCalendar)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setDate(String paramString, Date paramDate, Calendar paramCalendar)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setDouble(int paramInt, double paramDouble)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setDoubleAtName(String paramString, double paramDouble)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setDouble(String paramString, double paramDouble)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setFloat(int paramInt, float paramFloat)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setFloatAtName(String paramString, float paramFloat)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setFloat(String paramString, float paramFloat)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setInt(int paramInt1, int paramInt2)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setIntAtName(String paramString, int paramInt)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setInt(String paramString, int paramInt)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setLong(int paramInt, long paramLong)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setLongAtName(String paramString, long paramLong)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setLong(String paramString, long paramLong)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setNClob(int paramInt, NClob paramNClob)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setNClobAtName(String paramString, NClob paramNClob)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setNClob(String paramString, NClob paramNClob)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setNString(int paramInt, String paramString)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setNStringAtName(String paramString1, String paramString2)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setNString(String paramString1, String paramString2)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setObject(int paramInt, Object paramObject)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setObjectAtName(String paramString, Object paramObject)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setObject(String paramString, Object paramObject)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setObject(int paramInt1, Object paramObject, int paramInt2)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setObjectAtName(String paramString, Object paramObject, int paramInt)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setObject(String paramString, Object paramObject, int paramInt)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setRef(int paramInt, Ref paramRef)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setRefAtName(String paramString, Ref paramRef)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setRef(String paramString, Ref paramRef)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setRowId(int paramInt, RowId paramRowId)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setRowIdAtName(String paramString, RowId paramRowId)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setRowId(String paramString, RowId paramRowId)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setShort(int paramInt, short paramShort)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setShortAtName(String paramString, short paramShort)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setShort(String paramString, short paramShort)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setSQLXML(int paramInt, SQLXML paramSQLXML)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setSQLXMLAtName(String paramString, SQLXML paramSQLXML)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setSQLXML(String paramString, SQLXML paramSQLXML)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setString(int paramInt, String paramString)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setStringAtName(String paramString1, String paramString2)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setString(String paramString1, String paramString2)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setTime(int paramInt, Time paramTime)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setTimeAtName(String paramString, Time paramTime)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setTime(String paramString, Time paramTime)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setTime(int paramInt, Time paramTime, Calendar paramCalendar)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setTimeAtName(String paramString, Time paramTime, Calendar paramCalendar)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setTime(String paramString, Time paramTime, Calendar paramCalendar)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setTimestamp(int paramInt, Timestamp paramTimestamp)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setTimestampAtName(String paramString, Timestamp paramTimestamp)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setTimestamp(String paramString, Timestamp paramTimestamp)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setTimestamp(int paramInt, Timestamp paramTimestamp, Calendar paramCalendar)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setTimestampAtName(String paramString, Timestamp paramTimestamp, Calendar paramCalendar)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setTimestamp(String paramString, Timestamp paramTimestamp, Calendar paramCalendar)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setURL(int paramInt, URL paramURL)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setURLAtName(String paramString, URL paramURL)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setURL(String paramString, URL paramURL)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setARRAY(int paramInt, ARRAY paramARRAY)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setARRAYAtName(String paramString, ARRAY paramARRAY)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setARRAY(String paramString, ARRAY paramARRAY)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setBFILE(int paramInt, BFILE paramBFILE)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setBFILEAtName(String paramString, BFILE paramBFILE)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setBFILE(String paramString, BFILE paramBFILE)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setBfile(int paramInt, BFILE paramBFILE)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setBfileAtName(String paramString, BFILE paramBFILE)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setBfile(String paramString, BFILE paramBFILE)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setBinaryFloat(int paramInt, float paramFloat)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setBinaryFloatAtName(String paramString, float paramFloat)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setBinaryFloat(String paramString, float paramFloat)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setBinaryFloat(int paramInt, BINARY_FLOAT paramBINARY_FLOAT)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setBinaryFloatAtName(String paramString, BINARY_FLOAT paramBINARY_FLOAT)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setBinaryFloat(String paramString, BINARY_FLOAT paramBINARY_FLOAT)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setBinaryDouble(int paramInt, double paramDouble)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setBinaryDoubleAtName(String paramString, double paramDouble)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setBinaryDouble(String paramString, double paramDouble)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setBinaryDouble(int paramInt, BINARY_DOUBLE paramBINARY_DOUBLE)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setBinaryDoubleAtName(String paramString, BINARY_DOUBLE paramBINARY_DOUBLE)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setBinaryDouble(String paramString, BINARY_DOUBLE paramBINARY_DOUBLE)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setBLOB(int paramInt, BLOB paramBLOB)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setBLOBAtName(String paramString, BLOB paramBLOB)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setBLOB(String paramString, BLOB paramBLOB)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setCHAR(int paramInt, CHAR paramCHAR)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setCHARAtName(String paramString, CHAR paramCHAR)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setCHAR(String paramString, CHAR paramCHAR)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setCLOB(int paramInt, CLOB paramCLOB)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setCLOBAtName(String paramString, CLOB paramCLOB)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setCLOB(String paramString, CLOB paramCLOB)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setCursor(int paramInt, ResultSet paramResultSet)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setCursorAtName(String paramString, ResultSet paramResultSet)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setCursor(String paramString, ResultSet paramResultSet)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setCustomDatum(int paramInt, CustomDatum paramCustomDatum)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setCustomDatumAtName(String paramString, CustomDatum paramCustomDatum)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setCustomDatum(String paramString, CustomDatum paramCustomDatum)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setDATE(int paramInt, DATE paramDATE)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setDATEAtName(String paramString, DATE paramDATE)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setDATE(String paramString, DATE paramDATE)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setFixedCHAR(int paramInt, String paramString)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setFixedCHARAtName(String paramString1, String paramString2)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setFixedCHAR(String paramString1, String paramString2)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setINTERVALDS(int paramInt, INTERVALDS paramINTERVALDS)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setINTERVALDSAtName(String paramString, INTERVALDS paramINTERVALDS)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setINTERVALDS(String paramString, INTERVALDS paramINTERVALDS)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setINTERVALYM(int paramInt, INTERVALYM paramINTERVALYM)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setINTERVALYMAtName(String paramString, INTERVALYM paramINTERVALYM)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setINTERVALYM(String paramString, INTERVALYM paramINTERVALYM)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setNUMBER(int paramInt, NUMBER paramNUMBER)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setNUMBERAtName(String paramString, NUMBER paramNUMBER)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setNUMBER(String paramString, NUMBER paramNUMBER)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setOPAQUE(int paramInt, OPAQUE paramOPAQUE)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setOPAQUEAtName(String paramString, OPAQUE paramOPAQUE)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setOPAQUE(String paramString, OPAQUE paramOPAQUE)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setOracleObject(int paramInt, Datum paramDatum)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setOracleObjectAtName(String paramString, Datum paramDatum)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setOracleObject(String paramString, Datum paramDatum)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setORAData(int paramInt, ORAData paramORAData)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setORADataAtName(String paramString, ORAData paramORAData)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setORAData(String paramString, ORAData paramORAData)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setRAW(int paramInt, RAW paramRAW)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setRAWAtName(String paramString, RAW paramRAW)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setRAW(String paramString, RAW paramRAW)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setREF(int paramInt, REF paramREF)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setREFAtName(String paramString, REF paramREF)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setREF(String paramString, REF paramREF)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setRefType(int paramInt, REF paramREF)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setRefTypeAtName(String paramString, REF paramREF)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setRefType(String paramString, REF paramREF)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setROWID(int paramInt, ROWID paramROWID)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setROWIDAtName(String paramString, ROWID paramROWID)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setROWID(String paramString, ROWID paramROWID)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setSTRUCT(int paramInt, STRUCT paramSTRUCT)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setSTRUCTAtName(String paramString, STRUCT paramSTRUCT)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setSTRUCT(String paramString, STRUCT paramSTRUCT)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setTIMESTAMPLTZ(int paramInt, TIMESTAMPLTZ paramTIMESTAMPLTZ)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setTIMESTAMPLTZAtName(String paramString, TIMESTAMPLTZ paramTIMESTAMPLTZ)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setTIMESTAMPLTZ(String paramString, TIMESTAMPLTZ paramTIMESTAMPLTZ)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setTIMESTAMPTZ(int paramInt, TIMESTAMPTZ paramTIMESTAMPTZ)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setTIMESTAMPTZAtName(String paramString, TIMESTAMPTZ paramTIMESTAMPTZ)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setTIMESTAMPTZ(String paramString, TIMESTAMPTZ paramTIMESTAMPTZ)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setTIMESTAMP(int paramInt, TIMESTAMP paramTIMESTAMP)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setTIMESTAMPAtName(String paramString, TIMESTAMP paramTIMESTAMP)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setTIMESTAMP(String paramString, TIMESTAMP paramTIMESTAMP)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setBlob(int paramInt, InputStream paramInputStream)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setBlobAtName(String paramString, InputStream paramInputStream)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setBlob(String paramString, InputStream paramInputStream)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setBlob(int paramInt, InputStream paramInputStream, long paramLong)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setBlobAtName(String paramString, InputStream paramInputStream, long paramLong)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setBlob(String paramString, InputStream paramInputStream, long paramLong)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setClob(int paramInt, Reader paramReader)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setClobAtName(String paramString, Reader paramReader)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setClob(String paramString, Reader paramReader)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setClob(int paramInt, Reader paramReader, long paramLong)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setClobAtName(String paramString, Reader paramReader, long paramLong)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setClob(String paramString, Reader paramReader, long paramLong)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setNClob(int paramInt, Reader paramReader)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setNClobAtName(String paramString, Reader paramReader)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setNClob(String paramString, Reader paramReader)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setNClob(int paramInt, Reader paramReader, long paramLong)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setNClobAtName(String paramString, Reader paramReader, long paramLong)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setNClob(String paramString, Reader paramReader, long paramLong)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setAsciiStream(int paramInt, InputStream paramInputStream)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setAsciiStreamAtName(String paramString, InputStream paramInputStream)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setAsciiStream(String paramString, InputStream paramInputStream)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setAsciiStream(int paramInt1, InputStream paramInputStream, int paramInt2)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setAsciiStreamAtName(String paramString, InputStream paramInputStream, int paramInt)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setAsciiStream(String paramString, InputStream paramInputStream, int paramInt)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setAsciiStream(int paramInt, InputStream paramInputStream, long paramLong)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setAsciiStreamAtName(String paramString, InputStream paramInputStream, long paramLong)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setAsciiStream(String paramString, InputStream paramInputStream, long paramLong)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setBinaryStream(int paramInt, InputStream paramInputStream)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setBinaryStreamAtName(String paramString, InputStream paramInputStream)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setBinaryStream(String paramString, InputStream paramInputStream)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setBinaryStream(int paramInt1, InputStream paramInputStream, int paramInt2)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setBinaryStreamAtName(String paramString, InputStream paramInputStream, int paramInt)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setBinaryStream(String paramString, InputStream paramInputStream, int paramInt)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setBinaryStream(int paramInt, InputStream paramInputStream, long paramLong)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setBinaryStreamAtName(String paramString, InputStream paramInputStream, long paramLong)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setBinaryStream(String paramString, InputStream paramInputStream, long paramLong)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setCharacterStream(int paramInt, Reader paramReader)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setCharacterStreamAtName(String paramString, Reader paramReader)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setCharacterStream(String paramString, Reader paramReader)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setCharacterStream(int paramInt1, Reader paramReader, int paramInt2)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setCharacterStreamAtName(String paramString, Reader paramReader, int paramInt)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setCharacterStream(String paramString, Reader paramReader, int paramInt)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setCharacterStream(int paramInt, Reader paramReader, long paramLong)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setCharacterStreamAtName(String paramString, Reader paramReader, long paramLong)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setCharacterStream(String paramString, Reader paramReader, long paramLong)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setNCharacterStream(int paramInt, Reader paramReader)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setNCharacterStreamAtName(String paramString, Reader paramReader)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setNCharacterStream(String paramString, Reader paramReader)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setNCharacterStream(int paramInt, Reader paramReader, long paramLong)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setNCharacterStreamAtName(String paramString, Reader paramReader, long paramLong)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setNCharacterStream(String paramString, Reader paramReader, long paramLong)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setUnicodeStream(int paramInt1, InputStream paramInputStream, int paramInt2)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setUnicodeStreamAtName(String paramString, InputStream paramInputStream, int paramInt)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setUnicodeStream(String paramString, InputStream paramInputStream, int paramInt)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public Array getArray(int paramInt)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public Array getArray(String paramString)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public BigDecimal getBigDecimal(int paramInt)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public BigDecimal getBigDecimal(String paramString)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public BigDecimal getBigDecimal(int paramInt1, int paramInt2)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public BigDecimal getBigDecimal(String paramString, int paramInt)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public Blob getBlob(int paramInt)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public Blob getBlob(String paramString)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public boolean getBoolean(int paramInt)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public boolean getBoolean(String paramString)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public byte getByte(int paramInt)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public byte getByte(String paramString)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public byte[] getBytes(int paramInt)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public byte[] getBytes(String paramString)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public Clob getClob(int paramInt)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public Clob getClob(String paramString)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public Date getDate(int paramInt)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public Date getDate(String paramString)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public Date getDate(int paramInt, Calendar paramCalendar)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public Date getDate(String paramString, Calendar paramCalendar)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public double getDouble(int paramInt)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public double getDouble(String paramString)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public float getFloat(int paramInt)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public float getFloat(String paramString)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public int getInt(int paramInt)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public int getInt(String paramString)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public long getLong(int paramInt)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public long getLong(String paramString)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public NClob getNClob(int paramInt)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public NClob getNClob(String paramString)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public String getNString(int paramInt)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public String getNString(String paramString)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public Object getObject(int paramInt)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public Object getObject(String paramString)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public Object getObject(int paramInt, Map paramMap)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public Object getObject(String paramString, Map paramMap)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public Ref getRef(int paramInt)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public Ref getRef(String paramString)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public RowId getRowId(int paramInt)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public RowId getRowId(String paramString)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public short getShort(int paramInt)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public short getShort(String paramString)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public SQLXML getSQLXML(int paramInt)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public SQLXML getSQLXML(String paramString)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public String getString(int paramInt)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public String getString(String paramString)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public Time getTime(int paramInt)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public Time getTime(String paramString)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public Time getTime(int paramInt, Calendar paramCalendar)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public Time getTime(String paramString, Calendar paramCalendar)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public Timestamp getTimestamp(int paramInt)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public Timestamp getTimestamp(String paramString)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public Timestamp getTimestamp(int paramInt, Calendar paramCalendar)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public Timestamp getTimestamp(String paramString, Calendar paramCalendar)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public URL getURL(int paramInt)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public URL getURL(String paramString)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public ARRAY getARRAY(int paramInt)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public ARRAY getARRAY(String paramString)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public BFILE getBFILE(int paramInt)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public BFILE getBFILE(String paramString)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public BFILE getBfile(int paramInt)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public BFILE getBfile(String paramString)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public BLOB getBLOB(int paramInt)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public BLOB getBLOB(String paramString)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public CHAR getCHAR(int paramInt)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public CHAR getCHAR(String paramString)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public CLOB getCLOB(int paramInt)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public CLOB getCLOB(String paramString)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public ResultSet getCursor(int paramInt)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public ResultSet getCursor(String paramString)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public CustomDatum getCustomDatum(int paramInt, CustomDatumFactory paramCustomDatumFactory)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public CustomDatum getCustomDatum(String paramString, CustomDatumFactory paramCustomDatumFactory)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public DATE getDATE(int paramInt)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public DATE getDATE(String paramString)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public INTERVALDS getINTERVALDS(int paramInt)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public INTERVALDS getINTERVALDS(String paramString)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public INTERVALYM getINTERVALYM(int paramInt)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public INTERVALYM getINTERVALYM(String paramString)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public NUMBER getNUMBER(int paramInt)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public NUMBER getNUMBER(String paramString)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public OPAQUE getOPAQUE(int paramInt)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public OPAQUE getOPAQUE(String paramString)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public Datum getOracleObject(int paramInt)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public Datum getOracleObject(String paramString)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public ORAData getORAData(int paramInt, ORADataFactory paramORADataFactory)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public ORAData getORAData(String paramString, ORADataFactory paramORADataFactory)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public RAW getRAW(int paramInt)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public RAW getRAW(String paramString)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public REF getREF(int paramInt)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public REF getREF(String paramString)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public ROWID getROWID(int paramInt)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public ROWID getROWID(String paramString)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public STRUCT getSTRUCT(int paramInt)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public STRUCT getSTRUCT(String paramString)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public TIMESTAMPLTZ getTIMESTAMPLTZ(int paramInt)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public TIMESTAMPLTZ getTIMESTAMPLTZ(String paramString)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public TIMESTAMPTZ getTIMESTAMPTZ(int paramInt)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public TIMESTAMPTZ getTIMESTAMPTZ(String paramString)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public TIMESTAMP getTIMESTAMP(int paramInt)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public TIMESTAMP getTIMESTAMP(String paramString)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public InputStream getAsciiStream(int paramInt)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public InputStream getAsciiStream(String paramString)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public InputStream getBinaryStream(int paramInt)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public InputStream getBinaryStream(String paramString)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public Reader getCharacterStream(int paramInt)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public Reader getCharacterStream(String paramString)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public Reader getNCharacterStream(int paramInt)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public Reader getNCharacterStream(String paramString)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public InputStream getUnicodeStream(int paramInt)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public InputStream getUnicodeStream(String paramString)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setNull(int paramInt1, int paramInt2)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setNull(String paramString, int paramInt)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setNull(int paramInt1, int paramInt2, String paramString)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setNull(String paramString1, int paramInt, String paramString2)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setNullAtName(String paramString, int paramInt)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setNullAtName(String paramString1, int paramInt, String paramString2)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setObject(int paramInt1, Object paramObject, int paramInt2, int paramInt3)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setObject(String paramString, Object paramObject, int paramInt1, int paramInt2)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setObjectAtName(String paramString, Object paramObject, int paramInt1, int paramInt2)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public int getFetchDirection()
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public int getFetchSize()
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public int getMaxFieldSize()
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public int getMaxRows()
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public int getQueryTimeout()
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public int getResultSetConcurrency()
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public int getResultSetHoldability()
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public int getResultSetType()
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public int getUpdateCount()
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void cancel()
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void clearBatch()
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void clearWarnings()
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void close()
    throws SQLException
  {
  }

  public boolean getMoreResults()
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public int[] executeBatch()
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setFetchDirection(int paramInt)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setFetchSize(int paramInt)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setMaxFieldSize(int paramInt)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setMaxRows(int paramInt)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setQueryTimeout(int paramInt)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public boolean getMoreResults(int paramInt)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setEscapeProcessing(boolean paramBoolean)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public int executeUpdate(String paramString)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void addBatch(String paramString)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setCursorName(String paramString)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public boolean execute(String paramString)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public int executeUpdate(String paramString, int paramInt)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public boolean execute(String paramString, int paramInt)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public int executeUpdate(String paramString, int[] paramArrayOfInt)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public boolean execute(String paramString, int[] paramArrayOfInt)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public Connection getConnection()
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public ResultSet getGeneratedKeys()
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public ResultSet getResultSet()
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public SQLWarning getWarnings()
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public int executeUpdate(String paramString, String[] paramArrayOfString)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public boolean execute(String paramString, String[] paramArrayOfString)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public ResultSet executeQuery(String paramString)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void clearDefines()
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void defineColumnType(int paramInt1, int paramInt2)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void defineColumnType(int paramInt1, int paramInt2, int paramInt3)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void defineColumnType(int paramInt1, int paramInt2, int paramInt3, short paramShort)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void defineColumnTypeBytes(int paramInt1, int paramInt2, int paramInt3)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void defineColumnTypeChars(int paramInt1, int paramInt2, int paramInt3)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void defineColumnType(int paramInt1, int paramInt2, String paramString)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public int getRowPrefetch()
  {
    return -1;
  }

  public void setResultSetCache(OracleResultSetCache paramOracleResultSetCache)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setRowPrefetch(int paramInt)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public int getLobPrefetchSize()
  {
    return -1;
  }

  public void setLobPrefetchSize(int paramInt)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void closeWithKey(String paramString)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public int creationState()
  {
    return -1;
  }

  public boolean isNCHAR(int paramInt)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public int executeUpdate()
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void addBatch()
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void clearParameters()
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public boolean execute()
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public ParameterMetaData getParameterMetaData()
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public ResultSet executeQuery()
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public ResultSetMetaData getMetaData()
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public ResultSet getReturnResultSet()
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void defineParameterTypeBytes(int paramInt1, int paramInt2, int paramInt3)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void defineParameterTypeChars(int paramInt1, int paramInt2, int paramInt3)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void defineParameterType(int paramInt1, int paramInt2, int paramInt3)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public int getExecuteBatch()
  {
    return -1;
  }

  public int sendBatch()
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setExecuteBatch(int paramInt)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setPlsqlIndexTable(int paramInt1, Object paramObject, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setFormOfUse(int paramInt, short paramShort)
  {
  }

  public void setDisableStmtCaching(boolean paramBoolean)
  {
  }

  public OracleParameterMetaData OracleGetParameterMetaData()
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void registerReturnParameter(int paramInt1, int paramInt2)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void registerReturnParameter(int paramInt1, int paramInt2, int paramInt3)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void registerReturnParameter(int paramInt1, int paramInt2, String paramString)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setBytesForBlob(int paramInt, byte[] paramArrayOfByte)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setBytesForBlobAtName(String paramString, byte[] paramArrayOfByte)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setStringForClob(int paramInt, String paramString)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setStringForClobAtName(String paramString1, String paramString2)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setStructDescriptor(int paramInt, StructDescriptor paramStructDescriptor)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setStructDescriptor(String paramString, StructDescriptor paramStructDescriptor)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setStructDescriptorAtName(String paramString, StructDescriptor paramStructDescriptor)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public Object getAnyDataEmbeddedObject(int paramInt)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void registerOutParameter(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void registerOutParameter(int paramInt1, int paramInt2, String paramString)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void registerOutParameter(int paramInt1, int paramInt2, int paramInt3)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void registerOutParameter(int paramInt1, int paramInt2)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void registerOutParameterBytes(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void registerOutParameterChars(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public Object getPlsqlIndexTable(int paramInt)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public Object getPlsqlIndexTable(int paramInt, Class paramClass)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public Datum[] getOraclePlsqlIndexTable(int paramInt)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void registerIndexTableOutParameter(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setStringForClob(String paramString1, String paramString2)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setBytesForBlob(String paramString, byte[] paramArrayOfByte)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void registerOutParameter(String paramString, int paramInt1, int paramInt2, int paramInt3)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public boolean wasNull()
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void registerOutParameter(String paramString, int paramInt)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void registerOutParameter(String paramString, int paramInt1, int paramInt2)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void registerOutParameter(String paramString1, int paramInt, String paramString2)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public byte[] privateGetBytes(int paramInt)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setDatabaseChangeRegistration(DatabaseChangeRegistration paramDatabaseChangeRegistration)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public boolean isClosed()
    throws SQLException
  {
    return true;
  }

  public boolean isPoolable()
    throws SQLException
  {
    return false;
  }

  public void setPoolable(boolean paramBoolean)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public boolean isWrapperFor(Class<?> paramClass)
    throws SQLException
  {
    if (paramClass.isInterface()) return paramClass.isInstance(this);

    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 177);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public <T> T unwrap(Class<T> paramClass)
    throws SQLException
  {
    if ((paramClass.isInterface()) && (paramClass.isInstance(this))) return this;

    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 177);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setFixedString(boolean paramBoolean)
  {
  }

  public boolean getFixedString()
  {
    return false;
  }

  public boolean getserverCursor()
  {
    return false;
  }

  public int getcacheState()
  {
    return 0;
  }

  public int getstatementType()
  {
    return 3;
  }

  public void setCheckBindTypes(boolean paramBoolean)
  {
  }

  public void setInternalBytes(int paramInt1, byte[] paramArrayOfByte, int paramInt2)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void enterImplicitCache()
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void enterExplicitCache()
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void exitImplicitCacheToActive()
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void exitExplicitCacheToActive()
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void exitImplicitCacheToClose()
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void exitExplicitCacheToClose()
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public String[] getRegisteredTableNames() throws SQLException
  {
    return null;
  }

  public long getRegisteredQueryId() throws SQLException {
    return -1L;
  }

  public String getOriginalSql() throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  protected OracleConnection getConnectionDuringExceptionHandling()
  {
    return null;
  }
}