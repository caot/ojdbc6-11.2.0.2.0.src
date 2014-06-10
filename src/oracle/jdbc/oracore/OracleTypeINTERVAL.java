package oracle.jdbc.oracore;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Map;
import oracle.jdbc.driver.DatabaseError;
import oracle.jdbc.internal.OracleConnection;
import oracle.sql.Datum;
import oracle.sql.INTERVALDS;
import oracle.sql.INTERVALYM;

public class OracleTypeINTERVAL extends OracleType
  implements Serializable
{
  static final long serialVersionUID = 1394800182554224957L;
  static final int LDIINTYEARMONTH = 7;
  static final int LDIINTDAYSECOND = 10;
  static final int SIZE_INTERVAL_YM = 5;
  static final int SIZE_INTERVAL_DS = 11;
  byte typeId = 0;
  int scale = 0;
  int precision = 0;

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  protected OracleTypeINTERVAL()
  {
  }

  public OracleTypeINTERVAL(OracleConnection paramOracleConnection)
  {
  }

  public int getTypeCode()
  {
    if (this.typeId == 7)
      return -103;
    if (this.typeId == 10) {
      return -104;
    }
    return 1111;
  }

  public void parseTDSrec(TDSReader paramTDSReader)
    throws SQLException
  {
    this.typeId = paramTDSReader.readByte();
    this.precision = paramTDSReader.readByte();
    this.scale = paramTDSReader.readByte();
  }

  public int getScale()
    throws SQLException
  {
    return this.scale;
  }

  public int getPrecision()
    throws SQLException
  {
    return this.precision;
  }

  private void readObject(ObjectInputStream paramObjectInputStream)
    throws IOException, ClassNotFoundException
  {
    this.typeId = paramObjectInputStream.readByte();
    this.precision = paramObjectInputStream.readByte();
    this.scale = paramObjectInputStream.readByte();
  }

  private void writeObject(ObjectOutputStream paramObjectOutputStream)
    throws IOException
  {
    paramObjectOutputStream.writeByte(this.typeId);
    paramObjectOutputStream.writeByte(this.precision);
    paramObjectOutputStream.writeByte(this.scale);
  }

  protected Object toObject(byte[] paramArrayOfByte, int paramInt, Map paramMap)
    throws SQLException
  {
    if ((paramArrayOfByte == null) || (paramArrayOfByte.length == 0)) {
      return null;
    }
    switch (paramInt)
    {
    case 1:
      if (paramArrayOfByte.length == 5)
        return new INTERVALYM(paramArrayOfByte);
      if (paramArrayOfByte.length == 11) {
        return new INTERVALDS(paramArrayOfByte);
      }

      break;
    case 2:
      if (paramArrayOfByte.length == 5)
        return INTERVALYM.toString(paramArrayOfByte);
      if (paramArrayOfByte.length == 11) {
        return INTERVALDS.toString(paramArrayOfByte);
      }

      break;
    case 3:
      return paramArrayOfByte;
    default:
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 59);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    return null;
  }

  public Datum toDatum(Object paramObject, OracleConnection paramOracleConnection)
    throws SQLException
  {
    Object localObject = null;

    if (paramObject != null)
    {
      if (((paramObject instanceof INTERVALYM)) || ((paramObject instanceof INTERVALDS)))
      {
        localObject = (Datum)paramObject;
      } else if ((paramObject instanceof String))
      {
        try
        {
          localObject = new INTERVALDS((String)paramObject);
        }
        catch (StringIndexOutOfBoundsException localStringIndexOutOfBoundsException)
        {
          localObject = new INTERVALYM((String)paramObject);
        }
      }
      else
      {
        SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 59, paramObject);
        localSQLException.fillInStackTrace();
        throw localSQLException;
      }
    }

    return localObject;
  }

  protected Object unpickle81rec(UnpickleContext paramUnpickleContext, int paramInt1, int paramInt2, Map paramMap)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 90);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }
}