package oracle.jdbc.driver;

import java.io.IOException;
import java.sql.SQLException;

class T4CTTIqcinv extends T4CTTIMsg
{
  long kpdqcqid;
  long kpdqcopflg;
  T4CTTIkscn kpdqcscn = null;

  T4CTTIqcinv(T4CConnection paramT4CConnection) {
    super(paramT4CConnection, (byte)0);
  }

  void unmarshal() throws SQLException, IOException {
    this.kpdqcqid = this.meg.unmarshalSB8();
    this.kpdqcopflg = this.meg.unmarshalUB4();
    this.kpdqcscn = new T4CTTIkscn(this.connection);
    this.kpdqcscn.unmarshal();
  }
}