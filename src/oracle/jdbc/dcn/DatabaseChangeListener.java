package oracle.jdbc.dcn;

import java.util.EventListener;

public abstract interface DatabaseChangeListener extends EventListener
{
  public abstract void onDatabaseChangeNotification(DatabaseChangeEvent paramDatabaseChangeEvent);
}