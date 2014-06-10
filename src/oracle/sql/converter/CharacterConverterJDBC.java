package oracle.sql.converter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Hashtable;
import oracle.sql.ConverterArchive;

public abstract class CharacterConverterJDBC
  implements JdbcCharacterConverters, Serializable
{
  static final long serialVersionUID = 5948085171100875165L;
  static final String CONVERTERNAMEPREFIX = "converter_xcharset/lx2";
  static final String CONVERTERIDPREFIX = "0000";
  static final int HIBYTEMASK = 65280;
  static final int LOWBYTEMASK = 255;
  static final int STORE_INCREMENT = 10;
  static final int INVALID_ORA_CHAR = -1;
  static final int FIRSTBSHIFT = 24;
  static final int SECONDBSHIFT = 16;
  static final int THIRDBSHIFT = 8;
  static final int UB2MASK = 65535;
  static final int UB4MASK = 65535;
  static final HashMap m_converterStore = new HashMap();
  public int m_groupId;
  public int m_oracleId;
  public int[][] extraUnicodeToOracleMapping = (int[][])null;

  public int getGroupId()
  {
    return this.m_groupId;
  }

  public int getOracleId()
  {
    return this.m_oracleId;
  }

  public char[] getLeadingCodes()
  {
    return null;
  }

  public static JdbcCharacterConverters getInstance(int paramInt)
  {
    CharacterConverterJDBC localCharacterConverterJDBC = null;
    int i = 0;
    int j = 0;
    String str1 = Integer.toHexString(paramInt);

    synchronized (m_converterStore)
    {
      localCharacterConverterJDBC = (CharacterConverterJDBC)m_converterStore.get(str1);

      if (localCharacterConverterJDBC != null)
      {
        return localCharacterConverterJDBC;
      }

      String str2 = "converter_xcharset/lx2" + "0000".substring(0, 4 - str1.length()) + str1;

      ConverterArchive localConverterArchive = new ConverterArchive();

      localCharacterConverterJDBC = (CharacterConverterJDBC)localConverterArchive.readObj(str2 + ".glb");

      if (localCharacterConverterJDBC == null)
      {
        return null;
      }

      localCharacterConverterJDBC.buildUnicodeToOracleMapping();
      m_converterStore.put(str1, localCharacterConverterJDBC);

      return localCharacterConverterJDBC;
    }
  }

  protected void storeMappingRange(int paramInt, Hashtable paramHashtable1, Hashtable paramHashtable2)
  {
    int i = paramInt >> 24 & 0xFF;
    int j = paramInt >> 16 & 0xFF;
    int k = paramInt >> 8 & 0xFF;
    int m = paramInt & 0xFF;
    Integer localInteger1 = Integer.valueOf(i);
    Integer localInteger2 = Integer.valueOf(paramInt >> 16 & 0xFFFF);
    Integer localInteger3 = Integer.valueOf(paramInt >> 8 & 0xFFFFFF);

    if (paramInt >>> 26 == 54)
    {
      arrayOfChar = (char[])paramHashtable1.get(localInteger1);

      if (arrayOfChar == null)
      {
        arrayOfChar = new char[] { 'ÿ', '\000' };
      }

      if ((arrayOfChar[0] == 'ÿ') && (arrayOfChar[1] == 0))
      {
        arrayOfChar[0] = ((char)j);
        arrayOfChar[1] = ((char)j);
      }
      else
      {
        if (j < (arrayOfChar[0] & 0xFFFF))
        {
          arrayOfChar[0] = ((char)j);
        }

        if (j > (arrayOfChar[0] & 0xFFFF))
        {
          arrayOfChar[1] = ((char)j);
        }
      }

      paramHashtable1.put(localInteger1, arrayOfChar);

      arrayOfChar = (char[])paramHashtable1.get(localInteger2);

      if (arrayOfChar == null)
      {
        arrayOfChar = new char[] { 'ÿ', '\000' };
      }

      if ((arrayOfChar[0] == 'ÿ') && (arrayOfChar[1] == 0))
      {
        arrayOfChar[0] = ((char)k);
        arrayOfChar[1] = ((char)k);
      }
      else
      {
        if (k < (arrayOfChar[0] & 0xFFFF))
        {
          arrayOfChar[0] = ((char)k);
        }

        if (k > (arrayOfChar[0] & 0xFFFF))
        {
          arrayOfChar[1] = ((char)k);
        }
      }

      paramHashtable1.put(localInteger2, arrayOfChar);
    }

    char[] arrayOfChar = (char[])paramHashtable2.get(localInteger3);

    if (arrayOfChar == null)
    {
      arrayOfChar = new char[] { 'ÿ', '\000' };
    }

    if ((arrayOfChar[0] == 'ÿ') && (arrayOfChar[1] == 0))
    {
      arrayOfChar[0] = ((char)m);
      arrayOfChar[1] = ((char)m);
    }
    else
    {
      if (m < (arrayOfChar[0] & 0xFFFF))
      {
        arrayOfChar[0] = ((char)m);
      }

      if (m > (arrayOfChar[0] & 0xFFFF))
      {
        arrayOfChar[1] = ((char)m);
      }
    }

    paramHashtable2.put(localInteger3, arrayOfChar);
  }
}