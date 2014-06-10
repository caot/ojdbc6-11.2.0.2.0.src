package oracle.net.ano;

import java.io.IOException;
import oracle.net.aso.d;
import oracle.net.aso.g;
import oracle.net.ns.NetException;
import oracle.net.ns.SQLnetDef;
import oracle.net.ns.SessionAtts;

public class Ano
  implements SQLnetDef
{
  protected SessionAtts a;
  protected AnoComm b;
  protected byte[] c;
  protected byte[] d;
  protected byte[] e;
  protected boolean f = false;
  protected g g;
  protected d h;
  private int i = 1;
  private Service[] j;
  private byte[] k;
  private boolean l;

  public void init(SessionAtts paramSessionAtts)
    throws NetException
  {
    this.a = paramSessionAtts;
    this.a.ano = this;
    this.j = new Service[Service.a.length];
    this.b = new AnoComm(paramSessionAtts);
    for (int m = 1; m < Service.a.length; m++)
    {
      Service localService;
      try
      {
        localService = (Service)Class.forName("oracle.net.ano." + Service.a[m]).newInstance();
      }
      catch (Exception localException)
      {
        throw new NetException(308);
      }
      this.i |= localService.a(paramSessionAtts);
      this.j[localService.h] = localService;
    }
    if (((this.i & 0x10) > 0) && ((this.i & 0x8) > 0))
      this.i &= -17;
  }

  public void negotiation()
    throws NetException, IOException
  {
    int m = 0;
    for (int n = 1; n < 5; n++)
      m += this.j[n].f();
    n = 13 + m;
    a(n, this.j.length - 1, (short)0);
    this.j[4].b();
    this.j[1].b();
    this.j[2].b();
    this.j[3].b();
    this.b.b();
    int[] arrayOfInt1 = a();
    for (int i1 = 0; i1 < arrayOfInt1[2]; i1++)
    {
      int[] arrayOfInt2;
      if ((arrayOfInt2 = Service.a(this.b))[2] != 0)
        throw new NetException(arrayOfInt2[2]);
      this.j[arrayOfInt2[0]].c(arrayOfInt2[1]);
    }
    for (i1 = 1; i1 < 5; i1++)
      this.j[i1].e();
    i1 = 0;
    int i2 = 0;
    if (this.c != null)
    {
      i1 = 0 + (12 + this.c.length);
      i2++;
    }
    int i3;
    if ((i3 = ((AuthenticationService)this.j[1]).a()) > 0)
    {
      i1 += i3;
      i2++;
    }
    if (i1 > 0)
    {
      i1 += 13;
      a(i1, i2, (short)0);
      if (this.c != null)
      {
        this.j[3].b(1);
        this.b.a(this.c);
      }
      if (i3 > 0)
        ((AuthenticationService)this.j[1]).g();
      this.b.b();
      ((AuthenticationService)this.j[1]).h();
    }
    this.f = ((this.j[2].isActive()) || (this.j[3].isActive()));
    if (this.f)
      this.a.turnEncryptionOn(new AnoNetInputStream(this.a), new AnoNetOutputStream(this.a));
  }

  public int getNAFlags()
  {
    return this.i;
  }

  public void setO3logSessionKey(byte[] paramArrayOfByte)
  {
    this.k = paramArrayOfByte;
  }

  public byte[] getO3logSessionKey()
  {
    return this.k;
  }

  public g getEncryptionAlg()
  {
    return this.g;
  }

  public d getDataIntegrityAlg()
  {
    return this.h;
  }

  public String getEncryptionName()
  {
    if ((this.j == null) || (this.j.length <= 2))
      return "";
    return EncryptionService.j[this.j[2].i];
  }

  public String getDataIntegrityName()
  {
    if ((this.j == null) || (this.j.length <= 3))
      return "";
    return DataIntegrityService.j[this.j[3].i];
  }

  public String getAuthenticationAdaptorName()
  {
    if ((this.j == null) || (this.j.length <= 1))
      return "";
    return AuthenticationService.j[this.j[1].i];
  }

  public void setRenewKey(boolean paramBoolean)
  {
    this.l = paramBoolean;
  }

  public boolean getRenewKey()
  {
    return this.l;
  }

  protected final void a(int paramInt1, int paramInt2, short paramShort)
    throws NetException, IOException
  {
    this.b.b(-559038737L);
    this.b.c(paramInt1);
    this.b.d();
    this.b.c(paramInt2);
    this.b.b(paramShort);
  }

  final int[] a()
    throws NetException, IOException
  {
    long l1;
    if ((l1 = this.b.o()) != -559038737L)
      throw new NetException(302);
    int m = this.b.n();
    int n = (int)this.b.o();
    int i1 = this.b.n();
    int i2 = this.b.m();
    int[] arrayOfInt;
    (arrayOfInt = new int[4])[0] = m;
    arrayOfInt[1] = n;
    arrayOfInt[2] = i1;
    arrayOfInt[3] = i2;
    return arrayOfInt;
  }
}