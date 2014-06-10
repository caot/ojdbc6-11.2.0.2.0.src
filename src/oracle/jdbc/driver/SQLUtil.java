package oracle.jdbc.driver;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Hashtable;
import java.util.Map;
import oracle.jdbc.internal.OracleConnection;
import oracle.jdbc.oracore.OracleNamedType;
import oracle.jdbc.oracore.OracleTypeADT;
import oracle.jdbc.oracore.OracleTypeCOLLECTION;
import oracle.jdbc.oracore.OracleTypeOPAQUE;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import oracle.sql.BFILE;
import oracle.sql.BINARY_DOUBLE;
import oracle.sql.BINARY_FLOAT;
import oracle.sql.BLOB;
import oracle.sql.CHAR;
import oracle.sql.CLOB;
import oracle.sql.CharacterSet;
import oracle.sql.CustomDatum;
import oracle.sql.CustomDatumFactory;
import oracle.sql.DATE;
import oracle.sql.Datum;
import oracle.sql.INTERVALDS;
import oracle.sql.INTERVALYM;
import oracle.sql.NUMBER;
import oracle.sql.OPAQUE;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.OpaqueDescriptor;
import oracle.sql.RAW;
import oracle.sql.REF;
import oracle.sql.ROWID;
import oracle.sql.SQLName;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;
import oracle.sql.TIMESTAMP;
import oracle.sql.TIMESTAMPLTZ;
import oracle.sql.TIMESTAMPTZ;
import oracle.sql.TypeDescriptor;
import oracle.sql.converter.CharacterSetMetaData;

public class SQLUtil
{
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;
  private static final int CLASS_NOT_FOUND = -1;
  private static final int CLASS_STRING = 0;
  private static final int CLASS_BOOLEAN = 1;
  private static final int CLASS_INTEGER = 2;
  private static final int CLASS_LONG = 3;
  private static final int CLASS_FLOAT = 4;
  private static final int CLASS_DOUBLE = 5;
  private static final int CLASS_BIGDECIMAL = 6;
  private static final int CLASS_DATE = 7;
  private static final int CLASS_TIME = 8;
  private static final int CLASS_TIMESTAMP = 9;
  private static final int TOTAL_CLASSES = 10;
  private static Hashtable classTable = new Hashtable(10);

  public static Object SQLToJava(OracleConnection paramOracleConnection, byte[] paramArrayOfByte, int paramInt, String paramString, Class paramClass, Map paramMap)
    throws SQLException
  {
    Datum localDatum = makeDatum(paramOracleConnection, paramArrayOfByte, paramInt, paramString, 0);
    Object localObject = SQLToJava(paramOracleConnection, localDatum, paramClass, paramMap);

    return localObject;
  }

  public static CustomDatum SQLToJava(OracleConnection paramOracleConnection, byte[] paramArrayOfByte, int paramInt, String paramString, CustomDatumFactory paramCustomDatumFactory)
    throws SQLException
  {
    Datum localDatum = makeDatum(paramOracleConnection, paramArrayOfByte, paramInt, paramString, 0);
    CustomDatum localCustomDatum = paramCustomDatumFactory.create(localDatum, paramInt);

    return localCustomDatum;
  }

  public static ORAData SQLToJava(OracleConnection paramOracleConnection, byte[] paramArrayOfByte, int paramInt, String paramString, ORADataFactory paramORADataFactory)
    throws SQLException
  {
    Datum localDatum = makeDatum(paramOracleConnection, paramArrayOfByte, paramInt, paramString, 0);
    ORAData localORAData = paramORADataFactory.create(localDatum, paramInt);

    return localORAData;
  }

  public static Object SQLToJava(OracleConnection paramOracleConnection, Datum paramDatum, Class paramClass, Map paramMap)
    throws SQLException
  {
    Object localObject = null;

    if ((paramDatum instanceof STRUCT))
    {
      if (paramClass == null)
      {
        localObject = paramMap != null ? ((STRUCT)paramDatum).toJdbc(paramMap) : paramDatum.toJdbc();
      }
      else
      {
        localObject = paramMap != null ? ((STRUCT)paramDatum).toClass(paramClass, paramMap) : ((STRUCT)paramDatum).toClass(paramClass);
      }

    }
    else if (paramClass == null)
    {
      localObject = paramDatum.toJdbc();
    }
    else
    {
      int i = classNumber(paramClass);

      switch (i)
      {
      case 0:
        localObject = paramDatum.stringValue();

        break;
      case 1:
        localObject = Boolean.valueOf(paramDatum.longValue() != 0L);

        break;
      case 2:
        localObject = Integer.valueOf((int)paramDatum.longValue());

        break;
      case 3:
        localObject = Long.valueOf(paramDatum.longValue());

        break;
      case 4:
        localObject = Float.valueOf(paramDatum.bigDecimalValue().floatValue());

        break;
      case 5:
        localObject = Double.valueOf(paramDatum.bigDecimalValue().doubleValue());

        break;
      case 6:
        localObject = paramDatum.bigDecimalValue();

        break;
      case 7:
        localObject = paramDatum.dateValue();

        break;
      case 8:
        localObject = paramDatum.timeValue();

        break;
      case 9:
        localObject = paramDatum.timestampValue();

        break;
      case -1:
      default:
        localObject = paramDatum.toJdbc();

        if (!paramClass.isInstance(localObject))
        {
          SQLException localSQLException = DatabaseError.createSqlException(null, 59, "invalid data conversion");
          localSQLException.fillInStackTrace();
          throw localSQLException;
        }

        break;
      }

    }

    return localObject;
  }

  public static byte[] JavaToSQL(OracleConnection paramOracleConnection, Object paramObject, int paramInt, String paramString)
    throws SQLException
  {
    if (paramObject == null)
    {
      return null;
    }

    Object localObject = null;

    if ((paramObject instanceof Datum))
    {
      localObject = (Datum)paramObject;
    }
    else if ((paramObject instanceof ORAData))
    {
      localObject = ((ORAData)paramObject).toDatum(paramOracleConnection);
    }
    else if ((paramObject instanceof CustomDatum))
    {
      localObject = paramOracleConnection.toDatum((CustomDatum)paramObject);
    }
    else if ((paramObject instanceof SQLData))
    {
      localObject = STRUCT.toSTRUCT(paramObject, paramOracleConnection);
    }

    if (localObject != null)
    {
      if (!checkDatumType((Datum)localObject, paramInt, paramString))
      {
        localObject = null;
      }

    }
    else
    {
      localObject = makeDatum(paramOracleConnection, paramObject, paramInt, paramString);
    }

    byte[] arrayOfByte = null;

    if (localObject != null)
    {
      if ((localObject instanceof STRUCT))
        arrayOfByte = ((STRUCT)localObject).toBytes();
      else if ((localObject instanceof ARRAY))
        arrayOfByte = ((ARRAY)localObject).toBytes();
      else if ((localObject instanceof OPAQUE))
        arrayOfByte = ((OPAQUE)localObject).toBytes();
      else {
        arrayOfByte = ((Datum)localObject).shareBytes();
      }

    }
    else
    {
      SQLException localSQLException = DatabaseError.createSqlException(null, 1, "attempt to convert a Datum to incompatible SQL type");
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    return arrayOfByte;
  }

  public static Datum makeDatum(OracleConnection paramOracleConnection, byte[] paramArrayOfByte, int paramInt1, String paramString, int paramInt2)
    throws SQLException
  {
    Object localObject1 = null;

    int i = paramOracleConnection.getDbCsId();
    int j = paramOracleConnection.getJdbcCsId();
    int k = CharacterSetMetaData.getRatio(j, i);
    Object localObject2;
    switch (paramInt1)
    {
    case 96:
      if ((paramInt2 != 0) && (paramInt2 < paramArrayOfByte.length) && (k == 1))
      {
        localObject1 = new CHAR(paramArrayOfByte, 0, paramInt2, CharacterSet.make(paramOracleConnection.getJdbcCsId()));
      }
      else
      {
        localObject1 = new CHAR(paramArrayOfByte, CharacterSet.make(paramOracleConnection.getJdbcCsId()));
      }

      break;
    case 1:
    case 8:
      localObject1 = new CHAR(paramArrayOfByte, CharacterSet.make(paramOracleConnection.getJdbcCsId()));

      break;
    case 2:
    case 6:
      localObject1 = new NUMBER(paramArrayOfByte);

      break;
    case 100:
      localObject1 = new BINARY_FLOAT(paramArrayOfByte);

      break;
    case 101:
      localObject1 = new BINARY_DOUBLE(paramArrayOfByte);

      break;
    case 23:
    case 24:
      localObject1 = new RAW(paramArrayOfByte);

      break;
    case 104:
      localObject1 = new ROWID(paramArrayOfByte);

      break;
    case 102:
      localObject2 = DatabaseError.createSqlException(null, 1, "need resolution: do we want to handle ResultSet?");
      ((SQLException)localObject2).fillInStackTrace();
      throw ((Throwable)localObject2);
    case 12:
      localObject1 = new DATE(paramArrayOfByte);

      break;
    case 182:
      localObject1 = new INTERVALYM(paramArrayOfByte);

      break;
    case 183:
      localObject1 = new INTERVALDS(paramArrayOfByte);

      break;
    case 180:
      localObject1 = new TIMESTAMP(paramArrayOfByte);

      break;
    case 181:
      localObject1 = new TIMESTAMPTZ(paramArrayOfByte);

      break;
    case 231:
      localObject1 = new TIMESTAMPLTZ(paramArrayOfByte);

      break;
    case 113:
      localObject1 = paramOracleConnection.createBlob(paramArrayOfByte);

      break;
    case 112:
      localObject1 = paramOracleConnection.createClob(paramArrayOfByte);

      break;
    case 114:
      localObject1 = paramOracleConnection.createBfile(paramArrayOfByte);

      break;
    case 109:
      localObject2 = TypeDescriptor.getTypeDescriptor(paramString, paramOracleConnection, paramArrayOfByte, 0L);

      if ((localObject2 instanceof StructDescriptor))
      {
        localObject1 = new STRUCT((StructDescriptor)localObject2, paramArrayOfByte, paramOracleConnection);
      }
      else if ((localObject2 instanceof ArrayDescriptor))
      {
        localObject1 = new ARRAY((ArrayDescriptor)localObject2, paramArrayOfByte, paramOracleConnection);
      }
      else if ((localObject2 instanceof OpaqueDescriptor))
      {
        localObject1 = new OPAQUE((OpaqueDescriptor)localObject2, paramArrayOfByte, paramOracleConnection);
      }

      break;
    case 111:
      localObject2 = getTypeDescriptor(paramString, paramOracleConnection);

      if ((localObject2 instanceof StructDescriptor))
      {
        localObject1 = new REF((StructDescriptor)localObject2, paramOracleConnection, paramArrayOfByte);
      }
      else
      {
        SQLException localSQLException = DatabaseError.createSqlException(null, 1, "program error: REF points to a non-STRUCT");
        localSQLException.fillInStackTrace();
        throw localSQLException;
      }

      break;
    default:
      localObject2 = DatabaseError.createSqlException(null, 1, "program error: invalid SQL type code");
      ((SQLException)localObject2).fillInStackTrace();
      throw ((Throwable)localObject2);
    }

    return localObject1;
  }

  public static Datum makeNDatum(OracleConnection paramOracleConnection, byte[] paramArrayOfByte, int paramInt1, String paramString, short paramShort, int paramInt2)
    throws SQLException
  {
    Object localObject = null;

    switch (paramInt1)
    {
    case 96:
      int i = paramInt2 * CharacterSetMetaData.getRatio(paramOracleConnection.getNCharSet(), 1);

      if ((paramInt2 != 0) && (i < paramArrayOfByte.length)) {
        localObject = new CHAR(paramArrayOfByte, 0, paramInt2, CharacterSet.make(paramOracleConnection.getNCharSet()));
      }
      else {
        localObject = new CHAR(paramArrayOfByte, CharacterSet.make(paramOracleConnection.getNCharSet()));
      }

      break;
    case 1:
    case 8:
      localObject = new CHAR(paramArrayOfByte, CharacterSet.make(paramOracleConnection.getNCharSet()));

      break;
    case 112:
      localObject = paramOracleConnection.createClob(paramArrayOfByte, paramShort);

      break;
    default:
      SQLException localSQLException = DatabaseError.createSqlException(null, 1, "program error: invalid SQL type code");
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    return localObject;
  }

  public static Datum makeDatum(OracleConnection paramOracleConnection, Object paramObject, int paramInt, String paramString)
    throws SQLException
  {
    return makeDatum(paramOracleConnection, paramObject, paramInt, paramString, false);
  }

  public static Datum makeDatum(OracleConnection paramOracleConnection, Object paramObject, int paramInt, String paramString, boolean paramBoolean)
    throws SQLException
  {
    Object localObject1 = null;
    Object localObject2;
    switch (paramInt)
    {
    case 1:
    case 8:
    case 96:
      localObject1 = new CHAR(paramObject, CharacterSet.make(paramBoolean ? paramOracleConnection.getNCharSet() : paramOracleConnection.getJdbcCsId()));

      break;
    case 2:
    case 6:
      localObject1 = new NUMBER(paramObject);

      break;
    case 100:
      localObject1 = new BINARY_FLOAT((Float)paramObject);

      break;
    case 101:
      localObject1 = new BINARY_DOUBLE((Double)paramObject);

      break;
    case 23:
    case 24:
      localObject1 = new RAW(paramObject);

      break;
    case 104:
      localObject1 = new ROWID((byte[])paramObject);

      break;
    case 102:
      localObject2 = DatabaseError.createSqlException(null, 1, "need resolution: do we want to handle ResultSet");
      ((SQLException)localObject2).fillInStackTrace();
      throw ((Throwable)localObject2);
    case 12:
      localObject1 = new DATE(paramObject);

      break;
    case 180:
      if ((paramObject instanceof TIMESTAMP))
      {
        localObject1 = (Datum)paramObject;
      }
      else if ((paramObject instanceof Timestamp))
        localObject1 = new TIMESTAMP((Timestamp)paramObject);
      else if ((paramObject instanceof Date))
        localObject1 = new TIMESTAMP((Date)paramObject);
      else if ((paramObject instanceof Time))
        localObject1 = new TIMESTAMP((Time)paramObject);
      else if ((paramObject instanceof DATE))
        localObject1 = new TIMESTAMP((DATE)paramObject);
      else if ((paramObject instanceof String))
        localObject1 = new TIMESTAMP((String)paramObject);
      else if ((paramObject instanceof byte[]))
        localObject1 = new TIMESTAMP((byte[])paramObject); break;
    case 181:
      if ((paramObject instanceof TIMESTAMPTZ))
      {
        localObject1 = (Datum)paramObject;
      }
      else if ((paramObject instanceof Timestamp))
        localObject1 = new TIMESTAMPTZ(paramOracleConnection, (Timestamp)paramObject);
      else if ((paramObject instanceof Date))
        localObject1 = new TIMESTAMPTZ(paramOracleConnection, (Date)paramObject);
      else if ((paramObject instanceof Time))
        localObject1 = new TIMESTAMPTZ(paramOracleConnection, (Time)paramObject);
      else if ((paramObject instanceof DATE))
        localObject1 = new TIMESTAMPTZ(paramOracleConnection, (DATE)paramObject);
      else if ((paramObject instanceof String))
        localObject1 = new TIMESTAMPTZ(paramOracleConnection, (String)paramObject);
      else if ((paramObject instanceof byte[]))
        localObject1 = new TIMESTAMPTZ((byte[])paramObject); break;
    case 231:
      if ((paramObject instanceof TIMESTAMPLTZ))
      {
        localObject1 = (Datum)paramObject;
      }
      else if ((paramObject instanceof Timestamp))
        localObject1 = new TIMESTAMPLTZ(paramOracleConnection, (Timestamp)paramObject);
      else if ((paramObject instanceof Date))
        localObject1 = new TIMESTAMPLTZ(paramOracleConnection, (Date)paramObject);
      else if ((paramObject instanceof Time))
        localObject1 = new TIMESTAMPLTZ(paramOracleConnection, (Time)paramObject);
      else if ((paramObject instanceof DATE))
        localObject1 = new TIMESTAMPLTZ(paramOracleConnection, (DATE)paramObject);
      else if ((paramObject instanceof String))
        localObject1 = new TIMESTAMPLTZ(paramOracleConnection, (String)paramObject);
      else if ((paramObject instanceof byte[]))
        localObject1 = new TIMESTAMPLTZ((byte[])paramObject); break;
    case 113:
      if ((paramObject instanceof BLOB))
      {
        localObject1 = (Datum)paramObject;
      }
      if ((paramObject instanceof byte[]))
      {
        localObject1 = new RAW((byte[])paramObject); } break;
    case 112:
      if ((paramObject instanceof CLOB))
      {
        localObject1 = (Datum)paramObject;
      }

      if ((paramObject instanceof String))
      {
        localObject2 = CharacterSet.make(paramBoolean ? paramOracleConnection.getNCharSet() : paramOracleConnection.getJdbcCsId());
        localObject1 = new CHAR((String)paramObject, (CharacterSet)localObject2);
      }break;
    case 114:
      if ((paramObject instanceof BFILE))
      {
        localObject1 = (Datum)paramObject; } break;
    case 109:
      if (((paramObject instanceof STRUCT)) || ((paramObject instanceof ARRAY)) || ((paramObject instanceof OPAQUE)))
      {
        localObject1 = (Datum)paramObject; } break;
    case 111:
      if ((paramObject instanceof REF))
      {
        localObject1 = (Datum)paramObject; } break;
    }

    if (localObject1 == null)
    {
      localObject2 = DatabaseError.createSqlException(null, 1, "Unable to construct a Datum from the specified input");
      ((SQLException)localObject2).fillInStackTrace();
      throw ((Throwable)localObject2);
    }

    return localObject1;
  }

  private static int classNumber(Class paramClass)
  {
    int i = -1;
    Integer localInteger = (Integer)classTable.get(paramClass);

    if (localInteger != null)
    {
      i = localInteger.intValue();
    }

    return i;
  }

  public static Object getTypeDescriptor(String paramString, OracleConnection paramOracleConnection)
    throws SQLException
  {
    Object localObject = null;

    SQLName localSQLName = new SQLName(paramString, paramOracleConnection);
    String str = localSQLName.getName();

    localObject = paramOracleConnection.getDescriptor(str);

    if (localObject != null)
    {
      return localObject;
    }

    OracleTypeADT localOracleTypeADT = new OracleTypeADT(str, paramOracleConnection);
    localOracleTypeADT.init(paramOracleConnection);

    OracleNamedType localOracleNamedType = localOracleTypeADT.cleanup();

    switch (localOracleNamedType.getTypeCode())
    {
    case 2003:
      localObject = new ArrayDescriptor(localSQLName, (OracleTypeCOLLECTION)localOracleNamedType, paramOracleConnection);

      break;
    case 2002:
    case 2008:
      localObject = new StructDescriptor(localSQLName, (OracleTypeADT)localOracleNamedType, paramOracleConnection);

      break;
    case 2007:
      localObject = new OpaqueDescriptor(localSQLName, (OracleTypeOPAQUE)localOracleNamedType, paramOracleConnection);

      break;
    case 2004:
    case 2005:
    case 2006:
    default:
      SQLException localSQLException = DatabaseError.createSqlException(null, 1, "Unrecognized type code");
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    paramOracleConnection.putDescriptor(str, localObject);

    return localObject;
  }

  public static boolean checkDatumType(Datum paramDatum, int paramInt, String paramString)
    throws SQLException
  {
    boolean bool = false;

    switch (paramInt)
    {
    case 1:
    case 8:
    case 96:
      bool = paramDatum instanceof CHAR;

      break;
    case 2:
    case 6:
      bool = paramDatum instanceof NUMBER;

      break;
    case 100:
      bool = paramDatum instanceof BINARY_FLOAT;

      break;
    case 101:
      bool = paramDatum instanceof BINARY_DOUBLE;

      break;
    case 23:
    case 24:
      bool = paramDatum instanceof RAW;

      break;
    case 104:
      bool = paramDatum instanceof ROWID;

      break;
    case 12:
      bool = paramDatum instanceof DATE;

      break;
    case 180:
      bool = paramDatum instanceof TIMESTAMP;

      break;
    case 181:
      bool = paramDatum instanceof TIMESTAMPTZ;

      break;
    case 231:
      bool = paramDatum instanceof TIMESTAMPLTZ;

      break;
    case 113:
      bool = paramDatum instanceof BLOB;

      break;
    case 112:
      bool = paramDatum instanceof CLOB;

      break;
    case 114:
      bool = paramDatum instanceof BFILE;

      break;
    case 111:
      bool = ((paramDatum instanceof REF)) && (((REF)paramDatum).getBaseTypeName().equals(paramString));

      break;
    case 109:
      if ((paramDatum instanceof STRUCT))
      {
        bool = ((STRUCT)paramDatum).isInHierarchyOf(paramString);
      }
      else if ((paramDatum instanceof ARRAY))
      {
        bool = ((ARRAY)paramDatum).getSQLTypeName().equals(paramString);
      }
      else if ((paramDatum instanceof OPAQUE))
      {
        bool = ((OPAQUE)paramDatum).getSQLTypeName().equals(paramString); } break;
    case 102:
    default:
      bool = false;
    }

    return bool;
  }

  public static boolean implementsInterface(Class paramClass1, Class paramClass2)
  {
    if (paramClass1 == null)
    {
      return false;
    }

    if (paramClass1 == paramClass2)
    {
      return true;
    }

    Class[] arrayOfClass = paramClass1.getInterfaces();

    for (int i = 0; i < arrayOfClass.length; i++)
    {
      if (implementsInterface(arrayOfClass[i], paramClass2))
      {
        return true;
      }
    }

    return implementsInterface(paramClass1.getSuperclass(), paramClass2);
  }

  public static Datum makeOracleDatum(OracleConnection paramOracleConnection, Object paramObject, int paramInt, String paramString)
    throws SQLException
  {
    return makeOracleDatum(paramOracleConnection, paramObject, paramInt, paramString, false);
  }

  public static Datum makeOracleDatum(OracleConnection paramOracleConnection, Object paramObject, int paramInt, String paramString, boolean paramBoolean)
    throws SQLException
  {
    Datum localDatum = makeDatum(paramOracleConnection, paramObject, getInternalType(paramInt), paramString, paramBoolean);

    return localDatum;
  }

  public static int getInternalType(int paramInt)
    throws SQLException
  {
    int i = 0;

    switch (paramInt)
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
    case 8:
      i = 6;

      break;
    case 100:
      i = 100;

      break;
    case 101:
      i = 101;

      break;
    case 999:
      i = 999;

      break;
    case 1:
      i = 96;

      break;
    case -15:
      i = 96;
      break;
    case 12:
      i = 1;

      break;
    case -9:
      i = 1;
      break;
    case -1:
      i = 8;

      break;
    case 91:
    case 92:
      i = 12;

      break;
    case -100:
    case 93:
      i = 180;

      break;
    case -101:
      i = 181;

      break;
    case -102:
      i = 231;

      break;
    case -104:
      i = 183;

      break;
    case -103:
      i = 182;

      break;
    case -3:
    case -2:
      i = 23;

      break;
    case -4:
      i = 24;

      break;
    case -8:
      i = 104;

      break;
    case 2004:
      i = 113;

      break;
    case 2005:
      i = 112;

      break;
    case 2011:
      i = 112;

      break;
    case -13:
      i = 114;

      break;
    case -10:
      i = 102;

      break;
    case 2002:
    case 2003:
    case 2007:
    case 2008:
      i = 109;

      break;
    case 2006:
      i = 111;

      break;
    default:
      SQLException localSQLException = DatabaseError.createSqlException(null, 4, "get_internal_type");
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    return i;
  }

  protected OracleConnection getConnectionDuringExceptionHandling()
  {
    return null;
  }

  static
  {
    try
    {
      classTable.put(Class.forName("java.lang.String"), Integer.valueOf(0));

      classTable.put(Class.forName("java.lang.Boolean"), Integer.valueOf(1));

      classTable.put(Class.forName("java.lang.Integer"), Integer.valueOf(2));

      classTable.put(Class.forName("java.lang.Long"), Integer.valueOf(3));
      classTable.put(Class.forName("java.lang.Float"), Integer.valueOf(4));

      classTable.put(Class.forName("java.lang.Double"), Integer.valueOf(5));

      classTable.put(Class.forName("java.math.BigDecimal"), Integer.valueOf(6));

      classTable.put(Class.forName("java.sql.Date"), Integer.valueOf(7));
      classTable.put(Class.forName("java.sql.Time"), Integer.valueOf(8));
      classTable.put(Class.forName("java.sql.Timestamp"), Integer.valueOf(9));
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
    }
  }
}