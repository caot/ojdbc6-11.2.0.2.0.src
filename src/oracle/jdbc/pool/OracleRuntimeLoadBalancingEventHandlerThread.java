package oracle.jdbc.pool;

import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.sql.SQLException;
import oracle.ons.Notification;
import oracle.ons.ONSException;
import oracle.ons.Subscriber;
import oracle.ons.SubscriptionException;

/** @deprecated */
class OracleRuntimeLoadBalancingEventHandlerThread extends Thread
{
  private Notification event = null;
  private OracleConnectionCacheManager cacheManager = null;
  String m_service;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  OracleRuntimeLoadBalancingEventHandlerThread(String paramString)
    throws SQLException
  {
    this.m_service = paramString;
    this.cacheManager = OracleConnectionCacheManager.getConnectionCacheManagerInstance();
  }

  public void run()
  {
    Subscriber localSubscriber = null;

    final String str = "%\"eventType=database/event/servicemetrics/" + this.m_service + "\"";

    while (this.cacheManager.failoverEnabledCacheExists())
    {
      try
      {
        localSubscriber = (Subscriber)AccessController.doPrivileged(new PrivilegedExceptionAction()
        {
          public Object run()
          {
            try
            {
              return new Subscriber(str, "", 30000L);
            }
            catch (SubscriptionException localSubscriptionException)
            {
            }
            return null;
          }

        });
      }
      catch (PrivilegedActionException localPrivilegedActionException)
      {
      }

      if (localSubscriber != null)
      {
        try
        {
          while (this.cacheManager.failoverEnabledCacheExists())
          {
            if ((this.event = localSubscriber.receive(300000L)) != null)
              handleEvent(this.event);
          }
        }
        catch (ONSException localONSException)
        {
          localSubscriber.close();
        }

      }

      try
      {
        Thread.currentThread(); Thread.sleep(10000L);
      }
      catch (InterruptedException localInterruptedException)
      {
      }
    }
  }

  void handleEvent(Notification paramNotification)
  {
    try
    {
      this.cacheManager.parseRuntimeLoadBalancingEvent(this.m_service, paramNotification == null ? null : paramNotification.body());
    }
    catch (SQLException localSQLException)
    {
    }
  }
}