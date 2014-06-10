package oracle.sql;

import java.sql.NClob;
import java.sql.SQLException;
import oracle.jdbc.OracleConnection;

public class NCLOB extends CLOB
  implements NClob
{
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  protected NCLOB()
  {
  }

  public NCLOB(OracleConnection paramOracleConnection)
    throws SQLException
  {
    this(paramOracleConnection, null);
  }

  public NCLOB(OracleConnection paramOracleConnection, byte[] paramArrayOfByte)
    throws SQLException
  {
    super(paramOracleConnection, paramArrayOfByte, (short)2);
  }

  public NCLOB(CLOB paramCLOB)
    throws SQLException
  {
    this(paramCLOB.getPhysicalConnection(), paramCLOB.getBytes());
  }
}