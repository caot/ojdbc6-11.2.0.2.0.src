package oracle.sql;

import java.sql.SQLException;

public final class CharacterBuffer
{
  CharacterSet charSet;
  byte[] bytes;
  int next;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  public CharacterBuffer(CharacterSet paramCharacterSet)
  {
    this.charSet = paramCharacterSet;

    this.next = 0;

    this.bytes = new byte[32];
  }

  public void append(int paramInt)
    throws SQLException
  {
    this.charSet.encode(this, paramInt);
  }

  public byte[] getBytes()
  {
    return CharacterSet.useOrCopy(this.bytes, 0, this.next);
  }
}