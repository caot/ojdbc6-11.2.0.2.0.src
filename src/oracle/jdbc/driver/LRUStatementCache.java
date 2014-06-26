package oracle.jdbc.driver;

import java.io.PrintStream;
import java.sql.SQLException;
import oracle.jdbc.internal.OracleConnection;

class LRUStatementCache
{
  private int cacheSize;
  private int numElements;
  private OracleStatementCacheEntry applicationCacheStart;
  private OracleStatementCacheEntry applicationCacheEnd;
  private OracleStatementCacheEntry implicitCacheStart;
  private OracleStatementCacheEntry explicitCacheStart;
  boolean implicitCacheEnabled;
  boolean explicitCacheEnabled;
  private boolean debug = false;

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  protected LRUStatementCache(int paramInt)
    throws SQLException
  {
    if (paramInt < 0)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 123);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.cacheSize = paramInt;
    this.numElements = 0;

    this.implicitCacheStart = null;
    this.explicitCacheStart = null;

    this.implicitCacheEnabled = false;
    this.explicitCacheEnabled = false;
  }

  protected void resize(int paramInt)
    throws SQLException
  {
    SQLException localObject;
    if (paramInt < 0)
    {
      localObject = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 123);
      localObject.fillInStackTrace();
      throw localObject;
    }

    if ((paramInt >= this.cacheSize) || (paramInt >= this.numElements))
    {
      this.cacheSize = paramInt;
    }
    else
    {
      OracleStatementCacheEntry oraclestatementcacheentry;
      for (oraclestatementcacheentry = this.applicationCacheEnd; 
        this.numElements > paramInt; oraclestatementcacheentry = oraclestatementcacheentry.applicationPrev) {
        purgeCacheEntry(oraclestatementcacheentry);
      }
      this.cacheSize = paramInt;
    }
  }

  public void setImplicitCachingEnabled(boolean paramBoolean)
    throws SQLException
  {
    if (!paramBoolean) {
      purgeImplicitCache();
    }
    this.implicitCacheEnabled = paramBoolean;
  }

  public boolean getImplicitCachingEnabled()
    throws SQLException
  {
    boolean bool;
    if (this.cacheSize == 0)
      bool = false;
    else
      bool = this.implicitCacheEnabled;
    return bool;
  }

  public void setExplicitCachingEnabled(boolean paramBoolean)
    throws SQLException
  {
    if (!paramBoolean) {
      purgeExplicitCache();
    }
    this.explicitCacheEnabled = paramBoolean;
  }

  public boolean getExplicitCachingEnabled()
    throws SQLException
  {
    boolean bool;
    if (this.cacheSize == 0)
      bool = false;
    else
      bool = this.explicitCacheEnabled;
    return bool;
  }

  protected void addToImplicitCache(OraclePreparedStatement paramOraclePreparedStatement, String paramString, int paramInt1, int paramInt2)
    throws SQLException
  {
    if ((!this.implicitCacheEnabled) || (this.cacheSize == 0) || (paramOraclePreparedStatement.cacheState == 2))
    {
      return;
    }

    if (this.numElements == this.cacheSize) {
      purgeCacheEntry(this.applicationCacheEnd);
    }

    paramOraclePreparedStatement.enterImplicitCache();

    OracleStatementCacheEntry localOracleStatementCacheEntry = new OracleStatementCacheEntry();

    localOracleStatementCacheEntry.statement = paramOraclePreparedStatement;
    localOracleStatementCacheEntry.onImplicit = true;

    localOracleStatementCacheEntry.sql = paramString;
    localOracleStatementCacheEntry.statementType = paramInt1;
    localOracleStatementCacheEntry.scrollType = paramInt2;

    localOracleStatementCacheEntry.applicationNext = this.applicationCacheStart;
    localOracleStatementCacheEntry.applicationPrev = null;

    if (this.applicationCacheStart != null) {
      this.applicationCacheStart.applicationPrev = localOracleStatementCacheEntry;
    }
    this.applicationCacheStart = localOracleStatementCacheEntry;

    localOracleStatementCacheEntry.implicitNext = this.implicitCacheStart;
    localOracleStatementCacheEntry.implicitPrev = null;

    if (this.implicitCacheStart != null) {
      this.implicitCacheStart.implicitPrev = localOracleStatementCacheEntry;
    }
    this.implicitCacheStart = localOracleStatementCacheEntry;

    if (this.applicationCacheEnd == null) {
      this.applicationCacheEnd = localOracleStatementCacheEntry;
    }

    this.numElements += 1;
  }

  protected void addToExplicitCache(OraclePreparedStatement paramOraclePreparedStatement, String paramString)
    throws SQLException
  {
    if ((!this.explicitCacheEnabled) || (this.cacheSize == 0) || (paramOraclePreparedStatement.cacheState == 2))
    {
      return;
    }

    if (this.numElements == this.cacheSize) {
      purgeCacheEntry(this.applicationCacheEnd);
    }

    paramOraclePreparedStatement.enterExplicitCache();

    OracleStatementCacheEntry localOracleStatementCacheEntry = new OracleStatementCacheEntry();

    localOracleStatementCacheEntry.statement = paramOraclePreparedStatement;
    localOracleStatementCacheEntry.sql = paramString;
    localOracleStatementCacheEntry.onImplicit = false;

    localOracleStatementCacheEntry.applicationNext = this.applicationCacheStart;
    localOracleStatementCacheEntry.applicationPrev = null;

    if (this.applicationCacheStart != null) {
      this.applicationCacheStart.applicationPrev = localOracleStatementCacheEntry;
    }
    this.applicationCacheStart = localOracleStatementCacheEntry;

    localOracleStatementCacheEntry.explicitNext = this.explicitCacheStart;
    localOracleStatementCacheEntry.explicitPrev = null;

    if (this.explicitCacheStart != null) {
      this.explicitCacheStart.explicitPrev = localOracleStatementCacheEntry;
    }
    this.explicitCacheStart = localOracleStatementCacheEntry;

    if (this.applicationCacheEnd == null) {
      this.applicationCacheEnd = localOracleStatementCacheEntry;
    }

    this.numElements += 1;
  }

  protected OracleStatement searchImplicitCache(String paramString, int paramInt1, int paramInt2)
    throws SQLException
  {
    if (!this.implicitCacheEnabled)
    {
      return null;
    }

    OracleStatementCacheEntry localOracleStatementCacheEntry = null;

    for (localOracleStatementCacheEntry = this.implicitCacheStart; localOracleStatementCacheEntry != null; localOracleStatementCacheEntry = localOracleStatementCacheEntry.implicitNext)
    {
      if ((localOracleStatementCacheEntry.statementType == paramInt1) && (localOracleStatementCacheEntry.scrollType == paramInt2) && (localOracleStatementCacheEntry.sql.equals(paramString)))
      {
        break;
      }
    }
    if (localOracleStatementCacheEntry != null)
    {
      if (localOracleStatementCacheEntry.applicationPrev != null) {
        localOracleStatementCacheEntry.applicationPrev.applicationNext = localOracleStatementCacheEntry.applicationNext;
      }
      if (localOracleStatementCacheEntry.applicationNext != null) {
        localOracleStatementCacheEntry.applicationNext.applicationPrev = localOracleStatementCacheEntry.applicationPrev;
      }
      if (this.applicationCacheStart == localOracleStatementCacheEntry) {
        this.applicationCacheStart = localOracleStatementCacheEntry.applicationNext;
      }
      if (this.applicationCacheEnd == localOracleStatementCacheEntry) {
        this.applicationCacheEnd = localOracleStatementCacheEntry.applicationPrev;
      }
      if (localOracleStatementCacheEntry.implicitPrev != null) {
        localOracleStatementCacheEntry.implicitPrev.implicitNext = localOracleStatementCacheEntry.implicitNext;
      }
      if (localOracleStatementCacheEntry.implicitNext != null) {
        localOracleStatementCacheEntry.implicitNext.implicitPrev = localOracleStatementCacheEntry.implicitPrev;
      }
      if (this.implicitCacheStart == localOracleStatementCacheEntry) {
        this.implicitCacheStart = localOracleStatementCacheEntry.implicitNext;
      }

      this.numElements -= 1;

      localOracleStatementCacheEntry.statement.exitImplicitCacheToActive();

      return localOracleStatementCacheEntry.statement;
    }

    return null;
  }

  protected OracleStatement searchExplicitCache(String paramString)
    throws SQLException
  {
    if (!this.explicitCacheEnabled)
    {
      return null;
    }

    OracleStatementCacheEntry localOracleStatementCacheEntry = null;

    for (localOracleStatementCacheEntry = this.explicitCacheStart; localOracleStatementCacheEntry != null; localOracleStatementCacheEntry = localOracleStatementCacheEntry.explicitNext)
    {
      if (localOracleStatementCacheEntry.sql.equals(paramString)) {
        break;
      }
    }
    if (localOracleStatementCacheEntry != null)
    {
      if (localOracleStatementCacheEntry.applicationPrev != null) {
        localOracleStatementCacheEntry.applicationPrev.applicationNext = localOracleStatementCacheEntry.applicationNext;
      }
      if (localOracleStatementCacheEntry.applicationNext != null) {
        localOracleStatementCacheEntry.applicationNext.applicationPrev = localOracleStatementCacheEntry.applicationPrev;
      }
      if (this.applicationCacheStart == localOracleStatementCacheEntry) {
        this.applicationCacheStart = localOracleStatementCacheEntry.applicationNext;
      }
      if (this.applicationCacheEnd == localOracleStatementCacheEntry) {
        this.applicationCacheEnd = localOracleStatementCacheEntry.applicationPrev;
      }
      if (localOracleStatementCacheEntry.explicitPrev != null) {
        localOracleStatementCacheEntry.explicitPrev.explicitNext = localOracleStatementCacheEntry.explicitNext;
      }
      if (localOracleStatementCacheEntry.explicitNext != null) {
        localOracleStatementCacheEntry.explicitNext.explicitPrev = localOracleStatementCacheEntry.explicitPrev;
      }
      if (this.explicitCacheStart == localOracleStatementCacheEntry) {
        this.explicitCacheStart = localOracleStatementCacheEntry.explicitNext;
      }

      this.numElements -= 1;

      localOracleStatementCacheEntry.statement.exitExplicitCacheToActive();

      return localOracleStatementCacheEntry.statement;
    }

    return null;
  }

  protected void purgeImplicitCache()
    throws SQLException
  {
    for (OracleStatementCacheEntry localOracleStatementCacheEntry = this.implicitCacheStart; localOracleStatementCacheEntry != null; 
      localOracleStatementCacheEntry = localOracleStatementCacheEntry.implicitNext) {
      purgeCacheEntry(localOracleStatementCacheEntry);
    }
    this.implicitCacheStart = null;
  }

  protected void purgeExplicitCache()
    throws SQLException
  {
    for (OracleStatementCacheEntry localOracleStatementCacheEntry = this.explicitCacheStart; localOracleStatementCacheEntry != null; 
      localOracleStatementCacheEntry = localOracleStatementCacheEntry.explicitNext) {
      purgeCacheEntry(localOracleStatementCacheEntry);
    }
    this.explicitCacheStart = null;
  }

  private void purgeCacheEntry(OracleStatementCacheEntry paramOracleStatementCacheEntry)
    throws SQLException
  {
    if (paramOracleStatementCacheEntry.applicationNext != null) {
      paramOracleStatementCacheEntry.applicationNext.applicationPrev = paramOracleStatementCacheEntry.applicationPrev;
    }
    if (paramOracleStatementCacheEntry.applicationPrev != null) {
      paramOracleStatementCacheEntry.applicationPrev.applicationNext = paramOracleStatementCacheEntry.applicationNext;
    }
    if (this.applicationCacheStart == paramOracleStatementCacheEntry) {
      this.applicationCacheStart = paramOracleStatementCacheEntry.applicationNext;
    }
    if (this.applicationCacheEnd == paramOracleStatementCacheEntry) {
      this.applicationCacheEnd = paramOracleStatementCacheEntry.applicationPrev;
    }
    if (paramOracleStatementCacheEntry.onImplicit)
    {
      if (paramOracleStatementCacheEntry.implicitNext != null) {
        paramOracleStatementCacheEntry.implicitNext.implicitPrev = paramOracleStatementCacheEntry.implicitPrev;
      }
      if (paramOracleStatementCacheEntry.implicitPrev != null) {
        paramOracleStatementCacheEntry.implicitPrev.implicitNext = paramOracleStatementCacheEntry.implicitNext;
      }
      if (this.implicitCacheStart == paramOracleStatementCacheEntry)
        this.implicitCacheStart = paramOracleStatementCacheEntry.implicitNext;
    }
    else
    {
      if (paramOracleStatementCacheEntry.explicitNext != null) {
        paramOracleStatementCacheEntry.explicitNext.explicitPrev = paramOracleStatementCacheEntry.explicitPrev;
      }
      if (paramOracleStatementCacheEntry.explicitPrev != null) {
        paramOracleStatementCacheEntry.explicitPrev.explicitNext = paramOracleStatementCacheEntry.explicitNext;
      }
      if (this.explicitCacheStart == paramOracleStatementCacheEntry) {
        this.explicitCacheStart = paramOracleStatementCacheEntry.explicitNext;
      }
    }

    this.numElements -= 1;

    if (paramOracleStatementCacheEntry.onImplicit)
      paramOracleStatementCacheEntry.statement.exitImplicitCacheToClose();
    else
      paramOracleStatementCacheEntry.statement.exitExplicitCacheToClose();
  }

  public int getCacheSize()
  {
    return this.cacheSize;
  }

  public void printCache(String paramString)
    throws SQLException
  {
    System.out.println("*** Start of Statement Cache Dump (" + paramString + ") ***");
    System.out.println("cache size: " + this.cacheSize + " num elements: " + this.numElements + " implicit enabled: " + this.implicitCacheEnabled + " explicit enabled: " + this.explicitCacheEnabled);

    System.out.println("applicationStart: " + this.applicationCacheStart + "  applicationEnd: " + this.applicationCacheEnd);

    OracleStatementCacheEntry localOracleStatementCacheEntry;
    for (localOracleStatementCacheEntry = this.applicationCacheStart; localOracleStatementCacheEntry != null; localOracleStatementCacheEntry = localOracleStatementCacheEntry.applicationNext) {
      localOracleStatementCacheEntry.print();
    }
    System.out.println("implicitStart: " + this.implicitCacheStart);

    for (localOracleStatementCacheEntry = this.implicitCacheStart; localOracleStatementCacheEntry != null; localOracleStatementCacheEntry = localOracleStatementCacheEntry.implicitNext) {
      localOracleStatementCacheEntry.print();
    }
    System.out.println("explicitStart: " + this.explicitCacheStart);

    for (localOracleStatementCacheEntry = this.explicitCacheStart; localOracleStatementCacheEntry != null; localOracleStatementCacheEntry = localOracleStatementCacheEntry.explicitNext) {
      localOracleStatementCacheEntry.print();
    }
    System.out.println("*** End of Statement Cache Dump (" + paramString + ") ***");
  }

  public void close()
    throws SQLException
  {
    for (OracleStatementCacheEntry localOracleStatementCacheEntry = this.applicationCacheStart; 
      localOracleStatementCacheEntry != null; localOracleStatementCacheEntry = localOracleStatementCacheEntry.applicationNext)
    {
      if (localOracleStatementCacheEntry.onImplicit)
        localOracleStatementCacheEntry.statement.exitImplicitCacheToClose();
      else {
        localOracleStatementCacheEntry.statement.exitExplicitCacheToClose();
      }

    }

    this.applicationCacheStart = null;
    this.applicationCacheEnd = null;
    this.implicitCacheStart = null;
    this.explicitCacheStart = null;
    this.numElements = 0;
  }

  protected OracleConnection getConnectionDuringExceptionHandling()
  {
    return null;
  }
}