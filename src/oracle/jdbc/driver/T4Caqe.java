package oracle.jdbc.driver;

import java.io.IOException;
import java.sql.SQLException;
import oracle.jdbc.aq.AQEnqueueOptions;
import oracle.jdbc.aq.AQEnqueueOptions.DeliveryMode;
import oracle.jdbc.aq.AQEnqueueOptions.SequenceDeviationOption;
import oracle.jdbc.aq.AQEnqueueOptions.VisibilityOption;
import oracle.jdbc.internal.OracleConnection;

final class T4Caqe extends T4CTTIfun
{
  static final int KPD_AQ_BUFMSG = 2;
  static final int KPD_AQ_EITHER = 16;
  static final int OCI_COMMIT_ON_SUCCESS = 32;
  static final int ATTR_TRANSFORMATION = 196;
  T4CTTIaqm aqm;
  T4Ctoh toh;
  private byte[] queueNameBytes = null;
  private AQEnqueueOptions enqueueOptions = null;
  private AQMessagePropertiesI messageProperties = null;
  private byte[] messageData = null;
  private byte[] messageOid = null;
  private boolean isRawQueue = false;
  private int nbExtensions = 0;
  private byte[][] extensionTextValues = (byte[][])null;
  private byte[][] extensionBinaryValues = (byte[][])null;
  private int[] extensionKeywords = null;
  private AQAgentI[] attrRecipientList = null;
  private byte[][] recipientTextValues = (byte[][])null;
  private byte[][] recipientBinaryValues = (byte[][])null;
  private int[] recipientKeywords = null;
  private byte[] aqmcorBytes;
  private byte[] aqmeqnBytes;
  private boolean retrieveMessageId = false;
  private byte[] outMsgid = null;
  private byte[] senderAgentName = null;
  private byte[] senderAgentAddress = null;
  private byte senderAgentProtocol = 0;

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  T4Caqe(T4CConnection paramT4CConnection)
  {
    super(paramT4CConnection, (byte)3);

    setFunCode((short)121);
    this.toh = new T4Ctoh();
    this.aqm = new T4CTTIaqm(this.connection, this.toh);
  }

  void doOAQEQ(String paramString, AQEnqueueOptions paramAQEnqueueOptions, AQMessagePropertiesI paramAQMessagePropertiesI, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, boolean paramBoolean)
    throws SQLException, IOException
  {
    this.enqueueOptions = paramAQEnqueueOptions;
    this.messageProperties = paramAQMessagePropertiesI;

    String str1 = this.messageProperties.getCorrelation();
    if ((str1 != null) && (str1.length() != 0))
      this.aqmcorBytes = this.meg.conv.StringToCharBytes(str1);
    else
      this.aqmcorBytes = null;
    String str2 = this.messageProperties.getExceptionQueue();
    if ((str2 != null) && (str2.length() != 0))
      this.aqmeqnBytes = this.meg.conv.StringToCharBytes(str2);
    else {
      this.aqmeqnBytes = null;
    }
    AQAgentI localAQAgentI = (AQAgentI)this.messageProperties.getSender();
    if (localAQAgentI != null)
    {
      if (localAQAgentI.getName() != null) {
        this.senderAgentName = this.meg.conv.StringToCharBytes(localAQAgentI.getName());
      }
      else
        this.senderAgentName = null;
      if (localAQAgentI.getAddress() != null) {
        this.senderAgentAddress = this.meg.conv.StringToCharBytes(localAQAgentI.getAddress());
      }
      else
        this.senderAgentAddress = null;
      this.senderAgentProtocol = ((byte)localAQAgentI.getProtocol());
    }
    else
    {
      this.senderAgentName = null;
      this.senderAgentAddress = null;
      this.senderAgentProtocol = 0;
    }

    this.messageData = paramArrayOfByte1;
    this.messageOid = paramArrayOfByte2;
    this.isRawQueue = paramBoolean;
    if ((paramString != null) && (paramString.length() != 0))
      this.queueNameBytes = this.meg.conv.StringToCharBytes(paramString);
    else {
      this.queueNameBytes = null;
    }
    this.attrRecipientList = ((AQAgentI[])this.messageProperties.getRecipientList());

    if ((this.attrRecipientList != null) && (this.attrRecipientList.length > 0))
    {
      this.recipientTextValues = new byte[this.attrRecipientList.length * 3][];
      this.recipientBinaryValues = new byte[this.attrRecipientList.length * 3][];
      this.recipientKeywords = new int[this.attrRecipientList.length * 3];
      for (int i = 0; i < this.attrRecipientList.length; i++)
      {
        if (this.attrRecipientList[i].getName() != null) {
          this.recipientTextValues[(3 * i)] = this.meg.conv.StringToCharBytes(this.attrRecipientList[i].getName());
        }

        if (this.attrRecipientList[i].getAddress() != null) {
          this.recipientTextValues[(3 * i + 1)] = this.meg.conv.StringToCharBytes(this.attrRecipientList[i].getAddress());
        }

        this.recipientBinaryValues[(3 * i + 2)] = new byte[1];
        this.recipientBinaryValues[(3 * i + 2)][0] = ((byte)this.attrRecipientList[i].getProtocol());
        this.recipientKeywords[(3 * i)] = (3 * i);
        this.recipientKeywords[(3 * i + 1)] = (3 * i + 1);
        this.recipientKeywords[(3 * i + 2)] = (3 * i + 2);
      }
    }

    String str3 = this.enqueueOptions.getTransformation();
    if ((str3 != null) && (str3.length() > 0))
    {
      this.nbExtensions = 1;
      this.extensionTextValues = new byte[this.nbExtensions][];
      this.extensionBinaryValues = new byte[this.nbExtensions][];
      this.extensionKeywords = new int[this.nbExtensions];
      this.extensionTextValues[0] = this.meg.conv.StringToCharBytes(str3);
      this.extensionBinaryValues[0] = null;
      this.extensionKeywords[0] = 196;
    }
    else {
      this.nbExtensions = 0;
    }this.outMsgid = null;
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

    this.aqm.initToDefaultValues();
    this.aqm.aqmpri = this.messageProperties.getPriority();
    this.aqm.aqmdel = this.messageProperties.getDelay();
    this.aqm.aqmexp = this.messageProperties.getExpiration();
    this.aqm.aqmcorBytes = this.aqmcorBytes;
    this.aqm.aqmeqnBytes = this.aqmeqnBytes;
    this.aqm.senderAgentName = this.senderAgentName;
    this.aqm.senderAgentAddress = this.senderAgentAddress;
    this.aqm.senderAgentProtocol = this.senderAgentProtocol;
    this.aqm.originalMsgId = this.messageProperties.getPreviousQueueMessageId();
    this.aqm.marshal();

    AQAgentI[] arrayOfAQAgentI = (AQAgentI[])this.messageProperties.getRecipientList();

    if ((arrayOfAQAgentI != null) && (arrayOfAQAgentI.length > 0))
    {
      this.meg.marshalPTR();
      this.meg.marshalSWORD(arrayOfAQAgentI.length * 3);
    }
    else
    {
      this.meg.marshalNULLPTR();
      this.meg.marshalSWORD(0);
    }

    this.meg.marshalSB4(this.enqueueOptions.getVisibility().getCode());

    int i = 0;
    if ((this.enqueueOptions.getRelativeMessageId() != null) && (this.enqueueOptions.getRelativeMessageId().length > 0))
    {
      i = 1;
      this.meg.marshalPTR();
      this.meg.marshalSWORD(this.enqueueOptions.getRelativeMessageId().length);
    }
    else
    {
      this.meg.marshalNULLPTR();
      this.meg.marshalSWORD(0);
    }

    this.meg.marshalSWORD(this.enqueueOptions.getSequenceDeviation().getCode());

    this.meg.marshalPTR();
    this.meg.marshalSWORD(16);

    this.meg.marshalUB2(1);
    if (!this.isRawQueue)
    {
      this.meg.marshalPTR();

      this.meg.marshalNULLPTR();

      this.meg.marshalUB4(0L);
    }
    else
    {
      this.meg.marshalNULLPTR();

      this.meg.marshalPTR();

      this.meg.marshalUB4(this.messageData.length);
    }
    if (this.enqueueOptions.getRetrieveMessageId())
    {
      this.retrieveMessageId = true;

      this.meg.marshalPTR();

      this.meg.marshalSWORD(16);
    }
    else
    {
      this.retrieveMessageId = false;

      this.meg.marshalNULLPTR();

      this.meg.marshalSWORD(0);
    }

    int j = 0;
    if (this.connection.autocommit)
      j = 32;
    if (this.enqueueOptions.getDeliveryMode() == AQEnqueueOptions.DeliveryMode.BUFFERED)
      j |= 2;
    this.meg.marshalUB4(j);

    this.meg.marshalNULLPTR();

    this.meg.marshalNULLPTR();

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

    this.meg.marshalNULLPTR();

    this.meg.marshalSWORD(0);

    this.meg.marshalNULLPTR();

    this.meg.marshalSWORD(0);

    this.meg.marshalNULLPTR();

    if (this.connection.getTTCVersion() >= 4)
    {
      this.meg.marshalNULLPTR();

      this.meg.marshalSWORD(0);

      this.meg.marshalNULLPTR();

      this.meg.marshalSWORD(0);

      this.meg.marshalNULLPTR();

      this.meg.marshalSWORD(0);

      this.meg.marshalNULLPTR();

      this.meg.marshalNULLPTR();
    }

    if ((this.queueNameBytes != null) && (this.queueNameBytes.length != 0)) {
      this.meg.marshalCHR(this.queueNameBytes);
    }
    if ((arrayOfAQAgentI != null) && (arrayOfAQAgentI.length > 0))
    {
      this.meg.marshalKPDKV(this.recipientTextValues, this.recipientBinaryValues, this.recipientKeywords);
    }

    if (i != 0) {
      this.meg.marshalB1Array(this.enqueueOptions.getRelativeMessageId());
    }

    this.meg.marshalB1Array(this.messageOid);

    if (!this.isRawQueue)
    {
      this.toh.init(this.messageOid, this.messageData.length);
      this.toh.marshal(this.meg);
      this.meg.marshalCLR(this.messageData, 0, this.messageData.length);
    }
    else {
      this.meg.marshalB1Array(this.messageData);
    }
    if (this.nbExtensions > 0)
      this.meg.marshalKPDKV(this.extensionTextValues, this.extensionBinaryValues, this.extensionKeywords);
  }

  byte[] getMessageId()
  {
    return this.outMsgid;
  }

  void readRPA()
    throws SQLException, IOException
  {
    if (this.retrieveMessageId)
    {
      this.outMsgid = new byte[16];
      this.meg.unmarshalBuffer(this.outMsgid, 0, 16);
    }
    int i = this.meg.unmarshalUB2();
  }

  protected OracleConnection getConnectionDuringExceptionHandling()
  {
    return null;
  }
}