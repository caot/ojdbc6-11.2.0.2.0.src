package oracle.jdbc.pool;

import oracle.jdbc.OracleConnection;

/** @deprecated */
public abstract interface OracleConnectionCacheCallback
{
  /** @deprecated */
  public abstract boolean handleAbandonedConnection(OracleConnection paramOracleConnection, Object paramObject);

  /** @deprecated */
  public abstract void releaseConnection(OracleConnection paramOracleConnection, Object paramObject);
}