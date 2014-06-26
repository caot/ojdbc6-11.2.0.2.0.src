package oracle.jdbc.driver;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.sql.SQLException;
import java.util.Map;
import oracle.sql.BLOB;
import oracle.sql.Datum;

class BlobAccessor extends Accessor
{
  static final int maxLength = 4000;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  BlobAccessor(OracleStatement paramOracleStatement, int paramInt1, short paramShort, int paramInt2, boolean paramBoolean)
    throws SQLException
  {
    init(paramOracleStatement, 113, 113, paramShort, paramBoolean);
    initForDataAccess(paramInt2, paramInt1, null);
  }

  BlobAccessor(OracleStatement paramOracleStatement, int paramInt1, boolean paramBoolean, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, short paramShort)
    throws SQLException
  {
    init(paramOracleStatement, 113, 113, paramShort, false);
    initForDescribe(113, paramInt1, paramBoolean, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramShort, null);

    initForDataAccess(0, paramInt1, null);
  }

  void initForDataAccess(int paramInt1, int paramInt2, String paramString)
    throws SQLException
  {
    if (paramInt1 != 0) {
      this.externalType = paramInt1;
    }
    this.internalTypeMaxLength = 4000;

    if ((paramInt2 > 0) && (paramInt2 < this.internalTypeMaxLength)) {
      this.internalTypeMaxLength = paramInt2;
    }
    this.byteLength = this.internalTypeMaxLength;
  }

  Object getObject(int paramInt)
    throws SQLException
  {
    return getBLOB(paramInt);
  }

  Object getObject(int paramInt, Map paramMap)
    throws SQLException
  {
    return getBLOB(paramInt);
  }

  Datum getOracleObject(int paramInt)
    throws SQLException
  {
    return getBLOB(paramInt);
  }

  BLOB getBLOB(int paramInt)
    throws SQLException
  {
    BLOB localBLOB = null;

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

      localBLOB = new BLOB(this.statement.connection, arrayOfByte);

      if ((this.lobPrefetchSizeForThisColumn != -1) && (this.prefetchedLobSize != null))
      {
        localBLOB.setActivePrefetch(true);
        localBLOB.setLength(this.prefetchedLobSize[paramInt]);
        localBLOB.setChunkSize(this.prefetchedLobChunkSize[paramInt]);

        if ((this.prefetchedLobDataL != null) && (this.prefetchedLobDataL[paramInt] > 0))
        {
          initializeBlobForPrefetch(paramInt, localBLOB);
        }
        else {
          localBLOB.setPrefetchedData(null);
        }
      }
    }
    return localBLOB;
  }

  void initializeBlobForPrefetch(int paramInt, BLOB paramBLOB)
    throws SQLException
  {
    int i = this.prefetchedLobDataL[paramInt];
    byte[] arrayOfByte1 = this.prefetchedLobData[paramInt];
    byte[] arrayOfByte2 = new byte[i];
    System.arraycopy(arrayOfByte1, 0, arrayOfByte2, 0, i);
    paramBLOB.setPrefetchedData(arrayOfByte2);
  }

  InputStream getAsciiStream(int paramInt)
    throws SQLException
  {
    BLOB localBLOB = getBLOB(paramInt);

    if (localBLOB == null) {
      return null;
    }
    return localBLOB.asciiStreamValue();
  }

  Reader getCharacterStream(int paramInt)
    throws SQLException
  {
    BLOB localBLOB = getBLOB(paramInt);

    if (localBLOB == null) {
      return null;
    }
    return localBLOB.characterStreamValue();
  }

  InputStream getBinaryStream(int paramInt)
    throws SQLException
  {
    BLOB localBLOB = getBLOB(paramInt);

    if (localBLOB == null) {
      return null;
    }
    return localBLOB.getBinaryStream();
  }

  byte[] privateGetBytes(int paramInt)
    throws SQLException
  {
    return super.getBytes(paramInt);
  }

  byte[] getBytes(int paramInt)
    throws SQLException
  {
    BLOB localBLOB = getBLOB(paramInt);

    if (localBLOB == null) {
      return null;
    }
    InputStream localInputStream = localBLOB.getBinaryStream();
    int i = localBLOB.getBufferSize();
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
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localIOException);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
    catch (IndexOutOfBoundsException localIndexOutOfBoundsException)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 151);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if (localBLOB.isTemporary()) this.statement.addToTempLobsToFree(localBLOB);
    return localByteArrayOutputStream.toByteArray();
  }

  String getString(int paramInt)
    throws SQLException
  {
    unimpl("getString");
    return null;
  }

  String getNString(int paramInt)
    throws SQLException
  {
    unimpl("getNString");
    return null;
  }
}