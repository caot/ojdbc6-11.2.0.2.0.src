package oracle.net.ns;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import oracle.net.ano.Ano;
import oracle.net.nt.ConnOption;
import oracle.net.nt.NTAdapter;
import oracle.net.nt.TcpsNTAdapter;

public class SessionAtts
  implements SQLnetDef
{
  private int sdu;
  private int tdu;
  protected NSProtocol ns;
  protected NTAdapter nt;
  protected InputStream ntInputStream;
  protected OutputStream ntOutputStream;
  protected NetInputStream nsInputStream;
  protected NetOutputStream nsOutputStream;
  protected ConnOption cOption;
  protected boolean dataEOF;
  protected boolean connected;
  public boolean onBreakReset;
  public ClientProfile profile;
  public Ano ano;
  public boolean anoEnabled;
  public boolean isEncryptionActive;
  public boolean isChecksumActive;
  public boolean areEncryptionAndChecksumActive;
  boolean noAnoServices;
  int negotiatedOptions;
  public boolean poolEnabled = false;
  protected byte[] sessionId;
  protected int timeout;
  protected int tick;
  protected byte[] reconnectAddress;
  protected long timestampLastIO;
  protected boolean attemptingReconnect = false;
  String traceId;

  public SessionAtts(NSProtocol paramNSProtocol, int paramInt1, int paramInt2)
  {
    this.sdu = paramInt1;
    this.tdu = paramInt2;
    this.ns = paramNSProtocol;
  }

  public void setSDU(int paramInt)
  {
    if (paramInt <= 0)
      this.sdu = 8192;
    else if (paramInt > 65535)
      this.sdu = 65535;
    else if (paramInt < 512)
      this.sdu = 512;
    else
      this.sdu = paramInt;
  }

  public int getSDU()
  {
    return this.sdu;
  }

  public void setTDU(int paramInt)
  {
    if (paramInt <= 0)
      this.tdu = 32767;
    else if (paramInt > 65535)
      this.tdu = 65535;
    else if (paramInt < 255)
      this.tdu = 255;
    else
      this.tdu = paramInt;
  }

  public int getTDU()
  {
    return this.tdu;
  }

  public NTAdapter getNTAdapter()
  {
    return this.nt;
  }

  void renegotiateSSLSession()
    throws IOException
  {
    ((TcpsNTAdapter)this.nt).renegotiateSession();
    this.ntInputStream = this.nt.getInputStream();
    this.ntOutputStream = this.nt.getOutputStream();
  }

  public String toString()
  {
    return "Session Attributes: \nsdu=" + this.sdu + ", tdu=" + this.tdu + "\nnt: " + this.nt + "\n\nntInputStream : " + this.ntInputStream + "\nntOutputStream: " + this.ntOutputStream + "\nnsInputStream : " + this.nsInputStream + "\nnsOutputStream: " + this.nsOutputStream + "\n\nClient Profile: " + this.profile + "\n\nConnection Options: " + this.cOption + "\n\nonBreakReset=" + this.onBreakReset + ", dataEOF=" + this.dataEOF + ", negotiatedOptions=0x" + Integer.toHexString(this.negotiatedOptions) + ", connected=" + this.connected;
  }

  public void turnEncryptionOn(NetInputStream paramNetInputStream, NetOutputStream paramNetOutputStream)
    throws NetException
  {
    if ((paramNetInputStream != null) && (paramNetOutputStream != null))
    {
      this.nsInputStream = paramNetInputStream;
      this.nsOutputStream = paramNetOutputStream;
    }
    else {
      throw new NetException(300);
    }
  }

  public int getANOFlags()
  {
    int i = 1;

    if (this.ano != null) {
      i = this.ano.getNAFlags();
    }
    return i;
  }

  public OutputStream getOutputStream()
  {
    return this.nsOutputStream;
  }

  public InputStream getInputStream()
  {
    return this.nsInputStream;
  }

  public void setNegotiatedOptions(int paramInt)
  {
    this.negotiatedOptions = paramInt;
  }

  public int getNegotiatedOptions() {
    return this.negotiatedOptions;
  }
}