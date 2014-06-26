package oracle.jdbc.driver;

import java.io.IOException;
import java.net.InetAddress;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Properties;
import java.util.TimeZone;
import oracle.jdbc.internal.OracleConnection;
import oracle.jdbc.util.RepConversion;
import oracle.net.ano.AuthenticationService;
import oracle.net.ns.Communication;
import oracle.net.ns.SessionAtts;
import oracle.net.nt.TcpsNTAdapter;
import oracle.security.o3logon.O3LoginClientHelper;
import oracle.security.o5logon.O5LoginClientHelper;
import oracle.sql.ZONEIDMAP;
import oracle.sql.converter.CharacterSetMetaData;

final class T4CTTIoauthenticate extends T4CTTIfun
{
  byte[] terminal;
  byte[] machine;
  byte[] sysUserName;
  byte[] processID;
  byte[] programName;
  byte[] encryptedSK;
  byte[] internalName;
  byte[] externalName;
  byte[] alterSession;
  byte[] aclValue;
  byte[] clientname;
  byte[] editionName = null;
  byte[] driverName;
  String ressourceManagerId;
  boolean bUseO5Logon;
  int verifierType;
  static final int ZTVT_ORCL_7 = 2361;
  static final int ZTVT_SSH1 = 6949;
  static final int ZTVT_NTV = 7809;
  byte[] salt;
  byte[] encryptedKB;
  boolean isSessionTZ = true;
  static final int SERVER_VERSION_81 = 8100;
  static final int KPZ_LOGON = 1;
  static final int KPZ_CPW = 2;
  static final int KPZ_SRVAUTH = 4;
  static final int KPZ_ENCRYPTED_PASSWD = 256;
  static final int KPZ_LOGON_MIGRATE = 16;
  static final int KPZ_LOGON_SYSDBA = 32;
  static final int KPZ_LOGON_SYSOPER = 64;
  static final int KPZ_LOGON_SYSASM = 4194304;
  static final int KPZ_LOGON_PRELIMAUTH = 128;
  static final int KPZ_PASSWD_ENCRYPTED = 256;
  static final int KPZ_LOGON_DBCONC = 512;
  static final int KPZ_PROXY_AUTH = 1024;
  static final int KPZ_SESSION_CACHE = 2048;
  static final int KPZ_PASSWD_IS_VFR = 4096;
  static final int KPZ_SESSION_QCACHE = 8388608;
  static final String AUTH_TERMINAL = "AUTH_TERMINAL";
  static final String AUTH_PROGRAM_NM = "AUTH_PROGRAM_NM";
  static final String AUTH_MACHINE = "AUTH_MACHINE";
  static final String AUTH_PID = "AUTH_PID";
  static final String AUTH_SID = "AUTH_SID";
  static final String AUTH_SESSKEY = "AUTH_SESSKEY";
  static final String AUTH_VFR_DATA = "AUTH_VFR_DATA";
  static final String AUTH_PASSWORD = "AUTH_PASSWORD";
  static final String AUTH_INTERNALNAME = "AUTH_INTERNALNAME_";
  static final String AUTH_EXTERNALNAME = "AUTH_EXTERNALNAME_";
  static final String AUTH_ACL = "AUTH_ACL";
  static final String AUTH_ALTER_SESSION = "AUTH_ALTER_SESSION";
  static final String AUTH_INITIAL_CLIENT_ROLE = "INITIAL_CLIENT_ROLE";
  static final String AUTH_VERSION_SQL = "AUTH_VERSION_SQL";
  static final String AUTH_VERSION_NO = "AUTH_VERSION_NO";
  static final String AUTH_XACTION_TRAITS = "AUTH_XACTION_TRAITS";
  static final String AUTH_VERSION_STATUS = "AUTH_VERSION_STATUS";
  static final String AUTH_SERIAL_NUM = "AUTH_SERIAL_NUM";
  static final String AUTH_SESSION_ID = "AUTH_SESSION_ID";
  static final String AUTH_CLIENT_CERTIFICATE = "AUTH_CLIENT_CERTIFICATE";
  static final String AUTH_PROXY_CLIENT_NAME = "PROXY_CLIENT_NAME";
  static final String AUTH_CLIENT_DN = "AUTH_CLIENT_DISTINGUISHED_NAME";
  static final String AUTH_INSTANCENAME = "AUTH_INSTANCENAME";
  static final String AUTH_DBNAME = "AUTH_DBNAME";
  static final String AUTH_INSTANCE_NO = "AUTH_INSTANCE_NO";
  static final String AUTH_SC_SERVER_HOST = "AUTH_SC_SERVER_HOST";
  static final String AUTH_SC_INSTANCE_NAME = "AUTH_SC_INSTANCE_NAME";
  static final String AUTH_SC_INSTANCE_ID = "AUTH_SC_INSTANCE_ID";
  static final String AUTH_SC_INSTANCE_START_TIME = "AUTH_SC_INSTANCE_START_TIME";
  static final String AUTH_SC_DBUNIQUE_NAME = "AUTH_SC_DBUNIQUE_NAME";
  static final String AUTH_SC_SERVICE_NAME = "AUTH_SC_SERVICE_NAME";
  static final String AUTH_SC_SVC_FLAGS = "AUTH_SC_SVC_FLAGS";
  static final String AUTH_SESSION_CLIENT_CSET = "SESSION_CLIENT_CHARSET";
  static final String AUTH_SESSION_CLIENT_LTYPE = "SESSION_CLIENT_LIB_TYPE";
  static final String AUTH_SESSION_CLIENT_DRVNM = "SESSION_CLIENT_DRIVER_NAME";
  static final String AUTH_SESSION_CLIENT_VSN = "SESSION_CLIENT_VERSION";
  static final String AUTH_NLS_LXLAN = "AUTH_NLS_LXLAN";
  static final String AUTH_NLS_LXCTERRITORY = "AUTH_NLS_LXCTERRITORY";
  static final String AUTH_NLS_LXCCURRENCY = "AUTH_NLS_LXCCURRENCY";
  static final String AUTH_NLS_LXCISOCURR = "AUTH_NLS_LXCISOCURR";
  static final String AUTH_NLS_LXCNUMERICS = "AUTH_NLS_LXCNUMERICS";
  static final String AUTH_NLS_LXCDATEFM = "AUTH_NLS_LXCDATEFM";
  static final String AUTH_NLS_LXCDATELANG = "AUTH_NLS_LXCDATELANG";
  static final String AUTH_NLS_LXCSORT = "AUTH_NLS_LXCSORT";
  static final String AUTH_NLS_LXCCALENDAR = "AUTH_NLS_LXCCALENDAR";
  static final String AUTH_NLS_LXCUNIONCUR = "AUTH_NLS_LXCUNIONCUR";
  static final String AUTH_NLS_LXCTIMEFM = "AUTH_NLS_LXCTIMEFM";
  static final String AUTH_NLS_LXCSTMPFM = "AUTH_NLS_LXCSTMPFM";
  static final String AUTH_NLS_LXCTTZNFM = "AUTH_NLS_LXCTTZNFM";
  static final String AUTH_NLS_LXCSTZNFM = "AUTH_NLS_LXCSTZNFM";
  static final String DRIVER_NAME_DEFAULT = "jdbcthin";
  static final int KPU_LIB_UNKN = 0;
  static final int KPU_LIB_DEF = 1;
  static final int KPU_LIB_EI = 2;
  static final int KPU_LIB_XE = 3;
  static final int KPU_LIB_ICUS = 4;
  static final int KPU_LIB_OCI = 5;
  static final int KPU_LIB_THIN = 10;
  static final String AUTH_ORA_EDITION = "AUTH_ORA_EDITION";
  static final String AUTH_COPYRIGHT = "AUTH_COPYRIGHT";
  static final String COPYRIGHT_STR = "\"Oracle\nEverybody follows\nSpeedy bits exchange\nStars await to glow\"\nThe preceding key is copyrighted by Oracle Corporation.\nDuplication of this key is not allowed without permission\nfrom Oracle Corporation. Copyright 2003 Oracle Corporation.";
  static final String SESSION_TIME_ZONE = "SESSION_TIME_ZONE";
  static final String SESSION_NLS_LXCCHARSET = "SESSION_NLS_LXCCHARSET";
  static final String SESSION_NLS_LXCNLSLENSEM = "SESSION_NLS_LXCNLSLENSEM";
  static final String SESSION_NLS_LXCNCHAREXCP = "SESSION_NLS_LXCNCHAREXCP";
  static final String SESSION_NLS_LXCNCHARIMP = "SESSION_NLS_LXCNCHARIMP";
  String sessionTimeZone = null;

  private T4CKvaldfList keyValList = null;
  private byte[] user = null;
  private long logonMode;
  private byte[][] outKeys = (byte[][])null;
  private byte[][] outValues = (byte[][])null;
  private int[] outFlags = new int[0];
  private int outNbPairs = 0;

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  T4CTTIoauthenticate(T4CConnection paramT4CConnection, String paramString)
    throws SQLException
  {
    super(paramT4CConnection, (byte)3);

    this.ressourceManagerId = paramString;

    setSessionFields(paramT4CConnection);

    this.isSessionTZ = true;

    this.bUseO5Logon = false;
  }

  void marshal()
    throws IOException
  {
    if ((this.user != null) && (this.user.length > 0))
    {
      this.meg.marshalPTR();
      this.meg.marshalSB4(this.user.length);
    }
    else
    {
      this.meg.marshalNULLPTR();
      this.meg.marshalSB4(0);
    }
    this.meg.marshalUB4(this.logonMode);
    this.meg.marshalPTR();

    this.meg.marshalUB4(this.keyValList.size());
    this.meg.marshalPTR();
    this.meg.marshalPTR();

    if ((this.user != null) && (this.user.length > 0))
      this.meg.marshalCHR(this.user);
    this.meg.marshalKEYVAL(this.keyValList.getKeys(), this.keyValList.getValues(), this.keyValList.getFlags(), this.keyValList.size());
  }

  private void doOAUTH(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, long paramLong, String paramString, boolean paramBoolean, byte[] paramArrayOfByte3, byte[] paramArrayOfByte4, byte[][] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException, SQLException
  {
    setFunCode((short)115);

    this.user = paramArrayOfByte1;

    this.logonMode = (paramLong | 1L);

    if (paramBoolean) {
      this.logonMode |= 1024L;
    }
    if ((paramArrayOfByte1 != null) && (paramArrayOfByte1.length != 0) && (paramArrayOfByte2 != null) && (paramString != "RADIUS"))
    {
      this.logonMode |= 256L;
    }
    this.keyValList = new T4CKvaldfList(this.meg.conv);

    if (paramArrayOfByte2 != null) {
      this.keyValList.add("AUTH_PASSWORD", paramArrayOfByte2);
    }
    if (paramArrayOfByte != null) {
      for (int i = 0; i < paramArrayOfByte.length; i++)
        this.keyValList.add("INITIAL_CLIENT_ROLE", paramArrayOfByte[i]);
    }
    if (paramArrayOfByte3 != null) {
      this.keyValList.add("AUTH_CLIENT_DISTINGUISHED_NAME", paramArrayOfByte3);
    }
    if (paramArrayOfByte4 != null) {
      this.keyValList.add("AUTH_CLIENT_CERTIFICATE", paramArrayOfByte4);
    }
    this.keyValList.add("AUTH_TERMINAL", this.terminal);

    if ((this.bUseO5Logon) && (this.encryptedKB != null)) {
      this.keyValList.add("AUTH_SESSKEY", this.encryptedKB, (byte)1);
    }
    if (this.programName != null) {
      this.keyValList.add("AUTH_PROGRAM_NM", this.programName);
    }
    if (this.clientname != null) {
      this.keyValList.add("PROXY_CLIENT_NAME", this.clientname);
    }
    this.keyValList.add("AUTH_MACHINE", this.machine);
    this.keyValList.add("AUTH_PID", this.processID);

    if (!this.ressourceManagerId.equals("0000"))
    {
      byte[] arrayOfByte = this.meg.conv.StringToCharBytes("AUTH_INTERNALNAME_");

      arrayOfByte[(arrayOfByte.length - 1)] = 0;
      this.keyValList.add(arrayOfByte, this.internalName);

      arrayOfByte = this.meg.conv.StringToCharBytes("AUTH_EXTERNALNAME_");
      arrayOfByte[(arrayOfByte.length - 1)] = 0;
      this.keyValList.add(arrayOfByte, this.externalName);
    }

    this.keyValList.add("AUTH_ACL", this.aclValue);

    this.keyValList.add("AUTH_ALTER_SESSION", this.alterSession, (byte)1);

    if (this.editionName != null) {
      this.keyValList.add("AUTH_ORA_EDITION", this.editionName);
    }
    this.keyValList.add("SESSION_CLIENT_DRIVER_NAME", this.driverName);
    this.keyValList.add("SESSION_CLIENT_VERSION", this.meg.conv.StringToCharBytes(Integer.toString(versionStringToInt(this.connection.getMetaData().getDriverVersion()), 10)));

    if (paramInt1 != -1) {
      this.keyValList.add("AUTH_SESSION_ID", this.meg.conv.StringToCharBytes(Integer.toString(paramInt1)));
    }
    if (paramInt2 != -1) {
      this.keyValList.add("AUTH_SERIAL_NUM", this.meg.conv.StringToCharBytes(Integer.toString(paramInt2)));
    }
    this.keyValList.add("AUTH_COPYRIGHT", this.meg.conv.StringToCharBytes("\"Oracle\nEverybody follows\nSpeedy bits exchange\nStars await to glow\"\nThe preceding key is copyrighted by Oracle Corporation.\nDuplication of this key is not allowed without permission\nfrom Oracle Corporation. Copyright 2003 Oracle Corporation."));

    this.outNbPairs = 0;
    this.outKeys = ((byte[][])null);
    this.outValues = ((byte[][])null);
    this.outFlags = new int[0];
    doRPC();
  }

  void doOSESSKEY(String paramString, long paramLong)
    throws IOException, SQLException
  {
    setFunCode((short)118);
    this.user = this.meg.conv.StringToCharBytes(paramString);
    this.logonMode = (paramLong | 1L);

    this.keyValList = new T4CKvaldfList(this.meg.conv);
    this.keyValList.add("AUTH_TERMINAL", this.terminal);
    if (this.programName != null)
      this.keyValList.add("AUTH_PROGRAM_NM", this.programName);
    this.keyValList.add("AUTH_MACHINE", this.machine);
    this.keyValList.add("AUTH_PID", this.processID);
    this.keyValList.add("AUTH_SID", this.sysUserName);
    this.outNbPairs = 0;
    this.outKeys = ((byte[][])null);
    this.outValues = ((byte[][])null);
    this.outFlags = new int[0];
    doRPC();
  }

  void readRPA()
    throws IOException, SQLException
  {
    this.outNbPairs = this.meg.unmarshalUB2();

    this.outKeys = new byte[this.outNbPairs][];
    this.outValues = new byte[this.outNbPairs][];

    this.outFlags = this.meg.unmarshalKEYVAL(this.outKeys, this.outValues, this.outNbPairs);
  }

  void processError()
    throws SQLException
  {
    if (getFunCode() == 118)
    {
      if ((this.oer.getRetCode() != 28035) || (this.connection.net.getAuthenticationAdaptorName() != "RADIUS"))
      {
        this.oer.processError();
      }
    }
    else
      super.processError();
  }

  protected void processRPA()
    throws SQLException
  {
    Object localObject;
    if (getFunCode() == 115)
    {
      localObject = new Properties();

      for (int j = 0; j < this.outNbPairs; j++)
      {
        String str2 = this.meg.conv.CharBytesToString(this.outKeys[j], this.outKeys[j].length).trim();
        String str3 = "";
        if (this.outValues[j] != null) {
          str3 = this.meg.conv.CharBytesToString(this.outValues[j], this.outValues[j].length).trim();
        }
        ((Properties)localObject).setProperty(str2, str3);
      }

      String str1 = ((Properties)localObject).getProperty("AUTH_VERSION_NO");
      if (str1 != null)
      {
        try
        {
          int n = new Integer(str1).intValue();
        }
        catch (NumberFormatException localNumberFormatException)
        {
        }

      }

      ((Properties)localObject).setProperty("SERVER_HOST", ((Properties)localObject).getProperty("AUTH_SC_SERVER_HOST", ""));

      ((Properties)localObject).setProperty("INSTANCE_NAME", ((Properties)localObject).getProperty("AUTH_SC_INSTANCE_NAME", ""));

      ((Properties)localObject).setProperty("DATABASE_NAME", ((Properties)localObject).getProperty("AUTH_SC_DBUNIQUE_NAME", ""));

      ((Properties)localObject).setProperty("SERVICE_NAME", ((Properties)localObject).getProperty("AUTH_SC_SERVICE_NAME", ""));

      ((Properties)localObject).setProperty("SESSION_TIME_ZONE", this.sessionTimeZone);

      this.connection.sessionProperties = ((Properties)localObject);
    }
    else if (getFunCode() == 118)
    {
      if (this.connection.net.getAuthenticationAdaptorName() != "RADIUS")
      {
        if ((this.outKeys == null) || (this.outKeys.length < 1))
        {
          SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 438);
          sqlexception.fillInStackTrace();
          throw sqlexception;
        }

        int i = -1;
        for (int k = 0; k < this.outKeys.length; k++)
          if (new String(this.outKeys[k]).equals("AUTH_SESSKEY"))
          {
            i = k;
            break;
          }
        if (i == -1)
        {
          SQLException localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 438);
          localSQLException1.fillInStackTrace();
          throw localSQLException1;
        }
        this.encryptedSK = this.outValues[i];

        int m = -1;
        for (int i1 = 0; i1 < this.outKeys.length; i1++) {
          if (new String(this.outKeys[i1]).equals("AUTH_VFR_DATA"))
          {
            m = i1;
            break;
          }
        }
        if (m != -1)
        {
          this.bUseO5Logon = true;
          this.salt = this.outValues[m];
          this.verifierType = this.outFlags[m];
        }

        if (!this.bUseO5Logon)
        {
          if ((this.encryptedSK == null) || (this.encryptedSK.length != 16))
          {
            SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 438);
            localSQLException2.fillInStackTrace();
            throw localSQLException2;
          }
        }
      }
    }
  }

  void doOAUTH(String paramString1, String paramString2, long paramLong)
    throws IOException, SQLException
  {
    byte[] arrayOfByte1 = null;
    if ((paramString1 != null) && (paramString1.length() > 0)) {
      arrayOfByte1 = this.meg.conv.StringToCharBytes(paramString1);
    }
    byte[] arrayOfByte2 = null;
    byte[] arrayOfByte3 = null;
    byte[] arrayOfByte4 = null;

    String str1 = this.connection.net.getAuthenticationAdaptorName();

    if ((paramString1 != null) && (paramString1.length() != 0))
    {
      if ((str1 != "RADIUS") && (this.encryptedSK.length > 16) && (!this.bUseO5Logon))
      {
        SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 413);
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }

      if ((this.bUseO5Logon) && ((this.encryptedSK == null) || ((this.encryptedSK.length != 64) && (this.encryptedSK.length != 96))))
      {
        SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 413);
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }

      Object localObject1 = paramString1.trim();
      String str2 = null;
      if (paramString2 != null)
      {
        str2 = paramString2.trim();
      }
      paramString2 = null;

      Object localObject2 = localObject1;
      String str3 = str2;

      if ((((String)localObject1).startsWith("\"")) || (((String)localObject1).endsWith("\""))) {
        localObject2 = removeQuotes((String)localObject1);
      }
      if ((str2 != null) && ((str2.startsWith("\"")) || (str2.endsWith("\""))))
      {
        str3 = removeQuotes(str2);
      }

      if (str3 != null)
        arrayOfByte2 = this.meg.conv.StringToCharBytes(str3);
      Object localObject3;
      if (str1 != "RADIUS")
      {
        if (arrayOfByte2 == null)
        {
          arrayOfByte4 = null;
        }
        else if (this.bUseO5Logon)
        {
          int k;
          switch (this.verifierType)
          {
          case 2361:
            this.encryptedKB = new byte[64];
            for (k = 0; k < 64; k++) this.encryptedKB[k] = 1;
            break;
          case 6949:
            this.encryptedKB = new byte[96];
            for (k = 0; k < 96; k++) this.encryptedKB[k] = 1;
            break;
          default:
            SQLException localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 451);
            localSQLException1.fillInStackTrace();
            throw localSQLException1;
          }

          arrayOfByte4 = new byte[256];
          for (int m = 0; m < 256; m++) arrayOfByte4[m] = 0;

          if (!O5LoginClientHelper.generateOAuthResponse(this.verifierType, this.salt, (String)localObject2, str3, arrayOfByte2, this.encryptedSK, this.encryptedKB, arrayOfByte4, this.meg.conv.isServerCSMultiByte))
          {
            SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 452);
            sqlexception.fillInStackTrace();
            throw sqlexception;
          }

        }
        else
        {
          localObject3 = new O3LoginClientHelper(this.meg.conv.isServerCSMultiByte);

          byte[] arrayOfByte5 = ((O3LoginClientHelper)localObject3).getSessionKey((String)localObject2, str3, this.encryptedSK);
          byte i;
          if (arrayOfByte2.length % 8 > 0)
            i = (byte)(8 - arrayOfByte2.length % 8);
          else {
            i = 0;
          }
          arrayOfByte3 = new byte[arrayOfByte2.length + i];

          System.arraycopy(arrayOfByte2, 0, arrayOfByte3, 0, arrayOfByte2.length);

          byte[] arrayOfByte6 = ((O3LoginClientHelper)localObject3).getEPasswd(arrayOfByte5, arrayOfByte3);

          arrayOfByte4 = new byte[2 * arrayOfByte3.length + 1];

          if (arrayOfByte4.length < 2 * arrayOfByte6.length)
          {
            SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 413);
            localSQLException2.fillInStackTrace();
            throw localSQLException2;
          }

          RepConversion.bArray2Nibbles(arrayOfByte6, arrayOfByte4);

          arrayOfByte4[(arrayOfByte4.length - 1)] = RepConversion.nibbleToHex(i);
        }

      }
      else if (arrayOfByte2 != null)
      {
        if ((this.connection.net.getSessionAttributes().getNTAdapter() instanceof TcpsNTAdapter))
        {
          arrayOfByte4 = arrayOfByte2;
        }
        else
        {
          int j;
          if ((arrayOfByte2.length + 1) % 8 > 0)
            j = (byte)(8 - (arrayOfByte2.length + 1) % 8);
          else {
            j = 0;
          }
          arrayOfByte3 = new byte[arrayOfByte2.length + 1 + j];

          System.arraycopy(arrayOfByte2, 0, arrayOfByte3, 0, arrayOfByte2.length);
          byte[] bytea = AuthenticationService.obfuscatePasswordForRadius(arrayOfByte3);

          arrayOfByte4 = new byte[bytea.length * 2];

          for (int i2 = 0; i2 < bytea.length; i2++)
          {
            int n = (byte)((bytea[i2] & 0xF0) >> 4);
            int i1 = (byte)(bytea[i2] & 0xF);
            arrayOfByte4[(i2 * 2)] = ((byte)(n < 10 ? n + 48 : n - 10 + 97));

            arrayOfByte4[(i2 * 2 + 1)] = ((byte)(i1 < 10 ? i1 + 48 : i1 - 10 + 97));
          }
        }

      }

    }

    doOAUTH(arrayOfByte1, arrayOfByte4, paramLong, str1, false, null, null, (byte[][])null, -1, -1);
  }

  void doOAUTH(int paramInt1, Properties paramProperties, int paramInt2, int paramInt3)
    throws IOException, SQLException
  {
    byte[] arrayOfByte1 = null;
    byte[] arrayOfByte2 = null;
    String[] arrayOfString = null;
    byte[][] localObject1 = null;
    byte[] arrayOfByte3 = null;
    Object localObject2;
    String str;
    if (paramInt1 == 1)
    {
      localObject2 = paramProperties.getProperty("PROXY_USER_NAME");
      str = paramProperties.getProperty("PROXY_USER_PASSWORD");
      if (str != null)
        localObject2 = new StringBuilder().append((String)localObject2).append("/").append(str).toString();
      arrayOfByte3 = this.meg.conv.StringToCharBytes((String)localObject2);
    }
    else if (paramInt1 == 2)
    {
      localObject2 = paramProperties.getProperty("PROXY_DISTINGUISHED_NAME");

      arrayOfByte1 = this.meg.conv.StringToCharBytes((String)localObject2);
    }
    else
    {
      try
      {
        arrayOfByte2 = (byte[])paramProperties.get("PROXY_CERTIFICATE");

        localObject2 = new StringBuffer();

        for (int k = 0; k < arrayOfByte2.length; k++)
        {
          str = Integer.toHexString(0xFF & arrayOfByte2[k]);
          int j = str.length();

          if (j == 0) {
            ((StringBuffer)localObject2).append("00");
          } else if (j == 1)
          {
            ((StringBuffer)localObject2).append('0');
            ((StringBuffer)localObject2).append(str);
          }
          else {
            ((StringBuffer)localObject2).append(str);
          }
        }
        arrayOfByte2 = ((StringBuffer)localObject2).toString().getBytes();
      }
      catch (Exception localException1)
      {
      }
    }
    try
    {
      arrayOfString = (String[])paramProperties.get("PROXY_ROLES");
    }
    catch (Exception localException2) {
    }
    if (arrayOfString != null)
    {
      localObject1 = new byte[arrayOfString.length][];

      for (int i = 0; i < arrayOfString.length; i++) {
        localObject1[i] = this.meg.conv.StringToCharBytes(arrayOfString[i]);
      }
    }
    doOAUTH(arrayOfByte3, null, 0L, null, true, arrayOfByte1, arrayOfByte2, (byte[][])localObject1, paramInt2, paramInt3);
  }

  private void setSessionFields(T4CConnection paramT4CConnection)
    throws SQLException
  {
    String str1 = this.connection.thinVsessionTerminal;
    String str2 = this.connection.thinVsessionMachine;
    String str3 = this.connection.thinVsessionOsuser;
    String str4 = this.connection.thinVsessionProgram;
    String str5 = this.connection.thinVsessionProcess;
    String str6 = this.connection.thinVsessionIname;
    String str7 = this.connection.thinVsessionEname;
    String str8 = this.connection.proxyClientName;
    String str9 = this.connection.driverNameAttribute;
    String str10 = this.connection.editionName;

    if (str2 == null)
    {
      try
      {
        str2 = InetAddress.getLocalHost().getHostName();
      }
      catch (Exception localException)
      {
        str2 = "jdbcclient";
      }
    }

    if (str7 == null) {
      str7 = new StringBuilder().append("jdbc_").append(this.ressourceManagerId).toString();
    }
    if (str9 == null) {
      str9 = "jdbcthin";
    }

    this.terminal = this.meg.conv.StringToCharBytes(str1);
    this.machine = this.meg.conv.StringToCharBytes(str2);
    this.sysUserName = this.meg.conv.StringToCharBytes(str3);
    this.programName = this.meg.conv.StringToCharBytes(str4);
    this.processID = this.meg.conv.StringToCharBytes(str5);
    this.internalName = this.meg.conv.StringToCharBytes(str6);
    this.externalName = this.meg.conv.StringToCharBytes(str7);
    if (str8 != null)
      this.clientname = this.meg.conv.StringToCharBytes(str8);
    if (str10 != null)
      this.editionName = this.meg.conv.StringToCharBytes(str10);
    this.driverName = this.meg.conv.StringToCharBytes(str9);

    TimeZone localTimeZone = TimeZone.getDefault();

    String str11 = localTimeZone.getID();

    if ((!ZONEIDMAP.isValidRegion(str11)) || (!paramT4CConnection.timezoneAsRegion))
    {
      int i = localTimeZone.getOffset(System.currentTimeMillis());
      int j = i / 3600000;
      int k = i / 60000 % 60;

      str11 = new StringBuilder().append(j < 0 ? new StringBuilder().append("").append(j).toString() : new StringBuilder().append("+").append(j).toString()).append(k < 10 ? new StringBuilder().append(":0").append(k).toString() : new StringBuilder().append(":").append(k).toString()).toString();
    }

    this.sessionTimeZone = str11;
    paramT4CConnection.sessionTimeZone = str11;

    String str12 = CharacterSetMetaData.getNLSLanguage(Locale.getDefault());

    String str13 = CharacterSetMetaData.getNLSTerritory(Locale.getDefault());

    if ((str12 == null) || (str13 == null))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 176);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.alterSession = this.meg.conv.StringToCharBytes(new StringBuilder().append("ALTER SESSION SET ").append(this.isSessionTZ ? new StringBuilder().append("TIME_ZONE='").append(this.sessionTimeZone).append("'").toString() : "").append(" NLS_LANGUAGE='").append(str12).append("' NLS_TERRITORY='").append(str13).append("' ").toString());

    this.aclValue = this.meg.conv.StringToCharBytes("4400");
    this.alterSession[(this.alterSession.length - 1)] = 0;
  }

  String removeQuotes(String paramString)
  {
    int i = 0; int j = paramString.length() - 1;

    for (int k = 0; k < paramString.length(); k++)
    {
      if (paramString.charAt(k) != '"')
      {
        i = k;

        break;
      }
    }

    for (int k = paramString.length() - 1; k >= 0; k--)
    {
      if (paramString.charAt(k) != '"')
      {
        j = k;

        break;
      }
    }

    String str = paramString.substring(i, j + 1);

    return str;
  }

  private int versionStringToInt(String paramString)
    throws SQLException
  {
    String[] arrayOfString = paramString.split("\\.");
    int i = Integer.parseInt(arrayOfString[0].replaceAll("\\D", ""));
    int j = Integer.parseInt(arrayOfString[1].replaceAll("\\D", ""));
    int k = Integer.parseInt(arrayOfString[2].replaceAll("\\D", ""));
    int m = Integer.parseInt(arrayOfString[3].replaceAll("\\D", ""));
    int n = Integer.parseInt(arrayOfString[4].replaceAll("\\D", ""));
    int i1 = i << 24 | j << 20 | k << 12 | m << 8 | n;

    return i1;
  }

  private String versionIntToString(int paramInt)
    throws SQLException
  {
    int i = (paramInt & 0xFF000000) >> 24 & 0xFF;
    int j = (paramInt & 0xF00000) >> 20 & 0xFF;
    int k = (paramInt & 0xFF000) >> 12 & 0xFF;
    int m = (paramInt & 0xF00) >> 8 & 0xFF;
    int n = paramInt & 0xFF;
    String str = new StringBuilder().append("").append(i).append(".").append(j).append(".").append(k).append(".").append(m).append(".").append(n).toString();
    return str;
  }

  protected OracleConnection getConnectionDuringExceptionHandling()
  {
    return this.connection;
  }
}