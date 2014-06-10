package oracle.net.aso;

public final class p extends e
{
  private static final byte[] d = { -2, -2, -2, -2, -2, -2, -2, -2 };
  private static final byte[] e = { 88, -46, 26, -119, 7, 0, -59, -68 };
  private static final byte[] f = { 103, 98, -82, -38, 116, -21, -92, -87 };
  private static final byte[] g = { 14, -2, 14, -2, 14, -2, 14, -2 };

  public final void a(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
    throws s
  {
    super.a(paramArrayOfByte1, paramArrayOfByte2);
  }

  public final byte[] a(byte[] paramArrayOfByte, int paramInt)
  {
    if (this.b)
      a(this.a, (byte)0);
    return super.a(paramArrayOfByte, paramInt);
  }

  public final byte[] a(byte[] paramArrayOfByte)
  {
    if (this.b)
      a(this.a, (byte)1);
    return super.a(paramArrayOfByte);
  }

  public final int a()
  {
    return 8;
  }

  public final void b(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
    throws s
  {
    super.b(paramArrayOfByte1, paramArrayOfByte2);
  }

  public final void a(byte[] paramArrayOfByte, byte paramByte)
  {
    System.arraycopy(paramArrayOfByte, 0, this.a, 0, 8);
    c(this.a);
    super.a(this.a, paramByte);
  }

  private void c(byte[] paramArrayOfByte)
  {
    byte[] arrayOfByte1 = new byte[8];
    byte[] arrayOfByte2 = new byte[8];
    byte[] arrayOfByte3 = new byte[8];
    int[] arrayOfInt = null;
    a(arrayOfByte1, paramArrayOfByte, d, 1);
    arrayOfInt = b(f, (byte)0);
    a(arrayOfInt, arrayOfByte1, arrayOfByte3);
    arrayOfInt = b(e, (byte)0);
    a(arrayOfInt, arrayOfByte1, arrayOfByte2);
    a(arrayOfByte1, arrayOfByte3, arrayOfByte2, 2);
    a(paramArrayOfByte, arrayOfByte1, g, 1);
  }

  private void a(int[] paramArrayOfInt, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
  {
    int[] arrayOfInt = new int[2];
    a(paramArrayOfByte1, arrayOfInt);
    a(arrayOfInt, paramArrayOfInt);
    a(arrayOfInt, paramArrayOfByte2);
  }
}