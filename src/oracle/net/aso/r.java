package oracle.net.aso;

public final class r
{
  public static void a(char[] paramArrayOfChar, int paramInt1, byte[] paramArrayOfByte, int paramInt2)
  {
    int i = paramInt2 - 1;
    int j = 0;
    int k = paramInt1 < paramInt2 / 2 ? paramInt1 : paramInt2 / 2;
    paramInt1 -= k;
    paramInt2 -= 2 * k;
    while (k-- > 0)
    {
      paramArrayOfChar[j] = ((char)((0xFF & (char)paramArrayOfByte[i]) + ((0xFF & (char)paramArrayOfByte[(i - 1)]) << '\b')));
      j++;
      i -= 2;
    }
    if ((paramInt1 > 0) && (paramInt2 % 2 == 1))
    {
      paramArrayOfChar[j] = ((char)(0xFF & (char)paramArrayOfByte[i]));
      j++;
      paramInt1--;
    }
    while (paramInt1-- > 0)
      paramArrayOfChar[(j++)] = '\000';
  }

  public static void a(byte[] paramArrayOfByte, int paramInt1, char[] paramArrayOfChar, int paramInt2)
  {
    int i = paramInt1 - 1;
    int j = 0;
    int k = paramInt2 < paramInt1 / 2 ? paramInt2 : paramInt1 / 2;
    paramInt2 -= k;
    paramInt1 -= 2 * k;
    while (k-- > 0)
    {
      paramArrayOfByte[(i--)] = ((byte)(0xFF & (byte)paramArrayOfChar[j]));
      paramArrayOfByte[(i--)] = ((byte)(paramArrayOfChar[j] >>> '\b'));
      j++;
    }
    if ((paramInt2 > 0) && (paramInt1 % 2 == 1))
      paramArrayOfByte[(i--)] = ((byte)(0xFF & (byte)paramArrayOfChar[j]));
  }

  public static void a(char[] paramArrayOfChar1, char[] paramArrayOfChar2, char[] paramArrayOfChar3, char[] paramArrayOfChar4, int paramInt)
  {
    char[] arrayOfChar1 = new char[67];
    boolean[] arrayOfBoolean = new boolean[64];
    char[][] arrayOfChar = new char[16][65];
    char[] arrayOfChar2 = new char[65];
    c(arrayOfChar1, paramArrayOfChar4, paramInt);
    int j = b(paramArrayOfChar3, paramInt);
    int k = j < 64 ? 3 : j < 16 ? 2 : j < 4 ? 1 : 4;
    c(arrayOfChar[0], 1, paramInt);
    a(arrayOfChar[1], paramArrayOfChar2, paramInt);
    arrayOfBoolean[0] = true;
    arrayOfBoolean[1] = true;
    int m;
    for (m = 2; m < 64; m++)
      arrayOfBoolean[m] = false;
    m = 0;
    int i = 0;
    int n = (char)(1 << j % 16);
    for (int i1 = j; i1 >= 0; i1--)
    {
      if (i != 0)
        b(arrayOfChar2, arrayOfChar2, paramArrayOfChar4, arrayOfChar1, paramInt);
      m <<= 1;
      if (arrayOfBoolean[m] == false)
      {
        b(arrayOfChar[m], arrayOfChar[(m / 2)], paramArrayOfChar4, arrayOfChar1, paramInt);
        arrayOfBoolean[m] = true;
      }
      if ((paramArrayOfChar3[(i1 / 16)] & n) > 0)
        m += 1;
      n = n == 1 ? 32768 : (char)(n >>> 1 & 0x7FFF);
      if (arrayOfBoolean[m] == false)
      {
        a(arrayOfChar[m], arrayOfChar[(m - 1)], paramArrayOfChar2, paramArrayOfChar4, arrayOfChar1, paramInt);
        arrayOfBoolean[m] = true;
      }
      if ((i1 == 0) || (m >= 1 << k - 1))
      {
        if (i != 0)
          a(arrayOfChar2, arrayOfChar2, arrayOfChar[m], paramArrayOfChar4, arrayOfChar1, paramInt);
        else
          a(arrayOfChar2, arrayOfChar[m], paramInt);
        m = 0;
        i = 1;
      }
    }
    a(paramArrayOfChar1, arrayOfChar2, paramInt);
  }

  private static int a(char[] paramArrayOfChar, int paramInt)
  {
    if ((paramArrayOfChar[(paramInt - 1)] & 0x8000) > 0)
      return -1;
    for (int i = paramInt - 1; i >= 0; i--)
      if (paramArrayOfChar[i] > 0)
        return 1;
    return 0;
  }

  private static int a(int paramInt)
  {
    paramInt -= 1;
    int i = 0;
    while (paramInt > 0)
    {
      i++;
      paramInt >>>= 1;
    }
    return i;
  }

  private static int b(char[] paramArrayOfChar, int paramInt)
  {
    int i = (char)((paramArrayOfChar[(paramInt - 1)] & 0x8000) > 0 ? -1 : 0);
    int j;
    for (j = paramInt - 1; (j >= 0) && (paramArrayOfChar[j] == i); j--);
    if (j == -1)
      return 1;
    int m = 16;
    int k = 32768;
    m--;
    return 16 * j + m;
  }

  private static int b(int paramInt)
  {
    return 16 * ((paramInt + 1 + 15) / 16);
  }

  private static void a(char[] paramArrayOfChar, int paramInt1, int paramInt2)
  {
    for (int i = 0; i < paramInt2; i++)
      paramArrayOfChar[i] = '\000';
    paramArrayOfChar[(paramInt1 / 16)] = ((char)(1 << paramInt1 % 16));
  }

  private static void c(char[] paramArrayOfChar, int paramInt)
  {
    int i = 1;
    int j;
    for (j = 0; (j < paramInt - 1) && (i != 0); j++)
    {
      paramArrayOfChar[j] = ((char)(paramArrayOfChar[j] + '\001'));
      if (paramArrayOfChar[j] > 0)
        i = 0;
    }
    if (i != 0)
      paramArrayOfChar[j] = ((char)(paramArrayOfChar[j] + '\001'));
  }

  private static void d(char[] paramArrayOfChar, int paramInt)
  {
    int i = 0;
    while (i < paramInt)
      paramArrayOfChar[(i++)] = '\000';
  }

  private static void a(char[] paramArrayOfChar1, char[] paramArrayOfChar2, int paramInt)
  {
    for (int i = 0; i < paramInt; i++)
      paramArrayOfChar1[i] = paramArrayOfChar2[i];
  }

  private static int b(char[] paramArrayOfChar, int paramInt1, int paramInt2)
  {
    for (int i = paramInt2 - 1; i >= 0; i--)
      if (paramArrayOfChar[(i + paramInt1)] > 0)
        return i + 1;
    return 0;
  }

  private static char a(char[] paramArrayOfChar1, int paramInt1, char paramChar, char[] paramArrayOfChar2, int paramInt2, int paramInt3)
  {
    int i = 0;
    if (paramChar <= 0)
      return '\000';
    int j = paramChar;
    for (int k = 0; k < paramInt3; k++)
    {
      i = i += j * paramArrayOfChar2[(k + paramInt2)] + paramArrayOfChar1[(k + paramInt1)];
      paramArrayOfChar1[(k + paramInt1)] = ((char)i);
      i >>>= 16;
    }
    return (char)i;
  }

  private static void a(char[] paramArrayOfChar1, char[] paramArrayOfChar2, char[] paramArrayOfChar3, int paramInt1, int paramInt2)
  {
    d(paramArrayOfChar1, 2 * paramInt2);
    int i = b(paramArrayOfChar3, 0 + paramInt1, paramInt2);
    for (int j = 0; j < paramInt2; j++)
      paramArrayOfChar1[(i + j)] = a(paramArrayOfChar1, j, paramArrayOfChar2[j], paramArrayOfChar3, 0 + paramInt1, i);
  }

  private static void a(char[] paramArrayOfChar1, char[] paramArrayOfChar2, char[] paramArrayOfChar3, int paramInt)
  {
    int i = 0;
    for (int j = 0; j < paramInt; j++)
    {
      i = i += paramArrayOfChar2[j] + paramArrayOfChar3[j];
      paramArrayOfChar1[j] = ((char)i);
      i >>>= 16;
    }
  }

  private static void b(char[] paramArrayOfChar1, char[] paramArrayOfChar2, char[] paramArrayOfChar3, int paramInt1, int paramInt2)
  {
    int i = 1;
    for (int j = 0; j < paramInt2; j++)
    {
      i = i += paramArrayOfChar2[j] + ((paramArrayOfChar3[(j + paramInt1)] ^ 0xFFFFFFFF) & 0xFFFF);
      paramArrayOfChar1[j] = ((char)i);
      i >>>= 16;
    }
  }

  private static void b(char[] paramArrayOfChar1, char[] paramArrayOfChar2, int paramInt)
  {
    int i = 0;
    d(paramArrayOfChar1, 2 * paramInt);
    int j;
    if ((j = b(paramArrayOfChar2, 0, paramInt)) <= 0)
      return;
    int k;
    for (k = 0; k < j - 1; k++)
      paramArrayOfChar1[(j + k)] = a(paramArrayOfChar1, 2 * k + 1, paramArrayOfChar2[k], paramArrayOfChar2, k + 1, j - k - 1);
    a(paramArrayOfChar1, paramArrayOfChar1, paramArrayOfChar1, 2 * paramInt);
    for (k = 0; k < j; k++)
    {
      i = i += paramArrayOfChar2[k] * paramArrayOfChar2[k] + paramArrayOfChar1[(2 * k)];
      paramArrayOfChar1[(2 * k)] = ((char)i);
      i = (i >>>= 16) + paramArrayOfChar1[(2 * k + 1)];
      paramArrayOfChar1[(2 * k + 1)] = ((char)i);
      i >>>= 16;
    }
    paramArrayOfChar1[(2 * k)] = ((char)i);
  }

  private static void e(char[] paramArrayOfChar, int paramInt)
  {
    int i = 1;
    int j;
    for (j = 0; (j < paramInt - 1) && (i != 0); j++)
    {
      paramArrayOfChar[j] = ((char)(paramArrayOfChar[j] - '\001'));
      if (paramArrayOfChar[j] != 65535)
        i = 0;
    }
    if (i != 0)
      paramArrayOfChar[j] = ((char)(paramArrayOfChar[j] - '\001'));
  }

  private static void b(char[] paramArrayOfChar1, char[] paramArrayOfChar2, char[] paramArrayOfChar3, char[] paramArrayOfChar4, int paramInt)
  {
    char[] arrayOfChar;
    b(arrayOfChar = new char[130], paramArrayOfChar2, paramInt);
    c(paramArrayOfChar1, arrayOfChar, paramArrayOfChar3, paramArrayOfChar4, paramInt);
  }

  private static void c(char[] paramArrayOfChar1, char[] paramArrayOfChar2, int paramInt)
  {
    char[] arrayOfChar1 = new char[134];
    char[] arrayOfChar2 = new char[136];
    char[] arrayOfChar3 = new char[68];
    int i = b(paramArrayOfChar2, paramInt);
    int j;
    int k = (j = b(2 * i)) / 16;
    int m = (i - 2) / 16;
    a(paramArrayOfChar1, j - i, paramInt + 2);
    c(paramArrayOfChar1, paramInt + 2);
    d(arrayOfChar3, paramInt + 3);
    a(arrayOfChar3, paramArrayOfChar2, paramInt);
    for (int n = 1 + a(j - i + 1); n > 0; n--)
    {
      b(arrayOfChar1, paramArrayOfChar1, paramInt + 2);
      a(arrayOfChar2, arrayOfChar3, arrayOfChar1, m, paramInt + 3);
      a(paramArrayOfChar1, paramArrayOfChar1, paramArrayOfChar1, paramInt + 2);
      b(paramArrayOfChar1, paramArrayOfChar1, arrayOfChar2, k - m, paramInt + 2);
    }
    c(paramArrayOfChar1, paramInt + 2);
    while (true)
    {
      b(arrayOfChar1, paramArrayOfChar1, arrayOfChar3, paramInt + 2);
      e(arrayOfChar1, 2 * (paramInt + 2));
      if (b(arrayOfChar1, 2 * (paramInt + 2)) <= j)
        return;
      e(paramArrayOfChar1, paramInt + 2);
    }
  }

  private static void c(char[] paramArrayOfChar, int paramInt1, int paramInt2)
  {
    char i = (char)((paramInt1 & 0x8000) > 0 ? -1 : 0);
    paramArrayOfChar[0] = ((char)paramInt1);
    for (int j = 1; j < paramInt2; j++)
      paramArrayOfChar[j] = i;
  }

  private static void a(char[] paramArrayOfChar1, char[] paramArrayOfChar2, char[] paramArrayOfChar3, char[] paramArrayOfChar4, char[] paramArrayOfChar5, int paramInt)
  {
    char[] arrayOfChar;
    b(arrayOfChar = new char[130], paramArrayOfChar2, paramArrayOfChar3, paramInt);
    c(paramArrayOfChar1, arrayOfChar, paramArrayOfChar4, paramArrayOfChar5, paramInt);
  }

  private static void b(char[] paramArrayOfChar1, char[] paramArrayOfChar2, char[] paramArrayOfChar3, int paramInt)
  {
    d(paramArrayOfChar1, 2 * paramInt);
    int i = b(paramArrayOfChar3, 0, paramInt);
    for (int j = 0; j < paramInt; j++)
      paramArrayOfChar1[(i + j)] = a(paramArrayOfChar1, j, paramArrayOfChar2[j], paramArrayOfChar3, 0, i);
  }

  private static void c(char[] paramArrayOfChar1, char[] paramArrayOfChar2, char[] paramArrayOfChar3, char[] paramArrayOfChar4, int paramInt)
  {
    char[] arrayOfChar;
    b(arrayOfChar = new char[65], paramArrayOfChar1, paramArrayOfChar2, paramArrayOfChar3, paramArrayOfChar4, paramInt);
  }

  private static void a(char[] paramArrayOfChar1, char[] paramArrayOfChar2, char[] paramArrayOfChar3, int paramInt1, int paramInt2, int paramInt3)
  {
    d(paramArrayOfChar1, 2 * paramInt3);
    int j = b(paramArrayOfChar3, paramInt1, paramInt3);
    int k;
    for (int m = k = paramInt2 >= paramInt3 - 1 ? paramInt2 - (paramInt3 - 1) : 0; m < paramInt3; m++)
    {
      int i = paramInt2 >= m ? paramInt2 - m : 0;
      paramArrayOfChar1[(j + m)] = a(paramArrayOfChar1, m + i, paramArrayOfChar2[m], paramArrayOfChar3, i + paramInt1, j >= i ? j - i : 0);
    }
  }

  private static void b(char[] paramArrayOfChar1, char[] paramArrayOfChar2, char[] paramArrayOfChar3, char[] paramArrayOfChar4, char[] paramArrayOfChar5, int paramInt)
  {
    char[] arrayOfChar1 = new char[134];
    char[] arrayOfChar2 = new char[134];
    char[] arrayOfChar3 = new char[132];
    int n = b(paramArrayOfChar4, paramInt);
    int k;
    int m = (k = b(2 * n)) / 16;
    int i1 = (n - 2) / 16;
    int i;
    if ((i = m - i1 - 3) < 0)
      i = 0;
    d(arrayOfChar3, 2 * paramInt + 2);
    a(arrayOfChar3, paramArrayOfChar3, 2 * paramInt);
    a(arrayOfChar2, paramArrayOfChar5, arrayOfChar3, i1, i, paramInt + 2);
    for (int j = 0; j < paramInt; j++)
      paramArrayOfChar1[j] = arrayOfChar2[(j + (m - i1))];
    c(arrayOfChar1, paramArrayOfChar1, paramArrayOfChar4, paramInt);
    b(paramArrayOfChar2, paramArrayOfChar3, arrayOfChar1, 0, paramInt);
    while (d(paramArrayOfChar2, paramArrayOfChar4, paramInt) >= 0)
    {
      b(paramArrayOfChar2, paramArrayOfChar2, paramArrayOfChar4, 0, paramInt);
      c(paramArrayOfChar1, paramInt);
    }
  }

  private static int d(char[] paramArrayOfChar1, char[] paramArrayOfChar2, int paramInt)
  {
    int i = a(paramArrayOfChar1, paramInt);
    int j = a(paramArrayOfChar2, paramInt);
    if (i > j)
      return 1;
    if (i < j)
      return -1;
    int k;
    for (k = paramInt - 1; (k >= 0) && (paramArrayOfChar1[k] == paramArrayOfChar2[k]); k--);
    if (k == -1)
      return 0;
    if (paramArrayOfChar1[k] > paramArrayOfChar2[k])
      return 1;
    return -1;
  }

  private static void c(char[] paramArrayOfChar1, char[] paramArrayOfChar2, char[] paramArrayOfChar3, int paramInt)
  {
    d(paramArrayOfChar1, paramInt);
    int i = b(paramArrayOfChar3, 0, paramInt);
    for (int j = 0; j < paramInt; j++)
      if (i < paramInt - j)
        paramArrayOfChar1[(i + j)] = a(paramArrayOfChar1, j, paramArrayOfChar2[j], paramArrayOfChar3, 0, i);
      else
        a(paramArrayOfChar1, j, paramArrayOfChar2[j], paramArrayOfChar3, 0, paramInt - j);
  }
}