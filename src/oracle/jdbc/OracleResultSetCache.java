package oracle.jdbc;

import java.io.IOException;

public abstract interface OracleResultSetCache
{
  public abstract void put(int paramInt1, int paramInt2, Object paramObject)
    throws IOException;

  public abstract Object get(int paramInt1, int paramInt2)
    throws IOException;

  public abstract void remove(int paramInt)
    throws IOException;

  public abstract void remove(int paramInt1, int paramInt2)
    throws IOException;

  public abstract void clear()
    throws IOException;

  public abstract void close()
    throws IOException;
}