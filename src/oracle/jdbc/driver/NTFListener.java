package oracle.jdbc.driver;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

class NTFListener extends Thread
{
  private NTFConnection[] connections = null;
  private int nbOfConnections = 0;
  private boolean needsToBeClosed = false;
  NTFManager dcnManager;
  ServerSocketChannel ssChannel;
  int tcpport;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  NTFListener(NTFManager paramNTFManager, ServerSocketChannel paramServerSocketChannel, int paramInt)
  {
    this.dcnManager = paramNTFManager;
    this.connections = new NTFConnection[10];
    this.ssChannel = paramServerSocketChannel;
    this.tcpport = paramInt;
  }

  public void run()
  {
    try
    {
      Selector localSelector = Selector.open();
      this.ssChannel.register(localSelector, 16);
      while (true)
      {
        localSelector.select();
        if (this.needsToBeClosed) {
          break;
        }
        Iterator localIterator = localSelector.selectedKeys().iterator();
        while (localIterator.hasNext())
        {
          SelectionKey localSelectionKey = (SelectionKey)localIterator.next();

          if ((localSelectionKey.readyOps() & 0x10) == 16)
          {
            ServerSocketChannel localServerSocketChannel = (ServerSocketChannel)localSelectionKey.channel();

            SocketChannel localSocketChannel = localServerSocketChannel.accept();
            NTFConnection localNTFConnection = new NTFConnection(this.dcnManager, localSocketChannel);

            if (this.connections.length == this.nbOfConnections)
            {
              NTFConnection[] arrayOfNTFConnection = new NTFConnection[this.connections.length * 2];
              System.arraycopy(this.connections, 0, arrayOfNTFConnection, 0, this.connections.length);
              this.connections = arrayOfNTFConnection;
            }
            this.connections[(this.nbOfConnections++)] = localNTFConnection;
            localNTFConnection.start();
            localIterator.remove();
          }
        }
      }
      localSelector.close();
      this.ssChannel.close();
    }
    catch (IOException localIOException)
    {
    }
  }

  synchronized void closeThisListener()
  {
    for (int i = 0; i < this.nbOfConnections; i++)
    {
      this.connections[i].closeThisConnection();
      this.connections[i].interrupt();
    }
    this.needsToBeClosed = true;
  }
}