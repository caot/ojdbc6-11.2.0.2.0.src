package oracle.jdbc.driver;

abstract class VarnumBinder extends Binder
{
  static final boolean DEBUG = false;
  static final boolean SLOW_CONVERSIONS = true;
  Binder theVarnumCopyingBinder = OraclePreparedStatementReadOnly.theStaticVarnumCopyingBinder;
  static final int LNXSGNBT = 128;
  static final byte LNXDIGS = 20;
  static final byte LNXEXPBS = 64;
  static final int LNXEXPMX = 127;
  static final double[] factorTable = { 9.999999999999999E+253D, 1.E+252D, 9.999999999999999E+249D, 1.0E+248D, 1.E+246D, 1.E+244D, 1.E+242D, 1.0E+240D, 1.0E+238D, 1.E+236D, 1.0E+234D, 1.E+232D, 1.E+230D, 9.999999999999999E+227D, 1.0E+226D, 1.0E+224D, 1.0E+222D, 1.0E+220D, 1.E+218D, 1.0E+216D, 1.0E+214D, 9.999999999999999E+211D, 9.999999999999999E+209D, 1.0E+208D, 1.0E+206D, 1.0E+204D, 9.999999999999999E+201D, 1.0E+200D, 1.0E+198D, 1.0E+196D, 9.999999999999999E+193D, 1.0E+192D, 1.E+190D, 1.0E+188D, 1.0E+186D, 1.0E+184D, 1.E+182D, 1.0E+180D, 1.E+178D, 1.0E+176D, 1.E+174D, 1.E+172D, 1.0E+170D, 9.999999999999999E+167D, 9.999999999999999E+165D, 1.0E+164D, 9.999999999999999E+161D, 1.0E+160D, 1.0E+158D, 1.0E+156D, 1.0E+154D, 1.0E+152D, 1.0E+150D, 1.0E+148D, 9.999999999999999E+145D, 1.0E+144D, 1.E+142D, 1.E+140D, 1.0E+138D, 1.E+136D, 9.999999999999999E+133D, 1.0E+132D, 1.E+130D, 1.E+128D, 9.999999999999999E+125D, 1.0E+124D, 1.0E+122D, 1.0E+120D, 1.0E+118D, 1.0E+116D, 1.0E+114D, 9.999999999999999E+111D, 1.0E+110D, 1.0E+108D, 1.E+106D, 1.0E+104D, 1.0E+102D, 1.0E+100D, 1.0E+98D, 1.0E+96D, 1.0E+94D, 1.0E+92D, 1.0E+90D, 1.0E+88D, 1.0E+86D, 1.E+84D, 1.0E+82D, 1.0E+80D, 1.0E+78D, 1.0E+76D, 1.0E+74D, 9.999999999999999E+71D, 1.E+70D, 1.0E+68D, 1.0E+66D, 1.0E+64D, 1.0E+62D, 1.0E+60D, 9.999999999999999E+57D, 1.E+56D, 1.E+54D, 1.0E+52D, 1.E+50D, 1.0E+48D, 1.0E+46D, 1.E+44D, 1.0E+42D, 1.0E+40D, 1.0E+38D, 1.0E+36D, 1.0E+34D, 1.E+32D, 1.0E+30D, 1.0E+28D, 1.0E+26D, 1.0E+24D, 1.0E+22D, 1.0E+20D, 1.0E+18D, 10000000000000000.0D, 100000000000000.0D, 1000000000000.0D, 10000000000.0D, 100000000.0D, 1000000.0D, 10000.0D, 100.0D, 1.0D, 0.01D, 0.0001D, 1.0E-06D, 1.0E-08D, 1.0E-10D, 1.0E-12D, 1.0E-14D, 1.0E-16D, 1.E-18D, 1.0E-20D, 1.0E-22D, 9.999999999999999E-25D, 1.0E-26D, 1.0E-28D, 1.E-30D, 1.E-32D, 9.999999999999999E-35D, 9.999999999999999E-37D, 1.0E-38D, 9.999999999999999E-41D, 1.0E-42D, 1.0E-44D, 1.0E-46D, 1.0E-48D, 1.0E-50D, 1.0E-52D, 1.0E-54D, 1.0E-56D, 1.0E-58D, 1.0E-60D, 1.0E-62D, 1.0E-64D, 1.0E-66D, 1.E-68D, 1.0E-70D, 1.0E-72D, 1.0E-74D, 9.999999999999999E-77D, 1.0E-78D, 1.0E-80D, 1.0E-82D, 1.0E-84D, 1.E-86D, 9.999999999999999E-89D, 1.0E-90D, 1.0E-92D, 1.0E-94D, 9.999999999999999E-97D, 9.999999999999999E-99D, 1.0E-100D, 9.999999999999999E-103D, 9.999999999999999E-105D, 9.999999999999999E-107D, 1.0E-108D, 1.E-110D, 1.0E-112D, 1.E-114D, 1.0E-116D, 1.0E-118D, 1.0E-120D, 1.E-122D, 9.999999999999999E-125D, 1.0E-126D, 1.E-128D, 1.E-130D, 1.0E-132D, 1.0E-134D, 1.0E-136D, 1.E-138D, 1.0E-140D, 1.0E-142D, 1.0E-144D, 1.0E-146D, 9.999999999999999E-149D, 1.0E-150D, 1.E-152D, 1.0E-154D, 1.0E-156D, 1.E-158D, 1.0E-160D, 1.0E-162D, 1.0E-164D, 1.0E-166D, 1.0E-168D, 1.0E-170D, 1.0E-172D, 1.0E-174D, 1.0E-176D, 1.0E-178D, 1.0E-180D, 1.0E-182D, 1.E-184D, 9.999999999999999E-187D, 1.0E-188D, 1.0E-190D, 1.E-192D, 1.0E-194D, 1.0E-196D, 9.999999999999999E-199D, 1.0E-200D, 1.0E-202D, 1.0E-204D, 1.0E-206D, 1.E-208D, 1.0E-210D, 1.0E-212D, 9.999999999999999E-215D, 1.0E-216D, 1.0E-218D, 1.0E-220D, 1.0E-222D, 1.0E-224D, 9.999999999999999E-227D, 1.0E-228D, 1.0E-230D, 1.0E-232D, 1.0E-234D, 1.0E-236D, 1.0E-238D, 1.0E-240D, 1.0E-242D, 9.999999999999999E-245D, 1.0E-246D, 1.0E-248D, 1.E-250D, 9.999999999999999E-253D, 9.999999999999999E-255D };
  static final int MANTISSA_SIZE = 53;
  static final int expShift = 52;
  static final long fractHOB = 4503599627370496L;
  static final long fractMask = 4503599627370495L;
  static final int expBias = 1023;
  static final int maxSmallBinExp = 62;
  static final int minSmallBinExp = -21;
  static final long expOne = 4607182418800017408L;
  static final long highbyte = -72057594037927936L;
  static final long highbit = -9223372036854775808L;
  static final long lowbytes = 72057594037927935L;
  static final int[] small5pow = { 1, 5, 25, 125, 625, 3125, 15625, 78125, 390625, 1953125, 9765625, 48828125, 244140625, 1220703125 };

  static final long[] long5pow = { 1L, 5L, 25L, 125L, 625L, 3125L, 15625L, 78125L, 390625L, 1953125L, 9765625L, 48828125L, 244140625L, 1220703125L, 6103515625L, 30517578125L, 152587890625L, 762939453125L, 3814697265625L, 19073486328125L, 95367431640625L, 476837158203125L, 2384185791015625L, 11920928955078125L, 59604644775390625L, 298023223876953125L, 1490116119384765625L };

  static final int[] n5bits = { 0, 3, 5, 7, 10, 12, 14, 17, 19, 21, 24, 26, 28, 31, 33, 35, 38, 40, 42, 45, 47, 49, 52, 54, 56, 59, 61 };
  static FDBigInt[] b5p;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  static void init(Binder paramBinder)
  {
    paramBinder.type = 6;
    paramBinder.bytelen = 22;
  }

  VarnumBinder()
  {
    init(this);
  }

  Binder copyingBinder()
  {
    return this.theVarnumCopyingBinder;
  }

  static int setLongInternal(byte[] paramArrayOfByte, int paramInt, long paramLong)
  {
    if (paramLong == 0L)
    {
      paramArrayOfByte[paramInt] = -128;

      return 1;
    }

    if (paramLong == -9223372036854775808L)
    {
      paramArrayOfByte[paramInt] = 53;
      paramArrayOfByte[(paramInt + 1)] = 92;
      paramArrayOfByte[(paramInt + 2)] = 79;
      paramArrayOfByte[(paramInt + 3)] = 68;
      paramArrayOfByte[(paramInt + 4)] = 29;
      paramArrayOfByte[(paramInt + 5)] = 98;
      paramArrayOfByte[(paramInt + 6)] = 33;
      paramArrayOfByte[(paramInt + 7)] = 47;
      paramArrayOfByte[(paramInt + 8)] = 24;
      paramArrayOfByte[(paramInt + 9)] = 43;
      paramArrayOfByte[(paramInt + 10)] = 93;
      paramArrayOfByte[(paramInt + 11)] = 102;

      return 12;
    }

    int i = 10;
    int j = 0;

    if (paramLong / 1000000000000000000L == 0L)
    {
      i--;

      if (paramLong / 10000000000000000L == 0L)
      {
        i--;

        if (paramLong / 100000000000000L == 0L)
        {
          i--;

          if (paramLong / 1000000000000L == 0L)
          {
            i--;

            if (paramLong / 10000000000L == 0L)
            {
              i--;

              if (paramLong / 100000000L == 0L)
              {
                i--;

                if (paramLong / 1000000L == 0L)
                {
                  i--;

                  if (paramLong / 10000L == 0L)
                  {
                    i--;

                    if (paramLong / 100L == 0L)
                    {
                      i--;
                    }
                  }
                }
              }
            }
          }
        }
      }
    }

    int k = paramInt + i;
    int m;
    if (paramLong < 0L)
    {
      paramLong = -paramLong;
      paramArrayOfByte[paramInt] = ((byte)(63 - i));
      while (true)
      {
        m = (int)(paramLong % 100L);

        if (j == 0)
        {
          if (m != 0)
          {
            paramArrayOfByte[(k + 1)] = 102;
            j = k + 2 - paramInt;
            paramArrayOfByte[k] = ((byte)(101 - m));
          }
        }
        else
        {
          paramArrayOfByte[k] = ((byte)(101 - m));
        }

        k--; if (k == paramInt) {
          break;
        }
        paramLong /= 100L;
      }

    }

    paramArrayOfByte[paramInt] = ((byte)(192 + i));
    while (true)
    {
      m = (int)(paramLong % 100L);

      if (j == 0)
      {
        if (m != 0)
        {
          j = k + 1 - paramInt;
          paramArrayOfByte[k] = ((byte)(m + 1));
        }
      }
      else
      {
        paramArrayOfByte[k] = ((byte)(m + 1));
      }

      k--; if (k == paramInt) {
        break;
      }
      paramLong /= 100L;
    }

    return j;
  }

  static int countBits(long paramLong)
  {
    if (paramLong == 0L) {
      return 0;
    }
    while ((paramLong & 0x0) == 0L)
    {
      paramLong <<= 8;
    }

    while (paramLong > 0L)
    {
      paramLong <<= 1;
    }

    int i = 0;

    while ((paramLong & 0xFFFFFFFF) != 0L)
    {
      paramLong <<= 8;
      i += 8;
    }

    while (paramLong != 0L)
    {
      paramLong <<= 1;
      i++;
    }

    return i;
  }

  boolean roundup(char[] paramArrayOfChar, int paramInt)
  {
    int i = paramInt - 1;
    int j = paramArrayOfChar[i];

    if (j == 57)
    {
      while ((j == 57) && (i > 0))
      {
        paramArrayOfChar[i] = '0';
        j = paramArrayOfChar[(--i)];
      }

      if (j == 57)
      {
        paramArrayOfChar[0] = '1';

        return true;
      }

    }

    paramArrayOfChar[i] = ((char)(j + 1));

    return false;
  }

  static FDBigInt big5pow(int paramInt)
  {
    if (paramInt < 0) {
      throw new RuntimeException("Assertion botch: negative power of 5");
    }
    if (b5p == null)
    {
      b5p = new FDBigInt[paramInt + 1];
    }
    else if (b5p.length <= paramInt)
    {
      FDBigInt[] arrayOfFDBigInt = new FDBigInt[paramInt + 1];

      System.arraycopy(b5p, 0, arrayOfFDBigInt, 0, b5p.length);

      b5p = arrayOfFDBigInt;
    }

    if (b5p[paramInt] != null)
      return b5p[paramInt];
    if (paramInt < small5pow.length)
      return b5p[paramInt] =  = new FDBigInt(small5pow[paramInt]);
    if (paramInt < long5pow.length) {
      return b5p[paramInt] =  = new FDBigInt(long5pow[paramInt]);
    }

    int i = paramInt >> 1;
    int j = paramInt - i;

    FDBigInt localFDBigInt1 = b5p[i];

    if (localFDBigInt1 == null) {
      localFDBigInt1 = big5pow(i);
    }
    if (j < small5pow.length)
    {
      return b5p[paramInt] =  = localFDBigInt1.mult(small5pow[j]);
    }

    FDBigInt localFDBigInt2 = b5p[j];

    if (localFDBigInt2 == null) {
      localFDBigInt2 = big5pow(j);
    }
    return b5p[paramInt] =  = localFDBigInt1.mult(localFDBigInt2);
  }

  static FDBigInt multPow52(FDBigInt paramFDBigInt, int paramInt1, int paramInt2)
  {
    if (paramInt1 != 0)
    {
      if (paramInt1 < small5pow.length)
      {
        paramFDBigInt = paramFDBigInt.mult(small5pow[paramInt1]);
      }
      else
      {
        paramFDBigInt = paramFDBigInt.mult(big5pow(paramInt1));
      }
    }

    if (paramInt2 != 0)
    {
      paramFDBigInt.lshiftMe(paramInt2);
    }

    return paramFDBigInt;
  }

  static FDBigInt constructPow52(int paramInt1, int paramInt2)
  {
    FDBigInt localFDBigInt = new FDBigInt(big5pow(paramInt1));

    if (paramInt2 != 0)
    {
      localFDBigInt.lshiftMe(paramInt2);
    }

    return localFDBigInt;
  }

  int dtoa(byte[] paramArrayOfByte, int paramInt1, double paramDouble, boolean paramBoolean1, boolean paramBoolean2, char[] paramArrayOfChar, int paramInt2, long paramLong, int paramInt3)
  {
    int i = 2147483647;
    int j = -1;
    int k = countBits(paramLong);

    int m = k - paramInt2 - 1;

    int i1 = 0;

    if (m < 0)
      m = 0;
    int i11;
    int i8;
    int i9;
    int i10;
    if ((paramInt2 <= 62) && (paramInt2 >= -21))
    {
      if ((m < long5pow.length) && (k + n5bits[m] < 64))
      {
        if (m == 0)
        {
          long l1;
          if (paramInt2 > paramInt3)
          {
            l1 = 1L << paramInt2 - paramInt3 - 1;
          }
          else
          {
            l1 = 0L;
          }

          if (paramInt2 >= 52)
          {
            paramLong <<= paramInt2 - 52;
          }
          else
          {
            paramLong >>>= 52 - paramInt2;
          }

          i = 0;

          long l2 = paramLong;
          long l3 = l1;

          for (i11 = 0; l3 >= 10L; i11++) {
            l3 /= 10L;
          }
          if (i11 != 0)
          {
            long l4 = long5pow[i11] << i11;
            long l5 = l2 % l4;

            l2 /= l4;
            i += i11;

            if (l5 >= l4 >> 1)
            {
              l2 += 1L;
            }
          }

          if (l2 <= 2147483647L)
          {
            int i12 = (int)l2;

            i8 = 10;
            i9 = i8 - 1;
            i10 = i12 % 10;
            i12 /= 10;

            while (i10 == 0)
            {
              i++;
              i10 = i12 % 10;
              i12 /= 10;
            }

            while (i12 != 0)
            {
              paramArrayOfChar[(i9--)] = ((char)(i10 + 48));
              i++;
              i10 = i12 % 10;
              i12 /= 10;
            }

            paramArrayOfChar[i9] = ((char)(i10 + 48));
          }
          else
          {
            i8 = 20;
            i9 = i8 - 1;
            i10 = (int)(l2 % 10L);
            l2 /= 10L;

            while (i10 == 0)
            {
              i++;
              i10 = (int)(l2 % 10L);
              l2 /= 10L;
            }

            while (l2 != 0L)
            {
              paramArrayOfChar[(i9--)] = ((char)(i10 + 48));
              i++;
              i10 = (int)(l2 % 10L);
              l2 /= 10L;
            }

            paramArrayOfChar[i9] = ((char)(i10 + 48));
          }

          i8 -= i9;

          if (i9 != 0) {
            System.arraycopy(paramArrayOfChar, i9, paramArrayOfChar, 0, i8);
          }
          i += 1;
          j = i8;

          i1 = 1;
        }
      }
    }

    if (i1 == 0)
    {
      double d = Double.longBitsToDouble(0x0 | paramLong & 0xFFFFFFFF);

      int n = (int)Math.floor((d - 1.5D) * 0.289529654D + 0.176091259D + paramInt2 * 0.301029995663981D);

      i5 = Math.max(0, -n);
      i4 = i5 + m + paramInt2;

      int i7 = Math.max(0, n);
      i6 = i7 + m;

      i9 = i5;
      i8 = i4 - paramInt3;

      paramLong >>>= 53 - k;
      i4 -= k - 1;

      int i13 = Math.min(i4, i6);

      i4 -= i13;
      i6 -= i13;
      i8 -= i13;

      if (k == 1) {
        i8--;
      }
      if (i8 < 0)
      {
        i4 -= i8;
        i6 -= i8;
        i8 = 0;
      }

      int i14 = 0;

      i10 = k + i4 + (i5 < n5bits.length ? n5bits[i5] : i5 * 3);
      i11 = i6 + 1 + (i7 + 1 < n5bits.length ? n5bits[(i7 + 1)] : (i7 + 1) * 3);
      int i19;
      int i17;
      int i15;
      int i16;
      long l6;
      if ((i10 < 64) && (i11 < 64))
      {
        if ((i10 < 32) && (i11 < 32))
        {
          int i18 = (int)paramLong * small5pow[i5] << i4;
          i19 = small5pow[i7] << i6;
          int i20 = small5pow[i9] << i8;
          int i21 = i19 * 10;

          i14 = 0;
          i17 = i18 / i19;
          i18 = 10 * (i18 % i19);
          i20 *= 10;
          i15 = i18 < i20 ? 1 : 0;
          i16 = i18 + i20 > i21 ? 1 : 0;

          if ((i17 == 0) && (i16 == 0))
          {
            n--;
          }
          else
          {
            paramArrayOfChar[(i14++)] = ((char)(48 + i17));
          }

          if ((n <= -3) || (n >= 8))
          {
            i16 = i15 = 0;
          }

          while ((i15 == 0) && (i16 == 0))
          {
            i17 = i18 / i19;
            i18 = 10 * (i18 % i19);
            i20 *= 10;

            if (i20 > 0L)
            {
              i15 = i18 < i20 ? 1 : 0;
              i16 = i18 + i20 > i21 ? 1 : 0;
            }
            else
            {
              i15 = 1;
              i16 = 1;
            }

            paramArrayOfChar[(i14++)] = ((char)(48 + i17));
          }

          l6 = (i18 << 1) - i21;
        }
        else
        {
          long l7 = paramLong * long5pow[i5] << i4;
          long l8 = long5pow[i7] << i6;
          long l9 = long5pow[i9] << i8;
          long l10 = l8 * 10L;

          i14 = 0;
          i17 = (int)(l7 / l8);
          l7 = 10L * (l7 % l8);
          l9 *= 10L;
          i15 = l7 < l9 ? 1 : 0;
          i16 = l7 + l9 > l10 ? 1 : 0;

          if ((i17 == 0) && (i16 == 0))
          {
            n--;
          }
          else
          {
            paramArrayOfChar[(i14++)] = ((char)(48 + i17));
          }

          if ((n <= -3) || (n >= 8))
          {
            i16 = i15 = 0;
          }

          while ((i15 == 0) && (i16 == 0))
          {
            i17 = (int)(l7 / l8);
            l7 = 10L * (l7 % l8);
            l9 *= 10L;

            if (l9 > 0L)
            {
              i15 = l7 < l9 ? 1 : 0;
              i16 = l7 + l9 > l10 ? 1 : 0;
            }
            else
            {
              i15 = 1;
              i16 = 1;
            }

            paramArrayOfChar[(i14++)] = ((char)(48 + i17));
          }

          l6 = (l7 << 1) - l10;
        }

      }
      else
      {
        FDBigInt localFDBigInt2 = multPow52(new FDBigInt(paramLong), i5, i4);
        FDBigInt localFDBigInt1 = constructPow52(i7, i6);
        FDBigInt localFDBigInt3 = constructPow52(i9, i8);

        localFDBigInt2.lshiftMe(i19 = localFDBigInt1.normalizeMe());
        localFDBigInt3.lshiftMe(i19);

        FDBigInt localFDBigInt4 = localFDBigInt1.mult(10);

        i14 = 0;
        i17 = localFDBigInt2.quoRemIteration(localFDBigInt1);
        localFDBigInt3 = localFDBigInt3.mult(10);
        i15 = localFDBigInt2.cmp(localFDBigInt3) < 0 ? 1 : 0;
        i16 = localFDBigInt2.add(localFDBigInt3).cmp(localFDBigInt4) > 0 ? 1 : 0;

        if ((i17 == 0) && (i16 == 0))
        {
          n--;
        }
        else
        {
          paramArrayOfChar[(i14++)] = ((char)(48 + i17));
        }

        if ((n <= -3) || (n >= 8))
        {
          i16 = i15 = 0;
        }

        while ((i15 == 0) && (i16 == 0))
        {
          i17 = localFDBigInt2.quoRemIteration(localFDBigInt1);
          localFDBigInt3 = localFDBigInt3.mult(10);
          i15 = localFDBigInt2.cmp(localFDBigInt3) < 0 ? 1 : 0;
          i16 = localFDBigInt2.add(localFDBigInt3).cmp(localFDBigInt4) > 0 ? 1 : 0;
          paramArrayOfChar[(i14++)] = ((char)(48 + i17));
        }

        if ((i16 != 0) && (i15 != 0))
        {
          localFDBigInt2.lshiftMe(1);

          l6 = localFDBigInt2.cmp(localFDBigInt4);
        }
        else {
          l6 = 0L;
        }
      }
      i = n + 1;
      j = i14;

      if (i16 != 0)
      {
        if (i15 != 0)
        {
          if (l6 == 0L)
          {
            if ((paramArrayOfChar[(j - 1)] & 0x1) != 0)
            {
              if (roundup(paramArrayOfChar, j))
                i++;
            }
          }
          else if (l6 > 0L)
          {
            if (roundup(paramArrayOfChar, j)) {
              i++;
            }
          }

        }
        else if (roundup(paramArrayOfChar, j)) {
          i++;
        }
      }
    }

    while ((j - i > 0) && (paramArrayOfChar[(j - 1)] == '0')) j--;

    int i2 = i % 2 != 0 ? 1 : 0;
    int i3;
    if (i2 != 0)
    {
      if (j % 2 == 0) {
        paramArrayOfChar[(j++)] = '0';
      }
      i3 = (i - 1) / 2;
    }
    else
    {
      if (j % 2 == 1) {
        paramArrayOfChar[(j++)] = '0';
      }
      i3 = (i - 2) / 2;
    }

    int i4 = 117 - i3;

    int i5 = 0;
    int i6 = 1;

    if (paramBoolean1)
    {
      paramArrayOfByte[paramInt1] = ((byte)(62 - i3));

      if (i2 != 0)
      {
        paramArrayOfByte[(paramInt1 + i6)] = ((byte)(101 - (paramArrayOfChar[(i5++)] - '0')));
        i6++;
      }

      while (i5 < j)
      {
        paramArrayOfByte[(paramInt1 + i6)] = ((byte)(101 - ((paramArrayOfChar[i5] - '0') * 10 + (paramArrayOfChar[(i5 + 1)] - '0'))));

        i5 += 2;
        i6++;
      }

      paramArrayOfByte[(paramInt1 + i6++)] = 102;
    }
    else
    {
      paramArrayOfByte[paramInt1] = ((byte)(192 + (i3 + 1)));

      if (i2 != 0)
      {
        paramArrayOfByte[(paramInt1 + i6)] = ((byte)(paramArrayOfChar[(i5++)] - '0' + 1));
        i6++;
      }

      while (i5 < j)
      {
        paramArrayOfByte[(paramInt1 + i6)] = ((byte)((paramArrayOfChar[i5] - '0') * 10 + (paramArrayOfChar[(i5 + 1)] - '0') + 1));

        i5 += 2;
        i6++;
      }
    }

    return i6;
  }
}