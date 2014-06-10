package oracle.jdbc.driver;

import java.io.IOException;
import java.sql.SQLException;
import oracle.jdbc.internal.KeywordValueLong;

class KeywordValueLongI extends KeywordValueLong
{
  private int keyword;
  private String textValue;
  private byte[] textValueArr;
  private byte[] binaryValue;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  KeywordValueLongI(int paramInt, String paramString, byte[] paramArrayOfByte)
  {
    this.keyword = paramInt;
    this.textValue = paramString;
    this.binaryValue = paramArrayOfByte;
    this.textValueArr = null;
  }

  void doCharConversion(DBConversion paramDBConversion) throws SQLException {
    if (this.textValue != null)
      this.textValueArr = paramDBConversion.StringToCharBytes(this.textValue);
    else
      this.textValueArr = null;
  }

  public byte[] getBinaryValue() throws SQLException
  {
    return this.binaryValue;
  }

  public String getTextValue() throws SQLException {
    return this.textValue;
  }

  public int getKeyword() throws SQLException {
    return this.keyword;
  }

  void marshal(T4CMAREngine paramT4CMAREngine) throws IOException
  {
    if (this.textValueArr != null)
    {
      paramT4CMAREngine.marshalUB4(this.textValueArr.length);
      paramT4CMAREngine.marshalCLR(this.textValueArr, this.textValueArr.length);
      paramT4CMAREngine.marshalUB4(0L);
    }
    else
    {
      paramT4CMAREngine.marshalUB4(0L);
      if (this.binaryValue != null)
      {
        paramT4CMAREngine.marshalUB4(this.binaryValue.length);
        paramT4CMAREngine.marshalCLR(this.binaryValue, this.binaryValue.length);
      }
      else {
        paramT4CMAREngine.marshalUB4(0L);
      }
    }
    paramT4CMAREngine.marshalUB2(this.keyword);
  }

  static KeywordValueLongI unmarshal(T4CMAREngine paramT4CMAREngine) throws SQLException, IOException
  {
    int[] arrayOfInt = new int[1];
    String str = null;
    byte[] arrayOfByte1 = null;
    int i = (int)paramT4CMAREngine.unmarshalUB4();
    if (i != 0)
    {
      byte[] arrayOfByte2 = new byte[i];
      paramT4CMAREngine.unmarshalCLR(arrayOfByte2, 0, arrayOfInt);
      str = paramT4CMAREngine.conv.CharBytesToString(arrayOfByte2, arrayOfByte2.length);
    }

    int j = (int)paramT4CMAREngine.unmarshalUB4();
    if (j != 0)
    {
      arrayOfByte1 = new byte[j];
      paramT4CMAREngine.unmarshalCLR(arrayOfByte1, 0, arrayOfInt);
    }

    int k = paramT4CMAREngine.unmarshalUB2();
    return new KeywordValueLongI(k, str, arrayOfByte1);
  }

  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("keyword    : " + this.keyword + "\n");
    localStringBuffer.append("text value : " + this.textValue + "\n");
    if (this.binaryValue == null)
      localStringBuffer.append("bin value  : null\n");
    else
      localStringBuffer.append("bin value  : " + NTFAQEvent.byteBufferToHexString(this.binaryValue, 50) + "\n");
    return localStringBuffer.toString();
  }
}