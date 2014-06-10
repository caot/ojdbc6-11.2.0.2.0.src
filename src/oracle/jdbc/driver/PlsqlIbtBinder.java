package oracle.jdbc.driver;

import java.sql.SQLException;
import oracle.sql.Datum;

class PlsqlIbtBinder extends Binder
{
  Binder thePlsqlIbtCopyingBinder = OraclePreparedStatementReadOnly.theStaticPlsqlIbtCopyingBinder;

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  PlsqlIbtBinder()
  {
    init(this);
  }

  static void init(Binder paramBinder)
  {
    paramBinder.type = 998;
  }

  void bind(OraclePreparedStatement paramOraclePreparedStatement, int paramInt1, int paramInt2, int paramInt3, byte[] paramArrayOfByte, char[] paramArrayOfChar, short[] paramArrayOfShort, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, boolean paramBoolean)
    throws SQLException
  {
    PlsqlIbtBindInfo localPlsqlIbtBindInfo = paramOraclePreparedStatement.parameterPlsqlIbt[paramInt3][paramInt1];

    if (paramBoolean) {
      paramOraclePreparedStatement.parameterPlsqlIbt[paramInt3][paramInt1] = null;
    }
    int i = localPlsqlIbtBindInfo.ibtValueIndex;
    int j;
    switch (localPlsqlIbtBindInfo.element_internal_type)
    {
    case 9:
      for (j = 0; j < localPlsqlIbtBindInfo.curLen; j++)
      {
        int k = 0;
        String str = (String)localPlsqlIbtBindInfo.arrayData[j];

        if (str != null)
        {
          k = str.length();

          if (k > localPlsqlIbtBindInfo.elemMaxLen - 1) {
            k = localPlsqlIbtBindInfo.elemMaxLen - 1;
          }
          str.getChars(0, k, paramOraclePreparedStatement.ibtBindChars, i + 1);

          paramOraclePreparedStatement.ibtBindIndicators[(localPlsqlIbtBindInfo.ibtIndicatorIndex + j)] = 0;
          k <<= 1;
          paramOraclePreparedStatement.ibtBindChars[i] = ((char)k);

          paramOraclePreparedStatement.ibtBindIndicators[(localPlsqlIbtBindInfo.ibtLengthIndex + j)] = (k == 0 ? 3 : (short)(k + 2));
        }
        else
        {
          paramOraclePreparedStatement.ibtBindIndicators[(localPlsqlIbtBindInfo.ibtIndicatorIndex + j)] = -1;
        }
        i += localPlsqlIbtBindInfo.elemMaxLen;
      }

      break;
    case 6:
      for (j = 0; j < localPlsqlIbtBindInfo.curLen; j++)
      {
        byte[] arrayOfByte = null;

        if (localPlsqlIbtBindInfo.arrayData[j] != null) {
          arrayOfByte = ((Datum)localPlsqlIbtBindInfo.arrayData[j]).getBytes();
        }
        if (arrayOfByte == null)
        {
          paramOraclePreparedStatement.ibtBindIndicators[(localPlsqlIbtBindInfo.ibtIndicatorIndex + j)] = -1;
        }
        else
        {
          paramOraclePreparedStatement.ibtBindIndicators[(localPlsqlIbtBindInfo.ibtIndicatorIndex + j)] = 0;
          paramOraclePreparedStatement.ibtBindIndicators[(localPlsqlIbtBindInfo.ibtLengthIndex + j)] = ((short)(arrayOfByte.length + 1));

          paramOraclePreparedStatement.ibtBindBytes[i] = ((byte)arrayOfByte.length);

          System.arraycopy(arrayOfByte, 0, paramOraclePreparedStatement.ibtBindBytes, i + 1, arrayOfByte.length);
        }

        i += localPlsqlIbtBindInfo.elemMaxLen;
      }

      break;
    default:
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 97);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  Binder copyingBinder()
  {
    return this.thePlsqlIbtCopyingBinder;
  }
}