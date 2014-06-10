package oracle.net.jdbc.TNSAddress;

import java.util.Vector;
import oracle.net.jdbc.nl.NLException;
import oracle.net.jdbc.nl.NVFactory;
import oracle.net.jdbc.nl.NVPair;

public class DescriptionList
  implements SchemaObject
{
  public Vector children = new Vector();
  private SchemaObject child;
  private NVPair childnv;
  public boolean sourceRoute = false;
  public boolean loadBalance = true;
  public boolean failover = true;
  protected SchemaObjectFactoryInterface f = null;

  public DescriptionList(SchemaObjectFactoryInterface paramSchemaObjectFactoryInterface)
  {
    this.f = paramSchemaObjectFactoryInterface;
  }

  public int isA()
  {
    return 3;
  }

  public String isA_String()
  {
    return "DESCRIPTION_LIST";
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
      else if (this.childnv.getName().equalsIgnoreCase("DESCRIPTION"))
      {
        this.child = this.f.create(2);
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
    String str1 = new String("");
    if (this.children.size() < 1)
      return str1;
    String str2 = new String("");
    for (int i = 0; i < this.children.size(); i++)
    {
      str2 = ((SchemaObject)this.children.elementAt(i)).toString();
      if (!str2.equals(""))
        str1 = str1 + str2;
    }
    if ((str1.equals("")) && (this.sourceRoute))
      str1 = str1 + "(SOURCE_ROUTE=yes)";
    if ((str1.equals("")) && (!this.loadBalance))
      str1 = str1 + "(LOAD_BALANCE=no)";
    if ((str1.equals("")) && (!this.failover))
      str1 = str1 + "(FAILOVER=false)";
    if (!str1.equals(""))
      str1 = "(DESCRIPTION_LIST=" + str1 + ")";
    return str1;
  }

  protected void init()
  {
    this.children.removeAllElements();
    this.child = null;
    this.childnv = null;
    this.sourceRoute = false;
    this.loadBalance = true;
    this.failover = true;
  }
}