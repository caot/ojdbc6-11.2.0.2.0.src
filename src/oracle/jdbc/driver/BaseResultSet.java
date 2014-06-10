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
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import oracle.sql.ARRAY;
import oracle.sql.BFILE;
import oracle.sql.BINARY_DOUBLE;
import oracle.sql.BINARY_FLOAT;
import oracle.sql.BLOB;
import oracle.sql.CHAR;
import oracle.sql.CLOB;
import oracle.sql.CustomDatum;
import oracle.sql.DATE;
import oracle.sql.Datum;
import oracle.sql.INTERVALDS;
import oracle.sql.INTERVALYM;
import oracle.sql.NUMBER;
import oracle.sql.OPAQUE;
import oracle.sql.ORAData;
import oracle.sql.RAW;
import oracle.sql.REF;
import oracle.sql.ROWID;
import oracle.sql.STRUCT;
import oracle.sql.TIMESTAMP;
import oracle.sql.TIMESTAMPLTZ;
import oracle.sql.TIMESTAMPTZ;

abstract class BaseResultSet extends OracleResultSet
{
  SQLWarning sqlWarning = null;
  boolean autoRefetch = true;
  boolean closed = false;

  boolean close_statement_on_close = false;

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  public void closeStatementOnClose()
  {
    this.close_statement_on_close = true;
  }

  public void beforeFirst()
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 75, "beforeFirst");
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void afterLast()
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 75, "afterLast");
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public boolean first()
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 75, "first");
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public boolean last()
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 75, "last");
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public boolean absolute(int paramInt)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 75, "absolute");
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public boolean relative(int paramInt)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 75, "relative");
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public boolean previous()
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 75, "previous");
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setFetchDirection(int paramInt)
    throws SQLException
  {
    if (paramInt == 1000)
      return;
    if ((paramInt == 1001) || (paramInt == 1002))
    {
      localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 75, "setFetchDirection(FETCH_REVERSE, FETCH_UNKNOWN)");
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68, "setFetchDirection");
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public int getFetchDirection()
    throws SQLException
  {
    return 1000;
  }

  public int getType()
    throws SQLException
  {
    return 1003;
  }

  public int getConcurrency()
    throws SQLException
  {
    return 1007;
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

  public void updateArray(int paramInt, Array paramArray)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 76, "updateArray");
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void updateBigDecimal(int paramInt, BigDecimal paramBigDecimal)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 76, "updateBigDecimal");
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void updateBlob(int paramInt, Blob paramBlob)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 76, "updateBlob");
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void updateBoolean(int paramInt, boolean paramBoolean)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 76, "updateBoolean");
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void updateByte(int paramInt, byte paramByte)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 76, "updateByte");
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void updateBytes(int paramInt, byte[] paramArrayOfByte)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 76, "updateBytes");
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void updateClob(int paramInt, Clob paramClob)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 76, "updateClob");
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void updateDate(int paramInt, Date paramDate)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 76, "updateDate");
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void updateDate(int paramInt, Date paramDate, Calendar paramCalendar)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 76, "updateDate");
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void updateDouble(int paramInt, double paramDouble)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 76, "updateDouble");
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void updateFloat(int paramInt, float paramFloat)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 76, "updateFloat");
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void updateInt(int paramInt1, int paramInt2)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 76, "updateInt");
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void updateLong(int paramInt, long paramLong)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 76, "updateLong");
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void updateNClob(int paramInt, NClob paramNClob)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 76, "updateNClob");
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void updateNString(int paramInt, String paramString)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 76, "updateNString");
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void updateObject(int paramInt, Object paramObject)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 76, "updateObject");
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void updateObject(int paramInt1, Object paramObject, int paramInt2)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 76, "updateObject");
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void updateRef(int paramInt, Ref paramRef)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 76, "updateRef");
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void updateRowId(int paramInt, RowId paramRowId)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 76, "updateRowId");
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void updateShort(int paramInt, short paramShort)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 76, "updateShort");
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void updateSQLXML(int paramInt, SQLXML paramSQLXML)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 76, "updateSQLXML");
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void updateString(int paramInt, String paramString)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 76, "updateString");
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void updateTime(int paramInt, Time paramTime)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 76, "updateTime");
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void updateTime(int paramInt, Time paramTime, Calendar paramCalendar)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 76, "updateTime");
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void updateTimestamp(int paramInt, Timestamp paramTimestamp)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 76, "updateTimestamp");
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void updateTimestamp(int paramInt, Timestamp paramTimestamp, Calendar paramCalendar)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 76, "updateTimestamp");
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void updateURL(int paramInt, URL paramURL)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 76, "updateURL");
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void updateARRAY(int paramInt, ARRAY paramARRAY)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 76, "updateARRAY");
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void updateBFILE(int paramInt, BFILE paramBFILE)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 76, "updateBFILE");
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void updateBfile(int paramInt, BFILE paramBFILE)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 76, "updateBfile");
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void updateBinaryFloat(int paramInt, float paramFloat)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 76, "updateBinaryFloat");
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void updateBinaryFloat(int paramInt, BINARY_FLOAT paramBINARY_FLOAT)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 76, "updateBinaryFloat");
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void updateBinaryDouble(int paramInt, double paramDouble)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 76, "updateBinaryDouble");
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void updateBinaryDouble(int paramInt, BINARY_DOUBLE paramBINARY_DOUBLE)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 76, "updateBinaryDouble");
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void updateBLOB(int paramInt, BLOB paramBLOB)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 76, "updateBLOB");
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void updateCHAR(int paramInt, CHAR paramCHAR)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 76, "updateCHAR");
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void updateCLOB(int paramInt, CLOB paramCLOB)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 76, "updateCLOB");
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void updateCursor(int paramInt, ResultSet paramResultSet)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 76, "updateCursor");
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void updateCustomDatum(int paramInt, CustomDatum paramCustomDatum)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 76, "updateCustomDatum");
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void updateDATE(int paramInt, DATE paramDATE)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 76, "updateDATE");
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void updateFixedCHAR(int paramInt, String paramString)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 76, "updateFixedCHAR");
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void updateINTERVALDS(int paramInt, INTERVALDS paramINTERVALDS)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 76, "updateINTERVALDS");
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void updateINTERVALYM(int paramInt, INTERVALYM paramINTERVALYM)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 76, "updateINTERVALYM");
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void updateNUMBER(int paramInt, NUMBER paramNUMBER)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 76, "updateNUMBER");
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void updateOPAQUE(int paramInt, OPAQUE paramOPAQUE)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 76, "updateOPAQUE");
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void updateOracleObject(int paramInt, Datum paramDatum)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 76, "updateOracleObject");
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void updateORAData(int paramInt, ORAData paramORAData)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 76, "updateORAData");
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void updateRAW(int paramInt, RAW paramRAW)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 76, "updateRAW");
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void updateREF(int paramInt, REF paramREF)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 76, "updateREF");
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void updateRefType(int paramInt, REF paramREF)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 76, "updateRefType");
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void updateROWID(int paramInt, ROWID paramROWID)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 76, "updateROWID");
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void updateSTRUCT(int paramInt, STRUCT paramSTRUCT)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 76, "updateSTRUCT");
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void updateTIMESTAMPLTZ(int paramInt, TIMESTAMPLTZ paramTIMESTAMPLTZ)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 76, "updateTIMESTAMPLTZ");
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void updateTIMESTAMPTZ(int paramInt, TIMESTAMPTZ paramTIMESTAMPTZ)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 76, "updateTIMESTAMPTZ");
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void updateTIMESTAMP(int paramInt, TIMESTAMP paramTIMESTAMP)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 76, "updateTIMESTAMP");
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void updateBlob(int paramInt, InputStream paramInputStream)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 76, "updateBlob");
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void updateBlob(int paramInt, InputStream paramInputStream, long paramLong)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 76, "updateBlob");
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void updateClob(int paramInt, Reader paramReader)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 76, "updateClob");
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void updateClob(int paramInt, Reader paramReader, long paramLong)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 76, "updateClob");
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void updateNClob(int paramInt, Reader paramReader)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 76, "updateNClob");
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void updateNClob(int paramInt, Reader paramReader, long paramLong)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 76, "updateNClob");
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void updateAsciiStream(int paramInt, InputStream paramInputStream)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 76, "updateAsciiStream");
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void updateAsciiStream(int paramInt1, InputStream paramInputStream, int paramInt2)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 76, "updateAsciiStream");
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void updateAsciiStream(int paramInt, InputStream paramInputStream, long paramLong)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 76, "updateAsciiStream");
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void updateBinaryStream(int paramInt, InputStream paramInputStream)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 76, "updateBinaryStream");
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void updateBinaryStream(int paramInt1, InputStream paramInputStream, int paramInt2)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 76, "updateBinaryStream");
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void updateBinaryStream(int paramInt, InputStream paramInputStream, long paramLong)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 76, "updateBinaryStream");
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void updateCharacterStream(int paramInt, Reader paramReader)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 76, "updateCharacterStream");
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void updateCharacterStream(int paramInt1, Reader paramReader, int paramInt2)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 76, "updateCharacterStream");
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void updateCharacterStream(int paramInt, Reader paramReader, long paramLong)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 76, "updateCharacterStream");
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void updateNCharacterStream(int paramInt, Reader paramReader)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 76, "updateNCharacterStream");
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void updateNCharacterStream(int paramInt, Reader paramReader, long paramLong)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 76, "updateNCharacterStream");
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void updateUnicodeStream(int paramInt1, InputStream paramInputStream, int paramInt2)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 76, "updateUnicodeStream");
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void updateNull(int paramInt)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 76, "updateNull");
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public boolean rowUpdated()
    throws SQLException
  {
    return false;
  }

  public boolean rowInserted()
    throws SQLException
  {
    return false;
  }

  public boolean rowDeleted()
    throws SQLException
  {
    return false;
  }

  public void insertRow()
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 76, "insertRow");
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void updateRow()
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 76, "updateRow");
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void deleteRow()
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 76, "deleteRow");
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void refreshRow()
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 23, null);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void cancelRowUpdates()
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 76, "cancelRowUpdates");
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void moveToInsertRow()
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 76, "moveToInsertRow");
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void moveToCurrentRow()
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 76, "moveToCurrentRow");
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public void setAutoRefetch(boolean paramBoolean)
    throws SQLException
  {
    this.autoRefetch = paramBoolean;
  }

  public boolean getAutoRefetch()
    throws SQLException
  {
    return this.autoRefetch;
  }

  public void close()
    throws SQLException
  {
    this.closed = true;
  }

  OracleStatement getOracleStatement()
    throws SQLException
  {
    return null;
  }

  public int getHoldability()
    throws SQLException
  {
    return 1;
  }

  public boolean isClosed()
    throws SQLException
  {
    return this.closed;
  }
}