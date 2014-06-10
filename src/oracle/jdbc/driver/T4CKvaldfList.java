package oracle.jdbc.driver;

import java.sql.SQLException;

class T4CKvaldfList
{
  static final int INTIAL_CAPACITY = 30;
  private int capacity;
  private int offset;
  private byte[][] keys;
  private byte[][] values;
  private byte[] flags;
  DBConversion conv;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  T4CKvaldfList(DBConversion paramDBConversion)
  {
    this.conv = paramDBConversion;
    initializeList();
  }

  void initializeList()
  {
    this.capacity = 30;
    this.offset = 0;
    this.keys = new byte[this.capacity][];
    this.values = new byte[this.capacity][];
    this.flags = new byte[this.capacity];
  }

  void add(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte paramByte)
  {
    if (this.offset == this.capacity)
    {
      byte[][] arrayOfByte1 = new byte[this.capacity * 2][];
      byte[][] arrayOfByte2 = new byte[this.capacity * 2][];
      byte[] arrayOfByte = new byte[this.capacity * 2];
      System.arraycopy(this.keys, 0, arrayOfByte1, 0, this.capacity);
      System.arraycopy(this.values, 0, arrayOfByte2, 0, this.capacity);
      System.arraycopy(this.flags, 0, arrayOfByte, 0, this.capacity);
      this.keys = arrayOfByte1;
      this.values = arrayOfByte2;
      this.flags = arrayOfByte;
      this.capacity *= 2;
    }
    this.keys[this.offset] = paramArrayOfByte1;
    this.values[this.offset] = paramArrayOfByte2;
    this.flags[(this.offset++)] = paramByte;
  }

  void add(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
  {
    add(paramArrayOfByte1, paramArrayOfByte2, (byte)0);
  }

  void add(String paramString, byte[] paramArrayOfByte)
    throws SQLException
  {
    add(this.conv.StringToCharBytes(paramString), paramArrayOfByte, (byte)0);
  }

  void add(String paramString, byte[] paramArrayOfByte, byte paramByte)
    throws SQLException
  {
    add(this.conv.StringToCharBytes(paramString), paramArrayOfByte, paramByte);
  }

  int size()
  {
    return this.offset;
  }

  byte[][] getKeys()
  {
    return this.keys;
  }

  byte[][] getValues()
  {
    return this.values;
  }

  byte[] getFlags()
  {
    return this.flags;
  }
}