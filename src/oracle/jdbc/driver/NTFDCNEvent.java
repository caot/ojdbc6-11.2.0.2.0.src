package oracle.jdbc.driver;

import java.io.IOException;
import java.nio.ByteBuffer;
import oracle.jdbc.dcn.DatabaseChangeEvent;
import oracle.jdbc.dcn.DatabaseChangeEvent.AdditionalEventType;
import oracle.jdbc.dcn.DatabaseChangeEvent.EventType;
import oracle.jdbc.dcn.QueryChangeDescription;
import oracle.jdbc.dcn.TableChangeDescription;
import oracle.sql.CharacterSet;

class NTFDCNEvent extends DatabaseChangeEvent
{
  private int notifVersion = 0;
  private int notifRegid = 0;
  private DatabaseChangeEvent.EventType eventType;
  private DatabaseChangeEvent.AdditionalEventType additionalEventType = DatabaseChangeEvent.AdditionalEventType.NONE;
  private String databaseName = null;
  private byte[] notifXid = new byte[8];
  private int notifScn1 = 0;
  private int notifScn2 = 0;

  private int numberOfTables = 0;
  private NTFDCNTableChanges[] tcdesc = null;

  private int numberOfQueries = 0;
  private NTFDCNQueryChanges[] qdesc = null;
  private long registrationId;
  private NTFConnection conn;
  private int csid;
  private boolean isReady = false;
  private ByteBuffer dataBuffer;
  private boolean isDeregistrationEvent = false;
  private short databaseVersion;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  NTFDCNEvent(NTFConnection paramNTFConnection, short paramShort)
    throws IOException
  {
    super(paramNTFConnection);

    this.conn = paramNTFConnection;
    this.csid = this.conn.charset.getOracleId();

    int i = this.conn.readInt();
    byte[] arrayOfByte = new byte[i];
    this.conn.readBuffer(arrayOfByte, 0, i);
    this.dataBuffer = ByteBuffer.wrap(arrayOfByte);
    this.databaseVersion = paramShort;
  }

  private void initEvent()
  {
    int i = this.dataBuffer.get();
    int j = this.dataBuffer.getInt();
    byte[] arrayOfByte1 = new byte[j];
    this.dataBuffer.get(arrayOfByte1, 0, j);

    String str = null;
    try {
      str = new String(arrayOfByte1, "UTF-8");
    } catch (Exception localException1) {
    }
    str = str.replaceFirst("CHNF", "");
    this.registrationId = Long.parseLong(str);

    int k = this.dataBuffer.get();
    int m = this.dataBuffer.getInt();
    byte[] arrayOfByte2 = new byte[m];
    this.dataBuffer.get(arrayOfByte2, 0, m);

    int n = this.dataBuffer.get();
    int i1 = this.dataBuffer.getInt();
    if (this.dataBuffer.hasRemaining())
    {
      this.notifVersion = this.dataBuffer.getShort();
      this.notifRegid = this.dataBuffer.getInt();
      this.eventType = DatabaseChangeEvent.EventType.getEventType(this.dataBuffer.getInt());
      int i2 = this.dataBuffer.getShort();
      byte[] arrayOfByte3 = new byte[i2];
      this.dataBuffer.get(arrayOfByte3, 0, i2);
      try {
        this.databaseName = new String(arrayOfByte3, "UTF-8");
      }
      catch (Exception localException2)
      {
      }

      this.dataBuffer.get(this.notifXid);

      this.notifScn1 = this.dataBuffer.getInt();
      this.notifScn2 = this.dataBuffer.getShort();
      int i3;
      if (this.eventType == DatabaseChangeEvent.EventType.OBJCHANGE)
      {
        this.numberOfTables = this.dataBuffer.getShort();
        this.tcdesc = new NTFDCNTableChanges[this.numberOfTables];
        for (i3 = 0; i3 < this.tcdesc.length; i3++)
          this.tcdesc[i3] = new NTFDCNTableChanges(this.dataBuffer, this.csid);
      }
      else if (this.eventType == DatabaseChangeEvent.EventType.QUERYCHANGE)
      {
        this.numberOfQueries = this.dataBuffer.getShort();
        this.qdesc = new NTFDCNQueryChanges[this.numberOfQueries];

        for (i3 = 0; i3 < this.numberOfQueries; i3++)
        {
          this.qdesc[i3] = new NTFDCNQueryChanges(this.dataBuffer, this.csid);
        }
      }
    }
    this.isReady = true;
  }

  public String getDatabaseName()
  {
    if (!this.isReady)
      initEvent();
    return this.databaseName;
  }

  public TableChangeDescription[] getTableChangeDescription()
  {
    if (!this.isReady)
      initEvent();
    if (this.eventType == DatabaseChangeEvent.EventType.OBJCHANGE)
    {
      return this.tcdesc;
    }

    return null;
  }

  public QueryChangeDescription[] getQueryChangeDescription()
  {
    if (!this.isReady)
      initEvent();
    if (this.eventType == DatabaseChangeEvent.EventType.QUERYCHANGE)
    {
      return this.qdesc;
    }

    return null;
  }

  public byte[] getTransactionId()
  {
    if (!this.isReady)
      initEvent();
    return this.notifXid;
  }

  public String getTransactionId(boolean paramBoolean)
  {
    if (!this.isReady)
      initEvent();
    int i;
    int j;
    long l;
    if (!paramBoolean)
    {
      i = (this.notifXid[0] & 0xFF) << 8 | this.notifXid[1] & 0xFF;

      j = (this.notifXid[2] & 0xFF) << 8 | this.notifXid[3] & 0xFF;

      l = ((this.notifXid[4] & 0xFF) << 24 | (this.notifXid[5] & 0xFF) << 16 | (this.notifXid[6] & 0xFF) << 8 | this.notifXid[7] & 0xFF) & 0xFFFFFFFF;
    }
    else
    {
      i = (this.notifXid[1] & 0xFF) << 8 | this.notifXid[0] & 0xFF;

      j = (this.notifXid[3] & 0xFF) << 8 | this.notifXid[2] & 0xFF;

      l = ((this.notifXid[7] & 0xFF) << 24 | (this.notifXid[6] & 0xFF) << 16 | (this.notifXid[5] & 0xFF) << 8 | this.notifXid[4] & 0xFF) & 0xFFFFFFFF;
    }

    String str = "" + i + "." + j + "." + l;
    return str;
  }

  void setEventType(DatabaseChangeEvent.EventType paramEventType)
    throws IOException
  {
    if (!this.isReady)
      initEvent();
    this.eventType = paramEventType;
    if (this.eventType == DatabaseChangeEvent.EventType.DEREG)
      this.isDeregistrationEvent = true;
  }

  void setAdditionalEventType(DatabaseChangeEvent.AdditionalEventType paramAdditionalEventType)
  {
    this.additionalEventType = paramAdditionalEventType;
  }

  public DatabaseChangeEvent.EventType getEventType()
  {
    if (!this.isReady)
      initEvent();
    return this.eventType;
  }

  public DatabaseChangeEvent.AdditionalEventType getAdditionalEventType()
  {
    return this.additionalEventType;
  }

  boolean isDeregistrationEvent()
  {
    return this.isDeregistrationEvent;
  }

  public String getConnectionInformation()
  {
    return this.conn.connectionDescription;
  }

  public int getRegistrationId()
  {
    if (!this.isReady)
      initEvent();
    return (int)this.registrationId;
  }

  public long getRegId()
  {
    if (!this.isReady)
      initEvent();
    return this.registrationId;
  }

  public String toString()
  {
    if (!this.isReady)
      initEvent();
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("Connection information  : " + this.conn.connectionDescription + "\n");
    localStringBuffer.append("Registration ID         : " + this.registrationId + "\n");
    localStringBuffer.append("Notification version    : " + this.notifVersion + "\n");
    localStringBuffer.append("Event type              : " + this.eventType + "\n");
    if (this.additionalEventType != DatabaseChangeEvent.AdditionalEventType.NONE)
      localStringBuffer.append("Additional event type   : " + this.additionalEventType + "\n");
    if (this.databaseName != null) {
      localStringBuffer.append("Database name           : " + this.databaseName + "\n");
    }

    TableChangeDescription[] arrayOfTableChangeDescription = getTableChangeDescription();
    if (arrayOfTableChangeDescription != null)
    {
      localStringBuffer.append("Table Change Description (length=" + this.numberOfTables + ")\n");
      for (int i = 0; i < arrayOfTableChangeDescription.length; i++)
        localStringBuffer.append(arrayOfTableChangeDescription[i].toString());
    }
    QueryChangeDescription[] arrayOfQueryChangeDescription = getQueryChangeDescription();
    if (arrayOfQueryChangeDescription != null)
    {
      localStringBuffer.append("Query Change Description (length=" + this.numberOfQueries + ")\n");
      for (int j = 0; j < arrayOfQueryChangeDescription.length; j++) {
        localStringBuffer.append(arrayOfQueryChangeDescription[j].toString());
      }
    }
    return localStringBuffer.toString();
  }
}