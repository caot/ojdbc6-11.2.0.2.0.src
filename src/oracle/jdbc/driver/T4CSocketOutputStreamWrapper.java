package oracle.jdbc.driver;

import java.io.IOException;
import java.io.OutputStream;
import oracle.net.ns.BreakNetException;
import oracle.net.ns.NetException;
import oracle.net.ns.NetOutputStream;

class T4CSocketOutputStreamWrapper extends OutputStream
{
  static final int MAX_BUFFER_SIZE = 2048;
  NetOutputStream os = null;

  byte[] buffer = new byte[2048];
  int bIndex = 0;

  T4CSocketOutputStreamWrapper(NetOutputStream paramNetOutputStream)
    throws IOException
  {
    this.os = paramNetOutputStream;
  }

  public void write(int paramInt) throws IOException
  {
    if (this.bIndex + 1 >= 2048) {
      flush();
    }
    this.buffer[(this.bIndex++)] = ((byte)(paramInt & 0xFF));
  }

  public void write(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException
  {
    if (paramInt2 > 2048)
    {
      flush();

      this.os.write(paramArrayOfByte, paramInt1, paramInt2);
    } else if (this.bIndex + paramInt2 < 2048)
    {
      System.arraycopy(paramArrayOfByte, paramInt1, this.buffer, this.bIndex, paramInt2);
      this.bIndex += paramInt2;
    }
    else {
      flush();
      System.arraycopy(paramArrayOfByte, paramInt1, this.buffer, this.bIndex, paramInt2);
      this.bIndex += paramInt2;
    }
  }

  public void flush() throws IOException
  {
    flush(false);
  }

  public void flush(boolean paramBoolean) throws IOException
  {
    if (this.bIndex > 0)
    {
      this.os.write(this.buffer, 0, this.bIndex);
      this.bIndex = 0;
    }
    if (paramBoolean)
      this.os.flush();
  }

  public void close() throws IOException
  {
    this.os.close();
    super.close();
  }

  public void writeZeroCopyIO(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException, NetException, BreakNetException
  {
    flush(true);
    this.os.writeZeroCopyIO(paramArrayOfByte, paramInt1, paramInt2);
  }
}