package oracle.net.jdbc.nl.mesg;

import java.util.ListResourceBundle;

public class NLSR_sk extends ListResourceBundle
{
  static final Object[][] contents = { { "NoFile-04600", "TNS-04600: Súbor sa nenašiel: {0}" }, { "FileReadError-04601", "TNS-04601: Chyba počas čítania súboru parametrov: {0}, chyba je: {1}" }, { "SyntaxError-04602", "TNS-04602: Chyba neplatnej syntaxe: Očakáva sa, že  \"{0}\" sa bude nachádzať na mieste {1} alebo pred ním" }, { "UnexpectedChar-04603", "TNS-04603: Chyba neplatnej syntaxe: Neočakávaný znak \"{0}\" počas analýzy {1}" }, { "ParseError-04604", "TNS-04604: Analýza objektu nie je inicializovaná" }, { "UnexpectedChar-04605", "TNS-04605: Chyba neplatnej syntaxe: Neočakávaný znak alebo LITERAL \"{0}\" na mieste {1} alebo pred ním" }, { "NoLiterals-04610", "TNS-04610: Nezostali žiadne literály, bol dosiahnutý koniec NV páru" }, { "InvalidChar-04611", "TNS-04611: Neplatný pokračovací znak za poznámkou" }, { "NullRHS-04612", "TNS-04612: Nulové RHS pre \"{0}\"" }, { "Internal-04613", "TNS-04613: Interná chyba: {0}" }, { "NoNVPair-04614", "TNS-04614: NV pár {0} sa nenašiel" }, { "InvalidRHS-04615", "TNS-04615: Neplatné RHS pre {0}" } };

  public Object[][] getContents()
  {
    return contents;
  }
}