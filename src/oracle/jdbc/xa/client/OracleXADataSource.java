package oracle.jdbc.xa.client;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import javax.sql.PooledConnection;
import javax.sql.XAConnection;
import javax.transaction.xa.XAException;
import oracle.jdbc.OracleConnection;
import oracle.jdbc.driver.DatabaseError;
import oracle.jdbc.driver.OracleDriver;
import oracle.jdbc.driver.T2CConnection;
import oracle.jdbc.driver.T4CXAConnection;

public class OracleXADataSource extends oracle.jdbc.xa.OracleXADataSource
{
  private static final boolean DEBUG = false;
  private int rmid = -1;
  private String xaOpenString = null;
  private static boolean libraryLoaded = false;
  private static final String dbSuffix = "HeteroXA";
  private static final String dllName = "heteroxa11";
  private static final char atSignChar = '@';
  private static int rmidSeed = 0;
  private static final int MAX_RMID_SEED = 65536;
  private String driverCharSetIdString = null;

  private String oldTnsEntry = null;

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  public OracleXADataSource()
    throws SQLException
  {
    this.isOracleDataSource = true;
  }

  public XAConnection getXAConnection()
    throws SQLException
  {
    Properties localProperties = new Properties(this.connectionProperties);

    if ((this.user != null) && (this.password != null))
    {
      localProperties.setProperty("user", this.user);
      localProperties.setProperty("password", this.password);
    }

    return getXAConnection(localProperties);
  }

  public XAConnection getXAConnection(String paramString1, String paramString2)
    throws SQLException
  {
    Properties localProperties = new Properties(this.connectionProperties);
    if ((paramString1 != null) && (paramString2 != null))
    {
      localProperties.setProperty("user", paramString1);
      localProperties.setProperty("password", paramString2);
    }
    else {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    return getXAConnection(localProperties);
  }

  public XAConnection getXAConnection(Properties paramProperties)
    throws SQLException
  {
    if (this.connCachingEnabled)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 163);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    return (XAConnection)getPooledConnection(paramProperties);
  }

  public PooledConnection getPooledConnection(String paramString1, String paramString2)
    throws SQLException
  {
    Properties localProperties = new Properties();
    localProperties.setProperty("user", paramString1);
    localProperties.setProperty("password", paramString2);

    return getPooledConnection(localProperties);
  }

  public PooledConnection getPooledConnection(Properties paramProperties)
    throws SQLException
  {
    try
    {
      String str1 = getURL();
      String str2 = paramProperties.getProperty("user");
      String str3 = paramProperties.getProperty("password");
      String str4 = null;
      String str5 = null;
      String str6 = null;
      int i = 0;

      if ((str2 == null) || (str2.equals("")) || (str3 == null) || (str3.equals("")))
      {
        if ((str1 != null) && (!str1.matches("jdbc:oracle:.*/.*@.*")))
        {
          localObject1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 67);
          ((SQLException)localObject1).fillInStackTrace();
          throw ((Throwable)localObject1);
        }

      }

      if ((this.useNativeXA) && ((str1.startsWith("jdbc:oracle:oci8")) || (str1.startsWith("jdbc:oracle:oci"))))
      {
        localObject1 = new long[] { 0L, 0L };

        String str7 = null;
        String str8 = null;

        synchronized (this)
        {
          if (this.tnsEntry != null)
            str7 = this.tnsEntry;
          else {
            str7 = getTNSEntryFromUrl(str1);
          }

          if (((str7 != null) && (str7.length() == 0)) || (str7.startsWith("(DESCRIPTION")))
          {
            SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 207);
            localSQLException.fillInStackTrace();
            throw localSQLException;
          }

          if (!libraryLoaded)
          {
            synchronized (OracleXADataSource.class)
            {
              if (!libraryLoaded)
              {
                try
                {
                  System.loadLibrary("heteroxa11");

                  libraryLoaded = true;
                }
                catch (Error localError)
                {
                  libraryLoaded = false;

                  throw localError;
                }

              }

            }

          }

          if (this.connectionProperties != null)
          {
            str8 = this.connectionProperties.getProperty("oracle.jdbc.ociNlsLangBackwardCompatible");
          }

        }

        if ((str8 != null) && (str8.equalsIgnoreCase("true")))
        {
          ??? = T2CConnection.getDriverCharSetIdFromNLS_LANG(null);
          this.driverCharSetIdString = Integer.toString(???);
        }
        else if (!str7.equals(this.oldTnsEntry))
        {
          ??? = T2CConnection.getClientCharSetId();

          this.driverCharSetIdString = Integer.toString(???);
          this.oldTnsEntry = str7;
        }

        synchronized (this)
        {
          str4 = new StringBuilder().append(this.databaseName).append("HeteroXA").append(rmidSeed).toString();

          this.rmid = (i = rmidSeed);

          synchronized (OracleXADataSource.class)
          {
            rmidSeed = (rmidSeed + 1) % 65536;
          }

          int k = 0;

          localOracleXAHeteroConnection = this.connectionProperties != null ? this.connectionProperties.getProperty("oracle.jdbc.XATransLoose") : null;

          this.xaOpenString = (str6 = generateXAOpenString(str4, str7, str2, str3, 60, 2000, true, true, ".", k, false, (localOracleXAHeteroConnection != null) && (localOracleXAHeteroConnection.equalsIgnoreCase("true")), this.driverCharSetIdString, this.driverCharSetIdString));

          str5 = generateXACloseString(str4, false);
        }

        int j = t2cDoXaOpen(str6, i, 0, 0);

        if (j != 0)
        {
          localObject2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), -1 * j);
          ((SQLException)localObject2).fillInStackTrace();
          throw ((Throwable)localObject2);
        }

        j = t2cConvertOciHandles(str4, (long[])localObject1);

        if (j != 0)
        {
          localObject2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), -1 * j);
          ((SQLException)localObject2).fillInStackTrace();
          throw ((Throwable)localObject2);
        }

        paramProperties.put("OCISvcCtxHandle", String.valueOf(localObject1[0]));
        paramProperties.put("OCIEnvHandle", String.valueOf(localObject1[1]));
        paramProperties.put("JDBCDriverCharSetId", this.driverCharSetIdString);

        if (this.loginTimeout != 0) {
          paramProperties.put("oracle.net.CONNECT_TIMEOUT", new StringBuilder().append("").append(this.loginTimeout * 1000).toString());
        }

        Object localObject2 = this.driver.connect(getURL(), paramProperties);

        ((OracleConnection)localObject2).setStatementCacheSize(this.maxStatements);
        ((OracleConnection)localObject2).setExplicitCachingEnabled(this.explicitCachingEnabled);
        ((OracleConnection)localObject2).setImplicitCachingEnabled(this.implicitCachingEnabled);

        if ((this.maxStatements > 0) && (!this.explicitCachingEnabled) && (!this.implicitCachingEnabled))
        {
          ((OracleConnection)localObject2).setImplicitCachingEnabled(true);
          ((OracleConnection)localObject2).setExplicitCachingEnabled(true);
        }

        OracleXAHeteroConnection localOracleXAHeteroConnection = new OracleXAHeteroConnection((Connection)localObject2);

        if ((str2 != null) && (str3 != null))
          localOracleXAHeteroConnection.setUserName(str2, str3);
        localOracleXAHeteroConnection.setRmid(i);
        localOracleXAHeteroConnection.setXaCloseString(str5);
        localOracleXAHeteroConnection.registerCloseCallback(new OracleXAHeteroCloseCallback(), localOracleXAHeteroConnection);

        return localOracleXAHeteroConnection;
      }
      if ((this.thinUseNativeXA) && (str1.startsWith("jdbc:oracle:thin")))
      {
        localObject1 = new Properties();
        synchronized (this)
        {
          synchronized (OracleXADataSource.class)
          {
            rmidSeed = (rmidSeed + 1) % 65536;

            this.rmid = rmidSeed;
          }

          if (this.connectionProperties == null) {
            this.connectionProperties = new Properties();
          }
          this.connectionProperties.put("RessourceManagerId", Integer.toString(this.rmid));
          if (str2 != null)
            ((Properties)localObject1).setProperty("user", str2);
          if (str3 != null)
            ((Properties)localObject1).setProperty("password", str3);
          ((Properties)localObject1).setProperty("stmt_cache_size", new StringBuilder().append("").append(this.maxStatements).toString());

          ((Properties)localObject1).setProperty("ImplicitStatementCachingEnabled", new StringBuilder().append("").append(this.implicitCachingEnabled).toString());

          ((Properties)localObject1).setProperty("ExplicitStatementCachingEnabled", new StringBuilder().append("").append(this.explicitCachingEnabled).toString());

          ((Properties)localObject1).setProperty("LoginTimeout", new StringBuilder().append("").append(this.loginTimeout).toString());
        }

        ??? = new T4CXAConnection(super.getPhysicalConnection((Properties)localObject1));

        if ((str2 != null) && (str3 != null)) {
          ((T4CXAConnection)???).setUserName(str2, str3);
        }
        ??? = this.connectionProperties != null ? this.connectionProperties.getProperty("oracle.jdbc.XATransLoose") : null;

        ((OracleXAConnection)???).isXAResourceTransLoose = ((??? != null) && ((((String)???).equals("true")) || (((String)???).equalsIgnoreCase("true"))));

        return ???;
      }

      Object localObject1 = new Properties();
      synchronized (this)
      {
        if (str2 != null)
          ((Properties)localObject1).setProperty("user", str2);
        if (str3 != null)
          ((Properties)localObject1).setProperty("password", str3);
        ((Properties)localObject1).setProperty("stmt_cache_size", new StringBuilder().append("").append(this.maxStatements).toString());

        ((Properties)localObject1).setProperty("ImplicitStatementCachingEnabled", new StringBuilder().append("").append(this.implicitCachingEnabled).toString());

        ((Properties)localObject1).setProperty("ExplicitStatementCachingEnabled", new StringBuilder().append("").append(this.explicitCachingEnabled).toString());

        ((Properties)localObject1).setProperty("LoginTimeout", new StringBuilder().append("").append(this.loginTimeout).toString());
      }

      ??? = new OracleXAConnection(super.getPhysicalConnection((Properties)localObject1));

      if ((str2 != null) && (str3 != null)) {
        ((OracleXAConnection)???).setUserName(str2, str3);
      }
      ??? = this.connectionProperties != null ? this.connectionProperties.getProperty("oracle.jdbc.XATransLoose") : null;

      ((OracleXAConnection)???).isXAResourceTransLoose = ((??? != null) && ((((String)???).equals("true")) || (((String)???).equalsIgnoreCase("true"))));

      return ???;
    }
    catch (XAException localXAException)
    {
    }

    return null;
  }

  private native int t2cDoXaOpen(String paramString, int paramInt1, int paramInt2, int paramInt3);

  private native int t2cConvertOciHandles(String paramString, long[] paramArrayOfLong);

  synchronized void setRmid(int paramInt)
  {
    this.rmid = paramInt;
  }

  synchronized int getRmid()
  {
    return this.rmid;
  }

  synchronized void setXaOpenString(String paramString)
  {
    this.xaOpenString = paramString;
  }

  synchronized String getXaOpenString()
  {
    return this.xaOpenString;
  }

  private String generateXAOpenString(String paramString1, String paramString2, String paramString3, String paramString4, int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2, String paramString5, int paramInt3, boolean paramBoolean3, boolean paramBoolean4, String paramString6, String paramString7)
  {
    return new StringBuilder().append("ORACLE_XA+DB=").append(paramString1).append("+ACC=P/").append(paramString3).append("/").append(paramString4).append("+SESTM=").append(paramInt2).append("+SESWT=").append(paramInt1).append("+LOGDIR=").append(paramString5).append("+SQLNET=").append(paramString2).append(paramBoolean1 ? "+THREADS=true" : "").append(paramBoolean2 ? "+OBJECTS=true" : "").append("+DBGFL=0x").append(paramInt3).append(paramBoolean3 ? "+CONNCACHE=t" : "+CONNCACHE=f").append(paramBoolean4 ? "+Loose_Coupling=t" : "").append("+CharSet=").append(paramString6).append("+NCharSet=").append(paramString7).toString();
  }

  private String generateXACloseString(String paramString, boolean paramBoolean)
  {
    return new StringBuilder().append("ORACLE_XA+DB=").append(paramString).append(paramBoolean ? "+CONNCACHE=t" : "+CONNCACHE=f").toString();
  }

  private String getTNSEntryFromUrl(String paramString)
  {
    int i = paramString.indexOf('@');

    return paramString.substring(i + 1);
  }
}