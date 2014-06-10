package oracle.jdbc.driver;

import java.io.IOException;
import java.sql.SQLException;
import oracle.jdbc.internal.KeywordValueLong;
import oracle.jdbc.internal.OracleConnection;

final class T4CTTIoxssro extends T4CTTIfun
{
  private int functionId;
  private byte[] sessionId = null;
  private KeywordValueLong[] inKV = null;
  private int inFlags;
  private KeywordValueLong[] outKV = null;
  private int outFlags = -1;

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  T4CTTIoxssro(T4CConnection paramT4CConnection)
  {
    super(paramT4CConnection, (byte)3);

    setFunCode((short)156);
  }

  void doOXSSRO(int paramInt1, byte[] paramArrayOfByte, KeywordValueLong[] paramArrayOfKeywordValueLong, int paramInt2)
    throws IOException, SQLException
  {
    this.functionId = paramInt1;
    this.sessionId = paramArrayOfByte;
    this.inKV = paramArrayOfKeywordValueLong;
    this.inFlags = paramInt2;
    this.outKV = null;
    this.outFlags = -1;

    if (this.inKV != null)
      for (int i = 0; i < this.inKV.length; i++)
        ((KeywordValueLongI)this.inKV[i]).doCharConversion(this.meg.conv);
    doRPC();
  }

  void marshal()
    throws IOException
  {
    this.meg.marshalUB4(this.functionId);
    int i = 0;
    if ((this.sessionId != null) && (this.sessionId.length > 0))
    {
      i = 1;
      this.meg.marshalPTR();
      this.meg.marshalUB4(this.sessionId.length);
    }
    else
    {
      this.meg.marshalNULLPTR();
      this.meg.marshalUB4(0L);
    }
    int j = 0;
    if ((this.inKV != null) && (this.inKV.length > 0))
    {
      j = 1;
      this.meg.marshalPTR();
      this.meg.marshalUB4(this.inKV.length);
    }
    else
    {
      this.meg.marshalNULLPTR();
      this.meg.marshalUB4(0L);
    }
    this.meg.marshalUB4(this.inFlags);
    this.meg.marshalPTR();
    this.meg.marshalPTR();
    this.meg.marshalPTR();

    if (i != 0)
      this.meg.marshalB1Array(this.sessionId);
    if (j != 0)
      for (int k = 0; k < this.inKV.length; k++)
        ((KeywordValueLongI)this.inKV[k]).marshal(this.meg);
  }

  KeywordValueLong[] getOutKV()
  {
    return this.outKV;
  }

  int getOutFlags()
  {
    return this.outFlags;
  }

  void readRPA()
    throws SQLException, IOException
  {
    int i = (int)this.meg.unmarshalUB4();
    this.outKV = new KeywordValueLong[i];
    for (int j = 0; j < i; j++)
      this.outKV[j] = KeywordValueLongI.unmarshal(this.meg);
    this.outFlags = ((int)this.meg.unmarshalUB4());
  }

  protected OracleConnection getConnectionDuringExceptionHandling()
  {
    return this.connection;
  }
}