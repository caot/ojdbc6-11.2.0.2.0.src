package oracle.jdbc.driver;

import java.sql.SQLException;
import oracle.jdbc.internal.OracleConnection;

abstract class Binder
{
  short type;
  int bytelen;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  abstract Binder copyingBinder();

  abstract void bind(OraclePreparedStatement paramOraclePreparedStatement, int paramInt1, int paramInt2, int paramInt3, byte[] paramArrayOfByte, char[] paramArrayOfChar, short[] paramArrayOfShort, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, boolean paramBoolean)
    throws SQLException;

  public String toString()
  {
    return getClass() + " [type = " + this.type + ", bytelen = " + this.bytelen + "]";
  }

  protected OracleConnection getConnectionDuringExceptionHandling()
  {
    return null;
  }

  short updateInoutIndicatorValue(short paramShort)
  {
    return paramShort;
  }

  void lastBoundValueCleanup(OraclePreparedStatement paramOraclePreparedStatement, int paramInt)
  {
  }
}