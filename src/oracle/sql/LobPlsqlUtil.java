package oracle.sql;

import java.sql.Connection;
import java.sql.SQLException;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.internal.OracleConnection;

public class LobPlsqlUtil
{
  static boolean PLSQL_DEBUG = false;
  static final int MAX_PLSQL_SIZE = 32512;
  static final int MAX_PLSQL_INSTR_SIZE = 32512;
  static final int MAX_CHUNK_SIZE = 32512;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  public static long hasPattern(BLOB paramBLOB, byte[] paramArrayOfByte, long paramLong)
    throws SQLException
  {
    return hasPattern(paramBLOB.getInternalConnection(), paramBLOB, 2004, paramArrayOfByte, paramLong);
  }

  public static long isSubLob(BLOB paramBLOB1, BLOB paramBLOB2, long paramLong)
    throws SQLException
  {
    return isSubLob(paramBLOB1.getInternalConnection(), paramBLOB1, 2004, paramBLOB2, paramLong);
  }

  public static long hasPattern(CLOB paramCLOB, char[] paramArrayOfChar, long paramLong)
    throws SQLException
  {
    if ((paramArrayOfChar == null) || (paramLong <= 0L)) {
      return 0L;
    }
    OracleConnection localOracleConnection = paramCLOB.getInternalConnection();
    long l1 = paramArrayOfChar.length;
    long l2 = length(localOracleConnection, paramCLOB, 2005);

    if ((l1 == 0L) || (l1 > l2 - paramLong + 1L) || (paramLong > l2))
    {
      return 0L;
    }

    if (l1 <= getPlsqlMaxInstrSize(localOracleConnection))
    {
      OracleCallableStatement localOracleCallableStatement = null;
      try
      {
        localOracleCallableStatement = (OracleCallableStatement)localOracleConnection.prepareCall("begin :1 := dbms_lob.instr(:2, :3, :4); end;");

        localOracleCallableStatement.registerOutParameter(1, 2);

        if (paramCLOB.isNCLOB())
        {
          localOracleCallableStatement.setFormOfUse(2, (short)2);
          localOracleCallableStatement.setFormOfUse(3, (short)2);
        }

        localOracleCallableStatement.setCLOB(2, paramCLOB);
        localOracleCallableStatement.setString(3, new String(paramArrayOfChar));
        localOracleCallableStatement.setLong(4, paramLong);
        localOracleCallableStatement.execute();

        return localOracleCallableStatement.getLong(1);
      }
      finally
      {
        localOracleCallableStatement.close();

        localOracleCallableStatement = null;
      }

    }

    int i = 0;
    long l3 = paramLong;
    int j = 0;

    long l5 = 0L;

    while (j == 0)
    {
      if (l1 > l2 - l3 + 1L) {
        return 0L;
      }
      i = 0;

      int k = (int)Math.min(getPlsqlMaxInstrSize(localOracleConnection), l1 - i);

      char[] arrayOfChar = new char[k];

      System.arraycopy(paramArrayOfChar, i, arrayOfChar, 0, k);

      long l4 = hasPattern(paramCLOB, arrayOfChar, l3);

      if (l4 == 0L)
      {
        return 0L;
      }

      l5 = l4;

      i += k;
      l3 = l4 + k;

      int m = 1;

      while (m != 0)
      {
        k = (int)Math.min(getPlsqlMaxInstrSize(localOracleConnection), l1 - i);

        arrayOfChar = new char[k];

        System.arraycopy(paramArrayOfChar, i, arrayOfChar, 0, k);

        l4 = hasPattern(paramCLOB, arrayOfChar, l3);

        if (l4 == l3)
        {
          i += k;
          l3 += k;

          if (i == l1)
          {
            m = 0;
            j = 1;
          }
        } else {
          if (l4 == 0L)
          {
            return 0L;
          }

          l3 = l4 - i;

          m = 0;
        }
      }

    }

    return l5;
  }

  public static long isSubLob(CLOB paramCLOB1, CLOB paramCLOB2, long paramLong)
    throws SQLException
  {
    if ((paramCLOB2 == null) || (paramLong <= 0L)) {
      return 0L;
    }
    OracleConnection localOracleConnection = paramCLOB1.getInternalConnection();
    long l1 = length(localOracleConnection, paramCLOB2, 2005);
    long l2 = length(localOracleConnection, paramCLOB1, 2005);

    if ((l1 == 0L) || (l1 > l2 - paramLong + 1L) || (paramLong > l2))
    {
      return 0L;
    }

    if (l1 <= getPlsqlMaxInstrSize(localOracleConnection))
    {
      char[] arrayOfChar1 = new char[(int)l1];

      paramCLOB2.getChars(1L, (int)l1, arrayOfChar1);

      return hasPattern(paramCLOB1, arrayOfChar1, paramLong);
    }

    int i = 0;
    long l3 = paramLong;
    int j = 0;

    long l5 = 0L;

    while (j == 0)
    {
      if (l1 > l2 - l3 + 1L) {
        return 0L;
      }
      i = 0;

      int k = (int)Math.min(getPlsqlMaxInstrSize(localOracleConnection), l1 - i);

      char[] arrayOfChar2 = new char[k];

      paramCLOB2.getChars(i + 1, k, arrayOfChar2);

      long l4 = hasPattern(paramCLOB1, arrayOfChar2, l3);

      if (l4 == 0L)
      {
        return 0L;
      }

      l5 = l4;

      i += k;
      l3 = l4 + k;

      int m = 1;

      while (m != 0)
      {
        k = (int)Math.min(getPlsqlMaxInstrSize(localOracleConnection), l1 - i);

        arrayOfChar2 = new char[k];

        paramCLOB2.getChars(i + 1, k, arrayOfChar2);

        l4 = hasPattern(paramCLOB1, arrayOfChar2, l3);

        if (l4 == l3)
        {
          i += k;
          l3 += k;

          if (i == l1)
          {
            m = 0;
            j = 1;
          }
        } else {
          if (l4 == 0L)
          {
            return 0L;
          }

          l3 = l4 - i;

          m = 0;
        }
      }

    }

    return l5;
  }

  public static long hasPattern(BFILE paramBFILE, byte[] paramArrayOfByte, long paramLong)
    throws SQLException
  {
    return hasPattern(paramBFILE.getInternalConnection(), paramBFILE, -13, paramArrayOfByte, paramLong);
  }

  public static long isSubLob(BFILE paramBFILE1, BFILE paramBFILE2, long paramLong)
    throws SQLException
  {
    return isSubLob(paramBFILE1.getInternalConnection(), paramBFILE1, -13, paramBFILE2, paramLong);
  }

  public static String fileGetName(BFILE paramBFILE)
    throws SQLException
  {
    OracleCallableStatement localOracleCallableStatement = null;
    String str = null;
    try
    {
      localOracleCallableStatement = (OracleCallableStatement)paramBFILE.getInternalConnection().prepareCall("begin dbms_lob.fileGetName(:1, :2, :3); end; ");

      localOracleCallableStatement.setBFILE(1, paramBFILE);
      localOracleCallableStatement.registerOutParameter(2, 12);
      localOracleCallableStatement.registerOutParameter(3, 12);
      localOracleCallableStatement.execute();

      str = localOracleCallableStatement.getString(3);
    }
    finally
    {
      if (localOracleCallableStatement != null)
      {
        localOracleCallableStatement.close();

        localOracleCallableStatement = null;
      }
    }
    return str;
  }

  public static String fileGetDirAlias(BFILE paramBFILE)
    throws SQLException
  {
    OracleCallableStatement localOracleCallableStatement = null;
    String str = null;
    try
    {
      localOracleCallableStatement = (OracleCallableStatement)paramBFILE.getInternalConnection().prepareCall("begin dbms_lob.fileGetName(:1, :2, :3); end; ");

      localOracleCallableStatement.setBFILE(1, paramBFILE);
      localOracleCallableStatement.registerOutParameter(2, 12);
      localOracleCallableStatement.registerOutParameter(3, 12);
      localOracleCallableStatement.execute();

      str = localOracleCallableStatement.getString(2);
    }
    finally
    {
      if (localOracleCallableStatement != null)
      {
        localOracleCallableStatement.close();

        localOracleCallableStatement = null;
      }
    }
    return str;
  }

  private static int getPlsqlMaxInstrSize(OracleConnection paramOracleConnection)
    throws SQLException
  {
    boolean bool = paramOracleConnection.isCharSetMultibyte(paramOracleConnection.getDriverCharSet());

    int i = paramOracleConnection.getMaxCharbyteSize();

    int j = 32512;

    if (bool) {
      j = 32512 / (paramOracleConnection.getC2SNlsRatio() * i);
    }
    return j;
  }

  public static long read(OracleConnection paramOracleConnection, Datum paramDatum, int paramInt, long paramLong1, long paramLong2, byte[] paramArrayOfByte)
    throws SQLException
  {
    OracleCallableStatement localOracleCallableStatement = null;
    int i = 0;
    try
    {
      localOracleCallableStatement = (OracleCallableStatement)paramOracleConnection.prepareCall("begin dbms_lob.read (:1, :2, :3, :4); end;");

      int j = 0;
      int k = 0;

      if (isNCLOB(paramDatum))
      {
        localOracleCallableStatement.setFormOfUse(1, (short)2);
        localOracleCallableStatement.setFormOfUse(4, (short)2);
      }

      localOracleCallableStatement.setObject(1, paramDatum, paramInt);
      localOracleCallableStatement.registerOutParameter(2, 2);
      localOracleCallableStatement.registerOutParameter(4, -3);

      while (i < paramLong2)
      {
        k = Math.min((int)paramLong2, 32512);

        localOracleCallableStatement.setInt(2, k);
        localOracleCallableStatement.setInt(3, (int)paramLong1 + i);
        localOracleCallableStatement.execute();

        j = localOracleCallableStatement.getInt(2);
        byte[] arrayOfByte = localOracleCallableStatement.getBytes(4);

        System.arraycopy(arrayOfByte, 0, paramArrayOfByte, i, j);

        i += j;
        paramLong2 -= j;
      }

    }
    catch (SQLException localSQLException)
    {
      if (localSQLException.getErrorCode() != 1403)
      {
        throw localSQLException;
      }

    }
    finally
    {
      if (localOracleCallableStatement != null)
      {
        localOracleCallableStatement.close();

        localOracleCallableStatement = null;
      }
    }

    return i;
  }

  public static long length(OracleConnection paramOracleConnection, Datum paramDatum, int paramInt)
    throws SQLException
  {
    long l = 0L;
    OracleCallableStatement localOracleCallableStatement = null;
    try
    {
      localOracleCallableStatement = (OracleCallableStatement)paramOracleConnection.prepareCall("begin :1 := dbms_lob.getLength (:2); end;");

      if (isNCLOB(paramDatum)) {
        localOracleCallableStatement.setFormOfUse(2, (short)2);
      }
      localOracleCallableStatement.setObject(2, paramDatum, paramInt);
      localOracleCallableStatement.registerOutParameter(1, 2);
      localOracleCallableStatement.execute();

      l = localOracleCallableStatement.getLong(1);
    }
    finally
    {
      if (localOracleCallableStatement != null)
      {
        localOracleCallableStatement.close();

        localOracleCallableStatement = null;
      }
    }

    return l;
  }

  public static long hasPattern(OracleConnection paramOracleConnection, Datum paramDatum, int paramInt, byte[] paramArrayOfByte, long paramLong)
    throws SQLException
  {
    if ((paramArrayOfByte == null) || (paramLong <= 0L)) {
      return 0L;
    }
    long l1 = paramArrayOfByte.length;
    long l2 = length(paramOracleConnection, paramDatum, paramInt);

    if ((l1 == 0L) || (l1 > l2 - paramLong + 1L) || (paramLong > l2))
    {
      return 0L;
    }

    if (l1 <= 32512L)
    {
      OracleCallableStatement localOracleCallableStatement = null;
      try
      {
        localOracleCallableStatement = (OracleCallableStatement)paramOracleConnection.prepareCall("begin :1 := dbms_lob.instr(:2, :3, :4); end;");

        localOracleCallableStatement.registerOutParameter(1, 2);
        localOracleCallableStatement.setObject(2, paramDatum, paramInt);
        localOracleCallableStatement.setBytes(3, paramArrayOfByte);
        localOracleCallableStatement.setLong(4, paramLong);
        localOracleCallableStatement.execute();

        return localOracleCallableStatement.getLong(1);
      }
      finally
      {
        localOracleCallableStatement.close();

        localOracleCallableStatement = null;
      }

    }

    int i = 0;
    long l3 = paramLong;
    int j = 0;

    long l5 = 0L;

    while (j == 0)
    {
      if (l1 > l2 - l3 + 1L) {
        return 0L;
      }
      i = 0;

      int k = (int)Math.min(32512L, l1 - i);

      byte[] arrayOfByte = new byte[k];

      System.arraycopy(paramArrayOfByte, i, arrayOfByte, 0, k);

      long l4 = hasPattern(paramOracleConnection, paramDatum, paramInt, arrayOfByte, l3);

      if (l4 == 0L)
      {
        return 0L;
      }

      l5 = l4;

      i += k;
      l3 = l4 + k;

      int m = 1;

      while (m != 0)
      {
        k = (int)Math.min(32512L, l1 - i);

        arrayOfByte = new byte[k];

        System.arraycopy(paramArrayOfByte, i, arrayOfByte, 0, k);

        l4 = hasPattern(paramOracleConnection, paramDatum, paramInt, arrayOfByte, l3);

        if (l4 == l3)
        {
          i += k;
          l3 += k;

          if (i == l1)
          {
            m = 0;
            j = 1;
          }
        } else {
          if (l4 == 0L)
          {
            return 0L;
          }

          l3 = l4 - i;

          m = 0;
        }
      }

    }

    return l5;
  }

  public static long isSubLob(OracleConnection paramOracleConnection, Datum paramDatum1, int paramInt, Datum paramDatum2, long paramLong)
    throws SQLException
  {
    if ((paramDatum2 == null) || (paramLong <= 0L)) {
      return 0L;
    }
    long l1 = length(paramOracleConnection, paramDatum2, paramInt);
    long l2 = length(paramOracleConnection, paramDatum1, paramInt);

    if ((l1 == 0L) || (l1 > l2 - paramLong + 1L) || (paramLong > l2))
    {
      return 0L;
    }

    if (l1 <= 32512L)
    {
      byte[] arrayOfByte1 = new byte[(int)l1];

      read(paramOracleConnection, paramDatum2, paramInt, 1L, l1, arrayOfByte1);

      return hasPattern(paramOracleConnection, paramDatum1, paramInt, arrayOfByte1, paramLong);
    }

    int i = 0;
    long l3 = paramLong;
    int j = 0;

    long l5 = 0L;

    while (j == 0)
    {
      if (l1 > l2 - l3 + 1L) {
        return 0L;
      }
      i = 0;

      int k = (int)Math.min(32512L, l1 - i);

      byte[] arrayOfByte2 = new byte[k];

      read(paramOracleConnection, paramDatum2, paramInt, i + 1, k, arrayOfByte2);

      long l4 = hasPattern(paramOracleConnection, paramDatum1, paramInt, arrayOfByte2, l3);

      if (l4 == 0L)
      {
        return 0L;
      }

      l5 = l4;

      i += k;
      l3 = l4 + k;

      int m = 1;

      while (m != 0)
      {
        k = (int)Math.min(32512L, l1 - i);

        arrayOfByte2 = new byte[k];

        read(paramOracleConnection, paramDatum2, paramInt, i + 1, k, arrayOfByte2);

        l4 = hasPattern(paramOracleConnection, paramDatum1, paramInt, arrayOfByte2, l3);

        if (l4 == l3)
        {
          i += k;
          l3 += k;

          if (i == l1)
          {
            m = 0;
            j = 1;
          }
        } else {
          if (l4 == 0L)
          {
            return 0L;
          }

          l3 = l4 - i;

          m = 0;
        }
      }

    }

    return l5;
  }

  private static boolean isNCLOB(Datum paramDatum)
  {
    Class localClass = null;
    try
    {
      localClass = Class.forName("oracle.sql.CLOB");
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
      return false;
    }

    if (!localClass.isInstance(paramDatum)) {
      return false;
    }
    CLOB localCLOB = (CLOB)paramDatum;

    return localCLOB.isNCLOB();
  }

  public static Datum createTemporaryLob(Connection paramConnection, boolean paramBoolean, int paramInt1, int paramInt2, short paramShort)
    throws SQLException
  {
    OracleCallableStatement localOracleCallableStatement = null;
    Datum localDatum = null;
    try
    {
      localOracleCallableStatement = (OracleCallableStatement)paramConnection.prepareCall(new StringBuilder().append("begin dbms_lob.createTemporary (:1,").append(paramBoolean ? "TRUE" : "FALSE").append(", :2); end;").toString());

      localOracleCallableStatement.registerOutParameter(1, paramInt2);
      localOracleCallableStatement.setFormOfUse(1, paramShort);

      localOracleCallableStatement.setInt(2, paramInt1);
      localOracleCallableStatement.execute();

      localDatum = localOracleCallableStatement.getOracleObject(1);
    }
    finally
    {
      if (localOracleCallableStatement != null)
      {
        localOracleCallableStatement.close();

        localOracleCallableStatement = null;
      }
    }

    return localDatum;
  }

  public static void freeTemporaryLob(Connection paramConnection, Datum paramDatum, int paramInt)
    throws SQLException
  {
    OracleCallableStatement localOracleCallableStatement = null;
    try
    {
      localOracleCallableStatement = (OracleCallableStatement)paramConnection.prepareCall("begin dbms_lob.freeTemporary (:1); end;");

      localOracleCallableStatement.registerOutParameter(1, paramInt);

      if (isNCLOB(paramDatum)) {
        localOracleCallableStatement.setFormOfUse(1, (short)2);
      }
      localOracleCallableStatement.setOracleObject(1, paramDatum);
      localOracleCallableStatement.execute();
      Datum localDatum = localOracleCallableStatement.getOracleObject(1);
      byte[] arrayOfByte = localDatum.shareBytes();
      paramDatum.setShareBytes(arrayOfByte);
    }
    finally
    {
      if (localOracleCallableStatement != null)
      {
        localOracleCallableStatement.close();

        localOracleCallableStatement = null;
      }
    }
  }
}