package oracle.jdbc.driver;

import java.sql.SQLException;

class T2CResultSetAccessor extends ResultSetAccessor
{
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  T2CResultSetAccessor(OracleStatement paramOracleStatement, int paramInt1, short paramShort, int paramInt2, boolean paramBoolean)
    throws SQLException
  {
    super(paramOracleStatement, paramInt1 * 2, paramShort, paramInt2, paramBoolean);
  }

  T2CResultSetAccessor(OracleStatement paramOracleStatement, int paramInt1, boolean paramBoolean, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, short paramShort)
    throws SQLException
  {
    super(paramOracleStatement, paramInt1 * 2, paramBoolean, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramShort);
  }

  byte[] getBytes(int paramInt)
    throws SQLException
  {
    byte[] arrayOfByte = null;

    if (this.rowSpaceIndicator == null)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 21);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1)
    {
      int i = this.rowSpaceIndicator[(this.lengthIndex + paramInt)];
      int j = ((T2CConnection)this.statement.connection).byteAlign;
      int k = this.columnIndex + (j - 1) & (j - 1 ^ 0xFFFFFFFF);

      int m = k + i * paramInt;

      arrayOfByte = new byte[i];
      System.arraycopy(this.rowSpaceByte, m, arrayOfByte, 0, i);
    }

    return arrayOfByte;
  }
}