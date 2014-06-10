package oracle.jdbc.driver;

class OracleNumberBinder extends DatumBinder
{
  Binder theVarnumCopyingBinder = OraclePreparedStatementReadOnly.theStaticVarnumCopyingBinder;

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  static void init(Binder paramBinder)
  {
    paramBinder.type = 6;
    paramBinder.bytelen = 22;
  }

  OracleNumberBinder()
  {
    init(this);
  }

  Binder copyingBinder()
  {
    return this.theVarnumCopyingBinder;
  }

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
      paramArrayOfByte[paramInt6] = ((byte)arrayOfByte1.length);

      System.arraycopy(arrayOfByte1, 0, paramArrayOfByte, paramInt6 + 1, arrayOfByte1.length);

      paramArrayOfShort[paramInt8] = ((short)(arrayOfByte1.length + 1));
    }
  }
}