package oracle.net.jdbc.nl.mesg;

import java.util.ListResourceBundle;

public class NLSR_pt_BR extends ListResourceBundle
{
  static final Object[][] contents = { { "NoFile-04600", "TNS-04600: Arquivo não encontrado: {0}" }, { "FileReadError-04601", "TNS-04601: Erro ao ler o arquivo de parâmetros: {0}, o erro é: {1}" }, { "SyntaxError-04602", "TNS-04602: Erro de sintaxe inválido: Esperava \"{0}\" antes ou em {1}" }, { "UnexpectedChar-04603", "TNS-04603: Erro de sintaxe inválida: Caractere inesperado \"{0}\" durante o parse {1}" }, { "ParseError-04604", "TNS-04604: Objeto de parse não inicializado" }, { "UnexpectedChar-04605", "TNS-04605: Erro de sintaxe inválido: Caractere ou LITERAL inesperado\"{0}\" antes de ou em {1}" }, { "NoLiterals-04610", "TNS-04610: Não há literais; chegou ao fim do par NV" }, { "InvalidChar-04611", "TNS-04611: Caractere de continuação inválido após o Comentário" }, { "NullRHS-04612", "TNS-04612: RHS nulo para \"{0}\"" }, { "Internal-04613", "TNS-04613: Erro interno: {0}" }, { "NoNVPair-04614", "TNS-04614: Par NV {0} não encontrado" }, { "InvalidRHS-04615", "TNS-04615: RHS inválido para {0}" } };

  public Object[][] getContents()
  {
    return contents;
  }
}