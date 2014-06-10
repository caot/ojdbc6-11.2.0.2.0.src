package oracle.jdbc.driver;

import java.io.IOException;
import java.sql.SQLException;
import oracle.jdbc.internal.OracleConnection;

final class T4CTTIk2rpc extends T4CTTIfun
{
  static final int K2RPClogon = 1;
  static final int K2RPCbegin = 2;
  static final int K2RPCend = 3;
  static final int K2RPCrecover = 4;
  static final int K2RPCsession = 5;
  private int k2rpctyp;
  private int command;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  T4CTTIk2rpc(T4CConnection paramT4CConnection)
  {
    super(paramT4CConnection, (byte)3);

    setFunCode((short)67);
  }

  void doOK2RPC(int paramInt1, int paramInt2)
    throws IOException, SQLException
  {
    this.k2rpctyp = paramInt1;
    this.command = paramInt2;
    doRPC();
  }

  void marshal()
    throws IOException
  {
    this.meg.marshalUB4(0L);
    this.meg.marshalUB4(this.k2rpctyp);
    this.meg.marshalPTR();
    this.meg.marshalUB4(3L);
    this.meg.marshalNULLPTR();
    this.meg.marshalUB4(0L);
    this.meg.marshalNULLPTR();
    this.meg.marshalUB4(0L);
    this.meg.marshalPTR();
    this.meg.marshalUB4(3L);
    this.meg.marshalPTR();
    this.meg.marshalNULLPTR();
    this.meg.marshalUB4(0L);
    this.meg.marshalNULLPTR();
    this.meg.marshalNULLPTR();
    this.meg.marshalUB4(0L);
    this.meg.marshalNULLPTR();

    this.meg.marshalUB4(this.command);
    this.meg.marshalUB4(0L);
    this.meg.marshalUB4(0L);
  }

  void readRPA()
    throws IOException, SQLException
  {
    int i = this.meg.unmarshalUB2();

    for (int j = 0; j < i; j++)
    {
      this.meg.unmarshalUB4();
    }
  }

  protected OracleConnection getConnectionDuringExceptionHandling()
  {
    return null;
  }
}