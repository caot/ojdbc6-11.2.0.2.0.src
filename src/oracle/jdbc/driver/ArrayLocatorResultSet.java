package oracle.jdbc.driver;

import java.sql.SQLException;
import java.util.Map;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;

class ArrayLocatorResultSet extends OracleResultSetImpl
{
  static int COUNT_UNLIMITED = -1;
  Map map;
  long beginIndex;
  int count;
  long currentIndex;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  public ArrayLocatorResultSet(OracleConnection paramOracleConnection, ArrayDescriptor paramArrayDescriptor, byte[] paramArrayOfByte, Map paramMap)
    throws SQLException
  {
    this(paramOracleConnection, paramArrayDescriptor, paramArrayOfByte, 0L, COUNT_UNLIMITED, paramMap);
  }

  public ArrayLocatorResultSet(OracleConnection paramOracleConnection, ArrayDescriptor paramArrayDescriptor, byte[] paramArrayOfByte, long paramLong, int paramInt, Map paramMap)
    throws SQLException
  {
    super((PhysicalConnection)paramOracleConnection, (OracleStatement)null);

    if ((paramArrayDescriptor == null) || (paramOracleConnection == null))
    {
      localObject = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 1, "Invalid arguments");
      ((SQLException)localObject).fillInStackTrace();
      throw ((Throwable)localObject);
    }

    this.close_statement_on_close = true;

    this.count = paramInt;
    this.currentIndex = 0L;
    this.beginIndex = paramLong;

    this.map = paramMap;

    Object localObject = null;

    ARRAY localARRAY = new ARRAY(paramArrayDescriptor, paramOracleConnection, (byte[])null);

    localARRAY.setLocator(paramArrayOfByte);

    if ((paramArrayDescriptor.getBaseType() == 2002) || (paramArrayDescriptor.getBaseType() == 2008))
    {
      localObject = (OraclePreparedStatement)((OraclePreparedStatementWrapper)paramOracleConnection.prepareStatement("SELECT ROWNUM, SYS_NC_ROWINFO$ FROM TABLE( CAST(:1 AS " + paramArrayDescriptor.getName() + ") )")).preparedStatement;
    }
    else
    {
      localObject = (OraclePreparedStatement)((OraclePreparedStatementWrapper)paramOracleConnection.prepareStatement("SELECT ROWNUM, COLUMN_VALUE FROM TABLE( CAST(:1 AS " + paramArrayDescriptor.getName() + ") )")).preparedStatement;
    }

    ((OraclePreparedStatement)localObject).setArray(1, localARRAY);
    ((OraclePreparedStatement)localObject).executeQuery();

    this.statement = ((OracleStatement)localObject);
  }

  public boolean next()
    throws SQLException
  {
    synchronized (this.connection)
    {
      if (this.currentIndex < this.beginIndex)
      {
        while (this.currentIndex < this.beginIndex)
        {
          this.currentIndex += 1L;

          if (!super.next()) {
            return false;
          }
        }
        return true;
      }

      if (this.count == COUNT_UNLIMITED)
      {
        return super.next();
      }
      if (this.currentIndex < this.beginIndex + this.count - 1L)
      {
        this.currentIndex += 1L;

        return super.next();
      }

      return false;
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
}