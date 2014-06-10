package oracle.jdbc;

import java.sql.SQLException;
import java.sql.Statement;
import oracle.jdbc.dcn.DatabaseChangeRegistration;

public abstract interface OracleStatement extends Statement
{
  public static final int NEW = 0;
  public static final int IMPLICIT = 1;
  public static final int EXPLICIT = 2;

  public abstract void clearDefines()
    throws SQLException;

  public abstract void defineColumnType(int paramInt1, int paramInt2)
    throws SQLException;

  public abstract void defineColumnType(int paramInt1, int paramInt2, int paramInt3)
    throws SQLException;

  public abstract void defineColumnType(int paramInt1, int paramInt2, int paramInt3, short paramShort)
    throws SQLException;

  public abstract void defineColumnTypeBytes(int paramInt1, int paramInt2, int paramInt3)
    throws SQLException;

  public abstract void defineColumnTypeChars(int paramInt1, int paramInt2, int paramInt3)
    throws SQLException;

  public abstract void defineColumnType(int paramInt1, int paramInt2, String paramString)
    throws SQLException;

  public abstract int getRowPrefetch();

  public abstract void setResultSetCache(OracleResultSetCache paramOracleResultSetCache)
    throws SQLException;

  public abstract void setRowPrefetch(int paramInt)
    throws SQLException;

  public abstract int getLobPrefetchSize();

  public abstract void setLobPrefetchSize(int paramInt)
    throws SQLException;

  public abstract void closeWithKey(String paramString)
    throws SQLException;

  /** @deprecated */
  public abstract int creationState();

  public abstract boolean isNCHAR(int paramInt)
    throws SQLException;

  public abstract void setDatabaseChangeRegistration(DatabaseChangeRegistration paramDatabaseChangeRegistration)
    throws SQLException;

  public abstract String[] getRegisteredTableNames()
    throws SQLException;

  public abstract long getRegisteredQueryId()
    throws SQLException;
}