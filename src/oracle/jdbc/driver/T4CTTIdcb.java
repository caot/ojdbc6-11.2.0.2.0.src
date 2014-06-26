package oracle.jdbc.driver;

import java.io.IOException;
import java.sql.SQLException;
import oracle.jdbc.OracleResultSetMetaData.SecurityAttribute;
import oracle.jdbc.oracore.OracleTypeADT;

class T4CTTIdcb extends T4CTTIMsg
{
  static final int DCBRXFR = 1;
  static final int DCBFIOT = 2;
  static final int DCBFHAVECOOKIE = 4;
  static final int DCBFNEWCOOKIE = 8;
  static final int DCBFREM = 16;
  int numuds;
  int colOffset;
  byte[] ignoreBuff;
  StringBuffer colNameSB = null;

  OracleStatement statement = null;

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  T4CTTIdcb(T4CConnection paramT4CConnection)
  {
    super(paramT4CConnection, (byte)16);

    this.ignoreBuff = new byte[40];
  }

  void init(OracleStatement paramOracleStatement, int paramInt)
  {
    this.statement = paramOracleStatement;
    this.colOffset = paramInt;
  }

  Accessor[] receive(Accessor[] paramArrayOfAccessor)
    throws SQLException, IOException
  {
    int i = this.meg.unmarshalUB1();

    if (this.ignoreBuff.length < i) {
      this.ignoreBuff = new byte[i];
    }
    this.meg.unmarshalNBytes(this.ignoreBuff, 0, i);

    int j = (int)this.meg.unmarshalUB4();

    paramArrayOfAccessor = receiveCommon(paramArrayOfAccessor, false);

    return paramArrayOfAccessor;
  }

  Accessor[] receiveFromRefCursor(Accessor[] paramArrayOfAccessor)
    throws SQLException, IOException
  {
    int i = this.meg.unmarshalUB1();
    int j = (int)this.meg.unmarshalUB4();

    paramArrayOfAccessor = receiveCommon(paramArrayOfAccessor, false);

    return paramArrayOfAccessor;
  }

  Accessor[] receiveCommon(Accessor[] paramArrayOfAccessor, boolean paramBoolean)
    throws SQLException, IOException
  {
    if (paramBoolean) {
      this.numuds = this.meg.unmarshalUB2();
    }
    else {
      this.numuds = ((int)this.meg.unmarshalUB4());
      if (this.numuds > 0)
      {
        int i = this.meg.unmarshalUB1();
      }
    }

    if (this.statement.needToPrepareDefineBuffer)
    {
      if ((paramArrayOfAccessor == null) || (paramArrayOfAccessor.length != this.numuds + this.colOffset))
      {
        Accessor[] localObject = new Accessor[this.numuds + this.colOffset];
        if ((paramArrayOfAccessor != null) && (paramArrayOfAccessor.length == this.colOffset))
        {
          System.arraycopy(paramArrayOfAccessor, 0, localObject, 0, this.colOffset);
        }
        paramArrayOfAccessor = (Accessor[])localObject;
      }
    }

    Object localObject = new T4C8TTIuds((T4CConnection)this.statement.connection);

    for (int j = 0; j < this.numuds; j++)
    {
      ((T4C8TTIuds)localObject).unmarshal();
      String str = this.meg.conv.CharBytesToString(((T4C8TTIuds)localObject).getColumName(), ((T4C8TTIuds)localObject).getColumNameByteLength());

      if (this.statement.needToPrepareDefineBuffer) {
        fillupAccessors(paramArrayOfAccessor, this.colOffset + j, (T4C8TTIuds)localObject, str);
      }
    }
    if (!paramBoolean)
    {
      byte[] arrayOfByte1 = this.meg.unmarshalDALC();

      if (this.connection.getTTCVersion() >= 3)
      {
        int k = (int)this.meg.unmarshalUB4();
        int m = (int)this.meg.unmarshalUB4();

        if (this.connection.getTTCVersion() >= 4)
        {
          int n = (int)this.meg.unmarshalUB4();
          int i1 = (int)this.meg.unmarshalUB4();

          if (this.connection.getTTCVersion() >= 5)
          {
            byte[] arrayOfByte2 = this.meg.unmarshalDALC();
          }

        }

      }

    }

    if (this.statement.needToPrepareDefineBuffer)
    {
      if (!paramBoolean)
      {
        this.statement.rowPrefetchInLastFetch = -1;
        this.statement.describedWithNames = true;
        this.statement.described = true;
        this.statement.numberOfDefinePositions = this.numuds;
        this.statement.accessors = paramArrayOfAccessor;

        this.statement.prepareAccessors();

        this.statement.allocateTmpByteArray();
      }
    }

    return paramArrayOfAccessor;
  }

  void fillupAccessors(Accessor[] paramArrayOfAccessor, int paramInt, T4C8TTIuds paramT4C8TTIuds, String paramString)
    throws SQLException
  {
    int[] arrayOfInt1 = this.statement.definedColumnType;
    int[] arrayOfInt2 = this.statement.definedColumnSize;
    int[] arrayOfInt3 = this.statement.definedColumnFormOfUse;
    int i = this.statement.sqlObject.includeRowid ? 1 : 0;

    String str1 = null;
    String str2 = null;
    String str3 = null;

    int m = 0;
    int n = 0;
    int i1 = 0;
    int i2;
    if (paramInt >= i)
    {
      i2 = paramInt - i;
      if ((arrayOfInt1 != null) && (arrayOfInt1.length > i2) && (arrayOfInt1[i2] != 0))
      {
        m = arrayOfInt1[i2];
      }
      if ((arrayOfInt2 != null) && (arrayOfInt2.length > i2) && (arrayOfInt2[i2] > 0))
      {
        n = arrayOfInt2[i2];
      }
      if ((arrayOfInt3 != null) && (arrayOfInt3.length > i2) && (arrayOfInt3[i2] > 0))
      {
        i1 = arrayOfInt3[i2];
      }
    }
    int j = paramT4C8TTIuds.udsoac.oacmxl;
    int k;
    switch (paramT4C8TTIuds.udsoac.oacdty)
    {
    case 96:
      if ((paramT4C8TTIuds.udsoac.oacmxlc != 0) && (paramT4C8TTIuds.udsoac.oacmxlc < j))
      {
        j = 2 * paramT4C8TTIuds.udsoac.oacmxlc;
      }

      k = j;

      if (((m == 1) || (m == 12)) && (n > 0) && (n < j))
      {
        k = n;
      }
      paramArrayOfAccessor[paramInt] = new T4CCharAccessor(this.statement, k, paramT4C8TTIuds.udsnull, paramT4C8TTIuds.udsoac.oacflg, paramT4C8TTIuds.udsoac.oacpre, paramT4C8TTIuds.udsoac.oacscl, paramT4C8TTIuds.udsoac.oacfl2, paramT4C8TTIuds.udsoac.oacmal, paramT4C8TTIuds.udsoac.oaccsfrm, j, m, n, this.meg);

      if (((paramT4C8TTIuds.udsoac.oacfl2 & 0x1000) == 4096) || (paramT4C8TTIuds.udsoac.oacmxlc != 0))
      {
        paramArrayOfAccessor[paramInt].setDisplaySize(paramT4C8TTIuds.udsoac.oacmxlc); } break;
    case 2:
      paramArrayOfAccessor[paramInt] = new T4CNumberAccessor(this.statement, j, paramT4C8TTIuds.udsnull, paramT4C8TTIuds.udsoac.oacflg, paramT4C8TTIuds.udsoac.oacpre, paramT4C8TTIuds.udsoac.oacscl, paramT4C8TTIuds.udsoac.oacfl2, paramT4C8TTIuds.udsoac.oacmal, paramT4C8TTIuds.udsoac.oaccsfrm, m, n, this.meg);

      break;
    case 1:
      if ((paramT4C8TTIuds.udsoac.oacmxlc != 0) && (paramT4C8TTIuds.udsoac.oacmxlc < j)) {
        j = 2 * paramT4C8TTIuds.udsoac.oacmxlc;
      }
      k = j;

      if (((m == 1) || (m == 12)) && (n > 0) && (n < j))
      {
        k = n;
      }
      paramArrayOfAccessor[paramInt] = new T4CVarcharAccessor(this.statement, k, paramT4C8TTIuds.udsnull, paramT4C8TTIuds.udsoac.oacflg, paramT4C8TTIuds.udsoac.oacpre, paramT4C8TTIuds.udsoac.oacscl, paramT4C8TTIuds.udsoac.oacfl2, paramT4C8TTIuds.udsoac.oacmal, paramT4C8TTIuds.udsoac.oaccsfrm, j, m, n, this.meg);

      if (((paramT4C8TTIuds.udsoac.oacfl2 & 0x1000) == 4096) || (paramT4C8TTIuds.udsoac.oacmxlc != 0))
      {
        paramArrayOfAccessor[paramInt].setDisplaySize(paramT4C8TTIuds.udsoac.oacmxlc); } break;
    case 8:
      if (((m == 1) || (m == 12)) && (this.connection.versionNumber >= 9000) && (n < 4001))
      {
        if (n > 0) {
          k = n;
        }
        else
        {
          k = 4000;
        }

        j = -1;
        paramArrayOfAccessor[paramInt] = new T4CVarcharAccessor(this.statement, k, paramT4C8TTIuds.udsnull, paramT4C8TTIuds.udsoac.oacflg, paramT4C8TTIuds.udsoac.oacpre, paramT4C8TTIuds.udsoac.oacscl, paramT4C8TTIuds.udsoac.oacfl2, paramT4C8TTIuds.udsoac.oacmal, paramT4C8TTIuds.udsoac.oaccsfrm, j, m, n, this.meg);

        paramArrayOfAccessor[paramInt].describeType = 8;
      }
      else
      {
        j = 0;

        paramArrayOfAccessor[paramInt] = new T4CLongAccessor(this.statement, paramInt + 1, j, paramT4C8TTIuds.udsnull, paramT4C8TTIuds.udsoac.oacflg, paramT4C8TTIuds.udsoac.oacpre, paramT4C8TTIuds.udsoac.oacscl, paramT4C8TTIuds.udsoac.oacfl2, paramT4C8TTIuds.udsoac.oacmal, paramT4C8TTIuds.udsoac.oaccsfrm, m, n, this.meg);
      }

      break;
    case 6:
      paramArrayOfAccessor[paramInt] = new T4CVarnumAccessor(this.statement, j, paramT4C8TTIuds.udsnull, paramT4C8TTIuds.udsoac.oacflg, paramT4C8TTIuds.udsoac.oacpre, paramT4C8TTIuds.udsoac.oacscl, paramT4C8TTIuds.udsoac.oacfl2, paramT4C8TTIuds.udsoac.oacmal, paramT4C8TTIuds.udsoac.oaccsfrm, m, n, this.meg);

      break;
    case 100:
      paramArrayOfAccessor[paramInt] = new T4CBinaryFloatAccessor(this.statement, 4, paramT4C8TTIuds.udsnull, paramT4C8TTIuds.udsoac.oacflg, paramT4C8TTIuds.udsoac.oacpre, paramT4C8TTIuds.udsoac.oacscl, paramT4C8TTIuds.udsoac.oacfl2, paramT4C8TTIuds.udsoac.oacmal, paramT4C8TTIuds.udsoac.oaccsfrm, m, n, this.meg);

      break;
    case 101:
      paramArrayOfAccessor[paramInt] = new T4CBinaryDoubleAccessor(this.statement, 8, paramT4C8TTIuds.udsnull, paramT4C8TTIuds.udsoac.oacflg, paramT4C8TTIuds.udsoac.oacpre, paramT4C8TTIuds.udsoac.oacscl, paramT4C8TTIuds.udsoac.oacfl2, paramT4C8TTIuds.udsoac.oacmal, paramT4C8TTIuds.udsoac.oaccsfrm, m, n, this.meg);

      break;
    case 23:
      paramArrayOfAccessor[paramInt] = new T4CRawAccessor(this.statement, j, paramT4C8TTIuds.udsnull, paramT4C8TTIuds.udsoac.oacflg, paramT4C8TTIuds.udsoac.oacpre, paramT4C8TTIuds.udsoac.oacscl, paramT4C8TTIuds.udsoac.oacfl2, paramT4C8TTIuds.udsoac.oacmal, paramT4C8TTIuds.udsoac.oaccsfrm, m, n, this.meg);

      break;
    case 24:
      if ((m == -2) && (n < 2001) && (this.connection.versionNumber >= 9000))
      {
        j = -1;
        paramArrayOfAccessor[paramInt] = new T4CRawAccessor(this.statement, j, paramT4C8TTIuds.udsnull, paramT4C8TTIuds.udsoac.oacflg, paramT4C8TTIuds.udsoac.oacpre, paramT4C8TTIuds.udsoac.oacscl, paramT4C8TTIuds.udsoac.oacfl2, paramT4C8TTIuds.udsoac.oacmal, paramT4C8TTIuds.udsoac.oaccsfrm, m, n, this.meg);

        paramArrayOfAccessor[paramInt].describeType = 24;
      }
      else {
        paramArrayOfAccessor[paramInt] = new T4CLongRawAccessor(this.statement, paramInt + 1, j, paramT4C8TTIuds.udsnull, paramT4C8TTIuds.udsoac.oacflg, paramT4C8TTIuds.udsoac.oacpre, paramT4C8TTIuds.udsoac.oacscl, paramT4C8TTIuds.udsoac.oacfl2, paramT4C8TTIuds.udsoac.oacmal, paramT4C8TTIuds.udsoac.oaccsfrm, m, n, this.meg);
      }

      break;
    case 11:
    case 104:
    case 208:
      paramArrayOfAccessor[paramInt] = new T4CRowidAccessor(this.statement, j, paramT4C8TTIuds.udsnull, paramT4C8TTIuds.udsoac.oacflg, paramT4C8TTIuds.udsoac.oacpre, paramT4C8TTIuds.udsoac.oacscl, paramT4C8TTIuds.udsoac.oacfl2, paramT4C8TTIuds.udsoac.oacmal, paramT4C8TTIuds.udsoac.oaccsfrm, m, n, this.meg);

      if (paramT4C8TTIuds.udsoac.oacdty == 208)
        paramArrayOfAccessor[paramInt].describeType = 208; break;
    case 102:
      paramArrayOfAccessor[paramInt] = new T4CResultSetAccessor(this.statement, j, paramT4C8TTIuds.udsnull, paramT4C8TTIuds.udsoac.oacflg, paramT4C8TTIuds.udsoac.oacpre, paramT4C8TTIuds.udsoac.oacscl, paramT4C8TTIuds.udsoac.oacfl2, paramT4C8TTIuds.udsoac.oacmal, paramT4C8TTIuds.udsoac.oaccsfrm, m, n, this.meg);

      break;
    case 12:
      paramArrayOfAccessor[paramInt] = new T4CDateAccessor(this.statement, j, paramT4C8TTIuds.udsnull, paramT4C8TTIuds.udsoac.oacflg, paramT4C8TTIuds.udsoac.oacpre, paramT4C8TTIuds.udsoac.oacscl, paramT4C8TTIuds.udsoac.oacfl2, paramT4C8TTIuds.udsoac.oacmal, paramT4C8TTIuds.udsoac.oaccsfrm, m, n, this.meg);

      break;
    case 113:
      if ((m == -4) && (this.connection.versionNumber >= 9000))
      {
        paramArrayOfAccessor[paramInt] = new T4CLongRawAccessor(this.statement, paramInt + 1, 2147483647, paramT4C8TTIuds.udsnull, paramT4C8TTIuds.udsoac.oacflg, paramT4C8TTIuds.udsoac.oacpre, paramT4C8TTIuds.udsoac.oacscl, paramT4C8TTIuds.udsoac.oacfl2, paramT4C8TTIuds.udsoac.oacmal, paramT4C8TTIuds.udsoac.oaccsfrm, m, n, this.meg);

        paramArrayOfAccessor[paramInt].describeType = 113;
      }
      else if ((m == -3) && (this.connection.versionNumber >= 9000))
      {
        paramArrayOfAccessor[paramInt] = new T4CRawAccessor(this.statement, 4000, paramT4C8TTIuds.udsnull, paramT4C8TTIuds.udsoac.oacflg, paramT4C8TTIuds.udsoac.oacpre, paramT4C8TTIuds.udsoac.oacscl, paramT4C8TTIuds.udsoac.oacfl2, paramT4C8TTIuds.udsoac.oacmal, paramT4C8TTIuds.udsoac.oaccsfrm, m, n, this.meg);

        paramArrayOfAccessor[paramInt].describeType = 113;
      }
      else
      {
        paramArrayOfAccessor[paramInt] = new T4CBlobAccessor(this.statement, j, paramT4C8TTIuds.udsnull, paramT4C8TTIuds.udsoac.oacflg, paramT4C8TTIuds.udsoac.oacpre, paramT4C8TTIuds.udsoac.oacscl, paramT4C8TTIuds.udsoac.oacfl2, paramT4C8TTIuds.udsoac.oacmal, paramT4C8TTIuds.udsoac.oaccsfrm, m, n, this.meg);

        if ((this.connection.useLobPrefetch) && (m == 2004))
        {
          paramArrayOfAccessor[paramInt].lobPrefetchSizeForThisColumn = n;
        }
        else
        {
          paramArrayOfAccessor[paramInt].lobPrefetchSizeForThisColumn = -1;
        }
      }
      break;
    case 112:
      i2 = 1;
      if (i1 != 0) {
        i2 = (short)i1;
      }
      if ((m == -1) && (this.connection.versionNumber >= 9000))
      {
        j = 0;
        paramArrayOfAccessor[paramInt] = new T4CLongAccessor(this.statement, paramInt + 1, 2147483647, paramT4C8TTIuds.udsnull, paramT4C8TTIuds.udsoac.oacflg, paramT4C8TTIuds.udsoac.oacpre, paramT4C8TTIuds.udsoac.oacscl, paramT4C8TTIuds.udsoac.oacfl2, paramT4C8TTIuds.udsoac.oacmal, (short)i2, m, n, this.meg);

        paramArrayOfAccessor[paramInt].describeType = 112;
      }
      else if (((m == 12) || (m == 1)) && (this.connection.versionNumber >= 9000))
      {
        k = 4000;
        if ((n > 0) && (n < k))
        {
          k = n;
        }
        paramArrayOfAccessor[paramInt] = new T4CVarcharAccessor(this.statement, k, paramT4C8TTIuds.udsnull, paramT4C8TTIuds.udsoac.oacflg, paramT4C8TTIuds.udsoac.oacpre, paramT4C8TTIuds.udsoac.oacscl, paramT4C8TTIuds.udsoac.oacfl2, paramT4C8TTIuds.udsoac.oacmal, (short)i2, 4000, m, n, this.meg);

        paramArrayOfAccessor[paramInt].describeType = 112;
      }
      else {
        paramArrayOfAccessor[paramInt] = new T4CClobAccessor(this.statement, j, paramT4C8TTIuds.udsnull, paramT4C8TTIuds.udsoac.oacflg, paramT4C8TTIuds.udsoac.oacpre, paramT4C8TTIuds.udsoac.oacscl, paramT4C8TTIuds.udsoac.oacfl2, paramT4C8TTIuds.udsoac.oacmal, paramT4C8TTIuds.udsoac.oaccsfrm, m, n, this.meg);

        if ((this.connection.useLobPrefetch) && ((m == 2005) || (m == 2011)))
        {
          paramArrayOfAccessor[paramInt].lobPrefetchSizeForThisColumn = n;
        }
        else paramArrayOfAccessor[paramInt].lobPrefetchSizeForThisColumn = -1;
      }
      break;
    case 114:
      paramArrayOfAccessor[paramInt] = new T4CBfileAccessor(this.statement, j, paramT4C8TTIuds.udsnull, paramT4C8TTIuds.udsoac.oacflg, paramT4C8TTIuds.udsoac.oacpre, paramT4C8TTIuds.udsoac.oacscl, paramT4C8TTIuds.udsoac.oacfl2, paramT4C8TTIuds.udsoac.oacmal, paramT4C8TTIuds.udsoac.oaccsfrm, m, n, this.meg);

      if ((this.connection.useLobPrefetch) && (m == -13))
      {
        paramArrayOfAccessor[paramInt].lobPrefetchSizeForThisColumn = n;
      }
      else paramArrayOfAccessor[paramInt].lobPrefetchSizeForThisColumn = -1;
      break;
    case 109:
      str1 = this.meg.conv.CharBytesToString(paramT4C8TTIuds.getTypeName(), paramT4C8TTIuds.getTypeCharLength());

      str2 = this.meg.conv.CharBytesToString(paramT4C8TTIuds.getSchemaName(), paramT4C8TTIuds.getSchemaCharLength());

      str3 = str2 + "." + str1;

      paramArrayOfAccessor[paramInt] = new T4CNamedTypeAccessor(this.statement, j, paramT4C8TTIuds.udsnull, paramT4C8TTIuds.udsoac.oacflg, paramT4C8TTIuds.udsoac.oacpre, paramT4C8TTIuds.udsoac.oacscl, paramT4C8TTIuds.udsoac.oacfl2, paramT4C8TTIuds.udsoac.oacmal, paramT4C8TTIuds.udsoac.oaccsfrm, str3, m, n, this.meg);

      break;
    case 111:
      str1 = this.meg.conv.CharBytesToString(paramT4C8TTIuds.getTypeName(), paramT4C8TTIuds.getTypeCharLength());

      str2 = this.meg.conv.CharBytesToString(paramT4C8TTIuds.getSchemaName(), paramT4C8TTIuds.getSchemaCharLength());

      str3 = str2 + "." + str1;

      paramArrayOfAccessor[paramInt] = new T4CRefTypeAccessor(this.statement, j, paramT4C8TTIuds.udsnull, paramT4C8TTIuds.udsoac.oacflg, paramT4C8TTIuds.udsoac.oacpre, paramT4C8TTIuds.udsoac.oacscl, paramT4C8TTIuds.udsoac.oacfl2, paramT4C8TTIuds.udsoac.oacmal, paramT4C8TTIuds.udsoac.oaccsfrm, str3, m, n, this.meg);

      break;
    case 180:
      paramArrayOfAccessor[paramInt] = new T4CTimestampAccessor(this.statement, j, paramT4C8TTIuds.udsnull, paramT4C8TTIuds.udsoac.oacflg, paramT4C8TTIuds.udsoac.oacpre, paramT4C8TTIuds.udsoac.oacscl, paramT4C8TTIuds.udsoac.oacfl2, paramT4C8TTIuds.udsoac.oacmal, paramT4C8TTIuds.udsoac.oaccsfrm, m, n, this.meg);

      break;
    case 181:
      paramArrayOfAccessor[paramInt] = new T4CTimestamptzAccessor(this.statement, j, paramT4C8TTIuds.udsnull, paramT4C8TTIuds.udsoac.oacflg, paramT4C8TTIuds.udsoac.oacpre, paramT4C8TTIuds.udsoac.oacscl, paramT4C8TTIuds.udsoac.oacfl2, paramT4C8TTIuds.udsoac.oacmal, paramT4C8TTIuds.udsoac.oaccsfrm, m, n, this.meg);

      break;
    case 231:
      paramArrayOfAccessor[paramInt] = new T4CTimestampltzAccessor(this.statement, j, paramT4C8TTIuds.udsnull, paramT4C8TTIuds.udsoac.oacflg, paramT4C8TTIuds.udsoac.oacpre, paramT4C8TTIuds.udsoac.oacscl, paramT4C8TTIuds.udsoac.oacfl2, paramT4C8TTIuds.udsoac.oacmal, paramT4C8TTIuds.udsoac.oaccsfrm, m, n, this.meg);

      break;
    case 182:
      paramArrayOfAccessor[paramInt] = new T4CIntervalymAccessor(this.statement, j, paramT4C8TTIuds.udsnull, paramT4C8TTIuds.udsoac.oacflg, paramT4C8TTIuds.udsoac.oacpre, paramT4C8TTIuds.udsoac.oacscl, paramT4C8TTIuds.udsoac.oacfl2, paramT4C8TTIuds.udsoac.oacmal, paramT4C8TTIuds.udsoac.oaccsfrm, m, n, this.meg);

      break;
    case 183:
      paramArrayOfAccessor[paramInt] = new T4CIntervaldsAccessor(this.statement, j, paramT4C8TTIuds.udsnull, paramT4C8TTIuds.udsoac.oacflg, paramT4C8TTIuds.udsoac.oacpre, paramT4C8TTIuds.udsoac.oacscl, paramT4C8TTIuds.udsoac.oacfl2, paramT4C8TTIuds.udsoac.oacmal, paramT4C8TTIuds.udsoac.oaccsfrm, m, n, this.meg);

      break;
    default:
      paramArrayOfAccessor[paramInt] = null;
    }

    if (paramT4C8TTIuds.udsoac.oactoid.length > 0)
    {
      paramArrayOfAccessor[paramInt].internalOtype = new OracleTypeADT(paramT4C8TTIuds.udsoac.oactoid, paramT4C8TTIuds.udsoac.oacvsn, paramT4C8TTIuds.udsoac.oaccsi, paramT4C8TTIuds.udsoac.oaccsfrm, str2 + "." + str1);
    }
    else
    {
      paramArrayOfAccessor[paramInt].internalOtype = null;
    }

    paramArrayOfAccessor[paramInt].columnName = paramString;

    paramArrayOfAccessor[paramInt].securityAttribute = OracleResultSetMetaData.SecurityAttribute.NONE;

    if ((paramT4C8TTIuds.udsflg & 0x1) != 0) {
      paramArrayOfAccessor[paramInt].securityAttribute = OracleResultSetMetaData.SecurityAttribute.ENABLED;
    }
    else if ((paramT4C8TTIuds.udsflg & 0x2) != 0) {
      paramArrayOfAccessor[paramInt].securityAttribute = OracleResultSetMetaData.SecurityAttribute.UNKNOWN;
    }

    if (paramT4C8TTIuds.udsoac.oacmxl == 0) {
      paramArrayOfAccessor[paramInt].isNullByDescribe = true;
    }
    paramArrayOfAccessor[paramInt].udskpos = paramT4C8TTIuds.getKernelPosition();

    this.colNameSB = null;
  }
}