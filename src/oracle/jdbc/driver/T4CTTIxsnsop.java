package oracle.jdbc.driver;

import java.io.IOException;
import java.sql.SQLException;
import oracle.jdbc.internal.OracleConnection;
import oracle.jdbc.internal.OracleConnection.XSOperationCode;
import oracle.jdbc.internal.XSNamespace;

class T4CTTIxsnsop extends T4CTTIfun
{
  private OracleConnection.XSOperationCode operationCode;
  private byte[] sessionId;
  private XSNamespace[] namespaces;
  private XSNamespace[] outNamespaces;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  T4CTTIxsnsop(T4CConnection paramT4CConnection)
  {
    super(paramT4CConnection, (byte)3);

    setFunCode((short)172);
  }

  void doOXSNS(OracleConnection.XSOperationCode paramXSOperationCode, byte[] paramArrayOfByte, XSNamespace[] paramArrayOfXSNamespace, boolean paramBoolean)
    throws IOException, SQLException
  {
    if (paramBoolean)
      setTTCCode((byte)3);
    else
      setTTCCode((byte)17);
    this.operationCode = paramXSOperationCode;
    this.sessionId = paramArrayOfByte;
    this.namespaces = paramArrayOfXSNamespace;

    if (this.namespaces != null) {
      for (int i = 0; i < this.namespaces.length; i++)
        ((XSNamespaceI)this.namespaces[i]).doCharConversion(this.meg.conv);
    }
    if (paramBoolean)
      doRPC();
    else
      doPigRPC();
  }

  void marshal()
    throws IOException
  {
    this.meg.marshalUB4(this.operationCode.getCode());
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
    this.meg.marshalPTR();
    if ((this.namespaces != null) && (this.namespaces.length > 0))
    {
      j = 1;
      this.meg.marshalUB4(this.namespaces.length);
    }
    else
    {
      this.meg.marshalUB4(0L);
    }
    this.meg.marshalPTR();

    if (i != 0)
      this.meg.marshalB1Array(this.sessionId);
    if (j != 0)
      for (int k = 0; k < this.namespaces.length; k++)
        ((XSNamespaceI)this.namespaces[k]).marshal(this.meg);
  }

  void readRPA()
    throws SQLException, IOException
  {
    this.outNamespaces = null;
    int i = (int)this.meg.unmarshalUB4();
    if (i > 0)
    {
      this.outNamespaces = new XSNamespace[i];
      for (int j = 0; j < i; j++)
        this.outNamespaces[j] = XSNamespaceI.unmarshal(this.meg);
    }
  }

  XSNamespace[] getNamespaces()
    throws SQLException
  {
    return this.outNamespaces;
  }

  protected OracleConnection getConnectionDuringExceptionHandling()
  {
    return this.connection;
  }
}