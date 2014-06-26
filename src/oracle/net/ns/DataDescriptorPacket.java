package oracle.net.ns;

import java.io.IOException;
import java.io.OutputStream;

public class DataDescriptorPacket extends Packet
  implements SQLnetDef
{
  int totalDataLength;
  int descriptorFLaG;
  int[] sdd = new int[26];
  Packet packet;
  boolean useLongDescriptor = false;

  private static final byte[] STANDARD_SDD_MAX_DD = { 0, 72, 0, 0, 15, 0, 0, 0, 0, 0, 0, 2, 0, 25, -1, -26, 0, 0, 0, 26, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 };

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  public DataDescriptorPacket(SessionAtts paramSessionAtts)
  {
    super(paramSessionAtts, 72, 15, 0);
  }

  public DataDescriptorPacket(Packet paramPacket, SessionAtts paramSessionAtts)
  {
    super(paramSessionAtts);
    this.packet = paramPacket;
    this.buffer = this.packet.buffer;
  }

  protected void receive()
    throws IOException, NetException
  {
    this.packet.receive();
    this.descriptorFLaG = ((this.buffer[8] & 0xFF) << 24 | (this.buffer[9] & 0xFF) << 16 | (this.buffer[10] & 0xFF) << 8 | this.buffer[11] & 0xFF);

    if ((this.descriptorFLaG & 0x2) != 0)
      this.useLongDescriptor = false;
    else {
      this.useLongDescriptor = true;
    }

    this.totalDataLength = ((this.buffer[12] & 0xFF) << 24 | (this.buffer[13] & 0xFF) << 16 | (this.buffer[14] & 0xFF) << 8 | this.buffer[15] & 0xFF);
  }

  protected void send(int paramInt, boolean paramBoolean)
    throws IOException
  {
    if ((paramInt == 1703910) && (!paramBoolean))
    {
      synchronized (this.sAtts.ntOutputStream)
      {
        this.sAtts.ntOutputStream.write(STANDARD_SDD_MAX_DD, 0, STANDARD_SDD_MAX_DD.length);
      }

    }
    else
    {
      this.useLongDescriptor = false;
      this.descriptorFLaG = 2;
      if (paramBoolean) {
        this.descriptorFLaG |= 1;
      }
      int i = 0;
      int j = paramInt;
      while (j > 0)
      {
        if (j > 65535)
          this.sdd[i] = 65535;
        else
          this.sdd[i] = j;
        j -= this.sdd[i];
        i++;
      }

      writeB4ToBuffer(this.buffer, 8, this.descriptorFLaG);
      writeB4ToBuffer(this.buffer, 12, paramInt);
      writeB4ToBuffer(this.buffer, 16, i);
      for (int k = 0; k < i; k++)
        writeB2ToBuffer(this.buffer, 20 + k * 2, this.sdd[k]);
      for (int k = i; k < 26; k++) {
        writeB2ToBuffer(this.buffer, 20 + k * 2, 0);
      }

      synchronized (this.sAtts.ntOutputStream)
      {
        this.sAtts.ntOutputStream.write(this.buffer, 0, 72);
      }
    }
  }

  void writeB4ToBuffer(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    byte i = (byte)((paramInt2 & 0xFF000000) >>> 24 & 0xFF);
    byte j = (byte)((paramInt2 & 0xFF0000) >>> 16 & 0xFF);
    byte k = (byte)((paramInt2 & 0xFF00) >>> 8 & 0xFF);
    byte m = (byte)(paramInt2 & 0xFF);
    paramArrayOfByte[paramInt1] = i;
    paramArrayOfByte[(paramInt1 + 1)] = j;
    paramArrayOfByte[(paramInt1 + 2)] = k;
    paramArrayOfByte[(paramInt1 + 3)] = m;
  }

  void writeB2ToBuffer(byte[] paramArrayOfByte, int paramInt1, int paramInt2) {
    byte i = (byte)((paramInt2 & 0xFF00) >>> 8 & 0xFF);
    byte j = (byte)(paramInt2 & 0xFF);
    paramArrayOfByte[paramInt1] = i;
    paramArrayOfByte[(paramInt1 + 1)] = j;
  }
}