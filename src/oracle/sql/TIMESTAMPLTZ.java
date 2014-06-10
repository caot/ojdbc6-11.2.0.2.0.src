package oracle.sql;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import oracle.jdbc.driver.DatabaseError;

public class TIMESTAMPLTZ extends Datum
{
  private static int SIZE_TIMESTAMPLTZ = 11;
  private static int SIZE_TIMESTAMPLTZ_NOFRAC = 7;

  private static int SIZE_DATE = 7;

  private static int CENTURY_DEFAULT = 119;
  private static int DECADE_DEFAULT = 100;
  private static int MONTH_DEFAULT = 1;
  private static int DAY_DEFAULT = 1;

  private static int DECADE_INIT = 170;

  private static int HOUR_MILLISECOND = 3600000;

  private static int MINUTE_MILLISECOND = 60000;

  private static int JAVA_YEAR = 1970;
  private static int JAVA_MONTH = 0;
  private static int JAVA_DATE = 1;

  private static int MINYEAR = -4712;
  private static int MAXYEAR = 9999;

  private static boolean cached = false;
  private static Calendar dbtz;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  public TIMESTAMPLTZ()
  {
    super(initTimestampltz());
  }

  public TIMESTAMPLTZ(byte[] paramArrayOfByte)
  {
    super(paramArrayOfByte);
  }

  /** @deprecated */
  public TIMESTAMPLTZ(Connection paramConnection, Time paramTime, Calendar paramCalendar)
    throws SQLException
  {
    super(toBytes(paramConnection, paramTime, paramCalendar));
  }

  /** @deprecated */
  public TIMESTAMPLTZ(Connection paramConnection, java.sql.Date paramDate, Calendar paramCalendar)
    throws SQLException
  {
    super(toBytes(paramConnection, paramDate, paramCalendar));
  }

  /** @deprecated */
  public TIMESTAMPLTZ(Connection paramConnection, Timestamp paramTimestamp, Calendar paramCalendar)
    throws SQLException
  {
    super(toBytes(paramConnection, paramTimestamp, paramCalendar));
  }

  /** @deprecated */
  public TIMESTAMPLTZ(Connection paramConnection, DATE paramDATE, Calendar paramCalendar)
    throws SQLException
  {
    super(toBytes(paramConnection, paramDATE, paramCalendar));
  }

  /** @deprecated */
  public TIMESTAMPLTZ(Connection paramConnection, String paramString, Calendar paramCalendar)
    throws SQLException
  {
    super(toBytes(paramConnection, paramString, paramCalendar));
  }

  public TIMESTAMPLTZ(Connection paramConnection, Calendar paramCalendar, Time paramTime)
    throws SQLException
  {
    super(toBytes(paramConnection, paramCalendar, paramTime));
  }

  public TIMESTAMPLTZ(Connection paramConnection, Calendar paramCalendar, java.sql.Date paramDate)
    throws SQLException
  {
    super(toBytes(paramConnection, paramCalendar, paramDate));
  }

  public TIMESTAMPLTZ(Connection paramConnection, Calendar paramCalendar, Timestamp paramTimestamp)
    throws SQLException
  {
    super(toBytes(paramConnection, paramCalendar, paramTimestamp));
  }

  public TIMESTAMPLTZ(Connection paramConnection, Calendar paramCalendar, DATE paramDATE)
    throws SQLException
  {
    super(toBytes(paramConnection, paramCalendar, paramDATE));
  }

  /** @deprecated */
  public TIMESTAMPLTZ(Connection paramConnection, Calendar paramCalendar, String paramString)
    throws SQLException
  {
    super(toBytes(paramConnection, getSessCalendar(paramConnection), paramString));
  }

  public TIMESTAMPLTZ(Connection paramConnection, Time paramTime)
    throws SQLException
  {
    super(toBytes(paramConnection, getSessCalendar(paramConnection), paramTime));
  }

  public TIMESTAMPLTZ(Connection paramConnection, java.sql.Date paramDate)
    throws SQLException
  {
    super(toBytes(paramConnection, getSessCalendar(paramConnection), paramDate));
  }

  public TIMESTAMPLTZ(Connection paramConnection, Timestamp paramTimestamp)
    throws SQLException
  {
    super(toBytes(paramConnection, getSessCalendar(paramConnection), paramTimestamp));
  }

  public TIMESTAMPLTZ(Connection paramConnection, DATE paramDATE)
    throws SQLException
  {
    super(toBytes(paramConnection, getSessCalendar(paramConnection), paramDATE));
  }

  /** @deprecated */
  public TIMESTAMPLTZ(Connection paramConnection, String paramString)
    throws SQLException
  {
    super(toBytes(paramConnection, getSessCalendar(paramConnection), Timestamp.valueOf(paramString)));
  }

  public static java.sql.Date toDate(Connection paramConnection, byte[] paramArrayOfByte, Calendar paramCalendar)
    throws SQLException
  {
    Calendar localCalendar = toCalendar(paramConnection, Calendar.getInstance(), paramArrayOfByte, paramCalendar);

    long l = localCalendar.getTime().getTime();

    return new java.sql.Date(l);
  }

  public static Time toTime(Connection paramConnection, byte[] paramArrayOfByte, Calendar paramCalendar)
    throws SQLException
  {
    Calendar localCalendar = toCalendar(paramConnection, Calendar.getInstance(), paramArrayOfByte, paramCalendar);

    return new Time(localCalendar.get(11), localCalendar.get(12), localCalendar.get(13));
  }

  public static Timestamp toTimestamp(Connection paramConnection, byte[] paramArrayOfByte, Calendar paramCalendar)
    throws SQLException
  {
    return toTimestamp(paramConnection, Calendar.getInstance(), paramArrayOfByte, paramCalendar);
  }

  public static DATE toDATE(Connection paramConnection, byte[] paramArrayOfByte, Calendar paramCalendar)
    throws SQLException
  {
    return new DATE(toTimestamp(paramConnection, getSessCalendar(paramConnection), paramArrayOfByte, null));
  }

  public Timestamp timestampValue(Connection paramConnection, Calendar paramCalendar)
    throws SQLException
  {
    return toTimestamp(paramConnection, getBytes(), paramCalendar);
  }

  /** @deprecated */
  public static String toString(Connection paramConnection, byte[] paramArrayOfByte, Calendar paramCalendar)
    throws SQLException
  {
    Calendar localCalendar = toCalendar(paramConnection, null, paramArrayOfByte, paramCalendar);

    int i = localCalendar.get(1);
    int j = localCalendar.get(2) + 1;
    int k = localCalendar.get(5);
    int m = localCalendar.get(11);
    int n = localCalendar.get(12);
    int i1 = localCalendar.get(13);
    int i2 = -1;

    if (paramArrayOfByte.length == SIZE_TIMESTAMPLTZ) {
      i2 = TIMESTAMP.getNanos(paramArrayOfByte, 7);
    }
    return TIMESTAMPTZ.toString(i, j, k, m, n, i1, i2, localCalendar.getTimeZone().getID());
  }

  public byte[] toBytes()
  {
    return getBytes();
  }

  /** @deprecated */
  public static byte[] toBytes(Connection paramConnection, Time paramTime, Calendar paramCalendar)
    throws SQLException
  {
    if (paramTime == null) {
      return null;
    }
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTime(paramTime);
    localCalendar.set(1, (CENTURY_DEFAULT - 100) * 100 + DECADE_DEFAULT % 100);
    localCalendar.set(2, MONTH_DEFAULT - 1);
    localCalendar.set(5, DAY_DEFAULT);
    byte[] arrayOfByte = toBytes(paramConnection, localCalendar, paramCalendar, 0);
    return arrayOfByte;
  }

  /** @deprecated */
  public static byte[] toBytes(Connection paramConnection, java.sql.Date paramDate, Calendar paramCalendar)
    throws SQLException
  {
    if (paramDate == null)
      return null;
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTime(paramDate);
    localCalendar.set(11, 0);
    localCalendar.set(12, 0);
    localCalendar.set(13, 0);
    byte[] arrayOfByte = toBytes(paramConnection, localCalendar, paramCalendar, 0);
    return arrayOfByte;
  }

  /** @deprecated */
  public static byte[] toBytes(Connection paramConnection, Timestamp paramTimestamp, Calendar paramCalendar)
    throws SQLException
  {
    if (paramTimestamp == null)
      return null;
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTime(paramTimestamp);

    int i = paramTimestamp.getNanos();

    byte[] arrayOfByte = toBytes(paramConnection, localCalendar, paramCalendar, i);
    return arrayOfByte;
  }

  /** @deprecated */
  public static byte[] toBytes(Connection paramConnection, DATE paramDATE, Calendar paramCalendar)
    throws SQLException
  {
    if (paramDATE == null)
      return null;
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTime(DATE.toDate(paramDATE.toBytes()));
    byte[] arrayOfByte = toBytes(paramConnection, localCalendar, paramCalendar, 0);
    return arrayOfByte;
  }

  public static byte[] toBytes(Connection paramConnection, String paramString, Calendar paramCalendar)
    throws SQLException
  {
    return toBytes(paramConnection, Timestamp.valueOf(paramString), paramCalendar);
  }

  public static java.sql.Date toDate(Connection paramConnection, byte[] paramArrayOfByte)
    throws SQLException
  {
    Calendar localCalendar = toCalendar(paramConnection, null, paramArrayOfByte, null);

    long l = localCalendar.getTime().getTime();

    return new java.sql.Date(l);
  }

  public static Time toTime(Connection paramConnection, byte[] paramArrayOfByte)
    throws SQLException
  {
    Calendar localCalendar = toCalendar(paramConnection, null, paramArrayOfByte, null);

    return new Time(localCalendar.get(11), localCalendar.get(12), localCalendar.get(13));
  }

  public static Timestamp toTimestamp(Connection paramConnection, byte[] paramArrayOfByte)
    throws SQLException
  {
    return toTimestamp(paramConnection, null, paramArrayOfByte, null);
  }

  public static DATE toDATE(Connection paramConnection, byte[] paramArrayOfByte)
    throws SQLException
  {
    Calendar localCalendar = toCalendar(paramConnection, null, paramArrayOfByte, null);

    long l = localCalendar.getTime().getTime();

    return new DATE(new Timestamp(l));
  }

  public static TIMESTAMP toTIMESTAMP(Connection paramConnection, byte[] paramArrayOfByte)
    throws SQLException
  {
    return new TIMESTAMP(toTimestamp(paramConnection, getSessCalendar(paramConnection), paramArrayOfByte, null));
  }

  public static TIMESTAMPTZ toTIMESTAMPTZ(Connection paramConnection, byte[] paramArrayOfByte)
    throws SQLException
  {
    return new TIMESTAMPTZ(paramConnection, toTimestamp(paramConnection, getSessCalendar(paramConnection), paramArrayOfByte, null), getSessCalendar(paramConnection));
  }

  public static String toString(Connection paramConnection, byte[] paramArrayOfByte)
    throws SQLException
  {
    return toString(paramConnection, paramArrayOfByte, null);
  }

  public static byte[] toBytes(Connection paramConnection, Calendar paramCalendar, Time paramTime)
    throws SQLException
  {
    if (paramTime == null) {
      return null;
    }

    paramCalendar.setTime(paramTime);
    paramCalendar.set(1, (CENTURY_DEFAULT - 100) * 100 + DECADE_DEFAULT % 100);
    paramCalendar.set(2, MONTH_DEFAULT - 1);
    paramCalendar.set(5, DAY_DEFAULT);

    initDbTimeZone(paramConnection);
    Calendar localCalendar = (Calendar)dbtz.clone();

    byte[] arrayOfByte = toBytes(paramConnection, paramCalendar, localCalendar, 0);
    return arrayOfByte;
  }

  public static byte[] toBytes(Connection paramConnection, Calendar paramCalendar, java.sql.Date paramDate)
    throws SQLException
  {
    if (paramDate == null) {
      return null;
    }
    paramCalendar.setTime(paramDate);
    paramCalendar.set(11, 0);
    paramCalendar.set(12, 0);
    paramCalendar.set(13, 0);

    initDbTimeZone(paramConnection);
    Calendar localCalendar = (Calendar)dbtz.clone();

    byte[] arrayOfByte = toBytes(paramConnection, paramCalendar, localCalendar, 0);
    return arrayOfByte;
  }

  public static byte[] toBytes(Connection paramConnection, Calendar paramCalendar, Timestamp paramTimestamp)
    throws SQLException
  {
    if (paramTimestamp == null) {
      return null;
    }
    paramCalendar.setTime(paramTimestamp);

    int i = paramTimestamp.getNanos();

    initDbTimeZone(paramConnection);
    Calendar localCalendar = (Calendar)dbtz.clone();

    byte[] arrayOfByte = toBytes(paramConnection, paramCalendar, localCalendar, i);
    return arrayOfByte;
  }

  public static byte[] toBytes(Connection paramConnection, Calendar paramCalendar, DATE paramDATE)
    throws SQLException
  {
    if (paramDATE == null) {
      return null;
    }
    paramCalendar.setTime(DATE.toDate(paramDATE.toBytes()));

    initDbTimeZone(paramConnection);
    Calendar localCalendar = (Calendar)dbtz.clone();
    byte[] arrayOfByte = toBytes(paramConnection, paramCalendar, localCalendar, 0);
    return arrayOfByte;
  }

  public static byte[] toBytes(Connection paramConnection, Calendar paramCalendar, String paramString)
    throws SQLException
  {
    return toBytes(paramConnection, paramCalendar, Timestamp.valueOf(paramString));
  }

  public String stringValue(Connection paramConnection)
    throws SQLException
  {
    return toString(paramConnection, getBytes());
  }

  public String stringValue(Connection paramConnection, Calendar paramCalendar)
    throws SQLException
  {
    return toString(paramConnection, getBytes(), paramCalendar);
  }

  public java.sql.Date dateValue(Connection paramConnection, Calendar paramCalendar)
    throws SQLException
  {
    return toDate(paramConnection, getBytes(), paramCalendar);
  }

  public java.sql.Date dateValue(Connection paramConnection)
    throws SQLException
  {
    return toDate(paramConnection, getBytes());
  }

  public Time timeValue(Connection paramConnection)
    throws SQLException
  {
    return toTime(paramConnection, getBytes());
  }

  public Time timeValue(Connection paramConnection, Calendar paramCalendar)
    throws SQLException
  {
    return toTime(paramConnection, getBytes(), paramCalendar);
  }

  public Object toJdbc()
    throws SQLException
  {
    return null;
  }

  public Object makeJdbcArray(int paramInt)
  {
    Timestamp[] arrayOfTimestamp = new Timestamp[paramInt];

    return arrayOfTimestamp;
  }

  public boolean isConvertibleTo(Class paramClass)
  {
    if ((paramClass.getName().compareTo("java.sql.Date") == 0) || (paramClass.getName().compareTo("java.sql.Time") == 0) || (paramClass.getName().compareTo("java.sql.Timestamp") == 0) || (paramClass.getName().compareTo("java.lang.String") == 0))
    {
      return true;
    }
    return false;
  }

  private static byte[] initTimestampltz()
  {
    byte[] arrayOfByte = new byte[SIZE_TIMESTAMPLTZ];

    arrayOfByte[0] = ((byte)CENTURY_DEFAULT);
    arrayOfByte[1] = ((byte)DECADE_INIT);
    arrayOfByte[2] = ((byte)MONTH_DEFAULT);
    arrayOfByte[3] = ((byte)DAY_DEFAULT);
    arrayOfByte[4] = 1;
    arrayOfByte[5] = 1;
    arrayOfByte[6] = 1;

    return arrayOfByte;
  }

  private static byte[] toBytes(Connection paramConnection, Calendar paramCalendar1, Calendar paramCalendar2, int paramInt)
    throws SQLException
  {
    byte[] arrayOfByte;
    if (paramInt == 0)
      arrayOfByte = new byte[SIZE_TIMESTAMPLTZ_NOFRAC];
    else {
      arrayOfByte = new byte[SIZE_TIMESTAMPLTZ];
    }
    TimeZoneAdjust(paramConnection, paramCalendar1, paramCalendar2);

    int i = paramCalendar2.get(1);
    if ((i < MINYEAR) || (i > MAXYEAR))
    {
      SQLException localSQLException = DatabaseError.createSqlException(null, 268);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    arrayOfByte[0] = ((byte)(paramCalendar2.get(1) / 100 + 100));
    arrayOfByte[1] = ((byte)(paramCalendar2.get(1) % 100 + 100));
    arrayOfByte[2] = ((byte)(paramCalendar2.get(2) + 1));
    arrayOfByte[3] = ((byte)paramCalendar2.get(5));
    arrayOfByte[4] = ((byte)(paramCalendar2.get(11) + 1));
    arrayOfByte[5] = ((byte)(paramCalendar2.get(12) + 1));
    arrayOfByte[6] = ((byte)(paramCalendar2.get(13) + 1));

    if (paramInt != 0)
    {
      arrayOfByte[7] = ((byte)(paramInt >> 24));
      arrayOfByte[8] = ((byte)(paramInt >> 16 & 0xFF));
      arrayOfByte[9] = ((byte)(paramInt >> 8 & 0xFF));
      arrayOfByte[10] = ((byte)(paramInt & 0xFF));
    }

    return arrayOfByte;
  }

  private static Timestamp toTimestamp(Connection paramConnection, Calendar paramCalendar1, byte[] paramArrayOfByte, Calendar paramCalendar2)
    throws SQLException
  {
    Calendar localCalendar = toCalendar(paramConnection, paramCalendar1, paramArrayOfByte, paramCalendar2);

    long l = localCalendar.getTime().getTime();

    Timestamp localTimestamp = new Timestamp(l);
    int i = 0;

    if (paramArrayOfByte.length == SIZE_TIMESTAMPLTZ) {
      i = TIMESTAMP.getNanos(paramArrayOfByte, 7);
    }
    localTimestamp.setNanos(i);

    return localTimestamp;
  }

  private static final Calendar toCalendar(Connection paramConnection, Calendar paramCalendar1, byte[] paramArrayOfByte, Calendar paramCalendar2)
    throws SQLException
  {
    int i = paramArrayOfByte.length;
    int[] arrayOfInt;
    if (i == SIZE_TIMESTAMPLTZ)
      arrayOfInt = new int[SIZE_TIMESTAMPLTZ];
    else {
      arrayOfInt = new int[SIZE_TIMESTAMPLTZ_NOFRAC];
    }
    for (int j = 0; j < paramArrayOfByte.length; j++) {
      paramArrayOfByte[j] &= 255;
    }

    j = getJavaYear(arrayOfInt[0], arrayOfInt[1]);

    if (paramCalendar2 == null)
    {
      initDbTimeZone(paramConnection);

      paramCalendar2 = (Calendar)dbtz.clone();
    }

    paramCalendar2.set(1, j);
    paramCalendar2.set(2, arrayOfInt[2] - 1);
    paramCalendar2.set(5, arrayOfInt[3]);
    paramCalendar2.set(11, arrayOfInt[4] - 1);
    paramCalendar2.set(12, arrayOfInt[5] - 1);
    paramCalendar2.set(13, arrayOfInt[6] - 1);
    paramCalendar2.set(14, 0);

    if (paramCalendar1 == null) {
      paramCalendar1 = getSessCalendar(paramConnection);
    }

    TimeZoneAdjust(paramConnection, paramCalendar2, paramCalendar1);

    return paramCalendar1;
  }

  static void TimeZoneAdjust(Connection paramConnection, Calendar paramCalendar1, Calendar paramCalendar2)
    throws SQLException
  {
    TimeZone localTimeZone = paramCalendar1.getTimeZone();

    String str1 = new String(paramCalendar1.getTimeZone().getID());
    String str2 = new String(paramCalendar2.getTimeZone().getID());

    if ((!str2.equals(str1)) && ((!str2.equals("Custom")) || (!str1.equals("Custom"))))
    {
      OffsetDST localOffsetDST = new OffsetDST();

      k = getZoneOffset(paramConnection, paramCalendar1, localOffsetDST);
      m = localOffsetDST.getOFFSET();

      boolean bool1 = localTimeZone.inDaylightTime(paramCalendar1.getTime());

      paramCalendar1.add(11, -(m / HOUR_MILLISECOND));
      paramCalendar1.add(12, -(m % HOUR_MILLISECOND) / MINUTE_MILLISECOND);

      boolean bool2 = localTimeZone.inDaylightTime(paramCalendar1.getTime());

      if ((bool1) && (!bool2))
        paramCalendar1.add(14, 3600000);
      else if ((!bool1) && (bool2))
        paramCalendar1.add(14, -3600000);
      int i;
      if (str2.equals("Custom")) {
        i = paramCalendar2.getTimeZone().getRawOffset();
      }
      else
      {
        n = ZONEIDMAP.getID(str2);

        if (!ZONEIDMAP.isValidID(n))
        {
          if (paramCalendar2.getTimeZone().useDaylightTime())
            throw new SQLException("Timezone not supported");
          i = paramCalendar2.getTimeZone().getRawOffset();
        }
        else
        {
          TIMEZONETAB localTIMEZONETAB = getTIMEZONETAB(paramConnection);
          if (localTIMEZONETAB.checkID(n)) {
            localTIMEZONETAB.updateTable(paramConnection, n);
          }

          i = localTIMEZONETAB.getOffset(paramCalendar1, n);
        }

      }

      bool1 = localTimeZone.inDaylightTime(paramCalendar1.getTime());

      paramCalendar1.add(11, i / HOUR_MILLISECOND);
      paramCalendar1.add(12, i % HOUR_MILLISECOND / MINUTE_MILLISECOND);

      bool2 = localTimeZone.inDaylightTime(paramCalendar1.getTime());

      if ((bool1) && (!bool2))
        paramCalendar1.add(14, 3600000);
      else if ((!bool1) && (bool2)) {
        paramCalendar1.add(14, -3600000);
      }

    }

    if ((str2.equals("Custom")) && (str1.equals("Custom")))
    {
      j = paramCalendar1.getTimeZone().getRawOffset();
      k = paramCalendar2.getTimeZone().getRawOffset();
      m = 0;

      if (j != k)
      {
        m = j - k;
        m = m > 0 ? m : -m;
      }

      if (j > k) {
        m = -m;
      }
      paramCalendar1.add(11, m / HOUR_MILLISECOND);
      paramCalendar1.add(12, m % HOUR_MILLISECOND / MINUTE_MILLISECOND);
    }

    int j = paramCalendar1.get(1);
    int k = paramCalendar1.get(2);
    int m = paramCalendar1.get(5);
    int n = paramCalendar1.get(11);
    int i1 = paramCalendar1.get(12);
    int i2 = paramCalendar1.get(13);
    int i3 = paramCalendar1.get(14);

    paramCalendar2.set(1, j);
    paramCalendar2.set(2, k);
    paramCalendar2.set(5, m);
    paramCalendar2.set(11, n);
    paramCalendar2.set(12, i1);
    paramCalendar2.set(13, i2);
    paramCalendar2.set(14, i3);
  }

  private static int getJavaYear(int paramInt1, int paramInt2)
  {
    return (paramInt1 - 100) * 100 + (paramInt2 - 100);
  }

  private static byte getZoneOffset(Connection paramConnection, Calendar paramCalendar, OffsetDST paramOffsetDST)
    throws SQLException
  {
    byte b = 0;

    if (paramCalendar.getTimeZone().getID() == "Custom") {
      paramOffsetDST.setOFFSET(paramCalendar.getTimeZone().getRawOffset());
    }
    else
    {
      String str = new String(paramCalendar.getTimeZone().getID());

      int i = ZONEIDMAP.getID(str);
      if (!ZONEIDMAP.isValidID(i))
      {
        if (paramCalendar.getTimeZone().useDaylightTime()) {
          throw new SQLException("Timezone not supported");
        }
        paramOffsetDST.setOFFSET(paramCalendar.getTimeZone().getRawOffset());
      }
      else
      {
        TIMEZONETAB localTIMEZONETAB = getTIMEZONETAB(paramConnection);
        if (localTIMEZONETAB.checkID(i)) {
          localTIMEZONETAB.updateTable(paramConnection, i);
        }

        b = localTIMEZONETAB.getLocalOffset(paramCalendar, i, paramOffsetDST);
      }
    }

    return b;
  }

  private static Calendar getDbTzCalendar(String paramString)
  {
    int i = paramString.charAt(0);
    String str;
    if ((i == 43) || (i == 45))
    {
      str = "GMT" + paramString;
    }
    else
    {
      str = paramString;
    }

    TimeZone localTimeZone = TimeZone.getTimeZone(str);

    return new GregorianCalendar(localTimeZone);
  }

  static Calendar getSessCalendar(Connection paramConnection)
  {
    String str = ((oracle.jdbc.OracleConnection)paramConnection).getSessionTimeZone();
    Calendar localCalendar;
    if (str == null)
    {
      localCalendar = Calendar.getInstance();
    }
    else
    {
      TimeZone localTimeZone = TimeZone.getTimeZone(str);
      localCalendar = Calendar.getInstance(localTimeZone);
    }

    return localCalendar;
  }

  private static synchronized void initDbTimeZone(Connection paramConnection)
    throws SQLException
  {
    if (!cached)
    {
      oracle.jdbc.internal.OracleConnection localOracleConnection = ((oracle.jdbc.OracleConnection)paramConnection).physicalConnectionWithin();

      String str = localOracleConnection.getDatabaseTimeZone();
      dbtz = getDbTzCalendar(str);
      cached = true;
    }
  }

  static TIMEZONETAB getTIMEZONETAB(Connection paramConnection) throws SQLException
  {
    oracle.jdbc.internal.OracleConnection localOracleConnection = ((oracle.jdbc.OracleConnection)paramConnection).physicalConnectionWithin();

    return localOracleConnection.getTIMEZONETAB();
  }
}