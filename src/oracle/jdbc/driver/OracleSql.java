package oracle.jdbc.driver;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import oracle.jdbc.internal.OracleConnection;

public class OracleSql
{
  static final int UNINITIALIZED = -1;
  static final String[] EMPTY_LIST = new String[0];
  DBConversion conversion;
  String originalSql;
  String parameterSql;
  String utickSql;
  String processedSql;
  String rowidSql;
  String actualSql;
  byte[] sqlBytes;
  byte sqlKind = 0;
  int parameterCount = -1;
  boolean currentConvertNcharLiterals = true;
  boolean currentProcessEscapes = true;
  boolean includeRowid = false;
  String[] parameterList = EMPTY_LIST;
  char[] currentParameter = null;

  int bindParameterCount = -1;
  String[] bindParameterList = null;
  int cachedBindParameterCount = -1;
  String[] cachedBindParameterList = null;
  String cachedParameterSql;
  String cachedUtickSql;
  String cachedProcessedSql;
  String cachedRowidSql;
  String cachedActualSql;
  byte[] cachedSqlBytes;
  int selectEndIndex = -1;
  int orderByStartIndex = -1;
  int orderByEndIndex = -1;
  int whereStartIndex = -1;
  int whereEndIndex = -1;
  int forUpdateStartIndex = -1;
  int forUpdateEndIndex = -1;

  int[] ncharLiteralLocation = new int[513];
  int lastNcharLiteralLocation = -1;
  static final String paramPrefix = "rowid";
  int paramSuffix = 0;

  StringBuffer stringBufferForScrollableStatement = null;
  private static final int cMax = 127;
  private static final int[][] TRANSITION = OracleSqlReadOnly.TRANSITION;

  private static final int[][] ACTION = OracleSqlReadOnly.ACTION;
  private static final int NO_ACTION = 0;
  private static final int DELETE_ACTION = 1;
  private static final int INSERT_ACTION = 2;
  private static final int MERGE_ACTION = 3;
  private static final int UPDATE_ACTION = 4;
  private static final int PLSQL_ACTION = 5;
  private static final int CALL_ACTION = 6;
  private static final int SELECT_ACTION = 7;
  private static final int ORDER_ACTION = 10;
  private static final int ORDER_BY_ACTION = 11;
  private static final int WHERE_ACTION = 9;
  private static final int FOR_ACTION = 12;
  private static final int FOR_UPDATE_ACTION = 13;
  private static final int OTHER_ACTION = 8;
  private static final int QUESTION_ACTION = 14;
  private static final int PARAMETER_ACTION = 15;
  private static final int END_PARAMETER_ACTION = 16;
  private static final int START_NCHAR_LITERAL_ACTION = 17;
  private static final int END_NCHAR_LITERAL_ACTION = 18;
  private static final int SAVE_DELIMITER_ACTION = 19;
  private static final int LOOK_FOR_DELIMITER_ACTION = 20;
  private static final int INITIAL_STATE = 0;
  private static final int RESTART_STATE = 66;
  private static final OracleSqlReadOnly.ODBCAction[][] ODBC_ACTION = OracleSqlReadOnly.ODBC_ACTION;
  private static final boolean DEBUG_CBI = false;
  int current_argument;
  int i;
  int length;
  char c;
  boolean first;
  String odbc_sql;
  StringBuffer oracle_sql;
  StringBuffer token_buffer;
  static final Map<Byte, String> sqlKindStrings = new HashMap();

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  protected OracleSql(DBConversion paramDBConversion)
  {
    this.conversion = paramDBConversion;
  }

  protected void initialize(String paramString)
    throws SQLException
  {
    if ((paramString == null) || (paramString.length() == 0))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 104);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.originalSql = paramString;
    this.utickSql = null;
    this.processedSql = null;
    this.rowidSql = null;
    this.actualSql = null;
    this.sqlBytes = null;
    this.sqlKind = 0;
    this.parameterCount = -1;
    this.parameterList = EMPTY_LIST;
    this.includeRowid = false;

    this.parameterSql = this.originalSql;
    this.bindParameterCount = -1;
    this.bindParameterList = null;
    this.cachedBindParameterCount = -1;
    this.cachedBindParameterList = null;
    this.cachedParameterSql = null;
    this.cachedActualSql = null;
    this.cachedProcessedSql = null;
    this.cachedRowidSql = null;
    this.cachedSqlBytes = null;
    this.selectEndIndex = -1;
    this.orderByStartIndex = -1;
    this.orderByEndIndex = -1;
    this.whereStartIndex = -1;
    this.whereEndIndex = -1;
    this.forUpdateStartIndex = -1;
    this.forUpdateEndIndex = -1;
  }

  String getOriginalSql()
  {
    return this.originalSql;
  }

  boolean setNamedParameters(int paramInt, String[] paramArrayOfString)
    throws SQLException
  {
    boolean bool = false;

    if (paramInt == 0)
    {
      this.bindParameterCount = -1;
      bool = this.bindParameterCount != this.cachedBindParameterCount;
    }
    else
    {
      this.bindParameterCount = paramInt;
      this.bindParameterList = paramArrayOfString;
      bool = this.bindParameterCount != this.cachedBindParameterCount;

      if (!bool) {
        for (int j = 0; j < paramInt; j++)
          if (this.bindParameterList[j] != this.cachedBindParameterList[j])
          {
            bool = true;

            break;
          }
      }
      if (bool)
      {
        if (this.bindParameterCount != getParameterCount())
        {
          localObject = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 197);
          ((SQLException)localObject).fillInStackTrace();
          throw ((Throwable)localObject);
        }

        Object localObject = this.originalSql.toCharArray();
        StringBuffer localStringBuffer = new StringBuffer();

        int k = 0;

        for (int m = 0; m < localObject.length; m++)
        {
          if (localObject[m] != '?')
          {
            localStringBuffer.append(localObject[m]);
          }
          else
          {
            localStringBuffer.append(this.bindParameterList[(k++)]);
            localStringBuffer.append(new StringBuilder().append("=>").append(nextArgument()).toString());
          }
        }

        this.parameterSql = localStringBuffer.toString();
        this.actualSql = null;
        this.utickSql = null;
        this.processedSql = null;
        this.rowidSql = null;
        this.sqlBytes = null;
      }
      else
      {
        this.parameterSql = this.cachedParameterSql;
        this.actualSql = this.cachedActualSql;
        this.utickSql = this.cachedUtickSql;
        this.processedSql = this.cachedProcessedSql;
        this.rowidSql = this.cachedRowidSql;
        this.sqlBytes = this.cachedSqlBytes;
      }
    }

    this.cachedBindParameterList = null;
    this.cachedParameterSql = null;
    this.cachedActualSql = null;
    this.cachedUtickSql = null;
    this.cachedProcessedSql = null;
    this.cachedRowidSql = null;
    this.cachedSqlBytes = null;

    return bool;
  }

  void resetNamedParameters()
  {
    this.cachedBindParameterCount = this.bindParameterCount;

    if (this.bindParameterCount != -1)
    {
      if ((this.cachedBindParameterList == null) || (this.cachedBindParameterList == this.bindParameterList) || (this.cachedBindParameterList.length < this.bindParameterCount))
      {
        this.cachedBindParameterList = new String[this.bindParameterCount];
      }
      System.arraycopy(this.bindParameterList, 0, this.cachedBindParameterList, 0, this.bindParameterCount);

      this.cachedParameterSql = this.parameterSql;
      this.cachedActualSql = this.actualSql;
      this.cachedUtickSql = this.utickSql;
      this.cachedProcessedSql = this.processedSql;
      this.cachedRowidSql = this.rowidSql;
      this.cachedSqlBytes = this.sqlBytes;

      this.bindParameterCount = -1;
      this.bindParameterList = null;
      this.parameterSql = this.originalSql;
      this.actualSql = null;
      this.utickSql = null;
      this.processedSql = null;
      this.rowidSql = null;
      this.sqlBytes = null;
    }
  }

  String getSql(boolean paramBoolean1, boolean paramBoolean2)
    throws SQLException
  {
    if (this.sqlKind == 0) computeBasicInfo(this.parameterSql);
    if ((paramBoolean1 != this.currentProcessEscapes) || (paramBoolean2 != this.currentConvertNcharLiterals))
    {
      if (paramBoolean2 != this.currentConvertNcharLiterals) this.utickSql = null;
      this.processedSql = null;
      this.rowidSql = null;
      this.actualSql = null;
      this.sqlBytes = null;
    }

    this.currentConvertNcharLiterals = paramBoolean2;
    this.currentProcessEscapes = paramBoolean1;

    if (this.actualSql == null)
    {
      if (this.utickSql == null)
        this.utickSql = (this.currentConvertNcharLiterals ? convertNcharLiterals(this.parameterSql) : this.parameterSql);
      if (this.processedSql == null)
        this.processedSql = (this.currentProcessEscapes ? parse(this.utickSql) : this.utickSql);
      if (this.rowidSql == null)
        this.rowidSql = (this.includeRowid ? addRowid(this.processedSql) : this.processedSql);
      this.actualSql = this.rowidSql;
    }

    return this.actualSql;
  }

  String getRevisedSql()
    throws SQLException
  {
    String str = null;

    if (this.sqlKind == 0) computeBasicInfo(this.parameterSql);

    str = removeForUpdate(this.parameterSql);

    return addRowid(str);
  }

  String removeForUpdate(String paramString)
    throws SQLException
  {
    if ((this.orderByStartIndex != -1) && ((this.forUpdateStartIndex == -1) || (this.forUpdateStartIndex > this.orderByStartIndex)))
    {
      paramString = paramString.substring(0, this.orderByStartIndex);
    }
    else if (this.forUpdateStartIndex != -1)
    {
      paramString = paramString.substring(0, this.forUpdateStartIndex);
    }

    return paramString;
  }

  void appendForUpdate(StringBuffer paramStringBuffer)
    throws SQLException
  {
    if ((this.orderByStartIndex != -1) && ((this.forUpdateStartIndex == -1) || (this.forUpdateStartIndex > this.orderByStartIndex)))
    {
      paramStringBuffer.append(this.originalSql.substring(this.orderByStartIndex));
    }
    else if (this.forUpdateStartIndex != -1)
    {
      paramStringBuffer.append(this.originalSql.substring(this.forUpdateStartIndex));
    }
  }

  String getInsertSqlForUpdatableResultSet(UpdatableResultSet paramUpdatableResultSet)
    throws SQLException
  {
    String str = getOriginalSql();
    boolean bool = generatedSqlNeedEscapeProcessing();

    if (this.stringBufferForScrollableStatement == null)
      this.stringBufferForScrollableStatement = new StringBuffer(str.length() + 100);
    else {
      this.stringBufferForScrollableStatement.delete(0, this.stringBufferForScrollableStatement.length());
    }
    this.stringBufferForScrollableStatement.append("insert into (");

    this.stringBufferForScrollableStatement.append(removeForUpdate(str));
    this.stringBufferForScrollableStatement.append(") values ( ");

    for (int j = 1; 
      j < paramUpdatableResultSet.getColumnCount(); 
      j++)
    {
      if (j != 1) {
        this.stringBufferForScrollableStatement.append(", ");
      }
      if (bool)
        this.stringBufferForScrollableStatement.append("?");
      else {
        this.stringBufferForScrollableStatement.append(new StringBuilder().append(":").append(generateParameterName()).toString());
      }
    }
    this.stringBufferForScrollableStatement.append(")");

    this.paramSuffix = 0;

    return this.stringBufferForScrollableStatement.substring(0, this.stringBufferForScrollableStatement.length());
  }

  String getRefetchSqlForScrollableResultSet(ScrollableResultSet paramScrollableResultSet, int paramInt)
    throws SQLException
  {
    String str = getRevisedSql();
    boolean bool = generatedSqlNeedEscapeProcessing();

    if (this.stringBufferForScrollableStatement == null)
      this.stringBufferForScrollableStatement = new StringBuffer(str.length() + 100);
    else {
      this.stringBufferForScrollableStatement.delete(0, this.stringBufferForScrollableStatement.length());
    }

    this.stringBufferForScrollableStatement.append(str);

    if (this.whereStartIndex == -1)
    {
      this.stringBufferForScrollableStatement.append(bool ? " WHERE ( ROWID = ?" : new StringBuilder().append(" WHERE ( ROWID = :").append(generateParameterName()).toString());
    }
    else {
      this.stringBufferForScrollableStatement.append(bool ? " AND ( ROWID = ?" : new StringBuilder().append(" AND ( ROWID = :").append(generateParameterName()).toString());
    }

    for (int j = 0; j < paramInt - 1; j++) {
      if (bool) {
        this.stringBufferForScrollableStatement.append(" OR ROWID = ?");
      }
      else
        this.stringBufferForScrollableStatement.append(new StringBuilder().append(" OR ROWID = :").append(generateParameterName()).toString());
    }
    this.stringBufferForScrollableStatement.append(" ) ");

    appendForUpdate(this.stringBufferForScrollableStatement);

    this.paramSuffix = 0;

    return this.stringBufferForScrollableStatement.substring(0, this.stringBufferForScrollableStatement.length());
  }

  String getUpdateSqlForUpdatableResultSet(UpdatableResultSet paramUpdatableResultSet, int paramInt, Object[] paramArrayOfObject, int[] paramArrayOfInt)
    throws SQLException
  {
    String str = getRevisedSql();
    boolean bool = generatedSqlNeedEscapeProcessing();

    if (this.stringBufferForScrollableStatement == null)
      this.stringBufferForScrollableStatement = new StringBuffer(str.length() + 100);
    else {
      this.stringBufferForScrollableStatement.delete(0, this.stringBufferForScrollableStatement.length());
    }

    this.stringBufferForScrollableStatement.append("update (");
    this.stringBufferForScrollableStatement.append(str);
    this.stringBufferForScrollableStatement.append(") set ");

    if (paramArrayOfObject != null)
    {
      for (int j = 0; j < paramInt; j++)
      {
        if (j > 0) {
          this.stringBufferForScrollableStatement.append(", ");
        }
        this.stringBufferForScrollableStatement.append(paramUpdatableResultSet.getInternalMetadata().getColumnName(paramArrayOfInt[j] + 1));

        if (bool)
          this.stringBufferForScrollableStatement.append(" = ?");
        else {
          this.stringBufferForScrollableStatement.append(new StringBuilder().append(" = :").append(generateParameterName()).toString());
        }
      }
    }

    this.stringBufferForScrollableStatement.append(" WHERE ");
    if (bool)
      this.stringBufferForScrollableStatement.append(" ROWID = ?");
    else {
      this.stringBufferForScrollableStatement.append(new StringBuilder().append(" ROWID = :").append(generateParameterName()).toString());
    }

    this.paramSuffix = 0;

    return this.stringBufferForScrollableStatement.substring(0, this.stringBufferForScrollableStatement.length());
  }

  String getDeleteSqlForUpdatableResultSet(UpdatableResultSet paramUpdatableResultSet)
    throws SQLException
  {
    String str = getRevisedSql();
    boolean bool = generatedSqlNeedEscapeProcessing();

    if (this.stringBufferForScrollableStatement == null)
      this.stringBufferForScrollableStatement = new StringBuffer(str.length() + 100);
    else {
      this.stringBufferForScrollableStatement.delete(0, this.stringBufferForScrollableStatement.length());
    }

    this.stringBufferForScrollableStatement.append("delete from (");
    this.stringBufferForScrollableStatement.append(str);
    this.stringBufferForScrollableStatement.append(") where ");

    if (bool)
      this.stringBufferForScrollableStatement.append(" ROWID = ?");
    else {
      this.stringBufferForScrollableStatement.append(new StringBuilder().append(" ROWID = :").append(generateParameterName()).toString());
    }

    this.paramSuffix = 0;

    return this.stringBufferForScrollableStatement.substring(0, this.stringBufferForScrollableStatement.length());
  }

  final boolean generatedSqlNeedEscapeProcessing()
  {
    return (this.parameterCount > 0) && (this.parameterList == EMPTY_LIST);
  }

  byte[] getSqlBytes(boolean paramBoolean1, boolean paramBoolean2)
    throws SQLException
  {
    if ((this.sqlBytes == null) || (paramBoolean1 != this.currentProcessEscapes))
    {
      this.sqlBytes = this.conversion.StringToCharBytes(getSql(paramBoolean1, paramBoolean2));
    }

    return this.sqlBytes;
  }

  byte getSqlKind()
    throws SQLException
  {
    if (this.sqlKind == 0) {
      computeBasicInfo(this.parameterSql);
    }
    return this.sqlKind;
  }

  protected int getParameterCount()
    throws SQLException
  {
    if (this.parameterCount == -1)
    {
      computeBasicInfo(this.parameterSql);
    }

    return this.parameterCount;
  }

  protected String[] getParameterList()
    throws SQLException
  {
    if (this.parameterCount == -1)
    {
      computeBasicInfo(this.parameterSql);
    }

    return this.parameterList;
  }

  void setIncludeRowid(boolean paramBoolean)
  {
    if (paramBoolean != this.includeRowid)
    {
      this.includeRowid = paramBoolean;
      this.rowidSql = null;
      this.actualSql = null;
      this.sqlBytes = null;
    }
  }

  public String toString()
  {
    return this.parameterSql == null ? "null" : this.parameterSql;
  }

  private String hexUnicode(int paramInt)
    throws SQLException
  {
    String str = Integer.toHexString(paramInt);
    switch (str.length()) {
    case 0:
      return "\\0000";
    case 1:
      return new StringBuilder().append("\\000").append(str).toString();
    case 2:
      return new StringBuilder().append("\\00").append(str).toString();
    case 3:
      return new StringBuilder().append("\\0").append(str).toString();
    case 4:
      return new StringBuilder().append("\\").append(str).toString();
    }

    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 89, new StringBuilder().append("Unexpected case in OracleSql.hexUnicode: ").append(paramInt).toString());
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  String convertNcharLiterals(String paramString)
    throws SQLException
  {
    if (this.lastNcharLiteralLocation <= 2) return paramString;
    String str = "";
    int j = 0;
    while (true)
    {
      int k = this.ncharLiteralLocation[(j++)];
      int m = this.ncharLiteralLocation[(j++)];

      str = new StringBuilder().append(str).append(paramString.substring(k, m)).toString();
      if (j >= this.lastNcharLiteralLocation) break;
      k = this.ncharLiteralLocation[j];
      str = new StringBuilder().append(str).append("u'").toString();

      for (int n = m + 2; n < k; n++)
      {
        char c1 = paramString.charAt(n);
        if (c1 == '\\') str = new StringBuilder().append(str).append("\\\\").toString();
        else if (c1 < '') str = new StringBuilder().append(str).append(c1).toString(); else
          str = new StringBuilder().append(str).append(hexUnicode(c1)).toString();
      }
    }
    return str;
  }

  void computeBasicInfo(String paramString)
    throws SQLException
  {
    this.parameterCount = 0;

    this.lastNcharLiteralLocation = 0;
    this.ncharLiteralLocation[(this.lastNcharLiteralLocation++)] = 0;

    char c1 = '\000';

    int j = 0;

    int k = 0;
    int m = paramString.length();
    int n = -1;
    int i1 = -1;
    int i2 = m + 1;

    for (int i3 = 0; i3 < i2; i3++)
    {
      char c2 = i3 < m ? paramString.charAt(i3) : ' ';
      char c3 = c2;

      if (c2 > '')
      {
        if (Character.isLetterOrDigit(c2))
          c3 = 'X';
        else
          c3 = ' ';
      }
      Object localObject;
      switch (ACTION[k][c3])
      {
      case 0:
        break;
      case 1:
        this.sqlKind = 2;

        break;
      case 2:
        this.sqlKind = 4;

        break;
      case 3:
        this.sqlKind = 8;

        break;
      case 4:
        this.sqlKind = 16;

        break;
      case 5:
        this.sqlKind = 32;

        break;
      case 6:
        this.sqlKind = 64;

        break;
      case 7:
        this.sqlKind = 1;
        this.selectEndIndex = i3;

        break;
      case 8:
        this.sqlKind = -128;

        break;
      case 9:
        this.whereStartIndex = (i3 - 5);
        this.whereEndIndex = i3;

        break;
      case 10:
        n = i3 - 5;

        break;
      case 11:
        this.orderByStartIndex = n;
        this.orderByEndIndex = i3;

        break;
      case 12:
        i1 = i3 - 3;

        break;
      case 13:
        this.forUpdateStartIndex = i1;
        this.forUpdateEndIndex = i3;

        break;
      case 14:
        this.parameterCount += 1;

        break;
      case 15:
        if (this.currentParameter == null) {
          this.currentParameter = new char[32];
        }
        if (j >= this.currentParameter.length)
        {
          localObject = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 134, new String(this.currentParameter));
          ((SQLException)localObject).fillInStackTrace();
          throw ((Throwable)localObject);
        }

        this.currentParameter[(j++)] = c2;

        break;
      case 16:
        if (j > 0)
        {
          if (this.parameterList == EMPTY_LIST)
          {
            this.parameterList = new String[8];
          }
          else if (this.parameterList.length <= this.parameterCount)
          {
            localObject = new String[this.parameterList.length * 4];

            System.arraycopy(this.parameterList, 0, localObject, 0, this.parameterList.length);

            this.parameterList = ((String[])localObject);
          }

          this.parameterList[this.parameterCount] = new String(this.currentParameter, 0, j).intern();

          j = 0;
          this.parameterCount += 1; } break;
      case 17:
        this.ncharLiteralLocation[(this.lastNcharLiteralLocation++)] = (i3 - 1);
        if (this.lastNcharLiteralLocation >= this.ncharLiteralLocation.length)
          growNcharLiteralLocation(this.ncharLiteralLocation.length << 2); break;
      case 18:
        this.ncharLiteralLocation[(this.lastNcharLiteralLocation++)] = i3;
        if (this.lastNcharLiteralLocation >= this.ncharLiteralLocation.length)
          growNcharLiteralLocation(this.ncharLiteralLocation.length << 2); break;
      case 19:
        if (c2 == '[') c1 = ']';
        else if (c2 == '{') c1 = '}';
        else if (c2 == '<') c1 = '>';
        else if (c2 == '(') c1 = ')'; else
          c1 = c2;
        break;
      case 20:
        if (c2 == c1)
        {
          k += 1;
        }

        break;
      }

      k = TRANSITION[k][c3];
    }

    if (this.lastNcharLiteralLocation + 2 >= this.ncharLiteralLocation.length)
      growNcharLiteralLocation(this.lastNcharLiteralLocation + 2);
    this.ncharLiteralLocation[(this.lastNcharLiteralLocation++)] = m;
    this.ncharLiteralLocation[this.lastNcharLiteralLocation] = m;
  }

  void growNcharLiteralLocation(int paramInt)
  {
    int[] arrayOfInt = new int[paramInt];
    System.arraycopy(this.ncharLiteralLocation, 0, arrayOfInt, 0, this.ncharLiteralLocation.length);
    this.ncharLiteralLocation = null;
    this.ncharLiteralLocation = arrayOfInt;
  }

  private String addRowid(String paramString)
    throws SQLException
  {
    if (this.selectEndIndex == -1)
    {
      localObject = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 88);
      ((SQLException)localObject).fillInStackTrace();
      throw ((Throwable)localObject);
    }

    Object localObject = new StringBuilder().append("select rowid as \"__Oracle_JDBC_interal_ROWID__\",").append(paramString.substring(this.selectEndIndex)).toString();
    return localObject;
  }

  String parse(String paramString)
    throws SQLException
  {
    this.first = true;
    this.current_argument = 1;
    this.i = 0;
    this.odbc_sql = paramString;
    this.length = this.odbc_sql.length();

    if (this.oracle_sql == null)
    {
      this.oracle_sql = new StringBuffer(this.length);
      this.token_buffer = new StringBuffer(32);
    }
    else
    {
      this.oracle_sql.ensureCapacity(this.length);
    }

    this.oracle_sql.delete(0, this.oracle_sql.length());
    skipSpace();
    handleODBC(ParseMode.NORMAL);

    if (this.i < this.length)
    {
      Integer localInteger = Integer.valueOf(this.i);

      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 33, localInteger);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    return this.oracle_sql.substring(0, this.oracle_sql.length());
  }

  void handleODBC(ParseMode paramParseMode)
    throws SQLException
  {
    int j = paramParseMode == ParseMode.NORMAL ? 0 : 66;
    char c1 = '\000';
    int k = 0;

    while (this.i < this.length)
    {
      char c2 = this.i < this.length ? this.odbc_sql.charAt(this.i) : ' ';
      char c3 = c2;

      if (c2 > '')
      {
        if (Character.isLetterOrDigit(c2))
          c3 = 'X';
        else {
          c3 = ' ';
        }

      }

      switch (1.$SwitchMap$oracle$jdbc$driver$OracleSqlReadOnly$ODBCAction[ODBC_ACTION[j][c3].ordinal()])
      {
      case 1:
        break;
      case 2:
        this.oracle_sql.append(c2);
        break;
      case 3:
        this.oracle_sql.append(nextArgument());
        this.oracle_sql.append(' ');
        break;
      case 4:
        if (c2 == '[') c1 = ']';
        else if (c2 == '{') c1 = '}';
        else if (c2 == '<') c1 = '>';
        else if (c2 == '(') c1 = ')'; else
          c1 = c2;
        this.oracle_sql.append(c2);
        break;
      case 5:
        if (c2 == c1)
        {
          j += 1;
        }
        this.oracle_sql.append(c2);
        break;
      case 6:
        handleFunction();
        break;
      case 7:
        handleCall();
        break;
      case 8:
        handleTime();
        break;
      case 9:
        handleTimestamp();
        break;
      case 10:
        handleDate();
        break;
      case 11:
        handleEscape();
        break;
      case 12:
        handleScalarFunction();
        break;
      case 13:
        handleOuterJoin();
        break;
      case 14:
        SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 34, Integer.valueOf(this.i));
        localSQLException.fillInStackTrace();
        throw localSQLException;
      case 15:
        if (paramParseMode == ParseMode.SCALAR)
        {
          return;
        }

      case 16:
        if ((paramParseMode == ParseMode.LOCATE_1) && (k > 1)) {
          this.oracle_sql.append(c2);
        } else {
          if (paramParseMode == ParseMode.LOCATE_1)
          {
            return;
          }
          if (paramParseMode != ParseMode.LOCATE_2)
          {
            this.oracle_sql.append(c2);
          }
        }
        break;
      case 17:
        if (paramParseMode == ParseMode.LOCATE_1) {
          if (k > 0) this.oracle_sql.append(c2);
          k++;
        }
        else if (paramParseMode == ParseMode.LOCATE_2) {
          k++;
          this.oracle_sql.append(c2);
        }
        else
        {
          this.oracle_sql.append(c2);
        }
        break;
      case 18:
        if (paramParseMode == ParseMode.LOCATE_1) {
          k--;
          this.oracle_sql.append(c2);
        }
        else if ((paramParseMode == ParseMode.LOCATE_2) && (k > 1)) {
          k--;
          this.oracle_sql.append(c2);
        } else {
          if (paramParseMode == ParseMode.LOCATE_2) {
            this.i += 1;

            return;
          }

          this.oracle_sql.append(c2);
        }

        break;
      }

      j = TRANSITION[j][c3];
      this.i += 1;
    }
  }

  void handleFunction()
    throws SQLException
  {
    boolean bool = this.first;
    this.first = false;

    if (bool) {
      this.oracle_sql.append("BEGIN ");
    }
    appendChar(this.oracle_sql, '?');
    skipSpace();
    String str;
    SQLException localSQLException;
    if (this.c != '=')
    {
      str = new StringBuilder().append(this.i).append(". Expecting \"=\" got \"").append(this.c).append("\"").toString();

      localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 33, str);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.i += 1;

    skipSpace();

    if (!this.odbc_sql.startsWith("call", this.i))
    {
      str = new StringBuilder().append(this.i).append(". Expecting \"call\"").toString();

      localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 33, str);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.i += 4;

    this.oracle_sql.append(" := ");
    skipSpace();
    handleODBC(ParseMode.SCALAR);

    if (bool)
      this.oracle_sql.append("; END;");
  }

  void handleCall()
    throws SQLException
  {
    boolean bool = this.first;
    this.first = false;

    if (bool) {
      this.oracle_sql.append("BEGIN ");
    }
    skipSpace();
    handleODBC(ParseMode.SCALAR);
    skipSpace();

    if (bool)
      this.oracle_sql.append("; END;");
  }

  void handleTimestamp()
    throws SQLException
  {
    this.oracle_sql.append("TO_TIMESTAMP (");
    skipSpace();
    handleODBC(ParseMode.SCALAR);
    this.oracle_sql.append(", 'YYYY-MM-DD HH24:MI:SS.FF')");
  }

  void handleTime()
    throws SQLException
  {
    this.oracle_sql.append("TO_DATE (");
    skipSpace();
    handleODBC(ParseMode.SCALAR);
    this.oracle_sql.append(", 'HH24:MI:SS')");
  }

  void handleDate()
    throws SQLException
  {
    this.oracle_sql.append("TO_DATE (");
    skipSpace();
    handleODBC(ParseMode.SCALAR);
    this.oracle_sql.append(", 'YYYY-MM-DD')");
  }

  void handleEscape()
    throws SQLException
  {
    this.oracle_sql.append("ESCAPE ");
    skipSpace();
    handleODBC(ParseMode.SCALAR);
  }

  void handleScalarFunction()
    throws SQLException
  {
    this.token_buffer.delete(0, this.token_buffer.length());

    this.i += 1;

    skipSpace();

    while ((this.i < this.length) && ((Character.isJavaLetterOrDigit(this.c = this.odbc_sql.charAt(this.i))) || (this.c == '?')))
    {
      this.token_buffer.append(this.c);

      this.i += 1;
    }

    String str = this.token_buffer.substring(0, this.token_buffer.length()).toUpperCase().intern();

    if (str == "ABS") {
      usingFunctionName(str);
    } else if (str == "ACOS") {
      usingFunctionName(str);
    } else if (str == "ASIN") {
      usingFunctionName(str);
    } else if (str == "ATAN") {
      usingFunctionName(str);
    } else if (str == "ATAN2") {
      usingFunctionName(str);
    } else if (str == "CEILING") {
      usingFunctionName("CEIL");
    } else if (str == "COS") {
      usingFunctionName(str);
    }
    else
    {
      Object localObject;
      if (str == "COT")
      {
        localObject = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 34, str);
        ((SQLException)localObject).fillInStackTrace();
        throw ((Throwable)localObject);
      }
      if (str == "DEGREES")
      {
        localObject = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 34, str);
        ((SQLException)localObject).fillInStackTrace();
        throw ((Throwable)localObject);
      }
      if (str == "EXP") {
        usingFunctionName(str);
      } else if (str == "FLOOR") {
        usingFunctionName(str);
      } else if (str == "LOG") {
        usingFunctionName("LN");
      } else if (str == "LOG10") {
        replacingFunctionPrefix("LOG ( 10, ");
      } else if (str == "MOD") {
        usingFunctionName(str);
      } else if (str == "PI") {
        replacingFunctionPrefix("( 3.141592653589793238462643383279502884197169399375 ");
      } else if (str == "POWER") {
        usingFunctionName(str); } else {
        if (str == "RADIANS")
        {
          localObject = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 34, str);
          ((SQLException)localObject).fillInStackTrace();
          throw ((Throwable)localObject);
        }
        if (str == "RAND")
        {
          localObject = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 34, str);
          ((SQLException)localObject).fillInStackTrace();
          throw ((Throwable)localObject);
        }
        if (str == "ROUND") {
          usingFunctionName(str);
        } else if (str == "SIGN") {
          usingFunctionName(str);
        } else if (str == "SIN") {
          usingFunctionName(str);
        } else if (str == "SQRT") {
          usingFunctionName(str);
        } else if (str == "TAN") {
          usingFunctionName(str);
        } else if (str == "TRUNCATE") {
          usingFunctionName("TRUNC");
        }
        else if (str == "ASCII") {
          usingFunctionName(str);
        } else if (str == "CHAR") {
          usingFunctionName("CHR");
        }
        else if (str == "CHAR_LENGTH") {
          usingFunctionName("LENGTH");
        } else if (str == "CHARACTER_LENGTH") {
          usingFunctionName("LENGTH");
        }
        else if (str == "CONCAT") {
          usingFunctionName(str); } else {
          if (str == "DIFFERENCE")
          {
            localObject = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 34, str);
            ((SQLException)localObject).fillInStackTrace();
            throw ((Throwable)localObject);
          }
          if (str == "INSERT")
          {
            localObject = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 34, str);
            ((SQLException)localObject).fillInStackTrace();
            throw ((Throwable)localObject);
          }
          if (str == "LCASE") {
            usingFunctionName("LOWER"); } else {
            if (str == "LEFT")
            {
              localObject = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 34, str);
              ((SQLException)localObject).fillInStackTrace();
              throw ((Throwable)localObject);
            }
            if (str == "LENGTH") {
              usingFunctionName(str);
            } else if (str == "LOCATE")
            {
              localObject = this.oracle_sql;
              this.oracle_sql = new StringBuffer();
              handleODBC(ParseMode.LOCATE_1);
              StringBuffer localStringBuffer = this.oracle_sql;
              this.oracle_sql = ((StringBuffer)localObject);
              this.oracle_sql.append("INSTR(");
              handleODBC(ParseMode.LOCATE_2);
              this.oracle_sql.append(',');
              this.oracle_sql.append(localStringBuffer);
              this.oracle_sql.append(')');
              handleODBC(ParseMode.SCALAR);
            }
            else if (str == "LTRIM") {
              usingFunctionName(str);
            }
            else if (str == "OCTET_LENGTH") {
              usingFunctionName("LENGTHB"); } else {
              if (str == "POSITION")
              {
                localObject = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 34, str);
                ((SQLException)localObject).fillInStackTrace();
                throw ((Throwable)localObject);
              }

              if (str == "REPEAT")
              {
                localObject = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 34, str);
                ((SQLException)localObject).fillInStackTrace();
                throw ((Throwable)localObject);
              }
              if (str == "REPLACE") {
                usingFunctionName(str); } else {
                if (str == "RIGHT")
                {
                  localObject = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 34, str);
                  ((SQLException)localObject).fillInStackTrace();
                  throw ((Throwable)localObject);
                }
                if (str == "RTRIM") {
                  usingFunctionName(str);
                } else if (str == "SOUNDEX") {
                  usingFunctionName(str); } else {
                  if (str == "SPACE")
                  {
                    localObject = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 34, str);
                    ((SQLException)localObject).fillInStackTrace();
                    throw ((Throwable)localObject);
                  }
                  if (str == "SUBSTRING") {
                    usingFunctionName("SUBSTR");
                  } else if (str == "UCASE") {
                    usingFunctionName("UPPER");
                  }
                  else if (str == "CURRENT_DATE") {
                    replacingFunctionPrefix("(CURRENT_DATE"); } else {
                    if (str == "CURRENT_TIME")
                    {
                      localObject = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 34, str);
                      ((SQLException)localObject).fillInStackTrace();
                      throw ((Throwable)localObject);
                    }
                    if (str == "CURRENT_TIMESTAMP") {
                      replacingFunctionPrefix("(CURRENT_TIMESTAMP");
                    }
                    else if (str == "CURDATE") {
                      replacingFunctionPrefix("(CURRENT_DATE");
                    } else if (str == "CURTIME") {
                      replacingFunctionPrefix("(CURRENT_TIMESTAMP"); } else {
                      if (str == "DAYNAME")
                      {
                        localObject = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 34, str);
                        ((SQLException)localObject).fillInStackTrace();
                        throw ((Throwable)localObject);
                      }
                      if (str == "DAYOFMONTH") {
                        replacingFunctionPrefix("EXTRACT ( DAY FROM "); } else {
                        if (str == "DAYOFWEEK")
                        {
                          localObject = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 34, str);
                          ((SQLException)localObject).fillInStackTrace();
                          throw ((Throwable)localObject);
                        }
                        if (str == "DAYOFYEAR")
                        {
                          localObject = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 34, str);
                          ((SQLException)localObject).fillInStackTrace();
                          throw ((Throwable)localObject);
                        }

                        if (str == "EXTRACT") {
                          usingFunctionName("EXTRACT");
                        }
                        else if (str == "HOUR") {
                          replacingFunctionPrefix("EXTRACT ( HOUR FROM ");
                        } else if (str == "MINUTE") {
                          replacingFunctionPrefix("EXTRACT ( MINUTE FROM ");
                        } else if (str == "MONTH") {
                          replacingFunctionPrefix("EXTRACT ( MONTH FROM "); } else {
                          if (str == "MONTHNAME")
                          {
                            localObject = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 34, str);
                            ((SQLException)localObject).fillInStackTrace();
                            throw ((Throwable)localObject);
                          }
                          if (str == "NOW") {
                            replacingFunctionPrefix("(CURRENT_TIMESTAMP"); } else {
                            if (str == "QUARTER")
                            {
                              localObject = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 34, str);
                              ((SQLException)localObject).fillInStackTrace();
                              throw ((Throwable)localObject);
                            }
                            if (str == "SECOND") {
                              replacingFunctionPrefix("EXTRACT ( SECOND FROM "); } else {
                              if (str == "TIMESTAMPADD")
                              {
                                localObject = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 34, str);
                                ((SQLException)localObject).fillInStackTrace();
                                throw ((Throwable)localObject);
                              }
                              if (str == "TIMESTAMPDIFF")
                              {
                                localObject = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 34, str);
                                ((SQLException)localObject).fillInStackTrace();
                                throw ((Throwable)localObject);
                              }
                              if (str == "WEEK")
                              {
                                localObject = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 34, str);
                                ((SQLException)localObject).fillInStackTrace();
                                throw ((Throwable)localObject);
                              }
                              if (str == "YEAR") {
                                replacingFunctionPrefix("EXTRACT ( YEAR FROM ");
                              }
                              else {
                                if (str == "DATABASE")
                                {
                                  localObject = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 34, str);
                                  ((SQLException)localObject).fillInStackTrace();
                                  throw ((Throwable)localObject);
                                }
                                if (str == "IFNULL") {
                                  usingFunctionName("NVL");
                                } else if (str == "USER") {
                                  replacingFunctionPrefix("(USER");
                                }
                                else {
                                  if (str == "CONVERT")
                                  {
                                    localObject = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 34, str);
                                    ((SQLException)localObject).fillInStackTrace();
                                    throw ((Throwable)localObject);
                                  }

                                  localObject = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 34, str);
                                  ((SQLException)localObject).fillInStackTrace();
                                  throw ((Throwable)localObject); }  }  }  }  }  }  }  }  }  } 
            }
          }
        }
      }
    }
  }

  void usingFunctionName(String paramString) throws SQLException { this.oracle_sql.append(paramString);
    skipSpace();
    handleODBC(ParseMode.SCALAR);
  }

  void replacingFunctionPrefix(String paramString)
    throws SQLException
  {
    skipSpace();

    if ((this.i < this.length) && ((this.c = this.odbc_sql.charAt(this.i)) == '(')) {
      this.i += 1;
    }
    else {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 33);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.oracle_sql.append(paramString);
    skipSpace();
    handleODBC(ParseMode.SCALAR);
  }

  void handleOuterJoin()
    throws SQLException
  {
    this.oracle_sql.append(" ( ");
    skipSpace();
    handleODBC(ParseMode.SCALAR);
    this.oracle_sql.append(" ) ");
  }

  String nextArgument()
  {
    String str = new StringBuilder().append(":").append(this.current_argument).toString();

    this.current_argument += 1;

    return str;
  }

  void appendChar(StringBuffer paramStringBuffer, char paramChar)
  {
    if (paramChar == '?')
      paramStringBuffer.append(nextArgument());
    else
      paramStringBuffer.append(paramChar);
  }

  void skipSpace()
  {
    while ((this.i < this.length) && ((this.c = this.odbc_sql.charAt(this.i)) == ' '))
      this.i += 1;
  }

  String generateParameterName()
  {
    if ((this.parameterCount == 0) || (this.parameterList == null))
    {
      return new StringBuilder().append("rowid").append(this.paramSuffix++).toString();
    }

    String str = new StringBuilder().append("rowid").append(this.paramSuffix++).toString();
    for (int j = 0; ; j++) { if (j >= this.parameterList.length)
        break label109;
      if (str.equals(this.parameterList[j]))
        break; }
    label109: return str;
  }

  static boolean isValidPlsqlWarning(String paramString)
    throws SQLException
  {
    return paramString.matches("('\\s*([a-zA-Z0-9:,\\(\\)\\s])*')\\s*(,\\s*'([a-zA-Z0-9:,\\(\\)\\s])*')*");
  }

  public static boolean isValidObjectName(String paramString)
    throws SQLException
  {
    if (paramString.matches("([a-zA-Z]{1}\\w*(\\$|\\#)*\\w*)|(\".*)"))
    {
      return true;
    }

    char[] arrayOfChar = paramString.toCharArray();
    int j = arrayOfChar.length;

    if (!Character.isLetter(arrayOfChar[0]))
    {
      return false;
    }

    for (int k = 1; k < j; k++)
    {
      if ((!Character.isLetterOrDigit(arrayOfChar[k])) && (arrayOfChar[k] != '$') && (arrayOfChar[k] != '#') && (arrayOfChar[k] != '_'))
      {
        return false;
      }
    }

    return true;
  }

  public static void main(String[] paramArrayOfString)
  {
    if (paramArrayOfString.length < 2) {
      System.err.println("ERROR: incorrect usage. OracleSql (-transition <file> | <process_escapes> <convert_nchars> { <sql> } )");
      return;
    }
    if (paramArrayOfString[0].equals("-dump")) {
      dumpTransitionMatrix(paramArrayOfString[1]);
      return;
    }
    boolean bool1 = paramArrayOfString[0].equals("true");
    boolean bool2 = paramArrayOfString[1].equals("true");
    String[] arrayOfString1;
    if (paramArrayOfString.length > 2) {
      arrayOfString1 = new String[paramArrayOfString.length - 2];
      System.arraycopy(paramArrayOfString, 2, arrayOfString1, 0, arrayOfString1.length);
    }
    else {
      arrayOfString1 = new String[] { "select ? from dual", "insert into dual values (?)", "delete from dual", "update dual set dummy = ?", "merge tab into dual", " select ? from dual where ? = ?", "select ?from dual where?=?for update", "select '?', n'?', q'???', q'{?}', q'{cat's}' from dual", "select'?',n'?',q'???',q'{?}',q'{cat's}'from dual", "select--line\n? from dual", "select --line\n? from dual", "--line\nselect ? from dual", " --line\nselect ? from dual", "--line\n select ? from dual", "begin proc4in4out (:x1, :x2, :x3, :x4); end;", "{CALL tkpjpn01(:pin, :pinout, :pout)}", "select :NumberBindVar as the_number from dual", "select {fn locate(bob(carol(),ted(alice,sue)), 'xfy')} from dual", "CREATE USER vijay6 IDENTIFIED BY \"vjay?\"" };
    }

    for (String str1 : arrayOfString1)
      try {
        System.out.println("\n\n-----------------------");
        System.out.println(str1);
        System.out.println();
        OracleSql localOracleSql = new OracleSql(null);

        localOracleSql.initialize(str1);
        String str2 = localOracleSql.getSql(bool1, bool2);

        System.out.println(new StringBuilder().append((String)sqlKindStrings.get(Byte.valueOf(localOracleSql.sqlKind))).append(", ").append(localOracleSql.parameterCount).toString());

        String[] arrayOfString3 = localOracleSql.getParameterList();
        int m;
        if (arrayOfString3 == EMPTY_LIST)
          System.out.println("parameterList is empty");
        else {
          for (m = 0; m < arrayOfString3.length; m++)
            System.out.println(new StringBuilder().append("parameterList[").append(m).append("] = ").append(arrayOfString3[m]).toString());
        }
        if (localOracleSql.lastNcharLiteralLocation == 2) { System.out.println("No NCHAR literals");
        } else {
          System.out.println("NCHAR Literals");
          for (m = 1; m < localOracleSql.lastNcharLiteralLocation - 1; )
            System.out.println(str2.substring(localOracleSql.ncharLiteralLocation[(m++)], localOracleSql.ncharLiteralLocation[(m++)]));
        }
        System.out.println("Keywords");
        if (localOracleSql.selectEndIndex == -1) System.out.println("no select"); else
          System.out.println(new StringBuilder().append("'").append(str2.substring(localOracleSql.selectEndIndex - 6, localOracleSql.selectEndIndex)).append("'").toString());
        if (localOracleSql.orderByStartIndex == -1) System.out.println("no order by"); else
          System.out.println(new StringBuilder().append("'").append(str2.substring(localOracleSql.orderByStartIndex, localOracleSql.orderByEndIndex)).append("'").toString());
        if (localOracleSql.whereStartIndex == -1) System.out.println("no where"); else
          System.out.println(new StringBuilder().append("'").append(str2.substring(localOracleSql.whereStartIndex, localOracleSql.whereEndIndex)).append("'").toString());
        if (localOracleSql.forUpdateStartIndex == -1) System.out.println("no for update"); else {
          System.out.println(new StringBuilder().append("'").append(str2.substring(localOracleSql.forUpdateStartIndex, localOracleSql.forUpdateEndIndex)).append("'").toString());
        }
        System.out.println(new StringBuilder().append("\"").append(str2).append("\"").toString());
        System.out.println(new StringBuilder().append("\"").append(localOracleSql.getRevisedSql()).append("\"").toString());
      }
      catch (Exception localException) {
        System.out.println(localException);
      }
  }

  private static final void dumpTransitionMatrix(String paramString)
  {
    try {
      PrintWriter localPrintWriter = new PrintWriter(paramString);
      localPrintWriter.print(",");
      for (int j = 0; j < 128; j++) localPrintWriter.print(new StringBuilder().append("'").append(j < 32 ? new StringBuilder().append("0x").append(Integer.toHexString(j)).toString() : Character.toString((char)j)).append(j < 127 ? "'," : "'").toString());
      localPrintWriter.println();
      int[][] arrayOfInt = OracleSqlReadOnly.TRANSITION;
      String[] arrayOfString = OracleSqlReadOnly.PARSER_STATE_NAME;
      for (int k = 0; k < TRANSITION.length; k++) {
        localPrintWriter.print(new StringBuilder().append(arrayOfString[k]).append(",").toString());
        for (int m = 0; m < arrayOfInt[k].length; m++) localPrintWriter.print(new StringBuilder().append(arrayOfString[arrayOfInt[k][m]]).append(m < 127 ? "," : "").toString());
        localPrintWriter.println();
      }
      localPrintWriter.close();
    }
    catch (Throwable localThrowable) {
      System.err.println(localThrowable);
    }
  }

  protected OracleConnection getConnectionDuringExceptionHandling()
  {
    return null;
  }

  int getReturnParameterCount()
    throws SQLException
  {
    if (this.sqlKind == 0) {
      computeBasicInfo(this.parameterSql);
    }
    if ((this.sqlKind & 0x1E) == 0) {
      return -1;
    }
    int j = -1;
    String str1 = getOriginalSql().toUpperCase();
    int k = getSubstrPos(str1, "RETURNING");

    if (k >= 0)
    {
      String str2 = str1.substring(k);
      int m = getSubstrPos(str2, "INTO");

      if (m >= 0)
      {
        char[] arrayOfChar = new char[str1.length() - k];
        str1.getChars(k, str1.length(), arrayOfChar, 0);

        j = 0;
        for (int n = 0; n < arrayOfChar.length; n++)
        {
          switch (arrayOfChar[n])
          {
          case ':':
          case '?':
            j++;
          }
        }
      }

    }

    return j;
  }

  private int getSubstrPos(String paramString1, String paramString2)
    throws SQLException
  {
    int j = -1;
    int k = paramString1.indexOf(paramString2);

    if ((k >= 1) && (Character.isWhitespace(paramString1.charAt(k - 1))))
    {
      int m = k + paramString2.length();

      if ((m < paramString1.length()) && (Character.isWhitespace(paramString1.charAt(m))))
      {
        j = k;
      }
    }
    return j;
  }

  static
  {
    sqlKindStrings.put(Byte.valueOf((byte)0), "IS_UNINITIALIZED");
    sqlKindStrings.put(Byte.valueOf((byte)1), "IS_SELECT");
    sqlKindStrings.put(Byte.valueOf((byte)2), "IS_DELETE");
    sqlKindStrings.put(Byte.valueOf((byte)4), "IS_INSERT");
    sqlKindStrings.put(Byte.valueOf((byte)8), "IS_MERGE");
    sqlKindStrings.put(Byte.valueOf((byte)16), "IS_UPDATE");
    sqlKindStrings.put(Byte.valueOf((byte)32), "IS_PLSQL_BLOCK");
    sqlKindStrings.put(Byte.valueOf((byte)64), "IS_CALL_BLOCK");
    sqlKindStrings.put(Byte.valueOf((byte)-128), "IS_OTHER");
  }

  static enum ParseMode
  {
    NORMAL, SCALAR, LOCATE_1, LOCATE_2;
  }
}