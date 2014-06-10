package oracle.sql;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Reader;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;

public abstract class Datum
  implements Serializable
{
  private byte[] data;
  static final long serialVersionUID = 4645732484621936751L;

  public Datum()
  {
  }

  public Datum(byte[] paramArrayOfByte)
  {
    this.data = paramArrayOfByte;
  }

  public boolean equals(Object paramObject)
  {
    if (this == paramObject)
      return true;
    if ((paramObject == null) || (!(paramObject instanceof Datum)))
      return false;
    if (getClass() == paramObject.getClass())
    {
      Datum localDatum = (Datum)paramObject;

      if ((this.data == null) && (localDatum.data == null))
        return true;
      if (((this.data == null) && (localDatum.data != null)) || ((this.data != null) && (localDatum.data == null)))
      {
        return false;
      }
      if (this.data.length != localDatum.data.length) {
        return false;
      }
      for (int i = 0; i < this.data.length; i++)
      {
        if (this.data[i] != localDatum.data[i]) {
          return false;
        }
      }
      return true;
    }

    return false;
  }

  public byte[] shareBytes()
  {
    return this.data;
  }

  public long getLength()
  {
    if (null == this.data) {
      return 0L;
    }
    return this.data.length;
  }

  public void setBytes(byte[] paramArrayOfByte)
  {
    int i = paramArrayOfByte.length;
    this.data = new byte[i];
    System.arraycopy(paramArrayOfByte, 0, this.data, 0, i);
  }

  public void setShareBytes(byte[] paramArrayOfByte)
  {
    this.data = paramArrayOfByte;
  }

  public byte[] getBytes()
  {
    byte[] arrayOfByte = new byte[this.data.length];
    System.arraycopy(this.data, 0, arrayOfByte, 0, this.data.length);
    return arrayOfByte;
  }

  public InputStream getStream()
  {
    return new ByteArrayInputStream(this.data);
  }

  public String stringValue()
    throws SQLException
  {
    throw new SQLException("Conversion to String failed");
  }

  public String stringValue(Connection paramConnection)
    throws SQLException
  {
    return stringValue();
  }

  public boolean booleanValue()
    throws SQLException
  {
    throw new SQLException("Conversion to boolean failed");
  }

  public int intValue()
    throws SQLException
  {
    throw new SQLException("Conversion to integer failed");
  }

  public long longValue()
    throws SQLException
  {
    throw new SQLException("Conversion to long failed");
  }

  public float floatValue()
    throws SQLException
  {
    throw new SQLException("Conversion to float failed");
  }

  public double doubleValue()
    throws SQLException
  {
    throw new SQLException("Conversion to double failed");
  }

  public byte byteValue()
    throws SQLException
  {
    throw new SQLException("Conversion to byte failed");
  }

  public BigDecimal bigDecimalValue()
    throws SQLException
  {
    throw new SQLException("Conversion to BigDecimal failed");
  }

  public Date dateValue()
    throws SQLException
  {
    throw new SQLException("Conversion to Date failed");
  }

  public Time timeValue()
    throws SQLException
  {
    throw new SQLException("Conversion to Time failed");
  }

  public Time timeValue(Calendar paramCalendar) throws SQLException {
    throw new SQLException("Conversion to Time failed");
  }

  public Timestamp timestampValue()
    throws SQLException
  {
    throw new SQLException("Conversion to Timestamp failed");
  }

  public Timestamp timestampValue(Calendar paramCalendar) throws SQLException {
    throw new SQLException("Conversion to Timestamp failed");
  }

  public Reader characterStreamValue()
    throws SQLException
  {
    throw new SQLException("Conversion to character stream failed");
  }

  public InputStream asciiStreamValue()
    throws SQLException
  {
    throw new SQLException("Conversion to ascii stream failed");
  }

  public InputStream binaryStreamValue()
    throws SQLException
  {
    throw new SQLException("Conversion to binary stream failed");
  }

  public abstract boolean isConvertibleTo(Class paramClass);

  public abstract Object toJdbc()
    throws SQLException;

  public abstract Object makeJdbcArray(int paramInt);

  protected static int compareBytes(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
  {
    int i = paramArrayOfByte1.length;
    int j = paramArrayOfByte2.length;
    int k = 0;
    int m = Math.min(i, j);
    int n = 0;
    int i1 = 0;

    while (k < m)
    {
      n = paramArrayOfByte1[k] & 0xFF;
      i1 = paramArrayOfByte2[k] & 0xFF;

      if (n != i1)
      {
        if (n < i1) {
          return -1;
        }
        return 1;
      }

      k++;
    }

    if (i == j)
      return 0;
    if (i > j) {
      return 1;
    }
    return -1;
  }
}