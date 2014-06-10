package oracle.jdbc.xa.client;

import java.sql.Connection;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import oracle.jdbc.xa.OracleXAResource;

public class OracleXAHeteroConnection extends OracleXAConnection
{
  private int rmid = -1;
  private String xaCloseString = null;

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  public OracleXAHeteroConnection()
    throws XAException
  {
  }

  public OracleXAHeteroConnection(Connection paramConnection)
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
        this.xaResource = new OracleXAHeteroResource(this.physicalConn, this);

        ((OracleXAHeteroResource)this.xaResource).setRmid(this.rmid);

        if (this.logicalHandle != null)
        {
          ((OracleXAResource)this.xaResource).setLogicalConnection(this.logicalHandle);
        }
      }
    }
    catch (XAException localXAException)
    {
      this.xaResource = null;
    }

    return this.xaResource;
  }

  synchronized void setRmid(int paramInt)
  {
    this.rmid = paramInt;
  }

  synchronized int getRmid()
  {
    return this.rmid;
  }

  synchronized void setXaCloseString(String paramString)
  {
    this.xaCloseString = paramString;
  }

  synchronized String getXaCloseString()
  {
    return this.xaCloseString;
  }
}