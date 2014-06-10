package oracle.jdbc.driver;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.EnumSet;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;
import java.util.Vector;
import java.util.concurrent.Executor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.transaction.xa.XAResource;
import oracle.jdbc.OracleConnection.CommitOption;
import oracle.jdbc.OracleConnection.DatabaseShutdownMode;
import oracle.jdbc.OracleConnection.DatabaseStartupMode;
import oracle.jdbc.OracleOCIFailover;
import oracle.jdbc.OracleSQLPermission;
import oracle.jdbc.aq.AQDequeueOptions;
import oracle.jdbc.aq.AQEnqueueOptions;
import oracle.jdbc.aq.AQMessage;
import oracle.jdbc.aq.AQNotificationRegistration;
import oracle.jdbc.dcn.DatabaseChangeRegistration;
import oracle.jdbc.internal.KeywordValueLong;
import oracle.jdbc.internal.OracleConnection.BufferCacheStatistics;
import oracle.jdbc.internal.OracleConnection.InstanceProperty;
import oracle.jdbc.internal.OracleConnection.XSOperationCode;
import oracle.jdbc.internal.XSEventListener;
import oracle.jdbc.internal.XSNamespace;
import oracle.jdbc.oracore.OracleTypeADT;
import oracle.jdbc.oracore.OracleTypeCLOB;
import oracle.jdbc.oracore.Util;
import oracle.jdbc.pool.OracleConnectionCacheCallback;
import oracle.jdbc.pool.OraclePooledConnection;
import oracle.net.nt.CustomSSLSocketFactory;
import oracle.security.pki.OracleSecretStore;
import oracle.security.pki.OracleWallet;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import oracle.sql.BFILE;
import oracle.sql.BINARY_DOUBLE;
import oracle.sql.BINARY_FLOAT;
import oracle.sql.BLOB;
import oracle.sql.BfileDBAccess;
import oracle.sql.BlobDBAccess;
import oracle.sql.CLOB;
import oracle.sql.CharacterSet;
import oracle.sql.ClobDBAccess;
import oracle.sql.CustomDatum;
import oracle.sql.DATE;
import oracle.sql.Datum;
import oracle.sql.INTERVALDS;
import oracle.sql.INTERVALYM;
import oracle.sql.NCLOB;
import oracle.sql.NUMBER;
import oracle.sql.SQLName;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;
import oracle.sql.TIMESTAMP;
import oracle.sql.TIMESTAMPLTZ;
import oracle.sql.TIMESTAMPTZ;
import oracle.sql.TIMEZONETAB;
import oracle.sql.TypeDescriptor;

abstract class PhysicalConnection extends OracleConnection
{
  public static final String SECRET_STORE_CONNECT = "oracle.security.client.connect_string";
  public static final String SECRET_STORE_USERNAME = "oracle.security.client.username";
  public static final String SECRET_STORE_PASSWORD = "oracle.security.client.password";
  public static final String SECRET_STORE_DEFAULT_USERNAME = "oracle.security.client.default_username";
  public static final String SECRET_STORE_DEFAULT_PASSWORD = "oracle.security.client.default_password";
  public static final char slash_character = '/';
  public static final char at_sign_character = '@';
  public static final char left_square_bracket_character = '[';
  public static final char right_square_bracket_character = ']';
  static final byte[] EMPTY_BYTE_ARRAY = new byte[0];

  char[][] charOutput = new char[1][];
  byte[][] byteOutput = new byte[1][];
  short[][] shortOutput = new short[1][];

  Properties sessionProperties = null;
  boolean retainV9BindBehavior;
  String userName;
  String database;
  boolean autocommit;
  String protocol;
  int streamChunkSize;
  boolean setFloatAndDoubleUseBinary;
  String ocidll;
  String thinVsessionTerminal;
  String thinVsessionMachine;
  String thinVsessionOsuser;
  String thinVsessionProgram;
  String thinVsessionProcess;
  String thinVsessionIname;
  String thinVsessionEname;
  String thinNetProfile;
  String thinNetAuthenticationServices;
  String thinNetAuthenticationKrb5Mutual;
  String thinNetAuthenticationKrb5CcName;
  String thinNetEncryptionLevel;
  String thinNetEncryptionTypes;
  String thinNetChecksumLevel;
  String thinNetChecksumTypes;
  String thinNetCryptoSeed;
  boolean thinTcpNoDelay;
  String thinReadTimeout;
  String thinNetConnectTimeout;
  boolean thinNetDisableOutOfBandBreak;
  boolean thinNetUseZeroCopyIO;
  boolean thinNetEnableSDP;
  boolean use1900AsYearForTime;
  boolean timestamptzInGmt;
  boolean timezoneAsRegion;
  String thinSslServerDnMatch;
  String thinSslVersion;
  String thinSslCipherSuites;
  String thinJavaxNetSslKeystore;
  String thinJavaxNetSslKeystoretype;
  String thinJavaxNetSslKeystorepassword;
  String thinJavaxNetSslTruststore;
  String thinJavaxNetSslTruststoretype;
  String thinJavaxNetSslTruststorepassword;
  String thinSslKeymanagerfactoryAlgorithm;
  String thinSslTrustmanagerfactoryAlgorithm;
  String thinNetOldsyntax;
  String thinNamingContextInitial;
  String thinNamingProviderUrl;
  String thinNamingSecurityAuthentication;
  String thinNamingSecurityPrincipal;
  String thinNamingSecurityCredentials;
  String walletLocation;
  String walletPassword;
  String proxyClientName;
  boolean useNio;
  String ociDriverCharset;
  String editionName;
  String logonCap;
  String internalLogon;
  boolean createDescriptorUseCurrentSchemaForSchemaName;
  long ociSvcCtxHandle;
  long ociEnvHandle;
  long ociErrHandle;
  boolean prelimAuth;
  boolean nlsLangBackdoor;
  String setNewPassword;
  boolean spawnNewThreadToCancel;
  int defaultExecuteBatch;
  int defaultRowPrefetch;
  int defaultLobPrefetchSize;
  boolean enableDataInLocator;
  boolean enableReadDataInLocator;
  boolean overrideEnableReadDataInLocator;
  boolean reportRemarks;
  boolean includeSynonyms;
  boolean restrictGettables;
  boolean accumulateBatchResult;
  boolean useFetchSizeWithLongColumn;
  boolean processEscapes;
  boolean fixedString;
  boolean defaultnchar;
  boolean permitTimestampDateMismatch;
  String resourceManagerId;
  boolean disableDefinecolumntype;
  boolean convertNcharLiterals;
  boolean j2ee13Compliant;
  boolean mapDateToTimestamp;
  boolean useThreadLocalBufferCache;
  String driverNameAttribute;
  int maxCachedBufferSize;
  int implicitStatementCacheSize;
  boolean lobStreamPosStandardCompliant;
  boolean getObjectReturnsXmlType;
  boolean isStrictAsciiConversion;
  boolean thinForceDnsLoadBalancing;
  String url;
  String savedUser;
  int commitOption;
  int ociConnectionPoolMinLimit = 0;
  int ociConnectionPoolMaxLimit = 0;
  int ociConnectionPoolIncrement = 0;
  int ociConnectionPoolTimeout = 0;
  boolean ociConnectionPoolNoWait = false;
  boolean ociConnectionPoolTransactionDistributed = false;
  String ociConnectionPoolLogonMode = null;
  boolean ociConnectionPoolIsPooling = false;
  Object ociConnectionPoolObject = null;
  Object ociConnectionPoolConnID = null;
  String ociConnectionPoolProxyType = null;
  Integer ociConnectionPoolProxyNumRoles = Integer.valueOf(0);
  Object ociConnectionPoolProxyRoles = null;
  String ociConnectionPoolProxyUserName = null;
  String ociConnectionPoolProxyPassword = null;
  String ociConnectionPoolProxyDistinguishedName = null;
  Object ociConnectionPoolProxyCertificate = null;

  static NTFManager ntfManager = new NTFManager();

  public int protocolId = -3;
  OracleTimeout timeout;
  DBConversion conversion;
  boolean xaWantsError;
  boolean usingXA;
  int txnMode = 0;
  byte[] fdo;
  Boolean bigEndian;
  OracleStatement statements;
  int lifecycle;
  static final int OPEN = 1;
  static final int CLOSING = 2;
  static final int CLOSED = 4;
  static final int ABORTED = 8;
  static final int BLOCKED = 16;
  boolean clientIdSet = false;
  String clientId = null;
  int txnLevel;
  Map map;
  Map javaObjectMap;
  final Hashtable[] descriptorCacheStack = new Hashtable[2];

  int dci = 0;
  OracleStatement statementHoldingLine;
  oracle.jdbc.OracleDatabaseMetaData databaseMetaData = null;
  LogicalConnection logicalConnectionAttached;
  boolean isProxy = false;

  OracleSql sqlObj = null;

  SQLWarning sqlWarning = null;

  boolean readOnly = false;

  LRUStatementCache statementCache = null;

  boolean clearStatementMetaData = false;

  OracleCloseCallback closeCallback = null;
  Object privateData = null;

  Statement savepointStatement = null;

  boolean isUsable = true;

  TimeZone defaultTimeZone = null;

  final int[] endToEndMaxLength = new int[4];

  boolean endToEndAnyChanged = false;
  final boolean[] endToEndHasChanged = new boolean[4];
  short endToEndECIDSequenceNumber = -32768;
  static final int DMS_NONE = 0;
  static final int DMS_10G = 1;
  static final int DMS_11 = 2;
  String[] endToEndValues = null;
  final int whichDMS = 0;

  oracle.jdbc.OracleConnection wrapper = null;
  int minVcsBindSize;
  int maxRawBytesSql;
  int maxRawBytesPlsql;
  int maxVcsCharsSql;
  int maxVcsNCharsSql;
  int maxVcsBytesPlsql;
  int maxIbtVarcharElementLength;
  String instanceName = null;
  OracleDriverExtension driverExtension;
  static final String uninitializedMarker = "";
  String databaseProductVersion = "";
  short versionNumber = -1;
  int namedTypeAccessorByteLen;
  int refTypeAccessorByteLen;
  CharacterSet setCHARCharSetObj;
  CharacterSet setCHARNCharSetObj;
  boolean plsqlCompilerWarnings = false;

  private static final Pattern driverNameAttributePattern = Pattern.compile("[\\x20-\\x7e]{0,8}");

  private static final OracleSQLPermission CALL_ABORT_PERMISSION = new OracleSQLPermission("callAbort");
  static final String DATABASE_NAME = "DATABASE_NAME";
  static final String SERVER_HOST = "SERVER_HOST";
  static final String INSTANCE_NAME = "INSTANCE_NAME";
  static final String SERVICE_NAME = "SERVICE_NAME";
  Hashtable clientData;
  private BufferCacheStore connectionBufferCacheStore;
  private static ThreadLocal<BufferCacheStore> threadLocalBufferCacheStore;
  private int pingResult;
  String sessionTimeZone = null;
  String databaseTimeZone = null;
  Calendar dbTzCalendar = null;
  static final String RAW_STR = "RAW";
  static final String SYS_RAW_STR = "SYS.RAW";
  static final String SYS_ANYDATA_STR = "SYS.ANYDATA";
  static final String SYS_XMLTYPE_STR = "SYS.XMLTYPE";
  int timeZoneVersionNumber = -1;
  TIMEZONETAB timeZoneTab = null;

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  protected PhysicalConnection()
  {
  }

  PhysicalConnection(String paramString, Properties paramProperties, OracleDriverExtension paramOracleDriverExtension)
    throws SQLException
  {
    readConnectionProperties(paramString, paramProperties);

    this.driverExtension = paramOracleDriverExtension;

    initialize(null, null, null);

    this.logicalConnectionAttached = null;
    try
    {
      needLine();

      logon();

      setAutoCommit(this.autocommit);

      if (getVersionNumber() >= 11202)
      {
        this.minVcsBindSize = 4001;
        this.maxRawBytesSql = 4000;
        this.maxRawBytesPlsql = 32766;
        this.maxVcsCharsSql = 32766;
        this.maxVcsNCharsSql = 32766;
        this.maxVcsBytesPlsql = 32766;
        this.maxIbtVarcharElementLength = 32766;

        this.endToEndMaxLength[0] = 64;
        this.endToEndMaxLength[1] = 64;
        this.endToEndMaxLength[2] = 64;
        this.endToEndMaxLength[3] = 64;
      }
      else if (getVersionNumber() >= 11000)
      {
        this.minVcsBindSize = 4001;
        this.maxRawBytesSql = 4000;
        this.maxRawBytesPlsql = 32766;
        this.maxVcsCharsSql = 32766;
        this.maxVcsNCharsSql = 32766;
        this.maxVcsBytesPlsql = 32766;
        this.maxIbtVarcharElementLength = 32766;

        this.endToEndMaxLength[0] = 32;
        this.endToEndMaxLength[1] = 64;
        this.endToEndMaxLength[2] = 64;
        this.endToEndMaxLength[3] = 48;
      }
      else if (getVersionNumber() >= 10000)
      {
        this.minVcsBindSize = 4001;
        this.maxRawBytesSql = 2000;
        this.maxRawBytesPlsql = 32512;
        this.maxVcsCharsSql = 32766;
        this.maxVcsNCharsSql = 32766;
        this.maxVcsBytesPlsql = 32512;
        this.maxIbtVarcharElementLength = 32766;

        this.endToEndMaxLength[0] = 32;
        this.endToEndMaxLength[1] = 64;
        this.endToEndMaxLength[2] = 64;
        this.endToEndMaxLength[3] = 48;
      }
      else if (getVersionNumber() >= 9200)
      {
        this.minVcsBindSize = 4001;
        this.maxRawBytesSql = 2000;
        this.maxRawBytesPlsql = 32512;
        this.maxVcsCharsSql = 32766;
        this.maxVcsNCharsSql = 32766;
        this.maxVcsBytesPlsql = 32512;
        this.maxIbtVarcharElementLength = 32766;

        this.endToEndMaxLength[0] = 32;
        this.endToEndMaxLength[1] = 64;
        this.endToEndMaxLength[2] = 64;
        this.endToEndMaxLength[3] = 48;
      }
      else
      {
        this.minVcsBindSize = 4001;
        this.maxRawBytesSql = 2000;
        this.maxRawBytesPlsql = 2000;
        this.maxVcsCharsSql = 4000;
        this.maxVcsNCharsSql = 4000;
        this.maxVcsBytesPlsql = 4000;
        this.maxIbtVarcharElementLength = 4000;

        this.endToEndMaxLength[0] = 32;
        this.endToEndMaxLength[1] = 64;
        this.endToEndMaxLength[2] = 64;
        this.endToEndMaxLength[3] = 48;
      }

      initializeSetCHARCharSetObjs();

      if (this.implicitStatementCacheSize > 0) {
        setStatementCacheSize(this.implicitStatementCacheSize);
        setImplicitCachingEnabled(true);
      }

    }
    catch (SQLException localSQLException1)
    {
      this.lifecycle = 2;
      try
      {
        logoff();
      } catch (SQLException localSQLException2) {
      }
      this.lifecycle = 4;

      throw localSQLException1;
    }

    this.txnMode = 0;
  }

  private static final String propertyVariableName(String paramString)
  {
    char[] arrayOfChar = new char[paramString.length()];
    paramString.getChars(0, paramString.length(), arrayOfChar, 0);
    String str = "";
    for (int i = 0; i < arrayOfChar.length; i++)
    {
      if (Character.isUpperCase(arrayOfChar[i]))
        str = str + "_";
      str = str + Character.toUpperCase(arrayOfChar[i]);
    }
    return str;
  }

  private void initializeUserDefaults(Properties paramProperties)
  {
    for (String str : OracleDriver.DEFAULT_CONNECTION_PROPERTIES.stringPropertyNames())
      if (!paramProperties.containsKey(str))
        paramProperties.setProperty(str, OracleDriver.DEFAULT_CONNECTION_PROPERTIES.getProperty(str));
  }

  private void readConnectionProperties(String paramString, Properties paramProperties)
    throws SQLException
  {
    initializeUserDefaults(paramProperties);

    String str1 = null;

    str1 = null;
    if (paramProperties != null)
    {
      str1 = paramProperties.getProperty("oracle.jdbc.RetainV9LongBindBehavior");
    }
    if (str1 == null)
      str1 = getSystemProperty("oracle.jdbc.RetainV9LongBindBehavior", null);
    if (str1 == null) {
      str1 = "false";
    }

    this.retainV9BindBehavior = ((str1 != null) && (str1.equalsIgnoreCase("true")));

    str1 = null;
    if (paramProperties != null)
    {
      str1 = paramProperties.getProperty("user");
      if (str1 == null)
        str1 = paramProperties.getProperty("oracle.jdbc.user");
    }
    if (str1 == null)
      str1 = getSystemProperty("oracle.jdbc.user", null);
    if (str1 == null) {
      str1 = null;
    }

    this.userName = str1;

    str1 = null;
    if (paramProperties != null)
    {
      str1 = paramProperties.getProperty("database");
      if (str1 == null)
        str1 = paramProperties.getProperty("oracle.jdbc.database");
    }
    if (str1 == null)
      str1 = getSystemProperty("oracle.jdbc.database", null);
    if (str1 == null) {
      str1 = null;
    }

    this.database = str1;

    str1 = null;
    if (paramProperties != null)
    {
      str1 = paramProperties.getProperty("autoCommit");
      if (str1 == null)
        str1 = paramProperties.getProperty("oracle.jdbc.autoCommit");
    }
    if (str1 == null)
      str1 = getSystemProperty("oracle.jdbc.autoCommit", null);
    if (str1 == null) {
      str1 = "true";
    }

    this.autocommit = ((str1 != null) && (str1.equalsIgnoreCase("true")));

    str1 = null;
    if (paramProperties != null)
    {
      str1 = paramProperties.getProperty("protocol");
      if (str1 == null)
        str1 = paramProperties.getProperty("oracle.jdbc.protocol");
    }
    if (str1 == null)
      str1 = getSystemProperty("oracle.jdbc.protocol", null);
    if (str1 == null) {
      str1 = null;
    }

    this.protocol = str1;

    str1 = null;
    if (paramProperties != null)
    {
      str1 = paramProperties.getProperty("oracle.jdbc.StreamChunkSize");
    }
    if (str1 == null)
      str1 = getSystemProperty("oracle.jdbc.StreamChunkSize", null);
    if (str1 == null)
      str1 = "16384";
    try
    {
      this.streamChunkSize = Integer.parseInt(str1);
    }
    catch (NumberFormatException localNumberFormatException1)
    {
      localObject2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 190, "Property is 'streamChunkSize'");
      ((SQLException)localObject2).fillInStackTrace();
      throw ((Throwable)localObject2);
    }

    str1 = null;
    if (paramProperties != null)
    {
      str1 = paramProperties.getProperty("SetFloatAndDoubleUseBinary");
      if (str1 == null)
        str1 = paramProperties.getProperty("oracle.jdbc.SetFloatAndDoubleUseBinary");
    }
    if (str1 == null)
      str1 = getSystemProperty("oracle.jdbc.SetFloatAndDoubleUseBinary", null);
    if (str1 == null) {
      str1 = "false";
    }

    this.setFloatAndDoubleUseBinary = ((str1 != null) && (str1.equalsIgnoreCase("true")));

    str1 = null;
    if (paramProperties != null)
    {
      str1 = paramProperties.getProperty("oracle.jdbc.ocinativelibrary");
    }
    if (str1 == null)
      str1 = getSystemProperty("oracle.jdbc.ocinativelibrary", null);
    if (str1 == null) {
      str1 = null;
    }

    this.ocidll = str1;

    str1 = null;
    if (paramProperties != null)
    {
      str1 = paramProperties.getProperty("v$session.terminal");
      if (str1 == null)
        str1 = paramProperties.getProperty("oracle.jdbc.v$session.terminal");
    }
    if (str1 == null)
      str1 = getSystemProperty("oracle.jdbc.v$session.terminal", null);
    if (str1 == null) {
      str1 = "unknown";
    }

    this.thinVsessionTerminal = str1;

    str1 = null;
    if (paramProperties != null)
    {
      str1 = paramProperties.getProperty("v$session.machine");
      if (str1 == null)
        str1 = paramProperties.getProperty("oracle.jdbc.v$session.machine");
    }
    if (str1 == null)
      str1 = getSystemProperty("oracle.jdbc.v$session.machine", null);
    if (str1 == null) {
      str1 = null;
    }

    this.thinVsessionMachine = str1;

    str1 = null;
    if (paramProperties != null)
    {
      str1 = paramProperties.getProperty("v$session.osuser");
      if (str1 == null)
        str1 = paramProperties.getProperty("oracle.jdbc.v$session.osuser");
    }
    if (str1 == null)
      str1 = getSystemProperty("oracle.jdbc.v$session.osuser", null);
    if (str1 == null) {
      str1 = null;
    }

    this.thinVsessionOsuser = str1;

    str1 = null;
    if (paramProperties != null)
    {
      str1 = paramProperties.getProperty("v$session.program");
      if (str1 == null)
        str1 = paramProperties.getProperty("oracle.jdbc.v$session.program");
    }
    if (str1 == null)
      str1 = getSystemProperty("oracle.jdbc.v$session.program", null);
    if (str1 == null) {
      str1 = "JDBC Thin Client";
    }

    this.thinVsessionProgram = str1;

    str1 = null;
    if (paramProperties != null)
    {
      str1 = paramProperties.getProperty("v$session.process");
      if (str1 == null)
        str1 = paramProperties.getProperty("oracle.jdbc.v$session.process");
    }
    if (str1 == null)
      str1 = getSystemProperty("oracle.jdbc.v$session.process", null);
    if (str1 == null) {
      str1 = "1234";
    }

    this.thinVsessionProcess = str1;

    str1 = null;
    if (paramProperties != null)
    {
      str1 = paramProperties.getProperty("v$session.iname");
      if (str1 == null)
        str1 = paramProperties.getProperty("oracle.jdbc.v$session.iname");
    }
    if (str1 == null)
      str1 = getSystemProperty("oracle.jdbc.v$session.iname", null);
    if (str1 == null) {
      str1 = "jdbc_ttc_impl";
    }

    this.thinVsessionIname = str1;

    str1 = null;
    if (paramProperties != null)
    {
      str1 = paramProperties.getProperty("v$session.ename");
      if (str1 == null)
        str1 = paramProperties.getProperty("oracle.jdbc.v$session.ename");
    }
    if (str1 == null)
      str1 = getSystemProperty("oracle.jdbc.v$session.ename", null);
    if (str1 == null) {
      str1 = null;
    }

    this.thinVsessionEname = str1;

    str1 = null;
    if (paramProperties != null)
    {
      str1 = paramProperties.getProperty("oracle.net.profile");
    }
    if (str1 == null)
      str1 = getSystemProperty("oracle.net.profile", null);
    if (str1 == null) {
      str1 = null;
    }

    this.thinNetProfile = str1;

    str1 = null;
    if (paramProperties != null)
    {
      str1 = paramProperties.getProperty("oracle.net.authentication_services");
    }
    if (str1 == null)
      str1 = getSystemProperty("oracle.net.authentication_services", null);
    if (str1 == null) {
      str1 = null;
    }

    this.thinNetAuthenticationServices = str1;

    str1 = null;
    if (paramProperties != null)
    {
      str1 = paramProperties.getProperty("oracle.net.kerberos5_mutual_authentication");
    }
    if (str1 == null)
      str1 = getSystemProperty("oracle.net.kerberos5_mutual_authentication", null);
    if (str1 == null) {
      str1 = null;
    }

    this.thinNetAuthenticationKrb5Mutual = str1;

    str1 = null;
    if (paramProperties != null)
    {
      str1 = paramProperties.getProperty("oracle.net.kerberos5_cc_name");
    }
    if (str1 == null)
      str1 = getSystemProperty("oracle.net.kerberos5_cc_name", null);
    if (str1 == null) {
      str1 = null;
    }

    this.thinNetAuthenticationKrb5CcName = str1;

    str1 = null;
    if (paramProperties != null)
    {
      str1 = paramProperties.getProperty("oracle.net.encryption_client");
    }
    if (str1 == null)
      str1 = getSystemProperty("oracle.net.encryption_client", null);
    if (str1 == null) {
      str1 = null;
    }

    this.thinNetEncryptionLevel = str1;

    str1 = null;
    if (paramProperties != null)
    {
      str1 = paramProperties.getProperty("oracle.net.encryption_types_client");
    }
    if (str1 == null)
      str1 = getSystemProperty("oracle.net.encryption_types_client", null);
    if (str1 == null) {
      str1 = null;
    }

    this.thinNetEncryptionTypes = str1;

    str1 = null;
    if (paramProperties != null)
    {
      str1 = paramProperties.getProperty("oracle.net.crypto_checksum_client");
    }
    if (str1 == null)
      str1 = getSystemProperty("oracle.net.crypto_checksum_client", null);
    if (str1 == null) {
      str1 = null;
    }

    this.thinNetChecksumLevel = str1;

    str1 = null;
    if (paramProperties != null)
    {
      str1 = paramProperties.getProperty("oracle.net.crypto_checksum_types_client");
    }
    if (str1 == null)
      str1 = getSystemProperty("oracle.net.crypto_checksum_types_client", null);
    if (str1 == null) {
      str1 = null;
    }

    this.thinNetChecksumTypes = str1;

    str1 = null;
    if (paramProperties != null)
    {
      str1 = paramProperties.getProperty("oracle.net.crypto_seed");
    }
    if (str1 == null)
      str1 = getSystemProperty("oracle.net.crypto_seed", null);
    if (str1 == null) {
      str1 = null;
    }

    this.thinNetCryptoSeed = str1;

    str1 = null;
    if (paramProperties != null)
    {
      str1 = paramProperties.getProperty("oracle.jdbc.TcpNoDelay");
    }
    if (str1 == null)
      str1 = getSystemProperty("oracle.jdbc.TcpNoDelay", null);
    if (str1 == null) {
      str1 = "false";
    }

    this.thinTcpNoDelay = ((str1 != null) && (str1.equalsIgnoreCase("true")));

    str1 = null;
    if (paramProperties != null)
    {
      str1 = paramProperties.getProperty("oracle.jdbc.ReadTimeout");
    }
    if (str1 == null)
      str1 = getSystemProperty("oracle.jdbc.ReadTimeout", null);
    if (str1 == null) {
      str1 = null;
    }

    this.thinReadTimeout = str1;

    str1 = null;
    if (paramProperties != null)
    {
      str1 = paramProperties.getProperty("oracle.net.CONNECT_TIMEOUT");
    }
    if (str1 == null)
      str1 = getSystemProperty("oracle.net.CONNECT_TIMEOUT", null);
    if (str1 == null) {
      str1 = null;
    }

    this.thinNetConnectTimeout = str1;

    str1 = null;
    if (paramProperties != null)
    {
      str1 = paramProperties.getProperty("oracle.net.disableOob");
    }
    if (str1 == null)
      str1 = getSystemProperty("oracle.net.disableOob", null);
    if (str1 == null) {
      str1 = "false";
    }

    this.thinNetDisableOutOfBandBreak = ((str1 != null) && (str1.equalsIgnoreCase("true")));

    str1 = null;
    if (paramProperties != null)
    {
      str1 = paramProperties.getProperty("oracle.net.useZeroCopyIO");
    }
    if (str1 == null)
      str1 = getSystemProperty("oracle.net.useZeroCopyIO", null);
    if (str1 == null) {
      str1 = "true";
    }

    this.thinNetUseZeroCopyIO = ((str1 != null) && (str1.equalsIgnoreCase("true")));

    str1 = null;
    if (paramProperties != null)
    {
      str1 = paramProperties.getProperty("oracle.net.SDP");
    }
    if (str1 == null)
      str1 = getSystemProperty("oracle.net.SDP", null);
    if (str1 == null) {
      str1 = "false";
    }

    this.thinNetEnableSDP = ((str1 != null) && (str1.equalsIgnoreCase("true")));

    str1 = null;
    if (paramProperties != null)
    {
      str1 = paramProperties.getProperty("oracle.jdbc.use1900AsYearForTime");
    }
    if (str1 == null)
      str1 = getSystemProperty("oracle.jdbc.use1900AsYearForTime", null);
    if (str1 == null) {
      str1 = "false";
    }

    this.use1900AsYearForTime = ((str1 != null) && (str1.equalsIgnoreCase("true")));

    str1 = null;
    if (paramProperties != null)
    {
      str1 = paramProperties.getProperty("oracle.jdbc.timestampTzInGmt");
    }
    if (str1 == null)
      str1 = getSystemProperty("oracle.jdbc.timestampTzInGmt", null);
    if (str1 == null) {
      str1 = "true";
    }

    this.timestamptzInGmt = ((str1 != null) && (str1.equalsIgnoreCase("true")));

    str1 = null;
    if (paramProperties != null)
    {
      str1 = paramProperties.getProperty("oracle.jdbc.timezoneAsRegion");
    }
    if (str1 == null)
      str1 = getSystemProperty("oracle.jdbc.timezoneAsRegion", null);
    if (str1 == null) {
      str1 = "true";
    }

    this.timezoneAsRegion = ((str1 != null) && (str1.equalsIgnoreCase("true")));

    str1 = null;
    if (paramProperties != null)
    {
      str1 = paramProperties.getProperty("oracle.net.ssl_server_dn_match");
    }
    if (str1 == null)
      str1 = getSystemProperty("oracle.net.ssl_server_dn_match", null);
    if (str1 == null) {
      str1 = null;
    }

    this.thinSslServerDnMatch = str1;

    str1 = null;
    if (paramProperties != null)
    {
      str1 = paramProperties.getProperty("oracle.net.ssl_version");
    }
    if (str1 == null)
      str1 = getSystemProperty("oracle.net.ssl_version", null);
    if (str1 == null) {
      str1 = null;
    }

    this.thinSslVersion = str1;

    str1 = null;
    if (paramProperties != null)
    {
      str1 = paramProperties.getProperty("oracle.net.ssl_cipher_suites");
    }
    if (str1 == null)
      str1 = getSystemProperty("oracle.net.ssl_cipher_suites", null);
    if (str1 == null) {
      str1 = null;
    }

    this.thinSslCipherSuites = str1;

    str1 = null;
    if (paramProperties != null)
    {
      str1 = paramProperties.getProperty("javax.net.ssl.keyStore");
    }
    if (str1 == null)
      str1 = getSystemProperty("javax.net.ssl.keyStore", null);
    if (str1 == null) {
      str1 = null;
    }

    this.thinJavaxNetSslKeystore = str1;

    str1 = null;
    if (paramProperties != null)
    {
      str1 = paramProperties.getProperty("javax.net.ssl.keyStoreType");
    }
    if (str1 == null)
      str1 = getSystemProperty("javax.net.ssl.keyStoreType", null);
    if (str1 == null) {
      str1 = null;
    }

    this.thinJavaxNetSslKeystoretype = str1;

    str1 = null;
    if (paramProperties != null)
    {
      str1 = paramProperties.getProperty("javax.net.ssl.keyStorePassword");
    }
    if (str1 == null)
      str1 = getSystemProperty("javax.net.ssl.keyStorePassword", null);
    if (str1 == null) {
      str1 = null;
    }

    this.thinJavaxNetSslKeystorepassword = str1;

    str1 = null;
    if (paramProperties != null)
    {
      str1 = paramProperties.getProperty("javax.net.ssl.trustStore");
    }
    if (str1 == null)
      str1 = getSystemProperty("javax.net.ssl.trustStore", null);
    if (str1 == null) {
      str1 = null;
    }

    this.thinJavaxNetSslTruststore = str1;

    str1 = null;
    if (paramProperties != null)
    {
      str1 = paramProperties.getProperty("javax.net.ssl.trustStoreType");
    }
    if (str1 == null)
      str1 = getSystemProperty("javax.net.ssl.trustStoreType", null);
    if (str1 == null) {
      str1 = null;
    }

    this.thinJavaxNetSslTruststoretype = str1;

    str1 = null;
    if (paramProperties != null)
    {
      str1 = paramProperties.getProperty("javax.net.ssl.trustStorePassword");
    }
    if (str1 == null)
      str1 = getSystemProperty("javax.net.ssl.trustStorePassword", null);
    if (str1 == null) {
      str1 = null;
    }

    this.thinJavaxNetSslTruststorepassword = str1;

    str1 = null;
    if (paramProperties != null)
    {
      str1 = paramProperties.getProperty("ssl.keyManagerFactory.algorithm");
      if (str1 == null)
        str1 = paramProperties.getProperty("oracle.jdbc.ssl.keyManagerFactory.algorithm");
    }
    if (str1 == null)
      str1 = getSystemProperty("oracle.jdbc.ssl.keyManagerFactory.algorithm", null);
    if (str1 == null) {
      str1 = null;
    }

    this.thinSslKeymanagerfactoryAlgorithm = str1;

    str1 = null;
    if (paramProperties != null)
    {
      str1 = paramProperties.getProperty("ssl.trustManagerFactory.algorithm");
      if (str1 == null)
        str1 = paramProperties.getProperty("oracle.jdbc.ssl.trustManagerFactory.algorithm");
    }
    if (str1 == null)
      str1 = getSystemProperty("oracle.jdbc.ssl.trustManagerFactory.algorithm", null);
    if (str1 == null) {
      str1 = null;
    }

    this.thinSslTrustmanagerfactoryAlgorithm = str1;

    str1 = null;
    if (paramProperties != null)
    {
      str1 = paramProperties.getProperty("oracle.net.oldSyntax");
    }
    if (str1 == null)
      str1 = getSystemProperty("oracle.net.oldSyntax", null);
    if (str1 == null) {
      str1 = null;
    }

    this.thinNetOldsyntax = str1;

    str1 = null;
    if (paramProperties != null)
    {
      str1 = paramProperties.getProperty("java.naming.factory.initial");
    }
    if (str1 == null) {
      str1 = null;
    }

    this.thinNamingContextInitial = str1;

    str1 = null;
    if (paramProperties != null)
    {
      str1 = paramProperties.getProperty("java.naming.provider.url");
    }
    if (str1 == null) {
      str1 = null;
    }

    this.thinNamingProviderUrl = str1;

    str1 = null;
    if (paramProperties != null)
    {
      str1 = paramProperties.getProperty("java.naming.security.authentication");
    }
    if (str1 == null) {
      str1 = null;
    }

    this.thinNamingSecurityAuthentication = str1;

    str1 = null;
    if (paramProperties != null)
    {
      str1 = paramProperties.getProperty("java.naming.security.principal");
    }
    if (str1 == null) {
      str1 = null;
    }

    this.thinNamingSecurityPrincipal = str1;

    str1 = null;
    if (paramProperties != null)
    {
      str1 = paramProperties.getProperty("java.naming.security.credentials");
    }
    if (str1 == null) {
      str1 = null;
    }

    this.thinNamingSecurityCredentials = str1;

    str1 = null;
    if (paramProperties != null)
    {
      str1 = paramProperties.getProperty("oracle.net.wallet_location");
    }
    if (str1 == null)
      str1 = getSystemProperty("oracle.net.wallet_location", null);
    if (str1 == null) {
      str1 = null;
    }

    this.walletLocation = str1;

    str1 = null;
    if (paramProperties != null)
    {
      str1 = paramProperties.getProperty("oracle.net.wallet_password");
    }
    if (str1 == null)
      str1 = getSystemProperty("oracle.net.wallet_password", null);
    if (str1 == null) {
      str1 = null;
    }

    this.walletPassword = str1;

    str1 = null;
    if (paramProperties != null)
    {
      str1 = paramProperties.getProperty("oracle.jdbc.proxyClientName");
    }
    if (str1 == null)
      str1 = getSystemProperty("oracle.jdbc.proxyClientName", null);
    if (str1 == null) {
      str1 = null;
    }

    this.proxyClientName = str1;

    str1 = null;
    if (paramProperties != null)
    {
      str1 = paramProperties.getProperty("oracle.jdbc.useNio");
    }
    if (str1 == null)
      str1 = getSystemProperty("oracle.jdbc.useNio", null);
    if (str1 == null) {
      str1 = "false";
    }

    this.useNio = ((str1 != null) && (str1.equalsIgnoreCase("true")));

    str1 = null;
    if (paramProperties != null)
    {
      str1 = paramProperties.getProperty("JDBCDriverCharSetId");
      if (str1 == null)
        str1 = paramProperties.getProperty("oracle.jdbc.JDBCDriverCharSetId");
    }
    if (str1 == null)
      str1 = getSystemProperty("oracle.jdbc.JDBCDriverCharSetId", null);
    if (str1 == null) {
      str1 = null;
    }

    this.ociDriverCharset = str1;

    str1 = null;
    if (paramProperties != null)
    {
      str1 = paramProperties.getProperty("oracle.jdbc.editionName");
    }
    if (str1 == null)
      str1 = getSystemProperty("oracle.jdbc.editionName", null);
    if (str1 == null) {
      str1 = null;
    }

    this.editionName = str1;

    str1 = null;
    if (paramProperties != null)
    {
      str1 = paramProperties.getProperty("oracle.jdbc.thinLogonCapability");
    }
    if (str1 == null)
      str1 = getSystemProperty("oracle.jdbc.thinLogonCapability", null);
    if (str1 == null) {
      str1 = "o5";
    }

    this.logonCap = str1;

    str1 = null;
    if (paramProperties != null)
    {
      str1 = paramProperties.getProperty("internal_logon");
      if (str1 == null)
        str1 = paramProperties.getProperty("oracle.jdbc.internal_logon");
    }
    if (str1 == null)
      str1 = getSystemProperty("oracle.jdbc.internal_logon", null);
    if (str1 == null) {
      str1 = null;
    }

    this.internalLogon = str1;

    str1 = null;
    if (paramProperties != null)
    {
      str1 = paramProperties.getProperty("oracle.jdbc.createDescriptorUseCurrentSchemaForSchemaName");
    }
    if (str1 == null)
      str1 = getSystemProperty("oracle.jdbc.createDescriptorUseCurrentSchemaForSchemaName", null);
    if (str1 == null) {
      str1 = "false";
    }

    this.createDescriptorUseCurrentSchemaForSchemaName = ((str1 != null) && (str1.equalsIgnoreCase("true")));

    str1 = null;
    if (paramProperties != null)
    {
      str1 = paramProperties.getProperty("OCISvcCtxHandle");
      if (str1 == null)
        str1 = paramProperties.getProperty("oracle.jdbc.OCISvcCtxHandle");
    }
    if (str1 == null)
      str1 = getSystemProperty("oracle.jdbc.OCISvcCtxHandle", null);
    if (str1 == null)
      str1 = "0";
    try
    {
      this.ociSvcCtxHandle = Long.parseLong(str1);
    }
    catch (NumberFormatException localNumberFormatException2)
    {
      localObject2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 190, "Property is 'ociSvcCtxHandle'");
      ((SQLException)localObject2).fillInStackTrace();
      throw ((Throwable)localObject2);
    }

    str1 = null;
    if (paramProperties != null)
    {
      str1 = paramProperties.getProperty("OCIEnvHandle");
      if (str1 == null)
        str1 = paramProperties.getProperty("oracle.jdbc.OCIEnvHandle");
    }
    if (str1 == null)
      str1 = getSystemProperty("oracle.jdbc.OCIEnvHandle", null);
    if (str1 == null)
      str1 = "0";
    try
    {
      this.ociEnvHandle = Long.parseLong(str1);
    }
    catch (NumberFormatException localNumberFormatException3)
    {
      localObject2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 190, "Property is 'ociEnvHandle'");
      ((SQLException)localObject2).fillInStackTrace();
      throw ((Throwable)localObject2);
    }

    str1 = null;
    if (paramProperties != null)
    {
      str1 = paramProperties.getProperty("OCIErrHandle");
      if (str1 == null)
        str1 = paramProperties.getProperty("oracle.jdbc.OCIErrHandle");
    }
    if (str1 == null)
      str1 = getSystemProperty("oracle.jdbc.OCIErrHandle", null);
    if (str1 == null)
      str1 = "0";
    try
    {
      this.ociErrHandle = Long.parseLong(str1);
    }
    catch (NumberFormatException localNumberFormatException4)
    {
      localObject2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 190, "Property is 'ociErrHandle'");
      ((SQLException)localObject2).fillInStackTrace();
      throw ((Throwable)localObject2);
    }

    str1 = null;
    if (paramProperties != null)
    {
      str1 = paramProperties.getProperty("prelim_auth");
      if (str1 == null)
        str1 = paramProperties.getProperty("oracle.jdbc.prelim_auth");
    }
    if (str1 == null)
      str1 = getSystemProperty("oracle.jdbc.prelim_auth", null);
    if (str1 == null) {
      str1 = "false";
    }

    this.prelimAuth = ((str1 != null) && (str1.equalsIgnoreCase("true")));

    str1 = null;
    if (paramProperties != null)
    {
      str1 = paramProperties.getProperty("oracle.jdbc.ociNlsLangBackwardCompatible");
    }
    if (str1 == null)
      str1 = getSystemProperty("oracle.jdbc.ociNlsLangBackwardCompatible", null);
    if (str1 == null) {
      str1 = "false";
    }

    this.nlsLangBackdoor = ((str1 != null) && (str1.equalsIgnoreCase("true")));

    str1 = null;
    if (paramProperties != null)
    {
      str1 = paramProperties.getProperty("OCINewPassword");
      if (str1 == null)
        str1 = paramProperties.getProperty("oracle.jdbc.OCINewPassword");
    }
    if (str1 == null)
      str1 = getSystemProperty("oracle.jdbc.OCINewPassword", null);
    if (str1 == null) {
      str1 = null;
    }

    this.setNewPassword = str1;

    str1 = null;
    if (paramProperties != null)
    {
      str1 = paramProperties.getProperty("oracle.jdbc.spawnNewThreadToCancel");
    }
    if (str1 == null)
      str1 = getSystemProperty("oracle.jdbc.spawnNewThreadToCancel", null);
    if (str1 == null) {
      str1 = "false";
    }

    this.spawnNewThreadToCancel = ((str1 != null) && (str1.equalsIgnoreCase("true")));

    str1 = null;
    if (paramProperties != null)
    {
      str1 = paramProperties.getProperty("defaultExecuteBatch");
      if (str1 == null)
        str1 = paramProperties.getProperty("oracle.jdbc.defaultExecuteBatch");
    }
    if (str1 == null)
      str1 = getSystemProperty("oracle.jdbc.defaultExecuteBatch", null);
    if (str1 == null)
      str1 = "1";
    try
    {
      this.defaultExecuteBatch = Integer.parseInt(str1);
    }
    catch (NumberFormatException localNumberFormatException5)
    {
      localObject2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 190, "Property is 'defaultExecuteBatch'");
      ((SQLException)localObject2).fillInStackTrace();
      throw ((Throwable)localObject2);
    }

    str1 = null;
    if (paramProperties != null)
    {
      str1 = paramProperties.getProperty("defaultRowPrefetch");
      if (str1 == null)
        str1 = paramProperties.getProperty("oracle.jdbc.defaultRowPrefetch");
    }
    if (str1 == null)
      str1 = getSystemProperty("oracle.jdbc.defaultRowPrefetch", null);
    if (str1 == null)
      str1 = "10";
    try
    {
      this.defaultRowPrefetch = Integer.parseInt(str1);
    }
    catch (NumberFormatException localNumberFormatException6)
    {
      localObject2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 190, "Property is 'defaultRowPrefetch'");
      ((SQLException)localObject2).fillInStackTrace();
      throw ((Throwable)localObject2);
    }

    str1 = null;
    if (paramProperties != null)
    {
      str1 = paramProperties.getProperty("oracle.jdbc.defaultLobPrefetchSize");
    }
    if (str1 == null)
      str1 = getSystemProperty("oracle.jdbc.defaultLobPrefetchSize", null);
    if (str1 == null)
      str1 = "4000";
    try
    {
      this.defaultLobPrefetchSize = Integer.parseInt(str1);
    }
    catch (NumberFormatException localNumberFormatException7)
    {
      localObject2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 190, "Property is 'defaultLobPrefetchSize'");
      ((SQLException)localObject2).fillInStackTrace();
      throw ((Throwable)localObject2);
    }

    str1 = null;
    if (paramProperties != null)
    {
      str1 = paramProperties.getProperty("oracle.jdbc.enableDataInLocator");
    }
    if (str1 == null)
      str1 = getSystemProperty("oracle.jdbc.enableDataInLocator", null);
    if (str1 == null) {
      str1 = "true";
    }

    this.enableDataInLocator = ((str1 != null) && (str1.equalsIgnoreCase("true")));

    str1 = null;
    if (paramProperties != null)
    {
      str1 = paramProperties.getProperty("oracle.jdbc.enableReadDataInLocator");
    }
    if (str1 == null)
      str1 = getSystemProperty("oracle.jdbc.enableReadDataInLocator", null);
    if (str1 == null) {
      str1 = "true";
    }

    this.enableReadDataInLocator = ((str1 != null) && (str1.equalsIgnoreCase("true")));

    str1 = null;
    if (paramProperties != null)
    {
      str1 = paramProperties.getProperty("oracle.jdbc.overrideEnableReadDataInLocator");
    }
    if (str1 == null)
      str1 = getSystemProperty("oracle.jdbc.overrideEnableReadDataInLocator", null);
    if (str1 == null) {
      str1 = "false";
    }

    this.overrideEnableReadDataInLocator = ((str1 != null) && (str1.equalsIgnoreCase("true")));

    str1 = null;
    if (paramProperties != null)
    {
      str1 = paramProperties.getProperty("remarksReporting");
      if (str1 == null)
        str1 = paramProperties.getProperty("oracle.jdbc.remarksReporting");
    }
    if (str1 == null)
      str1 = getSystemProperty("oracle.jdbc.remarksReporting", null);
    if (str1 == null) {
      str1 = "false";
    }

    this.reportRemarks = ((str1 != null) && (str1.equalsIgnoreCase("true")));

    str1 = null;
    if (paramProperties != null)
    {
      str1 = paramProperties.getProperty("includeSynonyms");
      if (str1 == null)
        str1 = paramProperties.getProperty("oracle.jdbc.includeSynonyms");
    }
    if (str1 == null)
      str1 = getSystemProperty("oracle.jdbc.includeSynonyms", null);
    if (str1 == null) {
      str1 = "false";
    }

    this.includeSynonyms = ((str1 != null) && (str1.equalsIgnoreCase("true")));

    str1 = null;
    if (paramProperties != null)
    {
      str1 = paramProperties.getProperty("restrictGetTables");
      if (str1 == null)
        str1 = paramProperties.getProperty("oracle.jdbc.restrictGetTables");
    }
    if (str1 == null)
      str1 = getSystemProperty("oracle.jdbc.restrictGetTables", null);
    if (str1 == null) {
      str1 = "false";
    }

    this.restrictGettables = ((str1 != null) && (str1.equalsIgnoreCase("true")));

    str1 = null;
    if (paramProperties != null)
    {
      str1 = paramProperties.getProperty("AccumulateBatchResult");
      if (str1 == null)
        str1 = paramProperties.getProperty("oracle.jdbc.AccumulateBatchResult");
    }
    if (str1 == null)
      str1 = getSystemProperty("oracle.jdbc.AccumulateBatchResult", null);
    if (str1 == null) {
      str1 = "true";
    }

    this.accumulateBatchResult = ((str1 != null) && (str1.equalsIgnoreCase("true")));

    str1 = null;
    if (paramProperties != null)
    {
      str1 = paramProperties.getProperty("useFetchSizeWithLongColumn");
      if (str1 == null)
        str1 = paramProperties.getProperty("oracle.jdbc.useFetchSizeWithLongColumn");
    }
    if (str1 == null)
      str1 = getSystemProperty("oracle.jdbc.useFetchSizeWithLongColumn", null);
    if (str1 == null) {
      str1 = "false";
    }

    this.useFetchSizeWithLongColumn = ((str1 != null) && (str1.equalsIgnoreCase("true")));

    str1 = null;
    if (paramProperties != null)
    {
      str1 = paramProperties.getProperty("processEscapes");
      if (str1 == null)
        str1 = paramProperties.getProperty("oracle.jdbc.processEscapes");
    }
    if (str1 == null)
      str1 = getSystemProperty("oracle.jdbc.processEscapes", null);
    if (str1 == null) {
      str1 = "true";
    }

    this.processEscapes = ((str1 != null) && (str1.equalsIgnoreCase("true")));

    str1 = null;
    if (paramProperties != null)
    {
      str1 = paramProperties.getProperty("fixedString");
      if (str1 == null)
        str1 = paramProperties.getProperty("oracle.jdbc.fixedString");
    }
    if (str1 == null)
      str1 = getSystemProperty("oracle.jdbc.fixedString", null);
    if (str1 == null) {
      str1 = "false";
    }

    this.fixedString = ((str1 != null) && (str1.equalsIgnoreCase("true")));

    str1 = null;
    if (paramProperties != null)
    {
      str1 = paramProperties.getProperty("defaultNChar");
      if (str1 == null)
        str1 = paramProperties.getProperty("oracle.jdbc.defaultNChar");
    }
    if (str1 == null)
      str1 = getSystemProperty("oracle.jdbc.defaultNChar", null);
    if (str1 == null) {
      str1 = "false";
    }

    this.defaultnchar = ((str1 != null) && (str1.equalsIgnoreCase("true")));

    str1 = null;
    if (paramProperties != null)
    {
      str1 = paramProperties.getProperty("oracle.jdbc.internal.permitBindDateDefineTimestampMismatch");
    }
    if (str1 == null)
      str1 = getSystemProperty("oracle.jdbc.internal.permitBindDateDefineTimestampMismatch", null);
    if (str1 == null) {
      str1 = "false";
    }

    this.permitTimestampDateMismatch = ((str1 != null) && (str1.equalsIgnoreCase("true")));

    str1 = null;
    if (paramProperties != null)
    {
      str1 = paramProperties.getProperty("RessourceManagerId");
      if (str1 == null)
        str1 = paramProperties.getProperty("oracle.jdbc.RessourceManagerId");
    }
    if (str1 == null)
      str1 = getSystemProperty("oracle.jdbc.RessourceManagerId", null);
    if (str1 == null) {
      str1 = "0000";
    }

    this.resourceManagerId = str1;

    str1 = null;
    if (paramProperties != null)
    {
      str1 = paramProperties.getProperty("disableDefineColumnType");
      if (str1 == null)
        str1 = paramProperties.getProperty("oracle.jdbc.disableDefineColumnType");
    }
    if (str1 == null)
      str1 = getSystemProperty("oracle.jdbc.disableDefineColumnType", null);
    if (str1 == null) {
      str1 = "false";
    }

    this.disableDefinecolumntype = ((str1 != null) && (str1.equalsIgnoreCase("true")));

    str1 = null;
    if (paramProperties != null)
    {
      str1 = paramProperties.getProperty("oracle.jdbc.convertNcharLiterals");
    }
    if (str1 == null)
      str1 = getSystemProperty("oracle.jdbc.convertNcharLiterals", null);
    if (str1 == null) {
      str1 = "false";
    }

    this.convertNcharLiterals = ((str1 != null) && (str1.equalsIgnoreCase("true")));

    str1 = null;
    if (paramProperties != null)
    {
      str1 = paramProperties.getProperty("oracle.jdbc.J2EE13Compliant");
    }
    if (str1 == null)
      str1 = getSystemProperty("oracle.jdbc.J2EE13Compliant", null);
    if (str1 == null) {
      str1 = "false";
    }

    this.j2ee13Compliant = ((str1 != null) && (str1.equalsIgnoreCase("true")));

    str1 = null;
    if (paramProperties != null)
    {
      str1 = paramProperties.getProperty("oracle.jdbc.mapDateToTimestamp");
    }
    if (str1 == null)
      str1 = getSystemProperty("oracle.jdbc.mapDateToTimestamp", null);
    if (str1 == null) {
      str1 = "true";
    }

    this.mapDateToTimestamp = ((str1 != null) && (str1.equalsIgnoreCase("true")));

    str1 = null;
    if (paramProperties != null)
    {
      str1 = paramProperties.getProperty("oracle.jdbc.useThreadLocalBufferCache");
    }
    if (str1 == null)
      str1 = getSystemProperty("oracle.jdbc.useThreadLocalBufferCache", null);
    if (str1 == null) {
      str1 = "false";
    }

    this.useThreadLocalBufferCache = ((str1 != null) && (str1.equalsIgnoreCase("true")));

    str1 = null;
    if (paramProperties != null)
    {
      str1 = paramProperties.getProperty("oracle.jdbc.driverNameAttribute");
    }
    if (str1 == null)
      str1 = getSystemProperty("oracle.jdbc.driverNameAttribute", null);
    if (str1 == null) {
      str1 = null;
    }

    this.driverNameAttribute = str1;

    str1 = null;
    if (paramProperties != null)
    {
      str1 = paramProperties.getProperty("oracle.jdbc.maxCachedBufferSize");
    }
    if (str1 == null)
      str1 = getSystemProperty("oracle.jdbc.maxCachedBufferSize", null);
    if (str1 == null)
      str1 = "30";
    try
    {
      this.maxCachedBufferSize = Integer.parseInt(str1);
    }
    catch (NumberFormatException localNumberFormatException8)
    {
      localObject2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 190, "Property is 'maxCachedBufferSize'");
      ((SQLException)localObject2).fillInStackTrace();
      throw ((Throwable)localObject2);
    }

    str1 = null;
    if (paramProperties != null)
    {
      str1 = paramProperties.getProperty("oracle.jdbc.implicitStatementCacheSize");
    }
    if (str1 == null)
      str1 = getSystemProperty("oracle.jdbc.implicitStatementCacheSize", null);
    if (str1 == null)
      str1 = "0";
    try
    {
      this.implicitStatementCacheSize = Integer.parseInt(str1);
    }
    catch (NumberFormatException localNumberFormatException9)
    {
      localObject2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 190, "Property is 'implicitStatementCacheSize'");
      ((SQLException)localObject2).fillInStackTrace();
      throw ((Throwable)localObject2);
    }

    str1 = null;
    if (paramProperties != null)
    {
      str1 = paramProperties.getProperty("oracle.jdbc.LobStreamPosStandardCompliant");
    }
    if (str1 == null)
      str1 = getSystemProperty("oracle.jdbc.LobStreamPosStandardCompliant", null);
    if (str1 == null) {
      str1 = "false";
    }

    this.lobStreamPosStandardCompliant = ((str1 != null) && (str1.equalsIgnoreCase("true")));

    str1 = null;
    if (paramProperties != null)
    {
      str1 = paramProperties.getProperty("oracle.jdbc.getObjectReturnsXMLType");
    }
    if (str1 == null)
      str1 = getSystemProperty("oracle.jdbc.getObjectReturnsXMLType", null);
    if (str1 == null) {
      str1 = "false";
    }

    this.getObjectReturnsXmlType = ((str1 != null) && (str1.equalsIgnoreCase("true")));

    str1 = null;
    if (paramProperties != null)
    {
      str1 = paramProperties.getProperty("oracle.jdbc.strictASCIIConversion");
    }
    if (str1 == null)
      str1 = getSystemProperty("oracle.jdbc.strictASCIIConversion", null);
    if (str1 == null) {
      str1 = "false";
    }

    this.isStrictAsciiConversion = ((str1 != null) && (str1.equalsIgnoreCase("true")));

    str1 = null;
    if (paramProperties != null)
    {
      str1 = paramProperties.getProperty("oracle.jdbc.thinForceDNSLoadBalancing");
    }
    if (str1 == null)
      str1 = getSystemProperty("oracle.jdbc.thinForceDNSLoadBalancing", null);
    if (str1 == null) {
      str1 = "false";
    }

    this.thinForceDnsLoadBalancing = ((str1 != null) && (str1.equalsIgnoreCase("true")));

    str1 = null;
    if (paramProperties != null)
      str1 = paramProperties.getProperty("oracle.jdbc.commitOption");
    if (str1 == null)
      str1 = getSystemProperty("oracle.jdbc.commitOption", null);
    Object localObject1;
    Object localObject3;
    if (str1 != null)
    {
      this.commitOption = 0;
      localObject1 = str1.split(",");
      if ((localObject1 != null) && (localObject1.length > 0))
      {
        for (localObject3 : localObject1) {
          if (((String)localObject3).trim() != "") {
            this.commitOption |= OracleConnection.CommitOption.valueOf(((String)localObject3).trim()).getCode();
          }
        }
      }

    }

    this.includeSynonyms = parseConnectionProperty_boolean(paramProperties, "synonyms", (byte)3, this.includeSynonyms);

    this.reportRemarks = parseConnectionProperty_boolean(paramProperties, "remarks", (byte)3, this.reportRemarks);

    this.defaultRowPrefetch = parseConnectionProperty_int(paramProperties, "prefetch", (byte)3, this.defaultRowPrefetch);

    this.defaultRowPrefetch = parseConnectionProperty_int(paramProperties, "rowPrefetch", (byte)3, this.defaultRowPrefetch);

    this.defaultExecuteBatch = parseConnectionProperty_int(paramProperties, "batch", (byte)3, this.defaultExecuteBatch);

    this.defaultExecuteBatch = parseConnectionProperty_int(paramProperties, "executeBatch", (byte)3, this.defaultExecuteBatch);

    this.proxyClientName = parseConnectionProperty_String(paramProperties, "PROXY_CLIENT_NAME", (byte)1, this.proxyClientName);

    if (this.defaultRowPrefetch <= 0) {
      this.defaultRowPrefetch = Integer.parseInt("10");
    }
    if (this.defaultExecuteBatch <= 0) {
      this.defaultExecuteBatch = Integer.parseInt("1");
    }
    if (this.defaultLobPrefetchSize < -1)
    {
      localObject1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 267);
      ((SQLException)localObject1).fillInStackTrace();
      throw ((Throwable)localObject1);
    }

    if (this.streamChunkSize > 0)
      this.streamChunkSize = Math.max(4096, this.streamChunkSize);
    else {
      this.streamChunkSize = Integer.parseInt("16384");
    }

    if (this.thinVsessionOsuser == null)
    {
      this.thinVsessionOsuser = getSystemProperty("user.name", null);
      if (this.thinVsessionOsuser == null) {
        this.thinVsessionOsuser = "jdbcuser";
      }

    }

    if (this.thinNetConnectTimeout == CONNECTION_PROPERTY_THIN_NET_CONNECT_TIMEOUT_DEFAULT)
    {
      int i = DriverManager.getLoginTimeout();
      if (i != 0) {
        this.thinNetConnectTimeout = ("" + i * 1000);
      }

    }

    this.url = paramString;
    Hashtable localHashtable = parseUrl(this.url, this.walletLocation, this.walletPassword);

    if (this.userName == CONNECTION_PROPERTY_USER_NAME_DEFAULT)
      this.userName = ((String)localHashtable.get("user"));
    Object localObject2 = new String[1];
    String[] arrayOfString = new String[1];
    this.userName = parseLoginOption(this.userName, paramProperties, (String[])localObject2, arrayOfString);
    if (localObject2[0] != null)
      this.internalLogon = localObject2[0];
    if (arrayOfString[0] != null) {
      this.proxyClientName = arrayOfString[0];
    }
    String str2 = paramProperties.getProperty("password", CONNECTION_PROPERTY_PASSWORD_DEFAULT);

    if (str2 == CONNECTION_PROPERTY_PASSWORD_DEFAULT)
      str2 = (String)localHashtable.get("password");
    initializePassword(str2);

    if (this.database == CONNECTION_PROPERTY_DATABASE_DEFAULT) {
      this.database = paramProperties.getProperty("server", CONNECTION_PROPERTY_DATABASE_DEFAULT);
    }
    if (this.database == CONNECTION_PROPERTY_DATABASE_DEFAULT) {
      this.database = ((String)localHashtable.get("database"));
    }
    this.protocol = ((String)localHashtable.get("protocol"));

    if (this.protocol == null)
    {
      localObject3 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 40, "Protocol is not specified in URL");
      ((SQLException)localObject3).fillInStackTrace();
      throw ((Throwable)localObject3);
    }

    if ((this.protocol.equals("oci8")) || (this.protocol.equals("oci"))) {
      this.database = translateConnStr(this.database);
    }

    if (paramProperties.getProperty("is_connection_pooling") == "true")
    {
      if (this.database == null) {
        this.database = "";
      }
    }
    if ((this.userName != null) && (!this.userName.startsWith("\"")))
    {
      localObject3 = this.userName.toCharArray();
      for (int m = 0; m < localObject3.length; m++)
        localObject3[m] = Character.toUpperCase(localObject3[m]);
      this.userName = String.copyValueOf((char[])localObject3);
    }

    this.xaWantsError = false;
    this.usingXA = false;

    readOCIConnectionPoolProperties(paramProperties);
    validateConnectionProperties();
  }

  private void readOCIConnectionPoolProperties(Properties paramProperties)
    throws SQLException
  {
    this.ociConnectionPoolMinLimit = parseConnectionProperty_int(paramProperties, "connpool_min_limit", (byte)1, 0);

    this.ociConnectionPoolMaxLimit = parseConnectionProperty_int(paramProperties, "connpool_max_limit", (byte)1, 0);

    this.ociConnectionPoolIncrement = parseConnectionProperty_int(paramProperties, "connpool_increment", (byte)1, 0);

    this.ociConnectionPoolTimeout = parseConnectionProperty_int(paramProperties, "connpool_timeout", (byte)1, 0);

    this.ociConnectionPoolNoWait = parseConnectionProperty_boolean(paramProperties, "connpool_nowait", (byte)1, false);

    this.ociConnectionPoolTransactionDistributed = parseConnectionProperty_boolean(paramProperties, "transactions_distributed", (byte)1, false);

    this.ociConnectionPoolLogonMode = parseConnectionProperty_String(paramProperties, "connection_pool", (byte)1, null);

    this.ociConnectionPoolIsPooling = parseConnectionProperty_boolean(paramProperties, "is_connection_pooling", (byte)1, false);

    this.ociConnectionPoolObject = parseConnectionProperty_Object(paramProperties, "connpool_object", null);

    this.ociConnectionPoolConnID = parseConnectionProperty_Object(paramProperties, "connection_id", null);

    this.ociConnectionPoolProxyType = parseConnectionProperty_String(paramProperties, "proxytype", (byte)1, null);

    this.ociConnectionPoolProxyNumRoles = ((Integer)parseConnectionProperty_Object(paramProperties, "proxy_num_roles", Integer.valueOf(0)));

    this.ociConnectionPoolProxyRoles = parseConnectionProperty_Object(paramProperties, "proxy_roles", null);

    this.ociConnectionPoolProxyUserName = parseConnectionProperty_String(paramProperties, "proxy_user_name", (byte)1, null);

    this.ociConnectionPoolProxyPassword = parseConnectionProperty_String(paramProperties, "proxy_password", (byte)1, null);

    this.ociConnectionPoolProxyDistinguishedName = parseConnectionProperty_String(paramProperties, "proxy_distinguished_name", (byte)1, null);

    this.ociConnectionPoolProxyCertificate = parseConnectionProperty_Object(paramProperties, "proxy_certificate", null);
  }

  void validateConnectionProperties()
    throws SQLException
  {
    if ((this.driverNameAttribute != null) && (!driverNameAttributePattern.matcher(this.driverNameAttribute).matches()))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 257);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  private static final Object parseConnectionProperty_Object(Properties paramProperties, String paramString, Object paramObject)
    throws SQLException
  {
    Object localObject1 = paramObject;
    if (paramProperties != null)
    {
      Object localObject2 = paramProperties.get(paramString);
      if (localObject2 != null)
        localObject1 = localObject2;
    }
    return localObject1;
  }

  private static final String parseConnectionProperty_String(Properties paramProperties, String paramString1, byte paramByte, String paramString2)
    throws SQLException
  {
    String str = null;
    if (((paramByte == 1) || (paramByte == 3)) && (paramProperties != null))
    {
      str = paramProperties.getProperty(paramString1);
      if ((str == null) && (!paramString1.startsWith("oracle.")) && (!paramString1.startsWith("java.")) && (!paramString1.startsWith("javax.")))
        str = paramProperties.getProperty("oracle.jdbc." + paramString1);
    }
    if ((str == null) && ((paramByte == 2) || (paramByte == 3)))
    {
      if ((paramString1.startsWith("oracle.")) || (paramString1.startsWith("java.")) || (paramString1.startsWith("javax.")))
        str = getSystemProperty(paramString1, null);
      else
        str = getSystemProperty("oracle.jdbc." + paramString1, null);
    }
    if (str == null)
      str = paramString2;
    return str;
  }

  private static final int parseConnectionProperty_int(Properties paramProperties, String paramString, byte paramByte, int paramInt)
    throws SQLException
  {
    int i = paramInt;
    String str = parseConnectionProperty_String(paramProperties, paramString, paramByte, null);

    if (str != null)
    {
      try
      {
        i = Integer.parseInt(str);
      }
      catch (NumberFormatException localNumberFormatException)
      {
        SQLException localSQLException = DatabaseError.createSqlException(null, 190, "Property is '" + paramString + "' and value is '" + str + "'");
        localSQLException.fillInStackTrace();
        throw localSQLException;
      }

    }

    return i;
  }

  private static final long parseConnectionProperty_long(Properties paramProperties, String paramString, byte paramByte, long paramLong)
    throws SQLException
  {
    long l = paramLong;
    String str = parseConnectionProperty_String(paramProperties, paramString, paramByte, null);

    if (str != null)
    {
      try
      {
        l = Long.parseLong(str);
      }
      catch (NumberFormatException localNumberFormatException)
      {
        SQLException localSQLException = DatabaseError.createSqlException(null, 190, "Property is '" + paramString + "' and value is '" + str + "'");
        localSQLException.fillInStackTrace();
        throw localSQLException;
      }

    }

    return l;
  }

  private static final boolean parseConnectionProperty_boolean(Properties paramProperties, String paramString, byte paramByte, boolean paramBoolean)
    throws SQLException
  {
    boolean bool = paramBoolean;
    String str = parseConnectionProperty_String(paramProperties, paramString, paramByte, null);

    if (str != null)
    {
      if (str.equalsIgnoreCase("false"))
        bool = false;
      else if (str.equalsIgnoreCase("true"))
        bool = true;
    }
    return bool;
  }

  private static String parseLoginOption(String paramString, Properties paramProperties, String[] paramArrayOfString1, String[] paramArrayOfString2)
  {
    int j = 0;
    String str1 = null;
    String str2 = null;

    if (paramString == null) {
      return null;
    }
    int k = paramString.length();

    if (k == 0) {
      return null;
    }

    int i = paramString.indexOf(91);
    if (i > 0) {
      j = paramString.indexOf(93);
      str2 = paramString.substring(i + 1, j);
      str2 = str2.trim();

      if (str2.length() > 0) {
        paramArrayOfString2[0] = str2;
      }

      paramString = paramString.substring(0, i) + paramString.substring(j + 1, k);
    }

    String str3 = paramString.toLowerCase();

    i = str3.lastIndexOf(" as ");

    if ((i == -1) || (i < str3.lastIndexOf("\""))) {
      return paramString;
    }

    str1 = paramString.substring(0, i);

    i += 4;

    while ((i < k) && (str3.charAt(i) == ' ')) {
      i++;
    }
    if (i == k) {
      return paramString;
    }
    String str4 = str3.substring(i).trim();

    if (str4.length() > 0) {
      paramArrayOfString1[0] = str4;
    }
    return str1;
  }

  private static final Hashtable parseUrl(String paramString1, String paramString2, String paramString3)
    throws SQLException
  {
    Hashtable localHashtable = new Hashtable(5);
    int i = paramString1.indexOf(58, paramString1.indexOf(58) + 1) + 1;
    int j = paramString1.length();

    if (i == j) {
      return localHashtable;
    }
    int k = paramString1.indexOf(58, i);

    if (k == -1)
    {
      return localHashtable;
    }

    localHashtable.put("protocol", paramString1.substring(i, k));

    int m = k + 1;
    int n = paramString1.indexOf(47, m);

    int i1 = paramString1.indexOf(64, m);
    Object localObject;
    if ((i1 > m) && (m > i) && (n == -1))
    {
      localObject = DatabaseError.createSqlException(null, 67);
      ((SQLException)localObject).fillInStackTrace();
      throw ((Throwable)localObject);
    }

    if (i1 == -1) {
      i1 = j;
    }
    if (n == -1) {
      n = i1;
    }
    if ((n < i1) && (n != m) && (i1 != m))
    {
      localHashtable.put("user", paramString1.substring(m, n));
      localHashtable.put("password", paramString1.substring(n + 1, i1));
    }

    if ((n <= i1) && ((n == m) || (i1 == m)))
    {
      if (i1 < j)
      {
        localObject = paramString1.substring(i1 + 1);
        String[] arrayOfString = getSecretStoreCredentials((String)localObject, paramString2, paramString3);
        if ((arrayOfString[0] != null) || (arrayOfString[1] != null))
        {
          localHashtable.put("user", arrayOfString[0]);
          localHashtable.put("password", arrayOfString[1]);
        }
      }

    }

    if (i1 < j) {
      localHashtable.put("database", paramString1.substring(i1 + 1));
    }
    return localHashtable;
  }

  private static final String[] getSecretStoreCredentials(String paramString1, String paramString2, String paramString3)
    throws SQLException
  {
    String[] arrayOfString = new String[2];
    arrayOfString[0] = null;
    arrayOfString[1] = null;

    if (paramString2 != null)
    {
      try
      {
        if (paramString2.startsWith("(")) {
          paramString2 = "file:" + CustomSSLSocketFactory.processWalletLocation(paramString2);
        }
        OracleWallet localOracleWallet = new OracleWallet();
        if (localOracleWallet.exists(paramString2))
        {
          localObject = null;
          if (paramString3 != null) {
            localObject = paramString3.toCharArray();
          }

          localOracleWallet.open(paramString2, (char[])localObject);
          OracleSecretStore localOracleSecretStore = localOracleWallet.getSecretStore();

          if (localOracleSecretStore.containsAlias("oracle.security.client.default_username")) {
            arrayOfString[0] = new String(localOracleSecretStore.getSecret("oracle.security.client.default_username"));
          }
          if (localOracleSecretStore.containsAlias("oracle.security.client.default_password")) {
            arrayOfString[1] = new String(localOracleSecretStore.getSecret("oracle.security.client.default_password"));
          }

          Enumeration localEnumeration = localOracleWallet.getSecretStore().internalAliases();

          String str1 = null;
          while (localEnumeration.hasMoreElements())
          {
            str1 = (String)localEnumeration.nextElement();
            if ((str1.startsWith("oracle.security.client.connect_string")) && 
              (paramString1.equalsIgnoreCase(new String(localOracleSecretStore.getSecret(str1)))))
            {
              String str2 = str1.substring("oracle.security.client.connect_string".length());
              arrayOfString[0] = new String(localOracleSecretStore.getSecret("oracle.security.client.username" + str2));

              arrayOfString[1] = new String(localOracleSecretStore.getSecret("oracle.security.client.password" + str2));
            }

          }

        }

      }
      catch (NoClassDefFoundError localNoClassDefFoundError)
      {
        localObject = DatabaseError.createSqlException(null, 167, localNoClassDefFoundError);
        ((SQLException)localObject).fillInStackTrace();
        throw ((Throwable)localObject);
      }
      catch (Exception localException)
      {
        if ((localException instanceof RuntimeException)) throw ((RuntimeException)localException);

        Object localObject = DatabaseError.createSqlException(null, 168, localException);
        ((SQLException)localObject).fillInStackTrace();
        throw ((Throwable)localObject);
      }

    }

    return arrayOfString;
  }

  private String translateConnStr(String paramString)
    throws SQLException
  {
    int i = 0;
    int j = 0;

    if ((paramString == null) || (paramString.equals(""))) {
      return paramString;
    }

    if (paramString.indexOf(41) != -1) {
      return paramString;
    }
    int k = 0;
    if (paramString.indexOf(91) != -1)
    {
      i = paramString.indexOf(93);
      if (i == -1)
      {
        localObject = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 67, paramString);
        ((SQLException)localObject).fillInStackTrace();
        throw ((Throwable)localObject);
      }
      k = 1;
    }

    i = paramString.indexOf(58, i);
    if (i == -1)
      return paramString;
    j = paramString.indexOf(58, i + 1);
    if (j == -1) {
      return paramString;
    }

    if (paramString.indexOf(58, j + 1) != -1)
    {
      localObject = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 67, paramString);
      ((SQLException)localObject).fillInStackTrace();
      throw ((Throwable)localObject);
    }

    Object localObject = null;
    if (k != 0)
      localObject = paramString.substring(1, i - 1);
    else {
      localObject = paramString.substring(0, i);
    }
    String str2 = paramString.substring(i + 1, j);
    String str3 = paramString.substring(j + 1, paramString.length());

    String str1 = "(DESCRIPTION=(ADDRESS=(PROTOCOL=tcp)(HOST=" + (String)localObject + ")(PORT=" + str2 + "))(CONNECT_DATA=(SID=" + str3 + ")))";

    return str1;
  }

  protected static String getSystemPropertyPollInterval()
  {
    return getSystemProperty("oracle.jdbc.TimeoutPollInterval", "1000");
  }

  static String getSystemPropertyFastConnectionFailover(String paramString)
  {
    return getSystemProperty("oracle.jdbc.FastConnectionFailover", paramString);
  }

  static String getSystemPropertyJserverVersion()
  {
    return getSystemProperty("oracle.jserver.version", null);
  }

  private static String getSystemProperty(String paramString1, String paramString2)
  {
    if (paramString1 != null)
    {
      final String str1 = paramString1;
      final String str2 = paramString2;
      String[] arrayOfString = { paramString2 };
      AccessController.doPrivileged(new PrivilegedAction()
      {
        public Object run()
        {
          this.val$rets[0] = System.getProperty(str1, str2);
          return null;
        }
      });
      return arrayOfString[0];
    }

    return paramString2;
  }

  abstract void initializePassword(String paramString)
    throws SQLException;

  public Properties getProperties()
  {
    Properties localProperties = new Properties();
    try
    {
      Class localClass1 = null;
      Class localClass2 = null;
      try
      {
        localClass1 = ClassRef.newInstance("oracle.jdbc.OracleConnection").get();
        localClass2 = ClassRef.newInstance("oracle.jdbc.driver.PhysicalConnection").get();
      }
      catch (ClassNotFoundException localClassNotFoundException)
      {
      }

      Field[] arrayOfField = localClass2.getDeclaredFields();
      for (int i = 0; i < arrayOfField.length; i++)
      {
        int j = arrayOfField[i].getModifiers();
        if (!Modifier.isStatic(j))
        {
          String str1 = arrayOfField[i].getName();

          String str2 = "CONNECTION_PROPERTY_" + propertyVariableName(str1);

          Field localField = null;
          try
          {
            localField = localClass1.getField(str2);
          }
          catch (NoSuchFieldException localNoSuchFieldException)
          {
            continue;
          }

          if (!str2.matches(".*PASSWORD.*"))
          {
            String str3 = (String)localField.get(null);
            String str4 = arrayOfField[i].getType().getName();
            if (str4.equals("boolean"))
            {
              boolean bool = arrayOfField[i].getBoolean(this);
              if (bool)
                localProperties.setProperty(str3, "true");
              else
                localProperties.setProperty(str3, "false");
            }
            else if (str4.equals("int"))
            {
              int k = arrayOfField[i].getInt(this);
              localProperties.setProperty(str3, Integer.toString(k));
            }
            else if (str4.equals("long"))
            {
              long l = arrayOfField[i].getLong(this);
              localProperties.setProperty(str3, Long.toString(l));
            }
            else if (str4.equals("java.lang.String"))
            {
              String str5 = (String)arrayOfField[i].get(this);
              if (str5 != null)
                localProperties.setProperty(str3, str5);
            }
          }
        }
      }
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
    }
    return localProperties;
  }

  /** @deprecated */
  public synchronized Connection _getPC()
  {
    return null;
  }

  public synchronized oracle.jdbc.internal.OracleConnection getPhysicalConnection()
  {
    return this;
  }

  public synchronized boolean isLogicalConnection()
  {
    return false;
  }

  void initialize(Hashtable paramHashtable, Map paramMap1, Map paramMap2)
    throws SQLException
  {
    this.clearStatementMetaData = false;

    if (paramHashtable != null)
      this.descriptorCacheStack[this.dci] = paramHashtable;
    else {
      this.descriptorCacheStack[this.dci] = new Hashtable(10);
    }
    this.map = paramMap1;

    if (paramMap2 != null)
      this.javaObjectMap = paramMap2;
    else {
      this.javaObjectMap = new Hashtable(10);
    }
    this.lifecycle = 1;
    this.txnLevel = 2;

    this.clientIdSet = false;
  }

  void initializeSetCHARCharSetObjs()
  {
    this.setCHARNCharSetObj = this.conversion.getDriverNCharSetObj();
    this.setCHARCharSetObj = this.conversion.getDriverCharSetObj();
  }

  OracleTimeout getTimeout()
    throws SQLException
  {
    if (this.timeout == null)
    {
      this.timeout = OracleTimeout.newTimeout(this.url);
    }

    return this.timeout;
  }

  public synchronized Statement createStatement()
    throws SQLException
  {
    return createStatement(-1, -1);
  }

  public synchronized Statement createStatement(int paramInt1, int paramInt2)
    throws SQLException
  {
    if (this.lifecycle != 1)
    {
      localObject = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 8);
      ((SQLException)localObject).fillInStackTrace();
      throw ((Throwable)localObject);
    }

    Object localObject = null;

    localObject = this.driverExtension.allocateStatement(this, paramInt1, paramInt2);

    return new OracleStatementWrapper((oracle.jdbc.OracleStatement)localObject);
  }

  public synchronized PreparedStatement prepareStatement(String paramString)
    throws SQLException
  {
    return prepareStatement(paramString, -1, -1);
  }

  /** @deprecated */
  public synchronized PreparedStatement prepareStatementWithKey(String paramString)
    throws SQLException
  {
    if (this.lifecycle != 1)
    {
      localObject = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 8);
      ((SQLException)localObject).fillInStackTrace();
      throw ((Throwable)localObject);
    }

    if (paramString == null) {
      return null;
    }
    if (!isStatementCacheInitialized())
    {
      localObject = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 95);
      ((SQLException)localObject).fillInStackTrace();
      throw ((Throwable)localObject);
    }

    Object localObject = null;

    localObject = (OraclePreparedStatement)this.statementCache.searchExplicitCache(paramString);

    if (localObject != null) {
      localObject = new OraclePreparedStatementWrapper((oracle.jdbc.OraclePreparedStatement)localObject);
    }
    return localObject;
  }

  public synchronized PreparedStatement prepareStatement(String paramString, int paramInt1, int paramInt2)
    throws SQLException
  {
    if ((paramString == null) || (paramString.length() == 0))
    {
      localObject = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 104);
      ((SQLException)localObject).fillInStackTrace();
      throw ((Throwable)localObject);
    }

    if (this.lifecycle != 1)
    {
      localObject = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 8);
      ((SQLException)localObject).fillInStackTrace();
      throw ((Throwable)localObject);
    }

    Object localObject = null;

    if (this.statementCache != null) {
      localObject = (OraclePreparedStatement)this.statementCache.searchImplicitCache(paramString, 1, (paramInt1 != -1) || (paramInt2 != -1) ? ResultSetUtil.getRsetTypeCode(paramInt1, paramInt2) : 1);
    }

    if (localObject == null) {
      localObject = this.driverExtension.allocatePreparedStatement(this, paramString, paramInt1, paramInt2);
    }

    return new OraclePreparedStatementWrapper((oracle.jdbc.OraclePreparedStatement)localObject);
  }

  public synchronized CallableStatement prepareCall(String paramString)
    throws SQLException
  {
    return prepareCall(paramString, -1, -1);
  }

  public synchronized CallableStatement prepareCall(String paramString, int paramInt1, int paramInt2)
    throws SQLException
  {
    if ((paramString == null) || (paramString.length() == 0))
    {
      localObject = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 104);
      ((SQLException)localObject).fillInStackTrace();
      throw ((Throwable)localObject);
    }

    if (this.lifecycle != 1)
    {
      localObject = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 8);
      ((SQLException)localObject).fillInStackTrace();
      throw ((Throwable)localObject);
    }

    Object localObject = null;

    if (this.statementCache != null) {
      localObject = (OracleCallableStatement)this.statementCache.searchImplicitCache(paramString, 2, (paramInt1 != -1) || (paramInt2 != -1) ? ResultSetUtil.getRsetTypeCode(paramInt1, paramInt2) : 1);
    }

    if (localObject == null) {
      localObject = this.driverExtension.allocateCallableStatement(this, paramString, paramInt1, paramInt2);
    }

    return new OracleCallableStatementWrapper((oracle.jdbc.OracleCallableStatement)localObject);
  }

  public synchronized CallableStatement prepareCallWithKey(String paramString)
    throws SQLException
  {
    if (this.lifecycle != 1)
    {
      localObject = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 8);
      ((SQLException)localObject).fillInStackTrace();
      throw ((Throwable)localObject);
    }

    if (paramString == null) {
      return null;
    }
    if (!isStatementCacheInitialized())
    {
      localObject = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 95);
      ((SQLException)localObject).fillInStackTrace();
      throw ((Throwable)localObject);
    }

    Object localObject = null;

    localObject = (OracleCallableStatement)this.statementCache.searchExplicitCache(paramString);

    if (localObject != null) {
      localObject = new OracleCallableStatementWrapper((oracle.jdbc.OracleCallableStatement)localObject);
    }
    return localObject;
  }

  public String nativeSQL(String paramString)
    throws SQLException
  {
    if (this.sqlObj == null)
    {
      this.sqlObj = new OracleSql(this.conversion);
    }

    this.sqlObj.initialize(paramString);

    String str = this.sqlObj.getSql(this.processEscapes, this.convertNcharLiterals);

    return str;
  }

  public synchronized void setAutoCommit(boolean paramBoolean)
    throws SQLException
  {
    if (paramBoolean) {
      disallowGlobalTxnMode(116);
    }
    if (this.lifecycle != 1)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 8);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    needLine();
    doSetAutoCommit(paramBoolean);

    this.autocommit = paramBoolean;
  }

  public boolean getAutoCommit()
    throws SQLException
  {
    return this.autocommit;
  }

  public void cancel()
    throws SQLException
  {
    OracleStatement localOracleStatement = this.statements;

    if ((this.lifecycle != 1) && (this.lifecycle != 16))
    {
      SQLException localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 8);
      localSQLException1.fillInStackTrace();
      throw localSQLException1;
    }

    int i = 0;

    while (localOracleStatement != null)
    {
      try
      {
        if (localOracleStatement.doCancel()) {
          i = 1;
        }
      }
      catch (SQLException localSQLException2)
      {
      }
      localOracleStatement = localOracleStatement.next;
    }

    if (i == 0)
      cancelOperationOnServer();
  }

  public void commit(EnumSet<OracleConnection.CommitOption> paramEnumSet)
    throws SQLException
  {
    int i = 0;
    Object localObject;
    if (paramEnumSet != null)
    {
      if (((paramEnumSet.contains(OracleConnection.CommitOption.WRITEBATCH)) && (paramEnumSet.contains(OracleConnection.CommitOption.WRITEIMMED))) || ((paramEnumSet.contains(OracleConnection.CommitOption.WAIT)) && (paramEnumSet.contains(OracleConnection.CommitOption.NOWAIT))))
      {
        localObject = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 191);
        ((SQLException)localObject).fillInStackTrace();
        throw ((Throwable)localObject);
      }

      for (localObject = paramEnumSet.iterator(); ((Iterator)localObject).hasNext(); ) { OracleConnection.CommitOption localCommitOption = (OracleConnection.CommitOption)((Iterator)localObject).next();
        i |= localCommitOption.getCode(); }
    }
    commit(i);
  }

  synchronized void commit(int paramInt)
    throws SQLException
  {
    disallowGlobalTxnMode(114);

    if (this.lifecycle != 1)
    {
      localObject = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 8);
      ((SQLException)localObject).fillInStackTrace();
      throw ((Throwable)localObject);
    }

    Object localObject = this.statements;

    while (localObject != null)
    {
      if (!((OracleStatement)localObject).closed) {
        ((OracleStatement)localObject).sendBatch();
      }
      localObject = ((OracleStatement)localObject).next;
    }
    if ((((paramInt & OracleConnection.CommitOption.WRITEBATCH.getCode()) != 0) && ((paramInt & OracleConnection.CommitOption.WRITEIMMED.getCode()) != 0)) || (((paramInt & OracleConnection.CommitOption.WAIT.getCode()) != 0) && ((paramInt & OracleConnection.CommitOption.NOWAIT.getCode()) != 0)))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 191);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    registerHeartbeat();

    needLine();
    doCommit(paramInt);
  }

  public void commit()
    throws SQLException
  {
    commit(this.commitOption);
  }

  public synchronized void rollback()
    throws SQLException
  {
    disallowGlobalTxnMode(115);

    if (this.lifecycle != 1)
    {
      localObject = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 8);
      ((SQLException)localObject).fillInStackTrace();
      throw ((Throwable)localObject);
    }

    Object localObject = this.statements;

    while (localObject != null)
    {
      if (((OracleStatement)localObject).isOracleBatchStyle()) {
        ((OracleStatement)localObject).clearBatch();
      }
      localObject = ((OracleStatement)localObject).next;
    }

    registerHeartbeat();

    needLine();
    doRollback();
  }

  public synchronized void close()
    throws SQLException
  {
    if ((this.lifecycle == 2) || (this.lifecycle == 4)) {
      return;
    }
    if (this.lifecycle == 1) this.lifecycle = 2;

    try
    {
      if (this.closeCallback != null) {
        this.closeCallback.beforeClose(this, this.privateData);
      }
      closeStatementCache();
      closeStatements(false);

      needLineUnchecked();

      if (this.isProxy)
      {
        close(1);
      }

      if (this.timeZoneTab != null) {
        this.timeZoneTab.freeInstance();
      }
      logoff();
      cleanup();

      if (this.timeout != null) {
        this.timeout.close();
      }
      if (this.closeCallback != null)
        this.closeCallback.afterClose(this.privateData);
    }
    finally
    {
      this.lifecycle = 4;
      this.isUsable = false;
    }
  }

  public String getDataIntegrityAlgorithmName()
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createUnsupportedFeatureSqlException();
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public String getEncryptionAlgorithmName()
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createUnsupportedFeatureSqlException();
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public String getAuthenticationAdaptorName()
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createUnsupportedFeatureSqlException();
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void closeInternal(boolean paramBoolean)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createUnsupportedFeatureSqlException();
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void cleanupAndClose(boolean paramBoolean)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createUnsupportedFeatureSqlException();
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  void cleanupAndClose()
    throws SQLException
  {
    if (this.lifecycle != 1) {
      return;
    }

    this.lifecycle = 16;

    cancel();
  }

  synchronized void closeLogicalConnection()
    throws SQLException
  {
    if ((this.lifecycle == 1) || (this.lifecycle == 16) || (this.lifecycle == 2))
    {
      this.savepointStatement = null;
      closeStatements(true);

      if (this.clientIdSet) {
        clearClientIdentifier(this.clientId);
      }
      this.logicalConnectionAttached = null;
      this.lifecycle = 1;
    }
  }

  public synchronized void close(Properties paramProperties)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 152);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public synchronized void close(int paramInt)
    throws SQLException
  {
    if ((paramInt & 0x1000) != 0)
    {
      close();

      return;
    }

    if (((paramInt & 0x1) != 0) && (this.isProxy))
    {
      purgeStatementCache();
      closeStatements(false);
      this.descriptorCacheStack[(this.dci--)] = null;

      closeProxySession();

      this.isProxy = false;
    }
  }

  public void abort()
    throws SQLException
  {
    SecurityManager localSecurityManager = System.getSecurityManager();
    if (localSecurityManager != null) {
      localSecurityManager.checkPermission(CALL_ABORT_PERMISSION);
    }

    if ((this.lifecycle == 4) || (this.lifecycle == 8)) {
      return;
    }

    this.lifecycle = 8;

    doAbort();
  }

  abstract void doAbort()
    throws SQLException;

  void closeProxySession()
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createUnsupportedFeatureSqlException();
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public Properties getServerSessionInfo()
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createUnsupportedFeatureSqlException();
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public synchronized void applyConnectionAttributes(Properties paramProperties)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 152);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public synchronized Properties getConnectionAttributes()
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 152);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public synchronized Properties getUnMatchedConnectionAttributes()
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 152);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public synchronized void setAbandonedTimeoutEnabled(boolean paramBoolean)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 152);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public synchronized void registerConnectionCacheCallback(OracleConnectionCacheCallback paramOracleConnectionCacheCallback, Object paramObject, int paramInt)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 152);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public OracleConnectionCacheCallback getConnectionCacheCallbackObj()
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 152);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public Object getConnectionCacheCallbackPrivObj()
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 152);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public int getConnectionCacheCallbackFlag()
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 152);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public synchronized void setConnectionReleasePriority(int paramInt)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 152);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public int getConnectionReleasePriority()
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 152);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public synchronized boolean isClosed()
    throws SQLException
  {
    return this.lifecycle != 1;
  }

  public synchronized boolean isProxySession()
  {
    return this.isProxy;
  }

  public synchronized void openProxySession(int paramInt, Properties paramProperties)
    throws SQLException
  {
    if (this.isProxy)
    {
      localObject1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 149);
      ((SQLException)localObject1).fillInStackTrace();
      throw ((Throwable)localObject1);
    }

    purgeStatementCache();
    closeStatements(false);

    Object localObject1 = paramProperties.getProperty("PROXY_USER_NAME");
    String str1 = paramProperties.getProperty("PROXY_USER_PASSWORD");
    String str2 = paramProperties.getProperty("PROXY_DISTINGUISHED_NAME");

    Object localObject2 = paramProperties.get("PROXY_CERTIFICATE");
    Object localObject3;
    if (paramInt == 1)
    {
      if ((localObject1 == null) && (str1 == null))
      {
        localObject3 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 150);
        ((SQLException)localObject3).fillInStackTrace();
        throw ((Throwable)localObject3);
      }
    }
    else if (paramInt == 2)
    {
      if (str2 == null)
      {
        localObject3 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 150);
        ((SQLException)localObject3).fillInStackTrace();
        throw ((Throwable)localObject3);
      }
    }
    else if (paramInt == 3)
    {
      if (localObject2 == null)
      {
        localObject3 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 150);
        ((SQLException)localObject3).fillInStackTrace();
        throw ((Throwable)localObject3);
      }

      try
      {
        localObject3 = (byte[])localObject2;
      }
      catch (ClassCastException localClassCastException)
      {
        SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 150);
        localSQLException2.fillInStackTrace();
        throw localSQLException2;
      }

    }
    else
    {
      SQLException localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 150);
      localSQLException1.fillInStackTrace();
      throw localSQLException1;
    }

    doProxySession(paramInt, paramProperties);
    this.dci += 1;
  }

  void doProxySession(int paramInt, Properties paramProperties)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createUnsupportedFeatureSqlException();
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  void cleanup()
  {
    this.fdo = null;
    this.conversion = null;
    this.statements = null;
    this.descriptorCacheStack[this.dci] = null;
    this.map = null;
    this.javaObjectMap = null;
    this.statementHoldingLine = null;
    this.sqlObj = null;
    this.isProxy = false;
  }

  public synchronized DatabaseMetaData getMetaData()
    throws SQLException
  {
    if (this.lifecycle != 1)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 8);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if (this.databaseMetaData == null) {
      this.databaseMetaData = new OracleDatabaseMetaData(this);
    }
    return this.databaseMetaData;
  }

  public void setReadOnly(boolean paramBoolean)
    throws SQLException
  {
    this.readOnly = paramBoolean;
  }

  public boolean isReadOnly()
    throws SQLException
  {
    return this.readOnly;
  }

  public void setCatalog(String paramString)
    throws SQLException
  {
  }

  public String getCatalog()
    throws SQLException
  {
    return null;
  }

  public synchronized void setTransactionIsolation(int paramInt)
    throws SQLException
  {
    if (this.txnLevel == paramInt) {
      return;
    }
    Statement localStatement = createStatement();
    try
    {
      switch (paramInt)
      {
      case 2:
        localStatement.execute("ALTER SESSION SET ISOLATION_LEVEL = READ COMMITTED");

        this.txnLevel = 2;

        break;
      case 8:
        localStatement.execute("ALTER SESSION SET ISOLATION_LEVEL = SERIALIZABLE");

        this.txnLevel = 8;

        break;
      default:
        SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 30);
        localSQLException.fillInStackTrace();
        throw localSQLException;
      }

    }
    finally
    {
      localStatement.close();
    }
  }

  public int getTransactionIsolation()
    throws SQLException
  {
    return this.txnLevel;
  }

  public synchronized void setAutoClose(boolean paramBoolean)
    throws SQLException
  {
    if (!paramBoolean)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 31);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  public boolean getAutoClose()
    throws SQLException
  {
    return true;
  }

  public SQLWarning getWarnings()
    throws SQLException
  {
    return this.sqlWarning;
  }

  public void clearWarnings()
    throws SQLException
  {
    this.sqlWarning = null;
  }

  public void setWarnings(SQLWarning paramSQLWarning)
  {
    this.sqlWarning = paramSQLWarning;
  }

  public void setDefaultRowPrefetch(int paramInt)
    throws SQLException
  {
    if (paramInt <= 0)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 20);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.defaultRowPrefetch = paramInt;
  }

  public int getDefaultRowPrefetch()
  {
    return this.defaultRowPrefetch;
  }

  public boolean getTimestamptzInGmt()
  {
    return this.timestamptzInGmt;
  }

  public synchronized void setDefaultExecuteBatch(int paramInt)
    throws SQLException
  {
    if (paramInt <= 0)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 42);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.defaultExecuteBatch = paramInt;
  }

  public synchronized int getDefaultExecuteBatch()
  {
    return this.defaultExecuteBatch;
  }

  public synchronized void setRemarksReporting(boolean paramBoolean)
  {
    this.reportRemarks = paramBoolean;
  }

  public synchronized boolean getRemarksReporting()
  {
    return this.reportRemarks;
  }

  public void setIncludeSynonyms(boolean paramBoolean)
  {
    this.includeSynonyms = paramBoolean;
  }

  public synchronized String[] getEndToEndMetrics()
    throws SQLException
  {
    String[] arrayOfString;
    if (this.endToEndValues == null)
    {
      arrayOfString = null;
    }
    else
    {
      arrayOfString = new String[this.endToEndValues.length];

      System.arraycopy(this.endToEndValues, 0, arrayOfString, 0, this.endToEndValues.length);
    }
    return arrayOfString;
  }

  public short getEndToEndECIDSequenceNumber()
    throws SQLException
  {
    return this.endToEndECIDSequenceNumber;
  }

  public synchronized void setEndToEndMetrics(String[] paramArrayOfString, short paramShort)
    throws SQLException
  {
    String[] arrayOfString = new String[paramArrayOfString.length];

    System.arraycopy(paramArrayOfString, 0, arrayOfString, 0, paramArrayOfString.length);
    setEndToEndMetricsInternal(arrayOfString, paramShort);
  }

  void setEndToEndMetricsInternal(String[] paramArrayOfString, short paramShort)
    throws SQLException
  {
    if (paramArrayOfString != this.endToEndValues)
    {
      if (paramArrayOfString.length != 4)
      {
        SQLException localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 156);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }
      String str;
      for (int i = 0; i < 4; i++)
      {
        str = paramArrayOfString[i];

        if ((str != null) && (str.length() > this.endToEndMaxLength[i]))
        {
          SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 159, str);
          localSQLException2.fillInStackTrace();
          throw localSQLException2;
        }

      }

      if (this.endToEndValues != null)
      {
        for (i = 0; i < 4; i++)
        {
          str = paramArrayOfString[i];

          if (((str == null) && (this.endToEndValues[i] != null)) || ((str != null) && (!str.equals(this.endToEndValues[i]))))
          {
            this.endToEndHasChanged[i] = true;
            this.endToEndAnyChanged = true;
          }

        }

        this.endToEndHasChanged[0] |= this.endToEndHasChanged[3];
      }
      else
      {
        for (i = 0; i < 4; i++)
        {
          this.endToEndHasChanged[i] = true;
        }

        this.endToEndAnyChanged = true;
      }

      this.endToEndValues = paramArrayOfString;
    }

    this.endToEndECIDSequenceNumber = paramShort;
  }

  void updateSystemContext()
    throws SQLException
  {
  }

  void resetSystemContext()
  {
  }

  void updateSystemContext11()
    throws SQLException
  {
  }

  public boolean getIncludeSynonyms()
  {
    return this.includeSynonyms;
  }

  public void setRestrictGetTables(boolean paramBoolean)
  {
    this.restrictGettables = paramBoolean;
  }

  public boolean getRestrictGetTables()
  {
    return this.restrictGettables;
  }

  public void setDefaultFixedString(boolean paramBoolean)
  {
    this.fixedString = paramBoolean;
  }

  public void setDefaultNChar(boolean paramBoolean)
  {
    this.defaultnchar = paramBoolean;
  }

  public boolean getDefaultFixedString()
  {
    return this.fixedString;
  }

  public int getNlsRatio()
  {
    return 1;
  }

  public int getC2SNlsRatio()
  {
    return 1;
  }

  synchronized void addStatement(OracleStatement paramOracleStatement)
  {
    if (paramOracleStatement.next != null) {
      throw new Error("add_statement called twice on " + paramOracleStatement);
    }
    paramOracleStatement.next = this.statements;

    if (this.statements != null) {
      this.statements.prev = paramOracleStatement;
    }
    this.statements = paramOracleStatement;
  }

  synchronized void removeStatement(OracleStatement paramOracleStatement)
  {
    OracleStatement localOracleStatement1 = paramOracleStatement.prev;
    OracleStatement localOracleStatement2 = paramOracleStatement.next;

    if (localOracleStatement1 == null)
    {
      if (this.statements != paramOracleStatement) {
        return;
      }
      this.statements = localOracleStatement2;
    }
    else {
      localOracleStatement1.next = localOracleStatement2;
    }
    if (localOracleStatement2 != null) {
      localOracleStatement2.prev = localOracleStatement1;
    }
    paramOracleStatement.next = null;
    paramOracleStatement.prev = null;
  }

  synchronized void closeStatements(boolean paramBoolean)
    throws SQLException
  {
    Object localObject = this.statements;
    OracleStatement localOracleStatement;
    while (localObject != null)
    {
      localOracleStatement = ((OracleStatement)localObject).nextChild;

      if (((OracleStatement)localObject).serverCursor)
      {
        ((OracleStatement)localObject).close();
        removeStatement((OracleStatement)localObject);
      }

      localObject = localOracleStatement;
    }

    localObject = this.statements;

    while (localObject != null)
    {
      localOracleStatement = ((OracleStatement)localObject).next;

      if (paramBoolean)
        ((OracleStatement)localObject).close();
      else
        ((OracleStatement)localObject).hardClose();
      removeStatement((OracleStatement)localObject);

      localObject = localOracleStatement;
    }
  }

  final void purgeStatementCache()
    throws SQLException
  {
    if (isStatementCacheInitialized())
    {
      this.statementCache.purgeImplicitCache();
      this.statementCache.purgeExplicitCache();
    }
  }

  final void closeStatementCache()
    throws SQLException
  {
    if (isStatementCacheInitialized())
    {
      this.statementCache.close();

      this.statementCache = null;
      this.clearStatementMetaData = true;
    }
  }

  void needLine()
    throws SQLException
  {
    if (this.lifecycle != 1)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 8);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    needLineUnchecked();
  }

  synchronized void needLineUnchecked()
    throws SQLException
  {
    if (this.statementHoldingLine != null)
    {
      this.statementHoldingLine.freeLine();
    }
  }

  synchronized void holdLine(oracle.jdbc.internal.OracleStatement paramOracleStatement)
  {
    holdLine((OracleStatement)paramOracleStatement);
  }

  synchronized void holdLine(OracleStatement paramOracleStatement)
  {
    this.statementHoldingLine = paramOracleStatement;
  }

  synchronized void releaseLine()
  {
    releaseLineForCancel();
  }

  void releaseLineForCancel()
  {
    this.statementHoldingLine = null;
  }

  public synchronized void startup(String paramString, int paramInt)
    throws SQLException
  {
    if (this.lifecycle != 1)
    {
      localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 8);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    SQLException localSQLException = DatabaseError.createUnsupportedFeatureSqlException();
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public synchronized void startup(OracleConnection.DatabaseStartupMode paramDatabaseStartupMode)
    throws SQLException
  {
    SQLException localSQLException;
    if (this.lifecycle != 1)
    {
      localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 8);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
    if (paramDatabaseStartupMode == null)
    {
      localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    needLine();
    doStartup(paramDatabaseStartupMode.getMode());
  }

  void doStartup(int paramInt)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createUnsupportedFeatureSqlException();
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public synchronized void shutdown(OracleConnection.DatabaseShutdownMode paramDatabaseShutdownMode)
    throws SQLException
  {
    SQLException localSQLException;
    if (this.lifecycle != 1)
    {
      localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 8);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
    if (paramDatabaseShutdownMode == null)
    {
      localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    needLine();
    doShutdown(paramDatabaseShutdownMode.getMode());
  }

  void doShutdown(int paramInt)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createUnsupportedFeatureSqlException();
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public synchronized void archive(int paramInt1, int paramInt2, String paramString)
    throws SQLException
  {
    if (this.lifecycle != 1)
    {
      localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 8);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    SQLException localSQLException = DatabaseError.createUnsupportedFeatureSqlException();
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public synchronized void registerSQLType(String paramString1, String paramString2)
    throws SQLException
  {
    if ((paramString1 == null) || (paramString2 == null))
    {
      SQLException localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68);
      localSQLException1.fillInStackTrace();
      throw localSQLException1;
    }

    try
    {
      registerSQLType(paramString1, Class.forName(paramString2));
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
      SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 1, "Class not found: " + paramString2);
      localSQLException2.fillInStackTrace();
      throw localSQLException2;
    }
  }

  public synchronized void registerSQLType(String paramString, Class paramClass)
    throws SQLException
  {
    if ((paramString == null) || (paramClass == null))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if (this.map == null) this.map = new Hashtable(10);

    this.map.put(paramString, paramClass);
    this.map.put(paramClass.getName(), paramString);
  }

  public synchronized String getSQLType(Object paramObject)
    throws SQLException
  {
    if ((paramObject != null) && (this.map != null))
    {
      String str = paramObject.getClass().getName();

      return (String)this.map.get(str);
    }

    return null;
  }

  public synchronized Object getJavaObject(String paramString)
    throws SQLException
  {
    Object localObject = null;
    try
    {
      if ((paramString != null) && (this.map != null))
      {
        Class localClass = (Class)this.map.get(paramString);

        localObject = localClass.newInstance();
      }
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      localIllegalAccessException.printStackTrace();
    }
    catch (InstantiationException localInstantiationException)
    {
      localInstantiationException.printStackTrace();
    }

    return localObject;
  }

  public synchronized void putDescriptor(String paramString, Object paramObject)
    throws SQLException
  {
    if ((paramString != null) && (paramObject != null))
    {
      if (this.descriptorCacheStack[this.dci] == null) {
        this.descriptorCacheStack[this.dci] = new Hashtable(10);
      }
      ((TypeDescriptor)paramObject).fixupConnection(this);
      this.descriptorCacheStack[this.dci].put(paramString, paramObject);
    }
    else
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  public synchronized Object getDescriptor(String paramString)
  {
    Object localObject = null;

    if (paramString != null) {
      if (this.descriptorCacheStack[this.dci] != null)
        localObject = this.descriptorCacheStack[this.dci].get(paramString);
      if ((localObject == null) && (this.dci == 1) && (this.descriptorCacheStack[0] != null)) {
        localObject = this.descriptorCacheStack[0].get(paramString);
      }
    }
    return localObject;
  }

  /** @deprecated */
  public synchronized void removeDecriptor(String paramString)
  {
    removeDescriptor(paramString);
  }

  public synchronized void removeDescriptor(String paramString)
  {
    if ((paramString != null) && (this.descriptorCacheStack[this.dci] != null))
      this.descriptorCacheStack[this.dci].remove(paramString);
    if ((paramString != null) && (this.dci == 1) && (this.descriptorCacheStack[0] != null))
      this.descriptorCacheStack[0].remove(paramString);
  }

  public synchronized void removeAllDescriptor()
  {
    for (int i = 0; i <= this.dci; i++)
      if (this.descriptorCacheStack[i] != null)
        this.descriptorCacheStack[i].clear();
  }

  public int numberOfDescriptorCacheEntries()
  {
    int i = 0;
    for (int j = 0; j <= this.dci; j++) {
      if (this.descriptorCacheStack[j] != null)
        i += this.descriptorCacheStack[j].size();
    }
    return i;
  }

  public Enumeration descriptorCacheKeys()
  {
    if (this.dci == 0) {
      if (this.descriptorCacheStack[this.dci] != null) {
        return this.descriptorCacheStack[this.dci].keys();
      }
      return null;
    }
    if ((this.descriptorCacheStack[0] == null) && (this.descriptorCacheStack[1] != null))
      return this.descriptorCacheStack[1].keys();
    if ((this.descriptorCacheStack[1] == null) && (this.descriptorCacheStack[0] != null))
      return this.descriptorCacheStack[0].keys();
    if ((this.descriptorCacheStack[0] == null) && (this.descriptorCacheStack[1] == null)) {
      return null;
    }
    Vector localVector = new Vector(this.descriptorCacheStack[1].keySet());
    localVector.addAll(this.descriptorCacheStack[0].keySet());
    return localVector.elements();
  }

  public synchronized void putDescriptor(byte[] paramArrayOfByte, Object paramObject)
    throws SQLException
  {
    if ((paramArrayOfByte != null) && (paramObject != null))
    {
      if (this.descriptorCacheStack[this.dci] == null) {
        this.descriptorCacheStack[this.dci] = new Hashtable(10);
      }
      this.descriptorCacheStack[this.dci].put(new ByteArrayKey(paramArrayOfByte), paramObject);
    }
    else
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  public synchronized Object getDescriptor(byte[] paramArrayOfByte)
  {
    Object localObject = null;

    if (paramArrayOfByte != null) {
      ByteArrayKey localByteArrayKey = new ByteArrayKey(paramArrayOfByte);
      if (this.descriptorCacheStack[this.dci] != null)
        localObject = this.descriptorCacheStack[this.dci].get(localByteArrayKey);
      if ((localObject == null) && (this.dci == 1) && (this.descriptorCacheStack[0] != null)) {
        localObject = this.descriptorCacheStack[0].get(localByteArrayKey);
      }
    }
    return localObject;
  }

  public synchronized void removeDecriptor(byte[] paramArrayOfByte)
  {
    if (paramArrayOfByte != null) {
      ByteArrayKey localByteArrayKey = new ByteArrayKey(paramArrayOfByte);
      if (this.descriptorCacheStack[this.dci] != null)
        this.descriptorCacheStack[this.dci].remove(localByteArrayKey);
      if ((this.dci == 1) && (this.descriptorCacheStack[0] != null))
        this.descriptorCacheStack[0].remove(localByteArrayKey);
    }
  }

  public short getJdbcCsId()
    throws SQLException
  {
    if (this.conversion == null)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 65);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    return this.conversion.getClientCharSet();
  }

  public short getDbCsId()
    throws SQLException
  {
    if (this.conversion == null)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 65);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    return this.conversion.getServerCharSetId();
  }

  public short getNCsId()
    throws SQLException
  {
    if (this.conversion == null)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 65);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    return this.conversion.getNCharSetId();
  }

  public short getStructAttrCsId()
    throws SQLException
  {
    return getDbCsId();
  }

  public short getStructAttrNCsId()
    throws SQLException
  {
    return getNCsId();
  }

  public synchronized Map getTypeMap()
  {
    if (this.map == null) this.map = new Hashtable(10);
    return this.map;
  }

  public synchronized void setTypeMap(Map paramMap)
  {
    this.map = paramMap;
  }

  public synchronized void setUsingXAFlag(boolean paramBoolean)
  {
    this.usingXA = paramBoolean;
  }

  public synchronized boolean getUsingXAFlag()
  {
    return this.usingXA;
  }

  public synchronized void setXAErrorFlag(boolean paramBoolean)
  {
    this.xaWantsError = paramBoolean;
  }

  public synchronized boolean getXAErrorFlag()
  {
    return this.xaWantsError;
  }

  String getPropertyFromDatabase(String paramString)
    throws SQLException
  {
    String str = null;
    Statement localStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localStatement = createStatement();
      localStatement.setFetchSize(1);
      localResultSet = localStatement.executeQuery(paramString);
      if (localResultSet.next())
        str = localResultSet.getString(1);
    }
    finally
    {
      if (localResultSet != null)
        localResultSet.close();
      if (localStatement != null)
        localStatement.close();
    }
    return str;
  }

  public synchronized String getUserName()
    throws SQLException
  {
    if (this.userName == null) {
      this.userName = getPropertyFromDatabase("SELECT USER FROM DUAL");
    }
    return this.userName;
  }

  public String getCurrentSchema()
    throws SQLException
  {
    return getPropertyFromDatabase("SELECT SYS_CONTEXT('USERENV', 'CURRENT_SCHEMA') FROM DUAL");
  }

  public String getDefaultSchemaNameForNamedTypes()
    throws SQLException
  {
    String str = null;

    if (this.createDescriptorUseCurrentSchemaForSchemaName)
      str = getCurrentSchema();
    else {
      str = getUserName();
    }
    return str;
  }

  public synchronized void setStartTime(long paramLong)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 152);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public synchronized long getStartTime()
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 152);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  void registerHeartbeat()
    throws SQLException
  {
    if (this.logicalConnectionAttached != null)
      this.logicalConnectionAttached.registerHeartbeat();
  }

  public int getHeartbeatNoChangeCount()
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 152);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public synchronized byte[] getFDO(boolean paramBoolean)
    throws SQLException
  {
    if ((this.fdo == null) && (paramBoolean))
    {
      CallableStatement localCallableStatement = null;
      try
      {
        localCallableStatement = prepareCall("begin :1 := dbms_pickler.get_format (:2); end;");

        localCallableStatement.registerOutParameter(1, 2);
        localCallableStatement.registerOutParameter(2, -4);
        localCallableStatement.execute();

        this.fdo = localCallableStatement.getBytes(2);
      }
      finally
      {
        if (localCallableStatement != null) {
          localCallableStatement.close();
        }
        localCallableStatement = null;
      }
    }

    return this.fdo;
  }

  public synchronized void setFDO(byte[] paramArrayOfByte)
    throws SQLException
  {
    this.fdo = paramArrayOfByte;
  }

  public synchronized boolean getBigEndian()
    throws SQLException
  {
    if (this.bigEndian == null)
    {
      int[] arrayOfInt = Util.toJavaUnsignedBytes(getFDO(true));

      int i = arrayOfInt[(6 + arrayOfInt[5] + arrayOfInt[6] + 5)];

      int j = (byte)(i & 0x10);

      if (j < 0) {
        j += 256;
      }
      if (j > 0)
        this.bigEndian = Boolean.TRUE;
      else {
        this.bigEndian = Boolean.FALSE;
      }
    }
    return this.bigEndian.booleanValue();
  }

  public void setHoldability(int paramInt)
    throws SQLException
  {
  }

  public int getHoldability()
    throws SQLException
  {
    return 1;
  }

  public synchronized Savepoint setSavepoint()
    throws SQLException
  {
    return oracleSetSavepoint();
  }

  public synchronized Savepoint setSavepoint(String paramString)
    throws SQLException
  {
    return oracleSetSavepoint(paramString);
  }

  public synchronized void rollback(Savepoint paramSavepoint)
    throws SQLException
  {
    disallowGlobalTxnMode(122);

    if (this.autocommit)
    {
      localObject = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 121);
      ((SQLException)localObject).fillInStackTrace();
      throw ((Throwable)localObject);
    }

    if (this.savepointStatement == null)
    {
      this.savepointStatement = createStatement();
    }

    Object localObject = null;
    try
    {
      localObject = paramSavepoint.getSavepointName();
    }
    catch (SQLException localSQLException)
    {
      localObject = "ORACLE_SVPT_" + paramSavepoint.getSavepointId();
    }

    this.savepointStatement.executeUpdate("ROLLBACK TO " + (String)localObject);
  }

  public synchronized void releaseSavepoint(Savepoint paramSavepoint)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createUnsupportedFeatureSqlException();
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public Statement createStatement(int paramInt1, int paramInt2, int paramInt3)
    throws SQLException
  {
    return createStatement(paramInt1, paramInt2);
  }

  public PreparedStatement prepareStatement(String paramString, int paramInt1, int paramInt2, int paramInt3)
    throws SQLException
  {
    return prepareStatement(paramString, paramInt1, paramInt2);
  }

  public CallableStatement prepareCall(String paramString, int paramInt1, int paramInt2, int paramInt3)
    throws SQLException
  {
    return prepareCall(paramString, paramInt1, paramInt2);
  }

  public PreparedStatement prepareStatement(String paramString, int paramInt)
    throws SQLException
  {
    AutoKeyInfo localAutoKeyInfo = new AutoKeyInfo(paramString);
    if ((paramInt == 2) || (!localAutoKeyInfo.isInsertSqlStmt()))
    {
      return prepareStatement(paramString);
    }
    if (paramInt != 1)
    {
      localObject = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68);
      ((SQLException)localObject).fillInStackTrace();
      throw ((Throwable)localObject);
    }

    Object localObject = localAutoKeyInfo.getNewSql();
    oracle.jdbc.OraclePreparedStatement localOraclePreparedStatement = (oracle.jdbc.OraclePreparedStatement)prepareStatement((String)localObject);
    OraclePreparedStatement localOraclePreparedStatement1 = (OraclePreparedStatement)((OraclePreparedStatementWrapper)localOraclePreparedStatement).preparedStatement;

    localOraclePreparedStatement1.isAutoGeneratedKey = true;
    localOraclePreparedStatement1.autoKeyInfo = localAutoKeyInfo;
    localOraclePreparedStatement1.registerReturnParamsForAutoKey();
    return localOraclePreparedStatement;
  }

  public PreparedStatement prepareStatement(String paramString, int[] paramArrayOfInt)
    throws SQLException
  {
    AutoKeyInfo localAutoKeyInfo = new AutoKeyInfo(paramString, paramArrayOfInt);

    if (!localAutoKeyInfo.isInsertSqlStmt()) return prepareStatement(paramString);

    if ((paramArrayOfInt == null) || (paramArrayOfInt.length == 0))
    {
      localObject = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68);
      ((SQLException)localObject).fillInStackTrace();
      throw ((Throwable)localObject);
    }

    doDescribeTable(localAutoKeyInfo);

    Object localObject = localAutoKeyInfo.getNewSql();
    oracle.jdbc.OraclePreparedStatement localOraclePreparedStatement = (oracle.jdbc.OraclePreparedStatement)prepareStatement((String)localObject);
    OraclePreparedStatement localOraclePreparedStatement1 = (OraclePreparedStatement)((OraclePreparedStatementWrapper)localOraclePreparedStatement).preparedStatement;

    localOraclePreparedStatement1.isAutoGeneratedKey = true;
    localOraclePreparedStatement1.autoKeyInfo = localAutoKeyInfo;
    localOraclePreparedStatement1.registerReturnParamsForAutoKey();
    return localOraclePreparedStatement;
  }

  public PreparedStatement prepareStatement(String paramString, String[] paramArrayOfString)
    throws SQLException
  {
    AutoKeyInfo localAutoKeyInfo = new AutoKeyInfo(paramString, paramArrayOfString);
    if (!localAutoKeyInfo.isInsertSqlStmt()) return prepareStatement(paramString);

    if ((paramArrayOfString == null) || (paramArrayOfString.length == 0))
    {
      localObject = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68);
      ((SQLException)localObject).fillInStackTrace();
      throw ((Throwable)localObject);
    }

    doDescribeTable(localAutoKeyInfo);

    Object localObject = localAutoKeyInfo.getNewSql();
    oracle.jdbc.OraclePreparedStatement localOraclePreparedStatement = (oracle.jdbc.OraclePreparedStatement)prepareStatement((String)localObject);
    OraclePreparedStatement localOraclePreparedStatement1 = (OraclePreparedStatement)((OraclePreparedStatementWrapper)localOraclePreparedStatement).preparedStatement;

    localOraclePreparedStatement1.isAutoGeneratedKey = true;
    localOraclePreparedStatement1.autoKeyInfo = localAutoKeyInfo;
    localOraclePreparedStatement1.registerReturnParamsForAutoKey();
    return localOraclePreparedStatement;
  }

  public synchronized oracle.jdbc.OracleSavepoint oracleSetSavepoint()
    throws SQLException
  {
    disallowGlobalTxnMode(117);

    if (this.autocommit)
    {
      localObject = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 120);
      ((SQLException)localObject).fillInStackTrace();
      throw ((Throwable)localObject);
    }

    if (this.savepointStatement == null)
    {
      this.savepointStatement = createStatement();
    }

    Object localObject = new OracleSavepoint();

    String str = "SAVEPOINT ORACLE_SVPT_" + ((OracleSavepoint)localObject).getSavepointId();

    this.savepointStatement.executeUpdate(str);

    return localObject;
  }

  public synchronized oracle.jdbc.OracleSavepoint oracleSetSavepoint(String paramString)
    throws SQLException
  {
    disallowGlobalTxnMode(117);

    if (this.autocommit)
    {
      localObject = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 120);
      ((SQLException)localObject).fillInStackTrace();
      throw ((Throwable)localObject);
    }

    if (this.savepointStatement == null)
    {
      this.savepointStatement = createStatement();
    }

    Object localObject = new OracleSavepoint(paramString);

    String str = "SAVEPOINT " + ((OracleSavepoint)localObject).getSavepointName();

    this.savepointStatement.executeUpdate(str);

    return localObject;
  }

  public synchronized void oracleRollback(oracle.jdbc.OracleSavepoint paramOracleSavepoint)
    throws SQLException
  {
    disallowGlobalTxnMode(115);

    if (this.autocommit)
    {
      localObject = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 121);
      ((SQLException)localObject).fillInStackTrace();
      throw ((Throwable)localObject);
    }

    if (this.savepointStatement == null)
    {
      this.savepointStatement = createStatement();
    }

    Object localObject = null;
    try
    {
      localObject = paramOracleSavepoint.getSavepointName();
    }
    catch (SQLException localSQLException)
    {
      localObject = "ORACLE_SVPT_" + paramOracleSavepoint.getSavepointId();
    }

    this.savepointStatement.executeUpdate("ROLLBACK TO " + (String)localObject);
  }

  public synchronized void oracleReleaseSavepoint(oracle.jdbc.OracleSavepoint paramOracleSavepoint)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createUnsupportedFeatureSqlException();
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  void disallowGlobalTxnMode(int paramInt)
    throws SQLException
  {
    if (this.txnMode == 1)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), paramInt);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  public void setTxnMode(int paramInt)
  {
    this.txnMode = paramInt;
  }

  public int getTxnMode()
  {
    return this.txnMode;
  }

  public synchronized Object getClientData(Object paramObject)
  {
    if (this.clientData == null)
    {
      return null;
    }

    return this.clientData.get(paramObject);
  }

  public synchronized Object setClientData(Object paramObject1, Object paramObject2)
  {
    if (this.clientData == null)
    {
      this.clientData = new Hashtable();
    }

    return this.clientData.put(paramObject1, paramObject2);
  }

  public synchronized Object removeClientData(Object paramObject)
  {
    if (this.clientData == null)
    {
      return null;
    }

    return this.clientData.remove(paramObject);
  }

  public BlobDBAccess createBlobDBAccess()
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createUnsupportedFeatureSqlException();
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public ClobDBAccess createClobDBAccess()
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createUnsupportedFeatureSqlException();
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public BfileDBAccess createBfileDBAccess()
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createUnsupportedFeatureSqlException();
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void printState()
  {
    try
    {
      int i = getJdbcCsId();

      int j = getDbCsId();

      int k = getStructAttrCsId();
    }
    catch (SQLException localSQLException)
    {
      localSQLException.printStackTrace();
    }
  }

  public String getProtocolType()
  {
    return this.protocol;
  }

  public String getURL()
  {
    return this.url;
  }

  /** @deprecated */
  public synchronized void setStmtCacheSize(int paramInt)
    throws SQLException
  {
    setStatementCacheSize(paramInt);
    setImplicitCachingEnabled(true);
    setExplicitCachingEnabled(true);
  }

  /** @deprecated */
  public synchronized void setStmtCacheSize(int paramInt, boolean paramBoolean)
    throws SQLException
  {
    setStatementCacheSize(paramInt);
    setImplicitCachingEnabled(true);

    setExplicitCachingEnabled(true);

    this.clearStatementMetaData = paramBoolean;
  }

  /** @deprecated */
  public synchronized int getStmtCacheSize()
  {
    int i = 0;
    try
    {
      i = getStatementCacheSize();
    }
    catch (SQLException localSQLException)
    {
    }

    if (i == -1)
    {
      i = 0;
    }

    return i;
  }

  public synchronized void setStatementCacheSize(int paramInt)
    throws SQLException
  {
    if (this.statementCache == null)
    {
      this.statementCache = new LRUStatementCache(paramInt);
    }
    else
    {
      this.statementCache.resize(paramInt);
    }
  }

  public synchronized int getStatementCacheSize()
    throws SQLException
  {
    if (this.statementCache == null) {
      return -1;
    }
    return this.statementCache.getCacheSize();
  }

  public synchronized void setImplicitCachingEnabled(boolean paramBoolean)
    throws SQLException
  {
    if (this.statementCache == null)
    {
      this.statementCache = new LRUStatementCache(0);
    }

    this.statementCache.setImplicitCachingEnabled(paramBoolean);
  }

  public synchronized boolean getImplicitCachingEnabled()
    throws SQLException
  {
    if (this.statementCache == null) {
      return false;
    }
    return this.statementCache.getImplicitCachingEnabled();
  }

  public synchronized void setExplicitCachingEnabled(boolean paramBoolean)
    throws SQLException
  {
    if (this.statementCache == null)
    {
      this.statementCache = new LRUStatementCache(0);
    }

    this.statementCache.setExplicitCachingEnabled(paramBoolean);
  }

  public synchronized boolean getExplicitCachingEnabled()
    throws SQLException
  {
    if (this.statementCache == null) {
      return false;
    }
    return this.statementCache.getExplicitCachingEnabled();
  }

  public synchronized void purgeImplicitCache()
    throws SQLException
  {
    if (this.statementCache != null)
      this.statementCache.purgeImplicitCache();
  }

  public synchronized void purgeExplicitCache()
    throws SQLException
  {
    if (this.statementCache != null)
      this.statementCache.purgeExplicitCache();
  }

  public synchronized PreparedStatement getStatementWithKey(String paramString)
    throws SQLException
  {
    if (this.statementCache != null)
    {
      OracleStatement localOracleStatement = this.statementCache.searchExplicitCache(paramString);

      if ((localOracleStatement == null) || (localOracleStatement.statementType == 1)) {
        return (PreparedStatement)localOracleStatement;
      }

      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 125);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    return null;
  }

  public synchronized CallableStatement getCallWithKey(String paramString)
    throws SQLException
  {
    if (this.statementCache != null)
    {
      OracleStatement localOracleStatement = this.statementCache.searchExplicitCache(paramString);

      if ((localOracleStatement == null) || (localOracleStatement.statementType == 2)) {
        return (CallableStatement)localOracleStatement;
      }

      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 125);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    return null;
  }

  public synchronized void cacheImplicitStatement(OraclePreparedStatement paramOraclePreparedStatement, String paramString, int paramInt1, int paramInt2)
    throws SQLException
  {
    if (this.statementCache == null)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 95);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.statementCache.addToImplicitCache(paramOraclePreparedStatement, paramString, paramInt1, paramInt2);
  }

  public synchronized void cacheExplicitStatement(OraclePreparedStatement paramOraclePreparedStatement, String paramString)
    throws SQLException
  {
    if (this.statementCache == null)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 95);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.statementCache.addToExplicitCache(paramOraclePreparedStatement, paramString);
  }

  public synchronized boolean isStatementCacheInitialized()
  {
    if (this.statementCache == null)
      return false;
    if (this.statementCache.getCacheSize() == 0) {
      return false;
    }
    return true;
  }

  private BufferCacheStore getBufferCacheStore()
  {
    if (this.useThreadLocalBufferCache) {
      if (threadLocalBufferCacheStore == null)
      {
        BufferCacheStore.MAX_CACHED_BUFFER_SIZE = this.maxCachedBufferSize;
        threadLocalBufferCacheStore = new ThreadLocal()
        {
          protected PhysicalConnection.BufferCacheStore initialValue()
          {
            return new PhysicalConnection.BufferCacheStore();
          }
        };
      }
      return (BufferCacheStore)threadLocalBufferCacheStore.get();
    }

    if (this.connectionBufferCacheStore == null)
    {
      this.connectionBufferCacheStore = new BufferCacheStore(this.maxCachedBufferSize);
    }
    return this.connectionBufferCacheStore;
  }

  void cacheBuffer(byte[] paramArrayOfByte)
  {
    if (paramArrayOfByte != null) {
      BufferCacheStore localBufferCacheStore = getBufferCacheStore();
      localBufferCacheStore.byteBufferCache.put(paramArrayOfByte);
    }
  }

  void cacheBuffer(char[] paramArrayOfChar)
  {
    if (paramArrayOfChar != null) {
      BufferCacheStore localBufferCacheStore = getBufferCacheStore();
      localBufferCacheStore.charBufferCache.put(paramArrayOfChar);
    }
  }

  public void cacheBufferSync(char[] paramArrayOfChar)
  {
    synchronized (this) {
      cacheBuffer(paramArrayOfChar);
    }
  }

  byte[] getByteBuffer(int paramInt)
  {
    BufferCacheStore localBufferCacheStore = getBufferCacheStore();
    return (byte[])localBufferCacheStore.byteBufferCache.get(Byte.TYPE, paramInt);
  }

  char[] getCharBuffer(int paramInt)
  {
    BufferCacheStore localBufferCacheStore = getBufferCacheStore();
    return (char[])localBufferCacheStore.charBufferCache.get(Character.TYPE, paramInt);
  }

  public char[] getCharBufferSync(int paramInt)
  {
    synchronized (this) {
      return getCharBuffer(paramInt);
    }
  }

  public OracleConnection.BufferCacheStatistics getByteBufferCacheStatistics()
  {
    BufferCacheStore localBufferCacheStore = getBufferCacheStore();
    return localBufferCacheStore.byteBufferCache.getStatistics();
  }

  public OracleConnection.BufferCacheStatistics getCharBufferCacheStatistics()
  {
    BufferCacheStore localBufferCacheStore = getBufferCacheStore();
    return localBufferCacheStore.charBufferCache.getStatistics();
  }

  public synchronized void registerTAFCallback(OracleOCIFailover paramOracleOCIFailover, Object paramObject)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createUnsupportedFeatureSqlException();
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public String getDatabaseProductVersion()
    throws SQLException
  {
    if (this.databaseProductVersion == "")
    {
      needLine();

      this.databaseProductVersion = doGetDatabaseProductVersion();
    }

    return this.databaseProductVersion;
  }

  public synchronized boolean getReportRemarks()
  {
    return this.reportRemarks;
  }

  public short getVersionNumber()
    throws SQLException
  {
    if (this.versionNumber == -1)
    {
      synchronized (this)
      {
        if (this.versionNumber == -1)
        {
          needLine();

          this.versionNumber = doGetVersionNumber();
        }
      }
    }

    return this.versionNumber;
  }

  public synchronized void registerCloseCallback(OracleCloseCallback paramOracleCloseCallback, Object paramObject)
  {
    this.closeCallback = paramOracleCloseCallback;
    this.privateData = paramObject;
  }

  public void setCreateStatementAsRefCursor(boolean paramBoolean)
  {
  }

  public boolean getCreateStatementAsRefCursor()
  {
    return false;
  }

  public int pingDatabase()
    throws SQLException
  {
    if (this.lifecycle != 1)
      return -1;
    return doPingDatabase();
  }

  /** @deprecated */
  public int pingDatabase(int paramInt)
    throws SQLException
  {
    if (this.lifecycle != 1)
      return -1;
    if (paramInt == 0) {
      return pingDatabase();
    }
    try
    {
      this.pingResult = -2;
      Thread localThread = new Thread(new Runnable() {
        public void run() {
          try {
            PhysicalConnection.this.pingResult = PhysicalConnection.this.doPingDatabase();
          }
          catch (Throwable localThrowable)
          {
          }
        }
      });
      localThread.start();
      localThread.join(paramInt * 1000);
      return this.pingResult;
    } catch (InterruptedException localInterruptedException) {
    }
    return -3;
  }

  int doPingDatabase()
    throws SQLException
  {
    Statement localStatement = null;
    try
    {
      localStatement = createStatement();

      ((oracle.jdbc.OracleStatement)localStatement).defineColumnType(1, 12, 1);
      localStatement.executeQuery("SELECT 'x' FROM DUAL");
    }
    catch (SQLException localSQLException)
    {
      return -1;
    }
    finally
    {
      if (localStatement != null) {
        localStatement.close();
      }
    }
    return 0;
  }

  public synchronized Map getJavaObjectTypeMap()
  {
    return this.javaObjectMap;
  }

  public synchronized void setJavaObjectTypeMap(Map paramMap)
  {
    this.javaObjectMap = paramMap;
  }

  /** @deprecated */
  public void clearClientIdentifier(String paramString)
    throws SQLException
  {
    if ((paramString != null) && (paramString.length() != 0))
    {
      String[] arrayOfString = getEndToEndMetrics();

      if ((arrayOfString != null) && (paramString.equals(arrayOfString[1])))
      {
        arrayOfString[1] = null;

        setEndToEndMetrics(arrayOfString, getEndToEndECIDSequenceNumber());
      }
    }
  }

  /** @deprecated */
  public void setClientIdentifier(String paramString)
    throws SQLException
  {
    String[] arrayOfString = getEndToEndMetrics();

    if (arrayOfString == null)
    {
      arrayOfString = new String[4];
    }

    arrayOfString[1] = paramString;

    setEndToEndMetrics(arrayOfString, getEndToEndECIDSequenceNumber());
  }

  public void setSessionTimeZone(String paramString)
    throws SQLException
  {
    Statement localStatement = null;
    Object localObject1 = null;
    try
    {
      localStatement = createStatement();

      localStatement.executeUpdate("ALTER SESSION SET TIME_ZONE = '" + paramString + "'");

      if (this.dbTzCalendar == null) {
        setDbTzCalendar(getDatabaseTimeZone());
      }
    }
    catch (SQLException localSQLException)
    {
      throw localSQLException;
    }
    finally
    {
      if (localStatement != null) {
        localStatement.close();
      }
    }
    this.sessionTimeZone = paramString;
  }

  public String getDatabaseTimeZone()
    throws SQLException
  {
    if (this.databaseTimeZone == null)
    {
      this.databaseTimeZone = getPropertyFromDatabase("SELECT DBTIMEZONE FROM DUAL");
    }

    return this.databaseTimeZone;
  }

  public String getSessionTimeZone()
  {
    return this.sessionTimeZone;
  }

  private static String to2DigitString(int paramInt)
  {
    String str;
    if (paramInt < 10)
      str = "0" + paramInt;
    else {
      str = "" + paramInt;
    }
    return str;
  }

  public String tzToOffset(String paramString)
  {
    if (paramString == null) {
      return paramString;
    }
    int i = paramString.charAt(0);

    if ((i != 45) && (i != 43))
    {
      TimeZone localTimeZone = TimeZone.getTimeZone(paramString);

      int j = localTimeZone.getOffset(System.currentTimeMillis());

      if (j != 0)
      {
        int k = j / 60000;
        int m = k / 60;
        k -= m * 60;

        if (j > 0)
        {
          paramString = "+" + to2DigitString(m) + ":" + to2DigitString(k);
        }
        else
        {
          paramString = "-" + to2DigitString(-m) + ":" + to2DigitString(-k);
        }

      }
      else
      {
        paramString = "+00:00";
      }
    }

    return paramString;
  }

  public String getSessionTimeZoneOffset()
    throws SQLException
  {
    String str = getPropertyFromDatabase("SELECT SESSIONTIMEZONE FROM DUAL");

    if (str != null)
    {
      str = tzToOffset(str.trim());
    }

    return str;
  }

  private void setDbTzCalendar(String paramString)
  {
    int i = paramString.charAt(0);

    if ((i == 45) || (i == 43)) {
      paramString = "GMT" + paramString;
    }
    TimeZone localTimeZone = TimeZone.getTimeZone(paramString);

    this.dbTzCalendar = new GregorianCalendar(localTimeZone);
  }

  public Calendar getDbTzCalendar()
    throws SQLException
  {
    if (this.dbTzCalendar == null)
    {
      setDbTzCalendar(getDatabaseTimeZone());
    }

    return this.dbTzCalendar;
  }

  public void setAccumulateBatchResult(boolean paramBoolean)
  {
    this.accumulateBatchResult = paramBoolean;
  }

  public boolean isAccumulateBatchResult()
  {
    return this.accumulateBatchResult;
  }

  public void setJ2EE13Compliant(boolean paramBoolean)
  {
    this.j2ee13Compliant = paramBoolean;
  }

  public boolean getJ2EE13Compliant()
  {
    return this.j2ee13Compliant;
  }

  public Class classForNameAndSchema(String paramString1, String paramString2)
    throws ClassNotFoundException
  {
    return Class.forName(paramString1);
  }

  public Class safelyGetClassForName(String paramString)
    throws ClassNotFoundException
  {
    return Class.forName(paramString);
  }

  public int getHeapAllocSize()
    throws SQLException
  {
    if (this.lifecycle != 1)
    {
      localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 8);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    SQLException localSQLException = DatabaseError.createUnsupportedFeatureSqlException();
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public int getOCIEnvHeapAllocSize()
    throws SQLException
  {
    if (this.lifecycle != 1)
    {
      localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 8);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    SQLException localSQLException = DatabaseError.createUnsupportedFeatureSqlException();
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public static OracleConnection unwrapCompletely(oracle.jdbc.OracleConnection paramOracleConnection)
  {
    Object localObject1 = paramOracleConnection;
    Object localObject2 = localObject1;
    while (true)
    {
      if (localObject2 == null) {
        return (OracleConnection)localObject1;
      }
      localObject1 = localObject2;
      localObject2 = ((oracle.jdbc.OracleConnection)localObject1).unwrap();
    }
  }

  public void setWrapper(oracle.jdbc.OracleConnection paramOracleConnection)
  {
    this.wrapper = paramOracleConnection;
  }

  public oracle.jdbc.OracleConnection unwrap()
  {
    return null;
  }

  public oracle.jdbc.OracleConnection getWrapper()
  {
    if (this.wrapper != null) {
      return this.wrapper;
    }
    return this;
  }

  static oracle.jdbc.internal.OracleConnection _physicalConnectionWithin(Connection paramConnection)
  {
    OracleConnection localOracleConnection = null;

    if (paramConnection != null)
    {
      localOracleConnection = unwrapCompletely((oracle.jdbc.OracleConnection)paramConnection);
    }

    return localOracleConnection;
  }

  public oracle.jdbc.internal.OracleConnection physicalConnectionWithin()
  {
    return this;
  }

  public long getTdoCState(String paramString1, String paramString2)
    throws SQLException
  {
    return 0L;
  }

  public void getOracleTypeADT(OracleTypeADT paramOracleTypeADT)
    throws SQLException
  {
  }

  public Datum toDatum(CustomDatum paramCustomDatum) throws SQLException
  {
    return paramCustomDatum.toDatum(this);
  }

  public short getNCharSet()
  {
    return this.conversion.getNCharSetId();
  }

  public ResultSet newArrayDataResultSet(Datum[] paramArrayOfDatum, long paramLong, int paramInt, Map paramMap)
    throws SQLException
  {
    return new ArrayDataResultSet(this, paramArrayOfDatum, paramLong, paramInt, paramMap);
  }

  public ResultSet newArrayDataResultSet(ARRAY paramARRAY, long paramLong, int paramInt, Map paramMap)
    throws SQLException
  {
    return new ArrayDataResultSet(this, paramARRAY, paramLong, paramInt, paramMap);
  }

  public ResultSet newArrayLocatorResultSet(ArrayDescriptor paramArrayDescriptor, byte[] paramArrayOfByte, long paramLong, int paramInt, Map paramMap)
    throws SQLException
  {
    return new ArrayLocatorResultSet(this, paramArrayDescriptor, paramArrayOfByte, paramLong, paramInt, paramMap);
  }

  public ResultSetMetaData newStructMetaData(StructDescriptor paramStructDescriptor)
    throws SQLException
  {
    return new StructMetaData(paramStructDescriptor);
  }

  public int CHARBytesToJavaChars(byte[] paramArrayOfByte, int paramInt, char[] paramArrayOfChar)
    throws SQLException
  {
    int[] arrayOfInt = new int[1];

    arrayOfInt[0] = paramInt;

    return this.conversion.CHARBytesToJavaChars(paramArrayOfByte, 0, paramArrayOfChar, 0, arrayOfInt, paramArrayOfChar.length);
  }

  public int NCHARBytesToJavaChars(byte[] paramArrayOfByte, int paramInt, char[] paramArrayOfChar)
    throws SQLException
  {
    int[] arrayOfInt = new int[1];

    return this.conversion.NCHARBytesToJavaChars(paramArrayOfByte, 0, paramArrayOfChar, 0, arrayOfInt, paramArrayOfChar.length);
  }

  public boolean IsNCharFixedWith()
  {
    return this.conversion.IsNCharFixedWith();
  }

  public short getDriverCharSet()
  {
    return this.conversion.getClientCharSet();
  }

  public int getMaxCharSize()
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 58);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public int getMaxCharbyteSize()
  {
    return this.conversion.getMaxCharbyteSize();
  }

  public int getMaxNCharbyteSize()
  {
    return this.conversion.getMaxNCharbyteSize();
  }

  public boolean isCharSetMultibyte(short paramShort)
  {
    return DBConversion.isCharSetMultibyte(paramShort);
  }

  public int javaCharsToCHARBytes(char[] paramArrayOfChar, int paramInt, byte[] paramArrayOfByte)
    throws SQLException
  {
    return this.conversion.javaCharsToCHARBytes(paramArrayOfChar, paramInt, paramArrayOfByte);
  }

  public int javaCharsToNCHARBytes(char[] paramArrayOfChar, int paramInt, byte[] paramArrayOfByte)
    throws SQLException
  {
    return this.conversion.javaCharsToNCHARBytes(paramArrayOfChar, paramInt, paramArrayOfByte);
  }

  public abstract void getPropertyForPooledConnection(OraclePooledConnection paramOraclePooledConnection)
    throws SQLException;

  final void getPropertyForPooledConnection(OraclePooledConnection paramOraclePooledConnection, String paramString)
    throws SQLException
  {
    Hashtable localHashtable = new Hashtable();

    localHashtable.put("obj_type_map", this.javaObjectMap);

    Properties localProperties = new Properties();

    localProperties.put("user", this.userName);
    localProperties.put("password", paramString);

    localProperties.put("connection_url", this.url);
    localProperties.put("connect_auto_commit", "" + this.autocommit);

    localProperties.put("trans_isolation", "" + this.txnLevel);

    if (getStatementCacheSize() != -1)
    {
      localProperties.put("stmt_cache_size", "" + getStatementCacheSize());

      localProperties.put("implicit_cache_enabled", "" + getImplicitCachingEnabled());

      localProperties.put("explict_cache_enabled", "" + getExplicitCachingEnabled());
    }

    localProperties.put("defaultExecuteBatch", "" + this.defaultExecuteBatch);
    localProperties.put("defaultRowPrefetch", "" + this.defaultRowPrefetch);
    localProperties.put("remarksReporting", "" + this.reportRemarks);
    localProperties.put("AccumulateBatchResult", "" + this.accumulateBatchResult);
    localProperties.put("oracle.jdbc.J2EE13Compliant", "" + this.j2ee13Compliant);
    localProperties.put("processEscapes", "" + this.processEscapes);

    localProperties.put("restrictGetTables", "" + this.restrictGettables);
    localProperties.put("includeSynonyms", "" + this.includeSynonyms);
    localProperties.put("fixedString", "" + this.fixedString);

    localHashtable.put("connection_properties", localProperties);

    paramOraclePooledConnection.setProperties(localHashtable);
  }

  public Properties getDBAccessProperties()
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createUnsupportedFeatureSqlException();
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public Properties getOCIHandles()
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createUnsupportedFeatureSqlException();
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  abstract void logon()
    throws SQLException;

  void logoff()
    throws SQLException
  {
  }

  abstract void open(OracleStatement paramOracleStatement)
    throws SQLException;

  abstract void cancelOperationOnServer()
    throws SQLException;

  abstract void doSetAutoCommit(boolean paramBoolean)
    throws SQLException;

  abstract void doCommit(int paramInt)
    throws SQLException;

  abstract void doRollback()
    throws SQLException;

  abstract String doGetDatabaseProductVersion()
    throws SQLException;

  abstract short doGetVersionNumber()
    throws SQLException;

  int getDefaultStreamChunkSize()
  {
    return this.streamChunkSize;
  }

  abstract OracleStatement RefCursorBytesToStatement(byte[] paramArrayOfByte, OracleStatement paramOracleStatement)
    throws SQLException;

  public oracle.jdbc.internal.OracleStatement refCursorCursorToStatement(int paramInt)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createUnsupportedFeatureSqlException();
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public Connection getLogicalConnection(OraclePooledConnection paramOraclePooledConnection, boolean paramBoolean)
    throws SQLException
  {
    if ((this.logicalConnectionAttached != null) || (paramOraclePooledConnection.getPhysicalHandle() != this))
    {
      localObject = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 143);
      ((SQLException)localObject).fillInStackTrace();
      throw ((Throwable)localObject);
    }

    Object localObject = new LogicalConnection(paramOraclePooledConnection, this, paramBoolean);

    this.logicalConnectionAttached = ((LogicalConnection)localObject);

    return localObject;
  }

  public void getForm(OracleTypeADT paramOracleTypeADT, OracleTypeCLOB paramOracleTypeCLOB, int paramInt)
    throws SQLException
  {
  }

  public CLOB createClob(byte[] paramArrayOfByte)
    throws SQLException
  {
    return new CLOB(this, paramArrayOfByte);
  }

  public CLOB createClobWithUnpickledBytes(byte[] paramArrayOfByte)
    throws SQLException
  {
    return new CLOB(this, paramArrayOfByte, true);
  }

  public CLOB createClob(byte[] paramArrayOfByte, short paramShort)
    throws SQLException
  {
    if (paramShort == 2) {
      return new NCLOB(this, paramArrayOfByte);
    }

    return new CLOB(this, paramArrayOfByte, paramShort);
  }

  public BLOB createBlob(byte[] paramArrayOfByte)
    throws SQLException
  {
    return new BLOB(this, paramArrayOfByte);
  }

  public BLOB createBlobWithUnpickledBytes(byte[] paramArrayOfByte)
    throws SQLException
  {
    return new BLOB(this, paramArrayOfByte, true);
  }

  public BFILE createBfile(byte[] paramArrayOfByte)
    throws SQLException
  {
    return new BFILE(this, paramArrayOfByte);
  }

  public ARRAY createARRAY(String paramString, Object paramObject)
    throws SQLException
  {
    ArrayDescriptor localArrayDescriptor = ArrayDescriptor.createDescriptor(paramString, this);
    return new ARRAY(localArrayDescriptor, this, paramObject);
  }

  public BINARY_DOUBLE createBINARY_DOUBLE(double paramDouble)
    throws SQLException
  {
    return new BINARY_DOUBLE(paramDouble);
  }

  public BINARY_FLOAT createBINARY_FLOAT(float paramFloat)
    throws SQLException
  {
    return new BINARY_FLOAT(paramFloat);
  }

  public DATE createDATE(Date paramDate)
    throws SQLException
  {
    return new DATE(paramDate);
  }

  public DATE createDATE(Time paramTime)
    throws SQLException
  {
    return new DATE(paramTime);
  }

  public DATE createDATE(Timestamp paramTimestamp)
    throws SQLException
  {
    return new DATE(paramTimestamp);
  }

  public DATE createDATE(Date paramDate, Calendar paramCalendar)
    throws SQLException
  {
    return new DATE(paramDate);
  }

  public DATE createDATE(Time paramTime, Calendar paramCalendar)
    throws SQLException
  {
    return new DATE(paramTime);
  }

  public DATE createDATE(Timestamp paramTimestamp, Calendar paramCalendar)
    throws SQLException
  {
    return new DATE(paramTimestamp);
  }

  public DATE createDATE(String paramString)
    throws SQLException
  {
    return new DATE(paramString);
  }

  public INTERVALDS createINTERVALDS(String paramString)
    throws SQLException
  {
    return new INTERVALDS(paramString);
  }

  public INTERVALYM createINTERVALYM(String paramString)
    throws SQLException
  {
    return new INTERVALYM(paramString);
  }

  public NUMBER createNUMBER(boolean paramBoolean)
    throws SQLException
  {
    return new NUMBER(paramBoolean);
  }

  public NUMBER createNUMBER(byte paramByte)
    throws SQLException
  {
    return new NUMBER(paramByte);
  }

  public NUMBER createNUMBER(short paramShort)
    throws SQLException
  {
    return new NUMBER(paramShort);
  }

  public NUMBER createNUMBER(int paramInt)
    throws SQLException
  {
    return new NUMBER(paramInt);
  }

  public NUMBER createNUMBER(long paramLong)
    throws SQLException
  {
    return new NUMBER(paramLong);
  }

  public NUMBER createNUMBER(float paramFloat)
    throws SQLException
  {
    return new NUMBER(paramFloat);
  }

  public NUMBER createNUMBER(double paramDouble)
    throws SQLException
  {
    return new NUMBER(paramDouble);
  }

  public NUMBER createNUMBER(BigDecimal paramBigDecimal)
    throws SQLException
  {
    return new NUMBER(paramBigDecimal);
  }

  public NUMBER createNUMBER(BigInteger paramBigInteger)
    throws SQLException
  {
    return new NUMBER(paramBigInteger);
  }

  public NUMBER createNUMBER(String paramString, int paramInt)
    throws SQLException
  {
    return new NUMBER(paramString, paramInt);
  }

  public Array createArrayOf(String paramString, Object[] paramArrayOfObject)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createUnsupportedFeatureSqlException();
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public Struct createStruct(String paramString, Object[] paramArrayOfObject)
    throws SQLException
  {
    StructDescriptor localStructDescriptor = StructDescriptor.createDescriptor(paramString, this);
    return new STRUCT(localStructDescriptor, this, paramArrayOfObject);
  }

  public TIMESTAMP createTIMESTAMP(Date paramDate)
    throws SQLException
  {
    return new TIMESTAMP(paramDate);
  }

  public TIMESTAMP createTIMESTAMP(DATE paramDATE)
    throws SQLException
  {
    return new TIMESTAMP(paramDATE);
  }

  public TIMESTAMP createTIMESTAMP(Time paramTime)
    throws SQLException
  {
    return new TIMESTAMP(paramTime);
  }

  public TIMESTAMP createTIMESTAMP(Timestamp paramTimestamp)
    throws SQLException
  {
    return new TIMESTAMP(paramTimestamp);
  }

  public TIMESTAMP createTIMESTAMP(String paramString)
    throws SQLException
  {
    return new TIMESTAMP(paramString);
  }

  public TIMESTAMPTZ createTIMESTAMPTZ(Date paramDate)
    throws SQLException
  {
    return new TIMESTAMPTZ(this, paramDate);
  }

  public TIMESTAMPTZ createTIMESTAMPTZ(Date paramDate, Calendar paramCalendar)
    throws SQLException
  {
    return new TIMESTAMPTZ(this, paramDate, paramCalendar);
  }

  public TIMESTAMPTZ createTIMESTAMPTZ(Time paramTime)
    throws SQLException
  {
    return new TIMESTAMPTZ(this, paramTime);
  }

  public TIMESTAMPTZ createTIMESTAMPTZ(Time paramTime, Calendar paramCalendar)
    throws SQLException
  {
    return new TIMESTAMPTZ(this, paramTime, paramCalendar);
  }

  public TIMESTAMPTZ createTIMESTAMPTZ(Timestamp paramTimestamp)
    throws SQLException
  {
    return new TIMESTAMPTZ(this, paramTimestamp);
  }

  public TIMESTAMPTZ createTIMESTAMPTZ(Timestamp paramTimestamp, Calendar paramCalendar)
    throws SQLException
  {
    return new TIMESTAMPTZ(this, paramTimestamp, paramCalendar);
  }

  public TIMESTAMPTZ createTIMESTAMPTZ(String paramString)
    throws SQLException
  {
    return new TIMESTAMPTZ(this, paramString);
  }

  public TIMESTAMPTZ createTIMESTAMPTZ(String paramString, Calendar paramCalendar)
    throws SQLException
  {
    return new TIMESTAMPTZ(this, paramString, paramCalendar);
  }

  public TIMESTAMPTZ createTIMESTAMPTZ(DATE paramDATE)
    throws SQLException
  {
    return new TIMESTAMPTZ(this, paramDATE);
  }

  public TIMESTAMPLTZ createTIMESTAMPLTZ(Date paramDate, Calendar paramCalendar)
    throws SQLException
  {
    return new TIMESTAMPLTZ(this, paramCalendar, paramDate);
  }

  public TIMESTAMPLTZ createTIMESTAMPLTZ(Time paramTime, Calendar paramCalendar)
    throws SQLException
  {
    return new TIMESTAMPLTZ(this, paramCalendar, paramTime);
  }

  public TIMESTAMPLTZ createTIMESTAMPLTZ(Timestamp paramTimestamp, Calendar paramCalendar)
    throws SQLException
  {
    return new TIMESTAMPLTZ(this, paramCalendar, paramTimestamp);
  }

  public TIMESTAMPLTZ createTIMESTAMPLTZ(String paramString, Calendar paramCalendar)
    throws SQLException
  {
    return new TIMESTAMPLTZ(this, paramCalendar, paramString);
  }

  public TIMESTAMPLTZ createTIMESTAMPLTZ(DATE paramDATE, Calendar paramCalendar)
    throws SQLException
  {
    return new TIMESTAMPLTZ(this, paramCalendar, paramDATE);
  }

  public abstract BLOB createTemporaryBlob(Connection paramConnection, boolean paramBoolean, int paramInt)
    throws SQLException;

  public abstract CLOB createTemporaryClob(Connection paramConnection, boolean paramBoolean, int paramInt, short paramShort)
    throws SQLException;

  public Blob createBlob()
    throws SQLException
  {
    if (this.lifecycle != 1)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 8);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    return createTemporaryBlob(this, true, 10);
  }

  public Clob createClob()
    throws SQLException
  {
    if (this.lifecycle != 1)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 8);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    return createTemporaryClob(this, true, 10, (short)1);
  }

  public NClob createNClob()
    throws SQLException
  {
    if (this.lifecycle != 1)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 8);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    return (NClob)createTemporaryClob(this, true, 10, (short)2);
  }

  public SQLXML createSQLXML()
    throws SQLException
  {
    if (this.lifecycle != 1)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 8);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    return new OracleSQLXML(this);
  }

  public boolean isDescriptorSharable(oracle.jdbc.internal.OracleConnection paramOracleConnection)
    throws SQLException
  {
    PhysicalConnection localPhysicalConnection1 = this;
    PhysicalConnection localPhysicalConnection2 = (PhysicalConnection)paramOracleConnection.getPhysicalConnection();

    return (localPhysicalConnection1 == localPhysicalConnection2) || (localPhysicalConnection1.url.equals(localPhysicalConnection2.url)) || ((localPhysicalConnection2.protocol != null) && (localPhysicalConnection2.protocol.equals("kprb")));
  }

  boolean useLittleEndianSetCHARBinder()
    throws SQLException
  {
    return false;
  }

  public void setPlsqlWarnings(String paramString)
    throws SQLException
  {
    if (paramString == null)
    {
      localObject1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68);
      ((SQLException)localObject1).fillInStackTrace();
      throw ((Throwable)localObject1);
    }

    if ((paramString != null) && ((paramString = paramString.trim()).length() > 0) && (!OracleSql.isValidPlsqlWarning(paramString)))
    {
      localObject1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68);
      ((SQLException)localObject1).fillInStackTrace();
      throw ((Throwable)localObject1);
    }

    Object localObject1 = "ALTER SESSION SET PLSQL_WARNINGS=" + paramString;

    String str = "ALTER SESSION SET EVENTS='10933 TRACE NAME CONTEXT LEVEL 32768'";

    Statement localStatement = null;
    try
    {
      localStatement = createStatement(-1, -1);

      localStatement.execute((String)localObject1);

      if (paramString.equals("'DISABLE:ALL'"))
      {
        this.plsqlCompilerWarnings = false;
      }
      else
      {
        localStatement.execute(str);

        this.plsqlCompilerWarnings = true;
      }
    }
    finally {
      if (localStatement != null)
        localStatement.close();
    }
  }

  void internalClose()
    throws SQLException
  {
    this.lifecycle = 4;

    Object localObject = this.statements;
    OracleStatement localOracleStatement;
    while (localObject != null)
    {
      localOracleStatement = ((OracleStatement)localObject).nextChild;

      if (((OracleStatement)localObject).serverCursor)
      {
        ((OracleStatement)localObject).internalClose();
        removeStatement((OracleStatement)localObject);
      }

      localObject = localOracleStatement;
    }

    localObject = this.statements;

    while (localObject != null)
    {
      localOracleStatement = ((OracleStatement)localObject).next;
      ((OracleStatement)localObject).internalClose();
      localObject = localOracleStatement;
    }

    this.statements = null;
  }

  public XAResource getXAResource()
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 164);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  protected void doDescribeTable(AutoKeyInfo paramAutoKeyInfo)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createUnsupportedFeatureSqlException();
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setApplicationContext(String paramString1, String paramString2, String paramString3)
    throws SQLException
  {
    if ((paramString1 == null) || (paramString2 == null) || (paramString3 == null))
    {
      throw new NullPointerException();
    }
    SQLException localSQLException;
    if (paramString1.equals(""))
    {
      localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 170);

      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if (paramString1.compareToIgnoreCase("CLIENTCONTEXT") != 0)
    {
      localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 174);

      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if (paramString2.length() > 30)
    {
      localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 171);

      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if (paramString3.length() > 4000)
    {
      localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 172);

      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    doSetApplicationContext(paramString1, paramString2, paramString3);
  }

  void doSetApplicationContext(String paramString1, String paramString2, String paramString3)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createUnsupportedFeatureSqlException();
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void clearAllApplicationContext(String paramString)
    throws SQLException
  {
    if (paramString == null)
      throw new NullPointerException();
    if (paramString.equals(""))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 170);

      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    doClearAllApplicationContext(paramString);
  }

  void doClearAllApplicationContext(String paramString)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createUnsupportedFeatureSqlException();
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public byte[] createLightweightSession(String paramString, KeywordValueLong[] paramArrayOfKeywordValueLong, int paramInt, KeywordValueLong[][] paramArrayOfKeywordValueLong1, int[] paramArrayOfInt)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createUnsupportedFeatureSqlException();
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void executeLightweightSessionRoundtrip(int paramInt1, byte[] paramArrayOfByte, KeywordValueLong[] paramArrayOfKeywordValueLong, int paramInt2, KeywordValueLong[][] paramArrayOfKeywordValueLong1, int[] paramArrayOfInt)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createUnsupportedFeatureSqlException();
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void executeLightweightSessionPiggyback(int paramInt1, byte[] paramArrayOfByte, KeywordValueLong[] paramArrayOfKeywordValueLong, int paramInt2)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createUnsupportedFeatureSqlException();
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void doXSNamespaceOp(OracleConnection.XSOperationCode paramXSOperationCode, byte[] paramArrayOfByte, XSNamespace[] paramArrayOfXSNamespace, XSNamespace[][] paramArrayOfXSNamespace1)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createUnsupportedFeatureSqlException();
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void doXSNamespaceOp(OracleConnection.XSOperationCode paramXSOperationCode, byte[] paramArrayOfByte, XSNamespace[] paramArrayOfXSNamespace)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createUnsupportedFeatureSqlException();
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void enqueue(String paramString, AQEnqueueOptions paramAQEnqueueOptions, AQMessage paramAQMessage)
    throws SQLException
  {
    AQMessageI localAQMessageI = (AQMessageI)paramAQMessage;

    byte[][] arrayOfByte = new byte[1][];

    doEnqueue(paramString, paramAQEnqueueOptions, localAQMessageI.getMessagePropertiesI(), localAQMessageI.getPayloadTOID(), localAQMessageI.getPayload(), arrayOfByte, localAQMessageI.isRAWPayload());

    if (arrayOfByte[0] != null)
      localAQMessageI.setMessageId(arrayOfByte[0]);
  }

  public AQMessage dequeue(String paramString, AQDequeueOptions paramAQDequeueOptions, byte[] paramArrayOfByte)
    throws SQLException
  {
    byte[][] arrayOfByte1 = new byte[1][];
    AQMessagePropertiesI localAQMessagePropertiesI = new AQMessagePropertiesI();
    byte[][] arrayOfByte2 = new byte[1][];
    boolean bool = false;

    bool = doDequeue(paramString, paramAQDequeueOptions, localAQMessagePropertiesI, paramArrayOfByte, arrayOfByte2, arrayOfByte1, AQMessageI.compareToid(paramArrayOfByte, TypeDescriptor.RAWTOID));

    AQMessageI localAQMessageI = null;
    if (bool)
    {
      localAQMessageI = new AQMessageI(localAQMessagePropertiesI, this);
      localAQMessageI.setPayload(arrayOfByte2[0], paramArrayOfByte);
      localAQMessageI.setMessageId(arrayOfByte1[0]);
    }
    return localAQMessageI;
  }

  public AQMessage dequeue(String paramString1, AQDequeueOptions paramAQDequeueOptions, String paramString2)
    throws SQLException
  {
    byte[] arrayOfByte = null;
    TypeDescriptor localTypeDescriptor = null;
    if (("RAW".equals(paramString2)) || ("SYS.RAW".equals(paramString2)))
    {
      arrayOfByte = TypeDescriptor.RAWTOID;
    } else if ("SYS.ANYDATA".equals(paramString2)) {
      arrayOfByte = TypeDescriptor.ANYDATATOID;
    } else if ("SYS.XMLTYPE".equals(paramString2)) {
      arrayOfByte = TypeDescriptor.XMLTYPETOID;
    }
    else {
      localTypeDescriptor = TypeDescriptor.getTypeDescriptor(paramString2, this);
      arrayOfByte = ((OracleTypeADT)localTypeDescriptor.getPickler()).getTOID();
    }
    AQMessageI localAQMessageI = (AQMessageI)dequeue(paramString1, paramAQDequeueOptions, arrayOfByte);
    if (localAQMessageI != null)
    {
      localAQMessageI.setTypeName(paramString2);
      localAQMessageI.setTypeDescriptor(localTypeDescriptor);
    }
    return localAQMessageI;
  }

  synchronized void doEnqueue(String paramString, AQEnqueueOptions paramAQEnqueueOptions, AQMessagePropertiesI paramAQMessagePropertiesI, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[][] paramArrayOfByte, boolean paramBoolean)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createUnsupportedFeatureSqlException();
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  synchronized boolean doDequeue(String paramString, AQDequeueOptions paramAQDequeueOptions, AQMessagePropertiesI paramAQMessagePropertiesI, byte[] paramArrayOfByte, byte[][] paramArrayOfByte1, byte[][] paramArrayOfByte2, boolean paramBoolean)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createUnsupportedFeatureSqlException();
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public boolean isGetObjectReturnsXMLType()
  {
    return this.getObjectReturnsXmlType;
  }

  /** @deprecated */
  public boolean isV8Compatible()
    throws SQLException
  {
    return this.mapDateToTimestamp;
  }

  public boolean getMapDateToTimestamp()
  {
    return this.mapDateToTimestamp;
  }

  public byte getInstanceProperty(OracleConnection.InstanceProperty paramInstanceProperty)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createUnsupportedFeatureSqlException();
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public AQNotificationRegistration[] registerAQNotification(String[] paramArrayOfString, Properties[] paramArrayOfProperties, Properties paramProperties)
    throws SQLException
  {
    String str = readNTFlocalhost(paramProperties);
    int i = readNTFtcpport(paramProperties);

    NTFAQRegistration[] arrayOfNTFAQRegistration = doRegisterAQNotification(paramArrayOfString, str, i, paramArrayOfProperties);

    return (AQNotificationRegistration[])arrayOfNTFAQRegistration;
  }

  NTFAQRegistration[] doRegisterAQNotification(String[] paramArrayOfString, String paramString, int paramInt, Properties[] paramArrayOfProperties)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createUnsupportedFeatureSqlException();
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void unregisterAQNotification(AQNotificationRegistration paramAQNotificationRegistration)
    throws SQLException
  {
    NTFAQRegistration localNTFAQRegistration = (NTFAQRegistration)paramAQNotificationRegistration;
    doUnregisterAQNotification(localNTFAQRegistration);
  }

  void doUnregisterAQNotification(NTFAQRegistration paramNTFAQRegistration)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createUnsupportedFeatureSqlException();
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  private String readNTFlocalhost(Properties paramProperties)
    throws SQLException
  {
    String str = null;
    try
    {
      str = paramProperties.getProperty("NTF_LOCAL_HOST", InetAddress.getLocalHost().getHostAddress());
    }
    catch (UnknownHostException localUnknownHostException)
    {
      localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 240);

      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
    catch (SecurityException localSecurityException)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 241);

      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    return str;
  }

  private int readNTFtcpport(Properties paramProperties)
    throws SQLException
  {
    int i = 0;
    try
    {
      i = Integer.parseInt(paramProperties.getProperty("NTF_LOCAL_TCP_PORT", "0"));

      if (i < 0)
      {
        SQLException localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 242);

        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

    }
    catch (NumberFormatException localNumberFormatException)
    {
      SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 242);

      localSQLException2.fillInStackTrace();
      throw localSQLException2;
    }

    return i;
  }

  int readNTFtimeout(Properties paramProperties)
    throws SQLException
  {
    int i = 0;
    try
    {
      i = Integer.parseInt(paramProperties.getProperty("NTF_TIMEOUT", "0"));
    }
    catch (NumberFormatException localNumberFormatException)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 243);

      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    return i;
  }

  public DatabaseChangeRegistration registerDatabaseChangeNotification(Properties paramProperties)
    throws SQLException
  {
    String str = readNTFlocalhost(paramProperties);
    int i = readNTFtcpport(paramProperties);
    int j = readNTFtimeout(paramProperties);
    int k = 0;
    try
    {
      k = Integer.parseInt(paramProperties.getProperty("DCN_NOTIFY_CHANGELAG", "0"));
    }
    catch (NumberFormatException localNumberFormatException)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 244);

      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    NTFDCNRegistration localNTFDCNRegistration = doRegisterDatabaseChangeNotification(str, i, paramProperties, j, k);

    ntfManager.addRegistration(localNTFDCNRegistration);
    return localNTFDCNRegistration;
  }

  NTFDCNRegistration doRegisterDatabaseChangeNotification(String paramString, int paramInt1, Properties paramProperties, int paramInt2, int paramInt3)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createUnsupportedFeatureSqlException();
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public DatabaseChangeRegistration getDatabaseChangeRegistration(int paramInt)
    throws SQLException
  {
    NTFDCNRegistration localNTFDCNRegistration = new NTFDCNRegistration(this.instanceName, paramInt, this.userName, this.versionNumber);
    return localNTFDCNRegistration;
  }

  public void unregisterDatabaseChangeNotification(DatabaseChangeRegistration paramDatabaseChangeRegistration)
    throws SQLException
  {
    NTFDCNRegistration localNTFDCNRegistration = (NTFDCNRegistration)paramDatabaseChangeRegistration;
    if (localNTFDCNRegistration.getDatabaseName().compareToIgnoreCase(this.instanceName) != 0)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 245);

      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    doUnregisterDatabaseChangeNotification(localNTFDCNRegistration);
  }

  void doUnregisterDatabaseChangeNotification(NTFDCNRegistration paramNTFDCNRegistration)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createUnsupportedFeatureSqlException();
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void unregisterDatabaseChangeNotification(int paramInt)
    throws SQLException
  {
    String str = null;
    try
    {
      str = InetAddress.getLocalHost().getHostAddress();
    }
    catch (Exception localException)
    {
    }

    unregisterDatabaseChangeNotification(paramInt, str, 47632);
  }

  public void unregisterDatabaseChangeNotification(int paramInt1, String paramString, int paramInt2)
    throws SQLException
  {
    String str = "(ADDRESS=(PROTOCOL=tcp)(HOST=" + paramString + ")(PORT=" + paramInt2 + "))?PR=0";

    unregisterDatabaseChangeNotification(paramInt1, str);
  }

  public void unregisterDatabaseChangeNotification(long paramLong, String paramString)
    throws SQLException
  {
    doUnregisterDatabaseChangeNotification(paramLong, paramString);
  }

  void doUnregisterDatabaseChangeNotification(long paramLong, String paramString)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createUnsupportedFeatureSqlException();
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void addXSEventListener(XSEventListener paramXSEventListener)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createUnsupportedFeatureSqlException();
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void addXSEventListener(XSEventListener paramXSEventListener, Executor paramExecutor)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createUnsupportedFeatureSqlException();
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void removeXSEventListener(XSEventListener paramXSEventListener)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createUnsupportedFeatureSqlException();
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public TypeDescriptor[] getAllTypeDescriptorsInCurrentSchema()
    throws SQLException
  {
    TypeDescriptor[] arrayOfTypeDescriptor = null;
    Statement localStatement = null;
    try
    {
      localStatement = createStatement();
      ResultSet localResultSet = localStatement.executeQuery("SELECT schema_name, typename, typoid, typecode, version, tds  FROM TABLE(private_jdbc.Get_Type_Shape_Info())");

      arrayOfTypeDescriptor = getTypeDescriptorsFromResultSet(localResultSet);
      localResultSet.close();
    }
    catch (SQLException localSQLException1)
    {
      if (localSQLException1.getErrorCode() == 904)
      {
        SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 165);
        localSQLException2.fillInStackTrace();
        throw localSQLException2;
      }

      throw localSQLException1;
    }
    finally
    {
      if (localStatement != null) localStatement.close();
    }
    return arrayOfTypeDescriptor;
  }

  public TypeDescriptor[] getTypeDescriptorsFromListInCurrentSchema(String[] paramArrayOfString)
    throws SQLException
  {
    String str = "SELECT schema_name, typename, typoid, typecode, version, tds  FROM TABLE(private_jdbc.Get_Type_Shape_Info(?))";
    TypeDescriptor[] arrayOfTypeDescriptor = null;
    PreparedStatement localPreparedStatement = null;
    try
    {
      localPreparedStatement = prepareStatement(str);
      int i = paramArrayOfString.length;
      localObject1 = new StringBuffer(i * 8);
      for (int j = 0; j < i; j++)
      {
        ((StringBuffer)localObject1).append(paramArrayOfString[j]);
        if (j < i - 1) ((StringBuffer)localObject1).append(',');
      }
      localPreparedStatement.setString(1, ((StringBuffer)localObject1).toString());
      ResultSet localResultSet = localPreparedStatement.executeQuery();
      arrayOfTypeDescriptor = getTypeDescriptorsFromResultSet(localResultSet);
      localResultSet.close();
    }
    catch (SQLException localSQLException)
    {
      Object localObject1;
      if (localSQLException.getErrorCode() == 904)
      {
        localObject1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 165);
        ((SQLException)localObject1).fillInStackTrace();
        throw ((Throwable)localObject1);
      }

      throw localSQLException;
    }
    finally
    {
      if (localPreparedStatement != null) localPreparedStatement.close();
    }
    return arrayOfTypeDescriptor;
  }

  public TypeDescriptor[] getTypeDescriptorsFromList(String[][] paramArrayOfString)
    throws SQLException
  {
    TypeDescriptor[] arrayOfTypeDescriptor = null;
    PreparedStatement localPreparedStatement = null;
    int i = paramArrayOfString.length;
    StringBuffer localStringBuffer1 = new StringBuffer(i * 8);
    StringBuffer localStringBuffer2 = new StringBuffer(i * 8);
    for (int j = 0; j < i; j++)
    {
      localStringBuffer1.append(paramArrayOfString[j][0]);
      localStringBuffer2.append(paramArrayOfString[j][1]);
      if (j < i - 1)
      {
        localStringBuffer1.append(',');
        localStringBuffer2.append(',');
      }
    }

    try
    {
      String str = "SELECT schema_name, typename, typoid, typecode, version, tds FROM TABLE(private_jdbc.Get_All_Type_Shape_Info(?,?))";

      localPreparedStatement = prepareStatement(str);
      localPreparedStatement.setString(1, localStringBuffer1.toString());
      localPreparedStatement.setString(2, localStringBuffer2.toString());

      localObject1 = localPreparedStatement.executeQuery();
      arrayOfTypeDescriptor = getTypeDescriptorsFromResultSet((ResultSet)localObject1);
      ((ResultSet)localObject1).close();
    }
    catch (SQLException localSQLException)
    {
      Object localObject1;
      if (localSQLException.getErrorCode() == 904)
      {
        localObject1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 165);
        ((SQLException)localObject1).fillInStackTrace();
        throw ((Throwable)localObject1);
      }

      throw localSQLException;
    }
    finally
    {
      if (localPreparedStatement != null) localPreparedStatement.close();
    }
    return arrayOfTypeDescriptor;
  }

  TypeDescriptor[] getTypeDescriptorsFromResultSet(ResultSet paramResultSet)
    throws SQLException
  {
    ArrayList localArrayList = new ArrayList();
    Object localObject2;
    while (paramResultSet.next())
    {
      localObject1 = paramResultSet.getString(1);
      String str1 = paramResultSet.getString(2);
      localObject2 = paramResultSet.getBytes(3);
      String str2 = paramResultSet.getString(4);
      int j = paramResultSet.getInt(5);
      byte[] arrayOfByte = paramResultSet.getBytes(6);
      SQLName localSQLName = new SQLName((String)localObject1, str1, this);
      Object localObject3;
      if (str2.equals("OBJECT"))
      {
        localObject3 = StructDescriptor.createDescriptor(localSQLName, (byte[])localObject2, j, arrayOfByte, this);

        putDescriptor((byte[])localObject2, localObject3);
        putDescriptor(((TypeDescriptor)localObject3).getName(), localObject3);
        localArrayList.add(localObject3);
      }
      else if (str2.equals("COLLECTION"))
      {
        localObject3 = ArrayDescriptor.createDescriptor(localSQLName, (byte[])localObject2, j, arrayOfByte, this);

        putDescriptor((byte[])localObject2, localObject3);
        putDescriptor(((TypeDescriptor)localObject3).getName(), localObject3);
        localArrayList.add(localObject3);
      }
    }
    Object localObject1 = new TypeDescriptor[localArrayList.size()];
    for (int i = 0; i < localArrayList.size(); i++)
    {
      localObject2 = (TypeDescriptor)localArrayList.get(i);
      localObject1[i] = localObject2;
    }
    return localObject1;
  }

  public synchronized boolean isUsable()
  {
    return this.isUsable;
  }

  public synchronized void setUsable(boolean paramBoolean)
  {
    this.isUsable = paramBoolean;
  }

  void queryFCFProperties(Properties paramProperties)
    throws SQLException
  {
    Statement localStatement = null;
    ResultSet localResultSet = null;
    String str1 = "select sys_context('userenv', 'instance_name'),sys_context('userenv', 'server_host'),sys_context('userenv', 'service_name'),sys_context('userenv', 'db_unique_name') from dual";
    try
    {
      localStatement = createStatement();
      localStatement.setFetchSize(1);
      localResultSet = localStatement.executeQuery(str1);
      while (localResultSet.next())
      {
        String str2 = null;
        str2 = localResultSet.getString(1);
        if (str2 != null) {
          paramProperties.put("INSTANCE_NAME", str2.trim());
        }
        str2 = localResultSet.getString(2);
        if (str2 != null) {
          paramProperties.put("SERVER_HOST", str2.trim());
        }

        str2 = localResultSet.getString(3);
        if (str2 != null) {
          paramProperties.put("SERVICE_NAME", str2.trim());
        }
        str2 = localResultSet.getString(4);
        if (str2 != null)
          paramProperties.put("DATABASE_NAME", str2.trim());
      }
    }
    finally {
      if (localResultSet != null)
        localResultSet.close();
      if (localStatement != null)
        localStatement.close();
    }
  }

  public void setDefaultTimeZone(TimeZone paramTimeZone)
    throws SQLException
  {
    this.defaultTimeZone = paramTimeZone;
  }

  public TimeZone getDefaultTimeZone()
    throws SQLException
  {
    return this.defaultTimeZone;
  }

  public int getTimezoneVersionNumber()
    throws SQLException
  {
    return this.timeZoneVersionNumber;
  }

  public TIMEZONETAB getTIMEZONETAB()
    throws SQLException
  {
    if (this.timeZoneTab == null) {
      this.timeZoneTab = TIMEZONETAB.getInstance(getTimezoneVersionNumber());
    }
    return this.timeZoneTab;
  }

  public boolean isDataInLocatorEnabled()
    throws SQLException
  {
    return (getVersionNumber() >= 10200 ? 1 : 0) & (getVersionNumber() < 11000 ? 1 : 0) & this.enableReadDataInLocator | this.overrideEnableReadDataInLocator;
  }

  private static final class BufferCacheStore
  {
    static int MAX_CACHED_BUFFER_SIZE = 2147483647;
    final BufferCache<byte[]> byteBufferCache;
    final BufferCache<char[]> charBufferCache;

    BufferCacheStore()
    {
      this(MAX_CACHED_BUFFER_SIZE);
    }

    BufferCacheStore(int paramInt) {
      this.byteBufferCache = new BufferCache(paramInt);
      this.charBufferCache = new BufferCache(paramInt);
    }
  }
}