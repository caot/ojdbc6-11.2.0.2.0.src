package oracle.sql;

import java.sql.SQLException;
import oracle.sql.converter.JdbcCharacterConverters;

class CharacterSet1Byte extends CharacterSetWithConverter
{
  static final String CHAR_CONV_SUPERCLASS_NAME = "oracle.sql.converter.CharacterConverter1Byte";
  static Class m_charConvSuperclass;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  CharacterSet1Byte(int paramInt, JdbcCharacterConverters paramJdbcCharacterConverters)
  {
    super(paramInt, paramJdbcCharacterConverters);
  }

  static CharacterSet1Byte getInstance(int paramInt, JdbcCharacterConverters paramJdbcCharacterConverters)
  {
    if (paramJdbcCharacterConverters.getGroupId() == 0)
    {
      return new CharacterSet1Byte(paramInt, paramJdbcCharacterConverters);
    }

    return null;
  }

  int decode(CharacterWalker paramCharacterWalker)
    throws SQLException
  {
    int i = paramCharacterWalker.bytes[paramCharacterWalker.next] & 0xFF;

    paramCharacterWalker.next += 1;

    return i;
  }

  void encode(CharacterBuffer paramCharacterBuffer, int paramInt)
    throws SQLException
  {
    need(paramCharacterBuffer, 1);

    if (paramInt < 256)
    {
      paramCharacterBuffer.bytes[paramCharacterBuffer.next] = ((byte)paramInt);
      paramCharacterBuffer.next += 1;
    }
  }

  public int toCharWithReplacement(byte[] paramArrayOfByte, int paramInt1, char[] paramArrayOfChar, int paramInt2, int paramInt3)
    throws SQLException
  {
    return this.m_converter.toUnicodeChars(paramArrayOfByte, paramInt1, paramArrayOfChar, paramInt2, paramInt3);
  }
}