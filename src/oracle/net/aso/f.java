package oracle.net.aso;

public final class f
  implements g
{
  private m a;
  private m b;
  private m c;
  private boolean d = true;
  private boolean e = false;
  private int f = 40;

  public f()
  {
  }

  public f(boolean paramBoolean, int paramInt)
    throws s
  {
    switch (paramInt)
    {
    case 40:
    case 56:
    case 128:
    case 256:
      this.f = paramInt;
      break;
    default:
      throw new s(100);
    }
    this.d = paramBoolean;
  }

  protected f(int paramInt, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
  {
    this.f = paramInt;
    this.e = true;
    this.d = false;
    try
    {
      a(paramArrayOfByte1, paramArrayOfByte2);
      return;
    }
    catch (s locals)
    {
    }
  }

  public final void a(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
    throws s
  {
    this.a = new m(this);
    this.b = new m(this);
    this.c = new m(this);
    b(paramArrayOfByte1, paramArrayOfByte2);
  }

  public final byte[] a(byte[] paramArrayOfByte)
  {
    byte[] arrayOfByte1;
    if (this.d)
    {
      arrayOfByte1 = this.c.b(paramArrayOfByte, paramArrayOfByte.length - 1);
      byte[] arrayOfByte2 = new byte[paramArrayOfByte.length - 1];
      System.arraycopy(arrayOfByte1, 0, arrayOfByte2, 0, paramArrayOfByte.length - 1);
      return arrayOfByte2;
    }
    return arrayOfByte1 = this.c.b(paramArrayOfByte, paramArrayOfByte.length);
  }

  public final byte[] b(byte[] paramArrayOfByte)
  {
    byte[] arrayOfByte1 = this.b.b(paramArrayOfByte, paramArrayOfByte.length);
    if (this.d)
    {
      byte[] arrayOfByte2 = new byte[paramArrayOfByte.length + 1];
      System.arraycopy(arrayOfByte1, 0, arrayOfByte2, 0, paramArrayOfByte.length);
      arrayOfByte2[paramArrayOfByte.length] = 0;
      return arrayOfByte2;
    }
    return arrayOfByte1;
  }

  public final int a()
  {
    return 1;
  }

  public final void b(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
    throws s
  {
    if ((paramArrayOfByte1 == null) && (paramArrayOfByte2 == null))
    {
      b();
      return;
    }
    int i = this.f / 8;
    if (paramArrayOfByte1.length < i)
      throw new s(102);
    int j = 0;
    if (paramArrayOfByte2 != null)
      j = paramArrayOfByte2.length;
    byte[] arrayOfByte = new byte[i + 1 + j];
    System.arraycopy(paramArrayOfByte1, paramArrayOfByte1.length - i, arrayOfByte, 0, i);
    arrayOfByte[i] = (!this.e ? 123 : -1);
    if (paramArrayOfByte2 != null)
      System.arraycopy(paramArrayOfByte2, 0, arrayOfByte, i + 1, paramArrayOfByte2.length);
    this.a.a(arrayOfByte, arrayOfByte.length);
    b();
  }

  protected final void b()
  {
    byte[] arrayOfByte1 = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32 };
    int i = this.f / 8;
    byte[] arrayOfByte2 = this.a.b(arrayOfByte1, i);
    if (this.d)
    {
      int tmp236_235 = (i - 1);
      byte[] tmp236_232 = arrayOfByte2;
      tmp236_232[tmp236_235] = ((byte)(tmp236_232[tmp236_235] ^ 0xAA));
    }
    this.c.a(arrayOfByte2, i);
    int tmp257_256 = (i - 1);
    byte[] tmp257_253 = arrayOfByte2;
    tmp257_253[tmp257_256] = ((byte)(tmp257_253[tmp257_256] ^ 0xAA));
    arrayOfByte2 = new byte[i + 1];
    this.a.a(arrayOfByte2, arrayOfByte1, i);
    arrayOfByte2[i] = -76;
    this.c.a(arrayOfByte2, i + 1);
    arrayOfByte2[i] = 90;
    this.b.a(arrayOfByte2, !this.e ? i : i + 1);
  }
}