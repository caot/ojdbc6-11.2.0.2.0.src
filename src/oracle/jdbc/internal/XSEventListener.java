package oracle.jdbc.internal;

import java.util.EventListener;

public abstract interface XSEventListener extends EventListener
{
  public abstract void onXSEvent(XSEvent paramXSEvent);
}