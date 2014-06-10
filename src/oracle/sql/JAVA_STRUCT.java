package oracle.sql;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Map;
import oracle.jdbc.driver.DatabaseError;
import oracle.jdbc.internal.OracleConnection;

public class JAVA_STRUCT extends STRUCT
{
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  public JAVA_STRUCT(StructDescriptor paramStructDescriptor, Connection paramConnection, Object[] paramArrayOfObject)
    throws SQLException
  {
    super(paramStructDescriptor, paramConnection, paramArrayOfObject);
  }

  public JAVA_STRUCT(StructDescriptor paramStructDescriptor, byte[] paramArrayOfByte, Connection paramConnection)
    throws SQLException
  {
    super(paramStructDescriptor, paramArrayOfByte, paramConnection);
  }

  public Object toJdbc()
    throws SQLException
  {
    Object localObject = getInternalConnection().getJavaObjectTypeMap();

    Class localClass = null;

    if (localObject != null) {
      localClass = this.descriptor.getClass((Map)localObject);
    }
    else {
      localObject = new Hashtable(10);

      getInternalConnection().setJavaObjectTypeMap((Map)localObject);
    }

    if (localClass == null)
    {
      String str1 = StructDescriptor.getJavaObjectClassName(getInternalConnection(), getDescriptor());

      String str2 = getDescriptor().getSchemaName();

      if ((str1 == null) || (str1.length() == 0))
      {
        SQLException localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 1);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      try
      {
        localClass = getInternalConnection().classForNameAndSchema(str1, str2);
      }
      catch (ClassNotFoundException localClassNotFoundException)
      {
        SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 49, "ClassNotFoundException: " + localClassNotFoundException.getMessage());

        localSQLException2.fillInStackTrace();
        throw localSQLException2;
      }

      ((Map)localObject).put(getSQLTypeName(), localClass);
    }
    return toClass(localClass, getMap());
  }

  public Object toJdbc(Map paramMap)
    throws SQLException
  {
    return toJdbc();
  }
}