package oracle.jdbc.oracore;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Map;
import oracle.jdbc.driver.DatabaseError;
import oracle.jdbc.internal.OracleConnection;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import oracle.sql.Datum;
import oracle.sql.OPAQUE;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;

public class OracleTypeUPT extends OracleTypeADT
  implements Serializable
{
  static final long serialVersionUID = -1994358478872378695L;
  static final byte KOPU_UPT_ADT = -6;
  static final byte KOPU_UPT_COLL = -5;
  static final byte KOPU_UPT_REFCUR = 102;
  static final byte KOTTCOPQ = 58;
  byte uptCode = 0;
  OracleNamedType realType = null;

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  protected OracleTypeUPT()
  {
  }

  public OracleTypeUPT(String paramString, OracleConnection paramOracleConnection)
    throws SQLException
  {
    super(paramString, paramOracleConnection);
  }

  public OracleTypeUPT(OracleTypeADT paramOracleTypeADT, int paramInt, OracleConnection paramOracleConnection)
    throws SQLException
  {
    super(paramOracleTypeADT, paramInt, paramOracleConnection);
  }

  public Datum toDatum(Object paramObject, OracleConnection paramOracleConnection)
    throws SQLException
  {
    if (paramObject != null) {
      return this.realType.toDatum(paramObject, paramOracleConnection);
    }
    return null;
  }

  public Datum[] toDatumArray(Object paramObject, OracleConnection paramOracleConnection, long paramLong, int paramInt)
    throws SQLException
  {
    if (paramObject != null) {
      return this.realType.toDatumArray(paramObject, paramOracleConnection, paramLong, paramInt);
    }
    return null;
  }

  public int getTypeCode()
    throws SQLException
  {
    switch (this.uptCode)
    {
    case -6:
      return this.realType.getTypeCode();
    case -5:
      return 2003;
    case 58:
      return 2007;
    }

    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 1, "Invalid type code");
    localSQLException.fillInStackTrace();
    throw localSQLException;
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

  public void parseTDSrec(TDSReader paramTDSReader)
    throws SQLException
  {
    long l = paramTDSReader.readLong();

    this.uptCode = paramTDSReader.readByte();

    paramTDSReader.addNormalPatch(l, this.uptCode, this);
  }

  protected int pickle81(PickleContext paramPickleContext, Datum paramDatum)
    throws SQLException
  {
    int i = 0;

    if (paramDatum == null)
    {
      i += paramPickleContext.writeElementNull();
    }
    else
    {
      int j = paramPickleContext.offset();

      i += paramPickleContext.writeLength(PickleContext.KOPI20_LN_MAXV + 1);

      int k = 0;

      if ((this.uptCode == -6) && (!((OracleTypeADT)this.realType).isFinalType()))
      {
        if ((paramDatum instanceof STRUCT))
        {
          k = ((STRUCT)paramDatum).getDescriptor().getOracleTypeADT().pickle81(paramPickleContext, paramDatum);
        }
        else
        {
          SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 1, "invalid upt state");
          localSQLException.fillInStackTrace();
          throw localSQLException;
        }
      }
      else {
        k = this.realType.pickle81(paramPickleContext, paramDatum);
      }
      i += k;

      paramPickleContext.patchImageLen(j, k);
    }

    return i;
  }

  protected Object unpickle81rec(PickleContext paramPickleContext, int paramInt, Map paramMap)
    throws SQLException
  {
    byte b = paramPickleContext.readByte();

    if (PickleContext.isElementNull(b))
    {
      return null;
    }
    if (paramInt == 9)
    {
      paramPickleContext.skipBytes(paramPickleContext.readRestOfLength(b));

      return null;
    }

    paramPickleContext.skipRestOfLength(b);

    return unpickle81UPT(paramPickleContext, paramInt, paramMap);
  }

  protected Object unpickle81rec(PickleContext paramPickleContext, byte paramByte, int paramInt, Map paramMap)
    throws SQLException
  {
    long l = paramPickleContext.readRestOfLength(paramByte);

    if (paramInt == 9)
    {
      paramPickleContext.skipBytes((int)l);

      return null;
    }

    return unpickle81UPT(paramPickleContext, paramInt, paramMap);
  }

  private Object unpickle81UPT(PickleContext paramPickleContext, int paramInt, Map paramMap)
    throws SQLException
  {
    SQLException localSQLException;
    switch (this.uptCode)
    {
    case -6:
      switch (paramInt)
      {
      case 1:
        return ((OracleTypeADT)this.realType).unpickle81(paramPickleContext, (STRUCT)null, 3, paramInt, paramMap);
      case 2:
        localObject = ((OracleTypeADT)this.realType).unpickle81(paramPickleContext, (STRUCT)null, 1, paramInt, paramMap);

        return localObject == null ? localObject : ((STRUCT)localObject).toJdbc(paramMap);
      case 9:
        return ((OracleTypeADT)this.realType).unpickle81(paramPickleContext, (STRUCT)null, 9, 1, paramMap);
      }

      localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 1);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    case -5:
      return ((OracleTypeCOLLECTION)this.realType).unpickle81(paramPickleContext, (ARRAY)null, paramInt == 9 ? paramInt : 3, paramInt, paramMap);
    case 58:
      switch (paramInt)
      {
      case 1:
      case 9:
        return ((OracleTypeOPAQUE)this.realType).unpickle81(paramPickleContext, (OPAQUE)null, paramInt, paramMap);
      case 2:
        localObject = ((OracleTypeOPAQUE)this.realType).unpickle81(paramPickleContext, (OPAQUE)null, paramInt, paramMap);

        return localObject == null ? localObject : ((OPAQUE)localObject).toJdbc(paramMap);
      }

      localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 1);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    Object localObject = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 1, "Unrecognized UPT code");
    ((SQLException)localObject).fillInStackTrace();
    throw ((Throwable)localObject);
  }

  protected Datum unpickle81datumAsNull(PickleContext paramPickleContext, byte paramByte1, byte paramByte2)
    throws SQLException
  {
    return null;
  }

  StructDescriptor createStructDescriptor()
    throws SQLException
  {
    StructDescriptor localStructDescriptor = null;

    if (this.sqlName == null)
      localStructDescriptor = new StructDescriptor((OracleTypeADT)this.realType, this.connection);
    else {
      localStructDescriptor = StructDescriptor.createDescriptor(this.sqlName, this.connection);
    }
    return localStructDescriptor;
  }

  ArrayDescriptor createArrayDescriptor()
    throws SQLException
  {
    ArrayDescriptor localArrayDescriptor = null;

    if (this.sqlName == null)
      localArrayDescriptor = new ArrayDescriptor((OracleTypeCOLLECTION)this.realType, this.connection);
    else {
      localArrayDescriptor = ArrayDescriptor.createDescriptor(this.sqlName, this.connection);
    }
    return localArrayDescriptor;
  }

  public OracleType getRealType()
    throws SQLException
  {
    return this.realType;
  }

  public int getNumAttrs()
    throws SQLException
  {
    return ((OracleTypeADT)this.realType).getNumAttrs();
  }

  public OracleType getAttrTypeAt(int paramInt)
    throws SQLException
  {
    return ((OracleTypeADT)this.realType).getAttrTypeAt(paramInt);
  }

  private void writeObject(ObjectOutputStream paramObjectOutputStream)
    throws IOException
  {
    paramObjectOutputStream.writeByte(this.uptCode);
    paramObjectOutputStream.writeObject(this.realType);
  }

  private void readObject(ObjectInputStream paramObjectInputStream)
    throws IOException, ClassNotFoundException
  {
    this.uptCode = paramObjectInputStream.readByte();
    this.realType = ((OracleNamedType)paramObjectInputStream.readObject());
  }

  public void setConnection(OracleConnection paramOracleConnection)
    throws SQLException
  {
    this.connection = paramOracleConnection;

    this.realType.setConnection(paramOracleConnection);
  }

  public void initChildNamesRecursively(Map paramMap)
    throws SQLException
  {
    if (this.realType != null)
    {
      this.realType.setSqlName(this.sqlName);
      this.realType.initChildNamesRecursively(paramMap);
    }
  }

  public void initMetadataRecursively()
    throws SQLException
  {
    initMetadata(this.connection);
    if (this.realType != null) this.realType.initMetadataRecursively();
  }

  public void cacheDescriptor()
    throws SQLException
  {
  }

  public void printXML(PrintWriter paramPrintWriter, int paramInt, boolean paramBoolean)
    throws SQLException
  {
    for (int i = 0; i < paramInt; i++) paramPrintWriter.print("  ");
    paramPrintWriter.println("<OracleTypeUPT sqlName=\"" + this.sqlName + "\" " + " toid=\"" + this.toid + "\" " + ">");

    if (this.realType != null)
      this.realType.printXML(paramPrintWriter, paramInt + 1, paramBoolean);
    for (i = 0; i < paramInt; i++) paramPrintWriter.print("  ");
    paramPrintWriter.println("</OracleTypeUPT>");
  }

  public void printXML(PrintWriter paramPrintWriter, int paramInt)
    throws SQLException
  {
    printXML(paramPrintWriter, paramInt, false);
  }
}