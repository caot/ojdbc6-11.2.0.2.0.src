package oracle.net.aso;

public abstract class d
{
  private f b = null;
  protected c a;

  public final void a(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
  {
    this.a.a();
    this.b = new f(40, paramArrayOfByte1, paramArrayOfByte2);
  }

  public final boolean b(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
  {
    byte[] arrayOfByte1 = new byte[b()];
    byte[] arrayOfByte2 = this.b.a(arrayOfByte1);
    this.a.a();
    this.a.a(paramArrayOfByte1, 0, paramArrayOfByte1.length);
    this.a.a(arrayOfByte2, 0, arrayOfByte2.length);
    this.a.a(arrayOfByte1, 0);
    boolean bool = false;
    for (int i = 0; i < b(); i++)
      if (arrayOfByte1[i] != paramArrayOfByte2[i])
      {
        bool = true;
        break;
      }
    return bool;
  }

  public final byte[] a(byte[] paramArrayOfByte, int paramInt)
  {
    if (paramArrayOfByte.length < paramInt)
      return null;
    byte[] arrayOfByte1 = new byte[b()];
    byte[] arrayOfByte2 = this.b.b(arrayOfByte1);
    this.a.a();
    this.a.a(paramArrayOfByte, 0, paramInt);
    this.a.a(arrayOfByte2, 0, arrayOfByte2.length);
    this.a.a(arrayOfByte2, 0);
    return arrayOfByte2;
  }

  public final int c(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
  {
    try
    {
      this.b.b(paramArrayOfByte1, paramArrayOfByte2);
    }
    catch (s locals)
    {
    }
    return 0;
  }

  public final void a()
  {
    this.b.b();
  }

  public final int b()
  {
    return this.a.b();
  }
}