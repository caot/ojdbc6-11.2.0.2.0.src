package oracle.jdbc.driver;

import java.io.IOException;
import java.sql.SQLException;

final class T4C8Odscrarr extends T4CTTIfun
{
  private static final byte OPERATIONFLAGS = 7;
  private static final long SQLPARSEVERSION = 2L;
  byte[] sqltext;
  T4CTTIdcb dcb;
  int cursor_id = 0;

  int numuds = 0;
  private static final boolean UDSARRAYO2U = true;
  private static final boolean NUMUDSO2U = true;
  OracleStatement statement = null;
  private Accessor[] accessors;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  T4C8Odscrarr(T4CConnection paramT4CConnection)
  {
    super(paramT4CConnection, (byte)3);

    setFunCode((short)98);
    this.dcb = new T4CTTIdcb(paramT4CConnection);
  }

  void doODNY(OracleStatement paramOracleStatement, int paramInt, Accessor[] paramArrayOfAccessor, byte[] paramArrayOfByte)
    throws IOException, SQLException
  {
    this.numuds = 0;
    this.cursor_id = paramOracleStatement.cursorId;
    this.statement = paramOracleStatement;

    if ((paramArrayOfByte != null) && (paramArrayOfByte.length > 0))
      this.sqltext = paramArrayOfByte;
    else {
      this.sqltext = PhysicalConnection.EMPTY_BYTE_ARRAY;
    }
    this.dcb.init(paramOracleStatement, paramInt);
    this.accessors = paramArrayOfAccessor;
    this.numuds = 0;
    doRPC();
  }

  void marshal()
    throws IOException
  {
    this.meg.marshalUB1((short)7);
    this.meg.marshalSWORD(this.cursor_id);

    if (this.sqltext.length == 0)
      this.meg.marshalNULLPTR();
    else {
      this.meg.marshalPTR();
    }
    this.meg.marshalSB4(this.sqltext.length);

    this.meg.marshalUB4(2L);

    this.meg.marshalO2U(true);
    this.meg.marshalO2U(true);

    this.meg.marshalCHR(this.sqltext);
    this.sqltext = PhysicalConnection.EMPTY_BYTE_ARRAY;
  }

  Accessor[] getAccessors()
  {
    return this.accessors;
  }

  void readRPA()
    throws IOException, SQLException
  {
    this.accessors = this.dcb.receiveCommon(this.accessors, true);
    this.numuds = this.dcb.numuds;
  }
}