package oracle.jdbc.driver;

import java.sql.SQLException;

class SensitiveScrollableResultSet extends ScrollableResultSet
{
  int beginLastFetchedIndex;
  int endLastFetchedIndex;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  SensitiveScrollableResultSet(ScrollRsetStatement paramScrollRsetStatement, OracleResultSetImpl paramOracleResultSetImpl, int paramInt1, int paramInt2)
    throws SQLException
  {
    super(paramScrollRsetStatement, paramOracleResultSetImpl, paramInt1, paramInt2);

    int i = paramOracleResultSetImpl.getValidRows();

    if (i > 0)
    {
      this.beginLastFetchedIndex = 1;
      this.endLastFetchedIndex = i;
    }
    else
    {
      this.beginLastFetchedIndex = 0;
      this.endLastFetchedIndex = 0;
    }
  }

  public boolean next()
    throws SQLException
  {
    synchronized (this.connection)
    {
      if (super.next())
      {
        handle_refetch();

        return true;
      }

      return false;
    }
  }

  public boolean first()
    throws SQLException
  {
    synchronized (this.connection)
    {
      if (super.first())
      {
        handle_refetch();

        return true;
      }

      return false;
    }
  }

  public boolean last()
    throws SQLException
  {
    synchronized (this.connection)
    {
      if (super.last())
      {
        handle_refetch();

        return true;
      }

      return false;
    }
  }

  public boolean absolute(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      if (super.absolute(paramInt))
      {
        handle_refetch();

        return true;
      }

      return false;
    }
  }

  public boolean relative(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      if (super.relative(paramInt))
      {
        handle_refetch();

        return true;
      }

      return false;
    }
  }

  public boolean previous()
    throws SQLException
  {
    synchronized (this.connection)
    {
      if (super.previous())
      {
        handle_refetch();

        return true;
      }

      return false;
    }
  }

  public void refreshRow()
    throws SQLException
  {
    synchronized (this.connection)
    {
      if (!isValidRow(this.currentRow))
      {
        SQLException localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 11);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      int i = getFetchDirection();
      int j = 0;
      try
      {
        j = refreshRowsInCache(this.currentRow, getFetchSize(), i);
      }
      catch (SQLException localSQLException2)
      {
        SQLException localSQLException3 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localSQLException2, 90, "Unsupported syntax for refreshRow()");
        localSQLException3.fillInStackTrace();
        throw localSQLException3;
      }

      if (j != 0)
      {
        this.beginLastFetchedIndex = this.currentRow;

        this.endLastFetchedIndex = (this.currentRow + j - 1);
      }
    }
  }

  int removeRowInCache(int paramInt)
    throws SQLException
  {
    synchronized (this.connection)
    {
      int i = super.removeRowInCache(paramInt);

      if (i != 0)
      {
        if ((paramInt >= this.beginLastFetchedIndex) && (paramInt <= this.endLastFetchedIndex) && (this.beginLastFetchedIndex != this.endLastFetchedIndex))
        {
          this.endLastFetchedIndex -= 1;
        }
        else
        {
          this.beginLastFetchedIndex = (this.endLastFetchedIndex = 0);
        }
      }
      return i;
    }
  }

  private boolean handle_refetch()
    throws SQLException
  {
    synchronized (this.connection)
    {
      if (((this.currentRow >= this.beginLastFetchedIndex) && (this.currentRow <= this.endLastFetchedIndex)) || ((this.currentRow >= this.endLastFetchedIndex) && (this.currentRow <= this.beginLastFetchedIndex)))
      {
        return false;
      }

      refreshRow();

      return true;
    }
  }
}