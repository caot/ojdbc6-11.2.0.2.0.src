package oracle.sql;

import java.sql.SQLException;

class CharacterSetAL32UTF8 extends CharacterSet
  implements CharacterRepConstants
{
  private static int[] m_byteLen = { 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 2, 2, 3, 4 };

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  CharacterSetAL32UTF8(int paramInt)
  {
    super(paramInt);

    this.rep = 6;
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

  public String toStringWithReplacement(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    try
    {
      char[] arrayOfChar = new char[paramArrayOfByte.length];

      int[] arrayOfInt = new int[1];

      arrayOfInt[0] = paramInt2;

      int i = CharacterSet.convertAL32UTF8BytesToJavaChars(paramArrayOfByte, paramInt1, arrayOfChar, 0, arrayOfInt, true);

      return new String(arrayOfChar, 0, i);
    }
    catch (SQLException localSQLException)
    {
    }

    return "";
  }

  public String toString(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws SQLException
  {
    try
    {
      char[] arrayOfChar = new char[paramArrayOfByte.length];
      int[] arrayOfInt = new int[1];

      arrayOfInt[0] = paramInt2;

      int i = CharacterSet.convertAL32UTF8BytesToJavaChars(paramArrayOfByte, paramInt1, arrayOfChar, 0, arrayOfInt, false);

      return new String(arrayOfChar, 0, i);
    }
    catch (SQLException localSQLException)
    {
      failUTFConversion();
    }
    return "";
  }

  public byte[] convertWithReplacement(String paramString)
  {
    return stringToAL32UTF8(paramString);
  }

  public byte[] convert(String paramString)
    throws SQLException
  {
    return stringToAL32UTF8(paramString);
  }

  public byte[] convert(CharacterSet paramCharacterSet, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws SQLException
  {
    byte[] arrayOfByte;
    if (paramCharacterSet.rep == 6)
    {
      arrayOfByte = useOrCopy(paramArrayOfByte, paramInt1, paramInt2);
    }
    else
    {
      String str = paramCharacterSet.toString(paramArrayOfByte, paramInt1, paramInt2);

      arrayOfByte = stringToAL32UTF8(str);
    }

    return arrayOfByte;
  }

  int decode(CharacterWalker paramCharacterWalker)
    throws SQLException
  {
    byte[] arrayOfByte = paramCharacterWalker.bytes;
    int i = paramCharacterWalker.next;
    int j = paramCharacterWalker.end;

    if (i >= j)
    {
      failUTFConversion();
    }

    int k = arrayOfByte[i];
    int m = getUTFByteLength((byte)k);

    if ((m == 0) || (i + (m - 1) >= j))
    {
      failUTFConversion();
    }

    try
    {
      char[] arrayOfChar = new char[2];
      int[] arrayOfInt = new int[1];

      arrayOfInt[0] = m;

      int n = CharacterSet.convertAL32UTF8BytesToJavaChars(arrayOfByte, i, arrayOfChar, 0, arrayOfInt, false);

      paramCharacterWalker.next += m;

      if (n == 1)
      {
        return arrayOfChar[0];
      }

      return arrayOfChar[0] << '\020' | arrayOfChar[1];
    }
    catch (SQLException localSQLException)
    {
      failUTFConversion();
    }

    return 0;
  }

  void encode(CharacterBuffer paramCharacterBuffer, int paramInt)
    throws SQLException
  {
    char[] arrayOfChar;
    int i;
    if ((paramInt & 0xFFFF0000) != 0)
    {
      need(paramCharacterBuffer, 4);

      arrayOfChar = new char[] { (char)(paramInt >>> 16), (char)paramInt };

      i = CharacterSet.convertJavaCharsToAL32UTF8Bytes(arrayOfChar, 0, paramCharacterBuffer.bytes, paramCharacterBuffer.next, 2);
    }
    else
    {
      need(paramCharacterBuffer, 3);

      arrayOfChar = new char[] { (char)paramInt };

      i = CharacterSet.convertJavaCharsToAL32UTF8Bytes(arrayOfChar, 0, paramCharacterBuffer.bytes, paramCharacterBuffer.next, 1);
    }

    paramCharacterBuffer.next += i;
  }

  private static int getUTFByteLength(byte paramByte)
  {
    return m_byteLen[(paramByte >>> 4 & 0xF)];
  }

  public int encodedByteLength(String paramString)
  {
    return CharacterSet.string32UTF8Length(paramString);
  }

  public int encodedByteLength(char[] paramArrayOfChar)
  {
    return CharacterSet.charArray32UTF8Length(paramArrayOfChar);
  }
}