package oracle.jdbc.xa;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Hashtable;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;
import oracle.jdbc.internal.OracleConnection;

public abstract class OracleXAResource
  implements XAResource
{
  public static final int XA_OK = 0;
  public static final short DEFAULT_XA_TIMEOUT = 60;
  protected boolean savedConnectionAutoCommit = false;
  protected boolean savedXAConnectionAutoCommit = false;
  public static final int TMNOFLAGS = 0;
  public static final int TMNOMIGRATE = 2;
  public static final int TMENDRSCAN = 8388608;
  public static final int TMFAIL = 536870912;
  public static final int TMMIGRATE = 1048576;
  public static final int TMJOIN = 2097152;
  public static final int TMONEPHASE = 1073741824;
  public static final int TMRESUME = 134217728;
  public static final int TMSTARTRSCAN = 16777216;
  public static final int TMSUCCESS = 67108864;
  public static final int TMSUSPEND = 33554432;
  public static final int ORATMREADONLY = 256;
  public static final int ORATMREADWRITE = 512;
  public static final int ORATMSERIALIZABLE = 1024;
  public static final int ORAISOLATIONMASK = 65280;
  public static final int ORATRANSLOOSE = 65536;
  protected Connection connection = null;
  protected OracleXAConnection xaconnection = null;
  protected int timeout = 60;
  protected String dblink = null;

  private Connection logicalConnection = null;

  private String synchronizeBeforeRecoverNewCall = "BEGIN sys.dbms_xa.dist_txn_sync \n; END;";

  private String synchronizeBeforeRecoverOldCall = "BEGIN sys.dbms_system.dist_txn_sync(0) \n; END;";

  private String recoverySqlRows = "SELECT formatid, globalid, branchid FROM SYS.DBA_PENDING_TRANSACTIONS";

  protected boolean canBeMigratablySuspended = false;

  private boolean isTMRScanStarted = false;

  private static final Xid[] NO_XID = new Xid[0];

  Xid lastActiveXid = null;

  protected Xid activeXid = null;

  protected Hashtable<Xid, XidListEntry> xidHash = new Hashtable(50);

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  public OracleXAResource()
  {
  }

  public OracleXAResource(Connection paramConnection, OracleXAConnection paramOracleXAConnection)
    throws XAException
  {
    this.connection = paramConnection;
    this.xaconnection = paramOracleXAConnection;

    if (this.connection == null)
      throw new XAException(-7);
  }

  public synchronized void setConnection(Connection paramConnection)
    throws XAException
  {
    this.connection = paramConnection;

    if (this.connection == null)
      throw new XAException(-7);
  }

  final synchronized XidListEntry getMatchingXidListEntry(Xid paramXid)
  {
    XidListEntry localXidListEntry = (XidListEntry)this.xidHash.get(paramXid);

    return localXidListEntry;
  }

  protected final synchronized boolean removeXidFromList(Xid paramXid)
  {
    if (isSameXid(this.activeXid, paramXid)) {
      this.activeXid = null;
    }
    return this.xidHash.remove(paramXid) != null;
  }

  final boolean isSameXid(Xid paramXid1, Xid paramXid2)
  {
    return paramXid1 == paramXid2;
  }

  protected final boolean isOnStack(Xid paramXid)
    throws XAException
  {
    return this.xidHash.containsKey(paramXid);
  }

  protected final synchronized boolean isXidListEmpty()
  {
    return this.xidHash.isEmpty();
  }

  protected synchronized void createOrUpdateXid(Xid paramXid, boolean paramBoolean, boolean[] paramArrayOfBoolean)
  {
    XidListEntry localXidListEntry = getMatchingXidListEntry(paramXid);

    if (localXidListEntry != null)
    {
      paramArrayOfBoolean[0] = true;

      localXidListEntry.isSuspended = paramBoolean;
    }
    else
    {
      localXidListEntry = new XidListEntry(paramXid, paramBoolean);
      this.xidHash.put(paramXid, localXidListEntry);
    }

    if (paramBoolean)
    {
      this.lastActiveXid = this.activeXid;
      this.activeXid = null;
    }
    else
    {
      enterGlobalTxnMode();

      if ((this.lastActiveXid != null) && (isSameXid(paramXid, this.lastActiveXid))) {
        this.lastActiveXid = null;
      }
      this.activeXid = localXidListEntry.xid;
    }
  }

  protected synchronized boolean updateXidList(Xid paramXid, boolean[] paramArrayOfBoolean)
  {
    boolean bool = false;
    XidListEntry localXidListEntry = getMatchingXidListEntry(paramXid);
    if (localXidListEntry != null)
    {
      bool = true;
      paramArrayOfBoolean[0] = true;
      paramArrayOfBoolean[1] = localXidListEntry.isSuspended;

      if (localXidListEntry.isSuspended)
      {
        enterGlobalTxnMode();
      }
      else
      {
        exitGlobalTxnMode();
      }
    }

    return bool;
  }

  protected boolean isXidSuspended(Xid paramXid)
    throws XAException
  {
    boolean bool = false;
    XidListEntry localXidListEntry = getMatchingXidListEntry(paramXid);

    if (localXidListEntry != null) {
      bool = localXidListEntry.isSuspended;
    }
    return bool;
  }

  protected Xid suspendStacked(Xid paramXid)
    throws XAException
  {
    Xid localXid = null;

    if ((this.activeXid != null) && (!isSameXid(this.activeXid, paramXid)))
    {
      localXid = this.activeXid;

      if (!isXidSuspended(this.activeXid))
      {
        end(this.activeXid, 33554432);
        this.lastActiveXid = this.activeXid;
        this.activeXid = null;
      }

    }

    return localXid;
  }

  protected void resumeStacked(Xid paramXid)
    throws XAException
  {
    if (paramXid != null)
    {
      start(paramXid, 134217728);
      this.activeXid = paramXid;
    }
  }

  public abstract void start(Xid paramXid, int paramInt)
    throws XAException;

  public abstract void end(Xid paramXid, int paramInt)
    throws XAException;

  public abstract void commit(Xid paramXid, boolean paramBoolean)
    throws XAException;

  public abstract int prepare(Xid paramXid)
    throws XAException;

  public abstract void forget(Xid paramXid)
    throws XAException;

  public abstract void rollback(Xid paramXid)
    throws XAException;

  public Xid[] recover(int paramInt)
    throws XAException
  {
    synchronized (this.connection)
    {
      if ((paramInt & 0x1800000) != paramInt)
      {
        throw new XAException(-5);
      }

      if (paramInt == 16777216) {
        this.isTMRScanStarted = true; } else {
        if ((this.isTMRScanStarted) && (paramInt == 8388608))
        {
          this.isTMRScanStarted = false;
          return NO_XID;
        }
        if ((this.isTMRScanStarted) && (paramInt == 0))
          return NO_XID;
      }
      Statement localStatement = null;
      ResultSet localResultSet = null;
      ArrayList localArrayList = new ArrayList(50);
      try
      {
        localStatement = this.connection.createStatement();
        try
        {
          localStatement.execute(this.synchronizeBeforeRecoverNewCall);
        }
        catch (Exception localException1)
        {
          localStatement.execute(this.synchronizeBeforeRecoverOldCall);
        }

        localResultSet = localStatement.executeQuery(this.recoverySqlRows);

        while (localResultSet.next())
        {
          localArrayList.add(new OracleXid(localResultSet.getInt(1), localResultSet.getBytes(2), localResultSet.getBytes(3)));
        }

      }
      catch (SQLException localSQLException)
      {
        throw new XAException(-3);
      }
      finally
      {
        try
        {
          if (localStatement != null) {
            localStatement.close();
          }
          if (localResultSet != null)
            localResultSet.close();
        }
        catch (Exception localException3) {
        }
      }
      int i = localArrayList.size();
      Xid[] arrayOfXid = new Xid[i];
      System.arraycopy(localArrayList.toArray(), 0, arrayOfXid, 0, i);

      return arrayOfXid;
    }
  }

  protected void restoreAutoCommitModeForGlobalTransaction()
    throws XAException
  {
    if ((this.savedConnectionAutoCommit) && (((OracleConnection)this.connection).getTxnMode() != 1))
    {
      try
      {
        this.connection.setAutoCommit(this.savedConnectionAutoCommit);
        this.xaconnection.setAutoCommit(this.savedXAConnectionAutoCommit);
      }
      catch (SQLException localSQLException)
      {
      }
    }
  }

  protected void saveAndAlterAutoCommitModeForGlobalTransaction()
    throws XAException
  {
    if (((OracleConnection)this.connection).getTxnMode() != 1)
    {
      try
      {
        this.savedConnectionAutoCommit = this.connection.getAutoCommit();
        this.connection.setAutoCommit(false);
        this.savedXAConnectionAutoCommit = this.xaconnection.getAutoCommit();
        this.xaconnection.setAutoCommit(false);
      }
      catch (SQLException localSQLException)
      {
      }
    }
  }

  public void resume(Xid paramXid)
    throws XAException
  {
    start(paramXid, 134217728);
  }

  public void join(Xid paramXid)
    throws XAException
  {
    start(paramXid, 2097152);
  }

  public void suspend(Xid paramXid)
    throws XAException
  {
    end(paramXid, 33554432);
  }

  public void join(Xid paramXid, int paramInt)
    throws XAException
  {
    this.timeout = paramInt;

    start(paramXid, 2097152);
  }

  public void resume(Xid paramXid, int paramInt)
    throws XAException
  {
    this.timeout = paramInt;

    start(paramXid, 134217728);
  }

  public Connection getConnection()
  {
    return this.connection;
  }

  public int getTransactionTimeout()
    throws XAException
  {
    return this.timeout;
  }

  public boolean isSameRM(XAResource paramXAResource)
    throws XAException
  {
    Connection localConnection = null;

    if ((paramXAResource instanceof OracleXAResource)) {
      localConnection = ((OracleXAResource)paramXAResource).getConnection();
    }
    else {
      return false;
    }

    try
    {
      if ((this.connection == null) || (((OracleConnection)this.connection).isClosed())) {
        return false;
      }
      String str1 = ((OracleConnection)this.connection).getURL();
      String str2 = ((OracleConnection)this.connection).getProtocolType();

      if (localConnection != null)
      {
        return (localConnection.equals(this.connection)) || (((OracleConnection)localConnection).getURL().equals(str1)) || ((((OracleConnection)localConnection).getProtocolType().equals(str2)) && (str2.equals("kprb")));
      }

    }
    catch (SQLException localSQLException)
    {
      throw new XAException(-3);
    }

    return false;
  }

  public boolean setTransactionTimeout(int paramInt)
    throws XAException
  {
    if (paramInt < 0) {
      throw new XAException(-5);
    }
    this.timeout = paramInt;

    return true;
  }

  public String getDBLink()
  {
    return this.dblink;
  }

  public void setDBLink(String paramString)
  {
    this.dblink = paramString;
  }

  public void setLogicalConnection(Connection paramConnection)
  {
    this.logicalConnection = paramConnection;
  }

  protected void allowGlobalTxnModeOnly(int paramInt)
    throws XAException
  {
    if (((OracleConnection)this.connection).getTxnMode() != 1)
    {
      throw new XAException(paramInt);
    }
  }

  protected void exitGlobalTxnMode()
  {
    ((OracleConnection)this.connection).setTxnMode(0);
  }

  protected void enterGlobalTxnMode()
  {
    ((OracleConnection)this.connection).setTxnMode(1);
  }

  protected void checkError(int paramInt)
    throws XAException
  {
    if ((paramInt & 0xFFFF) != 0)
    {
      XAException localXAException = OracleXAException.newXAException(getConnectionDuringExceptionHandling(), paramInt);
      localXAException.fillInStackTrace();
      throw localXAException;
    }
  }

  protected void checkError(int paramInt1, int paramInt2)
    throws XAException
  {
    if ((paramInt1 & 0xFFFF) != 0)
    {
      XAException localXAException = OracleXAException.newXAException(getConnectionDuringExceptionHandling(), paramInt1, paramInt2);
      localXAException.fillInStackTrace();
      throw localXAException;
    }
  }

  protected void checkError(SQLException paramSQLException, int paramInt)
    throws XAException
  {
    XAException localXAException = OracleXAException.newXAException(getConnectionDuringExceptionHandling(), paramSQLException, paramInt);
    localXAException.fillInStackTrace();
    throw localXAException;
  }

  protected OracleConnection getConnectionDuringExceptionHandling()
  {
    return (OracleConnection)this.connection;
  }

  class XidListEntry
  {
    Xid xid;
    boolean isSuspended;

    XidListEntry(Xid paramBoolean, boolean bool)
    {
      this.xid = paramBoolean;
      this.isSuspended = bool;
    }
  }
}