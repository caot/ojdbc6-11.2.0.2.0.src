package oracle.jdbc.driver;

import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.Executor;
import oracle.jdbc.dcn.DatabaseChangeListener;
import oracle.jdbc.dcn.DatabaseChangeRegistration;

class NTFDCNRegistration extends NTFRegistration
  implements DatabaseChangeRegistration
{
  private final long regid;
  private String[] tables = new String[10];
  private int nbOfStringsInTable = 0;

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  NTFDCNRegistration(int paramInt1, boolean paramBoolean, String paramString1, long paramLong, String paramString2, String paramString3, int paramInt2, Properties paramProperties, short paramShort)
  {
    super(paramInt1, 2, paramBoolean, paramString1, paramString3, paramInt2, paramProperties, paramString2, paramShort);

    this.regid = paramLong;
  }

  NTFDCNRegistration(String paramString1, long paramLong, String paramString2, short paramShort)
  {
    super(0, 2, false, paramString1, null, 0, null, paramString2, paramShort);

    this.regid = paramLong;
  }

  public int getRegistrationId()
  {
    return (int)this.regid;
  }

  public long getRegId()
  {
    return this.regid;
  }

  public void addListener(DatabaseChangeListener paramDatabaseChangeListener, Executor paramExecutor)
    throws SQLException
  {
    NTFEventListener localNTFEventListener = new NTFEventListener(paramDatabaseChangeListener);
    localNTFEventListener.setExecutor(paramExecutor);
    addListener(localNTFEventListener);
  }

  public void addListener(DatabaseChangeListener paramDatabaseChangeListener)
    throws SQLException
  {
    NTFEventListener localNTFEventListener = new NTFEventListener(paramDatabaseChangeListener);
    addListener(localNTFEventListener);
  }

  public void removeListener(DatabaseChangeListener paramDatabaseChangeListener)
    throws SQLException
  {
    super.removeListener(paramDatabaseChangeListener);
  }

  synchronized void addTablesName(String[] paramArrayOfString, int paramInt)
  {
    if (this.nbOfStringsInTable + paramInt > this.tables.length)
    {
      String[] arrayOfString = new String[(this.nbOfStringsInTable + paramInt) * 2];
      System.arraycopy(this.tables, 0, arrayOfString, 0, this.tables.length);
      this.tables = arrayOfString;
    }

    System.arraycopy(paramArrayOfString, 0, this.tables, this.nbOfStringsInTable, paramInt);
    this.nbOfStringsInTable += paramInt;
  }

  public String[] getTables()
  {
    String[] arrayOfString = new String[this.nbOfStringsInTable];

    System.arraycopy(this.tables, 0, arrayOfString, 0, this.nbOfStringsInTable);
    return arrayOfString;
  }
}