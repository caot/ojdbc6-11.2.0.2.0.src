package oracle.net.aso;

public final class l
{
  private byte[] a = null;
  private int b = 0;
  private byte[] c = null;
  private int d = 0;
  private byte[] e = null;
  private byte[] f = null;
  private short g;
  private short h;
  private char[] i = new char[65];
  private char[] j = new char[65];
  private byte[] k;
  private int l;
  private int m;
  private int n;
  private byte[] o;
  private static final short[] p = { 40, 41, 56, 128, 256 };
  private static final short[] q = { 40, 64, 56, 128, 256 };
  private static final short[] r = { 80, 112, 112, 512, 512 };
  private static final short[] s = { 300, 512, 512, 512, 512 };
  private static final byte[] t = { 2, 83, -77, -14, -90, -115, 61, -69, 106, -61, -103, 9, -64, -41, 4, 5, -14, 91, -126, 97, 107, 122, -24, -36, 29, 123, 3, -106, 53, -30, -37, -17, 67, 102, -6, -48, 76, -63 };
  private static final byte[] u = { 12, 54, -127, -73, 4, 71, 3, -96, 120, 96, 81, 38, -116, -22, -101, -68, -93, 62, 124, 1, -85, 54, -117, 34, 117, -104, 119, 102, 53, -59, -128, -43, 36, -46, 80, 99, -72, -13 };
  private static final byte[] v = { -126, -104, -34, 73, -34, -9, 9, -27, -32, 13, -80, -96, -91, -100, -87, -14, 61, -10, -58, -89, -23, 74, 68, -93, -31, -121, 46, -11, 76, 31, -95, 122, -33, 92, -14, 117, -127, -19, 81, -61, 38, -18, -117, -31, 4, 3, 30, 103, 80, 83, -75, 124, 75, 69, 111, 21, 74, 23, 86, 11, 90, 21, -107, -91 };
  private static final byte[] w = { -36, -114, -93, 27, 8, 96, 105, -118, -52, -10, -47, -98, -121, 14, 52, -4, 103, -59, 89, 11, 78, -90, -79, 60, -43, -3, -17, 21, -84, -99, 95, 63, 33, 76, -36, 7, -52, -121, 74, -77, 1, -41, 127, 44, 67, 51, 81, 60, -34, 11, 30, -50, 100, 71, 118, 87, 92, 81, -52, -104, -77, -2, -25, -17 };
  private static final byte[][] x = { t, v, v, v, v };
  private static final byte[][] y = { u, w, w, w, w };

  public l(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, short paramShort1, short paramShort2)
  {
    if ((paramArrayOfByte1 != null) && (paramArrayOfByte2 != null))
    {
      this.e = paramArrayOfByte1;
      this.f = paramArrayOfByte2;
      this.h = paramShort2;
      this.g = paramShort1;
      return;
    }
    a(paramArrayOfByte1, paramArrayOfByte2, 40);
  }

  private void a(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt)
  {
    this.a = paramArrayOfByte1;
    this.b = (paramArrayOfByte1 != null ? paramArrayOfByte1.length : 0);
    this.c = paramArrayOfByte2;
    this.d = (paramArrayOfByte2 != null ? paramArrayOfByte2.length : 0);
    a(paramInt);
  }

  public final byte[] a()
  {
    b();
    return this.k;
  }

  public final byte[] a(byte[] paramArrayOfByte, int paramInt)
  {
    c(paramArrayOfByte, paramInt);
    return this.o;
  }

  private static void b(byte[] paramArrayOfByte, int paramInt)
  {
    new h().a(paramArrayOfByte, paramInt);
  }

  private void a(int paramInt)
  {
    for (int i1 = 0; i1 < p.length; i1++)
      if ((paramInt >= p[i1]) && (paramInt <= q[i1]))
      {
        this.g = r[i1];
        this.h = s[i1];
        this.e = new byte[(this.h + 7) / 8];
        this.f = new byte[(this.h + 7) / 8];
        if ((this.b * 8 >= this.h) && (this.d * 8 >= this.h))
        {
          System.arraycopy(this.a, 0, this.e, 0, this.e.length);
          System.arraycopy(this.c, 0, this.f, 0, this.f.length);
          return;
        }
        System.arraycopy(x[i1], 0, this.e, 0, this.e.length);
        System.arraycopy(y[i1], 0, this.f, 0, this.f.length);
        return;
      }
  }

  private void b()
  {
    char[] arrayOfChar1 = new char[65];
    char[] arrayOfChar2 = new char[65];
    byte[] arrayOfByte = new byte[128];
    int i1 = (short)(this.g + 7) / 8;
    int i2 = (short)(this.h + 7) / 8;
    this.l = ((short)i2);
    this.m = (this.h / 16 + 1);
    this.k = new byte[this.l];
    b(arrayOfByte, i1);
    int tmp80_79 = 0;
    byte[] tmp80_77 = arrayOfByte;
    tmp80_77[tmp80_79] = ((byte)(tmp80_77[tmp80_79] & 255 >>> i1 - 8 * this.g));
    oracle.net.aso.r.a(arrayOfChar1, this.m, this.e, i2);
    oracle.net.aso.r.a(this.j, this.m, arrayOfByte, i1);
    oracle.net.aso.r.a(this.i, this.m, this.f, i2);
    oracle.net.aso.r.a(arrayOfChar2, arrayOfChar1, this.j, this.i, this.m);
    oracle.net.aso.r.a(this.k, this.l, arrayOfChar2, this.m);
  }

  private void c(byte[] paramArrayOfByte, int paramInt)
  {
    char[] arrayOfChar1 = new char[65];
    char[] arrayOfChar2 = new char[65];
    this.n = this.l;
    this.o = new byte[this.n];
    oracle.net.aso.r.a(arrayOfChar1, this.m, paramArrayOfByte, paramInt);
    oracle.net.aso.r.a(arrayOfChar2, arrayOfChar1, this.j, this.i, this.m);
    oracle.net.aso.r.a(this.o, this.n, arrayOfChar2, this.m);
  }
}