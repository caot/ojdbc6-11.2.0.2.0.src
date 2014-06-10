package oracle.jdbc;

import java.util.Properties;

public abstract interface NotificationRegistration
{
  public abstract Properties getRegistrationOptions();

  public abstract String getDatabaseName();

  public abstract String getUserName();

  public abstract RegistrationState getState();

  public static enum RegistrationState
  {
    ACTIVE, 

    CLOSED;
  }
}