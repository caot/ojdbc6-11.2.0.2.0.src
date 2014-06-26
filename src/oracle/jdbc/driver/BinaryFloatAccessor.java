package oracle.jdbc.driver;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.Map;
import oracle.sql.BINARY_FLOAT;
import oracle.sql.Datum;
import oracle.sql.NUMBER;

class BinaryFloatAccessor extends Accessor
{
  static final int MAXLENGTH = 4;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  BinaryFloatAccessor(OracleStatement paramOracleStatement, int paramInt1, short paramShort, int paramInt2, boolean paramBoolean)
    throws SQLException
  {
    init(paramOracleStatement, 100, 100, paramShort, paramBoolean);
    initForDataAccess(paramInt2, paramInt1, null);
  }

  BinaryFloatAccessor(OracleStatement paramOracleStatement, int paramInt1, boolean paramBoolean, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, short paramShort)
    throws SQLException
  {
    init(paramOracleStatement, 100, 100, paramShort, false);
    initForDescribe(100, paramInt1, paramBoolean, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramShort, null);

    int i = paramOracleStatement.maxFieldSize;

    if ((i > 0) && ((paramInt1 == 0) || (i < paramInt1))) {
      paramInt1 = i;
    }
    initForDataAccess(0, paramInt1, null);
  }

  void init(OracleStatement paramOracleStatement, int paramInt1, int paramInt2, int paramInt3, short paramShort, int paramInt4)
    throws SQLException
  {
    init(paramOracleStatement, paramInt1, paramInt2, paramShort, false);
    initForDataAccess(paramInt4, paramInt3, null);
  }

  void init(OracleStatement paramOracleStatement, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, short paramShort)
    throws SQLException
  {
    init(paramOracleStatement, paramInt1, paramInt2, paramShort, false);
    initForDescribe(paramInt1, paramInt3, paramBoolean, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8, paramShort, null);

    int i = paramOracleStatement.maxFieldSize;

    if ((i > 0) && ((paramInt3 == 0) || (i < paramInt3))) {
      paramInt3 = i;
    }
    initForDataAccess(0, paramInt3, null);
  }

  void initForDataAccess(int paramInt1, int paramInt2, String paramString)
    throws SQLException
  {
    if (paramInt1 != 0) {
      this.externalType = paramInt1;
    }
    this.internalTypeMaxLength = 4;

    if ((paramInt2 > 0) && (paramInt2 < this.internalTypeMaxLength)) {
      this.internalTypeMaxLength = paramInt2;
    }
    this.byteLength = this.internalTypeMaxLength;
  }

  float getFloat(int paramInt)
    throws SQLException
  {
    if (this.rowSpaceIndicator == null)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 21);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] == -1) {
      return 0.0F;
    }
    int i = this.columnIndex + this.byteLength * paramInt;
    int j = this.rowSpaceByte[i];
    int k = this.rowSpaceByte[(i + 1)];
    int m = this.rowSpaceByte[(i + 2)];
    int n = this.rowSpaceByte[(i + 3)];

    if ((j & 0x80) != 0)
    {
      j &= 127;
      k &= 255;
      m &= 255;
      n &= 255;
    }
    else
    {
      j = (j ^ 0xFFFFFFFF) & 0xFF;
      k = (k ^ 0xFFFFFFFF) & 0xFF;
      m = (m ^ 0xFFFFFFFF) & 0xFF;
      n = (n ^ 0xFFFFFFFF) & 0xFF;
    }

    int i1 = j << 24 | k << 16 | m << 8 | n;

    return Float.intBitsToFloat(i1);
  }

  String getString(int paramInt)
    throws SQLException
  {
    if (this.rowSpaceIndicator == null)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 21);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1) {
      return Float.toString(getFloat(paramInt));
    }
    return null;
  }

  Object getObject(int paramInt)
    throws SQLException
  {
    if (this.rowSpaceIndicator == null)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 21);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1) {
      return new Float(getFloat(paramInt));
    }
    return null;
  }

  Object getObject(int paramInt, Map paramMap)
    throws SQLException
  {
    if (this.rowSpaceIndicator == null)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 21);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1) {
      return new Float(getFloat(paramInt));
    }
    return null;
  }

  Datum getOracleObject(int paramInt)
    throws SQLException
  {
    return getBINARY_FLOAT(paramInt);
  }

  BINARY_FLOAT getBINARY_FLOAT(int paramInt)
    throws SQLException
  {
    BINARY_FLOAT localBINARY_FLOAT = null;

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
      byte[] arrayOfByte = new byte[i];

      System.arraycopy(this.rowSpaceByte, j, arrayOfByte, 0, i);

      localBINARY_FLOAT = new BINARY_FLOAT(arrayOfByte);
    }

    return localBINARY_FLOAT;
  }

  NUMBER getNUMBER(int paramInt)
    throws SQLException
  {
    if (this.rowSpaceIndicator == null)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 21);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1) {
      return new NUMBER(getFloat(paramInt));
    }
    return null;
  }

  BigInteger getBigInteger(int paramInt)
    throws SQLException
  {
    if (this.rowSpaceIndicator == null)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 21);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1) {
      return new BigInteger(getString(paramInt));
    }
    return null;
  }

  BigDecimal getBigDecimal(int paramInt)
    throws SQLException
  {
    if (this.rowSpaceIndicator == null)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 21);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1) {
      return new BigDecimal(getString(paramInt));
    }
    return null;
  }

  byte getByte(int paramInt)
    throws SQLException
  {
    return (byte)(int)getFloat(paramInt);
  }

  short getShort(int paramInt)
    throws SQLException
  {
    return (short)(int)getFloat(paramInt);
  }

  int getInt(int paramInt)
    throws SQLException
  {
    return (int)getFloat(paramInt);
  }

  long getLong(int paramInt)
    throws SQLException
  {
    return (long)getFloat(paramInt);
  }

  double getDouble(int paramInt)
    throws SQLException
  {
    return getFloat(paramInt);
  }
}