package oracle.jdbc.driver;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.Map;
import oracle.sql.CLOB;
import oracle.sql.Datum;
import oracle.sql.NCLOB;

class ClobAccessor extends Accessor
{
  static final int maxLength = 4000;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  ClobAccessor(OracleStatement paramOracleStatement, int paramInt1, short paramShort, int paramInt2, boolean paramBoolean)
    throws SQLException
  {
    init(paramOracleStatement, 112, 112, paramShort, paramBoolean);
    initForDataAccess(paramInt2, paramInt1, null);
  }

  ClobAccessor(OracleStatement paramOracleStatement, int paramInt1, boolean paramBoolean, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, short paramShort)
    throws SQLException
  {
    init(paramOracleStatement, 112, 112, paramShort, false);
    initForDescribe(112, paramInt1, paramBoolean, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramShort, null);

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
    return getCLOB(paramInt);
  }

  Object getObject(int paramInt, Map paramMap)
    throws SQLException
  {
    return getCLOB(paramInt);
  }

  Datum getOracleObject(int paramInt)
    throws SQLException
  {
    return getCLOB(paramInt);
  }

  CLOB getCLOB(int paramInt)
    throws SQLException
  {
    Object localObject = null;

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

      if (this.formOfUse == 1) {
        localObject = new CLOB(this.statement.connection, arrayOfByte, this.formOfUse);
      }
      else {
        localObject = new NCLOB(this.statement.connection, arrayOfByte);
      }

      if ((this.lobPrefetchSizeForThisColumn != -1) && (this.prefetchedLobSize != null))
      {
        ((CLOB)localObject).setActivePrefetch(true);
        ((CLOB)localObject).setLength(this.prefetchedLobSize[paramInt]);
        ((CLOB)localObject).setChunkSize(this.prefetchedLobChunkSize[paramInt]);

        if ((this.prefetchedLobDataL != null) && (this.prefetchedLobDataL[paramInt] > 0))
        {
          initializeClobForPrefetch(paramInt, (CLOB)localObject);
        }
        else {
          ((CLOB)localObject).setPrefetchedData(null);
        }
      }
    }
    return localObject;
  }

  void initializeClobForPrefetch(int paramInt, CLOB paramCLOB)
    throws SQLException
  {
    paramCLOB.setPrefetchedData(this.prefetchedLobCharData[paramInt]);
  }

  InputStream getAsciiStream(int paramInt)
    throws SQLException
  {
    CLOB localCLOB = getCLOB(paramInt);

    if (localCLOB == null) {
      return null;
    }
    return localCLOB.getAsciiStream();
  }

  Reader getCharacterStream(int paramInt)
    throws SQLException
  {
    CLOB localCLOB = getCLOB(paramInt);

    if (localCLOB == null) {
      return null;
    }
    return localCLOB.getCharacterStream();
  }

  InputStream getBinaryStream(int paramInt)
    throws SQLException
  {
    CLOB localCLOB = getCLOB(paramInt);

    if (localCLOB == null) {
      return null;
    }
    return localCLOB.getAsciiStream();
  }

  String getString(int paramInt)
    throws SQLException
  {
    CLOB localCLOB = getCLOB(paramInt);

    if (localCLOB == null) {
      return null;
    }
    Reader localReader = localCLOB.getCharacterStream();
    int i = localCLOB.getBufferSize();
    int j = 0;
    StringWriter localStringWriter = new StringWriter(i);
    char[] arrayOfChar = new char[i];
    try
    {
      while ((j = localReader.read(arrayOfChar)) != -1)
      {
        localStringWriter.write(arrayOfChar, 0, j);
      }

    }
    catch (IOException localIOException)
    {
      localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localIOException);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
    catch (IndexOutOfBoundsException localIndexOutOfBoundsException)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 151);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if (localCLOB.isTemporary()) this.statement.addToTempLobsToFree(localCLOB);
    return localStringWriter.getBuffer().substring(0);
  }

  byte[] privateGetBytes(int paramInt)
    throws SQLException
  {
    return super.getBytes(paramInt);
  }

  byte[] getBytes(int paramInt)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createUnsupportedFeatureSqlException();
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }
}