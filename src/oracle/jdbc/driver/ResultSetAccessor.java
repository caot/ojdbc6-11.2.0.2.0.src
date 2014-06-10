package oracle.jdbc.driver;

import java.sql.ResultSet;
import java.sql.SQLException;

class ResultSetAccessor extends Accessor
{
  static final int maxLength = 16;
  OracleStatement currentStmt;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  ResultSetAccessor(OracleStatement paramOracleStatement, int paramInt1, short paramShort, int paramInt2, boolean paramBoolean)
    throws SQLException
  {
    init(paramOracleStatement, 102, 116, paramShort, paramBoolean);
    initForDataAccess(paramInt2, paramInt1, null);
  }

  ResultSetAccessor(OracleStatement paramOracleStatement, int paramInt1, boolean paramBoolean, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, short paramShort)
    throws SQLException
  {
    init(paramOracleStatement, 102, 116, paramShort, false);
    initForDescribe(102, paramInt1, paramBoolean, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramShort, null);

    initForDataAccess(0, paramInt1, null);
  }

  void initForDataAccess(int paramInt1, int paramInt2, String paramString)
    throws SQLException
  {
    if (paramInt1 != 0) {
      this.externalType = paramInt1;
    }
    this.internalTypeMaxLength = 16;

    if ((paramInt2 > 0) && (paramInt2 < this.internalTypeMaxLength)) {
      this.internalTypeMaxLength = paramInt2;
    }
    this.byteLength = this.internalTypeMaxLength;
  }

  ResultSet getCursor(int paramInt)
    throws SQLException
  {
    byte[] arrayOfByte = getBytes(paramInt);

    OracleStatement localOracleStatement = this.statement.connection.RefCursorBytesToStatement(arrayOfByte, this.statement);

    if ((this.currentStmt != null) && (this.currentStmt.cursorId == localOracleStatement.cursorId) && (this.currentStmt.currentResultSet != null))
    {
      return this.currentStmt.currentResultSet;
    }

    localOracleStatement.doDescribe(false);
    localOracleStatement.prepareAccessors();

    OracleResultSetImpl localOracleResultSetImpl = new OracleResultSetImpl(localOracleStatement.connection, localOracleStatement);

    localOracleResultSetImpl.close_statement_on_close = true;
    localOracleStatement.currentResultSet = localOracleResultSetImpl;
    this.currentStmt = localOracleStatement;

    return localOracleResultSetImpl;
  }

  Object getObject(int paramInt)
    throws SQLException
  {
    return getCursor(paramInt);
  }
}