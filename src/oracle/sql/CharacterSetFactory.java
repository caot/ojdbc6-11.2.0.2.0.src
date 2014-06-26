package oracle.sql;

import java.io.PrintStream;
import java.sql.SQLException;

abstract class CharacterSetFactory
{
  public static final short DEFAULT_CHARSET = -1;
  public static final short ASCII_CHARSET = 1;
  public static final short ISO_LATIN_1_CHARSET = 31;
  public static final short UNICODE_1_CHARSET = 870;
  public static final short UNICODE_2_CHARSET = 871;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  public abstract CharacterSet make(int paramInt);

  public static void main(String[] paramArrayOfString)
  {
    CharacterSet localCharacterSet1 = CharacterSet.make(871);
    int[] arrayOfInt = { 1, 31, 870, 871 };

    for (int i = 0; i < arrayOfInt.length; i++)
    {
      CharacterSet localCharacterSet2 = CharacterSet.make(arrayOfInt[i]);

      String str1 = "longlonglonglong";
      str1 = new StringBuilder().append(str1).append(str1).append(str1).append(str1).toString();
      str1 = new StringBuilder().append(str1).append(str1).append(str1).append(str1).toString();
      str1 = new StringBuilder().append(str1).append(str1).append(str1).append(str1).toString();
      str1 = new StringBuilder().append(str1).append(str1).append(str1).append(str1).toString();

      String[] arrayOfString = { "abc", "ab?c", "XYZ", str1 };

      for (int j = 0; j < arrayOfString.length; j++)
      {
        String str2 = arrayOfString[j];
        String str3 = str2;

        if (str2.length() > 16)
        {
          str3 = new StringBuilder().append(str3.substring(0, 16)).append("...").toString();
        }

        System.out.println(new StringBuilder().append("testing ").append(localCharacterSet2).append(" against <").append(str3).append(">").toString());

        int k = 1;
        try
        {
          byte[] arrayOfByte1 = localCharacterSet2.convertWithReplacement(str2);
          String str4 = localCharacterSet2.toStringWithReplacement(arrayOfByte1, 0, arrayOfByte1.length);

          arrayOfByte1 = localCharacterSet2.convert(str4);

          String str5 = localCharacterSet2.toString(arrayOfByte1, 0, arrayOfByte1.length);

          if (!str4.equals(str5))
          {
            System.out.println(new StringBuilder().append("    FAILED roundTrip ").append(str5).toString());

            k = 0;
          }
          if (localCharacterSet2.isLossyFrom(localCharacterSet1))
          {
            try
            {
              byte[] arrayOfByte2 = localCharacterSet2.convert(str2);
              String localObject = localCharacterSet2.toString(arrayOfByte2, 0, arrayOfByte2.length);

              if (!localObject.equals(str5))
              {
                System.out.println("    FAILED roundtrip, no throw");
              }
            }
            catch (SQLException localSQLException) {
            }
          }
          else {
            if (!str5.equals(str2))
            {
              System.out.println(new StringBuilder().append("    FAILED roundTrip ").append(str5).toString());

              k = 0;
            }

            byte[] arrayOfByte3 = localCharacterSet1.convert(str2);
            byte[] localObject = localCharacterSet2.convert(localCharacterSet1, arrayOfByte3, 0, arrayOfByte3.length);

            String str6 = localCharacterSet2.toString((byte[])localObject, 0, localObject.length);

            if (!str6.equals(str2))
            {
              System.out.println(new StringBuilder().append("    FAILED withoutReplacement ").append(str6).toString());

              k = 0;
            }
          }
        }
        catch (Exception localException)
        {
          System.out.println(new StringBuilder().append("    FAILED with Exception ").append(localException).toString());
        }

        if (k != 0)
        {
          System.out.println(new StringBuilder().append("    PASSED ").append(localCharacterSet2.isLossyFrom(localCharacterSet1) ? "LOSSY" : "").toString());
        }
      }
    }
  }
}