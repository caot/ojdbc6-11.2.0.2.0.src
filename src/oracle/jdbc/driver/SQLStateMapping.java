package oracle.jdbc.driver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Reader;
import java.sql.SQLClientInfoException;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.SQLInvalidAuthorizationSpecException;
import java.sql.SQLNonTransientConnectionException;
import java.sql.SQLNonTransientException;
import java.sql.SQLRecoverableException;
import java.sql.SQLSyntaxErrorException;
import java.sql.SQLTimeoutException;
import java.sql.SQLTransactionRollbackException;
import java.sql.SQLTransientConnectionException;
import java.sql.SQLTransientException;
import java.util.ArrayList;
import java.util.List;

class SQLStateMapping
{
  public static final int SQLEXCEPTION = 0;
  public static final int SQLNONTRANSIENTEXCEPTION = 1;
  public static final int SQLTRANSIENTEXCEPTION = 2;
  public static final int SQLDATAEXCEPTION = 3;
  public static final int SQLFEATURENOTSUPPORTEDEXCEPTION = 4;
  public static final int SQLINTEGRITYCONSTRAINTVIOLATIONEXCEPTION = 5;
  public static final int SQLINVALIDAUTHORIZATIONSPECEXCEPTION = 6;
  public static final int SQLNONTRANSIENTCONNECTIONEXCEPTION = 7;
  public static final int SQLSYNTAXERROREXCEPTION = 8;
  public static final int SQLTIMEOUTEXCEPTION = 9;
  public static final int SQLTRANSACTIONROLLBACKEXCEPTION = 10;
  public static final int SQLTRANSIENTCONNECTIONEXCEPTION = 11;
  public static final int SQLCLIENTINFOEXCEPTION = 12;
  public static final int SQLRECOVERABLEEXCEPTION = 13;
  int low;
  int high;
  public String sqlState;
  public int exception;
  static final String mappingResource = "errorMap.xml";
  static SQLStateMapping[] all;
  private static final int NUMEBER_OF_MAPPINGS_IN_ERRORMAP_XML = 128;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  public SQLStateMapping(int paramInt1, int paramInt2, String paramString, int paramInt3)
  {
    this.low = paramInt1;
    this.sqlState = paramString;
    this.exception = paramInt3;
    this.high = paramInt2;
  }

  public boolean isIncluded(int paramInt)
  {
    return (this.low <= paramInt) && (paramInt <= this.high);
  }

  public SQLException newSQLException(String paramString, int paramInt)
  {
    switch (this.exception) {
    case 0:
      return new SQLException(paramString, this.sqlState, paramInt);
    case 1:
      return new SQLNonTransientException(paramString, this.sqlState, paramInt);
    case 2:
      return new SQLTransientException(paramString, this.sqlState, paramInt);
    case 3:
      return new SQLDataException(paramString, this.sqlState, paramInt);
    case 4:
      return new SQLFeatureNotSupportedException(paramString, this.sqlState, paramInt);
    case 5:
      return new SQLIntegrityConstraintViolationException(paramString, this.sqlState, paramInt);
    case 6:
      return new SQLInvalidAuthorizationSpecException(paramString, this.sqlState, paramInt);
    case 7:
      return new SQLNonTransientConnectionException(paramString, this.sqlState, paramInt);
    case 8:
      return new SQLSyntaxErrorException(paramString, this.sqlState, paramInt);
    case 9:
      return new SQLTimeoutException(paramString, this.sqlState, paramInt);
    case 10:
      return new SQLTransactionRollbackException(paramString, this.sqlState, paramInt);
    case 11:
      return new SQLTransientConnectionException(paramString, this.sqlState, paramInt);
    case 12:
      return new SQLClientInfoException(paramString, this.sqlState, paramInt, null);
    case 13:
      return new SQLRecoverableException(paramString, this.sqlState, paramInt);
    }
    return new SQLException(paramString, this.sqlState, paramInt);
  }

  boolean lessThan(SQLStateMapping paramSQLStateMapping)
  {
    if (this.low < paramSQLStateMapping.low) {
      return this.high < paramSQLStateMapping.high;
    }

    return this.high <= paramSQLStateMapping.high;
  }

  public String toString()
  {
    return super.toString() + "(" + this.low + ", " + this.high + ", " + this.sqlState + ", " + this.exception + ")";
  }

  public static void main(String[] paramArrayOfString)
    throws IOException
  {
    SQLStateMapping[] arrayOfSQLStateMapping = doGetMappings();
    System.out.println("a\t" + arrayOfSQLStateMapping);
    for (int i = 0; i < arrayOfSQLStateMapping.length; i++)
      System.out.println("low:\t" + arrayOfSQLStateMapping[i].low + "\thigh:\t" + arrayOfSQLStateMapping[i].high + "\tsqlState:\t" + arrayOfSQLStateMapping[i].sqlState + "\tsqlException:\t" + arrayOfSQLStateMapping[i].exception);
  }

  static SQLStateMapping[] getMappings()
  {
    if (all == null) {
      try {
        all = doGetMappings();
      }
      catch (Throwable localThrowable)
      {
        all = new SQLStateMapping[0];
      }
    }
    return all;
  }

  static SQLStateMapping[] doGetMappings()
    throws IOException
  {
    InputStream localInputStream = SQLStateMapping.class.getResourceAsStream("errorMap.xml");
    ArrayList localArrayList = new ArrayList(128);
    load(localInputStream, localArrayList);
    return (SQLStateMapping[])localArrayList.toArray(new SQLStateMapping[0]);
  }

  static void load(InputStream paramInputStream, List paramList)
    throws IOException
  {
    BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(paramInputStream));
    Tokenizer localTokenizer = new Tokenizer(localBufferedReader);
    int i = -1;
    int j = -1;
    Object localObject1 = null;
    int k = -1;
    Object localObject2 = null;
    int m = 0;
    String str;
    while ((str = localTokenizer.next()) != null)
      switch (m) {
      case 0:
        if (str.equals("<")) m = 1; break;
      case 1:
        if (str.equals("!")) m = 2;
        else if (str.equals("oraErrorSqlStateSqlExceptionMapping")) m = 6; else {
          throw new IOException("Unexpected token \"" + str + "\" at line " + localTokenizer.lineno + " of errorMap.xml. Expected \"oraErrorSqlStateSqlExceptionMapping\".");
        }
        break;
      case 2:
        if (str.equals("-")) m = 3; else {
          throw new IOException("Unexpected token \"" + str + "\" at line " + localTokenizer.lineno + " of errorMap.xml. Expected \"-\".");
        }
        break;
      case 3:
        if (str.equals("-")) m = 4; break;
      case 4:
        if (str.equals("-")) m = 5; else
          m = 3;
        break;
      case 5:
        if (str.equals(">")) m = 1; else {
          throw new IOException("Unexpected token \"" + str + "\" at line " + localTokenizer.lineno + " of errorMap.xml. Expected \">\".");
        }
        break;
      case 6:
        if (str.equals(">")) m = 7; else {
          throw new IOException("Unexpected token \"" + str + "\" at line " + localTokenizer.lineno + " of errorMap.xml. Expected \">\".");
        }
        break;
      case 7:
        if (str.equals("<")) m = 8; else {
          throw new IOException("Unexpected token \"" + str + "\" at line " + localTokenizer.lineno + " of errorMap.xml. Expected \"<\".");
        }
        break;
      case 8:
        if (str.equals("!")) m = 9;
        else if (str.equals("error")) m = 14;
        else if (str.equals("/")) m = 16; else {
          throw new IOException("Unexpected token \"" + str + "\" at line " + localTokenizer.lineno + " of errorMap.xml. Expected one of \"!--\", \"error\", \"/\".");
        }
        break;
      case 9:
        if (str.equals("-")) m = 10; else {
          throw new IOException("Unexpected token \"" + str + "\" at line " + localTokenizer.lineno + " of errorMap.xml. Expected \"-\".");
        }
        break;
      case 10:
        if (str.equals("-")) m = 11; else {
          throw new IOException("Unexpected token \"" + str + "\" at line " + localTokenizer.lineno + " of errorMap.xml. Expected \"-\".");
        }
        break;
      case 11:
        if (str.equals("-")) m = 12; break;
      case 12:
        if (str.equals("-")) m = 13; else
          m = 11;
        break;
      case 13:
        if (str.equals(">")) m = 7; else {
          throw new IOException("Unexpected token \"" + str + "\" at line " + localTokenizer.lineno + " of errorMap.xml. Expected \">\".");
        }
        break;
      case 14:
        if (str.equals("/")) m = 15;
        else if (str.equals("oraErrorFrom")) m = 19;
        else if (str.equals("oraErrorTo")) m = 21;
        else if (str.equals("sqlState")) m = 23;
        else if (str.equals("sqlException")) m = 25;
        else if (str.equals("comment")) m = 27; else {
          throw new IOException("Unexpected token \"" + str + "\" at line " + localTokenizer.lineno + " of errorMap.xml. Expected one of " + "\"oraErrorFrom\", \"oraErrorTo\", \"sqlState\", " + "\"sqlException\", \"comment\", \"/\".");
        }

        break;
      case 15:
        if (str.equals(">")) {
          try {
            createOne(paramList, i, j, (String)localObject1, k, (String)localObject2);
          }
          catch (IOException localIOException) {
            throw new IOException("Invalid error element at line " + localTokenizer.lineno + " of errorMap.xml. " + localIOException.getMessage());
          }

          i = -1;
          j = -1;
          localObject1 = null;
          k = -1;
          localObject2 = null;
          m = 7;
        } else {
          throw new IOException("Unexpected token \"" + str + "\" at line " + localTokenizer.lineno + " of errorMap.xml. Expected \">\".");
        }
        break;
      case 16:
        if (str.equals("oraErrorSqlStateSqlExceptionMapping")) m = 17; else {
          throw new IOException("Unexpected token \"" + str + "\" at line " + localTokenizer.lineno + " of errorMap.xml. Expected \"oraErrorSqlStateSqlExceptionMapping\".");
        }
        break;
      case 17:
        if (str.equals(">")) m = 18; else {
          throw new IOException("Unexpected token \"" + str + "\" at line " + localTokenizer.lineno + " of errorMap.xml. Expected \">\".");
        }
        break;
      case 18:
        break;
      case 19:
        if (str.equals("=")) m = 20; else
          throw new IOException("Unexpected token \"" + str + "\" at line " + localTokenizer.lineno + " of errorMap.xml. Expected \"=\".");
        break;
      case 20:
        try
        {
          i = Integer.parseInt(str);
        }
        catch (NumberFormatException localNumberFormatException1) {
          throw new IOException("Unexpected value \"" + str + "\" at line " + localTokenizer.lineno + " of errorMap.xml. Expected an integer.");
        }

        m = 14;
        break;
      case 21:
        if (str.equals("=")) m = 22; else
          throw new IOException("Unexpected token \"" + str + "\" at line " + localTokenizer.lineno + " of errorMap.xml. Expected \"=\".");
        break;
      case 22:
        try
        {
          j = Integer.parseInt(str);
        }
        catch (NumberFormatException localNumberFormatException2) {
          throw new IOException("Unexpected value \"" + str + "\" at line " + localTokenizer.lineno + " of errorMap.xml. Expected an integer.");
        }

        m = 14;
        break;
      case 23:
        if (str.equals("=")) m = 24; else {
          throw new IOException("Unexpected token \"" + str + "\" at line " + localTokenizer.lineno + " of errorMap.xml. Expected \"=\".");
        }
        break;
      case 24:
        localObject1 = str;
        m = 14;
        break;
      case 25:
        if (str.equals("=")) m = 26; else
          throw new IOException("Unexpected token \"" + str + "\" at line " + localTokenizer.lineno + " of errorMap.xml. Expected \"=\".");
        break;
      case 26:
        try
        {
          k = valueOf(str);
        }
        catch (Exception localException) {
          throw new IOException("Unexpected token \"" + str + "\" at line " + localTokenizer.lineno + " of errorMap.xml. Expected SQLException" + " subclass name.");
        }

        m = 14;
        break;
      case 27:
        if (str.equals("=")) m = 28; else {
          throw new IOException("Unexpected token \"" + str + "\" at line " + localTokenizer.lineno + " of errorMap.xml. Expected \"=\".");
        }
        break;
      case 28:
        localObject2 = str;
        m = 14;
        break;
      default:
        throw new IOException("Unknown parser state " + m + " at line " + localTokenizer.lineno + " of errorMap.xml.");
      }
  }

  private static void createOne(List paramList, int paramInt1, int paramInt2, String paramString1, int paramInt3, String paramString2)
    throws IOException
  {
    if (paramInt1 == -1) throw new IOException("oraErrorFrom is a required attribute");
    if (paramInt2 == -1) paramInt2 = paramInt1;
    if ((paramString1 == null) || (paramString1.length() == 0)) throw new IOException("sqlState is a required attribute");
    if (paramInt3 == -1) throw new IOException("sqlException is a required attribute");
    if ((paramString2 == null) || (paramString2.length() < 8)) throw new IOException("a lengthy comment in required");
    SQLStateMapping localSQLStateMapping = new SQLStateMapping(paramInt1, paramInt2, paramString1, paramInt3);
    add(paramList, localSQLStateMapping);
  }

  static void add(List paramList, SQLStateMapping paramSQLStateMapping) {
    int i = paramList.size();
    while ((i > 0) && 
      (!((SQLStateMapping)paramList.get(i - 1)).lessThan(paramSQLStateMapping))) {
      i--;
    }

    paramList.add(i, paramSQLStateMapping);
  }

  static int valueOf(String paramString) throws Exception
  {
    if (paramString.equalsIgnoreCase("SQLEXCEPTION")) return 0;
    if (paramString.equalsIgnoreCase("SQLNONTRANSIENTEXCEPTION")) return 1;
    if (paramString.equalsIgnoreCase("SQLTRANSIENTEXCEPTION")) return 2;
    if (paramString.equalsIgnoreCase("SQLDATAEXCEPTION")) return 3;
    if (paramString.equalsIgnoreCase("SQLFEATURENOTSUPPORTEDEXCEPTION")) return 4;
    if (paramString.equalsIgnoreCase("SQLINTEGRITYCONSTRAINTVIOLATIONEXCEPTION")) return 5;
    if (paramString.equalsIgnoreCase("SQLINVALIDAUTHORIZATIONSPECEXCEPTION")) return 6;
    if (paramString.equalsIgnoreCase("SQLNONTRANSIENTCONNECTIONEXCEPTION")) return 7;
    if (paramString.equalsIgnoreCase("SQLSYNTAXERROREXCEPTION")) return 8;
    if (paramString.equalsIgnoreCase("SQLTIMEOUTEXCEPTION")) return 9;
    if (paramString.equalsIgnoreCase("SQLTRANSACTIONROLLBACKEXCEPTION")) return 10;
    if (paramString.equalsIgnoreCase("SQLTRANSIENTCONNECTIONEXCEPTION")) return 11;
    if (paramString.equalsIgnoreCase("SQLCLIENTINFOEXCEPTION")) return 12;
    if (paramString.equalsIgnoreCase("SQLRECOVERABLEEXCEPTION")) return 13;
    throw new Exception("unexpected exception name: " + paramString);
  }

  private static final class Tokenizer
  {
    int lineno = 1;
    Reader r;
    int c;

    Tokenizer(Reader paramReader)
      throws IOException
    {
      this.r = paramReader;
      this.c = paramReader.read();
    }

    String next() throws IOException {
      StringBuffer localStringBuffer = new StringBuffer(16);
      int i = 1;

      while (this.c != -1) {
        if (this.c == 10) this.lineno += 1;
        if ((this.c <= 32) && (i != 0)) {
          this.c = this.r.read();
        }
        else if ((this.c <= 32) && (i == 0)) {
          this.c = this.r.read();
        }
        else if (this.c == 34) {
          while ((this.c = this.r.read()) != 34) localStringBuffer.append((char)this.c);
          this.c = this.r.read();
        }
        else if (((48 <= this.c) && (this.c <= 57)) || ((65 <= this.c) && (this.c <= 90)) || ((97 <= this.c) && (this.c <= 122)) || (this.c == 95))
        {
          do
          {
            localStringBuffer.append((char)this.c);
          }

          while (((48 <= (this.c = this.r.read())) && (this.c <= 57)) || ((65 <= this.c) && (this.c <= 90)) || ((97 <= this.c) && (this.c <= 122)) || (this.c == 95));
        }
        else {
          localStringBuffer.append((char)this.c);
          this.c = this.r.read();
        }
      }
      if (localStringBuffer.length() > 0) return localStringBuffer.toString();
      return null;
    }
  }
}