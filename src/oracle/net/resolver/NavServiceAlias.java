package oracle.net.resolver;

import oracle.net.jdbc.TNSAddress.SchemaObjectFactoryInterface;
import oracle.net.jdbc.TNSAddress.ServiceAlias;
import oracle.net.nt.ConnStrategy;

public class NavServiceAlias extends ServiceAlias
  implements NavSchemaObject
{
  public NavServiceAlias(SchemaObjectFactoryInterface paramSchemaObjectFactoryInterface)
  {
    super(paramSchemaObjectFactoryInterface);
  }

  public void navigate(ConnStrategy paramConnStrategy, StringBuffer paramStringBuffer)
  {
    StringBuffer localStringBuffer = new StringBuffer("");
    ((NavSchemaObject)this.child).navigate(paramConnStrategy, localStringBuffer);
  }

  public void addToString(ConnStrategy paramConnStrategy)
  {
  }
}