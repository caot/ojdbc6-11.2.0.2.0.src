package oracle.jdbc.pool;

import java.io.Serializable;
import java.sql.SQLException;
import javax.sql.ConnectionEvent;
import javax.sql.ConnectionEventListener;
import javax.sql.PooledConnection;

/** @deprecated */
class OracleConnectionCacheEventListener
  implements ConnectionEventListener, Serializable
{
  static final int CONNECTION_CLOSED_EVENT = 101;
  static final int CONNECTION_ERROROCCURED_EVENT = 102;
  protected OracleImplicitConnectionCache implicitCache = null;

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  public OracleConnectionCacheEventListener()
  {
    this(null);
  }

  public OracleConnectionCacheEventListener(OracleImplicitConnectionCache paramOracleImplicitConnectionCache)
  {
    this.implicitCache = paramOracleImplicitConnectionCache;
  }

  public void connectionClosed(ConnectionEvent paramConnectionEvent)
  {
    try
    {
      if (this.implicitCache != null)
      {
        this.implicitCache.reusePooledConnection((PooledConnection)paramConnectionEvent.getSource());
      }
    }
    catch (SQLException localSQLException)
    {
    }
  }

  public void connectionErrorOccurred(ConnectionEvent paramConnectionEvent)
  {
    try
    {
      if (this.implicitCache != null)
      {
        this.implicitCache.closePooledConnection((PooledConnection)paramConnectionEvent.getSource());
      }
    }
    catch (SQLException localSQLException)
    {
    }
  }
}