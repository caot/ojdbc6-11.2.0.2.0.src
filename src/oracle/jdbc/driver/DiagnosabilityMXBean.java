package oracle.jdbc.driver;

public abstract interface DiagnosabilityMXBean
{
  public abstract boolean stateManageable();

  public abstract boolean statisticsProvider();

  public abstract boolean getLoggingEnabled();

  public abstract void setLoggingEnabled(boolean paramBoolean);
}