package oracle.jdbc.connector;

import javax.resource.spi.ConnectionRequestInfo;

public class OracleConnectionRequestInfo
  implements ConnectionRequestInfo
{
  private String user = null;
  private String password = null;

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  public OracleConnectionRequestInfo(String paramString1, String paramString2)
  {
    this.user = paramString1;
    this.password = paramString2;
  }

  public String getUser()
  {
    return this.user;
  }

  public void setUser(String paramString)
  {
    this.user = paramString;
  }

  public String getPassword()
  {
    return this.password;
  }

  public void setPassword(String paramString)
  {
    this.password = paramString;
  }

  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof OracleConnectionRequestInfo)) {
      return false;
    }
    OracleConnectionRequestInfo localOracleConnectionRequestInfo = (OracleConnectionRequestInfo)paramObject;

    return (this.user.equalsIgnoreCase(localOracleConnectionRequestInfo.getUser())) && (this.password.equals(localOracleConnectionRequestInfo.getPassword()));
  }
}