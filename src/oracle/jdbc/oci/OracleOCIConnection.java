package oracle.jdbc.oci;

import java.sql.SQLException;
import java.util.Properties;

public class OracleOCIConnection extends oracle.jdbc.driver.OracleOCIConnection
{
  public OracleOCIConnection(String paramString, Properties paramProperties, Object paramObject)
    throws SQLException
  {
    super(paramString, paramProperties, paramObject);
  }
}