package oracle.jdbc.driver;

import java.io.IOException;
import java.sql.SQLException;

class T4C8TTIrxh extends T4CTTIMsg
{
  short rxhflg;
  int numRqsts;
  int iterNum;
  int numItersThisTime;
  int uacBufLength;
  static final byte RXHFU2O = 1;
  static final byte RXHFEOR = 2;
  static final byte RXHPLSV = 4;
  static final byte RXHFRXR = 8;
  static final byte RXHFKCO = 16;
  static final byte RXHFDCF = 32;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  T4C8TTIrxh(T4CConnection paramT4CConnection)
  {
    super(paramT4CConnection, (byte)0);
  }

  void unmarshalV10(T4CTTIrxd paramT4CTTIrxd)
    throws SQLException, IOException
  {
    this.rxhflg = this.meg.unmarshalUB1();
    this.numRqsts = this.meg.unmarshalUB2();
    this.iterNum = this.meg.unmarshalUB2();

    this.numRqsts += this.iterNum * 256;

    this.numItersThisTime = this.meg.unmarshalUB2();
    this.uacBufLength = this.meg.unmarshalUB2();

    byte[] arrayOfByte1 = this.meg.unmarshalDALC();
    paramT4CTTIrxd.readBitVector(arrayOfByte1);

    byte[] arrayOfByte2 = this.meg.unmarshalDALC();
  }

  void init()
  {
    this.rxhflg = 0;
    this.numRqsts = 0;
    this.iterNum = 0;
    this.numItersThisTime = 0;
    this.uacBufLength = 0;
  }

  void print()
  {
  }
}