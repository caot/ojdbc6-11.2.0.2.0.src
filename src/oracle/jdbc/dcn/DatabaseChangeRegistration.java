package oracle.jdbc.dcn;

import java.sql.SQLException;
import java.util.concurrent.Executor;
import oracle.jdbc.NotificationRegistration;

public abstract interface DatabaseChangeRegistration extends NotificationRegistration
{
  /** @deprecated */
  public abstract int getRegistrationId();

  public abstract long getRegId();

  public abstract String[] getTables();

  public abstract void addListener(DatabaseChangeListener paramDatabaseChangeListener)
    throws SQLException;

  public abstract void addListener(DatabaseChangeListener paramDatabaseChangeListener, Executor paramExecutor)
    throws SQLException;

  public abstract void removeListener(DatabaseChangeListener paramDatabaseChangeListener)
    throws SQLException;
}