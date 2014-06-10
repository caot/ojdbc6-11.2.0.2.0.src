package oracle.sql;

import java.sql.SQLException;
import oracle.jdbc.internal.ObjectDataFactory;

public abstract interface CustomDatumFactory extends ObjectDataFactory
{
  public abstract CustomDatum create(Datum paramDatum, int paramInt)
    throws SQLException;
}