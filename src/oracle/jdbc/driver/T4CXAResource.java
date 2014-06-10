package oracle.jdbc.driver;

import java.io.IOException;
import java.sql.SQLException;
import javax.sql.XAConnection;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;
import oracle.jdbc.internal.OracleConnection;
import oracle.jdbc.xa.OracleXAConnection;
import oracle.jdbc.xa.OracleXAException;
import oracle.jdbc.xa.OracleXid;
import oracle.jdbc.xa.client.OracleXADataSource;
import oracle.jdbc.xa.client.OracleXAResource;

class T4CXAResource extends OracleXAResource
{
  T4CConnection physicalConn;
  int[] applicationValueArr = new int[1];
  boolean isTransLoose = false;
  byte[] context;
  int errorNumber;
  private String password;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  T4CXAResource(T4CConnection paramT4CConnection, OracleXAConnection paramOracleXAConnection, boolean paramBoolean)
    throws XAException
  {
    super(paramT4CConnection, paramOracleXAConnection);

    this.physicalConn = paramT4CConnection;
    this.isTransLoose = paramBoolean;
  }

  protected int doStart(Xid paramXid, int paramInt)
    throws XAException
  {
    synchronized (this.physicalConn)
    {
      int i = -1;

      if (this.isTransLoose) {
        paramInt |= 65536;
      }

      int j = paramInt & 0x8200000;

      if ((j == 134217728) && (OracleXid.isLocalTransaction(paramXid))) {
        return 0;
      }

      this.applicationValueArr[0] = 0;
      try
      {
        try
        {
          T4CTTIOtxse localT4CTTIOtxse = this.physicalConn.otxse;
          localObject1 = null;
          byte[] arrayOfByte1 = paramXid.getGlobalTransactionId();
          byte[] arrayOfByte2 = paramXid.getBranchQualifier();

          int k = 0;
          int m = 0;

          if ((arrayOfByte1 != null) && (arrayOfByte2 != null))
          {
            k = Math.min(arrayOfByte1.length, 64);
            m = Math.min(arrayOfByte2.length, 64);
            localObject1 = new byte[''];

            System.arraycopy(arrayOfByte1, 0, localObject1, 0, k);
            System.arraycopy(arrayOfByte2, 0, localObject1, k, m);
          }

          int n = 0;

          if (((paramInt & 0x200000) != 0) || ((paramInt & 0x8000000) != 0))
            n |= 4;
          else {
            n |= 1;
          }
          if ((paramInt & 0x100) != 0) {
            n |= 256;
          }
          if ((paramInt & 0x200) != 0) {
            n |= 512;
          }
          if ((paramInt & 0x400) != 0) {
            n |= 1024;
          }
          if ((paramInt & 0x10000) != 0) {
            n |= 65536;
          }

          this.physicalConn.needLine();
          this.physicalConn.sendPiggyBackedMessages();
          localT4CTTIOtxse.doOTXSE(1, null, (byte[])localObject1, paramXid.getFormatId(), k, m, this.timeout, n, this.applicationValueArr);

          this.applicationValueArr[0] = localT4CTTIOtxse.getApplicationValue();
          byte[] arrayOfByte3 = localT4CTTIOtxse.getContext();

          if (arrayOfByte3 != null) {
            this.context = arrayOfByte3;
          }
          i = 0;
        }
        catch (IOException localIOException)
        {
          Object localObject1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localIOException);
          ((SQLException)localObject1).fillInStackTrace();
          throw ((Throwable)localObject1);
        }

      }
      catch (SQLException localSQLException)
      {
        i = localSQLException.getErrorCode();

        if (i == 0) {
          throw new XAException(-6);
        }

      }

      return i;
    }
  }

  protected int doEnd(Xid paramXid, int paramInt, boolean paramBoolean)
    throws XAException
  {
    synchronized (this.physicalConn)
    {
      int i = -1;
      try
      {
        try
        {
          T4CTTIOtxse localT4CTTIOtxse = this.physicalConn.otxse;
          localObject1 = null;
          byte[] arrayOfByte1 = paramXid.getGlobalTransactionId();
          byte[] arrayOfByte2 = paramXid.getBranchQualifier();

          int j = 0;
          int k = 0;

          if ((arrayOfByte1 != null) && (arrayOfByte2 != null))
          {
            j = Math.min(arrayOfByte1.length, 64);
            k = Math.min(arrayOfByte2.length, 64);
            localObject1 = new byte[''];

            System.arraycopy(arrayOfByte1, 0, localObject1, 0, j);
            System.arraycopy(arrayOfByte2, 0, localObject1, j, k);
          }

          if (this.context == null)
          {
            i = doStart(paramXid, 134217728);

            if (i != 0) {
              return i;
            }
          }
          byte[] arrayOfByte3 = this.context;
          int m = 0;
          if ((paramInt & 0x2) == 2)
          {
            m = 1048576;
          } else if (((paramInt & 0x2000000) == 33554432) && ((paramInt & 0x100000) != 1048576))
          {
            m = 1048576;
          }

          this.applicationValueArr[0] >>= 16;

          this.physicalConn.needLine();
          this.physicalConn.sendPiggyBackedMessages();
          localT4CTTIOtxse.doOTXSE(2, arrayOfByte3, (byte[])localObject1, paramXid.getFormatId(), j, k, this.timeout, m, this.applicationValueArr);

          this.applicationValueArr[0] = localT4CTTIOtxse.getApplicationValue();
          byte[] arrayOfByte4 = localT4CTTIOtxse.getContext();

          if (arrayOfByte4 != null) {
            this.context = arrayOfByte4;
          }
          i = 0;
        }
        catch (IOException localIOException)
        {
          Object localObject1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localIOException);
          ((SQLException)localObject1).fillInStackTrace();
          throw ((Throwable)localObject1);
        }

      }
      catch (SQLException localSQLException)
      {
        i = localSQLException.getErrorCode();

        if (i == 0) {
          throw new XAException(-6);
        }
      }
      return i;
    }
  }

  protected void doCommit(Xid paramXid, boolean paramBoolean)
    throws SQLException, XAException
  {
    synchronized (this.physicalConn)
    {
      int i = paramBoolean ? 4 : 2;
      try
      {
        int j = doTransaction(paramXid, 1, i);

        if ((!paramBoolean) || ((j != 2) && (j != 4)))
        {
          if ((paramBoolean) || (j != 5))
          {
            if (j == 8) {
              throw new XAException(106);
            }
            throw new XAException(-6);
          }
        }
      } catch (SQLException localSQLException1) {
        int k = localSQLException1.getErrorCode();
        if (k == 24756)
        {
          kputxrec(paramXid, 1, this.timeout + 120, localSQLException1);
        }
        else if (k == 24780)
        {
          OracleXADataSource localOracleXADataSource = null;
          XAConnection localXAConnection = null;
          try
          {
            localOracleXADataSource = new OracleXADataSource();

            localOracleXADataSource.setURL(this.physicalConn.url);
            localOracleXADataSource.setUser(this.physicalConn.userName);
            this.physicalConn.getPasswordInternal(this);
            localOracleXADataSource.setPassword(this.password);

            localXAConnection = localOracleXADataSource.getXAConnection();

            XAResource localXAResource = localXAConnection.getXAResource();

            localXAResource.commit(paramXid, paramBoolean);
          }
          catch (SQLException localSQLException2)
          {
            XAException localXAException = new XAException(-6);
            localXAException.initCause(localSQLException2);
            throw localXAException;
          }
          finally
          {
            try
            {
              if (localXAConnection != null) {
                localXAConnection.close();
              }
              if (localOracleXADataSource != null)
                localOracleXADataSource.close();
            }
            catch (Exception localException2)
            {
            }
          }
        }
        else {
          throw localSQLException1;
        }
      }
    }
  }

  protected int doPrepare(Xid paramXid)
    throws XAException, SQLException
  {
    synchronized (this.physicalConn)
    {
      int i = -1;
      try {
        int j = doTransaction(paramXid, 3, 0);

        if (j == 8)
        {
          throw new XAException(106);
        }
        if (j == 4)
        {
          i = 3;
        }
        else if (j == 1)
        {
          i = 0;
        } else {
          if (j == 3)
          {
            throw new XAException(100);
          }

          throw new XAException(-6);
        }
      }
      catch (SQLException localSQLException)
      {
        int k = localSQLException.getErrorCode();

        if (k == 25351)
        {
          XAException localXAException = new XAException(-6);
          localXAException.initCause(localSQLException);
          throw localXAException;
        }
        throw localSQLException;
      }
      return i;
    }
  }

  protected int doForget(Xid paramXid)
    throws XAException, SQLException
  {
    synchronized (this.physicalConn)
    {
      int i = -1;

      if (OracleXid.isLocalTransaction(paramXid)) {
        return 24771;
      }

      int j = doStart(paramXid, 134217728);

      if (j != 24756)
      {
        if (j == 0)
        {
          try
          {
            doEnd(paramXid, 0, false);
          }
          catch (Exception localException)
          {
          }

        }

        if ((j == 0) || (j == 2079) || (j == 24754) || (j == 24761) || (j == 24774) || (j == 24776) || (j == 25351))
        {
          return 24769;
        }if (j == 24752) {
          return 24771;
        }
        return j;
      }

      kputxrec(paramXid, 4, 1, null);

      return i;
    }
  }

  protected void doRollback(Xid paramXid)
    throws XAException, SQLException
  {
    synchronized (this.physicalConn)
    {
      try
      {
        int i = doTransaction(paramXid, 2, 3);

        if (i == 8)
          throw new XAException(106);
        if (i != 3)
        {
          throw new XAException(-6);
        }
      }
      catch (SQLException localSQLException1) {
        int j = localSQLException1.getErrorCode();

        if (j == 24756)
        {
          kputxrec(paramXid, 2, this.timeout + 120, localSQLException1);
        }
        else if (j == 24780)
        {
          OracleXADataSource localOracleXADataSource = null;
          XAConnection localXAConnection = null;
          try
          {
            localOracleXADataSource = new OracleXADataSource();

            localOracleXADataSource.setURL(this.physicalConn.url);
            localOracleXADataSource.setUser(this.physicalConn.userName);
            this.physicalConn.getPasswordInternal(this);
            localOracleXADataSource.setPassword(this.password);

            localXAConnection = localOracleXADataSource.getXAConnection();

            XAResource localXAResource = localXAConnection.getXAResource();

            localXAResource.rollback(paramXid);
          }
          catch (SQLException localSQLException2)
          {
            XAException localXAException = new XAException(-6);
            localXAException.initCause(localSQLException2);
            throw localXAException;
          }
          finally
          {
            try
            {
              if (localXAConnection != null) {
                localXAConnection.close();
              }
              if (localOracleXADataSource != null)
                localOracleXADataSource.close();
            } catch (Exception localException2) {
            }
          }
        }
        else if (j != 25402)
        {
          throw localSQLException1;
        }
      }
    }
  }

  int doTransaction(Xid paramXid, int paramInt1, int paramInt2)
    throws SQLException
  {
    int i = -1;
    try
    {
      T4CTTIOtxen localT4CTTIOtxen = this.physicalConn.otxen;
      localObject = null;
      byte[] arrayOfByte1 = paramXid.getGlobalTransactionId();
      byte[] arrayOfByte2 = paramXid.getBranchQualifier();

      int j = 0;
      int k = 0;

      if ((arrayOfByte1 != null) && (arrayOfByte2 != null))
      {
        j = Math.min(arrayOfByte1.length, 64);
        k = Math.min(arrayOfByte2.length, 64);
        localObject = new byte[''];

        System.arraycopy(arrayOfByte1, 0, localObject, 0, j);
        System.arraycopy(arrayOfByte2, 0, localObject, j, k);
      }

      byte[] arrayOfByte3 = this.context;

      this.physicalConn.needLine();
      this.physicalConn.sendPiggyBackedMessages();
      localT4CTTIOtxen.doOTXEN(paramInt1, arrayOfByte3, (byte[])localObject, paramXid.getFormatId(), j, k, this.timeout, paramInt2, 0);

      i = localT4CTTIOtxen.getOutStateFromServer();
    }
    catch (IOException localIOException)
    {
      this.physicalConn.handleIOException(localIOException);

      Object localObject = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localIOException);
      ((SQLException)localObject).fillInStackTrace();
      throw ((Throwable)localObject);
    }

    return i;
  }

  protected void kputxrec(Xid paramXid, int paramInt1, int paramInt2, SQLException paramSQLException)
    throws XAException, SQLException
  {
    int i;
    switch (paramInt1)
    {
    case 1:
      i = 3;

      break;
    case 4:
      i = 2;

      break;
    default:
      i = 0;
    }

    int j = 0;

    while (paramInt2-- > 0)
    {
      j = doTransaction(paramXid, 5, i);

      if (j != 7) {
        break;
      }
      try
      {
        Thread.sleep(1000L);
      }
      catch (Exception localException)
      {
      }

    }

    if (j == 7)
    {
      throw new XAException(-6);
    }

    int m = -1;

    switch (j)
    {
    case 3:
      if (paramInt1 == 1) {
        k = 7;
      }
      else
      {
        k = 8;
        m = -3;
      }

      break;
    case 0:
      if (paramInt1 == 4)
      {
        k = 8;
        m = -3;
      }
      else
      {
        k = 7;
        if (paramInt1 == 1)
          m = -4;  } break;
    case 2:
      if (paramInt1 == 4)
      {
        k = 8;

        m = -6;
      }break;
    case 5:
      if (paramInt1 == 4)
      {
        k = 7;
      }
      else
      {
        m = 7;
        k = 8;
      }
      break;
    case 4:
      if (paramInt1 == 4)
      {
        k = 7;
      }
      else
      {
        m = 6;
        k = 8;
      }
      break;
    case 6:
      if (paramInt1 == 4)
      {
        k = 7;
      }
      else
      {
        m = 5;
        k = 8;
      }

      break;
    case 1:
    }
    m = -3;
    int k = 8;

    T4CTTIk2rpc localT4CTTIk2rpc = this.physicalConn.k2rpc;
    try
    {
      localT4CTTIk2rpc.doOK2RPC(3, k);
    }
    catch (IOException localIOException)
    {
      localXAException = new XAException(-7);
      localXAException.initCause(localIOException);
      throw localXAException;
    }
    catch (SQLException localSQLException)
    {
      XAException localXAException = new XAException(-6);
      localXAException.initCause(localSQLException);
      throw localXAException;
    }

    if (m != -1)
    {
      OracleXAException localOracleXAException = null;
      if (paramSQLException != null)
      {
        localOracleXAException = new OracleXAException(paramSQLException.getErrorCode(), m);
        localOracleXAException.initCause(paramSQLException);
      }
      else {
        localOracleXAException = new OracleXAException(0, m);
      }
      throw localOracleXAException;
    }
  }

  final void setPasswordInternal(String paramString)
  {
    this.password = paramString;
  }

  protected OracleConnection getConnectionDuringExceptionHandling()
  {
    return this.physicalConn;
  }
}