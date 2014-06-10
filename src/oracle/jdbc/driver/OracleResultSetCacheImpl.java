package oracle.jdbc.driver;

import java.util.Vector;

class OracleResultSetCacheImpl
  implements OracleResultSetCache
{
  private static int DEFAULT_WIDTH = 5;
  private static int DEFAULT_SIZE = 5;

  Vector cachedRows = null;
  int nbOfColumnsInRow;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  OracleResultSetCacheImpl()
  {
    this(DEFAULT_WIDTH);
  }

  OracleResultSetCacheImpl(int paramInt)
  {
    if (paramInt > 0) {
      this.nbOfColumnsInRow = paramInt;
    }
    this.cachedRows = new Vector(DEFAULT_SIZE);
  }

  public void put(int paramInt1, int paramInt2, Object paramObject)
  {
    Vector localVector = null;

    while (this.cachedRows.size() < paramInt1)
    {
      localVector = new Vector(this.nbOfColumnsInRow);
      this.cachedRows.addElement(localVector);
    }

    localVector = (Vector)this.cachedRows.elementAt(paramInt1 - 1);

    while (localVector.size() < paramInt2) {
      localVector.addElement(null);
    }
    localVector.setElementAt(paramObject, paramInt2 - 1);
  }

  public Object get(int paramInt1, int paramInt2)
  {
    Vector localVector = (Vector)this.cachedRows.elementAt(paramInt1 - 1);

    return localVector.elementAt(paramInt2 - 1);
  }

  public void remove(int paramInt)
  {
    this.cachedRows.removeElementAt(paramInt - 1);
  }

  public void remove(int paramInt1, int paramInt2)
  {
    this.cachedRows.removeElementAt(paramInt1 - 1);
  }

  public void clear()
  {
  }

  public void close()
  {
  }

  public int getLength()
  {
    return 0;
  }
}