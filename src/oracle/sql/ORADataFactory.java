package oracle.sql;

import java.sql.SQLException;
import oracle.jdbc.internal.ObjectDataFactory;

public abstract interface ORADataFactory extends ObjectDataFactory
{
  public abstract ORAData create(Datum paramDatum, int paramInt)
    throws SQLException;
}