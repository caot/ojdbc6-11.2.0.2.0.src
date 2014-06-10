package oracle.jdbc.driver;

import java.io.ByteArrayInputStream;
import java.io.CharArrayReader;
import java.io.InputStream;
import java.io.Reader;
import java.sql.SQLException;
import java.util.Map;
import oracle.jdbc.util.RepConversion;
import oracle.sql.Datum;
import oracle.sql.RAW;

class RawCommonAccessor extends Accessor
{
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

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
    this.internalTypeMaxLength = 2147483647;

    if ((paramInt2 > 0) && (paramInt2 < this.internalTypeMaxLength))
      this.internalTypeMaxLength = paramInt2;
  }

  String getString(int paramInt)
    throws SQLException
  {
    byte[] arrayOfByte = getBytes(paramInt);

    if (arrayOfByte == null) {
      return null;
    }
    int i = arrayOfByte.length;

    if (i == 0) {
      return null;
    }
    return RepConversion.bArray2String(arrayOfByte);
  }

  InputStream getAsciiStream(int paramInt)
    throws SQLException
  {
    byte[] arrayOfByte = getBytes(paramInt);

    if (arrayOfByte == null) {
      return null;
    }
    PhysicalConnection localPhysicalConnection = this.statement.connection;

    return localPhysicalConnection.conversion.ConvertStream(new ByteArrayInputStream(arrayOfByte), 2);
  }

  InputStream getUnicodeStream(int paramInt)
    throws SQLException
  {
    byte[] arrayOfByte = getBytes(paramInt);

    if (arrayOfByte == null) {
      return null;
    }
    PhysicalConnection localPhysicalConnection = this.statement.connection;

    return localPhysicalConnection.conversion.ConvertStream(new ByteArrayInputStream(arrayOfByte), 3);
  }

  Reader getCharacterStream(int paramInt)
    throws SQLException
  {
    byte[] arrayOfByte = getBytes(paramInt);

    if (arrayOfByte == null) {
      return null;
    }
    int i = arrayOfByte.length;

    char[] arrayOfChar = new char[i << 1];

    int j = DBConversion.RAWBytesToHexChars(arrayOfByte, i, arrayOfChar);

    return new CharArrayReader(arrayOfChar, 0, j);
  }

  InputStream getBinaryStream(int paramInt)
    throws SQLException
  {
    byte[] arrayOfByte = getBytes(paramInt);

    if (arrayOfByte == null) {
      return null;
    }
    return new ByteArrayInputStream(arrayOfByte);
  }

  Object getObject(int paramInt)
    throws SQLException
  {
    return getBytes(paramInt);
  }

  Object getObject(int paramInt, Map paramMap)
    throws SQLException
  {
    return getBytes(paramInt);
  }

  Datum getOracleObject(int paramInt)
    throws SQLException
  {
    return getRAW(paramInt);
  }

  RAW getRAW(int paramInt)
    throws SQLException
  {
    byte[] arrayOfByte = getBytes(paramInt);

    if (arrayOfByte == null) {
      return null;
    }
    return new RAW(arrayOfByte);
  }
}