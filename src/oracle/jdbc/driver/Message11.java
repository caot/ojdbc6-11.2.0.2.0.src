package oracle.jdbc.driver;

import java.util.ResourceBundle;

class Message11
  implements Message
{
  private static ResourceBundle bundle;
  private static final String messageFile = "oracle.jdbc.driver.Messages";
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  public String msg(String paramString, Object paramObject)
  {
    if (bundle == null)
    {
      try
      {
        bundle = ResourceBundle.getBundle("oracle.jdbc.driver.Messages");
      }
      catch (Exception localException1)
      {
        return "Message file 'oracle.jdbc.driver.Messages' is missing.";
      }

    }

    try
    {
      if (paramObject != null) {
        return bundle.getString(paramString) + ": " + paramObject;
      }
      return bundle.getString(paramString);
    }
    catch (Exception localException2) {
    }
    return "Message [" + paramString + "] not found in '" + "oracle.jdbc.driver.Messages" + "'.";
  }
}