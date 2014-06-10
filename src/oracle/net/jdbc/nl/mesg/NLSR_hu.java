package oracle.net.jdbc.nl.mesg;

import java.util.ListResourceBundle;

public class NLSR_hu extends ListResourceBundle
{
  static final Object[][] contents = { { "NoFile-04600", "TNS-04600: a fájl nem található: {0}" }, { "FileReadError-04601", "TNS-04601: hiba a(z) {0} paraméterfájl olvasása közben. Hiba: {1}" }, { "SyntaxError-04602", "TNS-04602: szintaktikai hiba:  \"{0}\" szükséges a(z) {1} helyen vagy előtte." }, { "UnexpectedChar-04603", "TNS-04603: szintaktikai hiba: váratlan \"{0}\" karakter {1} elemzése közben." }, { "ParseError-04604", "TNS-04604: az elemzési objektum inicializálása elmaradt." }, { "UnexpectedChar-04605", "TNS-04605: szintaktikai hiba: váratlan \"{0}\" karakter vagy LITERAL típus a(z) {1} helyen vagy előtte." }, { "NoLiterals-04610", "TNS-04610: Nincs több literál, NV Pair végére ért." }, { "InvalidChar-04611", "TNS-04611: a megjegyzés deklarálása után érvénytelen a folytatási karakter." }, { "NullRHS-04612", "TNS-04612: \"{0}\" RHS-értéke üres." }, { "Internal-04613", "TNS-04613: belső hiba: {0}" }, { "NoNVPair-04614", "TNS-04614: a(z) {0} NV Pair nem található." }, { "InvalidRHS-04615", "TNS-04615: {0} RHS-értéke érvénytelen." } };

  public Object[][] getContents()
  {
    return contents;
  }
}