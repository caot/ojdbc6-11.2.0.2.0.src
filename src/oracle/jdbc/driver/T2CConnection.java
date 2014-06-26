package oracle.jdbc.driver;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.TimeZone;
import oracle.jdbc.OracleOCIFailover;
import oracle.jdbc.internal.OracleConnection;
import oracle.jdbc.internal.OracleConnection.InstanceProperty;
import oracle.jdbc.oracore.OracleTypeADT;
import oracle.jdbc.oracore.OracleTypeCLOB;
import oracle.jdbc.pool.OracleOCIConnectionPool;
import oracle.jdbc.pool.OraclePooledConnection;
import oracle.sql.BFILE;
import oracle.sql.BLOB;
import oracle.sql.BfileDBAccess;
import oracle.sql.BlobDBAccess;
import oracle.sql.CLOB;
import oracle.sql.ClobDBAccess;
import oracle.sql.LobPlsqlUtil;
import oracle.sql.NCLOB;
import oracle.sql.SQLName;
import oracle.sql.ZONEIDMAP;
import oracle.sql.converter.CharacterSetMetaData;

public class T2CConnection extends PhysicalConnection
  implements BfileDBAccess, BlobDBAccess, ClobDBAccess
{
  static final long JDBC_OCI_LIBRARY_VERSION = Long.parseLong("11.2.0.2.0".replaceAll("\\.", ""));

  short[] queryMetaData1 = null;
  byte[] queryMetaData2 = null;
  int queryMetaData1Offset = 0;
  int queryMetaData2Offset = 0;
  private String password;
  int fatalErrorNumber = 0;
  String fatalErrorMessage = null;
  static final int QMD_dbtype = 0;
  static final int QMD_dbsize = 1;
  static final int QMD_nullok = 2;
  static final int QMD_precision = 3;
  static final int QMD_scale = 4;
  static final int QMD_formOfUse = 5;
  static final int QMD_columnNameLength = 6;
  static final int QMD_tdo0 = 7;
  static final int QMD_tdo1 = 8;
  static final int QMD_tdo2 = 9;
  static final int QMD_tdo3 = 10;
  static final int QMD_charLength = 11;
  static final int QMD_typeNameLength = 12;
  static final int T2C_LOCATOR_MAX_LEN = 16;
  static final int T2C_LINEARIZED_LOCATOR_MAX_LEN = 4000;
  static final int T2C_LINEARIZED_BFILE_LOCATOR_MAX_LEN = 530;
  static final int METADATA1_INDICES_PER_COLUMN = 13;
  protected static final int SIZEOF_QUERYMETADATA2 = 8;
  static final String defaultDriverNameAttribute = "jdbcoci";
  int queryMetaData1Size = 100;
  int queryMetaData2Size = 800;
  long m_nativeState;
  short m_clientCharacterSet;
  byte byteAlign;
  private static final int EOJ_SUCCESS = 0;
  private static final int EOJ_ERROR = -1;
  private static final int EOJ_WARNING = 1;
  private static final int EOJ_GET_STORAGE_ERROR = -4;
  private static final int EOJ_ORA3113_SERVER_NORMAL = -6;
  private static final String OCILIBRARY = "ocijdbc11";
  private int logon_mode = 0;
  static final int LOGON_MODE_DEFAULT = 0;
  static final int LOGON_MODE_SYSDBA = 2;
  static final int LOGON_MODE_SYSOPER = 4;
  static final int LOGON_MODE_SYSASM = 32768;
  static final int LOGON_MODE_CONNECTION_POOL = 5;
  static final int LOGON_MODE_CONNPOOL_CONNECTION = 6;
  static final int LOGON_MODE_CONNPOOL_PROXY_CONNECTION = 7;
  static final int LOGON_MODE_CONNPOOL_ALIASED_CONNECTION = 8;
  static final int T2C_PROXYTYPE_NONE = 0;
  static final int T2C_PROXYTYPE_USER_NAME = 1;
  static final int T2C_PROXYTYPE_DISTINGUISHED_NAME = 2;
  static final int T2C_PROXYTYPE_CERTIFICATE = 3;
  static final int T2C_CONNECTION_FLAG_DEFAULT_LOB_PREFETCH = 0;
  static final int T2C_CONNECTION_FLAG_PRELIM_AUTH = 1;
  private static boolean isLibraryLoaded;
  OracleOCIFailover appCallback = null;
  Object appCallbackObject = null;
  private Properties nativeInfo;
  ByteBuffer nioBufferForLob;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  protected T2CConnection(String paramString, Properties paramProperties, OracleDriverExtension paramOracleDriverExtension)
    throws SQLException
  {
    super(paramString, paramProperties, paramOracleDriverExtension);

    initialize();
  }

  final void initializePassword(String paramString)
    throws SQLException
  {
    this.password = paramString;
  }

  protected void initialize()
  {
    allocQueryMetaDataBuffers();
  }

  private void allocQueryMetaDataBuffers()
  {
    this.queryMetaData1Offset = 0;
    this.queryMetaData1 = new short[this.queryMetaData1Size * 13];

    this.queryMetaData2Offset = 0;
    this.queryMetaData2 = new byte[this.queryMetaData2Size];

    this.namedTypeAccessorByteLen = 0;
    this.refTypeAccessorByteLen = 0;
  }

  void reallocateQueryMetaData(int paramInt1, int paramInt2)
  {
    this.queryMetaData1 = null;
    this.queryMetaData2 = null;

    this.queryMetaData1Size = Math.max(paramInt1, this.queryMetaData1Size);
    this.queryMetaData2Size = Math.max(paramInt2, this.queryMetaData2Size);

    allocQueryMetaDataBuffers();
  }

  protected void logon()
    throws SQLException
  {
    if (this.database == null)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 64);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if (!isLibraryLoaded) {
      loadNativeLibrary(this.ocidll);
    }

    if (this.ociConnectionPoolIsPooling)
    {
      processOCIConnectionPooling();
    }
    else
    {
      long l1 = this.ociSvcCtxHandle;
      long l2 = this.ociEnvHandle;
      long l3 = this.ociErrHandle;

      if ((l1 != 0L) && (l2 != 0L))
      {
        if (this.ociDriverCharset != null) {
          this.m_clientCharacterSet = new Integer(this.ociDriverCharset).shortValue();
        }
        else
        {
          SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 89);
          sqlexception.fillInStackTrace();
          throw sqlexception;
        }

        this.conversion = new DBConversion(this.m_clientCharacterSet, this.m_clientCharacterSet, this.m_clientCharacterSet);

        short[] localObject1 = new short[5];
        long[] localObject2 = new long[] { this.defaultLobPrefetchSize };

        this.sqlWarning = checkError(t2cUseConnection(this.m_nativeState, l2, l1, l3, (short[])localObject1, (long[])localObject2), this.sqlWarning);

        this.conversion = new DBConversion(localObject1[0], this.m_clientCharacterSet, localObject1[1]);
        this.byteAlign = ((byte)(localObject1[2] & 0xFF));
        this.timeZoneVersionNumber = ((localObject1[3] << 16) + (localObject1[4] & 0xFFFF));

        return;
      }

      if (this.internalLogon == null)
        this.logon_mode = 0;
      else if (this.internalLogon.equalsIgnoreCase("SYSDBA"))
        this.logon_mode = 2;
      else if (this.internalLogon.equalsIgnoreCase("SYSOPER"))
        this.logon_mode = 4;
      else if (this.internalLogon.equalsIgnoreCase("SYSASM")) {
        this.logon_mode = 32768;
      }
      byte[] localObject1 = null;
      byte[] localObject2 = null;
      byte[] arrayOfByte1 = null;
      String str1 = this.setNewPassword;
      byte[] arrayOfByte2 = new byte[0];
      byte[] arrayOfByte3 = new byte[0];
      byte[] arrayOfByte4 = new byte[0];

      if (this.nlsLangBackdoor)
      {
        this.m_clientCharacterSet = getDriverCharSetIdFromNLS_LANG(this.ocidll);
      }
      else
      {
        this.m_clientCharacterSet = getClientCharSetId();
      }

      if (str1 != null) {
        arrayOfByte2 = DBConversion.stringToDriverCharBytes(str1, this.m_clientCharacterSet);
      }
      if (this.editionName != null) {
        arrayOfByte3 = DBConversion.stringToDriverCharBytes(this.editionName, this.m_clientCharacterSet);
      }
      if (this.driverNameAttribute == null)
        arrayOfByte4 = DBConversion.stringToDriverCharBytes("jdbcoci", this.m_clientCharacterSet);
      else {
        arrayOfByte4 = DBConversion.stringToDriverCharBytes(this.driverNameAttribute, this.m_clientCharacterSet);
      }
      localObject1 = this.userName == null ? new byte[0] : DBConversion.stringToDriverCharBytes(this.userName, this.m_clientCharacterSet);

      localObject2 = this.proxyClientName == null ? new byte[0] : DBConversion.stringToDriverCharBytes(this.proxyClientName, this.m_clientCharacterSet);

      arrayOfByte1 = this.password == null ? new byte[0] : DBConversion.stringToDriverCharBytes(this.password, this.m_clientCharacterSet);

      byte[] arrayOfByte5 = DBConversion.stringToDriverCharBytes(this.database, this.m_clientCharacterSet);

      short[] arrayOfShort = new short[5];
      String str2 = null;
      byte[] arrayOfByte6 = (str2 = CharacterSetMetaData.getNLSLanguage(Locale.getDefault())) != null ? str2.getBytes() : null;

      byte[] arrayOfByte7 = (str2 = CharacterSetMetaData.getNLSTerritory(Locale.getDefault())) != null ? str2.getBytes() : null;

      if ((arrayOfByte6 == null) || (arrayOfByte7 == null))
      {
        SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 176);
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }

      Object localObject3 = TimeZone.getDefault();
      String str3 = ((TimeZone)localObject3).getID();

      if ((!ZONEIDMAP.isValidRegion(str3)) || (!this.timezoneAsRegion))
      {
        int i = ((TimeZone)localObject3).getOffset(System.currentTimeMillis());
        int j = i / 3600000;
        int k = i / 60000 % 60;

        str3 = new StringBuilder().append(j < 0 ? new StringBuilder().append("").append(j).toString() : new StringBuilder().append("+").append(j).toString()).append(k < 10 ? new StringBuilder().append(":0").append(k).toString() : new StringBuilder().append(":").append(k).toString()).toString();
      }

      doSetSessionTimeZone(str3);

      this.sessionTimeZone = str3;

      this.conversion = new DBConversion(this.m_clientCharacterSet, this.m_clientCharacterSet, this.m_clientCharacterSet);

      long[] arrayOfLong = { this.defaultLobPrefetchSize, this.prelimAuth ? 1 : 0 };

      if (this.m_nativeState == 0L)
      {
        this.sqlWarning = checkError(t2cCreateState((byte[])localObject1, localObject1.length, (byte[])localObject2, localObject2.length, arrayOfByte1, arrayOfByte1.length, arrayOfByte2, arrayOfByte2.length, arrayOfByte3, arrayOfByte3.length, arrayOfByte4, arrayOfByte4.length, arrayOfByte5, arrayOfByte5.length, this.m_clientCharacterSet, this.logon_mode, arrayOfShort, arrayOfByte6, arrayOfByte7, arrayOfLong), this.sqlWarning);
      }
      else
      {
        this.sqlWarning = checkError(t2cLogon(this.m_nativeState, (byte[])localObject1, localObject1.length, (byte[])localObject2, localObject2.length, arrayOfByte1, arrayOfByte1.length, arrayOfByte2, arrayOfByte2.length, arrayOfByte3, arrayOfByte3.length, arrayOfByte4, arrayOfByte4.length, arrayOfByte5, arrayOfByte5.length, this.logon_mode, arrayOfShort, arrayOfByte6, arrayOfByte7, arrayOfLong), this.sqlWarning);
      }

      this.conversion = new DBConversion(arrayOfShort[0], this.m_clientCharacterSet, arrayOfShort[1]);
      this.byteAlign = ((byte)(arrayOfShort[2] & 0xFF));
      this.timeZoneVersionNumber = ((arrayOfShort[3] << 16) + (arrayOfShort[4] & 0xFFFF));
    }
  }

  protected void logoff()
    throws SQLException
  {
    try
    {
      if (this.lifecycle == 2)
      {
        checkError(t2cLogoff(this.m_nativeState));
      }
    }
    catch (NullPointerException localNullPointerException) {
    }
    this.m_nativeState = 0L;
  }

  public void open(OracleStatement paramOracleStatement)
    throws SQLException
  {
    byte[] arrayOfByte = paramOracleStatement.sqlObject.getSql(paramOracleStatement.processEscapes, paramOracleStatement.convertNcharLiterals).getBytes();

    checkError(t2cCreateStatement(this.m_nativeState, 0L, arrayOfByte, arrayOfByte.length, paramOracleStatement, false, paramOracleStatement.rowPrefetch));
  }

  void cancelOperationOnServer()
    throws SQLException
  {
    checkError(t2cCancel(this.m_nativeState));
  }

  native int t2cAbort(long paramLong);

  void doAbort()
    throws SQLException
  {
    checkError(t2cAbort(this.m_nativeState));
  }

  protected void doSetAutoCommit(boolean paramBoolean)
    throws SQLException
  {
    checkError(t2cSetAutoCommit(this.m_nativeState, paramBoolean));
  }

  protected void doCommit(int paramInt)
    throws SQLException
  {
    checkError(t2cCommit(this.m_nativeState, paramInt));
  }

  protected void doRollback()
    throws SQLException
  {
    checkError(t2cRollback(this.m_nativeState));
  }

  synchronized int doPingDatabase()
    throws SQLException
  {
    if (t2cPingDatabase(this.m_nativeState) == 0) {
      return 0;
    }
    return -1;
  }

  protected String doGetDatabaseProductVersion()
    throws SQLException
  {
    byte[] arrayOfByte = t2cGetProductionVersion(this.m_nativeState);

    return this.conversion.CharBytesToString(arrayOfByte, arrayOfByte.length);
  }

  protected short doGetVersionNumber()
    throws SQLException
  {
    short s = 0;
    try
    {
      String str1 = doGetDatabaseProductVersion();

      StringTokenizer localStringTokenizer = new StringTokenizer(str1.trim(), " .", false);
      String str2 = null;
      int i = 0;
      int j = 0;

      while (localStringTokenizer.hasMoreTokens())
      {
        str2 = localStringTokenizer.nextToken();
        try
        {
          j = Integer.decode(str2).shortValue();
          s = (short)(s * 10 + j);
          i++;

          if (i == 4) {
            break;
          }
        }
        catch (NumberFormatException localNumberFormatException)
        {
        }

      }

    }
    catch (NoSuchElementException localNoSuchElementException)
    {
    }

    if (s == -1) {
      s = 0;
    }

    return s;
  }

  public ClobDBAccess createClobDBAccess()
  {
    return this;
  }

  public BlobDBAccess createBlobDBAccess()
  {
    return this;
  }

  public BfileDBAccess createBfileDBAccess()
  {
    return this;
  }

  protected SQLWarning checkError(int paramInt)
    throws SQLException
  {
    return checkError(paramInt, null);
  }

  protected SQLWarning checkError(int paramInt, SQLWarning paramSQLWarning)
    throws SQLException
  {
    T2CError localObject1;
    switch (paramInt)
    {
    case 0:
      break;
    case -1:
    case 1:
      localObject1 = new T2CError();

      int i = -1;

      if ((this.lifecycle == 1) || (this.lifecycle == 16))
      {
        i = t2cDescribeError(this.m_nativeState, (T2CError)localObject1, ((T2CError)localObject1).m_errorMessage);
      }
      else {
        if (this.fatalErrorNumber != 0)
        {
          SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 269);
          sqlexception.fillInStackTrace();
          throw sqlexception;
        }

        SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 8);
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }

      Object localObject2 = null;
      if (i != -1)
      {
        int j = 0;

        while ((j < ((T2CError)localObject1).m_errorMessage.length) && (localObject1.m_errorMessage[j] != 0)) {
          j++;
        }
        if (this.conversion == null) throw new Error("conversion == null");
        if (localObject1 == null) throw new Error("l_error == null");
        localObject2 = this.conversion.CharBytesToString(((T2CError)localObject1).m_errorMessage, j, true);
      }

      switch (((T2CError)localObject1).m_errorNumber)
      {
      case 28:
      case 600:
      case 1012:
      case 1041:
        internalClose();
        break;
      case 3113:
      case 3114:
        close();
        break;
      case -6:
        ((T2CError)localObject1).m_errorNumber = 3113;
      }
      SQLException localSQLException;
      if (i == -1)
      {
        localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 1, "Fetch error message failed!");
        localSQLException.fillInStackTrace();
        throw localSQLException;
      }

      if (paramInt == -1)
      {
        localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), (String)localObject2, ((T2CError)localObject1).m_errorNumber);
        localSQLException.fillInStackTrace();
        throw localSQLException;
      }

      paramSQLWarning = DatabaseError.addSqlWarning(paramSQLWarning, (String)localObject2, ((T2CError)localObject1).m_errorNumber);

      break;
    case -4:
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 254);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    case -3:
    case -2:
    }

    return paramSQLWarning;
  }

  OracleStatement RefCursorBytesToStatement(byte[] paramArrayOfByte, OracleStatement paramOracleStatement)
    throws SQLException
  {
    T2CStatement localT2CStatement = new T2CStatement(this, 1, this.defaultRowPrefetch, -1, -1);

    localT2CStatement.needToParse = false;
    localT2CStatement.serverCursor = true;
    localT2CStatement.isOpen = true;
    localT2CStatement.processEscapes = false;

    localT2CStatement.prepareForNewResults(true, false);
    localT2CStatement.sqlObject.initialize("select unknown as ref cursor from whatever");

    localT2CStatement.sqlKind = 1;

    checkError(t2cCreateStatement(this.m_nativeState, paramOracleStatement.c_state, paramArrayOfByte, paramArrayOfByte.length, localT2CStatement, true, this.defaultRowPrefetch));

    paramOracleStatement.addChild(localT2CStatement);
    return localT2CStatement;
  }

  public void getForm(OracleTypeADT paramOracleTypeADT, OracleTypeCLOB paramOracleTypeCLOB, int paramInt)
    throws SQLException
  {
    int i = 0;

    if (paramOracleTypeCLOB != null)
    {
      String[] arrayOfString1 = new String[1];
      String[] arrayOfString2 = new String[1];

      SQLName.parse(paramOracleTypeADT.getFullName(), arrayOfString1, arrayOfString2, true);

      String str = new StringBuilder().append("\"").append(arrayOfString1[0]).append("\".\"").append(arrayOfString2[0]).append("\"").toString();

      byte[] arrayOfByte = this.conversion.StringToCharBytes(str);

      int j = t2cGetFormOfUse(this.m_nativeState, paramOracleTypeCLOB, arrayOfByte, arrayOfByte.length, paramInt);

      if (j < 0) {
        checkError(j);
      }

      paramOracleTypeCLOB.setForm(j);
    }
  }

  public long getTdoCState(String paramString1, String paramString2)
    throws SQLException
  {
    String str = new StringBuilder().append("\"").append(paramString1).append("\".\"").append(paramString2).append("\"").toString();
    byte[] arrayOfByte = this.conversion.StringToCharBytes(str);
    int[] arrayOfInt = new int[1];
    long l = t2cGetTDO(this.m_nativeState, arrayOfByte, arrayOfByte.length, arrayOfInt);
    if (l == 0L)
    {
      checkError(arrayOfInt[0]);
    }
    return l;
  }

  /** @deprecated */
  public Properties getDBAccessProperties()
    throws SQLException
  {
    return getOCIHandles();
  }

  public synchronized Properties getOCIHandles()
    throws SQLException
  {
    long[] localObject;
    if (this.lifecycle != 1)
    {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 8);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    if (this.nativeInfo == null)
    {
      localObject = new long[3];

      checkError(t2cGetHandles(this.m_nativeState, (long[])localObject));

      this.nativeInfo = new Properties();

      this.nativeInfo.put("OCIEnvHandle", String.valueOf(localObject[0]));
      this.nativeInfo.put("OCISvcCtxHandle", String.valueOf(localObject[1]));
      this.nativeInfo.put("OCIErrHandle", String.valueOf(localObject[2]));
      this.nativeInfo.put("ClientCharSet", String.valueOf(this.m_clientCharacterSet));
    }

    return this.nativeInfo;
  }

  public Properties getServerSessionInfo()
    throws SQLException
  {
    if (this.lifecycle != 1)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 8);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if (this.sessionProperties == null) {
      this.sessionProperties = new Properties();
    }

    if (getVersionNumber() < 10200)
      queryFCFProperties(this.sessionProperties);
    else {
      checkError(t2cGetServerSessionInfo(this.m_nativeState, this.sessionProperties));
    }
    return this.sessionProperties;
  }

  public byte getInstanceProperty(OracleConnection.InstanceProperty paramInstanceProperty)
    throws SQLException
  {
    byte b = 0;
    if (paramInstanceProperty == OracleConnection.InstanceProperty.ASM_VOLUME_SUPPORTED)
    {
      b = t2cGetAsmVolProperty(this.m_nativeState);
    }
    else if (paramInstanceProperty == OracleConnection.InstanceProperty.INSTANCE_TYPE)
    {
      b = t2cGetInstanceType(this.m_nativeState);
    }
    return b;
  }

  public Properties getConnectionPoolInfo()
    throws SQLException
  {
    if (this.lifecycle != 1)
    {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 8);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }
    Properties localObject = new Properties();

    checkError(t2cGetConnPoolInfo(this.m_nativeState, (Properties)localObject));

    return localObject;
  }

  public void setConnectionPoolInfo(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
    throws SQLException
  {
    checkError(t2cSetConnPoolInfo(this.m_nativeState, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6));
  }

  public void ociPasswordChange(String paramString1, String paramString2, String paramString3)
    throws SQLException
  {
    if (this.lifecycle != 1)
    {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 8);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }
    byte[] localObject = paramString1 == null ? new byte[0] : DBConversion.stringToDriverCharBytes(paramString1, this.m_clientCharacterSet);

    byte[] arrayOfByte1 = paramString2 == null ? new byte[0] : DBConversion.stringToDriverCharBytes(paramString2, this.m_clientCharacterSet);

    byte[] arrayOfByte2 = paramString3 == null ? new byte[0] : DBConversion.stringToDriverCharBytes(paramString3, this.m_clientCharacterSet);

    this.sqlWarning = checkError(t2cPasswordChange(this.m_nativeState, (byte[])localObject, localObject.length, arrayOfByte1, arrayOfByte1.length, arrayOfByte2, arrayOfByte2.length), this.sqlWarning);
  }

  private void processOCIConnectionPooling()
    throws SQLException
  {
    if (this.lifecycle != 1)
    {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 8);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject1 = null;

    if (this.ociConnectionPoolLogonMode == "connection_pool")
    {
      if (this.nlsLangBackdoor)
      {
        this.m_clientCharacterSet = getDriverCharSetIdFromNLS_LANG(this.ocidll);
      }
      else
      {
        this.m_clientCharacterSet = getClientCharSetId();
      }
    }
    else {
      localObject1 = (T2CConnection)this.ociConnectionPoolObject;
      this.m_clientCharacterSet = ((T2CConnection)localObject1).m_clientCharacterSet;
    }

    byte[] arrayOfByte1 = null;

    byte[] arrayOfByte2 = this.password == null ? new byte[0] : DBConversion.stringToDriverCharBytes(this.password, this.m_clientCharacterSet);

    byte[] arrayOfByte3 = this.editionName == null ? new byte[0] : DBConversion.stringToDriverCharBytes(this.editionName, this.m_clientCharacterSet);

    byte[] arrayOfByte4 = DBConversion.stringToDriverCharBytes(this.driverNameAttribute == null ? "jdbcoci" : this.driverNameAttribute, this.m_clientCharacterSet);

    byte[] arrayOfByte5 = DBConversion.stringToDriverCharBytes(this.database, this.m_clientCharacterSet);

    byte[] arrayOfByte6 = CharacterSetMetaData.getNLSLanguage(Locale.getDefault()).getBytes();

    byte[] arrayOfByte7 = CharacterSetMetaData.getNLSTerritory(Locale.getDefault()).getBytes();

    if ((arrayOfByte6 == null) || (arrayOfByte7 == null))
    {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 176);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    short[] localObject2 = new short[5];
    long[] arrayOfLong = { this.defaultLobPrefetchSize };
    int[] localObject3;
    if (this.ociConnectionPoolLogonMode == "connection_pool")
    {
      arrayOfByte1 = this.userName == null ? new byte[0] : DBConversion.stringToDriverCharBytes(this.userName, this.m_clientCharacterSet);

      this.conversion = new DBConversion(this.m_clientCharacterSet, this.m_clientCharacterSet, this.m_clientCharacterSet);

      this.logon_mode = 5;

      if (this.lifecycle == 1)
      {
        localObject3 = new int[6];

        OracleOCIConnectionPool.readPoolConfig(this.ociConnectionPoolMinLimit, this.ociConnectionPoolMaxLimit, this.ociConnectionPoolIncrement, this.ociConnectionPoolTimeout, this.ociConnectionPoolNoWait, this.ociConnectionPoolTransactionDistributed, (int[])localObject3);

        this.sqlWarning = checkError(t2cCreateConnPool(arrayOfByte1, arrayOfByte1.length, arrayOfByte2, arrayOfByte2.length, arrayOfByte5, arrayOfByte5.length, this.m_clientCharacterSet, this.logon_mode, localObject3[0], localObject3[1], localObject3[2], localObject3[3], localObject3[4], localObject3[5]), this.sqlWarning);

        this.versionNumber = 10000;
      }
      else
      {
        SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 0, "Internal Error: ");
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }

    }
    else if (this.ociConnectionPoolLogonMode == "connpool_connection")
    {
      this.logon_mode = 6;

      arrayOfByte1 = this.userName == null ? new byte[0] : DBConversion.stringToDriverCharBytes(this.userName, this.m_clientCharacterSet);

      this.conversion = new DBConversion(this.m_clientCharacterSet, this.m_clientCharacterSet, this.m_clientCharacterSet);

      this.sqlWarning = checkError(t2cConnPoolLogon(((T2CConnection)localObject1).m_nativeState, arrayOfByte1, arrayOfByte1.length, arrayOfByte2, arrayOfByte2.length, arrayOfByte3, arrayOfByte3.length, arrayOfByte4, arrayOfByte4.length, arrayOfByte5, arrayOfByte5.length, this.logon_mode, 0, 0, null, null, 0, null, 0, null, 0, null, 0, null, 0, (short[])localObject2, arrayOfByte6, arrayOfByte7, arrayOfLong), this.sqlWarning);
    }
    else if (this.ociConnectionPoolLogonMode == "connpool_alias_connection")
    {
      this.logon_mode = 8;

      byte[] bytea = (byte[])this.ociConnectionPoolConnID;

      arrayOfByte1 = this.userName == null ? new byte[0] : DBConversion.stringToDriverCharBytes(this.userName, this.m_clientCharacterSet);

      this.conversion = new DBConversion(this.m_clientCharacterSet, this.m_clientCharacterSet, this.m_clientCharacterSet);

      this.sqlWarning = checkError(t2cConnPoolLogon(((T2CConnection)localObject1).m_nativeState, arrayOfByte1, arrayOfByte1.length, arrayOfByte2, arrayOfByte2.length, arrayOfByte3, arrayOfByte3.length, arrayOfByte4, arrayOfByte4.length, arrayOfByte5, arrayOfByte5.length, this.logon_mode, 0, 0, null, null, 0, null, 0, null, 0, null, 0, bytea, bytea == null ? 0 : bytea.length, (short[])localObject2, arrayOfByte6, arrayOfByte7, arrayOfLong), this.sqlWarning);
    }
    else if (this.ociConnectionPoolLogonMode == "connpool_proxy_connection")
    {
      this.logon_mode = 7;

      String str = this.ociConnectionPoolProxyType;

      int i = this.ociConnectionPoolProxyNumRoles.intValue();

      String[] arrayOfString = null;

      if (i > 0)
      {
        arrayOfString = (String[])this.ociConnectionPoolProxyRoles;
      }

      byte[] arrayOfByte8 = null;
      byte[] arrayOfByte9 = null;
      byte[] arrayOfByte10 = null;
      byte[] arrayOfByte11 = null;

      int j = 0;
      if (str == "proxytype_user_name")
      {
        j = 1;

        str = this.ociConnectionPoolProxyUserName;

        if (str != null) {
          arrayOfByte8 = str.getBytes();
        }
        str = this.ociConnectionPoolProxyPassword;

        if (str != null)
          arrayOfByte9 = str.getBytes();
      }
      else if (str == "proxytype_distinguished_name")
      {
        j = 2;

        str = this.ociConnectionPoolProxyDistinguishedName;

        if (str != null)
          arrayOfByte10 = str.getBytes();
      }
      else if (str == "proxytype_certificate")
      {
        j = 3;

        arrayOfByte11 = (byte[])this.ociConnectionPoolProxyCertificate;
      }
      else
      {
        SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 107);
        localSQLException.fillInStackTrace();
        throw localSQLException;
      }

      arrayOfByte1 = this.userName == null ? new byte[0] : DBConversion.stringToDriverCharBytes(this.userName, this.m_clientCharacterSet);

      this.conversion = new DBConversion(this.m_clientCharacterSet, this.m_clientCharacterSet, this.m_clientCharacterSet);

      this.sqlWarning = checkError(t2cConnPoolLogon(((T2CConnection)localObject1).m_nativeState, arrayOfByte1, arrayOfByte1.length, arrayOfByte2, arrayOfByte2.length, arrayOfByte3, arrayOfByte3.length, arrayOfByte4, arrayOfByte4.length, arrayOfByte5, arrayOfByte5.length, this.logon_mode, j, i, arrayOfString, arrayOfByte8, arrayOfByte8 == null ? 0 : arrayOfByte8.length, arrayOfByte9, arrayOfByte9 == null ? 0 : arrayOfByte9.length, arrayOfByte10, arrayOfByte10 == null ? 0 : arrayOfByte10.length, arrayOfByte11, arrayOfByte11 == null ? 0 : arrayOfByte11.length, null, 0, (short[])localObject2, arrayOfByte6, arrayOfByte7, arrayOfLong), this.sqlWarning);
    }
    else
    {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 23, "connection-pool-logon");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    this.conversion = new DBConversion(localObject2[0], this.m_clientCharacterSet, localObject2[1]);
    this.byteAlign = ((byte)(localObject2[2] & 0xFF));
    this.timeZoneVersionNumber = ((localObject2[3] << 16) + (localObject2[4] & 0xFFFF));
  }

  public boolean isDescriptorSharable(OracleConnection paramOracleConnection)
    throws SQLException
  {
    T2CConnection localT2CConnection = this;
    PhysicalConnection localPhysicalConnection = (PhysicalConnection)paramOracleConnection.getPhysicalConnection();

    return localT2CConnection == localPhysicalConnection;
  }

  native int t2cBlobRead(long paramLong1, byte[] paramArrayOfByte1, int paramInt1, long paramLong2, int paramInt2, byte[] paramArrayOfByte2, int paramInt3, boolean paramBoolean, ByteBuffer paramByteBuffer);

  native int t2cClobRead(long paramLong1, byte[] paramArrayOfByte, int paramInt1, long paramLong2, int paramInt2, char[] paramArrayOfChar, int paramInt3, boolean paramBoolean1, boolean paramBoolean2, ByteBuffer paramByteBuffer);

  native int t2cBlobWrite(long paramLong1, byte[] paramArrayOfByte1, int paramInt1, long paramLong2, int paramInt2, byte[] paramArrayOfByte2, int paramInt3, byte[][] paramArrayOfByte);

  native int t2cClobWrite(long paramLong1, byte[] paramArrayOfByte, int paramInt1, long paramLong2, int paramInt2, char[] paramArrayOfChar, int paramInt3, byte[][] paramArrayOfByte1, boolean paramBoolean);

  native long t2cLobGetLength(long paramLong, byte[] paramArrayOfByte, int paramInt);

  native int t2cBfileOpen(long paramLong, byte[] paramArrayOfByte, int paramInt, byte[][] paramArrayOfByte1);

  native int t2cBfileIsOpen(long paramLong, byte[] paramArrayOfByte, int paramInt, boolean[] paramArrayOfBoolean);

  native int t2cBfileExists(long paramLong, byte[] paramArrayOfByte, int paramInt, boolean[] paramArrayOfBoolean);

  native String t2cBfileGetName(long paramLong, byte[] paramArrayOfByte, int paramInt);

  native String t2cBfileGetDirAlias(long paramLong, byte[] paramArrayOfByte, int paramInt);

  native int t2cBfileClose(long paramLong, byte[] paramArrayOfByte, int paramInt, byte[][] paramArrayOfByte1);

  native int t2cLobGetChunkSize(long paramLong, byte[] paramArrayOfByte, int paramInt);

  native int t2cLobTrim(long paramLong1, int paramInt1, long paramLong2, byte[] paramArrayOfByte, int paramInt2, byte[][] paramArrayOfByte1);

  native int t2cLobCreateTemporary(long paramLong, int paramInt1, boolean paramBoolean, int paramInt2, short paramShort, byte[][] paramArrayOfByte);

  native int t2cLobFreeTemporary(long paramLong, int paramInt1, byte[] paramArrayOfByte, int paramInt2, byte[][] paramArrayOfByte1);

  native int t2cLobIsTemporary(long paramLong, int paramInt1, byte[] paramArrayOfByte, int paramInt2, boolean[] paramArrayOfBoolean);

  native int t2cLobOpen(long paramLong, int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3, byte[][] paramArrayOfByte1);

  native int t2cLobIsOpen(long paramLong, int paramInt1, byte[] paramArrayOfByte, int paramInt2, boolean[] paramArrayOfBoolean);

  native int t2cLobClose(long paramLong, int paramInt1, byte[] paramArrayOfByte, int paramInt2, byte[][] paramArrayOfByte1);

  private long lobLength(byte[] paramArrayOfByte)
    throws SQLException
  {
    long l = 0L;
    l = t2cLobGetLength(this.m_nativeState, paramArrayOfByte, paramArrayOfByte.length);

    checkError((int)l);

    return l;
  }

  private int blobRead(byte[] paramArrayOfByte1, long paramLong, int paramInt, byte[] paramArrayOfByte2, boolean paramBoolean, ByteBuffer paramByteBuffer)
    throws SQLException
  {
    int i = 0;

    i = t2cBlobRead(this.m_nativeState, paramArrayOfByte1, paramArrayOfByte1.length, paramLong, paramInt, paramArrayOfByte2, paramArrayOfByte2.length, paramBoolean, paramByteBuffer);

    checkError(i);

    return i;
  }

  private int blobWrite(byte[] paramArrayOfByte1, long paramLong, byte[] paramArrayOfByte2, byte[][] paramArrayOfByte, int paramInt1, int paramInt2)
    throws SQLException
  {
    int i = 0;

    i = t2cBlobWrite(this.m_nativeState, paramArrayOfByte1, paramArrayOfByte1.length, paramLong, paramInt2, paramArrayOfByte2, paramInt1, paramArrayOfByte);

    checkError(i);

    return i;
  }

  private int clobWrite(byte[] paramArrayOfByte, long paramLong, char[] paramArrayOfChar, byte[][] paramArrayOfByte1, boolean paramBoolean, int paramInt1, int paramInt2)
    throws SQLException
  {
    int i = 0;

    i = t2cClobWrite(this.m_nativeState, paramArrayOfByte, paramArrayOfByte.length, paramLong, paramInt2, paramArrayOfChar, paramInt1, paramArrayOfByte1, paramBoolean);

    checkError(i);

    return i;
  }

  private int lobGetChunkSize(byte[] paramArrayOfByte)
    throws SQLException
  {
    int i = 0;
    i = t2cLobGetChunkSize(this.m_nativeState, paramArrayOfByte, paramArrayOfByte.length);

    checkError(i);

    return i;
  }

  public synchronized long length(BFILE paramBFILE)
    throws SQLException
  {
    byte[] arrayOfByte = null;

    checkTrue(this.lifecycle == 1, 8);
    checkTrue((paramBFILE != null) && ((arrayOfByte = paramBFILE.getLocator()) != null), 54);

    return lobLength(arrayOfByte);
  }

  public synchronized long position(BFILE paramBFILE, byte[] paramArrayOfByte, long paramLong)
    throws SQLException
  {
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

  public synchronized long position(BFILE paramBFILE1, BFILE paramBFILE2, long paramLong)
    throws SQLException
  {
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
    byte[] arrayOfByte = null;

    checkTrue(this.lifecycle == 1, 8);
    checkTrue((paramBFILE != null) && ((arrayOfByte = paramBFILE.getLocator()) != null), 54);

    if ((paramInt <= 0) || (paramArrayOfByte == null)) {
      return 0;
    }
    if (paramInt > paramArrayOfByte.length) {
      paramInt = paramArrayOfByte.length;
    }
    if (this.useNio)
    {
      int i = paramArrayOfByte.length;
      if ((this.nioBufferForLob == null) || (this.nioBufferForLob.capacity() < i))
        this.nioBufferForLob = ByteBuffer.allocateDirect(i);
      else {
        this.nioBufferForLob.rewind();
      }
    }
    int i = blobRead(arrayOfByte, paramLong, paramInt, paramArrayOfByte, this.useNio, this.nioBufferForLob);
    if (this.useNio)
    {
      this.nioBufferForLob.get(paramArrayOfByte);
    }

    return i;
  }

  public synchronized String getName(BFILE paramBFILE)
    throws SQLException
  {
    byte[] arrayOfByte = null;
    String str = null;

    checkTrue(this.lifecycle == 1, 8);
    checkTrue((paramBFILE != null) && ((arrayOfByte = paramBFILE.getLocator()) != null), 54);

    str = t2cBfileGetName(this.m_nativeState, arrayOfByte, arrayOfByte.length);

    checkError(str.length());

    return str;
  }

  public synchronized String getDirAlias(BFILE paramBFILE)
    throws SQLException
  {
    byte[] arrayOfByte = null;
    String str = null;

    checkTrue(this.lifecycle == 1, 8);
    checkTrue((paramBFILE != null) && ((arrayOfByte = paramBFILE.getLocator()) != null), 54);

    str = t2cBfileGetDirAlias(this.m_nativeState, arrayOfByte, arrayOfByte.length);

    checkError(str.length());

    return str;
  }

  public synchronized void openFile(BFILE paramBFILE)
    throws SQLException
  {
    byte[] arrayOfByte = null;

    checkTrue(this.lifecycle == 1, 8);
    checkTrue((paramBFILE != null) && ((arrayOfByte = paramBFILE.getLocator()) != null), 54);

    byte[][] arrayOfByte1 = new byte[1][];

    checkError(t2cBfileOpen(this.m_nativeState, arrayOfByte, arrayOfByte.length, arrayOfByte1));

    paramBFILE.setLocator(arrayOfByte1[0]);
  }

  public synchronized boolean isFileOpen(BFILE paramBFILE)
    throws SQLException
  {
    byte[] arrayOfByte = null;

    checkTrue(this.lifecycle == 1, 8);
    checkTrue((paramBFILE != null) && ((arrayOfByte = paramBFILE.getLocator()) != null), 54);

    boolean[] arrayOfBoolean = new boolean[1];

    checkError(t2cBfileIsOpen(this.m_nativeState, arrayOfByte, arrayOfByte.length, arrayOfBoolean));

    return arrayOfBoolean[0];
  }

  public synchronized boolean fileExists(BFILE paramBFILE)
    throws SQLException
  {
    byte[] arrayOfByte = null;

    checkTrue(this.lifecycle == 1, 8);
    checkTrue((paramBFILE != null) && ((arrayOfByte = paramBFILE.getLocator()) != null), 54);

    boolean[] arrayOfBoolean = new boolean[1];

    checkError(t2cBfileExists(this.m_nativeState, arrayOfByte, arrayOfByte.length, arrayOfBoolean));

    return arrayOfBoolean[0];
  }

  public synchronized void closeFile(BFILE paramBFILE)
    throws SQLException
  {
    byte[] arrayOfByte = null;

    checkTrue(this.lifecycle == 1, 8);
    checkTrue((paramBFILE != null) && ((arrayOfByte = paramBFILE.getLocator()) != null), 54);

    byte[][] arrayOfByte1 = new byte[1][];

    checkError(t2cBfileClose(this.m_nativeState, arrayOfByte, arrayOfByte.length, arrayOfByte1));

    paramBFILE.setLocator(arrayOfByte1[0]);
  }

  public synchronized void open(BFILE paramBFILE, int paramInt)
    throws SQLException
  {
    byte[] arrayOfByte = null;

    checkTrue(this.lifecycle == 1, 8);
    checkTrue((paramBFILE != null) && ((arrayOfByte = paramBFILE.shareBytes()) != null), 54);

    byte[][] arrayOfByte1 = new byte[1][];

    checkError(t2cLobOpen(this.m_nativeState, 114, arrayOfByte, arrayOfByte.length, paramInt, arrayOfByte1));

    paramBFILE.setShareBytes(arrayOfByte1[0]);
  }

  public synchronized void close(BFILE paramBFILE)
    throws SQLException
  {
    byte[] arrayOfByte = null;

    checkTrue(this.lifecycle == 1, 8);
    checkTrue((paramBFILE != null) && ((arrayOfByte = paramBFILE.shareBytes()) != null), 54);

    byte[][] arrayOfByte1 = new byte[1][];

    checkError(t2cLobClose(this.m_nativeState, 114, arrayOfByte, arrayOfByte.length, arrayOfByte1));

    paramBFILE.setShareBytes(arrayOfByte1[0]);
  }

  public synchronized boolean isOpen(BFILE paramBFILE)
    throws SQLException
  {
    byte[] arrayOfByte = null;

    checkTrue(this.lifecycle == 1, 8);
    checkTrue((paramBFILE != null) && ((arrayOfByte = paramBFILE.shareBytes()) != null), 54);

    boolean[] arrayOfBoolean = new boolean[1];

    checkError(t2cLobIsOpen(this.m_nativeState, 114, arrayOfByte, arrayOfByte.length, arrayOfBoolean));

    return arrayOfBoolean[0];
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
    checkTrue((paramBFILE != null) && (paramBFILE.shareBytes() != null), 54);

    OracleConversionInputStream localOracleConversionInputStream = new OracleConversionInputStream(this.conversion, paramBFILE.getBinaryStream(), paramInt);

    return localOracleConversionInputStream;
  }

  public Reader newConversionReader(BFILE paramBFILE, int paramInt)
    throws SQLException
  {
    checkTrue((paramBFILE != null) && (paramBFILE.shareBytes() != null), 54);

    OracleConversionReader localOracleConversionReader = new OracleConversionReader(this.conversion, paramBFILE.getBinaryStream(), paramInt);

    return localOracleConversionReader;
  }

  public synchronized long length(BLOB paramBLOB)
    throws SQLException
  {
    byte[] arrayOfByte = null;

    checkTrue(this.lifecycle == 1, 8);
    checkTrue((paramBLOB != null) && ((arrayOfByte = paramBLOB.getLocator()) != null), 54);

    return lobLength(arrayOfByte);
  }

  public synchronized long position(BLOB paramBLOB, byte[] paramArrayOfByte, long paramLong)
    throws SQLException
  {
    checkTrue(this.lifecycle == 1, 8);
    checkTrue((paramBLOB != null) && (paramBLOB.shareBytes() != null), 54);

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

  public synchronized long position(BLOB paramBLOB1, BLOB paramBLOB2, long paramLong)
    throws SQLException
  {
    checkTrue(this.lifecycle == 1, 8);
    checkTrue((paramBLOB1 != null) && (paramBLOB1.shareBytes() != null), 54);

    checkTrue((paramBLOB2 != null) && (paramBLOB2.shareBytes() != null), 54);

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
    byte[] arrayOfByte1 = null;
    int i = 0;

    checkTrue(this.lifecycle == 1, 8);
    checkTrue((paramBLOB != null) && ((arrayOfByte1 = paramBLOB.getLocator()) != null), 54);

    if ((paramInt <= 0) || (paramArrayOfByte == null)) {
      return 0;
    }
    if (paramInt > paramArrayOfByte.length) {
      paramInt = paramArrayOfByte.length;
    }
    long l = -1L;
    byte[] arrayOfByte2;
    int j;
    if (paramBLOB.isActivePrefetch())
    {
      arrayOfByte2 = paramBLOB.getPrefetchedData();
      l = paramBLOB.length();
      if ((arrayOfByte2 != null) && (arrayOfByte2 != null) && (paramLong <= arrayOfByte2.length))
      {
        j = Math.min(arrayOfByte2.length - (int)paramLong + 1, paramInt);

        System.arraycopy(arrayOfByte2, (int)paramLong - 1, paramArrayOfByte, 0, j);

        i += j;
      }
    }

    if ((i < paramInt) && ((l == -1L) || (paramLong - 1L + i < l)))
    {
      arrayOfByte2 = paramArrayOfByte;
      j = i;
      int k = ((l > 0L) && (l < paramInt) ? (int)l : paramInt) - i;

      if (i > 0)
      {
        arrayOfByte2 = new byte[k];
      }

      if (this.useNio)
      {
        int m = paramArrayOfByte.length;
        if ((this.nioBufferForLob == null) || (this.nioBufferForLob.capacity() < m))
        {
          this.nioBufferForLob = ByteBuffer.allocateDirect(m);
        }
        else this.nioBufferForLob.rewind();
      }

      i += blobRead(arrayOfByte1, paramLong + i, k, arrayOfByte2, this.useNio, this.nioBufferForLob);

      if (this.useNio)
      {
        this.nioBufferForLob.get(arrayOfByte2);
      }

      if (j > 0)
      {
        System.arraycopy(arrayOfByte2, 0, paramArrayOfByte, j, arrayOfByte2.length);
      }
    }

    return i;
  }

  public synchronized int putBytes(BLOB paramBLOB, long paramLong, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws SQLException
  {
    checkTrue(paramLong >= 0L, 68);

    int i = 0;

    if ((paramArrayOfByte == null) || (paramArrayOfByte.length == 0) || (paramInt2 <= 0)) {
      i = 0;
    }
    else {
      byte[] arrayOfByte = null;

      checkTrue(this.lifecycle == 1, 8);
      checkTrue((paramBLOB != null) && ((arrayOfByte = paramBLOB.getLocator()) != null), 54);

      byte[][] arrayOfByte1 = new byte[1][];

      paramBLOB.setActivePrefetch(false);
      paramBLOB.clearCachedData();
      i = blobWrite(arrayOfByte, paramLong, paramArrayOfByte, arrayOfByte1, paramInt1, paramInt2);

      paramBLOB.setLocator(arrayOfByte1[0]);
    }

    return i;
  }

  public synchronized int getChunkSize(BLOB paramBLOB)
    throws SQLException
  {
    byte[] arrayOfByte = null;

    checkTrue(this.lifecycle == 1, 8);
    checkTrue((paramBLOB != null) && ((arrayOfByte = paramBLOB.getLocator()) != null), 54);

    return lobGetChunkSize(arrayOfByte);
  }

  public synchronized void trim(BLOB paramBLOB, long paramLong)
    throws SQLException
  {
    byte[] arrayOfByte = null;

    checkTrue(this.lifecycle == 1, 8);
    checkTrue((paramBLOB != null) && ((arrayOfByte = paramBLOB.shareBytes()) != null), 54);

    byte[][] arrayOfByte1 = new byte[1][];

    paramBLOB.setActivePrefetch(false);
    paramBLOB.clearCachedData();
    checkError(t2cLobTrim(this.m_nativeState, 113, paramLong, arrayOfByte, arrayOfByte.length, arrayOfByte1));

    paramBLOB.setShareBytes(arrayOfByte1[0]);
  }

  public synchronized BLOB createTemporaryBlob(Connection paramConnection, boolean paramBoolean, int paramInt)
    throws SQLException
  {
    BLOB localBLOB = null;

    checkTrue(this.lifecycle == 1, 8);

    localBLOB = new BLOB((PhysicalConnection)paramConnection);

    byte[][] arrayOfByte = new byte[1][];

    checkError(t2cLobCreateTemporary(this.m_nativeState, 113, paramBoolean, paramInt, (short)0, arrayOfByte));

    localBLOB.setShareBytes(arrayOfByte[0]);

    return localBLOB;
  }

  public synchronized void freeTemporary(BLOB paramBLOB, boolean paramBoolean)
    throws SQLException
  {
    try
    {
      byte[] arrayOfByte = null;

      checkTrue(this.lifecycle == 1, 8);
      checkTrue((paramBLOB != null) && ((arrayOfByte = paramBLOB.shareBytes()) != null), 54);

      byte[][] arrayOfByte1 = new byte[1][];

      checkError(t2cLobFreeTemporary(this.m_nativeState, 113, arrayOfByte, arrayOfByte.length, arrayOfByte1));

      paramBLOB.setShareBytes(arrayOfByte1[0]);
    }
    catch (SQLException localSQLException) {
      if ((paramBoolean & localSQLException.getErrorCode() == 64201))
        LobPlsqlUtil.freeTemporaryLob(this, paramBLOB, 2004);
      else
        throw localSQLException;
    }
  }

  public synchronized boolean isTemporary(BLOB paramBLOB)
    throws SQLException
  {
    byte[] arrayOfByte = null;

    checkTrue((paramBLOB != null) && ((arrayOfByte = paramBLOB.shareBytes()) != null), 54);

    boolean[] arrayOfBoolean = new boolean[1];

    checkError(t2cLobIsTemporary(this.m_nativeState, 113, arrayOfByte, arrayOfByte.length, arrayOfBoolean));

    return arrayOfBoolean[0];
  }

  public synchronized void open(BLOB paramBLOB, int paramInt)
    throws SQLException
  {
    byte[] arrayOfByte = null;

    checkTrue(this.lifecycle == 1, 8);
    checkTrue((paramBLOB != null) && ((arrayOfByte = paramBLOB.shareBytes()) != null), 54);

    byte[][] arrayOfByte1 = new byte[1][];

    checkError(t2cLobOpen(this.m_nativeState, 113, arrayOfByte, arrayOfByte.length, paramInt, arrayOfByte1));

    paramBLOB.setShareBytes(arrayOfByte1[0]);
  }

  public synchronized void close(BLOB paramBLOB)
    throws SQLException
  {
    byte[] arrayOfByte = null;

    checkTrue(this.lifecycle == 1, 8);
    checkTrue((paramBLOB != null) && ((arrayOfByte = paramBLOB.shareBytes()) != null), 54);

    byte[][] arrayOfByte1 = new byte[1][];

    checkError(t2cLobClose(this.m_nativeState, 113, arrayOfByte, arrayOfByte.length, arrayOfByte1));

    paramBLOB.setShareBytes(arrayOfByte1[0]);
  }

  public synchronized boolean isOpen(BLOB paramBLOB)
    throws SQLException
  {
    byte[] arrayOfByte = null;

    checkTrue(this.lifecycle == 1, 8);
    checkTrue((paramBLOB != null) && ((arrayOfByte = paramBLOB.shareBytes()) != null), 54);

    boolean[] arrayOfBoolean = new boolean[1];

    checkError(t2cLobIsOpen(this.m_nativeState, 113, arrayOfByte, arrayOfByte.length, arrayOfBoolean));

    return arrayOfBoolean[0];
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
    checkTrue((paramBLOB != null) && (paramBLOB.shareBytes() != null), 54);

    OracleConversionInputStream localOracleConversionInputStream = new OracleConversionInputStream(this.conversion, paramBLOB.getBinaryStream(), paramInt);

    return localOracleConversionInputStream;
  }

  public Reader newConversionReader(BLOB paramBLOB, int paramInt)
    throws SQLException
  {
    checkTrue((paramBLOB != null) && (paramBLOB.shareBytes() != null), 54);

    OracleConversionReader localOracleConversionReader = new OracleConversionReader(this.conversion, paramBLOB.getBinaryStream(), paramInt);

    return localOracleConversionReader;
  }

  public synchronized long length(CLOB paramCLOB)
    throws SQLException
  {
    byte[] arrayOfByte = null;

    checkTrue(this.lifecycle == 1, 8);
    checkTrue((paramCLOB != null) && ((arrayOfByte = paramCLOB.getLocator()) != null), 54);

    return lobLength(arrayOfByte);
  }

  public synchronized long position(CLOB paramCLOB, String paramString, long paramLong)
    throws SQLException
  {
    if (paramString == null)
    {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    checkTrue(this.lifecycle == 1, 8);
    checkTrue((paramCLOB != null) && (paramCLOB.shareBytes() != null), 54);

    if (paramLong < 1L)
    {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68, "position()");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    char[] localObject = new char[paramString.length()];

    paramString.getChars(0, localObject.length, (char[])localObject, 0);

    long l = LobPlsqlUtil.hasPattern(paramCLOB, (char[])localObject, paramLong);

    l = l == 0L ? -1L : l;
    return l;
  }

  public synchronized long position(CLOB paramCLOB1, CLOB paramCLOB2, long paramLong)
    throws SQLException
  {
    checkTrue(this.lifecycle == 1, 8);
    checkTrue((paramCLOB1 != null) && (paramCLOB1.shareBytes() != null), 54);

    checkTrue((paramCLOB2 != null) && (paramCLOB2.shareBytes() != null), 54);

    if (paramLong < 1L)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68, "position()");
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
    byte[] arrayOfByte = null;

    checkTrue(this.lifecycle == 1, 8);
    checkTrue((paramCLOB != null) && ((arrayOfByte = paramCLOB.getLocator()) != null), 54);

    if ((paramInt <= 0) || (paramArrayOfChar == null)) {
      return 0;
    }
    if (paramInt > paramArrayOfChar.length) {
      paramInt = paramArrayOfChar.length;
    }
    int i = 0;

    long l = -1L;
    char[] arrayOfChar;
    int j;
    if (paramCLOB.isActivePrefetch())
    {
      l = paramCLOB.length();
      arrayOfChar = paramCLOB.getPrefetchedData();
      if ((arrayOfChar != null) && (paramLong <= arrayOfChar.length))
      {
        j = Math.min(arrayOfChar.length - (int)paramLong + 1, paramInt);

        System.arraycopy(arrayOfChar, (int)paramLong - 1, paramArrayOfChar, 0, j);

        i += j;
      }

    }

    if ((i < paramInt) && ((l == -1L) || (paramLong - 1L + i < l)))
    {
      arrayOfChar = paramArrayOfChar;
      j = i;
      int k = ((l > 0L) && (l < paramInt) ? (int)l : paramInt) - i;

      if (i > 0)
      {
        arrayOfChar = new char[k];
      }

      if (this.useNio)
      {
        int m = paramArrayOfChar.length * 2;
        if ((this.nioBufferForLob == null) || (this.nioBufferForLob.capacity() < m))
          this.nioBufferForLob = ByteBuffer.allocateDirect(m);
        else {
          this.nioBufferForLob.rewind();
        }
      }
      i += t2cClobRead(this.m_nativeState, arrayOfByte, arrayOfByte.length, paramLong + i, k, arrayOfChar, arrayOfChar.length, paramCLOB.isNCLOB(), this.useNio, this.nioBufferForLob);

      if (this.useNio)
      {
        ByteBuffer localByteBuffer = this.nioBufferForLob.order(ByteOrder.LITTLE_ENDIAN);
        CharBuffer localCharBuffer = localByteBuffer.asCharBuffer();
        localCharBuffer.get(arrayOfChar);
      }

      if (j > 0)
      {
        System.arraycopy(arrayOfChar, 0, paramArrayOfChar, j, arrayOfChar.length);
      }

      checkError(i);
    }

    return i;
  }

  public synchronized int putChars(CLOB paramCLOB, long paramLong, char[] paramArrayOfChar, int paramInt1, int paramInt2)
    throws SQLException
  {
    byte[] arrayOfByte = null;

    checkTrue(this.lifecycle == 1, 8);
    checkTrue(paramLong >= 0L, 68);

    checkTrue((paramCLOB != null) && ((arrayOfByte = paramCLOB.getLocator()) != null), 54);

    if (paramArrayOfChar == null) {
      return 0;
    }
    byte[][] arrayOfByte1 = new byte[1][];
    paramCLOB.setActivePrefetch(false);
    paramCLOB.clearCachedData();
    int i = clobWrite(arrayOfByte, paramLong, paramArrayOfChar, arrayOfByte1, paramCLOB.isNCLOB(), paramInt1, paramInt2);

    paramCLOB.setLocator(arrayOfByte1[0]);

    return i;
  }

  public synchronized int getChunkSize(CLOB paramCLOB)
    throws SQLException
  {
    byte[] arrayOfByte = null;

    checkTrue(this.lifecycle == 1, 8);
    checkTrue((paramCLOB != null) && ((arrayOfByte = paramCLOB.getLocator()) != null), 54);

    return lobGetChunkSize(arrayOfByte);
  }

  public synchronized void trim(CLOB paramCLOB, long paramLong)
    throws SQLException
  {
    byte[] arrayOfByte = null;

    checkTrue(this.lifecycle == 1, 8);
    checkTrue((paramCLOB != null) && ((arrayOfByte = paramCLOB.shareBytes()) != null), 54);

    byte[][] arrayOfByte1 = new byte[1][];

    paramCLOB.setActivePrefetch(false);
    paramCLOB.clearCachedData();
    checkError(t2cLobTrim(this.m_nativeState, 112, paramLong, arrayOfByte, arrayOfByte.length, arrayOfByte1));

    paramCLOB.setShareBytes(arrayOfByte1[0]);
  }

  public synchronized CLOB createTemporaryClob(Connection paramConnection, boolean paramBoolean, int paramInt, short paramShort)
    throws SQLException
  {
    CLOB localObject = null;

    checkTrue(this.lifecycle == 1, 8);

    if (paramShort == 1) {
      localObject = new CLOB((PhysicalConnection)paramConnection);
    }
    else {
      localObject = new NCLOB((PhysicalConnection)paramConnection);
    }

    byte[][] arrayOfByte = new byte[1][];

    checkError(t2cLobCreateTemporary(this.m_nativeState, 112, paramBoolean, paramInt, paramShort, arrayOfByte));

    ((CLOB)localObject).setShareBytes(arrayOfByte[0]);

    return localObject;
  }

  public synchronized void freeTemporary(CLOB paramCLOB, boolean paramBoolean)
    throws SQLException
  {
    try
    {
      byte[] arrayOfByte = null;

      checkTrue(this.lifecycle == 1, 8);
      checkTrue((paramCLOB != null) && ((arrayOfByte = paramCLOB.shareBytes()) != null), 54);

      byte[][] arrayOfByte1 = new byte[1][];

      checkError(t2cLobFreeTemporary(this.m_nativeState, 112, arrayOfByte, arrayOfByte.length, arrayOfByte1));

      paramCLOB.setShareBytes(arrayOfByte1[0]);
    }
    catch (SQLException localSQLException) {
      if ((paramBoolean & localSQLException.getErrorCode() == 64201))
        LobPlsqlUtil.freeTemporaryLob(this, paramCLOB, 2005);
      else
        throw localSQLException;
    }
  }

  public synchronized boolean isTemporary(CLOB paramCLOB)
    throws SQLException
  {
    byte[] arrayOfByte = null;

    checkTrue((paramCLOB != null) && ((arrayOfByte = paramCLOB.shareBytes()) != null), 54);

    boolean[] arrayOfBoolean = new boolean[1];

    checkError(t2cLobIsTemporary(this.m_nativeState, 112, arrayOfByte, arrayOfByte.length, arrayOfBoolean));

    return arrayOfBoolean[0];
  }

  public synchronized void open(CLOB paramCLOB, int paramInt)
    throws SQLException
  {
    byte[] arrayOfByte = null;

    checkTrue(this.lifecycle == 1, 8);
    checkTrue((paramCLOB != null) && ((arrayOfByte = paramCLOB.shareBytes()) != null), 54);

    byte[][] arrayOfByte1 = new byte[1][];

    checkError(t2cLobOpen(this.m_nativeState, 112, arrayOfByte, arrayOfByte.length, paramInt, arrayOfByte1));

    paramCLOB.setShareBytes(arrayOfByte1[0]);
  }

  public synchronized void close(CLOB paramCLOB)
    throws SQLException
  {
    byte[] arrayOfByte = null;

    checkTrue(this.lifecycle == 1, 8);
    checkTrue((paramCLOB != null) && ((arrayOfByte = paramCLOB.shareBytes()) != null), 54);

    byte[][] arrayOfByte1 = new byte[1][];

    checkError(t2cLobClose(this.m_nativeState, 112, arrayOfByte, arrayOfByte.length, arrayOfByte1));

    paramCLOB.setShareBytes(arrayOfByte1[0]);
  }

  public synchronized boolean isOpen(CLOB paramCLOB)
    throws SQLException
  {
    byte[] arrayOfByte = null;

    checkTrue(this.lifecycle == 1, 8);
    checkTrue((paramCLOB != null) && ((arrayOfByte = paramCLOB.shareBytes()) != null), 54);

    boolean[] arrayOfBoolean = new boolean[1];

    checkError(t2cLobIsOpen(this.m_nativeState, 112, arrayOfByte, arrayOfByte.length, arrayOfBoolean));

    return arrayOfBoolean[0];
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

  public synchronized void registerTAFCallback(OracleOCIFailover paramOracleOCIFailover, Object paramObject)
    throws SQLException
  {
    this.appCallback = paramOracleOCIFailover;
    this.appCallbackObject = paramObject;

    checkError(t2cRegisterTAFCallback(this.m_nativeState));
  }

  synchronized int callTAFCallbackMethod(int paramInt1, int paramInt2)
  {
    int i = 0;

    if (this.appCallback != null) {
      i = this.appCallback.callbackFn(this, this.appCallbackObject, paramInt1, paramInt2);
    }
    return i;
  }

  public int getHeapAllocSize()
    throws SQLException
  {
    if (this.lifecycle != 1)
    {
      SQLException localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 8);
      localSQLException1.fillInStackTrace();
      throw localSQLException1;
    }

    int i = t2cGetHeapAllocSize(this.m_nativeState);

    if (i < 0)
    {
      if (i == -999)
      {
        SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 23);
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }

      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 89);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    return i;
  }

  public int getOCIEnvHeapAllocSize()
    throws SQLException
  {
    if (this.lifecycle != 1)
    {
      SQLException localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 8);
      localSQLException1.fillInStackTrace();
      throw localSQLException1;
    }

    int i = t2cGetOciEnvHeapAllocSize(this.m_nativeState);

    if (i < 0)
    {
      if (i == -999)
      {
        SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 23);
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }

      checkError(i);

      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 89);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    return i;
  }

  public static final short getClientCharSetId()
  {
    return 871;
  }

  public static short getDriverCharSetIdFromNLS_LANG(String paramString)
    throws SQLException
  {
    if (!isLibraryLoaded) {
      loadNativeLibrary(paramString);
    }

    short s = t2cGetDriverCharSetFromNlsLang();

    if (s < 0)
    {
      SQLException localSQLException = DatabaseError.createSqlException(null, 8);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    return s;
  }

  void doProxySession(int paramInt, Properties paramProperties)
    throws SQLException
  {
    byte[][] localObject1 = null;

    int i = 0;

    this.savedUser = this.userName;
    this.userName = null;
    byte[] arrayOfByte4;
    byte[] arrayOfByte3;
    byte[] arrayOfByte2;
    byte[] arrayOfByte1 = arrayOfByte2 = arrayOfByte3 = arrayOfByte4 = new byte[0];

    switch (paramInt)
    {
    case 1:
      this.userName = paramProperties.getProperty("PROXY_USER_NAME");
      String str1 = paramProperties.getProperty("PROXY_USER_PASSWORD");
      if (this.userName != null) {
        arrayOfByte1 = DBConversion.stringToDriverCharBytes(this.userName, this.m_clientCharacterSet);
      }
      if (str1 != null)
        arrayOfByte2 = DBConversion.stringToDriverCharBytes(str1, this.m_clientCharacterSet); break;
    case 2:
      String str2 = paramProperties.getProperty("PROXY_DISTINGUISHED_NAME");
      if (str2 != null)
        arrayOfByte3 = DBConversion.stringToDriverCharBytes(str2, this.m_clientCharacterSet); break;
    case 3:
      Object localObject2 = paramProperties.get("PROXY_CERTIFICATE");
      arrayOfByte4 = (byte[])localObject2;
    }

    String[] arrayOfString = (String[])paramProperties.get("PROXY_ROLES");

    if (arrayOfString != null)
    {
      i = arrayOfString.length;
      localObject1 = new byte[i][];
      for (int j = 0; j < i; j++)
      {
        if (arrayOfString[j] == null)
        {
          SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 150);
          localSQLException.fillInStackTrace();
          throw localSQLException;
        }

        localObject1[j] = DBConversion.stringToDriverCharBytes(arrayOfString[j], this.m_clientCharacterSet);
      }

    }

    this.sqlWarning = checkError(t2cDoProxySession(this.m_nativeState, paramInt, arrayOfByte1, arrayOfByte1.length, arrayOfByte2, arrayOfByte2.length, arrayOfByte3, arrayOfByte3.length, arrayOfByte4, arrayOfByte4.length, i, (byte[][])localObject1), this.sqlWarning);

    this.isProxy = true;
  }

  void closeProxySession()
    throws SQLException
  {
    checkError(t2cCloseProxySession(this.m_nativeState));
    this.userName = this.savedUser;
  }

  protected void doDescribeTable(AutoKeyInfo paramAutoKeyInfo)
    throws SQLException
  {
    String str = paramAutoKeyInfo.getTableName();

    byte[] arrayOfByte = DBConversion.stringToDriverCharBytes(str, this.m_clientCharacterSet);

    int i = 0;
    int j;
    do
    {
      j = t2cDescribeTable(this.m_nativeState, arrayOfByte, arrayOfByte.length, this.queryMetaData1, this.queryMetaData2, this.queryMetaData1Offset, this.queryMetaData2Offset, this.queryMetaData1Size, this.queryMetaData2Size);

      if (j == -1)
      {
        checkError(j);
      }

      if (j == T2CStatement.T2C_EXTEND_BUFFER)
      {
        i = 1;

        reallocateQueryMetaData(this.queryMetaData1Size * 2, this.queryMetaData2Size * 2);
      }
    }
    while (i != 0);

    processDescribeTableData(j, paramAutoKeyInfo);
  }

  private void processDescribeTableData(int paramInt, AutoKeyInfo paramAutoKeyInfo)
    throws SQLException
  {
    short[] arrayOfShort = this.queryMetaData1;
    byte[] arrayOfByte = this.queryMetaData2;
    int i = this.queryMetaData1Offset;
    int j = this.queryMetaData2Offset;

    paramAutoKeyInfo.allocateSpaceForDescribedData(paramInt);

    for (int i5 = 0; i5 < paramInt; i5++)
    {
      int m = arrayOfShort[(i + 0)];
      int k = arrayOfShort[(i + 6)];
      String str1 = bytes2String(arrayOfByte, j, k, this.conversion);

      int n = arrayOfShort[(i + 1)];
      int i1 = arrayOfShort[(i + 11)];
      boolean bool = arrayOfShort[(i + 2)] != 0;
      short s = arrayOfShort[(i + 5)];
      int i2 = arrayOfShort[(i + 3)];
      int i3 = arrayOfShort[(i + 4)];
      int i4 = arrayOfShort[(i + 12)];

      j += k;
      i += 13;

      String str2 = null;
      if (i4 > 0)
      {
        str2 = bytes2String(arrayOfByte, j, i4, this.conversion);

        j += i4;
      }

      paramAutoKeyInfo.fillDescribedData(i5, str1, m, i1 > 0 ? i1 : n, bool, s, i2, i3, str2);
    }
  }

  void doSetApplicationContext(String paramString1, String paramString2, String paramString3)
    throws SQLException
  {
    checkError(t2cSetApplicationContext(this.m_nativeState, paramString1, paramString2, paramString3));
  }

  void doClearAllApplicationContext(String paramString)
    throws SQLException
  {
    checkError(t2cClearAllApplicationContext(this.m_nativeState, paramString));
  }

  void doStartup(int paramInt)
    throws SQLException
  {
    checkError(t2cStartupDatabase(this.m_nativeState, paramInt));
  }

  void doShutdown(int paramInt)
    throws SQLException
  {
    checkError(t2cShutdownDatabase(this.m_nativeState, paramInt));
  }

  private static final void loadNativeLibrary(String paramString)
    throws SQLException
  {
    if ((paramString == null) || (paramString.equals("ocijdbc11")))
    {
      synchronized (T2CConnection.class)
      {
        if (!isLibraryLoaded)
        {
          AccessController.doPrivileged(new PrivilegedAction()
          {
            public Object run()
            {
              System.loadLibrary("ocijdbc11");
              int i = T2CConnection.getLibraryVersionNumber();
              if (i != T2CConnection.JDBC_OCI_LIBRARY_VERSION) {
                throw new Error("Incompatible version of libocijdbc[Jdbc:" + T2CConnection.JDBC_OCI_LIBRARY_VERSION + ", Jdbc-OCI:" + i);
              }

              return null;
            }
          });
          isLibraryLoaded = true;
        }
      }

    }
    else
    {
      synchronized (T2CConnection.class)
      {
        try
        {
          System.loadLibrary(paramString);
          isLibraryLoaded = true;
        }
        catch (SecurityException localSecurityException)
        {
          if (!isLibraryLoaded)
          {
            System.loadLibrary(paramString);
            isLibraryLoaded = true;
          }
        }
      }
    }
  }

  private final void checkTrue(boolean paramBoolean, int paramInt)
    throws SQLException
  {
    if (!paramBoolean)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), paramInt);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  boolean useLittleEndianSetCHARBinder()
    throws SQLException
  {
    return t2cPlatformIsLittleEndian(this.m_nativeState);
  }

  public void getPropertyForPooledConnection(OraclePooledConnection paramOraclePooledConnection)
    throws SQLException
  {
    super.getPropertyForPooledConnection(paramOraclePooledConnection, this.password);
  }

  static final char[] getCharArray(String paramString)
  {
    char[] arrayOfChar = null;

    if (paramString == null)
    {
      arrayOfChar = new char[0];
    }
    else
    {
      arrayOfChar = new char[paramString.length()];

      paramString.getChars(0, paramString.length(), arrayOfChar, 0);
    }

    return arrayOfChar;
  }

  static String bytes2String(byte[] paramArrayOfByte, int paramInt1, int paramInt2, DBConversion paramDBConversion)
    throws SQLException
  {
    byte[] arrayOfByte = new byte[paramInt2];
    System.arraycopy(paramArrayOfByte, paramInt1, arrayOfByte, 0, paramInt2);

    return paramDBConversion.CharBytesToString(arrayOfByte, paramInt2);
  }

  void disableNio()
  {
    this.useNio = false;
  }

  private static synchronized void doSetSessionTimeZone(String paramString)
    throws SQLException
  {
    t2cSetSessionTimeZone(paramString);
  }

  static native int getLibraryVersionNumber();

  static native short t2cGetServerSessionInfo(long paramLong, Properties paramProperties);

  static native short t2cGetDriverCharSetFromNlsLang();

  native int t2cDescribeError(long paramLong, T2CError paramT2CError, byte[] paramArrayOfByte);

  native int t2cCreateState(byte[] paramArrayOfByte1, int paramInt1, byte[] paramArrayOfByte2, int paramInt2, byte[] paramArrayOfByte3, int paramInt3, byte[] paramArrayOfByte4, int paramInt4, byte[] paramArrayOfByte5, int paramInt5, byte[] paramArrayOfByte6, int paramInt6, byte[] paramArrayOfByte7, int paramInt7, short paramShort, int paramInt8, short[] paramArrayOfShort, byte[] paramArrayOfByte8, byte[] paramArrayOfByte9, long[] paramArrayOfLong);

  native int t2cLogon(long paramLong, byte[] paramArrayOfByte1, int paramInt1, byte[] paramArrayOfByte2, int paramInt2, byte[] paramArrayOfByte3, int paramInt3, byte[] paramArrayOfByte4, int paramInt4, byte[] paramArrayOfByte5, int paramInt5, byte[] paramArrayOfByte6, int paramInt6, byte[] paramArrayOfByte7, int paramInt7, int paramInt8, short[] paramArrayOfShort, byte[] paramArrayOfByte8, byte[] paramArrayOfByte9, long[] paramArrayOfLong);

  private native int t2cLogoff(long paramLong);

  private native int t2cCancel(long paramLong);

  private native byte t2cGetAsmVolProperty(long paramLong);

  private native byte t2cGetInstanceType(long paramLong);

  private native int t2cCreateStatement(long paramLong1, long paramLong2, byte[] paramArrayOfByte, int paramInt1, OracleStatement paramOracleStatement, boolean paramBoolean, int paramInt2);

  private native int t2cSetAutoCommit(long paramLong, boolean paramBoolean);

  private native int t2cCommit(long paramLong, int paramInt);

  private native int t2cRollback(long paramLong);

  private native int t2cPingDatabase(long paramLong);

  private native byte[] t2cGetProductionVersion(long paramLong);

  private native int t2cGetVersionNumber(long paramLong);

  private native int t2cGetDefaultStreamChunkSize(long paramLong);

  native int t2cGetFormOfUse(long paramLong, OracleTypeCLOB paramOracleTypeCLOB, byte[] paramArrayOfByte, int paramInt1, int paramInt2);

  native long t2cGetTDO(long paramLong, byte[] paramArrayOfByte, int paramInt, int[] paramArrayOfInt);

  native int t2cCreateConnPool(byte[] paramArrayOfByte1, int paramInt1, byte[] paramArrayOfByte2, int paramInt2, byte[] paramArrayOfByte3, int paramInt3, short paramShort, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10);

  native int t2cConnPoolLogon(long paramLong, byte[] paramArrayOfByte1, int paramInt1, byte[] paramArrayOfByte2, int paramInt2, byte[] paramArrayOfByte3, int paramInt3, byte[] paramArrayOfByte4, int paramInt4, byte[] paramArrayOfByte5, int paramInt5, int paramInt6, int paramInt7, int paramInt8, String[] paramArrayOfString, byte[] paramArrayOfByte6, int paramInt9, byte[] paramArrayOfByte7, int paramInt10, byte[] paramArrayOfByte8, int paramInt11, byte[] paramArrayOfByte9, int paramInt12, byte[] paramArrayOfByte10, int paramInt13, short[] paramArrayOfShort, byte[] paramArrayOfByte11, byte[] paramArrayOfByte12, long[] paramArrayOfLong);

  native int t2cGetConnPoolInfo(long paramLong, Properties paramProperties);

  native int t2cSetConnPoolInfo(long paramLong, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6);

  native int t2cPasswordChange(long paramLong, byte[] paramArrayOfByte1, int paramInt1, byte[] paramArrayOfByte2, int paramInt2, byte[] paramArrayOfByte3, int paramInt3);

  protected native byte[] t2cGetConnectionId(long paramLong);

  native int t2cGetHandles(long paramLong, long[] paramArrayOfLong);

  native int t2cUseConnection(long paramLong1, long paramLong2, long paramLong3, long paramLong4, short[] paramArrayOfShort, long[] paramArrayOfLong);

  native boolean t2cPlatformIsLittleEndian(long paramLong);

  native int t2cRegisterTAFCallback(long paramLong);

  native int t2cGetHeapAllocSize(long paramLong);

  native int t2cGetOciEnvHeapAllocSize(long paramLong);

  native int t2cDoProxySession(long paramLong, int paramInt1, byte[] paramArrayOfByte1, int paramInt2, byte[] paramArrayOfByte2, int paramInt3, byte[] paramArrayOfByte3, int paramInt4, byte[] paramArrayOfByte4, int paramInt5, int paramInt6, byte[][] paramArrayOfByte);

  native int t2cCloseProxySession(long paramLong);

  static native int t2cDescribeTable(long paramLong, byte[] paramArrayOfByte1, int paramInt1, short[] paramArrayOfShort, byte[] paramArrayOfByte2, int paramInt2, int paramInt3, int paramInt4, int paramInt5);

  native int t2cSetApplicationContext(long paramLong, String paramString1, String paramString2, String paramString3);

  native int t2cClearAllApplicationContext(long paramLong, String paramString);

  native int t2cStartupDatabase(long paramLong, int paramInt);

  native int t2cShutdownDatabase(long paramLong, int paramInt);

  static native void t2cSetSessionTimeZone(String paramString);
}