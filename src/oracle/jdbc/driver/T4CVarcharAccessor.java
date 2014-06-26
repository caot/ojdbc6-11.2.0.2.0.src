package oracle.jdbc.driver;

import java.io.IOException;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Properties;
import oracle.sql.DATE;
import oracle.sql.Datum;
import oracle.sql.NUMBER;
import oracle.sql.RAW;
import oracle.sql.ROWID;
import oracle.sql.TIMESTAMP;
import oracle.sql.TIMESTAMPLTZ;
import oracle.sql.TIMESTAMPTZ;

class T4CVarcharAccessor extends VarcharAccessor
{
  T4CMAREngine mare;
  static final int t4MaxLength = 4000;
  static final int t4CallMaxLength = 4001;
  static final int t4PlsqlMaxLength = 32766;
  static final int t4SqlMinLength = 32;
  boolean underlyingLong = false;

  final int[] meta = new int[1];
  final int[] tmp = new int[1];
  final int[] escapeSequenceArr = new int[1];
  final boolean[] readHeaderArr = new boolean[1];
  final boolean[] readAsNonStreamArr = new boolean[1];
  static final int NONE = -1;
  static final int DAY = 1;
  static final int MM_MONTH = 2;
  static final int FULL_MONTH = 3;
  static final int MON_MONTH = 4;
  static final int YY_YEAR = 5;
  static final int RR_YEAR = 6;
  static final int HH_HOUR = 7;
  static final int HH24_HOUR = 8;
  static final int MINUTE = 9;
  static final int SECOND = 10;
  static final int NSECOND = 11;
  static final int AM = 12;
  static final int TZR = 13;
  static final int TZH = 14;
  static final int TZM = 15;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  T4CVarcharAccessor(OracleStatement paramOracleStatement, int paramInt1, short paramShort, int paramInt2, boolean paramBoolean, T4CMAREngine paramT4CMAREngine)
    throws SQLException
  {
    super(paramOracleStatement, paramInt1, paramShort, paramInt2, paramBoolean);

    this.mare = paramT4CMAREngine;

    calculateSizeTmpByteArray();
  }

  T4CVarcharAccessor(OracleStatement paramOracleStatement, int paramInt1, boolean paramBoolean, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, short paramShort, int paramInt7, int paramInt8, int paramInt9, T4CMAREngine paramT4CMAREngine)
    throws SQLException
  {
    super(paramOracleStatement, paramInt1, paramBoolean, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramShort);

    this.mare = paramT4CMAREngine;
    this.definedColumnType = paramInt8;
    this.definedColumnSize = paramInt9;

    calculateSizeTmpByteArray();

    this.oacmxl = paramInt7;

    if (this.oacmxl == -1)
    {
      this.underlyingLong = true;
      this.oacmxl = 4000;
    }
  }

  void processIndicator(int paramInt)
    throws IOException, SQLException
  {
    if (((this.internalType == 1) && (this.describeType == 112)) || ((this.internalType == 23) && (this.describeType == 113)))
    {
      this.mare.unmarshalUB2();
      this.mare.unmarshalUB2();
    }
    else if (this.statement.connection.versionNumber < 9200)
    {
      this.mare.unmarshalSB2();

      if ((this.statement.sqlKind != 32) && (this.statement.sqlKind != 64))
      {
        this.mare.unmarshalSB2();
      }
    } else if ((this.statement.sqlKind == 32) || (this.statement.sqlKind == 64) || (this.isDMLReturnedParam))
    {
      this.mare.processIndicator(paramInt <= 0, paramInt);
    }
  }

  boolean unmarshalOneRow()
    throws SQLException, IOException
  {
    if (this.isUseLess)
    {
      this.lastRowProcessed += 1;

      return false;
    }

    int i = this.indicatorIndex + this.lastRowProcessed;
    int j = this.lengthIndex + this.lastRowProcessed;

    byte[] arrayOfByte1 = this.statement.tmpByteArray;
    int k = this.columnIndex + this.lastRowProcessed * this.charLength;

    if (!this.underlyingLong)
    {
      if (this.rowSpaceIndicator == null)
      {
        byte[] arrayOfByte2 = new byte[16000];

        this.mare.unmarshalCLR(arrayOfByte2, 0, this.meta);
        processIndicator(this.meta[0]);

        this.lastRowProcessed += 1;

        return false;
      }

      if (this.isNullByDescribe)
      {
        this.rowSpaceIndicator[i] = -1;
        this.rowSpaceIndicator[j] = 0;
        this.lastRowProcessed += 1;

        if (this.statement.connection.versionNumber < 9200) {
          processIndicator(0);
        }
        return false;
      }

      if (this.statement.maxFieldSize > 0)
        this.mare.unmarshalCLR(arrayOfByte1, 0, this.meta, this.statement.maxFieldSize);
      else {
        this.mare.unmarshalCLR(arrayOfByte1, 0, this.meta);
      }

    }
    else
    {
      this.escapeSequenceArr[0] = this.mare.unmarshalUB1();

      int m;
      if (this.mare.escapeSequenceNull(this.escapeSequenceArr[0]))
      {
        this.meta[0] = 0;

        this.mare.processIndicator(false, 0);

        m = this.mare.unmarshalUB2();
      }
      else
      {
        m = 0;
        int n = 0;
        byte[] arrayOfByte3 = arrayOfByte1;
        int i1 = 0;

        this.readHeaderArr[0] = true;
        this.readAsNonStreamArr[0] = false;

        while (m != -1)
        {
          if ((arrayOfByte3 == arrayOfByte1) && (n + 255 > arrayOfByte1.length))
          {
            arrayOfByte3 = new byte[255];
          }
          if (arrayOfByte3 == arrayOfByte1)
            i1 = n;
          else {
            i1 = 0;
          }
          m = T4CLongAccessor.readStreamFromWire(arrayOfByte3, i1, 255, this.escapeSequenceArr, this.readHeaderArr, this.readAsNonStreamArr, this.mare, ((T4CConnection)this.statement.connection).oer);

          if (m != -1)
          {
            if (arrayOfByte3 == arrayOfByte1) {
              n += m;
            } else if (arrayOfByte1.length - n > 0)
            {
              int i2 = arrayOfByte1.length - n;

              System.arraycopy(arrayOfByte3, 0, arrayOfByte1, n, i2);

              n += i2;
            }
          }
        }

        if (arrayOfByte3 != arrayOfByte1) {
          arrayOfByte3 = null;
        }
        this.meta[0] = n;
      }

    }

    this.tmp[0] = this.meta[0];

    int m = 0;

    if (this.formOfUse == 2) {
      m = this.statement.connection.conversion.NCHARBytesToJavaChars(arrayOfByte1, 0, this.rowSpaceChar, k + 1, this.tmp, this.charLength - 1);
    }
    else
    {
      m = this.statement.connection.conversion.CHARBytesToJavaChars(arrayOfByte1, 0, this.rowSpaceChar, k + 1, this.tmp, this.charLength - 1);
    }

    this.rowSpaceChar[k] = ((char)(m * 2));

    if (!this.underlyingLong) {
      processIndicator(this.meta[0]);
    }
    if (this.meta[0] == 0)
    {
      this.rowSpaceIndicator[i] = -1;
      this.rowSpaceIndicator[j] = 0;
    }
    else
    {
      this.rowSpaceIndicator[j] = ((short)(this.meta[0] * 2));

      this.rowSpaceIndicator[i] = 0;
    }

    this.lastRowProcessed += 1;

    return false;
  }

  void copyRow()
    throws SQLException, IOException
  {
    int i;
    if (this.lastRowProcessed == 0)
      i = this.statement.rowPrefetchInLastFetch - 1;
    else {
      i = this.lastRowProcessed - 1;
    }

    int j = this.columnIndex + this.lastRowProcessed * this.charLength;
    int k = this.columnIndex + i * this.charLength;
    int m = this.indicatorIndex + this.lastRowProcessed;
    int n = this.indicatorIndex + i;
    int i1 = this.lengthIndex + this.lastRowProcessed;
    int i2 = this.lengthIndex + i;
    int i3 = this.rowSpaceIndicator[i2];
    int i4 = this.metaDataIndex + this.lastRowProcessed * 1;

    int i5 = this.metaDataIndex + i * 1;

    this.rowSpaceIndicator[i1] = ((short)i3);
    this.rowSpaceIndicator[m] = this.rowSpaceIndicator[n];

    if (!this.isNullByDescribe)
    {
      System.arraycopy(this.rowSpaceChar, k, this.rowSpaceChar, j, this.rowSpaceChar[k] / '\002' + 1);
    }

    System.arraycopy(this.rowSpaceMetaData, i5, this.rowSpaceMetaData, i4, 1);

    this.lastRowProcessed += 1;
  }

  void saveDataFromOldDefineBuffers(byte[] paramArrayOfByte, char[] paramArrayOfChar, short[] paramArrayOfShort, int paramInt1, int paramInt2)
    throws SQLException
  {
    int i = this.columnIndex + (paramInt2 - 1) * this.charLength;

    int j = this.columnIndexLastRow + (paramInt1 - 1) * this.charLength;

    int k = this.indicatorIndex + paramInt2 - 1;
    int m = this.indicatorIndexLastRow + paramInt1 - 1;
    int n = this.lengthIndex + paramInt2 - 1;
    int i1 = this.lengthIndexLastRow + paramInt1 - 1;
    int i2 = paramArrayOfShort[i1];

    this.rowSpaceIndicator[n] = ((short)i2);
    this.rowSpaceIndicator[k] = paramArrayOfShort[m];

    if (i2 != 0)
    {
      System.arraycopy(paramArrayOfChar, j, this.rowSpaceChar, i, paramArrayOfChar[j] / '\002' + 1);
    }
    else
    {
      this.rowSpaceChar[i] = '\000';
    }
  }

  void calculateSizeTmpByteArray()
  {
    int i;
    if (this.formOfUse == 2)
    {
      i = (this.charLength - 1) * this.statement.connection.conversion.maxNCharSize;
    }
    else
    {
      i = (this.charLength - 1) * this.statement.connection.conversion.cMaxCharSize;
    }

    if (this.statement.sizeTmpByteArray < i)
      this.statement.sizeTmpByteArray = i;
  }

  String getString(int paramInt)
    throws SQLException
  {
    String str = super.getString(paramInt);

    if ((str != null) && (this.definedColumnSize > 0) && (str.length() > this.definedColumnSize))
    {
      str = str.substring(0, this.definedColumnSize);
    }
    return str;
  }

  NUMBER getNUMBER(int paramInt)
    throws SQLException
  {
    NUMBER localNUMBER = null;

    if (this.definedColumnType == 0) {
      localNUMBER = super.getNUMBER(paramInt);
    }
    else {
      String str = getString(paramInt);
      if (str != null)
      {
        return StringToNUMBER(str);
      }
    }

    return localNUMBER;
  }

  DATE getDATE(int paramInt)
    throws SQLException
  {
    DATE localDATE = null;

    if (this.definedColumnType == 0) {
      localDATE = super.getDATE(paramInt);
    }
    else {
      Date localDate = getDate(paramInt);
      if (localDate != null)
      {
        localDATE = new DATE(localDate);
      }
    }

    return localDATE;
  }

  TIMESTAMP getTIMESTAMP(int paramInt)
    throws SQLException
  {
    TIMESTAMP localTIMESTAMP = null;

    if (this.definedColumnType == 0) {
      localTIMESTAMP = super.getTIMESTAMP(paramInt);
    }
    else {
      String str = getString(paramInt);
      if (str != null)
      {
        int[] arrayOfInt = new int[1];
        Calendar localCalendar = DATEStringToCalendar(str, (String)this.statement.connection.sessionProperties.get("AUTH_NLS_LXCSTMPFM"), arrayOfInt);

        Timestamp localTimestamp = new Timestamp(localCalendar.getTimeInMillis());
        localTimestamp.setNanos(arrayOfInt[0]);
        localTIMESTAMP = new TIMESTAMP(localTimestamp);
      }
    }

    return localTIMESTAMP;
  }

  TIMESTAMPTZ getTIMESTAMPTZ(int paramInt)
    throws SQLException
  {
    TIMESTAMPTZ localTIMESTAMPTZ = null;

    if (this.definedColumnType == 0) {
      localTIMESTAMPTZ = super.getTIMESTAMPTZ(paramInt);
    }
    else {
      String str = getString(paramInt);
      if (str != null)
      {
        int[] arrayOfInt = new int[1];
        Calendar localCalendar = DATEStringToCalendar(str, (String)this.statement.connection.sessionProperties.get("AUTH_NLS_LXCSTZNFM"), arrayOfInt);

        Timestamp localTimestamp = new Timestamp(localCalendar.getTimeInMillis());
        localTimestamp.setNanos(arrayOfInt[0]);
        localTIMESTAMPTZ = new TIMESTAMPTZ(this.statement.connection, localTimestamp, localCalendar);
      }

    }

    return localTIMESTAMPTZ;
  }

  TIMESTAMPLTZ getTIMESTAMPLTZ(int paramInt)
    throws SQLException
  {
    TIMESTAMPLTZ localTIMESTAMPLTZ = null;

    if (this.definedColumnType == 0) {
      localTIMESTAMPLTZ = super.getTIMESTAMPLTZ(paramInt);
    }
    else {
      String str = getString(paramInt);
      if (str != null)
      {
        int[] arrayOfInt = new int[1];
        Calendar localCalendar = DATEStringToCalendar(str, (String)this.statement.connection.sessionProperties.get("AUTH_NLS_LXCSTZNFM"), arrayOfInt);

        Timestamp localTimestamp = new Timestamp(localCalendar.getTimeInMillis());
        localTimestamp.setNanos(arrayOfInt[0]);
        localTIMESTAMPLTZ = new TIMESTAMPLTZ(this.statement.connection, localTimestamp, localCalendar);
      }

    }

    return localTIMESTAMPLTZ;
  }

  RAW getRAW(int paramInt)
    throws SQLException
  {
    RAW localRAW = null;

    if (this.definedColumnType == 0) {
      localRAW = super.getRAW(paramInt);
    }
    else {
      if (this.rowSpaceIndicator == null)
      {
        SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 21);
        localSQLException.fillInStackTrace();
        throw localSQLException;
      }

      if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1)
      {
        if ((this.definedColumnType == -2) || (this.definedColumnType == -3) || (this.definedColumnType == -4))
        {
          localRAW = new RAW(getBytesFromHexChars(paramInt));
        }
        else localRAW = new RAW(super.getBytes(paramInt));
      }
    }

    return localRAW;
  }

  Datum getOracleObject(int paramInt)
    throws SQLException
  {
    if (this.definedColumnType == 0) {
      return super.getOracleObject(paramInt);
    }

    Datum localDatum = null;
    SQLException localSQLException;
    if (this.rowSpaceIndicator == null)
    {
      localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 21);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1)
    {
      switch (this.definedColumnType)
      {
      case -16:
      case -15:
      case -9:
      case -1:
      case 1:
      case 12:
        return super.getOracleObject(paramInt);
      case -7:
      case -6:
      case -5:
      case 2:
      case 3:
      case 4:
      case 5:
      case 6:
      case 7:
      case 8:
      case 16:
        return getNUMBER(paramInt);
      case 91:
        return getDATE(paramInt);
      case 92:
        return getDATE(paramInt);
      case 93:
        return getTIMESTAMP(paramInt);
      case -101:
        return getTIMESTAMPTZ(paramInt);
      case -102:
        return getTIMESTAMPLTZ(paramInt);
      case -4:
      case -3:
      case -2:
        return getRAW(paramInt);
      case -8:
        return getROWID(paramInt);
      }

      localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    return localDatum;
  }

  byte[] getBytes(int paramInt)
    throws SQLException
  {
    if (this.definedColumnType == 0) {
      return super.getBytes(paramInt);
    }
    Datum localDatum = getOracleObject(paramInt);
    if (localDatum != null) {
      return localDatum.shareBytes();
    }
    return null;
  }

  boolean getBoolean(int paramInt)
    throws SQLException
  {
    boolean bool = false;

    if (this.definedColumnType == 0) {
      bool = super.getBoolean(paramInt);
    }
    else {
      bool = getNUMBER(paramInt).booleanValue();
    }

    return bool;
  }

  byte getByte(int paramInt)
    throws SQLException
  {
    byte b = 0;

    if (this.definedColumnType == 0) {
      b = super.getByte(paramInt);
    }
    else {
      b = getNUMBER(paramInt).byteValue();
    }

    return b;
  }

  int getInt(int paramInt)
    throws SQLException
  {
    int i = 0;

    if (this.definedColumnType == 0) {
      i = super.getInt(paramInt);
    }
    else {
      i = getNUMBER(paramInt).intValue();
    }

    return i;
  }

  short getShort(int paramInt)
    throws SQLException
  {
    short s = 0;

    if (this.definedColumnType == 0) {
      s = super.getShort(paramInt);
    }
    else {
      s = getNUMBER(paramInt).shortValue();
    }

    return s;
  }

  long getLong(int paramInt)
    throws SQLException
  {
    long l = 0L;

    if (this.definedColumnType == 0) {
      l = super.getLong(paramInt);
    }
    else {
      l = getNUMBER(paramInt).longValue();
    }

    return l;
  }

  float getFloat(int paramInt)
    throws SQLException
  {
    float f = 0.0F;

    if (this.definedColumnType == 0) {
      f = super.getFloat(paramInt);
    }
    else {
      f = getNUMBER(paramInt).floatValue();
    }

    return f;
  }

  double getDouble(int paramInt)
    throws SQLException
  {
    double d = 0.0D;

    if (this.definedColumnType == 0) {
      d = super.getDouble(paramInt);
    }
    else {
      d = getNUMBER(paramInt).doubleValue();
    }

    return d;
  }

  Date getDate(int paramInt)
    throws SQLException
  {
    Date localDate = null;

    if (this.definedColumnType == 0) {
      localDate = super.getDate(paramInt);
    }
    else {
      String str = getString(paramInt);
      if (str != null)
      {
        int[] arrayOfInt = new int[1];
        localDate = new Date(DATEStringToCalendar(str, (String)this.statement.connection.sessionProperties.get("AUTH_NLS_LXCDATEFM"), arrayOfInt).getTimeInMillis());
      }

    }

    return localDate;
  }

  Timestamp getTimestamp(int paramInt)
    throws SQLException
  {
    Timestamp localTimestamp = null;

    if (this.definedColumnType == 0) {
      localTimestamp = super.getTimestamp(paramInt);
    }
    else {
      String str = getString(paramInt);
      if (str != null)
      {
        int[] arrayOfInt = new int[1];
        Calendar localCalendar = DATEStringToCalendar(str, (String)this.statement.connection.sessionProperties.get("AUTH_NLS_LXCSTMPFM"), arrayOfInt);

        localTimestamp = new Timestamp(localCalendar.getTimeInMillis());
        localTimestamp.setNanos(arrayOfInt[0]);
      }
    }

    return localTimestamp;
  }

  Time getTime(int paramInt)
    throws SQLException
  {
    Time localTime = null;

    if (this.definedColumnType == 0) {
      localTime = super.getTime(paramInt);
    }
    else {
      String str = getString(paramInt);
      if (str != null)
      {
        int[] arrayOfInt = new int[1];
        Calendar localCalendar = DATEStringToCalendar(str, (String)this.statement.connection.sessionProperties.get("AUTH_NLS_LXCSTZNFM"), arrayOfInt);

        localTime = new Time(localCalendar.getTimeInMillis());
      }
    }

    return localTime;
  }

  Object getObject(int paramInt)
    throws SQLException
  {
    if (this.definedColumnType == 0) {
      return super.getObject(paramInt);
    }

    Object localObject = null;
    SQLException localSQLException;
    if (this.rowSpaceIndicator == null)
    {
      localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 21);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1)
    {
      switch (this.definedColumnType)
      {
      case -16:
      case -15:
      case -9:
      case -1:
      case 1:
      case 12:
        return getString(paramInt);
      case 2:
      case 3:
        return getBigDecimal(paramInt);
      case 4:
        return Integer.valueOf(getInt(paramInt));
      case -6:
        return Byte.valueOf(getByte(paramInt));
      case 5:
        return Short.valueOf(getShort(paramInt));
      case -7:
      case 16:
        return Boolean.valueOf(getBoolean(paramInt));
      case -5:
        return Long.valueOf(getLong(paramInt));
      case 7:
        return Float.valueOf(getFloat(paramInt));
      case 6:
      case 8:
        return Double.valueOf(getDouble(paramInt));
      case 91:
        return getDate(paramInt);
      case 92:
        return getTime(paramInt);
      case 93:
        return getTimestamp(paramInt);
      case -101:
        return getTIMESTAMPTZ(paramInt);
      case -102:
        return getTIMESTAMPLTZ(paramInt);
      case -4:
      case -3:
      case -2:
        return getBytesFromHexChars(paramInt);
      case -8:
        return getROWID(paramInt);
      }

      localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    return localObject;
  }

  ROWID getROWID(int paramInt)
    throws SQLException
  {
    byte[] arrayOfByte = getBytesInternal(paramInt);
    ROWID localROWID = null;
    if (arrayOfByte != null) {
      localROWID = new ROWID(arrayOfByte);
    }
    return localROWID;
  }

  static final NUMBER StringToNUMBER(String paramString)
    throws SQLException
  {
    return new NUMBER(new BigDecimal(paramString));
  }

  static final Calendar DATEStringToCalendar(String paramString1, String paramString2, int[] paramArrayOfInt)
    throws SQLException
  {
    char[] arrayOfChar = (paramString2 + " ").toCharArray();
    paramString1 = paramString1 + " ";

    int i = Math.min(paramString1.length(), arrayOfChar.length);

    int j = -1;
    int k = -1;

    int m = 0;
    int n = 0;

    int str1 = 0;
    int i1 = 0;

    int i2 = 0;
    int i3 = 0;
    int i4 = 0;

    int i5 = 0;
    int i6 = 0;
    int i7 = 0;
    int i8 = 0;

    String str2 = null;
    String str3 = null;

    int i9 = 0;

    String[] arrayOfString1 = null;
    String[] arrayOfString2 = null;

    for (int i10 = 0; i10 < i; i10++)
    {
      switch (arrayOfChar[i10])
      {
      case 'R':
      case 'r':
        if (j != 6)
        {
          j = 6;
          m = i10; } break;
      case 'Y':
      case 'y':
        if (j != 5)
        {
          j = 5;
          m = i10; } break;
      case 'D':
      case 'd':
        if (j != 1)
        {
          j = 1;
          m = i10; } break;
      case 'M':
      case 'm':
        if ((j != 2) || (j != 4) || (j != 3) || (j != 9))
        {
          m = i10;

          if ((i10 + 4 < i) && ((arrayOfChar[(i10 + 1)] == 'O') || (arrayOfChar[(i10 + 1)] == 'o')) && ((arrayOfChar[(i10 + 2)] == 'N') || (arrayOfChar[(i10 + 2)] == 'n')) && ((arrayOfChar[(i10 + 3)] == 'T') || (arrayOfChar[(i10 + 3)] == 't')) && ((arrayOfChar[(i10 + 4)] == 'H') || (arrayOfChar[(i10 + 4)] == 'h')))
          {
            j = 3;
            i10 += 4;
          } else if ((i10 + 2 < i) && ((arrayOfChar[(i10 + 1)] == 'O') || (arrayOfChar[(i10 + 1)] == 'o')) && ((arrayOfChar[(i10 + 2)] == 'N') || (arrayOfChar[(i10 + 2)] == 'n')))
          {
            j = 4;
            i10 += 2;
          } else if ((i10 + 1 < i) && ((arrayOfChar[(i10 + 1)] == 'M') || (arrayOfChar[(i10 + 1)] == 'm')))
          {
            j = 2;
            i10++;
          } else if ((i10 + 1 < i) && ((arrayOfChar[(i10 + 1)] == 'I') || (arrayOfChar[(i10 + 1)] == 'i')))
          {
            j = 9;
            i10++; }  } break;
      case 'H':
      case 'h':
        if (j != 7)
        {
          j = 7;
          m = i10;
        } else if ((i10 + 2 < i) && ((arrayOfChar[(i10 + 1)] == '2') || (arrayOfChar[(i10 + 4)] == '4')))
        {
          j = 8;
          i10 += 2; } break;
      case 'S':
      case 's':
        if ((i10 + 1 < i) && ((arrayOfChar[(i10 + 1)] == 'S') || (arrayOfChar[(i10 + 1)] == 's')))
        {
          j = 10;
          m = i10;
          i10++; } break;
      case 'F':
      case 'f':
        if (j != 11)
        {
          j = 11;
          m = i10; } break;
      case 'A':
      case 'a':
        if ((i10 + 1 < i) && ((arrayOfChar[(i10 + 1)] == 'M') || (arrayOfChar[(i10 + 1)] == 'm')))
        {
          j = 12;
          m = i10;
          i10++; } break;
      case 'T':
      case 't':
        if ((i10 + 2 < i) && ((arrayOfChar[(i10 + 1)] == 'Z') || (arrayOfChar[(i10 + 1)] == 'z')) && ((arrayOfChar[(i10 + 2)] == 'R') || (arrayOfChar[(i10 + 2)] == 'r')))
        {
          j = 13;
          m = i10;
          i10 += 2; } break;
      case 'B':
      case 'C':
      case 'E':
      case 'G':
      case 'I':
      case 'J':
      case 'K':
      case 'L':
      case 'N':
      case 'O':
      case 'P':
      case 'Q':
      case 'U':
      case 'V':
      case 'W':
      case 'X':
      case 'Z':
      case '[':
      case '\\':
      case ']':
      case '^':
      case '_':
      case '`':
      case 'b':
      case 'c':
      case 'e':
      case 'g':
      case 'i':
      case 'j':
      case 'k':
      case 'l':
      case 'n':
      case 'o':
      case 'p':
      case 'q':
      case 'u':
      case 'v':
      case 'w':
      case 'x':
      default:
        i9 = 1;
      }

      if ((i9 != 0) && (j != -1))
      {
        int i11 = i10 - m;
        int i12 = m - n;
        str1 = i1 + i12;

        i1 = str1 + i11;
        Object localObject;
        int i15;
        switch (j)
        {
        case 1:
          i2 = Integer.parseInt(paramString1.substring(str1, i1));
          break;
        case 2:
          i3 = Integer.parseInt(paramString1.substring(str1, i1));
          break;
        case 3:
          int i16 = str1;
          i1 = str1;
          for (i16 = str1; (i16 < paramString1.length()) && 
            (paramString1.charAt(i16) != arrayOfChar[i10]); i16++);
          i1 = i16;

          localObject = null;
          if (i1 != str1) {
            localObject = paramString1.substring(str1, i1);

            localObject = ((String)localObject).trim();

            if (arrayOfString2 == null)
              arrayOfString2 = new DateFormatSymbols().getMonths();
            for (i3 = 0; (i3 < arrayOfString2.length) && 
              (!((String)localObject).equalsIgnoreCase(arrayOfString2[i3])); i3++);
            if (i3 >= 12)
            {
              SQLException localSQLException = DatabaseError.createSqlException(null, 59);
              localSQLException.fillInStackTrace();
              throw localSQLException;
            }

          }

          break;
        case 4:
          int i13 = str1;
          i1 = str1;
          int i14;
          for (i14 = str1; (i14 < paramString1.length()) && 
            (paramString1.charAt(i14) != arrayOfChar[i10]); i14++);
          i1 = i14;

          String str4 = null;
          if (i1 != str1) {
            str4 = paramString1.substring(str1, i1);

            str4 = str4.trim();

            if (arrayOfString1 == null)
              arrayOfString1 = new DateFormatSymbols().getShortMonths();
            for (i3 = 0; (i3 < arrayOfString1.length) && 
              (!str4.equalsIgnoreCase(arrayOfString1[i3])); i3++);
            if (i3 >= 12)
            {
              SQLException sqlexception = DatabaseError.createSqlException(null, 59);
              sqlexception.fillInStackTrace();
              throw sqlexception;
            }

          }

          break;
        case 5:
          i4 = Integer.parseInt(paramString1.substring(str1, i1));

          if (i11 == 2)
            i4 += 2000; break;
        case 6:
          i4 = Integer.parseInt(paramString1.substring(str1, i1));

          if ((i11 == 2) && (i4 < 50))
            i4 += 2000;
          else
            i4 += 1900;
          break;
        case 7:
        case 8:
          i1 = str1 + 2;
          i5 = Integer.parseInt(paramString1.substring(str1, i1));
          break;
        case 9:
          i6 = Integer.parseInt(paramString1.substring(str1, i1));
          break;
        case 10:
          i7 = Integer.parseInt(paramString1.substring(str1, i1));
          break;
        case 11:
          int i20 = str1;
          i1 = str1;
          for (i20 = str1; (i20 < paramString1.length()) && 
            ((i15 = paramString1.charAt(i20)) >= '0') && (i15 <= 57); i20++);
          i1 += i20 - str1;

          if (i1 != str1)
            i8 = Integer.parseInt(paramString1.substring(str1, i1)); break;
        case 12:
          if (i1 > 0)
            str2 = paramString1.substring(str1, i1); break;
        case 13:
          int i17 = str1;
          i1 = str1;
          for (int i18 = str1; (i18 < paramString1.length()) && (
            (((i15 = paramString1.charAt(i18)) >= '0') && (i15 <= 57)) || ((i15 >= 97) && (i15 <= 122)) || ((i15 >= 65) && (i15 <= 90))); i18++)
          {
            i1 = i18;
          }
          if (i1 != str1)
            str3 = paramString1.substring(str1, i1); break;
        default:
          System.out.println("\n\n\n             ***** ERROR(1) ****\n");
        }

        n = i10;
        j = -1;
        i9 = 0;
      }

    }

    GregorianCalendar localGregorianCalendar = new GregorianCalendar(i4, i3, i2, i5, i6, i7);
    if (str2 != null) {
      localGregorianCalendar.set(9, str2.equalsIgnoreCase("AM") ? 0 : 1);
    }

    if ((str3 == null) || 
      (i8 != 0)) {
      paramArrayOfInt[0] = i8;
    }
    return localGregorianCalendar;
  }
}