package oracle.sql;

import java.sql.SQLException;
import oracle.jdbc.driver.DatabaseError;
import oracle.sql.converter.JdbcCharacterConverters;

class CharacterSetZHTEUC extends CharacterSetWithConverter
{
  static final String CHAR_CONV_SUPERCLASS_NAME = "oracle.sql.converter.CharacterConverterZHTEUC";
  static final int MAX_7BIT = 127;
  static final int CHARLENGTH = 4;
  static Class m_charConvSuperclass;
  char[] m_leadingCodes;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  CharacterSetZHTEUC(int paramInt, JdbcCharacterConverters paramJdbcCharacterConverters)
  {
    super(paramInt, paramJdbcCharacterConverters);

    this.m_leadingCodes = paramJdbcCharacterConverters.getLeadingCodes();
  }

  static CharacterSetZHTEUC getInstance(int paramInt, JdbcCharacterConverters paramJdbcCharacterConverters)
  {
    if (paramJdbcCharacterConverters.getGroupId() == 5)
    {
      return new CharacterSetZHTEUC(paramInt, paramJdbcCharacterConverters);
    }

    return null;
  }

  int decode(CharacterWalker paramCharacterWalker)
    throws SQLException
  {
    if (paramCharacterWalker.next + 1 < paramCharacterWalker.bytes.length)
    {
      i = paramCharacterWalker.bytes[paramCharacterWalker.next] << 8 | paramCharacterWalker.bytes[(paramCharacterWalker.next + 1)];

      for (int j = 0; j < this.m_leadingCodes.length; j++)
      {
        if (i == this.m_leadingCodes[j])
        {
          if (paramCharacterWalker.bytes.length - paramCharacterWalker.next < 4)
          {
            SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 182, "destination too small");
            localSQLException2.fillInStackTrace();
            throw localSQLException2;
          }

          int k = 0;

          for (int m = 0; m < 4; m++)
          {
            k = k << 8 | paramCharacterWalker.bytes[(paramCharacterWalker.next++)];
          }

          return k;
        }

      }

    }

    int i = paramCharacterWalker.bytes[paramCharacterWalker.next] & 0xFF;

    paramCharacterWalker.next += 1;

    if (i > 127)
    {
      if (paramCharacterWalker.bytes.length > paramCharacterWalker.next)
      {
        i = i << 8 | paramCharacterWalker.bytes[paramCharacterWalker.next];
        paramCharacterWalker.next += 1;
      }
      else
      {
        SQLException localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 182);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

    }

    return i;
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

        for (int k = 0; k < 4; k++)
        {
          paramCharacterBuffer.bytes[(paramCharacterBuffer.next++)] = ((byte)paramInt);
          paramInt >>= 8;
        }

        return;
      }

    }

    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 181, "Failed to find valid leading code");
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }
}