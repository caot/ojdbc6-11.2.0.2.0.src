package oracle.security.o3logon;

public final class a
{
  private int[] a = null;
  private int[] b = null;
  private static final short[] c = { 128, 64, 32, 16, 8, 4, 2, 1 };
  private static final int[] d = { 8388608, 4194304, 2097152, 1048576, 524288, 262144, 131072, 65536, 32768, 16384, 8192, 4096, 2048, 1024, 512, 256, 128, 64, 32, 16, 8, 4, 2, 1 };
  private static final byte[] e = { 56, 48, 40, 32, 24, 16, 8, 0, 57, 49, 41, 33, 25, 17, 9, 1, 58, 50, 42, 34, 26, 18, 10, 2, 59, 51, 43, 35, 62, 54, 46, 38, 30, 22, 14, 6, 61, 53, 45, 37, 29, 21, 13, 5, 60, 52, 44, 36, 28, 20, 12, 4, 27, 19, 11, 3 };
  private static final byte[] f = { 1, 2, 4, 6, 8, 10, 12, 14, 15, 17, 19, 21, 23, 25, 27, 28 };
  private static final byte[] g = { 13, 16, 10, 23, 0, 4, 2, 27, 14, 5, 20, 9, 22, 18, 11, 3, 25, 7, 15, 6, 26, 19, 12, 1, 40, 51, 30, 36, 46, 54, 29, 39, 50, 44, 32, 47, 43, 48, 38, 55, 33, 52, 45, 41, 49, 35, 28, 31 };
  private static final int[] h = { 16843776, 0, 65536, 16843780, 16842756, 66564, 4, 65536, 1024, 16843776, 16843780, 1024, 16778244, 16842756, 16777216, 4, 1028, 16778240, 16778240, 66560, 66560, 16842752, 16842752, 16778244, 65540, 16777220, 16777220, 65540, 0, 1028, 66564, 16777216, 65536, 16843780, 4, 16842752, 16843776, 16777216, 16777216, 1024, 16842756, 65536, 66560, 16777220, 1024, 4, 16778244, 66564, 16843780, 65540, 16842752, 16778244, 16777220, 1028, 66564, 16843776, 1028, 16778240, 16778240, 0, 65540, 66560, 0, 16842756 };
  private static final int[] i = { -2146402272, -2147450880, 32768, 1081376, 1048576, 32, -2146435040, -2147450848, -2147483616, -2146402272, -2146402304, -2147483648, -2147450880, 1048576, 32, -2146435040, 1081344, 1048608, -2147450848, 0, -2147483648, 32768, 1081376, -2146435072, 1048608, -2147483616, 0, 1081344, 32800, -2146402304, -2146435072, 32800, 0, 1081376, -2146435040, 1048576, -2147450848, -2146435072, -2146402304, 32768, -2146435072, -2147450880, 32, -2146402272, 1081376, 32, 32768, -2147483648, 32800, -2146402304, 1048576, -2147483616, 1048608, -2147450848, -2147483616, 1048608, 1081344, 0, -2147450880, 32800, -2147483648, -2146435040, -2146402272, 1081344 };
  private static final int[] j = { 520, 134349312, 0, 134348808, 134218240, 0, 131592, 134218240, 131080, 134217736, 134217736, 131072, 134349320, 131080, 134348800, 520, 134217728, 8, 134349312, 512, 131584, 134348800, 134348808, 131592, 134218248, 131584, 131072, 134218248, 8, 134349320, 512, 134217728, 134349312, 134217728, 131080, 520, 131072, 134349312, 134218240, 0, 512, 131080, 134349320, 134218240, 134217736, 512, 0, 134348808, 134218248, 131072, 134217728, 134349320, 8, 131592, 131584, 134217736, 134348800, 134218248, 520, 134348800, 131592, 8, 134348808, 131584 };
  private static final int[] k = { 8396801, 8321, 8321, 128, 8396928, 8388737, 8388609, 8193, 0, 8396800, 8396800, 8396929, 129, 0, 8388736, 8388609, 1, 8192, 8388608, 8396801, 128, 8388608, 8193, 8320, 8388737, 1, 8320, 8388736, 8192, 8396928, 8396929, 129, 8388736, 8388609, 8396800, 8396929, 129, 0, 0, 8396800, 8320, 8388736, 8388737, 1, 8396801, 8321, 8321, 128, 8396929, 129, 1, 8192, 8388609, 8193, 8396928, 8388737, 8193, 8320, 8388608, 8396801, 128, 8388608, 8192, 8396928 };
  private static final int[] l = { 256, 34078976, 34078720, 1107296512, 524288, 256, 1073741824, 34078720, 1074266368, 524288, 33554688, 1074266368, 1107296512, 1107820544, 524544, 1073741824, 33554432, 1074266112, 1074266112, 0, 1073742080, 1107820800, 1107820800, 33554688, 1107820544, 1073742080, 0, 1107296256, 34078976, 33554432, 1107296256, 524544, 524288, 1107296512, 256, 33554432, 1073741824, 34078720, 1107296512, 1074266368, 33554688, 1073741824, 1107820544, 34078976, 1074266368, 256, 33554432, 1107820544, 1107820800, 524544, 1107296256, 1107820800, 34078720, 0, 1074266112, 1107296256, 524544, 33554688, 1073742080, 524288, 0, 1074266112, 34078976, 1073742080 };
  private static final int[] m = { 536870928, 541065216, 16384, 541081616, 541065216, 16, 541081616, 4194304, 536887296, 4210704, 4194304, 536870928, 4194320, 536887296, 536870912, 16400, 0, 4194320, 536887312, 16384, 4210688, 536887312, 16, 541065232, 541065232, 0, 4210704, 541081600, 16400, 4210688, 541081600, 536870912, 536887296, 16, 541065232, 4210688, 541081616, 4194304, 16400, 536870928, 4194304, 536887296, 536870912, 16400, 536870928, 541081616, 4210688, 541065216, 4210704, 541081600, 0, 541065232, 16, 16384, 541065216, 4210704, 16384, 4194320, 536887312, 0, 541081600, 536870912, 4194320, 536887312 };
  private static final int[] n = { 2097152, 69206018, 67110914, 0, 2048, 67110914, 2099202, 69208064, 69208066, 2097152, 0, 67108866, 2, 67108864, 69206018, 2050, 67110912, 2099202, 2097154, 67110912, 67108866, 69206016, 69208064, 2097154, 69206016, 2048, 2050, 69208066, 2099200, 2, 67108864, 2099200, 67108864, 2099200, 2097152, 67110914, 67110914, 69206018, 69206018, 2, 2097154, 67108864, 67110912, 2097152, 69208064, 2050, 2099202, 69208064, 2050, 67108866, 69208066, 69206016, 2099200, 0, 2, 69208066, 0, 2099202, 69206016, 2048, 67108866, 67110912, 2048, 2097154 };
  private static final int[] o = { 268439616, 4096, 262144, 268701760, 268435456, 268439616, 64, 268435456, 262208, 268697600, 268701760, 266240, 268701696, 266304, 4096, 64, 268697600, 268435520, 268439552, 4160, 266240, 262208, 268697664, 268701696, 4160, 0, 0, 268697664, 268435520, 268439552, 266304, 262144, 266304, 262144, 268701696, 4096, 64, 268697664, 4096, 266304, 268439552, 64, 268435520, 268697600, 268697664, 268435456, 262144, 268439616, 0, 268701760, 262208, 268435520, 268697600, 268439552, 268439616, 0, 268701760, 266240, 266240, 4160, 4160, 262208, 268435456, 268701696 };

  public final byte[] a(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
  {
    a(paramArrayOfByte1);
    byte[] arrayOfByte1 = new byte[8];
    int i1 = 8 - paramArrayOfByte2.length % 8;
    byte[] arrayOfByte2 = new byte[paramArrayOfByte2.length + i1];
    System.arraycopy(paramArrayOfByte2, 0, arrayOfByte2, 0, paramArrayOfByte2.length);
    for (int i2 = paramArrayOfByte2.length; i2 < arrayOfByte2.length; i2++)
      arrayOfByte2[i2] = ((byte)i1);
    for (int i2 = 0; i2 < arrayOfByte2.length / 8; i2++)
    {
      for (int i3 = 0; i3 < 8; i3++)
        arrayOfByte1[i3] = arrayOfByte2[(i2 * 8 + i3)];
      a(arrayOfByte1, 1);
      System.arraycopy(arrayOfByte1, 0, arrayOfByte2, i2 * 8, 8);
    }
    return arrayOfByte2;
  }

  public final byte[] b(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
  {
    a(paramArrayOfByte1);
    byte[] arrayOfByte1 = new byte[8];
    byte[] arrayOfByte2 = new byte[paramArrayOfByte2.length];
    for (int i1 = 0; i1 < paramArrayOfByte2.length / 8; i1++)
    {
      for (int i2 = 0; i2 < 8; i2++)
        arrayOfByte1[i2] = paramArrayOfByte2[(i1 * 8 + i2)];
      b(arrayOfByte1, 1);
      System.arraycopy(arrayOfByte1, 0, arrayOfByte2, i1 * 8, 8);
    }
    byte[] arrayOfByte3 = new byte[arrayOfByte2.length - arrayOfByte2[(arrayOfByte2.length - 1)]];
    System.arraycopy(arrayOfByte2, 0, arrayOfByte3, 0, arrayOfByte3.length);
    return arrayOfByte3;
  }

  public final byte[] a(byte[] paramArrayOfByte, boolean paramBoolean)
  {
    byte[] arrayOfByte1 = new byte[8];
    byte[] arrayOfByte2 = new byte[paramArrayOfByte.length];
    for (int i1 = 0; i1 < paramArrayOfByte.length / 8; i1++)
    {
      for (int i2 = 0; i2 < 8; i2++)
      {
        int tmp37_35 = i2;
        byte[] tmp37_34 = arrayOfByte1;
        tmp37_34[tmp37_35] = ((byte)(tmp37_34[tmp37_35] ^ paramArrayOfByte[(i1 * 8 + i2)]));
      }
      a(arrayOfByte1, 1);
      if (!paramBoolean)
        System.arraycopy(arrayOfByte1, 0, arrayOfByte2, i1 * 8, 8);
    }
    if (paramBoolean)
      return arrayOfByte1;
    return arrayOfByte2;
  }

  private void a(byte[] paramArrayOfByte, int paramInt)
  {
    if ((paramArrayOfByte == null) || (paramArrayOfByte.length == 0))
      return;
    int[] arrayOfInt = new int[2];
    byte[] arrayOfByte = paramArrayOfByte;
    for (int i1 = 0; i1 < paramInt; i1++)
    {
      a(arrayOfByte, arrayOfInt);
      a(arrayOfInt, this.a);
      a(arrayOfInt, arrayOfByte);
    }
  }

  private void b(byte[] paramArrayOfByte, int paramInt)
  {
    if ((paramArrayOfByte == null) || (paramArrayOfByte.length == 0))
      return;
    int[] arrayOfInt = new int[2];
    byte[] arrayOfByte = paramArrayOfByte;
    for (int i1 = 0; i1 < paramInt; i1++)
    {
      a(arrayOfByte, arrayOfInt);
      a(arrayOfInt, this.b);
      a(arrayOfInt, arrayOfByte);
    }
  }

  public final void a(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt)
  {
    if ((paramArrayOfByte2 == null) || (paramArrayOfByte2.length == 0))
      return;
    int[] arrayOfInt = new int[2];
    byte[] arrayOfByte = paramArrayOfByte2;
    for (int i1 = 0; i1 < paramInt; i1++)
    {
      a(arrayOfByte, arrayOfInt);
      a(arrayOfInt, a(paramArrayOfByte1, (short)1));
      a(arrayOfInt, arrayOfByte);
    }
  }

  public final void a(byte[] paramArrayOfByte)
  {
    this.a = a(paramArrayOfByte, (short)0);
    this.b = a(paramArrayOfByte, (short)1);
  }

  private int[] a(byte[] paramArrayOfByte, short paramShort)
  {
    byte[] arrayOfByte1 = new byte[56];
    byte[] arrayOfByte2 = new byte[56];
    int[] arrayOfInt = new int[32];
    int i3 = 0;
    int i4;
    for (int i2 = 0; i2 < 56; i2++)
    {
      i4 = (i3 = e[i2]) & 0x7;
      arrayOfByte1[i2] = ((byte)((paramArrayOfByte[(i3 >> 3)] & c[i4]) != 0 ? 1 : 0));
    }
    int i1 = 0;
    int i5 = (i4 = (paramShort == 1 ? 15 - i1 : i1) << 1) + 1;
    int tmp114_113 = 0;
    arrayOfInt[i5] = tmp114_113;
    arrayOfInt[i4] = tmp114_113;
    int i2 = 0;
    arrayOfByte2[i2] = arrayOfByte1[(i3 - 28)];
    i2 = 28;
    arrayOfByte2[i2] = arrayOfByte1[(i3 - 28)];
    i2 = 0;
    if (arrayOfByte2[g[i2]] != 0)
      arrayOfInt[i4] |= d[i2];
    if (arrayOfByte2[g[(i2 + 24)]] != 0)
      arrayOfInt[i5] |= d[i2];
    i2++;
    i1++;
    return a(arrayOfInt);
  }

  private static int[] a(int[] paramArrayOfInt)
  {
    int[] arrayOfInt3;
    int[] arrayOfInt1 = arrayOfInt3 = new int[32];
    int[] arrayOfInt2 = paramArrayOfInt;
    int i1 = 0;
    int i2 = 0;
    int i3 = 0;
    int i4 = 0;
    while (i1 < 16)
    {
      i2 = i3++;
      arrayOfInt1[i4] = ((arrayOfInt2[i2] & 0xFC0000) << 6);
      arrayOfInt1[i4] |= (arrayOfInt2[i2] & 0xFC0) << 10;
      arrayOfInt1[i4] |= (paramArrayOfInt[i3] & 0xFC0000) >> 10;
      arrayOfInt1[i4] |= (paramArrayOfInt[i3] & 0xFC0) >> 6;
      i4++;
      arrayOfInt1[i4] = ((arrayOfInt2[i2] & 0x3F000) << 12);
      arrayOfInt1[i4] |= (arrayOfInt2[i2] & 0x3F) << 16;
      arrayOfInt1[i4] |= (paramArrayOfInt[i3] & 0x3F000) >> 4;
      arrayOfInt1[i4] |= paramArrayOfInt[i3] & 0x3F;
      i4++;
      i1++;
      i3++;
    }
    return arrayOfInt1;
  }

  private static void a(byte[] paramArrayOfByte, int[] paramArrayOfInt)
  {
    paramArrayOfInt[0] = ((paramArrayOfByte[0] & 0xFF) << 24);
    paramArrayOfInt[0] |= (paramArrayOfByte[1] & 0xFF) << 16;
    paramArrayOfInt[0] |= (paramArrayOfByte[2] & 0xFF) << 8;
    paramArrayOfInt[0] |= paramArrayOfByte[3] & 0xFF;
    paramArrayOfInt[1] = ((paramArrayOfByte[4] & 0xFF) << 24);
    paramArrayOfInt[1] |= (paramArrayOfByte[5] & 0xFF) << 16;
    paramArrayOfInt[1] |= (paramArrayOfByte[6] & 0xFF) << 8;
    paramArrayOfInt[1] |= paramArrayOfByte[7] & 0xFF;
  }

  private static void a(int[] paramArrayOfInt, byte[] paramArrayOfByte)
  {
    paramArrayOfByte[0] = ((byte)(paramArrayOfInt[0] >> 24 & 0xFF));
    paramArrayOfByte[1] = ((byte)(paramArrayOfInt[0] >> 16 & 0xFF));
    paramArrayOfByte[2] = ((byte)(paramArrayOfInt[0] >> 8 & 0xFF));
    paramArrayOfByte[3] = ((byte)(paramArrayOfInt[0] & 0xFF));
    paramArrayOfByte[4] = ((byte)(paramArrayOfInt[1] >> 24 & 0xFF));
    paramArrayOfByte[5] = ((byte)(paramArrayOfInt[1] >> 16 & 0xFF));
    paramArrayOfByte[6] = ((byte)(paramArrayOfInt[1] >> 8 & 0xFF));
    paramArrayOfByte[7] = ((byte)(paramArrayOfInt[1] & 0xFF));
  }

  private static void a(int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    int i6 = 0;
    int i4 = paramArrayOfInt1[0];
    int i3 = paramArrayOfInt1[1];
    int i2 = (i4 >>> 4 ^ i3) & 0xF0F0F0F;
    i3 ^= i2;
    i2 = ((i4 ^= i2 << 4) >>> 16 ^ i3) & 0xFFFF;
    i3 ^= i2;
    i4 ^= i2 << 16;
    i2 = (i3 >>> 2 ^ i4) & 0x33333333;
    i4 ^= i2;
    i2 = ((i3 ^= i2 << 2) >>> 8 ^ i4) & 0xFF00FF;
    i4 ^= i2;
    i3 = ((i3 ^= i2 << 8) << 1 | i3 >>> 31 & 0x1) & 0xFFFFFFFF;
    i2 = (i4 ^ i3) & 0xAAAAAAAA;
    i4 ^= i2;
    i3 ^= i2;
    i4 = (i4 << 1 | i4 >>> 31 & 0x1) & 0xFFFFFFFF;
    for (int i5 = 0; i5 < 8; i5++)
    {
      i2 = (i2 = i3 << 28 | i3 >>> 4) ^ paramArrayOfInt2[i6];
      i6++;
      int i1 = (i1 = (i1 = (i1 = n[(i2 & 0x3F)]) | l[(i2 >>> 8 & 0x3F)]) | j[(i2 >>> 16 & 0x3F)]) | h[(i2 >>> 24 & 0x3F)];
      i2 = i3 ^ paramArrayOfInt2[i6];
      i6++;
      i1 = (i1 = (i1 = i1 |= o[(i2 & 0x3F)] | m[(i2 >>> 8 & 0x3F)]) | k[(i2 >>> 16 & 0x3F)]) | i[(i2 >>> 24 & 0x3F)];
      i2 = (i2 = (i4 ^= i1) << 28 | i4 >>> 4) ^ paramArrayOfInt2[i6];
      i6++;
      i1 = (i1 = (i1 = (i1 = n[(i2 & 0x3F)]) | l[(i2 >>> 8 & 0x3F)]) | j[(i2 >>> 16 & 0x3F)]) | h[(i2 >>> 24 & 0x3F)];
      i2 = i4 ^ paramArrayOfInt2[i6];
      i6++;
      i1 = (i1 = (i1 = i1 |= o[(i2 & 0x3F)] | m[(i2 >>> 8 & 0x3F)]) | k[(i2 >>> 16 & 0x3F)]) | i[(i2 >>> 24 & 0x3F)];
      i3 ^= i1;
    }
    i3 = i3 << 31 | i3 >>> 1;
    i2 = (i4 ^ i3) & 0xAAAAAAAA;
    i4 ^= i2;
    i3 ^= i2;
    i2 = ((i4 = i4 << 31 | i4 >>> 1) >>> 8 ^ i3) & 0xFF00FF;
    i3 ^= i2;
    i2 = ((i4 ^= i2 << 8) >>> 2 ^ i3) & 0x33333333;
    i3 ^= i2;
    i4 ^= i2 << 2;
    i2 = (i3 >>> 16 ^ i4) & 0xFFFF;
    i4 ^= i2;
    i2 = ((i3 ^= i2 << 16) >>> 4 ^ i4) & 0xF0F0F0F;
    i4 ^= i2;
    i3 ^= i2 << 4;
    paramArrayOfInt1[0] = i3;
    paramArrayOfInt1[1] = i4;
  }
}