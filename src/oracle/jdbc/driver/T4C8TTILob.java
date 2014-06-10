package oracle.jdbc.driver;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import oracle.sql.Datum;

abstract class T4C8TTILob extends T4CTTIfun
{
  static final int KPLOB_READ = 2;
  static final int KPLOB_WRITE = 64;
  static final int KPLOB_WRITE_APPEND = 8192;
  static final int KPLOB_PAGE_SIZE = 16384;
  static final int KPLOB_FILE_OPEN = 256;
  static final int KPLOB_FILE_ISOPEN = 1024;
  static final int KPLOB_FILE_EXISTS = 2048;
  static final int KPLOB_FILE_CLOSE = 512;
  static final int KPLOB_OPEN = 32768;
  static final int KPLOB_CLOSE = 65536;
  static final int KPLOB_ISOPEN = 69632;
  static final int KPLOB_TMP_CREATE = 272;
  static final int KPLOB_TMP_FREE = 273;
  static final int KPLOB_GET_LEN = 1;
  static final int KPLOB_TRIM = 32;
  static final int KOKL_ORDONLY = 1;
  static final int KOKL_ORDWR = 2;
  static final int KOLF_ORDONLY = 11;
  static final int DTYCLOB = 112;
  static final int DTYBLOB = 113;
  byte[] sourceLobLocator = null;
  byte[] destinationLobLocator = null;
  long sourceOffset = 0L;
  long destinationOffset = 0L;
  int destinationLength = 0;
  short characterSet = 0;
  long lobamt = 0L;
  boolean lobnull = false;
  long lobops = 0L;
  int[] lobscn = null;
  int lobscnl = 0;

  boolean nullO2U = false;

  boolean sendLobamt = false;

  byte[] inBuffer = null;
  long inBufferOffset;
  long inBufferNumBytes;
  byte[] outBuffer = null;
  int offsetInOutBuffer = 0;
  int rowsProcessed = 0;
  long lobBytesRead = 0L;
  boolean varWidthChar = false;
  boolean littleEndianClob = false;
  T4C8TTILobd lobd = null;

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  T4C8TTILob(T4CConnection paramT4CConnection)
  {
    super(paramT4CConnection, (byte)3);

    setFunCode((short)96);

    this.lobd = new T4C8TTILobd(paramT4CConnection);
  }

  long read(byte[] paramArrayOfByte1, long paramLong1, long paramLong2, byte[] paramArrayOfByte2, int paramInt)
    throws SQLException, IOException
  {
    initializeLobdef();

    this.lobops = 2L;
    this.sourceLobLocator = paramArrayOfByte1;
    this.sourceOffset = paramLong1;
    this.lobamt = paramLong2;
    this.sendLobamt = true;
    this.outBuffer = paramArrayOfByte2;
    this.offsetInOutBuffer = paramInt;
    doRPC();

    return this.lobBytesRead;
  }

  long write(byte[] paramArrayOfByte1, long paramLong1, byte[] paramArrayOfByte2, long paramLong2, long paramLong3)
    throws SQLException, IOException
  {
    long l = 0L;

    initializeLobdef();

    this.lobops = 64L;
    this.sourceLobLocator = paramArrayOfByte1;
    this.sourceOffset = paramLong1;
    this.lobamt = paramLong3;
    this.sendLobamt = true;
    this.inBuffer = paramArrayOfByte2;
    this.inBufferOffset = paramLong2;
    this.inBufferNumBytes = paramLong3;

    doRPC();

    l = this.lobamt;

    return l;
  }

  long getLength(byte[] paramArrayOfByte)
    throws SQLException, IOException
  {
    long l = 0L;

    initializeLobdef();

    this.lobops = 1L;
    this.sourceLobLocator = paramArrayOfByte;

    this.sendLobamt = true;

    doRPC();
    l = this.lobamt;

    return l;
  }

  long getChunkSize(byte[] paramArrayOfByte)
    throws SQLException, IOException
  {
    long l = 0L;

    initializeLobdef();

    this.lobops = 16384L;
    this.sourceLobLocator = paramArrayOfByte;

    this.sendLobamt = true;

    doRPC();

    l = this.lobamt;

    return l;
  }

  long trim(byte[] paramArrayOfByte, long paramLong)
    throws SQLException, IOException
  {
    long l = 0L;

    initializeLobdef();

    this.lobops = 32L;
    this.sourceLobLocator = paramArrayOfByte;
    this.lobamt = paramLong;
    this.sendLobamt = true;

    doRPC();

    l = this.lobamt;

    return l;
  }

  abstract Datum createTemporaryLob(Connection paramConnection, boolean paramBoolean, int paramInt)
    throws SQLException, IOException;

  void freeTemporaryLob(byte[] paramArrayOfByte)
    throws SQLException, IOException
  {
    initializeLobdef();

    this.lobops = 273L;
    this.sourceLobLocator = paramArrayOfByte;

    doRPC();
  }

  abstract boolean open(byte[] paramArrayOfByte, int paramInt)
    throws SQLException, IOException;

  boolean _open(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws SQLException, IOException
  {
    boolean bool = false;

    if (((paramArrayOfByte[7] & 0x1) == 1) || ((paramArrayOfByte[4] & 0x40) == 64))
    {
      if ((paramArrayOfByte[7] & 0x8) == 8)
      {
        SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 445);
        localSQLException.fillInStackTrace();
        throw localSQLException;
      }
      byte[] tmp60_57 = paramArrayOfByte; tmp60_57[7] = ((byte)(tmp60_57[7] | 0x8));

      if (paramInt1 == 2)
      {
        byte[] tmp75_72 = paramArrayOfByte; tmp75_72[7] = ((byte)(tmp75_72[7] | 0x10));
      }
      bool = true;
    }
    else
    {
      initializeLobdef();

      this.sourceLobLocator = paramArrayOfByte;
      this.lobops = paramInt2;
      this.lobamt = paramInt1;
      this.sendLobamt = true;

      doRPC();

      if (this.lobamt != 0L) {
        bool = true;
      }
    }
    return bool;
  }

  abstract boolean close(byte[] paramArrayOfByte)
    throws SQLException, IOException;

  boolean _close(byte[] paramArrayOfByte, int paramInt)
    throws SQLException, IOException
  {
    boolean bool = true;

    if (((paramArrayOfByte[7] & 0x1) == 1) || ((paramArrayOfByte[4] & 0x40) == 64))
    {
      if ((paramArrayOfByte[7] & 0x8) != 8)
      {
        SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 446);
        localSQLException.fillInStackTrace();
        throw localSQLException;
      }
      byte[] tmp59_56 = paramArrayOfByte; tmp59_56[7] = ((byte)(tmp59_56[7] & 0xFFFFFFE7));
    }
    else
    {
      initializeLobdef();

      this.sourceLobLocator = paramArrayOfByte;
      this.lobops = paramInt;

      doRPC();
    }

    return bool;
  }

  abstract boolean isOpen(byte[] paramArrayOfByte)
    throws SQLException, IOException;

  boolean _isOpen(byte[] paramArrayOfByte, int paramInt)
    throws SQLException, IOException
  {
    boolean bool = false;

    if (((paramArrayOfByte[7] & 0x1) == 1) || ((paramArrayOfByte[4] & 0x40) == 64))
    {
      if ((paramArrayOfByte[7] & 0x8) == 8) {
        bool = true;
      }

    }
    else
    {
      initializeLobdef();

      this.sourceLobLocator = paramArrayOfByte;
      this.lobops = paramInt;
      this.nullO2U = true;

      doRPC();

      bool = this.lobnull;
    }

    return bool;
  }

  void initializeLobdef()
  {
    this.sourceLobLocator = null;
    this.destinationLobLocator = null;
    this.sourceOffset = 0L;
    this.destinationOffset = 0L;
    this.destinationLength = 0;
    this.characterSet = 0;
    this.lobamt = 0L;
    this.lobnull = false;
    this.lobops = 0L;
    this.lobscn = null;
    this.lobscnl = 0;
    this.inBuffer = null;
    this.outBuffer = null;
    this.nullO2U = false;
    this.sendLobamt = false;
    this.varWidthChar = false;
    this.littleEndianClob = false;
    this.lobBytesRead = 0L;
  }

  void marshal()
    throws IOException
  {
    int i = 0;

    if (this.sourceLobLocator != null)
    {
      i = this.sourceLobLocator.length;

      this.meg.marshalPTR();
    }
    else {
      this.meg.marshalNULLPTR();
    }

    this.meg.marshalSB4(i);

    if (this.destinationLobLocator != null)
    {
      this.destinationLength = this.destinationLobLocator.length;

      this.meg.marshalPTR();
    }
    else {
      this.meg.marshalNULLPTR();
    }

    this.meg.marshalSB4(this.destinationLength);

    if (this.connection.getTTCVersion() >= 3)
      this.meg.marshalUB4(0L);
    else {
      this.meg.marshalUB4(this.sourceOffset);
    }

    if (this.connection.getTTCVersion() >= 3)
      this.meg.marshalUB4(0L);
    else {
      this.meg.marshalUB4(this.destinationOffset);
    }

    if (this.characterSet != 0)
      this.meg.marshalPTR();
    else {
      this.meg.marshalNULLPTR();
    }

    if ((this.sendLobamt == true) && (this.connection.getTTCVersion() < 3))
      this.meg.marshalPTR();
    else {
      this.meg.marshalNULLPTR();
    }

    if (this.nullO2U == true)
      this.meg.marshalPTR();
    else {
      this.meg.marshalNULLPTR();
    }

    this.meg.marshalUB4(this.lobops);

    if (this.lobscnl != 0)
      this.meg.marshalPTR();
    else {
      this.meg.marshalNULLPTR();
    }

    this.meg.marshalSB4(this.lobscnl);

    if (this.connection.getTTCVersion() >= 3)
    {
      this.meg.marshalSB8(this.sourceOffset);
      this.meg.marshalSB8(this.destinationOffset);

      if (this.sendLobamt == true)
        this.meg.marshalPTR();
      else
        this.meg.marshalNULLPTR();
      if (this.connection.getTTCVersion() >= 4)
      {
        this.meg.marshalNULLPTR();
        this.meg.marshalSWORD(0);
        this.meg.marshalNULLPTR();
        this.meg.marshalSWORD(0);
        this.meg.marshalNULLPTR();
        this.meg.marshalSWORD(0);
      }

    }

    if (this.sourceLobLocator != null)
    {
      this.meg.marshalB1Array(this.sourceLobLocator);
    }

    if (this.destinationLobLocator != null)
    {
      this.meg.marshalB1Array(this.destinationLobLocator);
    }

    if (this.characterSet != 0)
    {
      this.meg.marshalUB2(this.characterSet);
    }

    if ((this.sendLobamt == true) && (this.connection.getTTCVersion() < 3))
    {
      this.meg.marshalUB4(this.lobamt);
    }

    if (this.lobscnl != 0)
    {
      for (int j = 0; j < this.lobscnl; j++)
      {
        this.meg.marshalUB4(this.lobscn[j]);
      }
    }

    if ((this.sendLobamt == true) && (this.connection.getTTCVersion() >= 3))
    {
      this.meg.marshalSB8(this.lobamt);
    }
    if (this.lobops == 64L)
      marshalData();
  }

  void marshalData()
    throws IOException
  {
    boolean bool = this.connection.isZeroCopyIOEnabled() & (this.sourceLobLocator[7] & 0xFFFFFF80) != 0;

    if ((this.connection.versionNumber < 10101) && (this.varWidthChar))
      this.lobd.marshalClobUB2_For9iDB(this.inBuffer, this.inBufferOffset, this.inBufferNumBytes);
    else
      this.lobd.marshalLobData(this.inBuffer, this.inBufferOffset, this.inBufferNumBytes, bool);
  }

  void readLOBD()
    throws IOException, SQLException
  {
    boolean bool = this.connection.isZeroCopyIOEnabled() & (this.sourceLobLocator[7] & 0xFFFFFF80) != 0;

    if ((this.connection.versionNumber < 10101) && (this.varWidthChar))
      this.lobBytesRead = this.lobd.unmarshalClobUB2_For9iDB(this.outBuffer, this.offsetInOutBuffer);
    else
      this.lobBytesRead = this.lobd.unmarshalLobData(this.outBuffer, this.offsetInOutBuffer, bool);
  }

  void processError()
    throws SQLException
  {
    this.rowsProcessed = this.oer.getCurRowNumber();

    if (this.oer.getRetCode() != 1403)
    {
      this.oer.processError();
    }
  }

  void readRPA()
    throws SQLException, IOException
  {
    int i;
    if (this.sourceLobLocator != null)
    {
      i = this.sourceLobLocator.length;

      this.meg.getNBytes(this.sourceLobLocator, 0, i);
    }

    if (this.destinationLobLocator != null)
    {
      i = this.meg.unmarshalSB2();

      this.destinationLobLocator = this.meg.unmarshalNBytes(i);
    }

    if (this.characterSet != 0)
    {
      this.characterSet = this.meg.unmarshalSB2();
    }

    if (this.sendLobamt == true)
    {
      if (this.connection.getTTCVersion() >= 3)
        this.lobamt = this.meg.unmarshalSB8();
      else {
        this.lobamt = this.meg.unmarshalUB4();
      }
    }

    if (this.nullO2U == true)
    {
      i = this.meg.unmarshalSB2();

      if (i != 0)
        this.lobnull = true;
    }
  }
}