package oracle.jdbc.internal;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.sql.SQLException;

public abstract interface OracleCallableStatement extends oracle.jdbc.OracleCallableStatement, OraclePreparedStatement
{
  public abstract byte[] privateGetBytes(int paramInt)
    throws SQLException;

  /** @deprecated */
  public abstract BigDecimal getBigDecimal(String paramString, int paramInt)
    throws SQLException;

  /** @deprecated */
  public abstract InputStream getAsciiStream(String paramString)
    throws SQLException;

  /** @deprecated */
  public abstract Reader getCharacterStream(String paramString)
    throws SQLException;
}