package oracle.sql.converter;

import java.sql.SQLException;
import java.util.Vector;
import oracle.i18n.text.converter.CharacterConverter;
import oracle.jdbc.internal.OracleConnection;

public class I18CharacterConvertersWrapper
  implements JdbcCharacterConverters
{
  CharacterConverter wrapper;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  public I18CharacterConvertersWrapper(CharacterConverter paramCharacterConverter)
  {
    this.wrapper = paramCharacterConverter;
  }

  public int getGroupId()
  {
    return this.wrapper.getGroupId();
  }

  public int getOracleId()
  {
    return this.wrapper.getOracleId();
  }

  public char[] getLeadingCodes()
  {
    return this.wrapper.getLeadingCodes();
  }

  public String toUnicodeString(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws SQLException
  {
    return this.wrapper.toUnicodeString(paramArrayOfByte, paramInt1, paramInt2);
  }

  public String toUnicodeStringWithReplacement(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    return this.wrapper.toUnicodeStringWithReplacement(paramArrayOfByte, paramInt1, paramInt2);
  }

  public byte[] toOracleString(String paramString)
    throws SQLException
  {
    return this.wrapper.toOracleString(paramString);
  }

  public byte[] toOracleStringWithReplacement(String paramString)
  {
    return this.wrapper.toOracleStringWithReplacement(paramString);
  }

  public void buildUnicodeToOracleMapping()
  {
    this.wrapper.buildUnicodeToOracleMapping();
  }

  public void extractCodepoints(Vector paramVector)
  {
    this.wrapper.extractCodepoints(paramVector);
  }

  public void extractExtraMappings(Vector paramVector)
  {
    this.wrapper.extractExtraMappings(paramVector);
  }

  public boolean hasExtraMappings()
  {
    return this.wrapper.hasExtraMappings();
  }

  public char getOraChar1ByteRep()
  {
    return this.wrapper.getOraChar1ByteRep();
  }

  public char getOraChar2ByteRep()
  {
    return this.wrapper.getOraChar2ByteRep();
  }

  public int getUCS2CharRep()
  {
    return this.wrapper.getUCS2CharRep();
  }

  public int toUnicodeChars(byte[] paramArrayOfByte, int paramInt1, char[] paramArrayOfChar, int paramInt2, int paramInt3)
    throws SQLException
  {
    return this.wrapper.toUnicodeCharsWithReplacement(paramArrayOfByte, paramInt1, paramArrayOfChar, paramInt2, paramInt3);
  }

  protected OracleConnection getConnectionDuringExceptionHandling()
  {
    return null;
  }
}