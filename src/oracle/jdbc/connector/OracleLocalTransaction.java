package oracle.jdbc.connector;

import java.sql.Connection;
import java.sql.SQLException;
import javax.resource.ResourceException;
import javax.resource.spi.EISSystemException;
import javax.resource.spi.IllegalStateException;
import javax.resource.spi.LocalTransaction;
import javax.resource.spi.LocalTransactionException;
import oracle.jdbc.internal.OracleConnection;

public class OracleLocalTransaction
  implements LocalTransaction
{
  private OracleManagedConnection managedConnection = null;
  private Connection connection = null;
  boolean isBeginCalled = false;
  private static final String RAERR_LTXN_COMMIT = "commit without begin";
  private static final String RAERR_LTXN_ROLLBACK = "rollback without begin";
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  OracleLocalTransaction(OracleManagedConnection paramOracleManagedConnection)
    throws ResourceException
  {
    this.managedConnection = paramOracleManagedConnection;
    this.connection = paramOracleManagedConnection.getPhysicalConnection();
    this.isBeginCalled = false;
  }

  public void begin()
    throws ResourceException
  {
    try
    {
      if (((OracleConnection)this.connection).getTxnMode() == 1)
      {
        throw new IllegalStateException("Could not start a new transaction inside an active transaction");
      }

      if (this.connection.getAutoCommit()) {
        this.connection.setAutoCommit(false);
      }
      this.isBeginCalled = true;
    }
    catch (SQLException localSQLException)
    {
      EISSystemException localEISSystemException = new EISSystemException("SQLException: " + localSQLException.getMessage());

      localEISSystemException.setLinkedException(localSQLException);

      throw localEISSystemException;
    }

    this.managedConnection.eventOccurred(2);
  }

  public void commit()
    throws ResourceException
  {
    if (!this.isBeginCalled) {
      throw new LocalTransactionException("begin() must be called before commit()", "commit without begin");
    }
    try
    {
      this.connection.commit();
    }
    catch (SQLException localSQLException)
    {
      EISSystemException localEISSystemException = new EISSystemException("SQLException: " + localSQLException.getMessage());

      localEISSystemException.setLinkedException(localSQLException);

      throw localEISSystemException;
    }

    this.isBeginCalled = false;

    this.managedConnection.eventOccurred(3);
  }

  public void rollback()
    throws ResourceException
  {
    if (!this.isBeginCalled) {
      throw new LocalTransactionException("begin() must be called before rollback()", "rollback without begin");
    }
    try
    {
      this.connection.rollback();
    }
    catch (SQLException localSQLException)
    {
      EISSystemException localEISSystemException = new EISSystemException("SQLException: " + localSQLException.getMessage());

      localEISSystemException.setLinkedException(localSQLException);

      throw localEISSystemException;
    }

    this.isBeginCalled = false;

    this.managedConnection.eventOccurred(4);
  }
}