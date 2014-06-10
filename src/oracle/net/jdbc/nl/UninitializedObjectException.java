package oracle.net.jdbc.nl;

public class UninitializedObjectException extends NLException
{
  public UninitializedObjectException(String paramString)
  {
    super(paramString);
  }

  public UninitializedObjectException(String paramString, Object paramObject)
  {
    super(paramString, paramObject);
  }

  public UninitializedObjectException(String paramString, Object[] paramArrayOfObject)
  {
    super(paramString, paramArrayOfObject);
  }
}