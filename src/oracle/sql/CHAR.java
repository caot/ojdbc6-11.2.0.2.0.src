package oracle.sql;

import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import oracle.jdbc.driver.DatabaseError;
import oracle.jdbc.internal.OracleConnection;

public class CHAR extends Datum
{
  public static final CharacterSet DEFAULT_CHARSET = CharacterSet.make(-1);
  private CharacterSet charSet;
  private int oracleId;
  private static final byte[] empty = new byte[0];

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  protected CHAR()
  {
  }

  public CHAR(byte[] paramArrayOfByte, CharacterSet paramCharacterSet)
  {
    setValue(paramArrayOfByte, paramCharacterSet);
  }

  public CHAR(byte[] paramArrayOfByte, int paramInt1, int paramInt2, CharacterSet paramCharacterSet)
  {
    byte[] arrayOfByte = new byte[paramInt2];

    System.arraycopy(paramArrayOfByte, paramInt1, arrayOfByte, 0, paramInt2);
    setValue(arrayOfByte, paramCharacterSet);
  }

  public CHAR(String paramString, CharacterSet paramCharacterSet)
    throws SQLException
  {
    if (paramCharacterSet == null)
    {
      paramCharacterSet = DEFAULT_CHARSET;
    }

    setValue(paramCharacterSet.convertWithReplacement(paramString), paramCharacterSet);
  }

  public CHAR(Object paramObject, CharacterSet paramCharacterSet)
    throws SQLException
  {
    this(paramObject.toString(), paramCharacterSet);
  }

  public CharacterSet getCharacterSet()
  {
    if (this.charSet == null)
    {
      if (this.oracleId == 0)
      {
        this.oracleId = -1;
      }

      if ((DEFAULT_CHARSET != null) && ((this.oracleId == -1) || (this.oracleId == DEFAULT_CHARSET.getOracleId())))
      {
        this.charSet = DEFAULT_CHARSET;
      }
      else this.charSet = CharacterSet.make(this.oracleId);
    }

    return this.charSet;
  }

  public int oracleId()
  {
    return this.oracleId;
  }

  public String getString()
    throws SQLException
  {
    return getCharacterSet().toString(shareBytes(), 0, (int)getLength());
  }

  public String getStringWithReplacement()
  {
    byte[] arrayOfByte = shareBytes();
    return getCharacterSet().toStringWithReplacement(arrayOfByte, 0, arrayOfByte.length);
  }

  public String toString()
  {
    return getStringWithReplacement();
  }

  public boolean equals(Object paramObject)
  {
    return ((paramObject instanceof CHAR)) && (getCharacterSet().equals(((CHAR)paramObject).getCharacterSet())) && (super.equals(paramObject));
  }

  void setValue(byte[] paramArrayOfByte, CharacterSet paramCharacterSet)
  {
    this.charSet = (paramCharacterSet == null ? DEFAULT_CHARSET : paramCharacterSet);
    this.oracleId = this.charSet.getOracleId();

    setShareBytes(paramArrayOfByte == null ? empty : paramArrayOfByte);
  }

  public Object toJdbc()
    throws SQLException
  {
    return stringValue();
  }

  public boolean isConvertibleTo(Class paramClass)
  {
    String str = paramClass.getName();
    return (str.compareTo("java.lang.String") == 0) || (str.compareTo("java.lang.Long") == 0) || (str.compareTo("java.math.BigDecimal") == 0) || (str.compareTo("java.io.InputStream") == 0) || (str.compareTo("java.sql.Date") == 0) || (str.compareTo("java.sql.Time") == 0) || (str.compareTo("java.sql.Timestamp") == 0) || (str.compareTo("java.io.Reader") == 0);
  }

  public String stringValue()
  {
    return toString();
  }

  public boolean booleanValue()
    throws SQLException
  {
    String str = stringValue();
    if (str == null)
    {
      return false;
    }
    str = str.trim();
    try
    {
      BigDecimal localBigDecimal = new BigDecimal(str);
      return localBigDecimal.signum() != 0;
    }
    catch (NumberFormatException localNumberFormatException)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 59);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  public int intValue()
    throws SQLException
  {
    long l = longValue();

    if ((l > 2147483647L) || (l < -2147483648L))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 26);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    return (int)l;
  }

  public long longValue()
    throws SQLException
  {
    long l = 0L;
    try
    {
      l = Long.valueOf(stringValue().trim()).longValue();
    }
    catch (NumberFormatException localNumberFormatException)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 59);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    return l;
  }

  public float floatValue()
    throws SQLException
  {
    float f = 0.0F;
    try
    {
      f = Float.valueOf(stringValue().trim()).floatValue();
    }
    catch (NumberFormatException localNumberFormatException)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 59);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    return f;
  }

  public double doubleValue()
    throws SQLException
  {
    double d = 0.0D;
    try
    {
      d = Double.valueOf(stringValue().trim()).doubleValue();
    }
    catch (NumberFormatException localNumberFormatException)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 59);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    return d;
  }

  public byte byteValue()
    throws SQLException
  {
    long l = longValue();

    if ((l > 127L) || (l < -128L))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 26);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    return (byte)(int)l;
  }

  public Date dateValue()
    throws SQLException
  {
    return Date.valueOf(stringValue().trim());
  }

  public Time timeValue()
    throws SQLException
  {
    return Time.valueOf(stringValue().trim());
  }

  public Timestamp timestampValue()
    throws SQLException
  {
    return Timestamp.valueOf(stringValue().trim());
  }

  public BigDecimal bigDecimalValue()
    throws SQLException
  {
    BigDecimal localBigDecimal = null;
    try
    {
      localBigDecimal = new BigDecimal(stringValue().trim());
    }
    catch (NumberFormatException localNumberFormatException)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 12, "bigDecimalValue");
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    return localBigDecimal;
  }

  public Reader characterStreamValue()
    throws SQLException
  {
    return new StringReader(getString());
  }

  public InputStream asciiStreamValue()
    throws SQLException
  {
    return getStream();
  }

  public InputStream binaryStreamValue()
    throws SQLException
  {
    return getStream();
  }

  public Object makeJdbcArray(int paramInt)
  {
    return new String[paramInt];
  }

  protected OracleConnection getConnectionDuringExceptionHandling()
  {
    return null;
  }
}