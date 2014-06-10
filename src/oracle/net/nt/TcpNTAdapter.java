package oracle.net.nt;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Hashtable;
import java.util.Properties;
import javax.net.SocketFactory;
import oracle.net.jdbc.nl.NLException;
import oracle.net.jdbc.nl.NVFactory;
import oracle.net.jdbc.nl.NVNavigator;
import oracle.net.jdbc.nl.NVPair;
import oracle.net.ns.NetException;

public class TcpNTAdapter
  implements NTAdapter
{
  static final boolean DEBUG = false;
  static final String SDP_SOCKET_FACTORY_NAME = "com.oracle.net.SDPSocketFactory";
  static SocketFactory SDP_SOCKET_FACTORY = null;
  int port;
  String host;
  protected Socket socket;
  protected int sockTimeout;
  protected Properties socketOptions;
  private static Hashtable<String, InetAddress[]> inetaddressesCache = new Hashtable();
  private static Hashtable<String, Integer> circularOffsets = new Hashtable();

  private static SocketFactory getSDPSocketFactory()
    throws IOException
  {
    if (SDP_SOCKET_FACTORY == null) {
      try {
        Class localClass = Class.forName("com.oracle.net.SDPSocketFactory");
        Method localMethod = localClass.getMethod("getDefault", new Class[0]);
        SDP_SOCKET_FACTORY = (SocketFactory)localMethod.invoke(null, new Object[0]);
      }
      catch (ClassNotFoundException localClassNotFoundException) {
        throw new IOException("SDP enabled, but SDP SocketFactory not in classpath", localClassNotFoundException);
      }
      catch (NoSuchMethodException localNoSuchMethodException)
      {
        throw new IOException("SDP enabled but unable to get default SDP SocketFactory", localNoSuchMethodException);
      }
      catch (IllegalAccessException localIllegalAccessException)
      {
        throw new IOException("SDP enabled, but SDP SocketFactory could not be accessed", localIllegalAccessException);
      }
      catch (InvocationTargetException localInvocationTargetException)
      {
        throw new IOException("SDP enabled, but SDP SocketFactory raised an exception", localInvocationTargetException);
      }

    }

    return SDP_SOCKET_FACTORY;
  }

  public TcpNTAdapter(String paramString, Properties paramProperties)
    throws NLException
  {
    this.socketOptions = paramProperties;

    NVNavigator localNVNavigator = new NVNavigator();
    NVPair localNVPair1 = new NVFactory().createNVPair(paramString);

    NVPair localNVPair2 = localNVNavigator.findNVPair(localNVPair1, "HOST");
    NVPair localNVPair3 = localNVNavigator.findNVPair(localNVPair1, "PORT");

    if (localNVPair2 == null)
      throw new NLException("NoNVPair-04614", "HOST");
    this.host = localNVPair2.getAtom();

    if (localNVPair3 != null) {
      try
      {
        this.port = Integer.parseInt(localNVPair3.getAtom());
      } catch (Exception localException) {
        throw ((NLException)new NLException(new NetException(116).getMessage()).initCause(localException));
      }
    }
    else {
      this.port = 1521;
    }
    if ((this.port < 0) || (this.port > 65535))
      throw new NLException(new NetException(116).getMessage());
  }

  public void connect()
    throws IOException
  {
    String str = (String)this.socketOptions.get(Integer.valueOf(2));

    int i = Integer.parseInt((String)this.socketOptions.get(Integer.valueOf(17)));

    boolean bool1 = Boolean.parseBoolean((String)this.socketOptions.get(Integer.valueOf(18)));

    boolean bool2 = Boolean.parseBoolean((String)this.socketOptions.get(Integer.valueOf(19)));

    InetAddress[] arrayOfInetAddress = InetAddress.getAllByName(this.host);
    if ((bool1) && (arrayOfInetAddress.length > 1))
    {
      arrayOfInetAddress = getAddressesInCircularOrder(this.host, arrayOfInetAddress);
    }

    int j = i * arrayOfInetAddress.length;

    for (int k = 0; k < i; k++)
    {
      int m = 0;
      do
      {
        InetAddress localInetAddress = arrayOfInetAddress[m];
        m++;
        j--;
        if (bool2) {
          this.socket = getSDPSocketFactory().createSocket();
        }
        else
        {
          this.socket = new Socket();
        }

        try
        {
          this.socket.connect(new InetSocketAddress(localInetAddress, this.port), Integer.parseInt(str));
        }
        catch (IOException localIOException)
        {
          try
          {
            if (this.socket != null) {
              this.socket.close();
            }

          }
          catch (Exception localException)
          {
          }

          if (j <= 0)
          {
            throw localIOException;
          }
        }
      }

      while (m < arrayOfInetAddress.length);
    }

    setOption(3, str);

    setSocketOptions();
  }

  public void setSocketOptions()
    throws IOException
  {
    String str;
    if ((str = (String)this.socketOptions.get(Integer.valueOf(0))) != null)
    {
      setOption(0, str);
    }if ((str = (String)this.socketOptions.get(Integer.valueOf(1))) != null)
    {
      setOption(1, str);
    }
  }

  public void disconnect()
    throws IOException
  {
    try
    {
      this.socket.close();
    } finally {
      this.socket = null;
    }
  }

  public InputStream getInputStream()
    throws IOException
  {
    return this.socket.getInputStream();
  }

  public OutputStream getOutputStream()
    throws IOException
  {
    return this.socket.getOutputStream();
  }

  public void setOption(int paramInt, Object paramObject)
    throws IOException, NetException
  {
    String str;
    switch (paramInt)
    {
    case 0:
      str = (String)paramObject;
      this.socket.setTcpNoDelay(str.equals("YES"));

      break;
    case 1:
      str = (String)paramObject;
      if (str.equals("YES")) {
        this.socket.setKeepAlive(true);
      }
      break;
    case 3:
      this.sockTimeout = Integer.parseInt((String)paramObject);
      this.socket.setSoTimeout(this.sockTimeout);
      break;
    case 2:
    }
  }

  public Object getOption(int paramInt)
    throws IOException, NetException
  {
    switch (paramInt)
    {
    case 1:
      return "" + this.sockTimeout;
    }
    return null;
  }

  public void abort()
    throws NetException, IOException
  {
    try
    {
      this.socket.setSoLinger(true, 0);
    }
    catch (Exception localException)
    {
    }
    this.socket.close();
  }

  public void sendUrgentByte(int paramInt)
    throws IOException
  {
    this.socket.sendUrgentData(paramInt);
  }

  public boolean isCharacteristicUrgentSupported() throws IOException
  {
    try {
      return !this.socket.getOOBInline();
    }
    catch (IOException localIOException) {
    }
    return false;
  }

  public void setReadTimeoutIfRequired(Properties paramProperties)
    throws IOException, NetException
  {
    String str = (String)paramProperties.get("oracle.net.READ_TIMEOUT");
    if (str == null) {
      str = "0";
    }
    setOption(3, str);
  }

  public String toString()
  {
    return "host=" + this.host + ", port=" + this.port + "\n    socket_timeout=" + this.sockTimeout + ", socketOptions=" + this.socketOptions.toString() + "\n    socket=" + this.socket;
  }

  private static final synchronized InetAddress[] getAddressesInCircularOrder(String paramString, InetAddress[] paramArrayOfInetAddress)
  {
    InetAddress[] arrayOfInetAddress1 = (InetAddress[])inetaddressesCache.get(paramString);
    Integer localInteger = (Integer)circularOffsets.get(paramString);
    if ((arrayOfInetAddress1 == null) || (!areEquals(arrayOfInetAddress1, paramArrayOfInetAddress)))
    {
      localInteger = new Integer(0);
      arrayOfInetAddress1 = paramArrayOfInetAddress;
      inetaddressesCache.put(paramString, paramArrayOfInetAddress);
      circularOffsets.put(paramString, localInteger);
    }
    InetAddress[] arrayOfInetAddress2 = getCopyAddresses(arrayOfInetAddress1, localInteger.intValue());
    circularOffsets.put(paramString, new Integer((localInteger.intValue() + 1) % arrayOfInetAddress1.length));
    return arrayOfInetAddress2;
  }

  private static final boolean areEquals(InetAddress[] paramArrayOfInetAddress1, InetAddress[] paramArrayOfInetAddress2)
  {
    if (paramArrayOfInetAddress1.length != paramArrayOfInetAddress2.length)
      return false;
    for (int i = 0; i < paramArrayOfInetAddress1.length; i++)
      if (!paramArrayOfInetAddress1[i].equals(paramArrayOfInetAddress2[i]))
        return false;
    return true;
  }

  private static final InetAddress[] getCopyAddresses(InetAddress[] paramArrayOfInetAddress, int paramInt)
  {
    InetAddress[] arrayOfInetAddress = new InetAddress[paramArrayOfInetAddress.length];
    for (int i = 0; i < paramArrayOfInetAddress.length; i++)
      arrayOfInetAddress[i] = paramArrayOfInetAddress[((i + paramInt) % paramArrayOfInetAddress.length)];
    return arrayOfInetAddress;
  }
}