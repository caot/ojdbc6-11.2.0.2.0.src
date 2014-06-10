package oracle.jdbc.xa.client;

import java.sql.SQLException;
import oracle.jdbc.driver.DatabaseError;
import oracle.jdbc.driver.OracleCloseCallback;
import oracle.jdbc.internal.OracleConnection;

public class OracleXAHeteroCloseCallback
  implements OracleCloseCallback
{
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  public synchronized void beforeClose(OracleConnection paramOracleConnection, Object paramObject)
  {
  }

  public synchronized void afterClose(Object paramObject)
  {
    int i = ((OracleXAHeteroConnection)paramObject).getRmid();
    String str = ((OracleXAHeteroConnection)paramObject).getXaCloseString();
    try
    {
      int j = t2cDoXaClose(str, i, 0, 0);

      if (j != 0)
      {
        SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), -1 * j);
        localSQLException2.fillInStackTrace();
        throw localSQLException2;
      }
    }
    catch (SQLException localSQLException1)
    {
    }
  }

  protected OracleConnection getConnectionDuringExceptionHandling()
  {
    return null;
  }

  private native int t2cDoXaClose(String paramString, int paramInt1, int paramInt2, int paramInt3);
}