package oracle.net.jdbc.nl.mesg;

import java.util.ListResourceBundle;

public class NLSR_no extends ListResourceBundle
{
  static final Object[][] contents = { { "NoFile-04600", "TNS-04600: Fant ikke fil: {0}" }, { "FileReadError-04601", "TNS-04601: Feil ved lesing av parameterfilen: {0}, feilen er: {1}" }, { "SyntaxError-04602", "TNS-04602: Ugyldig syntaks: Forventet {0} før eller på {1}" }, { "UnexpectedChar-04603", "TNS-04603: Ugyldig syntaks: Uventet tegn {0} ved analyse av {1}" }, { "ParseError-04604", "TNS-04604: Analyseobjekt ikke initialisert" }, { "UnexpectedChar-04605", "TNS-04605: Ugyldig syntaks: Uventet CHAR (tegn) eller LITERAL (strengkonstant) {0} før eller på {1}" }, { "NoLiterals-04610", "TNS-04610: Ingen flere strengkonstanter. Enden av NV-par nådd" }, { "InvalidChar-04611", "TNS-04611: Ugyldig fortsettelsestegn etter kommentar" }, { "NullRHS-04612", "TNS-04612: Null RHS for {0}" }, { "Internal-04613", "TNS-04613: Intern feil: {0}" }, { "NoNVPair-04614", "TNS-04614: Fant ikke NV-par {0}" }, { "InvalidRHS-04615", "TNS-04615: Ugyldig RHS for {0}" } };

  public Object[][] getContents()
  {
    return contents;
  }
}