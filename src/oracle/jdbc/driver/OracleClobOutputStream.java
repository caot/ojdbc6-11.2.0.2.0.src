package oracle.jdbc.driver;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import oracle.jdbc.internal.OracleConnection;
import oracle.sql.CLOB;

class OracleClobOutputStream extends OutputStream
{
  long lobOffset;
  CLOB clob;
  byte[] buf;
  int count;
  int bufSize;
  boolean isClosed;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  public OracleClobOutputStream(CLOB paramCLOB, int paramInt)
    throws SQLException
  {
    this(paramCLOB, paramInt, 1L);
  }

  public OracleClobOutputStream(CLOB paramCLOB, int paramInt, long paramLong)
    throws SQLException
  {
    if ((paramCLOB == null) || (paramInt <= 0) || (paramLong < 1L))
    {
      throw new IllegalArgumentException();
    }

    this.clob = paramCLOB;
    this.lobOffset = paramLong;

    PhysicalConnection localPhysicalConnection = (PhysicalConnection)paramCLOB.getInternalConnection();
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
        char[] arrayOfChar = new char[j];

        for (int m = 0; m < j; m++) {
          arrayOfChar[m] = ((char)paramArrayOfByte[(m + paramInt1)]);
        }
        this.lobOffset += this.clob.putChars(this.lobOffset, arrayOfChar);
      }
      catch (SQLException localSQLException)
      {
        IOException localIOException = DatabaseError.createIOException(localSQLException);
        localIOException.fillInStackTrace();
        throw localIOException;
      }

      return;
    }

    int k = i + j;

    while (i < k)
    {
      int n = Math.min(this.bufSize - this.count, k - i);

      System.arraycopy(paramArrayOfByte, i, this.buf, this.count, n);

      i += n;
      this.count += n;

      if (this.count >= this.bufSize)
        flushBuffer();
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
        PhysicalConnection localPhysicalConnection2 = (PhysicalConnection)this.clob.getInternalConnection();
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
        char[] arrayOfChar = new char[this.count];

        for (int i = 0; i < this.count; i++) {
          arrayOfChar[i] = ((char)this.buf[i]);
        }
        this.lobOffset += this.clob.putChars(this.lobOffset, arrayOfChar);

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
      return this.clob.getInternalConnection();
    }
    catch (Exception localException) {
    }
    return null;
  }
}