package oracle.jdbc.pool;

import java.sql.SQLException;

/** @deprecated */
class OracleGravitateConnectionCacheThread extends Thread
{
  protected OracleImplicitConnectionCache implicitCache = null;

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  OracleGravitateConnectionCacheThread(OracleImplicitConnectionCache paramOracleImplicitConnectionCache)
    throws SQLException
  {
    this.implicitCache = paramOracleImplicitConnectionCache;
  }

  public void run()
  {
    this.implicitCache.gravitateCache();
  }
}