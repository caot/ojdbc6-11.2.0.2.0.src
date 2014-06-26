package oracle.jdbc.driver;

import java.io.IOException;
import java.sql.SQLException;
import oracle.sql.TIMESTAMP;

class T4CTTIaqm
{
  static final int ATTR_ORIGINAL_MSGID = 69;
  static final byte ATTR_AGENT_NAME = 64;
  static final byte ATTR_AGENT_ADDRESS = 65;
  static final byte ATTR_AGENT_PROTOCOL = 66;
  static final int AQM_MSG_NO_DELAY = 0;
  static final int AQM_MSG_NO_EXPIRATION = -1;
  static final int AQM_MSGPROP_CORRID_SIZE = 128;
  int aqmpri;
  int aqmdel;
  int aqmexp;
  byte[] aqmcorBytes;
  int aqmcorBytesLength;
  int aqmatt;
  byte[] aqmeqnBytes;
  int aqmeqnBytesLength;
  int aqmsta;
  private byte[] aqmeqtBuffer = new byte[7];
  private int[] retInt = new int[1];
  TIMESTAMP aqmeqt;
  byte[] aqmetiBytes;
  byte[] senderAgentName = null;
  int senderAgentNameLength = 0;
  byte[] senderAgentAddress = null;
  int senderAgentAddressLength = 0;
  byte senderAgentProtocol = 0;
  byte[] originalMsgId;
  T4Ctoh toh;
  int aqmcsn;
  int aqmdsn;
  int aqmflg;
  T4CMAREngine mar;
  T4CConnection connection;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  T4CTTIaqm(T4CConnection paramT4CConnection, T4Ctoh paramT4Ctoh)
  {
    this.toh = paramT4Ctoh;
    this.connection = paramT4CConnection;
    this.mar = this.connection.mare;
  }

  void initToDefaultValues()
  {
    this.aqmpri = 0;
    this.aqmdel = 0;
    this.aqmexp = -1;
    this.aqmcorBytes = null;
    this.aqmcorBytesLength = 0;
    this.aqmatt = 0;
    this.aqmeqnBytes = null;
    this.aqmeqnBytesLength = 0;
    this.aqmsta = 0;
    this.aqmeqt = null;
    this.aqmetiBytes = null;
    this.senderAgentName = null;
    this.senderAgentNameLength = 0;
    this.senderAgentAddress = null;
    this.senderAgentAddressLength = 0;
    this.senderAgentProtocol = 0;
    this.originalMsgId = null;
    this.aqmcsn = 0;
    this.aqmdsn = 0;
    this.aqmflg = 0;
  }

  void marshal()
    throws IOException
  {
    this.mar.marshalSB4(this.aqmpri);
    this.mar.marshalSB4(this.aqmdel);
    this.mar.marshalSB4(this.aqmexp);
    if ((this.aqmcorBytes != null) && (this.aqmcorBytes.length != 0))
    {
      this.mar.marshalSWORD(this.aqmcorBytes.length);
      this.mar.marshalCLR(this.aqmcorBytes, 0, this.aqmcorBytes.length);
    }
    else {
      this.mar.marshalSWORD(0);
    }

    this.mar.marshalSB4(0);

    if ((this.aqmeqnBytes != null) && (this.aqmeqnBytes.length != 0))
    {
      this.mar.marshalSWORD(this.aqmeqnBytes.length);
      this.mar.marshalCLR(this.aqmeqnBytes, 0, this.aqmeqnBytes.length);
    }
    else {
      this.mar.marshalSWORD(0);
    }

    this.mar.marshalSB4(this.aqmsta);

    this.mar.marshalSWORD(0);

    if (this.connection.getTTCVersion() >= 3)
    {
      if ((this.aqmetiBytes != null) && (this.aqmetiBytes.length > 0))
      {
        this.mar.marshalSWORD(this.aqmetiBytes.length);
        this.mar.marshalCLR(this.aqmetiBytes, 0, this.aqmetiBytes.length);
      }
      else {
        this.mar.marshalSWORD(0);
      }
    }
    int i = 4;
    byte[][] arrayOfByte1 = new byte[i][];
    byte[][] arrayOfByte2 = new byte[i][];
    int[] arrayOfInt = new int[i];
    arrayOfByte1[0] = this.senderAgentName;
    arrayOfByte2[0] = null;
    arrayOfInt[0] = 64;
    arrayOfByte1[1] = this.senderAgentAddress;
    arrayOfByte2[1] = null;
    arrayOfInt[1] = 65;
    arrayOfByte1[2] = null;
    arrayOfByte2[2] = new byte[1];
    arrayOfByte2[2][0] = this.senderAgentProtocol;
    arrayOfInt[2] = 66;

    arrayOfByte1[3] = null;
    arrayOfByte2[3] = this.originalMsgId;
    arrayOfInt[3] = 69;

    this.mar.marshalSWORD(i);

    this.mar.marshalUB1((short)14);

    this.mar.marshalKPDKV(arrayOfByte1, arrayOfByte2, arrayOfInt);

    if (this.connection.getTTCVersion() >= 3)
    {
      this.mar.marshalUB4(1L);

      this.toh.init(T4Ctoh.ANYDATA_TOID, 0);
      this.toh.marshal(this.mar);

      this.mar.marshalUB4(0L);

      this.mar.marshalUB4(0L);

      if (this.connection.getTTCVersion() >= 4)
      {
        this.mar.marshalUB4(0L);
      }
    }
  }

  void receive()
    throws SQLException, IOException
  {
    this.aqmpri = this.mar.unmarshalSB4();
    this.aqmdel = this.mar.unmarshalSB4();
    this.aqmexp = this.mar.unmarshalSB4();
    int i = this.mar.unmarshalSWORD();
    if (i > 0)
    {
      this.aqmcorBytes = new byte[i];
      int[] arrayOfInt1 = new int[1];
      this.mar.unmarshalCLR(this.aqmcorBytes, 0, arrayOfInt1, this.aqmcorBytes.length);
      this.aqmcorBytesLength = arrayOfInt1[0];
    }
    else {
      this.aqmcorBytes = null;
    }
    this.aqmatt = this.mar.unmarshalSB4();
    int j = this.mar.unmarshalSWORD();
    if (j > 0)
    {
      this.aqmeqnBytes = new byte[j];
      int[] arrayOfInt2 = new int[1];
      this.mar.unmarshalCLR(this.aqmeqnBytes, 0, arrayOfInt2, this.aqmeqnBytes.length);
      this.aqmeqnBytesLength = arrayOfInt2[0];
    }
    else {
      this.aqmeqnBytes = null;
    }
    this.aqmsta = this.mar.unmarshalSB4();
    int k = this.mar.unmarshalSB4();
    if (k > 0)
    {
      this.mar.unmarshalCLR(this.aqmeqtBuffer, 0, this.retInt, 7);
      this.aqmeqt = new TIMESTAMP(this.aqmeqtBuffer);
    }
    if (this.connection.getTTCVersion() >= 3)
    {
      int m = this.mar.unmarshalSWORD();
      if (m > 0)
      {
        this.aqmetiBytes = new byte[m];
        int[] localObject = new int[1];
        this.mar.unmarshalCLR(this.aqmetiBytes, 0, (int[])localObject, this.aqmetiBytes.length);
      }
      else {
        this.aqmetiBytes = null;
      }
    }
    int m = this.mar.unmarshalSWORD();
    this.mar.unmarshalUB1();
    if (m > 0)
    {
      byte[][] localObject = new byte[m][];
      int[] arrayOfInt3 = new int[m];
      byte[][] arrayOfByte = new byte[m][];
      int[] arrayOfInt4 = new int[m];
      this.mar.unmarshalKPDKV((byte[][])localObject, arrayOfInt3, arrayOfByte, arrayOfInt4);

      for (int i1 = 0; i1 < m; i1++)
      {
        if ((arrayOfInt4[i1] == 64) && (localObject[i1] != null) && (arrayOfInt3[i1] > 0))
        {
          this.senderAgentName = localObject[i1];
          this.senderAgentNameLength = arrayOfInt3[i1];
        }

        if ((arrayOfInt4[i1] == 65) && (localObject[i1] != null) && (arrayOfInt3[i1] > 0))
        {
          this.senderAgentAddress = localObject[i1];
          this.senderAgentAddressLength = arrayOfInt3[i1];
        }

        if ((arrayOfInt4[i1] == 66) && (arrayOfByte[i1] != null) && (arrayOfByte[i1].length > 0))
        {
          this.senderAgentProtocol = arrayOfByte[i1][0];
        }if ((arrayOfInt4[i1] == 69) && (arrayOfByte[i1] != null) && (arrayOfByte[i1].length > 0))
        {
          this.originalMsgId = arrayOfByte[i1];
        }

      }

    }

    if (this.connection.getTTCVersion() >= 3)
    {
      int n = this.mar.unmarshalSWORD();

      this.aqmcsn = ((int)this.mar.unmarshalUB4());
      this.aqmdsn = ((int)this.mar.unmarshalUB4());
      if (this.connection.getTTCVersion() >= 4)
      {
        this.aqmflg = ((int)this.mar.unmarshalUB4());
      }
    }
  }
}