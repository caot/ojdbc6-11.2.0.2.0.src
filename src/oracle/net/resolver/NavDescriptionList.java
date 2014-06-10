package oracle.net.resolver;

import java.util.BitSet;
import java.util.Random;
import java.util.Vector;
import oracle.net.jdbc.TNSAddress.DescriptionList;
import oracle.net.jdbc.TNSAddress.SchemaObjectFactoryInterface;
import oracle.net.nt.ConnStrategy;

public class NavDescriptionList extends DescriptionList
  implements NavSchemaObject
{
  private Vector activeChildren;
  private int descProcessed;
  private boolean done;

  public NavDescriptionList(SchemaObjectFactoryInterface paramSchemaObjectFactoryInterface)
  {
    super(paramSchemaObjectFactoryInterface);
    this.activeChildren = new Vector(1, 10);
  }

  public void navigate(ConnStrategy paramConnStrategy, StringBuffer paramStringBuffer)
  {
    paramStringBuffer.append("(DESCRIPTION_LIST=");

    this.activeChildren = setActiveChildren(this.children, this.failover, this.loadBalance);
    while (this.descProcessed < this.activeChildren.size())
    {
      ((NavSchemaObject)this.activeChildren.elementAt(this.descProcessed)).navigate(paramConnStrategy, paramStringBuffer);

      this.descProcessed += 1;
    }
  }

  public void addToString(ConnStrategy paramConnStrategy)
  {
  }

  public static Vector setActiveChildren(Vector paramVector, boolean paramBoolean1, boolean paramBoolean2)
  {
    int j = paramVector.size();
    Vector localVector = new Vector(1, 10);
    Random localRandom = new Random();
    BitSet localBitSet = new BitSet(j);
    int i;
    if (paramBoolean1)
    {
      if (paramBoolean2)
      {
        for (int k = 0; k < j; k++)
        {
          do
          {
            i = Math.abs(localRandom.nextInt()) % j;
          }while (localBitSet.get(i));
          localBitSet.set(i);
          localVector.addElement(paramVector.elementAt(i));
        }
      }
      else
      {
        localVector = paramVector;
      }

    }
    else if (paramBoolean2)
    {
      i = Math.abs(localRandom.nextInt()) % j;
      localVector.addElement(paramVector.elementAt(i));
    }
    else
    {
      localVector.addElement(paramVector.elementAt(0));
    }

    return localVector;
  }
}