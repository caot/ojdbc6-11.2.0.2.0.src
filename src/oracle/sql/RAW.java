package oracle.sql;

import java.io.ByteArrayInputStream;
import java.io.CharArrayReader;
import java.io.InputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import oracle.jdbc.driver.DBConversion;
import oracle.jdbc.driver.DatabaseError;
import oracle.jdbc.internal.OracleConnection;
import oracle.jdbc.util.RepConversion;

public class RAW extends Datum
{
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  static int hexDigit2Nibble(char paramChar)
    throws SQLException
  {
    int i = Character.digit(paramChar, 16);

    if (i == -1)
    {
      SQLException localSQLException = DatabaseError.createSqlException(null, 59, "Invalid hex digit: " + paramChar);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    return i;
  }

  public static byte[] hexString2Bytes(String paramString)
    throws SQLException
  {
    int i = paramString.length();
    char[] arrayOfChar = new char[i];

    paramString.getChars(0, i, arrayOfChar, 0);

    int j = 0;
    int k = 0;

    if (i == 0)
      return new byte[0];
    byte[] arrayOfByte;
    if (i % 2 > 0)
    {
      arrayOfByte = new byte[(i + 1) / 2];
      arrayOfByte[(j++)] = ((byte)hexDigit2Nibble(arrayOfChar[(k++)]));
    }
    else
    {
      arrayOfByte = new byte[i / 2];
    }

    for (; j < arrayOfByte.length; j++)
    {
      arrayOfByte[j] = ((byte)(hexDigit2Nibble(arrayOfChar[(k++)]) << 4 | hexDigit2Nibble(arrayOfChar[(k++)])));
    }

    return arrayOfByte;
  }

  public static RAW newRAW(Object paramObject)
    throws SQLException
  {
    RAW localRAW = new RAW(paramObject);
    return localRAW;
  }

  public static RAW oldRAW(Object paramObject)
    throws SQLException
  {
    RAW localRAW;
    if ((paramObject instanceof String))
    {
      String str = (String)paramObject;
      byte[] arrayOfByte = null;
      try
      {
        arrayOfByte = str.getBytes("ISO8859_1");
      }
      catch (UnsupportedEncodingException localUnsupportedEncodingException)
      {
        SQLException localSQLException = DatabaseError.createSqlException(null, 109, "ISO8859_1 character encoding not found");
        localSQLException.fillInStackTrace();
        throw localSQLException;
      }

      localRAW = new RAW(arrayOfByte);
    }
    else
    {
      localRAW = new RAW(paramObject);
    }
    return localRAW;
  }

  public RAW()
  {
  }

  public RAW(byte[] paramArrayOfByte)
  {
    super(paramArrayOfByte);
  }

  /** @deprecated */
  public RAW(Object paramObject)
    throws SQLException
  {
    this();

    if ((paramObject instanceof byte[]))
    {
      setShareBytes((byte[])paramObject);
    }
    else if ((paramObject instanceof String))
    {
      setShareBytes(hexString2Bytes((String)paramObject));
    }
    else
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 59, paramObject);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  public Object toJdbc()
    throws SQLException
  {
    return getBytes();
  }

  public boolean isConvertibleTo(Class paramClass)
  {
    String str = paramClass.getName();

    if ((str.compareTo("java.lang.String") == 0) || (str.compareTo("java.io.InputStream") == 0) || (str.compareTo("java.io.Reader") == 0))
    {
      return true;
    }

    return false;
  }

  public String stringValue()
  {
    String str = RepConversion.bArray2String(getBytes());
    return str;
  }

  public Reader characterStreamValue()
    throws SQLException
  {
    int i = (int)getLength();
    char[] arrayOfChar = new char[i * 2];
    byte[] arrayOfByte = shareBytes();

    DBConversion.RAWBytesToHexChars(arrayOfByte, i, arrayOfChar);

    CharArrayReader localCharArrayReader = new CharArrayReader(arrayOfChar);

    return localCharArrayReader;
  }

  public InputStream asciiStreamValue()
    throws SQLException
  {
    int i = (int)getLength();
    char[] arrayOfChar = new char[i * 2];
    byte[] arrayOfByte1 = shareBytes();

    DBConversion.RAWBytesToHexChars(arrayOfByte1, i, arrayOfChar);

    byte[] arrayOfByte2 = new byte[i * 2];

    DBConversion.javaCharsToAsciiBytes(arrayOfChar, i * 2, arrayOfByte2);

    ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(arrayOfByte2);
    return localByteArrayInputStream;
  }

  public InputStream binaryStreamValue()
    throws SQLException
  {
    return getStream();
  }

  public Object makeJdbcArray(int paramInt)
  {
    return new byte[paramInt][];
  }

  protected OracleConnection getConnectionDuringExceptionHandling()
  {
    return null;
  }
}