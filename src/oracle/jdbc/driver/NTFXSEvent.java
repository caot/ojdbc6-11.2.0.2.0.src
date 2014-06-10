package oracle.jdbc.driver;

import java.io.IOException;
import java.sql.SQLException;
import oracle.jdbc.internal.KeywordValueLong;
import oracle.jdbc.internal.XSEvent;

class NTFXSEvent extends XSEvent
{
  private final byte[] sid_kpuzxsss;
  private final KeywordValueLongI[] sess_kpuzxsss;
  private final int flg_kpuzxsss;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  NTFXSEvent(T4CConnection paramT4CConnection)
    throws SQLException, IOException
  {
    super(paramT4CConnection);

    T4CMAREngine localT4CMAREngine = paramT4CConnection.getMarshalEngine();

    this.sid_kpuzxsss = localT4CMAREngine.unmarshalDALC();
    int i = (int)localT4CMAREngine.unmarshalUB4();
    int j = (byte)localT4CMAREngine.unmarshalUB1();
    this.sess_kpuzxsss = new KeywordValueLongI[i];
    for (int k = 0; k < i; k++)
    {
      this.sess_kpuzxsss[k] = KeywordValueLongI.unmarshal(localT4CMAREngine);
    }
    this.flg_kpuzxsss = ((int)localT4CMAREngine.unmarshalUB4());
  }

  public byte[] getSessionId()
  {
    return this.sid_kpuzxsss;
  }

  public KeywordValueLong[] getDetails()
  {
    return (KeywordValueLong[])this.sess_kpuzxsss;
  }

  public int getFlags()
  {
    return this.flg_kpuzxsss;
  }

  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("sid_kpuzxsss  : " + NTFAQEvent.byteBufferToHexString(this.sid_kpuzxsss, 50) + "\n");
    localStringBuffer.append("sess_kpuzxsss : \n");
    localStringBuffer.append("  size : " + this.sess_kpuzxsss.length + "\n");
    for (int i = 0; i < this.sess_kpuzxsss.length; i++)
    {
      localStringBuffer.append("  sess_kpuzxsss #" + i + " : \n");
      if (this.sess_kpuzxsss[i] == null)
        localStringBuffer.append("null\n");
      else
        localStringBuffer.append(this.sess_kpuzxsss[i].toString());
    }
    localStringBuffer.append("flg_kpuzxsss  : " + this.flg_kpuzxsss + "\n");
    return localStringBuffer.toString();
  }
}