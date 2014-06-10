package oracle.jdbc.aq;

import java.util.EventListener;

public abstract interface AQNotificationListener extends EventListener
{
  public abstract void onAQNotification(AQNotificationEvent paramAQNotificationEvent);
}