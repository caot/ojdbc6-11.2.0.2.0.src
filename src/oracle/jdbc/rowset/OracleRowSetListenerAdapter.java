package oracle.jdbc.rowset;

import java.io.Serializable;
import javax.sql.RowSetEvent;
import javax.sql.RowSetListener;

public abstract class OracleRowSetListenerAdapter
  implements RowSetListener, Serializable
{
  public void cursorMoved(RowSetEvent paramRowSetEvent)
  {
  }

  public void rowChanged(RowSetEvent paramRowSetEvent)
  {
  }

  public void rowSetChanged(RowSetEvent paramRowSetEvent)
  {
  }
}