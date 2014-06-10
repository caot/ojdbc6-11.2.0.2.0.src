package oracle.net.ano;

import java.io.IOException;
import oracle.net.aso.e;
import oracle.net.aso.f;
import oracle.net.aso.g;
import oracle.net.aso.k;
import oracle.net.aso.n;
import oracle.net.aso.p;
import oracle.net.aso.q;
import oracle.net.ns.ClientProfile;
import oracle.net.ns.NetException;
import oracle.net.ns.SQLnetDef;
import oracle.net.ns.SessionAtts;

public class EncryptionService extends Service
  implements SQLnetDef
{
  static final String[] j = { "", "RC4_40", "RC4_56", "RC4_128", "RC4_256", "DES40C", "DES56C", "3DES112", "3DES168", "AES128", "AES192", "AES256" };
  private final byte[] k = { 0, 1, 8, 10, 6, 3, 2, 11, 12, 15, 16, 17 };
  private boolean l = false;

  final int a(SessionAtts paramSessionAtts)
    throws NetException
  {
    super.a(paramSessionAtts);
    this.h = 2;
    this.e = paramSessionAtts.profile.getEncryptionLevelNum();
    String[] arrayOfString = a(arrayOfString = paramSessionAtts.profile.getEncryptionServices(), j);
    this.f = new int[arrayOfString.length];
    for (int i = 0; i < this.f.length; i++)
      this.f[i] = a(j, arrayOfString[i]);
    this.f = a(this.f, this.e);
    this.g = new byte[this.f.length];
    for (i = 0; i < this.g.length; i++)
      this.g[i] = this.k[this.f[i]];
    i = 1;
    if (this.e == 3)
      throw new NetException(315);
    if (this.e == 3)
      i = this.f.length == 0 ? 9 : 17;
    return i;
  }

  public boolean isActive()
  {
    return this.l;
  }

  final void a(int paramInt)
    throws NetException, IOException
  {
    if (paramInt != 2)
      throw new NetException(305);
    this.c.j();
    int i = this.c.e();
    this.i = -1;
    for (int m = 0; m < j.length; m++)
      if (this.k[m] == i)
        this.i = ((short)m);
    this.l = (this.i > 0);
  }

  final void d()
    throws NetException, IOException
  {
    if (this.i < 0)
      throw new NetException(316);
    for (int i = 0; (i < this.f.length) && (this.f[i] != this.i); i++);
    if (i == this.f.length)
      throw new NetException(316);
  }

  final void e()
    throws NetException, IOException
  {
    if (this.l)
    {
      if (j[this.i].equals("RC4_40"))
        this.b.g = new f(true, 40);
      else if (j[this.i].equals("RC4_56"))
        this.b.g = new f(true, 56);
      else if (j[this.i].equals("RC4_128"))
        this.b.g = new f(true, 128);
      else if (j[this.i].equals("RC4_256"))
        this.b.g = new f(true, 256);
      else if (j[this.i].equals("DES40C"))
        this.b.g = new p();
      else if (j[this.i].equals("DES56C"))
        this.b.g = new e();
      else if (j[this.i].equals("3DES112"))
        this.b.g = new k();
      else if (j[this.i].equals("3DES168"))
        this.b.g = new q();
      else if (j[this.i].equals("AES128"))
        this.b.g = new n(1, 1);
      else if (j[this.i].equals("AES192"))
        this.b.g = new n(1, 2);
      else if (j[this.i].equals("AES256"))
        this.b.g = new n(1, 3);
      else
        throw new NetException(317);
      this.b.g.a(this.b.e, this.b.d);
    }
  }
}