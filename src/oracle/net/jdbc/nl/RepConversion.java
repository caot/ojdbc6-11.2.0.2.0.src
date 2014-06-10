package oracle.net.jdbc.nl;

import java.io.PrintStream;

public class RepConversion
{
  public static void printInHex(byte paramByte)
  {
    System.out.print((char)nibbleToHex((byte)((paramByte & 0xF0) >> 4)));
    System.out.print((char)nibbleToHex((byte)(paramByte & 0xF)));
  }

  public static byte nibbleToHex(byte paramByte)
  {
    paramByte = (byte)(paramByte & 0xF);
    return (byte)(paramByte < 10 ? paramByte + 48 : paramByte - 10 + 65);
  }

  public static byte asciiHexToNibble(byte paramByte)
  {
    byte b;
    if ((paramByte >= 97) && (paramByte <= 102))
      b = (byte)(paramByte - 97 + 10);
    else if ((paramByte >= 65) && (paramByte <= 70))
      b = (byte)(paramByte - 65 + 10);
    else if ((paramByte >= 48) && (paramByte <= 57))
      b = (byte)(paramByte - 48);
    else
      b = paramByte;
    return b;
  }

  public static void bArray2nibbles(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
  {
    for (int i = 0; i < paramArrayOfByte1.length; i++)
    {
      paramArrayOfByte2[(i * 2)] = nibbleToHex((byte)((paramArrayOfByte1[i] & 0xF0) >> 4));
      paramArrayOfByte2[(i * 2 + 1)] = nibbleToHex((byte)(paramArrayOfByte1[i] & 0xF));
    }
  }

  public static String bArray2String(byte[] paramArrayOfByte)
  {
    StringBuffer localStringBuffer = new StringBuffer(paramArrayOfByte.length * 2);
    for (int i = 0; i < paramArrayOfByte.length; i++)
    {
      localStringBuffer.append((char)nibbleToHex((byte)((paramArrayOfByte[i] & 0xF0) >> 4)));
      localStringBuffer.append((char)nibbleToHex((byte)(paramArrayOfByte[i] & 0xF)));
    }
    return localStringBuffer.toString();
  }

  public static byte[] nibbles2bArray(byte[] paramArrayOfByte)
  {
    byte[] arrayOfByte = new byte[paramArrayOfByte.length / 2];
    for (int i = 0; i < arrayOfByte.length; i++)
    {
      arrayOfByte[i] = ((byte)(asciiHexToNibble(paramArrayOfByte[(i * 2)]) << 4));
      int tmp31_30 = i;
      byte[] tmp31_29 = arrayOfByte;
      tmp31_29[tmp31_30] = ((byte)(tmp31_29[tmp31_30] | asciiHexToNibble(paramArrayOfByte[(i * 2 + 1)])));
    }
    return arrayOfByte;
  }

  public static void printInHex(long paramLong)
  {
    byte[] arrayOfByte = toHex(paramLong);
    System.out.print(new String(arrayOfByte));
  }

  public static void printInHex(int paramInt)
  {
    byte[] arrayOfByte = toHex(paramInt);
    System.out.print(new String(arrayOfByte));
  }

  public static void printInHex(short paramShort)
  {
    byte[] arrayOfByte = toHex(paramShort);
    System.out.print(new String(arrayOfByte));
  }

  public static byte[] toHex(long paramLong)
  {
    int i = 16;
    byte[] arrayOfByte = new byte[i];
    for (int j = i - 1; j >= 0; j--)
    {
      arrayOfByte[j] = nibbleToHex((byte)(int)(paramLong & 0xF));
      paramLong >>= 4;
    }
    return arrayOfByte;
  }

  public static byte[] toHex(int paramInt)
  {
    int i = 8;
    byte[] arrayOfByte = new byte[i];
    for (int j = i - 1; j >= 0; j--)
    {
      arrayOfByte[j] = nibbleToHex((byte)(paramInt & 0xF));
      paramInt >>= 4;
    }
    return arrayOfByte;
  }

  public static byte[] toHex(short paramShort)
  {
    int i = 4;
    byte[] arrayOfByte = new byte[i];
    for (int j = i - 1; j >= 0; j--)
    {
      arrayOfByte[j] = nibbleToHex((byte)(paramShort & 0xF));
      paramShort = (short)(paramShort >> 4);
    }
    return arrayOfByte;
  }
}