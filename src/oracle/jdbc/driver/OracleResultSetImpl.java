package oracle.jdbc.driver;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
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

class OracleResultSetImpl extends BaseResultSet
{
  PhysicalConnection connection;
  OracleStatement statement;
  boolean explicitly_closed;
  boolean m_emptyRset;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  OracleResultSetImpl(PhysicalConnection paramPhysicalConnection, OracleStatement paramOracleStatement)
    throws SQLException
  {
    this.connection = paramPhysicalConnection;
    this.statement = paramOracleStatement;
    this.close_statement_on_close = false;
    this.explicitly_closed = false;
    this.m_emptyRset = false;
  }

  public String getCursorName()
    throws SQLException
  {
    synchronized (this.connection)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 23, "getCursorName");
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  public void close()
    throws SQLException
  {
    synchronized (this.connection)
    {
      internal_close(false);
      this.statement.totalRowsVisited = 0;

      if (this.close_statement_on_close)
      {
        try
        {
          this.statement.close();
        }
        catch (SQLException localSQLException)
        {
        }

      }

      this.explicitly_closed = true;
    }
  }

  public boolean wasNull()
    throws SQLException
  {
    synchronized (this.connection)
    {
      return this.statement.wasNullValue();
    }
  }

  public ResultSetMetaData getMetaData()
    throws SQLException
  {
    synchronized (this.connection)
    {
      SQLException localSQLException;
      if (this.explicitly_closed)
      {
        localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 10, "getMetaData");
        localSQLException.fillInStackTrace();
        throw localSQLException;
      }

      if (this.statement.closed)
      {
        localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9, "getMetaData");
        localSQLException.fillInStackTrace();
        throw localSQLException;
      }

      if (!this.statement.isOpen)
      {
        localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 144, "getMetaData");
        localSQLException.fillInStackTrace();
        throw localSQLException;
      }

      return new OracleResultSetMetaData(this.connection, this.statement);
    }
  }

  public Statement getStatement()
    throws SQLException
  {
    synchronized (this.connection)
    {
      return this.statement.wrapper == null ? this.statement : this.statement.wrapper;
    }
  }

  OracleStatement getOracleStatement()
    throws SQLException
  {
    synchronized (this.connection)
    {
      return this.statement;
    }
  }

  public boolean next()
    throws SQLException
  {
    synchronized (this.connection)
    {
      boolean bool = true;

      PhysicalConnection localPhysicalConnection = this.statement.connection;
      SQLException localSQLException;
      if (this.explicitly_closed)
      {
        localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 10, "next");
        localSQLException.fillInStackTrace();
        throw localSQLException;
      }

      if ((localPhysicalConnection == null) || (localPhysicalConnection.lifecycle != 1))
      {
        localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 8, "next");
        localSQLException.fillInStackTrace();
        throw localSQLException;
      }

      if (this.statement.closed)
      {
        localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9, "next");
        localSQLException.fillInStackTrace();
        throw localSQLException;
      }

      if ((this.statement.sqlKind == 32) || (this.statement.sqlKind == 64))
      {
        localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 166, "next");
        localSQLException.fillInStackTrace();
        throw localSQLException;
      }

      if (this.closed) {
        return false;
      }
      this.statement.currentRow += 1;
      this.statement.totalRowsVisited += 1;

      if ((this.statement.maxRows != 0) && (this.statement.totalRowsVisited > this.statement.maxRows))
      {
        internal_close(false);
        this.statement.currentRow -= 1;
        this.statement.totalRowsVisited -= 1;

        return false;
      }

      if (this.statement.currentRow >= this.statement.validRows) {
        bool = close_or_fetch_from_next(false);
      }
      if ((bool) && (localPhysicalConnection.useFetchSizeWithLongColumn)) {
        this.statement.reopenStreams();
      }
      if (!bool)
      {
        this.statement.currentRow -= 1;
        this.statement.totalRowsVisited -= 1;
      }

      return bool;
    }
  }

  private boolean close_or_fetch_from_next(boolean paramBoolean)
    throws SQLException
  {
    if (paramBoolean)
    {
      internal_close(false);

      return false;
    }

    if (this.statement.gotLastBatch)
    {
      internal_close(false);

      return false;
    }

    this.statement.check_row_prefetch_changed();

    PhysicalConnection localPhysicalConnection = this.statement.connection;

    if (localPhysicalConnection.protocolId == 3) {
      this.sqlWarning = null;
    }
    else
    {
      if (this.statement.streamList != null)
      {
        while (this.statement.nextStream != null)
        {
          try
          {
            this.statement.nextStream.close();
          }
          catch (IOException localIOException)
          {
            SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localIOException);
            localSQLException.fillInStackTrace();
            throw localSQLException;
          }

          this.statement.nextStream = this.statement.nextStream.nextStream;
        }
      }

      clearWarnings();

      localPhysicalConnection.registerHeartbeat();

      localPhysicalConnection.needLine();
    }

    synchronized (localPhysicalConnection)
    {
      try
      {
        this.statement.isExecuting = true;
        this.statement.fetch();
      }
      finally {
        this.statement.isExecuting = false;
      }
    }

    if (this.statement.validRows == 0)
    {
      internal_close(false);

      return false;
    }

    this.statement.currentRow = 0;

    this.statement.checkValidRowsStatus();

    return true;
  }

  public boolean isBeforeFirst()
    throws SQLException
  {
    SQLException localSQLException;
    if (this.explicitly_closed)
    {
      localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 10);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if ((this.statement != null) && (this.statement.connection.protocolId != 3) && (!this.statement.closed) && (this.statement.serverCursor))
    {
      if (this.statement.validRows < 1)
        close_or_fetch_from_next(false);
      if (this.statement.validRows > 0)
        this.statement.currentRow = -1;
      else {
        this.m_emptyRset = true;
      }
    }

    if ((this.statement.connection.protocolId == 3) && (this.statement.serverCursor))
    {
      localSQLException = DatabaseError.createUnsupportedFeatureSqlException();
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    return (!isEmptyResultSet()) && (this.statement.currentRow == -1) && (!this.closed);
  }

  public boolean isAfterLast()
    throws SQLException
  {
    if (this.explicitly_closed)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 10);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    return (!isEmptyResultSet()) && (this.closed);
  }

  public boolean isFirst()
    throws SQLException
  {
    if (this.explicitly_closed)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 10);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
    return getRow() == 1;
  }

  public boolean isLast()
    throws SQLException
  {
    if (this.explicitly_closed)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 10);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 75, "isLast");
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public int getRow()
    throws SQLException
  {
    if (this.explicitly_closed)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 10);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
    return this.statement.totalRowsVisited;
  }

  public Array getArray(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      return getARRAY(paramInt);
    }
  }

  public BigDecimal getBigDecimal(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      SQLException localSQLException1;
      if (this.closed)
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 11);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      if ((paramInt <= 0) || (paramInt > this.statement.numberOfDefinePositions))
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      int i = this.statement.currentRow;

      if (i < 0)
      {
        SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 14);
        localSQLException2.fillInStackTrace();
        throw localSQLException2;
      }

      this.statement.lastIndex = paramInt;

      if (this.statement.streamList != null)
      {
        this.statement.closeUsedStreams(paramInt);
      }

      return this.statement.accessors[(paramInt - 1)].getBigDecimal(i);
    }
  }

  public BigDecimal getBigDecimal(int paramInt1, int paramInt2)
    throws SQLException
  {
    synchronized (this.connection)
    {
      SQLException localSQLException1;
      if (this.closed)
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 11);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      if ((paramInt1 <= 0) || (paramInt1 > this.statement.numberOfDefinePositions))
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      int i = this.statement.currentRow;

      if (i < 0)
      {
        SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 14);
        localSQLException2.fillInStackTrace();
        throw localSQLException2;
      }

      this.statement.lastIndex = paramInt1;

      if (this.statement.streamList != null)
      {
        this.statement.closeUsedStreams(paramInt1);
      }

      return this.statement.accessors[(paramInt1 - 1)].getBigDecimal(i, paramInt2);
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

  public boolean getBoolean(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      SQLException localSQLException1;
      if (this.closed)
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 11);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      if ((paramInt <= 0) || (paramInt > this.statement.numberOfDefinePositions))
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      int i = this.statement.currentRow;

      if (i < 0)
      {
        SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 14);
        localSQLException2.fillInStackTrace();
        throw localSQLException2;
      }

      this.statement.lastIndex = paramInt;

      if (this.statement.streamList != null)
      {
        this.statement.closeUsedStreams(paramInt);
      }

      return this.statement.accessors[(paramInt - 1)].getBoolean(i);
    }
  }

  public byte getByte(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      SQLException localSQLException1;
      if (this.closed)
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 11);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      if ((paramInt <= 0) || (paramInt > this.statement.numberOfDefinePositions))
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      int i = this.statement.currentRow;

      if (i < 0)
      {
        SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 14);
        localSQLException2.fillInStackTrace();
        throw localSQLException2;
      }

      this.statement.lastIndex = paramInt;

      if (this.statement.streamList != null)
      {
        this.statement.closeUsedStreams(paramInt);
      }

      return this.statement.accessors[(paramInt - 1)].getByte(i);
    }
  }

  public byte[] getBytes(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      SQLException localSQLException1;
      if (this.closed)
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 11);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      if ((paramInt <= 0) || (paramInt > this.statement.numberOfDefinePositions))
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      int i = this.statement.currentRow;

      if (i < 0)
      {
        SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 14);
        localSQLException2.fillInStackTrace();
        throw localSQLException2;
      }

      this.statement.lastIndex = paramInt;

      if (this.statement.streamList != null)
      {
        this.statement.closeUsedStreams(paramInt);
      }

      return this.statement.accessors[(paramInt - 1)].getBytes(i);
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

  public Date getDate(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      SQLException localSQLException1;
      if (this.closed)
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 11);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      if ((paramInt <= 0) || (paramInt > this.statement.numberOfDefinePositions))
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      int i = this.statement.currentRow;

      if (i < 0)
      {
        SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 14);
        localSQLException2.fillInStackTrace();
        throw localSQLException2;
      }

      this.statement.lastIndex = paramInt;

      if (this.statement.streamList != null)
      {
        this.statement.closeUsedStreams(paramInt);
      }

      return this.statement.accessors[(paramInt - 1)].getDate(i);
    }
  }

  public Date getDate(int paramInt, Calendar paramCalendar)
    throws SQLException
  {
    synchronized (this.connection)
    {
      SQLException localSQLException1;
      if (this.closed)
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 11);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      if ((paramInt <= 0) || (paramInt > this.statement.numberOfDefinePositions))
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      int i = this.statement.currentRow;

      if (i < 0)
      {
        SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 14);
        localSQLException2.fillInStackTrace();
        throw localSQLException2;
      }

      this.statement.lastIndex = paramInt;

      if (this.statement.streamList != null)
      {
        this.statement.closeUsedStreams(paramInt);
      }

      return this.statement.accessors[(paramInt - 1)].getDate(i, paramCalendar);
    }
  }

  public double getDouble(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      SQLException localSQLException1;
      if (this.closed)
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 11);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      if ((paramInt <= 0) || (paramInt > this.statement.numberOfDefinePositions))
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      int i = this.statement.currentRow;

      if (i < 0)
      {
        SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 14);
        localSQLException2.fillInStackTrace();
        throw localSQLException2;
      }

      this.statement.lastIndex = paramInt;

      if (this.statement.streamList != null)
      {
        this.statement.closeUsedStreams(paramInt);
      }

      return this.statement.accessors[(paramInt - 1)].getDouble(i);
    }
  }

  public float getFloat(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      SQLException localSQLException1;
      if (this.closed)
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 11);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      if ((paramInt <= 0) || (paramInt > this.statement.numberOfDefinePositions))
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      int i = this.statement.currentRow;

      if (i < 0)
      {
        SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 14);
        localSQLException2.fillInStackTrace();
        throw localSQLException2;
      }

      this.statement.lastIndex = paramInt;

      if (this.statement.streamList != null)
      {
        this.statement.closeUsedStreams(paramInt);
      }

      return this.statement.accessors[(paramInt - 1)].getFloat(i);
    }
  }

  public int getInt(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      SQLException localSQLException1;
      if (this.closed)
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 11);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      if ((paramInt <= 0) || (paramInt > this.statement.numberOfDefinePositions))
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      int i = this.statement.currentRow;

      if (i < 0)
      {
        SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 14);
        localSQLException2.fillInStackTrace();
        throw localSQLException2;
      }

      this.statement.lastIndex = paramInt;

      if (this.statement.streamList != null)
      {
        this.statement.closeUsedStreams(paramInt);
      }

      return this.statement.accessors[(paramInt - 1)].getInt(i);
    }
  }

  public long getLong(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      SQLException localSQLException1;
      if (this.closed)
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 11);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      if ((paramInt <= 0) || (paramInt > this.statement.numberOfDefinePositions))
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      int i = this.statement.currentRow;

      if (i < 0)
      {
        SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 14);
        localSQLException2.fillInStackTrace();
        throw localSQLException2;
      }

      this.statement.lastIndex = paramInt;

      if (this.statement.streamList != null)
      {
        this.statement.closeUsedStreams(paramInt);
      }

      return this.statement.accessors[(paramInt - 1)].getLong(i);
    }
  }

  public NClob getNClob(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      SQLException localSQLException1;
      if (this.closed)
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 11);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      if ((paramInt <= 0) || (paramInt > this.statement.numberOfDefinePositions))
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      int i = this.statement.currentRow;

      if (i < 0)
      {
        SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 14);
        localSQLException2.fillInStackTrace();
        throw localSQLException2;
      }

      this.statement.lastIndex = paramInt;

      if (this.statement.streamList != null)
      {
        this.statement.closeUsedStreams(paramInt);
      }

      return this.statement.accessors[(paramInt - 1)].getNClob(i);
    }
  }

  public String getNString(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      SQLException localSQLException1;
      if (this.closed)
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 11);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      if ((paramInt <= 0) || (paramInt > this.statement.numberOfDefinePositions))
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      int i = this.statement.currentRow;

      if (i < 0)
      {
        SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 14);
        localSQLException2.fillInStackTrace();
        throw localSQLException2;
      }

      this.statement.lastIndex = paramInt;

      if (this.statement.streamList != null)
      {
        this.statement.closeUsedStreams(paramInt);
      }

      return this.statement.accessors[(paramInt - 1)].getNString(i);
    }
  }

  public Object getObject(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      SQLException localSQLException1;
      if (this.closed)
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 11);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      if ((paramInt <= 0) || (paramInt > this.statement.numberOfDefinePositions))
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      int i = this.statement.currentRow;

      if (i < 0)
      {
        SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 14);
        localSQLException2.fillInStackTrace();
        throw localSQLException2;
      }

      this.statement.lastIndex = paramInt;

      if (this.statement.streamList != null)
      {
        this.statement.closeUsedStreams(paramInt);
      }

      return this.statement.accessors[(paramInt - 1)].getObject(i);
    }
  }

  public Object getObject(int paramInt, Map paramMap)
    throws SQLException
  {
    synchronized (this.connection)
    {
      SQLException localSQLException1;
      if (this.closed)
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 11);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      if ((paramInt <= 0) || (paramInt > this.statement.numberOfDefinePositions))
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      int i = this.statement.currentRow;

      if (i < 0)
      {
        SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 14);
        localSQLException2.fillInStackTrace();
        throw localSQLException2;
      }

      this.statement.lastIndex = paramInt;

      if (this.statement.streamList != null)
      {
        this.statement.closeUsedStreams(paramInt);
      }

      return this.statement.accessors[(paramInt - 1)].getObject(i, paramMap);
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

  public RowId getRowId(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      return getROWID(paramInt);
    }
  }

  public short getShort(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      SQLException localSQLException1;
      if (this.closed)
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 11);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      if ((paramInt <= 0) || (paramInt > this.statement.numberOfDefinePositions))
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      int i = this.statement.currentRow;

      if (i < 0)
      {
        SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 14);
        localSQLException2.fillInStackTrace();
        throw localSQLException2;
      }

      this.statement.lastIndex = paramInt;

      if (this.statement.streamList != null)
      {
        this.statement.closeUsedStreams(paramInt);
      }

      return this.statement.accessors[(paramInt - 1)].getShort(i);
    }
  }

  public SQLXML getSQLXML(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      SQLException localSQLException1;
      if (this.closed)
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 11);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      if ((paramInt <= 0) || (paramInt > this.statement.numberOfDefinePositions))
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      int i = this.statement.currentRow;

      if (i < 0)
      {
        SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 14);
        localSQLException2.fillInStackTrace();
        throw localSQLException2;
      }

      this.statement.lastIndex = paramInt;

      if (this.statement.streamList != null)
      {
        this.statement.closeUsedStreams(paramInt);
      }

      return this.statement.accessors[(paramInt - 1)].getSQLXML(i);
    }
  }

  public String getString(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      SQLException localSQLException1;
      if (this.closed)
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 11);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      if ((paramInt <= 0) || (paramInt > this.statement.numberOfDefinePositions))
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      int i = this.statement.currentRow;

      if (i < 0)
      {
        SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 14);
        localSQLException2.fillInStackTrace();
        throw localSQLException2;
      }

      this.statement.lastIndex = paramInt;

      if (this.statement.streamList != null)
      {
        this.statement.closeUsedStreams(paramInt);
      }

      return this.statement.accessors[(paramInt - 1)].getString(i);
    }
  }

  public Time getTime(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      SQLException localSQLException1;
      if (this.closed)
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 11);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      if ((paramInt <= 0) || (paramInt > this.statement.numberOfDefinePositions))
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      int i = this.statement.currentRow;

      if (i < 0)
      {
        SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 14);
        localSQLException2.fillInStackTrace();
        throw localSQLException2;
      }

      this.statement.lastIndex = paramInt;

      if (this.statement.streamList != null)
      {
        this.statement.closeUsedStreams(paramInt);
      }

      return this.statement.accessors[(paramInt - 1)].getTime(i);
    }
  }

  public Time getTime(int paramInt, Calendar paramCalendar)
    throws SQLException
  {
    synchronized (this.connection)
    {
      SQLException localSQLException1;
      if (this.closed)
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 11);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      if ((paramInt <= 0) || (paramInt > this.statement.numberOfDefinePositions))
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      int i = this.statement.currentRow;

      if (i < 0)
      {
        SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 14);
        localSQLException2.fillInStackTrace();
        throw localSQLException2;
      }

      this.statement.lastIndex = paramInt;

      if (this.statement.streamList != null)
      {
        this.statement.closeUsedStreams(paramInt);
      }

      return this.statement.accessors[(paramInt - 1)].getTime(i, paramCalendar);
    }
  }

  public Timestamp getTimestamp(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      SQLException localSQLException1;
      if (this.closed)
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 11);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      if ((paramInt <= 0) || (paramInt > this.statement.numberOfDefinePositions))
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      int i = this.statement.currentRow;

      if (i < 0)
      {
        SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 14);
        localSQLException2.fillInStackTrace();
        throw localSQLException2;
      }

      this.statement.lastIndex = paramInt;

      if (this.statement.streamList != null)
      {
        this.statement.closeUsedStreams(paramInt);
      }

      return this.statement.accessors[(paramInt - 1)].getTimestamp(i);
    }
  }

  public Timestamp getTimestamp(int paramInt, Calendar paramCalendar)
    throws SQLException
  {
    synchronized (this.connection)
    {
      SQLException localSQLException1;
      if (this.closed)
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 11);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      if ((paramInt <= 0) || (paramInt > this.statement.numberOfDefinePositions))
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      int i = this.statement.currentRow;

      if (i < 0)
      {
        SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 14);
        localSQLException2.fillInStackTrace();
        throw localSQLException2;
      }

      this.statement.lastIndex = paramInt;

      if (this.statement.streamList != null)
      {
        this.statement.closeUsedStreams(paramInt);
      }

      return this.statement.accessors[(paramInt - 1)].getTimestamp(i, paramCalendar);
    }
  }

  public URL getURL(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      SQLException localSQLException1;
      if (this.closed)
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 11);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      if ((paramInt <= 0) || (paramInt > this.statement.numberOfDefinePositions))
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      int i = this.statement.currentRow;

      if (i < 0)
      {
        SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 14);
        localSQLException2.fillInStackTrace();
        throw localSQLException2;
      }

      this.statement.lastIndex = paramInt;

      if (this.statement.streamList != null)
      {
        this.statement.closeUsedStreams(paramInt);
      }

      return this.statement.accessors[(paramInt - 1)].getURL(i);
    }
  }

  public ARRAY getARRAY(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      SQLException localSQLException1;
      if (this.closed)
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 11);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      if ((paramInt <= 0) || (paramInt > this.statement.numberOfDefinePositions))
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      int i = this.statement.currentRow;

      if (i < 0)
      {
        SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 14);
        localSQLException2.fillInStackTrace();
        throw localSQLException2;
      }

      this.statement.lastIndex = paramInt;

      if (this.statement.streamList != null)
      {
        this.statement.closeUsedStreams(paramInt);
      }

      return this.statement.accessors[(paramInt - 1)].getARRAY(i);
    }
  }

  public BFILE getBFILE(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      SQLException localSQLException1;
      if (this.closed)
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 11);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      if ((paramInt <= 0) || (paramInt > this.statement.numberOfDefinePositions))
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      int i = this.statement.currentRow;

      if (i < 0)
      {
        SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 14);
        localSQLException2.fillInStackTrace();
        throw localSQLException2;
      }

      this.statement.lastIndex = paramInt;

      if (this.statement.streamList != null)
      {
        this.statement.closeUsedStreams(paramInt);
      }

      return this.statement.accessors[(paramInt - 1)].getBFILE(i);
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

  public BLOB getBLOB(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      SQLException localSQLException1;
      if (this.closed)
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 11);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      if ((paramInt <= 0) || (paramInt > this.statement.numberOfDefinePositions))
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      int i = this.statement.currentRow;

      if (i < 0)
      {
        SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 14);
        localSQLException2.fillInStackTrace();
        throw localSQLException2;
      }

      this.statement.lastIndex = paramInt;

      if (this.statement.streamList != null)
      {
        this.statement.closeUsedStreams(paramInt);
      }

      return this.statement.accessors[(paramInt - 1)].getBLOB(i);
    }
  }

  public CHAR getCHAR(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      SQLException localSQLException1;
      if (this.closed)
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 11);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      if ((paramInt <= 0) || (paramInt > this.statement.numberOfDefinePositions))
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      int i = this.statement.currentRow;

      if (i < 0)
      {
        SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 14);
        localSQLException2.fillInStackTrace();
        throw localSQLException2;
      }

      this.statement.lastIndex = paramInt;

      if (this.statement.streamList != null)
      {
        this.statement.closeUsedStreams(paramInt);
      }

      return this.statement.accessors[(paramInt - 1)].getCHAR(i);
    }
  }

  public CLOB getCLOB(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      SQLException localSQLException1;
      if (this.closed)
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 11);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      if ((paramInt <= 0) || (paramInt > this.statement.numberOfDefinePositions))
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      int i = this.statement.currentRow;

      if (i < 0)
      {
        SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 14);
        localSQLException2.fillInStackTrace();
        throw localSQLException2;
      }

      this.statement.lastIndex = paramInt;

      if (this.statement.streamList != null)
      {
        this.statement.closeUsedStreams(paramInt);
      }

      return this.statement.accessors[(paramInt - 1)].getCLOB(i);
    }
  }

  public ResultSet getCursor(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      SQLException localSQLException1;
      if (this.closed)
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 11);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      if ((paramInt <= 0) || (paramInt > this.statement.numberOfDefinePositions))
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      int i = this.statement.currentRow;

      if (i < 0)
      {
        SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 14);
        localSQLException2.fillInStackTrace();
        throw localSQLException2;
      }

      this.statement.lastIndex = paramInt;

      if (this.statement.streamList != null)
      {
        this.statement.closeUsedStreams(paramInt);
      }

      return this.statement.accessors[(paramInt - 1)].getCursor(i);
    }
  }

  public CustomDatum getCustomDatum(int paramInt, CustomDatumFactory paramCustomDatumFactory)
    throws SQLException
  {
    synchronized (this.connection)
    {
      SQLException localSQLException1;
      if (this.closed)
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 11);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      if ((paramInt <= 0) || (paramInt > this.statement.numberOfDefinePositions))
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      int i = this.statement.currentRow;

      if (i < 0)
      {
        SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 14);
        localSQLException2.fillInStackTrace();
        throw localSQLException2;
      }

      this.statement.lastIndex = paramInt;

      if (this.statement.streamList != null)
      {
        this.statement.closeUsedStreams(paramInt);
      }

      return this.statement.accessors[(paramInt - 1)].getCustomDatum(i, paramCustomDatumFactory);
    }
  }

  public DATE getDATE(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      SQLException localSQLException1;
      if (this.closed)
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 11);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      if ((paramInt <= 0) || (paramInt > this.statement.numberOfDefinePositions))
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      int i = this.statement.currentRow;

      if (i < 0)
      {
        SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 14);
        localSQLException2.fillInStackTrace();
        throw localSQLException2;
      }

      this.statement.lastIndex = paramInt;

      if (this.statement.streamList != null)
      {
        this.statement.closeUsedStreams(paramInt);
      }

      return this.statement.accessors[(paramInt - 1)].getDATE(i);
    }
  }

  public INTERVALDS getINTERVALDS(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      SQLException localSQLException1;
      if (this.closed)
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 11);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      if ((paramInt <= 0) || (paramInt > this.statement.numberOfDefinePositions))
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      int i = this.statement.currentRow;

      if (i < 0)
      {
        SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 14);
        localSQLException2.fillInStackTrace();
        throw localSQLException2;
      }

      this.statement.lastIndex = paramInt;

      if (this.statement.streamList != null)
      {
        this.statement.closeUsedStreams(paramInt);
      }

      return this.statement.accessors[(paramInt - 1)].getINTERVALDS(i);
    }
  }

  public INTERVALYM getINTERVALYM(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      SQLException localSQLException1;
      if (this.closed)
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 11);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      if ((paramInt <= 0) || (paramInt > this.statement.numberOfDefinePositions))
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      int i = this.statement.currentRow;

      if (i < 0)
      {
        SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 14);
        localSQLException2.fillInStackTrace();
        throw localSQLException2;
      }

      this.statement.lastIndex = paramInt;

      if (this.statement.streamList != null)
      {
        this.statement.closeUsedStreams(paramInt);
      }

      return this.statement.accessors[(paramInt - 1)].getINTERVALYM(i);
    }
  }

  public NUMBER getNUMBER(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      SQLException localSQLException1;
      if (this.closed)
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 11);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      if ((paramInt <= 0) || (paramInt > this.statement.numberOfDefinePositions))
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      int i = this.statement.currentRow;

      if (i < 0)
      {
        SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 14);
        localSQLException2.fillInStackTrace();
        throw localSQLException2;
      }

      this.statement.lastIndex = paramInt;

      if (this.statement.streamList != null)
      {
        this.statement.closeUsedStreams(paramInt);
      }

      return this.statement.accessors[(paramInt - 1)].getNUMBER(i);
    }
  }

  public OPAQUE getOPAQUE(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      SQLException localSQLException1;
      if (this.closed)
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 11);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      if ((paramInt <= 0) || (paramInt > this.statement.numberOfDefinePositions))
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      int i = this.statement.currentRow;

      if (i < 0)
      {
        SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 14);
        localSQLException2.fillInStackTrace();
        throw localSQLException2;
      }

      this.statement.lastIndex = paramInt;

      if (this.statement.streamList != null)
      {
        this.statement.closeUsedStreams(paramInt);
      }

      return this.statement.accessors[(paramInt - 1)].getOPAQUE(i);
    }
  }

  public Datum getOracleObject(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      SQLException localSQLException1;
      if (this.closed)
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 11);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      if ((paramInt <= 0) || (paramInt > this.statement.numberOfDefinePositions))
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      int i = this.statement.currentRow;

      if (i < 0)
      {
        SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 14);
        localSQLException2.fillInStackTrace();
        throw localSQLException2;
      }

      this.statement.lastIndex = paramInt;

      if (this.statement.streamList != null)
      {
        this.statement.closeUsedStreams(paramInt);
      }

      return this.statement.accessors[(paramInt - 1)].getOracleObject(i);
    }
  }

  public ORAData getORAData(int paramInt, ORADataFactory paramORADataFactory)
    throws SQLException
  {
    synchronized (this.connection)
    {
      SQLException localSQLException1;
      if (this.closed)
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 11);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      if ((paramInt <= 0) || (paramInt > this.statement.numberOfDefinePositions))
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      int i = this.statement.currentRow;

      if (i < 0)
      {
        SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 14);
        localSQLException2.fillInStackTrace();
        throw localSQLException2;
      }

      this.statement.lastIndex = paramInt;

      if (this.statement.streamList != null)
      {
        this.statement.closeUsedStreams(paramInt);
      }

      return this.statement.accessors[(paramInt - 1)].getORAData(i, paramORADataFactory);
    }
  }

  public RAW getRAW(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      SQLException localSQLException1;
      if (this.closed)
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 11);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      if ((paramInt <= 0) || (paramInt > this.statement.numberOfDefinePositions))
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      int i = this.statement.currentRow;

      if (i < 0)
      {
        SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 14);
        localSQLException2.fillInStackTrace();
        throw localSQLException2;
      }

      this.statement.lastIndex = paramInt;

      if (this.statement.streamList != null)
      {
        this.statement.closeUsedStreams(paramInt);
      }

      return this.statement.accessors[(paramInt - 1)].getRAW(i);
    }
  }

  public REF getREF(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      SQLException localSQLException1;
      if (this.closed)
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 11);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      if ((paramInt <= 0) || (paramInt > this.statement.numberOfDefinePositions))
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      int i = this.statement.currentRow;

      if (i < 0)
      {
        SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 14);
        localSQLException2.fillInStackTrace();
        throw localSQLException2;
      }

      this.statement.lastIndex = paramInt;

      if (this.statement.streamList != null)
      {
        this.statement.closeUsedStreams(paramInt);
      }

      return this.statement.accessors[(paramInt - 1)].getREF(i);
    }
  }

  public ROWID getROWID(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      SQLException localSQLException1;
      if (this.closed)
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 11);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      if ((paramInt <= 0) || (paramInt > this.statement.numberOfDefinePositions))
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      int i = this.statement.currentRow;

      if (i < 0)
      {
        SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 14);
        localSQLException2.fillInStackTrace();
        throw localSQLException2;
      }

      this.statement.lastIndex = paramInt;

      if (this.statement.streamList != null)
      {
        this.statement.closeUsedStreams(paramInt);
      }

      return this.statement.accessors[(paramInt - 1)].getROWID(i);
    }
  }

  public STRUCT getSTRUCT(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      SQLException localSQLException1;
      if (this.closed)
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 11);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      if ((paramInt <= 0) || (paramInt > this.statement.numberOfDefinePositions))
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      int i = this.statement.currentRow;

      if (i < 0)
      {
        SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 14);
        localSQLException2.fillInStackTrace();
        throw localSQLException2;
      }

      this.statement.lastIndex = paramInt;

      if (this.statement.streamList != null)
      {
        this.statement.closeUsedStreams(paramInt);
      }

      return this.statement.accessors[(paramInt - 1)].getSTRUCT(i);
    }
  }

  public TIMESTAMPLTZ getTIMESTAMPLTZ(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      SQLException localSQLException1;
      if (this.closed)
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 11);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      if ((paramInt <= 0) || (paramInt > this.statement.numberOfDefinePositions))
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      int i = this.statement.currentRow;

      if (i < 0)
      {
        SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 14);
        localSQLException2.fillInStackTrace();
        throw localSQLException2;
      }

      this.statement.lastIndex = paramInt;

      if (this.statement.streamList != null)
      {
        this.statement.closeUsedStreams(paramInt);
      }

      return this.statement.accessors[(paramInt - 1)].getTIMESTAMPLTZ(i);
    }
  }

  public TIMESTAMPTZ getTIMESTAMPTZ(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      SQLException localSQLException1;
      if (this.closed)
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 11);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      if ((paramInt <= 0) || (paramInt > this.statement.numberOfDefinePositions))
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      int i = this.statement.currentRow;

      if (i < 0)
      {
        SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 14);
        localSQLException2.fillInStackTrace();
        throw localSQLException2;
      }

      this.statement.lastIndex = paramInt;

      if (this.statement.streamList != null)
      {
        this.statement.closeUsedStreams(paramInt);
      }

      return this.statement.accessors[(paramInt - 1)].getTIMESTAMPTZ(i);
    }
  }

  public TIMESTAMP getTIMESTAMP(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      SQLException localSQLException1;
      if (this.closed)
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 11);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      if ((paramInt <= 0) || (paramInt > this.statement.numberOfDefinePositions))
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      int i = this.statement.currentRow;

      if (i < 0)
      {
        SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 14);
        localSQLException2.fillInStackTrace();
        throw localSQLException2;
      }

      this.statement.lastIndex = paramInt;

      if (this.statement.streamList != null)
      {
        this.statement.closeUsedStreams(paramInt);
      }

      return this.statement.accessors[(paramInt - 1)].getTIMESTAMP(i);
    }
  }

  public InputStream getAsciiStream(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      SQLException localSQLException1;
      if (this.closed)
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 11);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      if ((paramInt <= 0) || (paramInt > this.statement.numberOfDefinePositions))
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      int i = this.statement.currentRow;

      if (i < 0)
      {
        SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 14);
        localSQLException2.fillInStackTrace();
        throw localSQLException2;
      }

      this.statement.lastIndex = paramInt;

      if (this.statement.streamList != null)
      {
        this.statement.closeUsedStreams(paramInt);
      }

      return this.statement.accessors[(paramInt - 1)].getAsciiStream(i);
    }
  }

  public InputStream getBinaryStream(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      SQLException localSQLException1;
      if (this.closed)
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 11);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      if ((paramInt <= 0) || (paramInt > this.statement.numberOfDefinePositions))
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      int i = this.statement.currentRow;

      if (i < 0)
      {
        SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 14);
        localSQLException2.fillInStackTrace();
        throw localSQLException2;
      }

      this.statement.lastIndex = paramInt;

      if (this.statement.streamList != null)
      {
        this.statement.closeUsedStreams(paramInt);
      }

      return this.statement.accessors[(paramInt - 1)].getBinaryStream(i);
    }
  }

  public Reader getCharacterStream(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      SQLException localSQLException1;
      if (this.closed)
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 11);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      if ((paramInt <= 0) || (paramInt > this.statement.numberOfDefinePositions))
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      int i = this.statement.currentRow;

      if (i < 0)
      {
        SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 14);
        localSQLException2.fillInStackTrace();
        throw localSQLException2;
      }

      this.statement.lastIndex = paramInt;

      if (this.statement.streamList != null)
      {
        this.statement.closeUsedStreams(paramInt);
      }

      return this.statement.accessors[(paramInt - 1)].getCharacterStream(i);
    }
  }

  public Reader getNCharacterStream(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      SQLException localSQLException1;
      if (this.closed)
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 11);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      if ((paramInt <= 0) || (paramInt > this.statement.numberOfDefinePositions))
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      int i = this.statement.currentRow;

      if (i < 0)
      {
        SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 14);
        localSQLException2.fillInStackTrace();
        throw localSQLException2;
      }

      this.statement.lastIndex = paramInt;

      if (this.statement.streamList != null)
      {
        this.statement.closeUsedStreams(paramInt);
      }

      return this.statement.accessors[(paramInt - 1)].getNCharacterStream(i);
    }
  }

  public InputStream getUnicodeStream(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      SQLException localSQLException1;
      if (this.closed)
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 11);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      if ((paramInt <= 0) || (paramInt > this.statement.numberOfDefinePositions))
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      int i = this.statement.currentRow;

      if (i < 0)
      {
        SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 14);
        localSQLException2.fillInStackTrace();
        throw localSQLException2;
      }

      this.statement.lastIndex = paramInt;

      if (this.statement.streamList != null)
      {
        this.statement.closeUsedStreams(paramInt);
      }

      return this.statement.accessors[(paramInt - 1)].getUnicodeStream(i);
    }
  }

  public OracleResultSet.AuthorizationIndicator getAuthorizationIndicator(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      SQLException localSQLException1;
      if (this.closed)
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 11);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      if ((paramInt <= 0) || (paramInt > this.statement.numberOfDefinePositions))
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      int i = this.statement.currentRow;

      if (i < 0)
      {
        SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 14);
        localSQLException2.fillInStackTrace();
        throw localSQLException2;
      }

      this.statement.lastIndex = paramInt;

      if (this.statement.streamList != null)
      {
        this.statement.closeUsedStreams(paramInt);
      }
      return this.statement.accessors[(paramInt - 1)].getAuthorizationIndicator(i);
    }
  }

  byte[] privateGetBytes(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      SQLException localSQLException1;
      if ((paramInt <= 0) || (paramInt > this.statement.numberOfDefinePositions))
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      if (this.closed)
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 11);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      if ((paramInt <= 0) || (paramInt > this.statement.numberOfDefinePositions))
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      int i = this.statement.currentRow;

      if (i < 0)
      {
        SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 14);
        localSQLException2.fillInStackTrace();
        throw localSQLException2;
      }

      this.statement.lastIndex = paramInt;

      if (this.statement.streamList != null)
      {
        this.statement.closeUsedStreams(paramInt);
      }

      return this.statement.accessors[(paramInt - 1)].privateGetBytes(i);
    }
  }

  public void setFetchSize(int paramInt)
    throws SQLException
  {
    this.statement.setPrefetchInternal(paramInt, false, false);
  }

  public int getFetchSize()
    throws SQLException
  {
    return this.statement.getPrefetchInternal(false);
  }

  void internal_close(boolean paramBoolean)
    throws SQLException
  {
    if (this.closed) {
      return;
    }
    super.close();

    if ((this.statement.gotLastBatch) && (this.statement.validRows == 0)) {
      this.m_emptyRset = true;
    }
    PhysicalConnection localPhysicalConnection = this.statement.connection;
    try
    {
      localPhysicalConnection.registerHeartbeat();

      localPhysicalConnection.needLine();

      synchronized (localPhysicalConnection)
      {
        this.statement.closeQuery();
      }

    }
    catch (SQLException localSQLException)
    {
    }

    this.statement.endOfResultSet(paramBoolean);
  }

  public int findColumn(String paramString)
    throws SQLException
  {
    synchronized (this.connection)
    {
      return this.statement.getColumnIndex(paramString);
    }
  }

  boolean isEmptyResultSet()
  {
    return (this.m_emptyRset) || ((!this.m_emptyRset) && (this.statement.gotLastBatch) && (this.statement.validRows == 0));
  }

  int getValidRows()
  {
    return this.statement.validRows;
  }

  protected OracleConnection getConnectionDuringExceptionHandling()
  {
    return this.connection;
  }
}