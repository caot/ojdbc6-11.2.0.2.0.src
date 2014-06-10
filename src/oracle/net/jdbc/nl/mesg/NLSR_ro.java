package oracle.net.jdbc.nl.mesg;

import java.util.ListResourceBundle;

public class NLSR_ro extends ListResourceBundle
{
  static final Object[][] contents = { { "NoFile-04600", "TNS-04600: Fişierul nu a fost găsit: {0}" }, { "FileReadError-04601", "TNS-04601: Eroare la citirea fişierului de parametri: {0}, eroarea este: {1}" }, { "SyntaxError-04602", "TNS-04602: Eroare de sintaxă nevalidă: Se aştepta \"{0}\" înainte de sau la {1}" }, { "UnexpectedChar-04603", "TNS-04603: Eroare de sintaxă nevalidă: Caracter neaşteptat \"{0}\" la interpretarea {1}" }, { "ParseError-04604", "TNS-04604: Obiectul de interpretat nu a fost iniţializat" }, { "UnexpectedChar-04605", "TNS-04605: Eroare de sintaxă nevalidă: Caracter neaşteptat sau caracter de tip LITERAL \"{0}\" înainte de sau la {1}" }, { "NoLiterals-04610", "TNS-04610: Nu există literale rămase, s-a ajuns la sfârşitul perechii NV" }, { "InvalidChar-04611", "TNS-04611: Caracter de continuare nevalid după comentariu" }, { "NullRHS-04612", "TNS-04612: Termenul din dreapta este nul pentru \"{0}\"" }, { "Internal-04613", "TNS-04613: Eroare internă: {0}" }, { "NoNVPair-04614", "TNS-04614: Perechea NV {0} nu a fost găsită" }, { "InvalidRHS-04615", "TNS-04615: Termenul din dreapta este nevalid pentru {0}" } };

  public Object[][] getContents()
  {
    return contents;
  }
}