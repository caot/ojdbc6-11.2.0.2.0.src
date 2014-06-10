package oracle.sql;

import java.sql.Connection;
import java.sql.SQLException;
import oracle.jdbc.internal.ObjectData;

public abstract interface ORAData extends ObjectData
{
  public abstract Datum toDatum(Connection paramConnection)
    throws SQLException;
}