package oracle.jdbc.driver;

import java.io.IOException;
import java.sql.SQLException;
import oracle.jdbc.internal.KeywordValue;

class T4CTTIkvarr extends T4CTTIMsg
{
  KeywordValue[] kpdkvarrptr = null;
  long kpdkvarrflg;

  T4CTTIkvarr(T4CConnection paramT4CConnection)
  {
    super(paramT4CConnection, (byte)0);
  }

  void unmarshal() throws SQLException, IOException {
    int i = (int)this.meg.unmarshalUB4();
    int j = (byte)this.meg.unmarshalUB1();
    if (i > 0)
    {
      this.kpdkvarrptr = new KeywordValueI[i];
      for (int k = 0; k < i; k++)
        this.kpdkvarrptr[k] = KeywordValueI.unmarshal(this.meg);
      this.connection.updateSessionProperties(this.kpdkvarrptr);
    }
    else {
      this.kpdkvarrptr = null;
    }this.kpdkvarrflg = this.meg.unmarshalUB4();
  }
}