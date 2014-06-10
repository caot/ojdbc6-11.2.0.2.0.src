package oracle.jdbc.driver;

import oracle.jdbc.dcn.RowChangeDescription;
import oracle.jdbc.dcn.RowChangeDescription.RowOperation;
import oracle.sql.ROWID;

class NTFDCNRowChanges
  implements RowChangeDescription
{
  RowChangeDescription.RowOperation opcode;
  int rowidLength;
  byte[] rowid;
  ROWID rowidObj;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  NTFDCNRowChanges(RowChangeDescription.RowOperation paramRowOperation, int paramInt, byte[] paramArrayOfByte)
  {
    this.opcode = paramRowOperation;
    this.rowidLength = paramInt;
    this.rowid = paramArrayOfByte;
    this.rowidObj = null;
  }

  public ROWID getRowid()
  {
    if (this.rowidObj == null)
      this.rowidObj = new ROWID(this.rowid);
    return this.rowidObj;
  }

  public RowChangeDescription.RowOperation getRowOperation()
  {
    return this.opcode;
  }

  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("      ROW:  operation=" + getRowOperation() + ", ROWID=" + new String(this.rowid) + "\n");
    return localStringBuffer.toString();
  }
}