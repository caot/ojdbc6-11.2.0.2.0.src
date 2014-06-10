package oracle.jdbc.driver;

import java.io.IOException;
import java.sql.SQLException;
import oracle.jdbc.internal.OracleConnection;

final class T4CTTIOtxen extends T4CTTIfun
{
  static final int OTXCOMIT = 1;
  static final int OTXABORT = 2;
  static final int OTXPREPA = 3;
  static final int OTXFORGT = 4;
  static final int OTXRECOV = 5;
  static final int OTXMLPRE = 6;
  static final int K2CMDprepare = 0;
  static final int K2CMDrqcommit = 1;
  static final int K2CMDcommit = 2;
  static final int K2CMDabort = 3;
  static final int K2CMDrdonly = 4;
  static final int K2CMDforget = 5;
  static final int K2CMDrecovered = 7;
  static final int K2CMDtimeout = 8;
  static final int K2STAidle = 0;
  static final int K2STAcollecting = 1;
  static final int K2STAprepared = 2;
  static final int K2STAcommitted = 3;
  static final int K2STAhabort = 4;
  static final int K2STAhcommit = 5;
  static final int K2STAhdamage = 6;
  static final int K2STAtimeout = 7;
  static final int K2STAinactive = 9;
  static final int K2STAactive = 10;
  static final int K2STAptprepared = 11;
  static final int K2STAptcommitted = 12;
  static final int K2STAmax = 13;
  static final int OTXNDEF_F_CWRBATCH = 1;
  static final int OTXNDEF_F_CWRBATOPT = 2;
  static final int OTXNDEF_F_CWRNOWAIT = 4;
  static final int OTXNDEF_F_CWRWATOPT = 8;
  static final int OTXNDEF_F_CWRBATMSK = 3;
  static final int OTXNDEF_F_CWRWATMSK = 12;
  private int operation;
  private int formatId;
  private int gtridLength;
  private int bqualLength;
  private int timeout;
  private int inState;
  private int txnflg;
  private byte[] transactionContext;
  private byte[] xid = null;
  private int outState = -1;

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  T4CTTIOtxen(T4CConnection paramT4CConnection)
  {
    super(paramT4CConnection, (byte)3);

    setFunCode((short)104);
  }

  void doOTXEN(int paramInt1, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7)
    throws IOException, SQLException
  {
    if ((paramInt1 != 1) && (paramInt1 != 2) && (paramInt1 != 3) && (paramInt1 != 4) && (paramInt1 != 5) && (paramInt1 != 6))
    {
      throw new SQLException("Invalid operation.");
    }this.operation = paramInt1;
    this.formatId = paramInt2;
    this.gtridLength = paramInt3;
    this.bqualLength = paramInt4;
    this.timeout = paramInt5;
    this.inState = paramInt6;
    this.txnflg = paramInt7;

    this.transactionContext = paramArrayOfByte1;
    this.xid = paramArrayOfByte2;

    this.outState = -1;
    doRPC();
  }

  void marshal()
    throws IOException
  {
    int i = this.operation;

    this.meg.marshalSWORD(i);

    if (this.transactionContext == null)
      this.meg.marshalNULLPTR();
    else {
      this.meg.marshalPTR();
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

    this.meg.marshalUWORD(this.timeout);

    this.meg.marshalUB4(this.inState);

    this.meg.marshalPTR();

    if (this.connection.getTTCVersion() >= 4)
    {
      this.meg.marshalUB4(this.txnflg);
    }

    if (this.transactionContext != null) {
      this.meg.marshalB1Array(this.transactionContext);
    }
    if (this.xid != null)
      this.meg.marshalB1Array(this.xid);
  }

  void readRPA()
    throws IOException, SQLException
  {
    this.outState = ((int)this.meg.unmarshalUB4());
  }

  int getOutStateFromServer()
  {
    return this.outState;
  }

  protected OracleConnection getConnectionDuringExceptionHandling()
  {
    return null;
  }
}