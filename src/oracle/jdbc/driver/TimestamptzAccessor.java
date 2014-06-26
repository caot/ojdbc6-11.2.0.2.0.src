package oracle.jdbc.driver;

import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;
import java.util.TimeZone;
import oracle.sql.DATE;
import oracle.sql.Datum;
import oracle.sql.TIMESTAMP;
import oracle.sql.TIMESTAMPTZ;
import oracle.sql.TIMEZONETAB;
import oracle.sql.ZONEIDMAP;

class TimestamptzAccessor extends DateTimeCommonAccessor
{
  static final int maxLength = 13;
  TimestampTzConverter tstzConverter = null;

  static int OFFSET_HOUR = 20;
  static int OFFSET_MINUTE = 60;

  static byte REGIONIDBIT = -128;

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  TimestamptzAccessor(OracleStatement paramOracleStatement, int paramInt1, short paramShort, int paramInt2, boolean paramBoolean)
    throws SQLException
  {
    init(paramOracleStatement, 181, 181, paramShort, paramBoolean);
    initForDataAccess(paramInt2, paramInt1, null);

    if (this.statement.connection.timestamptzInGmt)
      this.tstzConverter = new GmtTimestampTzConverter();
    else
      this.tstzConverter = new OldTimestampTzConverter();
  }

  TimestamptzAccessor(OracleStatement paramOracleStatement, int paramInt1, boolean paramBoolean, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, short paramShort)
    throws SQLException
  {
    init(paramOracleStatement, 181, 181, paramShort, false);
    initForDescribe(181, paramInt1, paramBoolean, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramShort, null);

    initForDataAccess(0, paramInt1, null);

    if (this.statement.connection.timestamptzInGmt)
      this.tstzConverter = new GmtTimestampTzConverter();
    else
      this.tstzConverter = new OldTimestampTzConverter();
  }

  void initForDataAccess(int paramInt1, int paramInt2, String paramString)
    throws SQLException
  {
    if (paramInt1 != 0) {
      this.externalType = paramInt1;
    }
    this.internalTypeMaxLength = 13;

    if ((paramInt2 > 0) && (paramInt2 < this.internalTypeMaxLength)) {
      this.internalTypeMaxLength = paramInt2;
    }
    this.byteLength = this.internalTypeMaxLength;
  }

  String getString(int paramInt)
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
    int i = this.columnIndex + this.byteLength * paramInt;

    int j = 0;
    String str;
    if ((oracleTZ1(i) & REGIONIDBIT) != 0)
    {
      j = getHighOrderbits(oracleTZ1(i));
      j += getLowOrderbits(oracleTZ2(i));

      TIMEZONETAB localTIMEZONETAB1 = this.statement.connection.getTIMEZONETAB();
      if (localTIMEZONETAB1.checkID(j)) {
        localTIMEZONETAB1.updateTable(this.statement.connection, j);
      }
      str = ZONEIDMAP.getRegion(j);
    }
    else
    {
      int k = oracleTZ1(i) - OFFSET_HOUR;
      int m = oracleTZ2(i) - OFFSET_MINUTE;

      str = new StringBuilder().append("GMT").append(k < 0 ? "-" : "+").append(Math.abs(k)).append(":").append(m < 10 ? "0" : "").append(m).toString();
    }

    Calendar localCalendar = this.statement.getGMTCalendar();

    int m = ((this.rowSpaceByte[(0 + i)] & 0xFF) - 100) * 100 + (this.rowSpaceByte[(1 + i)] & 0xFF) - 100;

    localCalendar.set(1, m);
    localCalendar.set(2, oracleMonth(i));
    localCalendar.set(5, oracleDay(i));
    localCalendar.set(11, oracleHour(i));
    localCalendar.set(12, oracleMin(i));
    localCalendar.set(13, oracleSec(i));
    localCalendar.set(14, 0);

    if ((oracleTZ1(i) & REGIONIDBIT) != 0)
    {
      TIMEZONETAB localTIMEZONETAB2 = this.statement.connection.getTIMEZONETAB();

      int i1 = localTIMEZONETAB2.getOffset(localCalendar, j);

      localCalendar.add(14, i1);
    }
    else
    {
      localCalendar.add(10, oracleTZ1(i) - OFFSET_HOUR);
      localCalendar.add(12, oracleTZ2(i) - OFFSET_MINUTE);
    }

    m = localCalendar.get(1);

    int n = localCalendar.get(2) + 1;
    int i1 = localCalendar.get(5);
    int i2 = localCalendar.get(11);
    int i3 = localCalendar.get(12);
    int i4 = localCalendar.get(13);
    boolean bool = i2 < 12;
    if ((str.length() > 3) && (str.startsWith("GMT"))) {
      str = str.substring(3);
    }
    int i5 = oracleNanos(i);

    return toText(m, n, i1, i2, i3, i4, i5, bool, str);
  }

  java.sql.Date getDate(int paramInt)
    throws SQLException
  {
    return this.tstzConverter.getDate(paramInt);
  }

  java.sql.Date getDate(int paramInt, Calendar paramCalendar)
    throws SQLException
  {
    return getDate(paramInt);
  }

  Time getTime(int paramInt)
    throws SQLException
  {
    return this.tstzConverter.getTime(paramInt);
  }

  Time getTime(int paramInt, Calendar paramCalendar)
    throws SQLException
  {
    return getTime(paramInt);
  }

  Timestamp getTimestamp(int paramInt)
    throws SQLException
  {
    return this.tstzConverter.getTimestamp(paramInt);
  }

  Timestamp getTimestamp(int paramInt, Calendar paramCalendar)
    throws SQLException
  {
    return getTimestamp(paramInt);
  }

  Object getObject(int paramInt) throws SQLException
  {
    return this.tstzConverter.getObject(paramInt);
  }

  Datum getOracleObject(int paramInt)
    throws SQLException
  {
    return this.tstzConverter.getOracleObject(paramInt);
  }

  DATE getDATE(int paramInt) throws SQLException
  {
    TIMESTAMPTZ localTIMESTAMPTZ = this.tstzConverter.getTIMESTAMPTZ(paramInt);
    return oracle.sql.TIMESTAMPTZ.toDATE(this.statement.connection, localTIMESTAMPTZ.getBytes());
  }

  TIMESTAMP getTIMESTAMP(int paramInt) throws SQLException
  {
    TIMESTAMPTZ localTIMESTAMPTZ = this.tstzConverter.getTIMESTAMPTZ(paramInt);
    return oracle.sql.TIMESTAMPTZ.toTIMESTAMP(this.statement.connection, localTIMESTAMPTZ.getBytes());
  }

  TIMESTAMPTZ getTIMESTAMPTZ(int paramInt) throws SQLException
  {
    return this.tstzConverter.getTIMESTAMPTZ(paramInt);
  }

  static int setHighOrderbits(int paramInt)
  {
    return (paramInt & 0x1FC0) >> 6;
  }

  static int setLowOrderbits(int paramInt)
  {
    return (paramInt & 0x3F) << 2;
  }

  static int getHighOrderbits(int paramInt)
  {
    return (paramInt & 0x7F) << 6;
  }

  static int getLowOrderbits(int paramInt)
  {
    return (paramInt & 0xFC) >> 2;
  }

  abstract class TimestampTzConverter
  {
    TimestampTzConverter()
    {
    }

    abstract java.sql.Date getDate(int paramInt)
      throws SQLException;

    abstract Time getTime(int paramInt)
      throws SQLException;

    abstract Timestamp getTimestamp(int paramInt)
      throws SQLException;

    Object getObject(int paramInt)
      throws SQLException
    {
      return getTIMESTAMPTZ(paramInt);
    }

    Datum getOracleObject(int paramInt)
      throws SQLException
    {
      return getTIMESTAMPTZ(paramInt);
    }

    Object getObject(int paramInt, Map paramMap)
      throws SQLException
    {
      return getTIMESTAMPTZ(paramInt);
    }

    abstract TIMESTAMPTZ getTIMESTAMPTZ(int paramInt)
      throws SQLException;
  }

  class GmtTimestampTzConverter extends TimestamptzAccessor.TimestampTzConverter
  {
    GmtTimestampTzConverter()
    {
      super();
    }

    java.sql.Date getDate(int paramInt)
      throws SQLException
    {
      if (TimestamptzAccessor.this.rowSpaceIndicator == null)
      {
        SQLException localSQLException = DatabaseError.createSqlException(TimestamptzAccessor.this.getConnectionDuringExceptionHandling(), 21);
        localSQLException.fillInStackTrace();
        throw localSQLException;
      }

      if (TimestamptzAccessor.this.rowSpaceIndicator[(TimestamptzAccessor.this.indicatorIndex + paramInt)] == -1) {
        return null;
      }
      int i = TimestamptzAccessor.this.columnIndex + TimestamptzAccessor.this.byteLength * paramInt;

      Calendar localCalendar = TimestamptzAccessor.this.statement.getGMTCalendar();

      int j = ((TimestamptzAccessor.this.rowSpaceByte[(0 + i)] & 0xFF) - 100) * 100 + (TimestamptzAccessor.this.rowSpaceByte[(1 + i)] & 0xFF) - 100;

      localCalendar.set(1, j);
      localCalendar.set(2, TimestamptzAccessor.this.oracleMonth(i));
      localCalendar.set(5, TimestamptzAccessor.this.oracleDay(i));
      localCalendar.set(11, TimestamptzAccessor.this.oracleHour(i));
      localCalendar.set(12, TimestamptzAccessor.this.oracleMin(i));
      localCalendar.set(13, TimestamptzAccessor.this.oracleSec(i));
      localCalendar.set(14, 0);

      long l = localCalendar.getTimeInMillis();

      return new java.sql.Date(l);
    }

    Time getTime(int paramInt)
      throws SQLException
    {
      if (TimestamptzAccessor.this.rowSpaceIndicator == null)
      {
        SQLException localSQLException = DatabaseError.createSqlException(TimestamptzAccessor.this.getConnectionDuringExceptionHandling(), 21);
        localSQLException.fillInStackTrace();
        throw localSQLException;
      }

      if (TimestamptzAccessor.this.rowSpaceIndicator[(TimestamptzAccessor.this.indicatorIndex + paramInt)] == -1) {
        return null;
      }
      int i = TimestamptzAccessor.this.columnIndex + TimestamptzAccessor.this.byteLength * paramInt;

      Calendar localCalendar = TimestamptzAccessor.this.statement.getGMTCalendar();

      int j = ((TimestamptzAccessor.this.rowSpaceByte[(0 + i)] & 0xFF) - 100) * 100 + (TimestamptzAccessor.this.rowSpaceByte[(1 + i)] & 0xFF) - 100;

      localCalendar.set(1, j);
      localCalendar.set(2, TimestamptzAccessor.this.oracleMonth(i));
      localCalendar.set(5, TimestamptzAccessor.this.oracleDay(i));
      localCalendar.set(11, TimestamptzAccessor.this.oracleHour(i));
      localCalendar.set(12, TimestamptzAccessor.this.oracleMin(i));
      localCalendar.set(13, TimestamptzAccessor.this.oracleSec(i));
      localCalendar.set(14, 0);

      return new Time(localCalendar.getTimeInMillis());
    }

    Timestamp getTimestamp(int paramInt)
      throws SQLException
    {
      if (TimestamptzAccessor.this.rowSpaceIndicator == null)
      {
        SQLException localSQLException = DatabaseError.createSqlException(TimestamptzAccessor.this.getConnectionDuringExceptionHandling(), 21);
        localSQLException.fillInStackTrace();
        throw localSQLException;
      }

      if (TimestamptzAccessor.this.rowSpaceIndicator[(TimestamptzAccessor.this.indicatorIndex + paramInt)] == -1) {
        return null;
      }
      int i = TimestamptzAccessor.this.columnIndex + TimestamptzAccessor.this.byteLength * paramInt;

      Calendar localCalendar = TimestamptzAccessor.this.statement.getGMTCalendar();

      int j = ((TimestamptzAccessor.this.rowSpaceByte[(0 + i)] & 0xFF) - 100) * 100 + (TimestamptzAccessor.this.rowSpaceByte[(1 + i)] & 0xFF) - 100;

      localCalendar.set(1, j);
      localCalendar.set(2, TimestamptzAccessor.this.oracleMonth(i));
      localCalendar.set(5, TimestamptzAccessor.this.oracleDay(i));
      localCalendar.set(11, TimestamptzAccessor.this.oracleHour(i));
      localCalendar.set(12, TimestamptzAccessor.this.oracleMin(i));
      localCalendar.set(13, TimestamptzAccessor.this.oracleSec(i));
      localCalendar.set(14, 0);

      long l = localCalendar.getTimeInMillis();

      Timestamp localTimestamp = new Timestamp(l);

      int k = TimestamptzAccessor.this.oracleNanos(i);

      localTimestamp.setNanos(k);

      return localTimestamp;
    }

    TIMESTAMPTZ getTIMESTAMPTZ(int paramInt)
      throws SQLException
    {
      TIMESTAMPTZ localTIMESTAMPTZ = null;

      if (TimestamptzAccessor.this.rowSpaceIndicator == null)
      {
        SQLException localSQLException = DatabaseError.createSqlException(TimestamptzAccessor.this.getConnectionDuringExceptionHandling(), 21);
        localSQLException.fillInStackTrace();
        throw localSQLException;
      }

      if (TimestamptzAccessor.this.rowSpaceIndicator[(TimestamptzAccessor.this.indicatorIndex + paramInt)] != -1)
      {
        int i = TimestamptzAccessor.this.columnIndex + TimestamptzAccessor.this.byteLength * paramInt;
        byte[] arrayOfByte = new byte[13];

        System.arraycopy(TimestamptzAccessor.this.rowSpaceByte, i, arrayOfByte, 0, 13);

        localTIMESTAMPTZ = new TIMESTAMPTZ(arrayOfByte);
      }

      return localTIMESTAMPTZ;
    }
  }

  class OldTimestampTzConverter extends TimestamptzAccessor.TimestampTzConverter
  {
    OldTimestampTzConverter()
    {
      super();
    }

    java.sql.Date getDate(int paramInt)
      throws SQLException
    {
      if (TimestamptzAccessor.this.rowSpaceIndicator == null)
      {
        SQLException localSQLException = DatabaseError.createSqlException(TimestamptzAccessor.this.getConnectionDuringExceptionHandling(), 21);
        localSQLException.fillInStackTrace();
        throw localSQLException;
      }

      if (TimestamptzAccessor.this.rowSpaceIndicator[(TimestamptzAccessor.this.indicatorIndex + paramInt)] == -1) {
        return null;
      }
      int i = TimestamptzAccessor.this.columnIndex + TimestamptzAccessor.this.byteLength * paramInt;

      TimeZone localTimeZone = TimestamptzAccessor.this.statement.getDefaultTimeZone();
      Calendar localCalendar = Calendar.getInstance(localTimeZone);

      int j = ((TimestamptzAccessor.this.rowSpaceByte[(0 + i)] & 0xFF) - 100) * 100 + (TimestamptzAccessor.this.rowSpaceByte[(1 + i)] & 0xFF) - 100;

      localCalendar.set(1, j);
      localCalendar.set(2, TimestamptzAccessor.this.oracleMonth(i));
      localCalendar.set(5, TimestamptzAccessor.this.oracleDay(i));
      localCalendar.set(11, TimestamptzAccessor.this.oracleHour(i));
      localCalendar.set(12, TimestamptzAccessor.this.oracleMin(i));
      localCalendar.set(13, TimestamptzAccessor.this.oracleSec(i));
      localCalendar.set(14, 0);

      if ((TimestamptzAccessor.this.oracleTZ1(i) & TimestamptzAccessor.REGIONIDBIT) != 0)
      {
        int k = TimestamptzAccessor.getHighOrderbits(TimestamptzAccessor.this.oracleTZ1(i));
        k += TimestamptzAccessor.getLowOrderbits(TimestamptzAccessor.this.oracleTZ2(i));

        TIMEZONETAB localTIMEZONETAB = TimestamptzAccessor.this.statement.connection.getTIMEZONETAB();

        if (localTIMEZONETAB.checkID(k)) {
          localTIMEZONETAB.updateTable(TimestamptzAccessor.this.statement.connection, k);
        }
        int m = localTIMEZONETAB.getOffset(localCalendar, k);

        boolean bool1 = localTimeZone.inDaylightTime(localCalendar.getTime());
        boolean bool2 = localTimeZone.inDaylightTime(new java.util.Date(localCalendar.getTimeInMillis() + m));

        if ((!bool1) && (bool2))
        {
          localCalendar.add(14, -1 * localTimeZone.getDSTSavings());
        }
        else if ((bool1) && (!bool2))
        {
          localCalendar.add(14, localTimeZone.getDSTSavings());
        }

        localCalendar.add(10, m / 3600000);
        localCalendar.add(12, m % 3600000 / 60000);
      }
      else
      {
        localCalendar.add(10, TimestamptzAccessor.this.oracleTZ1(i) - TimestamptzAccessor.OFFSET_HOUR);
        localCalendar.add(12, TimestamptzAccessor.this.oracleTZ2(i) - TimestamptzAccessor.OFFSET_MINUTE);
      }

      long l = localCalendar.getTimeInMillis();

      return new java.sql.Date(l);
    }

    Time getTime(int paramInt)
      throws SQLException
    {
      if (TimestamptzAccessor.this.rowSpaceIndicator == null)
      {
        SQLException localSQLException = DatabaseError.createSqlException(TimestamptzAccessor.this.getConnectionDuringExceptionHandling(), 21);
        localSQLException.fillInStackTrace();
        throw localSQLException;
      }

      if (TimestamptzAccessor.this.rowSpaceIndicator[(TimestamptzAccessor.this.indicatorIndex + paramInt)] == -1) {
        return null;
      }
      int i = TimestamptzAccessor.this.columnIndex + TimestamptzAccessor.this.byteLength * paramInt;

      TimeZone localTimeZone = TimestamptzAccessor.this.statement.getDefaultTimeZone();
      Calendar localCalendar = Calendar.getInstance(localTimeZone);

      int j = ((TimestamptzAccessor.this.rowSpaceByte[(0 + i)] & 0xFF) - 100) * 100 + (TimestamptzAccessor.this.rowSpaceByte[(1 + i)] & 0xFF) - 100;

      localCalendar.set(1, j);
      localCalendar.set(2, TimestamptzAccessor.this.oracleMonth(i));
      localCalendar.set(5, TimestamptzAccessor.this.oracleDay(i));
      localCalendar.set(11, TimestamptzAccessor.this.oracleHour(i));
      localCalendar.set(12, TimestamptzAccessor.this.oracleMin(i));
      localCalendar.set(13, TimestamptzAccessor.this.oracleSec(i));
      localCalendar.set(14, 0);

      if ((TimestamptzAccessor.this.oracleTZ1(i) & TimestamptzAccessor.REGIONIDBIT) != 0)
      {
        int k = TimestamptzAccessor.getHighOrderbits(TimestamptzAccessor.this.oracleTZ1(i));
        k += TimestamptzAccessor.getLowOrderbits(TimestamptzAccessor.this.oracleTZ2(i));

        TIMEZONETAB localTIMEZONETAB = TimestamptzAccessor.this.statement.connection.getTIMEZONETAB();

        if (localTIMEZONETAB.checkID(k)) {
          localTIMEZONETAB.updateTable(TimestamptzAccessor.this.statement.connection, k);
        }
        int m = localTIMEZONETAB.getOffset(localCalendar, k);

        boolean bool1 = localTimeZone.inDaylightTime(localCalendar.getTime());
        boolean bool2 = localTimeZone.inDaylightTime(new java.util.Date(localCalendar.getTimeInMillis() + m));

        if ((!bool1) && (bool2))
        {
          localCalendar.add(14, -1 * localTimeZone.getDSTSavings());
        }
        else if ((bool1) && (!bool2))
        {
          localCalendar.add(14, localTimeZone.getDSTSavings());
        }

        localCalendar.add(10, m / 3600000);
        localCalendar.add(12, m % 3600000 / 60000);
      }
      else
      {
        localCalendar.add(10, TimestamptzAccessor.this.oracleTZ1(i) - TimestamptzAccessor.OFFSET_HOUR);
        localCalendar.add(12, TimestamptzAccessor.this.oracleTZ2(i) - TimestamptzAccessor.OFFSET_MINUTE);
      }

      long l = localCalendar.getTimeInMillis();

      return new Time(l);
    }

    Timestamp getTimestamp(int paramInt)
      throws SQLException
    {
      if (TimestamptzAccessor.this.rowSpaceIndicator == null)
      {
        SQLException localSQLException = DatabaseError.createSqlException(TimestamptzAccessor.this.getConnectionDuringExceptionHandling(), 21);
        localSQLException.fillInStackTrace();
        throw localSQLException;
      }

      if (TimestamptzAccessor.this.rowSpaceIndicator[(TimestamptzAccessor.this.indicatorIndex + paramInt)] == -1) {
        return null;
      }
      int i = TimestamptzAccessor.this.columnIndex + TimestamptzAccessor.this.byteLength * paramInt;

      TimeZone localTimeZone = TimestamptzAccessor.this.statement.getDefaultTimeZone();
      Calendar localCalendar1 = Calendar.getInstance(localTimeZone);
      Calendar localCalendar2 = TimestamptzAccessor.this.statement.getGMTCalendar();

      int j = ((TimestamptzAccessor.this.rowSpaceByte[(0 + i)] & 0xFF) - 100) * 100 + (TimestamptzAccessor.this.rowSpaceByte[(1 + i)] & 0xFF) - 100;

      localCalendar1.set(1, j);
      localCalendar1.set(2, TimestamptzAccessor.this.oracleMonth(i));
      localCalendar1.set(5, TimestamptzAccessor.this.oracleDay(i));
      localCalendar1.set(11, TimestamptzAccessor.this.oracleHour(i));
      localCalendar1.set(12, TimestamptzAccessor.this.oracleMin(i));
      localCalendar1.set(13, TimestamptzAccessor.this.oracleSec(i));
      localCalendar1.set(14, 0);

      localCalendar2.set(1, j);
      localCalendar2.set(2, TimestamptzAccessor.this.oracleMonth(i));
      localCalendar2.set(5, TimestamptzAccessor.this.oracleDay(i));
      localCalendar2.set(11, TimestamptzAccessor.this.oracleHour(i));
      localCalendar2.set(12, TimestamptzAccessor.this.oracleMin(i));
      localCalendar2.set(13, TimestamptzAccessor.this.oracleSec(i));
      localCalendar2.set(14, 0);

      if ((TimestamptzAccessor.this.oracleTZ1(i) & TimestamptzAccessor.REGIONIDBIT) != 0)
      {
        int k = TimestamptzAccessor.getHighOrderbits(TimestamptzAccessor.this.oracleTZ1(i));
        k += TimestamptzAccessor.getLowOrderbits(TimestamptzAccessor.this.oracleTZ2(i));

        TIMEZONETAB localTIMEZONETAB = TimestamptzAccessor.this.statement.connection.getTIMEZONETAB();
        if (localTIMEZONETAB.checkID(k)) {
          localTIMEZONETAB.updateTable(TimestamptzAccessor.this.statement.connection, k);
        }
        int m = localTIMEZONETAB.getOffset(localCalendar2, k);

        boolean bool1 = localTimeZone.inDaylightTime(localCalendar1.getTime());
        boolean bool2 = localTimeZone.inDaylightTime(new java.util.Date(localCalendar1.getTimeInMillis() + m));

        if ((!bool1) && (bool2))
        {
          localCalendar1.add(14, -1 * localTimeZone.getDSTSavings());
        }
        else if ((bool1) && (!bool2))
        {
          localCalendar1.add(14, localTimeZone.getDSTSavings());
        }

        localCalendar1.add(10, m / 3600000);
        localCalendar1.add(12, m % 3600000 / 60000);
      }
      else
      {
        localCalendar1.add(10, TimestamptzAccessor.this.oracleTZ1(i) - TimestamptzAccessor.OFFSET_HOUR);
        localCalendar1.add(12, TimestamptzAccessor.this.oracleTZ2(i) - TimestamptzAccessor.OFFSET_MINUTE);
      }

      long l = localCalendar1.getTimeInMillis();

      Timestamp localTimestamp = new Timestamp(l);

      int n = TimestamptzAccessor.this.oracleNanos(i);

      localTimestamp.setNanos(n);

      return localTimestamp;
    }

    TIMESTAMPTZ getTIMESTAMPTZ(int paramInt)
      throws SQLException
    {
      TIMESTAMPTZ localTIMESTAMPTZ = null;

      if (TimestamptzAccessor.this.rowSpaceIndicator == null)
      {
        SQLException localSQLException = DatabaseError.createSqlException(TimestamptzAccessor.this.getConnectionDuringExceptionHandling(), 21);
        localSQLException.fillInStackTrace();
        throw localSQLException;
      }

      if (TimestamptzAccessor.this.rowSpaceIndicator[(TimestamptzAccessor.this.indicatorIndex + paramInt)] != -1)
      {
        int i = TimestamptzAccessor.this.columnIndex + TimestamptzAccessor.this.byteLength * paramInt;
        byte[] arrayOfByte = new byte[13];

        System.arraycopy(TimestamptzAccessor.this.rowSpaceByte, i, arrayOfByte, 0, 13);

        localTIMESTAMPTZ = new TIMESTAMPTZ(arrayOfByte);
      }

      return localTIMESTAMPTZ;
    }
  }
}