package oracle.net.nt;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import oracle.net.ns.NetException;

public abstract interface NTAdapter
{
  public abstract void connect()
    throws IOException;

  public abstract void disconnect()
    throws IOException;

  public abstract InputStream getInputStream()
    throws IOException;

  public abstract OutputStream getOutputStream()
    throws IOException;

  public abstract void setOption(int paramInt, Object paramObject)
    throws IOException, NetException;

  public abstract Object getOption(int paramInt)
    throws IOException, NetException;

  public abstract void abort()
    throws IOException, NetException;

  public abstract void sendUrgentByte(int paramInt)
    throws IOException;

  public abstract boolean isCharacteristicUrgentSupported()
    throws IOException;

  public abstract void setReadTimeoutIfRequired(Properties paramProperties)
    throws IOException, NetException;
}