package oracle.jdbc.driver;

import java.io.IOException;
import java.sql.SQLException;
import oracle.jdbc.internal.OracleConnection;

final class T4CTTIOtxse extends T4CTTIfun
{
  static final int OTXSTA = 1;
  static final int OTXDET = 2;
  static final int OCI_TRANS_NEW = 1;
  static final int OCI_TRANS_JOIN = 2;
  static final int OCI_TRANS_RESUME = 4;
  static final int OCI_TRANS_STARTMASK = 255;
  static final int OCI_TRANS_READONLY = 256;
  static final int OCI_TRANS_READWRITE = 512;
  static final int OCI_TRANS_SERIALIZABLE = 1024;
  static final int OCI_TRANS_ISOLMASK = 65280;
  static final int OCI_TRANS_LOOSE = 65536;
  static final int OCI_TRANS_TIGHT = 131072;
  static final int OCI_TRANS_TYPEMASK = 983040;
  static final int OCI_TRANS_NOMIGRATE = 1048576;
  static final int OCI_TRANS_SEPARABLE = 2097152;
  boolean sendTransactionContext = false;
  private int operation;
  private int formatId;
  private int gtridLength;
  private int bqualLength;
  private int timeout;
  private int flag;
  private int[] xidapp = null;
  private byte[] transactionContext;
  private byte[] xid = null;

  private int applicationValue = -1;
  private byte[] ctx = null;

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  T4CTTIOtxse(T4CConnection paramT4CConnection)
  {
    super(paramT4CConnection, (byte)3);

    setFunCode((short)103);
  }

  void doOTXSE(int paramInt1, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int[] paramArrayOfInt)
    throws IOException, SQLException
  {
    if ((paramInt1 != 1) && (paramInt1 != 2))
      throw new SQLException("Invalid operation.");
    this.operation = paramInt1;
    this.formatId = paramInt2;
    this.gtridLength = paramInt3;
    this.bqualLength = paramInt4;
    this.timeout = paramInt5;
    this.flag = paramInt6;
    this.xidapp = paramArrayOfInt;
    this.transactionContext = paramArrayOfByte1;
    this.xid = paramArrayOfByte2;

    this.applicationValue = -1;
    this.ctx = null;

    if ((this.operation == 2) && (this.transactionContext == null))
    {
      throw new SQLException("Transaction context cannot be null when detach is called.");
    }
    doRPC();
  }

  void marshal()
    throws IOException
  {
    int i = this.operation;

    this.meg.marshalSWORD(i);

    if (this.operation == 2)
    {
      this.sendTransactionContext = true;

      this.meg.marshalPTR();
    }
    else
    {
      this.sendTransactionContext = false;

      this.meg.marshalNULLPTR();
    }

    if (this.transactionContext == null)
      this.meg.marshalUB4(0L);
    else {
      this.meg.marshalUB4(this.transactionContext.length);
    }

    this.meg.marshalUB4(this.formatId);

    this.meg.marshalUB4(this.gtridLength);

    this.meg.marshalUB4(this.bqualLength);

    if (this.xid != null)
      this.meg.marshalPTR();
    else {
      this.meg.marshalNULLPTR();
    }

    if (this.xid != null)
      this.meg.marshalUB4(this.xid.length);
    else {
      this.meg.marshalUB4(0L);
    }

    this.meg.marshalUB4(this.flag);

    this.meg.marshalUWORD(this.timeout);

    if (this.xidapp != null)
      this.meg.marshalPTR();
    else {
      this.meg.marshalNULLPTR();
    }
    this.meg.marshalPTR();
    this.meg.marshalPTR();

    int j = 0;
    int k = 0;

    if (this.connection.getTTCVersion() >= 5)
    {
      if (this.connection.internalName != null)
      {
        j = 1;
        this.meg.marshalPTR();
        this.meg.marshalUB4(this.connection.internalName.length);
      }
      else
      {
        this.meg.marshalNULLPTR();
        this.meg.marshalUB4(0L);
      }
      if (this.connection.externalName != null)
      {
        k = 1;
        this.meg.marshalPTR();
        this.meg.marshalUB4(this.connection.externalName.length);
      }
      else
      {
        this.meg.marshalNULLPTR();
        this.meg.marshalUB4(0L);
      }

    }

    if (this.sendTransactionContext) {
      this.meg.marshalB1Array(this.transactionContext);
    }
    if (this.xid != null) {
      this.meg.marshalB1Array(this.xid);
    }
    if (this.xidapp != null) {
      this.meg.marshalUB4(this.xidapp[0]);
    }
    if (this.connection.getTTCVersion() >= 5)
    {
      if (j != 0)
        this.meg.marshalCHR(this.connection.internalName);
      if (k != 0)
        this.meg.marshalCHR(this.connection.externalName);
    }
  }

  byte[] getContext()
  {
    return this.ctx;
  }

  int getApplicationValue()
  {
    return this.applicationValue;
  }

  void readRPA()
    throws IOException, SQLException
  {
    this.applicationValue = ((int)this.meg.unmarshalUB4());

    int i = this.meg.unmarshalUB2();

    this.ctx = this.meg.unmarshalNBytes(i);
  }

  protected OracleConnection getConnectionDuringExceptionHandling()
  {
    return this.connection;
  }
}