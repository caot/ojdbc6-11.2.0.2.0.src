package oracle.jdbc.rowset;

import java.sql.SQLException;
import javax.sql.rowset.FilteredRowSet;
import javax.sql.rowset.Predicate;
import oracle.jdbc.driver.DatabaseError;

public class OracleFilteredRowSet extends OracleWebRowSet
  implements FilteredRowSet
{
  private Predicate predicate;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  public OracleFilteredRowSet()
    throws SQLException
  {
  }

  public void setFilter(Predicate paramPredicate)
    throws SQLException
  {
    this.predicate = paramPredicate;
  }

  public Predicate getFilter()
  {
    return this.predicate;
  }

  public boolean next()
    throws SQLException
  {
    if (this.rowCount <= 0) {
      return false;
    }
    if (this.presentRow >= this.rowCount) {
      return false;
    }
    int i = 0;
    do
    {
      this.presentRow += 1;

      if ((this.predicate == null) || ((this.predicate != null) && (this.predicate.evaluate(this))))
      {
        i = 1;
        break;
      }
    }
    while (this.presentRow <= this.rowCount);

    if (i != 0)
    {
      notifyCursorMoved();
      return true;
    }

    return false;
  }

  public boolean previous()
    throws SQLException
  {
    if (this.rowsetType == 1003)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 344);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if (this.rowCount <= 0) {
      return false;
    }
    if (this.presentRow <= 1) {
      return false;
    }
    int i = 0;
    do
    {
      this.presentRow -= 1;

      if ((this.predicate == null) || ((this.predicate != null) && (this.predicate.evaluate(this))))
      {
        i = 1;
        break;
      }
    }
    while (this.presentRow >= 1);

    if (i != 0)
    {
      notifyCursorMoved();
      return true;
    }

    return false;
  }

  public boolean absolute(int paramInt)
    throws SQLException
  {
    if (this.rowsetType == 1003)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 344);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if ((paramInt == 0) || (Math.abs(paramInt) > this.rowCount)) {
      return false;
    }

    int i = paramInt < 0 ? this.rowCount + paramInt + 1 : paramInt;

    int j = 0;
    this.presentRow = 0;

    while ((j < i) && (this.presentRow <= this.rowCount))
    {
      if (next())
        j++;
      else {
        return false;
      }
    }
    if (j == i)
    {
      notifyCursorMoved();
      return true;
    }

    return false;
  }

  protected void checkAndFilterObject(int paramInt, Object paramObject)
    throws SQLException
  {
    if ((this.predicate != null) && (!this.predicate.evaluate(paramObject, paramInt)))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 345);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }
}