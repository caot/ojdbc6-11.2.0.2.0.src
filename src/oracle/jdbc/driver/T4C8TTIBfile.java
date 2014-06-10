package oracle.jdbc.driver;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import oracle.sql.Datum;

final class T4C8TTIBfile extends T4C8TTILob
{
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  T4C8TTIBfile(T4CConnection paramT4CConnection)
  {
    super(paramT4CConnection);
  }

  Datum createTemporaryLob(Connection paramConnection, boolean paramBoolean, int paramInt)
    throws SQLException, IOException
  {
    Object localObject = null;

    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), "cannot create a temporary BFILE", -1);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  boolean open(byte[] paramArrayOfByte, int paramInt)
    throws SQLException, IOException
  {
    boolean bool = false;

    bool = _open(paramArrayOfByte, 11, 256);

    return bool;
  }

  boolean close(byte[] paramArrayOfByte)
    throws SQLException, IOException
  {
    boolean bool = false;

    bool = _close(paramArrayOfByte, 512);

    return bool;
  }

  boolean isOpen(byte[] paramArrayOfByte)
    throws SQLException, IOException
  {
    boolean bool = _isOpen(paramArrayOfByte, 1024);

    return bool;
  }

  boolean doesExist(byte[] paramArrayOfByte)
    throws SQLException, IOException
  {
    boolean bool = false;

    initializeLobdef();

    this.sourceLobLocator = paramArrayOfByte;
    this.lobops = 2048L;
    this.nullO2U = true;

    doRPC();

    bool = this.lobnull;
    return bool;
  }
}