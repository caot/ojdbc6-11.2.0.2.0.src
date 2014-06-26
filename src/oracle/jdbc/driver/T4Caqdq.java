package oracle.jdbc.driver;

import java.io.IOException;
import java.sql.SQLException;
import oracle.jdbc.aq.AQAgent;
import oracle.jdbc.aq.AQDequeueOptions;
import oracle.jdbc.aq.AQDequeueOptions.DeliveryFilter;
import oracle.jdbc.aq.AQDequeueOptions.DequeueMode;
import oracle.jdbc.aq.AQDequeueOptions.NavigationOption;
import oracle.jdbc.aq.AQDequeueOptions.VisibilityOption;
import oracle.jdbc.aq.AQMessageProperties.DeliveryMode;
import oracle.jdbc.aq.AQMessageProperties.MessageState;
import oracle.jdbc.internal.OracleConnection;
import oracle.sql.TIMESTAMP;

final class T4Caqdq extends T4CTTIfun
{
  T4CTTIaqm aqm;
  T4Ctoh toh;
  private String queueName;
  private AQDequeueOptions dequeueOptions = null;
  private byte[] payloadToid = null;
  private byte[] queueNameBytes = null;
  private byte[] consumerNameBytes = null;
  private byte[] correlationBytes = null;
  private byte[] conditionBytes = null;
  private int nbExtensions = 0;
  private byte[][] extensionTextValues = (byte[][])null;
  private byte[][] extensionBinaryValues = (byte[][])null;
  private int[] extensionKeywords = null;

  private byte[] payload = null;
  private boolean hasAMessageBeenDequeued = false;
  private byte[] dequeuedMessageId = null;
  private boolean isRawQueue = false;
  private AQMessagePropertiesI properties = null;

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  T4Caqdq(T4CConnection paramT4CConnection)
  {
    super(paramT4CConnection, (byte)3);

    setFunCode((short)122);
    this.toh = new T4Ctoh();
    this.aqm = new T4CTTIaqm(this.connection, this.toh);
  }

  void doOAQDQ(String paramString, AQDequeueOptions paramAQDequeueOptions, byte[] paramArrayOfByte, boolean paramBoolean, AQMessagePropertiesI paramAQMessagePropertiesI)
    throws SQLException, IOException
  {
    this.queueName = paramString;
    this.dequeueOptions = paramAQDequeueOptions;
    this.payloadToid = paramArrayOfByte;
    this.isRawQueue = paramBoolean;
    this.properties = paramAQMessagePropertiesI;

    if ((this.queueName != null) && (this.queueName.length() != 0))
      this.queueNameBytes = this.meg.conv.StringToCharBytes(this.queueName);
    else {
      this.queueNameBytes = null;
    }
    String str1 = this.dequeueOptions.getConsumerName();

    if ((str1 != null) && (str1.length() > 0))
    {
      this.consumerNameBytes = this.meg.conv.StringToCharBytes(str1);
    }
    else {
      this.consumerNameBytes = null;
    }
    String str2 = this.dequeueOptions.getCorrelation();
    if ((str2 != null) && (str2.length() != 0))
      this.correlationBytes = this.meg.conv.StringToCharBytes(str2);
    else {
      this.correlationBytes = null;
    }
    String str3 = this.dequeueOptions.getCondition();
    if ((str3 != null) && (str3.length() > 0))
      this.conditionBytes = this.meg.conv.StringToCharBytes(str3);
    else {
      this.conditionBytes = null;
    }
    String str4 = this.dequeueOptions.getTransformation();
    if ((str4 != null) && (str4.length() > 0))
    {
      this.nbExtensions = 1;
      this.extensionTextValues = new byte[this.nbExtensions][];
      this.extensionBinaryValues = new byte[this.nbExtensions][];
      this.extensionKeywords = new int[this.nbExtensions];
      this.extensionTextValues[0] = this.meg.conv.StringToCharBytes(str4);
      this.extensionBinaryValues[0] = null;
      this.extensionKeywords[0] = 196;
    }
    else {
      this.nbExtensions = 0;
    }
    this.hasAMessageBeenDequeued = false;
    this.dequeuedMessageId = null;
    this.payload = null;

    doRPC();
  }

  void marshal()
    throws IOException
  {
    if ((this.queueNameBytes != null) && (this.queueNameBytes.length != 0))
    {
      this.meg.marshalPTR();
      this.meg.marshalSWORD(this.queueNameBytes.length);
    }
    else
    {
      this.meg.marshalNULLPTR();
      this.meg.marshalSWORD(0);
    }

    this.meg.marshalPTR();
    this.meg.marshalPTR();

    this.meg.marshalPTR();
    this.meg.marshalPTR();

    if ((this.consumerNameBytes != null) && (this.consumerNameBytes.length != 0))
    {
      this.meg.marshalPTR();
      this.meg.marshalSWORD(this.consumerNameBytes.length);
    }
    else
    {
      this.meg.marshalNULLPTR();
      this.meg.marshalSWORD(0);
    }

    this.meg.marshalSB4(this.dequeueOptions.getDequeueMode().getCode());

    this.meg.marshalSB4(this.dequeueOptions.getNavigation().getCode());

    this.meg.marshalSB4(this.dequeueOptions.getVisibility().getCode());

    this.meg.marshalSB4(this.dequeueOptions.getWait());

    byte[] arrayOfByte = this.dequeueOptions.getDequeueMessageId();
    int i = 0;
    if ((arrayOfByte != null) && (arrayOfByte.length > 0))
    {
      this.meg.marshalPTR();
      this.meg.marshalSWORD(arrayOfByte.length);
      i = 1;
    }
    else
    {
      this.meg.marshalNULLPTR();
      this.meg.marshalSWORD(0);
    }

    if ((this.correlationBytes != null) && (this.correlationBytes.length != 0))
    {
      this.meg.marshalPTR();
      this.meg.marshalSWORD(this.correlationBytes.length);
    }
    else
    {
      this.meg.marshalNULLPTR();
      this.meg.marshalSWORD(0);
    }

    this.meg.marshalPTR();
    this.meg.marshalSWORD(this.payloadToid.length);

    this.meg.marshalUB2(1);

    this.meg.marshalPTR();

    if (this.dequeueOptions.getRetrieveMessageId())
    {
      this.meg.marshalPTR();
      this.meg.marshalSWORD(16);
    }
    else
    {
      this.meg.marshalNULLPTR();
      this.meg.marshalSWORD(0);
    }

    int j = 0;
    if (this.connection.autocommit)
      j = 32;
    if (this.dequeueOptions.getDeliveryFilter() == AQDequeueOptions.DeliveryFilter.BUFFERED)
      j |= 2;
    else if (this.dequeueOptions.getDeliveryFilter() == AQDequeueOptions.DeliveryFilter.PERSISTENT_OR_BUFFERED)
    {
      j |= 16;
    }this.meg.marshalUB4(j);

    if ((this.conditionBytes != null) && (this.conditionBytes.length > 0))
    {
      this.meg.marshalPTR();
      this.meg.marshalSWORD(this.conditionBytes.length);
    }
    else
    {
      this.meg.marshalNULLPTR();
      this.meg.marshalSWORD(0);
    }

    if (this.nbExtensions > 0)
    {
      this.meg.marshalPTR();
      this.meg.marshalSWORD(this.nbExtensions);
    }
    else
    {
      this.meg.marshalNULLPTR();
      this.meg.marshalSWORD(0);
    }

    if ((this.queueNameBytes != null) && (this.queueNameBytes.length != 0)) {
      this.meg.marshalCHR(this.queueNameBytes);
    }

    if ((this.consumerNameBytes != null) && (this.consumerNameBytes.length != 0)) {
      this.meg.marshalCHR(this.consumerNameBytes);
    }

    if (i != 0) {
      this.meg.marshalB1Array(arrayOfByte);
    }

    if ((this.correlationBytes != null) && (this.correlationBytes.length != 0)) {
      this.meg.marshalCHR(this.correlationBytes);
    }

    this.meg.marshalB1Array(this.payloadToid);

    if ((this.conditionBytes != null) && (this.conditionBytes.length > 0)) {
      this.meg.marshalCHR(this.conditionBytes);
    }
    if (this.nbExtensions > 0)
      this.meg.marshalKPDKV(this.extensionTextValues, this.extensionBinaryValues, this.extensionKeywords);
  }

  byte[] getPayload()
  {
    return this.payload;
  }

  boolean hasAMessageBeenDequeued()
  {
    return this.hasAMessageBeenDequeued;
  }

  byte[] getDequeuedMessageId()
  {
    return this.dequeuedMessageId;
  }

  void readRPA()
    throws SQLException, IOException
  {
    this.hasAMessageBeenDequeued = true;

    int i = (int)this.meg.unmarshalUB4();
    if (i > 0)
    {
      this.aqm.initToDefaultValues();
      this.aqm.receive();
      this.properties.setPriority(this.aqm.aqmpri);
      this.properties.setDelay(this.aqm.aqmdel);
      this.properties.setExpiration(this.aqm.aqmexp);
      String localObject;
      if (this.aqm.aqmcorBytes != null)
      {
        localObject = this.meg.conv.CharBytesToString(this.aqm.aqmcorBytes, this.aqm.aqmcorBytesLength, true);

        this.properties.setCorrelation((String)localObject);
      }
      this.properties.setAttempts(this.aqm.aqmatt);
      if (this.aqm.aqmeqnBytes != null)
      {
        localObject = this.meg.conv.CharBytesToString(this.aqm.aqmeqnBytes, this.aqm.aqmeqnBytesLength, true);

        this.properties.setExceptionQueue((String)localObject);
      }
      this.properties.setMessageState(MessageState.getMessageState(this.aqm.aqmsta));
      this.properties.setEnqueueTime(this.aqm.aqmeqt.timestampValue());
      AQAgentI aqagenti = new AQAgentI();
      if (this.aqm.senderAgentName != null) {
        aqagenti.setName(this.meg.conv.CharBytesToString(this.aqm.senderAgentName, this.aqm.senderAgentNameLength, true));
      }

      if (this.aqm.senderAgentAddress != null) {
        aqagenti.setAddress(this.meg.conv.CharBytesToString(this.aqm.senderAgentAddress, this.aqm.senderAgentAddressLength, true));
      }

      aqagenti.setProtocol(this.aqm.senderAgentProtocol);

      this.properties.setSender((AQAgent)aqagenti);
      this.properties.setPreviousQueueMessageId(this.aqm.originalMsgId);
      this.properties.setDeliveryMode(DeliveryMode.getDeliveryMode(this.aqm.aqmflg));

      if (this.aqm.aqmetiBytes != null)
      {
        String str = this.meg.conv.CharBytesToString(this.aqm.aqmetiBytes, this.aqm.aqmetiBytes.length, true);

        this.properties.setTransactionGroup(str);
      }
    }

    int j = (int)this.meg.unmarshalUB4();

    this.toh.unmarshal(this.meg);
    int k = this.toh.imageLength;

    if (k > 0)
    {
      int m = k;
      byte[] arrayOfByte2;
      int[] arrayOfInt;
      if (this.isRawQueue)
      {
        if (k > 4) {
          m -= 4;
        }

        m = Math.min(m, this.dequeueOptions.getMaximumBufferLength());

        arrayOfByte2 = new byte[m];
        arrayOfInt = new int[1];
        if (k > 4)
          this.meg.unmarshalCLR(arrayOfByte2, 0, arrayOfInt, arrayOfByte2.length, 4);
        else
          this.meg.unmarshalCLR(arrayOfByte2, 0, arrayOfInt, arrayOfByte2.length);
        this.payload = arrayOfByte2;
      }
      else
      {
        arrayOfByte2 = new byte[m];
        arrayOfInt = new int[1];
        this.meg.unmarshalCLR(arrayOfByte2, 0, arrayOfInt, arrayOfByte2.length);
        this.payload = arrayOfByte2;
      }

    }

    if (this.dequeueOptions.getRetrieveMessageId())
    {
      byte[] arrayOfByte1 = new byte[16];
      this.meg.unmarshalBuffer(arrayOfByte1, 0, 16);
      this.dequeuedMessageId = arrayOfByte1;
    }
  }

  void processError()
    throws SQLException
  {
    if (this.oer.retCode != 25228)
    {
      this.oer.processError();
    }
  }

  protected OracleConnection getConnectionDuringExceptionHandling()
  {
    return null;
  }
}