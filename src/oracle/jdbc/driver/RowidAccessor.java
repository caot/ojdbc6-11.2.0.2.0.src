package oracle.jdbc.driver;

import java.sql.SQLException;
import java.util.Map;
import oracle.sql.Datum;
import oracle.sql.ROWID;

class RowidAccessor extends Accessor
{
  static final int maxLength = 128;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  RowidAccessor(OracleStatement paramOracleStatement, int paramInt1, short paramShort, int paramInt2, boolean paramBoolean)
    throws SQLException
  {
    init(paramOracleStatement, 104, 9, paramShort, paramBoolean);
    initForDataAccess(paramInt2, paramInt1, null);
  }

  RowidAccessor(OracleStatement paramOracleStatement, int paramInt1, boolean paramBoolean, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, short paramShort)
    throws SQLException
  {
    init(paramOracleStatement, 104, 9, paramShort, false);
    initForDescribe(104, paramInt1, paramBoolean, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramShort, null);

    initForDataAccess(0, paramInt1, null);
  }

  void initForDataAccess(int paramInt1, int paramInt2, String paramString)
    throws SQLException
  {
    if (paramInt1 != 0) {
      this.externalType = paramInt1;
    }
    this.internalTypeMaxLength = 128;

    this.byteLength = (this.internalTypeMaxLength + 2);
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

      str = new String(this.rowSpaceByte, i + 2, j);
    }

    return str;
  }

  Object getObject(int paramInt)
    throws SQLException
  {
    return getROWID(paramInt);
  }

  Datum getOracleObject(int paramInt)
    throws SQLException
  {
    return getROWID(paramInt);
  }

  ROWID getROWID(int paramInt)
    throws SQLException
  {
    byte[] arrayOfByte = getBytes(paramInt);

    return arrayOfByte == null ? null : new ROWID(arrayOfByte);
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

      System.arraycopy(this.rowSpaceByte, j + 2, arrayOfByte, 0, i);
    }

    return arrayOfByte;
  }

  Object getObject(int paramInt, Map paramMap)
    throws SQLException
  {
    return getROWID(paramInt);
  }
}