package oracle.jdbc.driver;

import java.sql.SQLException;

class AutoKeyInfo extends OracleResultSetMetaData
{
  String originalSql;
  String newSql;
  String tableName;
  byte sqlKind = 0;
  int sqlParserParamCount;
  String[] sqlParserParamList;
  boolean useNamedParameter;
  int current_argument;
  String[] columnNames;
  int[] columnIndexes;
  int numColumns;
  String[] tableColumnNames;
  int[] tableColumnTypes;
  int[] tableMaxLengths;
  boolean[] tableNullables;
  short[] tableFormOfUses;
  int[] tablePrecisions;
  int[] tableScales;
  String[] tableTypeNames;
  int autoKeyType;
  static final int KEYFLAG = 0;
  static final int COLUMNAME = 1;
  static final int COLUMNINDEX = 2;
  static final char QMARK = '?';
  int[] returnTypes;
  Accessor[] returnAccessors;
  private static final ThreadLocal<OracleSql> SQL_PARSER = new ThreadLocal() {
    protected OracleSql initialValue() {
      return new OracleSql(null);
    }
  };

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  AutoKeyInfo(String paramString)
  {
    this.originalSql = paramString;
    this.autoKeyType = 0;
  }

  AutoKeyInfo(String paramString, String[] paramArrayOfString)
  {
    this.originalSql = paramString;
    this.columnNames = paramArrayOfString;
    this.autoKeyType = 1;
  }

  AutoKeyInfo(String paramString, int[] paramArrayOfInt)
  {
    this.originalSql = paramString;
    this.columnIndexes = paramArrayOfInt;
    this.autoKeyType = 2;
  }

  private void parseSql() throws SQLException
  {
    if (this.originalSql == null)
    {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject = (OracleSql)SQL_PARSER.get();
    ((OracleSql)localObject).initialize(this.originalSql);

    this.sqlKind = ((OracleSql)localObject).getSqlKind();

    if (this.sqlKind == 4)
    {
      this.sqlParserParamCount = ((OracleSql)localObject).getParameterCount();
      this.sqlParserParamList = ((OracleSql)localObject).getParameterList();

      if (this.sqlParserParamList == OracleSql.EMPTY_LIST) {
        this.useNamedParameter = false;
      }
      else {
        this.useNamedParameter = true;

        this.current_argument = this.sqlParserParamCount;
      }
    }
  }

  private String generateUniqueNamedParameter()
  {
    int i;
    String str;
    do
    {
      i = 0;
      str = Integer.toString(++this.current_argument).intern();

      for (int j = 0; j < this.sqlParserParamCount; j++)
      {
        if (this.sqlParserParamList[j] == str)
        {
          i = 1;
          break;
        }
      }
    }
    while (i != 0);

    return new StringBuilder().append(":").append(str).toString();
  }

  String getNewSql()
    throws SQLException
  {
    try
    {
      if (this.newSql != null) return this.newSql;

      if (this.sqlKind == 0) parseSql();

      switch (this.autoKeyType)
      {
      case 0:
        this.newSql = new StringBuilder().append(this.originalSql).append(" RETURNING ROWID INTO ").append(this.useNamedParameter ? generateUniqueNamedParameter() : Character.valueOf('?')).toString();

        this.returnTypes = new int[1];
        this.returnTypes[0] = 104;
        break;
      case 1:
        getNewSqlByColumnName();
        break;
      case 2:
        getNewSqlByColumnIndexes();
      }

      this.sqlKind = 0;
      this.sqlParserParamList = null;
      return this.newSql;
    }
    catch (Exception localException)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localException);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  private String getNewSqlByColumnName()
    throws SQLException
  {
    this.returnTypes = new int[this.columnNames.length];

    this.columnIndexes = new int[this.columnNames.length];

    StringBuffer localStringBuffer = new StringBuffer(this.originalSql);
    localStringBuffer.append(" RETURNING ");

    for (int j = 0; j < this.columnNames.length; j++)
    {
      int i = getReturnParamTypeCode(j, this.columnNames[j], this.columnIndexes);
      this.returnTypes[j] = i;

      localStringBuffer.append(this.columnNames[j]);

      if (j < this.columnNames.length - 1) localStringBuffer.append(", ");
    }

    localStringBuffer.append(" INTO ");

    for (int j = 0; j < this.columnNames.length - 1; j++)
    {
      localStringBuffer.append(new StringBuilder().append(this.useNamedParameter ? generateUniqueNamedParameter() : Character.valueOf('?')).append(", ").toString());
    }

    localStringBuffer.append(this.useNamedParameter ? generateUniqueNamedParameter() : Character.valueOf('?'));

    this.newSql = new String(localStringBuffer);
    return this.newSql;
  }

  private String getNewSqlByColumnIndexes() throws SQLException
  {
    this.returnTypes = new int[this.columnIndexes.length];

    StringBuffer localStringBuffer = new StringBuffer(this.originalSql);
    localStringBuffer.append(" RETURNING ");

    for (int j = 0; j < this.columnIndexes.length; j++)
    {
      int k = this.columnIndexes[j] - 1;
      if ((k < 0) || (k > this.tableColumnNames.length))
      {
        SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68);
        localSQLException.fillInStackTrace();
        throw localSQLException;
      }

      int i = this.tableColumnTypes[k];
      String str = this.tableColumnNames[k];
      this.returnTypes[j] = i;

      localStringBuffer.append(str);

      if (j < this.columnIndexes.length - 1) localStringBuffer.append(", ");
    }

    localStringBuffer.append(" INTO ");

    for (int j = 0; j < this.columnIndexes.length - 1; j++)
    {
      localStringBuffer.append(new StringBuilder().append(this.useNamedParameter ? generateUniqueNamedParameter() : Character.valueOf('?')).append(", ").toString());
    }

    localStringBuffer.append(this.useNamedParameter ? generateUniqueNamedParameter() : Character.valueOf('?'));

    this.newSql = new String(localStringBuffer);
    return this.newSql;
  }

  private final int getReturnParamTypeCode(int paramInt, String paramString, int[] paramArrayOfInt)
    throws SQLException
  {
    for (int i = 0; i < this.tableColumnNames.length; i++)
    {
      if (paramString.equalsIgnoreCase(this.tableColumnNames[i]))
      {
        paramArrayOfInt[paramInt] = (i + 1);
        return this.tableColumnTypes[i];
      }

    }

    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  final boolean isInsertSqlStmt()
    throws SQLException
  {
    if (this.sqlKind == 0) {
      parseSql();
    }
    return this.sqlKind == 4;
  }

  String getTableName() throws SQLException
  {
    if (this.tableName != null) return this.tableName;

    String str = this.originalSql.trim().toUpperCase();

    int i = str.indexOf("INSERT");
    i = str.indexOf("INTO", i);

    if (i < 0)
    {
      SQLException localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68);
      localSQLException1.fillInStackTrace();
      throw localSQLException1;
    }

    int j = str.length();
    int k = i + 5;

    while ((k < j) && (str.charAt(k) == ' ')) k++;

    if (k >= j)
    {
      SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68);
      localSQLException2.fillInStackTrace();
      throw localSQLException2;
    }

    int m = k + 1;

    while ((m < j) && (str.charAt(m) != ' ') && (str.charAt(m) != '(')) m++;

    if (k == m - 1)
    {
      SQLException localSQLException3 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68);
      localSQLException3.fillInStackTrace();
      throw localSQLException3;
    }

    this.tableName = str.substring(k, m);

    return this.tableName;
  }

  void allocateSpaceForDescribedData(int paramInt) throws SQLException
  {
    this.numColumns = paramInt;

    this.tableColumnNames = new String[paramInt];
    this.tableColumnTypes = new int[paramInt];
    this.tableMaxLengths = new int[paramInt];
    this.tableNullables = new boolean[paramInt];
    this.tableFormOfUses = new short[paramInt];
    this.tablePrecisions = new int[paramInt];
    this.tableScales = new int[paramInt];
    this.tableTypeNames = new String[paramInt];
  }

  void fillDescribedData(int paramInt1, String paramString1, int paramInt2, int paramInt3, boolean paramBoolean, short paramShort, int paramInt4, int paramInt5, String paramString2)
    throws SQLException
  {
    this.tableColumnNames[paramInt1] = paramString1;
    this.tableColumnTypes[paramInt1] = paramInt2;
    this.tableMaxLengths[paramInt1] = paramInt3;
    this.tableNullables[paramInt1] = paramBoolean;
    this.tableFormOfUses[paramInt1] = paramShort;
    this.tablePrecisions[paramInt1] = paramInt4;
    this.tableScales[paramInt1] = paramInt5;
    this.tableTypeNames[paramInt1] = paramString2;
  }

  void initMetaData(OracleReturnResultSet paramOracleReturnResultSet) throws SQLException
  {
    if (this.returnAccessors != null) return;

    this.returnAccessors = paramOracleReturnResultSet.returnAccessors;

    switch (this.autoKeyType)
    {
    case 0:
      initMetaDataKeyFlag();
      break;
    case 1:
    case 2:
      initMetaDataColumnIndexes();
    }
  }

  void initMetaDataKeyFlag()
    throws SQLException
  {
    this.returnAccessors[0].columnName = "ROWID";
    this.returnAccessors[0].describeType = 104;
    this.returnAccessors[0].describeMaxLength = 4;
    this.returnAccessors[0].nullable = true;
    this.returnAccessors[0].precision = 0;
    this.returnAccessors[0].scale = 0;
    this.returnAccessors[0].formOfUse = 0;
  }

  void initMetaDataColumnIndexes()
    throws SQLException
  {
    for (int j = 0; j < this.returnAccessors.length; j++)
    {
      Accessor localAccessor = this.returnAccessors[j];
      int i = this.columnIndexes[j] - 1;

      localAccessor.columnName = this.tableColumnNames[i];
      localAccessor.describeType = this.tableColumnTypes[i];
      localAccessor.describeMaxLength = this.tableMaxLengths[i];
      localAccessor.nullable = this.tableNullables[i];
      localAccessor.precision = this.tablePrecisions[i];
      localAccessor.scale = this.tablePrecisions[i];
      localAccessor.formOfUse = this.tableFormOfUses[i];
    }
  }

  int getValidColumnIndex(int paramInt)
    throws SQLException
  {
    if ((paramInt <= 0) || (paramInt > this.returnAccessors.length))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    return paramInt - 1;
  }

  public int getColumnCount() throws SQLException
  {
    return this.returnAccessors.length;
  }

  public String getColumnName(int paramInt)
    throws SQLException
  {
    if ((paramInt <= 0) || (paramInt > this.returnAccessors.length))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    return this.returnAccessors[(paramInt - 1)].columnName;
  }

  public String getTableName(int paramInt)
    throws SQLException
  {
    if ((paramInt <= 0) || (paramInt > this.returnAccessors.length))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    return getTableName();
  }

  Accessor[] getDescription() throws SQLException
  {
    return this.returnAccessors;
  }
}