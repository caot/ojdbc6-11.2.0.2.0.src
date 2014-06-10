package oracle.jdbc;

import java.sql.Connection;

public abstract interface OracleOCIFailover
{
  public static final int FO_SESSION = 1;
  public static final int FO_SELECT = 2;
  public static final int FO_NONE = 3;
  public static final int FO_TYPE_UNKNOWN = 4;
  public static final int FO_BEGIN = 1;
  public static final int FO_END = 2;
  public static final int FO_ABORT = 3;
  public static final int FO_REAUTH = 4;
  public static final int FO_ERROR = 5;
  public static final int FO_RETRY = 6;
  public static final int FO_EVENT_UNKNOWN = 7;

  public abstract int callbackFn(Connection paramConnection, Object paramObject, int paramInt1, int paramInt2);
}