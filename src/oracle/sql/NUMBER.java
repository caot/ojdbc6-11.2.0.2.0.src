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
  
  public static byte[] toBytes(BigInteger biginteger)
      throws SQLException
  {
      byte abyte0[] = new byte[66];
      long al[] = new long[54];
      long al1[] = new long[22];
      byte byte0 = 21;
      byte byte4 = 0;
      byte byte5 = 21;
      boolean flag = false;
      int k = 0;
      boolean flag1 = true;
      if(biginteger.signum() == 0)
          return _makeZero();
      byte abyte1[];
      int i1;
      if(biginteger.signum() == -1)
      {
          BigInteger biginteger1 = biginteger.abs();
          flag1 = false;
          abyte1 = biginteger1.toByteArray();
          i1 = (int)Math.floor((double)biginteger1.bitLength() * 0.1505149978319906D);
      } else
      {
          abyte1 = biginteger.toByteArray();
          i1 = (int)Math.floor((double)biginteger.bitLength() * 0.1505149978319906D);
      }
      if(biginteger.abs().compareTo(BIGINT_HUND.pow(i1)) < 0)
          i1--;
      if(abyte1.length > 54)
          throw new SQLException(CoreException.getMessage((byte)3));
      for(int j1 = 0; j1 < abyte1.length; j1++)
          if(abyte1[j1] < 0)
              al[j1] = abyte1[j1] + 256;
          else
              al[j1] = abyte1[j1];

      int i = abyte1.length;
      switch(i % 3)
      {
      case 2: // '\002'
          al1[byte0] = (al[byte4] << 8) + al[byte4 + 1];
          byte4 += 2;
          i -= 2;
          break;

      case 1: // '\001'
          al1[byte0] = al[byte4];
          byte4++;
          i--;
          break;

      default:
          long l = (al[byte4] << 16) + (al[byte4 + 1] << 8) + al[byte4 + 2];
          al1[byte0] = l % 0xf4240L;
          al1[byte0 - 1] = l / 0xf4240L;
          byte5 -= ((byte)(al1[byte0 - 1] == 0L ? 0 : 1));
          byte4 += 3;
          i -= 3;
          break;
      }
      for(; i != 0; i -= 3)
      {
          long l1 = (al[byte4] << 4) + (al[byte4 + 1] >> 4);
          for(byte byte1 = 21; byte1 >= byte5; byte1--)
          {
              l1 += al1[byte1] << 12;
              al1[byte1] = l1 % 0xf4240L;
              l1 /= 0xf4240L;
          }

          if(l1 != 0L)
          {
              byte5--;
              al1[byte5] = l1;
          }
          l1 = ((al[byte4 + 1] & 15L) << 8) + al[byte4 + 2];
          for(byte byte2 = 21; byte2 >= byte5; byte2--)
          {
              l1 += al1[byte2] << 12;
              al1[byte2] = l1 % 0xf4240L;
              l1 /= 0xf4240L;
          }

          if(l1 != 0L)
          {
              byte5--;
              al1[byte5] = l1;
          }
          byte4 += 3;
      }

      int j;
      if((abyte0[k] = (byte)(int)(al1[byte5] / 10000L)) != 0)
      {
          j = 3 * (21 - byte5) + 3;
          abyte0[k + 1] = (byte)(int)((al1[byte5] % 10000L) / 100L);
          abyte0[k + 2] = (byte)(int)(al1[byte5] % 100L);
          k += 3;
      } else
      if((abyte0[k] = (byte)(int)((al1[byte5] % 10000L) / 100L)) != 0)
      {
          j = 3 * (21 - byte5) + 2;
          abyte0[k + 1] = (byte)(int)(al1[byte5] % 100L);
          k += 2;
      } else
      {
          abyte0[k] = (byte)(int)al1[byte5];
          j = 3 * (21 - byte5) + 1;
          k++;
      }
      for(byte byte3 = (byte)(byte5 + 1); byte3 <= 21; byte3++)
      {
          abyte0[k] = (byte)(int)(al1[byte3] / 10000L);
          abyte0[k + 1] = (byte)(int)((al1[byte3] % 10000L) / 100L);
          abyte0[k + 2] = (byte)(int)(al1[byte3] % 100L);
          k += 3;
      }

      for(int k1 = k - 1; k1 >= 0 && abyte0[k1] == 0; k1--)
          j--;

      if(j > 19)
      {
          int i2 = 20;
          j = 19;
          if(abyte0[i2] >= 50)
          {
              i2--;
              abyte0[i2]++;
              do
              {
                  if(abyte0[i2] != 100)
                      break;
                  if(i2 == 0)
                  {
                      i1++;
                      abyte0[i2] = 1;
                      break;
                  }
                  abyte0[i2] = 0;
                  i2--;
                  abyte0[i2]++;
              } while(true);
              for(int j2 = j - 1; j2 >= 0 && abyte0[j2] == 0; j2--)
                  j--;

          }
      }
      if(i1 > 62)
      {
          throw new SQLException(CoreException.getMessage((byte)3));
      } else
      {
          byte abyte2[] = new byte[j + 1];
          abyte2[0] = (byte)i1;
          System.arraycopy(abyte0, 0, abyte2, 1, j);
          return _toLnxFmt(abyte2, flag1);
      }
  }
  
  public static byte[] toBytes(BigDecimal bigdecimal)
      throws SQLException
  {
      byte abyte0[] = new byte[66];
      long al[] = new long[54];
      long al1[] = new long[22];
      byte byte0 = 21;
      byte byte4 = 0;
      byte byte5 = 21;
      int k = 0;
      int i1 = 0;
      BigDecimal bigdecimal1 = bigdecimal.abs();
      int k3 = 0;
      if(bigdecimal.signum() == 0)
          return _makeZero();
      boolean flag = bigdecimal.signum() != -1;
      int i3 = bigdecimal.scale();
      if(i3 < 0)
      {
          bigdecimal = bigdecimal.setScale(0);
          i3 = 0;
      }
      int j3 = bigdecimal1.compareTo(BIGDEC_ONE);
      int l3 = 0;
      if(j3 == -1)
      {
          BigDecimal bigdecimal2;
          do
          {
              l3++;
              bigdecimal2 = bigdecimal1.movePointRight(l3);
          } while(bigdecimal2.compareTo(BIGDEC_ONE) < 0);
          k3 = -l3;
      } else
      {
          BigDecimal bigdecimal3;
          do
          {
              l3++;
              bigdecimal3 = bigdecimal1.movePointLeft(l3);
          } while(bigdecimal3.compareTo(BIGDEC_ONE) >= 0);
          k3 = l3;
      }
      byte abyte1[] = bigdecimal1.movePointRight(i3).toBigInteger().toByteArray();
      if(abyte1.length > 54)
          throw new SQLException(CoreException.getMessage((byte)3));
      for(int j1 = 0; j1 < abyte1.length; j1++)
          if(abyte1[j1] < 0)
              al[j1] = abyte1[j1] + 256;
          else
              al[j1] = abyte1[j1];

      int i = abyte1.length;
      switch(i % 3)
      {
      case 2: // '\002'
          al1[byte0] = (al[byte4] << 8) + al[byte4 + 1];
          byte4 += 2;
          i -= 2;
          break;

      case 1: // '\001'
          al1[byte0] = al[byte4];
          byte4++;
          i--;
          break;

      default:
          long l = (al[byte4] << 16) + (al[byte4 + 1] << 8) + al[byte4 + 2];
          al1[byte0] = l % 0xf4240L;
          al1[byte0 - 1] = l / 0xf4240L;
          byte5 -= ((byte)(al1[byte0 - 1] == 0L ? 0 : 1));
          byte4 += 3;
          i -= 3;
          break;
      }
      for(; i != 0; i -= 3)
      {
          long l1 = (al[byte4] << 4) + (al[byte4 + 1] >> 4);
          for(byte byte1 = 21; byte1 >= byte5; byte1--)
          {
              l1 += al1[byte1] << 12;
              al1[byte1] = l1 % 0xf4240L;
              l1 /= 0xf4240L;
          }

          if(l1 != 0L)
          {
              byte5--;
              al1[byte5] = l1;
          }
          l1 = ((al[byte4 + 1] & 15L) << 8) + al[byte4 + 2];
          for(byte byte2 = 21; byte2 >= byte5; byte2--)
          {
              l1 += al1[byte2] << 12;
              al1[byte2] = l1 % 0xf4240L;
              l1 /= 0xf4240L;
          }

          if(l1 != 0L)
          {
              byte5--;
              al1[byte5] = l1;
          }
          byte4 += 3;
      }

      int j;
      if((abyte0[k] = (byte)(int)(al1[byte5] / 10000L)) != 0)
      {
          j = 3 * (21 - byte5) + 3;
          abyte0[k + 1] = (byte)(int)((al1[byte5] % 10000L) / 100L);
          abyte0[k + 2] = (byte)(int)(al1[byte5] % 100L);
          k += 3;
      } else
      if((abyte0[k] = (byte)(int)((al1[byte5] % 10000L) / 100L)) != 0)
      {
          j = 3 * (21 - byte5) + 2;
          abyte0[k + 1] = (byte)(int)(al1[byte5] % 100L);
          k += 2;
      } else
      {
          abyte0[k] = (byte)(int)al1[byte5];
          j = 3 * (21 - byte5) + 1;
          k++;
      }
      for(byte byte3 = (byte)(byte5 + 1); byte3 <= 21; byte3++)
      {
          abyte0[k] = (byte)(int)(al1[byte3] / 10000L);
          abyte0[k + 1] = (byte)(int)((al1[byte3] % 10000L) / 100L);
          abyte0[k + 2] = (byte)(int)(al1[byte3] % 100L);
          k += 3;
      }

      for(int k1 = k - 1; k1 >= 0 && abyte0[k1] == 0; k1--)
          j--;

      if(i3 > 0 && (i3 & 1) != 0)
      {
          int i4 = j;
          byte abyte3[] = new byte[i4 + 1];
          if(abyte0[0] <= 9)
          {
              int i2;
              for(i2 = 0; i2 < i4 - 1; i2++)
                  abyte3[i2] = (byte)((abyte0[i2] % 10) * 10 + abyte0[i2 + 1] / 10);

              abyte3[i2] = (byte)((abyte0[i2] % 10) * 10);
              if(abyte3[i4 - 1] == 0)
                  j--;
          } else
          {
              abyte3[i4] = (byte)((abyte0[i4 - 1] % 10) * 10);
              int j2;
              for(j2 = i4 - 1; j2 > 0; j2--)
                  abyte3[j2] = (byte)(abyte0[j2] / 10 + (abyte0[j2 - 1] % 10) * 10);

              abyte3[j2] = (byte)(abyte0[j2] / 10);
              if(abyte3[i4] > 0)
                  j++;
          }
          System.arraycopy(abyte3, 0, abyte0, 0, j);
      }
      if(j > 20)
      {
          int k2 = 20;
          j = 20;
          if(abyte0[k2] >= 50)
          {
              k2--;
              abyte0[k2]++;
              do
              {
                  if(abyte0[k2] != 100)
                      break;
                  if(k2 == 0)
                  {
                      k3++;
                      abyte0[k2] = 1;
                      break;
                  }
                  abyte0[k2] = 0;
                  k2--;
                  abyte0[k2]++;
              } while(true);
          }
          for(int l2 = j - 1; l2 >= 0 && abyte0[l2] == 0; l2--)
              j--;

      }
      if(k3 <= 0)
      {
          if(abyte0[0] < 10)
              i1 = -(2 - k3) / 2 + 1;
          else
              i1 = -(2 - k3) / 2;
      } else
      {
          i1 = (k3 - 1) / 2;
      }
      if(i1 > 62)
          throw new SQLException(CoreException.getMessage((byte)3));
      if(i1 <= -65)
      {
          throw new SQLException(CoreException.getMessage((byte)2));
      } else
      {
          byte abyte2[] = new byte[j + 1];
          abyte2[0] = (byte)i1;
          System.arraycopy(abyte0, 0, abyte2, 1, j);
          return _toLnxFmt(abyte2, flag);
      }
  }
  
  public static byte[] toBytes(String s, int i)
      throws SQLException
  {
      int j = 0;
      int k = 0;
      byte abyte0[] = new byte[22];
      int l1 = 0;
      boolean flag = true;
      boolean flag1 = false;
      boolean flag2 = false;
      int i2 = 0;
      int k2 = 0;
      int l2 = 0;
      byte byte0 = 40;
      int i3 = 0;
      boolean flag3 = false;
      int l3 = 0;
      int j4 = 0;
      int k4;
      if((k4 = s.indexOf("E")) != -1 || (k4 = s.indexOf("e")) != -1)
      {
          StringBuffer stringbuffer = new StringBuffer(s.length() + 5);
          int l4 = 0;
          BigDecimal bigdecimal = null;
          boolean flag4 = s.charAt(0) == '-';
          String s1 = s.substring(k4 + 1);
          String s2 = s.substring(flag4 ? 1 : 0, k4);
          bigdecimal = new BigDecimal(s2);
          boolean flag5 = s1.charAt(0) == '-';
          s1 = s1.substring(1);
          l4 = Integer.parseInt(s1);
          String s3 = bigdecimal.toString();
          int k5 = s3.indexOf(".");
          int l5 = s3.length();
          int i6 = l5;
          if(k5 != -1)
          {
              s3 = (new StringBuilder()).append(s3.substring(0, k5)).append(s3.substring(k5 + 1)).toString();
              l5--;
              if(flag5)
                  l4 -= k5;
              else
                  i6 = ++l4;
          } else
          if(flag5)
              l4 -= l5;
          else
              i6 = ++l4;
          if(flag4)
              stringbuffer.append("-");
          if(flag5)
          {
              stringbuffer.append("0.");
              for(int j6 = 0; j6 < l4; j6++)
                  stringbuffer.append("0");

              stringbuffer.append(s3);
          } else
          {
              int k6 = l4 <= l5 ? l5 : l4;
              for(int l6 = 0; l6 < k6; l6++)
              {
                  if(i6 == l6)
                      stringbuffer.append(".");
                  stringbuffer.append(l5 <= l6 ? '0' : s3.charAt(l6));
              }

          }
          s = stringbuffer.toString();
      }
      s = s.trim();
      int i4 = s.length();
      if(s.charAt(0) == '-')
      {
          i4--;
          flag = false;
          l3 = 1;
      }
      j = i4;
      char ac[] = new char[i4];
      s.getChars(l3, i4 + l3, ac, 0);
      int l = 0;
      do
      {
          if(l >= i4)
              break;
          if(ac[l] == '.')
          {
              flag2 = true;
              break;
          }
          l++;
      } while(true);
      if(!flag2)
          i = 0;
      do
      {
          if(k >= j || ac[k] != '0')
              break;
          k++;
          if(flag2)
              j4++;
      } while(true);
      if(k == j)
          return _makeZero();
      if(i4 >= 2 && ac[k] == '.')
      {
          for(; j > 0 && ac[j - 1] == '0'; j--);
          if(++k == j)
              return _makeZero();
          i2--;
          for(; k < j - 1 && ac[k] == '0' && ac[k + 1] == '0'; k += 2)
          {
              i2--;
              l2 += 2;
          }

          if(i2 < -65)
              throw new SQLException(CoreException.getMessage((byte)2));
          if(j - k > byte0)
          {
              int j1 = 2 + byte0;
              if(l2 > 0)
                  j1 += l2;
              if(j1 <= j)
                  j = j1;
              i3 = j;
              flag1 = true;
          }
          l1 = j - k >> 1;
          if((j - k) % 2 != 0)
          {
              abyte0[l1] = (byte)(Integer.parseInt(new String(ac, j - 1, 1)) * 10);
              k2++;
              j--;
          }
          while(j > k) 
          {
              l1--;
              abyte0[l1] = (byte)Integer.parseInt(new String(ac, j - 2, 2));
              j -= 2;
              k2++;
          }
      } else
      {
          for(; i > 0 && j > 0 && ac[j - 1] == '0'; i--)
              j--;

          if(i == 0 && j > 1)
          {
              if(ac[j - 1] == '.')
                  j--;
              if(k == j)
                  return _makeZero();
              while(j > 1 && ac[j - 2] == '0' && ac[j - 1] == '0') 
              {
                  j -= 2;
                  i2++;
              }
          }
          if(i2 > 62)
              throw new SQLException(CoreException.getMessage((byte)3));
          if(j - k - (flag2 ? 1 : 0) > byte0)
          {
              int k1 = byte0 + (flag2 ? 1 : 0);
              int i5 = j - k1;
              j = k1;
              i -= i5;
              if(i < 0)
                  i = 0;
              flag1 = true;
              i3 = j;
          }
          int j2 = i != 0 ? j - i - 1 : j - k;
          if(j4 > 0)
              j2 -= j4;
          int j3;
          if(j2 % 2 != 0)
          {
              j3 = Integer.parseInt(new String(ac, k, 1));
              k++;
              j2--;
              if(j - 1 == byte0)
              {
                  i--;
                  j--;
                  flag1 = true;
                  i3 = j;
              }
          } else
          {
              j3 = Integer.parseInt(new String(ac, k, 2));
              k += 2;
              j2 -= 2;
          }
          abyte0[l1] = (byte)j3;
          l1++;
          for(k2++; j2 > 0; k2++)
          {
              abyte0[l1] = (byte)Integer.parseInt(new String(ac, k, 2));
              l1++;
              k += 2;
              i2++;
              j2 -= 2;
          }

          if(k < j)
          {
              if(i % 2 != 0)
              {
                  l1 += i / 2;
                  abyte0[l1] = (byte)(Integer.parseInt(new String(ac, j - 1, 1)) * 10);
                  j--;
                  i--;
              } else
              {
                  l1 += i / 2 - 1;
                  abyte0[l1] = (byte)Integer.parseInt(new String(ac, j - 2, 2));
                  j -= 2;
                  i -= 2;
              }
              k2++;
              l1--;
          }
          while(i > 0) 
          {
              abyte0[l1] = (byte)Integer.parseInt(new String(ac, j - 2, 2));
              l1--;
              j -= 2;
              i -= 2;
              k2++;
          }
      }
      if(flag1)
      {
          int j5 = k2;
          int k3 = Integer.parseInt(new String(ac, i3, 1));
          if(k3 >= 5)
          {
              j5--;
              abyte0[j5]++;
              do
              {
                  if(abyte0[j5] != 100)
                      break;
                  if(j5 == 0)
                  {
                      i2++;
                      abyte0[j5] = 1;
                      break;
                  }
                  abyte0[j5] = 0;
                  j5--;
                  abyte0[j5]++;
              } while(true);
              for(int i1 = k2 - 1; i1 >= 0 && abyte0[i1] == 0; i1--)
                  k2--;

          }
      }
      byte abyte1[] = new byte[k2 + 1];
      abyte1[0] = (byte)i2;
      System.arraycopy(abyte0, 0, abyte1, 1, k2);
      return _toLnxFmt(abyte1, flag);
  }
  
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

      for (int k = 1; k < i; k++)
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

      for (int i = 1; i < j; i++) {
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
    int i;
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

    for (i = 1; i < j; i++) {
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