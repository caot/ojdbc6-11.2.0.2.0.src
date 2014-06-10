package oracle.net.nt;

import java.io.IOException;
import javax.net.ssl.SSLSocket;
import oracle.net.jdbc.nl.NLException;
import oracle.net.jdbc.nl.NVFactory;
import oracle.net.jdbc.nl.NVPair;
import oracle.net.ns.NetException;

public class TcpsConfigure
{
  static final boolean DEBUG = false;
  public static final String[] VALID_SSL_VERSION_STRINGS = { "0", "undetermined", "2", "2.0", "version 2", "3", "3.0", "version 3 only", "1", "1.0", "version 1 only", "1 or 3", "1.0 or 3.0", "version 1 or version 3" };

  public static final String[][] TABLE_ENABLED_SSL_PROTOCOLS = { { "TLSv1", "SSLv3", "SSLv2Hello" }, { "SSLv2Hello" }, { "SSLv3" }, { "TLSv1" }, { "TLSv1", "SSLv3" } };

  public static final int[] VALID_SSL_STRING_TO_PROTOCOLS_MAP = { 0, 0, 1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 4 };

  public static void configureVersion(SSLSocket paramSSLSocket, String paramString)
    throws NetException, IOException
  {
    if (paramString == null) {
      paramString = System.getProperty("oracle.net.ssl_version");
    }

    int i = 0;
    if (paramString != null)
    {
      if ((paramString.startsWith("(")) && (paramString.endsWith(")")))
        paramString = "(ssl_version=" + paramString.substring(1);
      else
        paramString = "(ssl_version=" + paramString + ")";
      try {
        NVPair localNVPair = new NVFactory().createNVPair(paramString);
        String str = localNVPair.getAtom();

        for (int j = 0; j < VALID_SSL_VERSION_STRINGS.length; j++)
        {
          if (str.equalsIgnoreCase(VALID_SSL_VERSION_STRINGS[j]))
          {
            i = j;
            break;
          }
        }
      }
      catch (NLException localNLException)
      {
        throw ((NetException)new NetException(400, paramString).initCause(localNLException));
      }
    }

    if (i >= VALID_SSL_VERSION_STRINGS.length) {
      throw new NetException(400);
    }
    String[] arrayOfString = TABLE_ENABLED_SSL_PROTOCOLS[VALID_SSL_STRING_TO_PROTOCOLS_MAP[i]];
    try
    {
      paramSSLSocket.setEnabledProtocols(arrayOfString);
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      throw ((NetException)new NetException(401).initCause(localIllegalArgumentException));
    }
  }

  public static void configureCipherSuites(SSLSocket paramSSLSocket, String paramString)
    throws NetException, IOException
  {
    if (paramString == null)
      paramString = System.getProperty("oracle.net.ssl_cipher_suites");
    if (paramString == null) {
      return;
    }

    if ((paramString.startsWith("(")) && (paramString.endsWith(")")))
      paramString = "(cipher_suites=" + paramString + ")";
    else
      paramString = "(cipher_suites=(" + paramString + "))";
    try {
      NVPair localNVPair = new NVFactory().createNVPair(paramString);
      String[] arrayOfString = new String[localNVPair.getListSize()];
      if ((localNVPair.getRHSType() == NVPair.LIST_COMMASEP) || (localNVPair.getRHSType() == NVPair.RHS_LIST))
      {
        for (int i = 0; i < localNVPair.getListSize(); i++) {
          arrayOfString[i] = localNVPair.getListElement(i).getName();
        }
      }
      else {
        throw new NetException(403, paramString);
      }

      paramSSLSocket.setEnabledCipherSuites(arrayOfString);
    }
    catch (NLException localNLException)
    {
      throw new NetException(403, paramString);
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      throw ((NetException)new NetException(404).initCause(localIllegalArgumentException));
    }
  }

  public static boolean matchServerDN(String paramString1, String paramString2, boolean paramBoolean)
  {
    paramString1 = normalizeDN(paramString1);
    if (paramString1 == null)
      return false;
    if (paramBoolean)
    {
      paramString2 = normalizeDN(paramString2);
      if (paramString2 == null)
        return false;
      if (paramString2.equals(paramString1)) {
        return true;
      }

      paramString1 = reverseDN(paramString1);
      if (paramString2.equals(paramString1)) {
        return true;
      }
      return false;
    }
    int i = paramString1.indexOf("CN=");
    if (i != -1)
    {
      if (paramString1.indexOf(44, i) != -1) {
        paramString1 = paramString1.substring(i, paramString1.indexOf(44, i));
      }
      else
        paramString1 = paramString1.substring(i);
      if (paramString2.equals(paramString1))
        return true;
    }
    return false;
  }

  public static String normalizeDN(String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    String str1 = null;
    String str2 = null;
    int i = 0;
    int j = 0;
    paramString = paramString.trim();

    while ((i = paramString.indexOf(61, i)) != -1)
    {
      str1 = paramString.substring(j, i);
      str1 = str1.trim();
      localStringBuffer.append(str1.toUpperCase());
      localStringBuffer.append('=');
      j = i;
      if (j >= paramString.length() - 1)
        return null;
      i = paramString.indexOf(44, i);
      if (i == -1)
      {
        str2 = paramString.substring(j + 1);
        localStringBuffer.append(str2.trim());
        break;
      }
      str2 = paramString.substring(j + 1, i);
      localStringBuffer.append(str2.trim());
      localStringBuffer.append(',');
      if (i >= paramString.length() - 1) {
        return null;
      }
      j = i + 1;
    }
    return localStringBuffer.toString();
  }

  public static String reverseDN(String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    String str = null;
    int i = paramString.length();
    int j = i;
    do
    {
      i = paramString.lastIndexOf(44, i);
      if (i == -1) {
        localStringBuffer.append(paramString.substring(0, j));
        break;
      }
      str = paramString.substring(i + 1, j);
      localStringBuffer.append(str);
      localStringBuffer.append(',');
      j = i;
      i--;
    }while (i != -1);
    return localStringBuffer.toString();
  }
}