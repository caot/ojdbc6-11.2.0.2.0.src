package oracle.jdbc.driver;

import java.io.IOException;
import java.io.StringWriter;
import java.sql.SQLException;

class T4C8TTIpro extends T4CTTIMsg
{
  short svrCharSet;
  short svrCharSetElem;
  byte svrFlags;
  byte[] proSvrStr;
  byte proSvrVer;
  short oVersion = -1;

  boolean svrInfoAvailable = false;

  byte[] proCliVerTTC8 = { 6, 5, 4, 3, 2, 1, 0 };

  byte[] proCliStrTTC8 = { 74, 97, 118, 97, 95, 84, 84, 67, 45, 56, 46, 50, 46, 48, 0 };

  short NCHAR_CHARSET = 0;

  byte[] runtimeCapabilities = null;

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  T4C8TTIpro(T4CConnection paramT4CConnection)
    throws SQLException, IOException
  {
    super(paramT4CConnection, (byte)1);
  }

  byte[] receive()
    throws SQLException, IOException
  {
    SQLException localSQLException;
    if (this.meg.unmarshalSB1() != 1)
    {
      localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 401);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.proSvrVer = this.meg.unmarshalSB1();
    this.meg.proSvrVer = this.proSvrVer;

    switch (this.proSvrVer)
    {
    case 4:
      this.oVersion = 7230;

      break;
    case 5:
      this.oVersion = 8030;

      break;
    case 6:
      this.oVersion = 8100;

      break;
    default:
      localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 444);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.meg.unmarshalSB1();

    this.proSvrStr = this.meg.unmarshalTEXT(50);
    this.oVersion = getOracleVersion();

    this.svrCharSet = ((short)this.meg.unmarshalUB2());
    this.svrFlags = ((byte)this.meg.unmarshalUB1());

    if ((this.svrCharSetElem = (short)this.meg.unmarshalUB2()) > 0)
    {
      this.meg.unmarshalNBytes(this.svrCharSetElem * 5);
    }

    this.svrInfoAvailable = true;

    if (this.proSvrVer < 5) {
      return null;
    }

    byte b = this.meg.types.getRep((byte)1);
    this.meg.types.setRep((byte)1, (byte)0);
    int i = this.meg.unmarshalUB2();

    this.meg.types.setRep((byte)1, b);

    byte[] arrayOfByte1 = this.meg.unmarshalNBytes(i);

    int j = 6 + (arrayOfByte1[5] & 0xFF) + (arrayOfByte1[6] & 0xFF);

    this.NCHAR_CHARSET = ((short)((arrayOfByte1[(j + 3)] & 0xFF) << 8));
    this.NCHAR_CHARSET = ((short)(this.NCHAR_CHARSET | (short)(arrayOfByte1[(j + 4)] & 0xFF)));

    if (this.proSvrVer < 6) {
      return null;
    }

    int k = this.meg.unmarshalUB1();
    byte[] arrayOfByte2 = new byte[k];
    for (int m = 0; m < k; m++) {
      arrayOfByte2[m] = ((byte)this.meg.unmarshalUB1());
    }

    k = this.meg.unmarshalUB1();
    if (k > 0)
    {
      this.runtimeCapabilities = new byte[k];
      for (m = 0; m < k; m++) {
        this.runtimeCapabilities[m] = ((byte)this.meg.unmarshalUB1());
      }
    }
    return arrayOfByte2;
  }

  short getOracleVersion()
  {
    return this.oVersion;
  }

  byte[] getServerRuntimeCapabilities()
  {
    return this.runtimeCapabilities;
  }

  short getCharacterSet()
  {
    return this.svrCharSet;
  }

  short getncharCHARSET()
  {
    return this.NCHAR_CHARSET;
  }

  byte getFlags()
  {
    return this.svrFlags;
  }

  void marshal()
    throws SQLException, IOException
  {
    marshalTTCcode();

    this.meg.marshalB1Array(this.proCliVerTTC8);
    this.meg.marshalB1Array(this.proCliStrTTC8);
  }

  void printServerInfo()
  {
    if (this.svrInfoAvailable)
    {
      int i = 0;

      StringWriter localStringWriter = new StringWriter();

      localStringWriter.write("Protocol string  =");

      while (i < this.proSvrStr.length)
        localStringWriter.write((char)this.proSvrStr[(i++)]);
    }
  }
}