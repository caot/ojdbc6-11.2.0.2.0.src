package oracle.jdbc.driver;

import java.io.IOException;
import java.sql.SQLException;

class T4CPlsqlIndexTableAccessor extends PlsqlIndexTableAccessor
{
  T4CMAREngine mare;
  final int[] meta = new int[1];
  final int[] tmp = new int[1];

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  T4CPlsqlIndexTableAccessor(OracleStatement paramOracleStatement, int paramInt1, int paramInt2, int paramInt3, int paramInt4, short paramShort, boolean paramBoolean, T4CMAREngine paramT4CMAREngine)
    throws SQLException
  {
    super(paramOracleStatement, paramInt1, paramInt2, paramInt3, paramInt4, paramShort, paramBoolean);

    calculateSizeTmpByteArray();

    this.mare = paramT4CMAREngine;
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

    if (this.rowSpaceIndicator == null)
    {
      byte[] arrayOfByte1 = new byte[16000];

      this.mare.unmarshalCLR(arrayOfByte1, 0, this.meta);
      processIndicator(this.meta[0]);

      this.lastRowProcessed += 1;

      return false;
    }

    int i = this.indicatorIndex + this.lastRowProcessed;
    int j = this.lengthIndex + this.lastRowProcessed;
    byte[] arrayOfByte2 = this.statement.ibtBindBytes;
    char[] arrayOfChar = this.statement.ibtBindChars;
    short[] arrayOfShort = this.statement.ibtBindIndicators;

    if (this.isNullByDescribe)
    {
      this.rowSpaceIndicator[i] = -1;
      this.rowSpaceIndicator[j] = 0;
      this.lastRowProcessed += 1;

      if (this.statement.connection.versionNumber < 9200)
        processIndicator(0);
      return false;
    }

    int k = (int)this.mare.unmarshalUB4();

    arrayOfShort[(this.ibtMetaIndex + 4)] = ((short)((k & 0xFFFF0000) >> 16 & 0xFFFF));

    arrayOfShort[(this.ibtMetaIndex + 5)] = ((short)(k & 0xFFFF));
    int n;
    if ((this.elementInternalType == 9) || (this.elementInternalType == 96) || (this.elementInternalType == 1))
    {
      byte[] arrayOfByte3 = this.statement.tmpByteArray;

      for (n = 0; n < k; n++)
      {
        int i1 = this.ibtValueIndex + this.elementMaxLen * n;

        this.mare.unmarshalCLR(arrayOfByte3, 0, this.meta);

        this.tmp[0] = this.meta[0];

        int i2 = this.statement.connection.conversion.CHARBytesToJavaChars(arrayOfByte3, 0, arrayOfChar, i1 + 1, this.tmp, arrayOfChar.length - i1 - 1);

        arrayOfChar[i1] = ((char)(i2 * 2));

        processIndicator(this.meta[0]);

        if (this.meta[0] == 0)
        {
          arrayOfShort[(this.ibtIndicatorIndex + n)] = -1;
          arrayOfShort[(this.ibtLengthIndex + n)] = 0;
        }
        else
        {
          arrayOfShort[(this.ibtLengthIndex + n)] = ((short)(this.meta[0] * 2));

          arrayOfShort[(this.ibtIndicatorIndex + n)] = 0;
        }

      }

    }
    else
    {
      for (int m = 0; m < k; m++)
      {
        n = this.ibtValueIndex + this.elementMaxLen * m;

        this.mare.unmarshalCLR(arrayOfByte2, n + 1, this.meta);

        arrayOfByte2[n] = ((byte)this.meta[0]);

        processIndicator(this.meta[0]);

        if (this.meta[0] == 0)
        {
          arrayOfShort[(this.ibtIndicatorIndex + m)] = -1;
          arrayOfShort[(this.ibtLengthIndex + m)] = 0;
        }
        else
        {
          arrayOfShort[(this.ibtLengthIndex + m)] = ((short)this.meta[0]);
          arrayOfShort[(this.ibtIndicatorIndex + m)] = 0;
        }
      }
    }

    this.lastRowProcessed += 1;

    return false;
  }

  void calculateSizeTmpByteArray()
  {
    if ((this.elementInternalType == 9) || (this.elementInternalType == 96) || (this.elementInternalType == 1))
    {
      int i = this.ibtCharLength * this.statement.connection.conversion.cMaxCharSize / this.maxNumberOfElements;

      if (this.statement.sizeTmpByteArray < i)
        this.statement.sizeTmpByteArray = i;
    }
  }

  String getString(int paramInt)
    throws SQLException
  {
    String str = super.getString(paramInt);

    if ((str != null) && (this.definedColumnSize > 0) && (str.length() > this.definedColumnSize))
    {
      str = str.substring(0, this.definedColumnSize);
    }
    return str;
  }
}