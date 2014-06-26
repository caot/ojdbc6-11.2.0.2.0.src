package oracle.jdbc.oracore;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLData;
import java.sql.SQLException;
import java.util.Map;
import java.util.Vector;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.driver.DatabaseError;
import oracle.jdbc.internal.ObjectData;
import oracle.jdbc.internal.OracleConnection;
import oracle.sql.AttributeDescriptor;
import oracle.sql.BLOB;
import oracle.sql.Datum;
import oracle.sql.JAVA_STRUCT;
import oracle.sql.NUMBER;
import oracle.sql.SQLName;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;
import oracle.sql.TypeDescriptor;

public class OracleTypeADT extends OracleNamedType
  implements Serializable
{
  static final long serialVersionUID = 3031304012507165702L;
  static final int S_TOP = 1;
  static final int S_EMBEDDED = 2;
  static final int S_UPT_ADT = 4;
  static final int S_JAVA_OBJECT = 16;
  static final int S_FINAL_TYPE = 32;
  static final int S_SUB_TYPE = 64;
  static final int S_ATTR_TDS = 128;
  static final int S_HAS_METADATA = 256;
  static final int S_TDS_PARSED = 512;
  private int statusBits = 1;

  int tdsVersion = -9999;
  static final int KOPT_V80 = 1;
  static final int KOPT_V81 = 2;
  static final int KOPT_VNFT = 3;
  static final int KOPT_VERSION = 3;
  boolean endOfAdt = false;

  int typeVersion = 1;

  long fixedDataSize = -1L;
  int alignmentRequirement = -1;

  OracleType[] attrTypes = null;
  String[] attrNames;
  String[] attrTypeNames;
  public long tdoCState = 0L;

  byte[] toid = null;
  int charSetId;
  int charSetForm;
  int flattenedAttrNum;
  transient int opcode;
  transient int idx = 1;

  boolean isTransient = false;
  static final int CURRENT_USER_OBJECT = 0;
  static final int CURRENT_USER_SYNONYM = 1;
  static final int CURRENT_USER_SYNONYM_10g = 2;
  static final int CURRENT_USER_PUBLIC_SYNONYM = 3;
  static final int CURRENT_USER_PUBLIC_SYNONYM_10g = 4;
  static final int POSSIBLY_OTHER_USER_OBJECT = 5;
  static final int POSSIBLY_OTHER_USER_OBJECT_10g = 6;
  static final int OTHER_USER_OBJECT = 7;
  static final int OTHER_USER_SYNONYM = 8;
  static final int PUBLIC_SYNONYM = 9;
  static final int PUBLIC_SYNONYM_10g = 10;
  static final int BREAK = 11;
  static final String[] sqlString = { "SELECT ATTR_NO, ATTR_NAME, ATTR_TYPE_NAME, ATTR_TYPE_OWNER FROM USER_TYPE_ATTRS WHERE TYPE_NAME = :1 ORDER BY ATTR_NO", "SELECT ATTR_NO, ATTR_NAME, ATTR_TYPE_NAME, ATTR_TYPE_OWNER FROM USER_TYPE_ATTRS WHERE TYPE_NAME in (SELECT TABLE_NAME FROM USER_SYNONYMS START WITH SYNONYM_NAME = :1 CONNECT BY PRIOR TABLE_NAME = SYNONYM_NAME UNION SELECT :1 FROM DUAL) ORDER BY ATTR_NO", "SELECT ATTR_NO, ATTR_NAME, ATTR_TYPE_NAME, ATTR_TYPE_OWNER FROM USER_TYPE_ATTRS WHERE TYPE_NAME in (SELECT TABLE_NAME FROM USER_SYNONYMS START WITH SYNONYM_NAME = :1 CONNECT BY NOCYCLE PRIOR TABLE_NAME = SYNONYM_NAME UNION SELECT :1 FROM DUAL) ORDER BY ATTR_NO", "SELECT /*+RULE*/ATTR_NO, ATTR_NAME, ATTR_TYPE_NAME, ATTR_TYPE_OWNER FROM USER_TYPE_ATTRS WHERE TYPE_NAME IN (SELECT TABLE_NAME FROM ALL_SYNONYMS START WITH SYNONYM_NAME = :1 AND  OWNER = 'PUBLIC' CONNECT BY PRIOR TABLE_NAME = SYNONYM_NAME AND TABLE_OWNER = OWNER UNION SELECT :2  FROM DUAL) ORDER BY ATTR_NO", "SELECT /*+RULE*/ATTR_NO, ATTR_NAME, ATTR_TYPE_NAME, ATTR_TYPE_OWNER FROM USER_TYPE_ATTRS WHERE TYPE_NAME IN (SELECT TABLE_NAME FROM ALL_SYNONYMS START WITH SYNONYM_NAME = :1 AND  OWNER = 'PUBLIC' CONNECT BY NOCYCLE PRIOR TABLE_NAME = SYNONYM_NAME AND TABLE_OWNER = OWNER UNION SELECT :2  FROM DUAL) ORDER BY ATTR_NO", "SELECT ATTR_NO, ATTR_NAME, ATTR_TYPE_NAME, ATTR_TYPE_OWNER FROM ALL_TYPE_ATTRS WHERE TYPE_NAME IN (SELECT TABLE_NAME FROM USER_SYNONYMS START WITH SYNONYM_NAME = :tname CONNECT BY PRIOR TABLE_NAME = SYNONYM_NAME UNION SELECT :tname FROM DUAL) ORDER BY ATTR_NO", "SELECT ATTR_NO, ATTR_NAME, ATTR_TYPE_NAME, ATTR_TYPE_OWNER FROM ALL_TYPE_ATTRS WHERE TYPE_NAME IN (SELECT TABLE_NAME FROM USER_SYNONYMS START WITH SYNONYM_NAME = :tname CONNECT BY NOCYCLE PRIOR TABLE_NAME = SYNONYM_NAME UNION SELECT :tname FROM DUAL) ORDER BY ATTR_NO", "SELECT ATTR_NO, ATTR_NAME, ATTR_TYPE_NAME, ATTR_TYPE_OWNER FROM ALL_TYPE_ATTRS WHERE OWNER = :1 AND TYPE_NAME = :2 ORDER BY ATTR_NO", "SELECT ATTR_NO, ATTR_NAME, ATTR_TYPE_NAME, ATTR_TYPE_OWNER FROM ALL_TYPE_ATTRS WHERE OWNER = (SELECT TABLE_OWNER FROM ALL_SYNONYMS WHERE SYNONYM_NAME=:1) AND TYPE_NAME = (SELECT TABLE_NAME FROM ALL_SYNONYMS WHERE SYNONYM_NAME=:2) ORDER BY ATTR_NO", "DECLARE /*+RULE*/  the_owner VARCHAR2(100);   the_type  VARCHAR2(100); begin  SELECT TABLE_NAME, TABLE_OWNER INTO THE_TYPE, THE_OWNER  FROM ALL_SYNONYMS  WHERE TABLE_NAME IN (SELECT TYPE_NAME FROM ALL_TYPES)  START WITH SYNONYM_NAME = :1 AND OWNER = 'PUBLIC'  CONNECT BY PRIOR TABLE_NAME = SYNONYM_NAME AND TABLE_OWNER = OWNER; OPEN :2 FOR SELECT ATTR_NO, ATTR_NAME, ATTR_TYPE_NAME,  ATTR_TYPE_OWNER FROM ALL_TYPE_ATTRS  WHERE TYPE_NAME = THE_TYPE and OWNER = THE_OWNER; END;", "DECLARE /*+RULE*/  the_owner VARCHAR2(100);   the_type  VARCHAR2(100); begin  SELECT TABLE_NAME, TABLE_OWNER INTO THE_TYPE, THE_OWNER  FROM ALL_SYNONYMS  WHERE TABLE_NAME IN (SELECT TYPE_NAME FROM ALL_TYPES)  START WITH SYNONYM_NAME = :1 AND OWNER = 'PUBLIC'  CONNECT BY NOCYCLE PRIOR TABLE_NAME = SYNONYM_NAME AND TABLE_OWNER = OWNER; OPEN :2 FOR SELECT ATTR_NO, ATTR_NAME, ATTR_TYPE_NAME,  ATTR_TYPE_OWNER FROM ALL_TYPE_ATTRS  WHERE TYPE_NAME = THE_TYPE and OWNER = THE_OWNER; END;" };
  static final int TDS_SIZE = 4;
  static final int TDS_NUMBER = 1;
  static final int KOPM_OTS_SQL_CHAR = 1;
  static final int KOPM_OTS_DATE = 2;
  static final int KOPM_OTS_DECIMAL = 3;
  static final int KOPM_OTS_DOUBLE = 4;
  static final int KOPM_OTS_FLOAT = 5;
  static final int KOPM_OTS_NUMBER = 6;
  static final int KOPM_OTS_SQL_VARCHAR2 = 7;
  static final int KOPM_OTS_SINT32 = 8;
  static final int KOPM_OTS_REF = 9;
  static final int KOPM_OTS_VARRAY = 10;
  static final int KOPM_OTS_UINT8 = 11;
  static final int KOPM_OTS_SINT8 = 12;
  static final int KOPM_OTS_UINT16 = 13;
  static final int KOPM_OTS_UINT32 = 14;
  static final int KOPM_OTS_LOB = 15;
  static final int KOPM_OTS_CANONICAL = 17;
  static final int KOPM_OTS_OCTET = 18;
  static final int KOPM_OTS_RAW = 19;
  static final int KOPM_OTS_ROWID = 20;
  static final int KOPM_OTS_STAMP = 21;
  static final int KOPM_OTS_TZSTAMP = 23;
  static final int KOPM_OTS_INTERVAL = 24;
  static final int KOPM_OTS_PTR = 25;
  static final int KOPM_OTS_SINT16 = 26;
  static final int KOPM_OTS_UPT = 27;
  static final int KOPM_OTS_COLLECTION = 28;
  static final int KOPM_OTS_CLOB = 29;
  static final int KOPM_OTS_BLOB = 30;
  static final int KOPM_OTS_BFILE = 31;
  static final int KOPM_OTS_BINARY_INTEGE = 32;
  static final int KOPM_OTS_IMPTZSTAMP = 33;
  static final int KOPM_OTS_BFLOAT = 37;
  static final int KOPM_OTS_BDOUBLE = 45;
  static final int KOTTCOPQ = 58;
  static final int KOPT_OP_STARTEMBADT = 39;
  static final int KOPT_OP_ENDEMBADT = 40;
  static final int KOPT_OP_STARTADT = 41;
  static final int KOPT_OP_ENDADT = 42;
  static final int KOPT_OP_SUBTYPE_MARKER = 43;
  static final int KOPT_OP_EMBADT_INFO = 44;
  static final int KOPT_OPCODE_START = 38;
  static final int KOPT_OP_VERSION = 38;
  static final int REGULAR_PATCH = 0;
  static final int SIMPLE_PATCH = 1;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  protected OracleTypeADT()
  {
  }

  public OracleTypeADT(byte[] paramArrayOfByte, int paramInt1, int paramInt2, short paramShort, String paramString)
    throws SQLException
  {
    this(paramString, (OracleConnection)null);

    this.toid = paramArrayOfByte;
    this.typeVersion = paramInt1;
    this.charSetId = paramInt2;
    this.charSetForm = paramShort;
  }

  public OracleTypeADT(String paramString, Connection paramConnection)
    throws SQLException
  {
    super(paramString, (OracleConnection)paramConnection);
  }

  public OracleTypeADT(OracleTypeADT paramOracleTypeADT, int paramInt, Connection paramConnection)
    throws SQLException
  {
    super(paramOracleTypeADT, paramInt, (OracleConnection)paramConnection);
  }

  public OracleTypeADT(SQLName paramSQLName, byte[] paramArrayOfByte1, int paramInt, byte[] paramArrayOfByte2, OracleConnection paramOracleConnection)
    throws SQLException
  {
    this.sqlName = paramSQLName;
    init(paramArrayOfByte2, paramOracleConnection);
    this.toid = paramArrayOfByte1;
    this.typeVersion = paramInt;
  }

  public OracleTypeADT(AttributeDescriptor[] paramArrayOfAttributeDescriptor, OracleConnection paramOracleConnection)
    throws SQLException
  {
    setConnectionInternal(paramOracleConnection);
    this.isTransient = true;
    this.flattenedAttrNum = paramArrayOfAttributeDescriptor.length;
    this.attrTypes = new OracleType[this.flattenedAttrNum];
    this.attrNames = new String[this.flattenedAttrNum];
    for (int i = 0; i < this.flattenedAttrNum; i++)
      this.attrNames[i] = paramArrayOfAttributeDescriptor[i].getAttributeName();
    this.statusBits |= 256;

    for (int i = 0; i < this.flattenedAttrNum; i++)
    {
      TypeDescriptor localTypeDescriptor = paramArrayOfAttributeDescriptor[i].getTypeDescriptor();
      switch (localTypeDescriptor.getInternalTypeCode())
      {
      case 12:
        this.attrTypes[i] = new OracleTypeDATE();
        break;
      case 9:
        this.attrTypes[i] = new OracleTypeCHAR(this.connection, 12);
        ((OracleTypeCHAR)this.attrTypes[i]).length = ((int)localTypeDescriptor.getPrecision());
        ((OracleTypeCHAR)this.attrTypes[i]).form = 1;
        break;
      case 96:
        this.attrTypes[i] = new OracleTypeCHAR(this.connection, 1);
        ((OracleTypeCHAR)this.attrTypes[i]).length = ((int)localTypeDescriptor.getPrecision());
        ((OracleTypeCHAR)this.attrTypes[i]).form = 1;
        break;
      case 108:
        this.attrTypes[i] = ((OracleTypeADT)localTypeDescriptor.getPickler());
        ((OracleTypeADT)this.attrTypes[i]).setEmbeddedADT();
        break;
      case 2:
        this.attrTypes[i] = new OracleTypeNUMBER(2);
        ((OracleTypeNUMBER)this.attrTypes[i]).precision = ((int)localTypeDescriptor.getPrecision());
        ((OracleTypeNUMBER)this.attrTypes[i]).scale = localTypeDescriptor.getScale();
        break;
      case 7:
        this.attrTypes[i] = new OracleTypeNUMBER(3);
        ((OracleTypeNUMBER)this.attrTypes[i]).precision = ((int)localTypeDescriptor.getPrecision());
        ((OracleTypeNUMBER)this.attrTypes[i]).scale = localTypeDescriptor.getScale();
        break;
      case 22:
        this.attrTypes[i] = new OracleTypeNUMBER(8);
        ((OracleTypeNUMBER)this.attrTypes[i]).precision = ((int)localTypeDescriptor.getPrecision());
        ((OracleTypeNUMBER)this.attrTypes[i]).scale = localTypeDescriptor.getScale();
        break;
      case 4:
        this.attrTypes[i] = new OracleTypeFLOAT();
        ((OracleTypeFLOAT)this.attrTypes[i]).precision = ((int)localTypeDescriptor.getPrecision());
        break;
      case 100:
        this.attrTypes[i] = new OracleTypeBINARY_FLOAT();
        break;
      case 101:
        this.attrTypes[i] = new OracleTypeBINARY_DOUBLE();
        break;
      case 29:
        this.attrTypes[i] = new OracleTypeSINT32();
        break;
      case 110:
        this.attrTypes[i] = new OracleTypeREF(this, i, this.connection);
        break;
      case 114:
        this.attrTypes[i] = new OracleTypeBFILE(this.connection);
        break;
      case 95:
        this.attrTypes[i] = new OracleTypeRAW();
        break;
      case 112:
        this.attrTypes[i] = new OracleTypeCLOB(this.connection);
        break;
      case 113:
        this.attrTypes[i] = new OracleTypeBLOB(this.connection);
        break;
      case 187:
        this.attrTypes[i] = new OracleTypeTIMESTAMP(this.connection);
        ((OracleTypeTIMESTAMP)this.attrTypes[i]).precision = ((int)localTypeDescriptor.getPrecision());
        break;
      case 188:
        this.attrTypes[i] = new OracleTypeTIMESTAMPTZ(this.connection);
        ((OracleTypeTIMESTAMPTZ)this.attrTypes[i]).precision = ((int)localTypeDescriptor.getPrecision());
        break;
      case 232:
        this.attrTypes[i] = new OracleTypeTIMESTAMPLTZ(this.connection);
        ((OracleTypeTIMESTAMPLTZ)this.attrTypes[i]).precision = ((int)localTypeDescriptor.getPrecision());
        break;
      case 189:
        this.attrTypes[i] = new OracleTypeINTERVAL(this.connection);
        ((OracleTypeINTERVAL)this.attrTypes[i]).typeId = 7;
        ((OracleTypeINTERVAL)this.attrTypes[i]).precision = ((int)localTypeDescriptor.getPrecision());
        ((OracleTypeINTERVAL)this.attrTypes[i]).scale = localTypeDescriptor.getScale();
        break;
      case 190:
        this.attrTypes[i] = new OracleTypeINTERVAL(this.connection);
        ((OracleTypeINTERVAL)this.attrTypes[i]).typeId = 10;
        ((OracleTypeINTERVAL)this.attrTypes[i]).precision = ((int)localTypeDescriptor.getPrecision());
        ((OracleTypeINTERVAL)this.attrTypes[i]).scale = localTypeDescriptor.getScale();
        break;
      case 122:
        this.attrTypes[i] = new OracleTypeCOLLECTION(this, i, this.connection);
        break;
      default:
        SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 48, "type: " + localTypeDescriptor.getInternalTypeCode());
        localSQLException.fillInStackTrace();
        throw localSQLException;
      }
    }
  }

  public Datum toDatum(Object paramObject, OracleConnection paramOracleConnection)
    throws SQLException
  {
    if (paramObject != null)
    {
      if ((paramObject instanceof STRUCT))
      {
        return (STRUCT)paramObject;
      }
      if (((paramObject instanceof SQLData)) || ((paramObject instanceof ObjectData)))
      {
        return STRUCT.toSTRUCT(paramObject, paramOracleConnection);
      }
      if ((paramObject instanceof Object[]))
      {
        StructDescriptor localObject = createStructDescriptor();
        STRUCT localSTRUCT = createObjSTRUCT(localObject, (Object[])paramObject);
        return localSTRUCT;
      }

      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 59, paramObject);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    return null;
  }

  public Datum[] toDatumArray(Object paramObject, OracleConnection paramOracleConnection, long paramLong, int paramInt)
    throws SQLException
  {
    Datum[] arrayOfDatum = null;

    if (paramObject != null)
    {
      Object[] localObject;
      if ((paramObject instanceof Object[]))
      {
        localObject = (Object[])paramObject;

        int i = (int)(paramInt == -1 ? localObject.length : Math.min(localObject.length - paramLong + 1L, paramInt));

        arrayOfDatum = new Datum[i];

        for (int j = 0; j < i; j++)
          arrayOfDatum[j] = toDatum(localObject[((int)paramLong + j - 1)], paramOracleConnection);
      }
      else
      {
        SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 59, paramObject);
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }
    }

    return arrayOfDatum;
  }

  public int getTypeCode()
    throws SQLException
  {
    if ((getStatus() & 0x10) != 0) {
      return 2008;
    }
    return 2002;
  }

  public OracleType[] getAttrTypes()
    throws SQLException
  {
    if (this.attrTypes == null) {
      init(this.connection);
    }
    return this.attrTypes;
  }

  public boolean isInHierarchyOf(OracleType paramOracleType)
    throws SQLException
  {
    if (paramOracleType == null) {
      return false;
    }
    if (!paramOracleType.isObjectType()) {
      return false;
    }
    StructDescriptor localStructDescriptor = (StructDescriptor)paramOracleType.getTypeDescriptor();

    return this.descriptor.isInHierarchyOf(localStructDescriptor.getName());
  }

  public boolean isInHierarchyOf(StructDescriptor paramStructDescriptor)
    throws SQLException
  {
    if (paramStructDescriptor == null) {
      return false;
    }
    return this.descriptor.isInHierarchyOf(paramStructDescriptor.getName());
  }

  public boolean isObjectType()
  {
    return true;
  }

  public TypeDescriptor getTypeDescriptor()
  {
    return this.descriptor;
  }

  public void init(OracleConnection paramOracleConnection)
    throws SQLException
  {
    synchronized (paramOracleConnection)
    {
      byte[] arrayOfByte = initMetadata(paramOracleConnection);
      init(arrayOfByte, paramOracleConnection);
    }
  }

  public void init(byte[] paramArrayOfByte, OracleConnection paramOracleConnection)
    throws SQLException
  {
    synchronized (paramOracleConnection)
    {
      this.statusBits = 1;
      this.connection = paramOracleConnection;

      if (paramArrayOfByte != null) parseTDS(paramArrayOfByte, 0L);
      setStatusBits(256);
    }
  }

  public byte[] initMetadata(OracleConnection paramOracleConnection)
    throws SQLException
  {
    synchronized (this.connection)
    {
      byte[] arrayOfByte = null;
      if ((this.statusBits & 0x100) != 0) return null;

      if (this.sqlName == null) getFullName();

      if ((this.statusBits & 0x100) == 0)
      {
        CallableStatement localCallableStatement = null;
        try
        {
          if (this.tdoCState == 0L) this.tdoCState = this.connection.getTdoCState(this.sqlName.getSchema(), this.sqlName.getSimpleName());

          String str = "begin :1 := dbms_pickler.get_type_shape(:2,:3,:4,:5,:6,:7); end;";

          int i = 0;
          localCallableStatement = this.connection.prepareCall(str);

          localCallableStatement.registerOutParameter(1, 2);
          localCallableStatement.registerOutParameter(4, -4);
          localCallableStatement.registerOutParameter(5, 4);
          localCallableStatement.registerOutParameter(6, -4);
          localCallableStatement.registerOutParameter(7, -4);
          localCallableStatement.setString(2, this.sqlName.getSchema());
          localCallableStatement.setString(3, this.sqlName.getSimpleName());

          localCallableStatement.execute();

          int j = localCallableStatement.getInt(1);
          Object localObject1;
          if (j != 0)
          {
            if (j != 24331)
            {
              SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 74, this.sqlName.toString());
              sqlexception.fillInStackTrace();
              throw sqlexception;
            }
            if (j == 24331)
            {
              i = 1;
              localCallableStatement.registerOutParameter(6, 2004);

              localCallableStatement.execute();

              j = localCallableStatement.getInt(1);
              if (j != 0)
              {
                SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 74, this.sqlName.toString());
                sqlexception.fillInStackTrace();
                throw sqlexception;
              }

            }

          }

          this.toid = localCallableStatement.getBytes(4);
          this.typeVersion = NUMBER.toInt(localCallableStatement.getBytes(5));
          if (i == 0)
          {
            arrayOfByte = localCallableStatement.getBytes(6);
          }
          else
          {
            try
            {
              Blob localBlob = ((OracleCallableStatement)localCallableStatement).getBlob(6);
              localObject1 = localBlob.getBinaryStream();
              arrayOfByte = new byte[(int)localBlob.length()];
              ((InputStream)localObject1).read(arrayOfByte);
              ((InputStream)localObject1).close();
              ((BLOB)localBlob).freeTemporary();
            }
            catch (IOException localIOException)
            {
              SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localIOException);
              localSQLException.fillInStackTrace();
              throw localSQLException;
            }
          }

          this.metaDataInitialized = true;

          this.flattenedAttrNum = (Util.getUnsignedByte(arrayOfByte[8]) * 256 + Util.getUnsignedByte(arrayOfByte[9]));

          localCallableStatement.getBytes(7);
        }
        finally
        {
          if (localCallableStatement != null) localCallableStatement.close();
        }
      }
      setStatusBits(256);
      return arrayOfByte;
    }
  }

  TDSReader parseTDS(byte[] paramArrayOfByte, long paramLong)
    throws SQLException
  {
    if (this.attrTypes != null) {
      return null;
    }

    TDSReader localTDSReader = new TDSReader(paramArrayOfByte, paramLong);

    long l1 = localTDSReader.readLong() + localTDSReader.offset();

    localTDSReader.checkNextByte((byte)38);

    this.tdsVersion = localTDSReader.readByte();

    localTDSReader.skipBytes(2);

    this.flattenedAttrNum = localTDSReader.readUB2();

    if ((localTDSReader.readByte() & 0xFF) == 255) {
      setStatusBits(128);
    }

    long l2 = localTDSReader.offset();

    localTDSReader.checkNextByte((byte)41);

    if (localTDSReader.readUB2() != 0)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 47, "parseTDS");
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    long l3 = localTDSReader.readLong();

    parseTDSrec(localTDSReader);

    if (this.tdsVersion >= 3)
    {
      localTDSReader.skip_to(l2 + l3 + 2L);

      localTDSReader.skipBytes(2 * this.flattenedAttrNum);

      byte b = localTDSReader.readByte();

      if (localTDSReader.isJavaObject(this.tdsVersion, b)) {
        setStatusBits(16);
      }

      if (localTDSReader.isFinalType(this.tdsVersion, b)) {
        setStatusBits(32);
      }

      if (localTDSReader.readByte() != 1)
        setStatusBits(64);
    }
    else
    {
      setStatusBits(32);
    }

    localTDSReader.skip_to(l1);
    return localTDSReader;
  }

  public void parseTDSrec(TDSReader paramTDSReader)
    throws SQLException
  {
    Vector localVector = new Vector(5);
    OracleType localOracleType = null;

    this.idx = 1;

    while ((localOracleType = getNextTypeObject(paramTDSReader)) != null)
    {
      localVector.addElement(localOracleType);
    }

    if (this.opcode == 42)
    {
      this.endOfAdt = true;

      applyTDSpatches(paramTDSReader);
    }

    this.attrTypes = new OracleType[localVector.size()];

    localVector.copyInto(this.attrTypes);
  }

  private void applyTDSpatches(TDSReader paramTDSReader)
    throws SQLException
  {
    TDSPatch localTDSPatch = paramTDSReader.getNextPatch();

    while (localTDSPatch != null)
    {
      paramTDSReader.moveToPatchPos(localTDSPatch);

      int i = localTDSPatch.getType();

      if (i == 0)
      {
        paramTDSReader.readByte();

        int j = localTDSPatch.getUptTypeCode();
        Object localObject2;
        Object localObject3;
        switch (j)
        {
        case -6:
          paramTDSReader.readLong();
        case -5:
          localObject2 = localTDSPatch.getOwner();
          localObject3 = null;

          if (((OracleNamedType)localObject2).hasName())
          {
            localObject3 = new OracleTypeADT(((OracleNamedType)localObject2).getFullName(), this.connection);
          }
          else
          {
            localObject3 = new OracleTypeADT(((OracleNamedType)localObject2).getParent(), ((OracleNamedType)localObject2).getOrder(), this.connection);
          }

          ((OracleTypeADT)localObject3).setUptADT();
          TDSReader localTDSReader = ((OracleTypeADT)localObject3).parseTDS(paramTDSReader.tds(), paramTDSReader.absoluteOffset());

          paramTDSReader.skipBytes((int)localTDSReader.offset());
          localTDSPatch.apply(((OracleTypeADT)localObject3).cleanup());

          break;
        case 58:
          localObject2 = localTDSPatch.getOwner();
          localObject3 = null;

          if (((OracleNamedType)localObject2).hasName())
          {
            localObject3 = new OracleTypeOPAQUE(((OracleNamedType)localObject2).getFullName(), this.connection);
          }
          else
          {
            localObject3 = new OracleTypeOPAQUE(((OracleNamedType)localObject2).getParent(), ((OracleNamedType)localObject2).getOrder(), this.connection);
          }

          ((OracleTypeOPAQUE)localObject3).parseTDSrec(paramTDSReader);
          localTDSPatch.apply((OracleType)localObject3);

          break;
        default:
          SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 1);
          sqlexception.fillInStackTrace();
          throw sqlexception;
        }
      }
      else
      {
        Object localObject1;
        if (i == 1)
        {
          localObject1 = getNextTypeObject(paramTDSReader);

          localTDSPatch.apply((OracleType)localObject1, this.opcode);
        }
        else
        {
          SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 47, "parseTDS");
          sqlexception.fillInStackTrace();
          throw sqlexception;
        }
      }
      localTDSPatch = paramTDSReader.getNextPatch();
    }
  }

  public OracleNamedType cleanup()
  {
    synchronized (this.connection)
    {
      OracleNamedType localObject1;
      if ((this.attrTypes.length == 1) && ((this.attrTypes[0] instanceof OracleTypeCOLLECTION)))
      {
        localObject1 = (OracleTypeCOLLECTION)this.attrTypes[0];

        ((OracleTypeCOLLECTION)localObject1).copy_properties(this);

        return localObject1;
      }
      if ((this.attrTypes.length == 1) && ((this.statusBits & 0x80) != 0) && ((this.attrTypes[0] instanceof OracleTypeUPT)) && ((((OracleTypeUPT)this.attrTypes[0]).realType instanceof OracleTypeOPAQUE)))
      {
        localObject1 = (OracleTypeOPAQUE)((OracleTypeUPT)this.attrTypes[0]).realType;

        ((OracleTypeOPAQUE)localObject1).copy_properties(this);

        return localObject1;
      }

      return this;
    }
  }

  void copy_properties(OracleTypeADT paramOracleTypeADT)
  {
    this.sqlName = paramOracleTypeADT.sqlName;
    this.parent = paramOracleTypeADT.parent;
    this.idx = paramOracleTypeADT.idx;
    this.connection = paramOracleTypeADT.connection;
    this.toid = paramOracleTypeADT.toid;
    this.tdsVersion = paramOracleTypeADT.tdsVersion;
    this.typeVersion = paramOracleTypeADT.typeVersion;
    this.tdoCState = paramOracleTypeADT.tdoCState;
    this.endOfAdt = paramOracleTypeADT.endOfAdt;
  }

  OracleType getNextTypeObject(TDSReader paramTDSReader)
    throws SQLException
  {
    while (true)
    {
      this.opcode = paramTDSReader.readByte();

      if (this.opcode != 43)
      {
        if (this.opcode != 44)
        {
          break;
        }
        byte b = paramTDSReader.readByte();

        if (paramTDSReader.isJavaObject(3, b)) {
          setStatusBits(16);
        }

      }

    }
    
    OracleType localObject;

    switch (this.opcode)
    {
    case 40:
    case 42:
      return null;
    case 2:
      localObject = new OracleTypeDATE();

      ((OracleTypeDATE)localObject).parseTDSrec(paramTDSReader);

      this.idx += 1;

      return localObject;
    case 7:
      localObject = new OracleTypeCHAR(this.connection, 12);

      ((OracleTypeCHAR)localObject).parseTDSrec(paramTDSReader);

      this.idx += 1;

      return localObject;
    case 1:
      localObject = new OracleTypeCHAR(this.connection, 1);

      ((OracleTypeCHAR)localObject).parseTDSrec(paramTDSReader);

      this.idx += 1;

      return localObject;
    case 39:
      localObject = new OracleTypeADT(this, this.idx, this.connection);

      ((OracleTypeADT)localObject).setEmbeddedADT();
      ((OracleTypeADT)localObject).parseTDSrec(paramTDSReader);

      this.idx += 1;

      return localObject;
    case 6:
      localObject = new OracleTypeNUMBER(2);

      ((OracleTypeNUMBER)localObject).parseTDSrec(paramTDSReader);

      this.idx += 1;

      return localObject;
    case 3:
      localObject = new OracleTypeNUMBER(3);

      ((OracleTypeNUMBER)localObject).parseTDSrec(paramTDSReader);

      this.idx += 1;

      return localObject;
    case 4:
      localObject = new OracleTypeNUMBER(8);

      ((OracleTypeNUMBER)localObject).parseTDSrec(paramTDSReader);

      this.idx += 1;

      return localObject;
    case 5:
      localObject = new OracleTypeFLOAT();

      ((OracleTypeFLOAT)localObject).parseTDSrec(paramTDSReader);

      this.idx += 1;

      return localObject;
    case 37:
      localObject = new OracleTypeBINARY_FLOAT();

      ((OracleTypeBINARY_FLOAT)localObject).parseTDSrec(paramTDSReader);

      this.idx += 1;

      return localObject;
    case 45:
      localObject = new OracleTypeBINARY_DOUBLE();

      ((OracleTypeBINARY_DOUBLE)localObject).parseTDSrec(paramTDSReader);

      this.idx += 1;

      return localObject;
    case 8:
      localObject = new OracleTypeSINT32();

      ((OracleTypeSINT32)localObject).parseTDSrec(paramTDSReader);

      this.idx += 1;

      return localObject;
    case 9:
      localObject = new OracleTypeREF(this, this.idx, this.connection);

      ((OracleTypeREF)localObject).parseTDSrec(paramTDSReader);

      this.idx += 1;

      return localObject;
    case 31:
      localObject = new OracleTypeBFILE(this.connection);

      ((OracleTypeBFILE)localObject).parseTDSrec(paramTDSReader);

      this.idx += 1;

      return localObject;
    case 19:
      localObject = new OracleTypeRAW();

      ((OracleTypeRAW)localObject).parseTDSrec(paramTDSReader);

      this.idx += 1;

      return localObject;
    case 29:
      localObject = new OracleTypeCLOB(this.connection);

      ((OracleTypeCLOB)localObject).parseTDSrec(paramTDSReader);

      if ((this.sqlName != null) && (!this.endOfAdt)) {
        this.connection.getForm(this, (OracleTypeCLOB)localObject, this.idx);
      }
      this.idx += 1;

      return localObject;
    case 30:
      localObject = new OracleTypeBLOB(this.connection);

      ((OracleTypeBLOB)localObject).parseTDSrec(paramTDSReader);

      this.idx += 1;

      return localObject;
    case 21:
      localObject = new OracleTypeTIMESTAMP(this.connection);

      ((OracleTypeTIMESTAMP)localObject).parseTDSrec(paramTDSReader);

      this.idx += 1;

      return localObject;
    case 23:
      localObject = new OracleTypeTIMESTAMPTZ(this.connection);

      ((OracleTypeTIMESTAMPTZ)localObject).parseTDSrec(paramTDSReader);

      this.idx += 1;

      return localObject;
    case 33:
      localObject = new OracleTypeTIMESTAMPLTZ(this.connection);

      ((OracleTypeTIMESTAMPLTZ)localObject).parseTDSrec(paramTDSReader);

      this.idx += 1;

      return localObject;
    case 24:
      localObject = new OracleTypeINTERVAL(this.connection);

      ((OracleTypeINTERVAL)localObject).parseTDSrec(paramTDSReader);

      this.idx += 1;

      return localObject;
    case 28:
      localObject = new OracleTypeCOLLECTION(this, this.idx, this.connection);

      ((OracleTypeCOLLECTION)localObject).parseTDSrec(paramTDSReader);

      this.idx += 1;

      return localObject;
    case 27:
      localObject = new OracleTypeUPT(this, this.idx, this.connection);

      ((OracleTypeUPT)localObject).parseTDSrec(paramTDSReader);

      this.idx += 1;

      return localObject;
    case 10:
    case 11:
    case 12:
    case 13:
    case 14:
    case 15:
    case 16:
    case 17:
    case 18:
    case 20:
    case 22:
    case 25:
    case 26:
    case 32:
    case 34:
    case 35:
    case 36:
    case 38:
    case 41:
    case 43:
    case 44: };

    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 48, "get_next_type: " + this.opcode);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public byte[] linearize(Datum paramDatum)
    throws SQLException
  {
    synchronized (this.connection)
    {
      return pickle81(paramDatum);
    }
  }

  public Datum unlinearize(byte[] paramArrayOfByte, long paramLong, Datum paramDatum, int paramInt, Map paramMap)
    throws SQLException
  {
    OracleConnection localOracleConnection = getConnection();
    Datum localDatum = null;

    if (localOracleConnection == null)
    {
      localDatum = _unlinearize(paramArrayOfByte, paramLong, paramDatum, paramInt, paramMap);
    }
    else
    {
      synchronized (localOracleConnection) {
        localDatum = _unlinearize(paramArrayOfByte, paramLong, paramDatum, paramInt, paramMap);
      }
    }

    return localDatum;
  }

  public Datum _unlinearize(byte[] paramArrayOfByte, long paramLong, Datum paramDatum, int paramInt, Map paramMap)
    throws SQLException
  {
    synchronized (this.connection)
    {
      if (paramArrayOfByte == null) {
        return null;
      }

      PickleContext localPickleContext = new PickleContext(paramArrayOfByte, paramLong);

      return unpickle81(localPickleContext, (STRUCT)paramDatum, 1, paramInt, paramMap);
    }
  }

  protected STRUCT unpickle81(PickleContext paramPickleContext, STRUCT paramSTRUCT, int paramInt1, int paramInt2, Map paramMap)
    throws SQLException
  {
    STRUCT localSTRUCT = paramSTRUCT;
    long l1 = paramPickleContext.offset();

    byte b = paramPickleContext.readByte();
    SQLException localSQLException;
    if (!PickleContext.is81format(b))
    {
      localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 1, "Image is not in 8.1 format");
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if (PickleContext.isCollectionImage_pctx(b))
    {
      localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 1, "Image is a collection image,expecting ADT");
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if (!paramPickleContext.readAndCheckVersion())
    {
      localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 1, "Image version is not recognized");
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    switch (paramInt1)
    {
    case 9:
      paramPickleContext.skipBytes(paramPickleContext.readLength(true) - 2);

      break;
    case 3:
      long l2 = paramPickleContext.readLength();

      localSTRUCT = unpickle81Prefix(paramPickleContext, localSTRUCT, b);

      if (localSTRUCT == null)
      {
        StructDescriptor localStructDescriptor = createStructDescriptor();

        localSTRUCT = createByteSTRUCT(localStructDescriptor, (byte[])null);
      }

      localSTRUCT.setImage(paramPickleContext.image(), l1, 0L);
      localSTRUCT.setImageLength(l2);
      paramPickleContext.skipTo(l1 + l2);

      break;
    default:
      paramPickleContext.skipLength();

      localSTRUCT = unpickle81Prefix(paramPickleContext, localSTRUCT, b);

      if (localSTRUCT == null)
      {
        StructDescriptor localObject1 = createStructDescriptor();

        localSTRUCT = createByteSTRUCT(localObject1, (byte[])null);
      }

      OracleType[] localObject1 = localSTRUCT.getDescriptor().getOracleTypeADT().getAttrTypes();
      Object[] localObject2;
      int i;
      switch (paramInt2)
      {
      case 1:
        localObject2 = new Datum[localObject1.length];

        for (i = 0; i < localObject1.length; i++)
        {
          localObject2[i] = ((Datum)localObject1[i].unpickle81rec(paramPickleContext, paramInt2, paramMap));
        }

        localSTRUCT.setDatumArray((Datum[])localObject2);

        break;
      case 2:
        localObject2 = new Object[localObject1.length];

        for (i = 0; i < localObject1.length; i++)
        {
          localObject2[i] = localObject1[i].unpickle81rec(paramPickleContext, paramInt2, paramMap);
        }

        localSTRUCT.setObjArray((Object[])localObject2);

        break;
      default:
        SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 1);
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }

      break;
    }

    return localSTRUCT;
  }

  protected STRUCT unpickle81Prefix(PickleContext paramPickleContext, STRUCT paramSTRUCT, byte paramByte)
    throws SQLException
  {
    STRUCT localSTRUCT = paramSTRUCT;

    if (PickleContext.hasPrefix(paramByte))
    {
      long l = paramPickleContext.readLength() + paramPickleContext.absoluteOffset();

      int i = paramPickleContext.readByte();

      int j = (byte)(i & 0xC);
      int k = j == 0 ? 1 : 0;

      int m = j == 4 ? 1 : 0;

      int n = j == 8 ? 1 : 0;

      int i1 = j == 12 ? 1 : 0;

      int i2 = (i & 0x10) != 0 ? 1 : 0;
      Object localObject;
      if (m != 0)
      {
        localObject = paramPickleContext.readBytes(16);
        String str = toid2typename(this.connection, (byte[])localObject);

        StructDescriptor localStructDescriptor = (StructDescriptor)TypeDescriptor.getTypeDescriptor(str, this.connection);

        if (localSTRUCT == null)
          localSTRUCT = createByteSTRUCT(localStructDescriptor, (byte[])null);
        else {
          localSTRUCT.setDescriptor(localStructDescriptor);
        }
      }
      if (i2 != 0)
      {
        paramPickleContext.readLength();
      }

      if ((n | i1) != 0)
      {
        SQLException sqlexception = DatabaseError.createUnsupportedFeatureSqlException();
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }

      paramPickleContext.skipTo(l);
    }

    return localSTRUCT;
  }

  protected Object unpickle81rec(PickleContext paramPickleContext, int paramInt, Map paramMap)
    throws SQLException
  {
    byte b1 = paramPickleContext.readByte();
    byte b2 = 0;

    if (PickleContext.isAtomicNull(b1))
      return null;
    if (PickleContext.isImmediatelyEmbeddedNull(b1)) {
      b2 = paramPickleContext.readByte();
    }
    STRUCT localSTRUCT = unpickle81datum(paramPickleContext, b1, b2);

    return toObject(localSTRUCT, paramInt, paramMap);
  }

  protected Object unpickle81rec(PickleContext paramPickleContext, byte paramByte, int paramInt, Map paramMap)
    throws SQLException
  {
    STRUCT localSTRUCT = unpickle81datum(paramPickleContext, paramByte, (byte)0);

    return toObject(localSTRUCT, paramInt, paramMap);
  }

  private STRUCT unpickle81datum(PickleContext paramPickleContext, byte paramByte1, byte paramByte2)
    throws SQLException
  {
    int i = getNumAttrs();

    StructDescriptor localStructDescriptor = createStructDescriptor();
    STRUCT localSTRUCT = createByteSTRUCT(localStructDescriptor, (byte[])null);
    OracleType localOracleType = getAttrTypeAt(0);
    Object localObject = null;

    if ((PickleContext.isImmediatelyEmbeddedNull(paramByte1)) && (paramByte2 == 1))
      localObject = null;
    else if (PickleContext.isImmediatelyEmbeddedNull(paramByte1)) {
      localObject = ((OracleTypeADT)localOracleType).unpickle81datum(paramPickleContext, paramByte1, (byte)(paramByte2 - 1));
    }
    else if (PickleContext.isElementNull(paramByte1))
    {
      if ((localOracleType.getTypeCode() == 2002) || (localOracleType.getTypeCode() == 2008))
      {
        localObject = localOracleType.unpickle81datumAsNull(paramPickleContext, paramByte1, paramByte2);
      }
      else localObject = null;
    }
    else {
      localObject = localOracleType.unpickle81rec(paramPickleContext, paramByte1, 1, null);
    }

    Datum[] arrayOfDatum = new Datum[i];

    arrayOfDatum[0] = ((Datum)localObject);

    for (int j = 1; j < i; j++)
    {
      localOracleType = getAttrTypeAt(j);
      arrayOfDatum[j] = ((Datum)localOracleType.unpickle81rec(paramPickleContext, 1, null));
    }

    localSTRUCT.setDatumArray(arrayOfDatum);

    return localSTRUCT;
  }

  protected Datum unpickle81datumAsNull(PickleContext paramPickleContext, byte paramByte1, byte paramByte2)
    throws SQLException
  {
    int i = getNumAttrs();

    StructDescriptor localStructDescriptor = createStructDescriptor();
    STRUCT localSTRUCT = createByteSTRUCT(localStructDescriptor, (byte[])null);
    Datum[] arrayOfDatum = new Datum[i];
    int j = 0;
    OracleType localOracleType = getAttrTypeAt(j);

    if ((localOracleType.getTypeCode() == 2002) || (localOracleType.getTypeCode() == 2008))
    {
      arrayOfDatum[(j++)] = localOracleType.unpickle81datumAsNull(paramPickleContext, paramByte1, paramByte2);
    }
    else arrayOfDatum[(j++)] = ((Datum)null);

    for (; j < i; j++)
    {
      localOracleType = getAttrTypeAt(j);
      if ((localOracleType.getTypeCode() == 2002) || (localOracleType.getTypeCode() == 2008))
      {
        arrayOfDatum[j] = ((Datum)localOracleType.unpickle81rec(paramPickleContext, 1, null));
      }
      else
        arrayOfDatum[j] = ((Datum)localOracleType.unpickle81rec(paramPickleContext, 1, null));
    }
    localSTRUCT.setDatumArray(arrayOfDatum);
    return localSTRUCT;
  }

  public byte[] pickle81(Datum paramDatum)
    throws SQLException
  {
    PickleContext localPickleContext = new PickleContext();

    localPickleContext.initStream();
    pickle81(localPickleContext, paramDatum);

    byte[] arrayOfByte = localPickleContext.stream2Bytes();

    paramDatum.setShareBytes(arrayOfByte);

    return arrayOfByte;
  }

  protected int pickle81(PickleContext paramPickleContext, Datum paramDatum)
    throws SQLException
  {
    int i = paramPickleContext.offset() + 2;
    int j = 0;

    j += paramPickleContext.writeImageHeader(shouldHavePrefix());

    j += pickle81Prefix(paramPickleContext);
    j += pickle81rec(paramPickleContext, paramDatum, 0);

    paramPickleContext.patchImageLen(i, j);

    return j;
  }

  private boolean hasVersion()
  {
    return this.typeVersion > 1;
  }

  private boolean needsToid()
  {
    if (this.isTransient)
      return false;
    return ((this.statusBits & 0x40) != 0) || ((this.statusBits & 0x20) == 0) || (hasVersion());
  }

  private boolean shouldHavePrefix()
  {
    if (this.isTransient)
      return false;
    return (hasVersion()) || (needsToid());
  }

  protected int pickle81Prefix(PickleContext paramPickleContext)
    throws SQLException
  {
    if (shouldHavePrefix())
    {
      int i = 0;
      int j = 1;
      int k = 1;

      if (needsToid())
      {
        k += getTOID().length;
        j |= 4;
      }

      if (hasVersion())
      {
        j |= 16;

        if (this.typeVersion > PickleContext.KOPI20_LN_MAXV)
          k += 5;
        else {
          k += 2;
        }
      }
      i = paramPickleContext.writeLength(k);

      i += paramPickleContext.writeData((byte)j);

      if (needsToid()) {
        i += paramPickleContext.writeData(this.toid);
      }
      if (hasVersion())
      {
        if (this.typeVersion > PickleContext.KOPI20_LN_MAXV)
          i += paramPickleContext.writeLength(this.typeVersion);
        else {
          i += paramPickleContext.writeSB2(this.typeVersion);
        }
      }
      return i;
    }

    return 0;
  }

  private int pickle81rec(PickleContext paramPickleContext, Datum paramDatum, int paramInt)
    throws SQLException
  {
    int i = 0;

    if (!this.metaDataInitialized) {
      copy_properties((OracleTypeADT)((STRUCT)paramDatum).getDescriptor().getPickler());
    }
    Datum[] arrayOfDatum = ((STRUCT)paramDatum).getOracleAttributes();
    int j = arrayOfDatum.length;
    int k = 0;
    OracleType localOracleType = getAttrTypeAt(0);

    if (((localOracleType instanceof OracleTypeADT)) && (!(localOracleType instanceof OracleTypeCOLLECTION)) && (!(localOracleType instanceof OracleTypeUPT)))
    {
      k = 1;

      if (arrayOfDatum[0] == null)
      {
        if (paramInt > 0)
          i += paramPickleContext.writeImmediatelyEmbeddedElementNull((byte)paramInt);
        else {
          i += paramPickleContext.writeAtomicNull();
        }
      }
      else {
        i += ((OracleTypeADT)localOracleType).pickle81rec(paramPickleContext, arrayOfDatum[0], paramInt + 1);
      }

    }

    for (; k < j; k++)
    {
      localOracleType = getAttrTypeAt(k);

      if (arrayOfDatum[k] == null)
      {
        if (((localOracleType instanceof OracleTypeADT)) && (!(localOracleType instanceof OracleTypeCOLLECTION)) && (!(localOracleType instanceof OracleTypeUPT)))
        {
          i += paramPickleContext.writeAtomicNull();
        }
        else
        {
          i += paramPickleContext.writeElementNull();
        }

      }
      else if (((localOracleType instanceof OracleTypeADT)) && (!(localOracleType instanceof OracleTypeCOLLECTION)) && (!(localOracleType instanceof OracleTypeUPT)))
      {
        i += ((OracleTypeADT)localOracleType).pickle81rec(paramPickleContext, arrayOfDatum[k], 1);
      }
      else
      {
        i += localOracleType.pickle81(paramPickleContext, arrayOfDatum[k]);
      }

    }

    return i;
  }

  private Object toObject(STRUCT paramSTRUCT, int paramInt, Map paramMap)
    throws SQLException
  {
    switch (paramInt)
    {
    case 1:
      return paramSTRUCT;
    case 2:
      if (paramSTRUCT != null) {
        return paramSTRUCT.toJdbc(paramMap);
      }

      break;
    default:
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 1);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    return null;
  }

  public String getAttributeType(int paramInt)
    throws SQLException
  {
    return getAttributeType(paramInt, true);
  }

  public String getAttributeType(int paramInt, boolean paramBoolean)
    throws SQLException
  {
    if (paramBoolean)
    {
      if (this.sqlName == null) {
        getFullName();
      }
      if (this.attrNames == null)
        initADTAttrNames();
    }
    if ((paramInt < 1) || ((this.attrTypeNames != null) && (paramInt > this.attrTypeNames.length)))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 1, "Invalid index");
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if (this.attrTypeNames != null) {
      return this.attrTypeNames[(paramInt - 1)];
    }
    return null;
  }

  public String getAttributeName(int paramInt)
    throws SQLException
  {
    if (this.attrNames == null) {
      initADTAttrNames();
    }
    String str = null;
    if (this.attrNames != null)
    {
      synchronized (this.connection) {
        if ((paramInt < 1) || (paramInt > this.attrNames.length))
        {
          SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 1, "Invalid index");
          localSQLException.fillInStackTrace();
          throw localSQLException;
        }
      }
      str = this.attrNames[(paramInt - 1)];
    }
    return str;
  }

  public String getAttributeName(int paramInt, boolean paramBoolean)
    throws SQLException
  {
    if ((paramBoolean) && (this.connection != null)) {
      return getAttributeName(paramInt);
    }

    if ((paramInt < 1) || ((this.attrNames != null) && (paramInt > this.attrNames.length)))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 1, "Invalid index");
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if (this.attrNames != null) {
      return this.attrNames[(paramInt - 1)];
    }
    return null;
  }

  private void initADTAttrNames()
    throws SQLException
  {
    if (this.connection == null)
      return;
    if (this.sqlName == null)
      getFullName();
    synchronized (this.connection)
    {
      CallableStatement localCallableStatement = null;
      PreparedStatement localPreparedStatement = null;
      ResultSet localResultSet = null;
      String[] arrayOfString1 = new String[this.attrTypes.length];
      String[] arrayOfString2 = new String[this.attrTypes.length];
      int i = 0;
      int j = 0;
      if (this.attrNames == null)
      {
        i = this.sqlName.getSchema().equalsIgnoreCase(this.connection.getDefaultSchemaNameForNamedTypes()) ? 0 : 7;

        while (i != 11)
        {
          switch (i)
          {
          case 0:
            localPreparedStatement = this.connection.prepareStatement(getSqlHint() + sqlString[i]);
            localPreparedStatement.setString(1, this.sqlName.getSimpleName());
            localPreparedStatement.setFetchSize(this.idx);
            localResultSet = localPreparedStatement.executeQuery();
            i = 1;
            break;
          case 1:
            if (this.connection.getVersionNumber() >= 10000)
            {
              i = 2;
            }

          case 2:
            localPreparedStatement = this.connection.prepareStatement(getSqlHint() + sqlString[i]);
            localPreparedStatement.setString(1, this.sqlName.getSimpleName());
            localPreparedStatement.setString(2, this.sqlName.getSimpleName());
            localPreparedStatement.setFetchSize(this.idx);
            localResultSet = localPreparedStatement.executeQuery();
            i = 3;
            break;
          case 3:
            if (this.connection.getVersionNumber() >= 10000)
            {
              i = 4;
            }

          case 4:
            localPreparedStatement = this.connection.prepareStatement(getSqlHint() + sqlString[i]);
            localPreparedStatement.setString(1, this.sqlName.getSimpleName());
            localPreparedStatement.setString(2, this.sqlName.getSimpleName());
            localPreparedStatement.setFetchSize(this.idx);
            localResultSet = localPreparedStatement.executeQuery();
            i = 5;
            break;
          case 5:
            if (this.connection.getVersionNumber() >= 10000)
            {
              i = 6;
            }

          case 6:
            localPreparedStatement = this.connection.prepareStatement(getSqlHint() + sqlString[i]);
            localPreparedStatement.setString(1, this.sqlName.getSimpleName());
            localPreparedStatement.setString(2, this.sqlName.getSimpleName());
            localPreparedStatement.setFetchSize(this.idx);
            localResultSet = localPreparedStatement.executeQuery();
            i = 8;
            break;
          case 7:
            localPreparedStatement = this.connection.prepareStatement(getSqlHint() + sqlString[i]);
            localPreparedStatement.setString(1, this.sqlName.getSchema());
            localPreparedStatement.setString(2, this.sqlName.getSimpleName());
            localPreparedStatement.setFetchSize(this.idx);
            localResultSet = localPreparedStatement.executeQuery();
            i = 8;
            break;
          case 8:
            localPreparedStatement = this.connection.prepareStatement(getSqlHint() + sqlString[i]);
            localPreparedStatement.setString(1, this.sqlName.getSimpleName());
            localPreparedStatement.setString(2, this.sqlName.getSimpleName());
            localPreparedStatement.setFetchSize(this.idx);
            localResultSet = localPreparedStatement.executeQuery();
            i = 9;
            break;
          case 9:
            if (this.connection.getVersionNumber() >= 10000)
            {
              i = 10;
            }

          case 10:
            localCallableStatement = this.connection.prepareCall(getSqlHint() + sqlString[i]);
            localCallableStatement.setString(1, this.sqlName.getSimpleName());
            localCallableStatement.registerOutParameter(2, -10);
            localCallableStatement.execute();
            localResultSet = ((OracleCallableStatement)localCallableStatement).getCursor(2);
            i = 11;
          }

          try
          {
            for (j = 0; 
              (j < this.attrTypes.length) && (localResultSet.next()); 
              j++)
            {
              if (localResultSet.getInt(1) != j + 1)
              {
                SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 1, "inconsistent ADT attribute");
                sqlexception.fillInStackTrace();
                throw sqlexception;
              }

              arrayOfString1[j] = localResultSet.getString(2);

              Object localObject1 = localResultSet.getString(4);
              arrayOfString2[j] = "";
              if (localObject1 != null)
                arrayOfString2[j] = ((String)localObject1 + ".");
              int tmp970_968 = j;
              String[] tmp970_966 = arrayOfString2; tmp970_966[tmp970_968] = (tmp970_966[tmp970_968] + localResultSet.getString(3));
            }

            if (j != 0)
            {
              this.attrTypeNames = arrayOfString2;

              this.attrNames = arrayOfString1;
              i = 11;
            }
            else {
              if (localResultSet != null)
                localResultSet.close();
              if (localPreparedStatement != null)
                localPreparedStatement.close();
            }
          } finally {
            if (localResultSet != null)
              localResultSet.close();
            if (localPreparedStatement != null)
              localPreparedStatement.close();
            if (localCallableStatement != null)
              localCallableStatement.close();
          }
        }
      }
    }
  }

  StructDescriptor createStructDescriptor()
    throws SQLException
  {
    StructDescriptor localStructDescriptor = (StructDescriptor)this.descriptor;

    if (localStructDescriptor == null) {
      localStructDescriptor = new StructDescriptor(this, this.connection);
    }
    return localStructDescriptor;
  }

  STRUCT createObjSTRUCT(StructDescriptor paramStructDescriptor, Object[] paramArrayOfObject)
    throws SQLException
  {
    if ((this.statusBits & 0x10) != 0) {
      return new JAVA_STRUCT(paramStructDescriptor, this.connection, paramArrayOfObject);
    }
    return new STRUCT(paramStructDescriptor, this.connection, paramArrayOfObject);
  }

  STRUCT createByteSTRUCT(StructDescriptor paramStructDescriptor, byte[] paramArrayOfByte)
    throws SQLException
  {
    if ((this.statusBits & 0x10) != 0) {
      return new JAVA_STRUCT(paramStructDescriptor, paramArrayOfByte, this.connection);
    }
    return new STRUCT(paramStructDescriptor, paramArrayOfByte, this.connection);
  }

  public static String getSubtypeName(Connection paramConnection, byte[] paramArrayOfByte, long paramLong)
    throws SQLException
  {
    PickleContext localPickleContext = new PickleContext(paramArrayOfByte, paramLong);
    byte b = localPickleContext.readByte();

    if ((!PickleContext.is81format(b)) || (PickleContext.isCollectionImage_pctx(b)) || (!PickleContext.hasPrefix(b)))
    {
      return null;
    }
    Object localObject;
    if (!localPickleContext.readAndCheckVersion())
    {
      SQLException sqlexception = DatabaseError.createSqlException(null, 1, "Image version is not recognized");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    localPickleContext.skipLength();

    localPickleContext.skipLength();

    b = localPickleContext.readByte();

    if ((b & 0x4) != 0)
    {
      localObject = localPickleContext.readBytes(16);

      return toid2typename(paramConnection, (byte[])localObject);
    }

    return null;
  }

  public static String toid2typename(Connection paramConnection, byte[] paramArrayOfByte)
    throws SQLException
  {
    String str = (String)((OracleConnection)paramConnection).getDescriptor(paramArrayOfByte);

    if (str == null)
    {
      PreparedStatement localPreparedStatement = null;
      ResultSet localResultSet = null;
      try
      {
        localPreparedStatement = paramConnection.prepareStatement("select owner, type_name from all_types where type_oid = :1");

        localPreparedStatement.setBytes(1, paramArrayOfByte);

        localResultSet = localPreparedStatement.executeQuery();

        if (localResultSet.next())
        {
          str = localResultSet.getString(1) + "." + localResultSet.getString(2);

          ((OracleConnection)paramConnection).putDescriptor(paramArrayOfByte, str);
        }
        else
        {
          SQLException localSQLException = DatabaseError.createSqlException(null, 1, "Invalid type oid");
          localSQLException.fillInStackTrace();
          throw localSQLException;
        }
      }
      finally
      {
        if (localResultSet != null) {
          localResultSet.close();
        }
        if (localPreparedStatement != null) {
          localPreparedStatement.close();
        }
      }
    }

    return str;
  }

  public int getTdsVersion()
  {
    return this.tdsVersion;
  }

  public void printDebug()
  {
  }

  private String debugText()
  {
    StringWriter localStringWriter = new StringWriter();
    PrintWriter localPrintWriter = new PrintWriter(localStringWriter);

    localPrintWriter.println("OracleTypeADT = " + this);
    localPrintWriter.println("sqlName = " + this.sqlName);

    localPrintWriter.println("OracleType[] : ");

    if (this.attrTypes != null)
    {
      for (int i = 0; i < this.attrTypes.length; i++)
        localPrintWriter.println("[" + i + "] = " + this.attrTypes[i]);
    }
    else {
      localPrintWriter.println("null");
    }
    localPrintWriter.println("toid : ");

    if (this.toid != null)
      printUnsignedByteArray(this.toid, localPrintWriter);
    else {
      localPrintWriter.println("null");
    }

    localPrintWriter.println("tds version : " + this.tdsVersion);
    localPrintWriter.println("type version : " + this.typeVersion);
    localPrintWriter.println("type version : " + this.typeVersion);
    localPrintWriter.println("opcode : " + this.opcode);

    localPrintWriter.println("tdoCState : " + this.tdoCState);

    return localStringWriter.getBuffer().substring(0);
  }

  public byte[] getTOID()
  {
    try
    {
      if (this.toid == null)
      {
        initMetadata(this.connection);
      }
    }
    catch (SQLException localSQLException) {
    }
    return this.toid;
  }

  public int getImageFormatVersion()
  {
    return PickleContext.KOPI20_VERSION;
  }

  public int getTypeVersion()
  {
    try
    {
      if (this.typeVersion == -1)
      {
        initMetadata(this.connection);
      }
    } catch (SQLException localSQLException) {
    }
    return this.typeVersion;
  }

  public int getCharSet()
  {
    return this.charSetId;
  }

  public int getCharSetForm()
  {
    return this.charSetForm;
  }

  public long getTdoCState()
  {
    synchronized (this.connection)
    {
      try
      {
        if (this.tdoCState == 0L)
        {
          getFullName();
          this.tdoCState = this.connection.getTdoCState(this.sqlName.getSchema(), this.sqlName.getSimpleName());
        }
      }
      catch (SQLException localSQLException) {
      }
      return this.tdoCState;
    }
  }

  public long getFIXED_DATA_SIZE()
  {
    try
    {
      return getFixedDataSize();
    }
    catch (SQLException localSQLException) {
    }
    return 0L;
  }

  public long getFixedDataSize()
    throws SQLException
  {
    return this.fixedDataSize;
  }

  public int getAlignmentReq()
    throws SQLException
  {
    return this.alignmentRequirement;
  }

  public int getNumAttrs()
    throws SQLException
  {
    if ((this.attrTypes == null) && (this.connection != null)) {
      init(this.connection);
    }
    return this.attrTypes.length;
  }

  public OracleType getAttrTypeAt(int paramInt)
    throws SQLException
  {
    if ((this.attrTypes == null) && (this.connection != null)) {
      init(this.connection);
    }
    return this.attrTypes[paramInt];
  }

  public boolean isEmbeddedADT()
    throws SQLException
  {
    return (this.statusBits & 0x2) != 0;
  }

  public boolean isUptADT()
    throws SQLException
  {
    return (this.statusBits & 0x4) != 0;
  }

  public boolean isTopADT()
    throws SQLException
  {
    return (this.statusBits & 0x1) != 0;
  }

  public void setStatus(int paramInt)
    throws SQLException
  {
    this.statusBits = paramInt;
  }

  void setEmbeddedADT()
    throws SQLException
  {
    maskAndSetStatusBits(-16, 2);
  }

  void setUptADT()
    throws SQLException
  {
    maskAndSetStatusBits(-16, 4);
  }

  public boolean isSubType()
    throws SQLException
  {
    return (this.statusBits & 0x40) != 0;
  }

  public boolean isFinalType()
    throws SQLException
  {
    return ((this.statusBits & 0x20) != 0 ? true : false) || ((this.statusBits & 0x2) != 0 ? true : false);
  }

  public boolean isJavaObject()
    throws SQLException
  {
    return (this.statusBits & 0x10) != 0;
  }

  public int getStatus()
    throws SQLException
  {
    if (((this.statusBits & 0x1) != 0) && ((this.statusBits & 0x100) == 0))
      init(this.connection);
    return this.statusBits;
  }

  public static OracleTypeADT shallowClone(OracleTypeADT paramOracleTypeADT)
    throws SQLException
  {
    OracleTypeADT localOracleTypeADT = new OracleTypeADT();
    shallowCopy(paramOracleTypeADT, localOracleTypeADT);
    return localOracleTypeADT;
  }

  public static void shallowCopy(OracleTypeADT paramOracleTypeADT1, OracleTypeADT paramOracleTypeADT2)
    throws SQLException
  {
    paramOracleTypeADT2.connection = paramOracleTypeADT1.connection;
    paramOracleTypeADT2.sqlName = paramOracleTypeADT1.sqlName;
    paramOracleTypeADT2.parent = paramOracleTypeADT1.parent;
    paramOracleTypeADT2.idx = paramOracleTypeADT1.idx;
    paramOracleTypeADT2.descriptor = paramOracleTypeADT1.descriptor;
    paramOracleTypeADT2.statusBits = paramOracleTypeADT1.statusBits;

    paramOracleTypeADT2.typeCode = paramOracleTypeADT1.typeCode;
    paramOracleTypeADT2.dbTypeCode = paramOracleTypeADT1.dbTypeCode;
    paramOracleTypeADT2.tdsVersion = paramOracleTypeADT1.tdsVersion;
    paramOracleTypeADT2.typeVersion = paramOracleTypeADT1.typeVersion;
    paramOracleTypeADT2.fixedDataSize = paramOracleTypeADT1.fixedDataSize;
    paramOracleTypeADT2.alignmentRequirement = paramOracleTypeADT1.alignmentRequirement;
    paramOracleTypeADT2.attrTypes = paramOracleTypeADT1.attrTypes;
    paramOracleTypeADT2.sqlName = paramOracleTypeADT1.sqlName;
    paramOracleTypeADT2.tdoCState = paramOracleTypeADT1.tdoCState;
    paramOracleTypeADT2.toid = paramOracleTypeADT1.toid;
    paramOracleTypeADT2.charSetId = paramOracleTypeADT1.charSetId;
    paramOracleTypeADT2.charSetForm = paramOracleTypeADT1.charSetForm;
    paramOracleTypeADT2.flattenedAttrNum = paramOracleTypeADT1.flattenedAttrNum;
    paramOracleTypeADT2.statusBits = paramOracleTypeADT1.statusBits;
    paramOracleTypeADT2.attrNames = paramOracleTypeADT1.attrNames;
    paramOracleTypeADT2.attrTypeNames = paramOracleTypeADT1.attrTypeNames;
    paramOracleTypeADT2.opcode = paramOracleTypeADT1.opcode;
    paramOracleTypeADT2.idx = paramOracleTypeADT1.idx;
  }

  private void writeObject(ObjectOutputStream paramObjectOutputStream)
    throws IOException
  {
    paramObjectOutputStream.writeInt(this.statusBits);
    paramObjectOutputStream.writeInt(this.tdsVersion);
    paramObjectOutputStream.writeInt(this.typeVersion);
    paramObjectOutputStream.writeObject(null);
    paramObjectOutputStream.writeObject(null);
    paramObjectOutputStream.writeLong(this.fixedDataSize);
    paramObjectOutputStream.writeInt(this.alignmentRequirement);
    paramObjectOutputStream.writeObject(this.attrTypes);
    paramObjectOutputStream.writeObject(this.attrNames);
    paramObjectOutputStream.writeObject(this.attrTypeNames);
    paramObjectOutputStream.writeLong(this.tdoCState);
    paramObjectOutputStream.writeObject(this.toid);
    paramObjectOutputStream.writeObject(null);
    paramObjectOutputStream.writeInt(this.charSetId);
    paramObjectOutputStream.writeInt(this.charSetForm);
    paramObjectOutputStream.writeBoolean(true);
    paramObjectOutputStream.writeInt(this.flattenedAttrNum);
  }

  private void readObject(ObjectInputStream paramObjectInputStream)
    throws IOException, ClassNotFoundException
  {
    this.statusBits = paramObjectInputStream.readInt();
    this.tdsVersion = paramObjectInputStream.readInt();
    this.typeVersion = paramObjectInputStream.readInt();
    paramObjectInputStream.readObject();
    paramObjectInputStream.readObject();
    paramObjectInputStream.readLong();
    paramObjectInputStream.readInt();
    this.attrTypes = ((OracleType[])paramObjectInputStream.readObject());
    this.attrNames = ((String[])paramObjectInputStream.readObject());
    this.attrTypeNames = ((String[])paramObjectInputStream.readObject());
    paramObjectInputStream.readLong();
    this.toid = ((byte[])paramObjectInputStream.readObject());
    paramObjectInputStream.readObject();
    this.charSetId = paramObjectInputStream.readInt();
    this.charSetForm = paramObjectInputStream.readInt();
    paramObjectInputStream.readBoolean();
    this.flattenedAttrNum = paramObjectInputStream.readInt();
  }

  public void setConnection(OracleConnection paramOracleConnection)
    throws SQLException
  {
    synchronized (paramOracleConnection)
    {
      this.connection = paramOracleConnection;
      for (int i = 0; i < this.attrTypes.length; i++)
        this.attrTypes[i].setConnection(this.connection);
    }
  }

  private void setStatusBits(int paramInt)
  {
    synchronized (this.connection)
    {
      this.statusBits |= paramInt;
    }
  }

  private void maskAndSetStatusBits(int paramInt1, int paramInt2)
  {
    synchronized (this.connection)
    {
      this.statusBits &= paramInt1;
      this.statusBits |= paramInt2;
    }
  }

  private void printUnsignedByteArray(byte[] paramArrayOfByte, PrintWriter paramPrintWriter)
  {
    int i = paramArrayOfByte.length;

    int[] arrayOfInt = Util.toJavaUnsignedBytes(paramArrayOfByte);

    for (int j = 0; j < i; j++)
    {
      paramPrintWriter.print("0x" + Integer.toHexString(arrayOfInt[j]) + " ");
    }

    paramPrintWriter.println();

    for (int j = 0; j < i; j++)
    {
      paramPrintWriter.print(arrayOfInt[j] + " ");
    }

    paramPrintWriter.println();
  }

  public void initChildNamesRecursively(Map paramMap)
    throws SQLException
  {
    TypeTreeElement localTypeTreeElement = (TypeTreeElement)paramMap.get(this.sqlName);

    if ((this.attrTypes != null) && (this.attrTypes.length > 0))
    {
      for (int i = 0; i < this.attrTypes.length; i++)
      {
        OracleType localOracleType = this.attrTypes[i];
        localOracleType.setNames(localTypeTreeElement.getChildSchemaName(i + 1), localTypeTreeElement.getChildTypeName(i + 1));
        localOracleType.initChildNamesRecursively(paramMap);
        localOracleType.cacheDescriptor();
      }
    }
  }

  public void cacheDescriptor()
    throws SQLException
  {
    this.descriptor = StructDescriptor.createDescriptor(this);
  }

  public void printXML(PrintWriter paramPrintWriter, int paramInt)
    throws SQLException
  {
    printXML(paramPrintWriter, paramInt, false);
  }

  public void printXML(PrintWriter paramPrintWriter, int paramInt, boolean paramBoolean)
    throws SQLException
  {
    int i;
    for (i = 0; i < paramInt; i++) paramPrintWriter.print("  ");
    paramPrintWriter.print("<OracleTypeADT sqlName=\"" + this.sqlName + "\" ");

    paramPrintWriter.print(" typecode=\"" + this.typeCode + "\"");
    if (this.tdsVersion != -9999)
      paramPrintWriter.print(" tds_version=\"" + this.tdsVersion + "\"");
    paramPrintWriter.println();
    for (i = 0; i < paramInt + 4; i++) paramPrintWriter.print("  ");
    paramPrintWriter.println(" is_embedded=\"" + isEmbeddedADT() + "\"" + " is_top_level=\"" + isTopADT() + "\"" + " is_upt=\"" + isUptADT() + "\"" + " finalType=\"" + isFinalType() + "\"" + " subtype=\"" + isSubType() + "\">");

    if ((this.attrTypes != null) && (this.attrTypes.length > 0))
    {
      for (i = 0; i < paramInt + 1; i++) paramPrintWriter.print("  ");
      paramPrintWriter.println("<attributes>");
      for (i = 0; i < this.attrTypes.length; i++)
      {
        for (int j = 0; j < paramInt + 2; j++) paramPrintWriter.print("  ");

        paramPrintWriter.println("<attribute name=\"" + getAttributeName(i + 1, paramBoolean) + "\" " + " type=\"" + getAttributeType(i + 1, false) + "\" >");

        this.attrTypes[i].printXML(paramPrintWriter, paramInt + 3, paramBoolean);
        for (int j = 0; j < paramInt + 2; j++) paramPrintWriter.print("  ");
        paramPrintWriter.println("</attribute> ");
      }
      for (i = 0; i < paramInt + 1; i++) paramPrintWriter.print("  ");
      paramPrintWriter.println("</attributes>");
    }
    for (i = 0; i < paramInt; i++) paramPrintWriter.print("  ");
    paramPrintWriter.println("</OracleTypeADT>");
  }
}