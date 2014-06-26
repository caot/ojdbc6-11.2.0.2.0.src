package oracle.net.ns;

import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.io.PrintStream;
import oracle.net.jdbc.nl.RepConversion;

public class Packet
  implements SQLnetDef
{
  private int buffer2send;
  protected int sdu;
  protected int tdu;
  protected int length;
  public int type;
  protected int flags;
  protected int dataLen;
  protected int dataOff;
  protected String data;
  protected byte[] buffer;
  protected int leftOverFromPreviousRead = 0;

  protected int nextPacketOffset = 0;
  public SessionAtts sAtts;
  StringBuilder sb = null;
  StringBuilder tmpBuf = null;

  public static final String[] toHex = { " 00", " 01", " 02", " 03", " 04", " 05", " 06", " 07", " 08", " 09", " 0A", " 0B", " 0C", " 0D", " 0E", " 0F", " 10", " 11", " 12", " 13", " 14", " 15", " 16", " 17", " 18", " 19", " 1A", " 1B", " 1C", " 1D", " 1E", " 1F", " 20", " 21", " 22", " 23", " 24", " 25", " 26", " 27", " 28", " 29", " 2A", " 2B", " 2C", " 2D", " 2E", " 2F", " 30", " 31", " 32", " 33", " 34", " 35", " 36", " 37", " 38", " 39", " 3A", " 3B", " 3C", " 3D", " 3E", " 3F", " 40", " 41", " 42", " 43", " 44", " 45", " 46", " 47", " 48", " 49", " 4A", " 4B", " 4C", " 4D", " 4E", " 4F", " 50", " 51", " 52", " 53", " 54", " 55", " 56", " 57", " 58", " 59", " 5A", " 5B", " 5C", " 5D", " 5E", " 5F", " 60", " 61", " 62", " 63", " 64", " 65", " 66", " 67", " 68", " 69", " 6A", " 6B", " 6C", " 6D", " 6E", " 6F", " 70", " 71", " 72", " 73", " 74", " 75", " 76", " 77", " 78", " 79", " 7A", " 7B", " 7C", " 7D", " 7E", " 7F", " 80", " 81", " 82", " 83", " 84", " 85", " 86", " 87", " 88", " 89", " 8A", " 8B", " 8C", " 8D", " 8E", " 8F", " 90", " 91", " 92", " 93", " 94", " 95", " 96", " 97", " 98", " 99", " 9A", " 9B", " 9C", " 9D", " 9E", " 9F", " A0", " A1", " A2", " A3", " A4", " A5", " A6", " A7", " A8", " A9", " AA", " AB", " AC", " AD", " AE", " AF", " B0", " B1", " B2", " B3", " B4", " B5", " B6", " B7", " B8", " B9", " BA", " BB", " BC", " BD", " BE", " BF", " C0", " C1", " C2", " C3", " C4", " C5", " C6", " C7", " C8", " C9", " CA", " CB", " CC", " CD", " CE", " CF", " D0", " D1", " D2", " D3", " D4", " D5", " D6", " D7", " D8", " D9", " DA", " DB", " DC", " DD", " DE", " DF", " E0", " E1", " E2", " E3", " E4", " E5", " E6", " E7", " E8", " E9", " EA", " EB", " EC", " ED", " EE", " EF", " F0", " F1", " F2", " F3", " F4", " F5", " F6", " F7", " F8", " F9", " FA", " FB", " FC", " FD", " FE", " FF" };

  public static final char[] toChar = { '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '!', '"', '#', '$', '%', '&', '\'', '(', ')', '*', '+', ',', '-', '.', '/', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', ':', ';', '<', '=', '>', '?', '@', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '[', '\\', ']', '^', '_', '`', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '{', '|', '}', '~', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.' };
  public static final String DIVIDER = "     |";
  public static final String BLANK_SPACE = "   ";
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  public Packet(SessionAtts paramSessionAtts)
  {
    this.sAtts = paramSessionAtts;
    this.sdu = paramSessionAtts.getSDU();
    this.tdu = paramSessionAtts.getTDU();
  }

  public Packet(SessionAtts paramSessionAtts, int paramInt)
  {
    this(paramSessionAtts);
    createBuffer(paramInt);
  }

  public Packet(SessionAtts paramSessionAtts, int paramInt1, int paramInt2, int paramInt3)
  {
    this(paramSessionAtts);
    createBuffer(paramInt1, paramInt2, paramInt3);
  }

  public Packet(Packet paramPacket)
  {
    this(paramPacket.sAtts);

    this.length = paramPacket.length;
    this.type = paramPacket.type;
    this.flags = paramPacket.flags;
    this.dataLen = paramPacket.dataLen;
    this.dataOff = paramPacket.dataOff;
    this.buffer = paramPacket.buffer;
  }

  protected void createBuffer(int paramInt)
  {
    this.buffer = new byte[paramInt];

    this.buffer[0] = ((byte)(paramInt / 256));
    this.buffer[1] = ((byte)(paramInt % 256));
  }

  protected void createBuffer(int paramInt1, int paramInt2, int paramInt3)
  {
    createBuffer(paramInt1);
    this.buffer[5] = ((byte)paramInt3);
    this.buffer[4] = ((byte)paramInt2);
  }

  protected void receive()
    throws IOException, NetException
  {
    int i = 0;
    int j = 0;
    int k = this.sdu;
    int m = 1;
    while (k > 0)
    {
      if (this.leftOverFromPreviousRead > 0)
      {
        System.arraycopy(this.buffer, this.nextPacketOffset, this.buffer, 0, this.leftOverFromPreviousRead);

        i = this.leftOverFromPreviousRead;
        if (i >= 10)
        {
          processHeader();
          m = 0;
          if (this.length >= this.leftOverFromPreviousRead)
          {
            k = this.length - this.leftOverFromPreviousRead;
            this.leftOverFromPreviousRead = 0;
            this.nextPacketOffset = 0;
          }
          else {
            this.leftOverFromPreviousRead -= this.length;
            k = 0;
            this.nextPacketOffset = this.length;
          }

        }
        else
        {
          k -= i;
          this.leftOverFromPreviousRead -= i;

          this.nextPacketOffset = 0;
        }
      }

      try
      {
        if ((k > 0) && 
          ((j = this.sAtts.ntInputStream.read(this.buffer, i, k)) <= 0))
        {
          throw new NetException(0);
        }
        i += j;

        if (m != 0)
        {
          m = 0;

          processHeader();
          k = this.length;

          if (i > this.length)
          {
            this.leftOverFromPreviousRead = (i - this.length);
            k = 0;
            this.nextPacketOffset = this.length;
          }
          else {
            this.leftOverFromPreviousRead = 0;
            this.nextPacketOffset = 0;
            k -= i;
          }

        }
        else
        {
          k -= j;
        }

      }
      catch (InterruptedIOException localInterruptedIOException)
      {
        throw new NetException(504);
      }
    }
  }

  void processHeader()
    throws IOException, NetException
  {
    this.length = (this.buffer[0] & 0xFF);
    this.length <<= 8;
    this.length |= this.buffer[1] & 0xFF;

    this.type = this.buffer[4];
    this.flags = this.buffer[5];

    if (this.type > 19)
      throw new NetException(204);
    if ((this.length > 65535) || (this.length > this.sdu))
      throw new NetException(203);
    if (this.length < 8)
      throw new NetException(207);
  }

  protected void send()
    throws IOException
  {
    synchronized (this.sAtts.ntOutputStream)
    {
      this.sAtts.ntOutputStream.write(this.buffer, 0, this.buffer.length);
    }
  }

  protected void extractData()
    throws IOException, NetException
  {
    if (this.dataLen <= 0) {
      this.data = new String();
    }
    else if (this.length > this.dataOff)
    {
      this.data = new String(this.buffer, 0, this.dataOff, this.dataLen);
    }
    else
    {
      byte[] arrayOfByte = new byte[this.dataLen];

      if (this.sAtts.nsInputStream.read(arrayOfByte) < 0) {
        throw new NetException(0);
      }
      this.data = new String(arrayOfByte, 0);
    }
  }

  protected String getData()
  {
    return this.data;
  }

  void setFlags(int paramInt)
    throws NetException
  {
    this.flags = paramInt;
    this.buffer[5] = ((byte)this.flags);
  }

  void reinitialize(SessionAtts paramSessionAtts)
    throws NetException
  {
    this.sAtts = paramSessionAtts;
  }

  public static final int toUb2(byte[] paramArrayOfByte, int paramInt)
  {
    int i = 0;
    i = paramArrayOfByte[paramInt] & 0xFF;
    i <<= 8;
    i |= paramArrayOfByte[(paramInt + 1)] & 0xFF;

    return i;
  }

  public static final void setUb2ToBytes(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    paramArrayOfByte[paramInt1] = ((byte)(paramInt2 << 8 & 0xFF));
    paramArrayOfByte[(paramInt1 + 1)] = ((byte)(paramInt2 & 0xFF));
  }

  int readLocal(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws NetException
  {
    int i = 0;
    if (this.leftOverFromPreviousRead > 0)
    {
      if (this.leftOverFromPreviousRead > paramInt2)
      {
        i = paramInt2;
        System.arraycopy(this.buffer, this.nextPacketOffset, paramArrayOfByte, paramInt1, i);

        this.leftOverFromPreviousRead -= i;

        this.nextPacketOffset += i;
      }
      else
      {
        i = this.leftOverFromPreviousRead;
        System.arraycopy(this.buffer, this.nextPacketOffset, paramArrayOfByte, paramInt1, i);

        this.leftOverFromPreviousRead -= i;
        this.nextPacketOffset = 0;
      }
    }

    return i;
  }

  protected String dumpBytes(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    if (this.sb == null)
    {
      this.sb = new StringBuilder(16384);
      this.tmpBuf = new StringBuilder(80);
    }
    return dumpBytes(paramArrayOfByte, paramInt1, paramInt2, 8, this.sb, this.tmpBuf);
  }

  public String dumpBytes(byte[] paramArrayOfByte, int paramInt1, int paramInt2, int paramInt3, StringBuilder paramStringBuilder1, StringBuilder paramStringBuilder2)
  {
    if (paramArrayOfByte == null) {
      return "NULL";
    }
    paramStringBuilder1.delete(0, paramStringBuilder1.length());
    paramStringBuilder2.delete(0, paramStringBuilder2.length());

    int j = paramInt1 + paramInt2;
    int k = paramInt1;

    int m = 0;
    int n = 0;
    for (; k < j; k++) {
      int i = paramArrayOfByte[k] & 0xFF;
      paramStringBuilder1.append(toHex[i]);
      paramStringBuilder2.append(toChar[i]);

      n++;

      if (n == paramInt3) {
        paramStringBuilder1.append("     |");
        paramStringBuilder1.append(paramStringBuilder2.substring(0, paramStringBuilder2.length()));
        paramStringBuilder1.append("|\n");
        paramStringBuilder2.delete(0, paramStringBuilder2.length());
        n = 0;
      }

    }

    if (n > 0) {
      int i1 = paramInt3 - n - 1;

      for (int i2 = 0; i2 <= i1; i2++) {
        paramStringBuilder1.append("   ");
      }
      paramStringBuilder1.append("     |");
      paramStringBuilder1.append(paramStringBuilder2.substring(0, paramStringBuilder2.length()));
      for (int i2 = 0; i2 <= i1; i2++)
        paramStringBuilder1.append(" ");
      paramStringBuilder1.append("|\n");
      paramStringBuilder2.delete(0, paramStringBuilder2.length());
    }

    return paramStringBuilder1.substring(0, paramStringBuilder1.length());
  }

  protected void dump(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    int i = 0;
    System.out.println("Packet dump");
    System.out.println(new StringBuilder().append("buffer.length=").append(paramArrayOfByte.length).toString());
    System.out.println(new StringBuilder().append("offset       =").append(paramInt1).toString());
    System.out.println(new StringBuilder().append("len          =").append(paramInt2).toString());

    for (int j = paramInt1; j < paramInt2; j += 8)
    {
      System.out.print("|");
      for (int k = 0; (k < 8) && (i < paramInt2 - 1); k++)
      {
        i = j + k;
        RepConversion.printInHex(paramArrayOfByte[i]);
        System.out.print(" ");
      }

      System.out.println("|");
    }
    System.out.println("finish dump");
  }
}