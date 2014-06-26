package oracle.jdbc.oracore;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.driver.DatabaseError;
import oracle.jdbc.internal.OracleConnection;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import oracle.sql.Datum;
import oracle.sql.SQLName;
import oracle.sql.StructDescriptor;
import oracle.sql.TypeDescriptor;

public class OracleTypeCOLLECTION extends OracleTypeADT
  implements Serializable
{
  static final long serialVersionUID = -7279638692691669378L;
  public static final int TYPE_PLSQL_INDEX_TABLE = 1;
  public static final int TYPE_NESTED_TABLE = 2;
  public static final int TYPE_VARRAY = 3;
  int userCode = 0;
  long maxSize = 0L;
  OracleType elementType = null;
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
  static final String[] sqlString = { "SELECT ELEM_TYPE_NAME, ELEM_TYPE_OWNER FROM USER_COLL_TYPES WHERE TYPE_NAME = :1", "SELECT ELEM_TYPE_NAME, ELEM_TYPE_OWNER FROM USER_COLL_TYPES WHERE TYPE_NAME in (SELECT TABLE_NAME FROM USER_SYNONYMS START WITH SYNONYM_NAME = :1 CONNECT BY PRIOR TABLE_NAME = SYNONYM_NAME UNION SELECT :1 FROM DUAL) ", "SELECT ELEM_TYPE_NAME, ELEM_TYPE_OWNER FROM USER_COLL_TYPES WHERE TYPE_NAME in (SELECT TABLE_NAME FROM USER_SYNONYMS START WITH SYNONYM_NAME = :1 CONNECT BY NOCYCLE PRIOR TABLE_NAME = SYNONYM_NAME UNION SELECT :1 FROM DUAL) ", "SELECT ELEM_TYPE_NAME, ELEM_TYPE_OWNER FROM USER_COLL_TYPES WHERE TYPE_NAME IN (SELECT TABLE_NAME FROM ALL_SYNONYMS START WITH SYNONYM_NAME = :1 AND  OWNER = 'PUBLIC' CONNECT BY PRIOR TABLE_NAME = SYNONYM_NAME AND TABLE_OWNER = OWNER UNION SELECT :2  FROM DUAL) ", "SELECT ELEM_TYPE_NAME, ELEM_TYPE_OWNER FROM USER_COLL_TYPES WHERE TYPE_NAME IN (SELECT TABLE_NAME FROM ALL_SYNONYMS START WITH SYNONYM_NAME = :1 AND  OWNER = 'PUBLIC' CONNECT BY NOCYCLE PRIOR TABLE_NAME = SYNONYM_NAME AND TABLE_OWNER = OWNER UNION SELECT :2  FROM DUAL) ", "SELECT ELEM_TYPE_NAME, ELEM_TYPE_OWNER FROM ALL_COLL_TYPES WHERE TYPE_NAME IN (SELECT TABLE_NAME FROM USER_SYNONYMS START WITH SYNONYM_NAME = :tname CONNECT BY PRIOR TABLE_NAME = SYNONYM_NAME UNION SELECT :tname FROM DUAL)", "SELECT ELEM_TYPE_NAME, ELEM_TYPE_OWNER FROM ALL_COLL_TYPES WHERE TYPE_NAME IN (SELECT TABLE_NAME FROM USER_SYNONYMS START WITH SYNONYM_NAME = :tname CONNECT BY NOCYCLE PRIOR TABLE_NAME = SYNONYM_NAME UNION SELECT :tname FROM DUAL)", "SELECT ELEM_TYPE_NAME, ELEM_TYPE_OWNER FROM ALL_COLL_TYPES WHERE OWNER = :1 AND TYPE_NAME = :2", "SELECT ELEM_TYPE_NAME, ELEM_TYPE_OWNER FROM ALL_COLL_TYPES WHERE OWNER = (SELECT TABLE_OWNER FROM ALL_SYNONYMS WHERE SYNONYM_NAME=:1) AND TYPE_NAME = (SELECT TABLE_NAME FROM ALL_SYNONYMS WHERE SYNONYM_NAME=:2) ", "DECLARE /*+RULE*/  the_owner VARCHAR2(100);   the_type  VARCHAR2(100); begin  SELECT TABLE_NAME, TABLE_OWNER INTO THE_TYPE, THE_OWNER  FROM ALL_SYNONYMS  WHERE TABLE_NAME IN (SELECT TYPE_NAME FROM ALL_TYPES)  START WITH SYNONYM_NAME = :1 AND OWNER = 'PUBLIC'  CONNECT BY PRIOR TABLE_NAME = SYNONYM_NAME AND TABLE_OWNER = OWNER; OPEN :2 FOR SELECT ELEM_TYPE_NAME, ELEM_TYPE_OWNER FROM ALL_COLL_TYPES  WHERE TYPE_NAME = THE_TYPE and OWNER = THE_OWNER; END;", "DECLARE /*+RULE*/  the_owner VARCHAR2(100);   the_type  VARCHAR2(100); begin  SELECT TABLE_NAME, TABLE_OWNER INTO THE_TYPE, THE_OWNER  FROM ALL_SYNONYMS  WHERE TABLE_NAME IN (SELECT TYPE_NAME FROM ALL_TYPES)  START WITH SYNONYM_NAME = :1 AND OWNER = 'PUBLIC'  CONNECT BY NOCYCLE PRIOR TABLE_NAME = SYNONYM_NAME AND TABLE_OWNER = OWNER; OPEN :2 FOR SELECT ELEM_TYPE_NAME, ELEM_TYPE_OWNER FROM ALL_COLL_TYPES  WHERE TYPE_NAME = THE_TYPE and OWNER = THE_OWNER; END;" };

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  public OracleTypeCOLLECTION(String paramString, OracleConnection paramOracleConnection)
    throws SQLException
  {
    super(paramString, paramOracleConnection);
  }

  public OracleTypeCOLLECTION(OracleTypeADT paramOracleTypeADT, int paramInt, OracleConnection paramOracleConnection)
    throws SQLException
  {
    super(paramOracleTypeADT, paramInt, paramOracleConnection);
  }

  public OracleTypeCOLLECTION(SQLName paramSQLName, byte[] paramArrayOfByte1, int paramInt, byte[] paramArrayOfByte2, OracleConnection paramOracleConnection)
    throws SQLException
  {
    super(paramSQLName, paramArrayOfByte1, paramInt, paramArrayOfByte2, paramOracleConnection);
  }

  public Datum toDatum(Object paramObject, OracleConnection paramOracleConnection)
    throws SQLException
  {
    if (paramObject != null)
    {
      if ((paramObject instanceof ARRAY)) {
        return (ARRAY)paramObject;
      }

      ArrayDescriptor localArrayDescriptor = createArrayDescriptor();

      return new ARRAY(localArrayDescriptor, this.connection, paramObject);
    }

    return null;
  }

  public int getTypeCode()
  {
    return 2003;
  }

  public boolean isInHierarchyOf(OracleType paramOracleType)
    throws SQLException
  {
    if (paramOracleType == null) {
      return false;
    }
    if (paramOracleType == this) {
      return true;
    }
    if (paramOracleType.getClass() != getClass()) {
      return false;
    }
    return paramOracleType.getTypeDescriptor().getName().equals(this.descriptor.getName());
  }

  public boolean isInHierarchyOf(StructDescriptor paramStructDescriptor)
    throws SQLException
  {
    return false;
  }

  public boolean isObjectType()
  {
    return false;
  }

  public void parseTDSrec(TDSReader paramTDSReader)
    throws SQLException
  {
    long l = paramTDSReader.readLong();

    this.maxSize = paramTDSReader.readLong();

    this.userCode = paramTDSReader.readByte();

    paramTDSReader.addSimplePatch(l, this);
  }

  public Datum unlinearize(byte[] paramArrayOfByte, long paramLong, Datum paramDatum, int paramInt, Map paramMap)
    throws SQLException
  {
    return unlinearize(paramArrayOfByte, paramLong, paramDatum, 1L, -1, paramInt, paramMap);
  }

  public Datum unlinearize(byte[] paramArrayOfByte, long paramLong1, Datum paramDatum, long paramLong2, int paramInt1, int paramInt2, Map paramMap)
    throws SQLException
  {
    OracleConnection localOracleConnection = getConnection();
    Datum localDatum = null;

    if (localOracleConnection == null)
    {
      localDatum = unlinearizeInternal(paramArrayOfByte, paramLong1, paramDatum, paramLong2, paramInt1, paramInt2, paramMap);
    }
    else
    {
      synchronized (localOracleConnection)
      {
        localDatum = unlinearizeInternal(paramArrayOfByte, paramLong1, paramDatum, paramLong2, paramInt1, paramInt2, paramMap);
      }

    }

    return localDatum;
  }

  public synchronized Datum unlinearizeInternal(byte[] paramArrayOfByte, long paramLong1, Datum paramDatum, long paramLong2, int paramInt1, int paramInt2, Map paramMap)
    throws SQLException
  {
    if (paramArrayOfByte == null) {
      return null;
    }

    PickleContext localPickleContext = new PickleContext(paramArrayOfByte, paramLong1);

    return unpickle81(localPickleContext, (ARRAY)paramDatum, paramLong2, paramInt1, 1, paramInt2, paramMap);
  }

  public synchronized boolean isInlineImage(byte[] paramArrayOfByte, int paramInt)
    throws SQLException
  {
    if (paramArrayOfByte == null) {
      return false;
    }

    if (PickleContext.isCollectionImage_pctx(paramArrayOfByte[paramInt]))
      return true;
    if (PickleContext.isDegenerateImage_pctx(paramArrayOfByte[paramInt])) {
      return false;
    }

    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 1, "Image is not a collection image");
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  protected int pickle81(PickleContext paramPickleContext, Datum paramDatum)
    throws SQLException
  {
    ARRAY localARRAY = (ARRAY)paramDatum;

    boolean bool = localARRAY.hasDataSeg();
    int i = 0;
    int j = paramPickleContext.offset() + 2;

    if (bool)
    {
      if (!this.metaDataInitialized) {
        copy_properties((OracleTypeCOLLECTION)localARRAY.getDescriptor().getPickler());
      }
      Datum[] arrayOfDatum = localARRAY.getOracleArray();

      if (this.userCode == 3)
      {
        if (arrayOfDatum.length > this.maxSize)
        {
          SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 71, null);
          localSQLException.fillInStackTrace();
          throw localSQLException;
        }
      }

      i += paramPickleContext.writeCollImageHeader(arrayOfDatum.length, this.typeVersion);

      for (int k = 0; k < arrayOfDatum.length; k++)
      {
        if (arrayOfDatum[k] == null)
          i += paramPickleContext.writeElementNull();
        else {
          i += this.elementType.pickle81(paramPickleContext, arrayOfDatum[k]);
        }
        String str = "idx=" + k + " is " + arrayOfDatum[k];
      }

    }
    else
    {
      i += paramPickleContext.writeCollImageHeader(localARRAY.getLocator());
    }

    paramPickleContext.patchImageLen(j, i);

    return i;
  }

  ARRAY unpickle81(PickleContext paramPickleContext, ARRAY paramARRAY, int paramInt1, int paramInt2, Map paramMap)
    throws SQLException
  {
    return unpickle81(paramPickleContext, paramARRAY, 1L, -1, paramInt1, paramInt2, paramMap);
  }

  ARRAY unpickle81(PickleContext paramPickleContext, ARRAY paramARRAY, long paramLong, int paramInt1, int paramInt2, int paramInt3, Map paramMap)
    throws SQLException
  {
    ARRAY localARRAY = paramARRAY;

    if (localARRAY == null)
    {
      ArrayDescriptor localArrayDescriptor = createArrayDescriptor();

      localARRAY = new ARRAY(localArrayDescriptor, (byte[])null, this.connection);
    }

    if (unpickle81ImgHeader(paramPickleContext, localARRAY, paramInt2, paramInt3))
    {
      if ((paramLong == 1L) && (paramInt1 == -1))
        unpickle81ImgBody(paramPickleContext, localARRAY, paramInt3, paramMap);
      else {
        unpickle81ImgBody(paramPickleContext, localARRAY, paramLong, paramInt1, paramInt3, paramMap);
      }
    }

    return localARRAY;
  }

  boolean unpickle81ImgHeader(PickleContext paramPickleContext, ARRAY paramARRAY, int paramInt1, int paramInt2)
    throws SQLException
  {
    int i = 1;

    if (paramInt1 == 3)
    {
      paramARRAY.setImage(paramPickleContext.image(), paramPickleContext.absoluteOffset(), 0L);
    }

    byte b = paramPickleContext.readByte();
    SQLException localSQLException;
    if (!PickleContext.is81format(b))
    {
      localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 1, "Image is not in 8.1 format");
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if (!PickleContext.hasPrefix(b))
    {
      localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 1, "Image has no prefix segment");
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if (PickleContext.isCollectionImage_pctx(b)) {
      i = 1;
    } else if (PickleContext.isDegenerateImage_pctx(b)) {
      i = 0;
    }
    else {
      localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 1, "Image is not a collection image");
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    paramPickleContext.readByte();

    if (paramInt1 == 9)
    {
      paramPickleContext.skipBytes(paramPickleContext.readLength(true) - 2);

      return false;
    }
    if (paramInt1 == 3)
    {
      long l = paramPickleContext.readLength();

      paramARRAY.setImageLength(l);
      paramPickleContext.skipTo(paramARRAY.getImageOffset() + l);

      return false;
    }

    paramPickleContext.skipLength();

    int j = paramPickleContext.readLength();

    paramARRAY.setPrefixFlag(paramPickleContext.readByte());

    if (paramARRAY.isInline())
    {
      paramPickleContext.readDataValue(j - 1);
    }
    else
    {
      paramARRAY.setLocator(paramPickleContext.readDataValue(j - 1));
    }

    return paramARRAY.isInline();
  }

  void unpickle81ImgBody(PickleContext paramPickleContext, ARRAY paramARRAY, long paramLong, int paramInt1, int paramInt2, Map paramMap)
    throws SQLException
  {
    paramPickleContext.readByte();

    int i = paramPickleContext.readLength();

    paramARRAY.setLength(i);

    if (paramInt2 == 0) {
      return;
    }

    int j = (int)getAccessLength(i, paramLong, paramInt1);
    int k = ArrayDescriptor.getCacheStyle(paramARRAY) == 1 ? 1 : 0;

    if ((paramLong > 1L) && (j > 0))
    {
      long l1 = paramARRAY.getLastIndex();
      long l2;
      if (l1 < paramLong)
      {
        if (l1 > 0L)
          paramPickleContext.skipTo(paramARRAY.getLastOffset());
        else {
          l1 = 1L;
        }
        if (k != 0)
        {
          for (l2 = l1; l2 < paramLong; l2 += 1L)
          {
            paramARRAY.setIndexOffset(l2, paramPickleContext.offset());
            this.elementType.unpickle81rec(paramPickleContext, 9, null);
          }
        }
        else
        {
          for (l2 = l1; l2 < paramLong; l2 += 1L)
            this.elementType.unpickle81rec(paramPickleContext, 9, null);
        }
      }
      else if (l1 > paramLong)
      {
        l2 = paramARRAY.getOffset(paramLong);

        if (l2 != -1L)
        {
          paramPickleContext.skipTo(l2);
        }
        else
        {
          int m;
          if (k != 0)
          {
            for (m = 1; m < paramLong; m++)
            {
              paramARRAY.setIndexOffset(m, paramPickleContext.offset());
              this.elementType.unpickle81rec(paramPickleContext, 9, null);
            }
          }
          else
          {
            for (m = 1; m < paramLong; m++)
              this.elementType.unpickle81rec(paramPickleContext, 9, null);
          }
        }
      }
      else {
        paramPickleContext.skipTo(paramARRAY.getLastOffset());
      }
      paramARRAY.setLastIndexOffset(paramLong, paramPickleContext.offset());
    }

    unpickle81ImgBodyElements(paramPickleContext, paramARRAY, (int)paramLong, j, paramInt2, paramMap);
  }

  void unpickle81ImgBody(PickleContext paramPickleContext, ARRAY paramARRAY, int paramInt, Map paramMap)
    throws SQLException
  {
    paramPickleContext.readByte();

    int i = paramPickleContext.readLength();

    paramARRAY.setLength(i);

    if (paramInt == 0) {
      return;
    }

    unpickle81ImgBodyElements(paramPickleContext, paramARRAY, 1, i, paramInt, paramMap);
  }

  private void unpickle81ImgBodyElements(PickleContext paramPickleContext, ARRAY paramARRAY, int paramInt1, int paramInt2, int paramInt3, Map paramMap)
    throws SQLException
  {
    int i = ArrayDescriptor.getCacheStyle(paramARRAY) == 1 ? 1 : 0;
    Datum[] localObject;
    int j;
    switch (paramInt3)
    {
    case 1:
      localObject = new Datum[paramInt2];

      if (i != 0)
      {
        for (j = 0; j < paramInt2; j++)
        {
          paramARRAY.setIndexOffset(paramInt1 + j, paramPickleContext.offset());

          localObject[j] = ((Datum)this.elementType.unpickle81rec(paramPickleContext, paramInt3, paramMap));
        }

      }
      else
      {
        for (j = 0; j < paramInt2; j++) {
          localObject[j] = ((Datum)this.elementType.unpickle81rec(paramPickleContext, paramInt3, paramMap));
        }
      }

      paramARRAY.setDatumArray((Datum[])localObject);

      break;
    case 2:
      localObject = (Datum[])ArrayDescriptor.makeJavaArray(paramInt2, this.elementType.getTypeCode());

      if (i != 0)
      {
        for (j = 0; j < paramInt2; j++)
        {
          paramARRAY.setIndexOffset(paramInt1 + j, paramPickleContext.offset());

          localObject[j] = (Datum)this.elementType.unpickle81rec(paramPickleContext, paramInt3, paramMap);
        }
      }
      else
      {
        for (j = 0; j < paramInt2; j++) {
          localObject[j] = (Datum)this.elementType.unpickle81rec(paramPickleContext, paramInt3, paramMap);
        }
      }
      paramARRAY.setObjArray(localObject);

      break;
    case 4:
    case 5:
    case 6:
    case 7:
    case 8:
      if (((this.elementType instanceof OracleTypeNUMBER)) || ((this.elementType instanceof OracleTypeFLOAT)))
      {
        paramARRAY.setObjArray(OracleTypeNUMBER.unpickle81NativeArray(paramPickleContext, 1L, paramInt2, paramInt3));
      }
      else
      {
        SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 23, "This feature is limited to numeric collection");
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }

      break;
    case 3:
    default:
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68, "Invalid conversion type " + this.elementType);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    paramARRAY.setLastIndexOffset(paramInt1 + paramInt2, paramPickleContext.offset());
  }

  private void initCollElemTypeName()
    throws SQLException
  {
    if (this.connection == null)
      return;
    synchronized (this.connection) {
      if (this.sqlName == null) {
        getFullName();
      }
      CallableStatement localCallableStatement = null;
      PreparedStatement localPreparedStatement = null;
      ResultSet localResultSet = null;
      try {
        int i = this.sqlName.getSchema().equalsIgnoreCase(this.connection.getDefaultSchemaNameForNamedTypes()) ? 0 : 7;

        while (i != 11)
        {
          switch (i)
          {
          case 0:
            localPreparedStatement = this.connection.prepareStatement(getSqlHint() + sqlString[i]);
            localPreparedStatement.setString(1, this.sqlName.getSimpleName());
            localPreparedStatement.setFetchSize(1);
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
            localPreparedStatement.setFetchSize(1);
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
            localPreparedStatement.setFetchSize(1);
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
            localPreparedStatement.setFetchSize(1);
            localResultSet = localPreparedStatement.executeQuery();
            i = 8;
            break;
          case 7:
            localPreparedStatement = this.connection.prepareStatement(getSqlHint() + sqlString[i]);
            localPreparedStatement.setString(1, this.sqlName.getSchema());
            localPreparedStatement.setString(2, this.sqlName.getSimpleName());
            localPreparedStatement.setFetchSize(1);
            localResultSet = localPreparedStatement.executeQuery();
            i = 8;
            break;
          case 8:
            localPreparedStatement = this.connection.prepareStatement(getSqlHint() + sqlString[i]);
            localPreparedStatement.setString(1, this.sqlName.getSimpleName());
            localPreparedStatement.setString(2, this.sqlName.getSimpleName());
            localPreparedStatement.setFetchSize(1);
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

          if (localResultSet.next())
          {
            if (this.attrTypeNames == null) {
              this.attrTypeNames = new String[1];
            }
            this.attrTypeNames[0] = (localResultSet.getString(2) + "." + localResultSet.getString(1));
            i = 11;
          } else if (i == 11)
          {
            SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 1);
            localSQLException.fillInStackTrace();
            throw localSQLException;
          }
        }
        while (i != 11);
      }
      finally {
        if (localResultSet != null)
          localResultSet.close();
        if (localPreparedStatement != null)
          localPreparedStatement.close();
        if (localCallableStatement != null)
          localCallableStatement.close();
      }
    }
  }

  public String getAttributeName(int paramInt)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 1);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public String getAttributeName(int paramInt, boolean paramBoolean)
    throws SQLException
  {
    return getAttributeName(paramInt);
  }

  public String getAttributeType(int paramInt)
    throws SQLException
  {
    if (paramInt != 1)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if (this.sqlName == null) {
      getFullName();
    }
    if (this.attrTypeNames == null) {
      initCollElemTypeName();
    }
    return this.attrTypeNames[0];
  }

  public String getAttributeType(int paramInt, boolean paramBoolean)
    throws SQLException
  {
    if (paramBoolean) {
      return getAttributeType(paramInt);
    }

    if (paramInt != 1)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if ((this.sqlName != null) && (this.attrTypeNames != null)) {
      return this.attrTypeNames[0];
    }
    return null;
  }

  public int getNumAttrs()
    throws SQLException
  {
    return 0;
  }

  public OracleType getAttrTypeAt(int paramInt)
    throws SQLException
  {
    return null;
  }

  ArrayDescriptor createArrayDescriptor()
    throws SQLException
  {
    return new ArrayDescriptor(this, this.connection);
  }

  ArrayDescriptor createArrayDescriptorWithItsOwnTree()
    throws SQLException
  {
    if (this.descriptor == null)
    {
      if ((this.sqlName == null) && (getFullName(false) == null))
      {
        this.descriptor = new ArrayDescriptor(this, this.connection);
      }
      else
      {
        this.descriptor = ArrayDescriptor.createDescriptor(this.sqlName, this.connection);
      }
    }

    return (ArrayDescriptor)this.descriptor;
  }

  public OracleType getElementType()
    throws SQLException
  {
    return this.elementType;
  }

  public int getUserCode()
    throws SQLException
  {
    return this.userCode;
  }

  public long getMaxLength()
    throws SQLException
  {
    return this.maxSize;
  }

  private long getAccessLength(long paramLong1, long paramLong2, int paramInt)
    throws SQLException
  {
    if (paramLong2 > paramLong1) {
      return 0L;
    }
    if (paramInt < 0)
    {
      return paramLong1 - paramLong2 + 1L;
    }

    return Math.min(paramLong1 - paramLong2 + 1L, paramInt);
  }

  private void writeObject(ObjectOutputStream paramObjectOutputStream)
    throws IOException
  {
    paramObjectOutputStream.writeInt(this.userCode);
    paramObjectOutputStream.writeLong(this.maxSize);
    paramObjectOutputStream.writeObject(this.elementType);
  }

  private void readObject(ObjectInputStream paramObjectInputStream)
    throws IOException, ClassNotFoundException
  {
    this.userCode = paramObjectInputStream.readInt();
    this.maxSize = paramObjectInputStream.readLong();
    this.elementType = ((OracleType)paramObjectInputStream.readObject());
  }

  public void setConnection(OracleConnection paramOracleConnection)
    throws SQLException
  {
    this.connection = paramOracleConnection;

    this.elementType.setConnection(paramOracleConnection);
  }

  public void initMetadataRecursively()
    throws SQLException
  {
    initMetadata(this.connection);
    if (this.elementType != null) this.elementType.initMetadataRecursively();
  }

  public void initChildNamesRecursively(Map paramMap)
    throws SQLException
  {
    TypeTreeElement localTypeTreeElement = (TypeTreeElement)paramMap.get(this.sqlName);

    if (this.elementType != null)
    {
      this.elementType.setNames(localTypeTreeElement.getChildSchemaName(0), localTypeTreeElement.getChildTypeName(0));
      this.elementType.initChildNamesRecursively(paramMap);
      this.elementType.cacheDescriptor();
    }
  }

  public void cacheDescriptor()
    throws SQLException
  {
    this.descriptor = ArrayDescriptor.createDescriptor(this);
  }

  public void printXML(PrintWriter paramPrintWriter, int paramInt)
    throws SQLException
  {
    printXML(paramPrintWriter, paramInt, false);
  }

  public void printXML(PrintWriter paramPrintWriter, int paramInt, boolean paramBoolean)
    throws SQLException
  {
    for (int i = 0; i < paramInt; i++) paramPrintWriter.print("  ");
    paramPrintWriter.println("<OracleTypeCOLLECTION sqlName=\"" + this.sqlName + "\" " + ">");

    if (this.elementType != null)
      this.elementType.printXML(paramPrintWriter, paramInt + 1, paramBoolean);
    for (int i = 0; i < paramInt; i++) paramPrintWriter.print("  ");
    paramPrintWriter.println("</OracleTypeCOLLECTION>");
  }
}