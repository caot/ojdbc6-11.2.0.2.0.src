package oracle.jdbc.driver;

import java.io.IOException;
import java.sql.SQLException;

class T4CInputStream extends OracleInputStream
{
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  T4CInputStream(OracleStatement paramOracleStatement, int paramInt, Accessor paramAccessor)
  {
    super(paramOracleStatement, paramInt, paramAccessor);
  }

  public boolean isNull()
    throws IOException
  {
    if (!this.statement.connection.useFetchSizeWithLongColumn) {
      return super.isNull();
    }
    boolean bool = false;
    try
    {
      int i = this.statement.currentRow;

      if (i < 0) {
        i = 0;
      }
      if (i >= this.statement.validRows) {
        return true;
      }
      bool = this.accessor.isNull(i);
    }
    catch (SQLException localSQLException)
    {
      IOException localIOException = DatabaseError.createIOException(localSQLException);
      localIOException.fillInStackTrace();
      throw localIOException;
    }

    return bool;
  }

  public int getBytes(int paramInt)
    throws IOException
  {
    synchronized (this.statement.connection) {
      int i = -1;
      try
      {
        if ((this.statement.connection.lifecycle == 1) || (this.statement.connection.lifecycle == 2))
        {
          i = this.accessor.readStream(this.resizableBuffer, this.initialBufferSize);
        }
      }
      catch (SQLException localSQLException1) {
        throw new IOException(localSQLException1.getMessage());
      }
      catch (IOException localIOException)
      {
        try
        {
          ((T4CConnection)this.statement.connection).handleIOException(localIOException);
        }
        catch (SQLException localSQLException2) {
        }
        throw localIOException;
      }

      return i;
    }
  }
}