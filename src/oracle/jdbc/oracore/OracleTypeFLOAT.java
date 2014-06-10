package oracle.jdbc.oracore;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Map;
import oracle.jdbc.internal.OracleConnection;
import oracle.sql.Datum;

public class OracleTypeFLOAT extends OracleType
  implements Serializable
{
  static final long serialVersionUID = 4088841548269771109L;
  int precision;
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
    return 6;
  }

  public void parseTDSrec(TDSReader paramTDSReader)
    throws SQLException
  {
    this.precision = paramTDSReader.readUnsignedByte();
  }

  public int getScale()
  {
    return -127;
  }

  public int getPrecision()
  {
    return this.precision;
  }

  protected static Object unpickle81NativeArray(PickleContext paramPickleContext, long paramLong, int paramInt1, int paramInt2)
    throws SQLException
  {
    return OracleTypeNUMBER.unpickle81NativeArray(paramPickleContext, paramLong, paramInt1, paramInt2);
  }

  protected Object toObject(byte[] paramArrayOfByte, int paramInt, Map paramMap)
    throws SQLException
  {
    return OracleTypeNUMBER.toNumericObject(paramArrayOfByte, paramInt, paramMap);
  }

  private void writeObject(ObjectOutputStream paramObjectOutputStream)
    throws IOException
  {
    paramObjectOutputStream.writeInt(this.precision);
  }

  private void readObject(ObjectInputStream paramObjectInputStream)
    throws IOException, ClassNotFoundException
  {
    this.precision = paramObjectInputStream.readInt();
  }
}