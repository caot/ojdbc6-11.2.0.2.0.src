package oracle.jdbc.driver;

import java.sql.Connection;
import java.sql.SQLException;
import oracle.jdbc.aq.AQMessage;
import oracle.jdbc.aq.AQMessageProperties;
import oracle.jdbc.oracore.OracleTypeADT;
import oracle.sql.ANYDATA;
import oracle.sql.Datum;
import oracle.sql.OPAQUE;
import oracle.sql.OpaqueDescriptor;
import oracle.sql.RAW;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;
import oracle.sql.TypeDescriptor;
import oracle.xdb.XMLType;

class AQMessageI
  implements AQMessage
{
  private byte[] id = null;
  private AQMessagePropertiesI properties = null;
  private byte[] toid = null;
  private byte[] payload;
  private STRUCT payLoadSTRUCT;
  private ANYDATA payLoadANYDATA;
  private RAW payLoadRAW;
  private XMLType payLoadXMLType;
  private Connection conn;
  private String typeName;
  private TypeDescriptor sd;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  AQMessageI(AQMessagePropertiesI paramAQMessagePropertiesI, Connection paramConnection)
  {
    this.properties = paramAQMessagePropertiesI;
    this.conn = paramConnection;
  }

  AQMessageI(AQMessagePropertiesI paramAQMessagePropertiesI)
    throws SQLException
  {
    this.properties = paramAQMessagePropertiesI;
  }

  void setTypeName(String paramString)
  {
    this.typeName = paramString;
  }

  void setTypeDescriptor(TypeDescriptor paramTypeDescriptor)
  {
    this.sd = paramTypeDescriptor;
  }

  public byte[] getMessageId()
  {
    return this.id;
  }

  void setMessageId(byte[] paramArrayOfByte)
    throws SQLException
  {
    this.id = paramArrayOfByte;
  }

  public AQMessageProperties getMessageProperties()
  {
    return this.properties;
  }

  AQMessagePropertiesI getMessagePropertiesI()
  {
    return this.properties;
  }

  public void setPayload(byte[] paramArrayOfByte)
    throws SQLException
  {
    this.payload = paramArrayOfByte;
    this.toid = TypeDescriptor.RAWTOID;
  }

  public void setPayload(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
    throws SQLException
  {
    this.payload = paramArrayOfByte1;
    this.toid = paramArrayOfByte2;
  }

  public void setPayload(STRUCT paramSTRUCT)
    throws SQLException
  {
    this.payload = paramSTRUCT.toBytes();
    this.payLoadSTRUCT = paramSTRUCT;
    this.toid = paramSTRUCT.getDescriptor().getOracleTypeADT().getTOID();
  }

  public void setPayload(ANYDATA paramANYDATA)
    throws SQLException
  {
    this.payload = paramANYDATA.toDatum(this.conn).shareBytes();
    this.payLoadANYDATA = paramANYDATA;
    this.toid = TypeDescriptor.ANYDATATOID;
  }

  public void setPayload(RAW paramRAW)
    throws SQLException
  {
    this.payload = paramRAW.shareBytes();
    this.payLoadRAW = paramRAW;
    this.toid = TypeDescriptor.RAWTOID;
  }

  public void setPayload(XMLType paramXMLType)
    throws SQLException
  {
    this.payload = paramXMLType.toBytes();
    this.payLoadXMLType = paramXMLType;
    this.toid = TypeDescriptor.XMLTYPETOID;
  }

  public byte[] getPayload()
  {
    return this.payload;
  }

  public RAW getRAWPayload()
    throws SQLException
  {
    RAW localRAW = null;
    if (this.payLoadRAW != null) {
      localRAW = this.payLoadRAW;
    } else if (isRAWPayload())
    {
      this.payLoadRAW = new RAW(this.payload);
      localRAW = this.payLoadRAW;
    }
    else
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 193);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
    return localRAW;
  }

  public boolean isRAWPayload()
    throws SQLException
  {
    if ((this.toid == null) || (this.toid.length != 16))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 252);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if (compareToid(this.toid, TypeDescriptor.RAWTOID)) {
      return true;
    }
    return false;
  }

  public STRUCT getSTRUCTPayload()
    throws SQLException
  {
    STRUCT localSTRUCT = null;
    SQLException localSQLException;
    if (!isSTRUCTPayload())
    {
      localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 193);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if (this.payLoadSTRUCT != null) {
      localSTRUCT = this.payLoadSTRUCT;
    }
    else {
      if (this.sd == null)
      {
        this.typeName = OracleTypeADT.toid2typename(this.conn, this.toid);
        this.sd = TypeDescriptor.getTypeDescriptor(this.typeName, (OracleConnection)this.conn);
      }

      if ((this.sd instanceof StructDescriptor))
      {
        localSTRUCT = new STRUCT((StructDescriptor)this.sd, this.payload, this.conn);
        this.payLoadSTRUCT = localSTRUCT;
      }
      else
      {
        localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 193);
        localSQLException.fillInStackTrace();
        throw localSQLException;
      }
    }

    return localSTRUCT;
  }

  public boolean isSTRUCTPayload()
    throws SQLException
  {
    if ((this.toid == null) || (this.toid.length != 16))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 252);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    boolean bool = true;

    int i = 1;
    for (int j = 0; j < 15; j++) {
      if (this.toid[j] != 0)
      {
        i = 0;
        break;
      }
    }
    if ((i != 0) || (isRAWPayload()) || (isANYDATAPayload())) {
      bool = false;
    }
    return bool;
  }

  public ANYDATA getANYDATAPayload()
    throws SQLException
  {
    ANYDATA localANYDATA = null;

    if (this.payLoadANYDATA != null) {
      localANYDATA = this.payLoadANYDATA;
    }
    else
    {
      OpaqueDescriptor localObject;
      if (isANYDATAPayload())
      {
        localObject = OpaqueDescriptor.createDescriptor("SYS.ANYDATA", this.conn);

        OPAQUE localOPAQUE = new OPAQUE(localObject, this.payload, this.conn);
        this.payLoadANYDATA = new ANYDATA(localOPAQUE);
        localANYDATA = this.payLoadANYDATA;
      }
      else
      {
        SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 193);
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }
    }
    return localANYDATA;
  }

  public boolean isANYDATAPayload()
    throws SQLException
  {
    if ((this.toid == null) || (this.toid.length != 16))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 252);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
    if (((this.typeName != null) && (this.typeName.equals("SYS.ANYDATA"))) || (compareToid(this.toid, TypeDescriptor.ANYDATATOID)))
    {
      return true;
    }
    return false;
  }

  public XMLType getXMLTypePayload()
    throws SQLException
  {
    XMLType localXMLType = null;

    if (this.payLoadXMLType != null) {
      localXMLType = this.payLoadXMLType;
    }
    else
    {
      Object localObject;
      if (isXMLTypePayload())
      {
        localObject = OpaqueDescriptor.createDescriptor("SYS.XMLTYPE", this.conn);

        OPAQUE localOPAQUE = new OPAQUE((OpaqueDescriptor)localObject, this.payload, this.conn);
        this.payLoadXMLType = XMLType.createXML(localOPAQUE);
        localXMLType = this.payLoadXMLType;
      }
      else
      {
        SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 193);
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }
    }
    return localXMLType;
  }

  public boolean isXMLTypePayload()
    throws SQLException
  {
    if ((this.toid == null) || (this.toid.length != 16))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 252);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
    if (((this.typeName != null) && (this.typeName.equals("SYS.XMLTYPE"))) || (compareToid(this.toid, TypeDescriptor.XMLTYPETOID)))
    {
      return true;
    }
    return false;
  }

  public byte[] getPayloadTOID()
  {
    return this.toid;
  }

  static boolean compareToid(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
  {
    boolean bool = false;

    if (paramArrayOfByte1 != null)
    {
      if (paramArrayOfByte1 == paramArrayOfByte2) {
        bool = true;
      } else if (paramArrayOfByte1.length == paramArrayOfByte2.length)
      {
        int i = 1;
        for (int j = 0; j < paramArrayOfByte1.length; j++)
          if (paramArrayOfByte1[j] != paramArrayOfByte2[j])
          {
            i = 0;
            break;
          }
        if (i != 0)
          bool = true;
      }
    }
    return bool;
  }

  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("Message Properties={");
    localStringBuffer.append(this.properties);
    localStringBuffer.append("} ");
    return localStringBuffer.toString();
  }

  protected oracle.jdbc.internal.OracleConnection getConnectionDuringExceptionHandling()
  {
    return null;
  }
}