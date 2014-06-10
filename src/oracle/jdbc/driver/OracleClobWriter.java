package oracle.jdbc.driver;

import java.io.IOException;
import java.io.Writer;
import java.sql.SQLException;
import oracle.jdbc.internal.OracleConnection;
import oracle.sql.CLOB;

class OracleClobWriter extends Writer
{
  DBConversion dbConversion;
  CLOB clob;
  long lobOffset;
  char[] charBuf;
  byte[] nativeBuf;
  int pos;
  int count;
  int chunkSize;
  boolean isClosed;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  public OracleClobWriter(CLOB paramCLOB, int paramInt)
    throws SQLException
  {
    this(paramCLOB, paramInt, 1L);
  }

  public OracleClobWriter(CLOB paramCLOB, int paramInt, long paramLong)
    throws SQLException
  {
    if ((paramCLOB == null) || (paramInt <= 0) || (paramCLOB.getJavaSqlConnection() == null) || (paramLong < 1L))
    {
      throw new IllegalArgumentException();
    }

    this.dbConversion = ((PhysicalConnection)paramCLOB.getInternalConnection()).conversion;

    this.clob = paramCLOB;
    this.lobOffset = paramLong;

    this.charBuf = new char[paramInt];
    this.nativeBuf = new byte[paramInt * 3];
    this.pos = (this.count = 0);
    this.chunkSize = paramInt;

    this.isClosed = false;
  }

  public void write(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    throws IOException
  {
    synchronized (this.lock)
    {
      ensureOpen();

      int i = paramInt1;
      int j = Math.min(paramInt2, paramArrayOfChar.length - paramInt1);

      if (j >= 2 * this.chunkSize)
      {
        if (this.count > 0) flushBuffer();
        try
        {
          this.lobOffset += this.clob.putChars(this.lobOffset, paramArrayOfChar, paramInt1, j);
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
        int m = Math.min(this.chunkSize - this.count, k - i);

        System.arraycopy(paramArrayOfChar, i, this.charBuf, this.count, m);

        i += m;
        this.count += m;

        if (this.count >= this.chunkSize)
          flushBuffer();
      }
    }
  }

  public void flush()
    throws IOException
  {
    synchronized (this.lock)
    {
      ensureOpen();
      flushBuffer();
    }
  }

  public void close()
    throws IOException
  {
    synchronized (this.lock)
    {
      flushBuffer();

      this.isClosed = true;
    }
  }

  private void flushBuffer()
    throws IOException
  {
    synchronized (this.lock)
    {
      try
      {
        if (this.count > 0)
        {
          this.lobOffset += this.clob.putChars(this.lobOffset, this.charBuf, 0, this.count);
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