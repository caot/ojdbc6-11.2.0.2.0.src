package oracle.net.ns;

import java.io.IOException;

public class RedirectPacket extends Packet
  implements SQLnetDef
{
  public RedirectPacket(Packet paramPacket)
    throws IOException, NetException
  {
    super(paramPacket);

    this.dataOff = 10;

    this.dataLen = (this.buffer[8] & 0xFF);
    this.dataLen <<= 8;
    this.dataLen |= this.buffer[9] & 0xFF;

    extractData();
  }
}