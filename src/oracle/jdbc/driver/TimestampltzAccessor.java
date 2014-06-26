package oracle.jdbc.driver;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;
import java.util.TimeZone;
import oracle.sql.DATE;
import oracle.sql.Datum;
import oracle.sql.OffsetDST;
import oracle.sql.TIMESTAMP;
import oracle.sql.TIMESTAMPLTZ;
import oracle.sql.TIMESTAMPTZ;
import oracle.sql.TIMEZONETAB;
import oracle.sql.ZONEIDMAP;

class TimestampltzAccessor extends DateTimeCommonAccessor
{
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  TimestampltzAccessor(OracleStatement paramOracleStatement, int paramInt1, short paramShort, int paramInt2, boolean paramBoolean)
    throws SQLException
  {
    init(paramOracleStatement, 231, 231, paramShort, paramBoolean);
    initForDataAccess(paramInt2, paramInt1, null);
  }

  TimestampltzAccessor(OracleStatement paramOracleStatement, int paramInt1, boolean paramBoolean, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, short paramShort)
    throws SQLException
  {
    init(paramOracleStatement, 231, 231, paramShort, false);
    initForDescribe(231, paramInt1, paramBoolean, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramShort, null);

    initForDataAccess(0, paramInt1, null);
  }

  void initForDataAccess(int paramInt1, int paramInt2, String paramString)
    throws SQLException
  {
    if (paramInt1 != 0) {
      this.externalType = paramInt1;
    }
    this.internalTypeMaxLength = 11;

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
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 21);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] == -1) {
      return null;
    }

    Object localObject1 = this.statement.connection.getDbTzCalendar();

    String str1 = this.statement.connection.getSessionTimeZone();

    if (str1 == null)
    {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 198);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject2 = TimeZone.getTimeZone(str1);

    Calendar localCalendar = Calendar.getInstance((TimeZone)localObject2);

    int i = this.columnIndex + this.byteLength * paramInt;
    int j = this.rowSpaceIndicator[(this.lengthIndex + paramInt)];

    int k = ((this.rowSpaceByte[(0 + i)] & 0xFF) - 100) * 100 + (this.rowSpaceByte[(1 + i)] & 0xFF) - 100;

    ((Calendar)localObject1).set(1, k);
    ((Calendar)localObject1).set(2, oracleMonth(i));
    ((Calendar)localObject1).set(5, oracleDay(i));
    ((Calendar)localObject1).set(11, oracleHour(i));
    ((Calendar)localObject1).set(12, oracleMin(i));
    ((Calendar)localObject1).set(13, oracleSec(i));
    ((Calendar)localObject1).set(14, 0);

    TimeZoneAdjust((Calendar)localObject1, localCalendar);

    k = localCalendar.get(1);

    int m = localCalendar.get(2) + 1;
    int n = localCalendar.get(5);
    int i1 = localCalendar.get(11);
    int i2 = localCalendar.get(12);
    int i3 = localCalendar.get(13);
    int i4 = 0;
    boolean bool = i1 < 12;
    String str2 = localCalendar.getTimeZone().getID();
    if ((str2.length() > 3) && (str2.startsWith("GMT"))) {
      str2 = str2.substring(3);
    }
    if (j == 11)
    {
      i4 = oracleNanos(i);
    }

    return toText(k, m, n, i1, i2, i3, i4, bool, str2);
  }

  Date getDate(int paramInt, Calendar paramCalendar)
    throws SQLException
  {
    return getDate(paramInt);
  }

  Date getDate(int paramInt)
    throws SQLException
  {
    if (this.rowSpaceIndicator == null)
    {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 21);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] == -1) {
      return null;
    }

    Object localObject1 = this.statement.connection.getDbTzCalendar();

    String str = this.statement.connection.getSessionTimeZone();

    if (str == null)
    {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 198);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject2 = TimeZone.getTimeZone(str);

    Calendar localCalendar = Calendar.getInstance((TimeZone)localObject2);

    int i = this.columnIndex + this.byteLength * paramInt;
    int j = ((this.rowSpaceByte[(0 + i)] & 0xFF) - 100) * 100 + (this.rowSpaceByte[(1 + i)] & 0xFF) - 100;

    ((Calendar)localObject1).set(1, j);
    ((Calendar)localObject1).set(2, oracleMonth(i));
    ((Calendar)localObject1).set(5, oracleDay(i));
    ((Calendar)localObject1).set(11, oracleHour(i));
    ((Calendar)localObject1).set(12, oracleMin(i));
    ((Calendar)localObject1).set(13, oracleSec(i));
    ((Calendar)localObject1).set(14, 0);

    long l = TimeZoneAdjustUTC((Calendar)localObject1, localCalendar);

    return new Date(l);
  }

  Time getTime(int paramInt, Calendar paramCalendar)
    throws SQLException
  {
    return getTime(paramInt);
  }

  Time getTime(int paramInt)
    throws SQLException
  {
    if (this.rowSpaceIndicator == null)
    {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 21);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] == -1) {
      return null;
    }

    Object localObject1 = this.statement.connection.getDbTzCalendar();

    String str = this.statement.connection.getSessionTimeZone();

    if (str == null)
    {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 198);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject2 = TimeZone.getTimeZone(str);

    Calendar localCalendar = Calendar.getInstance((TimeZone)localObject2);

    int i = this.columnIndex + this.byteLength * paramInt;
    int j = ((this.rowSpaceByte[(0 + i)] & 0xFF) - 100) * 100 + (this.rowSpaceByte[(1 + i)] & 0xFF) - 100;

    ((Calendar)localObject1).set(1, j);
    ((Calendar)localObject1).set(2, oracleMonth(i));
    ((Calendar)localObject1).set(5, oracleDay(i));
    ((Calendar)localObject1).set(11, oracleHour(i));
    ((Calendar)localObject1).set(12, oracleMin(i));
    ((Calendar)localObject1).set(13, oracleSec(i));
    ((Calendar)localObject1).set(14, 0);

    long l = TimeZoneAdjustUTC((Calendar)localObject1, localCalendar);

    return new Time(l);
  }

  Timestamp getTimestamp(int paramInt, Calendar paramCalendar)
    throws SQLException
  {
    return getTimestamp(paramInt);
  }

  Timestamp getTimestamp(int paramInt)
    throws SQLException
  {
    if (this.rowSpaceIndicator == null)
    {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 21);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] == -1) {
      return null;
    }

    Object localObject1 = this.statement.connection.getDbTzCalendar();

    String str = this.statement.connection.getSessionTimeZone();

    if (str == null)
    {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 198);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject2 = TimeZone.getTimeZone(str);
    Calendar localCalendar = Calendar.getInstance((TimeZone)localObject2);

    int i = this.columnIndex + this.byteLength * paramInt;
    int j = this.rowSpaceIndicator[(this.lengthIndex + paramInt)];

    int k = ((this.rowSpaceByte[(0 + i)] & 0xFF) - 100) * 100 + (this.rowSpaceByte[(1 + i)] & 0xFF) - 100;

    ((Calendar)localObject1).set(1, k);
    ((Calendar)localObject1).set(2, oracleMonth(i));
    ((Calendar)localObject1).set(5, oracleDay(i));
    ((Calendar)localObject1).set(11, oracleHour(i));
    ((Calendar)localObject1).set(12, oracleMin(i));
    ((Calendar)localObject1).set(13, oracleSec(i));
    ((Calendar)localObject1).set(14, 0);

    long l = TimeZoneAdjustUTC((Calendar)localObject1, localCalendar);

    Timestamp localTimestamp = new Timestamp(l);

    if (j == 11)
    {
      localTimestamp.setNanos(oracleNanos(i));
    }

    return localTimestamp;
  }

  Object getObject(int paramInt)
    throws SQLException
  {
    return getTIMESTAMPLTZ(paramInt);
  }

  Datum getOracleObject(int paramInt)
    throws SQLException
  {
    return getTIMESTAMPLTZ(paramInt);
  }

  Object getObject(int paramInt, Map paramMap)
    throws SQLException
  {
    return getTIMESTAMPLTZ(paramInt);
  }

  TIMESTAMPLTZ getTIMESTAMPLTZ(int paramInt)
    throws SQLException
  {
    TIMESTAMPLTZ localTIMESTAMPLTZ = null;

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

      localTIMESTAMPLTZ = new TIMESTAMPLTZ(arrayOfByte);
    }

    return localTIMESTAMPLTZ;
  }

  TIMESTAMPTZ getTIMESTAMPTZ(int paramInt)
    throws SQLException
  {
    TIMESTAMPTZ localTIMESTAMPTZ = null;

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

      localTIMESTAMPTZ = oracle.sql.TIMESTAMPLTZ.toTIMESTAMPTZ(this.statement.connection, arrayOfByte);
    }

    return localTIMESTAMPTZ;
  }

  TIMESTAMP getTIMESTAMP(int paramInt)
    throws SQLException
  {
    TIMESTAMPTZ localTIMESTAMPTZ = getTIMESTAMPTZ(paramInt);
    return oracle.sql.TIMESTAMPTZ.toTIMESTAMP(this.statement.connection, localTIMESTAMPTZ.getBytes());
  }

  DATE getDATE(int paramInt) throws SQLException
  {
    TIMESTAMPTZ localTIMESTAMPTZ = getTIMESTAMPTZ(paramInt);
    return oracle.sql.TIMESTAMPTZ.toDATE(this.statement.connection, localTIMESTAMPTZ.getBytes());
  }

  void TimeZoneAdjust(Calendar paramCalendar1, Calendar paramCalendar2)
    throws SQLException
  {
    String str1 = paramCalendar1.getTimeZone().getID();
    String str2 = paramCalendar2.getTimeZone().getID();

    if (!str2.equals(str1))
    {
      OffsetDST localOffsetDST = new OffsetDST();

      int k = getZoneOffset(paramCalendar1, localOffsetDST);

      int m = localOffsetDST.getOFFSET();

      paramCalendar1.add(11, -(m / 3600000));
      paramCalendar1.add(12, -(m % 3600000) / 60000);
      int i;
      if ((str2.equals("Custom")) || ((str2.startsWith("GMT")) && (str2.length() > 3)))
      {
        i = paramCalendar2.getTimeZone().getRawOffset();
      }
      else
      {
        int n = ZONEIDMAP.getID(str2);

        if (!ZONEIDMAP.isValidID(n))
        {
          SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 199);
          sqlexception.fillInStackTrace();
          throw sqlexception;
        }

        Object localObject = this.statement.connection.getTIMEZONETAB();
        if (((TIMEZONETAB)localObject).checkID(n))
        {
          ((TIMEZONETAB)localObject).updateTable(this.statement.connection, n);
        }

        Calendar localCalendar = this.statement.getGMTCalendar();

        localCalendar.set(1, paramCalendar1.get(1));
        localCalendar.set(2, paramCalendar1.get(2));
        localCalendar.set(5, paramCalendar1.get(5));
        localCalendar.set(11, paramCalendar1.get(11));
        localCalendar.set(12, paramCalendar1.get(12));
        localCalendar.set(13, paramCalendar1.get(13));
        localCalendar.set(14, paramCalendar1.get(14));

        i = ((TIMEZONETAB)localObject).getOffset(localCalendar, n);
      }

      paramCalendar1.add(11, i / 3600000);
      paramCalendar1.add(12, i % 3600000 / 60000);
    }

    if (((str2.equals("Custom")) && (str1.equals("Custom"))) || ((str2.startsWith("GMT")) && (str2.length() > 3) && (str1.startsWith("GMT")) && (str1.length() > 3)))
    {
      int j = paramCalendar1.getTimeZone().getRawOffset();
      int k = paramCalendar2.getTimeZone().getRawOffset();
      int m = 0;

      if (j != k)
      {
        m = j - k;
        m = m > 0 ? m : -m;
      }

      if (j > k) {
        m = -m;
      }
      paramCalendar1.add(11, m / 3600000);
      paramCalendar1.add(12, m % 3600000 / 60000);
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

  long TimeZoneAdjustUTC(Calendar paramCalendar1, Calendar paramCalendar2)
    throws SQLException
  {
    String str1 = paramCalendar1.getTimeZone().getID();
    String str2 = paramCalendar2.getTimeZone().getID();

    if (!str2.equals(str1))
    {
      OffsetDST localOffsetDST = new OffsetDST();

      int j = getZoneOffset(paramCalendar1, localOffsetDST);

      int k = localOffsetDST.getOFFSET();

      paramCalendar1.add(11, -(k / 3600000));
      paramCalendar1.add(12, -(k % 3600000) / 60000);
    }

    if (((str2.equals("Custom")) && (str1.equals("Custom"))) || ((str2.startsWith("GMT")) && (str2.length() > 3) && (str1.startsWith("GMT")) && (str1.length() > 3)))
    {
      int i = paramCalendar1.getTimeZone().getRawOffset();
      int j = paramCalendar2.getTimeZone().getRawOffset();

      if (i != j)
      {
        paramCalendar1.add(11, -(i / 3600000));
        paramCalendar1.add(12, -(i % 3600000) / 60000);
      }

    }

    int i = paramCalendar1.get(1);
    int j = paramCalendar1.get(2);
    int k = paramCalendar1.get(5);
    int m = paramCalendar1.get(11);
    int n = paramCalendar1.get(12);
    int i1 = paramCalendar1.get(13);
    int i2 = paramCalendar1.get(14);

    Calendar localCalendar = this.statement.getGMTCalendar();

    localCalendar.set(1, i);
    localCalendar.set(2, j);
    localCalendar.set(5, k);
    localCalendar.set(11, m);
    localCalendar.set(12, n);
    localCalendar.set(13, i1);
    localCalendar.set(14, i2);

    long l = localCalendar.getTimeInMillis();

    return l;
  }

  byte getZoneOffset(Calendar paramCalendar, OffsetDST paramOffsetDST)
    throws SQLException
  {
    byte b = 0;

    String str = paramCalendar.getTimeZone().getID();

    if ((str == "Custom") || ((str.startsWith("GMT")) && (str.length() > 3)))
    {
      paramOffsetDST.setOFFSET(paramCalendar.getTimeZone().getRawOffset());
    }
    else
    {
      int i = ZONEIDMAP.getID(str);

      if (!ZONEIDMAP.isValidID(i))
      {
        SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 199);
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }

      Object localObject = this.statement.connection.getTIMEZONETAB();
      if (((TIMEZONETAB)localObject).checkID(i)) {
        ((TIMEZONETAB)localObject).updateTable(this.statement.connection, i);
      }

      b = ((TIMEZONETAB)localObject).getLocalOffset(paramCalendar, i, paramOffsetDST);
    }

    return b;
  }
}