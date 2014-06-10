package oracle.jdbc.driver;

import java.io.IOException;
import java.sql.SQLException;

class T4CRowidAccessor extends RowidAccessor
{
  T4CMAREngine mare;
  static final int maxLength = 128;
  static final int EXTENDED_ROWID_MAX_LENGTH = 18;
  final int[] meta = new int[1];
  static final int KGRD_EXTENDED_OBJECT = 6;
  static final int KGRD_EXTENDED_BLOCK = 6;
  static final int KGRD_EXTENDED_FILE = 3;
  static final int KGRD_EXTENDED_SLOT = 3;
  static final int kd4_ubridtype_physical = 1;
  static final int kd4_ubridtype_logical = 2;
  static final int kd4_ubridtype_remote = 3;
  static final int kd4_ubridtype_exttab = 4;
  static final int kd4_ubridtype_future2 = 5;
  static final int kd4_ubridtype_max = 5;
  static final int kd4_ubridlen_typeind = 1;
  static final byte[] kgrd_indbyte_char = { 65, 42, 45, 40, 41 };

  static final byte[] kgrd_basis_64 = { 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47 };

  static final byte[] kgrd_index_64 = { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1 };

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  T4CRowidAccessor(OracleStatement paramOracleStatement, int paramInt1, short paramShort, int paramInt2, boolean paramBoolean, T4CMAREngine paramT4CMAREngine)
    throws SQLException
  {
    super(paramOracleStatement, paramInt1, paramShort, paramInt2, paramBoolean);

    this.mare = paramT4CMAREngine;
    this.defineType = 104;
  }

  T4CRowidAccessor(OracleStatement paramOracleStatement, int paramInt1, boolean paramBoolean, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, short paramShort, int paramInt7, int paramInt8, T4CMAREngine paramT4CMAREngine)
    throws SQLException
  {
    super(paramOracleStatement, paramInt1, paramBoolean, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramShort);

    this.mare = paramT4CMAREngine;
    this.definedColumnType = paramInt7;
    this.definedColumnSize = paramInt8;
    this.defineType = 104;
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
    int m;
    int i2;
    if (this.rowSpaceIndicator == null)
    {
      i = this.mare.unmarshalUB1();
      long l1 = 0L;
      m = 0;
      int n = 0;
      long l3 = 0L;
      i2 = 0;

      if (i > 0)
      {
        l1 = this.mare.unmarshalUB4();
        m = this.mare.unmarshalUB2();
        n = this.mare.unmarshalUB1();
        l3 = this.mare.unmarshalUB4();
        i2 = this.mare.unmarshalUB2();
      }

      processIndicator(this.meta[0]);

      this.lastRowProcessed += 1;

      return false;
    }

    int i = this.indicatorIndex + this.lastRowProcessed;
    int j = this.lengthIndex + this.lastRowProcessed;

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

    int k = this.columnIndex + this.lastRowProcessed * this.byteLength;

    if (this.describeType != 208)
    {
      m = this.mare.unmarshalUB1();
      long l2 = 0L;
      int i1 = 0;
      i2 = 0;
      long l4 = 0L;
      int i3 = 0;

      if (m > 0)
      {
        l2 = this.mare.unmarshalUB4();
        i1 = this.mare.unmarshalUB2();
        i2 = this.mare.unmarshalUB1();
        l4 = this.mare.unmarshalUB4();
        i3 = this.mare.unmarshalUB2();
      }

      if ((l2 == 0L) && (i1 == 0) && (i2 == 0) && (l4 == 0L) && (i3 == 0))
      {
        this.meta[0] = 0;
      }
      else {
        long[] arrayOfLong = { l2, i1, l4, i3 };

        byte[] arrayOfByte2 = rowidToString(arrayOfLong);
        int i4 = 18;

        if (this.byteLength - 2 < 18) {
          i4 = this.byteLength - 2;
        }
        System.arraycopy(arrayOfByte2, 0, this.rowSpaceByte, k + 2, i4);

        this.meta[0] = i4;
      }

    }
    else if ((this.meta[0] = (int)this.mare.unmarshalUB4()) > 0)
    {
      byte[] arrayOfByte1 = new byte[this.meta[0]];
      this.mare.unmarshalCLR(arrayOfByte1, 0, this.meta);
      this.meta[0] = kgrdub2c(arrayOfByte1, this.meta[0], 0, this.rowSpaceByte, k + 2);
    }

    this.rowSpaceByte[k] = ((byte)((this.meta[0] & 0xFF00) >> 8));
    this.rowSpaceByte[(k + 1)] = ((byte)(this.meta[0] & 0xFF));

    processIndicator(this.meta[0]);

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

      if ((this.describeType != 208) || (this.rowSpaceByte[i] == 1))
      {
        str = new String(this.rowSpaceByte, i + 2, j);

        long[] arrayOfLong = stringToRowid(str.getBytes(), 0, str.length());

        str = new String(rowidToString(arrayOfLong));
      }
      else
      {
        str = new String(this.rowSpaceByte, i + 2, j);
      }
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
      case -8:
        return getROWID(paramInt);
      }

      localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    return localObject;
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

    System.arraycopy(this.rowSpaceByte, k, this.rowSpaceByte, j, i3 + 2);

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

  static final byte[] rowidToString(long[] paramArrayOfLong)
  {
    long l1 = paramArrayOfLong[0];

    long l2 = paramArrayOfLong[1];

    long l3 = paramArrayOfLong[2];

    long l4 = paramArrayOfLong[3];

    int i = 18;

    byte[] arrayOfByte = new byte[i];
    int j = 0;

    j = kgrd42b(arrayOfByte, l1, 6, j);

    j = kgrd42b(arrayOfByte, l2, 3, j);

    j = kgrd42b(arrayOfByte, l3, 6, j);

    j = kgrd42b(arrayOfByte, l4, 3, j);

    return arrayOfByte;
  }

  static final long[] rcToRowid(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws SQLException
  {
    int i = 18;

    if (paramInt2 != i)
    {
      throw new SQLException("Rowid size incorrect.");
    }

    long[] arrayOfLong = new long[3];
    String str = new String(paramArrayOfByte, paramInt1, paramInt2);

    long l1 = Long.parseLong(str.substring(0, 8), 16);
    long l2 = Long.parseLong(str.substring(9, 13), 16);
    long l3 = Long.parseLong(str.substring(14, 8), 16);

    arrayOfLong[0] = l3;
    arrayOfLong[1] = l1;
    arrayOfLong[2] = l2;

    return arrayOfLong;
  }

  static final void kgrdr2rc(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, byte[] paramArrayOfByte, int paramInt6)
    throws SQLException
  {
    paramInt6 = lmx42h(paramArrayOfByte, paramInt4, 8, paramInt6);
    paramArrayOfByte[(paramInt6++)] = 46;

    paramInt6 = lmx42h(paramArrayOfByte, paramInt5, 4, paramInt6);
    paramArrayOfByte[(paramInt6++)] = 46;

    paramInt6 = lmx42h(paramArrayOfByte, paramInt2, 4, paramInt6);
  }

  static final int lmx42h(byte[] paramArrayOfByte, long paramLong, int paramInt1, int paramInt2)
  {
    String str = Long.toHexString(paramLong).toUpperCase();

    int i = paramInt1;
    int j = 0;
    do
    {
      if (j < str.length())
      {
        paramArrayOfByte[(paramInt2 + paramInt1 - 1)] = ((byte)str.charAt(str.length() - j - 1));

        j++;
      }
      else
      {
        paramArrayOfByte[(paramInt2 + paramInt1 - 1)] = 48;
      }

      paramInt1--; } while (paramInt1 > 0);

    return i + paramInt2;
  }

  static final int kgrdc2ub(byte[] paramArrayOfByte1, int paramInt1, byte[] paramArrayOfByte2, int paramInt2, int paramInt3)
    throws SQLException
  {
    int i = getRowidType(paramArrayOfByte1, paramInt1);
    byte[] arrayOfByte1 = paramArrayOfByte2;
    int j = paramInt3 - 1;
    int k = 0;

    byte[] arrayOfByte2 = kgrd_index_64;

    int i2 = 1 + (3 * ((paramInt3 - 1) / 4) + ((paramInt3 - 1) % 4 != 0 ? (paramInt3 - 1) % 4 - 1 : 0));
    SQLException localSQLException;
    if (j == 0)
    {
      localSQLException = DatabaseError.createSqlException(null, 132);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    arrayOfByte1[(paramInt2 + 0)] = i;

    k = paramInt1 + 1;
    int i1 = 1;

    while (j > 0)
    {
      if (j == 1)
      {
        localSQLException = DatabaseError.createSqlException(null, 132);
        localSQLException.fillInStackTrace();
        throw localSQLException;
      }

      int m = arrayOfByte2[paramArrayOfByte1[k]];
      if (m == -1)
      {
        localSQLException = DatabaseError.createSqlException(null, 132);
        localSQLException.fillInStackTrace();
        throw localSQLException;
      }

      k++;
      int n = arrayOfByte2[paramArrayOfByte1[k]];
      if (n == -1)
      {
        localSQLException = DatabaseError.createSqlException(null, 132);
        localSQLException.fillInStackTrace();
        throw localSQLException;
      }

      arrayOfByte1[(paramInt2 + i1)] = ((byte)((m & 0xFF) << 2 | (n & 0x30) >> 4));

      if (j == 2)
      {
        break;
      }

      i1++;
      m = n;
      k++;
      n = arrayOfByte2[paramArrayOfByte1[k]];
      if (n == -1)
      {
        localSQLException = DatabaseError.createSqlException(null, 132);
        localSQLException.fillInStackTrace();
        throw localSQLException;
      }

      arrayOfByte1[(paramInt2 + i1)] = ((byte)((m & 0xFF) << 4 | (n & 0x3C) >> 2));

      if (j == 3)
      {
        break;
      }

      i1++;
      m = n;
      k++;
      n = arrayOfByte2[paramArrayOfByte1[k]];
      if (n == -1)
      {
        localSQLException = DatabaseError.createSqlException(null, 132);
        localSQLException.fillInStackTrace();
        throw localSQLException;
      }

      arrayOfByte1[(paramInt2 + i1)] = ((byte)((m & 0x3) << 6 | n));

      j -= 4;
      k++;
      i1++;
    }

    return i2;
  }

  static final long[] stringToRowid(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws SQLException
  {
    int i = 18;

    if (paramInt2 != i)
    {
      throw new SQLException("Rowid size incorrect.");
    }

    long[] arrayOfLong = new long[4];
    try
    {
      arrayOfLong[0] = kgrdb42(paramArrayOfByte, 6, paramInt1);

      paramInt1 += 6;

      arrayOfLong[1] = kgrdb42(paramArrayOfByte, 3, paramInt1);

      paramInt1 += 3;

      arrayOfLong[2] = kgrdb42(paramArrayOfByte, 6, paramInt1);

      paramInt1 += 6;

      arrayOfLong[3] = kgrdb42(paramArrayOfByte, 3, paramInt1);

      paramInt1 += 3;
    }
    catch (Exception localException)
    {
      arrayOfLong[0] = 0L;
      arrayOfLong[1] = 0L;
      arrayOfLong[2] = 0L;
      arrayOfLong[3] = 0L;
    }

    return arrayOfLong;
  }

  static final int kgrd42b(byte[] paramArrayOfByte, long paramLong, int paramInt1, int paramInt2)
  {
    int i = paramInt1;
    long l = paramLong;

    for (; paramInt1 > 0; paramInt1--)
    {
      paramArrayOfByte[(paramInt2 + paramInt1 - 1)] = kgrd_basis_64[((int)l & 0x3F)];
      l = l >>> 6 & 0x3FFFFFF;
    }

    return i + paramInt2;
  }

  static final long kgrdb42(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws SQLException
  {
    long l = 0L;

    for (int i = 0; i < paramInt1; i++)
    {
      int j = paramArrayOfByte[(paramInt2 + i)];

      j = kgrd_index_64[j];

      if (j == -1) {
        throw new SQLException("Char data to rowid conversion failed.");
      }
      l <<= 6;
      l |= j;
    }

    return l;
  }

  static final void kgrdr2ec(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, byte[] paramArrayOfByte, int paramInt6)
    throws SQLException
  {
    paramInt6 = kgrd42b(paramInt1, paramArrayOfByte, paramInt6, 6);
    paramInt6 = kgrd42b(paramInt2, paramArrayOfByte, paramInt6, 3);
    paramInt6 = kgrd42b(paramInt4, paramArrayOfByte, paramInt6, 6);
    paramInt6 = kgrd42b(paramInt5, paramArrayOfByte, paramInt6, 3);
  }

  static final int kgrd42b(int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3)
    throws SQLException
  {
    int i = paramInt3;
    while (paramInt3 > 0)
    {
      paramInt3--;
      paramArrayOfByte[(paramInt2 + paramInt3)] = kgrd_basis_64[(paramInt1 & 0x3F)];

      paramInt1 >>= 6;
    }

    return paramInt2 + i;
  }

  static final int kgrdub2c(byte[] paramArrayOfByte1, int paramInt1, int paramInt2, byte[] paramArrayOfByte2, int paramInt3)
    throws SQLException
  {
    int i = -1;
    int j = paramArrayOfByte1[paramInt2];
    int m;
    int n;
    int i1;
    int i2;
    int i3;
    if (j == 1)
    {
      int[] arrayOfInt = new int[paramArrayOfByte1.length];
      for (m = 0; m < paramArrayOfByte1.length; m++) paramArrayOfByte1[m] &= 255;

      m = paramInt2 + 1;

      n = (((arrayOfInt[(m + 0)] << 8) + arrayOfInt[(m + 1)] << 8) + arrayOfInt[(m + 2)] << 8) + arrayOfInt[(m + 3)];

      m = paramInt2 + 5;

      i1 = (arrayOfInt[(m + 0)] << 8) + arrayOfInt[(m + 1)];
      i2 = 0;

      m = paramInt2 + 7;
      i3 = (((arrayOfInt[(m + 0)] << 8) + arrayOfInt[(m + 1)] << 8) + arrayOfInt[(m + 2)] << 8) + arrayOfInt[(m + 3)];

      m = paramInt2 + 11;

      int i4 = (arrayOfInt[(m + 0)] << 8) + arrayOfInt[(m + 1)];

      if (n == 0) {
        kgrdr2rc(n, i1, i2, i3, i4, paramArrayOfByte2, paramInt3);
      }
      else
      {
        kgrdr2ec(n, i1, i2, i3, i4, paramArrayOfByte2, paramInt3);
      }

      i = 18;
    }
    else
    {
      int k = 0;
      m = paramInt1 - 1;
      n = 4 * (paramInt1 / 3) + (paramInt1 % 3 == 0 ? paramInt1 % 3 + 1 : 0);

      i1 = 1 + n - 1;

      if (i1 != 0)
      {
        paramArrayOfByte2[(paramInt3 + 0)] = kgrd_indbyte_char[(j - 1)];

        i2 = paramInt2 + 1;

        k = 1;

        i3 = 0;

        while (m > 0)
        {
          paramArrayOfByte2[(paramInt3 + k++)] = kgrd_basis_64[((paramArrayOfByte1[i2] & 0xFF) >> 2)];

          if (m == 1)
          {
            paramArrayOfByte2[(paramInt3 + k++)] = kgrd_basis_64[((paramArrayOfByte1[i2] & 0x3) << 4)];

            break;
          }

          i3 = (byte)(paramArrayOfByte1[(i2 + 1)] & 0xFF);

          paramArrayOfByte2[(paramInt3 + k++)] = kgrd_basis_64[((paramArrayOfByte1[i2] & 0x3) << 4 | (i3 & 0xF0) >> 4)];

          if (m == 2)
          {
            paramArrayOfByte2[(paramInt3 + k++)] = kgrd_basis_64[((i3 & 0xF) << 2)];

            break;
          }

          i2 += 2;
          paramArrayOfByte2[(paramInt3 + k++)] = kgrd_basis_64[((i3 & 0xF) << 2 | (paramArrayOfByte1[i2] & 0xC0) >> 6)];

          paramArrayOfByte2[(paramInt3 + k)] = kgrd_basis_64[(paramArrayOfByte1[i2] & 0x3F)];

          m -= 3;
          i2++;
          k++;
        }

      }

      i = k;
    }

    return i;
  }

  static final boolean isUROWID(byte[] paramArrayOfByte, int paramInt)
  {
    return getRowidType(paramArrayOfByte, paramInt) == 2;
  }

  static final byte getRowidType(byte[] paramArrayOfByte, int paramInt)
  {
    byte b = 5;
    switch (paramArrayOfByte[paramInt])
    {
    case 65:
      b = 1;
      break;
    case 42:
      b = 2;
      break;
    case 45:
      b = 3;
      break;
    case 40:
      b = 4;
      break;
    case 41:
      b = 5;
    }

    return b;
  }
}