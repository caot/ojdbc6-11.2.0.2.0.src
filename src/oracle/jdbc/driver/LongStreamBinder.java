package oracle.jdbc.driver;

class LongStreamBinder extends Binder
{
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  LongStreamBinder()
  {
    this.type = 8;
  }

  void bind(OraclePreparedStatement paramOraclePreparedStatement, int paramInt1, int paramInt2, int paramInt3, byte[] paramArrayOfByte, char[] paramArrayOfChar, short[] paramArrayOfShort, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, boolean paramBoolean)
  {
    paramArrayOfShort[paramInt9] = 0;
  }

  Binder copyingBinder()
  {
    return OraclePreparedStatementReadOnly.theStaticVarcharNullBinder;
  }
}