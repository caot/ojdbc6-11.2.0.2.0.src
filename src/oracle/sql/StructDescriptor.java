package oracle.sql;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.util.Map;
import java.util.Vector;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.driver.DatabaseError;
import oracle.jdbc.oracore.OracleNamedType;
import oracle.jdbc.oracore.OracleType;
import oracle.jdbc.oracore.OracleTypeADT;

public class StructDescriptor extends TypeDescriptor
  implements Serializable
{
  static final boolean DEBUG = false;
  static final long serialVersionUID = 1013921343538311063L;
  static final int LOCAL_TYPE = 0;
  static final int LOOK_FOR_USER_SYNONYM = 1;
  static final int LOOK_FOR_PUBLIC_SYNONYM = 2;
  static final String[] initMetaData1_9_0_SQL = { "SELECT INSTANTIABLE, supertype_owner, supertype_name, LOCAL_ATTRIBUTES FROM all_types WHERE type_name = :1 AND owner = :2 ", "DECLARE \n bind_synonym_name user_synonyms.synonym_name%type := :1; \n the_table_owner  user_synonyms.table_owner%type; \n the_table_name   user_synonyms.table_name%type; \n the_db_link      user_synonyms.db_link%type; \n sql_string       VARCHAR2(1000); \nBEGIN \n   SELECT  TABLE_NAME, TABLE_OWNER, DB_LINK INTO  \n         the_table_name, the_table_owner, the_db_link \n         FROM USER_SYNONYMS WHERE \n         SYNONYM_NAME = bind_synonym_name; \n \n   sql_string := 'SELECT  INSTANTIABLE, SUPERTYPE_OWNER,      SUPERTYPE_NAME, LOCAL_ATTRIBUTES FROM ALL_TYPES'; \n \n   IF the_db_link IS NOT NULL  \n   THEN \n     sql_string := sql_string || '@' || the_db_link; \n   END IF; \n   sql_string := sql_string       || ' WHERE TYPE_NAME = '''       || the_table_name   || ''' AND OWNER = '''       || the_table_owner  || ''''; \n   OPEN :2 FOR sql_string; \nEND;", "DECLARE \n bind_synonym_name user_synonyms.synonym_name%type := :1; \n the_table_owner  user_synonyms.table_owner%type; \n the_table_name   user_synonyms.table_name%type; \n the_db_link      user_synonyms.db_link%type; \n sql_string       VARCHAR2(1000); \nBEGIN \n   SELECT  TABLE_NAME, TABLE_OWNER, DB_LINK INTO  \n         the_table_name, the_table_owner, the_db_link \n         FROM ALL_SYNONYMS WHERE \n         OWNER = 'PUBLIC' AND \n         SYNONYM_NAME = bind_synonym_name; \n \n   sql_string := 'SELECT  INSTANTIABLE, SUPERTYPE_OWNER,      SUPERTYPE_NAME, LOCAL_ATTRIBUTES FROM ALL_TYPES'; \n \n   IF the_db_link IS NOT NULL  \n   THEN \n     sql_string := sql_string || '@' || the_db_link; \n   END IF; \n   sql_string := sql_string       || ' WHERE TYPE_NAME = '''       || the_table_name   || ''' AND OWNER = '''       || the_table_owner  || ''''; \n   OPEN :2 FOR sql_string; \nEND;" };

  String sqlHint = null;

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  public static StructDescriptor createDescriptor(String paramString, Connection paramConnection)
    throws SQLException
  {
    return createDescriptor(paramString, paramConnection, false, false);
  }

  public static StructDescriptor createDescriptor(String paramString, Connection paramConnection, boolean paramBoolean1, boolean paramBoolean2)
    throws SQLException
  {
    if ((paramString == null) || (paramString.length() == 0) || (paramConnection == null))
    {
      SQLException sqlexception = DatabaseError.createSqlException(null, 60, "Invalid arguments");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject = new SQLName(paramString, (oracle.jdbc.OracleConnection)paramConnection);

    StructDescriptor localStructDescriptor = createDescriptor((SQLName)localObject, paramConnection, paramBoolean1, paramBoolean2);
    return localStructDescriptor;
  }

  public static StructDescriptor createDescriptor(SQLName paramSQLName, Connection paramConnection, boolean paramBoolean1, boolean paramBoolean2)
    throws SQLException
  {
    String str = paramSQLName.getName();
    StructDescriptor localStructDescriptor = null;
    if (!paramBoolean2)
    {
      localStructDescriptor = (StructDescriptor)((oracle.jdbc.OracleConnection)paramConnection).getDescriptor(str);

      if (localStructDescriptor == null)
      {
        localStructDescriptor = new StructDescriptor(paramSQLName, paramConnection);
        if (paramBoolean1) localStructDescriptor.initNamesRecursively();
        ((oracle.jdbc.OracleConnection)paramConnection).putDescriptor(str, localStructDescriptor);
      }
    }
    return localStructDescriptor;
  }

  public static StructDescriptor createDescriptor(SQLName paramSQLName, Connection paramConnection)
    throws SQLException
  {
    return createDescriptor(paramSQLName, paramConnection, false, false);
  }

  public static StructDescriptor createDescriptor(OracleTypeADT paramOracleTypeADT)
    throws SQLException
  {
    String str = paramOracleTypeADT.getFullName();
    oracle.jdbc.internal.OracleConnection localOracleConnection = paramOracleTypeADT.getConnection();
    StructDescriptor localStructDescriptor = (StructDescriptor)localOracleConnection.getDescriptor(str);

    if (localStructDescriptor == null)
    {
      SQLName localSQLName = new SQLName(paramOracleTypeADT.getSchemaName(), paramOracleTypeADT.getSimpleName(), paramOracleTypeADT.getConnection());

      localStructDescriptor = new StructDescriptor(localSQLName, paramOracleTypeADT, localOracleConnection);

      localOracleConnection.putDescriptor(str, localStructDescriptor);
    }
    return localStructDescriptor;
  }

  public static StructDescriptor createDescriptor(SQLName paramSQLName, byte[] paramArrayOfByte1, int paramInt, byte[] paramArrayOfByte2, oracle.jdbc.internal.OracleConnection paramOracleConnection)
    throws SQLException
  {
    OracleTypeADT localOracleTypeADT = new OracleTypeADT(paramSQLName, paramArrayOfByte1, paramInt, paramArrayOfByte2, paramOracleConnection);
    return new StructDescriptor(paramSQLName, localOracleTypeADT, paramOracleConnection);
  }

  public StructDescriptor(OracleTypeADT paramOracleTypeADT, Connection paramConnection)
    throws SQLException
  {
    super((short)108, paramOracleTypeADT, paramConnection);
  }

  public StructDescriptor(String paramString, Connection paramConnection)
    throws SQLException
  {
    super((short)108, paramString, paramConnection);

    initPickler();
  }

  public StructDescriptor(SQLName paramSQLName, Connection paramConnection)
    throws SQLException
  {
    super((short)108, paramSQLName, paramConnection);

    initPickler();
  }

  public StructDescriptor(SQLName paramSQLName, OracleTypeADT paramOracleTypeADT, Connection paramConnection)
    throws SQLException
  {
    super((short)108, paramSQLName, paramOracleTypeADT, paramConnection);

    this.toid = paramOracleTypeADT.getTOID();
  }

  StructDescriptor(byte[] paramArrayOfByte, int paramInt, Connection paramConnection)
    throws SQLException
  {
    super((short)108);

    this.toid = paramArrayOfByte;
    this.toidVersion = paramInt;
    setPhysicalConnectionOf(paramConnection);
    initPickler();
  }

  StructDescriptor(AttributeDescriptor[] paramArrayOfAttributeDescriptor, Connection paramConnection)
    throws SQLException
  {
    super((short)108);

    this.attributesDescriptor = paramArrayOfAttributeDescriptor;
    setPhysicalConnectionOf(paramConnection);
    this.isTransient = true;

    initPickler();
    this.isInstanciable = Boolean.TRUE;
  }

  private void initPickler()
    throws SQLException
  {
    try
    {
      if (this.isTransient) {
        this.pickler = new OracleTypeADT(this.attributesDescriptor, this.connection);
      }
      else {
        this.pickler = new OracleTypeADT(getName(), this.connection);
        ((OracleTypeADT)this.pickler).init(this.connection);
        this.toid = ((OracleTypeADT)this.pickler).getTOID();
      }

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

  public int getTypeCode()
    throws SQLException
  {
    int i = getOracleTypeADT().getTypeCode();
    return i;
  }

  public int getTypeVersion()
    throws SQLException
  {
    int i = getOracleTypeADT().getTypeVersion();
    return i;
  }

  void setAttributesDescriptor(AttributeDescriptor[] paramArrayOfAttributeDescriptor)
  {
    this.attributesDescriptor = paramArrayOfAttributeDescriptor;
  }

  public AttributeDescriptor[] getAttributesDescriptor()
  {
    return this.attributesDescriptor;
  }

  byte[] toBytes(STRUCT paramSTRUCT, boolean paramBoolean)
    throws SQLException
  {
    byte[] localObject1 = paramSTRUCT.shareBytes();
    if (localObject1 == null)
    {
      if (paramSTRUCT.datumArray != null)
      {
        localObject1 = this.pickler.linearize(paramSTRUCT);

        if (!paramBoolean) {
          paramSTRUCT.setShareBytes(null);
        }
      }
      else if (paramSTRUCT.objectArray != null)
      {
        paramSTRUCT.datumArray = toOracleArray(paramSTRUCT.objectArray);
        localObject1 = this.pickler.linearize(paramSTRUCT);

        if (!paramBoolean)
        {
          paramSTRUCT.datumArray = null;

          paramSTRUCT.setShareBytes(null);
        }

      }
      else
      {
        SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 1);
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }

    }
    else if (paramSTRUCT.imageLength != 0L)
    {
      if ((paramSTRUCT.imageOffset != 0L) || (paramSTRUCT.imageLength != localObject1.length))
      {
        byte[] localObject2 = new byte[(int)paramSTRUCT.imageLength];

        System.arraycopy(localObject1, (int)paramSTRUCT.imageOffset, localObject2, 0, (int)paramSTRUCT.imageLength);

        paramSTRUCT.setImage((byte[])localObject2, 0L, 0L);

        localObject1 = localObject2;
      }
    }

    return localObject1;
  }

  Datum[] toOracleArray(STRUCT paramSTRUCT, boolean paramBoolean)
    throws SQLException
  {
    Datum[] arrayOfDatum1 = paramSTRUCT.datumArray;
    Datum[] arrayOfDatum2 = null;

    if (arrayOfDatum1 == null)
    {
      if (paramSTRUCT.objectArray != null)
      {
        arrayOfDatum1 = toOracleArray(paramSTRUCT.objectArray);
      }
      else if (paramSTRUCT.shareBytes() != null)
      {
        if (((paramSTRUCT.shareBytes()[0] & 0x80) <= 0) && (((OracleTypeADT)this.pickler).isEmbeddedADT()))
        {
          this.pickler = OracleTypeADT.shallowClone((OracleTypeADT)this.pickler);
        }

        this.pickler.unlinearize(paramSTRUCT.shareBytes(), paramSTRUCT.imageOffset, paramSTRUCT, 1, null);

        arrayOfDatum1 = paramSTRUCT.datumArray;

        if (!paramBoolean) {
          paramSTRUCT.datumArray = null;
        }
      }
      else
      {
        SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 1);
        localSQLException.fillInStackTrace();
        throw localSQLException;
      }

    }

    if (paramBoolean)
    {
      paramSTRUCT.datumArray = arrayOfDatum1;
      arrayOfDatum2 = (Datum[])arrayOfDatum1.clone();
    }
    else
    {
      arrayOfDatum2 = arrayOfDatum1;
    }
    return arrayOfDatum2;
  }

  Object[] toArray(STRUCT paramSTRUCT, Map paramMap, boolean paramBoolean)
    throws SQLException
  {
    Object[] arrayOfObject = null;

    if (paramSTRUCT.objectArray == null)
    {
      if (paramSTRUCT.datumArray != null)
      {
        arrayOfObject = new Object[paramSTRUCT.datumArray.length];

        for (int i = 0; i < paramSTRUCT.datumArray.length; i++)
        {
          if (paramSTRUCT.datumArray[i] != null)
          {
            if ((paramSTRUCT.datumArray[i] instanceof STRUCT))
              arrayOfObject[i] = ((STRUCT)paramSTRUCT.datumArray[i]).toJdbc(paramMap);
            else
              arrayOfObject[i] = paramSTRUCT.datumArray[i].toJdbc();
          }
        }
      }
      else if (paramSTRUCT.shareBytes() != null)
      {
        if (((paramSTRUCT.shareBytes()[0] & 0x80) <= 0) && (((OracleTypeADT)this.pickler).isEmbeddedADT()))
        {
          this.pickler = OracleTypeADT.shallowClone((OracleTypeADT)this.pickler);
        }

        this.pickler.unlinearize(paramSTRUCT.shareBytes(), paramSTRUCT.imageOffset, paramSTRUCT, 2, paramMap);

        arrayOfObject = paramSTRUCT.objectArray;

        paramSTRUCT.objectArray = null;
      }
      else
      {
        SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 1);
        localSQLException.fillInStackTrace();
        throw localSQLException;
      }

    }
    else
    {
      arrayOfObject = (Object[])paramSTRUCT.objectArray.clone();
    }
    return arrayOfObject;
  }

  public int getLength()
    throws SQLException
  {
    return getFieldTypes().length;
  }

  public OracleTypeADT getOracleTypeADT()
    throws SQLException
  {
    if (this.pickler == null)
      initPickler();
    OracleTypeADT localOracleTypeADT = (OracleTypeADT)this.pickler;
    return localOracleTypeADT;
  }

  private OracleType[] getFieldTypes()
    throws SQLException
  {
    return ((OracleTypeADT)this.pickler).getAttrTypes();
  }

  public SQLInput toJdbc2SQLInput(STRUCT paramSTRUCT, Map paramMap)
    throws SQLException
  {
    return new OracleJdbc2SQLInput(toOracleArray(paramSTRUCT, false), paramMap, this.connection);
  }

  public SQLOutput toJdbc2SQLOutput()
    throws SQLException
  {
    return new OracleSQLOutput(this, this.connection);
  }

  public Datum[] toOracleArray(Object[] paramArrayOfObject)
    throws SQLException
  {
    Datum[] arrayOfDatum = null;

    if (paramArrayOfObject != null)
    {
      OracleType[] arrayOfOracleType = getFieldTypes();
      int i = arrayOfOracleType.length;

      if (paramArrayOfObject.length != i)
      {
        SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 49, null);
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }

      arrayOfDatum = new Datum[i];

      Object localObject = this.connection;

      for (int j = 0; j < i; j++) {
        arrayOfDatum[j] = arrayOfOracleType[j].toDatum(paramArrayOfObject[j], (oracle.jdbc.internal.OracleConnection)localObject);
      }
    }
    return arrayOfDatum;
  }

  public Datum[] toOracleArray(Map paramMap)
    throws SQLException
  {
    Datum[] arrayOfDatum = null;
    int i = 0;

    if (paramMap != null)
    {
      OracleType[] arrayOfOracleType = getFieldTypes();
      int j = arrayOfOracleType.length;
      int k = paramMap.size();

      arrayOfDatum = new Datum[j];

      oracle.jdbc.internal.OracleConnection localOracleConnection = this.connection;

      for (int m = 0; m < j; m++)
      {
        Object localObject = paramMap.get(((OracleTypeADT)this.pickler).getAttributeName(m + 1));

        arrayOfDatum[m] = arrayOfOracleType[m].toDatum(localObject, localOracleConnection);

        if ((localObject != null) || (paramMap.containsKey(((OracleTypeADT)this.pickler).getAttributeName(m + 1))))
        {
          i++;
        }
      }
      if (i < k)
      {
        SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68, null);
        localSQLException.fillInStackTrace();
        throw localSQLException;
      }
    }

    return arrayOfDatum;
  }

  public ResultSetMetaData getMetaData()
    throws SQLException
  {
    return this.connection.newStructMetaData(this);
  }

  public boolean isFinalType()
    throws SQLException
  {
    return getOracleTypeADT().isFinalType();
  }

  public boolean isSubtype()
    throws SQLException
  {
    return getOracleTypeADT().isSubType();
  }

  public boolean isInHierarchyOf(String paramString)
    throws SQLException
  {
    StructDescriptor localStructDescriptor = this;
    String str = localStructDescriptor.getName();
    boolean bool = false;

    if (paramString.equals(str)) {
      bool = true;
    }
    else {
      while (true)
      {
        str = localStructDescriptor.getSupertypeName();

        if (str == null)
        {
          bool = false;

          break;
        }

        if (paramString.equals(str))
        {
          bool = true;

          break;
        }

        localStructDescriptor = createDescriptor(str, this.connection);
      }
    }

    return bool;
  }

  public boolean isInstantiable()
    throws SQLException
  {
    if (this.isInstanciable == null)
      initMetaData1();
    return this.isInstanciable.booleanValue();
  }

  public boolean isJavaObject()
    throws SQLException
  {
    return getOracleTypeADT().isJavaObject();
  }

  public String getSupertypeName()
    throws SQLException
  {
    String str = null;

    if (isSubtype())
    {
      if (this.supertype == null) {
        initMetaData1();
      }
      str = this.supertype;
    }

    return str;
  }

  public int getLocalAttributeCount()
    throws SQLException
  {
    int i;
    if (!isSubtype()) {
      i = getOracleTypeADT().getAttrTypes().length;
    }
    else {
      if (this.numLocalAttrs == -1) {
        initMetaData1();
      }
      i = this.numLocalAttrs;
    }
    return i;
  }

  public String[] getSubtypeNames()
    throws SQLException
  {
    if (this.subtypes == null)
      initMetaData2();
    return this.subtypes;
  }

  public String getJavaClassName()
    throws SQLException
  {
    String str = null;

    if (isJavaObject()) {
      str = getJavaObjectClassName(this.connection, this);
    }
    return str;
  }

  public String getAttributeJavaName(int paramInt)
    throws SQLException
  {
    String str = null;

    if (isJavaObject())
    {
      if (this.attrJavaNames == null) {
        initMetaData3();
      }
      str = this.attrJavaNames[paramInt];
    }
    return str;
  }

  public String[] getAttributeJavaNames()
    throws SQLException
  {
    String[] arrayOfString = null;

    if (isJavaObject())
    {
      if (this.attrJavaNames == null) {
        initMetaData3();
      }
      arrayOfString = this.attrJavaNames;
    }
    else
    {
      arrayOfString = new String[0];
    }
    return arrayOfString;
  }

  public String getLanguage()
    throws SQLException
  {
    String str = null;

    if (isJavaObject())
      str = "JAVA";
    else {
      str = "SQL";
    }
    return str;
  }

  public Class getClass(Map paramMap)
    throws SQLException
  {
    String str1 = getName();

    Class localClass = this.connection.getClassForType(str1, paramMap);

    String str2 = getSchemaName();
    String str3 = getTypeName();

    if (localClass == null)
    {
      localClass = (Class)paramMap.get(str3);
    }

    if (SQLName.s_parseAllFormat)
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
    }

    return localClass;
  }

  public static String getJavaObjectClassName(Connection paramConnection, StructDescriptor paramStructDescriptor)
    throws SQLException
  {
    return getJavaObjectClassName(paramConnection, paramStructDescriptor.getSchemaName(), paramStructDescriptor.getTypeName());
  }

  public static String getJavaObjectClassName(Connection paramConnection, String paramString1, String paramString2)
    throws SQLException
  {
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;

    String str = null;
    try
    {
      localPreparedStatement = paramConnection.prepareStatement("select external_name from all_sqlj_types where owner = :1 and type_name = :2");

      localPreparedStatement.setString(1, paramString1);
      localPreparedStatement.setString(2, paramString2);

      localResultSet = localPreparedStatement.executeQuery();

      if (localResultSet.next()) {
        str = localResultSet.getString(1);
      }
      else
      {
        SQLException localSQLException1 = DatabaseError.createSqlException(null, 100);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

    }
    catch (SQLException sqlexception)
    {
    }
    finally
    {
      if (localResultSet != null) {
        localResultSet.close();
      }
      if (localPreparedStatement != null)
        localPreparedStatement.close();
    }
    return str;
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
    paramStringBuffer.append(str1);
    paramStringBuffer.append("Subtype=" + getOracleTypeADT().isSubType());
    paramStringBuffer.append(" JavaObject=" + getOracleTypeADT().isJavaObject());
    paramStringBuffer.append(" FinalType=" + getOracleTypeADT().isFinalType());
    paramStringBuffer.append("\n");

    ResultSetMetaData localResultSetMetaData = getMetaData();
    int j = localResultSetMetaData.getColumnCount();

    for (int k = 0; k < j; k++)
    {
      int m = localResultSetMetaData.getColumnType(k + 1);
      Object localObject;
      if ((m == 2002) || (m == 2008))
      {
        localObject = createDescriptor(localResultSetMetaData.getColumnTypeName(k + 1), this.connection);

        ((StructDescriptor)localObject).descType(paramStringBuffer, paramInt + 1);
      }
      else if (m == 2003)
      {
        localObject = ArrayDescriptor.createDescriptor(localResultSetMetaData.getColumnTypeName(k + 1), this.connection);

        ((ArrayDescriptor)localObject).descType(paramStringBuffer, paramInt + 1);
      }
      else if (m == 2007)
      {
        localObject = OpaqueDescriptor.createDescriptor(localResultSetMetaData.getColumnTypeName(k + 1), this.connection);

        ((OpaqueDescriptor)localObject).descType(paramStringBuffer, paramInt + 1);
      }
      else
      {
        paramStringBuffer.append(str2);
        paramStringBuffer.append(localResultSetMetaData.getColumnTypeName(k + 1));
        paramStringBuffer.append("\n");
      }
    }

    return paramStringBuffer.substring(0, paramStringBuffer.length());
  }

  public byte[] toBytes(Object[] paramArrayOfObject)
    throws SQLException
  {
    Datum[] arrayOfDatum = toOracleArray(paramArrayOfObject);

    return toBytes(arrayOfDatum);
  }

  /** @deprecated */
  public byte[] toBytes(Datum[] paramArrayOfDatum)
    throws SQLException
  {
    STRUCT localSTRUCT = new STRUCT(this, (byte[])null, this.connection);

    localSTRUCT.setDatumArray(paramArrayOfDatum);

    return this.pickler.linearize(localSTRUCT);
  }

  public Datum[] toArray(Object[] paramArrayOfObject)
    throws SQLException
  {
    return toOracleArray(paramArrayOfObject);
  }

  public Datum[] toArray(byte[] paramArrayOfByte)
    throws SQLException
  {
    STRUCT localSTRUCT = new STRUCT(this, paramArrayOfByte, this.connection);

    return toOracleArray(localSTRUCT, false);
  }

  private void initMetaData1()
    throws SQLException
  {
    int i = this.connection.getVersionNumber();

    if (i >= 9000)
      initMetaData1_9_0();
    else
      initMetaData1_pre_9_0();
  }

  private String getSqlHint()
    throws SQLException
  {
    if (this.sqlHint == null)
    {
      if (this.connection.getVersionNumber() >= 11000)
        this.sqlHint = "";
      else
        this.sqlHint = "/*+RULE*/";
    }
    return this.sqlHint;
  }

  private void initMetaData1_9_0()
    throws SQLException
  {
    synchronized (this.connection)
    {
      int i = 0;

      if (this.numLocalAttrs == -1)
      {
        PreparedStatement localPreparedStatement = null;
        OracleCallableStatement localOracleCallableStatement = null;
        ResultSet localResultSet = null;
        int j = -1;
        try
        {
          label411:
          while (true)
          {
            switch (i)
            {
            case 0:
              localPreparedStatement = this.connection.prepareStatement(getSqlHint() + initMetaData1_9_0_SQL[i]);

              localPreparedStatement.setString(1, getTypeName());
              localPreparedStatement.setString(2, getSchemaName());

              localPreparedStatement.setFetchSize(1);
              localResultSet = localPreparedStatement.executeQuery();

              break;
            case 1:
            case 2:
              try
              {
                localOracleCallableStatement = (OracleCallableStatement)this.connection.prepareCall(getSqlHint() + initMetaData1_9_0_SQL[i]);

                localOracleCallableStatement.setString(1, getTypeName());
                localOracleCallableStatement.registerOutParameter(2, -10);

                localOracleCallableStatement.execute();

                localResultSet = localOracleCallableStatement.getCursor(2);
                localResultSet.setFetchSize(1);
              }
              catch (SQLException localSQLException1) {
                if (localSQLException1.getErrorCode() == 1403)
                {
                  if (i == 1)
                  {
                    localOracleCallableStatement.close();
                    i++;
                  }
                  else
                  {
                    SQLException localSQLException3 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 1, "Inconsistent catalog view");
                    localSQLException3.fillInStackTrace();
                    throw localSQLException3;
                  }
                }
                else
                {
                  throw localSQLException1;
                }

              }

            default:
              if (localResultSet.next())
              {
                this.isInstanciable = Boolean.valueOf(localResultSet.getString(1).equals("YES"));
                this.supertype = (localResultSet.getString(2) + "." + localResultSet.getString(3));
                j = localResultSet.getInt(4);

                break label411;
              }

              if (i == 2)
              {
                SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 1, "Inconsistent catalog view");
                sqlexception.fillInStackTrace();
                throw sqlexception;
              }

              localResultSet.close();
              if (localOracleCallableStatement != null)
                localOracleCallableStatement.close();
              i++;
            }
          }

        }
        finally
        {
          label411: if (localResultSet != null) {
            localResultSet.close();
          }
          if (localPreparedStatement != null) {
            localPreparedStatement.close();
          }
          if (localOracleCallableStatement != null) {
            localOracleCallableStatement.close();
          }
        }
        this.numLocalAttrs = j;
      }
    }
  }

  private void initMetaData1_pre_9_0()
    throws SQLException
  {
    synchronized (this.connection)
    {
      this.isInstanciable = Boolean.TRUE;
      this.supertype = "";
      this.numLocalAttrs = 0;
    }
  }

  private void initMetaData2()
    throws SQLException
  {
    int i = this.connection.getVersionNumber();

    if (i >= 9000) {
      initMetaData2_9_0();
    }
    else
      initMetaData2_pre_9_0();
  }

  private void initMetaData2_9_0()
    throws SQLException
  {
    synchronized (this.connection)
    {
      if (this.subtypes == null)
      {
        PreparedStatement localPreparedStatement = null;
        ResultSet localResultSet = null;
        String[] arrayOfString = null;
        try
        {
          localPreparedStatement = this.connection.prepareStatement("select owner, type_name from all_types where supertype_name = :1 and supertype_owner = :2");

          localPreparedStatement.setString(1, getTypeName());
          localPreparedStatement.setString(2, getSchemaName());

          localResultSet = localPreparedStatement.executeQuery();

          Vector localVector = new Vector();

          while (localResultSet.next()) {
            localVector.addElement(localResultSet.getString(1) + "." + localResultSet.getString(2));
          }
          arrayOfString = new String[localVector.size()];

          for (int i = 0; i < arrayOfString.length; i++) {
            arrayOfString[i] = ((String)localVector.elementAt(i));
          }
          localVector.removeAllElements();

          localVector = null;
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
        this.subtypes = arrayOfString;
      }
    }
  }

  private void initMetaData2_pre_9_0()
    throws SQLException
  {
    this.subtypes = new String[0];
  }

  private void initMetaData3()
    throws SQLException
  {
    synchronized (this.connection)
    {
      if (this.attrJavaNames == null)
      {
        String[] arrayOfString = null;
        PreparedStatement localPreparedStatement = null;
        ResultSet localResultSet = null;
        try
        {
          localPreparedStatement = this.connection.prepareStatement("select EXTERNAL_ATTR_NAME from all_sqlj_type_attrs where owner = :1 and type_name = :2");

          localPreparedStatement.setString(1, getSchemaName());
          localPreparedStatement.setString(2, getTypeName());

          localResultSet = localPreparedStatement.executeQuery();
          arrayOfString = new String[getOracleTypeADT().getAttrTypes().length];

          for (int i = 0; localResultSet.next(); i++)
            arrayOfString[i] = localResultSet.getString(1);
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
        this.attrJavaNames = arrayOfString;
      }
    }
  }

  String tagName()
  {
    return "StructDescriptor";
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