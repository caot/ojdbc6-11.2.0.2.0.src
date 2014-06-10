package oracle.net.jdbc.nl.mesg;

import java.util.ListResourceBundle;

public class NLSR_fi extends ListResourceBundle
{
  static final Object[][] contents = { { "NoFile-04600", "TNS-04600: Tiedostoa ei löydy: {0}" }, { "FileReadError-04601", "TNS-04601: Virhe luettaessa parametritiedostoa: {0}, virhe: {1}" }, { "SyntaxError-04602", "TNS-04602: Virheellinen syntaksi: Odotettiin kohdetta \"{0}\" kohteessa {1} tai ennen sitä" }, { "UnexpectedChar-04603", "TNS-04603: Virheellinen syntaksi: Odottamaton merkki \"{0}\" jäsennettäessä kohdetta {1}" }, { "ParseError-04604", "TNS-04604: Jäsennysobjektia ei ole alustettu" }, { "UnexpectedChar-04605", "TNS-04605: Virheellinen syntaksi: Odottamaton merkki tai LITERAL \"{0}\" kohteessa {1} tai ennen sitä" }, { "NoLiterals-04610", "TNS-04610: Literaaleja ei ole jäljellä, NV-parin loppu on saavutettu" }, { "InvalidChar-04611", "TNS-04611: Virheellinen jatkomerkki kommentin jälkeen" }, { "NullRHS-04612", "TNS-04612: Tyhjä RHS kohteessa \"{0}\"" }, { "Internal-04613", "TNS-04613: Sisäinen virhe: {0}" }, { "NoNVPair-04614", "TNS-04614: NV-paria {0} ei löydy" }, { "InvalidRHS-04615", "TNS-04615: Virheellinen RHS kohteessa {0}" } };

  public Object[][] getContents()
  {
    return contents;
  }
}