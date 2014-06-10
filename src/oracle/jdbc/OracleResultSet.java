package oracle.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
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

public abstract interface OracleResultSet extends ResultSet
{
  public static final int HOLD_CURSORS_OVER_COMMIT = 1;
  public static final int CLOSE_CURSORS_AT_COMMIT = 2;

  public abstract ARRAY getARRAY(int paramInt)
    throws SQLException;

  public abstract ARRAY getARRAY(String paramString)
    throws SQLException;

  public abstract BFILE getBfile(int paramInt)
    throws SQLException;

  public abstract BFILE getBFILE(int paramInt)
    throws SQLException;

  public abstract BFILE getBfile(String paramString)
    throws SQLException;

  public abstract BFILE getBFILE(String paramString)
    throws SQLException;

  public abstract BLOB getBLOB(int paramInt)
    throws SQLException;

  public abstract BLOB getBLOB(String paramString)
    throws SQLException;

  public abstract CHAR getCHAR(int paramInt)
    throws SQLException;

  public abstract CHAR getCHAR(String paramString)
    throws SQLException;

  public abstract CLOB getCLOB(int paramInt)
    throws SQLException;

  public abstract CLOB getCLOB(String paramString)
    throws SQLException;

  public abstract OPAQUE getOPAQUE(int paramInt)
    throws SQLException;

  public abstract OPAQUE getOPAQUE(String paramString)
    throws SQLException;

  public abstract INTERVALYM getINTERVALYM(int paramInt)
    throws SQLException;

  public abstract INTERVALYM getINTERVALYM(String paramString)
    throws SQLException;

  public abstract INTERVALDS getINTERVALDS(int paramInt)
    throws SQLException;

  public abstract INTERVALDS getINTERVALDS(String paramString)
    throws SQLException;

  public abstract TIMESTAMP getTIMESTAMP(int paramInt)
    throws SQLException;

  public abstract TIMESTAMP getTIMESTAMP(String paramString)
    throws SQLException;

  public abstract TIMESTAMPTZ getTIMESTAMPTZ(int paramInt)
    throws SQLException;

  public abstract TIMESTAMPTZ getTIMESTAMPTZ(String paramString)
    throws SQLException;

  public abstract TIMESTAMPLTZ getTIMESTAMPLTZ(int paramInt)
    throws SQLException;

  public abstract TIMESTAMPLTZ getTIMESTAMPLTZ(String paramString)
    throws SQLException;

  public abstract ResultSet getCursor(int paramInt)
    throws SQLException;

  public abstract ResultSet getCursor(String paramString)
    throws SQLException;

  /** @deprecated */
  public abstract CustomDatum getCustomDatum(int paramInt, CustomDatumFactory paramCustomDatumFactory)
    throws SQLException;

  public abstract ORAData getORAData(int paramInt, ORADataFactory paramORADataFactory)
    throws SQLException;

  /** @deprecated */
  public abstract CustomDatum getCustomDatum(String paramString, CustomDatumFactory paramCustomDatumFactory)
    throws SQLException;

  public abstract ORAData getORAData(String paramString, ORADataFactory paramORADataFactory)
    throws SQLException;

  public abstract DATE getDATE(int paramInt)
    throws SQLException;

  public abstract DATE getDATE(String paramString)
    throws SQLException;

  public abstract NUMBER getNUMBER(int paramInt)
    throws SQLException;

  public abstract NUMBER getNUMBER(String paramString)
    throws SQLException;

  public abstract Datum getOracleObject(int paramInt)
    throws SQLException;

  public abstract Datum getOracleObject(String paramString)
    throws SQLException;

  public abstract RAW getRAW(int paramInt)
    throws SQLException;

  public abstract RAW getRAW(String paramString)
    throws SQLException;

  public abstract REF getREF(int paramInt)
    throws SQLException;

  public abstract REF getREF(String paramString)
    throws SQLException;

  public abstract ROWID getROWID(int paramInt)
    throws SQLException;

  public abstract ROWID getROWID(String paramString)
    throws SQLException;

  public abstract STRUCT getSTRUCT(int paramInt)
    throws SQLException;

  public abstract STRUCT getSTRUCT(String paramString)
    throws SQLException;

  public abstract void updateARRAY(int paramInt, ARRAY paramARRAY)
    throws SQLException;

  public abstract void updateARRAY(String paramString, ARRAY paramARRAY)
    throws SQLException;

  public abstract void updateBfile(int paramInt, BFILE paramBFILE)
    throws SQLException;

  public abstract void updateBFILE(int paramInt, BFILE paramBFILE)
    throws SQLException;

  public abstract void updateBfile(String paramString, BFILE paramBFILE)
    throws SQLException;

  public abstract void updateBFILE(String paramString, BFILE paramBFILE)
    throws SQLException;

  public abstract void updateBLOB(int paramInt, BLOB paramBLOB)
    throws SQLException;

  public abstract void updateBLOB(String paramString, BLOB paramBLOB)
    throws SQLException;

  public abstract void updateCHAR(int paramInt, CHAR paramCHAR)
    throws SQLException;

  public abstract void updateCHAR(String paramString, CHAR paramCHAR)
    throws SQLException;

  public abstract void updateCLOB(int paramInt, CLOB paramCLOB)
    throws SQLException;

  public abstract void updateCLOB(String paramString, CLOB paramCLOB)
    throws SQLException;

  /** @deprecated */
  public abstract void updateCustomDatum(int paramInt, CustomDatum paramCustomDatum)
    throws SQLException;

  public abstract void updateORAData(int paramInt, ORAData paramORAData)
    throws SQLException;

  /** @deprecated */
  public abstract void updateCustomDatum(String paramString, CustomDatum paramCustomDatum)
    throws SQLException;

  public abstract void updateORAData(String paramString, ORAData paramORAData)
    throws SQLException;

  public abstract void updateDATE(int paramInt, DATE paramDATE)
    throws SQLException;

  public abstract void updateDATE(String paramString, DATE paramDATE)
    throws SQLException;

  public abstract void updateINTERVALYM(int paramInt, INTERVALYM paramINTERVALYM)
    throws SQLException;

  public abstract void updateINTERVALYM(String paramString, INTERVALYM paramINTERVALYM)
    throws SQLException;

  public abstract void updateINTERVALDS(int paramInt, INTERVALDS paramINTERVALDS)
    throws SQLException;

  public abstract void updateINTERVALDS(String paramString, INTERVALDS paramINTERVALDS)
    throws SQLException;

  public abstract void updateTIMESTAMP(int paramInt, TIMESTAMP paramTIMESTAMP)
    throws SQLException;

  public abstract void updateTIMESTAMP(String paramString, TIMESTAMP paramTIMESTAMP)
    throws SQLException;

  public abstract void updateTIMESTAMPTZ(int paramInt, TIMESTAMPTZ paramTIMESTAMPTZ)
    throws SQLException;

  public abstract void updateTIMESTAMPTZ(String paramString, TIMESTAMPTZ paramTIMESTAMPTZ)
    throws SQLException;

  public abstract void updateTIMESTAMPLTZ(int paramInt, TIMESTAMPLTZ paramTIMESTAMPLTZ)
    throws SQLException;

  public abstract void updateTIMESTAMPLTZ(String paramString, TIMESTAMPLTZ paramTIMESTAMPLTZ)
    throws SQLException;

  public abstract void updateNUMBER(int paramInt, NUMBER paramNUMBER)
    throws SQLException;

  public abstract void updateNUMBER(String paramString, NUMBER paramNUMBER)
    throws SQLException;

  public abstract void updateOracleObject(int paramInt, Datum paramDatum)
    throws SQLException;

  public abstract void updateOracleObject(String paramString, Datum paramDatum)
    throws SQLException;

  public abstract void updateRAW(int paramInt, RAW paramRAW)
    throws SQLException;

  public abstract void updateRAW(String paramString, RAW paramRAW)
    throws SQLException;

  public abstract void updateREF(int paramInt, REF paramREF)
    throws SQLException;

  public abstract void updateREF(String paramString, REF paramREF)
    throws SQLException;

  public abstract void updateROWID(int paramInt, ROWID paramROWID)
    throws SQLException;

  public abstract void updateROWID(String paramString, ROWID paramROWID)
    throws SQLException;

  public abstract void updateSTRUCT(int paramInt, STRUCT paramSTRUCT)
    throws SQLException;

  public abstract void updateSTRUCT(String paramString, STRUCT paramSTRUCT)
    throws SQLException;

  public abstract AuthorizationIndicator getAuthorizationIndicator(int paramInt)
    throws SQLException;

  public abstract AuthorizationIndicator getAuthorizationIndicator(String paramString)
    throws SQLException;

  public static enum AuthorizationIndicator
  {
    NONE, 

    UNAUTHORIZED, 

    UNKNOWN;
  }
}