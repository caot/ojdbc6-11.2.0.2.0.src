package oracle.jdbc.driver;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

abstract interface ScrollRsetStatement
{
  public abstract Connection getConnection()
    throws SQLException;

  public abstract void notifyCloseRset()
    throws SQLException;

  public abstract int copyBinds(Statement paramStatement, int paramInt)
    throws SQLException;

  public abstract String getOriginalSql()
    throws SQLException;

  public abstract OracleResultSetCache getResultSetCache()
    throws SQLException;

  public abstract int getMaxFieldSize()
    throws SQLException;
}