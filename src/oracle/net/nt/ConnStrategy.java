package oracle.net.nt;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;
import oracle.net.ns.NetException;

public class ConnStrategy
{
  static final boolean DEBUG = false;
  private boolean optFound = false;
  public boolean reuseOpt = false;
  private ConnOption copt;
  public int sdu;
  public int tdu;
  public int nextOptToTry;
  public Properties socketOptions = new Properties();
  private String osuser;
  private String programName;
  public int connectTimeout = -1;
  public int retryCount = 1;

  public Vector cOpts = new Vector(10, 10);

  public ConnStrategy(Properties paramProperties)
  {
    this.nextOptToTry = 0;
    this.osuser = paramProperties.getProperty("oracle.jdbc.v$session.osuser");
    this.programName = paramProperties.getProperty("oracle.jdbc.v$session.program");
    createSocketOptions(paramProperties);
  }

  public String getOSUsername()
  {
    return this.osuser;
  }

  public String getProgramName() {
    return this.programName;
  }

  public void createSocketOptions(Properties paramProperties) {
    String str1 = null;
    String str2 = null;
    int i = 0;

    Enumeration localEnumeration = paramProperties.keys();
    while (localEnumeration.hasMoreElements())
    {
      str1 = (String)localEnumeration.nextElement();

      if (str1.equalsIgnoreCase("TCP.NODELAY"))
      {
        i = 1;
        str2 = paramProperties.getProperty("TCP.NODELAY").toUpperCase();
        if (str2.equals("NO"))
          this.socketOptions.put(Integer.valueOf(0), "NO");
        else
          this.socketOptions.put(Integer.valueOf(0), "YES");
      }
      else if (str1.equalsIgnoreCase("oracle.net.READ_TIMEOUT"))
      {
        str2 = paramProperties.getProperty("oracle.net.READ_TIMEOUT");
        this.socketOptions.put(Integer.valueOf(3), str2);
      }
      else if (str1.equalsIgnoreCase("oracle.net.CONNECT_TIMEOUT"))
      {
        str2 = paramProperties.getProperty("oracle.net.CONNECT_TIMEOUT");
        this.socketOptions.put(Integer.valueOf(2), str2);
      }
      else if (str1.equalsIgnoreCase("oracle.net.ssl_server_dn_match"))
      {
        str2 = paramProperties.getProperty("oracle.net.ssl_server_dn_match");
        this.socketOptions.put(Integer.valueOf(4), str2);
      }
      else if (str1.equalsIgnoreCase("oracle.net.wallet_location"))
      {
        str2 = paramProperties.getProperty("oracle.net.wallet_location");
        this.socketOptions.put(Integer.valueOf(5), str2);
      }
      else if (str1.equalsIgnoreCase("oracle.net.wallet_password"))
      {
        str2 = paramProperties.getProperty("oracle.net.wallet_password");
        this.socketOptions.put(Integer.valueOf(16), str2);
      }
      else if (str1.equalsIgnoreCase("oracle.net.ssl_version"))
      {
        str2 = paramProperties.getProperty("oracle.net.ssl_version");
        this.socketOptions.put(Integer.valueOf(6), str2);
      }
      else if (str1.equalsIgnoreCase("oracle.net.ssl_cipher_suites"))
      {
        str2 = paramProperties.getProperty("oracle.net.ssl_cipher_suites");
        this.socketOptions.put(Integer.valueOf(7), str2);
      }
      else if (str1.equalsIgnoreCase("javax.net.ssl.keyStore"))
      {
        str2 = paramProperties.getProperty("javax.net.ssl.keyStore");
        this.socketOptions.put(Integer.valueOf(8), str2);
      }
      else if (str1.equalsIgnoreCase("javax.net.ssl.keyStoreType"))
      {
        str2 = paramProperties.getProperty("javax.net.ssl.keyStoreType");
        this.socketOptions.put(Integer.valueOf(9), str2);
      }
      else if (str1.equalsIgnoreCase("javax.net.ssl.keyStorePassword"))
      {
        str2 = paramProperties.getProperty("javax.net.ssl.keyStorePassword");

        this.socketOptions.put(Integer.valueOf(10), str2);
      }
      else if (str1.equalsIgnoreCase("javax.net.ssl.trustStore"))
      {
        str2 = paramProperties.getProperty("javax.net.ssl.trustStore");
        this.socketOptions.put(Integer.valueOf(11), str2);
      }
      else if (str1.equalsIgnoreCase("javax.net.ssl.trustStoreType"))
      {
        str2 = paramProperties.getProperty("javax.net.ssl.trustStoreType");

        this.socketOptions.put(Integer.valueOf(12), str2);
      }
      else if (str1.equalsIgnoreCase("javax.net.ssl.trustStorePassword"))
      {
        str2 = paramProperties.getProperty("javax.net.ssl.trustStorePassword");

        this.socketOptions.put(Integer.valueOf(13), str2);
      }
      else if (str1.equalsIgnoreCase("ssl.keyManagerFactory.algorithm"))
      {
        str2 = paramProperties.getProperty("ssl.keyManagerFactory.algorithm");

        this.socketOptions.put(Integer.valueOf(14), str2);
      }
      else if (str1.equalsIgnoreCase("FORCE_DNS_LOAD_BALANCING"))
      {
        str2 = paramProperties.getProperty("FORCE_DNS_LOAD_BALANCING");

        this.socketOptions.put(Integer.valueOf(18), str2);
      }
      else if (str1.equalsIgnoreCase("oracle.net.SDP"))
      {
        str2 = paramProperties.getProperty("oracle.net.SDP");
        this.socketOptions.put(Integer.valueOf(19), str2);
      }

    }

    if ((i == 0) && (!this.reuseOpt))
      this.socketOptions.put(Integer.valueOf(0), "YES");
  }

  public void addSocketOptions(boolean paramBoolean)
  {
    if (paramBoolean)
      this.socketOptions.put(Integer.valueOf(1), "YES");
    else if (!this.reuseOpt)
      this.socketOptions.put(Integer.valueOf(1), "NO");
  }

  public void addOption(ConnOption paramConnOption)
  {
    this.cOpts.addElement(paramConnOption);
  }

  public boolean hasMoreOptions()
  {
    if (this.nextOptToTry < this.cOpts.size()) {
      return true;
    }
    return false;
  }

  public ConnOption execute()
    throws NetException
  {
    Object localObject = null;

    if (this.connectTimeout == -1)
    {
      if (this.socketOptions.get(Integer.valueOf(2)) == null) {
        this.socketOptions.put(Integer.valueOf(2), Integer.toString(60000));
      }

    }
    else
    {
      this.socketOptions.put(Integer.valueOf(2), Integer.toString(this.connectTimeout));
    }

    this.socketOptions.put(Integer.valueOf(17), Integer.toString(this.retryCount));

    while (this.nextOptToTry < this.cOpts.size()) {
      try
      {
        this.copt = ((ConnOption)this.cOpts.elementAt(this.nextOptToTry));

        this.copt.connect(this.socketOptions);

        this.copt.sdu = this.sdu;
        this.copt.tdu = this.tdu;
        this.optFound = true;
        this.nextOptToTry += 1;
        return this.copt;
      }
      catch (IOException localIOException)
      {
        this.nextOptToTry += 1;
        localObject = localIOException;
      }

    }

    if (localObject == null) {
      throw new NetException(20);
    }
    throw ((NetException)new NetException(20).initCause(localObject));
  }

  public boolean optAvailable() {
    return this.optFound;
  }

  public void clearElements() {
    this.cOpts.removeAllElements();
  }

  public ConnOption getOption()
  {
    return this.copt;
  }
}