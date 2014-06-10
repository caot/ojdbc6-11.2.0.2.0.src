package oracle.jdbc.driver;

import java.sql.SQLException;
import java.sql.Time;

class TimeBinder extends DateCommonBinder
{
  Binder theTimeCopyingBinder = OraclePreparedStatementReadOnly.theStaticTimeCopyingBinder;

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  static void init(Binder paramBinder)
  {
    paramBinder.type = 12;
    paramBinder.bytelen = 7;
  }

  TimeBinder()
  {
    init(this);
  }

  Binder copyingBinder()
  {
    return this.theTimeCopyingBinder;
  }

  void bind(OraclePreparedStatement paramOraclePreparedStatement, int paramInt1, int paramInt2, int paramInt3, byte[] paramArrayOfByte, char[] paramArrayOfChar, short[] paramArrayOfShort, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, boolean paramBoolean)
    throws SQLException
  {
    Time[] arrayOfTime = paramOraclePreparedStatement.parameterTime[paramInt3];
    Time localTime = arrayOfTime[paramInt1];

    if (paramBoolean) {
      arrayOfTime[paramInt1] = null;
    }
    if (localTime == null)
    {
      paramArrayOfShort[paramInt9] = -1;
    }
    else
    {
      paramArrayOfShort[paramInt9] = 0;

      setOracleHMS(setOracleCYMD(localTime.getTime(), paramArrayOfByte, paramInt6, paramOraclePreparedStatement), paramArrayOfByte, paramInt6);

      if (paramOraclePreparedStatement.connection.use1900AsYearForTime) {
        paramArrayOfByte[(0 + paramInt6)] = 119;
        paramArrayOfByte[(1 + paramInt6)] = 100;
        paramArrayOfByte[(2 + paramInt6)] = 1;
        paramArrayOfByte[(3 + paramInt6)] = 1;
      }

      paramArrayOfShort[paramInt8] = ((short)paramInt4);
    }
  }
}