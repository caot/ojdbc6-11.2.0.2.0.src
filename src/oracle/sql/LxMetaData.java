package oracle.sql;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class LxMetaData
{
  private static final String DEFAULT_ES_ORA_LANGUAGE = "LATIN AMERICAN SPANISH";
  private static final int WIDTH_SIZE = 8;
  private static final short WIDTH_MASK = 255;
  public static final int ST_BADCODESET = 0;
  private static final Locale EN_LOCALE = new Locale("en", "US");

  private static Map ORACLE_LANG_2_ISO_A2_LANG = null;

  private static Map ORACLE_TERR_2_ISO_A2_TERR = null;

  private static Map ORACLE_LANG_2_TERR = null;

  private static Map ISO_A2_LANG_2_ORACLE_LANG = null;

  private static Map ISO_LANGUAGE_DEFAULT_TERRITORY = null;

  private static Map ISO_LOCALE_2_ORACLE_LOCALE = null;

  private static Map ISO_A2_TERR_2_ORACLE_TERR = null;

  private static Map CHARSET_RATIO = null;

  static Locale getJavaLocale(String paramString1, String paramString2)
  {
    if (paramString1 == null)
    {
      return null;
    }

    String str1 = paramString1;

    String str2 = EN_LOCALE.getLanguage();
    if (!"".equals(str1))
    {
      if (ORACLE_LANG_2_ISO_A2_LANG == null)
        ORACLE_LANG_2_ISO_A2_LANG = getLang2IsoLangMap();
      str2 = (String)ORACLE_LANG_2_ISO_A2_LANG.get(str1.toUpperCase(Locale.US));

      if (str2 == null)
      {
        return null;
      }
    }
    else
    {
      str1 = "AMERICAN";
    }

    String str3 = null;
    if ((paramString2 == null) || ((str3 = paramString2.toUpperCase(Locale.US)) == null) || ("".equals(str3)))
    {
      if (ORACLE_LANG_2_TERR == null)
        ORACLE_LANG_2_TERR = getLang2Terr();
      str3 = (String)ORACLE_LANG_2_TERR.get(str1);
    }

    if (ORACLE_TERR_2_ISO_A2_TERR == null)
      ORACLE_TERR_2_ISO_A2_TERR = getTerr2IsoTerrMap();
    String str4 = (String)ORACLE_TERR_2_ISO_A2_TERR.get(str3);
    if (str4 == null)
    {
      return null;
    }

    return new Locale(str2, str4);
  }

  public static String getNLSLanguage(Locale paramLocale)
  {
    String str = getOraLocale(paramLocale);
    if (str == null)
      return null;
    int i = str.indexOf('_');
    return i < 0 ? str : str.substring(0, i);
  }

  public static String getNLSTerritory(Locale paramLocale)
  {
    String str = getOraLocale(paramLocale);
    if (str == null)
      return null;
    int i = str.indexOf('_');
    return i < 0 ? null : str.substring(i + 1);
  }

  private static String getOraLocale(Locale paramLocale)
  {
    if (paramLocale == null)
    {
      return null;
    }

    String str1 = paramLocale.getLanguage().equals("") ? EN_LOCALE.getLanguage() : paramLocale.getLanguage();

    if (ISO_A2_LANG_2_ORACLE_LANG == null) {
      ISO_A2_LANG_2_ORACLE_LANG = getIsoLangToOracleMap();
    }

    String str2 = (String)ISO_A2_LANG_2_ORACLE_LANG.get(str1);
    if (str2 == null) {
      return null;
    }

    String str3 = paramLocale.getCountry();

    if (str3.equals(""))
    {
      if (ISO_LANGUAGE_DEFAULT_TERRITORY == null) {
        ISO_LANGUAGE_DEFAULT_TERRITORY = getIsoLangDefaultTerrMap();
      }
      str3 = (String)ISO_LANGUAGE_DEFAULT_TERRITORY.get(str1);

      if (str3 == null) {
        return null;
      }
    }
    Locale localLocale = new Locale(str1, str3);

    if (ISO_LOCALE_2_ORACLE_LOCALE == null) {
      ISO_LOCALE_2_ORACLE_LOCALE = getIsoLocToOracleMap();
    }
    String str4 = (String)ISO_LOCALE_2_ORACLE_LOCALE.get(localLocale.toString());

    if (str4 != null)
    {
      return str4;
    }

    if ("es".equals(str1))
    {
      str2 = "LATIN AMERICAN SPANISH";
    }

    if (ISO_A2_TERR_2_ORACLE_TERR == null) {
      ISO_A2_TERR_2_ORACLE_TERR = getIsoTerrToOracleMap();
    }
    String str5 = (String)ISO_A2_TERR_2_ORACLE_TERR.get(str3);
    return str5 != null ? str2 + "_" + str5 : null;
  }

  public static int getRatio(int paramInt1, int paramInt2)
  {
    if (paramInt2 == paramInt1)
    {
      return 1;
    }

    if (CHARSET_RATIO == null)
    {
      CHARSET_RATIO = getCharsetRatio();
    }

    Object localObject = CHARSET_RATIO.get(Integer.toString(paramInt1));
    if (localObject == null) {
      return 0;
    }
    int i = Integer.parseInt((String)localObject);

    localObject = CHARSET_RATIO.get(Integer.toString(paramInt2));
    if (localObject == null) {
      return 0;
    }
    int j = Integer.parseInt((String)localObject);

    int k = i & 0xFF;

    if (k == 1)
    {
      return 1;
    }

    if (j >>> 8 == 0)
    {
      return k;
    }

    int m = j & 0xFF;
    int n = k / m;

    if (k % m != 0)
    {
      n++;
    }

    return n;
  }

  private static synchronized Map getLang2IsoLangMap()
  {
    HashMap localHashMap = new HashMap();

    localHashMap.put("ALBANIAN", "sq");
    localHashMap.put("AMERICAN", "en");
    localHashMap.put("ARABIC", "ar");
    localHashMap.put("ASSAMESE", "as");
    localHashMap.put("AZERBAIJANI", "az");
    localHashMap.put("BANGLA", "bn");
    localHashMap.put("BELARUSIAN", "be");
    localHashMap.put("BENGALI", "bn");
    localHashMap.put("BRAZILIAN PORTUGUESE", "pt");
    localHashMap.put("BULGARIAN", "bg");
    localHashMap.put("CANADIAN FRENCH", "fr");
    localHashMap.put("CATALAN", "ca");
    localHashMap.put("CROATIAN", "hr");
    localHashMap.put("CYRILLIC KAZAKH", "kk");
    localHashMap.put("CYRILLIC SERBIAN", "sr");
    localHashMap.put("CYRILLIC UZBEK", "uz");
    localHashMap.put("CZECH", "cs");
    localHashMap.put("DANISH", "da");
    localHashMap.put("DUTCH", "nl");
    localHashMap.put("EGYPTIAN", "ar");
    localHashMap.put("ENGLISH", "en");
    localHashMap.put("ESTONIAN", "et");
    localHashMap.put("FINNISH", "fi");
    localHashMap.put("FRENCH", "fr");
    localHashMap.put("GERMAN", "de");
    localHashMap.put("GERMAN DIN", "de");
    localHashMap.put("GREEK", "el");
    localHashMap.put("GUJARATI", "gu");
    localHashMap.put("HEBREW", "iw");
    localHashMap.put("HINDI", "hi");
    localHashMap.put("HUNGARIAN", "hu");
    localHashMap.put("ICELANDIC", "is");
    localHashMap.put("INDONESIAN", "in");
    localHashMap.put("IRISH", "ga");
    localHashMap.put("ITALIAN", "it");
    localHashMap.put("JAPANESE", "ja");
    localHashMap.put("KANNADA", "kn");
    localHashMap.put("KOREAN", "ko");
    localHashMap.put("LATIN AMERICAN SPANISH", "es");
    localHashMap.put("LATIN SERBIAN", "sr");
    localHashMap.put("LATIN UZBEK", "uz");
    localHashMap.put("LATVIAN", "lv");
    localHashMap.put("LITHUANIAN", "lt");
    localHashMap.put("MACEDONIAN", "mk");
    localHashMap.put("MALAY", "ms");
    localHashMap.put("MALAYALAM", "ml");
    localHashMap.put("MARATHI", "mr");
    localHashMap.put("MEXICAN SPANISH", "es");
    localHashMap.put("NORWEGIAN", "no");
    localHashMap.put("NUMERIC DATE LANGUAGE", "en");
    localHashMap.put("ORIYA", "or");
    localHashMap.put("POLISH", "pl");
    localHashMap.put("PORTUGUESE", "pt");
    localHashMap.put("PUNJABI", "pa");
    localHashMap.put("ROMANIAN", "ro");
    localHashMap.put("RUSSIAN", "ru");
    localHashMap.put("SIMPLIFIED CHINESE", "zh");
    localHashMap.put("SLOVAK", "sk");
    localHashMap.put("SLOVENIAN", "sl");
    localHashMap.put("SPANISH", "es");
    localHashMap.put("SWEDISH", "sv");
    localHashMap.put("TAMIL", "ta");
    localHashMap.put("TELUGU", "te");
    localHashMap.put("THAI", "th");
    localHashMap.put("TRADITIONAL CHINESE", "zh");
    localHashMap.put("TURKISH", "tr");
    localHashMap.put("UKRAINIAN", "uk");
    localHashMap.put("VIETNAMESE", "vi");

    return localHashMap;
  }

  private static synchronized Map getTerr2IsoTerrMap()
  {
    HashMap localHashMap = new HashMap();

    localHashMap.put("ALBANIA", "AL");
    localHashMap.put("ALGERIA", "DZ");
    localHashMap.put("AMERICA", "US");
    localHashMap.put("ARGENTINA", "AR");
    localHashMap.put("AUSTRALIA", "AU");
    localHashMap.put("AUSTRIA", "AT");
    localHashMap.put("AZERBAIJAN", "AZ");
    localHashMap.put("BAHRAIN", "BH");
    localHashMap.put("BANGLADESH", "BD");
    localHashMap.put("BELARUS", "BY");
    localHashMap.put("BELGIUM", "BE");
    localHashMap.put("BRAZIL", "BR");
    localHashMap.put("BULGARIA", "BG");
    localHashMap.put("CANADA", "CA");
    localHashMap.put("CATALONIA", "ES");
    localHashMap.put("CHILE", "CL");
    localHashMap.put("CHINA", "CN");
    localHashMap.put("CIS", "RU");
    localHashMap.put("COLOMBIA", "CO");
    localHashMap.put("COSTA RICA", "CR");
    localHashMap.put("CROATIA", "HR");
    localHashMap.put("CYPRUS", "CY");
    localHashMap.put("CZECH REPUBLIC", "CZ");
    localHashMap.put("CZECHOSLOVAKIA", "CZ");
    localHashMap.put("DENMARK", "DK");
    localHashMap.put("DJIBOUTI", "DJ");
    localHashMap.put("ECUADOR", "EC");
    localHashMap.put("EGYPT", "EG");
    localHashMap.put("EL SALVADOR", "SV");
    localHashMap.put("ESTONIA", "EE");
    localHashMap.put("FINLAND", "FI");
    localHashMap.put("FRANCE", "FR");
    localHashMap.put("FYR MACEDONIA", "MK");
    localHashMap.put("GERMANY", "DE");
    localHashMap.put("GREECE", "GR");
    localHashMap.put("GUATEMALA", "GT");
    localHashMap.put("HONG KONG", "HK");
    localHashMap.put("HUNGARY", "HU");
    localHashMap.put("ICELAND", "IS");
    localHashMap.put("INDIA", "IN");
    localHashMap.put("INDONESIA", "ID");
    localHashMap.put("IRAQ", "IQ");
    localHashMap.put("IRELAND", "IE");
    localHashMap.put("ISRAEL", "IL");
    localHashMap.put("ITALY", "IT");
    localHashMap.put("JAPAN", "JP");
    localHashMap.put("JORDAN", "JO");
    localHashMap.put("KAZAKHSTAN", "KZ");
    localHashMap.put("KOREA", "KR");
    localHashMap.put("KUWAIT", "KW");
    localHashMap.put("LATVIA", "LV");
    localHashMap.put("LEBANON", "LB");
    localHashMap.put("LIBYA", "LY");
    localHashMap.put("LITHUANIA", "LT");
    localHashMap.put("LUXEMBOURG", "LU");
    localHashMap.put("MACEDONIA", "MK");
    localHashMap.put("MALAYSIA", "MY");
    localHashMap.put("MAURITANIA", "MR");
    localHashMap.put("MEXICO", "MX");
    localHashMap.put("MOROCCO", "MA");
    localHashMap.put("NEW ZEALAND", "NZ");
    localHashMap.put("NICARAGUA", "NI");
    localHashMap.put("NORWAY", "NO");
    localHashMap.put("OMAN", "OM");
    localHashMap.put("PANAMA", "PA");
    localHashMap.put("PERU", "PE");
    localHashMap.put("PHILIPPINES", "PH");
    localHashMap.put("POLAND", "PL");
    localHashMap.put("PORTUGAL", "PT");
    localHashMap.put("PUERTO RICO", "PR");
    localHashMap.put("QATAR", "QA");
    localHashMap.put("ROMANIA", "RO");
    localHashMap.put("RUSSIA", "RU");
    localHashMap.put("SAUDI ARABIA", "SA");
    localHashMap.put("SERBIA AND MONTENEGRO", "CS");
    localHashMap.put("SINGAPORE", "SG");
    localHashMap.put("SLOVAKIA", "SK");
    localHashMap.put("SLOVENIA", "SI");
    localHashMap.put("SOMALIA", "SO");
    localHashMap.put("SOUTH AFRICA", "ZA");
    localHashMap.put("SPAIN", "ES");
    localHashMap.put("SUDAN", "SD");
    localHashMap.put("SWEDEN", "SE");
    localHashMap.put("SWITZERLAND", "CH");
    localHashMap.put("SYRIA", "SY");
    localHashMap.put("TAIWAN", "TW");
    localHashMap.put("THAILAND", "TH");
    localHashMap.put("THE NETHERLANDS", "NL");
    localHashMap.put("TUNISIA", "TN");
    localHashMap.put("TURKEY", "TR");
    localHashMap.put("UKRAINE", "UA");
    localHashMap.put("UNITED ARAB EMIRATES", "AE");
    localHashMap.put("UNITED KINGDOM", "GB");
    localHashMap.put("UZBEKISTAN", "UZ");
    localHashMap.put("VENEZUELA", "VE");
    localHashMap.put("VIETNAM", "VN");
    localHashMap.put("YEMEN", "YE");
    localHashMap.put("YUGOSLAVIA", "YU");

    return localHashMap;
  }

  private static synchronized Map getLang2Terr()
  {
    HashMap localHashMap = new HashMap();

    localHashMap.put("ALBANIAN", "ALBANIA");
    localHashMap.put("AMERICAN", "AMERICA");
    localHashMap.put("ARABIC", "UNITED ARAB EMIRATES");
    localHashMap.put("ASSAMESE", "INDIA");
    localHashMap.put("AZERBAIJANI", "AZERBAIJAN");
    localHashMap.put("BANGLA", "INDIA");
    localHashMap.put("BELARUSIAN", "BELARUS");
    localHashMap.put("BRAZILIAN PORTUGUESE", "BRAZIL");
    localHashMap.put("BULGARIAN", "BULGARIA");
    localHashMap.put("CANADIAN FRENCH", "CANADA");
    localHashMap.put("CATALAN", "CATALONIA");
    localHashMap.put("CROATIAN", "CROATIA");
    localHashMap.put("CYRILLIC KAZAKH", "KAZAKHSTAN");
    localHashMap.put("CYRILLIC SERBIAN", "SERBIA AND MONTENEGRO");
    localHashMap.put("CYRILLIC UZBEK", "UZBEKISTAN");
    localHashMap.put("CZECH", "CZECH REPUBLIC");
    localHashMap.put("DANISH", "DENMARK");
    localHashMap.put("DUTCH", "THE NETHERLANDS");
    localHashMap.put("EGYPTIAN", "EGYPT");
    localHashMap.put("ENGLISH", "UNITED KINGDOM");
    localHashMap.put("ESTONIAN", "ESTONIA");
    localHashMap.put("FINNISH", "FINLAND");
    localHashMap.put("FRENCH", "FRANCE");
    localHashMap.put("GERMAN", "GERMANY");
    localHashMap.put("GERMAN DIN", "GERMANY");
    localHashMap.put("GREEK", "GREECE");
    localHashMap.put("GUJARATI", "INDIA");
    localHashMap.put("HEBREW", "ISRAEL");
    localHashMap.put("HINDI", "INDIA");
    localHashMap.put("HUNGARIAN", "HUNGARY");
    localHashMap.put("ICELANDIC", "ICELAND");
    localHashMap.put("INDONESIAN", "INDONESIA");
    localHashMap.put("IRISH", "IRELAND");
    localHashMap.put("ITALIAN", "ITALY");
    localHashMap.put("JAPANESE", "JAPAN");
    localHashMap.put("KANNADA", "INDIA");
    localHashMap.put("KOREAN", "KOREA");
    localHashMap.put("LATIN AMERICAN SPANISH", "AMERICA");
    localHashMap.put("LATIN SERBIAN", "SERBIA AND MONTENEGRO");
    localHashMap.put("LATIN UZBEK", "UZBEKISTAN");
    localHashMap.put("LATVIAN", "LATVIA");
    localHashMap.put("LITHUANIAN", "LITHUANIA");
    localHashMap.put("MACEDONIAN", "FYR MACEDONIA");
    localHashMap.put("MALAY", "MALAYSIA");
    localHashMap.put("MALAYALAM", "INDIA");
    localHashMap.put("MARATHI", "INDIA");
    localHashMap.put("MEXICAN SPANISH", "MEXICO");
    localHashMap.put("NORWEGIAN", "NORWAY");
    localHashMap.put("ORIYA", "INDIA");
    localHashMap.put("POLISH", "POLAND");
    localHashMap.put("PORTUGUESE", "PORTUGAL");
    localHashMap.put("PUNJABI", "INDIA");
    localHashMap.put("ROMANIAN", "ROMANIA");
    localHashMap.put("RUSSIAN", "RUSSIA");
    localHashMap.put("SIMPLIFIED CHINESE", "CHINA");
    localHashMap.put("SLOVAK", "SLOVAKIA");
    localHashMap.put("SLOVENIAN", "SLOVENIA");
    localHashMap.put("SPANISH", "SPAIN");
    localHashMap.put("SWEDISH", "SWEDEN");
    localHashMap.put("TAMIL", "INDIA");
    localHashMap.put("TELUGU", "INDIA");
    localHashMap.put("THAI", "THAILAND");
    localHashMap.put("TRADITIONAL CHINESE", "TAIWAN");
    localHashMap.put("TURKISH", "TURKEY");
    localHashMap.put("UKRAINIAN", "UKRAINE");
    localHashMap.put("VIETNAMESE", "VIETNAM");

    return localHashMap;
  }

  private static synchronized Map getIsoLangToOracleMap()
  {
    HashMap localHashMap = new HashMap();

    localHashMap.put("ar", "ARABIC");
    localHashMap.put("as", "ASSAMESE");
    localHashMap.put("az", "AZERBAIJANI");
    localHashMap.put("be", "BELARUSIAN");
    localHashMap.put("bg", "BULGARIAN");
    localHashMap.put("bn", "BANGLA");
    localHashMap.put("ca", "CATALAN");
    localHashMap.put("cs", "CZECH");
    localHashMap.put("da", "DANISH");
    localHashMap.put("de", "GERMAN");
    localHashMap.put("el", "GREEK");
    localHashMap.put("en", "ENGLISH");
    localHashMap.put("es", "SPANISH");
    localHashMap.put("et", "ESTONIAN");
    localHashMap.put("fi", "FINNISH");
    localHashMap.put("fr", "FRENCH");
    localHashMap.put("ga", "IRISH");
    localHashMap.put("gu", "GUJARATI");
    localHashMap.put("he", "HEBREW");
    localHashMap.put("hi", "HINDI");
    localHashMap.put("hr", "CROATIAN");
    localHashMap.put("hu", "HUNGARIAN");
    localHashMap.put("id", "INDONESIAN");
    localHashMap.put("in", "INDONESIAN");
    localHashMap.put("is", "ICELANDIC");
    localHashMap.put("it", "ITALIAN");
    localHashMap.put("iw", "HEBREW");
    localHashMap.put("ja", "JAPANESE");
    localHashMap.put("kk", "CYRILLIC KAZAKH");
    localHashMap.put("kn", "KANNADA");
    localHashMap.put("ko", "KOREAN");
    localHashMap.put("lt", "LITHUANIAN");
    localHashMap.put("lv", "LATVIAN");
    localHashMap.put("mk", "MACEDONIAN");
    localHashMap.put("ml", "MALAYALAM");
    localHashMap.put("mr", "MARATHI");
    localHashMap.put("ms", "MALAY");
    localHashMap.put("nb", "NORWEGIAN");
    localHashMap.put("nl", "DUTCH");
    localHashMap.put("nn", "NORWEGIAN");
    localHashMap.put("no", "NORWEGIAN");
    localHashMap.put("or", "ORIYA");
    localHashMap.put("pa", "PUNJABI");
    localHashMap.put("pl", "POLISH");
    localHashMap.put("pt", "PORTUGUESE");
    localHashMap.put("ro", "ROMANIAN");
    localHashMap.put("ru", "RUSSIAN");
    localHashMap.put("sk", "SLOVAK");
    localHashMap.put("sl", "SLOVENIAN");
    localHashMap.put("sq", "ALBANIAN");
    localHashMap.put("sr", "CYRILLIC SERBIAN");
    localHashMap.put("sv", "SWEDISH");
    localHashMap.put("ta", "TAMIL");
    localHashMap.put("te", "TELUGU");
    localHashMap.put("th", "THAI");
    localHashMap.put("tr", "TURKISH");
    localHashMap.put("uk", "UKRAINIAN");
    localHashMap.put("uz", "LATIN UZBEK");
    localHashMap.put("vi", "VIETNAMESE");
    localHashMap.put("zh", "SIMPLIFIED CHINESE");

    return localHashMap;
  }

  private static synchronized Map getIsoLangDefaultTerrMap()
  {
    HashMap localHashMap = new HashMap();

    localHashMap.put("ar", "AE");
    localHashMap.put("as", "IN");
    localHashMap.put("az", "AZ");
    localHashMap.put("be", "BY");
    localHashMap.put("bg", "BG");
    localHashMap.put("bn", "BD");
    localHashMap.put("ca", "ES");
    localHashMap.put("cs", "CZ");
    localHashMap.put("da", "DK");
    localHashMap.put("de", "DE");
    localHashMap.put("el", "GR");
    localHashMap.put("en", "US");
    localHashMap.put("es", "ES");
    localHashMap.put("et", "EE");
    localHashMap.put("fi", "FI");
    localHashMap.put("fr", "FR");
    localHashMap.put("ga", "IE");
    localHashMap.put("gu", "IN");
    localHashMap.put("he", "IL");
    localHashMap.put("hi", "IN");
    localHashMap.put("hr", "HR");
    localHashMap.put("hu", "HU");
    localHashMap.put("id", "ID");
    localHashMap.put("in", "ID");
    localHashMap.put("is", "IS");
    localHashMap.put("it", "IT");
    localHashMap.put("iw", "IL");
    localHashMap.put("ja", "JP");
    localHashMap.put("kk", "KZ");
    localHashMap.put("kn", "IN");
    localHashMap.put("ko", "KR");
    localHashMap.put("lt", "LT");
    localHashMap.put("lv", "LV");
    localHashMap.put("mk", "MK");
    localHashMap.put("ml", "IN");
    localHashMap.put("mr", "IN");
    localHashMap.put("ms", "MY");
    localHashMap.put("nb", "NO");
    localHashMap.put("nl", "NL");
    localHashMap.put("nn", "NO");
    localHashMap.put("no", "NO");
    localHashMap.put("or", "IN");
    localHashMap.put("pa", "IN");
    localHashMap.put("pl", "PL");
    localHashMap.put("pt", "PT");
    localHashMap.put("ro", "RO");
    localHashMap.put("ru", "RU");
    localHashMap.put("sk", "SK");
    localHashMap.put("sl", "SI");
    localHashMap.put("sq", "AL");
    localHashMap.put("sr", "CS");
    localHashMap.put("sv", "SE");
    localHashMap.put("ta", "IN");
    localHashMap.put("te", "IN");
    localHashMap.put("th", "TH");
    localHashMap.put("tr", "TR");
    localHashMap.put("uk", "UA");
    localHashMap.put("uz", "UZ");
    localHashMap.put("vi", "VN");
    localHashMap.put("zh", "CN");

    return localHashMap;
  }

  private static synchronized Map getIsoLocToOracleMap()
  {
    HashMap localHashMap = new HashMap();

    localHashMap.put("ar_EG", "EGYPTIAN_EGYPT");
    localHashMap.put("ca_ES", "CATALAN_CATALONIA");
    localHashMap.put("en_US", "AMERICAN_AMERICA");
    localHashMap.put("es_ES", "SPANISH_SPAIN");
    localHashMap.put("es_MX", "MEXICAN SPANISH_MEXICO");
    localHashMap.put("fr_CA", "CANADIAN FRENCH_CANADA");
    localHashMap.put("pt_BR", "BRAZILIAN PORTUGUESE_BRAZIL");
    localHashMap.put("zh_HK", "TRADITIONAL CHINESE_HONG KONG");
    localHashMap.put("zh_TW", "TRADITIONAL CHINESE_TAIWAN");

    return localHashMap;
  }

  private static synchronized Map getIsoTerrToOracleMap()
  {
    HashMap localHashMap = new HashMap();

    localHashMap.put("AE", "UNITED ARAB EMIRATES");
    localHashMap.put("AL", "ALBANIA");
    localHashMap.put("AR", "ARGENTINA");
    localHashMap.put("AT", "AUSTRIA");
    localHashMap.put("AU", "AUSTRALIA");
    localHashMap.put("AZ", "AZERBAIJAN");
    localHashMap.put("BD", "BANGLADESH");
    localHashMap.put("BE", "BELGIUM");
    localHashMap.put("BG", "BULGARIA");
    localHashMap.put("BH", "BAHRAIN");
    localHashMap.put("BR", "BRAZIL");
    localHashMap.put("BY", "BELARUS");
    localHashMap.put("CA", "CANADA");
    localHashMap.put("CH", "SWITZERLAND");
    localHashMap.put("CL", "CHILE");
    localHashMap.put("CN", "CHINA");
    localHashMap.put("CO", "COLOMBIA");
    localHashMap.put("CR", "COSTA RICA");
    localHashMap.put("CS", "SERBIA AND MONTENEGRO");
    localHashMap.put("CY", "CYPRUS");
    localHashMap.put("CZ", "CZECH REPUBLIC");
    localHashMap.put("DE", "GERMANY");
    localHashMap.put("DJ", "DJIBOUTI");
    localHashMap.put("DK", "DENMARK");
    localHashMap.put("DZ", "ALGERIA");
    localHashMap.put("EC", "ECUADOR");
    localHashMap.put("EE", "ESTONIA");
    localHashMap.put("EG", "EGYPT");
    localHashMap.put("ES", "SPAIN");
    localHashMap.put("FI", "FINLAND");
    localHashMap.put("FR", "FRANCE");
    localHashMap.put("GB", "UNITED KINGDOM");
    localHashMap.put("GR", "GREECE");
    localHashMap.put("GT", "GUATEMALA");
    localHashMap.put("HK", "HONG KONG");
    localHashMap.put("HR", "CROATIA");
    localHashMap.put("HU", "HUNGARY");
    localHashMap.put("ID", "INDONESIA");
    localHashMap.put("IE", "IRELAND");
    localHashMap.put("IL", "ISRAEL");
    localHashMap.put("IN", "INDIA");
    localHashMap.put("IQ", "IRAQ");
    localHashMap.put("IS", "ICELAND");
    localHashMap.put("IT", "ITALY");
    localHashMap.put("JO", "JORDAN");
    localHashMap.put("JP", "JAPAN");
    localHashMap.put("KR", "KOREA");
    localHashMap.put("KW", "KUWAIT");
    localHashMap.put("KZ", "KAZAKHSTAN");
    localHashMap.put("LB", "LEBANON");
    localHashMap.put("LT", "LITHUANIA");
    localHashMap.put("LU", "LUXEMBOURG");
    localHashMap.put("LV", "LATVIA");
    localHashMap.put("LY", "LIBYA");
    localHashMap.put("MA", "MOROCCO");
    localHashMap.put("MK", "FYR MACEDONIA");
    localHashMap.put("MR", "MAURITANIA");
    localHashMap.put("MX", "MEXICO");
    localHashMap.put("MY", "MALAYSIA");
    localHashMap.put("NI", "NICARAGUA");
    localHashMap.put("NL", "THE NETHERLANDS");
    localHashMap.put("NO", "NORWAY");
    localHashMap.put("NZ", "NEW ZEALAND");
    localHashMap.put("OM", "OMAN");
    localHashMap.put("PA", "PANAMA");
    localHashMap.put("PE", "PERU");
    localHashMap.put("PH", "PHILIPPINES");
    localHashMap.put("PL", "POLAND");
    localHashMap.put("PR", "PUERTO RICO");
    localHashMap.put("PT", "PORTUGAL");
    localHashMap.put("QA", "QATAR");
    localHashMap.put("RO", "ROMANIA");
    localHashMap.put("RU", "RUSSIA");
    localHashMap.put("SA", "SAUDI ARABIA");
    localHashMap.put("SD", "SUDAN");
    localHashMap.put("SE", "SWEDEN");
    localHashMap.put("SG", "SINGAPORE");
    localHashMap.put("SI", "SLOVENIA");
    localHashMap.put("SK", "SLOVAKIA");
    localHashMap.put("SO", "SOMALIA");
    localHashMap.put("SV", "EL SALVADOR");
    localHashMap.put("SY", "SYRIA");
    localHashMap.put("TH", "THAILAND");
    localHashMap.put("TN", "TUNISIA");
    localHashMap.put("TR", "TURKEY");
    localHashMap.put("TW", "TAIWAN");
    localHashMap.put("UA", "UKRAINE");
    localHashMap.put("US", "AMERICA");
    localHashMap.put("UZ", "UZBEKISTAN");
    localHashMap.put("VE", "VENEZUELA");
    localHashMap.put("VN", "VIETNAM");
    localHashMap.put("YE", "YEMEN");
    localHashMap.put("YU", "YUGOSLAVIA");
    localHashMap.put("ZA", "SOUTH AFRICA");

    return localHashMap;
  }

  private static synchronized Map getCharsetRatio()
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("2000", "258");
    localHashMap.put("873", "4");
    localHashMap.put("557", "1");
    localHashMap.put("558", "1");
    localHashMap.put("559", "1");
    localHashMap.put("565", "1");
    localHashMap.put("566", "1");
    localHashMap.put("500", "1");
    localHashMap.put("320", "1");
    localHashMap.put("70", "1");
    localHashMap.put("36", "1");
    localHashMap.put("560", "1");
    localHashMap.put("556", "1");
    localHashMap.put("554", "1");
    localHashMap.put("561", "1");
    localHashMap.put("563", "1");
    localHashMap.put("555", "1");
    localHashMap.put("52", "1");
    localHashMap.put("173", "1");
    localHashMap.put("140", "1");
    localHashMap.put("191", "1");
    localHashMap.put("194", "1");
    localHashMap.put("314", "1");
    localHashMap.put("47", "1");
    localHashMap.put("179", "1");
    localHashMap.put("197", "1");
    localHashMap.put("43", "1");
    localHashMap.put("390", "1");
    localHashMap.put("233", "1");
    localHashMap.put("48", "1");
    localHashMap.put("19", "1");
    localHashMap.put("235", "1");
    localHashMap.put("185", "1");
    localHashMap.put("322", "1");
    localHashMap.put("323", "1");
    localHashMap.put("317", "1");
    localHashMap.put("188", "1");
    localHashMap.put("325", "1");
    localHashMap.put("326", "1");
    localHashMap.put("35", "1");
    localHashMap.put("49", "1");
    localHashMap.put("196", "1");
    localHashMap.put("51", "1");
    localHashMap.put("158", "1");
    localHashMap.put("159", "1");
    localHashMap.put("171", "1");
    localHashMap.put("11", "1");
    localHashMap.put("207", "1");
    localHashMap.put("222", "1");
    localHashMap.put("189", "1");
    localHashMap.put("180", "1");
    localHashMap.put("204", "1");
    localHashMap.put("225", "1");
    localHashMap.put("198", "1");
    localHashMap.put("182", "1");
    localHashMap.put("14", "1");
    localHashMap.put("202", "1");
    localHashMap.put("224", "1");
    localHashMap.put("232", "1");
    localHashMap.put("184", "1");
    localHashMap.put("301", "1");
    localHashMap.put("316", "1");
    localHashMap.put("32", "1");
    localHashMap.put("262", "1");
    localHashMap.put("162", "1");
    localHashMap.put("263", "1");
    localHashMap.put("163", "1");
    localHashMap.put("170", "1");
    localHashMap.put("150", "1");
    localHashMap.put("110", "1");
    localHashMap.put("113", "1");
    localHashMap.put("81", "1");
    localHashMap.put("327", "1");
    localHashMap.put("381", "1");
    localHashMap.put("324", "1");
    localHashMap.put("211", "1");
    localHashMap.put("37", "1");
    localHashMap.put("266", "1");
    localHashMap.put("166", "1");
    localHashMap.put("174", "1");
    localHashMap.put("380", "1");
    localHashMap.put("382", "1");
    localHashMap.put("386", "1");
    localHashMap.put("385", "1");
    localHashMap.put("172", "1");
    localHashMap.put("12", "1");
    localHashMap.put("201", "1");
    localHashMap.put("223", "1");
    localHashMap.put("208", "1");
    localHashMap.put("186", "1");
    localHashMap.put("401", "1");
    localHashMap.put("368", "1");
    localHashMap.put("17", "1");
    localHashMap.put("206", "1");
    localHashMap.put("200", "1");
    localHashMap.put("181", "1");
    localHashMap.put("25", "1");
    localHashMap.put("265", "1");
    localHashMap.put("165", "1");
    localHashMap.put("161", "1");
    localHashMap.put("23", "1");
    localHashMap.put("187", "1");
    localHashMap.put("92", "1");
    localHashMap.put("315", "1");
    localHashMap.put("38", "1");
    localHashMap.put("267", "1");
    localHashMap.put("167", "1");
    localHashMap.put("175", "1");
    localHashMap.put("154", "1");
    localHashMap.put("833", "2");
    localHashMap.put("835", "2");
    localHashMap.put("830", "3");
    localHashMap.put("837", "3");
    localHashMap.put("831", "3");
    localHashMap.put("836", "2");
    localHashMap.put("832", "2");
    localHashMap.put("838", "2");
    localHashMap.put("834", "2");
    localHashMap.put("829", "2");
    localHashMap.put("842", "2");
    localHashMap.put("840", "2");
    localHashMap.put("845", "2");
    localHashMap.put("846", "2");
    localHashMap.put("590", "1");
    localHashMap.put("114", "1");
    localHashMap.put("176", "1");
    localHashMap.put("383", "1");
    localHashMap.put("384", "1");
    localHashMap.put("192", "1");
    localHashMap.put("193", "1");
    localHashMap.put("195", "1");
    localHashMap.put("205", "1");
    localHashMap.put("190", "1");
    localHashMap.put("16", "1");
    localHashMap.put("40", "1");
    localHashMap.put("34", "1");
    localHashMap.put("18", "1");
    localHashMap.put("153", "1");
    localHashMap.put("155", "1");
    localHashMap.put("152", "1");
    localHashMap.put("13", "1");
    localHashMap.put("203", "1");
    localHashMap.put("226", "1");
    localHashMap.put("199", "1");
    localHashMap.put("183", "1");
    localHashMap.put("33", "1");
    localHashMap.put("15", "1");
    localHashMap.put("21", "1");
    localHashMap.put("353", "1");
    localHashMap.put("354", "1");
    localHashMap.put("41", "1");
    localHashMap.put("42", "1");
    localHashMap.put("319", "1");
    localHashMap.put("22", "1");
    localHashMap.put("82", "1");
    localHashMap.put("93", "1");
    localHashMap.put("312", "1");
    localHashMap.put("264", "1");
    localHashMap.put("164", "1");
    localHashMap.put("177", "1");
    localHashMap.put("156", "1");
    localHashMap.put("1", "1");
    localHashMap.put("221", "1");
    localHashMap.put("277", "1");
    localHashMap.put("4", "1");
    localHashMap.put("871", "3");
    localHashMap.put("872", "4");
    localHashMap.put("45", "1");
    localHashMap.put("44", "1");
    localHashMap.put("231", "1");
    localHashMap.put("230", "1");
    localHashMap.put("239", "1");
    localHashMap.put("2", "1");
    localHashMap.put("241", "1");
    localHashMap.put("96", "1");
    localHashMap.put("100", "1");
    localHashMap.put("7", "1");
    localHashMap.put("97", "1");
    localHashMap.put("98", "1");
    localHashMap.put("9", "1");
    localHashMap.put("27", "1");
    localHashMap.put("99", "1");
    localHashMap.put("95", "1");
    localHashMap.put("8", "1");
    localHashMap.put("5", "1");
    localHashMap.put("90", "1");
    localHashMap.put("6", "1");
    localHashMap.put("91", "1");
    localHashMap.put("94", "1");
    localHashMap.put("101", "1");
    localHashMap.put("210", "1");
    localHashMap.put("3", "1");
    localHashMap.put("278", "1");
    localHashMap.put("31", "1");
    localHashMap.put("46", "1");
    localHashMap.put("39", "1");
    localHashMap.put("279", "1");
    localHashMap.put("351", "1");
    localHashMap.put("352", "1");
    localHashMap.put("178", "1");
    localHashMap.put("251", "1");
    localHashMap.put("50", "1");
    localHashMap.put("10", "1");
    localHashMap.put("28", "1");
    localHashMap.put("160", "1");
    localHashMap.put("261", "1");
    localHashMap.put("20", "1");
    localHashMap.put("850", "2");
    localHashMap.put("853", "2");
    localHashMap.put("852", "2");
    localHashMap.put("851", "2");
    localHashMap.put("854", "260");
    localHashMap.put("865", "2");
    localHashMap.put("866", "2");
    localHashMap.put("864", "2");
    localHashMap.put("862", "1");
    localHashMap.put("868", "2");
    localHashMap.put("992", "2");
    localHashMap.put("867", "2");
    localHashMap.put("860", "4");
    localHashMap.put("861", "2");
    localHashMap.put("863", "4");

    return localHashMap;
  }
}