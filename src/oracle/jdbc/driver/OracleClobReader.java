package oracle.jdbc.driver;

import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import oracle.jdbc.internal.OracleConnection;
import oracle.sql.CLOB;

class OracleClobReader extends Reader
{
  CLOB clob;
  DBConversion dbConversion;
  long lobOffset;
  long markedChar;
  char[] resizableBuffer;
  int initialBufferSize;
  int currentBufferSize;
  int pos;
  int count;
  long maxPosition = 9223372036854775807L;
  boolean isClosed;
  boolean endOfStream;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  public OracleClobReader(CLOB paramCLOB)
    throws SQLException
  {
    this(paramCLOB, ((PhysicalConnection)paramCLOB.getInternalConnection()).getDefaultStreamChunkSize() / 3);
  }

  public OracleClobReader(CLOB paramCLOB, int paramInt)
    throws SQLException
  {
    this(paramCLOB, paramInt, 1L);
  }

  public OracleClobReader(CLOB paramCLOB, int paramInt, long paramLong)
    throws SQLException
  {
    if ((paramCLOB == null) || (paramInt <= 0) || (paramCLOB.getInternalConnection() == null) || (paramLong < 1L))
    {
      throw new IllegalArgumentException();
    }

    this.dbConversion = ((PhysicalConnection)paramCLOB.getInternalConnection()).conversion;

    this.clob = paramCLOB;
    this.lobOffset = paramLong;
    this.markedChar = -1L;

    this.resizableBuffer = null;
    this.initialBufferSize = paramInt;
    this.currentBufferSize = 0;
    this.pos = (this.count = 0);

    this.isClosed = false;
  }

  public OracleClobReader(CLOB paramCLOB, int paramInt, long paramLong1, long paramLong2)
    throws SQLException
  {
    this(paramCLOB, paramInt, paramLong1);
    this.maxPosition = (paramLong1 + paramLong2);
  }

  public int read(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    throws IOException
  {
    ensureOpen();

    int i = paramInt1;
    int j = i + Math.min(paramInt2, paramArrayOfChar.length - paramInt1);

    if (!needChars(j - i)) {
      return -1;
    }

    i += writeChars(paramArrayOfChar, i, j - i);

    while ((i < j) && (needChars(j - i)))
    {
      i += writeChars(paramArrayOfChar, i, j - i);
    }

    return i - paramInt1;
  }

  protected boolean needChars(int paramInt)
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
              this.resizableBuffer = localPhysicalConnection.getCharBuffer(this.currentBufferSize);
            }
          }

          int i = this.currentBufferSize;
          if (this.maxPosition - this.lobOffset < this.currentBufferSize) i = (int)(this.maxPosition - this.lobOffset);

          this.count = this.clob.getChars(this.lobOffset, i, this.resizableBuffer);

          if (this.count < this.currentBufferSize) {
            this.endOfStream = true;
          }
          if (this.count > 0)
          {
            this.pos = 0;
            this.lobOffset += this.count;
            if (this.lobOffset >= this.maxPosition) this.endOfStream = true;

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

  protected int writeChars(char[] paramArrayOfChar, int paramInt1, int paramInt2)
  {
    int i = Math.min(paramInt2, this.count - this.pos);

    System.arraycopy(this.resizableBuffer, this.pos, paramArrayOfChar, paramInt1, i);

    this.pos += i;

    return i;
  }

  public boolean ready()
    throws IOException
  {
    ensureOpen();

    return this.pos < this.count;
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

      PhysicalConnection localPhysicalConnection = (PhysicalConnection)this.clob.getInternalConnection();
      synchronized (localPhysicalConnection)
      {
        if (this.resizableBuffer != null)
        {
          localPhysicalConnection.cacheBuffer(this.resizableBuffer);
          this.resizableBuffer = null;
        }
      }
    }
    catch (SQLException localSQLException)
    {
      IOException ioexception = DatabaseError.createIOException(localSQLException);
      ioexception.fillInStackTrace();
      throw ioexception;
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

  public boolean markSupported()
  {
    return true;
  }

  public void mark(int paramInt)
    throws IOException
  {
    if (paramInt < 0) {
      throw new IllegalArgumentException(DatabaseError.findMessage(195, null));
    }
    this.markedChar = (this.lobOffset - this.count + this.pos);
  }

  public void reset()
    throws IOException
  {
    ensureOpen();

    if (this.markedChar < 0L) {
      throw new IOException(DatabaseError.findMessage(195, null));
    }
    this.lobOffset = this.markedChar;
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
        long l2 = this.clob.length() - this.lobOffset + 1L;

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