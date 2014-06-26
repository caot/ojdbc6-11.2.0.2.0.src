package oracle.jdbc.driver;

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
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;
import oracle.sql.ANYDATA;
import oracle.sql.ARRAY;
import oracle.sql.BFILE;
import oracle.sql.BINARY_DOUBLE;
import oracle.sql.BINARY_FLOAT;
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
import oracle.sql.StructDescriptor;
import oracle.sql.TIMESTAMP;
import oracle.sql.TIMESTAMPLTZ;
import oracle.sql.TIMESTAMPTZ;

abstract class OracleCallableStatement extends OraclePreparedStatement
  implements oracle.jdbc.internal.OracleCallableStatement
{
  boolean atLeastOneOrdinalParameter = false;
  boolean atLeastOneNamedParameter = false;

  String[] namedParameters = new String[8];

  int parameterCount = 0;

  final String errMsgMixedBind = "Ordinal binding and Named binding cannot be combined!";

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  OracleCallableStatement(PhysicalConnection paramPhysicalConnection, String paramString, int paramInt1, int paramInt2)
    throws SQLException
  {
    this(paramPhysicalConnection, paramString, paramInt1, paramInt2, 1003, 1007);
  }

  OracleCallableStatement(PhysicalConnection paramPhysicalConnection, String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    throws SQLException
  {
    super(paramPhysicalConnection, paramString, 1, paramInt2, paramInt3, paramInt4);

    this.statementType = 2;
  }

  void registerOutParameterInternal(int paramInt1, int paramInt2, int paramInt3, int paramInt4, String paramString)
    throws SQLException
  {
    int i = paramInt1 - 1;
    SQLException localSQLException;
    if ((i < 0) || (paramInt1 > this.numberOfBindPositions))
    {
      localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if (paramInt2 == 0)
    {
      localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
    int j = getInternalType(paramInt2);

    resetBatch();
    this.currentRowNeedToPrepareBinds = true;

    if (this.currentRowBindAccessors == null) {
      this.currentRowBindAccessors = new Accessor[this.numberOfBindPositions];
    }

    switch (paramInt2)
    {
    case -4:
    case -3:
    case -1:
    case 1:
    case 12:
    case 70:
      break;
    case -16:
    case -15:
    case -9:
      this.currentRowFormOfUse[i] = 2;
      break;
    case 2011:
      paramInt4 = 0;
      this.currentRowFormOfUse[i] = 2;
      break;
    case 2009:
      paramInt4 = 0;
      paramString = "SYS.XMLTYPE";
      break;
    default:
      paramInt4 = 0;
    }

    this.currentRowBindAccessors[i] = allocateAccessor(j, paramInt2, i + 1, paramInt4, this.currentRowFormOfUse[i], paramString, true);
  }

  public void registerOutParameter(int paramInt1, int paramInt2, String paramString)
    throws SQLException
  {
    if ((paramString == null) || (paramString.length() == 0))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 60, "empty Object name");
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    synchronized (this.connection) {
      registerOutParameterInternal(paramInt1, paramInt2, 0, 0, paramString);
    }
  }

  /** @deprecated */
  public void registerOutParameterBytes(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    throws SQLException
  {
    synchronized (this.connection)
    {
      registerOutParameterInternal(paramInt1, paramInt2, paramInt3, paramInt4, null);
    }
  }

  /** @deprecated */
  public void registerOutParameterChars(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    throws SQLException
  {
    synchronized (this.connection)
    {
      registerOutParameterInternal(paramInt1, paramInt2, paramInt3, paramInt4, null);
    }
  }

  public void registerOutParameter(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    throws SQLException
  {
    synchronized (this.connection)
    {
      registerOutParameterInternal(paramInt1, paramInt2, paramInt3, paramInt4, null);
    }
  }

  public void registerOutParameter(String paramString, int paramInt1, int paramInt2, int paramInt3)
    throws SQLException
  {
    synchronized (this.connection)
    {
      registerOutParameterInternal(paramString, paramInt1, paramInt2, paramInt3, null);
    }
  }

  boolean isOracleBatchStyle()
  {
    return false;
  }

  void resetBatch()
  {
    this.batch = 1;
  }

  public void setExecuteBatch(int paramInt)
    throws SQLException
  {
  }

  public int sendBatch()
    throws SQLException
  {
    synchronized (this.connection)
    {
      return this.validRows;
    }
  }

  public void registerOutParameter(int paramInt1, int paramInt2)
    throws SQLException
  {
    registerOutParameter(paramInt1, paramInt2, 0, -1);
  }

  public void registerOutParameter(int paramInt1, int paramInt2, int paramInt3)
    throws SQLException
  {
    registerOutParameter(paramInt1, paramInt2, paramInt3, -1);
  }

  public boolean wasNull()
    throws SQLException
  {
    return wasNullValue();
  }

  public String getString(int paramInt)
    throws SQLException
  {
    SQLException sqlexception;
    if (this.closed)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    if (this.atLeastOneNamedParameter)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 90, "Ordinal binding and Named binding cannot be combined!");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject = null;
    if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || (this.outBindAccessors == null) || ((localObject = this.outBindAccessors[(paramInt - 1)]) == null))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.lastIndex = paramInt;

    if (this.streamList != null) {
      closeUsedStreams(paramInt);
    }

    return ((Accessor)localObject).getString(this.currentRank);
  }

  public Datum getOracleObject(int paramInt)
    throws SQLException
  {
    SQLException sqlexception;
    if (this.closed)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    if (this.atLeastOneNamedParameter)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 90, "Ordinal binding and Named binding cannot be combined!");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject = null;
    if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || (this.outBindAccessors == null) || ((localObject = this.outBindAccessors[(paramInt - 1)]) == null))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.lastIndex = paramInt;

    if (this.streamList != null) {
      closeUsedStreams(paramInt);
    }

    return ((Accessor)localObject).getOracleObject(this.currentRank);
  }

  public ROWID getROWID(int paramInt)
    throws SQLException
  {
    SQLException sqlexception;
    if (this.closed)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    if (this.atLeastOneNamedParameter)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 90, "Ordinal binding and Named binding cannot be combined!");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject = null;
    if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || (this.outBindAccessors == null) || ((localObject = this.outBindAccessors[(paramInt - 1)]) == null))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.lastIndex = paramInt;

    if (this.streamList != null) {
      closeUsedStreams(paramInt);
    }

    return ((Accessor)localObject).getROWID(this.currentRank);
  }

  public NUMBER getNUMBER(int paramInt)
    throws SQLException
  {
    SQLException sqlexception;
    if (this.closed)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    if (this.atLeastOneNamedParameter)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 90, "Ordinal binding and Named binding cannot be combined!");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject = null;
    if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || (this.outBindAccessors == null) || ((localObject = this.outBindAccessors[(paramInt - 1)]) == null))
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    this.lastIndex = paramInt;

    if (this.streamList != null) {
      closeUsedStreams(paramInt);
    }

    return ((Accessor)localObject).getNUMBER(this.currentRank);
  }

  public DATE getDATE(int paramInt)
    throws SQLException
  {
    SQLException sqlexception;
    if (this.closed)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    if (this.atLeastOneNamedParameter)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 90, "Ordinal binding and Named binding cannot be combined!");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject = null;
    if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || (this.outBindAccessors == null) || ((localObject = this.outBindAccessors[(paramInt - 1)]) == null))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.lastIndex = paramInt;

    if (this.streamList != null) {
      closeUsedStreams(paramInt);
    }

    return ((Accessor)localObject).getDATE(this.currentRank);
  }

  public INTERVALYM getINTERVALYM(int paramInt)
    throws SQLException
  {
    SQLException sqlexception;
    if (this.closed)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    if (this.atLeastOneNamedParameter)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 90, "Ordinal binding and Named binding cannot be combined!");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject = null;
    if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || (this.outBindAccessors == null) || ((localObject = this.outBindAccessors[(paramInt - 1)]) == null))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.lastIndex = paramInt;

    if (this.streamList != null) {
      closeUsedStreams(paramInt);
    }

    return ((Accessor)localObject).getINTERVALYM(this.currentRank);
  }

  public INTERVALDS getINTERVALDS(int paramInt)
    throws SQLException
  {
    SQLException sqlexception;
    if (this.closed)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    if (this.atLeastOneNamedParameter)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 90, "Ordinal binding and Named binding cannot be combined!");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject = null;
    if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || (this.outBindAccessors == null) || ((localObject = this.outBindAccessors[(paramInt - 1)]) == null))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.lastIndex = paramInt;

    if (this.streamList != null) {
      closeUsedStreams(paramInt);
    }

    return ((Accessor)localObject).getINTERVALDS(this.currentRank);
  }

  public TIMESTAMP getTIMESTAMP(int paramInt)
    throws SQLException
  {
    SQLException sqlexception;
    if (this.closed)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    if (this.atLeastOneNamedParameter)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 90, "Ordinal binding and Named binding cannot be combined!");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject = null;
    if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || (this.outBindAccessors == null) || ((localObject = this.outBindAccessors[(paramInt - 1)]) == null))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.lastIndex = paramInt;

    if (this.streamList != null) {
      closeUsedStreams(paramInt);
    }

    return ((Accessor)localObject).getTIMESTAMP(this.currentRank);
  }

  public TIMESTAMPTZ getTIMESTAMPTZ(int paramInt)
    throws SQLException
  {
    SQLException sqlexception;
    if (this.closed)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    if (this.atLeastOneNamedParameter)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 90, "Ordinal binding and Named binding cannot be combined!");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject = null;
    if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || (this.outBindAccessors == null) || ((localObject = this.outBindAccessors[(paramInt - 1)]) == null))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.lastIndex = paramInt;

    if (this.streamList != null) {
      closeUsedStreams(paramInt);
    }

    return ((Accessor)localObject).getTIMESTAMPTZ(this.currentRank);
  }

  public TIMESTAMPLTZ getTIMESTAMPLTZ(int paramInt)
    throws SQLException
  {
    SQLException sqlexception;
    if (this.closed)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    if (this.atLeastOneNamedParameter)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 90, "Ordinal binding and Named binding cannot be combined!");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject = null;
    if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || (this.outBindAccessors == null) || ((localObject = this.outBindAccessors[(paramInt - 1)]) == null))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.lastIndex = paramInt;

    if (this.streamList != null) {
      closeUsedStreams(paramInt);
    }

    return ((Accessor)localObject).getTIMESTAMPLTZ(this.currentRank);
  }

  public REF getREF(int paramInt)
    throws SQLException
  {
    SQLException sqlexception;
    if (this.closed)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    if (this.atLeastOneNamedParameter)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 90, "Ordinal binding and Named binding cannot be combined!");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject = null;
    if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || (this.outBindAccessors == null) || ((localObject = this.outBindAccessors[(paramInt - 1)]) == null))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.lastIndex = paramInt;

    if (this.streamList != null) {
      closeUsedStreams(paramInt);
    }

    return ((Accessor)localObject).getREF(this.currentRank);
  }

  public ARRAY getARRAY(int paramInt)
    throws SQLException
  {
    SQLException sqlexception;
    if (this.closed)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    if (this.atLeastOneNamedParameter)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 90, "Ordinal binding and Named binding cannot be combined!");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject = null;
    if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || (this.outBindAccessors == null) || ((localObject = this.outBindAccessors[(paramInt - 1)]) == null))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.lastIndex = paramInt;

    if (this.streamList != null) {
      closeUsedStreams(paramInt);
    }

    return ((Accessor)localObject).getARRAY(this.currentRank);
  }

  public STRUCT getSTRUCT(int paramInt)
    throws SQLException
  {
    SQLException sqlexception;
    if (this.closed)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    if (this.atLeastOneNamedParameter)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 90, "Ordinal binding and Named binding cannot be combined!");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject = null;
    if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || (this.outBindAccessors == null) || ((localObject = this.outBindAccessors[(paramInt - 1)]) == null))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.lastIndex = paramInt;

    if (this.streamList != null) {
      closeUsedStreams(paramInt);
    }

    return ((Accessor)localObject).getSTRUCT(this.currentRank);
  }

  public OPAQUE getOPAQUE(int paramInt)
    throws SQLException
  {
    SQLException sqlexception;
    if (this.closed)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    if (this.atLeastOneNamedParameter)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 90, "Ordinal binding and Named binding cannot be combined!");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject = null;
    if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || (this.outBindAccessors == null) || ((localObject = this.outBindAccessors[(paramInt - 1)]) == null))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.lastIndex = paramInt;

    if (this.streamList != null) {
      closeUsedStreams(paramInt);
    }

    return ((Accessor)localObject).getOPAQUE(this.currentRank);
  }

  public CHAR getCHAR(int paramInt)
    throws SQLException
  {
    SQLException sqlexception;
    if (this.closed)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    if (this.atLeastOneNamedParameter)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 90, "Ordinal binding and Named binding cannot be combined!");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject = null;
    if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || (this.outBindAccessors == null) || ((localObject = this.outBindAccessors[(paramInt - 1)]) == null))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.lastIndex = paramInt;

    if (this.streamList != null) {
      closeUsedStreams(paramInt);
    }

    return ((Accessor)localObject).getCHAR(this.currentRank);
  }

  public Reader getCharacterStream(int paramInt)
    throws SQLException
  {
    SQLException sqlexception;
    if (this.closed)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    if (this.atLeastOneNamedParameter)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 90, "Ordinal binding and Named binding cannot be combined!");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject = null;
    if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || (this.outBindAccessors == null) || ((localObject = this.outBindAccessors[(paramInt - 1)]) == null))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.lastIndex = paramInt;

    if (this.streamList != null) {
      closeUsedStreams(paramInt);
    }

    return ((Accessor)localObject).getCharacterStream(this.currentRank);
  }

  public RAW getRAW(int paramInt)
    throws SQLException
  {
    SQLException sqlexception;
    if (this.closed)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    if (this.atLeastOneNamedParameter)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 90, "Ordinal binding and Named binding cannot be combined!");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject = null;
    if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || (this.outBindAccessors == null) || ((localObject = this.outBindAccessors[(paramInt - 1)]) == null))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.lastIndex = paramInt;

    if (this.streamList != null) {
      closeUsedStreams(paramInt);
    }

    return ((Accessor)localObject).getRAW(this.currentRank);
  }

  public BLOB getBLOB(int paramInt)
    throws SQLException
  {
    SQLException sqlexception;
    if (this.closed)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    if (this.atLeastOneNamedParameter)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 90, "Ordinal binding and Named binding cannot be combined!");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject = null;
    if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || (this.outBindAccessors == null) || ((localObject = this.outBindAccessors[(paramInt - 1)]) == null))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.lastIndex = paramInt;

    if (this.streamList != null) {
      closeUsedStreams(paramInt);
    }

    return ((Accessor)localObject).getBLOB(this.currentRank);
  }

  public CLOB getCLOB(int paramInt)
    throws SQLException
  {
    SQLException sqlexception;
    if (this.closed)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    if (this.atLeastOneNamedParameter)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 90, "Ordinal binding and Named binding cannot be combined!");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject = null;
    if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || (this.outBindAccessors == null) || ((localObject = this.outBindAccessors[(paramInt - 1)]) == null))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.lastIndex = paramInt;

    if (this.streamList != null) {
      closeUsedStreams(paramInt);
    }

    return ((Accessor)localObject).getCLOB(this.currentRank);
  }

  public BFILE getBFILE(int paramInt)
    throws SQLException
  {
    SQLException sqlexception;
    if (this.closed)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    if (this.atLeastOneNamedParameter)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 90, "Ordinal binding and Named binding cannot be combined!");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject = null;
    if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || (this.outBindAccessors == null) || ((localObject = this.outBindAccessors[(paramInt - 1)]) == null))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.lastIndex = paramInt;

    if (this.streamList != null) {
      closeUsedStreams(paramInt);
    }

    return ((Accessor)localObject).getBFILE(this.currentRank);
  }

  public BFILE getBfile(int paramInt)
    throws SQLException
  {
    SQLException sqlexception;
    if (this.closed)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    if (this.atLeastOneNamedParameter)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 90, "Ordinal binding and Named binding cannot be combined!");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject = null;
    if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || (this.outBindAccessors == null) || ((localObject = this.outBindAccessors[(paramInt - 1)]) == null))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.lastIndex = paramInt;

    if (this.streamList != null) {
      closeUsedStreams(paramInt);
    }

    return ((Accessor)localObject).getBFILE(this.currentRank);
  }

  public boolean getBoolean(int paramInt)
    throws SQLException
  {
    SQLException sqlexception;
    if (this.closed)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    if (this.atLeastOneNamedParameter)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 90, "Ordinal binding and Named binding cannot be combined!");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject = null;
    if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || (this.outBindAccessors == null) || ((localObject = this.outBindAccessors[(paramInt - 1)]) == null))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.lastIndex = paramInt;

    if (this.streamList != null) {
      closeUsedStreams(paramInt);
    }

    return ((Accessor)localObject).getBoolean(this.currentRank);
  }

  public byte getByte(int paramInt)
    throws SQLException
  {
    SQLException sqlexception;
    if (this.closed)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    if (this.atLeastOneNamedParameter)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 90, "Ordinal binding and Named binding cannot be combined!");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject = null;
    if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || (this.outBindAccessors == null) || ((localObject = this.outBindAccessors[(paramInt - 1)]) == null))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.lastIndex = paramInt;

    if (this.streamList != null) {
      closeUsedStreams(paramInt);
    }

    return ((Accessor)localObject).getByte(this.currentRank);
  }

  public short getShort(int paramInt)
    throws SQLException
  {
    SQLException sqlexception;
    if (this.closed)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    if (this.atLeastOneNamedParameter)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 90, "Ordinal binding and Named binding cannot be combined!");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject = null;
    if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || (this.outBindAccessors == null) || ((localObject = this.outBindAccessors[(paramInt - 1)]) == null))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.lastIndex = paramInt;

    if (this.streamList != null) {
      closeUsedStreams(paramInt);
    }

    return ((Accessor)localObject).getShort(this.currentRank);
  }

  public int getInt(int paramInt)
    throws SQLException
  {
    SQLException sqlexception;
    if (this.closed)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    if (this.atLeastOneNamedParameter)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 90, "Ordinal binding and Named binding cannot be combined!");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject = null;
    if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || (this.outBindAccessors == null) || ((localObject = this.outBindAccessors[(paramInt - 1)]) == null))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.lastIndex = paramInt;

    if (this.streamList != null) {
      closeUsedStreams(paramInt);
    }

    return ((Accessor)localObject).getInt(this.currentRank);
  }

  public long getLong(int paramInt)
    throws SQLException
  {
    SQLException sqlexception;
    if (this.closed)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    if (this.atLeastOneNamedParameter)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 90, "Ordinal binding and Named binding cannot be combined!");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject = null;
    if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || (this.outBindAccessors == null) || ((localObject = this.outBindAccessors[(paramInt - 1)]) == null))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.lastIndex = paramInt;

    if (this.streamList != null) {
      closeUsedStreams(paramInt);
    }

    return ((Accessor)localObject).getLong(this.currentRank);
  }

  public float getFloat(int paramInt)
    throws SQLException
  {
    SQLException sqlexception;
    if (this.closed)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    if (this.atLeastOneNamedParameter)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 90, "Ordinal binding and Named binding cannot be combined!");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject = null;
    if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || (this.outBindAccessors == null) || ((localObject = this.outBindAccessors[(paramInt - 1)]) == null))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.lastIndex = paramInt;

    if (this.streamList != null) {
      closeUsedStreams(paramInt);
    }

    return ((Accessor)localObject).getFloat(this.currentRank);
  }

  public double getDouble(int paramInt)
    throws SQLException
  {
    SQLException sqlexception;
    if (this.closed)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    if (this.atLeastOneNamedParameter)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 90, "Ordinal binding and Named binding cannot be combined!");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject = null;
    if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || (this.outBindAccessors == null) || ((localObject = this.outBindAccessors[(paramInt - 1)]) == null))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.lastIndex = paramInt;

    if (this.streamList != null) {
      closeUsedStreams(paramInt);
    }

    return ((Accessor)localObject).getDouble(this.currentRank);
  }

  public BigDecimal getBigDecimal(int paramInt1, int paramInt2)
    throws SQLException
  {
    SQLException sqlexception;
    if (this.closed)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    if (this.atLeastOneNamedParameter)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 90, "Ordinal binding and Named binding cannot be combined!");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject = null;
    if ((paramInt1 <= 0) || (paramInt1 > this.numberOfBindPositions) || (this.outBindAccessors == null) || ((localObject = this.outBindAccessors[(paramInt1 - 1)]) == null))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.lastIndex = paramInt1;

    if (this.streamList != null) {
      closeUsedStreams(paramInt1);
    }

    return ((Accessor)localObject).getBigDecimal(this.currentRank);
  }

  public byte[] getBytes(int paramInt)
    throws SQLException
  {
    SQLException sqlexception;
    if (this.closed)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    if (this.atLeastOneNamedParameter)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 90, "Ordinal binding and Named binding cannot be combined!");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject = null;
    if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || (this.outBindAccessors == null) || ((localObject = this.outBindAccessors[(paramInt - 1)]) == null))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.lastIndex = paramInt;

    if (this.streamList != null) {
      closeUsedStreams(paramInt);
    }

    return ((Accessor)localObject).getBytes(this.currentRank);
  }

  public byte[] privateGetBytes(int paramInt)
    throws SQLException
  {
    SQLException sqlexception;
    if (this.closed)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    if (this.atLeastOneNamedParameter)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 90, "Ordinal binding and Named binding cannot be combined!");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject = null;
    if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || (this.outBindAccessors == null) || ((localObject = this.outBindAccessors[(paramInt - 1)]) == null))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.lastIndex = paramInt;

    if (this.streamList != null) {
      closeUsedStreams(paramInt);
    }

    return ((Accessor)localObject).privateGetBytes(this.currentRank);
  }

  public Date getDate(int paramInt)
    throws SQLException
  {
    SQLException sqlexception;
    if (this.closed)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    if (this.atLeastOneNamedParameter)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 90, "Ordinal binding and Named binding cannot be combined!");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject = null;
    if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || (this.outBindAccessors == null) || ((localObject = this.outBindAccessors[(paramInt - 1)]) == null))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.lastIndex = paramInt;

    if (this.streamList != null) {
      closeUsedStreams(paramInt);
    }

    return ((Accessor)localObject).getDate(this.currentRank);
  }

  public Time getTime(int paramInt)
    throws SQLException
  {
    SQLException sqlexception;
    if (this.closed)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    if (this.atLeastOneNamedParameter)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 90, "Ordinal binding and Named binding cannot be combined!");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject = null;
    if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || (this.outBindAccessors == null) || ((localObject = this.outBindAccessors[(paramInt - 1)]) == null))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.lastIndex = paramInt;

    if (this.streamList != null) {
      closeUsedStreams(paramInt);
    }

    return ((Accessor)localObject).getTime(this.currentRank);
  }

  public Timestamp getTimestamp(int paramInt)
    throws SQLException
  {
    SQLException sqlexception;
    if (this.closed)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    if (this.atLeastOneNamedParameter)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 90, "Ordinal binding and Named binding cannot be combined!");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject = null;
    if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || (this.outBindAccessors == null) || ((localObject = this.outBindAccessors[(paramInt - 1)]) == null))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.lastIndex = paramInt;

    if (this.streamList != null) {
      closeUsedStreams(paramInt);
    }

    return ((Accessor)localObject).getTimestamp(this.currentRank);
  }

  public InputStream getAsciiStream(int paramInt)
    throws SQLException
  {
    SQLException sqlexception;
    if (this.closed)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    if (this.atLeastOneNamedParameter)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 90, "Ordinal binding and Named binding cannot be combined!");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject = null;
    if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || (this.outBindAccessors == null) || ((localObject = this.outBindAccessors[(paramInt - 1)]) == null))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.lastIndex = paramInt;

    if (this.streamList != null) {
      closeUsedStreams(paramInt);
    }

    return ((Accessor)localObject).getAsciiStream(this.currentRank);
  }

  public InputStream getUnicodeStream(int paramInt)
    throws SQLException
  {
    SQLException sqlexception;
    if (this.closed)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    if (this.atLeastOneNamedParameter)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 90, "Ordinal binding and Named binding cannot be combined!");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject = null;
    if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || (this.outBindAccessors == null) || ((localObject = this.outBindAccessors[(paramInt - 1)]) == null))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.lastIndex = paramInt;

    if (this.streamList != null) {
      closeUsedStreams(paramInt);
    }

    return ((Accessor)localObject).getUnicodeStream(this.currentRank);
  }

  public InputStream getBinaryStream(int paramInt)
    throws SQLException
  {
    SQLException sqlexception;
    if (this.closed)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    if (this.atLeastOneNamedParameter)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 90, "Ordinal binding and Named binding cannot be combined!");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject = null;
    if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || (this.outBindAccessors == null) || ((localObject = this.outBindAccessors[(paramInt - 1)]) == null))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.lastIndex = paramInt;

    if (this.streamList != null) {
      closeUsedStreams(paramInt);
    }

    return ((Accessor)localObject).getBinaryStream(this.currentRank);
  }

  public Object getObject(int paramInt)
    throws SQLException
  {
    SQLException sqlexception;
    if (this.closed)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    if (this.atLeastOneNamedParameter)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 90, "Ordinal binding and Named binding cannot be combined!");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject = null;
    if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || (this.outBindAccessors == null) || ((localObject = this.outBindAccessors[(paramInt - 1)]) == null))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.lastIndex = paramInt;

    if (this.streamList != null) {
      closeUsedStreams(paramInt);
    }

    return ((Accessor)localObject).getObject(this.currentRank);
  }

  public Object getAnyDataEmbeddedObject(int paramInt)
    throws SQLException
  {
    Object localObject1 = null;
    Object localObject2 = getObject(paramInt);
    if ((localObject2 instanceof ANYDATA))
    {
      Datum localDatum = ((ANYDATA)localObject2).accessDatum();
      if (localDatum != null) localObject1 = localDatum.toJdbc();
    }
    return localObject1;
  }

  public Object getCustomDatum(int paramInt, CustomDatumFactory paramCustomDatumFactory)
    throws SQLException
  {
    SQLException sqlexception;
    if (this.closed)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    if (this.atLeastOneNamedParameter)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 90, "Ordinal binding and Named binding cannot be combined!");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject = null;
    if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || (this.outBindAccessors == null) || ((localObject = this.outBindAccessors[(paramInt - 1)]) == null))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.lastIndex = paramInt;

    if (this.streamList != null) {
      closeUsedStreams(paramInt);
    }

    return ((Accessor)localObject).getCustomDatum(this.currentRank, paramCustomDatumFactory);
  }

  public Object getORAData(int paramInt, ORADataFactory paramORADataFactory)
    throws SQLException
  {
    SQLException sqlexception;
    if (this.closed)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    if (this.atLeastOneNamedParameter)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 90, "Ordinal binding and Named binding cannot be combined!");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject = null;
    if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || (this.outBindAccessors == null) || ((localObject = this.outBindAccessors[(paramInt - 1)]) == null))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.lastIndex = paramInt;

    if (this.streamList != null) {
      closeUsedStreams(paramInt);
    }

    return ((Accessor)localObject).getORAData(this.currentRank, paramORADataFactory);
  }

  public ResultSet getCursor(int paramInt)
    throws SQLException
  {
    SQLException sqlexception;
    if (this.closed)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    if (this.atLeastOneNamedParameter)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 90, "Ordinal binding and Named binding cannot be combined!");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject = null;
    if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || (this.outBindAccessors == null) || ((localObject = this.outBindAccessors[(paramInt - 1)]) == null))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.lastIndex = paramInt;

    if (this.streamList != null) {
      closeUsedStreams(paramInt);
    }

    return ((Accessor)localObject).getCursor(this.currentRank);
  }

  public void clearParameters()
    throws SQLException
  {
    synchronized (this.connection)
    {
      super.clearParameters();
    }
  }

  public Object getObject(int paramInt, Map paramMap)
    throws SQLException
  {
    SQLException sqlexception;
    if (this.closed)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    if (this.atLeastOneNamedParameter)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 90, "Ordinal binding and Named binding cannot be combined!");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject = null;
    if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || (this.outBindAccessors == null) || ((localObject = this.outBindAccessors[(paramInt - 1)]) == null))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.lastIndex = paramInt;

    if (this.streamList != null) {
      closeUsedStreams(paramInt);
    }

    return ((Accessor)localObject).getObject(this.currentRank, paramMap);
  }

  public Ref getRef(int paramInt)
    throws SQLException
  {
    SQLException sqlexception;
    if (this.closed)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    if (this.atLeastOneNamedParameter)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 90, "Ordinal binding and Named binding cannot be combined!");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject = null;
    if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || (this.outBindAccessors == null) || ((localObject = this.outBindAccessors[(paramInt - 1)]) == null))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.lastIndex = paramInt;

    if (this.streamList != null) {
      closeUsedStreams(paramInt);
    }

    return ((Accessor)localObject).getREF(this.currentRank);
  }

  public Blob getBlob(int paramInt)
    throws SQLException
  {
    SQLException sqlexception;
    if (this.closed)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    if (this.atLeastOneNamedParameter)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 90, "Ordinal binding and Named binding cannot be combined!");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject = null;
    if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || (this.outBindAccessors == null) || ((localObject = this.outBindAccessors[(paramInt - 1)]) == null))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.lastIndex = paramInt;

    if (this.streamList != null) {
      closeUsedStreams(paramInt);
    }

    return ((Accessor)localObject).getBLOB(this.currentRank);
  }

  public Clob getClob(int paramInt)
    throws SQLException
  {
    SQLException sqlexception;
    if (this.closed)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    if (this.atLeastOneNamedParameter)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 90, "Ordinal binding and Named binding cannot be combined!");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject = null;
    if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || (this.outBindAccessors == null) || ((localObject = this.outBindAccessors[(paramInt - 1)]) == null))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.lastIndex = paramInt;

    if (this.streamList != null) {
      closeUsedStreams(paramInt);
    }

    return ((Accessor)localObject).getCLOB(this.currentRank);
  }

  public Array getArray(int paramInt)
    throws SQLException
  {
    SQLException sqlexception;
    if (this.closed)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    if (this.atLeastOneNamedParameter)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 90, "Ordinal binding and Named binding cannot be combined!");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject = null;
    if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || (this.outBindAccessors == null) || ((localObject = this.outBindAccessors[(paramInt - 1)]) == null))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.lastIndex = paramInt;

    if (this.streamList != null) {
      closeUsedStreams(paramInt);
    }

    return ((Accessor)localObject).getARRAY(this.currentRank);
  }

  public BigDecimal getBigDecimal(int paramInt)
    throws SQLException
  {
    SQLException sqlexception;
    if (this.closed)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    if (this.atLeastOneNamedParameter)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 90, "Ordinal binding and Named binding cannot be combined!");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject = null;
    if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || (this.outBindAccessors == null) || ((localObject = this.outBindAccessors[(paramInt - 1)]) == null))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.lastIndex = paramInt;

    if (this.streamList != null) {
      closeUsedStreams(paramInt);
    }

    return ((Accessor)localObject).getBigDecimal(this.currentRank);
  }

  public Date getDate(int paramInt, Calendar paramCalendar)
    throws SQLException
  {
    SQLException sqlexception;
    if (this.closed)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    if (this.atLeastOneNamedParameter)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 90, "Ordinal binding and Named binding cannot be combined!");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject = null;
    if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || (this.outBindAccessors == null) || ((localObject = this.outBindAccessors[(paramInt - 1)]) == null))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.lastIndex = paramInt;

    if (this.streamList != null) {
      closeUsedStreams(paramInt);
    }

    return ((Accessor)localObject).getDate(this.currentRank, paramCalendar);
  }

  public Time getTime(int paramInt, Calendar paramCalendar)
    throws SQLException
  {
    SQLException sqlexception;
    if (this.closed)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    if (this.atLeastOneNamedParameter)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 90, "Ordinal binding and Named binding cannot be combined!");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject = null;
    if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || (this.outBindAccessors == null) || ((localObject = this.outBindAccessors[(paramInt - 1)]) == null))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.lastIndex = paramInt;

    if (this.streamList != null) {
      closeUsedStreams(paramInt);
    }

    return ((Accessor)localObject).getTime(this.currentRank, paramCalendar);
  }

  public Timestamp getTimestamp(int paramInt, Calendar paramCalendar)
    throws SQLException
  {
    SQLException sqlexception;
    if (this.closed)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    if (this.atLeastOneNamedParameter)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 90, "Ordinal binding and Named binding cannot be combined!");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject = null;
    if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || (this.outBindAccessors == null) || ((localObject = this.outBindAccessors[(paramInt - 1)]) == null))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.lastIndex = paramInt;

    if (this.streamList != null) {
      closeUsedStreams(paramInt);
    }

    return ((Accessor)localObject).getTimestamp(this.currentRank, paramCalendar);
  }

  public void addBatch()
    throws SQLException
  {
    if (this.currentRowBindAccessors != null)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 90, "Stored procedure with out or inout parameters cannot be batched");
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    super.addBatch();
  }

  protected void alwaysOnClose()
    throws SQLException
  {
    this.sqlObject.resetNamedParameters();

    this.namedParameters = new String[8];
    this.parameterCount = 0;
    this.atLeastOneOrdinalParameter = false;
    this.atLeastOneNamedParameter = false;

    super.alwaysOnClose();
  }

  public void registerOutParameter(String paramString, int paramInt)
    throws SQLException
  {
    registerOutParameterInternal(paramString, paramInt, 0, -1, null);
  }

  public void registerOutParameter(String paramString, int paramInt1, int paramInt2)
    throws SQLException
  {
    registerOutParameterInternal(paramString, paramInt1, paramInt2, -1, null);
  }

  public void registerOutParameter(String paramString1, int paramInt, String paramString2)
    throws SQLException
  {
    registerOutParameterInternal(paramString1, paramInt, 0, -1, paramString2);
  }

  void registerOutParameterInternal(String paramString1, int paramInt1, int paramInt2, int paramInt3, String paramString2)
    throws SQLException
  {
    int i = addNamedPara(paramString1);
    registerOutParameterInternal(i, paramInt1, paramInt2, paramInt3, paramString2);
  }

  public URL getURL(int paramInt)
    throws SQLException
  {
    SQLException sqlexception;
    if (this.closed)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    if (this.atLeastOneNamedParameter)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 90, "Ordinal binding and Named binding cannot be combined!");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject = null;
    if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || (this.outBindAccessors == null) || ((localObject = this.outBindAccessors[(paramInt - 1)]) == null))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.lastIndex = paramInt;

    if (this.streamList != null) {
      closeUsedStreams(paramInt);
    }

    return ((Accessor)localObject).getURL(this.currentRank);
  }

  public void setStringForClob(String paramString1, String paramString2)
    throws SQLException
  {
    int i = addNamedPara(paramString1);
    if ((paramString2 == null) || (paramString2.length() == 0))
    {
      setNull(i, 2005);
      return;
    }
    setStringForClob(i, paramString2);
  }

  public void setStringForClob(int paramInt, String paramString)
    throws SQLException
  {
    if ((paramString == null) || (paramString.length() == 0))
    {
      setNull(paramInt, 2005);
      return;
    }
    synchronized (this.connection) {
      setStringForClobCritical(paramInt, paramString);
    }
  }

  public void setBytesForBlob(String paramString, byte[] paramArrayOfByte)
    throws SQLException
  {
    int i = addNamedPara(paramString);
    setBytesForBlob(i, paramArrayOfByte);
  }

  public void setBytesForBlob(int paramInt, byte[] paramArrayOfByte)
    throws SQLException
  {
    if ((paramArrayOfByte == null) || (paramArrayOfByte.length == 0))
    {
      setNull(paramInt, 2004);
      return;
    }
    synchronized (this.connection) {
      setBytesForBlobCritical(paramInt, paramArrayOfByte);
    }
  }

  public String getString(String paramString)
    throws SQLException
  {
    SQLException sqlexception;
    if (!this.atLeastOneNamedParameter)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 90, "Ordinal binding and Named binding cannot be combined!");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject = paramString.toUpperCase().intern();

    int i;
    for (i = 0; i < this.parameterCount; i++)
    {
      if (localObject == this.namedParameters[i])
        break;
    }
    i++;

    Accessor localAccessor = null;
    if ((i <= 0) || (i > this.numberOfBindPositions) || (this.outBindAccessors == null) || ((localAccessor = this.outBindAccessors[(i - 1)]) == null))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 6);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.lastIndex = i;

    if (this.streamList != null) {
      closeUsedStreams(i);
    }

    return localAccessor.getString(this.currentRank);
  }

  public boolean getBoolean(String paramString)
    throws SQLException
  {
    SQLException sqlexception;
    if (!this.atLeastOneNamedParameter)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 90, "Ordinal binding and Named binding cannot be combined!");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject = paramString.toUpperCase().intern();

    int i;
    for (i = 0; i < this.parameterCount; i++)
    {
      if (localObject == this.namedParameters[i])
        break;
    }
    i++;

    Accessor localAccessor = null;
    if ((i <= 0) || (i > this.numberOfBindPositions) || (this.outBindAccessors == null) || ((localAccessor = this.outBindAccessors[(i - 1)]) == null))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 6);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.lastIndex = i;

    if (this.streamList != null) {
      closeUsedStreams(i);
    }

    return localAccessor.getBoolean(this.currentRank);
  }

  public byte getByte(String paramString)
    throws SQLException
  {
    SQLException sqlexception;
    if (!this.atLeastOneNamedParameter)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 90, "Ordinal binding and Named binding cannot be combined!");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject = paramString.toUpperCase().intern();

    int i;
    for (i = 0; i < this.parameterCount; i++)
    {
      if (localObject == this.namedParameters[i])
        break;
    }
    i++;

    Accessor localAccessor = null;
    if ((i <= 0) || (i > this.numberOfBindPositions) || (this.outBindAccessors == null) || ((localAccessor = this.outBindAccessors[(i - 1)]) == null))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 6);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.lastIndex = i;

    if (this.streamList != null) {
      closeUsedStreams(i);
    }

    return localAccessor.getByte(this.currentRank);
  }

  public short getShort(String paramString)
    throws SQLException
  {
    SQLException sqlexception;
    if (!this.atLeastOneNamedParameter)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 90, "Ordinal binding and Named binding cannot be combined!");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject = paramString.toUpperCase().intern();

    int i;
    for (i = 0; i < this.parameterCount; i++)
    {
      if (localObject == this.namedParameters[i])
        break;
    }
    i++;

    Accessor localAccessor = null;
    if ((i <= 0) || (i > this.numberOfBindPositions) || (this.outBindAccessors == null) || ((localAccessor = this.outBindAccessors[(i - 1)]) == null))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 6);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.lastIndex = i;

    if (this.streamList != null) {
      closeUsedStreams(i);
    }

    return localAccessor.getShort(this.currentRank);
  }

  public int getInt(String paramString)
    throws SQLException
  {
    SQLException sqlexception;
    if (!this.atLeastOneNamedParameter)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 90, "Ordinal binding and Named binding cannot be combined!");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject = paramString.toUpperCase().intern();

    int i;
    for (i = 0; i < this.parameterCount; i++)
    {
      if (localObject == this.namedParameters[i])
        break;
    }
    i++;

    Accessor localAccessor = null;
    if ((i <= 0) || (i > this.numberOfBindPositions) || (this.outBindAccessors == null) || ((localAccessor = this.outBindAccessors[(i - 1)]) == null))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 6);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.lastIndex = i;

    if (this.streamList != null) {
      closeUsedStreams(i);
    }

    return localAccessor.getInt(this.currentRank);
  }

  public long getLong(String paramString)
    throws SQLException
  {
    SQLException sqlexception;
    if (!this.atLeastOneNamedParameter)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 90, "Ordinal binding and Named binding cannot be combined!");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject = paramString.toUpperCase().intern();

    int i;
    for (i = 0; i < this.parameterCount; i++)
    {
      if (localObject == this.namedParameters[i])
        break;
    }
    i++;

    Accessor localAccessor = null;
    if ((i <= 0) || (i > this.numberOfBindPositions) || (this.outBindAccessors == null) || ((localAccessor = this.outBindAccessors[(i - 1)]) == null))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 6);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.lastIndex = i;

    if (this.streamList != null) {
      closeUsedStreams(i);
    }

    return localAccessor.getLong(this.currentRank);
  }

  public float getFloat(String paramString)
    throws SQLException
  {
    SQLException sqlexception;
    if (!this.atLeastOneNamedParameter)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 90, "Ordinal binding and Named binding cannot be combined!");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject = paramString.toUpperCase().intern();

    int i;
    for (i = 0; i < this.parameterCount; i++)
    {
      if (localObject == this.namedParameters[i])
        break;
    }
    i++;

    Accessor localAccessor = null;
    if ((i <= 0) || (i > this.numberOfBindPositions) || (this.outBindAccessors == null) || ((localAccessor = this.outBindAccessors[(i - 1)]) == null))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 6);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.lastIndex = i;

    if (this.streamList != null) {
      closeUsedStreams(i);
    }

    return localAccessor.getFloat(this.currentRank);
  }

  public double getDouble(String paramString)
    throws SQLException
  {
    SQLException sqlexception;
    if (!this.atLeastOneNamedParameter)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 90, "Ordinal binding and Named binding cannot be combined!");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject = paramString.toUpperCase().intern();

    int i;
    for (i = 0; i < this.parameterCount; i++)
    {
      if (localObject == this.namedParameters[i])
        break;
    }
    i++;

    Accessor localAccessor = null;
    if ((i <= 0) || (i > this.numberOfBindPositions) || (this.outBindAccessors == null) || ((localAccessor = this.outBindAccessors[(i - 1)]) == null))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 6);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.lastIndex = i;

    if (this.streamList != null) {
      closeUsedStreams(i);
    }

    return localAccessor.getDouble(this.currentRank);
  }

  public byte[] getBytes(String paramString)
    throws SQLException
  {
    SQLException sqlexception;
    if (!this.atLeastOneNamedParameter)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 90, "Ordinal binding and Named binding cannot be combined!");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject = paramString.toUpperCase().intern();

    int i;
    for (i = 0; i < this.parameterCount; i++)
    {
      if (localObject == this.namedParameters[i])
        break;
    }
    i++;

    Accessor localAccessor = null;
    if ((i <= 0) || (i > this.numberOfBindPositions) || (this.outBindAccessors == null) || ((localAccessor = this.outBindAccessors[(i - 1)]) == null))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 6);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.lastIndex = i;

    if (this.streamList != null) {
      closeUsedStreams(i);
    }

    return localAccessor.getBytes(this.currentRank);
  }

  public Date getDate(String paramString)
    throws SQLException
  {
    SQLException sqlexception;
    if (!this.atLeastOneNamedParameter)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 90, "Ordinal binding and Named binding cannot be combined!");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject = paramString.toUpperCase().intern();

    int i;
    for (i = 0; i < this.parameterCount; i++)
    {
      if (localObject == this.namedParameters[i])
        break;
    }
    i++;

    Accessor localAccessor = null;
    if ((i <= 0) || (i > this.numberOfBindPositions) || (this.outBindAccessors == null) || ((localAccessor = this.outBindAccessors[(i - 1)]) == null))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 6);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.lastIndex = i;

    if (this.streamList != null) {
      closeUsedStreams(i);
    }

    return localAccessor.getDate(this.currentRank);
  }

  public Time getTime(String paramString)
    throws SQLException
  {
    SQLException sqlexception;
    if (!this.atLeastOneNamedParameter)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 90, "Ordinal binding and Named binding cannot be combined!");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject = paramString.toUpperCase().intern();

    int i;
    for (i = 0; i < this.parameterCount; i++)
    {
      if (localObject == this.namedParameters[i])
        break;
    }
    i++;

    Accessor localAccessor = null;
    if ((i <= 0) || (i > this.numberOfBindPositions) || (this.outBindAccessors == null) || ((localAccessor = this.outBindAccessors[(i - 1)]) == null))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 6);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.lastIndex = i;

    if (this.streamList != null) {
      closeUsedStreams(i);
    }

    return localAccessor.getTime(this.currentRank);
  }

  public Timestamp getTimestamp(String paramString)
    throws SQLException
  {
    SQLException sqlexception;
    if (!this.atLeastOneNamedParameter)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 90, "Ordinal binding and Named binding cannot be combined!");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject = paramString.toUpperCase().intern();

    int i;
    for (i = 0; i < this.parameterCount; i++)
    {
      if (localObject == this.namedParameters[i])
        break;
    }
    i++;

    Accessor localAccessor = null;
    if ((i <= 0) || (i > this.numberOfBindPositions) || (this.outBindAccessors == null) || ((localAccessor = this.outBindAccessors[(i - 1)]) == null))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 6);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.lastIndex = i;

    if (this.streamList != null) {
      closeUsedStreams(i);
    }

    return localAccessor.getTimestamp(this.currentRank);
  }

  public Object getObject(String paramString)
    throws SQLException
  {
    SQLException sqlexception;
    if (!this.atLeastOneNamedParameter)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 90, "Ordinal binding and Named binding cannot be combined!");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject = paramString.toUpperCase().intern();

    int i;
    for (i = 0; i < this.parameterCount; i++)
    {
      if (localObject == this.namedParameters[i])
        break;
    }
    i++;

    Accessor localAccessor = null;
    if ((i <= 0) || (i > this.numberOfBindPositions) || (this.outBindAccessors == null) || ((localAccessor = this.outBindAccessors[(i - 1)]) == null))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 6);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.lastIndex = i;

    if (this.streamList != null) {
      closeUsedStreams(i);
    }

    return localAccessor.getObject(this.currentRank);
  }

  public BigDecimal getBigDecimal(String paramString)
    throws SQLException
  {
    SQLException sqlexception;
    if (!this.atLeastOneNamedParameter)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 90, "Ordinal binding and Named binding cannot be combined!");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject = paramString.toUpperCase().intern();

    int i;
    for (i = 0; i < this.parameterCount; i++)
    {
      if (localObject == this.namedParameters[i])
        break;
    }
    i++;

    Accessor localAccessor = null;
    if ((i <= 0) || (i > this.numberOfBindPositions) || (this.outBindAccessors == null) || ((localAccessor = this.outBindAccessors[(i - 1)]) == null))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 6);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.lastIndex = i;

    if (this.streamList != null) {
      closeUsedStreams(i);
    }

    return localAccessor.getBigDecimal(this.currentRank);
  }

  public BigDecimal getBigDecimal(String paramString, int paramInt)
    throws SQLException
  {
    SQLException sqlexception;
    if (!this.atLeastOneNamedParameter)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 90, "Ordinal binding and Named binding cannot be combined!");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject = paramString.toUpperCase().intern();

    int i;
    for (i = 0; i < this.parameterCount; i++)
    {
      if (localObject == this.namedParameters[i])
        break;
    }
    i++;

    Accessor localAccessor = null;
    if ((i <= 0) || (i > this.numberOfBindPositions) || (this.outBindAccessors == null) || ((localAccessor = this.outBindAccessors[(i - 1)]) == null))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 6);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.lastIndex = i;

    if (this.streamList != null) {
      closeUsedStreams(i);
    }

    return localAccessor.getBigDecimal(this.currentRank, paramInt);
  }

  public Object getObject(String paramString, Map paramMap)
    throws SQLException
  {
    SQLException sqlexception;
    if (!this.atLeastOneNamedParameter)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 90, "Ordinal binding and Named binding cannot be combined!");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject = paramString.toUpperCase().intern();

    int i;
    for (i = 0; i < this.parameterCount; i++)
    {
      if (localObject == this.namedParameters[i])
        break;
    }
    i++;

    Accessor localAccessor = null;
    if ((i <= 0) || (i > this.numberOfBindPositions) || (this.outBindAccessors == null) || ((localAccessor = this.outBindAccessors[(i - 1)]) == null))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 6);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.lastIndex = i;

    if (this.streamList != null) {
      closeUsedStreams(i);
    }

    return localAccessor.getObject(this.currentRank, paramMap);
  }

  public Ref getRef(String paramString)
    throws SQLException
  {
    SQLException sqlexception;
    if (!this.atLeastOneNamedParameter)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 90, "Ordinal binding and Named binding cannot be combined!");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject = paramString.toUpperCase().intern();

    int i;
    for (i = 0; i < this.parameterCount; i++)
    {
      if (localObject == this.namedParameters[i])
        break;
    }
    i++;

    Accessor localAccessor = null;
    if ((i <= 0) || (i > this.numberOfBindPositions) || (this.outBindAccessors == null) || ((localAccessor = this.outBindAccessors[(i - 1)]) == null))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 6);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.lastIndex = i;

    if (this.streamList != null) {
      closeUsedStreams(i);
    }

    return localAccessor.getREF(this.currentRank);
  }

  public Blob getBlob(String paramString)
    throws SQLException
  {
    SQLException sqlexception;
    if (!this.atLeastOneNamedParameter)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 90, "Ordinal binding and Named binding cannot be combined!");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject = paramString.toUpperCase().intern();

    int i;
    for (i = 0; i < this.parameterCount; i++)
    {
      if (localObject == this.namedParameters[i])
        break;
    }
    i++;

    Accessor localAccessor = null;
    if ((i <= 0) || (i > this.numberOfBindPositions) || (this.outBindAccessors == null) || ((localAccessor = this.outBindAccessors[(i - 1)]) == null))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 6);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.lastIndex = i;

    if (this.streamList != null) {
      closeUsedStreams(i);
    }

    return localAccessor.getBLOB(this.currentRank);
  }

  public Clob getClob(String paramString)
    throws SQLException
  {
    SQLException sqlexception;
    if (!this.atLeastOneNamedParameter)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 90, "Ordinal binding and Named binding cannot be combined!");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject = paramString.toUpperCase().intern();

    int i;
    for (i = 0; i < this.parameterCount; i++)
    {
      if (localObject == this.namedParameters[i])
        break;
    }
    i++;

    Accessor localAccessor = null;
    if ((i <= 0) || (i > this.numberOfBindPositions) || (this.outBindAccessors == null) || ((localAccessor = this.outBindAccessors[(i - 1)]) == null))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 6);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.lastIndex = i;

    if (this.streamList != null) {
      closeUsedStreams(i);
    }

    return localAccessor.getCLOB(this.currentRank);
  }

  public Array getArray(String paramString)
    throws SQLException
  {
    SQLException sqlexception;
    if (!this.atLeastOneNamedParameter)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 90, "Ordinal binding and Named binding cannot be combined!");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject = paramString.toUpperCase().intern();

    int i;
    for (i = 0; i < this.parameterCount; i++)
    {
      if (localObject == this.namedParameters[i])
        break;
    }
    i++;

    Accessor localAccessor = null;
    if ((i <= 0) || (i > this.numberOfBindPositions) || (this.outBindAccessors == null) || ((localAccessor = this.outBindAccessors[(i - 1)]) == null))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 6);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.lastIndex = i;

    if (this.streamList != null) {
      closeUsedStreams(i);
    }

    return localAccessor.getARRAY(this.currentRank);
  }

  public Date getDate(String paramString, Calendar paramCalendar)
    throws SQLException
  {
    SQLException sqlexception;
    if (!this.atLeastOneNamedParameter)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 90, "Ordinal binding and Named binding cannot be combined!");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject = paramString.toUpperCase().intern();

    int i;
    for (i = 0; i < this.parameterCount; i++)
    {
      if (localObject == this.namedParameters[i])
        break;
    }
    i++;

    Accessor localAccessor = null;
    if ((i <= 0) || (i > this.numberOfBindPositions) || (this.outBindAccessors == null) || ((localAccessor = this.outBindAccessors[(i - 1)]) == null))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 6);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.lastIndex = i;

    if (this.streamList != null) {
      closeUsedStreams(i);
    }

    return localAccessor.getDate(this.currentRank, paramCalendar);
  }

  public Time getTime(String paramString, Calendar paramCalendar)
    throws SQLException
  {
    SQLException sqlexception;
    if (!this.atLeastOneNamedParameter)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 90, "Ordinal binding and Named binding cannot be combined!");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject = paramString.toUpperCase().intern();

    int i;
    for (i = 0; i < this.parameterCount; i++)
    {
      if (localObject == this.namedParameters[i])
        break;
    }
    i++;

    Accessor localAccessor = null;
    if ((i <= 0) || (i > this.numberOfBindPositions) || (this.outBindAccessors == null) || ((localAccessor = this.outBindAccessors[(i - 1)]) == null))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 6);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.lastIndex = i;

    if (this.streamList != null) {
      closeUsedStreams(i);
    }

    return localAccessor.getTime(this.currentRank, paramCalendar);
  }

  public Timestamp getTimestamp(String paramString, Calendar paramCalendar)
    throws SQLException
  {
    SQLException sqlexception;
    if (!this.atLeastOneNamedParameter)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 90, "Ordinal binding and Named binding cannot be combined!");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject = paramString.toUpperCase().intern();

    int i;
    for (i = 0; i < this.parameterCount; i++)
    {
      if (localObject == this.namedParameters[i])
        break;
    }
    i++;

    Accessor localAccessor = null;
    if ((i <= 0) || (i > this.numberOfBindPositions) || (this.outBindAccessors == null) || ((localAccessor = this.outBindAccessors[(i - 1)]) == null))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 6);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.lastIndex = i;

    if (this.streamList != null) {
      closeUsedStreams(i);
    }

    return localAccessor.getTimestamp(this.currentRank, paramCalendar);
  }

  public URL getURL(String paramString)
    throws SQLException
  {
    SQLException sqlexception;
    if (!this.atLeastOneNamedParameter)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 90, "Ordinal binding and Named binding cannot be combined!");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject = paramString.toUpperCase().intern();

    int i;
    for (i = 0; i < this.parameterCount; i++)
    {
      if (localObject == this.namedParameters[i])
        break;
    }
    i++;

    Accessor localAccessor = null;
    if ((i <= 0) || (i > this.numberOfBindPositions) || (this.outBindAccessors == null) || ((localAccessor = this.outBindAccessors[(i - 1)]) == null))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 6);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.lastIndex = i;

    if (this.streamList != null) {
      closeUsedStreams(i);
    }

    return localAccessor.getURL(this.currentRank);
  }

  /** @deprecated */
  public InputStream getAsciiStream(String paramString)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createUnsupportedFeatureSqlException();
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void registerIndexTableOutParameter(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    throws SQLException
  {
    synchronized (this.connection)
    {
      int i = paramInt1 - 1;
      if ((i < 0) || (paramInt1 > this.numberOfBindPositions))
      {
        SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
        localSQLException.fillInStackTrace();
        throw localSQLException;
      }

      int j = getInternalType(paramInt3);

      resetBatch();
      this.currentRowNeedToPrepareBinds = true;

      if (this.currentRowBindAccessors == null) {
        this.currentRowBindAccessors = new Accessor[this.numberOfBindPositions];
      }
      this.currentRowBindAccessors[i] = allocateIndexTableAccessor(paramInt3, j, paramInt4, paramInt2, this.currentRowFormOfUse[i], true);

      this.hasIbtBind = true;
    }
  }

  PlsqlIndexTableAccessor allocateIndexTableAccessor(int paramInt1, int paramInt2, int paramInt3, int paramInt4, short paramShort, boolean paramBoolean)
    throws SQLException
  {
    return new PlsqlIndexTableAccessor(this, paramInt1, paramInt2, paramInt3, paramInt4, paramShort, paramBoolean);
  }

  public Object getPlsqlIndexTable(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      Datum[] arrayOfDatum = getOraclePlsqlIndexTable(paramInt);

      PlsqlIndexTableAccessor localPlsqlIndexTableAccessor = (PlsqlIndexTableAccessor)this.outBindAccessors[(paramInt - 1)];

      int i = localPlsqlIndexTableAccessor.elementInternalType;

      Object[] localObject1 = null;

      switch (i)
      {
      case 9:
        localObject1 = new String[arrayOfDatum.length];
        break;
      case 6:
        localObject1 = new BigDecimal[arrayOfDatum.length];
        break;
      default:
        SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 1, "Invalid column type");
        localSQLException.fillInStackTrace();
        throw localSQLException;
      }

      for (int j = 0; j < localObject1.length; j++) {
        localObject1[j] = ((arrayOfDatum[j] != null) && (arrayOfDatum[j].getLength() != 0L) ? arrayOfDatum[j].toJdbc() : null);
      }

      return localObject1;
    }
  }

  public Object getPlsqlIndexTable(int paramInt, Class paramClass)
    throws SQLException
  {
    synchronized (this.connection)
    {
      Datum[] arrayOfDatum = getOraclePlsqlIndexTable(paramInt);

      SQLException sqlexception;
      if ((paramClass == null) || (!paramClass.isPrimitive()))
      {
        sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68);
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }

      Object localObject1 = paramClass.getName();
      int i;
      if (((String)localObject1).equals("byte"))
      {
        byte[] localObject2 = new byte[arrayOfDatum.length];
        for (i = 0; i < arrayOfDatum.length; i++)
          localObject2[i] = (arrayOfDatum[i] != null ? arrayOfDatum[i].byteValue() : 0);
        return localObject2;
      }
      if (((String)localObject1).equals("char"))
      {
        char[] localObject2 = new char[arrayOfDatum.length];
        for (i = 0; i < arrayOfDatum.length; i++) {
          localObject2[i] = ((arrayOfDatum[i] != null) && (arrayOfDatum[i].getLength() != 0L) ? (char)arrayOfDatum[i].intValue() : 0);
        }
        return localObject2;
      }
      if (((String)localObject1).equals("double"))
      {
        double[] localObject2 = new double[arrayOfDatum.length];
        for (i = 0; i < arrayOfDatum.length; i++) {
          localObject2[i] = ((arrayOfDatum[i] != null) && (arrayOfDatum[i].getLength() != 0L) ? arrayOfDatum[i].doubleValue() : 0.0D);
        }
        return localObject2;
      }
      if (((String)localObject1).equals("float"))
      {
        float[] localObject2 = new float[arrayOfDatum.length];
        for (i = 0; i < arrayOfDatum.length; i++) {
          localObject2[i] = ((arrayOfDatum[i] != null) && (arrayOfDatum[i].getLength() != 0L) ? arrayOfDatum[i].floatValue() : 0.0F);
        }
        return localObject2;
      }
      if (((String)localObject1).equals("int"))
      {
        int[] localObject2 = new int[arrayOfDatum.length];
        for (i = 0; i < arrayOfDatum.length; i++) {
          localObject2[i] = ((arrayOfDatum[i] != null) && (arrayOfDatum[i].getLength() != 0L) ? arrayOfDatum[i].intValue() : 0);
        }
        return localObject2;
      }
      if (((String)localObject1).equals("long"))
      {
        long[] localObject2 = new long[arrayOfDatum.length];
        for (i = 0; i < arrayOfDatum.length; i++) {
          localObject2[i] = ((arrayOfDatum[i] != null) && (arrayOfDatum[i].getLength() != 0L) ? arrayOfDatum[i].longValue() : 0L);
        }
        return localObject2;
      }
      if (((String)localObject1).equals("short"))
      {
        short[] localObject2 = new short[arrayOfDatum.length];
        for (i = 0; i < arrayOfDatum.length; i++) {
          localObject2[i] = ((arrayOfDatum[i] != null) && (arrayOfDatum[i].getLength() != 0L) ? (short)arrayOfDatum[i].intValue() : 0);
        }
        return localObject2;
      }
      if (((String)localObject1).equals("boolean"))
      {
        boolean[] localObject2 = new boolean[arrayOfDatum.length];
        for (i = 0; i < arrayOfDatum.length; i++) {
          localObject2[i] = ((arrayOfDatum[i] != null) && (arrayOfDatum[i].getLength() != 0L) ? arrayOfDatum[i].booleanValue() : false);
        }
        return localObject2;
      }

      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 23);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }
  }

  public Datum[] getOraclePlsqlIndexTable(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      SQLException sqlexception;
      if (this.closed)
      {
        sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }

      if (this.atLeastOneNamedParameter)
      {
        sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 90, "Ordinal binding and Named binding cannot be combined!");
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }

      Object localObject1 = null;
      if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || (this.outBindAccessors == null) || ((localObject1 = this.outBindAccessors[(paramInt - 1)]) == null))
      {
        SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
        localSQLException.fillInStackTrace();
        throw localSQLException;
      }

      this.lastIndex = paramInt;

      if (this.streamList != null) {
        closeUsedStreams(paramInt);
      }

      return ((Accessor)localObject1).getOraclePlsqlIndexTable(this.currentRank);
    }
  }

  public boolean execute()
    throws SQLException
  {
    synchronized (this.connection) {
      ensureOpen();
      if ((this.atLeastOneNamedParameter) && (this.atLeastOneOrdinalParameter))
      {
        SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 90, "Ordinal binding and Named binding cannot be combined!");
        localSQLException.fillInStackTrace();
        throw localSQLException;
      }
      if (this.sqlObject.setNamedParameters(this.parameterCount, this.namedParameters))
        this.needToParse = true;
      return super.execute();
    }
  }

  public int executeUpdate()
    throws SQLException
  {
    synchronized (this.connection)
    {
      ensureOpen();
      if ((this.atLeastOneNamedParameter) && (this.atLeastOneOrdinalParameter))
      {
        SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 90, "Ordinal binding and Named binding cannot be combined!");
        localSQLException.fillInStackTrace();
        throw localSQLException;
      }
      if (this.sqlObject.setNamedParameters(this.parameterCount, this.namedParameters))
        this.needToParse = true;
      return super.executeUpdate();
    }
  }

  void releaseBuffers()
  {
    if (this.outBindAccessors != null)
    {
      int i = this.outBindAccessors.length;

      for (int j = 0; j < i; j++)
      {
        if (this.outBindAccessors[j] != null)
        {
          this.outBindAccessors[j].rowSpaceByte = null;
          this.outBindAccessors[j].rowSpaceChar = null;
        }
      }
    }

    super.releaseBuffers();
  }

  void doLocalInitialization()
  {
    if (this.outBindAccessors != null)
    {
      int i = this.outBindAccessors.length;

      for (int j = 0; j < i; j++)
      {
        if (this.outBindAccessors[j] != null)
        {
          this.outBindAccessors[j].rowSpaceByte = this.bindBytes;
          this.outBindAccessors[j].rowSpaceChar = this.bindChars;
        }
      }
    }
  }

  public void setArray(int paramInt, Array paramArray)
    throws SQLException
  {
    this.atLeastOneOrdinalParameter = true;
    setArrayInternal(paramInt, paramArray);
  }

  public void setBigDecimal(int paramInt, BigDecimal paramBigDecimal)
    throws SQLException
  {
    synchronized (this.connection)
    {
      this.atLeastOneOrdinalParameter = true;
      setBigDecimalInternal(paramInt, paramBigDecimal);
    }
  }

  public void setBlob(int paramInt, Blob paramBlob)
    throws SQLException
  {
    synchronized (this.connection)
    {
      this.atLeastOneOrdinalParameter = true;
      setBlobInternal(paramInt, paramBlob);
    }
  }

  public void setBoolean(int paramInt, boolean paramBoolean)
    throws SQLException
  {
    synchronized (this.connection)
    {
      this.atLeastOneOrdinalParameter = true;
      setBooleanInternal(paramInt, paramBoolean);
    }
  }

  public void setByte(int paramInt, byte paramByte)
    throws SQLException
  {
    synchronized (this.connection)
    {
      this.atLeastOneOrdinalParameter = true;
      setByteInternal(paramInt, paramByte);
    }
  }

  public void setBytes(int paramInt, byte[] paramArrayOfByte)
    throws SQLException
  {
    synchronized (this.connection)
    {
      this.atLeastOneOrdinalParameter = true;
      setBytesInternal(paramInt, paramArrayOfByte);
    }
  }

  public void setClob(int paramInt, Clob paramClob)
    throws SQLException
  {
    synchronized (this.connection)
    {
      this.atLeastOneOrdinalParameter = true;
      setClobInternal(paramInt, paramClob);
    }
  }

  public void setDate(int paramInt, Date paramDate)
    throws SQLException
  {
    synchronized (this.connection)
    {
      this.atLeastOneOrdinalParameter = true;
      setDateInternal(paramInt, paramDate);
    }
  }

  public void setDate(int paramInt, Date paramDate, Calendar paramCalendar)
    throws SQLException
  {
    synchronized (this.connection)
    {
      this.atLeastOneOrdinalParameter = true;
      setDateInternal(paramInt, paramDate, paramCalendar);
    }
  }

  public void setDouble(int paramInt, double paramDouble)
    throws SQLException
  {
    synchronized (this.connection)
    {
      this.atLeastOneOrdinalParameter = true;
      setDoubleInternal(paramInt, paramDouble);
    }
  }

  public void setFloat(int paramInt, float paramFloat)
    throws SQLException
  {
    synchronized (this.connection)
    {
      this.atLeastOneOrdinalParameter = true;
      setFloatInternal(paramInt, paramFloat);
    }
  }

  public void setInt(int paramInt1, int paramInt2)
    throws SQLException
  {
    synchronized (this.connection)
    {
      this.atLeastOneOrdinalParameter = true;
      setIntInternal(paramInt1, paramInt2);
    }
  }

  public void setLong(int paramInt, long paramLong)
    throws SQLException
  {
    synchronized (this.connection)
    {
      this.atLeastOneOrdinalParameter = true;
      setLongInternal(paramInt, paramLong);
    }
  }

  public void setNClob(int paramInt, NClob paramNClob)
    throws SQLException
  {
    synchronized (this.connection)
    {
      this.atLeastOneOrdinalParameter = true;
      setNClobInternal(paramInt, paramNClob);
    }
  }

  public void setNString(int paramInt, String paramString)
    throws SQLException
  {
    synchronized (this.connection)
    {
      this.atLeastOneOrdinalParameter = true;
      setNStringInternal(paramInt, paramString);
    }
  }

  public void setObject(int paramInt, Object paramObject)
    throws SQLException
  {
    this.atLeastOneOrdinalParameter = true;
    setObjectInternal(paramInt, paramObject);
  }

  public void setObject(int paramInt1, Object paramObject, int paramInt2)
    throws SQLException
  {
    this.atLeastOneOrdinalParameter = true;
    setObjectInternal(paramInt1, paramObject, paramInt2);
  }

  public void setRef(int paramInt, Ref paramRef)
    throws SQLException
  {
    this.atLeastOneOrdinalParameter = true;
    setRefInternal(paramInt, paramRef);
  }

  public void setRowId(int paramInt, RowId paramRowId)
    throws SQLException
  {
    synchronized (this.connection)
    {
      this.atLeastOneOrdinalParameter = true;
      setRowIdInternal(paramInt, paramRowId);
    }
  }

  public void setShort(int paramInt, short paramShort)
    throws SQLException
  {
    synchronized (this.connection)
    {
      this.atLeastOneOrdinalParameter = true;
      setShortInternal(paramInt, paramShort);
    }
  }

  public void setSQLXML(int paramInt, SQLXML paramSQLXML)
    throws SQLException
  {
    synchronized (this.connection)
    {
      this.atLeastOneOrdinalParameter = true;
      setSQLXMLInternal(paramInt, paramSQLXML);
    }
  }

  public void setString(int paramInt, String paramString)
    throws SQLException
  {
    synchronized (this.connection)
    {
      this.atLeastOneOrdinalParameter = true;
      setStringInternal(paramInt, paramString);
    }
  }

  public void setTime(int paramInt, Time paramTime)
    throws SQLException
  {
    synchronized (this.connection)
    {
      this.atLeastOneOrdinalParameter = true;
      setTimeInternal(paramInt, paramTime);
    }
  }

  public void setTime(int paramInt, Time paramTime, Calendar paramCalendar)
    throws SQLException
  {
    synchronized (this.connection)
    {
      this.atLeastOneOrdinalParameter = true;
      setTimeInternal(paramInt, paramTime, paramCalendar);
    }
  }

  public void setTimestamp(int paramInt, Timestamp paramTimestamp)
    throws SQLException
  {
    synchronized (this.connection)
    {
      this.atLeastOneOrdinalParameter = true;
      setTimestampInternal(paramInt, paramTimestamp);
    }
  }

  public void setTimestamp(int paramInt, Timestamp paramTimestamp, Calendar paramCalendar)
    throws SQLException
  {
    synchronized (this.connection)
    {
      this.atLeastOneOrdinalParameter = true;
      setTimestampInternal(paramInt, paramTimestamp, paramCalendar);
    }
  }

  public void setURL(int paramInt, URL paramURL)
    throws SQLException
  {
    synchronized (this.connection)
    {
      this.atLeastOneOrdinalParameter = true;
      setURLInternal(paramInt, paramURL);
    }
  }

  public void setARRAY(int paramInt, ARRAY paramARRAY)
    throws SQLException
  {
    this.atLeastOneOrdinalParameter = true;
    setARRAYInternal(paramInt, paramARRAY);
  }

  public void setBFILE(int paramInt, BFILE paramBFILE)
    throws SQLException
  {
    synchronized (this.connection)
    {
      this.atLeastOneOrdinalParameter = true;
      setBFILEInternal(paramInt, paramBFILE);
    }
  }

  public void setBfile(int paramInt, BFILE paramBFILE)
    throws SQLException
  {
    synchronized (this.connection)
    {
      this.atLeastOneOrdinalParameter = true;
      setBfileInternal(paramInt, paramBFILE);
    }
  }

  public void setBinaryFloat(int paramInt, float paramFloat)
    throws SQLException
  {
    synchronized (this.connection)
    {
      this.atLeastOneOrdinalParameter = true;
      setBinaryFloatInternal(paramInt, paramFloat);
    }
  }

  public void setBinaryFloat(int paramInt, BINARY_FLOAT paramBINARY_FLOAT)
    throws SQLException
  {
    synchronized (this.connection)
    {
      this.atLeastOneOrdinalParameter = true;
      setBinaryFloatInternal(paramInt, paramBINARY_FLOAT);
    }
  }

  public void setBinaryDouble(int paramInt, double paramDouble)
    throws SQLException
  {
    synchronized (this.connection)
    {
      this.atLeastOneOrdinalParameter = true;
      setBinaryDoubleInternal(paramInt, paramDouble);
    }
  }

  public void setBinaryDouble(int paramInt, BINARY_DOUBLE paramBINARY_DOUBLE)
    throws SQLException
  {
    synchronized (this.connection)
    {
      this.atLeastOneOrdinalParameter = true;
      setBinaryDoubleInternal(paramInt, paramBINARY_DOUBLE);
    }
  }

  public void setBLOB(int paramInt, BLOB paramBLOB)
    throws SQLException
  {
    synchronized (this.connection)
    {
      this.atLeastOneOrdinalParameter = true;
      setBLOBInternal(paramInt, paramBLOB);
    }
  }

  public void setCHAR(int paramInt, CHAR paramCHAR)
    throws SQLException
  {
    synchronized (this.connection)
    {
      this.atLeastOneOrdinalParameter = true;
      setCHARInternal(paramInt, paramCHAR);
    }
  }

  public void setCLOB(int paramInt, CLOB paramCLOB)
    throws SQLException
  {
    synchronized (this.connection)
    {
      this.atLeastOneOrdinalParameter = true;
      setCLOBInternal(paramInt, paramCLOB);
    }
  }

  public void setCursor(int paramInt, ResultSet paramResultSet)
    throws SQLException
  {
    synchronized (this.connection)
    {
      this.atLeastOneOrdinalParameter = true;
      setCursorInternal(paramInt, paramResultSet);
    }
  }

  public void setCustomDatum(int paramInt, CustomDatum paramCustomDatum)
    throws SQLException
  {
    this.atLeastOneOrdinalParameter = true;
    setCustomDatumInternal(paramInt, paramCustomDatum);
  }

  public void setDATE(int paramInt, DATE paramDATE)
    throws SQLException
  {
    synchronized (this.connection)
    {
      this.atLeastOneOrdinalParameter = true;
      setDATEInternal(paramInt, paramDATE);
    }
  }

  public void setFixedCHAR(int paramInt, String paramString)
    throws SQLException
  {
    synchronized (this.connection)
    {
      this.atLeastOneOrdinalParameter = true;
      setFixedCHARInternal(paramInt, paramString);
    }
  }

  public void setINTERVALDS(int paramInt, INTERVALDS paramINTERVALDS)
    throws SQLException
  {
    synchronized (this.connection)
    {
      this.atLeastOneOrdinalParameter = true;
      setINTERVALDSInternal(paramInt, paramINTERVALDS);
    }
  }

  public void setINTERVALYM(int paramInt, INTERVALYM paramINTERVALYM)
    throws SQLException
  {
    synchronized (this.connection)
    {
      this.atLeastOneOrdinalParameter = true;
      setINTERVALYMInternal(paramInt, paramINTERVALYM);
    }
  }

  public void setNUMBER(int paramInt, NUMBER paramNUMBER)
    throws SQLException
  {
    synchronized (this.connection)
    {
      this.atLeastOneOrdinalParameter = true;
      setNUMBERInternal(paramInt, paramNUMBER);
    }
  }

  public void setOPAQUE(int paramInt, OPAQUE paramOPAQUE)
    throws SQLException
  {
    this.atLeastOneOrdinalParameter = true;
    setOPAQUEInternal(paramInt, paramOPAQUE);
  }

  public void setOracleObject(int paramInt, Datum paramDatum)
    throws SQLException
  {
    this.atLeastOneOrdinalParameter = true;
    setOracleObjectInternal(paramInt, paramDatum);
  }

  public void setORAData(int paramInt, ORAData paramORAData)
    throws SQLException
  {
    this.atLeastOneOrdinalParameter = true;
    setORADataInternal(paramInt, paramORAData);
  }

  public void setRAW(int paramInt, RAW paramRAW)
    throws SQLException
  {
    synchronized (this.connection)
    {
      this.atLeastOneOrdinalParameter = true;
      setRAWInternal(paramInt, paramRAW);
    }
  }

  public void setREF(int paramInt, REF paramREF)
    throws SQLException
  {
    this.atLeastOneOrdinalParameter = true;
    setREFInternal(paramInt, paramREF);
  }

  public void setRefType(int paramInt, REF paramREF)
    throws SQLException
  {
    this.atLeastOneOrdinalParameter = true;
    setRefTypeInternal(paramInt, paramREF);
  }

  public void setROWID(int paramInt, ROWID paramROWID)
    throws SQLException
  {
    synchronized (this.connection)
    {
      this.atLeastOneOrdinalParameter = true;
      setROWIDInternal(paramInt, paramROWID);
    }
  }

  public void setSTRUCT(int paramInt, STRUCT paramSTRUCT)
    throws SQLException
  {
    this.atLeastOneOrdinalParameter = true;
    setSTRUCTInternal(paramInt, paramSTRUCT);
  }

  public void setTIMESTAMPLTZ(int paramInt, TIMESTAMPLTZ paramTIMESTAMPLTZ)
    throws SQLException
  {
    synchronized (this.connection)
    {
      this.atLeastOneOrdinalParameter = true;
      setTIMESTAMPLTZInternal(paramInt, paramTIMESTAMPLTZ);
    }
  }

  public void setTIMESTAMPTZ(int paramInt, TIMESTAMPTZ paramTIMESTAMPTZ)
    throws SQLException
  {
    synchronized (this.connection)
    {
      this.atLeastOneOrdinalParameter = true;
      setTIMESTAMPTZInternal(paramInt, paramTIMESTAMPTZ);
    }
  }

  public void setTIMESTAMP(int paramInt, TIMESTAMP paramTIMESTAMP)
    throws SQLException
  {
    synchronized (this.connection)
    {
      this.atLeastOneOrdinalParameter = true;
      setTIMESTAMPInternal(paramInt, paramTIMESTAMP);
    }
  }

  public void setBlob(int paramInt, InputStream paramInputStream)
    throws SQLException
  {
    synchronized (this.connection)
    {
      this.atLeastOneOrdinalParameter = true;
      setBlobInternal(paramInt, paramInputStream);
    }
  }

  public void setBlob(int paramInt, InputStream paramInputStream, long paramLong)
    throws SQLException
  {
    synchronized (this.connection)
    {
      if (paramLong < 0L)
      {
        SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68, "length for setBlob() cannot be negative");
        localSQLException.fillInStackTrace();
        throw localSQLException;
      }

      this.atLeastOneOrdinalParameter = true;
      setBlobInternal(paramInt, paramInputStream, paramLong);
    }
  }

  public void setClob(int paramInt, Reader paramReader)
    throws SQLException
  {
    synchronized (this.connection)
    {
      this.atLeastOneOrdinalParameter = true;
      setClobInternal(paramInt, paramReader);
    }
  }

  public void setClob(int paramInt, Reader paramReader, long paramLong)
    throws SQLException
  {
    synchronized (this.connection)
    {
      if (paramLong < 0L)
      {
        SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68, "length for setClob() cannot be negative");
        localSQLException.fillInStackTrace();
        throw localSQLException;
      }
      this.atLeastOneOrdinalParameter = true;
      setClobInternal(paramInt, paramReader, paramLong);
    }
  }

  public void setNClob(int paramInt, Reader paramReader)
    throws SQLException
  {
    synchronized (this.connection)
    {
      this.atLeastOneOrdinalParameter = true;
      setNClobInternal(paramInt, paramReader);
    }
  }

  public void setNClob(int paramInt, Reader paramReader, long paramLong)
    throws SQLException
  {
    synchronized (this.connection)
    {
      this.atLeastOneOrdinalParameter = true;
      setNClobInternal(paramInt, paramReader, paramLong);
    }
  }

  public void setAsciiStream(int paramInt, InputStream paramInputStream)
    throws SQLException
  {
    synchronized (this.connection)
    {
      this.atLeastOneOrdinalParameter = true;
      setAsciiStreamInternal(paramInt, paramInputStream);
    }
  }

  public void setAsciiStream(int paramInt1, InputStream paramInputStream, int paramInt2)
    throws SQLException
  {
    synchronized (this.connection)
    {
      this.atLeastOneOrdinalParameter = true;
      setAsciiStreamInternal(paramInt1, paramInputStream, paramInt2);
    }
  }

  public void setAsciiStream(int paramInt, InputStream paramInputStream, long paramLong)
    throws SQLException
  {
    synchronized (this.connection)
    {
      this.atLeastOneOrdinalParameter = true;
      setAsciiStreamInternal(paramInt, paramInputStream, paramLong);
    }
  }

  public void setBinaryStream(int paramInt, InputStream paramInputStream)
    throws SQLException
  {
    synchronized (this.connection)
    {
      this.atLeastOneOrdinalParameter = true;
      setBinaryStreamInternal(paramInt, paramInputStream);
    }
  }

  public void setBinaryStream(int paramInt1, InputStream paramInputStream, int paramInt2)
    throws SQLException
  {
    synchronized (this.connection)
    {
      this.atLeastOneOrdinalParameter = true;
      setBinaryStreamInternal(paramInt1, paramInputStream, paramInt2);
    }
  }

  public void setBinaryStream(int paramInt, InputStream paramInputStream, long paramLong)
    throws SQLException
  {
    synchronized (this.connection)
    {
      this.atLeastOneOrdinalParameter = true;
      setBinaryStreamInternal(paramInt, paramInputStream, paramLong);
    }
  }

  public void setCharacterStream(int paramInt, Reader paramReader)
    throws SQLException
  {
    synchronized (this.connection)
    {
      this.atLeastOneOrdinalParameter = true;
      setCharacterStreamInternal(paramInt, paramReader);
    }
  }

  public void setCharacterStream(int paramInt1, Reader paramReader, int paramInt2)
    throws SQLException
  {
    synchronized (this.connection)
    {
      this.atLeastOneOrdinalParameter = true;
      setCharacterStreamInternal(paramInt1, paramReader, paramInt2);
    }
  }

  public void setCharacterStream(int paramInt, Reader paramReader, long paramLong)
    throws SQLException
  {
    synchronized (this.connection)
    {
      this.atLeastOneOrdinalParameter = true;
      setCharacterStreamInternal(paramInt, paramReader, paramLong);
    }
  }

  public void setNCharacterStream(int paramInt, Reader paramReader)
    throws SQLException
  {
    synchronized (this.connection)
    {
      this.atLeastOneOrdinalParameter = true;
      setNCharacterStreamInternal(paramInt, paramReader);
    }
  }

  public void setNCharacterStream(int paramInt, Reader paramReader, long paramLong)
    throws SQLException
  {
    synchronized (this.connection)
    {
      this.atLeastOneOrdinalParameter = true;
      setNCharacterStreamInternal(paramInt, paramReader, paramLong);
    }
  }

  public void setUnicodeStream(int paramInt1, InputStream paramInputStream, int paramInt2)
    throws SQLException
  {
    synchronized (this.connection)
    {
      this.atLeastOneOrdinalParameter = true;
      setUnicodeStreamInternal(paramInt1, paramInputStream, paramInt2);
    }
  }

  public void setArray(String paramString, Array paramArray)
    throws SQLException
  {
    int i = addNamedPara(paramString);
    setArrayInternal(i, paramArray);
  }

  public void setBigDecimal(String paramString, BigDecimal paramBigDecimal)
    throws SQLException
  {
    int i = addNamedPara(paramString);
    setBigDecimalInternal(i, paramBigDecimal);
  }

  public void setBlob(String paramString, Blob paramBlob)
    throws SQLException
  {
    int i = addNamedPara(paramString);
    setBlobInternal(i, paramBlob);
  }

  public void setBoolean(String paramString, boolean paramBoolean)
    throws SQLException
  {
    int i = addNamedPara(paramString);
    setBooleanInternal(i, paramBoolean);
  }

  public void setByte(String paramString, byte paramByte)
    throws SQLException
  {
    int i = addNamedPara(paramString);
    setByteInternal(i, paramByte);
  }

  public void setBytes(String paramString, byte[] paramArrayOfByte)
    throws SQLException
  {
    int i = addNamedPara(paramString);
    setBytesInternal(i, paramArrayOfByte);
  }

  public void setClob(String paramString, Clob paramClob)
    throws SQLException
  {
    int i = addNamedPara(paramString);
    setClobInternal(i, paramClob);
  }

  public void setDate(String paramString, Date paramDate)
    throws SQLException
  {
    int i = addNamedPara(paramString);
    setDateInternal(i, paramDate);
  }

  public void setDate(String paramString, Date paramDate, Calendar paramCalendar)
    throws SQLException
  {
    int i = addNamedPara(paramString);
    setDateInternal(i, paramDate, paramCalendar);
  }

  public void setDouble(String paramString, double paramDouble)
    throws SQLException
  {
    int i = addNamedPara(paramString);
    setDoubleInternal(i, paramDouble);
  }

  public void setFloat(String paramString, float paramFloat)
    throws SQLException
  {
    int i = addNamedPara(paramString);
    setFloatInternal(i, paramFloat);
  }

  public void setInt(String paramString, int paramInt)
    throws SQLException
  {
    int i = addNamedPara(paramString);
    setIntInternal(i, paramInt);
  }

  public void setLong(String paramString, long paramLong)
    throws SQLException
  {
    int i = addNamedPara(paramString);
    setLongInternal(i, paramLong);
  }

  public void setNClob(String paramString, NClob paramNClob)
    throws SQLException
  {
    int i = addNamedPara(paramString);
    setNClobInternal(i, paramNClob);
  }

  public void setNString(String paramString1, String paramString2)
    throws SQLException
  {
    int i = addNamedPara(paramString1);
    setNStringInternal(i, paramString2);
  }

  public void setObject(String paramString, Object paramObject)
    throws SQLException
  {
    int i = addNamedPara(paramString);
    setObjectInternal(i, paramObject);
  }

  public void setObject(String paramString, Object paramObject, int paramInt)
    throws SQLException
  {
    int i = addNamedPara(paramString);
    setObjectInternal(i, paramObject, paramInt);
  }

  public void setRef(String paramString, Ref paramRef)
    throws SQLException
  {
    int i = addNamedPara(paramString);
    setRefInternal(i, paramRef);
  }

  public void setRowId(String paramString, RowId paramRowId)
    throws SQLException
  {
    int i = addNamedPara(paramString);
    setRowIdInternal(i, paramRowId);
  }

  public void setShort(String paramString, short paramShort)
    throws SQLException
  {
    int i = addNamedPara(paramString);
    setShortInternal(i, paramShort);
  }

  public void setSQLXML(String paramString, SQLXML paramSQLXML)
    throws SQLException
  {
    int i = addNamedPara(paramString);
    setSQLXMLInternal(i, paramSQLXML);
  }

  public void setString(String paramString1, String paramString2)
    throws SQLException
  {
    int i = addNamedPara(paramString1);
    setStringInternal(i, paramString2);
  }

  public void setTime(String paramString, Time paramTime)
    throws SQLException
  {
    int i = addNamedPara(paramString);
    setTimeInternal(i, paramTime);
  }

  public void setTime(String paramString, Time paramTime, Calendar paramCalendar)
    throws SQLException
  {
    int i = addNamedPara(paramString);
    setTimeInternal(i, paramTime, paramCalendar);
  }

  public void setTimestamp(String paramString, Timestamp paramTimestamp)
    throws SQLException
  {
    int i = addNamedPara(paramString);
    setTimestampInternal(i, paramTimestamp);
  }

  public void setTimestamp(String paramString, Timestamp paramTimestamp, Calendar paramCalendar)
    throws SQLException
  {
    int i = addNamedPara(paramString);
    setTimestampInternal(i, paramTimestamp, paramCalendar);
  }

  public void setURL(String paramString, URL paramURL)
    throws SQLException
  {
    int i = addNamedPara(paramString);
    setURLInternal(i, paramURL);
  }

  public void setARRAY(String paramString, ARRAY paramARRAY)
    throws SQLException
  {
    int i = addNamedPara(paramString);
    setARRAYInternal(i, paramARRAY);
  }

  public void setBFILE(String paramString, BFILE paramBFILE)
    throws SQLException
  {
    int i = addNamedPara(paramString);
    setBFILEInternal(i, paramBFILE);
  }

  public void setBfile(String paramString, BFILE paramBFILE)
    throws SQLException
  {
    int i = addNamedPara(paramString);
    setBfileInternal(i, paramBFILE);
  }

  public void setBinaryFloat(String paramString, float paramFloat)
    throws SQLException
  {
    int i = addNamedPara(paramString);
    setBinaryFloatInternal(i, paramFloat);
  }

  public void setBinaryFloat(String paramString, BINARY_FLOAT paramBINARY_FLOAT)
    throws SQLException
  {
    int i = addNamedPara(paramString);
    setBinaryFloatInternal(i, paramBINARY_FLOAT);
  }

  public void setBinaryDouble(String paramString, double paramDouble)
    throws SQLException
  {
    int i = addNamedPara(paramString);
    setBinaryDoubleInternal(i, paramDouble);
  }

  public void setBinaryDouble(String paramString, BINARY_DOUBLE paramBINARY_DOUBLE)
    throws SQLException
  {
    int i = addNamedPara(paramString);
    setBinaryDoubleInternal(i, paramBINARY_DOUBLE);
  }

  public void setBLOB(String paramString, BLOB paramBLOB)
    throws SQLException
  {
    int i = addNamedPara(paramString);
    setBLOBInternal(i, paramBLOB);
  }

  public void setCHAR(String paramString, CHAR paramCHAR)
    throws SQLException
  {
    int i = addNamedPara(paramString);
    setCHARInternal(i, paramCHAR);
  }

  public void setCLOB(String paramString, CLOB paramCLOB)
    throws SQLException
  {
    int i = addNamedPara(paramString);
    setCLOBInternal(i, paramCLOB);
  }

  public void setCursor(String paramString, ResultSet paramResultSet)
    throws SQLException
  {
    int i = addNamedPara(paramString);
    setCursorInternal(i, paramResultSet);
  }

  public void setCustomDatum(String paramString, CustomDatum paramCustomDatum)
    throws SQLException
  {
    int i = addNamedPara(paramString);
    setCustomDatumInternal(i, paramCustomDatum);
  }

  public void setDATE(String paramString, DATE paramDATE)
    throws SQLException
  {
    int i = addNamedPara(paramString);
    setDATEInternal(i, paramDATE);
  }

  public void setFixedCHAR(String paramString1, String paramString2)
    throws SQLException
  {
    int i = addNamedPara(paramString1);
    setFixedCHARInternal(i, paramString2);
  }

  public void setINTERVALDS(String paramString, INTERVALDS paramINTERVALDS)
    throws SQLException
  {
    int i = addNamedPara(paramString);
    setINTERVALDSInternal(i, paramINTERVALDS);
  }

  public void setINTERVALYM(String paramString, INTERVALYM paramINTERVALYM)
    throws SQLException
  {
    int i = addNamedPara(paramString);
    setINTERVALYMInternal(i, paramINTERVALYM);
  }

  public void setNUMBER(String paramString, NUMBER paramNUMBER)
    throws SQLException
  {
    int i = addNamedPara(paramString);
    setNUMBERInternal(i, paramNUMBER);
  }

  public void setOPAQUE(String paramString, OPAQUE paramOPAQUE)
    throws SQLException
  {
    int i = addNamedPara(paramString);
    setOPAQUEInternal(i, paramOPAQUE);
  }

  public void setOracleObject(String paramString, Datum paramDatum)
    throws SQLException
  {
    int i = addNamedPara(paramString);
    setOracleObjectInternal(i, paramDatum);
  }

  public void setORAData(String paramString, ORAData paramORAData)
    throws SQLException
  {
    int i = addNamedPara(paramString);
    setORADataInternal(i, paramORAData);
  }

  public void setRAW(String paramString, RAW paramRAW)
    throws SQLException
  {
    int i = addNamedPara(paramString);
    setRAWInternal(i, paramRAW);
  }

  public void setREF(String paramString, REF paramREF)
    throws SQLException
  {
    int i = addNamedPara(paramString);
    setREFInternal(i, paramREF);
  }

  public void setRefType(String paramString, REF paramREF)
    throws SQLException
  {
    int i = addNamedPara(paramString);
    setRefTypeInternal(i, paramREF);
  }

  public void setROWID(String paramString, ROWID paramROWID)
    throws SQLException
  {
    int i = addNamedPara(paramString);
    setROWIDInternal(i, paramROWID);
  }

  public void setSTRUCT(String paramString, STRUCT paramSTRUCT)
    throws SQLException
  {
    int i = addNamedPara(paramString);
    setSTRUCTInternal(i, paramSTRUCT);
  }

  public void setTIMESTAMPLTZ(String paramString, TIMESTAMPLTZ paramTIMESTAMPLTZ)
    throws SQLException
  {
    int i = addNamedPara(paramString);
    setTIMESTAMPLTZInternal(i, paramTIMESTAMPLTZ);
  }

  public void setTIMESTAMPTZ(String paramString, TIMESTAMPTZ paramTIMESTAMPTZ)
    throws SQLException
  {
    int i = addNamedPara(paramString);
    setTIMESTAMPTZInternal(i, paramTIMESTAMPTZ);
  }

  public void setTIMESTAMP(String paramString, TIMESTAMP paramTIMESTAMP)
    throws SQLException
  {
    int i = addNamedPara(paramString);
    setTIMESTAMPInternal(i, paramTIMESTAMP);
  }

  public void setBlob(String paramString, InputStream paramInputStream)
    throws SQLException
  {
    int i = addNamedPara(paramString);
    setBlobInternal(i, paramInputStream);
  }

  public void setBlob(String paramString, InputStream paramInputStream, long paramLong)
    throws SQLException
  {
    if (paramLong < 0L)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68, "length for setBlob() cannot be negative");
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    int i = addNamedPara(paramString);
    setBlobInternal(i, paramInputStream, paramLong);
  }

  public void setClob(String paramString, Reader paramReader)
    throws SQLException
  {
    int i = addNamedPara(paramString);
    setClobInternal(i, paramReader);
  }

  public void setClob(String paramString, Reader paramReader, long paramLong)
    throws SQLException
  {
    if (paramLong < 0L)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68, "length for setClob() cannot be negative");
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
    int i = addNamedPara(paramString);
    setClobInternal(i, paramReader, paramLong);
  }

  public void setNClob(String paramString, Reader paramReader)
    throws SQLException
  {
    int i = addNamedPara(paramString);
    setNClobInternal(i, paramReader);
  }

  public void setNClob(String paramString, Reader paramReader, long paramLong)
    throws SQLException
  {
    int i = addNamedPara(paramString);
    setNClobInternal(i, paramReader, paramLong);
  }

  public void setAsciiStream(String paramString, InputStream paramInputStream)
    throws SQLException
  {
    int i = addNamedPara(paramString);
    setAsciiStreamInternal(i, paramInputStream);
  }

  public void setAsciiStream(String paramString, InputStream paramInputStream, int paramInt)
    throws SQLException
  {
    int i = addNamedPara(paramString);
    setAsciiStreamInternal(i, paramInputStream, paramInt);
  }

  public void setAsciiStream(String paramString, InputStream paramInputStream, long paramLong)
    throws SQLException
  {
    int i = addNamedPara(paramString);
    setAsciiStreamInternal(i, paramInputStream, paramLong);
  }

  public void setBinaryStream(String paramString, InputStream paramInputStream)
    throws SQLException
  {
    int i = addNamedPara(paramString);
    setBinaryStreamInternal(i, paramInputStream);
  }

  public void setBinaryStream(String paramString, InputStream paramInputStream, int paramInt)
    throws SQLException
  {
    int i = addNamedPara(paramString);
    setBinaryStreamInternal(i, paramInputStream, paramInt);
  }

  public void setBinaryStream(String paramString, InputStream paramInputStream, long paramLong)
    throws SQLException
  {
    int i = addNamedPara(paramString);
    setBinaryStreamInternal(i, paramInputStream, paramLong);
  }

  public void setCharacterStream(String paramString, Reader paramReader)
    throws SQLException
  {
    int i = addNamedPara(paramString);
    setCharacterStreamInternal(i, paramReader);
  }

  public void setCharacterStream(String paramString, Reader paramReader, int paramInt)
    throws SQLException
  {
    int i = addNamedPara(paramString);
    setCharacterStreamInternal(i, paramReader, paramInt);
  }

  public void setCharacterStream(String paramString, Reader paramReader, long paramLong)
    throws SQLException
  {
    int i = addNamedPara(paramString);
    setCharacterStreamInternal(i, paramReader, paramLong);
  }

  public void setNCharacterStream(String paramString, Reader paramReader)
    throws SQLException
  {
    int i = addNamedPara(paramString);
    setNCharacterStreamInternal(i, paramReader);
  }

  public void setNCharacterStream(String paramString, Reader paramReader, long paramLong)
    throws SQLException
  {
    int i = addNamedPara(paramString);
    setNCharacterStreamInternal(i, paramReader, paramLong);
  }

  public void setUnicodeStream(String paramString, InputStream paramInputStream, int paramInt)
    throws SQLException
  {
    int i = addNamedPara(paramString);
    setUnicodeStreamInternal(i, paramInputStream, paramInt);
  }

  public void setNull(String paramString1, int paramInt, String paramString2)
    throws SQLException
  {
    int i = addNamedPara(paramString1);
    setNullInternal(i, paramInt, paramString2);
  }

  public void setNull(String paramString, int paramInt)
    throws SQLException
  {
    int i = addNamedPara(paramString);
    setNullInternal(i, paramInt);
  }

  public void setStructDescriptor(String paramString, StructDescriptor paramStructDescriptor)
    throws SQLException
  {
    int i = addNamedPara(paramString);
    setStructDescriptorInternal(i, paramStructDescriptor);
  }

  public void setObject(String paramString, Object paramObject, int paramInt1, int paramInt2)
    throws SQLException
  {
    int i = addNamedPara(paramString);
    setObjectInternal(i, paramObject, paramInt1, paramInt2);
  }

  public void setPlsqlIndexTable(int paramInt1, Object paramObject, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
    throws SQLException
  {
    synchronized (this.connection)
    {
      this.atLeastOneOrdinalParameter = true;
      setPlsqlIndexTableInternal(paramInt1, paramObject, paramInt2, paramInt3, paramInt4, paramInt5);
    }
  }

  int addNamedPara(String paramString)
    throws SQLException
  {
    if (this.closed)
    {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    String localObject = paramString.toUpperCase().intern();

    for (int i = 0; i < this.parameterCount; i++)
    {
      if (localObject == this.namedParameters[i]) {
        return i + 1;
      }
    }
    if (this.parameterCount >= this.namedParameters.length)
    {
      String[] arrayOfString = new String[this.namedParameters.length * 2];
      System.arraycopy(this.namedParameters, 0, arrayOfString, 0, this.namedParameters.length);
      this.namedParameters = arrayOfString;
    }

    this.namedParameters[(this.parameterCount++)] = localObject;

    this.atLeastOneNamedParameter = true;
    return this.parameterCount;
  }

  public Reader getCharacterStream(String paramString)
    throws SQLException
  {
    if (!this.atLeastOneNamedParameter)
    {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 90, "Ordinal binding and Named binding cannot be combined!");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject = paramString.toUpperCase().intern();

    int i;
    for (i = 0; i < this.parameterCount; i++)
    {
      if (localObject == this.namedParameters[i])
        break;
    }
    i++;

    Accessor localAccessor = null;
    if ((i <= 0) || (i > this.numberOfBindPositions) || (this.outBindAccessors == null) || ((localAccessor = this.outBindAccessors[(i - 1)]) == null))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 6);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.lastIndex = i;

    if (this.streamList != null) {
      closeUsedStreams(i);
    }

    return localAccessor.getCharacterStream(this.currentRank);
  }

  public InputStream getUnicodeStream(String paramString)
    throws SQLException
  {
    if (!this.atLeastOneNamedParameter)
    {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 90, "Ordinal binding and Named binding cannot be combined!");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject = paramString.toUpperCase().intern();

    int i;
    for (i = 0; i < this.parameterCount; i++)
    {
      if (localObject == this.namedParameters[i])
        break;
    }
    i++;

    Accessor localAccessor = null;
    if ((i <= 0) || (i > this.numberOfBindPositions) || (this.outBindAccessors == null) || ((localAccessor = this.outBindAccessors[(i - 1)]) == null))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 6);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.lastIndex = i;

    if (this.streamList != null) {
      closeUsedStreams(i);
    }

    return localAccessor.getUnicodeStream(this.currentRank);
  }

  public InputStream getBinaryStream(String paramString)
    throws SQLException
  {
    if (!this.atLeastOneNamedParameter)
    {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 90, "Ordinal binding and Named binding cannot be combined!");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject = paramString.toUpperCase().intern();

    int i;
    for (i = 0; i < this.parameterCount; i++)
    {
      if (localObject == this.namedParameters[i])
        break;
    }
    i++;

    Accessor localAccessor = null;
    if ((i <= 0) || (i > this.numberOfBindPositions) || (this.outBindAccessors == null) || ((localAccessor = this.outBindAccessors[(i - 1)]) == null))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 6);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.lastIndex = i;

    if (this.streamList != null) {
      closeUsedStreams(i);
    }

    return localAccessor.getBinaryStream(this.currentRank);
  }

  public RowId getRowId(int paramInt)
    throws SQLException
  {
    if (this.closed)
    {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    if (this.atLeastOneNamedParameter)
    {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 90, "Ordinal binding and Named binding cannot be combined!");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject = null;
    if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || (this.outBindAccessors == null) || ((localObject = this.outBindAccessors[(paramInt - 1)]) == null))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.lastIndex = paramInt;

    if (this.streamList != null) {
      closeUsedStreams(paramInt);
    }

    return ((Accessor)localObject).getROWID(this.currentRank);
  }

  public RowId getRowId(String paramString)
    throws SQLException
  {
    if (!this.atLeastOneNamedParameter)
    {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 90, "Ordinal binding and Named binding cannot be combined!");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject = paramString.toUpperCase().intern();

    int i;
    for (i = 0; i < this.parameterCount; i++)
    {
      if (localObject == this.namedParameters[i])
        break;
    }
    i++;

    Accessor localAccessor = null;
    if ((i <= 0) || (i > this.numberOfBindPositions) || (this.outBindAccessors == null) || ((localAccessor = this.outBindAccessors[(i - 1)]) == null))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 6);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.lastIndex = i;

    if (this.streamList != null) {
      closeUsedStreams(i);
    }

    return localAccessor.getROWID(this.currentRank);
  }

  public NClob getNClob(int paramInt)
    throws SQLException
  {
    if (this.closed)
    {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    if (this.atLeastOneNamedParameter)
    {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 90, "Ordinal binding and Named binding cannot be combined!");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject = null;
    if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || (this.outBindAccessors == null) || ((localObject = this.outBindAccessors[(paramInt - 1)]) == null))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.lastIndex = paramInt;

    if (this.streamList != null) {
      closeUsedStreams(paramInt);
    }

    return ((Accessor)localObject).getNClob(this.currentRank);
  }

  public NClob getNClob(String paramString)
    throws SQLException
  {
    if (!this.atLeastOneNamedParameter)
    {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 90, "Ordinal binding and Named binding cannot be combined!");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject = paramString.toUpperCase().intern();

    int i;
    for (i = 0; i < this.parameterCount; i++)
    {
      if (localObject == this.namedParameters[i])
        break;
    }
    i++;

    Accessor localAccessor = null;
    if ((i <= 0) || (i > this.numberOfBindPositions) || (this.outBindAccessors == null) || ((localAccessor = this.outBindAccessors[(i - 1)]) == null))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 6);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.lastIndex = i;

    if (this.streamList != null) {
      closeUsedStreams(i);
    }

    return localAccessor.getNClob(this.currentRank);
  }

  public SQLXML getSQLXML(int paramInt)
    throws SQLException
  {
    if (this.closed)
    {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    if (this.atLeastOneNamedParameter)
    {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 90, "Ordinal binding and Named binding cannot be combined!");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject = null;
    if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || (this.outBindAccessors == null) || ((localObject = this.outBindAccessors[(paramInt - 1)]) == null))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.lastIndex = paramInt;

    if (this.streamList != null) {
      closeUsedStreams(paramInt);
    }
    return ((Accessor)localObject).getSQLXML(this.currentRank);
  }

  public SQLXML getSQLXML(String paramString)
    throws SQLException
  {
    if (!this.atLeastOneNamedParameter)
    {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 90, "Ordinal binding and Named binding cannot be combined!");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject = paramString.toUpperCase().intern();

    int i;
    for (i = 0; i < this.parameterCount; i++)
    {
      if (localObject == this.namedParameters[i])
        break;
    }
    i++;

    Accessor localAccessor = null;
    if ((i <= 0) || (i > this.numberOfBindPositions) || (this.outBindAccessors == null) || ((localAccessor = this.outBindAccessors[(i - 1)]) == null))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 6);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.lastIndex = i;

    if (this.streamList != null) {
      closeUsedStreams(i);
    }
    return localAccessor.getSQLXML(this.currentRank);
  }

  public String getNString(int paramInt)
    throws SQLException
  {
    if (this.closed)
    {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    if (this.atLeastOneNamedParameter)
    {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 90, "Ordinal binding and Named binding cannot be combined!");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject = null;
    if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || (this.outBindAccessors == null) || ((localObject = this.outBindAccessors[(paramInt - 1)]) == null))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.lastIndex = paramInt;

    if (this.streamList != null) {
      closeUsedStreams(paramInt);
    }

    return ((Accessor)localObject).getNString(this.currentRank);
  }

  public String getNString(String paramString)
    throws SQLException
  {
    if (!this.atLeastOneNamedParameter)
    {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 90, "Ordinal binding and Named binding cannot be combined!");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject = paramString.toUpperCase().intern();

    int i;
    for (i = 0; i < this.parameterCount; i++)
    {
      if (localObject == this.namedParameters[i])
        break;
    }
    i++;

    Accessor localAccessor = null;
    if ((i <= 0) || (i > this.numberOfBindPositions) || (this.outBindAccessors == null) || ((localAccessor = this.outBindAccessors[(i - 1)]) == null))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 6);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.lastIndex = i;

    if (this.streamList != null) {
      closeUsedStreams(i);
    }

    return localAccessor.getNString(this.currentRank);
  }

  public Reader getNCharacterStream(int paramInt)
    throws SQLException
  {
    if (this.closed)
    {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 9);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    if (this.atLeastOneNamedParameter)
    {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 90, "Ordinal binding and Named binding cannot be combined!");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject = null;
    if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || (this.outBindAccessors == null) || ((localObject = this.outBindAccessors[(paramInt - 1)]) == null))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.lastIndex = paramInt;

    if (this.streamList != null) {
      closeUsedStreams(paramInt);
    }

    return ((Accessor)localObject).getNCharacterStream(this.currentRank);
  }

  public Reader getNCharacterStream(String paramString)
    throws SQLException
  {
    SQLException sqlexception;
    if (!this.atLeastOneNamedParameter)
    {
      sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 90, "Ordinal binding and Named binding cannot be combined!");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject = paramString.toUpperCase().intern();

    int i;
    for (i = 0; i < this.parameterCount; i++)
    {
      if (localObject == this.namedParameters[i])
        break;
    }
    i++;

    Accessor localAccessor = null;
    if ((i <= 0) || (i > this.numberOfBindPositions) || (this.outBindAccessors == null) || ((localAccessor = this.outBindAccessors[(i - 1)]) == null))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 6);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.lastIndex = i;

    if (this.streamList != null) {
      closeUsedStreams(i);
    }

    return localAccessor.getNCharacterStream(this.currentRank);
  }
}