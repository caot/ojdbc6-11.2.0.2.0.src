package oracle.sql.converter;

import java.sql.SQLException;
import java.util.Vector;

public abstract interface JdbcCharacterConverters
{
  public static final int CHARCONV1BYTEID = 0;
  public static final int CHARCONV12BYTEID = 1;
  public static final int CHARCONVJAEUCID = 2;
  public static final int CHARCONVLCFIXEDID = 3;
  public static final int CHARCONVSJISID = 4;
  public static final int CHARCONVZHTEUCID = 5;
  public static final int CHARCONV2BYTEFIXEDID = 6;
  public static final int CHARCONVSHIFTID = 7;
  public static final int CHARCONVLCID = 8;
  public static final int CHARCONVGB18030ID = 9;
  public static final int CHARCONVAL16UTF16ID = 10;
  public static final int CHARCONVMSOLISO2022JPFWID = 11;
  public static final int CHARCONVMSOLISO2022JPHWID = 12;
  public static final int CHARCONVGBKID = 13;

  public abstract int getGroupId();

  public abstract int getOracleId();

  public abstract String toUnicodeString(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws SQLException;

  public abstract String toUnicodeStringWithReplacement(byte[] paramArrayOfByte, int paramInt1, int paramInt2);

  public abstract int toUnicodeChars(byte[] paramArrayOfByte, int paramInt1, char[] paramArrayOfChar, int paramInt2, int paramInt3)
    throws SQLException;

  public abstract byte[] toOracleString(String paramString)
    throws SQLException;

  public abstract byte[] toOracleStringWithReplacement(String paramString);

  public abstract void buildUnicodeToOracleMapping();

  public abstract void extractCodepoints(Vector paramVector);

  public abstract void extractExtraMappings(Vector paramVector);

  public abstract boolean hasExtraMappings();

  public abstract char getOraChar1ByteRep();

  public abstract char getOraChar2ByteRep();

  public abstract int getUCS2CharRep();

  public abstract char[] getLeadingCodes();
}