package oracle.jdbc.driver;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

class T4CResultSetAccessor extends ResultSetAccessor
{
  T4CMAREngine mare;
  OracleStatement[] newstmt = new OracleStatement[10];
  byte[] empty = { 0 };

  boolean underlyingLongRaw = false;

  final int[] meta = new int[1];

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  T4CResultSetAccessor(OracleStatement paramOracleStatement, int paramInt1, short paramShort, int paramInt2, boolean paramBoolean, T4CMAREngine paramT4CMAREngine)
    throws SQLException
  {
    super(paramOracleStatement, paramInt1, paramShort, paramInt2, paramBoolean);

    this.mare = paramT4CMAREngine;
  }

  T4CResultSetAccessor(OracleStatement paramOracleStatement, int paramInt1, boolean paramBoolean, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, short paramShort, int paramInt7, int paramInt8, T4CMAREngine paramT4CMAREngine)
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
      byte[] arrayOfByte = new byte[16000];

      this.mare.unmarshalCLR(arrayOfByte, 0, this.meta);
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

      processIndicator(0);

      return false;
    }

    int k = this.columnIndex + this.lastRowProcessed * this.byteLength;

    if (this.newstmt.length <= this.lastRowProcessed)
    {
      localObject = new OracleStatement[this.newstmt.length * 4];

      System.arraycopy(this.newstmt, 0, localObject, 0, this.newstmt.length);

      this.newstmt = ((OracleStatement[])localObject);
    }

    this.newstmt[this.lastRowProcessed] = this.statement.connection.RefCursorBytesToStatement(this.empty, this.statement);

    this.newstmt[this.lastRowProcessed].needToSendOalToFetch = true;

    Object localObject = new T4CTTIdcb((T4CConnection)this.statement.connection);

    ((T4CTTIdcb)localObject).init(this.newstmt[this.lastRowProcessed], 0);

    this.newstmt[this.lastRowProcessed].accessors = ((T4CTTIdcb)localObject).receiveFromRefCursor(this.newstmt[this.lastRowProcessed].accessors);

    this.newstmt[this.lastRowProcessed].numberOfDefinePositions = this.newstmt[this.lastRowProcessed].accessors.length;

    this.newstmt[this.lastRowProcessed].describedWithNames = true;
    this.newstmt[this.lastRowProcessed].described = true;

    int m = (int)this.mare.unmarshalUB4();

    this.newstmt[this.lastRowProcessed].setCursorId(m);

    if (m > 0)
    {
      this.rowSpaceByte[k] = 1;
      this.rowSpaceByte[(k + 1)] = ((byte)m);

      this.meta[0] = 2;
    }
    else
    {
      this.newstmt[this.lastRowProcessed].close();

      this.newstmt[this.lastRowProcessed] = null;
      this.meta[0] = 0;
    }

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

    System.arraycopy(this.rowSpaceByte, k, this.rowSpaceByte, j, i3);

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
      System.arraycopy(paramArrayOfByte, j, this.rowSpaceByte, i, i2);
    }
  }

  ResultSet getCursor(int paramInt)
    throws SQLException
  {
    OracleResultSetImpl localOracleResultSetImpl1;
    if (this.newstmt[paramInt] != null)
    {
      for (int i = 0; i < this.newstmt[paramInt].numberOfDefinePositions; i++) {
        this.newstmt[paramInt].accessors[i].initMetadata();
      }
      this.newstmt[paramInt].prepareAccessors();

      OracleResultSetImpl localOracleResultSetImpl2 = new OracleResultSetImpl(this.newstmt[paramInt].connection, this.newstmt[paramInt]);

      localOracleResultSetImpl2.close_statement_on_close = true;
      this.newstmt[paramInt].currentResultSet = localOracleResultSetImpl2;
      localOracleResultSetImpl1 = localOracleResultSetImpl2;
    }
    else
    {
      throw new SQLException("Cursor is closed.");
    }

    return localOracleResultSetImpl1;
  }
}