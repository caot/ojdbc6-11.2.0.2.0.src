package oracle.jdbc.driver;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

abstract class OracleDriverExtension
{
  abstract Connection getConnection(String paramString, Properties paramProperties)
    throws SQLException;

  abstract OracleStatement allocateStatement(PhysicalConnection paramPhysicalConnection, int paramInt1, int paramInt2)
    throws SQLException;

  abstract OraclePreparedStatement allocatePreparedStatement(PhysicalConnection paramPhysicalConnection, String paramString, int paramInt1, int paramInt2)
    throws SQLException;

  abstract OracleCallableStatement allocateCallableStatement(PhysicalConnection paramPhysicalConnection, String paramString, int paramInt1, int paramInt2)
    throws SQLException;

  abstract OracleInputStream createInputStream(OracleStatement paramOracleStatement, int paramInt, Accessor paramAccessor)
    throws SQLException;
}