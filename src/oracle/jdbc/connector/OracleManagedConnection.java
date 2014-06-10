package oracle.jdbc.connector;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Hashtable;
import javax.resource.ResourceException;
import javax.resource.spi.ConnectionEvent;
import javax.resource.spi.ConnectionEventListener;
import javax.resource.spi.ConnectionRequestInfo;
import javax.resource.spi.EISSystemException;
import javax.resource.spi.IllegalStateException;
import javax.resource.spi.LocalTransaction;
import javax.resource.spi.ManagedConnection;
import javax.resource.spi.ManagedConnectionMetaData;
import javax.resource.spi.security.PasswordCredential;
import javax.security.auth.Subject;
import javax.sql.XAConnection;
import javax.transaction.xa.XAResource;
import oracle.jdbc.internal.OracleConnection;
import oracle.jdbc.xa.OracleXAConnection;

public class OracleManagedConnection
  implements ManagedConnection
{
  private OracleXAConnection xaConnection = null;
  private Hashtable connectionListeners = null;
  private Connection connection = null;
  private PrintWriter logWriter = null;
  private PasswordCredential passwordCredential = null;
  private OracleLocalTransaction localTxn = null;

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  OracleManagedConnection(XAConnection paramXAConnection)
  {
    this.xaConnection = ((OracleXAConnection)paramXAConnection);
    this.connectionListeners = new Hashtable(10);
  }

  public Object getConnection(Subject paramSubject, ConnectionRequestInfo paramConnectionRequestInfo)
    throws ResourceException
  {
    try
    {
      if (this.connection != null) {
        this.connection.close();
      }
      this.connection = this.xaConnection.getConnection();

      return this.connection;
    }
    catch (SQLException localSQLException)
    {
      EISSystemException localEISSystemException = new EISSystemException("SQLException: " + localSQLException.getMessage());

      localEISSystemException.setLinkedException(localSQLException);

      throw localEISSystemException;
    }
  }

  public void destroy()
    throws ResourceException
  {
    try
    {
      if (this.xaConnection != null)
      {
        Connection localConnection = this.xaConnection.getPhysicalHandle();

        if (((this.localTxn != null) && (this.localTxn.isBeginCalled)) || (((OracleConnection)localConnection).getTxnMode() == 1))
        {
          throw new IllegalStateException("Could not close connection while transaction is active");
        }
      }

      if (this.connection != null) {
        this.connection.close();
      }
      if (this.xaConnection != null) {
        this.xaConnection.close();
      }
    }
    catch (SQLException localSQLException)
    {
      EISSystemException localEISSystemException = new EISSystemException("SQLException: " + localSQLException.getMessage());

      localEISSystemException.setLinkedException(localSQLException);

      throw localEISSystemException;
    }
  }

  public void cleanup()
    throws ResourceException
  {
    try
    {
      if (this.connection != null)
      {
        if (((this.localTxn != null) && (this.localTxn.isBeginCalled)) || (((OracleConnection)this.connection).getTxnMode() == 1))
        {
          throw new IllegalStateException("Could not close connection while transaction is active");
        }

        this.connection.close();
      }

    }
    catch (SQLException localSQLException)
    {
      EISSystemException localEISSystemException = new EISSystemException("SQLException: " + localSQLException.getMessage());

      localEISSystemException.setLinkedException(localSQLException);

      throw localEISSystemException;
    }
  }

  public void associateConnection(Object paramObject)
  {
  }

  public void addConnectionEventListener(ConnectionEventListener paramConnectionEventListener)
  {
    this.connectionListeners.put(paramConnectionEventListener, paramConnectionEventListener);
  }

  public void removeConnectionEventListener(ConnectionEventListener paramConnectionEventListener)
  {
    this.connectionListeners.remove(paramConnectionEventListener);
  }

  public XAResource getXAResource()
    throws ResourceException
  {
    return this.xaConnection.getXAResource();
  }

  public LocalTransaction getLocalTransaction()
    throws ResourceException
  {
    if (this.localTxn == null)
    {
      this.localTxn = new OracleLocalTransaction(this);
    }

    return this.localTxn;
  }

  public ManagedConnectionMetaData getMetaData()
    throws ResourceException
  {
    return new OracleManagedConnectionMetaData(this);
  }

  public void setLogWriter(PrintWriter paramPrintWriter)
    throws ResourceException
  {
    this.logWriter = paramPrintWriter;
  }

  public PrintWriter getLogWriter()
    throws ResourceException
  {
    return this.logWriter;
  }

  Connection getPhysicalConnection()
    throws ResourceException
  {
    try
    {
      return this.xaConnection.getPhysicalHandle();
    }
    catch (Exception localException)
    {
      EISSystemException localEISSystemException = new EISSystemException("Exception: " + localException.getMessage());

      localEISSystemException.setLinkedException(localException);

      throw localEISSystemException;
    }
  }

  void setPasswordCredential(PasswordCredential paramPasswordCredential)
  {
    this.passwordCredential = paramPasswordCredential;
  }

  PasswordCredential getPasswordCredential()
  {
    return this.passwordCredential;
  }

  void eventOccurred(int paramInt)
    throws ResourceException
  {
    Enumeration localEnumeration = this.connectionListeners.keys();

    while (localEnumeration.hasMoreElements())
    {
      ConnectionEventListener localConnectionEventListener = (ConnectionEventListener)localEnumeration.nextElement();

      ConnectionEvent localConnectionEvent = new ConnectionEvent(this, paramInt);

      switch (paramInt)
      {
      case 1:
        localConnectionEventListener.connectionClosed(localConnectionEvent);

        break;
      case 2:
        localConnectionEventListener.localTransactionStarted(localConnectionEvent);

        break;
      case 3:
        localConnectionEventListener.localTransactionCommitted(localConnectionEvent);

        break;
      case 4:
        localConnectionEventListener.localTransactionRolledback(localConnectionEvent);

        break;
      case 5:
        localConnectionEventListener.connectionErrorOccurred(localConnectionEvent);

        break;
      default:
        throw new IllegalArgumentException("Illegal eventType in eventOccurred(): " + paramInt);
      }
    }
  }
}