package oracle.sql;

import java.sql.SQLException;

class CharacterSetAL16UTF16 extends CharacterSet
  implements CharacterRepConstants
{
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  CharacterSetAL16UTF16(int paramInt)
  {
    super(paramInt);

    this.rep = 4;
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
      char[] arrayOfChar = new char[paramInt2 >>> 1];
      int i = CharacterSet.convertAL16UTF16BytesToJavaChars(paramArrayOfByte, paramInt1, arrayOfChar, 0, paramInt2, true);

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
      char[] arrayOfChar = new char[paramInt2 >>> 1];

      int i = CharacterSet.convertAL16UTF16BytesToJavaChars(paramArrayOfByte, paramInt1, arrayOfChar, 0, paramInt2, false);

      return new String(arrayOfChar, 0, i);
    }
    catch (SQLException localSQLException)
    {
      failUTFConversion();
    }
    return "";
  }

  public byte[] convert(String paramString)
    throws SQLException
  {
    return stringToAL16UTF16Bytes(paramString);
  }

  public byte[] convertWithReplacement(String paramString)
  {
    return stringToAL16UTF16Bytes(paramString);
  }

  public byte[] convert(CharacterSet paramCharacterSet, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws SQLException
  {
    byte[] arrayOfByte;
    if (paramCharacterSet.rep == 4)
    {
      arrayOfByte = useOrCopy(paramArrayOfByte, paramInt1, paramInt2);
    }
    else
    {
      String str = paramCharacterSet.toString(paramArrayOfByte, paramInt1, paramInt2);

      arrayOfByte = stringToAL16UTF16Bytes(str);
    }

    return arrayOfByte;
  }

  int decode(CharacterWalker paramCharacterWalker)
    throws SQLException
  {
    byte[] arrayOfByte = paramCharacterWalker.bytes;
    int k = paramCharacterWalker.next;
    int m = paramCharacterWalker.end;

    if (k + 2 >= m)
    {
      failUTFConversion();
    }

    int i = arrayOfByte[(k++)];
    int j = arrayOfByte[(k++)];
    int n = i << 8 & 0xFF00 | j;
    paramCharacterWalker.next = k;

    return n;
  }

  void encode(CharacterBuffer paramCharacterBuffer, int paramInt)
    throws SQLException
  {
    if (paramInt > 65535)
    {
      failUTFConversion();
    }
    else
    {
      need(paramCharacterBuffer, 2);

      paramCharacterBuffer.bytes[(paramCharacterBuffer.next++)] = ((byte)(paramInt >> 8 & 0xFF));
      paramCharacterBuffer.bytes[(paramCharacterBuffer.next++)] = ((byte)(paramInt & 0xFF));
    }
  }
}