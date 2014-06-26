package oracle.net.jdbc.TNSAddress;

import java.util.Vector;
import oracle.net.jdbc.nl.InvalidSyntaxException;
import oracle.net.jdbc.nl.NLException;
import oracle.net.jdbc.nl.NVFactory;
import oracle.net.jdbc.nl.NVPair;

public class Description
  implements SchemaObject
{
  public Vector children = new Vector();
  private SchemaObject child;
  private NVPair childnv;
  protected SchemaObjectFactoryInterface f = null;
  public boolean sourceRoute = false;
  public boolean loadBalance = false;
  public boolean failover = true;
  public boolean keepAlive = false;
  public String SDU;
  public String TDU;
  public Vector protocolStacks = new Vector();
  public String sendBufSize;
  public String receiveBufSize;
  public String connectData;
  public String SID;
  public String server;
  public String failoverMode;
  public String instanceRole;
  public String serviceName;
  public String instanceName;
  public String handlerName;
  public String oracleHome;
  public String connectTimeout;
  public String retryCount;
  public String authTypes;
  public String sslServerCertDN;
  public Vector authParams = new Vector();
  public Vector extraConnInfo = new Vector();
  public Vector extraInfo = new Vector();

  public Description(SchemaObjectFactoryInterface paramSchemaObjectFactoryInterface)
  {
    this.f = paramSchemaObjectFactoryInterface;
  }

  public int isA()
  {
    return 2;
  }

  public String isA_String()
  {
    return "DESCRIPTION";
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
      else if (this.childnv.getName().equalsIgnoreCase("ENABLE"))
      {
        this.keepAlive = this.childnv.getAtom().equalsIgnoreCase("broken");
      }
      else if (this.childnv.getName().equalsIgnoreCase("PROTOCOL_STACK"))
      {
        this.protocolStacks.addElement(this.childnv.toString());
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
      else if (this.childnv.getName().equalsIgnoreCase("SDU"))
      {
        this.SDU = this.childnv.getAtom();
      }
      else if (this.childnv.getName().equalsIgnoreCase("TDU"))
      {
        this.TDU = this.childnv.getAtom();
      }
      else if (this.childnv.getName().equalsIgnoreCase("SEND_BUF_SIZE"))
      {
        this.sendBufSize = this.childnv.getAtom();
      }
      else if (this.childnv.getName().equalsIgnoreCase("RECV_BUF_SIZE"))
      {
        this.receiveBufSize = this.childnv.getAtom();
      }
      else
      {
        int k;
        int m;
        NVPair localNVPair;
        if (this.childnv.getName().equalsIgnoreCase("CONNECT_DATA"))
        {
          this.connectData = this.childnv.valueToString();
          k = this.childnv.getListSize();
          if (k == 0)
            throw new SOException();
          for (m = 0; m < k; m++)
          {
            localNVPair = this.childnv.getListElement(m);
            if (localNVPair.getName().equalsIgnoreCase("SID"))
            {
              this.SID = localNVPair.getAtom();
            }
            else if (localNVPair.getName().equalsIgnoreCase("SERVER"))
            {
              this.server = localNVPair.getAtom();
            }
            else if (localNVPair.getName().equalsIgnoreCase("SERVICE_NAME"))
            {
              this.serviceName = localNVPair.getAtom();
            }
            else if (localNVPair.getName().equalsIgnoreCase("INSTANCE_NAME"))
            {
              this.instanceName = localNVPair.getAtom();
            }
            else if (localNVPair.getName().equalsIgnoreCase("HANDLER_NAME"))
            {
              this.handlerName = localNVPair.getAtom();
            }
            else if (localNVPair.getName().equalsIgnoreCase("ORACLE_HOME"))
            {
              this.oracleHome = localNVPair.getAtom();
            }
            else if (localNVPair.getName().equalsIgnoreCase("FAILOVER_MODE"))
            {
              this.failoverMode = this.childnv.getListElement(m).toString();
            }
            else if (localNVPair.getName().equalsIgnoreCase("INSTANCE_ROLE"))
            {
              this.instanceRole = localNVPair.getAtom();
            }
            else
            {
              String str2 = localNVPair.toString().trim();
              str2 = str2.substring(1, str2.length() - 1);
              this.extraConnInfo.addElement(str2);
            }
          }
        }
        else if (this.childnv.getName().equalsIgnoreCase("SECURITY"))
        {
          k = this.childnv.getListSize();
          if (k == 0)
            throw new SOException();
          for (m = 0; m < k; m++)
          {
            localNVPair = this.childnv.getListElement(m);
            if (localNVPair.getName().equalsIgnoreCase("AUTHENTICATION"))
              this.authTypes = localNVPair.toString();
            if (localNVPair.getName().equalsIgnoreCase("ssl_server_cert_dn"))
            {
              this.sslServerCertDN = localNVPair.getAtom();
              if ((this.sslServerCertDN != null) && (this.sslServerCertDN.startsWith("\"")) && (this.sslServerCertDN.endsWith("\"")))
                this.sslServerCertDN = this.sslServerCertDN.substring(1, this.sslServerCertDN.length() - 1);
            }
            else
            {
              this.authParams.addElement(localNVPair.toString());
            }
          }
        }
        else if (this.childnv.getName().equalsIgnoreCase("CONNECT_TIMEOUT"))
        {
          this.connectTimeout = this.childnv.getAtom();
        }
        else if (this.childnv.getName().equalsIgnoreCase("RETRY_COUNT"))
        {
          this.retryCount = this.childnv.getAtom();
        }
        else
        {
          if ((this.childnv.getName().equalsIgnoreCase("HS")) && (this.childnv.getAtom() == null))
            try
            {
              this.childnv.setAtom("OK");
            }
            catch (InvalidSyntaxException localInvalidSyntaxException)
            {
            }
          String str1 = this.childnv.toString().trim();
          str1 = str1.substring(1, str1.length() - 1);
          this.extraInfo.addElement(str1);
        }
      }
    }
  }

  public String toString()
  {
    String str1 = new String("");
    String str2 = new String("");
    for (int i = 0; i < this.children.size(); i++)
    {
      str2 = ((SchemaObject)this.children.elementAt(i)).toString();
      if (!str2.equals(""))
        str1 = str1 + str2;
    }
    if ((!str1.equals("")) && (this.sourceRoute))
      str1 = str1 + "(SOURCE_ROUTE=yes)";
    if ((!str1.equals("")) && (this.loadBalance))
      str1 = str1 + "(LOAD_BALANCE=yes)";
    if ((!str1.equals("")) && (!this.failover))
      str1 = str1 + "(FAILOVER=false)";
    if (this.keepAlive)
      str1 = str1 + "(ENABLE=broken)";
    if (this.SDU != null)
      str1 = str1 + "(SDU=" + this.SDU + ")";
    if (this.TDU != null)
      str1 = str1 + "(TDU=" + this.TDU + ")";
    if (this.sendBufSize != null)
      str1 = str1 + "(SEND_BUF_SIZE=" + this.sendBufSize + ")";
    if (this.receiveBufSize != null)
      str1 = str1 + "(RECV_BUF_SIZE=" + this.receiveBufSize + ")";
    if (this.protocolStacks.size() != 0)
      for (int i = 0; i < this.protocolStacks.size(); i++)
        str1 = str1 + (String)this.protocolStacks.elementAt(i);
    if ((this.SID != null) || (this.server != null) || (this.serviceName != null) || (this.instanceName != null) || (this.handlerName != null) || (this.extraConnInfo.size() != 0) || (this.oracleHome != null))
    {
      str1 = str1 + "(CONNECT_DATA=";
      if (this.SID != null)
        str1 = str1 + "(SID=" + this.SID + ")";
      if (this.server != null)
        str1 = str1 + "(SERVER=" + this.server + ")";
      if (this.serviceName != null)
        str1 = str1 + "(SERVICE_NAME=" + this.serviceName + ")";
      if (this.instanceName != null)
        str1 = str1 + "(INSTANCE_NAME=" + this.instanceName + ")";
      if (this.handlerName != null)
        str1 = str1 + "(HANDLER_NAME=" + this.handlerName + ")";
      if (this.oracleHome != null)
        str1 = str1 + "(ORACLE_HOME=" + this.oracleHome + ")";
      if (this.instanceRole != null)
        str1 = str1 + "(INSTANCE_ROLE=" + this.instanceRole + ")";
      if (this.failoverMode != null)
        str1 = str1 + this.failoverMode;
      for (int i = 0; i < this.extraConnInfo.size(); i++)
        str1 = str1 + "(" + (String)this.extraConnInfo.elementAt(i) + ")";
      str1 = str1 + ")";
    }
    if ((this.authTypes != null) || (this.authParams.size() != 0))
    {
      str1 = str1 + "(SECURITY=";
      if (this.authTypes != null)
        str1 = str1 + "(AUTHENTICATION=" + this.authTypes + ")";
      for (int i = 0; i < this.authParams.size(); i++)
        str1 = str1 + (String)this.authParams.elementAt(i);
      str1 = str1 + ")";
    }
    for (int i = 0; i < this.extraInfo.size(); i++)
      str1 = str1 + "(" + (String)this.extraInfo.elementAt(i) + ")";
    if (!str1.equals(""))
      str1 = "(DESCRIPTION=" + str1 + ")";
    return str1;
  }

  protected void init()
  {
    this.children.removeAllElements();
    this.child = null;
    this.childnv = null;
    this.sourceRoute = false;
    this.loadBalance = false;
    this.failover = true;
    this.keepAlive = false;
    this.protocolStacks.removeAllElements();
    this.SDU = null;
    this.TDU = null;
    this.SID = null;
    this.server = null;
    this.serviceName = null;
    this.instanceName = null;
    this.handlerName = null;
    this.oracleHome = null;
    this.authTypes = null;
    this.sendBufSize = null;
    this.receiveBufSize = null;
    this.failoverMode = null;
    this.instanceRole = null;
    this.authParams.removeAllElements();
    this.extraConnInfo.removeAllElements();
    this.extraInfo.removeAllElements();
  }
}