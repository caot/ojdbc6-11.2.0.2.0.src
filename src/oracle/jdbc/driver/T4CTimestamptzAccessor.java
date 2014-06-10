package oracle.jdbc.driver;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormatSymbols;
import java.util.Properties;
import java.util.TimeZone;

class T4CTimestamptzAccessor extends TimestamptzAccessor
{
  T4CMAREngine mare;
  boolean underlyingLongRaw = false;

  final int[] meta = new int[1];

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  T4CTimestamptzAccessor(OracleStatement paramOracleStatement, int paramInt1, short paramShort, int paramInt2, boolean paramBoolean, T4CMAREngine paramT4CMAREngine)
    throws SQLException
  {
    super(paramOracleStatement, paramInt1, paramShort, paramInt2, paramBoolean);

    this.mare = paramT4CMAREngine;
  }

  T4CTimestamptzAccessor(OracleStatement paramOracleStatement, int paramInt1, boolean paramBoolean, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, short paramShort, int paramInt7, int paramInt8, T4CMAREngine paramT4CMAREngine)
    throws SQLException
  {
    super(paramOracleStatement, paramInt1 == -1 ? paramInt8 : paramInt1, paramBoolean, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramShort);

    this.mare = paramT4CMAREngine;
    this.definedColumnType = paramInt7;
    this.definedColumnSize = paramInt8;

    if (paramInt1 == -1)
      this.underlyingLongRaw = true;
  }

  void processIndicator(int paramInt)
    throws IOException, SQLException
  {
    if (((this.internalType == 1) && (this.describeType == 112)) || ((this.internalType == 23) && (this.describeType == 113)))
    {
      this.mare.unmarshalUB2();
      this.mare.unmarshalUB2();
    }
    else if (this.statement.connection.versionNumber < 9200)
    {
      this.mare.unmarshalSB2();

      if ((this.statement.sqlKind != 32) && (this.statement.sqlKind != 64))
      {
        this.mare.unmarshalSB2();
      }
    } else if ((this.statement.sqlKind == 32) || (this.statement.sqlKind == 64) || (this.isDMLReturnedParam))
    {
      this.mare.processIndicator(paramInt <= 0, paramInt);
    }
  }

  boolean unmarshalOneRow()
    throws SQLException, IOException
  {
    if (this.isUseLess)
    {
      this.lastRowProcessed += 1;

      return false;
    }

    if (this.rowSpaceIndicator == null)
    {
      byte[] arrayOfByte = new byte[16000];

      this.mare.unmarshalCLR(arrayOfByte, 0, this.meta);
      processIndicator(this.meta[0]);

      this.lastRowProcessed += 1;

      return false;
    }

    int i = this.indicatorIndex + this.lastRowProcessed;
    int j = this.lengthIndex + this.lastRowProcessed;

    if (this.isNullByDescribe)
    {
      this.rowSpaceIndicator[i] = -1;
      this.rowSpaceIndicator[j] = 0;
      this.lastRowProcessed += 1;

      if (this.statement.connection.versionNumber < 9200) {
        processIndicator(0);
      }
      return false;
    }

    int k = this.columnIndex + this.lastRowProcessed * this.byteLength;

    this.mare.unmarshalCLR(this.rowSpaceByte, k, this.meta, this.byteLength);

    processIndicator(this.meta[0]);

    if (this.meta[0] == 0)
    {
      this.rowSpaceIndicator[i] = -1;
      this.rowSpaceIndicator[j] = 0;
    }
    else
    {
      this.rowSpaceIndicator[j] = ((short)this.meta[0]);
      this.rowSpaceIndicator[i] = 0;
    }

    this.lastRowProcessed += 1;

    return false;
  }

  void copyRow()
    throws SQLException, IOException
  {
    int i;
    if (this.lastRowProcessed == 0)
      i = this.statement.rowPrefetchInLastFetch - 1;
    else {
      i = this.lastRowProcessed - 1;
    }

    int j = this.columnIndex + this.lastRowProcessed * this.byteLength;
    int k = this.columnIndex + i * this.byteLength;
    int m = this.indicatorIndex + this.lastRowProcessed;
    int n = this.indicatorIndex + i;
    int i1 = this.lengthIndex + this.lastRowProcessed;
    int i2 = this.lengthIndex + i;
    int i3 = this.rowSpaceIndicator[i2];
    int i4 = this.metaDataIndex + this.lastRowProcessed * 1;

    int i5 = this.metaDataIndex + i * 1;

    this.rowSpaceIndicator[i1] = ((short)i3);
    this.rowSpaceIndicator[m] = this.rowSpaceIndicator[n];

    if (!this.isNullByDescribe)
    {
      System.arraycopy(this.rowSpaceByte, k, this.rowSpaceByte, j, i3);
    }

    System.arraycopy(this.rowSpaceMetaData, i5, this.rowSpaceMetaData, i4, 1);

    this.lastRowProcessed += 1;
  }

  void saveDataFromOldDefineBuffers(byte[] paramArrayOfByte, char[] paramArrayOfChar, short[] paramArrayOfShort, int paramInt1, int paramInt2)
    throws SQLException
  {
    int i = this.columnIndex + (paramInt2 - 1) * this.byteLength;

    int j = this.columnIndexLastRow + (paramInt1 - 1) * this.byteLength;

    int k = this.indicatorIndex + paramInt2 - 1;
    int m = this.indicatorIndexLastRow + paramInt1 - 1;
    int n = this.lengthIndex + paramInt2 - 1;
    int i1 = this.lengthIndexLastRow + paramInt1 - 1;
    int i2 = paramArrayOfShort[i1];

    this.rowSpaceIndicator[n] = ((short)i2);
    this.rowSpaceIndicator[k] = paramArrayOfShort[m];

    if (i2 != 0)
    {
      System.arraycopy(paramArrayOfByte, j, this.rowSpaceByte, i, i2);
    }
  }

  String getString(int paramInt)
    throws SQLException
  {
    String str = super.getString(paramInt);

    if ((str != null) && (this.definedColumnSize > 0) && (str.length() > this.definedColumnSize))
    {
      str = str.substring(0, this.definedColumnSize);
    }
    return str;
  }

  String toText(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, boolean paramBoolean, String paramString)
    throws SQLException
  {
    if ((this.definedColumnType == 0) || (this.definedColumnType == -101))
    {
      return super.toText(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramBoolean, paramString);
    }

    String str = (String)this.statement.connection.sessionProperties.get("AUTH_NLS_LXCSTZNFM");
    return nlsFormatToText(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramBoolean, paramString, str);
  }

  private static final String nlsFormatToText(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, boolean paramBoolean, String paramString1, String paramString2)
    throws SQLException
  {
    char[] arrayOfChar = new StringBuilder().append(paramString2).append("      ").toString().toCharArray();
    int i = paramString2.length();

    StringBuffer localStringBuffer = new StringBuffer(i + 25);
    String[] arrayOfString1 = null;
    String[] arrayOfString2 = null;
    TimeZone localTimeZone = null;

    for (int j = 0; j < i; j++)
    {
      switch (arrayOfChar[j])
      {
      case 'R':
      case 'r':
        if ((arrayOfChar[(j + 1)] == 'R') || (arrayOfChar[(j + 1)] == 'r'))
        {
          if (((arrayOfChar[(j + 2)] == 'R') || (arrayOfChar[(j + 2)] == 'r')) && ((arrayOfChar[(j + 3)] == 'R') || (arrayOfChar[(j + 3)] == 'r')))
          {
            if (paramInt1 < 1000)
              localStringBuffer.append(new StringBuilder().append("0").append(paramInt1).toString());
            else if (paramInt1 < 100)
              localStringBuffer.append(new StringBuilder().append("00").append(paramInt1).toString());
            else if (paramInt1 < 10)
              localStringBuffer.append(new StringBuilder().append("000").append(paramInt1).toString());
            else {
              localStringBuffer.append(paramInt1);
            }
            j += 3;
          }
          else
          {
            if (paramInt1 >= 100) {
              paramInt1 %= 100;
            }
            if (paramInt1 < 10)
              localStringBuffer.append(new StringBuilder().append("0").append(paramInt1).toString());
            else {
              localStringBuffer.append(paramInt1);
            }
            j++; }  } break;
      case 'Y':
      case 'y':
        if ((arrayOfChar[(j + 1)] == 'Y') || (arrayOfChar[(j + 1)] == 'y'))
        {
          if (((arrayOfChar[(j + 2)] == 'Y') || (arrayOfChar[(j + 2)] == 'y')) && ((arrayOfChar[(j + 3)] == 'Y') || (arrayOfChar[(j + 3)] == 'y')))
          {
            if (paramInt1 < 1000)
              localStringBuffer.append(new StringBuilder().append("0").append(paramInt1).toString());
            else if (paramInt1 < 100)
              localStringBuffer.append(new StringBuilder().append("00").append(paramInt1).toString());
            else if (paramInt1 < 10)
              localStringBuffer.append(new StringBuilder().append("000").append(paramInt1).toString());
            else {
              localStringBuffer.append(paramInt1);
            }
            j += 3;
          }
          else
          {
            if (paramInt1 >= 100) {
              paramInt1 %= 100;
            }
            if (paramInt1 < 10)
              localStringBuffer.append(new StringBuilder().append("0").append(paramInt1).toString());
            else {
              localStringBuffer.append(paramInt1);
            }
            j++; }  } break;
      case 'D':
      case 'd':
        if ((arrayOfChar[(j + 1)] == 'D') || (arrayOfChar[(j + 1)] == 'd'))
        {
          localStringBuffer.append(new StringBuilder().append(paramInt3 < 10 ? "0" : "").append(paramInt3).toString());
          j++; } break;
      case 'M':
      case 'm':
        if ((arrayOfChar[(j + 1)] == 'M') || (arrayOfChar[(j + 1)] == 'm'))
        {
          localStringBuffer.append(new StringBuilder().append(paramInt2 < 10 ? "0" : "").append(paramInt2).toString());
          j++;
        }
        else if ((arrayOfChar[(j + 1)] == 'I') || (arrayOfChar[(j + 1)] == 'i'))
        {
          localStringBuffer.append(new StringBuilder().append(paramInt5 < 10 ? "0" : "").append(paramInt5).toString());
          j++;
        }
        else if (((arrayOfChar[(j + 1)] == 'O') || (arrayOfChar[(j + 1)] == 'o')) && ((arrayOfChar[(j + 2)] == 'N') || (arrayOfChar[(j + 2)] == 'n')))
        {
          if (((arrayOfChar[(j + 3)] == 'T') || (arrayOfChar[(j + 3)] == 't')) && ((arrayOfChar[(j + 4)] == 'H') || (arrayOfChar[(j + 4)] == 'h')))
          {
            if (arrayOfString2 == null)
              arrayOfString2 = new DateFormatSymbols().getMonths();
            localStringBuffer.append(arrayOfString2[(paramInt2 - 1)]);
            j += 4;
          }
          else {
            if (arrayOfString1 == null)
              arrayOfString1 = new DateFormatSymbols().getShortMonths();
            localStringBuffer.append(arrayOfString1[(paramInt2 - 1)]);
            j += 2; }  } break;
      case 'H':
      case 'h':
        if ((arrayOfChar[(j + 1)] == 'H') || (arrayOfChar[(j + 1)] == 'h'))
        {
          if ((arrayOfChar[(j + 2)] == '2') || (arrayOfChar[(j + 3)] == '4'))
          {
            localStringBuffer.append(new StringBuilder().append(paramInt4 < 10 ? "0" : "").append(paramInt4).toString());
            j += 3;
          }
          else {
            if (paramInt4 > 12)
              paramInt4 -= 12;
            localStringBuffer.append(new StringBuilder().append(paramInt4 < 10 ? "0" : "").append(paramInt4).toString());
            j++; }  } break;
      case 'S':
      case 's':
        if ((arrayOfChar[(j + 1)] == 'S') || (arrayOfChar[(j + 1)] == 's'))
        {
          localStringBuffer.append(new StringBuilder().append(paramInt6 < 10 ? "0" : "").append(paramInt6).toString());
          j++;
          if (((arrayOfChar[(j + 1)] == 'X') || (arrayOfChar[(j + 1)] == 'x')) && ((arrayOfChar[(j + 2)] == 'F') || (arrayOfChar[(j + 2)] == 'f')) && ((arrayOfChar[(j + 3)] == 'F') || (arrayOfChar[(j + 3)] == 'f')))
          {
            localStringBuffer.append(".");
            j++; }  } break;
      case 'F':
      case 'f':
        if ((arrayOfChar[(j + 1)] == 'F') || (arrayOfChar[(j + 1)] == 'f'))
        {
          localStringBuffer.append(paramInt7);
          j++; } break;
      case 'T':
      case 't':
        if ((arrayOfChar[(j + 1)] == 'Z') || (arrayOfChar[(j + 1)] == 'z'))
        {
          if ((arrayOfChar[(j + 2)] == 'R') || (arrayOfChar[(j + 2)] == 'r'))
          {
            if ((paramString1.length() > 3) && (paramString1.startsWith("GMT")))
            {
              localStringBuffer.append(paramString1.substring(3));
            }
            else
            {
              localStringBuffer.append(paramString1.toUpperCase());
            }
            j += 2;
          }
          else
          {
            long l;
            if ((arrayOfChar[(j + 2)] == 'H') || (arrayOfChar[(j + 2)] == 'h'))
            {
              if (localTimeZone == null)
                localTimeZone = TimeZone.getTimeZone(paramString1);
              l = localTimeZone.getRawOffset() / 3600000;
              localStringBuffer.append(l);
              j += 2;
            } else if ((arrayOfChar[(j + 2)] == 'M') || (arrayOfChar[(j + 2)] == 'm'))
            {
              if (localTimeZone == null)
                localTimeZone = TimeZone.getTimeZone(paramString1);
              l = Math.abs(localTimeZone.getRawOffset()) % 3600000 / 60000;
              localStringBuffer.append(new StringBuilder().append(l < 10L ? "0" : "").append(l).toString());
              j += 2; }  } 
        }break;
      case 'A':
      case 'P':
      case 'a':
      case 'p':
        if ((arrayOfChar[(j + 1)] == 'M') || (arrayOfChar[(j + 1)] == 'm'))
        {
          localStringBuffer.append(paramBoolean ? "AM" : "PM");
          j++; } break;
      case 'B':
      case 'C':
      case 'E':
      case 'G':
      case 'I':
      case 'J':
      case 'K':
      case 'L':
      case 'N':
      case 'O':
      case 'Q':
      case 'U':
      case 'V':
      case 'W':
      case 'X':
      case 'Z':
      case '[':
      case '\\':
      case ']':
      case '^':
      case '_':
      case '`':
      case 'b':
      case 'c':
      case 'e':
      case 'g':
      case 'i':
      case 'j':
      case 'k':
      case 'l':
      case 'n':
      case 'o':
      case 'q':
      case 'u':
      case 'v':
      case 'w':
      case 'x':
      default:
        localStringBuffer.append(arrayOfChar[j]);
      }

    }

    return localStringBuffer.substring(0, localStringBuffer.length());
  }

  Object getObject(int paramInt)
    throws SQLException
  {
    if (this.definedColumnType == 0) {
      return super.getObject(paramInt);
    }

    Object localObject = null;
    SQLException localSQLException;
    if (this.rowSpaceIndicator == null)
    {
      localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 21);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1)
    {
      switch (this.definedColumnType)
      {
      case -1:
      case 1:
      case 12:
        return getString(paramInt);
      case 93:
        return getTimestamp(paramInt);
      case -101:
        return getTIMESTAMPTZ(paramInt);
      case 91:
        return getDate(paramInt);
      case 92:
        return getTime(paramInt);
      case -4:
      case -3:
      case -2:
        return getBytes(paramInt);
      }

      localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    return localObject;
  }
}