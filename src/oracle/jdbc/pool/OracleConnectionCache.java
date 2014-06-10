package oracle.jdbc.pool;

import java.sql.SQLException;
import javax.sql.DataSource;
import javax.sql.PooledConnection;

public abstract interface OracleConnectionCache extends DataSource
{
  public abstract void reusePooledConnection(PooledConnection paramPooledConnection)
    throws SQLException;

  public abstract void closePooledConnection(PooledConnection paramPooledConnection)
    throws SQLException;

  public abstract void close()
    throws SQLException;
}