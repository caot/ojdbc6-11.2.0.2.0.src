package oracle.jdbc.driver;

class ClobBinder extends DatumBinder
{
  Binder theClobCopyingBinder = OraclePreparedStatementReadOnly.theStaticClobCopyingBinder;

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  static void init(Binder paramBinder)
  {
    paramBinder.type = 112;
    paramBinder.bytelen = 4000;
  }

  ClobBinder()
  {
    init(this);
  }

  Binder copyingBinder()
  {
    return this.theClobCopyingBinder;
  }

  void lastBoundValueCleanup(OraclePreparedStatement paramOraclePreparedStatement, int paramInt)
  {
    if (paramOraclePreparedStatement.lastBoundClobs != null)
      paramOraclePreparedStatement.moveTempLobsToFree(paramOraclePreparedStatement.lastBoundClobs[paramInt]);
  }
}