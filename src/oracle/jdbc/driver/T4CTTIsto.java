package oracle.jdbc.driver;

import java.io.IOException;
import java.sql.SQLException;
import oracle.jdbc.internal.OracleConnection;

final class T4CTTIsto extends T4CTTIfun
{
  static final short OV6STRT = 48;
  static final short OV6STOP = 49;
  static final int STOMFDBA = 1;
  static final int STOMFACA = 2;
  static final int STOMFALO = 4;
  static final int STOMFSHU = 8;
  static final int STOMFFRC = 16;
  static final int STOMFPOL = 32;
  static final int STOMFABO = 64;
  static final int STOMFATX = 128;
  static final int STOMFLTX = 256;
  static final int STOSDONE = 1;
  static final int STOSINPR = 2;
  static final int STOSERR = 3;
  private int inmode = 0;
  private int outmode = 0;

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  T4CTTIsto(T4CConnection paramT4CConnection)
  {
    super(paramT4CConnection, (byte)3);
  }

  void doOV6STRT(int paramInt)
    throws IOException, SQLException
  {
    setFunCode((short)48);
    this.inmode = paramInt;
    this.outmode = 0;
    doRPC();
  }

  void doOV6STOP(int paramInt)
    throws IOException, SQLException
  {
    setFunCode((short)49);
    this.inmode = paramInt;
    this.outmode = 0;
    doRPC();
  }

  void marshal()
    throws IOException
  {
    this.meg.marshalSWORD(this.inmode);
    this.meg.marshalPTR();
  }

  void readRPA()
    throws IOException, SQLException
  {
    this.outmode = ((int)this.meg.unmarshalUB4());
    if (this.outmode == 3);
  }

  protected OracleConnection getConnectionDuringExceptionHandling()
  {
    return this.connection;
  }
}