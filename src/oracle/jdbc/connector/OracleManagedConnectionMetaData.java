package oracle.jdbc.connector;

import java.sql.SQLException;
import javax.resource.ResourceException;
import javax.resource.spi.EISSystemException;
import javax.resource.spi.ManagedConnectionMetaData;
import oracle.jdbc.OracleConnection;
import oracle.jdbc.OracleDatabaseMetaData;

public class OracleManagedConnectionMetaData
  implements ManagedConnectionMetaData
{
  private OracleManagedConnection managedConnection = null;
  private OracleDatabaseMetaData databaseMetaData = null;

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  OracleManagedConnectionMetaData(OracleManagedConnection paramOracleManagedConnection)
    throws ResourceException
  {
    try
    {
      this.managedConnection = paramOracleManagedConnection;

      OracleConnection localOracleConnection = (OracleConnection)paramOracleManagedConnection.getPhysicalConnection();

      this.databaseMetaData = ((OracleDatabaseMetaData)localOracleConnection.getMetaData());
    }
    catch (Exception localException)
    {
      EISSystemException localEISSystemException = new EISSystemException("Exception: " + localException.getMessage());

      localEISSystemException.setLinkedException(localException);

      throw localEISSystemException;
    }
  }

  public String getEISProductName()
    throws ResourceException
  {
    try
    {
      return this.databaseMetaData.getDatabaseProductName();
    }
    catch (SQLException localSQLException)
    {
      EISSystemException localEISSystemException = new EISSystemException("SQLException: " + localSQLException.getMessage());

      localEISSystemException.setLinkedException(localSQLException);

      throw localEISSystemException;
    }
  }

  public String getEISProductVersion()
    throws ResourceException
  {
    try
    {
      return this.databaseMetaData.getDatabaseProductVersion();
    }
    catch (Exception localException)
    {
      EISSystemException localEISSystemException = new EISSystemException("Exception: " + localException.getMessage());

      localEISSystemException.setLinkedException(localException);

      throw localEISSystemException;
    }
  }

  public int getMaxConnections()
    throws ResourceException
  {
    try
    {
      return this.databaseMetaData.getMaxConnections();
    }
    catch (SQLException localSQLException)
    {
      EISSystemException localEISSystemException = new EISSystemException("SQLException: " + localSQLException.getMessage());

      localEISSystemException.setLinkedException(localSQLException);

      throw localEISSystemException;
    }
  }

  public String getUserName()
    throws ResourceException
  {
    try
    {
      return this.databaseMetaData.getUserName();
    }
    catch (SQLException localSQLException)
    {
      EISSystemException localEISSystemException = new EISSystemException("SQLException: " + localSQLException.getMessage());

      localEISSystemException.setLinkedException(localSQLException);

      throw localEISSystemException;
    }
  }
}