package oracle.jdbc.rowset;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Vector;
import oracle.jdbc.driver.DatabaseError;
import oracle.jdbc.internal.OracleConnection;

public class OracleRow
  implements Serializable, Cloneable
{
  private Object[] column;
  private Object[] changedColumn;
  private boolean[] isOriginalNull;
  private byte[] columnChangeFlag;
  private int noColumn = 0;
  private int noColumnsInserted;
  private boolean rowDeleted = false;

  private boolean rowInserted = false;
  private static final byte COLUMN_CHANGED = 17;
  private boolean rowUpdated = false;
  int[][] columnTypeInfo;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  public OracleRow(int paramInt)
  {
    this.noColumn = paramInt;
    this.column = new Object[paramInt];
    this.changedColumn = new Object[paramInt];
    this.columnChangeFlag = new byte[paramInt];
    this.isOriginalNull = new boolean[paramInt];
    this.columnTypeInfo = new int[paramInt][];
    for (int i = 0; i < paramInt; i++)
      this.columnChangeFlag[i] = 0;
  }

  public OracleRow(int paramInt, boolean paramBoolean)
  {
    this(paramInt);

    this.rowInserted = paramBoolean;
    this.noColumnsInserted = 0;
  }

  public OracleRow(int paramInt, Object[] paramArrayOfObject)
  {
    this(paramInt);

    System.arraycopy(paramArrayOfObject, 0, this.column, 0, paramInt);
  }

  public void setColumnValue(int paramInt, Object paramObject)
  {
    if (this.rowInserted)
      this.noColumnsInserted += 1;
    this.column[(paramInt - 1)] = paramObject;
  }

  void markOriginalNull(int paramInt, boolean paramBoolean)
    throws SQLException
  {
    this.isOriginalNull[(paramInt - 1)] = paramBoolean;
  }

  boolean isOriginalNull(int paramInt)
    throws SQLException
  {
    return this.isOriginalNull[(paramInt - 1)];
  }

  public void updateObject(int paramInt, Object paramObject)
  {
    updateObject(paramInt, paramObject, (int[])null);
  }

  void updateObject(int paramInt, Object paramObject, int[] paramArrayOfInt)
  {
    if (this.rowInserted)
      this.noColumnsInserted += 1;
    this.columnChangeFlag[(paramInt - 1)] = 17;
    this.changedColumn[(paramInt - 1)] = paramObject;
    this.columnTypeInfo[(paramInt - 1)] = paramArrayOfInt;
  }

  public void cancelRowUpdates()
  {
    this.noColumnsInserted = 0;
    for (int i = 0; i < this.noColumn; i++)
      this.columnChangeFlag[i] = 0;
    this.changedColumn = null;
    this.changedColumn = new Object[this.noColumn];
  }

  public Object getColumn(int paramInt)
  {
    return this.column[(paramInt - 1)];
  }

  public Object getModifiedColumn(int paramInt)
  {
    return this.changedColumn[(paramInt - 1)];
  }

  public boolean isColumnChanged(int paramInt)
  {
    return this.columnChangeFlag[(paramInt - 1)] == 17;
  }

  public boolean isRowUpdated()
  {
    if ((this.rowInserted) || (this.rowDeleted)) {
      return false;
    }
    for (int i = 0; i < this.noColumn; i++) {
      if (this.columnChangeFlag[i] == 17)
        return true;
    }
    return false;
  }

  public void setRowUpdated(boolean paramBoolean)
  {
    this.rowUpdated = paramBoolean;
    if (!paramBoolean)
      cancelRowUpdates();
  }

  public boolean isRowInserted()
  {
    return this.rowInserted;
  }

  public void cancelRowDeletion()
  {
    this.rowDeleted = false;
  }

  public void setRowDeleted(boolean paramBoolean)
  {
    this.rowDeleted = paramBoolean;
  }

  public boolean isRowDeleted()
  {
    return this.rowDeleted;
  }

  public Object[] getOriginalRow()
  {
    return this.column;
  }

  public boolean isRowFullyPopulated()
  {
    if (!this.rowInserted) {
      return false;
    }
    return this.noColumnsInserted == this.noColumn;
  }

  public void setInsertedFlag(boolean paramBoolean)
  {
    this.rowInserted = paramBoolean;
  }

  void makeUpdatesOriginal()
  {
    for (int i = 0; i < this.noColumn; i++)
    {
      if (this.columnChangeFlag[i] == 17)
      {
        this.column[i] = this.changedColumn[i];
        this.changedColumn[i] = null;
        this.columnChangeFlag[i] = 0;
      }
    }

    this.rowUpdated = false;
  }

  public void insertRow()
  {
    this.columnChangeFlag = null;
    this.columnChangeFlag = new byte[this.noColumn];
    System.arraycopy(this.changedColumn, 0, this.column, 0, this.noColumn);
    this.changedColumn = null;
    this.changedColumn = new Object[this.noColumn];
  }

  public Collection toCollection()
  {
    Vector localVector = new Vector(this.noColumn);
    for (int i = 1; i <= this.noColumn; i++) {
      localVector.add(isColumnChanged(i) ? getModifiedColumn(i) : getColumn(i));
    }

    return localVector;
  }

  public OracleRow createCopy()
    throws SQLException
  {
    OracleRow localOracleRow = new OracleRow(this.noColumn);
    for (int i = 0; i < this.noColumn; i++)
    {
      localOracleRow.column[i] = getCopy(this.column[i]);
      localOracleRow.changedColumn[i] = getCopy(this.changedColumn[i]);
    }

    System.arraycopy(this.columnChangeFlag, 0, localOracleRow.columnChangeFlag, 0, this.noColumn);
    localOracleRow.noColumnsInserted = this.noColumnsInserted;
    localOracleRow.rowDeleted = this.rowDeleted;
    localOracleRow.rowInserted = this.rowInserted;
    localOracleRow.rowUpdated = this.rowUpdated;

    return localOracleRow;
  }

  public Object getCopy(Object paramObject)
    throws SQLException
  {
    Object localObject = null;
    try
    {
      if (paramObject == null) {
        return null;
      }
      if ((paramObject instanceof String)) {
        localObject = (String)paramObject;
      }
      else if ((paramObject instanceof Number)) {
        localObject = new BigDecimal(((Number)paramObject).toString());
      }
      else if ((paramObject instanceof Date)) {
        localObject = new Date(((Date)paramObject).getTime());
      }
      else if ((paramObject instanceof Timestamp)) {
        localObject = new Timestamp(((Timestamp)paramObject).getTime());
      }
      else if ((paramObject instanceof InputStream)) {
        localObject = new DataInputStream((InputStream)paramObject);
      }
      else if ((paramObject instanceof OutputStream)) {
        localObject = new DataOutputStream((OutputStream)paramObject);
      }
      else
      {
        SQLException localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 348, paramObject.getClass().getName());
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }
    }
    catch (Exception localException)
    {
      SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 349, paramObject.getClass().getName() + localException.getMessage());
      localSQLException2.fillInStackTrace();
      throw localSQLException2;
    }

    return localObject;
  }

  public Object clone()
    throws CloneNotSupportedException
  {
    try
    {
      return createCopy();
    }
    catch (SQLException localSQLException) {
      throw new CloneNotSupportedException("Error while cloning\n" + localSQLException.getMessage());
    }
  }

  protected OracleConnection getConnectionDuringExceptionHandling()
  {
    return null;
  }
}