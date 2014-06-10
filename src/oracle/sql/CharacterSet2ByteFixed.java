package oracle.sql;

import java.sql.SQLException;
import oracle.jdbc.driver.DatabaseError;
import oracle.sql.converter.JdbcCharacterConverters;

class CharacterSet2ByteFixed extends CharacterSetWithConverter
{
  static final String CHAR_CONV_SUPERCLASS_NAME = "oracle.sql.converter.CharacterConverter2ByteFixed";
  static final short MAX_7BIT = 127;
  static final short MIN_8BIT_SB = 161;
  static final short MAX_8BIT_SB = 223;
  static final short CHARLENGTH = 2;
  static Class m_charConvSuperclass;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  CharacterSet2ByteFixed(int paramInt, JdbcCharacterConverters paramJdbcCharacterConverters)
  {
    super(paramInt, paramJdbcCharacterConverters);
  }

  static CharacterSet2ByteFixed getInstance(int paramInt, JdbcCharacterConverters paramJdbcCharacterConverters)
  {
    if (paramJdbcCharacterConverters.getGroupId() == 6)
    {
      return new CharacterSet2ByteFixed(paramInt, paramJdbcCharacterConverters);
    }

    return null;
  }

  int decode(CharacterWalker paramCharacterWalker)
    throws SQLException
  {
    int i = paramCharacterWalker.bytes[paramCharacterWalker.next] & 0xFF;

    paramCharacterWalker.next += 1;

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

    return i;
  }

  void encode(CharacterBuffer paramCharacterBuffer, int paramInt)
    throws SQLException
  {
    need(paramCharacterBuffer, 2);

    paramCharacterBuffer.bytes[(paramCharacterBuffer.next++)] = ((byte)(paramInt >> 8 & 0xFF));
    paramCharacterBuffer.bytes[(paramCharacterBuffer.next++)] = ((byte)(paramInt & 0xFF));
  }
}