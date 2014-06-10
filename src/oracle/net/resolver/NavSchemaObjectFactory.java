package oracle.net.resolver;

import oracle.net.jdbc.TNSAddress.SchemaObject;
import oracle.net.jdbc.TNSAddress.SchemaObjectFactoryInterface;

public class NavSchemaObjectFactory
  implements SchemaObjectFactoryInterface
{
  public SchemaObject create(int paramInt)
  {
    switch (paramInt)
    {
    case 0:
      return new NavAddress(this);
    case 1:
      return new NavAddressList(this);
    case 2:
      return new NavDescription(this);
    case 3:
      return new NavDescriptionList(this);
    case 4:
      return new NavServiceAlias(this);
    }
    return null;
  }
}