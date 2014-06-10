package oracle.net.ano;

import java.io.IOException;
import oracle.net.ns.BreakNetException;
import oracle.net.ns.NetException;
import oracle.net.ns.NetInputStream;
import oracle.net.ns.SessionAtts;

public class AnoNetInputStream extends NetInputStream
{
  public AnoNetInputStream(SessionAtts paramSessionAtts)
  {
    super(paramSessionAtts);
    this.daPkt = new CryptoDataPacket(paramSessionAtts);
  }

  protected void processMarker()
    throws IOException, NetException, BreakNetException
  {
    this.sAtts.ano.setRenewKey(true);
  }
}