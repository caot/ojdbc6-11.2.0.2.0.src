package oracle.sql;

import java.sql.SQLException;
import oracle.jdbc.driver.DatabaseError;
import oracle.sql.converter.JdbcCharacterConverters;

class CharacterSetGB18030 extends CharacterSetWithConverter
{
  static final int MAX_7BIT = 127;
  static Class m_charConvSuperclass;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  CharacterSetGB18030(int paramInt, JdbcCharacterConverters paramJdbcCharacterConverters)
  {
    super(paramInt, paramJdbcCharacterConverters);
  }

  static CharacterSetGB18030 getInstance(int paramInt, JdbcCharacterConverters paramJdbcCharacterConverters)
  {
    if (paramJdbcCharacterConverters.getGroupId() == 9)
    {
      return new CharacterSetGB18030(paramInt, paramJdbcCharacterConverters);
    }

    return null;
  }

  int decode(CharacterWalker paramCharacterWalker)
    throws SQLException
  {
    int i = paramCharacterWalker.bytes[paramCharacterWalker.next] & 0xFF;

    if (i > 127)
    {
      SQLException localSQLException;
      if (paramCharacterWalker.bytes.length > paramCharacterWalker.next + 1)
      {
        if (((paramCharacterWalker.bytes[paramCharacterWalker.next] & 0xFF) >= 129) && ((paramCharacterWalker.bytes[paramCharacterWalker.next] & 0xFF) <= 254) && ((paramCharacterWalker.bytes[(paramCharacterWalker.next + 1)] & 0xFF) >= 48) && ((paramCharacterWalker.bytes[(paramCharacterWalker.next + 1)] & 0xFF) <= 57))
        {
          if (paramCharacterWalker.bytes.length > paramCharacterWalker.next + 3)
          {
            if (((paramCharacterWalker.bytes[(paramCharacterWalker.next + 2)] & 0xFF) >= 129) && ((paramCharacterWalker.bytes[(paramCharacterWalker.next + 2)] & 0xFF) <= 254) && ((paramCharacterWalker.bytes[(paramCharacterWalker.next + 3)] & 0xFF) >= 48) && ((paramCharacterWalker.bytes[(paramCharacterWalker.next + 3)] & 0xFF) <= 57))
            {
              i = (paramCharacterWalker.bytes[paramCharacterWalker.next] & 0xFF) << 24 | (paramCharacterWalker.bytes[(paramCharacterWalker.next + 1)] & 0xFF) << 16 | (paramCharacterWalker.bytes[(paramCharacterWalker.next + 2)] & 0xFF) << 8 | paramCharacterWalker.bytes[(paramCharacterWalker.next + 3)] & 0xFF;

              paramCharacterWalker.next += 4;
            }
            else
            {
              i = paramCharacterWalker.bytes[paramCharacterWalker.next] & 0xFF;
              paramCharacterWalker.next += 1;
            }

          }
          else
          {
            localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 182, "destination too small");
            localSQLException.fillInStackTrace();
            throw localSQLException;
          }

        }
        else
        {
          i = (paramCharacterWalker.bytes[paramCharacterWalker.next] & 0xFF) << 8 | paramCharacterWalker.bytes[(paramCharacterWalker.next + 1)] & 0xFF;

          paramCharacterWalker.next += 2;
        }

      }
      else
      {
        localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 182, "destination too small");
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
    int j = 0;

    while (paramInt >> i != 0)
    {
      i = (short)(i + 8);
      j = (short)(j + 1);
    }

    if (paramInt >> 16 != 0)
    {
      i = 3;
      j = 4;
    }
    else if (paramInt >> 8 != 0)
    {
      i = 1;
      j = 2;
    }
    else
    {
      i = 0;
      j = 1;
    }

    need(paramCharacterBuffer, j);

    while (i >= 0)
    {
      paramCharacterBuffer.bytes[(paramCharacterBuffer.next++)] = ((byte)(paramInt >> i & 0xFF));
      i = (short)(i - 8);
    }
  }
}