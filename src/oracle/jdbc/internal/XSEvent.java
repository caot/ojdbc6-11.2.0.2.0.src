package oracle.jdbc.internal;

import java.util.EventObject;

public abstract class XSEvent extends EventObject
{
  protected XSEvent(Object paramObject)
  {
    super(paramObject);
  }

  public abstract byte[] getSessionId();

  public abstract KeywordValueLong[] getDetails();

  public abstract int getFlags();
}