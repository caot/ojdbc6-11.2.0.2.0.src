package oracle.jdbc.pool;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import javax.sql.ConnectionPoolDataSource;
import javax.sql.PooledConnection;
import oracle.jdbc.internal.OracleConnection;

public class OracleConnectionPoolDataSource extends OracleDataSource
  implements ConnectionPoolDataSource
{
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  public OracleConnectionPoolDataSource()
    throws SQLException
  {
    this.dataSourceName = "OracleConnectionPoolDataSource";
    this.isOracleDataSource = false;

    this.connCachingEnabled = false;

    this.fastConnFailover = false;
  }

  public PooledConnection getPooledConnection()
    throws SQLException
  {
    String str1 = null;
    String str2 = null;
    synchronized (this)
    {
      str1 = this.user;
      str2 = this.password;
    }
    return getPooledConnection(str1, str2);
  }

  public PooledConnection getPooledConnection(String paramString1, String paramString2)
    throws SQLException
  {
    Connection localConnection = getPhysicalConnection(this.url, paramString1, paramString2);
    OraclePooledConnection localOraclePooledConnection = new OraclePooledConnection(localConnection);

    if (paramString2 == null)
      paramString2 = this.password;
    localOraclePooledConnection.setUserName(!paramString1.startsWith("\"") ? paramString1.toLowerCase() : paramString1, paramString2);

    return localOraclePooledConnection;
  }

  PooledConnection getPooledConnection(Properties paramProperties)
    throws SQLException
  {
    Connection localConnection = getPhysicalConnection(paramProperties);
    OraclePooledConnection localOraclePooledConnection = new OraclePooledConnection(localConnection);

    String str1 = paramProperties.getProperty("user");
    if (str1 == null)
      str1 = ((OracleConnection)localConnection).getUserName();
    String str2 = paramProperties.getProperty("password");
    if (str2 == null)
      str2 = this.password;
    localOraclePooledConnection.setUserName(!str1.startsWith("\"") ? str1.toLowerCase() : str1, str2);

    return localOraclePooledConnection;
  }

  protected Connection getPhysicalConnection()
    throws SQLException
  {
    return super.getConnection(this.user, this.password);
  }

  protected Connection getPhysicalConnection(String paramString1, String paramString2, String paramString3)
    throws SQLException
  {
    this.url = paramString1;
    return super.getConnection(paramString2, paramString3);
  }

  protected Connection getPhysicalConnection(String paramString1, String paramString2)
    throws SQLException
  {
    return super.getConnection(paramString1, paramString2);
  }
}