package oracle.sql;

import java.io.PrintWriter;
import java.sql.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import oracle.jdbc.driver.DatabaseError;

public class ARRAY extends DatumWithConnection
  implements Array
{
  static final byte KOPUP_INLINE_COLL = 1;
  ArrayDescriptor descriptor;
  Object objArray;
  Datum[] datumArray;
  byte[] locator;
  byte prefixFlag;
  byte[] prefixSegment;
  int numElems = -1;

  boolean enableBuffering = false;
  boolean enableIndexing = false;
  public static final int ACCESS_FORWARD = 1;
  public static final int ACCESS_REVERSE = 2;
  public static final int ACCESS_UNKNOWN = 3;
  int accessDirection = 3;
  long lastIndex;
  long lastOffset;
  long[] indexArray;
  long imageOffset;
  long imageLength;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  public ARRAY(ArrayDescriptor paramArrayDescriptor, Connection paramConnection, Object paramObject)
    throws SQLException
  {
    assertNotNull(paramArrayDescriptor);

    this.descriptor = paramArrayDescriptor;

    assertNotNull(paramConnection);

    if (!paramArrayDescriptor.getInternalConnection().isDescriptorSharable(((oracle.jdbc.OracleConnection)paramConnection).physicalConnectionWithin()))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 1, "Cannot construct ARRAY instance,invalid connection");

      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    paramArrayDescriptor.setConnection(paramConnection);
    setPhysicalConnectionOf(paramConnection);

    if (paramObject == null)
      this.datumArray = new Datum[0];
    else
      this.datumArray = this.descriptor.toOracleArray(paramObject, 1L, -1);
  }

  public ARRAY(ArrayDescriptor paramArrayDescriptor, byte[] paramArrayOfByte, Connection paramConnection)
    throws SQLException
  {
    super(paramArrayOfByte);

    assertNotNull(paramArrayDescriptor);

    this.descriptor = paramArrayDescriptor;

    assertNotNull(paramConnection);

    if (!paramArrayDescriptor.getInternalConnection().isDescriptorSharable(((oracle.jdbc.OracleConnection)paramConnection).physicalConnectionWithin()))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 1, "Cannot construct ARRAY instance,invalid connection");

      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    paramArrayDescriptor.setConnection(paramConnection);
    setPhysicalConnectionOf(paramConnection);

    this.datumArray = null;
    this.locator = null;
  }

  public static ARRAY toARRAY(Object paramObject, oracle.jdbc.OracleConnection paramOracleConnection)
    throws SQLException
  {
    ARRAY localARRAY = null;

    if (paramObject != null) {
      if ((paramObject instanceof ARRAY))
      {
        localARRAY = (ARRAY)paramObject;
      }
      else if ((paramObject instanceof ORAData))
      {
        localARRAY = (ARRAY)((ORAData)paramObject).toDatum(paramOracleConnection);
      }
      else if ((paramObject instanceof CustomDatum))
      {
        localARRAY = (ARRAY)paramOracleConnection.physicalConnectionWithin().toDatum((CustomDatum)paramObject);
      }
      else
      {
        SQLException localSQLException = DatabaseError.createSqlException(null, 59, paramObject);
        localSQLException.fillInStackTrace();
        throw localSQLException;
      }
    }
    return localARRAY;
  }

  public String getBaseTypeName()
    throws SQLException
  {
    synchronized (getInternalConnection())
    {
      return this.descriptor.getBaseName();
    }
  }

  public int getBaseType()
    throws SQLException
  {
    synchronized (getInternalConnection())
    {
      return this.descriptor.getBaseType();
    }
  }

  public Object getArray()
    throws SQLException
  {
    synchronized (getInternalConnection())
    {
      return this.descriptor.toJavaArray(this, 1L, -1, getMap(), this.enableBuffering);
    }
  }

  public Object getArray(Map paramMap)
    throws SQLException
  {
    synchronized (getInternalConnection())
    {
      return this.descriptor.toJavaArray(this, 1L, -1, paramMap, this.enableBuffering);
    }
  }

  public Object getArray(long paramLong, int paramInt)
    throws SQLException
  {
    synchronized (getInternalConnection())
    {
      if ((paramLong < 1L) || (paramInt < 0))
      {
        SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68, "Invalid arguments,'index' should be >= 1 and 'count' >= 0. An exception is thrown.");

        localSQLException.fillInStackTrace();
        throw localSQLException;
      }

      return this.descriptor.toJavaArray(this, paramLong, paramInt, getMap(), false);
    }
  }

  public Object getArray(long paramLong, int paramInt, Map paramMap)
    throws SQLException
  {
    synchronized (getInternalConnection())
    {
      if ((paramLong < 1L) || (paramInt < 0))
      {
        SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68, "Invalid arguments,'index' should be >= 1 and 'count' >= 0. An exception is thrown.");

        localSQLException.fillInStackTrace();
        throw localSQLException;
      }

      return this.descriptor.toJavaArray(this, paramLong, paramInt, paramMap, false);
    }
  }

  public ResultSet getResultSet()
    throws SQLException
  {
    synchronized (getInternalConnection())
    {
      return getResultSet(getInternalConnection().getTypeMap());
    }
  }

  public ResultSet getResultSet(Map paramMap)
    throws SQLException
  {
    synchronized (getInternalConnection())
    {
      return this.descriptor.toResultSet(this, 1L, -1, paramMap, this.enableBuffering);
    }
  }

  public ResultSet getResultSet(long paramLong, int paramInt)
    throws SQLException
  {
    synchronized (getInternalConnection())
    {
      return getResultSet(paramLong, paramInt, getInternalConnection().getTypeMap());
    }
  }

  public ResultSet getResultSet(long paramLong, int paramInt, Map paramMap)
    throws SQLException
  {
    synchronized (getInternalConnection())
    {
      if ((paramLong < 1L) || (paramInt < -1))
      {
        SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68, "getResultSet()");
        localSQLException.fillInStackTrace();
        throw localSQLException;
      }

      return this.descriptor.toResultSet(this, paramLong, paramInt, paramMap, false);
    }
  }

  public Datum[] getOracleArray()
    throws SQLException
  {
    synchronized (getInternalConnection())
    {
      return this.descriptor.toOracleArray(this, 1L, -1, this.enableBuffering);
    }
  }

  public int length()
    throws SQLException
  {
    synchronized (getInternalConnection())
    {
      return this.descriptor.toLength(this);
    }
  }

  public Datum[] getOracleArray(long paramLong, int paramInt)
    throws SQLException
  {
    synchronized (getInternalConnection())
    {
      if ((paramLong < 1L) || (paramInt < 0))
      {
        SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68, "getOracleArray()");
        localSQLException.fillInStackTrace();
        throw localSQLException;
      }

      return this.descriptor.toOracleArray(this, paramLong, paramInt, false);
    }
  }

  public String getSQLTypeName()
    throws SQLException
  {
    synchronized (getInternalConnection())
    {
      String str = null;

      if (this.descriptor != null)
      {
        str = this.descriptor.getName();
      }
      else
      {
        SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 61, "ARRAY");
        localSQLException.fillInStackTrace();
        throw localSQLException;
      }

      return str;
    }
  }

  public Map getMap()
    throws SQLException
  {
    return getInternalConnection().getTypeMap();
  }

  public ArrayDescriptor getDescriptor()
    throws SQLException
  {
    return this.descriptor;
  }

  public byte[] toBytes()
    throws SQLException
  {
    synchronized (getInternalConnection())
    {
      return this.descriptor.toBytes(this, this.enableBuffering);
    }
  }

  public void setDatumArray(Datum[] paramArrayOfDatum)
  {
    this.datumArray = paramArrayOfDatum;
  }

  public void setObjArray(Object paramObject)
    throws SQLException
  {
    synchronized (getInternalConnection())
    {
      if (paramObject == null)
      {
        SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 1, "Invalid argument,'oarray' should not be null. An exception is thrown.");

        localSQLException.fillInStackTrace();
        throw localSQLException;
      }

      this.objArray = paramObject;
    }
  }

  public void setLocator(byte[] paramArrayOfByte)
  {
    if ((paramArrayOfByte != null) && (paramArrayOfByte.length != 0))
      this.locator = paramArrayOfByte;
  }

  public void setPrefixSegment(byte[] paramArrayOfByte)
  {
    if ((paramArrayOfByte != null) && (paramArrayOfByte.length != 0))
      this.prefixSegment = paramArrayOfByte;
  }

  public void setPrefixFlag(byte paramByte)
  {
    this.prefixFlag = paramByte;
  }

  public byte[] getLocator()
  {
    return this.locator;
  }

  public void setLength(int paramInt)
  {
    this.numElems = paramInt;
  }

  public boolean hasDataSeg()
  {
    return this.locator == null;
  }

  public boolean isInline()
  {
    return (this.prefixFlag & 0x1) == 1;
  }

  public Object toJdbc()
    throws SQLException
  {
    return this;
  }

  public boolean isConvertibleTo(Class paramClass)
  {
    return false;
  }

  public Object makeJdbcArray(int paramInt)
  {
    return new Object[paramInt][];
  }

  public int[] getIntArray()
    throws SQLException
  {
    synchronized (getInternalConnection())
    {
      return (int[])this.descriptor.toNumericArray(this, 1L, -1, 4, this.enableBuffering);
    }
  }

  public int[] getIntArray(long paramLong, int paramInt)
    throws SQLException
  {
    synchronized (getInternalConnection())
    {
      return (int[])this.descriptor.toNumericArray(this, paramLong, paramInt, 4, false);
    }
  }

  public double[] getDoubleArray()
    throws SQLException
  {
    synchronized (getInternalConnection())
    {
      return (double[])this.descriptor.toNumericArray(this, 1L, -1, 5, this.enableBuffering);
    }
  }

  public double[] getDoubleArray(long paramLong, int paramInt)
    throws SQLException
  {
    synchronized (getInternalConnection())
    {
      return (double[])this.descriptor.toNumericArray(this, paramLong, paramInt, 5, false);
    }
  }

  public short[] getShortArray()
    throws SQLException
  {
    synchronized (getInternalConnection())
    {
      return (short[])this.descriptor.toNumericArray(this, 1L, -1, 8, this.enableBuffering);
    }
  }

  public short[] getShortArray(long paramLong, int paramInt)
    throws SQLException
  {
    synchronized (getInternalConnection())
    {
      return (short[])this.descriptor.toNumericArray(this, paramLong, paramInt, 8, false);
    }
  }

  public long[] getLongArray()
    throws SQLException
  {
    synchronized (getInternalConnection())
    {
      return (long[])this.descriptor.toNumericArray(this, 1L, -1, 7, this.enableBuffering);
    }
  }

  public long[] getLongArray(long paramLong, int paramInt)
    throws SQLException
  {
    synchronized (getInternalConnection())
    {
      return (long[])this.descriptor.toNumericArray(this, paramLong, paramInt, 7, false);
    }
  }

  public float[] getFloatArray()
    throws SQLException
  {
    synchronized (getInternalConnection())
    {
      return (float[])this.descriptor.toNumericArray(this, 1L, -1, 6, this.enableBuffering);
    }
  }

  public float[] getFloatArray(long paramLong, int paramInt)
    throws SQLException
  {
    synchronized (getInternalConnection())
    {
      return (float[])this.descriptor.toNumericArray(this, paramLong, paramInt, 6, false);
    }
  }

  public void setAutoBuffering(boolean paramBoolean)
    throws SQLException
  {
    synchronized (getInternalConnection())
    {
      this.enableBuffering = paramBoolean;
    }
  }

  public boolean getAutoBuffering()
    throws SQLException
  {
    return this.enableBuffering;
  }

  public void setAutoIndexing(boolean paramBoolean, int paramInt)
    throws SQLException
  {
    synchronized (getInternalConnection())
    {
      this.enableIndexing = paramBoolean;
      this.accessDirection = paramInt;
    }
  }

  public void setAutoIndexing(boolean paramBoolean)
    throws SQLException
  {
    synchronized (getInternalConnection())
    {
      this.enableIndexing = paramBoolean;
      this.accessDirection = 3;
    }
  }

  public boolean getAutoIndexing()
    throws SQLException
  {
    return this.enableIndexing;
  }

  public int getAccessDirection()
    throws SQLException
  {
    return this.accessDirection;
  }

  public void setLastIndexOffset(long paramLong1, long paramLong2)
    throws SQLException
  {
    this.lastIndex = paramLong1;
    this.lastOffset = paramLong2;
  }

  public void setIndexOffset(long paramLong1, long paramLong2)
    throws SQLException
  {
    if (this.indexArray == null) {
      this.indexArray = new long[this.numElems];
    }
    this.indexArray[((int)paramLong1 - 1)] = paramLong2;
  }

  public long getLastIndex()
    throws SQLException
  {
    return this.lastIndex;
  }

  public long getLastOffset()
    throws SQLException
  {
    return this.lastOffset;
  }

  public long getOffset(long paramLong)
    throws SQLException
  {
    long l = -1L;

    if (this.indexArray != null) {
      l = this.indexArray[((int)paramLong - 1)];
    }
    return l;
  }

  public void setImage(byte[] paramArrayOfByte, long paramLong1, long paramLong2)
    throws SQLException
  {
    setShareBytes(paramArrayOfByte);

    this.imageOffset = paramLong1;
    this.imageLength = paramLong2;
  }

  public void setImageLength(long paramLong)
    throws SQLException
  {
    this.imageLength = paramLong;
  }

  public long getImageOffset()
  {
    return this.imageOffset;
  }

  public long getImageLength()
  {
    return this.imageLength;
  }

  public String dump()
    throws SQLException
  {
    return STRUCT.dump(this);
  }

  static void dump(ARRAY paramARRAY, PrintWriter paramPrintWriter, int paramInt)
    throws SQLException
  {
    if (paramInt > 0) paramPrintWriter.println();

    ArrayDescriptor localArrayDescriptor = paramARRAY.getDescriptor();
    for (int j = 0; j < paramInt; j++) paramPrintWriter.print(' ');
    paramPrintWriter.println("name = " + localArrayDescriptor.getName());

    int j;
    for (j = 0; j < paramInt; j++) paramPrintWriter.print(' ');
    paramPrintWriter.println("max length = " + localArrayDescriptor.getMaxLength());
    Object[] arrayOfObject = (Object[])paramARRAY.getArray();
    for (j = 0; j < paramInt; j++) paramPrintWriter.print(' ');
    int i;
    paramPrintWriter.println("length = " + (i = arrayOfObject.length));
    for (j = 0; j < i; j++)
    {
      for (int k = 0; k < paramInt; k++) paramPrintWriter.print(' ');
      paramPrintWriter.print("element[" + j + "] = ");
      STRUCT.dump(arrayOfObject[j], paramPrintWriter, paramInt + 4);
    }
  }

  public void free()
    throws SQLException
  {
  }
}