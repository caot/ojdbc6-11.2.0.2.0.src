package oracle.jdbc.rowset;

import java.io.InputStream;
import java.io.Reader;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.StringTokenizer;
import javax.sql.RowSet;
import javax.sql.RowSetInternal;
import javax.sql.RowSetWriter;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.driver.DatabaseError;
import oracle.jdbc.internal.OracleConnection;

public class OracleCachedRowSetWriter
  implements RowSetWriter, Serializable
{
  static final long serialVersionUID = 8932894189919931169L;
  private StringBuffer updateClause = new StringBuffer("");

  private StringBuffer deleteClause = new StringBuffer("");

  private StringBuffer insertClause = new StringBuffer("");
  private PreparedStatement insertStmt;
  private PreparedStatement updateStmt;
  private PreparedStatement deleteStmt;
  private ResultSetMetaData rsmd;
  private transient Connection connection;
  private int columnCount;
  static final int ASCII_STREAM = 1;
  static final int BINARY_STREAM = 2;
  static final int CHARACTER_STREAM = 3;
  static final int NCHARACTER_STREAM = 4;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  private String getSchemaName(RowSet paramRowSet)
    throws SQLException
  {
    return paramRowSet.getUsername();
  }

  private String getTableName(RowSet paramRowSet)
    throws SQLException
  {
    String str1 = ((OracleCachedRowSet)paramRowSet).getTableName();
    if (str1 != null) {
      return str1;
    }
    String str2 = paramRowSet.getCommand().toUpperCase();

    int i = str2.indexOf(" FROM ");

    if (i == -1)
    {
      localObject = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 343, str2.length() != 0 ? str2 : "Please use RowSet.setCommand (String) to set the SQL query string.");
      ((SQLException)localObject).fillInStackTrace();
      throw ((Throwable)localObject);
    }

    Object localObject = str2.substring(i + 6).trim();

    StringTokenizer localStringTokenizer = new StringTokenizer((String)localObject);
    if (localStringTokenizer.hasMoreTokens()) {
      localObject = localStringTokenizer.nextToken();
    }
    return localObject;
  }

  private void initSQLStatement(RowSet paramRowSet)
    throws SQLException
  {
    this.insertClause = new StringBuffer("INSERT INTO " + getTableName(paramRowSet) + "(");
    this.updateClause = new StringBuffer("UPDATE " + getTableName(paramRowSet) + " SET ");
    this.deleteClause = new StringBuffer("DELETE FROM " + getTableName(paramRowSet) + " WHERE ");

    this.rsmd = paramRowSet.getMetaData();
    this.columnCount = this.rsmd.getColumnCount();

    for (int i = 0; i < this.columnCount; i++)
    {
      if (i != 0) this.insertClause.append(", ");
      this.insertClause.append(this.rsmd.getColumnName(i + 1));

      if (i != 0) this.updateClause.append(", ");
      this.updateClause.append(this.rsmd.getColumnName(i + 1) + " = :" + i);

      if (i != 0) this.deleteClause.append(" AND ");
      this.deleteClause.append(this.rsmd.getColumnName(i + 1) + " = :" + i);
    }
    this.insertClause.append(") VALUES (");
    this.updateClause.append(" WHERE ");

    for (i = 0; i < this.columnCount; i++)
    {
      if (i != 0) this.insertClause.append(", ");
      this.insertClause.append(":" + i);

      if (i != 0) this.updateClause.append(" AND ");
      this.updateClause.append(this.rsmd.getColumnName(i + 1) + " = :" + i);
    }
    this.insertClause.append(")");

    this.insertStmt = this.connection.prepareStatement(this.insertClause.substring(0, this.insertClause.length()));

    this.updateStmt = this.connection.prepareStatement(this.updateClause.substring(0, this.updateClause.length()));

    this.deleteStmt = this.connection.prepareStatement(this.deleteClause.substring(0, this.deleteClause.length()));
  }

  private boolean insertRow(OracleRow paramOracleRow)
    throws SQLException
  {
    this.insertStmt.clearParameters();
    for (int i = 1; i <= this.columnCount; i++)
    {
      Object localObject = null;
      localObject = paramOracleRow.isColumnChanged(i) ? paramOracleRow.getModifiedColumn(i) : paramOracleRow.getColumn(i);

      if (localObject == null) {
        this.insertStmt.setNull(i, this.rsmd.getColumnType(i));
        paramOracleRow.markOriginalNull(i, true);
      } else {
        this.insertStmt.setObject(i, localObject);
      }
    }
    return this.insertStmt.executeUpdate() == 1;
  }

  private boolean updateRow(RowSet paramRowSet, OracleRow paramOracleRow)
    throws SQLException
  {
    this.updateStmt.clearParameters();
    for (int i = 1; i <= this.columnCount; i++)
    {
      Object localObject = null;
      localObject = paramOracleRow.isColumnChanged(i) ? paramOracleRow.getModifiedColumn(i) : paramOracleRow.getColumn(i);

      if (localObject == null) {
        this.updateStmt.setNull(i, this.rsmd.getColumnType(i));
      }
      else if ((localObject instanceof Reader))
      {
        OraclePreparedStatement localOraclePreparedStatement = (OraclePreparedStatement)this.updateStmt;
        if (paramOracleRow.columnTypeInfo[(i - 1)][1] == 4)
          localOraclePreparedStatement.setFormOfUse(i, (short)2);
        else if (paramOracleRow.columnTypeInfo[(i - 1)][1] == 3) {
          localOraclePreparedStatement.setFormOfUse(i, (short)1);
        }
        this.updateStmt.setCharacterStream(i, (Reader)localObject, paramOracleRow.columnTypeInfo[(i - 1)][0]);
      }
      else if ((localObject instanceof InputStream))
      {
        if (paramOracleRow.columnTypeInfo[(i - 1)][1] == 2) {
          this.updateStmt.setBinaryStream(i, (InputStream)localObject, paramOracleRow.columnTypeInfo[(i - 1)][0]);
        }
        else if (paramOracleRow.columnTypeInfo[(i - 1)][1] == 1) {
          this.updateStmt.setAsciiStream(i, (InputStream)localObject, paramOracleRow.columnTypeInfo[(i - 1)][0]);
        }
      }
      else
      {
        this.updateStmt.setObject(i, localObject);
      }
    }
    for (i = 1; i <= this.columnCount; i++)
    {
      if (paramOracleRow.isOriginalNull(i)) {
        return updateRowWithNull(paramRowSet, paramOracleRow);
      }
      this.updateStmt.setObject(i + this.columnCount, paramOracleRow.getColumn(i));
    }

    return this.updateStmt.executeUpdate() == 1;
  }

  private boolean updateRowWithNull(RowSet paramRowSet, OracleRow paramOracleRow)
    throws SQLException
  {
    boolean bool = false;
    StringBuffer localStringBuffer = new StringBuffer("UPDATE " + getTableName(paramRowSet) + " SET ");

    for (int i = 1; i <= this.columnCount; i++)
    {
      if (i != 1) {
        localStringBuffer.append(", ");
      }
      localStringBuffer.append(this.rsmd.getColumnName(i) + " = :" + i);
    }

    localStringBuffer.append(" WHERE ");

    for (i = 1; i <= this.columnCount; i++)
    {
      if (i != 1)
        localStringBuffer.append(" AND ");
      if (paramOracleRow.isOriginalNull(i))
        localStringBuffer.append(this.rsmd.getColumnName(i) + " IS NULL ");
      else {
        localStringBuffer.append(this.rsmd.getColumnName(i) + " = :" + i);
      }
    }
    PreparedStatement localPreparedStatement = null;
    try
    {
      localPreparedStatement = this.connection.prepareStatement(localStringBuffer.substring(0, localStringBuffer.length()));

      for (int j = 1; j <= this.columnCount; j++)
      {
        Object localObject1 = null;
        localObject1 = paramOracleRow.isColumnChanged(j) ? paramOracleRow.getModifiedColumn(j) : paramOracleRow.getColumn(j);

        if (localObject1 == null) {
          localPreparedStatement.setNull(j, this.rsmd.getColumnType(j));
        }
        else if ((localObject1 instanceof Reader))
        {
          OraclePreparedStatement localOraclePreparedStatement = (OraclePreparedStatement)localPreparedStatement;
          if (paramOracleRow.columnTypeInfo[(j - 1)][1] == 4)
            localOraclePreparedStatement.setFormOfUse(j, (short)2);
          else if (paramOracleRow.columnTypeInfo[(j - 1)][1] == 3) {
            localOraclePreparedStatement.setFormOfUse(j, (short)1);
          }
          localPreparedStatement.setCharacterStream(j, (Reader)localObject1, paramOracleRow.columnTypeInfo[(j - 1)][0]);
        }
        else if ((localObject1 instanceof InputStream))
        {
          if (paramOracleRow.columnTypeInfo[(j - 1)][1] == 2) {
            localPreparedStatement.setBinaryStream(j, (InputStream)localObject1, paramOracleRow.columnTypeInfo[(j - 1)][0]);
          }
          else if (paramOracleRow.columnTypeInfo[(j - 1)][1] == 1) {
            localPreparedStatement.setAsciiStream(j, (InputStream)localObject1, paramOracleRow.columnTypeInfo[(j - 1)][0]);
          }
        }
        else
        {
          localPreparedStatement.setObject(j, localObject1);
        }
      }

      j = 1; for (int k = 1; j <= this.columnCount; j++)
      {
        if (!paramOracleRow.isOriginalNull(j))
        {
          localPreparedStatement.setObject(k + this.columnCount, paramOracleRow.getColumn(j));

          k++;
        }
      }
      bool = localPreparedStatement.executeUpdate() == 1;
    }
    finally {
      if (localPreparedStatement != null) {
        localPreparedStatement.close();
      }
    }
    return bool;
  }

  private boolean deleteRow(RowSet paramRowSet, OracleRow paramOracleRow)
    throws SQLException
  {
    this.deleteStmt.clearParameters();
    for (int i = 1; i <= this.columnCount; i++)
    {
      if (paramOracleRow.isOriginalNull(i)) {
        return deleteRowWithNull(paramRowSet, paramOracleRow);
      }
      Object localObject = paramOracleRow.getColumn(i);
      if (localObject == null)
        this.deleteStmt.setNull(i, this.rsmd.getColumnType(i));
      else {
        this.deleteStmt.setObject(i, localObject);
      }
    }
    return this.deleteStmt.executeUpdate() == 1;
  }

  private boolean deleteRowWithNull(RowSet paramRowSet, OracleRow paramOracleRow)
    throws SQLException
  {
    boolean bool = false;
    StringBuffer localStringBuffer = new StringBuffer("DELETE FROM " + getTableName(paramRowSet) + " WHERE ");

    for (int i = 1; i <= this.columnCount; i++)
    {
      if (i != 1)
        localStringBuffer.append(" AND ");
      if (paramOracleRow.isOriginalNull(i))
        localStringBuffer.append(this.rsmd.getColumnName(i) + " IS NULL ");
      else {
        localStringBuffer.append(this.rsmd.getColumnName(i) + " = :" + i);
      }
    }
    PreparedStatement localPreparedStatement = null;
    try
    {
      localPreparedStatement = this.connection.prepareStatement(localStringBuffer.substring(0, localStringBuffer.length()));

      int j = 1; for (int k = 1; j <= this.columnCount; j++)
      {
        if (!paramOracleRow.isOriginalNull(j))
        {
          localPreparedStatement.setObject(k++, paramOracleRow.getColumn(j));
        }
      }
      bool = localPreparedStatement.executeUpdate() == 1;
    }
    finally {
      if (localPreparedStatement != null) {
        localPreparedStatement.close();
      }
    }
    return bool;
  }

  public synchronized boolean writeData(RowSetInternal paramRowSetInternal)
    throws SQLException
  {
    OracleCachedRowSet localOracleCachedRowSet = (OracleCachedRowSet)paramRowSetInternal;
    this.connection = ((OracleCachedRowSetReader)localOracleCachedRowSet.getReader()).getConnection(paramRowSetInternal);

    if (this.connection == null)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 342);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if (this.connection.getAutoCommit()) {
      this.connection.setAutoCommit(false);
    }
    try
    {
      this.connection.setTransactionIsolation(localOracleCachedRowSet.getTransactionIsolation());
    }
    catch (Exception localException)
    {
    }

    initSQLStatement(localOracleCachedRowSet);
    if (this.columnCount < 1)
    {
      this.connection.close();
      return true;
    }
    boolean bool = localOracleCachedRowSet.getShowDeleted();
    localOracleCachedRowSet.setShowDeleted(true);
    localOracleCachedRowSet.beforeFirst();
    int i = 1;
    int j = 1;
    int k = 1;
    OracleRow localOracleRow = null;
    while (localOracleCachedRowSet.next())
    {
      if (localOracleCachedRowSet.rowInserted())
      {
        if (!localOracleCachedRowSet.rowDeleted())
        {
          localOracleRow = localOracleCachedRowSet.getCurrentRow();

          j = (insertRow(localOracleRow)) || (j != 0) ? 1 : 0;
        }
      } else if (localOracleCachedRowSet.rowUpdated())
      {
        localOracleRow = localOracleCachedRowSet.getCurrentRow();

        i = (updateRow(localOracleCachedRowSet, localOracleRow)) || (i != 0) ? 1 : 0;
      }
      else if (localOracleCachedRowSet.rowDeleted())
      {
        localOracleRow = localOracleCachedRowSet.getCurrentRow();

        k = (deleteRow(localOracleCachedRowSet, localOracleRow)) || (k != 0) ? 1 : 0;
      }

    }

    if ((i != 0) && (j != 0) && (k != 0))
    {
      this.connection.commit();

      localOracleCachedRowSet.setOriginal();
    }
    else {
      this.connection.rollback();
    }
    this.insertStmt.close();
    this.updateStmt.close();
    this.deleteStmt.close();

    if (!localOracleCachedRowSet.isConnectionStayingOpen())
    {
      this.connection.close();
    }

    localOracleCachedRowSet.setShowDeleted(bool);
    return true;
  }

  protected OracleConnection getConnectionDuringExceptionHandling()
  {
    return null;
  }
}