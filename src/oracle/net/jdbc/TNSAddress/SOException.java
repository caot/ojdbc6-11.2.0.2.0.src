package oracle.net.jdbc.TNSAddress;

public class SOException extends Throwable
{
  public String error = null;

  public SOException()
  {
  }

  public SOException(String paramString)
  {
    super(paramString);
    this.error = paramString;
  }
}