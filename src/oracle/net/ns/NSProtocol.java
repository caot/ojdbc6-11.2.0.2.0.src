package oracle.net.ns;

import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.Properties;
import oracle.net.ano.Ano;
import oracle.net.jdbc.nl.NLException;
import oracle.net.jdbc.nl.NVFactory;
import oracle.net.jdbc.nl.NVNavigator;
import oracle.net.jdbc.nl.NVPair;
import oracle.net.nt.ConnOption;
import oracle.net.nt.NTAdapter;
import oracle.net.resolver.AddrResolution;

public class NSProtocol
  implements Communication, SQLnetDef
{
  private static final boolean ACTIVATE_ANO = true;
  private AddrResolution addrRes;
  private SessionAtts sAtts;
  private MarkerPacket mkPkt;
  private DataPacket probePacket;
  private Packet packet;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  public NSProtocol()
  {
    this.sAtts = new SessionAtts(this, 65535, 65535);
    this.sAtts.connected = false;
  }

  public SessionAtts getSessionAttributes()
  {
    return this.sAtts;
  }

  public void connect(String paramString, Properties paramProperties)
    throws IOException, NetException
  {
    if (this.sAtts.connected) {
      throw new NetException(201);
    }
    if (paramString == null) {
      throw new NetException(208);
    }

    NVFactory localNVFactory = new NVFactory();
    NVNavigator localNVNavigator = new NVNavigator();
    NVPair localNVPair1 = null;
    String str1 = null;

    this.addrRes = new AddrResolution(paramString, paramProperties);

    String str2 = (String)paramProperties.get("DISABLE_OOB");
    int i = (str2 != null) && ("true".equals(str2)) ? 1 : 0;

    String str3 = (String)paramProperties.get("USE_ZERO_COPY_IO");
    boolean bool = true;
    if ((str3 != null) && ("false".equals(str3)))
      bool = false;
    this.sAtts.traceId = ((String)paramProperties.get("T4CConnection.hashCode"));

    if (this.addrRes.connection_revised)
    {
      paramString = this.addrRes.getTNSAddress();
      paramProperties = this.addrRes.getUp();
    }

    this.sAtts.profile = new ClientProfile(paramProperties);

    establishConnection(paramString);

    Object localObject1 = null;
    try
    {
      localObject1 = Class.forName("oracle.net.ano.Ano").newInstance();
      this.sAtts.anoEnabled = true;
    }
    catch (Exception localException1)
    {
      this.sAtts.anoEnabled = false;
    }

    if (localObject1 != null)
    {
      ((Ano)localObject1).init(this.sAtts);
      this.sAtts.ano = ((Ano)localObject1);
      this.sAtts.anoEnabled = true;
    }

    Object localObject3;
    
    label0:
    while (true)
    {
      IOException localObject2 = null;
      ConnectPacket localConnectPacket = new ConnectPacket(this.sAtts, i == 0, bool);
      this.packet = new Packet(this.sAtts, this.sAtts.getSDU());
      try
      {
        localConnectPacket.send();

        this.packet.receive();
      }
      catch (InterruptedIOException localInterruptedIOException)
      {
        throw localInterruptedIOException;
      }
      catch (IOException localIOException)
      {
        this.packet.type = 4;
        localObject2 = localIOException;
      }

      switch (this.packet.type)
      {
      case 2:
        AcceptPacket localAcceptPacket = new AcceptPacket(this.packet);
        break label0;
        
      case 5:
        RedirectPacket localRedirectPacket = new RedirectPacket(this.packet);

        localObject3 = this.sAtts.cOption;
        this.addrRes.connection_redirected = true;

        this.sAtts.cOption.nt.disconnect();
        this.sAtts = establishConnection(localRedirectPacket.getData());

        this.sAtts.cOption.restoreFromOrigCoption((ConnOption)localObject3);

        break;
        
      case 4:
        RefusePacket localRefusePacket = new RefusePacket(this.packet);

        this.sAtts.cOption.nt.disconnect();
        this.sAtts.cOption = null;

        establishConnection(null);

        if (this.sAtts.cOption == null)
        {
          if (localObject2 != null) {
            throw localObject2;
          }

          try
          {
            localNVPair1 = localNVNavigator.findNVPairRecurse(localNVFactory.createNVPair(localRefusePacket.getData()), "ERROR");

            if (localNVPair1 != null)
            {
              NVPair localNVPair2 = localNVNavigator.findNVPairRecurse(localNVPair1, "CODE");
              if (localNVPair2 != null)
                str1 = localNVPair2.valueToString();
            }
          }
          catch (NLException localNLException)
          {
            System.err.println(localNLException.getMessage());
          }

          throw new NetException(str1 == null ? 206 : Integer.parseInt(str1), "");
        }
        break;
      case 11:
        if ((this.packet.flags & 0x8) == 8)
        {
          this.sAtts.renegotiateSSLSession(); }
        break;
      case 3:
      case 6:
      case 7:
      case 8:
      case 9:
      case 10:
      default:
        this.sAtts.cOption.nt.disconnect();
        throw new NetException(205);
      }

    }

    setNetStreams();

    this.sAtts.connected = true;

    this.sAtts.nt.setReadTimeoutIfRequired(paramProperties);

    Object localObject2 = (String)this.sAtts.nt.getOption(6);

    if ((localObject2 != null) && (((String)localObject2).equalsIgnoreCase("false"))) {
      throw new NetException(405);
    }

    if (!this.sAtts.noAnoServices)
    {
      if (this.sAtts.ano != null) {
        this.sAtts.ano.negotiation();

        localObject3 = (String)this.sAtts.nt.getOption(2);

        if ((localObject3 != null) && (((String)localObject3).equals("TRUE")))
        {
          try
          {
            Method localMethod = this.sAtts.ano.getClass().getMethod("getEncryptionAlg", (Class[])null);

            if (localMethod.invoke(this.sAtts.ano, (Object[])null) != null) {
              throw new NetException(406);
            }
          }
          catch (Exception localException2)
          {
          }
        }

      }

    }

    this.packet = null;
    ConnectPacket localConnectPacket = null;
    AcceptPacket localAcceptPacket = null;
    RedirectPacket localRedirectPacket = null;
    RefusePacket localRefusePacket = null;
  }

  public void disconnect()
    throws IOException, NetException
  {
    if (!this.sAtts.connected)
      throw new NetException(200);
    IOException localObject = null;
    try
    {
      this.sAtts.nsOutputStream.close();
    }
    catch (IOException localIOException) {
      localObject = localIOException;
    }
    this.sAtts.connected = false;

    this.sAtts.cOption.nt.disconnect();
    if (localObject != null)
      throw localObject;
  }

  public void sendBreak()
    throws IOException, NetException
  {
    if ((this.sAtts.negotiatedOptions & 0x400) == 1024)
    {
      this.sAtts.nt.sendUrgentByte(33);
      if ((this.sAtts.negotiatedOptions & 0x800) != 2048)
      {
        sendMarker(1);
      }
    }
    else
    {
      sendMarker(1);
    }
  }

  public void sendInterrupt()
    throws IOException, NetException
  {
    if ((this.sAtts.negotiatedOptions & 0x400) == 1024)
    {
      this.sAtts.nt.sendUrgentByte(33);
      if ((this.sAtts.negotiatedOptions & 0x800) != 2048)
      {
        sendMarker(3);
      }
    }
    else
    {
      sendMarker(3);
    }
  }

  public void sendReset()
    throws IOException, NetException
  {
    if (!this.sAtts.connected) {
      throw new NetException(200);
    }

    sendMarker(2);

    while (this.sAtts.onBreakReset)
    {
      Packet localPacket = ((NetInputStream)this.sAtts.getInputStream()).getCurrentPacket();
      localPacket.receive();

      if (localPacket.type == 12)
      {
        this.mkPkt = new MarkerPacket(localPacket);
        if (this.mkPkt.data == 2)
          this.sAtts.onBreakReset = false;
      }
    }
    this.mkPkt = null;
  }

  public NetInputStream getNetInputStream()
    throws NetException
  {
    if (!this.sAtts.connected) {
      throw new NetException(200);
    }
    return this.sAtts.nsInputStream;
  }

  public InputStream getInputStream() throws NetException
  {
    return getNetInputStream();
  }

  public NetOutputStream getNetOutputStream()
    throws NetException
  {
    if (!this.sAtts.connected) {
      throw new NetException(200);
    }
    return this.sAtts.nsOutputStream;
  }

  public OutputStream getOutputStream() throws NetException
  {
    return getNetOutputStream();
  }

  private SessionAtts establishConnection(String paramString)
    throws NetException, IOException
  {
    this.sAtts.cOption = this.addrRes.resolveAndExecute(paramString);

    if (this.sAtts.cOption == null)
      return null;
    this.sAtts.nt = this.sAtts.cOption.nt;
    this.sAtts.ntInputStream = this.sAtts.cOption.nt.getInputStream();
    this.sAtts.ntOutputStream = this.sAtts.cOption.nt.getOutputStream();

    this.sAtts.setTDU(this.sAtts.cOption.tdu);

    this.sAtts.setSDU(this.sAtts.cOption.sdu);

    if (this.sAtts.attemptingReconnect)
    {
      this.sAtts.nsOutputStream.reinitialize(this.sAtts);
      this.sAtts.nsInputStream.reinitialize(this.sAtts);
      if (this.mkPkt != null)
        this.mkPkt.reinitialize(this.sAtts);
    } else {
      this.sAtts.nsOutputStream = new NetOutputStream(this.sAtts, 255);
      this.sAtts.nsInputStream = new NetInputStream(this.sAtts);
    }

    return this.sAtts;
  }

  private void setNetStreams()
    throws NetException, IOException
  {
    this.sAtts.nsOutputStream = new NetOutputStream(this.sAtts);

    this.sAtts.nsInputStream = new NetInputStream(this.sAtts);
  }

  private void sendMarker(int paramInt)
    throws IOException, NetException
  {
    if (!this.sAtts.connected) {
      throw new NetException(200);
    }
    this.mkPkt = new MarkerPacket(this.sAtts, paramInt);

    this.mkPkt.send();
    this.mkPkt = null;
  }

  public void setO3logSessionKey(byte[] paramArrayOfByte)
    throws NetException
  {
    if (paramArrayOfByte != null)
    {
      this.sAtts.ano.setO3logSessionKey(paramArrayOfByte);
    }
  }

  public void setOption(int paramInt, Object paramObject)
    throws NetException, IOException
  {
    if ((paramInt > 0) && (paramInt < 10))
    {
      NTAdapter localNTAdapter = this.sAtts.getNTAdapter();
      localNTAdapter.setOption(paramInt, paramObject);
    }
  }

  public Object getOption(int paramInt)
    throws NetException, IOException
  {
    if ((paramInt > 0) && (paramInt < 10))
    {
      NTAdapter localNTAdapter = this.sAtts.getNTAdapter();
      return localNTAdapter.getOption(paramInt);
    }
    return null;
  }

  public void abort()
    throws NetException, IOException
  {
    NTAdapter localNTAdapter = this.sAtts.getNTAdapter();
    if (localNTAdapter != null)
      localNTAdapter.abort();
  }

  public String getEncryptionName()
  {
    String str = null;
    NTAdapter localNTAdapter = this.sAtts.getNTAdapter();
    try
    {
      str = (String)localNTAdapter.getOption(5);
    }
    catch (Exception localException)
    {
    }

    if ((str == null) && (this.sAtts.ano != null))
    {
      str = this.sAtts.ano.getEncryptionName();
    }

    if (str == null)
      str = "";
    return str;
  }

  public String getDataIntegrityName()
  {
    String str = "";
    if (this.sAtts.ano != null)
    {
      str = this.sAtts.ano.getDataIntegrityName();
    }
    return str;
  }

  public String getAuthenticationAdaptorName()
  {
    String str = "";
    if (this.sAtts.ano != null)
    {
      str = this.sAtts.ano.getAuthenticationAdaptorName();
    }
    return str;
  }

  public void reconnectIfRequired(boolean paramBoolean)
    throws IOException
  {
    long l1 = System.currentTimeMillis();
    long l2 = l1 - this.sAtts.timestampLastIO;

    if (l2 > this.sAtts.timeout)
      reconnect(paramBoolean);
  }

  public void reconnect(boolean paramBoolean)
    throws IOException
  {
    try
    {
      String str = new String(this.sAtts.reconnectAddress);
      this.sAtts.attemptingReconnect = true;
      ConnOption localConnOption = this.sAtts.cOption;
      this.addrRes.connection_redirected = true;

      this.sAtts.cOption.nt.disconnect();

      establishConnection(str);

      this.sAtts.cOption.restoreFromOrigCoption(localConnOption);

      if (paramBoolean)
      {
        if (this.probePacket == null)
          this.probePacket = new DataPacket(this.sAtts, 26);
        else {
          this.probePacket.reinitialize(this.sAtts);
        }
        this.probePacket.send();
      }
    } finally {
      this.sAtts.attemptingReconnect = false;
    }
  }
}