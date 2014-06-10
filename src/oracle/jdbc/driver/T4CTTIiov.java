package oracle.jdbc.driver;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import oracle.jdbc.oracore.OracleTypeADT;

class T4CTTIiov extends T4CTTIMsg
{
  T4C8TTIrxh rxh;
  T4CTTIrxd rxd;
  byte bindtype = 0;
  byte[] iovector;
  int bindcnt = 0;
  int inbinds = 0;
  int outbinds = 0;
  static final byte BV_IN_V = 32;
  static final byte BV_OUT_V = 16;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  T4CTTIiov(T4CConnection paramT4CConnection, T4C8TTIrxh paramT4C8TTIrxh, T4CTTIrxd paramT4CTTIrxd)
    throws SQLException, IOException
  {
    super(paramT4CConnection, (byte)0);

    this.rxh = paramT4C8TTIrxh;
    this.rxd = paramT4CTTIrxd;
  }

  void init()
    throws SQLException, IOException
  {
  }

  Accessor[] processRXD(Accessor[] paramArrayOfAccessor, int paramInt1, byte[] paramArrayOfByte1, char[] paramArrayOfChar1, short[] paramArrayOfShort1, int paramInt2, DBConversion paramDBConversion, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3, InputStream[][] paramArrayOfInputStream, byte[][][] paramArrayOfByte, OracleTypeADT[][] paramArrayOfOracleTypeADT, OracleStatement paramOracleStatement, byte[] paramArrayOfByte4, char[] paramArrayOfChar2, short[] paramArrayOfShort2)
    throws SQLException, IOException
  {
    if (paramArrayOfByte3 != null)
    {
      for (int i = 0; i < paramArrayOfByte3.length; i++)
      {
        if (((paramArrayOfByte3[i] & 0x10) != 0) && ((paramArrayOfAccessor == null) || (paramArrayOfAccessor.length <= i) || (paramArrayOfAccessor[i] == null)))
        {
          int j = paramInt2 + 5 + 10 * i;

          int k = paramArrayOfShort1[(j + 0)] & 0xFFFF;

          int m = k;

          if (k == 9) {
            k = 1;
          }
          Accessor localAccessor = paramOracleStatement.allocateAccessor(k, k, i, 0, (short)0, null, false);

          localAccessor.rowSpaceIndicator = null;

          if ((localAccessor.defineType == 109) || (localAccessor.defineType == 111))
          {
            localAccessor.setOffsets(1);
          }
          if (paramArrayOfAccessor == null)
          {
            paramArrayOfAccessor = new Accessor[i + 1];
            paramArrayOfAccessor[i] = localAccessor;
          }
          else if (paramArrayOfAccessor.length <= i)
          {
            Accessor[] arrayOfAccessor = new Accessor[i + 1];

            arrayOfAccessor[i] = localAccessor;

            for (int n = 0; n < paramArrayOfAccessor.length; n++)
            {
              if (paramArrayOfAccessor[n] != null) {
                arrayOfAccessor[n] = paramArrayOfAccessor[n];
              }
            }
            paramArrayOfAccessor = arrayOfAccessor;
          }
          else
          {
            paramArrayOfAccessor[i] = localAccessor;
          }
        }
        else if (((paramArrayOfByte3[i] & 0x10) == 0) && (paramArrayOfAccessor != null) && (i < paramArrayOfAccessor.length) && (paramArrayOfAccessor[i] != null))
        {
          paramArrayOfAccessor[i].isUseLess = true;
        }

      }

    }

    return paramArrayOfAccessor;
  }

  void unmarshalV10()
    throws IOException, SQLException
  {
    this.rxh.unmarshalV10(this.rxd);

    this.bindcnt = this.rxh.numRqsts;

    this.iovector = new byte[this.connection.all8.numberOfBindPositions];

    for (int i = 0; i < this.iovector.length; i++)
    {
      if ((this.bindtype = this.meg.unmarshalSB1()) == 0)
      {
        SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 401);
        localSQLException.fillInStackTrace();
        throw localSQLException;
      }

      if ((this.bindtype & 0x20) > 0)
      {
        int tmp97_96 = i;
        byte[] tmp97_93 = this.iovector; tmp97_93[tmp97_96] = ((byte)(tmp97_93[tmp97_96] | 0x20));
        this.inbinds += 1;
      }

      if ((this.bindtype & 0x10) > 0)
      {
        int tmp129_128 = i;
        byte[] tmp129_125 = this.iovector; tmp129_125[tmp129_128] = ((byte)(tmp129_125[tmp129_128] | 0x10));
        this.outbinds += 1;
      }
    }
  }

  byte[] getIOVector()
  {
    return this.iovector;
  }

  boolean isIOVectorEmpty()
  {
    return this.iovector.length == 0;
  }
}