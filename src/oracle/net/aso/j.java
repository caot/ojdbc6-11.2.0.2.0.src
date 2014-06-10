package oracle.net.aso;

final class j
  implements c
{
  private long[] a = new long[2];
  private long[] b = new long[4];
  private char[] c = new char[64];
  private char[] d = new char[16];
  private static final byte[] e = { -128, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

  private static long a(long paramLong1, long paramLong2, long paramLong3)
  {
    return paramLong1 & paramLong2 | (paramLong1 ^ 0xFFFFFFFF) & paramLong3;
  }

  private static long b(long paramLong1, long paramLong2, long paramLong3)
  {
    return paramLong1 & paramLong3 | paramLong2 & (paramLong3 ^ 0xFFFFFFFF);
  }

  private static long c(long paramLong1, long paramLong2, long paramLong3)
  {
    return paramLong1 ^ paramLong2 ^ paramLong3;
  }

  private static long d(long paramLong1, long paramLong2, long paramLong3)
  {
    return paramLong2 ^ (paramLong1 | paramLong3 ^ 0xFFFFFFFF);
  }

  private static long a(long paramLong, int paramInt)
  {
    long l1 = -1L;
    long l2 = paramLong << paramInt;
    long l3;
    long l4 = ((l3 = l1 << paramInt) ^ 0xFFFFFFFF) & l1;
    long l5;
    return (l5 = paramLong >>> 32 - paramInt & l4) | l2;
  }

  private long a(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, int paramInt, long paramLong6)
  {
    return paramLong1 = (paramLong1 = a(paramLong1 += a(paramLong2, paramLong3, paramLong4) + paramLong5 + paramLong6, paramInt)) + paramLong2;
  }

  private long b(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, int paramInt, long paramLong6)
  {
    return paramLong1 = (paramLong1 = a(paramLong1 += b(paramLong2, paramLong3, paramLong4) + paramLong5 + paramLong6, paramInt)) + paramLong2;
  }

  private long c(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, int paramInt, long paramLong6)
  {
    return paramLong1 = (paramLong1 = a(paramLong1 += c(paramLong2, paramLong3, paramLong4) + paramLong5 + paramLong6, paramInt)) + paramLong2;
  }

  private long d(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, int paramInt, long paramLong6)
  {
    return paramLong1 = (paramLong1 = a(paramLong1 += d(paramLong2, paramLong3, paramLong4) + paramLong5 + paramLong6, paramInt)) + paramLong2;
  }

  private static void a(byte[] paramArrayOfByte, int paramInt1, char[] paramArrayOfChar, int paramInt2)
  {
    for (int i = 0; i < paramInt2; i++)
      paramArrayOfByte[(i + paramInt1)] = ((byte)(paramArrayOfChar[i] & 0xFF));
  }

  private static void a(char[] paramArrayOfChar, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    for (int i = 0; i < paramInt2; i++)
      paramArrayOfChar[i] = ((char)paramArrayOfByte[(i + paramInt1)]);
  }

  public final void a()
  {
    this.a[0] = 0L;
    this.a[1] = 0L;
    this.b[0] = 1732584193L;
    this.b[1] = -271733879L;
    this.b[2] = -1732584194L;
    this.b[3] = 271733878L;
  }

  private static void a(char[] paramArrayOfChar, long[] paramArrayOfLong, int paramInt)
  {
    int i = 0;
    for (int j = 0; i < paramInt; j += 4)
    {
      paramArrayOfChar[j] = ((char)(int)(paramArrayOfLong[i] & 0xFF));
      paramArrayOfChar[(j + 1)] = ((char)(int)(paramArrayOfLong[i] >> 8 & 0xFF));
      paramArrayOfChar[(j + 2)] = ((char)(int)(paramArrayOfLong[i] >> 16 & 0xFF));
      paramArrayOfChar[(j + 3)] = ((char)(int)(paramArrayOfLong[i] >> 24 & 0xFF));
      i++;
    }
  }

  private static void a(long[] paramArrayOfLong, char[] paramArrayOfChar, int paramInt)
  {
    int i = 0;
    for (int j = 0; j < paramInt; j += 4)
    {
      paramArrayOfLong[i] = (paramArrayOfChar[j] & 0xFF | paramArrayOfChar[(j + 1)] << 8 & 0xFF00 | paramArrayOfChar[(j + 2)] << 16 & 0xFF0000 | paramArrayOfChar[(j + 3)] << 24 & 0xFF000000);
      i++;
    }
  }

  private void a(long[] paramArrayOfLong1, long[] paramArrayOfLong2)
  {
    long l1 = paramArrayOfLong1[0];
    long l2 = paramArrayOfLong1[1];
    long l3 = paramArrayOfLong1[2];
    long l4 = paramArrayOfLong1[3];
    l1 = a(l1, l2, l3, l4, paramArrayOfLong2[0], 7, -680876936L);
    l4 = a(l4, l1, l2, l3, paramArrayOfLong2[1], 12, -389564586L);
    l3 = a(l3, l4, l1, l2, paramArrayOfLong2[2], 17, 606105819L);
    l2 = a(l2, l3, l4, l1, paramArrayOfLong2[3], 22, -1044525330L);
    l1 = a(l1, l2, l3, l4, paramArrayOfLong2[4], 7, -176418897L);
    l4 = a(l4, l1, l2, l3, paramArrayOfLong2[5], 12, 1200080426L);
    l3 = a(l3, l4, l1, l2, paramArrayOfLong2[6], 17, -1473231341L);
    l2 = a(l2, l3, l4, l1, paramArrayOfLong2[7], 22, -45705983L);
    l1 = a(l1, l2, l3, l4, paramArrayOfLong2[8], 7, 1770035416L);
    l4 = a(l4, l1, l2, l3, paramArrayOfLong2[9], 12, -1958414417L);
    l3 = a(l3, l4, l1, l2, paramArrayOfLong2[10], 17, -42063L);
    l2 = a(l2, l3, l4, l1, paramArrayOfLong2[11], 22, -1990404162L);
    l1 = a(l1, l2, l3, l4, paramArrayOfLong2[12], 7, 1804603682L);
    l4 = a(l4, l1, l2, l3, paramArrayOfLong2[13], 12, -40341101L);
    l3 = a(l3, l4, l1, l2, paramArrayOfLong2[14], 17, -1502002290L);
    l2 = a(l2, l3, l4, l1, paramArrayOfLong2[15], 22, 1236535329L);
    l1 = b(l1, l2, l3, l4, paramArrayOfLong2[1], 5, -165796510L);
    l4 = b(l4, l1, l2, l3, paramArrayOfLong2[6], 9, -1069501632L);
    l3 = b(l3, l4, l1, l2, paramArrayOfLong2[11], 14, 643717713L);
    l2 = b(l2, l3, l4, l1, paramArrayOfLong2[0], 20, -373897302L);
    l1 = b(l1, l2, l3, l4, paramArrayOfLong2[5], 5, -701558691L);
    l4 = b(l4, l1, l2, l3, paramArrayOfLong2[10], 9, 38016083L);
    l3 = b(l3, l4, l1, l2, paramArrayOfLong2[15], 14, -660478335L);
    l2 = b(l2, l3, l4, l1, paramArrayOfLong2[4], 20, -405537848L);
    l1 = b(l1, l2, l3, l4, paramArrayOfLong2[9], 5, 568446438L);
    l4 = b(l4, l1, l2, l3, paramArrayOfLong2[14], 9, -1019803690L);
    l3 = b(l3, l4, l1, l2, paramArrayOfLong2[3], 14, -187363961L);
    l2 = b(l2, l3, l4, l1, paramArrayOfLong2[8], 20, 1163531501L);
    l1 = b(l1, l2, l3, l4, paramArrayOfLong2[13], 5, -1444681467L);
    l4 = b(l4, l1, l2, l3, paramArrayOfLong2[2], 9, -51403784L);
    l3 = b(l3, l4, l1, l2, paramArrayOfLong2[7], 14, 1735328473L);
    l2 = b(l2, l3, l4, l1, paramArrayOfLong2[12], 20, -1926607734L);
    l1 = c(l1, l2, l3, l4, paramArrayOfLong2[5], 4, -378558L);
    l4 = c(l4, l1, l2, l3, paramArrayOfLong2[8], 11, -2022574463L);
    l3 = c(l3, l4, l1, l2, paramArrayOfLong2[11], 16, 1839030562L);
    l2 = c(l2, l3, l4, l1, paramArrayOfLong2[14], 23, -35309556L);
    l1 = c(l1, l2, l3, l4, paramArrayOfLong2[1], 4, -1530992060L);
    l4 = c(l4, l1, l2, l3, paramArrayOfLong2[4], 11, 1272893353L);
    l3 = c(l3, l4, l1, l2, paramArrayOfLong2[7], 16, -155497632L);
    l2 = c(l2, l3, l4, l1, paramArrayOfLong2[10], 23, -1094730640L);
    l1 = c(l1, l2, l3, l4, paramArrayOfLong2[13], 4, 681279174L);
    l4 = c(l4, l1, l2, l3, paramArrayOfLong2[0], 11, -358537222L);
    l3 = c(l3, l4, l1, l2, paramArrayOfLong2[3], 16, -722521979L);
    l2 = c(l2, l3, l4, l1, paramArrayOfLong2[6], 23, 76029189L);
    l1 = c(l1, l2, l3, l4, paramArrayOfLong2[9], 4, -640364487L);
    l4 = c(l4, l1, l2, l3, paramArrayOfLong2[12], 11, -421815835L);
    l3 = c(l3, l4, l1, l2, paramArrayOfLong2[15], 16, 530742520L);
    l2 = c(l2, l3, l4, l1, paramArrayOfLong2[2], 23, -995338651L);
    l1 = d(l1, l2, l3, l4, paramArrayOfLong2[0], 6, -198630844L);
    l4 = d(l4, l1, l2, l3, paramArrayOfLong2[7], 10, 1126891415L);
    l3 = d(l3, l4, l1, l2, paramArrayOfLong2[14], 15, -1416354905L);
    l2 = d(l2, l3, l4, l1, paramArrayOfLong2[5], 21, -57434055L);
    l1 = d(l1, l2, l3, l4, paramArrayOfLong2[12], 6, 1700485571L);
    l4 = d(l4, l1, l2, l3, paramArrayOfLong2[3], 10, -1894986606L);
    l3 = d(l3, l4, l1, l2, paramArrayOfLong2[10], 15, -1051523L);
    l2 = d(l2, l3, l4, l1, paramArrayOfLong2[1], 21, -2054922799L);
    l1 = d(l1, l2, l3, l4, paramArrayOfLong2[8], 6, 1873313359L);
    l4 = d(l4, l1, l2, l3, paramArrayOfLong2[15], 10, -30611744L);
    l3 = d(l3, l4, l1, l2, paramArrayOfLong2[6], 15, -1560198380L);
    l2 = d(l2, l3, l4, l1, paramArrayOfLong2[13], 21, 1309151649L);
    l1 = d(l1, l2, l3, l4, paramArrayOfLong2[4], 6, -145523070L);
    l4 = d(l4, l1, l2, l3, paramArrayOfLong2[11], 10, -1120210379L);
    l3 = d(l3, l4, l1, l2, paramArrayOfLong2[2], 15, 718787259L);
    l2 = d(l2, l3, l4, l1, paramArrayOfLong2[9], 21, -343485551L);
    paramArrayOfLong1[0] += l1;
    paramArrayOfLong1[1] += l2;
    paramArrayOfLong1[2] += l3;
    paramArrayOfLong1[3] += l4;
  }

  j()
  {
    a();
  }

  public final int b()
  {
    return 16;
  }

  public final void a(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    char[] arrayOfChar;
    a(arrayOfChar = new char[paramInt2], paramArrayOfByte, paramInt1, paramInt2);
    long[] arrayOfLong = new long[16];
    int i = (int)(this.a[0] >>> 3 & 0x3F);
    if (this.a[0] + (paramInt2 << 3) < this.a[0])
      this.a[1] += 1L;
    this.a[0] += (paramInt2 << 3);
    this.a[1] += (paramInt2 >>> 29);
    int j = 0;
    while (paramInt2-- > 0)
    {
      this.c[(i++)] = arrayOfChar[(j++)];
      if (i == 64)
      {
        a(arrayOfLong, this.c, 64);
        a(this.b, arrayOfLong);
        i = 0;
      }
    }
  }

  public final int a(byte[] paramArrayOfByte, int paramInt)
  {
    if (paramArrayOfByte.length - paramInt < 16)
      return 0;
    long[] arrayOfLong;
    (arrayOfLong = new long[16])[14] = this.a[0];
    arrayOfLong[15] = this.a[1];
    int i;
    int j = ((i = (int)(this.a[0] >>> 3 & 0x3F)) < 56 ? 56 : 120) - i;
    a(e, 0, j);
    a(arrayOfLong, this.c, 56);
    a(this.b, arrayOfLong);
    a(this.d, this.b, 4);
    a(paramArrayOfByte, paramInt, this.d, this.d.length);
    return 16;
  }
}