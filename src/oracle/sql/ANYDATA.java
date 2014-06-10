package oracle.sql;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import oracle.jdbc.driver.DatabaseError;
import oracle.jdbc.driver.InternalFactory;
import oracle.jdbc.internal.OracleConnection;
import oracle.jdbc.oracore.PickleContext;

public class ANYDATA
  implements ORAData
{
  static final byte KAD_VSN = 1;
  static final byte KAD_VSN2 = 2;
  boolean isNull;
  byte[] data;
  TypeDescriptor type;
  boolean isREF = false;
  short serverCharsetId = 0;
  short serverNCharsetId = 0;
  OracleConnection connection;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  ANYDATA(TypeDescriptor paramTypeDescriptor, boolean paramBoolean1, byte[] paramArrayOfByte, boolean paramBoolean2)
  {
    this.type = paramTypeDescriptor;
    this.isNull = paramBoolean1;
    this.data = paramArrayOfByte;
    this.isREF = paramBoolean2;
  }

  public ANYDATA(OPAQUE paramOPAQUE)
    throws SQLException
  {
    byte[] arrayOfByte = paramOPAQUE.getBytesValue();
    this.connection = paramOPAQUE.getPhysicalConnection();
    if (this.connection != null)
    {
      this.serverCharsetId = this.connection.getDbCsId();
      this.serverNCharsetId = this.connection.getNCharSet();
    }
    unpickle(arrayOfByte, 0);
  }

  int unpickle(byte[] paramArrayOfByte, int paramInt)
    throws SQLException
  {
    PickleContext localPickleContext = new PickleContext(paramArrayOfByte, paramInt);
    int i = localPickleContext.readByte();
    if (i == 2) {
      localPickleContext.skipBytes(4);
    }

    short[] arrayOfShort = new short[1];
    this.type = TypeDescriptor.unpickleOpaqueTypeImage(localPickleContext, this.connection, arrayOfShort);
    if (localPickleContext.readByte() != 0)
      this.isNull = true;
    else
      this.isNull = false;
    if (!this.isNull)
    {
      j = (int)localPickleContext.readUB4();
      this.data = localPickleContext.readDataValue(j);
    }
    if (arrayOfShort[0] == 110)
      this.isREF = true;
    else
      this.isREF = false;
    int j = localPickleContext.offset();
    return j;
  }

  int pickle(byte[] paramArrayOfByte, int paramInt)
  {
    paramArrayOfByte[(paramInt++)] = 1;
    paramInt = this.type.pickleOpaqueTypeImage(paramArrayOfByte, paramInt, this.isREF);

    if (this.isNull)
      paramArrayOfByte[(paramInt++)] = 1;
    else
      paramArrayOfByte[(paramInt++)] = 0;
    if (!this.isNull)
    {
      int i = this.data.length;
      paramArrayOfByte[(paramInt++)] = ((byte)((i & 0xFF000000) >> 24 & 0xFF));
      paramArrayOfByte[(paramInt++)] = ((byte)((i & 0xFF0000) >> 16 & 0xFF));
      paramArrayOfByte[(paramInt++)] = ((byte)((i & 0xFF00) >> 8 & 0xFF));
      paramArrayOfByte[(paramInt++)] = ((byte)(i & 0xFF));
      System.arraycopy(this.data, 0, paramArrayOfByte, paramInt, i);
    }
    return paramInt;
  }

  int getImageSize()
  {
    int i = this.type.getOpaqueImageTypeSize() + 1 + 1;

    if (!this.isNull)
      i += 4 + this.data.length;
    return i;
  }

  public Datum toDatum(Connection paramConnection)
    throws SQLException
  {
    this.connection = ((OracleConnection)paramConnection);
    OpaqueDescriptor localOpaqueDescriptor = OpaqueDescriptor.createDescriptor("SYS.ANYDATA", paramConnection);

    byte[] arrayOfByte = new byte[getImageSize()];
    pickle(arrayOfByte, 0);
    OPAQUE localOPAQUE = new OPAQUE(localOpaqueDescriptor, this.connection, arrayOfByte);

    localOPAQUE.setShareBytes(localOPAQUE.toBytes());
    return localOPAQUE;
  }

  public static ANYDATA convertDatum(Datum paramDatum)
    throws SQLException
  {
    ANYDATA localANYDATA = null;
    if ((paramDatum instanceof STRUCT)) {
      localANYDATA = new ANYDATA(((STRUCT)paramDatum).getDescriptor(), false, ((STRUCT)paramDatum).toBytes(), false);
    } else if ((paramDatum instanceof ARRAY)) {
      localANYDATA = new ANYDATA(((ARRAY)paramDatum).getDescriptor(), false, ((ARRAY)paramDatum).toBytes(), false);
    } else if ((paramDatum instanceof REF)) {
      localANYDATA = new ANYDATA(((REF)paramDatum).getDescriptor(), false, ((REF)paramDatum).getBytes(), true);
    } else if ((paramDatum instanceof OPAQUE)) {
      localANYDATA = new ANYDATA(((OPAQUE)paramDatum).getDescriptor(), false, ((OPAQUE)paramDatum).toBytes(), false);
    }
    else {
      TypeDescriptor localTypeDescriptor = null;
      if ((paramDatum instanceof NUMBER))
        localTypeDescriptor = new TypeDescriptor((short)2);
      else if ((paramDatum instanceof DATE))
        localTypeDescriptor = new TypeDescriptor((short)12);
      else if ((paramDatum instanceof INTERVALDS))
        localTypeDescriptor = new TypeDescriptor((short)190);
      else if ((paramDatum instanceof INTERVALYM))
        localTypeDescriptor = new TypeDescriptor((short)189);
      else if ((paramDatum instanceof TIMESTAMPTZ))
        localTypeDescriptor = new TypeDescriptor((short)188);
      else if ((paramDatum instanceof TIMESTAMPLTZ))
        localTypeDescriptor = new TypeDescriptor((short)232);
      else if ((paramDatum instanceof TIMESTAMP))
        localTypeDescriptor = new TypeDescriptor((short)187);
      else if ((paramDatum instanceof NCLOB))
      {
        localTypeDescriptor = new TypeDescriptor((short)288);
      } else if ((paramDatum instanceof CLOB))
        localTypeDescriptor = new TypeDescriptor((short)112);
      else if ((paramDatum instanceof BLOB))
        localTypeDescriptor = new TypeDescriptor((short)113);
      else if ((paramDatum instanceof BFILE))
        localTypeDescriptor = new TypeDescriptor((short)114);
      else if ((paramDatum instanceof RAW))
        localTypeDescriptor = new TypeDescriptor((short)95);
      else if ((paramDatum instanceof BINARY_DOUBLE))
        localTypeDescriptor = new TypeDescriptor((short)101);
      else if ((paramDatum instanceof BINARY_FLOAT))
        localTypeDescriptor = new TypeDescriptor((short)100);
      else if ((paramDatum instanceof ROWID))
        localTypeDescriptor = new TypeDescriptor((short)104);
      else if ((paramDatum instanceof CHAR)) {
        localTypeDescriptor = new TypeDescriptor((short)96);
      }
      if ((paramDatum instanceof ROWID))
      {
        byte[] arrayOfByte1 = paramDatum.shareBytes();

        long[] arrayOfLong = InternalFactory.rowid2urowid(arrayOfByte1, 0, arrayOfByte1.length);
        byte[] arrayOfByte2 = new byte[13];

        arrayOfByte2[0] = 1;
        arrayOfByte2[1] = ((byte)(int)((arrayOfLong[0] & 0xFF000000) >> 24));
        arrayOfByte2[2] = ((byte)(int)((arrayOfLong[0] & 0xFF0000) >> 16));
        arrayOfByte2[3] = ((byte)(int)((arrayOfLong[0] & 0xFF00) >> 8));
        arrayOfByte2[4] = ((byte)(int)(arrayOfLong[0] & 0xFF));
        arrayOfByte2[5] = ((byte)(int)((arrayOfLong[1] & 0xFF00) >> 8));
        arrayOfByte2[6] = ((byte)(int)(arrayOfLong[1] & 0xFF));
        arrayOfByte2[7] = ((byte)(int)((arrayOfLong[2] & 0xFF000000) >> 24));
        arrayOfByte2[8] = ((byte)(int)((arrayOfLong[2] & 0xFF0000) >> 16));
        arrayOfByte2[9] = ((byte)(int)((arrayOfLong[2] & 0xFF00) >> 8));
        arrayOfByte2[10] = ((byte)(int)(arrayOfLong[2] & 0xFF));
        arrayOfByte2[11] = ((byte)(int)((arrayOfLong[3] & 0xFF00) >> 8));
        arrayOfByte2[12] = ((byte)(int)(arrayOfLong[3] & 0xFF));
        localANYDATA = new ANYDATA(localTypeDescriptor, false, arrayOfByte2, false);
      }
      else {
        localANYDATA = new ANYDATA(localTypeDescriptor, false, paramDatum.shareBytes(), false);
      }
    }

    if ((paramDatum instanceof DatumWithConnection)) {
      localANYDATA.connection = ((DatumWithConnection)paramDatum).getInternalConnection();
    }
    return localANYDATA;
  }

  public TypeDescriptor getTypeDescriptor()
  {
    return this.type;
  }

  public boolean isNull()
  {
    return this.isNull;
  }

  public byte[] getData()
  {
    return this.data;
  }

  public boolean isREF()
  {
    return this.isREF;
  }

  public String stringValue()
    throws SQLException
  {
    return stringValue(this.connection);
  }

  public String stringValue(Connection paramConnection)
    throws SQLException
  {
    String str1 = null;

    str1 = "ANYDATA TypeCode: \"" + getTypeDescriptor().getTypeCodeName();
    if (this.isREF)
      str1 = str1 + "(REF)";
    str1 = str1 + "\" - ANYDATA Value: \"";

    Datum localDatum = accessDatum();
    int i = 0;
    try
    {
      str1 = str1 + localDatum.stringValue();
      i = 1;
    }
    catch (SQLException localSQLException) {
    }
    if (i == 0)
    {
      if (((this.type.getInternalTypeCode() == 108) || (this.type.getInternalTypeCode() == 110)) && (!this.type.isTransient()))
      {
        str1 = str1 + ((StructDescriptor)this.type).getName() + "(...)";
      } else if ((this.type.getInternalTypeCode() == 122) && (!this.type.isTransient()))
      {
        str1 = str1 + ((ArrayDescriptor)this.type).getName() + "(...)";
      }
      else {
        switch (this.type.getInternalTypeCode())
        {
        case 113:
          InputStream localInputStream = ((BLOB)localDatum).getBinaryStream();
          try
          {
            String str2 = "";
            int j;
            while ((j = localInputStream.read()) != -1)
              str2 = str2 + Integer.toHexString(j);
            str1 = str1 + str2; } catch (IOException localIOException2) { } finally {
            try {
              localInputStream.close(); } catch (IOException localIOException4) {  }
          }
          break;
        case 188:
          if (paramConnection == null)
            str1 = str1 + "?";
          else {
            str1 = str1 + ((TIMESTAMPTZ)localDatum).stringValue(paramConnection);
          }
          break;
        case 232:
          if (paramConnection == null)
            str1 = str1 + "?";
          else {
            str1 = str1 + ((TIMESTAMPLTZ)localDatum).stringValue(paramConnection);
          }
          break;
        case 114:
          str1 = str1 + "bfile_dir=" + ((BFILE)localDatum).getDirAlias() + " bfile_name=" + ((BFILE)localDatum).getName();
        }

      }

    }

    str1 = str1 + "\"";

    return str1;
  }

  public Datum accessDatum()
    throws SQLException
  {
    Object localObject = null;
    if (!this.isNull)
    {
      int i = this.type.getInternalTypeCode();
      switch (i)
      {
      case 58:
        localObject = new OPAQUE((OpaqueDescriptor)this.type, this.data, this.connection);
        break;
      case 108:
        if ((this.type instanceof OpaqueDescriptor))
          localObject = new OPAQUE((OpaqueDescriptor)this.type, this.data, this.connection);
        else if (!this.isREF)
          localObject = new STRUCT((StructDescriptor)this.type, this.data, this.connection);
        else
          localObject = new REF((StructDescriptor)this.type, this.connection, this.data);
        break;
      case 122:
        localObject = new ARRAY((ArrayDescriptor)this.type, this.data, this.connection);
        break;
      case 110:
        localObject = new REF((StructDescriptor)this.type, this.connection, this.data);
        break;
      case 2:
        localObject = new NUMBER(this.data);
        break;
      case 12:
        localObject = new DATE(this.data);
        break;
      case 190:
        localObject = new INTERVALDS(this.data);
        break;
      case 189:
        localObject = new INTERVALYM(this.data);
        break;
      case 188:
        localObject = new TIMESTAMPTZ(this.data);
        break;
      case 232:
        localObject = new TIMESTAMPLTZ(this.data);
        break;
      case 187:
        localObject = new TIMESTAMP(this.data);
        break;
      case 112:
        localObject = new CLOB(this.connection, this.data);
        break;
      case 288:
        localObject = new NCLOB(this.connection, this.data);
        break;
      case 113:
        localObject = new BLOB(this.connection, this.data);
        break;
      case 114:
        localObject = new BFILE(this.connection, this.data);
        break;
      case 95:
        localObject = new RAW(this.data);
        break;
      case 101:
        localObject = new BINARY_DOUBLE(this.data);
        break;
      case 100:
        localObject = new BINARY_FLOAT(this.data);
        break;
      case 104:
        long l1 = (this.data[1] & 0xFF) << 24 | (this.data[2] & 0xFF) << 16 | (this.data[3] & 0xFF) << 8 | this.data[4] & 0xFF;

        long l2 = (this.data[5] & 0xFF) << 8 | this.data[6] & 0xFF;

        long l3 = (this.data[7] & 0xFF) << 24 | (this.data[8] & 0xFF) << 16 | (this.data[9] & 0xFF) << 8 | this.data[10] & 0xFF;

        long l4 = (this.data[11] & 0xFF) << 8 | this.data[12] & 0xFF;

        long[] arrayOfLong = new long[4];
        arrayOfLong[0] = l1;
        arrayOfLong[1] = l2;
        arrayOfLong[2] = l3;
        arrayOfLong[3] = l4;
        byte[] arrayOfByte = InternalFactory.urowid2rowid(arrayOfLong);
        localObject = new ROWID(arrayOfByte);
        break;
      case 1:
      case 9:
      case 96:
        if (this.serverCharsetId != 0)
          localObject = new CHAR(this.data, CharacterSet.make(this.serverCharsetId));
        else
          localObject = new CHAR(this.data, null);
        break;
      case 286:
      case 287:
        if (this.serverNCharsetId != 0)
          localObject = new CHAR(this.data, CharacterSet.make(this.serverNCharsetId));
        else
          localObject = new CHAR(this.data, null);
        break;
      default:
        String str = "internal typecode: " + i;

        SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 1, str);
        localSQLException.fillInStackTrace();
        throw localSQLException;
      }
    }

    return localObject;
  }

  protected OracleConnection getConnectionDuringExceptionHandling()
  {
    return this.connection;
  }
}