package oracle.net.jdbc.nl.mesg;

import java.util.ListResourceBundle;

public class NLSR_pt extends ListResourceBundle
{
  static final Object[][] contents = { { "NoFile-04600", "TNS-04600: Ficheiro não encontrado: {0}" }, { "FileReadError-04601", "TNS-04601: Erro na leitura do ficheiro de parâmetros: {0}; o erro é: {1}" }, { "SyntaxError-04602", "TNS-04602: Erro de sintaxe inválida: Era esperado \"{0}\" antes de ou em {1}" }, { "UnexpectedChar-04603", "TNS-04603: Erro de sintaxe inválida: Carácter inesperado \"{0}\" na análise de {1}" }, { "ParseError-04604", "TNS-04604: Objecto de análise não inicializado" }, { "UnexpectedChar-04605", "TNS-04605: Erro de sintaxe inválida: LITERAL ou carácter inesperado \"{0}\" antes de ou em {1}" }, { "NoLiterals-04610", "TNS-04610: Não existem mais literais; foi atingido o fim do par NV" }, { "InvalidChar-04611", "TNS-04611: Carácter de continuação inválido após o Comentário" }, { "NullRHS-04612", "TNS-04612: RHS nula para \"{0}\"" }, { "Internal-04613", "TNS-04613: Erro interno: {0}" }, { "NoNVPair-04614", "TNS-04614: O par NV {0} não foi encontrado" }, { "InvalidRHS-04615", "TNS-04615: RHS inválida para {0}" } };

  public Object[][] getContents()
  {
    return contents;
  }
}