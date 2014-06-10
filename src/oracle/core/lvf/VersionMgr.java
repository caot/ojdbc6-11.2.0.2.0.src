package oracle.core.lvf;

public final class VersionMgr
{
  public static final byte ALPHA = 1;
  public static final byte BETA = 2;
  public static final byte PROD = 3;
  public static final byte NONE = 4;
  private final byte MAX_LEN = 64;
  private final byte MAX_PRODLEN = 30;
  private final byte MAX_VERLEN = 15;
  private final byte MAX_DISTLEN = 5;
  private final String alpha = "Alpha";
  private final String beta = "Beta";
  private final String prod = "Production";
  private String version;

  public void setVersion(String paramString1, byte paramByte1, byte paramByte2, byte paramByte3, byte paramByte4, byte paramByte5, char paramChar, String paramString2, byte paramByte6, int paramInt)
  {
    char[] arrayOfChar = new char[64];

    String str2 = "";
    int k;
    if ((k = (byte)paramString1.length()) > 30) {
      k = 30;
    }

    int j = 0;
    while (true) { k = (byte)(k - 1); if (0 >= k)
        break;
      arrayOfChar[j] = paramString1.charAt(j);
      j = (byte)(j + 1);
    }

    j = (byte)(j + 1); arrayOfChar[j] = '\t';

    if (paramByte1 < 0)
      paramByte1 = 0;
    if (paramByte2 < 0)
      paramByte2 = 0;
    if (paramByte3 < 0)
      paramByte3 = 0;
    if (paramByte4 < 0)
      paramByte4 = 0;
    if (paramByte5 < 0) {
      paramByte5 = 0;
    }

    if (paramByte1 > 99)
      paramByte1 = 99;
    if (paramByte2 > 99)
      paramByte2 = 99;
    if (paramByte3 > 99)
      paramByte3 = 99;
    if (paramByte4 > 99)
      paramByte4 = 99;
    if (paramByte5 > 99)
      paramByte5 = 99;
    String str1;
    if (paramChar != 0)
      str1 = paramByte1 + "." + paramByte2 + "." + paramByte3 + "." + paramByte4 + "." + paramByte5 + paramChar;
    else
      str1 = paramByte1 + "." + paramByte2 + "." + paramByte3 + "." + paramByte4 + "." + paramByte5;
    int m = (byte)str1.length();

    int i = 0;
    while (true) { m = (byte)(m - 1); if (0 >= m) break;
      j = (byte)(j + 1); i = (byte)(i + 1); arrayOfChar[j] = str1.charAt(i);
    }
    if (paramByte6 != 4)
    {
      j = (byte)(j + 1); arrayOfChar[j] = '\t';

      if (paramString2 != null)
      {
        int i1 = 0;

        if ((i1 = (byte)paramString2.length()) > 5) {
          i1 = 5;
        }

        i = 0;
        while (true) { i1 = (byte)(i1 - 1); if (0 >= i1) break;
          j = (byte)(j + 1); i = (byte)(i + 1); arrayOfChar[j] = paramString2.charAt(i);
        }

        j = (byte)(j + 1); arrayOfChar[j] = '\t';
      }

      switch (paramByte6)
      {
      case 1:
        str2 = "Alpha";
        break;
      case 2:
        str2 = "Beta";
        break;
      case 3:
        str2 = "Production";
      }

      i = 0;
      int n = (byte)str2.length();
      while (true)
      {
        n = (byte)(n - 1); if (0 >= n) break;
        j = (byte)(j + 1); i = (byte)(i + 1); arrayOfChar[j] = str2.charAt(i);
      }
    }

    this.version = new String(arrayOfChar, 0, j);
  }

  public String getVersion()
  {
    return this.version;
  }
}