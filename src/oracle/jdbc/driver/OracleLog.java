package oracle.jdbc.driver;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.logging.Level;

public class OracleLog
{
  private static final int maxPrintBytes = 512;
  public static final boolean TRACE = false;
  public static final Level INTERNAL_ERROR = OracleLevel.INTERNAL_ERROR;
  public static final Level TRACE_1 = OracleLevel.TRACE_1;
  public static final Level TRACE_10 = OracleLevel.TRACE_10;
  public static final Level TRACE_16 = OracleLevel.TRACE_16;
  public static final Level TRACE_20 = OracleLevel.TRACE_20;
  public static final Level TRACE_30 = OracleLevel.TRACE_30;
  public static final Level TRACE_32 = OracleLevel.TRACE_32;
  static boolean securityExceptionWhileGettingSystemProperties;

  public static boolean isDebugZip()
  {
    boolean bool = true;

    bool = false;
    return bool;
  }

  public static boolean isPrivateLogAvailable()
  {
    boolean bool = false;

    return bool;
  }

  public static boolean isEnabled()
  {
    return false;
  }

  public static boolean registerClassNameAndGetCurrentTraceSetting(Class paramClass)
  {
    return false;
  }

  public static void setTrace(boolean paramBoolean)
  {
  }

  private static void initialize()
  {
    setupFromSystemProperties();
  }

  public static void setupFromSystemProperties()
  {
    boolean bool = false;
    securityExceptionWhileGettingSystemProperties = false;
    try
    {
      String str = null;
      str = getSystemProperty("oracle.jdbc.Trace", null);
      if ((str != null) && (str.compareTo("true") == 0))
        bool = true;
    }
    catch (SecurityException localSecurityException)
    {
      securityExceptionWhileGettingSystemProperties = true;
    }
    setTrace(bool);
  }

  private static String getSystemProperty(String paramString)
  {
    return getSystemProperty(paramString, null);
  }

  private static String getSystemProperty(String paramString1, String paramString2)
  {
    if (paramString1 != null)
    {
      final String str1 = paramString1;
      final String str2 = paramString2;
      String[] arrayOfString = { paramString2 };
      AccessController.doPrivileged(new PrivilegedAction()
      {
        public Object run()
        {
          this.val$retStr[0] = System.getProperty(str1, str2);
          return null;
        }
      });
      return arrayOfString[0];
    }
    return paramString2;
  }

  public static String argument()
  {
    return "";
  }

  public static String argument(boolean paramBoolean) {
    return Boolean.toString(paramBoolean);
  }

  public static String argument(byte paramByte) {
    return Byte.toString(paramByte);
  }

  public static String argument(short paramShort) {
    return Short.toString(paramShort);
  }

  public static String argument(int paramInt) {
    return Integer.toString(paramInt);
  }

  public static String argument(long paramLong) {
    return Long.toString(paramLong);
  }

  public static String argument(float paramFloat) {
    return Float.toString(paramFloat);
  }

  public static String argument(double paramDouble) {
    return Double.toString(paramDouble);
  }

  public static String argument(Object paramObject) {
    if (paramObject == null) return "null";
    if ((paramObject instanceof String)) return "\"" + (String)paramObject + "\"";
    return paramObject.toString();
  }

  /** @deprecated */
  public static String byteToHexString(byte paramByte)
  {
    StringBuffer localStringBuffer = new StringBuffer("");
    int i = 0xFF & paramByte;

    if (i <= 15)
      localStringBuffer.append("0x0");
    else {
      localStringBuffer.append("0x");
    }
    localStringBuffer.append(Integer.toHexString(i));

    return localStringBuffer.toString();
  }

  /** @deprecated */
  public static String bytesToPrintableForm(String paramString, byte[] paramArrayOfByte)
  {
    int i = paramArrayOfByte == null ? 0 : paramArrayOfByte.length;

    return bytesToPrintableForm(paramString, paramArrayOfByte, i);
  }

  /** @deprecated */
  public static String bytesToPrintableForm(String paramString, byte[] paramArrayOfByte, int paramInt)
  {
    String str = null;

    if (paramArrayOfByte == null)
      str = paramString + ": null";
    else {
      str = paramString + " (" + paramArrayOfByte.length + " bytes):\n" + bytesToFormattedStr(paramArrayOfByte, paramInt, "  ");
    }

    return str;
  }

  /** @deprecated */
  public static String bytesToFormattedStr(byte[] paramArrayOfByte, int paramInt, String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer("");

    if (paramString == null) {
      paramString = new String("");
    }
    localStringBuffer.append(paramString);

    if (paramArrayOfByte == null)
    {
      localStringBuffer.append("byte [] is null");

      return localStringBuffer.toString();
    }

    for (int i = 0; i < paramInt; i++)
    {
      if (i >= 512)
      {
        localStringBuffer.append("\n" + paramString + "... last " + (paramInt - 512) + " bytes were not printed to limit the output size");

        break;
      }

      if ((i > 0) && (i % 20 == 0)) {
        localStringBuffer.append("\n" + paramString);
      }
      if (i % 20 == 10) {
        localStringBuffer.append(" ");
      }
      int j = 0xFF & paramArrayOfByte[i];

      if (j <= 15) {
        localStringBuffer.append("0");
      }
      localStringBuffer.append(Integer.toHexString(j) + " ");
    }

    return localStringBuffer.toString();
  }

  /** @deprecated */
  public static byte[] strToUcs2Bytes(String paramString)
  {
    if (paramString == null) {
      return null;
    }
    return charsToUcs2Bytes(paramString.toCharArray());
  }

  /** @deprecated */
  public static byte[] charsToUcs2Bytes(char[] paramArrayOfChar)
  {
    if (paramArrayOfChar == null) {
      return null;
    }
    return charsToUcs2Bytes(paramArrayOfChar, paramArrayOfChar.length);
  }

  /** @deprecated */
  public static byte[] charsToUcs2Bytes(char[] paramArrayOfChar, int paramInt)
  {
    if (paramArrayOfChar == null) {
      return null;
    }
    if (paramInt < 0) {
      return null;
    }
    return charsToUcs2Bytes(paramArrayOfChar, paramInt, 0);
  }

  /** @deprecated */
  public static byte[] charsToUcs2Bytes(char[] paramArrayOfChar, int paramInt1, int paramInt2)
  {
    if (paramArrayOfChar == null) {
      return null;
    }
    if (paramInt1 > paramArrayOfChar.length - paramInt2) {
      paramInt1 = paramArrayOfChar.length - paramInt2;
    }
    if (paramInt1 < 0) {
      return null;
    }
    byte[] arrayOfByte = new byte[2 * paramInt1];

    int j = paramInt2; for (int i = 0; j < paramInt1; j++)
    {
      arrayOfByte[(i++)] = ((byte)(paramArrayOfChar[j] >> '\b' & 0xFF));
      arrayOfByte[(i++)] = ((byte)(paramArrayOfChar[j] & 0xFF));
    }

    return arrayOfByte;
  }

  /** @deprecated */
  public static String toPrintableStr(String paramString, int paramInt)
  {
    if (paramString == null)
    {
      return "null";
    }

    if (paramString.length() > paramInt)
    {
      return paramString.substring(0, paramInt - 1) + "\n ... the actual length was " + paramString.length();
    }

    return paramString;
  }

  public static String toHex(long paramLong, int paramInt)
  {
    String str;
    switch (paramInt)
    {
    case 1:
      str = "00" + Long.toString(paramLong & 0xFF, 16);
      break;
    case 2:
      str = "0000" + Long.toString(paramLong & 0xFFFF, 16);
      break;
    case 3:
      str = "000000" + Long.toString(paramLong & 0xFFFFFF, 16);
      break;
    case 4:
      str = "00000000" + Long.toString(paramLong & 0xFFFFFFFF, 16);
      break;
    case 5:
      str = "0000000000" + Long.toString(paramLong & 0xFFFFFFFF, 16);
      break;
    case 6:
      str = "000000000000" + Long.toString(paramLong & 0xFFFFFFFF, 16);
      break;
    case 7:
      str = "00000000000000" + Long.toString(paramLong & 0xFFFFFFFF, 16);

      break;
    case 8:
      return toHex(paramLong >> 32, 4) + toHex(paramLong, 4).substring(2);
    default:
      return "more than 8 bytes";
    }
    return "0x" + str.substring(str.length() - 2 * paramInt);
  }

  public static String toHex(byte paramByte)
  {
    String str = "00" + Integer.toHexString(paramByte & 0xFF);
    return "0x" + str.substring(str.length() - 2);
  }

  public static String toHex(short paramShort)
  {
    return toHex(paramShort, 2);
  }

  public static String toHex(int paramInt)
  {
    return toHex(paramInt, 4);
  }

  public static String toHex(byte[] paramArrayOfByte, int paramInt)
  {
    if (paramArrayOfByte == null)
      return "null";
    if (paramInt > paramArrayOfByte.length)
      return "byte array not long enough";
    String str = "[";
    int i = Math.min(64, paramInt);
    for (int j = 0; j < i; j++)
    {
      str = str + toHex(paramArrayOfByte[j]) + " ";
    }
    if (i < paramInt)
      str = str + "...";
    return str + "]";
  }

  public static String toHex(byte[] paramArrayOfByte)
  {
    if (paramArrayOfByte == null)
      return "null";
    return toHex(paramArrayOfByte, paramArrayOfByte.length);
  }

  static
  {
    initialize();
  }

  private static class OracleLevel extends Level
  {
    static final OracleLevel INTERNAL_ERROR = new OracleLevel("INTERNAL_ERROR", 1100);
    static final OracleLevel TRACE_1 = new OracleLevel("TRACE_1", Level.FINE.intValue());
    static final OracleLevel TRACE_10 = new OracleLevel("TRACE_10", 446);
    static final OracleLevel TRACE_16 = new OracleLevel("TRACE_16", Level.FINER.intValue());
    static final OracleLevel TRACE_20 = new OracleLevel("TRACE_20", 376);
    static final OracleLevel TRACE_30 = new OracleLevel("TRACE_30", 316);
    static final OracleLevel TRACE_32 = new OracleLevel("TRACE_32", Level.FINEST.intValue());

    OracleLevel(String paramString, int paramInt) {
      super(paramInt);
    }
  }
}