package oracle.jdbc.rowset;

import java.sql.SQLException;
import javax.sql.RowSet;
import javax.sql.rowset.Predicate;

public abstract interface OraclePredicate extends Predicate
{
  public abstract boolean evaluate(RowSet paramRowSet);

  public abstract boolean evaluate(Object paramObject, int paramInt)
    throws SQLException;

  public abstract boolean evaluate(Object paramObject, String paramString)
    throws SQLException;
}