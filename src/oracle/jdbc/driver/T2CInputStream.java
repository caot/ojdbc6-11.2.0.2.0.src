package oracle.jdbc.driver;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.sql.SQLException;

class T2CInputStream extends OracleInputStream
{
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  native int t2cGetBytes(long paramLong1, int paramInt1, byte[] paramArrayOfByte1, int paramInt2, Accessor[] paramArrayOfAccessor, byte[] paramArrayOfByte2, int paramInt3, char[] paramArrayOfChar, int paramInt4, short[] paramArrayOfShort, int paramInt5, Object[] paramArrayOfObject1, Object[] paramArrayOfObject2, long paramLong2);

  T2CInputStream(OracleStatement paramOracleStatement, int paramInt, Accessor paramAccessor)
  {
    super(paramOracleStatement, paramInt, paramAccessor);
  }

  public int getBytes(int paramInt)
    throws IOException
  {
    synchronized (this.statement.connection)
    {
      if (paramInt > this.currentBufferSize)
      {
        this.currentBufferSize = Math.max(paramInt, this.initialBufferSize);
        this.resizableBuffer = new byte[this.currentBufferSize];
      }
      long l = this.statement.connection.useNio ? 1 : 0;
      if (this.statement.connection.useNio)
      {
        if ((this.statement.nioBuffers[3] == null) || (this.statement.nioBuffers[3].capacity() < this.resizableBuffer.length))
        {
          this.statement.nioBuffers[3] = ByteBuffer.allocateDirect(this.resizableBuffer.length);
        }
        else {
          this.statement.nioBuffers[3].rewind();
        }
      }
      int i = t2cGetBytes(this.statement.c_state, this.columnIndex, this.resizableBuffer, this.currentBufferSize, this.statement.accessors, this.statement.defineBytes, this.statement.accessorByteOffset, this.statement.defineChars, this.statement.accessorCharOffset, this.statement.defineIndicators, this.statement.accessorShortOffset, this.statement.nioBuffers, this.statement.lobPrefetchMetaData, l);

      int j = 0;
      try
      {
        if (i == -1)
        {
          ((T2CConnection)this.statement.connection).checkError(i, this.statement.sqlWarning);
        }
        else if (i == -2)
        {
          j = 1;
          this.accessor.setNull(this.statement.currentRow == -1 ? 0 : this.statement.currentRow, true);
          i = 0;
        } else if ((this.statement.connection.useNio) && (i >= 0))
        {
          this.accessor.setNull(this.statement.currentRow == -1 ? 0 : this.statement.currentRow, false);
        }
      }
      catch (SQLException localSQLException1) {
        throw new IOException(localSQLException1.getMessage());
      }

      if (i <= 0) {
        i = -1;
        j = 1;
      }

      if (this.statement.connection.useNio)
      {
        ByteBuffer localByteBuffer = this.statement.nioBuffers[3];
        if ((localByteBuffer != null) && (i > 0))
        {
          localByteBuffer.get(this.resizableBuffer, 0, i);
        }

        if (j != 0)
        {
          try
          {
            this.statement.extractNioDefineBuffers(this.columnIndex);
          }
          catch (SQLException localSQLException2)
          {
            throw new IOException(localSQLException2.getMessage());
          }
        }
      }

      if ((j != 0) && (this.statement.lobPrefetchMetaData != null))
      {
        this.statement.processLobPrefetchMetaData(this.statement.lobPrefetchMetaData);
      }
      return i;
    }
  }
}