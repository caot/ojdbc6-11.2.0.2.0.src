package oracle.jdbc.driver;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import oracle.jdbc.OracleResultSetCache;
import oracle.jdbc.dcn.DatabaseChangeRegistration;
import oracle.jdbc.internal.OracleCallableStatement;
import oracle.jdbc.internal.OracleConnection;

class OracleStatementWrapper
  implements oracle.jdbc.internal.OracleStatement
{
  private Object forEquals;
  protected oracle.jdbc.internal.OracleStatement statement;
  static final OracleCallableStatement closedStatement = new OracleClosedStatement();

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  OracleStatementWrapper(oracle.jdbc.OracleStatement paramOracleStatement)
    throws SQLException
  {
    this.forEquals = paramOracleStatement;

    this.statement = ((oracle.jdbc.internal.OracleStatement)paramOracleStatement);
    ((OracleStatement)paramOracleStatement).wrapper = this;
  }

  public void close()
    throws SQLException
  {
    this.statement.close();
    this.statement = closedStatement;
  }

  public void closeWithKey(String paramString)
    throws SQLException
  {
    this.statement.closeWithKey(paramString);
    this.statement = closedStatement;
  }

  public boolean equals(Object paramObject)
  {
    return (paramObject != null) && (getClass() == paramObject.getClass()) && (this.forEquals == ((OracleStatementWrapper)paramObject).forEquals);
  }

  public int hashCode()
  {
    return this.forEquals.hashCode();
  }

  public int getFetchDirection()
    throws SQLException
  {
    return this.statement.getFetchDirection();
  }

  public int getFetchSize()
    throws SQLException
  {
    return this.statement.getFetchSize();
  }

  public int getMaxFieldSize()
    throws SQLException
  {
    return this.statement.getMaxFieldSize();
  }

  public int getMaxRows()
    throws SQLException
  {
    return this.statement.getMaxRows();
  }

  public int getQueryTimeout()
    throws SQLException
  {
    return this.statement.getQueryTimeout();
  }

  public int getResultSetConcurrency()
    throws SQLException
  {
    return this.statement.getResultSetConcurrency();
  }

  public int getResultSetHoldability()
    throws SQLException
  {
    return this.statement.getResultSetHoldability();
  }

  public int getResultSetType()
    throws SQLException
  {
    return this.statement.getResultSetType();
  }

  public int getUpdateCount()
    throws SQLException
  {
    return this.statement.getUpdateCount();
  }

  public void cancel()
    throws SQLException
  {
    this.statement.cancel();
  }

  public void clearBatch()
    throws SQLException
  {
    this.statement.clearBatch();
  }

  public void clearWarnings()
    throws SQLException
  {
    this.statement.clearWarnings();
  }

  public boolean getMoreResults()
    throws SQLException
  {
    return this.statement.getMoreResults();
  }

  public int[] executeBatch()
    throws SQLException
  {
    return this.statement.executeBatch();
  }

  public void setFetchDirection(int paramInt)
    throws SQLException
  {
    this.statement.setFetchDirection(paramInt);
  }

  public void setFetchSize(int paramInt)
    throws SQLException
  {
    this.statement.setFetchSize(paramInt);
  }

  public void setMaxFieldSize(int paramInt)
    throws SQLException
  {
    this.statement.setMaxFieldSize(paramInt);
  }

  public void setMaxRows(int paramInt)
    throws SQLException
  {
    this.statement.setMaxRows(paramInt);
  }

  public void setQueryTimeout(int paramInt)
    throws SQLException
  {
    this.statement.setQueryTimeout(paramInt);
  }

  public boolean getMoreResults(int paramInt)
    throws SQLException
  {
    return this.statement.getMoreResults(paramInt);
  }

  public void setEscapeProcessing(boolean paramBoolean)
    throws SQLException
  {
    this.statement.setEscapeProcessing(paramBoolean);
  }

  public int executeUpdate(String paramString)
    throws SQLException
  {
    return this.statement.executeUpdate(paramString);
  }

  public void addBatch(String paramString)
    throws SQLException
  {
    this.statement.addBatch(paramString);
  }

  public void setCursorName(String paramString)
    throws SQLException
  {
    this.statement.setCursorName(paramString);
  }

  public boolean execute(String paramString)
    throws SQLException
  {
    return this.statement.execute(paramString);
  }

  public int executeUpdate(String paramString, int paramInt)
    throws SQLException
  {
    return this.statement.executeUpdate(paramString, paramInt);
  }

  public boolean execute(String paramString, int paramInt)
    throws SQLException
  {
    return this.statement.execute(paramString, paramInt);
  }

  public int executeUpdate(String paramString, int[] paramArrayOfInt)
    throws SQLException
  {
    return this.statement.executeUpdate(paramString, paramArrayOfInt);
  }

  public boolean execute(String paramString, int[] paramArrayOfInt)
    throws SQLException
  {
    return this.statement.execute(paramString, paramArrayOfInt);
  }

  public Connection getConnection()
    throws SQLException
  {
    return this.statement.getConnection();
  }

  public ResultSet getGeneratedKeys()
    throws SQLException
  {
    return this.statement.getGeneratedKeys();
  }

  public ResultSet getResultSet()
    throws SQLException
  {
    return this.statement.getResultSet();
  }

  public SQLWarning getWarnings()
    throws SQLException
  {
    return this.statement.getWarnings();
  }

  public int executeUpdate(String paramString, String[] paramArrayOfString)
    throws SQLException
  {
    return this.statement.executeUpdate(paramString, paramArrayOfString);
  }

  public boolean execute(String paramString, String[] paramArrayOfString)
    throws SQLException
  {
    return this.statement.execute(paramString, paramArrayOfString);
  }

  public ResultSet executeQuery(String paramString)
    throws SQLException
  {
    return this.statement.executeQuery(paramString);
  }

  public void clearDefines()
    throws SQLException
  {
    this.statement.clearDefines();
  }

  public void defineColumnType(int paramInt1, int paramInt2)
    throws SQLException
  {
    this.statement.defineColumnType(paramInt1, paramInt2);
  }

  public void defineColumnType(int paramInt1, int paramInt2, int paramInt3)
    throws SQLException
  {
    this.statement.defineColumnType(paramInt1, paramInt2, paramInt3);
  }

  public void defineColumnType(int paramInt1, int paramInt2, int paramInt3, short paramShort)
    throws SQLException
  {
    this.statement.defineColumnType(paramInt1, paramInt2, paramInt3, paramShort);
  }

  public void defineColumnTypeBytes(int paramInt1, int paramInt2, int paramInt3)
    throws SQLException
  {
    this.statement.defineColumnTypeBytes(paramInt1, paramInt2, paramInt3);
  }

  public void defineColumnTypeChars(int paramInt1, int paramInt2, int paramInt3)
    throws SQLException
  {
    this.statement.defineColumnTypeChars(paramInt1, paramInt2, paramInt3);
  }

  public void defineColumnType(int paramInt1, int paramInt2, String paramString)
    throws SQLException
  {
    this.statement.defineColumnType(paramInt1, paramInt2, paramString);
  }

  public int getRowPrefetch()
  {
    return this.statement.getRowPrefetch();
  }

  public void setResultSetCache(OracleResultSetCache paramOracleResultSetCache)
    throws SQLException
  {
    this.statement.setResultSetCache(paramOracleResultSetCache);
  }

  public void setRowPrefetch(int paramInt)
    throws SQLException
  {
    this.statement.setRowPrefetch(paramInt);
  }

  public int getLobPrefetchSize()
  {
    return this.statement.getLobPrefetchSize();
  }

  public void setLobPrefetchSize(int paramInt)
    throws SQLException
  {
    this.statement.setLobPrefetchSize(paramInt);
  }

  public int creationState()
  {
    return this.statement.creationState();
  }

  public boolean isNCHAR(int paramInt)
    throws SQLException
  {
    return this.statement.isNCHAR(paramInt);
  }

  public void setDatabaseChangeRegistration(DatabaseChangeRegistration paramDatabaseChangeRegistration)
    throws SQLException
  {
    this.statement.setDatabaseChangeRegistration(paramDatabaseChangeRegistration);
  }

  public boolean isClosed()
    throws SQLException
  {
    return this.statement.isClosed();
  }

  public boolean isPoolable()
    throws SQLException
  {
    return this.statement.isPoolable();
  }

  public void setPoolable(boolean paramBoolean)
    throws SQLException
  {
    this.statement.setPoolable(paramBoolean);
  }

  public boolean isWrapperFor(Class<?> paramClass)
    throws SQLException
  {
    if (paramClass.isInterface()) return paramClass.isInstance(this);

    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 177);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public <T> T unwrap(Class<T> paramClass)
    throws SQLException
  {
    if ((paramClass.isInterface()) && (paramClass.isInstance(this))) return this;

    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 177);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setFixedString(boolean paramBoolean)
  {
    this.statement.setFixedString(paramBoolean);
  }

  public boolean getFixedString()
  {
    return this.statement.getFixedString();
  }

  public int sendBatch()
    throws SQLException
  {
    return this.statement.sendBatch();
  }

  public boolean getserverCursor()
  {
    return this.statement.getserverCursor();
  }

  public int getcacheState()
  {
    return this.statement.getcacheState();
  }

  public int getstatementType()
  {
    return this.statement.getstatementType();
  }

  public String[] getRegisteredTableNames()
    throws SQLException
  {
    return this.statement.getRegisteredTableNames();
  }

  public long getRegisteredQueryId()
    throws SQLException
  {
    return this.statement.getRegisteredQueryId();
  }

  protected OracleConnection getConnectionDuringExceptionHandling()
  {
    return null;
  }
}