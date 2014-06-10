package oracle.net.jdbc.nl;

import java.io.PrintStream;
import java.util.Vector;

public final class NVTokens
{
  public static final int TKN_NONE = 0;
  public static final int TKN_LPAREN = 1;
  public static final int TKN_RPAREN = 2;
  public static final int TKN_COMMA = 3;
  public static final int TKN_EQUAL = 4;
  public static final int TKN_LITERAL = 8;
  public static final int TKN_EOS = 9;
  private static final char TKN_LPAREN_VALUE = '(';
  private static final char TKN_RPAREN_VALUE = ')';
  private static final char TKN_COMMA_VALUE = ',';
  private static final char TKN_EQUAL_VALUE = '=';
  private static final char TKN_BKSLASH_VALUE = '\\';
  private static final char TKN_DQUOTE_VALUE = '"';
  private static final char TKN_SQUOTE_VALUE = '\'';
  private static final char TKN_EOS_VALUE = '%';
  private static final char TKN_SPC_VALUE = ' ';
  private static final char TKN_TAB_VALUE = '\t';
  private static final char TKN_LF_VALUE = '\n';
  private static final char TKN_CR_VALUE = '\r';
  private Vector _tkType = null;
  private Vector _tkValue = null;
  private int _numTokens = 0;
  private int _tkPos = 0;

  private static boolean _isWhiteSpace(char paramChar)
  {
    return (paramChar == ' ') || (paramChar == '\t') || (paramChar == '\n') || (paramChar == '\r');
  }

  private static String _trimWhiteSpace(String paramString)
  {
    int i = paramString.length();
    int j = 0;
    int k = i;
    while ((j < i) && (_isWhiteSpace(paramString.charAt(j))))
      j++;
    while ((j < k) && (_isWhiteSpace(paramString.charAt(k - 1))))
      k--;
    return paramString.substring(j, k);
  }

  public boolean parseTokens(String paramString)
  {
    this._numTokens = 0;
    this._tkPos = 0;
    this._tkType = new Vector(25, 25);
    this._tkValue = new Vector(25, 25);
    int i = paramString.length();
    char[] arrayOfChar = paramString.toCharArray();
    int j = 0;
    while (j < i)
    {
      while ((j < i) && (_isWhiteSpace(arrayOfChar[j])))
        j++;
      if (j < i)
        switch (arrayOfChar[j])
        {
        case '(':
          _addToken(1, '(');
          j++;
          break;
        case '=':
          _addToken(4, '=');
          j++;
          break;
        case ')':
          _addToken(2, ')');
          j++;
          break;
        case ',':
          _addToken(3, ',');
          j++;
          break;
        default:
          int k = j;
          int m = -1;
          int n = 0;
          int i1 = 34;
          if ((arrayOfChar[j] == '\'') || (arrayOfChar[j] == '"'))
          {
            n = 1;
            i1 = arrayOfChar[j];
            j++;
          }
          while (j < i)
            if (arrayOfChar[j] == '\\')
            {
              j += 2;
            }
            else
            {
              if (n != 0)
              {
                if (arrayOfChar[j] == i1)
                {
                  j++;
                  m = j;
                  break;
                }
              }
              else if ((arrayOfChar[j] == '(') || (arrayOfChar[j] == ')') || (arrayOfChar[j] == ',') || (arrayOfChar[j] == '='))
              {
                m = j;
                break;
              }
              j++;
            }
          if (m == -1)
            m = j;
          _addToken(8, _trimWhiteSpace(paramString.substring(k, m)));
        }
    }
    _addToken(9, '%');
    return true;
  }

  public int getToken()
    throws NLException
  {
    if (this._tkType == null)
      throw new UninitializedObjectException("ParseError-04604", "");
    if (this._tkPos < this._numTokens)
      return ((Integer)this._tkType.elementAt(this._tkPos)).intValue();
    throw new NLException("NoLiterals-04610", "");
  }

  public int popToken()
    throws UninitializedObjectException, NLException
  {
    int i = 0;
    if (this._tkType == null)
      throw new UninitializedObjectException("ParseError-04604", "");
    if (this._tkPos < this._numTokens)
      i = ((Integer)this._tkType.elementAt(this._tkPos++)).intValue();
    else
      throw new NLException("NoLiterals-04610", "");
    return i;
  }

  public String getLiteral()
    throws NLException
  {
    String str = null;
    if (this._tkValue == null)
      throw new UninitializedObjectException("ParseError-04604", "");
    if (this._tkPos < this._numTokens)
      str = (String)this._tkValue.elementAt(this._tkPos);
    else
      throw new NLException("NoLiterals-04610", "");
    return str;
  }

  public String popLiteral()
    throws NLException
  {
    String str = null;
    if (this._tkValue == null)
      throw new UninitializedObjectException("ParseError-04604", "");
    if (this._tkPos < this._numTokens)
      str = (String)this._tkValue.elementAt(this._tkPos++);
    else
      throw new NLException("NoLiterals-04610", "");
    return str;
  }

  public void eatToken()
  {
    if (this._tkPos < this._numTokens)
      this._tkPos += 1;
  }

  public String toString()
  {
    if (this._tkType == null)
      return "*NO TOKENS*";
    String str = "Tokens";
    for (int i = 0; i < this._numTokens; i++)
      str = str + " : " + this._tkValue.elementAt(i);
    return str;
  }

  public void println()
  {
    System.out.println(toString());
  }

  private void _addToken(int paramInt, char paramChar)
  {
    _addToken(paramInt, String.valueOf(paramChar));
  }

  private void _addToken(int paramInt, String paramString)
  {
    this._tkType.addElement(new Integer(paramInt));
    this._tkValue.addElement(paramString);
    this._numTokens += 1;
  }
}