package oracle.jdbc.xa.client;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import javax.transaction.xa.XAException;
import javax.transaction.xa.Xid;
import oracle.jdbc.internal.OracleConnection;
import oracle.jdbc.oracore.Util;
import oracle.jdbc.xa.OracleXAConnection;
import oracle.jdbc.xa.OracleXAException;

public class OracleXAResource extends oracle.jdbc.xa.OracleXAResource
{
  private short m_version = 0;

  private boolean needStackingForCommitRollbackPrepare = false;

  private static String xa_start_816 = "begin ? := JAVA_XA.xa_start(?,?,?,?); end;";

  private static String xa_start_post_816 = "begin ? := JAVA_XA.xa_start_new(?,?,?,?,?); end;";

  private static String xa_end_816 = "begin ? := JAVA_XA.xa_end(?,?); end;";
  private static String xa_end_post_816 = "begin ? := JAVA_XA.xa_end_new(?,?,?,?); end;";

  private static String xa_commit_816 = "begin ? := JAVA_XA.xa_commit (?,?,?); end;";

  private static String xa_commit_post_816 = "begin ? := JAVA_XA.xa_commit_new (?,?,?,?); end;";

  private static String xa_prepare_816 = "begin ? := JAVA_XA.xa_prepare (?,?); end;";

  private static String xa_prepare_post_816 = "begin ? := JAVA_XA.xa_prepare_new (?,?,?); end;";

  private static String xa_rollback_816 = "begin ? := JAVA_XA.xa_rollback (?,?); end;";

  private static String xa_rollback_post_816 = "begin ? := JAVA_XA.xa_rollback_new (?,?,?); end;";

  private static String xa_forget_816 = "begin ? := JAVA_XA.xa_forget (?,?); end;";

  private static String xa_forget_post_816 = "begin ? := JAVA_XA.xa_forget_new (?,?,?); end;";

  boolean isTransLoose = false;

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  public OracleXAResource()
  {
  }

  public OracleXAResource(Connection paramConnection, OracleXAConnection paramOracleXAConnection)
    throws XAException
  {
    super(paramConnection, paramOracleXAConnection);
    try
    {
      this.m_version = ((OracleConnection)paramConnection).getVersionNumber();
      this.needStackingForCommitRollbackPrepare = (this.m_version < 9000);
    }
    catch (SQLException localSQLException)
    {
    }

    if (this.m_version < 8170)
    {
      throw new XAException(-6);
    }
  }

  public void start(Xid paramXid, int paramInt)
    throws XAException
  {
    synchronized (this.connection)
    {
      int i = -1;
      try
      {
        if (paramXid == null)
        {
          throw new XAException(-5);
        }

        int j = paramInt & 0xFF00;

        paramInt &= -65281;

        int k = paramInt & 0x10000 | (this.isTransLoose ? 65536 : 0);

        paramInt &= -65537;

        if (((paramInt & 0x8200002) != paramInt) || ((k != 0) && ((k & 0x10000) != 65536)))
        {
          throw new XAException(-5);
        }

        if (((j & 0xFF00) != 0) && (j != 256) && (j != 512) && (j != 1024))
        {
          throw new XAException(-5);
        }

        if (((paramInt & 0x8200000) != 0) && (((j & 0xFF00) != 0) || ((k & 0x10000) != 0)))
        {
          throw new XAException(-5);
        }

        paramInt |= j | k;

        saveAndAlterAutoCommitModeForGlobalTransaction();
        try
        {
          i = doStart(paramXid, paramInt);
        }
        catch (SQLException localSQLException)
        {
          checkError(localSQLException, -3);
        }

        checkError(i);

        boolean[] arrayOfBoolean = { false };
        super.createOrUpdateXid(paramXid, false, arrayOfBoolean);
      }
      catch (XAException localXAException)
      {
        restoreAutoCommitModeForGlobalTransaction();

        throw localXAException;
      }
    }
  }

  protected int doStart(Xid paramXid, int paramInt)
    throws XAException, SQLException
  {
    int i = -1;
    CallableStatement localCallableStatement = null;
    try
    {
      localCallableStatement = this.connection.prepareCall(xa_start_post_816);

      localCallableStatement.registerOutParameter(1, 2);
      localCallableStatement.setInt(2, paramXid.getFormatId());
      localCallableStatement.setBytes(3, paramXid.getGlobalTransactionId());
      localCallableStatement.setBytes(4, paramXid.getBranchQualifier());
      localCallableStatement.setInt(5, this.timeout);
      localCallableStatement.setInt(6, paramInt);

      localCallableStatement.execute();

      i = localCallableStatement.getInt(1);
    }
    catch (SQLException localSQLException2)
    {
      i = localSQLException2.getErrorCode();

      if (i == 0) {
        throw new XAException(-6);
      }

      throw localSQLException2;
    }
    finally
    {
      try
      {
        if (localCallableStatement != null)
          localCallableStatement.close();
      }
      catch (SQLException localSQLException3) {
      }
      localCallableStatement = null;
    }

    return i;
  }

  public void end(Xid paramXid, int paramInt)
    throws XAException
  {
    synchronized (this.connection)
    {
      int i = -1;
      int j = 0;
      int k = 0;
      try
      {
        if (paramXid == null)
        {
          throw new XAException(-5);
        }

        int m = 638582786;
        if ((paramInt & m) != paramInt)
        {
          throw new XAException(-5);
        }

        Xid localXid = null;
        j = (paramInt & 0x4000000) != 0 ? 1 : 0;
        k = (paramInt & 0x20000000) != 0 ? 1 : 0;

        if ((j != 0) || (k != 0)) {
          localXid = super.suspendStacked(paramXid);
        }
        try
        {
          boolean bool = false;
          if ((j != 0) || (k != 0))
          {
            bool = isXidSuspended(paramXid);

            if (bool) {
              super.resumeStacked(paramXid);
            }

            removeXidFromList(paramXid);
          } else if (paramInt == 33554432)
          {
            boolean[] arrayOfBoolean = { false };
            super.createOrUpdateXid(paramXid, true, arrayOfBoolean);

            bool = arrayOfBoolean[0];
          }

          i = doEnd(paramXid, paramInt, bool);
        }
        catch (SQLException localSQLException)
        {
          checkError(localSQLException, -3);
        }

        if (localXid != null)
        {
          super.resumeStacked(localXid);
        } else if (isXidListEmpty())
        {
          exitGlobalTxnMode();
          this.activeXid = null;
        }

        checkError(i);

        if (((j != 0) && (paramInt != 67108864)) || ((k != 0) && (paramInt != 536870912)))
        {
          throw new XAException(-5);
        }
      }
      finally
      {
        restoreAutoCommitModeForGlobalTransaction();
      }
    }
  }

  protected int doEnd(Xid paramXid, int paramInt, boolean paramBoolean)
    throws XAException, SQLException
  {
    CallableStatement localCallableStatement = null;
    int i = -1;
    try
    {
      localCallableStatement = this.connection.prepareCall(xa_end_post_816);

      localCallableStatement.registerOutParameter(1, 2);
      localCallableStatement.setInt(2, paramXid.getFormatId());
      localCallableStatement.setBytes(3, paramXid.getGlobalTransactionId());
      localCallableStatement.setBytes(4, paramXid.getBranchQualifier());
      localCallableStatement.setInt(5, paramInt);
      localCallableStatement.execute();

      i = localCallableStatement.getInt(1);
    }
    catch (SQLException localSQLException2)
    {
      i = localSQLException2.getErrorCode();

      if (i == 0) {
        throw new XAException(-6);
      }

      throw localSQLException2;
    }
    finally
    {
      try
      {
        if (localCallableStatement != null)
          localCallableStatement.close();
      }
      catch (SQLException localSQLException3) {
      }
      localCallableStatement = null;
    }

    return i;
  }

  public void commit(Xid paramXid, boolean paramBoolean)
    throws XAException
  {
    synchronized (this.connection)
    {
      if (paramXid == null)
      {
        throw new XAException(-5);
      }

      Xid localXid = null;
      if (this.needStackingForCommitRollbackPrepare) {
        localXid = super.suspendStacked(paramXid);
      }
      else
      {
        removeXidFromList(paramXid);

        if (this.activeXid == null) {
          exitGlobalTxnMode();
        }
      }
      try
      {
        try
        {
          doCommit(paramXid, paramBoolean);
        }
        catch (SQLException localSQLException1)
        {
          checkError(localSQLException1, -3);
        }
      }
      catch (XAException localXAException)
      {
        if (localXAException.errorCode == -7)
        {
          try
          {
            this.connection.close();
          }
          catch (SQLException localSQLException2) {
          }
        }
        else if (this.needStackingForCommitRollbackPrepare) {
          super.resumeStacked(localXid);
        }
        throw localXAException;
      }

      if (this.needStackingForCommitRollbackPrepare)
        super.resumeStacked(localXid);
    }
  }

  protected void doCommit(Xid paramXid, boolean paramBoolean)
    throws XAException, SQLException
  {
    CallableStatement localCallableStatement = null;
    try
    {
      localCallableStatement = this.connection.prepareCall(xa_commit_post_816);

      localCallableStatement.registerOutParameter(1, 2);
      localCallableStatement.setInt(2, paramXid.getFormatId());
      localCallableStatement.setBytes(3, paramXid.getGlobalTransactionId());
      localCallableStatement.setBytes(4, paramXid.getBranchQualifier());
      localCallableStatement.setInt(5, paramBoolean ? 1 : 0);

      localCallableStatement.execute();

      int i = localCallableStatement.getInt(1);
      checkError(i, -7);
    }
    catch (SQLException localSQLException2)
    {
      int j = localSQLException2.getErrorCode();

      if (j == 0) {
        throw new XAException(-6);
      }

      throw localSQLException2;
    }
    finally
    {
      try
      {
        if (localCallableStatement != null)
          localCallableStatement.close();
      }
      catch (SQLException localSQLException3) {
      }
      localCallableStatement = null;
    }
  }

  public int prepare(Xid paramXid)
    throws XAException
  {
    synchronized (this.connection)
    {
      int i = 0;

      if (paramXid == null)
      {
        throw new XAException(-5);
      }

      Xid localXid = null;
      if (this.needStackingForCommitRollbackPrepare) {
        localXid = super.suspendStacked(paramXid);
      }
      try
      {
        try
        {
          i = doPrepare(paramXid);
          if ((i != 0) && (i != 3))
          {
            int j = OracleXAException.errorConvert(i);

            if ((j != 0) && (j != 3))
            {
              XAException localXAException2 = OracleXAException.newXAException(getConnectionDuringExceptionHandling(), i);
              localXAException2.fillInStackTrace();
              throw localXAException2;
            }

            i = j;
          }

        }
        catch (SQLException localSQLException1)
        {
          checkError(localSQLException1, -3);
        }
      }
      catch (XAException localXAException1)
      {
        if (localXAException1.errorCode == -7)
        {
          try
          {
            this.connection.close();
          }
          catch (SQLException localSQLException2) {
          }
        }
        else if (this.needStackingForCommitRollbackPrepare) {
          super.resumeStacked(localXid);
        }
        throw localXAException1;
      }

      if (this.needStackingForCommitRollbackPrepare) {
        super.resumeStacked(localXid);
      }
      return i;
    }
  }

  protected int doPrepare(Xid paramXid)
    throws XAException, SQLException
  {
    int i = 0;
    CallableStatement localCallableStatement = null;
    try
    {
      localCallableStatement = this.connection.prepareCall(xa_prepare_post_816);

      localCallableStatement.registerOutParameter(1, 2);
      localCallableStatement.setInt(2, paramXid.getFormatId());
      localCallableStatement.setBytes(3, paramXid.getGlobalTransactionId());
      localCallableStatement.setBytes(4, paramXid.getBranchQualifier());

      localCallableStatement.execute();

      i = localCallableStatement.getInt(1);
    }
    catch (SQLException localSQLException2)
    {
      int j = localSQLException2.getErrorCode();

      if (j == 0) {
        throw new XAException(-6);
      }

      throw localSQLException2;
    }
    finally
    {
      try
      {
        if (localCallableStatement != null)
          localCallableStatement.close();
      }
      catch (SQLException localSQLException3) {
      }
      localCallableStatement = null;
    }
    return i;
  }

  public void forget(Xid paramXid)
    throws XAException
  {
    synchronized (this.connection)
    {
      int i = 0;

      if (paramXid == null)
      {
        throw new XAException(-5);
      }

      removeXidFromList(paramXid);
      try
      {
        i = doForget(paramXid);
      }
      catch (SQLException localSQLException)
      {
        checkError(localSQLException, -3);
      }

      checkError(i);
    }
  }

  protected int doForget(Xid paramXid)
    throws XAException, SQLException
  {
    int i = 0;
    CallableStatement localCallableStatement = null;
    try
    {
      localCallableStatement = this.connection.prepareCall(xa_forget_post_816);

      localCallableStatement.registerOutParameter(1, 2);
      localCallableStatement.setInt(2, paramXid.getFormatId());
      localCallableStatement.setBytes(3, paramXid.getGlobalTransactionId());
      localCallableStatement.setBytes(4, paramXid.getBranchQualifier());

      localCallableStatement.execute();

      i = localCallableStatement.getInt(1);
    }
    catch (SQLException localSQLException2)
    {
      i = localSQLException2.getErrorCode();

      if (i == 0) {
        throw new XAException(-6);
      }

      throw localSQLException2;
    }
    finally
    {
      try
      {
        if (localCallableStatement != null)
          localCallableStatement.close();
      }
      catch (SQLException localSQLException3) {
      }
      localCallableStatement = null;
    }

    return i;
  }

  public void rollback(Xid paramXid)
    throws XAException
  {
    synchronized (this.connection)
    {
      int i = 0;

      if (paramXid == null)
      {
        throw new XAException(-5);
      }

      Xid localXid = null;
      if (this.needStackingForCommitRollbackPrepare) {
        localXid = super.suspendStacked(paramXid);
      }
      else
      {
        removeXidFromList(paramXid);

        if (this.activeXid == null)
          exitGlobalTxnMode();
      }
      try
      {
        doRollback(paramXid);
      }
      catch (SQLException localSQLException)
      {
        checkError(localSQLException, -3);
      }

      if (this.needStackingForCommitRollbackPrepare) {
        super.resumeStacked(localXid);
      }

      checkError(i);
    }
  }

  protected void doRollback(Xid paramXid)
    throws XAException, SQLException
  {
    CallableStatement localCallableStatement = null;
    try
    {
      localCallableStatement = this.connection.prepareCall(xa_rollback_post_816);

      localCallableStatement.registerOutParameter(1, 2);
      localCallableStatement.setInt(2, paramXid.getFormatId());
      localCallableStatement.setBytes(3, paramXid.getGlobalTransactionId());
      localCallableStatement.setBytes(4, paramXid.getBranchQualifier());

      localCallableStatement.execute();

      int i = localCallableStatement.getInt(1);

      checkError(i, -7);
    }
    catch (SQLException localSQLException2)
    {
      int j = localSQLException2.getErrorCode();

      if (j == 0) {
        throw new XAException(-6);
      }

      throw localSQLException2;
    }
    finally
    {
      try
      {
        if (localCallableStatement != null)
          localCallableStatement.close();
      }
      catch (SQLException localSQLException3) {
      }
      localCallableStatement = null;
    }
  }

  public void doTwoPhaseAction(int paramInt1, int paramInt2, String[] paramArrayOfString, Xid[] paramArrayOfXid)
    throws XAException
  {
    synchronized (this.connection)
    {
      doDoTwoPhaseAction(paramInt1, paramInt2, paramArrayOfString, paramArrayOfXid);
    }
  }

  protected int doDoTwoPhaseAction(int paramInt1, int paramInt2, String[] paramArrayOfString, Xid[] paramArrayOfXid)
    throws XAException
  {
    throw new XAException(-6);
  }

  private static byte[] getSerializedBytes(Xid paramXid)
  {
    try
    {
      return Util.serializeObject(paramXid);
    }
    catch (IOException localIOException)
    {
    }

    return null;
  }
}