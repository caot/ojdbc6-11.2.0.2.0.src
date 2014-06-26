package oracle.net.jndi;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import javax.net.SocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class CustomSSLSocketFactory extends SSLSocketFactory
{
  private static final boolean DEBUG = false;
  private static SSLSocketFactory ossl = null;

  protected static boolean isFactorySet()
  {
    return null != ossl;
  }

  protected static void setFactory(SSLSocketFactory paramSSLSocketFactory)
  {
    if (null == ossl)
      ossl = paramSSLSocketFactory;
  }

  protected void setDefaultFactory()
  {
    setFactory((SSLSocketFactory)SSLSocketFactory.getDefault());
  }

  public static SocketFactory getDefault()
  {
    SocketFactory localObject = null;
    try
    {
      localObject = new TrustManagerSSLSocketFactory();
    }
    catch (Exception localException)
    {
      localObject = new CustomSSLSocketFactory();
    }

    ((CustomSSLSocketFactory)localObject).setDefaultFactory();
    return localObject;
  }

  public Socket createSocket(String paramString, int paramInt)
    throws IOException
  {
    SSLSocket localSSLSocket = (SSLSocket)ossl.createSocket(paramString, paramInt);
    return init(localSSLSocket);
  }

  public Socket createSocket(InetAddress paramInetAddress, int paramInt)
    throws IOException
  {
    SSLSocket localSSLSocket = (SSLSocket)ossl.createSocket(paramInetAddress, paramInt);
    return init(localSSLSocket);
  }

  public Socket createSocket(String paramString, int paramInt1, InetAddress paramInetAddress, int paramInt2)
    throws IOException
  {
    SSLSocket localSSLSocket = (SSLSocket)ossl.createSocket(paramString, paramInt1, paramInetAddress, paramInt2);
    return init(localSSLSocket);
  }

  public Socket createSocket(InetAddress paramInetAddress1, int paramInt1, InetAddress paramInetAddress2, int paramInt2)
    throws IOException
  {
    SSLSocket localSSLSocket = (SSLSocket)ossl.createSocket(paramInetAddress1, paramInt1, paramInetAddress2, paramInt2);
    return init(localSSLSocket);
  }

  public Socket createSocket(Socket paramSocket, String paramString, int paramInt, boolean paramBoolean)
    throws IOException
  {
    SSLSocket localSSLSocket = (SSLSocket)ossl.createSocket(paramSocket, paramString, paramInt, paramBoolean);
    return init(localSSLSocket);
  }

  public String[] getDefaultCipherSuites()
  {
    return ossl.getDefaultCipherSuites();
  }

  public String[] getSupportedCipherSuites()
  {
    return ossl.getSupportedCipherSuites();
  }

  private SSLSocket init(SSLSocket paramSSLSocket)
    throws IOException
  {
    paramSSLSocket.setUseClientMode(true);

    String[] arrayOfString = { "SSL_DH_anon_WITH_3DES_EDE_CBC_SHA" };

    paramSSLSocket.setEnabledCipherSuites(arrayOfString);
    paramSSLSocket.startHandshake();
    return paramSSLSocket;
  }
}