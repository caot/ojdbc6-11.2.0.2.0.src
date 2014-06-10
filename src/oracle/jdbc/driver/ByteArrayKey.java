package oracle.jdbc.driver;

class ByteArrayKey
{
  private byte[] theBytes;
  private int cachedHashCode = -1;

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  public ByteArrayKey(byte[] paramArrayOfByte)
  {
    this.theBytes = paramArrayOfByte;
    for (int k : this.theBytes)
      this.cachedHashCode = (this.cachedHashCode << 1 & (this.cachedHashCode < 0 ? 1 : 0) ^ k);
  }

  public boolean equals(Object paramObject)
  {
    if (paramObject == this) {
      return true;
    }
    if (!(paramObject instanceof ByteArrayKey))
    {
      return false;
    }

    byte[] arrayOfByte = ((ByteArrayKey)paramObject).theBytes;

    if (this.theBytes.length != arrayOfByte.length) {
      return false;
    }
    for (int i = 0; i < this.theBytes.length; i++) {
      if (this.theBytes[i] != arrayOfByte[i])
        return false;
    }
    return true;
  }

  public int hashCode()
  {
    return this.cachedHashCode;
  }
}