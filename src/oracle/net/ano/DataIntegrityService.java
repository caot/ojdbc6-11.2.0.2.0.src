package oracle.net.ano;

import java.io.IOException;
import java.io.PrintStream;
import oracle.net.aso.b;
import oracle.net.aso.d;
import oracle.net.aso.i;
import oracle.net.aso.l;
import oracle.net.ns.ClientProfile;
import oracle.net.ns.NetException;
import oracle.net.ns.SQLnetDef;
import oracle.net.ns.SessionAtts;

public class DataIntegrityService extends Service
  implements SQLnetDef
{
  static final String[] j = { "", "MD5", "SHA1" };
  private static final byte[] k = { 0, 1, 3 };
  private boolean l = false;
  private byte[] m;

  final int a(SessionAtts paramSessionAtts)
    throws NetException
  {
    super.a(paramSessionAtts);
    this.h = 3;
    this.e = paramSessionAtts.profile.getDataIntegrityLevelNum();
    String[] arrayOfString = a(arrayOfString = paramSessionAtts.profile.getDataIntegrityServices(), j);
    this.f = new int[arrayOfString.length];
    int i;
    for (i = 0; i < this.f.length; i++)
      this.f[i] = a(j, arrayOfString[i]);
    this.f = a(this.f, this.e);
    this.g = new byte[this.f.length];
    for (i = 0; i < this.g.length; i++)
      this.g[i] = k[this.f[i]];
    i = 1;
    if (this.e == 3)
      throw new NetException(315);
    if (this.e == 3)
      i = this.f.length == 0 ? 9 : 17;
    return i;
  }

  final void a(int paramInt)
    throws NetException, IOException
  {
    this.c.j();
    int i = this.c.e();
    this.i = -1;
    for (int n = 0; n < j.length; n++)
      if (k[n] == i)
        this.i = ((short)n);
    if ((paramInt != 2) && (paramInt == 8))
    {
      short n = (short)this.c.f();
      short s = (short)this.c.f();
      byte[] arrayOfByte1 = this.c.l();
      byte[] arrayOfByte2 = this.c.l();
      byte[] arrayOfByte3 = this.c.l();
      byte[] arrayOfByte4 = this.c.l();
      if ((n <= 0) || (s <= 0))
        throw new IOException("Bad parameters from server");
      int i1 = (s + 7) / 8;
      if ((arrayOfByte3.length != i1) || (arrayOfByte2.length != i1))
        throw new IOException("DiffieHellman negotiation out of synch");
      l locall = new l(arrayOfByte1, arrayOfByte2, n, s);
      this.m = locall.a();
      this.d.ano.c = this.m;
      this.d.ano.d = arrayOfByte4;
      this.d.ano.e = locall.a(arrayOfByte3, arrayOfByte3.length);
    }
    this.l = (this.i > 0);
  }

  final void d()
    throws NetException, IOException
  {
    if (this.i < 0)
      throw new NetException(319);
    for (int i = 0; (i < this.f.length) && (this.f[i] != this.i); i++);
    if (i == this.f.length)
      throw new NetException(319);
  }

  public boolean isActive()
  {
    return this.l;
  }

  final void e()
    throws NetException, IOException
  {
    if (this.l)
    {
      if (j[this.i].equals("MD5"))
        this.b.h = new i();
      else if (j[this.i].equals("SHA1"))
        this.b.h = new b();
      else
        throw new NetException(318);
      this.b.h.a(this.b.e, this.b.d);
    }
  }

  public static void printInHex(int paramInt)
  {
    byte[] arrayOfByte = toHex(paramInt);
    System.out.print(new String(arrayOfByte));
  }

  public static byte[] toHex(int paramInt)
  {
    byte[] arrayOfByte = new byte[8];
    for (int i = 7; i >= 0; i--)
    {
      arrayOfByte[i] = nibbleToHex((byte)(paramInt & 0xF));
      paramInt >>= 4;
    }
    return arrayOfByte;
  }

  public static byte nibbleToHexX(byte paramByte)
  {
    return (byte)(paramByte - 10 + ((paramByte = (byte)(paramByte & 0xF)) < 10 ? 48 : 65));
  }

  public static byte nibbleToHex(byte byte0)
  {
      //if((byte0 &= 0xf) >= 10) goto _L2; else goto _L1
    if ((byte0 &= 0xf) <10) {
_L1:
//      byte0;
//      48;
//        goto _L3
      return  (byte)(byte0 +  48);
    } else {
_L2:
//      byte0 - 10;
//      65;
      return (byte)(byte0 - 10 + 65);
    }
//_L3:
//      JVM INSTR iadd ;
//      (byte);
//      return;
  }

  public static String bArray2String(byte[] paramArrayOfByte)
  {
    StringBuffer localStringBuffer = new StringBuffer(paramArrayOfByte.length * 2);
    for (int i = 0; i < paramArrayOfByte.length; i++)
    {
      localStringBuffer.append((char)nibbleToHex((byte)((paramArrayOfByte[i] & 0xF0) >> 4)));
      localStringBuffer.append((char)nibbleToHex((byte)(paramArrayOfByte[i] & 0xF)));
    }
    return localStringBuffer.toString();
  }
}