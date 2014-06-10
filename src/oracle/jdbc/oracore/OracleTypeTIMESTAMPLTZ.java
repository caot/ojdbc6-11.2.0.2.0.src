package oracle.jdbc.oracore;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Map;
import oracle.jdbc.driver.DatabaseError;
import oracle.jdbc.internal.OracleConnection;
import oracle.sql.DATE;
import oracle.sql.Datum;
import oracle.sql.TIMESTAMPLTZ;

public class OracleTypeTIMESTAMPLTZ extends OracleType
  implements Serializable
{
  static final long serialVersionUID = 1615519855865602397L;
  int precision = 0;
  transient OracleConnection connection;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  protected OracleTypeTIMESTAMPLTZ()
  {
  }

  public OracleTypeTIMESTAMPLTZ(OracleConnection paramOracleConnection)
  {
    this.connection = paramOracleConnection;
  }

  public int getTypeCode()
  {
    return -102;
  }

  public void parseTDSrec(TDSReader paramTDSReader)
    throws SQLException
  {
    this.precision = paramTDSReader.readByte();
  }

  public int getScale()
    throws SQLException
  {
    return 0;
  }

  public int getPrecision()
    throws SQLException
  {
    return this.precision;
  }

  private void readObject(ObjectInputStream paramObjectInputStream)
    throws IOException, ClassNotFoundException
  {
    this.precision = paramObjectInputStream.readByte();
  }

  private void writeObject(ObjectOutputStream paramObjectOutputStream)
    throws IOException
  {
    paramObjectOutputStream.writeByte(this.precision);
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
      return new TIMESTAMPLTZ(paramArrayOfByte);
    case 2:
      return TIMESTAMPLTZ.toTimestamp(this.connection, paramArrayOfByte);
    case 3:
      return paramArrayOfByte;
    }

    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 59);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public Datum toDatum(Object paramObject, OracleConnection paramOracleConnection)
    throws SQLException
  {
    TIMESTAMPLTZ localTIMESTAMPLTZ = null;

    if (paramObject != null)
    {
      try
      {
        if ((paramObject instanceof TIMESTAMPLTZ)) {
          localTIMESTAMPLTZ = (TIMESTAMPLTZ)paramObject;
        } else if ((paramObject instanceof byte[])) {
          localTIMESTAMPLTZ = new TIMESTAMPLTZ((byte[])paramObject);
        } else if ((paramObject instanceof Timestamp)) {
          localTIMESTAMPLTZ = new TIMESTAMPLTZ(paramOracleConnection, (Timestamp)paramObject);
        } else if ((paramObject instanceof DATE)) {
          localTIMESTAMPLTZ = new TIMESTAMPLTZ(paramOracleConnection, (DATE)paramObject);
        } else if ((paramObject instanceof String)) {
          localTIMESTAMPLTZ = new TIMESTAMPLTZ(paramOracleConnection, (String)paramObject);
        } else if ((paramObject instanceof Date)) {
          localTIMESTAMPLTZ = new TIMESTAMPLTZ(paramOracleConnection, (Date)paramObject);
        } else if ((paramObject instanceof Time)) {
          localTIMESTAMPLTZ = new TIMESTAMPLTZ(paramOracleConnection, (Time)paramObject);
        }
        else {
          SQLException localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 59, paramObject);
          localSQLException1.fillInStackTrace();
          throw localSQLException1;
        }

      }
      catch (Exception localException)
      {
        SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 59, paramObject);
        localSQLException2.fillInStackTrace();
        throw localSQLException2;
      }

    }

    return localTIMESTAMPLTZ;
  }

  protected Object unpickle81rec(UnpickleContext paramUnpickleContext, int paramInt1, int paramInt2, Map paramMap)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 90);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  protected OracleConnection getConnectionDuringExceptionHandling()
  {
    return this.connection;
  }
}