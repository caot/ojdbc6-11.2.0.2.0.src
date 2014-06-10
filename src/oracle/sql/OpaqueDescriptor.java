package oracle.sql;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import oracle.jdbc.driver.DatabaseError;
import oracle.jdbc.oracore.OracleNamedType;
import oracle.jdbc.oracore.OracleTypeADT;
import oracle.jdbc.oracore.OracleTypeOPAQUE;

public class OpaqueDescriptor extends TypeDescriptor
  implements Serializable
{
  static final boolean DEBUG = false;
  static final long serialVersionUID = 1013921343538311063L;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  public OpaqueDescriptor(String paramString, Connection paramConnection)
    throws SQLException
  {
    super((short)58, paramString, paramConnection);

    initPickler();
  }

  public OpaqueDescriptor(SQLName paramSQLName, Connection paramConnection)
    throws SQLException
  {
    super((short)58, paramSQLName, paramConnection);

    initPickler();
  }

  public OpaqueDescriptor(SQLName paramSQLName, OracleTypeOPAQUE paramOracleTypeOPAQUE, Connection paramConnection)
    throws SQLException
  {
    super((short)58, paramSQLName, paramOracleTypeOPAQUE, paramConnection);
  }

  public OpaqueDescriptor(OracleTypeADT paramOracleTypeADT, Connection paramConnection)
    throws SQLException
  {
    super((short)58, paramOracleTypeADT, paramConnection);
  }

  OpaqueDescriptor(byte[] paramArrayOfByte, int paramInt, Connection paramConnection)
    throws SQLException
  {
    super((short)108);

    this.toid = paramArrayOfByte;
    this.toidVersion = paramInt;
    setPhysicalConnectionOf(paramConnection);
    initPickler();
  }

  public static OpaqueDescriptor createDescriptor(String paramString, Connection paramConnection)
    throws SQLException
  {
    if ((paramString == null) || (paramString.length() == 0))
    {
      localObject = DatabaseError.createSqlException(null, 60, "Invalid argument,'name' shouldn't be null nor an empty string and 'conn' should not be null");

      ((SQLException)localObject).fillInStackTrace();
      throw ((Throwable)localObject);
    }

    Object localObject = new SQLName(paramString, (oracle.jdbc.OracleConnection)paramConnection);
    return createDescriptor((SQLName)localObject, paramConnection);
  }

  public static OpaqueDescriptor createDescriptor(SQLName paramSQLName, Connection paramConnection)
    throws SQLException
  {
    String str = paramSQLName.getName();

    OpaqueDescriptor localOpaqueDescriptor = null;
    if (paramConnection != null) {
      localOpaqueDescriptor = (OpaqueDescriptor)((oracle.jdbc.OracleConnection)paramConnection).getDescriptor(str);
    }

    if (localOpaqueDescriptor == null)
    {
      OracleTypeOPAQUE localOracleTypeOPAQUE;
      if (str.equals("SYS.ANYTYPE"))
      {
        localOracleTypeOPAQUE = new OracleTypeOPAQUE(TypeDescriptor.ANYTYPETOID, 1, 0, (short)0, str, 7L);

        localOpaqueDescriptor = new OpaqueDescriptor(paramSQLName, localOracleTypeOPAQUE, paramConnection);
      }
      else if (str.equals("SYS.ANYDATA"))
      {
        localOracleTypeOPAQUE = new OracleTypeOPAQUE(TypeDescriptor.ANYDATATOID, 1, 0, (short)0, str, 7L);

        localOpaqueDescriptor = new OpaqueDescriptor(paramSQLName, localOracleTypeOPAQUE, paramConnection);
      }
      else {
        localOpaqueDescriptor = new OpaqueDescriptor(paramSQLName, paramConnection);
      }
      if (paramConnection != null) {
        ((oracle.jdbc.OracleConnection)paramConnection).putDescriptor(str, localOpaqueDescriptor);
      }
    }
    return localOpaqueDescriptor;
  }

  private void initPickler()
    throws SQLException
  {
    try
    {
      this.pickler = new OracleTypeADT(getName(), this.connection);

      ((OracleTypeADT)this.pickler).init(this.connection);

      this.pickler = ((OracleTypeOPAQUE)((OracleTypeADT)this.pickler).cleanup());

      this.pickler.setDescriptor(this);
    }
    catch (Exception localException)
    {
      if ((localException instanceof SQLException)) {
        throw ((SQLException)localException);
      }

      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 60, "Unable to resolve type \"" + getName() + "\"");

      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  byte[] toBytes(OPAQUE paramOPAQUE, boolean paramBoolean)
    throws SQLException
  {
    byte[] arrayOfByte = null;

    if (paramOPAQUE.shareBytes() != null)
    {
      arrayOfByte = paramOPAQUE.shareBytes();
    }
    else
    {
      try
      {
        arrayOfByte = this.pickler.linearize(paramOPAQUE);
      }
      finally
      {
        if (!paramBoolean) {
          paramOPAQUE.setShareBytes(null);
        }
      }
    }
    return arrayOfByte;
  }

  byte[] toValue(OPAQUE paramOPAQUE, boolean paramBoolean)
    throws SQLException
  {
    byte[] arrayOfByte = null;

    if (paramOPAQUE.value != null)
    {
      arrayOfByte = paramOPAQUE.value;
    }
    else
    {
      try
      {
        this.pickler.unlinearize(paramOPAQUE.shareBytes(), 0L, paramOPAQUE, 1, null);

        arrayOfByte = paramOPAQUE.value;
      }
      finally
      {
        if (!paramBoolean)
          paramOPAQUE.value = null;
      }
    }
    return arrayOfByte;
  }

  public int getTypeCode()
    throws SQLException
  {
    if ((this.connection.isGetObjectReturnsXMLType()) && ("SYS.XMLTYPE".equalsIgnoreCase(this.sqlName.getName()))) {
      return 2009;
    }

    return 2007;
  }

  public boolean isInHierarchyOf(String paramString)
    throws SQLException
  {
    OpaqueDescriptor localOpaqueDescriptor = this;
    String str = localOpaqueDescriptor.getName();
    return paramString.equals(str);
  }

  public long getMaxLength()
    throws SQLException
  {
    long l = hasUnboundedSize() ? 0L : ((OracleTypeOPAQUE)this.pickler).getMaxLength();

    return l;
  }

  public boolean isTrustedLibrary()
    throws SQLException
  {
    return ((OracleTypeOPAQUE)this.pickler).isTrustedLibrary();
  }

  public boolean isModeledInC()
    throws SQLException
  {
    return ((OracleTypeOPAQUE)this.pickler).isModeledInC();
  }

  public boolean hasUnboundedSize()
    throws SQLException
  {
    return ((OracleTypeOPAQUE)this.pickler).isUnboundedSized();
  }

  public boolean hasFixedSize()
    throws SQLException
  {
    return ((OracleTypeOPAQUE)this.pickler).isFixedSized();
  }

  public String descType()
    throws SQLException
  {
    StringBuffer localStringBuffer = new StringBuffer();
    return descType(localStringBuffer, 0);
  }

  String descType(StringBuffer paramStringBuffer, int paramInt)
    throws SQLException
  {
    String str1 = "";

    for (int i = 0; i < paramInt; i++) {
      str1 = str1 + "  ";
    }
    String str2 = str1 + "  ";

    paramStringBuffer.append(str1);
    paramStringBuffer.append(getTypeName());
    paramStringBuffer.append(" maxLen=" + getMaxLength() + " isTrusted=" + isTrustedLibrary() + " hasUnboundedSize=" + hasUnboundedSize() + " hasFixedSize=" + hasFixedSize());

    paramStringBuffer.append("\n");

    return paramStringBuffer.toString();
  }

  public Class getClass(Map paramMap)
    throws SQLException
  {
    Object localObject = null;

    String str1 = getName();

    Class localClass = this.connection.getClassForType(str1, paramMap);

    String str2 = getSchemaName();
    String str3 = getTypeName();

    if (localClass == null)
    {
      if (this.connection.getDefaultSchemaNameForNamedTypes().equals(str2)) {
        localClass = (Class)paramMap.get(str3);
      }
    }
    if (!SQLName.s_parseAllFormat)
    {
      localObject = localClass;
    }
    else
    {
      if (localClass == null)
      {
        if (this.connection.getDefaultSchemaNameForNamedTypes().equals(str2)) {
          localClass = (Class)paramMap.get("\"" + str3 + "\"");
        }
      }
      if (localClass == null)
      {
        localClass = (Class)paramMap.get("\"" + str2 + "\"" + "." + "\"" + str3 + "\"");
      }

      if (localClass == null)
      {
        localClass = (Class)paramMap.get("\"" + str2 + "\"" + "." + str3);
      }

      if (localClass == null)
      {
        localClass = (Class)paramMap.get(str2 + "." + "\"" + str3 + "\"");
      }

      localObject = localClass;
    }

    return localObject;
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