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

  public boolean readZeroCopyIO(byte[] abyte0, int i, int[] ai)
    throws IOException, NetException, BreakNetException
  {
    boolean bool = false;

    if (this.sAtts.nsOutputStream.available() > 0) {
      this.sAtts.nsOutputStream.flush();
    }

    this.ddPkt.receive();
    int j = this.ddPkt.totalDataLength;
    if ((this.ddPkt.descriptorFLaG & 0x1) != 0) {
      bool = true;
    }

    if (abyte0.length < i + j)
    {
      throw new IOException("Assertion Failed");
    }

    int k = this.ddPkt.packet.readLocal(abyte0, i, j);
_L2:
    while (k < j)
    {
      try
      {
//        if((k += sAtts.ntInputStream.read(abyte0, k + i, j - k)) > 0) goto _L2; else goto _L1
        if ((k += this.sAtts.ntInputStream.read(abyte0, k + i, j - k)) > 0) {
          continue _L2;
        } else {
_L1:
          throw new NetException(0);
        }
      }
      catch (InterruptedIOException localInterruptedIOException) {
        throw new NetException(504);
      }
    }
    ai[0] = k;
    return bool;
  }

  public int read(byte[] abyte0, int i, int j)
    throws IOException, NetException, BreakNetException
  {
    int k = 0;
    try
    {
      do
      {
        if (this.daPkt == null || this.daPkt.availableBytesToRead <= 0 || this.daPkt.type == 7) {
          getNextPacket();
        }
        k += this.daPkt.getDataFromBuffer(abyte0, i + k, j - k);
      }
      while (k < j);
    }
    catch (NetException localNetException) {
      if (localNetException.getErrorNumber() == 0) {
        return -1;
      }
      else
        throw localNetException;
    }
    return k;
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