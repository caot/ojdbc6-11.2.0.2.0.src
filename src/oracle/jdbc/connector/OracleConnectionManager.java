package oracle.jdbc.connector;

import javax.resource.ResourceException;
import javax.resource.spi.ConnectionManager;
import javax.resource.spi.ConnectionRequestInfo;
import javax.resource.spi.ManagedConnection;
import javax.resource.spi.ManagedConnectionFactory;

public class OracleConnectionManager
  implements ConnectionManager
{
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  public Object allocateConnection(ManagedConnectionFactory paramManagedConnectionFactory, ConnectionRequestInfo paramConnectionRequestInfo)
    throws ResourceException
  {
    ManagedConnection localManagedConnection = paramManagedConnectionFactory.createManagedConnection(null, paramConnectionRequestInfo);

    return localManagedConnection.getConnection(null, paramConnectionRequestInfo);
  }
}