package oracle.jdbc.driver;

import java.io.IOException;
import java.sql.SQLException;
import oracle.jdbc.internal.OracleConnection;

class T4CTTIoer extends T4CTTIMsg
{
  final int MAXERRBUF = 512;
  long curRowNumber;
  int retCode;
  int arrayElemWError;
  int arrayElemErrno;
  int currCursorID;
  short errorPosition;
  short sqlType;
  byte oerFatal;
  short flags;
  short userCursorOpt;
  short upiParam;
  short warningFlag;
  int osError;
  short stmtNumber;
  short callNumber;
  int pad1;
  long successIters;
  int partitionId;
  int tableId;
  int slotNumber;
  long rba;
  long blockNumber;
  int warnLength = 0;
  int warnFlag = 0;

  int[] errorLength = new int[1];
  byte[] errorMsg;
  static final int ORA1403 = 1403;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  T4CTTIoer(T4CConnection paramT4CConnection)
  {
    super(paramT4CConnection, (byte)4);
  }

  void init()
  {
    this.retCode = 0;
    this.errorMsg = null;
  }

  int unmarshal()
    throws IOException, SQLException
  {
    if (this.connection.getTTCVersion() >= 3)
    {
      short s = (short)this.meg.unmarshalUB2();

      this.connection.endToEndECIDSequenceNumber = s;
    }

    this.curRowNumber = this.meg.unmarshalUB4();
    this.retCode = this.meg.unmarshalUB2();
    this.arrayElemWError = this.meg.unmarshalUB2();
    this.arrayElemErrno = this.meg.unmarshalUB2();
    this.currCursorID = this.meg.unmarshalUB2();
    this.errorPosition = this.meg.unmarshalSB2();
    this.sqlType = this.meg.unmarshalUB1();
    this.oerFatal = this.meg.unmarshalSB1();
    this.flags = this.meg.unmarshalSB2();
    this.userCursorOpt = this.meg.unmarshalSB2();
    this.upiParam = this.meg.unmarshalUB1();
    this.warningFlag = this.meg.unmarshalUB1();

    this.rba = this.meg.unmarshalUB4();
    this.partitionId = this.meg.unmarshalUB2();
    this.tableId = this.meg.unmarshalUB1();
    this.blockNumber = this.meg.unmarshalUB4();
    this.slotNumber = this.meg.unmarshalUB2();

    this.osError = this.meg.unmarshalSWORD();
    this.stmtNumber = this.meg.unmarshalUB1();
    this.callNumber = this.meg.unmarshalUB1();
    this.pad1 = this.meg.unmarshalUB2();
    this.successIters = this.meg.unmarshalUB4();

    byte[] arrayOfByte = this.meg.unmarshalDALC();

    int i = this.meg.unmarshalUB2();
    for (int j = 0; j < i; j++)
      this.meg.unmarshalUB2();
    int j = (int)this.meg.unmarshalUB4();
    int k;
    for (k = 0; k < j; k++)
      this.meg.unmarshalUB4();
    k = this.meg.unmarshalUB2();

    if (this.retCode != 0)
    {
      this.errorMsg = this.meg.unmarshalCLRforREFS();
      this.errorLength[0] = this.errorMsg.length;
    }

    return this.currCursorID;
  }

  void unmarshalWarning()
    throws IOException, SQLException
  {
    this.retCode = this.meg.unmarshalUB2();
    this.warnLength = this.meg.unmarshalUB2();
    this.warnFlag = this.meg.unmarshalUB2();

    if ((this.retCode != 0) && (this.warnLength > 0))
    {
      this.errorMsg = this.meg.unmarshalCHR(this.warnLength);
      this.errorLength[0] = this.warnLength;
    }
  }

  void print()
    throws SQLException
  {
    if (this.retCode == 0)
    {
      if (this.warnFlag == 0);
    }
  }

  void processError()
    throws SQLException
  {
    processError(true);
  }

  void processError(boolean paramBoolean)
    throws SQLException
  {
    processError(paramBoolean, null);
  }

  void processError(OracleStatement paramOracleStatement)
    throws SQLException
  {
    processError(true, paramOracleStatement);
  }

  void processError(boolean paramBoolean, OracleStatement paramOracleStatement)
    throws SQLException
  {
    if (paramOracleStatement != null) {
      paramOracleStatement.numberOfExecutedElementsInBatch = ((int)this.successIters);
    }
    if (this.retCode != 0)
    {
      switch (this.retCode)
      {
      case 28:
      case 600:
      case 1012:
      case 3113:
      case 3114:
        this.connection.internalClose();
      }

      if (paramBoolean)
      {
        SQLException localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), this.meg.conv.CharBytesToString(this.errorMsg, this.errorLength[0], true), this.retCode);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      return;
    }

    if (!paramBoolean) {
      return;
    }

    if ((this.warningFlag & 0x1) == 1)
    {
      int i = this.warningFlag & 0xFFFFFFFE;

      if (((i & 0x20) == 32) || ((i & 0x4) == 4))
      {
        SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 110);
        localSQLException2.fillInStackTrace();
        throw localSQLException2;
      }

    }

    if ((this.connection != null) && (this.connection.plsqlCompilerWarnings))
    {
      if ((this.flags & 0x4) == 4)
        paramOracleStatement.foundPlsqlCompilerWarning();
    }
  }

  void processWarning()
    throws SQLException
  {
    if (this.retCode != 0)
    {
      throw DatabaseError.newSqlWarning(this.meg.conv.CharBytesToString(this.errorMsg, this.errorLength[0], true), this.retCode);
    }
  }

  int getCurRowNumber()
    throws SQLException
  {
    return (int)this.curRowNumber;
  }

  int getRetCode()
  {
    return this.retCode;
  }

  protected OracleConnection getConnectionDuringExceptionHandling()
  {
    return this.connection;
  }
}