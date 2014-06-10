package oracle.jdbc.dcn;

import java.util.EventObject;

public abstract class DatabaseChangeEvent extends EventObject
{
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  protected DatabaseChangeEvent(Object paramObject)
  {
    super(paramObject);
  }

  public abstract EventType getEventType();

  public abstract AdditionalEventType getAdditionalEventType();

  public abstract TableChangeDescription[] getTableChangeDescription();

  public abstract QueryChangeDescription[] getQueryChangeDescription();

  public abstract String getConnectionInformation();

  public abstract String getDatabaseName();

  /** @deprecated */
  public abstract int getRegistrationId();

  public abstract long getRegId();

  public abstract byte[] getTransactionId();

  public abstract String getTransactionId(boolean paramBoolean);

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
    NONE(0), 

    STARTUP(1), 

    SHUTDOWN(2), 

    SHUTDOWN_ANY(3), 

    DEREG(5), 

    OBJCHANGE(6), 

    QUERYCHANGE(7);

    private final int code;

    private EventType(int paramInt) {
      this.code = paramInt;
    }

    public final int getCode()
    {
      return this.code;
    }

    public static final EventType getEventType(int paramInt)
    {
      if (paramInt == STARTUP.getCode())
        return STARTUP;
      if (paramInt == SHUTDOWN.getCode())
        return SHUTDOWN;
      if (paramInt == SHUTDOWN_ANY.getCode())
        return SHUTDOWN_ANY;
      if (paramInt == DEREG.getCode())
        return DEREG;
      if (paramInt == OBJCHANGE.getCode())
        return OBJCHANGE;
      if (paramInt == QUERYCHANGE.getCode()) {
        return QUERYCHANGE;
      }
      return NONE;
    }
  }
}