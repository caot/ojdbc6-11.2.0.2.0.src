package oracle.jdbc.dcn;

public abstract interface QueryChangeDescription
{
  public abstract long getQueryId();

  public abstract QueryChangeEventType getQueryChangeEventType();

  public abstract TableChangeDescription[] getTableChangeDescription();

  public static enum QueryChangeEventType
  {
    DEREG(DatabaseChangeEvent.EventType.DEREG.getCode()), 

    QUERYCHANGE(DatabaseChangeEvent.EventType.QUERYCHANGE.getCode());

    private final int code;

    private QueryChangeEventType(int paramInt) { this.code = paramInt; }


    public final int getCode()
    {
      return this.code;
    }

    public static final QueryChangeEventType getQueryChangeEventType(int paramInt)
    {
      if (paramInt == DEREG.getCode()) {
        return DEREG;
      }
      return QUERYCHANGE;
    }
  }
}