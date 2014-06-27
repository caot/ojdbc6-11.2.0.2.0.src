package oracle.security.o5logon;

import java.security.Key;
import java.security.MessageDigest;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import oracle.security.o3logon.O3LoginProtocolHelper;

public class O5LoginClientHelper
{
  public static byte[] concatKeys(int paramInt1, byte[] paramArrayOfByte1, int paramInt2, byte[] paramArrayOfByte2, int paramInt3)
    throws Exception
  {
    byte[] arrayOfByte1 = null;
    byte[] arrayOfByte2 = null;
    MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
    int i;
    switch (paramInt1)
    {
    case 2361:
      arrayOfByte2 = new byte[16];
      for (i = 0; i < 16; i++)
        arrayOfByte2[i] = ((byte)(paramArrayOfByte1[(i + paramInt2)] ^ paramArrayOfByte2[(i + paramInt3)]));
      localMessageDigest.reset();
      arrayOfByte1 = localMessageDigest.digest(arrayOfByte2);
      break;
    case 6949:
      arrayOfByte2 = new byte[24];
      for (i = 0; i < 24; i++)
        arrayOfByte2[i] = ((byte)(paramArrayOfByte1[(i + paramInt2)] ^ paramArrayOfByte2[(i + paramInt3)]));
      arrayOfByte1 = new byte[24];
      localMessageDigest.reset();
      localMessageDigest.update(arrayOfByte2, 0, 16);
      byte[] arrayOfByte3 = localMessageDigest.digest();
      System.arraycopy(arrayOfByte3, 0, arrayOfByte1, 0, 16);
      localMessageDigest.reset();
      localMessageDigest.update(arrayOfByte2, 16, 8);
      System.arraycopy(arrayOfByte3, 0, arrayOfByte1, 16, 8);
      break;
    default:
      arrayOfByte1 = new byte[0];
      break;
    }

    return arrayOfByte1;
  }


  public static byte[] decryptAES(String paramString1, byte[] paramArrayOfByte, String paramString2)
      throws Exception
  {
    if (paramArrayOfByte == null)
      return new byte[0];
    byte[] arrayOfByte1 = new byte[16];
    for (int i = 0; i < arrayOfByte1.length; i++)
      arrayOfByte1[0] = 0;

    byte[] arrayOfByte2 = new byte[0];
    if(paramArrayOfByte.length == 16)
    {
    SecretKeySpec localObject = new SecretKeySpec(paramArrayOfByte, "AES");
    IvParameterSpec localIvParameterSpec = new IvParameterSpec(arrayOfByte1);
    Cipher localCipher;
    (localCipher = Cipher.getInstance(paramString1)).init(2, (Key)localObject, localIvParameterSpec);
    byte[] arrayOfByte3 = toBinArray(paramString2);
    arrayOfByte2 = localCipher.doFinal(arrayOfByte3);
    } else
    if ((paramArrayOfByte.length == 24) && (paramString1.endsWith("PKCS5Padding")))
    {
      b bobj;
      (bobj = new b(1, 2, 2)).a(paramArrayOfByte);
      arrayOfByte2 = bobj.b(toBinArray(paramString2));
    }
    return arrayOfByte2;
  }

  public static byte[] encryptAES(String paramString, byte[] abyte0, byte[] abyte1)
    throws Exception
  {
    if (abyte0 == null)
      return new byte[0];
    byte[] abyte2 = new byte[16];
    for (int i = 0; i < abyte2.length; i++)
      abyte2[0] = 0;

    byte[] abyte3 = new byte[0];
    if(abyte0.length == 16)
    {
    SecretKeySpec secretkeyspec  = new SecretKeySpec(abyte0, "AES");
    IvParameterSpec ivparameterspec = new IvParameterSpec(abyte2);
    Cipher cipher;
    (cipher = Cipher.getInstance(paramString)).init(1, secretkeyspec, ivparameterspec);
    abyte3 = cipher.doFinal(abyte1);
    } else
    if ((abyte0.length == 24) && (paramString.endsWith("PKCS5Padding")))
    {
      b bobj;
      ( bobj = new b(1, 2, 2)).a(abyte0);
      abyte3 = bobj.c(abyte1);
    }
    return abyte3;
  }

  public static byte nibbleToHex(byte byte0)
  {
    byte0 &= 0xf;
    if(byte0 >= 10) {
      return (byte)(byte0 - 10 + 65);
    } else {
      return (byte)(byte0 + 48);
    }
  }

  public static void bArray2Nibbles(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
  {
    for (int i = 0; i < paramArrayOfByte1.length; i++)
    {
      paramArrayOfByte2[(i * 2)] = nibbleToHex((byte)((paramArrayOfByte1[i] & 0xF0) >> 4));
      paramArrayOfByte2[(i * 2 + 1)] = nibbleToHex((byte)(paramArrayOfByte1[i] & 0xF));
    }
  }

  public static byte[] toBinArray(String paramString)
  {
    byte[] arrayOfByte = new byte[paramString.length() / 2];
    for (int i = 0; i < paramString.length() / 2; i++)
    {
      int j = Byte.parseByte(paramString.substring(2 * i, 2 * i + 1), 16);
      int k;
      int m = (k = Byte.parseByte(paramString.substring(2 * i + 1, 2 * i + 2), 16)) | j << 4;
      arrayOfByte[i] = ((byte)m);
    }
    return arrayOfByte;
  }

  public static boolean generateOAuthResponse(int paramInt, byte[] paramArrayOfByte1, String paramString1, String paramString2, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3, byte[] paramArrayOfByte4)
  {
    return generateOAuthResponse(paramInt, paramArrayOfByte1, paramString1, paramString2, paramString2.getBytes(), paramArrayOfByte2, paramArrayOfByte3, paramArrayOfByte4, true);
  }

  public static boolean generateOAuthResponse(int paramInt, byte[] paramArrayOfByte1, String paramString1, String paramString2, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3, byte[] paramArrayOfByte4, byte[] paramArrayOfByte5)
  {
    return generateOAuthResponse(paramInt, paramArrayOfByte1, paramString1, paramString2, paramArrayOfByte2, paramArrayOfByte3, paramArrayOfByte4, paramArrayOfByte5, true);
  }

  public static boolean generateOAuthResponse(int paramInt, byte[] paramArrayOfByte1, String paramString1, String paramString2, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3, byte[] paramArrayOfByte4, byte[] paramArrayOfByte5, boolean paramBoolean)
  {
    boolean bool = true;
    byte[] arrayOfByte1 = null;
    int i = 0;
    int j = 0;
    int k = 0;
    String str1 = null;
    String str2 = null;
    String str3 = null;
    try
    {
      int m;
      if (paramInt == 2361)
      {
        i = 16;
        j = 32;
        k = 64;
        str1 = "AES/CBC/NoPadding";
        str2 = "AES/CBC/NoPadding";
        str3 = "AES/CBC/PKCS5Padding";
        O3LoginProtocolHelper localObject1;
        byte[] localObject2 = (localObject1 = new O3LoginProtocolHelper()).getVerifier(paramString1, paramString2, Boolean.valueOf(paramBoolean));
        arrayOfByte1 = new byte[16];
        System.arraycopy(localObject2, 0, arrayOfByte1, 0, 8);
        for (m = 8; m < 16; m++)
          arrayOfByte1[m] = 0;
      }
      else if (paramInt == 6949)
      {
        i = 24;
        j = 40;
        k = 96;
        str1 = "AES/CBC/PKCS5Padding";
        str2 = "AES/CBC/PKCS5Padding";
        str3 = "AES/CBC/PKCS5Padding";
        MessageDigest localObject1;
        (localObject1 = MessageDigest.getInstance("SHA1")).update(paramString2.getBytes("UTF-8"));
        localObject1.update(toBinArray(new String(paramArrayOfByte1)));
        byte[] localObject2 = localObject1.digest();
        arrayOfByte1 = new byte[24];
        for (m = 0; m < arrayOfByte1.length; m++)
          arrayOfByte1[m] = 0;
        System.arraycopy(localObject2, 0, arrayOfByte1, 0, localObject2.length);
      }
      else
      {
        return false;
      }
      byte[] localObject1 = decryptAES(str1, arrayOfByte1, new String(paramArrayOfByte3));
      SecureRandom localObject2 = SecureRandom.getInstance("SHA1PRNG");
      byte[] arrayOfByte2 = new byte[j];
      localObject2.nextBytes(arrayOfByte2);
      byte[] arrayOfByte3 = encryptAES(str2, arrayOfByte1, arrayOfByte2);
      if ((paramArrayOfByte4 == null) || (paramArrayOfByte4.length != k))
        return false;
      bArray2Nibbles(arrayOfByte3, paramArrayOfByte4);
      byte[] arrayOfByte4;
      if ((arrayOfByte4 = concatKeys(paramInt, localObject1, localObject1.length - i, arrayOfByte2, arrayOfByte2.length - i)).length != i)
        return false;
      byte[] arrayOfByte5 = new byte[16];
      localObject2.nextBytes(arrayOfByte5);
      byte[] arrayOfByte6 = paramArrayOfByte2;
      byte[] arrayOfByte7 = new byte[16 + arrayOfByte6.length];
      System.arraycopy(arrayOfByte5, 0, arrayOfByte7, 0, 16);
      System.arraycopy(arrayOfByte6, 0, arrayOfByte7, 16, arrayOfByte6.length);
      if (paramArrayOfByte5 == null)
        return false;
      byte[] arrayOfByte8;
      bArray2Nibbles(arrayOfByte8 = encryptAES(str3, arrayOfByte4, arrayOfByte7), paramArrayOfByte5);
    }
    catch (Exception localException)
    {
      bool = false;
    }
    return bool;
  }
}