package oracle.jdbc.xa;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.XAConnection;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import oracle.jdbc.pool.OraclePooledConnection;

public abstract class OracleXAConnection extends OraclePooledConnection
  implements XAConnection
{
  protected XAResource xaResource = null;

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  public OracleXAConnection()
    throws XAException
  {
    this(null);
  }

  public OracleXAConnection(Connection paramConnection)
    throws XAException
  {
    super(paramConnection);
  }

  public abstract XAResource getXAResource();

  public synchronized Connection getConnection()
    throws SQLException
  {
    Connection localConnection = super.getConnection();

    if (this.xaResource != null) {
      ((OracleXAResource)this.xaResource).setLogicalConnection(localConnection);
    }
    return localConnection;
  }

  boolean getAutoCommit()
    throws SQLException
  {
    return this.autoCommit;
  }

  void setAutoCommit(boolean paramBoolean)
    throws SQLException
  {
    this.autoCommit = paramBoolean;
  }
}