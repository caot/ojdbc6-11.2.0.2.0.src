package oracle.jdbc;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Properties;

public class OracleDriver extends oracle.jdbc.driver.OracleDriver
{
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  public static final boolean isDMS()
  {
    return false;
  }

  public static final boolean isInServer()
  {
    return false;
  }

  /** @deprecated */
  public static final boolean isJDK14()
  {
    return true;
  }

  public static final boolean isDebug()
  {
    return false;
  }

  public static final boolean isPrivateDebug()
  {
    return false;
  }

  public static final String getJDBCVersion()
  {
    return "JDBC 4.0";
  }

  public static final String getDriverVersion()
  {
    return "11.2.0.2.0";
  }

  public static final String getBuildDate()
  {
    return "Sat_Aug_14_12:18:34_PDT_2010";
  }

  public static void main(String[] paramArrayOfString)
    throws Exception
  {
    String str = "JDK6";

    System.out.println(new StringBuilder().append("Oracle ").append(getDriverVersion()).append(" ").append(getJDBCVersion()).append(isDMS() ? " DMS" : "").append(isPrivateDebug() ? " private" : "").append(isDebug() ? " debug" : "").append(isInServer() ? " for JAVAVM" : "").append(" compiled with ").append(str).append(" on ").append(getBuildDate()).toString());

    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream(128);
    DEFAULT_CONNECTION_PROPERTIES.store(localByteArrayOutputStream, "Default Connection Properties Resource");
    System.out.println(localByteArrayOutputStream.toString("ISO-8859-1"));
  }
}