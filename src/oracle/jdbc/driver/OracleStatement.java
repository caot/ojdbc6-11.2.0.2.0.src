package oracle.jdbc.driver;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.sql.BatchUpdateException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Locale;
import java.util.TimeZone;
import java.util.Vector;
import oracle.jdbc.dcn.DatabaseChangeRegistration;
import oracle.jdbc.internal.OracleConnection;
import oracle.sql.BLOB;
import oracle.sql.CLOB;

abstract class OracleStatement
  implements oracle.jdbc.internal.OracleStatement, ScrollRsetStatement
{
  static final int PLAIN_STMT = 0;
  static final int PREP_STMT = 1;
  static final int CALL_STMT = 2;
  static final byte IS_UNINITIALIZED = 0;
  static final byte IS_SELECT = 1;
  static final byte IS_DELETE = 2;
  static final byte IS_INSERT = 4;
  static final byte IS_MERGE = 8;
  static final byte IS_UPDATE = 16;
  static final byte IS_PLSQL_BLOCK = 32;
  static final byte IS_CALL_BLOCK = 64;
  static final byte IS_OTHER = -128;
  static final byte IS_DML = 30;
  static final byte IS_PLSQL = 96;
  int cursorId;
  int numberOfDefinePositions;
  int definesBatchSize;
  Accessor[] accessors;
  int defineByteSubRange;
  int defineCharSubRange;
  int defineIndicatorSubRange;
  int defineLengthSubRange;
  byte[] defineBytes;
  char[] defineChars;
  short[] defineIndicators;
  boolean described = false;
  boolean describedWithNames = false;
  byte[] defineMetaData;
  int defineMetaDataSubRange;
  static final int METADATALENGTH = 1;
  int rowsProcessed;
  int cachedDefineByteSize = 0;
  int cachedDefineCharSize = 0;
  int cachedDefineIndicatorSize = 0;
  int cachedDefineMetaDataSize = 0;

  OracleStatement children = null;
  OracleStatement parent = null;

  OracleStatement nextChild = null;
  OracleStatement next;
  OracleStatement prev;
  long c_state;
  int numberOfBindPositions;
  byte[] bindBytes;
  char[] bindChars;
  short[] bindIndicators;
  int bindByteOffset;
  int bindCharOffset;
  int bindIndicatorOffset;
  int bindByteSubRange;
  int bindCharSubRange;
  int bindIndicatorSubRange;
  Accessor[] outBindAccessors;
  InputStream[][] parameterStream;
  Object[][] userStream;
  int firstRowInBatch;
  boolean hasIbtBind = false;
  byte[] ibtBindBytes;
  char[] ibtBindChars;
  short[] ibtBindIndicators;
  int ibtBindByteOffset;
  int ibtBindCharOffset;
  int ibtBindIndicatorOffset;
  int ibtBindIndicatorSize;
  ByteBuffer[] nioBuffers = null;
  Object[] lobPrefetchMetaData = null;
  boolean hasStream;
  byte[] tmpByteArray;
  int sizeTmpByteArray = 0;
  byte[] tmpBindsByteArray;
  boolean needToSendOalToFetch = false;

  int[] definedColumnType = null;
  int[] definedColumnSize = null;
  int[] definedColumnFormOfUse = null;

  T4CTTIoac[] oacdefSent = null;

  int[] nbPostPonedColumns = null;
  int[][] indexOfPostPonedColumn = (int[][])null;

  boolean aFetchWasDoneDuringDescribe = false;

  boolean implicitDefineForLobPrefetchDone = false;

  int accessorByteOffset = 0;
  int accessorCharOffset = 0;
  int accessorShortOffset = 0;
  static final int VALID_ROWS_UNINIT = -999;
  PhysicalConnection connection;
  OracleInputStream streamList;
  OracleInputStream nextStream;
  OracleResultSetImpl currentResultSet;
  boolean processEscapes;
  boolean convertNcharLiterals;
  int queryTimeout;
  int batch;
  int numberOfExecutedElementsInBatch = -1;
  int currentRank;
  int currentRow;
  int validRows;
  int maxFieldSize;
  int maxRows;
  int totalRowsVisited;
  int rowPrefetch;
  int rowPrefetchInLastFetch = -1;
  int defaultRowPrefetch;
  boolean rowPrefetchChanged;
  int defaultLobPrefetchSize;
  boolean gotLastBatch;
  boolean clearParameters;
  boolean closed;
  boolean sqlStringChanged;
  OracleSql sqlObject;
  boolean needToParse;
  boolean needToPrepareDefineBuffer;
  boolean columnsDefinedByUser;
  byte sqlKind = 1;
  int autoRollback;
  int defaultFetchDirection;
  boolean serverCursor;
  boolean fixedString = false;

  boolean noMoreUpdateCounts = false;

  boolean isExecuting = false;
  OracleStatementWrapper wrapper;
  static final byte EXECUTE_NONE = -1;
  static final byte EXECUTE_QUERY = 1;
  static final byte EXECUTE_UPDATE = 2;
  static final byte EXECUTE_NORMAL = 3;
  byte executionType = -1;
  OracleResultSet scrollRset;
  oracle.jdbc.OracleResultSetCache rsetCache;
  int userRsetType;
  int realRsetType;
  boolean needToAddIdentifier;
  SQLWarning sqlWarning;
  int cacheState = 3;

  int creationState = 0;

  boolean isOpen = false;

  int statementType = 0;

  boolean columnSetNull = false;
  int[] returnParamMeta;
  static final int DMLR_METADATA_PREFIX_SIZE = 3;
  static final int DMLR_METADATA_NUM_OF_RETURN_PARAMS = 0;
  static final int DMLR_METADATA_ROW_BIND_BYTES = 1;
  static final int DMLR_METADATA_ROW_BIND_CHARS = 2;
  static final int DMLR_METADATA_TYPE_OFFSET = 0;
  static final int DMLR_METADATA_IS_CHAR_TYPE_OFFSET = 1;
  static final int DMLR_METADATA_BIND_SIZE_OFFSET = 2;
  static final int DMLR_METADATA_PER_POSITION_SIZE = 3;
  Accessor[] returnParamAccessors;
  boolean returnParamsFetched;
  int rowsDmlReturned;
  int numReturnParams;
  byte[] returnParamBytes;
  char[] returnParamChars;
  short[] returnParamIndicators;
  int returnParamRowBytes;
  int returnParamRowChars;
  OracleReturnResultSet returnResultSet;
  boolean isAutoGeneratedKey;
  AutoKeyInfo autoKeyInfo;
  TimeZone defaultTimeZone = null;
  String defaultTimeZoneName = null;

  Calendar defaultCalendar = null;
  Calendar gmtCalendar = null;
  int lastIndex;
  Vector m_batchItems = new Vector();

  ArrayList tempClobsToFree = null;
  ArrayList tempBlobsToFree = null;

  ArrayList oldTempClobsToFree = null;
  ArrayList oldTempBlobsToFree = null;

  NTFDCNRegistration registration = null;
  String[] dcnTableName = null;
  long dcnQueryId = -1L;

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  abstract void doDescribe(boolean paramBoolean)
    throws SQLException;

  abstract void executeForDescribe()
    throws SQLException;

  abstract void executeForRows(boolean paramBoolean)
    throws SQLException;

  abstract void fetch()
    throws SQLException;

  void continueReadRow(int paramInt)
    throws SQLException
  {
    SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 1, "continueReadRow is only implemented by the T4C statements.");
    sqlexception.fillInStackTrace();
    throw sqlexception;
  }

  abstract void doClose()
    throws SQLException;

  abstract void closeQuery()
    throws SQLException;

  public int cursorIfRefCursor()
    throws SQLException
  {
    SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 1, "cursorIfRefCursor not implemented");
    sqlexception.fillInStackTrace();
    throw sqlexception;
  }

  OracleStatement(PhysicalConnection paramPhysicalConnection, int paramInt1, int paramInt2)
    throws SQLException
  {
    this(paramPhysicalConnection, paramInt1, paramInt2, -1, -1);
  }

  OracleStatement(PhysicalConnection paramPhysicalConnection, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    throws SQLException
  {
    this.connection = paramPhysicalConnection;

    this.connection.needLine();

    this.connection.registerHeartbeat();

    this.connection.addStatement(this);

    this.sqlObject = new OracleSql(this.connection.conversion);

    this.processEscapes = this.connection.processEscapes;
    this.convertNcharLiterals = this.connection.convertNcharLiterals;
    this.autoRollback = 2;
    this.gotLastBatch = false;
    this.closed = false;
    this.clearParameters = true;
    this.serverCursor = false;
    this.needToAddIdentifier = false;
    this.defaultFetchDirection = 1000;
    this.fixedString = this.connection.getDefaultFixedString();
    this.rowPrefetchChanged = false;
    this.rowPrefetch = paramInt2;
    this.defaultRowPrefetch = paramInt2;
    if (this.connection.getVersionNumber() >= 11000)
      this.defaultLobPrefetchSize = this.connection.defaultLobPrefetchSize;
    else
      this.defaultLobPrefetchSize = -1;
    this.batch = paramInt1;

    this.sqlStringChanged = true;
    this.needToParse = true;
    this.needToPrepareDefineBuffer = true;
    this.columnsDefinedByUser = false;

    if ((paramInt3 != -1) || (paramInt4 != -1))
    {
      this.realRsetType = 0;
      this.userRsetType = ResultSetUtil.getRsetTypeCode(paramInt3, paramInt4);

      this.needToAddIdentifier = ResultSetUtil.needIdentifier(this.userRsetType);
    }
    else
    {
      this.userRsetType = 1;
      this.realRsetType = 1;
    }
  }

  void initializeDefineSubRanges()
  {
    this.defineByteSubRange = 0;
    this.defineCharSubRange = 0;
    this.defineIndicatorSubRange = 0;
    this.defineMetaDataSubRange = 0;
  }

  void prepareDefinePreambles()
  {
  }

  void prepareAccessors()
    throws SQLException
  {
    byte[] arrayOfByte1 = null;
    char[] arrayOfChar = null;
    short[] arrayOfShort = null;
    boolean bool = false;
    byte[] arrayOfByte2 = null;

    if (this.accessors == null)
    {
      SQLException sqlexception1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 21);
      sqlexception1.fillInStackTrace();
      throw sqlexception1;
    }

    int i = 0;
    int j = 0;
    int k = 0;
    Accessor localAccessor;
    for (int m = 0; m < this.numberOfDefinePositions; m++)
    {
      localAccessor = this.accessors[m];

      if (localAccessor == null)
      {
        SQLException sqlexception2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 21);
        sqlexception2.fillInStackTrace();
        throw sqlexception2;
      }
      switch (localAccessor.internalType)
      {
      case 8:
      case 24:
        this.hasStream = true;
      }

      i += localAccessor.byteLength;
      j += localAccessor.charLength;
      k += 1;
    }

    if ((this.streamList != null) && (!this.connection.useFetchSizeWithLongColumn)) {
      this.rowPrefetch = 1;
    }
    int m = this.rowPrefetch;

    this.definesBatchSize = m;

    initializeDefineSubRanges();

    int n = k * m;
    if ((this.defineMetaData == null) || (this.defineMetaData.length < n))
    {
      if (this.defineMetaData != null)
        arrayOfByte2 = this.defineMetaData;
      this.defineMetaData = new byte[n];
    }

    this.cachedDefineByteSize = (this.defineByteSubRange + i * m);

    if ((this.defineBytes == null) || (this.defineBytes.length < this.cachedDefineByteSize))
    {
      if (this.defineBytes != null) arrayOfByte1 = this.defineBytes;
      this.defineBytes = this.connection.getByteBuffer(this.cachedDefineByteSize);
    }

    this.defineByteSubRange += this.accessorByteOffset;

    this.cachedDefineCharSize = (this.defineCharSubRange + j * m);

    if (((this.defineChars == null) || (this.defineChars.length < this.cachedDefineCharSize)) && (this.cachedDefineCharSize > 0))
    {
      if (this.defineChars != null) arrayOfChar = this.defineChars;

      this.defineChars = this.connection.getCharBuffer(this.cachedDefineCharSize);
    }

    this.defineCharSubRange += this.accessorCharOffset;

    int i1 = this.numberOfDefinePositions * m;
    int i2 = this.defineIndicatorSubRange + i1 + i1;

    if ((this.defineIndicators == null) || (this.defineIndicators.length < i2))
    {
      if (this.defineIndicators != null) arrayOfShort = this.defineIndicators;
      this.defineIndicators = new short[i2];
    } else if (this.defineIndicators.length >= i2)
    {
      bool = true;
      arrayOfShort = this.defineIndicators;
    }

    this.defineIndicatorSubRange += this.accessorShortOffset;

    int i3 = this.defineIndicatorSubRange + i1;

    for (int i4 = 0; i4 < this.numberOfDefinePositions; i4++)
    {
      localAccessor = this.accessors[i4];

      localAccessor.lengthIndexLastRow = localAccessor.lengthIndex;
      localAccessor.indicatorIndexLastRow = localAccessor.indicatorIndex;
      localAccessor.columnIndexLastRow = localAccessor.columnIndex;

      localAccessor.setOffsets(m);

      localAccessor.lengthIndex = i3;
      localAccessor.indicatorIndex = this.defineIndicatorSubRange;
      localAccessor.metaDataIndex = this.defineMetaDataSubRange;
      localAccessor.rowSpaceByte = this.defineBytes;
      localAccessor.rowSpaceChar = this.defineChars;
      localAccessor.rowSpaceIndicator = this.defineIndicators;
      localAccessor.rowSpaceMetaData = this.defineMetaData;
      this.defineIndicatorSubRange += m;
      i3 += m;
      this.defineMetaDataSubRange += m * 1;
    }

    prepareDefinePreambles();

    if ((this.rowPrefetchInLastFetch != -1) && (this.rowPrefetch != this.rowPrefetchInLastFetch))
    {
      if (arrayOfChar == null) arrayOfChar = this.defineChars;
      if (arrayOfByte1 == null) arrayOfByte1 = this.defineBytes;
      if (arrayOfShort == null) arrayOfShort = this.defineIndicators;
      saveDefineBuffersIfRequired(arrayOfChar, arrayOfByte1, arrayOfShort, bool);
    }
  }

  boolean checkAccessorsUsable()
    throws SQLException
  {
    int i = this.accessors.length;

    if (i < this.numberOfDefinePositions) {
      return false;
    }
    int j = 1;
    int k = 0;
    boolean bool = false;

    for (int m = 0; m < this.numberOfDefinePositions; m++)
    {
      Accessor localAccessor = this.accessors[m];

      if ((localAccessor == null) || (localAccessor.externalType == 0))
        j = 0;
      else {
        k = 1;
      }
    }
    if (j != 0)
    {
      bool = true; } else {
      if (k != 0)
      {
        SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 21);
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }

      this.columnsDefinedByUser = false;
    }
    return bool;
  }

  void executeMaybeDescribe()
    throws SQLException
  {
    int i = 1;

    if (this.rowPrefetchChanged)
    {
      if ((this.streamList == null) && (this.rowPrefetch != this.definesBatchSize)) {
        this.needToPrepareDefineBuffer = true;
      }
      this.rowPrefetchChanged = false;
    }

    if (!this.needToPrepareDefineBuffer)
    {
      if (this.accessors == null)
      {
        this.needToPrepareDefineBuffer = true;
      } else if (this.columnsDefinedByUser) {
        this.needToPrepareDefineBuffer = (!checkAccessorsUsable());
      }
    }
    boolean bool = false;
    try
    {
      this.isExecuting = true;

      if (this.needToPrepareDefineBuffer)
      {
        if (!this.columnsDefinedByUser)
        {
          executeForDescribe();

          bool = true;

          if (this.aFetchWasDoneDuringDescribe) {
            i = 0;
          }

        }

        if (this.needToPrepareDefineBuffer)
        {
          prepareAccessors();
        }

      }

      int j = this.accessors.length;

      for (int k = this.numberOfDefinePositions; k < j; k++)
      {
        Accessor localAccessor = this.accessors[k];

        if (localAccessor != null)
          localAccessor.rowSpaceIndicator = null;
      }
      if (i != 0) {
        executeForRows(bool);
      }
    }
    catch (SQLException sqlexception)
    {
      this.needToParse = true;
      throw sqlexception;
    }
    finally
    {
      this.isExecuting = false;
    }
  }

  void adjustGotLastBatch()
  {
  }

  void doExecuteWithTimeout()
    throws SQLException
  {
    try
    {
      cleanOldTempLobs();

      this.connection.registerHeartbeat();

      this.rowsProcessed = 0;
      SQLException sqlexception1;
      if (this.sqlKind == 1)
      {
        if ((this.connection.j2ee13Compliant) && (this.executionType == 2))
        {
          sqlexception1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 129);
          sqlexception1.fillInStackTrace();
          throw sqlexception1;
        }

        this.connection.needLine();

        if (!this.isOpen)
        {
          this.connection.open(this);

          this.isOpen = true;
        }

        if (this.queryTimeout != 0)
        {
          try
          {
            this.connection.getTimeout().setTimeout(this.queryTimeout * 1000, this);
            executeMaybeDescribe();
          }
          finally
          {
            this.connection.getTimeout().cancelTimeout();
          }
        }
        else {
          executeMaybeDescribe();
        }
        checkValidRowsStatus();

        if (this.serverCursor)
          adjustGotLastBatch();
      }
      else
      {
        if ((this.connection.j2ee13Compliant) && ((this.sqlKind & 0x60) == 0) && (this.executionType == 1))
        {
          sqlexception1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 128);
          sqlexception1.fillInStackTrace();
          throw sqlexception1;
        }

        this.currentRank += 1;

        if (this.currentRank >= this.batch)
        {
          try
          {
            this.connection.needLine();

            if (!this.isOpen)
            {
              this.connection.open(this);

              this.isOpen = true;
            }

            if (this.queryTimeout != 0) {
              this.connection.getTimeout().setTimeout(this.queryTimeout * 1000, this);
            }
            this.isExecuting = true;

            executeForRows(false);
          }
          catch (SQLException sqlexception2)
          {
            this.needToParse = true;
            if (this.batch > 1)
            {
              clearBatch();
              int[] arrayOfInt;
              int i;
              if ((this.numberOfExecutedElementsInBatch != -1) && (this.numberOfExecutedElementsInBatch < this.batch))
              {
                arrayOfInt = new int[this.numberOfExecutedElementsInBatch];
                for (i = 0; i < arrayOfInt.length; i++)
                  arrayOfInt[i] = -2;
              }
              else
              {
                arrayOfInt = new int[this.batch];
                for (i = 0; i < arrayOfInt.length; i++) {
                  arrayOfInt[i] = -3;
                }
              }
              BatchUpdateException localBatchUpdateException = DatabaseError.createBatchUpdateException(sqlexception2, arrayOfInt.length, arrayOfInt);
              localBatchUpdateException.fillInStackTrace();
              throw localBatchUpdateException;
            }

            resetCurrentRowBinders();

            throw sqlexception2;
          }
          finally
          {
            if (this.queryTimeout != 0) {
              this.connection.getTimeout().cancelTimeout();
            }
            this.currentRank = 0;
            this.isExecuting = false;

            checkValidRowsStatus();
          }

        }

      }

    }
    catch (SQLException sqlexception3)
    {
      resetOnExceptionDuringExecute();
      throw sqlexception3;
    }

    this.connection.registerHeartbeat();
  }

  void resetOnExceptionDuringExecute()
  {
    this.needToParse = true;
  }

  void resetCurrentRowBinders()
  {
  }

  void open()
    throws SQLException
  {
    if (!this.isOpen)
    {
      this.connection.needLine();
      this.connection.open(this);

      this.isOpen = true;
    }
  }

  public ResultSet executeQuery(String paramString)
    throws SQLException
  {
    synchronized (this.connection)
    {
      ResultSet localObject1 = null;
      try
      {
        this.executionType = 1;

        this.noMoreUpdateCounts = false;

        ensureOpen();
        checkIfJdbcBatchExists();

        sendBatch();

        this.hasStream = false;

        this.sqlObject.initialize(paramString);

        this.sqlKind = this.sqlObject.getSqlKind();
        this.needToParse = true;

        prepareForNewResults(true, true);

        if (this.userRsetType == 1)
        {
          doExecuteWithTimeout();

          this.currentResultSet = new OracleResultSetImpl(this.connection, this);
          localObject1 = this.currentResultSet;
        }
        else
        {
          localObject1 = doScrollStmtExecuteQuery();

          if (localObject1 == null)
          {
            this.currentResultSet = new OracleResultSetImpl(this.connection, this);
            localObject1 = this.currentResultSet;
          }
        }
      }
      finally
      {
        this.executionType = -1;
      }

      return localObject1;
    }
  }

  public void closeWithKey(String paramString)
    throws SQLException
  {
    SQLException sqlexception = DatabaseError.createUnsupportedFeatureSqlException();
    sqlexception.fillInStackTrace();
    throw sqlexception;
  }

  public void close()
    throws SQLException
  {
    synchronized (this.connection)
    {
      closeOrCache(null);
    }
  }

  protected void closeOrCache(String paramString)
    throws SQLException
  {
    if (this.closed) {
      return;
    }

    if ((this.statementType != 0) && (this.cacheState != 0) && (this.cacheState != 3) && (this.connection.isStatementCacheInitialized()))
    {
      if (paramString == null)
      {
        if (this.connection.getImplicitCachingEnabled())
        {
          this.connection.cacheImplicitStatement((OraclePreparedStatement)this, this.sqlObject.getOriginalSql(), this.statementType, this.userRsetType);
        }
        else
        {
          this.cacheState = 0;

          hardClose();
        }

      }
      else if (this.connection.getExplicitCachingEnabled())
      {
        this.connection.cacheExplicitStatement((OraclePreparedStatement)this, paramString);
      }
      else
      {
        this.cacheState = 0;

        hardClose();
      }

    }
    else
    {
      hardClose();
    }
  }

  protected void hardClose()
    throws SQLException
  {
    hardClose(true);
  }

  private void hardClose(boolean paramBoolean)
    throws SQLException
  {
    alwaysOnClose();

    this.describedWithNames = false;
    this.described = false;

    this.connection.removeStatement(this);

    cleanupDefines();

    if ((this.isOpen) && (paramBoolean) && ((this.connection.lifecycle == 1) || (this.connection.lifecycle == 16) || (this.connection.lifecycle == 2)))
    {
      this.connection.registerHeartbeat();

      if (this.connection.lifecycle == 2)
        this.connection.needLineUnchecked();
      else
        this.connection.needLine();
      doClose();

      this.isOpen = false;
    }

    this.sqlObject = null;
  }

  protected void alwaysOnClose()
    throws SQLException
  {
    Object localObject = this.children;

    while (localObject != null)
    {
      OracleStatement localOracleStatement = ((OracleStatement)localObject).nextChild;

      ((OracleStatement)localObject).close();

      localObject = localOracleStatement;
    }

    if (this.parent != null)
    {
      this.parent.removeChild(this);
    }

    this.closed = true;

    if ((this.connection.lifecycle == 1) || (this.connection.lifecycle == 2))
    {
      if (this.currentResultSet != null)
      {
        this.currentResultSet.internal_close(false);

        this.currentResultSet = null;
      }

      if (this.scrollRset != null)
      {
        this.scrollRset.close();

        this.scrollRset = null;
      }

      if (this.returnResultSet != null)
      {
        this.returnResultSet.close();
        this.returnResultSet = null;
      }
    }

    clearWarnings();

    this.m_batchItems = null;
  }

  void closeLeaveCursorOpen()
    throws SQLException
  {
    synchronized (this.connection)
    {
      if (this.closed)
      {
        return;
      }

      hardClose(false);
    }
  }

  public int executeUpdate(String paramString)
    throws SQLException
  {
    synchronized (this.connection)
    {
      setNonAutoKey();
      return executeUpdateInternal(paramString);
    }
  }

  int executeUpdateInternal(String paramString)
    throws SQLException
  {
    try
    {
      if (this.executionType == -1) {
        this.executionType = 2;
      }

      this.noMoreUpdateCounts = false;

      ensureOpen();
      checkIfJdbcBatchExists();

      sendBatch();

      this.hasStream = false;

      this.sqlObject.initialize(paramString);

      this.sqlKind = this.sqlObject.getSqlKind();
      this.needToParse = true;

      prepareForNewResults(true, true);

      if (this.userRsetType == 1)
      {
        doExecuteWithTimeout();
      }
      else
      {
        doScrollStmtExecuteQuery();
      }

      return this.validRows;
    }
    finally
    {
      this.executionType = -1;
    }
  }

  public boolean execute(String paramString)
    throws SQLException
  {
    synchronized (this.connection)
    {
      setNonAutoKey();
      return executeInternal(paramString);
    }
  }

  boolean executeInternal(String paramString)
    throws SQLException
  {
    try
    {
      this.executionType = 3;

      this.noMoreUpdateCounts = false;

      ensureOpen();
      checkIfJdbcBatchExists();

      sendBatch();

      this.hasStream = false;

      this.sqlObject.initialize(paramString);

      this.sqlKind = this.sqlObject.getSqlKind();
      this.needToParse = true;

      prepareForNewResults(true, true);

      if (this.userRsetType == 1)
      {
        doExecuteWithTimeout();
      }
      else
      {
        doScrollStmtExecuteQuery();
      }

      return this.sqlKind == 1;
    }
    finally
    {
      this.executionType = -1;
    }
  }

  int getNumberOfColumns()
    throws SQLException
  {
    ensureOpen();
    if (!this.described)
    {
      synchronized (this.connection) {
        doDescribe(false);

        this.described = true;
      }
    }

    return this.numberOfDefinePositions;
  }

  Accessor[] getDescription()
    throws SQLException
  {
    ensureOpen();
    if (!this.described)
    {
      synchronized (this.connection) {
        doDescribe(false);

        this.described = true;
      }
    }

    return this.accessors;
  }

  Accessor[] getDescriptionWithNames()
    throws SQLException
  {
    ensureOpen();
    if (!this.describedWithNames)
    {
      synchronized (this.connection) {
        doDescribe(true);

        this.described = true;
        this.describedWithNames = true;
      }
    }

    return this.accessors;
  }

  byte getSqlKind()
  {
    return this.sqlKind;
  }

  public void clearDefines()
    throws SQLException
  {
    synchronized (this.connection)
    {
      freeLine();

      this.streamList = null;

      this.columnsDefinedByUser = false;
      this.needToPrepareDefineBuffer = true;

      this.numberOfDefinePositions = 0;
      this.definesBatchSize = 0;

      this.described = false;
      this.describedWithNames = false;

      cleanupDefines();
    }
  }

  void reparseOnRedefineIfNeeded()
    throws SQLException
  {
  }

  void defineColumnTypeInternal(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean, String paramString)
    throws SQLException
  {
    defineColumnTypeInternal(paramInt1, paramInt2, paramInt3, (short)1, paramBoolean, paramString);
  }

  void defineColumnTypeInternal(int paramInt1, int paramInt2, int paramInt3, short paramShort, boolean paramBoolean, String paramString)
    throws SQLException
  {
    if (this.connection.disableDefinecolumntype)
      return;
    SQLException sqlexception;
    if (paramInt1 < 1)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    if (paramInt2 == 0)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    int i = paramInt1 - 1;
    int j = this.maxFieldSize > 0 ? this.maxFieldSize : -1;
    Object localObject1;
    if (paramBoolean)
    {
      if ((paramInt2 == 1) || (paramInt2 == 12))
      {
        this.sqlWarning = DatabaseError.addSqlWarning(this.sqlWarning, 108);
      }

    }
    else
    {
      if (paramInt3 < 0)
      {
        sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 53);
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }

      if (((j == -1) && (paramInt3 > 0)) || ((j > 0) && (paramInt3 < j)))
      {
        j = paramInt3;
      }
    }
    if ((this.currentResultSet != null) && (!this.currentResultSet.closed))
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 28);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    if (!this.columnsDefinedByUser)
    {
      clearDefines();

      this.columnsDefinedByUser = true;
    }

    if (this.numberOfDefinePositions < paramInt1)
    {
      if ((this.accessors == null) || (this.accessors.length < paramInt1))
      {
        localObject1 = new Accessor[paramInt1 << 1];

        if (this.accessors != null) {
          System.arraycopy(this.accessors, 0, localObject1, 0, this.numberOfDefinePositions);
        }
        this.accessors = ((Accessor[])localObject1);
      }

      this.numberOfDefinePositions = paramInt1;
    }

    switch (paramInt2) {
    case -16:
    case -15:
    case -9:
    case 2011:
      paramShort = 2;
      break;
    case 2009:
      paramString = "SYS.XMLTYPE";
      break;
    }

    int k = getInternalType(paramInt2);

    if (((k == 109) || (k == 111)) && ((paramString == null) || (paramString.equals(""))))
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 60, "Invalid arguments");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject2 = this.accessors[i];
    int m = 1;

    if (localObject2 != null)
    {
      int n = ((Accessor)localObject2).useForDataAccessIfPossible(k, paramInt2, j, paramString);

      if (n == 0)
      {
        paramShort = ((Accessor)localObject2).formOfUse;
        localObject2 = null;

        reparseOnRedefineIfNeeded();
      }
      else if (n == 1)
      {
        localObject2 = null;

        reparseOnRedefineIfNeeded();
      }
      else if (n == 2) {
        m = 0;
      }
    }
    if (m != 0) {
      this.needToPrepareDefineBuffer = true;
    }
    if (localObject2 == null)
    {
      this.accessors[i] = allocateAccessor(k, paramInt2, paramInt1, j, paramShort, paramString, false);

      this.described = false;
      this.describedWithNames = false;
    }
  }

  Accessor allocateAccessor(int paramInt1, int paramInt2, int paramInt3, int paramInt4, short paramShort, String paramString, boolean paramBoolean)
    throws SQLException
  {
    Accessor localObject;
    switch (paramInt1)
    {
    case 96:
      if ((paramBoolean) && (paramString != null))
      {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 12, "sqlType=" + paramInt2);
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }

      return new CharAccessor(this, paramInt4, paramShort, paramInt2, paramBoolean);
    case 8:
      if ((paramBoolean) && (paramString != null))
      {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 12, "sqlType=" + paramInt2);
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }

      if (!paramBoolean) {
        return new LongAccessor(this, paramInt3, paramInt4, paramShort, paramInt2);
      }

    case 1:
      if ((paramBoolean) && (paramString != null))
      {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 12, "sqlType=" + paramInt2);
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }

      return new VarcharAccessor(this, paramInt4, paramShort, paramInt2, paramBoolean);
    case 2:
      if ((paramBoolean) && (paramString != null))
      {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 12, "sqlType=" + paramInt2);
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }

      return new NumberAccessor(this, paramInt4, paramShort, paramInt2, paramBoolean);
    case 6:
      if ((paramBoolean) && (paramString != null))
      {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 12, "sqlType=" + paramInt2);
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }

      return new VarnumAccessor(this, paramInt4, paramShort, paramInt2, paramBoolean);
    case 24:
      if ((paramBoolean) && (paramString != null))
      {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 12, "sqlType=" + paramInt2);
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }

      if (!paramBoolean) {
        return new LongRawAccessor(this, paramInt3, paramInt4, paramShort, paramInt2);
      }

    case 23:
      if ((paramBoolean) && (paramString != null))
      {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 12, "sqlType=" + paramInt2);
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }

      if (paramBoolean) {
        return new OutRawAccessor(this, paramInt4, paramShort, paramInt2);
      }
      return new RawAccessor(this, paramInt4, paramShort, paramInt2, false);
    case 100:
      if ((paramBoolean) && (paramString != null))
      {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 12, "sqlType=" + paramInt2);
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }

      return new BinaryFloatAccessor(this, paramInt4, paramShort, paramInt2, paramBoolean);
    case 101:
      if ((paramBoolean) && (paramString != null))
      {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 12, "sqlType=" + paramInt2);
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }

      return new BinaryDoubleAccessor(this, paramInt4, paramShort, paramInt2, paramBoolean);
    case 104:
      if ((paramBoolean) && (paramString != null))
      {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 12, "sqlType=" + paramInt2);
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }

      return new RowidAccessor(this, paramInt4, paramShort, paramInt2, paramBoolean);
    case 102:
      if ((paramBoolean) && (paramString != null))
      {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 12, "sqlType=" + paramInt2);
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }

      return new ResultSetAccessor(this, paramInt4, paramShort, paramInt2, paramBoolean);
    case 12:
      if ((paramBoolean) && (paramString != null))
      {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 12, "sqlType=" + paramInt2);
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }

      return new DateAccessor(this, paramInt4, paramShort, paramInt2, paramBoolean);
    case 113:
      if ((paramBoolean) && (paramString != null))
      {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 12, "sqlType=" + paramInt2);
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }

      localObject = new BlobAccessor(this, -1, paramShort, paramInt2, paramBoolean);
      if (!paramBoolean)
        ((Accessor)localObject).lobPrefetchSizeForThisColumn = paramInt4;
      return localObject;
    case 112:
      if ((paramBoolean) && (paramString != null))
      {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 12, "sqlType=" + paramInt2);
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }

      localObject = new ClobAccessor(this, -1, paramShort, paramInt2, paramBoolean);
      if (!paramBoolean)
        ((Accessor)localObject).lobPrefetchSizeForThisColumn = paramInt4;
      return localObject;
    case 114:
      if ((paramBoolean) && (paramString != null))
      {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 12, "sqlType=" + paramInt2);
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }

      localObject = new BfileAccessor(this, -1, paramShort, paramInt2, paramBoolean);

      return localObject;
    case 109:
      if (paramString == null) {
        if (paramBoolean)
        {
        SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 12, "sqlType=" + paramInt2);
          sqlexception.fillInStackTrace();
          throw sqlexception;
        }

      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 60, "Unable to resolve type \"null\"");
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }

      localObject = new NamedTypeAccessor(this, paramString, paramShort, paramInt2, paramBoolean);

      ((Accessor)localObject).initMetadata();

      return localObject;
    case 111:
      if (paramString == null) {
        if (paramBoolean)
        {
        SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 12, "sqlType=" + paramInt2);
          sqlexception.fillInStackTrace();
          throw sqlexception;
        }

      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 60, "Unable to resolve type \"null\"");
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }

      localObject = new RefTypeAccessor(this, paramString, paramShort, paramInt2, paramBoolean);

      ((Accessor)localObject).initMetadata();

      return localObject;
    case 180:
      if ((paramBoolean) && (paramString != null))
      {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 12, "sqlType=" + paramInt2);
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }

      return new TimestampAccessor(this, paramInt4, paramShort, paramInt2, paramBoolean);
    case 181:
      if ((paramBoolean) && (paramString != null))
      {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 12, "sqlType=" + paramInt2);
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }

      return new TimestamptzAccessor(this, paramInt4, paramShort, paramInt2, paramBoolean);
    case 231:
      if ((paramBoolean) && (paramString != null))
      {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 12, "sqlType=" + paramInt2);
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }

      return new TimestampltzAccessor(this, paramInt4, paramShort, paramInt2, paramBoolean);
    case 182:
      if ((paramBoolean) && (paramString != null))
      {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 12, "sqlType=" + paramInt2);
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }

      return new IntervalymAccessor(this, paramInt4, paramShort, paramInt2, paramBoolean);
    case 183:
      if ((paramBoolean) && (paramString != null))
      {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 12, "sqlType=" + paramInt2);
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }

      return new IntervaldsAccessor(this, paramInt4, paramShort, paramInt2, paramBoolean);
    case 995:
    SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 89);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4);
    sqlexception.fillInStackTrace();
    throw sqlexception;
  }

  public void defineColumnType(int paramInt1, int paramInt2)
    throws SQLException
  {
    synchronized (this.connection)
    {
      defineColumnTypeInternal(paramInt1, paramInt2, -1, true, null);
    }
  }

  public void defineColumnType(int paramInt1, int paramInt2, int paramInt3)
    throws SQLException
  {
    defineColumnTypeInternal(paramInt1, paramInt2, paramInt3, false, null);
  }

  public void defineColumnType(int paramInt1, int paramInt2, int paramInt3, short paramShort)
    throws SQLException
  {
    defineColumnTypeInternal(paramInt1, paramInt2, paramInt3, paramShort, false, null);
  }

  /** @deprecated */
  public void defineColumnTypeBytes(int paramInt1, int paramInt2, int paramInt3)
    throws SQLException
  {
    synchronized (this.connection)
    {
      defineColumnTypeInternal(paramInt1, paramInt2, paramInt3, false, null);
    }
  }

  /** @deprecated */
  public void defineColumnTypeChars(int paramInt1, int paramInt2, int paramInt3)
    throws SQLException
  {
    defineColumnTypeInternal(paramInt1, paramInt2, paramInt3, false, null);
  }

  public void defineColumnType(int paramInt1, int paramInt2, String paramString)
    throws SQLException
  {
    synchronized (this.connection)
    {
      defineColumnTypeInternal(paramInt1, paramInt2, -1, true, paramString);
    }
  }

  void setCursorId(int paramInt)
    throws SQLException
  {
    this.cursorId = paramInt;
  }

  void setPrefetchInternal(int paramInt, boolean paramBoolean1, boolean paramBoolean2)
    throws SQLException
  {
    SQLException sqlexception;
    if (paramBoolean1)
    {
      if (paramInt <= 0)
      {
        sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 20);
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }
    }
    else
    {
      if (paramInt < 0)
      {
        sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68, "setFetchSize");
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }
      if (paramInt == 0) {
        paramInt = this.connection.getDefaultRowPrefetch();
      }
    }

    if (paramBoolean2)
    {
      if (paramInt != this.defaultRowPrefetch)
      {
        this.defaultRowPrefetch = paramInt;

        if ((this.currentResultSet == null) || (this.currentResultSet.closed)) {
          this.rowPrefetchChanged = true;
        }

      }

    }
    else if ((paramInt != this.rowPrefetch) && (this.streamList == null))
    {
      this.rowPrefetch = paramInt;
      this.rowPrefetchChanged = true;
    }
  }

  public void setRowPrefetch(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      setPrefetchInternal(paramInt, true, true);
    }
  }

  public void setLobPrefetchSize(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      if (paramInt < -1)
      {
        SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 267);
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }
      this.defaultLobPrefetchSize = paramInt;
    }
  }

  public int getLobPrefetchSize()
  {
    return this.defaultLobPrefetchSize;
  }

  int getPrefetchInternal(boolean paramBoolean)
  {
    int i = paramBoolean ? this.defaultRowPrefetch : this.rowPrefetch;

    return i;
  }

  public int getRowPrefetch()
  {
    synchronized (this.connection)
    {
      return getPrefetchInternal(true);
    }
  }

  public void setFixedString(boolean paramBoolean)
  {
    this.fixedString = paramBoolean;
  }

  public boolean getFixedString()
  {
    return this.fixedString;
  }

  void check_row_prefetch_changed()
    throws SQLException
  {
    if (this.rowPrefetchChanged)
    {
      if (this.streamList == null)
      {
        prepareAccessors();

        this.needToPrepareDefineBuffer = true;
      }

      this.rowPrefetchChanged = false;
    }
  }

  void setDefinesInitialized(boolean paramBoolean)
  {
  }

  void printState(String paramString)
    throws SQLException
  {
  }

  void checkValidRowsStatus()
    throws SQLException
  {
    if (this.validRows == -2)
    {
      this.validRows = 1;
      this.connection.holdLine(this);

      OracleInputStream localOracleInputStream = this.streamList;

      while (localOracleInputStream != null)
      {
        if (localOracleInputStream.hasBeenOpen) {
          localOracleInputStream = localOracleInputStream.accessor.initForNewRow();
        }

        localOracleInputStream.closed = false;
        localOracleInputStream.hasBeenOpen = true;

        localOracleInputStream = localOracleInputStream.nextStream;
      }

      this.nextStream = this.streamList;
    }
    else if (this.sqlKind == 1)
    {
      if (this.validRows < this.rowPrefetch)
        this.gotLastBatch = true;
    }
    else if ((this.sqlKind & 0x60) == 0)
    {
      this.rowsProcessed = this.validRows;
    }
  }

  void cleanupDefines()
  {
    if (this.accessors != null) {
      for (int i = 0; i < this.accessors.length; i++)
        this.accessors[i] = null;
    }
    this.accessors = null;

    this.connection.cacheBuffer(this.defineBytes);
    this.defineBytes = null;
    this.connection.cacheBuffer(this.defineChars);
    this.defineChars = null;
    this.defineIndicators = null;
    this.defineMetaData = null;
  }

  public int getMaxFieldSize()
    throws SQLException
  {
    synchronized (this.connection)
    {
      return this.maxFieldSize;
    }
  }

  public void setMaxFieldSize(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      if (paramInt < 0)
      {
        SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68);
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }

      this.maxFieldSize = paramInt;
    }
  }

  public int getMaxRows()
    throws SQLException
  {
    return this.maxRows;
  }

  public void setMaxRows(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      if (paramInt < 0)
      {
        SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68);
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }

      this.maxRows = paramInt;
    }
  }

  public void setEscapeProcessing(boolean paramBoolean)
    throws SQLException
  {
    synchronized (this.connection)
    {
      this.processEscapes = paramBoolean;
    }
  }

  public int getQueryTimeout()
    throws SQLException
  {
    synchronized (this.connection)
    {
      return this.queryTimeout;
    }
  }

  public void setQueryTimeout(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      if (paramInt < 0)
      {
        SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68);
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }

      this.queryTimeout = paramInt;
    }
  }

  public void cancel()
    throws SQLException
  {
    doCancel();
  }

  boolean doCancel()
    throws SQLException
  {
    boolean bool = false;

    if (this.closed) {
      return bool;
    }

    if (this.connection.statementHoldingLine != null) {
      freeLine();
    } else if (this.isExecuting)
    {
      bool = true;
      this.connection.cancelOperationOnServer();
    }

    OracleStatement localOracleStatement = this.children;
    while (localOracleStatement != null) {
      bool = (bool) || (localOracleStatement.doCancel());
      localOracleStatement = localOracleStatement.nextChild;
    }

    this.connection.releaseLineForCancel();
    return bool;
  }

  public SQLWarning getWarnings()
    throws SQLException
  {
    return this.sqlWarning;
  }

  public void clearWarnings()
    throws SQLException
  {
    this.sqlWarning = null;
  }

  void foundPlsqlCompilerWarning()
    throws SQLException
  {
    SQLWarning localSQLWarning = DatabaseError.addSqlWarning(this.sqlWarning, "Found Plsql compiler warnings.", 24439);

    if (this.sqlWarning != null)
    {
      this.sqlWarning.setNextWarning(localSQLWarning);
    }
    else
    {
      this.sqlWarning = localSQLWarning;
    }
  }

  public void setCursorName(String paramString)
    throws SQLException
  {
    SQLException sqlexception = DatabaseError.createUnsupportedFeatureSqlException();
    sqlexception.fillInStackTrace();
    throw sqlexception;
  }

  public ResultSet getResultSet()
    throws SQLException
  {
    synchronized (this.connection)
    {
      if (this.userRsetType == 1)
      {
        if (this.sqlKind == 1)
        {
          if (this.currentResultSet == null) {
            this.currentResultSet = new OracleResultSetImpl(this.connection, this);
          }
          return this.currentResultSet;
        }
      }
      else
      {
        return this.scrollRset;
      }

      return null;
    }
  }

  public int getUpdateCount()
    throws SQLException
  {
    synchronized (this.connection)
    {
      int i = -1;

      switch (this.sqlKind)
      {
      case 0:
      case 1:
        break;
      case -128:
        if (!this.noMoreUpdateCounts) {
          i = this.rowsProcessed;
        }
        this.noMoreUpdateCounts = true;

        break;
      case 32:
      case 64:
        this.noMoreUpdateCounts = true;

        break;
      case 2:
      case 4:
      case 8:
      case 16:
        if (!this.noMoreUpdateCounts) {
          i = this.rowsProcessed;
        }
        this.noMoreUpdateCounts = true;
      }

      return i;
    }
  }

  public boolean getMoreResults()
    throws SQLException
  {
    return false;
  }

  public int sendBatch()
    throws SQLException
  {
    return 0;
  }

  void prepareForNewResults(boolean paramBoolean1, boolean paramBoolean2)
    throws SQLException
  {
    clearWarnings();

    if (this.streamList != null)
    {
      Object localObject;
      while (this.nextStream != null)
      {
        try
        {
          this.nextStream.close();
        }
        catch (IOException localIOException)
        {
          SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localIOException);
          sqlexception.fillInStackTrace();
          throw sqlexception;
        }

        this.nextStream = this.nextStream.nextStream;
      }

      if (paramBoolean2)
      {
        OracleInputStream localOracleInputStream = this.streamList;
        localObject = null;

        this.streamList = null;

        while (localOracleInputStream != null)
        {
          if (!localOracleInputStream.hasBeenOpen)
          {
            if (localObject == null)
              this.streamList = localOracleInputStream;
            else {
              ((OracleInputStream)localObject).nextStream = localOracleInputStream;
            }
            localObject = localOracleInputStream;
          }

          localOracleInputStream = localOracleInputStream.nextStream;
        }
      }
    }

    if (this.currentResultSet != null)
    {
      this.currentResultSet.internal_close(true);

      this.currentResultSet = null;
    }

    this.currentRow = -1;
    this.validRows = 0;
    if (paramBoolean1)
      this.totalRowsVisited = 0;
    this.gotLastBatch = false;

    if ((this.needToParse) && (!this.columnsDefinedByUser))
    {
      if ((paramBoolean2) && (this.numberOfDefinePositions != 0)) {
        this.numberOfDefinePositions = 0;
      }
      this.needToPrepareDefineBuffer = true;
    }

    if ((paramBoolean1) && (this.rowPrefetch != this.defaultRowPrefetch) && (this.streamList == null))
    {
      this.rowPrefetch = this.defaultRowPrefetch;
      this.rowPrefetchChanged = true;
    }
  }

  void reopenStreams()
    throws SQLException
  {
    OracleInputStream localOracleInputStream = this.streamList;

    while (localOracleInputStream != null)
    {
      if (localOracleInputStream.hasBeenOpen) {
        localOracleInputStream = localOracleInputStream.accessor.initForNewRow();
      }
      localOracleInputStream.closed = false;
      localOracleInputStream.hasBeenOpen = true;
      localOracleInputStream = localOracleInputStream.nextStream;
    }

    this.nextStream = this.streamList;
  }

  void endOfResultSet(boolean paramBoolean)
    throws SQLException
  {
    if (!paramBoolean)
    {
      prepareForNewResults(false, false);
    }

    clearDefines();
    this.rowPrefetchInLastFetch = -1;
  }

  boolean wasNullValue()
    throws SQLException
  {
    if (this.lastIndex == 0)
    {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 24);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    if (this.sqlKind == 1) {
      return this.accessors[(this.lastIndex - 1)].isNull(this.currentRow);
    }
    return this.outBindAccessors[(this.lastIndex - 1)].isNull(this.currentRank);
  }

  int getColumnIndex(String paramString)
    throws SQLException
  {
    ensureOpen();
    if (!this.describedWithNames)
    {
      synchronized (this.connection) {
        doDescribe(true);

        this.described = true;
        this.describedWithNames = true;
      }
    }

    for (int i = 0; i < this.numberOfDefinePositions; i++) {
      if (this.accessors[i].columnName.equalsIgnoreCase(paramString)) {
        return i + 1;
      }
    }
    SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 6);
    sqlexception.fillInStackTrace();
    throw sqlexception;
  }

  int getJDBCType(int paramInt)
    throws SQLException
  {
    int i = 0;
    switch (paramInt)
    {
    case 6:
      i = 2;
      break;
    case 100:
      i = 100;
      break;
    case 101:
      i = 101;
      break;
    case 999:
      i = 999;
      break;
    case 96:
      i = 1;
      break;
    case 1:
      i = 12;
      break;
    case 8:
      i = -1;
      break;
    case 12:
      i = 91;
      break;
    case 180:
      i = 93;
      break;
    case 181:
      i = -101;
      break;
    case 231:
      i = -102;
      break;
    case 182:
      i = -103;
      break;
    case 183:
      i = -104;
      break;
    case 23:
      i = -2;
      break;
    case 24:
      i = -4;
      break;
    case 104:
      i = -8;
      break;
    case 113:
      i = 2004;
      break;
    case 112:
      i = 2005;
      break;
    case 114:
      i = -13;
      break;
    case 102:
      i = -10;
      break;
    case 109:
      i = 2002;
      break;
    case 111:
      i = 2006;
      break;
    case 998:
      i = -14;
      break;
    case 995:
      i = 0;
      break;
    default:
      i = paramInt;
    }

    return i;
  }

  int getInternalType(int paramInt)
    throws SQLException
  {
    int i = 0;

    switch (paramInt)
    {
    case -7:
    case -6:
    case -5:
    case 2:
    case 3:
    case 4:
    case 5:
    case 6:
    case 7:
    case 8:
      i = 6;
      break;
    case 100:
      i = 100;
      break;
    case 101:
      i = 101;
      break;
    case 999:
      i = 999;
      break;
    case 1:
      i = 96;
      break;
    case -15:
    case -9:
    case 12:
      i = 1;
      break;
    case -16:
    case -1:
      i = 8;
      break;
    case 91:
    case 92:
      i = 12;
      break;
    case -100:
    case 93:
      i = 180;
      break;
    case -101:
      i = 181;
      break;
    case -102:
      i = 231;
      break;
    case -103:
      i = 182;
      break;
    case -104:
      i = 183;
      break;
    case -3:
    case -2:
      i = 23;
      break;
    case -4:
      i = 24;
      break;
    case -8:
      i = 104;
      break;
    case 2004:
      i = 113;
      break;
    case 2005:
    case 2011:
      i = 112;
      break;
    case -13:
      i = 114;
      break;
    case -10:
      i = 102;
      break;
    case 2002:
    case 2003:
    case 2007:
    case 2008:
    case 2009:
      i = 109;
      break;
    case 2006:
      i = 111;
      break;
    case -14:
      i = 998;
      break;
    case 70:
      i = 1;
      break;
    case 0:
      i = 995;
      break;
    default:
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4, Integer.toString(paramInt));
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    return i;
  }

  void describe()
    throws SQLException
  {
    synchronized (this.connection)
    {
      ensureOpen();
      if (!this.described)
      {
        doDescribe(false);
      }
    }
  }

  void freeLine()
    throws SQLException
  {
    if (this.streamList != null)
    {
      while (this.nextStream != null)
      {
        try
        {
          this.nextStream.close();
        }
        catch (IOException localIOException)
        {
          SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localIOException);
          sqlexception.fillInStackTrace();
          throw sqlexception;
        }

        this.nextStream = this.nextStream.nextStream;
      }
    }
  }

  void closeUsedStreams(int paramInt)
    throws SQLException
  {
    while ((this.nextStream != null) && (this.nextStream.columnIndex < paramInt))
    {
      try
      {
        this.nextStream.close();
      }
      catch (IOException localIOException)
      {
        SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localIOException);
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }

      this.nextStream = this.nextStream.nextStream;
    }
  }

  final void ensureOpen()
    throws SQLException
  {
    SQLException sqlexception;
    if (this.connection.lifecycle != 1)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 8);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }
    if (this.closed)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }
  }

  void allocateTmpByteArray()
  {
  }

  public void setFetchDirection(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      if (paramInt == 1000)
      {
        this.defaultFetchDirection = paramInt;
      }
      else if ((paramInt == 1001) || (paramInt == 1002))
      {
        this.defaultFetchDirection = 1000;
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
    return this.defaultFetchDirection;
  }

  public void setFetchSize(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      setPrefetchInternal(paramInt, false, true);
    }
  }

  public int getFetchSize()
    throws SQLException
  {
    return getPrefetchInternal(true);
  }

  public int getResultSetConcurrency()
    throws SQLException
  {
    return ResultSetUtil.getUpdateConcurrency(this.userRsetType);
  }

  public int getResultSetType()
    throws SQLException
  {
    return ResultSetUtil.getScrollType(this.userRsetType);
  }

  public Connection getConnection()
    throws SQLException
  {
    return this.connection.getWrapper();
  }

  public void setResultSetCache(oracle.jdbc.OracleResultSetCache paramOracleResultSetCache)
    throws SQLException
  {
    synchronized (this.connection)
    {
      try
      {
        if (paramOracleResultSetCache == null)
        {
          SQLException sqlexception1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68);
          sqlexception1.fillInStackTrace();
          throw sqlexception1;
        }

        if (this.rsetCache != null) {
          this.rsetCache.close();
        }
        this.rsetCache = paramOracleResultSetCache;
      }
      catch (IOException localIOException)
      {
        SQLException sqlexception2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localIOException);
        sqlexception2.fillInStackTrace();
        throw sqlexception2;
      }
    }
  }

  public void setResultSetCache(OracleResultSetCache paramOracleResultSetCache)
    throws SQLException
  {
    synchronized (this.connection)
    {
      setResultSetCache(paramOracleResultSetCache);
    }
  }

  public OracleResultSetCache getResultSetCache()
    throws SQLException
  {
    synchronized (this.connection)
    {
      return (OracleResultSetCache)this.rsetCache;
    }
  }

  boolean isOracleBatchStyle()
  {
    return false;
  }

  void initBatch()
  {
  }

  int getBatchSize()
  {
    return this.m_batchItems.size();
  }

  void addBatchItem(String paramString)
  {
    this.m_batchItems.addElement(paramString);
  }

  String getBatchItem(int paramInt)
  {
    return (String)this.m_batchItems.elementAt(paramInt);
  }

  void clearBatchItems()
  {
    this.m_batchItems.removeAllElements();
  }

  void checkIfJdbcBatchExists()
    throws SQLException
  {
    if (getBatchSize() > 0)
    {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 81, "batch must be either executed or cleared");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }
  }

  public void addBatch(String paramString)
    throws SQLException
  {
    synchronized (this.connection)
    {
      addBatchItem(paramString);
    }
  }

  public void clearBatch()
    throws SQLException
  {
    synchronized (this.connection)
    {
      clearBatchItems();
    }
  }

  public int[] executeBatch()
    throws SQLException
  {
    synchronized (this.connection)
    {
      cleanOldTempLobs();
      int i = 0;
      int j = getBatchSize();

      if (j <= 0)
      {
        return new int[0];
      }

      int[] arrayOfInt = new int[j];

      ensureOpen();

      prepareForNewResults(true, true);

      int k = this.numberOfDefinePositions;
      String str = this.sqlObject.getOriginalSql();
      byte b = this.sqlKind;

      this.noMoreUpdateCounts = false;

      int m = 0;
      try
      {
        this.connection.registerHeartbeat();

        this.connection.needLine();

        for (i = 0; i < j; i++)
        {
          this.sqlObject.initialize(getBatchItem(i));

          this.sqlKind = this.sqlObject.getSqlKind();

          this.needToParse = true;
          this.numberOfDefinePositions = 0;

          this.rowsProcessed = 0;
          this.currentRank = 1;

          if (this.sqlKind == 1)
          {
            BatchUpdateException localBatchUpdateException1 = DatabaseError.createBatchUpdateException(80, "invalid SELECT batch command " + i, i, arrayOfInt);

            localBatchUpdateException1.fillInStackTrace();
            throw localBatchUpdateException1;
          }

          if (!this.isOpen)
          {
            this.connection.open(this);

            this.isOpen = true;
          }

          int n = -1;
          try
          {
            if (this.queryTimeout != 0) {
              this.connection.getTimeout().setTimeout(this.queryTimeout * 1000, this);
            }
            this.isExecuting = true;

            executeForRows(false);

            if (this.validRows > 0) {
              m += this.validRows;
            }
            n = this.validRows;
          }
          catch (SQLException sqlexception2)
          {
            this.needToParse = true;
            resetCurrentRowBinders();
            throw sqlexception2;
          }
          finally
          {
            if (this.queryTimeout != 0) {
              this.connection.getTimeout().cancelTimeout();
            }
            this.validRows = m;

            checkValidRowsStatus();

            this.isExecuting = false;
          }

          arrayOfInt[i] = n;

          if (arrayOfInt[i] < 0)
          {
            BatchUpdateException localBatchUpdateException2 = DatabaseError.createBatchUpdateException(81, "command return value " + arrayOfInt[i], i, arrayOfInt);

            localBatchUpdateException2.fillInStackTrace();
            throw localBatchUpdateException2;
          }
        }

      }
      catch (SQLException sqlexception1)
      {
        if ((sqlexception1 instanceof BatchUpdateException))
        {
          throw sqlexception1;
        }

        BatchUpdateException localBatchUpdateException2 = DatabaseError.createBatchUpdateException(81, sqlexception1.getMessage(), i, arrayOfInt);
        localBatchUpdateException2.fillInStackTrace();
        throw localBatchUpdateException2;
      }
      finally
      {
        clearBatchItems();

        this.numberOfDefinePositions = k;

        if (str != null)
        {
          this.sqlObject.initialize(str);

          this.sqlKind = b;
        }

        this.currentRank = 0;
      }

      this.connection.registerHeartbeat();

      return arrayOfInt;
    }
  }

  public int copyBinds(Statement paramStatement, int paramInt)
    throws SQLException
  {
    return 0;
  }

  public void notifyCloseRset()
    throws SQLException
  {
    this.scrollRset = null;
    endOfResultSet(false);
  }

  public String getOriginalSql()
    throws SQLException
  {
    return this.sqlObject.getOriginalSql();
  }

  void doScrollExecuteCommon()
    throws SQLException
  {
    if (this.scrollRset != null)
    {
      this.scrollRset.close();

      this.scrollRset = null;
    }

    if (this.sqlKind != 1)
    {
      doExecuteWithTimeout();

      return;
    }

    if (!this.needToAddIdentifier)
    {
      doExecuteWithTimeout();

      this.currentResultSet = new OracleResultSetImpl(this.connection, this);
      this.realRsetType = this.userRsetType;
    }
    else
    {
      try
      {
        this.sqlObject.setIncludeRowid(true);

        this.needToParse = true;

        prepareForNewResults(true, false);

        if (this.columnsDefinedByUser)
        {
          Accessor[] arrayOfAccessor = this.accessors;

          if ((this.accessors == null) || (this.accessors.length <= this.numberOfDefinePositions)) {
            this.accessors = new Accessor[this.numberOfDefinePositions + 1];
          }
          if (arrayOfAccessor != null) {
            for (int i = this.numberOfDefinePositions; i > 0; i--)
            {
              Accessor localAccessor = arrayOfAccessor[(i - 1)];

              this.accessors[i] = localAccessor;

              if (localAccessor.isColumnNumberAware)
              {
                localAccessor.updateColumnNumber(i);
              }
            }
          }

          allocateRowidAccessor();

          this.numberOfDefinePositions += 1;
        }

        doExecuteWithTimeout();

        this.currentResultSet = new OracleResultSetImpl(this.connection, this);
        this.realRsetType = this.userRsetType;
      }
      catch (SQLException sqlexception)
      {
        int i;
        Accessor localAccessor;
        if (this.userRsetType > 3)
          this.realRsetType = 3;
        else {
          this.realRsetType = 1;
        }
        this.sqlObject.setIncludeRowid(false);

        this.needToParse = true;

        prepareForNewResults(true, false);

        if (this.columnsDefinedByUser)
        {
          this.needToPrepareDefineBuffer = true;
          this.numberOfDefinePositions -= 1;

          System.arraycopy(this.accessors, 1, this.accessors, 0, this.numberOfDefinePositions);

          this.accessors[this.numberOfDefinePositions] = null;

          for (i = 0; i < this.numberOfDefinePositions; i++)
          {
            localAccessor = this.accessors[i];

            if (localAccessor.isColumnNumberAware)
            {
              localAccessor.updateColumnNumber(i);
            }
          }
        }
        doExecuteWithTimeout();

        this.currentResultSet = new OracleResultSetImpl(this.connection, this);
        this.sqlWarning = DatabaseError.addSqlWarning(this.sqlWarning, 91, sqlexception.getMessage());
      }

    }

    this.scrollRset = ResultSetUtil.createScrollResultSet(this, this.currentResultSet, this.realRsetType);
  }

  void allocateRowidAccessor()
    throws SQLException
  {
    this.accessors[0] = new RowidAccessor(this, 128, (short)1, -8, false);
  }

  OracleResultSet doScrollStmtExecuteQuery()
    throws SQLException
  {
    doScrollExecuteCommon();

    return this.scrollRset;
  }

  void processDmlReturningBind()
    throws SQLException
  {
    if (this.returnResultSet != null) this.returnResultSet.close();

    this.returnParamsFetched = false;
    this.returnParamRowBytes = 0;
    this.returnParamRowChars = 0;

    int i = 0;
    for (int j = 0; j < this.numberOfBindPositions; j++)
    {
      Accessor localAccessor = this.returnParamAccessors[j];

      if (localAccessor != null)
      {
        i++;

        if (localAccessor.charLength > 0)
        {
          this.returnParamRowChars += localAccessor.charLength;
        }
        else
        {
          this.returnParamRowBytes += localAccessor.byteLength;
        }
      }
    }

    if (this.isAutoGeneratedKey)
    {
      this.numReturnParams = i;
    }
    else
    {
      if (this.numReturnParams <= 0) {
        this.numReturnParams = this.sqlObject.getReturnParameterCount();
      }
      if (this.numReturnParams != i)
      {
        SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 173);
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }

    }

    this.returnParamMeta[0] = this.numReturnParams;
    this.returnParamMeta[1] = this.returnParamRowBytes;
    this.returnParamMeta[2] = this.returnParamRowChars;
  }

  void allocateDmlReturnStorage()
  {
    if (this.rowsDmlReturned == 0) return;

    int i = this.returnParamRowBytes * this.rowsDmlReturned;
    int j = this.returnParamRowChars * this.rowsDmlReturned;
    int k = 2 * this.numReturnParams * this.rowsDmlReturned;

    this.returnParamBytes = new byte[i];
    this.returnParamChars = new char[j];
    this.returnParamIndicators = new short[k];

    for (int m = 0; m < this.numberOfBindPositions; m++)
    {
      Accessor localAccessor = this.returnParamAccessors[m];

      if ((localAccessor != null) && ((localAccessor.internalType == 111) || (localAccessor.internalType == 109)))
      {
        TypeAccessor localTypeAccessor = (TypeAccessor)localAccessor;
        if ((localTypeAccessor.pickledBytes == null) || (localTypeAccessor.pickledBytes.length < this.rowsDmlReturned))
        {
          localTypeAccessor.pickledBytes = new byte[this.rowsDmlReturned][];
        }
      }
    }
  }

  void fetchDmlReturnParams()
    throws SQLException
  {
    SQLException sqlexception = DatabaseError.createUnsupportedFeatureSqlException();
    sqlexception.fillInStackTrace();
    throw sqlexception;
  }

  void setupReturnParamAccessors()
  {
    if (this.rowsDmlReturned == 0) return;

    int i = 0;
    int j = 0;
    int k = 0;
    int m = this.numReturnParams * this.rowsDmlReturned;

    for (int n = 0; n < this.numberOfBindPositions; n++)
    {
      Accessor localAccessor = this.returnParamAccessors[n];

      if (localAccessor != null)
      {
        if (localAccessor.charLength > 0)
        {
          localAccessor.rowSpaceChar = this.returnParamChars;
          localAccessor.columnIndex = j;
          j += this.rowsDmlReturned * localAccessor.charLength;
        }
        else
        {
          localAccessor.rowSpaceByte = this.returnParamBytes;
          localAccessor.columnIndex = i;
          i += this.rowsDmlReturned * localAccessor.byteLength;
        }

        localAccessor.rowSpaceIndicator = this.returnParamIndicators;
        localAccessor.indicatorIndex = k;
        k += this.rowsDmlReturned;
        localAccessor.lengthIndex = m;
        m += this.rowsDmlReturned;
      }
    }
  }

  void registerReturnParameterInternal(int paramInt1, int paramInt2, int paramInt3, int paramInt4, short paramShort, String paramString)
    throws SQLException
  {
    if (this.returnParamAccessors == null) {
      this.returnParamAccessors = new Accessor[this.numberOfBindPositions];
    }
    if (this.returnParamMeta == null)
    {
      this.returnParamMeta = new int[3 + this.numberOfBindPositions * 3];
    }

    switch (paramInt3) {
    case -16:
    case -15:
    case -9:
    case 2011:
      paramShort = 2;
      break;
    case 2009:
      paramString = "SYS.SQLXML";
      break;
    case -8:
    }

    Accessor localAccessor = allocateAccessor(paramInt2, paramInt3, paramInt1 + 1, paramInt4, paramShort, paramString, true);

    localAccessor.isDMLReturnedParam = true;
    this.returnParamAccessors[paramInt1] = localAccessor;

    int i = localAccessor.charLength > 0 ? 1 : 0;

    this.returnParamMeta[(3 + paramInt1 * 3 + 0)] = localAccessor.defineType;

    this.returnParamMeta[(3 + paramInt1 * 3 + 1)] = (i != 0 ? 1 : 0);

    this.returnParamMeta[(3 + paramInt1 * 3 + 2)] = (i != 0 ? localAccessor.charLength : localAccessor.byteLength);
  }

  /** @deprecated */
  public int creationState()
  {
    synchronized (this.connection)
    {
      return this.creationState;
    }
  }

  public boolean isColumnSetNull(int paramInt)
  {
    return this.columnSetNull;
  }

  public boolean isNCHAR(int paramInt)
    throws SQLException
  {
    if (!this.described) {
      describe();
    }
    int i = paramInt - 1;
    if ((i < 0) || (i >= this.numberOfDefinePositions))
    {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    boolean bool = this.accessors[i].formOfUse == 2;

    return bool;
  }

  void addChild(OracleStatement paramOracleStatement)
  {
    paramOracleStatement.nextChild = this.children;
    this.children = paramOracleStatement;
    paramOracleStatement.parent = this;
  }

  void removeChild(OracleStatement paramOracleStatement)
  {
    if (paramOracleStatement == this.children) {
      this.children = paramOracleStatement.nextChild;
    }
    else {
      OracleStatement localOracleStatement = this.children;
      while (localOracleStatement.nextChild != paramOracleStatement) {
        localOracleStatement = localOracleStatement.nextChild;
      }
      localOracleStatement.nextChild = paramOracleStatement.nextChild;
    }
    paramOracleStatement.parent = null;
    paramOracleStatement.nextChild = null;
  }

  public boolean getMoreResults(int paramInt)
    throws SQLException
  {
    SQLException sqlexception = DatabaseError.createUnsupportedFeatureSqlException();
    sqlexception.fillInStackTrace();
    throw sqlexception;
  }

  public ResultSet getGeneratedKeys()
    throws SQLException
  {
    SQLException sqlexception;
    if (this.closed)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    if (!this.isAutoGeneratedKey)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 90);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    if ((this.returnParamAccessors == null) || (this.numReturnParams == 0))
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 144);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    if (this.returnResultSet == null)
    {
      this.returnResultSet = new OracleReturnResultSet(this);
    }

    return this.returnResultSet;
  }

  public int executeUpdate(String paramString, int paramInt)
    throws SQLException
  {
    this.autoKeyInfo = new AutoKeyInfo(paramString);
    if ((paramInt == 2) || (!this.autoKeyInfo.isInsertSqlStmt()))
    {
      this.autoKeyInfo = null;
      return executeUpdate(paramString);
    }

    if (paramInt != 1)
    {
      this.autoKeyInfo = null;

      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    synchronized (this.connection) {
      this.isAutoGeneratedKey = true;
      String str = this.autoKeyInfo.getNewSql();
      this.numberOfBindPositions = 1;

      autoKeyRegisterReturnParams();

      processDmlReturningBind();

      return executeUpdateInternal(str);
    }
  }

  public int executeUpdate(String paramString, int[] paramArrayOfInt)
    throws SQLException
  {
    if ((paramArrayOfInt == null) || (paramArrayOfInt.length == 0))
    {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    this.autoKeyInfo = new AutoKeyInfo(paramString, paramArrayOfInt);
    if (!this.autoKeyInfo.isInsertSqlStmt())
    {
      this.autoKeyInfo = null;
      return executeUpdate(paramString);
    }

    synchronized (this.connection) {
      this.isAutoGeneratedKey = true;

      this.connection.doDescribeTable(this.autoKeyInfo);

      String str = this.autoKeyInfo.getNewSql();
      this.numberOfBindPositions = paramArrayOfInt.length;

      autoKeyRegisterReturnParams();

      processDmlReturningBind();

      return executeUpdateInternal(str);
    }
  }

  public int executeUpdate(String paramString, String[] paramArrayOfString)
    throws SQLException
  {
    if ((paramArrayOfString == null) || (paramArrayOfString.length == 0))
    {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    this.autoKeyInfo = new AutoKeyInfo(paramString, paramArrayOfString);
    if (!this.autoKeyInfo.isInsertSqlStmt())
    {
      this.autoKeyInfo = null;
      return executeUpdate(paramString);
    }

    synchronized (this.connection) {
      this.isAutoGeneratedKey = true;

      this.connection.doDescribeTable(this.autoKeyInfo);

      String str = this.autoKeyInfo.getNewSql();
      this.numberOfBindPositions = paramArrayOfString.length;

      autoKeyRegisterReturnParams();

      processDmlReturningBind();

      return executeUpdateInternal(str);
    }
  }

  public boolean execute(String paramString, int paramInt)
    throws SQLException
  {
    this.autoKeyInfo = new AutoKeyInfo(paramString);
    if ((paramInt == 2) || (!this.autoKeyInfo.isInsertSqlStmt()))
    {
      this.autoKeyInfo = null;
      return execute(paramString);
    }

    if (paramInt != 1)
    {
      this.autoKeyInfo = null;

      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    synchronized (this.connection) {
      this.isAutoGeneratedKey = true;
      String str = this.autoKeyInfo.getNewSql();
      this.numberOfBindPositions = 1;

      autoKeyRegisterReturnParams();

      processDmlReturningBind();

      return executeInternal(str);
    }
  }

  public boolean execute(String paramString, int[] paramArrayOfInt)
    throws SQLException
  {
    if ((paramArrayOfInt == null) || (paramArrayOfInt.length == 0))
    {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    this.autoKeyInfo = new AutoKeyInfo(paramString, paramArrayOfInt);
    if (!this.autoKeyInfo.isInsertSqlStmt())
    {
      this.autoKeyInfo = null;
      return execute(paramString);
    }

    synchronized (this.connection) {
      this.isAutoGeneratedKey = true;

      this.connection.doDescribeTable(this.autoKeyInfo);

      String str = this.autoKeyInfo.getNewSql();
      this.numberOfBindPositions = paramArrayOfInt.length;

      autoKeyRegisterReturnParams();

      processDmlReturningBind();

      return executeInternal(str);
    }
  }

  public boolean execute(String paramString, String[] paramArrayOfString)
    throws SQLException
  {
    if ((paramArrayOfString == null) || (paramArrayOfString.length == 0))
    {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    this.autoKeyInfo = new AutoKeyInfo(paramString, paramArrayOfString);
    if (!this.autoKeyInfo.isInsertSqlStmt())
    {
      this.autoKeyInfo = null;
      return execute(paramString);
    }

    synchronized (this.connection) {
      this.isAutoGeneratedKey = true;

      this.connection.doDescribeTable(this.autoKeyInfo);

      String str = this.autoKeyInfo.getNewSql();
      this.numberOfBindPositions = paramArrayOfString.length;

      autoKeyRegisterReturnParams();

      processDmlReturningBind();

      return executeInternal(str);
    }
  }

  public int getResultSetHoldability()
    throws SQLException
  {
    return 1;
  }

  public int getcacheState()
  {
    return this.cacheState;
  }

  public int getstatementType()
  {
    return this.statementType;
  }

  public boolean getserverCursor()
  {
    return this.serverCursor;
  }

  void initializeIndicatorSubRange()
  {
    this.bindIndicatorSubRange = 0;
  }

  private void autoKeyRegisterReturnParams()
    throws SQLException
  {
    initializeIndicatorSubRange();

    int i = this.bindIndicatorSubRange + 5 + this.numberOfBindPositions * 10;

    int j = i + 2 * this.numberOfBindPositions;

    this.bindIndicators = new short[j];

    int k = this.bindIndicatorSubRange;

    this.bindIndicators[(k + 0)] = ((short)this.numberOfBindPositions);

    this.bindIndicators[(k + 1)] = 0;

    this.bindIndicators[(k + 2)] = 1;

    this.bindIndicators[(k + 3)] = 0;

    this.bindIndicators[(k + 4)] = 1;

    k += 5;

    short[] arrayOfShort = this.autoKeyInfo.tableFormOfUses;
    int[] arrayOfInt = this.autoKeyInfo.columnIndexes;

    for (int m = 0; m < this.numberOfBindPositions; m++)
    {
      this.bindIndicators[(k + 0)] = 994;

      short s = (short)(this.connection.defaultnchar ? 2 : 1);

      if ((arrayOfShort != null) && (arrayOfInt != null))
      {
        if (arrayOfShort[(arrayOfInt[m] - 1)] == 2)
        {
          s = 2;
          this.bindIndicators[(k + 9)] = s;
        }

      }

      k += 10;

      checkTypeForAutoKey(this.autoKeyInfo.returnTypes[m]);

      String str = null;
      if (this.autoKeyInfo.returnTypes[m] == 111) {
        str = this.autoKeyInfo.tableTypeNames[(arrayOfInt[m] - 1)];
      }

      registerReturnParameterInternal(m, this.autoKeyInfo.returnTypes[m], this.autoKeyInfo.returnTypes[m], -1, s, str);
    }
  }

  private final void setNonAutoKey()
  {
    this.isAutoGeneratedKey = false;
    this.numberOfBindPositions = 0;
    this.bindIndicators = null;
    this.returnParamMeta = null;
  }

  void saveDefineBuffersIfRequired(char[] paramArrayOfChar, byte[] paramArrayOfByte, short[] paramArrayOfShort, boolean paramBoolean)
    throws SQLException
  {
    if (paramArrayOfChar != this.defineChars) this.connection.cacheBuffer(paramArrayOfChar);
    if (paramArrayOfByte != this.defineBytes) this.connection.cacheBuffer(paramArrayOfByte);
  }

  final void checkTypeForAutoKey(int paramInt)
    throws SQLException
  {
    if (paramInt == 109)
    {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 5);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }
  }

  void moveTempLobsToFree(CLOB paramCLOB)
  {
    int i;
    if ((this.oldTempClobsToFree != null) && 
      ((i = this.oldTempClobsToFree.indexOf(paramCLOB)) != -1))
    {
      addToTempLobsToFree(paramCLOB);
      this.oldTempClobsToFree.remove(i);
    }
  }

  void moveTempLobsToFree(BLOB paramBLOB)
  {
    int i;
    if ((this.oldTempBlobsToFree != null) && 
      ((i = this.oldTempBlobsToFree.indexOf(paramBLOB)) != -1))
    {
      addToTempLobsToFree(paramBLOB);
      this.oldTempBlobsToFree.remove(i);
    }
  }

  void addToTempLobsToFree(CLOB paramCLOB)
  {
    if (this.tempClobsToFree == null)
      this.tempClobsToFree = new ArrayList();
    this.tempClobsToFree.add(paramCLOB);
  }

  void addToTempLobsToFree(BLOB paramBLOB)
  {
    if (this.tempBlobsToFree == null)
      this.tempBlobsToFree = new ArrayList();
    this.tempBlobsToFree.add(paramBLOB);
  }

  void addToOldTempLobsToFree(CLOB paramCLOB)
  {
    if (this.oldTempClobsToFree == null)
      this.oldTempClobsToFree = new ArrayList();
    this.oldTempClobsToFree.add(paramCLOB);
  }

  void addToOldTempLobsToFree(BLOB paramBLOB)
  {
    if (this.oldTempBlobsToFree == null)
      this.oldTempBlobsToFree = new ArrayList();
    this.oldTempBlobsToFree.add(paramBLOB);
  }

  void cleanAllTempLobs()
  {
    cleanTempClobs(this.tempClobsToFree);
    this.tempClobsToFree = null;
    cleanTempBlobs(this.tempBlobsToFree);
    this.tempBlobsToFree = null;
    cleanTempClobs(this.oldTempClobsToFree);
    this.oldTempClobsToFree = null;
    cleanTempBlobs(this.oldTempBlobsToFree);
    this.oldTempBlobsToFree = null;
  }

  void cleanOldTempLobs()
  {
    cleanTempClobs(this.oldTempClobsToFree);
    cleanTempBlobs(this.oldTempBlobsToFree);
    this.oldTempClobsToFree = this.tempClobsToFree;
    this.tempClobsToFree = null;
    this.oldTempBlobsToFree = this.tempBlobsToFree;
    this.tempBlobsToFree = null;
  }

  void cleanTempClobs(ArrayList paramArrayList)
  {
    if (paramArrayList != null)
    {
      Iterator localIterator = paramArrayList.iterator();

      while (localIterator.hasNext())
      {
        try
        {
          ((CLOB)localIterator.next()).freeTemporary();
        }
        catch (SQLException sqlexception)
        {
        }
      }
    }
  }

  void cleanTempBlobs(ArrayList paramArrayList)
  {
    if (paramArrayList != null)
    {
      Iterator localIterator = paramArrayList.iterator();

      while (localIterator.hasNext())
      {
        try
        {
          ((BLOB)localIterator.next()).freeTemporary();
        }
        catch (SQLException sqlexception)
        {
        }
      }
    }
  }

  TimeZone getDefaultTimeZone()
    throws SQLException
  {
    return getDefaultTimeZone(false);
  }

  TimeZone getDefaultTimeZone(boolean paramBoolean)
    throws SQLException
  {
    if (this.defaultTimeZone == null)
    {
      try {
        this.defaultTimeZone = this.connection.getDefaultTimeZone();
      }
      catch (SQLException sqlexception)
      {
      }
      if (this.defaultTimeZone == null) {
        this.defaultTimeZone = TimeZone.getDefault();
      }
    }
    return this.defaultTimeZone;
  }

  public void setDatabaseChangeRegistration(DatabaseChangeRegistration paramDatabaseChangeRegistration)
    throws SQLException
  {
    this.registration = ((NTFDCNRegistration)paramDatabaseChangeRegistration);
  }

  public String[] getRegisteredTableNames() throws SQLException
  {
    return this.dcnTableName;
  }

  public long getRegisteredQueryId() throws SQLException {
    return this.dcnQueryId;
  }

  Calendar getDefaultCalendar()
    throws SQLException
  {
    if (this.defaultCalendar == null)
    {
      this.defaultCalendar = Calendar.getInstance(getDefaultTimeZone());
    }

    return this.defaultCalendar;
  }

  void releaseBuffers()
  {
    this.cachedDefineIndicatorSize = (this.defineIndicators != null ? this.defineIndicators.length : 0);
    this.cachedDefineMetaDataSize = (this.defineMetaData != null ? this.defineMetaData.length : 0);
    this.connection.cacheBuffer(this.defineChars);
    this.defineChars = null;
    this.connection.cacheBuffer(this.defineBytes);
    this.defineBytes = null;
    this.defineIndicators = null;
    this.defineMetaData = null;
  }

  public boolean isClosed()
    throws SQLException
  {
    return false;
  }

  public boolean isPoolable()
    throws SQLException
  {
    if (this.closed)
    {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    return this.cacheState != 3;
  }

  public void setPoolable(boolean paramBoolean)
    throws SQLException
  {
    if (this.closed)
    {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    if (paramBoolean) {
      this.cacheState = 1;
    }
    else
      this.cacheState = 3;
  }

  public boolean isWrapperFor(Class<?> paramClass)
    throws SQLException
  {
    if (paramClass.isInterface()) return paramClass.isInstance(this);

    SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 177);
    sqlexception.fillInStackTrace();
    throw sqlexception;
  }

  public <T> T unwrap(Class<T> paramClass)
    throws SQLException
  {
    if ((paramClass.isInterface()) && (paramClass.isInstance(this))) return (T)this;

    SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 177);
    sqlexception.fillInStackTrace();
    throw sqlexception;
  }

  protected OracleConnection getConnectionDuringExceptionHandling()
  {
    return this.connection;
  }

  Calendar getGMTCalendar()
  {
    if (this.gmtCalendar == null)
    {
      this.gmtCalendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"), Locale.US);
    }

    return this.gmtCalendar;
  }

  void extractNioDefineBuffers(int paramInt)
    throws SQLException
  {
  }

  void processLobPrefetchMetaData(Object[] paramArrayOfObject)
  {
  }

  void internalClose()
    throws SQLException
  {
    this.closed = true;
    if (this.currentResultSet != null) {
      this.currentResultSet.closed = true;
    }
    cleanupDefines();
    this.bindBytes = null;
    this.bindChars = null;
    this.bindIndicators = null;

    this.outBindAccessors = null;
    this.parameterStream = ((InputStream[][])null);
    this.userStream = ((Object[][])null);

    this.ibtBindBytes = null;
    this.ibtBindChars = null;
    this.ibtBindIndicators = null;

    this.lobPrefetchMetaData = null;
    this.tmpByteArray = null;

    this.definedColumnType = null;
    this.definedColumnSize = null;
    this.definedColumnFormOfUse = null;

    if (this.wrapper != null)
      this.wrapper.close();
  }
}