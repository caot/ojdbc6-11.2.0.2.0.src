package oracle.jdbc.internal;

public abstract interface ClientDataSupport
{
  public abstract Object getClientData(Object paramObject);

  public abstract Object setClientData(Object paramObject1, Object paramObject2);

  public abstract Object removeClientData(Object paramObject);
}