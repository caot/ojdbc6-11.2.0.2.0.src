package oracle.net.jdbc.nl;

import java.util.Locale;

public class NLException extends Exception
{
  public Object[] msg_params;
  private String error_index;
  private String error_msg;

  public NLException(String paramString)
  {
    this.error_msg = paramString;
  }

  public NLException(String paramString, Object paramObject)
  {
    this.error_index = paramString;
    Object[] arrayOfObject = { paramObject };
    this.msg_params = arrayOfObject;
    initErrorMessage();
  }

  public NLException(String paramString, Object[] paramArrayOfObject)
  {
    this.error_index = paramString;
    this.msg_params = paramArrayOfObject;
    initErrorMessage();
  }

  private void initErrorMessage()
  {
    NetStrings localNetStrings = new NetStrings("oracle.net.jdbc.nl.mesg.NLSR", Locale.getDefault());
    this.error_msg = localNetStrings.getString(this.error_index, this.msg_params);
  }

  public String toString()
  {
    return getErrorMessage();
  }

  public String getErrorIndex()
  {
    return this.error_index;
  }

  public String getErrorMessage()
  {
    return this.error_msg;
  }

  public String getMessage()
  {
    return getErrorMessage();
  }
}