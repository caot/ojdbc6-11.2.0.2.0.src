package oracle.net.aso;

final class o
  implements c
{
  private int[] a = new int[80];
  private long b;
  private int[] c = new int[5];

  o()
  {
    a();
  }

  public final int b()
  {
    return 20;
  }

  private void a(byte paramByte)
  {
    int i = ((int)this.b & 0x3F) >>> 2;
    int j = (((int)this.b ^ 0xFFFFFFFF) & 0x3) << 3;
    this.a[i] = (this.a[i] & (255 << j ^ 0xFFFFFFFF) | (paramByte & 0xFF) << j);
    if (((int)this.b & 0x3F) == 63)
      c();
    this.b += 1L;
  }

  public final void a(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    while (paramInt2 > 0)
      if ((((int)this.b & 0x3) != 0) || (paramInt2 < 4))
      {
        a(paramArrayOfByte[paramInt1]);
        paramInt1++;
        paramInt2--;
      }
      else
      {
        int i = ((int)this.b & 0x3F) >> 2;
        this.a[i] = ((paramArrayOfByte[paramInt1] & 0xFF) << 24 | (paramArrayOfByte[(paramInt1 + 1)] & 0xFF) << 16 | (paramArrayOfByte[(paramInt1 + 2)] & 0xFF) << 8 | paramArrayOfByte[(paramInt1 + 3)] & 0xFF);
        this.b += 4L;
        if (((int)this.b & 0x3F) == 0)
          c();
        paramInt1 += 4;
        paramInt2 -= 4;
      }
  }

  public final void a()
  {
    this.c[0] = 1732584193;
    this.c[1] = -271733879;
    this.c[2] = -1732584194;
    this.c[3] = 271733878;
    this.c[4] = -1009589776;
    for (int i = 0; i < 80; i++)
      this.a[i] = 0;
    this.b = 0L;
  }

  public final int a(byte[] paramArrayOfByte, int paramInt)
  {
    if (paramArrayOfByte.length - paramInt < 20)
      return 0;
    long l = this.b << 3;
    a((byte)-128);
    this.a[14] = ((int)(l >>> 32));
    this.a[15] = ((int)l);
    this.b += 8L;
    c();
    int i = 0;
    int j = 0;
    paramArrayOfByte[(paramInt + j)] = ((byte)(this.c[i] >>> 24));
    paramArrayOfByte[(paramInt + j + 1)] = ((byte)(this.c[i] >>> 16));
    paramArrayOfByte[(paramInt + j + 2)] = ((byte)(this.c[i] >>> 8));
    paramArrayOfByte[(paramInt + j + 3)] = ((byte)this.c[i]);
    j += 4;
    a();
    return 20;
  }

  private void c()
  {
    a(this.a);
    int i = this.c[0];
    int j = this.c[1];
    int k = this.c[2];
    int m = this.c[3];
    int n = this.c[4];
    for (int i1 = 0; i1 < 20; i1++)
      a(this.c, this.a, this.c[0], this.c[1], this.c[2], this.c[3], this.c[4], i1);
    for (i1 = 20; i1 < 40; i1++)
      b(this.c, this.a, this.c[0], this.c[1], this.c[2], this.c[3], this.c[4], i1);
    for (i1 = 40; i1 < 60; i1++)
      c(this.c, this.a, this.c[0], this.c[1], this.c[2], this.c[3], this.c[4], i1);
    for (i1 = 60; i1 < 80; i1++)
      d(this.c, this.a, this.c[0], this.c[1], this.c[2], this.c[3], this.c[4], i1);
    this.c[0] += i;
    this.c[1] += j;
    this.c[2] += k;
    this.c[3] += m;
    this.c[4] += n;
  }

  private static void a(int[] paramArrayOfInt)
  {
    for (int i = 16; i < 80; i++)
    {
      int j = paramArrayOfInt[(i - 16)] ^ paramArrayOfInt[(i - 14)] ^ paramArrayOfInt[(i - 8)] ^ paramArrayOfInt[(i - 3)];
      paramArrayOfInt[i] = (j << 1 | j >>> 31);
    }
  }

  private static void a(int[] paramArrayOfInt1, int[] paramArrayOfInt2, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
  {
    int i = (paramInt1 << 5 | paramInt1 >>> 27) + (paramInt2 & paramInt3 | (paramInt2 ^ 0xFFFFFFFF) & paramInt4) + paramInt5 + paramArrayOfInt2[paramInt6] + 1518500249;
    paramArrayOfInt1[4] = paramArrayOfInt1[3];
    paramArrayOfInt1[3] = paramArrayOfInt1[2];
    paramArrayOfInt1[2] = (paramArrayOfInt1[1] << 30 | paramArrayOfInt1[1] >>> 2);
    paramArrayOfInt1[1] = paramArrayOfInt1[0];
    paramArrayOfInt1[0] = i;
  }

  private static void b(int[] paramArrayOfInt1, int[] paramArrayOfInt2, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
  {
    int i = (paramInt1 << 5 | paramInt1 >>> 27) + (paramInt2 ^ paramInt3 ^ paramInt4) + paramInt5 + paramArrayOfInt2[paramInt6] + 1859775393;
    paramArrayOfInt1[4] = paramArrayOfInt1[3];
    paramArrayOfInt1[3] = paramArrayOfInt1[2];
    paramArrayOfInt1[2] = (paramArrayOfInt1[1] << 30 | paramArrayOfInt1[1] >>> 2);
    paramArrayOfInt1[1] = paramArrayOfInt1[0];
    paramArrayOfInt1[0] = i;
  }

  private static void c(int[] paramArrayOfInt1, int[] paramArrayOfInt2, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
  {
    int i = (paramInt1 << 5 | paramInt1 >>> 27) + (paramInt2 & paramInt3 | paramInt2 & paramInt4 | paramInt3 & paramInt4) + paramInt5 + paramArrayOfInt2[paramInt6] + -1894007588;
    paramArrayOfInt1[4] = paramArrayOfInt1[3];
    paramArrayOfInt1[3] = paramArrayOfInt1[2];
    paramArrayOfInt1[2] = (paramArrayOfInt1[1] << 30 | paramArrayOfInt1[1] >>> 2);
    paramArrayOfInt1[1] = paramArrayOfInt1[0];
    paramArrayOfInt1[0] = i;
  }

  private static void d(int[] paramArrayOfInt1, int[] paramArrayOfInt2, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
  {
    int i = (paramInt1 << 5 | paramInt1 >>> 27) + (paramInt2 ^ paramInt3 ^ paramInt4) + paramInt5 + paramArrayOfInt2[paramInt6] + -899497514;
    paramArrayOfInt1[4] = paramArrayOfInt1[3];
    paramArrayOfInt1[3] = paramArrayOfInt1[2];
    paramArrayOfInt1[2] = (paramArrayOfInt1[1] << 30 | paramArrayOfInt1[1] >>> 2);
    paramArrayOfInt1[1] = paramArrayOfInt1[0];
    paramArrayOfInt1[0] = i;
  }
}