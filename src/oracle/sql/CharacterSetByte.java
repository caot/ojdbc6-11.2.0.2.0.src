package oracle.sql;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import oracle.jdbc.driver.DatabaseError;

class CharacterSetByte extends CharacterSet
  implements CharacterRepConstants
{
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  CharacterSetByte(int paramInt)
  {
    super(paramInt);

    this.rep = 1;
  }

  public boolean isLossyFrom(CharacterSet paramCharacterSet)
  {
    return paramCharacterSet.rep != 1;
  }

  public boolean isConvertibleFrom(CharacterSet paramCharacterSet)
  {
    return paramCharacterSet.rep <= 1024;
  }

  private String toString(byte[] paramArrayOfByte, int paramInt1, int paramInt2, char paramChar)
    throws SQLException
  {
    try
    {
      return new String(paramArrayOfByte, paramInt1, paramInt2, "ASCII");
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 183);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  public String toStringWithReplacement(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    try
    {
      return toString(paramArrayOfByte, paramInt1, paramInt2, '?');
    }
    catch (SQLException localSQLException)
    {
    }

    throw new Error("CharacterSetByte.toString");
  }

  public String toString(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws SQLException
  {
    return toString(paramArrayOfByte, paramInt1, paramInt2, '\000');
  }

  public byte[] convert(String paramString)
    throws SQLException
  {
    int i = paramString.length();
    char[] arrayOfChar = new char[paramString.length()];

    paramString.getChars(0, i, arrayOfChar, 0);

    return charsToBytes(arrayOfChar, (byte)0);
  }

  public byte[] convertWithReplacement(String paramString)
  {
    int i = paramString.length();
    char[] arrayOfChar = new char[paramString.length()];

    paramString.getChars(0, i, arrayOfChar, 0);
    try
    {
      return charsToBytes(arrayOfChar, (byte)63);
    }
    catch (SQLException localSQLException)
    {
    }

    return new byte[0];
  }

  public byte[] convert(CharacterSet paramCharacterSet, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws SQLException
  {
    byte[] arrayOfByte;
    if (paramCharacterSet.rep == 1)
    {
      arrayOfByte = useOrCopy(paramArrayOfByte, paramInt1, paramInt2);
    }
    else
    {
      Object localObject;
      if (paramCharacterSet.rep == 2)
      {
        localObject = CharacterSetUTF.UTFToJavaChar(paramArrayOfByte, paramInt1, paramInt2);

        arrayOfByte = charsToBytes((char[])localObject, (byte)0);
      }
      else
      {
        localObject = paramCharacterSet.toString(paramArrayOfByte, paramInt1, paramInt2);
        char[] arrayOfChar = ((String)localObject).toCharArray();

        arrayOfByte = charsToBytes(arrayOfChar, (byte)0);
      }
    }
    return arrayOfByte;
  }

  int decode(CharacterWalker paramCharacterWalker)
  {
    int i = paramCharacterWalker.bytes[paramCharacterWalker.next] & 0xFF;

    paramCharacterWalker.next += 1;

    return i;
  }

  void encode(CharacterBuffer paramCharacterBuffer, int paramInt)
    throws SQLException
  {
    need(paramCharacterBuffer, 1);

    if (paramInt < 256)
    {
      paramCharacterBuffer.bytes[paramCharacterBuffer.next] = ((byte)paramInt);
      paramCharacterBuffer.next += 1;
    }
  }

  static byte[] charsToBytes(char[] paramArrayOfChar, byte paramByte)
    throws SQLException
  {
    byte[] arrayOfByte = new byte[paramArrayOfChar.length];

    for (int i = 0; i < paramArrayOfChar.length; i++)
    {
      if (paramArrayOfChar[i] > '\377')
      {
        arrayOfByte[i] = paramByte;

        if (paramByte == 0)
        {
          failCharacterConversion(CharacterSet.make(31));
        }
      }
      else
      {
        arrayOfByte[i] = ((byte)paramArrayOfChar[i]);
      }
    }

    return arrayOfByte;
  }
}