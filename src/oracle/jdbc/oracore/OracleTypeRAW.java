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
import oracle.sql.RAW;

public class OracleTypeRAW extends OracleType
  implements Serializable
{
  static final long serialVersionUID = -6083664758336974576L;
  int length;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  public OracleTypeRAW()
  {
  }

  public OracleTypeRAW(int paramInt)
  {
    super(paramInt);
  }

  public Datum toDatum(Object paramObject, OracleConnection paramOracleConnection)
    throws SQLException
  {
    RAW localRAW = null;

    if (paramObject != null)
    {
      try
      {
        if ((paramObject instanceof RAW))
          localRAW = (RAW)paramObject;
        else {
          localRAW = new RAW(paramObject);
        }
      }
      catch (SQLException localSQLException1)
      {
        SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 59, paramObject);
        localSQLException2.fillInStackTrace();
        throw localSQLException2;
      }

    }

    return localRAW;
  }

  public Datum[] toDatumArray(Object paramObject, OracleConnection paramOracleConnection, long paramLong, int paramInt)
    throws SQLException
  {
    Datum[] arrayOfDatum = null;

    if (paramObject != null)
    {
      Object localObject;
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

        localObject = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 59, paramObject);
        ((SQLException)localObject).fillInStackTrace();
        throw ((Throwable)localObject);
      }
    }

    return arrayOfDatum;
  }

  public int getTypeCode()
  {
    return -2;
  }

  public void parseTDSrec(TDSReader paramTDSReader)
    throws SQLException
  {
    super.parseTDSrec(paramTDSReader);

    this.length = paramTDSReader.readUB2();
  }

  protected int pickle81(PickleContext paramPickleContext, Datum paramDatum)
    throws SQLException
  {
    if (paramDatum.getLength() > this.length)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 72, this);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    int i = paramPickleContext.writeLength((int)paramDatum.getLength());

    i += paramPickleContext.writeData(paramDatum.shareBytes());

    return i;
  }

  public int getLength()
  {
    return this.length;
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
      return new RAW(paramArrayOfByte);
    case 2:
    case 3:
      return paramArrayOfByte;
    }

    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 59, paramArrayOfByte);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  private void writeObject(ObjectOutputStream paramObjectOutputStream)
    throws IOException
  {
    paramObjectOutputStream.writeInt(this.length);
  }

  private void readObject(ObjectInputStream paramObjectInputStream)
    throws IOException, ClassNotFoundException
  {
    this.length = paramObjectInputStream.readInt();
  }
}