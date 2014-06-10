package oracle.jdbc.driver;

import java.sql.SQLException;
import oracle.jdbc.internal.OracleConnection;

class T4CTypeRep
{
  static final byte REPUNV = 1;
  static final byte REPBUNV = 1;
  static final byte REPCUNV = 1;
  static final byte REPCASC = 10;
  static final byte REPCEBC = 11;
  static final byte REPIUNV = 1;
  static final byte REPIT11 = 10;
  static final byte REPIT12_OLD = 11;
  static final byte REPIT14_OLD = 12;
  static final byte REPIU11 = 13;
  static final byte REPIU12_OLD = 14;
  static final byte REPIU14_OLD = 15;
  static final byte REPIT21_OLD = 16;
  static final byte REPIT41_OLD = 17;
  static final byte REPIU21_OLD = 18;
  static final byte REPIU41_OLD = 19;
  static final byte REPIT32_OLD = 20;
  static final byte REPIU32_OLD = 21;
  static final byte REPIT12 = 22;
  static final byte REPIT14 = 23;
  static final byte REPIU12 = 24;
  static final byte REPIU14 = 25;
  static final byte REPIT21 = 26;
  static final byte REPIT41 = 27;
  static final byte REPIU21 = 28;
  static final byte REPIU41 = 29;
  static final byte REPIT32 = 30;
  static final byte REPIU32 = 31;
  static final byte REPIT18 = 32;
  static final byte REPIU18 = 33;
  static final byte REPIT81 = 34;
  static final byte REPIU81 = 35;
  static final byte REPAUNV = 1;
  static final byte REPA4Z = 10;
  static final byte REPA2Z = 11;
  static final byte REPA8Z = 12;
  static final byte REPA16Z = 13;
  static final byte REPNV51 = 10;
  static final byte REPDV51 = 10;
  static final byte REPRUNV = 1;
  static final byte NATIVE = 0;
  static final byte UNIVERSAL = 1;
  static final byte LSB = 2;
  static final byte MAXREP = 3;
  static final byte B1 = 0;
  static final byte B2 = 1;
  static final byte B4 = 2;
  static final byte B8 = 3;
  static final byte PTR = 4;
  static final byte MAXTYPE = 4;
  byte[] rep;
  final byte NUMREPS = 5;
  byte conversionFlags;
  boolean serverConversion;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  T4CTypeRep()
  {
    this.conversionFlags = 0;
    this.serverConversion = false;
    this.rep = new byte[5];

    this.rep[0] = 0;
    this.rep[1] = 1;
    this.rep[2] = 1;
    this.rep[3] = 1;
    this.rep[4] = 1;
  }

  void setRep(byte paramByte1, byte paramByte2)
    throws SQLException
  {
    if ((paramByte1 < 0) || (paramByte1 > 4) || (paramByte2 > 3))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 407);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.rep[paramByte1] = paramByte2;
  }

  byte getRep(byte paramByte)
    throws SQLException
  {
    if ((paramByte < 0) || (paramByte > 4))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 408);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    return this.rep[paramByte];
  }

  void setFlags(byte paramByte)
  {
    this.conversionFlags = paramByte;
  }

  byte getFlags()
  {
    return this.conversionFlags;
  }

  boolean isConvNeeded()
  {
    boolean bool = (this.conversionFlags & 0x2) > 0;

    return bool;
  }

  void setServerConversion(boolean paramBoolean)
  {
    this.serverConversion = paramBoolean;
  }

  boolean isServerConversion()
  {
    return this.serverConversion;
  }

  protected OracleConnection getConnectionDuringExceptionHandling()
  {
    return null;
  }
}