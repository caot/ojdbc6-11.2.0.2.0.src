package oracle.jdbc.oracore;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Map;
import oracle.jdbc.driver.DatabaseError;
import oracle.jdbc.internal.OracleConnection;
import oracle.sql.CLOB;
import oracle.sql.Datum;

public class OracleTypeCLOB extends OracleType
  implements Serializable
{
  static final long serialVersionUID = 1122821330765834411L;
  static int fixedDataSize = 86;
  transient OracleConnection connection;
  int form;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  protected OracleTypeCLOB()
  {
  }

  public OracleTypeCLOB(OracleConnection paramOracleConnection)
  {
    this.connection = paramOracleConnection;
  }

  public Datum toDatum(Object paramObject, OracleConnection paramOracleConnection)
    throws SQLException
  {
    CLOB localCLOB = null;

    if (paramObject != null)
    {
      if ((paramObject instanceof CLOB)) {
        localCLOB = (CLOB)paramObject;
      }
      else {
        SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 59, paramObject);
        localSQLException.fillInStackTrace();
        throw localSQLException;
      }
    }

    return localCLOB;
  }

  public int getTypeCode()
  {
    return 2005;
  }

  protected Object toObject(byte[] paramArrayOfByte, int paramInt, Map paramMap)
    throws SQLException
  {
    if ((paramArrayOfByte == null) || (paramArrayOfByte.length == 0)) {
      return null;
    }
    if ((paramInt == 1) || (paramInt == 2)) {
      return this.connection.createClobWithUnpickledBytes(paramArrayOfByte);
    }

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

  public void setConnection(OracleConnection paramOracleConnection)
    throws SQLException
  {
    this.connection = paramOracleConnection;
  }

  public boolean isNCHAR()
    throws SQLException
  {
    return this.form == 2;
  }

  public void setForm(int paramInt)
  {
    this.form = paramInt;
  }

  protected OracleConnection getConnectionDuringExceptionHandling()
  {
    return this.connection;
  }
}