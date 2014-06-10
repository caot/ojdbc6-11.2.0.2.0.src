package oracle.net.jdbc.nl.mesg;

import java.util.ListResourceBundle;

public class NLSR_it extends ListResourceBundle
{
  static final Object[][] contents = { { "NoFile-04600", "TNS-04600: file non trovato: {0}" }, { "FileReadError-04601", "TNS-04601: errore durante la lettura del file dei parametri: {0}, l''errore è: {1}" }, { "SyntaxError-04602", "TNS-04602: errore di sintassi non valida: previsto \"{0}\" in o prima di {1}" }, { "UnexpectedChar-04603", "TNS-04603: errore di sintassi non valida: carattere non previsto \"{0}\" durante l''analisi {1}" }, { "ParseError-04604", "TNS-04604: oggetto di analisi non inizializzato" }, { "UnexpectedChar-04605", "TNS-04605: errore di sintassi non valida: carattere non previsto o LITERAL \"{0}\" in o prima di {1}" }, { "NoLiterals-04610", "TNS-04610: non sono più presenti valori, raggiunta la fine della coppia NV" }, { "InvalidChar-04611", "TNS-04611: carattere di continuazione non valido dopo Comment" }, { "NullRHS-04612", "TNS-04612: RHS nullo per \"{0}\"" }, { "Internal-04613", "TNS-04613: errore interno: {0}" }, { "NoNVPair-04614", "TNS-04614: coppia NV {0} non trovata" }, { "InvalidRHS-04615", "TNS-04615: RHS non valido per {0}" } };

  public Object[][] getContents()
  {
    return contents;
  }
}