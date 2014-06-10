package oracle.jdbc.xa.client;

import java.io.IOException;
import java.sql.Connection;
import javax.transaction.xa.XAException;
import javax.transaction.xa.Xid;
import oracle.jdbc.oracore.Util;

public class OracleXAHeteroResource extends OracleXAResource
{
  private int rmid = -1;

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  public OracleXAHeteroResource(Connection paramConnection, OracleXAConnection paramOracleXAConnection)
    throws XAException
  {
    this.connection = paramConnection;
    this.xaconnection = paramOracleXAConnection;

    if (this.connection == null)
      throw new XAException(-7);
  }

  public void start(Xid paramXid, int paramInt)
    throws XAException
  {
    if (paramXid == null)
    {
      throw new XAException(-5);
    }

    int i = paramInt & 0xFF00;

    paramInt &= -65281;

    if ((paramInt & 0x8200002) != paramInt)
    {
      throw new XAException(-5);
    }

    if (((i & 0xFF00) != 0) && (i != 256) && (i != 512) && (i != 1024))
    {
      throw new XAException(-5);
    }

    if (((i & 0xFF00) != 0) && ((paramInt & 0x8200000) != 0))
    {
      throw new XAException(-5);
    }

    try
    {
      saveAndAlterAutoCommitModeForGlobalTransaction();

      paramInt |= i;

      int j = paramXid.getFormatId();
      byte[] arrayOfByte1 = paramXid.getGlobalTransactionId();
      byte[] arrayOfByte2 = paramXid.getBranchQualifier();

      int k = t2cDoXaStart(j, arrayOfByte1, arrayOfByte2, this.rmid, paramInt, 0);

      checkStatus(k);
      enterGlobalTxnMode();
    }
    catch (XAException localXAException)
    {
      restoreAutoCommitModeForGlobalTransaction();
      throw localXAException;
    }
  }

  public void end(Xid paramXid, int paramInt)
    throws XAException
  {
    try
    {
      if (paramXid == null)
      {
        throw new XAException(-5);
      }

      int i = 638582786;
      if ((paramInt & i) != paramInt)
      {
        throw new XAException(-5);
      }

      int j = paramXid.getFormatId();
      byte[] arrayOfByte1 = paramXid.getGlobalTransactionId();
      byte[] arrayOfByte2 = paramXid.getBranchQualifier();
      exitGlobalTxnMode();

      int k = t2cDoXaEnd(j, arrayOfByte1, arrayOfByte2, this.rmid, paramInt, 0);

      checkStatus(k);
    }
    finally
    {
      restoreAutoCommitModeForGlobalTransaction();
    }
  }

  public void commit(Xid paramXid, boolean paramBoolean)
    throws XAException
  {
    if (paramXid == null)
    {
      throw new XAException(-5);
    }

    int i = paramBoolean ? 1073741824 : 0;

    int j = paramXid.getFormatId();
    byte[] arrayOfByte1 = paramXid.getGlobalTransactionId();
    byte[] arrayOfByte2 = paramXid.getBranchQualifier();

    int k = t2cDoXaCommit(j, arrayOfByte1, arrayOfByte2, this.rmid, i, 0);

    checkStatus(k);
  }

  public int prepare(Xid paramXid)
    throws XAException
  {
    if (paramXid == null)
    {
      throw new XAException(-5);
    }

    int i = paramXid.getFormatId();
    byte[] arrayOfByte1 = paramXid.getGlobalTransactionId();
    byte[] arrayOfByte2 = paramXid.getBranchQualifier();

    int j = t2cDoXaPrepare(i, arrayOfByte1, arrayOfByte2, this.rmid, 0, 0);

    if ((j != 0) && (j != 3))
    {
      checkStatus(j);
    }

    return j;
  }

  public void forget(Xid paramXid)
    throws XAException
  {
    if (paramXid == null)
    {
      throw new XAException(-5);
    }

    int i = paramXid.getFormatId();
    byte[] arrayOfByte1 = paramXid.getGlobalTransactionId();
    byte[] arrayOfByte2 = paramXid.getBranchQualifier();

    int j = t2cDoXaForget(i, arrayOfByte1, arrayOfByte2, this.rmid, 0, 0);

    checkStatus(j);
  }

  public void rollback(Xid paramXid)
    throws XAException
  {
    if (paramXid == null)
    {
      throw new XAException(-5);
    }

    int i = paramXid.getFormatId();
    byte[] arrayOfByte1 = paramXid.getGlobalTransactionId();
    byte[] arrayOfByte2 = paramXid.getBranchQualifier();

    int j = t2cDoXaRollback(i, arrayOfByte1, arrayOfByte2, this.rmid, 0, 0);

    checkStatus(j);
  }

  private native int t2cDoXaStart(int paramInt1, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt2, int paramInt3, int paramInt4);

  private native int t2cDoXaEnd(int paramInt1, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt2, int paramInt3, int paramInt4);

  private native int t2cDoXaCommit(int paramInt1, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt2, int paramInt3, int paramInt4);

  private native int t2cDoXaPrepare(int paramInt1, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt2, int paramInt3, int paramInt4);

  private native int t2cDoXaForget(int paramInt1, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt2, int paramInt3, int paramInt4);

  private native int t2cDoXaRollback(int paramInt1, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt2, int paramInt3, int paramInt4);

  synchronized void setRmid(int paramInt)
  {
    this.rmid = paramInt;
  }

  synchronized int getRmid()
  {
    return this.rmid;
  }

  private static byte[] getSerializedBytes(Xid paramXid)
  {
    try
    {
      return Util.serializeObject(paramXid);
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }

    return null;
  }

  private void checkStatus(int paramInt)
    throws XAException
  {
    if (paramInt != 0)
      throw new XAException(paramInt);
  }
}