package oracle.jdbc.oracore;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Map;
import oracle.jdbc.internal.OracleConnection;
import oracle.sql.Datum;

public class OracleTypeSINT32 extends OracleType
  implements Serializable
{
  static final long serialVersionUID = -5465988397261455848L;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  public Datum toDatum(Object paramObject, OracleConnection paramOracleConnection)
    throws SQLException
  {
    return OracleTypeNUMBER.toNUMBER(paramObject, paramOracleConnection);
  }

  public Datum[] toDatumArray(Object paramObject, OracleConnection paramOracleConnection, long paramLong, int paramInt)
    throws SQLException
  {
    return OracleTypeNUMBER.toNUMBERArray(paramObject, paramOracleConnection, paramLong, paramInt);
  }

  public int getTypeCode()
  {
    return 2;
  }

  protected Object toObject(byte[] paramArrayOfByte, int paramInt, Map paramMap)
    throws SQLException
  {
    return OracleTypeNUMBER.toNumericObject(paramArrayOfByte, paramInt, paramMap);
  }

  private void writeObject(ObjectOutputStream paramObjectOutputStream)
    throws IOException
  {
  }

  private void readObject(ObjectInputStream paramObjectInputStream)
    throws IOException, ClassNotFoundException
  {
  }
}