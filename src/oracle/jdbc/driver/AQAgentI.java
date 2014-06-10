package oracle.jdbc.driver;

import java.sql.SQLException;
import oracle.jdbc.aq.AQAgent;

class AQAgentI
  implements AQAgent
{
  private String attrAgentAddress;
  private String attrAgentName;
  private int attrAgentProtocol;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  AQAgentI()
    throws SQLException
  {
    this.attrAgentName = null;
    this.attrAgentAddress = null;
    this.attrAgentProtocol = 0;
  }

  public void setAddress(String paramString)
    throws SQLException
  {
    this.attrAgentAddress = paramString;
  }

  public String getAddress()
  {
    return this.attrAgentAddress;
  }

  public void setName(String paramString)
    throws SQLException
  {
    this.attrAgentName = paramString;
  }

  public String getName()
  {
    return this.attrAgentName;
  }

  public void setProtocol(int paramInt)
    throws SQLException
  {
    this.attrAgentProtocol = paramInt;
  }

  public int getProtocol()
  {
    return this.attrAgentProtocol;
  }

  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("Name=\"");
    localStringBuffer.append(getName());
    localStringBuffer.append("\" ");
    localStringBuffer.append("Address=\"");
    localStringBuffer.append(getAddress());
    localStringBuffer.append("\" ");
    localStringBuffer.append("Protocol=\"");
    localStringBuffer.append(getProtocol());
    localStringBuffer.append("\"");
    return localStringBuffer.toString();
  }
}