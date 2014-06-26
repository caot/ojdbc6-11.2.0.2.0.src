package oracle.jdbc.pool;

import java.io.UnsupportedEncodingException;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.concurrent.atomic.AtomicInteger;
import javax.sql.ConnectionPoolDataSource;
import oracle.jdbc.driver.DatabaseError;
import oracle.jdbc.internal.OracleConnection;
import oracle.ons.ONS;
import oracle.ons.ONSException;

/** @deprecated */
public class OracleConnectionCacheManager
{
  private static OracleConnectionCacheManager cacheManagerInstance = null;

  protected Hashtable m_connCache = null;
  public static final int REFRESH_INVALID_CONNECTIONS = 4096;
  public static final int REFRESH_ALL_CONNECTIONS = 8192;
  public static final String PHYSICAL_CONNECTION_CREATED_COUNT = "PhysicalConnectionCreatedCount";
  public static final String PHYSICAL_CONNECTION_CLOSED_COUNT = "PhysicalConnectionClosedCount";
  protected static final int FAILOVER_EVENT_TYPE_SERVICE = 256;
  protected static final int FAILOVER_EVENT_TYPE_HOST = 512;
  protected static final String EVENT_DELIMITER = "{} =";
  protected OracleFailoverEventHandlerThread failoverEventHandlerThread = null;

  private static boolean isONSInitializedForRemoteSubscription = false;
  static final int ORAERROR_END_OF_FILE_ON_COM_CHANNEL = 3113;
  static final int ORAERROR_NOT_CONNECTED_TO_ORACLE = 3114;
  static final int ORAERROR_INIT_SHUTDOWN_IN_PROGRESS = 1033;
  static final int ORAERROR_ORACLE_NOT_AVAILABLE = 1034;
  static final int ORAERROR_IMMEDIATE_SHUTDOWN_IN_PROGRESS = 1089;
  static final int ORAERROR_SHUTDOWN_IN_PROGRESS_NO_CONN = 1090;
  static final int ORAERROR_NET_IO_EXCEPTION = 17002;
  protected int[] fatalErrorCodes = null;
  protected int failoverEnabledCacheCount = 0;
  protected static AtomicInteger UNNAMED_CACHE_COUNT;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  private OracleConnectionCacheManager()
  {
    this.m_connCache = new Hashtable();

    UNNAMED_CACHE_COUNT = new AtomicInteger();
  }

  /** @deprecated */
  public static synchronized OracleConnectionCacheManager getConnectionCacheManagerInstance()
    throws SQLException
  {
    try
    {
      if (cacheManagerInstance == null) {
        cacheManagerInstance = new OracleConnectionCacheManager();
      }
    }
    catch (RuntimeException localRuntimeException)
    {
    }
    return cacheManagerInstance;
  }

  /** @deprecated */
  public String createCache(OracleDataSource oracleDataSource, Properties paramProperties)
    throws SQLException
  {
    String str = null;

    if ((oracleDataSource == null) || (!oracleDataSource.getConnectionCachingEnabled()))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 137);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if (oracleDataSource.connCacheName != null)
    {
      str = oracleDataSource.connCacheName;
    }
    else
    {
      str = oracleDataSource.dataSourceName + "#0x" + Integer.toHexString(UNNAMED_CACHE_COUNT.getAndIncrement());
    }

    createCache(str, oracleDataSource, paramProperties);

    return str;
  }

  /** @deprecated */
  public void createCache(String paramString, OracleDataSource oracleDataSource, Properties paramProperties)
    throws SQLException
  {
    SQLException localSQLException1;
    if ((oracleDataSource == null) || (!oracleDataSource.getConnectionCachingEnabled()))
    {
      localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 137);
      localSQLException1.fillInStackTrace();
      throw localSQLException1;
    }

    if (paramString == null)
    {
      localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 138);
      localSQLException1.fillInStackTrace();
      throw localSQLException1;
    }

    if (this.m_connCache.containsKey(paramString))
    {
      localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 140);
      localSQLException1.fillInStackTrace();
      throw localSQLException1;
    }

    boolean bool = oracleDataSource.getFastConnectionFailoverEnabled();

    if ((bool) && (this.failoverEventHandlerThread == null))
    {
      final String onsConfigStr = oracleDataSource.getONSConfiguration();

      if ((onsConfigStr != null) && (!onsConfigStr.equals("")))
      {
        synchronized (this)
        {
          if (!isONSInitializedForRemoteSubscription)
          {
            try
            {
              AccessController.doPrivileged(new PrivilegedExceptionAction()
              {
                public Object run()
                  throws ONSException
                {
                  ONS localONS = new ONS(onsConfigStr);
                  return null;
                }

              });
            }
            catch (PrivilegedActionException localPrivilegedActionException)
            {
              SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 175, localPrivilegedActionException);
              localSQLException2.fillInStackTrace();
              throw localSQLException2;
            }

            isONSInitializedForRemoteSubscription = true;
          }
        }
      }

      this.failoverEventHandlerThread = new OracleFailoverEventHandlerThread();
    }

    Object localObject1 = new OracleImplicitConnectionCache(oracleDataSource, paramProperties);

    ((OracleImplicitConnectionCache)localObject1).cacheName = paramString;
    oracleDataSource.odsCache = ((OracleImplicitConnectionCache)localObject1);

    this.m_connCache.put(paramString, localObject1);

    if (bool)
    {
      checkAndStartThread(this.failoverEventHandlerThread);
    }
  }

  /** @deprecated */
  public void removeCache(String paramString, long paramLong)
    throws SQLException
  {
    OracleImplicitConnectionCache localOracleImplicitConnectionCache = paramString != null ? (OracleImplicitConnectionCache)this.m_connCache.remove(paramString) : null;

    if (localOracleImplicitConnectionCache != null)
    {
      localOracleImplicitConnectionCache.disableConnectionCache();

      if (paramLong > 0L)
      {
        try
        {
          Thread.currentThread(); Thread.sleep(paramLong * 1000L);
        }
        catch (InterruptedException localInterruptedException)
        {
        }

      }

      if (localOracleImplicitConnectionCache.cacheEnabledDS.getFastConnectionFailoverEnabled()) {
        cleanupFCFThreads(localOracleImplicitConnectionCache);
      }

      localOracleImplicitConnectionCache.closeConnectionCache(paramLong < 0L ? 32 : 1);

      localOracleImplicitConnectionCache = null;
    }
    else
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 141);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  /** @deprecated */
  public void reinitializeCache(String paramString, Properties paramProperties)
    throws SQLException
  {
    OracleImplicitConnectionCache cache = paramString != null ? (OracleImplicitConnectionCache)this.m_connCache.get(paramString) : null;

    if (cache != null)
    {
      disableCache(paramString);
      cache.reinitializeCacheConnections(paramProperties);
      enableCache(paramString);
    }
    else
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 141);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  /** @deprecated */
  public boolean existsCache(String paramString)
    throws SQLException
  {
    return this.m_connCache.containsKey(paramString);
  }

  /** @deprecated */
  public void enableCache(String paramString)
    throws SQLException
  {
    OracleImplicitConnectionCache cache = paramString != null ? (OracleImplicitConnectionCache)this.m_connCache.get(paramString) : null;

    if (cache != null)
    {
      cache.enableConnectionCache();
    }
    else
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 141);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  /** @deprecated */
  public void disableCache(String paramString)
    throws SQLException
  {
    OracleImplicitConnectionCache cache = paramString != null ? (OracleImplicitConnectionCache)this.m_connCache.get(paramString) : null;

    if (cache != null)
    {
      cache.disableConnectionCache();
    }
    else
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 141);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  /** @deprecated */
  public void refreshCache(String paramString, int paramInt)
    throws SQLException
  {
    OracleImplicitConnectionCache cache = paramString != null ? (OracleImplicitConnectionCache)this.m_connCache.get(paramString) : null;
    SQLException localSQLException;
    if (cache != null)
    {
      switch (paramInt)
      {
      case 4096:
      case 8192:
        cache.refreshCacheConnections(paramInt);
        break;
      default:
        localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68);
        localSQLException.fillInStackTrace();
        throw localSQLException;
      }

    }
    else
    {
      localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 141);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  /** @deprecated */
  public void purgeCache(String paramString, boolean paramBoolean)
    throws SQLException
  {
    OracleImplicitConnectionCache cache = paramString != null ? (OracleImplicitConnectionCache)this.m_connCache.get(paramString) : null;

    if (cache != null)
    {
      cache.purgeCacheConnections(paramBoolean, 1);
    }
    else
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 141);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  /** @deprecated */
  public Properties getCacheProperties(String paramString)
    throws SQLException
  {
    OracleImplicitConnectionCache cache = paramString != null ? (OracleImplicitConnectionCache)this.m_connCache.get(paramString) : null;

    if (cache != null)
    {
      return cache.getConnectionCacheProperties();
    }

    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 141);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  /** @deprecated */
  public String[] getCacheNameList()
    throws SQLException
  {
    String[] arrayOfString = (String[])this.m_connCache.keySet().toArray(new String[this.m_connCache.size()]);

    return arrayOfString;
  }

  /** @deprecated */
  public int getNumberOfAvailableConnections(String paramString)
    throws SQLException
  {
    OracleImplicitConnectionCache cache = paramString != null ? (OracleImplicitConnectionCache)this.m_connCache.get(paramString) : null;

    if (cache != null)
    {
      return cache.cacheSize;
    }

    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 141);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  /** @deprecated */
  public int getNumberOfActiveConnections(String paramString)
    throws SQLException
  {
    OracleImplicitConnectionCache cache = paramString != null ? (OracleImplicitConnectionCache)this.m_connCache.get(paramString) : null;

    if (cache != null)
    {
      return cache.getNumberOfCheckedOutConnections();
    }

    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 141);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  /** @deprecated */
  public synchronized void setConnectionPoolDataSource(String paramString, ConnectionPoolDataSource paramConnectionPoolDataSource)
    throws SQLException
  {
    OracleImplicitConnectionCache localObject = paramString != null ? (OracleImplicitConnectionCache)this.m_connCache.get(paramString) : null;
    SQLException localSQLException;
    if (localObject != null)
    {
      if (localObject.cacheSize > 0)
      {
        localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 78);
        localSQLException.fillInStackTrace();
        throw localSQLException;
      }

      ((OracleConnectionPoolDataSource)paramConnectionPoolDataSource).makeURL();
      ((OracleConnectionPoolDataSource)paramConnectionPoolDataSource).setURL(((OracleConnectionPoolDataSource)paramConnectionPoolDataSource).url);

      localObject.connectionPoolDS = ((OracleConnectionPoolDataSource)paramConnectionPoolDataSource);
    }
    else
    {
      localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 141);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  protected void verifyAndHandleEvent(int paramInt, byte[] paramArrayOfByte)
    throws SQLException
  {
    String str0 = null;
    String str1 = null;
    String str2 = null;
    String str3 = null;
    String str9 = null;

    int i = 0;
    StringTokenizer localStringTokenizer = null;
    try
    {
      localStringTokenizer = new StringTokenizer(new String(paramArrayOfByte, "UTF-8"), "{} =", true);
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
    }

    String str4 = null;
    String str5 = null;
    String str6 = null;

    while (localStringTokenizer.hasMoreTokens())
    {
      str5 = null;
      str4 = localStringTokenizer.nextToken();
      if ((str4.equals("=")) && (localStringTokenizer.hasMoreTokens()))
      {
        str5 = localStringTokenizer.nextToken();
      }
      else
      {
        str6 = str4;
      }

      if ((str6.equalsIgnoreCase("version")) && (str5 != null) && (!str5.equals("1.0")))
      {
        SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 146);
        localSQLException.fillInStackTrace();
        throw localSQLException;
      }

      if ((str6.equalsIgnoreCase("service")) && (str5 != null)) {
        str0 = str5;
      }
      if ((str6.equalsIgnoreCase("instance")) && (str5 != null) && (!str5.equals(" ")))
      {
        str1 = str5.toLowerCase().intern();
      }

      if ((str6.equalsIgnoreCase("database")) && (str5 != null)) {
        str2 = str5.toLowerCase().intern();
      }
      if ((str6.equalsIgnoreCase("host")) && (str5 != null)) {
        str3 = str5.toLowerCase().intern();
      }
      if ((str6.equalsIgnoreCase("status")) && (str5 != null)) {
        str9 = str5;
      }
      if ((str6.equalsIgnoreCase("card")) && (str5 != null))
      {
        try
        {
          i = Integer.parseInt(str5);
        }
        catch (NumberFormatException localNumberFormatException)
        {
        }
      }

    }

    invokeFailoverProcessingThreads(paramInt, str0, str1, str2, str3, str9, i);

    localStringTokenizer = null;
  }

  private void invokeFailoverProcessingThreads(int paramInt1, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, int paramInt2)
    throws SQLException
  {
    OracleImplicitConnectionCache localOracleImplicitConnectionCache = null;
    int i = 0;
    int j = 0;

    if (paramInt1 == 256) {
      i = 1;
    }
    if (paramInt1 == 512) {
      j = 1;
    }
    Iterator localIterator = this.m_connCache.values().iterator();

    while (localIterator.hasNext())
    {
      localOracleImplicitConnectionCache = (OracleImplicitConnectionCache)localIterator.next();

      if (((i != 0) && (paramString1.equalsIgnoreCase(localOracleImplicitConnectionCache.dataSourceServiceName))) || (j != 0))
      {
        OracleFailoverWorkerThread localOracleFailoverWorkerThread = new OracleFailoverWorkerThread(localOracleImplicitConnectionCache, paramInt1, paramString2, paramString3, paramString4, paramString5, paramInt2);

        checkAndStartThread(localOracleFailoverWorkerThread);

        localOracleImplicitConnectionCache.failoverWorkerThread = localOracleFailoverWorkerThread;
      }
    }
  }

  protected void checkAndStartThread(Thread paramThread)
    throws SQLException
  {
    try
    {
      if (!paramThread.isAlive())
      {
        paramThread.setDaemon(true);
        paramThread.start();
      }
    }
    catch (IllegalThreadStateException localIllegalThreadStateException)
    {
    }
  }

  protected boolean failoverEnabledCacheExists()
  {
    if (this.failoverEnabledCacheCount > 0) {
      return true;
    }
    return false;
  }

  protected void parseRuntimeLoadBalancingEvent(String paramString, byte[] paramArrayOfByte)
    throws SQLException
  {
    OracleImplicitConnectionCache localOracleImplicitConnectionCache = null;
    Enumeration localEnumeration = this.m_connCache.elements();

    while (localEnumeration.hasMoreElements())
    {
      try
      {
        localOracleImplicitConnectionCache = (OracleImplicitConnectionCache)localEnumeration.nextElement();
        if (paramString.equalsIgnoreCase(localOracleImplicitConnectionCache.dataSourceServiceName))
        {
          if (paramArrayOfByte == null)
            localOracleImplicitConnectionCache.zapRLBInfo();
          else
            retrieveServiceMetrics(localOracleImplicitConnectionCache, paramArrayOfByte);
        }
      }
      catch (Exception localException)
      {
      }
    }
  }

  private void retrieveServiceMetrics(OracleImplicitConnectionCache paramOracleImplicitConnectionCache, byte[] paramArrayOfByte)
    throws SQLException
  {
    StringTokenizer localStringTokenizer = null;
    String str1 = null;
    String str2 = null;
    int i = 0;
    int j = 0;
    int k = 0;
    try
    {
      localStringTokenizer = new StringTokenizer(new String(paramArrayOfByte, "UTF-8"), "{} =", true);
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
    }

    String str3 = null;
    String str4 = null;
    String str5 = null;

    while (localStringTokenizer.hasMoreTokens())
    {
      str4 = null;
      str3 = localStringTokenizer.nextToken();

      if ((str3.equals("=")) && (localStringTokenizer.hasMoreTokens()))
      {
        str4 = localStringTokenizer.nextToken();
      } else {
        if (str3.equals("}"))
        {
          if (k == 0)
            continue;
          paramOracleImplicitConnectionCache.updateDatabaseInstance(str2, str1, i, j);
          k = 0; continue;
        }

        if ((str3.equals("{")) || (str3.equals(" ")))
        {
          continue;
        }

        str5 = str3;
        k = 1;
      }

      if ((str5.equalsIgnoreCase("version")) && (str4 != null))
      {
        if (!str4.equals("1.0"))
        {
          SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 146);
          localSQLException.fillInStackTrace();
          throw localSQLException;
        }

      }

      if ((str5.equalsIgnoreCase("database")) && (str4 != null)) {
        str2 = str4.toLowerCase().intern();
      }
      if ((str5.equalsIgnoreCase("instance")) && (str4 != null)) {
        str1 = str4.toLowerCase().intern();
      }
      if ((str5.equalsIgnoreCase("percent")) && (str4 != null))
      {
        try
        {
          i = Integer.parseInt(str4);
          if (i == 0) i = 1;
        }
        catch (NumberFormatException localNumberFormatException)
        {
        }

      }

      if ((str5.equalsIgnoreCase("flag")) && (str4 != null))
      {
        if (str4.equalsIgnoreCase("good"))
          j = 1;
        else if (str4.equalsIgnoreCase("violating"))
          j = 3;
        else if (str4.equalsIgnoreCase("NO_DATA"))
          j = 4;
        else if (str4.equalsIgnoreCase("UNKNOWN"))
          j = 2;
        else if (str4.equalsIgnoreCase("BLOCKED")) {
          j = 5;
        }
      }
    }

    paramOracleImplicitConnectionCache.processDatabaseInstances();
  }

  protected void cleanupFCFThreads(OracleImplicitConnectionCache paramOracleImplicitConnectionCache)
    throws SQLException
  {
    cleanupFCFWorkerThread(paramOracleImplicitConnectionCache);
    paramOracleImplicitConnectionCache.cleanupRLBThreads();

    if (this.failoverEnabledCacheCount <= 0) {
      cleanupFCFEventHandlerThread();
    }

    this.failoverEnabledCacheCount -= 1;
  }

  protected void cleanupFCFWorkerThread(OracleImplicitConnectionCache paramOracleImplicitConnectionCache)
    throws SQLException
  {
    if (paramOracleImplicitConnectionCache.failoverWorkerThread != null)
    {
      try
      {
        if (paramOracleImplicitConnectionCache.failoverWorkerThread.isAlive()) {
          paramOracleImplicitConnectionCache.failoverWorkerThread.join();
        }
      }
      catch (InterruptedException localInterruptedException)
      {
      }

      paramOracleImplicitConnectionCache.failoverWorkerThread = null;
    }
  }

  protected void cleanupFCFEventHandlerThread()
    throws SQLException
  {
    if (this.failoverEventHandlerThread != null)
    {
      try
      {
        this.failoverEventHandlerThread.interrupt();
      }
      catch (Exception localException)
      {
      }

      this.failoverEventHandlerThread = null;
    }
  }

  public boolean isFatalConnectionError(SQLException paramSQLException)
  {
    boolean bool = false;
    int i = paramSQLException.getErrorCode();

    if ((i == 3113) || (i == 3114) || (i == 1033) || (i == 1034) || (i == 1089) || (i == 1090) || (i == 17002))
    {
      bool = true;
    }

    if ((!bool) && (this.fatalErrorCodes != null))
    {
      for (int j = 0; j < this.fatalErrorCodes.length; j++)
        if (i == this.fatalErrorCodes[j])
        {
          bool = true;
          break;
        }
    }
    return bool;
  }

  public synchronized void setConnectionErrorCodes(int[] paramArrayOfInt)
    throws SQLException
  {
    if (paramArrayOfInt != null)
      this.fatalErrorCodes = paramArrayOfInt;
  }

  public int[] getConnectionErrorCodes()
    throws SQLException
  {
    return this.fatalErrorCodes;
  }

  /** @deprecated */
  public Map getStatistics(String paramString)
    throws SQLException
  {
    Map localMap = null;
    OracleImplicitConnectionCache localOracleImplicitConnectionCache = null;

    if ((this.m_connCache != null) && ((localOracleImplicitConnectionCache = (OracleImplicitConnectionCache)this.m_connCache.get(paramString)) != null))
    {
      localMap = localOracleImplicitConnectionCache.getStatistics();
    }
    return localMap;
  }

  protected OracleConnection getConnectionDuringExceptionHandling()
  {
    return null;
  }
}