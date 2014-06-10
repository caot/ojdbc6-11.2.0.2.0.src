package oracle.jdbc.aq;

import java.sql.SQLException;
import java.sql.Timestamp;

public abstract interface AQMessageProperties
{
  public static final int MESSAGE_NO_DELAY = 0;
  public static final int MESSAGE_NO_EXPIRATION = -1;

  public abstract int getDequeueAttemptsCount();

  public abstract void setCorrelation(String paramString)
    throws SQLException;

  public abstract String getCorrelation();

  public abstract void setDelay(int paramInt)
    throws SQLException;

  public abstract int getDelay();

  public abstract Timestamp getEnqueueTime();

  public abstract void setExceptionQueue(String paramString)
    throws SQLException;

  public abstract String getExceptionQueue();

  public abstract void setExpiration(int paramInt)
    throws SQLException;

  public abstract int getExpiration();

  public abstract MessageState getState();

  public abstract void setPriority(int paramInt)
    throws SQLException;

  public abstract int getPriority();

  public abstract void setRecipientList(AQAgent[] paramArrayOfAQAgent)
    throws SQLException;

  public abstract AQAgent[] getRecipientList();

  public abstract void setSender(AQAgent paramAQAgent)
    throws SQLException;

  public abstract AQAgent getSender();

  public abstract String getTransactionGroup();

  public abstract byte[] getPreviousQueueMessageId();

  public abstract DeliveryMode getDeliveryMode();

  public abstract String toString();

  public static enum DeliveryMode
  {
    PERSISTENT(1), 

    BUFFERED(2);

    private final int code;

    private DeliveryMode(int paramInt) { this.code = paramInt; }


    public final int getCode()
    {
      return this.code;
    }

    public static final DeliveryMode getDeliveryMode(int paramInt)
    {
      if (paramInt == BUFFERED.getCode()) {
        return BUFFERED;
      }
      return PERSISTENT;
    }
  }

  public static enum MessageState
  {
    WAITING(1), 

    READY(0), 

    PROCESSED(2), 

    EXPIRED(3);

    private final int code;

    private MessageState(int paramInt) { this.code = paramInt; }


    public final int getCode()
    {
      return this.code;
    }

    public static final MessageState getMessageState(int paramInt)
    {
      if (paramInt == WAITING.getCode())
        return WAITING;
      if (paramInt == READY.getCode())
        return READY;
      if (paramInt == PROCESSED.getCode()) {
        return PROCESSED;
      }
      return EXPIRED;
    }
  }
}