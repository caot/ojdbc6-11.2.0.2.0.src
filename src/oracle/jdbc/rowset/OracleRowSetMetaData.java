package oracle.jdbc.rowset;

import java.io.Serializable;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.sql.RowSetMetaData;
import oracle.jdbc.driver.DatabaseError;
import oracle.jdbc.internal.OracleConnection;

public class OracleRowSetMetaData
  implements RowSetMetaData, Serializable
{
  private int columnCount;
  private int[] nullable;
  private int[] columnDisplaySize;
  private int[] precision;
  private int[] scale;
  private int[] columnType;
  private boolean[] searchable;
  private boolean[] caseSensitive;
  private boolean[] readOnly;
  private boolean[] writable;
  private boolean[] definatelyWritable;
  private boolean[] currency;
  private boolean[] autoIncrement;
  private boolean[] signed;
  private String[] columnLabel;
  private String[] schemaName;
  private String[] columnName;
  private String[] tableName;
  private String[] columnTypeName;
  private String[] catalogName;
  private String[] columnClassName;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  OracleRowSetMetaData(int paramInt)
    throws SQLException
  {
    this.columnCount = paramInt;
    this.searchable = new boolean[this.columnCount];
    this.caseSensitive = new boolean[this.columnCount];
    this.readOnly = new boolean[this.columnCount];
    this.nullable = new int[this.columnCount];
    this.signed = new boolean[this.columnCount];
    this.columnDisplaySize = new int[this.columnCount];
    this.columnType = new int[this.columnCount];
    this.columnLabel = new String[this.columnCount];
    this.columnName = new String[this.columnCount];
    this.schemaName = new String[this.columnCount];
    this.precision = new int[this.columnCount];
    this.scale = new int[this.columnCount];
    this.tableName = new String[this.columnCount];
    this.columnTypeName = new String[this.columnCount];
    this.writable = new boolean[this.columnCount];
    this.definatelyWritable = new boolean[this.columnCount];
    this.currency = new boolean[this.columnCount];
    this.autoIncrement = new boolean[this.columnCount];
    this.catalogName = new String[this.columnCount];
    this.columnClassName = new String[this.columnCount];

    for (int i = 0; i < this.columnCount; i++)
    {
      this.searchable[i] = false;
      this.caseSensitive[i] = false;
      this.readOnly[i] = false;
      this.nullable[i] = 1;
      this.signed[i] = false;
      this.columnDisplaySize[i] = 0;
      this.columnType[i] = 0;
      this.columnLabel[i] = "";
      this.columnName[i] = "";
      this.schemaName[i] = "";
      this.precision[i] = 0;
      this.scale[i] = 0;
      this.tableName[i] = "";
      this.columnTypeName[i] = "";
      this.writable[i] = false;
      this.definatelyWritable[i] = false;
      this.currency[i] = false;
      this.autoIncrement[i] = true;
      this.catalogName[i] = "";
      this.columnClassName[i] = "";
    }
  }

  OracleRowSetMetaData(ResultSetMetaData paramResultSetMetaData)
    throws SQLException
  {
    this.columnCount = paramResultSetMetaData.getColumnCount();
    this.searchable = new boolean[this.columnCount];
    this.caseSensitive = new boolean[this.columnCount];
    this.readOnly = new boolean[this.columnCount];
    this.nullable = new int[this.columnCount];
    this.signed = new boolean[this.columnCount];
    this.columnDisplaySize = new int[this.columnCount];
    this.columnType = new int[this.columnCount];
    this.columnLabel = new String[this.columnCount];
    this.columnName = new String[this.columnCount];
    this.schemaName = new String[this.columnCount];
    this.precision = new int[this.columnCount];
    this.scale = new int[this.columnCount];
    this.tableName = new String[this.columnCount];
    this.columnTypeName = new String[this.columnCount];
    this.writable = new boolean[this.columnCount];
    this.definatelyWritable = new boolean[this.columnCount];
    this.currency = new boolean[this.columnCount];
    this.autoIncrement = new boolean[this.columnCount];
    this.catalogName = new String[this.columnCount];
    this.columnClassName = new String[this.columnCount];

    for (int i = 0; i < this.columnCount; i++)
    {
      this.searchable[i] = paramResultSetMetaData.isSearchable(i + 1);
      this.caseSensitive[i] = paramResultSetMetaData.isCaseSensitive(i + 1);
      this.readOnly[i] = paramResultSetMetaData.isReadOnly(i + 1);
      this.nullable[i] = paramResultSetMetaData.isNullable(i + 1);
      this.signed[i] = paramResultSetMetaData.isSigned(i + 1);
      this.columnDisplaySize[i] = paramResultSetMetaData.getColumnDisplaySize(i + 1);
      this.columnType[i] = paramResultSetMetaData.getColumnType(i + 1);
      this.columnLabel[i] = paramResultSetMetaData.getColumnLabel(i + 1);
      this.columnName[i] = paramResultSetMetaData.getColumnName(i + 1);
      this.schemaName[i] = paramResultSetMetaData.getSchemaName(i + 1);

      if ((this.columnType[i] == 2) || (this.columnType[i] == 2) || (this.columnType[i] == -5) || (this.columnType[i] == 3) || (this.columnType[i] == 8) || (this.columnType[i] == 6) || (this.columnType[i] == 4))
      {
        this.precision[i] = paramResultSetMetaData.getPrecision(i + 1);
        this.scale[i] = paramResultSetMetaData.getScale(i + 1);
      }
      else
      {
        this.precision[i] = 0;
        this.scale[i] = 0;
      }

      this.tableName[i] = paramResultSetMetaData.getTableName(i + 1);
      this.columnTypeName[i] = paramResultSetMetaData.getColumnTypeName(i + 1);
      this.writable[i] = paramResultSetMetaData.isWritable(i + 1);
      this.definatelyWritable[i] = paramResultSetMetaData.isDefinitelyWritable(i + 1);
      this.currency[i] = paramResultSetMetaData.isCurrency(i + 1);
      this.autoIncrement[i] = paramResultSetMetaData.isAutoIncrement(i + 1);
      this.catalogName[i] = paramResultSetMetaData.getCatalogName(i + 1);
      this.columnClassName[i] = paramResultSetMetaData.getColumnClassName(i + 1);
    }
  }

  private void validateColumnIndex(int paramInt)
    throws SQLException
  {
    if ((paramInt < 1) || (paramInt > this.columnCount))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3, "" + paramInt);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  public int getColumnCount()
    throws SQLException
  {
    return this.columnCount;
  }

  public boolean isAutoIncrement(int paramInt)
    throws SQLException
  {
    validateColumnIndex(paramInt);
    return this.autoIncrement[(paramInt - 1)];
  }

  public boolean isCaseSensitive(int paramInt)
    throws SQLException
  {
    validateColumnIndex(paramInt);
    return this.caseSensitive[(paramInt - 1)];
  }

  public boolean isSearchable(int paramInt)
    throws SQLException
  {
    validateColumnIndex(paramInt);
    return this.searchable[(paramInt - 1)];
  }

  public boolean isCurrency(int paramInt)
    throws SQLException
  {
    validateColumnIndex(paramInt);
    return this.currency[(paramInt - 1)];
  }

  public int isNullable(int paramInt)
    throws SQLException
  {
    validateColumnIndex(paramInt);
    return this.nullable[(paramInt - 1)];
  }

  public boolean isSigned(int paramInt)
    throws SQLException
  {
    validateColumnIndex(paramInt);
    return this.signed[(paramInt - 1)];
  }

  public int getColumnDisplaySize(int paramInt)
    throws SQLException
  {
    validateColumnIndex(paramInt);
    return this.columnDisplaySize[(paramInt - 1)];
  }

  public String getColumnLabel(int paramInt)
    throws SQLException
  {
    validateColumnIndex(paramInt);
    return this.columnLabel[(paramInt - 1)];
  }

  public String getColumnName(int paramInt)
    throws SQLException
  {
    validateColumnIndex(paramInt);
    return this.columnName[(paramInt - 1)];
  }

  public String getSchemaName(int paramInt)
    throws SQLException
  {
    validateColumnIndex(paramInt);
    return this.schemaName[(paramInt - 1)];
  }

  public int getPrecision(int paramInt)
    throws SQLException
  {
    validateColumnIndex(paramInt);
    return this.precision[(paramInt - 1)];
  }

  public int getScale(int paramInt)
    throws SQLException
  {
    validateColumnIndex(paramInt);
    return this.scale[(paramInt - 1)];
  }

  public String getTableName(int paramInt)
    throws SQLException
  {
    validateColumnIndex(paramInt);
    return this.tableName[(paramInt - 1)];
  }

  public String getCatalogName(int paramInt)
    throws SQLException
  {
    validateColumnIndex(paramInt);
    return this.catalogName[(paramInt - 1)];
  }

  public int getColumnType(int paramInt)
    throws SQLException
  {
    validateColumnIndex(paramInt);
    return this.columnType[(paramInt - 1)];
  }

  public String getColumnTypeName(int paramInt)
    throws SQLException
  {
    validateColumnIndex(paramInt);
    return this.columnTypeName[(paramInt - 1)];
  }

  public boolean isReadOnly(int paramInt)
    throws SQLException
  {
    validateColumnIndex(paramInt);
    return this.readOnly[(paramInt - 1)];
  }

  public boolean isWritable(int paramInt)
    throws SQLException
  {
    validateColumnIndex(paramInt);
    return this.writable[(paramInt - 1)];
  }

  public boolean isDefinitelyWritable(int paramInt)
    throws SQLException
  {
    validateColumnIndex(paramInt);
    return this.definatelyWritable[(paramInt - 1)];
  }

  public String getColumnClassName(int paramInt)
    throws SQLException
  {
    validateColumnIndex(paramInt);
    return this.columnClassName[(paramInt - 1)];
  }

  public void setAutoIncrement(int paramInt, boolean paramBoolean)
    throws SQLException
  {
    validateColumnIndex(paramInt);
    this.autoIncrement[(paramInt - 1)] = paramBoolean;
  }

  public void setCaseSensitive(int paramInt, boolean paramBoolean)
    throws SQLException
  {
    validateColumnIndex(paramInt);
    this.caseSensitive[(paramInt - 1)] = paramBoolean;
  }

  public void setCatalogName(int paramInt, String paramString)
    throws SQLException
  {
    validateColumnIndex(paramInt);
    this.catalogName[(paramInt - 1)] = paramString;
  }

  public void setColumnCount(int paramInt)
    throws SQLException
  {
    this.columnCount = paramInt;
  }

  public void setColumnDisplaySize(int paramInt1, int paramInt2)
    throws SQLException
  {
    validateColumnIndex(paramInt1);
    this.columnDisplaySize[(paramInt1 - 1)] = paramInt2;
  }

  public void setColumnLabel(int paramInt, String paramString)
    throws SQLException
  {
    validateColumnIndex(paramInt);
    this.columnLabel[(paramInt - 1)] = paramString;
  }

  public void setColumnName(int paramInt, String paramString)
    throws SQLException
  {
    validateColumnIndex(paramInt);
    this.columnName[(paramInt - 1)] = paramString;
  }

  public void setColumnType(int paramInt1, int paramInt2)
    throws SQLException
  {
    validateColumnIndex(paramInt1);
    this.columnType[(paramInt1 - 1)] = paramInt2;
  }

  public void setColumnTypeName(int paramInt, String paramString)
    throws SQLException
  {
    validateColumnIndex(paramInt);
    this.columnTypeName[(paramInt - 1)] = paramString;
  }

  public void setCurrency(int paramInt, boolean paramBoolean)
    throws SQLException
  {
    validateColumnIndex(paramInt);
    this.currency[(paramInt - 1)] = paramBoolean;
  }

  public void setNullable(int paramInt1, int paramInt2)
    throws SQLException
  {
    validateColumnIndex(paramInt1);
    this.nullable[(paramInt1 - 1)] = paramInt2;
  }

  public void setPrecision(int paramInt1, int paramInt2)
    throws SQLException
  {
    validateColumnIndex(paramInt1);
    this.precision[(paramInt1 - 1)] = paramInt2;
  }

  public void setScale(int paramInt1, int paramInt2)
    throws SQLException
  {
    validateColumnIndex(paramInt1);
    this.scale[(paramInt1 - 1)] = paramInt2;
  }

  public void setSchemaName(int paramInt, String paramString)
    throws SQLException
  {
    validateColumnIndex(paramInt);
    this.schemaName[(paramInt - 1)] = paramString;
  }

  public void setSearchable(int paramInt, boolean paramBoolean)
    throws SQLException
  {
    validateColumnIndex(paramInt);
    this.searchable[(paramInt - 1)] = paramBoolean;
  }

  public void setSigned(int paramInt, boolean paramBoolean)
    throws SQLException
  {
    validateColumnIndex(paramInt);
    this.signed[(paramInt - 1)] = paramBoolean;
  }

  public void setTableName(int paramInt, String paramString)
    throws SQLException
  {
    validateColumnIndex(paramInt);
    this.tableName[(paramInt - 1)] = paramString;
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

    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 177);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  protected OracleConnection getConnectionDuringExceptionHandling()
  {
    return null;
  }
}