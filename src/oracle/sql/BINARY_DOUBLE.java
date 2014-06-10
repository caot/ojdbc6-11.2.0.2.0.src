package oracle.sql;

import java.math.BigDecimal;
import java.sql.SQLException;

public class BINARY_DOUBLE extends Datum
{
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  public BINARY_DOUBLE()
  {
  }

  public BINARY_DOUBLE(byte[] paramArrayOfByte)
  {
    super(paramArrayOfByte);
  }

  public BINARY_DOUBLE(double paramDouble)
  {
    super(doubleToCanonicalFormatBytes(paramDouble));
  }

  public BINARY_DOUBLE(Double paramDouble)
  {
    super(doubleToCanonicalFormatBytes(paramDouble.doubleValue()));
  }

  public Object toJdbc()
    throws SQLException
  {
    return new Double(canonicalFormatBytesToDouble(getBytes()));
  }

  public boolean isConvertibleTo(Class paramClass)
  {
    String str = paramClass.getName();

    return (str.compareTo("java.lang.String") == 0) || (str.compareTo("java.lang.Double") == 0);
  }

  public String stringValue()
  {
    String str = Double.toString(canonicalFormatBytesToDouble(getBytes()));
    return str;
  }

  public double doubleValue()
    throws SQLException
  {
    return canonicalFormatBytesToDouble(getBytes());
  }

  public BigDecimal bigDecimalValue()
    throws SQLException
  {
    return new BigDecimal(canonicalFormatBytesToDouble(getBytes()));
  }

  public Object makeJdbcArray(int paramInt)
  {
    return new Double[paramInt];
  }

  static byte[] doubleToCanonicalFormatBytes(double paramDouble)
  {
    double d = paramDouble;

    if (d == 0.0D)
      d = 0.0D;
    else if (d != d) {
      d = (0.0D / 0.0D);
    }
    long l = Double.doubleToLongBits(d);
    byte[] arrayOfByte = new byte[8];
    int i = (int)l;
    int j = (int)(l >> 32);

    int k = i;

    i >>= 8;

    int m = i;

    i >>= 8;

    int n = i;

    i >>= 8;

    int i1 = i;

    int i2 = j;

    j >>= 8;

    int i3 = j;

    j >>= 8;

    int i4 = j;

    j >>= 8;

    int i5 = j;

    if ((i5 & 0x80) == 0)
    {
      i5 |= 128;
    }
    else
    {
      i5 ^= -1;
      i4 ^= -1;
      i3 ^= -1;
      i2 ^= -1;
      i1 ^= -1;
      n ^= -1;
      m ^= -1;
      k ^= -1;
    }

    arrayOfByte[7] = ((byte)k);
    arrayOfByte[6] = ((byte)m);
    arrayOfByte[5] = ((byte)n);
    arrayOfByte[4] = ((byte)i1);
    arrayOfByte[3] = ((byte)i2);
    arrayOfByte[2] = ((byte)i3);
    arrayOfByte[1] = ((byte)i4);
    arrayOfByte[0] = ((byte)i5);

    return arrayOfByte;
  }

  static double canonicalFormatBytesToDouble(byte[] paramArrayOfByte)
  {
    int i = paramArrayOfByte[0];
    int j = paramArrayOfByte[1];
    int k = paramArrayOfByte[2];
    int m = paramArrayOfByte[3];
    int n = paramArrayOfByte[4];
    int i1 = paramArrayOfByte[5];
    int i2 = paramArrayOfByte[6];
    int i3 = paramArrayOfByte[7];

    if ((i & 0x80) != 0)
    {
      i &= 127;
      j &= 255;
      k &= 255;
      m &= 255;
      n &= 255;
      i1 &= 255;
      i2 &= 255;
      i3 &= 255;
    }
    else
    {
      i = (i ^ 0xFFFFFFFF) & 0xFF;
      j = (j ^ 0xFFFFFFFF) & 0xFF;
      k = (k ^ 0xFFFFFFFF) & 0xFF;
      m = (m ^ 0xFFFFFFFF) & 0xFF;
      n = (n ^ 0xFFFFFFFF) & 0xFF;
      i1 = (i1 ^ 0xFFFFFFFF) & 0xFF;
      i2 = (i2 ^ 0xFFFFFFFF) & 0xFF;
      i3 = (i3 ^ 0xFFFFFFFF) & 0xFF;
    }

    int i4 = i << 24 | j << 16 | k << 8 | m;
    int i5 = n << 24 | i1 << 16 | i2 << 8 | i3;
    long l = i4 << 32 | i5 & 0xFFFFFFFF;

    return Double.longBitsToDouble(l);
  }
}