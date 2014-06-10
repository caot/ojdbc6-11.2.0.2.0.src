package oracle.sql;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLXML;
import java.sql.Struct;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Map;
import oracle.jdbc.driver.DatabaseError;

public class OracleJdbc2SQLInput
  implements SQLInput
{
  private int index;
  private Datum[] attributes;
  private Map map;
  private oracle.jdbc.OracleConnection conn;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  public OracleJdbc2SQLInput(Datum[] paramArrayOfDatum, Map paramMap, oracle.jdbc.OracleConnection paramOracleConnection)
  {
    this.attributes = paramArrayOfDatum;
    this.map = paramMap;
    this.conn = paramOracleConnection;
    this.index = 0;
  }

  public String readString()
    throws SQLException
  {
    String str = null;
    try
    {
      if (this.attributes[this.index] != null)
      {
        str = this.attributes[this.index].stringValue();
      }
    }
    finally
    {
      this.index += 1;
    }
    return str;
  }

  public boolean readBoolean()
    throws SQLException
  {
    boolean bool = false;
    try
    {
      if (this.attributes[this.index] != null)
      {
        bool = this.attributes[this.index].booleanValue();
      }
    }
    finally
    {
      this.index += 1;
    }
    return bool;
  }

  public byte readByte()
    throws SQLException
  {
    byte b = 0;
    try
    {
      if (this.attributes[this.index] != null)
      {
        b = this.attributes[this.index].byteValue();
      }
    }
    finally
    {
      this.index += 1;
    }
    return b;
  }

  public short readShort()
    throws SQLException
  {
    long l = readLong();

    if ((l > 65537L) || (l < -65538L))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 26, "readShort");
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    return (short)(int)l;
  }

  public int readInt()
    throws SQLException
  {
    int i = 0;
    try
    {
      if (this.attributes[this.index] != null)
      {
        i = this.attributes[this.index].intValue();
      }
    }
    finally
    {
      this.index += 1;
    }
    return i;
  }

  public long readLong()
    throws SQLException
  {
    long l = 0L;
    try
    {
      if (this.attributes[this.index] != null)
      {
        l = this.attributes[this.index].longValue();
      }
    }
    finally
    {
      this.index += 1;
    }
    return l;
  }

  public float readFloat()
    throws SQLException
  {
    float f = 0.0F;
    try
    {
      if (this.attributes[this.index] != null)
      {
        f = this.attributes[this.index].floatValue();
      }
    }
    finally
    {
      this.index += 1;
    }
    return f;
  }

  public double readDouble()
    throws SQLException
  {
    double d = 0.0D;
    try
    {
      if (this.attributes[this.index] != null)
      {
        d = this.attributes[this.index].doubleValue();
      }
    }
    finally
    {
      this.index += 1;
    }
    return d;
  }

  public BigDecimal readBigDecimal()
    throws SQLException
  {
    BigDecimal localBigDecimal = null;
    try
    {
      if (this.attributes[this.index] != null)
      {
        localBigDecimal = this.attributes[this.index].bigDecimalValue();
      }
    }
    finally
    {
      this.index += 1;
    }
    return localBigDecimal;
  }

  public byte[] readBytes()
    throws SQLException
  {
    byte[] arrayOfByte = null;
    try
    {
      if (this.attributes[this.index] != null)
      {
        if ((this.attributes[this.index] instanceof RAW)) {
          arrayOfByte = ((RAW)this.attributes[this.index]).shareBytes();
        }
        else
        {
          SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4, null);
          localSQLException.fillInStackTrace();
          throw localSQLException;
        }
      }

    }
    finally
    {
      this.index += 1;
    }
    return arrayOfByte;
  }

  public Date readDate()
    throws SQLException
  {
    Date localDate = null;
    try
    {
      if (this.attributes[this.index] != null)
      {
        localDate = this.attributes[this.index].dateValue();
      }
    }
    finally
    {
      this.index += 1;
    }
    return localDate;
  }

  public Time readTime()
    throws SQLException
  {
    Time localTime = null;
    try
    {
      if (this.attributes[this.index] != null)
      {
        localTime = this.attributes[this.index].timeValue();
      }
    }
    finally
    {
      this.index += 1;
    }
    return localTime;
  }

  public Timestamp readTimestamp()
    throws SQLException
  {
    Timestamp localTimestamp = null;
    try
    {
      if (this.attributes[this.index] != null)
      {
        localTimestamp = this.attributes[this.index].timestampValue();
      }
    }
    finally
    {
      this.index += 1;
    }
    return localTimestamp;
  }

  public Reader readCharacterStream()
    throws SQLException
  {
    Reader localReader = null;
    try
    {
      Datum localDatum = this.attributes[this.index];

      if (localDatum != null)
      {
        localReader = localDatum.characterStreamValue();
      }
    }
    finally
    {
      this.index += 1;
    }
    return localReader;
  }

  public InputStream readAsciiStream()
    throws SQLException
  {
    InputStream localInputStream = null;
    try
    {
      Datum localDatum = this.attributes[this.index];

      if (localDatum != null)
      {
        localInputStream = localDatum.asciiStreamValue();
      }
    }
    finally
    {
      this.index += 1;
    }
    return localInputStream;
  }

  public InputStream readBinaryStream()
    throws SQLException
  {
    InputStream localInputStream = null;
    try
    {
      Datum localDatum = this.attributes[this.index];

      if (localDatum != null)
      {
        localInputStream = localDatum.binaryStreamValue();
      }
    }
    finally
    {
      this.index += 1;
    }
    return localInputStream;
  }

  public Object readObject()
    throws SQLException
  {
    Datum localDatum = (Datum)readOracleObject();
    Object localObject = null;

    if (localDatum != null)
    {
      if ((localDatum instanceof STRUCT))
        localObject = ((STRUCT)localDatum).toJdbc(this.map);
      else {
        localObject = localDatum.toJdbc();
      }

    }

    return localObject;
  }

  public Ref readRef()
    throws SQLException
  {
    return readREF();
  }

  public Blob readBlob()
    throws SQLException
  {
    return readBLOB();
  }

  public Clob readClob()
    throws SQLException
  {
    return readCLOB();
  }

  public Array readArray()
    throws SQLException
  {
    return readARRAY();
  }

  public Struct readStruct()
    throws SQLException
  {
    return readSTRUCT();
  }

  public boolean wasNull()
    throws SQLException
  {
    if (this.index == 0)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 24);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    boolean bool = this.attributes[(this.index - 1)] == null;
    return bool;
  }

  public Object readOracleObject()
    throws SQLException
  {
    return this.attributes[(this.index++)];
  }

  public NUMBER readNUMBER()
    throws SQLException
  {
    NUMBER localNUMBER = null;
    try
    {
      if (this.attributes[this.index] != null)
      {
        if ((this.attributes[this.index] instanceof NUMBER)) {
          localNUMBER = (NUMBER)this.attributes[this.index];
        }
        else
        {
          SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4, null);
          localSQLException.fillInStackTrace();
          throw localSQLException;
        }
      }

    }
    finally
    {
      this.index += 1;
    }
    return localNUMBER;
  }

  public CHAR readCHAR()
    throws SQLException
  {
    CHAR localCHAR = null;
    try
    {
      if (this.attributes[this.index] != null)
      {
        if ((this.attributes[this.index] instanceof CHAR)) {
          localCHAR = (CHAR)this.attributes[this.index];
        }
        else
        {
          SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4, null);
          localSQLException.fillInStackTrace();
          throw localSQLException;
        }
      }

    }
    finally
    {
      this.index += 1;
    }
    return localCHAR;
  }

  public DATE readDATE()
    throws SQLException
  {
    DATE localDATE = null;
    try
    {
      if (this.attributes[this.index] != null)
      {
        if ((this.attributes[this.index] instanceof DATE)) {
          localDATE = (DATE)this.attributes[this.index];
        }
        else
        {
          SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4, null);
          localSQLException.fillInStackTrace();
          throw localSQLException;
        }
      }

    }
    finally
    {
      this.index += 1;
    }
    return localDATE;
  }

  public BFILE readBFILE()
    throws SQLException
  {
    BFILE localBFILE = null;
    try
    {
      if (this.attributes[this.index] != null)
      {
        if ((this.attributes[this.index] instanceof BFILE)) {
          localBFILE = (BFILE)this.attributes[this.index];
        }
        else
        {
          SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4, null);
          localSQLException.fillInStackTrace();
          throw localSQLException;
        }
      }

    }
    finally
    {
      this.index += 1;
    }
    return localBFILE;
  }

  public BLOB readBLOB()
    throws SQLException
  {
    BLOB localBLOB = null;
    try
    {
      if (this.attributes[this.index] != null)
      {
        if ((this.attributes[this.index] instanceof BLOB)) {
          localBLOB = (BLOB)this.attributes[this.index];
        }
        else
        {
          SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4, null);
          localSQLException.fillInStackTrace();
          throw localSQLException;
        }
      }

    }
    finally
    {
      this.index += 1;
    }
    return localBLOB;
  }

  public CLOB readCLOB()
    throws SQLException
  {
    CLOB localCLOB = null;
    try
    {
      if (this.attributes[this.index] != null)
      {
        if ((this.attributes[this.index] instanceof CLOB)) {
          localCLOB = (CLOB)this.attributes[this.index];
        }
        else
        {
          SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4, null);
          localSQLException.fillInStackTrace();
          throw localSQLException;
        }
      }

    }
    finally
    {
      this.index += 1;
    }
    return localCLOB;
  }

  public RAW readRAW()
    throws SQLException
  {
    RAW localRAW = null;
    try
    {
      if (this.attributes[this.index] != null)
      {
        if ((this.attributes[this.index] instanceof RAW)) {
          localRAW = (RAW)this.attributes[this.index];
        }
        else
        {
          SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4, null);
          localSQLException.fillInStackTrace();
          throw localSQLException;
        }
      }

    }
    finally
    {
      this.index += 1;
    }
    return localRAW;
  }

  public REF readREF()
    throws SQLException
  {
    REF localREF = null;
    try
    {
      if (this.attributes[this.index] != null)
      {
        if ((this.attributes[this.index] instanceof REF)) {
          localREF = (REF)this.attributes[this.index];
        }
        else
        {
          SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4, null);
          localSQLException.fillInStackTrace();
          throw localSQLException;
        }
      }

    }
    finally
    {
      this.index += 1;
    }
    return localREF;
  }

  public ROWID readROWID()
    throws SQLException
  {
    ROWID localROWID = null;
    try
    {
      if (this.attributes[this.index] != null)
      {
        if ((this.attributes[this.index] instanceof ROWID)) {
          localROWID = (ROWID)this.attributes[this.index];
        }
        else
        {
          SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4, null);
          localSQLException.fillInStackTrace();
          throw localSQLException;
        }
      }

    }
    finally
    {
      this.index += 1;
    }
    return localROWID;
  }

  public ARRAY readARRAY()
    throws SQLException
  {
    ARRAY localARRAY = null;
    try
    {
      if (this.attributes[this.index] != null)
      {
        if ((this.attributes[this.index] instanceof ARRAY)) {
          localARRAY = (ARRAY)this.attributes[this.index];
        }
        else
        {
          SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4, null);
          localSQLException.fillInStackTrace();
          throw localSQLException;
        }
      }

    }
    finally
    {
      this.index += 1;
    }
    return localARRAY;
  }

  public STRUCT readSTRUCT()
    throws SQLException
  {
    STRUCT localSTRUCT = null;
    try
    {
      if (this.attributes[this.index] != null)
      {
        if ((this.attributes[this.index] instanceof STRUCT)) {
          localSTRUCT = (STRUCT)this.attributes[this.index];
        }
        else
        {
          SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4, null);
          localSQLException.fillInStackTrace();
          throw localSQLException;
        }
      }

    }
    finally
    {
      this.index += 1;
    }
    return localSTRUCT;
  }

  public URL readURL()
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createUnsupportedFeatureSqlException();
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public NClob readNClob()
    throws SQLException
  {
    NCLOB localNCLOB = null;
    try
    {
      if (this.attributes[this.index] != null)
      {
        if ((this.attributes[this.index] instanceof NCLOB)) {
          localNCLOB = (NCLOB)this.attributes[this.index];
        }
        else
        {
          SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4, null);
          localSQLException.fillInStackTrace();
          throw localSQLException;
        }
      }

    }
    finally
    {
      this.index += 1;
    }
    return localNCLOB;
  }

  public String readNString()
    throws SQLException
  {
    return readString();
  }

  public SQLXML readSQLXML()
    throws SQLException
  {
    SQLXML localSQLXML = null;
    try
    {
      if (this.attributes[this.index] != null)
      {
        if ((this.attributes[this.index] instanceof SQLXML)) {
          localSQLXML = (SQLXML)this.attributes[this.index];
        }
        else
        {
          SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4, null);
          localSQLException.fillInStackTrace();
          throw localSQLException;
        }
      }

    }
    finally
    {
      this.index += 1;
    }
    return localSQLXML;
  }

  public RowId readRowId()
    throws SQLException
  {
    return readROWID();
  }

  protected oracle.jdbc.internal.OracleConnection getConnectionDuringExceptionHandling()
  {
    return null;
  }
}