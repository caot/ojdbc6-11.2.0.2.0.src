package oracle.net.nt;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.Security;
import java.util.Properties;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import oracle.net.jdbc.nl.NVFactory;
import oracle.net.jdbc.nl.NVNavigator;
import oracle.net.jdbc.nl.NVPair;
import oracle.net.ns.NetException;

public class CustomSSLSocketFactory
{
  static final boolean DEBUG = false;
  public static final String DEFAULT_SSO_WALLET_FILE_NAME = "cwallet.sso";
  public static final String DEFAULT_PKCS12_WALLET_FILE_NAME = "ewallet.p12";
  public static final String SSO_WALLET_TYPE = "SSO";
  public static final String PKCS12_WALLET_TYPE = "PKCS12";
  public static final String SUPPORTED_METHOD_TYPE = "FILE";
  public static SSLSocketFactory defSSLFactory;
  public static String defPropString = getDefaultPropertiesString();
  public static boolean initDefFactory;

  public static SSLSocketFactory getSSLSocketFactory(Properties paramProperties)
    throws IOException
  {
    String str1;
    Object localObject2;
    Object localObject1 = localObject2 = str1 = null;
    String str2;
    Object localObject4;
    Object localObject3 = localObject4 = str2 = null;
    String str3 = null;
    String str4 = null;
    String str5 = null;
    String str6 = null;
    String str7 = null;
    String str8 = null;
    SSLSocketFactory localSSLSocketFactory = null;

    str7 = (String)paramProperties.get(Integer.valueOf(5));

    str8 = (String)paramProperties.get(Integer.valueOf(16));
    Object localObject5;
    if (str7 == null)
    {
      localObject1 = (String)paramProperties.get(Integer.valueOf(8));

      if (localObject1 != null)
      {
        str1 = (String)paramProperties.get(Integer.valueOf(9));

        if (str1 == null)
          str1 = KeyStore.getDefaultType();
        localObject2 = (String)paramProperties.get(Integer.valueOf(10));

        if (localObject2 == null) {
          localObject2 = "";
        }
        str3 = (String)paramProperties.get(Integer.valueOf(14));

        if (str3 == null) {
          str3 = Security.getProperty("ssl.keyManagerFactory.algorithm");
        }
        if (str3 == null) {
          str3 = KeyManagerFactory.getDefaultAlgorithm();
        }
      }
      localObject3 = (String)paramProperties.get(Integer.valueOf(11));

      if (localObject3 != null)
      {
        str2 = (String)paramProperties.get(Integer.valueOf(12));

        if (str2 == null) {
          str2 = KeyStore.getDefaultType();
        }
        localObject4 = (String)paramProperties.get(Integer.valueOf(13));

        if (localObject4 == null) {
          localObject4 = "";
        }
        str4 = (String)paramProperties.get(Integer.valueOf(15));

        if (str4 == null) {
          str4 = Security.getProperty("ssl.trustManagerFactory.algorithm");
        }
        if (str4 == null) {
          str4 = TrustManagerFactory.getDefaultAlgorithm();
        }
      }
      str5 = (String)localObject1 + "#" + str1 + "#" + (String)localObject2 + "#" + (String)localObject3 + "#" + str2 + "#" + (String)localObject4 + "#" + str3 + "#" + str4;
    }
    else
    {
      int i = 0;

      if (str7.startsWith("(")) {
        str6 = processWalletLocation(str7);
      } else if (str7.startsWith("file:"))
      {
        str6 = str7.substring("file:".length());
        localObject5 = new File(str6);
        if (!((File)localObject5).exists())
          throw new NetException(407, "Couldn't find file at " + str6);
        if (!((File)localObject5).isDirectory())
          i = 1;
      }
      else
      {
        throw new NetException(412, "Location: " + str7);
      }

      if (str8 == null)
      {
        if (i == 0) {
          localObject1 = str6 + System.getProperty("file.separator") + "cwallet.sso";
        }
        else {
          localObject1 = str6;
        }
        str1 = "SSO";
        localObject2 = "";
        str3 = KeyManagerFactory.getDefaultAlgorithm();

        localObject3 = localObject1;
        str2 = "SSO";
        localObject4 = "";
        str4 = TrustManagerFactory.getDefaultAlgorithm();
      }
      else
      {
        if (i == 0) {
          localObject1 = str6 + System.getProperty("file.separator") + "ewallet.p12";
        }
        else {
          localObject1 = str6;
        }
        str1 = "PKCS12";
        localObject2 = str8;
        str3 = KeyManagerFactory.getDefaultAlgorithm();

        localObject3 = localObject1;
        str2 = "PKCS12";
        localObject4 = str8;
        str4 = TrustManagerFactory.getDefaultAlgorithm();
      }

      str5 = str7 + "#" + str3 + "#" + str4;
    }
    Object localObject6;
    if (str5.equals(defPropString))
    {
      if (initDefFactory) {
        return defSSLFactory;
      }
      synchronized (CustomSSLSocketFactory.class)
      {
        if (!initDefFactory) {
          try
          {
            localObject5 = null;
            localObject6 = null;

            if (localObject1 != null) {
              localObject5 = getKeyManagerArray((String)localObject1, (String)localObject2, str1, str3);
            }

            if (localObject3 != null) {
              localObject6 = getTrustManagerArray((String)localObject3, (String)localObject4, str2, str4);
            }

            SSLContext localSSLContext = SSLContext.getInstance("SSL");
            localSSLContext.init((KeyManager[])localObject5, (TrustManager[])localObject6, null);
            defSSLFactory = localSSLContext.getSocketFactory();
            if (defSSLFactory != null) {
              initDefFactory = true;
            }

          }
          catch (Exception localException2)
          {
            throw ((NetException)new NetException(410).initCause(localException2));
          }
        }
        return defSSLFactory;
      }
    }
    try
    {
      ??? = null;
      TrustManager[] arrayOfTrustManager = null;

      if (localObject1 != null) {
        ??? = getKeyManagerArray((String)localObject1, (String)localObject2, str1, str3);
      }
      if (localObject3 != null) {
        arrayOfTrustManager = getTrustManagerArray((String)localObject3, (String)localObject4, str2, str4);
      }
      localObject6 = SSLContext.getInstance("SSL");
      ((SSLContext)localObject6).init((KeyManager[])???, arrayOfTrustManager, null);

      return ((SSLContext)localObject6).getSocketFactory();
    }
    catch (Exception localException1)
    {
      throw ((NetException)new NetException(410).initCause(localException1));
    }
  }

  public static KeyManager[] getKeyManagerArray(String paramString1, String paramString2, String paramString3, String paramString4)
    throws IOException
  {
    FileInputStream localFileInputStream = null;
    try
    {
      KeyStore localKeyStore = KeyStore.getInstance(paramString3);
      localFileInputStream = new FileInputStream(paramString1);

      localKeyStore.load(localFileInputStream, paramString2.toCharArray());
      KeyManagerFactory localKeyManagerFactory = KeyManagerFactory.getInstance(paramString4);

      localKeyManagerFactory.init(localKeyStore, paramString2.toCharArray());

      return localKeyManagerFactory.getKeyManagers();
    }
    catch (Exception localException)
    {
      throw ((NetException)new NetException(408).initCause(localException));
    } finally {
      if (localFileInputStream != null)
        localFileInputStream.close();
    }
  }

  public static TrustManager[] getTrustManagerArray(String paramString1, String paramString2, String paramString3, String paramString4)
    throws IOException
  {
    FileInputStream localFileInputStream = null;
    try
    {
      KeyStore localKeyStore = KeyStore.getInstance(paramString3);
      localFileInputStream = new FileInputStream(paramString1);

      localKeyStore.load(localFileInputStream, paramString2.toCharArray());
      TrustManagerFactory localTrustManagerFactory = TrustManagerFactory.getInstance(paramString4);

      localTrustManagerFactory.init(localKeyStore);
      return localTrustManagerFactory.getTrustManagers();
    }
    catch (Exception localException)
    {
      throw ((NetException)new NetException(409).initCause(localException));
    }
    finally {
      if (localFileInputStream != null)
        localFileInputStream.close();
    }
  }

  public static String processWalletLocation(String paramString)
    throws NetException
  {
    String str1 = null;
    try {
      NVNavigator localNVNavigator = new NVNavigator();
      NVPair localNVPair1 = new NVFactory().createNVPair(paramString);

      NVPair localNVPair2 = localNVNavigator.findNVPair(localNVPair1, "METHOD");
      NVPair localNVPair3 = localNVNavigator.findNVPair(localNVPair1, "METHOD_DATA");
      NVPair localNVPair4 = localNVNavigator.findNVPair(localNVPair3, "DIRECTORY");
      str1 = localNVPair2.getAtom();

      if (str1.equalsIgnoreCase("FILE")) {
        return localNVPair4.getAtom();
      }

      throw new NetException(412, str1);
    }
    catch (Exception localException)
    {
      throw ((NetException)new NetException(407).initCause(localException));
    }
  }

  public static String getDefaultPropertiesString()
  {
    Object localObject1 = null;
    String str1 = null;
    Object localObject2 = null;
    String str2 = null;
    String str3 = null;

    str1 = System.getProperty("oracle.net.wallet_location");

    if (str1 != null)
      localObject1 = str1;
    else {
      localObject1 = System.getProperty("javax.net.ssl.keyStore", "") + "#" + System.getProperty("javax.net.ssl.keyStoreType", KeyStore.getDefaultType()) + "#" + System.getProperty("javax.net.ssl.keyStorePassword", "") + "#" + System.getProperty("javax.net.ssl.trustStore", "") + "#" + System.getProperty("javax.net.ssl.trustStoreType", KeyStore.getDefaultType()) + "#" + System.getProperty("javax.net.ssl.trustStorePassword", "");
    }

    if (str2 == null)
      str2 = KeyManagerFactory.getDefaultAlgorithm();
    if (str3 == null)
      str3 = TrustManagerFactory.getDefaultAlgorithm();
    return (String)localObject1 + "#" + str2 + "#" + str3;
  }
}