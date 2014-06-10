package oracle.jdbc.driver;

class CopiedNullBinder extends Binder
{
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  CopiedNullBinder(short paramShort, int paramInt)
  {
    this.type = paramShort;
    this.bytelen = paramInt;
  }

  Binder copyingBinder()
  {
    return this;
  }

  void bind(OraclePreparedStatement paramOraclePreparedStatement, int paramInt1, int paramInt2, int paramInt3, byte[] paramArrayOfByte, char[] paramArrayOfChar, short[] paramArrayOfShort, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, boolean paramBoolean)
  {
    paramArrayOfShort[paramInt9] = -1;
  }
}