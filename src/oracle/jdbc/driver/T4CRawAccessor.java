package oracle.jdbc.driver;

import java.io.IOException;
import java.sql.SQLException;

class T4CRawAccessor extends RawAccessor
{
  T4CMAREngine mare;
  boolean underlyingLongRaw = false;

  final int[] meta = new int[1];
  final int[] escapeSequenceArr = new int[1];
  final boolean[] readHeaderArr = new boolean[1];
  final boolean[] readAsNonStreamArr = new boolean[1];

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  T4CRawAccessor(OracleStatement paramOracleStatement, int paramInt1, short paramShort, int paramInt2, boolean paramBoolean, T4CMAREngine paramT4CMAREngine)
    throws SQLException
  {
    super(paramOracleStatement, paramInt1, paramShort, paramInt2, paramBoolean);

    this.mare = paramT4CMAREngine;
  }

  T4CRawAccessor(OracleStatement paramOracleStatement, int paramInt1, boolean paramBoolean, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, short paramShort, int paramInt7, int paramInt8, T4CMAREngine paramT4CMAREngine)
    throws SQLException
  {
    super(paramOracleStatement, paramInt1 == -1 ? paramInt8 : paramInt1, paramBoolean, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramShort);

    this.mare = paramT4CMAREngine;
    this.definedColumnType = paramInt7;
    this.definedColumnSize = paramInt8;

    if (paramInt1 == -1)
      this.underlyingLongRaw = true;
  }

  void processIndicator(int paramInt)
    throws IOException, SQLException
  {
    if (((this.internalType == 1) && (this.describeType == 112)) || ((this.internalType == 23) && (this.describeType == 113)))
    {
      this.mare.unmarshalUB2();
      this.mare.unmarshalUB2();
    }
    else if (this.statement.connection.versionNumber < 9200)
    {
      this.mare.unmarshalSB2();

      if ((this.statement.sqlKind != 32) && (this.statement.sqlKind != 64))
      {
        this.mare.unmarshalSB2();
      }
    } else if ((this.statement.sqlKind == 32) || (this.statement.sqlKind == 64) || (this.isDMLReturnedParam))
    {
      this.mare.processIndicator(paramInt <= 0, paramInt);
    }
  }

  boolean unmarshalOneRow()
    throws SQLException, IOException
  {
    if (this.isUseLess)
    {
      this.lastRowProcessed += 1;

      return false;
    }

    int i = this.indicatorIndex + this.lastRowProcessed;
    int j = this.lengthIndex + this.lastRowProcessed;
    int k = this.columnIndex + this.lastRowProcessed * this.byteLength;

    if (!this.underlyingLongRaw)
    {
      if (this.rowSpaceIndicator == null)
      {
        byte[] arrayOfByte1 = new byte[16000];

        this.mare.unmarshalCLR(arrayOfByte1, 0, this.meta);
        processIndicator(this.meta[0]);

        this.lastRowProcessed += 1;

        return false;
      }

      if (this.isNullByDescribe)
      {
        this.rowSpaceIndicator[i] = -1;
        this.rowSpaceIndicator[j] = 0;
        this.lastRowProcessed += 1;

        if (this.statement.connection.versionNumber < 9200) {
          processIndicator(0);
        }
        return false;
      }

      this.mare.unmarshalCLR(this.rowSpaceByte, k + 2, this.meta, this.byteLength - 2);
    }
    else
    {
      this.escapeSequenceArr[0] = this.mare.unmarshalUB1();
      int m;
      if (this.mare.escapeSequenceNull(this.escapeSequenceArr[0]))
      {
        this.meta[0] = 0;

        this.mare.processIndicator(false, 0);

        m = (int)this.mare.unmarshalUB4();
      }
      else
      {
        m = 0;
        int n = 0;
        byte[] arrayOfByte2 = this.rowSpaceByte;
        int i1 = k + 2;

        this.readHeaderArr[0] = true;
        this.readAsNonStreamArr[0] = false;

        while (m != -1)
        {
          if ((arrayOfByte2 == this.rowSpaceByte) && (n + 255 > this.byteLength - 2))
          {
            arrayOfByte2 = new byte[255];
          }
          if (arrayOfByte2 != this.rowSpaceByte) {
            i1 = 0;
          }
          m = T4CLongRawAccessor.readStreamFromWire(arrayOfByte2, i1, 255, this.escapeSequenceArr, this.readHeaderArr, this.readAsNonStreamArr, this.mare, ((T4CConnection)this.statement.connection).oer);

          if (m != -1)
          {
            if (arrayOfByte2 == this.rowSpaceByte)
            {
              n += m;
              i1 += m;
            }
            else if (this.byteLength - 2 - n > 0)
            {
              int i2 = this.byteLength - 2 - n;

              System.arraycopy(arrayOfByte2, 0, this.rowSpaceByte, k, i2);

              n += i2;
            }
          }
        }

        if (arrayOfByte2 != this.rowSpaceByte) {
          arrayOfByte2 = null;
        }
        this.meta[0] = n;
      }

    }

    this.rowSpaceByte[k] = ((byte)((this.meta[0] & 0xFF00) >> 8));
    this.rowSpaceByte[(k + 1)] = ((byte)(this.meta[0] & 0xFF));

    if (!this.underlyingLongRaw) {
      processIndicator(this.meta[0]);
    }
    if (this.meta[0] == 0)
    {
      this.rowSpaceIndicator[i] = -1;
      this.rowSpaceIndicator[j] = 0;
    }
    else
    {
      this.rowSpaceIndicator[j] = ((short)this.meta[0]);

      this.rowSpaceIndicator[i] = 0;
    }

    this.lastRowProcessed += 1;

    return false;
  }

  void copyRow()
    throws SQLException, IOException
  {
    int i;
    if (this.lastRowProcessed == 0)
      i = this.statement.rowPrefetchInLastFetch - 1;
    else {
      i = this.lastRowProcessed - 1;
    }

    int j = this.columnIndex + this.lastRowProcessed * this.byteLength;
    int k = this.columnIndex + i * this.byteLength;
    int m = this.indicatorIndex + this.lastRowProcessed;
    int n = this.indicatorIndex + i;
    int i1 = this.lengthIndex + this.lastRowProcessed;
    int i2 = this.lengthIndex + i;
    int i3 = this.rowSpaceIndicator[i2];
    int i4 = this.metaDataIndex + this.lastRowProcessed * 1;

    int i5 = this.metaDataIndex + i * 1;

    this.rowSpaceIndicator[i1] = ((short)i3);
    this.rowSpaceIndicator[m] = this.rowSpaceIndicator[n];

    if (!this.isNullByDescribe)
    {
      System.arraycopy(this.rowSpaceByte, k, this.rowSpaceByte, j, i3 + 2);
    }

    System.arraycopy(this.rowSpaceMetaData, i5, this.rowSpaceMetaData, i4, 1);

    this.lastRowProcessed += 1;
  }

  void saveDataFromOldDefineBuffers(byte[] paramArrayOfByte, char[] paramArrayOfChar, short[] paramArrayOfShort, int paramInt1, int paramInt2)
    throws SQLException
  {
    int i = this.columnIndex + (paramInt2 - 1) * this.byteLength;

    int j = this.columnIndexLastRow + (paramInt1 - 1) * this.byteLength;

    int k = this.indicatorIndex + paramInt2 - 1;
    int m = this.indicatorIndexLastRow + paramInt1 - 1;
    int n = this.lengthIndex + paramInt2 - 1;
    int i1 = this.lengthIndexLastRow + paramInt1 - 1;
    int i2 = paramArrayOfShort[i1];

    this.rowSpaceIndicator[n] = ((short)i2);
    this.rowSpaceIndicator[k] = paramArrayOfShort[m];

    if (i2 != 0)
    {
      System.arraycopy(paramArrayOfByte, j, this.rowSpaceByte, i, i2 + 2);
    }
  }

  String getString(int paramInt)
    throws SQLException
  {
    String str = super.getString(paramInt);

    if ((str != null) && (this.definedColumnSize > 0) && (str.length() > this.definedColumnSize * 2))
    {
      str = str.substring(0, this.definedColumnSize * 2);
    }
    return str;
  }

  Object getObject(int paramInt)
    throws SQLException
  {
    if (this.definedColumnType == 0) {
      return super.getObject(paramInt);
    }

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
      switch (this.definedColumnType)
      {
      case -1:
      case 1:
      case 12:
        return getString(paramInt);
      case -2:
        return getRAW(paramInt);
      }

      localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    return localObject;
  }

  byte[] getBytes(int paramInt)
    throws SQLException
  {
    byte[] localObject = super.getBytes(paramInt);

    if ((localObject != null) && (this.definedColumnSize > 0) && (this.definedColumnSize < localObject.length))
    {
      byte[] arrayOfByte = new byte[this.definedColumnSize];

      System.arraycopy(localObject, 0, arrayOfByte, 0, this.definedColumnSize);

      localObject = arrayOfByte;
    }

    return localObject;
  }
}