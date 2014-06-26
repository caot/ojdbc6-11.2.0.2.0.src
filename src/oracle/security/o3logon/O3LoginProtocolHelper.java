package oracle.security.o3logon;

import java.security.SecureRandom;

public final class O3LoginProtocolHelper
{
  private final byte[] a;
  private final byte[] b = new byte[8];
  private static long c = System.currentTimeMillis();
  private static int d = 0;
  private static b e;

  public O3LoginProtocolHelper()
  {
    this.a = null;
  }

  public O3LoginProtocolHelper(byte[] paramArrayOfByte)
  {
    this.a = paramArrayOfByte;
  }

  public final byte[] getVerifier(String paramString1, String paramString2)
  {
    return getVerifier(paramString1, paramString2, Boolean.valueOf(true));
  }

  public final byte[] getVerifier(String paramString1, String paramString2, Boolean paramBoolean)
  {
    if (e == null)
      e = new b();
    return e.a(paramString1, paramString2, paramBoolean.booleanValue());
  }

  public final boolean authenticate(String paramString1, String paramString2)
  {
    try
    {
      Thread.sleep(d * 1000);
    }
    catch (InterruptedException localInterruptedException)
    {
    }
    if (e == null)
      e = new b();
    byte[] arrayOfByte = e.a(paramString1, paramString2);
    if (this.a.length != arrayOfByte.length)
    {
      d += 1;
      return false;
    }
    for (int i = 0; i < arrayOfByte.length; i++)
      if (arrayOfByte[i] != this.a[i])
      {
        d += 1;
        return false;
      }
    return true;
  }

  public final byte[] getChallenge(byte[] paramArrayOfByte)
  {
    SecureRandom localSecureRandom = null;
    localSecureRandom = new SecureRandom(paramArrayOfByte);
    c += System.currentTimeMillis();
    localSecureRandom.setSeed(c);
    localSecureRandom.setSeed(this.a);
    localSecureRandom.nextBytes(this.b);
    a locala;
    byte[] arrayOfByte;
    return arrayOfByte = (locala = new a()).a(this.a, this.b);
  }

  public final String getPassword(byte[] paramArrayOfByte)
  {
    a locala = new a();
    int i = paramArrayOfByte[(paramArrayOfByte.length - 1)];
    byte[] arrayOfByte1 = new byte[paramArrayOfByte.length - 1];
    System.arraycopy(paramArrayOfByte, 0, arrayOfByte1, 0, arrayOfByte1.length);
    byte[] arrayOfByte2 = null;
    try
    {
      arrayOfByte2 = locala.b(this.b, arrayOfByte1);
    }
    catch (Exception localException)
    {
      return null;
    }
    byte[] arrayOfByte3 = new byte[arrayOfByte2.length - i];
    System.arraycopy(arrayOfByte2, 0, arrayOfByte3, 0, arrayOfByte3.length);
    String str;
    return str = new String(arrayOfByte3).toUpperCase();
  }

  public static byte[] getResponse(String paramString1, String paramString2, byte[] paramArrayOfByte)
  {
    if (e == null)
      e = new b();
    byte[] arrayOfByte1 = e.a(paramString1, paramString2);
    a locala;
    byte[] arrayOfByte3 = (locala = new a()).b(arrayOfByte1, paramArrayOfByte);
    byte[] arrayOfByte4;
    byte i = (arrayOfByte4 = paramString2.getBytes()).length % 8 > 0 ? (byte)(8 - arrayOfByte4.length % 8) : 0;
    byte[] arrayOfByte2 = new byte[arrayOfByte4.length + i];
    System.arraycopy(arrayOfByte4, 0, arrayOfByte2, 0, arrayOfByte4.length);
    byte[] arrayOfByte5;
    byte[] arrayOfByte6 = new byte[(arrayOfByte5 = locala.a(arrayOfByte3, arrayOfByte2)).length + 1];
    System.arraycopy(arrayOfByte5, 0, arrayOfByte6, 0, arrayOfByte5.length);
    arrayOfByte6[(arrayOfByte6.length - 1)] = i;
    return arrayOfByte6;
  }
}