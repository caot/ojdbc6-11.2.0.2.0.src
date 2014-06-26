package oracle.jdbc.oracore;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.Map;
import oracle.jdbc.driver.DatabaseError;
import oracle.jdbc.internal.OracleConnection;
import oracle.sql.Datum;
import oracle.sql.StructDescriptor;
import oracle.sql.TypeDescriptor;

public abstract class OracleType
  implements Serializable
{
  static final long serialVersionUID = -4124152314660261528L;
  public static final int STYLE_ARRAY_LENGTH = 0;
  public static final int STYLE_DATUM = 1;
  public static final int STYLE_JAVA = 2;
  public static final int STYLE_RAWBYTE = 3;
  public static final int STYLE_INT = 4;
  public static final int STYLE_DOUBLE = 5;
  public static final int STYLE_FLOAT = 6;
  public static final int STYLE_LONG = 7;
  public static final int STYLE_SHORT = 8;
  public static final int STYLE_SKIP = 9;
  static final int FORMAT_ADT_ATTR = 1;
  static final int FORMAT_COLL_ELEM = 2;
  static final int FORMAT_COLL_ELEM_NO_INDICATOR = 3;
  int typeCode;
  int dbTypeCode;
  boolean metaDataInitialized = false;

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  public OracleType()
  {
  }

  public OracleType(int paramInt)
  {
    this();

    this.typeCode = paramInt;
  }

  public boolean isInHierarchyOf(OracleType paramOracleType)
    throws SQLException
  {
    return false;
  }

  public boolean isInHierarchyOf(StructDescriptor paramStructDescriptor)
    throws SQLException
  {
    return false;
  }

  public boolean isObjectType()
  {
    return false;
  }

  public TypeDescriptor getTypeDescriptor()
  {
    return null;
  }

  public abstract Datum toDatum(Object paramObject, OracleConnection paramOracleConnection)
    throws SQLException;

  public Datum[] toDatumArray(Object paramObject, OracleConnection paramOracleConnection, long paramLong, int paramInt)
    throws SQLException
  {
    Datum[] arrayOfDatum = null;

    if (paramObject != null)
    {
      Object[] localObject;
      if ((paramObject instanceof Object[]))
      {
        localObject = (Object[])paramObject;

        int i = (int)(paramInt == -1 ? localObject.length : Math.min(localObject.length - paramLong + 1L, paramInt));

        arrayOfDatum = new Datum[i];

        for (int j = 0; j < i; j++)
          arrayOfDatum[j] = toDatum(localObject[((int)paramLong + j - 1)], paramOracleConnection);
      }
      else
      {
        SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 59, paramObject);
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }
    }
    return arrayOfDatum;
  }

  public void setTypeCode(int paramInt)
  {
    this.typeCode = paramInt;
  }

  public int getTypeCode()
    throws SQLException
  {
    return this.typeCode;
  }

  public void setDBTypeCode(int paramInt)
  {
    this.dbTypeCode = paramInt;
  }

  public int getDBTypeCode()
    throws SQLException
  {
    return this.dbTypeCode;
  }

  public void parseTDSrec(TDSReader paramTDSReader)
    throws SQLException
  {
  }

  protected Object unpickle81rec(PickleContext paramPickleContext, int paramInt, Map paramMap)
    throws SQLException
  {
    if (paramInt == 9)
    {
      paramPickleContext.skipDataValue();

      return null;
    }

    byte[] arrayOfByte = paramPickleContext.readDataValue();

    return toObject(arrayOfByte, paramInt, paramMap);
  }

  protected Object unpickle81rec(PickleContext paramPickleContext, byte paramByte, int paramInt, Map paramMap)
    throws SQLException
  {
    if (paramInt == 9)
    {
      paramPickleContext.skipDataValue();

      return null;
    }

    byte[] arrayOfByte = paramPickleContext.readDataValue(paramByte);

    return toObject(arrayOfByte, paramInt, paramMap);
  }

  protected Datum unpickle81datumAsNull(PickleContext paramPickleContext, byte paramByte1, byte paramByte2)
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 1);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  protected Object toObject(byte[] paramArrayOfByte, int paramInt, Map paramMap)
    throws SQLException
  {
    return null;
  }

  protected int pickle81(PickleContext paramPickleContext, Datum paramDatum)
    throws SQLException
  {
    int i = paramPickleContext.writeLength((int)paramDatum.getLength());

    i += paramPickleContext.writeData(paramDatum.shareBytes());

    return i;
  }

  void writeSerializedFields(ObjectOutputStream paramObjectOutputStream)
    throws IOException
  {
    writeObject(paramObjectOutputStream);
  }

  void readSerializedFields(ObjectInputStream paramObjectInputStream)
    throws IOException, ClassNotFoundException
  {
    readObject(paramObjectInputStream);
  }

  private void writeObject(ObjectOutputStream paramObjectOutputStream)
    throws IOException
  {
    paramObjectOutputStream.writeInt(0);
    paramObjectOutputStream.writeInt(0);
    paramObjectOutputStream.writeInt(0);
    paramObjectOutputStream.writeInt(0);
    paramObjectOutputStream.writeInt(this.typeCode);
    paramObjectOutputStream.writeInt(this.dbTypeCode);
    paramObjectOutputStream.writeBoolean(this.metaDataInitialized);
  }

  private void readObject(ObjectInputStream paramObjectInputStream)
    throws IOException, ClassNotFoundException
  {
    int i = paramObjectInputStream.readInt();
    int j = paramObjectInputStream.readInt();
    int k = paramObjectInputStream.readInt();
    int m = paramObjectInputStream.readInt();
    this.typeCode = paramObjectInputStream.readInt();
    this.dbTypeCode = paramObjectInputStream.readInt();
    this.metaDataInitialized = paramObjectInputStream.readBoolean();
  }

  public void setConnection(OracleConnection paramOracleConnection)
    throws SQLException
  {
  }

  public boolean isNCHAR()
    throws SQLException
  {
    return false;
  }

  public int getPrecision()
    throws SQLException
  {
    return 0;
  }

  public int getScale()
    throws SQLException
  {
    return 0;
  }

  public void initMetadataRecursively()
    throws SQLException
  {
  }

  public void initNamesRecursively()
    throws SQLException
  {
  }

  public void initChildNamesRecursively(Map paramMap)
    throws SQLException
  {
  }

  public void cacheDescriptor()
    throws SQLException
  {
  }

  public void setNames(String paramString1, String paramString2)
    throws SQLException
  {
  }

  public String toXMLString()
    throws SQLException
  {
    StringWriter localStringWriter = new StringWriter();
    PrintWriter localPrintWriter = new PrintWriter(localStringWriter);
    printXMLHeader(localPrintWriter);
    printXML(localPrintWriter, 0);
    return localStringWriter.getBuffer().substring(0);
  }

  public void printXML(PrintStream paramPrintStream)
    throws SQLException
  {
    PrintWriter localPrintWriter = new PrintWriter(paramPrintStream, true);
    printXMLHeader(localPrintWriter);
    printXML(localPrintWriter, 0);
  }

  void printXMLHeader(PrintWriter paramPrintWriter)
    throws SQLException
  {
    paramPrintWriter.println("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
  }

  public void printXML(PrintWriter paramPrintWriter, int paramInt)
    throws SQLException
  {
    printXML(paramPrintWriter, paramInt, false);
  }

  public void printXML(PrintWriter paramPrintWriter, int paramInt, boolean paramBoolean)
    throws SQLException
  {
    for (int i = 0; i < paramInt; i++) paramPrintWriter.print("  ");
    paramPrintWriter.println("<OracleType typecode=\"" + this.typeCode + "\"" + " />");
  }

  protected OracleConnection getConnectionDuringExceptionHandling()
  {
    return null;
  }
}