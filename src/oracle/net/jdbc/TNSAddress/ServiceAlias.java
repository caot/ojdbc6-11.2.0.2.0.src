package oracle.net.jdbc.TNSAddress;

import oracle.net.jdbc.nl.NLException;
import oracle.net.jdbc.nl.NVFactory;
import oracle.net.jdbc.nl.NVPair;

public class ServiceAlias
  implements SchemaObject
{
  protected SchemaObject child;
  public String name;
  private SchemaObjectFactoryInterface f = null;

  public ServiceAlias(SchemaObjectFactoryInterface paramSchemaObjectFactoryInterface)
  {
    this.f = paramSchemaObjectFactoryInterface;
  }

  public int isA()
  {
    return 4;
  }

  public String isA_String()
  {
    return null;
  }

  public void initFromString(String paramString)
    throws NLException, SOException
  {
    if (paramString.charAt(0) != '(')
      paramString = "(" + paramString + ")";
    NVPair localNVPair = new NVFactory().createNVPair(paramString);
    initFromNVPair(localNVPair);
  }

  public void initFromNVPair(NVPair paramNVPair)
    throws SOException
  {
    if (paramNVPair.getListSize() != 1)
      throw new SOException();
    NVPair localNVPair = paramNVPair.getListElement(0);
    if (localNVPair.getName().equalsIgnoreCase("DESCRIPTION_LIST"))
      this.child = this.f.create(3);
    else if (localNVPair.getName().equalsIgnoreCase("DESCRIPTION"))
      this.child = this.f.create(2);
    else if (localNVPair.getName().equalsIgnoreCase("ADDRESS_LIST"))
      this.child = this.f.create(1);
    else if (localNVPair.getName().equalsIgnoreCase("ADDRESS"))
      this.child = this.f.create(0);
    else
      throw new SOException();
    this.child.initFromNVPair(localNVPair);
    this.name = paramNVPair.getName();
  }

  public String toString()
  {
    return this.name + "=" + this.child.toString();
  }
}