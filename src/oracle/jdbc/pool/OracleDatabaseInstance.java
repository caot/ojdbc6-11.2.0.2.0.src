package oracle.jdbc.pool;

/** @deprecated */
class OracleDatabaseInstance
{
  String databaseUniqName = null;
  String instanceName = null;
  int percent = 0;
  int flag = 0;
  int attemptedConnRequestCount = 0;
  int numberOfConnectionsCount = 0;

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  OracleDatabaseInstance(String paramString1, String paramString2)
  {
    this.databaseUniqName = paramString1;
    this.instanceName = paramString2;
  }
}