package oracle.jdbc.driver;

import java.io.IOException;
import java.sql.SQLException;
import oracle.jdbc.oracore.OracleTypeADT;

class T4CTTIoac
{
  static final short UACFIND = 1;
  static final short UACFALN = 2;
  static final short UACFRCP = 4;
  static final short UACFBBV = 8;
  static final short UACFNCP = 16;
  static final short UACFBLP = 32;
  static final short UACFARR = 64;
  static final short UACFIGN = 128;
  static final int UACFNSCL = 1;
  static final int UACFBUC = 2;
  static final int UACFSKP = 4;
  static final int UACFCHRCNT = 8;
  static final int UACFNOADJ = 16;
  static final int UACFCUS = 4096;
  static final int UACFLSZ = 33554432;
  static final int UACFVFSP = 134217728;
  static final byte[] NO_BYTES = new byte[0];
  int oaccsi;
  short oaccsfrm;
  static int maxBindArrayLength;
  T4CMAREngine meg;
  T4CConnection connection;
  short oacdty;
  short oacflg;
  short oacpre;
  short oacscl;
  int oacmxl;
  int oacmxlc;
  int oacmal;
  int oacfl2;
  byte[] oactoid;
  int oactoidl;
  int oacvsn;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  T4CTTIoac(T4CConnection paramT4CConnection)
  {
    this.connection = paramT4CConnection;
    this.meg = this.connection.mare;

    this.oactoid = NO_BYTES;
  }

  void init(short paramShort, int paramInt)
  {
    if ((paramShort == 9) || (paramShort == 1) || (paramShort == 996))
      this.oacdty = 1;
    else if (paramShort == 104)
      this.oacdty = 11;
    else if ((paramShort == 6) || (paramShort == 2))
      this.oacdty = 2;
    else if (paramShort == 15)
      this.oacdty = 23;
    else if (paramShort == 116) {
      this.oacdty = 102;
    }
    else
    {
      this.oacdty = paramShort;
    }
    if ((this.oacdty == 1) || (this.oacdty == 96))
    {
      this.oacfl2 = 16;
    }
    if (this.oacdty == 102)
      this.oacmxl = 1;
    else {
      this.oacmxl = paramInt;
    }

    this.oacflg = 3;
  }

  boolean isOldSufficient(T4CTTIoac paramT4CTTIoac)
  {
    boolean bool = false;

    if ((this.oactoidl != 0) || (paramT4CTTIoac.oactoidl != 0)) {
      return false;
    }
    if ((this.oaccsi == paramT4CTTIoac.oaccsi) && (this.oaccsfrm == paramT4CTTIoac.oaccsfrm) && (this.oacdty == paramT4CTTIoac.oacdty) && (this.oacflg == paramT4CTTIoac.oacflg) && (this.oacpre == paramT4CTTIoac.oacpre) && (this.oacscl == paramT4CTTIoac.oacscl) && ((this.oacmxl == paramT4CTTIoac.oacmxl) || ((paramT4CTTIoac.oacmxl > this.oacmxl) && (paramT4CTTIoac.oacmxl < 4000))) && (this.oacmxlc == paramT4CTTIoac.oacmxlc) && (this.oacmal == paramT4CTTIoac.oacmal) && (this.oacfl2 == paramT4CTTIoac.oacfl2) && (this.oacvsn == paramT4CTTIoac.oacvsn))
    {
      bool = true;
    }
    return bool;
  }

  boolean isNType()
  {
    boolean bool = this.oaccsfrm == 2;
    return bool;
  }

  void unmarshal()
    throws IOException, SQLException
  {
    this.oacdty = this.meg.unmarshalUB1();
    this.oacflg = this.meg.unmarshalUB1();
    this.oacpre = this.meg.unmarshalUB1();

    if ((this.oacdty == 2) || (this.oacdty == 180) || (this.oacdty == 181) || (this.oacdty == 231) || (this.oacdty == 183))
    {
      this.oacscl = ((short)this.meg.unmarshalUB2());
    }
    else this.oacscl = this.meg.unmarshalUB1();

    this.oacmxl = this.meg.unmarshalSB4();
    this.oacmal = this.meg.unmarshalSB4();
    this.oacfl2 = this.meg.unmarshalSB4();
    this.oactoid = this.meg.unmarshalDALC();
    this.oactoidl = (this.oactoid == null ? 0 : this.oactoid.length);
    this.oacvsn = this.meg.unmarshalUB2();
    this.oaccsi = this.meg.unmarshalUB2();
    this.oaccsfrm = this.meg.unmarshalUB1();

    if (this.connection.getTTCVersion() >= 2) {
      this.oacmxlc = ((int)this.meg.unmarshalUB4());
    }

    if (this.oacmxl > 0)
    {
      switch (this.oacdty)
      {
      case 2:
        this.oacmxl = 22;

        break;
      case 12:
        this.oacmxl = 7;

        break;
      case 181:
        this.oacmxl = 13;
      }
    }
  }

  void setMal(int paramInt)
  {
    this.oacmal = paramInt;
  }

  void addFlg(short paramShort)
  {
    this.oacflg = ((short)(this.oacflg | paramShort));
  }

  void addFlg2(int paramInt)
  {
    this.oacfl2 |= paramInt;
  }

  void setFormOfUse(short paramShort)
  {
    this.oaccsfrm = paramShort;
  }

  void setCharset(int paramInt)
  {
    this.oaccsi = paramInt;
  }

  void setMxlc(int paramInt)
  {
    this.oacmxlc = paramInt;
  }

  void setTimestampFractionalSecondsPrecision(short paramShort)
  {
    this.oacscl = paramShort;
  }

  void setADT(OracleTypeADT paramOracleTypeADT)
  {
    this.oactoid = paramOracleTypeADT.getTOID();
    this.oacvsn = paramOracleTypeADT.getTypeVersion();
    this.oaccsi = 2;
    this.oaccsfrm = ((short)paramOracleTypeADT.getCharSetForm());
  }

  void marshal()
    throws IOException
  {
    this.meg.marshalUB1(this.oacdty);
    this.meg.marshalUB1(this.oacflg);
    this.meg.marshalUB1(this.oacpre);

    if ((this.oacdty == 2) || (this.oacdty == 180) || (this.oacdty == 181) || (this.oacdty == 231) || (this.oacdty == 183))
    {
      this.meg.marshalUB2(this.oacscl);
    }
    else
    {
      this.meg.marshalUB1(this.oacscl);
    }

    this.meg.marshalUB4(this.oacmxl);

    this.meg.marshalSB4(this.oacmal);
    this.meg.marshalSB4(this.oacfl2);

    this.meg.marshalDALC(this.oactoid);

    this.meg.marshalUB2(this.oacvsn);
    this.meg.marshalUB2(this.oaccsi);
    this.meg.marshalUB1(this.oaccsfrm);

    if (this.connection.getTTCVersion() >= 2)
      this.meg.marshalUB4(this.oacmxlc);
  }
}