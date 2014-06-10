package oracle.jdbc.driver;

import java.sql.SQLException;
import oracle.jdbc.internal.OracleConnection;

class OracleSavepoint
  implements oracle.jdbc.OracleSavepoint
{
  private static final int MAX_ID_VALUE = 65535;
  private static final int INVALID_ID_VALUE = -1;
  static final int NAMED_SAVEPOINT_TYPE = 2;
  static final int UNNAMED_SAVEPOINT_TYPE = 1;
  static final int UNKNOWN_SAVEPOINT_TYPE = 0;
  private static int s_seedId = 0;
  private int m_id = -1;
  private String m_name = null;
  private int m_type = 0;

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  OracleSavepoint()
  {
    this.m_type = 1;
    this.m_id = getNextId();
    this.m_name = null;
  }

  OracleSavepoint(String paramString)
    throws SQLException
  {
    if ((paramString != null) && (paramString.length() != 0) && (!OracleSql.isValidObjectName(paramString)))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.m_type = 2;
    this.m_name = paramString;
    this.m_id = -1;
  }

  public int getSavepointId()
    throws SQLException
  {
    if (this.m_type == 2)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 118);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    return this.m_id;
  }

  public String getSavepointName()
    throws SQLException
  {
    if (this.m_type == 1)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 119);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    return this.m_name;
  }

  int getType()
  {
    return this.m_type;
  }

  private synchronized int getNextId()
  {
    s_seedId = (s_seedId + 1) % 65535;

    return s_seedId;
  }

  protected OracleConnection getConnectionDuringExceptionHandling()
  {
    return null;
  }
}