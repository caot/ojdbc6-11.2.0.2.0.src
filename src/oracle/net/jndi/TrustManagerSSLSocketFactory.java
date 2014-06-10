package oracle.net.jndi;

import javax.net.ssl.SSLContext;

public final class TrustManagerSSLSocketFactory extends CustomSSLSocketFactory
{
  private static final boolean DEBUG = false;

  protected void setDefaultFactory()
  {
    try
    {
      SSLContext localSSLContext = SSLContext.getInstance("SSL");
      localSSLContext.init(null, new javax.net.ssl.TrustManager[] { new TrustManager() }, null);

      setFactory(localSSLContext.getSocketFactory());
    }
    catch (Exception localException)
    {
      super.setDefaultFactory();
    }
  }
}