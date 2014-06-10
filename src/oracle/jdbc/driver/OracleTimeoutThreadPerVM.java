package oracle.jdbc.driver;

import java.sql.SQLException;
import oracle.jdbc.internal.OracleConnection;

class OracleTimeoutThreadPerVM extends OracleTimeout
{
  private static final OracleTimeoutPollingThread watchdog = new OracleTimeoutPollingThread();
  private OracleStatement statement;
  private long interruptAfter;
  private String name;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  OracleTimeoutThreadPerVM(String paramString)
  {
    this.name = paramString;
    this.interruptAfter = 9223372036854775807L;
    watchdog.addTimeout(this);
  }

  void close()
  {
    watchdog.removeTimeout(this);
  }

  synchronized void setTimeout(long paramLong, OracleStatement paramOracleStatement)
    throws SQLException
  {
    if (this.interruptAfter != 9223372036854775807L)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 131);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.statement = paramOracleStatement;
    this.interruptAfter = (System.currentTimeMillis() + paramLong);
  }

  synchronized void cancelTimeout()
    throws SQLException
  {
    this.statement = null;
    this.interruptAfter = 9223372036854775807L;
  }

  void interruptIfAppropriate(long paramLong)
  {
    if (paramLong > this.interruptAfter)
    {
      synchronized (this)
      {
        if (paramLong > this.interruptAfter)
        {
          if (this.statement.connection.spawnNewThreadToCancel) {
            final OracleStatement localOracleStatement = this.statement;
            Thread localThread = new Thread(new Runnable() {
              public void run() {
                try {
                  localOracleStatement.cancel();
                }
                catch (Throwable localThrowable)
                {
                }
              }
            });
            localThread.setName("interruptIfAppropriate_" + this);
            localThread.setDaemon(true);
            localThread.setPriority(10);
            localThread.start();
          }
          else {
            try {
              this.statement.cancel();
            }
            catch (Throwable localThrowable)
            {
            }
          }

          this.statement = null;
          this.interruptAfter = 9223372036854775807L;
        }
      }
    }
  }

  protected OracleConnection getConnectionDuringExceptionHandling()
  {
    return null;
  }
}