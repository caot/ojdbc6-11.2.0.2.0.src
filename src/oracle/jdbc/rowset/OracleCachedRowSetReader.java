package oracle.jdbc.rowset;

import java.io.InputStream;
import java.io.Reader;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Properties;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.sql.RowSet;
import javax.sql.RowSetInternal;
import javax.sql.RowSetReader;
import oracle.jdbc.driver.DatabaseError;
import oracle.jdbc.driver.OracleDriver;
import oracle.jdbc.internal.OracleConnection;

public class OracleCachedRowSetReader
  implements RowSetReader, Serializable
{
  static final long serialVersionUID = -3565405169674271176L;
  static final transient int SETUNICODESTREAM_INTLENGTH = 1;
  static final transient int SETBINARYSTREAM_INTLENGTH = 2;
  static final transient int SETASCIISTREAM_INTLENGTH = 3;
  static final transient int SETCHARACTERSTREAM_INTLENGTH = 4;
  static final transient int SETBINARYSTREAM = 5;
  static final transient int SETBINARYSTREAM_LONGLENGTH = 6;
  static final transient int SETASCIISTREAM = 7;
  static final transient int SETASCIISTREAM_LONGLENGTH = 8;
  static final transient int SETCHARACTERSTREAM = 9;
  static final transient int SETCHARACTERSTREAM_LONGLENGTH = 10;
  static final transient int SETNCHARACTERSTREAM = 11;
  static final transient int SETNCHARACTERSTREAM_LONGLENGTH = 12;
  static final transient int SETBLOB_STREAM = 13;
  static final transient int SETBLOB_STREAM_LONG = 14;
  static final transient int SETCLOB_READER = 15;
  static final transient int SETCLOB_READER_LONG = 16;
  static final transient int SETNCLOB_READER = 17;
  static final transient int SETNCLOB_READER_LONG = 18;
  static final transient int TWO_PARAMETERS = 2;
  static final transient int THREE_PARAMETERS = 3;
  private static transient boolean driverManagerInitialized = false;

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  Connection getConnection(RowSetInternal paramRowSetInternal)
    throws SQLException
  {
    Object localObject1 = null;

    Connection localConnection = paramRowSetInternal.getConnection();
    if ((localConnection != null) && (!localConnection.isClosed()))
    {
      localObject1 = localConnection;
    }
    else
    {
      Object localObject2;
      String str2;
      Object localObject3;
      if (((RowSet)paramRowSetInternal).getDataSourceName() != null)
      {
        try
        {
          InitialContext localInitialContext = null;
          try
          {
            Properties localProperties = System.getProperties();
            localInitialContext = new InitialContext(localProperties);
          }
          catch (SecurityException localSecurityException) {
          }
          if (localInitialContext == null)
            localInitialContext = new InitialContext();
          localObject2 = (DataSource)localInitialContext.lookup(((RowSet)paramRowSetInternal).getDataSourceName());

          str2 = ((RowSet)paramRowSetInternal).getUsername();
          localObject3 = ((RowSet)paramRowSetInternal).getPassword();
          if ((str2 == null) && (localObject3 == null))
          {
            localObject1 = ((DataSource)localObject2).getConnection();
          }
          else
          {
            localObject1 = ((DataSource)localObject2).getConnection(str2, (String)localObject3);
          }

        }
        catch (NamingException localNamingException)
        {
          localObject2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 300, localNamingException.getMessage());
          ((SQLException)localObject2).fillInStackTrace();
          throw ((Throwable)localObject2);
        }

      }
      else if (((RowSet)paramRowSetInternal).getUrl() != null)
      {
        if (!driverManagerInitialized)
        {
          DriverManager.registerDriver(new OracleDriver());
          driverManagerInitialized = true;
        }
        String str1 = ((RowSet)paramRowSetInternal).getUrl();
        localObject2 = ((RowSet)paramRowSetInternal).getUsername();
        str2 = ((RowSet)paramRowSetInternal).getPassword();

        if ((str1.equals("")) || (((String)localObject2).equals("")) || (str2.equals("")))
        {
          localObject3 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 301);
          ((SQLException)localObject3).fillInStackTrace();
          throw ((Throwable)localObject3);
        }

        localObject1 = DriverManager.getConnection(str1, (String)localObject2, str2);
      }
    }
    return localObject1;
  }

  private void setParams(Object[] paramArrayOfObject, PreparedStatement paramPreparedStatement)
    throws SQLException
  {
    for (int i = 0; i < paramArrayOfObject.length; i++)
    {
      int j = 0;

      if ((paramArrayOfObject[i] instanceof byte[]))
      {
        paramPreparedStatement.setObject(i + 1, paramArrayOfObject[i]);
      }
      else
      {
        try
        {
          j = Array.getLength(paramArrayOfObject[i]);
        }
        catch (IllegalArgumentException localIllegalArgumentException)
        {
          paramPreparedStatement.setObject(i + 1, paramArrayOfObject[i]);
          continue;
        }

        Object[] arrayOfObject = (Object[])paramArrayOfObject[i];
        SQLException localSQLException;
        if (j == 2)
        {
          if (arrayOfObject[0] == null) {
            paramPreparedStatement.setNull(i + 1, ((Integer)arrayOfObject[1]).intValue());
          }
          else if ((arrayOfObject[0] instanceof Date))
          {
            if ((arrayOfObject[1] instanceof Calendar))
            {
              paramPreparedStatement.setDate(i + 1, (Date)arrayOfObject[0], (Calendar)arrayOfObject[1]);
            }
            else
            {
              localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 323);
              localSQLException.fillInStackTrace();
              throw localSQLException;
            }

          }
          else if ((arrayOfObject[0] instanceof Time))
          {
            if ((arrayOfObject[1] instanceof Calendar))
            {
              paramPreparedStatement.setTime(i + 1, (Time)arrayOfObject[0], (Calendar)arrayOfObject[1]);
            }
            else
            {
              localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 323);
              localSQLException.fillInStackTrace();
              throw localSQLException;
            }

          }
          else if ((arrayOfObject[0] instanceof Timestamp))
          {
            if ((arrayOfObject[1] instanceof Calendar))
            {
              paramPreparedStatement.setTimestamp(i + 1, (Timestamp)arrayOfObject[0], (Calendar)arrayOfObject[1]);
            }
            else
            {
              localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 323);
              localSQLException.fillInStackTrace();
              throw localSQLException;
            }

          }
          else if ((arrayOfObject[0] instanceof Reader))
          {
            switch (((Integer)arrayOfObject[1]).intValue())
            {
            case 9:
              paramPreparedStatement.setCharacterStream(i + 1, (Reader)arrayOfObject[0]);

              break;
            case 11:
              paramPreparedStatement.setNCharacterStream(i + 1, (Reader)arrayOfObject[0]);

              break;
            case 15:
              paramPreparedStatement.setClob(i + 1, (Reader)arrayOfObject[0]);
              break;
            case 17:
              paramPreparedStatement.setNClob(i + 1, (Reader)arrayOfObject[0]);
              break;
            case 10:
            case 12:
            case 13:
            case 14:
            case 16:
            default:
              localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 323);
              localSQLException.fillInStackTrace();
              throw localSQLException;
            }

          }
          else if ((arrayOfObject[0] instanceof InputStream))
          {
            switch (((Integer)arrayOfObject[1]).intValue())
            {
            case 7:
              paramPreparedStatement.setAsciiStream(i + 1, (InputStream)arrayOfObject[0]);

              break;
            case 5:
              paramPreparedStatement.setBinaryStream(i + 1, (InputStream)arrayOfObject[0]);

              break;
            case 13:
              paramPreparedStatement.setBlob(i + 1, (InputStream)arrayOfObject[0]);
              break;
            default:
              localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 323);
              localSQLException.fillInStackTrace();
              throw localSQLException;
            }

          }
          else if ((arrayOfObject[1] instanceof Integer)) {
            paramPreparedStatement.setObject(i + 1, arrayOfObject[0], ((Integer)arrayOfObject[1]).intValue());
          }

        }
        else if (j == 3)
        {
          if (arrayOfObject[0] == null)
          {
            paramPreparedStatement.setNull(i + 1, ((Integer)arrayOfObject[1]).intValue(), (String)arrayOfObject[2]);
          }
          else if ((arrayOfObject[0] instanceof Reader))
          {
            switch (((Integer)arrayOfObject[2]).intValue())
            {
            case 4:
              paramPreparedStatement.setCharacterStream(i + 1, (Reader)arrayOfObject[0], ((Integer)arrayOfObject[1]).intValue());

              break;
            case 10:
              paramPreparedStatement.setCharacterStream(i + 1, (Reader)arrayOfObject[0], ((Long)arrayOfObject[1]).longValue());

              break;
            case 12:
              paramPreparedStatement.setNCharacterStream(i + 1, (Reader)arrayOfObject[0], ((Long)arrayOfObject[1]).longValue());

              break;
            case 16:
              paramPreparedStatement.setClob(i + 1, (Reader)arrayOfObject[0], ((Long)arrayOfObject[1]).longValue());

              break;
            case 18:
              paramPreparedStatement.setNClob(i + 1, (Reader)arrayOfObject[0], ((Long)arrayOfObject[1]).longValue());

              break;
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 11:
            case 13:
            case 14:
            case 15:
            case 17:
            default:
              localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 323);
              localSQLException.fillInStackTrace();
              throw localSQLException;
            }

          }
          else if ((arrayOfObject[0] instanceof InputStream)) {
            switch (((Integer)arrayOfObject[2]).intValue())
            {
            case 1:
              paramPreparedStatement.setUnicodeStream(i + 1, (InputStream)arrayOfObject[0], ((Integer)arrayOfObject[1]).intValue());

              break;
            case 2:
              paramPreparedStatement.setBinaryStream(i + 1, (InputStream)arrayOfObject[0], ((Integer)arrayOfObject[1]).intValue());

              break;
            case 3:
              paramPreparedStatement.setAsciiStream(i + 1, (InputStream)arrayOfObject[0], ((Integer)arrayOfObject[1]).intValue());

              break;
            case 6:
              paramPreparedStatement.setBinaryStream(i + 1, (InputStream)arrayOfObject[0], ((Long)arrayOfObject[1]).longValue());

              break;
            case 8:
              paramPreparedStatement.setAsciiStream(i + 1, (InputStream)arrayOfObject[0], ((Long)arrayOfObject[1]).longValue());

              break;
            case 14:
              paramPreparedStatement.setBlob(i + 1, (InputStream)arrayOfObject[0], ((Long)arrayOfObject[1]).longValue());

              break;
            case 4:
            case 5:
            case 7:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            default:
              localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 323);
              localSQLException.fillInStackTrace();
              throw localSQLException;
            }
          }
          else if (((arrayOfObject[1] instanceof Integer)) && ((arrayOfObject[2] instanceof Integer)))
          {
            paramPreparedStatement.setObject(i + 1, arrayOfObject[0], ((Integer)arrayOfObject[1]).intValue(), ((Integer)arrayOfObject[2]).intValue());
          }
          else
          {
            localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 323);
            localSQLException.fillInStackTrace();
            throw localSQLException;
          }
        }
      }
    }
  }

  public synchronized void readData(RowSetInternal paramRowSetInternal)
    throws SQLException
  {
    OracleCachedRowSet localOracleCachedRowSet = (OracleCachedRowSet)paramRowSetInternal;

    Connection localConnection = getConnection(paramRowSetInternal);

    if ((localConnection == null) || (localOracleCachedRowSet.getCommand() == null))
    {
      SQLException localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 342);
      localSQLException1.fillInStackTrace();
      throw localSQLException1;
    }

    try
    {
      localConnection.setTransactionIsolation(localOracleCachedRowSet.getTransactionIsolation());
    }
    catch (Exception localException1)
    {
    }
    PreparedStatement localPreparedStatement = localConnection.prepareStatement(localOracleCachedRowSet.getCommand(), localOracleCachedRowSet.getType(), localOracleCachedRowSet.getConcurrency());

    setParams(paramRowSetInternal.getParams(), localPreparedStatement);
    try
    {
      localPreparedStatement.setMaxRows(localOracleCachedRowSet.getMaxRows());
      localPreparedStatement.setMaxFieldSize(localOracleCachedRowSet.getMaxFieldSize());
      localPreparedStatement.setEscapeProcessing(localOracleCachedRowSet.getEscapeProcessing());
      localPreparedStatement.setQueryTimeout(localOracleCachedRowSet.getQueryTimeout());
    } catch (Exception localException2) {
    }
    ResultSet localResultSet = localPreparedStatement.executeQuery();
    localOracleCachedRowSet.populate(localResultSet, localOracleCachedRowSet.getCurrentPage() * localOracleCachedRowSet.getPageSize());
    localResultSet.close();
    localPreparedStatement.close();
    try
    {
      localConnection.commit();
    }
    catch (SQLException localSQLException2)
    {
    }
    if (!localOracleCachedRowSet.isConnectionStayingOpen())
    {
      localConnection.close();
    }
  }

  protected OracleConnection getConnectionDuringExceptionHandling()
  {
    return null;
  }
}