package oracle.jdbc.driver;

import java.io.IOException;
import java.sql.SQLException;
import oracle.jdbc.internal.KeywordValueLong;
import oracle.jdbc.internal.OracleConnection;

final class T4CTTIoxsscs extends T4CTTIfun
{
  private String userName = null;
  private KeywordValueLong[] inKV = null;
  private int inFlags;
  private byte[] userNameArr = null;

  private byte[] sessionId = null;
  private KeywordValueLong[] outKV = null;
  private int outFlags = -1;

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  T4CTTIoxsscs(T4CConnection paramT4CConnection)
  {
    super(paramT4CConnection, (byte)3);

    setFunCode((short)155);
  }

  void doOXSSCS(String paramString, KeywordValueLong[] paramArrayOfKeywordValueLong, int paramInt)
    throws IOException, SQLException
  {
    this.userName = paramString;
    this.inKV = paramArrayOfKeywordValueLong;
    this.inFlags = paramInt;
    if ((this.userName != null) && (this.userName.length() > 0))
      this.userNameArr = this.meg.conv.StringToCharBytes(this.userName);
    else {
      this.userNameArr = null;
    }

    this.sessionId = null;
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
    this.meg.marshalPTR();
    this.meg.marshalPTR();
    if (this.userNameArr != null)
    {
      this.meg.marshalPTR();
      this.meg.marshalSB4(this.userNameArr.length);
    }
    else
    {
      this.meg.marshalNULLPTR();
      this.meg.marshalSB4(0);
    }
    int i = 0;
    if ((this.inKV != null) && (this.inKV.length > 0))
    {
      i = 1;
      this.meg.marshalPTR();
      this.meg.marshalSB4(this.inKV.length);
    }
    else
    {
      this.meg.marshalNULLPTR();
      this.meg.marshalSB4(0);
    }
    this.meg.marshalUB4(this.inFlags);
    this.meg.marshalPTR();
    this.meg.marshalPTR();
    this.meg.marshalPTR();

    if (this.userNameArr != null)
      this.meg.marshalCHR(this.userNameArr);
    if (i != 0)
      for (int j = 0; j < this.inKV.length; j++)
        ((KeywordValueLongI)this.inKV[j]).marshal(this.meg);
  }

  byte[] getSessionId()
  {
    return this.sessionId;
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
    this.sessionId = this.meg.unmarshalNBytes(i);
    int j = (int)this.meg.unmarshalUB4();
    this.outKV = new KeywordValueLong[j];
    for (int k = 0; k < j; k++)
      this.outKV[k] = KeywordValueLongI.unmarshal(this.meg);
    this.outFlags = ((int)this.meg.unmarshalUB4());
  }

  protected OracleConnection getConnectionDuringExceptionHandling()
  {
    return this.connection;
  }
}