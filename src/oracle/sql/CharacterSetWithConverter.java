package oracle.sql;

import java.sql.SQLException;
import oracle.sql.converter.CharacterConverterFactory;
import oracle.sql.converter.CharacterConverterFactoryJDBC;
import oracle.sql.converter.JdbcCharacterConverters;

public abstract class CharacterSetWithConverter extends CharacterSet
{
  public static CharacterConverterFactory ccFactory = new CharacterConverterFactoryJDBC();
  JdbcCharacterConverters m_converter;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  CharacterSetWithConverter(int paramInt, JdbcCharacterConverters paramJdbcCharacterConverters)
  {
    super(paramInt);

    this.m_converter = paramJdbcCharacterConverters;
  }

  static CharacterSet getInstance(int paramInt)
  {
    JdbcCharacterConverters localJdbcCharacterConverters = ccFactory.make(paramInt);

    if (localJdbcCharacterConverters == null)
    {
      return null;
    }

    Object localObject = null;

    if ((localObject = CharacterSet1Byte.getInstance(paramInt, localJdbcCharacterConverters)) != null)
    {
      return localObject;
    }

    if ((localObject = CharacterSetSJIS.getInstance(paramInt, localJdbcCharacterConverters)) != null)
    {
      return localObject;
    }

    if ((localObject = CharacterSetShift.getInstance(paramInt, localJdbcCharacterConverters)) != null)
    {
      return localObject;
    }

    if ((localObject = CharacterSet2ByteFixed.getInstance(paramInt, localJdbcCharacterConverters)) != null)
    {
      return localObject;
    }

    if ((localObject = CharacterSetGB18030.getInstance(paramInt, localJdbcCharacterConverters)) != null)
    {
      return localObject;
    }

    if ((localObject = CharacterSet12Byte.getInstance(paramInt, localJdbcCharacterConverters)) != null)
    {
      return localObject;
    }

    if ((localObject = CharacterSetJAEUC.getInstance(paramInt, localJdbcCharacterConverters)) != null)
    {
      return localObject;
    }

    if ((localObject = CharacterSetZHTEUC.getInstance(paramInt, localJdbcCharacterConverters)) != null)
    {
      return localObject;
    }

    return CharacterSetLCFixed.getInstance(paramInt, localJdbcCharacterConverters);
  }

  public boolean isLossyFrom(CharacterSet paramCharacterSet)
  {
    return paramCharacterSet.getOracleId() != getOracleId();
  }

  public boolean isConvertibleFrom(CharacterSet paramCharacterSet)
  {
    return paramCharacterSet.getOracleId() == getOracleId();
  }

  public String toStringWithReplacement(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    return this.m_converter.toUnicodeStringWithReplacement(paramArrayOfByte, paramInt1, paramInt2);
  }

  public String toString(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws SQLException
  {
    return this.m_converter.toUnicodeString(paramArrayOfByte, paramInt1, paramInt2);
  }

  public byte[] convert(String paramString)
    throws SQLException
  {
    return this.m_converter.toOracleString(paramString);
  }

  public byte[] convertWithReplacement(String paramString)
  {
    return this.m_converter.toOracleStringWithReplacement(paramString);
  }

  public byte[] convert(CharacterSet paramCharacterSet, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws SQLException
  {
    if (paramCharacterSet.getOracleId() == getOracleId())
    {
      return useOrCopy(paramArrayOfByte, paramInt1, paramInt2);
    }

    return convert(paramCharacterSet.toString(paramArrayOfByte, paramInt1, paramInt2));
  }
}