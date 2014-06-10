package oracle.jdbc.driver;

import java.nio.ByteBuffer;
import java.util.EnumSet;
import oracle.jdbc.dcn.RowChangeDescription;
import oracle.jdbc.dcn.RowChangeDescription.RowOperation;
import oracle.jdbc.dcn.TableChangeDescription;
import oracle.jdbc.dcn.TableChangeDescription.TableOperation;
import oracle.sql.CharacterSet;

class NTFDCNTableChanges
  implements TableChangeDescription
{
  final EnumSet<TableChangeDescription.TableOperation> opcode;
  String tableName;
  final int objectNumber;
  final int numberOfRows;
  final RowChangeDescription.RowOperation[] rowOpcode;
  final int[] rowIdLength;
  final byte[][] rowid;
  final CharacterSet charset;
  NTFDCNRowChanges[] rowsDescription = null;
  private static final byte OPERATION_ANY = 0;
  private static final byte OPERATION_UNKNOWN = 64;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  NTFDCNTableChanges(ByteBuffer paramByteBuffer, int paramInt)
  {
    this.charset = CharacterSet.make(paramInt);
    this.opcode = TableChangeDescription.TableOperation.getTableOperations(paramByteBuffer.getInt());
    int i = paramByteBuffer.getShort();
    byte[] arrayOfByte = new byte[i];
    paramByteBuffer.get(arrayOfByte, 0, i);
    this.tableName = this.charset.toStringWithReplacement(arrayOfByte, 0, i);

    this.objectNumber = paramByteBuffer.getInt();
    if (!this.opcode.contains(TableChangeDescription.TableOperation.ALL_ROWS))
    {
      this.numberOfRows = paramByteBuffer.getShort();
      this.rowOpcode = new RowChangeDescription.RowOperation[this.numberOfRows];
      this.rowIdLength = new int[this.numberOfRows];
      this.rowid = new byte[this.numberOfRows][];
      for (int j = 0; j < this.numberOfRows; j++)
      {
        this.rowOpcode[j] = RowChangeDescription.RowOperation.getRowOperation(paramByteBuffer.getInt());
        this.rowIdLength[j] = paramByteBuffer.getShort();
        this.rowid[j] = new byte[this.rowIdLength[j]];
        paramByteBuffer.get(this.rowid[j], 0, this.rowIdLength[j]);
      }
    }
    else
    {
      this.numberOfRows = 0;
      this.rowid = ((byte[][])null);
      this.rowOpcode = null;
      this.rowIdLength = null;
    }
  }

  public String getTableName()
  {
    return this.tableName;
  }

  public int getObjectNumber()
  {
    return this.objectNumber;
  }

  public RowChangeDescription[] getRowChangeDescription()
  {
    if (this.rowsDescription == null)
    {
      synchronized (this)
      {
        if (this.rowsDescription == null)
        {
          this.rowsDescription = new NTFDCNRowChanges[this.numberOfRows];
          for (int i = 0; i < this.rowsDescription.length; i++)
            this.rowsDescription[i] = new NTFDCNRowChanges(this.rowOpcode[i], this.rowIdLength[i], this.rowid[i]);
        }
      }
    }
    return this.rowsDescription;
  }

  public EnumSet<TableChangeDescription.TableOperation> getTableOperations()
  {
    return this.opcode;
  }

  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("    operation=" + getTableOperations() + ", tableName=" + this.tableName + ", objectNumber=" + this.objectNumber + "\n");
    RowChangeDescription[] arrayOfRowChangeDescription = getRowChangeDescription();
    if ((arrayOfRowChangeDescription != null) && (arrayOfRowChangeDescription.length > 0))
    {
      localStringBuffer.append("    Row Change Description (length=" + arrayOfRowChangeDescription.length + "):\n");
      for (int i = 0; i < arrayOfRowChangeDescription.length; i++)
        localStringBuffer.append(arrayOfRowChangeDescription[i].toString());
    }
    return localStringBuffer.toString();
  }
}