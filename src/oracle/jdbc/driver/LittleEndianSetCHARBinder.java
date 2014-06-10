package oracle.jdbc.driver;

class LittleEndianSetCHARBinder extends SetCHARBinder
{
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  void bind(OraclePreparedStatement paramOraclePreparedStatement, int paramInt1, int paramInt2, int paramInt3, byte[] paramArrayOfByte, char[] paramArrayOfChar, short[] paramArrayOfShort, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, boolean paramBoolean)
  {
    byte[][] arrayOfByte = paramOraclePreparedStatement.parameterDatum[paramInt3];
    byte[] arrayOfByte1 = arrayOfByte[paramInt1];

    if (paramBoolean) {
      arrayOfByte[paramInt1] = null;
    }
    if (arrayOfByte1 == null)
    {
      paramArrayOfShort[paramInt9] = -1;
    }
    else
    {
      paramArrayOfShort[paramInt9] = 0;

      int i = arrayOfByte1.length;

      paramArrayOfChar[paramInt7] = ((char)i);

      if (i > 65532)
        paramArrayOfShort[paramInt8] = -2;
      else {
        paramArrayOfShort[paramInt8] = ((short)(i + 2));
      }
      int j = paramInt7 + (i >> 1);

      if (i % 2 == 1) {
        paramArrayOfChar[(j + 1)] = ((char)(arrayOfByte1[(--i)] & 0xFF));
      }
      while (i > 0)
      {
        i -= 2;
        paramArrayOfChar[(j--)] = ((char)(arrayOfByte1[i] & 0xFF | arrayOfByte1[(i + 1)] << 8));
      }
    }
  }
}