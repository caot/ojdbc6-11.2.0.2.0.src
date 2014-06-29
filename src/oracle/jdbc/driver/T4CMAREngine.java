package oracle.jdbc.driver;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;
import oracle.jdbc.internal.OracleConnection;
import oracle.net.ns.BreakNetException;
import oracle.net.ns.Communication;
import oracle.net.ns.NetException;
import oracle.net.ns.NetInputStream;
import oracle.net.ns.NetOutputStream;

class T4CMAREngine
{
  static final int TTCC_MXL = 252;
  static final int TTCC_ESC = 253;
  static final int TTCC_LNG = 254;
  static final int TTCC_ERR = 255;
  static final int TTCC_MXIN = 64;
  static final byte TTCLXMULTI = 1;
  static final byte TTCLXMCONV = 2;
  T4CTypeRep types;
  Communication net;
  DBConversion conv;
  byte proSvrVer;
  T4CSocketInputStreamWrapper inStream;
  T4CSocketOutputStreamWrapper outStream;
  final byte[] ignored = new byte[255];
  final byte[] tmpBuffer1 = new byte[1];
  final byte[] tmpBuffer2 = new byte[2];
  final byte[] tmpBuffer3 = new byte[3];
  final byte[] tmpBuffer4 = new byte[4];
  final byte[] tmpBuffer5 = new byte[5];
  final byte[] tmpBuffer6 = new byte[6];
  final byte[] tmpBuffer7 = new byte[7];
  final byte[] tmpBuffer8 = new byte[8];
  final int[] retLen = new int[1];

  AtomicReference<OracleConnection> connForException = new AtomicReference();

  ArrayList refVector = null;

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  static String toHex(long paramLong, int paramInt)
  {
    String str;
    switch (paramInt)
    {
    case 1:
      str = "00" + Long.toString(paramLong & 0xFF, 16);

      break;
    case 2:
      str = "0000" + Long.toString(paramLong & 0xFFFF, 16);

      break;
    case 3:
      str = "000000" + Long.toString(paramLong & 0xFFFFFF, 16);

      break;
    case 4:
      str = "00000000" + Long.toString(paramLong & 0xFFFFFFFF, 16);

      break;
    case 5:
      str = "0000000000" + Long.toString(paramLong & 0xFFFFFFFF, 16);

      break;
    case 6:
      str = "000000000000" + Long.toString(paramLong & 0xFFFFFFFF, 16);

      break;
    case 7:
      str = "00000000000000" + Long.toString(paramLong & 0xFFFFFFFF, 16);

      break;
    case 8:
      return toHex(paramLong >> 32, 4) + toHex(paramLong, 4).substring(2);
    default:
      return "more than 8 bytes";
    }

    return "0x" + str.substring(str.length() - 2 * paramInt);
  }

  static String toHex(byte paramByte)
  {
    String str = "00" + Integer.toHexString(paramByte & 0xFF);
    return "0x" + str.substring(str.length() - 2);
  }

  static String toHex(short paramShort)
  {
    return toHex(paramShort, 2);
  }

  static String toHex(int paramInt)
  {
    return toHex(paramInt, 4);
  }

  static String toHex(byte[] paramArrayOfByte, int paramInt)
  {
    if (paramArrayOfByte == null) {
      return "null";
    }
    if (paramInt > paramArrayOfByte.length) {
      return "byte array not long enough";
    }
    String str = "[";
    int i = Math.min(64, paramInt);

    for (int j = 0; j < i; j++)
    {
      str = str + toHex(paramArrayOfByte[j]) + " ";
    }

    if (i < paramInt) {
      str = str + "...";
    }
    return str + "]";
  }

  static String toHex(byte[] paramArrayOfByte)
  {
    if (paramArrayOfByte == null) {
      return "null";
    }
    return toHex(paramArrayOfByte, paramArrayOfByte.length);
  }

  T4CMAREngine(Communication paramCommunication)
    throws SQLException, IOException
  {
    if (paramCommunication == null)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 433);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.net = paramCommunication;
    try
    {
      this.outStream = new T4CSocketOutputStreamWrapper((NetOutputStream)paramCommunication.getOutputStream());
      this.inStream = new T4CSocketInputStreamWrapper((NetInputStream)paramCommunication.getInputStream(), this.outStream);
    }
    catch (NetException localNetException)
    {
      throw new IOException(localNetException.getMessage());
    }

    this.types = new T4CTypeRep();

    this.types.setRep((byte)1, (byte)2);
  }

  void initBuffers()
  {
  }

  final void marshalSB1(byte paramByte)
    throws IOException
  {
    try
    {
      this.outStream.write(paramByte);
    }
    finally
    {
    }
  }

  final void marshalUB1(short paramShort)
    throws IOException
  {
    try
    {
      this.outStream.write((byte)(paramShort & 0xFF));
    }
    finally
    {
    }
  }

  final void marshalSB2(short paramShort)
    throws IOException
  {
    int i = value2Buffer(paramShort, this.tmpBuffer2, (byte)1);

    if (i != 0)
    {
      try
      {
        this.outStream.write(this.tmpBuffer2, 0, i);
      }
      finally
      {
      }
    }
  }

  final void marshalUB2(int paramInt)
    throws IOException
  {
    marshalSB2((short)(paramInt & 0xFFFF));
  }

  final void marshalSB4(int paramInt)
    throws IOException
  {
    int i = value2Buffer(paramInt, this.tmpBuffer4, (byte)2);

    if (i != 0)
    {
      try
      {
        this.outStream.write(this.tmpBuffer4, 0, i);
      }
      finally
      {
      }
    }
  }

  final void marshalUB4(long paramLong)
    throws IOException
  {
    marshalSB4((int)(paramLong & 0xFFFFFFFF));
  }

  final void marshalSB8(long paramLong)
    throws IOException
  {
    int i = value2Buffer(paramLong, this.tmpBuffer8, (byte)3);

    if (i != 0)
    {
      try
      {
        this.outStream.write(this.tmpBuffer8, 0, i);
      }
      finally
      {
      }
    }
  }

  final void marshalSWORD(int paramInt)
    throws IOException
  {
    marshalSB4(paramInt);
  }

  final void marshalUWORD(long paramLong)
    throws IOException
  {
    marshalSB4((int)(paramLong & 0xFFFFFFFF));
  }

  final void marshalB1Array(byte[] paramArrayOfByte)
    throws IOException
  {
    if (paramArrayOfByte.length > 0)
    {
      try
      {
        this.outStream.write(paramArrayOfByte);
      }
      finally
      {
      }
    }
  }

  final void marshalB1Array(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException
  {
    if (paramArrayOfByte.length > 0)
    {
      try
      {
        this.outStream.write(paramArrayOfByte, paramInt1, paramInt2);
      }
      finally
      {
      }
    }
  }

  final void marshalUB4Array(long[] paramArrayOfLong)
    throws IOException
  {
    for (int i = 0; i < paramArrayOfLong.length; i++)
      marshalSB4((int)(paramArrayOfLong[i] & 0xFFFFFFFF));
  }

  final void marshalO2U(boolean paramBoolean)
    throws IOException
  {
    if (paramBoolean)
      addPtr((byte)1);
    else
      addPtr((byte)0);
  }

  final void marshalNULLPTR()
    throws IOException
  {
    addPtr((byte)0);
  }

  final void marshalPTR()
    throws IOException
  {
    addPtr((byte)1);
  }

  final void marshalCHR(byte[] paramArrayOfByte)
    throws IOException
  {
    marshalCHR(paramArrayOfByte, 0, paramArrayOfByte.length);
  }

  final void marshalCHR(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException
  {
    if (paramInt2 > 0)
    {
      if (this.types.isConvNeeded()) {
        marshalCLR(paramArrayOfByte, paramInt1, paramInt2);
      }
      else
        try
        {
          this.outStream.write(paramArrayOfByte, paramInt1, paramInt2);
        }
        finally
        {
        }
    }
  }

  final void marshalCLR(byte[] paramArrayOfByte, int paramInt)
    throws IOException
  {
    marshalCLR(paramArrayOfByte, 0, paramInt);
  }

  final void marshalCLR(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException
  {
    try
    {
      if (paramInt2 > 64)
      {
        int k = 0;

        this.outStream.write(-2);
        do
        {
          int i = paramInt2 - k;
          int j = i > 64 ? 64 : i;

          this.outStream.write((byte)(j & 0xFF));
          this.outStream.write(paramArrayOfByte, paramInt1 + k, j);

          k += j;
        }
        while (k < paramInt2);

        this.outStream.write(0);
      }
      else
      {
        this.outStream.write((byte)(paramInt2 & 0xFF));

        if (paramArrayOfByte.length != 0)
          this.outStream.write(paramArrayOfByte, paramInt1, paramInt2);
      }
    }
    finally
    {
    }
  }

  final void marshalKEYVAL(byte[][] paramArrayOfByte1, int[] paramArrayOfInt1, byte[][] paramArrayOfByte2, int[] paramArrayOfInt2, byte[] paramArrayOfByte, int paramInt)
    throws IOException
  {
    for (int i = 0; i < paramInt; i++)
    {
      if ((paramArrayOfByte1[i] != null) && (paramArrayOfInt1[i] > 0))
      {
        marshalUB4(paramArrayOfInt1[i]);
        marshalCLR(paramArrayOfByte1[i], 0, paramArrayOfInt1[i]);
      }
      else {
        marshalUB4(0L);
      }
      if ((paramArrayOfByte2[i] != null) && (paramArrayOfInt2[i] > 0))
      {
        marshalUB4(paramArrayOfInt2[i]);
        marshalCLR(paramArrayOfByte2[i], 0, paramArrayOfInt2[i]);
      }
      else {
        marshalUB4(0L);
      }

      if (paramArrayOfByte[i] != 0)
        marshalUB4(1L);
      else
        marshalUB4(0L);
    }
  }

  final void marshalKEYVAL(byte[][] paramArrayOfByte1, byte[][] paramArrayOfByte2, byte[] paramArrayOfByte, int paramInt)
    throws IOException
  {
    int[] arrayOfInt1 = new int[paramInt];
    int[] arrayOfInt2 = new int[paramInt];
    for (int i = 0; i < paramInt; i++)
    {
      if (paramArrayOfByte1[i] != null)
        arrayOfInt1[i] = paramArrayOfByte1[i].length;
      if (paramArrayOfByte2[i] != null)
        arrayOfInt2[i] = paramArrayOfByte2[i].length;
    }
    marshalKEYVAL(paramArrayOfByte1, arrayOfInt1, paramArrayOfByte2, arrayOfInt2, paramArrayOfByte, paramInt);
  }

  final void marshalDALC(byte[] paramArrayOfByte)
    throws IOException
  {
    if ((paramArrayOfByte == null) || (paramArrayOfByte.length < 1))
    {
      try
      {
        this.outStream.write(0);
      }
      finally
      {
      }
    }
    else
    {
      marshalSB4(0xFFFFFFFF & paramArrayOfByte.length);
      marshalCLR(paramArrayOfByte, paramArrayOfByte.length);
    }
  }

  final void marshalKPDKV(byte[][] paramArrayOfByte1, byte[][] paramArrayOfByte2, int[] paramArrayOfInt)
    throws IOException
  {
    for (int i = 0; i < paramArrayOfByte1.length; i++)
    {
      if (paramArrayOfByte1[i] != null)
      {
        marshalUB4(paramArrayOfByte1[i].length);
        marshalCLR(paramArrayOfByte1[i], 0, paramArrayOfByte1[i].length);
      }
      else {
        marshalUB4(0L);
      }if (paramArrayOfByte2[i] != null)
      {
        marshalUB4(paramArrayOfByte2[i].length);
        marshalCLR(paramArrayOfByte2[i], 0, paramArrayOfByte2[i].length);
      }
      else {
        marshalUB4(0L);
      }marshalUB2(paramArrayOfInt[i]);
    }
  }

  final void unmarshalKPDKV(byte[][] paramArrayOfByte1, int[] paramArrayOfInt1, byte[][] paramArrayOfByte2, int[] paramArrayOfInt2)
    throws IOException, SQLException
  {
    int i = 0;
    int[] arrayOfInt = new int[1];

    for (int j = 0; j < paramArrayOfByte1.length; j++)
    {
      i = (int)unmarshalUB4();
      if (i > 0)
      {
        paramArrayOfByte1[j] = new byte[i];
        unmarshalCLR(paramArrayOfByte1[j], 0, arrayOfInt, i);
        paramArrayOfInt1[j] = arrayOfInt[0];
      }
      i = (int)unmarshalUB4();
      if (i > 0)
      {
        paramArrayOfByte2[j] = new byte[i];
        unmarshalCLR(paramArrayOfByte2[j], 0, arrayOfInt, i);
      }
      paramArrayOfInt2[j] = unmarshalUB2();
    }
  }

  final void addPtr(byte paramByte)
    throws IOException
  {
    try
    {
      if ((this.types.rep[4] & 0x1) > 0) {
        this.outStream.write(paramByte);
      }
      else
      {
        int i = value2Buffer(paramByte, this.tmpBuffer4, (byte)4);

        if (i != 0)
          this.outStream.write(this.tmpBuffer4, 0, i);
      }
    }
    finally
    {
    }
  }

  final byte value2Buffer(int paramInt, byte[] paramArrayOfByte, byte paramByte)
    throws IOException
  {
    int i = (this.types.rep[paramByte] & 0x1) > 0 ? 1 : 0;
    int j = 1;
    byte b = 0;

    for (int k = paramArrayOfByte.length - 1; k >= 0; k--)
    {
      paramArrayOfByte[b] = ((byte)(paramInt >>> 8 * k & 0xFF));

      if (i != 0)
      {
        if ((j == 0) || (paramArrayOfByte[b] != 0))
        {
          j = 0;
          b = (byte)(b + 1);
        }
      }
      else {
        b = (byte)(b + 1);
      }
    }

    if (i != 0)
    {
      try
      {
        this.outStream.write(b);
      }
      finally
      {
      }

    }

    if ((this.types.rep[paramByte] & 0x2) > 0) {
      reverseArray(paramArrayOfByte, b);
    }
    return b;
  }

  final byte value2Buffer(long paramLong, byte[] paramArrayOfByte, byte paramByte)
    throws IOException
  {
    int i = (this.types.rep[paramByte] & 0x1) > 0 ? 1 : 0;
    int j = 1;
    byte b = 0;

    for (int k = paramArrayOfByte.length - 1; k >= 0; k--)
    {
      paramArrayOfByte[b] = ((byte)(int)(paramLong >>> 8 * k & 0xFF));

      if (i != 0)
      {
        if ((j == 0) || (paramArrayOfByte[b] != 0))
        {
          j = 0;
          b = (byte)(b + 1);
        }
      }
      else {
        b = (byte)(b + 1);
      }
    }

    if (i != 0)
    {
      try
      {
        this.outStream.write(b);
      }
      finally
      {
      }

    }

    if ((this.types.rep[paramByte] & 0x2) > 0) {
      reverseArray(paramArrayOfByte, b);
    }
    return b;
  }

  final void reverseArray(byte[] paramArrayOfByte, byte paramByte)
  {
    int j = paramByte / 2;

    for (int k = 0; k < j; k++)
    {
      byte i = paramArrayOfByte[k];
      paramArrayOfByte[k] = paramArrayOfByte[(paramByte - 1 - k)];
      paramArrayOfByte[(paramByte - 1 - k)] = i;
    }
  }

  final byte unmarshalSB1()
    throws SQLException, IOException
  {
    byte b = (byte)unmarshalUB1();
    return b;
  }

  final short unmarshalUB1()
    throws SQLException, IOException
  {
    short s = 0;
    try
    {
      s = (short)this.inStream.read();
    }
    catch (BreakNetException localBreakNetException) {
      localBreakNetException = localBreakNetException;

      this.net.sendReset();
      throw localBreakNetException;
    }
    finally
    {
    }

    if (s < 0)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 410);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    return s;
  }

  final short unmarshalSB2()
    throws SQLException, IOException
  {
    short s = (short)unmarshalUB2();
    return s;
  }

  final int unmarshalUB2()
    throws SQLException, IOException
  {
    int i = (int)buffer2Value((byte)1);

    return i & 0xFFFF;
  }

  final int unmarshalUCS2(byte[] paramArrayOfByte, long paramLong)
    throws SQLException, IOException
  {
    int i = unmarshalUB2();

    this.tmpBuffer2[0] = ((byte)((i & 0xFF00) >> 8));
    this.tmpBuffer2[1] = ((byte)(i & 0xFF));

    if (paramLong + 1L < paramArrayOfByte.length)
    {
      paramArrayOfByte[((int)paramLong)] = this.tmpBuffer2[0];
      paramArrayOfByte[((int)paramLong + 1)] = this.tmpBuffer2[1];
    }

    return this.tmpBuffer2[0] == 0 ? 2 : this.tmpBuffer2[1] == 0 ? 1 : 3;
  }

  final int unmarshalSB4()
    throws SQLException, IOException
  {
    int i = (int)unmarshalUB4();
    return i;
  }

  final long unmarshalUB4()
    throws SQLException, IOException
  {
    long l = buffer2Value((byte)2);

    return l;
  }

  final int unmarshalSB4(byte[] paramArrayOfByte)
    throws SQLException, IOException
  {
    long l = buffer2Value((byte)2, new ByteArrayInputStream(paramArrayOfByte));

    return (int)l;
  }

  final long unmarshalSB8()
    throws SQLException, IOException
  {
    long l = buffer2Value((byte)3);
    return l;
  }

  final int unmarshalRefCursor(byte[] paramArrayOfByte)
    throws SQLException, IOException
  {
    int i = unmarshalSB4(paramArrayOfByte);
    return i;
  }

  int unmarshalSWORD()
    throws SQLException, IOException
  {
    int i = (int)unmarshalUB4();
    return i;
  }

  long unmarshalUWORD()
    throws SQLException, IOException
  {
    long l = unmarshalUB4();
    return l;
  }

  byte[] unmarshalNBytes(int paramInt)
    throws SQLException, IOException
  {
    byte[] arrayOfByte = new byte[paramInt];

    if (paramInt > 0)
    {
      try
      {
        if (this.inStream.read(arrayOfByte) < 0)
        {
          SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 410);
          localSQLException.fillInStackTrace();
          throw localSQLException;
        }

      }
      catch (BreakNetException localBreakNetException)
      {
        localBreakNetException = localBreakNetException;

        this.net.sendReset();
        throw localBreakNetException;
      }
      finally
      {
      }
    }

    return arrayOfByte;
  }

  int unmarshalNBytes(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws SQLException, IOException
  {
    int i = 0;

    while (i < paramInt2) {
      i += getNBytes(paramArrayOfByte, paramInt1 + i, paramInt2 - i);
    }
    return i;
  }

  int getNBytes(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws SQLException, IOException
  {
    int i = 0;
    try
    {
      if ((i = this.inStream.read(paramArrayOfByte, paramInt1, paramInt2)) < 0)
      {
        SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 410);
        localSQLException.fillInStackTrace();
        throw localSQLException;
      }

    }
    catch (BreakNetException localBreakNetException)
    {
      localBreakNetException = localBreakNetException;

      this.net.sendReset();
      throw localBreakNetException;
    }
    finally
    {
    }

    return i;
  }

  byte[] getNBytes(int paramInt)
    throws SQLException, IOException
  {
    byte[] arrayOfByte = new byte[paramInt];
    try
    {
      if (this.inStream.read(arrayOfByte) < 0)
      {
        SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 410);
        localSQLException.fillInStackTrace();
        throw localSQLException;
      }

    }
    catch (BreakNetException localBreakNetException)
    {
      localBreakNetException = localBreakNetException;

      this.net.sendReset();
      throw localBreakNetException;
    }
    finally
    {
    }

    return arrayOfByte;
  }

  byte[] unmarshalTEXT(int paramInt)
    throws SQLException, IOException
  {
    int i = 0;

    byte[] arrayOfByte1 = new byte[paramInt];

_L2:
    while (i < paramInt)
    {
      try
      {
        if (this.inStream.read(arrayOfByte1, i, 1) < 0)
        {
          SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 410);
          localSQLException.fillInStackTrace();
          throw localSQLException;
        }

      }
      catch (BreakNetException localBreakNetException)
      {
        localBreakNetException = localBreakNetException;

        this.net.sendReset();
        throw localBreakNetException;
      }
      finally
      {
      }

//      if(abyte0[j++] != 0) goto _L2; else goto _L1
      if (arrayOfByte1[(i++)] == 0)
        break;
    }
//_L1:
    byte[] arrayOfByte2;
    if (arrayOfByte1.length == --i)
    {
      arrayOfByte2 = arrayOfByte1;
    }
    else
    {
      arrayOfByte2 = new byte[i];

      System.arraycopy(arrayOfByte1, 0, arrayOfByte2, 0, i);
    }

    return arrayOfByte2;
  }

  byte[] unmarshalCHR(int paramInt)
    throws SQLException, IOException
  {
    byte[] localObject = null;

    if (this.types.isConvNeeded())
    {
      localObject = unmarshalCLR(paramInt, this.retLen);

      if (localObject.length != this.retLen[0])
      {
        byte[] arrayOfByte = new byte[this.retLen[0]];

        System.arraycopy(localObject, 0, arrayOfByte, 0, this.retLen[0]);

        localObject = arrayOfByte;
      }
    }
    else
    {
      localObject = getNBytes(paramInt);
    }

    return localObject;
  }

  void unmarshalCLR(byte[] paramArrayOfByte, int paramInt, int[] paramArrayOfInt)
    throws SQLException, IOException
  {
    unmarshalCLR(paramArrayOfByte, paramInt, paramArrayOfInt, 2147483647);
  }

  void unmarshalCLR(byte[] paramArrayOfByte, int paramInt1, int[] paramArrayOfInt, int paramInt2)
    throws SQLException, IOException
  {
    unmarshalCLR(paramArrayOfByte, paramInt1, paramArrayOfInt, paramInt2, 0);
  }

  void unmarshalCLR(byte[] paramArrayOfByte, int paramInt1, int[] paramArrayOfInt, int paramInt2, int paramInt3)
    throws SQLException, IOException
  {
    int i = 0;
    int j = 0;
    int k = paramInt1;
    int m = 0;
    int n = 0;
    int i1 = 0;
    int i2 = 0;

    int i3 = -1;

    i = unmarshalUB1();

    if (i < 0)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 401);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if (i == 0)
    {
      paramArrayOfInt[0] = 0;
      return;
    }

    if (escapeSequenceNull(i))
    {
      paramArrayOfInt[0] = 0;
      return;
    }
    int i4;
    if (i != 254)
    {
      if (paramInt3 - i2 >= i)
      {
        unmarshalBuffer(this.ignored, 0, i);
        i2 += i;

        i = 0;
      }
      else if (paramInt3 - i2 > 0)
      {
        unmarshalBuffer(this.ignored, 0, paramInt3 - i2);

        i -= paramInt3 - i2;
        i2 += paramInt3 - i2;
      }

      if (i > 0)
      {
        i1 = Math.min(paramInt2 - n, i);
        k = unmarshalBuffer(paramArrayOfByte, k, i1);
        n += i1;

        i4 = i - i1;

        if (i4 > 0)
          unmarshalBuffer(this.ignored, 0, i4);
      }
    }
    else
    {
      i3 = -1;
      while (true)
      {
        if (i3 != -1)
        {
          i = unmarshalUB1();

          if (i <= 0) {
            break;
          }
        }
        if (i == 254)
        {
          switch (i3)
          {
          case -1:
            i3 = 1;

            break;
          case 1:
            i3 = 0;

            break;
          case 0:
            if (m != 0)
            {
              i3 = 0;
            }
            else
            {
              i3 = 0;
            }
            break;
          }
        }
        else {
          if (k == -1)
          {
            unmarshalBuffer(this.ignored, 0, i);
          }
          else
          {
            j = i;
            if (paramInt3 - i2 >= j)
            {
              unmarshalBuffer(this.ignored, 0, j);
              i2 += j;

              j = 0;
            }
            else if (paramInt3 - i2 > 0)
            {
              unmarshalBuffer(this.ignored, 0, paramInt3 - i2);

              j -= paramInt3 - i2;
              i2 += paramInt3 - i2;
            }

            if (j > 0)
            {
              i1 = Math.min(paramInt2 - n, j);
              k = unmarshalBuffer(paramArrayOfByte, k, i1);
              n += i1;

              i4 = j - i1;

              if (i4 > 0) {
                unmarshalBuffer(this.ignored, 0, i4);
              }
            }
          }

          i3 = 0;

          if (i > 252) {
            m = 1;
          }
        }
      }
    }

    if (paramArrayOfInt != null)
    {
      if (k != -1) {
        paramArrayOfInt[0] = n;
      }
      else
        paramArrayOfInt[0] = (paramArrayOfByte.length - paramInt1);
    }
  }

  final byte[] unmarshalCLR(int paramInt, int[] paramArrayOfInt)
    throws SQLException, IOException
  {
    byte[] arrayOfByte = new byte[paramInt * this.conv.c2sNlsRatio];

    unmarshalCLR(arrayOfByte, 0, paramArrayOfInt, paramInt);
    return arrayOfByte;
  }

  final int[] unmarshalKEYVAL(byte[][] paramArrayOfByte1, byte[][] paramArrayOfByte2, int paramInt)
    throws SQLException, IOException
  {
    byte[] arrayOfByte = new byte[1000];
    int[] arrayOfInt1 = new int[1];
    int[] arrayOfInt2 = new int[paramInt];

    for (int j = 0; j < paramInt; j++)
    {
      int i = unmarshalSB4();

      if (i > 0)
      {
        unmarshalCLR(arrayOfByte, 0, arrayOfInt1);

        paramArrayOfByte1[j] = new byte[arrayOfInt1[0]];

        System.arraycopy(arrayOfByte, 0, paramArrayOfByte1[j], 0, arrayOfInt1[0]);
      }

      i = unmarshalSB4();

      if (i > 0)
      {
        unmarshalCLR(arrayOfByte, 0, arrayOfInt1);

        paramArrayOfByte2[j] = new byte[arrayOfInt1[0]];

        System.arraycopy(arrayOfByte, 0, paramArrayOfByte2[j], 0, arrayOfInt1[0]);
      }

      arrayOfInt2[j] = unmarshalSB4();
    }

    arrayOfByte = null;

    return arrayOfInt2;
  }

  final int unmarshalBuffer(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws SQLException, IOException
  {
    if (paramInt2 <= 0) {
      return paramInt1;
    }
    if (paramArrayOfByte.length < paramInt1 + paramInt2)
    {
      unmarshalNBytes(paramArrayOfByte, paramInt1, paramArrayOfByte.length - paramInt1);

      unmarshalNBytes(this.ignored, 0, paramInt1 + paramInt2 - paramArrayOfByte.length);

      paramInt1 = -1;
    }
    else
    {
      unmarshalNBytes(paramArrayOfByte, paramInt1, paramInt2);

      paramInt1 += paramInt2;
    }
    return paramInt1;
  }

  final byte[] unmarshalCLRforREFS()
    throws SQLException, IOException
  {
    int i = 0;
    int j = 0;
    byte[] arrayOfByte1 = null;

    int k = unmarshalUB1();

    if (k < 0)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 401);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if (k == 0)
    {
      return null;
    }

    boolean bool = escapeSequenceNull(k);
    if (!bool)
    {
      if (this.refVector == null)
        this.refVector = new ArrayList(10);
      else {
        this.refVector.clear();
      }
    }
    if (!bool)
    {
      if (k == 254)
      {
        while ((i = unmarshalUB1()) > 0)
        {
          if ((i != 254) || 
            (!this.types.isServerConversion()))
          {
            j = (short)(j + i);
            byte[] arrayOfByte2 = new byte[i];

            unmarshalBuffer(arrayOfByte2, 0, i);
            this.refVector.add(arrayOfByte2);
          }
        }
      }

      j = k;

      byte[] arrayOfByte2 = new byte[k];

      unmarshalBuffer(arrayOfByte2, 0, k);
      this.refVector.add(arrayOfByte2);

      arrayOfByte1 = new byte[j];

      int m = 0;

      while (this.refVector.size() > 0)
      {
        int n = ((byte[])this.refVector.get(0)).length;

        System.arraycopy(this.refVector.get(0), 0, arrayOfByte1, m, n);

        m += n;

        this.refVector.remove(0);
      }

    }
    else
    {
      arrayOfByte1 = null;
    }
    return arrayOfByte1;
  }

  final boolean escapeSequenceNull(int paramInt)
    throws SQLException
  {
    boolean bool = false;

    switch (paramInt)
    {
    case 0:
      bool = true;

      break;
    case 253:
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 401);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    case 255:
      bool = true;
      break;
    case 254:
      break;
    }

    return bool;
  }

  final int processIndicator(boolean paramBoolean, int paramInt)
    throws SQLException, IOException
  {
    int i = unmarshalSB2();
    int j = 0;

    if (!paramBoolean)
    {
      if (i == 0)
        j = paramInt;
      else if ((i == -2) || (i > 0)) {
        j = i;
      }
      else
      {
        j = 65536 + i;
      }
    }
    return j;
  }

  final long unmarshalDALC(byte[] paramArrayOfByte, int paramInt, int[] paramArrayOfInt)
    throws SQLException, IOException
  {
    long l = unmarshalUB4();

    if (l > 0L)
      unmarshalCLR(paramArrayOfByte, paramInt, paramArrayOfInt);
    return l;
  }

  final byte[] unmarshalDALC()
    throws SQLException, IOException
  {
    long l = unmarshalUB4();
    byte[] arrayOfByte = new byte[(int)(0xFFFFFFFF & l)];

    if (arrayOfByte.length > 0)
    {
      arrayOfByte = unmarshalCLR(arrayOfByte.length, this.retLen);

      if (arrayOfByte == null)
      {
        SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 401);
        localSQLException.fillInStackTrace();
        throw localSQLException;
      }
    }
    else
    {
      arrayOfByte = new byte[0];
    }return arrayOfByte;
  }

  final byte[] unmarshalDALC(int[] paramArrayOfInt)
    throws SQLException, IOException
  {
    long l = unmarshalUB4();
    byte[] arrayOfByte = new byte[(int)(0xFFFFFFFF & l)];

    if (arrayOfByte.length > 0)
    {
      arrayOfByte = unmarshalCLR(arrayOfByte.length, paramArrayOfInt);

      if (arrayOfByte == null)
      {
        SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 401);
        localSQLException.fillInStackTrace();
        throw localSQLException;
      }
    }
    else
    {
      arrayOfByte = new byte[0];
    }return arrayOfByte;
  }

  final long buffer2Value(byte paramByte)
    throws SQLException, IOException
  {
    long l = 0L;
    int i = 1;
    if ((this.types.rep[paramByte] & 0x1) > 0)
    {
      i = this.inStream.readB1();
    }
    else {
      switch (paramByte)
      {
      case 1:
        i = 2;
        break;
      case 2:
        i = 4;
        break;
      case 3:
        i = 8;
      }

    }

    if ((this.types.rep[paramByte] & 0x2) > 0)
      l = this.inStream.readLongLSB(i);
    else {
      l = this.inStream.readLongMSB(i);
    }
    return l;
  }

  final long buffer2Value(byte paramByte, ByteArrayInputStream paramByteArrayInputStream)
    throws SQLException, IOException
  {
    int j = 0;

    long l = 0L;
    int k = 0;
    SQLException localSQLException;
    if ((this.types.rep[paramByte] & 0x1) > 0)
    {
      j = paramByteArrayInputStream.read();

      if ((j & 0x80) > 0)
      {
        j &= 127;
        k = 1;
      }

      if (j < 0)
      {
        localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 410);
        localSQLException.fillInStackTrace();
        throw localSQLException;
      }

      if (j == 0)
      {
        return 0L;
      }

      if (((paramByte == 1) && (j > 2)) || ((paramByte == 2) && (j > 4)))
      {
        localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 412);
        localSQLException.fillInStackTrace();
        throw localSQLException;
      }

    }
    else if (paramByte == 1) {
      j = 2;
    } else if (paramByte == 2) {
      j = 4;
    }

    byte[] arrayOfByte = new byte[j];

    if (paramByteArrayInputStream.read(arrayOfByte) < 0)
    {
      localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 410);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    for (int m = 0; m < arrayOfByte.length; m++)
    {
      int i;
      if ((this.types.rep[paramByte] & 0x2) > 0)
        i = (short)(arrayOfByte[(arrayOfByte.length - 1 - m)] & 0xFF);
      else {
        i = (short)(arrayOfByte[m] & 0xFF);
      }
      l |= i << 8 * (arrayOfByte.length - 1 - m);
    }

    l &= -1L;

    if (k != 0) {
      l = -l;
    }
    return l;
  }

  protected OracleConnection getConnectionDuringExceptionHandling()
  {
    return (OracleConnection)this.connForException.get();
  }

  protected void setConnectionDuringExceptionHandling(OracleConnection paramOracleConnection)
  {
    this.connForException.set(paramOracleConnection);
  }
}