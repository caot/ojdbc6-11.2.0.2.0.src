package oracle.sql;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import oracle.jdbc.driver.DatabaseError;
import oracle.jdbc.oracore.OracleNamedType;
import oracle.jdbc.oracore.OracleTypeADT;
import oracle.jdbc.oracore.OracleTypeCOLLECTION;
import oracle.jdbc.oracore.OracleTypeOPAQUE;
import oracle.jdbc.oracore.PickleContext;

public class TypeDescriptor
  implements Serializable, ORAData
{
  public static boolean DEBUG_SERIALIZATION = false;
  static final long serialVersionUID = 2022598722047823723L;
  static final int KOIDFLEN = 16;
  static final short KOTA_TRN = 1;
  static final short KOTA_PDF = 2;
  static final short KOTA_ITOID = 4;
  static final short KOTA_LOB = 8;
  static final short KOTA_AD = 16;
  static final short KOTA_NMHSH = 32;
  static final short KOTA_TEV = 64;
  static final short KOTA_INH = 128;
  static final short KOTA_10I = 256;
  static final short KOTA_RBF = 512;
  static final short KOTA_HBF = 1024;
  static final int ANYTYPE_IMAGE_SIZE_TOID = 23;
  static final int ANYTYPE_IMAGE_SIZE_NO_TOID = 5;
  static final byte KOTTDOID = 1;
  static final byte KOTTBOID = 2;
  static final byte KOTADOID = 3;
  static final byte KOTREFOID = 4;
  static final byte KOTMDOID = 5;
  static final byte KOTMIOID = 6;
  static final byte KOTEXOID = 7;
  static final byte KOTDATOID = 8;
  static final byte KOTBYTOID = 9;
  static final byte KOTSHOOID = 10;
  static final byte KOTLONOID = 11;
  static final byte KOTREAOID = 12;
  static final byte KOTDOUOID = 13;
  static final byte KOTFLOOID = 14;
  static final byte KOTNUMOID = 15;
  static final byte KOTDECOID = 16;
  static final byte KOTUBYOID = 17;
  static final byte KOTUSHOID = 18;
  static final byte KOTULOOID = 19;
  static final byte KOTOCTOID = 20;
  static final byte KOTSMLOID = 21;
  static final byte KOTINTOID = 22;
  static final byte KOTRAWOID = 23;
  static final byte KOTPTROID = 24;
  static final byte KOTVSIOID = 25;
  static final byte KOTFSIOID = 26;
  static final byte KOTVSOOID = 27;
  static final byte KOTMLSOID = 28;
  static final byte KOTVAROID = 29;
  static final byte KOTMSTOID = 30;
  static final byte KOTNATOID = 31;
  static final byte KOTDOMOID = 32;
  static final byte KOTUND1OID = 33;
  static final byte KOTCLBOID = 34;
  static final byte KOTBLBOID = 35;
  static final byte KOTCFLOID = 36;
  static final byte KOTBFLOID = 37;
  static final byte KOTOIDOID = 38;
  static final byte KOTCAROID = 39;
  static final byte KOTCANOID = 40;
  static final byte KOTLPTOID = 41;
  static final byte KOTBRIOID = 42;
  static final byte KOTUCOOID = 43;
  static final byte KOTRECOID = 44;
  static final byte KOTRCUOID = 45;
  static final byte KOTBOOOID = 46;
  static final byte KOTRIDOID = 47;
  static final byte KOTPLOOID = 48;
  static final byte KOTPLROID = 49;
  static final byte KOTPBIOID = 50;
  static final byte KOTPINOID = 51;
  static final byte KOTPNAOID = 52;
  static final byte KOTPNNOID = 53;
  static final byte KOTPPOOID = 54;
  static final byte KOTPPNOID = 55;
  static final byte KOTPSTOID = 56;
  static final byte KOTEX1OID = 57;
  static final byte KOTOPQOID = 58;
  static final byte KOTTMOID = 59;
  static final byte KOTTMTZOID = 60;
  static final byte KOTTSOID = 61;
  static final byte KOTTSTZOID = 62;
  static final byte KOTIYMOID = 63;
  static final byte KOTIDSOID = 64;
  static final byte KOTTSIMPTZOID = 65;
  static final byte KOTTBXOID = 66;
  static final byte KOTADXOID = 67;
  static final byte KOTOIDBFLT = 68;
  static final byte KOTOIDBDBL = 69;
  static final byte KOTURDOID = 70;
  static final byte KOTLASTOID = 71;
  static final byte[] KOTTDEXTOID = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1 };

  static final byte[] KOTTBEXTOID = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 2 };

  static final byte[] KOTADEXTOID = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 3 };

  static final byte[] KOTMDEXTOID = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 4 };

  static final byte[] KOTTBXEXTOID = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 66 };

  static final byte[] KOTADXEXTOID = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 67 };

  static final byte[] KOTTDTOID = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 };

  static final byte[] KOTTBTOID = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2 };

  static final byte[] KOTADTOID = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3 };

  static final byte[] KOTMDTOID = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5 };

  static final byte[] KOTMITOID = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 6 };

  static final byte[] KOTEXTOID = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7 };

  static final byte[] KOTEX1TOID = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 57 };

  static final byte[] KOTTBXTOID = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 66 };

  static final byte[] KOTADXTOID = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 67 };

  public static final byte[] RAWTOID = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 23 };

  public static final byte[] ANYTYPETOID = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 16 };

  public static final byte[] ANYDATATOID = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 17 };

  public static final byte[] ANYDATASETTOID = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 18 };

  public static final byte[] XMLTYPETOID = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 1, 0 };
  static final short SQLT_NONE = 0;
  static final short SQLT_CHR = 1;
  static final short SQLT_NUM = 2;
  static final short SQLT_INT = 3;
  static final short SQLT_FLT = 4;
  static final short SQLT_STR = 5;
  static final short SQLT_VNU = 6;
  static final short SQLT_PDN = 7;
  static final short SQLT_LNG = 8;
  static final short SQLT_VCS = 9;
  static final short SQLT_NON = 10;
  static final short SQLT_RID = 11;
  static final short SQLT_DAT = 12;
  static final short SQLT_VBI = 15;
  static final short SQLT_BFLOAT = 21;
  static final short SQLT_BDOUBLE = 22;
  static final short SQLT_BIN = 23;
  static final short SQLT_LBI = 24;
  static final short SQLT_UIN = 68;
  static final short SQLT_SLS = 91;
  static final short SQLT_LVC = 94;
  static final short SQLT_LVB = 95;
  static final short SQLT_AFC = 96;
  static final short SQLT_AVC = 97;
  static final short SQLT_IBFLOAT = 100;
  static final short SQLT_IBDOUBLE = 101;
  static final short SQLT_CUR = 102;
  static final short SQLT_RDD = 104;
  static final short SQLT_LAB = 105;
  static final short SQLT_OSL = 106;
  static final short SQLT_NTY = 108;
  static final short SQLT_REF = 110;
  static final short SQLT_CLOB = 112;
  static final short SQLT_BLOB = 113;
  static final short SQLT_BFILEE = 114;
  static final short SQLT_FILE = 114;
  static final short SQLT_CFILEE = 115;
  static final short SQLT_RSET = 116;
  static final short SQLT_SVT = 118;
  static final short SQLT_NCO = 122;
  static final short SQLT_DTR = 152;
  static final short SQLT_DUN = 153;
  static final short SQLT_DOP = 154;
  static final short SQLT_VST = 155;
  static final short SQLT_ODT = 156;
  static final short SQLT_DOL = 172;
  static final short SQLT_DATE = 184;
  static final short SQLT_TIME = 185;
  static final short SQLT_TIME_TZ = 186;
  static final short SQLT_TIMESTAMP = 187;
  static final short SQLT_TIMESTAMP_TZ = 188;
  static final short SQLT_INTERVAL_YM = 189;
  static final short SQLT_INTERVAL_DS = 190;
  static final short SQLT_TIMESTAMP_LTZ = 232;
  static final short SQLT_PNTY = 241;
  static final short SQLT_CFILE = 115;
  static final short SQLT_BFILE = 114;
  static final short SQLT_REC = 250;
  static final short SQLT_TAB = 251;
  static final short SQLT_BOL = 252;
  static final short SQLCS_IMPLICIT = 1;
  static final short SQLCS_NCHAR = 2;
  static final short SQLCS_EXPLICIT = 3;
  static final short SQLCS_FLEXIBLE = 4;
  static final short SQLCS_LIT_NULL = 5;
  static final short SQLT_XDP = 103;
  static final short SQLT_OKO = 107;
  static final short SQLT_INTY = 109;
  static final short SQLT_IREF = 111;
  static final short SQLT_DCLOB = 195;
  public static final short TYPECODE_REF = 110;
  public static final short TYPECODE_DATE = 12;
  public static final short TYPECODE_SIGNED8 = 27;
  public static final short TYPECODE_SIGNED16 = 28;
  public static final short TYPECODE_SIGNED32 = 29;
  public static final short TYPECODE_REAL = 21;
  public static final short TYPECODE_DOUBLE = 22;
  public static final short TYPECODE_BFLOAT = 100;
  public static final short TYPECODE_BDOUBLE = 101;
  public static final short TYPECODE_FLOAT = 4;
  public static final short TYPECODE_NUMBER = 2;
  public static final short TYPECODE_DECIMAL = 7;
  public static final short TYPECODE_UNSIGNED8 = 23;
  public static final short TYPECODE_UNSIGNED16 = 25;
  public static final short TYPECODE_UNSIGNED32 = 26;
  public static final short TYPECODE_OCTET = 245;
  public static final short TYPECODE_SMALLINT = 246;
  public static final short TYPECODE_INTEGER = 3;
  public static final short TYPECODE_RAW = 95;
  public static final short TYPECODE_PTR = 32;
  public static final short TYPECODE_VARCHAR2 = 9;
  public static final short TYPECODE_CHAR = 96;
  public static final short TYPECODE_VARCHAR = 1;
  public static final short TYPECODE_MLSLABEL = 105;
  public static final short TYPECODE_VARRAY = 247;
  public static final short TYPECODE_TABLE = 248;
  public static final short TYPECODE_OBJECT = 108;
  public static final short TYPECODE_OPAQUE = 58;
  public static final short TYPECODE_NAMEDCOLLECTION = 122;
  public static final short TYPECODE_BLOB = 113;
  public static final short TYPECODE_BFILE = 114;
  public static final short TYPECODE_CLOB = 112;
  public static final short TYPECODE_CFILE = 115;
  public static final short TYPECODE_TIME = 185;
  public static final short TYPECODE_TIME_TZ = 186;
  public static final short TYPECODE_TIMESTAMP = 187;
  public static final short TYPECODE_TIMESTAMP_TZ = 188;
  public static final short TYPECODE_TIMESTAMP_LTZ = 232;
  public static final short TYPECODE_INTERVAL_YM = 189;
  public static final short TYPECODE_INTERVAL_DS = 190;
  public static final short TYPECODE_UROWID = 104;
  public static final short TYPECODE_OTMFIRST = 228;
  public static final short TYPECODE_OTMLAST = 320;
  public static final short TYPECODE_SYSFIRST = 228;
  public static final short TYPECODE_SYSLAST = 235;
  public static final short TYPECODE_PLS_INTEGER = 266;
  public static final short TYPECODE_ITABLE = 251;
  public static final short TYPECODE_RECORD = 250;
  public static final short TYPECODE_BOOLEAN = 252;
  public static final short TYPECODE_NCHAR = 286;
  public static final short TYPECODE_NVARCHAR2 = 287;
  public static final short TYPECODE_NCLOB = 288;
  public static final short TYPECODE_NONE = 0;
  public static final short TYPECODE_ERRHP = 283;
  public static final short TYPECODE_JDBC_JOBJECT = 2000;
  public static final short TYPECODE_JDBC_STRUCT = 2002;
  public static final short TYPECODE_JDBC_ARRAY = 2003;
  public static final short TYPECODE_JDBC_JOPAQUE = 2000;
  public static final short TYPECODE_JDBC_REF = 2006;
  public static final short TYPECODE_JDBC_JSTRUCT = 2008;
  private static final short TYPECODE_MAXVALUE = 2008;
  static final short[] OID_TO_TYPECODE = new short[71];
  SQLName sqlName;
  OracleNamedType pickler;
  transient oracle.jdbc.internal.OracleConnection connection;
  short internalTypeCode;
  boolean isTransient = false;
  byte[] toid = null;
  int toidVersion = 1;
  long precision;
  byte scale;
  byte[] transientImage = null;

  AttributeDescriptor[] attributesDescriptor = null;

  transient Boolean isInstanciable = null;
  transient String supertype = null;
  transient int numLocalAttrs = -1;
  transient String[] subtypes = null;
  transient String[] attrJavaNames = null;
  private static String[] typeCodeTypeNameMap;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  void copyDescriptor(TypeDescriptor paramTypeDescriptor)
  {
    this.sqlName = paramTypeDescriptor.sqlName;
    this.pickler = paramTypeDescriptor.pickler;
    this.connection = paramTypeDescriptor.connection;
    this.internalTypeCode = paramTypeDescriptor.internalTypeCode;
    this.isTransient = paramTypeDescriptor.isTransient;
    this.toid = paramTypeDescriptor.toid;
    this.toidVersion = paramTypeDescriptor.toidVersion;
    this.precision = paramTypeDescriptor.precision;
    this.scale = paramTypeDescriptor.scale;
    this.transientImage = paramTypeDescriptor.transientImage;
    this.attributesDescriptor = paramTypeDescriptor.attributesDescriptor;
    this.isInstanciable = paramTypeDescriptor.isInstanciable;
    this.supertype = paramTypeDescriptor.supertype;
    this.numLocalAttrs = paramTypeDescriptor.numLocalAttrs;
    this.subtypes = paramTypeDescriptor.subtypes;
    this.attrJavaNames = paramTypeDescriptor.attrJavaNames;
  }

  protected TypeDescriptor(short paramShort)
  {
    this.internalTypeCode = paramShort;
  }

  protected TypeDescriptor(short paramShort, String paramString, Connection paramConnection)
    throws SQLException
  {
    this.internalTypeCode = paramShort;

    if ((paramString == null) || (paramConnection == null))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 60, "Invalid arguments");
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    setPhysicalConnectionOf(paramConnection);

    this.sqlName = new SQLName(paramString, getInternalConnection());
  }

  protected TypeDescriptor(short paramShort, SQLName paramSQLName, Connection paramConnection)
    throws SQLException
  {
    this.internalTypeCode = paramShort;
    if ((paramSQLName == null) || (paramConnection == null))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 60, "Invalid arguments");
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.sqlName = paramSQLName;

    setPhysicalConnectionOf(paramConnection);
  }

  protected TypeDescriptor(short paramShort, SQLName paramSQLName, OracleTypeADT paramOracleTypeADT, Connection paramConnection)
    throws SQLException
  {
    this.internalTypeCode = paramShort;
    if ((paramSQLName == null) || (paramOracleTypeADT == null))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 60, "Invalid arguments");
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.sqlName = paramSQLName;
    if (paramConnection != null) {
      setPhysicalConnectionOf(paramConnection);
    }
    this.pickler = paramOracleTypeADT;

    this.pickler.setDescriptor(this);
  }

  protected TypeDescriptor(short paramShort, OracleTypeADT paramOracleTypeADT, Connection paramConnection)
    throws SQLException
  {
    this.internalTypeCode = paramShort;
    if ((paramOracleTypeADT == null) || (paramConnection == null))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 60, "Invalid arguments");
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    setPhysicalConnectionOf(paramConnection);

    this.sqlName = null;
    this.pickler = paramOracleTypeADT;
    this.pickler.setDescriptor(this);
  }

  public String getName()
    throws SQLException
  {
    synchronized (this.connection)
    {
      if (this.sqlName == null) {
        initSQLName();
      }
      String str = null;

      if (this.sqlName != null)
        str = this.sqlName.getName();
      return str;
    }
  }

  public SQLName getSQLName()
    throws SQLException
  {
    synchronized (this.connection)
    {
      if (this.sqlName == null) {
        initSQLName();
      }
      return this.sqlName;
    }
  }

  void initSQLName()
    throws SQLException
  {
    if (!this.isTransient)
    {
      SQLException sqlexception;
      if (this.connection == null)
      {
        sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 1);
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }

      if (this.pickler != null) {
        this.sqlName = new SQLName(this.pickler.getFullName(), this.connection);
      } else if (this.toid != null)
      {
        String str = OracleTypeADT.toid2typename(this.connection, this.toid);

        this.sqlName = new SQLName(str, this.connection);

        TypeDescriptor localTypeDescriptor = null;
        str = this.sqlName.getName();
        localTypeDescriptor = (TypeDescriptor)this.connection.getDescriptor(str);

        if (localTypeDescriptor != null)
          copyDescriptor(localTypeDescriptor);
      }
      else if ((this.internalTypeCode == 108) || (this.internalTypeCode == 122))
      {
        sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 1);
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }
    }
  }

  public String getSchemaName()
    throws SQLException
  {
    String str = null;
    if (this.sqlName == null)
      initSQLName();
    if (this.sqlName != null)
      str = this.sqlName.getSchema();
    return str;
  }

  public String getTypeName()
    throws SQLException
  {
    String str = null;
    if (this.sqlName == null)
      initSQLName();
    if (this.sqlName != null)
      str = this.sqlName.getSimpleName();
    return str;
  }

  public OracleNamedType getPickler()
  {
    return this.pickler;
  }

  public oracle.jdbc.internal.OracleConnection getInternalConnection()
  {
    return this.connection;
  }

  public void setPhysicalConnectionOf(Connection paramConnection)
  {
    this.connection = ((oracle.jdbc.OracleConnection)paramConnection).physicalConnectionWithin();
  }

  public int getTypeCode()
    throws SQLException
  {
    return this.internalTypeCode;
  }

  public String getTypeCodeName()
    throws SQLException
  {
    return getTypeCodeTypeNameMap()[getTypeCode()];
  }

  private static String[] getTypeCodeTypeNameMap()
    throws SQLException
  {
    if (typeCodeTypeNameMap == null)
    {
      String[] arrayOfString = new String[2009];

      Class localClass = null;
      try
      {
        localClass = Class.forName("oracle.sql.TypeDescriptor");
      }
      catch (ClassNotFoundException localClassNotFoundException)
      {
        SQLException localSQLException1 = DatabaseError.createSqlException(null, 1, "TypeDescriptor.getTypeCodeName: got a ClassNotFoundException: " + localClassNotFoundException.getMessage());
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      Field[] arrayOfField = localClass.getFields();
      for (int i = 0; i < arrayOfField.length; i++) {
        if (arrayOfField[i].getName().startsWith("TYPECODE_")) {
          try
          {
            arrayOfString[arrayOfField[i].getInt(null)] = arrayOfField[i].getName();
          }
          catch (Exception localException)
          {
            SQLException localSQLException2 = DatabaseError.createSqlException(null, 1, "TypeDescriptor.getTypeCodeName: " + localException.getMessage());
            localSQLException2.fillInStackTrace();
            throw localSQLException2;
          }
        }

      }

      typeCodeTypeNameMap = arrayOfString;
    }
    return typeCodeTypeNameMap;
  }

  public short getInternalTypeCode()
    throws SQLException
  {
    return this.internalTypeCode;
  }

  public static TypeDescriptor getTypeDescriptor(String paramString, oracle.jdbc.OracleConnection paramOracleConnection)
    throws SQLException
  {
    TypeDescriptor typedescriptor = null;
    try
    {
      SQLName localSQLName = new SQLName(paramString, paramOracleConnection);
      String str = localSQLName.getName();

      typedescriptor = (TypeDescriptor)paramOracleConnection.getDescriptor(str);

      if (typedescriptor == null)
      {
        OracleTypeADT localOracleTypeADT = new OracleTypeADT(str, paramOracleConnection);
        oracle.jdbc.internal.OracleConnection localOracleConnection = (oracle.jdbc.internal.OracleConnection)paramOracleConnection;

        localOracleTypeADT.init(localOracleConnection);

        OracleNamedType localOracleNamedType = localOracleTypeADT.cleanup();

        switch (localOracleNamedType.getTypeCode())
        {
        case 2002:
        case 2008:
          typedescriptor = new StructDescriptor(localSQLName, (OracleTypeADT)localOracleNamedType, paramOracleConnection);

          break;
        case 2003:
          typedescriptor = new ArrayDescriptor(localSQLName, (OracleTypeCOLLECTION)localOracleNamedType, paramOracleConnection);

          break;
        case 2007:
          typedescriptor = new OpaqueDescriptor(localSQLName, (OracleTypeOPAQUE)localOracleNamedType, paramOracleConnection);

          break;
        case 2004:
        case 2005:
        case 2006:
        default:
          SQLException localSQLException = DatabaseError.createSqlException(null, 1);
          localSQLException.fillInStackTrace();
          throw localSQLException;
        }

        paramOracleConnection.putDescriptor(str, typedescriptor);

        localOracleNamedType.setDescriptor((TypeDescriptor)typedescriptor);
      }

    }
    catch (Exception localException)
    {
      if ((localException instanceof SQLException))
      {
        SQLException sqlexception  = DatabaseError.createSqlException(null, (SQLException)localException, 60, "Unable to resolve type \"" + paramString + "\"");

        sqlexception .fillInStackTrace();
        throw sqlexception;
      }

      SQLException sqlexception = DatabaseError.createSqlException(null, 60, "Unable to resolve type \"" + paramString + "\"");

      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    return typedescriptor;
  }

  public static TypeDescriptor getTypeDescriptor(String paramString, oracle.jdbc.OracleConnection paramOracleConnection, byte[] paramArrayOfByte, long paramLong)
    throws SQLException
  {
    TypeDescriptor localObject = null;

    String str = getSubtypeName(paramOracleConnection, paramArrayOfByte, paramLong);

    if (str == null) {
      str = paramString;
    }

    localObject = (TypeDescriptor)paramOracleConnection.getDescriptor(str);

    if (localObject == null)
    {
      SQLName localSQLName = new SQLName(str, paramOracleConnection);

      OracleTypeADT localOracleTypeADT = new OracleTypeADT(str, paramOracleConnection);
      oracle.jdbc.internal.OracleConnection localOracleConnection = (oracle.jdbc.internal.OracleConnection)paramOracleConnection;

      localOracleTypeADT.init(localOracleConnection);

      OracleNamedType localOracleNamedType = localOracleTypeADT.cleanup();

      switch (localOracleNamedType.getTypeCode())
      {
      case 2002:
      case 2008:
        localObject = new StructDescriptor(localSQLName, (OracleTypeADT)localOracleNamedType, paramOracleConnection);

        break;
      case 2003:
        localObject = new ArrayDescriptor(localSQLName, (OracleTypeCOLLECTION)localOracleNamedType, paramOracleConnection);

        break;
      case 2007:
        localObject = new OpaqueDescriptor(localSQLName, (OracleTypeOPAQUE)localOracleNamedType, paramOracleConnection);

        break;
      case 2004:
      case 2005:
      case 2006:
      default:
        SQLException localSQLException = DatabaseError.createSqlException(null, 1);
        localSQLException.fillInStackTrace();
        throw localSQLException;
      }

      paramOracleConnection.putDescriptor(str, localObject);
    }
    return localObject;
  }

  public Datum toDatum(Connection paramConnection)
    throws SQLException
  {
    this.connection = ((oracle.jdbc.internal.OracleConnection)paramConnection);
    OpaqueDescriptor localOpaqueDescriptor = OpaqueDescriptor.createDescriptor("SYS.ANYTYPE", paramConnection);

    byte[] arrayOfByte = new byte[getOpaqueImageTypeSize()];
    pickleOpaqueTypeImage(arrayOfByte, 0, false);
    OPAQUE localOPAQUE = new OPAQUE(localOpaqueDescriptor, this.connection, arrayOfByte);
    localOPAQUE.setShareBytes(localOPAQUE.toBytes());
    return localOPAQUE;
  }

  static TypeDescriptor unpickleOpaqueTypeImage(PickleContext paramPickleContext, Connection paramConnection, short[] paramArrayOfShort)
    throws SQLException
  {
    byte[] arrayOfByte1 = null;

    TypeDescriptor typedescriptor = null;

    int k = paramPickleContext.offset();
    byte[] arrayOfByte2 = paramPickleContext.image();

    paramPickleContext.skipBytes(1);
    int i = (short)paramPickleContext.readUB2();
    paramArrayOfShort[0] = ((short)paramPickleContext.readUB2());
    SQLException sqlexception;
    if ((i & 0x20) != 0)
    {
      sqlexception = DatabaseError.createSqlException(null, 178);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }
    SQLException localObject3;
    int m;
    byte[] localObject4;
    if ((i & 0x1) == 0)
    {
      if (((i & 0x2) == 0) || (paramArrayOfShort[0] == 110))
      {
        arrayOfByte1 = paramPickleContext.readDataValue(16);
        int j = paramPickleContext.readUB2();

        String str = (String)((oracle.jdbc.internal.OracleConnection)paramConnection).getDescriptor(arrayOfByte1);
        typedescriptor = (TypeDescriptor)((oracle.jdbc.internal.OracleConnection)paramConnection).getDescriptor(str);
        if (typedescriptor == null)
        {
          if (paramArrayOfShort[0] == 122) {
            typedescriptor = new ArrayDescriptor(arrayOfByte1, j, paramConnection);
          } else if ((paramArrayOfShort[0] == 108) || (paramArrayOfShort[0] == 110)) {
            typedescriptor = new StructDescriptor(arrayOfByte1, j, paramConnection);
          } else if (paramArrayOfShort[0] == 58) {
            typedescriptor = new OpaqueDescriptor(arrayOfByte1, j, paramConnection);
          }
          else {
            sqlexception = DatabaseError.createSqlException(null, 178);
            sqlexception.fillInStackTrace();
            throw sqlexception;
          }

        }

      }
      else
      {
        typedescriptor = new TypeDescriptor(paramArrayOfShort[0]);
      }
      ((TypeDescriptor)typedescriptor).setTransient(false);
    }
    else
    {
      m = (int)paramPickleContext.readUB4();
      if (paramArrayOfShort[0] == 108)
      {
        AttributeDescriptor[] attributedescriptor = null;
        if (m > 0)
        {
          attributedescriptor = new AttributeDescriptor[m];
          for (int i1 = 0; i1 < m; i1++)
          {
            int i2 = paramPickleContext.readByte();
            attributedescriptor[i1] = Kotad.unpickleAttributeImage(i2 == 2, paramPickleContext);
            if (i2 != 2)
            {
              short[] arrayOfShort = new short[1];
              attributedescriptor[i1].setTypeDescriptor(unpickleOpaqueTypeImage(paramPickleContext, paramConnection, arrayOfShort));
            }
          }
        }

        typedescriptor = new StructDescriptor(attributedescriptor, paramConnection);
      }
      else if (m == 1)
      {
        int n = paramPickleContext.readByte();
        typedescriptor = Kotad.unpickleTypeDescriptorImage(paramPickleContext);
      }
      else
      {
        sqlexception = DatabaseError.createSqlException(null, 178);
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }

      ((TypeDescriptor)typedescriptor).setTransient(true);
    }

    if (((TypeDescriptor)typedescriptor).isTransient())
    {
      m = paramPickleContext.offset();
      localObject4 = new byte[m - k];
      System.arraycopy(arrayOfByte2, k, localObject4, 0, localObject4.length);
      typedescriptor.setTransientImage(localObject4);
    }

    return typedescriptor;
  }

  void setTransientImage(byte[] paramArrayOfByte)
  {
    this.transientImage = paramArrayOfByte;
  }

  void setTransient(boolean paramBoolean)
  {
    this.isTransient = paramBoolean;
  }

  public boolean isTransient()
  {
    return this.isTransient;
  }

  int getOpaqueImageTypeSize()
  {
    int i = 0;
    if (this.isTransient) {
      i = this.transientImage.length;
    }
    else {
      i = 5;
      if ((this.toid != null) && (this.toid.length == 16))
        i = 23;
    }
    return i;
  }

  int pickleOpaqueTypeImage(byte[] paramArrayOfByte, int paramInt, boolean paramBoolean)
  {
    if (this.isTransient)
    {
      System.arraycopy(this.transientImage, 0, paramArrayOfByte, paramInt, this.transientImage.length);
      paramInt += this.transientImage.length;
    }
    else
    {
      int i = 0;
      if ((this.toid != null) && (this.toid.length == 16))
        i = 1;
      paramArrayOfByte[(paramInt++)] = 1;
      int j = this.internalTypeCode;
      if (paramBoolean)
        j = 110;
      int k = 512;
      if ((j != 108) && (j != 122))
      {
        k |= 2;
      }if ((i != 0) && (j != 110))
        k |= 4;
      paramArrayOfByte[(paramInt++)] = ((byte)((k & 0xFF00) >> 8 & 0xFF));
      paramArrayOfByte[(paramInt++)] = ((byte)(k & 0xFF));
      paramArrayOfByte[(paramInt++)] = ((byte)((j & 0xFF00) >> 8 & 0xFF));
      paramArrayOfByte[(paramInt++)] = ((byte)(j & 0xFF));
      if (i != 0)
      {
        System.arraycopy(this.toid, 0, paramArrayOfByte, paramInt, this.toid.length);
        paramInt += this.toid.length;
        paramArrayOfByte[(paramInt++)] = ((byte)((this.toidVersion & 0xFF00) >> 8 & 0xFF));
        paramArrayOfByte[(paramInt++)] = ((byte)(this.toidVersion & 0xFF));
      }
    }
    return paramInt;
  }

  public void setPrecision(long paramLong)
  {
    this.precision = paramLong;
  }

  public long getPrecision()
  {
    return this.precision;
  }

  public void setScale(byte paramByte)
  {
    this.scale = paramByte;
  }

  public byte getScale()
  {
    return this.scale;
  }

  public boolean isInHierarchyOf(String paramString)
    throws SQLException
  {
    return false;
  }

  private void writeObject(ObjectOutputStream paramObjectOutputStream)
    throws IOException
  {
    try
    {
      if (this.sqlName == null) {
        initSQLName();
      }
    }
    catch (SQLException localSQLException)
    {
      throw new IOException(localSQLException.getMessage());
    }

    paramObjectOutputStream.writeObject(this.sqlName);
    paramObjectOutputStream.writeObject(this.pickler);
  }

  private void readObject(ObjectInputStream paramObjectInputStream)
    throws IOException, ClassNotFoundException
  {
    this.sqlName = ((SQLName)paramObjectInputStream.readObject());
    this.pickler = ((OracleNamedType)paramObjectInputStream.readObject());
  }

  public void setConnection(Connection paramConnection)
    throws SQLException
  {
    setPhysicalConnectionOf(paramConnection);
    this.pickler.setConnection(getInternalConnection());
  }

  public static String getSubtypeName(oracle.jdbc.OracleConnection paramOracleConnection, byte[] paramArrayOfByte, long paramLong)
    throws SQLException
  {
    if ((paramArrayOfByte == null) || (paramArrayOfByte.length == 0) || (paramOracleConnection == null))
    {
      SQLException sqlexception = DatabaseError.createSqlException(null, 68, " 'image' should not be empty and 'conn' should not be null. ");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    String str = OracleTypeADT.getSubtypeName(paramOracleConnection, paramArrayOfByte, paramLong);

    return str;
  }

  public void initMetadataRecursively()
    throws SQLException
  {
    if (this.pickler != null)
      this.pickler.initMetadataRecursively();
  }

  public void initNamesRecursively()
    throws SQLException
  {
    if (this.pickler != null)
      this.pickler.initNamesRecursively();
  }

  public void fixupConnection(oracle.jdbc.internal.OracleConnection paramOracleConnection)
    throws SQLException
  {
    if (this.connection == null) this.connection = paramOracleConnection;
    if (this.pickler != null) this.pickler.fixupConnection(paramOracleConnection);
  }

  public String toXMLString()
    throws SQLException
  {
    return toXMLString(false);
  }

  public String toXMLString(boolean paramBoolean)
    throws SQLException
  {
    StringWriter localStringWriter = new StringWriter();
    PrintWriter localPrintWriter = new PrintWriter(localStringWriter);
    printXMLHeader(localPrintWriter);
    printXML(localPrintWriter, 0, paramBoolean);
    return localStringWriter.getBuffer().substring(0);
  }

  public void printXML(PrintStream paramPrintStream)
    throws SQLException
  {
    printXML(paramPrintStream, false);
  }

  public void printXML(PrintStream paramPrintStream, boolean paramBoolean)
    throws SQLException
  {
    PrintWriter localPrintWriter = new PrintWriter(paramPrintStream, true);
    printXMLHeader(localPrintWriter);
    printXML(localPrintWriter, 0, paramBoolean);
  }

  void printXML(PrintWriter paramPrintWriter, int paramInt, boolean paramBoolean)
    throws SQLException
  {
    String str = tagName();
    paramPrintWriter.println("<" + str + " sqlName=\"" + this.sqlName + "\" >");
    if (this.pickler != null) this.pickler.printXML(paramPrintWriter, paramInt + 1, paramBoolean);
    paramPrintWriter.println("</" + str + ">");
  }

  String tagName()
  {
    return "TypeDescriptor";
  }

  void printXMLHeader(PrintWriter paramPrintWriter)
    throws SQLException
  {
    paramPrintWriter.println("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
  }

  protected oracle.jdbc.internal.OracleConnection getConnectionDuringExceptionHandling()
  {
    return this.connection;
  }

  static
  {
    OID_TO_TYPECODE[8] = 12;
    OID_TO_TYPECODE[9] = 27;
    OID_TO_TYPECODE[10] = 28;
    OID_TO_TYPECODE[11] = 29;
    OID_TO_TYPECODE[12] = 21;
    OID_TO_TYPECODE[13] = 22;
    OID_TO_TYPECODE[14] = 4;
    OID_TO_TYPECODE[15] = 2;
    OID_TO_TYPECODE[16] = 7;
    OID_TO_TYPECODE[17] = 23;
    OID_TO_TYPECODE[18] = 25;
    OID_TO_TYPECODE[19] = 26;
    OID_TO_TYPECODE[20] = 245;
    OID_TO_TYPECODE[21] = 246;
    OID_TO_TYPECODE[22] = 3;

    OID_TO_TYPECODE[23] = 95;
    OID_TO_TYPECODE[24] = 32;
    OID_TO_TYPECODE[25] = 9;
    OID_TO_TYPECODE[26] = 96;
    OID_TO_TYPECODE[27] = 1;
    OID_TO_TYPECODE[28] = 105;
    OID_TO_TYPECODE[29] = 247;
    OID_TO_TYPECODE[30] = 248;
    OID_TO_TYPECODE[31] = 108;
    OID_TO_TYPECODE[32] = 0;
    OID_TO_TYPECODE[33] = 0;
    OID_TO_TYPECODE[34] = 112;
    OID_TO_TYPECODE[35] = 113;
    OID_TO_TYPECODE[36] = 115;
    OID_TO_TYPECODE[37] = 114;

    OID_TO_TYPECODE[38] = 0;
    OID_TO_TYPECODE[39] = 0;
    OID_TO_TYPECODE[40] = 0;
    OID_TO_TYPECODE[41] = 0;
    OID_TO_TYPECODE[42] = 0;
    OID_TO_TYPECODE[43] = 0;
    OID_TO_TYPECODE[44] = 0;
    OID_TO_TYPECODE[45] = 0;

    OID_TO_TYPECODE[46] = 0;
    OID_TO_TYPECODE[47] = 0;
    OID_TO_TYPECODE[48] = 0;
    OID_TO_TYPECODE[49] = 0;
    OID_TO_TYPECODE[50] = 0;
    OID_TO_TYPECODE[51] = 0;
    OID_TO_TYPECODE[52] = 0;
    OID_TO_TYPECODE[53] = 0;
    OID_TO_TYPECODE[54] = 0;
    OID_TO_TYPECODE[55] = 0;
    OID_TO_TYPECODE[56] = 0;

    OID_TO_TYPECODE[57] = 0;

    OID_TO_TYPECODE[58] = 58;

    OID_TO_TYPECODE[59] = 185;
    OID_TO_TYPECODE[60] = 186;
    OID_TO_TYPECODE[61] = 187;
    OID_TO_TYPECODE[62] = 188;
    OID_TO_TYPECODE[63] = 189;
    OID_TO_TYPECODE[64] = 190;
    OID_TO_TYPECODE[65] = 232;
    OID_TO_TYPECODE[66] = 0;
    OID_TO_TYPECODE[67] = 0;
    OID_TO_TYPECODE[68] = 100;
    OID_TO_TYPECODE[69] = 101;
    OID_TO_TYPECODE[70] = 104;

    typeCodeTypeNameMap = null;
    try
    {
      getTypeCodeTypeNameMap();
    }
    catch (Exception localException)
    {
    }
  }
}