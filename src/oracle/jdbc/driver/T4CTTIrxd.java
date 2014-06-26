package oracle.jdbc.driver;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.BitSet;
import java.util.Vector;
import oracle.jdbc.OracleResultSetMetaData.SecurityAttribute;
import oracle.jdbc.internal.OracleConnection;
import oracle.jdbc.oracore.OracleTypeADT;

class T4CTTIrxd extends T4CTTIMsg
{
  static final byte[] NO_BYTES = new byte[0];
  byte[] buffer;
  byte[] bufferCHAR;
  BitSet bvcColSent = null;
  int nbOfColumns = 0;
  boolean bvcFound = false;
  boolean isFirstCol;
  static final byte TTICMD_UNAUTHORIZED = 1;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  T4CTTIrxd(T4CConnection paramT4CConnection)
  {
    super(paramT4CConnection, (byte)7);

    this.isFirstCol = true;
  }

  void init()
  {
    this.isFirstCol = true;
  }

  void setNumberOfColumns(int paramInt)
  {
    this.nbOfColumns = paramInt;
    this.bvcFound = false;

    if ((this.bvcColSent == null) || (this.bvcColSent.length() < this.nbOfColumns))
      this.bvcColSent = new BitSet(this.nbOfColumns);
    else
      this.bvcColSent.clear();
  }

  void unmarshalBVC(int paramInt)
    throws SQLException, IOException
  {
    int i = 0;

    for (int j = 0; j < this.bvcColSent.length(); j++) {
      this.bvcColSent.clear(j);
    }

    int j = this.nbOfColumns / 8 + (this.nbOfColumns % 8 != 0 ? 1 : 0);

    for (int k = 0; k < j; k++)
    {
      int m = (byte)(this.meg.unmarshalUB1() & 0xFF);

      for (int n = 0; n < 8; n++)
      {
        if ((m & 1 << n) != 0)
        {
          this.bvcColSent.set(k * 8 + n);

          i++;
        }
      }
    }

    if (i != paramInt)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), -1, "INTERNAL ERROR: oracle.jdbc.driver.T4CTTIrxd.unmarshalBVC: bits missing in vector");

      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.bvcFound = true;
  }

  void readBitVector(byte[] paramArrayOfByte)
    throws SQLException, IOException
  {
    for (int i = 0; i < this.bvcColSent.length(); i++) {
      this.bvcColSent.clear(i);
    }
    if ((paramArrayOfByte == null) || (paramArrayOfByte.length == 0)) {
      this.bvcFound = false;
    }
    else {
      for (int i = 0; i < paramArrayOfByte.length; i++) {
        int j = paramArrayOfByte[i];
        for (int k = 0; k < 8; k++) {
          if ((j & 1 << k) != 0)
            this.bvcColSent.set(i * 8 + k);
        }
      }
      this.bvcFound = true;
    }
  }

  Vector<IOException> marshal(byte[] paramArrayOfByte1, char[] paramArrayOfChar1, short[] paramArrayOfShort1, int paramInt1, byte[] paramArrayOfByte2, DBConversion paramDBConversion, InputStream[] paramArrayOfInputStream, byte[][] paramArrayOfByte, OracleTypeADT[] paramArrayOfOracleTypeADT, byte[] paramArrayOfByte3, char[] paramArrayOfChar2, short[] paramArrayOfShort2, byte[] paramArrayOfByte4, int paramInt2, int[] paramArrayOfInt1, boolean paramBoolean, int[] paramArrayOfInt2, int[] paramArrayOfInt3, int[][] paramArrayOfInt)
    throws IOException
  {
    Vector localVector = null;
    try
    {
      marshalTTCcode();

      int i = paramArrayOfShort1[(paramInt1 + 0)] & 0xFFFF;

      int i10 = 0;
      int i11 = paramArrayOfInt3[0];
      int[] localObject1 = paramArrayOfInt[0];

      int i12 = 0;
      int i14;
      int j;
      int i7;
      int k;
      int i9;
      int m;
      int i8;
      int i22;
      int i23;
      int i4;
      int i6;
      int i5;
      int i1;
      int i3;
      int i17;
      int i19;
      for (int i13 = 0; i13 < i; i13++)
      {
        if ((i10 < i11) && (localObject1[i10] == i13))
        {
          i10++;
        }
        else
        {
          i14 = 0;

          j = paramInt1 + 5 + 10 * i13;

          i7 = paramArrayOfShort1[(j + 0)] & 0xFFFF;

          if ((paramArrayOfByte4 != null) && ((paramArrayOfByte4[i13] & 0x20) == 0))
          {
            if (i7 == 998) {
              i12++;
            }
          }
          else
          {
            k = ((paramArrayOfShort1[(j + 7)] & 0xFFFF) << 16) + (paramArrayOfShort1[(j + 8)] & 0xFFFF) + paramInt2;

            i9 = ((paramArrayOfShort1[(j + 5)] & 0xFFFF) << 16) + (paramArrayOfShort1[(j + 6)] & 0xFFFF) + paramInt2;

            m = paramArrayOfShort1[k] & 0xFFFF;

            i8 = paramArrayOfShort1[i9];

            if (i7 == 116)
            {
              this.meg.marshalUB1((short)1);
              this.meg.marshalUB1((short)0);
            }
            else
            {
              if (i7 == 994)
              {
                i8 = -1;
                int i15 = paramArrayOfInt2[(3 + i13 * 3 + 0)];

                if (i15 == 109)
                  i14 = 1;
              }
              else if ((i7 == 8) || (i7 == 24) || ((!paramBoolean) && (paramArrayOfInt1 != null) && (paramArrayOfInt1.length > i13) && (paramArrayOfInt1[i13] > 4000)))
              {
                if (i11 >= localObject1.length)
                {
                  int[] arrayOfInt = new int[localObject1.length << 1];

                  System.arraycopy(localObject1, 0, arrayOfInt, 0, localObject1.length);

                  localObject1 = arrayOfInt;
                }

                localObject1[(i11++)] = i13;

                continue;
              }

              if (i8 == -1)
              {
                if ((i7 == 109) || (i14 != 0))
                {
                  this.meg.marshalDALC(NO_BYTES);
                  this.meg.marshalDALC(NO_BYTES);
                  this.meg.marshalDALC(NO_BYTES);
                  this.meg.marshalUB2(0);
                  this.meg.marshalUB4(0L);
                  this.meg.marshalUB2(1);

                  continue;
                }
                if (i7 == 998)
                {
                  i12++;
                  this.meg.marshalUB4(0L);
                  continue;
                }
                if ((i7 == 112) || (i7 == 113) || (i7 == 114))
                {
                  this.meg.marshalUB4(0L);
                  continue;
                }
                if ((i7 != 8) && (i7 != 24))
                {
                  this.meg.marshalUB1((short)0);

                  continue;
                }
              }
              int i18;
              int i24;
              if (i7 == 998)
              {
                int i16 = (paramArrayOfShort2[(6 + i12 * 8 + 4)] & 0xFFFF) << 16 & 0xFFFF000 | paramArrayOfShort2[(6 + i12 * 8 + 5)] & 0xFFFF;

                i18 = (paramArrayOfShort2[(6 + i12 * 8 + 6)] & 0xFFFF) << 16 & 0xFFFF000 | paramArrayOfShort2[(6 + i12 * 8 + 7)] & 0xFFFF;

                int i20 = paramArrayOfShort2[(6 + i12 * 8)] & 0xFFFF;
                i22 = paramArrayOfShort2[(6 + i12 * 8 + 1)] & 0xFFFF;

                this.meg.marshalUB4(i16);

                for (i23 = 0; i23 < i16; i23++)
                {
                  i24 = i18 + i23 * i22;

                  if (i20 == 9)
                  {
                    i4 = paramArrayOfChar2[i24] / '\002';
                    i6 = 0;
                    i6 = paramDBConversion.javaCharsToCHARBytes(paramArrayOfChar2, i24 + 1, paramArrayOfByte2, 0, i4);

                    this.meg.marshalCLR(paramArrayOfByte2, i6);
                  }
                  else
                  {
                    m = paramArrayOfByte3[i24];

                    if (m < 1)
                      this.meg.marshalUB1((short)0);
                    else {
                      this.meg.marshalCLR(paramArrayOfByte3, i24 + 1, m);
                    }
                  }
                }
                i12++;
              }
              else
              {
                int n = paramArrayOfShort1[(j + 1)] & 0xFFFF;

                if (n != 0)
                {
                  int i2 = ((paramArrayOfShort1[(j + 3)] & 0xFFFF) << 16) + (paramArrayOfShort1[(j + 4)] & 0xFFFF) + n * paramInt2;

                  if (i7 == 6)
                  {
                    i2++;
                    m--;
                  }
                  else if (i7 == 9)
                  {
                    i2 += 2;

                    m -= 2;
                  }
                  else if ((i7 == 114) || (i7 == 113) || (i7 == 112))
                  {
                    this.meg.marshalUB4(m);
                  }

                  if ((i7 == 109) || (i7 == 111))
                  {
                    if (paramArrayOfByte == null)
                    {
                      SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), -1, "INTERNAL ERROR: oracle.jdbc.driver.T4CTTIrxd.marshal: parameterDatum is null");

                      sqlexception.fillInStackTrace();
                      throw sqlexception;
                    }

                    byte[] localObject2 = paramArrayOfByte[i13];

                    m = localObject2 == null ? 0 : localObject2.length;

                    if (i7 == 109)
                    {
                      this.meg.marshalDALC(NO_BYTES);
                      this.meg.marshalDALC(NO_BYTES);
                      this.meg.marshalDALC(NO_BYTES);
                      this.meg.marshalUB2(0);

                      this.meg.marshalUB4(m);
                      this.meg.marshalUB2(1);
                    }

                    if (m > 0)
                      this.meg.marshalCLR((byte[])localObject2, 0, m);
                  }
                  else if (i7 == 104)
                  {
                    i2 += 2;

                    long[] localObject2 = T4CRowidAccessor.stringToRowid(paramArrayOfByte1, i2, 18);

                    i18 = 14;
                    long l1 = localObject2[0];
                    i23 = (int)localObject2[1];
                    i24 = 0;
                    long l2 = localObject2[2];
                    int i25 = (int)localObject2[3];

                    if ((l1 == 0L) && (i23 == 0) && (l2 == 0L) && (i25 == 0))
                    {
                      this.meg.marshalUB1((short)0);
                    }
                    else
                    {
                      this.meg.marshalUB1((short)i18);
                      this.meg.marshalUB4(l1);
                      this.meg.marshalUB2(i23);
                      this.meg.marshalUB1((short)i24);
                      this.meg.marshalUB4(l2);
                      this.meg.marshalUB2(i25);
                    }
                  }
                  else if (i7 == 208)
                  {
                    i2 += 2;
                    m -= 2;
                    this.meg.marshalUB4(m);
                    this.meg.marshalCLR(paramArrayOfByte1, i2, m);
                  }
                  else if (m < 1) {
                    this.meg.marshalUB1((short)0);
                  } else {
                    this.meg.marshalCLR(paramArrayOfByte1, i2, m);
                  }

                }
                else
                {
                  i5 = paramArrayOfShort1[(j + 9)] & 0xFFFF;

                  i1 = paramArrayOfShort1[(j + 2)] & 0xFFFF;

                  i3 = ((paramArrayOfShort1[(j + 3)] & 0xFFFF) << 16) + (paramArrayOfShort1[(j + 4)] & 0xFFFF) + i1 * paramInt2 + 1;

                  if (i7 == 996)
                  {
                    i17 = paramArrayOfChar1[(i3 - 1)];

                    if ((this.bufferCHAR == null) || (this.bufferCHAR.length < i17)) {
                      this.bufferCHAR = new byte[i17];
                    }
                    for (i19 = 0; i19 < i17; i19++)
                    {
                      this.bufferCHAR[i19] = ((byte)((paramArrayOfChar1[(i3 + i19 / 2)] & 0xFF00) >> '\b' & 0xFF));

                      if (i19 < i17 - 1)
                      {
                        this.bufferCHAR[(i19 + 1)] = ((byte)(paramArrayOfChar1[(i3 + i19 / 2)] & 0xFF & 0xFF));

                        i19++;
                      }
                    }

                    this.meg.marshalCLR(this.bufferCHAR, i17);

                    if (this.bufferCHAR.length > 4000) {
                      this.bufferCHAR = null;
                    }

                  }
                  else
                  {
                    if (i7 == 96)
                    {
                      i4 = m / 2;
                      i3--;
                    }
                    else
                    {
                      i4 = (m - 2) / 2;
                    }

                    i6 = 0;

                    if (i5 == 2)
                    {
                      i6 = paramDBConversion.javaCharsToNCHARBytes(paramArrayOfChar1, i3, paramArrayOfByte2, 0, i4);
                    }
                    else
                    {
                      i6 = paramDBConversion.javaCharsToCHARBytes(paramArrayOfChar1, i3, paramArrayOfByte2, 0, i4);
                    }

                    this.meg.marshalCLR(paramArrayOfByte2, i6);
                  }
                }
              }
            }
          }
        }

      }

      if (i11 > 0)
      {
        for (int i13 = 0; i13 < i11; i13++)
        {
          i14 = localObject1[i13];

          j = paramInt1 + 5 + 10 * i14;

          i7 = paramArrayOfShort1[(j + 0)] & 0xFFFF;

          k = ((paramArrayOfShort1[(j + 7)] & 0xFFFF) << 16) + (paramArrayOfShort1[(j + 8)] & 0xFFFF) + paramInt2;

          i9 = ((paramArrayOfShort1[(j + 5)] & 0xFFFF) << 16) + (paramArrayOfShort1[(j + 6)] & 0xFFFF) + paramInt2;

          i8 = paramArrayOfShort1[i9];
          m = paramArrayOfShort1[k] & 0xFFFF;

          i1 = paramArrayOfShort1[(j + 2)] & 0xFFFF;

          i3 = ((paramArrayOfShort1[(j + 3)] & 0xFFFF) << 16) + (paramArrayOfShort1[(j + 4)] & 0xFFFF) + i1 * paramInt2 + 1;

          if (i8 == -1)
          {
            this.meg.marshalUB1((short)0);
          }
          else if (i7 == 996)
          {
            i17 = paramArrayOfChar1[(i3 - 1)];

            if ((this.bufferCHAR == null) || (this.bufferCHAR.length < i17)) {
              this.bufferCHAR = new byte[i17];
            }
            for (i19 = 0; i19 < i17; i19++)
            {
              this.bufferCHAR[i19] = ((byte)((paramArrayOfChar1[(i3 + i19 / 2)] & 0xFF00) >> '\b' & 0xFF));

              if (i19 < i17 - 1)
              {
                this.bufferCHAR[(i19 + 1)] = ((byte)(paramArrayOfChar1[(i3 + i19 / 2)] & 0xFF & 0xFF));

                i19++;
              }
            }

            this.meg.marshalCLR(this.bufferCHAR, i17);

            if (this.bufferCHAR.length > 4000)
              this.bufferCHAR = null;
          }
          else if ((i7 != 8) && (i7 != 24))
          {
            if (i7 == 96)
            {
              i4 = m / 2;
              i3--;
            }
            else
            {
              i4 = (m - 2) / 2;
            }

            i5 = paramArrayOfShort1[(j + 9)] & 0xFFFF;

            i6 = 0;

            if (i5 == 2)
            {
              i6 = paramDBConversion.javaCharsToNCHARBytes(paramArrayOfChar1, i3, paramArrayOfByte2, 0, i4);
            }
            else
            {
              i6 = paramDBConversion.javaCharsToCHARBytes(paramArrayOfChar1, i3, paramArrayOfByte2, 0, i4);
            }

            this.meg.marshalCLR(paramArrayOfByte2, i6);
          }
          else
          {
            i17 = i14;

            if (paramArrayOfInputStream != null)
            {
              InputStream localInputStream = paramArrayOfInputStream[i17];

              if (localInputStream != null)
              {
                int i21 = 64;

                if (this.buffer == null) {
                  this.buffer = new byte[i21];
                }
                i22 = 0;

                this.meg.marshalUB1((short)254);

                i23 = 0;

                while ((i23 == 0) && (!this.connection.sentCancel))
                {
                  try {
                    i22 = localInputStream.read(this.buffer, 0, i21);
                  } catch (IOException localIOException2) {
                    i22 = -1;
                    if (localVector == null)
                      localVector = new Vector();
                    localVector.add(localIOException2);
                  }

                  if (i22 == -1) {
                    i23 = 1;
                  }
                  if (i22 > 0)
                  {
                    this.meg.marshalUB1((short)(i22 & 0xFF));

                    this.meg.marshalB1Array(this.buffer, 0, i22);
                  }

                }

                this.meg.marshalSB1((byte)0);
              }

            }

          }

        }

      }

      paramArrayOfInt3[0] = i11;
      paramArrayOfInt[0] = localObject1;
    }
    catch (SQLException localSQLException)
    {
      IOException localIOException1 = new IOException();
      localIOException1.initCause(localSQLException);
      throw localIOException1;
    }

    return localVector;
  }

  boolean unmarshal(Accessor[] paramArrayOfAccessor, int paramInt)
    throws SQLException, IOException
  {
    return unmarshal(paramArrayOfAccessor, 0, paramInt);
  }

  boolean unmarshal(Accessor[] paramArrayOfAccessor, int paramInt1, int paramInt2)
    throws SQLException, IOException
  {
    if (paramInt1 == 0) {
      this.isFirstCol = true;
    }
    for (int i = paramInt1; (i < paramInt2) && (i < paramArrayOfAccessor.length); i++)
    {
      if (paramArrayOfAccessor[i] != null)
      {
        byte j;
        int k;
        if (paramArrayOfAccessor[i].physicalColumnIndex < 0)
        {
          j = 0;

          for (k = 0; (k < paramInt2) && (k < paramArrayOfAccessor.length); k++)
          {
            if (paramArrayOfAccessor[k] != null)
            {
              paramArrayOfAccessor[k].physicalColumnIndex = j;

              if (!paramArrayOfAccessor[k].isUseLess) {
                j++;
              }
            }
          }
        }
        if ((this.bvcFound) && (!paramArrayOfAccessor[i].isUseLess) && (!this.bvcColSent.get(paramArrayOfAccessor[i].physicalColumnIndex)))
        {
          paramArrayOfAccessor[i].copyRow();
        }
        else
        {
          j = 0;

          if ((paramArrayOfAccessor[i].statement.statementType != 2) && (paramArrayOfAccessor[i].statement.sqlKind != 64) && (paramArrayOfAccessor[i].statement.sqlKind != 32))
          {
            k = paramArrayOfAccessor[i].metaDataIndex + paramArrayOfAccessor[i].lastRowProcessed * 1;

            if (paramArrayOfAccessor[i].securityAttribute == OracleResultSetMetaData.SecurityAttribute.ENABLED) {
              j = (byte)this.meg.unmarshalUB1();
            }
            paramArrayOfAccessor[i].rowSpaceMetaData[k] = j;
          }

          if (paramArrayOfAccessor[i].unmarshalOneRow()) {
            return true;
          }
          this.isFirstCol = false;
        }
      }
    }

    this.bvcFound = false;

    return false;
  }

  boolean unmarshal(Accessor[] paramArrayOfAccessor, int paramInt1, int paramInt2, int paramInt3)
    throws SQLException, IOException
  {
    return false;
  }

  protected OracleConnection getConnectionDuringExceptionHandling()
  {
    return null;
  }
}