package oracle.jdbc.driver;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import oracle.jdbc.OracleConnection;
import oracle.sql.CLOB;
import oracle.sql.CharacterSet;
import oracle.sql.Datum;
import oracle.sql.NCLOB;

final class T4C8TTIClob extends T4C8TTILob
{
  int[] nBytes;
  byte[] myBufferForReuse = null;

  boolean varChar = false;

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  T4C8TTIClob(T4CConnection paramT4CConnection)
  {
    super(paramT4CConnection);

    this.nBytes = new int[1];
  }

  long read(byte[] paramArrayOfByte, long paramLong1, long paramLong2, boolean paramBoolean, char[] paramArrayOfChar, int paramInt)
    throws SQLException, IOException
  {
    long l1 = 0L;

    byte[] arrayOfByte = null;

    initializeLobdef();

    if ((paramArrayOfByte[6] & 0x80) == 128) {
      this.varWidthChar = true;
    }

    int i = 0;
    if (this.varWidthChar == true)
      i = (int)paramLong2 * 2;
    else {
      i = (int)paramLong2 * 3;
    }

    if ((this.myBufferForReuse == null) || (i > this.myBufferForReuse.length))
    {
      arrayOfByte = new byte[i];
    }
    else arrayOfByte = this.myBufferForReuse;

    if ((paramArrayOfByte[7] & 0x40) > 0) {
      this.littleEndianClob = true;
    }

    this.lobops = 2L;
    this.sourceLobLocator = paramArrayOfByte;
    this.sourceOffset = paramLong1;
    this.lobamt = paramLong2;
    this.sendLobamt = true;
    this.outBuffer = arrayOfByte;

    doRPC();

    long l2 = this.lobamt;

    long l3 = 0L;

    if (this.varWidthChar == true)
    {
      if (this.connection.versionNumber < 10101)
      {
        DBConversion.ucs2BytesToJavaChars(this.outBuffer, (int)this.lobBytesRead, paramArrayOfChar);
      }
      else if (this.littleEndianClob)
      {
        CharacterSet.convertAL16UTF16LEBytesToJavaChars(this.outBuffer, 0, paramArrayOfChar, paramInt, (int)this.lobBytesRead, true);
      }
      else
      {
        CharacterSet.convertAL16UTF16BytesToJavaChars(this.outBuffer, 0, paramArrayOfChar, paramInt, (int)this.lobBytesRead, true);
      }

    }
    else if (!paramBoolean)
    {
      this.nBytes[0] = ((int)this.lobBytesRead);

      this.meg.conv.CHARBytesToJavaChars(this.outBuffer, 0, paramArrayOfChar, paramInt, this.nBytes, paramArrayOfChar.length);
    }
    else
    {
      this.nBytes[0] = ((int)this.lobBytesRead);

      this.meg.conv.NCHARBytesToJavaChars(this.outBuffer, 0, paramArrayOfChar, paramInt, this.nBytes, paramArrayOfChar.length);
    }

    return l2;
  }

  long write(byte[] paramArrayOfByte, long paramLong1, boolean paramBoolean, char[] paramArrayOfChar, long paramLong2, long paramLong3)
    throws SQLException, IOException
  {
    if ((paramArrayOfByte[6] & 0x80) == 128)
      this.varChar = true;
    else {
      this.varChar = false;
    }
    if ((paramArrayOfByte[7] & 0x40) == 64) {
      this.littleEndianClob = true;
    }

    long l1 = 0L;
    byte[] arrayOfByte = null;

    if (this.varChar == true)
    {
      arrayOfByte = new byte[(int)paramLong3 * 2];

      if (this.connection.versionNumber < 10101)
      {
        DBConversion.javaCharsToUcs2Bytes(paramArrayOfChar, (int)paramLong2, arrayOfByte, 0, (int)paramLong3);
      }
      else if (this.littleEndianClob)
      {
        CharacterSet.convertJavaCharsToAL16UTF16LEBytes(paramArrayOfChar, (int)paramLong2, arrayOfByte, 0, (int)paramLong3);
      }
      else
      {
        CharacterSet.convertJavaCharsToAL16UTF16Bytes(paramArrayOfChar, (int)paramLong2, arrayOfByte, 0, (int)paramLong3);
      }

    }
    else
    {
      arrayOfByte = new byte[(int)paramLong3 * 3];

      if (!paramBoolean)
      {
        l1 = this.meg.conv.javaCharsToCHARBytes(paramArrayOfChar, (int)paramLong2, arrayOfByte, 0, (int)paramLong3);
      }
      else
      {
        l1 = this.meg.conv.javaCharsToNCHARBytes(paramArrayOfChar, (int)paramLong2, arrayOfByte, 0, (int)paramLong3);
      }

    }

    initializeLobdef();

    this.lobops = 64L;
    this.sourceLobLocator = paramArrayOfByte;
    this.sourceOffset = paramLong1;
    this.lobamt = paramLong3;
    this.sendLobamt = true;
    this.inBuffer = arrayOfByte;
    this.inBufferOffset = 0L;

    if (this.varChar == true)
    {
      if (this.connection.versionNumber < 10101)
        this.inBufferNumBytes = paramLong3;
      else {
        this.inBufferNumBytes = (paramLong3 * 2L);
      }

    }
    else
    {
      this.inBufferNumBytes = l1;
    }
    doRPC();

    long l2 = this.lobamt;

    return l2;
  }

  Datum createTemporaryLob(Connection paramConnection, boolean paramBoolean, int paramInt)
    throws SQLException, IOException
  {
    return createTemporaryLob(paramConnection, paramBoolean, paramInt, (short)1);
  }

  Datum createTemporaryLob(Connection paramConnection, boolean paramBoolean, int paramInt, short paramShort)
    throws SQLException, IOException
  {
    if (paramInt == 12)
    {
      localObject = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 158);
      ((SQLException)localObject).fillInStackTrace();
      throw ((Throwable)localObject);
    }

    Object localObject = null;

    initializeLobdef();

    this.lobops = 272L;
    this.sourceLobLocator = new byte[86];
    this.sourceLobLocator[1] = 84;

    this.lobamt = 10L;
    this.sendLobamt = true;

    if (paramShort == 1)
      this.sourceOffset = 1L;
    else {
      this.sourceOffset = 2L;
    }

    this.destinationOffset = 112L;

    this.destinationLength = paramInt;

    this.nullO2U = true;

    this.characterSet = (paramShort == 2 ? this.meg.conv.getNCharSetId() : this.meg.conv.getServerCharSetId());

    if (this.connection.versionNumber >= 9000)
    {
      this.lobscn = new int[1];
      this.lobscn[0] = (paramBoolean ? 1 : 0);
      this.lobscnl = 1;
    }

    doRPC();

    if (this.sourceLobLocator != null)
    {
      if (paramShort == 1) {
        localObject = new CLOB((OracleConnection)paramConnection, this.sourceLobLocator);
      }
      else
      {
        localObject = new NCLOB((OracleConnection)paramConnection, this.sourceLobLocator);
      }
    }

    return localObject;
  }

  boolean open(byte[] paramArrayOfByte, int paramInt)
    throws SQLException, IOException
  {
    boolean bool = false;

    int i = 2;

    if (paramInt == 0) {
      i = 1;
    }
    bool = _open(paramArrayOfByte, i, 32768);

    return bool;
  }

  boolean close(byte[] paramArrayOfByte)
    throws SQLException, IOException
  {
    boolean bool = false;

    bool = _close(paramArrayOfByte, 65536);

    return bool;
  }

  boolean isOpen(byte[] paramArrayOfByte)
    throws SQLException, IOException
  {
    boolean bool = false;

    bool = _isOpen(paramArrayOfByte, 69632);

    return bool;
  }
}