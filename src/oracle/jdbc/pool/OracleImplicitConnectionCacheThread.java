package oracle.jdbc.pool;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Vector;
import oracle.jdbc.internal.OracleConnection;

/** @deprecated */
class OracleImplicitConnectionCacheThread extends Thread
{
  private OracleImplicitConnectionCache implicitCache = null;
  protected boolean timeToLive = true;
  protected boolean isSleeping = false;

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  OracleImplicitConnectionCacheThread(OracleImplicitConnectionCache paramOracleImplicitConnectionCache)
    throws SQLException
  {
    this.implicitCache = paramOracleImplicitConnectionCache;
  }

  public void run()
  {
    long l1 = 0L;
    long l2 = 0L;
    long l3 = 0L;

    while (this.timeToLive)
    {
      try
      {
        if ((this.timeToLive) && ((l1 = this.implicitCache.getCacheTimeToLiveTimeout()) > 0L))
        {
          runTimeToLiveTimeout(l1);
        }

        if ((this.timeToLive) && ((l2 = this.implicitCache.getCacheInactivityTimeout()) > 0L))
        {
          runInactivityTimeout();
        }

        if ((this.timeToLive) && ((l3 = this.implicitCache.getCacheAbandonedTimeout()) > 0L))
        {
          runAbandonedTimeout(l3);
        }

        if (this.timeToLive)
        {
          this.isSleeping = true;
          try
          {
            sleep(this.implicitCache.getCachePropertyCheckInterval() * 1000);
          }
          catch (InterruptedException localInterruptedException)
          {
          }

          this.isSleeping = false;
        }

        if ((this.implicitCache == null) || ((l1 <= 0L) && (l2 <= 0L) && (l3 <= 0L)))
        {
          this.timeToLive = false;
        }
      }
      catch (SQLException localSQLException)
      {
      }
    }
  }

  private void runTimeToLiveTimeout(long paramLong)
    throws SQLException
  {
    long l1 = 0L;
    long l2 = 0L;

    if (this.implicitCache.getNumberOfCheckedOutConnections() > 0)
    {
      OraclePooledConnection localOraclePooledConnection = null;

      synchronized (this.implicitCache)
      {
        Object[] arrayOfObject = this.implicitCache.checkedOutConnectionList.toArray();
        int i = this.implicitCache.checkedOutConnectionList.size();

        for (int j = 0; j < i; j++)
        {
          localOraclePooledConnection = (OraclePooledConnection)arrayOfObject[j];

          Connection localConnection = localOraclePooledConnection.getLogicalHandle();

          if (localConnection != null)
          {
            l2 = ((OracleConnection)localConnection).getStartTime();

            l1 = System.currentTimeMillis();

            if (l1 - l2 > paramLong * 1000L)
            {
              try
              {
                this.implicitCache.closeCheckedOutConnection(localOraclePooledConnection, true);
              }
              catch (SQLException localSQLException)
              {
              }
            }
          }
        }
      }
    }
  }

  private void runInactivityTimeout()
  {
    try
    {
      this.implicitCache.doForEveryCachedConnection(4);
    }
    catch (SQLException localSQLException)
    {
    }
  }

  private void runAbandonedTimeout(long paramLong)
    throws SQLException
  {
    if (this.implicitCache.getNumberOfCheckedOutConnections() > 0)
    {
      OraclePooledConnection localOraclePooledConnection = null;

      synchronized (this.implicitCache)
      {
        Object[] arrayOfObject = this.implicitCache.checkedOutConnectionList.toArray();

        for (int i = 0; i < arrayOfObject.length; i++)
        {
          localOraclePooledConnection = (OraclePooledConnection)arrayOfObject[i];

          OracleConnection localOracleConnection = (OracleConnection)localOraclePooledConnection.getLogicalHandle();

          if (localOracleConnection != null)
          {
            OracleConnectionCacheCallback localOracleConnectionCacheCallback = localOracleConnection.getConnectionCacheCallbackObj();

            if (localOracleConnection.getHeartbeatNoChangeCount() * this.implicitCache.getCachePropertyCheckInterval() > paramLong)
            {
              try
              {
                boolean bool = true;
                if ((localOracleConnectionCacheCallback != null) && ((localOracleConnection.getConnectionCacheCallbackFlag() == 4) || (localOracleConnection.getConnectionCacheCallbackFlag() == 1)))
                {
                  bool = localOracleConnectionCacheCallback.handleAbandonedConnection(localOracleConnection, localOracleConnection.getConnectionCacheCallbackPrivObj());
                }

                if (bool)
                  this.implicitCache.closeCheckedOutConnection(localOraclePooledConnection, true);
              }
              catch (SQLException localSQLException)
              {
              }
            }
          }
        }
      }
    }
  }
}