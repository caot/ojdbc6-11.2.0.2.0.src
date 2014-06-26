package oracle.sql;

import java.io.ByteArrayInputStream;
import java.io.CharArrayReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.SQLException;
import oracle.jdbc.driver.DatabaseError;

public class CLOB extends DatumWithConnection
  implements Clob
{
  public static final int MAX_CHUNK_SIZE = 32768;
  public static final int DURATION_SESSION = 10;
  public static final int DURATION_CALL = 12;
  static final int OLD_WRONG_DURATION_SESSION = 1;
  static final int OLD_WRONG_DURATION_CALL = 2;
  public static final int MODE_READONLY = 0;
  public static final int MODE_READWRITE = 1;
  ClobDBAccess dbaccess;
  private int dbChunkSize = -1;
  private short csform;
  boolean isFree = false;

  boolean fromObject = false;

  long cachedLengthOfClobInChars = -1L;

  char[] prefetchData = null;

  int prefetchDataSize = 0;

  boolean activePrefetch = false;
  static final int KDLCTLSIZE = 16;
  static final int KDF_FLAG = 88;
  static final int KDLIDDAT = 8;
  transient CharacterSet dilCharacterSet = null;

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  protected CLOB()
  {
  }

  public CLOB(oracle.jdbc.OracleConnection paramOracleConnection)
    throws SQLException
  {
    this(paramOracleConnection, null);
  }

  public CLOB(oracle.jdbc.OracleConnection paramOracleConnection, byte[] paramArrayOfByte, boolean paramBoolean)
    throws SQLException
  {
    this(paramOracleConnection, paramArrayOfByte);

    this.fromObject = paramBoolean;
  }

  public CLOB(oracle.jdbc.OracleConnection paramOracleConnection, byte[] paramArrayOfByte)
    throws SQLException
  {
    super(paramArrayOfByte);

    if (paramArrayOfByte != null)
    {
      if (((paramArrayOfByte[5] & 0x40) != 0) && ((paramArrayOfByte[5] & 0xFFFFFF80) == 0))
      {
        this.csform = 2;
      }
      else this.csform = 1;

    }

    assertNotNull(paramOracleConnection);
    setPhysicalConnectionOf(paramOracleConnection);

    this.dbaccess = ((oracle.jdbc.internal.OracleConnection)paramOracleConnection).createClobDBAccess();
  }

  public CLOB(oracle.jdbc.OracleConnection paramOracleConnection, byte[] paramArrayOfByte, short paramShort)
    throws SQLException
  {
    this(paramOracleConnection, paramArrayOfByte);

    this.csform = paramShort;
  }

  public boolean isNCLOB()
  {
    return this.csform == 2;
  }

  public long length()
    throws SQLException
  {
    if (this.isFree) {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 192);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    long l = -1L;
    if ((this.activePrefetch) && (this.cachedLengthOfClobInChars != -1L))
      l = this.cachedLengthOfClobInChars;
    else if (canReadBasicLobDataInLocator())
    {
      l = dilGetChars().length;
    }
    else
      l = getDBAccess().length(this);
    return l;
  }

  public String getSubString(long paramLong, int paramInt)
    throws SQLException
  {
    if (this.isFree) {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 192);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }
    if ((paramInt < 0) || (paramLong < 1L))
    {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    if (canReadBasicLobDataInLocator())
    {
      return dilGetSubString(paramLong, paramInt);
    }

    String localObject = null;

    if ((paramInt == 0) || ((this.activePrefetch) && ((this.cachedLengthOfClobInChars == 0L) || ((this.cachedLengthOfClobInChars > 0L) && (paramLong - 1L >= this.cachedLengthOfClobInChars)))))
    {
      localObject = new String();
    }
    else if ((this.prefetchData != null) && (this.prefetchDataSize > 0) && (this.cachedLengthOfClobInChars == this.prefetchDataSize) && (paramLong + paramInt - 1L <= this.cachedLengthOfClobInChars))
    {
      localObject = new String(this.prefetchData, (int)paramLong - 1, paramInt);
    }
    else
    {
      char[] arrayOfChar = getDBAccess().getCharBufferSync(paramInt);

      int i = getChars(paramLong, paramInt, arrayOfChar);

      if (i > 0)
      {
        localObject = new String(arrayOfChar, 0, i);
      }
      else
      {
        localObject = new String();
      }

      getDBAccess().cacheBufferSync(arrayOfChar);
    }

    return localObject;
  }

  public Reader getCharacterStream()
    throws SQLException
  {
    if (this.isFree) {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 192);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }
    if (canReadBasicLobDataInLocator())
    {
      return dilGetCharacterStream(1L);
    }

    return getDBAccess().newReader(this, getBufferSize(), 0L);
  }

  public InputStream getAsciiStream()
    throws SQLException
  {
    if (this.isFree) {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 192);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }
    if (canReadBasicLobDataInLocator())
    {
      return dilGetAsciiStream(1L);
    }

    return getDBAccess().newInputStream(this, getBufferSize(), 0L);
  }

  public long position(String paramString, long paramLong)
    throws SQLException
  {
    if (this.isFree) {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 192);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }
    return getDBAccess().position(this, paramString, paramLong);
  }

  public long position(Clob paramClob, long paramLong)
    throws SQLException
  {
    if (this.isFree) {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 192);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }
    return getDBAccess().position(this, (CLOB)paramClob, paramLong);
  }

  public int getChars(long paramLong, int paramInt, char[] paramArrayOfChar)
    throws SQLException
  {
    if (this.isFree) {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 192);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }
    return getDBAccess().getChars(this, paramLong, paramInt, paramArrayOfChar);
  }

  /** @deprecated */
  public Writer getCharacterOutputStream()
    throws SQLException
  {
    if (this.isFree) {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 192);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }
    return setCharacterStream(1L);
  }

  /** @deprecated */
  public OutputStream getAsciiOutputStream()
    throws SQLException
  {
    if (this.isFree) {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 192);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }
    return setAsciiStream(1L);
  }

  public byte[] getLocator()
  {
    return getBytes();
  }

  public void setLocator(byte[] paramArrayOfByte)
  {
    setBytes(paramArrayOfByte);
  }

  public int putChars(long paramLong, char[] paramArrayOfChar)
    throws SQLException
  {
    if (this.isFree) {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 192);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }
    return getDBAccess().putChars(this, paramLong, paramArrayOfChar, 0, paramArrayOfChar != null ? paramArrayOfChar.length : 0);
  }

  public int putChars(long paramLong, char[] paramArrayOfChar, int paramInt)
    throws SQLException
  {
    if (this.isFree) {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 192);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }
    return getDBAccess().putChars(this, paramLong, paramArrayOfChar, 0, paramInt);
  }

  public int putChars(long paramLong, char[] paramArrayOfChar, int paramInt1, int paramInt2)
    throws SQLException
  {
    if (this.isFree) {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 192);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }
    return getDBAccess().putChars(this, paramLong, paramArrayOfChar, paramInt1, paramInt2);
  }

  /** @deprecated */
  public int putString(long paramLong, String paramString)
    throws SQLException
  {
    if (this.isFree) {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 192);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }
    return setString(paramLong, paramString);
  }

  public int getChunkSize()
    throws SQLException
  {
    if (this.isFree) {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 192);
      sqlexception.fillInStackTrace();
      throw sqlexception;
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
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 192);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }
    int i = getChunkSize();
    int j = 0;

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
  public static CLOB empty_lob()
    throws SQLException
  {
    return getEmptyCLOB();
  }

  public static CLOB getEmptyCLOB()
    throws SQLException
  {
    byte[] arrayOfByte = new byte[86];

    arrayOfByte[1] = 84;
    arrayOfByte[5] = 24;

    CLOB localCLOB = new CLOB();

    localCLOB.setShareBytes(arrayOfByte);

    return localCLOB;
  }

  public boolean isEmptyLob()
    throws SQLException
  {
    if (this.isFree) {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 192);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }
    return (shareBytes()[5] & 0x10) != 0;
  }

  public boolean isSecureFile()
    throws SQLException
  {
    if (this.isFree) {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 192);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }
    boolean bool = (shareBytes()[7] & 0xFFFFFF80) != 0;
    return bool;
  }

  /** @deprecated */
  public OutputStream getAsciiOutputStream(long paramLong)
    throws SQLException
  {
    if (this.isFree) {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 192);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }
    return getDBAccess().newOutputStream(this, getBufferSize(), paramLong, false);
  }

  /** @deprecated */
  public Writer getCharacterOutputStream(long paramLong)
    throws SQLException
  {
    if (this.isFree) {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 192);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }
    return getDBAccess().newWriter(this, getBufferSize(), paramLong, false);
  }

  public InputStream getAsciiStream(long paramLong)
    throws SQLException
  {
    if (this.isFree) {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 192);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }
    if (canReadBasicLobDataInLocator())
    {
      return dilGetAsciiStream(paramLong);
    }

    return getDBAccess().newInputStream(this, getBufferSize(), paramLong);
  }

  public Reader getCharacterStream(long paramLong)
    throws SQLException
  {
    if (this.isFree) {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 192);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }
    if (canReadBasicLobDataInLocator())
    {
      return dilGetCharacterStream(paramLong);
    }

    return getDBAccess().newReader(this, getBufferSize(), paramLong);
  }

  /** @deprecated */
  public void trim(long paramLong)
    throws SQLException
  {
    if (this.isFree) {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 192);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }
    truncate(paramLong);
  }

  public static CLOB createTemporary(Connection paramConnection, boolean paramBoolean, int paramInt)
    throws SQLException
  {
    return createTemporary(paramConnection, paramBoolean, paramInt, (short)1);
  }

  public static CLOB createTemporary(Connection paramConnection, boolean paramBoolean, int paramInt, short paramShort)
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
      SQLException sqlexception = DatabaseError.createSqlException(null, 68, "'conn' should not be null and 'duration' should either be equal to DURATION_SESSION or DURATION_CALL");

      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject = ((oracle.jdbc.OracleConnection)paramConnection).physicalConnectionWithin();

    CLOB localCLOB = getDBAccess((Connection)localObject).createTemporaryClob((Connection)localObject, paramBoolean, i, paramShort);

    localCLOB.csform = paramShort;

    return localCLOB;
  }

  public static void freeTemporary(CLOB paramCLOB)
    throws SQLException
  {
    if (paramCLOB == null)
      return;
    paramCLOB.freeTemporary();
  }

  public static boolean isTemporary(CLOB paramCLOB)
    throws SQLException
  {
    if (paramCLOB == null) {
      return false;
    }
    return paramCLOB.isTemporary();
  }

  public void freeTemporary()
    throws SQLException
  {
    if (this.isFree) {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 192);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    getDBAccess().freeTemporary(this, this.fromObject);
  }

  public boolean isTemporary()
    throws SQLException
  {
    if (this.isFree) {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 192);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }
    return getDBAccess().isTemporary(this);
  }

  public void open(int paramInt)
    throws SQLException
  {
    if (this.isFree) {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 192);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }
    getDBAccess().open(this, paramInt);
  }

  public void close()
    throws SQLException
  {
    if (this.isFree) {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 192);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }
    getDBAccess().close(this);
  }

  public boolean isOpen()
    throws SQLException
  {
    if (this.isFree) {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 192);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }
    return getDBAccess().isOpen(this);
  }

  public int setString(long paramLong, String paramString)
    throws SQLException
  {
    SQLException sqlexception;
    if (this.isFree) {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 192);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }
    if (paramLong < 1L)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68, "'pos' should not be < 1");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    int i = 0;

    if ((paramString != null) && (paramString.length() != 0))
      i = putChars(paramLong, paramString.toCharArray());
    return i;
  }

  public int setString(long paramLong, String paramString, int paramInt1, int paramInt2)
    throws SQLException
  {
    SQLException sqlexception;
    if (this.isFree) {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 192);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }
    if (paramLong < 1L)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68, "'pos' should not be < 1");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    if (paramInt1 < 0)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68, "'offset' should not be < 0");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    if (paramInt1 + paramInt2 > paramString.length())
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68, " 'offset + len' should not be exceed string length. ");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    int i = 0;

    if ((paramString != null) && (paramString.length() != 0))
      i = putChars(paramLong, paramString.toCharArray(), paramInt1, paramInt2);
    return i;
  }

  public OutputStream setAsciiStream(long paramLong)
    throws SQLException
  {
    if (this.isFree) {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 192);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }
    return getDBAccess().newOutputStream(this, getBufferSize(), paramLong, true);
  }

  public Writer setCharacterStream(long paramLong)
    throws SQLException
  {
    if (this.isFree) {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 192);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }
    return getDBAccess().newWriter(this, getBufferSize(), paramLong, true);
  }

  public void truncate(long paramLong)
    throws SQLException
  {
    SQLException sqlexception;
    if (this.isFree) {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 192);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }
    if (paramLong < 0L)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68, " 'len' should not be < 0");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    getDBAccess().trim(this, paramLong);
  }

  public Object toJdbc()
    throws SQLException
  {
    if (this.isFree) {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 192);
      sqlexception.fillInStackTrace();
      throw sqlexception;
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
    return getCharacterStream();
  }

  public InputStream asciiStreamValue()
    throws SQLException
  {
    return getAsciiStream();
  }

  public InputStream binaryStreamValue()
    throws SQLException
  {
    return getAsciiStream();
  }

  public String stringValue()
    throws SQLException
  {
    Reader localReader = getCharacterStream();
    int i = getBufferSize();
    int j = 0;
    StringWriter localStringWriter = new StringWriter(i);
    char[] arrayOfChar = new char[i];
    try
    {
      while ((j = localReader.read(arrayOfChar)) != -1)
      {
        localStringWriter.write(arrayOfChar, 0, j);
      }

    }
    catch (IOException localIOException)
    {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localIOException);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }
    catch (IndexOutOfBoundsException localIndexOutOfBoundsException)
    {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 151);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    return localStringWriter.getBuffer().substring(0);
  }

  public Object makeJdbcArray(int paramInt)
  {
    return new CLOB[paramInt];
  }

  public ClobDBAccess getDBAccess()
    throws SQLException
  {
    SQLException sqlexception;
    if (this.dbaccess == null)
    {
      if (isEmptyLob())
      {
        sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 98);
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }

      this.dbaccess = getInternalConnection().createClobDBAccess();
    }

    if (getPhysicalConnection().isClosed())
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 8);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    return this.dbaccess;
  }

  public static ClobDBAccess getDBAccess(Connection paramConnection)
    throws SQLException
  {
    return ((oracle.jdbc.OracleConnection)paramConnection).physicalConnectionWithin().createClobDBAccess();
  }

  public Connection getJavaSqlConnection()
    throws SQLException
  {
    if (this.isFree) {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 192);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }
    return super.getJavaSqlConnection();
  }

  public final void setLength(long paramLong)
  {
    this.cachedLengthOfClobInChars = paramLong;
  }

  public final void setChunkSize(int paramInt)
  {
    this.dbChunkSize = paramInt;
  }

  public final void setPrefetchedData(char[] paramArrayOfChar)
  {
    if (paramArrayOfChar == null)
      setPrefetchedData(null, 0);
    else
      setPrefetchedData(paramArrayOfChar, paramArrayOfChar.length);
  }

  public final void setPrefetchedData(char[] paramArrayOfChar, int paramInt)
  {
    this.prefetchData = paramArrayOfChar;
    this.prefetchDataSize = paramInt;
  }

  public final char[] getPrefetchedData()
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
    this.cachedLengthOfClobInChars = -1L;
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
    if ((k != 0) && (m == 0))
    {
      int i1 = arrayOfByte[88] & 0xFF;
      n = (i1 & 0x8) == 8 ? 1 : 0;
    }
    int i1 = (k != 0) && (m == 0) && (n != 0) ? 1 : 0;
    boolean bool = false;
    if (i1 != 0)
    {
      dilGetCharacterSet();
      bool = !this.dilCharacterSet.isUnknown();
    }
    return bool;
  }

  int dilGetCharSetId()
    throws SQLException
  {
    int i = shareBytes()[32];
    int j = shareBytes()[33];
    int k = (i & 0xFF) << 8 | j & 0xFF;
    return k;
  }

  boolean isMigratedAL16UTF16LE()
  {
    int i = shareBytes()[7] & 0xFF;
    return (i & 0x40) == 64;
  }

  boolean isVariableWidth()
  {
    int i = shareBytes()[6] & 0xFF;
    int j = 128;
    return (i & j) == j;
  }

  void dilGetCharacterSet()
    throws SQLException
  {
    if (this.dilCharacterSet == null)
    {
      if (isMigratedAL16UTF16LE())
      {
        this.dilCharacterSet = CharacterSet.make(2002);
      }
      else if (isVariableWidth())
      {
        this.dilCharacterSet = CharacterSet.make(2000);
      }
      else
      {
        int i = dilGetCharSetId();
        this.dilCharacterSet = CharacterSet.make(i);
      }
    }
  }

  int dilLength()
  {
    return shareBytes().length - 86 - 16;
  }

  char[] dilGetChars()
    throws SQLException
  {
    int i = dilLength();
    byte[] arrayOfByte = new byte[i];
    System.arraycopy(shareBytes(), 102, arrayOfByte, 0, i);
    String str = this.dilCharacterSet.toStringWithReplacement(arrayOfByte, 0, i);
    char[] arrayOfChar = str.toCharArray();
    return arrayOfChar;
  }

  InputStream dilGetAsciiStream(long paramLong)
    throws SQLException
  {
    char[] arrayOfChar = dilGetChars();
    byte[] localObject;
    if (paramLong - 1L > arrayOfChar.length)
    {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68, "");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    if (dilGetCharSetId() == 1)
    {
      localObject = new byte[arrayOfChar.length];
      for (int i = 0; i < arrayOfChar.length; i++)
        localObject[i] = ((byte)arrayOfChar[i]);
    }
    else
    {
      CharacterSet localCharacterSet = CharacterSet.make(1);
      localObject = localCharacterSet.convertWithReplacement(new String(arrayOfChar));
    }
    return new ByteArrayInputStream((byte[])localObject);
  }

  Reader dilGetCharacterStream(long paramLong)
    throws SQLException
  {
    char[] arrayOfChar = dilGetChars();
    int i = arrayOfChar.length;
    if (paramLong - 1L > i)
    {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    return new CharArrayReader(arrayOfChar, (int)(paramLong - 1L), 2147483647);
  }

  String dilGetSubString(long paramLong, int paramInt)
    throws SQLException
  {
    char[] arrayOfChar = dilGetChars();
    if ((int)paramLong > arrayOfChar.length)
    {
      return "";
    }
    int i = (int)Math.min(paramInt, arrayOfChar.length - (paramLong - 1L));
    if (i == 0)
    {
      return "";
    }
    return new String(arrayOfChar, (int)(paramLong - 1L), i);
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

  public Reader getCharacterStream(long paramLong1, long paramLong2)
    throws SQLException
  {
    if (this.isFree) {
      SQLException sqlexception1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 192);
      sqlexception1.fillInStackTrace();
      throw sqlexception1;
    }
    if (canReadBasicLobDataInLocator())
    {
      return dilGetCharacterStream(paramLong1, paramLong2);
    }

    long l = length();
    if ((paramLong1 < 1L) || (paramLong2 < 0L) || (paramLong1 > l) || (paramLong1 - 1L + paramLong2 > l))
    {
      SQLException sqlexception2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68);
      sqlexception2.fillInStackTrace();
      throw sqlexception2;
    }
    return getDBAccess().newReader(this, getChunkSize(), paramLong1, paramLong2);
  }

  Reader dilGetCharacterStream(long paramLong1, long paramLong2)
    throws SQLException
  {
    if ((paramLong1 < 1L) || (paramLong2 < 0L))
    {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }
    char[] localObject = dilGetChars();
    long l = localObject.length;
    if ((paramLong1 < 1L) || (paramLong2 < 0L) || (paramLong1 > l) || (paramLong1 - 1L + paramLong2 > l))
    {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }
    return new CharArrayReader((char[])localObject, (int)(paramLong1 - 1L), (int)paramLong2);
  }
}