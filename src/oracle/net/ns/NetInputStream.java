package oracle.net.ns;

import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;

public class NetInputStream extends InputStream
  implements SQLnetDef
{
  protected DataPacket daPkt;
  protected DataDescriptorPacket ddPkt;
  protected MarkerPacket mkPkt;
  protected SessionAtts sAtts;
  private byte[] tmpBuf = new byte[1];

  public NetInputStream(SessionAtts paramSessionAtts)
  {
    this.sAtts = paramSessionAtts;
    this.daPkt = new DataPacket(paramSessionAtts);
    this.ddPkt = new DataDescriptorPacket(this.daPkt, paramSessionAtts);
  }

  public int read()
    throws IOException, NetException, BreakNetException
  {
    return read(this.tmpBuf) < 0 ? -1 : this.tmpBuf[0] & 0xFF;
  }

  public int read(byte[] paramArrayOfByte)
    throws IOException, NetException, BreakNetException
  {
    return read(paramArrayOfByte, 0, paramArrayOfByte.length);
  }

  public boolean readZeroCopyIO(byte[] paramArrayOfByte, int paramInt, int[] paramArrayOfInt)
    throws IOException, NetException, BreakNetException
  {
    boolean bool = false;

    if (this.sAtts.nsOutputStream.available() > 0) {
      this.sAtts.nsOutputStream.flush();
    }

    this.ddPkt.receive();
    int i = this.ddPkt.totalDataLength;
    if ((this.ddPkt.descriptorFLaG & 0x1) != 0) {
      bool = true;
    }

    if (paramArrayOfByte.length < paramInt + i)
    {
      throw new IOException("Assertion Failed");
    }

    int j = this.ddPkt.packet.readLocal(paramArrayOfByte, paramInt, i);

    while (j < i)
    {
      try
      {
        if ((j += this.sAtts.ntInputStream.read(paramArrayOfByte, j + paramInt, i - j)) <= 0)
        {
          throw new NetException(0);
        }
      }
      catch (InterruptedIOException localInterruptedIOException) {
        throw new NetException(504);
      }
    }
    paramArrayOfInt[0] = j;
    return bool;
  }

  public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException, NetException, BreakNetException
  {
    int i = 0;
    try
    {
      do
      {
        if ((this.daPkt == null) || (this.daPkt.availableBytesToRead <= 0) || (this.daPkt.type == 7)) {
          getNextPacket();
        }
        i += this.daPkt.getDataFromBuffer(paramArrayOfByte, paramInt1 + i, paramInt2 - i);
      }
      while (i < paramInt2);
    }
    catch (NetException localNetException) {
      if (localNetException.getErrorNumber() == 0) {
        return -1;
      }
      throw localNetException;
    }
    return i;
  }

  public int available()
    throws IOException
  {
    return this.daPkt.availableBytesToRead;
  }

  protected void getNextPacket()
    throws IOException, NetException, BreakNetException
  {
    if (this.sAtts.dataEOF) {
      throw new NetException(202);
    }

    if (this.sAtts.nsOutputStream.available() > 0) {
      this.sAtts.nsOutputStream.flush();
    }

    this.daPkt.receive();

    switch (this.daPkt.type)
    {
    case 6:
      this.daPkt.availableBytesToRead = this.daPkt.dataLen;
      break;
    case 12:
      this.mkPkt = new MarkerPacket(this.daPkt);

      this.daPkt.availableBytesToRead = 0;
      this.sAtts.onBreakReset = this.mkPkt.isBreakPkt();
      processMarker();
      throw new BreakNetException(500);
    case 7:
      this.daPkt.availableBytesToRead = this.daPkt.dataLen;
      break;
    default:
      throw new NetException(205);
    }
  }

  protected void processMarker()
    throws IOException, NetException, BreakNetException
  {
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

  Packet getCurrentPacket()
    throws IOException, NetException
  {
    return this.daPkt;
  }
}