package oracle.jdbc.driver;

import java.sql.SQLException;
import java.sql.Timestamp;
import oracle.jdbc.aq.AQAgent;
import oracle.jdbc.aq.AQMessageProperties;
import oracle.jdbc.aq.AQMessageProperties.DeliveryMode;
import oracle.jdbc.aq.AQMessageProperties.MessageState;

class AQMessagePropertiesI
  implements AQMessageProperties
{
  private int attrAttempts;
  private String attrCorrelation;
  private int attrDelay;
  private Timestamp attrEnqTime;
  private String attrExceptionQueue;
  private int attrExpiration;
  private AQMessageProperties.MessageState attrMsgState;
  private int attrPriority;
  private AQAgentI[] attrRecipientList;
  private AQAgentI attrSenderId;
  private String attrTransactionGroup;
  private byte[] attrPreviousQueueMsgId;
  private AQMessageProperties.DeliveryMode deliveryMode;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  AQMessagePropertiesI()
  {
    this.attrAttempts = -1;
    this.attrCorrelation = null;
    this.attrDelay = 0;
    this.attrEnqTime = null;
    this.attrExceptionQueue = null;
    this.attrExpiration = -1;
    this.attrMsgState = null;
    this.attrPriority = 0;
    this.attrRecipientList = null;
    this.attrSenderId = null;
    this.attrTransactionGroup = null;
    this.attrPreviousQueueMsgId = null;
    this.deliveryMode = null;
  }

  public int getDequeueAttemptsCount()
  {
    return this.attrAttempts;
  }

  public void setCorrelation(String paramString)
    throws SQLException
  {
    this.attrCorrelation = paramString;
  }

  public String getCorrelation()
  {
    return this.attrCorrelation;
  }

  public void setDelay(int paramInt)
    throws SQLException
  {
    this.attrDelay = paramInt;
  }

  public int getDelay()
  {
    return this.attrDelay;
  }

  public Timestamp getEnqueueTime()
  {
    return this.attrEnqTime;
  }

  public void setExceptionQueue(String paramString)
    throws SQLException
  {
    this.attrExceptionQueue = paramString;
  }

  public String getExceptionQueue()
  {
    return this.attrExceptionQueue;
  }

  public void setExpiration(int paramInt)
    throws SQLException
  {
    this.attrExpiration = paramInt;
  }

  public int getExpiration()
  {
    return this.attrExpiration;
  }

  public AQMessageProperties.MessageState getState()
  {
    return this.attrMsgState;
  }

  public void setPriority(int paramInt)
    throws SQLException
  {
    this.attrPriority = paramInt;
  }

  public int getPriority()
  {
    return this.attrPriority;
  }

  public void setRecipientList(AQAgent[] paramArrayOfAQAgent)
    throws SQLException
  {
    this.attrRecipientList = new AQAgentI[paramArrayOfAQAgent.length];
    for (int i = 0; i < paramArrayOfAQAgent.length; i++)
      this.attrRecipientList[i] = ((AQAgentI)paramArrayOfAQAgent[i]);
  }

  public AQAgent[] getRecipientList()
  {
    return this.attrRecipientList;
  }

  public void setSender(AQAgent paramAQAgent)
    throws SQLException
  {
    this.attrSenderId = ((AQAgentI)paramAQAgent);
  }

  public AQAgent getSender()
  {
    return this.attrSenderId;
  }

  public String getTransactionGroup()
  {
    return this.attrTransactionGroup;
  }

  void setTransactionGroup(String paramString)
  {
    this.attrTransactionGroup = paramString;
  }

  void setPreviousQueueMessageId(byte[] paramArrayOfByte)
  {
    this.attrPreviousQueueMsgId = paramArrayOfByte;
  }

  public byte[] getPreviousQueueMessageId()
  {
    return this.attrPreviousQueueMsgId;
  }

  public AQMessageProperties.DeliveryMode getDeliveryMode()
  {
    return this.deliveryMode;
  }

  void setDeliveryMode(AQMessageProperties.DeliveryMode paramDeliveryMode)
  {
    this.deliveryMode = paramDeliveryMode;
  }

  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("Correlation             : " + getCorrelation() + "\n");
    Timestamp localTimestamp = getEnqueueTime();
    if (localTimestamp != null)
      localStringBuffer.append("Enqueue time            : " + localTimestamp + "\n");
    localStringBuffer.append("Exception Queue         : " + getExceptionQueue() + "\n");
    localStringBuffer.append("Sender                  : (" + getSender() + ")\n");
    int i = getDequeueAttemptsCount();
    if (i != -1)
      localStringBuffer.append("Attempts                : " + i + "\n");
    localStringBuffer.append("Delay                   : " + getDelay() + "\n");
    localStringBuffer.append("Expiration              : " + getExpiration() + "\n");
    AQMessageProperties.MessageState localMessageState = getState();

    if (localMessageState != null)
      localStringBuffer.append("State                   : " + localMessageState + "\n");
    localStringBuffer.append("Priority                : " + getPriority() + "\n");
    AQMessageProperties.DeliveryMode localDeliveryMode = getDeliveryMode();

    if (localDeliveryMode != null)
      localStringBuffer.append("Delivery Mode           : " + localDeliveryMode + "\n");
    localStringBuffer.append("Recipient List          : {");
    AQAgent[] arrayOfAQAgent = getRecipientList();
    if (arrayOfAQAgent != null)
    {
      for (int j = 0; j < arrayOfAQAgent.length; j++)
      {
        localStringBuffer.append(arrayOfAQAgent[j]);
        if (j != arrayOfAQAgent.length - 1)
          localStringBuffer.append("; ");
      }
    }
    localStringBuffer.append("}");

    return localStringBuffer.toString();
  }

  void setAttempts(int paramInt)
    throws SQLException
  {
    this.attrAttempts = paramInt;
  }

  void setEnqueueTime(Timestamp paramTimestamp)
    throws SQLException
  {
    this.attrEnqTime = paramTimestamp;
  }

  void setMessageState(AQMessageProperties.MessageState paramMessageState)
    throws SQLException
  {
    this.attrMsgState = paramMessageState;
  }
}