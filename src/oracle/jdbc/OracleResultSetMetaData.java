package oracle.jdbc;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public abstract interface OracleResultSetMetaData extends ResultSetMetaData
{
  public abstract boolean isNCHAR(int paramInt)
    throws SQLException;

  public abstract SecurityAttribute getSecurityAttribute(int paramInt)
    throws SQLException;

  public static enum SecurityAttribute
  {
    NONE, 

    ENABLED, 

    UNKNOWN;
  }
}