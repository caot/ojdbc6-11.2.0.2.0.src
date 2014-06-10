package oracle.jdbc.driver;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Properties;
import oracle.sql.DATE;
import oracle.sql.Datum;
import oracle.sql.NUMBER;
import oracle.sql.RAW;
import oracle.sql.TIMESTAMP;
import oracle.sql.TIMESTAMPLTZ;
import oracle.sql.TIMESTAMPTZ;

class T4CCharAccessor extends CharAccessor
{
  T4CMAREngine mare;
  boolean underlyingLong = false;

  final int[] meta = new int[1];
  final int[] tmp = new int[1];

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  T4CCharAccessor(OracleStatement paramOracleStatement, int paramInt1, short paramShort, int paramInt2, boolean paramBoolean, T4CMAREngine paramT4CMAREngine)
    throws SQLException
  {
    super(paramOracleStatement, paramInt1, paramShort, paramInt2, paramBoolean);

    this.mare = paramT4CMAREngine;

    calculateSizeTmpByteArray();
  }

  T4CCharAccessor(OracleStatement paramOracleStatement, int paramInt1, boolean paramBoolean, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, short paramShort, int paramInt7, int paramInt8, int paramInt9, T4CMAREngine paramT4CMAREngine)
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

    processIndicator(this.meta[0]);

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
        return T4CVarcharAccessor.StringToNUMBER(str);
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
        Calendar localCalendar = T4CVarcharAccessor.DATEStringToCalendar(str, (String)this.statement.connection.sessionProperties.get("AUTH_NLS_LXCSTMPFM"), arrayOfInt);

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
        Calendar localCalendar = T4CVarcharAccessor.DATEStringToCalendar(str, (String)this.statement.connection.sessionProperties.get("AUTH_NLS_LXCSTZNFM"), arrayOfInt);

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
        Calendar localCalendar = T4CVarcharAccessor.DATEStringToCalendar(str, (String)this.statement.connection.sessionProperties.get("AUTH_NLS_LXCSTZNFM"), arrayOfInt);

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
        localDate = new Date(T4CVarcharAccessor.DATEStringToCalendar(str, (String)this.statement.connection.sessionProperties.get("AUTH_NLS_LXCDATEFM"), arrayOfInt).getTimeInMillis());
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
        Calendar localCalendar = T4CVarcharAccessor.DATEStringToCalendar(str, (String)this.statement.connection.sessionProperties.get("AUTH_NLS_LXCSTMPFM"), arrayOfInt);

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
        Calendar localCalendar = T4CVarcharAccessor.DATEStringToCalendar(str, (String)this.statement.connection.sessionProperties.get("AUTH_NLS_LXCSTZNFM"), arrayOfInt);

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
      case -4:
      case -3:
      case -2:
        return getBytesFromHexChars(paramInt);
      }

      localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    return localObject;
  }
}