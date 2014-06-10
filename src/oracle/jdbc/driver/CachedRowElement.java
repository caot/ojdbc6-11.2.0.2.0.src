package oracle.jdbc.driver;

import oracle.jdbc.OracleResultSet.AuthorizationIndicator;
import oracle.sql.Datum;

class CachedRowElement
{
  private final int row;
  private final int col;
  private final OracleResultSet.AuthorizationIndicator securityIndicator;
  private final byte[] data;
  private Datum dataAsDatum;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  CachedRowElement(int paramInt1, int paramInt2, OracleResultSet.AuthorizationIndicator paramAuthorizationIndicator, byte[] paramArrayOfByte)
  {
    this.row = paramInt1;
    this.col = paramInt2;
    this.securityIndicator = paramAuthorizationIndicator;
    this.data = paramArrayOfByte;
    this.dataAsDatum = null;
  }

  OracleResultSet.AuthorizationIndicator getIndicator()
  {
    return this.securityIndicator;
  }

  void setData(Datum paramDatum)
  {
    this.dataAsDatum = paramDatum;
  }

  byte[] getData()
  {
    return this.data;
  }

  Datum getDataAsDatum()
  {
    return this.dataAsDatum;
  }
}