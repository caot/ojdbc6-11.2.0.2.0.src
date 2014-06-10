package oracle.jdbc.driver;

import java.io.IOException;

final class T4C8Oclose extends T4CTTIfun
{
  private int[] cursorId = null;
  private int offset = 0;

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  T4C8Oclose(T4CConnection paramT4CConnection)
  {
    super(paramT4CConnection, (byte)17);
  }

  void doOCANA(int[] paramArrayOfInt, int paramInt)
    throws IOException
  {
    setFunCode((short)120);
    this.cursorId = paramArrayOfInt;
    this.offset = paramInt;
    doPigRPC();
  }

  void doOCCA(int[] paramArrayOfInt, int paramInt)
    throws IOException
  {
    setFunCode((short)105);
    this.cursorId = paramArrayOfInt;
    this.offset = paramInt;
    doPigRPC();
  }

  void marshal()
    throws IOException
  {
    this.meg.marshalPTR();
    this.meg.marshalUB4(this.offset);

    for (int i = 0; i < this.offset; i++)
    {
      this.meg.marshalUB4(this.cursorId[i]);
    }
  }
}