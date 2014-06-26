package oracle.jdbc.driver;

import java.io.IOException;
import java.sql.SQLException;

class T4CTTIidc extends T4CTTIMsg
{
  T4CTTIkscn kpdqidcscn;
  T4CTTIqcinv[] kpdqidccinv;
  T4CTTIqcinv[] kpdqidcusr;
  long kpdqidcflg;

  T4CTTIidc(T4CConnection paramT4CConnection)
  {
    super(paramT4CConnection, (byte)0);
  }

  void unmarshal() throws SQLException, IOException {
    this.kpdqidcscn = new T4CTTIkscn(this.connection);
    this.kpdqidcscn.unmarshal();
    int i = this.meg.unmarshalSWORD();
    int j = (byte)this.meg.unmarshalUB1();
    if (i > 0)
    {
      this.kpdqidccinv = new T4CTTIqcinv[i];
      for (int k = 0; k < i; k++)
      {
        this.kpdqidccinv[k] = new T4CTTIqcinv(this.connection);
        this.kpdqidccinv[k].unmarshal();
      }
    }
    else {
      this.kpdqidccinv = null;
    }
    int k = this.meg.unmarshalSWORD();
    if (k > 0)
    {
      this.kpdqidcusr = new T4CTTIqcinv[k];
      for (int m = 0; m < k; m++)
      {
        this.kpdqidcusr[m] = new T4CTTIqcinv(this.connection);
        this.kpdqidcusr[m].unmarshal();
      }
    }
    else {
      this.kpdqidcusr = null;
    }this.kpdqidcflg = this.meg.unmarshalUB4();
  }
}