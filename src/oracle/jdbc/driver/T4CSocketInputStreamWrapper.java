package oracle.jdbc.driver;

import java.io.IOException;
import java.io.InputStream;
import oracle.net.ns.BreakNetException;
import oracle.net.ns.NetException;
import oracle.net.ns.NetInputStream;

class T4CSocketInputStreamWrapper extends InputStream
{
  static final int MAX_BUFFER_SIZE = 2048;
  NetInputStream is = null;
  T4CSocketOutputStreamWrapper os = null;
  boolean eof = false;

  byte[] buffer = new byte[2048];

  int bIndex = 0;
  int bytesAvailable;

  T4CSocketInputStreamWrapper(NetInputStream paramNetInputStream, T4CSocketOutputStreamWrapper paramT4CSocketOutputStreamWrapper)
    throws IOException
  {
    this.is = paramNetInputStream;
    this.os = paramT4CSocketOutputStreamWrapper;
  }

  public final int read()
    throws IOException
  {
    if (this.eof) {
      return -1;
    }
    if (this.bytesAvailable < 1)
    {
      readNextPacket();
      if (this.eof)
        return -1;
    }
    this.bytesAvailable -= 1;
    return this.buffer[(this.bIndex++)] & 0xFF;
  }

  public final int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException
  {
    if (this.eof) {
      return 0;
    }
    if (this.bytesAvailable < paramInt2)
    {
      int i = this.bytesAvailable;

      System.arraycopy(this.buffer, this.bIndex, paramArrayOfByte, paramInt1, i);
      paramInt1 += i;
      this.bIndex += i;
      this.bytesAvailable -= i;

      this.is.read(paramArrayOfByte, paramInt1, paramInt2 - i);
    }
    else {
      System.arraycopy(this.buffer, this.bIndex, paramArrayOfByte, paramInt1, paramInt2);
      this.bIndex += paramInt2;
      this.bytesAvailable -= paramInt2;
    }

    return paramInt2;
  }

  void readNextPacket()
    throws IOException
  {
    this.os.flush();

    int i = this.is.read();

    if (i == -1)
    {
      this.eof = true;
      return;
    }

    this.buffer[0] = ((byte)i);

    this.bytesAvailable = (this.is.available() + 1);

    this.bytesAvailable = (this.bytesAvailable > 2048 ? 2048 : this.bytesAvailable);

    if (this.bytesAvailable > 1) {
      this.is.read(this.buffer, 1, this.bytesAvailable - 1);
    }

    this.bIndex = 0;
  }

  int readB1() throws IOException
  {
    return read();
  }

  long readLongLSB(int paramInt) throws IOException
  {
    long l = 0L;
    int i = 0;

    if ((paramInt & 0x80) > 0)
    {
      paramInt &= 127;
      i = 1;
    }

    int j = paramInt; for (int k = 0; j > 0; k++)
    {
      if (this.bytesAvailable < 1)
      {
        readNextPacket();
      }
      l |= (this.buffer[(this.bIndex++)] & 0xFF) << 8 * k;
      this.bytesAvailable -= 1;

      j--;
    }

    return (i != 0 ? -1 : 1) * l;
  }

  long readLongMSB(int paramInt) throws IOException
  {
    long l = 0L;
    int i = 0;

    if ((paramInt & 0x80) > 0)
    {
      paramInt &= 127;
      i = 1;
    }

    for (int j = paramInt; j > 0; j--)
    {
      if (this.bytesAvailable < 1)
      {
        readNextPacket();
      }
      l |= (this.buffer[(this.bIndex++)] & 0xFF) << 8 * (j - 1);

      this.bytesAvailable -= 1;
    }

    return (i != 0 ? -1 : 1) * l;
  }

  public boolean readZeroCopyIO(byte[] paramArrayOfByte, int paramInt, int[] paramArrayOfInt)
    throws IOException, NetException, BreakNetException
  {
    this.os.flush();
    return this.is.readZeroCopyIO(paramArrayOfByte, paramInt, paramArrayOfInt);
  }
}