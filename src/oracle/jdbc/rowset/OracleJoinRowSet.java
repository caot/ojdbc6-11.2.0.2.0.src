package oracle.jdbc.rowset;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Vector;
import javax.sql.RowSet;
import javax.sql.RowSetMetaData;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.JoinRowSet;
import javax.sql.rowset.Joinable;
import oracle.jdbc.driver.DatabaseError;

public class OracleJoinRowSet extends OracleWebRowSet
  implements JoinRowSet
{
  private static final String MATCH_COLUMN_SUFFIX = "#MATCH_COLUMN";
  private static boolean[] supportedJoins = { false, true, false, false, false };
  private int joinType;
  private Vector addedRowSets;
  private Vector addedRowSetNames;
  private Object lockForJoinActions;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  public OracleJoinRowSet()
    throws SQLException
  {
    this.joinType = 1;
    this.addedRowSets = new Vector();
    this.addedRowSetNames = new Vector();
  }

  public synchronized void addRowSet(Joinable paramJoinable)
    throws SQLException
  {
    if (paramJoinable == null)
    {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 350);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    if (!(paramJoinable instanceof RowSet))
    {
      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 351);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject = checkAndWrapRowSet((RowSet)paramJoinable);
    String str = getMatchColumnTableName((RowSet)paramJoinable);

    switch (this.joinType)
    {
    case 1:
      doInnerJoin((OracleCachedRowSet)localObject);

      this.addedRowSets.add(paramJoinable);
      this.addedRowSetNames.add(str);
      break;
    case 0:
    case 2:
    case 3:
    case 4:
    default:
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 352);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  public synchronized void addRowSet(RowSet paramRowSet, int paramInt)
    throws SQLException
  {
    ((OracleRowSet)paramRowSet).setMatchColumn(paramInt);
    addRowSet((Joinable)paramRowSet);
  }

  public synchronized void addRowSet(RowSet paramRowSet, String paramString)
    throws SQLException
  {
    ((OracleRowSet)paramRowSet).setMatchColumn(paramString);
    addRowSet((Joinable)paramRowSet);
  }

  public synchronized void addRowSet(RowSet[] paramArrayOfRowSet, int[] paramArrayOfInt)
    throws SQLException
  {
    if (paramArrayOfRowSet.length != paramArrayOfInt.length)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 353);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    for (int i = 0; i < paramArrayOfRowSet.length; i++)
    {
      ((OracleRowSet)paramArrayOfRowSet[i]).setMatchColumn(paramArrayOfInt[i]);
      addRowSet((Joinable)paramArrayOfRowSet[i]);
    }
  }

  public synchronized void addRowSet(RowSet[] paramArrayOfRowSet, String[] paramArrayOfString)
    throws SQLException
  {
    if (paramArrayOfRowSet.length != paramArrayOfString.length)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 353);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    for (int i = 0; i < paramArrayOfRowSet.length; i++)
    {
      ((OracleRowSet)paramArrayOfRowSet[i]).setMatchColumn(paramArrayOfString[i]);
      addRowSet((Joinable)paramArrayOfRowSet[i]);
    }
  }

  public Collection getRowSets()
    throws SQLException
  {
    return this.addedRowSets;
  }

  public String[] getRowSetNames()
    throws SQLException
  {
    Object[] arrayOfObject = this.addedRowSetNames.toArray();
    String[] arrayOfString = new String[arrayOfObject.length];
    for (int i = 0; i < arrayOfObject.length; i++)
    {
      arrayOfString[i] = ((String)arrayOfObject[i]);
    }
    return arrayOfString;
  }

  public CachedRowSet toCachedRowSet()
    throws SQLException
  {
    OracleCachedRowSet localOracleCachedRowSet = (OracleCachedRowSet)createCopy();

    localOracleCachedRowSet.setCommand("");

    return localOracleCachedRowSet;
  }

  public int getJoinType()
  {
    return this.joinType;
  }

  public boolean supportsCrossJoin()
  {
    return supportedJoins[0];
  }

  public boolean supportsInnerJoin()
  {
    return supportedJoins[1];
  }

  public boolean supportsLeftOuterJoin()
  {
    return supportedJoins[2];
  }

  public boolean supportsRightOuterJoin()
  {
    return supportedJoins[3];
  }

  public boolean supportsFullJoin()
  {
    return supportedJoins[4];
  }

  public void setJoinType(int paramInt)
    throws SQLException
  {
    if (paramInt != 1)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 352);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.joinType = paramInt;
  }

  public synchronized String getWhereClause()
    throws SQLException
  {
    if (this.addedRowSets.size() < 2) {
      return "WHERE";
    }
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("WHERE\n");

    Object localObject1 = (OracleRowSet)this.addedRowSets.get(0);
    int[] localObject2 = ((OracleRowSet)localObject1).getMatchColumnIndexes();
    Object localObject3 = ((OracleRowSet)localObject1).getMetaData();
    Object localObject4 = ((OracleRowSet)localObject1).getTableName();

    for (int i = 1; i < this.addedRowSets.size(); i++)
    {
      if (i > 1)
      {
        localStringBuffer.append("\nAND\n");
      }

      OracleRowSet localOracleRowSet = (OracleRowSet)this.addedRowSets.get(i);
      int[] arrayOfInt = localOracleRowSet.getMatchColumnIndexes();
      ResultSetMetaData localResultSetMetaData = localOracleRowSet.getMetaData();
      String str = localOracleRowSet.getTableName();

      for (int j = 0; j < localObject2.length; j++)
      {
        if (j > 0)
        {
          localStringBuffer.append("\nAND\n");
        }

        localStringBuffer.append("(" + (String)localObject4 + "." + ((ResultSetMetaData)localObject3).getColumnName(localObject2[j]) + " = " + str + "." + localResultSetMetaData.getColumnName(arrayOfInt[j]) + ")");
      }

      localObject1 = localOracleRowSet;
      localObject2 = arrayOfInt;
      localObject3 = localResultSetMetaData;
      localObject4 = str;
    }

    localStringBuffer.append(";");

    return localStringBuffer.toString();
  }

  private void doInnerJoin(OracleCachedRowSet paramOracleCachedRowSet)
    throws SQLException
  {
    if (this.addedRowSets.isEmpty())
    {
      setMetaData((RowSetMetaData)paramOracleCachedRowSet.getMetaData());
      populate(paramOracleCachedRowSet);

      setMatchColumn(paramOracleCachedRowSet.getMatchColumnIndexes());
    }
    else
    {
      Vector localVector = new Vector(100);
      OracleRowSetMetaData localOracleRowSetMetaData = new OracleRowSetMetaData(10);

      int[] arrayOfInt1 = getMatchColumnIndexes();
      int[] arrayOfInt2 = paramOracleCachedRowSet.getMatchColumnIndexes();

      int i = getMetaData().getColumnCount() + paramOracleCachedRowSet.getMetaData().getColumnCount() - arrayOfInt2.length;

      localOracleRowSetMetaData.setColumnCount(i);

      String str = getTableName() + "#" + paramOracleCachedRowSet.getTableName();
      boolean bool;
      for (int j = 1; j <= this.colCount; j++)
      {
        bool = false;
        for (int k = 0; k < arrayOfInt1.length; k++)
        {
          if (j == arrayOfInt1[k])
          {
            bool = true;
            break;
          }
        }

        setNewColumnMetaData(j, localOracleRowSetMetaData, j, (RowSetMetaData)this.rowsetMetaData, bool, str);
      }

      RowSetMetaData localRowSetMetaData = (RowSetMetaData)paramOracleCachedRowSet.getMetaData();

      int k = localRowSetMetaData.getColumnCount();

      int m = this.colCount + 1;
      int[] arrayOfInt3 = new int[k];

      for (int n = 1; n <= k; n++)
      {
        bool = false;
        for (int i1 = 0; i1 < arrayOfInt2.length; i1++)
        {
          if (n == arrayOfInt1[i1])
          {
            bool = true;
            break;
          }
        }

        if (!bool)
        {
          setNewColumnMetaData(m, localOracleRowSetMetaData, n, localRowSetMetaData, bool, str);

          arrayOfInt3[(n - 1)] = m;
          m++;
        }
        else
        {
          arrayOfInt3[(n - 1)] = -1;
        }
      }

      beforeFirst();

      int n = paramOracleCachedRowSet.size();
      int i1 = 0;

      for (int i2 = 1; i2 <= this.rowCount; i2++)
      {
        next();
        paramOracleCachedRowSet.beforeFirst();

        for (int i3 = 1; i3 <= n; i3++)
        {
          paramOracleCachedRowSet.next();

          i1 = 1;
          for (int i4 = 0; i4 < arrayOfInt1.length; i4++)
          {
            Object localObject1 = getObject(arrayOfInt1[i4]);
            Object localObject2 = paramOracleCachedRowSet.getObject(arrayOfInt2[i4]);
            if (!localObject1.equals(localObject2))
            {
              i1 = 0;
              break;
            }
          }

          if (i1 != 0)
          {
            OracleRow localOracleRow = new OracleRow(i, true);

            for (int i5 = 1; i5 <= this.colCount; i5++)
            {
              localOracleRow.updateObject(i5, getObject(i5));
            }

            for (int i5 = 1; i5 <= k; i5++)
            {
              if (arrayOfInt3[(i5 - 1)] != -1)
              {
                localOracleRow.updateObject(arrayOfInt3[(i5 - 1)], paramOracleCachedRowSet.getObject(i5));
              }

            }

            localVector.add(localOracleRow);
          }
        }
      }

      this.rows = localVector;
      this.presentRow = 0;
      this.rowCount = this.rows.size();
      setMetaData(localOracleRowSetMetaData);
    }
  }

  private void setNewColumnMetaData(int paramInt1, RowSetMetaData paramRowSetMetaData1, int paramInt2, RowSetMetaData paramRowSetMetaData2, boolean paramBoolean, String paramString)
    throws SQLException
  {
    paramRowSetMetaData1.setAutoIncrement(paramInt1, paramRowSetMetaData2.isAutoIncrement(paramInt2));
    paramRowSetMetaData1.setCaseSensitive(paramInt1, paramRowSetMetaData2.isCaseSensitive(paramInt2));
    paramRowSetMetaData1.setCatalogName(paramInt1, paramRowSetMetaData2.getCatalogName(paramInt2));
    paramRowSetMetaData1.setColumnDisplaySize(paramInt1, paramRowSetMetaData2.getColumnDisplaySize(paramInt2));

    if (paramBoolean)
    {
      paramRowSetMetaData1.setColumnName(paramInt1, paramRowSetMetaData2.getColumnName(paramInt1) + "#MATCH_COLUMN");
    }
    else
    {
      paramRowSetMetaData1.setColumnName(paramInt1, paramRowSetMetaData2.getColumnName(paramInt2));
    }

    paramRowSetMetaData1.setColumnLabel(paramInt1, paramRowSetMetaData1.getColumnName(paramInt2));

    paramRowSetMetaData1.setColumnType(paramInt1, paramRowSetMetaData2.getColumnType(paramInt2));
    paramRowSetMetaData1.setColumnTypeName(paramInt1, paramRowSetMetaData2.getColumnTypeName(paramInt2));
    paramRowSetMetaData1.setCurrency(paramInt1, paramRowSetMetaData2.isCurrency(paramInt2));
    paramRowSetMetaData1.setNullable(paramInt1, paramRowSetMetaData2.isNullable(paramInt2));
    paramRowSetMetaData1.setPrecision(paramInt1, paramRowSetMetaData2.getPrecision(paramInt2));
    paramRowSetMetaData1.setScale(paramInt1, paramRowSetMetaData2.getScale(paramInt2));
    paramRowSetMetaData1.setSchemaName(paramInt1, paramRowSetMetaData2.getSchemaName(paramInt2));
    paramRowSetMetaData1.setSearchable(paramInt1, paramRowSetMetaData2.isSearchable(paramInt2));
    paramRowSetMetaData1.setSigned(paramInt1, paramRowSetMetaData2.isSigned(paramInt2));

    if (paramBoolean)
    {
      paramRowSetMetaData1.setTableName(paramInt1, paramString);
    }
    else
    {
      paramRowSetMetaData1.setTableName(paramInt1, paramRowSetMetaData2.getTableName(paramInt2));
    }
  }

  private OracleCachedRowSet checkAndWrapRowSet(RowSet paramRowSet)
    throws SQLException
  {
    OracleCachedRowSet localOracleCachedRowSet = null;

    if ((paramRowSet instanceof OracleCachedRowSet))
    {
      localOracleCachedRowSet = (OracleCachedRowSet)paramRowSet;
    }
    else
    {
      Object localObject;
      if ((paramRowSet instanceof OracleJDBCRowSet))
      {
        localOracleCachedRowSet = new OracleCachedRowSet();
        localOracleCachedRowSet.populate(paramRowSet);

        localObject = ((OracleJDBCRowSet)paramRowSet).getMatchColumnIndexes();
        localOracleCachedRowSet.setMatchColumn((int[])localObject);
      }
      else
      {
        SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 354);
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }
    }
    return localOracleCachedRowSet;
  }

  private String getMatchColumnTableName(RowSet paramRowSet)
    throws SQLException
  {
    String str = null;
    if ((paramRowSet instanceof OracleRowSet))
    {
      str = ((OracleRowSet)paramRowSet).getTableName();
    }

    return str;
  }
}