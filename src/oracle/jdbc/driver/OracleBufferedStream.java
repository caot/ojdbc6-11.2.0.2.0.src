package oracle.jdbc.driver;

import java.io.IOException;
import java.io.InputStream;
import oracle.jdbc.internal.OracleConnection;

abstract class OracleBufferedStream extends InputStream
{
  byte[] resizableBuffer;
  int initialBufferSize;
  int currentBufferSize;
  int pos;
  int count;
  long maxPosition = 2147483647L;
  boolean closed;
  OracleStatement statement;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  public OracleBufferedStream(int paramInt)
  {
    this.pos = 0;
    this.count = 0;
    this.closed = false;
    this.initialBufferSize = paramInt;
    this.currentBufferSize = 0;
    this.resizableBuffer = null;
  }

  public OracleBufferedStream(OracleStatement paramOracleStatement, int paramInt)
  {
    this(paramInt);

    this.statement = paramOracleStatement;
  }

  public void close()
    throws IOException
  {
    this.closed = true;
    this.resizableBuffer = null;
  }

  public boolean needBytes()
    throws IOException
  {
    return needBytes(Math.max(this.initialBufferSize, this.currentBufferSize));
  }

  public abstract boolean needBytes(int paramInt)
    throws IOException;

  public int flushBytes(int paramInt)
  {
    int i = paramInt > this.count - this.pos ? this.count - this.pos : paramInt;

    this.pos += i;

    return i;
  }

  public int writeBytes(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    int i = paramInt2 > this.count - this.pos ? this.count - this.pos : paramInt2;

    System.arraycopy(this.resizableBuffer, this.pos, paramArrayOfByte, paramInt1, i);

    this.pos += i;

    return i;
  }

  public int read()
    throws IOException
  {
    if (this.statement == null)
    {
      synchronized (this) {
        return readInternal();
      }
    }

    synchronized (this.statement.connection) {
      return readInternal();
    }
  }

  private final int readInternal()
    throws IOException
  {
    if ((this.closed) || (isNull())) {
      return -1;
    }
    if (needBytes()) {
      return this.resizableBuffer[(this.pos++)] & 0xFF;
    }
    return -1;
  }

  public int read(byte[] paramArrayOfByte)
    throws IOException
  {
    return read(paramArrayOfByte, 0, paramArrayOfByte.length);
  }

  public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException
  {
    if (this.statement == null)
    {
      synchronized (this) {
        return readInternal(paramArrayOfByte, paramInt1, paramInt2);
      }
    }

    synchronized (this.statement.connection) {
      return readInternal(paramArrayOfByte, paramInt1, paramInt2);
    }
  }

  private final int readInternal(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException
  {
    int j = paramInt1;

    if ((this.closed) || (isNull()))
      return -1;
    int i;
    if (paramInt2 > paramArrayOfByte.length)
      i = j + paramArrayOfByte.length;
    else {
      i = j + paramInt2;
    }
    if (!needBytes(paramInt2)) {
      return -1;
    }
    j += writeBytes(paramArrayOfByte, j, i - j);

    while ((j < i) && (needBytes(i - j)))
    {
      j += writeBytes(paramArrayOfByte, j, i - j);
    }

    return j - paramInt1;
  }

  public int available()
    throws IOException
  {
    if ((this.closed) || (isNull())) {
      return 0;
    }
    return this.count - this.pos;
  }

  public boolean isNull()
    throws IOException
  {
    return false;
  }

  public void mark(int paramInt)
  {
  }

  public void reset()
    throws IOException
  {
    synchronized (this.statement.connection)
    {
      throw new IOException(DatabaseError.findMessage(194, null));
    }
  }

  public boolean markSupported()
  {
    return false;
  }

  public long skip(int paramInt)
    throws IOException
  {
    if (this.statement == null)
    {
      synchronized (this) {
        return skipInternal(paramInt);
      }
    }

    synchronized (this.statement.connection) {
      return skipInternal(paramInt);
    }
  }

  private final int skipInternal(int paramInt)
    throws IOException
  {
    int i = 0;
    int j = paramInt;

    if ((this.closed) || (isNull())) {
      return -1;
    }
    if (!needBytes()) {
      return -1;
    }
    while ((i < j) && (needBytes()))
    {
      i += flushBytes(j - i);
    }

    return i;
  }

  protected OracleConnection getConnectionDuringExceptionHandling()
  {
    return this.statement.getConnectionDuringExceptionHandling();
  }
}