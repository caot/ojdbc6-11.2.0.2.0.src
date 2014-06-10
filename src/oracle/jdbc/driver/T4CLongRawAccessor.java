package oracle.jdbc.driver;

import java.io.IOException;
import java.sql.SQLException;
import oracle.net.ns.BreakNetException;

class T4CLongRawAccessor extends LongRawAccessor
{
  T4CMAREngine mare;
  byte[][] data = (byte[][])null;
  int[] nbBytesRead = null;
  int[] bytesReadSoFar = null;

  final int[] escapeSequenceArr = new int[1];
  final boolean[] readHeaderArr = new boolean[1];
  final boolean[] readAsNonStreamArr = new boolean[1];

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  T4CLongRawAccessor(OracleStatement paramOracleStatement, int paramInt1, int paramInt2, short paramShort, int paramInt3, T4CMAREngine paramT4CMAREngine)
    throws SQLException
  {
    super(paramOracleStatement, paramInt1, paramInt2, paramShort, paramInt3);

    this.mare = paramT4CMAREngine;

    if (paramOracleStatement.connection.useFetchSizeWithLongColumn)
    {
      this.data = new byte[paramOracleStatement.rowPrefetch][];

      for (int i = 0; i < paramOracleStatement.rowPrefetch; i++) {
        this.data[i] = new byte[4080];
      }
      this.nbBytesRead = new int[paramOracleStatement.rowPrefetch];
      this.bytesReadSoFar = new int[paramOracleStatement.rowPrefetch];
    }
  }

  T4CLongRawAccessor(OracleStatement paramOracleStatement, int paramInt1, int paramInt2, boolean paramBoolean, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, short paramShort, int paramInt8, int paramInt9, T4CMAREngine paramT4CMAREngine)
    throws SQLException
  {
    super(paramOracleStatement, paramInt1, paramInt2, paramBoolean, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramShort);

    this.mare = paramT4CMAREngine;
    this.definedColumnType = paramInt8;
    this.definedColumnSize = paramInt9;

    if (paramOracleStatement.connection.useFetchSizeWithLongColumn)
    {
      this.data = new byte[paramOracleStatement.rowPrefetch][];

      for (int i = 0; i < paramOracleStatement.rowPrefetch; i++) {
        this.data[i] = new byte[4080];
      }
      this.nbBytesRead = new int[paramOracleStatement.rowPrefetch];
      this.bytesReadSoFar = new int[paramOracleStatement.rowPrefetch];
    }
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

    boolean bool = false;
    int i = this.indicatorIndex + this.lastRowProcessed;

    this.escapeSequenceArr[0] = this.mare.unmarshalUB1();
    int j;
    if (this.mare.escapeSequenceNull(this.escapeSequenceArr[0]))
    {
      this.rowSpaceIndicator[i] = -1;

      this.mare.processIndicator(false, 0);

      j = (int)this.mare.unmarshalUB4();

      bool = false;
      this.escapeSequenceArr[0] = 0;
      this.lastRowProcessed += 1;
    }
    else
    {
      this.rowSpaceIndicator[i] = 0;
      this.readHeaderArr[0] = true;
      this.readAsNonStreamArr[0] = false;

      if (this.statement.connection.useFetchSizeWithLongColumn)
      {
        j = 0;

        while (j != -1)
        {
          if (this.data[this.lastRowProcessed].length < this.nbBytesRead[this.lastRowProcessed] + 255)
          {
            byte[] arrayOfByte = new byte[this.data[this.lastRowProcessed].length * 4];

            System.arraycopy(this.data[this.lastRowProcessed], 0, arrayOfByte, 0, this.nbBytesRead[this.lastRowProcessed]);

            this.data[this.lastRowProcessed] = arrayOfByte;
          }

          j = readStreamFromWire(this.data[this.lastRowProcessed], this.nbBytesRead[this.lastRowProcessed], 255, this.escapeSequenceArr, this.readHeaderArr, this.readAsNonStreamArr, this.mare, ((T4CConnection)this.statement.connection).oer);

          if (j != -1)
          {
            this.nbBytesRead[this.lastRowProcessed] += j;
          }
        }

        this.lastRowProcessed += 1;
      }
      else
      {
        bool = true;
      }
    }

    return bool;
  }

  void fetchNextColumns()
    throws SQLException
  {
    this.statement.continueReadRow(this.columnPosition);
  }

  int readStream(byte[] paramArrayOfByte, int paramInt)
    throws SQLException, IOException
  {
    int i = this.statement.currentRow;

    if (this.statement.connection.useFetchSizeWithLongColumn)
    {
      byte[] arrayOfByte = this.data[i];
      int k = this.nbBytesRead[i];
      int m = this.bytesReadSoFar[i];

      if (m == k) {
        return -1;
      }

      int n = 0;

      if (paramInt <= k - m)
        n = paramInt;
      else {
        n = k - m;
      }
      System.arraycopy(arrayOfByte, m, paramArrayOfByte, 0, n);

      this.bytesReadSoFar[i] += n;

      return n;
    }

    int j = readStreamFromWire(paramArrayOfByte, 0, paramInt, this.escapeSequenceArr, this.readHeaderArr, this.readAsNonStreamArr, this.mare, ((T4CConnection)this.statement.connection).oer);

    return j;
  }

  protected static final int readStreamFromWire(byte[] paramArrayOfByte, int paramInt1, int paramInt2, int[] paramArrayOfInt, boolean[] paramArrayOfBoolean1, boolean[] paramArrayOfBoolean2, T4CMAREngine paramT4CMAREngine, T4CTTIoer paramT4CTTIoer)
    throws SQLException, IOException
  {
    int i = -1;
    try
    {
      if (paramArrayOfBoolean2[0] == 0)
      {
        SQLException localSQLException;
        if ((paramInt2 > 255) || (paramInt2 < 0))
        {
          localSQLException = DatabaseError.createSqlException(null, 433);
          localSQLException.fillInStackTrace();
          throw localSQLException;
        }

        if (paramArrayOfBoolean1[0] != 0)
        {
          if (paramArrayOfInt[0] == 254)
          {
            i = paramT4CMAREngine.unmarshalUB1();
          }
          else
          {
            if (paramArrayOfInt[0] == 0)
            {
              paramT4CTTIoer.connection.internalClose();

              localSQLException = DatabaseError.createSqlException(null, 401);
              localSQLException.fillInStackTrace();
              throw localSQLException;
            }

            paramArrayOfBoolean2[0] = true;
            i = paramArrayOfInt[0];
          }

          paramArrayOfBoolean1[0] = false;
          paramArrayOfInt[0] = 0;
        }
        else
        {
          i = paramT4CMAREngine.unmarshalUB1();
        }

      }
      else
      {
        paramArrayOfBoolean2[0] = false;
      }

      if (i > 0)
      {
        paramT4CMAREngine.unmarshalNBytes(paramArrayOfByte, paramInt1, i);
      }
      else
      {
        i = -1;
      }

    }
    catch (BreakNetException localBreakNetException)
    {
      i = paramT4CMAREngine.unmarshalSB1();
      if (i == 4)
      {
        paramT4CTTIoer.init();
        paramT4CTTIoer.processError();
      }

    }

    if (i == -1)
    {
      paramArrayOfBoolean1[0] = true;

      paramT4CMAREngine.unmarshalUB2();
      paramT4CMAREngine.unmarshalUB2();
    }

    return i;
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
}