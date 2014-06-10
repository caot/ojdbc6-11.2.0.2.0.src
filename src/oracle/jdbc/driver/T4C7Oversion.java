package oracle.jdbc.driver;

import java.io.IOException;
import java.sql.SQLException;
import oracle.jdbc.internal.OracleConnection;

final class T4C7Oversion extends T4CTTIfun
{
  byte[] rdbmsVersion = { 78, 111, 116, 32, 100, 101, 116, 101, 114, 109, 105, 110, 101, 100, 32, 121, 101, 116 };

  private final boolean rdbmsVersionO2U = true;

  private final int bufLen = 256;
  private final boolean retVerLenO2U = true;
  int retVerLen = 0;
  private final boolean retVerNumO2U = true;
  long retVerNum = 0L;

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  T4C7Oversion(T4CConnection paramT4CConnection)
  {
    super(paramT4CConnection, (byte)3);

    setFunCode((short)59);
  }

  void doOVERSION()
    throws SQLException, IOException
  {
    doRPC();
  }

  void readRPA()
    throws IOException, SQLException
  {
    this.retVerLen = this.meg.unmarshalUB2();
    this.rdbmsVersion = this.meg.unmarshalCHR(this.retVerLen);
    this.retVerNum = this.meg.unmarshalUB4();
  }

  void processRPA()
    throws SQLException
  {
    if (this.rdbmsVersion == null)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 438);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  byte[] getVersion()
  {
    return this.rdbmsVersion;
  }

  short getVersionNumber()
  {
    int i = 0;

    i = (int)(i + (this.retVerNum >>> 24 & 0xFF) * 1000L);
    i = (int)(i + (this.retVerNum >>> 20 & 0xF) * 100L);
    i = (int)(i + (this.retVerNum >>> 12 & 0xF) * 10L);
    i = (int)(i + (this.retVerNum >>> 8 & 0xF));

    return (short)i;
  }

  long getVersionNumberasIs()
  {
    return this.retVerNum;
  }

  void marshal()
    throws IOException
  {
    this.meg.marshalO2U(true);
    this.meg.marshalSWORD(256);
    this.meg.marshalO2U(true);
    this.meg.marshalO2U(true);
  }

  protected OracleConnection getConnectionDuringExceptionHandling()
  {
    return this.connection;
  }
}