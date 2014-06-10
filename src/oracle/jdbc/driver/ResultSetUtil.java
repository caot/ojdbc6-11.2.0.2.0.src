package oracle.jdbc.driver;

import java.sql.SQLException;
import oracle.jdbc.internal.OracleConnection;

class ResultSetUtil
{
  static final int[][] allRsetTypes = { { 0, 0 }, { 1003, 1007 }, { 1003, 1008 }, { 1004, 1007 }, { 1004, 1008 }, { 1005, 1007 }, { 1005, 1008 } };

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  static OracleResultSet createScrollResultSet(ScrollRsetStatement paramScrollRsetStatement, OracleResultSet paramOracleResultSet, int paramInt)
    throws SQLException
  {
    switch (paramInt)
    {
    case 1:
      return paramOracleResultSet;
    case 2:
      return new UpdatableResultSet(paramScrollRsetStatement, (OracleResultSetImpl)paramOracleResultSet, getScrollType(paramInt), getUpdateConcurrency(paramInt));
    case 3:
      return new ScrollableResultSet(paramScrollRsetStatement, (OracleResultSetImpl)paramOracleResultSet, getScrollType(paramInt), getUpdateConcurrency(paramInt));
    case 4:
      ScrollableResultSet localScrollableResultSet = new ScrollableResultSet(paramScrollRsetStatement, (OracleResultSetImpl)paramOracleResultSet, getScrollType(paramInt), getUpdateConcurrency(paramInt));

      return new UpdatableResultSet(paramScrollRsetStatement, localScrollableResultSet, getScrollType(paramInt), getUpdateConcurrency(paramInt));
    case 5:
      return new SensitiveScrollableResultSet(paramScrollRsetStatement, (OracleResultSetImpl)paramOracleResultSet, getScrollType(paramInt), getUpdateConcurrency(paramInt));
    case 6:
      SensitiveScrollableResultSet localSensitiveScrollableResultSet = new SensitiveScrollableResultSet(paramScrollRsetStatement, (OracleResultSetImpl)paramOracleResultSet, getScrollType(paramInt), getUpdateConcurrency(paramInt));

      return new UpdatableResultSet(paramScrollRsetStatement, localSensitiveScrollableResultSet, getScrollType(paramInt), getUpdateConcurrency(paramInt));
    }

    SQLException localSQLException = DatabaseError.createSqlException(null, 23, null);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  static int getScrollType(int paramInt)
  {
    return allRsetTypes[paramInt][0];
  }

  static int getUpdateConcurrency(int paramInt)
  {
    return allRsetTypes[paramInt][1];
  }

  static int getRsetTypeCode(int paramInt1, int paramInt2)
    throws SQLException
  {
    for (int i = 0; i < allRsetTypes.length; i++)
    {
      if ((allRsetTypes[i][0] == paramInt1) && (allRsetTypes[i][1] == paramInt2))
      {
        return i;
      }

    }

    SQLException localSQLException = DatabaseError.createSqlException(null, 68);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  static boolean needIdentifier(int paramInt)
    throws SQLException
  {
    return (paramInt != 1) && (paramInt != 3);
  }

  static boolean needIdentifier(int paramInt1, int paramInt2)
    throws SQLException
  {
    return needIdentifier(getRsetTypeCode(paramInt1, paramInt2));
  }

  static boolean needCache(int paramInt)
    throws SQLException
  {
    return paramInt >= 3;
  }

  static boolean needCache(int paramInt1, int paramInt2)
    throws SQLException
  {
    return needCache(getRsetTypeCode(paramInt1, paramInt2));
  }

  static boolean supportRefreshRow(int paramInt)
    throws SQLException
  {
    return paramInt >= 4;
  }

  static boolean supportRefreshRow(int paramInt1, int paramInt2)
    throws SQLException
  {
    return supportRefreshRow(getRsetTypeCode(paramInt1, paramInt2));
  }

  protected OracleConnection getConnectionDuringExceptionHandling()
  {
    return null;
  }
}