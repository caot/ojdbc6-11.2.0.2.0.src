package oracle.jdbc.driver;

class OracleTimestampBinder extends DatumBinder
{
  Binder theTimestampCopyingBinder = OraclePreparedStatementReadOnly.theStaticTimestampCopyingBinder;

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  static void init(Binder paramBinder)
  {
    paramBinder.type = 180;
    paramBinder.bytelen = 11;
  }

  OracleTimestampBinder()
  {
    init(this);
  }

  Binder copyingBinder()
  {
    return this.theTimestampCopyingBinder;
  }
}