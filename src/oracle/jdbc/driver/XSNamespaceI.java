package oracle.jdbc.driver;

import java.io.IOException;
import java.sql.SQLException;
import oracle.jdbc.internal.XSAttribute;
import oracle.jdbc.internal.XSNamespace;
import oracle.sql.TIMESTAMPTZ;

class XSNamespaceI extends XSNamespace
{
  String namespaceName;
  byte[] namespaceNameBytes;
  XSAttributeI[] attributes;
  byte[] timestampBytes;
  long flag;
  byte[][] aclList;

  XSNamespaceI()
  {
    this.namespaceName = null;
    this.attributes = null;
    this.timestampBytes = null;
    this.flag = 0L;
    this.aclList = ((byte[][])null);
  }

  public void setNamespaceName(String paramString) throws SQLException {
    this.namespaceName = paramString;
  }

  public void setTimestamp(TIMESTAMPTZ paramTIMESTAMPTZ) throws SQLException {
    this.timestampBytes = paramTIMESTAMPTZ.toBytes();
  }

  private void setTimestamp(byte[] paramArrayOfByte) throws SQLException {
    this.timestampBytes = paramArrayOfByte;
  }

  public void setACLIdList(byte[][] paramArrayOfByte) throws SQLException {
    this.aclList = paramArrayOfByte;
  }

  public void setFlag(long paramLong) throws SQLException
  {
    this.flag = paramLong;
  }

  public void setAttributes(XSAttribute[] paramArrayOfXSAttribute) throws SQLException {
    if (paramArrayOfXSAttribute != null)
    {
      XSAttributeI[] arrayOfXSAttributeI = new XSAttributeI[paramArrayOfXSAttribute.length];
      for (int i = 0; i < paramArrayOfXSAttribute.length; i++) {
        arrayOfXSAttributeI[i] = ((XSAttributeI)paramArrayOfXSAttribute[i]);
      }
      this.attributes = arrayOfXSAttributeI;
    }
  }

  void doCharConversion(DBConversion paramDBConversion) throws SQLException
  {
    if (this.namespaceName != null)
      this.namespaceNameBytes = paramDBConversion.StringToCharBytes(this.namespaceName);
    else {
      this.namespaceNameBytes = null;
    }
    if (this.attributes != null)
    {
      for (int i = 0; i < this.attributes.length; i++)
        this.attributes[i].doCharConversion(paramDBConversion);
    }
  }

  public String getNamespaceName()
  {
    return this.namespaceName;
  }

  public TIMESTAMPTZ getTimestamp()
  {
    return new TIMESTAMPTZ(this.timestampBytes);
  }

  public long getFlag()
  {
    return this.flag;
  }

  public XSAttribute[] getAttributes()
  {
    return this.attributes;
  }

  public byte[][] getACLIdList() {
    return this.aclList;
  }

  void marshal(T4CMAREngine paramT4CMAREngine) throws IOException
  {
    if (this.namespaceNameBytes != null)
    {
      paramT4CMAREngine.marshalUB4(this.namespaceNameBytes.length);
      paramT4CMAREngine.marshalCLR(this.namespaceNameBytes, this.namespaceNameBytes.length);
    }
    else {
      paramT4CMAREngine.marshalUB4(0L);
    }
    if (this.timestampBytes != null)
    {
      paramT4CMAREngine.marshalUB4(this.timestampBytes.length);
      paramT4CMAREngine.marshalCLR(this.timestampBytes, this.timestampBytes.length);
    }
    else {
      paramT4CMAREngine.marshalUB4(0L);
    }
    paramT4CMAREngine.marshalUB4(this.flag);

    if (this.attributes != null)
    {
      paramT4CMAREngine.marshalUB4(this.attributes.length);

      paramT4CMAREngine.marshalUB1((short)28);
      for (int i = 0; i < this.attributes.length; i++)
        this.attributes[i].marshal(paramT4CMAREngine);
    }
    else {
      paramT4CMAREngine.marshalUB4(0L);
    }
    if (this.aclList != null)
    {
      byte[] arrayOfByte = new byte[this.aclList.length * 16];
      for (int j = 0; j < this.aclList.length; j++)
        System.arraycopy(this.aclList[j], 0, arrayOfByte, 16 * j, 16);
      paramT4CMAREngine.marshalUB4(arrayOfByte.length);
      paramT4CMAREngine.marshalCLR(arrayOfByte, arrayOfByte.length);
    }
    else {
      paramT4CMAREngine.marshalUB4(0L);
    }
  }

  static XSNamespaceI unmarshal(T4CMAREngine paramT4CMAREngine) throws SQLException, IOException { int[] arrayOfInt = new int[1];
    String str = null;

    int i = (int)paramT4CMAREngine.unmarshalUB4();
    if (i > 0)
    {
      byte[] arrayOfByte = new byte[i];
      paramT4CMAREngine.unmarshalCLR(arrayOfByte, 0, arrayOfInt);
      str = paramT4CMAREngine.conv.CharBytesToString(arrayOfByte, arrayOfInt[0]);
    }

    byte[] arrayOfByte = null;
    int j = (int)paramT4CMAREngine.unmarshalUB4();
    if (j > 0) {
      arrayOfByte = paramT4CMAREngine.unmarshalNBytes(j);
    }
    long l = paramT4CMAREngine.unmarshalUB4();

    XSAttribute[] arrayOfXSAttribute = null;
    int k = (int)paramT4CMAREngine.unmarshalUB4();
    arrayOfXSAttribute = new XSAttribute[k];
    if (k > 0)
      paramT4CMAREngine.unmarshalUB1();
    for (int m = 0; m < k; m++) {
      arrayOfXSAttribute[m] = XSAttributeI.unmarshal(paramT4CMAREngine);
    }
    int m = (int)paramT4CMAREngine.unmarshalUB4();
    byte[][] localObject1 = null;
    if (m > 0)
    {
      byte[] localObject2 = new byte[m];
      paramT4CMAREngine.unmarshalCLR((byte[])localObject2, 0, arrayOfInt);

      int n = (int)m / 16;
      localObject1 = new byte[n][];
      for (int i1 = 0; i1 < n; i1++)
      {
        localObject1[i1] = new byte[16];
        System.arraycopy(localObject2, i1 * 16, localObject1[i1], 0, 16);
      }
    }

    XSNamespaceI xsnamespacei = new XSNamespaceI();
    xsnamespacei.setNamespaceName(str);
    xsnamespacei.setTimestamp(arrayOfByte);
    xsnamespacei.setFlag(l);
    xsnamespacei.setAttributes(arrayOfXSAttribute);
    xsnamespacei.setACLIdList((byte[][])localObject1);
    return xsnamespacei;
  }
}