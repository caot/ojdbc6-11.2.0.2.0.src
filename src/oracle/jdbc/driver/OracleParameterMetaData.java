package oracle.jdbc.driver;

import java.sql.SQLException;
import oracle.jdbc.internal.OracleConnection;

class OracleParameterMetaData
  implements oracle.jdbc.OracleParameterMetaData
{
  int parameterCount = 0;

  int parameterNoNulls = 0;

  int parameterNullable = 1;

  int parameterNullableUnknown = 2;

  int parameterModeUnknown = 0;

  int parameterModeIn = 1;

  int parameterModeInOut = 2;

  int parameterModeOut = 4;

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  OracleParameterMetaData(int paramInt)
    throws SQLException
  {
    this.parameterCount = paramInt;
  }

  public int getParameterCount()
    throws SQLException
  {
    return this.parameterCount;
  }

  public int isNullable(int paramInt)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createUnsupportedFeatureSqlException();
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public boolean isSigned(int paramInt)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createUnsupportedFeatureSqlException();
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public int getPrecision(int paramInt)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createUnsupportedFeatureSqlException();
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public int getScale(int paramInt)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createUnsupportedFeatureSqlException();
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public int getParameterType(int paramInt)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createUnsupportedFeatureSqlException();
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public String getParameterTypeName(int paramInt)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createUnsupportedFeatureSqlException();
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public String getParameterClassName(int paramInt)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createUnsupportedFeatureSqlException();
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public int getParameterMode(int paramInt)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createUnsupportedFeatureSqlException();
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public boolean isWrapperFor(Class<?> paramClass)
    throws SQLException
  {
    if (paramClass.isInterface()) return paramClass.isInstance(this);

    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 177);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public <T> T unwrap(Class<T> paramClass)
    throws SQLException
  {
    if ((paramClass.isInterface()) && (paramClass.isInstance(this))) return this;

    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 177);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  protected OracleConnection getConnectionDuringExceptionHandling()
  {
    return null;
  }
}