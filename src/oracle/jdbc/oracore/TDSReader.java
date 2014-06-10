package oracle.jdbc.oracore;

import java.sql.SQLException;
import java.util.Vector;
import oracle.jdbc.driver.DatabaseError;
import oracle.jdbc.internal.OracleConnection;

public class TDSReader
{
  static final int KOPT_NONE_FINAL_TYPE = 1;
  static final int KOPT_JAVA_OBJECT = 2;
  long fixedDataSize;
  Vector patches;
  byte[] tds;
  int beginIndex;
  int index;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  TDSReader(byte[] paramArrayOfByte, long paramLong)
  {
    this.fixedDataSize = 0L;
    this.patches = null;

    this.tds = paramArrayOfByte;
    this.beginIndex = ((int)paramLong);
    this.index = ((int)paramLong);
  }

  void skipBytes(int paramInt)
    throws SQLException
  {
    this.index += paramInt;
  }

  void checkNextByte(byte paramByte)
    throws SQLException
  {
    try
    {
      if (paramByte != this.tds[this.index])
      {
        SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 47, "parseTDS");
        localSQLException.fillInStackTrace();
        throw localSQLException;
      }

    }
    finally
    {
      this.index += 1;
    }
  }

  byte readByte()
    throws SQLException
  {
    try
    {
      return this.tds[this.index];
    }
    finally
    {
      this.index += 1;
    }
  }

  int readUnsignedByte()
    throws SQLException
  {
    try
    {
      return this.tds[this.index] & 0xFF;
    }
    finally
    {
      this.index += 1;
    }
  }

  int readUB2()
    throws SQLException
  {
    try
    {
      return ((this.tds[this.index] & 0xFF) << 8) + (this.tds[(this.index + 1)] & 0xFF);
    }
    finally
    {
      this.index += 2;
    }
  }

  long readLong()
    throws SQLException
  {
    try
    {
      return (((this.tds[this.index] & 0xFF) * 256 + (this.tds[(this.index + 1)] & 0xFF)) * 256 + (this.tds[(this.index + 2)] & 0xFF)) * 256 + (this.tds[(this.index + 3)] & 0xFF);
    }
    finally
    {
      this.index += 4;
    }
  }

  void addNormalPatch(long paramLong, byte paramByte, OracleType paramOracleType)
    throws SQLException
  {
    addPatch(new TDSPatch(0, paramOracleType, paramLong, paramByte));
  }

  void addSimplePatch(long paramLong, OracleType paramOracleType)
    throws SQLException
  {
    addPatch(new TDSPatch(1, paramOracleType, paramLong, 0));
  }

  void addPatch(TDSPatch paramTDSPatch)
    throws SQLException
  {
    if (this.patches == null) {
      this.patches = new Vector(5);
    }
    this.patches.addElement(paramTDSPatch);
  }

  long moveToPatchPos(TDSPatch paramTDSPatch)
    throws SQLException
  {
    long l = paramTDSPatch.getPosition();

    if (this.beginIndex + l > this.tds.length)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 47, "parseTDS");
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    skip_to(l);

    return l;
  }

  TDSPatch getNextPatch()
    throws SQLException
  {
    TDSPatch localTDSPatch = null;

    if (this.patches != null)
    {
      if (this.patches.size() > 0)
      {
        localTDSPatch = (TDSPatch)this.patches.firstElement();

        this.patches.removeElementAt(0);
      }
    }

    return localTDSPatch;
  }

  void skip_to(long paramLong)
  {
    this.index = (this.beginIndex + (int)paramLong);
  }

  long offset()
    throws SQLException
  {
    return this.index - this.beginIndex;
  }

  long absoluteOffset()
    throws SQLException
  {
    return this.index;
  }

  byte[] tds()
    throws SQLException
  {
    return this.tds;
  }

  boolean isJavaObject(int paramInt, byte paramByte)
  {
    return (paramInt >= 3) && ((paramByte & 0x2) != 0);
  }

  boolean isFinalType(int paramInt, byte paramByte)
  {
    return (paramInt >= 3) && ((paramByte & 0x1) == 0);
  }

  protected OracleConnection getConnectionDuringExceptionHandling()
  {
    return null;
  }
}