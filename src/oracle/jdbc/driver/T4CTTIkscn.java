package oracle.jdbc.driver;

import java.io.IOException;
import java.sql.SQLException;

class T4CTTIkscn extends T4CTTIMsg
{
  long kscnbas;
  int kscnwrp;

  T4CTTIkscn(T4CConnection paramT4CConnection)
  {
    super(paramT4CConnection, (byte)0);
  }

  void unmarshal() throws SQLException, IOException {
    this.kscnbas = this.meg.unmarshalUB4();
    this.kscnwrp = this.meg.unmarshalUB2();
  }
}