package oracle.jdbc.pool;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;
import javax.sql.ConnectionEvent;
import javax.sql.ConnectionEventListener;
import javax.sql.PooledConnection;
import javax.sql.StatementEventListener;
import javax.transaction.xa.XAResource;
import oracle.jdbc.driver.DatabaseError;
import oracle.jdbc.driver.OracleCloseCallback;
import oracle.jdbc.driver.OracleDriver;
import oracle.jdbc.internal.OracleConnection;

public class OraclePooledConnection
  implements PooledConnection, Serializable
{
  static final long serialVersionUID = -203725628718322873L;
  public static final String url_string = "connection_url";
  public static final String pool_auto_commit_string = "pool_auto_commit";
  public static final String object_type_map = "obj_type_map";
  public static final String transaction_isolation = "trans_isolation";
  public static final String statement_cache_size = "stmt_cache_size";
  public static final String isClearMetaData = "stmt_cache_clear_metadata";
  public static final String ImplicitStatementCachingEnabled = "ImplicitStatementCachingEnabled";
  public static final String ExplicitStatementCachingEnabled = "ExplicitStatementCachingEnabled";
  public static final String LoginTimeout = "LoginTimeout";
  public static final String connect_auto_commit_string = "connect_auto_commit";
  public static final String implicit_caching_enabled = "implicit_cache_enabled";
  public static final String explicit_caching_enabled = "explict_cache_enabled";
  public static final String connection_properties_string = "connection_properties";
  public static final String event_listener_string = "event_listener";
  public static final String sql_exception_string = "sql_exception";
  public static final String close_callback_string = "close_callback";
  public static final String private_data = "private_data";
  static final int CONNECTION_CLOSED_EVENT = 101;
  static final int CONNECTION_ERROROCCURED_EVENT = 102;
  private Hashtable eventListeners = null;
  private SQLException sqlException = null;
  protected boolean autoCommit = true;

  private ConnectionEventListener iccEventListener = null;

  protected transient OracleConnection logicalHandle = null;

  protected transient OracleConnection physicalConn = null;

  private Hashtable connectionProperty = null;

  public Properties cachedConnectionAttributes = null;
  public Properties unMatchedCachedConnAttr = null;
  public int closeOption = 0;

  private String pcKey = null;

  private OracleCloseCallback closeCallback = null;
  private Object privateData = null;

  private long lastAccessedTime = 0L;

  protected String dataSourceInstanceNameKey = null;
  protected String dataSourceHostNameKey = null;
  protected String dataSourceDbUniqNameKey = null;
  protected boolean connectionMarkedDown = false;
  protected boolean needToAbort = false;

  protected transient OracleDriver oracleDriver = new OracleDriver();
  boolean localTxnCommitOnClose = false;

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  public OraclePooledConnection()
  {
    this((Connection)null);
  }

  public OraclePooledConnection(String paramString)
    throws SQLException
  {
    Connection localConnection = this.oracleDriver.connect(paramString, new Properties());

    if (localConnection == null)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 67);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    initialize(localConnection);
  }

  public OraclePooledConnection(String paramString1, String paramString2, String paramString3)
    throws SQLException
  {
    Properties localProperties = new Properties();

    localProperties.put("user", paramString2);
    localProperties.put("password", paramString3);
    Connection localConnection = this.oracleDriver.connect(paramString1, localProperties);
    if (localConnection == null)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 67);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    initialize(localConnection);
  }

  public OraclePooledConnection(Connection paramConnection)
  {
    initialize(paramConnection);
  }

  public OraclePooledConnection(Connection paramConnection, boolean paramBoolean)
  {
    this(paramConnection);

    this.autoCommit = paramBoolean;
  }

  private void initialize(Connection paramConnection)
  {
    this.physicalConn = ((OracleConnection)paramConnection);
    this.eventListeners = new Hashtable(10);

    this.closeCallback = null;
    this.privateData = null;
    this.lastAccessedTime = 0L;
  }

  public synchronized void addConnectionEventListener(ConnectionEventListener paramConnectionEventListener)
  {
    if (this.eventListeners == null)
      this.sqlException = new SQLException("Listener Hashtable Null");
    else
      this.eventListeners.put(paramConnectionEventListener, paramConnectionEventListener);
  }

  public synchronized void close()
    throws SQLException
  {
    if (this.closeCallback != null) {
      this.closeCallback.beforeClose(this.physicalConn, this.privateData);
    }
    if (this.physicalConn != null)
    {
      try
      {
        this.physicalConn.close();
      }
      catch (SQLException localSQLException)
      {
      }

      this.physicalConn = null;
    }

    if (this.closeCallback != null) {
      this.closeCallback.afterClose(this.privateData);
    }

    this.lastAccessedTime = 0L;
    this.iccEventListener = null;
  }

  public synchronized Connection getConnection()
    throws SQLException
  {
    if (this.physicalConn == null)
    {
      this.sqlException = new SQLException("Physical Connection doesn't exist");

      callListener(102);

      SQLException localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 8);
      localSQLException1.fillInStackTrace();
      throw localSQLException1;
    }

    try
    {
      if (this.logicalHandle != null)
      {
        this.logicalHandle.closeInternal(false);
      }

      this.logicalHandle = ((OracleConnection)this.physicalConn.getLogicalConnection(this, this.autoCommit));
    }
    catch (SQLException localSQLException2)
    {
      this.sqlException = localSQLException2;

      callListener(102);

      callImplicitCacheListener(102);

      SQLException localSQLException3 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 8, "OraclePooledConnection.getConnection() - SQLException Ocurred:" + localSQLException2.getMessage());

      localSQLException3.fillInStackTrace();
      throw localSQLException3;
    }

    return this.logicalHandle;
  }

  public Connection getLogicalHandle()
    throws SQLException
  {
    return this.logicalHandle;
  }

  public Connection getPhysicalHandle()
    throws SQLException
  {
    return this.physicalConn;
  }

  public synchronized void setLastAccessedTime(long paramLong)
    throws SQLException
  {
    this.lastAccessedTime = paramLong;
  }

  public long getLastAccessedTime()
    throws SQLException
  {
    return this.lastAccessedTime;
  }

  public synchronized void registerCloseCallback(OracleCloseCallback paramOracleCloseCallback, Object paramObject)
  {
    this.closeCallback = paramOracleCloseCallback;
    this.privateData = paramObject;
  }

  public synchronized void removeConnectionEventListener(ConnectionEventListener paramConnectionEventListener)
  {
    if (this.eventListeners == null)
      this.sqlException = new SQLException("Listener Hashtable Null");
    else
      this.eventListeners.remove(paramConnectionEventListener);
  }

  public synchronized void registerImplicitCacheConnectionEventListener(ConnectionEventListener paramConnectionEventListener)
  {
    if (this.iccEventListener != null) {
      this.sqlException = new SQLException("Implicit cache listeneralready registered");
    }
    else
      this.iccEventListener = paramConnectionEventListener;
  }

  public void logicalCloseForImplicitConnectionCache()
  {
    if (this.closeOption == 4096)
    {
      callImplicitCacheListener(102);
    }
    else
    {
      callImplicitCacheListener(101);
    }
  }

  public void logicalClose()
  {
    if (this.cachedConnectionAttributes != null)
    {
      logicalCloseForImplicitConnectionCache();
    }
    else
    {
      callListener(101);
    }
  }

  private void callListener(int paramInt)
  {
    if (this.eventListeners == null) {
      return;
    }

    Enumeration localEnumeration = this.eventListeners.keys();

    ConnectionEvent localConnectionEvent = new ConnectionEvent(this, this.sqlException);

    while (localEnumeration.hasMoreElements())
    {
      ConnectionEventListener localConnectionEventListener1 = (ConnectionEventListener)localEnumeration.nextElement();

      ConnectionEventListener localConnectionEventListener2 = (ConnectionEventListener)this.eventListeners.get(localConnectionEventListener1);

      if (paramInt == 101)
        localConnectionEventListener2.connectionClosed(localConnectionEvent);
      else if (paramInt == 102)
        localConnectionEventListener2.connectionErrorOccurred(localConnectionEvent);
    }
  }

  private void callImplicitCacheListener(int paramInt)
  {
    if (this.iccEventListener == null) {
      return;
    }
    ConnectionEvent localConnectionEvent = new ConnectionEvent(this, this.sqlException);

    switch (paramInt)
    {
    case 101:
      this.iccEventListener.connectionClosed(localConnectionEvent);

      break;
    case 102:
      this.iccEventListener.connectionErrorOccurred(localConnectionEvent);
    }
  }

  /** @deprecated */
  public synchronized void setStmtCacheSize(int paramInt)
    throws SQLException
  {
    setStmtCacheSize(paramInt, false);
  }

  /** @deprecated */
  public synchronized void setStmtCacheSize(int paramInt, boolean paramBoolean)
    throws SQLException
  {
    if (paramInt < 0)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if (this.physicalConn != null)
      this.physicalConn.setStmtCacheSize(paramInt, paramBoolean);
  }

  /** @deprecated */
  public synchronized int getStmtCacheSize()
  {
    if (this.physicalConn != null) {
      return this.physicalConn.getStmtCacheSize();
    }
    return 0;
  }

  public void setStatementCacheSize(int paramInt)
    throws SQLException
  {
    if (this.physicalConn != null)
      this.physicalConn.setStatementCacheSize(paramInt);
  }

  public int getStatementCacheSize()
    throws SQLException
  {
    if (this.physicalConn != null) {
      return this.physicalConn.getStatementCacheSize();
    }
    return 0;
  }

  public void setImplicitCachingEnabled(boolean paramBoolean)
    throws SQLException
  {
    if (this.physicalConn != null)
      this.physicalConn.setImplicitCachingEnabled(paramBoolean);
  }

  public boolean getImplicitCachingEnabled()
    throws SQLException
  {
    if (this.physicalConn != null) {
      return this.physicalConn.getImplicitCachingEnabled();
    }
    return false;
  }

  public void setExplicitCachingEnabled(boolean paramBoolean)
    throws SQLException
  {
    if (this.physicalConn != null)
      this.physicalConn.setExplicitCachingEnabled(paramBoolean);
  }

  public boolean getExplicitCachingEnabled()
    throws SQLException
  {
    if (this.physicalConn != null) {
      return this.physicalConn.getExplicitCachingEnabled();
    }

    return false;
  }

  public void purgeImplicitCache()
    throws SQLException
  {
    if (this.physicalConn != null)
      this.physicalConn.purgeImplicitCache();
  }

  public void purgeExplicitCache()
    throws SQLException
  {
    if (this.physicalConn != null)
      this.physicalConn.purgeExplicitCache();
  }

  public PreparedStatement getStatementWithKey(String paramString)
    throws SQLException
  {
    if (this.physicalConn != null) {
      return this.physicalConn.getStatementWithKey(paramString);
    }

    return null;
  }

  public CallableStatement getCallWithKey(String paramString)
    throws SQLException
  {
    if (this.physicalConn != null) {
      return this.physicalConn.getCallWithKey(paramString);
    }

    return null;
  }

  public boolean isStatementCacheInitialized()
  {
    if (this.physicalConn != null) {
      return this.physicalConn.isStatementCacheInitialized();
    }

    return false;
  }

  public final void setProperties(Hashtable paramHashtable)
  {
    this.connectionProperty = paramHashtable;
  }

  public final void setUserName(String paramString1, String paramString2)
  {
    this.pcKey = generateKey(paramString1, paramString2);
  }

  static final String generateKey(String paramString1, String paramString2)
  {
    return paramString1.toUpperCase() + paramString2;
  }

  final OracleConnectionCacheEntry addToImplicitCache(HashMap paramHashMap, OracleConnectionCacheEntry paramOracleConnectionCacheEntry)
  {
    return (OracleConnectionCacheEntry)paramHashMap.put(this.pcKey, paramOracleConnectionCacheEntry);
  }

  final OracleConnectionCacheEntry removeFromImplictCache(HashMap paramHashMap)
  {
    return (OracleConnectionCacheEntry)paramHashMap.get(this.pcKey);
  }

  final boolean isSameUser(String paramString1, String paramString2)
  {
    return (paramString1 != null) && (paramString2 != null) && (this.pcKey.equalsIgnoreCase(paramString1 + paramString2));
  }

  public XAResource getXAResource()
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createUnsupportedFeatureSqlException();
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  private void writeObject(ObjectOutputStream paramObjectOutputStream)
    throws IOException
  {
    paramObjectOutputStream.defaultWriteObject();
    try
    {
      this.physicalConn.getPropertyForPooledConnection(this);

      if (this.eventListeners != null) {
        this.connectionProperty.put("event_listener", this.eventListeners);
      }
      if (this.sqlException != null) {
        this.connectionProperty.put("sql_exception", this.sqlException);
      }
      this.connectionProperty.put("pool_auto_commit", "" + this.autoCommit);

      if (this.closeCallback != null) {
        this.connectionProperty.put("close_callback", this.closeCallback);
      }
      if (this.privateData != null) {
        this.connectionProperty.put("private_data", this.privateData);
      }
      paramObjectOutputStream.writeObject(this.connectionProperty);
      this.physicalConn.close();
    }
    catch (SQLException localSQLException)
    {
    }
  }

  private void readObject(ObjectInputStream paramObjectInputStream)
    throws IOException, ClassNotFoundException, SQLException
  {
    paramObjectInputStream.defaultReadObject();

    this.connectionProperty = ((Hashtable)paramObjectInputStream.readObject());
    try
    {
      Properties localProperties = (Properties)this.connectionProperty.get("connection_properties");

      String str1 = localProperties.getProperty("connection_url");

      this.oracleDriver = new OracleDriver();

      Connection localConnection = this.oracleDriver.connect(str1, localProperties);

      initialize(localConnection);

      this.eventListeners = ((Hashtable)this.connectionProperty.get("event_listener"));

      this.sqlException = ((SQLException)this.connectionProperty.get("sql_exception"));

      this.autoCommit = ((String)this.connectionProperty.get("pool_auto_commit")).equals("true");

      this.closeCallback = ((OracleCloseCallback)this.connectionProperty.get("close_callback"));

      this.privateData = this.connectionProperty.get("private_data");

      Map localMap = (Map)this.connectionProperty.get("obj_type_map");

      if (localMap != null) {
        ((OracleConnection)localConnection).setTypeMap(localMap);
      }
      String str2 = localProperties.getProperty("trans_isolation");

      localConnection.setTransactionIsolation(Integer.parseInt(str2));

      str2 = localProperties.getProperty("stmt_cache_size");

      int i = Integer.parseInt(str2);

      if (i != -1)
      {
        setStatementCacheSize(i);

        str2 = localProperties.getProperty("implicit_cache_enabled");
        if ((str2 != null) && (str2.equalsIgnoreCase("true")))
          setImplicitCachingEnabled(true);
        else {
          setImplicitCachingEnabled(false);
        }
        str2 = localProperties.getProperty("explict_cache_enabled");
        if ((str2 != null) && (str2.equalsIgnoreCase("true")))
          setExplicitCachingEnabled(true);
        else
          setExplicitCachingEnabled(false);
      }
      this.physicalConn.setAutoCommit(((String)localProperties.get("connect_auto_commit")).equals("true"));
    }
    catch (Exception localException)
    {
    }
  }

  public void addStatementEventListener(StatementEventListener paramStatementEventListener)
  {
  }

  public void removeStatementEventListener(StatementEventListener paramStatementEventListener)
  {
  }

  protected OracleConnection getConnectionDuringExceptionHandling()
  {
    return null;
  }
}