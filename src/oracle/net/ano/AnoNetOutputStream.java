package oracle.net.ano;

import oracle.net.ns.NetOutputStream;
import oracle.net.ns.SessionAtts;

public class AnoNetOutputStream extends NetOutputStream
{
  public AnoNetOutputStream(SessionAtts paramSessionAtts)
  {
    super(paramSessionAtts);
    this.daPkt = new CryptoDataPacket(paramSessionAtts);
  }
}