package oracle.net.jdbc.nl.mesg;

import java.util.ListResourceBundle;

public class NLSR extends ListResourceBundle
{
  static final Object[][] contents = { { "NoFile-04600", "TNS-04600: File not found: {0}" }, { "FileReadError-04601", "TNS-04601: Error while reading the parameter file: {0}, error is: {1}" }, { "SyntaxError-04602", "TNS-04602: Invalid syntax error: Expected \"{0}\" before or at {1}" }, { "UnexpectedChar-04603", "TNS-04603: Invalid syntax error: Unexpected char \"{0}\" while parsing {1}" }, { "ParseError-04604", "TNS-04604: Parse object not initialized" }, { "UnexpectedChar-04605", "TNS-04605: Invalid syntax error: Unexpected char or LITERAL \"{0}\" before or at {1}" }, { "NoLiterals-04610", "TNS-04610: No literals left, reached end of NV pair" }, { "InvalidChar-04611", "TNS-04611: Invalid continuation character after Comment" }, { "NullRHS-04612", "TNS-04612: Null RHS for \"{0}\"" }, { "Internal-04613", "TNS-04613: Internal error: {0}" }, { "NoNVPair-04614", "TNS-04614: NV Pair {0} not found" }, { "InvalidRHS-04615", "TNS-04615: Invalid RHS for {0}" } };

  public Object[][] getContents()
  {
    return contents;
  }
}