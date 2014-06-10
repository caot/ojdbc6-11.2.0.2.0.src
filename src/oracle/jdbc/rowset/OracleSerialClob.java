package oracle.jdbc.rowset;

import java.io.BufferedReader;
import java.io.CharArrayReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Serializable;
import java.io.StringBufferInputStream;
import java.io.Writer;
import java.sql.Clob;
import java.sql.NClob;
import java.sql.SQLException;
import oracle.jdbc.driver.DatabaseError;
import oracle.jdbc.internal.OracleConnection;

public class OracleSerialClob
  implements Clob, NClob, Serializable, Cloneable
{
  private static final int MAX_CHAR_BUFFER_SIZE = 1024;
  private char[] buffer;
  private long length;
  private boolean isFreed = false;

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  public OracleSerialClob(char[] paramArrayOfChar)
    throws SQLException
  {
    this.length = paramArrayOfChar.length;
    this.buffer = new char[(int)this.length];
    for (int i = 0; i < this.length; i++)
      this.buffer[i] = paramArrayOfChar[i];
  }

  public OracleSerialClob(Clob paramClob)
    throws SQLException
  {
    this.length = paramClob.length();
    this.buffer = new char[(int)this.length];
    BufferedReader localBufferedReader = new BufferedReader(paramClob.getCharacterStream());
    try
    {
      int i = 0;
      int j = 0;
      do
      {
        i = localBufferedReader.read(this.buffer, j, (int)(this.length - j));

        j += i;
      }while (i > 0);
    }
    catch (IOException localIOException2)
    {
      SQLException localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 347, localIOException2.getMessage());
      localSQLException1.fillInStackTrace();
      throw localSQLException1;
    }
    finally
    {
      try {
        if (localBufferedReader != null)
          localBufferedReader.close();
      }
      catch (IOException localIOException3)
      {
        SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 347, localIOException3.getMessage());
        localSQLException2.fillInStackTrace();
        throw localSQLException2;
      }
    }
  }

  public OracleSerialClob(Reader paramReader)
    throws SQLException
  {
    try
    {
      int i = 0;
      localObject = new char[1024];
      StringBuilder localStringBuilder = new StringBuilder(1024);
      while (true)
      {
        i = paramReader.read((char[])localObject);

        if (i == -1) {
          break;
        }
        localStringBuilder.append((char[])localObject, 0, i);
      }

      paramReader.close();

      this.buffer = localStringBuilder.toString().toCharArray();
      this.length = this.buffer.length;
    }
    catch (Exception localException)
    {
      Object localObject = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 347, localException.getMessage());
      ((SQLException)localObject).fillInStackTrace();
      throw ((Throwable)localObject);
    }
  }

  public OracleSerialClob(Reader paramReader, long paramLong)
    throws SQLException
  {
    try
    {
      int i = 0;
      long l = paramLong;
      char[] arrayOfChar = new char[1024];
      StringBuilder localStringBuilder = new StringBuilder(1024);

      while (l > 0L)
      {
        i = paramReader.read(arrayOfChar, 0, Math.min(1024, (int)l));

        if (i == -1) {
          break;
        }
        localStringBuilder.append(arrayOfChar, 0, i);
        l -= i;
      }

      paramReader.close();

      this.buffer = localStringBuilder.toString().toCharArray();
      this.length = this.buffer.length;
    }
    catch (Exception localException)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 347, localException.getMessage());
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  public InputStream getAsciiStream()
    throws SQLException
  {
    if (this.isFreed)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 192);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    return new StringBufferInputStream(new String(this.buffer));
  }

  public Reader getCharacterStream()
    throws SQLException
  {
    if (this.isFreed)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 192);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    return new CharArrayReader(this.buffer);
  }

  public String getSubString(long paramLong, int paramInt)
    throws SQLException
  {
    SQLException localSQLException;
    if (this.isFreed)
    {
      localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 192);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if ((paramLong < 1L) || (paramInt < 0) || (paramInt > this.length) || (paramLong + paramInt - 1L > this.length))
    {
      localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
    if (paramInt == 0) {
      return new String();
    }
    return new String(this.buffer, (int)paramLong - 1, paramInt);
  }

  public long length()
    throws SQLException
  {
    if (this.isFreed)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 192);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    return this.length;
  }

  public long position(String paramString, long paramLong)
    throws SQLException
  {
    if (this.isFreed)
    {
      localObject = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 192);
      ((SQLException)localObject).fillInStackTrace();
      throw ((Throwable)localObject);
    }

    if (paramLong < 1L)
    {
      localObject = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68, "position()");
      ((SQLException)localObject).fillInStackTrace();
      throw ((Throwable)localObject);
    }

    if ((paramLong > this.length) || (paramLong + paramString.length() > this.length)) {
      return -1L;
    }
    Object localObject = paramString.toCharArray();
    int i = (int)(paramLong - 1L);
    int j = 0;
    long l1 = localObject.length;

    while (i < this.length)
    {
      int k = 0;
      long l2 = i + 1;
      int m = i;
      while ((k < l1) && (m < this.length) && (localObject[k] == this.buffer[m]))
      {
        k++;
        m++;
        if (k == l1) {
          return l2;
        }
      }
      i++;
    }
    return -1L;
  }

  public long position(Clob paramClob, long paramLong)
    throws SQLException
  {
    if (this.isFreed)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 192);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    return position(paramClob.getSubString(0L, (int)paramClob.length()), paramLong);
  }

  public int setString(long paramLong, String paramString)
    throws SQLException
  {
    if (this.isFreed)
    {
      localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 192);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    SQLException localSQLException = DatabaseError.createUnsupportedFeatureSqlException();
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public int setString(long paramLong, String paramString, int paramInt1, int paramInt2)
    throws SQLException
  {
    if (this.isFreed)
    {
      localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 192);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    SQLException localSQLException = DatabaseError.createUnsupportedFeatureSqlException();
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public OutputStream setAsciiStream(long paramLong)
    throws SQLException
  {
    if (this.isFreed)
    {
      localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 192);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    SQLException localSQLException = DatabaseError.createUnsupportedFeatureSqlException();
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public Writer setCharacterStream(long paramLong)
    throws SQLException
  {
    if (this.isFreed)
    {
      localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 192);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    SQLException localSQLException = DatabaseError.createUnsupportedFeatureSqlException();
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void truncate(long paramLong)
    throws SQLException
  {
    if (this.isFreed)
    {
      localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 192);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    SQLException localSQLException = DatabaseError.createUnsupportedFeatureSqlException();
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void free()
    throws SQLException
  {
    if (this.isFreed) return;

    this.isFreed = true;
    this.buffer = null;
    this.length = 0L;
  }

  public Reader getCharacterStream(long paramLong1, long paramLong2)
    throws SQLException
  {
    SQLException localSQLException;
    if (this.isFreed)
    {
      localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 192);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    paramLong1 -= 1L;
    if ((paramLong1 < 0L) || (paramLong1 + 1L > this.length) || (paramLong2 < 0L) || (paramLong2 > this.length) || (paramLong1 + paramLong2 > this.length))
    {
      localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    return new CharArrayReader(this.buffer, (int)paramLong1, (int)paramLong2);
  }

  protected OracleConnection getConnectionDuringExceptionHandling()
  {
    return null;
  }
}