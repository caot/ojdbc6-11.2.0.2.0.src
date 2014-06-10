package oracle.sql;

import java.sql.SQLException;
import oracle.jdbc.driver.DatabaseError;
import oracle.sql.converter.JdbcCharacterConverters;

class CharacterSet12Byte extends CharacterSetWithConverter
{
  static final String CHAR_CONV_SUPERCLASS_NAME = "oracle.sql.converter.CharacterConverter12Byte";
  static final int MAX_7BIT = 127;
  static Class m_charConvSuperclass;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  CharacterSet12Byte(int paramInt, JdbcCharacterConverters paramJdbcCharacterConverters)
  {
    super(paramInt, paramJdbcCharacterConverters);
  }

  static CharacterSet12Byte getInstance(int paramInt, JdbcCharacterConverters paramJdbcCharacterConverters)
  {
    if (paramJdbcCharacterConverters.getGroupId() == 1)
    {
      return new CharacterSet12Byte(paramInt, paramJdbcCharacterConverters);
    }

    return null;
  }

  int decode(CharacterWalker paramCharacterWalker)
    throws SQLException
  {
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
        SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 182);
        localSQLException.fillInStackTrace();
        throw localSQLException;
      }

    }

    return i;
  }

  void encode(CharacterBuffer paramCharacterBuffer, int paramInt)
    throws SQLException
  {
    int i = 0;
    int j = 1;

    while (paramInt >> i != 0)
    {
      i = (short)(i + 8);
      j = (short)(j + 1);
    }

    need(paramCharacterBuffer, j);

    while (i >= 0)
    {
      paramCharacterBuffer.bytes[(paramCharacterBuffer.next++)] = ((byte)(paramInt >> i & 0xFF));
      i = (short)(i - 8);
    }
  }
}