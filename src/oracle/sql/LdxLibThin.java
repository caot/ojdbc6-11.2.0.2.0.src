package oracle.sql;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import oracle.core.lmx.CoreException;

class LdxLibThin
  implements LdxLib
{
  private static final int LDXFDLSZ = 50;
  private static final byte LDX_CC = 1;
  private static final byte LDX_SCC = 2;
  private static final byte LDX_I = 3;
  private static final byte LDX_Y = 4;
  private static final byte LDX_IY = 5;
  private static final byte LDX_YY = 6;
  private static final byte LDX_IYY = 7;
  private static final byte LDX_YYY = 8;
  private static final byte LDX_IYYY = 9;
  private static final byte LDX_YYYY = 10;
  private static final byte LDX_YCYYY = 11;
  private static final byte LDX_SYYYY = 12;
  private static final byte LDX_SYCYYY = 13;
  private static final byte LDX_YEAR = 14;
  private static final byte LDX_SYEAR = 15;
  private static final byte LDX_Q = 16;
  private static final byte LDX_MM = 17;
  private static final byte LDX_IW = 18;
  private static final byte LDX_WW = 19;
  private static final byte LDX_W = 20;
  private static final byte LDX_D = 21;
  private static final byte LDX_DD = 22;
  private static final byte LDX_DDD = 23;
  private static final byte LDX_HH24 = 24;
  private static final byte LDX_HH = 25;
  private static final byte LDX_MI = 26;
  private static final byte LDX_SS = 27;
  private static final byte LDX_SSSSS = 28;
  private static final byte LDX_J = 29;
  private static final byte LDX_MONTH = 30;
  private static final byte LDX_MON = 31;
  private static final byte LDX_DAY = 32;
  private static final byte LDX_DY = 33;
  private static final byte LDX_AMPM = 34;
  private static final byte LDX_A_M_P_M = 35;
  private static final byte LDX_BCAD = 36;
  private static final byte LDX_B_C_A_D = 37;
  private static final byte LDX_RM = 38;
  private static final byte LDX_FM = 39;
  private static final byte LDX_RR = 40;
  private static final byte LDX_RRRR = 41;
  private static final byte LDX_FX = 42;
  private static final byte LDX_E = 43;
  private static final byte LDX_EE = 44;
  private static final byte LDX_LIT = 45;
  private static final byte LDX_JUS = 16;
  private static final byte LDX_NTH = 1;
  private static final byte LDX_SPL = 2;
  private static final byte LDX_CAP = 4;
  private static final byte LDX_UPR = 8;
  private static final byte LDX_QUO = 1;
  private static final byte LDX_SPA = 2;
  private static final byte LDX_PUN = 4;
  private static final byte LDX_ALPHA = -128;
  private static final byte LDXFNJUS = 64;
  private static final byte LDX_NEG = 32;
  private static final byte LDX_COMMA = 16;
  private static final byte LDX_LEN = 15;
  private static final byte LDXFL_NOT = 0;
  private static final byte LDXFL_FLEX = 1;
  private static final byte LDXFL_STD = 2;
  private static final byte LDXFL_MDONE = 4;
  private static final byte LDXFL_YDONE = 8;
  private static final byte LDXFL_PUNC = 16;
  private static final byte LDXFL_MSEC = 32;
  private static final int LDXSBUFFERSIZE = 64;
  private static final int LDXWBUFSIZE = 64;
  private static final int LDXTCE = 0;
  private static final int LDXTYE = 1;
  private static final int LDXTMO = 2;
  private static final int LDXTDA = 3;
  private static final int LDXTHO = 4;
  private static final int LDXTMI = 5;
  private static final int LDXTSO = 6;
  private static final int LDXTSIZ = 7;
  private static final int LDX_SUNDAY = 2445029;
  private static final int LDXPMXYR = 9999;
  private static final int LDXPMNYR = -4712;
  private static final char[][] ldxfda = { { 'A', '.', 'D', '.' }, { 'A', '.', 'M', '.' }, { 'A', 'D' }, { 'A', 'M' }, { 'B', '.', 'C', '.' }, { 'B', 'C' }, { 'C', 'C' }, { 'D' }, { 'D', 'A', 'Y' }, { 'D', 'D' }, { 'D', 'D', 'D' }, { 'D', 'Y' }, { 'E' }, { 'E', 'E' }, { 'F', 'M' }, { 'F', 'X' }, { 'H', 'H' }, { 'H', 'H', '1', '2' }, { 'H', 'H', '2', '4' }, { 'I' }, { 'I', 'W' }, { 'I', 'Y' }, { 'I', 'Y', 'Y' }, { 'I', 'Y', 'Y', 'Y' }, { 'J' }, { 'M', 'I' }, { 'M', 'M' }, { 'M', 'O', 'N' }, { 'M', 'O', 'N', 'T', 'H' }, { 'P', '.', 'M', '.' }, { 'P', 'M' }, { 'Q' }, { 'R', 'M' }, { 'R', 'R' }, { 'R', 'R', 'R', 'R' }, { 'S', 'C', 'C' }, { 'S', 'S' }, { 'S', 'S', 'S', 'S', 'S' }, { 'S', 'Y', ',', 'Y', 'Y', 'Y' }, { 'S', 'Y', 'E', 'A', 'R' }, { 'S', 'Y', 'Y', 'Y', 'Y' }, { 'W' }, { 'W', 'W' }, { 'Y' }, { 'Y', ',', 'Y', 'Y', 'Y' }, { 'Y', 'E', 'A', 'R' }, { 'Y', 'Y' }, { 'Y', 'Y', 'Y' }, { 'Y', 'Y', 'Y', 'Y' }, { '\000' } };

  private static final byte[] ldxfdc = { 37, 35, 36, 34, 37, 36, 1, 21, 32, 22, 23, 33, 43, 44, 39, 42, 25, 25, 24, 3, 18, 5, 7, 9, 29, 26, 17, 31, 30, 35, 34, 16, 38, 40, 41, 2, 27, 28, 13, 15, 12, 20, 19, 4, 11, 14, 6, 8, 10, 0 };

  private static final byte[] ldxfcdlen = { 0, 2, 35, 1, 1, 2, 2, 3, 3, 4, 4, 21, 37, 54, -60, -27, 1, 2, 2, 2, 1, 1, 2, 3, 2, 2, 2, 2, 5, 7, -128, -128, -128, -128, -62, -60, -62, -60, -124, 0, 2, 4, 0, -113, -98, -128, -128 };

  private static int[] ldxfdi = { 0, 4, 6, 7, 12, 14, -2147483648, 16, 19, 24, -2147483648, -2147483648, 25, -2147483648, -2147483648, 29, 31, 32, 35, -2147483648, -2147483648, -2147483648, 41, -2147483648, 43, -2147483648 };

  private static final char[][] ldxfdx = { { 'S', 'P' }, { 'S', 'P', 'T', 'H' }, { 'T', 'H' }, { 'T', 'H', 'S', 'P' }, { '\000' } };

  private static final byte[] ldxfdxc = { 2, 3, 1, 3, 0 };

  private static final byte[] NULLFMT = { 0, 16 };

  private static final byte[] DEFAULT_FORMAT = { 22, 24, 46, 4, 47, 31, 24, 46, 4, 47, 10, 24 };

  private static final String[] ldxpaa = { "A.D.", "A.M.", "B.C.", "P.M." };

  private static final int[] ldxdom = { 0, 0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334, 365 };

  private final byte[][] ldxpmxa = { { 23, 29 }, { 4, 6, 8, 10, 12, 11, 13 }, { 25, 24 }, { 34, 35 }, { 36, 37 }, { 30, 31, 17, 38 }, { 32, 33, 21 }, { 34, 35, 24 }, { 12, 13, 36, 37 } };

  public byte[] ldxadm(byte[] paramArrayOfByte, int paramInt)
    throws SQLException
  {
    int j = ((paramArrayOfByte[0] & 0xFF) - 100) * 100 + ((paramArrayOfByte[1] & 0xFF) - 100);

    int i = j;

    int k = paramArrayOfByte[2] & 0xFF;

    int m = paramArrayOfByte[3] & 0xFF;

    paramInt += (paramArrayOfByte[2] & 0xFF) + i * 12;

    if (paramInt > 0)
    {
      if ((i = paramInt / 12) > 9999) {
        throw new SQLException(CoreException.getMessage((byte)8));
      }
      if (paramInt %= 12 == 0)
      {
        i--;
        paramInt = 12;
      }
    }
    else
    {
      if ((i = paramInt / 12 - 1) < -4712) {
        throw new SQLException(CoreException.getMessage((byte)8));
      }
      paramInt = paramInt % 12 + 12;
    }

    int i1 = (paramInt == 2) && (ldxisl(j)) ? 29 : ldxdom[(k + 1)] - ldxdom[k];

    paramArrayOfByte[0] = ((byte)(i / 100 + 100));
    paramArrayOfByte[1] = ((byte)(i % 100 + 100));

    paramArrayOfByte[2] = ((byte)paramInt);
    int n = (paramInt == 2) && (ldxisl(i)) ? 29 : ldxdom[(paramInt + 1)] - ldxdom[paramInt];

    paramArrayOfByte[3] = ((byte)((m == i1) || (m > n) ? n : m));

    return paramArrayOfByte;
  }

  public byte[] ldxads(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws SQLException
  {
    if (paramInt2 != 0)
    {
      paramInt2 += ((paramArrayOfByte[4] - 1) * 60 + (paramArrayOfByte[5] - 1)) * 60 + (paramArrayOfByte[6] - 1);
      paramInt1 += paramInt2 / 86400;

      if (paramInt2 %= 86400 < 0)
      {
        paramInt2 += 86400;
        paramInt1--;
      }

      paramArrayOfByte[4] = ((byte)(paramInt2 / 3600 + 1));
      paramArrayOfByte[5] = ((byte)(paramInt2 % 3600 / 60 + 1));
      paramArrayOfByte[6] = ((byte)(paramInt2 % 3600 % 60 + 1));
    }

    int i = ((paramArrayOfByte[0] & 0xFF) - 100) * 100 + ((paramArrayOfByte[1] & 0xFF) - 100);

    if (paramInt1 != 0)
    {
      paramInt1 += ldxctj(i, paramArrayOfByte[2], paramArrayOfByte[3]);

      if (paramInt1 < 1) {
        throw new SQLException(CoreException.getMessage((byte)8));
      }

      paramArrayOfByte = ldxjtc(paramInt1, paramArrayOfByte);
    }

    i = ((paramArrayOfByte[0] & 0xFF) - 100) * 100 + ((paramArrayOfByte[1] & 0xFF) - 100);

    if (i > 9999) {
      throw new SQLException(CoreException.getMessage((byte)8));
    }

    return paramArrayOfByte;
  }

  public int ldxchk(byte[] paramArrayOfByte)
    throws SQLException
  {
    throw new SQLException(CoreException.getMessage((byte)1));
  }

  public byte[] ldxdfd(int paramInt1, int paramInt2)
    throws SQLException
  {
    throw new SQLException(CoreException.getMessage((byte)1));
  }

  public void ldxdtd(byte[] paramArrayOfByte, int[] paramArrayOfInt1, int[] paramArrayOfInt2)
    throws SQLException
  {
    throw new SQLException(CoreException.getMessage((byte)1));
  }

  public String ldxdts(byte[] paramArrayOfByte, String paramString1, String paramString2)
    throws SQLException
  {
    return ldxdts(paramArrayOfByte, ldxsto(paramString1, paramString2), paramString2);
  }

  public String ldxdts(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, String paramString)
    throws SQLException
  {
    int i = 0;

    int k = 0;
    int m = 0;

    int i1 = 0;

    int i3 = 0;
    int i4 = 0;
    String str1 = null;

    StringBuffer localStringBuffer1 = new StringBuffer(64);

    StringBuffer localStringBuffer2 = null;

    if ((paramArrayOfByte2.length == 0) || (paramArrayOfByte2 == null) || ((paramArrayOfByte2[0] == 0) && (paramArrayOfByte2[1] == 16)))
    {
      paramArrayOfByte2 = DEFAULT_FORMAT;
    }

    int n = paramArrayOfByte2.length;
    Locale localLocale;
    if ((paramString != null) && (paramString.compareTo("") != 0))
    {
      i4 = paramString.indexOf("_");
      if (i4 == -1)
      {
        localLocale = LxMetaData.getJavaLocale(paramString, "");
      }
      else
      {
        String str2 = paramString.substring(0, i4);
        String str3 = paramString.substring(i4 + 1);
        localLocale = LxMetaData.getJavaLocale(str2, str3);
      }
      if (localLocale == null)
      {
        localLocale = Locale.getDefault();
      }
    }
    else
    {
      localLocale = Locale.getDefault();
    }

    i3 = ((paramArrayOfByte1[0] & 0xFF) - 100) * 100 + ((paramArrayOfByte1[1] & 0xFF) - 100);

    while (m < n)
    {
      i = paramArrayOfByte2[(m++)];
      k = paramArrayOfByte2[(m++)];

      if (i == 0)
      {
        break;
      }
      if (i >= 45)
      {
        int i2 = i - 45;

        localStringBuffer1.append(new String(paramArrayOfByte2, m, i2));

        m += i2;
      }
      else
      {
        int j = ldxfcdlen[i];
        StringBuffer localStringBuffer3;
        if ((((j & 0xFFFFFF80) != 0) && ((k & 0xC) != 0)) || (((k & 0x10) != 0) && ((j & 0x40) == 0)) || (((k & 0xC) != 0) && ((k & 0x3) != 0)))
        {
          localStringBuffer2 = new StringBuffer(64);
          localStringBuffer3 = localStringBuffer2;
        }
        else {
          localStringBuffer3 = localStringBuffer1;
        }
        int i5;
        int i6;
        switch (i)
        {
        case 37:
          if ((localLocale.getCountry().compareTo("US") == 0) && (localLocale.getLanguage().compareTo("en") == 0))
          {
            str1 = paramArrayOfByte1[0] < 100 ? ldxpaa[2] : ldxpaa[0];
          }
          else
            i--;
        case 36:
          localStringBuffer3.append(new DateFormatSymbols(localLocale).getEras()[1]);

          i5 = localStringBuffer3.length();
          break;
        case 35:
          if ((localLocale.getCountry().compareTo("US") == 0) && (localLocale.getLanguage().compareTo("en") == 0))
          {
            str1 = paramArrayOfByte1[4] - 1 >= 12 ? ldxpaa[3] : ldxpaa[1];
          }
          else
            i--;
        case 34:
          localStringBuffer3.append(new DateFormatSymbols(localLocale).getAmPmStrings()[0]);

          i5 = localStringBuffer3.length();
          break;
        case 29:
          i4 = ldxctj(i3, paramArrayOfByte1[2], paramArrayOfByte1[3]);
          break;
        case 21:
          i4 = ldxdow(i3, paramArrayOfByte1[2], paramArrayOfByte1[3], localLocale);
          break;
        case 32:
        case 33:
          i6 = ldxdow(i3, paramArrayOfByte1[2], paramArrayOfByte1[3], localLocale);

          Calendar localCalendar = GregorianCalendar.getInstance(localLocale);
          if (localCalendar.getFirstDayOfWeek() > 1)
            i6++;
          if (i6 > 7)
            i6 -= 7;
          if (i6 == 0)
            i6++;
          String[] arrayOfString1;
          if (i == 32)
            arrayOfString1 = new DateFormatSymbols(localLocale).getWeekdays();
          else {
            arrayOfString1 = new DateFormatSymbols(localLocale).getShortWeekdays();
          }
          localStringBuffer3.append(arrayOfString1[i6]);
          i5 = localStringBuffer3.length();
          break;
        case 1:
        case 2:
          if ((i4 = i3) > 0)
            i4 = (i4 - 1) / 100 + 1;
          else
            i4 = -((-1 - i4) / 100) - 1;
          break;
        case 22:
          i4 = paramArrayOfByte1[3];
          break;
        case 43:
        case 44:
          throw new SQLException(CoreException.getMessage((byte)1));
        case 24:
        case 25:
          i4 = paramArrayOfByte1[4] - 1;
          if (i == 25)
            i4 = i4 == 0 ? 12 : i4 > 12 ? i4 - 12 : i4; break;
        case 26:
          i4 = paramArrayOfByte1[5] - 1;
          break;
        case 16:
          i4 = (paramArrayOfByte1[2] + 2) / 3;
          break;
        case 17:
          i4 = paramArrayOfByte1[2];
          break;
        case 30:
        case 31:
          i4 = paramArrayOfByte1[2];
          String[] arrayOfString2;
          if (i == 30)
            arrayOfString2 = new DateFormatSymbols(localLocale).getMonths();
          else {
            arrayOfString2 = new DateFormatSymbols(localLocale).getShortMonths();
          }

          localStringBuffer3.append(arrayOfString2[(--i4)]);
          i5 = localStringBuffer3.length();
          break;
        case 38:
          throw new SQLException(CoreException.getMessage((byte)1));
        case 27:
          i4 = paramArrayOfByte1[6] - 1;
          break;
        case 28:
          i4 = ((paramArrayOfByte1[4] - 1) * 60 + (paramArrayOfByte1[5] - 1)) * 60 + (paramArrayOfByte1[6] - 1);

          break;
        case 20:
          i4 = (paramArrayOfByte1[3] + 6) / 7;
          break;
        case 23:
          i4 = ldxcty(i3, paramArrayOfByte1[2], paramArrayOfByte1[3]);
          break;
        case 18:
          throw new SQLException(CoreException.getMessage((byte)1));
        case 19:
          i4 = (ldxcty(i3, paramArrayOfByte1[2], paramArrayOfByte1[3]) + 6) / 7;
          break;
        case 3:
          throw new SQLException(CoreException.getMessage((byte)1));
        case 4:
          i4 = i3 % 10;
          break;
        case 5:
          throw new SQLException(CoreException.getMessage((byte)1));
        case 6:
        case 40:
          i4 = i3 % 100;
          break;
        case 7:
          throw new SQLException(CoreException.getMessage((byte)1));
        case 8:
          i4 = i3 % 1000;
          break;
        case 10:
        case 11:
        case 12:
        case 13:
        case 41:
          i4 = i3;
          break;
        case 9:
          throw new SQLException(CoreException.getMessage((byte)1));
        case 14:
        case 15:
          throw new SQLException(CoreException.getMessage((byte)1));
        case 42:
          break;
        case 39:
        default:
          throw new SQLException(CoreException.getMessage((byte)7));

          if ((j & 0xFFFFFF80) == 0)
          {
            if ((j & 0x20) == 0)
            {
              if (i4 < 0) {
                i4 = -i4;
              }

            }
            else if (i4 >= 0)
            {
              localStringBuffer3.insert(0, " ");
              j = (byte)(j - 1);
            }
            if ((k & 0x2) != 0)
            {
              throw new SQLException(CoreException.getMessage((byte)1));
            }

            if ((k & 0x10) != 0)
              i5 = j & 0xF;
            else {
              i5 = 0;
            }

            String str4 = lxi42b(64, i4, i5, (j & 0x10) != 0, localLocale);

            localStringBuffer3.append(str4);

            if ((k & 0x1) != 0)
            {
              throw new SQLException(CoreException.getMessage((byte)1));
            }

          }
          else if (((k & 0x10) != 0) && ((j & 0x40) == 0))
          {
            String[] arrayOfString3;
            switch (i)
            {
            case 33:
              arrayOfString3 = new DateFormatSymbols(localLocale).getShortWeekdays();
              break;
            case 32:
              arrayOfString3 = new DateFormatSymbols(localLocale).getWeekdays();
              break;
            case 31:
              arrayOfString3 = new DateFormatSymbols(localLocale).getShortMonths();
              break;
            case 30:
              arrayOfString3 = new DateFormatSymbols(localLocale).getMonths();
              break;
            default:
              throw new SQLException(CoreException.getMessage((byte)7));
            }

            i6 = 0;

            for (int i7 = 0; i7 < arrayOfString3.length; i7++)
            {
              i4 = arrayOfString3[i7].length();
              if (i4 > i6) {
                i6 = i4;
              }
            }

            i6 -= localStringBuffer3.length();
            for (i7 = 0; i7 < i6; i7++) {
              localStringBuffer3.append(" ");
            }
          }

          if (str1 != null)
          {
            i5 = 4;

            if ((k & 0xC) == 0)
              localStringBuffer3 = localStringBuffer1;
            localStringBuffer3.append(str1.toLowerCase(localLocale));
            str1 = null;
          }

          if ((k & 0x4) != 0)
          {
            if (Character.isLowerCase(localStringBuffer3.charAt(0))) {
              localStringBuffer3.setCharAt(0, Character.toUpperCase(localStringBuffer3.charAt(0)));
            }
            localStringBuffer1.append(localStringBuffer3.toString());
          }
          else if ((k & 0x8) != 0)
          {
            localStringBuffer1.append(localStringBuffer3.toString().toUpperCase());
          }
          else if (localStringBuffer3 != localStringBuffer1)
          {
            localStringBuffer1.append(localStringBuffer3.toString());
          }
          break;
        }
      }
    }
    return localStringBuffer1.toString();
  }

  private int ldxdow(int paramInt1, int paramInt2, int paramInt3, Locale paramLocale)
  {
    Calendar localCalendar = GregorianCalendar.getInstance(paramLocale);

    int i = ldxctj(paramInt1, paramInt2, paramInt3);

    int j = (i - (2445029 + (localCalendar.getFirstDayOfWeek() - 1))) % 7;
    if (j < 0)
      j += 7;
    return j + 1;
  }

  private int ldxctj(int paramInt1, int paramInt2, int paramInt3)
  {
    int i;
    if (paramInt1 == -4712) {
      i = 0;
    }
    else {
      i = paramInt1 + 4712;

      i = 365 * i + (i - 1) / 4;
    }

    if (paramInt1 >= 1583) {
      i = i - 10 - (paramInt1 - 1501) / 100 + (paramInt1 - 1201) / 400;
    }

    i += ldxcty(paramInt1, paramInt2, paramInt3);

    if ((paramInt1 == 1582) && (((paramInt2 == 10) && (paramInt3 >= 15)) || (paramInt2 >= 11))) {
      i -= 10;
    }
    return i;
  }

  private byte[] ldxjtc(int paramInt, byte[] paramArrayOfByte)
    throws SQLException
  {
    if (paramInt < 1) {
      throw new SQLException(CoreException.getMessage((byte)10));
    }
    if (paramInt < 366)
    {
      paramArrayOfByte[0] = 53;
      paramArrayOfByte[1] = 28;

      paramArrayOfByte = ldxdyc(-4712, paramInt, paramArrayOfByte);
    }
    else
    {
      int k;
      int i;
      int j;
      if (paramInt < 2299161)
      {
        paramInt -= 366;

        k = -4711 + paramInt / 1461 * 4;
        paramInt %= 1461;

        i = paramInt / 365;
        j = paramInt % 365;

        if ((j == 0) && (i == 4))
        {
          j = 366;
          i = 3;
        }
        else {
          j++;
        }
        k += i;

        paramArrayOfByte = ldxdyc(k, j, paramArrayOfByte);

        paramArrayOfByte[0] = ((byte)(k / 100 + 100));
        paramArrayOfByte[1] = ((byte)(k % 100 + 100));
      }
      else
      {
        paramInt = 4 * (paramInt - 1721119) - 1;
        k = paramInt / 146097;
        paramInt %= 146097;

        j = paramInt / 4;

        paramInt = 4 * j + 3;
        j = paramInt % 1461;
        paramInt /= 1461;

        j /= 4;
        j++;

        i = 5 * j - 3;
        j = i % 153;
        i /= 153;

        j /= 5;
        j++;

        k *= 100;
        k += paramInt;

        if (i < 10) {
          i += 3;
        }
        else {
          i -= 9;
          k++;
        }

        paramArrayOfByte[3] = ((byte)j);
        paramArrayOfByte[2] = ((byte)i);
        paramArrayOfByte[0] = ((byte)(k / 100 + 100));
        paramArrayOfByte[1] = ((byte)(k % 100 + 100));
      }
    }

    return paramArrayOfByte;
  }

  private byte[] ldxdyc(int paramInt1, int paramInt2, byte[] paramArrayOfByte)
    throws SQLException
  {
    boolean bool = ldxisl(paramInt1);

    if ((paramInt2 == 366) && (!bool)) {
      throw new SQLException(CoreException.getMessage((byte)9));
    }

    int i = paramInt2;

    if (paramInt2 > 59 + (bool ? 1 : 0)) {
      i += 2 - (bool ? 1 : 0);
    }
    i += 91; int j = i * 100;
    paramArrayOfByte[3] = ((byte)(i - (j - j % 3055) / 100));
    paramArrayOfByte[2] = ((byte)(j / 3055 - 2));

    return paramArrayOfByte;
  }

  private int ldxcty(int paramInt1, int paramInt2, int paramInt3)
  {
    return ldxdom[paramInt2] + paramInt3 + ((paramInt2 >= 3) && (ldxisl(paramInt1)) ? 1 : 0);
  }

  private boolean ldxisl(int paramInt)
  {
    return (paramInt % 4 == 0) && (paramInt <= 1582 ? paramInt == -4712 : (paramInt % 100 != 0) || (paramInt % 400 == 0));
  }

  private String lxi42b(int paramInt1, long paramLong, int paramInt2, boolean paramBoolean, Locale paramLocale)
    throws SQLException
  {
    int i = 0;
    int j = 0;
    long l = paramLong;
    NumberFormat localNumberFormat = NumberFormat.getInstance(paramLocale);
    DecimalFormat localDecimalFormat = (DecimalFormat)localNumberFormat;
    StringBuffer localStringBuffer = new StringBuffer();
    int k = localDecimalFormat.getGroupingSize();
    char c1 = '\000';
    char c2 = localDecimalFormat.getDecimalFormatSymbols().getZeroDigit();

    for (j = 1; l /= 10L != 0L; j++);
    if (paramLong < 0L) {
      j++;
    }
    if (paramBoolean) {
      j += (j - 1) / k;
    }

    if (!paramBoolean) {
      localNumberFormat.setGroupingUsed(false);
    }
    localStringBuffer.append(localDecimalFormat.format(paramLong));

    if ((j > paramInt1) || (paramInt2 > paramInt1) || ((paramInt2 != 0) && (j > paramInt2)))
    {
      throw new SQLException(CoreException.getMessage((byte)3));
    }

    if (paramBoolean) {
      c1 = localDecimalFormat.getDecimalFormatSymbols().getGroupingSeparator();
    }
    if (paramInt2 != 0)
    {
      for (paramInt2 -= j; paramInt2-- != 0; )
      {
        if ((paramBoolean) && (i++ == k) && (paramInt2 != 0))
        {
          localStringBuffer.insert(0, c1);
          i = 1;
          paramInt2--;
        }
        localStringBuffer.insert(0, c2);
      }
    }

    return localStringBuffer.toString();
  }

  public byte[] ldxsto(String paramString1, String paramString2)
    throws SQLException
  {
    int i = 0;
    int j = 0;

    int m = 0;
    byte[] arrayOfByte1 = new byte[512];
    int n = 16;
    int i1 = 0;
    int i2 = 0;
    char[] arrayOfChar = new char[256];
    int i3 = 0;

    int i4 = 0;

    if ((paramString1 == null) || (paramString1.compareTo("") == 0))
    {
      return NULLFMT;
    }

    int k = paramString1.length();

    while (i < k)
    {
      n = 16;
      i2 = 0;
      do
      {
        if ((i < k) && (paramString1.charAt(i) == '|'))
        {
          i++;
          break;
        }

        for (i3 = 0; (i < k) && (!Character.isLetterOrDigit(paramString1.charAt(i))); )
        {
          if (paramString1.charAt(i) == '"')
          {
            i1 = 1;

            while ((i != k) && (paramString1.charAt(++i) != '"'))
            {
              arrayOfChar[(i2++)] = paramString1.charAt(i);
              i3++;
            }
            if (paramString1.charAt(i) == '"')
              i++;
          }
          else
          {
            arrayOfChar[(i2++)] = paramString1.charAt(i++);
            i3++;
          }
        }
        int i5;
        if (i3 > 0)
        {
          if (i3 > 210)
          {
            throw new SQLException(CoreException.getMessage((byte)7));
          }

          if (Character.isWhitespace(arrayOfChar[0]))
          {
            int i10 = 0;

            i5 = i3; int i8 = 0;

            for (; (i5 > 0) && (Character.isWhitespace(arrayOfChar[i10])); 
              i5--) { i10++; i8++; }
            arrayOfByte1[(j++)] = ((byte)(45 + i8));

            arrayOfByte1[(j++)] = 2;

            byte[] arrayOfByte4 = new String(arrayOfChar, 0, i8).getBytes();
            System.arraycopy(arrayOfByte4, 0, arrayOfByte1, j, arrayOfByte4.length);

            j += arrayOfByte4.length;

            i3 -= i8;
            if (i3 == 0)
            {
              continue;
            }

            i += i8 + 1;

            i2 = i10;
          }
          else {
            i2 = 0;
          }
          if (i1 != 1) {
            i1 = 4;
          }
          arrayOfByte1[(j++)] = ((byte)(45 + i3));
          arrayOfByte1[(j++)] = i1;

          byte[] arrayOfByte2 = new String(arrayOfChar, 0, i3).getBytes();
          System.arraycopy(arrayOfByte2, 0, arrayOfByte1, j, arrayOfByte2.length);

          j += arrayOfByte2.length;
        }
        else
        {
          if (!Character.isLetterOrDigit(paramString1.charAt(i)))
          {
            throw new SQLException(CoreException.getMessage((byte)7));
          }

          char c = Character.toUpperCase(paramString1.charAt(i));
          i5 = c - 'A';
          if ((i5 > 25) || (ldxfdi[i5] == -2147483648))
          {
            throw new SQLException(CoreException.getMessage((byte)7));
          }

          i4 = ldxfdi[i5];

          i5 = 50;
          int i9;
          int i6;
          int i7;
          for (; i4 < ldxfda.length; i4++)
          {
            i9 = ldxfda[i4].length;
            i6 = 0; for (i7 = i; 
              (i6 < i9) && (i7 < k); i7++)
            {
              if (Character.toUpperCase(paramString1.charAt(i7)) != ldxfda[i4][i6])
                break;
              i6++;
            }

            if (i6 == i9)
            {
              i5 = i4;
            }

            if (ldxfda[(i4 + 1)][0] != c) {
              break;
            }
          }
          i4 = i5;

          if (i4 >= ldxfda.length)
          {
            throw new SQLException(CoreException.getMessage((byte)7));
          }

          if (k - i > 1)
          {
            if (Character.isUpperCase(paramString1.charAt(i)))
            {
              c = Character.isLetterOrDigit(paramString1.charAt(i + 1)) ? paramString1.charAt(i + 1) : paramString1.charAt(i + 2);

              if (Character.isLowerCase(c))
                n = (byte)(n | 0x4);
              else {
                n = (byte)(n | 0x8);
              }
            }
          }
          i += ldxfda[i4].length;
          m = ldxfdc[i4];

          if ((ldxfcdlen[m] & 0xFFFFFF80) == 0)
          {
            i4 = 0; for (i6 = -1; i4 < ldxfdx.length; i4++)
            {
              i9 = ldxfdx[i4].length;
              i5 = 0; for (i7 = i; 
                (i5 < i9) && (i7 < k); i7++)
              {
                if (Character.toUpperCase(paramString1.charAt(i7)) != ldxfdx[i4][i5])
                  break;
                i5++;
              }

              if (i5 == i9)
              {
                i6 = i4;
              }
            }

            i4 = i6;

            if ((i4 >= 0) && (i4 < ldxfdx.length))
            {
              n = (byte)(n | ldxfdxc[i4]);
              i += ldxfdx[i4].length;
            }

          }

          if (512 - j < 2)
          {
            throw new SQLException(CoreException.getMessage((byte)7));
          }

          arrayOfByte1[(j++)] = ((byte)m);
          arrayOfByte1[(j++)] = n;
        }

        if (m == 39)
          n = (n & 0x10) == 1 ? 0 : 16;
      }
      while (m == 39);
    }

    byte[] arrayOfByte3 = new byte[j];
    System.arraycopy(arrayOfByte1, 0, arrayOfByte3, 0, arrayOfByte3.length);
    return arrayOfByte3;
  }

  public byte[] ldxdyf(byte[] paramArrayOfByte)
    throws SQLException
  {
    throw new SQLException(CoreException.getMessage((byte)1));
  }

  public void ldxftd(byte[] paramArrayOfByte, int[] paramArrayOfInt1, int[] paramArrayOfInt2)
    throws SQLException
  {
    throw new SQLException(CoreException.getMessage((byte)1));
  }

  public byte[] ldxgdt()
    throws SQLException
  {
    throw new SQLException(CoreException.getMessage((byte)1));
  }

  public byte[] ldxldd(byte[] paramArrayOfByte)
    throws SQLException
  {
    throw new SQLException(CoreException.getMessage((byte)1));
  }

  public byte[] ldxnxd(byte[] paramArrayOfByte, int paramInt)
    throws SQLException
  {
    throw new SQLException(CoreException.getMessage((byte)1));
  }

  public byte[] ldxrnd(byte[] paramArrayOfByte, String paramString)
    throws SQLException
  {
    throw new SQLException(CoreException.getMessage((byte)1));
  }

  public byte[] ldxsbm(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
    throws SQLException
  {
    throw new SQLException(CoreException.getMessage((byte)1));
  }

  public void ldxsub(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int[] paramArrayOfInt1, int[] paramArrayOfInt2)
    throws SQLException
  {
    throw new SQLException(CoreException.getMessage((byte)1));
  }

  public byte[] ldxstd(String paramString1, String paramString2, String paramString3)
    throws SQLException
  {
    Object localObject = null;
    byte[] arrayOfByte = null;
    int i = 0;
    int j = 0;
    char[] arrayOfChar = new char[512];
    int k = 0;
    int m = 0;
    int n = 0;
    int i1 = 0;
    ParsePosition localParsePosition = new ParsePosition(0);
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat();

    arrayOfByte = ldxsto(paramString2, paramString3);

    ldxsti(arrayOfByte);

    m = arrayOfByte.length;

    while (k < m)
    {
      i = arrayOfByte[(k++)];
      j = arrayOfByte[(k++)];
      int i2;
      switch (i)
      {
      case 43:
      case 44:
        throw new SQLException(CoreException.getMessage((byte)1));
      case 41:
        throw new SQLException(CoreException.getMessage((byte)1));
      case 40:
        throw new SQLException(CoreException.getMessage((byte)1));
      case 4:
        arrayOfChar[(n++)] = 'y';
        break;
      case 6:
        for (i2 = 0; i2 < 2; i2++)
          arrayOfChar[(n++)] = 'y';
        break;
      case 8:
        for (i2 = 0; i2 < 3; i2++)
          arrayOfChar[(n++)] = 'y';
        break;
      case 10:
        for (i2 = 0; i2 < 4; i2++)
          arrayOfChar[(n++)] = 'y';
        break;
      case 11:
      case 12:
      case 13:
        throw new SQLException(CoreException.getMessage((byte)1));
      case 38:
        throw new SQLException(CoreException.getMessage((byte)1));
      case 17:
        arrayOfChar[(n++)] = 'M';
        arrayOfChar[(n++)] = 'M';
        break;
      case 31:
        for (i2 = 0; i2 < 3; i2++)
          arrayOfChar[(n++)] = 'M';
        break;
      case 30:
        for (i2 = 0; i2 < 4; i2++)
          arrayOfChar[(n++)] = 'M';
        break;
      case 21:
      case 33:
        arrayOfChar[(n++)] = 'E';
        break;
      case 32:
        for (i2 = 0; i2 < 4; i2++)
          arrayOfChar[(n++)] = 'E';
        break;
      case 22:
        arrayOfChar[(n++)] = 'd';
        break;
      case 23:
        arrayOfChar[(n++)] = 'D';
        break;
      case 29:
        throw new SQLException(CoreException.getMessage((byte)1));
      case 25:
        arrayOfChar[(n++)] = 'h';
        break;
      case 24:
        arrayOfChar[(n++)] = 'H';
        break;
      case 26:
        arrayOfChar[(n++)] = 'm';
        break;
      case 27:
        arrayOfChar[(n++)] = 's';
        break;
      case 28:
        throw new SQLException(CoreException.getMessage((byte)1));
      case 34:
      case 35:
        arrayOfChar[(n++)] = 'a';
        break;
      case 36:
      case 37:
        arrayOfChar[(n++)] = 'G';
        break;
      case 39:
      case 42:
        throw new SQLException(CoreException.getMessage((byte)1));
      case 5:
      case 7:
      case 9:
      case 14:
      case 15:
      case 16:
      case 18:
      case 19:
      case 20:
      default:
        i1 = i - 45;
        str = new String(arrayOfByte, k, i1);

        if (j == 1)
        {
          arrayOfChar[(n++)] = '\'';
          System.arraycopy(str.toCharArray(), 0, arrayOfChar, n, i1);

          n += i1;
          k += i1;
          arrayOfChar[(n++)] = '\'';
        }
        else
        {
          System.arraycopy(str.toCharArray(), 0, arrayOfChar, n, i1);

          n += i1;
          k += i1;
        }

        break;
      }

    }

    String str = new String(arrayOfChar, 0, n);
    localSimpleDateFormat.applyPattern(str);
    localSimpleDateFormat.setLenient(false);
    Date localDate = localSimpleDateFormat.parse(paramString1, localParsePosition);
    if (localDate != null)
    {
      return DATE.toBytes(new Timestamp(localDate.getTime()));
    }

    throw new SQLException(CoreException.getMessage((byte)6));
  }

  public byte[] ldxtrn(byte[] paramArrayOfByte, String paramString)
    throws SQLException
  {
    throw new SQLException(CoreException.getMessage((byte)1));
  }

  private void ldxsti(byte[] paramArrayOfByte)
    throws SQLException
  {
    int[] arrayOfInt = new int[46];
    int j;
    for (int i = 0; i < paramArrayOfByte.length; i += 2)
    {
      if (paramArrayOfByte[i] < 45)
      {
        j = paramArrayOfByte[i];

        if ((paramArrayOfByte[i] != 42) && (paramArrayOfByte[i] != 39) && (arrayOfInt[paramArrayOfByte[i]] != 0))
        {
          throw new SQLException(CoreException.getMessage((byte)7));
        }

        arrayOfInt[paramArrayOfByte[i]] += 1;

        switch (paramArrayOfByte[i])
        {
        case 1:
        case 2:
        case 3:
        case 5:
        case 7:
        case 9:
        case 14:
        case 15:
        case 16:
        case 18:
        case 19:
        case 20:
          throw new SQLException(CoreException.getMessage((byte)7));
        case 4:
        case 6:
        case 8:
        case 10:
        case 11:
        case 12:
        case 13:
        case 17: }  } else { i += paramArrayOfByte[i] - 45; }


    }

    for (i = 0; i < this.ldxpmxa.length; i++)
    {
      j = 0;

      for (int k = 0; k < this.ldxpmxa[i].length; k++) {
        j += arrayOfInt[this.ldxpmxa[i][k]];
      }
      if (j > 1)
      {
        throw new SQLException(CoreException.getMessage((byte)7));
      }
    }
  }
}