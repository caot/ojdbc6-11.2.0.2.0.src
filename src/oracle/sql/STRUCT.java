package oracle.sql;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.ResultSetMetaData;
import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.sql.Struct;
import java.util.Hashtable;
import java.util.Map;
import oracle.jdbc.driver.DatabaseError;

public class STRUCT extends DatumWithConnection
  implements Struct
{
  StructDescriptor descriptor;
  Datum[] datumArray;
  Object[] objectArray;
  boolean enableLocalCache = false;
  long imageOffset;
  long imageLength;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  public STRUCT(StructDescriptor paramStructDescriptor, Connection paramConnection, Object[] paramArrayOfObject)
    throws SQLException
  {
    assertNotNull(paramStructDescriptor);

    this.descriptor = paramStructDescriptor;

    assertNotNull(paramConnection);
    SQLException localSQLException;
    if (!paramStructDescriptor.getInternalConnection().isDescriptorSharable(((oracle.jdbc.OracleConnection)paramConnection).physicalConnectionWithin()))
    {
      localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 1, "Cannot construct STRUCT instance,invalid connection");

      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    paramStructDescriptor.setConnection(paramConnection);

    if (!this.descriptor.isInstantiable())
    {
      localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 1, "Cannot construct STRUCT instance for a non-instantiable object type");

      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    setPhysicalConnectionOf(paramConnection);

    if (paramArrayOfObject != null)
      this.datumArray = this.descriptor.toArray(paramArrayOfObject);
    else
      this.datumArray = new Datum[this.descriptor.getLength()];
  }

  public STRUCT(StructDescriptor paramStructDescriptor, Connection paramConnection, Map paramMap)
    throws SQLException
  {
    assertNotNull(paramStructDescriptor);

    this.descriptor = paramStructDescriptor;

    assertNotNull(paramConnection);
    SQLException localSQLException;
    if (!paramStructDescriptor.getInternalConnection().isDescriptorSharable(((oracle.jdbc.OracleConnection)paramConnection).physicalConnectionWithin()))
    {
      localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 1, "Cannot construct STRUCT instance,invalid connection");

      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    paramStructDescriptor.setConnection(paramConnection);

    if (!this.descriptor.isInstantiable())
    {
      localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 1, "Cannot construct STRUCT instance for a non-instantiable object type");

      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    setPhysicalConnectionOf(paramConnection);

    this.datumArray = this.descriptor.toOracleArray(paramMap);
  }

  public STRUCT(StructDescriptor paramStructDescriptor, byte[] paramArrayOfByte, Connection paramConnection)
    throws SQLException
  {
    super(paramArrayOfByte);

    assertNotNull(paramStructDescriptor);

    this.descriptor = paramStructDescriptor;

    assertNotNull(paramConnection);

    if (!paramStructDescriptor.getInternalConnection().isDescriptorSharable(((oracle.jdbc.OracleConnection)paramConnection).physicalConnectionWithin()))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 1, "Cannot construct STRUCT instance,invalid connection");

      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    paramStructDescriptor.setConnection(paramConnection);
    setPhysicalConnectionOf(paramConnection);

    this.datumArray = null;
  }

  public String getSQLTypeName()
    throws SQLException
  {
    synchronized (getInternalConnection())
    {
      return this.descriptor.getName();
    }
  }

  public Object[] getAttributes()
    throws SQLException
  {
    synchronized (getInternalConnection())
    {
      Object[] arrayOfObject = getAttributes(getMap());

      return arrayOfObject;
    }
  }

  public Object[] getAttributes(Map paramMap)
    throws SQLException
  {
    synchronized (getInternalConnection())
    {
      return this.descriptor.toArray(this, paramMap, this.enableLocalCache);
    }
  }

  public StructDescriptor getDescriptor()
    throws SQLException
  {
    synchronized (getInternalConnection())
    {
      return this.descriptor;
    }
  }

  public void setDescriptor(StructDescriptor paramStructDescriptor)
  {
    this.descriptor = paramStructDescriptor;
  }

  public Datum[] getOracleAttributes()
    throws SQLException
  {
    synchronized (getInternalConnection())
    {
      return this.descriptor.toOracleArray(this, this.enableLocalCache);
    }
  }

  public Map getMap()
  {
    Map localMap = null;
    try
    {
      localMap = getInternalConnection().getTypeMap();
    }
    catch (SQLException localSQLException)
    {
    }

    return localMap;
  }

  public byte[] toBytes()
    throws SQLException
  {
    synchronized (getInternalConnection())
    {
      return this.descriptor.toBytes(this, this.enableLocalCache);
    }
  }

  public void setDatumArray(Datum[] paramArrayOfDatum)
  {
    try
    {
      this.datumArray = (paramArrayOfDatum == null ? new Datum[this.descriptor.getLength()] : paramArrayOfDatum);
    }
    catch (SQLException localSQLException)
    {
    }
  }

  public void setObjArray(Object[] paramArrayOfObject)
    throws SQLException
  {
    synchronized (getInternalConnection())
    {
      this.objectArray = (paramArrayOfObject == null ? new Object[0] : paramArrayOfObject);
    }
  }

  public static STRUCT toSTRUCT(Object paramObject, oracle.jdbc.OracleConnection paramOracleConnection)
    throws SQLException
  {
    STRUCT localSTRUCT = null;

    if (paramObject != null) {
      if ((paramObject instanceof STRUCT))
      {
        localSTRUCT = (STRUCT)paramObject;
      }
      else if ((paramObject instanceof ORAData))
      {
        localSTRUCT = (STRUCT)((ORAData)paramObject).toDatum(paramOracleConnection);
      }
      else if ((paramObject instanceof CustomDatum))
      {
        localSTRUCT = (STRUCT)((oracle.jdbc.internal.OracleConnection)paramOracleConnection).toDatum((CustomDatum)paramObject);
      }
      else
      {
        Object localObject;
        if ((paramObject instanceof SQLData))
        {
          localObject = (SQLData)paramObject;

          StructDescriptor localStructDescriptor = StructDescriptor.createDescriptor(((SQLData)localObject).getSQLTypeName(), paramOracleConnection);

          SQLOutput localSQLOutput = localStructDescriptor.toJdbc2SQLOutput();

          ((SQLData)localObject).writeSQL(localSQLOutput);

          localSTRUCT = ((OracleSQLOutput)localSQLOutput).getSTRUCT();
        }
        else
        {
          SQLException sqlexception = DatabaseError.createSqlException(null, 59, paramObject);
          sqlexception.fillInStackTrace();
          throw sqlexception;
        }
      }
    }
    return localSTRUCT;
  }

  public Object toJdbc()
    throws SQLException
  {
    Map localMap = getMap();
    return toJdbc(localMap);
  }

  public Object toJdbc(Map paramMap)
    throws SQLException
  {
    Object localObject = this;

    if (paramMap != null)
    {
      Class localClass = this.descriptor.getClass(paramMap);

      if (localClass != null) {
        localObject = toClass(localClass, paramMap);
      }
    }
    return localObject;
  }

  public Object toClass(Class paramClass)
    throws SQLException
  {
    return toClass(paramClass, getMap());
  }

  public Object toClass(Class paramClass, Map paramMap)
    throws SQLException
  {
    Object localObject1 = null;
    try
    {
      if ((paramClass == null) || (paramClass == STRUCT.class) || (paramClass == Struct.class))
      {
        localObject1 = this;
      }
      else
      {
        Object localObject2 = paramClass.newInstance();

        if ((localObject2 instanceof SQLData))
        {
          ((SQLData)localObject2).readSQL(this.descriptor.toJdbc2SQLInput(this, paramMap), this.descriptor.getName());

          localObject1 = localObject2;
        }
        else if ((localObject2 instanceof ORADataFactory))
        {
          ORADataFactory localObject3 = (ORADataFactory)localObject2;

          localObject1 = localObject3.create(this, 2002);
        }
        else if ((localObject2 instanceof CustomDatumFactory))
        {
          CustomDatumFactory localObject3 = (CustomDatumFactory)localObject2;

          localObject1 = localObject3.create(this, 2002);
        }
        else
        {
          SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 49, this.descriptor.getName());
          sqlexception.fillInStackTrace();
          throw sqlexception;
        }

      }

    }
    catch (InstantiationException localInstantiationException)
    {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 49, "InstantiationException: " + localInstantiationException.getMessage());

      sqlexception.fillInStackTrace();
      throw sqlexception;
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 49, "IllegalAccessException: " + localIllegalAccessException.getMessage());

      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    return localObject1;
  }

  public boolean isConvertibleTo(Class paramClass)
  {
    return false;
  }

  public Object makeJdbcArray(int paramInt)
  {
    return new Object[paramInt];
  }

  public void setAutoBuffering(boolean paramBoolean)
    throws SQLException
  {
    synchronized (getInternalConnection())
    {
      this.enableLocalCache = paramBoolean;
    }
  }

  public boolean getAutoBuffering()
    throws SQLException
  {
    synchronized (getInternalConnection())
    {
      return this.enableLocalCache;
    }
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

  public CustomDatumFactory getFactory(Hashtable paramHashtable, String paramString)
    throws SQLException
  {
    String str = getSQLTypeName();
    Object localObject = paramHashtable.get(str);

    if (localObject == null)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 1, "Unable to convert a \"" + str + "\" to a \"" + paramString + "\" or a subclass of \"" + paramString + "\"");

      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    return (CustomDatumFactory)localObject;
  }

  public ORADataFactory getORADataFactory(Hashtable paramHashtable, String paramString)
    throws SQLException
  {
    String str = getSQLTypeName();
    Object localObject = paramHashtable.get(str);

    if (localObject == null)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 1, "Unable to convert a \"" + str + "\" to a \"" + paramString + "\" or a subclass of \"" + paramString + "\"");

      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    return (ORADataFactory)localObject;
  }

  public String debugString()
  {
    StringWriter localStringWriter = new StringWriter();
    String str = null;
    try
    {
      StructDescriptor localStructDescriptor = getDescriptor();

      localStringWriter.write("name = " + localStructDescriptor.getName());
      int i;
      localStringWriter.write(" length = " + (i = localStructDescriptor.getLength()));

      Object[] arrayOfObject = getAttributes();

      for (int j = 0; j < i; j++)
      {
        localStringWriter.write(" attribute[" + j + "] = " + arrayOfObject[j]);
      }
      str = localStringWriter.toString();
    }
    catch (SQLException localSQLException)
    {
      str = "StructDescriptor missing or bad";
    }
    return str;
  }

  public boolean isInHierarchyOf(String paramString)
    throws SQLException
  {
    return getDescriptor().isInHierarchyOf(paramString);
  }

  public Connection getJavaSqlConnection()
    throws SQLException
  {
    return super.getJavaSqlConnection();
  }

  public String dump()
    throws SQLException
  {
    return dump(this);
  }

  public static String dump(Object paramObject)
    throws SQLException
  {
    StringWriter localStringWriter = new StringWriter();
    PrintWriter localPrintWriter = new PrintWriter(localStringWriter);
    dump(paramObject, localPrintWriter);
    return localStringWriter.getBuffer().substring(0);
  }

  public static void dump(Object paramObject, PrintStream paramPrintStream) throws SQLException
  {
    dump(paramObject, new PrintWriter(paramPrintStream, true));
  }

  public static void dump(Object paramObject, PrintWriter paramPrintWriter) throws SQLException
  {
    dump(paramObject, paramPrintWriter, 0);
  }

  static void dump(Object paramObject, PrintWriter paramPrintWriter, int paramInt) throws SQLException
  {
    if ((paramObject instanceof STRUCT)) { dump((STRUCT)paramObject, paramPrintWriter, paramInt); return; }
    if ((paramObject instanceof ARRAY)) { ARRAY.dump((ARRAY)paramObject, paramPrintWriter, paramInt); return; }
    if (paramObject == null)
      paramPrintWriter.println("null");
    else
      paramPrintWriter.println(paramObject.toString());
  }

  static void dump(STRUCT paramSTRUCT, PrintWriter paramPrintWriter, int paramInt)
    throws SQLException
  {
    StructDescriptor localStructDescriptor = paramSTRUCT.getDescriptor();
    ResultSetMetaData localResultSetMetaData = localStructDescriptor.getMetaData();

    for (int j = 0; j < paramInt; j++) paramPrintWriter.print(' ');
    paramPrintWriter.println("name = " + localStructDescriptor.getName());

    int j;
    for (j = 0; j < paramInt; j++) paramPrintWriter.print(' ');
    int i;
    paramPrintWriter.println("length = " + (i = localStructDescriptor.getLength()));
    Object[] arrayOfObject = paramSTRUCT.getAttributes();
    for (j = 0; j < i; j++)
    {
      for (int k = 0; k < paramInt; k++) paramPrintWriter.print(' ');
      paramPrintWriter.print(localResultSetMetaData.getColumnName(j + 1) + " = ");
      dump(arrayOfObject[j], paramPrintWriter, paramInt + 1);
    }
  }
}