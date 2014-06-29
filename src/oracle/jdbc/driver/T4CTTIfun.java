package oracle.jdbc.driver;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import oracle.jdbc.internal.OracleConnection;
import oracle.net.ns.BreakNetException;

abstract class T4CTTIfun extends T4CTTIMsg
{
  static final short OOPEN = 2;
  static final short OEXEC = 4;
  static final short OFETCH = 5;
  static final short OCLOSE = 8;
  static final short OLOGOFF = 9;
  static final short OCOMON = 12;
  static final short OCOMOFF = 13;
  static final short OCOMMIT = 14;
  static final short OROLLBACK = 15;
  static final short OCANCEL = 20;
  static final short ODSCRARR = 43;
  static final short OVERSION = 59;
  static final short OK2RPC = 67;
  static final short OALL7 = 71;
  static final short OSQL7 = 74;
  static final short O3LOGON = 81;
  static final short O3LOGA = 82;
  static final short OKOD = 92;
  static final short OALL8 = 94;
  static final short OLOBOPS = 96;
  static final short ODNY = 98;
  static final short OTXSE = 103;
  static final short OTXEN = 104;
  static final short OCCA = 105;
  static final short O80SES = 107;
  static final short OAUTH = 115;
  static final short OSESSKEY = 118;
  static final short OCANA = 120;
  static final short OKPN = 125;
  static final short OOTCM = 127;
  static final short OSCID = 135;
  static final short OSPFPPUT = 138;
  static final short OKPFC = 139;
  static final short OPING = 147;
  static final short OKEYVAL = 154;
  static final short OXSSCS = 155;
  static final short OXSSRO = 156;
  static final short OXSSPO = 157;
  static final short OAQEQ = 121;
  static final short OAQDQ = 122;
  static final short OAQGPS = 132;
  static final short OAQLS = 126;
  static final short OAQXQ = 145;
  static final short OXSNS = 172;
  private short funCode;
  private final byte seqNumber = 0;
  protected final T4CTTIoer oer;
  int receiveState = 0;
  static final int IDLE_RECEIVE_STATE = 0;
  static final int ACTIVE_RECEIVE_STATE = 1;
  static final int READROW_RECEIVE_STATE = 2;
  static final int STREAM_RECEIVE_STATE = 3;
  boolean rpaProcessed = false;
  boolean rxhProcessed = false;
  boolean iovProcessed = false;

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  T4CTTIfun(T4CConnection paramT4CConnection, byte paramByte)
  {
    super(paramT4CConnection, paramByte);

    this.oer = paramT4CConnection.getT4CTTIoer();
  }

  final void setFunCode(short paramShort)
  {
    this.funCode = paramShort;
  }

  final short getFunCode()
  {
    return this.funCode;
  }

  private final void marshalFunHeader()
    throws IOException
  {
    marshalTTCcode();
    this.meg.marshalUB1(this.funCode);
    this.meg.marshalUB1((short)0);
  }

  abstract void marshal()
    throws IOException;

  final void doRPC()
    throws IOException, SQLException
  {
    if (getTTCCode() == 17)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 401);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    init();
    marshalFunHeader();
    try
    {
      this.connection.pipeState = 1;
      marshal();
      this.connection.pipeState = 2;
      receive();
    }
    finally {
      this.connection.pipeState = -1;
    }
  }

  final void doPigRPC()
    throws IOException
  {
    init();
    marshalFunHeader();
    marshal();
  }

  private void init()
  {
    this.rpaProcessed = false;
    this.rxhProcessed = false;
    this.iovProcessed = false;
  }

  void resumeReceive()
    throws SQLException, IOException
  {
    receive();
  }

  private void receive()
    throws SQLException, IOException
  {
    this.receiveState = 1;

    SQLException localObject1 = null;
_L16:
    while (true)
    {
      try
      {
        byte i = this.meg.unmarshalSB1();

        switch (i)
        {
        case 8:
_L5:
          if (this.rpaProcessed)
          {
            SQLException localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 401);
            localSQLException1.fillInStackTrace();
            throw localSQLException1;
          }
          readRPA();
          try
          {
            processRPA();
          }
          catch (SQLException localSQLException2)
          {
            localObject1 = localSQLException2;
          }
          this.rpaProcessed = true;
          break;
        case 21:
_L12:
          readBVC();
          break;
        case 11:
_L7:
          readIOV();
          this.iovProcessed = true;
          break;
        case 6:
_L3:
          readRXH();
          this.rxhProcessed = true;
          break;
        case 7:
_L4:
          this.receiveState = 2;

          if (readRXD())
          {
_L14:
            this.receiveState = 3;
            this.connection.sentCancel = false;
            return;
          }

_L15:
          this.receiveState = 1;

          break;
        case 16:
_L10:
          readDCB();
          break;
        case 14:
_L8:
          readLOBD();
          break;
        case 23:
//_L13:
          byte j = (byte)this.meg.unmarshalUB1();
          int k = this.meg.unmarshalUB2();
          byte m = (byte)this.meg.unmarshalUB1();
          int n;
          if (j == 1)
          {
            n = 0;
            while (n < k)
            {
              T4CTTIidc localT4CTTIidc = new T4CTTIidc(this.connection);
              localT4CTTIidc.unmarshal();

              n++; continue;
            }

          }
          else if (j == 2)
          {
            n = 0;
            while (n < k)
            {
              int i2 = this.meg.unmarshalUB1();

              n++; continue;
            }

          }
          else if (j == 3 || j == 4)
            break;

          if (j == 5)
          {
            T4CTTIkvarr localT4CTTIkvarr = new T4CTTIkvarr(this.connection);
            localT4CTTIkvarr.unmarshal();
            break;
          }

          short s;
          SQLException sqlexception;
          if (j == 6)
          {
            int i1 = 0;
            while (i1 < k)
            {
              NTFXSEvent localNTFXSEvent = new NTFXSEvent(this.connection);
              this.connection.notify(localNTFXSEvent);

              i1++;
            }
          }
          break;
        case 19:
_L11:
          this.meg.marshalUB1((short)19);
          break;
        case 15:
_L9:
          this.oer.init();
          this.oer.unmarshalWarning();
          try
          {
            this.oer.processWarning();
          }
          catch (SQLWarning localSQLWarning)
          {
            this.connection.setWarnings(DatabaseError.addSqlWarning(this.connection.getWarnings(), localSQLWarning));
          }

          break;
        case 9:
_L6:
          if (this.connection.getTTCVersion() >= 3)
          {
            s = (short)this.meg.unmarshalUB2();
            this.connection.endToEndECIDSequenceNumber = s;
          }

          this.connection.sentCancel = false;
          return;
        case 4:
_L2:
          this.oer.init();
          this.oer.unmarshal();
          try
          {
            processError();
          }
          catch (SQLException localSQLException3)
          {
            localObject1 = localSQLException3;
          }

          connection.sentCancel = false;
          break _L16;
        case 5:
        case 10:
        case 12:
        case 13:
        case 17:
        case 18:
        case 20:
        case 22:
        default:
          sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 401);
          sqlexception.fillInStackTrace();
          throw sqlexception;
        }
      }
      catch (BreakNetException localBreakNetException)
      {
      }
      finally
      {
        this.connection.sentCancel = false;
      }

    }

    this.receiveState = 0;

    if (localObject1 != null)
      throw localObject1;
  }

  void processRPA()
    throws SQLException
  {
  }

  void readRPA()
    throws IOException, SQLException
  {
  }

  void readBVC()
    throws IOException, SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 401);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  void readLOBD()
    throws IOException, SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 401);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  void readIOV()
    throws IOException, SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 401);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  void readRXH()
    throws IOException, SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 401);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  boolean readRXD()
    throws IOException, SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 401);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  void readDCB()
    throws IOException, SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 401);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  void processError()
    throws SQLException
  {
    this.oer.processError();
  }

  final int getErrorCode()
    throws SQLException
  {
    return this.oer.retCode;
  }

  protected OracleConnection getConnectionDuringExceptionHandling()
  {
    return null;
  }
}