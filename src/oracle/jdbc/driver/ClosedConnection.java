package oracle.jdbc.driver;

import java.sql.Connection;
import java.sql.SQLException;
import oracle.jdbc.pool.OraclePooledConnection;
import oracle.sql.BLOB;
import oracle.sql.CLOB;

class ClosedConnection extends PhysicalConnection
{
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  ClosedConnection()
  {
    this.lifecycle = 4;
  }

  void initializePassword(String paramString)
    throws SQLException
  {
  }

  OracleStatement RefCursorBytesToStatement(byte[] paramArrayOfByte, OracleStatement paramOracleStatement)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 8);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  int getDefaultStreamChunkSize()
  {
    return -1;
  }

  short doGetVersionNumber()
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 8);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  String doGetDatabaseProductVersion()
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 8);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  void doRollback()
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 8);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  void doCommit(int paramInt)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 8);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  void doSetAutoCommit(boolean paramBoolean)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 8);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  void cancelOperationOnServer()
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 8);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  void doAbort()
    throws SQLException
  {
  }

  void open(OracleStatement paramOracleStatement)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 8);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  void logon()
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 8);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void getPropertyForPooledConnection(OraclePooledConnection paramOraclePooledConnection)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 8);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public BLOB createTemporaryBlob(Connection paramConnection, boolean paramBoolean, int paramInt)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 8);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public CLOB createTemporaryClob(Connection paramConnection, boolean paramBoolean, int paramInt, short paramShort)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 8);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }
}