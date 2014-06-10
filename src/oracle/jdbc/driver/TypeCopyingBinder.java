package oracle.jdbc.driver;

abstract class TypeCopyingBinder extends Binder
{
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  Binder copyingBinder()
  {
    return this;
  }

  void bind(OraclePreparedStatement paramOraclePreparedStatement, int paramInt1, int paramInt2, int paramInt3, byte[] paramArrayOfByte, char[] paramArrayOfChar, short[] paramArrayOfShort, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, boolean paramBoolean)
  {
    if (paramInt2 == 0)
    {
      paramOraclePreparedStatement.parameterDatum[0][paramInt1] = paramOraclePreparedStatement.lastBoundTypeBytes[paramInt1];

      paramOraclePreparedStatement.parameterOtype[0][paramInt1] = paramOraclePreparedStatement.lastBoundTypeOtypes[paramInt1];
    }
    else
    {
      paramOraclePreparedStatement.parameterDatum[paramInt2][paramInt1] = paramOraclePreparedStatement.parameterDatum[(paramInt2 - 1)][paramInt1];

      paramOraclePreparedStatement.parameterOtype[paramInt2][paramInt1] = paramOraclePreparedStatement.parameterOtype[(paramInt2 - 1)][paramInt1];
    }
  }
}