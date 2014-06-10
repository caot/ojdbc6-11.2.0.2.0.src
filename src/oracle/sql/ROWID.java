package oracle.sql;

import java.sql.RowId;
import java.sql.SQLException;

public class ROWID extends Datum
  implements RowId
{
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  public ROWID()
  {
  }

  public ROWID(byte[] paramArrayOfByte)
  {
    super(paramArrayOfByte);
  }

  public Object toJdbc()
    throws SQLException
  {
    return this;
  }

  public boolean isConvertibleTo(Class paramClass)
  {
    String str = paramClass.getName();

    return str.compareTo("java.lang.String") == 0;
  }

  public String stringValue()
  {
    byte[] arrayOfByte = null;

    arrayOfByte = getBytes();

    return new String(arrayOfByte, 0, 0, arrayOfByte.length);
  }

  public Object makeJdbcArray(int paramInt)
  {
    return new byte[paramInt][];
  }
}