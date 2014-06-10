package oracle.jdbc.oracore;

import java.io.ByteArrayOutputStream;

public class PickleOutputStream extends ByteArrayOutputStream
{
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  public PickleOutputStream()
  {
  }

  public PickleOutputStream(int paramInt)
  {
    super(paramInt);
  }

  public synchronized int offset()
  {
    return this.count;
  }

  public synchronized void overwrite(int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3)
  {
    if ((paramInt2 < 0) || (paramInt2 > paramArrayOfByte.length) || (paramInt3 < 0) || (paramInt2 + paramInt3 > paramArrayOfByte.length) || (paramInt2 + paramInt3 < 0) || (paramInt1 + paramInt3 > this.buf.length))
    {
      throw new IndexOutOfBoundsException();
    }
    if (paramInt3 == 0)
    {
      return;
    }

    for (int i = 0; i < paramInt3; i++)
    {
      this.buf[(paramInt1 + i)] = paramArrayOfByte[(paramInt2 + i)];
    }
  }
}