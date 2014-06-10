package oracle.jdbc.driver;

import java.io.IOException;
import oracle.jdbc.internal.OracleConnection;

class T4CTTIMsg
{
  static final byte TTIPRO = 1;
  static final byte TTIDTY = 2;
  static final byte TTIFUN = 3;
  static final byte TTIOER = 4;
  static final byte TTIRXH = 6;
  static final byte TTIRXD = 7;
  static final byte TTIRPA = 8;
  static final byte TTISTA = 9;
  static final byte TTIIOV = 11;
  static final byte TTIUDS = 12;
  static final byte TTIOAC = 13;
  static final byte TTILOBD = 14;
  static final byte TTIWRN = 15;
  static final byte TTIDCB = 16;
  static final byte TTIPFN = 17;
  static final byte TTIFOB = 19;
  static final byte TTINTY = 1;
  static final byte TTIBVC = 21;
  static final byte TTISPF = 23;
  static final byte OERFSPND = 1;
  static final byte OERFATAL = 2;
  static final byte OERFPLSW = 4;
  static final byte OERFUPD = 8;
  static final byte OERFEXIT = 16;
  static final byte OERFNCF = 32;
  static final byte OERFRDONLY = 64;
  static final short OERFSBRK = 128;
  static final byte OERwANY = 1;
  static final byte OERwTRUN = 2;
  static final byte OERwLICM = 2;
  static final byte OERwNVIC = 4;
  static final byte OERwITCE = 8;
  static final byte OERwUDnW = 16;
  static final byte OERwCPER = 32;
  static final byte OERwPLEX = 64;
  static final short ORACLE8_PROD_VERSION = 8030;
  static final short ORACLE81_PROD_VERSION = 8100;
  static final short MIN_OVERSION_SUPPORTED = 7230;
  static final short MIN_TTCVER_SUPPORTED = 4;
  static final short V8_TTCVER_SUPPORTED = 5;
  static final short MAX_TTCVER_SUPPORTED = 6;
  static final int REFCURSOR_SIZE = 5;
  static final byte OCQCINV = 1;
  static final byte OCOSPID = 2;
  static final byte OCTRCEVT = 3;
  static final byte OCSESSRET = 4;
  static final byte OCSSYNC = 5;
  static final byte OCXSSS = 6;
  static final byte MAX_OCFN = 6;
  private byte ttcCode;
  final T4CConnection connection;
  final T4CMAREngine meg;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  private T4CTTIMsg()
  {
    this.ttcCode = 0;
    this.connection = null;
    this.meg = null;
  }

  T4CTTIMsg(T4CConnection paramT4CConnection, byte paramByte)
  {
    this.connection = paramT4CConnection;
    this.ttcCode = paramByte;
    this.meg = paramT4CConnection.getMarshalEngine();
  }

  final byte getTTCCode()
  {
    return this.ttcCode;
  }

  final void setTTCCode(byte paramByte)
  {
    this.ttcCode = paramByte;
  }

  void marshalTTCcode()
    throws IOException
  {
    this.meg.marshalUB1((short)this.ttcCode);
  }

  void send()
    throws IOException
  {
  }

  protected OracleConnection getConnectionDuringExceptionHandling()
  {
    return null;
  }
}