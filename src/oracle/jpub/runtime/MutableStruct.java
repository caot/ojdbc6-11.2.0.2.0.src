package oracle.jpub.runtime;

import java.sql.Connection;
import java.sql.SQLException;
import oracle.sql.CustomDatumFactory;
import oracle.sql.Datum;
import oracle.sql.ORADataFactory;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;

public class MutableStruct
{
  int length;
  STRUCT pickled;
  Datum[] datums;
  Object[] attributes;
  CustomDatumFactory[] old_factories;
  ORADataFactory[] factories;
  int[] sqlTypes;
  boolean pickledCorrect;
  boolean[] isNChar;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  public MutableStruct(STRUCT paramSTRUCT, int[] paramArrayOfInt, ORADataFactory[] paramArrayOfORADataFactory)
  {
    this.length = paramArrayOfORADataFactory.length;
    this.pickled = paramSTRUCT;
    this.factories = paramArrayOfORADataFactory;
    this.sqlTypes = paramArrayOfInt;
    initNChar(paramArrayOfInt.length);
    this.pickledCorrect = true;
  }

  public MutableStruct(Object[] paramArrayOfObject, int[] paramArrayOfInt, ORADataFactory[] paramArrayOfORADataFactory)
  {
    this.length = paramArrayOfORADataFactory.length;
    this.attributes = paramArrayOfObject;
    this.factories = paramArrayOfORADataFactory;
    this.sqlTypes = paramArrayOfInt;
    initNChar(paramArrayOfInt.length);
    this.pickledCorrect = false;
  }

  public MutableStruct(STRUCT paramSTRUCT, int[] paramArrayOfInt, CustomDatumFactory[] paramArrayOfCustomDatumFactory)
  {
    this.length = paramArrayOfCustomDatumFactory.length;
    this.pickled = paramSTRUCT;
    this.old_factories = paramArrayOfCustomDatumFactory;
    this.sqlTypes = paramArrayOfInt;
    initNChar(paramArrayOfInt.length);
    this.pickledCorrect = true;
  }

  public MutableStruct(Object[] paramArrayOfObject, int[] paramArrayOfInt, CustomDatumFactory[] paramArrayOfCustomDatumFactory)
  {
    this.length = paramArrayOfCustomDatumFactory.length;
    this.attributes = paramArrayOfObject;
    this.old_factories = paramArrayOfCustomDatumFactory;
    this.sqlTypes = paramArrayOfInt;
    initNChar(paramArrayOfInt.length);
    this.pickledCorrect = false;
  }

  public Datum toDatum(Connection paramConnection, String paramString)
    throws SQLException
  {
    if (!this.pickledCorrect)
    {
      this.pickled = new STRUCT(StructDescriptor.createDescriptor(paramString, paramConnection), paramConnection, getDatumAttributes(paramConnection));

      this.pickledCorrect = true;
    }

    return this.pickled;
  }

  public Datum toDatum(oracle.jdbc.OracleConnection paramOracleConnection, String paramString)
    throws SQLException
  {
    return toDatum(paramOracleConnection, paramString);
  }

  /** @deprecated */
  public Datum toDatum(oracle.jdbc.driver.OracleConnection paramOracleConnection, String paramString)
    throws SQLException
  {
    return toDatum(paramOracleConnection, paramString);
  }

  public Object getAttribute(int paramInt)
    throws SQLException
  {
    Object localObject = getLazyAttributes()[paramInt];

    if (localObject == null)
    {
      Datum localDatum = getLazyDatums()[paramInt];

      if (this.old_factories == null)
      {
        localObject = Util.convertToObject(localDatum, this.sqlTypes[paramInt], this.factories[paramInt]);
        this.attributes[paramInt] = localObject;

        if (Util.isMutable(localDatum, this.factories[paramInt]))
          resetDatum(paramInt);
      }
      else
      {
        localObject = Util.convertToObject(localDatum, this.sqlTypes[paramInt], this.old_factories[paramInt]);
        this.attributes[paramInt] = localObject;

        if (Util.isMutable(localDatum, this.old_factories[paramInt]))
          resetDatum(paramInt);
      }
    }
    return localObject;
  }

  public Object getOracleAttribute(int paramInt)
    throws SQLException
  {
    Object localObject;
    Datum localDatum;
    if (this.old_factories == null)
    {
      if (this.factories[paramInt] == null)
      {
        localObject = getDatumAttribute(paramInt, null);

        localDatum = getLazyDatums()[paramInt];

        if (Util.isMutable(localDatum, this.factories[paramInt]))
          this.pickledCorrect = false;
      }
      else {
        localObject = getAttribute(paramInt);
      }

    }
    else if (this.old_factories[paramInt] == null)
    {
      localObject = getDatumAttribute(paramInt, null);

      localDatum = getLazyDatums()[paramInt];

      if (Util.isMutable(localDatum, this.old_factories[paramInt]))
        this.pickledCorrect = false;
    }
    else {
      localObject = getAttribute(paramInt);
    }
    return localObject;
  }

  public Object[] getAttributes()
    throws SQLException
  {
    for (int i = 0; i < this.length; i++)
    {
      getAttribute(i);
    }
    return this.attributes;
  }

  public Object[] getOracleAttributes()
    throws SQLException
  {
    Object[] arrayOfObject = new Object[this.length];

    for (int i = 0; i < this.length; i++)
    {
      arrayOfObject[i] = getOracleAttribute(i);
    }
    return arrayOfObject;
  }

  public void setAttribute(int paramInt, Object paramObject)
    throws SQLException
  {
    if (paramObject == null)
    {
      getLazyDatums();
    }

    resetDatum(paramInt);

    getLazyAttributes()[paramInt] = paramObject;
  }

  public void setDoubleAttribute(int paramInt, double paramDouble)
    throws SQLException
  {
    setAttribute(paramInt, Double.valueOf(paramDouble));
  }

  public void setFloatAttribute(int paramInt, float paramFloat)
    throws SQLException
  {
    setAttribute(paramInt, Float.valueOf(paramFloat));
  }

  public void setIntAttribute(int paramInt1, int paramInt2)
    throws SQLException
  {
    setAttribute(paramInt1, Integer.valueOf(paramInt2));
  }

  public void setOracleAttribute(int paramInt, Object paramObject)
    throws SQLException
  {
    if (this.old_factories == null)
    {
      if (this.factories[paramInt] == null)
        setDatumAttribute(paramInt, (Datum)paramObject);
      else {
        setAttribute(paramInt, paramObject);
      }

    }
    else if (this.old_factories[paramInt] == null)
      setDatumAttribute(paramInt, (Datum)paramObject);
    else
      setAttribute(paramInt, paramObject);
  }

  Datum getDatumAttribute(int paramInt, Connection paramConnection)
    throws SQLException
  {
    Datum localDatum = getLazyDatums()[paramInt];

    if (localDatum == null)
    {
      Object localObject = getLazyAttributes()[paramInt];

      localDatum = Util.convertToOracle(localObject, paramConnection, isNChar(paramInt));
      this.datums[paramInt] = localDatum;
    }
    return localDatum;
  }

  void setDatumAttribute(int paramInt, Datum paramDatum)
    throws SQLException
  {
    resetAttribute(paramInt);

    getLazyDatums()[paramInt] = paramDatum;
    this.pickledCorrect = false;
  }

  Datum[] getDatumAttributes(Connection paramConnection)
    throws SQLException
  {
    for (int i = 0; i < this.length; i++)
    {
      getDatumAttribute(i, paramConnection);
    }

    return (Datum[])this.datums.clone();
  }

  void resetAttribute(int paramInt)
    throws SQLException
  {
    if (this.attributes != null)
    {
      this.attributes[paramInt] = null;
    }
  }

  void resetDatum(int paramInt)
    throws SQLException
  {
    if (this.datums != null)
    {
      this.datums[paramInt] = null;
    }

    this.pickledCorrect = false;
  }

  Object[] getLazyAttributes()
  {
    if (this.attributes == null)
    {
      this.attributes = new Object[this.length];
    }

    return this.attributes;
  }

  Datum[] getLazyDatums()
    throws SQLException
  {
    if (this.datums == null)
    {
      if (this.pickled != null)
      {
        this.datums = this.pickled.getOracleAttributes();
        this.pickledCorrect = true;

        if (this.attributes != null)
        {
          for (int i = 0; i < this.length; i++)
          {
            if (this.attributes[i] != null)
            {
              this.datums[i] = null;
              this.pickledCorrect = false;
            }
          }
        }
      }
      else
      {
        this.datums = new Datum[this.length];
      }
    }
    return this.datums;
  }

  private void initNChar(int paramInt)
  {
    this.isNChar = new boolean[paramInt];
    for (int i = 0; i < paramInt; i++)
      this.isNChar[i] = false;
  }

  public void setNChar(int paramInt)
    throws SQLException
  {
    this.isNChar[paramInt] = true;
  }

  public boolean isNChar(int paramInt)
    throws SQLException
  {
    return this.isNChar[paramInt];
  }
}