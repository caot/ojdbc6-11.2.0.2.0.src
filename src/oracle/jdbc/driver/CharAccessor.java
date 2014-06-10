package oracle.jdbc.driver;

import java.sql.SQLException;

class CharAccessor extends CharCommonAccessor
{
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  CharAccessor(OracleStatement paramOracleStatement, int paramInt1, short paramShort, int paramInt2, boolean paramBoolean)
    throws SQLException
  {
    int i = 2000;

    if (paramInt1 > i) {
      i = paramInt1;
    }
    if (paramOracleStatement.sqlKind == 32) {
      i = 32766;
    }
    init(paramOracleStatement, 96, 9, paramInt1, paramShort, paramInt2, paramBoolean, i, 255);
  }

  CharAccessor(OracleStatement paramOracleStatement, int paramInt1, boolean paramBoolean, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, short paramShort)
    throws SQLException
  {
    int i = 2000;

    if (paramInt1 > i) {
      i = paramInt1;
    }
    if (paramOracleStatement.sqlKind == 32) {
      i = 32766;
    }
    init(paramOracleStatement, 96, 9, paramInt1, paramBoolean, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramShort, i, 255);
  }
}