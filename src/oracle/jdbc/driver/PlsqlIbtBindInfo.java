package oracle.jdbc.driver;

import java.sql.SQLException;

class PlsqlIbtBindInfo
{
  Object[] arrayData;
  int maxLen;
  int curLen;
  int element_internal_type;
  int elemMaxLen;
  int ibtByteLength;
  int ibtCharLength;
  int ibtValueIndex;
  int ibtIndicatorIndex;
  int ibtLengthIndex;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  PlsqlIbtBindInfo(Object[] paramArrayOfObject, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    throws SQLException
  {
    this.arrayData = paramArrayOfObject;
    this.maxLen = paramInt1;
    this.curLen = paramInt2;
    this.element_internal_type = paramInt3;

    switch (paramInt3)
    {
    case 1:
    case 96:
      this.elemMaxLen = (paramInt4 == 0 ? 2 : paramInt4 + 1);

      this.ibtCharLength = (this.elemMaxLen * paramInt1);
      this.element_internal_type = 9;

      break;
    case 6:
      this.elemMaxLen = 22;
      this.ibtByteLength = (this.elemMaxLen * paramInt1);

      break;
    default:
      SQLException localSQLException = DatabaseError.createSqlException(null, 97);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }
}