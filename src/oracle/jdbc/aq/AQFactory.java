package oracle.jdbc.aq;

import java.sql.SQLException;
import oracle.jdbc.driver.InternalFactory;

public abstract class AQFactory
{
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  public static AQMessage createAQMessage(AQMessageProperties paramAQMessageProperties)
    throws SQLException
  {
    return InternalFactory.createAQMessage(paramAQMessageProperties);
  }

  public static AQMessageProperties createAQMessageProperties()
    throws SQLException
  {
    return InternalFactory.createAQMessageProperties();
  }

  public static AQAgent createAQAgent()
    throws SQLException
  {
    return InternalFactory.createAQAgent();
  }
}