package oracle.net.ns;

import java.io.IOException;
import java.io.OutputStream;

public class NetOutputStream extends OutputStream
  implements SQLnetDef
{
  protected DataPacket daPkt;
  protected DataDescriptorPacket ddPkt;
  protected SessionAtts sAtts;
  private byte[] tmpBuf = new byte[1];

  public NetOutputStream(SessionAtts paramSessionAtts)
  {
    this.sAtts = paramSessionAtts;
    this.daPkt = new DataPacket(paramSessionAtts);
    this.ddPkt = new DataDescriptorPacket(paramSessionAtts);
  }

  public NetOutputStream(SessionAtts paramSessionAtts, int paramInt)
  {
    this.sAtts = paramSessionAtts;
    this.daPkt = new DataPacket(paramSessionAtts, paramInt);
    this.ddPkt = new DataDescriptorPacket(paramSessionAtts);
  }

  public void writeZeroCopyIO(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException, NetException, BreakNetException
  {
    int i = paramInt2;

    boolean bool = false;
    while (i > 0)
    {
      int j;
      if (i > 1703910) {
        j = 1703910;
      }
      else {
        j = i;
        bool = true;
      }
      this.ddPkt.send(j, bool);
      synchronized (this.sAtts.ntOutputStream)
      {
        this.sAtts.ntOutputStream.write(paramArrayOfByte, paramInt1, j);
      }
      paramInt1 += j;
      i -= j;
    }
  }

  public void write(int paramInt)
    throws IOException
  {
    this.tmpBuf[0] = ((byte)paramInt);
    write(this.tmpBuf);
  }

  public void write(byte[] paramArrayOfByte)
    throws IOException
  {
    write(paramArrayOfByte, 0, paramArrayOfByte.length);
  }

  public void write(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException
  {
    int i = 0;
    int j = 0;

    while (paramInt2 > i)
    {
      i += this.daPkt.putDataInBuffer(paramArrayOfByte, paramInt1 + i, paramInt2 - i);

      if (this.daPkt.isBufferFull)
      {
        j = paramInt2 > i ? 32 : 0;

        this.daPkt.send(j);
      }
    }
  }

  public int available()
    throws IOException
  {
    return this.daPkt.availableBytesToSend;
  }

  public void flush()
    throws IOException
  {
    if (this.daPkt.availableBytesToSend > 0)
      this.daPkt.send(0);
  }

  public void close()
    throws IOException
  {
    this.daPkt.send(64);
  }

  void poolEnabled(boolean paramBoolean)
    throws IOException, NetException, BreakNetException
  {
    if (paramBoolean)
      this.daPkt.setFlags(1);
  }

  void reinitialize(SessionAtts paramSessionAtts)
    throws NetException
  {
    this.sAtts = paramSessionAtts;
  }
}