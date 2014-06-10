package oracle.jdbc.driver;

import java.nio.ByteBuffer;
import oracle.jdbc.dcn.QueryChangeDescription;
import oracle.jdbc.dcn.QueryChangeDescription.QueryChangeEventType;
import oracle.jdbc.dcn.TableChangeDescription;

class NTFDCNQueryChanges
  implements QueryChangeDescription
{
  private final long queryId;
  private final QueryChangeDescription.QueryChangeEventType queryopflags;
  private final int numberOfTables;
  private final NTFDCNTableChanges[] tcdesc;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  NTFDCNQueryChanges(ByteBuffer paramByteBuffer, int paramInt)
  {
    long l1 = paramByteBuffer.getInt() & 0xFFFFFFFF;
    long l2 = paramByteBuffer.getInt() & 0xFFFFFFFF;
    this.queryId = (l1 | l2 << 32);
    this.queryopflags = QueryChangeDescription.QueryChangeEventType.getQueryChangeEventType(paramByteBuffer.getInt());
    this.numberOfTables = paramByteBuffer.getShort();
    this.tcdesc = new NTFDCNTableChanges[this.numberOfTables];
    for (int i = 0; i < this.tcdesc.length; i++)
      this.tcdesc[i] = new NTFDCNTableChanges(paramByteBuffer, paramInt);
  }

  public long getQueryId()
  {
    return this.queryId;
  }

  public QueryChangeDescription.QueryChangeEventType getQueryChangeEventType()
  {
    return this.queryopflags;
  }

  public TableChangeDescription[] getTableChangeDescription()
  {
    return this.tcdesc;
  }

  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("  query ID=" + this.queryId + ", query change event type=" + this.queryopflags + "\n");
    TableChangeDescription[] arrayOfTableChangeDescription = getTableChangeDescription();
    if (arrayOfTableChangeDescription != null)
    {
      localStringBuffer.append("  Table Change Description (length=" + arrayOfTableChangeDescription.length + "):");
      for (int i = 0; i < arrayOfTableChangeDescription.length; i++)
        localStringBuffer.append(arrayOfTableChangeDescription[i].toString());
    }
    return localStringBuffer.toString();
  }
}