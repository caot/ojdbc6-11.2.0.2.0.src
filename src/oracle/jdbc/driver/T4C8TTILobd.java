package oracle.jdbc.driver;

import java.io.IOException;
import java.sql.SQLException;

class T4C8TTILobd extends T4CTTIMsg
{
  static final int LOBD_STATE0 = 0;
  static final int LOBD_STATE1 = 1;
  static final int LOBD_STATE2 = 2;
  static final int LOBD_STATE3 = 3;
  static final int LOBD_STATE_EXIT = 4;
  static final short TTCG_LNG = 254;
  static final short LOBDATALENGTH = 252;
  static byte[] ucs2Char = new byte[2];

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  T4C8TTILobd(T4CConnection paramT4CConnection)
  {
    super(paramT4CConnection, (byte)14);
  }

  void marshalLobData(byte[] paramArrayOfByte, long paramLong1, long paramLong2, boolean paramBoolean)
    throws IOException
  {
    long l1 = paramLong2;

    marshalTTCcode();
    if (paramBoolean)
    {
      this.meg.outStream.flush();
      this.meg.outStream.writeZeroCopyIO(paramArrayOfByte, (int)paramLong1, (int)paramLong2);
    }
    else
    {
      int i = 0;

      if (l1 > 252L)
      {
        i = 1;

        this.meg.marshalUB1((short)254);
      }

      long l2 = 0L;

      for (; l1 > 252L; l1 -= 252L)
      {
        this.meg.marshalUB1((short)252);
        this.meg.marshalB1Array(paramArrayOfByte, (int)(paramLong1 + l2 * 252L), 252);

        l2 += 1L;
      }

      if (l1 > 0L)
      {
        this.meg.marshalUB1((short)(int)l1);
        this.meg.marshalB1Array(paramArrayOfByte, (int)(paramLong1 + l2 * 252L), (int)l1);
      }

      if (i == 1)
        this.meg.marshalUB1((short)0);
    }
  }

  void marshalClobUB2_For9iDB(byte[] paramArrayOfByte, long paramLong1, long paramLong2)
    throws IOException
  {
    long l1 = paramLong2;
    int i = 0;

    marshalTTCcode();

    if (l1 > 84L)
    {
      i = 1;

      this.meg.marshalUB1((short)254);
    }

    long l2 = 0L;

    for (; l1 > 84L; l1 -= 84L)
    {
      this.meg.marshalUB1((short)252);

      for (int j = 0; j < 84; j++)
      {
        this.meg.marshalUB1((short)2);

        this.meg.marshalB1Array(paramArrayOfByte, (int)(paramLong1 + l2 * 168L + j * 2), 2);
      }
      l2 += 1L;
    }

    if (l1 > 0L)
    {
      long l3 = l1 * 3L;

      this.meg.marshalUB1((short)(int)l3);

      for (int k = 0; k < l1; k++)
      {
        this.meg.marshalUB1((short)2);

        this.meg.marshalB1Array(paramArrayOfByte, (int)(paramLong1 + l2 * 168L + k * 2), 2);
      }

    }

    if (i == 1)
      this.meg.marshalUB1((short)0);
  }

  long unmarshalLobData(byte[] paramArrayOfByte, int paramInt, boolean paramBoolean)
    throws SQLException, IOException
  {
    long l1 = 0L;
    boolean bool;
    if (paramBoolean)
    {
      int i = 0;

      int[] arrayOfInt = new int[1];

      bool = false;
      while (!bool)
      {
        bool = this.meg.inStream.readZeroCopyIO(paramArrayOfByte, paramInt + i, arrayOfInt);

        i += arrayOfInt[0];
      }
      l1 = i;
    }
    else
    {
      long l2 = paramInt;
      bool = false;

      int k = 0;

      while (k != 4)
      {
        int j;
        switch (k)
        {
        case 0:
          j = this.meg.unmarshalUB1();

          if (j == 254) {
            k = 2;
          }
          else
          {
            k = 1;
          }
          break;
        case 1:
          this.meg.getNBytes(paramArrayOfByte, (int)l2, j);

          l1 += j;
          k = 4;

          break;
        case 2:
          j = this.meg.unmarshalUB1();

          if (j > 0) {
            k = 3;
          }
          else
          {
            k = 4;
          }
          break;
        case 3:
          this.meg.getNBytes(paramArrayOfByte, (int)l2, j);

          l1 += j;

          l2 += j;

          k = 2;
        }

      }

    }

    return l1;
  }

  long unmarshalClobUB2_For9iDB(byte[] paramArrayOfByte, int paramInt)
    throws SQLException, IOException
  {
    long l1 = 0L;
    long l2 = paramInt;
    int i = 0;
    int j = 0;
    int k = 0;

    int m = 0;

    while (m != 4)
    {
      switch (m)
      {
      case 0:
        i = this.meg.unmarshalUB1();

        if (i == 254) {
          m = 2;
        }
        else
        {
          m = 1;
        }
        break;
      case 1:
        for (j = 0; j < i; l2 += 2L)
        {
          k = this.meg.unmarshalUCS2(paramArrayOfByte, l2);

          j += k;
        }

        l1 += i;
        m = 4;

        break;
      case 2:
        i = this.meg.unmarshalUB1();

        if (i > 0) {
          m = 3;
        }
        else
        {
          m = 4;
        }
        break;
      case 3:
        for (j = 0; j < i; l2 += 2L)
        {
          k = this.meg.unmarshalUCS2(paramArrayOfByte, l2);

          j += k;
        }

        l1 += i;

        m = 2;
      }

    }

    return l1;
  }
}