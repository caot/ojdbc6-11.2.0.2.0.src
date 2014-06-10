package oracle.jdbc.connector;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.resource.ResourceException;
import javax.resource.spi.ConnectionManager;
import javax.resource.spi.ConnectionRequestInfo;
import javax.resource.spi.EISSystemException;
import javax.resource.spi.ManagedConnection;
import javax.resource.spi.ManagedConnectionFactory;
import javax.resource.spi.ResourceAdapterInternalException;
import javax.resource.spi.security.PasswordCredential;
import javax.security.auth.Subject;
import javax.sql.DataSource;
import javax.sql.XAConnection;
import javax.sql.XADataSource;

public class OracleManagedConnectionFactory
  implements ManagedConnectionFactory
{
  private XADataSource xaDataSource = null;
  private String xaDataSourceName = null;
  private static final String RAERR_MCF_SET_XADS = "invalid xads";
  private static final String RAERR_MCF_GET_PCRED = "no password credential";
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  public OracleManagedConnectionFactory()
    throws ResourceException
  {
  }

  public OracleManagedConnectionFactory(XADataSource paramXADataSource)
    throws ResourceException
  {
    this.xaDataSource = paramXADataSource;
    this.xaDataSourceName = "XADataSource";
  }

  public void setXADataSourceName(String paramString)
  {
    this.xaDataSourceName = paramString;
  }

  public String getXADataSourceName()
  {
    return this.xaDataSourceName;
  }

  public Object createConnectionFactory(ConnectionManager paramConnectionManager)
    throws ResourceException
  {
    if (this.xaDataSource == null)
    {
      setupXADataSource();
    }

    return (DataSource)this.xaDataSource;
  }

  public Object createConnectionFactory()
    throws ResourceException
  {
    return createConnectionFactory(null);
  }

  public ManagedConnection createManagedConnection(Subject paramSubject, ConnectionRequestInfo paramConnectionRequestInfo)
    throws ResourceException
  {
    Object localObject;
    try
    {
      if (this.xaDataSource == null)
      {
        setupXADataSource();
      }

      XAConnection localXAConnection = null;
      localObject = getPasswordCredential(paramSubject, paramConnectionRequestInfo);

      if (localObject == null)
      {
        localXAConnection = this.xaDataSource.getXAConnection();
      }
      else
      {
        localXAConnection = this.xaDataSource.getXAConnection(((PasswordCredential)localObject).getUserName(), new String(((PasswordCredential)localObject).getPassword()));
      }

      OracleManagedConnection localOracleManagedConnection = new OracleManagedConnection(localXAConnection);

      localOracleManagedConnection.setPasswordCredential((PasswordCredential)localObject);

      localOracleManagedConnection.setLogWriter(getLogWriter());

      return localOracleManagedConnection;
    }
    catch (SQLException localSQLException)
    {
      localObject = new EISSystemException("SQLException: " + localSQLException.getMessage());

      ((ResourceException)localObject).setLinkedException(localSQLException);
    }
    throw ((Throwable)localObject);
  }

  public ManagedConnection matchManagedConnections(Set paramSet, Subject paramSubject, ConnectionRequestInfo paramConnectionRequestInfo)
    throws ResourceException
  {
    PasswordCredential localPasswordCredential = getPasswordCredential(paramSubject, paramConnectionRequestInfo);
    Iterator localIterator = paramSet.iterator();

    while (localIterator.hasNext())
    {
      Object localObject = localIterator.next();

      if ((localObject instanceof OracleManagedConnection))
      {
        OracleManagedConnection localOracleManagedConnection = (OracleManagedConnection)localObject;

        if (localOracleManagedConnection.getPasswordCredential().equals(localPasswordCredential))
        {
          return localOracleManagedConnection;
        }
      }
    }

    return null;
  }

  public void setLogWriter(PrintWriter paramPrintWriter)
    throws ResourceException
  {
    try
    {
      if (this.xaDataSource == null)
      {
        setupXADataSource();
      }

      this.xaDataSource.setLogWriter(paramPrintWriter);
    }
    catch (SQLException localSQLException)
    {
      EISSystemException localEISSystemException = new EISSystemException("SQLException: " + localSQLException.getMessage());

      localEISSystemException.setLinkedException(localSQLException);

      throw localEISSystemException;
    }
  }

  public PrintWriter getLogWriter()
    throws ResourceException
  {
    try
    {
      if (this.xaDataSource == null)
      {
        setupXADataSource();
      }

      return this.xaDataSource.getLogWriter();
    }
    catch (SQLException localSQLException)
    {
      EISSystemException localEISSystemException = new EISSystemException("SQLException: " + localSQLException.getMessage());

      localEISSystemException.setLinkedException(localSQLException);

      throw localEISSystemException;
    }
  }

  private void setupXADataSource()
    throws ResourceException
  {
    try
    {
      InitialContext localInitialContext = null;
      try
      {
        Properties localProperties = System.getProperties();

        localInitialContext = new InitialContext(localProperties);
      }
      catch (java.lang.SecurityException localSecurityException)
      {
      }

      if (localInitialContext == null)
      {
        localInitialContext = new InitialContext();
      }

      localObject = (XADataSource)localInitialContext.lookup(this.xaDataSourceName);

      if (localObject == null)
      {
        throw new ResourceAdapterInternalException("Invalid XADataSource object");
      }

      this.xaDataSource = ((XADataSource)localObject);
    }
    catch (NamingException localNamingException)
    {
      Object localObject = new ResourceException("NamingException: " + localNamingException.getMessage());

      ((ResourceException)localObject).setLinkedException(localNamingException);

      throw ((Throwable)localObject);
    }
  }

  private PasswordCredential getPasswordCredential(Subject paramSubject, ConnectionRequestInfo paramConnectionRequestInfo)
    throws ResourceException
  {
    if (paramSubject != null)
    {
      localObject1 = paramSubject.getPrivateCredentials(PasswordCredential.class);
      localObject2 = ((Set)localObject1).iterator();

      while (((Iterator)localObject2).hasNext())
      {
        PasswordCredential localPasswordCredential = (PasswordCredential)((Iterator)localObject2).next();

        if (localPasswordCredential.getManagedConnectionFactory().equals(this))
        {
          return localPasswordCredential;
        }
      }

      throw new javax.resource.spi.SecurityException("Can not find user/password information", "no password credential");
    }

    if (paramConnectionRequestInfo == null)
    {
      return null;
    }

    Object localObject1 = (OracleConnectionRequestInfo)paramConnectionRequestInfo;

    Object localObject2 = new PasswordCredential(((OracleConnectionRequestInfo)localObject1).getUser(), ((OracleConnectionRequestInfo)localObject1).getPassword().toCharArray());

    ((PasswordCredential)localObject2).setManagedConnectionFactory(this);

    return localObject2;
  }
}