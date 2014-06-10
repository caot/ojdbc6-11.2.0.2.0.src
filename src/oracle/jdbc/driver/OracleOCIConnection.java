package oracle.jdbc.driver;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;
import oracle.jdbc.pool.OracleOCIConnectionPool;

public abstract class OracleOCIConnection extends T2CConnection
{
  OracleOCIConnectionPool ociConnectionPool = null;
  boolean isPool = false;
  boolean aliasing = false;

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  public OracleOCIConnection(String paramString, Properties paramProperties, Object paramObject)
    throws SQLException
  {
    this(paramString, paramProperties, (OracleDriverExtension)paramObject);
  }

  OracleOCIConnection(String paramString, Properties paramProperties, OracleDriverExtension paramOracleDriverExtension)
    throws SQLException
  {
    super(paramString, paramProperties, paramOracleDriverExtension);
  }

  public synchronized byte[] getConnectionId()
    throws SQLException
  {
    byte[] arrayOfByte = t2cGetConnectionId(this.m_nativeState);

    if (arrayOfByte == null)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 254, "Cannot create a ByteArray for the connectionId");
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.aliasing = true;

    return arrayOfByte;
  }

  public synchronized void passwordChange(String paramString1, String paramString2, String paramString3)
    throws SQLException, IOException
  {
    ociPasswordChange(paramString1, paramString2, paramString3);
  }

  public synchronized void close()
    throws SQLException
  {
    if ((this.lifecycle == 2) || (this.lifecycle == 4) || (this.aliasing)) {
      return;
    }
    super.close();

    this.ociConnectionPool.connectionClosed((oracle.jdbc.oci.OracleOCIConnection)this);
  }

  public synchronized void setConnectionPool(OracleOCIConnectionPool paramOracleOCIConnectionPool)
  {
    this.ociConnectionPool = paramOracleOCIConnectionPool;
  }

  public synchronized void setStmtCacheSize(int paramInt, boolean paramBoolean)
    throws SQLException
  {
    super.setStmtCacheSize(paramInt, paramBoolean);
  }
}