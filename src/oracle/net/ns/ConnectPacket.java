package oracle.net.ns;

import java.io.IOException;
import oracle.net.nt.ConnOption;
import oracle.net.nt.NTAdapter;

public class ConnectPacket extends Packet
  implements SQLnetDef
{
  private boolean connDataOflow;

  public ConnectPacket(SessionAtts paramSessionAtts, boolean paramBoolean1, boolean paramBoolean2)
    throws IOException
  {
    super(paramSessionAtts);

    this.data = paramSessionAtts.cOption.conn_data.toString();
    this.dataLen = (this.data == null ? 0 : this.data.length());

    this.connDataOflow = (this.dataLen > 230);

    int i = !this.connDataOflow ? 58 + this.dataLen : 58;

    createBuffer(i, 1, 0);

    this.buffer[8] = 1;

    this.buffer[9] = 54;
    this.buffer[10] = 1;
    this.buffer[11] = 44;

    int j = 1;
    if ((paramBoolean1) && (paramSessionAtts.nt.isCharacteristicUrgentSupported()))
      j |= 3584;
    if (paramBoolean2) {
      j |= 64;
    }
    this.buffer[12] = ((byte)(j >> 8 & 0xFF));
    this.buffer[13] = ((byte)(j & 0xFF));
    this.buffer[14] = ((byte)(this.sdu / 256));
    this.buffer[15] = ((byte)(this.sdu % 256));
    this.buffer[16] = ((byte)(this.tdu / 256));
    this.buffer[17] = ((byte)(this.tdu % 256));

    this.buffer[18] = 79;
    this.buffer[19] = -104;

    this.buffer[22] = 0;
    this.buffer[23] = 1;
    this.buffer[24] = ((byte)(this.dataLen / 256));
    this.buffer[25] = ((byte)(this.dataLen % 256));
    this.buffer[27] = 58;

    if (!paramSessionAtts.anoEnabled)
    {
      int tmp351_350 = 4; this.buffer[33] = tmp351_350; this.buffer[32] = tmp351_350;
    }
    else
    {
      byte tmp374_373 = ((byte)paramSessionAtts.getANOFlags()); this.buffer[33] = tmp374_373; this.buffer[32] = tmp374_373;
    }
    byte[] tmp383_378 = this.buffer; tmp383_378[32] = ((byte)(tmp383_378[32] | 0x80));
    byte[] tmp397_392 = this.buffer; tmp397_392[33] = ((byte)(tmp397_392[33] | 0x80));

    setUb2ToBytes(this.buffer, 50, 0);
    setUb2ToBytes(this.buffer, 52, 0);
    setUb2ToBytes(this.buffer, 54, 0);
    setUb2ToBytes(this.buffer, 56, 0);

    if ((!this.connDataOflow) && (this.dataLen > 0))
      this.data.getBytes(0, this.dataLen, this.buffer, 58);
  }

  protected void send()
    throws IOException
  {
    super.send();

    if (this.connDataOflow)
    {
      byte[] arrayOfByte = new byte[this.dataLen];

      this.data.getBytes(0, this.dataLen, arrayOfByte, 0);

      this.sAtts.nsOutputStream.write(arrayOfByte);
      this.sAtts.nsOutputStream.flush();
    }
  }
}