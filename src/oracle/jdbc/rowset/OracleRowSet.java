package oracle.jdbc.rowset;

import java.io.Serializable;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import javax.sql.RowSet;
import javax.sql.RowSetEvent;
import javax.sql.RowSetListener;
import javax.sql.rowset.Joinable;
import oracle.jdbc.driver.DatabaseError;
import oracle.jdbc.internal.OracleConnection;

abstract class OracleRowSet
  implements Serializable, Cloneable, Joinable
{
  protected String dataSource;
  protected String dataSourceName;
  protected String url;
  protected String username;
  protected String password;
  protected Map typeMap;
  protected int maxFieldSize;
  protected int maxRows;
  protected int queryTimeout;
  protected int fetchSize;
  protected int transactionIsolation;
  protected boolean escapeProcessing;
  protected String command;
  protected int concurrency;
  protected boolean readOnly;
  protected int fetchDirection;
  protected int rowsetType;
  protected boolean showDeleted;
  protected Vector listener;
  protected RowSetEvent rowsetEvent;
  protected Vector matchColumnIndexes;
  protected Vector matchColumnNames;
  protected boolean isClosed;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  protected OracleRowSet()
    throws SQLException
  {
    initializeProperties();

    this.matchColumnIndexes = new Vector(10);
    this.matchColumnNames = new Vector(10);

    this.listener = new Vector();
    this.rowsetEvent = new RowSetEvent((RowSet)this);

    this.isClosed = false;
  }

  protected void initializeProperties()
  {
    this.command = null;
    this.concurrency = 1007;
    this.dataSource = null;
    this.dataSourceName = null;

    this.escapeProcessing = true;
    this.fetchDirection = 1002;
    this.fetchSize = 0;
    this.maxFieldSize = 0;
    this.maxRows = 0;
    this.queryTimeout = 0;
    this.readOnly = false;
    this.showDeleted = false;
    this.transactionIsolation = 2;
    this.rowsetType = 1005;
    this.typeMap = new HashMap();
    this.username = null;
    this.password = null;
    this.url = null;
  }

  public String getCommand()
  {
    return this.command;
  }

  public int getConcurrency()
    throws SQLException
  {
    return this.concurrency;
  }

  /** @deprecated */
  public String getDataSource()
  {
    return this.dataSource;
  }

  public String getDataSourceName()
  {
    return this.dataSourceName;
  }

  public boolean getEscapeProcessing()
    throws SQLException
  {
    return this.escapeProcessing;
  }

  public int getFetchDirection()
    throws SQLException
  {
    return this.fetchDirection;
  }

  public int getFetchSize()
    throws SQLException
  {
    return this.fetchSize;
  }

  public int getMaxFieldSize()
    throws SQLException
  {
    return this.maxFieldSize;
  }

  public int getMaxRows()
    throws SQLException
  {
    return this.maxRows;
  }

  public String getPassword()
  {
    return this.password;
  }

  public int getQueryTimeout()
    throws SQLException
  {
    return this.queryTimeout;
  }

  public boolean getReadOnly()
  {
    return isReadOnly();
  }

  public boolean isReadOnly()
  {
    return this.readOnly;
  }

  public boolean getShowDeleted()
  {
    return this.showDeleted;
  }

  public int getTransactionIsolation()
  {
    return this.transactionIsolation;
  }

  public int getType()
    throws SQLException
  {
    return this.rowsetType;
  }

  public Map getTypeMap()
    throws SQLException
  {
    return this.typeMap;
  }

  public String getUrl()
  {
    return this.url;
  }

  public String getUsername()
  {
    return this.username;
  }

  public void setCommand(String paramString)
    throws SQLException
  {
    this.command = paramString;
  }

  public void setConcurrency(int paramInt)
    throws SQLException
  {
    if ((paramInt == 1007) || (paramInt == 1008)) {
      this.concurrency = paramInt;
    }
    else {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  /** @deprecated */
  public void setDataSource(String paramString)
  {
    this.dataSource = paramString;
  }

  public void setDataSourceName(String paramString)
    throws SQLException
  {
    this.dataSourceName = paramString;
  }

  public void setEscapeProcessing(boolean paramBoolean)
    throws SQLException
  {
    this.escapeProcessing = paramBoolean;
  }

  public void setFetchDirection(int paramInt)
    throws SQLException
  {
    this.fetchDirection = paramInt;
  }

  public void setFetchSize(int paramInt)
    throws SQLException
  {
    this.fetchSize = paramInt;
  }

  public void setMaxFieldSize(int paramInt)
    throws SQLException
  {
    this.maxFieldSize = paramInt;
  }

  public void setMaxRows(int paramInt)
    throws SQLException
  {
    this.maxRows = paramInt;
  }

  public void setPassword(String paramString)
    throws SQLException
  {
    this.password = paramString;
  }

  public void setQueryTimeout(int paramInt)
    throws SQLException
  {
    this.queryTimeout = paramInt;
  }

  public void setReadOnly(boolean paramBoolean)
    throws SQLException
  {
    this.readOnly = paramBoolean;
  }

  public void setShowDeleted(boolean paramBoolean)
    throws SQLException
  {
    this.showDeleted = paramBoolean;
  }

  public void setTransactionIsolation(int paramInt)
    throws SQLException
  {
    this.transactionIsolation = paramInt;
  }

  public void setType(int paramInt)
    throws SQLException
  {
    if ((paramInt == 1003) || (paramInt == 1004) || (paramInt == 1005))
    {
      this.rowsetType = paramInt;
    }
    else {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  public void setTypeMap(Map paramMap)
    throws SQLException
  {
    this.typeMap = paramMap;
  }

  public void setUrl(String paramString)
  {
    this.url = paramString;
  }

  public void setUsername(String paramString)
    throws SQLException
  {
    this.username = paramString;
  }

  public void addRowSetListener(RowSetListener paramRowSetListener)
  {
    for (int i = 0; i < this.listener.size(); i++)
      if (this.listener.elementAt(i).equals(paramRowSetListener))
        return;
    this.listener.add(paramRowSetListener);
  }

  public void removeRowSetListener(RowSetListener paramRowSetListener)
  {
    for (int i = 0; i < this.listener.size(); i++)
      if (this.listener.elementAt(i).equals(paramRowSetListener))
        this.listener.remove(i);
  }

  protected synchronized void notifyCursorMoved()
  {
    int i = this.listener.size();
    if (i > 0)
      for (int j = 0; j < i; j++)
        ((RowSetListener)this.listener.elementAt(j)).cursorMoved(this.rowsetEvent);
  }

  protected void notifyRowChanged()
  {
    int i = this.listener.size();
    if (i > 0)
      for (int j = 0; j < i; j++)
      {
        ((RowSetListener)this.listener.elementAt(j)).rowChanged(this.rowsetEvent);
      }
  }

  protected void notifyRowSetChanged()
  {
    int i = this.listener.size();
    if (i > 0)
      for (int j = 0; j < i; j++)
      {
        ((RowSetListener)this.listener.elementAt(j)).rowSetChanged(this.rowsetEvent);
      }
  }

  public int[] getMatchColumnIndexes()
    throws SQLException
  {
    if ((this.matchColumnIndexes.size() == 0) && (this.matchColumnNames.size() == 0))
    {
      SQLException localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 334);
      localSQLException1.fillInStackTrace();
      throw localSQLException1;
    }
    int i;
    int[] arrayOfInt;
    int k;
    if (this.matchColumnNames.size() > 0)
    {
      String[] arrayOfString = getMatchColumnNames();
      i = arrayOfString.length;
      arrayOfInt = new int[i];

      for (k = 0; k < i; k++)
      {
        arrayOfInt[k] = findColumn(arrayOfString[k]);
      }
    }
    else
    {
      i = this.matchColumnIndexes.size();
      arrayOfInt = new int[i];
      int j = -1;

      for (k = 0; k < i; k++)
      {
        try
        {
          j = ((Integer)this.matchColumnIndexes.get(k)).intValue();
        }
        catch (Exception localException)
        {
          SQLException localSQLException3 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 336);
          localSQLException3.fillInStackTrace();
          throw localSQLException3;
        }

        if (j <= 0)
        {
          SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 336);
          localSQLException2.fillInStackTrace();
          throw localSQLException2;
        }

        arrayOfInt[k] = j;
      }

    }

    return arrayOfInt;
  }

  public String[] getMatchColumnNames()
    throws SQLException
  {
    checkIfMatchColumnNamesSet();

    int i = this.matchColumnNames.size();
    String[] arrayOfString = new String[i];
    String str = null;

    for (int j = 0; j < i; j++)
    {
      try
      {
        str = (String)this.matchColumnNames.get(j);
      }
      catch (Exception localException)
      {
        SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 337);
        localSQLException2.fillInStackTrace();
        throw localSQLException2;
      }

      if ((str == null) || (str.equals("")))
      {
        SQLException localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 337);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      arrayOfString[j] = str;
    }

    return arrayOfString;
  }

  public void setMatchColumn(int paramInt)
    throws SQLException
  {
    if (paramInt <= 0)
    {
      SQLException localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 336);
      localSQLException1.fillInStackTrace();
      throw localSQLException1;
    }

    try
    {
      this.matchColumnIndexes.clear();
      this.matchColumnNames.clear();

      this.matchColumnIndexes.add(0, Integer.valueOf(paramInt));
    }
    catch (Exception localException)
    {
      SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 338);
      localSQLException2.fillInStackTrace();
      throw localSQLException2;
    }
  }

  public void setMatchColumn(int[] paramArrayOfInt)
    throws SQLException
  {
    this.matchColumnIndexes.clear();
    this.matchColumnNames.clear();

    if (paramArrayOfInt == null)
    {
      SQLException localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68);
      localSQLException1.fillInStackTrace();
      throw localSQLException1;
    }

    for (int i = 0; i < paramArrayOfInt.length; i++)
    {
      if (paramArrayOfInt[i] <= 0)
      {
        SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 336);
        localSQLException2.fillInStackTrace();
        throw localSQLException2;
      }

      try
      {
        this.matchColumnIndexes.add(i, Integer.valueOf(paramArrayOfInt[i]));
      }
      catch (Exception localException)
      {
        SQLException localSQLException3 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 338);
        localSQLException3.fillInStackTrace();
        throw localSQLException3;
      }
    }
  }

  public void setMatchColumn(String paramString)
    throws SQLException
  {
    if ((paramString == null) || (paramString.equals("")))
    {
      SQLException localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68);
      localSQLException1.fillInStackTrace();
      throw localSQLException1;
    }

    try
    {
      this.matchColumnIndexes.clear();
      this.matchColumnNames.clear();

      this.matchColumnNames.add(0, paramString.trim());
    }
    catch (Exception localException)
    {
      SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 339);
      localSQLException2.fillInStackTrace();
      throw localSQLException2;
    }
  }

  public void setMatchColumn(String[] paramArrayOfString)
    throws SQLException
  {
    this.matchColumnIndexes.clear();
    this.matchColumnNames.clear();

    for (int i = 0; i < paramArrayOfString.length; i++)
    {
      if ((paramArrayOfString[i] == null) || (paramArrayOfString[i].equals("")))
      {
        SQLException localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      try
      {
        this.matchColumnNames.add(i, paramArrayOfString[i].trim());
      }
      catch (Exception localException)
      {
        SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 339);
        localSQLException2.fillInStackTrace();
        throw localSQLException2;
      }
    }
  }

  public void unsetMatchColumn(int paramInt)
    throws SQLException
  {
    checkIfMatchColumnIndexesSet();

    if (paramInt <= 0)
    {
      SQLException localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 336);
      localSQLException1.fillInStackTrace();
      throw localSQLException1;
    }

    int i = -1;
    try
    {
      i = ((Integer)this.matchColumnIndexes.get(0)).intValue();
    }
    catch (Exception localException)
    {
      SQLException localSQLException3 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 334);
      localSQLException3.fillInStackTrace();
      throw localSQLException3;
    }

    if (i != paramInt)
    {
      SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 340);
      localSQLException2.fillInStackTrace();
      throw localSQLException2;
    }

    this.matchColumnIndexes.clear();
    this.matchColumnNames.clear();
  }

  public void unsetMatchColumn(int[] paramArrayOfInt)
    throws SQLException
  {
    checkIfMatchColumnIndexesSet();

    if (paramArrayOfInt == null)
    {
      SQLException localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68);
      localSQLException1.fillInStackTrace();
      throw localSQLException1;
    }

    int i = -1;

    for (int j = 0; j < paramArrayOfInt.length; j++)
    {
      if (paramArrayOfInt[j] <= 0)
      {
        SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 336);
        localSQLException2.fillInStackTrace();
        throw localSQLException2;
      }

      try
      {
        i = ((Integer)this.matchColumnIndexes.get(j)).intValue();
      }
      catch (Exception localException)
      {
        SQLException localSQLException4 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 334);
        localSQLException4.fillInStackTrace();
        throw localSQLException4;
      }

      if (i != paramArrayOfInt[j])
      {
        SQLException localSQLException3 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 340);
        localSQLException3.fillInStackTrace();
        throw localSQLException3;
      }

    }

    this.matchColumnIndexes.clear();
    this.matchColumnNames.clear();
  }

  public void unsetMatchColumn(String paramString)
    throws SQLException
  {
    checkIfMatchColumnNamesSet();

    if ((paramString == null) || (paramString.equals("")))
    {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject = null;
    try
    {
      localObject = (String)this.matchColumnNames.get(0);
    }
    catch (Exception localException)
    {
      SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 335);
      localSQLException2.fillInStackTrace();
      throw localSQLException2;
    }

    if (!((String)localObject).equals(paramString.trim()))
    {
      SQLException localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 341);
      localSQLException1.fillInStackTrace();
      throw localSQLException1;
    }

    this.matchColumnIndexes.clear();
    this.matchColumnNames.clear();
  }

  public void unsetMatchColumn(String[] paramArrayOfString)
    throws SQLException
  {
    checkIfMatchColumnNamesSet();

    if (paramArrayOfString == null)
    {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject = null;

    for (int i = 0; i < paramArrayOfString.length; i++)
    {
      if ((paramArrayOfString[i] == null) || (paramArrayOfString[i].equals("")))
      {
        SQLException localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      try
      {
        localObject = (String)this.matchColumnNames.get(i);
      }
      catch (Exception localException)
      {
        SQLException localSQLException3 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 335);
        localSQLException3.fillInStackTrace();
        throw localSQLException3;
      }

      if (!((String)localObject).equals(paramArrayOfString[i]))
      {
        SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 341);
        localSQLException2.fillInStackTrace();
        throw localSQLException2;
      }

    }

    this.matchColumnIndexes.clear();
    this.matchColumnNames.clear();
  }

  protected void checkIfMatchColumnIndexesSet()
    throws SQLException
  {
    if (this.matchColumnIndexes.size() == 0)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 334);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  protected void checkIfMatchColumnNamesSet()
    throws SQLException
  {
    if (this.matchColumnNames.size() == 0)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 335);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  public abstract int findColumn(String paramString)
    throws SQLException;

  public abstract ResultSetMetaData getMetaData()
    throws SQLException;

  abstract String getTableName()
    throws SQLException;

  public boolean isClosed()
    throws SQLException
  {
    return this.isClosed;
  }

  public int getHoldability()
    throws SQLException
  {
    return 1;
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