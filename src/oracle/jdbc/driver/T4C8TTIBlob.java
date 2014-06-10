package oracle.jdbc.driver;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import oracle.jdbc.OracleConnection;
import oracle.sql.BLOB;
import oracle.sql.Datum;

final class T4C8TTIBlob extends T4C8TTILob
{
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  T4C8TTIBlob(T4CConnection paramT4CConnection)
  {
    super(paramT4CConnection);
  }

  Datum createTemporaryLob(Connection paramConnection, boolean paramBoolean, int paramInt)
    throws SQLException, IOException
  {
    if (paramInt == 12)
    {
      localObject = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 158);
      ((SQLException)localObject).fillInStackTrace();
      throw ((Throwable)localObject);
    }

    Object localObject = null;

    initializeLobdef();

    this.lobops = 272L;
    this.sourceLobLocator = new byte[86];
    this.sourceLobLocator[1] = 84;

    this.characterSet = 1;

    this.lobamt = paramInt;
    this.sendLobamt = true;

    this.destinationOffset = 113L;

    this.nullO2U = true;

    if (this.connection.versionNumber >= 9000)
    {
      this.lobscn = new int[1];
      this.lobscn[0] = (paramBoolean ? 1 : 0);
      this.lobscnl = 1;
    }

    doRPC();

    if (this.sourceLobLocator != null)
    {
      localObject = new BLOB((OracleConnection)paramConnection, this.sourceLobLocator);
    }

    return localObject;
  }

  boolean open(byte[] paramArrayOfByte, int paramInt)
    throws SQLException, IOException
  {
    boolean bool = false;

    int i = 2;

    if (paramInt == 0) {
      i = 1;
    }
    bool = _open(paramArrayOfByte, i, 32768);

    return bool;
  }

  boolean close(byte[] paramArrayOfByte)
    throws SQLException, IOException
  {
    boolean bool = false;

    bool = _close(paramArrayOfByte, 65536);

    return bool;
  }

  boolean isOpen(byte[] paramArrayOfByte)
    throws SQLException, IOException
  {
    boolean bool = false;

    bool = _isOpen(paramArrayOfByte, 69632);

    return bool;
  }
}