package oracle.jdbc.driver;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

class OracleConversionInputStreamInternal extends OracleConversionInputStream
{
  boolean needReset = false;

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  public OracleConversionInputStreamInternal(DBConversion paramDBConversion, InputStream paramInputStream, int paramInt1, int paramInt2)
  {
    super(paramDBConversion, paramInputStream, paramInt1, paramInt2);
  }

  public OracleConversionInputStreamInternal(DBConversion paramDBConversion, Reader paramReader, int paramInt1, int paramInt2, short paramShort)
  {
    super(paramDBConversion, paramReader, paramInt1, paramInt2, paramShort);
  }

  public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException
  {
    if (this.needReset)
    {
      if ((this.istream != null) && (this.istream.markSupported()))
      {
        this.istream.reset();
        this.endOfStream = false;
        this.totalSize = 0;
        this.needReset = false;
      }
      else if ((this.reader != null) && (this.reader.markSupported()))
      {
        this.reader.reset();
        this.endOfStream = false;
        this.totalSize = 0;
        this.needReset = false;
      }
    }

    int i = super.read(paramArrayOfByte, paramInt1, paramInt2);

    if (i == -1) {
      this.needReset = true;
    }
    return i;
  }
}