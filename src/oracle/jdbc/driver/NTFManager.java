package oracle.jdbc.driver;

import java.io.IOException;
import java.net.BindException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.ServerSocketChannel;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Hashtable;
import oracle.jdbc.internal.OracleConnection;

class NTFManager
{
  private Hashtable<Integer, NTFListener> nsListeners = new Hashtable();

  private Hashtable<Integer, NTFRegistration> ntfRegistrations = new Hashtable();

  private byte[] listOfJdbcRegId = new byte[20];

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  synchronized boolean listenOnPortT4C(int[] paramArrayOfInt, boolean paramBoolean)
    throws SQLException
  {
    int i = paramArrayOfInt[0];
    boolean bool = false;
    if (this.nsListeners.get(Integer.valueOf(i)) == null)
    {
      bool = true;
      try
      {
        ServerSocketChannel localServerSocketChannel = ServerSocketChannel.open();
        localServerSocketChannel.configureBlocking(false);

        localObject = localServerSocketChannel.socket();
        while (true)
        {
          try
          {
            InetSocketAddress localInetSocketAddress = new InetSocketAddress(i);
            ((ServerSocket)localObject).bind(localInetSocketAddress);
          }
          catch (BindException localBindException)
          {
            if (!paramBoolean)
            {
              localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 250);
              localSQLException.fillInStackTrace();
              throw localSQLException;
            }

            i++;
          }
          catch (IOException localIOException2)
          {
            SQLException localSQLException;
            if (!paramBoolean)
            {
              localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 250);
              localSQLException.fillInStackTrace();
              throw localSQLException;
            }

            i++;
          }
        }

        NTFListener localNTFListener = new NTFListener(this, localServerSocketChannel, i);
        this.nsListeners.put(Integer.valueOf(i), localNTFListener);
        localNTFListener.start();
      }
      catch (IOException localIOException1)
      {
        Object localObject = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localIOException1);
        ((SQLException)localObject).fillInStackTrace();
        throw ((Throwable)localObject);
      }
    }

    paramArrayOfInt[0] = i;
    return bool;
  }

  synchronized int getNextJdbcRegId()
  {
    for (int i = 1; 
      i < this.listOfJdbcRegId.length; i++)
    {
      if (this.listOfJdbcRegId[i] == 0)
        break;
    }
    if (i == this.listOfJdbcRegId.length - 1)
    {
      byte[] arrayOfByte = new byte[this.listOfJdbcRegId.length * 2];
      System.arraycopy(this.listOfJdbcRegId, 0, arrayOfByte, 0, this.listOfJdbcRegId.length);
      this.listOfJdbcRegId = arrayOfByte;
    }
    this.listOfJdbcRegId[i] = 2;
    return i;
  }

  synchronized void addRegistration(NTFRegistration paramNTFRegistration)
  {
    Integer localInteger = Integer.valueOf(paramNTFRegistration.getJdbcRegId());
    Hashtable localHashtable = (Hashtable)this.ntfRegistrations.clone();
    localHashtable.put(localInteger, paramNTFRegistration);

    this.ntfRegistrations = localHashtable;
  }

  synchronized boolean removeRegistration(NTFRegistration paramNTFRegistration)
  {
    Integer localInteger = Integer.valueOf(paramNTFRegistration.getJdbcRegId());
    Hashtable localHashtable = (Hashtable)this.ntfRegistrations.clone();
    Object localObject = localHashtable.remove(localInteger);

    this.ntfRegistrations = localHashtable;

    boolean bool = false;

    if (localObject != null)
      bool = true;
    return bool;
  }

  synchronized void freeJdbcRegId(int paramInt)
  {
    if ((this.listOfJdbcRegId != null) && (this.listOfJdbcRegId.length > paramInt))
      this.listOfJdbcRegId[paramInt] = 0;
  }

  synchronized void cleanListenersT4C(int paramInt)
  {
    Enumeration localEnumeration = this.ntfRegistrations.keys();
    int i = 0;
    Object localObject;
    while ((i == 0) && (localEnumeration.hasMoreElements()))
    {
      localObject = localEnumeration.nextElement();
      NTFRegistration localNTFRegistration = (NTFRegistration)this.ntfRegistrations.get(localObject);
      if (localNTFRegistration.getClientTCPPort() == paramInt)
        i = 1;
    }
    if (i == 0)
    {
      localObject = (NTFListener)this.nsListeners.get(Integer.valueOf(paramInt));
      if (localObject != null)
      {
        ((NTFListener)localObject).closeThisListener();
        ((NTFListener)localObject).interrupt();
        this.nsListeners.remove(Integer.valueOf(paramInt));
      }
    }
  }

  NTFRegistration getRegistration(int paramInt)
  {
    Integer localInteger = Integer.valueOf(paramInt);

    Hashtable localHashtable = this.ntfRegistrations;
    NTFRegistration localNTFRegistration = (NTFRegistration)localHashtable.get(localInteger);
    return localNTFRegistration;
  }

  protected OracleConnection getConnectionDuringExceptionHandling()
  {
    return null;
  }
}