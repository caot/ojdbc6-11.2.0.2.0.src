package oracle.jdbc.driver;

class BooleanBinder extends VarnumBinder
{
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  void bind(OraclePreparedStatement paramOraclePreparedStatement, int paramInt1, int paramInt2, int paramInt3, byte[] paramArrayOfByte, char[] paramArrayOfChar, short[] paramArrayOfShort, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, boolean paramBoolean)
  {
    byte[] arrayOfByte = paramArrayOfByte;
    int i = paramInt6 + 1;
    int j = paramOraclePreparedStatement.parameterInt[paramInt3][paramInt1];
    int k = 0;

    if (j != 0)
    {
      arrayOfByte[i] = -63;
      arrayOfByte[(i + 1)] = 2;
      k = 2;
    }
    else
    {
      arrayOfByte[i] = -128;
      k = 1;
    }

    arrayOfByte[paramInt6] = ((byte)k);
    paramArrayOfShort[paramInt9] = 0;
    paramArrayOfShort[paramInt8] = ((short)(k + 1));
  }
}