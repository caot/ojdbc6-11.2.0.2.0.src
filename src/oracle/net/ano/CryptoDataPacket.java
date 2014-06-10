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
        c();
      }
      catch (IOException localIOException2)
      {
        throw (localIOException1 = localIOException2);
      }
    try
    {
      a();
    }
    catch (IOException localIOException3)
    {
      throw (localIOException1 = localIOException3);
    }
    super.send(paramInt);
  }

  protected final void a()
    throws IOException
  {
    byte[] arrayOfByte1 = new byte[this.availableBytesToSend];
    System.arraycopy(this.buffer, this.dataOff, arrayOfByte1, 0, arrayOfByte1.length);
    byte[] arrayOfByte2 = null;
    this.dataLen = this.availableBytesToSend;
    if ((this.b != null) && ((arrayOfByte2 = this.b.a(arrayOfByte1, arrayOfByte1.length)) != null))
      this.dataLen += arrayOfByte2.length;
    byte[] arrayOfByte3 = new byte[this.dataLen];
    System.arraycopy(arrayOfByte1, 0, arrayOfByte3, 0, arrayOfByte1.length);
    if (arrayOfByte2 != null)
      System.arraycopy(arrayOfByte2, 0, arrayOfByte3, arrayOfByte1.length, arrayOfByte2.length);
    byte[] arrayOfByte4;
    if ((arrayOfByte4 = this.a.b(arrayOfByte3)) == null)
      throw new IOException("Fail to encrypt buffer");
    this.dataLen = arrayOfByte4.length;
    if (this.b != null)
      System.arraycopy(this.a != null ? arrayOfByte4 : arrayOfByte3, 0, this.buffer, this.dataOff, this.dataLen);
    this.buffer[(this.dataOff + this.dataLen)] = ((byte)(this.c >= 2 ? 1 : 0));
    this.dataLen += 1;
    this.pktOffset = (10 + this.dataLen);
    this.length = (10 + this.dataLen);
  }

  protected final void b()
    throws IOException
  {
    byte[] arrayOfByte1 = new byte[this.dataLen - 1];
    this.dataLen -= 1;
    System.arraycopy(this.buffer, this.dataOff, arrayOfByte1, 0, this.dataLen);
    byte[] arrayOfByte2;
    if ((arrayOfByte2 = this.a != null ? this.a.a(arrayOfByte1) : arrayOfByte1) == null)
      throw new IOException("Bad buffer - Fail to decrypt buffer");
    this.dataLen = arrayOfByte2.length;
    byte[] arrayOfByte3 = new byte[this.b.b()];
    this.dataLen -= this.b.b();
    System.arraycopy(arrayOfByte2, this.dataLen, arrayOfByte3, 0, this.b.b());
    byte[] arrayOfByte4 = new byte[this.dataLen];
    System.arraycopy(arrayOfByte2, 0, arrayOfByte4, 0, this.dataLen);
    if (this.b.b(arrayOfByte4, arrayOfByte3) == true)
      throw new IOException("Checksum fail");
    System.arraycopy(this.b != null ? arrayOfByte4 : arrayOfByte2, 0, this.buffer, this.dataOff, this.dataLen);
    this.length = (this.dataOff + this.dataLen);
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