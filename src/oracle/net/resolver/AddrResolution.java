package oracle.net.resolver;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Vector;
import oracle.net.jdbc.TNSAddress.SOException;
import oracle.net.jdbc.nl.NLException;
import oracle.net.jdbc.nl.NVFactory;
import oracle.net.jdbc.nl.NVNavigator;
import oracle.net.jdbc.nl.NVPair;
import oracle.net.jndi.JndiAttrs;
import oracle.net.ns.NetException;
import oracle.net.nt.ConnOption;
import oracle.net.nt.ConnStrategy;

public class AddrResolution
{
  private ConnStrategy cs;
  private Properties up;
  private static final String default_proxy_rules = "__jdbc__";
  private static final String service_alias_name = "ora-net-service-alias";
  private static final String service_attr_name = "orclnetdescstring";
  private static final int length_of_alias_prefix = 6;
  public static final int DEFAULT_DATABASE_PORT = 1521;
  public static final String DEFAULT_CONNECT_PROTOCOL = "TCP";
  private boolean newSyntax = true;
  public boolean connection_revised = false;
  public boolean connection_redirected = false;
  private String TNSAddress;

  public AddrResolution(String paramString, Properties paramProperties)
    throws NetException
  {
    this.up = paramProperties;
    this.TNSAddress = paramString;

    if ((this.up.containsKey("java.naming.provider.url")) || (paramString.startsWith("ldap:")) || (paramString.startsWith("ldaps:")))
    {
      int i = 0;

      if ((paramString.startsWith("ldap:")) || (paramString.startsWith("ldaps:")))
      {
        if (paramString.indexOf(32) > 0)
        {
          i = 1;
        }
        else
        {
          int j;
          if ((j = paramString.lastIndexOf(47)) == -1) {
            throw new NetException(124);
          }
          this.up.put("java.naming.provider.url", paramString.substring(0, j));
          this.TNSAddress = paramString.substring(j + 1, paramString.length());
        }
      }

      if (i == 0)
      {
        String[] arrayOfString = new String[1];

        JndiAttrs localJndiAttrs = new JndiAttrs(this.up);
        arrayOfString[0] = "orclnetdescstring";
        Vector localVector = null;
        try
        {
          localVector = localJndiAttrs.getAttrs(this.TNSAddress, arrayOfString);
        }
        finally
        {
          localJndiAttrs.close();
        }
        this.TNSAddress = ((String)localVector.firstElement());
        this.connection_revised = true;
      }
      else
      {
        processLdapFailoverLoadblance(paramString);
      }
    }

    if (this.up.getProperty("oracle.net.oldSyntax") == "YES")
      this.newSyntax = false;
  }

  private void processLdapFailoverLoadblance(String paramString)
    throws NetException
  {
    int i = 0;

    Vector localVector = new Vector(10);

    char[] arrayOfChar = paramString.toCharArray();
    int k = arrayOfChar.length;
    String str1;
    for(; i < k;)
    {
      int j = i + 1;
      while ((j < k) && (arrayOfChar[j] != ' ')) j++;

      str1 = new String(arrayOfChar, i, j - i);

      if (str1.startsWith("ldap"))
      {
        localVector.addElement(str1);
      }
      else
      {
        throw new NetException(124);
      }

      i = j + 1;
      if ((i < k) && (arrayOfChar[i] == ' ')) i++;
    }

    if (localVector.size() <= 0) {
      throw new NetException(124);
    }

    boolean bool1 = true;
    boolean bool2 = true;
    String str2;
    if ((str2 = this.up.getProperty("oracle.net.ldap_failover")) != null)
    {
      if ((str2.equalsIgnoreCase("OFF")) || (str2.equalsIgnoreCase("FALSE")) || (str2.equalsIgnoreCase("NO")))
      {
        bool1 = false;
      }
    }
    if ((str2 = this.up.getProperty("oracle.net.ldap_loadbalance")) != null)
    {
      if ((str2.equalsIgnoreCase("OFF")) || (str2.equalsIgnoreCase("FALSE")) || (str2.equalsIgnoreCase("NO")))
      {
        bool2 = false;
      }
    }
    if (localVector.size() > 1)
    {
      localVector = NavDescriptionList.setActiveChildren(localVector, bool1, bool2);
    }

    StringBuffer localStringBuffer = new StringBuffer();

    int m = localVector.size();

    Hashtable localHashtable = new Hashtable(m);

    for (int n = 0; n < m; n++)
    {
      str1 = (String)localVector.elementAt(n);
      int i1;
      if ((i1 = str1.lastIndexOf(47)) == -1) {
        throw new NetException(124);
      }
      String str4 = str1.substring(0, i1);
      String localObject1 = str1.substring(i1 + 1, str1.length());

      localStringBuffer.append(str4);
      if (n < m - 1) localStringBuffer.append(' ');

      localHashtable.put(str4, localObject1);
    }

    String str3 = new String(localStringBuffer);

    this.up.put("java.naming.provider.url", str3);
    JndiAttrs localJndiAttrs = new JndiAttrs(this.up);

    String str4 = localJndiAttrs.getLdapUrlUsed();
    this.TNSAddress = ((String)localHashtable.get(str4));

    Object localObject1 = null;
    String[] arrayOfString = new String[1];
    arrayOfString[0] = "orclnetdescstring";
    try
    {
      localObject1 = localJndiAttrs.getAttrs(this.TNSAddress, arrayOfString);
    }
    finally
    {
      localJndiAttrs.close();
    }

    this.TNSAddress = ((String)((Vector)localObject1).firstElement());
    this.connection_revised = true;
  }

  public String getTNSAddress()
  {
    return this.TNSAddress.toUpperCase();
  }

  public ConnOption resolveAndExecute(String paramString)
    throws NetException, IOException
  {
    ConnStrategy localConnStrategy = this.cs;

    if (paramString != null)
    {
      this.cs = new ConnStrategy(this.up);

      if (this.connection_redirected)
      {
        this.cs.sdu = localConnStrategy.sdu;
        this.cs.tdu = localConnStrategy.tdu;

        this.cs.socketOptions = localConnStrategy.socketOptions;
        this.cs.reuseOpt = true;
        this.connection_redirected = false;
      }
      if (paramString.indexOf(41) == -1)
      {
        int i = 0;
        if (paramString.startsWith("//"))
          i = 2;
        if (paramString.charAt(i) == '[')
        {
          i = paramString.indexOf(93, i + 1);
        }

        if (((i = paramString.indexOf(58, i)) != -1) && (paramString.indexOf(58, i + 1) != -1))
        {
          resolveSimple(paramString);
        }
        else
        {
          String str = System.getProperty("oracle.net.tns_admin");
          NameResolver localNameResolver = NameResolverFactory.getNameResolver(str, this.cs.getOSUsername(), this.cs.getProgramName());
          resolveAddrTree(localNameResolver.resolveName(paramString));
        }

      }
      else if (this.newSyntax) {
        resolveAddrTree(paramString);
      } else {
        resolveAddr(paramString);
      }

    }
    else if ((this.cs == null) || (!this.cs.hasMoreOptions())) {
      return null;
    }

    return this.cs.execute();
  }

  private void resolveSimple(String paramString)
    throws NetException
  {
    ConnOption localConnOption = new ConnOption();
    int i = 0;
    int j = 0;
    int k = 0;

    int m = 0;
    int n = 0;
    if (paramString.startsWith("["))
    {
      m = paramString.indexOf(93);
      if (m == -1)
        throw new NetException(115);
      n = 1;
    }

    if (((i = paramString.indexOf(58, m)) == -1) || ((j = paramString.indexOf(58, i + 1)) == -1))
    {
      throw new NetException(115);
    }

    if ((k = paramString.indexOf(58, j + 1)) != -1)
    {
      throw new NetException(115);
    }

    try
    {
      if (n != 0)
        localConnOption.host = paramString.substring(1, i - 1);
      else
        localConnOption.host = paramString.substring(0, i);
      localConnOption.port = Integer.parseInt(paramString.substring(i + 1, j));
      localConnOption.addr = ("(ADDRESS=(PROTOCOL=tcp)(HOST=" + localConnOption.host + ")(PORT=" + localConnOption.port + "))");

      localConnOption.sid = paramString.substring(j + 1, paramString.length());
      String str = "(DESCRIPTION=(CONNECT_DATA=(SID=" + localConnOption.sid + ")(CID=(PROGRAM=" + this.cs.getProgramName() + ")(HOST=__jdbc__)(USER=" + this.cs.getOSUsername() + ")))" + "(ADDRESS=" + "(PROTOCOL=tcp)(HOST=" + localConnOption.host + ")(PORT=" + localConnOption.port + ")))";

      localConnOption.protocol = "TCP";
      localConnOption.conn_data = new StringBuffer(str);
      this.cs.addOption(localConnOption);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      throw new NetException(116);
    }
  }

  private void resolveAddr(String paramString)
    throws NetException
  {
    if (paramString.startsWith("alias=")) {
      String localObject = paramString;
      paramString = localObject.substring(localObject.indexOf("alias=") + 6, localObject.length());
    }

    Object localObject = new ConnOption();
    NVFactory localNVFactory = new NVFactory();
    NVNavigator localNVNavigator = new NVNavigator();
    NVPair localNVPair1 = null;
    NVPair localNVPair2 = null;
    try
    {
      localNVPair1 = localNVNavigator.findNVPairRecurse(localNVFactory.createNVPair(paramString), "CID");

      localNVPair2 = localNVNavigator.findNVPairRecurse(localNVFactory.createNVPair(paramString), "address");
    }
    catch (NLException localNLException1)
    {
      System.err.println(localNLException1.getMessage());
    }
    NVPair localNVPair3 = localNVNavigator.findNVPair(localNVPair2, "protocol");
    if (localNVPair3 == null) {
      throw new NetException(100);
    }

    ((ConnOption)localObject).protocol = localNVPair3.getAtom();
    if ((!((ConnOption)localObject).protocol.equals("TCP")) && (!((ConnOption)localObject).protocol.equals("tcp")) && (!((ConnOption)localObject).protocol.equals("SSL")) && (!((ConnOption)localObject).protocol.equals("ssl")) && (!((ConnOption)localObject).protocol.equals("ANO")) && (!((ConnOption)localObject).protocol.equals("ano")))
    {
      throw new NetException(102);
    }

    localNVPair3 = localNVNavigator.findNVPair(localNVPair2, "Host");
    if (localNVPair3 == null) {
      throw new NetException(103);
    }
    ((ConnOption)localObject).host = localNVPair3.getAtom();

    localNVPair3 = localNVNavigator.findNVPair(localNVPair2, "Port");
    if (localNVPair3 == null) {
      throw new NetException(104);
    }
    ((ConnOption)localObject).port = Integer.parseInt(localNVPair3.getAtom());

    localNVPair3 = localNVNavigator.findNVPair(localNVPair2, "sduSize");
    if (localNVPair3 != null) {
      ((ConnOption)localObject).sdu = Integer.parseInt(localNVPair3.getAtom());
    }

    localNVPair3 = localNVNavigator.findNVPair(localNVPair2, "tduSize");
    if (localNVPair3 != null) {
      ((ConnOption)localObject).tdu = Integer.parseInt(localNVPair3.getAtom());
    }

    NVPair localNVPair4 = null;
    try
    {
      localNVPair4 = localNVNavigator.findNVPairRecurse(localNVFactory.createNVPair(paramString), "connect_data");
    }
    catch (NLException localNLException2)
    {
      System.err.println(localNLException2.getMessage());
    }

    StringBuffer localStringBuffer = new StringBuffer(paramString);
    ((ConnOption)localObject).conn_data = (localNVPair4 != null ? insertCID(paramString) : localStringBuffer);

    ((ConnOption)localObject).addr = ("(ADDRESS=(PROTOCOL=tcp)(HOST=" + ((ConnOption)localObject).host + ")(PORT=" + ((ConnOption)localObject).port + "))");

    this.cs.addOption((ConnOption)localObject);
  }

  private void resolveAddrTree(String paramString)
    throws NetException
  {
    NavSchemaObjectFactory localNavSchemaObjectFactory = new NavSchemaObjectFactory();
    NavServiceAlias localNavServiceAlias = (NavServiceAlias)localNavSchemaObjectFactory.create(4);
    try
    {
      String str = "alias=" + paramString;

      localNavServiceAlias.initFromString(str);
    }
    catch (NLException localNLException) {
      throw new NetException(501);
    }
    catch (SOException localSOException) {
      throw new NetException(502, localSOException.getMessage());
    }
    localNavServiceAlias.navigate(this.cs, null);
  }

  private StringBuffer insertCID(String paramString)
    throws NetException
  {
    NVFactory localNVFactory = new NVFactory();
    NVNavigator localNVNavigator = new NVNavigator();

    StringBuffer localStringBuffer = new StringBuffer(2048);
    NVPair localNVPair1 = null;
    NVPair localNVPair2 = null;
    NVPair localNVPair3 = null;
    NVPair localNVPair4 = null;
    NVPair localNVPair5 = null;
    try
    {
      localNVPair1 = localNVNavigator.findNVPairRecurse(localNVFactory.createNVPair(paramString), "description");

      localNVPair2 = localNVNavigator.findNVPairRecurse(localNVFactory.createNVPair(paramString), "address_list");

      localNVPair3 = localNVNavigator.findNVPairRecurse(localNVFactory.createNVPair(paramString), "address");

      localNVPair4 = localNVNavigator.findNVPairRecurse(localNVFactory.createNVPair(paramString), "connect_data");

      localNVPair5 = localNVNavigator.findNVPairRecurse(localNVFactory.createNVPair(paramString), "source_route");
    }
    catch (NLException localNLException)
    {
      System.err.println(localNLException.getMessage());
    }
    NVPair localNVPair6 = null;
    NVPair localNVPair7 = null;
    NVPair localNVPair8 = null;

    if (localNVPair4 != null) {
      localNVPair6 = localNVNavigator.findNVPair(localNVPair4, "SID");
      localNVPair7 = localNVNavigator.findNVPair(localNVPair4, "CID");
      localNVPair8 = localNVNavigator.findNVPair(localNVPair4, "SERVICE_NAME");
    }
    else {
      throw new NetException(105);
    }
    if ((localNVPair6 == null) && (localNVPair8 == null)) {
      throw new NetException(106);
    }
    localStringBuffer.append("(DESCRIPTION=");
    if ((localNVPair2 != null) && (localNVPair2.getListSize() > 0))
    {
      for (int i = 0; i < localNVPair2.getListSize(); i++)
      {
        NVPair localNVPair9 = localNVPair2.getListElement(i);
        localStringBuffer.append(localNVPair9.toString());
      }

    }
    else if (localNVPair3 != null)
      localStringBuffer.append(localNVPair3.toString());
    else {
      throw new NetException(107);
    }

    if (localNVPair8 != null) {
      localStringBuffer.append("(CONNECT_DATA=" + localNVPair8.toString() + "(CID=(PROGRAM=" + this.cs.getProgramName() + ")(HOST=__jdbc__)(USER=" + this.cs.getOSUsername() + ")))");
    }
    else {
      localStringBuffer.append("(CONNECT_DATA=" + localNVPair6.toString() + "(CID=(PROGRAM=" + this.cs.getProgramName() + ")(HOST=__jdbc__)(USER=" + this.cs.getOSUsername() + ")))");
    }

    if (localNVPair5 != null) {
      localStringBuffer.append(localNVPair5.toString());
    }
    localStringBuffer.append(")");
    return localStringBuffer;
  }

  public Properties getUp()
  {
    return this.up;
  }
}