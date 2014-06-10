package oracle.sql;

import java.sql.SQLException;
import oracle.jdbc.driver.OracleConnection;
import oracle.jdbc.internal.ObjectData;

public abstract interface CustomDatum extends ObjectData
{
  public abstract Datum toDatum(OracleConnection paramOracleConnection)
    throws SQLException;
}