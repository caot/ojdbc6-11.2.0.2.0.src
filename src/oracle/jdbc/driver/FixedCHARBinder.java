package oracle.jdbc.driver;

class FixedCHARBinder extends Binder
{
  Binder theFixedCHARCopyingBinder = OraclePreparedStatementReadOnly.theStaticFixedCHARCopyingBinder;

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  static void init(Binder paramBinder)
  {
    paramBinder.type = 96;
    paramBinder.bytelen = 0;
  }

  FixedCHARBinder()
  {
    init(this);
  }

  void bind(OraclePreparedStatement paramOraclePreparedStatement, int paramInt1, int paramInt2, int paramInt3, byte[] paramArrayOfByte, char[] paramArrayOfChar, short[] paramArrayOfShort, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, boolean paramBoolean)
  {
    String[] arrayOfString = paramOraclePreparedStatement.parameterString[paramInt3];
    String str = arrayOfString[paramInt1];

    if (paramBoolean) {
      arrayOfString[paramInt1] = null;
    }
    if (str == null)
    {
      paramArrayOfShort[paramInt9] = -1;
    }
    else
    {
      paramArrayOfShort[paramInt9] = 0;

      int i = str.length();

      str.getChars(0, i, paramArrayOfChar, paramInt7);

      i <<= 1;

      if (i > 65534) {
        i = 65534;
      }
      paramArrayOfShort[paramInt8] = ((short)i);
    }
  }

  Binder copyingBinder()
  {
    return this.theFixedCHARCopyingBinder;
  }
}