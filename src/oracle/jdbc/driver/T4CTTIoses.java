package oracle.jdbc.driver;

import java.io.IOException;
import java.sql.SQLException;

final class T4CTTIoses extends T4CTTIfun
{
  static final int OSESSWS = 1;
  static final int OSESDET = 3;
  private int sididx;
  private int sidser;
  private int sidopc;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  T4CTTIoses(T4CConnection paramT4CConnection)
  {
    super(paramT4CConnection, (byte)17);

    setFunCode((short)107);
  }

  void doO80SES(int paramInt1, int paramInt2, int paramInt3)
    throws IOException, SQLException
  {
    this.sididx = paramInt1;
    this.sidser = paramInt2;
    this.sidopc = paramInt3;

    if ((this.sidopc != 1) && (this.sidopc != 3))
      throw new SQLException("Wrong operation : can only do switch or detach");
    doPigRPC();
  }

  void marshal()
    throws IOException
  {
    this.meg.marshalUB4(this.sididx);
    this.meg.marshalUB4(this.sidser);
    this.meg.marshalUB4(this.sidopc);
  }
}