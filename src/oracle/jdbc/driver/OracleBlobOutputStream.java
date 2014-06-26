package oracle.jdbc.driver;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import oracle.jdbc.internal.OracleConnection;
import oracle.sql.BLOB;

class OracleBlobOutputStream extends OutputStream
{
  long lobOffset;
  BLOB blob;
  byte[] buf;
  int count;
  int bufSize;
  boolean isClosed;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  public OracleBlobOutputStream(BLOB paramBLOB, int paramInt)
    throws SQLException
  {
    this(paramBLOB, paramInt, 1L);
  }

  public OracleBlobOutputStream(BLOB paramBLOB, int paramInt, long paramLong)
    throws SQLException
  {
    if ((paramBLOB == null) || (paramInt <= 0) || (paramLong < 1L))
    {
      throw new IllegalArgumentException("Illegal Arguments");
    }

    this.blob = paramBLOB;
    this.lobOffset = paramLong;

    PhysicalConnection localPhysicalConnection = (PhysicalConnection)paramBLOB.getInternalConnection();
    synchronized (localPhysicalConnection) {
      this.buf = localPhysicalConnection.getByteBuffer(paramInt);
    }
    this.count = 0;
    this.bufSize = paramInt;

    this.isClosed = false;
  }

  public void write(int paramInt)
    throws IOException
  {
    ensureOpen();

    if (this.count >= this.bufSize) {
      flushBuffer();
    }
    this.buf[(this.count++)] = ((byte)paramInt);
  }

  public void write(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException
  {
    ensureOpen();

    int i = paramInt1;
    int j = Math.min(paramInt2, paramArrayOfByte.length - paramInt1);

    if (j >= 2 * this.bufSize)
    {
      if (this.count > 0) flushBuffer();
      try
      {
        this.lobOffset += this.blob.setBytes(this.lobOffset, paramArrayOfByte, paramInt1, j);
      }
      catch (SQLException localSQLException)
      {
        IOException localIOException = DatabaseError.createIOException(localSQLException);
        localIOException.fillInStackTrace();
        throw localIOException;
      }

    }
    else
    {
      int k = i + j;

      while (i < k)
      {
        int m = Math.min(this.bufSize - this.count, k - i);

        System.arraycopy(paramArrayOfByte, i, this.buf, this.count, m);

        i += m;
        this.count += m;

        if (this.count >= this.bufSize)
          flushBuffer();
      }
    }
  }

  public void flush()
    throws IOException
  {
    ensureOpen();

    flushBuffer();
  }

  public void close()
    throws IOException
  {
    if (this.isClosed) {
      return;
    }
    try
    {
      this.isClosed = true;
      flushBuffer();
    }
    finally
    {
      try
      {
        PhysicalConnection localPhysicalConnection1;
        PhysicalConnection localPhysicalConnection2 = (PhysicalConnection)this.blob.getInternalConnection();
        synchronized (localPhysicalConnection2)
        {
          if (this.buf != null)
          {
            localPhysicalConnection2.cacheBuffer(this.buf);
            this.buf = null;
          }
        }
      }
      catch (SQLException localSQLException2)
      {
        IOException ioexception = DatabaseError.createIOException(localSQLException2);
        ioexception.fillInStackTrace();
        throw ioexception;
      }
    }
  }

  private void flushBuffer()
    throws IOException
  {
    try
    {
      if (this.count > 0)
      {
        this.lobOffset += this.blob.setBytes(this.lobOffset, this.buf, 0, this.count);
        this.count = 0;
      }

    }
    catch (SQLException localSQLException)
    {
      IOException localIOException = DatabaseError.createIOException(localSQLException);
      localIOException.fillInStackTrace();
      throw localIOException;
    }
  }

  void ensureOpen()
    throws IOException
  {
    try
    {
      if (this.isClosed)
      {
        SQLException localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 57, null);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

    }
    catch (SQLException localSQLException2)
    {
      IOException localIOException = DatabaseError.createIOException(localSQLException2);
      localIOException.fillInStackTrace();
      throw localIOException;
    }
  }

  protected OracleConnection getConnectionDuringExceptionHandling()
  {
    try
    {
      return this.blob.getInternalConnection();
    }
    catch (Exception localException) {
    }
    return null;
  }
}