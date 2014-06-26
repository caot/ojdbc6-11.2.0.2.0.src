package oracle.jdbc.driver;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.sql.SQLException;
import java.util.Map;
import oracle.sql.BFILE;
import oracle.sql.Datum;

class BfileAccessor extends Accessor
{
  static final int maxLength = 530;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  BfileAccessor(OracleStatement paramOracleStatement, int paramInt1, short paramShort, int paramInt2, boolean paramBoolean)
    throws SQLException
  {
    init(paramOracleStatement, 114, 114, paramShort, paramBoolean);
    initForDataAccess(paramInt2, paramInt1, null);
  }

  BfileAccessor(OracleStatement paramOracleStatement, int paramInt1, boolean paramBoolean, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, short paramShort)
    throws SQLException
  {
    init(paramOracleStatement, 114, 114, paramShort, false);
    initForDescribe(114, paramInt1, paramBoolean, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramShort, null);

    initForDataAccess(0, paramInt1, null);
  }

  void initForDataAccess(int paramInt1, int paramInt2, String paramString)
    throws SQLException
  {
    if (paramInt1 != 0) {
      this.externalType = paramInt1;
    }
    this.internalTypeMaxLength = 530;

    if ((paramInt2 > 0) && (paramInt2 < this.internalTypeMaxLength)) {
      this.internalTypeMaxLength = paramInt2;
    }
    this.byteLength = this.internalTypeMaxLength;
  }

  Object getObject(int paramInt)
    throws SQLException
  {
    return getBFILE(paramInt);
  }

  Object getObject(int paramInt, Map paramMap)
    throws SQLException
  {
    return getBFILE(paramInt);
  }

  Datum getOracleObject(int paramInt)
    throws SQLException
  {
    return getBFILE(paramInt);
  }

  BFILE getBFILE(int paramInt)
    throws SQLException
  {
    BFILE localBFILE = null;

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

      localBFILE = new BFILE(this.statement.connection, arrayOfByte);
      if (this.lobPrefetchSizeForThisColumn != -1) {
        localBFILE.setLength(this.prefetchedLobSize[paramInt]);
      }
    }
    return localBFILE;
  }

  InputStream getAsciiStream(int paramInt)
    throws SQLException
  {
    BFILE localBFILE = getBFILE(paramInt);

    if (localBFILE == null) {
      return null;
    }
    return localBFILE.asciiStreamValue();
  }

  Reader getCharacterStream(int paramInt)
    throws SQLException
  {
    BFILE localBFILE = getBFILE(paramInt);

    if (localBFILE == null) {
      return null;
    }
    return localBFILE.characterStreamValue();
  }

  InputStream getBinaryStream(int paramInt)
    throws SQLException
  {
    BFILE localBFILE = getBFILE(paramInt);

    if (localBFILE == null) {
      return null;
    }
    return localBFILE.getBinaryStream();
  }

  byte[] privateGetBytes(int paramInt)
    throws SQLException
  {
    return super.getBytes(paramInt);
  }

  byte[] getBytes(int paramInt)
    throws SQLException
  {
    BFILE localBFILE = getBFILE(paramInt);

    if (localBFILE == null) {
      return null;
    }
    InputStream localInputStream = localBFILE.getBinaryStream();
    int i = 4096;
    int j = 0;
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream(i);
    byte[] arrayOfByte = new byte[i];
    try
    {
      while ((j = localInputStream.read(arrayOfByte)) != -1)
      {
        localByteArrayOutputStream.write(arrayOfByte, 0, j);
      }

    }
    catch (IOException localIOException)
    {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localIOException);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }
    catch (IndexOutOfBoundsException localIndexOutOfBoundsException)
    {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 151);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    return localByteArrayOutputStream.toByteArray();
  }
}