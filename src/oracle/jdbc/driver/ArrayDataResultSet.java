package oracle.jdbc.driver;

import java.io.ByteArrayInputStream;
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

class ArrayDataResultSet extends BaseResultSet
{
  Datum[] data;
  Map map;
  private int currentIndex;
  private int lastIndex;
  PhysicalConnection connection;
  private Boolean wasNull;
  private int fetchSize;
  ARRAY array;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  public ArrayDataResultSet(OracleConnection paramOracleConnection, Datum[] paramArrayOfDatum, Map paramMap)
    throws SQLException
  {
    this.connection = ((PhysicalConnection)paramOracleConnection);
    this.data = paramArrayOfDatum;
    this.map = paramMap;
    this.currentIndex = 0;
    this.lastIndex = (this.data == null ? 0 : this.data.length);
    this.fetchSize = OracleConnection.DEFAULT_ROW_PREFETCH;
  }

  public ArrayDataResultSet(OracleConnection paramOracleConnection, Datum[] paramArrayOfDatum, long paramLong, int paramInt, Map paramMap)
    throws SQLException
  {
    this.connection = ((PhysicalConnection)paramOracleConnection);
    this.data = paramArrayOfDatum;
    this.map = paramMap;
    this.currentIndex = ((int)paramLong - 1);

    int i = this.data == null ? 0 : this.data.length;

    this.lastIndex = (this.currentIndex + Math.min(i - this.currentIndex, paramInt));

    this.fetchSize = OracleConnection.DEFAULT_ROW_PREFETCH;
  }

  public ArrayDataResultSet(OracleConnection paramOracleConnection, ARRAY paramARRAY, long paramLong, int paramInt, Map paramMap)
    throws SQLException
  {
    this.connection = ((PhysicalConnection)paramOracleConnection);
    this.array = paramARRAY;
    this.map = paramMap;
    this.currentIndex = ((int)paramLong - 1);

    int i = this.array == null ? 0 : paramARRAY.length();

    this.lastIndex = (this.currentIndex + (paramInt == -1 ? i - this.currentIndex : Math.min(i - this.currentIndex, paramInt)));

    this.fetchSize = OracleConnection.DEFAULT_ROW_PREFETCH;
  }

  public boolean next()
    throws SQLException
  {
    if (this.closed)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 10, "next");
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.currentIndex += 1;

    return this.currentIndex <= this.lastIndex;
  }

  public void close()
    throws SQLException
  {
    synchronized (this.connection)
    {
      super.close();
    }
  }

  public boolean wasNull()
    throws SQLException
  {
    synchronized (this.connection)
    {
      if (this.wasNull == null)
      {
        SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 24, null);
        localSQLException.fillInStackTrace();
        throw localSQLException;
      }

      return this.wasNull.booleanValue();
    }
  }

  public String getString(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      Datum localDatum = getOracleObject(paramInt);

      if (localDatum != null) {
        return localDatum.stringValue();
      }
      return null;
    }
  }

  public ResultSet getCursor(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4, "getCursor");
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  public Datum getOracleObject(int paramInt)
    throws SQLException
  {
    if (this.currentIndex <= 0)
    {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 14, null);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    if (paramInt == 1)
    {
      this.wasNull = Boolean.FALSE;

      return new NUMBER(this.currentIndex);
    }
    if (paramInt == 2)
    {
      if (this.data != null)
      {
        this.wasNull = (this.data[(this.currentIndex - 1)] == null ? Boolean.TRUE : Boolean.FALSE);

        return this.data[(this.currentIndex - 1)];
      }
      if (this.array != null)
      {
        Datum[] localObject = this.array.getOracleArray(this.currentIndex, 1);

        if ((localObject != null) && (localObject.length >= 1))
        {
          this.wasNull = (localObject[0] == null ? Boolean.TRUE : Boolean.FALSE);

          return localObject[0];
        }

      }

      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 1, "Out of sync");
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 3, null);
    sqlexception.fillInStackTrace();
    throw sqlexception;
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

        SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4, "getROWID");
        localSQLException.fillInStackTrace();
        throw localSQLException;
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

        SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4, "getNUMBER");
        localSQLException.fillInStackTrace();
        throw localSQLException;
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
        if ((localDatum instanceof DATE)) {
          return (DATE)localDatum;
        }

        SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4, "getDATE");
        localSQLException.fillInStackTrace();
        throw localSQLException;
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

        SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4, "getARRAY");
        localSQLException.fillInStackTrace();
        throw localSQLException;
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

        SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4, "getSTRUCT");
        localSQLException.fillInStackTrace();
        throw localSQLException;
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

        SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4, "getOPAQUE");
        localSQLException.fillInStackTrace();
        throw localSQLException;
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

        SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4, "getREF");
        localSQLException.fillInStackTrace();
        throw localSQLException;
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

        SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4, "getCHAR");
        localSQLException.fillInStackTrace();
        throw localSQLException;
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

        SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4, "getRAW");
        localSQLException.fillInStackTrace();
        throw localSQLException;
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

        SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4, "getBLOB");
        localSQLException.fillInStackTrace();
        throw localSQLException;
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

        SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4, "getCLOB");
        localSQLException.fillInStackTrace();
        throw localSQLException;
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

        SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4, "getBFILE");
        localSQLException.fillInStackTrace();
        throw localSQLException;
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

        SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4);
        localSQLException.fillInStackTrace();
        throw localSQLException;
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

        SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4);
        localSQLException.fillInStackTrace();
        throw localSQLException;
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

  public TIMESTAMP getTIMESTAMP(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      Datum localDatum = getOracleObject(paramInt);

      if (localDatum != null)
      {
        if ((localDatum instanceof TIMESTAMP)) {
          return (TIMESTAMP)localDatum;
        }

        SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4, "getTIMESTAMP");
        localSQLException.fillInStackTrace();
        throw localSQLException;
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
        if ((localDatum instanceof TIMESTAMPTZ)) {
          return (TIMESTAMPTZ)localDatum;
        }

        SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4, "getTIMESTAMPTZ");
        localSQLException.fillInStackTrace();
        throw localSQLException;
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

        SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4, "getTIMESTAMPLTZ");
        localSQLException.fillInStackTrace();
        throw localSQLException;
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

      if (localDatum != null) {
        return localDatum.booleanValue();
      }
      return false;
    }
  }

  public OracleResultSet.AuthorizationIndicator getAuthorizationIndicator(int paramInt)
    throws SQLException
  {
    return null;
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
        SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 26, "getShort");
        localSQLException.fillInStackTrace();
        throw localSQLException;
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
      Datum localDatum = getOracleObject(paramInt);

      if (localDatum != null)
      {
        if ((localDatum instanceof RAW)) {
          return ((RAW)localDatum).shareBytes();
        }

        SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4, "getBytes");
        localSQLException.fillInStackTrace();
        throw localSQLException;
      }

      return null;
    }
  }

  public Date getDate(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      Datum localDatum = getOracleObject(paramInt);

      if (localDatum != null)
      {
        return localDatum.dateValue();
      }

      return null;
    }
  }

  public Time getTime(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      Datum localDatum = getOracleObject(paramInt);

      if (localDatum != null)
      {
        return localDatum.timeValue();
      }

      return null;
    }
  }

  public Timestamp getTimestamp(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      Datum localDatum = getOracleObject(paramInt);

      if (localDatum != null)
      {
        return localDatum.timestampValue();
      }

      return null;
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
        localDatum.asciiStreamValue();
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

        SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4, "getUnicodeStream");
        localSQLException.fillInStackTrace();
        throw localSQLException;
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
    synchronized (this.connection)
    {
      return getObject(paramInt, this.map);
    }
  }

  /** @deprecated */
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

  public ResultSetMetaData getMetaData()
    throws SQLException
  {
    synchronized (this.connection)
    {
      if (this.closed)
      {
        SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 10, "getMetaData");
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }

      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 23, "getMetaData");
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  public int findColumn(String paramString)
    throws SQLException
  {
    synchronized (this.connection)
    {
      if (paramString.equalsIgnoreCase("index"))
        return 1;
      if (paramString.equalsIgnoreCase("value")) {
        return 2;
      }

      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 6, "get_column_index");
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  public Statement getStatement()
    throws SQLException
  {
    return null;
  }

  public Object getObject(int paramInt, Map paramMap)
    throws SQLException
  {
    synchronized (this.connection)
    {
      Datum localDatum = getOracleObject(paramInt);

      if (localDatum != null)
      {
        if ((localDatum instanceof STRUCT)) {
          return ((STRUCT)localDatum).toJdbc(paramMap);
        }
        return localDatum.toJdbc();
      }

      return null;
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
    synchronized (this.connection)
    {
      Datum localDatum = getOracleObject(paramInt);

      if (localDatum != null)
      {
        return localDatum.bigDecimalValue();
      }

      return null;
    }
  }

  public Date getDate(int paramInt, Calendar paramCalendar)
    throws SQLException
  {
    synchronized (this.connection)
    {
      Datum localDatum = getOracleObject(paramInt);

      if (localDatum != null)
      {
        DATE localDATE = null;

        if ((localDatum instanceof DATE))
          localDATE = (DATE)localDatum;
        else {
          localDATE = new DATE(localDatum.stringValue());
        }
        if (localDATE != null) {
          return localDATE.dateValue(paramCalendar);
        }
      }
      return null;
    }
  }

  public Time getTime(int paramInt, Calendar paramCalendar)
    throws SQLException
  {
    synchronized (this.connection)
    {
      Datum localDatum = getOracleObject(paramInt);

      if (localDatum != null)
      {
        DATE localDATE = null;

        if ((localDatum instanceof DATE))
          localDATE = (DATE)localDatum;
        else {
          localDATE = new DATE(localDatum.stringValue());
        }
        if (localDATE != null) {
          return localDATE.timeValue(paramCalendar);
        }
      }
      return null;
    }
  }

  public Timestamp getTimestamp(int paramInt, Calendar paramCalendar)
    throws SQLException
  {
    synchronized (this.connection)
    {
      Datum localDatum = getOracleObject(paramInt);

      if (localDatum != null)
      {
        DATE localDATE = null;

        if ((localDatum instanceof DATE))
          localDATE = (DATE)localDatum;
        else {
          localDATE = new DATE(localDatum.stringValue());
        }
        if (localDATE != null) {
          return localDATE.timestampValue(paramCalendar);
        }
      }
      return null;
    }
  }

  public URL getURL(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      SQLException localSQLException = DatabaseError.createUnsupportedFeatureSqlException();
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
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

  public NClob getNClob(int paramInt)
    throws SQLException
  {
    Datum localDatum = getOracleObject(paramInt);

    if (localDatum != null)
    {
      if ((localDatum instanceof NCLOB)) {
        return (NCLOB)localDatum;
      }

      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    return null;
  }

  public String getNString(int paramInt)
    throws SQLException
  {
    Datum localDatum = getOracleObject(paramInt);

    if (localDatum != null)
    {
      return localDatum.stringValue();
    }

    return null;
  }

  public Reader getNCharacterStream(int paramInt)
    throws SQLException
  {
    Datum localDatum = getOracleObject(paramInt);

    if (localDatum != null)
    {
      return localDatum.characterStreamValue();
    }

    return null;
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
      if ((localDatum instanceof SQLXML)) {
        return (SQLXML)localDatum;
      }

      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    return null;
  }

  public boolean isBeforeFirst()
    throws SQLException
  {
    if (this.closed)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 10);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
    return this.currentIndex < 1;
  }

  public boolean isAfterLast()
    throws SQLException
  {
    if (this.closed)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 10);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
    return this.currentIndex > this.lastIndex;
  }

  public boolean isFirst()
    throws SQLException
  {
    if (this.closed)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 10);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
    return this.currentIndex == 1;
  }

  public boolean isLast()
    throws SQLException
  {
    if (this.closed)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 10);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
    return this.currentIndex == this.lastIndex;
  }

  public int getRow()
    throws SQLException
  {
    if (this.closed)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 10);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
    return this.currentIndex;
  }

  public void setFetchSize(int paramInt)
    throws SQLException
  {
    if (paramInt < 0)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
    if (paramInt == 0)
      this.fetchSize = OracleConnection.DEFAULT_ROW_PREFETCH;
    else
      this.fetchSize = paramInt;
  }

  public int getFetchSize()
    throws SQLException
  {
    return this.fetchSize;
  }

  protected oracle.jdbc.internal.OracleConnection getConnectionDuringExceptionHandling()
  {
    return this.connection;
  }
}