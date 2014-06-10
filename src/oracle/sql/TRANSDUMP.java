package oracle.sql;

import java.sql.Connection;
import java.sql.SQLException;
import oracle.jdbc.OracleCallableStatement;

public class TRANSDUMP
{
  private static byte[] GMT_TRANSITIONS = { 1, 118, 100, 1, 1, 1, 1, 1, 20, 60, 0 };

  public static byte[] getTransitions(Connection paramConnection, int paramInt)
    throws SQLException
  {
    byte[] arrayOfByte = null;

    if (paramInt == ZONEIDMAP.getID("GMT")) {
      arrayOfByte = GMT_TRANSITIONS;
    }
    else
    {
      OracleCallableStatement localOracleCallableStatement = (OracleCallableStatement)paramConnection.prepareCall("begin dbms_utility.get_tz_transitions(:1,:2); end;");

      localOracleCallableStatement.setInt(1, paramInt);

      localOracleCallableStatement.registerOutParameter(2, -2);
      try
      {
        localOracleCallableStatement.execute();

        arrayOfByte = localOracleCallableStatement.getBytes(2);
      }
      finally
      {
        try
        {
          localOracleCallableStatement.close();
        }
        catch (Exception localException2)
        {
        }
      }
    }

    return arrayOfByte;
  }
}