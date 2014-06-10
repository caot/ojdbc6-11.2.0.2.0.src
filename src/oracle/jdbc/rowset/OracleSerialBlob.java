package oracle.jdbc.rowset;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.Blob;
import java.sql.SQLException;
import oracle.jdbc.driver.DatabaseError;
import oracle.jdbc.internal.OracleConnection;

public class OracleSerialBlob
  implements Blob, Serializable, Cloneable
{
  private byte[] buffer;
  private long length;
  private boolean isFreed = false;

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  public OracleSerialBlob(byte[] paramArrayOfByte)
    throws SQLException
  {
    this.length = paramArrayOfByte.length;
    this.buffer = new byte[(int)this.length];
    for (int i = 0; i < this.length; i++)
      this.buffer[i] = paramArrayOfByte[i];
  }

  public OracleSerialBlob(Blob paramBlob)
    throws SQLException
  {
    this.length = paramBlob.length();
    this.buffer = new byte[(int)this.length];
    BufferedInputStream localBufferedInputStream = new BufferedInputStream(paramBlob.getBinaryStream());
    try
    {
      int i = 0;
      int j = 0;
      do
      {
        i = localBufferedInputStream.read(this.buffer, j, (int)(this.length - j));

        j += i;
      }while (i > 0);
    }
    catch (IOException localIOException2)
    {
      SQLException localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 346, localIOException2.getMessage());
      localSQLException1.fillInStackTrace();
      throw localSQLException1;
    }
    finally
    {
      try {
        if (localBufferedInputStream != null)
          localBufferedInputStream.close();
      }
      catch (IOException localIOException3)
      {
        SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 346, localIOException3.getMessage());
        localSQLException2.fillInStackTrace();
        throw localSQLException2;
      }
    }
  }

  public InputStream getBinaryStream()
    throws SQLException
  {
    if (this.isFreed)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 192);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    return new ByteArrayInputStream(this.buffer);
  }

  public byte[] getBytes(long paramLong, int paramInt)
    throws SQLException
  {
    if (this.isFreed)
    {
      localObject = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 192);
      ((SQLException)localObject).fillInStackTrace();
      throw ((Throwable)localObject);
    }

    Object localObject = null;

    paramLong -= 1L;
    if ((paramLong < 0L) || (paramInt > this.length) || (paramLong + paramInt > this.length))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    localObject = new byte[paramInt];
    System.arraycopy(this.buffer, (int)paramLong, localObject, 0, paramInt);

    return localObject;
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

  public long position(byte[] paramArrayOfByte, long paramLong)
    throws SQLException
  {
    SQLException localSQLException;
    if (this.isFreed)
    {
      localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 192);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if (paramLong < 1L)
    {
      localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68, "position()");
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if ((paramLong > this.length) || (paramLong + paramArrayOfByte.length > this.length)) {
      return -1L;
    }
    int i = (int)(paramLong - 1L);
    int j = 0;
    long l1 = paramArrayOfByte.length;

    while (i < this.length)
    {
      int k = 0;
      long l2 = i + 1;
      int m = i;
      while ((k < l1) && (m < this.length) && (paramArrayOfByte[k] == this.buffer[m]))
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

  public long position(Blob paramBlob, long paramLong)
    throws SQLException
  {
    if (this.isFreed)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 192);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    return position(paramBlob.getBytes(1L, (int)paramBlob.length()), paramLong);
  }

  public int setBytes(long paramLong, byte[] paramArrayOfByte)
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

  public int setBytes(long paramLong, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
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

  public OutputStream setBinaryStream(long paramLong)
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

  public InputStream getBinaryStream(long paramLong1, long paramLong2)
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
    if ((paramLong1 < 0L) || (paramLong1 + 1L > this.length) || (paramLong2 > this.length) || (paramLong1 > 2147483647L) || (this.length > 2147483647L) || (paramLong1 + paramLong2 > this.length))
    {
      localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    return new ByteArrayInputStream(this.buffer, (int)paramLong1, (int)paramLong2);
  }

  protected OracleConnection getConnectionDuringExceptionHandling()
  {
    return null;
  }
}