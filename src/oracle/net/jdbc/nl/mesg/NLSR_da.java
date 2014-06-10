package oracle.net.jdbc.nl.mesg;

import java.util.ListResourceBundle;

public class NLSR_da extends ListResourceBundle
{
  static final Object[][] contents = { { "NoFile-04600", "TNS-04600: Filen blev ikke fundet: {0}" }, { "FileReadError-04601", "TNS-0460: Fejl under læsning af parameterfilen: {0}, fejlen er: {1}" }, { "SyntaxError-04602", "TNS-04602: Ugyldig syntaks-fejl: Forventede \"{0}\" før eller ved {1}" }, { "UnexpectedChar-04603", "TNS-04603: Ugyldig syntaks-fejl: Uventet char \"{0}\" under analyse af {1}" }, { "ParseError-04604", "TNS-04604: Analyseobjekt er ikke analyseret" }, { "UnexpectedChar-04605", "TNS-04605: Ugyldig syntaks-fejl: Uventet char eller LITERAL \"{0}\" før eller ved {1}" }, { "NoLiterals-04610", "TNS-04610: Ingen litteraler tilbage, har nået slutningen af NV-par" }, { "InvalidChar-04611", "TNS-0461: Ugyldigt fortsættelsestegn efter kommentar" }, { "NullRHS-04612", "TNS-04612: NULL-RHS for \"{0}\"" }, { "Internal-04613", "TNS-04613: Intern fejl: {0}" }, { "NoNVPair-04614", "TNS-04614: NV-parret {0} blev ikke fundet" }, { "InvalidRHS-04615", "TNS-04615: Ugyldig RHS for {0}" } };

  public Object[][] getContents()
  {
    return contents;
  }
}