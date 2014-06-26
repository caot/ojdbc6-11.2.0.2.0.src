package oracle.sql;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.SQLException;
import oracle.jdbc.driver.DatabaseError;

public class BLOB extends DatumWithConnection
  implements Blob
{
  public static final int MAX_CHUNK_SIZE = 32768;
  public static final int DURATION_SESSION = 10;
  public static final int DURATION_CALL = 12;
  static final int OLD_WRONG_DURATION_SESSION = 1;
  static final int OLD_WRONG_DURATION_CALL = 2;
  public static final int MODE_READONLY = 0;
  public static final int MODE_READWRITE = 1;
  BlobDBAccess dbaccess;
  int dbChunkSize = -1;
  boolean isFree = false;

  boolean fromObject = false;

  private long cachedLobLength = -1L;
  private byte[] prefetchData;
  private int prefetchDataSize = 0;
  private boolean activePrefetch = false;
  static final int KDLCTLSIZE = 16;
  static final int KDF_FLAG = 88;
  static final int KDLIDDAT = 8;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  protected BLOB()
  {
  }

  public BLOB(oracle.jdbc.OracleConnection paramOracleConnection)
    throws SQLException
  {
    this(paramOracleConnection, null);
  }

  public BLOB(oracle.jdbc.OracleConnection paramOracleConnection, byte[] paramArrayOfByte, boolean paramBoolean)
    throws SQLException
  {
    this(paramOracleConnection, paramArrayOfByte);

    this.fromObject = paramBoolean;
  }

  public BLOB(oracle.jdbc.OracleConnection paramOracleConnection, byte[] paramArrayOfByte)
    throws SQLException
  {
    super(paramArrayOfByte);

    assertNotNull(paramOracleConnection);
    setPhysicalConnectionOf(paramOracleConnection);

    this.dbaccess = getPhysicalConnection().createBlobDBAccess();
  }

  public long length()
    throws SQLException
  {
    if (this.isFree) {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 192);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
    long l = -1L;

    if ((this.activePrefetch) && (this.cachedLobLength != -1L))
      l = this.cachedLobLength;
    else if (canReadBasicLobDataInLocator())
      l = dilLength();
    else
      l = getDBAccess().length(this);
    return l;
  }

  public byte[] getBytes(long paramLong, int paramInt)
    throws SQLException
  {
    if (this.isFree) {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 192);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }
    if ((paramInt < 0) || (paramLong < 1L))
    {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68, "getBytes()");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    if (canReadBasicLobDataInLocator())
    {
      return dilGetBytes(paramLong, paramInt);
    }

    byte[] localObject = null;

    if (paramInt == 0) {
      return new byte[0];
    }
    if ((this.activePrefetch) && ((this.cachedLobLength == 0L) || ((this.cachedLobLength > 0L) && (paramLong - 1L >= this.cachedLobLength))))
    {
      localObject = null;
    }
    else {
      long l = 0L;
      byte[] arrayOfByte;
      if ((this.activePrefetch) && (this.cachedLobLength != -1L))
        arrayOfByte = new byte[Math.min((int)this.cachedLobLength, paramInt)];
      else {
        arrayOfByte = new byte[paramInt];
      }

      l = getBytes(paramLong, paramInt, arrayOfByte);

      if (l > 0L)
      {
        if (l == paramInt)
        {
          localObject = arrayOfByte;
        }
        else
        {
          localObject = new byte[(int)l];

          System.arraycopy(arrayOfByte, 0, localObject, 0, (int)l);
        }
      }
    }

    return localObject;
  }

  public InputStream getBinaryStream()
    throws SQLException
  {
    if (this.isFree) {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 192);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
    if (canReadBasicLobDataInLocator())
    {
      return dilGetBinaryStream(1L);
    }
    return getDBAccess().newInputStream(this, getBufferSize(), 0L);
  }

  public long position(byte[] paramArrayOfByte, long paramLong)
    throws SQLException
  {
    if (this.isFree) {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 192);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
    return getDBAccess().position(this, paramArrayOfByte, paramLong);
  }

  public long position(Blob paramBlob, long paramLong)
    throws SQLException
  {
    if (this.isFree) {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 192);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
    return getDBAccess().position(this, (BLOB)paramBlob, paramLong);
  }

  public int getBytes(long paramLong, int paramInt, byte[] paramArrayOfByte)
    throws SQLException
  {
    if (this.isFree) {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 192);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
    return getDBAccess().getBytes(this, paramLong, paramInt, paramArrayOfByte);
  }

  /** @deprecated */
  public int putBytes(long paramLong, byte[] paramArrayOfByte)
    throws SQLException
  {
    if (this.isFree) {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 192);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
    return setBytes(paramLong, paramArrayOfByte);
  }

  /** @deprecated */
  public int putBytes(long paramLong, byte[] paramArrayOfByte, int paramInt)
    throws SQLException
  {
    if (this.isFree) {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 192);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
    return setBytes(paramLong, paramArrayOfByte, 0, paramInt);
  }

  /** @deprecated */
  public OutputStream getBinaryOutputStream()
    throws SQLException
  {
    if (this.isFree) {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 192);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
    return setBinaryStream(1L);
  }

  public byte[] getLocator()
  {
    return getBytes();
  }

  public void setLocator(byte[] paramArrayOfByte)
  {
    setBytes(paramArrayOfByte);
  }

  public int getChunkSize()
    throws SQLException
  {
    if (this.isFree) {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 192);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
    if (this.dbChunkSize <= 0)
    {
      this.dbChunkSize = getDBAccess().getChunkSize(this);
    }

    return this.dbChunkSize;
  }

  public int getBufferSize()
    throws SQLException
  {
    if (this.isFree) {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 192);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
    int i = getChunkSize();
    int j = i;

    if ((i >= 32768) || (i <= 0))
    {
      j = 32768;
    }
    else
    {
      j = 32768 / i * i;
    }

    return j;
  }

  /** @deprecated */
  public static BLOB empty_lob()
    throws SQLException
  {
    return getEmptyBLOB();
  }

  public static BLOB getEmptyBLOB()
    throws SQLException
  {
    byte[] arrayOfByte = new byte[86];

    arrayOfByte[1] = 84;
    arrayOfByte[5] = 24;

    BLOB localBLOB = new BLOB();

    localBLOB.setShareBytes(arrayOfByte);

    return localBLOB;
  }

  public boolean isEmptyLob()
    throws SQLException
  {
    if (this.isFree) {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 192);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
    boolean bool = (shareBytes()[5] & 0x10) != 0;

    return bool;
  }

  public boolean isSecureFile()
    throws SQLException
  {
    if (this.isFree) {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 192);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
    boolean bool = (shareBytes()[7] & 0xFFFFFF80) != 0;
    return bool;
  }

  /** @deprecated */
  public OutputStream getBinaryOutputStream(long paramLong)
    throws SQLException
  {
    if (this.isFree) {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 192);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
    return getDBAccess().newOutputStream(this, getBufferSize(), paramLong, false);
  }

  public InputStream getBinaryStream(long paramLong)
    throws SQLException
  {
    if (this.isFree) {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 192);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
    if (canReadBasicLobDataInLocator())
    {
      return dilGetBinaryStream(paramLong);
    }
    return getDBAccess().newInputStream(this, getBufferSize(), paramLong);
  }

  /** @deprecated */
  public void trim(long paramLong)
    throws SQLException
  {
    if (this.isFree) {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 192);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
    truncate(paramLong);
  }

  public static BLOB createTemporary(Connection paramConnection, boolean paramBoolean, int paramInt)
    throws SQLException
  {
    int i = paramInt;

    if (paramInt == 1) {
      i = 10;
    }
    if (paramInt == 2) {
      i = 12;
    }
    if ((paramConnection == null) || ((i != 10) && (i != 12)))
    {
      SQLException sqlexception = DatabaseError.createSqlException(null, 68, "'conn' should not be null and 'duration' should either be equal to DURATION_SESSION or to DURATION_CALL");

      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject = ((oracle.jdbc.OracleConnection)paramConnection).physicalConnectionWithin();

    return getDBAccess((Connection)localObject).createTemporaryBlob((Connection)localObject, paramBoolean, i);
  }

  public static void freeTemporary(BLOB paramBLOB)
    throws SQLException
  {
    if (paramBLOB == null) {
      return;
    }
    paramBLOB.freeTemporary();
  }

  public static boolean isTemporary(BLOB paramBLOB)
    throws SQLException
  {
    if (paramBLOB == null) {
      return false;
    }
    return paramBLOB.isTemporary();
  }

  public void freeTemporary()
    throws SQLException
  {
    if (this.isFree) {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 192);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    getDBAccess().freeTemporary(this, this.fromObject);
  }

  public boolean isTemporary()
    throws SQLException
  {
    if (this.isFree) {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 192);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
    return getDBAccess().isTemporary(this);
  }

  public void open(int paramInt)
    throws SQLException
  {
    if (this.isFree) {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 192);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
    getDBAccess().open(this, paramInt);
  }

  public void close()
    throws SQLException
  {
    if (this.isFree) {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 192);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
    getDBAccess().close(this);
  }

  public boolean isOpen()
    throws SQLException
  {
    if (this.isFree) {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 192);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
    return getDBAccess().isOpen(this);
  }

  public int setBytes(long paramLong, byte[] paramArrayOfByte)
    throws SQLException
  {
    if (this.isFree) {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 192);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    return getDBAccess().putBytes(this, paramLong, paramArrayOfByte, 0, paramArrayOfByte != null ? paramArrayOfByte.length : 0);
  }

  public int setBytes(long paramLong, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws SQLException
  {
    if (this.isFree) {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 192);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
    return getDBAccess().putBytes(this, paramLong, paramArrayOfByte, paramInt1, paramInt2);
  }

  public OutputStream setBinaryStream(long paramLong)
    throws SQLException
  {
    if (this.isFree) {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 192);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
    return getDBAccess().newOutputStream(this, getBufferSize(), paramLong, true);
  }

  public void truncate(long paramLong)
    throws SQLException
  {
    SQLException localSQLException;
    if (this.isFree) {
      localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 192);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
    if (paramLong < 0L)
    {
      localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68, "'len' should be >= 0. ");
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    getDBAccess().trim(this, paramLong);
  }

  public Object toJdbc()
    throws SQLException
  {
    if (this.isFree) {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 192);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
    return this;
  }

  public boolean isConvertibleTo(Class paramClass)
  {
    String str = paramClass.getName();

    return (str.compareTo("java.io.InputStream") == 0) || (str.compareTo("java.io.Reader") == 0);
  }

  public Reader characterStreamValue()
    throws SQLException
  {
    getInternalConnection(); return getDBAccess().newConversionReader(this, 8);
  }

  public InputStream asciiStreamValue()
    throws SQLException
  {
    getInternalConnection(); return getDBAccess().newConversionInputStream(this, 2);
  }

  public InputStream binaryStreamValue()
    throws SQLException
  {
    return getBinaryStream();
  }

  public Object makeJdbcArray(int paramInt)
  {
    return new BLOB[paramInt];
  }

  public BlobDBAccess getDBAccess()
    throws SQLException
  {
    SQLException localSQLException;
    if (this.dbaccess == null)
    {
      if (isEmptyLob())
      {
        localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 98);
        localSQLException.fillInStackTrace();
        throw localSQLException;
      }

      this.dbaccess = getInternalConnection().createBlobDBAccess();
    }

    if (getPhysicalConnection().isClosed())
    {
      localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 8);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    return this.dbaccess;
  }

  public static BlobDBAccess getDBAccess(Connection paramConnection)
    throws SQLException
  {
    return ((oracle.jdbc.OracleConnection)paramConnection).physicalConnectionWithin().createBlobDBAccess();
  }

  public Connection getJavaSqlConnection()
    throws SQLException
  {
    if (this.isFree) {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 192);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
    return super.getJavaSqlConnection();
  }

  public final void setLength(long paramLong)
  {
    this.cachedLobLength = paramLong;
  }

  public final void setChunkSize(int paramInt)
  {
    this.dbChunkSize = paramInt;
  }

  public final void setPrefetchedData(byte[] paramArrayOfByte)
  {
    if (paramArrayOfByte == null)
      setPrefetchedData(null, 0);
    else
      setPrefetchedData(paramArrayOfByte, paramArrayOfByte.length);
  }

  public final void setPrefetchedData(byte[] paramArrayOfByte, int paramInt)
  {
    this.prefetchData = paramArrayOfByte;
    this.prefetchDataSize = paramInt;
  }

  public final byte[] getPrefetchedData()
  {
    return this.prefetchData;
  }

  public final int getPrefetchedDataSize()
  {
    return this.prefetchDataSize;
  }

  public final void setActivePrefetch(boolean paramBoolean)
  {
    if ((this.activePrefetch) && (!paramBoolean))
      clearCachedData();
    this.activePrefetch = paramBoolean;
  }

  public final void clearCachedData()
  {
    this.cachedLobLength = -1L;
    this.prefetchData = null;
  }

  public final boolean isActivePrefetch()
  {
    return this.activePrefetch;
  }

  boolean canReadBasicLobDataInLocator()
    throws SQLException
  {
    byte[] arrayOfByte = shareBytes();
    if ((arrayOfByte == null) || (arrayOfByte.length < 102))
    {
      return false;
    }
    if (!getPhysicalConnection().isDataInLocatorEnabled())
    {
      return false;
    }
    int i = arrayOfByte[6] & 0xFF;
    int j = arrayOfByte[7] & 0xFF;
    int k = (i & 0x8) == 8 ? 1 : 0;
    int m = (j & 0xFFFFFF80) == -128 ? 1 : 0;
    int n = 0;
    int i1;
    if ((k != 0) && (m == 0))
    {
      i1 = arrayOfByte[88] & 0xFF;
      n = (i1 & 0x8) == 8 ? 1 : 0;
    }
    
    return (k != 0) && (m == 0) && (n != 0);
  }

  int dilLength()
  {
    return shareBytes().length - 86 - 16;
  }

  byte[] dilGetBytes(long paramLong, int paramInt)
    throws SQLException
  {
    if (paramInt == 0)
    {
      return new byte[0];
    }

    if (dilLength() == 0)
    {
      return null;
    }
    int i = (int)Math.min(paramInt, dilLength() - (paramLong - 1L));

    if (i <= 0)
    {
      return null;
    }

    byte[] arrayOfByte = new byte[i];
    System.arraycopy(shareBytes(), (int)(paramLong - 1L) + 86 + 16, arrayOfByte, 0, i);
    return arrayOfByte;
  }

  InputStream dilGetBinaryStream(long paramLong)
    throws SQLException
  {
    if (paramLong - 1L > dilLength())
    {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68, "");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject = dilGetBytes(paramLong, dilLength());
    return new ByteArrayInputStream((byte[])localObject);
  }

  public void free()
    throws SQLException
  {
    if (this.isFree) return;
    if (isOpen()) close();
    if (isTemporary()) freeTemporary();
    this.isFree = true;
    this.dbaccess = null;
  }

  public InputStream getBinaryStream(long paramLong1, long paramLong2)
    throws SQLException
  {
    if (this.isFree) {
      SQLException localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 192);
      localSQLException1.fillInStackTrace();
      throw localSQLException1;
    }
    if (canReadBasicLobDataInLocator())
    {
      return dilGetBinaryStream(paramLong1, paramLong2);
    }
    long l = length();
    if ((paramLong1 < 1L) || (paramLong2 < 0L) || (paramLong1 > l) || (paramLong1 - 1L + paramLong2 > l))
    {
      SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68);
      localSQLException2.fillInStackTrace();
      throw localSQLException2;
    }
    return getDBAccess().newInputStream(this, getChunkSize(), paramLong1, paramLong2);
  }

  InputStream dilGetBinaryStream(long paramLong1, long paramLong2)
    throws SQLException
  {
    int i = dilLength();
    if ((paramLong1 < 1L) || (paramLong2 < 0L) || (paramLong1 > i) || (paramLong1 - 1L + paramLong2 > i))
    {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }
    Object localObject = dilGetBytes(paramLong1, i - (int)(paramLong1 - 1L));
    return new ByteArrayInputStream((byte[])localObject, 0, (int)paramLong2);
  }
}