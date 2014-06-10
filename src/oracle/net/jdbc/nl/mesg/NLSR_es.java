package oracle.net.jdbc.nl.mesg;

import java.util.ListResourceBundle;

public class NLSR_es extends ListResourceBundle
{
  static final Object[][] contents = { { "NoFile-04600", "TNS-04600: No se ha encontrado el archivo: {0}" }, { "FileReadError-04601", "TNS-04601: Error al leer el archivo de parámetros: {0}, el error es: {1}" }, { "SyntaxError-04602", "TNS-04602: Error de sintaxis no válida: Se esperaba \"{0}\" antes o en {1}" }, { "UnexpectedChar-04603", "TNS-04603: Error de sintaxis no válida: Carácter inesperado \"{0}\" al analizar {1}" }, { "ParseError-04604", "TNS-04604: No se ha inicializado el objeto de análisis" }, { "UnexpectedChar-04605", "TNS-04605: Error de sintaxis no válida: Carácter o LITERAL inesperado \"{0}\" antes o en {1}" }, { "NoLiterals-04610", "TNS-04610: No quedan literales, se ha alcanzado el final del par NV" }, { "InvalidChar-04611", "TNS-04611: Carácter de continuación no válido después del comentario" }, { "NullRHS-04612", "TNS-04612: RHS nulo para \"{0}\"" }, { "Internal-04613", "TNS-04613: Error interno: {0}" }, { "NoNVPair-04614", "TNS-04614: No se ha encontrado el par NV {0}" }, { "InvalidRHS-04615", "TNS-04615: RHS no válido para {0}" } };

  public Object[][] getContents()
  {
    return contents;
  }
}