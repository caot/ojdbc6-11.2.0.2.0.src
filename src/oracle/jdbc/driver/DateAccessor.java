package oracle.jdbc.driver;

import java.sql.SQLException;
import java.util.Map;
import oracle.sql.Datum;

class DateAccessor extends DateTimeCommonAccessor
{
  static final int maxLength = 7;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  DateAccessor(OracleStatement paramOracleStatement, int paramInt1, short paramShort, int paramInt2, boolean paramBoolean)
    throws SQLException
  {
    init(paramOracleStatement, 12, 12, paramShort, paramBoolean);
    initForDataAccess(paramInt2, paramInt1, null);
  }

  DateAccessor(OracleStatement paramOracleStatement, int paramInt1, boolean paramBoolean, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, short paramShort)
    throws SQLException
  {
    init(paramOracleStatement, 12, 12, paramShort, false);
    initForDescribe(12, paramInt1, paramBoolean, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramShort, null);

    initForDataAccess(0, paramInt1, null);
  }

  void initForDataAccess(int paramInt1, int paramInt2, String paramString)
    throws SQLException
  {
    if (paramInt1 != 0) {
      this.externalType = paramInt1;
    }
    this.internalTypeMaxLength = 7;

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
      int j = ((this.rowSpaceByte[(0 + i)] & 0xFF) - 100) * 100 + (this.rowSpaceByte[(1 + i)] & 0xFF) - 100;

      int k = 0;
      str = toText(j, this.rowSpaceByte[(2 + i)], this.rowSpaceByte[(3 + i)], k = this.rowSpaceByte[(4 + i)] - 1, this.rowSpaceByte[(5 + i)] - 1, this.rowSpaceByte[(6 + i)] - 1, -1, k < 12, null);
    }

    return str;
  }

  Object getObject(int paramInt)
    throws SQLException
  {
    Object localObject = null;
    SQLException localSQLException;
    if (this.rowSpaceIndicator == null)
    {
      localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 21);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1)
    {
      if (this.externalType == 0)
      {
        if (this.statement.connection.mapDateToTimestamp)
          localObject = getTimestamp(paramInt);
        else
          localObject = getDate(paramInt);
      }
      else
      {
        switch (this.externalType)
        {
        case 91:
          return getDate(paramInt);
        case 92:
          return getTime(paramInt);
        case 93:
          return getTimestamp(paramInt);
        }

        localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4);

        localSQLException.fillInStackTrace();
        throw localSQLException;
      }

    }

    return localObject;
  }

  Datum getOracleObject(int paramInt)
    throws SQLException
  {
    return getDATE(paramInt);
  }

  Object getObject(int paramInt, Map paramMap)
    throws SQLException
  {
    return getObject(paramInt);
  }

  static String toStr(int paramInt)
  {
    return paramInt < 10 ? "0" + paramInt : Integer.toString(paramInt);
  }
}