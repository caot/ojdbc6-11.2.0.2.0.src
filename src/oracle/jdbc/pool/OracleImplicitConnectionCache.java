package oracle.jdbc.pool;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.Vector;
import javax.sql.PooledConnection;
import oracle.jdbc.driver.DatabaseError;
import oracle.jdbc.internal.OracleConnection;
import oracle.jdbc.xa.client.OracleXADataSource;

/** @deprecated */
class OracleImplicitConnectionCache
{
  protected OracleDataSource cacheEnabledDS = null;
  protected String cacheName = null;
  protected OracleConnectionPoolDataSource connectionPoolDS = null;
  protected boolean fastConnectionFailoverEnabled = false;

  protected String defaultUser = null;
  protected String defaultPassword = null;
  protected static final int DEFAULT_MIN_LIMIT = 0;
  protected static final int DEFAULT_MAX_LIMIT = 2147483647;
  protected static final int DEFAULT_INITIAL_LIMIT = 0;
  protected static final int DEFAULT_MAX_STATEMENTS_LIMIT = 0;
  protected static final int DEFAULT_INACTIVITY_TIMEOUT = 0;
  protected static final int DEFAULT_TIMETOLIVE_TIMEOUT = 0;
  protected static final int DEFAULT_ABANDONED_CONN_TIMEOUT = 0;
  protected static final int DEFAULT_CONNECTION_WAIT_TIMEOUT = 0;
  protected static final String DEFAULT_ATTRIBUTE_WEIGHT = "0";
  protected static final int DEFAULT_LOWER_THRESHOLD_LIMIT = 20;
  protected static final int DEFAULT_PROPERTY_CHECK_INTERVAL = 900;
  protected static final int CLOSE_AND_REMOVE_ALL_CONNECTIONS = 1;
  protected static final int CLOSE_AND_REMOVE_FAILOVER_CONNECTIONS = 2;
  protected static final int PROCESS_INACTIVITY_TIMEOUT = 4;
  protected static final int CLOSE_AND_REMOVE_N_CONNECTIONS = 8;
  protected static final int DISABLE_STATEMENT_CACHING = 16;
  protected static final int RESET_STATEMENT_CACHE_SIZE = 18;
  protected static final int CLOSE_AND_REMOVE_RLB_CONNECTIONS = 24;
  protected static final int ABORT_AND_CLOSE_ALL_CONNECTIONS = 32;
  public static final int REFRESH_INVALID_CONNECTIONS = 4096;
  public static final int REFRESH_ALL_CONNECTIONS = 8192;
  private static final String ATTRKEY_DELIM = "0xffff";
  protected int cacheMinLimit = 0;
  protected int cacheMaxLimit = 2147483647;
  protected int cacheInitialLimit = 0;
  protected int cacheMaxStatementsLimit = 0;
  protected Properties cacheAttributeWeights = null;
  protected int cacheInactivityTimeout = 0;
  protected int cacheTimeToLiveTimeout = 0;
  protected int cacheAbandonedConnectionTimeout = 0;
  protected int cacheLowerThresholdLimit = 20;
  protected int cachePropertyCheckInterval = 900;
  protected boolean cacheClosestConnectionMatch = false;
  protected boolean cacheValidateConnection = false;
  protected int cacheConnectionWaitTimeout = 0;
  static final String MIN_LIMIT_KEY = "MinLimit";
  static final String MAX_LIMIT_KEY = "MaxLimit";
  static final String INITIAL_LIMIT_KEY = "InitialLimit";
  static final String MAX_STATEMENTS_LIMIT_KEY = "MaxStatementsLimit";
  static final String ATTRIBUTE_WEIGHTS_KEY = "AttributeWeights";
  static final String INACTIVITY_TIMEOUT_KEY = "InactivityTimeout";
  static final String TIME_TO_LIVE_TIMEOUT_KEY = "TimeToLiveTimeout";
  static final String ABANDONED_CONNECTION_TIMEOUT_KEY = "AbandonedConnectionTimeout";
  static final String LOWER_THRESHOLD_LIMIT_KEY = "LowerThresholdLimit";
  static final String PROPERTY_CHECK_INTERVAL_KEY = "PropertyCheckInterval";
  static final String VALIDATE_CONNECTION_KEY = "ValidateConnection";
  static final String CLOSEST_CONNECTION_MATCH_KEY = "ClosestConnectionMatch";
  static final String CONNECTION_WAIT_TIMEOUT_KEY = "ConnectionWaitTimeout";
  static final String LOCAL_TXN_COMMIT_ON_CLOSE = "LocalTransactionCommitOnClose";
  static final int INSTANCE_GOOD = 1;
  static final int INSTANCE_UNKNOWN = 2;
  static final int INSTANCE_VIOLATING = 3;
  static final int INSTANCE_NO_DATA = 4;
  static final int INSTANCE_BLOCKED = 5;
  static final int RLB_NUMBER_OF_HITS_PER_INSTANCE = 1000;
  int dbInstancePercentTotal = 0;
  boolean useGoodGroup = false;
  Vector instancesToRetireQueue = null;
  OracleDatabaseInstance instanceToRetire = null;
  int retireConnectionsCount = 0;
  int countTotal = 0;

  protected OracleConnectionCacheManager cacheManager = null;
  protected boolean disableConnectionRequest = false;
  protected OracleImplicitConnectionCacheThread timeoutThread = null;

  protected OracleRuntimeLoadBalancingEventHandlerThread runtimeLoadBalancingThread = null;

  protected OracleGravitateConnectionCacheThread gravitateCacheThread = null;
  protected int connectionsToRemove = 0;

  private HashMap userMap = null;
  Vector checkedOutConnectionList = null;

  LinkedList databaseInstancesList = null;

  int cacheSize = 0;
  protected static final String EVENT_DELIMITER = " ";
  protected boolean isEntireServiceDownProcessed = false;
  protected int defaultUserPreFailureSize = 0;
  protected String dataSourceServiceName = null;
  protected OracleFailoverWorkerThread failoverWorkerThread = null;
  protected Random rand = null;
  protected int downEventCount = 0;
  protected int upEventCount = 0;
  protected int pendingCreationRequests = 0;

  protected int connectionClosedCount = 0;
  protected int connectionCreatedCount = 0;
  boolean cacheLocalTxnCommitOnClose = false;

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  OracleImplicitConnectionCache(OracleDataSource paramOracleDataSource, Properties paramProperties)
    throws SQLException
  {
    this.cacheEnabledDS = paramOracleDataSource;

    initializeConnectionCache();
    setConnectionCacheProperties(paramProperties);

    defaultUserPrePopulateCache(this.cacheInitialLimit);
  }

  private void defaultUserPrePopulateCache(int paramInt)
    throws SQLException
  {
    if (paramInt > 0)
    {
      String str1 = this.defaultUser;
      String str2 = this.defaultPassword;

      validateUser(str1, str2);

      OraclePooledConnection localOraclePooledConnection = null;

      for (int i = 0; i < paramInt; i++)
      {
        localOraclePooledConnection = makeOneConnection(str1, str2);
        synchronized (this)
        {
          if (localOraclePooledConnection != null)
          {
            this.cacheSize -= 1;
            storeCacheConnection(null, localOraclePooledConnection);
          }
        }
      }
    }
  }

  protected void initializeConnectionCache()
    throws SQLException
  {
    this.userMap = new HashMap();
    this.checkedOutConnectionList = new Vector();

    if (this.cacheManager == null) {
      this.cacheManager = OracleConnectionCacheManager.getConnectionCacheManagerInstance();
    }

    if ((this.cacheEnabledDS.user != null) && (!this.cacheEnabledDS.user.startsWith("\"")))
    {
      this.defaultUser = this.cacheEnabledDS.user.toLowerCase();
    }
    else this.defaultUser = this.cacheEnabledDS.user;

    this.defaultPassword = this.cacheEnabledDS.password;
    if (this.connectionPoolDS == null)
    {
      if ((this.cacheEnabledDS instanceof OracleXADataSource))
      {
        this.connectionPoolDS = new OracleXADataSource();
      }
      else
      {
        this.connectionPoolDS = new OracleConnectionPoolDataSource();
      }

      this.cacheEnabledDS.copy(this.connectionPoolDS);
    }

    if ((this.fastConnectionFailoverEnabled = this.cacheEnabledDS.getFastConnectionFailoverEnabled()))
    {
      this.rand = new Random(0L);
      this.instancesToRetireQueue = new Vector();
      this.cacheManager.failoverEnabledCacheCount += 1;
    }
  }

  private void validateUser(String paramString1, String paramString2)
    throws SQLException
  {
    if ((paramString1 == null) || (paramString2 == null))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 79);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  protected Connection getConnection(String paramString1, String paramString2, Properties paramProperties)
    throws SQLException
  {
    OraclePooledConnection localOraclePooledConnection = null;
    Connection localConnection = null;
    try
    {
      if (this.disableConnectionRequest)
      {
        SQLException localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 142);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      validateUser(paramString1, paramString2);

      if (!paramString1.startsWith("\"")) {
        paramString1 = paramString1.toLowerCase();
      }

      if (getNumberOfCheckedOutConnections() < this.cacheMaxLimit) {
        localOraclePooledConnection = getCacheConnection(paramString1, paramString2, paramProperties);
      }

      if (localOraclePooledConnection == null)
      {
        processConnectionCacheCallback();

        if (this.cacheSize > 0) {
          localOraclePooledConnection = getCacheConnection(paramString1, paramString2, paramProperties);
        }

        if ((localOraclePooledConnection == null) && (this.cacheConnectionWaitTimeout > 0))
        {
          long l1 = this.cacheConnectionWaitTimeout * 1000L;
          long l2 = System.currentTimeMillis();
          long l3 = 0L;
          do
          {
            processConnectionWaitTimeout(l1);

            if (this.cacheSize > 0) {
              localOraclePooledConnection = getCacheConnection(paramString1, paramString2, paramProperties);
            }
            l3 = System.currentTimeMillis();
            l1 -= System.currentTimeMillis() - l2;
            l2 = l3;
          }

          while ((localOraclePooledConnection == null) && (l1 > 0L));
        }

      }

      if ((localOraclePooledConnection != null) && (localOraclePooledConnection.physicalConn != null))
      {
        localConnection = localOraclePooledConnection.getConnection();

        if (localConnection != null)
        {
          if ((this.cacheValidateConnection) && (testDatabaseConnection((OracleConnection)localConnection) != 0))
          {
            ((OracleConnection)localConnection).close(4096);

            SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 143);
            localSQLException2.fillInStackTrace();
            throw localSQLException2;
          }

          if (this.cacheAbandonedConnectionTimeout > 0) {
            ((OracleConnection)localConnection).setAbandonedTimeoutEnabled(true);
          }

          if (this.cacheTimeToLiveTimeout > 0) {
            ((OracleConnection)localConnection).setStartTime(System.currentTimeMillis());
          }
          synchronized (this)
          {
            this.cacheSize -= 1;
            this.checkedOutConnectionList.addElement(localOraclePooledConnection);
          }
        }
      }
    }
    catch (SQLException localSQLException3) {
      synchronized (this)
      {
        if (localOraclePooledConnection != null)
        {
          this.cacheSize -= 1;
          abortConnection(localOraclePooledConnection);
        }
      }
      throw localSQLException3;
    }

    return localConnection;
  }

  private OraclePooledConnection getCacheConnection(String paramString1, String paramString2, Properties paramProperties)
    throws SQLException
  {
    OraclePooledConnection localOraclePooledConnection = retrieveCacheConnection(paramString1, paramString2, paramProperties);

    if (localOraclePooledConnection == null)
    {
      localOraclePooledConnection = makeOneConnection(paramString1, paramString2);

      if ((localOraclePooledConnection != null) && (paramProperties != null) && (!paramProperties.isEmpty())) {
        setUnMatchedAttributes(paramProperties, localOraclePooledConnection);
      }

    }

    return localOraclePooledConnection;
  }

  OraclePooledConnection makeOneConnection(String paramString1, String paramString2)
    throws SQLException
  {
    OraclePooledConnection localOraclePooledConnection = null;
    int i = 0;
    synchronized (this)
    {
      if (getTotalCachedConnections() + this.pendingCreationRequests < this.cacheMaxLimit)
      {
        this.pendingCreationRequests += 1;
        i = 1;
      }
    }

    if (i != 0)
    {
      try
      {
        localOraclePooledConnection = makeCacheConnection(paramString1, paramString2);
      }
      finally
      {
        synchronized (this)
        {
          if (localOraclePooledConnection != null)
            this.connectionCreatedCount += 1;
          this.pendingCreationRequests -= 1;
        }
      }
    }

    return localOraclePooledConnection;
  }

  protected int getTotalCachedConnections()
  {
    return this.cacheSize + getNumberOfCheckedOutConnections();
  }

  protected int getNumberOfCheckedOutConnections()
  {
    return this.checkedOutConnectionList.size();
  }

  private synchronized OraclePooledConnection retrieveCacheConnection(String paramString1, String paramString2, Properties paramProperties)
    throws SQLException
  {
    OraclePooledConnection localOraclePooledConnection = null;

    OracleConnectionCacheEntry localOracleConnectionCacheEntry = (OracleConnectionCacheEntry)this.userMap.get(OraclePooledConnection.generateKey(paramString1, paramString2));

    if (localOracleConnectionCacheEntry != null)
    {
      if ((paramProperties == null) || ((paramProperties != null) && (paramProperties.isEmpty())))
      {
        if (localOracleConnectionCacheEntry.userConnList != null)
          localOraclePooledConnection = retrieveFromConnectionList(localOracleConnectionCacheEntry.userConnList);
      }
      else if (localOracleConnectionCacheEntry.attrConnMap != null)
      {
        String str = buildAttrKey(paramProperties);
        Vector localVector = (Vector)localOracleConnectionCacheEntry.attrConnMap.get(str);

        if (localVector != null)
        {
          localOraclePooledConnection = retrieveFromConnectionList(localVector);
        }

        if ((localOraclePooledConnection == null) && (this.cacheClosestConnectionMatch)) {
          localOraclePooledConnection = retrieveClosestConnectionMatch(localOracleConnectionCacheEntry.attrConnMap, paramProperties);
        }

        if ((localOraclePooledConnection == null) && (localOracleConnectionCacheEntry.userConnList != null)) {
          localOraclePooledConnection = retrieveFromConnectionList(localOracleConnectionCacheEntry.userConnList);
        }
      }
    }

    if (localOraclePooledConnection != null)
    {
      if ((paramProperties != null) && (!paramProperties.isEmpty())) {
        setUnMatchedAttributes(paramProperties, localOraclePooledConnection);
      }

    }

    return localOraclePooledConnection;
  }

  private OraclePooledConnection retrieveClosestConnectionMatch(HashMap paramHashMap, Properties paramProperties)
    throws SQLException
  {
    OraclePooledConnection localOraclePooledConnection1 = null;
    OraclePooledConnection localOraclePooledConnection2 = null;
    Vector localObject = null;

    int i = paramProperties.size();
    int j = 0;
    int k = 0;
    int m = 0;
    int n = 0;
    int i1 = 0;

    if (this.cacheAttributeWeights != null) {
      j = getAttributesWeightCount(paramProperties, null);
    }
    if ((paramHashMap != null) && (!paramHashMap.isEmpty()))
    {
      Iterator localIterator = paramHashMap.entrySet().iterator();

      while (localIterator.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)localIterator.next();

        Vector localVector = (Vector)localEntry.getValue();
        Object[] arrayOfObject = localVector.toArray();
        int i2 = localVector.size();

        for (int i3 = 0; i3 < i2; i3++)
        {
          localOraclePooledConnection1 = (OraclePooledConnection)arrayOfObject[i3];

          if ((localOraclePooledConnection1.cachedConnectionAttributes != null) && (!localOraclePooledConnection1.cachedConnectionAttributes.isEmpty()) && (localOraclePooledConnection1.cachedConnectionAttributes.size() <= i))
          {
            if (j > 0)
            {
              m = getAttributesWeightCount(paramProperties, localOraclePooledConnection1.cachedConnectionAttributes);

              if (m > k)
              {
                localOraclePooledConnection2 = localOraclePooledConnection1;
                k = m;
                localObject = localVector;
              }
            }
            else
            {
              i1 = getAttributesMatchCount(paramProperties, localOraclePooledConnection1.cachedConnectionAttributes);

              if (i1 > n)
              {
                localOraclePooledConnection2 = localOraclePooledConnection1;
                n = i1;
                localObject = localVector;
              }
            }
          }
        }
      }
    }

    if (localObject != null) {
      localObject.remove(localOraclePooledConnection2);
    }
    return localOraclePooledConnection2;
  }

  private int getAttributesMatchCount(Properties paramProperties1, Properties paramProperties2)
    throws SQLException
  {
    int i = 0;
    Map.Entry localEntry = null;
    Object localObject1 = null;
    Object localObject2 = null;

    Iterator localIterator = paramProperties1.entrySet().iterator();

    while (localIterator.hasNext())
    {
      localEntry = (Map.Entry)localIterator.next();
      localObject1 = localEntry.getKey();
      localObject2 = localEntry.getValue();

      if ((paramProperties2.containsKey(localObject1)) && (localObject2.equals(paramProperties2.get(localObject1))))
      {
        i++;
      }
    }
    return i;
  }

  private int getAttributesWeightCount(Properties paramProperties1, Properties paramProperties2)
    throws SQLException
  {
    Map.Entry localEntry = null;
    Object localObject1 = null;
    Object localObject2 = null;
    int i = 0;

    Iterator localIterator = paramProperties1.entrySet().iterator();

    while (localIterator.hasNext())
    {
      localEntry = (Map.Entry)localIterator.next();
      localObject1 = localEntry.getKey();
      localObject2 = localEntry.getValue();

      if (paramProperties2 == null)
      {
        if (this.cacheAttributeWeights.containsKey(localObject1))
        {
          i += Integer.parseInt((String)this.cacheAttributeWeights.get(localObject1));
        }

      }
      else if ((paramProperties2.containsKey(localObject1)) && (localObject2.equals(paramProperties2.get(localObject1))))
      {
        if (this.cacheAttributeWeights.containsKey(localObject1))
        {
          i += Integer.parseInt((String)this.cacheAttributeWeights.get(localObject1));
        }
        else
        {
          i++;
        }
      }
    }
    return i;
  }

  private void setUnMatchedAttributes(Properties paramProperties, OraclePooledConnection paramOraclePooledConnection)
    throws SQLException
  {
    if (paramOraclePooledConnection.unMatchedCachedConnAttr == null)
      paramOraclePooledConnection.unMatchedCachedConnAttr = new Properties();
    else {
      paramOraclePooledConnection.unMatchedCachedConnAttr.clear();
    }
    if (!this.cacheClosestConnectionMatch)
    {
      paramOraclePooledConnection.unMatchedCachedConnAttr.putAll(paramProperties);
    }
    else
    {
      Properties localProperties = paramOraclePooledConnection.cachedConnectionAttributes;
      Map.Entry localEntry = null;
      Object localObject1 = null;
      Object localObject2 = null;

      Iterator localIterator = paramProperties.entrySet().iterator();

      while (localIterator.hasNext())
      {
        localEntry = (Map.Entry)localIterator.next();
        localObject1 = localEntry.getKey();
        localObject2 = localEntry.getValue();

        if ((!localProperties.containsKey(localObject1)) && (!localObject2.equals(localProperties.get(localObject1))))
        {
          paramOraclePooledConnection.unMatchedCachedConnAttr.put(localObject1, localObject2);
        }
      }
    }
  }

  private OraclePooledConnection retrieveFromConnectionList(Vector vector)
    throws SQLException
  {
    if (vector.isEmpty()) {
      return null;
    }
    OraclePooledConnection localObject1 = null;
    if (this.fastConnectionFailoverEnabled)
    {
      int i;
      OracleDatabaseInstance localObject2;
      int localObject3;
      if ((this.useGoodGroup) && (this.databaseInstancesList != null) && (this.databaseInstancesList.size() > 0))
      {
        label234: synchronized (this.databaseInstancesList) {
          i = this.databaseInstancesList.size();
          localObject2 = null;
          localObject3 = 0;

          boolean[] arrayOfBoolean = new boolean[i];
          int j = this.dbInstancePercentTotal;

          for (int k = 0; k < i; k++) {
            int localObject4 = 0;

            if (j <= 1)
              localObject3 = 0;
            else {
              localObject3 = this.rand.nextInt(j - 1);
            }
            for (int m = 0; m < i; m++)
            {
              localObject2 = (OracleDatabaseInstance)this.databaseInstancesList.get(m);

              if (arrayOfBoolean[m] == false && (localObject2.flag <= 3))
              {
                localObject4 += localObject2.percent;

                if (localObject3 <= localObject4)
                {
                  if (k == 0) localObject2.attemptedConnRequestCount += 1;

                  if ((localObject1 = selectConnectionFromList(vector, localObject2)) != null)
                  {
                    break label234;
                  }

                  j -= localObject2.percent;
                  arrayOfBoolean[m] = true;
                  break;
                }

              }

            }

          }

        }

      }
      else
      {
        int s = vector.size();
        i = this.rand.nextInt(s);
        OraclePooledConnection oraclepooledconnection2 = null;

        for (localObject3 = 0; localObject3 < s; localObject3++)
        {
          oraclepooledconnection2 = (OraclePooledConnection)vector.get((i++ + s) % s);

          if (!oraclepooledconnection2.connectionMarkedDown)
          {
            localObject1 = oraclepooledconnection2;
            vector.remove(localObject1);
            break;
          }
        }
      }
    }
    else {
      localObject1 = (OraclePooledConnection)vector.remove(0);
    }
    return localObject1;
  }

  private OraclePooledConnection selectConnectionFromList(Vector vector, OracleDatabaseInstance paramOracleDatabaseInstance)
  {
    OraclePooledConnection localObject = null;
    OraclePooledConnection localOraclePooledConnection = null;

    int i = vector.size();
    for (int j = 0; j < i; j++)
    {
      localOraclePooledConnection = (OraclePooledConnection)vector.get(j);

      if ((!localOraclePooledConnection.connectionMarkedDown) && (localOraclePooledConnection.dataSourceDbUniqNameKey == paramOracleDatabaseInstance.databaseUniqName) && (localOraclePooledConnection.dataSourceInstanceNameKey == paramOracleDatabaseInstance.instanceName))
      {
        localObject = localOraclePooledConnection;
        vector.remove(localObject);
        break;
      }

    }

    return localObject;
  }

  private void removeCacheConnection(OraclePooledConnection paramOraclePooledConnection)
    throws SQLException
  {
    boolean bool = false;

    OracleConnectionCacheEntry localOracleConnectionCacheEntry = paramOraclePooledConnection.removeFromImplictCache(this.userMap);

    if (localOracleConnectionCacheEntry != null)
    {
      Properties localProperties = paramOraclePooledConnection.cachedConnectionAttributes;

      if ((localProperties == null) || ((localProperties != null) && (localProperties.isEmpty())))
      {
        if (localOracleConnectionCacheEntry.userConnList != null)
          bool = localOracleConnectionCacheEntry.userConnList.removeElement(paramOraclePooledConnection);
      }
      else if (localOracleConnectionCacheEntry.attrConnMap != null)
      {
        String str = buildAttrKey(localProperties);

        Vector localVector = (Vector)localOracleConnectionCacheEntry.attrConnMap.get(str);

        if (localVector != null)
        {
          if (paramOraclePooledConnection.unMatchedCachedConnAttr != null)
          {
            paramOraclePooledConnection.unMatchedCachedConnAttr.clear();
            paramOraclePooledConnection.unMatchedCachedConnAttr = null;
          }

          if (paramOraclePooledConnection.cachedConnectionAttributes != null)
          {
            paramOraclePooledConnection.cachedConnectionAttributes.clear();
            paramOraclePooledConnection.cachedConnectionAttributes = null;
          }

          localProperties = null;
          bool = localVector.removeElement(paramOraclePooledConnection);
        }
      }

    }

    if (bool)
    {
      this.cacheSize -= 1;
    }
  }

  protected void doForEveryCachedConnection(int paramInt)
    throws SQLException
  {
    int i = 0;

    synchronized (this)
    {
      if ((this.userMap != null) && (!this.userMap.isEmpty()))
      {
        Iterator localIterator = this.userMap.entrySet().iterator();

        while (localIterator.hasNext())
        {
          Map.Entry localEntry = (Map.Entry)localIterator.next();
          OracleConnectionCacheEntry localOracleConnectionCacheEntry = (OracleConnectionCacheEntry)localEntry.getValue();
          OraclePooledConnection localOraclePooledConnection;
          if ((localOracleConnectionCacheEntry.userConnList != null) && (!localOracleConnectionCacheEntry.userConnList.isEmpty()))
          {
            Vector localObject1 = localOracleConnectionCacheEntry.userConnList;
            Object[] localObject2 = localObject1.toArray();

            for (int j = 0; j < localObject2.length; j++)
            {
              localOraclePooledConnection = (OraclePooledConnection)localObject2[j];

              if ((localOraclePooledConnection != null) && (performPooledConnectionTask(localOraclePooledConnection, paramInt))) {
                i++;
              }
            }
          }
          if ((localOracleConnectionCacheEntry.attrConnMap != null) && (!localOracleConnectionCacheEntry.attrConnMap.isEmpty()))
          {
            Iterator localObject1 = localOracleConnectionCacheEntry.attrConnMap.entrySet().iterator();

            while (localObject1.hasNext())
            {
              Map.Entry localObject2 = (Map.Entry)((Iterator)localObject1).next();
              Vector localVector = (Vector)((Map.Entry)localObject2).getValue();
              Object[] arrayOfObject = localVector.toArray();

              for (int k = 0; k < arrayOfObject.length; k++)
              {
                localOraclePooledConnection = (OraclePooledConnection)arrayOfObject[k];

                if ((localOraclePooledConnection != null) && (performPooledConnectionTask(localOraclePooledConnection, paramInt))) {
                  i++;
                }
              }
            }
            if ((paramInt == 1) || (paramInt == 32))
            {
              localOracleConnectionCacheEntry.attrConnMap.clear();
            }
          }
        }

        if ((paramInt == 1) || (paramInt == 32))
        {
          this.userMap.clear();

          this.cacheSize = 0;
        }

      }

    }

    if (i > 0)
    {
      defaultUserPrePopulateCache(i);
    }
  }

  private boolean performPooledConnectionTask(OraclePooledConnection paramOraclePooledConnection, int paramInt)
    throws SQLException
  {
    boolean bool = false;

    switch (paramInt)
    {
    case 2:
      if (paramOraclePooledConnection.connectionMarkedDown)
      {
        paramOraclePooledConnection.needToAbort = true;
        closeAndRemovePooledConnection(paramOraclePooledConnection); } break;
    case 8:
      if (this.connectionsToRemove > 0)
      {
        closeAndRemovePooledConnection(paramOraclePooledConnection);

        this.connectionsToRemove -= 1; } break;
    case 24:
      if (this.retireConnectionsCount > 0)
      {
        if ((this.instanceToRetire.databaseUniqName == paramOraclePooledConnection.dataSourceDbUniqNameKey) && (this.instanceToRetire.instanceName == paramOraclePooledConnection.dataSourceInstanceNameKey))
        {
          closeAndRemovePooledConnection(paramOraclePooledConnection);
          this.retireConnectionsCount -= 1;

          if (getTotalCachedConnections() < this.cacheMinLimit)
            bool = true;  }  } break;
    case 4096:
      Connection localConnection = paramOraclePooledConnection.getLogicalHandle();

      if ((localConnection != null) || ((localConnection = paramOraclePooledConnection.getPhysicalHandle()) != null))
      {
        if (testDatabaseConnection((OracleConnection)localConnection) != 0)
        {
          closeAndRemovePooledConnection(paramOraclePooledConnection);

          bool = true; }  } break;
    case 8192:
      closeAndRemovePooledConnection(paramOraclePooledConnection);

      bool = true;

      break;
    case 1:
      closeAndRemovePooledConnection(paramOraclePooledConnection);

      break;
    case 4:
      processInactivityTimeout(paramOraclePooledConnection);

      break;
    case 16:
      setStatementCaching(paramOraclePooledConnection, this.cacheMaxStatementsLimit, false);

      break;
    case 18:
      setStatementCaching(paramOraclePooledConnection, this.cacheMaxStatementsLimit, true);

      break;
    case 32:
      abortConnection(paramOraclePooledConnection);
      closeAndRemovePooledConnection(paramOraclePooledConnection);
      break;
    }

    return bool;
  }

  protected synchronized void doForEveryCheckedOutConnection(int paramInt)
    throws SQLException
  {
    int i = this.checkedOutConnectionList.size();
    int j;
    OraclePooledConnection localOraclePooledConnection;
    switch (paramInt)
    {
    case 1:
      for (j = 0; j < i; j++)
      {
        closeCheckedOutConnection((OraclePooledConnection)this.checkedOutConnectionList.get(j), false);
      }

      this.checkedOutConnectionList.removeAllElements();
      break;
    case 24:
      for (j = 0; (j < i) && (this.retireConnectionsCount > 0); j++)
      {
        localOraclePooledConnection = (OraclePooledConnection)this.checkedOutConnectionList.get(j);
        if ((this.instanceToRetire.databaseUniqName == localOraclePooledConnection.dataSourceDbUniqNameKey) && (this.instanceToRetire.instanceName == localOraclePooledConnection.dataSourceInstanceNameKey))
        {
          localOraclePooledConnection.closeOption = 4096;

          this.retireConnectionsCount -= 2;
        }
      }
      break;
    case 32:
      for (j = 0; j < i; j++)
      {
        localOraclePooledConnection = null;
        abortConnection(localOraclePooledConnection = (OraclePooledConnection)this.checkedOutConnectionList.get(j));
        closeCheckedOutConnection(localOraclePooledConnection, false);
      }

      this.checkedOutConnectionList.removeAllElements();
    }
  }

  protected void closeCheckedOutConnection(OraclePooledConnection paramOraclePooledConnection, boolean paramBoolean)
    throws SQLException
  {
    if (paramOraclePooledConnection != null)
    {
      OracleConnection localOracleConnection = (OracleConnection)paramOraclePooledConnection.getLogicalHandle();
      try
      {
        if ((!localOracleConnection.getAutoCommit()) && (!paramOraclePooledConnection.needToAbort)) {
          localOracleConnection.rollback();
        }
      }
      catch (SQLException localSQLException1)
      {
      }

      if (paramBoolean)
      {
        boolean bool = paramOraclePooledConnection.localTxnCommitOnClose;
        try
        {
          paramOraclePooledConnection.localTxnCommitOnClose = false;
          localOracleConnection.cleanupAndClose(true);

          if (paramOraclePooledConnection.localTxnCommitOnClose != bool)
            paramOraclePooledConnection.localTxnCommitOnClose = bool;
        }
        catch (SQLException localSQLException2)
        {
          if (paramOraclePooledConnection.localTxnCommitOnClose != bool)
            paramOraclePooledConnection.localTxnCommitOnClose = bool;
        }
        finally
        {
          if (paramOraclePooledConnection.localTxnCommitOnClose != bool)
            paramOraclePooledConnection.localTxnCommitOnClose = bool;
        }
      }
      else
      {
        actualPooledConnectionClose(paramOraclePooledConnection);
      }
    }
  }

  private synchronized void storeCacheConnection(Properties paramProperties, OraclePooledConnection paramOraclePooledConnection)
    throws SQLException
  {
    boolean bool = false;

    if ((paramOraclePooledConnection == null) || (paramOraclePooledConnection.physicalConn == null))
    {
      return;
    }

    if (this.cacheInactivityTimeout > 0)
    {
      paramOraclePooledConnection.setLastAccessedTime(System.currentTimeMillis());
    }

    if (paramOraclePooledConnection.unMatchedCachedConnAttr != null)
    {
      paramOraclePooledConnection.unMatchedCachedConnAttr.clear();
      paramOraclePooledConnection.unMatchedCachedConnAttr = null;
    }

    OracleConnectionCacheEntry localOracleConnectionCacheEntry = paramOraclePooledConnection.removeFromImplictCache(this.userMap);
    Object localObject1;
    Object localObject2;
    if (localOracleConnectionCacheEntry != null)
    {
      if ((paramProperties == null) || ((paramProperties != null) && (paramProperties.isEmpty())))
      {
        if (localOracleConnectionCacheEntry.userConnList == null) {
          localOracleConnectionCacheEntry.userConnList = new Vector();
        }
        bool = localOracleConnectionCacheEntry.userConnList.add(paramOraclePooledConnection);
      }
      else
      {
        paramOraclePooledConnection.cachedConnectionAttributes = paramProperties;

        if (localOracleConnectionCacheEntry.attrConnMap == null) {
          localOracleConnectionCacheEntry.attrConnMap = new HashMap();
        }
        localObject1 = buildAttrKey(paramProperties);
        localObject2 = (Vector)localOracleConnectionCacheEntry.attrConnMap.get(localObject1);

        if (localObject2 != null)
        {
          bool = ((Vector)localObject2).add(paramOraclePooledConnection);
        }
        else
        {
          localObject2 = new Vector();

          bool = ((Vector)localObject2).add(paramOraclePooledConnection);
          localOracleConnectionCacheEntry.attrConnMap.put(localObject1, localObject2);
        }
      }
    }
    else
    {
      localOracleConnectionCacheEntry = new OracleConnectionCacheEntry();

      paramOraclePooledConnection.addToImplicitCache(this.userMap, localOracleConnectionCacheEntry);

      if ((paramProperties == null) || ((paramProperties != null) && (paramProperties.isEmpty())))
      {
        localObject1 = new Vector();

        bool = ((Vector)localObject1).add(paramOraclePooledConnection);

        localOracleConnectionCacheEntry.userConnList = ((Vector)localObject1);
      }
      else
      {
        localObject1 = buildAttrKey(paramProperties);

        paramOraclePooledConnection.cachedConnectionAttributes = paramProperties;

        localObject2 = new HashMap();
        Vector localVector = new Vector();

        bool = localVector.add(paramOraclePooledConnection);
        ((HashMap)localObject2).put(localObject1, localVector);

        localOracleConnectionCacheEntry.attrConnMap = ((HashMap)localObject2);
      }

    }

    if (bool) {
      this.cacheSize += 1;
    }

    if (this.cacheConnectionWaitTimeout > 0)
    {
      notifyAll();
    }
  }

  private String buildAttrKey(Properties paramProperties)
    throws SQLException
  {
    int i = paramProperties.keySet().size();
    Object[] arrayOfObject = paramProperties.keySet().toArray();
    int j = 1;
    StringBuffer localStringBuffer = new StringBuffer();

    while (j != 0)
    {
      j = 0;

      for (int k = 0; k < i - 1; k++)
      {
        if (((String)arrayOfObject[k]).compareTo((String)arrayOfObject[(k + 1)]) > 0)
        {
          j = 1;

          Object localObject = arrayOfObject[k];

          arrayOfObject[k] = arrayOfObject[(k + 1)];
          arrayOfObject[(k + 1)] = localObject;
        }
      }

    }

    for (int k = 0; k < i; k++) {
      localStringBuffer.append(arrayOfObject[k] + "0xffff" + paramProperties.get(arrayOfObject[k]));
    }
    return localStringBuffer.toString();
  }

  protected OraclePooledConnection makeCacheConnection(String paramString1, String paramString2)
    throws SQLException
  {
    OraclePooledConnection localOraclePooledConnection = (OraclePooledConnection)this.connectionPoolDS.getPooledConnection(paramString1, paramString2);

    if (localOraclePooledConnection != null)
    {
      if (this.cacheMaxStatementsLimit > 0) {
        setStatementCaching(localOraclePooledConnection, this.cacheMaxStatementsLimit, true);
      }

      localOraclePooledConnection.registerImplicitCacheConnectionEventListener(new OracleConnectionCacheEventListener(this));

      localOraclePooledConnection.cachedConnectionAttributes = new Properties();

      if (this.fastConnectionFailoverEnabled)
      {
        initFailoverParameters(localOraclePooledConnection);
      }

      synchronized (this)
      {
        this.cacheSize += 1;

        if ((this.fastConnectionFailoverEnabled) && (this.runtimeLoadBalancingThread == null))
        {
          this.runtimeLoadBalancingThread = new OracleRuntimeLoadBalancingEventHandlerThread(this.dataSourceServiceName);

          this.cacheManager.checkAndStartThread(this.runtimeLoadBalancingThread);
        }

      }

      localOraclePooledConnection.localTxnCommitOnClose = this.cacheLocalTxnCommitOnClose;
    }
    return localOraclePooledConnection;
  }

  private void setStatementCaching(OraclePooledConnection paramOraclePooledConnection, int paramInt, boolean paramBoolean)
    throws SQLException
  {
    if (paramInt > 0) {
      paramOraclePooledConnection.setStatementCacheSize(paramInt);
    }
    paramOraclePooledConnection.setImplicitCachingEnabled(paramBoolean);
    paramOraclePooledConnection.setExplicitCachingEnabled(paramBoolean);
  }

  protected synchronized void reusePooledConnection(PooledConnection paramPooledConnection)
    throws SQLException
  {
    OraclePooledConnection localOraclePooledConnection = (OraclePooledConnection)paramPooledConnection;
    if ((localOraclePooledConnection != null) && (localOraclePooledConnection.physicalConn != null))
    {
      if (localOraclePooledConnection.localTxnCommitOnClose)
        localOraclePooledConnection.physicalConn.commit();
      storeCacheConnection(localOraclePooledConnection.cachedConnectionAttributes, localOraclePooledConnection);

      this.checkedOutConnectionList.removeElement(localOraclePooledConnection);

      localOraclePooledConnection.logicalHandle = null;
    }
  }

  protected void closePooledConnection(PooledConnection paramPooledConnection)
    throws SQLException
  {
    if (paramPooledConnection != null)
    {
      actualPooledConnectionClose((OraclePooledConnection)paramPooledConnection);

      if (((OraclePooledConnection)paramPooledConnection).closeOption == 4096) {
        this.checkedOutConnectionList.removeElement(paramPooledConnection);
      }
      paramPooledConnection = null;

      if (getTotalCachedConnections() < this.cacheMinLimit)
        defaultUserPrePopulateCache(1);
    }
  }

  protected void refreshCacheConnections(int paramInt)
    throws SQLException
  {
    doForEveryCachedConnection(paramInt);
  }

  protected void reinitializeCacheConnections(Properties paramProperties)
    throws SQLException
  {
    int m = 0;

    synchronized (this)
    {
      this.defaultUser = this.cacheEnabledDS.user;
      this.defaultPassword = this.cacheEnabledDS.password;
      this.fastConnectionFailoverEnabled = this.cacheEnabledDS.getFastConnectionFailoverEnabled();

      cleanupTimeoutThread();

      doForEveryCheckedOutConnection(1);

      int i = this.cacheInitialLimit;
      int j = this.cacheMaxLimit;
      int k = this.cacheMaxStatementsLimit;

      setConnectionCacheProperties(paramProperties);

      if (this.cacheInitialLimit > i) {
        m = this.cacheInitialLimit - i;
      }

      if (j != 2147483647)
      {
        if ((this.cacheMaxLimit < j) && (this.cacheSize > this.cacheMaxLimit))
        {
          this.connectionsToRemove = (this.cacheSize - this.cacheMaxLimit);

          doForEveryCachedConnection(8);

          this.connectionsToRemove = 0;
        }

      }

      if (this.cacheMaxStatementsLimit != k)
      {
        if (this.cacheMaxStatementsLimit == 0)
          doForEveryCachedConnection(16);
        else {
          doForEveryCachedConnection(18);
        }

      }

    }

    if (m > 0)
    {
      defaultUserPrePopulateCache(m);
    }
  }

  protected synchronized void setConnectionCacheProperties(Properties paramProperties)
    throws SQLException
  {
    try
    {
      if (paramProperties != null)
      {
        String str = null;

        if ((str = paramProperties.getProperty("MinLimit")) != null)
        {
          if ((this.cacheMinLimit = Integer.parseInt(str)) < 0) {
            this.cacheMinLimit = 0;
          }
        }

        if ((str = paramProperties.getProperty("MaxLimit")) != null)
        {
          if ((this.cacheMaxLimit = Integer.parseInt(str)) < 0) {
            this.cacheMaxLimit = 2147483647;
          }
        }

        if (this.cacheMaxLimit < this.cacheMinLimit) {
          this.cacheMinLimit = this.cacheMaxLimit;
        }

        if ((str = paramProperties.getProperty("InitialLimit")) != null)
        {
          if ((this.cacheInitialLimit = Integer.parseInt(str)) < 0) {
            this.cacheInitialLimit = 0;
          }
        }
        if (this.cacheInitialLimit > this.cacheMaxLimit) {
          this.cacheInitialLimit = this.cacheMaxLimit;
        }

        if ((str = paramProperties.getProperty("MaxStatementsLimit")) != null)
        {
          if ((this.cacheMaxStatementsLimit = Integer.parseInt(str)) < 0) {
            this.cacheMaxStatementsLimit = 0;
          }
        }

        Properties localObject1 = (Properties)paramProperties.get("AttributeWeights");

        if (localObject1 != null)
        {
          Map.Entry localEntry = null;
          int i = 0;
          Object localObject2 = null;

          Iterator localIterator = ((Properties)localObject1).entrySet().iterator();

          while (localIterator.hasNext())
          {
            localEntry = (Map.Entry)localIterator.next();
            localObject2 = localEntry.getKey();

            if (((str = (String)((Properties)localObject1).get(localObject2)) != null) &&
              ((i = Integer.parseInt(str)) < 0)) {
              ((Properties)localObject1).put(localObject2, "0");
            }
          }

          if (this.cacheAttributeWeights == null) {
            this.cacheAttributeWeights = new Properties();
          }
          this.cacheAttributeWeights.putAll((Map)localObject1);
        }

        if ((str = paramProperties.getProperty("InactivityTimeout")) != null)
        {
          if ((this.cacheInactivityTimeout = Integer.parseInt(str)) < 0) {
            this.cacheInactivityTimeout = 0;
          }
        }

        if ((str = paramProperties.getProperty("TimeToLiveTimeout")) != null)
        {
          if ((this.cacheTimeToLiveTimeout = Integer.parseInt(str)) < 0) {
            this.cacheTimeToLiveTimeout = 0;
          }
        }

        if ((str = paramProperties.getProperty("AbandonedConnectionTimeout")) != null)
        {
          if ((this.cacheAbandonedConnectionTimeout = Integer.parseInt(str)) < 0) {
            this.cacheAbandonedConnectionTimeout = 0;
          }
        }

        if ((str = paramProperties.getProperty("LowerThresholdLimit")) != null)
        {
          this.cacheLowerThresholdLimit = Integer.parseInt(str);

          if ((this.cacheLowerThresholdLimit < 0) || (this.cacheLowerThresholdLimit > 100))
          {
            this.cacheLowerThresholdLimit = 20;
          }
        }

        if ((str = paramProperties.getProperty("PropertyCheckInterval")) != null)
        {
          if ((this.cachePropertyCheckInterval = Integer.parseInt(str)) < 0)
          {
            this.cachePropertyCheckInterval = 900;
          }

        }

        if ((str = paramProperties.getProperty("ValidateConnection")) != null) {
          this.cacheValidateConnection = Boolean.valueOf(str).booleanValue();
        }

        if ((str = paramProperties.getProperty("ClosestConnectionMatch")) != null)
        {
          this.cacheClosestConnectionMatch = Boolean.valueOf(str).booleanValue();
        }

        if ((str = paramProperties.getProperty("ConnectionWaitTimeout")) != null)
        {
          if ((this.cacheConnectionWaitTimeout = Integer.parseInt(str)) < 0) {
            this.cacheConnectionWaitTimeout = 0;
          }
        }

        if ((str = paramProperties.getProperty("LocalTransactionCommitOnClose")) != null)
        {
          this.cacheLocalTxnCommitOnClose = str.equalsIgnoreCase("true");
        }

      }
      else
      {
        this.cacheMinLimit = 0;
        this.cacheMaxLimit = 2147483647;
        this.cacheInitialLimit = 0;
        this.cacheMaxStatementsLimit = 0;
        this.cacheAttributeWeights = null;
        this.cacheInactivityTimeout = 0;
        this.cacheTimeToLiveTimeout = 0;
        this.cacheAbandonedConnectionTimeout = 0;
        this.cacheLowerThresholdLimit = 20;
        this.cachePropertyCheckInterval = 900;
        this.cacheClosestConnectionMatch = false;
        this.cacheValidateConnection = false;
        this.cacheConnectionWaitTimeout = 0;
        this.cacheLocalTxnCommitOnClose = false;
      }

      if (((this.cacheInactivityTimeout > 0) || (this.cacheTimeToLiveTimeout > 0) || (this.cacheAbandonedConnectionTimeout > 0)) && (this.cachePropertyCheckInterval > 0))
      {
        if (this.timeoutThread == null) {
          this.timeoutThread = new OracleImplicitConnectionCacheThread(this);
        }
        this.cacheManager.checkAndStartThread(this.timeoutThread);
      }

      if (this.cachePropertyCheckInterval == 0)
      {
        cleanupTimeoutThread();
      }

    }
    catch (NumberFormatException localNumberFormatException)
    {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 139, "OracleImplicitConnectionCache:setConnectionCacheProperties() - NumberFormatException Occurred :" + localNumberFormatException.getMessage());

      sqlexception.fillInStackTrace();
      throw sqlexception;
    }
  }

  protected Properties getConnectionCacheProperties()
    throws SQLException
  {
    Properties localProperties = new Properties();

    localProperties.setProperty("MinLimit", String.valueOf(this.cacheMinLimit));
    localProperties.setProperty("MaxLimit", String.valueOf(this.cacheMaxLimit));
    localProperties.setProperty("InitialLimit", String.valueOf(this.cacheInitialLimit));

    localProperties.setProperty("MaxStatementsLimit", String.valueOf(this.cacheMaxStatementsLimit));

    if (this.cacheAttributeWeights != null)
      localProperties.put("AttributeWeights", this.cacheAttributeWeights);
    else {
      localProperties.setProperty("AttributeWeights", "NULL");
    }
    localProperties.setProperty("InactivityTimeout", String.valueOf(this.cacheInactivityTimeout));

    localProperties.setProperty("TimeToLiveTimeout", String.valueOf(this.cacheTimeToLiveTimeout));

    localProperties.setProperty("AbandonedConnectionTimeout", String.valueOf(this.cacheAbandonedConnectionTimeout));

    localProperties.setProperty("LowerThresholdLimit", String.valueOf(this.cacheLowerThresholdLimit));

    localProperties.setProperty("PropertyCheckInterval", String.valueOf(this.cachePropertyCheckInterval));

    localProperties.setProperty("ConnectionWaitTimeout", String.valueOf(this.cacheConnectionWaitTimeout));

    localProperties.setProperty("ValidateConnection", String.valueOf(this.cacheValidateConnection));

    localProperties.setProperty("ClosestConnectionMatch", String.valueOf(this.cacheClosestConnectionMatch));

    localProperties.setProperty("LocalTransactionCommitOnClose", String.valueOf(this.cacheLocalTxnCommitOnClose));

    return localProperties;
  }

  protected int testDatabaseConnection(OracleConnection paramOracleConnection)
    throws SQLException
  {
    return paramOracleConnection.pingDatabase();
  }

  protected synchronized void closeConnectionCache(int paramInt)
    throws SQLException
  {
    cleanupTimeoutThread();

    purgeCacheConnections(true, paramInt);

    this.connectionPoolDS = null;
    this.cacheEnabledDS = null;
    this.checkedOutConnectionList = null;
    this.userMap = null;
    this.cacheManager = null;
  }

  protected synchronized void disableConnectionCache()
    throws SQLException
  {
    this.disableConnectionRequest = true;
  }

  protected synchronized void enableConnectionCache()
    throws SQLException
  {
    this.disableConnectionRequest = false;
  }

  protected void initFailoverParameters(OraclePooledConnection paramOraclePooledConnection)
    throws SQLException
  {
    String str1 = null;
    String str2 = null;
    String str3 = null;

    Properties localProperties = ((OracleConnection)paramOraclePooledConnection.getPhysicalHandle()).getServerSessionInfo();

    str3 = localProperties.getProperty("INSTANCE_NAME");
    if (str3 != null) {
      str1 = paramOraclePooledConnection.dataSourceInstanceNameKey = str3.trim().toLowerCase().intern();
    }

    str3 = localProperties.getProperty("SERVER_HOST");
    if (str3 != null) {
      paramOraclePooledConnection.dataSourceHostNameKey = str3.trim().toLowerCase().intern();
    }

    str3 = localProperties.getProperty("SERVICE_NAME");
    if (str3 != null) {
      this.dataSourceServiceName = str3.trim();
    }

    str3 = localProperties.getProperty("DATABASE_NAME");
    if (str3 != null) {
      str2 = paramOraclePooledConnection.dataSourceDbUniqNameKey = str3.trim().toLowerCase().intern();
    }

    if (this.databaseInstancesList == null) {
      this.databaseInstancesList = new LinkedList();
    }
    int i = this.databaseInstancesList.size();
    synchronized (this.databaseInstancesList)
    {
      OracleDatabaseInstance localOracleDatabaseInstance1 = null;
      int j = 0;

      for (int k = 0; k < i; k++)
      {
        localOracleDatabaseInstance1 = (OracleDatabaseInstance)this.databaseInstancesList.get(k);
        if ((localOracleDatabaseInstance1.databaseUniqName == str2) && (localOracleDatabaseInstance1.instanceName == str1))
        {
          localOracleDatabaseInstance1.numberOfConnectionsCount += 1;
          j = 1;
          break;
        }
      }

      if (j == 0)
      {
        OracleDatabaseInstance localOracleDatabaseInstance2 = new OracleDatabaseInstance(str2, str1);

        localOracleDatabaseInstance2.numberOfConnectionsCount += 1;
        this.databaseInstancesList.add(localOracleDatabaseInstance2);
      }
    }
  }

  protected void processFailoverEvent(int paramInt1, String paramString1, String paramString2, String paramString3, String paramString4, int paramInt2)
  {
    if (paramInt1 == 256)
    {
      if ((paramString4.equalsIgnoreCase("down")) || (paramString4.equalsIgnoreCase("not_restarting")) || (paramString4.equalsIgnoreCase("restart_failed")))
      {
        this.downEventCount += 1;

        markDownLostConnections(true, false, paramString1, paramString2, paramString3, paramString4);

        cleanupFailoverConnections(true, false, paramString1, paramString2, paramString3, paramString4);
      }
      else if (paramString4.equalsIgnoreCase("up"))
      {
        if (this.downEventCount > 0) {
          this.upEventCount += 1;
        }
        try
        {
          processUpEvent(paramInt2);
        }
        catch (Exception localException)
        {
        }

        this.isEntireServiceDownProcessed = false;
      }
    }
    else if ((paramInt1 == 512) && (paramString4.equalsIgnoreCase("nodedown")))
    {
      markDownLostConnections(false, true, paramString1, paramString2, paramString3, paramString4);

      cleanupFailoverConnections(false, true, paramString1, paramString2, paramString3, paramString4);
    }
  }

  void processUpEvent(int paramInt)
    throws SQLException
  {
    int i = 0;
    int j = 0;
    int k = getTotalCachedConnections();
    boolean bool = false;

    synchronized (this)
    {
      if (paramInt <= 1) {
        paramInt = 2;
      }

      if ((this.downEventCount == 0) && (this.upEventCount == 0) && (getNumberOfDefaultUserConnections() > 0))
      {
        i = (int)(this.cacheSize * 0.25D);
      }
      else
      {
        i = this.defaultUserPreFailureSize;
      }

      if (i <= 0)
      {
        if (getNumberOfDefaultUserConnections() > 0)
        {
          j = (int)(this.cacheSize * 0.25D);
          bool = true;
        }

      }
      else
      {
        j = i / paramInt;

        if (j + k > this.cacheMaxLimit) {
          bool = true;
        }

      }

      if (this.downEventCount == this.upEventCount)
      {
        this.defaultUserPreFailureSize = 0;
        this.downEventCount = 0;
        this.upEventCount = 0;
      }

    }

    if (j > 0)
      loadBalanceConnections(j, bool);
  }

  private void loadBalanceConnections(int paramInt, boolean paramBoolean)
    throws SQLException
  {
    if (paramBoolean)
    {
      this.connectionsToRemove = paramInt;

      doForEveryCachedConnection(8);

      this.connectionsToRemove = 0;
    }

    if (paramInt <= 10)
    {
      try
      {
        defaultUserPrePopulateCache(paramInt);
      }
      catch (Exception localException1)
      {
      }

    }
    else
    {
      int i = (int)(paramInt * 0.25D);

      for (int j = 0; j < 4; j++)
      {
        try
        {
          defaultUserPrePopulateCache(i);
        }
        catch (Exception localException2)
        {
        }
      }
    }
  }

  private int getNumberOfDefaultUserConnections()
  {
    int i = 0;

    if ((this.userMap != null) && (!this.userMap.isEmpty()))
    {
      OracleConnectionCacheEntry localOracleConnectionCacheEntry = (OracleConnectionCacheEntry)this.userMap.get(OraclePooledConnection.generateKey(this.defaultUser, this.defaultPassword));

      if ((localOracleConnectionCacheEntry != null) && (localOracleConnectionCacheEntry.userConnList != null) && (!localOracleConnectionCacheEntry.userConnList.isEmpty()))
      {
        i = localOracleConnectionCacheEntry.userConnList.size();
      }
    }
    return i;
  }

  synchronized void markDownLostConnections(boolean paramBoolean1, boolean paramBoolean2, String paramString1, String paramString2, String paramString3, String paramString4)
  {
    if (!this.isEntireServiceDownProcessed)
    {
      if ((this.userMap != null) && (!this.userMap.isEmpty()))
      {
        Iterator localIterator1 = this.userMap.entrySet().iterator();

        while (localIterator1.hasNext())
        {
          int i = 0;

          Map.Entry localEntry = (Map.Entry)localIterator1.next();
          String str = null;
          if ((this.defaultUser != null) && (this.defaultPassword != null)) {
            str = this.defaultUser + this.defaultPassword;
          }

          if ((str != null) && (str.equalsIgnoreCase((String)localEntry.getKey())))
          {
            i = 1;
          }
          OracleConnectionCacheEntry localOracleConnectionCacheEntry = (OracleConnectionCacheEntry)localEntry.getValue();
          Object localObject1;
          Object localObject2;
          if ((localOracleConnectionCacheEntry != null) && (localOracleConnectionCacheEntry.userConnList != null) && (!localOracleConnectionCacheEntry.userConnList.isEmpty()))
          {
            boolean bool = false;
            localObject1 = localOracleConnectionCacheEntry.userConnList.iterator();

            while (((Iterator)localObject1).hasNext())
            {
              localObject2 = (OraclePooledConnection)((Iterator)localObject1).next();

              if (paramBoolean1) {
                bool = markDownConnectionsForServiceEvent(paramString1, paramString2, (OraclePooledConnection)localObject2);
              }
              else if (paramBoolean2) {
                bool = markDownConnectionsForHostEvent(paramString3, (OraclePooledConnection)localObject2);
              }

              if ((bool) && (i != 0)) {
                this.defaultUserPreFailureSize += 1;
              }
            }
          }

          if ((localOracleConnectionCacheEntry != null) && (localOracleConnectionCacheEntry.attrConnMap != null) && (!localOracleConnectionCacheEntry.attrConnMap.isEmpty()))
          {
            Iterator localIterator2 = localOracleConnectionCacheEntry.attrConnMap.entrySet().iterator();

            while (localIterator2.hasNext())
            {
              localObject1 = (Map.Entry)localIterator2.next();
              localObject2 = ((Vector)((Map.Entry)localObject1).getValue()).iterator();

              while (((Iterator)localObject2).hasNext())
              {
                OraclePooledConnection localOraclePooledConnection = (OraclePooledConnection)((Iterator)localObject2).next();

                if (paramBoolean1) {
                  markDownConnectionsForServiceEvent(paramString1, paramString2, localOraclePooledConnection);
                }
                else if (paramBoolean2) {
                  markDownConnectionsForHostEvent(paramString3, localOraclePooledConnection);
                }
              }
            }
          }
        }
      }
      if (paramString1 == null)
        this.isEntireServiceDownProcessed = true;
    }
  }

  private boolean markDownConnectionsForServiceEvent(String paramString1, String paramString2, OraclePooledConnection paramOraclePooledConnection)
  {
    boolean bool = false;

    if ((paramString1 == null) || ((paramString2 == paramOraclePooledConnection.dataSourceDbUniqNameKey) && (paramString1 == paramOraclePooledConnection.dataSourceInstanceNameKey)))
    {
      paramOraclePooledConnection.connectionMarkedDown = true;
      bool = true;
    }

    return bool;
  }

  private boolean markDownConnectionsForHostEvent(String paramString, OraclePooledConnection paramOraclePooledConnection)
  {
    boolean bool = false;

    if (paramString == paramOraclePooledConnection.dataSourceHostNameKey)
    {
      paramOraclePooledConnection.connectionMarkedDown = true;
      paramOraclePooledConnection.needToAbort = true;
      bool = true;
    }
    return bool;
  }

  synchronized void cleanupFailoverConnections(boolean paramBoolean1, boolean paramBoolean2, String paramString1, String paramString2, String paramString3, String paramString4)
  {
    OraclePooledConnection localOraclePooledConnection = null;
    Object[] arrayOfObject = this.checkedOutConnectionList.toArray();
    int i = this.checkedOutConnectionList.size();

    OraclePooledConnection[] arrayOfOraclePooledConnection = new OraclePooledConnection[i];
    int j = 0;

    for (int k = 0; k < i; k++)
    {
      try
      {
        localOraclePooledConnection = (OraclePooledConnection)arrayOfObject[k];

        if (((paramBoolean1) && ((paramString1 == null) || (paramString1 == localOraclePooledConnection.dataSourceInstanceNameKey)) && (paramString2 == localOraclePooledConnection.dataSourceDbUniqNameKey)) || ((paramBoolean2) && (paramString3 == localOraclePooledConnection.dataSourceHostNameKey)))
        {
          if ((localOraclePooledConnection.isSameUser(this.defaultUser, this.defaultPassword)) && (localOraclePooledConnection.cachedConnectionAttributes != null) && (localOraclePooledConnection.cachedConnectionAttributes.isEmpty()))
          {
            this.defaultUserPreFailureSize += 1;
          }

          this.checkedOutConnectionList.removeElement(localOraclePooledConnection);

          abortConnection(localOraclePooledConnection);
          localOraclePooledConnection.needToAbort = true;

          arrayOfOraclePooledConnection[(j++)] = localOraclePooledConnection;
        }

      }
      catch (Exception localException)
      {
      }

    }

    for (int k = 0; k < j; k++)
    {
      try
      {
        closeCheckedOutConnection(arrayOfOraclePooledConnection[k], false);
      }
      catch (SQLException localSQLException2)
      {
      }

    }

    if ((this.checkedOutConnectionList.size() < i) && (this.cacheConnectionWaitTimeout > 0))
    {
      notifyAll();
    }

    try
    {
      doForEveryCachedConnection(2);
    }
    catch (SQLException localSQLException1)
    {
    }

    if ((this.databaseInstancesList != null) && ((i = this.databaseInstancesList.size()) > 0))
    {
      synchronized (this.databaseInstancesList)
      {
        OracleDatabaseInstance localOracleDatabaseInstance = null;
        arrayOfObject = this.databaseInstancesList.toArray();

        for (int m = 0; m < i; m++)
        {
          localOracleDatabaseInstance = (OracleDatabaseInstance)arrayOfObject[m];

          if ((localOracleDatabaseInstance.databaseUniqName == paramString2) && (localOracleDatabaseInstance.instanceName == paramString1))
          {
            if (localOracleDatabaseInstance.flag <= 3)
              this.dbInstancePercentTotal -= localOracleDatabaseInstance.percent;
            this.databaseInstancesList.remove(localOracleDatabaseInstance);
          }
        }
      }
    }
  }

  void zapRLBInfo()
  {
    this.databaseInstancesList.clear();
  }

  protected synchronized void closeAndRemovePooledConnection(OraclePooledConnection paramOraclePooledConnection)
    throws SQLException
  {
    if (paramOraclePooledConnection != null)
    {
      if (paramOraclePooledConnection.needToAbort) {
        abortConnection(paramOraclePooledConnection);
      }
      actualPooledConnectionClose(paramOraclePooledConnection);
      removeCacheConnection(paramOraclePooledConnection);
    }
  }

  private void abortConnection(OraclePooledConnection paramOraclePooledConnection)
  {
    try
    {
      ((OracleConnection)paramOraclePooledConnection.getPhysicalHandle()).abort();
    }
    catch (Exception localException)
    {
    }
  }

  private void actualPooledConnectionClose(OraclePooledConnection paramOraclePooledConnection)
    throws SQLException
  {
    int i = 0;
    if ((this.databaseInstancesList != null) && ((i = this.databaseInstancesList.size()) > 0))
    {
      synchronized (this.databaseInstancesList)
      {
        OracleDatabaseInstance localOracleDatabaseInstance = null;

        for (int j = 0; j < i; j++)
        {
          localOracleDatabaseInstance = (OracleDatabaseInstance)this.databaseInstancesList.get(j);
          if ((localOracleDatabaseInstance.databaseUniqName == paramOraclePooledConnection.dataSourceDbUniqNameKey) && (localOracleDatabaseInstance.instanceName == paramOraclePooledConnection.dataSourceInstanceNameKey))
          {
            if (localOracleDatabaseInstance.numberOfConnectionsCount <= 0) break;
            localOracleDatabaseInstance.numberOfConnectionsCount -= 1; break;
          }

        }

      }

    }

    try
    {
      this.connectionClosedCount += 1;
      paramOraclePooledConnection.close();
    }
    catch (SQLException localSQLException)
    {
    }
  }

  protected int getCacheTimeToLiveTimeout()
  {
    return this.cacheTimeToLiveTimeout;
  }

  protected int getCacheInactivityTimeout()
  {
    return this.cacheInactivityTimeout;
  }

  protected int getCachePropertyCheckInterval()
  {
    return this.cachePropertyCheckInterval;
  }

  protected int getCacheAbandonedTimeout()
  {
    return this.cacheAbandonedConnectionTimeout;
  }

  private synchronized void processConnectionCacheCallback()
    throws SQLException
  {
    float f = this.cacheMaxLimit / 100.0F;
    int i = (int)(this.cacheLowerThresholdLimit * f);

    releaseBasedOnPriority(1024, i);

    if (this.cacheSize < i)
      releaseBasedOnPriority(512, i);
  }

  private void releaseBasedOnPriority(int paramInt1, int paramInt2)
    throws SQLException
  {
    Object[] arrayOfObject = this.checkedOutConnectionList.toArray();

    for (int i = 0; (i < arrayOfObject.length) && (this.cacheSize < paramInt2); i++)
    {
      OraclePooledConnection localOraclePooledConnection = (OraclePooledConnection)arrayOfObject[i];
      OracleConnection localOracleConnection = null;

      if (localOraclePooledConnection != null) {
        localOracleConnection = (OracleConnection)localOraclePooledConnection.getLogicalHandle();
      }
      if (localOracleConnection != null)
      {
        OracleConnectionCacheCallback localOracleConnectionCacheCallback = localOracleConnection.getConnectionCacheCallbackObj();

        if ((localOracleConnectionCacheCallback != null) && ((localOracleConnection.getConnectionCacheCallbackFlag() == 2) || (localOracleConnection.getConnectionCacheCallbackFlag() == 4)))
        {
          if (paramInt1 == localOracleConnection.getConnectionReleasePriority())
          {
            Object localObject = localOracleConnection.getConnectionCacheCallbackPrivObj();
            localOracleConnectionCacheCallback.releaseConnection(localOracleConnection, localObject);
          }
        }
      }
    }
  }

  private synchronized void processConnectionWaitTimeout(long paramLong)
    throws SQLException
  {
    try
    {
      wait(paramLong);
    }
    catch (InterruptedException localInterruptedException)
    {
    }
  }

  private void processInactivityTimeout(OraclePooledConnection paramOraclePooledConnection)
    throws SQLException
  {
    long l1 = paramOraclePooledConnection.getLastAccessedTime();
    long l2 = System.currentTimeMillis();

    if ((getTotalCachedConnections() > this.cacheMinLimit) && (l2 - l1 > this.cacheInactivityTimeout * 1000))
    {
      closeAndRemovePooledConnection(paramOraclePooledConnection);
    }
  }

  private void cleanupTimeoutThread()
    throws SQLException
  {
    if (this.timeoutThread != null)
    {
      this.timeoutThread.timeToLive = false;

      if (this.timeoutThread.isSleeping) {
        this.timeoutThread.interrupt();
      }
      this.timeoutThread = null;
    }
  }

  protected void purgeCacheConnections(boolean paramBoolean, int paramInt)
  {
    try
    {
      if (paramBoolean) {
        doForEveryCheckedOutConnection(paramInt);
      }

      doForEveryCachedConnection(paramInt);
    }
    catch (SQLException localSQLException)
    {
    }
  }

  protected void updateDatabaseInstance(String paramString1, String paramString2, int paramInt1, int paramInt2)
  {
    if (this.databaseInstancesList == null) {
      this.databaseInstancesList = new LinkedList();
    }
    synchronized (this.databaseInstancesList)
    {
      int i = this.databaseInstancesList.size();
      int j = 0;

      for (int k = 0; k < i; k++)
      {
        OracleDatabaseInstance localOracleDatabaseInstance2 = (OracleDatabaseInstance)this.databaseInstancesList.get(k);

        if ((localOracleDatabaseInstance2.databaseUniqName == paramString1) && (localOracleDatabaseInstance2.instanceName == paramString2))
        {
          localOracleDatabaseInstance2.percent = paramInt1;
          localOracleDatabaseInstance2.flag = paramInt2;
          j = 1;
          break;
        }
      }

      if (j == 0)
      {
        OracleDatabaseInstance localOracleDatabaseInstance1 = new OracleDatabaseInstance(paramString1, paramString2);

        localOracleDatabaseInstance1.percent = paramInt1;
        localOracleDatabaseInstance1.flag = paramInt2;

        this.databaseInstancesList.add(localOracleDatabaseInstance1);
      }
    }
  }

  protected void processDatabaseInstances()
    throws SQLException
  {
    OracleDatabaseInstance localOracleDatabaseInstance = null;

    if (this.databaseInstancesList != null)
    {
      synchronized (this.databaseInstancesList)
      {
        int i = 0;
        int j = 0;

        this.useGoodGroup = false;

        int k = this.databaseInstancesList.size();

        for (int m = 0; m < k; m++)
        {
          localOracleDatabaseInstance = (OracleDatabaseInstance)this.databaseInstancesList.get(m);

          if (localOracleDatabaseInstance.flag <= 3) {
            i += localOracleDatabaseInstance.percent;
          }

        }

        if (i > 0)
        {
          this.dbInstancePercentTotal = i;
          this.useGoodGroup = true;
        }

        if (k > 1)
        {
          for (int m = 0; m < k; m++)
          {
            localOracleDatabaseInstance = (OracleDatabaseInstance)this.databaseInstancesList.get(m);
            this.countTotal += localOracleDatabaseInstance.attemptedConnRequestCount;
          }

          if (this.countTotal > k * 1000)
          {
            for (int m = 0; m < k; m++)
            {
              localOracleDatabaseInstance = (OracleDatabaseInstance)this.databaseInstancesList.get(m);

              float f1 = localOracleDatabaseInstance.attemptedConnRequestCount / this.countTotal;

              float f2 = localOracleDatabaseInstance.numberOfConnectionsCount / getTotalCachedConnections();

              if (f2 > f1 * 2.0F)
              {
                if ((int)(localOracleDatabaseInstance.numberOfConnectionsCount * 0.25D) >= 1) {
                  this.instancesToRetireQueue.addElement(localOracleDatabaseInstance);
                }
                j = 1;
              }

            }

            if (j != 0)
            {
              for (int m = 0; m < k; m++)
              {
                localOracleDatabaseInstance = (OracleDatabaseInstance)this.databaseInstancesList.get(m);

                localOracleDatabaseInstance.attemptedConnRequestCount = 0;
              }
              j = 0;
            }

          }

        }

      }

      if (this.instancesToRetireQueue.size() > 0)
      {
        if (this.gravitateCacheThread != null)
        {
          try
          {
            this.gravitateCacheThread.interrupt();
            this.gravitateCacheThread.join();
          }
          catch (InterruptedException localInterruptedException)
          {
          }

          this.gravitateCacheThread = null;
        }

        this.gravitateCacheThread = new OracleGravitateConnectionCacheThread(this);

        this.cacheManager.checkAndStartThread(this.gravitateCacheThread);
      }
    }
  }

  protected void gravitateCache()
  {
    while (this.instancesToRetireQueue.size() > 0)
    {
      this.instanceToRetire = ((OracleDatabaseInstance)this.instancesToRetireQueue.remove(0));
      this.retireConnectionsCount = ((int)(this.instanceToRetire.numberOfConnectionsCount * 0.25D));
      try
      {
        doForEveryCachedConnection(24);
      }
      catch (SQLException localSQLException1)
      {
      }

      if (this.retireConnectionsCount > 0)
      {
        try
        {
          doForEveryCheckedOutConnection(24);
        }
        catch (SQLException localSQLException2)
        {
        }

      }

    }

    this.retireConnectionsCount = 0;
    this.instanceToRetire = null;
    this.countTotal = 0;
  }

  protected void cleanupRLBThreads()
  {
    if (this.gravitateCacheThread != null)
    {
      try
      {
        this.gravitateCacheThread.interrupt();
        this.gravitateCacheThread.join();
      }
      catch (InterruptedException localInterruptedException)
      {
      }

      this.gravitateCacheThread = null;
    }

    if (this.runtimeLoadBalancingThread != null)
    {
      try
      {
        this.runtimeLoadBalancingThread.interrupt();
      }
      catch (Exception localException)
      {
      }

      this.runtimeLoadBalancingThread = null;
    }
  }

  Map getStatistics()
    throws SQLException
  {
    HashMap localHashMap = new HashMap(2);
    localHashMap.put("PhysicalConnectionClosedCount", new Integer(this.connectionClosedCount));
    localHashMap.put("PhysicalConnectionCreatedCount", new Integer(this.connectionCreatedCount));

    return localHashMap;
  }

  protected OracleConnection getConnectionDuringExceptionHandling()
  {
    return null;
  }
}