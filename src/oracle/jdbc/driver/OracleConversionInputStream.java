package oracle.jdbc.driver;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.sql.SQLException;
import oracle.jdbc.internal.OracleConnection;

class OracleConversionInputStream extends OracleBufferedStream
{
  static final int CHUNK_SIZE = 4096;
  DBConversion converter;
  int conversion;
  InputStream istream;
  Reader reader;
  byte[] convbuf;
  char[] javaChars;
  int maxSize;
  int totalSize;
  int numUnconvertedBytes;
  boolean endOfStream;
  private short csform;
  int[] nbytes;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  public OracleConversionInputStream(DBConversion paramDBConversion, InputStream paramInputStream, int paramInt)
  {
    this(paramDBConversion, paramInputStream, paramInt, (short)1);
  }

  public OracleConversionInputStream(DBConversion paramDBConversion, InputStream paramInputStream, int paramInt, short paramShort)
  {
    super(4096);

    this.istream = paramInputStream;
    this.conversion = paramInt;
    this.converter = paramDBConversion;
    this.maxSize = 0;
    this.totalSize = 0;
    this.numUnconvertedBytes = 0;
    this.endOfStream = false;
    this.nbytes = new int[1];
    this.csform = paramShort;
    this.currentBufferSize = this.initialBufferSize;
    this.resizableBuffer = new byte[this.currentBufferSize];
    int i;
    switch (paramInt)
    {
    case 0:
      this.javaChars = new char[4096];
      this.convbuf = new byte[4096];

      break;
    case 1:
      this.convbuf = new byte[2048];
      this.javaChars = new char[2048];

      break;
    case 2:
      this.convbuf = new byte[2048];
      this.javaChars = new char[4096];

      break;
    case 3:
      this.convbuf = new byte[1024];
      this.javaChars = new char[2048];

      break;
    case 4:
      i = 4096 / this.converter.getMaxCharbyteSize();

      this.convbuf = new byte[i * 2];
      this.javaChars = new char[i];

      break;
    case 5:
      if (this.converter.isUcs2CharSet())
      {
        this.convbuf = new byte[2048];
        this.javaChars = new char[2048];
      }
      else
      {
        this.convbuf = new byte[4096];
        this.javaChars = new char[4096];
      }

      break;
    case 7:
      i = 4096 / (paramShort == 2 ? this.converter.getMaxNCharbyteSize() : this.converter.getMaxCharbyteSize());

      this.javaChars = new char[i];

      break;
    case 6:
    default:
      this.convbuf = new byte[4096];
      this.javaChars = new char[4096];
    }
  }

  public OracleConversionInputStream(DBConversion paramDBConversion, InputStream paramInputStream, int paramInt1, int paramInt2)
  {
    this(paramDBConversion, paramInputStream, paramInt1, (short)1);

    this.maxSize = paramInt2;
    this.totalSize = 0;
  }

  public OracleConversionInputStream(DBConversion paramDBConversion, Reader paramReader, int paramInt1, int paramInt2, short paramShort)
  {
    this(paramDBConversion, (InputStream)null, paramInt1, paramShort);

    this.reader = paramReader;
    this.maxSize = paramInt2;
    this.totalSize = 0;
  }

  public void setFormOfUse(short paramShort)
  {
    this.csform = paramShort;
  }

  public boolean needBytes(int paramInt)
    throws IOException
  {
    return needBytes();
  }

  public boolean needBytes()
    throws IOException
  {
    if (this.closed) {
      return false;
    }

    if (this.pos < this.count) {
      return true;
    }
    if (this.istream != null)
    {
      return needBytesFromStream();
    }
    if (this.reader != null)
    {
      return needBytesFromReader();
    }

    return false;
  }

  public boolean needBytesFromReader()
    throws IOException
  {
    try
    {
      int i = 0;

      if (this.maxSize == 0)
      {
        i = this.javaChars.length;
      }
      else
      {
        i = Math.min(this.maxSize - this.totalSize, this.javaChars.length);
      }

      if (i <= 0)
      {
        return false;
      }

      int j = this.reader.read(this.javaChars, 0, i);

      if (j == -1)
      {
        return false;
      }

      this.totalSize += j;

      switch (this.conversion)
      {
      case 7:
        if (this.csform == 2)
          this.count = this.converter.javaCharsToNCHARBytes(this.javaChars, j, this.resizableBuffer);
        else {
          this.count = this.converter.javaCharsToCHARBytes(this.javaChars, j, this.resizableBuffer);
        }

        break;
      default:
        System.arraycopy(this.convbuf, 0, this.resizableBuffer, 0, j);

        this.count = j;
      }

    }
    catch (SQLException localSQLException)
    {
      IOException localIOException = DatabaseError.createIOException(localSQLException);
      localIOException.fillInStackTrace();
      throw localIOException;
    }

    this.pos = 0;

    return true;
  }

  public boolean needBytesFromStream()
    throws IOException
  {
    if (!this.endOfStream)
    {
      try
      {
        int i = 0;

        if (this.maxSize == 0)
        {
          i = this.convbuf.length;
        }
        else
        {
          i = Math.min(this.maxSize - this.totalSize, this.convbuf.length);
        }

        int j = 0;
        SQLException localSQLException2;
        if (i <= 0)
        {
          this.endOfStream = true;

          this.istream.close();

          if (this.numUnconvertedBytes != 0)
          {
            localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 55);
            localSQLException2.fillInStackTrace();
            throw localSQLException2;
          }

        }
        else
        {
          j = this.istream.read(this.convbuf, this.numUnconvertedBytes, i - this.numUnconvertedBytes);
        }

        if (j == -1)
        {
          this.endOfStream = true;

          this.istream.close();

          if (this.numUnconvertedBytes != 0)
          {
            localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 55);
            localSQLException2.fillInStackTrace();
            throw localSQLException2;
          }
        }
        else
        {
          j += this.numUnconvertedBytes;
          this.totalSize += j;
        }

        if (j <= 0)
        {
          return false;
        }
        int k;
        int m;
        switch (this.conversion)
        {
        case 0:
          this.nbytes[0] = j;

          k = this.converter.CHARBytesToJavaChars(this.convbuf, 0, this.javaChars, 0, this.nbytes, this.javaChars.length);

          this.numUnconvertedBytes = this.nbytes[0];

          for (m = 0; m < this.numUnconvertedBytes; m++) {
            this.convbuf[m] = this.convbuf[(j - this.numUnconvertedBytes)];
          }

          this.count = DBConversion.javaCharsToAsciiBytes(this.javaChars, k, this.resizableBuffer);

          break;
        case 1:
          this.nbytes[0] = j;

          k = this.converter.CHARBytesToJavaChars(this.convbuf, 0, this.javaChars, 0, this.nbytes, this.javaChars.length);

          this.numUnconvertedBytes = this.nbytes[0];

          for (m = 0; m < this.numUnconvertedBytes; m++) {
            this.convbuf[m] = this.convbuf[(j - this.numUnconvertedBytes)];
          }

          this.count = DBConversion.javaCharsToUcs2Bytes(this.javaChars, k, this.resizableBuffer);

          break;
        case 2:
          k = DBConversion.RAWBytesToHexChars(this.convbuf, j, this.javaChars);

          this.count = DBConversion.javaCharsToAsciiBytes(this.javaChars, k, this.resizableBuffer);

          break;
        case 3:
          k = DBConversion.RAWBytesToHexChars(this.convbuf, j, this.javaChars);

          this.count = DBConversion.javaCharsToUcs2Bytes(this.javaChars, k, this.resizableBuffer);

          break;
        case 4:
          k = DBConversion.ucs2BytesToJavaChars(this.convbuf, j, this.javaChars);

          this.count = this.converter.javaCharsToCHARBytes(this.javaChars, k, this.resizableBuffer);

          break;
        case 12:
          k = DBConversion.ucs2BytesToJavaChars(this.convbuf, j, this.javaChars);

          this.count = DBConversion.javaCharsToAsciiBytes(this.javaChars, k, this.resizableBuffer);

          break;
        case 5:
          DBConversion.asciiBytesToJavaChars(this.convbuf, j, this.javaChars);

          this.count = this.converter.javaCharsToCHARBytes(this.javaChars, j, this.resizableBuffer);

          break;
        case 6:
        case 7:
        case 8:
        case 9:
        case 10:
        case 11:
        default:
          System.arraycopy(this.convbuf, 0, this.resizableBuffer, 0, j);

          this.count = j;
        }

      }
      catch (SQLException localSQLException1)
      {
        IOException localIOException = DatabaseError.createIOException(localSQLException1);
        localIOException.fillInStackTrace();
        throw localIOException;
      }

      this.pos = 0;

      return true;
    }

    return false;
  }

  protected OracleConnection getConnectionDuringExceptionHandling()
  {
    return null;
  }
}