package oracle.jdbc.aq;

import java.sql.SQLException;
import java.util.EventObject;

public abstract class AQNotificationEvent extends EventObject
{
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  protected AQNotificationEvent(Object paramObject)
  {
    super(paramObject);
  }

  public abstract AQMessageProperties getMessageProperties()
    throws SQLException;

  public abstract String getRegistration()
    throws SQLException;

  public abstract byte[] getPayload()
    throws SQLException;

  public abstract String getQueueName()
    throws SQLException;

  public abstract byte[] getMessageId()
    throws SQLException;

  public abstract String getConsumerName()
    throws SQLException;

  public abstract String getConnectionInformation();

  public abstract EventType getEventType();

  public abstract AdditionalEventType getAdditionalEventType();

  public abstract String toString();

  public static enum AdditionalEventType
  {
    NONE(0), 

    TIMEOUT(1), 

    GROUPING(2);

    private final int code;

    private AdditionalEventType(int paramInt)
    {
      this.code = paramInt;
    }

    public final int getCode()
    {
      return this.code;
    }

    public static final AdditionalEventType getEventType(int paramInt)
    {
      if (paramInt == TIMEOUT.getCode())
        return TIMEOUT;
      if (paramInt == GROUPING.getCode()) {
        return GROUPING;
      }
      return NONE;
    }
  }

  public static enum EventType
  {
    REGULAR(0), 

    DEREG(1);

    private final int code;

    private EventType(int paramInt) {
      this.code = paramInt;
    }

    public final int getCode()
    {
      return this.code;
    }
  }
}