package oracle.net.jdbc.TNSAddress;

import java.util.Vector;
import oracle.net.jdbc.nl.NLException;
import oracle.net.jdbc.nl.NVFactory;
import oracle.net.jdbc.nl.NVPair;

public class AddressList
  implements SchemaObject
{
  public Vector children = new Vector();
  private SchemaObject child;
  private NVPair childnv;
  public boolean sourceRoute = false;
  public boolean loadBalance = false;
  public boolean failover = true;
  protected SchemaObjectFactoryInterface f = null;

  public AddressList(SchemaObjectFactoryInterface paramSchemaObjectFactoryInterface)
  {
    this.f = paramSchemaObjectFactoryInterface;
  }

  public int isA()
  {
    return 1;
  }

  public String isA_String()
  {
    return "ADDRESS_LIST";
  }

  public void initFromString(String paramString)
    throws NLException, SOException
  {
    NVPair localNVPair = new NVFactory().createNVPair(paramString);
    initFromNVPair(localNVPair);
  }

  public void initFromNVPair(NVPair paramNVPair)
    throws SOException
  {
    init();
    int i = paramNVPair.getListSize();
    if (i == 0)
      throw new SOException();
    for (int j = 0; j < i; j++)
    {
      this.childnv = paramNVPair.getListElement(j);
      if (this.childnv.getName().equalsIgnoreCase("SOURCE_ROUTE"))
      {
        this.sourceRoute = ((this.childnv.getAtom().equalsIgnoreCase("yes")) || (this.childnv.getAtom().equalsIgnoreCase("on")) || (this.childnv.getAtom().equalsIgnoreCase("true")));
      }
      else if (this.childnv.getName().equalsIgnoreCase("LOAD_BALANCE"))
      {
        this.loadBalance = ((this.childnv.getAtom().equalsIgnoreCase("yes")) || (this.childnv.getAtom().equalsIgnoreCase("on")) || (this.childnv.getAtom().equalsIgnoreCase("true")));
      }
      else if (this.childnv.getName().equalsIgnoreCase("FAILOVER"))
      {
        this.failover = ((this.childnv.getAtom().equalsIgnoreCase("yes")) || (this.childnv.getAtom().equalsIgnoreCase("on")) || (this.childnv.getAtom().equalsIgnoreCase("true")));
      }
      else if (this.childnv.getName().equalsIgnoreCase("ADDRESS"))
      {
        this.child = this.f.create(0);
        this.child.initFromNVPair(this.childnv);
        this.children.addElement(this.child);
      }
      else if (this.childnv.getName().equalsIgnoreCase("ADDRESS_LIST"))
      {
        this.child = this.f.create(1);
        this.child.initFromNVPair(this.childnv);
        this.children.addElement(this.child);
      }
      else
      {
        throw new SOException();
      }
    }
    if (this.children.size() == 0)
      throw new SOException();
  }

  public String toString()
  {
    String str = new String("");
    if (this.children.size() < 1)
      return str;
    str = str + "(ADDRESS_LIST=";
    for (int i = 0; i < this.children.size(); i++)
      str = str + ((SchemaObject)this.children.elementAt(i)).toString();
    if (this.sourceRoute)
      str = str + "(SOURCE_ROUTE=yes)";
    if (this.loadBalance)
      str = str + "(LOAD_BALANCE=yes)";
    if (!this.failover)
      str = str + "(FAILOVER=false)";
    str = str + ")";
    return str;
  }

  protected void init()
  {
    this.children.removeAllElements();
    this.child = null;
    this.childnv = null;
    this.sourceRoute = false;
    this.loadBalance = false;
    this.failover = true;
  }
}