package oracle.jdbc.driver;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.sql.SQLException;

class LongRawAccessor extends RawCommonAccessor
{
  OracleInputStream stream;
  int columnPosition = 0;

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  LongRawAccessor(OracleStatement paramOracleStatement, int paramInt1, int paramInt2, short paramShort, int paramInt3)
    throws SQLException
  {
    init(paramOracleStatement, 24, 24, paramShort, false);

    this.columnPosition = paramInt1;

    initForDataAccess(paramInt3, paramInt2, null);
  }

  LongRawAccessor(OracleStatement paramOracleStatement, int paramInt1, int paramInt2, boolean paramBoolean, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, short paramShort)
    throws SQLException
  {
    init(paramOracleStatement, 24, 24, paramShort, false);

    this.columnPosition = paramInt1;

    initForDescribe(24, paramInt2, paramBoolean, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramShort, null);

    int i = paramOracleStatement.maxFieldSize;

    if ((i > 0) && ((paramInt2 == 0) || (i < paramInt2))) {
      paramInt2 = i;
    }
    initForDataAccess(0, paramInt2, null);
  }

  void initForDataAccess(int paramInt1, int paramInt2, String paramString)
    throws SQLException
  {
    if (paramInt1 != 0) {
      this.externalType = paramInt1;
    }
    this.isStream = true;
    this.isColumnNumberAware = true;
    this.internalTypeMaxLength = 2147483647;

    if ((paramInt2 > 0) && (paramInt2 < this.internalTypeMaxLength)) {
      this.internalTypeMaxLength = paramInt2;
    }
    this.byteLength = 0;

    this.stream = this.statement.connection.driverExtension.createInputStream(this.statement, this.columnPosition, this);
  }

  OracleInputStream initForNewRow()
    throws SQLException
  {
    this.stream = this.statement.connection.driverExtension.createInputStream(this.statement, this.columnPosition, this);

    return this.stream;
  }

  void updateColumnNumber(int paramInt)
  {
    paramInt++;

    this.columnPosition = paramInt;

    if (this.stream != null)
      this.stream.columnIndex = paramInt;
  }

  byte[] getBytes(int paramInt)
    throws SQLException
  {
    byte[] arrayOfByte1 = null;
    SQLException localObject;
    if (this.rowSpaceIndicator == null)
    {
      localObject = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 21);
      localObject.fillInStackTrace();
      throw localObject;
    }

    if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1)
    {
      if (this.stream != null)
      {
        if (this.stream.closed)
        {
          localObject = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 27);
          localObject.fillInStackTrace();
          throw localObject;
        }

        ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream(1024);
        byte[] arrayOfByte2 = new byte[1024];
        try
        {
          int i;
          while ((i = this.stream.read(arrayOfByte2)) != -1)
          {
            bytearrayoutputstream.write(arrayOfByte2, 0, i);
          }

        }
        catch (IOException localIOException)
        {
          SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localIOException);
          localSQLException.fillInStackTrace();
          throw localSQLException;
        }

        arrayOfByte1 = bytearrayoutputstream.toByteArray();
      }

    }

    return arrayOfByte1;
  }

  InputStream getAsciiStream(int paramInt)
    throws SQLException
  {
    InputStream localInputStream = null;
    SQLException localObject;
    if (this.rowSpaceIndicator == null)
    {
      localObject = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 21);
      localObject.fillInStackTrace();
      throw localObject;
    }

    if ((this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1) && (this.stream != null))
    {
      if (this.stream.closed)
      {
        localObject = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 27);
        localObject.fillInStackTrace();
        throw localObject;
      }

      PhysicalConnection physicalconnection = this.statement.connection;

      localInputStream = physicalconnection.conversion.ConvertStream(this.stream, 2);
    }

    return localInputStream;
  }

  InputStream getUnicodeStream(int paramInt)
    throws SQLException
  {
    InputStream localInputStream = null;
    SQLException localObject;
    if (this.rowSpaceIndicator == null)
    {
      localObject = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 21);
      localObject.fillInStackTrace();
      throw localObject;
    }

    if ((this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1) && (this.stream != null))
    {
      if (this.stream.closed)
      {
        localObject = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 27);
        localObject.fillInStackTrace();
        throw localObject;
      }

      PhysicalConnection physicalconnection = this.statement.connection;

      localInputStream = physicalconnection.conversion.ConvertStream(this.stream, 3);
    }

    return localInputStream;
  }

  Reader getCharacterStream(int paramInt)
    throws SQLException
  {
    Reader localReader = null;
    SQLException localObject;
    if (this.rowSpaceIndicator == null)
    {
      localObject = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 21);
      localObject.fillInStackTrace();
      throw localObject;
    }

    if ((this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1) && (this.stream != null))
    {
      if (this.stream.closed)
      {
        localObject = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 27);
        localObject.fillInStackTrace();
        throw localObject;
      }

      PhysicalConnection physicalconnection = this.statement.connection;

      localReader = physicalconnection.conversion.ConvertCharacterStream(this.stream, 8);
    }

    return localReader;
  }

  InputStream getBinaryStream(int paramInt)
    throws SQLException
  {
    InputStream localInputStream = null;
    SQLException localObject;
    if (this.rowSpaceIndicator == null)
    {
      localObject = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 21);
      localObject.fillInStackTrace();
      throw localObject;
    }

    if ((this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1) && (this.stream != null))
    {
      if (this.stream.closed)
      {
        localObject = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 27);
        localObject.fillInStackTrace();
        throw localObject;
      }

      PhysicalConnection physicalconnection = this.statement.connection;

      localInputStream = physicalconnection.conversion.ConvertStream(this.stream, 6);
    }

    return localInputStream;
  }

  public String toString()
  {
    return "LongRawAccessor@" + Integer.toHexString(hashCode()) + "{columnPosition = " + this.columnPosition + "}";
  }
}