package oracle.jdbc.driver;

import java.sql.SQLException;
import oracle.sql.OPAQUE;

public abstract interface Opaqueable
{
  public abstract OPAQUE toOpaque()
    throws SQLException;
}