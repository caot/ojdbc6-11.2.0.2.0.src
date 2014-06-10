package oracle.jdbc.xa;

import java.sql.SQLException;
import java.util.Properties;
import javax.sql.XAConnection;
import javax.sql.XADataSource;
import oracle.jdbc.internal.OracleConnection;
import oracle.jdbc.pool.OracleConnectionPoolDataSource;
import oracle.jdbc.pool.OracleDataSource;

public abstract class OracleXADataSource extends OracleConnectionPoolDataSource
  implements XADataSource
{
  protected boolean useNativeXA = false;
  protected boolean thinUseNativeXA = true;

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  public OracleXADataSource()
    throws SQLException
  {
    this.dataSourceName = "OracleXADataSource";
  }

  public abstract XAConnection getXAConnection()
    throws SQLException;

  public abstract XAConnection getXAConnection(String paramString1, String paramString2)
    throws SQLException;

  public abstract XAConnection getXAConnection(Properties paramProperties)
    throws SQLException;

  public synchronized void setNativeXA(boolean paramBoolean)
  {
    this.useNativeXA = paramBoolean;
    this.thinUseNativeXA = paramBoolean;
  }

  public synchronized boolean getNativeXA()
  {
    return this.useNativeXA;
  }

  protected void copy(OracleDataSource paramOracleDataSource)
    throws SQLException
  {
    super.copy(paramOracleDataSource);

    ((OracleXADataSource)paramOracleDataSource).useNativeXA = this.useNativeXA;
    ((OracleXADataSource)paramOracleDataSource).thinUseNativeXA = this.thinUseNativeXA;
  }

  protected OracleConnection getConnectionDuringExceptionHandling()
  {
    return null;
  }
}