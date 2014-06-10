package oracle.sql;

import java.sql.SQLException;

class CharacterSetUTFE extends CharacterSet
  implements CharacterRepConstants
{
  static final int MAXBYTEPERCHAR = 4;
  static byte[][] utf8m2utfe = { { 0, 1, 2, 3, 55, 45, 46, 47, 22, 5, 21, 11, 12, 13, 14, 15 }, { 16, 17, 18, 19, 60, 61, 50, 38, 24, 25, 63, 39, 28, 29, 30, 31 }, { 64, 90, 127, 123, 91, 108, 80, 125, 77, 93, 92, 78, 107, 96, 75, 97 }, { -16, -15, -14, -13, -12, -11, -10, -9, -8, -7, 122, 94, 76, 126, 110, 111 }, { 124, -63, -62, -61, -60, -59, -58, -57, -56, -55, -47, -46, -45, -44, -43, -42 }, { -41, -40, -39, -30, -29, -28, -27, -26, -25, -24, -23, -83, -32, -67, 95, 109 }, { 121, -127, -126, -125, -124, -123, -122, -121, -120, -119, -111, -110, -109, -108, -107, -106 }, { -105, -104, -103, -94, -93, -92, -91, -90, -89, -88, -87, -64, 79, -48, -95, 7 }, { 32, 33, 34, 35, 36, 37, 6, 23, 40, 41, 42, 43, 44, 9, 10, 27 }, { 48, 49, 26, 51, 52, 53, 54, 8, 56, 57, 58, 59, 4, 20, 62, -1 }, { 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 81, 82, 83, 84, 85, 86 }, { 87, 88, 89, 98, 99, 100, 101, 102, 103, 104, 105, 106, 112, 113, 114, 115 }, { 116, 117, 118, 119, 120, -128, -118, -117, -116, -115, -114, -113, -112, -102, -101, -100 }, { -99, -98, -97, -96, -86, -85, -84, -82, -81, -80, -79, -78, -77, -76, -75, -74 }, { -73, -72, -71, -70, -69, -68, -66, -65, -54, -53, -52, -51, -50, -49, -38, -37 }, { -36, -35, -34, -33, -31, -22, -21, -20, -19, -18, -17, -6, -5, -4, -3, -2 } };

  static byte[][] utfe2utf8m = { { 0, 1, 2, 3, -100, 9, -122, 127, -105, -115, -114, 11, 12, 13, 14, 15 }, { 16, 17, 18, 19, -99, 10, 8, -121, 24, 25, -110, -113, 28, 29, 30, 31 }, { -128, -127, -126, -125, -124, -123, 23, 27, -120, -119, -118, -117, -116, 5, 6, 7 }, { -112, -111, 22, -109, -108, -107, -106, 4, -104, -103, -102, -101, 20, 21, -98, 26 }, { 32, -96, -95, -94, -93, -92, -91, -90, -89, -88, -87, 46, 60, 40, 43, 124 }, { 38, -86, -85, -84, -83, -82, -81, -80, -79, -78, 33, 36, 42, 41, 59, 94 }, { 45, 47, -77, -76, -75, -74, -73, -72, -71, -70, -69, 44, 37, 95, 62, 63 }, { -68, -67, -66, -65, -64, -63, -62, -61, -60, 96, 58, 35, 64, 39, 61, 34 }, { -59, 97, 98, 99, 100, 101, 102, 103, 104, 105, -58, -57, -56, -55, -54, -53 }, { -52, 106, 107, 108, 109, 110, 111, 112, 113, 114, -51, -50, -49, -48, -47, -46 }, { -45, 126, 115, 116, 117, 118, 119, 120, 121, 122, -44, -43, -42, 88, -41, -40 }, { -39, -38, -37, -36, -35, -34, -33, -32, -31, -30, -29, -28, -27, 93, -26, -25 }, { 123, 65, 66, 67, 68, 69, 70, 71, 72, 73, -24, -23, -22, -21, -20, -19 }, { 13, 74, 75, 76, 77, 78, 79, 80, 81, 82, -18, -17, -16, -15, -14, -13 }, { 92, -12, 83, 84, 85, 86, 87, 88, 89, 90, -11, -10, -9, -8, -7, -6 }, { 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, -5, -4, -3, -2, -1, -97 } };

  private static int[] m_byteLen = { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 2, 2, 3, 4 };

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  CharacterSetUTFE(int paramInt)
  {
    super(paramInt);

    this.rep = 3;
  }

  public boolean isLossyFrom(CharacterSet paramCharacterSet)
  {
    return !paramCharacterSet.isUnicode();
  }

  public boolean isConvertibleFrom(CharacterSet paramCharacterSet)
  {
    boolean bool = paramCharacterSet.rep <= 1024;

    return bool;
  }

  public boolean isUnicode()
  {
    return true;
  }

  public String toString(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws SQLException
  {
    char[] arrayOfChar = new char[paramArrayOfByte.length];
    int i = UTFEToJavaChar(paramArrayOfByte, paramInt1, paramInt2, arrayOfChar, CharacterSet.CharacterConverterBehavior.REPORT_ERROR);

    return new String(arrayOfChar, 0, i);
  }

  public String toStringWithReplacement(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    try
    {
      char[] arrayOfChar = new char[paramArrayOfByte.length];
      int i = UTFEToJavaChar(paramArrayOfByte, paramInt1, paramInt2, arrayOfChar, CharacterSet.CharacterConverterBehavior.REPLACEMENT);

      return new String(arrayOfChar, 0, i);
    }
    catch (SQLException localSQLException)
    {
      throw new IllegalStateException(localSQLException.getMessage());
    }
  }

  int UTFEToJavaChar(byte[] paramArrayOfByte, int paramInt1, int paramInt2, char[] paramArrayOfChar, CharacterSet.CharacterConverterBehavior paramCharacterConverterBehavior)
    throws SQLException
  {
    int i = paramInt1;
    int j = paramInt1 + paramInt2;
    int k = 0;

    while (i < j)
    {
      int m = utfe2utf8m[high(paramArrayOfByte[i])][low(paramArrayOfByte[(i++)])];
      byte b1;
      byte b2;
      switch (m >>> 4 & 0xF)
      {
      case 0:
      case 1:
      case 2:
      case 3:
      case 4:
      case 5:
      case 6:
      case 7:
        paramArrayOfChar[(k++)] = ((char)(m & 0x7F));

        break;
      case 8:
      case 9:
        paramArrayOfChar[(k++)] = ((char)(m & 0x1F));

        break;
      case 12:
      case 13:
        if (i >= j)
        {
          paramCharacterConverterBehavior.onFailConversion();

          i = j;
        }
        else
        {
          m = (byte)(m & 0x1F);
          b1 = utfe2utf8m[high(paramArrayOfByte[i])][low(paramArrayOfByte[(i++)])];

          if (!is101xxxxx(b1))
          {
            paramCharacterConverterBehavior.onFailConversion();

            paramArrayOfChar[(k++)] = 65533;
          }
          else
          {
            paramArrayOfChar[(k++)] = ((char)(m << 5 | b1 & 0x1F));
          }
        }
        break;
      case 14:
        if (i + 1 >= j)
        {
          paramCharacterConverterBehavior.onFailConversion();

          i = j;
        }
        else
        {
          m = (byte)(m & 0xF);
          b1 = utfe2utf8m[high(paramArrayOfByte[i])][low(paramArrayOfByte[(i++)])];
          b2 = utfe2utf8m[high(paramArrayOfByte[i])][low(paramArrayOfByte[(i++)])];

          if ((!is101xxxxx(b1)) || (!is101xxxxx(b2)))
          {
            paramCharacterConverterBehavior.onFailConversion();

            paramArrayOfChar[(k++)] = 65533;
          }
          else
          {
            paramArrayOfChar[(k++)] = ((char)(m << 10 | (b1 & 0x1F) << 5 | b2 & 0x1F));
          }
        }
        break;
      case 15:
        if (i + 2 >= j)
        {
          paramCharacterConverterBehavior.onFailConversion();

          i = j;
        }
        else
        {
          m = (byte)(m & 0x1);
          b1 = utfe2utf8m[high(paramArrayOfByte[i])][low(paramArrayOfByte[(i++)])];
          b2 = utfe2utf8m[high(paramArrayOfByte[i])][low(paramArrayOfByte[(i++)])];
          byte b3 = utfe2utf8m[high(paramArrayOfByte[i])][low(paramArrayOfByte[(i++)])];

          if ((!is101xxxxx(b1)) || (!is101xxxxx(b2)) || (!is101xxxxx(b3)))
          {
            paramCharacterConverterBehavior.onFailConversion();

            paramArrayOfChar[(k++)] = 65533;
          }
          else
          {
            paramArrayOfChar[(k++)] = ((char)(m << 15 | (b1 & 0x1F) << 10 | (b2 & 0x1F) << 5 | b3 & 0x1F));
          }
        }
        break;
      case 10:
      case 11:
      default:
        paramCharacterConverterBehavior.onFailConversion();

        paramArrayOfChar[(k++)] = 65533;
      }

    }

    return k;
  }

  public byte[] convertWithReplacement(String paramString)
  {
    char[] arrayOfChar = paramString.toCharArray();
    byte[] arrayOfByte1 = new byte[arrayOfChar.length * 4];

    int i = javaCharsToUTFE(arrayOfChar, 0, arrayOfChar.length, arrayOfByte1, 0);
    byte[] arrayOfByte2 = new byte[i];

    System.arraycopy(arrayOfByte1, 0, arrayOfByte2, 0, i);

    return arrayOfByte2;
  }

  public byte[] convert(String paramString)
    throws SQLException
  {
    return convertWithReplacement(paramString);
  }

  public byte[] convert(CharacterSet paramCharacterSet, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws SQLException
  {
    byte[] arrayOfByte;
    if (paramCharacterSet.rep == 3)
    {
      arrayOfByte = useOrCopy(paramArrayOfByte, paramInt1, paramInt2);
    }
    else
    {
      String str = paramCharacterSet.toString(paramArrayOfByte, paramInt1, paramInt2);

      arrayOfByte = convert(str);
    }

    return arrayOfByte;
  }

  int javaCharsToUTFE(char[] paramArrayOfChar, int paramInt1, int paramInt2, byte[] paramArrayOfByte, int paramInt3)
  {
    int i = paramInt1 + paramInt2;

    int k = 0;

    for (int m = paramInt1; m < i; m++)
    {
      int n = paramArrayOfChar[m];
      int j;
      if (n <= 31)
      {
        j = n | 0x80;
        paramArrayOfByte[(k++)] = utf8m2utfe[high(j)][low(j)];
      }
      else if (n <= 127)
      {
        paramArrayOfByte[(k++)] = utf8m2utfe[high(n)][low(n)];
      }
      else if (n <= 1023)
      {
        j = (n & 0x3E0) >> 5 | 0xC0;
        paramArrayOfByte[(k++)] = utf8m2utfe[high(j)][low(j)];
        j = n & 0x1F | 0xA0;
        paramArrayOfByte[(k++)] = utf8m2utfe[high(j)][low(j)];
      }
      else if (n <= 16383)
      {
        j = (n & 0x3C00) >> 10 | 0xE0;
        paramArrayOfByte[(k++)] = utf8m2utfe[high(j)][low(j)];
        j = (n & 0x3E0) >> 5 | 0xA0;
        paramArrayOfByte[(k++)] = utf8m2utfe[high(j)][low(j)];
        j = n & 0x1F | 0xA0;
        paramArrayOfByte[(k++)] = utf8m2utfe[high(j)][low(j)];
      }
      else
      {
        j = (n & 0x8000) >> 15 | 0xF0;
        paramArrayOfByte[(k++)] = utf8m2utfe[high(j)][low(j)];
        j = (n & 0x7C00) >> 10 | 0xA0;
        paramArrayOfByte[(k++)] = utf8m2utfe[high(j)][low(j)];
        j = (n & 0x3E0) >> 5 | 0xA0;
        paramArrayOfByte[(k++)] = utf8m2utfe[high(j)][low(j)];
        j = n & 0x1F | 0xA0;
        paramArrayOfByte[(k++)] = utf8m2utfe[high(j)][low(j)];
      }
    }

    return k;
  }

  int decode(CharacterWalker paramCharacterWalker)
    throws SQLException
  {
    byte[] arrayOfByte = paramCharacterWalker.bytes;
    int i = paramCharacterWalker.next;
    int j = paramCharacterWalker.end;
    int k = 0;

    if (i >= j)
    {
      failUTFConversion();
    }

    int m = arrayOfByte[i];
    int n = getUTFByteLength((byte)m);

    if ((n == 0) || (i + (n - 1) >= j))
    {
      failUTFConversion();
    }

    try
    {
      char[] arrayOfChar = new char[2];
      int i1 = UTFEToJavaChar(arrayOfByte, i, n, arrayOfChar, CharacterSet.CharacterConverterBehavior.REPORT_ERROR);

      paramCharacterWalker.next += n;

      if (i1 == 1)
      {
        return arrayOfChar[0];
      }

      return arrayOfChar[0] << '\020' | arrayOfChar[1];
    }
    catch (SQLException localSQLException)
    {
      failUTFConversion();

      paramCharacterWalker.next = i;
    }
    return k;
  }

  void encode(CharacterBuffer paramCharacterBuffer, int paramInt)
    throws SQLException
  {
    if ((paramInt & 0xFFFF0000) != 0)
    {
      failUTFConversion();
    }
    else
    {
      int i;
      if (paramInt <= 31)
      {
        need(paramCharacterBuffer, 1);

        i = paramInt | 0x80;
        paramCharacterBuffer.bytes[(paramCharacterBuffer.next++)] = utf8m2utfe[high(i)][low(i)];
      }
      else if (paramInt <= 127)
      {
        need(paramCharacterBuffer, 1);

        paramCharacterBuffer.bytes[(paramCharacterBuffer.next++)] = utf8m2utfe[high(paramInt)][low(paramInt)];
      }
      else if (paramInt <= 1023)
      {
        need(paramCharacterBuffer, 2);

        i = (paramInt & 0x3E0) >> 5 | 0xC0;
        paramCharacterBuffer.bytes[(paramCharacterBuffer.next++)] = utf8m2utfe[high(i)][low(i)];
        i = paramInt & 0x1F | 0xA0;
        paramCharacterBuffer.bytes[(paramCharacterBuffer.next++)] = utf8m2utfe[high(i)][low(i)];
      }
      else if (paramInt <= 16383)
      {
        need(paramCharacterBuffer, 3);

        i = (paramInt & 0x3C00) >> 10 | 0xE0;
        paramCharacterBuffer.bytes[(paramCharacterBuffer.next++)] = utf8m2utfe[high(i)][low(i)];
        i = (paramInt & 0x3E0) >> 5 | 0xA0;
        paramCharacterBuffer.bytes[(paramCharacterBuffer.next++)] = utf8m2utfe[high(i)][low(i)];
        i = paramInt & 0x1F | 0xA0;
        paramCharacterBuffer.bytes[(paramCharacterBuffer.next++)] = utf8m2utfe[high(i)][low(i)];
      }
      else
      {
        need(paramCharacterBuffer, 4);

        i = (paramInt & 0x8000) >> 15 | 0xF0;
        paramCharacterBuffer.bytes[(paramCharacterBuffer.next++)] = utf8m2utfe[high(i)][low(i)];
        i = (paramInt & 0x7C00) >> 10 | 0xA0;
        paramCharacterBuffer.bytes[(paramCharacterBuffer.next++)] = utf8m2utfe[high(i)][low(i)];
        i = (paramInt & 0x3E0) >> 5 | 0xA0;
        paramCharacterBuffer.bytes[(paramCharacterBuffer.next++)] = utf8m2utfe[high(i)][low(i)];
        i = paramInt & 0x1F | 0xA0;
        paramCharacterBuffer.bytes[(paramCharacterBuffer.next++)] = utf8m2utfe[high(i)][low(i)];
      }
    }
  }

  private static int high(int paramInt)
  {
    return paramInt >> 4 & 0xF;
  }

  private static int low(int paramInt)
  {
    return paramInt & 0xF;
  }

  private static boolean is101xxxxx(byte paramByte)
  {
    return (paramByte & 0xFFFFFFE0) == -96;
  }

  private static int getUTFByteLength(byte paramByte)
  {
    return m_byteLen[(utfe2utf8m[high(paramByte)][low(paramByte)] >>> 4 & 0xF)];
  }
}