package oracle.jdbc.driver;

import java.math.BigDecimal;
import java.sql.SQLException;
import oracle.core.lmx.CoreException;

class BigDecimalBinder extends VarnumBinder
{
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  void bind(OraclePreparedStatement paramOraclePreparedStatement, int paramInt1, int paramInt2, int paramInt3, byte[] paramArrayOfByte, char[] paramArrayOfChar, short[] paramArrayOfShort, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, boolean paramBoolean)
    throws SQLException
  {
    byte[] arrayOfByte = paramArrayOfByte;
    int i = paramInt6 + 1;
    BigDecimal localBigDecimal1 = paramOraclePreparedStatement.parameterBigDecimal[paramInt3][paramInt1];
    int j = 0;

    String str1 = localBigDecimal1.toString();
    int k;
    int i5;
    int i8;
    int i9;
    int i12;
    int m = str1.length();
    int n = str1.indexOf('.');
    int i1 = str1.charAt(0) == '-' ? 1 : 0;
    int i2 = i1;

    int i6 = 2;
    int i7 = m;
    if ((k = str1.indexOf("E")) != -1)
    {
      StringBuffer localStringBuffer = new StringBuffer(str1.length() + 5);
      n = 0;
      BigDecimal localBigDecimal2 = null;
      i2 = str1.charAt(0) == '-' ? 1 : 0;
      String str2 = str1.substring(k + 1);
      String str3 = str1.substring(i2 != 0 ? 1 : 0, k);
      localBigDecimal2 = new BigDecimal(str3);
      i5 = str2.charAt(0) == '-' ? 1 : 0;
      str2 = str2.substring(1);
      n = Integer.parseInt(str2);

      String str4 = localBigDecimal2.toString();
      i7 = str4.indexOf(".");
      i8 = str4.length();
      i9 = i8;

      if (i7 != -1)
      {
        str4 = str4.substring(0, i7) + str4.substring(i7 + 1);

        i8--;
        if (i5 != 0)
        {
          n -= i7;
        }
        else {
          n++;
          i9 = n;
        }

      }
      else if (i5 != 0)
      {
        n -= i8;
      }
      else {
        n++;
        i9 = n;
      }

      if (i2 != 0)
        localStringBuffer.append("-");
      int i10;
      if (i5 != 0)
      {
        localStringBuffer.append("0.");
        for (i10 = 0; i10 < n; i10++)
        {
          localStringBuffer.append("0");
        }
        localStringBuffer.append(str4);
      }
      else
      {
        i10 = n > i8 ? n : i8;
        for (i12 = 0; i12 < i10; i12++)
        {
          if (i9 == i12)
            localStringBuffer.append(".");
          localStringBuffer.append(i8 > i12 ? str4.charAt(i12) : '0');
        }
      }

      str1 = localStringBuffer.toString();
    }

    if (n == -1)
      n = m;
    else if ((m - n & 0x1) != 0)
      i7 = m + 1;
    int i3;
    while ((i2 < m) && (((i3 = str1.charAt(i2)) < '1') || (i3 > 57))) {
      i2++;
    }
    if (i2 >= m)
    {
      arrayOfByte[i] = -128;
      j = 1;
    }
    else
    {
      int i4;
      if (i2 < n)
        i4 = 2 - (n - i2 & 0x1);
      else {
        i4 = 1 + (i2 - n & 0x1);
      }
      i5 = (n - i2 - 1) / 2;
      SQLException localSQLException;
      if (i5 > 62)
      {
        localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 1, CoreException.getMessage((byte)3) + " trying to bind " + localBigDecimal1);

        localSQLException.fillInStackTrace();
        throw localSQLException;
      }

      if (i5 < -65)
      {
        localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 1, CoreException.getMessage((byte)2) + " trying to bind " + localBigDecimal1);

        localSQLException.fillInStackTrace();
        throw localSQLException;
      }

      i8 = i2 + i4 + 38;

      if (i8 > m) {
        i8 = m;
      }
      for (i9 = i2 + i4; i9 < i8; i9 += 2)
      {
        if (i9 == n)
        {
          i9--;

          if (i8 < m) {
            i8++;
          }

        }
        else if ((str1.charAt(i9) != '0') || ((i9 + 1 < m) && (str1.charAt(i9 + 1) != '0')))
        {
          i6 = (i9 - i2 - i4) / 2 + 3;
        }
      }

      int i11 = i + 2;

      i9 = i2 + i4;

      if (i1 == 0)
      {
        arrayOfByte[i] = ((byte)(192 + i5 + 1));
        i12 = str1.charAt(i2) - '0';

        if (i4 == 2) {
          i12 = i12 * 10 + (i2 + 1 < m ? str1.charAt(i2 + 1) - '0' : 0);
        }

        arrayOfByte[(i + 1)] = ((byte)(i12 + 1));

        while (i11 < i + i6)
        {
          if (i9 == n) {
            i9++;
          }
          i12 = (str1.charAt(i9) - '0') * 10;

          if (i9 + 1 < m) {
            i12 += str1.charAt(i9 + 1) - '0';
          }
          arrayOfByte[(i11++)] = ((byte)(i12 + 1));
          i9 += 2;
        }

      }

      arrayOfByte[i] = ((byte)(62 - i5));

      i12 = str1.charAt(i2) - '0';

      if (i4 == 2) {
        i12 = i12 * 10 + (i2 + 1 < m ? str1.charAt(i2 + 1) - '0' : 0);
      }

      arrayOfByte[(i + 1)] = ((byte)(101 - i12));

      while (i11 < i + i6)
      {
        if (i9 == n) {
          i9++;
        }
        i12 = (str1.charAt(i9) - '0') * 10;

        if (i9 + 1 < m) {
          i12 += str1.charAt(i9 + 1) - '0';
        }
        arrayOfByte[(i11++)] = ((byte)(101 - i12));
        i9 += 2;
      }

      if (i6 < 21) {
        arrayOfByte[(i + i6++)] = 102;
      }

      j = i6;
    }

    arrayOfByte[paramInt6] = ((byte)j);
    paramArrayOfShort[paramInt9] = 0;
    paramArrayOfShort[paramInt8] = ((short)(j + 1));
  }
}