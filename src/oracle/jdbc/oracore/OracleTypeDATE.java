package oracle.jdbc.oracore;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Map;
import oracle.jdbc.driver.DatabaseError;
import oracle.jdbc.internal.OracleConnection;
import oracle.sql.DATE;
import oracle.sql.Datum;
import oracle.sql.TIMESTAMP;

public class OracleTypeDATE extends OracleType
  implements Serializable
{
  static final long serialVersionUID = -5858803341118747965L;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  public OracleTypeDATE()
  {
  }

  public OracleTypeDATE(int paramInt)
  {
    super(paramInt);
  }

  public Datum toDatum(Object paramObject, OracleConnection paramOracleConnection)
    throws SQLException
  {
    DATE localDATE = null;

    if (paramObject != null)
    {
      try
      {
        if ((paramObject instanceof DATE))
          localDATE = (DATE)paramObject;
        else if ((paramObject instanceof TIMESTAMP))
          localDATE = new DATE(((TIMESTAMP)paramObject).timestampValue());
        else {
          localDATE = new DATE(paramObject);
        }
      }
      catch (SQLException localSQLException1)
      {
        SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 59, paramObject);
        localSQLException2.fillInStackTrace();
        throw localSQLException2;
      }
    }

    return localDATE;
  }

  public Datum[] toDatumArray(Object paramObject, OracleConnection paramOracleConnection, long paramLong, int paramInt)
    throws SQLException
  {
    Datum[] arrayOfDatum = null;

    if (paramObject != null)
    {
      char[][] localObject;
      if ((paramObject instanceof char[][]))
      {
        localObject = (char[][])paramObject;
        int i = (int)(paramInt == -1 ? localObject.length : Math.min(localObject.length - paramLong + 1L, paramInt));

        arrayOfDatum = new Datum[i];

        for (int j = 0; j < i; j++)
          arrayOfDatum[j] = toDatum(new String(localObject[((int)paramLong + j - 1)]), paramOracleConnection);
      }
      else {
        if ((paramObject instanceof Object[]))
        {
          return super.toDatumArray(paramObject, paramOracleConnection, paramLong, paramInt);
        }

        SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 59, paramObject);
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }
    }
    return arrayOfDatum;
  }

  public int getTypeCode()
  {
    return 91;
  }

  protected Object toObject(byte[] paramArrayOfByte, int paramInt, Map paramMap)
    throws SQLException
  {
    if ((paramArrayOfByte == null) || (paramArrayOfByte.length == 0)) {
      return null;
    }
    if (paramInt == 1)
      return new DATE(paramArrayOfByte);
    if (paramInt == 2)
      return DATE.toTimestamp(paramArrayOfByte);
    if (paramInt == 3) {
      return paramArrayOfByte;
    }

    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 59, paramArrayOfByte);
    localSQLException.fillInStackTrace();
    throw localSQLException;
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