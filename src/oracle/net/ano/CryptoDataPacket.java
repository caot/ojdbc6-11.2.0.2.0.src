package oracle.net.ano;

import java.io.IOException;
import oracle.net.aso.d;
import oracle.net.aso.g;
import oracle.net.ns.DataPacket;
import oracle.net.ns.NetException;
import oracle.net.ns.SQLnetDef;
import oracle.net.ns.SessionAtts;

public class CryptoDataPacket extends DataPacket
  implements SQLnetDef
{
  private g a = null;
  private d b = null;
  private int c = 0;
  private int d = 0;
  private Ano e = null;

  public CryptoDataPacket(SessionAtts paramSessionAtts)
  {
    super(paramSessionAtts);
    this.e = paramSessionAtts.ano;
    if (paramSessionAtts.ano.g != null)
    {
      this.a = paramSessionAtts.ano.g;
      this.d += this.a.a();
    }
    if (paramSessionAtts.ano.h != null)
    {
      this.b = paramSessionAtts.ano.h;
      this.d += this.b.b();
    }
    this.d += 1;
  }

  protected void createBuffer(int paramInt)
  {
    super.createBuffer(paramInt + (this.sAtts.poolEnabled ? 16 : 0));
  }

  public void receive()
    throws IOException, NetException
  {
    super.receive();
    if (this.type != 6)
      return;
    this.e = this.sAtts.ano;
    if (this.e.g != null)
    {
      this.a = this.e.g;
      this.d += this.a.a();
      if (this.e.getRenewKey())
        this.a.b(null, null);
    }
    if (this.e.h != null)
    {
      this.b = this.e.h;
      this.d += this.b.b();
      if (this.e.getRenewKey())
        this.b.a();
    }
    this.d += 1;
    this.e.setRenewKey(false);
    try
    {
      b();
      return;
    }
    catch (IOException localIOException2)
    {
      IOException localIOException1;
      throw (localIOException1 = localIOException2);
    }
  }

  protected int putDataInBuffer(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException
  {
    int i;
    if ((i = Math.min(this.buffer.length - this.sessionIdSize - this.d - this.pktOffset, paramInt2)) > 0)
    {
      System.arraycopy(paramArrayOfByte, paramInt1, this.buffer, this.pktOffset, i);
      this.pktOffset += i;
      this.isBufferFull = (this.pktOffset + this.d == this.buffer.length - this.sessionIdSize);
      this.availableBytesToSend = (this.dataOff < this.pktOffset ? this.pktOffset - this.dataOff : 0);
    }
    return i;
  }

  protected void send(int paramInt)
    throws IOException
  {
    IOException localIOException1;
    if (this.c < 2)
      try
      {
_L1:
        c();
      }
      catch (IOException localIOException2)
      {
        throw (localIOException1 = localIOException2);
      }
    try
    {
_L2:
      a();
    }
    catch (IOException localIOException3)
    {
      throw (localIOException1 = localIOException3);
    }
_L3:
    super.send(paramInt);
  }

  protected final void a()
    throws IOException
  {
    byte[] abyte0 = new byte[availableBytesToSend];
    System.arraycopy(buffer, dataOff, abyte0, 0, abyte0.length);
    byte[] abyte1 = null;
    this.dataLen = availableBytesToSend;
    if (b != null && (abyte1 = this.b.a(abyte0, abyte0.length)) != null)
      dataLen += abyte1.length;
    byte[] abyte2 = new byte[dataLen];
    System.arraycopy(abyte0, 0, abyte2, 0, abyte0.length);
    if (abyte1 != null)
      System.arraycopy(abyte1, 0, abyte2, abyte0.length, abyte1.length);

    //if(a == null) goto _L2; else goto _L1
    if(a != null) {
//_L1:
    byte[] abyte3;
    if ((abyte3 = this.a.b(abyte2)) == null)
      throw new IOException("Fail to encrypt buffer");
    this.dataLen = abyte3.length;
    //abyte3;
    System.arraycopy(abyte3, 0, this.buffer, this.dataOff, this.dataLen);
    } else {
L2:
      //if(b == null) goto _L5; else goto _L4

    if (b != null) {
_L4:
      // abyte2;
_L3:
      System.arraycopy(abyte2, 0, this.buffer, this.dataOff, this.dataLen);
    }
    }
_L5:
    buffer[dataOff + dataLen] = (byte)(c >= 2 ? 1 : 0);
    dataLen += 1;
    pktOffset = 10 + this.dataLen;
    length = 10 + this.dataLen;
  }

  protected final void b()
    throws IOException
  {
    byte[] abyte1;
    byte[] abyte0 = new byte[dataLen - 1];
    dataLen -= 1;
    System.arraycopy(buffer, dataOff, abyte0, 0, this.dataLen);
    if ((abyte1 = a != null ? this.a.a(abyte0) : abyte0) == null)
      throw new IOException("Bad buffer - Fail to decrypt buffer");
    dataLen = abyte1.length;

    //if(b == null) goto _L2; else goto _L1
    if(b != null) {
//_L1:
    byte[] abyte2 = new byte[this.b.b()];
    dataLen -= b.b();
    System.arraycopy(abyte1, dataLen, abyte2, 0, b.b());
    byte[] abyte3 = new byte[this.dataLen];
    System.arraycopy(abyte1, 0, abyte3, 0, this.dataLen);
    if (b.b(abyte3, abyte2))
      throw new IOException("Checksum fail");
//    abyte3;
//    goto _L3
    System.arraycopy(abyte3, 0, this.buffer, this.dataOff, this.dataLen);
    } else {
_L2:
    System.arraycopy(abyte1, 0, this.buffer, this.dataOff, this.dataLen);
    }
    length = dataOff + dataLen;
    this.pktOffset = 10;
  }

  private void c()
    throws IOException
  {
    byte[] arrayOfByte1 = this.e.e;
    byte[] arrayOfByte2;
    if ((arrayOfByte2 = this.e.getO3logSessionKey()) != null)
    {
      byte[] arrayOfByte3 = new byte[Math.max(arrayOfByte2.length, arrayOfByte1.length)];
      byte[] arrayOfByte4;
      System.arraycopy(arrayOfByte4 = arrayOfByte2.length > arrayOfByte1.length ? arrayOfByte2 : arrayOfByte1, 0, arrayOfByte3, 0, arrayOfByte4.length);
      if ((arrayOfByte2.length < 8) || (arrayOfByte1.length < 8))
        throw new IOException("Key is too small");
      for (int i = 0; i < 8; i++)
        arrayOfByte3[i] = ((byte)((arrayOfByte2[i] ^ arrayOfByte1[i]) & 0xFF));
      byte[] arrayOfByte5 = this.e.d;
      if (this.a != null)
        this.a.b(arrayOfByte3, arrayOfByte5);
      if (this.b != null)
        this.b.c(arrayOfByte3, arrayOfByte5);
      this.c = 3;
    }
  }
}