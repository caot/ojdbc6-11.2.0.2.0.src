package oracle.jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.RowIdLifetime;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import oracle.jdbc.driver.DatabaseError;
import oracle.jdbc.driver.OracleSql;
import oracle.jdbc.internal.OracleResultSet;
import oracle.sql.SQLName;

public class OracleDatabaseMetaData
  implements DatabaseMetaData
{
  private static String DRIVER_NAME = "Oracle JDBC driver";
  private static String DRIVER_VERSION = "11.2.0.2.0";
  private static int DRIVER_MAJOR_VERSION = 11;
  private static int DRIVER_MINOR_VERSION = 2;
  private static String LOB_MAXSIZE = "4294967295";
  private static long LOB_MAXLENGTH_32BIT = 4294967295L;
  protected oracle.jdbc.internal.OracleConnection connection;
  int procedureResultUnknown = 0;

  int procedureNoResult = 1;

  int procedureReturnsResult = 2;

  int procedureColumnUnknown = 0;

  int procedureColumnIn = 1;

  int procedureColumnInOut = 2;

  int procedureColumnOut = 4;

  int procedureColumnReturn = 5;

  int procedureColumnResult = 3;

  int procedureNoNulls = 0;

  int procedureNullable = 1;

  int procedureNullableUnknown = 2;

  int columnNoNulls = 0;

  int columnNullable = 1;

  int columnNullableUnknown = 2;
  static final int bestRowTemporary = 0;
  static final int bestRowTransaction = 1;
  static final int bestRowSession = 2;
  static final int bestRowUnknown = 0;
  static final int bestRowNotPseudo = 1;
  static final int bestRowPseudo = 2;
  int versionColumnUnknown = 0;

  int versionColumnNotPseudo = 1;

  int versionColumnPseudo = 2;

  int importedKeyCascade = 0;

  int importedKeyRestrict = 1;

  int importedKeySetNull = 2;

  int typeNoNulls = 0;

  int typeNullable = 1;

  int typeNullableUnknown = 2;

  int typePredNone = 0;

  int typePredChar = 1;

  int typePredBasic = 2;

  int typeSearchable = 3;

  short tableIndexStatistic = 0;

  short tableIndexClustered = 1;

  short tableIndexHashed = 2;

  short tableIndexOther = 3;

  short attributeNoNulls = 0;

  short attributeNullable = 1;

  short attributeNullableUnknown = 2;

  int sqlStateXOpen = 1;

  int sqlStateSQL99 = 2;
  protected static final String sqlWildcardRegex = "^%|^_|[^/]%|[^/]_";
  protected static Pattern sqlWildcardPattern = null;
  protected static final String sqlEscapeRegex = "/";
  protected static Pattern sqlEscapePattern = null;

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  /** @deprecated */
  public OracleDatabaseMetaData(OracleConnection paramOracleConnection)
  {
    this.connection = paramOracleConnection.physicalConnectionWithin();
  }

  public boolean allProceduresAreCallable()
    throws SQLException
  {
    return false;
  }

  public boolean allTablesAreSelectable()
    throws SQLException
  {
    return false;
  }

  public String getURL()
    throws SQLException
  {
    return this.connection.getURL();
  }

  public String getUserName()
    throws SQLException
  {
    return this.connection.getUserName();
  }

  public boolean isReadOnly()
    throws SQLException
  {
    return false;
  }

  public boolean nullsAreSortedHigh()
    throws SQLException
  {
    return false;
  }

  public boolean nullsAreSortedLow()
    throws SQLException
  {
    return true;
  }

  public boolean nullsAreSortedAtStart()
    throws SQLException
  {
    return false;
  }

  public boolean nullsAreSortedAtEnd()
    throws SQLException
  {
    return false;
  }

  public String getDatabaseProductName()
    throws SQLException
  {
    return "Oracle";
  }

  public String getDatabaseProductVersion()
    throws SQLException
  {
    return this.connection.getDatabaseProductVersion();
  }

  public String getDriverName()
    throws SQLException
  {
    return DRIVER_NAME;
  }

  public String getDriverVersion()
    throws SQLException
  {
    return DRIVER_VERSION;
  }

  public int getDriverMajorVersion()
  {
    return DRIVER_MAJOR_VERSION;
  }

  public int getDriverMinorVersion()
  {
    return DRIVER_MINOR_VERSION;
  }

  public boolean usesLocalFiles()
    throws SQLException
  {
    return false;
  }

  public boolean usesLocalFilePerTable()
    throws SQLException
  {
    return false;
  }

  public boolean supportsMixedCaseIdentifiers()
    throws SQLException
  {
    return false;
  }

  public boolean storesUpperCaseIdentifiers()
    throws SQLException
  {
    return true;
  }

  public boolean storesLowerCaseIdentifiers()
    throws SQLException
  {
    return false;
  }

  public boolean storesMixedCaseIdentifiers()
    throws SQLException
  {
    return false;
  }

  public boolean supportsMixedCaseQuotedIdentifiers()
    throws SQLException
  {
    return true;
  }

  public boolean storesUpperCaseQuotedIdentifiers()
    throws SQLException
  {
    return false;
  }

  public boolean storesLowerCaseQuotedIdentifiers()
    throws SQLException
  {
    return false;
  }

  public boolean storesMixedCaseQuotedIdentifiers()
    throws SQLException
  {
    return true;
  }

  public String getIdentifierQuoteString()
    throws SQLException
  {
    return "\"";
  }

  public String getSQLKeywords()
    throws SQLException
  {
    return "ACCESS, ADD, ALTER, AUDIT, CLUSTER, COLUMN, COMMENT, COMPRESS, CONNECT, DATE, DROP, EXCLUSIVE, FILE, IDENTIFIED, IMMEDIATE, INCREMENT, INDEX, INITIAL, INTERSECT, LEVEL, LOCK, LONG, MAXEXTENTS, MINUS, MODE, NOAUDIT, NOCOMPRESS, NOWAIT, NUMBER, OFFLINE, ONLINE, PCTFREE, PRIOR, all_PL_SQL_reserved_ words";
  }

  public String getNumericFunctions()
    throws SQLException
  {
    return "ABS,ACOS,ASIN,ATAN,ATAN2,CEILING,COS,EXP,FLOOR,LOG,LOG10,MOD,PI,POWER,ROUND,SIGN,SIN,SQRT,TAN,TRUNCATE";
  }

  public String getStringFunctions()
    throws SQLException
  {
    return "ASCII,CHAR,CHAR_LENGTH,CHARACTER_LENGTH,CONCAT,LCASE,LENGTH,LTRIM,OCTET_LENGTH,REPLACE,RTRIM,SOUNDEX,SUBSTRING,UCASE";
  }

  public String getSystemFunctions()
    throws SQLException
  {
    return "USER";
  }

  public String getTimeDateFunctions()
    throws SQLException
  {
    return "CURRENT_DATE,CURRENT_TIMESTAMP,CURDATE,EXTRACT,HOUR,MINUTE,MONTH,SECOND,YEAR";
  }

  public String getSearchStringEscape()
    throws SQLException
  {
    return "/";
  }

  public String getExtraNameCharacters()
    throws SQLException
  {
    return "$#";
  }

  public boolean supportsAlterTableWithAddColumn()
    throws SQLException
  {
    return true;
  }

  public boolean supportsAlterTableWithDropColumn()
    throws SQLException
  {
    return false;
  }

  public boolean supportsColumnAliasing()
    throws SQLException
  {
    return true;
  }

  public boolean nullPlusNonNullIsNull()
    throws SQLException
  {
    return true;
  }

  public boolean supportsConvert()
    throws SQLException
  {
    return false;
  }

  public boolean supportsConvert(int paramInt1, int paramInt2)
    throws SQLException
  {
    return false;
  }

  public boolean supportsTableCorrelationNames()
    throws SQLException
  {
    return true;
  }

  public boolean supportsDifferentTableCorrelationNames()
    throws SQLException
  {
    return true;
  }

  public boolean supportsExpressionsInOrderBy()
    throws SQLException
  {
    return true;
  }

  public boolean supportsOrderByUnrelated()
    throws SQLException
  {
    return true;
  }

  public boolean supportsGroupBy()
    throws SQLException
  {
    return true;
  }

  public boolean supportsGroupByUnrelated()
    throws SQLException
  {
    return true;
  }

  public boolean supportsGroupByBeyondSelect()
    throws SQLException
  {
    return true;
  }

  public boolean supportsLikeEscapeClause()
    throws SQLException
  {
    return true;
  }

  public boolean supportsMultipleResultSets()
    throws SQLException
  {
    return false;
  }

  public boolean supportsMultipleTransactions()
    throws SQLException
  {
    return true;
  }

  public boolean supportsNonNullableColumns()
    throws SQLException
  {
    return true;
  }

  public boolean supportsMinimumSQLGrammar()
    throws SQLException
  {
    return true;
  }

  public boolean supportsCoreSQLGrammar()
    throws SQLException
  {
    return true;
  }

  public boolean supportsExtendedSQLGrammar()
    throws SQLException
  {
    return true;
  }

  public boolean supportsANSI92EntryLevelSQL()
    throws SQLException
  {
    return true;
  }

  public boolean supportsANSI92IntermediateSQL()
    throws SQLException
  {
    return false;
  }

  public boolean supportsANSI92FullSQL()
    throws SQLException
  {
    return false;
  }

  public boolean supportsIntegrityEnhancementFacility()
    throws SQLException
  {
    return true;
  }

  public boolean supportsOuterJoins()
    throws SQLException
  {
    return true;
  }

  public boolean supportsFullOuterJoins()
    throws SQLException
  {
    return true;
  }

  public boolean supportsLimitedOuterJoins()
    throws SQLException
  {
    return true;
  }

  public String getSchemaTerm()
    throws SQLException
  {
    return "schema";
  }

  public String getProcedureTerm()
    throws SQLException
  {
    return "procedure";
  }

  public String getCatalogTerm()
    throws SQLException
  {
    return "";
  }

  public boolean isCatalogAtStart()
    throws SQLException
  {
    return false;
  }

  public String getCatalogSeparator()
    throws SQLException
  {
    return "";
  }

  public boolean supportsSchemasInDataManipulation()
    throws SQLException
  {
    return true;
  }

  public boolean supportsSchemasInProcedureCalls()
    throws SQLException
  {
    return true;
  }

  public boolean supportsSchemasInTableDefinitions()
    throws SQLException
  {
    return true;
  }

  public boolean supportsSchemasInIndexDefinitions()
    throws SQLException
  {
    return true;
  }

  public boolean supportsSchemasInPrivilegeDefinitions()
    throws SQLException
  {
    return true;
  }

  public boolean supportsCatalogsInDataManipulation()
    throws SQLException
  {
    return false;
  }

  public boolean supportsCatalogsInProcedureCalls()
    throws SQLException
  {
    return false;
  }

  public boolean supportsCatalogsInTableDefinitions()
    throws SQLException
  {
    return false;
  }

  public boolean supportsCatalogsInIndexDefinitions()
    throws SQLException
  {
    return false;
  }

  public boolean supportsCatalogsInPrivilegeDefinitions()
    throws SQLException
  {
    return false;
  }

  public boolean supportsPositionedDelete()
    throws SQLException
  {
    return false;
  }

  public boolean supportsPositionedUpdate()
    throws SQLException
  {
    return false;
  }

  public boolean supportsSelectForUpdate()
    throws SQLException
  {
    return true;
  }

  public boolean supportsStoredProcedures()
    throws SQLException
  {
    return true;
  }

  public boolean supportsSubqueriesInComparisons()
    throws SQLException
  {
    return true;
  }

  public boolean supportsSubqueriesInExists()
    throws SQLException
  {
    return true;
  }

  public boolean supportsSubqueriesInIns()
    throws SQLException
  {
    return true;
  }

  public boolean supportsSubqueriesInQuantifieds()
    throws SQLException
  {
    return true;
  }

  public boolean supportsCorrelatedSubqueries()
    throws SQLException
  {
    return true;
  }

  public boolean supportsUnion()
    throws SQLException
  {
    return true;
  }

  public boolean supportsUnionAll()
    throws SQLException
  {
    return true;
  }

  public boolean supportsOpenCursorsAcrossCommit()
    throws SQLException
  {
    return false;
  }

  public boolean supportsOpenCursorsAcrossRollback()
    throws SQLException
  {
    return false;
  }

  public boolean supportsOpenStatementsAcrossCommit()
    throws SQLException
  {
    return false;
  }

  public boolean supportsOpenStatementsAcrossRollback()
    throws SQLException
  {
    return false;
  }

  public int getMaxBinaryLiteralLength()
    throws SQLException
  {
    return 1000;
  }

  public int getMaxCharLiteralLength()
    throws SQLException
  {
    return 2000;
  }

  public int getMaxColumnNameLength()
    throws SQLException
  {
    return 30;
  }

  public int getMaxColumnsInGroupBy()
    throws SQLException
  {
    return 0;
  }

  public int getMaxColumnsInIndex()
    throws SQLException
  {
    return 32;
  }

  public int getMaxColumnsInOrderBy()
    throws SQLException
  {
    return 0;
  }

  public int getMaxColumnsInSelect()
    throws SQLException
  {
    return 0;
  }

  public int getMaxColumnsInTable()
    throws SQLException
  {
    return 1000;
  }

  public int getMaxConnections()
    throws SQLException
  {
    return 0;
  }

  public int getMaxCursorNameLength()
    throws SQLException
  {
    return 0;
  }

  public int getMaxIndexLength()
    throws SQLException
  {
    return 0;
  }

  public int getMaxSchemaNameLength()
    throws SQLException
  {
    return 30;
  }

  public int getMaxProcedureNameLength()
    throws SQLException
  {
    return 30;
  }

  public int getMaxCatalogNameLength()
    throws SQLException
  {
    return 0;
  }

  public int getMaxRowSize()
    throws SQLException
  {
    return 0;
  }

  public boolean doesMaxRowSizeIncludeBlobs()
    throws SQLException
  {
    return true;
  }

  public int getMaxStatementLength()
    throws SQLException
  {
    return 65535;
  }

  public int getMaxStatements()
    throws SQLException
  {
    return 0;
  }

  public int getMaxTableNameLength()
    throws SQLException
  {
    return 30;
  }

  public int getMaxTablesInSelect()
    throws SQLException
  {
    return 0;
  }

  public int getMaxUserNameLength()
    throws SQLException
  {
    return 30;
  }

  public int getDefaultTransactionIsolation()
    throws SQLException
  {
    return 2;
  }

  public boolean supportsTransactions()
    throws SQLException
  {
    return true;
  }

  public boolean supportsTransactionIsolationLevel(int paramInt)
    throws SQLException
  {
    return (paramInt == 2) || (paramInt == 8);
  }

  public boolean supportsDataDefinitionAndDataManipulationTransactions()
    throws SQLException
  {
    return true;
  }

  public boolean supportsDataManipulationTransactionsOnly()
    throws SQLException
  {
    return true;
  }

  public boolean dataDefinitionCausesTransactionCommit()
    throws SQLException
  {
    return true;
  }

  public boolean dataDefinitionIgnoredInTransactions()
    throws SQLException
  {
    return false;
  }

  public synchronized ResultSet getProcedures(String paramString1, String paramString2, String paramString3)
    throws SQLException
  {
    String str1 = "SELECT\n  -- Standalone procedures and functions\n  NULL AS procedure_cat,\n  owner AS procedure_schem,\n  object_name AS procedure_name,\n  NULL,\n  NULL,\n  NULL,\n  'Standalone procedure or function' AS remarks,\n  DECODE(object_type, 'PROCEDURE', 1,\n                      'FUNCTION', 2,\n                      0) AS procedure_type\n,  NULL AS specific_name\nFROM all_objects\nWHERE (object_type = 'PROCEDURE' OR object_type = 'FUNCTION')\n  AND owner LIKE :1 ESCAPE '/'\n  AND object_name LIKE :2 ESCAPE '/'\n";

    String str2 = "SELECT\n  -- Packaged procedures with no arguments\n  package_name AS procedure_cat,\n  owner AS procedure_schem,\n  object_name AS procedure_name,\n  NULL,\n  NULL,\n  NULL,\n  'Packaged procedure' AS remarks,\n  1 AS procedure_type\n,  NULL AS specific_name\nFROM all_arguments\nWHERE argument_name IS NULL\n  AND data_type IS NULL\n  AND ";

    String str3 = "SELECT\n  -- Packaged procedures with arguments\n  package_name AS procedure_cat,\n  owner AS procedure_schem,\n  object_name AS procedure_name,\n  NULL,\n  NULL,\n  NULL,\n  'Packaged procedure' AS remarks,\n  1 AS procedure_type\n,  NULL AS specific_name\nFROM all_arguments\nWHERE argument_name IS NOT NULL\n  AND position = 1\n  AND position = sequence\n  AND ";

    String str4 = "SELECT\n  -- Packaged functions\n  package_name AS procedure_cat,\n  owner AS procedure_schem,\n  object_name AS procedure_name,\n  NULL,\n  NULL,\n  NULL,\n  'Packaged function' AS remarks,\n  2 AS procedure_type\n,  NULL AS specific_name\nFROM all_arguments\nWHERE argument_name IS NULL\n  AND in_out = 'OUT'\n  AND   data_level = 0\n  AND ";

    String str5 = "package_name LIKE :3 ESCAPE '/'\n  AND owner LIKE :4 ESCAPE '/'\n  AND object_name LIKE :5 ESCAPE '/'\n";

    String str6 = "package_name IS NOT NULL\n  AND owner LIKE :6 ESCAPE '/'\n  AND object_name LIKE :7 ESCAPE '/'\n";

    String str7 = "ORDER BY procedure_schem, procedure_name\n";

    PreparedStatement preparedstatement = null;
    String str8 = null;

    String str9 = paramString2;

    if (paramString2 == null)
      str9 = "%";
    else if (paramString2.equals("")) {
      str9 = getUserName().toUpperCase();
    }
    String str10 = paramString3;

    if (paramString3 == null) {
      str10 = "%";
    } else if (paramString3.equals(""))
    {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 74);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    if (paramString1 == null)
    {
      str8 = new StringBuilder().append(str1).append("UNION ALL ").append(str2).append(str6).append("UNION ALL ").append(str3).append(str6).append("UNION ALL ").append(str4).append(str6).append(str7).toString();

      preparedstatement = this.connection.prepareStatement(str8);

      preparedstatement.setString(1, str9);
      preparedstatement.setString(2, str10);
      preparedstatement.setString(3, str9);
      preparedstatement.setString(4, str10);
      preparedstatement.setString(5, str9);
      preparedstatement.setString(6, str10);
      preparedstatement.setString(7, str9);
      preparedstatement.setString(8, str10);
    }
    else if (paramString1.equals(""))
    {
      str8 = str1;

      preparedstatement = this.connection.prepareStatement(str8);

      preparedstatement.setString(1, str9);
      preparedstatement.setString(2, str10);
    }
    else
    {
      str8 = new StringBuilder().append(str2).append(str5).append("UNION ALL ").append(str3).append(str5).append("UNION ALL ").append(str4).append(str5).append(str7).toString();

      preparedstatement = this.connection.prepareStatement(str8);

      preparedstatement.setString(1, paramString1);
      preparedstatement.setString(2, str9);
      preparedstatement.setString(3, str10);
      preparedstatement.setString(4, paramString1);
      preparedstatement.setString(5, str9);
      preparedstatement.setString(6, str10);
      preparedstatement.setString(7, paramString1);
      preparedstatement.setString(8, str9);
      preparedstatement.setString(9, str10);
    }

    Object localObject = (OracleResultSet)preparedstatement.executeQuery();

    ((OracleResultSet)localObject).closeStatementOnClose();

    return (OracleResultSet)localObject;
  }

  public synchronized ResultSet getProcedureColumns(String paramString1, String paramString2, String paramString3, String paramString4)
    throws SQLException
  {
    boolean bool = this.connection.getIncludeSynonyms();

    if (("".equals(paramString1)) && ((paramString2 == null) || (!hasSqlWildcard(paramString2))) && (paramString3 != null) && (!hasSqlWildcard(paramString3)))
    {
      return getUnpackagedProcedureColumnsNoWildcards(paramString2 != null ? stripSqlEscapes(paramString2) : null, paramString3 != null ? stripSqlEscapes(paramString3) : null, paramString4);
    }

    if ((paramString1 != null) && (paramString1.length() != 0) && (!hasSqlWildcard(paramString1)) && ((paramString2 == null) || (!hasSqlWildcard(paramString2))))
    {
      return getPackagedProcedureColumnsNoWildcards(paramString1 != null ? stripSqlEscapes(paramString1) : null, paramString2 != null ? stripSqlEscapes(paramString2) : null, paramString3, paramString4);
    }

    return getProcedureColumnsWithWildcards(paramString1, paramString2, paramString3, paramString4, bool);
  }

  ResultSet getUnpackagedProcedureColumnsNoWildcards(String paramString1, String paramString2, String paramString3)
    throws SQLException
  {
    if ("".equals(paramString3))
    {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 74);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }
    Object localObject1 = getUnpackagedProcedureColumnsNoWildcardsPlsql();
    CallableStatement localCallableStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localCallableStatement = this.connection.prepareCall((String)localObject1);
      localCallableStatement.setString(1, paramString1);
      localCallableStatement.setString(2, paramString2);
      localCallableStatement.setString(3, paramString3 == null ? "%" : paramString3);
      localCallableStatement.registerOutParameter(4, -10);
      localCallableStatement.registerOutParameter(5, 2);
      localCallableStatement.execute();
      int i = localCallableStatement.getInt(5);
      if (i == 0)
      {
        localResultSet = ((OracleCallableStatement)localCallableStatement).getCursor(4);
        ((OracleResultSet)localResultSet).closeStatementOnClose();
      }
      else
      {
        SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 258);
        localSQLException.fillInStackTrace();
        throw localSQLException;
      }
    }
    finally
    {
      if ((localResultSet == null) && (localCallableStatement != null)) localCallableStatement.close();
    }
    return localResultSet;
  }

  ResultSet getPackagedProcedureColumnsNoWildcards(String paramString1, String paramString2, String paramString3, String paramString4)
    throws SQLException
  {
    if ("".equals(paramString4))
    {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 74);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }
    Object localObject1 = getPackagedProcedureColumnsNoWildcardsPlsql();
    CallableStatement localCallableStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localCallableStatement = this.connection.prepareCall((String)localObject1);
      localCallableStatement.setString(1, paramString1);
      localCallableStatement.setString(2, paramString2);
      localCallableStatement.setString(3, paramString3);
      localCallableStatement.setString(4, paramString4 == null ? "%" : paramString4);
      localCallableStatement.registerOutParameter(5, -10);
      localCallableStatement.registerOutParameter(6, 2);
      localCallableStatement.execute();
      int i = localCallableStatement.getInt(6);
      if (i == 0)
      {
        localResultSet = ((OracleCallableStatement)localCallableStatement).getCursor(5);
        ((OracleResultSet)localResultSet).closeStatementOnClose();
      }
      else
      {
        SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 258);
        localSQLException.fillInStackTrace();
        throw localSQLException;
      }
    }
    finally
    {
      if ((localResultSet == null) && (localCallableStatement != null)) localCallableStatement.close();
    }
    return localResultSet;
  }

  ResultSet getProcedureColumnsWithWildcards(String paramString1, String paramString2, String paramString3, String paramString4, boolean paramBoolean)
    throws SQLException
  {
    String str1 = new StringBuilder().append("SELECT package_name AS procedure_cat,\n       owner AS procedure_schem,\n       object_name AS procedure_name,\n       argument_name AS column_name,\n       DECODE(position, 0, 5,\n                        DECODE(in_out, 'IN', 1,\n                                       'OUT', 4,\n                                       'IN/OUT', 2,\n                                       0)) AS column_type,\n       DECODE (data_type, 'CHAR', 1,\n                          'VARCHAR2', 12,\n                          'NUMBER', 3,\n                          'LONG', -1,\n                          'DATE', ").append(this.connection.getMapDateToTimestamp() ? "93,\n" : "91,\n").append("                          'RAW', -3,\n").append("                          'LONG RAW', -4,\n").append("                          'TIMESTAMP', 93, \n").append("                          'TIMESTAMP WITH TIME ZONE', -101, \n").append("               'TIMESTAMP WITH LOCAL TIME ZONE', -102, \n").append("               'INTERVAL YEAR TO MONTH', -103, \n").append("               'INTERVAL DAY TO SECOND', -104, \n").append("               'BINARY_FLOAT', 100, 'BINAvRY_DOUBLE', 101,").append("               1111) AS data_type,\n").append("       DECODE(data_type, 'OBJECT', type_owner || '.' || type_name, ").append("              data_type) AS type_name,\n").append("       DECODE (data_precision, NULL, data_length,\n").append("                               data_precision) AS precision,\n").append("       data_length AS length,\n").append("       data_scale AS scale,\n").append("       10 AS radix,\n").append("       1 AS nullable,\n").append("       NULL AS remarks,\n").append("       default_value AS column_def,\n").append("       NULL as sql_data_type,\n").append("       NULL AS sql_datetime_sub,\n").append("       DECODE(data_type,\n").append("                         'CHAR', 32767,\n").append("                         'VARCHAR2', 32767,\n").append("                         'LONG', 32767,\n").append("                         'RAW', 32767,\n").append("                         'LONG RAW', 32767,\n").append("                         NULL) AS char_octet_length,\n").append("       (sequence - 1) AS ordinal_position,\n").append("       'YES' AS is_nullable,\n").append("       NULL AS specific_name,\n").append("       sequence,\n").append("       overload,\n").append("       default_value\n").append(" FROM all_arguments\n").append("WHERE owner LIKE :1 ESCAPE '/'\n").append("  AND object_name LIKE :2 ESCAPE '/' AND data_level = 0\n").toString();

    String str2 = "  AND package_name LIKE :3 ESCAPE '/'\n";
    String str3 = "  AND package_name IS NULL\n";

    String str4 = "  AND argument_name LIKE :4 ESCAPE '/'\n";
    String str5 = "  AND (argument_name LIKE :5 ESCAPE '/'\n       OR (argument_name IS NULL\n           AND data_type IS NOT NULL))\n";

    String str6 = "ORDER BY procedure_schem, procedure_name, overload, sequence\n";

    String str7 = null;
    PreparedStatement preparedstatement = null;
    String str8 = null;

    String str9 = paramString2;

    if (paramString2 == null)
      str9 = "%";
    else if (paramString2.equals("")) {
      str9 = getUserName().toUpperCase();
    }
    String str10 = paramString3;

    if (paramString3 == null) {
      str10 = "%";
    } else if (paramString3.equals(""))
    {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 74);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject1 = paramString4;

    if ((paramString4 == null) || (paramString4.equals("%")))
    {
      localObject1 = "%";
      str8 = str5;
    } else {
      if (paramString4.equals(""))
      {
        SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 74);
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }

      str8 = str4;
    }
    if (paramString1 == null)
    {
      str7 = new StringBuilder().append(str1).append(str8).append(str6).toString();

      preparedstatement = this.connection.prepareStatement(str7);

      preparedstatement.setString(1, str9);
      preparedstatement.setString(2, str10);
      preparedstatement.setString(3, (String)localObject1);
    }
    else if (paramString1.equals(""))
    {
      str7 = new StringBuilder().append(str1).append(str3).append(str8).append(str6).toString();

      preparedstatement = this.connection.prepareStatement(str7);

      preparedstatement.setString(1, str9);
      preparedstatement.setString(2, str10);
      preparedstatement.setString(3, (String)localObject1);
    }
    else
    {
      str7 = new StringBuilder().append(str1).append(str2).append(str8).append(str6).toString();

      preparedstatement = this.connection.prepareStatement(str7);

      preparedstatement.setString(1, str9);
      preparedstatement.setString(2, str10);
      preparedstatement.setString(3, paramString1);
      preparedstatement.setString(4, (String)localObject1);
    }

    Object localObject2 = (OracleResultSet)preparedstatement.executeQuery();

    ((OracleResultSet)localObject2).closeStatementOnClose();

    return (OracleResultSet)localObject2;
  }

  public ResultSet getFunctionColumns(String paramString1, String paramString2, String paramString3, String paramString4)
    throws SQLException
  {
    String str1 = new StringBuilder().append("SELECT package_name AS function_cat,\n       arg.owner AS function_schem,\n       arg.object_name AS function_name,\n       arg.argument_name AS column_name,\n       DECODE(arg.position, 0, 5,\n                        DECODE(arg.in_out, 'IN', 1,\n                                       'OUT', 4,\n                                       'IN/OUT', 2,\n                                       0)) AS column_type,\n       DECODE (arg.data_type, 'CHAR', 1,\n                          'VARCHAR2', 12,\n                          'NUMBER', 3,\n                          'LONG', -1,\n                          'DATE', ").append(this.connection.getMapDateToTimestamp() ? "93,\n" : "91,\n").append("                          'RAW', -3,\n").append("                          'LONG RAW', -4,\n").append("                          'TIMESTAMP', 93, \n").append("                          'TIMESTAMP WITH TIME ZONE', -101, \n").append("               'TIMESTAMP WITH LOCAL TIME ZONE', -102, \n").append("               'INTERVAL YEAR TO MONTH', -103, \n").append("               'INTERVAL DAY TO SECOND', -104, \n").append("               'BINARY_FLOAT', 100, 'BINAvRY_DOUBLE', 101,").append("               1111) AS data_type,\n").append("       DECODE(arg.data_type, 'OBJECT', arg.type_owner || '.' || arg.type_name, ").append("              arg.data_type) AS type_name,\n").append("       DECODE (arg.data_precision, NULL, arg.data_length,\n").append("                               arg.data_precision) AS precision,\n").append("       arg.data_length AS length,\n").append("       arg.data_scale AS scale,\n").append("       10 AS radix,\n").append("       1 AS nullable,\n").append("       NULL AS remarks,\n").append("       arg.default_value AS column_def,\n").append("       NULL as sql_data_type,\n").append("       NULL AS sql_datetime_sub,\n").append("       DECODE(arg.data_type,\n").append("                         'CHAR', 32767,\n").append("                         'VARCHAR2', 32767,\n").append("                         'LONG', 32767,\n").append("                         'RAW', 32767,\n").append("                         'LONG RAW', 32767,\n").append("                         NULL) AS char_octet_length,\n").append("       (arg.sequence - 1) AS ordinal_position,\n").append("       'YES' AS is_nullable,\n").append("       NULL AS specific_name,\n").append("       arg.sequence,\n").append("       arg.overload,\n").append("       arg.default_value\n").append(" FROM all_arguments arg, all_procedures proc\n").append(" WHERE arg.owner LIKE :1 ESCAPE '/'\n").append("  AND arg.object_name LIKE :2 ESCAPE '/'\n").toString();

    int i = this.connection.getVersionNumber();

    String str2 = i >= 10200 ? "  AND proc.object_id = arg.object_id\n  AND proc.object_type = 'FUNCTION'\n" : "  AND proc.owner = arg.owner\n  AND proc.object_name = arg.object_name\n";

    String str3 = "  AND arg.package_name LIKE :3 ESCAPE '/'\n";

    String str4 = "  AND arg.package_name IS NULL\n";

    String str5 = "  AND arg.argument_name LIKE :4 ESCAPE '/'\n";

    String str6 = "  AND (arg.argument_name LIKE :5 ESCAPE '/'\n     OR (arg.argument_name IS NULL\n         AND arg.data_type IS NOT NULL))\n";

    String str7 = "ORDER BY function_schem, function_name, overload, sequence\n";

    String str8 = null;
    PreparedStatement preparedstatement = null;
    String str9 = null;

    String str10 = paramString2;

    if (paramString2 == null)
      str10 = "%";
    else if (paramString2.equals("")) {
      str10 = getUserName().toUpperCase();
    }
    String str11 = paramString3;

    if (paramString3 == null) {
      str11 = "%";
    } else if (paramString3.equals(""))
    {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 74);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject1 = paramString4;

    if ((paramString4 == null) || (paramString4.equals("%")))
    {
      localObject1 = "%";
      str9 = str6;
    } else {
      if (paramString4.equals(""))
      {
        SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 74);
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }

      str9 = str5;
    }
    if (paramString1 == null)
    {
      str8 = new StringBuilder().append(str1).append(str2).append(str9).append(str7).toString();

      preparedstatement = this.connection.prepareStatement(str8);

      preparedstatement.setString(1, str10);
      preparedstatement.setString(2, str11);
      preparedstatement.setString(3, (String)localObject1);
    }
    else if (paramString1.equals(""))
    {
      str8 = new StringBuilder().append(str1).append(str2).append(str4).append(str9).append(str7).toString();

      preparedstatement = this.connection.prepareStatement(str8);

      preparedstatement.setString(1, str10);
      preparedstatement.setString(2, str11);
      preparedstatement.setString(3, (String)localObject1);
    }
    else
    {
      str8 = new StringBuilder().append(str1).append(str2).append(str3).append(str9).append(str7).toString();

      preparedstatement = this.connection.prepareStatement(str8);

      preparedstatement.setString(1, str10);
      preparedstatement.setString(2, str11);
      preparedstatement.setString(3, paramString1);
      preparedstatement.setString(4, (String)localObject1);
    }

    Object localObject2 = (OracleResultSet)preparedstatement.executeQuery();

    ((OracleResultSet)localObject2).closeStatementOnClose();

    return (OracleResultSet)localObject2;
  }

  public synchronized ResultSet getTables(String paramString1, String paramString2, String paramString3, String[] paramArrayOfString)
    throws SQLException
  {
    String str1 = "SELECT NULL AS table_cat,\n       o.owner AS table_schem,\n       o.object_name AS table_name,\n       o.object_type AS table_type,\n";

    String str2 = "       c.comments AS remarks\n";
    String str3 = "       NULL AS remarks\n";

    String str4 = "  FROM all_objects o, all_tab_comments c\n";
    String str5 = "  FROM all_objects o\n";

    String str6 = "  WHERE o.owner LIKE :1 ESCAPE '/'\n    AND o.object_name LIKE :2 ESCAPE '/'\n";

    String str7 = "    AND o.owner = c.owner (+)\n    AND o.object_name = c.table_name (+)\n";

    int i = 0;

    String str8 = "";
    String str9 = "";

    if (paramArrayOfString != null)
    {
      str8 = "    AND o.object_type IN ('xxx'";
      str9 = "    AND o.object_type IN ('xxx'";

      for (int j = 0; j < paramArrayOfString.length; j++)
      {
        if (paramArrayOfString[j].equals("SYNONYM"))
        {
          str8 = new StringBuilder().append(str8).append(", '").append(paramArrayOfString[j]).append("'").toString();
          i = 1;
        }
        else
        {
          str8 = new StringBuilder().append(str8).append(", '").append(paramArrayOfString[j]).append("'").toString();
          str9 = new StringBuilder().append(str9).append(", '").append(paramArrayOfString[j]).append("'").toString();
        }
      }

      str8 = new StringBuilder().append(str8).append(")\n").toString();
      str9 = new StringBuilder().append(str9).append(")\n").toString();
    }
    else
    {
      i = 1;
      str8 = "    AND o.object_type IN ('TABLE', 'SYNONYM', 'VIEW')\n";
      str9 = "    AND o.object_type IN ('TABLE', 'VIEW')\n";
    }

    String str10 = "  ORDER BY table_type, table_schem, table_name\n";

    String str11 = "SELECT NULL AS table_cat,\n       s.owner AS table_schem,\n       s.synonym_name AS table_name,\n       'SYNONYM' AS table_table_type,\n";

    String str12 = "       c.comments AS remarks\n";
    String str13 = "       NULL AS remarks\n";

    String str14 = "  FROM all_synonyms s, all_objects o, all_tab_comments c\n";

    String str15 = "  FROM all_synonyms s, all_objects o\n";

    String str16 = "  WHERE s.owner LIKE :3 ESCAPE '/'\n    AND s.synonym_name LIKE :4 ESCAPE '/'\n    AND s.table_owner = o.owner\n    AND s.table_name = o.object_name\n    AND o.object_type IN ('TABLE', 'VIEW')\n";

    String str17 = "";

    str17 = new StringBuilder().append(str17).append(str1).toString();

    if (this.connection.getRemarksReporting())
      str17 = new StringBuilder().append(str17).append(str2).append(str4).toString();
    else {
      str17 = new StringBuilder().append(str17).append(str3).append(str5).toString();
    }
    str17 = new StringBuilder().append(str17).append(str6).toString();

    if (this.connection.getRestrictGetTables())
      str17 = new StringBuilder().append(str17).append(str9).toString();
    else {
      str17 = new StringBuilder().append(str17).append(str8).toString();
    }
    if (this.connection.getRemarksReporting()) {
      str17 = new StringBuilder().append(str17).append(str7).toString();
    }
    if ((i != 0) && (this.connection.getRestrictGetTables()))
    {
      str17 = new StringBuilder().append(str17).append("UNION\n").append(str11).toString();

      if (this.connection.getRemarksReporting())
        str17 = new StringBuilder().append(str17).append(str12).append(str14).toString();
      else {
        str17 = new StringBuilder().append(str17).append(str13).append(str15).toString();
      }
      str17 = new StringBuilder().append(str17).append(str16).toString();

      if (this.connection.getRemarksReporting()) {
        str17 = new StringBuilder().append(str17).append(str7).toString();
      }
    }
    str17 = new StringBuilder().append(str17).append(str10).toString();

    PreparedStatement preparedstatement = this.connection.prepareStatement(str17);

    preparedstatement.setString(1, paramString2 == null ? "%" : paramString2);
    preparedstatement.setString(2, paramString3 == null ? "%" : paramString3);

    if ((i != 0) && (this.connection.getRestrictGetTables()))
    {
      preparedstatement.setString(3, paramString2 == null ? "%" : paramString2);
      preparedstatement.setString(4, paramString3 == null ? "%" : paramString3);
    }

    OracleResultSet localOracleResultSet = (OracleResultSet)preparedstatement.executeQuery();

    localOracleResultSet.closeStatementOnClose();

    return localOracleResultSet;
  }

  public ResultSet getSchemas()
    throws SQLException
  {
    Statement localStatement = this.connection.createStatement();
    String str = "SELECT username AS table_schem FROM all_users ORDER BY table_schem";

    OracleResultSet localOracleResultSet = (OracleResultSet)localStatement.executeQuery(str);

    localOracleResultSet.closeStatementOnClose();

    return localOracleResultSet;
  }

  public ResultSet getCatalogs()
    throws SQLException
  {
    Statement localStatement = this.connection.createStatement();
    String str = "select 'nothing' as table_cat from dual where 1 = 2";
    OracleResultSet localOracleResultSet = (OracleResultSet)localStatement.executeQuery(str);

    localOracleResultSet.closeStatementOnClose();

    return localOracleResultSet;
  }

  public ResultSet getTableTypes()
    throws SQLException
  {
    Statement localStatement = this.connection.createStatement();
    String str = "select 'TABLE' as table_type from dual\nunion select 'VIEW' as table_type from dual\nunion select 'SYNONYM' as table_type from dual\n";

    OracleResultSet localOracleResultSet = (OracleResultSet)localStatement.executeQuery(str);

    localOracleResultSet.closeStatementOnClose();

    return localOracleResultSet;
  }

  public ResultSet getColumns(String paramString1, String paramString2, String paramString3, String paramString4)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createUnsupportedFeatureSqlException();
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public synchronized ResultSet getColumnPrivileges(String paramString1, String paramString2, String paramString3, String paramString4)
    throws SQLException
  {
    PreparedStatement preparedstatement = this.connection.prepareStatement("SELECT NULL AS table_cat,\n       table_schema AS table_schem,\n       table_name,\n       column_name,\n       grantor,\n       grantee,\n       privilege,\n       grantable AS is_grantable\nFROM all_col_privs\nWHERE table_schema LIKE :1 ESCAPE '/'\n  AND table_name LIKE :2 ESCAPE '/'\n  AND column_name LIKE :3 ESCAPE '/'\nORDER BY column_name, privilege\n");

    preparedstatement.setString(1, paramString2 == null ? "%" : paramString2);
    preparedstatement.setString(2, paramString3 == null ? "%" : paramString3);
    preparedstatement.setString(3, paramString4 == null ? "%" : paramString4);

    OracleResultSet localOracleResultSet = (OracleResultSet)preparedstatement.executeQuery();

    localOracleResultSet.closeStatementOnClose();

    return localOracleResultSet;
  }

  public synchronized ResultSet getTablePrivileges(String paramString1, String paramString2, String paramString3)
    throws SQLException
  {
    PreparedStatement preparedstatement = this.connection.prepareStatement("SELECT NULL AS table_cat,\n       table_schema AS table_schem,\n       table_name,\n       grantor,\n       grantee,\n       privilege,\n       grantable AS is_grantable\nFROM all_tab_privs\nWHERE table_schema LIKE :1 ESCAPE '/'\n  AND table_name LIKE :2 ESCAPE '/'\nORDER BY table_schem, table_name, privilege\n");

    preparedstatement.setString(1, paramString2 == null ? "%" : paramString2);
    preparedstatement.setString(2, paramString3 == null ? "%" : paramString3);

    OracleResultSet localOracleResultSet = (OracleResultSet)preparedstatement.executeQuery();

    localOracleResultSet.closeStatementOnClose();

    return localOracleResultSet;
  }

  public synchronized ResultSet getBestRowIdentifier(String paramString1, String paramString2, String paramString3, int paramInt, boolean paramBoolean)
    throws SQLException
  {
    PreparedStatement preparedstatement = this.connection.prepareStatement(new StringBuilder().append("SELECT 1 AS scope, 'ROWID' AS column_name, -8 AS data_type,\n 'ROWID' AS type_name, 0 AS column_size, 0 AS buffer_length,\n       0 AS decimal_digits, 2 AS pseudo_column\nFROM DUAL\nWHERE :1 = 1\nUNION\nSELECT 2 AS scope,\n  t.column_name,\n DECODE (t.data_type, 'CHAR', 1, 'VARCHAR2', 12, 'NUMBER', 3,\n 'LONG', -1, 'DATE', ").append(this.connection.getMapDateToTimestamp() ? "93,\n" : "91,\n").append(" 'RAW', -3, 'LONG RAW', -4, \n").append(" 'TIMESTAMP(6)', 93, ").append(" 'TIMESTAMP(6) WITH TIME ZONE', -101, \n").append(" 'TIMESTAMP(6) WITH LOCAL TIME ZONE', -102, \n").append(" 'INTERVAL YEAR(2) TO MONTH', -103, \n").append(" 'INTERVAL DAY(2) TO SECOND(6)', -104, \n").append(" 'BINARY_FLOAT', 100, ").append(" 'BINARY_DOUBLE', 101,").append(" 1111)\n").append(" AS data_type,\n").append(" t.data_type AS type_name,\n").append(" DECODE (t.data_precision, null, t.data_length, t.data_precision)\n").append("  AS column_size,\n").append("  0 AS buffer_length,\n").append("  t.data_scale AS decimal_digits,\n").append("       1 AS pseudo_column\n").append("FROM all_tab_columns t, all_ind_columns i\n").append("WHERE :2 = 1\n").append("  AND t.table_name = :3\n").append("  AND t.owner like :4 escape '/'\n").append("  AND t.nullable != :5\n").append("  AND t.owner = i.table_owner\n").append("  AND t.table_name = i.table_name\n").append("  AND t.column_name = i.column_name\n").toString());

    switch (paramInt)
    {
    case 0:
      preparedstatement.setInt(1, 0);
      preparedstatement.setInt(2, 0);

      break;
    case 1:
      preparedstatement.setInt(1, 1);
      preparedstatement.setInt(2, 1);

      break;
    case 2:
      preparedstatement.setInt(1, 0);
      preparedstatement.setInt(2, 1);
    }

    preparedstatement.setString(3, paramString3);
    preparedstatement.setString(4, paramString2 == null ? "%" : paramString2);
    preparedstatement.setString(5, paramBoolean ? "X" : "Y");

    OracleResultSet localOracleResultSet = (OracleResultSet)preparedstatement.executeQuery();

    localOracleResultSet.closeStatementOnClose();

    return localOracleResultSet;
  }

  public synchronized ResultSet getVersionColumns(String paramString1, String paramString2, String paramString3)
    throws SQLException
  {
    PreparedStatement preparedstatement = this.connection.prepareStatement(new StringBuilder().append("SELECT 0 AS scope,\n t.column_name,\n DECODE (c.data_type, 'CHAR', 1, 'VARCHAR2', 12, 'NUMBER', 3,\n  'LONG', -1, 'DATE',  ").append(this.connection.getMapDateToTimestamp() ? "93,\n" : "91,\n").append("  'RAW', -3, 'LONG RAW', -4, ").append("  'TIMESTAMP(6)', 93, 'TIMESTAMP(6) WITH TIME ZONE', -101, \n").append("  'TIMESTAMP(6) WITH LOCAL TIME ZONE', -102, \n").append("  'INTERVAL YEAR(2) TO MONTH', -103, \n").append("  'INTERVAL DAY(2) TO SECOND(6)', -104, \n").append("  'BINARY_FLOAT', 100, 'BINARY_DOUBLE', 101,").append("   1111)\n ").append(" AS data_type,\n").append("       c.data_type AS type_name,\n").append(" DECODE (c.data_precision, null, c.data_length, c.data_precision)\n").append("   AS column_size,\n").append("       0 as buffer_length,\n").append("   c.data_scale as decimal_digits,\n").append("   0 as pseudo_column\n").append("FROM all_trigger_cols t, all_tab_columns c\n").append("WHERE t.table_name = :1\n").append("  AND c.owner like :2 escape '/'\n").append(" AND t.table_owner = c.owner\n").append("  AND t.table_name = c.table_name\n").append(" AND t.column_name = c.column_name\n").toString());

    preparedstatement.setString(1, paramString3);
    preparedstatement.setString(2, paramString2 == null ? "%" : paramString2);

    OracleResultSet localOracleResultSet = (OracleResultSet)preparedstatement.executeQuery();

    localOracleResultSet.closeStatementOnClose();

    return localOracleResultSet;
  }

  public ResultSet getPrimaryKeys(String paramString1, String paramString2, String paramString3)
    throws SQLException
  {
    PreparedStatement preparedstatement = this.connection.prepareStatement("SELECT NULL AS table_cat,\n       c.owner AS table_schem,\n       c.table_name,\n       c.column_name,\n       c.position AS key_seq,\n       c.constraint_name AS pk_name\nFROM all_cons_columns c, all_constraints k\nWHERE k.constraint_type = 'P'\n  AND k.table_name = :1\n  AND k.owner like :2 escape '/'\n  AND k.constraint_name = c.constraint_name \n  AND k.table_name = c.table_name \n  AND k.owner = c.owner \nORDER BY column_name\n");

    preparedstatement.setString(1, paramString3);
    preparedstatement.setString(2, paramString2 == null ? "%" : paramString2);

    OracleResultSet localOracleResultSet = (OracleResultSet)preparedstatement.executeQuery();

    localOracleResultSet.closeStatementOnClose();

    return localOracleResultSet;
  }

  ResultSet keys_query(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
    throws SQLException
  {
    int i = 1;
    int j = paramString2 != null ? i++ : 0;
    int k = paramString4 != null ? i++ : 0;
    int m = (paramString1 != null) && (paramString1.length() > 0) ? i++ : 0;
    int n = (paramString3 != null) && (paramString3.length() > 0) ? i++ : 0;

    PreparedStatement preparedstatement = this.connection.prepareStatement(new StringBuilder().append("SELECT NULL AS pktable_cat,\n       p.owner as pktable_schem,\n       p.table_name as pktable_name,\n       pc.column_name as pkcolumn_name,\n       NULL as fktable_cat,\n       f.owner as fktable_schem,\n       f.table_name as fktable_name,\n       fc.column_name as fkcolumn_name,\n       fc.position as key_seq,\n       NULL as update_rule,\n       decode (f.delete_rule, 'CASCADE', 0, 'SET NULL', 2, 1) as delete_rule,\n       f.constraint_name as fk_name,\n       p.constraint_name as pk_name,\n       decode(f.deferrable,       'DEFERRABLE',5      ,'NOT DEFERRABLE',7      , 'DEFERRED', 6      ) deferrability \n      FROM all_cons_columns pc, all_constraints p,\n      all_cons_columns fc, all_constraints f\nWHERE 1 = 1\n").append(j != 0 ? "  AND p.table_name = :1\n" : "").append(k != 0 ? "  AND f.table_name = :2\n" : "").append(m != 0 ? "  AND p.owner = :3\n" : "").append(n != 0 ? "  AND f.owner = :4\n" : "").append("  AND f.constraint_type = 'R'\n").append("  AND p.owner = f.r_owner\n").append("  AND p.constraint_name = f.r_constraint_name\n").append("  AND p.constraint_type = 'P'\n").append("  AND pc.owner = p.owner\n").append("  AND pc.constraint_name = p.constraint_name\n").append("  AND pc.table_name = p.table_name\n").append("  AND fc.owner = f.owner\n").append("  AND fc.constraint_name = f.constraint_name\n").append("  AND fc.table_name = f.table_name\n").append("  AND fc.position = pc.position\n").append(paramString5).toString());

    if (j != 0)
    {
      preparedstatement.setString(j, paramString2);
    }

    if (k != 0)
    {
      preparedstatement.setString(k, paramString4);
    }

    if (m != 0)
    {
      preparedstatement.setString(m, paramString1);
    }

    if (n != 0)
    {
      preparedstatement.setString(n, paramString3);
    }

    OracleResultSet localOracleResultSet = (OracleResultSet)preparedstatement.executeQuery();

    localOracleResultSet.closeStatementOnClose();

    return localOracleResultSet;
  }

  public synchronized ResultSet getImportedKeys(String paramString1, String paramString2, String paramString3)
    throws SQLException
  {
    return keys_query(null, null, paramString2, paramString3, "ORDER BY pktable_schem, pktable_name, key_seq");
  }

  public ResultSet getExportedKeys(String paramString1, String paramString2, String paramString3)
    throws SQLException
  {
    return keys_query(paramString2, paramString3, null, null, "ORDER BY fktable_schem, fktable_name, key_seq");
  }

  public ResultSet getCrossReference(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6)
    throws SQLException
  {
    return keys_query(paramString2, paramString3, paramString5, paramString6, "ORDER BY fktable_schem, fktable_name, key_seq");
  }

  public ResultSet getTypeInfo()
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createUnsupportedFeatureSqlException();
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public synchronized ResultSet getIndexInfo(String paramString1, String paramString2, String paramString3, boolean paramBoolean1, boolean paramBoolean2)
    throws SQLException
  {
    Statement localStatement = this.connection.createStatement();

    if (((paramString2 != null) && (paramString2.length() != 0) && (!OracleSql.isValidObjectName(paramString2))) || ((paramString3 != null) && (paramString3.length() != 0) && (!OracleSql.isValidObjectName(paramString3))))
    {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    if (!paramBoolean2)
    {
      String str = new StringBuilder().append("analyze table ").append(paramString2 == null ? "" : new StringBuilder().append(paramString2).append(".").toString()).append(paramString3).append(" compute statistics").toString();

      localStatement.executeUpdate((String)str);
    }

    if ((paramString3.startsWith("\"")) && (paramString3.endsWith("\"")) && (paramString3.length() > 2))
    {
      paramString3 = paramString3.substring(1, paramString3.length() - 1);
    }

    Object localObject = new StringBuilder().append("select null as table_cat,\n       owner as table_schem,\n       table_name,\n       0 as NON_UNIQUE,\n       null as index_qualifier,\n       null as index_name, 0 as type,\n       0 as ordinal_position, null as column_name,\n       null as asc_or_desc,\n       num_rows as cardinality,\n       blocks as pages,\n       null as filter_condition\nfrom all_tables\nwhere table_name = '").append(paramString3).append("'\n").toString();

    String str1 = "";

    if ((paramString2 != null) && (paramString2.length() > 0))
    {
      if ((paramString2.startsWith("\"")) && (paramString2.endsWith("\"")) && (paramString2.length() > 2))
      {
        paramString2 = paramString2.substring(1, paramString2.length() - 1);
      }

      str1 = new StringBuilder().append("  and owner = '").append(paramString2).append("'\n").toString();
    }

    String str2 = new StringBuilder().append("select null as table_cat,\n       i.owner as table_schem,\n       i.table_name,\n       decode (i.uniqueness, 'UNIQUE', 0, 1),\n       null as index_qualifier,\n       i.index_name,\n       1 as type,\n       c.column_position as ordinal_position,\n       c.column_name,\n       null as asc_or_desc,\n       i.distinct_keys as cardinality,\n       i.leaf_blocks as pages,\n       null as filter_condition\nfrom all_indexes i, all_ind_columns c\nwhere i.table_name = '").append(paramString3).append("'\n").toString();

    String str3 = "";

    if ((paramString2 != null) && (paramString2.length() > 0)) {
      str3 = new StringBuilder().append("  and i.owner = '").append(paramString2).append("'\n").toString();
    }
    String str4 = "";

    if (paramBoolean1) {
      str4 = "  and i.uniqueness = 'UNIQUE'\n";
    }
    String str5 = "  and i.index_name = c.index_name\n  and i.table_owner = c.table_owner\n  and i.table_name = c.table_name\n  and i.owner = c.index_owner\n";

    String str6 = "order by non_unique, type, index_name, ordinal_position\n";

    String str7 = new StringBuilder().append((String)localObject).append(str1).append("union\n").append(str2).append(str3).append(str4).append(str5).append(str6).toString();

    OracleResultSet localOracleResultSet = (OracleResultSet)localStatement.executeQuery(str7);

    localOracleResultSet.closeStatementOnClose();

    return localOracleResultSet;
  }

  SQLException fail()
  {
    SQLException localSQLException = new SQLException("Not implemented yet");

    return localSQLException;
  }

  public boolean supportsResultSetType(int paramInt)
    throws SQLException
  {
    return true;
  }

  public boolean supportsResultSetConcurrency(int paramInt1, int paramInt2)
    throws SQLException
  {
    return true;
  }

  public boolean ownUpdatesAreVisible(int paramInt)
    throws SQLException
  {
    return paramInt != 1003;
  }

  public boolean ownDeletesAreVisible(int paramInt)
    throws SQLException
  {
    return paramInt != 1003;
  }

  public boolean ownInsertsAreVisible(int paramInt)
    throws SQLException
  {
    return false;
  }

  public boolean othersUpdatesAreVisible(int paramInt)
    throws SQLException
  {
    if (paramInt == 1005) {
      return true;
    }
    return false;
  }

  public boolean othersDeletesAreVisible(int paramInt)
    throws SQLException
  {
    return false;
  }

  public boolean othersInsertsAreVisible(int paramInt)
    throws SQLException
  {
    return false;
  }

  public boolean updatesAreDetected(int paramInt)
    throws SQLException
  {
    return false;
  }

  public boolean deletesAreDetected(int paramInt)
    throws SQLException
  {
    return false;
  }

  public boolean insertsAreDetected(int paramInt)
    throws SQLException
  {
    return false;
  }

  public boolean supportsBatchUpdates()
    throws SQLException
  {
    return true;
  }

  public ResultSet getUDTs(String paramString1, String paramString2, String paramString3, int[] paramArrayOfInt)
    throws SQLException
  {
    int i = 0;

    if ((paramString3 == null) || (paramString3.length() == 0))
    {
      i = 0;
    }
    else if (paramArrayOfInt == null)
    {
      i = 1;
    }
    else
    {
      for (int j = 0; j < paramArrayOfInt.length; j++)
      {
        if (paramArrayOfInt[j] == 2002)
        {
          i = 1;

          break;
        }

      }

    }

    StringBuffer localStringBuffer = new StringBuffer();

    localStringBuffer.append("SELECT NULL AS TYPE_CAT, owner AS TYPE_SCHEM, type_name, NULL AS CLASS_NAME, 'STRUCT' AS DATA_TYPE, NULL AS REMARKS FROM all_types ");

    if (i != 0)
    {
      localStringBuffer.append("WHERE typecode = 'OBJECT' AND owner LIKE :1 ESCAPE '/' AND type_name LIKE :2 ESCAPE '/'");
    }
    else
    {
      localStringBuffer.append("WHERE 1 = 2");
    }

    PreparedStatement preparedstatement = this.connection.prepareStatement(localStringBuffer.substring(0, localStringBuffer.length()));

    if (i != 0)
    {
      String[] strs = new String[1];
      String[] arrayOfString = new String[1];

      if (SQLName.parse(paramString3, (String[])strs, arrayOfString))
      {
        preparedstatement.setString(1, strs[0]);
        preparedstatement.setString(2, arrayOfString[0]);
      }
      else
      {
        if (paramString2 != null)
          preparedstatement.setString(1, paramString2);
        else {
          preparedstatement.setNull(1, 12);
        }
        preparedstatement.setString(2, paramString3);
      }

    }

    Object localObject = (OracleResultSet)preparedstatement.executeQuery();

    ((OracleResultSet)localObject).closeStatementOnClose();

    return (OracleResultSet)localObject;
  }

  public Connection getConnection()
    throws SQLException
  {
    return this.connection.getWrapper();
  }

  public boolean supportsSavepoints()
    throws SQLException
  {
    return true;
  }

  public boolean supportsNamedParameters()
    throws SQLException
  {
    return true;
  }

  public boolean supportsMultipleOpenResults()
    throws SQLException
  {
    return false;
  }

  public boolean supportsGetGeneratedKeys()
    throws SQLException
  {
    return true;
  }

  public ResultSet getSuperTypes(String paramString1, String paramString2, String paramString3)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createUnsupportedFeatureSqlException();
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public ResultSet getSuperTables(String paramString1, String paramString2, String paramString3)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createUnsupportedFeatureSqlException();
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public ResultSet getAttributes(String paramString1, String paramString2, String paramString3, String paramString4)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createUnsupportedFeatureSqlException();
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public boolean supportsResultSetHoldability(int paramInt)
    throws SQLException
  {
    if (paramInt == 1) {
      return true;
    }
    return false;
  }

  public int getResultSetHoldability()
    throws SQLException
  {
    return 1;
  }

  public int getDatabaseMajorVersion()
    throws SQLException
  {
    return this.connection.getVersionNumber() / 1000;
  }

  public int getDatabaseMinorVersion()
    throws SQLException
  {
    return this.connection.getVersionNumber() % 1000 / 100;
  }

  public int getJDBCMajorVersion()
    throws SQLException
  {
    return DRIVER_MAJOR_VERSION;
  }

  public int getJDBCMinorVersion()
    throws SQLException
  {
    return DRIVER_MINOR_VERSION;
  }

  public int getSQLStateType()
    throws SQLException
  {
    return 0;
  }

  public boolean locatorsUpdateCopy()
    throws SQLException
  {
    return true;
  }

  public boolean supportsStatementPooling()
    throws SQLException
  {
    return true;
  }

  public static String getDriverNameInfo()
    throws SQLException
  {
    return DRIVER_NAME;
  }

  /** @deprecated */
  public static String getDriverVersionInfo()
    throws SQLException
  {
    return DRIVER_VERSION;
  }

  /** @deprecated */
  public static int getDriverMajorVersionInfo()
  {
    return DRIVER_MAJOR_VERSION;
  }

  /** @deprecated */
  public static int getDriverMinorVersionInfo()
  {
    return DRIVER_MINOR_VERSION;
  }

  /** @deprecated */
  public static String getLobPrecision()
    throws SQLException
  {
    return "-1";
  }

  public long getLobMaxLength()
    throws SQLException
  {
    return this.connection.getVersionNumber() >= 10000 ? 9223372036854775807L : LOB_MAXLENGTH_32BIT;
  }

  public RowIdLifetime getRowIdLifetime()
    throws SQLException
  {
    return RowIdLifetime.ROWID_VALID_FOREVER;
  }

  public ResultSet getSchemas(String paramString1, String paramString2)
    throws SQLException
  {
    if (paramString2 == null) {
      return getSchemas();
    }

    String str = "SELECT username AS table_schem FROM all_users WHERE username LIKE ? ORDER BY table_schem";

    PreparedStatement preparedstatement = this.connection.prepareStatement(str);
    preparedstatement.setString(1, paramString2);
    OracleResultSet localOracleResultSet = (OracleResultSet)preparedstatement.executeQuery();

    localOracleResultSet.closeStatementOnClose();
    return localOracleResultSet;
  }

  public boolean supportsStoredFunctionsUsingCallSyntax()
    throws SQLException
  {
    return true;
  }

  public boolean autoCommitFailureClosesAllResultSets()
    throws SQLException
  {
    return false;
  }

  public ResultSet getClientInfoProperties()
    throws SQLException
  {
    Statement localStatement = this.connection.createStatement();
    return localStatement.executeQuery("select NULL NAME, -1 MAX_LEN, NULL DEFAULT_VALUE, NULL DESCRIPTION  from dual where 0 = 1");
  }

  public ResultSet getFunctions(String paramString1, String paramString2, String paramString3)
    throws SQLException
  {
    String str1 = "SELECT\n  -- Standalone functions\n  NULL AS function_cat,\n  owner AS function_schem,\n  object_name AS function_name,\n  'Standalone function' AS remarks,\n  0 AS function_type,\n  NULL AS specific_name\nFROM all_objects\nWHERE object_type = 'FUNCTION'\n  AND owner LIKE :1 ESCAPE '/'\n  AND object_name LIKE :2 ESCAPE '/'\n";

    String str2 = "SELECT\n  -- Packaged functions\n  package_name AS function_cat,\n  owner AS function_schem,\n  object_name AS function_name,\n  'Packaged function' AS remarks,\n  decode (data_type, 'TABLE', 2, 'PL/SQL TABLE', 2, 1) AS function_type,\n  NULL AS specific_name\nFROM all_arguments\nWHERE argument_name IS NULL\n  AND in_out = 'OUT'\n  AND data_level = 0\n";

    String str3 = "  AND package_name LIKE :3 ESCAPE '/'\n  AND owner LIKE :4 ESCAPE '/'\n  AND object_name LIKE :5 ESCAPE '/'\n";

    String str4 = "  AND package_name IS NOT NULL\n  AND owner LIKE :6 ESCAPE '/'\n  AND object_name LIKE :7 ESCAPE '/'\n";

    String str5 = "ORDER BY function_schem, function_name\n";

    PreparedStatement preparedstatement = null;
    String str6 = null;

    String str7 = paramString2;

    if (paramString2 == null)
      str7 = "%";
    else if (paramString2.equals("")) {
      str7 = getUserName().toUpperCase();
    }
    String str8 = paramString3;

    if (paramString3 == null) {
      str8 = "%";
    } else if (paramString3.equals(""))
    {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 74);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    if (paramString1 == null)
    {
      str6 = new StringBuilder().append(str1).append("UNION ALL ").append(str2).append(str4).append(str5).toString();

      preparedstatement = this.connection.prepareStatement(str6);

      preparedstatement.setString(1, str7);
      preparedstatement.setString(2, str8);
      preparedstatement.setString(3, str7);
      preparedstatement.setString(4, str8);
    }
    else if (paramString1.equals(""))
    {
      str6 = str1;

      preparedstatement = this.connection.prepareStatement(str6);

      preparedstatement.setString(1, str7);
      preparedstatement.setString(2, str8);
    }
    else
    {
      str6 = new StringBuilder().append(str2).append(str3).append(str5).toString();

      preparedstatement = this.connection.prepareStatement(str6);

      preparedstatement.setString(1, str7);
      preparedstatement.setString(2, str7);
      preparedstatement.setString(3, str8);
    }

    OracleResultSet oracleresultset = (OracleResultSet)preparedstatement.executeQuery();

    oracleresultset.closeStatementOnClose();

    return oracleresultset;
  }

  public boolean isWrapperFor(Class<?> paramClass)
    throws SQLException
  {
    if (paramClass.isInterface()) return paramClass.isInstance(this);

    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 177);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public <T> T unwrap(Class<T> paramClass)
    throws SQLException
  {
    if ((paramClass.isInterface()) && (paramClass.isInstance(this))) return (T)this;

    SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 177);
    sqlexception.fillInStackTrace();
    throw sqlexception;
  }

  protected oracle.jdbc.internal.OracleConnection getConnectionDuringExceptionHandling()
  {
    return this.connection;
  }

  protected boolean hasSqlWildcard(String paramString)
  {
    if (sqlWildcardPattern == null)
      sqlWildcardPattern = Pattern.compile("^%|^_|[^/]%|[^/]_");
    Matcher localMatcher = sqlWildcardPattern.matcher(paramString);
    return localMatcher.find();
  }

  protected String stripSqlEscapes(String paramString)
  {
    if (sqlEscapePattern == null)
      sqlEscapePattern = Pattern.compile("/");
    Matcher localMatcher = sqlEscapePattern.matcher(paramString);
    StringBuffer localStringBuffer = new StringBuffer();
    while (localMatcher.find())
    {
      localMatcher.appendReplacement(localStringBuffer, "");
    }
    localMatcher.appendTail(localStringBuffer);
    return localStringBuffer.toString();
  }

  String getUnpackagedProcedureColumnsNoWildcardsPlsql()
    throws SQLException
  {
    String str1 = "declare\n  in_owner varchar2(32) := null;\n  in_name varchar2(32) := null;\n  my_user_name varchar2(32) := null;\n  cnt number := 0;\n  temp_owner varchar2(32) := null;\n  temp_name  varchar2(32):= null;\n  out_owner varchar2(32) := null;\n  out_name  varchar2(32):= null;\n  loc varchar2(32) := null;\n  status number := 0;\n  TYPE recursion_check_type is table of number index by varchar2(65);\n  recursion_check recursion_check_type;\n  dotted_name varchar2(65);\n  recursion_cnt number := 0;\n  xxx SYS_REFCURSOR;\nbegin\n  in_owner := ?;\n  in_name := ?;\n  select user into my_user_name from dual;\n  if( my_user_name = in_owner ) then\n    select count(*) into cnt from user_procedures where object_name = in_name;\n    if( cnt = 1 ) then\n      out_owner := in_owner;\n      out_name := in_name;\n      loc := 'USER_PROCEDURES';\n    end if;\n  else\n    select count(*) into cnt from all_arguments where owner = in_owner and object_name = in_name;\n    if( cnt = 1 ) then\n      out_owner := in_owner;\n      out_name := in_name;\n      loc := 'ALL_ARGUMENTS';\n    end if;\n  end if;\n  if loc is null then\n    temp_owner := in_owner;\n    temp_name := in_name;\n    loop\n      begin\n        dotted_name := temp_owner || '.' ||temp_name;\n        begin\n          recursion_cnt := recursion_check(dotted_name );\n          status := -1;\n          exit;\n        exception\n        when NO_DATA_FOUND then\n          recursion_check( dotted_name ) := 1;\n        end;\n        select table_owner, table_name into out_owner, out_name from all_synonyms \n          where  owner = temp_owner and synonym_name = temp_name;\n        cnt := cnt + 1;\n        temp_owner  := out_owner;\n        temp_name := out_name;\n        exception\n        when NO_DATA_FOUND then\n          exit;\n        end;\n      end loop;\n      if( not(out_owner is null) ) then\n        loc := 'ALL_SYNONYMS';\n    end if;\n  end if;\n";

    int i = this.connection.getVersionNumber();
    String str2 = "if( status = 0 ) then \n open xxx for \n";

    String str3 = new StringBuilder().append("SELECT package_name AS procedure_cat,\n       owner AS procedure_schem,\n       object_name AS procedure_name,\n       argument_name AS column_name,\n       DECODE(position, 0, 5,\n                        DECODE(in_out, 'IN', 1,\n                                       'OUT', 4,\n                                       'IN/OUT', 2,\n                                       0)) AS column_type,\n       DECODE (data_type, 'CHAR', 1,\n                          'VARCHAR2', 12,\n                          'NUMBER', 3,\n                          'LONG', -1,\n                          'DATE', ").append(this.connection.getMapDateToTimestamp() ? "93,\n" : "91,\n").append("                          'RAW', -3,\n").append("                          'LONG RAW', -4,\n").append("                          'TIMESTAMP', 93, \n").append("                          'TIMESTAMP WITH TIME ZONE', -101, \n").append("               'TIMESTAMP WITH LOCAL TIME ZONE', -102, \n").append("               'INTERVAL YEAR TO MONTH', -103, \n").append("               'INTERVAL DAY TO SECOND', -104, \n").append("               'BINARY_FLOAT', 100, 'BINAvRY_DOUBLE', 101,").append("               1111) AS data_type,\n").append("       DECODE(data_type, 'OBJECT', type_owner || '.' || type_name, ").append("              data_type) AS type_name,\n").append("       DECODE (data_precision, NULL, data_length,\n").append("                               data_precision) AS precision,\n").append("       data_length AS length,\n").append("       data_scale AS scale,\n").append("       10 AS radix,\n").append("       1 AS nullable,\n").append("       NULL AS remarks,\n").append("       default_value AS column_def,\n").append("       NULL as sql_data_type,\n").append("       NULL AS sql_datetime_sub,\n").append("       DECODE(data_type,\n").append("                         'CHAR', 32767,\n").append("                         'VARCHAR2', 32767,\n").append("                         'LONG', 32767,\n").append("                         'RAW', 32767,\n").append("                         'LONG RAW', 32767,\n").append("                         NULL) AS char_octet_length,\n").append("       (sequence - 1) AS ordinal_position,\n").append("       'YES' AS is_nullable,\n").append("       NULL AS specific_name,\n").append("       sequence,\n").append("       overload,\n").append("       default_value\n").toString();

    String str4 = "FROM all_arguments a";

    String str5 = "WHERE a.owner = out_owner \n  AND a.object_name = out_name\n AND a.argument_name LIKE ? ESCAPE '/'\n AND data_level = 0\n AND package_name is null\n";

    String str6 = "ORDER BY procedure_schem, procedure_name, overload, sequence\n";

    String str7 = str2;
    str7 = new StringBuilder().append(str7).append(str3).toString();

    str7 = new StringBuilder().append(str7).append(str4).toString();

    str7 = new StringBuilder().append(str7).append("\n").append(str5).toString();

    str7 = new StringBuilder().append(str7).append("\n").append(str6).toString();

    String str8 = "; \n end if;\n  ? := xxx; ? := status;\n end;";

    String str9 = new StringBuilder().append(str1).append(str7).append(str8).toString();
    return str9;
  }

  String getPackagedProcedureColumnsNoWildcardsPlsql()
    throws SQLException
  {
    String str1 = "declare\n  in_package_name varchar2(32) := null;\n  in_owner varchar2(32) := null;\n  in_name varchar2(32) := null;\n  my_user_name varchar2(32) := null;\n  cnt number := 0;\n  temp_package_name varchar2(32) := null;\n  temp_owner varchar2(32) := null;\n  out_package_name varchar2(32) := null;\n  out_owner varchar2(32) := null;\n  loc varchar2(32) := null;\n  status number := 0;\n  TYPE recursion_check_type is table of number index by varchar2(65);\n  recursion_check recursion_check_type;\n  dotted_name varchar2(65);\n  recursion_cnt number := 0;\n  xxx SYS_REFCURSOR;\nbegin\n  in_package_name := ?;\n  in_owner := ?;\n  in_name := ?;\n  select user into my_user_name from dual;\n  if( my_user_name = in_owner ) then\n    select count(*) into cnt from user_arguments where package_name = in_package_name;\n    if( cnt >= 1 ) then\n      out_owner := in_owner;\n      out_package_name := in_package_name;\n      loc := 'USER_ARGUMENTS';\n    end if;\n  else\n    select count(*) into cnt from all_arguments where owner = in_owner and package_name = in_package_name;\n    if( cnt >= 1 ) then\n      out_owner := in_owner;\n      out_package_name := in_package_name;\n      loc := 'ALL_ARGUMENTS';\n    end if;\n  end if;\n  if loc is null then\n  temp_owner := in_owner;\n  temp_package_name := in_package_name;\n  loop\n    begin\n      dotted_name := temp_owner || '.' ||temp_package_name;\n      begin\n        recursion_cnt := recursion_check(dotted_name );\n        status := -1;\n        exit;\n      exception\n      when NO_DATA_FOUND then\n        recursion_check( dotted_name ) := 1;\n      end;\n      select table_owner, table_name into out_owner, out_package_name from all_synonyms \n        where  owner = temp_owner and synonym_name = temp_package_name;\n      cnt := cnt + 1;\n      temp_owner  := out_owner;\n      temp_package_name := out_package_name;\n      exception\n      when NO_DATA_FOUND then\n        exit;\n      end;\n    end loop;\n    if( not(out_owner is null) ) then\n      loc := 'ALL_SYNONYMS';\n    end if;\n  end if;\n";

    int i = this.connection.getVersionNumber();
    String str2 = "if( status = 0 ) then \n open xxx for \n";

    String str3 = new StringBuilder().append("SELECT package_name AS procedure_cat,\n       owner AS procedure_schem,\n       object_name AS procedure_name,\n       argument_name AS column_name,\n       DECODE(position, 0, 5,\n                        DECODE(in_out, 'IN', 1,\n                                       'OUT', 4,\n                                       'IN/OUT', 2,\n                                       0)) AS column_type,\n       DECODE (data_type, 'CHAR', 1,\n                          'VARCHAR2', 12,\n                          'NUMBER', 3,\n                          'LONG', -1,\n                          'DATE', ").append(this.connection.getMapDateToTimestamp() ? "93,\n" : "91,\n").append("                          'RAW', -3,\n").append("                          'LONG RAW', -4,\n").append("                          'TIMESTAMP', 93, \n").append("                          'TIMESTAMP WITH TIME ZONE', -101, \n").append("               'TIMESTAMP WITH LOCAL TIME ZONE', -102, \n").append("               'INTERVAL YEAR TO MONTH', -103, \n").append("               'INTERVAL DAY TO SECOND', -104, \n").append("               'BINARY_FLOAT', 100, 'BINAvRY_DOUBLE', 101,").append("               1111) AS data_type,\n").append("       DECODE(data_type, 'OBJECT', type_owner || '.' || type_name, ").append("              data_type) AS type_name,\n").append("       DECODE (data_precision, NULL, data_length,\n").append("                               data_precision) AS precision,\n").append("       data_length AS length,\n").append("       data_scale AS scale,\n").append("       10 AS radix,\n").append("       1 AS nullable,\n").append("       NULL AS remarks,\n").append("       default_value AS column_def,\n").append("       NULL as sql_data_type,\n").append("       NULL AS sql_datetime_sub,\n").append("       DECODE(data_type,\n").append("                         'CHAR', 32767,\n").append("                         'VARCHAR2', 32767,\n").append("                         'LONG', 32767,\n").append("                         'RAW', 32767,\n").append("                         'LONG RAW', 32767,\n").append("                         NULL) AS char_octet_length,\n").append("       (sequence - 1) AS ordinal_position,\n").append("       'YES' AS is_nullable,\n").append("       NULL AS specific_name,\n").append("       sequence,\n").append("       overload,\n").append("       default_value\n").toString();

    String str4 = "FROM all_arguments a";

    String str5 = "WHERE a.owner = out_owner \n  AND a.object_name LIKE in_name ESCAPE '/' \n AND a.argument_name LIKE ? ESCAPE '/' \n AND data_level = 0\n AND package_name = out_package_name\n";

    String str6 = "ORDER BY procedure_schem, procedure_name, overload, sequence\n";

    String str7 = str2;
    str7 = new StringBuilder().append(str7).append(str3).toString();

    str7 = new StringBuilder().append(str7).append(str4).toString();

    str7 = new StringBuilder().append(str7).append("\n").append(str5).toString();

    str7 = new StringBuilder().append(str7).append("\n").append(str6).toString();

    String str8 = "; \n end if;\n  ? := xxx; ? := status;\n end;";

    String str9 = new StringBuilder().append(str1).append(str7).append(str8).toString();
    return str9;
  }
}