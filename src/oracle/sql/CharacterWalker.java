package oracle.sql;

import java.sql.SQLException;
import java.util.NoSuchElementException;

public final class CharacterWalker
{
  CharacterSet charSet;
  byte[] bytes;
  int next;
  int end;
  int shiftstate;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  public CharacterWalker(CharacterSet paramCharacterSet, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    this.charSet = paramCharacterSet;
    this.bytes = paramArrayOfByte;
    this.next = paramInt1;
    this.end = (paramInt1 + paramInt2);

    if (this.next < 0)
    {
      this.next = 0;
    }

    if (this.end > paramArrayOfByte.length)
    {
      this.end = paramArrayOfByte.length;
    }
  }

  public int nextCharacter()
    throws NoSuchElementException
  {
    try
    {
      return this.charSet.decode(this);
    }
    catch (SQLException localSQLException)
    {
      throw new NoSuchElementException(localSQLException.getMessage());
    }
  }

  public boolean hasMoreCharacters()
  {
    return this.next < this.end;
  }
}