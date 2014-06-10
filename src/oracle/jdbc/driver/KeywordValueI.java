package oracle.jdbc.driver;

import java.io.IOException;
import java.sql.SQLException;
import oracle.jdbc.internal.KeywordValue;

class KeywordValueI extends KeywordValue
{
  private int keyword;
  private byte[] binaryValue;
  private String textValue;
  private byte[] textValueArr;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  KeywordValueI(int paramInt, String paramString, byte[] paramArrayOfByte)
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
      paramT4CMAREngine.marshalUB2(this.textValueArr.length);
      paramT4CMAREngine.marshalCLR(this.textValueArr, this.textValueArr.length);
      paramT4CMAREngine.marshalUB2(0);
    }
    else
    {
      paramT4CMAREngine.marshalUB2(0);
      if (this.binaryValue != null)
      {
        paramT4CMAREngine.marshalUB2(this.binaryValue.length);
        paramT4CMAREngine.marshalCLR(this.binaryValue, this.binaryValue.length);
      }
      else {
        paramT4CMAREngine.marshalUB2(0);
      }
    }
    paramT4CMAREngine.marshalUB2(this.keyword);
  }

  static KeywordValueI unmarshal(T4CMAREngine paramT4CMAREngine) throws SQLException, IOException
  {
    int[] arrayOfInt = new int[1];
    String str = null;
    byte[] arrayOfByte1 = null;
    int i = paramT4CMAREngine.unmarshalUB2();
    if (i != 0)
    {
      byte[] arrayOfByte2 = new byte[i];
      paramT4CMAREngine.unmarshalCLR(arrayOfByte2, 0, arrayOfInt);
      str = paramT4CMAREngine.conv.CharBytesToString(arrayOfByte2, arrayOfByte2.length);
    }

    int j = paramT4CMAREngine.unmarshalUB2();
    if (j != 0)
    {
      arrayOfByte1 = new byte[j];
      paramT4CMAREngine.unmarshalCLR(arrayOfByte1, 0, arrayOfInt);
    }

    int k = paramT4CMAREngine.unmarshalUB2();
    return new KeywordValueI(k, str, arrayOfByte1);
  }
}