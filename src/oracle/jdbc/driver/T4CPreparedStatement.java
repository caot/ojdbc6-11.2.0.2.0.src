package oracle.jdbc.driver;

import java.io.IOException;
import java.sql.SQLException;

class T4CPreparedStatement extends OraclePreparedStatement
{
  static final byte[] EMPTY_BYTE = new byte[0];
  T4CConnection t4Connection;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  T4CPreparedStatement(PhysicalConnection paramPhysicalConnection, String paramString, int paramInt1, int paramInt2)
    throws SQLException
  {
    super(paramPhysicalConnection, paramString, paramPhysicalConnection.defaultExecuteBatch, paramPhysicalConnection.defaultRowPrefetch, paramInt1, paramInt2);

    this.nbPostPonedColumns = new int[1];
    this.nbPostPonedColumns[0] = 0;
    this.indexOfPostPonedColumn = new int[1][3];
    this.t4Connection = ((T4CConnection)paramPhysicalConnection);

    this.theRowidBinder = theStaticT4CRowidBinder;
    this.theRowidNullBinder = theStaticT4CRowidNullBinder;
    this.theURowidBinder = theStaticT4CURowidBinder;
    this.theURowidNullBinder = theStaticT4CURowidNullBinder;
  }

  void doOall8(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, boolean paramBoolean5)
    throws SQLException, IOException
  {
    if ((paramBoolean1) || (paramBoolean4) || (!paramBoolean2)) {
      this.oacdefSent = null;
    }
    this.t4Connection.assertLoggedOn("oracle.jdbc.driver.T4CPreparedStatement.doOall8");

    if ((this.sqlKind & 0xFFFFFFFF) == 0)
    {
      SQLException localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 439, "sqlKind = " + this.sqlKind);
      localSQLException1.fillInStackTrace();
      throw localSQLException1;
    }

    if (paramBoolean3) {
      this.rowPrefetchInLastFetch = this.rowPrefetch;
    }
    int i = this.numberOfDefinePositions;

    if ((this.sqlKind & 0x1E) != 0)
      i = 0;
    int j;
    if (this.accessors != null)
      for (j = 0; j < this.accessors.length; j++)
        if (this.accessors[j] != null)
          this.accessors[j].lastRowProcessed = 0;
    if (this.outBindAccessors != null)
      for (j = 0; j < this.outBindAccessors.length; j++)
        if (this.outBindAccessors[j] != null)
          this.outBindAccessors[j].lastRowProcessed = 0;
    if (this.returnParamAccessors != null) {
      for (j = 0; j < this.returnParamAccessors.length; j++) {
        if (this.returnParamAccessors[j] != null) {
          this.returnParamAccessors[j].lastRowProcessed = 0;
        }

      }

    }

    if (this.bindIndicators != null)
    {
      j = ((this.bindIndicators[(this.bindIndicatorSubRange + 3)] & 0xFFFF) << 16) + (this.bindIndicators[(this.bindIndicatorSubRange + 4)] & 0xFFFF);

      int k = 0;

      if (this.ibtBindChars != null) {
        k = this.ibtBindChars.length * this.connection.conversion.cMaxCharSize;
      }
      for (int m = 0; m < this.numberOfBindPositions; m++)
      {
        int n = this.bindIndicatorSubRange + 5 + 10 * m;

        int i1 = this.bindIndicators[(n + 2)] & 0xFFFF;

        if (i1 != 0)
        {
          int i2 = this.bindIndicators[(n + 9)] & 0xFFFF;

          if (i2 == 2)
          {
            k = Math.max(i1 * this.connection.conversion.maxNCharSize, k);
          }
          else
          {
            k = Math.max(i1 * this.connection.conversion.cMaxCharSize, k);
          }
        }

      }

      if (this.tmpBindsByteArray == null)
      {
        this.tmpBindsByteArray = new byte[k];
      }
      else if (this.tmpBindsByteArray.length < k)
      {
        this.tmpBindsByteArray = null;
        this.tmpBindsByteArray = new byte[k];
      }

    }
    else
    {
      this.tmpBindsByteArray = null;
    }

    int[] arrayOfInt1 = this.definedColumnType;
    int[] arrayOfInt2 = this.definedColumnSize;
    int[] arrayOfInt3 = this.definedColumnFormOfUse;

    if ((paramBoolean5) && (paramBoolean4) && (this.sqlObject.includeRowid))
    {
      arrayOfInt1 = new int[this.definedColumnType.length + 1];
      System.arraycopy(this.definedColumnType, 0, arrayOfInt1, 1, this.definedColumnType.length);
      arrayOfInt1[0] = -8;
      arrayOfInt2 = new int[this.definedColumnSize.length + 1];
      System.arraycopy(this.definedColumnSize, 0, arrayOfInt2, 1, this.definedColumnSize.length);
      arrayOfInt3 = new int[this.definedColumnFormOfUse.length + 1];
      System.arraycopy(this.definedColumnFormOfUse, 0, arrayOfInt3, 1, this.definedColumnFormOfUse.length);
    }

    allocateTmpByteArray();

    T4C8Oall localT4C8Oall = this.t4Connection.all8;

    this.t4Connection.sendPiggyBackedMessages();
    try
    {
      localT4C8Oall.doOALL(paramBoolean1, paramBoolean2, paramBoolean3, paramBoolean4, paramBoolean5, this.sqlKind, this.cursorId, this.sqlObject.getSqlBytes(this.processEscapes, this.convertNcharLiterals), this.rowPrefetch, this.outBindAccessors, this.numberOfBindPositions, this.accessors, i, this.bindBytes, this.bindChars, this.bindIndicators, this.bindIndicatorSubRange, this.connection.conversion, this.tmpBindsByteArray, this.parameterStream, this.parameterDatum, this.parameterOtype, this, this.ibtBindBytes, this.ibtBindChars, this.ibtBindIndicators, this.oacdefSent, arrayOfInt1, arrayOfInt2, arrayOfInt3, this.registration);

      this.cursorId = localT4C8Oall.getCursorId();
      this.oacdefSent = localT4C8Oall.oacdefBindsSent;
    }
    catch (SQLException localSQLException2)
    {
      this.cursorId = localT4C8Oall.getCursorId();

      if (localSQLException2.getErrorCode() == DatabaseError.getVendorCode(110))
      {
        this.sqlWarning = DatabaseError.addSqlWarning(this.sqlWarning, 110);
      }
      else
      {
        throw localSQLException2;
      }
    }
  }

  void allocateTmpByteArray()
  {
    if (this.tmpByteArray == null)
    {
      this.tmpByteArray = new byte[this.sizeTmpByteArray];
    }
    else if (this.sizeTmpByteArray > this.tmpByteArray.length)
    {
      this.tmpByteArray = new byte[this.sizeTmpByteArray];
    }
  }

  void releaseBuffers()
  {
    super.releaseBuffers();
    this.tmpByteArray = null;
    this.tmpBindsByteArray = null;

    this.t4Connection.all8.bindChars = null;
    this.t4Connection.all8.bindBytes = null;
    this.t4Connection.all8.tmpBindsByteArray = null;
  }

  void allocateRowidAccessor()
    throws SQLException
  {
    this.accessors[0] = new T4CRowidAccessor(this, 128, (short)1, -8, false, this.t4Connection.mare);
  }

  void reparseOnRedefineIfNeeded()
    throws SQLException
  {
    this.needToParse = true;
  }

  protected void defineColumnTypeInternal(int paramInt1, int paramInt2, int paramInt3, short paramShort, boolean paramBoolean, String paramString)
    throws SQLException
  {
    if (this.connection.disableDefinecolumntype)
      return;
    SQLException localSQLException;
    if (paramInt1 < 1)
    {
      localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if (paramBoolean)
    {
      if ((paramInt2 == 1) || (paramInt2 == 12)) {
        this.sqlWarning = DatabaseError.addSqlWarning(this.sqlWarning, 108);
      }

    }
    else if (paramInt3 < 0)
    {
      localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 53);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if ((this.currentResultSet != null) && (!this.currentResultSet.closed))
    {
      localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 28);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    int i = paramInt1 - 1;
    int[] arrayOfInt;
    if ((this.definedColumnType == null) || (this.definedColumnType.length <= i))
    {
      if (this.definedColumnType == null)
      {
        this.definedColumnType = new int[(i + 1) * 4];
      }
      else
      {
        arrayOfInt = new int[(i + 1) * 4];

        System.arraycopy(this.definedColumnType, 0, arrayOfInt, 0, this.definedColumnType.length);

        this.definedColumnType = arrayOfInt;
      }

    }

    this.definedColumnType[i] = paramInt2;

    if ((this.definedColumnSize == null) || (this.definedColumnSize.length <= i))
    {
      if (this.definedColumnSize == null) {
        this.definedColumnSize = new int[(i + 1) * 4];
      }
      else {
        arrayOfInt = new int[(i + 1) * 4];

        System.arraycopy(this.definedColumnSize, 0, arrayOfInt, 0, this.definedColumnSize.length);

        this.definedColumnSize = arrayOfInt;
      }
    }

    this.definedColumnSize[i] = paramInt3;

    if ((this.definedColumnFormOfUse == null) || (this.definedColumnFormOfUse.length <= i))
    {
      if (this.definedColumnFormOfUse == null) {
        this.definedColumnFormOfUse = new int[(i + 1) * 4];
      }
      else {
        arrayOfInt = new int[(i + 1) * 4];

        System.arraycopy(this.definedColumnFormOfUse, 0, arrayOfInt, 0, this.definedColumnFormOfUse.length);

        this.definedColumnFormOfUse = arrayOfInt;
      }
    }

    this.definedColumnFormOfUse[i] = paramShort;

    if ((this.accessors != null) && (i < this.accessors.length) && (this.accessors[i] != null))
    {
      this.accessors[i].definedColumnSize = paramInt3;

      if (((this.accessors[i].internalType == 96) || (this.accessors[i].internalType == 1)) && ((paramInt2 == 1) || (paramInt2 == 12)))
      {
        if (paramInt3 <= this.accessors[i].oacmxl)
        {
          this.needToPrepareDefineBuffer = true;
          this.columnsDefinedByUser = true;

          this.accessors[i].initForDataAccess(paramInt2, paramInt3, null);
          this.accessors[i].calculateSizeTmpByteArray();
        }
      }
    }
  }

  public void clearDefines()
    throws SQLException
  {
    synchronized (this.connection)
    {
      super.clearDefines();
      this.definedColumnType = null;
      this.definedColumnSize = null;
      this.definedColumnFormOfUse = null;
    }
  }

  void saveDefineBuffersIfRequired(char[] paramArrayOfChar, byte[] paramArrayOfByte, short[] paramArrayOfShort, boolean paramBoolean)
    throws SQLException
  {
    int i = this.rowPrefetchInLastFetch < this.rowPrefetch ? 1 : 0;

    if (paramBoolean)
    {
      paramArrayOfShort = new short[this.defineIndicators.length];
      int j = this.accessors[0].lengthIndexLastRow;
      int k = this.accessors[0].indicatorIndexLastRow;

      int i1 = i != 0 ? this.accessors.length : 1;

      for (; i != 0 ? i1 >= 1 : i1 <= this.accessors.length; 
        i1 += (i != 0 ? -1 : 1))
      {
        int m = j + this.rowPrefetchInLastFetch * i1 - 1;
        int n = k + this.rowPrefetchInLastFetch * i1 - 1;
        paramArrayOfShort[n] = this.defineIndicators[n];
        paramArrayOfShort[m] = this.defineIndicators[m];
      }

    }

    int j = i != 0 ? this.accessors.length - 1 : 0;

    for (; i != 0 ? j > -1 : j < this.accessors.length; 
      j += (i != 0 ? -1 : 1))
    {
      this.accessors[j].saveDataFromOldDefineBuffers(paramArrayOfByte, paramArrayOfChar, paramArrayOfShort, this.rowPrefetchInLastFetch != -1 ? this.rowPrefetchInLastFetch : this.rowPrefetch, this.rowPrefetch);
    }

    super.saveDefineBuffersIfRequired(paramArrayOfChar, paramArrayOfByte, paramArrayOfShort, paramBoolean);
  }

  Accessor allocateAccessor(int paramInt1, int paramInt2, int paramInt3, int paramInt4, short paramShort, String paramString, boolean paramBoolean)
    throws SQLException
  {
    Accessor localObject = null;
    SQLException localSQLException;
    switch (paramInt1)
    {
    case 96:
      localObject = new T4CCharAccessor(this, paramInt4, paramShort, paramInt2, paramBoolean, this.t4Connection.mare);

      break;
    case 8:
      if (!paramBoolean)
      {
        localObject = new T4CLongAccessor(this, paramInt3, paramInt4, paramShort, paramInt2, this.t4Connection.mare);
      }

      break;
    case 1:
      localObject = new T4CVarcharAccessor(this, paramInt4, paramShort, paramInt2, paramBoolean, this.t4Connection.mare);

      break;
    case 2:
      localObject = new T4CNumberAccessor(this, paramInt4, paramShort, paramInt2, paramBoolean, this.t4Connection.mare);

      break;
    case 6:
      localObject = new T4CVarnumAccessor(this, paramInt4, paramShort, paramInt2, paramBoolean, this.t4Connection.mare);

      break;
    case 24:
      if (!paramBoolean)
      {
        localObject = new T4CLongRawAccessor(this, paramInt3, paramInt4, paramShort, paramInt2, this.t4Connection.mare);
      }

      break;
    case 23:
      if ((paramBoolean) && (paramString != null))
      {
        localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 12, "sqlType=" + paramInt2);
        localSQLException.fillInStackTrace();
        throw localSQLException;
      }

      if (paramBoolean) {
        localObject = new T4COutRawAccessor(this, paramInt4, paramShort, paramInt2, this.t4Connection.mare);
      }
      else {
        localObject = new T4CRawAccessor(this, paramInt4, paramShort, paramInt2, paramBoolean, this.t4Connection.mare);
      }

      break;
    case 100:
      localObject = new T4CBinaryFloatAccessor(this, paramInt4, paramShort, paramInt2, paramBoolean, this.t4Connection.mare);

      break;
    case 101:
      localObject = new T4CBinaryDoubleAccessor(this, paramInt4, paramShort, paramInt2, paramBoolean, this.t4Connection.mare);

      break;
    case 104:
      if (this.sqlKind == 64)
      {
        localObject = new T4CVarcharAccessor(this, 18, paramShort, paramInt2, paramBoolean, this.t4Connection.mare);

        ((Accessor)localObject).definedColumnType = -8;
      }
      else {
        localObject = new T4CRowidAccessor(this, paramInt4, paramShort, paramInt2, paramBoolean, this.t4Connection.mare);
      }

      break;
    case 102:
      localObject = new T4CResultSetAccessor(this, paramInt4, paramShort, paramInt2, paramBoolean, this.t4Connection.mare);

      break;
    case 12:
      localObject = new T4CDateAccessor(this, paramInt4, paramShort, paramInt2, paramBoolean, this.t4Connection.mare);

      break;
    case 113:
      localObject = new T4CBlobAccessor(this, paramInt4, paramShort, paramInt2, paramBoolean, this.t4Connection.mare);

      break;
    case 112:
      localObject = new T4CClobAccessor(this, paramInt4, paramShort, paramInt2, paramBoolean, this.t4Connection.mare);

      break;
    case 114:
      localObject = new T4CBfileAccessor(this, paramInt4, paramShort, paramInt2, paramBoolean, this.t4Connection.mare);

      break;
    case 109:
      localObject = new T4CNamedTypeAccessor(this, paramString, paramShort, paramInt2, paramBoolean, this.t4Connection.mare);

      ((Accessor)localObject).initMetadata();

      break;
    case 111:
      localObject = new T4CRefTypeAccessor(this, paramString, paramShort, paramInt2, paramBoolean, this.t4Connection.mare);

      ((Accessor)localObject).initMetadata();

      break;
    case 180:
      localObject = new T4CTimestampAccessor(this, paramInt4, paramShort, paramInt2, paramBoolean, this.t4Connection.mare);

      break;
    case 181:
      localObject = new T4CTimestamptzAccessor(this, paramInt4, paramShort, paramInt2, paramBoolean, this.t4Connection.mare);

      break;
    case 231:
      localObject = new T4CTimestampltzAccessor(this, paramInt4, paramShort, paramInt2, paramBoolean, this.t4Connection.mare);

      break;
    case 182:
      localObject = new T4CIntervalymAccessor(this, paramInt4, paramShort, paramInt2, paramBoolean, this.t4Connection.mare);

      break;
    case 183:
      localObject = new T4CIntervaldsAccessor(this, paramInt4, paramShort, paramInt2, paramBoolean, this.t4Connection.mare);

      break;
    case 995:
      localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 89);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    return localObject;
  }

  void doDescribe(boolean paramBoolean)
    throws SQLException
  {
    if (!this.isOpen)
    {
      this.connection.open(this);
      this.isOpen = true;
    }

    try
    {
      this.t4Connection.needLine();
      this.t4Connection.sendPiggyBackedMessages();
      this.t4Connection.describe.doODNY(this, 0, this.accessors, this.sqlObject.getSqlBytes(this.processEscapes, this.convertNcharLiterals));
      this.accessors = this.t4Connection.describe.getAccessors();

      this.numberOfDefinePositions = this.t4Connection.describe.numuds;

      for (int i = 0; i < this.numberOfDefinePositions; i++)
        this.accessors[i].initMetadata();
    }
    catch (IOException localIOException)
    {
      ((T4CConnection)this.connection).handleIOException(localIOException);

      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localIOException);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.describedWithNames = true;
    this.described = true;
  }

  void executeForDescribe()
    throws SQLException
  {
    this.t4Connection.assertLoggedOn("oracle.jdbc.driver.T4CPreparedStatement.execute_for_describe");
    cleanOldTempLobs();
    try
    {
      if (this.t4Connection.useFetchSizeWithLongColumn)
      {
        doOall8(true, true, true, true, false);
      }
      else
      {
        doOall8(true, true, false, true, this.definedColumnType != null);
      }

    }
    catch (SQLException localSQLException1)
    {
      throw localSQLException1;
    }
    catch (IOException localIOException)
    {
      ((T4CConnection)this.connection).handleIOException(localIOException);

      SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localIOException);
      localSQLException2.fillInStackTrace();
      throw localSQLException2;
    }
    finally
    {
      this.rowsProcessed = this.t4Connection.all8.rowsProcessed;
      this.validRows = this.t4Connection.all8.getNumRows();
    }

    this.needToParse = false;

    this.implicitDefineForLobPrefetchDone = false;

    this.aFetchWasDoneDuringDescribe = false;
    if (this.t4Connection.all8.aFetchWasDone)
    {
      this.aFetchWasDoneDuringDescribe = true;
      this.rowPrefetchInLastFetch = this.rowPrefetch;
    }

    for (int i = 0; i < this.numberOfDefinePositions; i++) {
      this.accessors[i].initMetadata();
    }
    this.needToPrepareDefineBuffer = false;
  }

  void executeForRows(boolean paramBoolean)
    throws SQLException
  {
    try
    {
      try
      {
        boolean bool = false;
        if (this.columnsDefinedByUser) {
          this.needToPrepareDefineBuffer = false;
        }
        else if ((this.t4Connection.useLobPrefetch) && (this.accessors != null) && (this.defaultLobPrefetchSize != -1) && (!this.implicitDefineForLobPrefetchDone) && (!this.aFetchWasDoneDuringDescribe) && (this.definedColumnType == null))
        {
          int i = 0;
          int[] arrayOfInt1 = new int[this.accessors.length];
          int[] arrayOfInt2 = new int[this.accessors.length];

          for (int j = 0; j < this.accessors.length; j++)
          {
            arrayOfInt1[j] = getJDBCType(this.accessors[j].internalType);
            if ((this.accessors[j].internalType == 113) || (this.accessors[j].internalType == 112) || (this.accessors[j].internalType == 114))
            {
              i = 1;
              this.accessors[j].lobPrefetchSizeForThisColumn = this.defaultLobPrefetchSize;
              arrayOfInt2[j] = this.defaultLobPrefetchSize;
            }
          }

          if (i != 0)
          {
            this.definedColumnType = arrayOfInt1;
            this.definedColumnSize = arrayOfInt2;
            bool = true;
          }

        }

        doOall8(this.needToParse, !paramBoolean, true, false, bool);

        this.needToParse = false;
        if (bool)
          this.implicitDefineForLobPrefetchDone = true;
      }
      finally
      {
        this.validRows = this.t4Connection.all8.getNumRows();
      }
    }
    catch (SQLException localSQLException1)
    {
      throw localSQLException1;
    }
    catch (IOException localIOException)
    {
      ((T4CConnection)this.connection).handleIOException(localIOException);

      SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localIOException);
      localSQLException2.fillInStackTrace();
      throw localSQLException2;
    }
  }

  void fetch()
    throws SQLException
  {
    SQLException localSQLException;
    if (this.streamList != null)
    {
      while (this.nextStream != null)
      {
        try
        {
          this.nextStream.close();
        }
        catch (IOException localIOException1)
        {
          ((T4CConnection)this.connection).handleIOException(localIOException1);

          localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localIOException1);
          localSQLException.fillInStackTrace();
          throw localSQLException;
        }

        this.nextStream = this.nextStream.nextStream;
      }
    }

    try
    {
      doOall8(false, false, true, false, false);

      this.validRows = this.t4Connection.all8.getNumRows();
    }
    catch (IOException localIOException2)
    {
      ((T4CConnection)this.connection).handleIOException(localIOException2);

      localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localIOException2);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  void continueReadRow(int paramInt)
    throws SQLException
  {
    try
    {
      if (!this.connection.useFetchSizeWithLongColumn)
      {
        T4C8Oall localT4C8Oall = this.t4Connection.all8;

        localT4C8Oall.continueReadRow(paramInt, this);
      }
    }
    catch (IOException localIOException)
    {
      ((T4CConnection)this.connection).handleIOException(localIOException);

      SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localIOException);
      localSQLException2.fillInStackTrace();
      throw localSQLException2;
    }
    catch (SQLException localSQLException1)
    {
      if (localSQLException1.getErrorCode() == DatabaseError.getVendorCode(110))
      {
        this.sqlWarning = DatabaseError.addSqlWarning(this.sqlWarning, 110);
      }
      else
      {
        throw localSQLException1;
      }
    }
  }

  void doClose()
    throws SQLException
  {
    this.t4Connection.assertLoggedOn("oracle.jdbc.driver.T4CPreparedStatement.do_close");
    try
    {
      if (this.cursorId != 0)
      {
        this.t4Connection.cursorToClose[(this.t4Connection.cursorToCloseOffset++)] = this.cursorId;

        if (this.t4Connection.cursorToCloseOffset >= this.t4Connection.cursorToClose.length)
        {
          this.t4Connection.sendPiggyBackedMessages();
        }
      }
    }
    catch (IOException localIOException)
    {
      ((T4CConnection)this.connection).handleIOException(localIOException);

      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localIOException);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.tmpByteArray = null;
    this.tmpBindsByteArray = null;
    this.definedColumnType = null;
    this.definedColumnSize = null;
    this.definedColumnFormOfUse = null;
    this.oacdefSent = null;
  }

  void closeQuery()
    throws SQLException
  {
    this.t4Connection.assertLoggedOn("oracle.jdbc.driver.T4CPreparedStatement.closeQuery");

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
          ((T4CConnection)this.connection).handleIOException(localIOException);

          SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localIOException);
          localSQLException.fillInStackTrace();
          throw localSQLException;
        }

        this.nextStream = this.nextStream.nextStream;
      }
    }
  }

  Binder getRowidNullBinder(int paramInt)
  {
    if (this.sqlKind == 64)
    {
      this.currentRowCharLens[paramInt] = 1;
      return this.theVarcharNullBinder;
    }

    return this.theRowidNullBinder;
  }

  void doLocalInitialization()
  {
    super.doLocalInitialization();

    this.t4Connection.all8.bindChars = this.bindChars;
    this.t4Connection.all8.bindBytes = this.bindBytes;
  }
}