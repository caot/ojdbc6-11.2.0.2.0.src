package oracle.net.ano;

import java.io.IOException;
import oracle.net.ns.NetException;
import oracle.net.ns.SQLnetDef;
import oracle.net.ns.SessionAtts;

public class SupervisorService extends Service
  implements SQLnetDef
{
  private byte[] j;
  private int[] k;
  private int[] l;
  private int m;
  private int n;

  final int a(SessionAtts paramSessionAtts)
    throws NetException
  {
    super.a(paramSessionAtts);
    this.h = 4;
    this.j = a();
    this.m = 0;
    this.n = 2;
    this.k = new int[4];
    this.k[0] = 4;
    this.k[1] = 1;
    this.k[2] = 2;
    this.k[3] = 3;
    return 1;
  }

  static byte[] a()
  {
    byte[] arrayOfByte = new byte[8];
    for (int i = 0; i < arrayOfByte.length; i++)
      arrayOfByte[i] = 9;
    return arrayOfByte;
  }

  final void b()
    throws NetException, IOException
  {
    b(3);
    this.c.c();
    this.c.a(this.j);
    this.c.a(this.k);
  }

  final int c()
  {
    return 12 + this.j.length + 4 + 10 + this.k.length * 2;
  }

  final void a(int paramInt)
    throws NetException, IOException
  {
    this.c.j();
    int i;
    if ((i = this.c.i()) != 31)
      throw new NetException(306);
    this.l = this.c.h();
  }

  final void d()
    throws NetException, IOException
  {
    for (int i = 0; i < this.l.length; i++)
    {
      for (int i1 = 0; i1 < this.k.length; i1++)
        if (this.l[i] == this.k[i1])
        {
          this.m += 1;
          break;
        }
      if (i1 == this.k.length)
        throw new NetException(320);
    }
    if (this.m != this.n)
      throw new NetException(321);
  }
}