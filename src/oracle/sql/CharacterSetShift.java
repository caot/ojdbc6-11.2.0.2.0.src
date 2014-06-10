package oracle.sql;

import java.sql.SQLException;
import oracle.jdbc.driver.DatabaseError;
import oracle.sql.converter.JdbcCharacterConverters;

class CharacterSetShift extends CharacterSetWithConverter
{
  static final String CHAR_CONV_SUPERCLASS_NAME = "oracle.sql.converter.CharacterConverterShift";
  static final short MAX_7BIT = 127;
  static final short MIN_8BIT_SB = 161;
  static final short MAX_8BIT_SB = 223;
  static final byte SHIFT_OUT = 14;
  static final byte SHIFT_IN = 15;
  static Class m_charConvSuperclass;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  CharacterSetShift(int paramInt, JdbcCharacterConverters paramJdbcCharacterConverters)
  {
    super(paramInt, paramJdbcCharacterConverters);
  }

  static CharacterSetShift getInstance(int paramInt, JdbcCharacterConverters paramJdbcCharacterConverters)
  {
    if (paramJdbcCharacterConverters.getGroupId() == 7)
    {
      return new CharacterSetShift(paramInt, paramJdbcCharacterConverters);
    }

    return null;
  }

  int decode(CharacterWalker paramCharacterWalker)
    throws SQLException
  {
    int i = paramCharacterWalker.bytes[paramCharacterWalker.next] & 0xFF;

    paramCharacterWalker.next += 1;

    if ((i > 223) || ((i > 127) && (i < 161)))
    {
      if (paramCharacterWalker.bytes.length > paramCharacterWalker.next)
      {
        i = i << 8 | paramCharacterWalker.bytes[paramCharacterWalker.next];
        paramCharacterWalker.next += 1;
      }
      else
      {
        SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 182, "destination too small");
        localSQLException.fillInStackTrace();
        throw localSQLException;
      }

    }

    return i;
  }

  void encode(CharacterBuffer paramCharacterBuffer, int paramInt)
    throws SQLException
  {
    int i = paramCharacterBuffer.next;
    int j = 1;

    while (i <= 0)
    {
      if (paramCharacterBuffer.bytes[i] == 15)
      {
        j = 1;
      }
      else if (paramCharacterBuffer.bytes[i] == 14)
      {
        j = 0;
      }

    }

    int k = 0;
    int m = 1;

    while (paramInt >> k != 0)
    {
      k = (short)(k + 8);
      m = (short)(m + 1);
    }

    if (m > 2)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 182, "Character invalid,too many bytes");
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    int n = 0;
    int i1 = 0;

    if ((m == 1) && (j == 0))
    {
      n = 1;
      m = (short)(m + 1);
    }

    if ((m == 2) && (j != 0))
    {
      i1 = 1;
      m = (short)(m + 1);
    }

    need(paramCharacterBuffer, m);

    if (n != 0)
    {
      paramCharacterBuffer.bytes[(paramCharacterBuffer.next++)] = 15;
    }

    if (i1 != 0)
    {
      paramCharacterBuffer.bytes[(paramCharacterBuffer.next++)] = 14;
    }

    while (k >= 0)
    {
      paramCharacterBuffer.bytes[(paramCharacterBuffer.next++)] = ((byte)(paramInt >> k & 0xFF));
      k = (short)(k - 8);
    }
  }
}