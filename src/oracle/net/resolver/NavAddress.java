package oracle.net.resolver;

import java.util.Vector;
import oracle.net.jdbc.TNSAddress.Address;
import oracle.net.jdbc.TNSAddress.SchemaObjectFactoryInterface;
import oracle.net.nt.ConnOption;
import oracle.net.nt.ConnStrategy;

public class NavAddress extends Address
  implements NavSchemaObject
{
  public NavAddress(SchemaObjectFactoryInterface paramSchemaObjectFactoryInterface)
  {
    super(paramSchemaObjectFactoryInterface);
  }

  public void navigate(ConnStrategy paramConnStrategy, StringBuffer paramStringBuffer)
  {
    ConnOption localConnOption = new ConnOption();
    localConnOption.addr = this.addr;
    localConnOption.conn_data.append(paramStringBuffer.toString());
    localConnOption.conn_data.append(toString());

    paramConnStrategy.addOption(localConnOption);
  }

  public void addToString(ConnStrategy paramConnStrategy)
  {
    String str = toString();

    for (int i = paramConnStrategy.cOpts.size() - 1; 
      (i >= 0) && (!((ConnOption)paramConnStrategy.cOpts.elementAt(i)).done); i--)
    {
      ((ConnOption)paramConnStrategy.cOpts.elementAt(i)).conn_data.append(str);
    }
  }
}