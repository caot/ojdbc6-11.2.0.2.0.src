package oracle.net.aso;

import java.util.Random;

public final class h
{
  int a = 971;
  int b = 11113;
  int c = 104322;
  int d = 4181;
  boolean e = false;

  private void a()
  {
    Random localRandom = new Random();
    this.d = localRandom.nextInt();
    this.e = true;
  }

  private byte b()
  {
    return (byte)c();
  }

  private short c()
  {
    if (!this.e)
    {
      a();
    }
    else
    {
      this.d += 7;
      this.b += 1907;
      this.c += 73939;
      if (this.d >= 9973)
        this.d -= 9871;
      if (this.b >= 99991)
        this.b -= 89989;
      if (this.c >= 224729)
        this.c -= 96233;
      this.d = (this.d * this.a + this.b + this.c);
    }
    return (short)(this.d >> 16 ^ this.d & 0xFFFF);
  }

  public final void a(byte[] paramArrayOfByte, int paramInt)
  {
    for (int i = 0; i < paramInt; i++)
      paramArrayOfByte[i] = b();
  }
}