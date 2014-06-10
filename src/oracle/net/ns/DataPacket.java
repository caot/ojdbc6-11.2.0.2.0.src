package oracle.net.ns;

import java.io.IOException;
import java.io.OutputStream;
import java.net.SocketException;

public class DataPacket extends Packet
  implements SQLnetDef
{
  static final boolean DEBUG2 = false;
  protected int pktOffset;
  protected int dataFlags;
  protected boolean isBufferFull = false;
  protected boolean isBufferEmpty = false;
  protected int availableBytesToSend = 0;
  protected int availableBytesToRead = 0;
  protected int sessionIdSize = 0;

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  public DataPacket(SessionAtts paramSessionAtts, int paramInt)
  {
    super(paramSessionAtts, paramInt, 6, paramSessionAtts.poolEnabled ? 1 : 0);

    initialize(paramInt);
  }

  public DataPacket(SessionAtts paramSessionAtts)
  {
    this(paramSessionAtts, paramSessionAtts.getSDU());
  }

  protected void receive()
    throws IOException, NetException
  {
    super.receive();

    this.dataOff = (this.pktOffset = 10);
    this.dataLen = (this.length - this.dataOff - (this.sAtts.poolEnabled ? 16 : 0));
    this.dataFlags = (this.buffer[8] & 0xFF);
    this.dataFlags <<= 8;
    this.dataFlags |= this.buffer[9] & 0xFF;

    if ((this.type == 6) && ((this.dataFlags & 0x40) != 0)) {
      this.sAtts.dataEOF = true;
    }

    if ((this.type == 6) && (0 == this.dataLen))
    {
      this.type = 7;
    }

    if (this.sAtts.poolEnabled)
      this.sAtts.timestampLastIO = System.currentTimeMillis();
  }

  protected void send()
    throws IOException
  {
    send(0);
  }

  protected void send(int paramInt)
    throws IOException
  {
    this.buffer[8] = ((byte)(paramInt / 256));
    this.buffer[9] = ((byte)(paramInt % 256));

    setBufferLength(this.pktOffset);

    synchronized (this.sAtts.ntOutputStream)
    {
      if (this.sAtts.poolEnabled)
      {
        while (true)
        {
          try
          {
            this.sAtts.ntOutputStream.write(this.buffer, 0, this.pktOffset);
          }
          catch (SocketException localSocketException)
          {
            if (("Connection reset".equals(localSocketException.getMessage())) || ("Connection reset by peer".equals(localSocketException.getMessage())))
            {
              if (!this.sAtts.attemptingReconnect)
              {
                this.sAtts.ns.reconnectIfRequired(false);
              }
            }
          }
        }

      }

      this.sAtts.ntOutputStream.write(this.buffer, 0, this.pktOffset);
    }

    this.pktOffset = 10;
    this.availableBytesToSend = 0;
    this.isBufferFull = false;
    if (this.sAtts.poolEnabled)
      this.sAtts.timestampLastIO = System.currentTimeMillis();
  }

  protected int putDataInBuffer(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException
  {
    int i = this.buffer.length - this.sessionIdSize - this.pktOffset <= paramInt2 ? this.buffer.length - this.sessionIdSize - this.pktOffset : paramInt2;

    if (i > 0)
    {
      System.arraycopy(paramArrayOfByte, paramInt1, this.buffer, this.pktOffset, i);
      this.pktOffset += i;

      this.isBufferFull = (this.pktOffset == this.buffer.length - this.sessionIdSize);

      this.availableBytesToSend = (this.dataOff < this.pktOffset ? this.pktOffset - this.dataOff : 0);
    }

    return i;
  }

  protected int getDataFromBuffer(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws NetException
  {
    int i = this.availableBytesToRead <= paramInt2 ? this.availableBytesToRead : paramInt2;

    if (i > 0)
    {
      System.arraycopy(this.buffer, this.pktOffset, paramArrayOfByte, paramInt1, i);
      this.pktOffset += i;

      this.isBufferEmpty = (this.pktOffset == this.length);

      this.availableBytesToRead -= i;
    }

    return i;
  }

  protected void setBufferLength(int paramInt)
    throws NetException
  {
    if (this.sAtts.poolEnabled)
    {
      System.arraycopy(this.sAtts.sessionId, 0, this.buffer, this.pktOffset, 16);

      paramInt += 16;
      this.pktOffset += 16;
    }

    this.buffer[0] = ((byte)(paramInt / 256));
    this.buffer[1] = ((byte)(paramInt % 256));
  }

  protected void initialize(int paramInt)
  {
    this.dataOff = (this.pktOffset = 10);
    this.dataLen = (paramInt - this.dataOff);
    this.dataFlags = 0;
    this.sessionIdSize = (this.sAtts.poolEnabled ? 16 : 0);
  }
}