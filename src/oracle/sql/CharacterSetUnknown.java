package oracle.sql;

import java.sql.SQLException;
import oracle.jdbc.driver.DatabaseError;

class CharacterSetUnknown extends CharacterSet
  implements CharacterRepConstants
{
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  CharacterSetUnknown(int paramInt)
  {
    super(paramInt);

    this.rep = (1024 + paramInt);
  }

  public boolean isLossyFrom(CharacterSet paramCharacterSet)
  {
    return paramCharacterSet.getOracleId() != getOracleId();
  }

  public boolean isConvertibleFrom(CharacterSet paramCharacterSet)
  {
    return paramCharacterSet.getOracleId() == getOracleId();
  }

  public String toStringWithReplacement(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    return "???";
  }

  public String toString(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws SQLException
  {
    failCharsetUnknown(this);

    return null;
  }

  public byte[] convert(String paramString)
    throws SQLException
  {
    failCharsetUnknown(this);

    return null;
  }

  public byte[] convertWithReplacement(String paramString)
  {
    return new byte[0];
  }

  public byte[] convert(CharacterSet paramCharacterSet, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws SQLException
  {
    if (paramCharacterSet.getOracleId() == getOracleId())
    {
      return useOrCopy(paramArrayOfByte, paramInt1, paramInt2);
    }

    failCharsetUnknown(this);

    return null;
  }

  int decode(CharacterWalker paramCharacterWalker)
    throws SQLException
  {
    failCharsetUnknown(this);

    return 0;
  }

  void encode(CharacterBuffer paramCharacterBuffer, int paramInt)
    throws SQLException
  {
    failCharsetUnknown(this);
  }

  static void failCharsetUnknown(CharacterSet paramCharacterSet)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(null, 56, paramCharacterSet);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public boolean isUnknown()
  {
    return true;
  }
}