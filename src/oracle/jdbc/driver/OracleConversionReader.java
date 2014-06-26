package oracle.jdbc.driver;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.sql.SQLException;
import oracle.jdbc.internal.OracleConnection;

class OracleConversionReader extends Reader
{
  static final int CHUNK_SIZE = 4096;
  DBConversion dbConversion;
  int conversion;
  InputStream istream;
  char[] buf;
  byte[] byteBuf;
  int pos;
  int count;
  int numUnconvertedBytes;
  boolean isClosed;
  boolean endOfStream;
  private short csform;
  int[] nbytes;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  public OracleConversionReader(DBConversion paramDBConversion, InputStream paramInputStream, int paramInt)
    throws SQLException
  {
    if ((paramDBConversion == null) || (paramInputStream == null) || ((paramInt != 8) && (paramInt != 9)))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 68);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.dbConversion = paramDBConversion;
    this.conversion = paramInt;
    this.istream = paramInputStream;
    this.pos = (this.count = 0);
    this.numUnconvertedBytes = 0;

    this.isClosed = false;
    this.nbytes = new int[1];

    if (paramInt == 8)
    {
      this.byteBuf = new byte[2048];
      this.buf = new char[4096];
    }
    else if (paramInt == 9)
    {
      this.byteBuf = new byte[4096];
      this.buf = new char[4096];
    }
  }

  public void setFormOfUse(short paramShort)
  {
    this.csform = paramShort;
  }

  public int read(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    throws IOException
  {
    ensureOpen();

    if (!needChars()) {
      return -1;
    }
    int i = paramInt1;
    int j = i + Math.min(paramInt2, paramArrayOfChar.length - paramInt1);

    i += writeChars(paramArrayOfChar, i, j - i);

    while ((i < j) && (needChars()))
    {
      i += writeChars(paramArrayOfChar, i, j - i);
    }

    return i - paramInt1;
  }

  protected boolean needChars()
    throws IOException
  {
    ensureOpen();

    if (this.pos >= this.count)
    {
      if (!this.endOfStream)
      {
        try
        {
          int i = this.istream.read(this.byteBuf, this.numUnconvertedBytes, this.byteBuf.length - this.numUnconvertedBytes);

          if (i == -1)
          {
            this.endOfStream = true;

            this.istream.close();

            if (this.numUnconvertedBytes != 0)
            {
              SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 55);
              localSQLException2.fillInStackTrace();
              throw localSQLException2;
            }
          }

          i += this.numUnconvertedBytes;

          if (i > 0)
          {
            switch (this.conversion)
            {
            case 8:
              this.count = DBConversion.RAWBytesToHexChars(this.byteBuf, i, this.buf);

              break;
            case 9:
              this.nbytes[0] = i;

              if (this.csform == 2) {
                this.count = this.dbConversion.NCHARBytesToJavaChars(this.byteBuf, 0, this.buf, 0, this.nbytes, this.buf.length);
              }
              else
              {
                this.count = this.dbConversion.CHARBytesToJavaChars(this.byteBuf, 0, this.buf, 0, this.nbytes, this.buf.length);
              }

              this.numUnconvertedBytes = this.nbytes[0];

              for (int j = 0; j < this.numUnconvertedBytes; j++) {
                this.byteBuf[j] = this.byteBuf[(i - this.numUnconvertedBytes + j)];
              }
              break;
            default:
              SQLException localObject = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 23);
              localObject.fillInStackTrace();
              throw localObject;
            }

            if (this.count > 0)
            {
              this.pos = 0;

              return true;
            }

          }

        }
        catch (SQLException localSQLException1)
        {
          IOException localObject = DatabaseError.createIOException(localSQLException1);
          localObject.fillInStackTrace();
          throw localObject;
        }

      }

      return false;
    }

    return true;
  }

  protected int writeChars(char[] paramArrayOfChar, int paramInt1, int paramInt2)
  {
    int i = Math.min(paramInt2, this.count - this.pos);

    System.arraycopy(this.buf, this.pos, paramArrayOfChar, paramInt1, i);

    this.pos += i;

    return i;
  }

  public boolean ready()
    throws IOException
  {
    ensureOpen();

    return this.pos < this.count;
  }

  public void close()
    throws IOException
  {
    if (!this.isClosed)
    {
      this.isClosed = true;

      this.istream.close();
    }
  }

  void ensureOpen()
    throws IOException
  {
    try
    {
      if (this.isClosed)
      {
        SQLException localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 57, null);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

    }
    catch (SQLException localSQLException2)
    {
      IOException localIOException = DatabaseError.createIOException(localSQLException2);
      localIOException.fillInStackTrace();
      throw localIOException;
    }
  }

  protected OracleConnection getConnectionDuringExceptionHandling()
  {
    return null;
  }
}