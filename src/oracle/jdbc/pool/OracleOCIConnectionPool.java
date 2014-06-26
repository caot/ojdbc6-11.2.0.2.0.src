package oracle.jdbc.pool;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Properties;
import javax.naming.NamingException;
import javax.naming.Reference;
import javax.naming.StringRefAddr;
import oracle.jdbc.driver.DatabaseError;
import oracle.jdbc.driver.OracleDriver;
import oracle.jdbc.internal.OracleConnection;
import oracle.jdbc.oci.OracleOCIConnection;

public class OracleOCIConnectionPool extends OracleDataSource
{
  public OracleOCIConnection m_connection_pool;
  public static final String IS_CONNECTION_POOLING = "is_connection_pooling";
  private int m_conn_min_limit = 0;
  private int m_conn_max_limit = 0;
  private int m_conn_increment = 0;
  private int m_conn_active_size = 0;
  private int m_conn_pool_size = 0;
  private int m_conn_timeout = 0;
  private String m_conn_nowait = "false";
  private int m_is_transactions_distributed = 0;
  public static final String CONNPOOL_OBJECT = "connpool_object";
  public static final String CONNPOOL_LOGON_MODE = "connection_pool";
  public static final String CONNECTION_POOL = "connection_pool";
  public static final String CONNPOOL_CONNECTION = "connpool_connection";
  public static final String CONNPOOL_PROXY_CONNECTION = "connpool_proxy_connection";
  public static final String CONNPOOL_ALIASED_CONNECTION = "connpool_alias_connection";
  public static final String PROXY_USER_NAME = "proxy_user_name";
  public static final String PROXY_DISTINGUISHED_NAME = "proxy_distinguished_name";
  public static final String PROXY_CERTIFICATE = "proxy_certificate";
  public static final String PROXY_ROLES = "proxy_roles";
  public static final String PROXY_NUM_ROLES = "proxy_num_roles";
  public static final String PROXY_PASSWORD = "proxy_password";
  public static final String PROXYTYPE = "proxytype";
  public static final String PROXYTYPE_USER_NAME = "proxytype_user_name";
  public static final String PROXYTYPE_DISTINGUISHED_NAME = "proxytype_distinguished_name";
  public static final String PROXYTYPE_CERTIFICATE = "proxytype_certificate";
  public static final String CONNECTION_ID = "connection_id";
  public static final String CONNPOOL_MIN_LIMIT = "connpool_min_limit";
  public static final String CONNPOOL_MAX_LIMIT = "connpool_max_limit";
  public static final String CONNPOOL_INCREMENT = "connpool_increment";
  public static final String CONNPOOL_ACTIVE_SIZE = "connpool_active_size";
  public static final String CONNPOOL_POOL_SIZE = "connpool_pool_size";
  public static final String CONNPOOL_TIMEOUT = "connpool_timeout";
  public static final String CONNPOOL_NOWAIT = "connpool_nowait";
  public static final String CONNPOOL_IS_POOLCREATED = "connpool_is_poolcreated";
  public static final String TRANSACTIONS_DISTRIBUTED = "transactions_distributed";
  private Hashtable m_lconnections = null;

  private Lifecycle lifecycle = Lifecycle.NEW;

  private OracleDriver m_oracleDriver = new OracleDriver();

  protected int m_stmtCacheSize = 0;
  protected boolean m_stmtClearMetaData = false;

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  private void ensureOpen()
    throws SQLException
  {
    if (this.lifecycle == Lifecycle.NEW) {
      createConnectionPool(null);
    }
    if (this.lifecycle != Lifecycle.OPEN)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 8);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  public OracleOCIConnectionPool(String paramString1, String paramString2, String paramString3, Properties paramProperties)
    throws SQLException
  {
    this();

    setURL(paramString3);
    setUser(paramString1);
    setPassword(paramString2);
    createConnectionPool(paramProperties);
  }

  /** @deprecated */
  public OracleOCIConnectionPool(String paramString1, String paramString2, String paramString3)
    throws SQLException
  {
    this();

    setURL(paramString3);
    setUser(paramString1);
    setPassword(paramString2);
    createConnectionPool(null);
  }

  public OracleOCIConnectionPool()
    throws SQLException
  {
    this.isOracleDataSource = false;
    this.m_lconnections = new Hashtable(10);

    setDriverType("oci8");
  }

  public synchronized Connection getConnection()
    throws SQLException
  {
    ensureOpen();

    Connection localConnection = getConnection(this.user, this.password);

    return localConnection;
  }

  public synchronized Connection getConnection(String paramString1, String paramString2)
    throws SQLException
  {
    ensureOpen();
    Properties localProperties;
    if (this.connectionProperties != null)
      localProperties = new Properties(this.connectionProperties);
    else {
      localProperties = new Properties();
    }
    localProperties.put("is_connection_pooling", "true");
    localProperties.put("user", paramString1);
    localProperties.put("password", paramString2);
    localProperties.put("connection_pool", "connpool_connection");
    localProperties.put("connpool_object", this.m_connection_pool);

    OracleOCIConnection localOracleOCIConnection = (OracleOCIConnection)this.m_oracleDriver.connect(this.url, localProperties);

    if (localOracleOCIConnection == null)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 67);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    localOracleOCIConnection.setStmtCacheSize(this.m_stmtCacheSize, this.m_stmtClearMetaData);

    this.m_lconnections.put(localOracleOCIConnection, localOracleOCIConnection);

    localOracleOCIConnection.setConnectionPool(this);

    return localOracleOCIConnection;
  }

  public synchronized Reference getReference()
    throws NamingException
  {
    Reference localReference = new Reference(getClass().getName(), "oracle.jdbc.pool.OracleDataSourceFactory", null);

    super.addRefProperties(localReference);

    localReference.add(new StringRefAddr("connpool_min_limit", String.valueOf(this.m_conn_min_limit)));

    localReference.add(new StringRefAddr("connpool_max_limit", String.valueOf(this.m_conn_max_limit)));

    localReference.add(new StringRefAddr("connpool_increment", String.valueOf(this.m_conn_increment)));

    localReference.add(new StringRefAddr("connpool_active_size", String.valueOf(this.m_conn_active_size)));

    localReference.add(new StringRefAddr("connpool_pool_size", String.valueOf(this.m_conn_pool_size)));

    localReference.add(new StringRefAddr("connpool_timeout", String.valueOf(this.m_conn_timeout)));

    localReference.add(new StringRefAddr("connpool_nowait", this.m_conn_nowait));

    localReference.add(new StringRefAddr("connpool_is_poolcreated", String.valueOf(isPoolCreated())));

    localReference.add(new StringRefAddr("transactions_distributed", String.valueOf(isDistributedTransEnabled())));

    return localReference;
  }

  public synchronized OracleConnection getProxyConnection(String paramString, Properties paramProperties)
    throws SQLException
  {
    ensureOpen();

    if ("proxytype_user_name".equals(paramString)) {
      paramProperties.put("user", paramProperties.getProperty("proxy_user_name"));
    } else if ("proxytype_distinguished_name".equals(paramString)) {
      paramProperties.put("user", paramProperties.getProperty("proxy_distinguished_name"));
    }
    else if ("proxytype_certificate".equals(paramString)) {
      paramProperties.put("user", String.valueOf(paramProperties.getProperty("proxy_user_name")));
    }
    else
    {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 107, "null properties");

      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    paramProperties.put("is_connection_pooling", "true");
    paramProperties.put("proxytype", paramString);
    String[] arrayOfString;
    if ((arrayOfString = (String[])paramProperties.get("proxy_roles")) != null)
    {
      paramProperties.put("proxy_num_roles", new Integer(arrayOfString.length));
    }
    else
    {
      paramProperties.put("proxy_num_roles", new Integer(0));
    }

    paramProperties.put("connection_pool", "connpool_proxy_connection");
    paramProperties.put("connpool_object", this.m_connection_pool);

    OracleOCIConnection ociconnection = (OracleOCIConnection)this.m_oracleDriver.connect(this.url, paramProperties);

    if (ociconnection == null)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 67);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    ociconnection.setStmtCacheSize(this.m_stmtCacheSize, this.m_stmtClearMetaData);

    this.m_lconnections.put(ociconnection, ociconnection);
    ociconnection.setConnectionPool(this);

    return ociconnection;
  }

  public synchronized OracleConnection getAliasedConnection(byte[] paramArrayOfByte)
    throws SQLException
  {
    ensureOpen();
    Properties localProperties = new Properties();

    localProperties.put("is_connection_pooling", "true");
    localProperties.put("connection_id", paramArrayOfByte);
    localProperties.put("connection_pool", "connpool_alias_connection");
    localProperties.put("connpool_object", this.m_connection_pool);

    OracleOCIConnection localOracleOCIConnection = (OracleOCIConnection)this.m_oracleDriver.connect(this.url, localProperties);

    if (localOracleOCIConnection == null)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 67);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    localOracleOCIConnection.setStmtCacheSize(this.m_stmtCacheSize, this.m_stmtClearMetaData);

    this.m_lconnections.put(localOracleOCIConnection, localOracleOCIConnection);
    localOracleOCIConnection.setConnectionPool(this);

    return localOracleOCIConnection;
  }

  public synchronized void close()
    throws SQLException
  {
    if (this.lifecycle != Lifecycle.OPEN) {
      return;
    }
    this.lifecycle = Lifecycle.CLOSING;

    Iterator localIterator = this.m_lconnections.values().iterator();

    while (localIterator.hasNext())
    {
      OracleOCIConnection localOracleOCIConnection = (OracleOCIConnection)localIterator.next();

      if ((localOracleOCIConnection != null) && (localOracleOCIConnection != this.m_connection_pool))
      {
        localOracleOCIConnection.close();
      }
      localIterator.remove();
    }

    this.m_connection_pool.close();

    this.lifecycle = Lifecycle.CLOSED;
  }

  public synchronized void setPoolConfig(Properties paramProperties)
    throws SQLException
  {
    Object localObject;
    if (paramProperties == null)
    {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 106, "null properties");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    if (!isPoolCreated())
    {
      createConnectionPool(paramProperties);
    }
    else
    {
      localObject = new Properties();

      checkPoolConfig(paramProperties, (Properties)localObject);

      int[] arrayOfInt = new int[6];

      readPoolConfig((Properties)localObject, arrayOfInt);

      this.m_connection_pool.setConnectionPoolInfo(arrayOfInt[0], arrayOfInt[1], arrayOfInt[2], arrayOfInt[3], arrayOfInt[4], arrayOfInt[5]);
    }

    storePoolProperties();
  }

  public static void readPoolConfig(int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean1, boolean paramBoolean2, int[] paramArrayOfInt)
  {
    for (int i = 0; i < 6; i++)
      paramArrayOfInt[i] = 0;
    paramArrayOfInt[0] = paramInt1;
    paramArrayOfInt[1] = paramInt2;
    paramArrayOfInt[2] = paramInt3;
    paramArrayOfInt[3] = paramInt4;
    if (paramBoolean1)
      paramArrayOfInt[4] = 1;
    if (paramBoolean2)
      paramArrayOfInt[5] = 1;
  }

  public static void readPoolConfig(Properties paramProperties, int[] paramArrayOfInt)
  {
    String str = paramProperties.getProperty("connpool_min_limit");

    if (str != null) {
      paramArrayOfInt[0] = Integer.parseInt(str);
    }
    str = paramProperties.getProperty("connpool_max_limit");

    if (str != null) {
      paramArrayOfInt[1] = Integer.parseInt(str);
    }
    str = paramProperties.getProperty("connpool_increment");

    if (str != null) {
      paramArrayOfInt[2] = Integer.parseInt(str);
    }
    str = paramProperties.getProperty("connpool_timeout");

    if (str != null) {
      paramArrayOfInt[3] = Integer.parseInt(str);
    }
    str = paramProperties.getProperty("connpool_nowait");

    if ((str != null) && (str.equalsIgnoreCase("true"))) {
      paramArrayOfInt[4] = 1;
    }
    str = paramProperties.getProperty("transactions_distributed");

    if ((str != null) && (str.equalsIgnoreCase("true")))
      paramArrayOfInt[5] = 1;
  }

  private void checkPoolConfig(Properties paramProperties1, Properties paramProperties2)
    throws SQLException
  {
    String str1 = (String)paramProperties1.get("transactions_distributed");
    String str2 = (String)paramProperties1.get("connpool_nowait");

    if (((str1 != null) && (!str1.equalsIgnoreCase("true"))) || ((str2 != null) && (!str2.equalsIgnoreCase("true"))) || (paramProperties1.get("connpool_min_limit") == null) || (paramProperties1.get("connpool_max_limit") == null) || (paramProperties1.get("connpool_increment") == null) || (Integer.decode((String)paramProperties1.get("connpool_min_limit")).intValue() < 0) || (Integer.decode((String)paramProperties1.get("connpool_max_limit")).intValue() < 0) || (Integer.decode((String)paramProperties1.get("connpool_increment")).intValue() < 0))
    {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 106, "");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject = paramProperties1.propertyNames();

    while (((Enumeration)localObject).hasMoreElements())
    {
      String str3 = (String)((Enumeration)localObject).nextElement();
      String str4 = paramProperties1.getProperty(str3);

      if (("transactions_distributed".equals(str3)) || ("connpool_nowait".equals(str3)))
        paramProperties2.put(str3, "true");
      else
        paramProperties2.put(str3, str4);
    }
  }

  private synchronized void storePoolProperties()
    throws SQLException
  {
    Properties localProperties = getPoolConfig();

    this.m_conn_min_limit = Integer.decode(localProperties.getProperty("connpool_min_limit")).intValue();

    this.m_conn_max_limit = Integer.decode(localProperties.getProperty("connpool_max_limit")).intValue();

    this.m_conn_increment = Integer.decode(localProperties.getProperty("connpool_increment")).intValue();

    this.m_conn_active_size = Integer.decode(localProperties.getProperty("connpool_active_size")).intValue();

    this.m_conn_pool_size = Integer.decode(localProperties.getProperty("connpool_pool_size")).intValue();

    this.m_conn_timeout = Integer.decode(localProperties.getProperty("connpool_timeout")).intValue();

    this.m_conn_nowait = localProperties.getProperty("connpool_nowait");
  }

  public synchronized Properties getPoolConfig()
    throws SQLException
  {
    ensureOpen();

    Properties localProperties = this.m_connection_pool.getConnectionPoolInfo();

    localProperties.put("connpool_is_poolcreated", String.valueOf(isPoolCreated()));

    return localProperties;
  }

  public synchronized int getActiveSize()
    throws SQLException
  {
    ensureOpen();

    Properties localProperties = this.m_connection_pool.getConnectionPoolInfo();

    String str = localProperties.getProperty("connpool_active_size");
    int i = Integer.decode(str).intValue();

    return i;
  }

  public synchronized int getPoolSize()
    throws SQLException
  {
    ensureOpen();

    Properties localProperties = this.m_connection_pool.getConnectionPoolInfo();

    String str = localProperties.getProperty("connpool_pool_size");
    int i = Integer.decode(str).intValue();

    return i;
  }

  public synchronized int getTimeout()
    throws SQLException
  {
    ensureOpen();

    Properties localProperties = this.m_connection_pool.getConnectionPoolInfo();

    String str = localProperties.getProperty("connpool_timeout");
    int i = Integer.decode(str).intValue();

    return i;
  }

  public synchronized String getNoWait()
    throws SQLException
  {
    ensureOpen();

    Properties localProperties = this.m_connection_pool.getConnectionPoolInfo();

    return localProperties.getProperty("connpool_nowait");
  }

  public synchronized int getMinLimit()
    throws SQLException
  {
    ensureOpen();

    Properties localProperties = this.m_connection_pool.getConnectionPoolInfo();

    String str = localProperties.getProperty("connpool_min_limit");
    int i = Integer.decode(str).intValue();

    return i;
  }

  public synchronized int getMaxLimit()
    throws SQLException
  {
    ensureOpen();

    Properties localProperties = this.m_connection_pool.getConnectionPoolInfo();

    String str = localProperties.getProperty("connpool_max_limit");
    int i = Integer.decode(str).intValue();

    return i;
  }

  public synchronized int getConnectionIncrement()
    throws SQLException
  {
    ensureOpen();

    Properties localProperties = this.m_connection_pool.getConnectionPoolInfo();

    String str = localProperties.getProperty("connpool_increment");
    int i = Integer.decode(str).intValue();

    return i;
  }

  public synchronized boolean isDistributedTransEnabled()
  {
    if (this.m_is_transactions_distributed == 1) {
      return true;
    }
    return false;
  }

  private void createConnectionPool(Properties paramProperties)
    throws SQLException
  {
    if (this.lifecycle != Lifecycle.NEW) {
      return;
    }
    if ((this.user == null) || (this.password == null))
    {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 106, " ");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject = new Properties();

    if (paramProperties != null) {
      checkPoolConfig(paramProperties, (Properties)localObject);
    }
    ((Properties)localObject).put("is_connection_pooling", "true");
    ((Properties)localObject).put("user", this.user);
    ((Properties)localObject).put("password", this.password);
    ((Properties)localObject).put("connection_pool", "connection_pool");

    if (getURL() == null) {
      makeURL();
    }

    this.m_connection_pool = ((OracleOCIConnection)this.m_oracleDriver.connect(this.url, (Properties)localObject));

    if (this.m_connection_pool == null)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 67);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.m_connection_pool.setConnectionPool(this);

    this.m_lconnections.put(this.m_connection_pool, this.m_connection_pool);

    this.lifecycle = Lifecycle.OPEN;

    storePoolProperties();

    if (paramProperties != null)
    {
      if ("true".equalsIgnoreCase(paramProperties.getProperty("transactions_distributed")))
        this.m_is_transactions_distributed = 1;
    }
  }

  public synchronized boolean isPoolCreated()
  {
    return this.lifecycle == Lifecycle.OPEN;
  }

  public synchronized void connectionClosed(OracleOCIConnection paramOracleOCIConnection)
    throws SQLException
  {
    if (this.lifecycle != Lifecycle.CLOSING)
    {
      if (this.m_lconnections.remove(paramOracleOCIConnection) == null)
      {
        SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 1, "internal OracleOCIConnectionPool error");
        localSQLException.fillInStackTrace();
        throw localSQLException;
      }
    }
  }

  public synchronized void setStmtCacheSize(int paramInt)
    throws SQLException
  {
    setStmtCacheSize(paramInt, false);
  }

  public synchronized void setStmtCacheSize(int paramInt, boolean paramBoolean)
    throws SQLException
  {
    if (paramInt < 0)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.m_stmtCacheSize = paramInt;
    this.m_stmtClearMetaData = paramBoolean;
  }

  public synchronized int getStmtCacheSize()
  {
    return this.m_stmtCacheSize;
  }

  public synchronized boolean isStmtCacheEnabled()
  {
    if (this.m_stmtCacheSize > 0) {
      return true;
    }
    return false;
  }

  protected OracleConnection getConnectionDuringExceptionHandling()
  {
    return null;
  }

  private static enum Lifecycle
  {
    NEW, OPEN, CLOSING, CLOSED;
  }
}