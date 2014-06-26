package oracle.jdbc.driver;

import java.io.IOException;
import java.io.InputStream;
import java.lang.management.ManagementFactory;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.management.InstanceAlreadyExistsException;
import javax.management.JMException;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import oracle.jdbc.OracleDatabaseMetaData;
import oracle.jdbc.internal.OracleConnection;

public class OracleDriver
  implements Driver
{
  public static final String oracle_string = "oracle";
  public static final String jdbc_string = "jdbc";
  public static final String protocol_string = "protocol";
  public static final String user_string = "user";
  public static final String password_string = "password";
  public static final String database_string = "database";
  public static final String server_string = "server";

  /** @deprecated */
  public static final String access_string = "access";

  /** @deprecated */
  public static final String protocolFullName_string = "protocolFullName";
  public static final String logon_as_internal_str = "internal_logon";
  public static final String proxy_client_name = "oracle.jdbc.proxyClientName";
  public static final String prefetch_string = "prefetch";
  public static final String row_prefetch_string = "rowPrefetch";
  public static final String default_row_prefetch_string = "defaultRowPrefetch";
  public static final String batch_string = "batch";
  public static final String execute_batch_string = "executeBatch";
  public static final String default_execute_batch_string = "defaultExecuteBatch";
  public static final String process_escapes_string = "processEscapes";
  public static final String accumulate_batch_result = "AccumulateBatchResult";
  public static final String j2ee_compliance = "oracle.jdbc.J2EE13Compliant";
  public static final String v8compatible_string = "V8Compatible";
  public static final String permit_timestamp_date_mismatch_string = "oracle.jdbc.internal.permitBindDateDefineTimestampMismatch";
  public static final String StreamChunkSize_string = "oracle.jdbc.StreamChunkSize";
  public static final String prelim_auth_string = "prelim_auth";
  public static final String SetFloatAndDoubleUseBinary_string = "SetFloatAndDoubleUseBinary";

  /** @deprecated */
  public static final String xa_trans_loose = "oracle.jdbc.XATransLoose";
  public static final String tcp_no_delay = "oracle.jdbc.TcpNoDelay";
  public static final String read_timeout = "oracle.jdbc.ReadTimeout";
  public static final String defaultnchar_string = "oracle.jdbc.defaultNChar";
  public static final String defaultncharprop_string = "defaultNChar";
  public static final String useFetchSizeWithLongColumn_prop_string = "useFetchSizeWithLongColumn";
  public static final String useFetchSizeWithLongColumn_string = "oracle.jdbc.useFetchSizeWithLongColumn";
  public static final String remarks_string = "remarks";
  public static final String report_remarks_string = "remarksReporting";
  public static final String synonyms_string = "synonyms";
  public static final String include_synonyms_string = "includeSynonyms";
  public static final String restrict_getTables_string = "restrictGetTables";
  public static final String fixed_string_string = "fixedString";
  public static final String dll_string = "oracle.jdbc.ocinativelibrary";
  public static final String nls_lang_backdoor = "oracle.jdbc.ociNlsLangBackwardCompatible";
  public static final String disable_defineColumnType_string = "disableDefineColumnType";
  public static final String convert_nchar_literals_string = "oracle.jdbc.convertNcharLiterals";
  public static final String dataSizeUnitsPropertyName = "";
  public static final String dataSizeBytes = "";
  public static final String dataSizeChars = "";
  public static final String set_new_password_string = "OCINewPassword";
  public static final String retain_v9_bind_behavior_string = "oracle.jdbc.RetainV9LongBindBehavior";
  public static final String no_caching_buffers = "oracle.jdbc.FreeMemoryOnEnterImplicitCache";
  static final int EXTENSION_TYPE_ORACLE_ERROR = -3;
  static final int EXTENSION_TYPE_GEN_ERROR = -2;
  static final int EXTENSION_TYPE_TYPE4_CLIENT = 0;
  static final int EXTENSION_TYPE_TYPE4_SERVER = 1;
  static final int EXTENSION_TYPE_TYPE2_CLIENT = 2;
  static final int EXTENSION_TYPE_TYPE2_SERVER = 3;
  private static final int NUMBER_OF_EXTENSION_TYPES = 4;
  private OracleDriverExtension[] driverExtensions = new OracleDriverExtension[4];
  private static final String DRIVER_PACKAGE_STRING = "driver";
  private static final String[] driverExtensionClassNames = { "oracle.jdbc.driver.T4CDriverExtension", "oracle.jdbc.driver.T4CDriverExtension", "oracle.jdbc.driver.T2CDriverExtension", "oracle.jdbc.driver.T2SDriverExtension" };
  private static Properties driverAccess;
  protected static Connection defaultConn = null;
  private static OracleDriver defaultDriver = null;
  public static final Map<String, ClassRef> systemTypeMap;
  private static final String DEFAULT_CONNECTION_PROPERTIES_RESOURCE_NAME = "/oracle/jdbc/defaultConnectionProperties.properties";
  protected static final Properties DEFAULT_CONNECTION_PROPERTIES;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  public static void registerMBeans()
  {
    try
    {
      MBeanServer localMBeanServer = null;
      Object localObject1;
      Object localObject3;
      try
      {
        ClassRef localClassRef = ClassRef.newInstance("oracle.as.jmx.framework.PortableMBeanFactory");
        localObject1 = localClassRef.get().getConstructor(new Class[0]);
        Object localObject2 = ((Constructor)localObject1).newInstance(new Object[0]);
        localObject3 = localClassRef.get().getMethod("getMBeanServer", new Class[0]);
        localMBeanServer = (MBeanServer)((Method)localObject3).invoke(localObject2, new Object[0]);
      }
      catch (ClassNotFoundException localClassNotFoundException)
      {
        localMBeanServer = ManagementFactory.getPlatformMBeanServer();
      }
      catch (NoSuchMethodException localNoSuchMethodException) {
        Logger.getLogger("oracle.jdbc").log(Level.WARNING, "Found Oracle Apps MBeanServer but not the getMBeanServer method.", localNoSuchMethodException);

        localMBeanServer = ManagementFactory.getPlatformMBeanServer();
      }
      catch (InstantiationException localInstantiationException) {
        Logger.getLogger("oracle.jdbc").log(Level.WARNING, "Found Oracle Apps MBeanServer but could not create an instance.", localInstantiationException);

        localMBeanServer = ManagementFactory.getPlatformMBeanServer();
      }
      catch (IllegalAccessException localIllegalAccessException) {
        Logger.getLogger("oracle.jdbc").log(Level.WARNING, "Found Oracle Apps MBeanServer but could not access the getMBeanServer method.", localIllegalAccessException);

        localMBeanServer = ManagementFactory.getPlatformMBeanServer();
      }
      catch (InvocationTargetException localInvocationTargetException) {
        Logger.getLogger("oracle.jdbc").log(Level.WARNING, "Found Oracle Apps MBeanServer but the getMBeanServer method threw an exception.", localInvocationTargetException);

        localMBeanServer = ManagementFactory.getPlatformMBeanServer();
      }
      if (localMBeanServer != null) {
        ClassLoader localClassLoader = OracleDriver.class.getClassLoader();
        localObject1 = localClassLoader == null ? "nullLoader" : localClassLoader.getClass().getName();
        int i = 0;
        while (true) {
          localObject3 = new StringBuilder().append((String)localObject1).append("@").append(Integer.toHexString((localClassLoader == null ? 0 : localClassLoader.hashCode()) + i++)).toString();

          ObjectName localObjectName = new ObjectName(new StringBuilder().append("com.oracle.jdbc:type=diagnosability,name=").append((String)localObject3).toString());
          try
          {
            localMBeanServer.registerMBean(new OracleDiagnosabilityMBean(), localObjectName);
          }
          catch (InstanceAlreadyExistsException localInstanceAlreadyExistsException)
          {
          }
        }
      }
      else {
        Logger.getLogger("oracle.jdbc").log(Level.WARNING, "Unable to find an MBeanServer so no MBears are registered.");
      }
    }
    catch (JMException localJMException)
    {
      Logger.getLogger("oracle.jdbc").log(Level.WARNING, "Error while registering Oracle JDBC Diagnosability MBean.", localJMException);
    }
    catch (Throwable localThrowable)
    {
      Logger.getLogger("oracle.jdbc").log(Level.WARNING, "Error while registering Oracle JDBC Diagnosability MBean.", localThrowable);
    }
  }

  public Connection connect(String paramString, Properties paramProperties)
    throws SQLException
  {
    if (paramString.regionMatches(0, "jdbc:default:connection", 0, 23))
    {
      String str = "jdbc:oracle:kprb";
      int j = paramString.length();

      if (j > 23)
        paramString = str.concat(paramString.substring(23, paramString.length()));
      else {
        paramString = str.concat(":");
      }
      str = null;
    }

    int i = oracleDriverExtensionTypeFromURL(paramString);

    if (i == -2) {
      return null;
    }
    if (i == -3)
    {
      SQLException localObject1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 67);
      localObject1.fillInStackTrace();
      throw localObject1;
    }

    OracleDriverExtension oracledriverextension = this.driverExtensions[i];

    if (oracledriverextension == null)
    {
      try
      {
        synchronized (this)
        {
          if (oracledriverextension == null)
          {
            oracledriverextension = (OracleDriverExtension)Class.forName(driverExtensionClassNames[i]).newInstance();

            this.driverExtensions[i] = oracledriverextension;
          }
          else
          {
            oracledriverextension = this.driverExtensions[i];
          }

        }

      }
      catch (Exception localException)
      {
        SQLException localObject3 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localException);
        localObject3.fillInStackTrace();
        throw localObject3;
      }

    }

    if (paramProperties == null) {
      paramProperties = new Properties();
    }

    Enumeration localEnumeration = DriverManager.getDrivers();

    while (localEnumeration.hasMoreElements())
    {
      Driver localObject3 = (Driver)localEnumeration.nextElement();

      if ((localObject3 instanceof OracleDriver))
      {
        break;
      }
    }
    while (localEnumeration.hasMoreElements())
    {
      Driver localObject3 = (Driver)localEnumeration.nextElement();

      if ((localObject3 instanceof OracleDriver)) {
        DriverManager.deregisterDriver((Driver)localObject3);
      }

    }

    PhysicalConnection connection = (PhysicalConnection)oracledriverextension.getConnection(paramString, paramProperties);

    connection.protocolId = i;

    return connection;
  }

  public Connection defaultConnection()
    throws SQLException
  {
    if ((defaultConn == null) || (defaultConn.isClosed()))
    {
      synchronized (OracleDriver.class)
      {
        if ((defaultConn == null) || (defaultConn.isClosed()))
        {
          defaultConn = connect("jdbc:oracle:kprb:", new Properties());
        }
      }
    }

    return defaultConn;
  }

  static final int oracleDriverExtensionTypeFromURL(String paramString)
  {
    int i = paramString.indexOf(58);

    if (i == -1) {
      return -2;
    }
    if (!paramString.regionMatches(true, 0, "jdbc", 0, i)) {
      return -2;
    }
    i++;

    int j = paramString.indexOf(58, i);

    if (j == -1) {
      return -2;
    }
    if (!paramString.regionMatches(true, i, "oracle", 0, j - i))
    {
      return -2;
    }
    j++;

    int k = paramString.indexOf(58, j);

    String str = null;

    if (k == -1) {
      return -3;
    }
    str = paramString.substring(j, k);

    if (str.equals("thin")) {
      return 0;
    }
    if ((str.equals("oci8")) || (str.equals("oci"))) {
      return 2;
    }

    return -3;
  }

  public boolean acceptsURL(String paramString)
  {
    if (paramString.startsWith("jdbc:oracle:"))
    {
      return oracleDriverExtensionTypeFromURL(paramString) > -2;
    }

    return false;
  }

  public DriverPropertyInfo[] getPropertyInfo(String paramString, Properties paramProperties)
    throws SQLException
  {
    Class localClass = null;
    try
    {
      localClass = ClassRef.newInstance("oracle.jdbc.OracleConnection").get();
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
    }
    int i = 0;
    String[] localObject1 = new String[150];
    String[] localObject2 = new String[150];

    Field[] arrayOfField = localClass.getFields();
    for (int j = 0; j < arrayOfField.length; j++)
    {
      if ((arrayOfField[j].getName().startsWith("CONNECTION_PROPERTY_")) && (!arrayOfField[j].getName().endsWith("_DEFAULT")) && (!arrayOfField[j].getName().endsWith("_ACCESSMODE")))
      {
        try
        {
          String str1 = (String)arrayOfField[j].get(null);
          Field localField = localClass.getField(new StringBuilder().append(arrayOfField[j].getName()).append("_DEFAULT").toString());
          String str2 = (String)localField.get(null);
          if (i == localObject1.length)
          {
            String[] arrayOfString1 = new String[localObject1.length * 2];
            String[] arrayOfString2 = new String[localObject1.length * 2];
            System.arraycopy(localObject1, 0, arrayOfString1, 0, localObject1.length);
            System.arraycopy(localObject2, 0, arrayOfString2, 0, localObject1.length);
            localObject1 = arrayOfString1;
            localObject2 = arrayOfString2;
          }
          localObject1[i] = str1;
          localObject2[i] = str2;
          i++;
        }
        catch (IllegalAccessException localIllegalAccessException) {
        }
        catch (NoSuchFieldException localNoSuchFieldException) {
        }
      }
    }
    DriverPropertyInfo[] arrayOfDriverPropertyInfo = new DriverPropertyInfo[i];
    for (int k = 0; k < i; k++)
      arrayOfDriverPropertyInfo[k] = new DriverPropertyInfo(localObject1[k], localObject2[k]);
    return arrayOfDriverPropertyInfo;
  }

  public int getMajorVersion()
  {
    return OracleDatabaseMetaData.getDriverMajorVersionInfo();
  }

  public int getMinorVersion()
  {
    return OracleDatabaseMetaData.getDriverMinorVersionInfo();
  }

  public boolean jdbcCompliant()
  {
    return true;
  }

  public String processSqlEscapes(String paramString)
    throws SQLException
  {
    OracleSql localOracleSql = new OracleSql(null);

    localOracleSql.initialize(paramString);

    return localOracleSql.parse(paramString);
  }

  public static String getCompileTime()
  {
    return "Sat_Aug_14_12:18:34_PDT_2010";
  }

  public static String getSystemPropertyFastConnectionFailover(String paramString)
  {
    return PhysicalConnection.getSystemPropertyFastConnectionFailover(paramString);
  }

  protected OracleConnection getConnectionDuringExceptionHandling()
  {
    return null;
  }

  static
  {
    Timestamp localTimestamp = Timestamp.valueOf("2000-01-01 00:00:00.0");
    try
    {
      if (defaultDriver == null)
      {
        defaultDriver = new oracle.jdbc.OracleDriver();
        DriverManager.registerDriver(defaultDriver);
      }

      AccessController.doPrivileged(new PrivilegedAction()
      {
        public Object run()
        {
          OracleDriver.registerMBeans();
          return null;
        }
      });

    }
    catch (SQLException localSQLException)
    {
      Logger.getLogger("oracle.jdbc.driver").log(Level.SEVERE, "SQLException in static block.", localSQLException);
    }
    catch (RuntimeException localRuntimeException)
    {
      Logger.getLogger("oracle.jdbc.driver").log(Level.SEVERE, "RuntimeException in static block.", localRuntimeException);
    }

    try
    {
      ClassRef localClassRef = ClassRef.newInstance("oracle.security.pki.OraclePKIProvider");
      Object localObject = localClassRef.get().newInstance();
    }
    catch (Throwable localThrowable)
    {
    }

    systemTypeMap = new Hashtable(3);
    try
    {
      systemTypeMap.put("SYS.XMLTYPE", ClassRef.newInstance("oracle.xdb.XMLTypeFactory"));
    }
    catch (ClassNotFoundException classnotfoundexception)
    {
    }

    try
    {
      systemTypeMap.put("SYS.ANYDATA", ClassRef.newInstance("oracle.sql.AnyDataFactory"));
      systemTypeMap.put("SYS.ANYTYPE", ClassRef.newInstance("oracle.sql.TypeDescriptorFactory"));
    }
    catch (ClassNotFoundException classnotfoundexception)
    {
    }

    DEFAULT_CONNECTION_PROPERTIES = new Properties();
    try
    {
      InputStream localInputStream = PhysicalConnection.class.getResourceAsStream("/oracle/jdbc/defaultConnectionProperties.properties");
      if (localInputStream != null) DEFAULT_CONNECTION_PROPERTIES.load(localInputStream);
    }
    catch (IOException ioexception)
    {
    }
  }
}