package oracle.jdbc.driver;

abstract class VarcharBinder extends Binder
{
  Binder theVarcharCopyingBinder = OraclePreparedStatementReadOnly.theStaticVarcharCopyingBinder;

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  static void init(Binder paramBinder)
  {
    paramBinder.type = 9;
    paramBinder.bytelen = 0;
  }

  VarcharBinder()
  {
    init(this);
  }

  Binder copyingBinder()
  {
    return this.theVarcharCopyingBinder;
  }
}