package oracle.jdbc.aq;

import java.sql.SQLException;
import oracle.sql.ANYDATA;
import oracle.sql.RAW;
import oracle.sql.STRUCT;
import oracle.xdb.XMLType;

public abstract interface AQMessage
{
  public abstract byte[] getMessageId()
    throws SQLException;

  public abstract AQMessageProperties getMessageProperties()
    throws SQLException;

  public abstract void setPayload(byte[] paramArrayOfByte)
    throws SQLException;

  public abstract void setPayload(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
    throws SQLException;

  public abstract void setPayload(STRUCT paramSTRUCT)
    throws SQLException;

  public abstract void setPayload(ANYDATA paramANYDATA)
    throws SQLException;

  public abstract void setPayload(RAW paramRAW)
    throws SQLException;

  public abstract void setPayload(XMLType paramXMLType)
    throws SQLException;

  public abstract byte[] getPayload()
    throws SQLException;

  public abstract byte[] getPayloadTOID();

  public abstract STRUCT getSTRUCTPayload()
    throws SQLException;

  public abstract boolean isSTRUCTPayload()
    throws SQLException;

  public abstract ANYDATA getANYDATAPayload()
    throws SQLException;

  public abstract boolean isANYDATAPayload()
    throws SQLException;

  public abstract RAW getRAWPayload()
    throws SQLException;

  public abstract boolean isRAWPayload()
    throws SQLException;

  public abstract XMLType getXMLTypePayload()
    throws SQLException;

  public abstract boolean isXMLTypePayload()
    throws SQLException;

  public abstract String toString();
}