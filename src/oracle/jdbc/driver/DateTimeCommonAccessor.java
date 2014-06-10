package oracle.jdbc.driver;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.TimeZone;
import oracle.sql.DATE;
import oracle.sql.TIMESTAMP;
import oracle.sql.TIMESTAMPTZ;

abstract class DateTimeCommonAccessor extends Accessor
{
  static final int GREGORIAN_CUTOVER_YEAR = 1582;
  static final long GREGORIAN_CUTOVER = -12219292800000L;
  static final int JAN_1_1_JULIAN_DAY = 1721426;
  static final int EPOCH_JULIAN_DAY = 2440588;
  static final int ONE_SECOND = 1000;
  static final int ONE_MINUTE = 60000;
  static final int ONE_HOUR = 3600000;
  static final long ONE_DAY = 86400000L;
  static final int[] NUM_DAYS = { 0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334 };

  static final int[] LEAP_NUM_DAYS = { 0, 31, 60, 91, 121, 152, 182, 213, 244, 274, 305, 335 };
  static final int ORACLE_CENTURY = 0;
  static final int ORACLE_YEAR = 1;
  static final int ORACLE_MONTH = 2;
  static final int ORACLE_DAY = 3;
  static final int ORACLE_HOUR = 4;
  static final int ORACLE_MIN = 5;
  static final int ORACLE_SEC = 6;
  static final int ORACLE_NANO1 = 7;
  static final int ORACLE_NANO2 = 8;
  static final int ORACLE_NANO3 = 9;
  static final int ORACLE_NANO4 = 10;
  static final int ORACLE_TZ1 = 11;
  static final int ORACLE_TZ2 = 12;
  static final int SIZE_DATE = 7;
  static final int MAX_TIMESTAMP_LENGTH = 11;
  static TimeZone epochTimeZone;
  static long epochTimeZoneOffset;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  Time getTime(int paramInt)
    throws SQLException
  {
    Time localTime = null;

    if (this.rowSpaceIndicator == null)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 21);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1)
    {
      int i = this.columnIndex + this.byteLength * paramInt;

      TimeZone localTimeZone = this.statement.getDefaultTimeZone();

      if (localTimeZone != epochTimeZone)
      {
        epochTimeZoneOffset = calculateEpochOffset(localTimeZone);
        epochTimeZone = localTimeZone;
      }

      localTime = new Time(oracleTime(i) - epochTimeZoneOffset);
    }

    return localTime;
  }

  Date getDate(int paramInt)
    throws SQLException
  {
    return getDate(paramInt, this.statement.getDefaultCalendar());
  }

  Date getDate(int paramInt, Calendar paramCalendar)
    throws SQLException
  {
    if (paramCalendar == null) {
      return getDate(paramInt);
    }
    Date localDate = null;

    if (this.rowSpaceIndicator == null)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 21);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1)
    {
      int i = this.columnIndex + this.byteLength * paramInt;
      int j = ((this.rowSpaceByte[(0 + i)] & 0xFF) - 100) * 100 + (this.rowSpaceByte[(1 + i)] & 0xFF) - 100;

      paramCalendar.set(j, oracleMonth(i), oracleDay(i), 0, 0, 0);
      paramCalendar.set(14, 0);

      if ((j > 0) && (paramCalendar.isSet(0))) {
        paramCalendar.set(0, 1);
      }
      localDate = new Date(paramCalendar.getTimeInMillis());
    }

    return localDate;
  }

  Time getTime(int paramInt, Calendar paramCalendar)
    throws SQLException
  {
    if (paramCalendar == null) {
      return getTime(paramInt);
    }
    Time localTime = null;

    if (this.rowSpaceIndicator == null)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 21);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1)
    {
      int i = this.columnIndex + this.byteLength * paramInt;
      int j = ((this.rowSpaceByte[(0 + i)] & 0xFF) - 100) * 100 + (this.rowSpaceByte[(1 + i)] & 0xFF) - 100;

      paramCalendar.set(1, 1970);
      paramCalendar.set(2, 0);
      paramCalendar.set(5, 1);
      paramCalendar.set(11, oracleHour(i));
      paramCalendar.set(12, oracleMin(i));
      paramCalendar.set(13, oracleSec(i));
      paramCalendar.set(14, 0);

      if ((j > 0) && (paramCalendar.isSet(0))) {
        paramCalendar.set(0, 1);
      }

      localTime = new Time(paramCalendar.getTimeInMillis());
    }

    return localTime;
  }

  Timestamp getTimestamp(int paramInt)
    throws SQLException
  {
    return getTimestamp(paramInt, this.statement.getDefaultCalendar());
  }

  Timestamp getTimestamp(int paramInt, Calendar paramCalendar)
    throws SQLException
  {
    if (paramCalendar == null) {
      return getTimestamp(paramInt);
    }
    Timestamp localTimestamp = null;

    if (this.rowSpaceIndicator == null)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 21);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1)
    {
      int i = this.columnIndex + this.byteLength * paramInt;
      int j = ((this.rowSpaceByte[(0 + i)] & 0xFF) - 100) * 100 + (this.rowSpaceByte[(1 + i)] & 0xFF) - 100;

      paramCalendar.set(j, oracleMonth(i), oracleDay(i), oracleHour(i), oracleMin(i), oracleSec(i));

      paramCalendar.set(14, 0);

      if ((j > 0) && (paramCalendar.isSet(0))) {
        paramCalendar.set(0, 1);
      }
      localTimestamp = new Timestamp(paramCalendar.getTimeInMillis());

      int k = this.rowSpaceIndicator[(this.lengthIndex + paramInt)];
      if (k >= 11)
      {
        localTimestamp.setNanos(oracleNanos(i));
      }

    }

    return localTimestamp;
  }

  DATE getDATE(int paramInt)
    throws SQLException
  {
    DATE localDATE = null;

    if (this.rowSpaceIndicator == null)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 21);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1)
    {
      int i = this.columnIndex + this.byteLength * paramInt;
      byte[] arrayOfByte = new byte[7];

      System.arraycopy(this.rowSpaceByte, i, arrayOfByte, 0, 7);

      localDATE = new DATE(arrayOfByte);
    }

    return localDATE;
  }

  TIMESTAMP getTIMESTAMP(int paramInt)
    throws SQLException
  {
    TIMESTAMP localTIMESTAMP = null;

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
      byte[] arrayOfByte = new byte[i];
      System.arraycopy(this.rowSpaceByte, j, arrayOfByte, 0, i);
      localTIMESTAMP = new TIMESTAMP(arrayOfByte);
    }

    return localTIMESTAMP;
  }

  final int oracleYear(int paramInt)
  {
    int i = ((this.rowSpaceByte[(0 + paramInt)] & 0xFF) - 100) * 100 + (this.rowSpaceByte[(1 + paramInt)] & 0xFF) - 100;

    return i <= 0 ? i + 1 : i;
  }

  final int oracleMonth(int paramInt)
  {
    return this.rowSpaceByte[(2 + paramInt)] - 1;
  }

  final int oracleDay(int paramInt)
  {
    return this.rowSpaceByte[(3 + paramInt)];
  }

  final int oracleHour(int paramInt)
  {
    return this.rowSpaceByte[(4 + paramInt)] - 1;
  }

  final int oracleMin(int paramInt)
  {
    return this.rowSpaceByte[(5 + paramInt)] - 1;
  }

  final int oracleSec(int paramInt)
  {
    return this.rowSpaceByte[(6 + paramInt)] - 1;
  }

  final int oracleTZ1(int paramInt)
  {
    return this.rowSpaceByte[(11 + paramInt)];
  }

  final int oracleTZ2(int paramInt)
  {
    return this.rowSpaceByte[(12 + paramInt)];
  }

  final int oracleTime(int paramInt)
  {
    int i = oracleHour(paramInt);

    i *= 60;
    i += oracleMin(paramInt);
    i *= 60;
    i += oracleSec(paramInt);
    i *= 1000;

    return i;
  }

  final int oracleNanos(int paramInt)
  {
    int i = (this.rowSpaceByte[(7 + paramInt)] & 0xFF) << 24;

    i |= (this.rowSpaceByte[(8 + paramInt)] & 0xFF) << 16;
    i |= (this.rowSpaceByte[(9 + paramInt)] & 0xFF) << 8;
    i |= this.rowSpaceByte[(10 + paramInt)] & 0xFF & 0xFF;

    return i;
  }

  static final long computeJulianDay(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3)
  {
    int i = paramInt1 % 4 == 0 ? 1 : 0;
    int j = paramInt1 - 1;
    long l = 365L * j + floorDivide(j, 4L) + 1721423L;

    if (paramBoolean)
    {
      i = (i != 0) && ((paramInt1 % 100 != 0) || (paramInt1 % 400 == 0)) ? 1 : 0;

      l += floorDivide(j, 400L) - floorDivide(j, 100L) + 2L;
    }

    return l + paramInt3 + (i != 0 ? LEAP_NUM_DAYS[paramInt2] : NUM_DAYS[paramInt2]);
  }

  static final long floorDivide(long paramLong1, long paramLong2)
  {
    return paramLong1 >= 0L ? paramLong1 / paramLong2 : (paramLong1 + 1L) / paramLong2 - 1L;
  }

  static final long julianDayToMillis(long paramLong)
  {
    return (paramLong - 2440588L) * 86400000L;
  }

  static final long zoneOffset(TimeZone paramTimeZone, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    return paramTimeZone.getOffset(paramInt1 < 0 ? 0 : 1, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
  }

  static long getMillis(int paramInt1, int paramInt2, int paramInt3, int paramInt4, TimeZone paramTimeZone)
  {
    boolean bool = paramInt1 >= 1582;
    long l1 = computeJulianDay(bool, paramInt1, paramInt2, paramInt3);
    long l2 = (l1 - 2440588L) * 86400000L;

    if (bool != l2 >= -12219292800000L)
    {
      l1 = computeJulianDay(!bool, paramInt1, paramInt2, paramInt3);
      l2 = (l1 - 2440588L) * 86400000L;
    }

    l2 += paramInt4;

    return l2 - zoneOffset(paramTimeZone, paramInt1, paramInt2, paramInt3, julianDayToDayOfWeek(l1), paramInt4);
  }

  static final int julianDayToDayOfWeek(long paramLong)
  {
    int i = (int)((paramLong + 1L) % 7L);

    return i + (i < 0 ? 8 : 1);
  }

  static long calculateEpochOffset(TimeZone paramTimeZone)
  {
    return zoneOffset(paramTimeZone, 1970, 0, 1, 5, 0);
  }

  String toText(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, boolean paramBoolean, String paramString)
    throws SQLException
  {
    return TIMESTAMPTZ.toString(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramString);
  }
}