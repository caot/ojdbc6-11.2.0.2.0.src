package oracle.jdbc.driver;

import java.io.IOException;
import java.sql.SQLException;
import oracle.jdbc.internal.OracleConnection;
import oracle.sql.CLOB;

class OracleClobInputStream extends OracleBufferedStream
{
  protected long lobOffset;
  protected CLOB clob;
  protected long markedByte;
  protected boolean endOfStream;
  protected char[] charBuf;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  public OracleClobInputStream(CLOB paramCLOB)
    throws SQLException
  {
    this(paramCLOB, ((PhysicalConnection)paramCLOB.getJavaSqlConnection()).getDefaultStreamChunkSize(), 1L);
  }

  public OracleClobInputStream(CLOB paramCLOB, int paramInt)
    throws SQLException
  {
    this(paramCLOB, paramInt, 1L);
  }

  public OracleClobInputStream(CLOB paramCLOB, int paramInt, long paramLong)
    throws SQLException
  {
    super(paramInt);

    if ((paramCLOB == null) || (paramInt <= 0) || (paramLong < 1L))
    {
      throw new IllegalArgumentException();
    }

    this.lobOffset = paramLong;
    this.clob = paramCLOB;
    this.markedByte = -1L;
    this.endOfStream = false;
  }

  public boolean needBytes(int paramInt)
    throws IOException
  {
    ensureOpen();

    if (this.pos >= this.count)
    {
      if (!this.endOfStream)
      {
        try
        {
          if (paramInt > this.currentBufferSize)
          {
            this.currentBufferSize = Math.max(paramInt, this.initialBufferSize);
            PhysicalConnection localPhysicalConnection = (PhysicalConnection)this.clob.getInternalConnection();

            synchronized (localPhysicalConnection) {
              this.resizableBuffer = localPhysicalConnection.getByteBuffer(this.currentBufferSize);
              this.charBuf = localPhysicalConnection.getCharBuffer(this.currentBufferSize);
            }
          }
          this.count = this.clob.getChars(this.lobOffset, this.currentBufferSize, this.charBuf);

          for (int i = 0; i < this.count; i++) {
            this.resizableBuffer[i] = ((byte)this.charBuf[i]);
          }
          if (this.count < this.currentBufferSize) {
            this.endOfStream = true;
          }
          if (this.count > 0)
          {
            this.pos = 0;
            this.lobOffset += this.count;

            return true;
          }

        }
        catch (SQLException localSQLException)
        {
          IOException ioexception = DatabaseError.createIOException(localSQLException);
          ioexception.fillInStackTrace();
          throw ioexception;
        }

      }

      return false;
    }

    return true;
  }

  protected void ensureOpen()
    throws IOException
  {
    try
    {
      if (this.closed)
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

  public boolean markSupported()
  {
    return true;
  }

  public void mark(int paramInt)
  {
    if (paramInt < 0) {
      throw new IllegalArgumentException(DatabaseError.findMessage(196, null));
    }
    this.markedByte = (this.lobOffset - this.count + this.pos);
  }

  public void markInternal(int paramInt)
  {
  }

  public void reset()
    throws IOException
  {
    ensureOpen();

    if (this.markedByte < 0L) {
      throw new IOException(DatabaseError.findMessage(195, null));
    }
    this.lobOffset = this.markedByte;
    this.pos = this.count;
    this.endOfStream = false;
  }

  public long skip(long paramLong)
    throws IOException
  {
    ensureOpen();

    long l1 = 0L;

    if (this.count - this.pos >= paramLong)
    {
      this.pos = ((int)(this.pos + paramLong));
      l1 += paramLong;
    }
    else
    {
      l1 += this.count - this.pos;
      this.pos = this.count;
      try
      {
        long l2 = 0L;

        l2 = this.clob.length() - this.lobOffset + 1L;

        if (l2 >= paramLong - l1)
        {
          this.lobOffset += paramLong - l1;
          l1 += paramLong - l1;
        }
        else
        {
          this.lobOffset += l2;
          l1 += l2;
        }

      }
      catch (SQLException localSQLException)
      {
        IOException localIOException = DatabaseError.createIOException(localSQLException);
        localIOException.fillInStackTrace();
        throw localIOException;
      }

    }

    return l1;
  }

  public void close()
    throws IOException
  {
    if (this.closed)
      return;
    try
    {
      super.close();
    }
    finally
    {
      try
      {
        PhysicalConnection localPhysicalConnection1;
        PhysicalConnection localPhysicalConnection2 = (PhysicalConnection)this.clob.getInternalConnection();

        synchronized (localPhysicalConnection2)
        {
          if (this.charBuf != null)
          {
            localPhysicalConnection2.cacheBuffer(this.charBuf);
            this.charBuf = null;
          }
          if (this.resizableBuffer != null)
          {
            localPhysicalConnection2.cacheBuffer(this.resizableBuffer);
            this.resizableBuffer = null;
          }
          this.currentBufferSize = 0;
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