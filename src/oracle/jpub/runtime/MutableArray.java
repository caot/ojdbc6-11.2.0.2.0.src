package oracle.jpub.runtime;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import oracle.jdbc.driver.DatabaseError;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import oracle.sql.BFILE;
import oracle.sql.BINARY_DOUBLE;
import oracle.sql.BINARY_FLOAT;
import oracle.sql.BLOB;
import oracle.sql.CHAR;
import oracle.sql.CLOB;
import oracle.sql.CustomDatum;
import oracle.sql.CustomDatumFactory;
import oracle.sql.DATE;
import oracle.sql.Datum;
import oracle.sql.INTERVALDS;
import oracle.sql.INTERVALYM;
import oracle.sql.NUMBER;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.RAW;
import oracle.sql.TIMESTAMP;
import oracle.sql.TIMESTAMPLTZ;
import oracle.sql.TIMESTAMPTZ;

public class MutableArray
{
  int length;
  Object[] elements;
  Datum[] datums;
  ARRAY pickled;
  boolean pickledCorrect;
  int sqlType;
  ORADataFactory factory;
  CustomDatumFactory old_factory;
  boolean isNChar;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  public MutableArray(int paramInt, ARRAY paramARRAY, ORADataFactory paramORADataFactory)
  {
    this.length = -1;
    this.elements = null;
    this.datums = null;
    this.pickled = paramARRAY;
    this.pickledCorrect = true;
    this.isNChar = false;
    this.sqlType = paramInt;
    this.factory = paramORADataFactory;
  }

  public MutableArray(int paramInt, Datum[] paramArrayOfDatum, ORADataFactory paramORADataFactory)
  {
    this.sqlType = paramInt;
    this.factory = paramORADataFactory;
    this.isNChar = false;

    setDatumArray(paramArrayOfDatum);
  }

  public MutableArray(int paramInt, Object[] paramArrayOfObject, ORADataFactory paramORADataFactory)
  {
    this.sqlType = paramInt;
    this.factory = paramORADataFactory;
    this.isNChar = false;

    setObjectArray(paramArrayOfObject);
  }

  public MutableArray(int paramInt, double[] paramArrayOfDouble, ORADataFactory paramORADataFactory)
  {
    this.sqlType = paramInt;
    this.factory = paramORADataFactory;
    this.isNChar = false;

    setArray(paramArrayOfDouble);
  }

  public MutableArray(int paramInt, int[] paramArrayOfInt, ORADataFactory paramORADataFactory)
  {
    this.sqlType = paramInt;
    this.factory = paramORADataFactory;
    this.isNChar = false;

    setArray(paramArrayOfInt);
  }

  public MutableArray(int paramInt, float[] paramArrayOfFloat, ORADataFactory paramORADataFactory)
  {
    this.sqlType = paramInt;
    this.factory = paramORADataFactory;
    this.isNChar = false;

    setArray(paramArrayOfFloat);
  }

  public MutableArray(int paramInt, short[] paramArrayOfShort, ORADataFactory paramORADataFactory)
  {
    this.sqlType = paramInt;
    this.factory = paramORADataFactory;
    this.isNChar = false;

    setArray(paramArrayOfShort);
  }

  public MutableArray(ARRAY paramARRAY, int paramInt, CustomDatumFactory paramCustomDatumFactory)
  {
    this.length = -1;
    this.elements = null;
    this.datums = null;
    this.pickled = paramARRAY;
    this.pickledCorrect = true;
    this.sqlType = paramInt;
    this.old_factory = paramCustomDatumFactory;
    this.isNChar = false;
  }

  public MutableArray(Datum[] paramArrayOfDatum, int paramInt, CustomDatumFactory paramCustomDatumFactory)
  {
    this.sqlType = paramInt;
    this.old_factory = paramCustomDatumFactory;
    this.isNChar = false;

    setDatumArray(paramArrayOfDatum);
  }

  public MutableArray(Object[] paramArrayOfObject, int paramInt, CustomDatumFactory paramCustomDatumFactory)
  {
    this.sqlType = paramInt;
    this.old_factory = paramCustomDatumFactory;
    this.isNChar = false;

    setObjectArray(paramArrayOfObject);
  }

  public MutableArray(double[] paramArrayOfDouble, int paramInt, CustomDatumFactory paramCustomDatumFactory)
  {
    this.sqlType = paramInt;
    this.old_factory = paramCustomDatumFactory;
    this.isNChar = false;

    setArray(paramArrayOfDouble);
  }

  public MutableArray(int[] paramArrayOfInt, int paramInt, CustomDatumFactory paramCustomDatumFactory)
  {
    this.sqlType = paramInt;
    this.old_factory = paramCustomDatumFactory;
    this.isNChar = false;

    setArray(paramArrayOfInt);
  }

  public MutableArray(float[] paramArrayOfFloat, int paramInt, CustomDatumFactory paramCustomDatumFactory)
  {
    this.sqlType = paramInt;
    this.old_factory = paramCustomDatumFactory;
    this.isNChar = false;

    setArray(paramArrayOfFloat);
  }

  public MutableArray(short[] paramArrayOfShort, int paramInt, CustomDatumFactory paramCustomDatumFactory)
  {
    this.sqlType = paramInt;
    this.old_factory = paramCustomDatumFactory;
    this.isNChar = false;

    setArray(paramArrayOfShort);
  }

  public Datum toDatum(Connection paramConnection, String paramString)
    throws SQLException
  {
    if (!this.pickledCorrect)
    {
      this.pickled = new ARRAY(ArrayDescriptor.createDescriptor(paramString, paramConnection), paramConnection, getDatumArray(paramConnection));

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

  public Object[] getOracleArray()
    throws SQLException
  {
    return getOracleArray(0L, 2147483647);
  }

  public Object[] getOracleArray(long paramLong, int paramInt)
    throws SQLException
  {
    int i = sliceLength(paramLong, paramInt);

    if (i < 0) {
      return null;
    }
    Object localObject = null;

    switch (this.sqlType)
    {
    case -13:
      localObject = new BFILE[i];

      break;
    case 2004:
      localObject = new BLOB[i];

      break;
    case 1:
    case 12:
      localObject = new CHAR[i];

      break;
    case 2005:
      localObject = new CLOB[i];

      break;
    case 91:
      localObject = new DATE[i];

      break;
    case 93:
      localObject = new TIMESTAMP[i];

      break;
    case -101:
      localObject = new TIMESTAMPTZ[i];

      break;
    case -102:
      localObject = new TIMESTAMPLTZ[i];

      break;
    case -104:
      localObject = new INTERVALDS[i];

      break;
    case -103:
      localObject = new INTERVALYM[i];

      break;
    case 2:
    case 3:
    case 4:
    case 5:
    case 6:
    case 7:
    case 8:
      localObject = new NUMBER[i];

      break;
    case -2:
      localObject = new RAW[i];

      break;
    case 100:
      localObject = new BINARY_FLOAT[i];

      break;
    case 101:
      localObject = new BINARY_DOUBLE[i];

      break;
    case 0:
    case 2002:
    case 2003:
    case 2006:
    case 2007:
      if (this.old_factory == null)
      {
        localObject = new ORAData[i];
      }
      else
      {
        localObject = new CustomDatum[i];
      }

      break;
    default:
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 48);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    return getOracleArray(paramLong, (Object[])localObject);
  }

  public Object[] getOracleArray(long paramLong, Object[] paramArrayOfObject)
    throws SQLException
  {
    if (paramArrayOfObject == null) {
      return null;
    }
    int i = sliceLength(paramLong, paramArrayOfObject.length);
    int j = (int)paramLong;

    if (i != paramArrayOfObject.length)
      return null;
    int k;
    if ((this.sqlType == 2002) || (this.sqlType == 2007) || (this.sqlType == 2003) || (this.sqlType == 2006) || (this.sqlType == 0))
    {
      if (this.old_factory == null)
      {
        for (k = 0; k < i; k++) {
          paramArrayOfObject[k] = this.factory.create(getDatumElement(j++, null), this.sqlType);
        }
      }
      else {
        for (k = 0; k < i; k++) {
          paramArrayOfObject[k] = this.old_factory.create(getDatumElement(j++, null), this.sqlType);
        }
      }
    }
    else {
      for (k = 0; k < i; k++)
        paramArrayOfObject[k] = getDatumElement(j++, null);
    }
    return paramArrayOfObject;
  }

  public Object[] getOracleArray(Object[] paramArrayOfObject)
    throws SQLException
  {
    return getOracleArray(0L, paramArrayOfObject);
  }

  public Object[] getObjectArray()
    throws SQLException
  {
    return getObjectArray(0L, 2147483647);
  }

  public Object[] getObjectArray(long paramLong, int paramInt)
    throws SQLException
  {
    int i = sliceLength(paramLong, paramInt);

    if (i < 0)
      return null;
    Object localObject;
    switch (this.sqlType)
    {
    case 1:
    case 12:
      localObject = new String[i];

      break;
    case 91:
      localObject = new Date[i];

      break;
    case 93:
      localObject = new Timestamp[i];

      break;
    case 2:
    case 3:
      localObject = new BigDecimal[i];

      break;
    case 6:
    case 8:
      localObject = new Double[i];

      break;
    case 4:
    case 5:
      localObject = new Integer[i];

      break;
    case 7:
      localObject = new Float[i];

      break;
    case -2:
      localObject = new byte[i][];

      break;
    default:
      return getOracleArray(paramLong, paramInt);
    }

    return getObjectArray(paramLong, (Object[])localObject);
  }

  public Object[] getObjectArray(long paramLong, Object[] paramArrayOfObject)
    throws SQLException
  {
    if (paramArrayOfObject == null) {
      return null;
    }
    int i = sliceLength(paramLong, paramArrayOfObject.length);
    int j = (int)paramLong;

    if (i != paramArrayOfObject.length) {
      return null;
    }
    switch (this.sqlType)
    {
    case -2:
    case 1:
    case 2:
    case 3:
    case 4:
    case 5:
    case 6:
    case 7:
    case 8:
    case 12:
    case 91:
    case 93:
      for (int k = 0; k < i; k++) {
        paramArrayOfObject[k] = getObjectElement(j++);
      }
      return paramArrayOfObject;
    }

    return getOracleArray(paramLong, paramArrayOfObject);
  }

  public Object[] getObjectArray(Object[] paramArrayOfObject)
    throws SQLException
  {
    return getObjectArray(0L, paramArrayOfObject);
  }

  public Object getArray()
    throws SQLException
  {
    return getArray(0L, 2147483647);
  }

  public Object getArray(long paramLong, int paramInt)
    throws SQLException
  {
    int i = sliceLength(paramLong, paramInt);
    int j = (int)paramLong;

    if (i < 0)
      return null;
    Object localObject;
    int k;
    switch (this.sqlType)
    {
    case 6:
    case 8:
    case 101:
      localObject = new double[i];

      for (k = 0; k < i; k++) {
        localObject[k] = ((Double)getObjectElement(j++)).doubleValue();
      }
      return localObject;
    case 100:
      localObject = new float[i];

      for (k = 0; k < i; k++) {
        localObject[k] = ((Float)getObjectElement(j++)).floatValue();
      }
      return localObject;
    case 4:
      localObject = new int[i];

      for (k = 0; k < i; k++) {
        localObject[k] = ((Integer)getObjectElement(j++)).intValue();
      }
      return localObject;
    case 5:
      localObject = new short[i];

      for (k = 0; k < i; k++) {
        localObject[k] = ((short)((Integer)getObjectElement(j++)).intValue());
      }
      return localObject;
    case 7:
      localObject = new float[i];

      for (k = 0; k < i; k++) {
        localObject[k] = ((Float)getObjectElement(j++)).floatValue();
      }
      return localObject;
    }

    return getObjectArray(paramLong, paramInt);
  }

  public void setOracleArray(Object[] paramArrayOfObject)
  {
    if ((this.factory == null) && (this.old_factory == null))
      setDatumArray((Datum[])paramArrayOfObject);
    else
      setObjectArray(paramArrayOfObject);
  }

  public void setOracleArray(Object[] paramArrayOfObject, long paramLong)
    throws SQLException
  {
    if ((this.factory == null) && (this.old_factory == null))
      setDatumArray((Datum[])paramArrayOfObject, paramLong);
    else
      setObjectArray(paramArrayOfObject, paramLong);
  }

  public void setObjectArray(Object[] paramArrayOfObject)
  {
    if (paramArrayOfObject == null) {
      setNullArray();
    }
    else {
      setArrayGeneric(paramArrayOfObject.length);

      this.elements = new Object[this.length];

      for (int i = 0; i < this.length; i++)
        this.elements[i] = paramArrayOfObject[i];
    }
  }

  public void setObjectArray(Object[] paramArrayOfObject, long paramLong)
    throws SQLException
  {
    if (paramArrayOfObject == null) {
      return;
    }
    int i = sliceLength(paramLong, paramArrayOfObject.length);
    int j = (int)paramLong;

    for (int k = 0; k < i; k++)
    {
      setObjectElement(paramArrayOfObject[k], j++);
    }
  }

  public void setArray(double[] paramArrayOfDouble)
  {
    if (paramArrayOfDouble == null) {
      setNullArray();
    }
    else {
      setArrayGeneric(paramArrayOfDouble.length);

      this.elements = new Object[this.length];

      for (int i = 0; i < this.length; i++)
        this.elements[i] = Double.valueOf(paramArrayOfDouble[i]);
    }
  }

  public void setArray(double[] paramArrayOfDouble, long paramLong)
    throws SQLException
  {
    if (paramArrayOfDouble == null) {
      return;
    }
    int i = sliceLength(paramLong, paramArrayOfDouble.length);
    int j = (int)paramLong;

    for (int k = 0; k < i; k++)
    {
      setObjectElement(Double.valueOf(paramArrayOfDouble[k]), j++);
    }
  }

  public void setArray(int[] paramArrayOfInt)
  {
    if (paramArrayOfInt == null) {
      setNullArray();
    }
    else {
      setArrayGeneric(paramArrayOfInt.length);

      this.elements = new Object[this.length];

      for (int i = 0; i < this.length; i++)
        this.elements[i] = Integer.valueOf(paramArrayOfInt[i]);
    }
  }

  public void setArray(int[] paramArrayOfInt, long paramLong)
    throws SQLException
  {
    if (paramArrayOfInt == null) {
      return;
    }
    int i = sliceLength(paramLong, paramArrayOfInt.length);
    int j = (int)paramLong;

    for (int k = 0; k < i; k++)
    {
      setObjectElement(Integer.valueOf(paramArrayOfInt[k]), j++);
    }
  }

  public void setArray(float[] paramArrayOfFloat)
  {
    if (paramArrayOfFloat == null) {
      setNullArray();
    }
    else {
      setArrayGeneric(paramArrayOfFloat.length);

      this.elements = new Object[this.length];

      for (int i = 0; i < this.length; i++)
        this.elements[i] = Float.valueOf(paramArrayOfFloat[i]);
    }
  }

  public void setArray(float[] paramArrayOfFloat, long paramLong)
    throws SQLException
  {
    if (paramArrayOfFloat == null) {
      return;
    }
    int i = sliceLength(paramLong, paramArrayOfFloat.length);
    int j = (int)paramLong;

    for (int k = 0; k < i; k++)
    {
      setObjectElement(Float.valueOf(paramArrayOfFloat[k]), j++);
    }
  }

  public void setArray(short[] paramArrayOfShort)
  {
    if (paramArrayOfShort == null) {
      setNullArray();
    }
    else {
      setArrayGeneric(paramArrayOfShort.length);

      this.elements = new Object[this.length];

      for (int i = 0; i < this.length; i++)
        this.elements[i] = Integer.valueOf(paramArrayOfShort[i]);
    }
  }

  public void setArray(short[] paramArrayOfShort, long paramLong)
    throws SQLException
  {
    if (paramArrayOfShort == null) {
      return;
    }
    int i = sliceLength(paramLong, paramArrayOfShort.length);
    int j = (int)paramLong;

    for (int k = 0; k < i; k++)
    {
      setObjectElement(Integer.valueOf(paramArrayOfShort[k]), j++);
    }
  }

  public Object getObjectElement(long paramLong)
    throws SQLException
  {
    Object localObject = getLazyArray()[((int)paramLong)];

    if (localObject == null)
    {
      Datum localDatum;
      if (this.old_factory == null)
      {
        localDatum = getLazyOracleArray()[((int)paramLong)];

        localObject = Util.convertToObject(localDatum, this.sqlType, this.factory);
        this.elements[((int)paramLong)] = localObject;

        if (Util.isMutable(localDatum, this.factory))
          resetOracleElement(paramLong);
      }
      else
      {
        localDatum = getLazyOracleArray()[((int)paramLong)];

        localObject = Util.convertToObject(localDatum, this.sqlType, this.old_factory);
        this.elements[((int)paramLong)] = localObject;

        if (Util.isMutable(localDatum, this.old_factory)) {
          resetOracleElement(paramLong);
        }
      }
    }
    return localObject;
  }

  public Object getOracleElement(long paramLong)
    throws SQLException
  {
    if ((this.factory == null) && (this.old_factory == null))
    {
      Datum localDatum = getDatumElement(paramLong, null);

      if (Util.isMutable(localDatum, this.factory)) {
        this.pickledCorrect = false;
      }
      return localDatum;
    }

    return getObjectElement(paramLong);
  }

  public void setObjectElement(Object paramObject, long paramLong)
    throws SQLException
  {
    if (paramObject == null)
    {
      getLazyOracleArray();
    }

    resetOracleElement(paramLong);

    getLazyArray()[((int)paramLong)] = paramObject;
  }

  public void setOracleElement(Object paramObject, long paramLong)
    throws SQLException
  {
    if ((this.factory == null) && (this.old_factory == null))
      setDatumElement((Datum)paramObject, paramLong);
    else
      setObjectElement(paramObject, paramLong);
  }

  public String getBaseTypeName()
    throws SQLException
  {
    return this.pickled.getBaseTypeName();
  }

  public int getBaseType()
    throws SQLException
  {
    return this.pickled.getBaseType();
  }

  public ArrayDescriptor getDescriptor()
    throws SQLException
  {
    return this.pickled.getDescriptor();
  }

  Datum[] getDatumArray(Connection paramConnection)
    throws SQLException
  {
    if (this.length < 0) {
      getLazyOracleArray();
    }
    if (this.datums == null) {
      return null;
    }
    Datum[] arrayOfDatum = new Datum[this.length];

    for (int i = 0; i < this.length; i++) {
      arrayOfDatum[i] = getDatumElement(i, paramConnection);
    }
    return arrayOfDatum;
  }

  void setDatumArray(Datum[] paramArrayOfDatum)
  {
    if (paramArrayOfDatum == null) {
      setNullArray();
    }
    else {
      this.length = paramArrayOfDatum.length;
      this.elements = null;
      this.datums = ((Datum[])paramArrayOfDatum.clone());
      this.pickled = null;
      this.pickledCorrect = false;
    }
  }

  void setDatumArray(Datum[] paramArrayOfDatum, long paramLong)
    throws SQLException
  {
    if (paramArrayOfDatum == null) {
      return;
    }
    int i = sliceLength(paramLong, paramArrayOfDatum.length);
    int j = (int)paramLong;

    for (int k = 0; k < i; k++)
    {
      setDatumElement(paramArrayOfDatum[k], j++);
    }
  }

  Datum getDatumElement(long paramLong, Connection paramConnection)
    throws SQLException
  {
    Datum localDatum = getLazyOracleArray()[((int)paramLong)];

    if (localDatum == null)
    {
      Object localObject = getLazyArray()[((int)paramLong)];

      localDatum = Util.convertToOracle(localObject, paramConnection, this.isNChar);
      this.datums[((int)paramLong)] = localDatum;
    }
    return localDatum;
  }

  void setDatumElement(Datum paramDatum, long paramLong)
    throws SQLException
  {
    resetElement(paramLong);

    getLazyOracleArray()[((int)paramLong)] = paramDatum;
    this.pickledCorrect = false;
  }

  void resetElement(long paramLong)
    throws SQLException
  {
    if (this.elements != null)
    {
      this.elements[((int)paramLong)] = null;
    }
  }

  void setNullArray()
  {
    this.length = -1;
    this.elements = null;
    this.datums = null;
    this.pickled = null;
    this.pickledCorrect = false;
  }

  void setArrayGeneric(int paramInt)
  {
    this.length = paramInt;
    this.datums = new Datum[paramInt];
    this.pickled = null;
    this.pickledCorrect = false;
  }

  public int length()
    throws SQLException
  {
    if (this.length < 0) {
      getLazyOracleArray();
    }
    return this.length;
  }

  public int sliceLength(long paramLong, int paramInt)
    throws SQLException
  {
    if (this.length < 0) {
      getLazyOracleArray();
    }
    if (paramLong < 0L) {
      return (int)paramLong;
    }
    return Math.min(this.length - (int)paramLong, paramInt);
  }

  void resetOracleElement(long paramLong)
    throws SQLException
  {
    if (this.datums != null)
    {
      this.datums[((int)paramLong)] = null;
    }

    this.pickledCorrect = false;
  }

  Object[] getLazyArray()
    throws SQLException
  {
    if (this.length == -1) {
      getLazyOracleArray();
    }
    if (this.elements == null)
    {
      this.elements = new Object[this.length];
    }

    return this.elements;
  }

  Datum[] getLazyOracleArray()
    throws SQLException
  {
    if (this.datums == null)
    {
      if (this.pickled != null)
      {
        this.datums = ((Datum[])this.pickled.getOracleArray());
        this.length = this.datums.length;
        this.pickledCorrect = true;

        if (this.elements != null)
        {
          for (int i = 0; i < this.length; i++)
          {
            if (this.elements[i] != null)
            {
              this.datums[i] = null;
              this.pickledCorrect = false;
            }
          }

        }

      }
      else if (this.length >= 0) {
        this.datums = new Datum[this.length];
      }
    }
    return this.datums;
  }

  public void setNChar()
  {
    this.isNChar = true;
  }

  protected oracle.jdbc.internal.OracleConnection getConnectionDuringExceptionHandling()
  {
    return null;
  }
}