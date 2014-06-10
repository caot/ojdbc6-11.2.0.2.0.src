package oracle.jdbc.driver;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Vector;
import oracle.jdbc.internal.KeywordValue;
import oracle.jdbc.internal.OracleConnection;
import oracle.jdbc.oracore.OracleTypeADT;

final class T4C8Oall extends T4CTTIfun
{
  Vector<IOException> nonFatalIOExceptions = null;

  static final byte[] EMPTY_BYTES = new byte[0];
  static final int UOPF_PRS = 1;
  static final int UOPF_BND = 8;
  static final int UOPF_EXE = 32;
  static final int UOPF_FEX = 512;
  static final int UOPF_FCH = 64;
  static final int UOPF_CAN = 128;
  static final int UOPF_COM = 256;
  static final int UOPF_DSY = 8192;
  static final int UOPF_SIO = 1024;
  static final int UOPF_NPL = 32768;
  static final int UOPF_DFN = 16;
  static final int EXE_COMMIT_ON_SUCCESS = 1;
  static final int EXE_LEAVE_CUR_MAPPED = 2;
  static final int EXE_BATCH_DML_ERRORS = 4;
  static final int EXE_SCROL_READ_ONLY = 8;
  static final int AL8KW_MAXLANG = 63;
  static final int AL8KW_TIMEZONE = 163;
  static final int AL8KW_ERR_OVLAP = 164;
  static final int AL8KW_SESSION_ID = 165;
  static final int AL8KW_SERIAL_NUM = 166;
  static final int AL8KW_TAG_FOUND = 167;
  static final int AL8KW_SCHEMA_NAME = 168;
  static final int AL8KW_SCHEMA_ID = 169;
  static final int AL8KW_ENABLED_ROLES = 170;
  static final int AL8KW_AUX_SESSSTATE = 171;
  static final String[] NLS_KEYS = { "AUTH_NLS_LXCCURRENCY", "AUTH_NLS_LXCISOCURR", "AUTH_NLS_LXCNUMERICS", null, null, null, null, "AUTH_NLS_LXCDATEFM", "AUTH_NLS_LXCDATELANG", "AUTH_NLS_LXCTERRITORY", "SESSION_NLS_LXCCHARSET", "AUTH_NLS_LXCSORT", "AUTH_NLS_LXCCALENDAR", null, null, null, "AUTH_NLS_LXLAN", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "AUTH_NLS_LXCSORT", null, "AUTH_NLS_LXCUNIONCUR", null, null, null, null, "AUTH_NLS_LXCTIMEFM", "AUTH_NLS_LXCSTMPFM", "AUTH_NLS_LXCTTZNFM", "AUTH_NLS_LXCSTZNFM", "SESSION_NLS_LXCNLSLENSEM", "SESSION_NLS_LXCNCHAREXCP", "SESSION_NLS_LXCNCHARIMP" };
  static final int LDIREGIDFLAG = 120;
  static final int LDIREGIDSET = 181;
  static final int LDIMAXTIMEFIELD = 60;
  int rowsProcessed;
  int numberOfDefinePositions;
  long options;
  int cursor;
  byte[] sqlStmt = new byte[0];
  final long[] al8i4 = new long[13];

  boolean plsql = false;
  Accessor[] definesAccessors;
  int definesLength;
  Accessor[] outBindAccessors;
  int numberOfBindPositions;
  InputStream[][] parameterStream;
  byte[][][] parameterDatum;
  OracleTypeADT[][] parameterOtype;
  short[] bindIndicators;
  byte[] bindBytes;
  char[] bindChars;
  int bindIndicatorSubRange;
  byte[] tmpBindsByteArray;
  DBConversion conversion;
  byte[] ibtBindBytes;
  char[] ibtBindChars;
  short[] ibtBindIndicators;
  boolean sendBindsDefinition = false;
  OracleStatement oracleStatement;
  short dbCharSet;
  short NCharSet;
  T4CTTIrxd rxd;
  T4C8TTIrxh rxh;
  T4CTTIdcb dcb;
  byte typeOfStatement;
  int defCols = 0;
  int rowsToFetch;
  boolean aFetchWasDone = false;
  T4CTTIoac[] oacdefBindsSent;
  T4CTTIoac[] oacdefDefines;
  int[] definedColumnSize;
  int[] definedColumnType;
  int[] definedColumnFormOfUse;
  NTFDCNRegistration registration = null;

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  T4C8Oall(T4CConnection paramT4CConnection)
  {
    super(paramT4CConnection, (byte)3);

    setFunCode((short)94);
    this.rxh = new T4C8TTIrxh(paramT4CConnection);
    this.rxd = new T4CTTIrxd(paramT4CConnection);
    this.dcb = new T4CTTIdcb(paramT4CConnection);
  }

  void doOALL(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, boolean paramBoolean5, byte paramByte, int paramInt1, byte[] paramArrayOfByte1, int paramInt2, Accessor[] paramArrayOfAccessor1, int paramInt3, Accessor[] paramArrayOfAccessor2, int paramInt4, byte[] paramArrayOfByte2, char[] paramArrayOfChar1, short[] paramArrayOfShort1, int paramInt5, DBConversion paramDBConversion, byte[] paramArrayOfByte3, InputStream[][] paramArrayOfInputStream, byte[][][] paramArrayOfByte, OracleTypeADT[][] paramArrayOfOracleTypeADT, OracleStatement paramOracleStatement, byte[] paramArrayOfByte4, char[] paramArrayOfChar2, short[] paramArrayOfShort2, T4CTTIoac[] paramArrayOfT4CTTIoac, int[] paramArrayOfInt1, int[] paramArrayOfInt2, int[] paramArrayOfInt3, NTFDCNRegistration paramNTFDCNRegistration)
    throws SQLException, IOException
  {
    this.typeOfStatement = paramByte;
    this.cursor = paramInt1;
    this.sqlStmt = (paramBoolean1 ? paramArrayOfByte1 : EMPTY_BYTES);
    this.rowsToFetch = paramInt2;
    this.outBindAccessors = paramArrayOfAccessor1;
    this.numberOfBindPositions = paramInt3;
    this.definesAccessors = paramArrayOfAccessor2;
    this.definesLength = paramInt4;
    this.bindBytes = paramArrayOfByte2;
    this.bindChars = paramArrayOfChar1;
    this.bindIndicators = paramArrayOfShort1;
    this.bindIndicatorSubRange = paramInt5;
    this.conversion = paramDBConversion;
    this.tmpBindsByteArray = paramArrayOfByte3;
    this.parameterStream = paramArrayOfInputStream;
    this.parameterDatum = paramArrayOfByte;
    this.parameterOtype = paramArrayOfOracleTypeADT;
    this.oracleStatement = paramOracleStatement;
    this.ibtBindBytes = paramArrayOfByte4;
    this.ibtBindChars = paramArrayOfChar2;
    this.ibtBindIndicators = paramArrayOfShort2;
    this.oacdefBindsSent = paramArrayOfT4CTTIoac;
    this.definedColumnType = paramArrayOfInt1;
    this.definedColumnSize = paramArrayOfInt2;
    this.definedColumnFormOfUse = paramArrayOfInt3;
    this.registration = paramNTFDCNRegistration;

    this.dbCharSet = paramDBConversion.getServerCharSetId();
    this.NCharSet = paramDBConversion.getNCharSetId();

    int i = 0;
    if (this.bindIndicators != null)
      i = ((this.bindIndicators[(this.bindIndicatorSubRange + 3)] & 0xFFFF) << 16) + (this.bindIndicators[(this.bindIndicatorSubRange + 4)] & 0xFFFF);
    Object localObject;
    if (paramArrayOfByte1 == null)
    {
      localObject = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 431);
      ((SQLException)localObject).fillInStackTrace();
      throw ((Throwable)localObject);
    }

    if (((this.typeOfStatement & 0x1E) == 0) && (i > 1))
    {
      localObject = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 433);
      ((SQLException)localObject).fillInStackTrace();
      throw ((Throwable)localObject);
    }

    this.rowsProcessed = 0;
    this.options = 0L;
    this.plsql = ((this.typeOfStatement & 0x60) != 0);
    this.sendBindsDefinition = false;

    if (this.receiveState != 0)
    {
      this.receiveState = 0;
    }

    this.rxh.init();
    this.rxd.init();
    this.oer.init();

    if (paramBoolean5) {
      initDefinesDefinition();
    }
    if ((this.numberOfBindPositions > 0) && (this.bindIndicators != null))
    {
      if (this.oacdefBindsSent == null)
        this.oacdefBindsSent = new T4CTTIoac[this.numberOfBindPositions];
      this.sendBindsDefinition = initBindsDefinition(this.oacdefBindsSent);
    }

    this.options = setOptions(paramBoolean1, paramBoolean2, paramBoolean3, paramBoolean5);

    if ((this.options & 1L) > 0L)
      this.al8i4[0] = 1L;
    else {
      this.al8i4[0] = 0L;
    }

    if ((this.plsql) || (this.typeOfStatement == -128))
      this.al8i4[1] = 1L;
    else if (paramBoolean4)
    {
      if ((paramBoolean3) && (this.oracleStatement.connection.useFetchSizeWithLongColumn))
        this.al8i4[1] = this.rowsToFetch;
      else
        this.al8i4[1] = 0L;
    }
    else if ((this.typeOfStatement & 0x1E) != 0)
    {
      this.al8i4[1] = (i == 0 ? this.oracleStatement.batch : i);
    }
    else if ((paramBoolean3) && (!paramBoolean4))
    {
      this.al8i4[1] = this.rowsToFetch;
    }
    else this.al8i4[1] = 0L;

    if (this.typeOfStatement == 1)
      this.al8i4[7] = 1L;
    else {
      this.al8i4[7] = 0L;
    }
    this.rowsProcessed = 0;
    this.aFetchWasDone = false;

    this.rxd.setNumberOfColumns(this.definesLength);

    if (((this.options & 0x40) != 0L) && ((this.options & 0x20) == 0L) && ((this.options & 1L) == 0L) && ((this.options & 0x8) == 0L) && ((this.options & 0x10) == 0L) && (!this.oracleStatement.needToSendOalToFetch))
    {
      setFunCode((short)5);
    }
    else setFunCode((short)94);

    this.nonFatalIOExceptions = null;
    doRPC();

    this.ibtBindIndicators = null;
    this.ibtBindChars = null;
    this.ibtBindBytes = null;
    this.tmpBindsByteArray = null;
    this.outBindAccessors = null;
    this.bindBytes = null;
    this.bindChars = null;
    this.bindIndicators = null;
    this.oracleStatement = null;

    if (this.nonFatalIOExceptions != null)
    {
      localObject = (IOException)this.nonFatalIOExceptions.get(0);
      try
      {
        SQLException localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 266);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }
      catch (SQLException localSQLException2)
      {
        localSQLException2.initCause((Throwable)localObject);
        throw localSQLException2;
      }
    }
  }

  void readBVC()
    throws IOException, SQLException
  {
    int i = this.meg.unmarshalUB2();

    this.rxd.unmarshalBVC(i);
  }

  void readIOV()
    throws IOException, SQLException
  {
    T4CTTIiov localT4CTTIiov = new T4CTTIiov(this.connection, this.rxh, this.rxd);

    localT4CTTIiov.init();
    localT4CTTIiov.unmarshalV10();

    if ((this.oracleStatement.returnParamAccessors == null) && (!localT4CTTIiov.isIOVectorEmpty()))
    {
      byte[] arrayOfByte = localT4CTTIiov.getIOVector();

      this.outBindAccessors = localT4CTTIiov.processRXD(this.outBindAccessors, this.numberOfBindPositions, this.bindBytes, this.bindChars, this.bindIndicators, this.bindIndicatorSubRange, this.conversion, this.tmpBindsByteArray, arrayOfByte, this.parameterStream, this.parameterDatum, this.parameterOtype, this.oracleStatement, null, null, null);
    }
  }

  void readRXH()
    throws IOException, SQLException
  {
    this.rxh.init();
    this.rxh.unmarshalV10(this.rxd);
    SQLException localSQLException1;
    if (this.rxh.uacBufLength > 0)
    {
      localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 405);
      localSQLException1.fillInStackTrace();
      throw localSQLException1;
    }

    if ((this.rxh.rxhflg & 0x8) == 8)
    {
      localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 449);
      localSQLException1.fillInStackTrace();
      throw localSQLException1;
    }

    if ((this.rxh.rxhflg & 0x10) == 16)
    {
      for (int i = 0; i < this.definesAccessors.length; i++)
      {
        if ((this.definesAccessors[i].udskpos >= 0) && (this.definesAccessors[i].udskpos != i))
        {
          SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 450);
          localSQLException2.fillInStackTrace();
          throw localSQLException2;
        }
      }
    }
  }

  boolean readRXD()
    throws IOException, SQLException
  {
    this.aFetchWasDone = true;

    if ((this.oracleStatement.returnParamAccessors != null) && (this.numberOfBindPositions > 0))
    {
      int i = 0;
      for (int j = 0; j < this.oracleStatement.numberOfBindPositions; j++)
      {
        Accessor localAccessor = this.oracleStatement.returnParamAccessors[j];
        if (localAccessor != null)
        {
          int k = (int)this.meg.unmarshalUB4();
          if (i == 0)
          {
            this.oracleStatement.rowsDmlReturned = k;
            this.oracleStatement.allocateDmlReturnStorage();
            this.oracleStatement.setupReturnParamAccessors();
            i = 1;
          }

          for (int m = 0; m < k; m++)
          {
            localAccessor.unmarshalOneRow();
          }
        }
      }
      this.oracleStatement.returnParamsFetched = true;
    }
    else if ((this.iovProcessed) || ((this.outBindAccessors != null) && (this.definesAccessors == null)))
    {
      if (this.rxd.unmarshal(this.outBindAccessors, this.numberOfBindPositions))
      {
        return true;
      }

    }
    else if (this.rxd.unmarshal(this.definesAccessors, this.definesLength))
    {
      return true;
    }

    return false;
  }

  void readRPA()
    throws IOException, SQLException
  {
    int i = this.meg.unmarshalUB2();
    int[] arrayOfInt = new int[i];

    for (int j = 0; j < i; j++) {
      arrayOfInt[j] = ((int)this.meg.unmarshalUB4());
    }

    this.cursor = arrayOfInt[2];

    j = this.meg.unmarshalUB2();

    byte[] arrayOfByte1 = null;
    if (j > 0) {
      arrayOfByte1 = this.meg.unmarshalNBytes(j);
    }

    int k = this.meg.unmarshalUB2();

    KeywordValue[] arrayOfKeywordValue = new KeywordValue[k];
    for (String str1 = 0; str1 < k; str1++) {
      arrayOfKeywordValue[str1] = KeywordValueI.unmarshal(this.meg);
    }
    this.connection.updateSessionProperties(arrayOfKeywordValue);

    this.oracleStatement.dcnQueryId = -1L;
    this.oracleStatement.dcnTableName = null;
    if (this.connection.getTTCVersion() >= 4)
    {
      str1 = (int)this.meg.unmarshalUB4();
      byte[] arrayOfByte2 = this.meg.unmarshalNBytes(str1);
      if ((str1 > 0) && (this.registration != null))
      {
        int m = 0;
        Properties localProperties = this.registration.getRegistrationOptions();
        if (localProperties != null)
        {
          str2 = localProperties.getProperty("DCN_QUERY_CHANGE_NOTIFICATION");
          if ((str2 != null) && (str2.compareToIgnoreCase("true") == 0))
            m = 1;
        }
        String str2 = str1;
        int n;
        if (m != 0) {
          n = str1 - 8;
        }
        String str3 = new String(arrayOfByte2, 0, n);
        char[] arrayOfChar = { '\000' };
        String[] arrayOfString = str3.split(new String(arrayOfChar));
        this.registration.addTablesName(arrayOfString, arrayOfString.length);

        this.oracleStatement.dcnTableName = arrayOfString;

        if (m != 0)
        {
          int i1 = arrayOfByte2[(str1 - 1)] | arrayOfByte2[(str1 - 2)] << 8 | arrayOfByte2[(str1 - 3)] << 16 | arrayOfByte2[(str1 - 4)] << 24;

          int i2 = arrayOfByte2[(str1 - 5)] | arrayOfByte2[(str1 - 6)] << 8 | arrayOfByte2[(str1 - 7)] << 16 | arrayOfByte2[(str1 - 8)] << 24;

          long l = i2 & 0xFFFFFFFF | i1 << 32;
          this.oracleStatement.dcnQueryId = l;
        }
      }
    }
  }

  void readDCB()
    throws IOException, SQLException
  {
    this.dcb.init(this.oracleStatement, 0);

    this.definesAccessors = this.dcb.receive(this.definesAccessors);
    this.numberOfDefinePositions = this.dcb.numuds;
    this.definesLength = this.numberOfDefinePositions;

    this.rxd.setNumberOfColumns(this.numberOfDefinePositions);
  }

  void processError()
    throws SQLException
  {
    this.cursor = this.oer.currCursorID;

    this.rowsProcessed = this.oer.getCurRowNumber();

    if ((this.typeOfStatement == 1) && (this.oer.retCode == 1403))
    {
      this.aFetchWasDone = true;
    }
    if ((this.typeOfStatement != 1) || ((this.typeOfStatement == 1) && (this.oer.retCode != 1403)))
    {
      this.oer.processError(this.oracleStatement);
    }
  }

  int getCursorId()
  {
    return this.cursor;
  }

  void continueReadRow(int paramInt, OracleStatement paramOracleStatement)
    throws SQLException, IOException
  {
    try
    {
      this.oracleStatement = paramOracleStatement;

      this.receiveState = 2;

      if (this.rxd.unmarshal(this.definesAccessors, paramInt, this.definesLength))
      {
        this.receiveState = 3;
      }
      else
      {
        resumeReceive();
      }
    }
    finally {
      this.oracleStatement = null;
    }
  }

  int getNumRows()
  {
    int i = 0;

    if (this.receiveState == 3) {
      i = -2;
    }
    else {
      switch (this.typeOfStatement)
      {
      case -128:
      case 2:
      case 4:
      case 8:
      case 16:
      case 32:
      case 64:
        i = this.rowsProcessed;

        break;
      case 1:
        i = (this.definesAccessors != null) && (this.definesLength > 0) ? this.definesAccessors[0].lastRowProcessed : 0;
      }

    }

    return i;
  }

  void marshal()
    throws IOException
  {
    if (getFunCode() == 5)
    {
      this.meg.marshalSWORD(this.cursor);
      this.meg.marshalSWORD((int)this.al8i4[1]);
    }
    else
    {
      if (this.oracleStatement.needToSendOalToFetch) {
        this.oracleStatement.needToSendOalToFetch = false;
      }
      marshalPisdef();

      this.meg.marshalCHR(this.sqlStmt);

      this.meg.marshalUB4Array(this.al8i4);

      int[] arrayOfInt = new int[this.numberOfBindPositions];

      for (int i = 0; i < this.numberOfBindPositions; i++)
      {
        arrayOfInt[i] = this.oacdefBindsSent[i].oacmxl;
      }

      if (((this.options & 0x8) != 0L) && (this.numberOfBindPositions > 0) && (this.bindIndicators != null) && (this.sendBindsDefinition))
      {
        marshalBindsTypes(this.oacdefBindsSent);
      }

      if ((this.connection.getTTCVersion() >= 2) && ((this.options & 0x10) != 0L))
      {
        for (i = 0; i < this.defCols; i++) {
          this.oacdefDefines[i].marshal();
        }

      }

      if (((this.options & 0x20) != 0L) && (this.numberOfBindPositions > 0) && (this.bindIndicators != null))
      {
        this.nonFatalIOExceptions = marshalBinds(arrayOfInt);
      }
    }
  }

  void marshalPisdef()
    throws IOException
  {
    this.meg.marshalUB4(this.options);

    this.meg.marshalSWORD(this.cursor);

    if (this.sqlStmt.length == 0)
      this.meg.marshalNULLPTR();
    else {
      this.meg.marshalPTR();
    }

    this.meg.marshalSWORD(this.sqlStmt.length);

    if (this.al8i4.length == 0)
      this.meg.marshalNULLPTR();
    else {
      this.meg.marshalPTR();
    }

    this.meg.marshalSWORD(this.al8i4.length);

    this.meg.marshalNULLPTR();

    this.meg.marshalNULLPTR();

    if (((this.options & 0x40) == 0L) && ((this.options & 0x20) != 0L) && ((this.options & 1L) != 0L) && (this.typeOfStatement == 1))
    {
      this.meg.marshalUB4(9223372036854775807L);
      this.meg.marshalUB4(this.rowsToFetch);
    }
    else
    {
      this.meg.marshalUB4(0L);

      this.meg.marshalUB4(0L);
    }

    if ((this.typeOfStatement & 0x60) == 0)
      this.meg.marshalUB4(2147483647L);
    else {
      this.meg.marshalUB4(32760L);
    }

    if (((this.options & 0x8) != 0L) && (this.numberOfBindPositions > 0) && (this.sendBindsDefinition))
    {
      this.meg.marshalPTR();

      this.meg.marshalSWORD(this.numberOfBindPositions);
    }
    else
    {
      this.meg.marshalNULLPTR();

      this.meg.marshalSWORD(0);
    }

    this.meg.marshalNULLPTR();

    this.meg.marshalNULLPTR();

    this.meg.marshalNULLPTR();

    this.meg.marshalNULLPTR();

    this.meg.marshalNULLPTR();

    if (this.connection.getTTCVersion() >= 2)
    {
      if ((this.defCols > 0) && ((this.options & 0x10) != 0L))
      {
        this.meg.marshalPTR();

        this.meg.marshalSWORD(this.defCols);
      }
      else
      {
        this.meg.marshalNULLPTR();

        this.meg.marshalSWORD(0);
      }
    }

    if (this.connection.getTTCVersion() >= 4)
    {
      int i = 0;
      int j = 0;
      if (this.registration != null)
      {
        long l = this.registration.getRegId();
        i = (int)(l & 0xFFFFFFFF);
        j = (int)((l & 0x0) >> 32);
      }

      this.meg.marshalUB4(i);

      this.meg.marshalNULLPTR();
      this.meg.marshalPTR();

      if (this.connection.getTTCVersion() >= 5)
      {
        this.meg.marshalNULLPTR();
        this.meg.marshalUB4(0L);
        this.meg.marshalNULLPTR();
        this.meg.marshalUB4(0L);

        this.meg.marshalUB4(j);
      }
    }
  }

  boolean initBindsDefinition(T4CTTIoac[] paramArrayOfT4CTTIoac)
    throws SQLException, IOException
  {
    boolean bool = false;

    if (paramArrayOfT4CTTIoac.length != this.numberOfBindPositions)
    {
      bool = true;
      paramArrayOfT4CTTIoac = new T4CTTIoac[this.numberOfBindPositions];
    }

    short[] arrayOfShort = this.bindIndicators;

    int j = 0;

    int m = 0;

    for (int n = 0; n < this.numberOfBindPositions; n++)
    {
      T4CTTIoac localT4CTTIoac = new T4CTTIoac(this.connection);

      int i = this.bindIndicatorSubRange + 5 + 10 * n;

      short s = arrayOfShort[(i + 9)];

      int k = arrayOfShort[(i + 0)] & 0xFFFF;
      Object localObject;
      switch (k)
      {
      case 8:
      case 24:
        if (this.plsql)
          j = 32760;
        else {
          j = 2147483647;
        }
        localT4CTTIoac.init((short)k, j);
        localT4CTTIoac.setFormOfUse(s);
        localT4CTTIoac.setCharset(s == 2 ? this.NCharSet : this.dbCharSet);

        break;
      case 998:
        if ((this.outBindAccessors != null) && (this.outBindAccessors[n] != null))
        {
          PlsqlIndexTableAccessor localPlsqlIndexTableAccessor = (PlsqlIndexTableAccessor)this.outBindAccessors[n];
          localT4CTTIoac.init((short)localPlsqlIndexTableAccessor.elementInternalType, localPlsqlIndexTableAccessor.elementMaxLen);
          localT4CTTIoac.setMal(localPlsqlIndexTableAccessor.maxNumberOfElements);
          localT4CTTIoac.addFlg((short)64);
          m++;
        }
        else if (this.ibtBindIndicators[(6 + m * 8)] != 0)
        {
          int i1 = this.ibtBindIndicators[(6 + m * 8)];
          int i3 = this.ibtBindIndicators[(6 + m * 8 + 2)] << 16 & 0xFFFF000 | this.ibtBindIndicators[(6 + m * 8 + 3)];

          j = this.ibtBindIndicators[(6 + m * 8 + 1)] * this.conversion.sMaxCharSize;

          localT4CTTIoac.init((short)i1, j);
          localT4CTTIoac.setMal(i3);
          localT4CTTIoac.addFlg((short)64);
          m++;
        }
        else
        {
          localObject = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), "INTERNAL ERROR: Binding PLSQL index-by table but no type defined", -1);
          ((SQLException)localObject).fillInStackTrace();
          throw ((Throwable)localObject);
        }

        break;
      case 109:
      case 111:
        if ((this.outBindAccessors != null) && (this.outBindAccessors[n] != null))
        {
          if (this.outBindAccessors[n].internalOtype != null)
          {
            localT4CTTIoac.init((short)k, k == 109 ? 11 : 4000);

            localT4CTTIoac.setADT((OracleTypeADT)((TypeAccessor)this.outBindAccessors[n]).internalOtype);
          }

        }
        else if ((this.parameterOtype != null) && (this.parameterOtype[this.oracleStatement.firstRowInBatch] != null))
        {
          localT4CTTIoac.init((short)k, k == 109 ? 11 : 4000);
          localT4CTTIoac.setADT(this.parameterOtype[this.oracleStatement.firstRowInBatch][n]);
        }
        else
        {
          localObject = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), "INTERNAL ERROR: Binding NAMED_TYPE but no type defined", -1);
          ((SQLException)localObject).fillInStackTrace();
          throw ((Throwable)localObject);
        }

        break;
      case 994:
        localObject = this.oracleStatement.returnParamMeta;
        k = localObject[(3 + n * 3 + 0)];

        j = localObject[(3 + n * 3 + 2)];

        if ((k == 109) || (k == 111))
        {
          TypeAccessor localTypeAccessor = (TypeAccessor)this.oracleStatement.returnParamAccessors[n];

          localT4CTTIoac.init((short)k, k == 109 ? 11 : 4000);

          localT4CTTIoac.setADT((OracleTypeADT)localTypeAccessor.internalOtype);
        }
        else
        {
          localT4CTTIoac.init((short)k, j);
          localT4CTTIoac.setFormOfUse(s);
          localT4CTTIoac.setCharset(s == 2 ? this.NCharSet : this.dbCharSet);
        }

        break;
      case 180:
        j = arrayOfShort[(i + 1)] & 0xFFFF;

        localT4CTTIoac.init((short)k, j);
        localT4CTTIoac.addFlg2(134217728);

        localT4CTTIoac.setTimestampFractionalSecondsPrecision((short)9);

        int i2 = ((this.bindIndicators[(this.bindIndicatorSubRange + 3)] & 0xFFFF) << 16) + (this.bindIndicators[(this.bindIndicatorSubRange + 4)] & 0xFFFF);

        if (i2 == 1)
        {
          int i4 = ((arrayOfShort[(i + 7)] & 0xFFFF) << 16) + (arrayOfShort[(i + 8)] & 0xFFFF);

          int i5 = arrayOfShort[i4];

          if (i5 == 7)
          {
            localT4CTTIoac.setTimestampFractionalSecondsPrecision((short)0);
          }
        }

        break;
      default:
        j = arrayOfShort[(i + 1)] & 0xFFFF;

        if (j == 0)
        {
          j = arrayOfShort[(i + 2)] & 0xFFFF;

          if (k == 996) {
            j *= 2;
          }
          else if (j > 1) {
            j--;
          }

          if (s == 2) {
            j *= this.conversion.maxNCharSize;
          }

          if ((this.typeOfStatement == 32) || ((this.connection.versionNumber >= 10200) && (this.typeOfStatement == 64)))
          {
            if (j == 0)
              j = 32766;
            else
              j *= this.conversion.sMaxCharSize;
          }
          else if (this.typeOfStatement == 64)
          {
            if (j < 4001) {
              j = 4001;
            }

          }
          else if (s != 2)
          {
            if ((((T4CConnection)this.oracleStatement.connection).retainV9BindBehavior) && (j <= 4000))
            {
              j = Math.min(j * this.conversion.sMaxCharSize, 4000);
            }
            else
            {
              j *= this.conversion.sMaxCharSize;
            }

          }

          if (j == 0) {
            j = 32;
          }
        }
        localT4CTTIoac.init((short)k, j);
        localT4CTTIoac.setFormOfUse(s);
        localT4CTTIoac.setCharset(s == 2 ? this.NCharSet : this.dbCharSet);
      }

      if ((paramArrayOfT4CTTIoac[n] == null) || (!localT4CTTIoac.isOldSufficient(paramArrayOfT4CTTIoac[n])))
      {
        paramArrayOfT4CTTIoac[n] = localT4CTTIoac;
        bool = true;
      }

    }

    if (bool) {
      this.oracleStatement.nbPostPonedColumns[0] = 0;
    }
    return bool;
  }

  void initDefinesDefinition()
    throws SQLException, IOException
  {
    this.defCols = 0;

    for (int i = 0; i < this.definedColumnType.length; i++)
    {
      if (this.definedColumnType[i] == 0)
        break;
      this.defCols += 1;
    }
    this.oacdefDefines = new T4CTTIoac[this.defCols];
    i = 0;
    int j = 0;
    int k = 0;
    short s1 = 0;
    for (int m = 0; m < this.oacdefDefines.length; m++)
    {
      this.oacdefDefines[m] = new T4CTTIoac(this.connection);
      s1 = (short)this.oracleStatement.getInternalType(this.definedColumnType[m]);
      j = 2147483647;
      i = 0;
      k = 0;
      short s2 = 1;
      if ((this.definedColumnFormOfUse != null) && (this.definedColumnFormOfUse.length > m) && (this.definedColumnFormOfUse[m] == 2))
      {
        s2 = 2;
      }
      if (s1 == 8) {
        s1 = 1;
      } else if (s1 == 24) {
        s1 = 23;
      } else if ((s1 == 1) || (s1 == 96))
      {
        s1 = 1;

        j = 4000 * this.conversion.sMaxCharSize;

        if ((this.definedColumnSize != null) && (this.definedColumnSize.length > m) && (this.definedColumnSize[m] > 0))
        {
          j = this.definedColumnSize[m] * this.conversion.sMaxCharSize;
        }
      } else if ((s1 == 113) || (s1 == 112) || (s1 == 114))
      {
        j = 0;
        i = 33554432;
        if ((this.definedColumnSize != null) && (this.definedColumnSize.length > m) && (this.definedColumnSize[m] > 0))
        {
          k = this.definedColumnSize[m];
        }
      } else if (s1 == 23) {
        j = 4000;
      }
      this.oacdefDefines[m].init(s1, j);
      this.oacdefDefines[m].addFlg2(i);
      this.oacdefDefines[m].setMxlc(k);
      this.oacdefDefines[m].setFormOfUse(s2);
      this.oacdefDefines[m].setCharset(s2 == 2 ? this.NCharSet : this.dbCharSet);
    }
  }

  void marshalBindsTypes(T4CTTIoac[] paramArrayOfT4CTTIoac)
    throws IOException
  {
    if (paramArrayOfT4CTTIoac == null) {
      return;
    }
    for (int i = 0; i < paramArrayOfT4CTTIoac.length; i++)
    {
      paramArrayOfT4CTTIoac[i].marshal();
    }
  }

  Vector<IOException> marshalBinds(int[] paramArrayOfInt)
    throws IOException
  {
    Vector localVector1 = null;
    int i = ((this.bindIndicators[(this.bindIndicatorSubRange + 3)] & 0xFFFF) << 16) + (this.bindIndicators[(this.bindIndicatorSubRange + 4)] & 0xFFFF);

    for (int j = 0; j < i; j++)
    {
      int k = this.oracleStatement.firstRowInBatch + j;
      InputStream[] arrayOfInputStream = null;
      if (this.parameterStream != null)
        arrayOfInputStream = this.parameterStream[k];
      byte[][] arrayOfByte = (byte[][])null;
      if (this.parameterDatum != null)
        arrayOfByte = this.parameterDatum[k];
      OracleTypeADT[] arrayOfOracleTypeADT = null;
      if (this.parameterOtype != null) {
        arrayOfOracleTypeADT = this.parameterOtype[k];
      }

      Vector localVector2 = this.rxd.marshal(this.bindBytes, this.bindChars, this.bindIndicators, this.bindIndicatorSubRange, this.tmpBindsByteArray, this.conversion, arrayOfInputStream, arrayOfByte, arrayOfOracleTypeADT, this.ibtBindBytes, this.ibtBindChars, this.ibtBindIndicators, null, j, paramArrayOfInt, this.plsql, this.oracleStatement.returnParamMeta, this.oracleStatement.nbPostPonedColumns, this.oracleStatement.indexOfPostPonedColumn);

      if (localVector2 != null)
      {
        if (localVector1 == null)
          localVector1 = new Vector();
        localVector1.addAll(localVector2);
      }
    }
    return localVector1;
  }

  long setOptions(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4)
    throws SQLException
  {
    long l = 0L;

    if ((paramBoolean1) && (!paramBoolean2) && (!paramBoolean3)) {
      l |= 1L;
    } else if ((paramBoolean1) && (paramBoolean2) && (!paramBoolean3)) {
      l = 32801L; } else {
      if ((paramBoolean2) && (paramBoolean3))
      {
        if (paramBoolean1) {
          l |= 1L;
        }

      }

      switch (this.typeOfStatement)
      {
      case 1:
        l |= 32864L;

        break;
      case 32:
      case 64:
        if (this.numberOfBindPositions > 0)
        {
          l |= 0x420 | (this.oracleStatement.connection.autocommit ? 256 : 0);

          if (this.sendBindsDefinition)
            l |= 8L;
        }
        else {
          l |= 0x20 | (this.oracleStatement.connection.autocommit ? 256 : 0);
        }

        break;
      case -128:
      case 2:
      case 4:
      case 8:
      case 16:
        if (this.oracleStatement.returnParamAccessors != null) {
          l |= 0x420 | (this.oracleStatement.connection.autocommit ? 256 : 0);
        }
        else {
          l |= 0x8020 | (this.oracleStatement.connection.autocommit ? 256 : 0);
        }

        break;
      default:
        SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 432);
        localSQLException.fillInStackTrace();
        throw localSQLException;

        if ((!paramBoolean1) && (!paramBoolean2) && (paramBoolean3)) {
          l = 32832L;
        }
        else
        {
          localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 432);
          localSQLException.fillInStackTrace();
          throw localSQLException;
        }
        break;
      }
    }
    if ((this.typeOfStatement & 0x60) == 0)
    {
      if ((paramBoolean1) || (paramBoolean2) || (!paramBoolean3))
      {
        if ((this.numberOfBindPositions > 0) && (this.sendBindsDefinition)) {
          l |= 8L;
        }
      }
      if ((this.connection.versionNumber >= 9000) && (paramBoolean4))
      {
        l |= 16L;
      }
    }

    l &= -1L;

    return l;
  }

  protected OracleConnection getConnectionDuringExceptionHandling()
  {
    return this.connection;
  }
}