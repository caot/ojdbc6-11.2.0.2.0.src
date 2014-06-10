package oracle.sql;

import java.sql.SQLException;
import oracle.jdbc.driver.DatabaseError;
import oracle.sql.converter.JdbcCharacterConverters;

class CharacterSetLCFixed extends CharacterSetWithConverter
{
  static final String CHAR_CONV_SUPERCLASS_NAME = "oracle.sql.converter.CharacterConverterLCFixed";
  static final int CHARLENGTH = 4;
  static Class m_charConvSuperclass;
  char[] m_leadingCodes;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  CharacterSetLCFixed(int paramInt, JdbcCharacterConverters paramJdbcCharacterConverters)
  {
    super(paramInt, paramJdbcCharacterConverters);

    this.m_leadingCodes = paramJdbcCharacterConverters.getLeadingCodes();
  }

  static CharacterSetLCFixed getInstance(int paramInt, JdbcCharacterConverters paramJdbcCharacterConverters)
  {
    if (paramJdbcCharacterConverters.getGroupId() == 3)
    {
      return new CharacterSetLCFixed(paramInt, paramJdbcCharacterConverters);
    }

    return null;
  }

  int decode(CharacterWalker paramCharacterWalker)
    throws SQLException
  {
    if (paramCharacterWalker.bytes.length - paramCharacterWalker.next < 4)
    {
      SQLException localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 182, "destination too small");
      localSQLException1.fillInStackTrace();
      throw localSQLException1;
    }

    int i = paramCharacterWalker.bytes[paramCharacterWalker.next] << 8 | paramCharacterWalker.bytes[(paramCharacterWalker.next + 1)];

    for (int j = 0; j < this.m_leadingCodes.length; j++)
    {
      if (i == this.m_leadingCodes[j])
      {
        int k = 0;

        for (int m = 0; m < 4; m++)
        {
          k = k << 8 | paramCharacterWalker.bytes[(paramCharacterWalker.next++)];
        }

        return k;
      }

    }

    SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 181, "Leading code invalid");
    localSQLException2.fillInStackTrace();
    throw localSQLException2;
  }

  void encode(CharacterBuffer paramCharacterBuffer, int paramInt)
    throws SQLException
  {
    int i = paramInt >> 16;

    for (int j = 0; j < this.m_leadingCodes.length; j++)
    {
      if (i == this.m_leadingCodes[j])
      {
        need(paramCharacterBuffer, 4);

        for (int k = 3; k >= 0; k--)
        {
          paramCharacterBuffer.bytes[(paramCharacterBuffer.next++)] = ((byte)(paramInt >> 8 * k & 0xFF));
        }

        return;
      }

    }

    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 181, "Leading code invalid");
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }
}