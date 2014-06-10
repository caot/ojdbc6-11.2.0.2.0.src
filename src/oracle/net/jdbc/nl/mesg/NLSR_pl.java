package oracle.net.jdbc.nl.mesg;

import java.util.ListResourceBundle;

public class NLSR_pl extends ListResourceBundle
{
  static final Object[][] contents = { { "NoFile-04600", "TNS-04600: Nie znaleziono pliku: {0}" }, { "FileReadError-04601", "TNS-04601: Błąd podczas odczytywania pliku parametrów: {0}; błąd: {1}" }, { "SyntaxError-04602", "TNS-04602: Błąd niepoprawnej składni: Oczekiwano \"{0}\" przed lub w {1}" }, { "UnexpectedChar-04603", "TNS-04603: Błąd niepoprawnej składni: Nieoczekiwana wartość znakowa \"{0}\" podczas analizy składniowej {1}" }, { "ParseError-04604", "TNS-04604: Nie zainicjalizowano analizy składniowej obiektu" }, { "UnexpectedChar-04605", "TNS-04605: Błąd niepoprawnej składni: Nieoczekiwana wartość znakowa lub literał \"{0}\" przed lub w {1}" }, { "NoLiterals-04610", "TNS-04610: Nie pozostawiono żadnych literałów, osiągnięto koniec pary nazwa-wartość" }, { "InvalidChar-04611", "TNS-04611: Niepoprawny znak kontynuacji po komentarzu" }, { "NullRHS-04612", "TNS-04612: Prawa strona równania ma wartość Null dla \"{0}\"" }, { "Internal-04613", "TNS-04613: Błąd wewnętrzny: {0}" }, { "NoNVPair-04614", "TNS-04614: Nie znaleziono pary nazwa-wartość {0}" }, { "InvalidRHS-04615", "TNS-04615: Niepoprawna prawa strona równania {0}" } };

  public Object[][] getContents()
  {
    return contents;
  }
}