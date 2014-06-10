package oracle.net.resolver;

import java.util.Vector;
import oracle.net.jdbc.TNSAddress.AddressList;
import oracle.net.jdbc.TNSAddress.SchemaObjectFactoryInterface;
import oracle.net.nt.ConnOption;
import oracle.net.nt.ConnStrategy;

public class NavAddressList extends AddressList
  implements NavSchemaObject
{
  private Vector activeChildren;
  private int sBuflength;

  public NavAddressList(SchemaObjectFactoryInterface paramSchemaObjectFactoryInterface)
  {
    super(paramSchemaObjectFactoryInterface);
    this.activeChildren = new Vector(1, 10);
  }

  public void navigate(ConnStrategy paramConnStrategy, StringBuffer paramStringBuffer)
  {
    navigate2(paramConnStrategy, paramStringBuffer, 0);
  }

  private void navigate2(ConnStrategy paramConnStrategy, StringBuffer paramStringBuffer, int paramInt)
  {
    int i = 0;

    paramInt++;
    this.sBuflength = paramStringBuffer.length();
    paramStringBuffer.append("(ADDRESS_LIST=");
    int j;
    if (this.sourceRoute)
    {
      this.activeChildren = this.children;
      ((NavSchemaObject)this.activeChildren.elementAt(0)).navigate(paramConnStrategy, paramStringBuffer);

      for (j = 1; j < this.activeChildren.size(); j++)
        ((NavSchemaObject)this.activeChildren.elementAt(j)).addToString(paramConnStrategy);
    }
    else
    {
      this.activeChildren = NavDescriptionList.setActiveChildren(this.children, this.failover, this.loadBalance);

      for (j = 0; j < this.activeChildren.size(); j++)
      {
        if (getChildrenType(j) == 1)
        {
          i = 1;
          ((NavAddressList)this.activeChildren.elementAt(j)).navigate2(paramConnStrategy, paramStringBuffer, paramInt);
        }
        else
        {
          ((NavSchemaObject)this.activeChildren.elementAt(j)).navigate(paramConnStrategy, paramStringBuffer);
        }
      }
    }

    paramInt--;

    if (((paramInt != 0) || (i == 0)) && (!this.sourceRoute))
    {
      closeNVPair(paramConnStrategy, false);
    }
    else
    {
      closeNVPair(paramConnStrategy, true);
    }
    paramStringBuffer.setLength(this.sBuflength);
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

  public String toString()
  {
    String str = "";
    str = str + "(ADDRESS_LIST=";

    for (int i = 0; i < this.children.size(); i++) {
      str = str + ((NavSchemaObject)this.children.elementAt(i)).toString();
    }
    if (this.sourceRoute) str = str + "(SOURCE_ROUTE=yes)(HOP_COUNT=0)";
    if (this.loadBalance) str = str + "(LOAD_BALANCE=yes)";
    if (!this.failover) str = str + "(FAILOVER=false)";

    str = str + ")";

    return str;
  }

  public int getChildrenSize()
  {
    return this.children.size();
  }

  public int getChildrenType(int paramInt)
  {
    return ((NavSchemaObject)this.children.elementAt(paramInt)).isA();
  }

  public NavAddress getChild(int paramInt)
  {
    return (NavAddress)this.children.elementAt(paramInt);
  }

  private void closeNVPair(ConnStrategy paramConnStrategy, boolean paramBoolean)
  {
    for (int i = paramConnStrategy.cOpts.size() - 1; 
      (i >= 0) && (!((ConnOption)paramConnStrategy.cOpts.elementAt(i)).done); i--)
    {
      if ((!paramBoolean) && (paramConnStrategy.cOpts.size() - 1 - i >= getChildrenSize())) {
        break;
      }
      if (this.sourceRoute)
      {
        ((ConnOption)paramConnStrategy.cOpts.elementAt(i)).conn_data.append("(SOURCE_ROUTE=yes)");
        ((ConnOption)paramConnStrategy.cOpts.elementAt(i)).conn_data.append("(HOP_COUNT=0)");
      }

      ((ConnOption)paramConnStrategy.cOpts.elementAt(i)).conn_data.append(")");
    }
  }
}