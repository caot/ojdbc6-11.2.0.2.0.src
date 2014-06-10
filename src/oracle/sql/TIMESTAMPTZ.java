package oracle.sql;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.SimpleTimeZone;
import java.util.TimeZone;
import oracle.jdbc.driver.DatabaseError;

public class TIMESTAMPTZ extends Datum
{
  static final Calendar CAL_GMT_US = Calendar.getInstance(TimeZone.getTimeZone("GMT"), Locale.US);

  static final TimeZone TIMEZONE_UTC = TimeZone.getTimeZone("UTC");

  private static int HOUR_MILLISECOND = 3600000;

  private static int MINUTE_MILLISECOND = 60000;

  private static int OFFSET_HOUR = 20;
  private static int OFFSET_MINUTE = 60;

  private static byte REGIONIDBIT = -128;

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  public TIMESTAMPTZ()
  {
    super(initTimestamptz());
  }

  public TIMESTAMPTZ(byte[] paramArrayOfByte)
  {
    super(paramArrayOfByte);
  }

  public TIMESTAMPTZ(Connection paramConnection, java.sql.Date paramDate)
    throws SQLException
  {
    super(toBytes(paramConnection, paramDate));
  }

  public TIMESTAMPTZ(Connection paramConnection, java.sql.Date paramDate, Calendar paramCalendar)
    throws SQLException
  {
    super(toBytes(paramConnection, paramDate, paramCalendar));
  }

  public TIMESTAMPTZ(Connection paramConnection, Time paramTime)
    throws SQLException
  {
    super(toBytes(paramConnection, paramTime));
  }

  public TIMESTAMPTZ(Connection paramConnection, Time paramTime, Calendar paramCalendar)
    throws SQLException
  {
    super(toBytes(paramConnection, paramTime, paramCalendar));
  }

  public TIMESTAMPTZ(Connection paramConnection, Timestamp paramTimestamp)
    throws SQLException
  {
    super(toBytes(paramConnection, paramTimestamp));
  }

  public TIMESTAMPTZ(Connection paramConnection, Timestamp paramTimestamp, Calendar paramCalendar)
    throws SQLException
  {
    super(toBytes(paramConnection, paramTimestamp, paramCalendar));
  }

  public TIMESTAMPTZ(Connection paramConnection, DATE paramDATE)
    throws SQLException
  {
    super(toBytes(paramConnection, paramDATE));
  }

  public TIMESTAMPTZ(Connection paramConnection, String paramString)
    throws SQLException
  {
    super(toBytes(paramConnection, paramString));
  }

  public TIMESTAMPTZ(Connection paramConnection, String paramString, Calendar paramCalendar)
    throws SQLException
  {
    super(toBytes(paramConnection, paramString, paramCalendar));
  }

  public static java.sql.Date toDate(Connection paramConnection, byte[] paramArrayOfByte)
    throws SQLException
  {
    int[] arrayOfInt = new int[13];

    for (int j = 0; j < 13; j++) {
      paramArrayOfByte[j] &= 255;
    }

    j = getJavaYear(arrayOfInt[0], arrayOfInt[1]);

    Calendar localCalendar = Calendar.getInstance();

    localCalendar.set(1, j);
    localCalendar.set(2, arrayOfInt[2] - 1);
    localCalendar.set(5, arrayOfInt[3]);
    localCalendar.set(11, arrayOfInt[4] - 1);
    localCalendar.set(12, arrayOfInt[5] - 1);
    localCalendar.set(13, arrayOfInt[6] - 1);
    localCalendar.set(14, 0);

    if ((arrayOfInt[11] & REGIONIDBIT) != 0)
    {
      int k = getHighOrderbits(arrayOfInt[11]);
      k += getLowOrderbits(arrayOfInt[12]);

      TIMEZONETAB localTIMEZONETAB = getTIMEZONETAB(paramConnection);
      if (localTIMEZONETAB.checkID(k)) {
        localTIMEZONETAB.updateTable(paramConnection, k);
      }

      int i = localTIMEZONETAB.getOffset(localCalendar, k);

      localCalendar.add(10, i / HOUR_MILLISECOND);
      localCalendar.add(12, i % HOUR_MILLISECOND / MINUTE_MILLISECOND);
    }
    else
    {
      localCalendar.add(10, arrayOfInt[11] - OFFSET_HOUR);
      localCalendar.add(12, arrayOfInt[12] - OFFSET_MINUTE);
    }

    long l = localCalendar.getTime().getTime();

    return new java.sql.Date(l);
  }

  public static java.sql.Date toDate2(Connection paramConnection, byte[] paramArrayOfByte)
    throws SQLException
  {
    int[] arrayOfInt = new int[13];

    for (int i = 0; i < 13; i++) {
      paramArrayOfByte[i] &= 255;
    }

    i = getJavaYear(arrayOfInt[0], arrayOfInt[1]);

    Calendar localCalendar = (Calendar)CAL_GMT_US.clone();
    localCalendar.set(1, i);
    localCalendar.set(2, arrayOfInt[2] - 1);
    localCalendar.set(5, arrayOfInt[3]);
    localCalendar.set(11, arrayOfInt[4] - 1);
    localCalendar.set(12, arrayOfInt[5] - 1);
    localCalendar.set(13, arrayOfInt[6] - 1);
    localCalendar.set(14, 0);

    long l = localCalendar.getTime().getTime();

    return new java.sql.Date(l);
  }

  public static Time toTime(Connection paramConnection, byte[] paramArrayOfByte)
    throws SQLException
  {
    int[] arrayOfInt = new int[13];

    for (int i = 0; i < 13; i++) {
      paramArrayOfByte[i] &= 255;
    }

    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTimeZone(TIMEZONE_UTC);

    int j = getJavaYear(arrayOfInt[0], arrayOfInt[1]);

    localCalendar.set(1, j);
    localCalendar.set(2, arrayOfInt[2] - 1);
    localCalendar.set(5, arrayOfInt[3]);
    localCalendar.set(11, arrayOfInt[4] - 1);
    localCalendar.set(12, arrayOfInt[5] - 1);
    localCalendar.set(13, arrayOfInt[6] - 1);
    localCalendar.set(14, 0);

    return new Time(localCalendar.getTimeInMillis());
  }

  public static DATE toDATE(Connection paramConnection, byte[] paramArrayOfByte)
    throws SQLException
  {
    return new DATE(toTimestampInSessionTimezone(paramConnection, paramArrayOfByte));
  }

  public static TIMESTAMP toTIMESTAMP(Connection paramConnection, byte[] paramArrayOfByte)
    throws SQLException
  {
    return new TIMESTAMP(toTimestampInSessionTimezone(paramConnection, paramArrayOfByte));
  }

  public static Timestamp toTimestamp(Connection paramConnection, byte[] paramArrayOfByte)
    throws SQLException
  {
    int[] arrayOfInt = new int[13];

    for (int j = 0; j < 13; j++) {
      paramArrayOfByte[j] &= 255;
    }

    Calendar localCalendar1 = Calendar.getInstance();

    Calendar localCalendar2 = (Calendar)CAL_GMT_US.clone();

    Calendar localCalendar3 = Calendar.getInstance();

    int k = getJavaYear(arrayOfInt[0], arrayOfInt[1]);

    localCalendar1.set(1, k);
    localCalendar1.set(2, arrayOfInt[2] - 1);
    localCalendar1.set(5, arrayOfInt[3]);
    localCalendar1.set(11, arrayOfInt[4] - 1);
    localCalendar1.set(12, arrayOfInt[5] - 1);
    localCalendar1.set(13, arrayOfInt[6] - 1);
    localCalendar1.set(14, 0);

    localCalendar2.set(1, k);
    localCalendar2.set(2, arrayOfInt[2] - 1);
    localCalendar2.set(5, arrayOfInt[3]);
    localCalendar2.set(11, arrayOfInt[4] - 1);
    localCalendar2.set(12, arrayOfInt[5] - 1);
    localCalendar2.set(13, arrayOfInt[6] - 1);
    localCalendar2.set(14, 0);

    long l1 = localCalendar1.getTime().getTime();

    if ((arrayOfInt[11] & REGIONIDBIT) != 0)
    {
      int m = getHighOrderbits(arrayOfInt[11]);
      m += getLowOrderbits(arrayOfInt[12]);

      TIMEZONETAB localTIMEZONETAB = getTIMEZONETAB(paramConnection);
      if (localTIMEZONETAB.checkID(m)) {
        localTIMEZONETAB.updateTable(paramConnection, m);
      }

      int i = localTIMEZONETAB.getOffset(localCalendar2, m);

      l1 += i;

      TimeZone localTimeZone = localCalendar1.getTimeZone();
      localObject = localCalendar3.getTimeZone();

      if ((!localTimeZone.inDaylightTime(localCalendar1.getTime())) && (((TimeZone)localObject).inDaylightTime(new Timestamp(l1)) == true))
      {
        if ((localObject instanceof SimpleTimeZone))
          l1 -= ((SimpleTimeZone)localObject).getDSTSavings();
        else {
          l1 -= 3600000L;
        }

      }

      if ((localTimeZone.inDaylightTime(localCalendar1.getTime()) == true) && (!((TimeZone)localObject).inDaylightTime(new Timestamp(l1))))
      {
        if ((localObject instanceof SimpleTimeZone))
          l1 += ((SimpleTimeZone)localTimeZone).getDSTSavings();
        else
          l1 += 3600000L;
      }
    }
    else
    {
      localCalendar1.add(10, arrayOfInt[11] - OFFSET_HOUR);
      localCalendar1.add(12, arrayOfInt[12] - OFFSET_MINUTE);

      l1 = localCalendar1.getTime().getTime();
    }

    Timestamp localTimestamp = new Timestamp(l1);

    long l2 = localCalendar2.getTime().getTime();

    Object localObject = Calendar.getInstance();
    ((Calendar)localObject).setTimeInMillis(l2);

    Calendar localCalendar4 = Calendar.getInstance();
    localCalendar4.setTime(localTimestamp);

    boolean bool1 = ((Calendar)localObject).getTimeZone().inDaylightTime(((Calendar)localObject).getTime());
    boolean bool2 = localCalendar4.getTimeZone().inDaylightTime(localCalendar4.getTime());

    if ((bool1 == true) && (!bool2))
      localTimestamp = new Timestamp(l1 - ((Calendar)localObject).getTimeZone().getDSTSavings());
    else if ((!bool1) && (bool2 == true)) {
      localTimestamp = new Timestamp(l1 + localCalendar4.getTimeZone().getDSTSavings());
    }

    int n = TIMESTAMP.getNanos(paramArrayOfByte, 7);

    localTimestamp.setNanos(n);

    return localTimestamp;
  }

  public static Timestamp toTimestamp2(Connection paramConnection, byte[] paramArrayOfByte)
    throws SQLException
  {
    int[] arrayOfInt = new int[13];

    for (int i = 0; i < 13; i++) {
      paramArrayOfByte[i] &= 255;
    }

    i = getJavaYear(arrayOfInt[0], arrayOfInt[1]);

    Calendar localCalendar = (Calendar)CAL_GMT_US.clone();

    localCalendar.clear();

    localCalendar.set(1, i);
    localCalendar.set(2, arrayOfInt[2] - 1);
    localCalendar.set(5, arrayOfInt[3]);
    localCalendar.set(11, arrayOfInt[4] - 1);
    localCalendar.set(12, arrayOfInt[5] - 1);
    localCalendar.set(13, arrayOfInt[6] - 1);
    localCalendar.set(14, 0);

    long l = localCalendar.getTime().getTime();

    Timestamp localTimestamp = new Timestamp(l);

    int j = TIMESTAMP.getNanos(paramArrayOfByte, 7);

    localTimestamp.setNanos(j);

    return localTimestamp;
  }

  static Timestamp toTimestampInSessionTimezone(Connection paramConnection, byte[] paramArrayOfByte)
    throws SQLException
  {
    int[] arrayOfInt = new int[13];
    for (int i = 0; i < 13; i++) {
      paramArrayOfByte[i] &= 255;
    }

    i = getJavaYear(arrayOfInt[0], arrayOfInt[1]);

    Calendar localCalendar1 = (Calendar)CAL_GMT_US.clone();
    localCalendar1.clear();

    localCalendar1.set(1, i);
    localCalendar1.set(2, arrayOfInt[2] - 1);
    localCalendar1.set(5, arrayOfInt[3]);
    localCalendar1.set(11, arrayOfInt[4] - 1);
    localCalendar1.set(12, arrayOfInt[5] - 1);
    localCalendar1.set(13, arrayOfInt[6] - 1);
    localCalendar1.set(14, 0);

    Calendar localCalendar2 = TIMESTAMPLTZ.getSessCalendar(paramConnection);
    TIMESTAMPLTZ.TimeZoneAdjust(paramConnection, localCalendar1, localCalendar2);

    long l = localCalendar2.getTime().getTime();

    Timestamp localTimestamp = new Timestamp(l);

    int j = TIMESTAMP.getNanos(paramArrayOfByte, 7);

    localTimestamp.setNanos(j);

    return localTimestamp;
  }

  public static String toString(Connection paramConnection, byte[] paramArrayOfByte)
    throws SQLException
  {
    Timestamp localTimestamp = toTimestamp(paramConnection, paramArrayOfByte);

    Calendar localCalendar = Calendar.getInstance();

    localCalendar.setTime(localTimestamp);

    int i = localCalendar.get(1);
    int j = localCalendar.get(2) + 1;
    int k = localCalendar.get(5);
    int m = localCalendar.get(11);
    int n = localCalendar.get(12);
    int i1 = localCalendar.get(13);
    int i2 = 0;
    i2 = (paramArrayOfByte[7] & 0xFF) << 24;
    i2 |= (paramArrayOfByte[8] & 0xFF) << 16;
    i2 |= (paramArrayOfByte[9] & 0xFF) << 8;
    i2 |= paramArrayOfByte[10] & 0xFF & 0xFF;
    int i3;
    String str;
    if ((paramArrayOfByte[11] & REGIONIDBIT) != 0)
    {
      i3 = getHighOrderbits(paramArrayOfByte[11]);
      i3 += getLowOrderbits(paramArrayOfByte[12]);
      str = ZONEIDMAP.getRegion(i3);
    }
    else
    {
      i3 = paramArrayOfByte[11] - OFFSET_HOUR;
      int i4 = paramArrayOfByte[12] - OFFSET_MINUTE;
      str = i3 + ":";
      if (i4 == 0)
        str = str + "00";
      else {
        str = str + "" + i4;
      }
    }
    return toString(i, j, k, m, n, i1, i2, str);
  }

  public static final String toString(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, String paramString)
  {
    String str1 = "" + paramInt1 + "-" + toStr(paramInt2) + "-" + toStr(paramInt3) + " " + toStr(paramInt4) + ":" + toStr(paramInt5) + ":" + toStr(paramInt6);

    if (paramInt7 >= 0)
    {
      String str2 = String.format("%09d", new Object[] { Integer.valueOf(paramInt7) });

      char[] arrayOfChar = str2.toCharArray();
      int i = arrayOfChar.length;

      while ((i > 1) && (arrayOfChar[(i - 1)] == '0')) {
        i--;
      }
      str2 = str2.substring(0, i);

      str1 = str1 + "." + str2;
    }

    if (paramString != null) {
      str1 = str1 + " " + paramString;
    }
    return str1;
  }

  private static final String toStr(int paramInt) {
    return paramInt < 10 ? "0" + paramInt : Integer.toString(paramInt);
  }

  public Timestamp timestampValue(Connection paramConnection)
    throws SQLException
  {
    if (((oracle.jdbc.OracleConnection)paramConnection).physicalConnectionWithin().getTimestamptzInGmt())
    {
      return toTimestamp2(paramConnection, getBytes());
    }

    return toTimestamp(paramConnection, getBytes());
  }

  public byte[] toBytes()
  {
    return getBytes();
  }

  public static byte[] toBytes(Connection paramConnection, java.sql.Date paramDate)
    throws SQLException
  {
    if (paramDate == null) {
      return null;
    }
    byte[] arrayOfByte = new byte[13];

    String str1 = ((oracle.jdbc.OracleConnection)paramConnection).getSessionTimeZone();
    Calendar localCalendar;
    if (str1 == null)
      localCalendar = Calendar.getInstance();
    else {
      localCalendar = Calendar.getInstance(TimeZone.getTimeZone(str1));
    }
    localCalendar.setTime(paramDate);
    int j;
    if (localCalendar.getTimeZone().inDaylightTime(paramDate))
      j = 1;
    else {
      j = 0;
    }
    localCalendar.set(11, 0);
    localCalendar.set(12, 0);
    localCalendar.set(13, 0);
    int i;
    if (localCalendar.getTimeZone().getID() == "Custom")
    {
      i = localCalendar.getTimeZone().getRawOffset();
      arrayOfByte[11] = ((byte)(i / HOUR_MILLISECOND + OFFSET_HOUR));
      arrayOfByte[12] = ((byte)(i % HOUR_MILLISECOND / MINUTE_MILLISECOND + OFFSET_MINUTE));
    }
    else
    {
      String str2 = localCalendar.getTimeZone().getID();

      int m = ZONEIDMAP.getID(str2);

      if (!ZONEIDMAP.isValidID(m))
      {
        if (localCalendar.getTimeZone().useDaylightTime()) {
          throw new SQLException("Timezone not supported");
        }

        i = localCalendar.getTimeZone().getRawOffset();
        arrayOfByte[11] = ((byte)(i / HOUR_MILLISECOND + OFFSET_HOUR));
        arrayOfByte[12] = ((byte)(i % HOUR_MILLISECOND / MINUTE_MILLISECOND + OFFSET_MINUTE));
      }
      else
      {
        TIMEZONETAB localTIMEZONETAB = getTIMEZONETAB(paramConnection);
        if (localTIMEZONETAB.checkID(m)) {
          localTIMEZONETAB.updateTable(paramConnection, m);
        }

        OffsetDST localOffsetDST = new OffsetDST();

        int n = localTIMEZONETAB.getLocalOffset(localCalendar, m, localOffsetDST);
        i = localOffsetDST.getOFFSET();

        if ((j != 0) && (n == 1))
        {
          if (localOffsetDST.getDSTFLAG() == 0)
            i += HOUR_MILLISECOND;
          else {
            throw new SQLException();
          }
        }

        int i1 = ZONEIDMAP.getID(str2);

        arrayOfByte[11] = ((byte)setHighOrderbits(i1));
        byte[] tmp351_348 = arrayOfByte; tmp351_348[11] = ((byte)(tmp351_348[11] | REGIONIDBIT));
        arrayOfByte[12] = ((byte)setLowOrderbits(i1));
      }

    }

    localCalendar.add(10, -(i / HOUR_MILLISECOND));
    localCalendar.add(12, -(i % HOUR_MILLISECOND) / MINUTE_MILLISECOND);

    int k = localCalendar.get(1);
    if ((k < -4712) || (k > 9999))
    {
      SQLException localSQLException = DatabaseError.createSqlException(null, 268);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    arrayOfByte[0] = ((byte)(localCalendar.get(1) / 100 + 100));
    arrayOfByte[1] = ((byte)(localCalendar.get(1) % 100 + 100));
    arrayOfByte[2] = ((byte)(localCalendar.get(2) + 1));
    arrayOfByte[3] = ((byte)localCalendar.get(5));
    arrayOfByte[4] = ((byte)(localCalendar.get(11) + 1));
    arrayOfByte[5] = ((byte)(localCalendar.get(12) + 1));
    arrayOfByte[6] = ((byte)(localCalendar.get(13) + 1));
    arrayOfByte[7] = 0;
    arrayOfByte[8] = 0;
    arrayOfByte[9] = 0;
    arrayOfByte[10] = 0;

    return arrayOfByte;
  }

  public static byte[] toBytes(Connection paramConnection, java.sql.Date paramDate, Calendar paramCalendar)
    throws SQLException
  {
    if (paramDate == null) {
      return null;
    }

    byte[] arrayOfByte = new byte[13];

    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTime(paramDate);
    int j;
    if (paramCalendar.getTimeZone().inDaylightTime(paramDate))
      j = 1;
    else {
      j = 0;
    }
    localCalendar.set(11, 0);
    localCalendar.set(12, 0);
    localCalendar.set(13, 0);
    int i;
    if (paramCalendar.getTimeZone().getID() == "Custom")
    {
      i = paramCalendar.getTimeZone().getRawOffset();
      arrayOfByte[11] = ((byte)(i / HOUR_MILLISECOND + OFFSET_HOUR));
      arrayOfByte[12] = ((byte)(i % HOUR_MILLISECOND / MINUTE_MILLISECOND + OFFSET_MINUTE));
    }
    else
    {
      String str = paramCalendar.getTimeZone().getID();

      int m = ZONEIDMAP.getID(str);

      if (!ZONEIDMAP.isValidID(m))
      {
        if (paramCalendar.getTimeZone().useDaylightTime()) {
          throw new SQLException("Timezone not supported");
        }

        i = paramCalendar.getTimeZone().getRawOffset();
        arrayOfByte[11] = ((byte)(i / HOUR_MILLISECOND + OFFSET_HOUR));
        arrayOfByte[12] = ((byte)(i % HOUR_MILLISECOND / MINUTE_MILLISECOND + OFFSET_MINUTE));
      }
      else
      {
        TIMEZONETAB localTIMEZONETAB = getTIMEZONETAB(paramConnection);
        if (localTIMEZONETAB.checkID(m)) {
          localTIMEZONETAB.updateTable(paramConnection, m);
        }

        OffsetDST localOffsetDST = new OffsetDST();

        int n = localTIMEZONETAB.getLocalOffset(localCalendar, m, localOffsetDST);
        i = localOffsetDST.getOFFSET();

        if ((j != 0) && (n == 1))
        {
          if (localOffsetDST.getDSTFLAG() == 0)
            i += HOUR_MILLISECOND;
          else {
            throw new SQLException();
          }
        }

        arrayOfByte[11] = ((byte)setHighOrderbits(ZONEIDMAP.getID(str)));
        byte[] tmp319_315 = arrayOfByte; tmp319_315[11] = ((byte)(tmp319_315[11] | REGIONIDBIT));
        arrayOfByte[12] = ((byte)setLowOrderbits(ZONEIDMAP.getID(str)));
      }

    }

    localCalendar.add(10, -(i / HOUR_MILLISECOND));
    localCalendar.add(12, -(i % HOUR_MILLISECOND) / MINUTE_MILLISECOND);

    int k = localCalendar.get(1);
    if ((k < -4712) || (k > 9999))
    {
      SQLException localSQLException = DatabaseError.createSqlException(null, 268);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    arrayOfByte[0] = ((byte)(localCalendar.get(1) / 100 + 100));
    arrayOfByte[1] = ((byte)(localCalendar.get(1) % 100 + 100));
    arrayOfByte[2] = ((byte)(localCalendar.get(2) + 1));
    arrayOfByte[3] = ((byte)localCalendar.get(5));
    arrayOfByte[4] = ((byte)(localCalendar.get(11) + 1));
    arrayOfByte[5] = ((byte)(localCalendar.get(12) + 1));
    arrayOfByte[6] = ((byte)(localCalendar.get(13) + 1));
    arrayOfByte[7] = 0;
    arrayOfByte[8] = 0;
    arrayOfByte[9] = 0;
    arrayOfByte[10] = 0;

    return arrayOfByte;
  }

  public static byte[] toBytes(Connection paramConnection, Time paramTime)
    throws SQLException
  {
    if (paramTime == null) {
      return null;
    }

    byte[] arrayOfByte = new byte[13];

    String str1 = ((oracle.jdbc.OracleConnection)paramConnection).getSessionTimeZone();
    Calendar localCalendar;
    if (str1 == null)
      localCalendar = Calendar.getInstance();
    else
      localCalendar = Calendar.getInstance(TimeZone.getTimeZone(str1));
    localCalendar.setTime(paramTime);
    int i;
    if (localCalendar.getTimeZone().inDaylightTime(paramTime))
      i = 1;
    else {
      i = 0;
    }
    localCalendar.set(1, 1900);
    localCalendar.set(2, 0);
    localCalendar.set(5, 1);
    int j;
    if (localCalendar.getTimeZone().getID() == "Custom")
    {
      j = localCalendar.getTimeZone().getRawOffset();
      arrayOfByte[11] = ((byte)(j / HOUR_MILLISECOND + OFFSET_HOUR));
      arrayOfByte[12] = ((byte)(j % HOUR_MILLISECOND / MINUTE_MILLISECOND + OFFSET_MINUTE));
    }
    else
    {
      String str2 = localCalendar.getTimeZone().getID();

      int m = ZONEIDMAP.getID(str2);

      if (!ZONEIDMAP.isValidID(m))
      {
        if (localCalendar.getTimeZone().useDaylightTime()) {
          throw new SQLException("Timezone not supported");
        }

        j = localCalendar.getTimeZone().getRawOffset();
        arrayOfByte[11] = ((byte)(j / HOUR_MILLISECOND + OFFSET_HOUR));
        arrayOfByte[12] = ((byte)(j % HOUR_MILLISECOND / MINUTE_MILLISECOND + OFFSET_MINUTE));
      }
      else
      {
        TIMEZONETAB localTIMEZONETAB = getTIMEZONETAB(paramConnection);
        if (localTIMEZONETAB.checkID(m)) {
          localTIMEZONETAB.updateTable(paramConnection, m);
        }

        OffsetDST localOffsetDST = new OffsetDST();

        int n = localTIMEZONETAB.getLocalOffset(localCalendar, m, localOffsetDST);

        j = localOffsetDST.getOFFSET();

        arrayOfByte[11] = ((byte)setHighOrderbits(ZONEIDMAP.getID(str2)));
        byte[] tmp309_305 = arrayOfByte; tmp309_305[11] = ((byte)(tmp309_305[11] | REGIONIDBIT));
        arrayOfByte[12] = ((byte)setLowOrderbits(ZONEIDMAP.getID(str2)));
      }

    }

    localCalendar.add(10, -(j / HOUR_MILLISECOND));
    localCalendar.add(12, -(j % HOUR_MILLISECOND) / MINUTE_MILLISECOND);

    int k = localCalendar.get(1);
    if ((k < -4712) || (k > 9999))
    {
      SQLException localSQLException = DatabaseError.createSqlException(null, 268);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    arrayOfByte[0] = ((byte)(localCalendar.get(1) / 100 + 100));
    arrayOfByte[1] = ((byte)(localCalendar.get(1) % 100 + 100));
    arrayOfByte[2] = ((byte)(localCalendar.get(2) + 1));
    arrayOfByte[3] = ((byte)localCalendar.get(5));
    arrayOfByte[4] = ((byte)(localCalendar.get(11) + 1));
    arrayOfByte[5] = ((byte)(localCalendar.get(12) + 1));
    arrayOfByte[6] = ((byte)(localCalendar.get(13) + 1));
    arrayOfByte[7] = 0;
    arrayOfByte[8] = 0;
    arrayOfByte[9] = 0;
    arrayOfByte[10] = 0;

    return arrayOfByte;
  }

  public static byte[] toBytes(Connection paramConnection, Time paramTime, Calendar paramCalendar)
    throws SQLException
  {
    if (paramTime == null) {
      return null;
    }

    Calendar localCalendar = Calendar.getInstance();

    byte[] arrayOfByte = new byte[13];

    localCalendar.setTime(paramTime);
    int j;
    if (paramCalendar.getTimeZone().inDaylightTime(paramTime))
      j = 1;
    else {
      j = 0;
    }

    localCalendar.set(1, 1900);
    localCalendar.set(2, 0);
    localCalendar.set(5, 1);
    int i;
    if (paramCalendar.getTimeZone().getID() == "Custom")
    {
      i = paramCalendar.getTimeZone().getRawOffset();
      arrayOfByte[11] = ((byte)(i / HOUR_MILLISECOND + OFFSET_HOUR));
      arrayOfByte[12] = ((byte)(i % HOUR_MILLISECOND / MINUTE_MILLISECOND + OFFSET_MINUTE));
    }
    else
    {
      String str = paramCalendar.getTimeZone().getID();

      int m = ZONEIDMAP.getID(str);

      if (!ZONEIDMAP.isValidID(m))
      {
        if (paramCalendar.getTimeZone().useDaylightTime()) {
          throw new SQLException("Timezone not supported");
        }

        i = paramCalendar.getTimeZone().getRawOffset();
        arrayOfByte[11] = ((byte)(i / HOUR_MILLISECOND + OFFSET_HOUR));
        arrayOfByte[12] = ((byte)(i % HOUR_MILLISECOND / MINUTE_MILLISECOND + OFFSET_MINUTE));
      }
      else
      {
        TIMEZONETAB localTIMEZONETAB = getTIMEZONETAB(paramConnection);
        if (localTIMEZONETAB.checkID(m)) {
          localTIMEZONETAB.updateTable(paramConnection, m);
        }

        OffsetDST localOffsetDST = new OffsetDST();

        int n = localTIMEZONETAB.getLocalOffset(localCalendar, m, localOffsetDST);

        i = localOffsetDST.getOFFSET();

        if ((j != 0) && (n == 1))
        {
          if (localOffsetDST.getDSTFLAG() == 0)
            i += HOUR_MILLISECOND;
          else {
            throw new SQLException();
          }
        }
        arrayOfByte[11] = ((byte)setHighOrderbits(ZONEIDMAP.getID(str)));
        byte[] tmp321_317 = arrayOfByte; tmp321_317[11] = ((byte)(tmp321_317[11] | REGIONIDBIT));
        arrayOfByte[12] = ((byte)setLowOrderbits(ZONEIDMAP.getID(str)));
      }

    }

    localCalendar.add(11, -(i / HOUR_MILLISECOND));
    localCalendar.add(12, -(i % HOUR_MILLISECOND) / MINUTE_MILLISECOND);

    int k = localCalendar.get(1);
    if ((k < -4712) || (k > 9999))
    {
      SQLException localSQLException = DatabaseError.createSqlException(null, 268);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    arrayOfByte[0] = ((byte)(localCalendar.get(1) / 100 + 100));
    arrayOfByte[1] = ((byte)(localCalendar.get(1) % 100 + 100));
    arrayOfByte[2] = ((byte)(localCalendar.get(2) + 1));
    arrayOfByte[3] = ((byte)localCalendar.get(5));
    arrayOfByte[4] = ((byte)(localCalendar.get(11) + 1));
    arrayOfByte[5] = ((byte)(localCalendar.get(12) + 1));
    arrayOfByte[6] = ((byte)(localCalendar.get(13) + 1));
    arrayOfByte[7] = 0;
    arrayOfByte[8] = 0;
    arrayOfByte[9] = 0;
    arrayOfByte[10] = 0;

    return arrayOfByte;
  }

  public static byte[] toBytes(Connection paramConnection, Timestamp paramTimestamp)
    throws SQLException
  {
    if (paramTimestamp == null) {
      return null;
    }
    byte[] arrayOfByte = new byte[13];

    long l = 0L;

    String str1 = ((oracle.jdbc.OracleConnection)paramConnection).getSessionTimeZone();
    int i3;
    if (str1 == null)
    {
      str1 = TimeZone.getDefault().getID();

      l = paramTimestamp.getTime();
    }
    else
    {
      int k = paramTimestamp.getNanos();

      Calendar localCalendar2 = Calendar.getInstance();
      localCalendar2.setTime(paramTimestamp);

      int n = localCalendar2.get(1);
      int i2 = localCalendar2.get(2) + 1;
      i3 = localCalendar2.get(5);
      int i4 = localCalendar2.get(11);
      int i5 = localCalendar2.get(12);
      int i6 = localCalendar2.get(13);
      double d = k / 1000000;

      String str2 = Integer.valueOf(n).toString() + "/" + Integer.valueOf(i2).toString() + "/" + Integer.valueOf(i3).toString() + " " + Integer.valueOf(i4).toString() + ":" + Integer.valueOf(i5).toString() + ":" + Integer.valueOf(i6).toString() + ":" + Double.valueOf(d).toString();

      TimeZone localTimeZone = TimeZone.getTimeZone(str1);

      SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("y/M/d H:m:s:S");
      localSimpleDateFormat.setTimeZone(localTimeZone);

      java.util.Date localDate = null;
      try
      {
        localDate = localSimpleDateFormat.parse(str2);
      }
      catch (ParseException localParseException)
      {
        throw new SQLException(localParseException.getMessage());
      }

      l = localDate.getTime();
    }

    Calendar localCalendar1 = Calendar.getInstance(TimeZone.getTimeZone(str1));
    int j;
    if (localCalendar1.getTimeZone().inDaylightTime(paramTimestamp))
      j = 1;
    else {
      j = 0;
    }
    localCalendar1.setTime(new Timestamp(l));
    int i;
    Object localObject2;
    if (localCalendar1.getTimeZone().getID() == "Custom")
    {
      i = localCalendar1.getTimeZone().getRawOffset();
      arrayOfByte[11] = ((byte)(i / HOUR_MILLISECOND + OFFSET_HOUR));
      arrayOfByte[12] = ((byte)(i % HOUR_MILLISECOND / MINUTE_MILLISECOND + OFFSET_MINUTE));
    }
    else
    {
      localObject1 = localCalendar1.getTimeZone().getID();

      int m = ZONEIDMAP.getID((String)localObject1);

      if (!ZONEIDMAP.isValidID(m))
      {
        if (localCalendar1.getTimeZone().useDaylightTime()) {
          throw new SQLException("Timezone not supported");
        }

        i = localCalendar1.getTimeZone().getRawOffset();
        arrayOfByte[11] = ((byte)(i / HOUR_MILLISECOND + OFFSET_HOUR));
        arrayOfByte[12] = ((byte)(i % HOUR_MILLISECOND / MINUTE_MILLISECOND + OFFSET_MINUTE));
      }
      else
      {
        TIMEZONETAB localTIMEZONETAB = getTIMEZONETAB(paramConnection);
        if (localTIMEZONETAB.checkID(m)) {
          localTIMEZONETAB.updateTable(paramConnection, m);
        }

        localObject2 = new OffsetDST();

        i3 = localTIMEZONETAB.getLocalOffset(localCalendar1, m, (OffsetDST)localObject2);

        i = ((OffsetDST)localObject2).getOFFSET();

        if ((j != 0) && (i3 == 1))
        {
          if (((OffsetDST)localObject2).getDSTFLAG() == 0)
            i += HOUR_MILLISECOND;
          else {
            throw new SQLException();
          }

        }

        arrayOfByte[11] = ((byte)setHighOrderbits(ZONEIDMAP.getID((String)localObject1)));
        byte[] tmp602_599 = arrayOfByte; tmp602_599[11] = ((byte)(tmp602_599[11] | REGIONIDBIT));
        arrayOfByte[12] = ((byte)setLowOrderbits(ZONEIDMAP.getID((String)localObject1)));
      }

    }

    Object localObject1 = TimeZone.getTimeZone("GMT");
    Calendar localCalendar3 = Calendar.getInstance((TimeZone)localObject1, Locale.US);

    localCalendar3.set(1, localCalendar1.get(1));
    localCalendar3.set(2, localCalendar1.get(2));
    localCalendar3.set(5, localCalendar1.get(5));
    localCalendar3.set(11, localCalendar1.get(11));
    localCalendar3.set(12, localCalendar1.get(12));
    localCalendar3.set(13, localCalendar1.get(13));

    localCalendar3.add(14, -1 * i);

    int i1 = localCalendar3.get(1);
    if ((i1 < -4712) || (i1 > 9999))
    {
      localObject2 = DatabaseError.createSqlException(null, 268);
      ((SQLException)localObject2).fillInStackTrace();
      throw ((Throwable)localObject2);
    }

    arrayOfByte[0] = ((byte)(localCalendar3.get(1) / 100 + 100));
    arrayOfByte[1] = ((byte)(localCalendar3.get(1) % 100 + 100));
    arrayOfByte[2] = ((byte)(localCalendar3.get(2) + 1));
    arrayOfByte[3] = ((byte)localCalendar3.get(5));
    arrayOfByte[4] = ((byte)(localCalendar3.get(11) + 1));
    arrayOfByte[5] = ((byte)(localCalendar3.get(12) + 1));
    arrayOfByte[6] = ((byte)(localCalendar3.get(13) + 1));

    arrayOfByte[7] = ((byte)(paramTimestamp.getNanos() >> 24));
    arrayOfByte[8] = ((byte)(paramTimestamp.getNanos() >> 16 & 0xFF));
    arrayOfByte[9] = ((byte)(paramTimestamp.getNanos() >> 8 & 0xFF));
    arrayOfByte[10] = ((byte)(paramTimestamp.getNanos() & 0xFF));

    return arrayOfByte;
  }

  public static byte[] toBytes(Connection paramConnection, Timestamp paramTimestamp, Calendar paramCalendar)
    throws SQLException
  {
    if (paramTimestamp == null) {
      return null;
    }

    byte[] arrayOfByte = new byte[13];
    Calendar localCalendar1;
    if (paramCalendar == null)
      localCalendar1 = Calendar.getInstance();
    else {
      localCalendar1 = Calendar.getInstance(paramCalendar.getTimeZone());
    }
    localCalendar1.setTime(paramTimestamp);

    TimeZone localTimeZone = localCalendar1.getTimeZone();
    int j;
    if (paramCalendar.getTimeZone().inDaylightTime(paramTimestamp))
      j = 1;
    else
      j = 0;
    int i;
    Object localObject2;
    if (paramCalendar.getTimeZone().getID() == "Custom")
    {
      i = paramCalendar.getTimeZone().getRawOffset();
      arrayOfByte[11] = ((byte)(i / HOUR_MILLISECOND + OFFSET_HOUR));
      arrayOfByte[12] = ((byte)(i % HOUR_MILLISECOND / MINUTE_MILLISECOND + OFFSET_MINUTE));
    }
    else
    {
      localObject1 = paramCalendar.getTimeZone().getID();

      int k = ZONEIDMAP.getID((String)localObject1);

      if (!ZONEIDMAP.isValidID(k))
      {
        if (paramCalendar.getTimeZone().useDaylightTime()) {
          throw new SQLException("Timezone not supported");
        }

        i = paramCalendar.getTimeZone().getRawOffset();
        arrayOfByte[11] = ((byte)(i / HOUR_MILLISECOND + OFFSET_HOUR));
        arrayOfByte[12] = ((byte)(i % HOUR_MILLISECOND / MINUTE_MILLISECOND + OFFSET_MINUTE));
      }
      else
      {
        TIMEZONETAB localTIMEZONETAB = getTIMEZONETAB(paramConnection);
        if (localTIMEZONETAB.checkID(k)) {
          localTIMEZONETAB.updateTable(paramConnection, k);
        }

        localObject2 = new OffsetDST();

        int n = localTIMEZONETAB.getLocalOffset(localCalendar1, k, (OffsetDST)localObject2);

        i = ((OffsetDST)localObject2).getOFFSET();

        if ((j != 0) && (n == 1))
        {
          if (((OffsetDST)localObject2).getDSTFLAG() == 0)
            i += HOUR_MILLISECOND;
          else {
            throw new SQLException();
          }
        }
        arrayOfByte[11] = ((byte)setHighOrderbits(ZONEIDMAP.getID((String)localObject1)));
        byte[] tmp326_322 = arrayOfByte; tmp326_322[11] = ((byte)(tmp326_322[11] | REGIONIDBIT));
        arrayOfByte[12] = ((byte)setLowOrderbits(ZONEIDMAP.getID((String)localObject1)));
      }

    }

    Object localObject1 = TimeZone.getTimeZone("GMT");
    Calendar localCalendar2 = Calendar.getInstance((TimeZone)localObject1, Locale.US);

    localCalendar2.set(1, localCalendar1.get(1));
    localCalendar2.set(2, localCalendar1.get(2));
    localCalendar2.set(5, localCalendar1.get(5));
    localCalendar2.set(11, localCalendar1.get(11));
    localCalendar2.set(12, localCalendar1.get(12));
    localCalendar2.set(13, localCalendar1.get(13));

    localCalendar2.add(14, -1 * i);

    int m = localCalendar2.get(1);
    if ((m < -4712) || (m > 9999))
    {
      localObject2 = DatabaseError.createSqlException(null, 268);
      ((SQLException)localObject2).fillInStackTrace();
      throw ((Throwable)localObject2);
    }

    arrayOfByte[0] = ((byte)(localCalendar2.get(1) / 100 + 100));
    arrayOfByte[1] = ((byte)(localCalendar2.get(1) % 100 + 100));
    arrayOfByte[2] = ((byte)(localCalendar2.get(2) + 1));
    arrayOfByte[3] = ((byte)localCalendar2.get(5));
    arrayOfByte[4] = ((byte)(localCalendar2.get(11) + 1));
    arrayOfByte[5] = ((byte)(localCalendar2.get(12) + 1));
    arrayOfByte[6] = ((byte)(localCalendar2.get(13) + 1));

    arrayOfByte[7] = ((byte)(paramTimestamp.getNanos() >> 24));
    arrayOfByte[8] = ((byte)(paramTimestamp.getNanos() >> 16 & 0xFF));
    arrayOfByte[9] = ((byte)(paramTimestamp.getNanos() >> 8 & 0xFF));
    arrayOfByte[10] = ((byte)(paramTimestamp.getNanos() & 0xFF));

    return arrayOfByte;
  }

  public static byte[] toBytes(Connection paramConnection, DATE paramDATE)
    throws SQLException
  {
    if (paramDATE == null) {
      return null;
    }
    byte[] arrayOfByte = new byte[13];

    Calendar localCalendar = Calendar.getInstance();

    localCalendar.setTime(DATE.toDate(paramDATE.toBytes()));
    int j;
    if (localCalendar.getTimeZone().inDaylightTime(DATE.toDate(paramDATE.toBytes())))
      j = 1;
    else
      j = 0;
    int i;
    if (localCalendar.getTimeZone().getID() == "Custom")
    {
      i = localCalendar.getTimeZone().getRawOffset();
      arrayOfByte[11] = ((byte)(i / HOUR_MILLISECOND + OFFSET_HOUR));
      arrayOfByte[12] = ((byte)(i % HOUR_MILLISECOND / MINUTE_MILLISECOND + OFFSET_MINUTE));
    }
    else
    {
      String str = localCalendar.getTimeZone().getID();

      int k = ZONEIDMAP.getID(str);

      if (!ZONEIDMAP.isValidID(k))
      {
        if (localCalendar.getTimeZone().useDaylightTime()) {
          throw new SQLException("Timezone not supported");
        }

        i = localCalendar.getTimeZone().getRawOffset();
        arrayOfByte[11] = ((byte)(i / HOUR_MILLISECOND + OFFSET_HOUR));
        arrayOfByte[12] = ((byte)(i % HOUR_MILLISECOND / MINUTE_MILLISECOND + OFFSET_MINUTE));
      }
      else
      {
        TIMEZONETAB localTIMEZONETAB = getTIMEZONETAB(paramConnection);
        if (localTIMEZONETAB.checkID(k)) {
          localTIMEZONETAB.updateTable(paramConnection, k);
        }

        OffsetDST localOffsetDST = new OffsetDST();

        int m = localTIMEZONETAB.getLocalOffset(localCalendar, k, localOffsetDST);

        i = localOffsetDST.getOFFSET();

        if ((j != 0) && (m == 1))
        {
          if (localOffsetDST.getDSTFLAG() == 0)
            i += HOUR_MILLISECOND;
          else {
            throw new SQLException();
          }

        }

        arrayOfByte[11] = ((byte)setHighOrderbits(ZONEIDMAP.getID(str)));
        byte[] tmp306_303 = arrayOfByte; tmp306_303[11] = ((byte)(tmp306_303[11] | REGIONIDBIT));
        arrayOfByte[12] = ((byte)setLowOrderbits(ZONEIDMAP.getID(str)));
      }

    }

    localCalendar.add(10, -(i / HOUR_MILLISECOND));
    localCalendar.add(12, -(i % HOUR_MILLISECOND) / MINUTE_MILLISECOND);

    arrayOfByte[0] = ((byte)(localCalendar.get(1) / 100 + 100));
    arrayOfByte[1] = ((byte)(localCalendar.get(1) % 100 + 100));
    arrayOfByte[2] = ((byte)(localCalendar.get(2) + 1));
    arrayOfByte[3] = ((byte)localCalendar.get(5));
    arrayOfByte[4] = ((byte)(localCalendar.get(11) + 1));
    arrayOfByte[5] = ((byte)(localCalendar.get(12) + 1));
    arrayOfByte[6] = ((byte)(localCalendar.get(13) + 1));
    arrayOfByte[7] = 0;
    arrayOfByte[8] = 0;
    arrayOfByte[9] = 0;
    arrayOfByte[10] = 0;

    return arrayOfByte;
  }

  public static byte[] toBytes(Connection paramConnection, String paramString)
    throws SQLException
  {
    return toBytes(paramConnection, Timestamp.valueOf(paramString));
  }

  public static byte[] toBytes(Connection paramConnection, String paramString, Calendar paramCalendar)
    throws SQLException
  {
    Calendar localCalendar1 = Calendar.getInstance();

    Timestamp localTimestamp = Timestamp.valueOf(paramString);
    localCalendar1.setTime(localTimestamp);
    Calendar localCalendar2;
    if (paramCalendar == null)
      localCalendar2 = Calendar.getInstance();
    else {
      localCalendar2 = Calendar.getInstance(paramCalendar.getTimeZone());
    }
    localCalendar2.set(1, localCalendar1.get(1));
    localCalendar2.set(2, localCalendar1.get(2));
    localCalendar2.set(5, localCalendar1.get(5));
    localCalendar2.set(11, localCalendar1.get(11));
    localCalendar2.set(12, localCalendar1.get(12));
    localCalendar2.set(13, localCalendar1.get(13));
    localCalendar2.set(14, localCalendar1.get(14));

    int i = localTimestamp.getNanos();
    localTimestamp = new Timestamp(localCalendar2.getTime().getTime());
    localTimestamp.setNanos(i);

    return toBytes(paramConnection, localTimestamp, paramCalendar);
  }

  public String stringValue(Connection paramConnection)
    throws SQLException
  {
    return toString(paramConnection, getBytes());
  }

  public java.sql.Date dateValue(Connection paramConnection)
    throws SQLException
  {
    if (((oracle.jdbc.OracleConnection)paramConnection).physicalConnectionWithin().getTimestamptzInGmt())
    {
      return toDate2(paramConnection, getBytes());
    }

    return toDate(paramConnection, getBytes());
  }

  public Time timeValue(Connection paramConnection)
    throws SQLException
  {
    return toTime(paramConnection, getBytes());
  }

  private static byte[] initTimestamptz()
  {
    byte[] arrayOfByte = new byte[13];
    Calendar localCalendar = Calendar.getInstance();

    arrayOfByte[0] = 119;
    arrayOfByte[1] = -86;
    arrayOfByte[2] = 1;
    arrayOfByte[3] = 1;
    arrayOfByte[4] = 1;
    arrayOfByte[5] = 1;
    arrayOfByte[6] = 1;
    arrayOfByte[7] = 0;
    arrayOfByte[8] = 0;
    arrayOfByte[9] = 0;
    arrayOfByte[10] = 0;

    String str = localCalendar.getTimeZone().getID();

    arrayOfByte[11] = ((byte)setHighOrderbits(ZONEIDMAP.getID(str)));
    arrayOfByte[11] = ((byte)(arrayOfByte[11] | REGIONIDBIT));
    arrayOfByte[12] = ((byte)setLowOrderbits(ZONEIDMAP.getID(str)));

    return arrayOfByte;
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

  private static int setHighOrderbits(int paramInt)
  {
    return (paramInt & 0x1FC0) >> 6;
  }

  private static int setLowOrderbits(int paramInt)
  {
    return (paramInt & 0x3F) << 2;
  }

  private static int getHighOrderbits(int paramInt)
  {
    return (paramInt & 0x7F) << 6;
  }

  private static int getLowOrderbits(int paramInt)
  {
    return (paramInt & 0xFC) >> 2;
  }

  private static int getJavaYear(int paramInt1, int paramInt2)
  {
    return (paramInt1 - 100) * 100 + (paramInt2 - 100);
  }

  static TIMEZONETAB getTIMEZONETAB(Connection paramConnection) throws SQLException
  {
    oracle.jdbc.internal.OracleConnection localOracleConnection = ((oracle.jdbc.OracleConnection)paramConnection).physicalConnectionWithin();
    return localOracleConnection.getTIMEZONETAB();
  }
}