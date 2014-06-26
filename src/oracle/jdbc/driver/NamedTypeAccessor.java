package oracle.jdbc.driver;

import java.sql.SQLException;
import java.sql.SQLXML;
import java.util.Map;
import oracle.jdbc.oracore.OracleType;
import oracle.jdbc.oracore.OracleTypeADT;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import oracle.sql.Datum;
import oracle.sql.JAVA_STRUCT;
import oracle.sql.OPAQUE;
import oracle.sql.OpaqueDescriptor;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;
import oracle.sql.TypeDescriptor;
import oracle.xdb.XMLType;

class NamedTypeAccessor extends TypeAccessor
{
  static Class localClass;
  private static final Class xmlType = localClass;

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  NamedTypeAccessor(OracleStatement paramOracleStatement, String paramString, short paramShort, int paramInt, boolean paramBoolean)
    throws SQLException
  {
    init(paramOracleStatement, 109, 109, paramShort, paramBoolean);
    initForDataAccess(paramInt, 0, paramString);
  }

  NamedTypeAccessor(OracleStatement paramOracleStatement, int paramInt1, boolean paramBoolean, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, short paramShort, String paramString)
    throws SQLException
  {
    init(paramOracleStatement, 109, 109, paramShort, false);
    initForDescribe(109, paramInt1, paramBoolean, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramShort, paramString);

    initForDataAccess(0, paramInt1, paramString);
  }

  NamedTypeAccessor(OracleStatement paramOracleStatement, int paramInt1, boolean paramBoolean, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, short paramShort, String paramString, OracleType paramOracleType)
    throws SQLException
  {
    init(paramOracleStatement, 109, 109, paramShort, false);

    this.describeOtype = paramOracleType;

    initForDescribe(109, paramInt1, paramBoolean, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramShort, paramString);

    this.internalOtype = paramOracleType;

    initForDataAccess(0, paramInt1, paramString);
  }

  OracleType otypeFromName(String paramString)
    throws SQLException
  {
    if (!this.outBind) {
      return TypeDescriptor.getTypeDescriptor(paramString, this.statement.connection).getPickler();
    }
    if (this.externalType == 2003) {
      return ArrayDescriptor.createDescriptor(paramString, this.statement.connection).getOracleTypeCOLLECTION();
    }
    if ((this.externalType == 2007) || (this.externalType == 2009))
    {
      return OpaqueDescriptor.createDescriptor(paramString, this.statement.connection).getPickler();
    }

    return StructDescriptor.createDescriptor(paramString, this.statement.connection).getOracleTypeADT();
  }

  void initForDataAccess(int paramInt1, int paramInt2, String paramString)
    throws SQLException
  {
    super.initForDataAccess(paramInt1, paramInt2, paramString);

    this.byteLength = this.statement.connection.namedTypeAccessorByteLen;
  }

  Object getObject(int paramInt)
    throws SQLException
  {
    return getObject(paramInt, this.statement.connection.getTypeMap());
  }

  Object getObject(int paramInt, Map paramMap)
    throws SQLException
  {
    Datum datum;
    SQLException sqlexception;
    if (this.rowSpaceIndicator == null)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 21);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1)
    {
      if (this.externalType == 0)
      {
        datum = getOracleObject(paramInt);

        if (datum == null) {
          return null;
        }
        if ((datum instanceof STRUCT)) {
          return ((STRUCT)datum).toJdbc(paramMap);
        }
        if ((datum instanceof OPAQUE)) {
          Object object = ((OPAQUE)datum).toJdbc(paramMap);

          if ((xmlType != null) && (xmlType.isInstance(object)))
          {
            if (this.statement.connection.getObjectReturnsXmlType) return object;

            return new OracleSQLXML(this.statement.connection, (XMLType)object);
          }

          return object;
        }

        return datum.toJdbc();
      }

      switch (this.externalType)
      {
      case 2008:
        paramMap = null;
      case 2000:
      case 2002:
      case 2003:
      case 2007:
        datum = getOracleObject(paramInt);

        if (datum == null) {
          return null;
        }
        if ((datum instanceof STRUCT)) {
          return ((STRUCT)datum).toJdbc(paramMap);
        }
        return datum.toJdbc();
      case 2009:
        datum = getOracleObject(paramInt);
        if (datum == null)
          return null;
        if (datum instanceof OPAQUE) {
          return new OracleSQLXML(this.statement.connection, (OPAQUE)datum);
        }
        sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4);
        sqlexception.fillInStackTrace();
        throw sqlexception;
      case 2001:
      case 2004:
      case 2005:
      case 2006:
      }

      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    return null;
  }

  Datum getOracleObject(int paramInt)
    throws SQLException
  {
    Datum datum = null;
    SQLException sqlexception;

    if (this.rowSpaceIndicator == null)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 21);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    byte[] localObject2 = pickledBytes(paramInt);

    if (localObject2 == null || localObject2.length == 0)
    {
      return null;
    }

    PhysicalConnection localPhysicalConnection = this.statement.connection;
    OracleTypeADT localOracleTypeADT = (OracleTypeADT)this.internalOtype;
    TypeDescriptor localTypeDescriptor = TypeDescriptor.getTypeDescriptor(localOracleTypeADT.getFullName(), localPhysicalConnection, (byte[])localObject2, 0L);

    switch (localTypeDescriptor.getTypeCode())
    {
    case 2003:
      datum = new ARRAY((ArrayDescriptor)localTypeDescriptor, localObject2, localPhysicalConnection);

      break;
    case 2002:
      datum = new STRUCT((StructDescriptor)localTypeDescriptor, (byte[])localObject2, localPhysicalConnection);

      break;
    case 2007:
    case 2009:
      datum = new OPAQUE((OpaqueDescriptor)localTypeDescriptor, (byte[])localObject2, localPhysicalConnection);

      break;
    case 2008:
      datum = new JAVA_STRUCT((StructDescriptor)localTypeDescriptor, (byte[])localObject2, localPhysicalConnection);

      break;
    case 2004:
    case 2005:
    case 2006:
    default:
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 1);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    return datum;
  }

  ARRAY getARRAY(int paramInt)
    throws SQLException
  {
    return (ARRAY)getOracleObject(paramInt);
  }

  STRUCT getSTRUCT(int paramInt)
    throws SQLException
  {
    return (STRUCT)getOracleObject(paramInt);
  }

  OPAQUE getOPAQUE(int paramInt)
    throws SQLException
  {
    return (OPAQUE)getOracleObject(paramInt);
  }

  boolean isNull(int paramInt)
    throws SQLException
  {
    if (this.rowSpaceIndicator == null)
    {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 21);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    byte[] localObject = pickledBytes(paramInt);
    return (localObject == null) || (localObject.length == 0);
  }

  SQLXML getSQLXML(int paramInt)
    throws SQLException
  {
    try
    {
      OPAQUE localOPAQUE = (OPAQUE)getOracleObject(paramInt);
      if (localOPAQUE == null) return null;
      return new OracleSQLXML(this.statement.connection, localOPAQUE);
    }
    catch (ClassCastException localClassCastException)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  static
  {
    
    try {
      localClass = Class.forName("oracle.xdb.XMLType");
    }
    catch (Throwable localThrowable)
    {
    }
  }
}