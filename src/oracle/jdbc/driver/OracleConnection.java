package oracle.jdbc.driver;

import java.sql.ClientInfoStatus;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Pattern;
import oracle.jdbc.OracleConnectionWrapper;
import oracle.jdbc.internal.ClientDataSupport;

public abstract class OracleConnection extends OracleConnectionWrapper
  implements oracle.jdbc.internal.OracleConnection, ClientDataSupport
{
  static int DEFAULT_ROW_PREFETCH = 10;
  static final String svptPrefix = "ORACLE_SVPT_";
  static final int BINARYSTREAM = 0;
  static final int ASCIISTREAM = 1;
  static final int UNICODESTREAM = 2;
  static final int EOJ_NON = 0;
  static final int EOJ_B_TO_A = 1;
  static final int EOJ_B_TO_U = 2;
  static final int EOJ_A_TO_U = 3;
  static final int EOJ_8_TO_A = 4;
  static final int EOJ_8_TO_U = 5;
  static final int EOJ_U_TO_A = 6;
  static final int ASCII_CHARSET = 0;
  static final int NLS_CHARSET = 1;
  static final int CHAR_TO_ASCII = 0;
  static final int CHAR_TO_UNICODE = 1;
  static final int RAW_TO_ASCII = 2;
  static final int RAW_TO_UNICODE = 3;
  static final int UNICODE_TO_CHAR = 4;
  static final int ASCII_TO_CHAR = 5;
  static final int NONE = 6;
  static final int JAVACHAR_TO_CHAR = 7;
  static final int RAW_TO_JAVACHAR = 8;
  static final int CHAR_TO_JAVACHAR = 9;
  static final int JAVACHAR_TO_ASCII = 10;
  static final int JAVACHAR_TO_UNICODE = 11;
  static final int UNICODE_TO_ASCII = 12;
  public static final int KOLBLLENB = 0;
  public static final int KOLBLVSNB = 2;
  public static final byte KOLL1FLG = 4;
  public static final byte KOLL2FLG = 5;
  public static final byte KOLL3FLG = 6;
  public static final byte KOLL4FLG = 7;
  public static final int KOLBLCIDB = 32;
  static final byte ALLFLAGS = -1;
  public static final int KOLBLIMRLL = 86;
  public static final byte KOLBLBLOB = 1;
  public static final byte KOLBLCLOB = 2;
  public static final byte KOLBLNLOB = 4;
  public static final byte KOLBLBFIL = 8;
  public static final byte KOLBLCFIL = 16;
  public static final byte KOLBLNFIL = 32;
  public static final byte KOLBLABS = 64;
  public static final byte KOLBLPXY = -128;
  public static final byte KOLBLPKEY = 1;
  public static final byte KOLBLIMP = 2;
  public static final byte KOLBLIDX = 4;
  public static final byte KOLBLINI = 8;
  public static final byte KOLBLEMP = 16;
  public static final byte KOLBLVIEW = 32;
  public static final byte KOLBL0FRM = 64;
  public static final byte KOLBL1FRM = -128;
  public static final byte KOLBLRDO = 1;
  public static final byte KOLBLPART = 2;
  public static final byte KOLBLCPD = 4;
  public static final byte KOLBLDIL = 8;
  public static final byte KOLBLBUF = 16;
  public static final byte KOLBLBPS = 32;
  public static final byte KOLBLMOD = 64;
  public static final byte KOLBLVAR = -128;
  public static final byte KOLBLTMP = 1;
  public static final byte KOLBLCACHE = 2;
  public static final byte KOLBLOPEN = 8;
  public static final byte KOLBLRDWR = 16;
  public static final byte KOLBLCLI = 32;
  public static final byte KOLBLVLE = 64;
  public static final byte KOLBLLCL = -128;
  static final List<String> RESERVED_NAMESPACES = Arrays.asList(new String[] { "SYS" });

  static final Pattern SUPPORTED_NAMESPACE_PATTERN = Pattern.compile("CLIENTCONTEXT");

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  static boolean containsKey(Map paramMap, Object paramObject)
  {
    return paramMap.get(paramObject) != null;
  }

  public abstract Object getClientData(Object paramObject);

  public abstract Object setClientData(Object paramObject1, Object paramObject2);

  public abstract Object removeClientData(Object paramObject);

  /** @deprecated */
  public abstract void setClientIdentifier(String paramString)
    throws SQLException;

  /** @deprecated */
  public abstract void clearClientIdentifier(String paramString)
    throws SQLException;

  public boolean isValid(int paramInt)
    throws SQLException
  {
    return pingDatabase(paramInt) == 0;
  }

  public void setClientInfo(String paramString1, String paramString2)
    throws SQLClientInfoException
  {
    setClientInfoInternal(paramString1, paramString2, null);
  }

  public void setClientInfo(Properties paramProperties)
    throws SQLClientInfoException
  {
    Properties localProperties = (Properties)paramProperties.clone();
    for (String str1 : paramProperties.stringPropertyNames()) {
      String str2 = paramProperties.getProperty(str1);
      setClientInfoInternal(str1, str2, localProperties);
      localProperties.remove(str1);
    }
  }

  void setClientInfoInternal(String paramString1, String paramString2, Properties paramProperties)
    throws SQLClientInfoException
  {
    HashMap localHashMap = new HashMap();
    if (paramProperties != null) {
      for (Iterator localObject = paramProperties.stringPropertyNames().iterator(); localObject.hasNext(); ) { String str = (String)((Iterator)localObject).next();
        localHashMap.put(str, ClientInfoStatus.REASON_UNKNOWN);
      }
    }

    SQLClientInfoException sqlclientinfoexception = DatabaseError.createSQLClientInfoException(253, localHashMap, null);
    sqlclientinfoexception.fillInStackTrace();
    throw sqlclientinfoexception;
  }

  public String getClientInfo(String paramString)
    throws SQLException
  {
    if (isClosed()) {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 8);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
    return null;
  }

  public Properties getClientInfo()
    throws SQLException
  {
    if (isClosed()) {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 8);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
    return new Properties();
  }

  public boolean isWrapperFor(Class<?> paramClass)
    throws SQLException
  {
    if (paramClass.isInterface()) return paramClass.isInstance(this);

    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 177);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public <T> T unwrap(Class<T> paramClass)
    throws SQLException
  {
    if ((paramClass.isInterface()) && (paramClass.isInstance(this))) return (T)this;

    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 177);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  protected oracle.jdbc.internal.OracleConnection getConnectionDuringExceptionHandling()
  {
    return this;
  }

  public Class getClassForType(String paramString, Map<String, Class> paramMap)
  {
    Class localClass = (Class)paramMap.get(paramString);

    if (localClass == null)
    {
      ClassRef localClassRef = (ClassRef)OracleDriver.systemTypeMap.get(paramString);
      if (localClassRef != null) localClass = localClassRef.get();
    }

    return localClass;
  }
}