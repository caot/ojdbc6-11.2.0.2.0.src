package oracle.jdbc.driver;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;
import java.util.Vector;
import oracle.jdbc.OracleResultSet.AuthorizationIndicator;
import oracle.jdbc.internal.OracleConnection;
import oracle.sql.ARRAY;
import oracle.sql.BFILE;
import oracle.sql.BLOB;
import oracle.sql.CHAR;
import oracle.sql.CLOB;
import oracle.sql.CustomDatum;
import oracle.sql.CustomDatumFactory;
import oracle.sql.DATE;
import oracle.sql.Datum;
import oracle.sql.INTERVALDS;
import oracle.sql.INTERVALYM;
import oracle.sql.NCLOB;
import oracle.sql.NUMBER;
import oracle.sql.OPAQUE;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.RAW;
import oracle.sql.REF;
import oracle.sql.ROWID;
import oracle.sql.STRUCT;
import oracle.sql.TIMESTAMP;
import oracle.sql.TIMESTAMPLTZ;
import oracle.sql.TIMESTAMPTZ;

class ScrollableResultSet extends BaseResultSet
{
  PhysicalConnection connection;
  OracleResultSetImpl resultSet;
  ScrollRsetStatement scrollStmt;
  ResultSetMetaData metadata;
  private int rsetType;
  private int rsetConcurency;
  private int beginColumnIndex;
  private int columnCount;
  private int wasNull;
  OracleResultSetCache rsetCache;
  int currentRow;
  private int numRowsCached;
  private boolean allRowsCached;
  private int lastRefetchSz;
  private Vector refetchRowids;
  private OraclePreparedStatement refetchStmt;
  private int usrFetchDirection;
  private static final ClassRef XMLTYPE_CLASS;

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  ScrollableResultSet(ScrollRsetStatement paramScrollRsetStatement, OracleResultSetImpl paramOracleResultSetImpl, int paramInt1, int paramInt2)
    throws SQLException
  {
    this.connection = ((OracleStatement)paramScrollRsetStatement).connection;
    this.resultSet = paramOracleResultSetImpl;
    this.metadata = null;
    this.scrollStmt = paramScrollRsetStatement;
    this.rsetType = paramInt1;
    this.rsetConcurency = paramInt2;
    this.beginColumnIndex = (needIdentifier(paramInt1, paramInt2) ? 1 : 0);
    this.columnCount = 0;
    this.wasNull = -1;
    this.rsetCache = paramScrollRsetStatement.getResultSetCache();

    if (this.rsetCache == null)
    {
      this.rsetCache = new OracleResultSetCacheImpl();
    }
    else
    {
      try
      {
        this.rsetCache.clear();
      }
      catch (IOException localIOException)
      {
        SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localIOException);
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }

    }

    this.currentRow = 0;
    this.numRowsCached = 0;
    this.allRowsCached = false;
    this.lastRefetchSz = 0;
    this.refetchRowids = null;
    this.refetchStmt = null;
    this.usrFetchDirection = 1000;
    getInternalMetadata();
  }

  public void close()
    throws SQLException
  {
    synchronized (this.connection)
    {
      if (this.closed) return;
      super.close();
      if (this.resultSet != null)
        this.resultSet.close();
      if (this.refetchStmt != null)
        this.refetchStmt.close();
      if (this.scrollStmt != null)
        this.scrollStmt.notifyCloseRset();
      if (this.refetchRowids != null) {
        this.refetchRowids.removeAllElements();
      }
      this.resultSet = null;
      this.scrollStmt = null;
      this.refetchStmt = null;
      this.refetchRowids = null;
      this.metadata = null;
      try
      {
        if (this.rsetCache != null)
        {
          this.rsetCache.clear();
          this.rsetCache.close();
        }

      }
      catch (IOException localIOException)
      {
        SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localIOException);
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }
      finally
      {
        this.rsetCache = null;
      }
    }
  }

  public boolean wasNull()
    throws SQLException
  {
    synchronized (this.connection)
    {
      if (this.wasNull == -1)
      {
        SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 24);
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }

      return this.wasNull == 1;
    }
  }

  public Statement getStatement()
    throws SQLException
  {
    synchronized (this.connection)
    {
      return (Statement)this.scrollStmt;
    }
  }

  void resetBeginColumnIndex()
  {
    synchronized (this.connection)
    {
      this.beginColumnIndex = 0;
    }
  }

  ResultSet getResultSet()
  {
    synchronized (this.connection)
    {
      return this.resultSet;
    }
  }

  int removeRowInCache(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      if ((!isEmptyResultSet()) && (isValidRow(paramInt)))
      {
        removeCachedRowAt(paramInt);

        this.numRowsCached -= 1;

        if (paramInt >= this.currentRow) {
          this.currentRow -= 1;
        }
        return 1;
      }

      return 0;
    }
  }

  int refreshRowsInCache(int paramInt1, int paramInt2, int paramInt3)
    throws SQLException
  {
    synchronized (this.connection)
    {
      OracleResultSetImpl localOracleResultSetImpl = null;
      int i = 0;

      i = get_refetch_size(paramInt1, paramInt2, paramInt3);
      try
      {
        if (i > 0)
        {
          if (i != this.lastRefetchSz)
          {
            if (this.refetchStmt != null) {
              this.refetchStmt.close();
            }
            this.refetchStmt = prepare_refetch_statement(i);
            this.refetchStmt.setQueryTimeout(((OracleStatement)this.scrollStmt).getQueryTimeout());
            this.lastRefetchSz = i;
          }

          prepare_refetch_binds(this.refetchStmt, i);

          localOracleResultSetImpl = (OracleResultSetImpl)this.refetchStmt.executeQuery();

          save_refetch_results(localOracleResultSetImpl, paramInt1, i, paramInt3);
        }

      }
      finally
      {
        if (localOracleResultSetImpl != null) {
          localOracleResultSetImpl.close();
        }
      }
      return i;
    }
  }

  public boolean next()
    throws SQLException
  {
    synchronized (this.connection)
    {
      if (this.closed)
      {
        SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 10);
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }

      if (isEmptyResultSet())
      {
        return false;
      }

      if (this.currentRow < 1)
      {
        this.currentRow = 1;
      }
      else
      {
        this.currentRow += 1;
      }

      return isValidRow(this.currentRow);
    }
  }

  public boolean isBeforeFirst()
    throws SQLException
  {
    synchronized (this.connection)
    {
      if (this.closed)
      {
        SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 10);
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }
      return (!isEmptyResultSet()) && (this.currentRow < 1);
    }
  }

  public boolean isAfterLast()
    throws SQLException
  {
    synchronized (this.connection)
    {
      if (this.closed)
      {
        SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 10);
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }
      return (!isEmptyResultSet()) && (this.currentRow > 0) && (!isValidRow(this.currentRow));
    }
  }

  public boolean isFirst()
    throws SQLException
  {
    synchronized (this.connection)
    {
      if (this.closed)
      {
        SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 10);
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }
      return this.currentRow == 1;
    }
  }

  public boolean isLast()
    throws SQLException
  {
    synchronized (this.connection)
    {
      if (this.closed)
      {
        SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 10);
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }
      return (!isEmptyResultSet()) && (isValidRow(this.currentRow)) && (!isValidRow(this.currentRow + 1));
    }
  }

  public void beforeFirst()
    throws SQLException
  {
    synchronized (this.connection)
    {
      if (this.closed)
      {
        SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 10);
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }
      if (!isEmptyResultSet())
        this.currentRow = 0;
    }
  }

  public void afterLast()
    throws SQLException
  {
    synchronized (this.connection) {
      if (this.closed)
      {
        SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 10);
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }

      if (!isEmptyResultSet())
        this.currentRow = (getLastRow() + 1);
    }
  }

  public boolean first()
    throws SQLException
  {
    synchronized (this.connection)
    {
      if (this.closed)
      {
        SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 10);
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }
      if (isEmptyResultSet())
      {
        return false;
      }

      this.currentRow = 1;

      return isValidRow(this.currentRow);
    }
  }

  public boolean last()
    throws SQLException
  {
    synchronized (this.connection)
    {
      if (this.closed)
      {
        SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 10);
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }
      if (isEmptyResultSet())
      {
        return false;
      }

      this.currentRow = getLastRow();

      return isValidRow(this.currentRow);
    }
  }

  public int getRow()
    throws SQLException
  {
    synchronized (this.connection)
    {
      if (this.closed)
      {
        SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 10);
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }
      if (isValidRow(this.currentRow)) {
        return this.currentRow;
      }
      return 0;
    }
  }

  public boolean absolute(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      SQLException sqlexception;
      if (this.closed)
      {
        sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 10);
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }
      if (paramInt == 0)
      {
        sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68, "absolute(" + paramInt + ")");
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }

      if (isEmptyResultSet())
      {
        return false;
      }
      if (paramInt > 0)
      {
        this.currentRow = paramInt;
      }
      else if (paramInt < 0)
      {
        this.currentRow = (getLastRow() + 1 + paramInt);
      }

      return isValidRow(this.currentRow);
    }
  }

  public boolean relative(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      if (this.closed)
      {
        SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 10);
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }
      if (isEmptyResultSet())
      {
        return false;
      }
      if (isValidRow(this.currentRow))
      {
        this.currentRow += paramInt;

        return isValidRow(this.currentRow);
      }

      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 82, "relative");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }
  }

  public boolean previous()
    throws SQLException
  {
    synchronized (this.connection)
    {
      if (this.closed)
      {
        SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 10);
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }
      if (isEmptyResultSet())
      {
        return false;
      }
      if (isAfterLast())
      {
        this.currentRow = getLastRow();
      }
      else
      {
        this.currentRow -= 1;
      }

      return isValidRow(this.currentRow);
    }
  }

  public Datum getOracleObject(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      if (this.closed)
      {
        SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 10);
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }

      this.wasNull = -1;

      if (!isValidRow(this.currentRow))
      {
        SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 11);
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }

      if ((paramInt < 1) || (paramInt > getColumnCount()))
      {
        SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }

      Datum datum = getCachedDatumValueAt(this.currentRow, paramInt + this.beginColumnIndex);

      this.wasNull = (datum == null ? 1 : 0);

      return datum;
    }
  }

  public String getString(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      Datum localDatum = getOracleObject(paramInt);

      if (localDatum != null)
      {
        switch (getInternalMetadata().getColumnType(paramInt + this.beginColumnIndex))
        {
        case 2005:
        case 2011:
          CLOB localCLOB = (CLOB)localDatum;
          return localCLOB.getSubString(1L, (int)localCLOB.length());
        }

        return localDatum.stringValue(this.connection);
      }

      return null;
    }
  }

  public boolean getBoolean(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      Datum localDatum = getOracleObject(paramInt);

      if (localDatum != null)
      {
        return localDatum.booleanValue();
      }

      return false;
    }
  }

  public OracleResultSet.AuthorizationIndicator getAuthorizationIndicator(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      if (!isValidRow(this.currentRow))
      {
        SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 11);
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }

      if ((paramInt < 1) || (paramInt > getColumnCount()))
      {
        SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }

      Object localObject1 = null;
      OracleResultSet.AuthorizationIndicator localAuthorizationIndicator = null;
      try
      {
        localObject1 = (CachedRowElement)this.rsetCache.get(this.currentRow, paramInt);
      }
      catch (IOException localIOException)
      {
        SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localIOException);
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }

      if (localObject1 != null) {
        localAuthorizationIndicator = ((CachedRowElement)localObject1).getIndicator();
      }
      return localAuthorizationIndicator;
    }
  }

  public byte getByte(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      Datum localDatum = getOracleObject(paramInt);

      if (localDatum != null) {
        return localDatum.byteValue();
      }
      return 0;
    }
  }

  public short getShort(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      long l = getLong(paramInt);

      if ((l > 65537L) || (l < -65538L))
      {
        SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 26, "getShort");
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }

      return (short)(int)l;
    }
  }

  public int getInt(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      Datum localDatum = getOracleObject(paramInt);

      if (localDatum != null)
      {
        return localDatum.intValue();
      }

      return 0;
    }
  }

  public long getLong(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      Datum localDatum = getOracleObject(paramInt);

      if (localDatum != null)
      {
        return localDatum.longValue();
      }

      return 0L;
    }
  }

  public float getFloat(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      Datum localDatum = getOracleObject(paramInt);

      if (localDatum != null)
      {
        return localDatum.floatValue();
      }

      return 0.0F;
    }
  }

  public double getDouble(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      Datum localDatum = getOracleObject(paramInt);

      if (localDatum != null)
      {
        return localDatum.doubleValue();
      }

      return 0.0D;
    }
  }

  public BigDecimal getBigDecimal(int paramInt1, int paramInt2)
    throws SQLException
  {
    synchronized (this.connection)
    {
      Datum localDatum = getOracleObject(paramInt1);

      if (localDatum != null)
      {
        return localDatum.bigDecimalValue();
      }

      return null;
    }
  }

  public byte[] getBytes(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      byte[] arrayOfByte = null;
      Datum localDatum = getOracleObject(paramInt);

      if (localDatum != null)
      {
        if ((localDatum instanceof RAW)) {
          arrayOfByte = ((RAW)localDatum).shareBytes();
        } else if ((localDatum instanceof BLOB))
        {
          BLOB localBLOB = (BLOB)localDatum;
          long l = localBLOB.length();
          if (l > 2147483647L)
          {
            SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 151);
            sqlexception.fillInStackTrace();
            throw sqlexception;
          }

          arrayOfByte = localBLOB.getBytes(1L, (int)l);
          if (localBLOB.isTemporary()) this.resultSet.statement.addToTempLobsToFree(localBLOB);
        }
        else
        {
          arrayOfByte = localDatum.getBytes();
        }
      }
      return arrayOfByte;
    }
  }

  public Date getDate(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      Datum localDatum = getOracleObject(paramInt);
      Date localDate = null;

      if (localDatum != null)
      {
        ResultSetMetaData localResultSetMetaData = getInternalMetadata();
        switch (localResultSetMetaData.getColumnType(paramInt + this.beginColumnIndex))
        {
        case -101:
          localDate = ((TIMESTAMPTZ)localDatum).dateValue(this.connection);
          break;
        case -102:
          localDate = ((TIMESTAMPLTZ)localDatum).dateValue(this.connection);
          break;
        default:
          localDate = localDatum.dateValue();
        }

      }

      return localDate;
    }
  }

  public Time getTime(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      Datum localDatum = getOracleObject(paramInt);
      Time localTime = null;

      if (localDatum != null)
      {
        ResultSetMetaData localResultSetMetaData = getInternalMetadata();
        switch (localResultSetMetaData.getColumnType(paramInt + this.beginColumnIndex))
        {
        case -101:
          localTime = ((TIMESTAMPTZ)localDatum).timeValue(this.connection);
          break;
        case -102:
          localTime = ((TIMESTAMPLTZ)localDatum).timeValue(this.connection, this.connection.getDbTzCalendar());

          break;
        default:
          localTime = localDatum.timeValue();
        }

      }

      return localTime;
    }
  }

  public Timestamp getTimestamp(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      Datum localDatum = getOracleObject(paramInt);
      Timestamp localTimestamp = null;

      if (localDatum != null)
      {
        ResultSetMetaData localResultSetMetaData = getInternalMetadata();
        switch (localResultSetMetaData.getColumnType(paramInt + this.beginColumnIndex))
        {
        case -101:
          localTimestamp = ((TIMESTAMPTZ)localDatum).timestampValue(this.connection);
          break;
        case -102:
          localTimestamp = ((TIMESTAMPLTZ)localDatum).timestampValue(this.connection, this.connection.getDbTzCalendar());

          break;
        default:
          localTimestamp = localDatum.timestampValue();
        }

      }

      return localTimestamp;
    }
  }

  public InputStream getAsciiStream(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      Datum localDatum = getOracleObject(paramInt);

      if (localDatum != null)
      {
        int i = getInternalMetadata().getColumnType(paramInt + this.beginColumnIndex);

        if (((localDatum instanceof CHAR)) && ((i == -15) || (i == -9)))
        {
          DBConversion localDBConversion = this.connection.conversion;
          byte[] arrayOfByte = localDatum.shareBytes();

          return localDBConversion.ConvertStream(new ByteArrayInputStream(arrayOfByte), 12);
        }

        return localDatum.asciiStreamValue();
      }

      return null;
    }
  }

  public InputStream getUnicodeStream(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      Datum localDatum = getOracleObject(paramInt);

      if (localDatum != null)
      {
        DBConversion localDBConversion = this.connection.conversion;
        byte[] arrayOfByte = localDatum.shareBytes();

        if ((localDatum instanceof RAW))
        {
          return localDBConversion.ConvertStream(new ByteArrayInputStream(arrayOfByte), 3);
        }

        if ((localDatum instanceof CHAR))
        {
          return localDBConversion.ConvertStream(new ByteArrayInputStream(arrayOfByte), 1);
        }

        SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4, "getUnicodeStream");
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }

      return null;
    }
  }

  public InputStream getBinaryStream(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      Datum localDatum = getOracleObject(paramInt);

      if (localDatum != null)
      {
        return localDatum.binaryStreamValue();
      }

      return null;
    }
  }

  public Object getObject(int paramInt)
    throws SQLException
  {
    return getObject(paramInt, this.connection.getTypeMap());
  }

  public Reader getCharacterStream(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      Datum localDatum = getOracleObject(paramInt);

      if (localDatum != null)
      {
        return localDatum.characterStreamValue();
      }

      return null;
    }
  }

  public BigDecimal getBigDecimal(int paramInt)
    throws SQLException
  {
    return getBigDecimal(paramInt, 0);
  }

  public Object getObject(int paramInt, Map paramMap)
    throws SQLException
  {
    synchronized (this.connection)
    {
      Object localObject1 = null;
      Datum localDatum = getOracleObject(paramInt);

      if (localDatum != null)
      {
        int i = getInternalMetadata().getColumnType(paramInt + this.beginColumnIndex);

        switch (i)
        {
        case 2002:
          localObject1 = ((STRUCT)localDatum).toJdbc(paramMap);
          break;
        case 91:
          if (this.connection.mapDateToTimestamp)
            localObject1 = localDatum.toJdbc();
          else
            localObject1 = localDatum.dateValue();
          break;
        case 93:
          if ((localDatum instanceof DATE))
          {
            if (this.connection.mapDateToTimestamp)
              localObject1 = localDatum.toJdbc();
            else {
              localObject1 = localDatum.dateValue();
            }

          }
          else if (this.connection.j2ee13Compliant)
          {
            localObject1 = localDatum.toJdbc();
          }
          else
          {
            localObject1 = localDatum;
          }

          break;
        case -102:
        case -101:
          localObject1 = localDatum;
          break;
        case 2007:
          localObject1 = ((OPAQUE)localDatum).toJdbc(paramMap);
          if ((!this.connection.getObjectReturnsXmlType) && (XMLTYPE_CLASS != null) && (XMLTYPE_CLASS.get().isInstance(localObject1)))
          {
            localObject1 = new OracleSQLXML(this.connection, (OPAQUE)localDatum); } break;
        default:
          localObject1 = localDatum.toJdbc();
        }
      }

      return localObject1;
    }
  }

  public Ref getRef(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      return getREF(paramInt);
    }
  }

  public Blob getBlob(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      return getBLOB(paramInt);
    }
  }

  public Clob getClob(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      return getCLOB(paramInt);
    }
  }

  public Array getArray(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      return getARRAY(paramInt);
    }
  }

  public Date getDate(int paramInt, Calendar paramCalendar)
    throws SQLException
  {
    synchronized (this.connection)
    {
      Datum localDatum = getOracleObject(paramInt);
      Date localDate = null;

      if (localDatum != null)
      {
        ResultSetMetaData localResultSetMetaData = getInternalMetadata();
        switch (localResultSetMetaData.getColumnType(paramInt + this.beginColumnIndex))
        {
        case -101:
          localDate = new Date(((TIMESTAMPTZ)localDatum).timestampValue(this.connection).getTime());

          break;
        case -102:
          Calendar localCalendar = this.connection.getDbTzCalendar();

          localDate = new Date(((TIMESTAMPLTZ)localDatum).timestampValue(this.connection, localCalendar == null ? paramCalendar : localCalendar).getTime());

          break;
        default:
          localDate = new Date(localDatum.timestampValue(paramCalendar).getTime());
        }
      }

      return localDate;
    }
  }

  public Time getTime(int paramInt, Calendar paramCalendar)
    throws SQLException
  {
    synchronized (this.connection)
    {
      Datum localDatum = getOracleObject(paramInt);
      Time localTime = null;

      if (localDatum != null)
      {
        ResultSetMetaData localResultSetMetaData = getInternalMetadata();
        switch (localResultSetMetaData.getColumnType(paramInt + this.beginColumnIndex))
        {
        case -101:
          localTime = new Time(((TIMESTAMPTZ)localDatum).timestampValue(this.connection).getTime());
          break;
        case -102:
          Calendar localCalendar = this.connection.getDbTzCalendar();
          localTime = new Time(((TIMESTAMPLTZ)localDatum).timestampValue(this.connection, localCalendar == null ? paramCalendar : localCalendar).getTime());

          break;
        default:
          localTime = new Time(localDatum.timestampValue(paramCalendar).getTime());
        }
      }

      return localTime;
    }
  }

  public Timestamp getTimestamp(int paramInt, Calendar paramCalendar)
    throws SQLException
  {
    synchronized (this.connection)
    {
      Datum localDatum = getOracleObject(paramInt);
      Timestamp localTimestamp = null;

      if (localDatum != null)
      {
        ResultSetMetaData localResultSetMetaData = getInternalMetadata();
        switch (localResultSetMetaData.getColumnType(paramInt + this.beginColumnIndex))
        {
        case -101:
          localTimestamp = ((TIMESTAMPTZ)localDatum).timestampValue(this.connection);
          break;
        case -102:
          Calendar localCalendar = this.connection.getDbTzCalendar();
          localTimestamp = ((TIMESTAMPLTZ)localDatum).timestampValue(this.connection, localCalendar == null ? paramCalendar : localCalendar);

          break;
        default:
          localTimestamp = localDatum.timestampValue(paramCalendar);
        }
      }

      return localTimestamp;
    }
  }

  public URL getURL(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      URL localURL = null;

      int i = getInternalMetadata().getColumnType(paramInt + this.beginColumnIndex);
      int j = SQLUtil.getInternalType(i);

      if ((j == 96) || (j == 1) || (j == 8))
      {
        try
        {
          String str = getString(paramInt);
          if (str == null) localURL = null; else {
            localURL = new URL(str);
          }
        }
        catch (MalformedURLException localMalformedURLException)
        {
          SQLException sqlexception2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 136);
          sqlexception2.fillInStackTrace();
          throw sqlexception2;
        }

      }
      else
      {
        SQLException sqlexception1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 1, "Conversion to java.net.URL not supported.");
        sqlexception1.fillInStackTrace();
        throw sqlexception1;
      }

      return localURL;
    }
  }

  public ResultSet getCursor(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4, "getCursor");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }
  }

  public ROWID getROWID(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      Datum localDatum = getOracleObject(paramInt);

      if (localDatum != null)
      {
        if ((localDatum instanceof ROWID)) {
          return (ROWID)localDatum;
        }

        SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4, "getROWID");
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }

      return null;
    }
  }

  public NUMBER getNUMBER(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      Datum localDatum = getOracleObject(paramInt);

      if (localDatum != null)
      {
        if ((localDatum instanceof NUMBER)) {
          return (NUMBER)localDatum;
        }

        SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4, "getNUMBER");
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }

      return null;
    }
  }

  public DATE getDATE(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      Datum localDatum = getOracleObject(paramInt);

      if (localDatum != null)
      {
        if ((localDatum instanceof DATE))
          return (DATE)localDatum;
        if ((localDatum instanceof TIMESTAMP))
          return TIMESTAMP.toDATE(localDatum.getBytes());
        if ((localDatum instanceof TIMESTAMPLTZ))
          return TIMESTAMPLTZ.toDATE(this.connection, localDatum.getBytes());
        if ((localDatum instanceof TIMESTAMPTZ)) {
          return TIMESTAMPTZ.toDATE(this.connection, localDatum.getBytes());
        }

        SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4, "getDATE");
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }

      return null;
    }
  }

  public TIMESTAMP getTIMESTAMP(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      Datum localDatum = getOracleObject(paramInt);

      if (localDatum != null)
      {
        if ((localDatum instanceof TIMESTAMP))
          return (TIMESTAMP)localDatum;
        if ((localDatum instanceof TIMESTAMPLTZ))
          return TIMESTAMPLTZ.toTIMESTAMP(this.connection, localDatum.getBytes());
        if ((localDatum instanceof TIMESTAMPTZ))
          return TIMESTAMPTZ.toTIMESTAMP(this.connection, localDatum.getBytes());
        if ((localDatum instanceof DATE)) {
          return new TIMESTAMP((DATE)localDatum);
        }

        SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4, "getTIMESTAMP");
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }

      return null;
    }
  }

  public TIMESTAMPTZ getTIMESTAMPTZ(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      Datum localDatum = getOracleObject(paramInt);

      if (localDatum != null)
      {
        if ((localDatum instanceof TIMESTAMPTZ))
          return (TIMESTAMPTZ)localDatum;
        if ((localDatum instanceof TIMESTAMPLTZ)) {
          return TIMESTAMPLTZ.toTIMESTAMPTZ(this.connection, localDatum.getBytes());
        }

        SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4, "getTIMESTAMPTZ");
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }

      return null;
    }
  }

  public TIMESTAMPLTZ getTIMESTAMPLTZ(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      Datum localDatum = getOracleObject(paramInt);

      if (localDatum != null)
      {
        if ((localDatum instanceof TIMESTAMPLTZ)) {
          return (TIMESTAMPLTZ)localDatum;
        }

        SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4, "getTIMESTAMPLTZ");
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }

      return null;
    }
  }

  public INTERVALDS getINTERVALDS(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      Datum localDatum = getOracleObject(paramInt);

      if (localDatum != null)
      {
        if ((localDatum instanceof INTERVALDS)) {
          return (INTERVALDS)localDatum;
        }

        SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4, "getINTERVALDS");
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }

      return null;
    }
  }

  public INTERVALYM getINTERVALYM(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      Datum localDatum = getOracleObject(paramInt);

      if (localDatum != null)
      {
        if ((localDatum instanceof INTERVALYM)) {
          return (INTERVALYM)localDatum;
        }

        SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4, "getINTERVALYM");
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }

      return null;
    }
  }

  public ARRAY getARRAY(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      Datum localDatum = getOracleObject(paramInt);

      if (localDatum != null)
      {
        if ((localDatum instanceof ARRAY)) {
          return (ARRAY)localDatum;
        }

        SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4, "getARRAY");
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }

      return null;
    }
  }

  public STRUCT getSTRUCT(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      Datum localDatum = getOracleObject(paramInt);

      if (localDatum != null)
      {
        if ((localDatum instanceof STRUCT)) {
          return (STRUCT)localDatum;
        }

        SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4, "getSTRUCT");
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }

      return null;
    }
  }

  public OPAQUE getOPAQUE(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      Datum localDatum = getOracleObject(paramInt);

      if (localDatum != null)
      {
        if ((localDatum instanceof OPAQUE)) {
          return (OPAQUE)localDatum;
        }

        SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4, "getOPAQUE");
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }

      return null;
    }
  }

  public REF getREF(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      Datum localDatum = getOracleObject(paramInt);

      if (localDatum != null)
      {
        if ((localDatum instanceof REF)) {
          return (REF)localDatum;
        }

        SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4, "getREF");
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }

      return null;
    }
  }

  public CHAR getCHAR(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      Datum localDatum = getOracleObject(paramInt);

      if (localDatum != null)
      {
        if ((localDatum instanceof CHAR)) {
          return (CHAR)localDatum;
        }

        SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4, "getCHAR");
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }

      return null;
    }
  }

  public RAW getRAW(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      Datum localDatum = getOracleObject(paramInt);

      if (localDatum != null)
      {
        if ((localDatum instanceof RAW)) {
          return (RAW)localDatum;
        }

        SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4, "getRAW");
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }

      return null;
    }
  }

  public BLOB getBLOB(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      Datum localDatum = getOracleObject(paramInt);

      if (localDatum != null)
      {
        if ((localDatum instanceof BLOB)) {
          return (BLOB)localDatum;
        }

        SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4, "getBLOB");
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }

      return null;
    }
  }

  public CLOB getCLOB(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      Datum localDatum = getOracleObject(paramInt);

      if (localDatum != null)
      {
        if ((localDatum instanceof CLOB)) {
          return (CLOB)localDatum;
        }

        SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4, "getCLOB");
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }

      return null;
    }
  }

  public NCLOB getNCLOB(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      Datum localDatum = getOracleObject(paramInt);

      if (localDatum != null)
      {
        if ((localDatum instanceof NCLOB)) {
          return (NCLOB)localDatum;
        }

        SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4, "getCLOB");
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }

      return null;
    }
  }

  public BFILE getBFILE(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      Datum localDatum = getOracleObject(paramInt);

      if (localDatum != null)
      {
        if ((localDatum instanceof BFILE)) {
          return (BFILE)localDatum;
        }

        SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4, "getBFILE");
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }

      return null;
    }
  }

  public BFILE getBfile(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      return getBFILE(paramInt);
    }
  }

  public CustomDatum getCustomDatum(int paramInt, CustomDatumFactory paramCustomDatumFactory)
    throws SQLException
  {
    synchronized (this.connection)
    {
      Datum localDatum = getOracleObject(paramInt);

      return paramCustomDatumFactory.create(localDatum, 0);
    }
  }

  public ORAData getORAData(int paramInt, ORADataFactory paramORADataFactory)
    throws SQLException
  {
    synchronized (this.connection)
    {
      Datum localDatum = getOracleObject(paramInt);

      return paramORADataFactory.create(localDatum, 0);
    }
  }

  public NClob getNClob(int paramInt)
    throws SQLException
  {
    NCLOB localNCLOB = getNCLOB(paramInt);

    if (localNCLOB == null) return null;

    if (!(localNCLOB instanceof NClob))
    {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 184);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    return localNCLOB;
  }

  public String getNString(int paramInt)
    throws SQLException
  {
    return getString(paramInt);
  }

  public Reader getNCharacterStream(int paramInt)
    throws SQLException
  {
    return getCharacterStream(paramInt);
  }

  public RowId getRowId(int paramInt)
    throws SQLException
  {
    return getROWID(paramInt);
  }

  public SQLXML getSQLXML(int paramInt)
    throws SQLException
  {
    Datum localDatum = getOracleObject(paramInt);

    if (localDatum != null)
    {
      if ((localDatum instanceof SQLXML))
        return (SQLXML)localDatum;
      if ((localDatum instanceof OPAQUE)) {
        return new OracleSQLXML(this.connection, (OPAQUE)localDatum);
      }

      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4, "getSQLXML");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    return null;
  }

  public ResultSetMetaData getMetaData()
    throws SQLException
  {
    synchronized (this.connection)
    {
      return new OracleResultSetMetaData(this.connection, (OracleStatement)this.scrollStmt, this.beginColumnIndex);
    }
  }

  public int findColumn(String paramString)
    throws SQLException
  {
    synchronized (this.connection)
    {
      if (this.closed)
      {
        SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 10);
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }
      return this.resultSet.findColumn(paramString) - this.beginColumnIndex;
    }
  }

  public void setFetchDirection(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      if (paramInt == 1000)
      {
        this.usrFetchDirection = paramInt;
      }
      else if ((paramInt == 1001) || (paramInt == 1002))
      {
        this.usrFetchDirection = paramInt;

        this.sqlWarning = DatabaseError.addSqlWarning(this.sqlWarning, 87);
      }
      else
      {
        SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68, "setFetchDirection");
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }
    }
  }

  public int getFetchDirection()
    throws SQLException
  {
    return 1000;
  }

  public void setFetchSize(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      this.resultSet.setFetchSize(paramInt);
    }
  }

  public int getFetchSize()
    throws SQLException
  {
    synchronized (this.connection)
    {
      return this.resultSet.getFetchSize();
    }
  }

  public int getType()
    throws SQLException
  {
    return this.rsetType;
  }

  public int getConcurrency()
    throws SQLException
  {
    return this.rsetConcurency;
  }

  public void refreshRow()
    throws SQLException
  {
    if (!needIdentifier(this.rsetType, this.rsetConcurency))
    {
      SQLException sqlexception1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 23, "refreshRow");
      sqlexception1.fillInStackTrace();
      throw sqlexception1;
    }

    if (isValidRow(this.currentRow))
    {
      int i = getFetchDirection();
      try
      {
        refreshRowsInCache(this.currentRow, getFetchSize(), i);
      }
      catch (SQLException sqlexception3)
      {
        SQLException sqlexception4 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), sqlexception3, 90, "Unsupported syntax for refreshRow()");
        sqlexception4.fillInStackTrace();
        throw sqlexception4;
      }

    }
    else
    {
      SQLException sqlexception2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 82, "refreshRow");
      sqlexception2.fillInStackTrace();
      throw sqlexception2;
    }
  }

  private boolean isEmptyResultSet()
    throws SQLException
  {
    if (this.numRowsCached != 0)
    {
      return false;
    }
    if ((this.numRowsCached == 0) && (this.allRowsCached))
    {
      return true;
    }

    return !isValidRow(1);
  }

  boolean isValidRow(int paramInt)
    throws SQLException
  {
    if ((paramInt > 0) && (paramInt <= this.numRowsCached))
    {
      return true;
    }
    if (paramInt <= 0)
    {
      return false;
    }

    return cacheRowAt(paramInt);
  }

  private void cacheCurrentRow(OracleResultSetImpl paramOracleResultSetImpl, int paramInt)
    throws SQLException
  {
    for (int i = 0; i < getColumnCount(); i++)
    {
      byte[] arrayOfByte = paramOracleResultSetImpl.privateGetBytes(i + 1);
      OracleResultSet.AuthorizationIndicator localAuthorizationIndicator = paramOracleResultSetImpl.getAuthorizationIndicator(i + 1);

      CachedRowElement localCachedRowElement = new CachedRowElement(paramInt, i + 1, localAuthorizationIndicator, arrayOfByte);

      int j = paramOracleResultSetImpl.statement.accessors[i].internalType;
      Object localObject;
      if (j == 112)
      {
        localObject = paramOracleResultSetImpl.getCLOB(i + 1);
        localCachedRowElement.setData((Datum)localObject);
      }
      else if (j == 113)
      {
        localObject = paramOracleResultSetImpl.getBLOB(i + 1);
        localCachedRowElement.setData((Datum)localObject);
      }

      putCachedValueAt(paramInt, i + 1, localCachedRowElement);
    }
  }

  private boolean cacheRowAt(int paramInt)
    throws SQLException
  {
    while ((this.numRowsCached < paramInt) && (this.resultSet.next())) {
      cacheCurrentRow(this.resultSet, ++this.numRowsCached);
    }
    if (this.numRowsCached < paramInt)
    {
      this.allRowsCached = true;

      return false;
    }

    return true;
  }

  private int cacheAllRows()
    throws SQLException
  {
    while (this.resultSet.next()) {
      cacheCurrentRow(this.resultSet, ++this.numRowsCached);
    }
    this.allRowsCached = true;

    return this.numRowsCached;
  }

  int getColumnCount()
    throws SQLException
  {
    if (this.columnCount == 0)
    {
      int i = this.resultSet.statement.numberOfDefinePositions;

      if ((this.resultSet.statement.accessors != null) && (i > 0))
        this.columnCount = i;
      else {
        this.columnCount = getInternalMetadata().getColumnCount();
      }
    }
    return this.columnCount;
  }

  private ResultSetMetaData getInternalMetadata()
    throws SQLException
  {
    if (this.metadata == null) {
      this.metadata = this.resultSet.getMetaData();
    }
    return this.metadata;
  }

  private int getLastRow()
    throws SQLException
  {
    if (!this.allRowsCached) {
      cacheAllRows();
    }
    return this.numRowsCached;
  }

  private int get_refetch_size(int paramInt1, int paramInt2, int paramInt3)
    throws SQLException
  {
    int i = paramInt3 == 1001 ? -1 : 1;

    int j = 0;

    if (this.refetchRowids == null)
      this.refetchRowids = new Vector(10);
    else {
      this.refetchRowids.removeAllElements();
    }

    while ((j < paramInt2) && (isValidRow(paramInt1 + j * i)))
    {
      this.refetchRowids.addElement(getCachedDatumValueAt(paramInt1 + j * i, 1));

      j++;
    }

    return j;
  }

  private OraclePreparedStatement prepare_refetch_statement(int paramInt)
    throws SQLException
  {
    if (paramInt < 1)
    {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject = this.connection.prepareStatement(((OracleStatement)this.scrollStmt).sqlObject.getRefetchSqlForScrollableResultSet(this, paramInt));

    return (OraclePreparedStatement)((OraclePreparedStatementWrapper)localObject).preparedStatement;
  }

  private void prepare_refetch_binds(OraclePreparedStatement paramOraclePreparedStatement, int paramInt)
    throws SQLException
  {
    int i = this.scrollStmt.copyBinds(paramOraclePreparedStatement, 0);

    for (int j = 0; j < paramInt; j++)
    {
      paramOraclePreparedStatement.setROWID(i + j + 1, (ROWID)this.refetchRowids.elementAt(j));
    }
  }

  private void save_refetch_results(OracleResultSetImpl paramOracleResultSetImpl, int paramInt1, int paramInt2, int paramInt3)
    throws SQLException
  {
    int i = paramInt3 == 1001 ? -1 : 1;

    while (paramOracleResultSetImpl.next())
    {
      ROWID localROWID = paramOracleResultSetImpl.getROWID(1);

      int j = 0;
      int k = paramInt1;

      while ((j == 0) && (k < paramInt1 + paramInt2 * i))
      {
        if (((ROWID)getCachedDatumValueAt(k, 1)).stringValue(this.connection).equals(localROWID.stringValue(this.connection)))
        {
          j = 1;
        }
        else k += i;
      }

      if (j != 0)
        cacheCurrentRow(paramOracleResultSetImpl, k);
    }
  }

  private Datum getCachedDatumValueAt(int paramInt1, int paramInt2)
    throws SQLException
  {
    CachedRowElement localCachedRowElement = null;
    byte[] localObject;
    try
    {
      localCachedRowElement = (CachedRowElement)this.rsetCache.get(paramInt1, paramInt2);
    }
    catch (IOException localIOException)
    {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localIOException);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Datum localDatum = null;

    if (localCachedRowElement != null)
    {
      localDatum = localCachedRowElement.getDataAsDatum();
      localObject = localCachedRowElement.getData();

      if ((localDatum == null) && (localObject != null) && (localObject.length > 0))
      {
        int i = getInternalMetadata().getColumnType(paramInt2);
        int j = getInternalMetadata().getColumnDisplaySize(paramInt2);

        int k = ((OracleResultSetMetaData)getInternalMetadata()).getDescription()[(paramInt2 - 1)].internalType;

        int m = this.scrollStmt.getMaxFieldSize();

        if ((m > 0) && (m < j)) {
          j = m;
        }
        String str = null;

        if ((i == 2006) || (i == 2002) || (i == 2008) || (i == 2007) || (i == 2003) || (i == 2009))
        {
          str = getInternalMetadata().getColumnTypeName(paramInt2);
        }

        short s = this.resultSet.statement.accessors[(paramInt2 - 1)].formOfUse;

        if ((s == 2) && ((k == 96) || (k == 1) || (k == 8) || (k == 112)))
        {
          localDatum = SQLUtil.makeNDatum(this.connection, (byte[])localObject, k, str, s, j);
        }
        else
        {
          localDatum = SQLUtil.makeDatum(this.connection, (byte[])localObject, k, str, j);
        }

        localCachedRowElement.setData(localDatum);
      }
    }

    return localDatum;
  }

  private void putCachedValueAt(int paramInt1, int paramInt2, CachedRowElement paramCachedRowElement)
    throws SQLException
  {
    try
    {
      this.rsetCache.put(paramInt1, paramInt2, paramCachedRowElement);
    }
    catch (IOException localIOException)
    {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localIOException);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }
  }

  private void removeCachedRowAt(int paramInt)
    throws SQLException
  {
    try
    {
      this.rsetCache.remove(paramInt);
    }
    catch (IOException localIOException)
    {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localIOException);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }
  }

  public static boolean needIdentifier(int paramInt1, int paramInt2)
  {
    if (((paramInt1 == 1003) && (paramInt2 == 1007)) || ((paramInt1 == 1004) && (paramInt2 == 1007)))
    {
      return false;
    }
    return true;
  }

  public static boolean needCache(int paramInt1, int paramInt2)
  {
    if ((paramInt1 == 1003) || ((paramInt1 == 1004) && (paramInt2 == 1007)))
    {
      return false;
    }
    return true;
  }

  public static boolean supportRefreshRow(int paramInt1, int paramInt2)
  {
    if ((paramInt1 == 1003) || ((paramInt1 == 1004) && (paramInt2 == 1007)))
    {
      return false;
    }
    return true;
  }

  int getFirstUserColumnIndex()
  {
    return this.beginColumnIndex;
  }

  public String getCursorName()
    throws SQLException
  {
    synchronized (this.connection)
    {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 23, "getCursorName");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }
  }

  protected OracleConnection getConnectionDuringExceptionHandling()
  {
    return this.connection;
  }

  OracleStatement getOracleStatement()
    throws SQLException
  {
    return this.resultSet == null ? null : this.resultSet.getOracleStatement();
  }

  static
  {
    ClassRef localClassRef = null;
    try {
      localClassRef = ClassRef.newInstance("oracle.xdb.XMLType");
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
    }
    XMLTYPE_CLASS = localClassRef;
  }
}