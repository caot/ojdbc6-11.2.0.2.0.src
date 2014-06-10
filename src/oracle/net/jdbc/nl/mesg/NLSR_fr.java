package oracle.net.jdbc.nl.mesg;

import java.util.ListResourceBundle;

public class NLSR_fr extends ListResourceBundle
{
  static final Object[][] contents = { { "NoFile-04600", "TNS-04600 : Fichier introuvable : {0}" }, { "FileReadError-04601", "TNS-04601 : Erreur lors de la lecture du fichier de paramètres {0}. Erreur : {1}" }, { "SyntaxError-04602", "TNS-04602 : Erreur de syntaxe : \"{0}\" est attendu avant ou à {1}" }, { "UnexpectedChar-04603", "TNS-04603 : Erreur de syntaxe : caractère inattendu \"{0}\" détecté lors de l''analyse de {1}" }, { "ParseError-04604", "TNS-04604 : L'objet d'analyse n'est pas initialisé" }, { "UnexpectedChar-04605", "TNS-04605 : Erreur de syntaxe : le caractère ou le littéral \"{0}\" ne doit pas se trouver avant ou à {1}" }, { "NoLiterals-04610", "TNS-04610 : Il ne reste aucun littéral, la fin de la paire NV a été atteinte" }, { "InvalidChar-04611", "TNS-04611 : Caractère de suite non valide après un commentaire" }, { "NullRHS-04612", "TNS-04612 : RHS null pour \"{0}\"" }, { "Internal-04613", "TNS-04613 : Erreur interne : {0}" }, { "NoNVPair-04614", "TNS-04614 : Paire NV {0} introuvable" }, { "InvalidRHS-04615", "TNS-04615 : RHS non valide pour {0}" } };

  public Object[][] getContents()
  {
    return contents;
  }
}