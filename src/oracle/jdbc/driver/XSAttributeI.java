package oracle.jdbc.driver;

import java.io.IOException;
import java.sql.SQLException;
import oracle.jdbc.internal.XSAttribute;

class XSAttributeI extends XSAttribute
{
  String attributeName;
  byte[] attributeNameBytes;
  String attributeValue;
  byte[] attributeValueBytes;
  String attributeDefaultValue;
  byte[] attributeDefaultValueBytes;
  long flag;

  XSAttributeI()
  {
    this.attributeName = null;
    this.attributeValue = null;
    this.attributeDefaultValue = null;
    this.flag = 0L;
  }

  public void setAttributeName(String paramString) throws SQLException {
    this.attributeName = paramString;
  }

  void doCharConversion(DBConversion paramDBConversion) throws SQLException {
    if (this.attributeName != null)
      this.attributeNameBytes = paramDBConversion.StringToCharBytes(this.attributeName);
    else {
      this.attributeNameBytes = null;
    }
    if (this.attributeValue != null)
      this.attributeValueBytes = paramDBConversion.StringToCharBytes(this.attributeValue);
    else {
      this.attributeValueBytes = null;
    }
    if (this.attributeDefaultValue != null)
      this.attributeDefaultValueBytes = paramDBConversion.StringToCharBytes(this.attributeDefaultValue);
    else
      this.attributeDefaultValueBytes = null;
  }

  public void setAttributeValue(String paramString) throws SQLException {
    this.attributeValue = paramString;
  }

  public void setAttributeDefaultValue(String paramString) throws SQLException {
    this.attributeDefaultValue = paramString;
  }

  public void setFlag(long paramLong) throws SQLException {
    this.flag = paramLong;
  }

  public String getAttributeName()
  {
    return this.attributeName;
  }

  public String getAttributeValue()
  {
    return this.attributeValue;
  }

  public String getAttributeDefaultValue() {
    return this.attributeDefaultValue;
  }

  public long getFlag() {
    return this.flag;
  }

  void marshal(T4CMAREngine paramT4CMAREngine) throws IOException {
    if (this.attributeNameBytes != null)
    {
      paramT4CMAREngine.marshalUB4(this.attributeNameBytes.length);
      paramT4CMAREngine.marshalCLR(this.attributeNameBytes, this.attributeNameBytes.length);
    }
    else {
      paramT4CMAREngine.marshalUB4(0L);
    }
    if (this.attributeValueBytes != null)
    {
      paramT4CMAREngine.marshalUB4(this.attributeValueBytes.length);
      paramT4CMAREngine.marshalCLR(this.attributeValueBytes, this.attributeValueBytes.length);
    }
    else {
      paramT4CMAREngine.marshalUB4(0L);
    }
    if (this.attributeDefaultValueBytes != null)
    {
      paramT4CMAREngine.marshalUB4(this.attributeDefaultValueBytes.length);
      paramT4CMAREngine.marshalCLR(this.attributeDefaultValueBytes, this.attributeDefaultValueBytes.length);
    }
    else {
      paramT4CMAREngine.marshalUB4(0L);
    }
    paramT4CMAREngine.marshalUB4(this.flag);
  }

  static XSAttributeI unmarshal(T4CMAREngine paramT4CMAREngine) throws SQLException, IOException {
    int[] arrayOfInt = new int[1];
    String str1 = null;
    String str2 = null;
    String str3 = null;

    int i = (int)paramT4CMAREngine.unmarshalUB4();
    if (i > 0)
    {
      byte[] arrayOfByte1 = new byte[i];
      paramT4CMAREngine.unmarshalCLR(arrayOfByte1, 0, arrayOfInt);
      str1 = paramT4CMAREngine.conv.CharBytesToString(arrayOfByte1, arrayOfInt[0]);
    }
    int j = (int)paramT4CMAREngine.unmarshalUB4();
    if (j > 0)
    {
      byte[] arrayOfByte2 = new byte[j];
      paramT4CMAREngine.unmarshalCLR(arrayOfByte2, 0, arrayOfInt);
      str2 = paramT4CMAREngine.conv.CharBytesToString(arrayOfByte2, arrayOfInt[0]);
    }
    int k = (int)paramT4CMAREngine.unmarshalUB4();
    if (k > 0)
    {
      localObject = new byte[k];
      paramT4CMAREngine.unmarshalCLR((byte[])localObject, 0, arrayOfInt);
      str3 = paramT4CMAREngine.conv.CharBytesToString((byte[])localObject, arrayOfInt[0]);
    }
    long l = paramT4CMAREngine.unmarshalUB4();
    Object localObject = new XSAttributeI();
    ((XSAttributeI)localObject).setAttributeName(str1);
    ((XSAttributeI)localObject).setAttributeValue(str2);
    ((XSAttributeI)localObject).setAttributeDefaultValue(str3);
    ((XSAttributeI)localObject).setFlag(l);
    return localObject;
  }
}