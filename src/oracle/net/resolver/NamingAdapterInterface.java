package oracle.net.resolver;

import oracle.net.ns.NetException;

public abstract interface NamingAdapterInterface
{
  public static final boolean DEBUG = false;

  public abstract String resolve(String paramString)
    throws NetException;
}