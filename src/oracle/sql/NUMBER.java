package oracle.sql;

import java.io.PrintStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.SQLException;
import oracle.core.lmx.CoreException;

public class NUMBER extends Datum
{
  static byte[] MAX_LONG = toBytes(9223372036854775807L);
  static byte[] MIN_LONG = toBytes(-9223372036854775808L);
  private static final int CHARACTER_ZERO = 48;
  private static final BigDecimal BIGDEC_NEGZERO = new BigDecimal("-0");
  private static final BigDecimal BIGDEC_ZERO = BigDecimal.valueOf(0L);
  private static final BigDecimal BIGDEC_ONE = BigDecimal.valueOf(1L);
  private static final BigInteger BIGINT_ZERO = BigInteger.valueOf(0L);
  private static final BigInteger BIGINT_HUND = BigInteger.valueOf(100L);
  private static final byte DIGEND = 21;
  private static final byte ODIGEND = 9;
  private static final int HUNDIGMAX = 66;
  private static final int BIGINTARRAYMAX = 54;
  private static final double BIGRATIO = 0.150514997831991D;
  private static final int BIGLENMAX = 22;
  static final byte LNXM_NUM = 22;
  static final int LNXSGNBT = 128;
  static final byte LNXDIGS = 20;
  static final byte LNXEXPBS = 64;
  static final double ORANUM_FBASE = 100.0D;
  static final int LNXBASE = 100;
  static final byte IEEE_DBL_DIG = 15;
  private static final byte IEEE_FLT_DIG = 6;
  static final int LNXEXPMX = 127;
  static final int LNXEXPMN = 0;
  static final int LNXMXOUT = 40;
  static final int LNXMXFMT = 64;
  private static final byte BYTE_MAX_VALUE = 127;
  private static final byte BYTE_MIN_VALUE = -128;
  private static final short SHORT_MAX_VALUE = 32767;
  private static final short SHORT_MIN_VALUE = -32768;
  private static final byte[] PI = { -63, 4, 15, 16, 93, 66, 36, 90, 80, 33, 39, 47, 27, 44, 39, 33, 80, 51, 29, 85, 21 };

  private static final byte[] E = { -63, 3, 72, 83, 82, 83, 85, 60, 5, 53, 36, 37, 3, 88, 48, 14, 53, 67, 25, 98, 77 };

  private static final byte[] LN10 = { -63, 3, 31, 26, 86, 10, 30, 95, 5, 57, 85, 2, 80, 92, 46, 47, 85, 37, 43, 8, 61 };
  private static LnxLib _slnxlib;
  private static LnxLib _thinlib = null;

  private static int DBL_MAX = 40;

  private static int INT_MAX = 15;
  private static float FLOAT_MAX_INT = 2.147484E+09F;
  private static float FLOAT_MIN_INT = -2.147484E+09F;
  private static double DOUBLE_MAX_INT = 2147483647.0D;
  private static double DOUBLE_MIN_INT = -2147483648.0D;
  private static double DOUBLE_MAX_INT_2 = 2147483649.0D;
  private static double DOUBLE_MIN_INT_2 = -2147483649.0D;

  private static Object drvType = null;

  private static String LANGID = "AMERICAN";

  public NUMBER()
  {
    super(_makeZero());
  }

  public NUMBER(byte[] paramArrayOfByte)
  {
    super(paramArrayOfByte);
  }

  public NUMBER(byte paramByte)
  {
    super(toBytes(paramByte));
  }

  public NUMBER(int paramInt)
  {
    super(toBytes(paramInt));
  }

  public NUMBER(long paramLong)
  {
    super(toBytes(paramLong));
  }

  public NUMBER(short paramShort)
  {
    super(toBytes(paramShort));
  }

  public NUMBER(float paramFloat)
  {
    super(toBytes(paramFloat));
  }

  public NUMBER(double paramDouble)
    throws SQLException
  {
    super(toBytes(paramDouble));
  }

  public NUMBER(BigDecimal paramBigDecimal)
    throws SQLException
  {
    super(toBytes(paramBigDecimal));
  }

  public NUMBER(BigInteger paramBigInteger)
    throws SQLException
  {
    super(toBytes(paramBigInteger));
  }

  public NUMBER(String paramString, int paramInt)
    throws SQLException
  {
    super(toBytes(paramString, paramInt));
  }

  public NUMBER(boolean paramBoolean)
  {
    super(toBytes(paramBoolean));
  }

  public NUMBER(Object paramObject)
    throws SQLException
  {
    if ((paramObject instanceof Integer))
      setShareBytes(toBytes(((Integer)paramObject).intValue()));
    else if ((paramObject instanceof Long))
      setShareBytes(toBytes(((Long)paramObject).longValue()));
    else if ((paramObject instanceof Float))
      setShareBytes(toBytes(((Float)paramObject).floatValue()));
    else if ((paramObject instanceof Double))
      setShareBytes(toBytes(((Double)paramObject).doubleValue()));
    else if ((paramObject instanceof BigInteger))
      setShareBytes(toBytes((BigInteger)paramObject));
    else if ((paramObject instanceof BigDecimal))
      setShareBytes(toBytes((BigDecimal)paramObject));
    else if ((paramObject instanceof Boolean))
      setShareBytes(toBytes(((Boolean)paramObject).booleanValue()));
    else if ((paramObject instanceof String)) {
      setShareBytes(stringToBytes((String)paramObject));
    }
    else
      throw new SQLException("Initialization failed");
  }

  public static double toDouble(byte[] paramArrayOfByte)
  {
    if (_isZero(paramArrayOfByte)) {
      return 0.0D;
    }
    if (_isPosInf(paramArrayOfByte)) {
      return (1.0D / 0.0D);
    }
    if (_isNegInf(paramArrayOfByte)) {
      return (-1.0D / 0.0D);
    }

    String str = null;
    try
    {
      if (drvType == null)
      {
        str = _slnxlib.lnxnuc(paramArrayOfByte, DBL_MAX, null);
      }
      else str = _slnxlib.lnxnuc(paramArrayOfByte, DBL_MAX, LANGID);
    }
    catch (Exception localException)
    {
    }

    double d = Double.valueOf(str).doubleValue();

    return d;
  }

  public static float toFloat(byte[] paramArrayOfByte)
  {
    return (float)toDouble(paramArrayOfByte);
  }

  public static long toLong(byte[] paramArrayOfByte)
    throws SQLException
  {
    if (_isZero(paramArrayOfByte)) {
      return 0L;
    }

    if ((_isInf(paramArrayOfByte)) || (compareBytes(paramArrayOfByte, MAX_LONG) > 0) || (compareBytes(paramArrayOfByte, MIN_LONG) < 0))
    {
      throw new SQLException(CoreException.getMessage((byte)3));
    }
    return _getLnxLib().lnxsni(paramArrayOfByte);
  }

  public static int toInt(byte[] paramArrayOfByte)
    throws SQLException
  {
    if (_isInf(paramArrayOfByte))
      throw new SQLException(CoreException.getMessage((byte)3));
    String str;
    if (drvType == null)
    {
      str = _slnxlib.lnxnuc(paramArrayOfByte, INT_MAX, null);
    }
    else str = _slnxlib.lnxnuc(paramArrayOfByte, INT_MAX, LANGID);

    double d = Double.valueOf(str).doubleValue();

    if (((float)d > FLOAT_MAX_INT) || ((float)d < FLOAT_MIN_INT)) {
      throw new SQLException(CoreException.getMessage((byte)3));
    }

    if ((d > DOUBLE_MAX_INT) && (d <= DOUBLE_MAX_INT_2)) {
      throw new SQLException(CoreException.getMessage((byte)3));
    }
    if ((d < DOUBLE_MIN_INT) && (d >= DOUBLE_MIN_INT_2))
      throw new SQLException(CoreException.getMessage((byte)3));
    int i = (int)d;
    return i;
  }

  public static short toShort(byte[] paramArrayOfByte)
    throws SQLException
  {
    long l = 0L;
    try
    {
      l = toLong(paramArrayOfByte);

      if ((l > 32767L) || (l < -32768L)) {
        throw new SQLException(CoreException.getMessage((byte)3));
      }
    }
    finally
    {
    }

    return (short)(int)l;
  }

  public static byte toByte(byte[] paramArrayOfByte)
    throws SQLException
  {
    long l = 0L;
    try
    {
      l = toLong(paramArrayOfByte);

      if ((l > 127L) || (l < -128L)) {
        throw new SQLException(CoreException.getMessage((byte)3));
      }
    }
    finally
    {
    }

    return (byte)(int)l;
  }

  public static BigInteger toBigInteger(byte[] paramArrayOfByte)
    throws SQLException
  {
    long[] arrayOfLong = new long[10];
    int i = 9;
    int j = 1;

    int i1 = 0;

    if (_isZero(paramArrayOfByte)) {
      return BIGINT_ZERO;
    }
    if (_isInf(paramArrayOfByte)) {
      throw new SQLException(CoreException.getMessage((byte)3));
    }

    boolean bool = _isPositive(paramArrayOfByte);

    byte[] arrayOfByte1 = _fromLnxFmt(paramArrayOfByte);

    if (arrayOfByte1[0] < 0)
    {
      return BIGINT_ZERO;
    }

    int i2 = Math.min(arrayOfByte1[0] + 1, arrayOfByte1.length - 1);
    int k = i2;

    if ((i2 & 0x1) == 1)
    {
      arrayOfLong[i] = arrayOfByte1[j];
      j = (byte)(j + 1);
      k--;
    }
    else
    {
      arrayOfLong[i] = (arrayOfByte1[j] * 100 + arrayOfByte1[(j + 1)]);
      j = (byte)(j + 2);
      k -= 2;
    }

    int m = i;
    while (k != 0)
    {
      long l = arrayOfByte1[j] * 100 + arrayOfByte1[(j + 1)];

      for (i = 9; i >= m; i = (byte)(i - 1))
      {
        l += arrayOfLong[i] * 10000L;
        arrayOfLong[i] = (l & 0xFFFF);
        l >>= 16;
      }

      if (l != 0L);
      m = (byte)(m - 1);
      arrayOfLong[m] = l;

      j = (byte)(j + 2);
      k -= 2;
    }
    int n;
    if (arrayOfLong[m] >> 8 != 0L)
      n = 2 * (9 - m) + 2;
    else {
      n = 2 * (9 - m) + 1;
    }
    byte[] arrayOfByte2 = new byte[n];

    if ((n & 0x1) == 1)
    {
      arrayOfByte2[i1] = ((byte)(int)arrayOfLong[m]);
      i1++;
    }
    else
    {
      arrayOfByte2[i1] = ((byte)(int)(arrayOfLong[m] >> 8));
      i1++;
      arrayOfByte2[i1] = ((byte)(int)(arrayOfLong[m] & 0xFF));
      i1++;
    }

    for (m = (byte)(m + 1); m <= 9; m = (byte)(m + 1))
    {
      arrayOfByte2[i1] = ((byte)(int)(arrayOfLong[m] >> 8));
      arrayOfByte2[(i1 + 1)] = ((byte)(int)(arrayOfLong[m] & 0xFF));
      i1 += 2;
    }

    BigInteger localBigInteger = new BigInteger(bool ? 1 : -1, arrayOfByte2);

    int i3 = arrayOfByte1[0] - (i2 - 1);
    return localBigInteger.multiply(BIGINT_HUND.pow(i3));
  }

  public static BigDecimal toBigDecimal(byte[] paramArrayOfByte)
    throws SQLException
  {
    long[] arrayOfLong = new long[10];
    int i = 9;
    int j = 1;

    int i1 = 0;

    if (_isZero(paramArrayOfByte)) {
      return BIGDEC_ZERO;
    }
    if (_isInf(paramArrayOfByte)) {
      throw new SQLException(CoreException.getMessage((byte)3));
    }

    boolean bool = _isPositive(paramArrayOfByte);

    byte[] arrayOfByte1 = _fromLnxFmt(paramArrayOfByte);
    int i2;
    int k = i2 = arrayOfByte1.length - 1;

    if ((i2 & 0x1) == 1)
    {
      arrayOfLong[i] = arrayOfByte1[j];
      j = (byte)(j + 1);
      k--;
    }
    else
    {
      arrayOfLong[i] = (arrayOfByte1[j] * 100 + arrayOfByte1[(j + 1)]);
      j = (byte)(j + 2);
      k -= 2;
    }

    int m = i;
    while (k != 0)
    {
      long l = arrayOfByte1[j] * 100 + arrayOfByte1[(j + 1)];

      for (i = 9; i >= m; i = (byte)(i - 1))
      {
        l += arrayOfLong[i] * 10000L;
        arrayOfLong[i] = (l & 0xFFFF);
        l >>= 16;
      }

      if (l != 0L);
      m = (byte)(m - 1);
      arrayOfLong[m] = l;

      j = (byte)(j + 2);
      k -= 2;
    }
    int n;
    if (arrayOfLong[m] >> 8 != 0L)
      n = 2 * (9 - m) + 2;
    else {
      n = 2 * (9 - m) + 1;
    }
    byte[] arrayOfByte2 = new byte[n];

    if ((n & 0x1) == 1)
    {
      arrayOfByte2[i1] = ((byte)(int)arrayOfLong[m]);
      i1++;
    }
    else
    {
      arrayOfByte2[i1] = ((byte)(int)(arrayOfLong[m] >> 8));
      i1++;
      arrayOfByte2[i1] = ((byte)(int)(arrayOfLong[m] & 0xFF));
      i1++;
    }

    for (m = (byte)(m + 1); m <= 9; m = (byte)(m + 1))
    {
      arrayOfByte2[i1] = ((byte)(int)(arrayOfLong[m] >> 8));
      arrayOfByte2[(i1 + 1)] = ((byte)(int)(arrayOfLong[m] & 0xFF));
      i1 += 2;
    }

    BigInteger localBigInteger = new BigInteger(bool ? 1 : -1, arrayOfByte2);
    BigDecimal localBigDecimal = new BigDecimal(localBigInteger);

    int i3 = arrayOfByte1[0] - i2 + 1;

    localBigDecimal = localBigDecimal.movePointRight(i3 * 2);

    if ((i3 < 0) && (arrayOfByte1[i2] % 10 == 0))
      localBigDecimal = localBigDecimal.setScale(-(i3 * 2 + 1));
    return localBigDecimal;
  }

  public static String toString(byte[] paramArrayOfByte)
  {
    int i = 0;

    if (_isZero(paramArrayOfByte)) {
      return "0";
    }
    if (_isPosInf(paramArrayOfByte)) {
      return new Double((1.0D / 0.0D)).toString();
    }
    if (_isNegInf(paramArrayOfByte)) {
      return new Double((-1.0D / 0.0D)).toString();
    }

    byte[] arrayOfByte = _fromLnxFmt(paramArrayOfByte);

    int k = arrayOfByte[0];
    int m = arrayOfByte.length - 1;
    int n = k - (m - 1);
    int i2;
    if (n >= 0)
    {
      i2 = 2 * (k + 1) + 1;
    }
    else if (k >= 0)
      i2 = 2 * (m + 1);
    else {
      i2 = 2 * (m - k) + 3;
    }

    char[] arrayOfChar = new char[i2];

    if (!_isPositive(paramArrayOfByte))
    {
      arrayOfChar[(i++)] = '-';
    }
    int j;
    if (n >= 0)
    {
      i += _byteToChars(arrayOfByte[1], arrayOfChar, i);

      for (j = 2; j <= m; k--)
      {
        _byteTo2Chars(arrayOfByte[j], arrayOfChar, i);
        i += 2;

        j++;
      }

      if (k > 0)
        for (; k > 0; k--)
        {
          arrayOfChar[(i++)] = '0';
          arrayOfChar[(i++)] = '0';
        }
    }
    else
    {
      int i1 = m + n;

      if (i1 > 0)
      {
        i += _byteToChars(arrayOfByte[1], arrayOfChar, i);

        if (i1 == 1)
        {
          arrayOfChar[(i++)] = '.';
        }

        for (j = 2; j < m; j++)
        {
          _byteTo2Chars(arrayOfByte[j], arrayOfChar, i);
          i += 2;

          if (i1 == j)
          {
            arrayOfChar[(i++)] = '.';
          }
        }
        if (arrayOfByte[j] % 10 == 0)
        {
          i += _byteToChars((byte)(arrayOfByte[j] / 10), arrayOfChar, i);
        }
        else
        {
          _byteTo2Chars(arrayOfByte[j], arrayOfChar, i);
          i += 2;
        }
      }
      else
      {
        arrayOfChar[(i++)] = '0';
        arrayOfChar[(i++)] = '.';

        for (; i1 < 0; i1++)
        {
          arrayOfChar[(i++)] = '0';
          arrayOfChar[(i++)] = '0';
        }

        for (j = 1; j < m; j++)
        {
          _byteTo2Chars(arrayOfByte[j], arrayOfChar, i);
          i += 2;
        }

        if (arrayOfByte[j] % 10 == 0)
        {
          i += _byteToChars((byte)(arrayOfByte[j] / 10), arrayOfChar, i);
        }
        else
        {
          _byteTo2Chars(arrayOfByte[j], arrayOfChar, i);
          i += 2;
        }
      }
    }

    return new String(arrayOfChar, 0, i);
  }

  public static boolean toBoolean(byte[] paramArrayOfByte)
  {
    if (_isZero(paramArrayOfByte)) {
      return false;
    }
    return true;
  }

  public static byte[] toBytes(double paramDouble)
    throws SQLException
  {
    if (Double.isNaN(paramDouble)) {
      throw new IllegalArgumentException(CoreException.getMessage((byte)11));
    }

    if ((paramDouble == 0.0D) || (paramDouble == -0.0D)) {
      return _makeZero();
    }
    if (paramDouble == (1.0D / 0.0D)) {
      return _makePosInf();
    }

    if (paramDouble == (-1.0D / 0.0D)) {
      return _makeNegInf();
    }

    return _getThinLib().lnxren(paramDouble);
  }

  public static byte[] toBytes(float paramFloat)
  {
    if (Float.isNaN(paramFloat)) {
      throw new IllegalArgumentException(CoreException.getMessage((byte)11));
    }

    if ((paramFloat == 0.0F) || (paramFloat == -0.0F)) {
      return _makeZero();
    }
    if (paramFloat == (1.0F / 1.0F)) {
      return _makePosInf();
    }
    if (paramFloat == (1.0F / -1.0F)) {
      return _makeNegInf();
    }

    String str = Float.toString(paramFloat);
    try
    {
      return _getLnxLib().lnxcpn(str, false, 0, false, 0, "AMERICAN_AMERICA");
    }
    catch (Exception localException) {
    }
    return null;
  }

  public static byte[] toBytes(long paramLong)
  {
    return _getLnxLib().lnxmin(paramLong);
  }

  public static byte[] toBytes(int paramInt)
  {
    return toBytes(paramInt);
  }

  public static byte[] toBytes(short paramShort)
  {
    return toBytes(paramShort);
  }

  public static byte[] toBytes(byte paramByte)
  {
    return toBytes(paramByte); } 
  public static byte[] toBytes(BigInteger paramBigInteger) throws SQLException { // Byte code:
    //   0: bipush 66
    //   2: newarray byte
    //   4: astore_1
    //   5: bipush 54
    //   7: newarray long
    //   9: astore_2
    //   10: bipush 22
    //   12: newarray long
    //   14: astore_3
    //   15: bipush 21
    //   17: istore 4
    //   19: iconst_0
    //   20: istore 5
    //   22: bipush 21
    //   24: istore 7
    //   26: iconst_0
    //   27: istore 10
    //   29: iconst_0
    //   30: istore 12
    //   32: iconst_1
    //   33: istore 14
    //   35: aload_0
    //   36: invokevirtual 111	java/math/BigInteger:signum	()I
    //   39: ifne +7 -> 46
    //   42: invokestatic 1	oracle/sql/NUMBER:_makeZero	()[B
    //   45: areturn
    //   46: aload_0
    //   47: invokevirtual 111	java/math/BigInteger:signum	()I
    //   50: iconst_m1
    //   51: if_icmpne +38 -> 89
    //   54: aload_0
    //   55: invokevirtual 112	java/math/BigInteger:abs	()Ljava/math/BigInteger;
    //   58: astore 17
    //   60: iconst_0
    //   61: istore 14
    //   63: aload 17
    //   65: invokevirtual 113	java/math/BigInteger:toByteArray	()[B
    //   68: astore 13
    //   70: aload 17
    //   72: invokevirtual 114	java/math/BigInteger:bitLength	()I
    //   75: i2d
    //   76: ldc2_w 115
    //   79: dmul
    //   80: invokestatic 117	java/lang/Math:floor	(D)D
    //   83: d2i
    //   84: istore 15
    //   86: goto +24 -> 110
    //   89: aload_0
    //   90: invokevirtual 113	java/math/BigInteger:toByteArray	()[B
    //   93: astore 13
    //   95: aload_0
    //   96: invokevirtual 114	java/math/BigInteger:bitLength	()I
    //   99: i2d
    //   100: ldc2_w 115
    //   103: dmul
    //   104: invokestatic 117	java/lang/Math:floor	(D)D
    //   107: d2i
    //   108: istore 15
    //   110: aload_0
    //   111: invokevirtual 112	java/math/BigInteger:abs	()Ljava/math/BigInteger;
    //   114: getstatic 81	oracle/sql/NUMBER:BIGINT_HUND	Ljava/math/BigInteger;
    //   117: iload 15
    //   119: invokevirtual 82	java/math/BigInteger:pow	(I)Ljava/math/BigInteger;
    //   122: invokevirtual 118	java/math/BigInteger:compareTo	(Ljava/math/BigInteger;)I
    //   125: ifge +6 -> 131
    //   128: iinc 15 255
    //   131: aload 13
    //   133: arraylength
    //   134: bipush 54
    //   136: if_icmple +15 -> 151
    //   139: new 29	java/sql/SQLException
    //   142: dup
    //   143: iconst_3
    //   144: invokestatic 51	oracle/core/lmx/CoreException:getMessage	(B)Ljava/lang/String;
    //   147: invokespecial 31	java/sql/SQLException:<init>	(Ljava/lang/String;)V
    //   150: athrow
    //   151: iconst_0
    //   152: istore 16
    //   154: iload 16
    //   156: aload 13
    //   158: arraylength
    //   159: if_icmpge +44 -> 203
    //   162: aload 13
    //   164: iload 16
    //   166: baload
    //   167: ifge +20 -> 187
    //   170: aload_2
    //   171: iload 16
    //   173: aload 13
    //   175: iload 16
    //   177: baload
    //   178: sipush 256
    //   181: iadd
    //   182: i2l
    //   183: lastore
    //   184: goto +13 -> 197
    //   187: aload_2
    //   188: iload 16
    //   190: aload 13
    //   192: iload 16
    //   194: baload
    //   195: i2l
    //   196: lastore
    //   197: iinc 16 1
    //   200: goto -46 -> 154
    //   203: aload 13
    //   205: arraylength
    //   206: istore 6
    //   208: iload 6
    //   210: iconst_3
    //   211: irem
    //   212: lookupswitch	default:+80->292, 1:+59->271, 2:+28->240
    //   241: iload 4
    //   243: aload_2
    //   244: iload 5
    //   246: laload
    //   247: bipush 8
    //   249: lshl
    //   250: aload_2
    //   251: iload 5
    //   253: iconst_1
    //   254: iadd
    //   255: laload
    //   256: ladd
    //   257: lastore
    //   258: iload 5
    //   260: iconst_2
    //   261: iadd
    //   262: i2b
    //   263: istore 5
    //   265: iinc 6 254
    //   268: goto +104 -> 372
    //   271: aload_3
    //   272: iload 4
    //   274: aload_2
    //   275: iload 5
    //   277: laload
    //   278: lastore
    //   279: iload 5
    //   281: iconst_1
    //   282: iadd
    //   283: i2b
    //   284: istore 5
    //   286: iinc 6 255
    //   289: goto +83 -> 372
    //   292: aload_2
    //   293: iload 5
    //   295: laload
    //   296: bipush 16
    //   298: lshl
    //   299: aload_2
    //   300: iload 5
    //   302: iconst_1
    //   303: iadd
    //   304: laload
    //   305: bipush 8
    //   307: lshl
    //   308: ladd
    //   309: aload_2
    //   310: iload 5
    //   312: iconst_2
    //   313: iadd
    //   314: laload
    //   315: ladd
    //   316: lstore 8
    //   318: aload_3
    //   319: iload 4
    //   321: lload 8
    //   323: ldc2_w 119
    //   326: lrem
    //   327: lastore
    //   328: aload_3
    //   329: iload 4
    //   331: iconst_1
    //   332: isub
    //   333: lload 8
    //   335: ldc2_w 119
    //   338: ldiv
    //   339: lastore
    //   340: iload 7
    //   342: aload_3
    //   343: iload 4
    //   345: iconst_1
    //   346: isub
    //   347: laload
    //   348: lconst_0
    //   349: lcmp
    //   350: ifeq +7 -> 357
    //   353: iconst_1
    //   354: goto +4 -> 358
    //   357: iconst_0
    //   358: isub
    //   359: i2b
    //   360: istore 7
    //   362: iload 5
    //   364: iconst_3
    //   365: iadd
    //   366: i2b
    //   367: istore 5
    //   369: iinc 6 253
    //   372: iload 6
    //   374: ifeq +197 -> 571
    //   377: aload_2
    //   378: iload 5
    //   380: laload
    //   381: iconst_4
    //   382: lshl
    //   383: aload_2
    //   384: iload 5
    //   386: iconst_1
    //   387: iadd
    //   388: laload
    //   389: iconst_4
    //   390: lshr
    //   391: ladd
    //   392: lstore 8
    //   394: bipush 21
    //   396: istore 4
    //   398: iload 4
    //   400: iload 7
    //   402: if_icmplt +43 -> 445
    //   405: lload 8
    //   407: aload_3
    //   408: iload 4
    //   410: laload
    //   411: bipush 12
    //   413: lshl
    //   414: ladd
    //   415: lstore 8
    //   417: aload_3
    //   418: iload 4
    //   420: lload 8
    //   422: ldc2_w 119
    //   425: lrem
    //   426: lastore
    //   427: lload 8
    //   429: ldc2_w 119
    //   432: ldiv
    //   433: lstore 8
    //   435: iload 4
    //   437: iconst_1
    //   438: isub
    //   439: i2b
    //   440: istore 4
    //   442: goto -44 -> 398
    //   445: lload 8
    //   447: lconst_0
    //   448: lcmp
    //   449: ifeq +16 -> 465
    //   452: iload 7
    //   454: iconst_1
    //   455: isub
    //   456: i2b
    //   457: istore 7
    //   459: aload_3
    //   460: iload 7
    //   462: lload 8
    //   464: lastore
    //   465: aload_2
    //   466: iload 5
    //   468: iconst_1
    //   469: iadd
    //   470: laload
    //   471: ldc2_w 121
    //   474: land
    //   475: bipush 8
    //   477: lshl
    //   478: aload_2
    //   479: iload 5
    //   481: iconst_2
    //   482: iadd
    //   483: laload
    //   484: ladd
    //   485: lstore 8
    //   487: bipush 21
    //   489: istore 4
    //   491: iload 4
    //   493: iload 7
    //   495: if_icmplt +43 -> 538
    //   498: lload 8
    //   500: aload_3
    //   501: iload 4
    //   503: laload
    //   504: bipush 12
    //   506: lshl
    //   507: ladd
    //   508: lstore 8
    //   510: aload_3
    //   511: iload 4
    //   513: lload 8
    //   515: ldc2_w 119
    //   518: lrem
    //   519: lastore
    //   520: lload 8
    //   522: ldc2_w 119
    //   525: ldiv
    //   526: lstore 8
    //   528: iload 4
    //   530: iconst_1
    //   531: isub
    //   532: i2b
    //   533: istore 4
    //   535: goto -44 -> 491
    //   538: lload 8
    //   540: lconst_0
    //   541: lcmp
    //   542: ifeq +16 -> 558
    //   545: iload 7
    //   547: iconst_1
    //   548: isub
    //   549: i2b
    //   550: istore 7
    //   552: aload_3
    //   553: iload 7
    //   555: lload 8
    //   557: lastore
    //   558: iload 5
    //   560: iconst_3
    //   561: iadd
    //   562: i2b
    //   563: istore 5
    //   565: iinc 6 253
    //   568: goto -196 -> 372
    //   571: aload_1
    //   572: iload 12
    //   574: aload_3
    //   575: iload 7
    //   577: laload
    //   578: ldc2_w 74
    //   581: ldiv
    //   582: l2i
    //   583: i2b
    //   584: dup_x2
    //   585: bastore
    //   586: ifeq +56 -> 642
    //   589: iconst_3
    //   590: bipush 21
    //   592: iload 7
    //   594: isub
    //   595: imul
    //   596: iconst_3
    //   597: iadd
    //   598: istore 11
    //   600: aload_1
    //   601: iload 12
    //   603: iconst_1
    //   604: iadd
    //   605: aload_3
    //   606: iload 7
    //   608: laload
    //   609: ldc2_w 74
    //   612: lrem
    //   613: ldc2_w 123
    //   616: ldiv
    //   617: l2i
    //   618: i2b
    //   619: bastore
    //   620: aload_1
    //   621: iload 12
    //   623: iconst_2
    //   624: iadd
    //   625: aload_3
    //   626: iload 7
    //   628: laload
    //   629: ldc2_w 123
    //   632: lrem
    //   633: l2i
    //   634: i2b
    //   635: bastore
    //   636: iinc 12 3
    //   639: goto +82 -> 721
    //   642: aload_1
    //   643: iload 12
    //   645: aload_3
    //   646: iload 7
    //   648: laload
    //   649: ldc2_w 74
    //   652: lrem
    //   653: ldc2_w 123
    //   656: ldiv
    //   657: l2i
    //   658: i2b
    //   659: dup_x2
    //   660: bastore
    //   661: ifeq +36 -> 697
    //   664: iconst_3
    //   665: bipush 21
    //   667: iload 7
    //   669: isub
    //   670: imul
    //   671: iconst_2
    //   672: iadd
    //   673: istore 11
    //   675: aload_1
    //   676: iload 12
    //   678: iconst_1
    //   679: iadd
    //   680: aload_3
    //   681: iload 7
    //   683: laload
    //   684: ldc2_w 123
    //   687: lrem
    //   688: l2i
    //   689: i2b
    //   690: bastore
    //   691: iinc 12 2
    //   694: goto +27 -> 721
    //   697: aload_1
    //   698: iload 12
    //   700: aload_3
    //   701: iload 7
    //   703: laload
    //   704: l2i
    //   705: i2b
    //   706: bastore
    //   707: iconst_3
    //   708: bipush 21
    //   710: iload 7
    //   712: isub
    //   713: imul
    //   714: iconst_1
    //   715: iadd
    //   716: istore 11
    //   718: iinc 12 1
    //   721: iload 7
    //   723: iconst_1
    //   724: iadd
    //   725: i2b
    //   726: istore 4
    //   728: iload 4
    //   730: bipush 21
    //   732: if_icmpgt +66 -> 798
    //   735: aload_1
    //   736: iload 12
    //   738: aload_3
    //   739: iload 4
    //   741: laload
    //   742: ldc2_w 74
    //   745: ldiv
    //   746: l2i
    //   747: i2b
    //   748: bastore
    //   749: aload_1
    //   750: iload 12
    //   752: iconst_1
    //   753: iadd
    //   754: aload_3
    //   755: iload 4
    //   757: laload
    //   758: ldc2_w 74
    //   761: lrem
    //   762: ldc2_w 123
    //   765: ldiv
    //   766: l2i
    //   767: i2b
    //   768: bastore
    //   769: aload_1
    //   770: iload 12
    //   772: iconst_2
    //   773: iadd
    //   774: aload_3
    //   775: iload 4
    //   777: laload
    //   778: ldc2_w 123
    //   781: lrem
    //   782: l2i
    //   783: i2b
    //   784: bastore
    //   785: iinc 12 3
    //   788: iload 4
    //   790: iconst_1
    //   791: iadd
    //   792: i2b
    //   793: istore 4
    //   795: goto -67 -> 728
    //   798: iload 12
    //   800: iconst_1
    //   801: isub
    //   802: istore 16
    //   804: iload 16
    //   806: iflt +19 -> 825
    //   809: aload_1
    //   810: iload 16
    //   812: baload
    //   813: ifne +12 -> 825
    //   816: iinc 11 255
    //   819: iinc 16 255
    //   822: goto -18 -> 804
    //   825: iload 11
    //   827: bipush 19
    //   829: if_icmple +104 -> 933
    //   832: bipush 20
    //   834: istore 16
    //   836: bipush 19
    //   838: istore 11
    //   840: aload_1
    //   841: iload 16
    //   843: baload
    //   844: bipush 50
    //   846: if_icmplt +87 -> 933
    //   849: iinc 16 255
    //   852: aload_1
    //   853: iload 16
    //   855: dup2
    //   856: baload
    //   857: iconst_1
    //   858: iadd
    //   859: i2b
    //   860: bastore
    //   861: aload_1
    //   862: iload 16
    //   864: baload
    //   865: bipush 100
    //   867: if_icmpne +39 -> 906
    //   870: iload 16
    //   872: ifne +14 -> 886
    //   875: iinc 15 1
    //   878: aload_1
    //   879: iload 16
    //   881: iconst_1
    //   882: bastore
    //   883: goto +23 -> 906
    //   886: aload_1
    //   887: iload 16
    //   889: iconst_0
    //   890: bastore
    //   891: iinc 16 255
    //   894: aload_1
    //   895: iload 16
    //   897: dup2
    //   898: baload
    //   899: iconst_1
    //   900: iadd
    //   901: i2b
    //   902: bastore
    //   903: goto -42 -> 861
    //   906: iload 11
    //   908: iconst_1
    //   909: isub
    //   910: istore 16
    //   912: iload 16
    //   914: iflt +19 -> 933
    //   917: aload_1
    //   918: iload 16
    //   920: baload
    //   921: ifne +12 -> 933
    //   924: iinc 11 255
    //   927: iinc 16 255
    //   930: goto -18 -> 912
    //   933: iload 15
    //   935: bipush 62
    //   937: if_icmple +15 -> 952
    //   940: new 29	java/sql/SQLException
    //   943: dup
    //   944: iconst_3
    //   945: invokestatic 51	oracle/core/lmx/CoreException:getMessage	(B)Ljava/lang/String;
    //   948: invokespecial 31	java/sql/SQLException:<init>	(Ljava/lang/String;)V
    //   951: athrow
    //   952: iload 11
    //   954: iconst_1
    //   955: iadd
    //   956: newarray byte
    //   958: astore 17
    //   960: aload 17
    //   962: iconst_0
    //   963: iload 15
    //   965: i2b
    //   966: bastore
    //   967: aload_1
    //   968: iconst_0
    //   969: aload 17
    //   971: iconst_1
    //   972: iload 11
    //   974: invokestatic 125	java/lang/System:arraycopy	(Ljava/lang/Object;ILjava/lang/Object;II)V
    //   977: aload 17
    //   979: iload 14
    //   981: invokestatic 126	oracle/sql/NUMBER:_toLnxFmt	([BZ)[B
    //   984: areturn } 
  public static byte[] toBytes(BigDecimal paramBigDecimal) throws SQLException { // Byte code:
    //   0: bipush 66
    //   2: newarray byte
    //   4: astore_1
    //   5: bipush 54
    //   7: newarray long
    //   9: astore_2
    //   10: bipush 22
    //   12: newarray long
    //   14: astore_3
    //   15: bipush 21
    //   17: istore 4
    //   19: iconst_0
    //   20: istore 5
    //   22: bipush 21
    //   24: istore 7
    //   26: iconst_0
    //   27: istore 11
    //   29: iconst_0
    //   30: istore 14
    //   32: aload_0
    //   33: invokevirtual 127	java/math/BigDecimal:abs	()Ljava/math/BigDecimal;
    //   36: astore 18
    //   38: iconst_0
    //   39: istore 20
    //   41: aload_0
    //   42: invokevirtual 128	java/math/BigDecimal:signum	()I
    //   45: ifne +7 -> 52
    //   48: invokestatic 1	oracle/sql/NUMBER:_makeZero	()[B
    //   51: areturn
    //   52: aload_0
    //   53: invokevirtual 128	java/math/BigDecimal:signum	()I
    //   56: iconst_m1
    //   57: if_icmpne +7 -> 64
    //   60: iconst_0
    //   61: goto +4 -> 65
    //   64: iconst_1
    //   65: istore 13
    //   67: aload_0
    //   68: invokevirtual 129	java/math/BigDecimal:scale	()I
    //   71: istore 16
    //   73: iload 16
    //   75: ifge +12 -> 87
    //   78: aload_0
    //   79: iconst_0
    //   80: invokevirtual 87	java/math/BigDecimal:setScale	(I)Ljava/math/BigDecimal;
    //   83: astore_0
    //   84: iconst_0
    //   85: istore 16
    //   87: aload 18
    //   89: getstatic 130	oracle/sql/NUMBER:BIGDEC_ONE	Ljava/math/BigDecimal;
    //   92: invokevirtual 131	java/math/BigDecimal:compareTo	(Ljava/math/BigDecimal;)I
    //   95: istore 19
    //   97: iconst_0
    //   98: istore 21
    //   100: iload 19
    //   102: iconst_m1
    //   103: if_icmpne +34 -> 137
    //   106: iinc 21 1
    //   109: aload 18
    //   111: iload 21
    //   113: invokevirtual 86	java/math/BigDecimal:movePointRight	(I)Ljava/math/BigDecimal;
    //   116: astore 22
    //   118: aload 22
    //   120: getstatic 130	oracle/sql/NUMBER:BIGDEC_ONE	Ljava/math/BigDecimal;
    //   123: invokevirtual 131	java/math/BigDecimal:compareTo	(Ljava/math/BigDecimal;)I
    //   126: iflt -20 -> 106
    //   129: iload 21
    //   131: ineg
    //   132: istore 20
    //   134: goto +30 -> 164
    //   137: iinc 21 1
    //   140: aload 18
    //   142: iload 21
    //   144: invokevirtual 132	java/math/BigDecimal:movePointLeft	(I)Ljava/math/BigDecimal;
    //   147: astore 22
    //   149: aload 22
    //   151: getstatic 130	oracle/sql/NUMBER:BIGDEC_ONE	Ljava/math/BigDecimal;
    //   154: invokevirtual 131	java/math/BigDecimal:compareTo	(Ljava/math/BigDecimal;)I
    //   157: ifge -20 -> 137
    //   160: iload 21
    //   162: istore 20
    //   164: aload 18
    //   166: iload 16
    //   168: invokevirtual 86	java/math/BigDecimal:movePointRight	(I)Ljava/math/BigDecimal;
    //   171: invokevirtual 133	java/math/BigDecimal:toBigInteger	()Ljava/math/BigInteger;
    //   174: invokevirtual 113	java/math/BigInteger:toByteArray	()[B
    //   177: astore 12
    //   179: aload 12
    //   181: arraylength
    //   182: bipush 54
    //   184: if_icmple +15 -> 199
    //   187: new 29	java/sql/SQLException
    //   190: dup
    //   191: iconst_3
    //   192: invokestatic 51	oracle/core/lmx/CoreException:getMessage	(B)Ljava/lang/String;
    //   195: invokespecial 31	java/sql/SQLException:<init>	(Ljava/lang/String;)V
    //   198: athrow
    //   199: iconst_0
    //   200: istore 15
    //   202: iload 15
    //   204: aload 12
    //   206: arraylength
    //   207: if_icmpge +44 -> 251
    //   210: aload 12
    //   212: iload 15
    //   214: baload
    //   215: ifge +20 -> 235
    //   218: aload_2
    //   219: iload 15
    //   221: aload 12
    //   223: iload 15
    //   225: baload
    //   226: sipush 256
    //   229: iadd
    //   230: i2l
    //   231: lastore
    //   232: goto +13 -> 245
    //   235: aload_2
    //   236: iload 15
    //   238: aload 12
    //   240: iload 15
    //   242: baload
    //   243: i2l
    //   244: lastore
    //   245: iinc 15 1
    //   248: goto -46 -> 202
    //   251: aload 12
    //   253: arraylength
    //   254: istore 6
    //   256: iload 6
    //   258: iconst_3
    //   259: irem
    //   260: lookupswitch	default:+80->340, 1:+59->319, 2:+28->288
    //   289: iload 4
    //   291: aload_2
    //   292: iload 5
    //   294: laload
    //   295: bipush 8
    //   297: lshl
    //   298: aload_2
    //   299: iload 5
    //   301: iconst_1
    //   302: iadd
    //   303: laload
    //   304: ladd
    //   305: lastore
    //   306: iload 5
    //   308: iconst_2
    //   309: iadd
    //   310: i2b
    //   311: istore 5
    //   313: iinc 6 254
    //   316: goto +104 -> 420
    //   319: aload_3
    //   320: iload 4
    //   322: aload_2
    //   323: iload 5
    //   325: laload
    //   326: lastore
    //   327: iload 5
    //   329: iconst_1
    //   330: iadd
    //   331: i2b
    //   332: istore 5
    //   334: iinc 6 255
    //   337: goto +83 -> 420
    //   340: aload_2
    //   341: iload 5
    //   343: laload
    //   344: bipush 16
    //   346: lshl
    //   347: aload_2
    //   348: iload 5
    //   350: iconst_1
    //   351: iadd
    //   352: laload
    //   353: bipush 8
    //   355: lshl
    //   356: ladd
    //   357: aload_2
    //   358: iload 5
    //   360: iconst_2
    //   361: iadd
    //   362: laload
    //   363: ladd
    //   364: lstore 8
    //   366: aload_3
    //   367: iload 4
    //   369: lload 8
    //   371: ldc2_w 119
    //   374: lrem
    //   375: lastore
    //   376: aload_3
    //   377: iload 4
    //   379: iconst_1
    //   380: isub
    //   381: lload 8
    //   383: ldc2_w 119
    //   386: ldiv
    //   387: lastore
    //   388: iload 7
    //   390: aload_3
    //   391: iload 4
    //   393: iconst_1
    //   394: isub
    //   395: laload
    //   396: lconst_0
    //   397: lcmp
    //   398: ifeq +7 -> 405
    //   401: iconst_1
    //   402: goto +4 -> 406
    //   405: iconst_0
    //   406: isub
    //   407: i2b
    //   408: istore 7
    //   410: iload 5
    //   412: iconst_3
    //   413: iadd
    //   414: i2b
    //   415: istore 5
    //   417: iinc 6 253
    //   420: iload 6
    //   422: ifeq +197 -> 619
    //   425: aload_2
    //   426: iload 5
    //   428: laload
    //   429: iconst_4
    //   430: lshl
    //   431: aload_2
    //   432: iload 5
    //   434: iconst_1
    //   435: iadd
    //   436: laload
    //   437: iconst_4
    //   438: lshr
    //   439: ladd
    //   440: lstore 8
    //   442: bipush 21
    //   444: istore 4
    //   446: iload 4
    //   448: iload 7
    //   450: if_icmplt +43 -> 493
    //   453: lload 8
    //   455: aload_3
    //   456: iload 4
    //   458: laload
    //   459: bipush 12
    //   461: lshl
    //   462: ladd
    //   463: lstore 8
    //   465: aload_3
    //   466: iload 4
    //   468: lload 8
    //   470: ldc2_w 119
    //   473: lrem
    //   474: lastore
    //   475: lload 8
    //   477: ldc2_w 119
    //   480: ldiv
    //   481: lstore 8
    //   483: iload 4
    //   485: iconst_1
    //   486: isub
    //   487: i2b
    //   488: istore 4
    //   490: goto -44 -> 446
    //   493: lload 8
    //   495: lconst_0
    //   496: lcmp
    //   497: ifeq +16 -> 513
    //   500: iload 7
    //   502: iconst_1
    //   503: isub
    //   504: i2b
    //   505: istore 7
    //   507: aload_3
    //   508: iload 7
    //   510: lload 8
    //   512: lastore
    //   513: aload_2
    //   514: iload 5
    //   516: iconst_1
    //   517: iadd
    //   518: laload
    //   519: ldc2_w 121
    //   522: land
    //   523: bipush 8
    //   525: lshl
    //   526: aload_2
    //   527: iload 5
    //   529: iconst_2
    //   530: iadd
    //   531: laload
    //   532: ladd
    //   533: lstore 8
    //   535: bipush 21
    //   537: istore 4
    //   539: iload 4
    //   541: iload 7
    //   543: if_icmplt +43 -> 586
    //   546: lload 8
    //   548: aload_3
    //   549: iload 4
    //   551: laload
    //   552: bipush 12
    //   554: lshl
    //   555: ladd
    //   556: lstore 8
    //   558: aload_3
    //   559: iload 4
    //   561: lload 8
    //   563: ldc2_w 119
    //   566: lrem
    //   567: lastore
    //   568: lload 8
    //   570: ldc2_w 119
    //   573: ldiv
    //   574: lstore 8
    //   576: iload 4
    //   578: iconst_1
    //   579: isub
    //   580: i2b
    //   581: istore 4
    //   583: goto -44 -> 539
    //   586: lload 8
    //   588: lconst_0
    //   589: lcmp
    //   590: ifeq +16 -> 606
    //   593: iload 7
    //   595: iconst_1
    //   596: isub
    //   597: i2b
    //   598: istore 7
    //   600: aload_3
    //   601: iload 7
    //   603: lload 8
    //   605: lastore
    //   606: iload 5
    //   608: iconst_3
    //   609: iadd
    //   610: i2b
    //   611: istore 5
    //   613: iinc 6 253
    //   616: goto -196 -> 420
    //   619: aload_1
    //   620: iload 11
    //   622: aload_3
    //   623: iload 7
    //   625: laload
    //   626: ldc2_w 74
    //   629: ldiv
    //   630: l2i
    //   631: i2b
    //   632: dup_x2
    //   633: bastore
    //   634: ifeq +56 -> 690
    //   637: iconst_3
    //   638: bipush 21
    //   640: iload 7
    //   642: isub
    //   643: imul
    //   644: iconst_3
    //   645: iadd
    //   646: istore 10
    //   648: aload_1
    //   649: iload 11
    //   651: iconst_1
    //   652: iadd
    //   653: aload_3
    //   654: iload 7
    //   656: laload
    //   657: ldc2_w 74
    //   660: lrem
    //   661: ldc2_w 123
    //   664: ldiv
    //   665: l2i
    //   666: i2b
    //   667: bastore
    //   668: aload_1
    //   669: iload 11
    //   671: iconst_2
    //   672: iadd
    //   673: aload_3
    //   674: iload 7
    //   676: laload
    //   677: ldc2_w 123
    //   680: lrem
    //   681: l2i
    //   682: i2b
    //   683: bastore
    //   684: iinc 11 3
    //   687: goto +82 -> 769
    //   690: aload_1
    //   691: iload 11
    //   693: aload_3
    //   694: iload 7
    //   696: laload
    //   697: ldc2_w 74
    //   700: lrem
    //   701: ldc2_w 123
    //   704: ldiv
    //   705: l2i
    //   706: i2b
    //   707: dup_x2
    //   708: bastore
    //   709: ifeq +36 -> 745
    //   712: iconst_3
    //   713: bipush 21
    //   715: iload 7
    //   717: isub
    //   718: imul
    //   719: iconst_2
    //   720: iadd
    //   721: istore 10
    //   723: aload_1
    //   724: iload 11
    //   726: iconst_1
    //   727: iadd
    //   728: aload_3
    //   729: iload 7
    //   731: laload
    //   732: ldc2_w 123
    //   735: lrem
    //   736: l2i
    //   737: i2b
    //   738: bastore
    //   739: iinc 11 2
    //   742: goto +27 -> 769
    //   745: aload_1
    //   746: iload 11
    //   748: aload_3
    //   749: iload 7
    //   751: laload
    //   752: l2i
    //   753: i2b
    //   754: bastore
    //   755: iconst_3
    //   756: bipush 21
    //   758: iload 7
    //   760: isub
    //   761: imul
    //   762: iconst_1
    //   763: iadd
    //   764: istore 10
    //   766: iinc 11 1
    //   769: iload 7
    //   771: iconst_1
    //   772: iadd
    //   773: i2b
    //   774: istore 4
    //   776: iload 4
    //   778: bipush 21
    //   780: if_icmpgt +66 -> 846
    //   783: aload_1
    //   784: iload 11
    //   786: aload_3
    //   787: iload 4
    //   789: laload
    //   790: ldc2_w 74
    //   793: ldiv
    //   794: l2i
    //   795: i2b
    //   796: bastore
    //   797: aload_1
    //   798: iload 11
    //   800: iconst_1
    //   801: iadd
    //   802: aload_3
    //   803: iload 4
    //   805: laload
    //   806: ldc2_w 74
    //   809: lrem
    //   810: ldc2_w 123
    //   813: ldiv
    //   814: l2i
    //   815: i2b
    //   816: bastore
    //   817: aload_1
    //   818: iload 11
    //   820: iconst_2
    //   821: iadd
    //   822: aload_3
    //   823: iload 4
    //   825: laload
    //   826: ldc2_w 123
    //   829: lrem
    //   830: l2i
    //   831: i2b
    //   832: bastore
    //   833: iinc 11 3
    //   836: iload 4
    //   838: iconst_1
    //   839: iadd
    //   840: i2b
    //   841: istore 4
    //   843: goto -67 -> 776
    //   846: iload 11
    //   848: iconst_1
    //   849: isub
    //   850: istore 15
    //   852: iload 15
    //   854: iflt +19 -> 873
    //   857: aload_1
    //   858: iload 15
    //   860: baload
    //   861: ifne +12 -> 873
    //   864: iinc 10 255
    //   867: iinc 15 255
    //   870: goto -18 -> 852
    //   873: iload 16
    //   875: ifle +201 -> 1076
    //   878: iload 16
    //   880: iconst_1
    //   881: iand
    //   882: ifeq +194 -> 1076
    //   885: iload 10
    //   887: istore 23
    //   889: iload 23
    //   891: iconst_1
    //   892: iadd
    //   893: newarray byte
    //   895: astore 24
    //   897: aload_1
    //   898: iconst_0
    //   899: baload
    //   900: bipush 9
    //   902: if_icmpgt +79 -> 981
    //   905: iconst_0
    //   906: istore 15
    //   908: iload 15
    //   910: iload 23
    //   912: iconst_1
    //   913: isub
    //   914: if_icmpge +35 -> 949
    //   917: aload 24
    //   919: iload 15
    //   921: aload_1
    //   922: iload 15
    //   924: baload
    //   925: bipush 10
    //   927: irem
    //   928: bipush 10
    //   930: imul
    //   931: aload_1
    //   932: iload 15
    //   934: iconst_1
    //   935: iadd
    //   936: baload
    //   937: bipush 10
    //   939: idiv
    //   940: iadd
    //   941: i2b
    //   942: bastore
    //   943: iinc 15 1
    //   946: goto -38 -> 908
    //   949: aload 24
    //   951: iload 15
    //   953: aload_1
    //   954: iload 15
    //   956: baload
    //   957: bipush 10
    //   959: irem
    //   960: bipush 10
    //   962: imul
    //   963: i2b
    //   964: bastore
    //   965: aload 24
    //   967: iload 23
    //   969: iconst_1
    //   970: isub
    //   971: baload
    //   972: ifne +94 -> 1066
    //   975: iinc 10 255
    //   978: goto +88 -> 1066
    //   981: aload 24
    //   983: iload 23
    //   985: aload_1
    //   986: iload 23
    //   988: iconst_1
    //   989: isub
    //   990: baload
    //   991: bipush 10
    //   993: irem
    //   994: bipush 10
    //   996: imul
    //   997: i2b
    //   998: bastore
    //   999: iload 23
    //   1001: iconst_1
    //   1002: isub
    //   1003: istore 15
    //   1005: iload 15
    //   1007: ifle +35 -> 1042
    //   1010: aload 24
    //   1012: iload 15
    //   1014: aload_1
    //   1015: iload 15
    //   1017: baload
    //   1018: bipush 10
    //   1020: idiv
    //   1021: aload_1
    //   1022: iload 15
    //   1024: iconst_1
    //   1025: isub
    //   1026: baload
    //   1027: bipush 10
    //   1029: irem
    //   1030: bipush 10
    //   1032: imul
    //   1033: iadd
    //   1034: i2b
    //   1035: bastore
    //   1036: iinc 15 255
    //   1039: goto -34 -> 1005
    //   1042: aload 24
    //   1044: iload 15
    //   1046: aload_1
    //   1047: iload 15
    //   1049: baload
    //   1050: bipush 10
    //   1052: idiv
    //   1053: i2b
    //   1054: bastore
    //   1055: aload 24
    //   1057: iload 23
    //   1059: baload
    //   1060: ifle +6 -> 1066
    //   1063: iinc 10 1
    //   1066: aload 24
    //   1068: iconst_0
    //   1069: aload_1
    //   1070: iconst_0
    //   1071: iload 10
    //   1073: invokestatic 125	java/lang/System:arraycopy	(Ljava/lang/Object;ILjava/lang/Object;II)V
    //   1076: iload 10
    //   1078: bipush 20
    //   1080: if_icmple +104 -> 1184
    //   1083: bipush 20
    //   1085: istore 15
    //   1087: bipush 20
    //   1089: istore 10
    //   1091: aload_1
    //   1092: iload 15
    //   1094: baload
    //   1095: bipush 50
    //   1097: if_icmplt +60 -> 1157
    //   1100: iinc 15 255
    //   1103: aload_1
    //   1104: iload 15
    //   1106: dup2
    //   1107: baload
    //   1108: iconst_1
    //   1109: iadd
    //   1110: i2b
    //   1111: bastore
    //   1112: aload_1
    //   1113: iload 15
    //   1115: baload
    //   1116: bipush 100
    //   1118: if_icmpne +39 -> 1157
    //   1121: iload 15
    //   1123: ifne +14 -> 1137
    //   1126: iinc 20 1
    //   1129: aload_1
    //   1130: iload 15
    //   1132: iconst_1
    //   1133: bastore
    //   1134: goto +23 -> 1157
    //   1137: aload_1
    //   1138: iload 15
    //   1140: iconst_0
    //   1141: bastore
    //   1142: iinc 15 255
    //   1145: aload_1
    //   1146: iload 15
    //   1148: dup2
    //   1149: baload
    //   1150: iconst_1
    //   1151: iadd
    //   1152: i2b
    //   1153: bastore
    //   1154: goto -42 -> 1112
    //   1157: iload 10
    //   1159: iconst_1
    //   1160: isub
    //   1161: istore 15
    //   1163: iload 15
    //   1165: iflt +19 -> 1184
    //   1168: aload_1
    //   1169: iload 15
    //   1171: baload
    //   1172: ifne +12 -> 1184
    //   1175: iinc 10 255
    //   1178: iinc 15 255
    //   1181: goto -18 -> 1163
    //   1184: iload 20
    //   1186: ifgt +37 -> 1223
    //   1189: aload_1
    //   1190: iconst_0
    //   1191: baload
    //   1192: bipush 10
    //   1194: if_icmpge +17 -> 1211
    //   1197: iconst_2
    //   1198: iload 20
    //   1200: isub
    //   1201: ineg
    //   1202: iconst_2
    //   1203: idiv
    //   1204: iconst_1
    //   1205: iadd
    //   1206: istore 14
    //   1208: goto +23 -> 1231
    //   1211: iconst_2
    //   1212: iload 20
    //   1214: isub
    //   1215: ineg
    //   1216: iconst_2
    //   1217: idiv
    //   1218: istore 14
    //   1220: goto +11 -> 1231
    //   1223: iload 20
    //   1225: iconst_1
    //   1226: isub
    //   1227: iconst_2
    //   1228: idiv
    //   1229: istore 14
    //   1231: iload 14
    //   1233: bipush 62
    //   1235: if_icmple +15 -> 1250
    //   1238: new 29	java/sql/SQLException
    //   1241: dup
    //   1242: iconst_3
    //   1243: invokestatic 51	oracle/core/lmx/CoreException:getMessage	(B)Ljava/lang/String;
    //   1246: invokespecial 31	java/sql/SQLException:<init>	(Ljava/lang/String;)V
    //   1249: athrow
    //   1250: iload 14
    //   1252: bipush 191
    //   1254: if_icmpgt +15 -> 1269
    //   1257: new 29	java/sql/SQLException
    //   1260: dup
    //   1261: iconst_2
    //   1262: invokestatic 51	oracle/core/lmx/CoreException:getMessage	(B)Ljava/lang/String;
    //   1265: invokespecial 31	java/sql/SQLException:<init>	(Ljava/lang/String;)V
    //   1268: athrow
    //   1269: iload 10
    //   1271: iconst_1
    //   1272: iadd
    //   1273: newarray byte
    //   1275: astore 23
    //   1277: aload 23
    //   1279: iconst_0
    //   1280: iload 14
    //   1282: i2b
    //   1283: bastore
    //   1284: aload_1
    //   1285: iconst_0
    //   1286: aload 23
    //   1288: iconst_1
    //   1289: iload 10
    //   1291: invokestatic 125	java/lang/System:arraycopy	(Ljava/lang/Object;ILjava/lang/Object;II)V
    //   1294: aload 23
    //   1296: iload 13
    //   1298: invokestatic 126	oracle/sql/NUMBER:_toLnxFmt	([BZ)[B
    //   1301: areturn } 
  public static byte[] toBytes(String paramString, int paramInt) throws SQLException { // Byte code:
    //   0: iconst_0
    //   1: istore_2
    //   2: iconst_0
    //   3: istore_3
    //   4: bipush 22
    //   6: newarray byte
    //   8: astore 5
    //   10: iconst_0
    //   11: istore 7
    //   13: iconst_1
    //   14: istore 9
    //   16: iconst_0
    //   17: istore 10
    //   19: iconst_0
    //   20: istore 11
    //   22: iconst_0
    //   23: istore 12
    //   25: iconst_0
    //   26: istore 14
    //   28: iconst_0
    //   29: istore 15
    //   31: bipush 40
    //   33: istore 16
    //   35: iconst_0
    //   36: istore 17
    //   38: iconst_0
    //   39: istore 18
    //   41: iconst_0
    //   42: istore 19
    //   44: iconst_0
    //   45: istore 21
    //   47: aload_0
    //   48: ldc 134
    //   50: invokevirtual 135	java/lang/String:indexOf	(Ljava/lang/String;)I
    //   53: dup
    //   54: istore 22
    //   56: iconst_m1
    //   57: if_icmpne +16 -> 73
    //   60: aload_0
    //   61: ldc 136
    //   63: invokevirtual 135	java/lang/String:indexOf	(Ljava/lang/String;)I
    //   66: dup
    //   67: istore 22
    //   69: iconst_m1
    //   70: if_icmpeq +370 -> 440
    //   73: new 137	java/lang/StringBuffer
    //   76: dup
    //   77: aload_0
    //   78: invokevirtual 138	java/lang/String:length	()I
    //   81: iconst_5
    //   82: iadd
    //   83: invokespecial 139	java/lang/StringBuffer:<init>	(I)V
    //   86: astore 23
    //   88: iconst_0
    //   89: istore 24
    //   91: aconst_null
    //   92: astore 25
    //   94: aload_0
    //   95: iconst_0
    //   96: invokevirtual 140	java/lang/String:charAt	(I)C
    //   99: bipush 45
    //   101: if_icmpne +7 -> 108
    //   104: iconst_1
    //   105: goto +4 -> 109
    //   108: iconst_0
    //   109: istore 26
    //   111: aload_0
    //   112: iload 22
    //   114: iconst_1
    //   115: iadd
    //   116: invokevirtual 141	java/lang/String:substring	(I)Ljava/lang/String;
    //   119: astore 27
    //   121: aload_0
    //   122: iload 26
    //   124: ifeq +7 -> 131
    //   127: iconst_1
    //   128: goto +4 -> 132
    //   131: iconst_0
    //   132: iload 22
    //   134: invokevirtual 142	java/lang/String:substring	(II)Ljava/lang/String;
    //   137: astore 28
    //   139: new 24	java/math/BigDecimal
    //   142: dup
    //   143: aload 28
    //   145: invokespecial 143	java/math/BigDecimal:<init>	(Ljava/lang/String;)V
    //   148: astore 25
    //   150: aload 27
    //   152: iconst_0
    //   153: invokevirtual 140	java/lang/String:charAt	(I)C
    //   156: bipush 45
    //   158: if_icmpne +7 -> 165
    //   161: iconst_1
    //   162: goto +4 -> 166
    //   165: iconst_0
    //   166: istore 29
    //   168: aload 27
    //   170: iconst_1
    //   171: invokevirtual 141	java/lang/String:substring	(I)Ljava/lang/String;
    //   174: astore 27
    //   176: aload 27
    //   178: invokestatic 144	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   181: istore 24
    //   183: aload 25
    //   185: invokevirtual 145	java/math/BigDecimal:toString	()Ljava/lang/String;
    //   188: astore 30
    //   190: aload 30
    //   192: ldc 146
    //   194: invokevirtual 135	java/lang/String:indexOf	(Ljava/lang/String;)I
    //   197: istore 31
    //   199: aload 30
    //   201: invokevirtual 138	java/lang/String:length	()I
    //   204: istore 32
    //   206: iload 32
    //   208: istore 33
    //   210: iload 31
    //   212: iconst_m1
    //   213: if_icmpeq +66 -> 279
    //   216: new 147	java/lang/StringBuilder
    //   219: dup
    //   220: invokespecial 148	java/lang/StringBuilder:<init>	()V
    //   223: aload 30
    //   225: iconst_0
    //   226: iload 31
    //   228: invokevirtual 142	java/lang/String:substring	(II)Ljava/lang/String;
    //   231: invokevirtual 149	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   234: aload 30
    //   236: iload 31
    //   238: iconst_1
    //   239: iadd
    //   240: invokevirtual 141	java/lang/String:substring	(I)Ljava/lang/String;
    //   243: invokevirtual 149	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   246: invokevirtual 150	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   249: astore 30
    //   251: iinc 32 255
    //   254: iload 29
    //   256: ifeq +13 -> 269
    //   259: iload 24
    //   261: iload 31
    //   263: isub
    //   264: istore 24
    //   266: goto +35 -> 301
    //   269: iinc 24 1
    //   272: iload 24
    //   274: istore 33
    //   276: goto +25 -> 301
    //   279: iload 29
    //   281: ifeq +13 -> 294
    //   284: iload 24
    //   286: iload 32
    //   288: isub
    //   289: istore 24
    //   291: goto +10 -> 301
    //   294: iinc 24 1
    //   297: iload 24
    //   299: istore 33
    //   301: iload 26
    //   303: ifeq +11 -> 314
    //   306: aload 23
    //   308: ldc 151
    //   310: invokevirtual 152	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   313: pop
    //   314: iload 29
    //   316: ifeq +46 -> 362
    //   319: aload 23
    //   321: ldc 153
    //   323: invokevirtual 152	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   326: pop
    //   327: iconst_0
    //   328: istore 34
    //   330: iload 34
    //   332: iload 24
    //   334: if_icmpge +17 -> 351
    //   337: aload 23
    //   339: ldc 88
    //   341: invokevirtual 152	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   344: pop
    //   345: iinc 34 1
    //   348: goto -18 -> 330
    //   351: aload 23
    //   353: aload 30
    //   355: invokevirtual 152	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   358: pop
    //   359: goto +75 -> 434
    //   362: iload 24
    //   364: iload 32
    //   366: if_icmple +8 -> 374
    //   369: iload 24
    //   371: goto +5 -> 376
    //   374: iload 32
    //   376: istore 34
    //   378: iconst_0
    //   379: istore 35
    //   381: iload 35
    //   383: iload 34
    //   385: if_icmpge +49 -> 434
    //   388: iload 33
    //   390: iload 35
    //   392: if_icmpne +11 -> 403
    //   395: aload 23
    //   397: ldc 146
    //   399: invokevirtual 152	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   402: pop
    //   403: aload 23
    //   405: iload 32
    //   407: iload 35
    //   409: if_icmple +13 -> 422
    //   412: aload 30
    //   414: iload 35
    //   416: invokevirtual 140	java/lang/String:charAt	(I)C
    //   419: goto +5 -> 424
    //   422: bipush 48
    //   424: invokevirtual 154	java/lang/StringBuffer:append	(C)Ljava/lang/StringBuffer;
    //   427: pop
    //   428: iinc 35 1
    //   431: goto -50 -> 381
    //   434: aload 23
    //   436: invokevirtual 155	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   439: astore_0
    //   440: aload_0
    //   441: invokevirtual 156	java/lang/String:trim	()Ljava/lang/String;
    //   444: astore_0
    //   445: aload_0
    //   446: invokevirtual 138	java/lang/String:length	()I
    //   449: istore 20
    //   451: aload_0
    //   452: iconst_0
    //   453: invokevirtual 140	java/lang/String:charAt	(I)C
    //   456: bipush 45
    //   458: if_icmpne +12 -> 470
    //   461: iinc 20 255
    //   464: iconst_0
    //   465: istore 9
    //   467: iconst_1
    //   468: istore 19
    //   470: iload 20
    //   472: istore_2
    //   473: iload 20
    //   475: newarray char
    //   477: astore 23
    //   479: aload_0
    //   480: iload 19
    //   482: iload 20
    //   484: iload 19
    //   486: iadd
    //   487: aload 23
    //   489: iconst_0
    //   490: invokevirtual 157	java/lang/String:getChars	(II[CI)V
    //   493: iconst_0
    //   494: istore 4
    //   496: iload 4
    //   498: iload 20
    //   500: if_icmpge +25 -> 525
    //   503: aload 23
    //   505: iload 4
    //   507: caload
    //   508: bipush 46
    //   510: if_icmpne +9 -> 519
    //   513: iconst_1
    //   514: istore 11
    //   516: goto +9 -> 525
    //   519: iinc 4 1
    //   522: goto -26 -> 496
    //   525: iload 11
    //   527: ifne +5 -> 532
    //   530: iconst_0
    //   531: istore_1
    //   532: iload_3
    //   533: iload_2
    //   534: if_icmpge +27 -> 561
    //   537: aload 23
    //   539: iload_3
    //   540: caload
    //   541: bipush 48
    //   543: if_icmpne +18 -> 561
    //   546: iinc 3 1
    //   549: iload 11
    //   551: iconst_1
    //   552: if_icmpne -20 -> 532
    //   555: iinc 21 1
    //   558: goto -26 -> 532
    //   561: iload_3
    //   562: iload_2
    //   563: if_icmpne +7 -> 570
    //   566: invokestatic 1	oracle/sql/NUMBER:_makeZero	()[B
    //   569: areturn
    //   570: iload 20
    //   572: iconst_2
    //   573: if_icmplt +232 -> 805
    //   576: aload 23
    //   578: iload_3
    //   579: caload
    //   580: bipush 46
    //   582: if_icmpne +223 -> 805
    //   585: iload_2
    //   586: ifle +20 -> 606
    //   589: aload 23
    //   591: iload_2
    //   592: iconst_1
    //   593: isub
    //   594: caload
    //   595: bipush 48
    //   597: if_icmpne +9 -> 606
    //   600: iinc 2 255
    //   603: goto -18 -> 585
    //   606: iinc 3 1
    //   609: iload_3
    //   610: iload_2
    //   611: if_icmpne +7 -> 618
    //   614: invokestatic 1	oracle/sql/NUMBER:_makeZero	()[B
    //   617: areturn
    //   618: iinc 12 255
    //   621: iload_3
    //   622: iload_2
    //   623: iconst_1
    //   624: isub
    //   625: if_icmpge +35 -> 660
    //   628: aload 23
    //   630: iload_3
    //   631: caload
    //   632: bipush 48
    //   634: if_icmpne +26 -> 660
    //   637: aload 23
    //   639: iload_3
    //   640: iconst_1
    //   641: iadd
    //   642: caload
    //   643: bipush 48
    //   645: if_icmpne +15 -> 660
    //   648: iinc 12 255
    //   651: iinc 15 2
    //   654: iinc 3 2
    //   657: goto -36 -> 621
    //   660: iload 12
    //   662: bipush 191
    //   664: if_icmpge +15 -> 679
    //   667: new 29	java/sql/SQLException
    //   670: dup
    //   671: iconst_2
    //   672: invokestatic 51	oracle/core/lmx/CoreException:getMessage	(B)Ljava/lang/String;
    //   675: invokespecial 31	java/sql/SQLException:<init>	(Ljava/lang/String;)V
    //   678: athrow
    //   679: iload_2
    //   680: iload_3
    //   681: isub
    //   682: iload 16
    //   684: if_icmple +36 -> 720
    //   687: iconst_2
    //   688: iload 16
    //   690: iadd
    //   691: istore 6
    //   693: iload 15
    //   695: ifle +10 -> 705
    //   698: iload 6
    //   700: iload 15
    //   702: iadd
    //   703: istore 6
    //   705: iload 6
    //   707: iload_2
    //   708: if_icmpgt +6 -> 714
    //   711: iload 6
    //   713: istore_2
    //   714: iload_2
    //   715: istore 17
    //   717: iconst_1
    //   718: istore 10
    //   720: iload_2
    //   721: iload_3
    //   722: isub
    //   723: iconst_1
    //   724: ishr
    //   725: istore 7
    //   727: iload_2
    //   728: iload_3
    //   729: isub
    //   730: iconst_2
    //   731: irem
    //   732: ifeq +34 -> 766
    //   735: aload 5
    //   737: iload 7
    //   739: new 27	java/lang/String
    //   742: dup
    //   743: aload 23
    //   745: iload_2
    //   746: iconst_1
    //   747: isub
    //   748: iconst_1
    //   749: invokespecial 93	java/lang/String:<init>	([CII)V
    //   752: invokestatic 144	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   755: bipush 10
    //   757: imul
    //   758: i2b
    //   759: bastore
    //   760: iinc 14 1
    //   763: iinc 2 255
    //   766: iload_2
    //   767: iload_3
    //   768: if_icmple +510 -> 1278
    //   771: iinc 7 255
    //   774: aload 5
    //   776: iload 7
    //   778: new 27	java/lang/String
    //   781: dup
    //   782: aload 23
    //   784: iload_2
    //   785: iconst_2
    //   786: isub
    //   787: iconst_2
    //   788: invokespecial 93	java/lang/String:<init>	([CII)V
    //   791: invokestatic 144	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   794: i2b
    //   795: bastore
    //   796: iinc 2 254
    //   799: iinc 14 1
    //   802: goto -36 -> 766
    //   805: iload_1
    //   806: ifle +27 -> 833
    //   809: iload_2
    //   810: ifle +23 -> 833
    //   813: aload 23
    //   815: iload_2
    //   816: iconst_1
    //   817: isub
    //   818: caload
    //   819: bipush 48
    //   821: if_icmpne +12 -> 833
    //   824: iinc 2 255
    //   827: iinc 1 255
    //   830: goto -25 -> 805
    //   833: iload_1
    //   834: ifne +67 -> 901
    //   837: iload_2
    //   838: iconst_1
    //   839: if_icmple +62 -> 901
    //   842: aload 23
    //   844: iload_2
    //   845: iconst_1
    //   846: isub
    //   847: caload
    //   848: bipush 46
    //   850: if_icmpne +6 -> 856
    //   853: iinc 2 255
    //   856: iload_3
    //   857: iload_2
    //   858: if_icmpne +7 -> 865
    //   861: invokestatic 1	oracle/sql/NUMBER:_makeZero	()[B
    //   864: areturn
    //   865: iload_2
    //   866: iconst_1
    //   867: if_icmple +34 -> 901
    //   870: aload 23
    //   872: iload_2
    //   873: iconst_2
    //   874: isub
    //   875: caload
    //   876: bipush 48
    //   878: if_icmpne +23 -> 901
    //   881: aload 23
    //   883: iload_2
    //   884: iconst_1
    //   885: isub
    //   886: caload
    //   887: bipush 48
    //   889: if_icmpne +12 -> 901
    //   892: iinc 2 254
    //   895: iinc 12 1
    //   898: goto -33 -> 865
    //   901: iload 12
    //   903: bipush 62
    //   905: if_icmple +15 -> 920
    //   908: new 29	java/sql/SQLException
    //   911: dup
    //   912: iconst_3
    //   913: invokestatic 51	oracle/core/lmx/CoreException:getMessage	(B)Ljava/lang/String;
    //   916: invokespecial 31	java/sql/SQLException:<init>	(Ljava/lang/String;)V
    //   919: athrow
    //   920: iload_2
    //   921: iload_3
    //   922: isub
    //   923: iload 11
    //   925: ifeq +7 -> 932
    //   928: iconst_1
    //   929: goto +4 -> 933
    //   932: iconst_0
    //   933: isub
    //   934: iload 16
    //   936: if_icmple +44 -> 980
    //   939: iload 16
    //   941: iload 11
    //   943: ifeq +7 -> 950
    //   946: iconst_1
    //   947: goto +4 -> 951
    //   950: iconst_0
    //   951: iadd
    //   952: istore 6
    //   954: iload_2
    //   955: iload 6
    //   957: isub
    //   958: istore 24
    //   960: iload 6
    //   962: istore_2
    //   963: iload_1
    //   964: iload 24
    //   966: isub
    //   967: istore_1
    //   968: iload_1
    //   969: ifge +5 -> 974
    //   972: iconst_0
    //   973: istore_1
    //   974: iconst_1
    //   975: istore 10
    //   977: iload_2
    //   978: istore 17
    //   980: iload_1
    //   981: ifne +9 -> 990
    //   984: iload_2
    //   985: iload_3
    //   986: isub
    //   987: goto +8 -> 995
    //   990: iload_2
    //   991: iload_1
    //   992: isub
    //   993: iconst_1
    //   994: isub
    //   995: istore 13
    //   997: iload 21
    //   999: ifle +10 -> 1009
    //   1002: iload 13
    //   1004: iload 21
    //   1006: isub
    //   1007: istore 13
    //   1009: iload 13
    //   1011: iconst_2
    //   1012: irem
    //   1013: ifeq +48 -> 1061
    //   1016: new 27	java/lang/String
    //   1019: dup
    //   1020: aload 23
    //   1022: iload_3
    //   1023: iconst_1
    //   1024: invokespecial 93	java/lang/String:<init>	([CII)V
    //   1027: invokestatic 144	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   1030: istore 18
    //   1032: iinc 3 1
    //   1035: iinc 13 255
    //   1038: iload_2
    //   1039: iconst_1
    //   1040: isub
    //   1041: iload 16
    //   1043: if_icmpne +40 -> 1083
    //   1046: iinc 1 255
    //   1049: iinc 2 255
    //   1052: iconst_1
    //   1053: istore 10
    //   1055: iload_2
    //   1056: istore 17
    //   1058: goto +25 -> 1083
    //   1061: new 27	java/lang/String
    //   1064: dup
    //   1065: aload 23
    //   1067: iload_3
    //   1068: iconst_2
    //   1069: invokespecial 93	java/lang/String:<init>	([CII)V
    //   1072: invokestatic 144	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   1075: istore 18
    //   1077: iinc 3 2
    //   1080: iinc 13 254
    //   1083: aload 5
    //   1085: iload 7
    //   1087: iload 18
    //   1089: i2b
    //   1090: bastore
    //   1091: iinc 7 1
    //   1094: iinc 14 1
    //   1097: iload 13
    //   1099: ifle +41 -> 1140
    //   1102: aload 5
    //   1104: iload 7
    //   1106: new 27	java/lang/String
    //   1109: dup
    //   1110: aload 23
    //   1112: iload_3
    //   1113: iconst_2
    //   1114: invokespecial 93	java/lang/String:<init>	([CII)V
    //   1117: invokestatic 144	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   1120: i2b
    //   1121: bastore
    //   1122: iinc 7 1
    //   1125: iinc 3 2
    //   1128: iinc 12 1
    //   1131: iinc 13 254
    //   1134: iinc 14 1
    //   1137: goto -40 -> 1097
    //   1140: iload_3
    //   1141: iload_2
    //   1142: if_icmpge +95 -> 1237
    //   1145: iload_1
    //   1146: iconst_2
    //   1147: irem
    //   1148: ifeq +45 -> 1193
    //   1151: iload 7
    //   1153: iload_1
    //   1154: iconst_2
    //   1155: idiv
    //   1156: iadd
    //   1157: istore 7
    //   1159: aload 5
    //   1161: iload 7
    //   1163: new 27	java/lang/String
    //   1166: dup
    //   1167: aload 23
    //   1169: iload_2
    //   1170: iconst_1
    //   1171: isub
    //   1172: iconst_1
    //   1173: invokespecial 93	java/lang/String:<init>	([CII)V
    //   1176: invokestatic 144	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   1179: bipush 10
    //   1181: imul
    //   1182: i2b
    //   1183: bastore
    //   1184: iinc 2 255
    //   1187: iinc 1 255
    //   1190: goto +41 -> 1231
    //   1193: iload 7
    //   1195: iload_1
    //   1196: iconst_2
    //   1197: idiv
    //   1198: iconst_1
    //   1199: isub
    //   1200: iadd
    //   1201: istore 7
    //   1203: aload 5
    //   1205: iload 7
    //   1207: new 27	java/lang/String
    //   1210: dup
    //   1211: aload 23
    //   1213: iload_2
    //   1214: iconst_2
    //   1215: isub
    //   1216: iconst_2
    //   1217: invokespecial 93	java/lang/String:<init>	([CII)V
    //   1220: invokestatic 144	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   1223: i2b
    //   1224: bastore
    //   1225: iinc 2 254
    //   1228: iinc 1 254
    //   1231: iinc 14 1
    //   1234: iinc 7 255
    //   1237: iload_1
    //   1238: ifle +40 -> 1278
    //   1241: aload 5
    //   1243: iload 7
    //   1245: new 27	java/lang/String
    //   1248: dup
    //   1249: aload 23
    //   1251: iload_2
    //   1252: iconst_2
    //   1253: isub
    //   1254: iconst_2
    //   1255: invokespecial 93	java/lang/String:<init>	([CII)V
    //   1258: invokestatic 144	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   1261: i2b
    //   1262: bastore
    //   1263: iinc 7 255
    //   1266: iinc 2 254
    //   1269: iinc 1 254
    //   1272: iinc 14 1
    //   1275: goto -38 -> 1237
    //   1278: iload 10
    //   1280: ifeq +120 -> 1400
    //   1283: iload 14
    //   1285: istore 24
    //   1287: new 27	java/lang/String
    //   1290: dup
    //   1291: aload 23
    //   1293: iload 17
    //   1295: iconst_1
    //   1296: invokespecial 93	java/lang/String:<init>	([CII)V
    //   1299: invokestatic 144	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   1302: istore 18
    //   1304: iload 18
    //   1306: iconst_5
    //   1307: if_icmplt +93 -> 1400
    //   1310: iinc 24 255
    //   1313: aload 5
    //   1315: iload 24
    //   1317: dup2
    //   1318: baload
    //   1319: iconst_1
    //   1320: iadd
    //   1321: i2b
    //   1322: bastore
    //   1323: aload 5
    //   1325: iload 24
    //   1327: baload
    //   1328: bipush 100
    //   1330: if_icmpne +42 -> 1372
    //   1333: iload 24
    //   1335: ifne +15 -> 1350
    //   1338: iinc 12 1
    //   1341: aload 5
    //   1343: iload 24
    //   1345: iconst_1
    //   1346: bastore
    //   1347: goto +25 -> 1372
    //   1350: aload 5
    //   1352: iload 24
    //   1354: iconst_0
    //   1355: bastore
    //   1356: iinc 24 255
    //   1359: aload 5
    //   1361: iload 24
    //   1363: dup2
    //   1364: baload
    //   1365: iconst_1
    //   1366: iadd
    //   1367: i2b
    //   1368: bastore
    //   1369: goto -46 -> 1323
    //   1372: iload 14
    //   1374: iconst_1
    //   1375: isub
    //   1376: istore 4
    //   1378: iload 4
    //   1380: iflt +20 -> 1400
    //   1383: aload 5
    //   1385: iload 4
    //   1387: baload
    //   1388: ifne +12 -> 1400
    //   1391: iinc 14 255
    //   1394: iinc 4 255
    //   1397: goto -19 -> 1378
    //   1400: iload 14
    //   1402: iconst_1
    //   1403: iadd
    //   1404: newarray byte
    //   1406: astore 24
    //   1408: aload 24
    //   1410: iconst_0
    //   1411: iload 12
    //   1413: i2b
    //   1414: bastore
    //   1415: aload 5
    //   1417: iconst_0
    //   1418: aload 24
    //   1420: iconst_1
    //   1421: iload 14
    //   1423: invokestatic 125	java/lang/System:arraycopy	(Ljava/lang/Object;ILjava/lang/Object;II)V
    //   1426: aload 24
    //   1428: iload 9
    //   1430: invokestatic 126	oracle/sql/NUMBER:_toLnxFmt	([BZ)[B
    //   1433: areturn } 
  public static byte[] toBytes(boolean paramBoolean) { if (paramBoolean) {
      return toBytes(1L);
    }
    return toBytes(0L);
  }

  public byte[] toBytes()
  {
    return getBytes();
  }

  public double doubleValue()
  {
    return toDouble(shareBytes());
  }

  public float floatValue()
  {
    return toFloat(shareBytes());
  }

  public long longValue()
    throws SQLException
  {
    return toLong(shareBytes());
  }

  public int intValue()
    throws SQLException
  {
    return toInt(shareBytes());
  }

  public short shortValue()
    throws SQLException
  {
    return toShort(shareBytes());
  }

  public byte byteValue()
    throws SQLException
  {
    return toByte(shareBytes());
  }

  public BigInteger bigIntegerValue()
    throws SQLException
  {
    return toBigInteger(shareBytes());
  }

  public BigDecimal bigDecimalValue()
    throws SQLException
  {
    return toBigDecimal(shareBytes());
  }

  public String stringValue()
  {
    return toString(shareBytes());
  }

  public boolean booleanValue()
  {
    return toBoolean(shareBytes());
  }

  public Object toJdbc()
    throws SQLException
  {
    try
    {
      return bigDecimalValue();
    }
    catch (SQLException localSQLException)
    {
      return new SQLException(localSQLException.getMessage());
    }
  }

  public Object makeJdbcArray(int paramInt)
  {
    BigDecimal[] arrayOfBigDecimal = new BigDecimal[paramInt];

    return arrayOfBigDecimal;
  }

  public boolean isConvertibleTo(Class paramClass)
  {
    String str = paramClass.getName();

    if ((str.compareTo("java.lang.Integer") == 0) || (str.compareTo("java.lang.Long") == 0) || (str.compareTo("java.lang.Float") == 0) || (str.compareTo("java.lang.Double") == 0) || (str.compareTo("java.math.BigInteger") == 0) || (str.compareTo("java.math.BigDecimal") == 0) || (str.compareTo("java.lang.String") == 0) || (str.compareTo("java.lang.Boolean") == 0))
    {
      return true;
    }

    return false;
  }

  public NUMBER abs()
    throws SQLException
  {
    return new NUMBER(_getLnxLib().lnxabs(shareBytes()));
  }

  public NUMBER acos()
    throws SQLException
  {
    return new NUMBER(_getLnxLib().lnxacos(shareBytes()));
  }

  public NUMBER add(NUMBER paramNUMBER)
    throws SQLException
  {
    return new NUMBER(_getLnxLib().lnxadd(shareBytes(), paramNUMBER.shareBytes()));
  }

  public NUMBER asin()
    throws SQLException
  {
    return new NUMBER(_getLnxLib().lnxasin(shareBytes()));
  }

  public NUMBER atan()
    throws SQLException
  {
    return new NUMBER(_getLnxLib().lnxatan(shareBytes()));
  }

  public NUMBER atan2(NUMBER paramNUMBER)
    throws SQLException
  {
    return new NUMBER(_getLnxLib().lnxatan2(shareBytes(), paramNUMBER.shareBytes()));
  }

  public NUMBER ceil()
    throws SQLException
  {
    return new NUMBER(_getLnxLib().lnxceil(shareBytes()));
  }

  public NUMBER cos()
    throws SQLException
  {
    return new NUMBER(_getLnxLib().lnxcos(shareBytes()));
  }

  public NUMBER cosh()
    throws SQLException
  {
    return new NUMBER(_getLnxLib().lnxcsh(shareBytes()));
  }

  public NUMBER decrement()
    throws SQLException
  {
    return new NUMBER(_getLnxLib().lnxdec(shareBytes()));
  }

  public NUMBER div(NUMBER paramNUMBER)
    throws SQLException
  {
    return new NUMBER(_getLnxLib().lnxdiv(shareBytes(), paramNUMBER.shareBytes()));
  }

  public NUMBER exp()
    throws SQLException
  {
    return new NUMBER(_getLnxLib().lnxexp(shareBytes()));
  }

  public NUMBER floatingPointRound(int paramInt)
    throws SQLException
  {
    return new NUMBER(_getLnxLib().lnxfpr(shareBytes(), paramInt));
  }

  public NUMBER floor()
    throws SQLException
  {
    return new NUMBER(_getLnxLib().lnxflo(shareBytes()));
  }

  public NUMBER increment()
    throws SQLException
  {
    return new NUMBER(_getLnxLib().lnxinc(shareBytes()));
  }

  public NUMBER ln()
    throws SQLException
  {
    return new NUMBER(_getLnxLib().lnxln(shareBytes()));
  }

  public NUMBER log(NUMBER paramNUMBER)
    throws SQLException
  {
    return new NUMBER(_getLnxLib().lnxlog(shareBytes(), paramNUMBER.shareBytes()));
  }

  public NUMBER mod(NUMBER paramNUMBER)
    throws SQLException
  {
    return new NUMBER(_getLnxLib().lnxmod(shareBytes(), paramNUMBER.shareBytes()));
  }

  public NUMBER mul(NUMBER paramNUMBER)
    throws SQLException
  {
    return new NUMBER(_getLnxLib().lnxmul(shareBytes(), paramNUMBER.shareBytes()));
  }

  public NUMBER negate()
    throws SQLException
  {
    return new NUMBER(_getLnxLib().lnxneg(shareBytes()));
  }

  public NUMBER pow(NUMBER paramNUMBER)
    throws SQLException
  {
    return new NUMBER(_getLnxLib().lnxbex(shareBytes(), paramNUMBER.shareBytes()));
  }

  public NUMBER pow(int paramInt)
    throws SQLException
  {
    return new NUMBER(_getLnxLib().lnxpow(shareBytes(), paramInt));
  }

  public NUMBER round(int paramInt)
    throws SQLException
  {
    return new NUMBER(_getLnxLib().lnxrou(shareBytes(), paramInt));
  }

  public NUMBER scale(int paramInt1, int paramInt2, boolean[] paramArrayOfBoolean)
    throws SQLException
  {
    return new NUMBER(_getLnxLib().lnxsca(shareBytes(), paramInt1, paramInt2, paramArrayOfBoolean));
  }

  public NUMBER shift(int paramInt)
    throws SQLException
  {
    return new NUMBER(_getLnxLib().lnxshift(shareBytes(), paramInt));
  }

  public NUMBER sin()
    throws SQLException
  {
    return new NUMBER(_getLnxLib().lnxsin(shareBytes()));
  }

  public NUMBER sinh()
    throws SQLException
  {
    return new NUMBER(_getLnxLib().lnxsnh(shareBytes()));
  }

  public NUMBER sqroot()
    throws SQLException
  {
    return new NUMBER(_getLnxLib().lnxsqr(shareBytes()));
  }

  public NUMBER sub(NUMBER paramNUMBER)
    throws SQLException
  {
    return new NUMBER(_getLnxLib().lnxsub(shareBytes(), paramNUMBER.shareBytes()));
  }

  public NUMBER tan()
    throws SQLException
  {
    return new NUMBER(_getLnxLib().lnxtan(shareBytes()));
  }

  public NUMBER tanh()
    throws SQLException
  {
    return new NUMBER(_getLnxLib().lnxtnh(shareBytes()));
  }

  public NUMBER truncate(int paramInt)
    throws SQLException
  {
    return new NUMBER(_getLnxLib().lnxtru(shareBytes(), paramInt));
  }

  public static NUMBER formattedTextToNumber(String paramString1, String paramString2, String paramString3)
    throws SQLException
  {
    return new NUMBER(_getLnxLib().lnxfcn(paramString1, paramString2, paramString3));
  }

  public static NUMBER textToPrecisionNumber(String paramString1, boolean paramBoolean1, int paramInt1, boolean paramBoolean2, int paramInt2, String paramString2)
    throws SQLException
  {
    return new NUMBER(_getLnxLib().lnxcpn(paramString1, paramBoolean1, paramInt1, paramBoolean2, paramInt2, paramString2));
  }

  public String toFormattedText(String paramString1, String paramString2)
    throws SQLException
  {
    return _getLnxLib().lnxnfn(shareBytes(), paramString1, paramString2);
  }

  public String toText(int paramInt, String paramString)
    throws SQLException
  {
    return _getLnxLib().lnxnuc(shareBytes(), paramInt, paramString);
  }

  public int compareTo(NUMBER paramNUMBER)
  {
    return compareBytes(shareBytes(), paramNUMBER.shareBytes());
  }

  public boolean isInf()
  {
    return _isInf(shareBytes());
  }

  public boolean isNegInf()
  {
    return _isNegInf(shareBytes());
  }

  public boolean isPosInf()
  {
    return _isPosInf(shareBytes());
  }

  public boolean isInt()
  {
    return _isInt(shareBytes());
  }

  public static boolean isValid(byte[] paramArrayOfByte)
  {
    int i = (byte)paramArrayOfByte.length;
    int j;
    if (_isPositive(paramArrayOfByte))
    {
      if (i == 1) {
        return _isZero(paramArrayOfByte);
      }

      if ((paramArrayOfByte[0] == -1) && (paramArrayOfByte[1] == 101))
      {
        return i == 2;
      }
      if (i > 21) {
        return false;
      }

      if ((paramArrayOfByte[1] < 2) || (paramArrayOfByte[(i - 1)] < 2)) {
        return false;
      }

      for (k = 1; k < i; k++)
      {
        j = paramArrayOfByte[k];
        if ((j < 1) || (j > 100)) {
          return false;
        }
      }
      return true;
    }

    if (i < 3) {
      return _isNegInf(paramArrayOfByte);
    }
    if (i > 21) {
      return false;
    }

    if (paramArrayOfByte[(i - 1)] != 102)
    {
      if (i <= 20) {
        return false;
      }
    }
    else {
      i = (byte)(i - 1);
    }

    if ((paramArrayOfByte[1] > 100) || (paramArrayOfByte[(i - 1)] > 100)) {
      return false;
    }

    for (int k = 1; k < i; k++)
    {
      j = paramArrayOfByte[k];

      if ((j < 2) || (j > 101)) {
        return false;
      }
    }
    return true;
  }

  public boolean isZero()
  {
    return _isZero(shareBytes());
  }

  public static NUMBER e()
  {
    return new NUMBER(E);
  }

  public static NUMBER ln10()
  {
    return new NUMBER(LN10);
  }

  public static NUMBER negInf()
  {
    return new NUMBER(_makeNegInf());
  }

  public static NUMBER pi()
  {
    return new NUMBER(PI);
  }

  public static NUMBER posInf()
  {
    return new NUMBER(_makePosInf());
  }

  public static NUMBER zero()
  {
    return new NUMBER(_makeZero());
  }

  public int sign()
  {
    if (_isZero(shareBytes())) {
      return 0;
    }
    return _isPositive(shareBytes()) ? 1 : -1;
  }

  static boolean _isInf(byte[] paramArrayOfByte)
  {
    if (((paramArrayOfByte.length == 2) && (paramArrayOfByte[0] == -1) && (paramArrayOfByte[1] == 101)) || ((paramArrayOfByte[0] == 0) && (paramArrayOfByte.length == 1)))
    {
      return true;
    }
    return false;
  }

  private static boolean _isInt(byte[] paramArrayOfByte)
  {
    if (_isZero(paramArrayOfByte)) {
      return true;
    }
    if (_isInf(paramArrayOfByte)) {
      return false;
    }
    byte[] arrayOfByte = _fromLnxFmt(paramArrayOfByte);
    int i = arrayOfByte[0];
    int j = (byte)(arrayOfByte.length - 1);

    if (j > i + 1) {
      return false;
    }
    return true;
  }

  static boolean _isNegInf(byte[] paramArrayOfByte)
  {
    if ((paramArrayOfByte[0] == 0) && (paramArrayOfByte.length == 1)) {
      return true;
    }
    return false;
  }

  static boolean _isPosInf(byte[] paramArrayOfByte)
  {
    if ((paramArrayOfByte.length == 2) && (paramArrayOfByte[0] == -1) && (paramArrayOfByte[1] == 101))
    {
      return true;
    }
    return false;
  }

  static boolean _isPositive(byte[] paramArrayOfByte)
  {
    if ((paramArrayOfByte[0] & 0xFFFFFF80) != 0) {
      return true;
    }
    return false;
  }

  static boolean _isZero(byte[] paramArrayOfByte)
  {
    if ((paramArrayOfByte[0] == -128) && (paramArrayOfByte.length == 1)) {
      return true;
    }
    return false;
  }

  static byte[] _makePosInf()
  {
    byte[] arrayOfByte = new byte[2];

    arrayOfByte[0] = -1;
    arrayOfByte[1] = 101;

    return arrayOfByte;
  }

  static byte[] _makeNegInf()
  {
    byte[] arrayOfByte = new byte[1];

    arrayOfByte[0] = 0;

    return arrayOfByte;
  }

  static byte[] _makeZero()
  {
    byte[] arrayOfByte = new byte[1];

    arrayOfByte[0] = -128;

    return arrayOfByte;
  }

  static byte[] _fromLnxFmt(byte[] paramArrayOfByte)
  {
    int j = paramArrayOfByte.length;
    byte[] arrayOfByte;
    if (_isPositive(paramArrayOfByte))
    {
      arrayOfByte = new byte[j];
      arrayOfByte[0] = ((byte)((paramArrayOfByte[0] & 0xFFFFFF7F) - 65));

      for (i = 1; i < j; i++) {
        arrayOfByte[i] = ((byte)(paramArrayOfByte[i] - 1));
      }
    }

    if ((j - 1 == 20) && (paramArrayOfByte[(j - 1)] != 102))
    {
      arrayOfByte = new byte[j];
    }
    else arrayOfByte = new byte[j - 1];

    arrayOfByte[0] = ((byte)(((paramArrayOfByte[0] ^ 0xFFFFFFFF) & 0xFFFFFF7F) - 65));

    for (int i = 1; i < arrayOfByte.length; i++) {
      arrayOfByte[i] = ((byte)(101 - paramArrayOfByte[i]));
    }

    return arrayOfByte;
  }

  static byte[] _toLnxFmt(byte[] paramArrayOfByte, boolean paramBoolean)
  {
    int j = paramArrayOfByte.length;
    byte[] arrayOfByte;
    if (paramBoolean)
    {
      arrayOfByte = new byte[j];
      arrayOfByte[0] = ((byte)(paramArrayOfByte[0] + 128 + 64 + 1));

      for (i = 1; i < j; i++) {
        arrayOfByte[i] = ((byte)(paramArrayOfByte[i] + 1));
      }

    }

    if (j - 1 < 20)
      arrayOfByte = new byte[j + 1];
    else {
      arrayOfByte = new byte[j];
    }
    arrayOfByte[0] = ((byte)(paramArrayOfByte[0] + 128 + 64 + 1 ^ 0xFFFFFFFF));

    for (int i = 1; i < j; i++) {
      arrayOfByte[i] = ((byte)(101 - paramArrayOfByte[i]));
    }
    if (i <= 20) {
      arrayOfByte[i] = 102;
    }

    return arrayOfByte;
  }

  private static LnxLib _getLnxLib()
  {
    if (_slnxlib == null)
    {
      try
      {
        if (System.getProperty("oracle.jserver.version") != null)
        {
          _slnxlib = new LnxLibServer();
        }
        else
        {
          _slnxlib = new LnxLibThin();
        }
      }
      catch (SecurityException localSecurityException)
      {
        _slnxlib = new LnxLibThin();
      }
    }

    return _slnxlib;
  }

  private static LnxLib _getThinLib()
  {
    if (_thinlib == null)
    {
      _thinlib = new LnxLibThin();
    }

    return _thinlib;
  }

  private static int _byteToChars(byte paramByte, char[] paramArrayOfChar, int paramInt)
  {
    if (paramByte < 0)
    {
      return 0;
    }
    if (paramByte < 10)
    {
      paramArrayOfChar[paramInt] = ((char)(48 + paramByte));
      return 1;
    }
    if (paramByte < 100)
    {
      paramArrayOfChar[paramInt] = ((char)(48 + paramByte / 10));
      paramArrayOfChar[(paramInt + 1)] = ((char)(48 + paramByte % 10));
      return 2;
    }

    paramArrayOfChar[paramInt] = '1';
    paramArrayOfChar[(paramInt + 1)] = ((char)(48 + paramByte / 10 - 10));
    paramArrayOfChar[(paramInt + 2)] = ((char)(48 + paramByte % 10));
    return 3;
  }

  private static void _byteTo2Chars(byte paramByte, char[] paramArrayOfChar, int paramInt)
  {
    if (paramByte < 0)
    {
      paramArrayOfChar[paramInt] = '0';
      paramArrayOfChar[(paramInt + 1)] = '0';
    }
    else if (paramByte < 10)
    {
      paramArrayOfChar[paramInt] = '0';
      paramArrayOfChar[(paramInt + 1)] = ((char)(48 + paramByte));
    }
    else if (paramByte < 100)
    {
      paramArrayOfChar[paramInt] = ((char)(48 + paramByte / 10));
      paramArrayOfChar[(paramInt + 1)] = ((char)(48 + paramByte % 10));
    }
    else
    {
      paramArrayOfChar[paramInt] = '0';
      paramArrayOfChar[(paramInt + 1)] = '0';
    }
  }

  private static void _printBytes(byte[] paramArrayOfByte)
  {
    int i = paramArrayOfByte.length;

    System.out.print(i + ": ");

    for (int j = 0; j < i; j++)
    {
      System.out.print(paramArrayOfByte[j] + " ");
    }

    System.out.println();
  }

  private byte[] stringToBytes(String paramString)
    throws SQLException
  {
    int i = 0;

    paramString = paramString.trim();

    if (paramString.indexOf(46) >= 0)
    {
      i = paramString.length() - 1 - paramString.indexOf(46);
    }

    return toBytes(paramString, i);
  }

  static
  {
    try
    {
      drvType = System.getProperty("oracle.jserver.version");
    }
    catch (SecurityException localSecurityException)
    {
      drvType = null;
    }
  }
}