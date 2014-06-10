package oracle.security.o3logon;

public final class O3LoginClientHelper
{
  private b a;
  private boolean b;

  public O3LoginClientHelper()
  {
    this.b = true;
    this.a = new b();
  }

  public O3LoginClientHelper(boolean paramBoolean)
  {
    this.b = paramBoolean;
    this.a = new b();
  }

  public final byte[] getSessionKey(String paramString1, String paramString2, byte[] paramArrayOfByte)
  {
    byte[] arrayOfByte;
    return b.a(arrayOfByte = this.a.a(paramString1, paramString2, this.b), paramArrayOfByte);
  }

  public final byte[] getEPasswd(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
  {
    return b.b(paramArrayOfByte1, paramArrayOfByte2);
  }
}