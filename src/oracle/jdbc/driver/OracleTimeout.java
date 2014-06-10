package oracle.jdbc.driver;

import java.sql.SQLException;

abstract class OracleTimeout
{
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  static OracleTimeout newTimeout(String paramString)
    throws SQLException
  {
    OracleTimeoutThreadPerVM localOracleTimeoutThreadPerVM = new OracleTimeoutThreadPerVM(paramString);
    return localOracleTimeoutThreadPerVM;
  }

  abstract void setTimeout(long paramLong, OracleStatement paramOracleStatement)
    throws SQLException;

  abstract void cancelTimeout()
    throws SQLException;

  abstract void close()
    throws SQLException;
}