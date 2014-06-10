package oracle.jdbc.driver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Properties;
import java.util.concurrent.Executor;
import oracle.jdbc.NotificationRegistration.RegistrationState;
import oracle.jdbc.OracleConnection.CommitOption;
import oracle.jdbc.OracleConnection.DatabaseShutdownMode;
import oracle.jdbc.OracleConnection.DatabaseStartupMode;
import oracle.jdbc.aq.AQDequeueOptions;
import oracle.jdbc.aq.AQEnqueueOptions;
import oracle.jdbc.internal.KeywordValue;
import oracle.jdbc.internal.KeywordValueLong;
import oracle.jdbc.internal.OracleConnection.InstanceProperty;
import oracle.jdbc.internal.OracleConnection.XSOperationCode;
import oracle.jdbc.internal.XSEventListener;
import oracle.jdbc.internal.XSNamespace;
import oracle.jdbc.pool.OraclePooledConnection;
import oracle.net.ns.Communication;
import oracle.net.ns.NSProtocol;
import oracle.net.ns.NetException;
import oracle.net.ns.SessionAtts;
import oracle.sql.BFILE;
import oracle.sql.BLOB;
import oracle.sql.BfileDBAccess;
import oracle.sql.BlobDBAccess;
import oracle.sql.CLOB;
import oracle.sql.ClobDBAccess;
import oracle.sql.LobPlsqlUtil;
import oracle.sql.TIMESTAMPTZ;

class T4CConnection extends PhysicalConnection
  implements BfileDBAccess, BlobDBAccess, ClobDBAccess
{
  static final short MIN_TTCVER_SUPPORTED = 4;
  static final short V8_TTCVER_SUPPORTED = 5;
  static final short MAX_TTCVER_SUPPORTED = 6;
  static final int DEFAULT_LONG_PREFETCH_SIZE = 4080;
  static final String DEFAULT_CONNECT_STRING = "localhost:1521:orcl";
  static final int STREAM_CHUNK_SIZE = 255;
  static final int REFCURSOR_SIZE = 5;
  long LOGON_MODE = 0L;
  static final long SYSDBA = 8L;
  static final long SYSOPER = 16L;
  static final long SYSASM = 128L;
  boolean isLoggedOn;
  private boolean useZeroCopyIO;
  boolean useLobPrefetch;
  private String password;
  Communication net;
  private NTFEventListener[] xsListeners = new NTFEventListener[0];
  boolean readAsNonStream;
  T4CTTIoer oer;
  T4CMAREngine mare;
  T4C8TTIpro pro;
  T4CTTIrxd rxd;
  T4CTTIsto sto;
  T4CTTIspfp spfp;
  T4CTTIoauthenticate auth;
  T4C8Odscrarr describe;
  T4C8Oall all8;
  T4C8Oclose close8;
  T4C7Ocommoncall commoncall;
  T4Caqe aqe;
  T4Caqdq aqdq;
  T4C8TTIBfile bfileMsg;
  T4C8TTIBlob blobMsg;
  T4C8TTIClob clobMsg;
  T4CTTIoses oses;
  T4CTTIoping oping;
  T4CTTIokpn okpn;
  byte[] EMPTY_BYTE = new byte[0];
  T4CTTIOtxen otxen;
  T4CTTIOtxse otxse;
  T4CTTIk2rpc k2rpc;
  T4CTTIoscid oscid;
  T4CTTIokeyval okeyval;
  T4CTTIoxsscs oxsscs;
  T4CTTIoxssro oxssro;
  T4CTTIoxsspo oxsspo;
  T4CTTIxsnsop xsnsop;
  int[] cursorToClose;
  int cursorToCloseOffset;
  int[] queryToClose;
  int queryToCloseOffset;
  int[] lusFunctionId2;
  byte[][] lusSessionId2;
  KeywordValueLong[][] lusInKeyVal2;
  int[] lusInFlags2;
  int lusOffset2;
  int sessionId;
  int serialNumber;
  byte negotiatedTTCversion;
  byte[] serverRuntimeCapabilities;
  Hashtable namespaces;
  byte[] internalName;
  byte[] externalName;
  static final int FREE = -1;
  static final int SEND = 1;
  static final int RECEIVE = 2;
  int pipeState = -1;
  boolean sentCancel = false;
  static final int MAX_SIZE_VSESSION_OSUSER = 30;
  static final int MAX_SIZE_VSESSION_PROCESS = 24;
  static final int MAX_SIZE_VSESSION_MACHINE = 64;
  static final int MAX_SIZE_VSESSION_TERMINAL = 30;
  static final int MAX_SIZE_VSESSION_PROGRAM = 48;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  T4CConnection(String paramString, Properties paramProperties, OracleDriverExtension paramOracleDriverExtension)
    throws SQLException
  {
    super(paramString, paramProperties, paramOracleDriverExtension);

    this.cursorToClose = new int[4];
    this.cursorToCloseOffset = 0;

    this.queryToClose = new int[10];
    this.queryToCloseOffset = 0;

    this.lusFunctionId2 = new int[10];
    this.lusSessionId2 = new byte[10][];
    this.lusInKeyVal2 = new KeywordValueLong[10][];
    this.lusInFlags2 = new int[10];
    this.lusOffset2 = 0;

    this.minVcsBindSize = 0;
    this.streamChunkSize = 255;

    this.namespaces = new Hashtable(5);
  }

  final void initializePassword(String paramString)
    throws SQLException
  {
    this.password = paramString;
  }

  void logon()
    throws SQLException
  {
    try
    {
      Object localObject1;
      if (this.isLoggedOn)
      {
        localObject1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 428);
        ((SQLException)localObject1).fillInStackTrace();
        throw ((Throwable)localObject1);
      }

      if (this.database == null)
      {
        this.database = "localhost:1521:orcl";
      }

      connect(this.database);

      this.all8 = new T4C8Oall(this);
      this.okpn = new T4CTTIokpn(this);
      this.close8 = new T4C8Oclose(this);
      this.sto = new T4CTTIsto(this);
      this.spfp = new T4CTTIspfp(this);
      this.commoncall = new T4C7Ocommoncall(this);
      this.describe = new T4C8Odscrarr(this);
      this.bfileMsg = new T4C8TTIBfile(this);
      this.blobMsg = new T4C8TTIBlob(this);
      this.clobMsg = new T4C8TTIClob(this);
      this.otxen = new T4CTTIOtxen(this);
      this.otxse = new T4CTTIOtxse(this);
      this.oping = new T4CTTIoping(this);
      this.k2rpc = new T4CTTIk2rpc(this);
      this.oses = new T4CTTIoses(this);
      this.okeyval = new T4CTTIokeyval(this);
      this.oxssro = new T4CTTIoxssro(this);
      this.oxsspo = new T4CTTIoxsspo(this);
      this.oxsscs = new T4CTTIoxsscs(this);
      this.xsnsop = new T4CTTIxsnsop(this);
      this.aqe = new T4Caqe(this);
      this.aqdq = new T4Caqdq(this);
      this.oscid = new T4CTTIoscid(this);

      this.LOGON_MODE = 0L;
      if (this.internalLogon != null)
      {
        if (this.internalLogon.equalsIgnoreCase("sysoper"))
        {
          this.LOGON_MODE = 64L;
        }
        else if (this.internalLogon.equalsIgnoreCase("sysdba"))
        {
          this.LOGON_MODE = 32L;
        }
        else if (this.internalLogon.equalsIgnoreCase("sysasm"))
        {
          this.LOGON_MODE = 4194304L;
        }

      }

      if (this.prelimAuth) {
        this.LOGON_MODE |= 128L;
      }
      this.auth = new T4CTTIoauthenticate(this, this.resourceManagerId);

      if ((this.userName != null) && (this.userName.length() != 0))
      {
        this.auth.doOSESSKEY(this.userName, this.LOGON_MODE);
      }

      this.auth.doOAUTH(this.userName, this.password, this.LOGON_MODE);

      this.sessionId = getSessionId();
      this.serialNumber = getSerialNumber();
      this.internalName = this.auth.internalName;
      this.externalName = this.auth.externalName;
      this.instanceName = this.sessionProperties.getProperty("AUTH_INSTANCENAME");

      if (!this.prelimAuth)
      {
        localObject1 = new T4C7Oversion(this);
        ((T4C7Oversion)localObject1).doOVERSION();

        localObject2 = ((T4C7Oversion)localObject1).getVersion();
        try
        {
          this.databaseProductVersion = new String((byte[])localObject2, "UTF8");
        }
        catch (UnsupportedEncodingException localUnsupportedEncodingException)
        {
          SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localUnsupportedEncodingException);
          localSQLException2.fillInStackTrace();
          throw localSQLException2;
        }

        this.versionNumber = ((T4C7Oversion)localObject1).getVersionNumber();
      }
      else
      {
        this.versionNumber = 0;
      }

      this.isLoggedOn = true;
    }
    catch (NetException localNetException)
    {
      localObject2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localNetException);
      ((SQLException)localObject2).fillInStackTrace();
      throw ((Throwable)localObject2);
    }
    catch (IOException localIOException)
    {
      handleIOException(localIOException);

      Object localObject2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localIOException);
      ((SQLException)localObject2).fillInStackTrace();
      throw ((Throwable)localObject2);
    }
    catch (SQLException localSQLException1)
    {
      try
      {
        this.net.disconnect();
      }
      catch (Exception localException)
      {
      }

      this.isLoggedOn = false;

      throw localSQLException1;
    }
  }

  void handleIOException(IOException paramIOException)
    throws SQLException
  {
    try
    {
      this.pipeState = -1;
      this.net.disconnect();
      this.net = null;
    }
    catch (Exception localException)
    {
    }

    this.isLoggedOn = false;
    this.lifecycle = 4;
  }

  synchronized void logoff()
    throws SQLException
  {
    try
    {
      assertLoggedOn("T4CConnection.logoff");
      if (this.lifecycle == 8) {
        return;
      }
      sendPiggyBackedMessages();
      this.commoncall.doOLOGOFF();
      this.net.disconnect();
      this.net = null;
    }
    catch (IOException localIOException)
    {
      handleIOException(localIOException);

      if (this.lifecycle != 8)
      {
        SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localIOException);
        localSQLException.fillInStackTrace();
        throw localSQLException;
      }

    }
    finally
    {
      try
      {
        if (this.net != null) {
          this.net.disconnect();
        }
      }
      catch (Exception localException4)
      {
      }

      this.isLoggedOn = false;
    }
  }

  T4CMAREngine getMarshalEngine()
  {
    return this.mare;
  }

  synchronized void doCommit(int paramInt)
    throws SQLException
  {
    assertLoggedOn("T4CConnection.do_commit");
    try
    {
      sendPiggyBackedMessages();
      if (paramInt == 0)
      {
        this.commoncall.doOCOMMIT();
      }
      else
      {
        int i = 0;
        if ((paramInt & OracleConnection.CommitOption.WRITEBATCH.getCode()) != 0) {
          i = i | 0x2 | 0x1;
        }
        else if ((paramInt & OracleConnection.CommitOption.WRITEIMMED.getCode()) != 0) {
          i |= 2;
        }
        if ((paramInt & OracleConnection.CommitOption.NOWAIT.getCode()) != 0) {
          i = i | 0x8 | 0x4;
        }
        else if ((paramInt & OracleConnection.CommitOption.WAIT.getCode()) != 0) {
          i |= 8;
        }

        this.otxen.doOTXEN(1, null, null, 0, 0, 0, 0, 4, i);

        int j = this.otxen.getOutStateFromServer();

        if ((j == 2) || (j == 4));
      }

    }
    catch (IOException localIOException)
    {
      handleIOException(localIOException);

      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localIOException);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  synchronized void doRollback()
    throws SQLException
  {
    try
    {
      assertLoggedOn("T4CConnection.do_rollback");
      sendPiggyBackedMessages();
      this.commoncall.doOROLLBACK();
    }
    catch (IOException localIOException)
    {
      handleIOException(localIOException);

      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localIOException);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  synchronized void doSetAutoCommit(boolean paramBoolean)
    throws SQLException
  {
  }

  public synchronized void open(OracleStatement paramOracleStatement)
    throws SQLException
  {
    assertLoggedOn("T4CConnection.open");
    paramOracleStatement.setCursorId(0);
  }

  synchronized String doGetDatabaseProductVersion()
    throws SQLException
  {
    assertLoggedOn("T4CConnection.do_getDatabaseProductVersion");

    T4C7Oversion localT4C7Oversion = new T4C7Oversion(this);
    try
    {
      localT4C7Oversion.doOVERSION();
    }
    catch (IOException localIOException)
    {
      handleIOException(localIOException);

      localObject = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localIOException);
      ((SQLException)localObject).fillInStackTrace();
      throw ((Throwable)localObject);
    }

    String str = null;

    Object localObject = localT4C7Oversion.getVersion();
    try
    {
      str = new String((byte[])localObject, "UTF8");
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localUnsupportedEncodingException);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    return str;
  }

  synchronized short doGetVersionNumber()
    throws SQLException
  {
    assertLoggedOn("T4CConnection.do_getVersionNumber");

    T4C7Oversion localT4C7Oversion = new T4C7Oversion(this);
    try
    {
      localT4C7Oversion.doOVERSION();
    }
    catch (IOException localIOException)
    {
      handleIOException(localIOException);

      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localIOException);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    return localT4C7Oversion.getVersionNumber();
  }

  OracleStatement RefCursorBytesToStatement(byte[] paramArrayOfByte, OracleStatement paramOracleStatement)
    throws SQLException
  {
    T4CStatement localT4CStatement = new T4CStatement(this, -1, -1);
    try
    {
      int i = this.mare.unmarshalRefCursor(paramArrayOfByte);

      localT4CStatement.setCursorId(i);

      localT4CStatement.isOpen = true;

      localT4CStatement.sqlObject = paramOracleStatement.sqlObject;

      localT4CStatement.serverCursor = true;
      paramOracleStatement.addChild(localT4CStatement);
      localT4CStatement.prepareForNewResults(true, false);
    }
    catch (IOException localIOException)
    {
      handleIOException(localIOException);

      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localIOException);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    localT4CStatement.sqlStringChanged = false;
    localT4CStatement.needToParse = false;

    return localT4CStatement;
  }

  void cancelOperationOnServer()
    throws SQLException
  {
    try
    {
      switch (this.pipeState)
      {
      case -1:
        return;
      case 1:
        this.net.sendBreak();
        break;
      case 2:
        this.net.sendInterrupt();
      case 0:
      }
      this.sentCancel = true;
    }
    catch (NetException localNetException)
    {
      localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localNetException);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
    catch (IOException localIOException)
    {
      handleIOException(localIOException);

      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localIOException);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  void connect(String paramString)
    throws IOException, SQLException
  {
    if (paramString == null)
    {
      localObject = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 433);
      ((SQLException)localObject).fillInStackTrace();
      throw ((Throwable)localObject);
    }

    Object localObject = new Properties();
    if (this.thinNetProfile != null)
      ((Properties)localObject).setProperty("oracle.net.profile", this.thinNetProfile);
    if (this.thinNetAuthenticationServices != null) {
      ((Properties)localObject).setProperty("oracle.net.authentication_services", this.thinNetAuthenticationServices);
    }
    if (this.thinNetAuthenticationKrb5Mutual != null) {
      ((Properties)localObject).setProperty("oracle.net.kerberos5_mutual_authentication", this.thinNetAuthenticationKrb5Mutual);
    }
    if (this.thinNetAuthenticationKrb5CcName != null) {
      ((Properties)localObject).setProperty("oracle.net.kerberos5_cc_name", this.thinNetAuthenticationKrb5CcName);
    }
    if (this.thinNetEncryptionLevel != null) {
      ((Properties)localObject).setProperty("oracle.net.encryption_client", this.thinNetEncryptionLevel);
    }
    if (this.thinNetEncryptionTypes != null) {
      ((Properties)localObject).setProperty("oracle.net.encryption_types_client", this.thinNetEncryptionTypes);
    }
    if (this.thinNetChecksumLevel != null) {
      ((Properties)localObject).setProperty("oracle.net.crypto_checksum_client", this.thinNetChecksumLevel);
    }
    if (this.thinNetChecksumTypes != null) {
      ((Properties)localObject).setProperty("oracle.net.crypto_checksum_types_client", this.thinNetChecksumTypes);
    }
    if (this.thinNetCryptoSeed != null)
      ((Properties)localObject).setProperty("oracle.net.crypto_seed", this.thinNetCryptoSeed);
    if (this.thinTcpNoDelay)
      ((Properties)localObject).setProperty("TCP.NODELAY", "YES");
    if (this.thinReadTimeout != null) {
      ((Properties)localObject).setProperty("oracle.net.READ_TIMEOUT", this.thinReadTimeout);
    }
    if (this.thinNetConnectTimeout != null) {
      ((Properties)localObject).setProperty("oracle.net.CONNECT_TIMEOUT", this.thinNetConnectTimeout);
    }
    if (this.thinSslServerDnMatch != null) {
      ((Properties)localObject).setProperty("oracle.net.ssl_server_dn_match", this.thinSslServerDnMatch);
    }
    if (this.walletLocation != null) {
      ((Properties)localObject).setProperty("oracle.net.wallet_location", this.walletLocation);
    }
    if (this.walletPassword != null) {
      ((Properties)localObject).setProperty("oracle.net.wallet_password", this.walletPassword);
    }
    if (this.thinSslVersion != null) {
      ((Properties)localObject).setProperty("oracle.net.ssl_version", this.thinSslVersion);
    }
    if (this.thinSslCipherSuites != null) {
      ((Properties)localObject).setProperty("oracle.net.ssl_cipher_suites", this.thinSslCipherSuites);
    }
    if (this.thinJavaxNetSslKeystore != null) {
      ((Properties)localObject).setProperty("javax.net.ssl.keyStore", this.thinJavaxNetSslKeystore);
    }
    if (this.thinJavaxNetSslKeystoretype != null) {
      ((Properties)localObject).setProperty("javax.net.ssl.keyStoreType", this.thinJavaxNetSslKeystoretype);
    }
    if (this.thinJavaxNetSslKeystorepassword != null) {
      ((Properties)localObject).setProperty("javax.net.ssl.keyStorePassword", this.thinJavaxNetSslKeystorepassword);
    }
    if (this.thinJavaxNetSslTruststore != null) {
      ((Properties)localObject).setProperty("javax.net.ssl.trustStore", this.thinJavaxNetSslTruststore);
    }
    if (this.thinJavaxNetSslTruststoretype != null) {
      ((Properties)localObject).setProperty("javax.net.ssl.trustStoreType", this.thinJavaxNetSslTruststoretype);
    }
    if (this.thinJavaxNetSslTruststorepassword != null) {
      ((Properties)localObject).setProperty("javax.net.ssl.trustStorePassword", this.thinJavaxNetSslTruststorepassword);
    }
    if (this.thinSslKeymanagerfactoryAlgorithm != null) {
      ((Properties)localObject).setProperty("ssl.keyManagerFactory.algorithm", this.thinSslKeymanagerfactoryAlgorithm);
    }
    if (this.thinSslTrustmanagerfactoryAlgorithm != null) {
      ((Properties)localObject).setProperty("ssl.trustManagerFactory.algorithm", this.thinSslTrustmanagerfactoryAlgorithm);
    }
    if (this.thinNetOldsyntax != null)
      ((Properties)localObject).setProperty("oracle.net.oldSyntax", this.thinNetOldsyntax);
    if (this.thinNamingContextInitial != null)
      ((Properties)localObject).setProperty("java.naming.factory.initial", this.thinNamingContextInitial);
    if (this.thinNamingProviderUrl != null)
      ((Properties)localObject).setProperty("java.naming.provider.url", this.thinNamingProviderUrl);
    if (this.thinNamingSecurityAuthentication != null) {
      ((Properties)localObject).setProperty("java.naming.security.authentication", this.thinNamingSecurityAuthentication);
    }
    if (this.thinNamingSecurityPrincipal != null) {
      ((Properties)localObject).setProperty("java.naming.security.principal", this.thinNamingSecurityPrincipal);
    }
    if (this.thinNamingSecurityCredentials != null) {
      ((Properties)localObject).setProperty("java.naming.security.credentials", this.thinNamingSecurityCredentials);
    }
    if (this.thinNetDisableOutOfBandBreak) {
      ((Properties)localObject).setProperty("DISABLE_OOB", new StringBuilder().append("").append(this.thinNetDisableOutOfBandBreak).toString());
    }
    if (this.thinNetEnableSDP) {
      ((Properties)localObject).setProperty("oracle.net.SDP", new StringBuilder().append("").append(this.thinNetEnableSDP).toString());
    }
    ((Properties)localObject).setProperty("USE_ZERO_COPY_IO", new StringBuilder().append("").append(this.thinNetUseZeroCopyIO).toString());

    ((Properties)localObject).setProperty("FORCE_DNS_LOAD_BALANCING", new StringBuilder().append("").append(this.thinForceDnsLoadBalancing).toString());

    ((Properties)localObject).setProperty("oracle.jdbc.v$session.osuser", this.thinVsessionOsuser);
    ((Properties)localObject).setProperty("oracle.jdbc.v$session.program", this.thinVsessionProgram);
    ((Properties)localObject).setProperty("T4CConnection.hashCode", Integer.toHexString(hashCode()).toUpperCase());

    this.net = new NSProtocol();

    this.net.connect(paramString, (Properties)localObject);

    this.mare = new T4CMAREngine(this.net);
    this.oer = new T4CTTIoer(this);
    this.mare.setConnectionDuringExceptionHandling(this);

    this.pro = new T4C8TTIpro(this);

    this.pro.marshal();

    byte[] arrayOfByte = this.pro.receive();
    this.serverRuntimeCapabilities = this.pro.getServerRuntimeCapabilities();

    short s1 = this.pro.getOracleVersion();
    short s2 = this.pro.getCharacterSet();
    short s3 = DBConversion.findDriverCharSet(s2, s1);

    this.conversion = new DBConversion(s2, s3, this.pro.getncharCHARSET(), this.isStrictAsciiConversion);

    this.mare.types.setServerConversion(s3 != s2);

    if (DBConversion.isCharSetMultibyte(s3))
    {
      if (DBConversion.isCharSetMultibyte(this.pro.getCharacterSet()))
        this.mare.types.setFlags((byte)1);
      else
        this.mare.types.setFlags((byte)2);
    }
    else {
      this.mare.types.setFlags(this.pro.getFlags());
    }
    this.mare.conv = this.conversion;

    T4C8TTIdty localT4C8TTIdty = new T4C8TTIdty(this, arrayOfByte, this.serverRuntimeCapabilities, (this.logonCap != null) && (this.logonCap.trim().equals("o3")), this.thinNetUseZeroCopyIO);

    localT4C8TTIdty.doRPC();

    this.negotiatedTTCversion = arrayOfByte[7];
    if (localT4C8TTIdty.jdbcThinCompileTimeCapabilities[7] < arrayOfByte[7]) {
      this.negotiatedTTCversion = localT4C8TTIdty.jdbcThinCompileTimeCapabilities[7];
    }

    if ((this.serverRuntimeCapabilities != null) && (this.serverRuntimeCapabilities.length > 6) && ((this.serverRuntimeCapabilities[6] & T4C8TTIdty.KPCCAP_RTB_TTC_ZCPY) != 0) && (this.thinNetUseZeroCopyIO) && ((this.net.getSessionAttributes().getNegotiatedOptions() & 0x40) != 0) && (getDataIntegrityAlgorithmName().equals("")) && (getEncryptionAlgorithmName().equals("")))
    {
      this.useZeroCopyIO = true;
    }
    else this.useZeroCopyIO = false;

    if ((arrayOfByte.length > 23) && ((arrayOfByte[23] & 0x40) != 0) && ((localT4C8TTIdty.jdbcThinCompileTimeCapabilities[23] & 0x40) != 0))
    {
      this.useLobPrefetch = true;
    }
    else this.useLobPrefetch = false;
  }

  boolean isZeroCopyIOEnabled()
  {
    return this.useZeroCopyIO;
  }

  final T4CTTIoer getT4CTTIoer()
  {
    return this.oer;
  }

  final byte getTTCVersion()
  {
    return this.negotiatedTTCversion;
  }

  void doStartup(int paramInt)
    throws SQLException
  {
    try
    {
      int i = 0;
      if (paramInt == OracleConnection.DatabaseStartupMode.FORCE.getMode())
        i = 16;
      else if (paramInt == OracleConnection.DatabaseStartupMode.RESTRICT.getMode())
        i = 1;
      this.spfp.doOSPFPPUT();
      this.sto.doOV6STRT(i);
    }
    catch (IOException localIOException)
    {
      handleIOException(localIOException);

      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localIOException);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  void doShutdown(int paramInt)
    throws SQLException
  {
    try
    {
      int i = 4;

      if (paramInt == OracleConnection.DatabaseShutdownMode.TRANSACTIONAL.getMode())
        i = 128;
      else if (paramInt == OracleConnection.DatabaseShutdownMode.TRANSACTIONAL_LOCAL.getMode())
        i = 256;
      else if (paramInt == OracleConnection.DatabaseShutdownMode.IMMEDIATE.getMode())
        i = 2;
      else if (paramInt == OracleConnection.DatabaseShutdownMode.FINAL.getMode())
        i = 8;
      else if (paramInt == OracleConnection.DatabaseShutdownMode.ABORT.getMode()) {
        i = 64;
      }
      sendPiggyBackedMessages();
      this.sto.doOV6STOP(i);
    }
    catch (IOException localIOException)
    {
      handleIOException(localIOException);

      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localIOException);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  void sendPiggyBackedMessages()
    throws SQLException, IOException
  {
    sendPiggyBackedClose();

    if ((this.endToEndAnyChanged) && (getTTCVersion() >= 3))
    {
      this.oscid.doOSCID(this.endToEndHasChanged, this.endToEndValues, this.endToEndECIDSequenceNumber);

      for (int i = 0; i < 4; i++) {
        if (this.endToEndHasChanged[i] != 0)
          this.endToEndHasChanged[i] = false;
      }
    }
    this.endToEndAnyChanged = false;

    if (!this.namespaces.isEmpty())
    {
      if (getTTCVersion() >= 4)
      {
        Object[] arrayOfObject = this.namespaces.values().toArray();
        for (int k = 0; k < arrayOfObject.length; k++)
        {
          this.okeyval.doOKEYVAL((Namespace)arrayOfObject[k]);
        }
      }
      this.namespaces.clear();
    }

    if (this.lusOffset2 > 0)
    {
      for (int j = 0; j < this.lusOffset2; j++) {
        this.oxsspo.doOXSSPO(this.lusFunctionId2[j], this.lusSessionId2[j], this.lusInKeyVal2[j], this.lusInFlags2[j]);
      }

      this.lusOffset2 = 0;
    }
  }

  private void sendPiggyBackedClose()
    throws SQLException, IOException
  {
    if (this.queryToCloseOffset > 0)
    {
      this.close8.doOCANA(this.queryToClose, this.queryToCloseOffset);
      this.queryToCloseOffset = 0;
    }

    if (this.cursorToCloseOffset > 0)
    {
      this.close8.doOCCA(this.cursorToClose, this.cursorToCloseOffset);
      this.cursorToCloseOffset = 0;
    }
  }

  void doProxySession(int paramInt, Properties paramProperties)
    throws SQLException
  {
    try
    {
      sendPiggyBackedMessages();

      this.auth.doOAUTH(paramInt, paramProperties, this.sessionId, this.serialNumber);

      int i = getSessionId();
      int j = getSerialNumber();

      this.oses.doO80SES(i, j, 1);

      this.savedUser = this.userName;

      if (paramInt == 1)
        this.userName = paramProperties.getProperty("PROXY_USER_NAME");
      else {
        this.userName = null;
      }
      this.isProxy = true;
    }
    catch (IOException localIOException)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localIOException);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  void closeProxySession()
    throws SQLException
  {
    try
    {
      sendPiggyBackedMessages();
      this.commoncall.doOLOGOFF();

      this.oses.doO80SES(this.sessionId, this.serialNumber, 1);

      this.userName = this.savedUser;
    }
    catch (IOException localIOException)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localIOException);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  void updateSessionProperties(KeywordValue[] paramArrayOfKeywordValue)
    throws SQLException
  {
    for (int i = 0; i < paramArrayOfKeywordValue.length; i++)
    {
      int j = paramArrayOfKeywordValue[i].getKeyword();
      byte[] arrayOfByte = paramArrayOfKeywordValue[i].getBinaryValue();
      if (j < T4C8Oall.NLS_KEYS.length)
      {
        String str1 = T4C8Oall.NLS_KEYS[j];
        if (str1 != null)
        {
          if (arrayOfByte != null)
          {
            this.sessionProperties.setProperty(str1, this.mare.conv.CharBytesToString(arrayOfByte, arrayOfByte.length));
          }
          else if (paramArrayOfKeywordValue[i].getTextValue() != null)
          {
            this.sessionProperties.setProperty(str1, paramArrayOfKeywordValue[i].getTextValue().trim());
          }
        }

      }
      else if (j == 163)
      {
        if (arrayOfByte != null)
        {
          int k = arrayOfByte[4];
          int m = arrayOfByte[5];

          if ((arrayOfByte[4] & 0xFF) > 120)
          {
            k = (arrayOfByte[4] & 0xFF) - 181;
            m = (arrayOfByte[5] & 0xFF) - 60;
          }
          else {
            k = (arrayOfByte[4] & 0xFF) - 60;
            m = (arrayOfByte[5] & 0xFF) - 60;
          }

          String str2 = new StringBuilder().append(k > 0 ? "+" : "").append(k).append(m <= 9 ? ":0" : ":").append(m).toString();

          this.sessionProperties.setProperty("SESSION_TIME_ZONE", str2);
        }

      }
      else if (j != 165)
      {
        if (j != 166)
        {
          if (j != 167)
          {
            if (j != 168)
            {
              if (j != 169)
              {
                if (j != 170)
                {
                  if (j != 171);
                }
              }
            }
          }
        }
      }
    }
  }

  public Properties getServerSessionInfo()
    throws SQLException
  {
    if ((getVersionNumber() >= 10000) && (getVersionNumber() < 10200))
      queryFCFProperties(this.sessionProperties);
    return this.sessionProperties;
  }

  public String getSessionTimeZoneOffset()
    throws SQLException
  {
    String str = getServerSessionInfo().getProperty("SESSION_TIME_ZONE");
    if (str == null)
    {
      str = super.getSessionTimeZoneOffset();
    }
    else
    {
      str = tzToOffset(str);
    }
    return str;
  }

  int getSessionId()
  {
    int i = -1;
    String str = this.sessionProperties.getProperty("AUTH_SESSION_ID");
    try
    {
      i = Integer.parseInt(str);
    } catch (NumberFormatException localNumberFormatException) {
    }
    return i;
  }

  int getSerialNumber()
  {
    int i = -1;
    String str = this.sessionProperties.getProperty("AUTH_SERIAL_NUM");
    try
    {
      i = Integer.parseInt(str);
    } catch (NumberFormatException localNumberFormatException) {
    }
    return i;
  }

  public byte getInstanceProperty(OracleConnection.InstanceProperty paramInstanceProperty)
    throws SQLException
  {
    byte b = 0;
    SQLException localSQLException;
    if (paramInstanceProperty == OracleConnection.InstanceProperty.ASM_VOLUME_SUPPORTED)
    {
      if ((this.serverRuntimeCapabilities == null) || (this.serverRuntimeCapabilities.length < 6))
      {
        localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 256);
        localSQLException.fillInStackTrace();
        throw localSQLException;
      }

      b = this.serverRuntimeCapabilities[5];
    }
    else if (paramInstanceProperty == OracleConnection.InstanceProperty.INSTANCE_TYPE)
    {
      if ((this.serverRuntimeCapabilities == null) || (this.serverRuntimeCapabilities.length < 4))
      {
        localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 256);
        localSQLException.fillInStackTrace();
        throw localSQLException;
      }

      b = this.serverRuntimeCapabilities[3];
    }

    return b;
  }

  public synchronized BlobDBAccess createBlobDBAccess()
    throws SQLException
  {
    return this;
  }

  public synchronized ClobDBAccess createClobDBAccess()
    throws SQLException
  {
    return this;
  }

  public synchronized BfileDBAccess createBfileDBAccess()
    throws SQLException
  {
    return this;
  }

  public synchronized long length(BFILE paramBFILE)
    throws SQLException
  {
    assertLoggedOn("length");
    assertNotNull(paramBFILE.shareBytes(), "length");

    needLine();

    long l = 0L;
    try
    {
      l = this.bfileMsg.getLength(paramBFILE.shareBytes());
    }
    catch (IOException localIOException)
    {
      handleIOException(localIOException);

      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localIOException);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    return l;
  }

  public synchronized long position(BFILE paramBFILE, byte[] paramArrayOfByte, long paramLong)
    throws SQLException
  {
    assertNotNull(paramBFILE.shareBytes(), "position");

    if (paramLong < 1L)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68, "position()");
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    long l = LobPlsqlUtil.hasPattern(paramBFILE, paramArrayOfByte, paramLong);

    l = l == 0L ? -1L : l;

    return l;
  }

  public long position(BFILE paramBFILE1, BFILE paramBFILE2, long paramLong)
    throws SQLException
  {
    assertNotNull(paramBFILE1.shareBytes(), "position");

    if (paramLong < 1L)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68, "position()");
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    long l = LobPlsqlUtil.isSubLob(paramBFILE1, paramBFILE2, paramLong);

    l = l == 0L ? -1L : l;

    return l;
  }

  public synchronized int getBytes(BFILE paramBFILE, long paramLong, int paramInt, byte[] paramArrayOfByte)
    throws SQLException
  {
    assertLoggedOn("getBytes");
    assertNotNull(paramBFILE.shareBytes(), "getBytes");
    SQLException localSQLException1;
    if (paramLong < 1L)
    {
      localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68, "getBytes()");
      localSQLException1.fillInStackTrace();
      throw localSQLException1;
    }

    if ((paramInt <= 0) || (paramArrayOfByte == null)) {
      return 0;
    }
    if (this.pipeState != -1)
    {
      localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 453, "getBytes()");
      localSQLException1.fillInStackTrace();
      throw localSQLException1;
    }

    needLine();

    long l = 0L;

    if (paramInt != 0)
    {
      try
      {
        l = this.bfileMsg.read(paramBFILE.shareBytes(), paramLong, paramInt, paramArrayOfByte, 0);
      }
      catch (IOException localIOException)
      {
        handleIOException(localIOException);

        SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localIOException);
        localSQLException2.fillInStackTrace();
        throw localSQLException2;
      }

    }

    return (int)l;
  }

  public String getName(BFILE paramBFILE)
    throws SQLException
  {
    assertLoggedOn("getName");
    assertNotNull(paramBFILE.shareBytes(), "getName");

    String str = LobPlsqlUtil.fileGetName(paramBFILE);

    return str;
  }

  public String getDirAlias(BFILE paramBFILE)
    throws SQLException
  {
    assertLoggedOn("getDirAlias");
    assertNotNull(paramBFILE.shareBytes(), "getDirAlias");

    String str = LobPlsqlUtil.fileGetDirAlias(paramBFILE);

    return str;
  }

  public synchronized void openFile(BFILE paramBFILE)
    throws SQLException
  {
    assertLoggedOn("openFile");
    assertNotNull(paramBFILE.shareBytes(), "openFile");

    needLine();
    try
    {
      this.bfileMsg.open(paramBFILE.shareBytes(), 11);
    }
    catch (IOException localIOException)
    {
      handleIOException(localIOException);

      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localIOException);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  public synchronized boolean isFileOpen(BFILE paramBFILE)
    throws SQLException
  {
    assertLoggedOn("openFile");
    assertNotNull(paramBFILE.shareBytes(), "openFile");

    needLine();

    boolean bool = false;
    try
    {
      bool = this.bfileMsg.isOpen(paramBFILE.shareBytes());
    }
    catch (IOException localIOException)
    {
      handleIOException(localIOException);

      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localIOException);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    return bool;
  }

  public synchronized boolean fileExists(BFILE paramBFILE)
    throws SQLException
  {
    assertLoggedOn("fileExists");
    assertNotNull(paramBFILE.shareBytes(), "fileExists");

    needLine();

    boolean bool = false;
    try
    {
      bool = this.bfileMsg.doesExist(paramBFILE.shareBytes());
    }
    catch (IOException localIOException)
    {
      handleIOException(localIOException);

      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localIOException);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    return bool;
  }

  public synchronized void closeFile(BFILE paramBFILE)
    throws SQLException
  {
    assertLoggedOn("closeFile");
    assertNotNull(paramBFILE.shareBytes(), "closeFile");

    needLine();
    try
    {
      this.bfileMsg.close(paramBFILE.shareBytes());
    }
    catch (IOException localIOException)
    {
      handleIOException(localIOException);

      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localIOException);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  public synchronized void open(BFILE paramBFILE, int paramInt)
    throws SQLException
  {
    assertLoggedOn("open");
    assertNotNull(paramBFILE.shareBytes(), "open");

    needLine();
    try
    {
      this.bfileMsg.open(paramBFILE.shareBytes(), paramInt);
    }
    catch (IOException localIOException)
    {
      handleIOException(localIOException);

      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localIOException);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  public synchronized void close(BFILE paramBFILE)
    throws SQLException
  {
    assertLoggedOn("close");
    assertNotNull(paramBFILE.shareBytes(), "close");

    needLine();
    try
    {
      this.bfileMsg.close(paramBFILE.shareBytes());
    }
    catch (IOException localIOException)
    {
      handleIOException(localIOException);

      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localIOException);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  public synchronized boolean isOpen(BFILE paramBFILE)
    throws SQLException
  {
    assertLoggedOn("isOpen");
    assertNotNull(paramBFILE.shareBytes(), "isOpen");

    needLine();

    boolean bool = false;
    try
    {
      bool = this.bfileMsg.isOpen(paramBFILE.shareBytes());
    }
    catch (IOException localIOException)
    {
      handleIOException(localIOException);

      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localIOException);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    return bool;
  }

  public InputStream newInputStream(BFILE paramBFILE, int paramInt, long paramLong)
    throws SQLException
  {
    if (paramLong == 0L)
    {
      return new OracleBlobInputStream(paramBFILE, paramInt);
    }

    return new OracleBlobInputStream(paramBFILE, paramInt, paramLong);
  }

  public InputStream newConversionInputStream(BFILE paramBFILE, int paramInt)
    throws SQLException
  {
    assertNotNull(paramBFILE.shareBytes(), "newConversionInputStream");

    OracleConversionInputStream localOracleConversionInputStream = new OracleConversionInputStream(this.conversion, paramBFILE.getBinaryStream(), paramInt);

    return localOracleConversionInputStream;
  }

  public Reader newConversionReader(BFILE paramBFILE, int paramInt)
    throws SQLException
  {
    assertNotNull(paramBFILE.shareBytes(), "newConversionReader");

    OracleConversionReader localOracleConversionReader = new OracleConversionReader(this.conversion, paramBFILE.getBinaryStream(), paramInt);

    return localOracleConversionReader;
  }

  public synchronized long length(BLOB paramBLOB)
    throws SQLException
  {
    assertLoggedOn("length");
    assertNotNull(paramBLOB.shareBytes(), "length");

    needLine();

    long l = 0L;
    try
    {
      l = this.blobMsg.getLength(paramBLOB.shareBytes());
    }
    catch (IOException localIOException)
    {
      handleIOException(localIOException);

      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localIOException);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    return l;
  }

  public long position(BLOB paramBLOB, byte[] paramArrayOfByte, long paramLong)
    throws SQLException
  {
    assertLoggedOn("position");
    assertNotNull(paramBLOB.shareBytes(), "position");

    if (paramLong < 1L)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68, "position()");
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    long l = LobPlsqlUtil.hasPattern(paramBLOB, paramArrayOfByte, paramLong);

    l = l == 0L ? -1L : l;

    return l;
  }

  public long position(BLOB paramBLOB1, BLOB paramBLOB2, long paramLong)
    throws SQLException
  {
    assertLoggedOn("position");
    assertNotNull(paramBLOB1.shareBytes(), "position");
    assertNotNull(paramBLOB2.shareBytes(), "position");

    if (paramLong < 1L)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68, "position()");
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    long l = LobPlsqlUtil.isSubLob(paramBLOB1, paramBLOB2, paramLong);

    l = l == 0L ? -1L : l;

    return l;
  }

  public synchronized int getBytes(BLOB paramBLOB, long paramLong, int paramInt, byte[] paramArrayOfByte)
    throws SQLException
  {
    assertLoggedOn("getBytes");
    assertNotNull(paramBLOB.shareBytes(), "getBytes");
    SQLException localSQLException1;
    if (paramLong < 1L)
    {
      localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68, "getBytes()");
      localSQLException1.fillInStackTrace();
      throw localSQLException1;
    }

    if (this.pipeState != -1)
    {
      localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 453, "getBytes()");
      localSQLException1.fillInStackTrace();
      throw localSQLException1;
    }

    if ((paramInt <= 0) || (paramArrayOfByte == null)) {
      return 0;
    }

    long l1 = 0L;

    long l2 = -1L;

    if (paramBLOB.isActivePrefetch())
    {
      byte[] arrayOfByte = paramBLOB.getPrefetchedData();
      int i = paramBLOB.getPrefetchedDataSize();
      l2 = paramBLOB.length();
      int j = 0;

      if (arrayOfByte != null) {
        j = Math.min(i, arrayOfByte.length);
      }
      if ((j > 0) && (paramLong <= j))
      {
        int k = Math.min(j - (int)paramLong + 1, paramInt);

        System.arraycopy(arrayOfByte, (int)paramLong - 1, paramArrayOfByte, 0, k);
        l1 += k;
      }
    }

    if ((l1 < paramInt) && ((l2 == -1L) || (paramLong - 1L + l1 < l2)))
    {
      needLine();
      try
      {
        l1 += this.blobMsg.read(paramBLOB.shareBytes(), paramLong + l1, paramInt - l1, paramArrayOfByte, (int)l1);
      }
      catch (IOException localIOException)
      {
        handleIOException(localIOException);

        SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localIOException);
        localSQLException2.fillInStackTrace();
        throw localSQLException2;
      }

    }

    return (int)l1;
  }

  public synchronized int putBytes(BLOB paramBLOB, long paramLong, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws SQLException
  {
    assertLoggedOn("putBytes");
    assertNotNull(paramBLOB.shareBytes(), "putBytes");

    if (paramLong < 1L)
    {
      SQLException localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68, "putBytes()");
      localSQLException1.fillInStackTrace();
      throw localSQLException1;
    }

    if ((paramArrayOfByte == null) || (paramInt2 <= 0)) {
      return 0;
    }
    needLine();

    long l = 0L;

    if (paramInt2 != 0)
    {
      try
      {
        paramBLOB.setActivePrefetch(false);
        paramBLOB.clearCachedData();
        l = this.blobMsg.write(paramBLOB.shareBytes(), paramLong, paramArrayOfByte, paramInt1, paramInt2);
      }
      catch (IOException localIOException)
      {
        handleIOException(localIOException);

        SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localIOException);
        localSQLException2.fillInStackTrace();
        throw localSQLException2;
      }

    }

    return (int)l;
  }

  public synchronized int getChunkSize(BLOB paramBLOB)
    throws SQLException
  {
    assertLoggedOn("getChunkSize");
    assertNotNull(paramBLOB.shareBytes(), "getChunkSize");

    needLine();

    long l = 0L;
    try
    {
      l = this.blobMsg.getChunkSize(paramBLOB.shareBytes());
    }
    catch (IOException localIOException)
    {
      handleIOException(localIOException);

      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localIOException);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    return (int)l;
  }

  public synchronized void trim(BLOB paramBLOB, long paramLong)
    throws SQLException
  {
    assertLoggedOn("trim");
    assertNotNull(paramBLOB.shareBytes(), "trim");

    if (paramLong < 0L)
    {
      SQLException localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68, "trim()");
      localSQLException1.fillInStackTrace();
      throw localSQLException1;
    }

    needLine();
    try
    {
      paramBLOB.setActivePrefetch(false);
      paramBLOB.clearCachedData();
      this.blobMsg.trim(paramBLOB.shareBytes(), paramLong);
    }
    catch (IOException localIOException)
    {
      handleIOException(localIOException);

      SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localIOException);
      localSQLException2.fillInStackTrace();
      throw localSQLException2;
    }
  }

  public synchronized BLOB createTemporaryBlob(Connection paramConnection, boolean paramBoolean, int paramInt)
    throws SQLException
  {
    assertLoggedOn("createTemporaryBlob");

    needLine();

    BLOB localBLOB = null;
    try
    {
      localBLOB = (BLOB)this.blobMsg.createTemporaryLob(this, paramBoolean, paramInt);
    }
    catch (IOException localIOException)
    {
      handleIOException(localIOException);

      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localIOException);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    return localBLOB;
  }

  public synchronized void freeTemporary(BLOB paramBLOB, boolean paramBoolean)
    throws SQLException
  {
    assertLoggedOn("freeTemporary");
    assertNotNull(paramBLOB.shareBytes(), "freeTemporary");

    needLine();
    try
    {
      this.blobMsg.freeTemporaryLob(paramBLOB.shareBytes());
    }
    catch (IOException localIOException)
    {
      handleIOException(localIOException);

      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localIOException);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  public boolean isTemporary(BLOB paramBLOB)
    throws SQLException
  {
    assertNotNull(paramBLOB.shareBytes(), "isTemporary");

    boolean bool = false;
    byte[] arrayOfByte = paramBLOB.shareBytes();

    if (((arrayOfByte[7] & 0x1) > 0) || ((arrayOfByte[4] & 0x40) > 0)) {
      bool = true;
    }
    return bool;
  }

  public synchronized void open(BLOB paramBLOB, int paramInt)
    throws SQLException
  {
    assertLoggedOn("open");
    assertNotNull(paramBLOB.shareBytes(), "open");

    needLine();
    try
    {
      this.blobMsg.open(paramBLOB.shareBytes(), paramInt);
    }
    catch (IOException localIOException)
    {
      handleIOException(localIOException);

      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localIOException);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  public synchronized void close(BLOB paramBLOB)
    throws SQLException
  {
    assertLoggedOn("close");
    assertNotNull(paramBLOB.shareBytes(), "close");

    needLine();
    try
    {
      this.blobMsg.close(paramBLOB.shareBytes());
    }
    catch (IOException localIOException)
    {
      handleIOException(localIOException);

      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localIOException);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  public synchronized boolean isOpen(BLOB paramBLOB)
    throws SQLException
  {
    assertLoggedOn("isOpen");
    assertNotNull(paramBLOB.shareBytes(), "isOpen");

    needLine();

    boolean bool = false;
    try
    {
      bool = this.blobMsg.isOpen(paramBLOB.shareBytes());
    }
    catch (IOException localIOException)
    {
      handleIOException(localIOException);

      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localIOException);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    return bool;
  }

  public InputStream newInputStream(BLOB paramBLOB, int paramInt, long paramLong)
    throws SQLException
  {
    if (paramLong == 0L)
    {
      return new OracleBlobInputStream(paramBLOB, paramInt);
    }

    return new OracleBlobInputStream(paramBLOB, paramInt, paramLong);
  }

  public InputStream newInputStream(BLOB paramBLOB, int paramInt, long paramLong1, long paramLong2)
    throws SQLException
  {
    return new OracleBlobInputStream(paramBLOB, paramInt, paramLong1, paramLong2);
  }

  public OutputStream newOutputStream(BLOB paramBLOB, int paramInt, long paramLong, boolean paramBoolean)
    throws SQLException
  {
    if (paramLong == 0L)
    {
      if ((paramBoolean & this.lobStreamPosStandardCompliant))
      {
        SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68);
        localSQLException.fillInStackTrace();
        throw localSQLException;
      }

      return new OracleBlobOutputStream(paramBLOB, paramInt);
    }

    return new OracleBlobOutputStream(paramBLOB, paramInt, paramLong);
  }

  public InputStream newConversionInputStream(BLOB paramBLOB, int paramInt)
    throws SQLException
  {
    assertNotNull(paramBLOB.shareBytes(), "newConversionInputStream");

    OracleConversionInputStream localOracleConversionInputStream = new OracleConversionInputStream(this.conversion, paramBLOB.getBinaryStream(), paramInt);

    return localOracleConversionInputStream;
  }

  public Reader newConversionReader(BLOB paramBLOB, int paramInt)
    throws SQLException
  {
    assertNotNull(paramBLOB.shareBytes(), "newConversionReader");

    OracleConversionReader localOracleConversionReader = new OracleConversionReader(this.conversion, paramBLOB.getBinaryStream(), paramInt);

    return localOracleConversionReader;
  }

  public synchronized long length(CLOB paramCLOB)
    throws SQLException
  {
    assertLoggedOn("length");
    assertNotNull(paramCLOB.shareBytes(), "length");

    needLine();

    long l = 0L;
    try
    {
      l = this.clobMsg.getLength(paramCLOB.shareBytes());
    }
    catch (IOException localIOException)
    {
      handleIOException(localIOException);

      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localIOException);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    return l;
  }

  public long position(CLOB paramCLOB, String paramString, long paramLong)
    throws SQLException
  {
    if (paramString == null)
    {
      localObject = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68, "position()");
      ((SQLException)localObject).fillInStackTrace();
      throw ((Throwable)localObject);
    }

    assertLoggedOn("position");
    assertNotNull(paramCLOB.shareBytes(), "position");

    if (paramLong < 1L)
    {
      localObject = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68, "position()");
      ((SQLException)localObject).fillInStackTrace();
      throw ((Throwable)localObject);
    }

    Object localObject = new char[paramString.length()];

    paramString.getChars(0, localObject.length, (char[])localObject, 0);

    long l = LobPlsqlUtil.hasPattern(paramCLOB, (char[])localObject, paramLong);

    l = l == 0L ? -1L : l;

    return l;
  }

  public long position(CLOB paramCLOB1, CLOB paramCLOB2, long paramLong)
    throws SQLException
  {
    SQLException localSQLException;
    if (paramCLOB2 == null)
    {
      localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68, "position()");
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    assertLoggedOn("position");
    assertNotNull(paramCLOB1.shareBytes(), "position");
    assertNotNull(paramCLOB2.shareBytes(), "position");

    if (paramLong < 1L)
    {
      localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68, "position()");
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    long l = LobPlsqlUtil.isSubLob(paramCLOB1, paramCLOB2, paramLong);

    l = l == 0L ? -1L : l;

    return l;
  }

  public synchronized int getChars(CLOB paramCLOB, long paramLong, int paramInt, char[] paramArrayOfChar)
    throws SQLException
  {
    assertLoggedOn("getChars");
    assertNotNull(paramCLOB.shareBytes(), "getChars");
    SQLException localSQLException1;
    if (paramLong < 1L)
    {
      localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68, "getChars()");
      localSQLException1.fillInStackTrace();
      throw localSQLException1;
    }

    if (this.pipeState != -1)
    {
      localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 453, "getChars()");
      localSQLException1.fillInStackTrace();
      throw localSQLException1;
    }

    if ((paramInt <= 0) || (paramArrayOfChar == null)) {
      return 0;
    }

    long l1 = 0L;

    long l2 = -1L;

    if (paramCLOB.isActivePrefetch())
    {
      l2 = paramCLOB.length();
      char[] arrayOfChar = paramCLOB.getPrefetchedData();
      int i = paramCLOB.getPrefetchedDataSize();

      int j = 0;
      if (arrayOfChar != null) {
        j = Math.min(i, arrayOfChar.length);
      }
      if ((j > 0) && (paramLong <= j))
      {
        int k = Math.min(j - (int)paramLong + 1, paramInt);

        System.arraycopy(arrayOfChar, (int)paramLong - 1, paramArrayOfChar, 0, k);
        l1 += k;
      }
    }

    if ((l1 < paramInt) && ((l2 == -1L) || (paramLong - 1L + l1 < l2)))
    {
      needLine();
      try
      {
        boolean bool = paramCLOB.isNCLOB();

        l1 += this.clobMsg.read(paramCLOB.shareBytes(), (int)paramLong + l1, paramInt - l1, bool, paramArrayOfChar, (int)l1);
      }
      catch (IOException localIOException)
      {
        handleIOException(localIOException);

        SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localIOException);
        localSQLException2.fillInStackTrace();
        throw localSQLException2;
      }

    }

    return (int)l1;
  }

  public synchronized int putChars(CLOB paramCLOB, long paramLong, char[] paramArrayOfChar, int paramInt1, int paramInt2)
    throws SQLException
  {
    assertLoggedOn("putChars");
    assertNotNull(paramCLOB.shareBytes(), "putChars");

    if (paramLong < 1L)
    {
      SQLException localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68, "putChars()");
      localSQLException1.fillInStackTrace();
      throw localSQLException1;
    }

    if ((paramArrayOfChar == null) || (paramInt2 <= 0)) {
      return 0;
    }
    needLine();

    long l = 0L;

    if (paramInt2 != 0)
    {
      try
      {
        boolean bool = paramCLOB.isNCLOB();

        paramCLOB.setActivePrefetch(false);
        paramCLOB.clearCachedData();
        l = this.clobMsg.write(paramCLOB.shareBytes(), paramLong, bool, paramArrayOfChar, paramInt1, paramInt2);
      }
      catch (IOException localIOException)
      {
        handleIOException(localIOException);

        SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localIOException);
        localSQLException2.fillInStackTrace();
        throw localSQLException2;
      }

    }

    return (int)l;
  }

  public synchronized int getChunkSize(CLOB paramCLOB)
    throws SQLException
  {
    assertLoggedOn("getChunkSize");
    assertNotNull(paramCLOB.shareBytes(), "getChunkSize");

    needLine();

    long l = 0L;
    try
    {
      l = this.clobMsg.getChunkSize(paramCLOB.shareBytes());
    }
    catch (IOException localIOException)
    {
      handleIOException(localIOException);

      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localIOException);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    return (int)l;
  }

  public synchronized void trim(CLOB paramCLOB, long paramLong)
    throws SQLException
  {
    assertLoggedOn("trim");
    assertNotNull(paramCLOB.shareBytes(), "trim");

    if (paramLong < 0L)
    {
      SQLException localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68, "trim()");
      localSQLException1.fillInStackTrace();
      throw localSQLException1;
    }

    needLine();
    try
    {
      paramCLOB.setActivePrefetch(false);
      paramCLOB.clearCachedData();
      this.clobMsg.trim(paramCLOB.shareBytes(), paramLong);
    }
    catch (IOException localIOException)
    {
      handleIOException(localIOException);

      SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localIOException);
      localSQLException2.fillInStackTrace();
      throw localSQLException2;
    }
  }

  public synchronized CLOB createTemporaryClob(Connection paramConnection, boolean paramBoolean, int paramInt, short paramShort)
    throws SQLException
  {
    assertLoggedOn("createTemporaryClob");

    needLine();

    CLOB localCLOB = null;
    try
    {
      localCLOB = (CLOB)this.clobMsg.createTemporaryLob(this, paramBoolean, paramInt, paramShort);
    }
    catch (IOException localIOException)
    {
      handleIOException(localIOException);

      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localIOException);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    return localCLOB;
  }

  public synchronized void freeTemporary(CLOB paramCLOB, boolean paramBoolean)
    throws SQLException
  {
    assertLoggedOn("freeTemporary");
    assertNotNull(paramCLOB.shareBytes(), "freeTemporary");

    needLine();
    try
    {
      this.clobMsg.freeTemporaryLob(paramCLOB.shareBytes());
    }
    catch (IOException localIOException)
    {
      handleIOException(localIOException);

      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localIOException);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  public boolean isTemporary(CLOB paramCLOB)
    throws SQLException
  {
    boolean bool = false;
    byte[] arrayOfByte = paramCLOB.shareBytes();

    if (((arrayOfByte[7] & 0x1) > 0) || ((arrayOfByte[4] & 0x40) > 0)) {
      bool = true;
    }
    return bool;
  }

  public synchronized void open(CLOB paramCLOB, int paramInt)
    throws SQLException
  {
    assertLoggedOn("open");
    assertNotNull(paramCLOB.shareBytes(), "open");

    needLine();
    try
    {
      this.clobMsg.open(paramCLOB.shareBytes(), paramInt);
    }
    catch (IOException localIOException)
    {
      handleIOException(localIOException);

      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localIOException);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  public synchronized void close(CLOB paramCLOB)
    throws SQLException
  {
    assertLoggedOn("close");
    assertNotNull(paramCLOB.shareBytes(), "close");

    needLine();
    try
    {
      this.clobMsg.close(paramCLOB.shareBytes());
    }
    catch (IOException localIOException)
    {
      handleIOException(localIOException);

      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localIOException);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  public synchronized boolean isOpen(CLOB paramCLOB)
    throws SQLException
  {
    assertLoggedOn("isOpen");
    assertNotNull(paramCLOB.shareBytes(), "isOpen");

    boolean bool = false;

    needLine();
    try
    {
      bool = this.clobMsg.isOpen(paramCLOB.shareBytes());
    }
    catch (IOException localIOException)
    {
      handleIOException(localIOException);

      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localIOException);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    return bool;
  }

  public InputStream newInputStream(CLOB paramCLOB, int paramInt, long paramLong)
    throws SQLException
  {
    if (paramLong == 0L)
    {
      return new OracleClobInputStream(paramCLOB, paramInt);
    }

    return new OracleClobInputStream(paramCLOB, paramInt, paramLong);
  }

  public OutputStream newOutputStream(CLOB paramCLOB, int paramInt, long paramLong, boolean paramBoolean)
    throws SQLException
  {
    if (paramLong == 0L)
    {
      if ((paramBoolean & this.lobStreamPosStandardCompliant))
      {
        SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68);
        localSQLException.fillInStackTrace();
        throw localSQLException;
      }

      return new OracleClobOutputStream(paramCLOB, paramInt);
    }

    return new OracleClobOutputStream(paramCLOB, paramInt, paramLong);
  }

  public Reader newReader(CLOB paramCLOB, int paramInt, long paramLong)
    throws SQLException
  {
    if (paramLong == 0L)
    {
      return new OracleClobReader(paramCLOB, paramInt);
    }

    return new OracleClobReader(paramCLOB, paramInt, paramLong);
  }

  public Reader newReader(CLOB paramCLOB, int paramInt, long paramLong1, long paramLong2)
    throws SQLException
  {
    return new OracleClobReader(paramCLOB, paramInt, paramLong1, paramLong2);
  }

  public Writer newWriter(CLOB paramCLOB, int paramInt, long paramLong, boolean paramBoolean)
    throws SQLException
  {
    if (paramLong == 0L)
    {
      if ((paramBoolean & this.lobStreamPosStandardCompliant))
      {
        SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68);
        localSQLException.fillInStackTrace();
        throw localSQLException;
      }

      return new OracleClobWriter(paramCLOB, paramInt);
    }

    return new OracleClobWriter(paramCLOB, paramInt, paramLong);
  }

  void assertLoggedOn(String paramString)
    throws SQLException
  {
    if (!this.isLoggedOn)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 430);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  void assertNotNull(byte[] paramArrayOfByte, String paramString)
    throws NullPointerException
  {
    if (paramArrayOfByte == null)
    {
      throw new NullPointerException("bytes are null");
    }
  }

  void internalClose()
    throws SQLException
  {
    super.internalClose();

    this.isLoggedOn = false;
    try
    {
      if (this.net != null)
        this.net.disconnect();
    }
    catch (Exception localException)
    {
    }
  }

  void doAbort()
    throws SQLException
  {
    try
    {
      this.net.abort();
    }
    catch (NetException localNetException)
    {
      localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localNetException);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
    catch (IOException localIOException)
    {
      handleIOException(localIOException);

      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localIOException);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  protected void doDescribeTable(AutoKeyInfo paramAutoKeyInfo) throws SQLException
  {
    T4CStatement localT4CStatement = new T4CStatement(this, -1, -1);
    localT4CStatement.open();

    String str1 = paramAutoKeyInfo.getTableName();
    String str2 = new StringBuilder().append("SELECT * FROM ").append(str1).toString();

    localT4CStatement.sqlObject.initialize(str2);

    Accessor[] arrayOfAccessor = null;
    Object localObject;
    try {
      this.describe.doODNY(localT4CStatement, 0, arrayOfAccessor, localT4CStatement.sqlObject.getSqlBytes(false, false));
      arrayOfAccessor = this.describe.getAccessors();
    }
    catch (IOException localIOException)
    {
      handleIOException(localIOException);

      localObject = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localIOException);
      ((SQLException)localObject).fillInStackTrace();
      throw ((Throwable)localObject);
    }

    int i = this.describe.numuds;

    paramAutoKeyInfo.allocateSpaceForDescribedData(i);

    for (int i1 = 0; i1 < i; i1++)
    {
      Accessor localAccessor = arrayOfAccessor[i1];
      localObject = localAccessor.columnName;
      int j = localAccessor.describeType;
      int k = localAccessor.describeMaxLength;
      boolean bool = localAccessor.nullable;
      short s = localAccessor.formOfUse;
      int m = localAccessor.precision;
      int n = localAccessor.scale;
      String str3 = localAccessor.describeTypeName;

      paramAutoKeyInfo.fillDescribedData(i1, (String)localObject, j, k, bool, s, m, n, str3);
    }

    localT4CStatement.close();
  }

  void doSetApplicationContext(String paramString1, String paramString2, String paramString3)
    throws SQLException
  {
    Namespace localNamespace = (Namespace)this.namespaces.get(paramString1);
    if (localNamespace == null)
    {
      localNamespace = new Namespace(paramString1);
      this.namespaces.put(paramString1, localNamespace);
    }
    localNamespace.setAttribute(paramString2, paramString3);
  }

  void doClearAllApplicationContext(String paramString)
    throws SQLException
  {
    Namespace localNamespace = new Namespace(paramString);
    localNamespace.clear();
    this.namespaces.put(paramString, localNamespace);
  }

  public void getPropertyForPooledConnection(OraclePooledConnection paramOraclePooledConnection)
    throws SQLException
  {
    super.getPropertyForPooledConnection(paramOraclePooledConnection, this.password);
  }

  final void getPasswordInternal(T4CXAResource paramT4CXAResource)
    throws SQLException
  {
    paramT4CXAResource.setPasswordInternal(this.password);
  }

  synchronized void doEnqueue(String paramString, AQEnqueueOptions paramAQEnqueueOptions, AQMessagePropertiesI paramAQMessagePropertiesI, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[][] paramArrayOfByte, boolean paramBoolean)
    throws SQLException
  {
    try
    {
      needLine();
      sendPiggyBackedMessages();
      this.aqe.doOAQEQ(paramString, paramAQEnqueueOptions, paramAQMessagePropertiesI, paramArrayOfByte2, paramArrayOfByte1, paramBoolean);

      if (paramAQEnqueueOptions.getRetrieveMessageId())
        paramArrayOfByte[0] = this.aqe.getMessageId();
    }
    catch (IOException localIOException)
    {
      handleIOException(localIOException);

      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localIOException);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  synchronized boolean doDequeue(String paramString, AQDequeueOptions paramAQDequeueOptions, AQMessagePropertiesI paramAQMessagePropertiesI, byte[] paramArrayOfByte, byte[][] paramArrayOfByte1, byte[][] paramArrayOfByte2, boolean paramBoolean)
    throws SQLException
  {
    boolean bool = false;
    try
    {
      needLine();
      sendPiggyBackedMessages();
      this.aqdq.doOAQDQ(paramString, paramAQDequeueOptions, paramArrayOfByte, paramBoolean, paramAQMessagePropertiesI);

      paramArrayOfByte1[0] = this.aqdq.getPayload();
      paramArrayOfByte2[0] = this.aqdq.getDequeuedMessageId();
      bool = this.aqdq.hasAMessageBeenDequeued();
    }
    catch (IOException localIOException)
    {
      handleIOException(localIOException);

      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localIOException);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    return bool;
  }

  synchronized int doPingDatabase()
    throws SQLException
  {
    if (this.versionNumber >= 10102)
    {
      try
      {
        needLine();
        sendPiggyBackedMessages();
        this.oping.doOPING();
      }
      catch (IOException localIOException)
      {
        return -1;
      }
      catch (SQLException localSQLException)
      {
        return -1;
      }
      return 0;
    }

    return super.doPingDatabase();
  }

  synchronized NTFAQRegistration[] doRegisterAQNotification(String[] paramArrayOfString, String paramString, int paramInt, Properties[] paramArrayOfProperties)
    throws SQLException
  {
    int i = paramArrayOfString.length;
    int[] arrayOfInt1 = new int[i];
    byte[][] arrayOfByte = new byte[i][];
    int[] arrayOfInt2 = new int[i];
    int[] arrayOfInt3 = new int[i];
    int[] arrayOfInt4 = new int[i];
    int[] arrayOfInt5 = new int[i];
    int[] arrayOfInt6 = new int[i];
    int[] arrayOfInt7 = new int[i];
    long[] arrayOfLong = new long[i];
    byte[] arrayOfByte1 = new byte[i];
    int[] arrayOfInt8 = new int[i];
    byte[] arrayOfByte2 = new byte[i];
    TIMESTAMPTZ[] arrayOfTIMESTAMPTZ = new TIMESTAMPTZ[i];
    int[] arrayOfInt9 = new int[i];

    boolean bool1 = false;
    if (paramInt == 0)
    {
      bool1 = true;
      paramInt = 47632;
    }

    for (int j = 0; j < i; j++)
    {
      arrayOfInt1[j] = PhysicalConnection.ntfManager.getNextJdbcRegId();
      arrayOfByte[j] = new byte[4];
      arrayOfByte[j][0] = ((byte)((arrayOfInt1[j] & 0xFF000000) >> 24));
      arrayOfByte[j][1] = ((byte)((arrayOfInt1[j] & 0xFF0000) >> 16));
      arrayOfByte[j][2] = ((byte)((arrayOfInt1[j] & 0xFF00) >> 8));
      arrayOfByte[j][3] = ((byte)(arrayOfInt1[j] & 0xFF));
      arrayOfInt2[j] = 1;
      arrayOfInt3[j] = 23;

      if ((paramArrayOfProperties.length > j) && (paramArrayOfProperties[j] != null))
      {
        if (paramArrayOfProperties[j].getProperty("NTF_QOS_RELIABLE", "false").compareToIgnoreCase("true") == 0)
        {
          arrayOfInt4[j] |= 1;
        }if (paramArrayOfProperties[j].getProperty("NTF_QOS_PURGE_ON_NTFN", "false").compareToIgnoreCase("true") == 0)
        {
          arrayOfInt4[j] |= 16;
        }if (paramArrayOfProperties[j].getProperty("NTF_AQ_PAYLOAD", "false").compareToIgnoreCase("true") == 0)
        {
          arrayOfInt4[j] |= 2;
        }arrayOfInt5[j] = readNTFtimeout(paramArrayOfProperties[j]);
      }
    }

    setNtfGroupingOptions(arrayOfByte1, arrayOfInt8, arrayOfByte2, arrayOfTIMESTAMPTZ, arrayOfInt9, paramArrayOfProperties);

    int[] arrayOfInt10 = new int[1];
    arrayOfInt10[0] = paramInt;

    boolean bool2 = PhysicalConnection.ntfManager.listenOnPortT4C(arrayOfInt10, bool1);
    paramInt = arrayOfInt10[0];

    String str = new StringBuilder().append("(ADDRESS=(PROTOCOL=tcp)(HOST=").append(paramString).append(")(PORT=").append(paramInt).append("))?PR=0").toString();
    try
    {
      try
      {
        int k = bool2 ? 1 : 0;
        sendPiggyBackedMessages();
        this.okpn.doOKPN(1, k, this.userName, str, i, arrayOfInt2, paramArrayOfString, arrayOfByte, arrayOfInt3, arrayOfInt4, arrayOfInt5, arrayOfInt6, arrayOfInt7, arrayOfLong, arrayOfByte1, arrayOfInt8, arrayOfByte2, arrayOfTIMESTAMPTZ, arrayOfInt9);
      }
      catch (IOException localIOException)
      {
        handleIOException(localIOException);

        SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localIOException);
        localSQLException2.fillInStackTrace();
        throw localSQLException2;
      }

    }
    catch (SQLException localSQLException1)
    {
      if (bool2) {
        PhysicalConnection.ntfManager.cleanListenersT4C(paramInt);
      }
      throw localSQLException1;
    }
    NTFAQRegistration[] arrayOfNTFAQRegistration = new NTFAQRegistration[i];

    for (int m = 0; m < i; m++) {
      arrayOfNTFAQRegistration[m] = new NTFAQRegistration(arrayOfInt1[m], true, this.instanceName, this.userName, paramString, paramInt, paramArrayOfProperties[m], paramArrayOfString[m], this.versionNumber);
    }

    for (m = 0; m < arrayOfNTFAQRegistration.length; m++)
      PhysicalConnection.ntfManager.addRegistration(arrayOfNTFAQRegistration[m]);
    return arrayOfNTFAQRegistration;
  }

  private void setNtfGroupingOptions(byte[] paramArrayOfByte1, int[] paramArrayOfInt1, byte[] paramArrayOfByte2, TIMESTAMPTZ[] paramArrayOfTIMESTAMPTZ, int[] paramArrayOfInt2, Properties[] paramArrayOfProperties)
    throws SQLException
  {
    for (int i = 0; i < paramArrayOfProperties.length; i++)
    {
      String str1 = paramArrayOfProperties[i].getProperty("NTF_GROUPING_CLASS", "NTF_GROUPING_CLASS_NONE");
      String str2 = paramArrayOfProperties[i].getProperty("NTF_GROUPING_VALUE");
      String str3 = paramArrayOfProperties[i].getProperty("NTF_GROUPING_TYPE");
      TIMESTAMPTZ localTIMESTAMPTZ = null;
      if (paramArrayOfProperties[i].get("NTF_GROUPING_START_TIME") != null)
        localTIMESTAMPTZ = (TIMESTAMPTZ)paramArrayOfProperties[i].get("NTF_GROUPING_START_TIME");
      String str4 = paramArrayOfProperties[i].getProperty("NTF_GROUPING_REPEAT_TIME", "NTF_GROUPING_REPEAT_FOREVER");
      SQLException localSQLException;
      if ((str1.compareTo("NTF_GROUPING_CLASS_TIME") != 0) && (str1.compareTo("NTF_GROUPING_CLASS_NONE") != 0))
      {
        localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68);
        localSQLException.fillInStackTrace();
        throw localSQLException;
      }

      if ((str1.compareTo("NTF_GROUPING_CLASS_NONE") != 0) && (getTTCVersion() < 5))
      {
        localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 23);
        localSQLException.fillInStackTrace();
        throw localSQLException;
      }

      if (str1.compareTo("NTF_GROUPING_CLASS_TIME") == 0)
      {
        paramArrayOfByte1[i] = 1;

        paramArrayOfInt1[i] = 600;
        if (str2 != null) {
          paramArrayOfInt1[i] = Integer.parseInt(str2);
        }
        paramArrayOfByte2[i] = 1;
        if (str3 != null)
        {
          if (str3.compareTo("NTF_GROUPING_TYPE_SUMMARY") == 0) {
            paramArrayOfByte2[i] = 1;
          } else if (str3.compareTo("NTF_GROUPING_TYPE_LAST") == 0) {
            paramArrayOfByte2[i] = 2;
          }
          else
          {
            localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68);
            localSQLException.fillInStackTrace();
            throw localSQLException;
          }

        }

        paramArrayOfTIMESTAMPTZ[i] = localTIMESTAMPTZ;
        if (str4.compareTo("NTF_GROUPING_REPEAT_FOREVER") == 0)
          paramArrayOfInt2[i] = 0;
        else
          paramArrayOfInt2[i] = Integer.parseInt(str4);
      }
    }
  }

  synchronized void doUnregisterAQNotification(NTFAQRegistration paramNTFAQRegistration)
    throws SQLException
  {
    String str1 = paramNTFAQRegistration.getClientHost();
    int i = paramNTFAQRegistration.getClientTCPPort();
    if (str1 == null) {
      return;
    }

    PhysicalConnection.ntfManager.removeRegistration(paramNTFAQRegistration);
    PhysicalConnection.ntfManager.freeJdbcRegId(paramNTFAQRegistration.getJdbcRegId());
    PhysicalConnection.ntfManager.cleanListenersT4C(paramNTFAQRegistration.getClientTCPPort());
    paramNTFAQRegistration.setState(NotificationRegistration.RegistrationState.CLOSED);

    String str2 = new StringBuilder().append("(ADDRESS=(PROTOCOL=tcp)(HOST=").append(str1).append(")(PORT=").append(i).append("))?PR=0").toString();

    int[] arrayOfInt1 = { 1 };
    String[] arrayOfString = new String[1];
    arrayOfString[0] = paramNTFAQRegistration.getQueueName();
    int[] arrayOfInt2 = { 0 };
    int[] arrayOfInt3 = { 0 };
    int[] arrayOfInt4 = { 0 };
    int[] arrayOfInt5 = { 0 };
    int[] arrayOfInt6 = { 0 };
    long[] arrayOfLong = { 0L };
    byte[] arrayOfByte1 = { 0 };
    int[] arrayOfInt7 = { 0 };
    byte[] arrayOfByte2 = { 0 };
    TIMESTAMPTZ[] arrayOfTIMESTAMPTZ = { null };
    int[] arrayOfInt8 = { 0 };
    byte[][] arrayOfByte = new byte[1][];
    int j = paramNTFAQRegistration.getJdbcRegId();
    arrayOfByte[0] = new byte[4];
    arrayOfByte[0][0] = ((byte)((j & 0xFF000000) >> 24));
    arrayOfByte[0][1] = ((byte)((j & 0xFF0000) >> 16));
    arrayOfByte[0][2] = ((byte)((j & 0xFF00) >> 8));
    arrayOfByte[0][3] = ((byte)(j & 0xFF));
    try
    {
      sendPiggyBackedMessages();
      this.okpn.doOKPN(2, 0, this.userName, str2, 1, arrayOfInt1, arrayOfString, arrayOfByte, arrayOfInt2, arrayOfInt3, arrayOfInt4, arrayOfInt5, arrayOfInt6, arrayOfLong, arrayOfByte1, arrayOfInt7, arrayOfByte2, arrayOfTIMESTAMPTZ, arrayOfInt8);
    }
    catch (IOException localIOException)
    {
      handleIOException(localIOException);

      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localIOException);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  synchronized NTFDCNRegistration doRegisterDatabaseChangeNotification(String paramString, int paramInt1, Properties paramProperties, int paramInt2, int paramInt3)
    throws SQLException
  {
    int i = 0;
    int j = 0;
    int k = 0;
    int m = 0;
    int n = 0;
    Object localObject = null;
    int i1 = 0;
    boolean bool1 = false;
    if (paramInt1 == 0)
    {
      bool1 = true;
      paramInt1 = 47632;
    }

    if (paramProperties.getProperty("NTF_QOS_RELIABLE", "false").compareToIgnoreCase("true") == 0)
    {
      j |= 1;
    }if (paramProperties.getProperty("NTF_QOS_PURGE_ON_NTFN", "false").compareToIgnoreCase("true") == 0)
    {
      j |= 16;
    }

    if (paramProperties.getProperty("DCN_NOTIFY_ROWIDS", "false").compareToIgnoreCase("true") == 0)
    {
      i |= 16;
    }

    if (paramProperties.getProperty("DCN_QUERY_CHANGE_NOTIFICATION", "false").compareToIgnoreCase("true") == 0)
    {
      i |= 32;
    }

    if (paramProperties.getProperty("DCN_BEST_EFFORT", "false").compareToIgnoreCase("true") == 0)
    {
      i |= 64;
    }
    int i2 = 0;
    int i3 = 0;
    int i4 = 0;
    if (paramProperties.getProperty("DCN_IGNORE_INSERTOP", "false").compareToIgnoreCase("true") == 0)
    {
      i2 = 1;
    }if (paramProperties.getProperty("DCN_IGNORE_UPDATEOP", "false").compareToIgnoreCase("true") == 0)
    {
      i3 = 1;
    }if (paramProperties.getProperty("DCN_IGNORE_DELETEOP", "false").compareToIgnoreCase("true") == 0)
    {
      i4 = 1;
    }
    if ((i2 != 0) || (i3 != 0) || (i4 != 0))
    {
      i |= 15;

      if (i2 != 0)
        i -= 2;
      if (i3 != 0)
        i -= 4;
      if (i4 != 0) {
        i -= 8;
      }

    }

    byte[] arrayOfByte1 = new byte[1];
    int[] arrayOfInt1 = new int[1];
    byte[] arrayOfByte2 = new byte[1];
    TIMESTAMPTZ[] arrayOfTIMESTAMPTZ = new TIMESTAMPTZ[1];
    int[] arrayOfInt2 = new int[1];
    Properties[] arrayOfProperties = { paramProperties };
    setNtfGroupingOptions(arrayOfByte1, arrayOfInt1, arrayOfByte2, arrayOfTIMESTAMPTZ, arrayOfInt2, arrayOfProperties);

    int[] arrayOfInt3 = new int[1];
    arrayOfInt3[0] = paramInt1;

    boolean bool2 = PhysicalConnection.ntfManager.listenOnPortT4C(arrayOfInt3, bool1);
    paramInt1 = arrayOfInt3[0];

    String str = new StringBuilder().append("(ADDRESS=(PROTOCOL=tcp)(HOST=").append(paramString).append(")(PORT=").append(paramInt1).append("))?PR=0").toString();

    int[] arrayOfInt4 = { 2 };
    String[] arrayOfString = new String[1];
    int[] arrayOfInt5 = { 23 };

    int[] arrayOfInt6 = { j };
    int[] arrayOfInt7 = { paramInt2 };
    int[] arrayOfInt8 = { i };
    int[] arrayOfInt9 = { paramInt3 };
    long[] arrayOfLong = { 0L };
    int i5 = PhysicalConnection.ntfManager.getNextJdbcRegId();
    byte[][] arrayOfByte = new byte[1][];
    arrayOfByte[0] = new byte[4];
    arrayOfByte[0][0] = ((byte)((i5 & 0xFF000000) >> 24));
    arrayOfByte[0][1] = ((byte)((i5 & 0xFF0000) >> 16));
    arrayOfByte[0][2] = ((byte)((i5 & 0xFF00) >> 8));
    arrayOfByte[0][3] = ((byte)(i5 & 0xFF));
    long l = 0L;
    try
    {
      try
      {
        int i6 = bool2 ? 1 : 0;
        sendPiggyBackedMessages();
        this.okpn.doOKPN(1, i6, this.userName, str, 1, arrayOfInt4, arrayOfString, arrayOfByte, arrayOfInt5, arrayOfInt6, arrayOfInt7, arrayOfInt8, arrayOfInt9, arrayOfLong, arrayOfByte1, arrayOfInt1, arrayOfByte2, arrayOfTIMESTAMPTZ, arrayOfInt2);

        l = this.okpn.getRegistrationId();
      }
      catch (IOException localIOException)
      {
        handleIOException(localIOException);

        SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localIOException);
        localSQLException2.fillInStackTrace();
        throw localSQLException2;
      }

    }
    catch (SQLException localSQLException1)
    {
      if (bool2) {
        PhysicalConnection.ntfManager.cleanListenersT4C(paramInt1);
      }
      throw localSQLException1;
    }
    NTFDCNRegistration localNTFDCNRegistration = new NTFDCNRegistration(i5, true, this.instanceName, l, this.userName, paramString, paramInt1, paramProperties, this.versionNumber);

    return localNTFDCNRegistration;
  }

  synchronized void doUnregisterDatabaseChangeNotification(long paramLong, String paramString)
    throws SQLException
  {
    int[] arrayOfInt1 = { 2 };
    String[] arrayOfString = new String[1];
    int[] arrayOfInt2 = { 0 };
    int[] arrayOfInt3 = { 0 };
    int[] arrayOfInt4 = { 0 };
    int[] arrayOfInt5 = { 0 };
    int[] arrayOfInt6 = { 0 };
    byte[] arrayOfByte1 = { 0 };
    int[] arrayOfInt7 = { 0 };
    byte[] arrayOfByte2 = { 0 };
    TIMESTAMPTZ[] arrayOfTIMESTAMPTZ = { null };
    int[] arrayOfInt8 = { 0 };
    long[] arrayOfLong = { paramLong };
    byte[][] arrayOfByte = new byte[1][];
    try
    {
      sendPiggyBackedMessages();
      this.okpn.doOKPN(2, 0, null, paramString, 1, arrayOfInt1, arrayOfString, arrayOfByte, arrayOfInt2, arrayOfInt3, arrayOfInt4, arrayOfInt5, arrayOfInt6, arrayOfLong, arrayOfByte1, arrayOfInt7, arrayOfByte2, arrayOfTIMESTAMPTZ, arrayOfInt8);
    }
    catch (IOException localIOException)
    {
      handleIOException(localIOException);

      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localIOException);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  synchronized void doUnregisterDatabaseChangeNotification(NTFDCNRegistration paramNTFDCNRegistration)
    throws SQLException
  {
    PhysicalConnection.ntfManager.removeRegistration(paramNTFDCNRegistration);
    PhysicalConnection.ntfManager.freeJdbcRegId(paramNTFDCNRegistration.getJdbcRegId());
    PhysicalConnection.ntfManager.cleanListenersT4C(paramNTFDCNRegistration.getClientTCPPort());
    paramNTFDCNRegistration.setState(NotificationRegistration.RegistrationState.CLOSED);

    doUnregisterDatabaseChangeNotification(paramNTFDCNRegistration.getRegId(), new StringBuilder().append("(ADDRESS=(PROTOCOL=tcp)(HOST=").append(paramNTFDCNRegistration.getClientHost()).append(")(PORT=").append(paramNTFDCNRegistration.getClientTCPPort()).append("))?PR=0").toString());
  }

  public String getDataIntegrityAlgorithmName()
    throws SQLException
  {
    return this.net.getDataIntegrityName();
  }

  public String getEncryptionAlgorithmName()
    throws SQLException
  {
    return this.net.getEncryptionName();
  }

  public String getAuthenticationAdaptorName()
    throws SQLException
  {
    return this.net.getAuthenticationAdaptorName();
  }

  void validateConnectionProperties()
    throws SQLException
  {
    super.validateConnectionProperties();

    String str = ".*[\\00\\(\\)].*";
    SQLException localSQLException;
    if ((this.thinVsessionOsuser != null) && ((this.thinVsessionOsuser.matches(str)) || (this.thinVsessionOsuser.length() > 30)))
    {
      localSQLException = DatabaseError.createSqlException(null, 190, new StringBuilder().append("Property is 'v$session.osuser' and value is '").append(this.thinVsessionOsuser).append("'").toString());
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if ((this.thinVsessionTerminal != null) && ((this.thinVsessionTerminal.matches(str)) || (this.thinVsessionTerminal.length() > 30)))
    {
      localSQLException = DatabaseError.createSqlException(null, 190, new StringBuilder().append("Property is 'v$session.terminal' and value is '").append(this.thinVsessionTerminal).append("'").toString());
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if ((this.thinVsessionMachine != null) && ((this.thinVsessionMachine.matches(str)) || (this.thinVsessionMachine.length() > 64)))
    {
      localSQLException = DatabaseError.createSqlException(null, 190, new StringBuilder().append("Property is 'v$session.machine' and value is '").append(this.thinVsessionMachine).append("'").toString());
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if ((this.thinVsessionProgram != null) && ((this.thinVsessionProgram.matches(str)) || (this.thinVsessionProgram.length() > 48)))
    {
      localSQLException = DatabaseError.createSqlException(null, 190, new StringBuilder().append("Property is 'v$session.program' and value is '").append(this.thinVsessionProgram).append("'").toString());
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if ((this.thinVsessionProcess != null) && ((this.thinVsessionProcess.matches(str)) || (this.thinVsessionProcess.length() > 24)))
    {
      localSQLException = DatabaseError.createSqlException(null, 190, new StringBuilder().append("Property is 'v$session.process' and value is '").append(this.thinVsessionProcess).append("'").toString());
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if ((this.thinVsessionIname != null) && (this.thinVsessionIname.matches(str)))
    {
      localSQLException = DatabaseError.createSqlException(null, 190, new StringBuilder().append("Property is 'v$session.iname' and value is '").append(this.thinVsessionIname).append("'").toString());
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if ((this.thinVsessionEname != null) && (this.thinVsessionEname.matches(str)))
    {
      localSQLException = DatabaseError.createSqlException(null, 190, new StringBuilder().append("Property is 'v$session.ename' and value is '").append(this.thinVsessionEname).append("'").toString());
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  public synchronized byte[] createLightweightSession(String paramString, KeywordValueLong[] paramArrayOfKeywordValueLong, int paramInt, KeywordValueLong[][] paramArrayOfKeywordValueLong1, int[] paramArrayOfInt)
    throws SQLException
  {
    if ((paramArrayOfKeywordValueLong1.length != 1) || (paramArrayOfInt.length != 1))
    {
      localObject = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68);
      ((SQLException)localObject).fillInStackTrace();
      throw ((Throwable)localObject);
    }

    Object localObject = null;
    try
    {
      sendPiggyBackedMessages();
      this.oxsscs.doOXSSCS(paramString, paramArrayOfKeywordValueLong, paramInt);
      localObject = this.oxsscs.getSessionId();
      paramArrayOfKeywordValueLong1[0] = this.oxsscs.getOutKV();
      paramArrayOfInt[0] = this.oxsscs.getOutFlags();
    }
    catch (IOException localIOException)
    {
      handleIOException(localIOException);

      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localIOException);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    return localObject;
  }

  private synchronized void doXSNamespaceOp(OracleConnection.XSOperationCode paramXSOperationCode, byte[] paramArrayOfByte, XSNamespace[] paramArrayOfXSNamespace, XSNamespace[][] paramArrayOfXSNamespace1, boolean paramBoolean)
    throws SQLException
  {
    XSNamespace[] arrayOfXSNamespace = null;
    try
    {
      if (paramBoolean)
        sendPiggyBackedMessages();
      this.xsnsop.doOXSNS(paramXSOperationCode, paramArrayOfByte, paramArrayOfXSNamespace, paramBoolean);
      if (paramBoolean)
        arrayOfXSNamespace = this.xsnsop.getNamespaces();
    }
    catch (IOException localIOException)
    {
      handleIOException(localIOException);

      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localIOException);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if ((paramArrayOfXSNamespace1 != null) && (paramArrayOfXSNamespace1.length > 0))
      paramArrayOfXSNamespace1[0] = arrayOfXSNamespace;
  }

  public void doXSNamespaceOp(OracleConnection.XSOperationCode paramXSOperationCode, byte[] paramArrayOfByte, XSNamespace[] paramArrayOfXSNamespace, XSNamespace[][] paramArrayOfXSNamespace1)
    throws SQLException
  {
    doXSNamespaceOp(paramXSOperationCode, paramArrayOfByte, paramArrayOfXSNamespace, paramArrayOfXSNamespace1, true);
  }

  public void doXSNamespaceOp(OracleConnection.XSOperationCode paramXSOperationCode, byte[] paramArrayOfByte, XSNamespace[] paramArrayOfXSNamespace)
    throws SQLException
  {
    doXSNamespaceOp(paramXSOperationCode, paramArrayOfByte, paramArrayOfXSNamespace, (XSNamespace[][])null, false);
  }

  public synchronized void executeLightweightSessionRoundtrip(int paramInt1, byte[] paramArrayOfByte, KeywordValueLong[] paramArrayOfKeywordValueLong, int paramInt2, KeywordValueLong[][] paramArrayOfKeywordValueLong1, int[] paramArrayOfInt)
    throws SQLException
  {
    if ((paramArrayOfKeywordValueLong1.length != 1) || (paramArrayOfInt.length != 1))
    {
      SQLException localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68);
      localSQLException1.fillInStackTrace();
      throw localSQLException1;
    }
    try
    {
      sendPiggyBackedMessages();
      this.oxssro.doOXSSRO(paramInt1, paramArrayOfByte, paramArrayOfKeywordValueLong, paramInt2);
      paramArrayOfKeywordValueLong1[0] = this.oxssro.getOutKV();
      paramArrayOfInt[0] = this.oxssro.getOutFlags();
    }
    catch (IOException localIOException)
    {
      handleIOException(localIOException);

      SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localIOException);
      localSQLException2.fillInStackTrace();
      throw localSQLException2;
    }
  }

  public synchronized void executeLightweightSessionPiggyback(int paramInt1, byte[] paramArrayOfByte, KeywordValueLong[] paramArrayOfKeywordValueLong, int paramInt2)
    throws SQLException
  {
    if (this.lusOffset2 == this.lusFunctionId2.length)
    {
      int i = this.lusFunctionId2.length;
      int[] arrayOfInt1 = new int[i * 2];
      System.arraycopy(this.lusFunctionId2, 0, arrayOfInt1, 0, i);
      byte[][] arrayOfByte = new byte[i * 2][];
      System.arraycopy(this.lusSessionId2, 0, arrayOfByte, 0, i);
      KeywordValueLong[][] arrayOfKeywordValueLong; = new KeywordValueLong[i * 2][];
      System.arraycopy(this.lusInKeyVal2, 0, arrayOfKeywordValueLong;, 0, i);
      int[] arrayOfInt2 = new int[i * 2];
      System.arraycopy(this.lusInFlags2, 0, arrayOfInt2, 0, i);
      this.lusFunctionId2 = arrayOfInt1;
      this.lusSessionId2 = arrayOfByte;
      this.lusInKeyVal2 = arrayOfKeywordValueLong;;
      this.lusInFlags2 = arrayOfInt2;
    }
    this.lusFunctionId2[this.lusOffset2] = paramInt1;
    this.lusSessionId2[this.lusOffset2] = paramArrayOfByte;
    this.lusInKeyVal2[this.lusOffset2] = paramArrayOfKeywordValueLong;
    this.lusInFlags2[this.lusOffset2] = paramInt2;
    this.lusOffset2 += 1;
  }

  public void addXSEventListener(XSEventListener paramXSEventListener, Executor paramExecutor)
    throws SQLException
  {
    if (this.lifecycle != 1)
    {
      localObject1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 8);
      ((SQLException)localObject1).fillInStackTrace();
      throw ((Throwable)localObject1);
    }

    Object localObject1 = new NTFEventListener(paramXSEventListener);
    ((NTFEventListener)localObject1).setExecutor(paramExecutor);
    synchronized (this.xsListeners)
    {
      int i = this.xsListeners.length;
      for (int j = 0; j < i; j++) {
        if (this.xsListeners[j].getXSEventListener() == paramXSEventListener)
        {
          SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 248);
          localSQLException.fillInStackTrace();
          throw localSQLException;
        }
      }

      NTFEventListener[] arrayOfNTFEventListener = new NTFEventListener[i + 1];
      System.arraycopy(this.xsListeners, 0, arrayOfNTFEventListener, 0, i);

      arrayOfNTFEventListener[i] = localObject1;

      this.xsListeners = arrayOfNTFEventListener;
    }
  }

  public void addXSEventListener(XSEventListener paramXSEventListener)
    throws SQLException
  {
    addXSEventListener(paramXSEventListener, null);
  }

  public void removeXSEventListener(XSEventListener paramXSEventListener)
    throws SQLException
  {
    synchronized (this.xsListeners)
    {
      int i = 0;
      int j = this.xsListeners.length;

      for (i = 0; (i < j) && 
        (this.xsListeners[i].getXSEventListener() != paramXSEventListener); i++);
      if (i == j)
      {
        localObject1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 249);
        ((SQLException)localObject1).fillInStackTrace();
        throw ((Throwable)localObject1);
      }

      Object localObject1 = new NTFEventListener[j - 1];
      int k = 0;
      for (i = 0; i < j; i++) {
        if (this.xsListeners[i].getXSEventListener() != paramXSEventListener) {
          localObject1[(k++)] = this.xsListeners[i];
        }
      }
      this.xsListeners = ((NTFEventListener[])localObject1);
    }
  }

  void notify(final NTFXSEvent paramNTFXSEvent)
  {
    NTFEventListener[] arrayOfNTFEventListener = this.xsListeners;

    int i = arrayOfNTFEventListener.length;
    for (int j = 0; j < i; j++)
    {
      Executor localExecutor = arrayOfNTFEventListener[j].getExecutor();
      if (localExecutor != null)
      {
        final XSEventListener localXSEventListener = arrayOfNTFEventListener[j].getXSEventListener();

        localExecutor.execute(new Runnable() {
          public void run() {
            localXSEventListener.onXSEvent(paramNTFXSEvent);
          }
        });
      }
      else
      {
        arrayOfNTFEventListener[j].getXSEventListener().onXSEvent(paramNTFXSEvent);
      }
    }
  }
}