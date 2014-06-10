package oracle.jdbc.driver;

import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.Executor;
import oracle.jdbc.aq.AQNotificationListener;
import oracle.jdbc.aq.AQNotificationRegistration;

class NTFAQRegistration extends NTFRegistration
  implements AQNotificationRegistration
{
  private final String name;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  NTFAQRegistration(int paramInt1, boolean paramBoolean, String paramString1, String paramString2, String paramString3, int paramInt2, Properties paramProperties, String paramString4, short paramShort)
  {
    super(paramInt1, 1, paramBoolean, paramString1, paramString3, paramInt2, paramProperties, paramString2, paramShort);

    this.name = paramString4;
  }

  public void addListener(AQNotificationListener paramAQNotificationListener, Executor paramExecutor)
    throws SQLException
  {
    NTFEventListener localNTFEventListener = new NTFEventListener(paramAQNotificationListener);
    localNTFEventListener.setExecutor(paramExecutor);
    addListener(localNTFEventListener);
  }

  public void addListener(AQNotificationListener paramAQNotificationListener)
    throws SQLException
  {
    NTFEventListener localNTFEventListener = new NTFEventListener(paramAQNotificationListener);
    addListener(localNTFEventListener);
  }

  public void removeListener(AQNotificationListener paramAQNotificationListener)
    throws SQLException
  {
    super.removeListener(paramAQNotificationListener);
  }

  public String getQueueName()
  {
    return this.name;
  }
}