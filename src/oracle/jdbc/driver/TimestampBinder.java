package oracle.jdbc.driver;

import java.sql.SQLException;
import java.sql.Timestamp;

class TimestampBinder extends DateCommonBinder
{
  Binder theTimestampCopyingBinder = OraclePreparedStatementReadOnly.theStaticTimestampCopyingBinder;

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  static void init(Binder paramBinder)
  {
    paramBinder.type = 180;
    paramBinder.bytelen = 11;
  }

  TimestampBinder()
  {
    init(this);
  }

  Binder copyingBinder()
  {
    return this.theTimestampCopyingBinder;
  }

  void bind(OraclePreparedStatement paramOraclePreparedStatement, int paramInt1, int paramInt2, int paramInt3, byte[] paramArrayOfByte, char[] paramArrayOfChar, short[] paramArrayOfShort, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, boolean paramBoolean)
    throws SQLException
  {
    Timestamp[] arrayOfTimestamp = paramOraclePreparedStatement.parameterTimestamp[paramInt3];
    Timestamp localTimestamp = arrayOfTimestamp[paramInt1];

    if (paramBoolean) {
      arrayOfTimestamp[paramInt1] = null;
    }
    if (localTimestamp == null)
    {
      paramArrayOfShort[paramInt9] = -1;
    }
    else
    {
      paramArrayOfShort[paramInt9] = 0;

      setOracleHMS(setOracleCYMD(localTimestamp.getTime(), paramArrayOfByte, paramInt6, paramOraclePreparedStatement), paramArrayOfByte, paramInt6);

      int i = localTimestamp.getNanos();

      if (i != 0)
      {
        setOracleNanos(i, paramArrayOfByte, paramInt6);

        paramArrayOfShort[paramInt8] = ((short)paramInt4);
      }
      else
      {
        paramArrayOfShort[paramInt8] = 7;
      }
    }
  }
}