package oracle.jdbc.pool;

import java.sql.SQLException;

/** @deprecated */
class OracleFailoverWorkerThread extends Thread
{
  protected OracleImplicitConnectionCache implicitCache = null;
  protected int eventType = 0;
  protected String eventServiceName = null;
  protected String instanceNameKey = null;
  protected String databaseNameKey = null;
  protected String hostNameKey = null;
  protected String status = null;
  protected int cardinality = 0;

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  OracleFailoverWorkerThread(OracleImplicitConnectionCache paramOracleImplicitConnectionCache, int paramInt1, String paramString1, String paramString2, String paramString3, String paramString4, int paramInt2)
    throws SQLException
  {
    this.implicitCache = paramOracleImplicitConnectionCache;
    this.eventType = paramInt1;
    this.instanceNameKey = paramString1;
    this.databaseNameKey = paramString2;
    this.hostNameKey = paramString3;
    this.status = paramString4;
    this.cardinality = paramInt2;
  }

  public void run()
  {
    try
    {
      if (this.status != null)
      {
        this.implicitCache.processFailoverEvent(this.eventType, this.instanceNameKey, this.databaseNameKey, this.hostNameKey, this.status, this.cardinality);
      }
    }
    catch (Exception localException)
    {
    }
  }
}