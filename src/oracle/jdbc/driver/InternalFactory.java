package oracle.jdbc.driver;

import java.sql.SQLException;
import oracle.jdbc.aq.AQMessageProperties;

public abstract class InternalFactory
{
  public static KeywordValueI createKeywordValue(int paramInt, String paramString, byte[] paramArrayOfByte)
    throws SQLException
  {
    return new KeywordValueI(paramInt, paramString, paramArrayOfByte);
  }

  public static KeywordValueLongI createKeywordValueLong(int paramInt, String paramString, byte[] paramArrayOfByte)
    throws SQLException
  {
    return new KeywordValueLongI(paramInt, paramString, paramArrayOfByte);
  }

  public static XSAttributeI createXSAttribute()
    throws SQLException
  {
    return new XSAttributeI();
  }

  public static XSNamespaceI createXSNamespace()
    throws SQLException
  {
    return new XSNamespaceI();
  }

  public static AQMessagePropertiesI createAQMessageProperties()
    throws SQLException
  {
    return new AQMessagePropertiesI();
  }

  public static AQAgentI createAQAgent()
    throws SQLException
  {
    return new AQAgentI();
  }

  public static AQMessageI createAQMessage(AQMessageProperties paramAQMessageProperties)
    throws SQLException
  {
    return new AQMessageI((AQMessagePropertiesI)paramAQMessageProperties);
  }

  public static byte[] urowid2rowid(long[] paramArrayOfLong)
  {
    return T4CRowidAccessor.rowidToString(paramArrayOfLong);
  }

  public static long[] rowid2urowid(byte[] paramArrayOfByte, int paramInt1, int paramInt2) throws SQLException {
    return T4CRowidAccessor.stringToRowid(paramArrayOfByte, paramInt1, paramInt2);
  }
}