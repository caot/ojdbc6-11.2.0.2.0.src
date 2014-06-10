package oracle.sql;

import java.sql.SQLException;
import oracle.jdbc.driver.DatabaseError;
import oracle.jdbc.internal.OracleConnection;

class AnyDataFactory
  implements ORADataFactory
{
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  public ORAData create(Datum paramDatum, int paramInt)
    throws SQLException
  {
    if (paramDatum == null) return null;

    if ((paramDatum instanceof OPAQUE)) {
      return new ANYDATA((OPAQUE)paramDatum);
    }

    String str = "expected oracle.sql.OPAQUE got: " + paramDatum.getClass().getName();

    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 1, str);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  protected OracleConnection getConnectionDuringExceptionHandling()
  {
    return null;
  }
}