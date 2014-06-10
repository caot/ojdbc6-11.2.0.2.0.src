package oracle.jdbc.driver;

import oracle.jdbc.internal.OracleConnection;

public abstract interface OracleCloseCallback
{
  public abstract void beforeClose(OracleConnection paramOracleConnection, Object paramObject);

  public abstract void afterClose(Object paramObject);
}