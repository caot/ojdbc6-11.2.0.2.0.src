package oracle.jdbc.driver;

import java.sql.SQLException;
import java.util.EventListener;
import java.util.Properties;
import java.util.concurrent.Executor;
import oracle.jdbc.NotificationRegistration.RegistrationState;
import oracle.jdbc.aq.AQNotificationEvent.EventType;
import oracle.jdbc.aq.AQNotificationListener;
import oracle.jdbc.dcn.DatabaseChangeListener;
import oracle.jdbc.internal.OracleConnection;

abstract class NTFRegistration
{
  private final boolean jdbcGetsNotification;
  private final String clientHost;
  private final int clientTCPPort;
  private final Properties options;
  private final boolean isPurgeOnNTF;
  private final String username;
  private final int namespace;
  private final int jdbcRegId;
  private final String dbName;
  private final short databaseVersion;
  private NotificationRegistration.RegistrationState state;
  private NTFEventListener[] listeners = new NTFEventListener[0];

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  NTFRegistration(int paramInt1, int paramInt2, boolean paramBoolean, String paramString1, String paramString2, int paramInt3, Properties paramProperties, String paramString3, short paramShort)
  {
    this.namespace = paramInt2;
    this.clientHost = paramString2;
    this.clientTCPPort = paramInt3;
    this.options = paramProperties;
    this.jdbcRegId = paramInt1;
    this.username = paramString3;
    this.jdbcGetsNotification = paramBoolean;
    this.dbName = paramString1;
    this.state = NotificationRegistration.RegistrationState.ACTIVE;
    if (this.options.getProperty("NTF_QOS_PURGE_ON_NTFN", "false").compareToIgnoreCase("true") == 0)
    {
      this.isPurgeOnNTF = true;
    }
    else this.isPurgeOnNTF = false;
    this.databaseVersion = paramShort;
  }

  short getDatabaseVersion()
  {
    return this.databaseVersion;
  }

  synchronized void addListener(NTFEventListener paramNTFEventListener)
    throws SQLException
  {
    SQLException localSQLException1;
    if (this.state == NotificationRegistration.RegistrationState.CLOSED)
    {
      localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 251);
      localSQLException1.fillInStackTrace();
      throw localSQLException1;
    }

    if (!this.jdbcGetsNotification)
    {
      localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 247);
      localSQLException1.fillInStackTrace();
      throw localSQLException1;
    }

    int i = this.listeners.length;
    for (int j = 0; j < i; j++) {
      if (this.listeners[j].getListener() == paramNTFEventListener.getListener())
      {
        SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 248);
        localSQLException2.fillInStackTrace();
        throw localSQLException2;
      }
    }

    NTFEventListener[] arrayOfNTFEventListener = new NTFEventListener[i + 1];
    System.arraycopy(this.listeners, 0, arrayOfNTFEventListener, 0, i);
    arrayOfNTFEventListener[i] = paramNTFEventListener;

    this.listeners = arrayOfNTFEventListener;
  }

  synchronized void removeListener(EventListener paramEventListener)
    throws SQLException
  {
    int i = 0;
    int j = this.listeners.length;

    for (i = 0; (i < j) && 
      (this.listeners[i].getListener() != paramEventListener); i++);
    if (i == j)
    {
      localObject = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 249);
      ((SQLException)localObject).fillInStackTrace();
      throw ((Throwable)localObject);
    }

    Object localObject = new NTFEventListener[j - 1];
    int k = 0;
    for (i = 0; i < j; i++) {
      if (this.listeners[i].getListener() != paramEventListener) {
        localObject[(k++)] = this.listeners[i];
      }
    }
    this.listeners = ((NTFEventListener[])localObject);
  }

  void notify(final NTFDCNEvent paramNTFDCNEvent)
  {
    long l = 0L;

    NTFEventListener[] arrayOfNTFEventListener = this.listeners;

    int i = arrayOfNTFEventListener.length;
    for (int j = 0; j < i; j++)
    {
      Executor localExecutor = arrayOfNTFEventListener[j].getExecutor();

      if (localExecutor != null)
      {
        final DatabaseChangeListener localDatabaseChangeListener = arrayOfNTFEventListener[j].getDCNListener();

        localExecutor.execute(new Runnable() {
          public void run() {
            localDatabaseChangeListener.onDatabaseChangeNotification(paramNTFDCNEvent);
          }
        });
      }
      else
      {
        arrayOfNTFEventListener[j].getDCNListener().onDatabaseChangeNotification(paramNTFDCNEvent);
      }

    }

    if ((paramNTFDCNEvent.isDeregistrationEvent()) || (this.isPurgeOnNTF))
    {
      PhysicalConnection.ntfManager.removeRegistration(this);
      PhysicalConnection.ntfManager.freeJdbcRegId(getJdbcRegId());
      PhysicalConnection.ntfManager.cleanListenersT4C(getClientTCPPort());
      this.state = NotificationRegistration.RegistrationState.CLOSED;
    }
  }

  void notify(final NTFAQEvent paramNTFAQEvent)
  {
    long l = 0L;

    NTFEventListener[] arrayOfNTFEventListener = this.listeners;

    int i = arrayOfNTFEventListener.length;
    for (int j = 0; j < i; j++)
    {
      Executor localExecutor = arrayOfNTFEventListener[j].getExecutor();

      if (localExecutor != null)
      {
        final AQNotificationListener localAQNotificationListener = arrayOfNTFEventListener[j].getAQListener();

        localExecutor.execute(new Runnable() {
          public void run() {
            localAQNotificationListener.onAQNotification(paramNTFAQEvent);
          }
        });
      }
      else
      {
        arrayOfNTFEventListener[j].getAQListener().onAQNotification(paramNTFAQEvent);
      }

    }

    if ((paramNTFAQEvent.getEventType() == AQNotificationEvent.EventType.DEREG) || (this.isPurgeOnNTF))
    {
      PhysicalConnection.ntfManager.removeRegistration(this);
      PhysicalConnection.ntfManager.freeJdbcRegId(getJdbcRegId());
      PhysicalConnection.ntfManager.cleanListenersT4C(getClientTCPPort());
      this.state = NotificationRegistration.RegistrationState.CLOSED;
    }
  }

  public Properties getRegistrationOptions()
  {
    return this.options;
  }

  int getJdbcRegId()
  {
    return this.jdbcRegId;
  }

  public String getUserName()
  {
    return this.username;
  }

  String getClientHost() {
    return this.clientHost;
  }

  int getClientTCPPort()
  {
    return this.clientTCPPort;
  }

  public String getDatabaseName() {
    return this.dbName;
  }

  public NotificationRegistration.RegistrationState getState() {
    return this.state;
  }

  protected void setState(NotificationRegistration.RegistrationState paramRegistrationState) {
    this.state = paramRegistrationState;
  }

  int getNamespace() {
    return this.namespace;
  }

  protected OracleConnection getConnectionDuringExceptionHandling()
  {
    return null;
  }
}