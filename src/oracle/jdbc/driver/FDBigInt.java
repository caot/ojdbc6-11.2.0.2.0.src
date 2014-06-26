package oracle.jdbc.driver;

class FDBigInt
{
  int nWords;
  int[] data;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  FDBigInt(int paramInt)
  {
    this.nWords = 1;
    this.data = new int[1];
    this.data[0] = paramInt;
  }

  FDBigInt(long paramLong)
  {
    this.data = new int[2];
    this.data[0] = ((int)paramLong);
    this.data[1] = ((int)(paramLong >>> 32));
    this.nWords = (this.data[1] == 0 ? 1 : 2);
  }

  FDBigInt(FDBigInt paramFDBigInt)
  {
    this.data = new int[this.nWords = paramFDBigInt.nWords];

    System.arraycopy(paramFDBigInt.data, 0, this.data, 0, this.nWords);
  }

  FDBigInt(int[] paramArrayOfInt, int paramInt)
  {
    this.data = paramArrayOfInt;
    this.nWords = paramInt;
  }

  void lshiftMe(int paramInt)
    throws IllegalArgumentException
  {
    if (paramInt <= 0)
    {
      if (paramInt == 0) {
        return;
      }
      throw new IllegalArgumentException("negative shift count");
    }

    int i = paramInt >> 5;
    int j = paramInt & 0x1F;
    int k = 32 - j;
    int[] arrayOfInt1 = this.data;
    int[] arrayOfInt2 = this.data;

    if (this.nWords + i + 1 > arrayOfInt1.length)
    {
      arrayOfInt1 = new int[this.nWords + i + 1];
    }

    int m = this.nWords + i;
    int n = this.nWords - 1;

    if (j == 0)
    {
      System.arraycopy(arrayOfInt2, 0, arrayOfInt1, i, this.nWords);

      m = i - 1;
    }
    else
    {
      arrayOfInt1[(m--)] = (arrayOfInt2[n] >>> k);

      while (n >= 1)
      {
        arrayOfInt1[(m--)] = (arrayOfInt2[n] << j | arrayOfInt2[(--n)] >>> k);
      }

      arrayOfInt1[(m--)] = (arrayOfInt2[n] << j);
    }

    while (m >= 0)
    {
      arrayOfInt1[(m--)] = 0;
    }

    this.data = arrayOfInt1;
    this.nWords += i + 1;

    while ((this.nWords > 1) && (this.data[(this.nWords - 1)] == 0))
      this.nWords -= 1;
  }

  int normalizeMe()
    throws IllegalArgumentException
  {
    int j = 0;
    int k = 0;
    int m = 0;
    int i;
    for (i = this.nWords - 1; (i >= 0) && ((m = this.data[i]) == 0); i--)
    {
      j++;
    }

    if (i < 0)
    {
      throw new IllegalArgumentException("zero value");
    }

    this.nWords -= j;

    if ((m & 0xF0000000) != 0)
    {
      for (k = 32; (m & 0xF0000000) != 0; k--) {
        m >>>= 1;
      }
    }

    while (m <= 1048575)
    {
      m <<= 8;
      k += 8;
    }

    while (m <= 134217727)
    {
      m <<= 1;
      k++;
    }

    if (k != 0) {
      lshiftMe(k);
    }
    return k;
  }

  FDBigInt mult(int paramInt)
  {
    long l1 = paramInt;

    int[] arrayOfInt = new int[l1 * (this.data[(this.nWords - 1)] & 0xFFFFFFFF) > 268435455L ? this.nWords + 1 : this.nWords];

    long l2 = 0L;

    for (int i = 0; i < this.nWords; i++)
    {
      l2 += l1 * (this.data[i] & 0xFFFFFFFF);
      arrayOfInt[i] = ((int)l2);
      l2 >>>= 32;
    }

    if (l2 == 0L)
    {
      return new FDBigInt(arrayOfInt, this.nWords);
    }

    arrayOfInt[this.nWords] = ((int)l2);

    return new FDBigInt(arrayOfInt, this.nWords + 1);
  }

  FDBigInt mult(FDBigInt paramFDBigInt)
  {
    int[] arrayOfInt = new int[this.nWords + paramFDBigInt.nWords];
    int i;
    for (i = 0; i < this.nWords; i++)
    {
      long l1 = this.data[i] & 0xFFFFFFFF;
      long l2 = 0L;
      int j;
      for (j = 0; j < paramFDBigInt.nWords; j++)
      {
        l2 += (arrayOfInt[(i + j)] & 0xFFFFFFFF) + l1 * (paramFDBigInt.data[j] & 0xFFFFFFFF);

        arrayOfInt[(i + j)] = ((int)l2);
        l2 >>>= 32;
      }

      arrayOfInt[(i + j)] = ((int)l2);
    }

    for (i = arrayOfInt.length - 1; (i > 0) && 
      (arrayOfInt[i] == 0); i--);
    return new FDBigInt(arrayOfInt, i + 1);
  }

  FDBigInt add(FDBigInt paramFDBigInt)
  {
    long l = 0L;
    int[] arrayOfInt1;
    int j;
    int[] arrayOfInt2;
    int k;
    if (this.nWords >= paramFDBigInt.nWords)
    {
      arrayOfInt1 = this.data;
      j = this.nWords;
      arrayOfInt2 = paramFDBigInt.data;
      k = paramFDBigInt.nWords;
    }
    else
    {
      arrayOfInt1 = paramFDBigInt.data;
      j = paramFDBigInt.nWords;
      arrayOfInt2 = this.data;
      k = this.nWords;
    }

    int[] arrayOfInt3 = new int[j];
    int i;
    for (i = 0; i < j; i++)
    {
      l += (arrayOfInt1[i] & 0xFFFFFFFF);

      if (i < k)
      {
        l += (arrayOfInt2[i] & 0xFFFFFFFF);
      }

      arrayOfInt3[i] = ((int)l);
      l >>= 32;
    }

    if (l != 0L)
    {
      int[] arrayOfInt4 = new int[arrayOfInt3.length + 1];

      System.arraycopy(arrayOfInt3, 0, arrayOfInt4, 0, arrayOfInt3.length);

      arrayOfInt4[(i++)] = ((int)l);

      return new FDBigInt(arrayOfInt4, i);
    }

    return new FDBigInt(arrayOfInt3, i);
  }

  int cmp(FDBigInt paramFDBigInt)
  {
    int i;
    int j;
    if (this.nWords > paramFDBigInt.nWords)
    {
      j = paramFDBigInt.nWords - 1;

      for (i = this.nWords - 1; i > j; i--)
        if (this.data[i] != 0)
          return 1;
    }
    else if (this.nWords < paramFDBigInt.nWords)
    {
      j = this.nWords - 1;

      for (i = paramFDBigInt.nWords - 1; i > j; i--)
        if (paramFDBigInt.data[i] != 0)
          return -1;
    }
    else
    {
      i = this.nWords - 1;
    }

    while ((i > 0) && 
      (this.data[i] == paramFDBigInt.data[i])) {
      i--;
    }

    j = this.data[i];
    int k = paramFDBigInt.data[i];

    if (j < 0)
    {
      if (k < 0)
      {
        return j - k;
      }

      return 1;
    }

    if (k < 0)
    {
      return -1;
    }

    return j - k;
  }

  int quoRemIteration(FDBigInt paramFDBigInt)
    throws IllegalArgumentException
  {
    if (this.nWords != paramFDBigInt.nWords)
    {
      throw new IllegalArgumentException("disparate values");
    }

    int i = this.nWords - 1;
    int k;
    long l1 = (this.data[i] & 0xFFFFFFFF) / paramFDBigInt.data[i];
    long l2 = 0L;
    long l3 = 0L;

    for (int j = 0; j <= i; j++)
    {
      l2 += (this.data[j] & 0xFFFFFFFF) - l1 * (paramFDBigInt.data[j] & 0xFFFFFFFF);

      this.data[j] = ((int)l2);
      l2 >>= 32;
    }

    if (l2 != 0L)
    {
      l3 = 0L;

      while (l3 == 0L)
      {
        l3 = 0L;

        for (k = 0; k <= i; k++)
        {
          l3 += (this.data[k] & 0xFFFFFFFF) + (paramFDBigInt.data[k] & 0xFFFFFFFF);

          this.data[k] = ((int)l3);
          l3 >>= 32;
        }

        if ((l3 != 0L) && (l3 != 1L)) {
          throw new RuntimeException("Assertion botch: " + l3 + " carry out of division correction");
        }
        l1 -= 1L;
      }

    }

    for (k = 0; k <= i; k++)
    {
      l3 += 10L * (this.data[k] & 0xFFFFFFFF);
      this.data[k] = ((int)l3);
      l3 >>= 32;
    }

    if (l3 != 0L) {
      throw new RuntimeException("Assertion botch: carry out of *10");
    }
    return (int)l1;
  }
}