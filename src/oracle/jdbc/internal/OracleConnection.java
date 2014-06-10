package oracle.jdbc.internal;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;
import javax.transaction.xa.XAResource;
import oracle.jdbc.oracore.OracleTypeADT;
import oracle.jdbc.oracore.OracleTypeCLOB;
import oracle.jdbc.pool.OracleConnectionCacheCallback;
import oracle.jdbc.pool.OraclePooledConnection;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import oracle.sql.BFILE;
import oracle.sql.BLOB;
import oracle.sql.BfileDBAccess;
import oracle.sql.BlobDBAccess;
import oracle.sql.CLOB;
import oracle.sql.ClobDBAccess;
import oracle.sql.CustomDatum;
import oracle.sql.Datum;
import oracle.sql.StructDescriptor;
import oracle.sql.TIMEZONETAB;

public abstract interface OracleConnection extends oracle.jdbc.OracleConnection
{
  public static final String CONNECTION_PROPERTY_LOGON_CAP = "oracle.jdbc.thinLogonCapability";
  public static final String CONNECTION_PROPERTY_LOGON_CAP_DEFAULT = "o5";
  public static final byte CONNECTION_PROPERTY_LOGON_CAP_ACCESSMODE = 3;
  public static final String CONNECTION_PROPERTY_NLS_LANG_BACKDOOR = "oracle.jdbc.ociNlsLangBackwardCompatible";
  public static final String CONNECTION_PROPERTY_NLS_LANG_BACKDOOR_DEFAULT = "false";
  public static final byte CONNECTION_PROPERTY_NLS_LANG_BACKDOOR_ACCESSMODE = 3;
  public static final String CONNECTION_PROPERTY_SPAWN_NEW_THREAD_TO_CANCEL = "oracle.jdbc.spawnNewThreadToCancel";
  public static final String CONNECTION_PROPERTY_SPAWN_NEW_THREAD_TO_CANCEL_DEFAULT = "false";
  public static final byte CONNECTION_PROPERTY_SPAWN_NEW_THREAD_TO_CANCEL_ACCESSMODE = 3;
  public static final String CONNECTION_PROPERTY_OVERRIDE_ENABLE_READ_DATA_IN_LOCATOR = "oracle.jdbc.overrideEnableReadDataInLocator";
  public static final String CONNECTION_PROPERTY_OVERRIDE_ENABLE_READ_DATA_IN_LOCATOR_DEFAULT = "false";
  public static final byte CONNECTION_PROPERTY_OVERRIDE_ENABLE_READ_DATA_IN_LOCATOR_ACCESSMODE = 3;
  public static final String CONNECTION_PROPERTY_PERMIT_TIMESTAMP_DATE_MISMATCH = "oracle.jdbc.internal.permitBindDateDefineTimestampMismatch";
  public static final String CONNECTION_PROPERTY_PERMIT_TIMESTAMP_DATE_MISMATCH_DEFAULT = "false";
  public static final byte CONNECTION_PROPERTY_PERMIT_TIMESTAMP_DATE_MISMATCH_ACCESSMODE = 3;
  public static final int CHAR_TO_ASCII = 0;
  public static final int CHAR_TO_UNICODE = 1;
  public static final int RAW_TO_ASCII = 2;
  public static final int RAW_TO_UNICODE = 3;
  public static final int UNICODE_TO_CHAR = 4;
  public static final int ASCII_TO_CHAR = 5;
  public static final int NONE = 6;
  public static final int JAVACHAR_TO_CHAR = 7;
  public static final int RAW_TO_JAVACHAR = 8;
  public static final int CHAR_TO_JAVACHAR = 9;
  public static final int GLOBAL_TXN = 1;
  public static final int NO_GLOBAL_TXN = 0;

  public abstract short getStructAttrNCsId()
    throws SQLException;

  public abstract Map getTypeMap()
    throws SQLException;

  public abstract Properties getDBAccessProperties()
    throws SQLException;

  public abstract Properties getOCIHandles()
    throws SQLException;

  public abstract String getDatabaseProductVersion()
    throws SQLException;

  public abstract String getURL()
    throws SQLException;

  public abstract short getVersionNumber()
    throws SQLException;

  public abstract Map getJavaObjectTypeMap();

  public abstract void setJavaObjectTypeMap(Map paramMap);

  public abstract byte getInstanceProperty(InstanceProperty paramInstanceProperty)
    throws SQLException;

  public abstract BfileDBAccess createBfileDBAccess()
    throws SQLException;

  public abstract BlobDBAccess createBlobDBAccess()
    throws SQLException;

  public abstract ClobDBAccess createClobDBAccess()
    throws SQLException;

  public abstract void setDefaultFixedString(boolean paramBoolean);

  public abstract boolean getDefaultFixedString();

  public abstract oracle.jdbc.OracleConnection getWrapper();

  public abstract Class classForNameAndSchema(String paramString1, String paramString2)
    throws ClassNotFoundException;

  public abstract void setFDO(byte[] paramArrayOfByte)
    throws SQLException;

  public abstract byte[] getFDO(boolean paramBoolean)
    throws SQLException;

  public abstract boolean getBigEndian()
    throws SQLException;

  public abstract Object getDescriptor(byte[] paramArrayOfByte);

  public abstract void putDescriptor(byte[] paramArrayOfByte, Object paramObject)
    throws SQLException;

  public abstract OracleConnection getPhysicalConnection();

  public abstract void removeDescriptor(String paramString);

  public abstract void removeAllDescriptor();

  public abstract int numberOfDescriptorCacheEntries();

  public abstract Enumeration descriptorCacheKeys();

  public abstract long getTdoCState(String paramString1, String paramString2)
    throws SQLException;

  public abstract BufferCacheStatistics getByteBufferCacheStatistics();

  public abstract BufferCacheStatistics getCharBufferCacheStatistics();

  public abstract Datum toDatum(CustomDatum paramCustomDatum)
    throws SQLException;

  public abstract short getDbCsId()
    throws SQLException;

  public abstract short getJdbcCsId()
    throws SQLException;

  public abstract short getNCharSet();

  public abstract ResultSet newArrayDataResultSet(Datum[] paramArrayOfDatum, long paramLong, int paramInt, Map paramMap)
    throws SQLException;

  public abstract ResultSet newArrayDataResultSet(ARRAY paramARRAY, long paramLong, int paramInt, Map paramMap)
    throws SQLException;

  public abstract ResultSet newArrayLocatorResultSet(ArrayDescriptor paramArrayDescriptor, byte[] paramArrayOfByte, long paramLong, int paramInt, Map paramMap)
    throws SQLException;

  public abstract ResultSetMetaData newStructMetaData(StructDescriptor paramStructDescriptor)
    throws SQLException;

  public abstract void getForm(OracleTypeADT paramOracleTypeADT, OracleTypeCLOB paramOracleTypeCLOB, int paramInt)
    throws SQLException;

  public abstract int CHARBytesToJavaChars(byte[] paramArrayOfByte, int paramInt, char[] paramArrayOfChar)
    throws SQLException;

  public abstract int NCHARBytesToJavaChars(byte[] paramArrayOfByte, int paramInt, char[] paramArrayOfChar)
    throws SQLException;

  public abstract boolean IsNCharFixedWith();

  public abstract short getDriverCharSet();

  public abstract int getC2SNlsRatio();

  public abstract int getMaxCharSize()
    throws SQLException;

  public abstract int getMaxCharbyteSize();

  public abstract int getMaxNCharbyteSize();

  public abstract boolean isCharSetMultibyte(short paramShort);

  public abstract int javaCharsToCHARBytes(char[] paramArrayOfChar, int paramInt, byte[] paramArrayOfByte)
    throws SQLException;

  public abstract int javaCharsToNCHARBytes(char[] paramArrayOfChar, int paramInt, byte[] paramArrayOfByte)
    throws SQLException;

  public abstract void setStartTime(long paramLong)
    throws SQLException;

  public abstract long getStartTime()
    throws SQLException;

  public abstract boolean isStatementCacheInitialized();

  public abstract void getPropertyForPooledConnection(OraclePooledConnection paramOraclePooledConnection)
    throws SQLException;

  public abstract void setTypeMap(Map paramMap)
    throws SQLException;

  public abstract String getProtocolType();

  public abstract Connection getLogicalConnection(OraclePooledConnection paramOraclePooledConnection, boolean paramBoolean)
    throws SQLException;

  public abstract void setTxnMode(int paramInt);

  public abstract int getTxnMode();

  public abstract int getHeapAllocSize()
    throws SQLException;

  public abstract int getOCIEnvHeapAllocSize()
    throws SQLException;

  public abstract void setAbandonedTimeoutEnabled(boolean paramBoolean)
    throws SQLException;

  public abstract int getHeartbeatNoChangeCount()
    throws SQLException;

  public abstract void closeInternal(boolean paramBoolean)
    throws SQLException;

  public abstract void cleanupAndClose(boolean paramBoolean)
    throws SQLException;

  public abstract OracleConnectionCacheCallback getConnectionCacheCallbackObj()
    throws SQLException;

  public abstract Object getConnectionCacheCallbackPrivObj()
    throws SQLException;

  public abstract int getConnectionCacheCallbackFlag()
    throws SQLException;

  public abstract Properties getServerSessionInfo()
    throws SQLException;

  public abstract CLOB createClob(byte[] paramArrayOfByte)
    throws SQLException;

  public abstract CLOB createClobWithUnpickledBytes(byte[] paramArrayOfByte)
    throws SQLException;

  public abstract CLOB createClob(byte[] paramArrayOfByte, short paramShort)
    throws SQLException;

  public abstract BLOB createBlob(byte[] paramArrayOfByte)
    throws SQLException;

  public abstract BLOB createBlobWithUnpickledBytes(byte[] paramArrayOfByte)
    throws SQLException;

  public abstract BFILE createBfile(byte[] paramArrayOfByte)
    throws SQLException;

  public abstract boolean isDescriptorSharable(OracleConnection paramOracleConnection)
    throws SQLException;

  public abstract OracleStatement refCursorCursorToStatement(int paramInt)
    throws SQLException;

  public abstract XAResource getXAResource()
    throws SQLException;

  /** @deprecated */
  public abstract boolean isV8Compatible()
    throws SQLException;

  public abstract boolean getMapDateToTimestamp();

  public abstract boolean isGetObjectReturnsXMLType();

  public abstract byte[] createLightweightSession(String paramString, KeywordValueLong[] paramArrayOfKeywordValueLong, int paramInt, KeywordValueLong[][] paramArrayOfKeywordValueLong1, int[] paramArrayOfInt)
    throws SQLException;

  public abstract void executeLightweightSessionRoundtrip(int paramInt1, byte[] paramArrayOfByte, KeywordValueLong[] paramArrayOfKeywordValueLong, int paramInt2, KeywordValueLong[][] paramArrayOfKeywordValueLong1, int[] paramArrayOfInt)
    throws SQLException;

  public abstract void executeLightweightSessionPiggyback(int paramInt1, byte[] paramArrayOfByte, KeywordValueLong[] paramArrayOfKeywordValueLong, int paramInt2)
    throws SQLException;

  public abstract void doXSNamespaceOp(XSOperationCode paramXSOperationCode, byte[] paramArrayOfByte, XSNamespace[] paramArrayOfXSNamespace, XSNamespace[][] paramArrayOfXSNamespace1)
    throws SQLException;

  public abstract void doXSNamespaceOp(XSOperationCode paramXSOperationCode, byte[] paramArrayOfByte, XSNamespace[] paramArrayOfXSNamespace)
    throws SQLException;

  public abstract String getDefaultSchemaNameForNamedTypes()
    throws SQLException;

  public abstract void setUsable(boolean paramBoolean);

  public abstract Class getClassForType(String paramString, Map<String, Class> paramMap);

  public abstract void addXSEventListener(XSEventListener paramXSEventListener)
    throws SQLException;

  public abstract void addXSEventListener(XSEventListener paramXSEventListener, Executor paramExecutor)
    throws SQLException;

  public abstract void removeXSEventListener(XSEventListener paramXSEventListener)
    throws SQLException;

  public abstract int getTimezoneVersionNumber()
    throws SQLException;

  public abstract TIMEZONETAB getTIMEZONETAB()
    throws SQLException;

  public abstract String getDatabaseTimeZone()
    throws SQLException;

  public abstract boolean getTimestamptzInGmt();

  public abstract boolean isDataInLocatorEnabled()
    throws SQLException;

  public static abstract interface BufferCacheStatistics
  {
    public abstract int getId();

    public abstract int[] getBufferSizes();

    public abstract int getCacheHits(int paramInt);

    public abstract int getCacheMisses(int paramInt);

    public abstract int getBuffersCached(int paramInt);

    public abstract int getBucketsFull(int paramInt);

    public abstract int getReferencesCleared(int paramInt);

    public abstract int getTooBigToCache();
  }

  public static enum XSOperationCode
  {
    NAMESPACE_CREATE(1), 
    NAMESPACE_DELETE(2), 
    ATTRIBUTE_CREATE(3), 
    ATTRIBUTE_SET(4), 
    ATTRIBUTE_DELETE(5), 
    ATTRIBUTE_RESET(6);

    private final int code;

    private XSOperationCode(int paramInt) { this.code = paramInt; }


    public final int getCode()
    {
      return this.code;
    }
  }

  public static enum InstanceProperty
  {
    ASM_VOLUME_SUPPORTED, 

    INSTANCE_TYPE;
  }
}