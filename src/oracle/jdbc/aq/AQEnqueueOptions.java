package oracle.jdbc.aq;

import java.sql.SQLException;

public class AQEnqueueOptions
{
  private byte[] attrRelativeMessageId;
  private SequenceDeviationOption attrSequenceDeviation;
  private VisibilityOption attrVisibility;
  private DeliveryMode attrDeliveryMode;
  private boolean retrieveMsgId;
  private String transformation;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  public AQEnqueueOptions()
  {
    this.attrRelativeMessageId = null;
    this.attrSequenceDeviation = SequenceDeviationOption.BOTTOM;
    this.attrVisibility = VisibilityOption.ON_COMMIT;
    this.attrDeliveryMode = DeliveryMode.PERSISTENT;

    this.retrieveMsgId = false;
  }

  /** @deprecated */
  public void setRelativeMessageId(byte[] paramArrayOfByte)
    throws SQLException
  {
    this.attrRelativeMessageId = paramArrayOfByte;
  }

  public byte[] getRelativeMessageId()
  {
    return this.attrRelativeMessageId;
  }

  /** @deprecated */
  public void setSequenceDeviation(SequenceDeviationOption paramSequenceDeviationOption)
    throws SQLException
  {
    this.attrSequenceDeviation = paramSequenceDeviationOption;
  }

  public SequenceDeviationOption getSequenceDeviation()
  {
    return this.attrSequenceDeviation;
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

  public void setDeliveryMode(DeliveryMode paramDeliveryMode)
    throws SQLException
  {
    this.attrDeliveryMode = paramDeliveryMode;
  }

  public DeliveryMode getDeliveryMode()
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

  public static enum DeliveryMode
  {
    PERSISTENT(AQDequeueOptions.DeliveryFilter.PERSISTENT.getCode()), 

    BUFFERED(AQDequeueOptions.DeliveryFilter.BUFFERED.getCode());

    private final int mode;

    private DeliveryMode(int paramInt) { this.mode = paramInt; }


    public final int getCode()
    {
      return this.mode;
    }
  }

  public static enum SequenceDeviationOption
  {
    BOTTOM(0), 

    BEFORE(2), 

    TOP(3);

    private final int mode;

    private SequenceDeviationOption(int paramInt) { this.mode = paramInt; }


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
}