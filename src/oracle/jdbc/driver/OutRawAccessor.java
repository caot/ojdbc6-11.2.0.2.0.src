package oracle.jdbc.driver;

import java.sql.SQLException;

class OutRawAccessor extends RawCommonAccessor
{
  static final int MAXLENGTH_NEW = 32767;
  static final int MAXLENGTH_OLD = 32767;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  OutRawAccessor(OracleStatement paramOracleStatement, int paramInt1, short paramShort, int paramInt2)
    throws SQLException
  {
    init(paramOracleStatement, 23, 23, paramShort, true);
    initForDataAccess(paramInt2, paramInt1, null);
  }

  void initForDataAccess(int paramInt1, int paramInt2, String paramString)
    throws SQLException
  {
    if (paramInt1 != 0) {
      this.externalType = paramInt1;
    }
    if (this.statement.connection.getVersionNumber() >= 8000)
      this.internalTypeMaxLength = 32767;
    else {
      this.internalTypeMaxLength = 32767;
    }
    if ((paramInt2 > 0) && (paramInt2 < this.internalTypeMaxLength)) {
      this.internalTypeMaxLength = paramInt2;
    }
    this.byteLength = this.internalTypeMaxLength;
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
      int j = this.columnIndex + this.byteLength * paramInt;

      arrayOfByte = new byte[i];

      System.arraycopy(this.rowSpaceByte, j, arrayOfByte, 0, i);
    }

    return arrayOfByte;
  }
}