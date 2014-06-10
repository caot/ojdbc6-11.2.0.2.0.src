package oracle.net.resolver;

import oracle.net.jdbc.TNSAddress.SchemaObject;
import oracle.net.nt.ConnStrategy;

public abstract interface NavSchemaObject extends SchemaObject
{
  public static final boolean DEBUG = false;
  public static final String SR = "(SOURCE_ROUTE=yes)";
  public static final String HC = "(HOP_COUNT=0)";
  public static final String LB = "(LOAD_BALANCE=yes)";
  public static final String NFO = "(FAILOVER=false)";
  public static final String CD = "(CONNECT_DATA=";
  public static final String CID1v2 = "(CID=(PROGRAM=";
  public static final String CID2v2 = ")(HOST=__jdbc__)(USER=";
  public static final String CID3v2 = "))";

  public abstract void navigate(ConnStrategy paramConnStrategy, StringBuffer paramStringBuffer);

  public abstract void addToString(ConnStrategy paramConnStrategy);
}