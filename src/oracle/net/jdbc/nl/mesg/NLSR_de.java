package oracle.net.jdbc.nl.mesg;

import java.util.ListResourceBundle;

public class NLSR_de extends ListResourceBundle
{
  static final Object[][] contents = { { "NoFile-04600", "TNS-04600: Datei nicht gefunden: {0}" }, { "FileReadError-04601", "TNS-04601: Fehler beim Lesen der Parameterdatei: {0}, Fehler ist: {1}" }, { "SyntaxError-04602", "TNS-04602: Ungültiger Syntaxfehler: \"{0}\" vor oder bei {1} erwartet" }, { "UnexpectedChar-04603", "TNS-04603: Ungültiger Syntaxfehler: Unerwartetes Zeichen \"{0}\" beim Parsen {1}" }, { "ParseError-04604", "TNS-04604: Parse-Objekt nicht initialisiert" }, { "UnexpectedChar-04605", "TNS-04605: Ungültiger Syntaxfehler: Unerwartetes Zeichen oder LITERAL \"{0}\" vor oder bei {1}" }, { "NoLiterals-04610", "TNS-04610: Keine Literale mehr übrig, Ende von NV-Paar erreicht" }, { "InvalidChar-04611", "TNS-04611: Ungültiges Fortsetzungszeichen nach Kommentar" }, { "NullRHS-04612", "TNS-04612: Null-RHS für \"{0}\"" }, { "Internal-04613", "TNS-04613: Interner Fehler: {0}" }, { "NoNVPair-04614", "TNS-04614: NV-Paar {0} nicht gefunden" }, { "InvalidRHS-04615", "TNS-04615: RHS ungültig für {0}" } };

  public Object[][] getContents()
  {
    return contents;
  }
}