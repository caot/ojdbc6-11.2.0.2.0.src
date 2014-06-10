package oracle.sql;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.SQLException;
import java.util.Map;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;
import oracle.jdbc.driver.DatabaseError;
import oracle.jdbc.internal.OracleConnection;
import oracle.jdbc.oracore.OracleTypeADT;

public class REF extends DatumWithConnection
  implements Ref, Serializable, Cloneable
{
  static final boolean DEBUG = false;
  static final long serialVersionUID = 1328446996944583167L;
  String typename;
  transient StructDescriptor descriptor;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  public String getBaseTypeName()
    throws SQLException
  {
    if (this.typename == null)
    {
      if (this.descriptor != null) {
        this.typename = this.descriptor.getName();
      }
      else
      {
        SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 52);
        localSQLException.fillInStackTrace();
        throw localSQLException;
      }

    }

    return this.typename;
  }

  public REF(String paramString, Connection paramConnection, byte[] paramArrayOfByte)
    throws SQLException
  {
    super(paramArrayOfByte);

    if ((paramConnection == null) || (paramString == null))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.typename = paramString;
    this.descriptor = null;

    setPhysicalConnectionOf(paramConnection);
  }

  public REF(StructDescriptor paramStructDescriptor, Connection paramConnection, byte[] paramArrayOfByte)
    throws SQLException
  {
    super(paramArrayOfByte);

    if ((paramConnection == null) || (paramStructDescriptor == null))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.descriptor = paramStructDescriptor;

    setPhysicalConnectionOf(paramConnection);
  }

  public Object getValue(Map paramMap)
    throws SQLException
  {
    STRUCT localSTRUCT = getSTRUCT();
    Object localObject = localSTRUCT != null ? localSTRUCT.toJdbc(paramMap) : null;

    return localObject;
  }

  public Object getValue()
    throws SQLException
  {
    STRUCT localSTRUCT = getSTRUCT();
    Object localObject = localSTRUCT != null ? localSTRUCT.toJdbc() : null;
    return localObject;
  }

  public STRUCT getSTRUCT()
    throws SQLException
  {
    synchronized (getInternalConnection())
    {
      STRUCT localSTRUCT = null;

      OraclePreparedStatement localOraclePreparedStatement = (OraclePreparedStatement)getInternalConnection().prepareStatement("select deref(:1) from dual");

      localOraclePreparedStatement.setRowPrefetch(1);
      localOraclePreparedStatement.setREF(1, this);

      OracleResultSet localOracleResultSet = (OracleResultSet)localOraclePreparedStatement.executeQuery();
      try
      {
        if (localOracleResultSet.next()) {
          localSTRUCT = localOracleResultSet.getSTRUCT(1);
        }
        else
        {
          SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 52);
          localSQLException.fillInStackTrace();
          throw localSQLException;
        }

      }
      finally
      {
        localOracleResultSet.close();

        localOracleResultSet = null;

        localOraclePreparedStatement.close();

        localOraclePreparedStatement = null;
      }
      return localSTRUCT;
    }
  }

  public void setValue(Object paramObject)
    throws SQLException
  {
    synchronized (getInternalConnection())
    {
      STRUCT localSTRUCT = STRUCT.toSTRUCT(paramObject, getInternalConnection());

      if (localSTRUCT.getInternalConnection() != getInternalConnection())
      {
        localObject1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 77, "Incompatible connection object");
        ((SQLException)localObject1).fillInStackTrace();
        throw ((Throwable)localObject1);
      }

      if (!getBaseTypeName().equals(localSTRUCT.getSQLTypeName()))
      {
        localObject1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 77, "Incompatible type");
        ((SQLException)localObject1).fillInStackTrace();
        throw ((Throwable)localObject1);
      }

      Object localObject1 = localSTRUCT.toBytes();

      byte[] arrayOfByte = localSTRUCT.getDescriptor().getOracleTypeADT().getTOID();

      CallableStatement localCallableStatement = null;
      try
      {
        localCallableStatement = getInternalConnection().prepareCall("begin :1 := dbms_pickler.update_through_ref (:2, :3, :4, :5); end;");

        localCallableStatement.registerOutParameter(1, 2);

        localCallableStatement.setBytes(2, shareBytes());
        localCallableStatement.setInt(3, 0);
        localCallableStatement.setBytes(4, arrayOfByte);
        localCallableStatement.setBytes(5, (byte[])localObject1);

        localCallableStatement.execute();

        int i = 0;

        if ((i = localCallableStatement.getInt(1)) != 0)
        {
          SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 77, "ORA-" + i);
          localSQLException.fillInStackTrace();
          throw localSQLException;
        }

      }
      finally
      {
        if (localCallableStatement != null) {
          localCallableStatement.close();
        }
        localCallableStatement = null;
      }
    }
  }

  public StructDescriptor getDescriptor()
    throws SQLException
  {
    if (this.descriptor == null)
    {
      this.descriptor = StructDescriptor.createDescriptor(this.typename, getInternalConnection());
    }

    return this.descriptor;
  }

  public String getSQLTypeName()
    throws SQLException
  {
    String str = getBaseTypeName();
    return str;
  }

  public Object getObject(Map paramMap)
    throws SQLException
  {
    STRUCT localSTRUCT = getSTRUCT();
    Object localObject = localSTRUCT != null ? localSTRUCT.toJdbc(paramMap) : null;
    return localObject;
  }

  public Object getObject()
    throws SQLException
  {
    STRUCT localSTRUCT = getSTRUCT();
    Object localObject = localSTRUCT != null ? localSTRUCT.toJdbc() : null;
    return localObject;
  }

  public void setObject(Object paramObject)
    throws SQLException
  {
    PreparedStatement localPreparedStatement = null;
    try
    {
      localPreparedStatement = getInternalConnection().prepareStatement("call sys.utl_ref.update_object( :1, :2 )");

      localPreparedStatement.setRef(1, this);
      localPreparedStatement.setObject(2, paramObject);
      localPreparedStatement.execute(); } finally {
      if (localPreparedStatement != null) localPreparedStatement.close();
    }
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
    return new REF[paramInt];
  }

  public Object clone()
    throws CloneNotSupportedException
  {
    REF localREF = null;
    try
    {
      localREF = new REF(getBaseTypeName(), getInternalConnection(), getBytes());
    }
    catch (SQLException localSQLException)
    {
      throw new CloneNotSupportedException(localSQLException.getMessage());
    }

    return localREF;
  }

  public boolean equals(Object paramObject)
  {
    boolean bool = false;
    try
    {
      bool = ((paramObject instanceof REF)) && (super.equals(paramObject)) && (getBaseTypeName().equals(((REF)paramObject).getSQLTypeName()));
    }
    catch (Exception localException)
    {
    }

    return bool;
  }

  public int hashCode()
  {
    byte[] arrayOfByte = shareBytes();
    int i = 0;
    int j;
    if ((arrayOfByte[2] & 0x5) == 5)
    {
      for (j = 0; j < 4; j++)
      {
        i *= 256;
        i += (arrayOfByte[(8 + j)] & 0xFF);
      }
    }
    else if ((arrayOfByte[2] & 0x3) == 3)
    {
      for (j = 0; (j < 4) && (j < arrayOfByte.length); j++)
      {
        i *= 256;
        i += (arrayOfByte[(6 + j)] & 0xFF);
      }
    }
    else if ((arrayOfByte[2] & 0x2) == 2)
    {
      for (j = 0; j < 4; j++)
      {
        i *= 256;
        i += (arrayOfByte[(8 + j)] & 0xFF);
      }
    }
    return i;
  }

  private void writeObject(ObjectOutputStream paramObjectOutputStream)
    throws IOException
  {
    paramObjectOutputStream.writeObject(shareBytes());
    try
    {
      paramObjectOutputStream.writeUTF(getBaseTypeName());
    }
    catch (SQLException localSQLException)
    {
      throw new IOException(localSQLException.getMessage());
    }
  }

  private void readObject(ObjectInputStream paramObjectInputStream)
    throws IOException, ClassNotFoundException
  {
    setBytes((byte[])paramObjectInputStream.readObject());

    this.typename = paramObjectInputStream.readUTF();
  }

  public Connection getJavaSqlConnection()
    throws SQLException
  {
    return super.getJavaSqlConnection();
  }
}