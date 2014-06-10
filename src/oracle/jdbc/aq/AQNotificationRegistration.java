package oracle.jdbc.aq;

import java.sql.SQLException;
import java.util.concurrent.Executor;
import oracle.jdbc.NotificationRegistration;

public abstract interface AQNotificationRegistration extends NotificationRegistration
{
  public abstract void addListener(AQNotificationListener paramAQNotificationListener)
    throws SQLException;

  public abstract void addListener(AQNotificationListener paramAQNotificationListener, Executor paramExecutor)
    throws SQLException;

  public abstract void removeListener(AQNotificationListener paramAQNotificationListener)
    throws SQLException;

  public abstract String getQueueName();
}