package oracle.jdbc.oracore;

import java.io.IOException;
import java.sql.SQLException;
import oracle.jdbc.driver.DatabaseError;
import oracle.jdbc.internal.OracleConnection;

public final class PickleContext
{
  private PickleOutputStream outStream;
  byte[] image;
  int imageOffset;
  private byte[] lengthBuffer;
  static short KOPI20_LN_ELNL = 255;
  static short KOPI20_LN_5BLN = 254;
  static short KOPI20_LN_ATMN = 253;
  static short KOPI20_LN_IEMN = 252;
  static short KOPI20_LN_MAXV = 245;

  static short KOPI20_IF_IS81 = 128;
  static short KOPI20_IF_CMSB = 64;
  static short KOPI20_IF_CLSB = 32;
  static short KOPI20_IF_DEGN = 16;
  static short KOPI20_IF_COLL = 8;
  static short KOPI20_IF_NOPS = 4;
  static short KOPI20_IF_ANY = 2;
  static short KOPI20_IF_NONL = 1;

  static short KOPI20_CF_CMSB = 64;
  static short KOPI20_CF_CLSB = 32;
  static short KOPI20_CF_INDX = 16;
  static short KOPI20_CF_NOLN = 8;

  static short KOPI20_VERSION = 1;
  static final byte KOPUP_INLINE_COLL = 1;
  static final byte KOPUP_TYPEINFO_NONE = 0;
  static final byte KOPUP_TYPEINFO_TOID = 4;
  static final byte KOPUP_TYPEINFO_TOBJN = 8;
  static final byte KOPUP_TYPEINFO_TDS = 12;
  static final byte KOPUP_VSN_PRESENT = 16;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  public PickleContext()
  {
    this.lengthBuffer = new byte[5];
  }

  public PickleContext(byte[] paramArrayOfByte)
  {
    this.lengthBuffer = new byte[5];
    this.image = paramArrayOfByte;
    this.imageOffset = 0;
  }

  public PickleContext(byte[] paramArrayOfByte, long paramLong)
  {
    this.lengthBuffer = new byte[5];
    this.image = paramArrayOfByte;
    this.imageOffset = ((int)paramLong);
  }

  public void initStream(int paramInt)
  {
    this.outStream = new PickleOutputStream(paramInt);
  }

  public void initStream()
  {
    this.outStream = new PickleOutputStream();
  }

  public int lengthInBytes(int paramInt)
  {
    return paramInt <= KOPI20_LN_MAXV ? 1 : 5;
  }

  public int writeElementNull()
    throws SQLException
  {
    this.outStream.write(KOPI20_LN_ELNL);

    return 1;
  }

  public int writeAtomicNull()
    throws SQLException
  {
    this.outStream.write(KOPI20_LN_ATMN);

    return 1;
  }

  public int writeImmediatelyEmbeddedElementNull(byte paramByte)
    throws SQLException
  {
    this.lengthBuffer[0] = ((byte)KOPI20_LN_IEMN);
    this.lengthBuffer[1] = paramByte;

    this.outStream.write(this.lengthBuffer, 0, 2);

    return 2;
  }

  public int writeSB2(int paramInt)
    throws SQLException
  {
    this.lengthBuffer[0] = ((byte)(paramInt >> 8 & 0xFF));
    this.lengthBuffer[1] = ((byte)(paramInt & 0xFF));
    this.outStream.write(this.lengthBuffer, 0, 2);

    return 2;
  }

  public int writeLength(int paramInt)
    throws SQLException
  {
    if (paramInt <= KOPI20_LN_MAXV)
    {
      this.outStream.write((byte)paramInt);

      return 1;
    }

    this.lengthBuffer[0] = ((byte)KOPI20_LN_5BLN);
    this.lengthBuffer[1] = ((byte)(paramInt >> 24));
    paramInt &= 16777215;
    this.lengthBuffer[2] = ((byte)(paramInt >> 16));
    paramInt &= 65535;
    this.lengthBuffer[3] = ((byte)(paramInt >> 8));
    paramInt &= 255;
    this.lengthBuffer[4] = ((byte)paramInt);
    try
    {
      this.outStream.write(this.lengthBuffer);
    }
    catch (IOException localIOException)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localIOException);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    return 5;
  }

  public int writeLength(int paramInt, boolean paramBoolean)
    throws SQLException
  {
    if (!paramBoolean)
      return writeLength(paramInt);
    if (paramInt <= KOPI20_LN_MAXV - 1)
    {
      this.outStream.write((byte)paramInt + 1);

      return 1;
    }

    paramInt += 5;
    this.lengthBuffer[0] = ((byte)KOPI20_LN_5BLN);
    this.lengthBuffer[1] = ((byte)(paramInt >> 24));
    paramInt &= 16777215;
    this.lengthBuffer[2] = ((byte)(paramInt >> 16));
    paramInt &= 65535;
    this.lengthBuffer[3] = ((byte)(paramInt >> 8));
    paramInt &= 255;
    this.lengthBuffer[4] = ((byte)paramInt);
    try
    {
      this.outStream.write(this.lengthBuffer);
    }
    catch (IOException localIOException)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localIOException);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    return 5;
  }

  public byte[] to5bLengthBytes_pctx(int paramInt)
    throws SQLException
  {
    this.lengthBuffer[0] = ((byte)KOPI20_LN_5BLN);
    this.lengthBuffer[1] = ((byte)(paramInt >> 24));
    paramInt &= 16777215;
    this.lengthBuffer[2] = ((byte)(paramInt >> 16));
    paramInt &= 65535;
    this.lengthBuffer[3] = ((byte)(paramInt >> 8));
    paramInt &= 255;
    this.lengthBuffer[4] = ((byte)paramInt);

    return this.lengthBuffer;
  }

  public int writeData(byte paramByte)
    throws SQLException
  {
    this.outStream.write(paramByte);

    return 1;
  }

  public int writeData(byte[] paramArrayOfByte)
    throws SQLException
  {
    try
    {
      this.outStream.write(paramArrayOfByte);
    }
    catch (IOException localIOException)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localIOException);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    return paramArrayOfByte.length;
  }

  public void patchImageLen(int paramInt1, int paramInt2)
    throws SQLException
  {
    byte[] arrayOfByte = to5bLengthBytes_pctx(paramInt2);

    this.outStream.overwrite(paramInt1, arrayOfByte, 0, arrayOfByte.length);
  }

  public int writeImageHeader(boolean paramBoolean)
    throws SQLException
  {
    return writeImageHeader(KOPI20_LN_MAXV + 1, paramBoolean);
  }

  public int writeOpaqueImageHeader(int paramInt)
    throws SQLException
  {
    int i = 2;

    this.lengthBuffer[0] = ((byte)(KOPI20_IF_IS81 | KOPI20_IF_NOPS | KOPI20_IF_NONL));
    this.lengthBuffer[1] = ((byte)KOPI20_VERSION);

    this.outStream.write(this.lengthBuffer, 0, 2);

    i += writeLength(paramInt + 2, true);

    return i;
  }

  public int writeImageHeader(int paramInt, boolean paramBoolean)
    throws SQLException
  {
    int i = 2;

    if (paramBoolean)
      this.lengthBuffer[0] = ((byte)KOPI20_IF_IS81);
    else {
      this.lengthBuffer[0] = ((byte)(KOPI20_IF_IS81 | KOPI20_IF_NOPS));
    }
    this.lengthBuffer[1] = ((byte)KOPI20_VERSION);

    this.outStream.write(this.lengthBuffer, 0, 2);

    i += writeLength(paramInt);

    return i;
  }

  public int writeCollImageHeader(int paramInt1, int paramInt2)
    throws SQLException
  {
    return writeCollImageHeader(KOPI20_LN_MAXV + 1, paramInt1, paramInt2);
  }

  public int writeCollImageHeader(int paramInt1, int paramInt2, int paramInt3)
    throws SQLException
  {
    int i = 5;

    this.lengthBuffer[0] = ((byte)(KOPI20_IF_IS81 | KOPI20_IF_COLL));
    this.lengthBuffer[1] = ((byte)KOPI20_VERSION);

    this.outStream.write(this.lengthBuffer, 0, 2);

    i += writeLength(paramInt1);

    this.lengthBuffer[0] = 1;

    this.lengthBuffer[1] = 17;

    if (paramInt3 > KOPI20_LN_MAXV)
    {
      int tmp77_76 = 0;
      byte[] tmp77_73 = this.lengthBuffer; tmp77_73[tmp77_76] = ((byte)(tmp77_73[tmp77_76] + 5));
      i += 5;

      this.outStream.write(this.lengthBuffer, 0, 2);
      writeLength(paramInt3);
    }
    else
    {
      int tmp113_112 = 0;
      byte[] tmp113_109 = this.lengthBuffer; tmp113_109[tmp113_112] = ((byte)(tmp113_109[tmp113_112] + 2));
      i += 2;

      this.outStream.write(this.lengthBuffer, 0, 2);
      writeSB2(paramInt3);
    }

    this.lengthBuffer[0] = 0;

    this.outStream.write(this.lengthBuffer, 0, 1);

    i += writeLength(paramInt2);

    return i;
  }

  public int writeCollImageHeader(byte[] paramArrayOfByte)
    throws SQLException
  {
    return writeCollImageHeader(KOPI20_LN_MAXV + 1, paramArrayOfByte);
  }

  public int writeCollImageHeader(int paramInt, byte[] paramArrayOfByte)
    throws SQLException
  {
    int i = paramArrayOfByte.length;

    int j = 3 + i;

    this.lengthBuffer[0] = ((byte)(KOPI20_IF_IS81 | KOPI20_IF_DEGN));
    this.lengthBuffer[1] = ((byte)KOPI20_VERSION);

    this.outStream.write(this.lengthBuffer, 0, 2);

    j += writeLength(paramInt);
    j += writeLength(i + 1);

    this.lengthBuffer[0] = 0;

    this.outStream.write(this.lengthBuffer, 0, 1);
    this.outStream.write(paramArrayOfByte, 0, i);

    return j;
  }

  public byte[] stream2Bytes()
    throws SQLException
  {
    return this.outStream.toByteArray();
  }

  public byte readByte()
    throws SQLException
  {
    try
    {
      return this.image[this.imageOffset];
    }
    finally
    {
      this.imageOffset += 1;
    }
  }

  public boolean readAndCheckVersion()
    throws SQLException
  {
    try
    {
      return (this.image[this.imageOffset] & 0xFF) <= KOPI20_VERSION;
    }
    finally
    {
      this.imageOffset += 1;
    }
  }

  public int readLength()
    throws SQLException
  {
    int i = this.image[this.imageOffset] & 0xFF;

    if (i > KOPI20_LN_MAXV)
    {
      if (i == KOPI20_LN_ELNL)
      {
        SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 1, "Invalid null flag read");
        localSQLException.fillInStackTrace();
        throw localSQLException;
      }

      i = (((this.image[(this.imageOffset + 1)] & 0xFF) * 256 + (this.image[(this.imageOffset + 2)] & 0xFF)) * 256 + (this.image[(this.imageOffset + 3)] & 0xFF)) * 256 + (this.image[(this.imageOffset + 4)] & 0xFF);

      this.imageOffset += 5;
    }
    else
    {
      this.imageOffset += 1;
    }return i;
  }

  public void skipLength()
    throws SQLException
  {
    int i = this.image[this.imageOffset] & 0xFF;

    if (i > KOPI20_LN_MAXV)
      this.imageOffset += 5;
    else
      this.imageOffset += 1;
  }

  public int readRestOfLength(byte paramByte)
    throws SQLException
  {
    if ((paramByte & 0xFF) != KOPI20_LN_5BLN) {
      return paramByte & 0xFF;
    }
    try
    {
      return (((this.image[this.imageOffset] & 0xFF) * 256 + (this.image[(this.imageOffset + 1)] & 0xFF)) * 256 + (this.image[(this.imageOffset + 2)] & 0xFF)) * 256 + (this.image[(this.imageOffset + 3)] & 0xFF);
    }
    finally
    {
      this.imageOffset += 4;
    }
  }

  public void skipRestOfLength(byte paramByte)
    throws SQLException
  {
    if ((paramByte & 0xFF) > KOPI20_LN_MAXV)
    {
      if ((paramByte & 0xFF) == KOPI20_LN_5BLN) {
        this.imageOffset += 4;
      }
      else {
        SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 1, "Invalid first length byte");
        localSQLException.fillInStackTrace();
        throw localSQLException;
      }
    }
  }

  public int readLength(boolean paramBoolean)
    throws SQLException
  {
    int i = this.image[this.imageOffset] & 0xFF;

    if (i > KOPI20_LN_MAXV)
    {
      i = (((this.image[(this.imageOffset + 1)] & 0xFF) * 256 + (this.image[(this.imageOffset + 2)] & 0xFF)) * 256 + (this.image[(this.imageOffset + 3)] & 0xFF)) * 256 + (this.image[(this.imageOffset + 4)] & 0xFF);

      if (paramBoolean) {
        i -= 5;
      }
      this.imageOffset += 5;
    }
    else
    {
      if (paramBoolean) {
        i--;
      }
      this.imageOffset += 1;
    }

    return i;
  }

  public byte[] readPrefixSegment()
    throws SQLException
  {
    byte[] arrayOfByte = new byte[readLength()];

    System.arraycopy(this.image, this.imageOffset, arrayOfByte, 0, arrayOfByte.length);

    this.imageOffset += arrayOfByte.length;
    return arrayOfByte;
  }

  public byte[] readDataValue()
    throws SQLException
  {
    int i = this.image[this.imageOffset] & 0xFF;

    if (i == KOPI20_LN_ELNL)
    {
      this.imageOffset += 1;

      return null;
    }

    if (i > KOPI20_LN_MAXV)
    {
      i = (((this.image[(this.imageOffset + 1)] & 0xFF) * 256 + (this.image[(this.imageOffset + 2)] & 0xFF)) * 256 + (this.image[(this.imageOffset + 3)] & 0xFF)) * 256 + (this.image[(this.imageOffset + 4)] & 0xFF);

      this.imageOffset += 5;
    }
    else {
      this.imageOffset += 1;
    }

    byte[] arrayOfByte = new byte[i];

    System.arraycopy(this.image, this.imageOffset, arrayOfByte, 0, arrayOfByte.length);

    this.imageOffset += arrayOfByte.length;

    return arrayOfByte;
  }

  public byte[] readBytes(int paramInt)
    throws SQLException
  {
    byte[] arrayOfByte = new byte[paramInt];

    System.arraycopy(this.image, this.imageOffset, arrayOfByte, 0, paramInt);

    this.imageOffset += paramInt;
    return arrayOfByte;
  }

  public byte[] read1ByteDataValue()
    throws SQLException
  {
    if ((this.image[this.imageOffset] & 0xFF) == KOPI20_LN_ELNL) {
      return null;
    }

    byte[] arrayOfByte = new byte[this.image[this.imageOffset] & 0xFF];

    System.arraycopy(this.image, this.imageOffset + 1, arrayOfByte, 0, arrayOfByte.length);

    this.imageOffset += arrayOfByte.length + 1;
    return arrayOfByte;
  }

  public byte[] readDataValue(byte paramByte)
    throws SQLException
  {
    byte[] arrayOfByte = new byte[readRestOfLength(paramByte)];

    System.arraycopy(this.image, this.imageOffset, arrayOfByte, 0, arrayOfByte.length);

    this.imageOffset += arrayOfByte.length;

    return arrayOfByte;
  }

  public byte[] readDataValue(int paramInt)
    throws SQLException
  {
    byte[] arrayOfByte = new byte[paramInt];

    System.arraycopy(this.image, this.imageOffset, arrayOfByte, 0, paramInt);

    this.imageOffset += paramInt;
    return arrayOfByte;
  }

  public long readUB4()
    throws SQLException
  {
    long l = this.image[(this.imageOffset++)] << 24 & 0xFF000000 | this.image[(this.imageOffset++)] << 16 & 0xFF0000 | this.image[(this.imageOffset++)] << 8 & 0xFF00 | this.image[(this.imageOffset++)] & 0xFF;

    return l;
  }

  public int readUB2()
    throws SQLException
  {
    int i = this.image[(this.imageOffset++)] << 8 & 0xFF00 | this.image[(this.imageOffset++)] & 0xFF;

    return i;
  }

  public void skipDataValue()
    throws SQLException
  {
    if ((this.image[this.imageOffset] & 0xFF) == KOPI20_LN_ELNL)
      this.imageOffset += 1;
    else
      skipBytes(readLength());
  }

  public void skipDataValue(byte paramByte)
    throws SQLException
  {
    skipBytes(readRestOfLength(paramByte));
  }

  public void skipBytes(int paramInt)
    throws SQLException
  {
    if (paramInt > 0)
      this.imageOffset += paramInt;
  }

  public int offset()
    throws SQLException
  {
    if (this.outStream != null) {
      return this.outStream.offset();
    }
    return this.imageOffset;
  }

  public int absoluteOffset()
    throws SQLException
  {
    return this.imageOffset;
  }

  public void skipTo(long paramLong)
    throws SQLException
  {
    if (paramLong > this.imageOffset)
      this.imageOffset = ((int)paramLong);
  }

  public byte[] image()
    throws SQLException
  {
    return this.image;
  }

  public static boolean is81format(byte paramByte)
    throws SQLException
  {
    return (paramByte & 0xFF & KOPI20_IF_IS81) != 0;
  }

  public static boolean isCollectionImage_pctx(byte paramByte)
    throws SQLException
  {
    return (paramByte & 0xFF & KOPI20_IF_COLL) != 0;
  }

  public static boolean isDegenerateImage_pctx(byte paramByte)
    throws SQLException
  {
    return (paramByte & 0xFF & KOPI20_IF_DEGN) != 0;
  }

  public static boolean hasPrefix(byte paramByte)
    throws SQLException
  {
    return (paramByte & 0xFF & KOPI20_IF_NOPS) == 0;
  }

  public static boolean isAtomicNull(byte paramByte)
    throws SQLException
  {
    return (paramByte & 0xFF) == KOPI20_LN_ATMN;
  }

  public static boolean isImmediatelyEmbeddedNull(byte paramByte)
    throws SQLException
  {
    return (paramByte & 0xFF) == KOPI20_LN_IEMN;
  }

  public static boolean isElementNull(byte paramByte)
    throws SQLException
  {
    return (paramByte & 0xFF) == KOPI20_LN_ELNL;
  }

  protected OracleConnection getConnectionDuringExceptionHandling()
  {
    return null;
  }
}