package oracle.jdbc.driver;

import java.io.IOException;
import java.sql.SQLException;

class T4Ctoh
{
  static final int TOPLVL_KPCTOH = 1;
  static final int SERBEG_KPCTOH = 2;
  static final int SEREND_KPCTOH = 4;
  static final int SERONE_KPCTOH = 8;
  static final int NEW_KPCTOH = 16;
  static final int UPDATE_KPCTOH = 32;
  static final int DELETE_KPCTOH = 64;
  static final int LAST_KPCTOH = 128;
  static final int NOOBJ_KPCTOH = 256;
  static final int NNO_KPCTOH = 512;
  static final int RAWSTR_KPCTOH = 1024;
  static final byte[] EOID_KOTTD = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1 };
  static final byte KORFPFNNL = 2;
  static final byte EXTENT_OID = 8;
  static final int DONE_KPCTOC = 0;
  static final int MORE_KPCTOC = -1;
  static final int IGNORE_KPCTOC = -2;
  static final byte[] ANYDATA_TOID = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 17 };
  static final int KOIDFLEN = 16;
  byte[] toid = null;
  byte[] oid = null;
  byte[] snapshot = null;
  int versionNumber = 0;
  int imageLength = 0;
  int flags = 0;

  int[] intArr = new int[1];

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  void init(byte[] paramArrayOfByte, int paramInt)
  {
    if ((this.toid == null) || (this.toid.length != 36)) {
      this.toid = new byte[36];
    }
    this.toid[0] = 0;
    this.toid[1] = 36;

    this.toid[2] = 2;
    this.toid[3] = 8;

    System.arraycopy(paramArrayOfByte, 0, this.toid, 4, 16);

    System.arraycopy(EOID_KOTTD, 0, this.toid, 20, 16);
    this.imageLength = paramInt;

    this.oid = null;
    this.snapshot = null;
    this.versionNumber = 0;
    this.flags = 1;
  }

  void marshal(T4CMAREngine paramT4CMAREngine)
    throws IOException
  {
    if (this.toid == null) {
      paramT4CMAREngine.marshalUB4(0L);
    }
    else {
      paramT4CMAREngine.marshalUB4(this.toid.length);
      paramT4CMAREngine.marshalCLR(this.toid, 0, this.toid.length);
    }
    if (this.oid == null) {
      paramT4CMAREngine.marshalUB4(0L);
    }
    else {
      paramT4CMAREngine.marshalUB4(this.oid.length);
      paramT4CMAREngine.marshalCLR(this.oid, 0, this.oid.length);
    }
    if (this.snapshot == null) {
      paramT4CMAREngine.marshalUB4(0L);
    }
    else {
      paramT4CMAREngine.marshalUB4(this.snapshot.length);
      paramT4CMAREngine.marshalCLR(this.snapshot, 0, this.snapshot.length);
    }
    paramT4CMAREngine.marshalUB2(this.versionNumber);
    paramT4CMAREngine.marshalUB4(this.imageLength);
    paramT4CMAREngine.marshalUB2(this.flags);
  }

  void unmarshal(T4CMAREngine paramT4CMAREngine)
    throws SQLException, IOException
  {
    int i = (int)paramT4CMAREngine.unmarshalUB4();
    if ((this.toid == null) || (this.toid.length != i))
      this.toid = new byte[i];
    if (i > 0) {
      paramT4CMAREngine.unmarshalCLR(this.toid, 0, this.intArr, i);
    }
    int j = (int)paramT4CMAREngine.unmarshalUB4();
    this.oid = new byte[j];
    if (j > 0) {
      paramT4CMAREngine.unmarshalCLR(this.oid, 0, this.intArr, j);
    }
    int k = (int)paramT4CMAREngine.unmarshalUB4();
    this.snapshot = new byte[k];
    if (k > 0) {
      paramT4CMAREngine.unmarshalCLR(this.snapshot, 0, this.intArr, k);
    }
    this.versionNumber = paramT4CMAREngine.unmarshalUB2();
    this.imageLength = ((int)paramT4CMAREngine.unmarshalUB4());
    this.flags = paramT4CMAREngine.unmarshalUB2();
  }
}