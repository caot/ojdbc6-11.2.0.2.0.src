package oracle.jdbc.pool;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.util.Enumeration;
import java.util.Properties;
import javax.naming.NamingException;
import javax.naming.Reference;
import javax.naming.Referenceable;
import javax.naming.StringRefAddr;
import javax.sql.DataSource;
import oracle.jdbc.driver.DatabaseError;
import oracle.jdbc.driver.OracleDriver;

public class OracleDataSource
  implements DataSource, Serializable, Referenceable
{
  static final long serialVersionUID = 3349652938965166731L;
  protected PrintWriter logWriter = null;
  protected int loginTimeout = 0;

  protected String databaseName = null;
  protected String serviceName = null;
  protected String dataSourceName = "OracleDataSource";
  protected String description = null;
  protected String networkProtocol = "tcp";
  protected int portNumber = 0;
  protected String user = null;
  protected String password = null;
  protected String serverName = null;
  protected String url = null;
  protected String driverType = null;
  protected String tnsEntry = null;
  protected int maxStatements = 0;
  protected boolean implicitCachingEnabled = false;
  protected boolean explicitCachingEnabled = false;

  protected transient OracleImplicitConnectionCache odsCache = null;
  protected transient OracleConnectionCacheManager cacheManager = null;
  protected String connCacheName = null;
  protected Properties connCacheProperties = null;
  protected Properties connectionProperties = null;
  protected boolean connCachingEnabled = false;
  protected boolean fastConnFailover = false;
  protected String onsConfigStr = null;
  public boolean isOracleDataSource = true;

  private static final boolean fastConnectionFailoverSysProperty = "true".equalsIgnoreCase(OracleDriver.getSystemPropertyFastConnectionFailover("false"));

  private boolean urlExplicit = false;
  private boolean useDefaultConnection = false;
  protected transient OracleDriver driver = new OracleDriver();
  private static final String spawnNewThreadToCancelProperty = "oracle.jdbc.spawnNewThreadToCancel";
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  public OracleDataSource()
    throws SQLException
  {
    processFastConnectionFailoverSysProperty();
  }

  void processFastConnectionFailoverSysProperty()
  {
    if ((this.isOracleDataSource) && (fastConnectionFailoverSysProperty))
    {
      this.connCachingEnabled = true;

      if (this.cacheManager == null)
      {
        try
        {
          this.cacheManager = OracleConnectionCacheManager.getConnectionCacheManagerInstance();
        }
        catch (SQLException localSQLException)
        {
        }

      }

      this.fastConnFailover = true;
      setSpawnNewThreadToCancel(true);
    }
  }

  public Connection getConnection()
    throws SQLException
  {
    String str1 = null;
    String str2 = null;
    synchronized (this)
    {
      str1 = this.user;
      str2 = this.password;
    }
    return getConnection(str1, str2);
  }

  public Connection getConnection(String paramString1, String paramString2)
    throws SQLException
  {
    Connection localConnection = null;
    Properties localProperties = null;
    if (this.connCachingEnabled)
    {
      localConnection = getConnection(paramString1, paramString2, null);
    }
    else
    {
      synchronized (this)
      {
        makeURL();

        localProperties = this.connectionProperties == null ? new Properties() : (Properties)this.connectionProperties.clone();

        if (this.url != null)
          localProperties.setProperty("connection_url", this.url);
        if (paramString1 != null)
          localProperties.setProperty("user", paramString1);
        if (paramString2 != null)
          localProperties.setProperty("password", paramString2);
        if (this.loginTimeout != 0) {
          localProperties.setProperty("LoginTimeout", "" + this.loginTimeout);
        }
        if (this.maxStatements != 0) {
          localProperties.setProperty("stmt_cache_size", "" + this.maxStatements);
        }
      }
      localConnection = getPhysicalConnection(localProperties);
      if (localConnection == null)
      {
        ??? = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 67);
        ((SQLException)???).fillInStackTrace();
        throw ((Throwable)???);
      }
    }
    return localConnection;
  }

  protected Connection getPhysicalConnection(Properties paramProperties)
    throws SQLException
  {
    Connection localConnection = null;
    Properties localProperties = paramProperties;
    String str1 = paramProperties.getProperty("connection_url");
    String str2 = paramProperties.getProperty("user");
    String str3 = localProperties.getProperty("password");
    String str4 = null;
    boolean bool1 = false;

    synchronized (this)
    {
      if (this.connectionProperties != null)
      {
        localProperties = (Properties)this.connectionProperties.clone();

        if (str2 != null) {
          localProperties.put("user", str2);
        }
        if (str3 != null)
          localProperties.put("password", str3);
      }
      if ((str2 == null) && (this.user != null))
        localProperties.put("user", this.user);
      if ((str3 == null) && (this.password != null)) {
        localProperties.put("password", this.password);
      }
      if (str1 == null) {
        str1 = this.url;
      }
      String str5 = paramProperties.getProperty("LoginTimeout");

      if (str5 != null) {
        localProperties.put("oracle.net.CONNECT_TIMEOUT", "" + Integer.parseInt(str5) * 1000);
      }

      bool1 = this.useDefaultConnection;

      if (this.driver == null) {
        this.driver = new OracleDriver();
      }

    }

    if (bool1)
    {
      localConnection = this.driver.defaultConnection();
    }
    else
    {
      localConnection = this.driver.connect(str1, localProperties);
    }

    if (localConnection == null)
    {
      ??? = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 67);
      ((SQLException)???).fillInStackTrace();
      throw ((Throwable)???);
    }

    str4 = paramProperties.getProperty("stmt_cache_size");

    int i = 0;
    if (str4 != null) {
      ((oracle.jdbc.OracleConnection)localConnection).setStatementCacheSize(i = Integer.parseInt(str4));
    }

    boolean bool2 = false;
    str4 = paramProperties.getProperty("ExplicitStatementCachingEnabled");

    if (str4 != null) {
      ((oracle.jdbc.OracleConnection)localConnection).setExplicitCachingEnabled(bool2 = str4.equals("true"));
    }

    boolean bool3 = false;
    str4 = paramProperties.getProperty("ImplicitStatementCachingEnabled");

    if (str4 != null)
    {
      ((oracle.jdbc.OracleConnection)localConnection).setImplicitCachingEnabled(bool3 = str4.equals("true"));
    }

    if ((i > 0) && (!bool2) && (!bool3))
    {
      ((oracle.jdbc.OracleConnection)localConnection).setImplicitCachingEnabled(true);
      ((oracle.jdbc.OracleConnection)localConnection).setExplicitCachingEnabled(true);
    }
    return localConnection;
  }

  /** @deprecated */
  public Connection getConnection(Properties paramProperties)
    throws SQLException
  {
    String str1 = null;
    String str2 = null;
    synchronized (this)
    {
      if (!this.connCachingEnabled)
      {
        SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 137);

        localSQLException.fillInStackTrace();
        throw localSQLException;
      }

      str1 = this.user;
      str2 = this.password;
    }

    ??? = getConnection(str1, str2, paramProperties);

    return ???;
  }

  /** @deprecated */
  public Connection getConnection(String paramString1, String paramString2, Properties paramProperties)
    throws SQLException
  {
    if (!this.connCachingEnabled)
    {
      localObject = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 137);
      ((SQLException)localObject).fillInStackTrace();
      throw ((Throwable)localObject);
    }

    if (this.odsCache == null) {
      cacheInitialize();
    }
    Object localObject = this.odsCache.getConnection(paramString1, paramString2, paramProperties);

    return localObject;
  }

  private synchronized void cacheInitialize()
    throws SQLException
  {
    if (this.odsCache == null)
    {
      if (this.connCacheName != null)
        this.cacheManager.createCache(this.connCacheName, this, this.connCacheProperties);
      else
        this.connCacheName = this.cacheManager.createCache(this, this.connCacheProperties);
    }
  }

  /** @deprecated */
  public synchronized void close()
    throws SQLException
  {
    if ((this.connCachingEnabled) && (this.odsCache != null))
    {
      this.cacheManager.removeCache(this.odsCache.cacheName, 0L);

      this.odsCache = null;
    }
  }

  /** @deprecated */
  public synchronized void setConnectionCachingEnabled(boolean paramBoolean)
    throws SQLException
  {
    if (this.isOracleDataSource)
    {
      if (paramBoolean)
      {
        this.connCachingEnabled = true;

        if (this.cacheManager == null)
        {
          this.cacheManager = OracleConnectionCacheManager.getConnectionCacheManagerInstance();
        }

      }
      else if (this.odsCache == null)
      {
        this.connCachingEnabled = false;
        this.fastConnFailover = false;
        setSpawnNewThreadToCancel(false);
        this.connCacheName = null;
        this.connCacheProperties = null;
      }

    }
    else
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 137);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  public boolean getConnectionCachingEnabled()
    throws SQLException
  {
    return this.connCachingEnabled;
  }

  public synchronized void setConnectionCacheName(String paramString)
    throws SQLException
  {
    if (paramString == null)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 138);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.connCacheName = paramString;
  }

  public String getConnectionCacheName()
    throws SQLException
  {
    if ((this.connCachingEnabled) && (this.odsCache != null)) {
      return this.odsCache.cacheName;
    }
    return this.connCacheName;
  }

  /** @deprecated */
  public synchronized void setConnectionCacheProperties(Properties paramProperties)
    throws SQLException
  {
    this.connCacheProperties = paramProperties;
  }

  public Properties getConnectionCacheProperties()
    throws SQLException
  {
    if ((this.connCachingEnabled) && (this.odsCache != null)) {
      return this.odsCache.getConnectionCacheProperties();
    }
    return this.connCacheProperties;
  }

  public synchronized void setFastConnectionFailoverEnabled(boolean paramBoolean)
    throws SQLException
  {
    if (!this.fastConnFailover)
    {
      this.fastConnFailover = paramBoolean;
      setSpawnNewThreadToCancel(paramBoolean);
    } else if (!paramBoolean)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 255);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  public boolean getFastConnectionFailoverEnabled()
    throws SQLException
  {
    return this.fastConnFailover;
  }

  /** @deprecated */
  public String getONSConfiguration()
    throws SQLException
  {
    return this.onsConfigStr;
  }

  /** @deprecated */
  public synchronized void setONSConfiguration(String paramString)
    throws SQLException
  {
    this.onsConfigStr = paramString;
  }

  public synchronized int getLoginTimeout()
  {
    return this.loginTimeout;
  }

  public synchronized void setLoginTimeout(int paramInt)
  {
    this.loginTimeout = paramInt;
  }

  public synchronized void setLogWriter(PrintWriter paramPrintWriter)
  {
    this.logWriter = paramPrintWriter;
  }

  public synchronized PrintWriter getLogWriter()
  {
    return this.logWriter;
  }

  public synchronized void setTNSEntryName(String paramString)
  {
    this.tnsEntry = paramString;
  }

  public synchronized String getTNSEntryName()
  {
    return this.tnsEntry;
  }

  public synchronized void setDataSourceName(String paramString)
  {
    this.dataSourceName = paramString;
  }

  public synchronized String getDataSourceName()
  {
    return this.dataSourceName;
  }

  public synchronized String getDatabaseName()
  {
    return this.databaseName;
  }

  public synchronized void setDatabaseName(String paramString)
  {
    this.databaseName = paramString;
  }

  public synchronized void setServiceName(String paramString)
  {
    this.serviceName = paramString;
  }

  public synchronized String getServiceName()
  {
    return this.serviceName;
  }

  public synchronized void setServerName(String paramString)
  {
    this.serverName = paramString;
  }

  public synchronized String getServerName()
  {
    return this.serverName;
  }

  public synchronized void setURL(String paramString)
  {
    this.url = paramString;

    if (this.url != null) {
      this.urlExplicit = true;
    }
    if ((this.connCachingEnabled) && (this.odsCache != null) && (this.odsCache.connectionPoolDS != null))
    {
      this.odsCache.connectionPoolDS.url = paramString;
    }
  }

  public synchronized String getURL()
    throws SQLException
  {
    if (!this.urlExplicit)
      makeURL();
    return this.url;
  }

  public synchronized void setUser(String paramString)
  {
    this.user = paramString;
  }

  public String getUser()
  {
    return this.user;
  }

  public synchronized void setPassword(String paramString)
  {
    this.password = paramString;
  }

  protected String getPassword()
  {
    return this.password;
  }

  public synchronized String getDescription()
  {
    return this.description;
  }

  public synchronized void setDescription(String paramString)
  {
    this.description = paramString;
  }

  public synchronized String getDriverType()
  {
    return this.driverType;
  }

  public synchronized void setDriverType(String paramString)
  {
    this.driverType = paramString;
  }

  public synchronized String getNetworkProtocol()
  {
    return this.networkProtocol;
  }

  public synchronized void setNetworkProtocol(String paramString)
  {
    this.networkProtocol = paramString;
  }

  public synchronized void setPortNumber(int paramInt)
  {
    this.portNumber = paramInt;
  }

  public synchronized int getPortNumber()
  {
    return this.portNumber;
  }

  public synchronized Reference getReference()
    throws NamingException
  {
    Reference localReference = new Reference(getClass().getName(), "oracle.jdbc.pool.OracleDataSourceFactory", null);

    addRefProperties(localReference);
    return localReference;
  }

  protected void addRefProperties(Reference paramReference)
  {
    if (this.url != null) {
      paramReference.add(new StringRefAddr("url", this.url));
    }
    if (this.user != null) {
      paramReference.add(new StringRefAddr("userName", this.user));
    }
    if (this.password != null) {
      paramReference.add(new StringRefAddr("passWord", this.password));
    }
    if (this.description != null) {
      paramReference.add(new StringRefAddr("description", this.description));
    }
    if (this.driverType != null) {
      paramReference.add(new StringRefAddr("driverType", this.driverType));
    }
    if (this.serverName != null) {
      paramReference.add(new StringRefAddr("serverName", this.serverName));
    }
    if (this.databaseName != null) {
      paramReference.add(new StringRefAddr("databaseName", this.databaseName));
    }
    if (this.serviceName != null) {
      paramReference.add(new StringRefAddr("serviceName", this.serviceName));
    }
    if (this.networkProtocol != null) {
      paramReference.add(new StringRefAddr("networkProtocol", this.networkProtocol));
    }
    if (this.portNumber != 0) {
      paramReference.add(new StringRefAddr("portNumber", Integer.toString(this.portNumber)));
    }
    if (this.tnsEntry != null) {
      paramReference.add(new StringRefAddr("tnsentryname", this.tnsEntry));
    }
    if (this.maxStatements != 0) {
      paramReference.add(new StringRefAddr("maxStatements", Integer.toString(this.maxStatements)));
    }

    if (this.implicitCachingEnabled) {
      paramReference.add(new StringRefAddr("implicitCachingEnabled", "true"));
    }
    if (this.explicitCachingEnabled) {
      paramReference.add(new StringRefAddr("explicitCachingEnabled", "true"));
    }

    if (this.connCachingEnabled) {
      paramReference.add(new StringRefAddr("connectionCachingEnabled", "true"));
    }
    if (this.connCacheName != null) {
      paramReference.add(new StringRefAddr("connectionCacheName", this.connCacheName));
    }
    if (this.connCacheProperties != null) {
      paramReference.add(new StringRefAddr("connectionCacheProperties", this.connCacheProperties.toString()));
    }

    if (this.fastConnFailover) {
      paramReference.add(new StringRefAddr("fastConnectionFailoverEnabled", "true"));
    }
    if (this.onsConfigStr != null)
      paramReference.add(new StringRefAddr("onsConfigStr", this.onsConfigStr));
  }

  void makeURL()
    throws SQLException
  {
    if (this.urlExplicit)
      return;
    SQLException localSQLException;
    if ((this.driverType == null) || ((!this.driverType.equals("oci8")) && (!this.driverType.equals("oci")) && (!this.driverType.equals("thin")) && (!this.driverType.equals("kprb"))))
    {
      localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 67, "OracleDataSource.makeURL");
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if (this.driverType.equals("kprb"))
    {
      this.useDefaultConnection = true;
      this.url = "jdbc:oracle:kprb:@";

      return;
    }

    if (((this.driverType.equals("oci8")) || (this.driverType.equals("oci"))) && (this.networkProtocol != null) && (this.networkProtocol.equals("ipc")))
    {
      this.url = "jdbc:oracle:oci:@";

      return;
    }

    if (this.tnsEntry != null)
    {
      this.url = ("jdbc:oracle:" + this.driverType + ":@" + this.tnsEntry);

      return;
    }

    if (this.serviceName != null)
    {
      this.url = ("jdbc:oracle:" + this.driverType + ":@(DESCRIPTION=(ADDRESS=(PROTOCOL=" + this.networkProtocol + ")(PORT=" + this.portNumber + ")(HOST=" + this.serverName + "))(CONNECT_DATA=(SERVICE_NAME=" + this.serviceName + ")))");
    }
    else
    {
      this.url = ("jdbc:oracle:" + this.driverType + ":@(DESCRIPTION=(ADDRESS=(PROTOCOL=" + this.networkProtocol + ")(PORT=" + this.portNumber + ")(HOST=" + this.serverName + "))(CONNECT_DATA=(SID=" + this.databaseName + ")))");

      DatabaseError.addSqlWarning(null, new SQLWarning("URL with SID jdbc:subprotocol:@host:port:sid will be deprecated in 10i\nPlease use URL with SERVICE_NAME as jdbc:subprotocol:@//host:port/service_name"));

      if (this.fastConnFailover)
      {
        localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 67, "OracleDataSource.makeURL");
        localSQLException.fillInStackTrace();
        throw localSQLException;
      }
    }
  }

  protected void trace(String paramString)
  {
    if (this.logWriter != null);
  }

  protected void copy(OracleDataSource paramOracleDataSource)
    throws SQLException
  {
    paramOracleDataSource.setUser(this.user);
    paramOracleDataSource.setPassword(this.password);
    paramOracleDataSource.setTNSEntryName(this.tnsEntry);
    makeURL();
    paramOracleDataSource.setURL(this.url);
    if (this.loginTimeout > 0)
      paramOracleDataSource.setLoginTimeout(this.loginTimeout);
    paramOracleDataSource.connectionProperties = this.connectionProperties;
  }

  /** @deprecated */
  public void setMaxStatements(int paramInt)
    throws SQLException
  {
    this.maxStatements = paramInt;
  }

  public int getMaxStatements()
    throws SQLException
  {
    return this.maxStatements;
  }

  public void setImplicitCachingEnabled(boolean paramBoolean)
    throws SQLException
  {
    this.implicitCachingEnabled = paramBoolean;
  }

  public boolean getImplicitCachingEnabled()
    throws SQLException
  {
    return this.implicitCachingEnabled;
  }

  public void setExplicitCachingEnabled(boolean paramBoolean)
    throws SQLException
  {
    this.explicitCachingEnabled = paramBoolean;
  }

  public boolean getExplicitCachingEnabled()
    throws SQLException
  {
    return this.explicitCachingEnabled;
  }

  public void setConnectionProperties(Properties paramProperties)
    throws SQLException
  {
    if (paramProperties == null) this.connectionProperties = paramProperties; else
      this.connectionProperties = ((Properties)paramProperties.clone());
    setSpawnNewThreadToCancel(this.fastConnFailover);
  }

  public Properties getConnectionProperties()
    throws SQLException
  {
    return filterConnectionProperties(this.connectionProperties);
  }

  public static final Properties filterConnectionProperties(Properties paramProperties)
  {
    Properties localProperties = null;

    if (paramProperties != null)
    {
      localProperties = (Properties)paramProperties.clone();
      Enumeration localEnumeration = localProperties.propertyNames();
      Object localObject = null;
      while (localEnumeration.hasMoreElements())
      {
        String str = (String)localEnumeration.nextElement();
        if ((str != null) && (str.matches(".*[P,p][A,a][S,s][S,s][W,w][O,o][R,r][D,d].*")))
        {
          localProperties.remove(str);
        }
      }
      paramProperties.remove("oracle.jdbc.spawnNewThreadToCancel");
    }
    return localProperties;
  }

  private void setSpawnNewThreadToCancel(boolean paramBoolean)
  {
    if (paramBoolean) {
      if (this.connectionProperties == null) this.connectionProperties = new Properties();

      this.connectionProperties.setProperty("oracle.jdbc.spawnNewThreadToCancel", "true");
    }
    else if (this.connectionProperties != null) {
      this.connectionProperties.remove("oracle.jdbc.spawnNewThreadToCancel");
    }
  }

  private void writeObject(ObjectOutputStream paramObjectOutputStream)
    throws IOException
  {
    paramObjectOutputStream.defaultWriteObject();
  }

  private void readObject(ObjectInputStream paramObjectInputStream)
    throws IOException, ClassNotFoundException, SQLException
  {
    paramObjectInputStream.defaultReadObject();

    if (this.connCachingEnabled)
      setConnectionCachingEnabled(this.connCachingEnabled);
  }

  public boolean isWrapperFor(Class<?> paramClass)
    throws SQLException
  {
    if (paramClass.isInterface()) return paramClass.isInstance(this);

    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 177);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public <T> T unwrap(Class<T> paramClass)
    throws SQLException
  {
    if ((paramClass.isInterface()) && (paramClass.isInstance(this))) return this;

    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 177);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  protected oracle.jdbc.internal.OracleConnection getConnectionDuringExceptionHandling()
  {
    return null;
  }
}