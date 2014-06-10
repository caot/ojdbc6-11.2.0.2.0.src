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
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;
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

class OracleCallableStatementWrapper extends OraclePreparedStatementWrapper
  implements oracle.jdbc.internal.OracleCallableStatement
{
  protected oracle.jdbc.internal.OracleCallableStatement callableStatement = null;

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  OracleCallableStatementWrapper(oracle.jdbc.OracleCallableStatement paramOracleCallableStatement)
    throws SQLException
  {
    super(paramOracleCallableStatement);
    this.callableStatement = ((oracle.jdbc.internal.OracleCallableStatement)paramOracleCallableStatement);
  }

  public void setArray(String paramString, Array paramArray)
    throws SQLException
  {
    this.callableStatement.setArray(paramString, paramArray);
  }

  public void setBigDecimal(String paramString, BigDecimal paramBigDecimal)
    throws SQLException
  {
    this.callableStatement.setBigDecimal(paramString, paramBigDecimal);
  }

  public void setBlob(String paramString, Blob paramBlob)
    throws SQLException
  {
    this.callableStatement.setBlob(paramString, paramBlob);
  }

  public void setBoolean(String paramString, boolean paramBoolean)
    throws SQLException
  {
    this.callableStatement.setBoolean(paramString, paramBoolean);
  }

  public void setByte(String paramString, byte paramByte)
    throws SQLException
  {
    this.callableStatement.setByte(paramString, paramByte);
  }

  public void setBytes(String paramString, byte[] paramArrayOfByte)
    throws SQLException
  {
    this.callableStatement.setBytes(paramString, paramArrayOfByte);
  }

  public void setClob(String paramString, Clob paramClob)
    throws SQLException
  {
    this.callableStatement.setClob(paramString, paramClob);
  }

  public void setDate(String paramString, Date paramDate)
    throws SQLException
  {
    this.callableStatement.setDate(paramString, paramDate);
  }

  public void setDate(String paramString, Date paramDate, Calendar paramCalendar)
    throws SQLException
  {
    this.callableStatement.setDate(paramString, paramDate, paramCalendar);
  }

  public void setDouble(String paramString, double paramDouble)
    throws SQLException
  {
    this.callableStatement.setDouble(paramString, paramDouble);
  }

  public void setFloat(String paramString, float paramFloat)
    throws SQLException
  {
    this.callableStatement.setFloat(paramString, paramFloat);
  }

  public void setInt(String paramString, int paramInt)
    throws SQLException
  {
    this.callableStatement.setInt(paramString, paramInt);
  }

  public void setLong(String paramString, long paramLong)
    throws SQLException
  {
    this.callableStatement.setLong(paramString, paramLong);
  }

  public void setNClob(String paramString, NClob paramNClob)
    throws SQLException
  {
    this.callableStatement.setNClob(paramString, paramNClob);
  }

  public void setNString(String paramString1, String paramString2)
    throws SQLException
  {
    this.callableStatement.setNString(paramString1, paramString2);
  }

  public void setObject(String paramString, Object paramObject)
    throws SQLException
  {
    this.callableStatement.setObject(paramString, paramObject);
  }

  public void setObject(String paramString, Object paramObject, int paramInt)
    throws SQLException
  {
    this.callableStatement.setObject(paramString, paramObject, paramInt);
  }

  public void setRef(String paramString, Ref paramRef)
    throws SQLException
  {
    this.callableStatement.setRef(paramString, paramRef);
  }

  public void setRowId(String paramString, RowId paramRowId)
    throws SQLException
  {
    this.callableStatement.setRowId(paramString, paramRowId);
  }

  public void setShort(String paramString, short paramShort)
    throws SQLException
  {
    this.callableStatement.setShort(paramString, paramShort);
  }

  public void setSQLXML(String paramString, SQLXML paramSQLXML)
    throws SQLException
  {
    this.callableStatement.setSQLXML(paramString, paramSQLXML);
  }

  public void setString(String paramString1, String paramString2)
    throws SQLException
  {
    this.callableStatement.setString(paramString1, paramString2);
  }

  public void setTime(String paramString, Time paramTime)
    throws SQLException
  {
    this.callableStatement.setTime(paramString, paramTime);
  }

  public void setTime(String paramString, Time paramTime, Calendar paramCalendar)
    throws SQLException
  {
    this.callableStatement.setTime(paramString, paramTime, paramCalendar);
  }

  public void setTimestamp(String paramString, Timestamp paramTimestamp)
    throws SQLException
  {
    this.callableStatement.setTimestamp(paramString, paramTimestamp);
  }

  public void setTimestamp(String paramString, Timestamp paramTimestamp, Calendar paramCalendar)
    throws SQLException
  {
    this.callableStatement.setTimestamp(paramString, paramTimestamp, paramCalendar);
  }

  public void setURL(String paramString, URL paramURL)
    throws SQLException
  {
    this.callableStatement.setURL(paramString, paramURL);
  }

  public void setARRAY(String paramString, ARRAY paramARRAY)
    throws SQLException
  {
    this.callableStatement.setARRAY(paramString, paramARRAY);
  }

  public void setBFILE(String paramString, BFILE paramBFILE)
    throws SQLException
  {
    this.callableStatement.setBFILE(paramString, paramBFILE);
  }

  public void setBfile(String paramString, BFILE paramBFILE)
    throws SQLException
  {
    this.callableStatement.setBfile(paramString, paramBFILE);
  }

  public void setBinaryFloat(String paramString, float paramFloat)
    throws SQLException
  {
    this.callableStatement.setBinaryFloat(paramString, paramFloat);
  }

  public void setBinaryFloat(String paramString, BINARY_FLOAT paramBINARY_FLOAT)
    throws SQLException
  {
    this.callableStatement.setBinaryFloat(paramString, paramBINARY_FLOAT);
  }

  public void setBinaryDouble(String paramString, double paramDouble)
    throws SQLException
  {
    this.callableStatement.setBinaryDouble(paramString, paramDouble);
  }

  public void setBinaryDouble(String paramString, BINARY_DOUBLE paramBINARY_DOUBLE)
    throws SQLException
  {
    this.callableStatement.setBinaryDouble(paramString, paramBINARY_DOUBLE);
  }

  public void setBLOB(String paramString, BLOB paramBLOB)
    throws SQLException
  {
    this.callableStatement.setBLOB(paramString, paramBLOB);
  }

  public void setCHAR(String paramString, CHAR paramCHAR)
    throws SQLException
  {
    this.callableStatement.setCHAR(paramString, paramCHAR);
  }

  public void setCLOB(String paramString, CLOB paramCLOB)
    throws SQLException
  {
    this.callableStatement.setCLOB(paramString, paramCLOB);
  }

  public void setCursor(String paramString, ResultSet paramResultSet)
    throws SQLException
  {
    this.callableStatement.setCursor(paramString, paramResultSet);
  }

  public void setCustomDatum(String paramString, CustomDatum paramCustomDatum)
    throws SQLException
  {
    this.callableStatement.setCustomDatum(paramString, paramCustomDatum);
  }

  public void setDATE(String paramString, DATE paramDATE)
    throws SQLException
  {
    this.callableStatement.setDATE(paramString, paramDATE);
  }

  public void setFixedCHAR(String paramString1, String paramString2)
    throws SQLException
  {
    this.callableStatement.setFixedCHAR(paramString1, paramString2);
  }

  public void setINTERVALDS(String paramString, INTERVALDS paramINTERVALDS)
    throws SQLException
  {
    this.callableStatement.setINTERVALDS(paramString, paramINTERVALDS);
  }

  public void setINTERVALYM(String paramString, INTERVALYM paramINTERVALYM)
    throws SQLException
  {
    this.callableStatement.setINTERVALYM(paramString, paramINTERVALYM);
  }

  public void setNUMBER(String paramString, NUMBER paramNUMBER)
    throws SQLException
  {
    this.callableStatement.setNUMBER(paramString, paramNUMBER);
  }

  public void setOPAQUE(String paramString, OPAQUE paramOPAQUE)
    throws SQLException
  {
    this.callableStatement.setOPAQUE(paramString, paramOPAQUE);
  }

  public void setOracleObject(String paramString, Datum paramDatum)
    throws SQLException
  {
    this.callableStatement.setOracleObject(paramString, paramDatum);
  }

  public void setORAData(String paramString, ORAData paramORAData)
    throws SQLException
  {
    this.callableStatement.setORAData(paramString, paramORAData);
  }

  public void setRAW(String paramString, RAW paramRAW)
    throws SQLException
  {
    this.callableStatement.setRAW(paramString, paramRAW);
  }

  public void setREF(String paramString, REF paramREF)
    throws SQLException
  {
    this.callableStatement.setREF(paramString, paramREF);
  }

  public void setRefType(String paramString, REF paramREF)
    throws SQLException
  {
    this.callableStatement.setRefType(paramString, paramREF);
  }

  public void setROWID(String paramString, ROWID paramROWID)
    throws SQLException
  {
    this.callableStatement.setROWID(paramString, paramROWID);
  }

  public void setSTRUCT(String paramString, STRUCT paramSTRUCT)
    throws SQLException
  {
    this.callableStatement.setSTRUCT(paramString, paramSTRUCT);
  }

  public void setTIMESTAMPLTZ(String paramString, TIMESTAMPLTZ paramTIMESTAMPLTZ)
    throws SQLException
  {
    this.callableStatement.setTIMESTAMPLTZ(paramString, paramTIMESTAMPLTZ);
  }

  public void setTIMESTAMPTZ(String paramString, TIMESTAMPTZ paramTIMESTAMPTZ)
    throws SQLException
  {
    this.callableStatement.setTIMESTAMPTZ(paramString, paramTIMESTAMPTZ);
  }

  public void setTIMESTAMP(String paramString, TIMESTAMP paramTIMESTAMP)
    throws SQLException
  {
    this.callableStatement.setTIMESTAMP(paramString, paramTIMESTAMP);
  }

  public void setBlob(String paramString, InputStream paramInputStream)
    throws SQLException
  {
    this.callableStatement.setBlob(paramString, paramInputStream);
  }

  public void setBlob(String paramString, InputStream paramInputStream, long paramLong)
    throws SQLException
  {
    this.callableStatement.setBlob(paramString, paramInputStream, paramLong);
  }

  public void setClob(String paramString, Reader paramReader)
    throws SQLException
  {
    this.callableStatement.setClob(paramString, paramReader);
  }

  public void setClob(String paramString, Reader paramReader, long paramLong)
    throws SQLException
  {
    this.callableStatement.setClob(paramString, paramReader, paramLong);
  }

  public void setNClob(String paramString, Reader paramReader)
    throws SQLException
  {
    this.callableStatement.setNClob(paramString, paramReader);
  }

  public void setNClob(String paramString, Reader paramReader, long paramLong)
    throws SQLException
  {
    this.callableStatement.setNClob(paramString, paramReader, paramLong);
  }

  public void setAsciiStream(String paramString, InputStream paramInputStream)
    throws SQLException
  {
    this.callableStatement.setAsciiStream(paramString, paramInputStream);
  }

  public void setAsciiStream(String paramString, InputStream paramInputStream, int paramInt)
    throws SQLException
  {
    this.callableStatement.setAsciiStream(paramString, paramInputStream, paramInt);
  }

  public void setAsciiStream(String paramString, InputStream paramInputStream, long paramLong)
    throws SQLException
  {
    this.callableStatement.setAsciiStream(paramString, paramInputStream, paramLong);
  }

  public void setBinaryStream(String paramString, InputStream paramInputStream)
    throws SQLException
  {
    this.callableStatement.setBinaryStream(paramString, paramInputStream);
  }

  public void setBinaryStream(String paramString, InputStream paramInputStream, int paramInt)
    throws SQLException
  {
    this.callableStatement.setBinaryStream(paramString, paramInputStream, paramInt);
  }

  public void setBinaryStream(String paramString, InputStream paramInputStream, long paramLong)
    throws SQLException
  {
    this.callableStatement.setBinaryStream(paramString, paramInputStream, paramLong);
  }

  public void setCharacterStream(String paramString, Reader paramReader)
    throws SQLException
  {
    this.callableStatement.setCharacterStream(paramString, paramReader);
  }

  public void setCharacterStream(String paramString, Reader paramReader, int paramInt)
    throws SQLException
  {
    this.callableStatement.setCharacterStream(paramString, paramReader, paramInt);
  }

  public void setCharacterStream(String paramString, Reader paramReader, long paramLong)
    throws SQLException
  {
    this.callableStatement.setCharacterStream(paramString, paramReader, paramLong);
  }

  public void setNCharacterStream(String paramString, Reader paramReader)
    throws SQLException
  {
    this.callableStatement.setNCharacterStream(paramString, paramReader);
  }

  public void setNCharacterStream(String paramString, Reader paramReader, long paramLong)
    throws SQLException
  {
    this.callableStatement.setNCharacterStream(paramString, paramReader, paramLong);
  }

  public void setUnicodeStream(String paramString, InputStream paramInputStream, int paramInt)
    throws SQLException
  {
    this.callableStatement.setUnicodeStream(paramString, paramInputStream, paramInt);
  }

  public void setNull(String paramString1, int paramInt, String paramString2)
    throws SQLException
  {
    this.callableStatement.setNull(paramString1, paramInt, paramString2);
  }

  public void setNull(String paramString, int paramInt)
    throws SQLException
  {
    this.callableStatement.setNull(paramString, paramInt);
  }

  public Array getArray(int paramInt)
    throws SQLException
  {
    return this.callableStatement.getArray(paramInt);
  }

  public BigDecimal getBigDecimal(int paramInt)
    throws SQLException
  {
    return this.callableStatement.getBigDecimal(paramInt);
  }

  public BigDecimal getBigDecimal(int paramInt1, int paramInt2)
    throws SQLException
  {
    return this.callableStatement.getBigDecimal(paramInt1, paramInt2);
  }

  public Blob getBlob(int paramInt)
    throws SQLException
  {
    return this.callableStatement.getBlob(paramInt);
  }

  public boolean getBoolean(int paramInt)
    throws SQLException
  {
    return this.callableStatement.getBoolean(paramInt);
  }

  public byte getByte(int paramInt)
    throws SQLException
  {
    return this.callableStatement.getByte(paramInt);
  }

  public byte[] getBytes(int paramInt)
    throws SQLException
  {
    return this.callableStatement.getBytes(paramInt);
  }

  public Clob getClob(int paramInt)
    throws SQLException
  {
    return this.callableStatement.getClob(paramInt);
  }

  public Date getDate(int paramInt)
    throws SQLException
  {
    return this.callableStatement.getDate(paramInt);
  }

  public Date getDate(int paramInt, Calendar paramCalendar)
    throws SQLException
  {
    return this.callableStatement.getDate(paramInt, paramCalendar);
  }

  public double getDouble(int paramInt)
    throws SQLException
  {
    return this.callableStatement.getDouble(paramInt);
  }

  public float getFloat(int paramInt)
    throws SQLException
  {
    return this.callableStatement.getFloat(paramInt);
  }

  public int getInt(int paramInt)
    throws SQLException
  {
    return this.callableStatement.getInt(paramInt);
  }

  public long getLong(int paramInt)
    throws SQLException
  {
    return this.callableStatement.getLong(paramInt);
  }

  public NClob getNClob(int paramInt)
    throws SQLException
  {
    return this.callableStatement.getNClob(paramInt);
  }

  public String getNString(int paramInt)
    throws SQLException
  {
    return this.callableStatement.getNString(paramInt);
  }

  public Object getObject(int paramInt)
    throws SQLException
  {
    return this.callableStatement.getObject(paramInt);
  }

  public Object getObject(int paramInt, Map paramMap)
    throws SQLException
  {
    return this.callableStatement.getObject(paramInt, paramMap);
  }

  public Ref getRef(int paramInt)
    throws SQLException
  {
    return this.callableStatement.getRef(paramInt);
  }

  public RowId getRowId(int paramInt)
    throws SQLException
  {
    return this.callableStatement.getRowId(paramInt);
  }

  public short getShort(int paramInt)
    throws SQLException
  {
    return this.callableStatement.getShort(paramInt);
  }

  public SQLXML getSQLXML(int paramInt)
    throws SQLException
  {
    return this.callableStatement.getSQLXML(paramInt);
  }

  public String getString(int paramInt)
    throws SQLException
  {
    return this.callableStatement.getString(paramInt);
  }

  public Time getTime(int paramInt)
    throws SQLException
  {
    return this.callableStatement.getTime(paramInt);
  }

  public Time getTime(int paramInt, Calendar paramCalendar)
    throws SQLException
  {
    return this.callableStatement.getTime(paramInt, paramCalendar);
  }

  public Timestamp getTimestamp(int paramInt)
    throws SQLException
  {
    return this.callableStatement.getTimestamp(paramInt);
  }

  public Timestamp getTimestamp(int paramInt, Calendar paramCalendar)
    throws SQLException
  {
    return this.callableStatement.getTimestamp(paramInt, paramCalendar);
  }

  public URL getURL(int paramInt)
    throws SQLException
  {
    return this.callableStatement.getURL(paramInt);
  }

  public ARRAY getARRAY(int paramInt)
    throws SQLException
  {
    return this.callableStatement.getARRAY(paramInt);
  }

  public BFILE getBFILE(int paramInt)
    throws SQLException
  {
    return this.callableStatement.getBFILE(paramInt);
  }

  public BFILE getBfile(int paramInt)
    throws SQLException
  {
    return this.callableStatement.getBfile(paramInt);
  }

  public BLOB getBLOB(int paramInt)
    throws SQLException
  {
    return this.callableStatement.getBLOB(paramInt);
  }

  public CHAR getCHAR(int paramInt)
    throws SQLException
  {
    return this.callableStatement.getCHAR(paramInt);
  }

  public CLOB getCLOB(int paramInt)
    throws SQLException
  {
    return this.callableStatement.getCLOB(paramInt);
  }

  public ResultSet getCursor(int paramInt)
    throws SQLException
  {
    return this.callableStatement.getCursor(paramInt);
  }

  public Object getCustomDatum(int paramInt, CustomDatumFactory paramCustomDatumFactory)
    throws SQLException
  {
    return this.callableStatement.getCustomDatum(paramInt, paramCustomDatumFactory);
  }

  public DATE getDATE(int paramInt)
    throws SQLException
  {
    return this.callableStatement.getDATE(paramInt);
  }

  public INTERVALDS getINTERVALDS(int paramInt)
    throws SQLException
  {
    return this.callableStatement.getINTERVALDS(paramInt);
  }

  public INTERVALYM getINTERVALYM(int paramInt)
    throws SQLException
  {
    return this.callableStatement.getINTERVALYM(paramInt);
  }

  public NUMBER getNUMBER(int paramInt)
    throws SQLException
  {
    return this.callableStatement.getNUMBER(paramInt);
  }

  public OPAQUE getOPAQUE(int paramInt)
    throws SQLException
  {
    return this.callableStatement.getOPAQUE(paramInt);
  }

  public Datum getOracleObject(int paramInt)
    throws SQLException
  {
    return this.callableStatement.getOracleObject(paramInt);
  }

  public Object getORAData(int paramInt, ORADataFactory paramORADataFactory)
    throws SQLException
  {
    return this.callableStatement.getORAData(paramInt, paramORADataFactory);
  }

  public RAW getRAW(int paramInt)
    throws SQLException
  {
    return this.callableStatement.getRAW(paramInt);
  }

  public REF getREF(int paramInt)
    throws SQLException
  {
    return this.callableStatement.getREF(paramInt);
  }

  public ROWID getROWID(int paramInt)
    throws SQLException
  {
    return this.callableStatement.getROWID(paramInt);
  }

  public STRUCT getSTRUCT(int paramInt)
    throws SQLException
  {
    return this.callableStatement.getSTRUCT(paramInt);
  }

  public TIMESTAMPLTZ getTIMESTAMPLTZ(int paramInt)
    throws SQLException
  {
    return this.callableStatement.getTIMESTAMPLTZ(paramInt);
  }

  public TIMESTAMPTZ getTIMESTAMPTZ(int paramInt)
    throws SQLException
  {
    return this.callableStatement.getTIMESTAMPTZ(paramInt);
  }

  public TIMESTAMP getTIMESTAMP(int paramInt)
    throws SQLException
  {
    return this.callableStatement.getTIMESTAMP(paramInt);
  }

  public InputStream getAsciiStream(int paramInt)
    throws SQLException
  {
    return this.callableStatement.getAsciiStream(paramInt);
  }

  public InputStream getBinaryStream(int paramInt)
    throws SQLException
  {
    return this.callableStatement.getBinaryStream(paramInt);
  }

  public Reader getCharacterStream(int paramInt)
    throws SQLException
  {
    return this.callableStatement.getCharacterStream(paramInt);
  }

  public Reader getNCharacterStream(int paramInt)
    throws SQLException
  {
    return this.callableStatement.getNCharacterStream(paramInt);
  }

  public InputStream getUnicodeStream(int paramInt)
    throws SQLException
  {
    return this.callableStatement.getUnicodeStream(paramInt);
  }

  public Array getArray(String paramString)
    throws SQLException
  {
    return this.callableStatement.getArray(paramString);
  }

  public BigDecimal getBigDecimal(String paramString)
    throws SQLException
  {
    return this.callableStatement.getBigDecimal(paramString);
  }

  public BigDecimal getBigDecimal(String paramString, int paramInt)
    throws SQLException
  {
    return this.callableStatement.getBigDecimal(paramString, paramInt);
  }

  public Blob getBlob(String paramString)
    throws SQLException
  {
    return this.callableStatement.getBlob(paramString);
  }

  public boolean getBoolean(String paramString)
    throws SQLException
  {
    return this.callableStatement.getBoolean(paramString);
  }

  public byte getByte(String paramString)
    throws SQLException
  {
    return this.callableStatement.getByte(paramString);
  }

  public byte[] getBytes(String paramString)
    throws SQLException
  {
    return this.callableStatement.getBytes(paramString);
  }

  public Clob getClob(String paramString)
    throws SQLException
  {
    return this.callableStatement.getClob(paramString);
  }

  public Date getDate(String paramString)
    throws SQLException
  {
    return this.callableStatement.getDate(paramString);
  }

  public Date getDate(String paramString, Calendar paramCalendar)
    throws SQLException
  {
    return this.callableStatement.getDate(paramString, paramCalendar);
  }

  public double getDouble(String paramString)
    throws SQLException
  {
    return this.callableStatement.getDouble(paramString);
  }

  public float getFloat(String paramString)
    throws SQLException
  {
    return this.callableStatement.getFloat(paramString);
  }

  public int getInt(String paramString)
    throws SQLException
  {
    return this.callableStatement.getInt(paramString);
  }

  public long getLong(String paramString)
    throws SQLException
  {
    return this.callableStatement.getLong(paramString);
  }

  public NClob getNClob(String paramString)
    throws SQLException
  {
    return this.callableStatement.getNClob(paramString);
  }

  public String getNString(String paramString)
    throws SQLException
  {
    return this.callableStatement.getNString(paramString);
  }

  public Object getObject(String paramString)
    throws SQLException
  {
    return this.callableStatement.getObject(paramString);
  }

  public Object getObject(String paramString, Map paramMap)
    throws SQLException
  {
    return this.callableStatement.getObject(paramString, paramMap);
  }

  public Ref getRef(String paramString)
    throws SQLException
  {
    return this.callableStatement.getRef(paramString);
  }

  public RowId getRowId(String paramString)
    throws SQLException
  {
    return this.callableStatement.getRowId(paramString);
  }

  public short getShort(String paramString)
    throws SQLException
  {
    return this.callableStatement.getShort(paramString);
  }

  public SQLXML getSQLXML(String paramString)
    throws SQLException
  {
    return this.callableStatement.getSQLXML(paramString);
  }

  public String getString(String paramString)
    throws SQLException
  {
    return this.callableStatement.getString(paramString);
  }

  public Time getTime(String paramString)
    throws SQLException
  {
    return this.callableStatement.getTime(paramString);
  }

  public Time getTime(String paramString, Calendar paramCalendar)
    throws SQLException
  {
    return this.callableStatement.getTime(paramString, paramCalendar);
  }

  public Timestamp getTimestamp(String paramString)
    throws SQLException
  {
    return this.callableStatement.getTimestamp(paramString);
  }

  public Timestamp getTimestamp(String paramString, Calendar paramCalendar)
    throws SQLException
  {
    return this.callableStatement.getTimestamp(paramString, paramCalendar);
  }

  public URL getURL(String paramString)
    throws SQLException
  {
    return this.callableStatement.getURL(paramString);
  }

  public InputStream getAsciiStream(String paramString)
    throws SQLException
  {
    return this.callableStatement.getAsciiStream(paramString);
  }

  public InputStream getBinaryStream(String paramString)
    throws SQLException
  {
    return this.callableStatement.getBinaryStream(paramString);
  }

  public Reader getCharacterStream(String paramString)
    throws SQLException
  {
    return this.callableStatement.getCharacterStream(paramString);
  }

  public Reader getNCharacterStream(String paramString)
    throws SQLException
  {
    return this.callableStatement.getNCharacterStream(paramString);
  }

  public InputStream getUnicodeStream(String paramString)
    throws SQLException
  {
    return this.callableStatement.getUnicodeStream(paramString);
  }

  public void setObject(String paramString, Object paramObject, int paramInt1, int paramInt2)
    throws SQLException
  {
    this.callableStatement.setObject(paramString, paramObject, paramInt1, paramInt2);
  }

  public Object getAnyDataEmbeddedObject(int paramInt)
    throws SQLException
  {
    return this.callableStatement.getAnyDataEmbeddedObject(paramInt);
  }

  public void setStructDescriptor(int paramInt, StructDescriptor paramStructDescriptor)
    throws SQLException
  {
    this.callableStatement.setStructDescriptor(paramInt, paramStructDescriptor);
  }

  public void setStructDescriptor(String paramString, StructDescriptor paramStructDescriptor)
    throws SQLException
  {
    this.callableStatement.setStructDescriptor(paramString, paramStructDescriptor);
  }

  public void close()
    throws SQLException
  {
    super.close();
  }

  public void closeWithKey(String paramString)
    throws SQLException
  {
    this.callableStatement.closeWithKey(paramString);
    this.statement = (this.preparedStatement = this.callableStatement = closedStatement);
  }

  public void registerOutParameter(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    throws SQLException
  {
    this.callableStatement.registerOutParameter(paramInt1, paramInt2, paramInt3, paramInt4);
  }

  public void registerOutParameterBytes(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    throws SQLException
  {
    this.callableStatement.registerOutParameterBytes(paramInt1, paramInt2, paramInt3, paramInt4);
  }

  public void registerOutParameterChars(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    throws SQLException
  {
    this.callableStatement.registerOutParameterChars(paramInt1, paramInt2, paramInt3, paramInt4);
  }

  public void registerIndexTableOutParameter(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    throws SQLException
  {
    this.callableStatement.registerIndexTableOutParameter(paramInt1, paramInt2, paramInt3, paramInt4);
  }

  public void registerOutParameter(String paramString, int paramInt1, int paramInt2, int paramInt3)
    throws SQLException
  {
    this.callableStatement.registerOutParameter(paramString, paramInt1, paramInt2, paramInt3);
  }

  public void registerOutParameter(String paramString1, int paramInt, String paramString2)
    throws SQLException
  {
    this.callableStatement.registerOutParameter(paramString1, paramInt, paramString2);
  }

  public int sendBatch()
    throws SQLException
  {
    return this.callableStatement.sendBatch();
  }

  public void setExecuteBatch(int paramInt)
    throws SQLException
  {
    this.callableStatement.setExecuteBatch(paramInt);
  }

  public Object getPlsqlIndexTable(int paramInt)
    throws SQLException
  {
    return this.callableStatement.getPlsqlIndexTable(paramInt);
  }

  public Object getPlsqlIndexTable(int paramInt, Class paramClass)
    throws SQLException
  {
    return this.callableStatement.getPlsqlIndexTable(paramInt, paramClass);
  }

  public Datum[] getOraclePlsqlIndexTable(int paramInt)
    throws SQLException
  {
    return this.callableStatement.getOraclePlsqlIndexTable(paramInt);
  }

  public void setStringForClob(String paramString1, String paramString2)
    throws SQLException
  {
    this.callableStatement.setStringForClob(paramString1, paramString2);
  }

  public void setBytesForBlob(String paramString, byte[] paramArrayOfByte)
    throws SQLException
  {
    this.callableStatement.setBytesForBlob(paramString, paramArrayOfByte);
  }

  public boolean wasNull()
    throws SQLException
  {
    return this.callableStatement.wasNull();
  }

  public void registerOutParameter(int paramInt1, int paramInt2)
    throws SQLException
  {
    this.callableStatement.registerOutParameter(paramInt1, paramInt2);
  }

  public void registerOutParameter(int paramInt1, int paramInt2, int paramInt3)
    throws SQLException
  {
    this.callableStatement.registerOutParameter(paramInt1, paramInt2, paramInt3);
  }

  public void registerOutParameter(int paramInt1, int paramInt2, String paramString)
    throws SQLException
  {
    this.callableStatement.registerOutParameter(paramInt1, paramInt2, paramString);
  }

  public void registerOutParameter(String paramString, int paramInt)
    throws SQLException
  {
    this.callableStatement.registerOutParameter(paramString, paramInt);
  }

  public void registerOutParameter(String paramString, int paramInt1, int paramInt2)
    throws SQLException
  {
    this.callableStatement.registerOutParameter(paramString, paramInt1, paramInt2);
  }

  public byte[] privateGetBytes(int paramInt)
    throws SQLException
  {
    return ((OracleCallableStatement)this.callableStatement).privateGetBytes(paramInt);
  }
}