package oracle.net.jndi;

import java.io.PrintStream;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Vector;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import oracle.net.ns.NetException;

public class JndiAttrs
{
  private Properties env;
  private static final String nFactory = "java.naming.factory.initial";
  private static final String nProvider = "java.naming.provider.url";
  private static final String nProfile = "ora-net-profile";
  private static final String default_nFactory = "com.sun.jndi.ldap.LdapCtxFactory";
  DirContext ctx;

  public JndiAttrs(Properties paramProperties)
    throws NetException
  {
    String str = null;

    this.env = new Properties();

    if (paramProperties.containsKey("java.naming.factory.initial"))
      this.env.put("java.naming.factory.initial", paramProperties.getProperty("java.naming.factory.initial"));
    else {
      this.env.put("java.naming.factory.initial", "com.sun.jndi.ldap.LdapCtxFactory");
    }
    if (paramProperties.containsKey("java.naming.provider.url"))
    {
      str = paramProperties.getProperty("java.naming.provider.url");

      if (str.startsWith("ldaps"))
      {
        this.env.put("java.naming.ldap.factory.socket", "oracle.net.jndi.CustomSSLSocketFactory");

        this.env.put("java.naming.security.protocol", "ssl");
        str = "ldap:" + str.substring(6);
      }
      this.env.put("java.naming.provider.url", str);
    }

    if (paramProperties.containsKey("oracle.net.profile"))
      this.env.put("ora-net-profile", paramProperties.getProperty("oracle.net.profile"));
    try
    {
      Class localClass1 = Class.forName("javax.naming.directory.InitialDirContext");

      if (this.env.get("java.naming.factory.initial") == null)
      {
        this.env.put("java.naming.factory.initial", "com.sun.jndi.ldap.LdapCtxFactory");
      }

      if (this.env.get("java.naming.provider.url") == null)
        this.env.put("java.naming.provider.url", str);
    }
    catch (Exception localException1)
    {
      throw new NetException(110, localException1.toString());
    }
    Object localObject;
    if ((localObject = paramProperties.get("java.naming.security.authentication")) != null)
    {
      this.env.put("java.naming.security.authentication", localObject);
    }

    if ((localObject = paramProperties.get("java.naming.security.principal")) != null)
    {
      this.env.put("java.naming.security.principal", localObject);
    }

    if ((localObject = paramProperties.get("java.naming.security.credentials")) != null)
    {
      this.env.put("java.naming.security.credentials", localObject);
    }

    for (int i = 0; i < 3; i++)
      try
      {
        this.ctx = new InitialDirContext(this.env);
      }
      catch (NamingException localNamingException)
      {
        Class localClass2 = null;
        try {
          localClass2 = Class.forName("javax.net.ssl.SSLException");
        }
        catch (Exception localException2)
        {
          throw new NetException(108, localNamingException.toString());
        }

        if ((!localClass2.isInstance(localNamingException.getRootCause())) || (i >= 3))
        {
          throw new NetException(108, localNamingException.toString());
        }
      }
  }

  public Vector getProfileAttrs(String paramString)
    throws NetException
  {
    String str = "cn=";
    Attributes localAttributes = null;
    try {
      str = str.concat(paramString);
      localAttributes = this.ctx.getAttributes(str);
    } catch (NamingException localNamingException) {
      throw new NetException(108, localNamingException.toString());
    }

    return setAttrs(localAttributes);
  }

  public Vector getAttrs(String paramString, String[] paramArrayOfString)
    throws NetException
  {
    String str = "cn=";
    Attributes localAttributes = null;
    try {
      str = str.concat(paramString);
      localAttributes = this.ctx.getAttributes(str, paramArrayOfString);
    }
    catch (NamingException localNamingException) {
      throw new NetException(108, localNamingException.toString());
    }

    return setAttrs(localAttributes);
  }

  private Vector setAttrs(Attributes paramAttributes)
    throws NetException
  {
    Vector localVector = new Vector(1, 1);

    if (paramAttributes == null) {
      System.out.println("No attributes");
    }
    else {
      try
      {
        NamingEnumeration localNamingEnumeration1 = paramAttributes.getAll();
        while ((localNamingEnumeration1 != null) && (localNamingEnumeration1.hasMoreElements())) {
          Attribute localAttribute = (Attribute)localNamingEnumeration1.next();

          NamingEnumeration localNamingEnumeration2 = localAttribute.getAll();
          while (localNamingEnumeration2.hasMoreElements())
            localVector.addElement(localNamingEnumeration2.nextElement());
        }
      }
      catch (NamingException localNamingException) {
        throw new NetException(108, localNamingException.toString());
      }
    }

    return localVector;
  }

  public void setEnv(String paramString1, String paramString2)
  {
    this.env.put(paramString1, paramString2);
  }

  public void close()
    throws NetException
  {
    try
    {
      if (null != this.ctx)
        this.ctx.close();
    }
    catch (NamingException localNamingException) {
      throw new NetException(108, localNamingException.toString());
    }
  }

  public final String getLdapUrlUsed()
    throws NetException
  {
    String str = null;
    try
    {
      str = (String)this.ctx.getEnvironment().get("java.naming.provider.url");
    }
    catch (NamingException localNamingException)
    {
      throw new NetException(108, localNamingException.toString());
    }

    return str;
  }
}