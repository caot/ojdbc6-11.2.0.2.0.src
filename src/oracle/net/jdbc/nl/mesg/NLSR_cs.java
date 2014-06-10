package oracle.net.jdbc.nl.mesg;

import java.util.ListResourceBundle;

public class NLSR_cs extends ListResourceBundle
{
  static final Object[][] contents = { { "NoFile-04600", "TNS-04600: Soubor nebyl nalezen: {0}" }, { "FileReadError-04601", "TNS-04601: Chyba při čtení souboru s parametry: {0}, chyba: {1}" }, { "SyntaxError-04602", "TNS-04602: Chyba neplatné syntaxe: Byo očekáváno \"{0}\" před nebo na {1}" }, { "UnexpectedChar-04603", "TNS-04603: Chyba neplatné syntaxe: Neočekávaný znak \"{0}\" při syntaktické analýze {1}" }, { "ParseError-04604", "TNS-04604: Objekt syntaktické analýzy nebyl inicializován" }, { "UnexpectedChar-04605", "TNS-04605: Chyba neplatné syntaxe: Neočekávaný znak nebo LITERAL \"{0}\" před nebo na {1}" }, { "NoLiterals-04610", "TNS-04610: Nezbyly žádné literály, bylo dosaženo konce páru NV" }, { "InvalidChar-04611", "TNS-04611: Neplatný pokračovací znak za komentářem" }, { "NullRHS-04612", "TNS-04612: RHS s hodnotou null pro \"{0}\"" }, { "Internal-04613", "TNS-04613: Vnitřní chyba: {0}" }, { "NoNVPair-04614", "TNS-04614: Pár NV {0} nebyl nalezen" }, { "InvalidRHS-04615", "TNS-04615: Neplatné RHS pro {0}" } };

  public Object[][] getContents()
  {
    return contents;
  }
}