package oracle.jdbc.driver;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import oracle.jdbc.aq.AQNotificationEvent;
import oracle.jdbc.dcn.DatabaseChangeEvent;
import oracle.sql.CharacterSet;

class NTFConnection extends Thread
{
  private static final int NS_HEADER_SIZE = 10;
  private static final int INTERRUPT_SIGNAL = -2;
  private SocketChannel channel;
  private ByteBuffer inBuffer = null;
  private ByteBuffer outBuffer = null;
  private int currentNSPacketLength;
  private int currentNSPacketType;
  private ByteBuffer currentNSPacketDataBuffer;
  private boolean needsToBeClosed = false;
  private NTFManager ntfManager;
  private Selector selector = null;
  private Iterator iterator = null;
  private SelectionKey aKey = null;
  int remotePort;
  String remoteAddress;
  String remoteName;
  int localPort;
  String localAddress;
  String localName;
  String connectionDescription;
  CharacterSet charset = null;
  static final int NSPTCN = 1;
  static final int NSPTAC = 2;
  static final int NSPTAK = 3;
  static final int NSPTRF = 4;
  static final int NSPTRD = 5;
  static final int NSPTDA = 6;
  static final int NSPTNL = 7;
  static final int NSPTAB = 9;
  static final int NSPTRS = 11;
  static final int NSPTMK = 12;
  static final int NSPTAT = 13;
  static final int NSPTCNL = 14;
  static final int NSPTHI = 19;
  static final short KPDNFY_TIMEOUT = 1;
  static final short KPDNFY_GROUPING = 2;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  NTFConnection(NTFManager paramNTFManager, SocketChannel paramSocketChannel)
  {
    try
    {
      this.ntfManager = paramNTFManager;
      this.channel = paramSocketChannel;
      this.channel.configureBlocking(false);

      this.inBuffer = ByteBuffer.allocate(4096);
      this.outBuffer = ByteBuffer.allocate(2048);
      Socket localSocket = this.channel.socket();
      InetAddress localInetAddress1 = localSocket.getInetAddress();
      InetAddress localInetAddress2 = localSocket.getLocalAddress();
      this.remotePort = localSocket.getPort();
      this.localPort = localSocket.getLocalPort();
      this.remoteAddress = localInetAddress1.getHostAddress();
      this.remoteName = localInetAddress1.getHostName();
      this.localAddress = localInetAddress2.getHostAddress();
      this.localName = localInetAddress2.getHostName();
      this.connectionDescription = ("local=" + this.localName + "/" + this.localAddress + ":" + this.localPort + ", remote=" + this.remoteName + "/" + this.remoteAddress + ":" + this.remotePort);
    }
    catch (IOException localIOException)
    {
    }
  }

  public void run()
  {
    try
    {
      this.selector = Selector.open();
      this.channel.register(this.selector, 1);

      int i = 0;
      this.inBuffer.limit(0);

      while (!this.needsToBeClosed)
      {
        if (!this.inBuffer.hasRemaining()) {
          do
            i = readFromNetwork();
          while (i == 0);
        }
        if (i == -1)
        {
          break;
        }

        if (i != -2)
        {
          unmarshalOneNSPacket();
        }
      }
      this.selector.close();
      this.channel.close();
    }
    catch (IOException localIOException)
    {
    }
  }

  private int readFromNetwork()
    throws IOException
  {
    this.inBuffer.compact();
    while (true)
    {
      if ((this.iterator == null) || (!this.iterator.hasNext()))
      {
        this.selector.select();

        if (this.needsToBeClosed)
        {
          return -2;
        }

        this.iterator = this.selector.selectedKeys().iterator();
      } else {
        this.aKey = ((SelectionKey)this.iterator.next());
        if ((this.aKey.readyOps() & 0x1) == 1)
        {
          break;
        }

      }

    }

    int i = this.channel.read(this.inBuffer);
    if (i > 0)
    {
      this.inBuffer.flip();
    }

    this.iterator.remove();

    return i;
  }

  private void getNextNSPacket()
    throws IOException
  {
    while ((!this.inBuffer.hasRemaining()) || (this.inBuffer.remaining() < 10)) {
      readFromNetwork();
    }
    this.currentNSPacketLength = this.inBuffer.getShort();

    this.inBuffer.position(this.inBuffer.position() + 2);
    this.currentNSPacketType = this.inBuffer.get();

    this.inBuffer.position(this.inBuffer.position() + 5);

    while (this.inBuffer.remaining() < this.currentNSPacketLength - 10) {
      readFromNetwork();
    }

    int i = this.inBuffer.limit();
    int j = this.inBuffer.position() + this.currentNSPacketLength - 10;

    this.inBuffer.limit(j);
    this.currentNSPacketDataBuffer = this.inBuffer.slice();
    this.inBuffer.limit(i);

    this.inBuffer.position(j);
  }

  private void unmarshalOneNSPacket()
    throws IOException
  {
    getNextNSPacket();

    if (this.currentNSPacketDataBuffer.hasRemaining())
    {
      switch (this.currentNSPacketType)
      {
      case 1:
        byte[] arrayOfByte1 = { 0, 24, 0, 0, 2, 0, 0, 0, 1, 52, 0, 0, 8, 0, 127, -1, 1, 0, 0, 0, 0, 24, 65, 1 };

        this.outBuffer.clear();
        this.outBuffer.put(arrayOfByte1);
        this.outBuffer.limit(24);
        this.outBuffer.rewind();
        this.channel.write(this.outBuffer);
        break;
      case 6:
        if ((this.currentNSPacketDataBuffer.get(0) == -34) && (this.currentNSPacketDataBuffer.get(1) == -83))
        {
          byte[] arrayOfByte2 = { 0, 127, 0, 0, 6, 0, 0, 0, 0, 0, -34, -83, -66, -17, 0, 117, 10, 32, 1, 0, 0, 4, 0, 0, 4, 0, 3, 0, 0, 0, 0, 0, 4, 0, 5, 10, 32, 1, 0, 0, 2, 0, 6, 0, 31, 0, 14, 0, 1, -34, -83, -66, -17, 0, 3, 0, 0, 0, 2, 0, 4, 0, 1, 0, 1, 0, 2, 0, 0, 0, 0, 0, 4, 0, 5, 10, 32, 1, 0, 0, 2, 0, 6, -5, -1, 0, 2, 0, 2, 0, 0, 0, 0, 0, 4, 0, 5, 10, 32, 1, 0, 0, 1, 0, 2, 0, 0, 3, 0, 2, 0, 0, 0, 0, 0, 4, 0, 5, 10, 32, 1, 0, 0, 1, 0, 2, 0 };

          this.outBuffer.clear();
          this.outBuffer.put(arrayOfByte2);
          this.outBuffer.limit(arrayOfByte2.length);
          this.outBuffer.rewind();
          this.channel.write(this.outBuffer);
        }
        else
        {
          unmarshalNSDataPacket();
        }
        break;
      }
    }
  }

  private void unmarshalNSDataPacket()
    throws IOException
  {
    int i = readShort();

    int j = readInt();

    int k = readByte();
    int m = readInt();
    int n = readShort();
    if ((this.charset == null) || (this.charset.getOracleId() != n)) {
      this.charset = CharacterSet.make(n);
    }

    int i1 = readByte();
    int i2 = readInt();
    int i3 = readShort();

    int i4 = readByte();
    int i5 = readInt();
    int i6 = readShort();

    int i7 = (j - 21) / 9;
    int[] arrayOfInt = new int[i7];
    for (int i8 = 0; i8 < i7; i8++)
    {
      int i9 = readByte();
      int i10 = readInt();
      byte[] arrayOfByte = new byte[i10];
      readBuffer(arrayOfByte, 0, i10);

      for (int i11 = 0; i11 < i10; i11++) {
        if (i11 < 4)
          arrayOfInt[i8] |= (arrayOfByte[i11] & 0xFF) << 8 * (i10 - i11 - 1);
      }
    }
    NTFDCNEvent localNTFDCNEvent = null;
    NTFAQEvent localNTFAQEvent = null;
    int i10 = 0;
    short s = 0;
    NTFRegistration[] arrayOfNTFRegistration = null;
    int i13;
    if (i >= 2)
    {
      int i12 = readShort();
      arrayOfNTFRegistration = new NTFRegistration[arrayOfInt.length];
      for (i13 = 0; i13 < arrayOfInt.length; i13++)
      {
        arrayOfNTFRegistration[i13] = this.ntfManager.getRegistration(arrayOfInt[i13]);
        if (arrayOfNTFRegistration[i13] != null)
        {
          i10 = arrayOfNTFRegistration[i13].getNamespace();
          s = arrayOfNTFRegistration[i13].getDatabaseVersion();
        }
      }

      if (i10 == 2)
      {
        localNTFDCNEvent = new NTFDCNEvent(this, s);
      }
      else if (i10 == 1)
      {
        localNTFAQEvent = new NTFAQEvent(this, s);
      }
      else if (i10 != 0);
    }

    int i12 = 0;
    if (i >= 3)
    {
      i13 = readShort();
      int i14 = readInt();
      int i15 = readByte();
      int i16 = readInt();
      i12 = readShort();
      if ((i10 == 2) && (localNTFDCNEvent != null))
      {
        localNTFDCNEvent.setAdditionalEventType(DatabaseChangeEvent.AdditionalEventType.getEventType(i12));

        if (i12 == 1)
          localNTFDCNEvent.setEventType(DatabaseChangeEvent.EventType.DEREG);
      }
      else if ((i10 == 1) && (localNTFAQEvent != null))
      {
        localNTFAQEvent.setAdditionalEventType(AQNotificationEvent.AdditionalEventType.getEventType(i12));

        if (i12 == 1) {
          localNTFAQEvent.setEventType(AQNotificationEvent.EventType.DEREG);
        }
      }
    }
    if ((i <= 3) ||
      (arrayOfNTFRegistration != null))
    {
      if (i10 == 2)
        for (i13 = 0; i13 < arrayOfNTFRegistration.length; i13++)
          if ((arrayOfNTFRegistration[i13] != null) && (localNTFDCNEvent != null))
          {
            arrayOfNTFRegistration[i13].notify(localNTFDCNEvent);
          }
      if (i10 == 1)
        for (i13 = 0; i13 < arrayOfNTFRegistration.length; i13++)
          if ((arrayOfNTFRegistration[i13] != null) && (localNTFAQEvent != null))
          {
            arrayOfNTFRegistration[i13].notify(localNTFAQEvent);
          }
    }
  }

  void closeThisConnection()
  {
    this.needsToBeClosed = true;
  }

  byte readByte()
    throws IOException
  {
    byte b = 0;
    if (this.currentNSPacketDataBuffer.hasRemaining()) {
      b = this.currentNSPacketDataBuffer.get();
    }
    else {
      getNextNSPacket();
      b = this.currentNSPacketDataBuffer.get();
    }
    return b;
  }

  short readShort()
    throws IOException
  {
    short s = 0;
    if (this.currentNSPacketDataBuffer.remaining() >= 2) {
      s = this.currentNSPacketDataBuffer.getShort();
    }
    else
    {
      int i = readByte() & 0xFF;
      int j = readByte() & 0xFF;
      s = (short)(i << 8 | j);
    }
    return s;
  }

  int readInt()
    throws IOException
  {
    int i = 0;
    if (this.currentNSPacketDataBuffer.remaining() >= 4) {
      i = this.currentNSPacketDataBuffer.getInt();
    }
    else
    {
      int j = readByte() & 0xFF;
      int k = readByte() & 0xFF;
      int m = readByte() & 0xFF;
      int n = readByte() & 0xFF;
      i = j << 24 | k << 16 | m << 8 | n;
    }
    return i;
  }

  long readLong()
    throws IOException
  {
    long l1 = 0L;
    if (this.currentNSPacketDataBuffer.remaining() >= 8) {
      l1 = this.currentNSPacketDataBuffer.getLong();
    }
    else
    {
      long l2 = readByte() & 0xFF;
      long l3 = readByte() & 0xFF;
      long l4 = readByte() & 0xFF;
      long l5 = readByte() & 0xFF;
      long l6 = readByte() & 0xFF;
      long l7 = readByte() & 0xFF;
      long l8 = readByte() & 0xFF;
      long l9 = readByte() & 0xFF;
      l1 = l2 << 56 | l3 << 48 | l4 << 40 | l5 << 32 | l6 << 24 | l7 << 16 | l8 << 8 | l9;
    }
    return l1;
  }

  void readBuffer(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException
  {
    if (this.currentNSPacketDataBuffer.remaining() >= paramInt2) {
      this.currentNSPacketDataBuffer.get(paramArrayOfByte, paramInt1, paramInt2);
    }
    else {
      int i = 0;
      int j = 0;
      int k = 0;
      int m = this.currentNSPacketDataBuffer.remaining();
      this.currentNSPacketDataBuffer.get(paramArrayOfByte, paramInt1, m);
      paramInt1 += m;
      j += m;
      while (i == 0)
      {
        getNextNSPacket();
        m = this.currentNSPacketDataBuffer.remaining();
        k = Math.min(m, paramInt2 - j);
        this.currentNSPacketDataBuffer.get(paramArrayOfByte, paramInt1, k);
        paramInt1 += k;
        j += k;
        if (j == paramInt2)
          i = 1;
      }
    }
  }

  private String packetToString(ByteBuffer paramByteBuffer)
    throws IOException
  {
    int i = 0;

    char[] arrayOfChar = new char[8];
    StringBuffer localStringBuffer = new StringBuffer();
    int k = paramByteBuffer.position();

    while (paramByteBuffer.hasRemaining()) {
      int j = paramByteBuffer.get();
      String str = Integer.toHexString(j & 0xFF);
      str = str.toUpperCase();
      if (str.length() == 1)
        str = "0" + str;
      localStringBuffer.append(str);
      localStringBuffer.append(' ');
      if ((j > 32) && (j < 127))
        arrayOfChar[i] = ((char)j);
      else
        arrayOfChar[i] = '.';
      i++;
      if (i == 8) {
        localStringBuffer.append('|');
        localStringBuffer.append(arrayOfChar);
        localStringBuffer.append('|');
        localStringBuffer.append('\n');
        i = 0;
      }
    }
    if (i != 0) {
      int m = 8 - i;
      for (int n = 0; n < m * 3; n++)
        localStringBuffer.append(' ');
      localStringBuffer.append('|');
      localStringBuffer.append(arrayOfChar, 0, i);
      for (int n = 0; n < m; n++)
        localStringBuffer.append(' ');
      localStringBuffer.append('|');
      localStringBuffer.append('\n');
    }
    localStringBuffer.append("\nEnd of Packet\n\n");
    paramByteBuffer.position(k);
    return localStringBuffer.toString();
  }
}