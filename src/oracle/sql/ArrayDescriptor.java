package oracle.sql;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Map;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;
import oracle.jdbc.driver.DatabaseError;
import oracle.jdbc.oracore.OracleNamedType;
import oracle.jdbc.oracore.OracleType;
import oracle.jdbc.oracore.OracleTypeADT;
import oracle.jdbc.oracore.OracleTypeCOLLECTION;
import oracle.jdbc.oracore.OracleTypeFLOAT;
import oracle.jdbc.oracore.OracleTypeNUMBER;
import oracle.jdbc.oracore.OracleTypeREF;

public class ArrayDescriptor extends TypeDescriptor
  implements Serializable
{
  public static final int TYPE_VARRAY = 3;
  public static final int TYPE_NESTED_TABLE = 2;
  public static final int CACHE_NONE = 0;
  public static final int CACHE_ALL = 1;
  public static final int CACHE_LAST = 2;
  static final long serialVersionUID = 3838105394346513809L;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  public static ArrayDescriptor createDescriptor(String paramString, Connection paramConnection)
    throws SQLException
  {
    return createDescriptor(paramString, paramConnection, false, false);
  }

  public static ArrayDescriptor createDescriptor(String paramString, Connection paramConnection, boolean paramBoolean1, boolean paramBoolean2)
    throws SQLException
  {
    if ((paramString == null) || (paramString.length() == 0) || (paramConnection == null))
    {
      SQLException sqlexception = DatabaseError.createSqlException(null, 60, "ArrayDescriptor.createDescriptor: Invalid argument,'name' should not be an empty string and 'conn' should not be null.");

      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject = new SQLName(paramString, (oracle.jdbc.OracleConnection)paramConnection);
    return createDescriptor((SQLName)localObject, paramConnection);
  }

  public static ArrayDescriptor createDescriptor(SQLName paramSQLName, Connection paramConnection)
    throws SQLException
  {
    return createDescriptor(paramSQLName, paramConnection, false, false);
  }

  public static ArrayDescriptor createDescriptor(SQLName paramSQLName, Connection paramConnection, boolean paramBoolean1, boolean paramBoolean2)
    throws SQLException
  {
    String str = paramSQLName.getName();

    ArrayDescriptor localArrayDescriptor = null;
    if (!paramBoolean2) {
      localArrayDescriptor = (ArrayDescriptor)((oracle.jdbc.OracleConnection)paramConnection).getDescriptor(str);
    }

    if (localArrayDescriptor == null)
    {
      localArrayDescriptor = new ArrayDescriptor(paramSQLName, paramConnection);
      if (paramBoolean1) localArrayDescriptor.initNamesRecursively();
      ((oracle.jdbc.OracleConnection)paramConnection).putDescriptor(str, localArrayDescriptor);
    }
    return localArrayDescriptor;
  }

  public static ArrayDescriptor createDescriptor(OracleTypeCOLLECTION paramOracleTypeCOLLECTION)
    throws SQLException
  {
    String str = paramOracleTypeCOLLECTION.getFullName();
    oracle.jdbc.internal.OracleConnection localOracleConnection = paramOracleTypeCOLLECTION.getConnection();
    ArrayDescriptor localArrayDescriptor = (ArrayDescriptor)localOracleConnection.getDescriptor(str);

    if (localArrayDescriptor == null)
    {
      SQLName localSQLName = new SQLName(paramOracleTypeCOLLECTION.getSchemaName(), paramOracleTypeCOLLECTION.getSimpleName(), paramOracleTypeCOLLECTION.getConnection());

      localArrayDescriptor = new ArrayDescriptor(localSQLName, paramOracleTypeCOLLECTION, localOracleConnection);
      localOracleConnection.putDescriptor(str, localArrayDescriptor);
    }
    return localArrayDescriptor;
  }

  public static ArrayDescriptor createDescriptor(SQLName paramSQLName, byte[] paramArrayOfByte1, int paramInt, byte[] paramArrayOfByte2, oracle.jdbc.internal.OracleConnection paramOracleConnection)
    throws SQLException
  {
    OracleTypeADT localOracleTypeADT = new OracleTypeADT(paramSQLName, paramArrayOfByte1, paramInt, paramArrayOfByte2, paramOracleConnection);

    localOracleTypeADT.init(paramArrayOfByte2, paramOracleConnection);
    OracleTypeCOLLECTION localOracleTypeCOLLECTION = (OracleTypeCOLLECTION)localOracleTypeADT.cleanup();
    return new ArrayDescriptor(paramSQLName, localOracleTypeCOLLECTION, paramOracleConnection);
  }

  public ArrayDescriptor(String paramString, Connection paramConnection)
    throws SQLException
  {
    super((short)122, paramString, paramConnection);

    initPickler();
  }

  public ArrayDescriptor(SQLName paramSQLName, Connection paramConnection)
    throws SQLException
  {
    super((short)122, paramSQLName, paramConnection);

    initPickler();
  }

  public ArrayDescriptor(SQLName paramSQLName, OracleTypeCOLLECTION paramOracleTypeCOLLECTION, Connection paramConnection)
    throws SQLException
  {
    super((short)122, paramSQLName, paramOracleTypeCOLLECTION, paramConnection);
  }

  public ArrayDescriptor(OracleTypeCOLLECTION paramOracleTypeCOLLECTION, Connection paramConnection)
    throws SQLException
  {
    super((short)122, paramOracleTypeCOLLECTION, paramConnection);
  }

  ArrayDescriptor(byte[] paramArrayOfByte, int paramInt, Connection paramConnection)
    throws SQLException
  {
    super((short)122);

    this.toid = paramArrayOfByte;
    this.toidVersion = paramInt;
    setPhysicalConnectionOf(paramConnection);
    initPickler();
  }

  public int getBaseType()
    throws SQLException
  {
    return ((OracleTypeCOLLECTION)this.pickler).getElementType().getTypeCode();
  }

  public String getBaseName()
    throws SQLException
  {
    String str = null;
    OracleNamedType localOracleNamedType;
    switch (getBaseType())
    {
    case 12:
      str = "VARCHAR";

      break;
    case 1:
      str = "CHAR";

      break;
    case -2:
      str = "RAW";

      break;
    case 6:
      str = "FLOAT";

      break;
    case 2:
      str = "NUMBER";

      break;
    case 8:
      str = "DOUBLE";

      break;
    case 3:
      str = "DECIMAL";

      break;
    case 91:
      str = "DATE";

      break;
    case 93:
      str = "TIMESTAMP";

      break;
    case -101:
      str = "TIMESTAMP WITH TIME ZONE";

      break;
    case -102:
      str = "TIMESTAMP WITH LOCAL TIME ZONE";

      break;
    case 2004:
      str = "BLOB";

      break;
    case 2005:
      str = "CLOB";

      break;
    case -13:
      str = "BFILE";

      break;
    case 2002:
    case 2003:
    case 2007:
    case 2008:
      localOracleNamedType = (OracleNamedType)((OracleTypeCOLLECTION)this.pickler).getElementType();

      str = localOracleNamedType.getFullName();

      break;
    case 2006:
      localOracleNamedType = (OracleNamedType)((OracleTypeCOLLECTION)this.pickler).getElementType();

      str = "REF " + ((OracleTypeREF)localOracleNamedType).getFullName();

      break;
    case 1111:
    default:
      str = null;
    }

    return str;
  }

  public OracleTypeCOLLECTION getOracleTypeCOLLECTION()
  {
    return (OracleTypeCOLLECTION)this.pickler;
  }

  public int getArrayType()
    throws SQLException
  {
    return ((OracleTypeCOLLECTION)this.pickler).getUserCode();
  }

  public long getMaxLength()
    throws SQLException
  {
    return getArrayType() == 3 ? ((OracleTypeCOLLECTION)this.pickler).getMaxLength() : 0L;
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
    paramStringBuffer.append("\n");

    int j = getBaseType();
    Object localObject;
    if ((j == 2002) || (j == 2008))
    {
      localObject = StructDescriptor.createDescriptor(getBaseName(), this.connection);

      ((StructDescriptor)localObject).descType(paramStringBuffer, paramInt + 1);
    }
    else if (j == 2003)
    {
      localObject = createDescriptor(getBaseName(), this.connection);

      ((ArrayDescriptor)localObject).descType(paramStringBuffer, paramInt + 1);
    }
    else if (j == 2007)
    {
      localObject = OpaqueDescriptor.createDescriptor(getBaseName(), this.connection);

      ((OpaqueDescriptor)localObject).descType(paramStringBuffer, paramInt + 1);
    }
    else
    {
      paramStringBuffer.append(str2);
      paramStringBuffer.append(getBaseName());
      paramStringBuffer.append("\n");
    }

    return paramStringBuffer.substring(0, paramStringBuffer.length());
  }

  int toLength(ARRAY paramARRAY)
    throws SQLException
  {
    if (paramARRAY.numElems == -1)
    {
      if (paramARRAY.datumArray != null)
      {
        paramARRAY.numElems = paramARRAY.datumArray.length;
      }
      else if (paramARRAY.objArray != null)
      {
        if ((paramARRAY.objArray instanceof Object[]))
          paramARRAY.numElems = ((Object[])paramARRAY.objArray).length;
        else if ((paramARRAY.objArray instanceof int[]))
          paramARRAY.numElems = ((long[])paramARRAY.objArray).length;
        else if ((paramARRAY.objArray instanceof long[]))
          paramARRAY.numElems = ((float[])paramARRAY.objArray).length;
        else if ((paramARRAY.objArray instanceof float[]))
          paramARRAY.numElems = ((double[])paramARRAY.objArray).length;
        else if ((paramARRAY.objArray instanceof double[]))
          paramARRAY.numElems = ((boolean[])paramARRAY.objArray).length;
        else if ((paramARRAY.objArray instanceof boolean[]))
          paramARRAY.numElems = ((int[])paramARRAY.objArray).length;
        else if ((paramARRAY.objArray instanceof byte[]))
          paramARRAY.numElems = ((byte[])paramARRAY.objArray).length;
        else if ((paramARRAY.objArray instanceof short[]))
          paramARRAY.numElems = ((short[])paramARRAY.objArray).length;
        else if ((paramARRAY.objArray instanceof char[]))
          paramARRAY.numElems = ((char[])paramARRAY.objArray).length;
      }
      else if (paramARRAY.locator != null)
      {
        paramARRAY.numElems = toLengthFromLocator(paramARRAY.locator);
      }
      else
      {
        SQLException localSQLException;
        if (paramARRAY.shareBytes() != null)
        {
          this.pickler.unlinearize(paramARRAY.shareBytes(), paramARRAY.imageOffset, paramARRAY, 0, null);

          if (paramARRAY.numElems == -1)
          {
            if (paramARRAY.locator != null) {
              paramARRAY.numElems = toLengthFromLocator(paramARRAY.locator);
            }
            else
            {
              localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 1, "Unable to get array length");
              localSQLException.fillInStackTrace();
              throw localSQLException;
            }

          }

        }
        else
        {
          localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 1, "Array is in inconsistent status");
          localSQLException.fillInStackTrace();
          throw localSQLException;
        }
      }
    }

    return paramARRAY.numElems;
  }

  byte[] toBytes(ARRAY paramARRAY, boolean paramBoolean)
    throws SQLException
  {
    byte[] arrayOfByte = paramARRAY.shareBytes();
    if (arrayOfByte == null)
    {
      if ((paramARRAY.datumArray != null) || (paramARRAY.locator != null))
      {
        arrayOfByte = this.pickler.linearize(paramARRAY);

        if (!paramBoolean) {
          paramARRAY.setShareBytes(null);
        }
      }
      else if (paramARRAY.objArray != null)
      {
        paramARRAY.datumArray = toOracleArray(paramARRAY.objArray, 1L, -1);
        arrayOfByte = this.pickler.linearize(paramARRAY);

        if (!paramBoolean)
        {
          paramARRAY.datumArray = null;

          paramARRAY.setShareBytes(null);
        }

      }
      else
      {
        SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 1, "Array is in inconsistent status");
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }

    }
    else if (paramARRAY.imageLength != 0L)
    {
      if ((paramARRAY.imageOffset != 0L) || (paramARRAY.imageLength != arrayOfByte.length))
      {
        byte[] localObject = new byte[(int)paramARRAY.imageLength];

        System.arraycopy(arrayOfByte, (int)paramARRAY.imageOffset, localObject, 0, (int)paramARRAY.imageLength);

        paramARRAY.setImage((byte[])localObject, 0L, 0L);

        return localObject;
      }
    }

    return arrayOfByte;
  }

  Datum[] toOracleArray(ARRAY paramARRAY, long paramLong, int paramInt, boolean paramBoolean)
    throws SQLException
  {
    Datum[] arrayOfDatum1 = paramARRAY.datumArray;

    if (arrayOfDatum1 == null)
    {
      if (paramARRAY.objArray != null)
      {
        arrayOfDatum1 = toOracleArray(paramARRAY.objArray, paramLong, paramInt);
      }
      else if (paramARRAY.locator != null)
      {
        arrayOfDatum1 = toOracleArrayFromLocator(paramARRAY.locator, paramLong, paramInt, null);
      }
      else if (paramARRAY.shareBytes() != null)
      {
        this.pickler.unlinearize(paramARRAY.shareBytes(), paramARRAY.imageOffset, paramARRAY, paramLong, paramInt, 1, null);

        if (paramARRAY.locator != null)
        {
          arrayOfDatum1 = toOracleArrayFromLocator(paramARRAY.locator, paramLong, paramInt, null);
        }
        else
        {
          arrayOfDatum1 = paramARRAY.datumArray;
        }

        if (!paramBoolean) {
          paramARRAY.datumArray = null;
        }

      }
      else
      {
        SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 1, "Array is in inconsistent status.");
        localSQLException.fillInStackTrace();
        throw localSQLException;
      }

    }
    else
    {
      if (paramLong > arrayOfDatum1.length)
      {
        return new Datum[0];
      }

      int i = (int)(paramInt == -1 ? arrayOfDatum1.length - paramLong + 1L : Math.min(arrayOfDatum1.length - paramLong + 1L, paramInt));

      arrayOfDatum1 = new Datum[i];

      System.arraycopy(paramARRAY.datumArray, (int)paramLong - 1, arrayOfDatum1, 0, i);
    }

    Datum[] arrayOfDatum2 = null;

    if (paramBoolean)
    {
      paramARRAY.datumArray = arrayOfDatum1;
      arrayOfDatum2 = (Datum[])arrayOfDatum1.clone();
    }
    else
    {
      arrayOfDatum2 = arrayOfDatum1;
    }

    return arrayOfDatum2;
  }

  Object[] toJavaArray(ARRAY paramARRAY, long paramLong, int paramInt, Map paramMap, boolean paramBoolean)
    throws SQLException
  {
    Object[] arrayOfObject1 = null;

    if (paramARRAY.objArray != null)
    {
      arrayOfObject1 = (Object[])((Object[])paramARRAY.objArray).clone();

      int i = arrayOfObject1.length;
      int j = (int)(paramInt == -1 ? i - paramLong + 1L : Math.min(i - paramLong + 1L, paramInt));

      if (j <= 0)
      {
        Object[] arrayOfObject2 = (Object[])makeJavaArray(j, getBaseType());

        return arrayOfObject2;
      }

      arrayOfObject1 = (Object[])makeJavaArray(j, getBaseType());

      System.arraycopy(paramARRAY.objArray, (int)paramLong - 1, arrayOfObject1, 0, j);
    }
    else
    {
      if (paramARRAY.datumArray != null)
      {
        arrayOfObject1 = (Object[])toJavaArray(paramARRAY.datumArray, paramLong, paramInt, paramMap);
      }
      else if (paramARRAY.locator != null)
      {
        arrayOfObject1 = toArrayFromLocator(paramARRAY.locator, paramLong, paramInt, paramMap);
      }
      else if (paramARRAY.shareBytes() != null)
      {
        this.pickler.unlinearize(paramARRAY.shareBytes(), paramARRAY.imageOffset, paramARRAY, paramLong, paramInt, 2, paramMap);

        if (paramARRAY.locator != null)
          arrayOfObject1 = toArrayFromLocator(paramARRAY.locator, paramLong, paramInt, paramMap);
        else {
          arrayOfObject1 = (Object[])paramARRAY.objArray;
        }

      }
      else
      {
        SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 1, "Array is in inconsistent status");
        localSQLException.fillInStackTrace();
        throw localSQLException;
      }

      if ((paramBoolean) && (getBaseType() != 2002) && (getBaseType() != 2008) && (arrayOfObject1 != null))
      {
        paramARRAY.objArray = arrayOfObject1.clone();
      }
      else paramARRAY.objArray = null;
    }

    return arrayOfObject1;
  }

  private Datum[] toOracleArrayFromLocator(byte[] paramArrayOfByte, long paramLong, int paramInt, Map paramMap)
    throws SQLException
  {
    int i = toLengthFromLocator(paramArrayOfByte);
    int j = (int)(paramInt == -1 ? i - paramLong + 1L : Math.min(i - paramLong + 1L, paramInt));

    Datum[] arrayOfDatum = null;

    if (j <= 0)
    {
      arrayOfDatum = new Datum[0];
    }
    else
    {
      arrayOfDatum = new Datum[j];

      ResultSet localResultSet = toResultSetFromLocator(paramArrayOfByte, paramLong, paramInt, paramMap);

      for (int k = 0; localResultSet.next(); k++) {
        arrayOfDatum[k] = ((OracleResultSet)localResultSet).getOracleObject(2);
      }
      localResultSet.close();
    }

    return arrayOfDatum;
  }

  private Object[] toArrayFromLocator(byte[] paramArrayOfByte, long paramLong, int paramInt, Map paramMap)
    throws SQLException
  {
    int i = toLengthFromLocator(paramArrayOfByte);
    int j = (int)(paramInt == -1 ? i - paramLong + 1L : Math.min(i - paramLong + 1L, paramInt));

    Object[] arrayOfObject = null;

    if (j <= 0)
    {
      arrayOfObject = (Object[])makeJavaArray(0, getBaseType());
    }
    else
    {
      arrayOfObject = (Object[])makeJavaArray(j, getBaseType());

      ResultSet localResultSet = toResultSetFromLocator(paramArrayOfByte, paramLong, paramInt, paramMap);

      for (int k = 0; localResultSet.next(); k++) {
        arrayOfObject[k] = ((OracleResultSet)localResultSet).getObject(2, paramMap);
      }
      localResultSet.close();
    }

    return arrayOfObject;
  }

  public ResultSet toResultSet(ARRAY paramARRAY, long paramLong, int paramInt, Map paramMap, boolean paramBoolean)
    throws SQLException
  {
    ResultSet localResultSet = null;
    SQLException localSQLException;
    if (paramARRAY.datumArray != null)
    {
      localResultSet = toResultSet(paramARRAY.datumArray, paramLong, paramInt, paramMap);
    }
    else if (paramARRAY.locator != null)
    {
      localResultSet = toResultSetFromLocator(paramARRAY.locator, paramLong, paramInt, paramMap);
    }
    else if (paramARRAY.objArray != null)
    {
      localResultSet = toResultSet(toOracleArray(paramARRAY.objArray, paramLong, paramInt), 1L, -1, paramMap);
    }
    else if (paramARRAY.shareBytes() != null)
    {
      if (((OracleTypeCOLLECTION)this.pickler).isInlineImage(paramARRAY.shareBytes(), (int)paramARRAY.imageOffset))
      {
        localResultSet = toResultSetFromImage(paramARRAY, paramLong, paramInt, paramMap);
      }
      else
      {
        this.pickler.unlinearize(paramARRAY.shareBytes(), paramARRAY.imageOffset, paramARRAY, 1, null);

        if (paramARRAY.locator != null) {
          localResultSet = toResultSetFromLocator(paramARRAY.locator, paramLong, paramInt, paramMap);
        }
        else
        {
          localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 1, "Array is in inconsistent status");
          localSQLException.fillInStackTrace();
          throw localSQLException;
        }
      }

    }

    if (localResultSet == null)
    {
      localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 1, "Unable to create array ResultSet");
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    return localResultSet;
  }

  public ResultSet toResultSet(Datum[] paramArrayOfDatum, long paramLong, int paramInt, Map paramMap)
    throws SQLException
  {
    ResultSet localResultSet = null;

    if (paramInt == -1)
      localResultSet = this.connection.newArrayDataResultSet(paramArrayOfDatum, paramLong, paramArrayOfDatum.length, paramMap);
    else
      localResultSet = this.connection.newArrayDataResultSet(paramArrayOfDatum, paramLong, paramInt, paramMap);
    return localResultSet;
  }

  public ResultSet toResultSetFromLocator(byte[] paramArrayOfByte, long paramLong, int paramInt, Map paramMap)
    throws SQLException
  {
    ResultSet localResultSet = this.connection.newArrayLocatorResultSet(this, paramArrayOfByte, paramLong, paramInt, paramMap);

    return localResultSet;
  }

  public ResultSet toResultSetFromImage(ARRAY paramARRAY, long paramLong, int paramInt, Map paramMap)
    throws SQLException
  {
    ResultSet localResultSet = this.connection.newArrayDataResultSet(paramARRAY, paramLong, paramInt, paramMap);

    return localResultSet;
  }

  public static Object[] makeJavaArray(int paramInt1, int paramInt2)
    throws SQLException
  {
    Object[] localObject = null;

    switch (paramInt2)
    {
    case -7:
    case -6:
    case -5:
    case 2:
    case 3:
    case 4:
    case 5:
    case 6:
    case 7:
      localObject = new BigDecimal[paramInt1];

      break;
    case 1:
    case 12:
      localObject = new String[paramInt1];

      break;
    case -102:
    case -101:
    case 91:
    case 92:
    case 93:
      localObject = new Timestamp[paramInt1];

      break;
    case 2002:
    case 2008:
      localObject = new Object[paramInt1];

      break;
    case -13:
      localObject = new BFILE[paramInt1];

      break;
    case 2004:
      localObject = new BLOB[paramInt1];

      break;
    case 2005:
      localObject = new CLOB[paramInt1];

      break;
    case -3:
    case -2:
      localObject = new byte[paramInt1][];

      break;
    case 2006:
      localObject = new REF[paramInt1];

      break;
    case 2003:
      localObject = new Object[paramInt1];

      break;
    default:
      SQLException localSQLException = DatabaseError.createSqlException(null, 1, "makeJavaArray doesn't support type " + paramInt2);

      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    return localObject;
  }

  private int toLengthFromLocator(byte[] paramArrayOfByte)
    throws SQLException
  {
    ARRAY localARRAY = new ARRAY(this, this.connection, (byte[])null);

    localARRAY.setLocator(paramArrayOfByte);

    int i = 0;

    OraclePreparedStatement localOraclePreparedStatement = null;
    OracleResultSet localOracleResultSet = null;

    localOraclePreparedStatement = (OraclePreparedStatement)this.connection.prepareStatement("SELECT count(*) FROM TABLE( CAST(:1 AS " + getName() + ") )");

    localOraclePreparedStatement.setArray(1, localARRAY);

    localOracleResultSet = (OracleResultSet)localOraclePreparedStatement.executeQuery();

    if (localOracleResultSet.next()) {
      i = localOracleResultSet.getInt(1);
    }
    else
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 1, "Fail to access array storage table");
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    localOracleResultSet.close();
    localOraclePreparedStatement.close();

    return i;
  }

  Datum[] toOracleArray(Object paramObject, long paramLong, int paramInt)
    throws SQLException
  {
    Datum[] arrayOfDatum = null;

    if (paramObject != null)
    {
      OracleType localOracleType = getElementType();

      arrayOfDatum = localOracleType.toDatumArray(paramObject, this.connection, paramLong, paramInt);
    }

    return arrayOfDatum;
  }

  private Object toJavaArray(Datum[] paramArrayOfDatum, long paramLong, int paramInt, Map paramMap)
    throws SQLException
  {
    int i = (int)(paramInt == -1 ? paramArrayOfDatum.length - paramLong + 1L : Math.min(paramArrayOfDatum.length - paramLong + 1L, paramInt));

    if (i < 0) {
      i = 0;
    }
    Object[] arrayOfObject = (Object[])makeJavaArray(i, getBaseType());
    Object localObject;
    int j;
    if (getBaseType() == 2002)
    {
      localObject = null;

      for (j = 0; j < i; j++)
      {
        localObject = (STRUCT)paramArrayOfDatum[((int)paramLong + j - 1)];
        arrayOfObject[j] = (localObject != null ? ((STRUCT)localObject).toJdbc(paramMap) : null);
      }
    }
    else
    {
      localObject = null;

      for (j = 0; j < i; j++)
      {
        localObject = paramArrayOfDatum[((int)paramLong + j - 1)];
        arrayOfObject[j] = (localObject != null ? ((Datum)localObject).toJdbc() : null);
      }
    }

    return arrayOfObject;
  }

  private Object toNumericArray(Datum[] paramArrayOfDatum, long paramLong, int paramInt1, int paramInt2)
    throws SQLException
  {
    Object localObject1 = null;

    int i = (int)(paramInt1 == -1 ? paramArrayOfDatum.length - paramLong + 1L : Math.min(paramArrayOfDatum.length - paramLong + 1L, paramInt1));

    if (i < 0)
      i = 0;
    int j;
    Datum localDatum;
    switch (paramInt2)
    {
    case 4:
      int[] ia = new int[i];

      for (j = 0; j < i; j++)
      {
        localDatum = paramArrayOfDatum[((int)paramLong + j - 1)];

        if (localDatum != null) {
          ia[j] = localDatum.intValue();
        }
      }
      localObject1 = ia;
      break;
    case 5:
      double[] d = new double[i];

      for (j = 0; j < i; j++)
      {
        localDatum = paramArrayOfDatum[((int)paramLong + j - 1)];

        if (localDatum != null) {
          d[j] = localDatum.doubleValue();
        }
      }
      localObject1 = d;
      break;
    case 6:
      float[] f = new float[i];

      for (j = 0; j < i; j++)
      {
        localDatum = paramArrayOfDatum[((int)paramLong + j - 1)];

        if (localDatum != null) {
          f[j] = localDatum.floatValue();
        }
      }
      localObject1 = f;
      break;
    case 7:
      long[] l = new long[i];

      for (j = 0; j < i; j++)
      {
        localDatum = paramArrayOfDatum[((int)paramLong + j - 1)];

        if (localDatum != null) {
          l[j] = localDatum.longValue();
        }
      }
      localObject1 = l;
      break;
    case 8:
      short[] s = new short[i];

      for (j = 0; j < i; j++)
      {
        localDatum = paramArrayOfDatum[((int)paramLong + j - 1)];

        if (localDatum != null) {
          s[j] = ((NUMBER)localDatum).shortValue();
        }
      }
      localObject1 = s;
      break;
    default:
      SQLException sqlexception = DatabaseError.createUnsupportedFeatureSqlException();
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    return localObject1;
  }

  private Object toNumericArrayFromLocator(byte[] paramArrayOfByte, long paramLong, int paramInt1, int paramInt2)
    throws SQLException
  {
    Object localObject1 = null;

    int i = toLengthFromLocator(paramArrayOfByte);

    ResultSet localResultSet = toResultSetFromLocator(paramArrayOfByte, paramLong, paramInt1, null);

    int j = 0;
    switch (paramInt2)
    {
    case 4:
      int[] ia = new int[i];

      while ((localResultSet.next()) && (j < i)) {
        ia[(j++)] = ((OracleResultSet)localResultSet).getInt(2);
      }
      localResultSet.close();

      localObject1 = ia;
      break;
    case 5:
      double[] d = new double[i];

      while ((localResultSet.next()) && (j < i)) {
        d[(j++)] = ((OracleResultSet)localResultSet).getDouble(2);
      }
      localResultSet.close();

      localObject1 = d;
      break;
    case 6:
      float[] f = new float[i];

      while ((localResultSet.next()) && (j < i)) {
        f[(j++)] = ((OracleResultSet)localResultSet).getFloat(2);
      }
      localResultSet.close();

      localObject1 = f;
      break;
    case 7:
      long[] l = new long[i];

      while ((localResultSet.next()) && (j < i)) {
        l[(j++)] = ((OracleResultSet)localResultSet).getLong(2);
      }
      localResultSet.close();

      localObject1 = l;
      break;
    case 8:
      short[] s = new short[i];

      while ((localResultSet.next()) && (j < i)) {
        s[(j++)] = ((OracleResultSet)localResultSet).getShort(2);
      }
      localResultSet.close();

      localObject1 = s;
      break;
    default:
      SQLException sqlexception = DatabaseError.createUnsupportedFeatureSqlException();
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    return localObject1;
  }

  Object toNumericArray(ARRAY paramARRAY, long paramLong, int paramInt1, int paramInt2, boolean paramBoolean)
    throws SQLException
  {
    OracleType localOracleType = getElementType();
    if ((!(localOracleType instanceof OracleTypeNUMBER)) && (!(localOracleType instanceof OracleTypeFLOAT)))
    {
      SQLException sqlexception = DatabaseError.createUnsupportedFeatureSqlException();
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject1 = null;

    if (paramARRAY.objArray != null)
    {
      int i;
      Object localObject2;
      if ((paramInt2 == 4) && ((paramARRAY.objArray instanceof int[])))
      {
        i = ((int[])paramARRAY.objArray).length;

        if (paramLong > i) {
          return new int[0];
        }
        i = (int)(paramInt1 == -1 ? i - paramLong + 1L : Math.min(i - paramLong + 1L, paramInt1));

        localObject2 = new int[i];

        System.arraycopy(paramARRAY.objArray, (int)paramLong - 1, localObject2, 0, i);

        localObject1 = localObject2;
      }
      else if ((paramInt2 == 5) && ((paramARRAY.objArray instanceof double[])))
      {
        i = ((double[])paramARRAY.objArray).length;

        if (paramLong > i) {
          return new double[0];
        }
        i = (int)(paramInt1 == -1 ? i - paramLong + 1L : Math.min(i - paramLong + 1L, paramInt1));

        localObject2 = new double[i];

        System.arraycopy(paramARRAY.objArray, (int)paramLong - 1, localObject2, 0, i);

        localObject1 = localObject2;
      }
      else if ((paramInt2 == 6) && ((paramARRAY.objArray instanceof float[])))
      {
        i = ((float[])paramARRAY.objArray).length;

        if (paramLong > i) {
          return new float[0];
        }
        i = (int)(paramInt1 == -1 ? i - paramLong + 1L : Math.min(i - paramLong + 1L, paramInt1));

        localObject2 = new float[i];

        System.arraycopy(paramARRAY.objArray, (int)paramLong - 1, localObject2, 0, i);

        localObject1 = localObject2;
      }
      else if ((paramInt2 == 7) && ((paramARRAY.objArray instanceof long[])))
      {
        i = ((long[])paramARRAY.objArray).length;

        if (paramLong > i) {
          return new long[0];
        }
        i = (int)(paramInt1 == -1 ? i - paramLong + 1L : Math.min(i - paramLong + 1L, paramInt1));

        localObject2 = new long[i];

        System.arraycopy(paramARRAY.objArray, (int)paramLong - 1, localObject2, 0, i);

        localObject1 = localObject2;
      }
      else if ((paramInt2 == 8) && ((paramARRAY.objArray instanceof short[])))
      {
        i = ((short[])paramARRAY.objArray).length;

        if (paramLong > i) {
          return new short[0];
        }
        i = (int)(paramInt1 == -1 ? i - paramLong + 1L : Math.min(i - paramLong + 1L, paramInt1));

        localObject2 = new short[i];

        System.arraycopy(paramARRAY.objArray, (int)paramLong - 1, localObject2, 0, i);

        localObject1 = localObject2;
      }

    }
    else
    {
      if (paramARRAY.datumArray != null)
      {
        localObject1 = toNumericArray(paramARRAY.datumArray, paramLong, paramInt1, paramInt2);
      }
      else if (paramARRAY.locator != null)
      {
        localObject1 = toNumericArrayFromLocator(paramARRAY.locator, paramLong, paramInt1, paramInt2);
      }
      else if (paramARRAY.shareBytes() != null)
      {
        this.pickler.unlinearize(paramARRAY.shareBytes(), paramARRAY.imageOffset, paramARRAY, paramLong, paramInt1, paramInt2, null);

        if (paramARRAY.locator != null)
        {
          localObject1 = toNumericArrayFromLocator(paramARRAY.locator, paramLong, paramInt1, paramInt2);
        }
        else
        {
          localObject1 = paramARRAY.objArray;
        }

      }
      else
      {
        SQLException localSQLException = DatabaseError.createUnsupportedFeatureSqlException();
        localSQLException.fillInStackTrace();
        throw localSQLException;
      }

      if (!paramBoolean) {
        paramARRAY.objArray = null;
      }
    }
    return localObject1;
  }

  private void initPickler()
    throws SQLException
  {
    try
    {
      OracleTypeADT localOracleTypeADT = new OracleTypeADT(getName(), this.connection);
      localOracleTypeADT.init(this.connection);
      this.pickler = ((OracleTypeCOLLECTION)localOracleTypeADT.cleanup());
      this.toid = ((OracleTypeADT)this.pickler).getTOID();
      this.pickler.setDescriptor(this);
    }
    catch (Exception localException)
    {
      if ((localException instanceof SQLException))
      {
        throw ((SQLException)localException);
      }

      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 60, "Unable to resolve type: \"" + getName() + "\"");

      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  private OracleType getElementType()
    throws SQLException
  {
    OracleType localOracleType = ((OracleTypeCOLLECTION)this.pickler).getElementType();

    return localOracleType;
  }

  public int getTypeCode()
    throws SQLException
  {
    int i = 2003;
    return i;
  }

  public byte[] toBytes(Datum[] paramArrayOfDatum)
    throws SQLException
  {
    ARRAY localARRAY = new ARRAY(this, this.connection, paramArrayOfDatum);

    return this.pickler.linearize(localARRAY);
  }

  public byte[] toBytes(Object[] paramArrayOfObject)
    throws SQLException
  {
    Datum[] arrayOfDatum = toArray(paramArrayOfObject);
    byte[] arrayOfByte = toBytes(arrayOfDatum);
    return arrayOfByte;
  }

  public int length(byte[] paramArrayOfByte)
    throws SQLException
  {
    ARRAY localARRAY = new ARRAY(this, this.connection, paramArrayOfByte);
    int i = toLength(localARRAY);

    return i;
  }

  public Datum[] toArray(byte[] paramArrayOfByte)
    throws SQLException
  {
    Datum[] arrayOfDatum = null;
    if (paramArrayOfByte != null)
    {
      ARRAY localARRAY = new ARRAY(this, this.connection, paramArrayOfByte);

      arrayOfDatum = toOracleArray(localARRAY, 1L, -1, false);
    }
    return arrayOfDatum;
  }

  public Datum[] toArray(Object paramObject)
    throws SQLException
  {
    Datum[] arrayOfDatum = toOracleArray(paramObject, 1L, -1);
    return arrayOfDatum;
  }

  public ResultSet toResultSet(byte[] paramArrayOfByte, Map paramMap)
    throws SQLException
  {
    ResultSet localResultSet = null;
    if (paramArrayOfByte != null)
    {
      ARRAY localARRAY = (ARRAY)this.pickler.unlinearize(paramArrayOfByte, 0L, null, 1, null);

      localResultSet = toResultSet(localARRAY, 1L, -1, paramMap, false);
    }
    return localResultSet;
  }

  public ResultSet toResultSet(byte[] paramArrayOfByte, long paramLong, int paramInt, Map paramMap)
    throws SQLException
  {
    ResultSet localResultSet = null;

    if (paramArrayOfByte != null)
    {
      ARRAY localARRAY = (ARRAY)this.pickler.unlinearize(paramArrayOfByte, 0L, (ARRAY)null, 1, null);

      localResultSet = toResultSet(localARRAY, paramLong, paramInt, paramMap, false);
    }

    return localResultSet;
  }

  String tagName()
  {
    return "ArrayDescriptor";
  }

  public static int getCacheStyle(ARRAY paramARRAY)
    throws SQLException
  {
    int i = 2;

    if ((paramARRAY.getAutoIndexing()) && ((paramARRAY.getAccessDirection() == 2) || (paramARRAY.getAccessDirection() == 3)))
    {
      i = 1;
    }

    return i;
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