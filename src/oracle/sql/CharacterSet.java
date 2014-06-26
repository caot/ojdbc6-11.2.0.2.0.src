package oracle.sql;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import oracle.jdbc.driver.DatabaseError;
import oracle.jdbc.internal.OracleConnection;
import oracle.sql.converter.CharacterConverterFactoryOGS;

public abstract class CharacterSet
{
  public static final short DEFAULT_CHARSET = -1;
  public static final short ASCII_CHARSET = 1;
  public static final short ISO_LATIN_1_CHARSET = 31;
  public static final short UNICODE_1_CHARSET = 870;
  public static final short US7ASCII_CHARSET = 1;
  public static final short WE8DEC_CHARSET = 2;
  public static final short WE8HP_CHARSET = 3;
  public static final short US8PC437_CHARSET = 4;
  public static final short WE8EBCDIC37_CHARSET = 5;
  public static final short WE8EBCDIC500_CHARSET = 6;
  public static final short WE8EBCDIC285_CHARSET = 8;
  public static final short WE8PC850_CHARSET = 10;
  public static final short D7DEC_CHARSET = 11;
  public static final short F7DEC_CHARSET = 12;
  public static final short S7DEC_CHARSET = 13;
  public static final short E7DEC_CHARSET = 14;
  public static final short SF7ASCII_CHARSET = 15;
  public static final short NDK7DEC_CHARSET = 16;
  public static final short I7DEC_CHARSET = 17;
  public static final short NL7DEC_CHARSET = 18;
  public static final short CH7DEC_CHARSET = 19;
  public static final short YUG7ASCII_CHARSET = 20;
  public static final short SF7DEC_CHARSET = 21;
  public static final short TR7DEC_CHARSET = 22;
  public static final short IW7IS960_CHARSET = 23;
  public static final short IN8ISCII_CHARSET = 25;
  public static final short WE8ISO8859P1_CHARSET = 31;
  public static final short EE8ISO8859P2_CHARSET = 32;
  public static final short SE8ISO8859P3_CHARSET = 33;
  public static final short NEE8ISO8859P4_CHARSET = 34;
  public static final short CL8ISO8859P5_CHARSET = 35;
  public static final short AR8ISO8859P6_CHARSET = 36;
  public static final short EL8ISO8859P7_CHARSET = 37;
  public static final short IW8ISO8859P8_CHARSET = 38;
  public static final short WE8ISO8859P9_CHARSET = 39;
  public static final short NE8ISO8859P10_CHARSET = 40;
  public static final short TH8TISASCII_CHARSET = 41;
  public static final short TH8TISEBCDIC_CHARSET = 42;
  public static final short BN8BSCII_CHARSET = 43;
  public static final short VN8VN3_CHARSET = 44;
  public static final short VN8MSWIN1258_CHARSET = 45;
  public static final short WE8NEXTSTEP_CHARSET = 50;
  public static final short AR8ASMO708PLUS_CHARSET = 61;
  public static final short AR8EBCDICX_CHARSET = 70;
  public static final short AR8XBASIC_CHARSET = 72;
  public static final short EL8DEC_CHARSET = 81;
  public static final short TR8DEC_CHARSET = 82;
  public static final short WE8EBCDIC37C_CHARSET = 90;
  public static final short WE8EBCDIC500C_CHARSET = 91;
  public static final short IW8EBCDIC424_CHARSET = 92;
  public static final short TR8EBCDIC1026_CHARSET = 93;
  public static final short WE8EBCDIC871_CHARSET = 94;
  public static final short WE8EBCDIC284_CHARSET = 95;
  public static final short WE8EBCDIC1047_CHARSET = 96;
  public static final short EEC8EUROASCI_CHARSET = 110;
  public static final short EEC8EUROPA3_CHARSET = 113;
  public static final short LA8PASSPORT_CHARSET = 114;
  public static final short BG8PC437S_CHARSET = 140;
  public static final short EE8PC852_CHARSET = 150;
  public static final short RU8PC866_CHARSET = 152;
  public static final short RU8BESTA_CHARSET = 153;
  public static final short IW8PC1507_CHARSET = 154;
  public static final short RU8PC855_CHARSET = 155;
  public static final short TR8PC857_CHARSET = 156;
  public static final short CL8MACCYRILLIC_CHARSET = 158;
  public static final short CL8MACCYRILLICS_CHARSET = 159;
  public static final short WE8PC860_CHARSET = 160;
  public static final short IS8PC861_CHARSET = 161;
  public static final short EE8MACCES_CHARSET = 162;
  public static final short EE8MACCROATIANS_CHARSET = 163;
  public static final short TR8MACTURKISHS_CHARSET = 164;
  public static final short IS8MACICELANDICS_CHARSET = 165;
  public static final short EL8MACGREEKS_CHARSET = 166;
  public static final short IW8MACHEBREWS_CHARSET = 167;
  public static final short EE8MSWIN1250_CHARSET = 170;
  public static final short CL8MSWIN1251_CHARSET = 171;
  public static final short ET8MSWIN923_CHARSET = 172;
  public static final short BG8MSWIN_CHARSET = 173;
  public static final short EL8MSWIN1253_CHARSET = 174;
  public static final short IW8MSWIN1255_CHARSET = 175;
  public static final short LT8MSWIN921_CHARSET = 176;
  public static final short TR8MSWIN1254_CHARSET = 177;
  public static final short WE8MSWIN1252_CHARSET = 178;
  public static final short BLT8MSWIN1257_CHARSET = 179;
  public static final short D8EBCDIC273_CHARSET = 180;
  public static final short I8EBCDIC280_CHARSET = 181;
  public static final short DK8EBCDIC277_CHARSET = 182;
  public static final short S8EBCDIC278_CHARSET = 183;
  public static final short EE8EBCDIC870_CHARSET = 184;
  public static final short CL8EBCDIC1025_CHARSET = 185;
  public static final short F8EBCDIC297_CHARSET = 186;
  public static final short IW8EBCDIC1086_CHARSET = 187;
  public static final short CL8EBCDIC1025X_CHARSET = 188;
  public static final short N8PC865_CHARSET = 190;
  public static final short BLT8CP921_CHARSET = 191;
  public static final short LV8PC1117_CHARSET = 192;
  public static final short LV8PC8LR_CHARSET = 193;
  public static final short BLT8EBCDIC1112_CHARSET = 194;
  public static final short LV8RST104090_CHARSET = 195;
  public static final short CL8KOI8R_CHARSET = 196;
  public static final short BLT8PC775_CHARSET = 197;
  public static final short F7SIEMENS9780X_CHARSET = 201;
  public static final short E7SIEMENS9780X_CHARSET = 202;
  public static final short S7SIEMENS9780X_CHARSET = 203;
  public static final short DK7SIEMENS9780X_CHARSET = 204;
  public static final short N7SIEMENS9780X_CHARSET = 205;
  public static final short I7SIEMENS9780X_CHARSET = 206;
  public static final short D7SIEMENS9780X_CHARSET = 207;
  public static final short WE8GCOS7_CHARSET = 210;
  public static final short EL8GCOS7_CHARSET = 211;
  public static final short US8BS2000_CHARSET = 221;
  public static final short D8BS2000_CHARSET = 222;
  public static final short F8BS2000_CHARSET = 223;
  public static final short E8BS2000_CHARSET = 224;
  public static final short DK8BS2000_CHARSET = 225;
  public static final short S8BS2000_CHARSET = 226;
  public static final short WE8BS2000_CHARSET = 231;
  public static final short CL8BS2000_CHARSET = 235;
  public static final short WE8BS2000L5_CHARSET = 239;
  public static final short WE8DG_CHARSET = 241;
  public static final short WE8NCR4970_CHARSET = 251;
  public static final short WE8ROMAN8_CHARSET = 261;
  public static final short EE8MACCE_CHARSET = 262;
  public static final short EE8MACCROATIAN_CHARSET = 263;
  public static final short TR8MACTURKISH_CHARSET = 264;
  public static final short IS8MACICELANDIC_CHARSET = 265;
  public static final short EL8MACGREEK_CHARSET = 266;
  public static final short IW8MACHEBREW_CHARSET = 267;
  public static final short US8ICL_CHARSET = 277;
  public static final short WE8ICL_CHARSET = 278;
  public static final short WE8ISOICLUK_CHARSET = 279;
  public static final short WE8MACROMAN8_CHARSET = 351;
  public static final short WE8MACROMAN8S_CHARSET = 352;
  public static final short TH8MACTHAI_CHARSET = 353;
  public static final short TH8MACTHAIS_CHARSET = 354;
  public static final short HU8CWI2_CHARSET = 368;
  public static final short EL8PC437S_CHARSET = 380;
  public static final short EL8EBCDIC875_CHARSET = 381;
  public static final short EL8PC737_CHARSET = 382;
  public static final short LT8PC772_CHARSET = 383;
  public static final short LT8PC774_CHARSET = 384;
  public static final short EL8PC869_CHARSET = 385;
  public static final short EL8PC851_CHARSET = 386;
  public static final short CDN8PC863_CHARSET = 390;
  public static final short HU8ABMOD_CHARSET = 401;
  public static final short AR8ASMO8X_CHARSET = 500;
  public static final short AR8NAFITHA711T_CHARSET = 504;
  public static final short AR8SAKHR707T_CHARSET = 505;
  public static final short AR8MUSSAD768T_CHARSET = 506;
  public static final short AR8ADOS710T_CHARSET = 507;
  public static final short AR8ADOS720T_CHARSET = 508;
  public static final short AR8APTEC715T_CHARSET = 509;
  public static final short AR8NAFITHA721T_CHARSET = 511;
  public static final short AR8HPARABIC8T_CHARSET = 514;
  public static final short AR8NAFITHA711_CHARSET = 554;
  public static final short AR8SAKHR707_CHARSET = 555;
  public static final short AR8MUSSAD768_CHARSET = 556;
  public static final short AR8ADOS710_CHARSET = 557;
  public static final short AR8ADOS720_CHARSET = 558;
  public static final short AR8APTEC715_CHARSET = 559;
  public static final short AR8MSAWIN_CHARSET = 560;
  public static final short AR8NAFITHA721_CHARSET = 561;
  public static final short AR8SAKHR706_CHARSET = 563;
  public static final short AR8ARABICMAC_CHARSET = 565;
  public static final short AR8ARABICMACS_CHARSET = 566;
  public static final short AR8ARABICMACT_CHARSET = 567;
  public static final short LA8ISO6937_CHARSET = 590;
  public static final short US8NOOP_CHARSET = 797;
  public static final short WE8DECTST_CHARSET = 798;
  public static final short JA16VMS_CHARSET = 829;
  public static final short JA16EUC_CHARSET = 830;
  public static final short JA16EUCYEN_CHARSET = 831;
  public static final short JA16SJIS_CHARSET = 832;
  public static final short JA16DBCS_CHARSET = 833;
  public static final short JA16SJISYEN_CHARSET = 834;
  public static final short JA16EBCDIC930_CHARSET = 835;
  public static final short JA16MACSJIS_CHARSET = 836;
  public static final short JA16EUCTILDE_CHARSET = 837;
  public static final short JA16SJISTILDE_CHARSET = 838;
  public static final short KO16KSC5601_CHARSET = 840;
  public static final short KO16DBCS_CHARSET = 842;
  public static final short KO16KSCCS_CHARSET = 845;
  public static final short KO16MSWIN949_CHARSET = 846;
  public static final short ZHS16CGB231280_CHARSET = 850;
  public static final short ZHS16MACCGB231280_CHARSET = 851;
  public static final short ZHS16GBK_CHARSET = 852;
  public static final short ZHS16DBCS_CHARSET = 853;
  public static final short ZHS32GB18030_CHARSET = 854;
  public static final short ZHT32EUC_CHARSET = 860;
  public static final short ZHT32SOPS_CHARSET = 861;
  public static final short ZHT16DBT_CHARSET = 862;
  public static final short ZHT32TRIS_CHARSET = 863;
  public static final short ZHT16DBCS_CHARSET = 864;
  public static final short ZHT16BIG5_CHARSET = 865;
  public static final short ZHT16CCDC_CHARSET = 866;
  public static final short ZHT16MSWIN950_CHARSET = 867;
  public static final short AL24UTFFSS_CHARSET = 870;
  public static final short UTF8_CHARSET = 871;
  public static final short UTFE_CHARSET = 872;
  public static final short AL32UTF8_CHARSET = 873;
  public static final short KO16TSTSET_CHARSET = 996;
  public static final short JA16TSTSET2_CHARSET = 997;
  public static final short JA16TSTSET_CHARSET = 998;
  public static final short US16TSTFIXED_CHARSET = 1001;
  public static final short AL16UTF16_CHARSET = 2000;
  public static final short AL16UTF16LE_CHARSET = 2002;
  public static final short TH8TISEBCDICS_CHARSET = 319;
  public static final short BLT8EBCDIC1112S_CHARSET = 314;
  public static final short CE8BS2000_CHARSET = 233;
  public static final short CL8EBCDIC1025R_CHARSET = 323;
  public static final short CL8EBCDIC1158R_CHARSET = 326;
  public static final short D8EBCDIC1141_CHARSET = 189;
  public static final short DK8EBCDIC1142_CHARSET = 198;
  public static final short EE8BS2000_CHARSET = 232;
  public static final short EE8EBCDIC870S_CHARSET = 316;
  public static final short EL8EBCDIC423R_CHARSET = 327;
  public static final short EL8EBCDIC875S_CHARSET = 311;
  public static final short EL8EBCDIC875R_CHARSET = 324;
  public static final short F8EBCDIC1147_CHARSET = 208;
  public static final short I8EBCDIC1144_CHARSET = 200;
  public static final short WE8BS2000E_CHARSET = 230;
  public static final short WE8EBCDIC1047E_CHARSET = 100;
  public static final short WE8EBCDIC1140_CHARSET = 7;
  public static final short WE8EBCDIC1145_CHARSET = 98;
  public static final short WE8EBCDIC1146_CHARSET = 9;
  public static final short WE8EBCDIC1148_CHARSET = 27;
  public static final short AR8EBCDIC420S_CHARSET = 320;
  public static final short IW8EBCDIC424S_CHARSET = 315;
  public static final short TR8EBCDIC1026S_CHARSET = 312;
  public static final short ZHT16HKSCS_CHARSET = 868;
  public static final short BLT8ISO8859P13_CHARSET = 47;
  public static final short WE8ISO8859P15_CHARSET = 46;
  public static final short AR8MSWIN1256_CHARSET = 560;
  public static final short S8EBCDIC1143_CHARSET = 199;
  public static final short ZHT16HKSCS31_CHARSET = 992;
  public static final short AZ8ISO8859P9E_CHARSET = 52;
  public static final short CEL8ISO8859P14_CHARSET = 48;
  public static final short CL8ISOIR111_CHARSET = 49;
  public static final short CL8KOI8U_CHARSET = 51;
  public static final short WE8PC858_CHARSET = 28;
  public static final short CL8EBCDIC1025C_CHARSET = 322;
  public static final short CL8EBCDIC1025S_CHARSET = 317;
  public static final short CL8EBCDIC1158_CHARSET = 325;
  public static final short EE8EBCDIC870C_CHARSET = 301;
  public static final short WE8EBCDIC924_CHARSET = 101;
  public static final short WE8EBCDIC1140C_CHARSET = 97;
  public static final short WE8EBCDIC1148C_CHARSET = 99;
  public static final short UNICODE_2_CHARSET = 871;
  private static CharacterSet asciiCharSet = null;
  
  static
  {
    try
    {
      Class.forName("oracle.i18n.text.converter.CharacterConverterSJIS");
      
      CharacterSetWithConverter.ccFactory = new CharacterConverterFactoryOGS();
    }
    catch (ClassNotFoundException localClassNotFoundException) {}
  }
  
  static CharacterSetFactory factory = new CharacterSetFactoryDefault();
  private int oracleId;
  int rep;
  
  CharacterSet(int paramInt)
  {
    this.oracleId = paramInt;
  }
  
  public static CharacterSet make(int paramInt)
  {
    return factory.make(paramInt);
  }
  
  public String toString()
  {
    if (ReadWriteCharacterSetNamesMap.cache == null) {
      buildCharacterSetNames();
    }
    return (String)ReadWriteCharacterSetNamesMap.cache.get(new Short((short)this.oracleId));
  }
  
  synchronized void buildCharacterSetNames()
  {
    if (ReadWriteCharacterSetNamesMap.cache == null)
    {
      Field[] arrayOfField = CharacterSet.class.getFields();
      HashMap localHashMap = new HashMap();
      for (int i = 0; i < arrayOfField.length; i++) {
        try
        {
          String str1 = arrayOfField[i].getName();
          int j = str1.lastIndexOf("_CHARSET");
          if (j != -1)
          {
            str1 = str1.substring(0, j);
            if ((!str1.equals("ASCII")) && (!str1.equals("ISO_LATIN_1")) && (!str1.equals("AR8MSAWIN")) && (!str1.equals("UNICODE_1")) && (!str1.equals("UNICODE_2")))
            {
              short s = arrayOfField[i].getShort(CharacterSet.class);
              int k = arrayOfField[i].getModifiers();
              if ((Modifier.isStatic(k)) && (Modifier.isFinal(k)))
              {
                String str2 = (String)localHashMap.get(new Short(s));
                if (str2 != null) {
                  throw new RuntimeException("duplicate field name: " + str1 + " for id: " + s);
                }
                localHashMap.put(new Short(s), str1);
              }
            }
          }
        }
        catch (Exception localException)
        {
          throw new RuntimeException("Failed for field: " + arrayOfField[i], localException);
        }
      }
      ReadWriteCharacterSetNamesMap.cache = localHashMap;
    }
  }
  
  public boolean isUnicode()
  {
    return false;
  }
  
  boolean isWellFormed(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    return true;
  }
  
  public int getOracleId()
  {
    return this.oracleId;
  }
  
  int getRep()
  {
    return this.rep;
  }
  
  public int getRatioTo(CharacterSet paramCharacterSet)
  {
    throw new Error("oracle.sql.CharacterSet.getRationTo Not Implemented");
  }
  
  public boolean equals(Object paramObject)
  {
    return ((paramObject instanceof CharacterSet)) && (this.oracleId == ((CharacterSet)paramObject).oracleId);
  }
  
  public int hashCode()
  {
    return this.oracleId;
  }
  
  public String toString(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws SQLException
  {
    String str = toStringWithReplacement(paramArrayOfByte, paramInt1, paramInt2);
    byte[] arrayOfByte = convert(str);
    if (paramInt2 != arrayOfByte.length) {
      failCharacterConversion(this);
    }
    for (int i = 0; i < paramInt2; i++) {
      if (arrayOfByte[i] != paramArrayOfByte[(paramInt1 + i)]) {
        failCharacterConversion(this);
      }
    }
    return null;
  }
  
  public byte[] convertUnshared(CharacterSet paramCharacterSet, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws SQLException
  {
    byte[] arrayOfByte = convert(paramCharacterSet, paramArrayOfByte, paramInt1, paramInt2);
    if (arrayOfByte == paramArrayOfByte)
    {
      arrayOfByte = new byte[paramArrayOfByte.length];
      
      System.arraycopy(paramArrayOfByte, 0, arrayOfByte, 0, paramInt2);
    }
    return arrayOfByte;
  }
  
  static final void failCharacterConversion(CharacterSet paramCharacterSet)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(null, 55, paramCharacterSet);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }
  
  static final byte[] useOrCopy(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    byte[] arrayOfByte;
    if ((paramArrayOfByte.length == paramInt2) && (paramInt1 == 0))
    {
      arrayOfByte = paramArrayOfByte;
    }
    else
    {
      arrayOfByte = new byte[paramInt2];
      
      System.arraycopy(paramArrayOfByte, paramInt1, arrayOfByte, 0, paramInt2);
    }
    return arrayOfByte;
  }
  
  static final void need(CharacterBuffer paramCharacterBuffer, int paramInt)
  {
    int i = paramCharacterBuffer.bytes.length;
    int j = paramInt + paramCharacterBuffer.next;
    if (j <= i) {
      return;
    }
    while (j > i) {
      i = 2 * i;
    }
    byte[] arrayOfByte = paramCharacterBuffer.bytes;
    
    paramCharacterBuffer.bytes = new byte[i];
    
    System.arraycopy(arrayOfByte, 0, paramCharacterBuffer.bytes, 0, paramCharacterBuffer.next);
  }
  
  public static final String UTFToString(byte[] paramArrayOfByte, int paramInt1, int paramInt2, boolean paramBoolean)
    throws SQLException
  {
    return new String(UTFToJavaChar(paramArrayOfByte, paramInt1, paramInt2, paramBoolean));
  }
  
  public static final String UTFToString(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws SQLException
  {
    return UTFToString(paramArrayOfByte, paramInt1, paramInt2, false);
  }
  
  public static final char[] UTFToJavaChar(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws SQLException
  {
    return UTFToJavaChar(paramArrayOfByte, paramInt1, paramInt2, false);
  }
  
  public static final char[] UTFToJavaChar(byte[] paramArrayOfByte, int paramInt1, int paramInt2, boolean paramBoolean)
    throws SQLException
  {
    char[] arrayOfChar1 = null;
    
    arrayOfChar1 = new char[paramInt2];
    


    int[] arrayOfInt = new int[1];
    
    arrayOfInt[0] = paramInt2;
    
    int i = convertUTFBytesToJavaChars(paramArrayOfByte, paramInt1, arrayOfChar1, 0, arrayOfInt, paramBoolean);
    
    char[] arrayOfChar2 = new char[i];
    
    System.arraycopy(arrayOfChar1, 0, arrayOfChar2, 0, i);
    
    arrayOfChar1 = null;
    
    return arrayOfChar2;
  }
  
  public static final char[] UTFToJavaCharWithReplacement(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    char[] arrayOfChar1 = null;
    try
    {
      arrayOfChar1 = new char[paramInt2];
      


      int[] arrayOfInt = new int[1];
      
      arrayOfInt[0] = paramInt2;
      
      int i = convertUTFBytesToJavaChars(paramArrayOfByte, paramInt1, arrayOfChar1, 0, arrayOfInt, true);
      
      char[] arrayOfChar2 = new char[i];
      
      System.arraycopy(arrayOfChar1, 0, arrayOfChar2, 0, i);
      
      arrayOfChar1 = null;
      
      return arrayOfChar2;
    }
    catch (SQLException localSQLException)
    {
      throw new IllegalStateException(localSQLException.getMessage());
    }
  }
  
  public static final int convertUTFBytesToJavaChars(byte[] paramArrayOfByte, int paramInt1, char[] paramArrayOfChar, int paramInt2, int[] paramArrayOfInt, boolean paramBoolean)
    throws SQLException
  {
    return convertUTFBytesToJavaChars(paramArrayOfByte, paramInt1, paramArrayOfChar, paramInt2, paramArrayOfInt, paramBoolean, paramArrayOfChar.length - paramInt2);
  }
  
  public static final int convertUTFBytesToJavaChars(byte[] paramArrayOfByte, int paramInt1, char[] paramArrayOfChar, int paramInt2, int[] paramArrayOfInt, boolean paramBoolean, int paramInt3)
    throws SQLException
  {
    CharacterConverterBehavior localCharacterConverterBehavior = paramBoolean ? CharacterConverterBehavior.REPLACEMENT : CharacterConverterBehavior.REPORT_ERROR;
    

    int i = paramArrayOfInt[0];
    
    paramArrayOfInt[0] = 0;
    
    int j = paramInt1;
    int k = paramInt1 + i;
    int m = paramInt2;
    int n = paramInt2 + paramInt3;
    label592:
    while (j < k)
    {
      byte i1 = paramArrayOfByte[(j++)];
      int i3 = i1 & 0xF0;
      char c1;
      switch (i3 / 16)
      {
      case 0: 
      case 1: 
      case 2: 
      case 3: 
      case 4: 
      case 5: 
      case 6: 
      case 7: 
        if (m < n) {
          paramArrayOfChar[(m++)] = ((char)(i1 & 0xFFFFFFFF));
        } else {
          paramArrayOfInt[0] = (k - j + 2);
        }
        break;
      case 12: 
      case 13: 
        if (j >= k)
        {
          paramArrayOfInt[0] = 1;
          
          localCharacterConverterBehavior.onFailConversion();
          break label592;
        }
        c1 = conv2ByteUTFtoUTF16(i1, paramArrayOfByte[(j++)]);
        if (m < n)
        {
          paramArrayOfChar[(m++)] = c1;
        }
        else
        {
          paramArrayOfInt[0] = (k - j + 3);
          break label592;
        }
        localCharacterConverterBehavior.onFailConversion(c1);
        


        break;
      case 14: 
        if (j + 1 >= k)
        {
          paramArrayOfInt[0] = (k - j + 1);
          
          localCharacterConverterBehavior.onFailConversion();
        }
        else
        {
          char c2 = conv3ByteUTFtoUTF16(i1, paramArrayOfByte[(j++)], paramArrayOfByte[(j++)]);
          if ((i3 != 244) && (paramArrayOfByte[(j - 2)] != -65) && (paramArrayOfByte[(j - 1)] != -67)) {
            localCharacterConverterBehavior.onFailConversion(c2);
          }
          if (isHiSurrogate(c2))
          {
            if (m > n - 2)
            {
              paramArrayOfInt[0] = (k - j + 4);
            }
            else
            {
              if (j >= k) {
                continue;
              }
              byte i2 = paramArrayOfByte[j];
              if ((byte)(i2 & 0xF0) != -32)
              {
                paramArrayOfChar[(m++)] = 65533;
                

                localCharacterConverterBehavior.onFailConversion();
                
                continue;
              }
              j++;
              if (j + 1 >= k)
              {
                paramArrayOfInt[0] = (k - j + 1);
                
                localCharacterConverterBehavior.onFailConversion();
              }
              else
              {
                c1 = conv3ByteUTFtoUTF16(i2, paramArrayOfByte[(j++)], paramArrayOfByte[(j++)]);
                if (isLoSurrogate(c1))
                {
                  paramArrayOfChar[(m++)] = c2;
                }
                else
                {
                  paramArrayOfChar[(m++)] = 65533;
                  

                  localCharacterConverterBehavior.onFailConversion();
                }
                paramArrayOfChar[(m++)] = c1;
              }
            }
          }
          else
          {
            if (m < n)
            {
              paramArrayOfChar[(m++)] = c2; continue;
            }
            paramArrayOfInt[0] = (k - j + 4);
          }
        }
        break;
      case 8: 
      case 9: 
      case 10: 
      case 11: 
      default: 
        if (m < n)
        {
          paramArrayOfChar[(m++)] = 65533;
        }
        else
        {
          paramArrayOfInt[0] = (k - j + 2);
          break label592;
        }
        localCharacterConverterBehavior.onFailConversion();
      }
    }
    int i3 = m - paramInt2;
    return i3;
  }
  
  public static final byte[] stringToUTF(String paramString)
  {
    char[] arrayOfChar = paramString.toCharArray();
    int i = arrayOfChar.length * 3;
    byte[] arrayOfByte1 = null;
    byte[] arrayOfByte2 = null;
    
    arrayOfByte1 = new byte[i];
    

    int j = convertJavaCharsToUTFBytes(arrayOfChar, 0, arrayOfByte1, 0, arrayOfChar.length);
    

    arrayOfByte2 = new byte[j];
    
    System.arraycopy(arrayOfByte1, 0, arrayOfByte2, 0, j);
    
    arrayOfByte1 = null;
    
    return arrayOfByte2;
  }
  
  public static final int convertJavaCharsToUTFBytes(char[] paramArrayOfChar, int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3)
  {
    int i = paramInt1;
    int j = paramInt1 + paramInt3;
    
    int k = paramInt2;
    for (int n = i; n < j; n++)
    {
      char m = paramArrayOfChar[n];
      if ((m >= 0) && (m <= 127))
      {
        paramArrayOfByte[(k++)] = ((byte)m);
      }
      else if (m > 2047)
      {
        paramArrayOfByte[(k++)] = ((byte)(0xE0 | m >>> 12 & 0xF));
        paramArrayOfByte[(k++)] = ((byte)(0x80 | m >>> 6 & 0x3F));
        paramArrayOfByte[(k++)] = ((byte)(0x80 | m >>> 0 & 0x3F));
      }
      else
      {
        paramArrayOfByte[(k++)] = ((byte)(0xC0 | m >>> 6 & 0x1F));
        paramArrayOfByte[(k++)] = ((byte)(0x80 | m >>> 0 & 0x3F));
      }
    }
    int m = k - paramInt2;
    
    return m;
  }
  
  public static final int UTFStringLength(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    int i = 0;
    int j = paramInt1;
    int k = paramInt1 + paramInt2;
    while (j < k) {
      switch ((paramArrayOfByte[j] & 0xF0) >>> 4)
      {
      case 0: 
      case 1: 
      case 2: 
      case 3: 
      case 4: 
      case 5: 
      case 6: 
      case 7: 
        j++;
        i++;
        
        break;
      case 12: 
      case 13: 
        if (j + 1 >= k)
        {
          j = k;
        }
        else
        {
          i++;
          j += 2;
        }
        break;
      case 14: 
        if (j + 2 >= k)
        {
          j = k;
        }
        else
        {
          i++;
          j += 3;
        }
        break;
      case 15: 
        if (j + 3 >= k)
        {
          j = k;
        }
        else
        {
          i += 2;
          j += 4;
        }
        break;
      case 8: 
      case 9: 
      case 10: 
      case 11: 
      default: 
        j++;
        i++;
      }
    }
    return i;
  }
  
  public static final int stringUTFLength(String paramString)
  {
    char[] arrayOfChar = paramString.toCharArray();
    return charArrayUTF8Length(arrayOfChar);
  }
  
  static final int charArrayUTF8Length(char[] paramArrayOfChar)
  {
    int i = 0;
    int j = paramArrayOfChar.length;
    for (int m = 0; m < j; m++)
    {
      int k = paramArrayOfChar[m];
      if ((k >= 0) && (k <= 127)) {
        i++;
      } else if (k > 2047) {
        i += 3;
      } else {
        i += 2;
      }
    }
    return i;
  }
  
  public static final String AL32UTF8ToString(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    return AL32UTF8ToString(paramArrayOfByte, paramInt1, paramInt2, false);
  }
  
  public static final String AL32UTF8ToString(byte[] paramArrayOfByte, int paramInt1, int paramInt2, boolean paramBoolean)
  {
    char[] arrayOfChar = null;
    try
    {
      arrayOfChar = AL32UTF8ToJavaChar(paramArrayOfByte, paramInt1, paramInt2, paramBoolean);
    }
    catch (SQLException localSQLException) {}
    return new String(arrayOfChar);
  }
  
  public static final char[] AL32UTF8ToJavaChar(byte[] paramArrayOfByte, int paramInt1, int paramInt2, boolean paramBoolean)
    throws SQLException
  {
    char[] arrayOfChar1 = null;
    try
    {
      arrayOfChar1 = new char[paramInt2];
      



      int[] arrayOfInt = new int[1];
      
      arrayOfInt[0] = paramInt2;
      
      int i = convertAL32UTF8BytesToJavaChars(paramArrayOfByte, paramInt1, arrayOfChar1, 0, arrayOfInt, paramBoolean);
      
      char[] arrayOfChar2 = new char[i];
      
      System.arraycopy(arrayOfChar1, 0, arrayOfChar2, 0, i);
      
      arrayOfChar1 = null;
      
      return arrayOfChar2;
    }
    catch (SQLException localSQLException)
    {
      failUTFConversion();
    }
    return new char[0];
  }
  
  public static final int convertAL32UTF8BytesToJavaChars(byte[] paramArrayOfByte, int paramInt1, char[] paramArrayOfChar, int paramInt2, int[] paramArrayOfInt, boolean paramBoolean)
    throws SQLException
  {
    return convertAL32UTF8BytesToJavaChars(paramArrayOfByte, paramInt1, paramArrayOfChar, paramInt2, paramArrayOfInt, paramBoolean, paramArrayOfChar.length - paramInt2);
  }
  
  public static final int convertAL32UTF8BytesToJavaChars(byte[] paramArrayOfByte, int paramInt1, char[] paramArrayOfChar, int paramInt2, int[] paramArrayOfInt, boolean paramBoolean, int paramInt3)
    throws SQLException
  {
    CharacterConverterBehavior localCharacterConverterBehavior = paramBoolean ? CharacterConverterBehavior.REPLACEMENT : CharacterConverterBehavior.REPORT_ERROR;
    

    int i = paramArrayOfInt[0];
    
    paramArrayOfInt[0] = 0;
    
    int j = paramInt1;
    int k = paramInt1 + i;
    int m = paramInt2;
    int n = paramInt2 + paramInt3;
    label503:
    while (j < k)
    {
      byte i1 = paramArrayOfByte[(j++)];
      int i2 = i1 & 0xF0;
      char c;
      switch (i2 / 16)
      {
      case 0: 
      case 1: 
      case 2: 
      case 3: 
      case 4: 
      case 5: 
      case 6: 
      case 7: 
        if (m < n) {
          paramArrayOfChar[(m++)] = ((char)(i1 & 0xFFFFFFFF));
        } else {
          paramArrayOfInt[0] = (k - j + 2);
        }
        break;
      case 12: 
      case 13: 
        if (j >= k)
        {
          paramArrayOfInt[0] = 1;
          
          localCharacterConverterBehavior.onFailConversion();
          break label503;
        }
        c = conv2ByteUTFtoUTF16(i1, paramArrayOfByte[(j++)]);
        if (m < n)
        {
          paramArrayOfChar[(m++)] = c;
        }
        else
        {
          paramArrayOfInt[0] = (k - j + 3);
          break label503;
        }
        localCharacterConverterBehavior.onFailConversion(c);
        

        break;
      case 14: 
        if (j + 1 >= k)
        {
          paramArrayOfInt[0] = (k - j + 1);
          
          localCharacterConverterBehavior.onFailConversion();
          break label503;
        }
        c = conv3ByteAL32UTF8toUTF16(i1, paramArrayOfByte[(j++)], paramArrayOfByte[(j++)]);
        if (m < n)
        {
          paramArrayOfChar[(m++)] = c;
        }
        else
        {
          paramArrayOfInt[0] = (k - j + 4);
          break label503;
        }
        localCharacterConverterBehavior.onFailConversion(c);
        



        break;
      case 15: 
        if (j + 2 >= k)
        {
          paramArrayOfInt[0] = (k - j + 1);
          
          localCharacterConverterBehavior.onFailConversion();
          break label503;
        }
        if (m > n - 2)
        {
          paramArrayOfInt[0] = (k - j + 2);
          break label503;
        }
        int i3 = conv4ByteAL32UTF8toUTF16(i1, paramArrayOfByte[(j++)], paramArrayOfByte[(j++)], paramArrayOfByte[(j++)], paramArrayOfChar, m);
        if (i3 == 1)
        {
          localCharacterConverterBehavior.onFailConversion();
          
          m++;
        }
        else
        {
          m += 2;
        }
        break;
      case 8: 
      case 9: 
      case 10: 
      case 11: 
      default: 
        if (m < n)
        {
          paramArrayOfChar[(m++)] = 65533;
        }
        else
        {
          paramArrayOfInt[0] = (k - j + 2);
          break label503;
        }
        localCharacterConverterBehavior.onFailConversion();
      }
    }
    int i3 = m - paramInt2;
    return i3;
  }
  
  public static final byte[] stringToAL32UTF8(String paramString)
  {
    char[] arrayOfChar = paramString.toCharArray();
    int i = arrayOfChar.length * 3;
    byte[] arrayOfByte1 = null;
    byte[] arrayOfByte2 = null;
    
    arrayOfByte1 = new byte[i];
    
    int j = convertJavaCharsToAL32UTF8Bytes(arrayOfChar, 0, arrayOfByte1, 0, arrayOfChar.length);
    

    arrayOfByte2 = new byte[j];
    
    System.arraycopy(arrayOfByte1, 0, arrayOfByte2, 0, j);
    
    arrayOfByte1 = null;
    
    return arrayOfByte2;
  }
  
  public static final int convertJavaCharsToAL32UTF8Bytes(char[] paramArrayOfChar, int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3)
  {
    int i = paramInt1;
    int j = paramInt1 + paramInt3;
    int k = paramInt2;
    for (int m = i; m < j; m++)
    {
      int n = paramArrayOfChar[m];
      int i1 = 0;
      if ((n >= 0) && (n <= 127))
      {
        paramArrayOfByte[(k++)] = ((byte)n);
      }
      else if (isHiSurrogate((char)n))
      {
        if ((m + 1 < j) && (isLoSurrogate((char)(i1 = paramArrayOfChar[(m + 1)]))))
        {
          int i2 = (n >>> 6 & 0xF) + 1;
          paramArrayOfByte[(k++)] = ((byte)(i2 >>> 2 | 0xF0));
          paramArrayOfByte[(k++)] = ((byte)((i2 & 0x3) << 4 | n >>> 2 & 0xF | 0x80));
          
          paramArrayOfByte[(k++)] = ((byte)((n & 0x3) << 4 | i1 >>> 6 & 0xF | 0x80));
          
          paramArrayOfByte[(k++)] = ((byte)(i1 & 0x3F | 0x80));
          m++;
        }
        else
        {
          paramArrayOfByte[(k++)] = -17;
          paramArrayOfByte[(k++)] = -65;
          paramArrayOfByte[(k++)] = -67;
        }
      }
      else if (n > 2047)
      {
        paramArrayOfByte[(k++)] = ((byte)(0xE0 | n >>> 12 & 0xF));
        paramArrayOfByte[(k++)] = ((byte)(0x80 | n >>> 6 & 0x3F));
        paramArrayOfByte[(k++)] = ((byte)(0x80 | n >>> 0 & 0x3F));
      }
      else
      {
        paramArrayOfByte[(k++)] = ((byte)(0xC0 | n >>> 6 & 0x1F));
        paramArrayOfByte[(k++)] = ((byte)(0x80 | n >>> 0 & 0x3F));
      }
    }
    int i2 = k - paramInt2;
    
    return i2;
  }
  
  public static final int string32UTF8Length(String paramString)
  {
    return charArray32UTF8Length(paramString.toCharArray());
  }
  
  static final int charArray32UTF8Length(char[] paramArrayOfChar)
  {
    int i = 0;
    int j = paramArrayOfChar.length;
    for (int k = 0; k < j; k++)
    {
      int m = paramArrayOfChar[k];
      if ((m >= 0) && (m <= 127)) {
        i++;
      } else if (m > 2047)
      {
        if (isHiSurrogate((char)m))
        {
          if (k + 1 < j)
          {
            i += 4;
            k++;
          }
        }
        else {
          i += 3;
        }
      }
      else {
        i += 2;
      }
    }
    return i;
  }
  
  public static final String AL16UTF16BytesToString(byte[] paramArrayOfByte, int paramInt)
  {
    char[] arrayOfChar = new char[paramInt >>> 1];
    
    AL16UTF16BytesToJavaChars(paramArrayOfByte, paramInt, arrayOfChar);
    
    return new String(arrayOfChar);
  }
  
  public static final int AL16UTF16BytesToJavaChars(byte[] paramArrayOfByte, int paramInt, char[] paramArrayOfChar)
  {
    int k = paramInt >>> 1;
    

    int i = 0;
    for (int j = 0; i < k; i++)
    {
      int m = paramArrayOfByte[j] << 8;
      paramArrayOfChar[i] = ((char)(m | paramArrayOfByte[(j + 1)] & 0xFF));j += 2;
    }
    return i;
  }
  
  public static final int convertAL16UTF16BytesToJavaChars(byte[] paramArrayOfByte, int paramInt1, char[] paramArrayOfChar, int paramInt2, int paramInt3, boolean paramBoolean)
    throws SQLException
  {
    CharacterConverterBehavior localCharacterConverterBehavior = paramBoolean ? CharacterConverterBehavior.REPLACEMENT : CharacterConverterBehavior.REPORT_ERROR;
    

    int i = paramInt2;
    int j = paramInt1;
    int k = paramInt1 + paramInt3;
    for (; j + 1 < k; j += 2)
    {
      int n = paramArrayOfByte[j] << 8;
      int m = (char)(n | paramArrayOfByte[(j + 1)] & 0xFF);
      
      paramArrayOfChar[(i++)] = (char)m;
    }
    j = i - paramInt2;
    
    return j;
  }
  
  public static final int convertAL16UTF16LEBytesToJavaChars(byte[] paramArrayOfByte, int paramInt1, char[] paramArrayOfChar, int paramInt2, int paramInt3, boolean paramBoolean)
    throws SQLException
  {
    CharacterConverterBehavior localCharacterConverterBehavior = paramBoolean ? CharacterConverterBehavior.REPLACEMENT : CharacterConverterBehavior.REPORT_ERROR;
    

    int i = paramInt2;
    int j = paramInt1;
    int k = paramInt1 + paramInt3;
    for (; j + 1 < k; j += 2)
    {
      int m = paramArrayOfByte[(j + 1)] << 8;
      char c1 = (char)(m | paramArrayOfByte[j] & 0xFF);
      if (isHiSurrogate(c1))
      {
        j += 2;
        if (j + 1 < k)
        {
          char c2 = (char)((paramArrayOfByte[(j + 1)] << 8) + (paramArrayOfByte[j] & 0xFF));
          if (isLoSurrogate(c2)) {
            paramArrayOfChar[(i++)] = c1;
          } else {
            paramArrayOfChar[(i++)] = 65533;
          }
          paramArrayOfChar[(i++)] = c2;
        }
      }
      else
      {
        paramArrayOfChar[(i++)] = c1;
      }
    }
    j = i - paramInt2;
    
    return j;
  }
  
  public static final byte[] stringToAL16UTF16Bytes(String paramString)
  {
    char[] arrayOfChar = paramString.toCharArray();
    int i = arrayOfChar.length;
    byte[] arrayOfByte = new byte[i * 2];
    
    javaCharsToAL16UTF16Bytes(arrayOfChar, i, arrayOfByte);
    
    return arrayOfByte;
  }
  
  public static final int javaCharsToAL16UTF16Bytes(char[] paramArrayOfChar, int paramInt, byte[] paramArrayOfByte)
  {
    int i = Math.min(paramInt, paramArrayOfByte.length >>> 1);
    
    return convertJavaCharsToAL16UTF16Bytes(paramArrayOfChar, 0, paramArrayOfByte, 0, i);
  }
  
  public static final int convertJavaCharsToAL16UTF16Bytes(char[] paramArrayOfChar, int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3)
  {
    int i = paramInt1;
    int j = paramInt2;
    int k = paramInt1 + paramInt3;
    for (; i < k; j += 2)
    {
      paramArrayOfByte[j] = ((byte)(paramArrayOfChar[i] >>> '\b' & 0xFF));
      paramArrayOfByte[(j + 1)] = ((byte)(paramArrayOfChar[i] & 0xFF));i++;
    }
    return j - paramInt2;
  }
  
  public static final byte[] stringToAL16UTF16LEBytes(String paramString)
  {
    char[] arrayOfChar = paramString.toCharArray();
    byte[] arrayOfByte = new byte[arrayOfChar.length * 2];
    
    javaCharsToAL16UTF16LEBytes(arrayOfChar, arrayOfChar.length, arrayOfByte);
    
    return arrayOfByte;
  }
  
  public static final int javaCharsToAL16UTF16LEBytes(char[] paramArrayOfChar, int paramInt, byte[] paramArrayOfByte)
  {
    return convertJavaCharsToAL16UTF16LEBytes(paramArrayOfChar, 0, paramArrayOfByte, 0, paramInt);
  }
  
  public static final int convertJavaCharsToAL16UTF16LEBytes(char[] paramArrayOfChar, int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3)
  {
    int i = paramInt1;
    int j = paramInt2;
    int k = paramInt1 + paramInt3;
    for (; i < k; j += 2)
    {
      paramArrayOfByte[j] = ((byte)(paramArrayOfChar[i] & 0xFF));
      paramArrayOfByte[(j + 1)] = ((byte)(paramArrayOfChar[i] >>> '\b'));i++;
    }
    return j - paramInt2;
  }
  
  public static final int convertASCIIBytesToJavaChars(byte[] paramArrayOfByte, int paramInt1, char[] paramArrayOfChar, int paramInt2, int paramInt3)
    throws SQLException
  {
    int k = paramInt2 + paramInt3;
    
    int i = paramInt2;
    for (int j = paramInt1; i < k; j++)
    {
      paramArrayOfChar[i] = ((char)(0xFF & paramArrayOfByte[j]));i++;
    }
    return paramInt3;
  }
  
  public static final int convertJavaCharsToASCIIBytes(char[] paramArrayOfChar, int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3)
    throws SQLException
  {
    convertJavaCharsToASCIIBytes(paramArrayOfChar, paramInt1, paramArrayOfByte, paramInt2, paramInt3, false);
    
    return paramInt3;
  }
  
  public static final int convertJavaCharsToASCIIBytes(char[] paramArrayOfChar, int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3, boolean paramBoolean)
    throws SQLException
  {
    if (paramBoolean)
    {
      if (asciiCharSet == null) {
        asciiCharSet = make(1);
      }
      byte[] arrayOfByte = asciiCharSet.convertWithReplacement(new String(paramArrayOfChar, paramInt1, paramInt3));
      System.arraycopy(arrayOfByte, 0, paramArrayOfByte, paramInt2, arrayOfByte.length);
      
      return arrayOfByte.length;
    }
    for (int i = 0; i < paramInt3; i++) {
      paramArrayOfByte[(paramInt2 + i)] = ((byte)paramArrayOfChar[(paramInt1 + i)]);
    }
    return paramInt3;
  }
  
  public static final int convertJavaCharsToISOLATIN1Bytes(char[] paramArrayOfChar, int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3)
    throws SQLException
  {
    for (int i = 0; i < paramInt3; i++)
    {
      int j = paramArrayOfChar[(paramInt1 + i)];
      if (j > 255) {
        paramArrayOfByte[(paramInt2 + i)] = -65;
      } else {
        paramArrayOfByte[(paramInt2 + i)] = ((byte)j);
      }
    }
    return paramInt3;
  }
  
  public static final byte[] stringToASCII(String paramString)
  {
    byte[] arrayOfByte = new byte[paramString.length()];
    
    arrayOfByte = paramString.getBytes();
    
    return arrayOfByte;
  }
  
  public static final long convertUTF32toUTF16(long paramLong)
  {
    if (paramLong > 65535L)
    {
      long l = 0xD8 | paramLong - 65536L >> 18 & 0xFF;
      l = paramLong - 65536L >> 10 & 0xFF | l << 8;
      l = 0xDC | (paramLong & 0x3FF) >> 8 & 0xFF | l << 8;
      l = paramLong & 0xFF | l << 8;
      
      return l;
    }
    return paramLong;
  }
  
  static final boolean isHiSurrogate(char paramChar)
  {
    return (char)(paramChar & 0xFC00) == 55296;
  }
  
  static final boolean isLoSurrogate(char paramChar)
  {
    return (char)(paramChar & 0xFC00) == 56320;
  }
  
  static final boolean check80toBF(byte paramByte)
  {
    return (paramByte & 0xFFFFFFC0) == -128;
  }
  
  static final boolean check80to8F(byte paramByte)
  {
    return (paramByte & 0xFFFFFFF0) == -128;
  }
  
  static final boolean check80to9F(byte paramByte)
  {
    return (paramByte & 0xFFFFFFE0) == -128;
  }
  
  static final boolean checkA0toBF(byte paramByte)
  {
    return (paramByte & 0xFFFFFFE0) == -96;
  }
  
  static final boolean check90toBF(byte paramByte)
  {
    return ((paramByte & 0xFFFFFFC0) == -128) && ((paramByte & 0x30) != 0);
  }
  
  static final char conv2ByteUTFtoUTF16(byte paramByte1, byte paramByte2)
  {
    if ((paramByte1 < -62) || (paramByte1 > -33) || (!check80toBF(paramByte2))) {
      return 65533;
    }
    return (char)((paramByte1 & 0x1F) << 6 | paramByte2 & 0x3F);
  }
  
  static final char conv3ByteUTFtoUTF16(byte paramByte1, byte paramByte2, byte paramByte3)
  {
    if (((paramByte1 != -32) || (!checkA0toBF(paramByte2)) || (!check80toBF(paramByte3))) && ((paramByte1 < -31) || (paramByte1 > -17) || (!check80toBF(paramByte2)) || (!check80toBF(paramByte3)))) {
      return 65533;
    }
    return (char)((paramByte1 & 0xF) << 12 | (paramByte2 & 0x3F) << 6 | paramByte3 & 0x3F);
  }
  
  static final char conv3ByteAL32UTF8toUTF16(byte paramByte1, byte paramByte2, byte paramByte3)
  {
    if (((paramByte1 != -32) || (!checkA0toBF(paramByte2)) || (!check80toBF(paramByte3))) && ((paramByte1 < -31) || (paramByte1 > -20) || (!check80toBF(paramByte2)) || (!check80toBF(paramByte3))) && ((paramByte1 != -19) || (!check80to9F(paramByte2)) || (!check80toBF(paramByte3))) && ((paramByte1 < -18) || (paramByte1 > -17) || (!check80toBF(paramByte2)) || (!check80toBF(paramByte3)))) {
      return 65533;
    }
    return (char)((paramByte1 & 0xF) << 12 | (paramByte2 & 0x3F) << 6 | paramByte3 & 0x3F);
  }
  
  static final int conv4ByteAL32UTF8toUTF16(byte paramByte1, byte paramByte2, byte paramByte3, byte paramByte4, char[] paramArrayOfChar, int paramInt)
  {
    int i = 0;
    if (((paramByte1 != -16) || (!check90toBF(paramByte2)) || (!check80toBF(paramByte3)) || (!check80toBF(paramByte4))) && ((paramByte1 < -15) || (paramByte1 > -13) || (!check80toBF(paramByte2)) || (!check80toBF(paramByte3)) || (!check80toBF(paramByte4))) && ((paramByte1 != -12) || (!check80to8F(paramByte2)) || (!check80toBF(paramByte3)) || (!check80toBF(paramByte4))))
    {
      paramArrayOfChar[paramInt] = 65533;
      
      return 1;
    }
    paramArrayOfChar[paramInt] = ((char)((((paramByte1 & 0x7) << 2 | paramByte2 >>> 4 & 0x3) - 1 & 0xF) << 6 | (paramByte2 & 0xF) << 2 | paramByte3 >>> 4 & 0x3 | 0xD800));
    



    paramArrayOfChar[(paramInt + 1)] = ((char)((paramByte3 & 0xF) << 6 | paramByte4 & 0x3F | 0xDC00));
    
    return 2;
  }
  
  static abstract class CharacterConverterBehavior
  {
    public static final char[] NULL_CHARS = new char[1];
    public static final char UTF16_REPLACEMENT_CHAR = 65533;
    public static final CharacterConverterBehavior REPORT_ERROR = new CharacterConverterBehavior("Report Error")
    {
      public void onFailConversion()
        throws SQLException
      {
        SQLException localSQLException = DatabaseError.createSqlException(null, 55);
        localSQLException.fillInStackTrace();
        throw localSQLException;
      }
      
      public void onFailConversion(char paramAnonymousChar)
        throws SQLException
      {
        if (paramAnonymousChar == 65533)
        {
          SQLException localSQLException = DatabaseError.createSqlException(null, 55);
          localSQLException.fillInStackTrace();
          throw localSQLException;
        }
      }
    };
    public static final CharacterConverterBehavior REPLACEMENT = new CharacterConverterBehavior("Replacement")
    {
      public void onFailConversion()
        throws SQLException
      {}
      
      public void onFailConversion(char paramAnonymousChar)
        throws SQLException
      {}
    };
    private final String m_name;
    
    public CharacterConverterBehavior(String paramString)
    {
      this.m_name = paramString;
    }
    
    public abstract void onFailConversion(char paramChar)
      throws SQLException;
    
    public abstract void onFailConversion()
      throws SQLException;
  }
  
  static void failUTFConversion()
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(null, 55);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }
  
  public int encodedByteLength(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      return 0;
    }
    return convertWithReplacement(paramString).length;
  }
  
  public int encodedByteLength(char[] paramArrayOfChar)
  {
    if ((paramArrayOfChar == null) || (paramArrayOfChar.length == 0)) {
      return 0;
    }
    return convertWithReplacement(new String(paramArrayOfChar)).length;
  }
  
  public int toCharWithReplacement(byte[] paramArrayOfByte, int paramInt1, char[] paramArrayOfChar, int paramInt2, int paramInt3)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 23);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }
  
  protected OracleConnection getConnectionDuringExceptionHandling()
  {
    return null;
  }
  
  public boolean isUnknown()
  {
    return false;
  }
  
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;
  
  public abstract boolean isLossyFrom(CharacterSet paramCharacterSet);
  
  public abstract boolean isConvertibleFrom(CharacterSet paramCharacterSet);
  
  public abstract String toStringWithReplacement(byte[] paramArrayOfByte, int paramInt1, int paramInt2);
  
  public abstract byte[] convert(String paramString)
    throws SQLException;
  
  public abstract byte[] convertWithReplacement(String paramString);
  
  public abstract byte[] convert(CharacterSet paramCharacterSet, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws SQLException;
  
  abstract int decode(CharacterWalker paramCharacterWalker)
    throws SQLException;
  
  abstract void encode(CharacterBuffer paramCharacterBuffer, int paramInt)
    throws SQLException;
}
