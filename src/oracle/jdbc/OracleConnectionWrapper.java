package oracle.jdbc;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;
import oracle.jdbc.aq.AQDequeueOptions;
import oracle.jdbc.aq.AQEnqueueOptions;
import oracle.jdbc.aq.AQMessage;
import oracle.jdbc.aq.AQNotificationRegistration;
import oracle.jdbc.dcn.DatabaseChangeRegistration;
import oracle.jdbc.driver.DatabaseError;
import oracle.jdbc.pool.OracleConnectionCacheCallback;
import oracle.sql.ARRAY;
import oracle.sql.BINARY_DOUBLE;
import oracle.sql.BINARY_FLOAT;
import oracle.sql.DATE;
import oracle.sql.INTERVALDS;
import oracle.sql.INTERVALYM;
import oracle.sql.NUMBER;
import oracle.sql.TIMESTAMP;
import oracle.sql.TIMESTAMPLTZ;
import oracle.sql.TIMESTAMPTZ;
import oracle.sql.TypeDescriptor;

public class OracleConnectionWrapper
  implements OracleConnection
{
  protected OracleConnection connection;
  private Map<Class, Object> proxies = new HashMap(3);
  private static Map<Class, Class> proxyClasses = new HashMap();

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  public OracleConnectionWrapper()
  {
  }

  public OracleConnectionWrapper(OracleConnection paramOracleConnection)
  {
    this.connection = paramOracleConnection;

    paramOracleConnection.setWrapper(this);
  }

  public OracleConnection unwrap()
  {
    return this.connection;
  }

  public oracle.jdbc.internal.OracleConnection physicalConnectionWithin()
  {
    return this.connection.physicalConnectionWithin();
  }

  public String getDatabaseTimeZone()
    throws SQLException
  {
    return physicalConnectionWithin().getDatabaseTimeZone();
  }

  public void setWrapper(OracleConnection paramOracleConnection)
  {
    this.connection.setWrapper(paramOracleConnection);
  }

  public Statement createStatement()
    throws SQLException
  {
    return this.connection.createStatement();
  }

  public PreparedStatement prepareStatement(String paramString) throws SQLException
  {
    return this.connection.prepareStatement(paramString);
  }

  public CallableStatement prepareCall(String paramString) throws SQLException
  {
    return this.connection.prepareCall(paramString);
  }

  public String nativeSQL(String paramString) throws SQLException
  {
    return this.connection.nativeSQL(paramString);
  }

  public void setAutoCommit(boolean paramBoolean) throws SQLException
  {
    this.connection.setAutoCommit(paramBoolean);
  }

  public boolean getAutoCommit() throws SQLException
  {
    return this.connection.getAutoCommit();
  }

  public void commit() throws SQLException
  {
    this.connection.commit();
  }

  public void rollback() throws SQLException
  {
    this.connection.rollback();
  }

  public void close() throws SQLException
  {
    this.connection.close();
  }

  public boolean isClosed() throws SQLException
  {
    return this.connection.isClosed();
  }

  public DatabaseMetaData getMetaData() throws SQLException
  {
    return this.connection.getMetaData();
  }

  public void setReadOnly(boolean paramBoolean) throws SQLException
  {
    this.connection.setReadOnly(paramBoolean);
  }

  public boolean isReadOnly() throws SQLException
  {
    return this.connection.isReadOnly();
  }

  public void setCatalog(String paramString) throws SQLException
  {
    this.connection.setCatalog(paramString);
  }

  public String getCatalog() throws SQLException
  {
    return this.connection.getCatalog();
  }

  public void setTransactionIsolation(int paramInt) throws SQLException
  {
    this.connection.setTransactionIsolation(paramInt);
  }

  public int getTransactionIsolation() throws SQLException
  {
    return this.connection.getTransactionIsolation();
  }

  public SQLWarning getWarnings() throws SQLException
  {
    return this.connection.getWarnings();
  }

  public void clearWarnings() throws SQLException
  {
    this.connection.clearWarnings();
  }

  public Statement createStatement(int paramInt1, int paramInt2)
    throws SQLException
  {
    return this.connection.createStatement(paramInt1, paramInt2);
  }

  public PreparedStatement prepareStatement(String paramString, int paramInt1, int paramInt2)
    throws SQLException
  {
    return this.connection.prepareStatement(paramString, paramInt1, paramInt2);
  }

  public CallableStatement prepareCall(String paramString, int paramInt1, int paramInt2)
    throws SQLException
  {
    return this.connection.prepareCall(paramString, paramInt1, paramInt2);
  }

  public Map getTypeMap() throws SQLException
  {
    return this.connection.getTypeMap();
  }

  public void setTypeMap(Map paramMap) throws SQLException
  {
    this.connection.setTypeMap(paramMap);
  }

  public boolean isProxySession()
  {
    return this.connection.isProxySession();
  }

  public void openProxySession(int paramInt, Properties paramProperties)
    throws SQLException
  {
    this.connection.openProxySession(paramInt, paramProperties);
  }

  public void archive(int paramInt1, int paramInt2, String paramString) throws SQLException
  {
    this.connection.archive(paramInt1, paramInt2, paramString);
  }

  public boolean getAutoClose() throws SQLException
  {
    return this.connection.getAutoClose();
  }

  public CallableStatement getCallWithKey(String paramString) throws SQLException
  {
    return this.connection.getCallWithKey(paramString);
  }

  public int getDefaultExecuteBatch()
  {
    return this.connection.getDefaultExecuteBatch();
  }

  public int getDefaultRowPrefetch()
  {
    return this.connection.getDefaultRowPrefetch();
  }

  public Object getDescriptor(String paramString)
  {
    return this.connection.getDescriptor(paramString);
  }

  public String[] getEndToEndMetrics() throws SQLException
  {
    return this.connection.getEndToEndMetrics();
  }

  public short getEndToEndECIDSequenceNumber() throws SQLException
  {
    return this.connection.getEndToEndECIDSequenceNumber();
  }

  public boolean getIncludeSynonyms()
  {
    return this.connection.getIncludeSynonyms();
  }

  public boolean getRestrictGetTables()
  {
    return this.connection.getRestrictGetTables();
  }

  public boolean getImplicitCachingEnabled() throws SQLException
  {
    return this.connection.getImplicitCachingEnabled();
  }

  public boolean getExplicitCachingEnabled() throws SQLException
  {
    return this.connection.getExplicitCachingEnabled();
  }

  public Object getJavaObject(String paramString)
    throws SQLException
  {
    return this.connection.getJavaObject(paramString);
  }

  public boolean getRemarksReporting()
  {
    return this.connection.getRemarksReporting();
  }

  public String getSQLType(Object paramObject) throws SQLException
  {
    return this.connection.getSQLType(paramObject);
  }

  public int getStmtCacheSize()
  {
    return this.connection.getStmtCacheSize();
  }

  public int getStatementCacheSize() throws SQLException
  {
    return this.connection.getStatementCacheSize();
  }

  public PreparedStatement getStatementWithKey(String paramString) throws SQLException
  {
    return this.connection.getStatementWithKey(paramString);
  }

  public short getStructAttrCsId() throws SQLException
  {
    return this.connection.getStructAttrCsId();
  }

  public String getUserName() throws SQLException
  {
    return this.connection.getUserName();
  }

  public String getCurrentSchema() throws SQLException
  {
    return this.connection.getCurrentSchema();
  }

  public boolean getUsingXAFlag()
  {
    return this.connection.getUsingXAFlag();
  }

  public boolean getXAErrorFlag()
  {
    return this.connection.getXAErrorFlag();
  }

  public OracleSavepoint oracleSetSavepoint() throws SQLException
  {
    return this.connection.oracleSetSavepoint();
  }

  public OracleSavepoint oracleSetSavepoint(String paramString) throws SQLException
  {
    return this.connection.oracleSetSavepoint(paramString);
  }

  public void oracleRollback(OracleSavepoint paramOracleSavepoint) throws SQLException
  {
    this.connection.oracleRollback(paramOracleSavepoint);
  }

  public void oracleReleaseSavepoint(OracleSavepoint paramOracleSavepoint)
    throws SQLException
  {
    this.connection.oracleReleaseSavepoint(paramOracleSavepoint);
  }

  public int pingDatabase()
    throws SQLException
  {
    return this.connection.pingDatabase();
  }

  public int pingDatabase(int paramInt) throws SQLException
  {
    return this.connection.pingDatabase(paramInt);
  }

  public void purgeExplicitCache() throws SQLException
  {
    this.connection.purgeExplicitCache();
  }

  public void purgeImplicitCache() throws SQLException
  {
    this.connection.purgeImplicitCache();
  }

  public void putDescriptor(String paramString, Object paramObject) throws SQLException
  {
    this.connection.putDescriptor(paramString, paramObject);
  }

  public void registerSQLType(String paramString, Class paramClass)
    throws SQLException
  {
    this.connection.registerSQLType(paramString, paramClass);
  }

  public void registerSQLType(String paramString1, String paramString2)
    throws SQLException
  {
    this.connection.registerSQLType(paramString1, paramString2);
  }

  public void setAutoClose(boolean paramBoolean) throws SQLException
  {
    this.connection.setAutoClose(paramBoolean);
  }

  public void setDefaultExecuteBatch(int paramInt) throws SQLException
  {
    this.connection.setDefaultExecuteBatch(paramInt);
  }

  public void setDefaultRowPrefetch(int paramInt) throws SQLException
  {
    this.connection.setDefaultRowPrefetch(paramInt);
  }

  public void setEndToEndMetrics(String[] paramArrayOfString, short paramShort)
    throws SQLException
  {
    this.connection.setEndToEndMetrics(paramArrayOfString, paramShort);
  }

  public void setExplicitCachingEnabled(boolean paramBoolean) throws SQLException
  {
    this.connection.setExplicitCachingEnabled(paramBoolean);
  }

  public void setImplicitCachingEnabled(boolean paramBoolean) throws SQLException
  {
    this.connection.setImplicitCachingEnabled(paramBoolean);
  }

  public void setIncludeSynonyms(boolean paramBoolean)
  {
    this.connection.setIncludeSynonyms(paramBoolean);
  }

  public void setRemarksReporting(boolean paramBoolean)
  {
    this.connection.setRemarksReporting(paramBoolean);
  }

  public void setRestrictGetTables(boolean paramBoolean)
  {
    this.connection.setRestrictGetTables(paramBoolean);
  }

  public void setStmtCacheSize(int paramInt) throws SQLException
  {
    this.connection.setStmtCacheSize(paramInt);
  }

  public void setStatementCacheSize(int paramInt) throws SQLException
  {
    this.connection.setStatementCacheSize(paramInt);
  }

  public void setStmtCacheSize(int paramInt, boolean paramBoolean)
    throws SQLException
  {
    this.connection.setStmtCacheSize(paramInt, paramBoolean);
  }

  public void setUsingXAFlag(boolean paramBoolean)
  {
    this.connection.setUsingXAFlag(paramBoolean);
  }

  public void setXAErrorFlag(boolean paramBoolean)
  {
    this.connection.setXAErrorFlag(paramBoolean);
  }

  public void shutdown(OracleConnection.DatabaseShutdownMode paramDatabaseShutdownMode) throws SQLException
  {
    this.connection.shutdown(paramDatabaseShutdownMode);
  }

  public void startup(String paramString, int paramInt) throws SQLException
  {
    this.connection.startup(paramString, paramInt);
  }

  public void startup(OracleConnection.DatabaseStartupMode paramDatabaseStartupMode) throws SQLException
  {
    this.connection.startup(paramDatabaseStartupMode);
  }

  public PreparedStatement prepareStatementWithKey(String paramString)
    throws SQLException
  {
    return this.connection.prepareStatementWithKey(paramString);
  }

  public CallableStatement prepareCallWithKey(String paramString) throws SQLException
  {
    return this.connection.prepareCallWithKey(paramString);
  }

  public void setCreateStatementAsRefCursor(boolean paramBoolean)
  {
    this.connection.setCreateStatementAsRefCursor(paramBoolean);
  }

  public boolean getCreateStatementAsRefCursor()
  {
    return this.connection.getCreateStatementAsRefCursor();
  }

  public void setSessionTimeZone(String paramString) throws SQLException
  {
    this.connection.setSessionTimeZone(paramString);
  }

  public String getSessionTimeZone()
  {
    return this.connection.getSessionTimeZone();
  }

  public String getSessionTimeZoneOffset() throws SQLException
  {
    return this.connection.getSessionTimeZoneOffset();
  }

  public Connection _getPC()
  {
    return this.connection._getPC();
  }

  public boolean isLogicalConnection()
  {
    return this.connection.isLogicalConnection();
  }

  public void registerTAFCallback(OracleOCIFailover paramOracleOCIFailover, Object paramObject)
    throws SQLException
  {
    this.connection.registerTAFCallback(paramOracleOCIFailover, paramObject);
  }

  public Properties getProperties()
  {
    return this.connection.getProperties();
  }

  public void close(Properties paramProperties)
    throws SQLException
  {
    this.connection.close(paramProperties);
  }

  public void close(int paramInt) throws SQLException
  {
    this.connection.close(paramInt);
  }

  public void applyConnectionAttributes(Properties paramProperties)
    throws SQLException
  {
    this.connection.applyConnectionAttributes(paramProperties);
  }

  public Properties getConnectionAttributes() throws SQLException
  {
    return this.connection.getConnectionAttributes();
  }

  public Properties getUnMatchedConnectionAttributes()
    throws SQLException
  {
    return this.connection.getUnMatchedConnectionAttributes();
  }

  public void registerConnectionCacheCallback(OracleConnectionCacheCallback paramOracleConnectionCacheCallback, Object paramObject, int paramInt)
    throws SQLException
  {
    this.connection.registerConnectionCacheCallback(paramOracleConnectionCacheCallback, paramObject, paramInt);
  }

  public void setConnectionReleasePriority(int paramInt) throws SQLException
  {
    this.connection.setConnectionReleasePriority(paramInt);
  }

  public int getConnectionReleasePriority() throws SQLException
  {
    return this.connection.getConnectionReleasePriority();
  }

  public void setPlsqlWarnings(String paramString) throws SQLException
  {
    this.connection.setPlsqlWarnings(paramString);
  }

  public void setHoldability(int paramInt) throws SQLException
  {
    this.connection.setHoldability(paramInt);
  }

  public int getHoldability() throws SQLException
  {
    return this.connection.getHoldability();
  }

  public Statement createStatement(int paramInt1, int paramInt2, int paramInt3) throws SQLException
  {
    return this.connection.createStatement(paramInt1, paramInt2, paramInt3);
  }

  public PreparedStatement prepareStatement(String paramString, int paramInt1, int paramInt2, int paramInt3)
    throws SQLException
  {
    return this.connection.prepareStatement(paramString, paramInt1, paramInt2, paramInt3);
  }

  public CallableStatement prepareCall(String paramString, int paramInt1, int paramInt2, int paramInt3)
    throws SQLException
  {
    return this.connection.prepareCall(paramString, paramInt1, paramInt2, paramInt3);
  }

  public synchronized Savepoint setSavepoint()
    throws SQLException
  {
    return this.connection.setSavepoint();
  }

  public synchronized Savepoint setSavepoint(String paramString)
    throws SQLException
  {
    return this.connection.setSavepoint(paramString);
  }

  public synchronized void rollback(Savepoint paramSavepoint)
    throws SQLException
  {
    this.connection.rollback(paramSavepoint);
  }

  public synchronized void releaseSavepoint(Savepoint paramSavepoint)
    throws SQLException
  {
    this.connection.releaseSavepoint(paramSavepoint);
  }

  public PreparedStatement prepareStatement(String paramString, int paramInt)
    throws SQLException
  {
    return this.connection.prepareStatement(paramString, paramInt);
  }

  public PreparedStatement prepareStatement(String paramString, int[] paramArrayOfInt)
    throws SQLException
  {
    return this.connection.prepareStatement(paramString, paramArrayOfInt);
  }

  public PreparedStatement prepareStatement(String paramString, String[] paramArrayOfString)
    throws SQLException
  {
    return this.connection.prepareStatement(paramString, paramArrayOfString);
  }

  public ARRAY createARRAY(String paramString, Object paramObject)
    throws SQLException
  {
    return this.connection.createARRAY(paramString, paramObject);
  }

  public BINARY_DOUBLE createBINARY_DOUBLE(double paramDouble)
    throws SQLException
  {
    return this.connection.createBINARY_DOUBLE(paramDouble);
  }

  public BINARY_FLOAT createBINARY_FLOAT(float paramFloat)
    throws SQLException
  {
    return this.connection.createBINARY_FLOAT(paramFloat);
  }

  public DATE createDATE(Date paramDate)
    throws SQLException
  {
    return this.connection.createDATE(paramDate);
  }

  public DATE createDATE(Time paramTime)
    throws SQLException
  {
    return this.connection.createDATE(paramTime);
  }

  public DATE createDATE(Timestamp paramTimestamp)
    throws SQLException
  {
    return this.connection.createDATE(paramTimestamp);
  }

  public DATE createDATE(Date paramDate, Calendar paramCalendar)
    throws SQLException
  {
    return this.connection.createDATE(paramDate, paramCalendar);
  }

  public DATE createDATE(Time paramTime, Calendar paramCalendar)
    throws SQLException
  {
    return this.connection.createDATE(paramTime, paramCalendar);
  }

  public DATE createDATE(Timestamp paramTimestamp, Calendar paramCalendar)
    throws SQLException
  {
    return this.connection.createDATE(paramTimestamp, paramCalendar);
  }

  public DATE createDATE(String paramString)
    throws SQLException
  {
    return this.connection.createDATE(paramString);
  }

  public INTERVALDS createINTERVALDS(String paramString)
    throws SQLException
  {
    return this.connection.createINTERVALDS(paramString);
  }

  public INTERVALYM createINTERVALYM(String paramString)
    throws SQLException
  {
    return this.connection.createINTERVALYM(paramString);
  }

  public NUMBER createNUMBER(boolean paramBoolean)
    throws SQLException
  {
    return this.connection.createNUMBER(paramBoolean);
  }

  public NUMBER createNUMBER(byte paramByte)
    throws SQLException
  {
    return this.connection.createNUMBER(paramByte);
  }

  public NUMBER createNUMBER(short paramShort)
    throws SQLException
  {
    return this.connection.createNUMBER(paramShort);
  }

  public NUMBER createNUMBER(int paramInt)
    throws SQLException
  {
    return this.connection.createNUMBER(paramInt);
  }

  public NUMBER createNUMBER(long paramLong)
    throws SQLException
  {
    return this.connection.createNUMBER(paramLong);
  }

  public NUMBER createNUMBER(float paramFloat)
    throws SQLException
  {
    return this.connection.createNUMBER(paramFloat);
  }

  public NUMBER createNUMBER(double paramDouble)
    throws SQLException
  {
    return this.connection.createNUMBER(paramDouble);
  }

  public NUMBER createNUMBER(BigDecimal paramBigDecimal)
    throws SQLException
  {
    return this.connection.createNUMBER(paramBigDecimal);
  }

  public NUMBER createNUMBER(BigInteger paramBigInteger)
    throws SQLException
  {
    return this.connection.createNUMBER(paramBigInteger);
  }

  public NUMBER createNUMBER(String paramString, int paramInt)
    throws SQLException
  {
    return this.connection.createNUMBER(paramString, paramInt);
  }

  public TIMESTAMP createTIMESTAMP(Date paramDate)
    throws SQLException
  {
    return this.connection.createTIMESTAMP(paramDate);
  }

  public TIMESTAMP createTIMESTAMP(DATE paramDATE)
    throws SQLException
  {
    return this.connection.createTIMESTAMP(paramDATE);
  }

  public TIMESTAMP createTIMESTAMP(Time paramTime)
    throws SQLException
  {
    return this.connection.createTIMESTAMP(paramTime);
  }

  public TIMESTAMP createTIMESTAMP(Timestamp paramTimestamp)
    throws SQLException
  {
    return this.connection.createTIMESTAMP(paramTimestamp);
  }

  public TIMESTAMP createTIMESTAMP(String paramString)
    throws SQLException
  {
    return this.connection.createTIMESTAMP(paramString);
  }

  public TIMESTAMPTZ createTIMESTAMPTZ(Date paramDate)
    throws SQLException
  {
    return this.connection.createTIMESTAMPTZ(paramDate);
  }

  public TIMESTAMPTZ createTIMESTAMPTZ(Date paramDate, Calendar paramCalendar)
    throws SQLException
  {
    return this.connection.createTIMESTAMPTZ(paramDate, paramCalendar);
  }

  public TIMESTAMPTZ createTIMESTAMPTZ(Time paramTime)
    throws SQLException
  {
    return this.connection.createTIMESTAMPTZ(paramTime);
  }

  public TIMESTAMPTZ createTIMESTAMPTZ(Time paramTime, Calendar paramCalendar)
    throws SQLException
  {
    return this.connection.createTIMESTAMPTZ(paramTime, paramCalendar);
  }

  public TIMESTAMPTZ createTIMESTAMPTZ(Timestamp paramTimestamp)
    throws SQLException
  {
    return this.connection.createTIMESTAMPTZ(paramTimestamp);
  }

  public TIMESTAMPTZ createTIMESTAMPTZ(Timestamp paramTimestamp, Calendar paramCalendar)
    throws SQLException
  {
    return this.connection.createTIMESTAMPTZ(paramTimestamp, paramCalendar);
  }

  public TIMESTAMPTZ createTIMESTAMPTZ(String paramString)
    throws SQLException
  {
    return this.connection.createTIMESTAMPTZ(paramString);
  }

  public TIMESTAMPTZ createTIMESTAMPTZ(String paramString, Calendar paramCalendar)
    throws SQLException
  {
    return this.connection.createTIMESTAMPTZ(paramString, paramCalendar);
  }

  public TIMESTAMPTZ createTIMESTAMPTZ(DATE paramDATE)
    throws SQLException
  {
    return this.connection.createTIMESTAMPTZ(paramDATE);
  }

  public TIMESTAMPLTZ createTIMESTAMPLTZ(Date paramDate, Calendar paramCalendar)
    throws SQLException
  {
    return this.connection.createTIMESTAMPLTZ(paramDate, paramCalendar);
  }

  public TIMESTAMPLTZ createTIMESTAMPLTZ(Time paramTime, Calendar paramCalendar)
    throws SQLException
  {
    return this.connection.createTIMESTAMPLTZ(paramTime, paramCalendar);
  }

  public TIMESTAMPLTZ createTIMESTAMPLTZ(Timestamp paramTimestamp, Calendar paramCalendar)
    throws SQLException
  {
    return this.connection.createTIMESTAMPLTZ(paramTimestamp, paramCalendar);
  }

  public TIMESTAMPLTZ createTIMESTAMPLTZ(String paramString, Calendar paramCalendar)
    throws SQLException
  {
    return this.connection.createTIMESTAMPLTZ(paramString, paramCalendar);
  }

  public TIMESTAMPLTZ createTIMESTAMPLTZ(DATE paramDATE, Calendar paramCalendar)
    throws SQLException
  {
    return this.connection.createTIMESTAMPLTZ(paramDATE, paramCalendar);
  }

  public Array createArrayOf(String paramString, Object[] paramArrayOfObject)
    throws SQLException
  {
    return this.connection.createArrayOf(paramString, paramArrayOfObject);
  }

  public Blob createBlob() throws SQLException
  {
    return this.connection.createBlob();
  }

  public Clob createClob() throws SQLException
  {
    return this.connection.createClob();
  }

  public NClob createNClob() throws SQLException
  {
    return this.connection.createNClob();
  }

  public SQLXML createSQLXML() throws SQLException
  {
    return this.connection.createSQLXML();
  }

  public Struct createStruct(String paramString, Object[] paramArrayOfObject)
    throws SQLException
  {
    return this.connection.createStruct(paramString, paramArrayOfObject);
  }

  public boolean isValid(int paramInt) throws SQLException
  {
    return this.connection.isValid(paramInt);
  }

  public void setClientInfo(String paramString1, String paramString2)
    throws SQLClientInfoException
  {
    this.connection.setClientInfo(paramString1, paramString2);
  }

  public void setClientInfo(Properties paramProperties) throws SQLClientInfoException
  {
    this.connection.setClientInfo(paramProperties);
  }

  public String getClientInfo(String paramString) throws SQLException
  {
    return this.connection.getClientInfo(paramString);
  }

  public Properties getClientInfo() throws SQLException
  {
    return this.connection.getClientInfo();
  }

  public boolean isWrapperFor(Class<?> paramClass)
    throws SQLException
  {
    if (paramClass.isInterface()) {
      return (paramClass.isInstance(this)) || (this.connection.isWrapperFor(paramClass));
    }

    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 177);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  protected <T> T proxyFor(Object paramObject, Class<T> paramClass)
    throws SQLException
  {
    Class localObject2;
    try
    {
      T localObject1 = (T)this.proxies.get(paramClass);
      if (localObject1 == null) {
        localObject2 = (Class)proxyClasses.get(paramClass);
        if (localObject2 == null) {
          localObject2 = Proxy.getProxyClass(paramClass.getClassLoader(), new Class[] { paramClass });

          proxyClasses.put(paramClass, localObject2);
        }
        localObject1 = (T)localObject2.getConstructor(new Class[] { InvocationHandler.class }).newInstance(new Object[] { new CloseInvocationHandler(this) });

        this.proxies.put(paramClass, localObject1);
      }
      return localObject1;
    }
    catch (Exception localException)
    {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 1, "Cannot construct proxy");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }
  }

  public <T> T unwrap(Class<T> paramClass)
    throws SQLException
  {
    if (paramClass.isInterface()) {
      if (paramClass.isInstance(this)) return (T)this;
      return proxyFor(this.connection.unwrap(paramClass), paramClass);
    }

    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 177);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public DatabaseChangeRegistration registerDatabaseChangeNotification(Properties paramProperties)
    throws SQLException
  {
    return this.connection.registerDatabaseChangeNotification(paramProperties);
  }

  public DatabaseChangeRegistration getDatabaseChangeRegistration(int paramInt)
    throws SQLException
  {
    return this.connection.getDatabaseChangeRegistration(paramInt);
  }

  public void unregisterDatabaseChangeNotification(DatabaseChangeRegistration paramDatabaseChangeRegistration)
    throws SQLException
  {
    this.connection.unregisterDatabaseChangeNotification(paramDatabaseChangeRegistration);
  }

  public void unregisterDatabaseChangeNotification(int paramInt1, String paramString, int paramInt2)
    throws SQLException
  {
    this.connection.unregisterDatabaseChangeNotification(paramInt1, paramString, paramInt2);
  }

  public void unregisterDatabaseChangeNotification(int paramInt)
    throws SQLException
  {
    this.connection.unregisterDatabaseChangeNotification(paramInt);
  }

  public void unregisterDatabaseChangeNotification(long paramLong, String paramString)
    throws SQLException
  {
    this.connection.unregisterDatabaseChangeNotification(paramLong, paramString);
  }

  public AQNotificationRegistration[] registerAQNotification(String[] paramArrayOfString, Properties[] paramArrayOfProperties, Properties paramProperties)
    throws SQLException
  {
    return this.connection.registerAQNotification(paramArrayOfString, paramArrayOfProperties, paramProperties);
  }

  public void unregisterAQNotification(AQNotificationRegistration paramAQNotificationRegistration)
    throws SQLException
  {
    this.connection.unregisterAQNotification(paramAQNotificationRegistration);
  }

  public AQMessage dequeue(String paramString, AQDequeueOptions paramAQDequeueOptions, byte[] paramArrayOfByte)
    throws SQLException
  {
    return this.connection.dequeue(paramString, paramAQDequeueOptions, paramArrayOfByte);
  }

  public AQMessage dequeue(String paramString1, AQDequeueOptions paramAQDequeueOptions, String paramString2)
    throws SQLException
  {
    return this.connection.dequeue(paramString1, paramAQDequeueOptions, paramString2);
  }

  public void enqueue(String paramString, AQEnqueueOptions paramAQEnqueueOptions, AQMessage paramAQMessage)
    throws SQLException
  {
    this.connection.enqueue(paramString, paramAQEnqueueOptions, paramAQMessage);
  }

  public void commit(EnumSet<OracleConnection.CommitOption> paramEnumSet) throws SQLException
  {
    this.connection.commit(paramEnumSet);
  }

  public void cancel() throws SQLException
  {
    this.connection.cancel();
  }

  public void abort() throws SQLException
  {
    this.connection.abort();
  }

  public TypeDescriptor[] getAllTypeDescriptorsInCurrentSchema() throws SQLException
  {
    return this.connection.getAllTypeDescriptorsInCurrentSchema();
  }

  public TypeDescriptor[] getTypeDescriptorsFromListInCurrentSchema(String[] paramArrayOfString) throws SQLException
  {
    return this.connection.getTypeDescriptorsFromListInCurrentSchema(paramArrayOfString);
  }

  public TypeDescriptor[] getTypeDescriptorsFromList(String[][] paramArrayOfString) throws SQLException
  {
    return this.connection.getTypeDescriptorsFromList(paramArrayOfString);
  }

  public String getDataIntegrityAlgorithmName() throws SQLException
  {
    return this.connection.getDataIntegrityAlgorithmName();
  }

  public String getEncryptionAlgorithmName() throws SQLException
  {
    return this.connection.getEncryptionAlgorithmName();
  }

  public String getAuthenticationAdaptorName() throws SQLException
  {
    return this.connection.getAuthenticationAdaptorName();
  }

  public boolean isUsable()
  {
    return this.connection.isUsable();
  }

  protected oracle.jdbc.internal.OracleConnection getConnectionDuringExceptionHandling()
  {
    return null;
  }

  public void setDefaultTimeZone(TimeZone paramTimeZone)
    throws SQLException
  {
    this.connection.setDefaultTimeZone(paramTimeZone);
  }

  public TimeZone getDefaultTimeZone()
    throws SQLException
  {
    return this.connection.getDefaultTimeZone();
  }

  public void setApplicationContext(String paramString1, String paramString2, String paramString3) throws SQLException
  {
    this.connection.setApplicationContext(paramString1, paramString2, paramString3);
  }

  public void clearAllApplicationContext(String paramString) throws SQLException
  {
    this.connection.clearAllApplicationContext(paramString);
  }

  protected class CloseInvocationHandler
    implements InvocationHandler
  {
    private OracleConnectionWrapper wrapper;

    protected CloseInvocationHandler(OracleConnectionWrapper wrapper)
    {
      this.wrapper = wrapper;
    }

    public Object invoke(Object paramObject, Method paramMethod, Object[] paramArrayOfObject)
      throws Throwable
    {
      try
      {
        return paramMethod.invoke(this.wrapper, paramArrayOfObject);
      }
      catch (IllegalArgumentException localIllegalArgumentException) {
      }
      return paramMethod.invoke(this.wrapper.connection, paramArrayOfObject);
    }
  }
}