package oracle.jdbc.oracore;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Map;
import oracle.jdbc.driver.DatabaseError;
import oracle.jdbc.internal.OracleConnection;
import oracle.sql.BINARY_FLOAT;
import oracle.sql.Datum;

public class OracleTypeBINARY_FLOAT extends OracleType
  implements Serializable
{
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  public Datum toDatum(Object paramObject, OracleConnection paramOracleConnection)
    throws SQLException
  {
    BINARY_FLOAT localBINARY_FLOAT = null;

    if (paramObject != null)
    {
      if ((paramObject instanceof BINARY_FLOAT)) {
        localBINARY_FLOAT = (BINARY_FLOAT)paramObject;
      } else if ((paramObject instanceof Float)) {
        localBINARY_FLOAT = new BINARY_FLOAT((Float)paramObject);
      } else if ((paramObject instanceof byte[])) {
        localBINARY_FLOAT = new BINARY_FLOAT((byte[])paramObject);
      }
      else {
        SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 59, paramObject);
        localSQLException.fillInStackTrace();
        throw localSQLException;
      }
    }

    return localBINARY_FLOAT;
  }

  public Datum[] toDatumArray(Object paramObject, OracleConnection paramOracleConnection, long paramLong, int paramInt)
    throws SQLException
  {
    Datum[] arrayOfDatum = null;

    if (paramObject != null)
    {
      if ((paramObject instanceof Object[]))
      {
        Object[] arrayOfObject = (Object[])paramObject;

        int i = (int)(paramInt == -1 ? arrayOfObject.length : Math.min(arrayOfObject.length - paramLong + 1L, paramInt));

        arrayOfDatum = new Datum[i];

        for (int j = 0; j < i; j++)
        {
          Object localObject = arrayOfObject[((int)paramLong + j - 1)];
          SQLException localSQLException;
          if (localObject != null)
          {
            if ((localObject instanceof Float)) {
              arrayOfDatum[j] = new BINARY_FLOAT(((Float)localObject).floatValue());
            }
            else if ((localObject instanceof BINARY_FLOAT)) {
              arrayOfDatum[j] = ((BINARY_FLOAT)localObject);
            }
            else {
              localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68);
              localSQLException.fillInStackTrace();
              throw localSQLException;
            }
          }
          else
          {
            localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68);
            localSQLException.fillInStackTrace();
            throw localSQLException;
          }
        }
      }
    }

    return arrayOfDatum;
  }

  public int getTypeCode()
  {
    return 100;
  }

  protected Object toObject(byte[] paramArrayOfByte, int paramInt, Map paramMap)
    throws SQLException
  {
    if ((paramArrayOfByte == null) || (paramArrayOfByte.length == 0)) {
      return null;
    }
    if (paramInt == 1)
      return new BINARY_FLOAT(paramArrayOfByte);
    if (paramInt == 2)
      return new BINARY_FLOAT(paramArrayOfByte).toJdbc();
    if (paramInt == 3) {
      return paramArrayOfByte;
    }

    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 59, paramArrayOfByte);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }
}