package oracle.jdbc.driver;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

class T4CDriverExtension extends OracleDriverExtension
{
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  Connection getConnection(String paramString, Properties paramProperties)
    throws SQLException
  {
    return new T4CConnection(paramString, paramProperties, this);
  }

  OracleStatement allocateStatement(PhysicalConnection paramPhysicalConnection, int paramInt1, int paramInt2)
    throws SQLException
  {
    return new T4CStatement(paramPhysicalConnection, paramInt1, paramInt2);
  }

  OraclePreparedStatement allocatePreparedStatement(PhysicalConnection paramPhysicalConnection, String paramString, int paramInt1, int paramInt2)
    throws SQLException
  {
    return new T4CPreparedStatement(paramPhysicalConnection, paramString, paramInt1, paramInt2);
  }

  OracleCallableStatement allocateCallableStatement(PhysicalConnection paramPhysicalConnection, String paramString, int paramInt1, int paramInt2)
    throws SQLException
  {
    return new T4CCallableStatement(paramPhysicalConnection, paramString, paramInt1, paramInt2);
  }

  OracleInputStream createInputStream(OracleStatement paramOracleStatement, int paramInt, Accessor paramAccessor)
    throws SQLException
  {
    return new T4CInputStream(paramOracleStatement, paramInt, paramAccessor);
  }
}