package oracle.jdbc.driver;

class ShortBinder extends VarnumBinder
{
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  void bind(OraclePreparedStatement paramOraclePreparedStatement, int paramInt1, int paramInt2, int paramInt3, byte[] paramArrayOfByte, char[] paramArrayOfChar, short[] paramArrayOfShort, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, boolean paramBoolean)
  {
    byte[] arrayOfByte = paramArrayOfByte;
    int i = paramInt6 + 1;
    int j = paramOraclePreparedStatement.parameterInt[paramInt3][paramInt1];
    int k = 0;

    if (j == 0)
    {
      arrayOfByte[i] = -128;
      k = 1;
    }
    else
    {
      int m;
      if (j < 0)
      {
        if (-j < 100)
        {
          arrayOfByte[i] = 62;
          arrayOfByte[(i + 1)] = ((byte)(101 + j));
          arrayOfByte[(i + 2)] = 102;
          k = 3;
        }
        else if (-j < 10000)
        {
          arrayOfByte[i] = 61;
          arrayOfByte[(i + 1)] = ((byte)(101 - -j / 100));
          m = -j % 100;

          if (m != 0)
          {
            arrayOfByte[(i + 2)] = ((byte)(101 - m));
            arrayOfByte[(i + 3)] = 102;
            k = 4;
          }
          else
          {
            arrayOfByte[(i + 2)] = 102;
            k = 3;
          }
        }
        else
        {
          arrayOfByte[i] = 60;
          arrayOfByte[(i + 1)] = ((byte)(101 - -j / 10000));
          m = -j % 100;

          if (m != 0)
          {
            arrayOfByte[(i + 2)] = ((byte)(101 - -j % 10000 / 100));
            arrayOfByte[(i + 3)] = ((byte)(101 - m));
            arrayOfByte[(i + 4)] = 102;
            k = 5;
          }
          else
          {
            m = -j % 10000 / 100;

            if (m != 0)
            {
              arrayOfByte[(i + 2)] = ((byte)(101 - m));
              arrayOfByte[(i + 3)] = 102;
              k = 4;
            }
            else
            {
              arrayOfByte[(i + 2)] = 102;
              k = 3;
            }
          }

        }

      }
      else if (j < 100)
      {
        arrayOfByte[i] = -63;
        arrayOfByte[(i + 1)] = ((byte)(j + 1));
        k = 2;
      }
      else if (j < 10000)
      {
        arrayOfByte[i] = -62;
        arrayOfByte[(i + 1)] = ((byte)(j / 100 + 1));
        m = j % 100;

        if (m != 0)
        {
          arrayOfByte[(i + 2)] = ((byte)(m + 1));
          k = 3;
        }
        else
        {
          k = 2;
        }
      }
      else
      {
        arrayOfByte[i] = -61;
        arrayOfByte[(i + 1)] = ((byte)(j / 10000 + 1));
        m = j % 100;

        if (m != 0)
        {
          arrayOfByte[(i + 2)] = ((byte)(j % 10000 / 100 + 1));
          arrayOfByte[(i + 3)] = ((byte)(m + 1));
          k = 4;
        }
        else
        {
          m = j % 10000 / 100;

          if (m != 0)
          {
            arrayOfByte[(i + 2)] = ((byte)(m + 1));
            k = 3;
          }
          else
          {
            k = 2;
          }
        }
      }
    }

    arrayOfByte[paramInt6] = ((byte)k);
    paramArrayOfShort[paramInt9] = 0;
    paramArrayOfShort[paramInt8] = ((short)(k + 1));
  }
}