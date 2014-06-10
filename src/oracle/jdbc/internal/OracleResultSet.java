package oracle.jdbc.internal;

import java.sql.SQLException;

public abstract interface OracleResultSet extends oracle.jdbc.OracleResultSet
{
  public abstract void closeStatementOnClose()
    throws SQLException;
}