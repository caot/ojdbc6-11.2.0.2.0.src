package oracle.jdbc.driver;

import java.io.IOException;
import java.sql.SQLException;

abstract class OracleInputStream extends OracleBufferedStream
{
  int columnIndex;
  Accessor accessor;
  OracleInputStream nextStream;
  boolean hasBeenOpen = false;

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  protected OracleInputStream(OracleStatement paramOracleStatement, int paramInt, Accessor paramAccessor)
  {
    super(paramOracleStatement, paramOracleStatement.connection.getDefaultStreamChunkSize());

    this.closed = true;
    this.statement = paramOracleStatement;
    this.columnIndex = paramInt;
    this.accessor = paramAccessor;
    this.nextStream = null;

    OracleInputStream localOracleInputStream = this.statement.streamList;

    if ((localOracleInputStream == null) || (this.columnIndex < localOracleInputStream.columnIndex))
    {
      this.nextStream = this.statement.streamList;
      this.statement.streamList = this;
    }
    else if (this.columnIndex == localOracleInputStream.columnIndex)
    {
      this.nextStream = localOracleInputStream.nextStream;
      localOracleInputStream.nextStream = null;
      this.statement.streamList = this;
    }
    else
    {
      while ((localOracleInputStream.nextStream != null) && (this.columnIndex > localOracleInputStream.nextStream.columnIndex))
      {
        localOracleInputStream = localOracleInputStream.nextStream;
      }

      if ((localOracleInputStream.nextStream != null) && (this.columnIndex == localOracleInputStream.nextStream.columnIndex))
      {
        this.nextStream = localOracleInputStream.nextStream.nextStream;
        localOracleInputStream.nextStream.nextStream = null;
        localOracleInputStream.nextStream = this;
      }
      else
      {
        this.nextStream = localOracleInputStream.nextStream;
        localOracleInputStream.nextStream = this;
      }
    }
  }

  public String toString()
  {
    return "OIS@" + Integer.toHexString(hashCode()) + "{" + "statement = " + this.statement + ", accessor = " + this.accessor + ", nextStream = " + this.nextStream + ", columnIndex = " + this.columnIndex + ", hasBeenOpen = " + this.hasBeenOpen + "}";
  }

  public boolean needBytes(int paramInt)
    throws IOException
  {
    if (this.closed) {
      return false;
    }
    if (this.pos >= this.count)
    {
      if (paramInt > this.currentBufferSize)
      {
        this.currentBufferSize = Math.max(paramInt, this.initialBufferSize);
        this.resizableBuffer = new byte[this.currentBufferSize];
      }
      try
      {
        int i = getBytes(this.currentBufferSize);

        this.pos = 0;
        this.count = i;

        if (this.count == -1)
        {
          if (this.nextStream == null) {
            this.statement.connection.releaseLine();
          }
          this.closed = true;

          this.accessor.fetchNextColumns();

          return false;
        }

      }
      catch (SQLException localSQLException)
      {
        IOException localIOException = DatabaseError.createIOException(localSQLException);
        localIOException.fillInStackTrace();
        throw localIOException;
      }

    }

    return true;
  }

  public boolean isNull()
    throws IOException
  {
    boolean bool = false;
    try
    {
      bool = this.accessor.isNull(0);
    }
    catch (SQLException localSQLException)
    {
      IOException localIOException = DatabaseError.createIOException(localSQLException);
      localIOException.fillInStackTrace();
      throw localIOException;
    }

    return bool;
  }

  public boolean isClosed()
  {
    return this.closed;
  }

  public void close()
    throws IOException
  {
    synchronized (this.statement.connection)
    {
      if ((!this.closed) && (this.hasBeenOpen))
      {
        while (this.statement.nextStream != this)
        {
          this.statement.nextStream.close();

          this.statement.nextStream = this.statement.nextStream.nextStream;
        }

        if (!isNull())
        {
          while (needBytes(Math.max(this.initialBufferSize, this.currentBufferSize)))
          {
            this.pos = this.count;
          }
        }

        this.closed = true;
        this.resizableBuffer = null;
        this.currentBufferSize = 0;
      }
    }
  }

  public abstract int getBytes(int paramInt)
    throws IOException;
}