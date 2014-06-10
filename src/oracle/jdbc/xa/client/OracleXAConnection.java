package oracle.jdbc.xa.client;

import java.sql.Connection;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;

public class OracleXAConnection extends oracle.jdbc.xa.OracleXAConnection
{
  protected boolean isXAResourceTransLoose = false;

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  public OracleXAConnection()
    throws XAException
  {
  }

  public OracleXAConnection(Connection paramConnection)
    throws XAException
  {
    super(paramConnection);
  }

  public synchronized XAResource getXAResource()
  {
    try
    {
      if (this.xaResource == null)
      {
        this.xaResource = new OracleXAResource(this.physicalConn, this);
        ((OracleXAResource)this.xaResource).isTransLoose = this.isXAResourceTransLoose;

        if (this.logicalHandle != null)
        {
          ((oracle.jdbc.xa.OracleXAResource)this.xaResource).setLogicalConnection(this.logicalHandle);
        }
      }
    }
    catch (XAException localXAException)
    {
      this.xaResource = null;
    }

    return this.xaResource;
  }
}