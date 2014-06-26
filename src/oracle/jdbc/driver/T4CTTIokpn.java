package oracle.jdbc.driver;

import java.io.IOException;
import java.sql.SQLException;
import oracle.jdbc.internal.OracleConnection;
import oracle.sql.TIMESTAMPTZ;

final class T4CTTIokpn extends T4CTTIfun
{
  static final int REGISTER_KPNDEF = 1;
  static final int UNREGISTER_KPNDEF = 2;
  static final int POST_KPNDEF = 3;
  static final int EXISTINGCLIENT_KPNDEF = 0;
  static final int NEWCLIENT_KPNDEF = 1;
  static final int KPUN_PRS_RAW = 1;
  static final int KPUN_VER_10200 = 2;
  static final int KPUN_VER_11100 = 3;
  static final int KPUN_VER_11200 = 4;
  static final int OCI_SUBSCR_NAMESPACE_ANONYMOUS = 0;
  static final int OCI_SUBSCR_NAMESPACE_AQ = 1;
  static final int OCI_SUBSCR_NAMESPACE_DBCHANGE = 2;
  static final int OCI_SUBSCR_NAMESPACE_MAX = 3;
  static final int KPD_CHNF_OPFILTER = 1;
  static final int KPD_CHNF_INSERT = 2;
  static final int KPD_CHNF_UPDATE = 4;
  static final int KPD_CHNF_DELETE = 8;
  static final int KPD_CHNF_ROWID = 16;
  static final int KPD_CQ_QUERYNF = 32;
  static final int KPD_CQ_BEST_EFFORT = 64;
  static final int KPD_CQ_CLQRYCACHE = 128;
  static final int KPD_CHNF_INVALID_REGID = 0;
  static final int SUBSCR_QOS_RELIABLE = 1;
  static final int SUBSCR_QOS_PAYLOAD = 2;
  static final int SUBSCR_QOS_REPLICATE = 4;
  static final int SUBSCR_QOS_SECURE = 8;
  static final int SUBSCR_QOS_PURGE_ON_NTFN = 16;
  static final int SUBSCR_QOS_MULTICBK = 32;
  static final byte SUBSCR_NTFN_GROUPING_CLASS_NONE = 0;
  static final byte SUBSCR_NTFN_GROUPING_CLASS_TIME = 1;
  static final byte SUBSCR_NTFN_GROUPING_TYPE_SUMMARY = 1;
  static final byte SUBSCR_NTFN_GROUPING_TYPE_LAST = 2;
  private int opcode;
  private int mode;
  private int nbOfRegistrationInfo;
  private String user;
  private String location;
  private int[] namespace;
  private int[] kpdnrgrpval;
  private int[] kpdnrgrprepcnt;
  private int[] payloadType;
  private int[] qosFlags;
  private int[] timeout;
  private int[] dbchangeOpFilter;
  private int[] dbchangeTxnLag = null;

  private byte[][] registeredAgentName = (byte[][])null;
  private byte[][] kpdnrcx = (byte[][])null;
  private byte[] kpdnrgrpcla;
  private byte[] kpdnrgrptyp = null;
  private TIMESTAMPTZ[] kpdnrgrpstatim = null;
  private long[] dbchangeRegistrationId = null;
  private byte[] userArr = null;
  private byte[] locationArr = null;

  private long regid = 0L;

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  T4CTTIokpn(T4CConnection paramT4CConnection)
  {
    super(paramT4CConnection, (byte)3);

    setFunCode((short)125);
  }

  void doOKPN(int paramInt1, int paramInt2, String paramString1, String paramString2, int paramInt3, int[] paramArrayOfInt1, String[] paramArrayOfString, byte[][] paramArrayOfByte, int[] paramArrayOfInt2, int[] paramArrayOfInt3, int[] paramArrayOfInt4, int[] paramArrayOfInt5, int[] paramArrayOfInt6, long[] paramArrayOfLong, byte[] paramArrayOfByte1, int[] paramArrayOfInt7, byte[] paramArrayOfByte2, TIMESTAMPTZ[] paramArrayOfTIMESTAMPTZ, int[] paramArrayOfInt8)
    throws IOException, SQLException
  {
    this.opcode = paramInt1;
    this.mode = paramInt2;
    this.user = paramString1;
    this.location = paramString2;
    this.nbOfRegistrationInfo = paramInt3;
    this.namespace = paramArrayOfInt1;
    this.kpdnrcx = paramArrayOfByte;
    this.payloadType = paramArrayOfInt2;
    this.qosFlags = paramArrayOfInt3;
    this.timeout = paramArrayOfInt4;
    this.dbchangeOpFilter = paramArrayOfInt5;
    this.dbchangeTxnLag = paramArrayOfInt6;
    this.dbchangeRegistrationId = paramArrayOfLong;
    this.kpdnrgrpcla = paramArrayOfByte1;
    this.kpdnrgrpval = paramArrayOfInt7;
    this.kpdnrgrptyp = paramArrayOfByte2;
    this.kpdnrgrpstatim = paramArrayOfTIMESTAMPTZ;
    this.kpdnrgrprepcnt = paramArrayOfInt8;

    this.registeredAgentName = new byte[this.nbOfRegistrationInfo][];
    for (int i = 0; i < this.nbOfRegistrationInfo; i++) {
      if (paramArrayOfString[i] != null)
        this.registeredAgentName[i] = this.meg.conv.StringToCharBytes(paramArrayOfString[i]);
    }
    if (this.user != null)
      this.userArr = this.meg.conv.StringToCharBytes(this.user);
    else {
      this.userArr = null;
    }
    if (this.location != null)
      this.locationArr = this.meg.conv.StringToCharBytes(this.location);
    else {
      this.locationArr = null;
    }

    this.regid = 0L;

    doRPC();
  }

  void marshal()
    throws IOException
  {
    int i = 1;
    int j = 2;

    this.meg.marshalUB1((short)(byte)this.opcode);

    this.meg.marshalUB4(this.mode);

    if (this.userArr != null)
    {
      this.meg.marshalPTR();
      this.meg.marshalUB4(this.userArr.length);
    }
    else
    {
      this.meg.marshalNULLPTR();
      this.meg.marshalUB4(0L);
    }

    if (this.locationArr != null)
    {
      this.meg.marshalPTR();
      this.meg.marshalUB4(this.locationArr.length);
    }
    else
    {
      this.meg.marshalNULLPTR();
      this.meg.marshalUB4(0L);
    }

    this.meg.marshalPTR();
    this.meg.marshalUB4(this.nbOfRegistrationInfo);

    this.meg.marshalUB2(i);

    this.meg.marshalUB2(j);
    if (this.connection.getTTCVersion() >= 4)
    {
      this.meg.marshalNULLPTR();

      this.meg.marshalPTR();

      if (this.connection.getTTCVersion() >= 5)
      {
        this.meg.marshalNULLPTR();

        this.meg.marshalPTR();
      }

    }

    if (this.userArr != null)
      this.meg.marshalCHR(this.userArr);
    if (this.locationArr != null) {
      this.meg.marshalCHR(this.locationArr);
    }
    for (int k = 0; k < this.nbOfRegistrationInfo; k++)
    {
      this.meg.marshalUB4(this.namespace[k]);

      byte[] arrayOfByte1 = this.registeredAgentName[k];

      if ((arrayOfByte1 != null) && (arrayOfByte1.length > 0))
      {
        this.meg.marshalUB4(arrayOfByte1.length);
        this.meg.marshalCLR(arrayOfByte1, 0, arrayOfByte1.length);
      }
      else {
        this.meg.marshalUB4(0L);
      }
      if ((this.kpdnrcx[k] != null) && (this.kpdnrcx[k].length > 0))
      {
        this.meg.marshalUB4(this.kpdnrcx[k].length);
        this.meg.marshalCLR(this.kpdnrcx[k], 0, this.kpdnrcx[k].length);
      }
      else {
        this.meg.marshalUB4(0L);
      }
      this.meg.marshalUB4(this.payloadType[k]);
      if (this.connection.getTTCVersion() >= 4)
      {
        this.meg.marshalUB4(this.qosFlags[k]);
        byte[] arrayOfByte2 = new byte[0];
        this.meg.marshalUB4(arrayOfByte2.length);
        if (arrayOfByte2.length > 0) {
          this.meg.marshalCLR(arrayOfByte2, arrayOfByte2.length);
        }
        this.meg.marshalUB4(this.timeout[k]);

        int m = 0;
        this.meg.marshalUB4(m);

        this.meg.marshalUB4(this.dbchangeOpFilter[k]);

        this.meg.marshalUB4(this.dbchangeTxnLag[k]);
        this.meg.marshalUB4((int)this.dbchangeRegistrationId[k]);

        if (this.connection.getTTCVersion() >= 5)
        {
          this.meg.marshalUB1((short)this.kpdnrgrpcla[k]);
          this.meg.marshalUB4(this.kpdnrgrpval[k]);
          this.meg.marshalUB1((short)this.kpdnrgrptyp[k]);
          if (this.kpdnrgrpstatim[k] == null)
            this.meg.marshalDALC(null);
          else
            this.meg.marshalDALC(this.kpdnrgrpstatim[k].shareBytes());
          this.meg.marshalSB4(this.kpdnrgrprepcnt[k]);

          this.meg.marshalSB8(this.dbchangeRegistrationId[k]);
        }
      }
    }
  }

  long getRegistrationId()
  {
    return this.regid;
  }

  void readRPA()
    throws IOException, SQLException
  {
    int i = (int)this.meg.unmarshalUB4();
    for (int j = 0; j < i; j++)
      this.meg.unmarshalUB4();
    int[] arrayOfInt = new int[i];
    for (int k = 0; k < i; k++)
      arrayOfInt[k] = ((int)this.meg.unmarshalUB4());
    this.regid = arrayOfInt[0];
    if (this.connection.getTTCVersion() >= 5)
    {
      int k = (int)this.meg.unmarshalUB4();
      long l = this.meg.unmarshalSB8();
      this.regid = l;
    }
  }

  protected OracleConnection getConnectionDuringExceptionHandling()
  {
    return this.connection;
  }
}