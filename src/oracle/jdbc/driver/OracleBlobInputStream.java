package oracle.jdbc.driver;

import java.io.IOException;
import java.sql.SQLException;
import oracle.jdbc.internal.OracleConnection;
import oracle.sql.BFILE;
import oracle.sql.BLOB;
import oracle.sql.Datum;

class OracleBlobInputStream extends OracleBufferedStream
{
  long lobOffset;
  Datum lob;
  long markedByte;
  boolean endOfStream = false;
  long maxPosition = 9223372036854775807L;

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  public OracleBlobInputStream(BLOB paramBLOB)
    throws SQLException
  {
    this(paramBLOB, ((PhysicalConnection)paramBLOB.getJavaSqlConnection()).getDefaultStreamChunkSize(), 1L);
  }

  public OracleBlobInputStream(BLOB paramBLOB, int paramInt)
    throws SQLException
  {
    this(paramBLOB, paramInt, 1L);
  }

  public OracleBlobInputStream(BLOB paramBLOB, int paramInt, long paramLong)
    throws SQLException
  {
    super(paramInt);

    if ((paramBLOB == null) || (paramInt <= 0) || (paramLong < 1L))
    {
      throw new IllegalArgumentException("Illegal Arguments");
    }

    this.lob = paramBLOB;
    this.markedByte = -1L;
    this.lobOffset = paramLong;
  }

  public OracleBlobInputStream(BLOB paramBLOB, int paramInt, long paramLong1, long paramLong2)
    throws SQLException
  {
    this(paramBLOB, paramInt, paramLong1);
    this.maxPosition = (paramLong1 + paramLong2);
  }

  public OracleBlobInputStream(BFILE paramBFILE)
    throws SQLException
  {
    this(paramBFILE, ((PhysicalConnection)paramBFILE.getJavaSqlConnection()).getDefaultStreamChunkSize(), 1L);
  }

  public OracleBlobInputStream(BFILE paramBFILE, int paramInt)
    throws SQLException
  {
    this(paramBFILE, paramInt, 1L);
  }

  public OracleBlobInputStream(BFILE paramBFILE, int paramInt, long paramLong)
    throws SQLException
  {
    super(paramInt);

    if ((paramBFILE == null) || (paramInt <= 0) || (paramLong < 1L))
    {
      throw new IllegalArgumentException("Illegal Arguments");
    }

    this.lob = paramBFILE;
    this.markedByte = -1L;
    this.lobOffset = paramLong;
  }

  public boolean needBytes(int paramInt)
    throws IOException
  {
    ensureOpen();

    if (this.pos >= this.count)
    {
      if (!this.endOfStream)
      {
        if (paramInt > this.currentBufferSize)
        {
          this.currentBufferSize = Math.max(paramInt, this.initialBufferSize);
          this.resizableBuffer = new byte[this.currentBufferSize];
        }
        try
        {
          int i;
          if (this.currentBufferSize < this.maxPosition - this.lobOffset) i = this.currentBufferSize; else {
            i = (int)(this.maxPosition - this.lobOffset);
          }

          if ((this.lob instanceof BLOB))
            this.count = ((BLOB)this.lob).getBytes(this.lobOffset, i, this.resizableBuffer);
          else {
            this.count = ((BFILE)this.lob).getBytes(this.lobOffset, i, this.resizableBuffer);
          }

          if (this.count < this.currentBufferSize) {
            this.endOfStream = true;
          }
          if (this.count > 0)
          {
            this.pos = 0;
            this.lobOffset += this.count;
            if (this.lobOffset > this.maxPosition) this.endOfStream = true;

            return true;
          }

        }
        catch (SQLException localSQLException)
        {
          IOException localIOException = DatabaseError.createIOException(localSQLException);
          localIOException.fillInStackTrace();
          throw localIOException;
        }

      }

      return false;
    }

    return true;
  }

  void ensureOpen()
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
      throw new IllegalArgumentException("Read-ahead limit < 0");
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
      throw new IOException("Mark invalid or stream not marked.");
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

        if ((this.lob instanceof BLOB))
          l2 = ((BLOB)this.lob).length() - this.lobOffset + 1L;
        else {
          l2 = ((BFILE)this.lob).length() - this.lobOffset + 1L;
        }
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
    OracleConnection localOracleConnection = null;

    if (this.lob != null)
    {
      try
      {
        if ((this.lob instanceof BLOB))
          localOracleConnection = ((BLOB)this.lob).getInternalConnection();
        else
          localOracleConnection = ((BFILE)this.lob).getInternalConnection();
      }
      catch (Exception localException)
      {
        localOracleConnection = null;
      }
    }

    return localOracleConnection;
  }
}