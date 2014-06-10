package oracle.jdbc.dcn;

import oracle.sql.ROWID;

public abstract interface RowChangeDescription
{
  public abstract RowOperation getRowOperation();

  public abstract ROWID getRowid();

  public static enum RowOperation
  {
    INSERT(TableChangeDescription.TableOperation.INSERT.getCode()), 

    UPDATE(TableChangeDescription.TableOperation.UPDATE.getCode()), 

    DELETE(TableChangeDescription.TableOperation.DELETE.getCode());

    private final int code;

    private RowOperation(int paramInt) { this.code = paramInt; }


    public final int getCode()
    {
      return this.code;
    }

    public static final RowOperation getRowOperation(int paramInt)
    {
      if (paramInt == INSERT.getCode())
        return INSERT;
      if (paramInt == UPDATE.getCode()) {
        return UPDATE;
      }
      return DELETE;
    }
  }
}