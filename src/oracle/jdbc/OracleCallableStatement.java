package oracle.jdbc;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Date;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
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

public abstract interface OracleCallableStatement extends CallableStatement, OraclePreparedStatement
{
  public abstract ARRAY getARRAY(int paramInt)
    throws SQLException;

  public abstract InputStream getAsciiStream(int paramInt)
    throws SQLException;

  public abstract BFILE getBFILE(int paramInt)
    throws SQLException;

  public abstract BFILE getBfile(int paramInt)
    throws SQLException;

  public abstract InputStream getBinaryStream(int paramInt)
    throws SQLException;

  public abstract InputStream getBinaryStream(String paramString)
    throws SQLException;

  public abstract BLOB getBLOB(int paramInt)
    throws SQLException;

  public abstract CHAR getCHAR(int paramInt)
    throws SQLException;

  public abstract Reader getCharacterStream(int paramInt)
    throws SQLException;

  public abstract CLOB getCLOB(int paramInt)
    throws SQLException;

  public abstract ResultSet getCursor(int paramInt)
    throws SQLException;

  /** @deprecated */
  public abstract Object getCustomDatum(int paramInt, CustomDatumFactory paramCustomDatumFactory)
    throws SQLException;

  public abstract Object getORAData(int paramInt, ORADataFactory paramORADataFactory)
    throws SQLException;

  /** @deprecated */
  public abstract Object getAnyDataEmbeddedObject(int paramInt)
    throws SQLException;

  public abstract DATE getDATE(int paramInt)
    throws SQLException;

  public abstract NUMBER getNUMBER(int paramInt)
    throws SQLException;

  public abstract OPAQUE getOPAQUE(int paramInt)
    throws SQLException;

  public abstract Datum getOracleObject(int paramInt)
    throws SQLException;

  public abstract RAW getRAW(int paramInt)
    throws SQLException;

  public abstract REF getREF(int paramInt)
    throws SQLException;

  public abstract ROWID getROWID(int paramInt)
    throws SQLException;

  public abstract STRUCT getSTRUCT(int paramInt)
    throws SQLException;

  public abstract INTERVALYM getINTERVALYM(int paramInt)
    throws SQLException;

  public abstract INTERVALDS getINTERVALDS(int paramInt)
    throws SQLException;

  public abstract TIMESTAMP getTIMESTAMP(int paramInt)
    throws SQLException;

  public abstract TIMESTAMPTZ getTIMESTAMPTZ(int paramInt)
    throws SQLException;

  public abstract TIMESTAMPLTZ getTIMESTAMPLTZ(int paramInt)
    throws SQLException;

  public abstract InputStream getUnicodeStream(int paramInt)
    throws SQLException;

  public abstract InputStream getUnicodeStream(String paramString)
    throws SQLException;

  public abstract void registerOutParameter(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    throws SQLException;

  /** @deprecated */
  public abstract void registerOutParameterBytes(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    throws SQLException;

  /** @deprecated */
  public abstract void registerOutParameterChars(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    throws SQLException;

  public abstract int sendBatch()
    throws SQLException;

  public abstract void setExecuteBatch(int paramInt)
    throws SQLException;

  public abstract Object getPlsqlIndexTable(int paramInt)
    throws SQLException;

  public abstract Object getPlsqlIndexTable(int paramInt, Class paramClass)
    throws SQLException;

  public abstract Datum[] getOraclePlsqlIndexTable(int paramInt)
    throws SQLException;

  public abstract void registerIndexTableOutParameter(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    throws SQLException;

  public abstract void setBinaryFloat(String paramString, BINARY_FLOAT paramBINARY_FLOAT)
    throws SQLException;

  public abstract void setBinaryDouble(String paramString, BINARY_DOUBLE paramBINARY_DOUBLE)
    throws SQLException;

  public abstract void setStringForClob(String paramString1, String paramString2)
    throws SQLException;

  public abstract void setBytesForBlob(String paramString, byte[] paramArrayOfByte)
    throws SQLException;

  public abstract void registerOutParameter(String paramString, int paramInt1, int paramInt2, int paramInt3)
    throws SQLException;

  public abstract void setNull(String paramString1, int paramInt, String paramString2)
    throws SQLException;

  public abstract void setNull(String paramString, int paramInt)
    throws SQLException;

  public abstract void setBoolean(String paramString, boolean paramBoolean)
    throws SQLException;

  public abstract void setByte(String paramString, byte paramByte)
    throws SQLException;

  public abstract void setShort(String paramString, short paramShort)
    throws SQLException;

  public abstract void setInt(String paramString, int paramInt)
    throws SQLException;

  public abstract void setLong(String paramString, long paramLong)
    throws SQLException;

  public abstract void setFloat(String paramString, float paramFloat)
    throws SQLException;

  public abstract void setBinaryFloat(String paramString, float paramFloat)
    throws SQLException;

  public abstract void setBinaryDouble(String paramString, double paramDouble)
    throws SQLException;

  public abstract void setDouble(String paramString, double paramDouble)
    throws SQLException;

  public abstract void setBigDecimal(String paramString, BigDecimal paramBigDecimal)
    throws SQLException;

  public abstract void setString(String paramString1, String paramString2)
    throws SQLException;

  public abstract void setFixedCHAR(String paramString1, String paramString2)
    throws SQLException;

  public abstract void setCursor(String paramString, ResultSet paramResultSet)
    throws SQLException;

  public abstract void setROWID(String paramString, ROWID paramROWID)
    throws SQLException;

  public abstract void setRAW(String paramString, RAW paramRAW)
    throws SQLException;

  public abstract void setCHAR(String paramString, CHAR paramCHAR)
    throws SQLException;

  public abstract void setDATE(String paramString, DATE paramDATE)
    throws SQLException;

  public abstract void setNUMBER(String paramString, NUMBER paramNUMBER)
    throws SQLException;

  public abstract void setBLOB(String paramString, BLOB paramBLOB)
    throws SQLException;

  public abstract void setBlob(String paramString, Blob paramBlob)
    throws SQLException;

  public abstract void setCLOB(String paramString, CLOB paramCLOB)
    throws SQLException;

  public abstract void setClob(String paramString, Clob paramClob)
    throws SQLException;

  public abstract void setBFILE(String paramString, BFILE paramBFILE)
    throws SQLException;

  public abstract void setBfile(String paramString, BFILE paramBFILE)
    throws SQLException;

  public abstract void setBytes(String paramString, byte[] paramArrayOfByte)
    throws SQLException;

  public abstract void setDate(String paramString, Date paramDate)
    throws SQLException;

  public abstract void setTime(String paramString, Time paramTime)
    throws SQLException;

  public abstract void setTimestamp(String paramString, Timestamp paramTimestamp)
    throws SQLException;

  public abstract void setINTERVALYM(String paramString, INTERVALYM paramINTERVALYM)
    throws SQLException;

  public abstract void setINTERVALDS(String paramString, INTERVALDS paramINTERVALDS)
    throws SQLException;

  public abstract void setTIMESTAMP(String paramString, TIMESTAMP paramTIMESTAMP)
    throws SQLException;

  public abstract void setTIMESTAMPTZ(String paramString, TIMESTAMPTZ paramTIMESTAMPTZ)
    throws SQLException;

  public abstract void setTIMESTAMPLTZ(String paramString, TIMESTAMPLTZ paramTIMESTAMPLTZ)
    throws SQLException;

  public abstract void setAsciiStream(String paramString, InputStream paramInputStream, int paramInt)
    throws SQLException;

  public abstract void setBinaryStream(String paramString, InputStream paramInputStream, int paramInt)
    throws SQLException;

  public abstract void setUnicodeStream(String paramString, InputStream paramInputStream, int paramInt)
    throws SQLException;

  public abstract void setCharacterStream(String paramString, Reader paramReader, int paramInt)
    throws SQLException;

  public abstract void setDate(String paramString, Date paramDate, Calendar paramCalendar)
    throws SQLException;

  public abstract void setTime(String paramString, Time paramTime, Calendar paramCalendar)
    throws SQLException;

  public abstract void setTimestamp(String paramString, Timestamp paramTimestamp, Calendar paramCalendar)
    throws SQLException;

  public abstract void setURL(String paramString, URL paramURL)
    throws SQLException;

  public abstract void setArray(String paramString, Array paramArray)
    throws SQLException;

  public abstract void setARRAY(String paramString, ARRAY paramARRAY)
    throws SQLException;

  public abstract void setOPAQUE(String paramString, OPAQUE paramOPAQUE)
    throws SQLException;

  public abstract void setStructDescriptor(String paramString, StructDescriptor paramStructDescriptor)
    throws SQLException;

  public abstract void setSTRUCT(String paramString, STRUCT paramSTRUCT)
    throws SQLException;

  public abstract void setCustomDatum(String paramString, CustomDatum paramCustomDatum)
    throws SQLException;

  public abstract void setORAData(String paramString, ORAData paramORAData)
    throws SQLException;

  public abstract void setObject(String paramString, Object paramObject, int paramInt1, int paramInt2)
    throws SQLException;

  public abstract void setObject(String paramString, Object paramObject, int paramInt)
    throws SQLException;

  public abstract void setRefType(String paramString, REF paramREF)
    throws SQLException;

  public abstract void setRef(String paramString, Ref paramRef)
    throws SQLException;

  public abstract void setREF(String paramString, REF paramREF)
    throws SQLException;

  public abstract void setObject(String paramString, Object paramObject)
    throws SQLException;

  public abstract void setOracleObject(String paramString, Datum paramDatum)
    throws SQLException;
}