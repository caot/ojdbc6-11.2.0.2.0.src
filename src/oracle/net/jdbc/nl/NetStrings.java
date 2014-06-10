package oracle.net.jdbc.nl;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class NetStrings
{
  ResourceBundle strBundle;
  Locale loc;

  public NetStrings(String paramString, Locale paramLocale)
  {
    this.strBundle = ResourceBundle.getBundle(paramString, paramLocale);
  }

  public NetStrings(Locale paramLocale)
  {
    this.strBundle = ResourceBundle.getBundle("oracle.net.jdbc.nl.mesg.NLSR", paramLocale);
  }

  public NetStrings()
  {
    this.strBundle = ResourceBundle.getBundle("oracle.network.nl.mesg.NLSR", Locale.getDefault());
  }

  public String getString(String paramString)
  {
    return this.strBundle.getString(paramString);
  }

  public String getString(String paramString, Object[] paramArrayOfObject)
  {
    String str = this.strBundle.getString(paramString);
    StringBuffer localStringBuffer = new StringBuffer();
    MessageFormat localMessageFormat = new MessageFormat(str);
    localMessageFormat.format(paramArrayOfObject, localStringBuffer, null);
    return localStringBuffer.toString();
  }
}