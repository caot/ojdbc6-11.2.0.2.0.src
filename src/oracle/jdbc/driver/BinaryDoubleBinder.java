package oracle.jdbc.driver;

class BinaryDoubleBinder extends Binder
{
  Binder theBinaryDoubleCopyingBinder = OraclePreparedStatementReadOnly.theStaticBinaryDoubleCopyingBinder;

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  static void init(Binder paramBinder)
  {
    paramBinder.type = 101;
    paramBinder.bytelen = 8;
  }

  BinaryDoubleBinder()
  {
    init(this);
  }

  Binder copyingBinder()
  {
    return this.theBinaryDoubleCopyingBinder;
  }

  void bind(OraclePreparedStatement paramOraclePreparedStatement, int paramInt1, int paramInt2, int paramInt3, byte[] paramArrayOfByte, char[] paramArrayOfChar, short[] paramArrayOfShort, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, boolean paramBoolean)
  {
    byte[] arrayOfByte = paramArrayOfByte;
    int i = paramInt6;
    double d = paramOraclePreparedStatement.parameterDouble[paramInt3][paramInt1];

    if (d == 0.0D)
      d = 0.0D;
    else if (d != d) {
      d = (0.0D / 0.0D);
    }
    long l = Double.doubleToLongBits(d);

    int j = (int)l;
    int k = (int)(l >> 32);

    int m = j;

    j >>= 8;

    int n = j;

    j >>= 8;

    int i1 = j;

    j >>= 8;

    int i2 = j;

    int i3 = k;

    k >>= 8;

    int i4 = k;

    k >>= 8;

    int i5 = k;

    k >>= 8;

    int i6 = k;

    if ((i6 & 0x80) == 0) {
      i6 |= 128;
    }
    else {
      i6 ^= -1;
      i5 ^= -1;
      i4 ^= -1;
      i3 ^= -1;
      i2 ^= -1;
      i1 ^= -1;
      n ^= -1;
      m ^= -1;
    }

    arrayOfByte[(i + 7)] = ((byte)m);
    arrayOfByte[(i + 6)] = ((byte)n);
    arrayOfByte[(i + 5)] = ((byte)i1);
    arrayOfByte[(i + 4)] = ((byte)i2);
    arrayOfByte[(i + 3)] = ((byte)i3);
    arrayOfByte[(i + 2)] = ((byte)i4);
    arrayOfByte[(i + 1)] = ((byte)i5);
    arrayOfByte[i] = ((byte)i6);
    paramArrayOfShort[paramInt9] = 0;
    paramArrayOfShort[paramInt8] = 8;
  }
}