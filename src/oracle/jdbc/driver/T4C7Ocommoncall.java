package oracle.jdbc.driver;

import java.io.IOException;
import java.sql.SQLException;
import oracle.jdbc.internal.OracleConnection;

final class T4C7Ocommoncall extends T4CTTIfun
{
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  T4C7Ocommoncall(T4CConnection paramT4CConnection)
  {
    super(paramT4CConnection, (byte)3);
  }

  void doOLOGOFF()
    throws SQLException, IOException
  {
    setFunCode((short)9);
    doRPC();
  }

  void doOROLLBACK()
    throws SQLException, IOException
  {
    setFunCode((short)15);
    doRPC();
  }

  void doOCOMMIT()
    throws SQLException, IOException
  {
    setFunCode((short)14);
    doRPC();
  }

  void marshal()
    throws IOException
  {
  }

  void processError()
    throws SQLException
  {
    if (this.oer.retCode != 2089)
      this.oer.processError();
  }

  protected OracleConnection getConnectionDuringExceptionHandling()
  {
    return null;
  }
}