package oracle.jdbc.aq;

import java.sql.SQLException;

public class AQDequeueOptions
{
  public static final int DEQUEUE_WAIT_FOREVER = -1;
  public static final int DEQUEUE_NO_WAIT = 0;
  private String attrConsumerName;
  private String attrCorrelation;
  private DequeueMode attrDeqMode;
  private byte[] attrDeqMsgId;
  private NavigationOption attrNavigation;
  private VisibilityOption attrVisibility;
  private int attrWait;
  private int maxBufferLength;
  private DeliveryFilter attrDeliveryMode;
  private boolean retrieveMsgId;
  private String transformation;
  private String condition;
  public static final int MAX_RAW_PAYLOAD = 67108787;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  public AQDequeueOptions()
  {
    this.attrConsumerName = null;
    this.attrCorrelation = null;
    this.attrDeqMode = DequeueMode.REMOVE;
    this.attrDeqMsgId = null;
    this.attrNavigation = NavigationOption.NEXT_MESSAGE;
    this.attrVisibility = VisibilityOption.ON_COMMIT;
    this.attrWait = -1;
    this.maxBufferLength = 67108787;
    this.attrDeliveryMode = DeliveryFilter.PERSISTENT;

    this.retrieveMsgId = false;
  }

  public void setConsumerName(String paramString)
    throws SQLException
  {
    this.attrConsumerName = paramString;
  }

  public String getConsumerName()
  {
    return this.attrConsumerName;
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

  public void setDequeueMode(DequeueMode paramDequeueMode)
    throws SQLException
  {
    this.attrDeqMode = paramDequeueMode;
  }

  public DequeueMode getDequeueMode()
  {
    return this.attrDeqMode;
  }

  public void setDequeueMessageId(byte[] paramArrayOfByte)
    throws SQLException
  {
    this.attrDeqMsgId = paramArrayOfByte;
  }

  public byte[] getDequeueMessageId()
  {
    return this.attrDeqMsgId;
  }

  public void setNavigation(NavigationOption paramNavigationOption)
    throws SQLException
  {
    this.attrNavigation = paramNavigationOption;
  }

  public NavigationOption getNavigation()
  {
    return this.attrNavigation;
  }

  public void setVisibility(VisibilityOption paramVisibilityOption)
    throws SQLException
  {
    this.attrVisibility = paramVisibilityOption;
  }

  public VisibilityOption getVisibility()
  {
    return this.attrVisibility;
  }

  public void setWait(int paramInt)
    throws SQLException
  {
    this.attrWait = paramInt;
  }

  public int getWait()
  {
    return this.attrWait;
  }

  public void setMaximumBufferLength(int paramInt)
    throws SQLException
  {
    if (paramInt > 0)
      this.maxBufferLength = paramInt;
  }

  public int getMaximumBufferLength()
  {
    return this.maxBufferLength;
  }

  public void setDeliveryFilter(DeliveryFilter paramDeliveryFilter)
    throws SQLException
  {
    this.attrDeliveryMode = paramDeliveryFilter;
  }

  public DeliveryFilter getDeliveryFilter()
  {
    return this.attrDeliveryMode;
  }

  public void setRetrieveMessageId(boolean paramBoolean)
  {
    this.retrieveMsgId = paramBoolean;
  }

  public boolean getRetrieveMessageId()
  {
    return this.retrieveMsgId;
  }

  public void setTransformation(String paramString)
  {
    this.transformation = paramString;
  }

  public String getTransformation()
  {
    return this.transformation;
  }

  public void setCondition(String paramString)
  {
    this.condition = paramString;
  }

  public String getCondition()
  {
    return this.condition;
  }

  public static enum DeliveryFilter
  {
    PERSISTENT(1), BUFFERED(2), PERSISTENT_OR_BUFFERED(3);

    private final int mode;

    private DeliveryFilter(int paramInt) { this.mode = paramInt; }


    public final int getCode()
    {
      return this.mode;
    }
  }

  public static enum VisibilityOption
  {
    ON_COMMIT(2), 

    IMMEDIATE(1);

    private final int mode;

    private VisibilityOption(int paramInt) { this.mode = paramInt; }


    public final int getCode()
    {
      return this.mode;
    }
  }

  public static enum NavigationOption
  {
    FIRST_MESSAGE(1), 

    NEXT_MESSAGE(3), 

    NEXT_TRANSACTION(2);

    private final int mode;

    private NavigationOption(int paramInt) { this.mode = paramInt; }


    public final int getCode()
    {
      return this.mode;
    }
  }

  public static enum DequeueMode
  {
    BROWSE(1), 

    LOCKED(2), 

    REMOVE(3), 

    REMOVE_NODATA(4);

    private final int mode;

    private DequeueMode(int paramInt) {
      this.mode = paramInt;
    }

    public final int getCode()
    {
      return this.mode;
    }
  }
}