package oracle.jdbc.driver;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import oracle.jdbc.internal.OracleConnection;

final class T4CTTIspfp extends T4CTTIfun
{
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  T4CTTIspfp(T4CConnection paramT4CConnection)
  {
    super(paramT4CConnection, (byte)3);

    setFunCode((short)138);
  }

  void doOSPFPPUT()
    throws IOException, SQLException
  {
    doRPC();
  }

  void marshal()
    throws IOException
  {
    this.meg.marshalPTR();
    this.meg.marshalSWORD(100);
    this.meg.marshalPTR();
    this.meg.marshalPTR();
    this.meg.marshalSWORD(0);
    this.meg.marshalNULLPTR();
    this.meg.marshalSWORD(0);
    this.meg.marshalNULLPTR();
    this.meg.marshalNULLPTR();
  }

  void readRPA()
    throws IOException, SQLException
  {
    int i = this.meg.unmarshalUB2();
    byte[] arrayOfByte = this.meg.unmarshalNBytes(i);
    if (i > 1)
    {
      String str = this.meg.conv.CharBytesToString(arrayOfByte, i, true);
      SQLWarning localSQLWarning1 = new SQLWarning(str);
      SQLWarning localSQLWarning2 = this.connection.getWarnings();
      if (localSQLWarning2 == null)
        this.connection.setWarnings(localSQLWarning1);
      else
        localSQLWarning2.setNextWarning(localSQLWarning1);
    }
    this.meg.unmarshalUB2();
    this.meg.unmarshalUB2();
  }

  protected OracleConnection getConnectionDuringExceptionHandling()
  {
    return this.connection;
  }
}