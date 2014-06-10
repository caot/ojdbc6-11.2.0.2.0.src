package oracle.jdbc.oracore;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Map;
import oracle.jdbc.driver.DatabaseError;
import oracle.jdbc.internal.OracleConnection;
import oracle.sql.Datum;
import oracle.sql.REF;
import oracle.sql.StructDescriptor;

public class OracleTypeREF extends OracleNamedType
  implements Serializable
{
  static final long serialVersionUID = 3186448715463064573L;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  protected OracleTypeREF()
  {
  }

  public OracleTypeREF(String paramString, OracleConnection paramOracleConnection)
    throws SQLException
  {
    super(paramString, paramOracleConnection);
  }

  public OracleTypeREF(OracleTypeADT paramOracleTypeADT, int paramInt, OracleConnection paramOracleConnection)
  {
    super(paramOracleTypeADT, paramInt, paramOracleConnection);
  }

  public Datum toDatum(Object paramObject, OracleConnection paramOracleConnection)
    throws SQLException
  {
    REF localREF = null;

    if (paramObject != null)
    {
      if ((paramObject instanceof REF)) {
        localREF = (REF)paramObject;
      }
      else {
        SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 59, paramObject);
        localSQLException.fillInStackTrace();
        throw localSQLException;
      }
    }

    return localREF;
  }

  public int getTypeCode()
  {
    return 2006;
  }

  protected Object toObject(byte[] paramArrayOfByte, int paramInt, Map paramMap)
    throws SQLException
  {
    if ((paramArrayOfByte == null) || (paramArrayOfByte.length == 0)) {
      return null;
    }
    if ((paramInt == 1) || (paramInt == 2))
    {
      localObject = createStructDescriptor();

      return new REF((StructDescriptor)localObject, this.connection, paramArrayOfByte);
    }
    if (paramInt == 3)
    {
      return paramArrayOfByte;
    }

    Object localObject = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 59, paramArrayOfByte);
    ((SQLException)localObject).fillInStackTrace();
    throw ((Throwable)localObject);
  }

  StructDescriptor createStructDescriptor()
    throws SQLException
  {
    if (this.descriptor == null)
    {
      if ((this.sqlName == null) && (getFullName(false) == null))
      {
        OracleTypeADT localOracleTypeADT = new OracleTypeADT(getParent(), getOrder(), this.connection);

        this.descriptor = new StructDescriptor(localOracleTypeADT, this.connection);
      }
      else
      {
        this.descriptor = StructDescriptor.createDescriptor(this.sqlName, this.connection);
      }
    }
    return (StructDescriptor)this.descriptor;
  }

  private void writeObject(ObjectOutputStream paramObjectOutputStream)
    throws IOException
  {
  }

  private void readObject(ObjectInputStream paramObjectInputStream)
    throws IOException, ClassNotFoundException
  {
  }
}