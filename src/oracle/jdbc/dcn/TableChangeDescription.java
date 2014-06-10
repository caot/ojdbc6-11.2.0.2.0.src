package oracle.jdbc.dcn;

import java.util.EnumSet;

public abstract interface TableChangeDescription
{
  public abstract EnumSet<TableOperation> getTableOperations();

  public abstract String getTableName();

  public abstract int getObjectNumber();

  public abstract RowChangeDescription[] getRowChangeDescription();

  public static enum TableOperation
  {
    ALL_ROWS(1), 

    INSERT(2), 

    UPDATE(4), 

    DELETE(8), 

    ALTER(16), 

    DROP(32);

    private final int code;

    private TableOperation(int paramInt) { this.code = paramInt; }


    public final int getCode()
    {
      return this.code;
    }

    public static final EnumSet<TableOperation> getTableOperations(int paramInt)
    {
      EnumSet localEnumSet = EnumSet.noneOf(TableOperation.class);
      if ((paramInt & ALL_ROWS.getCode()) != 0)
        localEnumSet.add(ALL_ROWS);
      if ((paramInt & INSERT.getCode()) != 0)
        localEnumSet.add(INSERT);
      if ((paramInt & UPDATE.getCode()) != 0)
        localEnumSet.add(UPDATE);
      if ((paramInt & DELETE.getCode()) != 0)
        localEnumSet.add(DELETE);
      if ((paramInt & ALTER.getCode()) != 0)
        localEnumSet.add(ALTER);
      if ((paramInt & DROP.getCode()) != 0)
        localEnumSet.add(DROP);
      return localEnumSet;
    }
  }
}