package oracle.net.nt;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.security.Principal;
import java.security.cert.X509Certificate;
import java.util.Properties;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import oracle.net.jdbc.nl.NLException;
import oracle.net.ns.NetException;

public class TcpsNTAdapter extends TcpNTAdapter
{
  String matchSSLServerCertDNWith;
  boolean fullDNMatch;
  SSLSocketFactory l_sslSockFac;
  Socket underlyingSocket = null;
  SSLSocket socketWithListener;

  public TcpsNTAdapter(String paramString, Properties paramProperties)
    throws NLException
  {
    super(paramString, paramProperties);
  }

  public void connect()
    throws IOException
  {
    this.l_sslSockFac = CustomSSLSocketFactory.getSSLSocketFactory(this.socketOptions);

    this.underlyingSocket = new Socket();
    InetSocketAddress localInetSocketAddress = new InetSocketAddress(this.host, this.port);

    String str1 = (String)this.socketOptions.get(Integer.valueOf(2));
    try
    {
      int i = str1 == null ? 0 : Integer.parseInt(str1);

      this.underlyingSocket.connect(localInetSocketAddress, i);

      this.socket = this.l_sslSockFac.createSocket(this.underlyingSocket, this.host, this.port, true);
      String str2;
      if ((str2 = (String)this.socketOptions.get(Integer.valueOf(3))) != null)
      {
        setOption(3, str2);
      }

      setSSLSocketOptions();
    } catch (NumberFormatException localNumberFormatException) {
      throw new NetException(505);
    }
  }

  public void renegotiateSession()
    throws IOException
  {
    this.socketWithListener = ((SSLSocket)this.socket);

    this.socket = this.l_sslSockFac.createSocket(this.underlyingSocket, this.host, this.port, true);
    String str;
    if ((str = (String)this.socketOptions.get(Integer.valueOf(3))) != null)
    {
      setOption(3, str);
    }

    setSSLSocketOptions();
  }

  public void setSSLSocketOptions()
    throws IOException
  {
    super.setSocketOptions();
    SSLSocket localSSLSocket = (SSLSocket)this.socket;

    localSSLSocket.setUseClientMode(true);

    TcpsConfigure.configureVersion(localSSLSocket, (String)this.socketOptions.get(Integer.valueOf(6)));

    TcpsConfigure.configureCipherSuites(localSSLSocket, (String)this.socketOptions.get(Integer.valueOf(7)));
  }

  public void setOption(int paramInt, Object paramObject)
    throws IOException, NetException
  {
    SSLSocket localSSLSocket = (SSLSocket)this.socket;
    switch (paramInt)
    {
    case 8:
      setServerDNMatchValue((String[])paramObject);
      break;
    case 7:
      if (((String)paramObject).equalsIgnoreCase("TRUE"))
        this.fullDNMatch = true;
      else
        this.fullDNMatch = false;
      break;
    default:
      super.setOption(paramInt, paramObject);
    }
  }

  public Object getOption(int paramInt)
    throws IOException, NetException
  {
    SSLSocket localSSLSocket = (SSLSocket)this.socket;
    switch (paramInt)
    {
    case 2:
      String str1 = localSSLSocket.getSession().getCipherSuite();

      if ((str1 != null) && (str1.indexOf("NULL") == -1))
        return "TRUE";
      return "FALSE";
    case 5:
      return localSSLSocket.getSession().getCipherSuite();
    case 3:
      X509Certificate localX509Certificate1 = (X509Certificate)localSSLSocket.getSession().getPeerCertificates()[0];

      return localX509Certificate1.getSubjectDN().getName();
    case 4:
      return localSSLSocket.getSession().getPeerCertificateChain();
    case 6:
      String str2 = (String)this.socketOptions.get(Integer.valueOf(4));

      if (str2 == null) {
        str2 = System.getProperty("oracle.net.ssl_server_dn_match", "false");
      }

      if ((str2.equalsIgnoreCase("YES")) || (str2.equalsIgnoreCase("ON")) || (str2.equalsIgnoreCase("TRUE")))
      {
        X509Certificate localX509Certificate2 = (X509Certificate)localSSLSocket.getSession().getPeerCertificates()[0];

        String str3 = localX509Certificate2.getSubjectDN().getName();

        if (TcpsConfigure.matchServerDN(str3, this.matchSSLServerCertDNWith, this.fullDNMatch))
        {
          return "TRUE";
        }
        return "FALSE";
      }
      return "TRUE";
    }

    return super.getOption(paramInt);
  }

  public void setServerDNMatchValue(String[] paramArrayOfString)
  {
    String str1 = paramArrayOfString[0];
    String str2 = paramArrayOfString[1];
    String str3 = paramArrayOfString[2];
    if (str1 != null)
    {
      this.matchSSLServerCertDNWith = str1;
      this.fullDNMatch = true;
    }
    else if (str2 != null)
    {
      if (str2.indexOf(46) != -1) {
        this.matchSSLServerCertDNWith = ("CN=" + str2.substring(0, str2.indexOf(46)));
      }
      else
        this.matchSSLServerCertDNWith = ("CN=" + str2.trim());
    } else if (str3 != null)
    {
      if (str3.indexOf(46) != -1) {
        this.matchSSLServerCertDNWith = ("CN=" + str3.substring(0, str3.indexOf(46)));
      }
      else
        this.matchSSLServerCertDNWith = ("CN=" + str3.trim());
    }
  }
}