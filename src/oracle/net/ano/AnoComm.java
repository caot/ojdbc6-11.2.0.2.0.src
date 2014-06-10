package oracle.net.ano;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import oracle.net.ns.NetException;
import oracle.net.ns.SessionAtts;

public class AnoComm
{
  private OutputStream a;
  private InputStream b;

  public AnoComm(SessionAtts paramSessionAtts)
  {
    this.a = paramSessionAtts.getOutputStream();
    this.b = paramSessionAtts.getInputStream();
  }

  protected static long a()
  {
    long l;
    return l = 169869312L;
  }

  protected final void b()
    throws IOException
  {
    this.a.flush();
  }

  protected final void a(short paramShort)
    throws IOException
  {
    a(1, 2);
    this.a.write(0xFF & paramShort);
  }

  protected final void a(int paramInt)
    throws IOException
  {
    a(2, 3);
    c(paramInt);
  }

  protected final void a(long paramLong)
    throws IOException
  {
    a(4, 4);
    b(paramLong);
  }

  protected final void a(int[] paramArrayOfInt)
    throws IOException
  {
    a(10 + paramArrayOfInt.length * 2, 1);
    b(-559038737L);
    c(3);
    b(paramArrayOfInt.length);
    for (int i = 0; i < paramArrayOfInt.length; i++)
      c(paramArrayOfInt[i] & 0xFFFF);
  }

  protected final void b(int paramInt)
    throws IOException
  {
    a(2, 6);
    c(paramInt);
  }

  protected final void c()
    throws IOException
  {
    a(4, 5);
    b(a());
  }

  protected final void a(String paramString)
    throws IOException
  {
    a(paramString.length(), 0);
    this.a.write(paramString.getBytes());
  }

  protected final void a(byte[] paramArrayOfByte)
    throws IOException
  {
    a(paramArrayOfByte.length, 1);
    this.a.write(paramArrayOfByte);
  }

  protected final void a(int paramInt1, int paramInt2)
    throws NetException, IOException
  {
    b(paramInt1, paramInt2);
    c(paramInt1);
    c(paramInt2);
  }

  protected final void d()
    throws IOException
  {
    b(a());
  }

  protected final void b(short paramShort)
    throws IOException
  {
    this.a.write(0xFF & paramShort);
  }

  protected final void c(int paramInt)
    throws IOException
  {
    byte[] arrayOfByte = new byte[2];
    int i = a((short)(0xFFFF & paramInt), arrayOfByte);
    this.a.write(arrayOfByte, 0, i);
  }

  protected final void b(long paramLong)
    throws IOException
  {
    byte[] arrayOfByte = new byte[4];
    int i = a((int)(0xFFFFFFFF & paramLong), arrayOfByte);
    this.a.write(arrayOfByte, 0, i);
  }

  protected final short e()
    throws NetException, IOException
  {
    e(2);
    int i;
    return i = m();
  }

  protected final int f()
    throws NetException, IOException
  {
    e(3);
    int i;
    return (i = n()) & 0xFFFF;
  }

  protected final long g()
    throws NetException, IOException
  {
    e(4);
    long l;
    return l = o();
  }

  protected final int[] h()
    throws NetException, IOException
  {
    e(1);
    long l1 = o();
    int i = n();
    long l2;
    int[] arrayOfInt = new int[(int)(l2 = o())];
    if ((l1 != -559038737L) || (i != 3))
      throw new NetException(310);
    for (int j = 0; j < arrayOfInt.length; j++)
      arrayOfInt[j] = n();
    return arrayOfInt;
  }

  protected final int i()
    throws NetException, IOException
  {
    e(6);
    return n();
  }

  protected final long j()
    throws NetException, IOException
  {
    e(5);
    return o();
  }

  protected final String k()
    throws NetException, IOException
  {
    int i = e(0);
    return new String(d(i));
  }

  protected final byte[] l()
    throws NetException, IOException
  {
    int i = e(1);
    return d(i);
  }

  protected final short m()
    throws NetException, IOException
  {
    short s = 0;
    try
    {
      if ((s = (short)this.b.read()) < 0)
        throw new NetException(0);
    }
    catch (NetException localNetException)
    {
      throw new IOException(localNetException.getMessage());
    }
    return s;
  }

  protected final int n()
    throws NetException, IOException
  {
    byte[] arrayOfByte = new byte[2];
    int i;
    return (i = (int)b(arrayOfByte)) & 0xFFFF;
  }

  protected final long o()
    throws NetException, IOException
  {
    byte[] arrayOfByte = new byte[4];
    long l;
    return l = b(arrayOfByte);
  }

  private byte[] d(int paramInt)
    throws NetException, IOException
  {
    byte[] arrayOfByte = new byte[paramInt];
    try
    {
      if (this.b.read(arrayOfByte) < 0)
        throw new NetException(0);
    }
    catch (NetException localNetException)
    {
      throw new IOException(localNetException.getMessage());
    }
    return arrayOfByte;
  }

  private int e(int paramInt)
    throws NetException, IOException
  {
    int i = n();
    int j = n();
    a(i, j, paramInt);
    return i;
  }

  private static void a(int paramInt1, int paramInt2, int paramInt3)
    throws NetException
  {
    if ((paramInt2 < 0) || (paramInt2 > 7))
      throw new NetException(313);
    if (paramInt2 != paramInt3)
      throw new NetException(314);
    switch (paramInt3)
    {
    case 0:
    case 1:
    case 2:
      if (paramInt1 > 1)
        throw new NetException(312);
      break;
    case 3:
    case 6:
      if (paramInt1 > 2)
        throw new NetException(312);
      break;
    case 4:
    case 5:
      if (paramInt1 > 4)
        throw new NetException(312);
      break;
    case 7:
      if (paramInt1 < 10)
        throw new NetException(312);
      break;
    default:
      throw new NetException(313);
    }
  }

  private long b(byte[] paramArrayOfByte)
    throws NetException, IOException
  {
    long l = 0L;
    try
    {
      if (this.b.read(paramArrayOfByte) < 0)
        throw new NetException(0);
    }
    catch (NetException localNetException)
    {
      throw new IOException(localNetException.getMessage());
    }
    for (int i = 0; i < paramArrayOfByte.length; i++)
      l |= (paramArrayOfByte[i] & 0xFF) << 8 * (paramArrayOfByte.length - 1 - i);
    return l &= -1L;
  }

  private static void b(int paramInt1, int paramInt2)
    throws NetException
  {
    if ((paramInt2 < 0) || (paramInt2 > 7))
      throw new NetException(313);
    switch (paramInt2)
    {
    case 0:
    case 1:
    case 2:
      if (paramInt1 > 1)
        throw new NetException(312);
      break;
    case 3:
    case 6:
      if (paramInt1 > 2)
        throw new NetException(312);
      break;
    case 4:
    case 5:
      if (paramInt1 > 4)
        throw new NetException(312);
      break;
    case 7:
      if (paramInt1 < 10)
        throw new NetException(312);
      break;
    default:
      throw new NetException(313);
    }
  }

  private static byte a(int paramInt, byte[] paramArrayOfByte)
  {
    byte b1 = 0;
    for (int i = paramArrayOfByte.length - 1; i >= 0; i--)
    {
      b1 = (byte)(b1 + 1);
      paramArrayOfByte[b1] = ((byte)(paramInt >>> 8 * i & 0xFF));
    }
    return b1;
  }
}