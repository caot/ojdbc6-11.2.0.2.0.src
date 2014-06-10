package oracle.sql;

public class INTERVALYM extends Datum
{
  private static int MASKVAL = 255;
  private static int INTYMYEAROFFSET = -2147483648;
  private static int INTYMMONTHOFFSET = 60;
  private static int INTERVALYMMAXLENGTH = 5;
  private static int MAXYEARPREC = 9;
  private static int MAXMONTH = 12;

  public INTERVALYM()
  {
    super(_initIntervalYM());
  }

  public INTERVALYM(byte[] paramArrayOfByte)
  {
    super(paramArrayOfByte);
  }

  public INTERVALYM(String paramString)
  {
    super(toBytes(paramString));
  }

  public byte[] toBytes()
  {
    return getBytes();
  }

  public static byte[] toBytes(String paramString)
  {
    byte[] arrayOfByte = new byte[INTERVALYMMAXLENGTH];

    String str1 = paramString.trim();

    int j = str1.charAt(0);
    int i;
    if ((j != 45) && (j != 43))
      i = 0;
    else {
      i = 1;
    }

    str1 = str1.substring(i);

    int k = str1.indexOf('-');

    String str2 = str1.substring(0, k);

    if (str2.length() > MAXYEARPREC) {
      throw new NumberFormatException();
    }
    int m = Integer.valueOf(str2).intValue();

    String str3 = str1.substring(k + 1);

    int n = Integer.valueOf(str3).intValue();

    if (n >= MAXMONTH) {
      throw new NumberFormatException();
    }

    if (j == 45)
    {
      m = -1 * m;
      n = -1 * n;
    }

    m += INTYMYEAROFFSET;

    arrayOfByte[0] = utilpack.RIGHTSHIFTFIRSTNIBBLE(m);
    arrayOfByte[1] = utilpack.RIGHTSHIFTSECONDNIBBLE(m);
    arrayOfByte[2] = utilpack.RIGHTSHIFTTHIRDNIBBLE(m);
    arrayOfByte[3] = utilpack.RIGHTSHIFTFOURTHNIBBLE(m);

    arrayOfByte[4] = ((byte)(n + INTYMMONTHOFFSET));

    return arrayOfByte;
  }

  public static String toString(byte[] paramArrayOfByte)
  {
    int i = 1;

    int j = utilpack.LEFTSHIFTFIRSTNIBBLE(paramArrayOfByte[0]);
    j |= utilpack.LEFTSHIFTSECONDNIBBLE(paramArrayOfByte[1]);
    j |= utilpack.LEFTSHIFTTHIRDNIBBLE(paramArrayOfByte[2]);
    j |= paramArrayOfByte[3] & 0xFF;

    j -= INTYMYEAROFFSET;

    int k = paramArrayOfByte[4] - INTYMMONTHOFFSET;

    if (j < 0)
    {
      i = 0;
      j = -j;
    }

    if (k < 0)
    {
      i = 0;
      k = -k;
    }
    String str;
    if (i != 0)
      str = j + "-" + k;
    else {
      str = "-" + j + "-" + k;
    }
    return str;
  }

  public Object toJdbc()
  {
    return this;
  }

  public String stringValue()
  {
    return toString(getBytes());
  }

  public String toString()
  {
    return toString(getBytes());
  }

  public Object makeJdbcArray(int paramInt)
  {
    INTERVALYM[] arrayOfINTERVALYM = new INTERVALYM[paramInt];

    return arrayOfINTERVALYM;
  }

  public boolean isConvertibleTo(Class paramClass)
  {
    if (paramClass.getName().compareTo("java.lang.String") == 0) {
      return true;
    }
    return false;
  }

  private static byte[] _initIntervalYM()
  {
    byte[] arrayOfByte = new byte[INTERVALYMMAXLENGTH];

    int i = 0;
    int j = 0;

    i += INTYMYEAROFFSET;

    arrayOfByte[0] = utilpack.RIGHTSHIFTFIRSTNIBBLE(i);
    arrayOfByte[1] = utilpack.RIGHTSHIFTSECONDNIBBLE(i);
    arrayOfByte[2] = utilpack.RIGHTSHIFTTHIRDNIBBLE(i);
    arrayOfByte[3] = utilpack.RIGHTSHIFTFOURTHNIBBLE(i);

    arrayOfByte[4] = ((byte)(j + INTYMMONTHOFFSET));

    return arrayOfByte;
  }
}