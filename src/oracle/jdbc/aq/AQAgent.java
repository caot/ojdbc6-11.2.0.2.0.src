package oracle.jdbc.aq;

import java.sql.SQLException;

public abstract interface AQAgent
{
  public abstract void setAddress(String paramString)
    throws SQLException;

  public abstract String getAddress();

  public abstract void setName(String paramString)
    throws SQLException;

  public abstract String getName();

  public abstract void setProtocol(int paramInt)
    throws SQLException;

  public abstract int getProtocol();

  public abstract String toString();
}