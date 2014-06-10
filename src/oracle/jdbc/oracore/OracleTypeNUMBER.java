package oracle.jdbc.oracore;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Map;
import oracle.jdbc.driver.DatabaseError;
import oracle.jdbc.internal.OracleConnection;
import oracle.sql.Datum;
import oracle.sql.NUMBER;

public class OracleTypeNUMBER extends OracleType
  implements Serializable
{
  static final long serialVersionUID = -7182242886677299812L;
  int precision;
  int scale;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  protected OracleTypeNUMBER()
  {
  }

  protected OracleTypeNUMBER(int paramInt)
  {
    super(paramInt);
  }

  public Datum toDatum(Object paramObject, OracleConnection paramOracleConnection)
    throws SQLException
  {
    return toNUMBER(paramObject, paramOracleConnection);
  }

  public Datum[] toDatumArray(Object paramObject, OracleConnection paramOracleConnection, long paramLong, int paramInt)
    throws SQLException
  {
    return toNUMBERArray(paramObject, paramOracleConnection, paramLong, paramInt);
  }

  public void parseTDSrec(TDSReader paramTDSReader)
    throws SQLException
  {
    this.precision = paramTDSReader.readUnsignedByte();

    this.scale = paramTDSReader.readByte();
  }

  protected static Object unpickle81NativeArray(PickleContext paramPickleContext, long paramLong, int paramInt1, int paramInt2)
    throws SQLException
  {
    for (int i = 1; (i < paramLong) && (paramInt1 > 0); i++) {
      paramPickleContext.skipDataValue();
    }
    byte[] arrayOfByte = null;
    int j;
    switch (paramInt2)
    {
    case 4:
      localObject = new int[paramInt1];

      for (j = 0; j < paramInt1; j++)
      {
        if ((arrayOfByte = paramPickleContext.readDataValue()) != null) {
          localObject[j] = NUMBER.toInt(arrayOfByte);
        }

      }

      return localObject;
    case 5:
      localObject = new double[paramInt1];

      for (j = 0; j < paramInt1; j++)
      {
        if ((arrayOfByte = paramPickleContext.readDataValue()) != null) {
          localObject[j] = NUMBER.toDouble(arrayOfByte);
        }
      }
      return localObject;
    case 7:
      localObject = new long[paramInt1];

      for (j = 0; j < paramInt1; j++)
      {
        if ((arrayOfByte = paramPickleContext.readDataValue()) != null) {
          localObject[j] = NUMBER.toLong(arrayOfByte);
        }
      }
      return localObject;
    case 6:
      localObject = new float[paramInt1];

      for (j = 0; j < paramInt1; j++)
      {
        if ((arrayOfByte = paramPickleContext.readDataValue()) != null) {
          localObject[j] = NUMBER.toFloat(arrayOfByte);
        }
      }
      return localObject;
    case 8:
      localObject = new short[paramInt1];

      for (j = 0; j < paramInt1; j++)
      {
        if ((arrayOfByte = paramPickleContext.readDataValue()) != null) {
          localObject[j] = NUMBER.toShort(arrayOfByte);
        }
      }
      return localObject;
    }

    Object localObject = DatabaseError.createSqlException(null, 23);
    ((SQLException)localObject).fillInStackTrace();
    throw ((Throwable)localObject);
  }

  protected Object toObject(byte[] paramArrayOfByte, int paramInt, Map paramMap)
    throws SQLException
  {
    return toNumericObject(paramArrayOfByte, paramInt, paramMap);
  }

  static Object toNumericObject(byte[] paramArrayOfByte, int paramInt, Map paramMap)
    throws SQLException
  {
    if ((paramArrayOfByte == null) || (paramArrayOfByte.length == 0)) {
      return null;
    }
    switch (paramInt)
    {
    case 1:
      return new NUMBER(paramArrayOfByte);
    case 2:
      return NUMBER.toBigDecimal(paramArrayOfByte);
    case 3:
      return paramArrayOfByte;
    }

    SQLException localSQLException = DatabaseError.createSqlException(null, 23);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public static NUMBER toNUMBER(Object paramObject, OracleConnection paramOracleConnection)
    throws SQLException
  {
    NUMBER localNUMBER = null;

    if (paramObject != null)
    {
      try
      {
        if ((paramObject instanceof NUMBER))
          localNUMBER = (NUMBER)paramObject;
        else {
          localNUMBER = new NUMBER(paramObject);
        }
      }
      catch (SQLException localSQLException1)
      {
        SQLException localSQLException2 = DatabaseError.createSqlException(null, 59, paramObject);
        localSQLException2.fillInStackTrace();
        throw localSQLException2;
      }
    }

    return localNUMBER;
  }

  public static Datum[] toNUMBERArray(Object paramObject, OracleConnection paramOracleConnection, long paramLong, int paramInt)
    throws SQLException
  {
    Datum[] arrayOfDatum = null;

    if (paramObject != null)
    {
      if (((paramObject instanceof Object[])) && (!(paramObject instanceof char[][])))
      {
        Object[] arrayOfObject = (Object[])paramObject;

        int i = (int)(paramInt == -1 ? arrayOfObject.length : Math.min(arrayOfObject.length - paramLong + 1L, paramInt));

        arrayOfDatum = new Datum[i];

        for (int j = 0; j < i; j++)
          arrayOfDatum[j] = toNUMBER(arrayOfObject[((int)paramLong + j - 1)], paramOracleConnection);
      }
      else {
        arrayOfDatum = cArrayToNUMBERArray(paramObject, paramOracleConnection, paramLong, paramInt);
      }
    }
    return arrayOfDatum;
  }

  static Datum[] cArrayToNUMBERArray(Object paramObject, OracleConnection paramOracleConnection, long paramLong, int paramInt)
    throws SQLException
  {
    Datum[] arrayOfDatum = null;

    if (paramObject != null)
    {
      Object localObject;
      int i;
      int j;
      if ((paramObject instanceof short[]))
      {
        localObject = (short[])paramObject;
        i = (int)(paramInt == -1 ? localObject.length : Math.min(localObject.length - paramLong + 1L, paramInt));

        arrayOfDatum = new Datum[i];

        for (j = 0; j < i; j++)
          arrayOfDatum[j] = new NUMBER(localObject[((int)paramLong + j - 1)]);
      }
      else if ((paramObject instanceof int[]))
      {
        localObject = (int[])paramObject;
        i = (int)(paramInt == -1 ? localObject.length : Math.min(localObject.length - paramLong + 1L, paramInt));

        arrayOfDatum = new Datum[i];

        for (j = 0; j < i; j++)
          arrayOfDatum[j] = new NUMBER(localObject[((int)paramLong + j - 1)]);
      }
      else if ((paramObject instanceof long[]))
      {
        localObject = (long[])paramObject;
        i = (int)(paramInt == -1 ? localObject.length : Math.min(localObject.length - paramLong + 1L, paramInt));

        arrayOfDatum = new Datum[i];

        for (j = 0; j < i; j++)
          arrayOfDatum[j] = new NUMBER(localObject[((int)paramLong + j - 1)]);
      }
      else if ((paramObject instanceof float[]))
      {
        localObject = (float[])paramObject;
        i = (int)(paramInt == -1 ? localObject.length : Math.min(localObject.length - paramLong + 1L, paramInt));

        arrayOfDatum = new Datum[i];

        for (j = 0; j < i; j++)
          arrayOfDatum[j] = new NUMBER(localObject[((int)paramLong + j - 1)]);
      }
      else if ((paramObject instanceof double[]))
      {
        localObject = (double[])paramObject;
        i = (int)(paramInt == -1 ? localObject.length : Math.min(localObject.length - paramLong + 1L, paramInt));

        arrayOfDatum = new Datum[i];

        for (j = 0; j < i; j++)
          arrayOfDatum[j] = new NUMBER(localObject[((int)paramLong + j - 1)]);
      }
      else if ((paramObject instanceof boolean[]))
      {
        localObject = (boolean[])paramObject;
        i = (int)(paramInt == -1 ? localObject.length : Math.min(localObject.length - paramLong + 1L, paramInt));

        arrayOfDatum = new Datum[i];

        for (j = 0; j < i; j++) {
          arrayOfDatum[j] = new NUMBER(Boolean.valueOf(localObject[((int)paramLong + j - 1)]));
        }
      }
      else if ((paramObject instanceof char[][]))
      {
        localObject = (char[][])paramObject;
        i = (int)(paramInt == -1 ? localObject.length : Math.min(localObject.length - paramLong + 1L, paramInt));

        arrayOfDatum = new Datum[i];

        for (j = 0; j < i; j++) {
          arrayOfDatum[j] = new NUMBER(new String(localObject[((int)paramLong + j - 1)]));
        }

      }
      else
      {
        localObject = DatabaseError.createSqlException(null, 59, paramObject);
        ((SQLException)localObject).fillInStackTrace();
        throw ((Throwable)localObject);
      }
    }

    return arrayOfDatum;
  }

  public int getPrecision()
  {
    return this.precision;
  }

  public int getScale()
  {
    return this.scale;
  }

  private void writeObject(ObjectOutputStream paramObjectOutputStream)
    throws IOException
  {
    paramObjectOutputStream.writeInt(this.scale);
    paramObjectOutputStream.writeInt(this.precision);
  }

  private void readObject(ObjectInputStream paramObjectInputStream)
    throws IOException, ClassNotFoundException
  {
    this.scale = paramObjectInputStream.readInt();
    this.precision = paramObjectInputStream.readInt();
  }
}