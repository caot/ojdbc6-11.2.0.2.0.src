package oracle.sql;

import java.sql.Connection;
import java.sql.SQLException;
import oracle.jdbc.driver.DatabaseError;
import oracle.jdbc.driver.OracleDriver;

public abstract class DatumWithConnection extends Datum
{
  private oracle.jdbc.internal.OracleConnection physicalConnection = null;

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  oracle.jdbc.internal.OracleConnection getPhysicalConnection()
  {
    if (this.physicalConnection == null)
    {
      try
      {
        this.physicalConnection = ((oracle.jdbc.internal.OracleConnection)new OracleDriver().defaultConnection());
      }
      catch (SQLException localSQLException)
      {
      }

    }

    return this.physicalConnection;
  }

  public DatumWithConnection(byte[] paramArrayOfByte)
    throws SQLException
  {
    super(paramArrayOfByte);
  }

  public DatumWithConnection()
  {
  }

  public static void assertNotNull(Connection paramConnection)
    throws SQLException
  {
    if (paramConnection == null)
    {
      SQLException localSQLException = DatabaseError.createSqlException(null, 68, "Connection is null");
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  public static void assertNotNull(TypeDescriptor paramTypeDescriptor)
    throws SQLException
  {
    if (paramTypeDescriptor == null)
    {
      SQLException localSQLException = DatabaseError.createSqlException(null, 61);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  public void setPhysicalConnectionOf(Connection paramConnection)
  {
    this.physicalConnection = ((oracle.jdbc.OracleConnection)paramConnection).physicalConnectionWithin();
  }

  public Connection getJavaSqlConnection()
    throws SQLException
  {
    return getPhysicalConnection().getWrapper();
  }

  public oracle.jdbc.OracleConnection getOracleConnection()
    throws SQLException
  {
    return getPhysicalConnection().getWrapper();
  }

  public oracle.jdbc.internal.OracleConnection getInternalConnection()
    throws SQLException
  {
    return getPhysicalConnection();
  }

  /** @deprecated */
  public oracle.jdbc.driver.OracleConnection getConnection()
    throws SQLException
  {
    oracle.jdbc.driver.OracleConnection localOracleConnection = null;
    try
    {
      localOracleConnection = (oracle.jdbc.driver.OracleConnection)((oracle.jdbc.driver.OracleConnection)this.physicalConnection).getWrapper();
    }
    catch (ClassCastException localClassCastException)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 103);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    return localOracleConnection;
  }

  protected oracle.jdbc.internal.OracleConnection getConnectionDuringExceptionHandling()
  {
    return this.physicalConnection;
  }
}