package oracle.jdbc.driver;

class CopiedCharBinder extends Binder
{
  char[] value;
  short len;
  short inoutIndicatorValue;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  CopiedCharBinder(short paramShort1, char[] paramArrayOfChar, short paramShort2, short paramShort3)
  {
    this.type = paramShort1;
    this.bytelen = 0;
    this.value = paramArrayOfChar;
    this.len = paramShort2;
    this.inoutIndicatorValue = paramShort3;
  }

  Binder copyingBinder()
  {
    return this;
  }

  void bind(OraclePreparedStatement paramOraclePreparedStatement, int paramInt1, int paramInt2, int paramInt3, byte[] paramArrayOfByte, char[] paramArrayOfChar, short[] paramArrayOfShort, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, boolean paramBoolean)
  {
    paramArrayOfShort[paramInt9] = 0;
    paramArrayOfShort[paramInt8] = this.len;

    System.arraycopy(this.value, 0, paramArrayOfChar, paramInt7, this.value.length);
  }

  short updateInoutIndicatorValue(short paramShort)
  {
    return this.inoutIndicatorValue;
  }
}