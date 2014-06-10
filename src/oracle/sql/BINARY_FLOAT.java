package oracle.sql;

import java.math.BigDecimal;
import java.sql.SQLException;

public class BINARY_FLOAT extends Datum
{
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  public BINARY_FLOAT()
  {
  }

  public BINARY_FLOAT(byte[] paramArrayOfByte)
  {
    super(paramArrayOfByte);
  }

  public BINARY_FLOAT(float paramFloat)
  {
    super(floatToCanonicalFormatBytes(paramFloat));
  }

  public BINARY_FLOAT(Float paramFloat)
  {
    super(floatToCanonicalFormatBytes(paramFloat.floatValue()));
  }

  public Object toJdbc()
    throws SQLException
  {
    return new Float(canonicalFormatBytesToFloat(getBytes()));
  }

  public boolean isConvertibleTo(Class paramClass)
  {
    String str = paramClass.getName();

    return (str.compareTo("java.lang.String") == 0) || (str.compareTo("java.lang.Float") == 0);
  }

  public String stringValue()
  {
    String str = Float.toString(canonicalFormatBytesToFloat(getBytes()));
    return str;
  }

  public float floatValue()
    throws SQLException
  {
    return canonicalFormatBytesToFloat(getBytes());
  }

  public double doubleValue()
    throws SQLException
  {
    return floatValue();
  }

  public BigDecimal bigDecimalValue()
    throws SQLException
  {
    return new BigDecimal(floatValue());
  }

  public Object makeJdbcArray(int paramInt)
  {
    return new Float[paramInt];
  }

  static byte[] floatToCanonicalFormatBytes(float paramFloat)
  {
    float f = paramFloat;

    if (f == 0.0F)
      f = 0.0F;
    else if (f != f) {
      f = (0.0F / 0.0F);
    }
    int i = Float.floatToIntBits(f);
    byte[] arrayOfByte = new byte[4];

    int j = i;

    i >>= 8;

    int k = i;

    i >>= 8;

    int m = i;

    i >>= 8;

    int n = i;

    if ((n & 0x80) == 0) {
      n |= 128;
    }
    else {
      n ^= -1;
      m ^= -1;
      k ^= -1;
      j ^= -1;
    }

    arrayOfByte[3] = ((byte)j);
    arrayOfByte[2] = ((byte)k);
    arrayOfByte[1] = ((byte)m);
    arrayOfByte[0] = ((byte)n);

    return arrayOfByte;
  }

  static float canonicalFormatBytesToFloat(byte[] paramArrayOfByte)
  {
    int i = paramArrayOfByte[0];
    int j = paramArrayOfByte[1];
    int k = paramArrayOfByte[2];
    int m = paramArrayOfByte[3];

    if ((i & 0x80) != 0)
    {
      i &= 127;
      j &= 255;
      k &= 255;
      m &= 255;
    }
    else
    {
      i = (i ^ 0xFFFFFFFF) & 0xFF;
      j = (j ^ 0xFFFFFFFF) & 0xFF;
      k = (k ^ 0xFFFFFFFF) & 0xFF;
      m = (m ^ 0xFFFFFFFF) & 0xFF;
    }

    int n = i << 24 | j << 16 | k << 8 | m;

    return Float.intBitsToFloat(n);
  }
}