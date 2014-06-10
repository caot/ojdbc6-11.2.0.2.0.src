package oracle.net.aso;

final class m
{
  private byte[] a = new byte[256];
  private int b;
  private int c;

  protected m(f paramf)
  {
  }

  public final void a(byte[] paramArrayOfByte, int paramInt)
  {
    for (int i = 0; i < 256; i++)
      this.a[i] = ((byte)i);
    this.b = (this.c = 0);
    i = 0;
    int j = 0;
    for (int k = 0; i < 256; k++)
    {
      int m = this.a[i];
      if (k == paramInt)
        k = 0;
      j = j + m + paramArrayOfByte[k] & 0xFF;
      this.a[i] = this.a[j];
      this.a[j] = m;
      i++;
    }
  }

  public final byte[] b(byte[] paramArrayOfByte, int paramInt)
  {
    byte[] arrayOfByte = new byte[paramInt];
    a(arrayOfByte, paramArrayOfByte, paramInt);
    return arrayOfByte;
  }

  public final void a(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt)
  {
    int i = this.b;
    int j = this.c;
    for (int k = 0; k < paramInt; k++)
    {
      i = i + 1 & 0xFF;
      int m = this.a[i];
      j = j + m & 0xFF;
      int n = this.a[j];
      this.a[i] = ((byte)(n & 0xFF));
      this.a[j] = ((byte)(m & 0xFF));
      int i1 = m + n & 0xFF;
      paramArrayOfByte1[k] = ((byte)((paramArrayOfByte2[k] ^ this.a[i1]) & 0xFF));
    }
    this.b = i;
    this.c = j;
  }
}