package oracle.sql;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import oracle.jdbc.driver.DatabaseError;
import oracle.jdbc.internal.OracleConnection;

public class OPAQUE extends DatumWithConnection
{
  OpaqueDescriptor descriptor;
  byte[] value;
  long imageOffset;
  long imageLength;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  public OPAQUE(OpaqueDescriptor paramOpaqueDescriptor, Connection paramConnection, Object paramObject)
    throws SQLException
  {
    SQLException localSQLException;
    if (paramOpaqueDescriptor != null) {
      this.descriptor = paramOpaqueDescriptor;
    }
    else
    {
      localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 61, "OPAQUE");
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if (paramConnection != null) {
      setPhysicalConnectionOf(paramConnection);
    }
    if ((paramObject instanceof byte[])) {
      this.value = ((byte[])paramObject);
    }
    else
    {
      localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 59);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  public OPAQUE(OpaqueDescriptor paramOpaqueDescriptor, byte[] paramArrayOfByte, Connection paramConnection)
    throws SQLException
  {
    super(paramArrayOfByte);

    setPhysicalConnectionOf(paramConnection);

    this.descriptor = paramOpaqueDescriptor;
    this.value = null;
  }

  public String getSQLTypeName()
    throws SQLException
  {
    return this.descriptor.getName();
  }

  public OpaqueDescriptor getDescriptor()
    throws SQLException
  {
    return this.descriptor;
  }

  public void setDescriptor(OpaqueDescriptor paramOpaqueDescriptor)
  {
    this.descriptor = paramOpaqueDescriptor;
  }

  public byte[] toBytes()
    throws SQLException
  {
    return this.descriptor.toBytes(this, false);
  }

  public Object getValue()
    throws SQLException
  {
    return this.descriptor.toValue(this, false);
  }

  public byte[] getBytesValue()
    throws SQLException
  {
    return this.descriptor.toValue(this, false);
  }

  public void setValue(byte[] paramArrayOfByte)
    throws SQLException
  {
    this.value = paramArrayOfByte;
  }

  public boolean isConvertibleTo(Class paramClass)
  {
    return false;
  }

  public Object makeJdbcArray(int paramInt)
  {
    return new Object[paramInt];
  }

  public Map getMap()
  {
    try
    {
      return getInternalConnection().getTypeMap();
    }
    catch (SQLException localSQLException) {
    }
    return null;
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
      if ((paramClass == null) || (paramClass == OPAQUE.class)) {
        localObject1 = this;
      }
      else {
        ORAData localORAData = null;
        localObject2 = paramClass.newInstance();
        Object localObject3;
        if ((localObject2 instanceof ORADataFactory))
        {
          localObject3 = (ORADataFactory)localObject2;

          localORAData = ((ORADataFactory)localObject3).create(this, 2007);
        }
        else
        {
          localObject3 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 49, this.descriptor.getName());
          ((SQLException)localObject3).fillInStackTrace();
          throw ((Throwable)localObject3);
        }

        localObject1 = localORAData;
      }

    }
    catch (InstantiationException localInstantiationException)
    {
      localObject2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 49, "InstantiationException: " + localInstantiationException.getMessage());

      ((SQLException)localObject2).fillInStackTrace();
      throw ((Throwable)localObject2);
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      Object localObject2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 49, "IllegalAccessException: " + localIllegalAccessException.getMessage());

      ((SQLException)localObject2).fillInStackTrace();
      throw ((Throwable)localObject2);
    }

    return localObject1;
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

  public Connection getJavaSqlConnection()
    throws SQLException
  {
    return super.getJavaSqlConnection();
  }
}