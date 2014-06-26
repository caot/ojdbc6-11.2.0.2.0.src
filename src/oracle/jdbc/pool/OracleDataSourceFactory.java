package oracle.jdbc.pool;

import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Properties;
import java.util.StringTokenizer;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.Reference;
import javax.naming.StringRefAddr;
import javax.naming.spi.ObjectFactory;
import oracle.jdbc.driver.DatabaseError;
import oracle.jdbc.internal.OracleConnection;
import oracle.jdbc.xa.client.OracleXADataSource;

public class OracleDataSourceFactory
  implements ObjectFactory
{
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  public Object getObjectInstance(Object paramObject, Name paramName, Context paramContext, Hashtable paramHashtable)
    throws Exception
  {
    Reference localReference = (Reference)paramObject;
    Object localObject1 = null;
    String str1 = localReference.getClassName();
    Properties localProperties = new Properties();
    Object localObject2;
    String str2;
    Object localObject3;
    if ((str1.equals("oracle.jdbc.pool.OracleDataSource")) || (str1.equals("oracle.jdbc.xa.client.OracleXADataSource")))
    {
      if (str1.equals("oracle.jdbc.pool.OracleDataSource"))
        localObject1 = new OracleDataSource();
      else {
        localObject1 = new OracleXADataSource();
      }
      localObject2 = null;

      if ((localObject2 = (StringRefAddr)localReference.get("connectionCachingEnabled")) != null)
      {
        str2 = (String)((StringRefAddr)localObject2).getContent();

        if (str2.equals(String.valueOf("true"))) {
          ((OracleDataSource)localObject1).setConnectionCachingEnabled(true);
        }
      }
      if ((localObject2 = (StringRefAddr)localReference.get("connectionCacheName")) != null)
      {
        ((OracleDataSource)localObject1).setConnectionCacheName((String)((StringRefAddr)localObject2).getContent());
      }

      if ((localObject2 = (StringRefAddr)localReference.get("connectionCacheProperties")) != null)
      {
        str2 = (String)((StringRefAddr)localObject2).getContent();
        localObject3 = extractConnectionCacheProperties(str2);

        ((OracleDataSource)localObject1).setConnectionCacheProperties((Properties)localObject3);
      }

      if ((localObject2 = (StringRefAddr)localReference.get("fastConnectionFailoverEnabled")) != null)
      {
        str2 = (String)((StringRefAddr)localObject2).getContent();

        if (str2.equals(String.valueOf("true"))) {
          ((OracleDataSource)localObject1).setFastConnectionFailoverEnabled(true);
        }
      }
      if ((localObject2 = (StringRefAddr)localReference.get("onsConfigStr")) != null)
      {
        ((OracleDataSource)localObject1).setONSConfiguration((String)((StringRefAddr)localObject2).getContent());
      }
    }
    else if (str1.equals("oracle.jdbc.pool.OracleConnectionPoolDataSource")) {
      localObject1 = new OracleConnectionPoolDataSource();
    } else if (str1.equals("oracle.jdbc.pool.OracleOCIConnectionPool"))
    {
      localObject1 = new OracleOCIConnectionPool();

      localObject2 = null;
      str2 = null;
      localObject3 = null;
      String str3 = null;
      String str4 = null;
      String str5 = null;
      String str6 = null;
      StringRefAddr localStringRefAddr = null;

      Object localObject4 = null;
      String str7 = null;

      if ((localStringRefAddr = (StringRefAddr)localReference.get("connpool_min_limit")) != null)
      {
        localObject2 = (String)localStringRefAddr.getContent();
      }
      if ((localStringRefAddr = (StringRefAddr)localReference.get("connpool_max_limit")) != null)
      {
        str2 = (String)localStringRefAddr.getContent();
      }
      if ((localStringRefAddr = (StringRefAddr)localReference.get("connpool_increment")) != null)
      {
        localObject3 = (String)localStringRefAddr.getContent();
      }
      if ((localStringRefAddr = (StringRefAddr)localReference.get("connpool_active_size")) != null)
      {
        str3 = (String)localStringRefAddr.getContent();
      }
      if ((localStringRefAddr = (StringRefAddr)localReference.get("connpool_pool_size")) != null)
      {
        str4 = (String)localStringRefAddr.getContent();
      }
      if ((localStringRefAddr = (StringRefAddr)localReference.get("connpool_timeout")) != null)
      {
        str5 = (String)localStringRefAddr.getContent();
      }
      if ((localStringRefAddr = (StringRefAddr)localReference.get("connpool_nowait")) != null)
      {
        str6 = (String)localStringRefAddr.getContent();
      }
      if ((localStringRefAddr = (StringRefAddr)localReference.get("transactions_distributed")) != null)
      {
        str7 = (String)localStringRefAddr.getContent();
      }

      localProperties.put("connpool_min_limit", localObject2);
      localProperties.put("connpool_max_limit", str2);
      localProperties.put("connpool_increment", localObject3);
      localProperties.put("connpool_active_size", str3);

      localProperties.put("connpool_pool_size", str4);
      localProperties.put("connpool_timeout", str5);

      if (str6 == "true") {
        localProperties.put("connpool_nowait", str6);
      }
      if (str7 == "true") {
        localProperties.put("transactions_distributed", str7);
      }

    }
    else
    {
      return null;
    }
    if (localObject1 != null)
    {
      localObject2 = null;

      if ((localObject2 = (StringRefAddr)localReference.get("url")) != null)
      {
        ((OracleDataSource)localObject1).setURL((String)((StringRefAddr)localObject2).getContent());
      }

      if (((localObject2 = (StringRefAddr)localReference.get("userName")) != null) || ((localObject2 = (StringRefAddr)localReference.get("u")) != null) || ((localObject2 = (StringRefAddr)localReference.get("user")) != null))
      {
        ((OracleDataSource)localObject1).setUser((String)((StringRefAddr)localObject2).getContent());
      }

      if (((localObject2 = (StringRefAddr)localReference.get("passWord")) != null) || ((localObject2 = (StringRefAddr)localReference.get("password")) != null))
      {
        ((OracleDataSource)localObject1).setPassword((String)((StringRefAddr)localObject2).getContent());
      }

      if (((localObject2 = (StringRefAddr)localReference.get("description")) != null) || ((localObject2 = (StringRefAddr)localReference.get("describe")) != null))
      {
        ((OracleDataSource)localObject1).setDescription((String)((StringRefAddr)localObject2).getContent());
      }
      if (((localObject2 = (StringRefAddr)localReference.get("driverType")) != null) || ((localObject2 = (StringRefAddr)localReference.get("driver")) != null))
      {
        ((OracleDataSource)localObject1).setDriverType((String)((StringRefAddr)localObject2).getContent());
      }

      if (((localObject2 = (StringRefAddr)localReference.get("serverName")) != null) || ((localObject2 = (StringRefAddr)localReference.get("host")) != null))
      {
        ((OracleDataSource)localObject1).setServerName((String)((StringRefAddr)localObject2).getContent());
      }

      if (((localObject2 = (StringRefAddr)localReference.get("databaseName")) != null) || ((localObject2 = (StringRefAddr)localReference.get("sid")) != null))
      {
        ((OracleDataSource)localObject1).setDatabaseName((String)((StringRefAddr)localObject2).getContent());
      }

      if ((localObject2 = (StringRefAddr)localReference.get("serviceName")) != null)
      {
        ((OracleDataSource)localObject1).setServiceName((String)((StringRefAddr)localObject2).getContent());
      }

      if (((localObject2 = (StringRefAddr)localReference.get("networkProtocol")) != null) || ((localObject2 = (StringRefAddr)localReference.get("protocol")) != null))
      {
        ((OracleDataSource)localObject1).setNetworkProtocol((String)((StringRefAddr)localObject2).getContent());
      }

      if (((localObject2 = (StringRefAddr)localReference.get("portNumber")) != null) || ((localObject2 = (StringRefAddr)localReference.get("port")) != null))
      {
        str2 = (String)((StringRefAddr)localObject2).getContent();

        ((OracleDataSource)localObject1).setPortNumber(Integer.parseInt(str2));
      }

      if (((localObject2 = (StringRefAddr)localReference.get("tnsentryname")) != null) || ((localObject2 = (StringRefAddr)localReference.get("tns")) != null))
      {
        ((OracleDataSource)localObject1).setTNSEntryName((String)((StringRefAddr)localObject2).getContent());
      }
      else if (str1.equals("oracle.jdbc.pool.OracleOCIConnectionPool"))
      {
        str2 = null;

        if ((localObject2 = (StringRefAddr)localReference.get("connpool_is_poolcreated")) != null)
        {
          str2 = (String)((StringRefAddr)localObject2).getContent();
        }
        if (str2.equals(String.valueOf("true"))) {
          ((OracleOCIConnectionPool)localObject1).setPoolConfig(localProperties);
        }
      }
    }
    return localObject1;
  }

  private Properties extractConnectionCacheProperties(String paramString)
    throws SQLException
  {
    Properties localProperties = new Properties();

    paramString = paramString.substring(1, paramString.length() - 1);

    int i = paramString.indexOf("AttributeWeights", 0);
    String str1;
    String str3;
    if (i >= 0)
    {
      if ((paramString.charAt(i + 16) != '=') || ((i > 0) && (paramString.charAt(i - 1) != ' ')))
      {
        SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 139);

        sqlexception.fillInStackTrace();
        throw sqlexception;
      }

      Properties properties = new Properties();
      int j = paramString.indexOf("}", i);
      str1 = paramString.substring(i, j);

      String str2 = str1.substring(18);

      StringTokenizer localStringTokenizer = new StringTokenizer(str2, ", ");

      synchronized (localStringTokenizer)
      {
        while (localStringTokenizer.hasMoreTokens())
        {
          str3 = localStringTokenizer.nextToken();
          int n = str3.length();
          int i1 = str3.indexOf("=");

          String str4 = str3.substring(0, i1);
          String str5 = str3.substring(i1 + 1, n);

          properties.setProperty(str4, str5);
        }
      }

      localProperties.put("AttributeWeights", properties);

      if ((i > 0) && (j + 1 == paramString.length()))
      {
        paramString = paramString.substring(0, i - 2);
      }
      else if ((i > 0) && (j + 1 < paramString.length()))
      {
        String str = paramString.substring(0, i - 2);
        str3 = paramString.substring(j + 1, paramString.length());

        paramString = str.concat(str3);
      }
      else
      {
        paramString = paramString.substring(j + 2, paramString.length());
      }

    }

    Object localObject1 = new StringTokenizer(paramString, ", ");

    synchronized (localObject1)
    {
      while (((StringTokenizer)localObject1).hasMoreTokens())
      {
        str1 = ((StringTokenizer)localObject1).nextToken();
        int k = str1.length();
        int m = str1.indexOf("=");

        String str = str1.substring(0, m);
        str3 = str1.substring(m + 1, k);

        localProperties.setProperty(str, str3);
      }
    }
    return localProperties;
  }

  protected OracleConnection getConnectionDuringExceptionHandling()
  {
    return null;
  }
}