package oracle.jdbc.driver;

import java.sql.SQLException;
import java.util.Map;
import oracle.sql.Datum;

class TimestampAccessor extends DateTimeCommonAccessor
{
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  TimestampAccessor(OracleStatement paramOracleStatement, int paramInt1, short paramShort, int paramInt2, boolean paramBoolean)
    throws SQLException
  {
    init(paramOracleStatement, 180, 180, paramShort, paramBoolean);
    initForDataAccess(paramInt2, paramInt1, null);
  }

  TimestampAccessor(OracleStatement paramOracleStatement, int paramInt1, boolean paramBoolean, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, short paramShort)
    throws SQLException
  {
    init(paramOracleStatement, 180, 180, paramShort, false);
    initForDescribe(180, paramInt1, paramBoolean, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramShort, null);

    initForDataAccess(0, paramInt1, null);
  }

  void initForDataAccess(int paramInt1, int paramInt2, String paramString)
    throws SQLException
  {
    if (paramInt1 != 0) {
      this.externalType = paramInt1;
    }
    this.internalTypeMaxLength = 11;

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
      int i = this.rowSpaceIndicator[(this.lengthIndex + paramInt)];
      int j = this.columnIndex + this.byteLength * paramInt;
      int k = ((this.rowSpaceByte[(0 + j)] & 0xFF) - 100) * 100 + (this.rowSpaceByte[(1 + j)] & 0xFF) - 100;

      int m = -1;

      if (i == 11)
      {
        m = oracleNanos(j);
      }

      int n = 0;
      str = toText(k, this.rowSpaceByte[(2 + j)], this.rowSpaceByte[(3 + j)], n = this.rowSpaceByte[(4 + j)] - 1, this.rowSpaceByte[(5 + j)] - 1, this.rowSpaceByte[(6 + j)] - 1, m, n < 12, null);
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
        if (this.statement.connection.j2ee13Compliant)
        {
          localObject = getTimestamp(paramInt);
        }
        else
        {
          localObject = getTIMESTAMP(paramInt);
        }
      }
      else
      {
        switch (this.externalType)
        {
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
    return getTIMESTAMP(paramInt);
  }

  Object getObject(int paramInt, Map paramMap)
    throws SQLException
  {
    return getObject(paramInt);
  }
}