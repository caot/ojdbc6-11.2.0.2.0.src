package oracle.jdbc;

import java.sql.SQLException;

public abstract interface StructMetaData extends OracleResultSetMetaData
{
  public abstract String getAttributeJavaName(int paramInt)
    throws SQLException;

  public abstract String getOracleColumnClassName(int paramInt)
    throws SQLException;

  public abstract boolean isInherited(int paramInt)
    throws SQLException;

  public abstract int getLocalColumnCount()
    throws SQLException;
}