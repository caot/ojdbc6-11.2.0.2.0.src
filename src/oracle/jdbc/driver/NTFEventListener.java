package oracle.jdbc.driver;

import java.sql.SQLException;
import java.util.EventListener;
import java.util.concurrent.Executor;
import oracle.jdbc.aq.AQNotificationListener;
import oracle.jdbc.dcn.DatabaseChangeListener;
import oracle.jdbc.internal.OracleConnection;
import oracle.jdbc.internal.XSEventListener;

class NTFEventListener
{
  private final AQNotificationListener aqlistener;
  private final DatabaseChangeListener dcnlistener;
  private final XSEventListener xslistener;
  private Executor executor = null;

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  NTFEventListener(DatabaseChangeListener paramDatabaseChangeListener)
    throws SQLException
  {
    if (paramDatabaseChangeListener == null)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 246);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.dcnlistener = paramDatabaseChangeListener;
    this.aqlistener = null;
    this.xslistener = null;
  }

  NTFEventListener(AQNotificationListener paramAQNotificationListener)
    throws SQLException
  {
    if (paramAQNotificationListener == null)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 246);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.aqlistener = paramAQNotificationListener;
    this.dcnlistener = null;
    this.xslistener = null;
  }

  NTFEventListener(XSEventListener paramXSEventListener)
    throws SQLException
  {
    if (paramXSEventListener == null)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 246);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.aqlistener = null;
    this.dcnlistener = null;
    this.xslistener = paramXSEventListener;
  }

  void setExecutor(Executor paramExecutor)
  {
    this.executor = paramExecutor;
  }

  Executor getExecutor()
  {
    return this.executor;
  }

  EventListener getListener()
  {
    Object localObject = this.dcnlistener;
    if (localObject == null)
      localObject = this.aqlistener;
    return localObject;
  }

  AQNotificationListener getAQListener()
  {
    return this.aqlistener;
  }

  DatabaseChangeListener getDCNListener()
  {
    return this.dcnlistener;
  }

  XSEventListener getXSEventListener()
  {
    return this.xslistener;
  }

  protected OracleConnection getConnectionDuringExceptionHandling()
  {
    return null;
  }
}