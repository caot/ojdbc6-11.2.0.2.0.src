package oracle.net.aso;

public final class k extends q
{
  public final void a(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
    throws s
  {
    if ((paramArrayOfByte1 == null) && (paramArrayOfByte2 == null))
      throw new s(102);
    if (paramArrayOfByte1.length < 16)
      throw new s(102);
    System.arraycopy(paramArrayOfByte1, 0, this.a, 0, 8);
    System.arraycopy(paramArrayOfByte1, 8, this.b, 0, 8);
    System.arraycopy(this.a, 0, this.c, 0, 8);
    this.d = true;
  }

  public final void b(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
    throws s
  {
    this.d = true;
    if ((paramArrayOfByte1 == null) && (paramArrayOfByte2 == null))
    {
      if (this.a == null)
        throw new s(102);
      return;
    }
    if (paramArrayOfByte1.length < 16)
      throw new s(102);
    System.arraycopy(paramArrayOfByte1, 0, this.a, 0, 8);
    System.arraycopy(paramArrayOfByte1, 8, this.b, 0, 8);
    System.arraycopy(this.a, 0, this.c, 0, 8);
  }
}