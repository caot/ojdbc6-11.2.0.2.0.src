package oracle.jdbc.driver;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import oracle.jdbc.oci.OracleOCIConnection;

class T2CDriverExtension extends OracleDriverExtension
{
  static final int T2C_DEFAULT_BATCHSIZE = 1;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  Connection getConnection(String paramString, Properties paramProperties)
    throws SQLException
  {
    Object localObject = null;
    if (paramProperties.getProperty("is_connection_pooling") == "true")
    {
      localObject = new OracleOCIConnection(paramString, paramProperties, this);
    }
    else
    {
      localObject = new T2CConnection(paramString, paramProperties, this);
    }
    return localObject;
  }

  OracleStatement allocateStatement(PhysicalConnection paramPhysicalConnection, int paramInt1, int paramInt2)
    throws SQLException
  {
    return new T2CStatement((T2CConnection)paramPhysicalConnection, 1, paramPhysicalConnection.defaultRowPrefetch, paramInt1, paramInt2);
  }

  OraclePreparedStatement allocatePreparedStatement(PhysicalConnection paramPhysicalConnection, String paramString, int paramInt1, int paramInt2)
    throws SQLException
  {
    return new T2CPreparedStatement((T2CConnection)paramPhysicalConnection, paramString, paramPhysicalConnection.defaultExecuteBatch, paramPhysicalConnection.defaultRowPrefetch, paramInt1, paramInt2);
  }

  OracleCallableStatement allocateCallableStatement(PhysicalConnection paramPhysicalConnection, String paramString, int paramInt1, int paramInt2)
    throws SQLException
  {
    return new T2CCallableStatement((T2CConnection)paramPhysicalConnection, paramString, paramPhysicalConnection.defaultExecuteBatch, paramPhysicalConnection.defaultRowPrefetch, paramInt1, paramInt2);
  }

  OracleInputStream createInputStream(OracleStatement paramOracleStatement, int paramInt, Accessor paramAccessor)
    throws SQLException
  {
    return new T2CInputStream(paramOracleStatement, paramInt, paramAccessor);
  }
}