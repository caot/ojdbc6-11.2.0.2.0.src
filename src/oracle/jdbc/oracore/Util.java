package oracle.jdbc.oracore;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.sql.SQLException;
import oracle.jdbc.driver.DatabaseError;
import oracle.jdbc.internal.OracleConnection;

public class Util
{
  private static int[] ldsRoundTable = { 0, 1, 0, 2, 0, 0, 0, 3, 0 };

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  static void checkNextByte(InputStream paramInputStream, byte paramByte)
    throws SQLException
  {
    try
    {
      if (paramInputStream.read() != paramByte)
      {
        SQLException localSQLException1 = DatabaseError.createSqlException(null, 47, "parseTDS");
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

    }
    catch (IOException localIOException)
    {
      SQLException localSQLException2 = DatabaseError.createSqlException(null, localIOException);
      localSQLException2.fillInStackTrace();
      throw localSQLException2;
    }
  }

  public static int[] toJavaUnsignedBytes(byte[] paramArrayOfByte)
  {
    int[] arrayOfInt = new int[paramArrayOfByte.length];

    for (int i = 0; i < paramArrayOfByte.length; i++) {
      paramArrayOfByte[i] &= 255;
    }
    return arrayOfInt;
  }

  static byte[] readBytes(InputStream paramInputStream, int paramInt)
    throws SQLException
  {
    byte[] arrayOfByte = new byte[paramInt];
    try
    {
      int i = paramInputStream.read(arrayOfByte);

      if (i != paramInt)
      {
        byte[] localObject = new byte[i];

        System.arraycopy(arrayOfByte, 0, localObject, 0, i);

        return localObject;
      }

    }
    catch (IOException localIOException)
    {
      SQLException sqlexception = DatabaseError.createSqlException(null, localIOException);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    return arrayOfByte;
  }

  static void writeBytes(OutputStream paramOutputStream, byte[] paramArrayOfByte)
    throws SQLException
  {
    try
    {
      paramOutputStream.write(paramArrayOfByte);
    }
    catch (IOException localIOException)
    {
      SQLException localSQLException = DatabaseError.createSqlException(null, localIOException);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  static void skipBytes(InputStream paramInputStream, int paramInt)
    throws SQLException
  {
    try
    {
      paramInputStream.skip(paramInt);
    }
    catch (IOException localIOException)
    {
      SQLException localSQLException = DatabaseError.createSqlException(null, localIOException);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  static long readLong(InputStream paramInputStream)
    throws SQLException
  {
    byte[] arrayOfByte = new byte[4];
    try
    {
      paramInputStream.read(arrayOfByte);

      return (((arrayOfByte[0] & 0xFF) * 256 + (arrayOfByte[1] & 0xFF)) * 256 + (arrayOfByte[2] & 0xFF)) * 256 + (arrayOfByte[3] & 0xFF);
    }
    catch (IOException localIOException)
    {
      SQLException localSQLException = DatabaseError.createSqlException(null, localIOException);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  static short readShort(InputStream paramInputStream)
    throws SQLException
  {
    byte[] arrayOfByte = new byte[2];
    try
    {
      paramInputStream.read(arrayOfByte);

      return (short)((arrayOfByte[0] & 0xFF) * 256 + (arrayOfByte[1] & 0xFF));
    }
    catch (IOException localIOException)
    {
      SQLException localSQLException = DatabaseError.createSqlException(null, localIOException);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  static byte readByte(InputStream paramInputStream)
    throws SQLException
  {
    try
    {
      return (byte)paramInputStream.read();
    }
    catch (IOException localIOException)
    {
      SQLException localSQLException = DatabaseError.createSqlException(null, localIOException);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  static byte fdoGetSize(byte[] paramArrayOfByte, int paramInt)
  {
    int i = fdoGetEntry(paramArrayOfByte, paramInt);

    return (byte)(i >> 3 & 0x1F);
  }

  static byte fdoGetAlign(byte[] paramArrayOfByte, int paramInt)
  {
    int i = fdoGetEntry(paramArrayOfByte, paramInt);

    return (byte)(i & 0x7);
  }

  static int ldsRound(int paramInt1, int paramInt2)
  {
    int i = ldsRoundTable[paramInt2];

    return (paramInt1 >> i) + 1 << i;
  }

  private static byte fdoGetEntry(byte[] paramArrayOfByte, int paramInt)
  {
    int i = getUnsignedByte(paramArrayOfByte[5]);
    byte b = paramArrayOfByte[(6 + i + paramInt)];

    return b;
  }

  public static short getUnsignedByte(byte paramByte)
  {
    return (short)(paramByte & 0xFF);
  }

  public static byte[] serializeObject(Object paramObject)
    throws IOException
  {
    if (paramObject == null) {
      return null;
    }
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    ObjectOutputStream localObjectOutputStream = new ObjectOutputStream(localByteArrayOutputStream);

    localObjectOutputStream.writeObject(paramObject);
    localObjectOutputStream.flush();

    return localByteArrayOutputStream.toByteArray();
  }

  public static Object deserializeObject(byte[] paramArrayOfByte)
    throws IOException, ClassNotFoundException
  {
    if (paramArrayOfByte == null) {
      return null;
    }
    ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(paramArrayOfByte);

    return new ObjectInputStream(localByteArrayInputStream).readObject();
  }

  public static void printByteArray(byte[] paramArrayOfByte)
  {
    System.out.println("DONT CALL THIS -- oracle.jdbc.oracore.Util.printByteArray");
  }

  protected OracleConnection getConnectionDuringExceptionHandling()
  {
    return null;
  }
}