package oracle.jdbc.driver;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Date;
import java.sql.NClob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;
import oracle.jdbc.OracleResultSet.AuthorizationIndicator;
import oracle.jdbc.OracleResultSetMetaData.SecurityAttribute;
import oracle.jdbc.internal.OracleConnection;
import oracle.jdbc.oracore.OracleType;
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

abstract class Accessor
{
  static final int FIXED_CHAR = 999;
  static final int CHAR = 96;
  static final int VARCHAR = 1;
  static final int VCS = 9;
  static final int LONG = 8;
  static final int NUMBER = 2;
  static final int VARNUM = 6;
  static final int BINARY_FLOAT = 100;
  static final int BINARY_DOUBLE = 101;
  static final int RAW = 23;
  static final int VBI = 15;
  static final int LONG_RAW = 24;
  static final int ROWID = 104;
  static final int RESULT_SET = 102;
  static final int RSET = 116;
  static final int DATE = 12;
  static final int BLOB = 113;
  static final int CLOB = 112;
  static final int BFILE = 114;
  static final int NAMED_TYPE = 109;
  static final int REF_TYPE = 111;
  static final int TIMESTAMP = 180;
  static final int TIMESTAMPTZ = 181;
  static final int TIMESTAMPLTZ = 231;
  static final int INTERVALYM = 182;
  static final int INTERVALDS = 183;
  static final int UROWID = 208;
  static final int PLSQL_INDEX_TABLE = 998;
  static final int T2S_OVERLONG_RAW = 997;
  static final int SET_CHAR_BYTES = 996;
  static final int NULL_TYPE = 995;
  static final int DML_RETURN_PARAM = 994;
  static final int ONLY_FORM_USABLE = 0;
  static final int NOT_USABLE = 1;
  static final int NO_NEED_TO_PREPARE = 2;
  static final int NEED_TO_PREPARE = 3;
  OracleStatement statement;
  boolean outBind;
  int internalType;
  int internalTypeMaxLength;
  boolean isStream = false;

  boolean isColumnNumberAware = false;

  short formOfUse = 2;
  OracleType internalOtype;
  int externalType;
  String internalTypeName;
  String columnName;
  int describeType;
  int describeMaxLength;
  boolean nullable;
  int precision;
  int scale;
  int flags;
  int contflag;
  int total_elems;
  OracleType describeOtype;
  String describeTypeName;
  int definedColumnType = 0;
  int definedColumnSize = 0;
  int oacmxl = 0;

  short udskpos = -1;

  int lobPrefetchSizeForThisColumn = -1;

  long[] prefetchedLobSize = null;

  int[] prefetchedLobChunkSize = null;

  byte[] prefetchedClobFormOfUse = null;

  byte[][] prefetchedLobData = (byte[][])null;
  char[][] prefetchedLobCharData = (char[][])null;

  int[] prefetchedLobDataL = null;
  OracleResultSetMetaData.SecurityAttribute securityAttribute;
  byte[] rowSpaceByte = null;
  char[] rowSpaceChar = null;
  short[] rowSpaceIndicator = null;
  static final byte DATA_UNAUTHORIZED = 1;
  byte[] rowSpaceMetaData = null;

  int columnIndex = 0;
  int lengthIndex = 0;
  int indicatorIndex = 0;
  int metaDataIndex = 0;
  int columnIndexLastRow = 0;
  int lengthIndexLastRow = 0;
  int indicatorIndexLastRow = 0;
  int metaDataIndexLastRow = 0;
  int byteLength = 0;
  int charLength = 0;
  int defineType;
  boolean isDMLReturnedParam = false;

  int lastRowProcessed = 0;

  boolean isUseLess = false;

  int physicalColumnIndex = -2;

  boolean isNullByDescribe = false;

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  void setOffsets(int paramInt)
  {
    this.columnIndex = this.statement.defineByteSubRange;
    this.statement.defineByteSubRange = (this.columnIndex + paramInt * this.byteLength);
  }

  void init(OracleStatement paramOracleStatement, int paramInt1, int paramInt2, short paramShort, boolean paramBoolean)
    throws SQLException
  {
    this.statement = paramOracleStatement;
    this.outBind = paramBoolean;
    this.internalType = paramInt1;
    this.defineType = paramInt2;
    this.formOfUse = paramShort;
  }

  abstract void initForDataAccess(int paramInt1, int paramInt2, String paramString)
    throws SQLException;

  void initForDescribe(int paramInt1, int paramInt2, boolean paramBoolean, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, short paramShort)
    throws SQLException
  {
    this.describeType = paramInt1;
    this.describeMaxLength = paramInt2;
    this.nullable = paramBoolean;
    this.precision = paramInt4;
    this.scale = paramInt5;
    this.flags = paramInt3;
    this.contflag = paramInt6;
    this.total_elems = paramInt7;
    this.formOfUse = paramShort;
  }

  void initForDescribe(int paramInt1, int paramInt2, boolean paramBoolean, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, short paramShort, String paramString)
    throws SQLException
  {
    this.describeTypeName = paramString;
    this.describeOtype = null;

    initForDescribe(paramInt1, paramInt2, paramBoolean, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramShort);
  }

  OracleInputStream initForNewRow()
    throws SQLException
  {
    unimpl("initForNewRow");
    return null;
  }

  int useForDataAccessIfPossible(int paramInt1, int paramInt2, int paramInt3, String paramString)
    throws SQLException
  {
    int i = 3;
    int j = 0;
    int k = 0;

    if (this.internalType != 0)
    {
      if (this.internalType != paramInt1) {
        i = 0;
      } else if (this.rowSpaceIndicator != null)
      {
        j = this.byteLength;
        k = this.charLength;
      }
    }

    if (i == 3)
    {
      initForDataAccess(paramInt2, paramInt3, paramString);

      if ((!this.outBind) && (j >= this.byteLength) && (k >= this.charLength))
      {
        i = 2;
      }
    }
    return i;
  }

  boolean useForDescribeIfPossible(int paramInt1, int paramInt2, boolean paramBoolean, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, short paramShort, String paramString)
    throws SQLException
  {
    if ((this.externalType == 0) && (paramInt1 != this.describeType)) {
      return false;
    }
    initForDescribe(paramInt1, paramInt2, paramBoolean, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramShort, paramString);

    return true;
  }

  void setFormOfUse(short paramShort)
  {
    this.formOfUse = paramShort;
  }

  void updateColumnNumber(int paramInt)
  {
  }

  public String toString()
  {
    return super.toString() + ", statement=" + this.statement + ", outBind=" + this.outBind + ", internalType=" + this.internalType + ", internalTypeMaxLength=" + this.internalTypeMaxLength + ", isStream=" + this.isStream + ", formOfUse=" + this.formOfUse + ", internalOtype=" + this.internalOtype + ", externalType=" + this.externalType + ", internalTypeName=" + this.internalTypeName + ", columnName=" + this.columnName + ", describeType=" + this.describeType + ", describeMaxLength=" + this.describeMaxLength + ", nullable=" + this.nullable + ", precision=" + this.precision + ", scale=" + this.scale + ", flags=" + this.flags + ", contflag=" + this.contflag + ", total_elems=" + this.total_elems + ", describeOtype=" + this.describeOtype + ", describeTypeName=" + this.describeTypeName + ", rowSpaceByte=" + this.rowSpaceByte + ", rowSpaceChar=" + this.rowSpaceChar + ", rowSpaceIndicator=" + this.rowSpaceIndicator + ", columnIndex=" + this.columnIndex + ", lengthIndex=" + this.lengthIndex + ", indicatorIndex=" + this.indicatorIndex + ", byteLength=" + this.byteLength + ", charLength=" + this.charLength;
  }

  void unimpl(String paramString)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4, paramString + " not implemented for " + getClass());

    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  boolean getBoolean(int paramInt)
    throws SQLException
  {
    if (this.rowSpaceIndicator == null)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 21);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] == -1) {
      return false;
    }
    unimpl("getBoolean");

    return false;
  }

  byte getByte(int paramInt)
    throws SQLException
  {
    if (this.rowSpaceIndicator == null)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 21);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] == -1) {
      return 0;
    }
    unimpl("getByte");

    return 0;
  }

  OracleResultSet.AuthorizationIndicator getAuthorizationIndicator(int paramInt)
    throws SQLException
  {
    if (this.rowSpaceMetaData == null)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 21);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    int i = this.rowSpaceMetaData[(this.metaDataIndex + 1 * paramInt)];

    if ((i & 0x1) != 0)
      return OracleResultSet.AuthorizationIndicator.UNAUTHORIZED;
    if ((this.securityAttribute == OracleResultSetMetaData.SecurityAttribute.ENABLED) || (this.securityAttribute == OracleResultSetMetaData.SecurityAttribute.NONE))
    {
      return OracleResultSet.AuthorizationIndicator.NONE;
    }
    return OracleResultSet.AuthorizationIndicator.UNKNOWN;
  }

  short getShort(int paramInt)
    throws SQLException
  {
    if (this.rowSpaceIndicator == null)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 21);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] == -1) {
      return 0;
    }
    unimpl("getShort");

    return 0;
  }

  int getInt(int paramInt)
    throws SQLException
  {
    if (this.rowSpaceIndicator == null)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 21);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] == -1) {
      return 0;
    }
    unimpl("getInt");

    return 0;
  }

  long getLong(int paramInt)
    throws SQLException
  {
    if (this.rowSpaceIndicator == null)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 21);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] == -1) {
      return 0L;
    }
    unimpl("getLong");

    return 0L;
  }

  float getFloat(int paramInt)
    throws SQLException
  {
    if (this.rowSpaceIndicator == null)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 21);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] == -1) {
      return 0.0F;
    }
    unimpl("getFloat");

    return 0.0F;
  }

  double getDouble(int paramInt)
    throws SQLException
  {
    if (this.rowSpaceIndicator == null)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 21);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] == -1) {
      return 0.0D;
    }
    unimpl("getDouble");

    return 0.0D;
  }

  BigDecimal getBigDecimal(int paramInt)
    throws SQLException
  {
    if (this.rowSpaceIndicator == null)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 21);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] == -1) {
      return null;
    }
    unimpl("getBigDecimal");

    return null;
  }

  BigDecimal getBigDecimal(int paramInt1, int paramInt2)
    throws SQLException
  {
    if (this.rowSpaceIndicator == null)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 21);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt1)] == -1) {
      return null;
    }
    unimpl("getBigDecimal");

    return null;
  }

  String getString(int paramInt)
    throws SQLException
  {
    return null;
  }

  Date getDate(int paramInt)
    throws SQLException
  {
    if (this.rowSpaceIndicator == null)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 21);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] == -1) {
      return null;
    }
    unimpl("getDate");

    return null;
  }

  Date getDate(int paramInt, Calendar paramCalendar)
    throws SQLException
  {
    if (this.rowSpaceIndicator == null)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 21);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] == -1) {
      return null;
    }
    unimpl("getDate");

    return null;
  }

  Time getTime(int paramInt)
    throws SQLException
  {
    if (this.rowSpaceIndicator == null)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 21);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] == -1) {
      return null;
    }
    unimpl("getTime");

    return null;
  }

  Time getTime(int paramInt, Calendar paramCalendar)
    throws SQLException
  {
    if (this.rowSpaceIndicator == null)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 21);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] == -1) {
      return null;
    }
    unimpl("getTime");

    return null;
  }

  Timestamp getTimestamp(int paramInt)
    throws SQLException
  {
    if (this.rowSpaceIndicator == null)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 21);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] == -1) {
      return null;
    }
    unimpl("getTimestamp");

    return null;
  }

  Timestamp getTimestamp(int paramInt, Calendar paramCalendar)
    throws SQLException
  {
    if (this.rowSpaceIndicator == null)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 21);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] == -1) {
      return null;
    }
    unimpl("getTimestamp");

    return null;
  }

  byte[] privateGetBytes(int paramInt)
    throws SQLException
  {
    return getBytes(paramInt);
  }

  byte[] getBytes(int paramInt)
    throws SQLException
  {
    byte[] arrayOfByte = null;

    if (this.rowSpaceIndicator == null)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 21);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1)
    {
      int i = this.rowSpaceIndicator[(this.lengthIndex + paramInt)];
      int j = this.columnIndex + this.byteLength * paramInt;

      arrayOfByte = new byte[i];
      System.arraycopy(this.rowSpaceByte, j, arrayOfByte, 0, i);
    }

    return arrayOfByte;
  }

  InputStream getAsciiStream(int paramInt)
    throws SQLException
  {
    if (this.rowSpaceIndicator == null)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 21);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] == -1) {
      return null;
    }
    unimpl("getAsciiStream");

    return null;
  }

  InputStream getUnicodeStream(int paramInt)
    throws SQLException
  {
    if (this.rowSpaceIndicator == null)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 21);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] == -1) {
      return null;
    }
    unimpl("getUnicodeStream");

    return null;
  }

  InputStream getBinaryStream(int paramInt)
    throws SQLException
  {
    if (this.rowSpaceIndicator == null)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 21);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] == -1) {
      return null;
    }
    unimpl("getBinaryStream");

    return null;
  }

  Object getObject(int paramInt)
    throws SQLException
  {
    if (this.rowSpaceIndicator == null)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 21);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] == -1) {
      return null;
    }
    unimpl("getObject");

    return null;
  }

  ResultSet getCursor(int paramInt)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  Datum getOracleObject(int paramInt)
    throws SQLException
  {
    if (this.rowSpaceIndicator == null)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 21);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] == -1) {
      return null;
    }
    unimpl("getOracleObject");

    return null;
  }

  ROWID getROWID(int paramInt)
    throws SQLException
  {
    if (this.rowSpaceIndicator == null)
    {
      localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 21);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] == -1) {
      return null;
    }

    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  NUMBER getNUMBER(int paramInt)
    throws SQLException
  {
    if (this.rowSpaceIndicator == null)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 21);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] == -1) {
      return null;
    }
    unimpl("getNUMBER");

    return null;
  }

  DATE getDATE(int paramInt)
    throws SQLException
  {
    if (this.rowSpaceIndicator == null)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 21);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] == -1) {
      return null;
    }
    unimpl("getDATE");

    return null;
  }

  ARRAY getARRAY(int paramInt)
    throws SQLException
  {
    if (this.rowSpaceIndicator == null)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 21);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] == -1) {
      return null;
    }
    unimpl("getARRAY");

    return null;
  }

  STRUCT getSTRUCT(int paramInt)
    throws SQLException
  {
    if (this.rowSpaceIndicator == null)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 21);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] == -1) {
      return null;
    }
    unimpl("getSTRUCT");

    return null;
  }

  OPAQUE getOPAQUE(int paramInt)
    throws SQLException
  {
    if (this.rowSpaceIndicator == null)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 21);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] == -1) {
      return null;
    }
    unimpl("getOPAQUE");

    return null;
  }

  REF getREF(int paramInt)
    throws SQLException
  {
    if (this.rowSpaceIndicator == null)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 21);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] == -1) {
      return null;
    }
    unimpl("getREF");

    return null;
  }

  CHAR getCHAR(int paramInt)
    throws SQLException
  {
    if (this.rowSpaceIndicator == null)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 21);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] == -1) {
      return null;
    }
    unimpl("getCHAR");

    return null;
  }

  RAW getRAW(int paramInt)
    throws SQLException
  {
    if (this.rowSpaceIndicator == null)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 21);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] == -1) {
      return null;
    }
    unimpl("getRAW");

    return null;
  }

  BLOB getBLOB(int paramInt)
    throws SQLException
  {
    if (this.rowSpaceIndicator == null)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 21);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] == -1) {
      return null;
    }
    unimpl("getBLOB");

    return null;
  }

  CLOB getCLOB(int paramInt)
    throws SQLException
  {
    if (this.rowSpaceIndicator == null)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 21);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] == -1) {
      return null;
    }
    unimpl("getCLOB");

    return null;
  }

  BFILE getBFILE(int paramInt)
    throws SQLException
  {
    if (this.rowSpaceIndicator == null)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 21);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] == -1) {
      return null;
    }
    unimpl("getBFILE");

    return null;
  }

  CustomDatum getCustomDatum(int paramInt, CustomDatumFactory paramCustomDatumFactory)
    throws SQLException
  {
    Datum localDatum = getOracleObject(paramInt);

    return paramCustomDatumFactory.create(localDatum, 0);
  }

  ORAData getORAData(int paramInt, ORADataFactory paramORADataFactory)
    throws SQLException
  {
    Datum localDatum = getOracleObject(paramInt);

    return paramORADataFactory.create(localDatum, 0);
  }

  Object getObject(int paramInt, Map paramMap)
    throws SQLException
  {
    if (this.rowSpaceIndicator == null)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 21);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] == -1) {
      return null;
    }
    unimpl("getObject");

    return null;
  }

  Reader getCharacterStream(int paramInt)
    throws SQLException
  {
    if (this.rowSpaceIndicator == null)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 21);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] == -1) {
      return null;
    }
    unimpl("getCharacterStream");

    return null;
  }

  INTERVALYM getINTERVALYM(int paramInt)
    throws SQLException
  {
    if (this.rowSpaceIndicator == null)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 21);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] == -1) {
      return null;
    }
    unimpl("getINTERVALYM");

    return null;
  }

  INTERVALDS getINTERVALDS(int paramInt)
    throws SQLException
  {
    if (this.rowSpaceIndicator == null)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 21);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] == -1) {
      return null;
    }
    unimpl("getINTERVALDS");

    return null;
  }

  TIMESTAMP getTIMESTAMP(int paramInt)
    throws SQLException
  {
    if (this.rowSpaceIndicator == null)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 21);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] == -1) {
      return null;
    }
    unimpl("getTIMESTAMP");

    return null;
  }

  TIMESTAMPTZ getTIMESTAMPTZ(int paramInt)
    throws SQLException
  {
    if (this.rowSpaceIndicator == null)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 21);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] == -1) {
      return null;
    }
    unimpl("getTIMESTAMPTZ");

    return null;
  }

  TIMESTAMPLTZ getTIMESTAMPLTZ(int paramInt)
    throws SQLException
  {
    if (this.rowSpaceIndicator == null)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 21);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] == -1) {
      return null;
    }
    unimpl("getTIMESTAMPLTZ");

    return null;
  }

  URL getURL(int paramInt)
    throws SQLException
  {
    if (this.rowSpaceIndicator == null)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 21);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] == -1) {
      return null;
    }
    unimpl("getURL");

    return null;
  }

  Datum[] getOraclePlsqlIndexTable(int paramInt)
    throws SQLException
  {
    if (this.rowSpaceIndicator == null)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 21);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] == -1) {
      return null;
    }
    unimpl("getOraclePlsqlIndexTable");

    return null;
  }

  NClob getNClob(int paramInt)
    throws SQLException
  {
    if (this.formOfUse != 2)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4, Integer.valueOf(this.columnIndex));
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    return (NClob)getCLOB(paramInt);
  }

  String getNString(int paramInt)
    throws SQLException
  {
    return getString(paramInt);
  }

  SQLXML getSQLXML(int paramInt)
    throws SQLException
  {
    if (this.rowSpaceIndicator == null)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 21);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] == -1) {
      return null;
    }
    unimpl("getSQLXML");

    return null;
  }

  Reader getNCharacterStream(int paramInt)
    throws SQLException
  {
    return getCharacterStream(paramInt);
  }

  boolean isNull(int paramInt)
    throws SQLException
  {
    if (this.rowSpaceIndicator == null)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 21);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    return this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] == -1;
  }

  void setNull(int paramInt, boolean paramBoolean)
    throws SQLException
  {
    if (this.rowSpaceIndicator == null)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 21);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] = ((short)(paramBoolean ? -1 : 0));
  }

  void fetchNextColumns()
    throws SQLException
  {
  }

  void calculateSizeTmpByteArray()
  {
  }

  boolean unmarshalOneRow()
    throws SQLException, IOException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 148);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  void copyRow()
    throws SQLException, IOException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 148);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  int readStream(byte[] paramArrayOfByte, int paramInt)
    throws SQLException, IOException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 148);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  void initMetadata()
    throws SQLException
  {
  }

  void setDisplaySize(int paramInt)
    throws SQLException
  {
    this.describeMaxLength = paramInt;
  }

  void saveDataFromOldDefineBuffers(byte[] paramArrayOfByte, char[] paramArrayOfChar, short[] paramArrayOfShort, int paramInt1, int paramInt2)
    throws SQLException
  {
  }

  protected OracleConnection getConnectionDuringExceptionHandling()
  {
    return this.statement.getConnectionDuringExceptionHandling();
  }
}