package oracle.jdbc.driver;

class T4CRowidBinder extends RowidBinder
{
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  static void init(Binder paramBinder)
  {
    paramBinder.type = 104;
    paramBinder.bytelen = 130;
  }

  T4CRowidBinder()
  {
    this.theRowidCopyingBinder = OraclePreparedStatementReadOnly.theStaticT4CRowidCopyingBinder;

    init(this);
  }
}