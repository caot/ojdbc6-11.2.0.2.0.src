package oracle.sql;

import java.sql.SQLException;
import oracle.sql.converter.JdbcCharacterConverters;

class CharacterSetJAEUC extends CharacterSetWithConverter
{
  static final String CHAR_CONV_SUPERCLASS_NAME = "oracle.sql.converter.CharacterConverterJAEUC";
  static final transient short MAX_7BIT = 127;
  static final transient short LEADINGCODE = 143;
  static Class m_charConvSuperclass;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  CharacterSetJAEUC(int paramInt, JdbcCharacterConverters paramJdbcCharacterConverters)
  {
    super(paramInt, paramJdbcCharacterConverters);
  }

  static CharacterSetJAEUC getInstance(int paramInt, JdbcCharacterConverters paramJdbcCharacterConverters)
  {
    if (paramJdbcCharacterConverters.getGroupId() == 2)
    {
      return new CharacterSetJAEUC(paramInt, paramJdbcCharacterConverters);
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
      if (i != 143)
      {
        if (paramCharacterWalker.bytes.length > paramCharacterWalker.next)
        {
          i = i << 8 | paramCharacterWalker.bytes[paramCharacterWalker.next];
          paramCharacterWalker.next += 1;
        }

      }
      else
      {
        for (int j = 0; j < 2; j++)
        {
          if (paramCharacterWalker.bytes.length > paramCharacterWalker.next)
          {
            i = i << 8 | paramCharacterWalker.bytes[paramCharacterWalker.next];
            paramCharacterWalker.next += 1;
          }
        }
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