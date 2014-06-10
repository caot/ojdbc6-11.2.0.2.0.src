package oracle.jdbc.driver;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.sql.SQLException;
import oracle.jdbc.aq.AQMessageProperties;
import oracle.jdbc.aq.AQMessageProperties.DeliveryMode;
import oracle.jdbc.aq.AQMessageProperties.MessageState;
import oracle.jdbc.aq.AQNotificationEvent;
import oracle.jdbc.aq.AQNotificationEvent.AdditionalEventType;
import oracle.jdbc.aq.AQNotificationEvent.EventType;
import oracle.sql.CharacterSet;
import oracle.sql.TIMESTAMP;

class NTFAQEvent extends AQNotificationEvent
{
  private String registrationString;
  private int namespace;
  private byte[] payload;
  private String queueName = null;
  private byte[] messageId = null;
  private String consumerName = null;
  private NTFConnection conn;
  private AQMessagePropertiesI msgProp;
  private AQNotificationEvent.EventType eventType = AQNotificationEvent.EventType.REGULAR;
  private AQNotificationEvent.AdditionalEventType additionalEventType = AQNotificationEvent.AdditionalEventType.NONE;
  private ByteBuffer dataBuffer;
  private boolean isReady = false;
  private short databaseVersion;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  NTFAQEvent(NTFConnection paramNTFConnection, short paramShort)
    throws IOException
  {
    super(paramNTFConnection);

    this.conn = paramNTFConnection;
    int i = this.conn.readInt();
    byte[] arrayOfByte = new byte[i];
    this.conn.readBuffer(arrayOfByte, 0, i);
    this.dataBuffer = ByteBuffer.wrap(arrayOfByte);
    this.databaseVersion = paramShort;
  }

  private void initEvent()
    throws SQLException
  {
    int i = this.dataBuffer.get();
    int j = this.dataBuffer.getInt();
    byte[] arrayOfByte1 = new byte[j];
    this.dataBuffer.get(arrayOfByte1, 0, j);
    this.registrationString = this.conn.charset.toString(arrayOfByte1, 0, j);

    int k = this.dataBuffer.get();
    int m = this.dataBuffer.getInt();
    byte[] arrayOfByte2 = new byte[m];
    this.dataBuffer.get(arrayOfByte2, 0, m);
    this.namespace = arrayOfByte2[0];

    int n = this.dataBuffer.get();
    int i1 = this.dataBuffer.getInt();
    if (i1 > 0)
    {
      this.payload = new byte[i1];
      this.dataBuffer.get(this.payload, 0, i1);
    }
    else {
      this.payload = null;
    }
    if (this.dataBuffer.hasRemaining())
    {
      int i2 = 0;
      if (this.databaseVersion >= 10200)
      {
        i3 = this.dataBuffer.get();
        i4 = this.dataBuffer.getInt();
        i2 = this.dataBuffer.getInt();
      }

      int i3 = this.dataBuffer.get();
      int i4 = this.dataBuffer.getInt();
      byte[] arrayOfByte3 = new byte[i4];
      this.dataBuffer.get(arrayOfByte3, 0, i4);
      this.queueName = this.conn.charset.toString(arrayOfByte3, 0, i4);

      int i5 = this.dataBuffer.get();
      int i6 = this.dataBuffer.getInt();
      this.messageId = new byte[i6];
      this.dataBuffer.get(this.messageId, 0, i6);

      int i7 = this.dataBuffer.get();
      int i8 = this.dataBuffer.getInt();
      byte[] arrayOfByte4 = new byte[i8];
      this.dataBuffer.get(arrayOfByte4, 0, i8);
      this.consumerName = this.conn.charset.toString(arrayOfByte4, 0, i8);

      int i9 = this.dataBuffer.get();
      int i10 = this.dataBuffer.getInt();
      byte[] arrayOfByte5 = new byte[i10];
      this.dataBuffer.get(arrayOfByte5, 0, i10);

      int i11 = this.dataBuffer.get();
      int i12 = this.dataBuffer.getInt();
      int i13 = this.dataBuffer.getInt();
      if (arrayOfByte5[0] == 1)
        i13 = -i13;
      int i14 = i13;

      int i15 = this.dataBuffer.get();
      int i16 = this.dataBuffer.getInt();
      int i17 = this.dataBuffer.getInt();

      int i18 = this.dataBuffer.get();
      int i19 = this.dataBuffer.getInt();
      byte[] arrayOfByte6 = new byte[i19];
      this.dataBuffer.get(arrayOfByte6, 0, i19);

      int i20 = this.dataBuffer.get();
      int i21 = this.dataBuffer.getInt();
      int i22 = this.dataBuffer.getInt();
      if (arrayOfByte6[0] == 1)
        i22 = -i22;
      int i23 = i22;

      int i24 = this.dataBuffer.get();
      int i25 = this.dataBuffer.getInt();
      int i26 = this.dataBuffer.getInt();

      int i27 = this.dataBuffer.get();
      int i28 = this.dataBuffer.getInt();
      byte[] arrayOfByte7 = new byte[i28];
      this.dataBuffer.get(arrayOfByte7, 0, i28);
      TIMESTAMP localTIMESTAMP = new TIMESTAMP(arrayOfByte7);

      int i29 = this.dataBuffer.get();
      int i30 = this.dataBuffer.getInt();
      byte[] arrayOfByte8 = new byte[i30];
      this.dataBuffer.get(arrayOfByte8, 0, i30);
      int i31 = arrayOfByte8[0];

      int i32 = this.dataBuffer.get();
      int i33 = this.dataBuffer.getInt();
      byte[] arrayOfByte9 = new byte[i33];
      this.dataBuffer.get(arrayOfByte9, 0, i33);
      String str1 = this.conn.charset.toString(arrayOfByte9, 0, i33);

      int i34 = this.dataBuffer.get();
      int i35 = this.dataBuffer.getInt();
      byte[] arrayOfByte10 = new byte[i35];
      this.dataBuffer.get(arrayOfByte10, 0, i35);
      String str2 = this.conn.charset.toString(arrayOfByte10, 0, i35);

      int i36 = this.dataBuffer.get();
      int i37 = this.dataBuffer.getInt();
      byte[] arrayOfByte11 = null;
      if (i37 > 0)
      {
        arrayOfByte11 = new byte[i37];
        this.dataBuffer.get(arrayOfByte11, 0, i37);
      }

      int i38 = this.dataBuffer.get();
      int i39 = this.dataBuffer.getInt();
      byte[] arrayOfByte12 = new byte[i39];
      this.dataBuffer.get(arrayOfByte12, 0, i39);
      String str3 = this.conn.charset.toString(arrayOfByte12, 0, i39);

      int i40 = this.dataBuffer.get();
      int i41 = this.dataBuffer.getInt();
      byte[] arrayOfByte13 = new byte[i41];
      this.dataBuffer.get(arrayOfByte13, 0, i41);
      String str4 = this.conn.charset.toString(arrayOfByte13, 0, i41);

      int i42 = this.dataBuffer.get();
      int i43 = this.dataBuffer.getInt();
      int i44 = this.dataBuffer.get();

      this.msgProp = new AQMessagePropertiesI();
      this.msgProp.setAttempts(i26);
      this.msgProp.setCorrelation(str2);
      this.msgProp.setDelay(i17);
      this.msgProp.setEnqueueTime(localTIMESTAMP.timestampValue());
      this.msgProp.setMessageState(AQMessageProperties.MessageState.getMessageState(i31));
      if (this.databaseVersion >= 10200)
        this.msgProp.setDeliveryMode(AQMessageProperties.DeliveryMode.getDeliveryMode(i2));
      this.msgProp.setPreviousQueueMessageId(arrayOfByte11);
      AQAgentI localAQAgentI = new AQAgentI();
      localAQAgentI.setAddress(str4);
      localAQAgentI.setName(str3);
      localAQAgentI.setProtocol(i44);
      this.msgProp.setSender(localAQAgentI);

      this.msgProp.setPriority(i14);
      this.msgProp.setExpiration(i23);
      this.msgProp.setExceptionQueue(str1);
    }
    this.isReady = true;
  }

  public AQMessageProperties getMessageProperties()
    throws SQLException
  {
    if (!this.isReady)
      initEvent();
    return this.msgProp;
  }

  public String getRegistration()
    throws SQLException
  {
    if (!this.isReady)
      initEvent();
    return this.registrationString;
  }

  public AQNotificationEvent.EventType getEventType()
  {
    return this.eventType;
  }

  public AQNotificationEvent.AdditionalEventType getAdditionalEventType()
  {
    return this.additionalEventType;
  }

  void setEventType(AQNotificationEvent.EventType paramEventType)
    throws IOException
  {
    this.eventType = paramEventType;
  }

  void setAdditionalEventType(AQNotificationEvent.AdditionalEventType paramAdditionalEventType)
  {
    this.additionalEventType = paramAdditionalEventType;
  }

  public byte[] getPayload()
    throws SQLException
  {
    if (!this.isReady)
      initEvent();
    return this.payload;
  }

  public String getQueueName()
    throws SQLException
  {
    if (!this.isReady)
      initEvent();
    return this.queueName;
  }

  public byte[] getMessageId()
    throws SQLException
  {
    if (!this.isReady)
      initEvent();
    return this.messageId;
  }

  public String getConsumerName()
    throws SQLException
  {
    if (!this.isReady)
      initEvent();
    return this.consumerName;
  }

  public String getConnectionInformation()
  {
    return this.conn.connectionDescription;
  }

  public String toString()
  {
    if (!this.isReady)
    {
      try
      {
        initEvent();
      }
      catch (SQLException localSQLException)
      {
        return localSQLException.getMessage();
      }
    }
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("Connection information  : " + this.conn.connectionDescription + "\n");
    localStringBuffer.append("Event type              : " + this.eventType + "\n");
    if (this.additionalEventType != AQNotificationEvent.AdditionalEventType.NONE)
      localStringBuffer.append("Additional event type   : " + this.additionalEventType + "\n");
    localStringBuffer.append("Namespace               : " + this.namespace + "\n");
    localStringBuffer.append("Registration            : " + this.registrationString + "\n");
    localStringBuffer.append("Queue name              : " + this.queueName + "\n");
    localStringBuffer.append("Consumer name           : " + this.consumerName + "\n");
    if (this.payload != null)
    {
      localStringBuffer.append("Payload length          : " + this.payload.length + "\n");
      localStringBuffer.append("Payload (first 50 bytes): " + byteBufferToHexString(this.payload, 50) + "\n");
    }
    else {
      localStringBuffer.append("Payload                 : null\n");
    }localStringBuffer.append("Message ID              : " + byteBufferToHexString(this.messageId, 50) + "\n");
    if (this.msgProp != null)
      localStringBuffer.append(this.msgProp.toString());
    return localStringBuffer.toString();
  }

  static final String byteBufferToHexString(byte[] paramArrayOfByte, int paramInt)
  {
    if (paramArrayOfByte == null) {
      return null;
    }
    int i = 0;
    int j = 1;
    StringBuffer localStringBuffer = new StringBuffer();
    while ((i < paramArrayOfByte.length) && (i < paramInt))
    {
      if (j == 0)
        localStringBuffer.append(' ');
      else
        j = 0;
      str = Integer.toHexString(paramArrayOfByte[i] & 0xFF);
      if (str.length() == 1)
        str = "0" + str;
      localStringBuffer.append(str);
      i++;
    }
    String str = localStringBuffer.toString();
    return str;
  }
}