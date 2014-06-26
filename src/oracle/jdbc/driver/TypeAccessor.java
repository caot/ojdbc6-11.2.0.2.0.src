package oracle.jdbc.driver;

import java.sql.SQLException;
import oracle.jdbc.oracore.OracleType;

abstract class TypeAccessor extends Accessor
{
  byte[][] pickledBytes;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  abstract OracleType otypeFromName(String paramString)
    throws SQLException;

  void initForDescribe(int paramInt1, int paramInt2, boolean paramBoolean, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, short paramShort, String paramString)
    throws SQLException
  {
    this.describeTypeName = paramString;

    initForDescribe(paramInt1, paramInt2, paramBoolean, paramInt4, paramInt5, paramInt3, paramInt6, paramInt7, paramShort);
  }

  void setOffsets(int paramInt)
  {
    if (!this.outBind)
    {
      this.columnIndex = this.statement.defineByteSubRange;
      this.statement.defineByteSubRange = (this.columnIndex + paramInt * this.byteLength);
    }

    if ((this.pickledBytes == null) || (this.pickledBytes.length < paramInt))
      this.pickledBytes = new byte[paramInt][];
  }

  byte[] pickledBytes(int paramInt)
  {
    return this.pickledBytes[paramInt];
  }

  void initForDataAccess(int paramInt1, int paramInt2, String paramString)
    throws SQLException
  {
    if (paramInt1 != 0) {
      this.externalType = paramInt1;
    }
    this.internalTypeMaxLength = 0;
    this.internalTypeName = paramString;
  }

  void initMetadata()
    throws SQLException
  {
    if ((this.describeOtype == null) && (this.describeTypeName != null)) {
      this.describeOtype = otypeFromName(this.describeTypeName);
    }
    if ((this.internalOtype == null) && (this.internalTypeName != null))
      this.internalOtype = otypeFromName(this.internalTypeName);
  }

  byte[] getBytes(int paramInt)
    throws SQLException
  {
    byte[] arrayOfByte = null;
    byte[] localObject;
    if (this.rowSpaceIndicator == null)
    {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 21);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1)
    {
      localObject = pickledBytes(paramInt);
      int i = localObject.length;

      arrayOfByte = new byte[i];

      System.arraycopy(localObject, 0, arrayOfByte, 0, i);
    }

    return arrayOfByte;
  }
}