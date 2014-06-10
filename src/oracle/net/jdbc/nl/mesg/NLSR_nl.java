package oracle.net.jdbc.nl.mesg;

import java.util.ListResourceBundle;

public class NLSR_nl extends ListResourceBundle
{
  static final Object[][] contents = { { "NoFile-04600", "TNS-04600: bestand niet gevonden: {0}" }, { "FileReadError-04601", "TNS-04601: fout bij het lezen van het parameterbestand: {0}, fout is: {1}" }, { "SyntaxError-04602", "TNS-04602: fout wegens ongeldige syntaxis: \"{0}\" werd verwacht voor of bij {1}" }, { "UnexpectedChar-04603", "TNS-04603: fout wegens ongeldige syntaxis: onverwacht teken \"{0}\" bij ontleden van {1}" }, { "ParseError-04604", "TNS-04604: ontleedobject niet geïnitialiseerd" }, { "UnexpectedChar-04605", "TNS-04605: fout wegens ongeldige syntaxis: onverwacht teken of tekstconstante \"{0}\" voor of bij {1}" }, { "NoLiterals-04610", "TNS-04610: er zijn geen tekstconstanten over; het einde van het NV-paar is bereikt" }, { "InvalidChar-04611", "TNS-04611: ongeldig vervolgteken na toelichting" }, { "NullRHS-04612", "TNS-04612: NULL RHS voor \"{0}\"" }, { "Internal-04613", "TNS-04613: interne fout: {0}" }, { "NoNVPair-04614", "TNS-04614: NV-paar {0} niet gevonden" }, { "InvalidRHS-04615", "TNS-04615: ongeldige RHS voor {0}" } };

  public Object[][] getContents()
  {
    return contents;
  }
}