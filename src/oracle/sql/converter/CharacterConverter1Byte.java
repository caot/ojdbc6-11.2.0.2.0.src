package oracle.sql.converter;

import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import oracle.jdbc.driver.DatabaseError;
import oracle.jdbc.internal.OracleConnection;

public class CharacterConverter1Byte extends CharacterConverterJDBC
{
  static final long serialVersionUID = 200017349723606452L;
  static final int ORACHARMASK = 255;
  static final int UCSCHARWIDTH = 16;
  public int m_ucsReplacement = 0;
  public int[] m_ucsChar = null;
  public char[] m_oraCharLevel1 = null;
  public char[] m_oraCharSurrogateLevel = null;
  public char[] m_oraCharLevel2 = null;
  public byte m_oraCharReplacement = 0;
  protected transient boolean noSurrogate = true;
  protected transient boolean strictASCII = true;
  protected transient int m_oraCharLevel2Size = 0;

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  public CharacterConverter1Byte()
  {
    this.m_groupId = 0;
  }

  int toUnicode(byte paramByte)
    throws SQLException
  {
    int i = this.m_ucsChar[(paramByte & 0xFF)];

    if (i == -1)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 154);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    return i;
  }

  int toUnicodeWithReplacement(byte paramByte)
  {
    int i = this.m_ucsChar[(paramByte & 0xFF)];

    if (i == -1)
    {
      return this.m_ucsReplacement;
    }

    return i;
  }

  byte toOracleCharacter(char paramChar1, char paramChar2)
    throws SQLException
  {
    int i = paramChar1 >>> '\b' & 0xFF;
    int j = paramChar1 & 0xFF;
    int k = paramChar2 >>> '\b' & 0xFF;
    int m = paramChar2 & 0xFF;

    if ((this.m_oraCharLevel1[i] != (char)this.m_oraCharLevel2Size) && (this.m_oraCharSurrogateLevel[(this.m_oraCharLevel1[i] + j)] != 65535) && (this.m_oraCharSurrogateLevel[(this.m_oraCharSurrogateLevel[(this.m_oraCharLevel1[i] + j)] + k)] != 65535) && (this.m_oraCharLevel2[(this.m_oraCharSurrogateLevel[(this.m_oraCharSurrogateLevel[(this.m_oraCharLevel1[i] + j)] + k)] + m)] != 65535))
    {
      return (byte)this.m_oraCharLevel2[(this.m_oraCharSurrogateLevel[(this.m_oraCharSurrogateLevel[(this.m_oraCharLevel1[i] + j)] + k)] + m)];
    }

    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 155);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  byte toOracleCharacter(char paramChar)
    throws SQLException
  {
    int i = paramChar >>> '\b';
    int j = paramChar & 0xFF;
    int k;
    if ((k = this.m_oraCharLevel2[(this.m_oraCharLevel1[i] + j)]) != 65535)
    {
      return (byte)k;
    }

    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 155);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  byte toOracleCharacterWithReplacement(char paramChar1, char paramChar2)
  {
    int i = paramChar1 >>> '\b' & 0xFF;
    int j = paramChar1 & 0xFF;
    int k = paramChar2 >>> '\b' & 0xFF;
    int m = paramChar2 & 0xFF;

    if ((this.m_oraCharLevel1[i] != (char)this.m_oraCharLevel2Size) && (this.m_oraCharSurrogateLevel[(this.m_oraCharLevel1[i] + j)] != 65535) && (this.m_oraCharSurrogateLevel[(this.m_oraCharSurrogateLevel[(this.m_oraCharLevel1[i] + j)] + k)] != 65535) && (this.m_oraCharLevel2[(this.m_oraCharSurrogateLevel[(this.m_oraCharSurrogateLevel[(this.m_oraCharLevel1[i] + j)] + k)] + m)] != 65535))
    {
      return (byte)this.m_oraCharLevel2[(this.m_oraCharSurrogateLevel[(this.m_oraCharSurrogateLevel[(this.m_oraCharLevel1[i] + j)] + k)] + m)];
    }

    return this.m_oraCharReplacement;
  }

  byte toOracleCharacterWithReplacement(char paramChar)
  {
    int i = paramChar >>> '\b';
    int j = paramChar & 0xFF;
    int k;
    if ((k = this.m_oraCharLevel2[(this.m_oraCharLevel1[i] + j)]) != 65535)
    {
      return (byte)k;
    }

    return this.m_oraCharReplacement;
  }

  public int toUnicodeChars(byte[] paramArrayOfByte, int paramInt1, char[] paramArrayOfChar, int paramInt2, int paramInt3)
    throws SQLException
  {
    int i = paramInt1 + paramInt3;

    int j = 0;

    int m = paramInt2;

    for (int k = paramInt1; (k < i) && (m < paramArrayOfChar.length); k++)
    {
      j = this.m_ucsChar[(paramArrayOfByte[k] & 0xFF)];

      if (j == this.m_ucsReplacement)
      {
        SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 154);
        localSQLException.fillInStackTrace();
        throw localSQLException;
      }

      if ((j & 0xFFFFFFFF) > 65535L)
      {
        if (m + 2 < paramArrayOfChar.length)
        {
          k--;
          break;
        }
        paramArrayOfChar[(m++)] = ((char)(j >>> 16));
        paramArrayOfChar[(m++)] = ((char)(j & 0xFFFF));
      }
      else
      {
        paramArrayOfChar[(m++)] = ((char)j);
      }
    }

    return k;
  }

  public String toUnicodeString(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws SQLException
  {
    int i = paramInt1 + paramInt2;

    StringBuilder localStringBuilder = new StringBuilder(paramInt2);
    int j = 0;

    for (int k = paramInt1; k < i; k++)
    {
      j = this.m_ucsChar[(paramArrayOfByte[k] & 0xFF)];

      if (j == this.m_ucsReplacement)
      {
        SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 154);
        localSQLException.fillInStackTrace();
        throw localSQLException;
      }

      if ((j & 0xFFFFFFFF) > 65535L)
      {
        localStringBuilder.append((char)(j >>> 16));
        localStringBuilder.append((char)(j & 0xFFFF));
      }
      else
      {
        localStringBuilder.append((char)j);
      }
    }

    return localStringBuilder.substring(0, localStringBuilder.length());
  }

  public String toUnicodeStringWithReplacement(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    int i = paramInt1 + paramInt2;
    StringBuilder localStringBuilder = new StringBuilder(paramInt2);
    int j = 0;

    for (int k = paramInt1; k < i; k++)
    {
      j = this.m_ucsChar[(paramArrayOfByte[k] & 0xFF)];

      if (j == -1)
        localStringBuilder.append((char)this.m_ucsReplacement);
      else {
        localStringBuilder.append((char)j);
      }
    }
    return localStringBuilder.substring(0, localStringBuilder.length());
  }

  public byte[] toOracleString(String paramString)
    throws SQLException
  {
    int i = paramString.length();

    if (i == 0)
    {
      return new byte[0];
    }

    char[] arrayOfChar = new char[i];

    paramString.getChars(0, i, arrayOfChar, 0);

    byte[] arrayOfByte1 = new byte[i * 4];

    int j = 0;

    for (int k = 0; k < i; k++)
    {
      if ((arrayOfChar[k] >= 55296) && (arrayOfChar[k] < 56320))
      {
        SQLException localSQLException;
        if ((k + 1 < i) && (arrayOfChar[(k + 1)] >= 56320) && (arrayOfChar[(k + 1)] <= 57343))
        {
          if (this.noSurrogate)
          {
            localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 155);
            localSQLException.fillInStackTrace();
            throw localSQLException;
          }

          arrayOfByte1[(j++)] = toOracleCharacter(arrayOfChar[k], arrayOfChar[(k + 1)]);

          k++;
        }
        else
        {
          localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 155);
          localSQLException.fillInStackTrace();
          throw localSQLException;
        }

      }
      else if ((arrayOfChar[k] < '') && (this.strictASCII))
      {
        arrayOfByte1[(j++)] = ((byte)arrayOfChar[k]);
      }
      else {
        arrayOfByte1[(j++)] = toOracleCharacter(arrayOfChar[k]);
      }

    }

    if (j < arrayOfByte1.length)
    {
      byte[] arrayOfByte2 = new byte[j];

      System.arraycopy(arrayOfByte1, 0, arrayOfByte2, 0, j);

      return arrayOfByte2;
    }

    return arrayOfByte1;
  }

  public byte[] toOracleStringWithReplacement(String paramString)
  {
    int i = paramString.length();

    if (i == 0)
    {
      return new byte[0];
    }

    char[] arrayOfChar = new char[i];

    paramString.getChars(0, i, arrayOfChar, 0);

    byte[] arrayOfByte1 = new byte[i * 4];

    int j = 0;

    for (int k = 0; k < i; k++)
    {
      if ((arrayOfChar[k] >= 55296) && (arrayOfChar[k] < 56320))
      {
        if ((k + 1 < i) && (arrayOfChar[(k + 1)] >= 56320) && (arrayOfChar[(k + 1)] <= 57343))
        {
          if (this.noSurrogate)
          {
            arrayOfByte1[(j++)] = this.m_oraCharReplacement;
          }
          else
          {
            arrayOfByte1[(j++)] = toOracleCharacterWithReplacement(arrayOfChar[k], arrayOfChar[(k + 1)]);
          }

          k++;
        }
        else
        {
          arrayOfByte1[(j++)] = this.m_oraCharReplacement;
        }

      }
      else if ((arrayOfChar[k] < '') && (this.strictASCII))
      {
        arrayOfByte1[(j++)] = ((byte)arrayOfChar[k]);
      }
      else {
        arrayOfByte1[(j++)] = toOracleCharacterWithReplacement(arrayOfChar[k]);
      }

    }

    if (j < arrayOfByte1.length)
    {
      byte[] arrayOfByte2 = new byte[j];

      System.arraycopy(arrayOfByte1, 0, arrayOfByte2, 0, j);

      return arrayOfByte2;
    }

    return arrayOfByte1;
  }

  public void buildUnicodeToOracleMapping()
  {
    this.m_oraCharLevel1 = new char[256];
    this.m_oraCharSurrogateLevel = null;
    this.m_oraCharLevel2 = null;

    Vector localVector = new Vector(45055, 12287);
    Hashtable localHashtable1 = new Hashtable();
    Hashtable localHashtable2 = new Hashtable();

    int i1 = this.m_ucsChar.length;
    int i2 = 0;
    int i3 = 0;

    for (int i4 = 0; i4 < 256; i4++)
    {
      this.m_oraCharLevel1[i4] = 65535;
    }
    int n;
    for (i4 = 0; i4 < i1; i4++)
    {
      n = this.m_ucsChar[i4];

      if (n != -1)
      {
        localObject1 = new int[2];

        localObject1[0] = n;
        localObject1[1] = i4;

        localVector.addElement(localObject1);
        storeMappingRange(n, localHashtable1, localHashtable2);
      }
    }
    if (this.extraUnicodeToOracleMapping != null)
    {
      i1 = this.extraUnicodeToOracleMapping.length;

      for (i4 = 0; i4 < i1; i4++)
      {
        n = this.extraUnicodeToOracleMapping[i4][0];

        storeMappingRange(n, localHashtable1, localHashtable2);
      }
    }

    Object localObject1 = localHashtable1.keys();

    int i5 = 0;
    int i6 = 0;
    Object localObject2;
    char[] arrayOfChar;
    while (((Enumeration)localObject1).hasMoreElements())
    {
      localObject2 = ((Enumeration)localObject1).nextElement();
      arrayOfChar = (char[])localHashtable1.get(localObject2);

      if (arrayOfChar != null)
      {
        i5 += 256;
      }
    }

    localObject1 = localHashtable2.keys();

    while (((Enumeration)localObject1).hasMoreElements())
    {
      localObject2 = ((Enumeration)localObject1).nextElement();
      arrayOfChar = (char[])localHashtable2.get(localObject2);

      if (arrayOfChar != null)
      {
        i6 += 256;
      }
    }

    if (i5 != 0)
    {
      this.m_oraCharSurrogateLevel = new char[i5];
    }

    if (i6 != 0)
    {
      this.m_oraCharLevel2 = new char[i6 + 256];
    }

    for (i4 = 0; i4 < i5; i4++)
    {
      this.m_oraCharSurrogateLevel[i4] = 65535;
    }

    for (i4 = 0; i4 < i6 + 256; i4++)
    {
      this.m_oraCharLevel2[i4] = 65535;
    }
    int i;
    int j;
    int k;
    int m;
    for (i4 = 0; i4 < localVector.size(); i4++)
    {
      int[] arrayOfInt = (int[])localVector.elementAt(i4);

      i = arrayOfInt[0] >> 24 & 0xFF;
      j = arrayOfInt[0] >> 16 & 0xFF;
      k = arrayOfInt[0] >> 8 & 0xFF;
      m = arrayOfInt[0] & 0xFF;

      if ((i >= 216) && (i < 220))
      {
        if (this.m_oraCharLevel1[i] == 65535)
        {
          this.m_oraCharLevel1[i] = i3;
          i3 = (char)(i3 + 256);
        }

        if (this.m_oraCharSurrogateLevel[(this.m_oraCharLevel1[i] + j)] == 65535)
        {
          this.m_oraCharSurrogateLevel[(this.m_oraCharLevel1[i] + j)] = i3;

          i3 = (char)(i3 + 256);
        }

        if (this.m_oraCharSurrogateLevel[(this.m_oraCharSurrogateLevel[(this.m_oraCharLevel1[i] + j)] + k)] == 65535)
        {
          this.m_oraCharSurrogateLevel[(this.m_oraCharSurrogateLevel[(this.m_oraCharLevel1[i] + j)] + k)] = i2;

          i2 = (char)(i2 + 256);
        }

        if (this.m_oraCharLevel2[(this.m_oraCharSurrogateLevel[(this.m_oraCharSurrogateLevel[(this.m_oraCharLevel1[i] + j)] + k)] + m)] == 65535)
        {
          this.m_oraCharLevel2[(this.m_oraCharSurrogateLevel[(this.m_oraCharSurrogateLevel[(this.m_oraCharLevel1[i] + j)] + k)] + m)] = ((char)(arrayOfInt[1] & 0xFFFF));
        }

      }
      else
      {
        if (this.m_oraCharLevel1[k] == 65535)
        {
          this.m_oraCharLevel1[k] = i2;
          i2 = (char)(i2 + 256);
        }

        if (this.m_oraCharLevel2[(this.m_oraCharLevel1[k] + m)] == 65535)
        {
          this.m_oraCharLevel2[(this.m_oraCharLevel1[k] + m)] = ((char)(arrayOfInt[1] & 0xFFFF));
        }

      }

    }

    if (this.extraUnicodeToOracleMapping != null)
    {
      i1 = this.extraUnicodeToOracleMapping.length;

      for (i4 = 0; i4 < i1; i4++)
      {
        n = this.extraUnicodeToOracleMapping[i4][0];

        i = n >>> 24 & 0xFF;
        j = n >>> 16 & 0xFF;
        k = n >>> 8 & 0xFF;
        m = n & 0xFF;

        if ((i >= 216) && (i < 220))
        {
          if (this.m_oraCharLevel1[i] == 65535)
          {
            this.m_oraCharLevel1[i] = i3;
            i3 = (char)(i3 + 256);
          }

          if (this.m_oraCharSurrogateLevel[(this.m_oraCharLevel1[i] + j)] == 65535)
          {
            this.m_oraCharSurrogateLevel[(this.m_oraCharLevel1[i] + j)] = i3;

            i3 = (char)(i3 + 256);
          }

          if (this.m_oraCharSurrogateLevel[(this.m_oraCharSurrogateLevel[(this.m_oraCharLevel1[i] + j)] + k)] == 65535)
          {
            this.m_oraCharSurrogateLevel[(this.m_oraCharSurrogateLevel[(this.m_oraCharLevel1[i] + j)] + k)] = i2;

            i2 = (char)(i2 + 256);
          }

          this.m_oraCharLevel2[(this.m_oraCharSurrogateLevel[(this.m_oraCharSurrogateLevel[(this.m_oraCharLevel1[i] + j)] + k)] + m)] = ((char)(this.extraUnicodeToOracleMapping[i4][1] & 0xFF));
        }
        else
        {
          if (this.m_oraCharLevel1[k] == 65535)
          {
            this.m_oraCharLevel1[k] = i2;
            i2 = (char)(i2 + 256);
          }

          this.m_oraCharLevel2[(this.m_oraCharLevel1[k] + m)] = ((char)(this.extraUnicodeToOracleMapping[i4][1] & 0xFFFF));
        }
      }

    }

    if (this.m_oraCharSurrogateLevel == null)
      this.noSurrogate = true;
    else {
      this.noSurrogate = false;
    }
    this.strictASCII = true;

    for (i4 = 0; i4 < 128; i4++)
    {
      if (this.m_oraCharLevel2[i4] != i4)
      {
        this.strictASCII = false;

        break;
      }
    }

    for (i4 = 0; i4 < 256; i4++)
    {
      if (this.m_oraCharLevel1[i4] == 65535) {
        this.m_oraCharLevel1[i4] = ((char)i6);
      }
    }
    this.m_oraCharLevel2Size = i6;
  }

  public void extractCodepoints(Vector paramVector)
  {
    int i = 0;
    int j = 255;

    for (int k = i; k <= j; k++)
    {
      try
      {
        int[] arrayOfInt = new int[2];
        arrayOfInt[0] = k;
        arrayOfInt[1] = toUnicode((byte)k);

        paramVector.addElement(arrayOfInt);
      }
      catch (SQLException localSQLException)
      {
      }
    }
  }

  public void extractExtraMappings(Vector paramVector)
  {
    if (this.extraUnicodeToOracleMapping == null)
    {
      return;
    }

    for (int i = 0; i < this.extraUnicodeToOracleMapping.length; i++)
    {
      int[] arrayOfInt = new int[2];
      arrayOfInt[0] = this.extraUnicodeToOracleMapping[i][0];
      arrayOfInt[1] = this.extraUnicodeToOracleMapping[i][1];

      paramVector.addElement(arrayOfInt);
    }
  }

  public boolean hasExtraMappings()
  {
    return this.extraUnicodeToOracleMapping != null;
  }

  public char getOraChar1ByteRep()
  {
    return (char)this.m_oraCharReplacement;
  }

  public char getOraChar2ByteRep()
  {
    return '\000';
  }

  public int getUCS2CharRep()
  {
    return this.m_ucsReplacement;
  }

  protected OracleConnection getConnectionDuringExceptionHandling()
  {
    return null;
  }
}