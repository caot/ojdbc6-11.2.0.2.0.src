package oracle.jdbc.driver;

public abstract interface Message
{
  public abstract String msg(String paramString, Object paramObject);
}