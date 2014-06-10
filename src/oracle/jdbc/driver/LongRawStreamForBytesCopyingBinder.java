package oracle.jdbc.driver;

class LongRawStreamForBytesCopyingBinder extends LongRawStreamForBytesBinder
{
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  void bind(OraclePreparedStatement paramOraclePreparedStatement, int paramInt1, int paramInt2, int paramInt3, byte[] paramArrayOfByte, char[] paramArrayOfChar, short[] paramArrayOfShort, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, boolean paramBoolean)
  {
    super.bind(paramOraclePreparedStatement, paramInt1, paramInt2, paramInt3, paramArrayOfByte, paramArrayOfChar, paramArrayOfShort, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8, paramInt9, paramBoolean);

    if (paramInt2 == 0)
    {
      paramOraclePreparedStatement.parameterStream[paramInt3][paramInt1] = paramOraclePreparedStatement.lastBoundStream[paramInt1];
    }
    else
    {
      paramOraclePreparedStatement.parameterStream[paramInt3][paramInt1] = paramOraclePreparedStatement.parameterStream[(paramInt3 - 1)][paramInt1];
    }
  }
}