package oracle.jdbc.driver;

import java.sql.SQLException;
import java.util.Map;
import oracle.sql.Datum;
import oracle.sql.INTERVALYM;

class IntervalymAccessor extends Accessor
{
  static final int maxLength = 5;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  IntervalymAccessor(OracleStatement paramOracleStatement, int paramInt1, short paramShort, int paramInt2, boolean paramBoolean)
    throws SQLException
  {
    init(paramOracleStatement, 182, 182, paramShort, paramBoolean);
    initForDataAccess(paramInt2, paramInt1, null);
  }

  IntervalymAccessor(OracleStatement paramOracleStatement, int paramInt1, boolean paramBoolean, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, short paramShort)
    throws SQLException
  {
    init(paramOracleStatement, 182, 182, paramShort, false);
    initForDescribe(182, paramInt1, paramBoolean, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramShort, null);

    initForDataAccess(0, paramInt1, null);
  }

  void initForDataAccess(int paramInt1, int paramInt2, String paramString)
    throws SQLException
  {
    if (paramInt1 != 0) {
      this.externalType = paramInt1;
    }
    this.internalTypeMaxLength = 5;

    if ((paramInt2 > 0) && (paramInt2 < this.internalTypeMaxLength)) {
      this.internalTypeMaxLength = paramInt2;
    }
    this.byteLength = this.internalTypeMaxLength;
  }

  String getString(int paramInt)
    throws SQLException
  {
    String str = null;

    if (this.rowSpaceIndicator == null)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 21);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1)
    {
      int i = this.columnIndex + this.byteLength * paramInt;
      int j = this.rowSpaceIndicator[(this.lengthIndex + paramInt)];

      byte[] arrayOfByte = new byte[j];
      System.arraycopy(this.rowSpaceByte, i, arrayOfByte, 0, j);

      str = new INTERVALYM(arrayOfByte).toString();
    }

    return str;
  }

  Object getObject(int paramInt)
    throws SQLException
  {
    return getINTERVALYM(paramInt);
  }

  Datum getOracleObject(int paramInt)
    throws SQLException
  {
    return getINTERVALYM(paramInt);
  }

  Object getObject(int paramInt, Map paramMap)
    throws SQLException
  {
    return getINTERVALYM(paramInt);
  }

  INTERVALYM getINTERVALYM(int paramInt)
    throws SQLException
  {
    INTERVALYM localINTERVALYM = null;

    if (this.rowSpaceIndicator == null)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 21);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1)
    {
      int i = this.columnIndex + this.byteLength * paramInt;
      int j = this.rowSpaceIndicator[(this.lengthIndex + paramInt)];

      byte[] arrayOfByte = new byte[j];

      System.arraycopy(this.rowSpaceByte, i, arrayOfByte, 0, j);

      localINTERVALYM = new INTERVALYM(arrayOfByte);
    }

    return localINTERVALYM;
  }
}