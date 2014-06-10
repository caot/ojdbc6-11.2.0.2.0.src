package oracle.net.jdbc.TNSAddress;

import oracle.net.jdbc.nl.NLException;
import oracle.net.jdbc.nl.NVPair;

public abstract interface SchemaObject
{
  public static final int ADDR = 0;
  public static final int ADDR_LIST = 1;
  public static final int DESC = 2;
  public static final int DESC_LIST = 3;
  public static final int ALIAS = 4;
  public static final int SERVICE = 5;
  public static final int DB_SERVICE = 6;

  public abstract int isA();

  public abstract String isA_String();

  public abstract void initFromString(String paramString)
    throws NLException, SOException;

  public abstract void initFromNVPair(NVPair paramNVPair)
    throws SOException;

  public abstract String toString();
}