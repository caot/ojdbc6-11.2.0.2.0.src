package oracle.jdbc.driver;

import java.io.IOException;
import java.sql.SQLException;

class T4CNamedTypeAccessor extends NamedTypeAccessor
{
  static final int maxLength = 2147483647;
  final int[] meta = new int[1];
  T4CMAREngine mare;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

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

  T4CNamedTypeAccessor(OracleStatement paramOracleStatement, String paramString, short paramShort, int paramInt, boolean paramBoolean, T4CMAREngine paramT4CMAREngine)
    throws SQLException
  {
    super(paramOracleStatement, paramString, paramShort, paramInt, paramBoolean);

    this.mare = paramT4CMAREngine;
  }

  T4CNamedTypeAccessor(OracleStatement paramOracleStatement, int paramInt1, boolean paramBoolean, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, short paramShort, String paramString, int paramInt7, int paramInt8, T4CMAREngine paramT4CMAREngine)
    throws SQLException
  {
    super(paramOracleStatement, paramInt1, paramBoolean, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramShort, paramString);

    this.mare = paramT4CMAREngine;
    this.definedColumnType = paramInt7;
    this.definedColumnSize = paramInt8;
  }

  boolean unmarshalOneRow()
    throws SQLException, IOException
  {
    if (this.isUseLess)
    {
      this.lastRowProcessed += 1;

      return false;
    }

    byte[] arrayOfByte1 = this.mare.unmarshalDALC();

    byte[] arrayOfByte2 = this.mare.unmarshalDALC();

    byte[] arrayOfByte3 = this.mare.unmarshalDALC();

    int i = this.mare.unmarshalUB2();
    long l = this.mare.unmarshalUB4();
    int j = this.mare.unmarshalUB2();

    byte[] arrayOfByte4 = null;

    if (l > 0L)
      arrayOfByte4 = this.mare.unmarshalCLR((int)l, this.meta);
    else {
      arrayOfByte4 = new byte[0];
    }
    this.pickledBytes[this.lastRowProcessed] = arrayOfByte4;

    processIndicator(this.meta[0]);

    int k = this.indicatorIndex + this.lastRowProcessed;
    int m = this.lengthIndex + this.lastRowProcessed;

    if (this.rowSpaceIndicator != null)
    {
      if (this.meta[0] == 0)
      {
        this.rowSpaceIndicator[k] = -1;
        this.rowSpaceIndicator[m] = 0;
      }
      else
      {
        this.rowSpaceIndicator[m] = ((short)this.meta[0]);

        this.rowSpaceIndicator[k] = 0;
      }
    }

    this.lastRowProcessed += 1;
    return false;
  }
}