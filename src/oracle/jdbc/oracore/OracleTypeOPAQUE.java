package oracle.jdbc.oracore;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Map;
import oracle.jdbc.driver.DatabaseError;
import oracle.jdbc.driver.Opaqueable;
import oracle.jdbc.internal.OracleConnection;
import oracle.sql.Datum;
import oracle.sql.OPAQUE;
import oracle.sql.OpaqueDescriptor;
import oracle.sql.StructDescriptor;
import oracle.sql.TypeDescriptor;

public class OracleTypeOPAQUE extends OracleTypeADT
  implements Serializable
{
  static final long KOLOFLLB = 1L;
  static final long KOLOFLCL = 2L;
  static final long KOLOFLUB = 4L;
  static final long KOLOFLFX = 8L;
  static final long serialVersionUID = -7279638692691669378L;
  long flagBits;
  long maxLen;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  public OracleTypeOPAQUE(byte[] paramArrayOfByte, int paramInt1, int paramInt2, short paramShort, String paramString, long paramLong)
    throws SQLException
  {
    super(paramArrayOfByte, paramInt1, paramInt2, paramShort, paramString);

    this.flagBits = paramLong;
    this.flattenedAttrNum = 1;
  }

  public OracleTypeOPAQUE(String paramString, OracleConnection paramOracleConnection)
    throws SQLException
  {
    super(paramString, paramOracleConnection);
  }

  public OracleTypeOPAQUE(OracleTypeADT paramOracleTypeADT, int paramInt, OracleConnection paramOracleConnection)
    throws SQLException
  {
    super(paramOracleTypeADT, paramInt, paramOracleConnection);
  }

  public Datum toDatum(Object paramObject, OracleConnection paramOracleConnection)
    throws SQLException
  {
    if (paramObject != null)
    {
      if ((paramObject instanceof OPAQUE))
        return (OPAQUE)paramObject;
      if ((paramObject instanceof Opaqueable)) {
        return ((Opaqueable)paramObject).toOpaque();
      }

      OpaqueDescriptor localOpaqueDescriptor = createOpaqueDescriptor();

      return new OPAQUE(localOpaqueDescriptor, this.connection, paramObject);
    }

    return null;
  }

  public int getTypeCode()
  {
    return 2007;
  }

  public boolean isInHierarchyOf(OracleType paramOracleType)
    throws SQLException
  {
    if (paramOracleType == null)
      return false;
    if (!(paramOracleType instanceof OracleTypeOPAQUE)) {
      return false;
    }
    OpaqueDescriptor localOpaqueDescriptor = (OpaqueDescriptor)paramOracleType.getTypeDescriptor();

    return this.descriptor.isInHierarchyOf(localOpaqueDescriptor.getName());
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
    paramTDSReader.skipBytes(5);

    this.flagBits = paramTDSReader.readLong();
    this.maxLen = paramTDSReader.readLong();
  }

  public Datum unlinearize(byte[] paramArrayOfByte, long paramLong, Datum paramDatum, int paramInt, Map paramMap)
    throws SQLException
  {
    if (paramArrayOfByte == null) {
      return null;
    }
    if ((paramArrayOfByte[0] & 0x80) > 0)
    {
      PickleContext localPickleContext = new PickleContext(paramArrayOfByte, paramLong);

      return unpickle81(localPickleContext, (OPAQUE)paramDatum, paramInt, paramMap);
    }

    return null;
  }

  public byte[] linearize(Datum paramDatum)
    throws SQLException
  {
    return pickle81(paramDatum);
  }

  protected int pickle81(PickleContext paramPickleContext, Datum paramDatum)
    throws SQLException
  {
    OPAQUE localOPAQUE = (OPAQUE)paramDatum;
    byte[] arrayOfByte = localOPAQUE.getBytesValue();
    int i = 0;

    i += paramPickleContext.writeOpaqueImageHeader(arrayOfByte.length);
    i += paramPickleContext.writeData(arrayOfByte);

    return i;
  }

  OPAQUE unpickle81(PickleContext paramPickleContext, OPAQUE paramOPAQUE, int paramInt, Map paramMap)
    throws SQLException
  {
    return unpickle81datum(paramPickleContext, paramOPAQUE, paramInt);
  }

  protected Object unpickle81rec(PickleContext paramPickleContext, int paramInt, Map paramMap)
    throws SQLException
  {
    byte b = paramPickleContext.readByte();
    Object localObject = null;

    if (PickleContext.isElementNull(b)) {
      return null;
    }
    paramPickleContext.skipRestOfLength(b);

    switch (paramInt)
    {
    case 1:
      localObject = unpickle81datum(paramPickleContext, null);
      break;
    case 2:
      localObject = unpickle81datum(paramPickleContext, null).toJdbc();
      break;
    case 3:
      localObject = new OPAQUE(createOpaqueDescriptor(), paramPickleContext.readDataValue(), this.connection);

      break;
    case 9:
      paramPickleContext.skipDataValue();
      break;
    case 4:
    case 5:
    case 6:
    case 7:
    case 8:
    default:
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 1);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    return localObject;
  }

  private OPAQUE unpickle81datum(PickleContext paramPickleContext, OPAQUE paramOPAQUE)
    throws SQLException
  {
    return unpickle81datum(paramPickleContext, paramOPAQUE, 1);
  }

  private OPAQUE unpickle81datum(PickleContext paramPickleContext, OPAQUE paramOPAQUE, int paramInt)
    throws SQLException
  {
    paramPickleContext.skipBytes(2);

    long l = paramPickleContext.readLength(true) - 2;

    if (paramOPAQUE == null)
    {
      if (paramInt == 2)
      {
        return new OPAQUE(createOpaqueDescriptor(), this.connection, paramPickleContext.readBytes((int)l));
      }

      return new OPAQUE(createOpaqueDescriptor(), this.connection, paramPickleContext.readBytes((int)l));
    }

    paramOPAQUE.setValue(paramPickleContext.readBytes((int)l));

    return paramOPAQUE;
  }

  OpaqueDescriptor createOpaqueDescriptor()
    throws SQLException
  {
    if (this.sqlName == null) {
      return new OpaqueDescriptor(this, this.connection);
    }
    return OpaqueDescriptor.createDescriptor(this.sqlName, this.connection);
  }

  public long getMaxLength()
    throws SQLException
  {
    return this.maxLen;
  }

  public boolean isTrustedLibrary()
    throws SQLException
  {
    return (this.flagBits & 1L) != 0L;
  }

  public boolean isModeledInC()
    throws SQLException
  {
    return (this.flagBits & 0x2) != 0L;
  }

  public boolean isUnboundedSized()
    throws SQLException
  {
    return (this.flagBits & 0x4) != 0L;
  }

  public boolean isFixedSized()
    throws SQLException
  {
    return (this.flagBits & 0x8) != 0L;
  }

  private void writeObject(ObjectOutputStream paramObjectOutputStream)
    throws IOException
  {
  }

  private void readObject(ObjectInputStream paramObjectInputStream)
    throws IOException, ClassNotFoundException
  {
  }

  public void setConnection(OracleConnection paramOracleConnection)
    throws SQLException
  {
    this.connection = paramOracleConnection;
  }
}