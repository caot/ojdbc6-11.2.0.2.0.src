package oracle.net.jdbc.nl;

public class InvalidSyntaxException extends NLException
{
  public InvalidSyntaxException(String paramString)
  {
    super(paramString);
  }

  public InvalidSyntaxException(String paramString, Object paramObject)
  {
    super(paramString, paramObject);
  }

  public InvalidSyntaxException(String paramString, Object[] paramArrayOfObject)
  {
    super(paramString, paramArrayOfObject);
  }
}