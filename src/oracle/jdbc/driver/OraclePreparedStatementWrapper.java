package oracle.jdbc.driver;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.ParameterMetaData;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import oracle.jdbc.OracleParameterMetaData;
import oracle.sql.ARRAY;
import oracle.sql.BFILE;
import oracle.sql.BINARY_DOUBLE;
import oracle.sql.BINARY_FLOAT;
import oracle.sql.BLOB;
import oracle.sql.CHAR;
import oracle.sql.CLOB;
import oracle.sql.CustomDatum;
import oracle.sql.DATE;
import oracle.sql.Datum;
import oracle.sql.INTERVALDS;
import oracle.sql.INTERVALYM;
import oracle.sql.NUMBER;
import oracle.sql.OPAQUE;
import oracle.sql.ORAData;
import oracle.sql.RAW;
import oracle.sql.REF;
import oracle.sql.ROWID;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;
import oracle.sql.TIMESTAMP;
import oracle.sql.TIMESTAMPLTZ;
import oracle.sql.TIMESTAMPTZ;

class OraclePreparedStatementWrapper extends OracleStatementWrapper
  implements oracle.jdbc.internal.OraclePreparedStatement
{
  protected oracle.jdbc.internal.OraclePreparedStatement preparedStatement = null;

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  OraclePreparedStatementWrapper(oracle.jdbc.OraclePreparedStatement paramOraclePreparedStatement)
    throws SQLException
  {
    super(paramOraclePreparedStatement);
    this.preparedStatement = ((oracle.jdbc.internal.OraclePreparedStatement)paramOraclePreparedStatement);
  }

  public void close()
    throws SQLException
  {
    super.close();
    this.preparedStatement = OracleStatementWrapper.closedStatement;
  }

  public void closeWithKey(String paramString)
    throws SQLException
  {
    this.preparedStatement.closeWithKey(paramString);
    this.statement = (this.preparedStatement = closedStatement);
  }

  public void setArray(int paramInt, Array paramArray)
    throws SQLException
  {
    this.preparedStatement.setArray(paramInt, paramArray);
  }

  public void setBigDecimal(int paramInt, BigDecimal paramBigDecimal)
    throws SQLException
  {
    this.preparedStatement.setBigDecimal(paramInt, paramBigDecimal);
  }

  public void setBlob(int paramInt, Blob paramBlob)
    throws SQLException
  {
    this.preparedStatement.setBlob(paramInt, paramBlob);
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

  public void setBytes(int paramInt, byte[] paramArrayOfByte)
    throws SQLException
  {
    this.preparedStatement.setBytes(paramInt, paramArrayOfByte);
  }

  public void setClob(int paramInt, Clob paramClob)
    throws SQLException
  {
    this.preparedStatement.setClob(paramInt, paramClob);
  }

  public void setDate(int paramInt, Date paramDate)
    throws SQLException
  {
    this.preparedStatement.setDate(paramInt, paramDate);
  }

  public void setDate(int paramInt, Date paramDate, Calendar paramCalendar)
    throws SQLException
  {
    this.preparedStatement.setDate(paramInt, paramDate, paramCalendar);
  }

  public void setDouble(int paramInt, double paramDouble)
    throws SQLException
  {
    this.preparedStatement.setDouble(paramInt, paramDouble);
  }

  public void setFloat(int paramInt, float paramFloat)
    throws SQLException
  {
    this.preparedStatement.setFloat(paramInt, paramFloat);
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

  public void setNClob(int paramInt, NClob paramNClob)
    throws SQLException
  {
    this.preparedStatement.setNClob(paramInt, paramNClob);
  }

  public void setNString(int paramInt, String paramString)
    throws SQLException
  {
    this.preparedStatement.setNString(paramInt, paramString);
  }

  public void setObject(int paramInt, Object paramObject)
    throws SQLException
  {
    this.preparedStatement.setObject(paramInt, paramObject);
  }

  public void setObject(int paramInt1, Object paramObject, int paramInt2)
    throws SQLException
  {
    this.preparedStatement.setObject(paramInt1, paramObject, paramInt2);
  }

  public void setRef(int paramInt, Ref paramRef)
    throws SQLException
  {
    this.preparedStatement.setRef(paramInt, paramRef);
  }

  public void setRowId(int paramInt, RowId paramRowId)
    throws SQLException
  {
    this.preparedStatement.setRowId(paramInt, paramRowId);
  }

  public void setShort(int paramInt, short paramShort)
    throws SQLException
  {
    this.preparedStatement.setShort(paramInt, paramShort);
  }

  public void setSQLXML(int paramInt, SQLXML paramSQLXML)
    throws SQLException
  {
    this.preparedStatement.setSQLXML(paramInt, paramSQLXML);
  }

  public void setString(int paramInt, String paramString)
    throws SQLException
  {
    this.preparedStatement.setString(paramInt, paramString);
  }

  public void setTime(int paramInt, Time paramTime)
    throws SQLException
  {
    this.preparedStatement.setTime(paramInt, paramTime);
  }

  public void setTime(int paramInt, Time paramTime, Calendar paramCalendar)
    throws SQLException
  {
    this.preparedStatement.setTime(paramInt, paramTime, paramCalendar);
  }

  public void setTimestamp(int paramInt, Timestamp paramTimestamp)
    throws SQLException
  {
    this.preparedStatement.setTimestamp(paramInt, paramTimestamp);
  }

  public void setTimestamp(int paramInt, Timestamp paramTimestamp, Calendar paramCalendar)
    throws SQLException
  {
    this.preparedStatement.setTimestamp(paramInt, paramTimestamp, paramCalendar);
  }

  public void setURL(int paramInt, URL paramURL)
    throws SQLException
  {
    this.preparedStatement.setURL(paramInt, paramURL);
  }

  public void setARRAY(int paramInt, ARRAY paramARRAY)
    throws SQLException
  {
    this.preparedStatement.setARRAY(paramInt, paramARRAY);
  }

  public void setBFILE(int paramInt, BFILE paramBFILE)
    throws SQLException
  {
    this.preparedStatement.setBFILE(paramInt, paramBFILE);
  }

  public void setBfile(int paramInt, BFILE paramBFILE)
    throws SQLException
  {
    this.preparedStatement.setBfile(paramInt, paramBFILE);
  }

  public void setBinaryFloat(int paramInt, float paramFloat)
    throws SQLException
  {
    this.preparedStatement.setBinaryFloat(paramInt, paramFloat);
  }

  public void setBinaryFloat(int paramInt, BINARY_FLOAT paramBINARY_FLOAT)
    throws SQLException
  {
    this.preparedStatement.setBinaryFloat(paramInt, paramBINARY_FLOAT);
  }

  public void setBinaryDouble(int paramInt, double paramDouble)
    throws SQLException
  {
    this.preparedStatement.setBinaryDouble(paramInt, paramDouble);
  }

  public void setBinaryDouble(int paramInt, BINARY_DOUBLE paramBINARY_DOUBLE)
    throws SQLException
  {
    this.preparedStatement.setBinaryDouble(paramInt, paramBINARY_DOUBLE);
  }

  public void setBLOB(int paramInt, BLOB paramBLOB)
    throws SQLException
  {
    this.preparedStatement.setBLOB(paramInt, paramBLOB);
  }

  public void setCHAR(int paramInt, CHAR paramCHAR)
    throws SQLException
  {
    this.preparedStatement.setCHAR(paramInt, paramCHAR);
  }

  public void setCLOB(int paramInt, CLOB paramCLOB)
    throws SQLException
  {
    this.preparedStatement.setCLOB(paramInt, paramCLOB);
  }

  public void setCursor(int paramInt, ResultSet paramResultSet)
    throws SQLException
  {
    this.preparedStatement.setCursor(paramInt, paramResultSet);
  }

  public void setCustomDatum(int paramInt, CustomDatum paramCustomDatum)
    throws SQLException
  {
    this.preparedStatement.setCustomDatum(paramInt, paramCustomDatum);
  }

  public void setDATE(int paramInt, DATE paramDATE)
    throws SQLException
  {
    this.preparedStatement.setDATE(paramInt, paramDATE);
  }

  public void setFixedCHAR(int paramInt, String paramString)
    throws SQLException
  {
    this.preparedStatement.setFixedCHAR(paramInt, paramString);
  }

  public void setINTERVALDS(int paramInt, INTERVALDS paramINTERVALDS)
    throws SQLException
  {
    this.preparedStatement.setINTERVALDS(paramInt, paramINTERVALDS);
  }

  public void setINTERVALYM(int paramInt, INTERVALYM paramINTERVALYM)
    throws SQLException
  {
    this.preparedStatement.setINTERVALYM(paramInt, paramINTERVALYM);
  }

  public void setNUMBER(int paramInt, NUMBER paramNUMBER)
    throws SQLException
  {
    this.preparedStatement.setNUMBER(paramInt, paramNUMBER);
  }

  public void setOPAQUE(int paramInt, OPAQUE paramOPAQUE)
    throws SQLException
  {
    this.preparedStatement.setOPAQUE(paramInt, paramOPAQUE);
  }

  public void setOracleObject(int paramInt, Datum paramDatum)
    throws SQLException
  {
    this.preparedStatement.setOracleObject(paramInt, paramDatum);
  }

  public void setORAData(int paramInt, ORAData paramORAData)
    throws SQLException
  {
    this.preparedStatement.setORAData(paramInt, paramORAData);
  }

  public void setRAW(int paramInt, RAW paramRAW)
    throws SQLException
  {
    this.preparedStatement.setRAW(paramInt, paramRAW);
  }

  public void setREF(int paramInt, REF paramREF)
    throws SQLException
  {
    this.preparedStatement.setREF(paramInt, paramREF);
  }

  public void setRefType(int paramInt, REF paramREF)
    throws SQLException
  {
    this.preparedStatement.setRefType(paramInt, paramREF);
  }

  public void setROWID(int paramInt, ROWID paramROWID)
    throws SQLException
  {
    this.preparedStatement.setROWID(paramInt, paramROWID);
  }

  public void setSTRUCT(int paramInt, STRUCT paramSTRUCT)
    throws SQLException
  {
    this.preparedStatement.setSTRUCT(paramInt, paramSTRUCT);
  }

  public void setTIMESTAMPLTZ(int paramInt, TIMESTAMPLTZ paramTIMESTAMPLTZ)
    throws SQLException
  {
    this.preparedStatement.setTIMESTAMPLTZ(paramInt, paramTIMESTAMPLTZ);
  }

  public void setTIMESTAMPTZ(int paramInt, TIMESTAMPTZ paramTIMESTAMPTZ)
    throws SQLException
  {
    this.preparedStatement.setTIMESTAMPTZ(paramInt, paramTIMESTAMPTZ);
  }

  public void setTIMESTAMP(int paramInt, TIMESTAMP paramTIMESTAMP)
    throws SQLException
  {
    this.preparedStatement.setTIMESTAMP(paramInt, paramTIMESTAMP);
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

  public void setNClob(int paramInt, Reader paramReader)
    throws SQLException
  {
    this.preparedStatement.setNClob(paramInt, paramReader);
  }

  public void setNClob(int paramInt, Reader paramReader, long paramLong)
    throws SQLException
  {
    this.preparedStatement.setNClob(paramInt, paramReader, paramLong);
  }

  public void setAsciiStream(int paramInt, InputStream paramInputStream)
    throws SQLException
  {
    this.preparedStatement.setAsciiStream(paramInt, paramInputStream);
  }

  public void setAsciiStream(int paramInt1, InputStream paramInputStream, int paramInt2)
    throws SQLException
  {
    this.preparedStatement.setAsciiStream(paramInt1, paramInputStream, paramInt2);
  }

  public void setAsciiStream(int paramInt, InputStream paramInputStream, long paramLong)
    throws SQLException
  {
    this.preparedStatement.setAsciiStream(paramInt, paramInputStream, paramLong);
  }

  public void setBinaryStream(int paramInt, InputStream paramInputStream)
    throws SQLException
  {
    this.preparedStatement.setBinaryStream(paramInt, paramInputStream);
  }

  public void setBinaryStream(int paramInt1, InputStream paramInputStream, int paramInt2)
    throws SQLException
  {
    this.preparedStatement.setBinaryStream(paramInt1, paramInputStream, paramInt2);
  }

  public void setBinaryStream(int paramInt, InputStream paramInputStream, long paramLong)
    throws SQLException
  {
    this.preparedStatement.setBinaryStream(paramInt, paramInputStream, paramLong);
  }

  public void setCharacterStream(int paramInt, Reader paramReader)
    throws SQLException
  {
    this.preparedStatement.setCharacterStream(paramInt, paramReader);
  }

  public void setCharacterStream(int paramInt1, Reader paramReader, int paramInt2)
    throws SQLException
  {
    this.preparedStatement.setCharacterStream(paramInt1, paramReader, paramInt2);
  }

  public void setCharacterStream(int paramInt, Reader paramReader, long paramLong)
    throws SQLException
  {
    this.preparedStatement.setCharacterStream(paramInt, paramReader, paramLong);
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

  public void setUnicodeStream(int paramInt1, InputStream paramInputStream, int paramInt2)
    throws SQLException
  {
    this.preparedStatement.setUnicodeStream(paramInt1, paramInputStream, paramInt2);
  }

  public void setArrayAtName(String paramString, Array paramArray)
    throws SQLException
  {
    this.preparedStatement.setArrayAtName(paramString, paramArray);
  }

  public void setBigDecimalAtName(String paramString, BigDecimal paramBigDecimal)
    throws SQLException
  {
    this.preparedStatement.setBigDecimalAtName(paramString, paramBigDecimal);
  }

  public void setBlobAtName(String paramString, Blob paramBlob)
    throws SQLException
  {
    this.preparedStatement.setBlobAtName(paramString, paramBlob);
  }

  public void setBooleanAtName(String paramString, boolean paramBoolean)
    throws SQLException
  {
    this.preparedStatement.setBooleanAtName(paramString, paramBoolean);
  }

  public void setByteAtName(String paramString, byte paramByte)
    throws SQLException
  {
    this.preparedStatement.setByteAtName(paramString, paramByte);
  }

  public void setBytesAtName(String paramString, byte[] paramArrayOfByte)
    throws SQLException
  {
    this.preparedStatement.setBytesAtName(paramString, paramArrayOfByte);
  }

  public void setClobAtName(String paramString, Clob paramClob)
    throws SQLException
  {
    this.preparedStatement.setClobAtName(paramString, paramClob);
  }

  public void setDateAtName(String paramString, Date paramDate)
    throws SQLException
  {
    this.preparedStatement.setDateAtName(paramString, paramDate);
  }

  public void setDateAtName(String paramString, Date paramDate, Calendar paramCalendar)
    throws SQLException
  {
    this.preparedStatement.setDateAtName(paramString, paramDate, paramCalendar);
  }

  public void setDoubleAtName(String paramString, double paramDouble)
    throws SQLException
  {
    this.preparedStatement.setDoubleAtName(paramString, paramDouble);
  }

  public void setFloatAtName(String paramString, float paramFloat)
    throws SQLException
  {
    this.preparedStatement.setFloatAtName(paramString, paramFloat);
  }

  public void setIntAtName(String paramString, int paramInt)
    throws SQLException
  {
    this.preparedStatement.setIntAtName(paramString, paramInt);
  }

  public void setLongAtName(String paramString, long paramLong)
    throws SQLException
  {
    this.preparedStatement.setLongAtName(paramString, paramLong);
  }

  public void setNClobAtName(String paramString, NClob paramNClob)
    throws SQLException
  {
    this.preparedStatement.setNClobAtName(paramString, paramNClob);
  }

  public void setNStringAtName(String paramString1, String paramString2)
    throws SQLException
  {
    this.preparedStatement.setNStringAtName(paramString1, paramString2);
  }

  public void setObjectAtName(String paramString, Object paramObject)
    throws SQLException
  {
    this.preparedStatement.setObjectAtName(paramString, paramObject);
  }

  public void setObjectAtName(String paramString, Object paramObject, int paramInt)
    throws SQLException
  {
    this.preparedStatement.setObjectAtName(paramString, paramObject, paramInt);
  }

  public void setRefAtName(String paramString, Ref paramRef)
    throws SQLException
  {
    this.preparedStatement.setRefAtName(paramString, paramRef);
  }

  public void setRowIdAtName(String paramString, RowId paramRowId)
    throws SQLException
  {
    this.preparedStatement.setRowIdAtName(paramString, paramRowId);
  }

  public void setShortAtName(String paramString, short paramShort)
    throws SQLException
  {
    this.preparedStatement.setShortAtName(paramString, paramShort);
  }

  public void setSQLXMLAtName(String paramString, SQLXML paramSQLXML)
    throws SQLException
  {
    this.preparedStatement.setSQLXMLAtName(paramString, paramSQLXML);
  }

  public void setStringAtName(String paramString1, String paramString2)
    throws SQLException
  {
    this.preparedStatement.setStringAtName(paramString1, paramString2);
  }

  public void setTimeAtName(String paramString, Time paramTime)
    throws SQLException
  {
    this.preparedStatement.setTimeAtName(paramString, paramTime);
  }

  public void setTimeAtName(String paramString, Time paramTime, Calendar paramCalendar)
    throws SQLException
  {
    this.preparedStatement.setTimeAtName(paramString, paramTime, paramCalendar);
  }

  public void setTimestampAtName(String paramString, Timestamp paramTimestamp)
    throws SQLException
  {
    this.preparedStatement.setTimestampAtName(paramString, paramTimestamp);
  }

  public void setTimestampAtName(String paramString, Timestamp paramTimestamp, Calendar paramCalendar)
    throws SQLException
  {
    this.preparedStatement.setTimestampAtName(paramString, paramTimestamp, paramCalendar);
  }

  public void setURLAtName(String paramString, URL paramURL)
    throws SQLException
  {
    this.preparedStatement.setURLAtName(paramString, paramURL);
  }

  public void setARRAYAtName(String paramString, ARRAY paramARRAY)
    throws SQLException
  {
    this.preparedStatement.setARRAYAtName(paramString, paramARRAY);
  }

  public void setBFILEAtName(String paramString, BFILE paramBFILE)
    throws SQLException
  {
    this.preparedStatement.setBFILEAtName(paramString, paramBFILE);
  }

  public void setBfileAtName(String paramString, BFILE paramBFILE)
    throws SQLException
  {
    this.preparedStatement.setBfileAtName(paramString, paramBFILE);
  }

  public void setBinaryFloatAtName(String paramString, float paramFloat)
    throws SQLException
  {
    this.preparedStatement.setBinaryFloatAtName(paramString, paramFloat);
  }

  public void setBinaryFloatAtName(String paramString, BINARY_FLOAT paramBINARY_FLOAT)
    throws SQLException
  {
    this.preparedStatement.setBinaryFloatAtName(paramString, paramBINARY_FLOAT);
  }

  public void setBinaryDoubleAtName(String paramString, double paramDouble)
    throws SQLException
  {
    this.preparedStatement.setBinaryDoubleAtName(paramString, paramDouble);
  }

  public void setBinaryDoubleAtName(String paramString, BINARY_DOUBLE paramBINARY_DOUBLE)
    throws SQLException
  {
    this.preparedStatement.setBinaryDoubleAtName(paramString, paramBINARY_DOUBLE);
  }

  public void setBLOBAtName(String paramString, BLOB paramBLOB)
    throws SQLException
  {
    this.preparedStatement.setBLOBAtName(paramString, paramBLOB);
  }

  public void setCHARAtName(String paramString, CHAR paramCHAR)
    throws SQLException
  {
    this.preparedStatement.setCHARAtName(paramString, paramCHAR);
  }

  public void setCLOBAtName(String paramString, CLOB paramCLOB)
    throws SQLException
  {
    this.preparedStatement.setCLOBAtName(paramString, paramCLOB);
  }

  public void setCursorAtName(String paramString, ResultSet paramResultSet)
    throws SQLException
  {
    this.preparedStatement.setCursorAtName(paramString, paramResultSet);
  }

  public void setCustomDatumAtName(String paramString, CustomDatum paramCustomDatum)
    throws SQLException
  {
    this.preparedStatement.setCustomDatumAtName(paramString, paramCustomDatum);
  }

  public void setDATEAtName(String paramString, DATE paramDATE)
    throws SQLException
  {
    this.preparedStatement.setDATEAtName(paramString, paramDATE);
  }

  public void setFixedCHARAtName(String paramString1, String paramString2)
    throws SQLException
  {
    this.preparedStatement.setFixedCHARAtName(paramString1, paramString2);
  }

  public void setINTERVALDSAtName(String paramString, INTERVALDS paramINTERVALDS)
    throws SQLException
  {
    this.preparedStatement.setINTERVALDSAtName(paramString, paramINTERVALDS);
  }

  public void setINTERVALYMAtName(String paramString, INTERVALYM paramINTERVALYM)
    throws SQLException
  {
    this.preparedStatement.setINTERVALYMAtName(paramString, paramINTERVALYM);
  }

  public void setNUMBERAtName(String paramString, NUMBER paramNUMBER)
    throws SQLException
  {
    this.preparedStatement.setNUMBERAtName(paramString, paramNUMBER);
  }

  public void setOPAQUEAtName(String paramString, OPAQUE paramOPAQUE)
    throws SQLException
  {
    this.preparedStatement.setOPAQUEAtName(paramString, paramOPAQUE);
  }

  public void setOracleObjectAtName(String paramString, Datum paramDatum)
    throws SQLException
  {
    this.preparedStatement.setOracleObjectAtName(paramString, paramDatum);
  }

  public void setORADataAtName(String paramString, ORAData paramORAData)
    throws SQLException
  {
    this.preparedStatement.setORADataAtName(paramString, paramORAData);
  }

  public void setRAWAtName(String paramString, RAW paramRAW)
    throws SQLException
  {
    this.preparedStatement.setRAWAtName(paramString, paramRAW);
  }

  public void setREFAtName(String paramString, REF paramREF)
    throws SQLException
  {
    this.preparedStatement.setREFAtName(paramString, paramREF);
  }

  public void setRefTypeAtName(String paramString, REF paramREF)
    throws SQLException
  {
    this.preparedStatement.setRefTypeAtName(paramString, paramREF);
  }

  public void setROWIDAtName(String paramString, ROWID paramROWID)
    throws SQLException
  {
    this.preparedStatement.setROWIDAtName(paramString, paramROWID);
  }

  public void setSTRUCTAtName(String paramString, STRUCT paramSTRUCT)
    throws SQLException
  {
    this.preparedStatement.setSTRUCTAtName(paramString, paramSTRUCT);
  }

  public void setTIMESTAMPLTZAtName(String paramString, TIMESTAMPLTZ paramTIMESTAMPLTZ)
    throws SQLException
  {
    this.preparedStatement.setTIMESTAMPLTZAtName(paramString, paramTIMESTAMPLTZ);
  }

  public void setTIMESTAMPTZAtName(String paramString, TIMESTAMPTZ paramTIMESTAMPTZ)
    throws SQLException
  {
    this.preparedStatement.setTIMESTAMPTZAtName(paramString, paramTIMESTAMPTZ);
  }

  public void setTIMESTAMPAtName(String paramString, TIMESTAMP paramTIMESTAMP)
    throws SQLException
  {
    this.preparedStatement.setTIMESTAMPAtName(paramString, paramTIMESTAMP);
  }

  public void setBlobAtName(String paramString, InputStream paramInputStream)
    throws SQLException
  {
    this.preparedStatement.setBlobAtName(paramString, paramInputStream);
  }

  public void setBlobAtName(String paramString, InputStream paramInputStream, long paramLong)
    throws SQLException
  {
    this.preparedStatement.setBlobAtName(paramString, paramInputStream, paramLong);
  }

  public void setClobAtName(String paramString, Reader paramReader)
    throws SQLException
  {
    this.preparedStatement.setClobAtName(paramString, paramReader);
  }

  public void setClobAtName(String paramString, Reader paramReader, long paramLong)
    throws SQLException
  {
    this.preparedStatement.setClobAtName(paramString, paramReader, paramLong);
  }

  public void setNClobAtName(String paramString, Reader paramReader)
    throws SQLException
  {
    this.preparedStatement.setNClobAtName(paramString, paramReader);
  }

  public void setNClobAtName(String paramString, Reader paramReader, long paramLong)
    throws SQLException
  {
    this.preparedStatement.setNClobAtName(paramString, paramReader, paramLong);
  }

  public void setAsciiStreamAtName(String paramString, InputStream paramInputStream)
    throws SQLException
  {
    this.preparedStatement.setAsciiStreamAtName(paramString, paramInputStream);
  }

  public void setAsciiStreamAtName(String paramString, InputStream paramInputStream, int paramInt)
    throws SQLException
  {
    this.preparedStatement.setAsciiStreamAtName(paramString, paramInputStream, paramInt);
  }

  public void setAsciiStreamAtName(String paramString, InputStream paramInputStream, long paramLong)
    throws SQLException
  {
    this.preparedStatement.setAsciiStreamAtName(paramString, paramInputStream, paramLong);
  }

  public void setBinaryStreamAtName(String paramString, InputStream paramInputStream)
    throws SQLException
  {
    this.preparedStatement.setBinaryStreamAtName(paramString, paramInputStream);
  }

  public void setBinaryStreamAtName(String paramString, InputStream paramInputStream, int paramInt)
    throws SQLException
  {
    this.preparedStatement.setBinaryStreamAtName(paramString, paramInputStream, paramInt);
  }

  public void setBinaryStreamAtName(String paramString, InputStream paramInputStream, long paramLong)
    throws SQLException
  {
    this.preparedStatement.setBinaryStreamAtName(paramString, paramInputStream, paramLong);
  }

  public void setCharacterStreamAtName(String paramString, Reader paramReader)
    throws SQLException
  {
    this.preparedStatement.setCharacterStreamAtName(paramString, paramReader);
  }

  public void setCharacterStreamAtName(String paramString, Reader paramReader, int paramInt)
    throws SQLException
  {
    this.preparedStatement.setCharacterStreamAtName(paramString, paramReader, paramInt);
  }

  public void setCharacterStreamAtName(String paramString, Reader paramReader, long paramLong)
    throws SQLException
  {
    this.preparedStatement.setCharacterStreamAtName(paramString, paramReader, paramLong);
  }

  public void setNCharacterStreamAtName(String paramString, Reader paramReader)
    throws SQLException
  {
    this.preparedStatement.setNCharacterStreamAtName(paramString, paramReader);
  }

  public void setNCharacterStreamAtName(String paramString, Reader paramReader, long paramLong)
    throws SQLException
  {
    this.preparedStatement.setNCharacterStreamAtName(paramString, paramReader, paramLong);
  }

  public void setUnicodeStreamAtName(String paramString, InputStream paramInputStream, int paramInt)
    throws SQLException
  {
    this.preparedStatement.setUnicodeStreamAtName(paramString, paramInputStream, paramInt);
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

  public void setNullAtName(String paramString, int paramInt)
    throws SQLException
  {
    this.preparedStatement.setNullAtName(paramString, paramInt);
  }

  public void setNullAtName(String paramString1, int paramInt, String paramString2)
    throws SQLException
  {
    this.preparedStatement.setNullAtName(paramString1, paramInt, paramString2);
  }

  public void setObject(int paramInt1, Object paramObject, int paramInt2, int paramInt3)
    throws SQLException
  {
    this.preparedStatement.setObject(paramInt1, paramObject, paramInt2, paramInt3);
  }

  public void setObjectAtName(String paramString, Object paramObject, int paramInt1, int paramInt2)
    throws SQLException
  {
    this.preparedStatement.setObjectAtName(paramString, paramObject, paramInt1, paramInt2);
  }

  public void setStructDescriptor(int paramInt, StructDescriptor paramStructDescriptor)
    throws SQLException
  {
    this.preparedStatement.setStructDescriptor(paramInt, paramStructDescriptor);
  }

  public void setStructDescriptorAtName(String paramString, StructDescriptor paramStructDescriptor)
    throws SQLException
  {
    this.preparedStatement.setStructDescriptorAtName(paramString, paramStructDescriptor);
  }

  public int executeUpdate()
    throws SQLException
  {
    return this.preparedStatement.executeUpdate();
  }

  public void addBatch()
    throws SQLException
  {
    this.preparedStatement.addBatch();
  }

  public void clearParameters()
    throws SQLException
  {
    this.preparedStatement.clearParameters();
  }

  public boolean execute()
    throws SQLException
  {
    return this.preparedStatement.execute();
  }

  public void setCheckBindTypes(boolean paramBoolean)
  {
    this.preparedStatement.setCheckBindTypes(paramBoolean);
  }

  public ResultSet getReturnResultSet()
    throws SQLException
  {
    return this.preparedStatement.getReturnResultSet();
  }

  public void defineParameterTypeBytes(int paramInt1, int paramInt2, int paramInt3)
    throws SQLException
  {
    this.preparedStatement.defineParameterTypeBytes(paramInt1, paramInt2, paramInt3);
  }

  public void defineParameterTypeChars(int paramInt1, int paramInt2, int paramInt3)
    throws SQLException
  {
    this.preparedStatement.defineParameterTypeChars(paramInt1, paramInt2, paramInt3);
  }

  public void defineParameterType(int paramInt1, int paramInt2, int paramInt3)
    throws SQLException
  {
    this.preparedStatement.defineParameterType(paramInt1, paramInt2, paramInt3);
  }

  public int getExecuteBatch()
  {
    return this.preparedStatement.getExecuteBatch();
  }

  public int sendBatch()
    throws SQLException
  {
    return this.preparedStatement.sendBatch();
  }

  public void setPlsqlIndexTable(int paramInt1, Object paramObject, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
    throws SQLException
  {
    this.preparedStatement.setPlsqlIndexTable(paramInt1, paramObject, paramInt2, paramInt3, paramInt4, paramInt5);
  }

  public void setFormOfUse(int paramInt, short paramShort)
  {
    this.preparedStatement.setFormOfUse(paramInt, paramShort);
  }

  public void setDisableStmtCaching(boolean paramBoolean)
  {
    this.preparedStatement.setDisableStmtCaching(paramBoolean);
  }

  public OracleParameterMetaData OracleGetParameterMetaData()
    throws SQLException
  {
    return this.preparedStatement.OracleGetParameterMetaData();
  }

  public void registerReturnParameter(int paramInt1, int paramInt2)
    throws SQLException
  {
    this.preparedStatement.registerReturnParameter(paramInt1, paramInt2);
  }

  public void registerReturnParameter(int paramInt1, int paramInt2, int paramInt3)
    throws SQLException
  {
    this.preparedStatement.registerReturnParameter(paramInt1, paramInt2, paramInt3);
  }

  public void registerReturnParameter(int paramInt1, int paramInt2, String paramString)
    throws SQLException
  {
    this.preparedStatement.registerReturnParameter(paramInt1, paramInt2, paramString);
  }

  public ResultSet executeQuery()
    throws SQLException
  {
    return this.preparedStatement.executeQuery();
  }

  public ResultSetMetaData getMetaData()
    throws SQLException
  {
    return this.preparedStatement.getMetaData();
  }

  public void setBytesForBlob(int paramInt, byte[] paramArrayOfByte)
    throws SQLException
  {
    this.preparedStatement.setBytesForBlob(paramInt, paramArrayOfByte);
  }

  public void setBytesForBlobAtName(String paramString, byte[] paramArrayOfByte)
    throws SQLException
  {
    this.preparedStatement.setBytesForBlobAtName(paramString, paramArrayOfByte);
  }

  public void setStringForClob(int paramInt, String paramString)
    throws SQLException
  {
    this.preparedStatement.setStringForClob(paramInt, paramString);
  }

  public void setStringForClobAtName(String paramString1, String paramString2)
    throws SQLException
  {
    this.preparedStatement.setStringForClobAtName(paramString1, paramString2);
  }

  public ParameterMetaData getParameterMetaData()
    throws SQLException
  {
    return this.preparedStatement.getParameterMetaData();
  }

  public void setExecuteBatch(int paramInt)
    throws SQLException
  {
    this.preparedStatement.setExecuteBatch(paramInt);
  }

  public boolean isPoolable()
    throws SQLException
  {
    return this.preparedStatement.isPoolable();
  }

  public void setPoolable(boolean paramBoolean)
    throws SQLException
  {
    this.preparedStatement.setPoolable(paramBoolean);
  }

  public void setInternalBytes(int paramInt1, byte[] paramArrayOfByte, int paramInt2)
    throws SQLException
  {
    this.preparedStatement.setInternalBytes(paramInt1, paramArrayOfByte, paramInt2);
  }

  public void enterImplicitCache()
    throws SQLException
  {
    this.preparedStatement.enterImplicitCache();
  }

  public void enterExplicitCache()
    throws SQLException
  {
    this.preparedStatement.enterExplicitCache();
  }

  public void exitImplicitCacheToActive()
    throws SQLException
  {
    this.preparedStatement.exitImplicitCacheToActive();
  }

  public void exitExplicitCacheToActive()
    throws SQLException
  {
    this.preparedStatement.exitExplicitCacheToActive();
  }

  public void exitImplicitCacheToClose()
    throws SQLException
  {
    this.preparedStatement.exitImplicitCacheToClose();
  }

  public void exitExplicitCacheToClose()
    throws SQLException
  {
    this.preparedStatement.exitExplicitCacheToClose();
  }

  public String getOriginalSql()
    throws SQLException
  {
    return this.preparedStatement.getOriginalSql();
  }
}