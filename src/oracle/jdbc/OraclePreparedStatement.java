package oracle.jdbc;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLXML;
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

public abstract interface OraclePreparedStatement extends PreparedStatement, OracleStatement
{
  public static final short FORM_NCHAR = 2;
  public static final short FORM_CHAR = 1;

  public abstract void defineParameterTypeBytes(int paramInt1, int paramInt2, int paramInt3)
    throws SQLException;

  public abstract void defineParameterTypeChars(int paramInt1, int paramInt2, int paramInt3)
    throws SQLException;

  public abstract void defineParameterType(int paramInt1, int paramInt2, int paramInt3)
    throws SQLException;

  public abstract int getExecuteBatch();

  public abstract int sendBatch()
    throws SQLException;

  public abstract void setARRAY(int paramInt, ARRAY paramARRAY)
    throws SQLException;

  public abstract void setBfile(int paramInt, BFILE paramBFILE)
    throws SQLException;

  public abstract void setBFILE(int paramInt, BFILE paramBFILE)
    throws SQLException;

  public abstract void setBLOB(int paramInt, BLOB paramBLOB)
    throws SQLException;

  public abstract void setCHAR(int paramInt, CHAR paramCHAR)
    throws SQLException;

  public abstract void setCLOB(int paramInt, CLOB paramCLOB)
    throws SQLException;

  /** @deprecated */
  public abstract void setCursor(int paramInt, ResultSet paramResultSet)
    throws SQLException;

  /** @deprecated */
  public abstract void setCustomDatum(int paramInt, CustomDatum paramCustomDatum)
    throws SQLException;

  public abstract void setORAData(int paramInt, ORAData paramORAData)
    throws SQLException;

  public abstract void setDATE(int paramInt, DATE paramDATE)
    throws SQLException;

  public abstract void setExecuteBatch(int paramInt)
    throws SQLException;

  public abstract void setFixedCHAR(int paramInt, String paramString)
    throws SQLException;

  public abstract void setNUMBER(int paramInt, NUMBER paramNUMBER)
    throws SQLException;

  public abstract void setBinaryFloat(int paramInt, float paramFloat)
    throws SQLException;

  public abstract void setBinaryFloat(int paramInt, BINARY_FLOAT paramBINARY_FLOAT)
    throws SQLException;

  public abstract void setBinaryDouble(int paramInt, double paramDouble)
    throws SQLException;

  public abstract void setBinaryDouble(int paramInt, BINARY_DOUBLE paramBINARY_DOUBLE)
    throws SQLException;

  public abstract void setOPAQUE(int paramInt, OPAQUE paramOPAQUE)
    throws SQLException;

  public abstract void setOracleObject(int paramInt, Datum paramDatum)
    throws SQLException;

  public abstract void setStructDescriptor(int paramInt, StructDescriptor paramStructDescriptor)
    throws SQLException;

  public abstract void setRAW(int paramInt, RAW paramRAW)
    throws SQLException;

  public abstract void setREF(int paramInt, REF paramREF)
    throws SQLException;

  public abstract void setRefType(int paramInt, REF paramREF)
    throws SQLException;

  public abstract void setROWID(int paramInt, ROWID paramROWID)
    throws SQLException;

  public abstract void setSTRUCT(int paramInt, STRUCT paramSTRUCT)
    throws SQLException;

  public abstract void setTIMESTAMP(int paramInt, TIMESTAMP paramTIMESTAMP)
    throws SQLException;

  public abstract void setTIMESTAMPTZ(int paramInt, TIMESTAMPTZ paramTIMESTAMPTZ)
    throws SQLException;

  public abstract void setTIMESTAMPLTZ(int paramInt, TIMESTAMPLTZ paramTIMESTAMPLTZ)
    throws SQLException;

  public abstract void setINTERVALYM(int paramInt, INTERVALYM paramINTERVALYM)
    throws SQLException;

  public abstract void setINTERVALDS(int paramInt, INTERVALDS paramINTERVALDS)
    throws SQLException;

  public abstract void setNullAtName(String paramString1, int paramInt, String paramString2)
    throws SQLException;

  public abstract void setNullAtName(String paramString, int paramInt)
    throws SQLException;

  public abstract void setBooleanAtName(String paramString, boolean paramBoolean)
    throws SQLException;

  public abstract void setByteAtName(String paramString, byte paramByte)
    throws SQLException;

  public abstract void setShortAtName(String paramString, short paramShort)
    throws SQLException;

  public abstract void setIntAtName(String paramString, int paramInt)
    throws SQLException;

  public abstract void setLongAtName(String paramString, long paramLong)
    throws SQLException;

  public abstract void setFloatAtName(String paramString, float paramFloat)
    throws SQLException;

  public abstract void setDoubleAtName(String paramString, double paramDouble)
    throws SQLException;

  public abstract void setBinaryFloatAtName(String paramString, float paramFloat)
    throws SQLException;

  public abstract void setBinaryFloatAtName(String paramString, BINARY_FLOAT paramBINARY_FLOAT)
    throws SQLException;

  public abstract void setBinaryDoubleAtName(String paramString, double paramDouble)
    throws SQLException;

  public abstract void setBinaryDoubleAtName(String paramString, BINARY_DOUBLE paramBINARY_DOUBLE)
    throws SQLException;

  public abstract void setBigDecimalAtName(String paramString, BigDecimal paramBigDecimal)
    throws SQLException;

  public abstract void setStringAtName(String paramString1, String paramString2)
    throws SQLException;

  public abstract void setStringForClob(int paramInt, String paramString)
    throws SQLException;

  public abstract void setStringForClobAtName(String paramString1, String paramString2)
    throws SQLException;

  public abstract void setFixedCHARAtName(String paramString1, String paramString2)
    throws SQLException;

  public abstract void setCursorAtName(String paramString, ResultSet paramResultSet)
    throws SQLException;

  public abstract void setROWIDAtName(String paramString, ROWID paramROWID)
    throws SQLException;

  public abstract void setArrayAtName(String paramString, Array paramArray)
    throws SQLException;

  public abstract void setARRAYAtName(String paramString, ARRAY paramARRAY)
    throws SQLException;

  public abstract void setOPAQUEAtName(String paramString, OPAQUE paramOPAQUE)
    throws SQLException;

  public abstract void setStructDescriptorAtName(String paramString, StructDescriptor paramStructDescriptor)
    throws SQLException;

  public abstract void setSTRUCTAtName(String paramString, STRUCT paramSTRUCT)
    throws SQLException;

  public abstract void setRAWAtName(String paramString, RAW paramRAW)
    throws SQLException;

  public abstract void setCHARAtName(String paramString, CHAR paramCHAR)
    throws SQLException;

  public abstract void setDATEAtName(String paramString, DATE paramDATE)
    throws SQLException;

  public abstract void setNUMBERAtName(String paramString, NUMBER paramNUMBER)
    throws SQLException;

  public abstract void setBLOBAtName(String paramString, BLOB paramBLOB)
    throws SQLException;

  public abstract void setBlobAtName(String paramString, Blob paramBlob)
    throws SQLException;

  public abstract void setBlobAtName(String paramString, InputStream paramInputStream, long paramLong)
    throws SQLException;

  public abstract void setBlobAtName(String paramString, InputStream paramInputStream)
    throws SQLException;

  public abstract void setCLOBAtName(String paramString, CLOB paramCLOB)
    throws SQLException;

  public abstract void setClobAtName(String paramString, Clob paramClob)
    throws SQLException;

  public abstract void setClobAtName(String paramString, Reader paramReader, long paramLong)
    throws SQLException;

  public abstract void setClobAtName(String paramString, Reader paramReader)
    throws SQLException;

  public abstract void setBFILEAtName(String paramString, BFILE paramBFILE)
    throws SQLException;

  public abstract void setBfileAtName(String paramString, BFILE paramBFILE)
    throws SQLException;

  public abstract void setBytesAtName(String paramString, byte[] paramArrayOfByte)
    throws SQLException;

  public abstract void setBytesForBlob(int paramInt, byte[] paramArrayOfByte)
    throws SQLException;

  public abstract void setBytesForBlobAtName(String paramString, byte[] paramArrayOfByte)
    throws SQLException;

  public abstract void setDateAtName(String paramString, Date paramDate)
    throws SQLException;

  public abstract void setDateAtName(String paramString, Date paramDate, Calendar paramCalendar)
    throws SQLException;

  public abstract void setTimeAtName(String paramString, Time paramTime)
    throws SQLException;

  public abstract void setTimeAtName(String paramString, Time paramTime, Calendar paramCalendar)
    throws SQLException;

  public abstract void setTimestampAtName(String paramString, Timestamp paramTimestamp)
    throws SQLException;

  public abstract void setTimestampAtName(String paramString, Timestamp paramTimestamp, Calendar paramCalendar)
    throws SQLException;

  public abstract void setINTERVALYMAtName(String paramString, INTERVALYM paramINTERVALYM)
    throws SQLException;

  public abstract void setINTERVALDSAtName(String paramString, INTERVALDS paramINTERVALDS)
    throws SQLException;

  public abstract void setTIMESTAMPAtName(String paramString, TIMESTAMP paramTIMESTAMP)
    throws SQLException;

  public abstract void setTIMESTAMPTZAtName(String paramString, TIMESTAMPTZ paramTIMESTAMPTZ)
    throws SQLException;

  public abstract void setTIMESTAMPLTZAtName(String paramString, TIMESTAMPLTZ paramTIMESTAMPLTZ)
    throws SQLException;

  public abstract void setAsciiStreamAtName(String paramString, InputStream paramInputStream, int paramInt)
    throws SQLException;

  public abstract void setAsciiStreamAtName(String paramString, InputStream paramInputStream, long paramLong)
    throws SQLException;

  public abstract void setAsciiStreamAtName(String paramString, InputStream paramInputStream)
    throws SQLException;

  public abstract void setBinaryStreamAtName(String paramString, InputStream paramInputStream, int paramInt)
    throws SQLException;

  public abstract void setBinaryStreamAtName(String paramString, InputStream paramInputStream, long paramLong)
    throws SQLException;

  public abstract void setBinaryStreamAtName(String paramString, InputStream paramInputStream)
    throws SQLException;

  public abstract void setCharacterStreamAtName(String paramString, Reader paramReader, long paramLong)
    throws SQLException;

  public abstract void setCharacterStreamAtName(String paramString, Reader paramReader)
    throws SQLException;

  public abstract void setUnicodeStreamAtName(String paramString, InputStream paramInputStream, int paramInt)
    throws SQLException;

  public abstract void setCustomDatumAtName(String paramString, CustomDatum paramCustomDatum)
    throws SQLException;

  public abstract void setORADataAtName(String paramString, ORAData paramORAData)
    throws SQLException;

  public abstract void setObjectAtName(String paramString, Object paramObject, int paramInt1, int paramInt2)
    throws SQLException;

  public abstract void setObjectAtName(String paramString, Object paramObject, int paramInt)
    throws SQLException;

  public abstract void setRefTypeAtName(String paramString, REF paramREF)
    throws SQLException;

  public abstract void setRefAtName(String paramString, Ref paramRef)
    throws SQLException;

  public abstract void setREFAtName(String paramString, REF paramREF)
    throws SQLException;

  public abstract void setObjectAtName(String paramString, Object paramObject)
    throws SQLException;

  public abstract void setOracleObjectAtName(String paramString, Datum paramDatum)
    throws SQLException;

  public abstract void setURLAtName(String paramString, URL paramURL)
    throws SQLException;

  public abstract void setCheckBindTypes(boolean paramBoolean);

  public abstract void setPlsqlIndexTable(int paramInt1, Object paramObject, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
    throws SQLException;

  public abstract void setFormOfUse(int paramInt, short paramShort);

  public abstract void setDisableStmtCaching(boolean paramBoolean);

  public abstract OracleParameterMetaData OracleGetParameterMetaData()
    throws SQLException;

  public abstract void registerReturnParameter(int paramInt1, int paramInt2)
    throws SQLException;

  public abstract void registerReturnParameter(int paramInt1, int paramInt2, int paramInt3)
    throws SQLException;

  public abstract void registerReturnParameter(int paramInt1, int paramInt2, String paramString)
    throws SQLException;

  public abstract ResultSet getReturnResultSet()
    throws SQLException;

  public abstract void setNCharacterStreamAtName(String paramString, Reader paramReader, long paramLong)
    throws SQLException;

  public abstract void setNCharacterStreamAtName(String paramString, Reader paramReader)
    throws SQLException;

  public abstract void setNClobAtName(String paramString, NClob paramNClob)
    throws SQLException;

  public abstract void setNClobAtName(String paramString, Reader paramReader, long paramLong)
    throws SQLException;

  public abstract void setNClobAtName(String paramString, Reader paramReader)
    throws SQLException;

  public abstract void setNStringAtName(String paramString1, String paramString2)
    throws SQLException;

  public abstract void setRowIdAtName(String paramString, RowId paramRowId)
    throws SQLException;

  public abstract void setSQLXMLAtName(String paramString, SQLXML paramSQLXML)
    throws SQLException;
}