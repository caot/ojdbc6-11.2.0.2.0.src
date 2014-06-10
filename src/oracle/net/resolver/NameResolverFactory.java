package oracle.net.resolver;

import java.util.HashMap;
import oracle.net.ns.NetException;

public class NameResolverFactory
{
  private static HashMap resolverMap = new HashMap();
  private static final String TNS_ADMIN_PROPERTY = "oracle.net.tns_admin";
  private static final boolean DEBUG = false;

  public static NameResolver getNameResolver(String paramString1, String paramString2, String paramString3)
    throws NetException
  {
    if (paramString1 != null) {
      paramString1 = paramString1.trim();
    }

    if ((paramString1 != null) && (paramString1.length() == 0)) {
      throw new NetException(119);
    }

    synchronized (NameResolverFactory.class)
    {
      NameResolver localNameResolver = (NameResolver)resolverMap.get(paramString1);
      if (localNameResolver == null)
      {
        localNameResolver = new NameResolver(paramString1, paramString2, paramString3);
        resolverMap.put(paramString1, localNameResolver);
      }

      return localNameResolver;
    }
  }
}