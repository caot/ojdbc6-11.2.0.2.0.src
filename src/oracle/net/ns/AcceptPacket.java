package oracle.net.ns;

import java.io.IOException;

public class AcceptPacket extends Packet
  implements SQLnetDef
{
  protected int version;
  protected int options;
  protected int sduSize;
  protected int tduSize;
  protected int myHWByteOrder;
  protected int flag0;
  protected int flag1;

  public AcceptPacket(Packet paramPacket)
    throws IOException, NetException
  {
    super(paramPacket);

    this.version = (this.buffer[8] & 0xFF);
    this.version <<= 8;
    this.version |= this.buffer[9] & 0xFF;

    this.options = (this.buffer[10] & 0xFF);
    this.options <<= 8;
    this.options |= this.buffer[11] & 0xFF;

    this.sduSize = (this.buffer[12] & 0xFF);
    this.sduSize <<= 8;
    this.sduSize |= this.buffer[13] & 0xFF;

    this.tduSize = (this.buffer[14] & 0xFF);
    this.tduSize <<= 8;
    this.tduSize |= this.buffer[15] & 0xFF;

    this.myHWByteOrder = (this.buffer[16] & 0xFF);
    this.myHWByteOrder <<= 8;
    this.myHWByteOrder |= this.buffer[17] & 0xFF;

    this.dataLen = (this.buffer[18] & 0xFF);
    this.dataLen <<= 8;
    this.dataLen |= this.buffer[19] & 0xFF;

    this.dataOff = (this.buffer[20] & 0xFF);
    this.dataOff <<= 8;
    this.dataOff |= this.buffer[21] & 0xFF;

    this.flag0 = this.buffer[22];
    this.flag1 = this.buffer[23];
    this.sAtts.noAnoServices = ((this.flag1 & 0x8) == 8);
    if (!this.sAtts.noAnoServices) {
      this.sAtts.noAnoServices = ((this.flag0 & 0x4) == 4);
    }
    this.sAtts.timeout = toUb2(this.buffer, 24);

    this.sAtts.tick = toUb2(this.buffer, 26);
    this.sAtts.timeout -= this.sAtts.tick;
    this.sAtts.timeout *= 10;

    int i = toUb2(this.buffer, 28);
    int j = toUb2(this.buffer, 30);
    if (i > 0)
    {
      this.sAtts.reconnectAddress = new byte[i];
      System.arraycopy(this.buffer, j, this.sAtts.reconnectAddress, 0, i);
    }

    if (this.sAtts.timeout > 0)
    {
      int k = this.dataLen + this.dataOff;
      this.sAtts.poolEnabled = true;
      this.sAtts.sessionId = new byte[16];
      System.arraycopy(this.buffer, k, this.sAtts.sessionId, 0, 16);
      this.sAtts.nsInputStream.poolEnabled(true);
      this.sAtts.nsOutputStream.poolEnabled(true);
      this.sAtts.timestampLastIO = System.currentTimeMillis();
    }

    extractData();

    this.sAtts.setSDU(this.sduSize);
    this.sAtts.setTDU(this.tduSize);

    if (this.tduSize < this.sduSize) {
      this.sAtts.setSDU(this.tduSize);
    }
    this.sAtts.setNegotiatedOptions(this.options);
  }

  public String toString()
  {
    return new StringBuilder().append("version=").append(this.version).append(", options=").append(this.options).append(", sduSize=").append(this.sduSize).append(", tduSize=").append(this.tduSize).append("\nmyHWByteOrder=").append(this.myHWByteOrder).append(", dataLen=").append(this.dataLen).append(", flag0=").append(this.flag0).append(", flag1=").append(this.flag1).append("\n").append(this.sAtts.poolEnabled ? new StringBuilder().append("timeout=").append(this.sAtts.timeout).append(",re-connect: ").append(this.sAtts.reconnectAddress).append("\n(byte)Dumping session-id:\n").append(dumpBytes(this.sAtts.sessionId, 0, 16)).toString() : "").append("\n\n").toString();
  }
}