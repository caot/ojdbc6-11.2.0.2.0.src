package oracle.net.ns;

import java.io.IOException;

public class RefusePacket extends Packet
  implements SQLnetDef
{
  protected int userReason;
  protected int systemReason;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  public RefusePacket(Packet paramPacket)
    throws IOException, NetException
  {
    super(paramPacket);

    this.userReason = this.buffer[8];
    this.systemReason = this.buffer[9];

    this.dataOff = 12;

    this.dataLen = (this.buffer[10] & 0xFF);
    this.dataLen <<= 8;
    this.dataLen |= this.buffer[11] & 0xFF;

    extractData();
  }
}