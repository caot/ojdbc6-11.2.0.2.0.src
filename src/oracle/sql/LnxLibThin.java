package oracle.sql;

import java.sql.SQLException;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import oracle.core.lmx.CoreException;

class LnxLibThin
  implements LnxLib
{
  private static final byte[] lnxqone = { -63, 2 };

  private static final byte[] lnxqtwo = { -63, 3 };
  private static final int LNXQACOS = 0;
  private static final int LNXQASIN = 1;
  private static final int LNXQATAN = 2;
  private static final int LNXQCOS = 3;
  private static final int LNXQSIN = 4;
  private static final int LNXQTAN = 5;
  private static final int LNXQCSH = 6;
  private static final int LNXQSNH = 7;
  private static final int LNXQTNH = 8;
  private static final int LNXQEXP = 9;
  private static final int LNXM_NUM = 22;
  private static final int LNXDIGS = 20;
  private static final int LNXSGNBT = 128;
  private static final int LNXEXPMX = 127;
  private static final int LNXEXPMN = 0;
  private static final int LNXEXPBS = 64;
  private static final int LNXBASE = 100;
  private static final int LNXMXFMT = 64;
  private static final int LNXMXOUT = 40;
  private static final int LNXDIV_LNXBASE_SQUARED = 10000;
  private static final int MINUB1MAXVAL = 255;
  private static final double ORANUM_FBASE = 100.0D;
  private static final int LNXQNOSGN = 127;
  private static final char LNXNFT_COMMA = ',';
  private static final int LNXBYTEMASK = 255;
  private static final int LNXSHORTMASK = 65535;
  private static byte[] LnxqFirstDigit = { 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 };

  private static byte[] LnxqNegate = { 0, 101, 100, 99, 98, 97, 96, 95, 94, 93, 92, 91, 90, 89, 88, 87, 86, 85, 84, 83, 82, 81, 80, 79, 78, 77, 76, 75, 74, 73, 72, 71, 70, 69, 68, 67, 66, 65, 64, 63, 62, 61, 60, 59, 58, 57, 56, 55, 54, 53, 52, 51, 50, 49, 48, 47, 46, 45, 44, 43, 42, 41, 40, 39, 38, 37, 36, 35, 34, 33, 32, 31, 30, 29, 28, 27, 26, 25, 24, 23, 22, 21, 20, 19, 18, 17, 16, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1 };

  private static byte[] LnxqTruncate_P = { 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 31, 31, 31, 31, 31, 31, 31, 31, 31, 31, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 51, 51, 51, 51, 51, 51, 51, 51, 51, 51, 61, 61, 61, 61, 61, 61, 61, 61, 61, 61, 71, 71, 71, 71, 71, 71, 71, 71, 71, 71, 81, 81, 81, 81, 81, 81, 81, 81, 81, 81, 91, 91, 91, 91, 91, 91, 91, 91, 91, 91 };

  private static byte[] LnxqTruncate_N = { 0, 0, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 31, 31, 31, 31, 31, 31, 31, 31, 31, 31, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 51, 51, 51, 51, 51, 51, 51, 51, 51, 51, 61, 61, 61, 61, 61, 61, 61, 61, 61, 61, 71, 71, 71, 71, 71, 71, 71, 71, 71, 71, 81, 81, 81, 81, 81, 81, 81, 81, 81, 81, 91, 91, 91, 91, 91, 91, 91, 91, 91, 91, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101 };

  private static byte[] LnxqRound_P = { 0, 1, 1, 1, 1, 1, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 31, 31, 31, 31, 31, 31, 31, 31, 31, 31, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 51, 51, 51, 51, 51, 51, 51, 51, 51, 51, 61, 61, 61, 61, 61, 61, 61, 61, 61, 61, 71, 71, 71, 71, 71, 71, 71, 71, 71, 71, 81, 81, 81, 81, 81, 81, 81, 81, 81, 81, 91, 91, 91, 91, 91, 91, 91, 91, 91, 91, 101, 101, 101, 101, 101 };

  private static byte[] LnxqRound_N = { 0, 0, 1, 1, 1, 1, 1, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 31, 31, 31, 31, 31, 31, 31, 31, 31, 31, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 51, 51, 51, 51, 51, 51, 51, 51, 51, 51, 61, 61, 61, 61, 61, 61, 61, 61, 61, 61, 71, 71, 71, 71, 71, 71, 71, 71, 71, 71, 81, 81, 81, 81, 81, 81, 81, 81, 81, 81, 91, 91, 91, 91, 91, 91, 91, 91, 91, 91, 101, 101, 101, 101, 101 };

  private static byte[][] LnxqComponents_P = { { 0, 0 }, { 0, 0 }, { 0, 1 }, { 0, 2 }, { 0, 3 }, { 0, 4 }, { 0, 5 }, { 0, 6 }, { 0, 7 }, { 0, 8 }, { 0, 9 }, { 1, 0 }, { 1, 1 }, { 1, 2 }, { 1, 3 }, { 1, 4 }, { 1, 5 }, { 1, 6 }, { 1, 7 }, { 1, 8 }, { 1, 9 }, { 2, 0 }, { 2, 1 }, { 2, 2 }, { 2, 3 }, { 2, 4 }, { 2, 5 }, { 2, 6 }, { 2, 7 }, { 2, 8 }, { 2, 9 }, { 3, 0 }, { 3, 1 }, { 3, 2 }, { 3, 3 }, { 3, 4 }, { 3, 5 }, { 3, 6 }, { 3, 7 }, { 3, 8 }, { 3, 9 }, { 4, 0 }, { 4, 1 }, { 4, 2 }, { 4, 3 }, { 4, 4 }, { 4, 5 }, { 4, 6 }, { 4, 7 }, { 4, 8 }, { 4, 9 }, { 5, 0 }, { 5, 1 }, { 5, 2 }, { 5, 3 }, { 5, 4 }, { 5, 5 }, { 5, 6 }, { 5, 7 }, { 5, 8 }, { 5, 9 }, { 6, 0 }, { 6, 1 }, { 6, 2 }, { 6, 3 }, { 6, 4 }, { 6, 5 }, { 6, 6 }, { 6, 7 }, { 6, 8 }, { 6, 9 }, { 7, 0 }, { 7, 1 }, { 7, 2 }, { 7, 3 }, { 7, 4 }, { 7, 5 }, { 7, 6 }, { 7, 7 }, { 7, 8 }, { 7, 9 }, { 8, 0 }, { 8, 1 }, { 8, 2 }, { 8, 3 }, { 8, 4 }, { 8, 5 }, { 8, 6 }, { 8, 7 }, { 8, 8 }, { 8, 9 }, { 9, 0 }, { 9, 1 }, { 9, 2 }, { 9, 3 }, { 9, 4 }, { 9, 5 }, { 9, 6 }, { 9, 7 }, { 9, 8 }, { 9, 9 } };

  private static byte[][] LnxqComponents_N = { { 0, 0 }, { 0, 0 }, { 9, 9 }, { 9, 8 }, { 9, 7 }, { 9, 6 }, { 9, 5 }, { 9, 4 }, { 9, 3 }, { 9, 2 }, { 9, 1 }, { 9, 0 }, { 8, 9 }, { 8, 8 }, { 8, 7 }, { 8, 6 }, { 8, 5 }, { 8, 4 }, { 8, 3 }, { 8, 2 }, { 8, 1 }, { 8, 0 }, { 7, 9 }, { 7, 8 }, { 7, 7 }, { 7, 6 }, { 7, 5 }, { 7, 4 }, { 7, 3 }, { 7, 2 }, { 7, 1 }, { 7, 0 }, { 6, 9 }, { 6, 8 }, { 6, 7 }, { 6, 6 }, { 6, 5 }, { 6, 4 }, { 6, 3 }, { 6, 2 }, { 6, 1 }, { 6, 0 }, { 5, 9 }, { 5, 8 }, { 5, 7 }, { 5, 6 }, { 5, 5 }, { 5, 4 }, { 5, 3 }, { 5, 2 }, { 5, 1 }, { 5, 0 }, { 4, 9 }, { 4, 8 }, { 4, 7 }, { 4, 6 }, { 4, 5 }, { 4, 4 }, { 4, 3 }, { 4, 2 }, { 4, 1 }, { 4, 0 }, { 3, 9 }, { 3, 8 }, { 3, 7 }, { 3, 6 }, { 3, 5 }, { 3, 4 }, { 3, 3 }, { 3, 2 }, { 3, 1 }, { 3, 0 }, { 2, 9 }, { 2, 8 }, { 2, 7 }, { 2, 6 }, { 2, 5 }, { 2, 4 }, { 2, 3 }, { 2, 2 }, { 2, 1 }, { 2, 0 }, { 1, 9 }, { 1, 8 }, { 1, 7 }, { 1, 6 }, { 1, 5 }, { 1, 4 }, { 1, 3 }, { 1, 2 }, { 1, 1 }, { 1, 0 }, { 0, 9 }, { 0, 8 }, { 0, 7 }, { 0, 6 }, { 0, 5 }, { 0, 4 }, { 0, 3 }, { 0, 2 }, { 0, 1 }, { 0, 0 } };

  private static byte[][] LnxqAdd_PPP = { { 0, 0 }, { 0, 1 }, { 1, 0 }, { 2, 0 }, { 3, 0 }, { 4, 0 }, { 5, 0 }, { 6, 0 }, { 7, 0 }, { 8, 0 }, { 9, 0 }, { 10, 0 }, { 11, 0 }, { 12, 0 }, { 13, 0 }, { 14, 0 }, { 15, 0 }, { 16, 0 }, { 17, 0 }, { 18, 0 }, { 19, 0 }, { 20, 0 }, { 21, 0 }, { 22, 0 }, { 23, 0 }, { 24, 0 }, { 25, 0 }, { 26, 0 }, { 27, 0 }, { 28, 0 }, { 29, 0 }, { 30, 0 }, { 31, 0 }, { 32, 0 }, { 33, 0 }, { 34, 0 }, { 35, 0 }, { 36, 0 }, { 37, 0 }, { 38, 0 }, { 39, 0 }, { 40, 0 }, { 41, 0 }, { 42, 0 }, { 43, 0 }, { 44, 0 }, { 45, 0 }, { 46, 0 }, { 47, 0 }, { 48, 0 }, { 49, 0 }, { 50, 0 }, { 51, 0 }, { 52, 0 }, { 53, 0 }, { 54, 0 }, { 55, 0 }, { 56, 0 }, { 57, 0 }, { 58, 0 }, { 59, 0 }, { 60, 0 }, { 61, 0 }, { 62, 0 }, { 63, 0 }, { 64, 0 }, { 65, 0 }, { 66, 0 }, { 67, 0 }, { 68, 0 }, { 69, 0 }, { 70, 0 }, { 71, 0 }, { 72, 0 }, { 73, 0 }, { 74, 0 }, { 75, 0 }, { 76, 0 }, { 77, 0 }, { 78, 0 }, { 79, 0 }, { 80, 0 }, { 81, 0 }, { 82, 0 }, { 83, 0 }, { 84, 0 }, { 85, 0 }, { 86, 0 }, { 87, 0 }, { 88, 0 }, { 89, 0 }, { 90, 0 }, { 91, 0 }, { 92, 0 }, { 93, 0 }, { 94, 0 }, { 95, 0 }, { 96, 0 }, { 97, 0 }, { 98, 0 }, { 99, 0 }, { 100, 0 }, { 1, 1 }, { 2, 1 }, { 3, 1 }, { 4, 1 }, { 5, 1 }, { 6, 1 }, { 7, 1 }, { 8, 1 }, { 9, 1 }, { 10, 1 }, { 11, 1 }, { 12, 1 }, { 13, 1 }, { 14, 1 }, { 15, 1 }, { 16, 1 }, { 17, 1 }, { 18, 1 }, { 19, 1 }, { 20, 1 }, { 21, 1 }, { 22, 1 }, { 23, 1 }, { 24, 1 }, { 25, 1 }, { 26, 1 }, { 27, 1 }, { 28, 1 }, { 29, 1 }, { 30, 1 }, { 31, 1 }, { 32, 1 }, { 33, 1 }, { 34, 1 }, { 35, 1 }, { 36, 1 }, { 37, 1 }, { 38, 1 }, { 39, 1 }, { 40, 1 }, { 41, 1 }, { 42, 1 }, { 43, 1 }, { 44, 1 }, { 45, 1 }, { 46, 1 }, { 47, 1 }, { 48, 1 }, { 49, 1 }, { 50, 1 }, { 51, 1 }, { 52, 1 }, { 53, 1 }, { 54, 1 }, { 55, 1 }, { 56, 1 }, { 57, 1 }, { 58, 1 }, { 59, 1 }, { 60, 1 }, { 61, 1 }, { 62, 1 }, { 63, 1 }, { 64, 1 }, { 65, 1 }, { 66, 1 }, { 67, 1 }, { 68, 1 }, { 69, 1 }, { 70, 1 }, { 71, 1 }, { 72, 1 }, { 73, 1 }, { 74, 1 }, { 75, 1 }, { 76, 1 }, { 77, 1 }, { 78, 1 }, { 79, 1 }, { 80, 1 }, { 81, 1 }, { 82, 1 }, { 83, 1 }, { 84, 1 }, { 85, 1 }, { 86, 1 }, { 87, 1 }, { 88, 1 }, { 89, 1 }, { 90, 1 }, { 91, 1 }, { 92, 1 }, { 93, 1 }, { 94, 1 }, { 95, 1 }, { 96, 1 }, { 97, 1 }, { 98, 1 }, { 99, 1 }, { 100, 1 } };

  private static byte[][] LnxqAdd_NNN = { { 0, 2 }, { 0, 1 }, { 0, 0 }, { 0, 0 }, { 0, 0 }, { 2, 1 }, { 3, 1 }, { 4, 1 }, { 5, 1 }, { 6, 1 }, { 7, 1 }, { 8, 1 }, { 9, 1 }, { 10, 1 }, { 11, 1 }, { 12, 1 }, { 13, 1 }, { 14, 1 }, { 15, 1 }, { 16, 1 }, { 17, 1 }, { 18, 1 }, { 19, 1 }, { 20, 1 }, { 21, 1 }, { 22, 1 }, { 23, 1 }, { 24, 1 }, { 25, 1 }, { 26, 1 }, { 27, 1 }, { 28, 1 }, { 29, 1 }, { 30, 1 }, { 31, 1 }, { 32, 1 }, { 33, 1 }, { 34, 1 }, { 35, 1 }, { 36, 1 }, { 37, 1 }, { 38, 1 }, { 39, 1 }, { 40, 1 }, { 41, 1 }, { 42, 1 }, { 43, 1 }, { 44, 1 }, { 45, 1 }, { 46, 1 }, { 47, 1 }, { 48, 1 }, { 49, 1 }, { 50, 1 }, { 51, 1 }, { 52, 1 }, { 53, 1 }, { 54, 1 }, { 55, 1 }, { 56, 1 }, { 57, 1 }, { 58, 1 }, { 59, 1 }, { 60, 1 }, { 61, 1 }, { 62, 1 }, { 63, 1 }, { 64, 1 }, { 65, 1 }, { 66, 1 }, { 67, 1 }, { 68, 1 }, { 69, 1 }, { 70, 1 }, { 71, 1 }, { 72, 1 }, { 73, 1 }, { 74, 1 }, { 75, 1 }, { 76, 1 }, { 77, 1 }, { 78, 1 }, { 79, 1 }, { 80, 1 }, { 81, 1 }, { 82, 1 }, { 83, 1 }, { 84, 1 }, { 85, 1 }, { 86, 1 }, { 87, 1 }, { 88, 1 }, { 89, 1 }, { 90, 1 }, { 91, 1 }, { 92, 1 }, { 93, 1 }, { 94, 1 }, { 95, 1 }, { 96, 1 }, { 97, 1 }, { 98, 1 }, { 99, 1 }, { 100, 1 }, { 101, 1 }, { 2, 2 }, { 3, 2 }, { 4, 2 }, { 5, 2 }, { 6, 2 }, { 7, 2 }, { 8, 2 }, { 9, 2 }, { 10, 2 }, { 11, 2 }, { 12, 2 }, { 13, 2 }, { 14, 2 }, { 15, 2 }, { 16, 2 }, { 17, 2 }, { 18, 2 }, { 19, 2 }, { 20, 2 }, { 21, 2 }, { 22, 2 }, { 23, 2 }, { 24, 2 }, { 25, 2 }, { 26, 2 }, { 27, 2 }, { 28, 2 }, { 29, 2 }, { 30, 2 }, { 31, 2 }, { 32, 2 }, { 33, 2 }, { 34, 2 }, { 35, 2 }, { 36, 2 }, { 37, 2 }, { 38, 2 }, { 39, 2 }, { 40, 2 }, { 41, 2 }, { 42, 2 }, { 43, 2 }, { 44, 2 }, { 45, 2 }, { 46, 2 }, { 47, 2 }, { 48, 2 }, { 49, 2 }, { 50, 2 }, { 51, 2 }, { 52, 2 }, { 53, 2 }, { 54, 2 }, { 55, 2 }, { 56, 2 }, { 57, 2 }, { 58, 2 }, { 59, 2 }, { 60, 2 }, { 61, 2 }, { 62, 2 }, { 63, 2 }, { 64, 2 }, { 65, 2 }, { 66, 2 }, { 67, 2 }, { 68, 2 }, { 69, 2 }, { 70, 2 }, { 71, 2 }, { 72, 2 }, { 73, 2 }, { 74, 2 }, { 75, 2 }, { 76, 2 }, { 77, 2 }, { 78, 2 }, { 79, 2 }, { 80, 2 }, { 81, 2 }, { 82, 2 }, { 83, 2 }, { 84, 2 }, { 85, 2 }, { 86, 2 }, { 87, 2 }, { 88, 2 }, { 89, 2 }, { 90, 2 }, { 91, 2 }, { 92, 2 }, { 93, 2 }, { 94, 2 }, { 95, 2 }, { 96, 2 }, { 97, 2 }, { 98, 2 }, { 99, 2 }, { 100, 2 }, { 101, 2 } };

  private static byte[][] LnxqAdd_PNP = { { 0, 2 }, { 0, 1 }, { 0, 0 }, { 0, 0 }, { 1, 1 }, { 2, 1 }, { 3, 1 }, { 4, 1 }, { 5, 1 }, { 6, 1 }, { 7, 1 }, { 8, 1 }, { 9, 1 }, { 10, 1 }, { 11, 1 }, { 12, 1 }, { 13, 1 }, { 14, 1 }, { 15, 1 }, { 16, 1 }, { 17, 1 }, { 18, 1 }, { 19, 1 }, { 20, 1 }, { 21, 1 }, { 22, 1 }, { 23, 1 }, { 24, 1 }, { 25, 1 }, { 26, 1 }, { 27, 1 }, { 28, 1 }, { 29, 1 }, { 30, 1 }, { 31, 1 }, { 32, 1 }, { 33, 1 }, { 34, 1 }, { 35, 1 }, { 36, 1 }, { 37, 1 }, { 38, 1 }, { 39, 1 }, { 40, 1 }, { 41, 1 }, { 42, 1 }, { 43, 1 }, { 44, 1 }, { 45, 1 }, { 46, 1 }, { 47, 1 }, { 48, 1 }, { 49, 1 }, { 50, 1 }, { 51, 1 }, { 52, 1 }, { 53, 1 }, { 54, 1 }, { 55, 1 }, { 56, 1 }, { 57, 1 }, { 58, 1 }, { 59, 1 }, { 60, 1 }, { 61, 1 }, { 62, 1 }, { 63, 1 }, { 64, 1 }, { 65, 1 }, { 66, 1 }, { 67, 1 }, { 68, 1 }, { 69, 1 }, { 70, 1 }, { 71, 1 }, { 72, 1 }, { 73, 1 }, { 74, 1 }, { 75, 1 }, { 76, 1 }, { 77, 1 }, { 78, 1 }, { 79, 1 }, { 80, 1 }, { 81, 1 }, { 82, 1 }, { 83, 1 }, { 84, 1 }, { 85, 1 }, { 86, 1 }, { 87, 1 }, { 88, 1 }, { 89, 1 }, { 90, 1 }, { 91, 1 }, { 92, 1 }, { 93, 1 }, { 94, 1 }, { 95, 1 }, { 96, 1 }, { 97, 1 }, { 98, 1 }, { 99, 1 }, { 100, 1 }, { 1, 2 }, { 2, 2 }, { 3, 2 }, { 4, 2 }, { 5, 2 }, { 6, 2 }, { 7, 2 }, { 8, 2 }, { 9, 2 }, { 10, 2 }, { 11, 2 }, { 12, 2 }, { 13, 2 }, { 14, 2 }, { 15, 2 }, { 16, 2 }, { 17, 2 }, { 18, 2 }, { 19, 2 }, { 20, 2 }, { 21, 2 }, { 22, 2 }, { 23, 2 }, { 24, 2 }, { 25, 2 }, { 26, 2 }, { 27, 2 }, { 28, 2 }, { 29, 2 }, { 30, 2 }, { 31, 2 }, { 32, 2 }, { 33, 2 }, { 34, 2 }, { 35, 2 }, { 36, 2 }, { 37, 2 }, { 38, 2 }, { 39, 2 }, { 40, 2 }, { 41, 2 }, { 42, 2 }, { 43, 2 }, { 44, 2 }, { 45, 2 }, { 46, 2 }, { 47, 2 }, { 48, 2 }, { 49, 2 }, { 50, 2 }, { 51, 2 }, { 52, 2 }, { 53, 2 }, { 54, 2 }, { 55, 2 }, { 56, 2 }, { 57, 2 }, { 58, 2 }, { 59, 2 }, { 60, 2 }, { 61, 2 }, { 62, 2 }, { 63, 2 }, { 64, 2 }, { 65, 2 }, { 66, 2 }, { 67, 2 }, { 68, 2 }, { 69, 2 }, { 70, 2 }, { 71, 2 }, { 72, 2 }, { 73, 2 }, { 74, 2 }, { 75, 2 }, { 76, 2 }, { 77, 2 }, { 78, 2 }, { 79, 2 }, { 80, 2 }, { 81, 2 }, { 82, 2 }, { 83, 2 }, { 84, 2 }, { 85, 2 }, { 86, 2 }, { 87, 2 }, { 88, 2 }, { 89, 2 }, { 90, 2 }, { 91, 2 }, { 92, 2 }, { 93, 2 }, { 94, 2 }, { 95, 2 }, { 96, 2 }, { 97, 2 }, { 98, 2 }, { 99, 2 }, { 100, 2 } };

  private static byte[][] LnxqAdd_PNN = { { 0, 0 }, { 0, 1 }, { 0, 0 }, { 2, 0 }, { 3, 0 }, { 4, 0 }, { 5, 0 }, { 6, 0 }, { 7, 0 }, { 8, 0 }, { 9, 0 }, { 10, 0 }, { 11, 0 }, { 12, 0 }, { 13, 0 }, { 14, 0 }, { 15, 0 }, { 16, 0 }, { 17, 0 }, { 18, 0 }, { 19, 0 }, { 20, 0 }, { 21, 0 }, { 22, 0 }, { 23, 0 }, { 24, 0 }, { 25, 0 }, { 26, 0 }, { 27, 0 }, { 28, 0 }, { 29, 0 }, { 30, 0 }, { 31, 0 }, { 32, 0 }, { 33, 0 }, { 34, 0 }, { 35, 0 }, { 36, 0 }, { 37, 0 }, { 38, 0 }, { 39, 0 }, { 40, 0 }, { 41, 0 }, { 42, 0 }, { 43, 0 }, { 44, 0 }, { 45, 0 }, { 46, 0 }, { 47, 0 }, { 48, 0 }, { 49, 0 }, { 50, 0 }, { 51, 0 }, { 52, 0 }, { 53, 0 }, { 54, 0 }, { 55, 0 }, { 56, 0 }, { 57, 0 }, { 58, 0 }, { 59, 0 }, { 60, 0 }, { 61, 0 }, { 62, 0 }, { 63, 0 }, { 64, 0 }, { 65, 0 }, { 66, 0 }, { 67, 0 }, { 68, 0 }, { 69, 0 }, { 70, 0 }, { 71, 0 }, { 72, 0 }, { 73, 0 }, { 74, 0 }, { 75, 0 }, { 76, 0 }, { 77, 0 }, { 78, 0 }, { 79, 0 }, { 80, 0 }, { 81, 0 }, { 82, 0 }, { 83, 0 }, { 84, 0 }, { 85, 0 }, { 86, 0 }, { 87, 0 }, { 88, 0 }, { 89, 0 }, { 90, 0 }, { 91, 0 }, { 92, 0 }, { 93, 0 }, { 94, 0 }, { 95, 0 }, { 96, 0 }, { 97, 0 }, { 98, 0 }, { 99, 0 }, { 100, 0 }, { 101, 0 }, { 2, 1 }, { 3, 1 }, { 4, 1 }, { 5, 1 }, { 6, 1 }, { 7, 1 }, { 8, 1 }, { 9, 1 }, { 10, 1 }, { 11, 1 }, { 12, 1 }, { 13, 1 }, { 14, 1 }, { 15, 1 }, { 16, 1 }, { 17, 1 }, { 18, 1 }, { 19, 1 }, { 20, 1 }, { 21, 1 }, { 22, 1 }, { 23, 1 }, { 24, 1 }, { 25, 1 }, { 26, 1 }, { 27, 1 }, { 28, 1 }, { 29, 1 }, { 30, 1 }, { 31, 1 }, { 32, 1 }, { 33, 1 }, { 34, 1 }, { 35, 1 }, { 36, 1 }, { 37, 1 }, { 38, 1 }, { 39, 1 }, { 40, 1 }, { 41, 1 }, { 42, 1 }, { 43, 1 }, { 44, 1 }, { 45, 1 }, { 46, 1 }, { 47, 1 }, { 48, 1 }, { 49, 1 }, { 50, 1 }, { 51, 1 }, { 52, 1 }, { 53, 1 }, { 54, 1 }, { 55, 1 }, { 56, 1 }, { 57, 1 }, { 58, 1 }, { 59, 1 }, { 60, 1 }, { 61, 1 }, { 62, 1 }, { 63, 1 }, { 64, 1 }, { 65, 1 }, { 66, 1 }, { 67, 1 }, { 68, 1 }, { 69, 1 }, { 70, 1 }, { 71, 1 }, { 72, 1 }, { 73, 1 }, { 74, 1 }, { 75, 1 }, { 76, 1 }, { 77, 1 }, { 78, 1 }, { 79, 1 }, { 80, 1 }, { 81, 1 }, { 82, 1 }, { 83, 1 }, { 84, 1 }, { 85, 1 }, { 86, 1 }, { 87, 1 }, { 88, 1 }, { 89, 1 }, { 90, 1 }, { 91, 1 }, { 92, 1 }, { 93, 1 }, { 94, 1 }, { 95, 1 }, { 96, 1 }, { 97, 1 }, { 98, 1 }, { 99, 1 }, { 100, 1 }, { 101, 1 } };

  private static byte[] LnxsubIdentity = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101 };

  private static byte[][] LnxqDigit_P = { { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 }, { 11, 12, 13, 14, 15, 16, 17, 18, 19, 20 }, { 21, 22, 23, 24, 25, 26, 27, 28, 29, 30 }, { 31, 32, 33, 34, 35, 36, 37, 38, 39, 40 }, { 41, 42, 43, 44, 45, 46, 47, 48, 49, 50 }, { 51, 52, 53, 54, 55, 56, 57, 58, 59, 60 }, { 61, 62, 63, 64, 65, 66, 67, 68, 69, 70 }, { 71, 72, 73, 74, 75, 76, 77, 78, 79, 80 }, { 81, 82, 83, 84, 85, 86, 87, 88, 89, 90 }, { 91, 92, 93, 94, 95, 96, 97, 98, 99, 100 } };

  private static byte[][] LnxqDigit_N = { { 101, 100, 99, 98, 97, 96, 95, 94, 93, 92 }, { 91, 90, 89, 88, 87, 86, 85, 84, 83, 82 }, { 81, 80, 79, 78, 77, 76, 75, 74, 73, 72 }, { 71, 70, 69, 68, 67, 66, 65, 64, 63, 62 }, { 61, 60, 59, 58, 57, 56, 55, 54, 53, 52 }, { 51, 50, 49, 48, 47, 46, 45, 44, 43, 42 }, { 41, 40, 39, 38, 37, 36, 35, 34, 33, 32 }, { 31, 30, 29, 28, 27, 26, 25, 24, 23, 22 }, { 21, 20, 19, 18, 17, 16, 15, 14, 13, 12 }, { 11, 10, 9, 8, 7, 6, 5, 4, 3, 2 } };

  private static final double[][] powerTable = { { 128.0D, 1.0E+256D, 1.0E-256D }, { 64.0D, 1.E+128D, 1.E-128D }, { 32.0D, 1.0E+64D, 1.0E-64D }, { 16.0D, 1.E+32D, 1.E-32D }, { 8.0D, 10000000000000000.0D, 1.0E-16D }, { 4.0D, 100000000.0D, 1.0E-08D }, { 2.0D, 10000.0D, 0.0001D }, { 1.0D, 100.0D, 0.01D } };

  private static final double[][] factorTable = { { 15.0D, 1.0E+30D, 1.E-30D }, { 14.0D, 1.0E+28D, 1.0E-28D }, { 13.0D, 1.0E+26D, 1.0E-26D }, { 12.0D, 1.0E+24D, 9.999999999999999E-25D }, { 11.0D, 1.0E+22D, 1.0E-22D }, { 10.0D, 1.0E+20D, 1.0E-20D }, { 9.0D, 1.0E+18D, 1.E-18D }, { 8.0D, 10000000000000000.0D, 1.0E-16D }, { 7.0D, 100000000000000.0D, 1.0E-14D }, { 6.0D, 1000000000000.0D, 1.0E-12D }, { 5.0D, 10000000000.0D, 1.0E-10D }, { 4.0D, 100000000.0D, 1.0E-08D }, { 3.0D, 1000000.0D, 1.0E-06D }, { 2.0D, 10000.0D, 0.0001D }, { 1.0D, 100.0D, 0.01D }, { 0.0D, 1.0D, 1.0D }, { -1.0D, 0.01D, 100.0D }, { -2.0D, 0.0001D, 10000.0D }, { -3.0D, 1.0E-06D, 1000000.0D }, { -4.0D, 1.0E-08D, 100000000.0D }, { -5.0D, 1.0E-10D, 10000000000.0D }, { -6.0D, 1.0E-12D, 1000000000000.0D }, { -7.0D, 1.0E-14D, 100000000000000.0D }, { -8.0D, 1.0E-16D, 10000000000000000.0D }, { -9.0D, 1.E-18D, 1.0E+18D }, { -10.0D, 1.0E-20D, 1.0E+20D }, { -11.0D, 1.0E-22D, 1.0E+22D }, { -12.0D, 9.999999999999999E-25D, 1.0E+24D }, { -13.0D, 1.0E-26D, 1.0E+26D }, { -14.0D, 1.0E-28D, 1.0E+28D }, { -15.0D, 1.E-30D, 1.0E+30D }, { -16.0D, 1.E-32D, 1.E+32D }, { -17.0D, 9.999999999999999E-35D, 1.0E+34D }, { -18.0D, 9.999999999999999E-37D, 1.0E+36D }, { -19.0D, 1.0E-38D, 1.0E+38D }, { -20.0D, 9.999999999999999E-41D, 1.0E+40D }, { -21.0D, 1.0E-42D, 1.0E+42D }, { -22.0D, 1.0E-44D, 1.E+44D }, { -23.0D, 1.0E-46D, 1.0E+46D }, { -24.0D, 1.0E-48D, 1.0E+48D }, { -25.0D, 1.0E-50D, 1.E+50D }, { -26.0D, 1.0E-52D, 1.0E+52D }, { -27.0D, 1.0E-54D, 1.E+54D }, { -28.0D, 1.0E-56D, 1.E+56D }, { -29.0D, 1.0E-58D, 9.999999999999999E+57D }, { -30.0D, 1.0E-60D, 1.0E+60D }, { -31.0D, 1.0E-62D, 1.0E+62D }, { -32.0D, 1.0E-64D, 1.0E+64D }, { -33.0D, 1.0E-66D, 1.0E+66D }, { -34.0D, 1.E-68D, 1.0E+68D } };

  private char[] lnx_chars = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '-', ' ', '.', ',', '$', '<', '>', '(', ')', '#', '~', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'i', 'l', 'm', 'p', 'r', 's', 't', 'v', 'A', 'B', 'C', 'D', 'E', 'F', 'I', 'L', 'M', 'P', 'R', 'S', 'T' };

  public byte[] lnxabs(byte[] paramArrayOfByte)
    throws SQLException
  {
    byte[] arrayOfByte = new byte[paramArrayOfByte.length];

    if (NUMBER._isPositive(paramArrayOfByte))
    {
      System.arraycopy(paramArrayOfByte, 0, arrayOfByte, 0, paramArrayOfByte.length);
      return arrayOfByte;
    }

    if (NUMBER._isNegInf(paramArrayOfByte))
    {
      return NUMBER.posInf().shareBytes();
    }

    int i = paramArrayOfByte.length;
    if (paramArrayOfByte[(i - 1)] == 102)
    {
      i--;
    }

    System.arraycopy(paramArrayOfByte, 0, arrayOfByte, 0, i);

    _negateNumber(arrayOfByte);

    return _setLength(arrayOfByte, i);
  }

  public byte[] lnxacos(byte[] paramArrayOfByte)
    throws SQLException
  {
    return lnxqtri(paramArrayOfByte, 0);
  }

  public byte[] lnxadd(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
    throws SQLException
  {
    int i = paramArrayOfByte1.length;
    int j = 0;
    int k = paramArrayOfByte2.length;
    int m = 0;

    byte[] arrayOfByte1 = new byte[41];
    int n = 0;

    int i5 = 0;
    int i6 = 0;

    int i15 = 0;
    int i16 = 0;
    int i17 = 0;

    int i9 = n + 1;

    boolean bool1 = NUMBER._isPositive(paramArrayOfByte1);
    int i19 = paramArrayOfByte1[0] < 0 ? 256 + paramArrayOfByte1[0] : 255 - paramArrayOfByte1[0];

    if (!bool1)
    {
      if (paramArrayOfByte1[(i - 1)] == 102) {
        i--;
      }
    }
    int i18 = i - 1;

    boolean bool2 = NUMBER._isPositive(paramArrayOfByte2);
    int i21 = paramArrayOfByte2[0] < 0 ? 256 + paramArrayOfByte2[0] : 255 - paramArrayOfByte2[0];

    if (!bool2)
    {
      if (paramArrayOfByte2[(k - 1)] == 102)
        k--;
    }
    int i20 = k - 1;
    boolean bool3;
    if ((i19 == 255) && ((i18 == 0) || (paramArrayOfByte1[1] == 101)))
    {
      bool3 = bool1;

      if (bool3) {
        return NUMBER._makePosInf();
      }
      return NUMBER._makeNegInf();
    }

    if ((i21 == 255) && ((i20 == 0) || (paramArrayOfByte2[1] == 101)))
    {
      bool3 = bool2;

      if (bool3) {
        return NUMBER._makePosInf();
      }
      return NUMBER._makeNegInf();
    }

    if ((i19 == 128) && (i18 == 0))
    {
      arrayOfByte1 = new byte[k];
      System.arraycopy(paramArrayOfByte2, 0, arrayOfByte1, 0, k);
      bool3 = bool2;
      i26 = k;

      return _setLength(arrayOfByte1, i26);
    }

    if ((i21 == 128) && (i20 == 0))
    {
      arrayOfByte1 = new byte[i];
      System.arraycopy(paramArrayOfByte1, 0, arrayOfByte1, 0, i);
      bool3 = bool1;
      i26 = i;

      return _setLength(arrayOfByte1, i26);
    }

    int i14 = i19 - i21;
    byte[][] arrayOfByte;
    int i24;
    int i25;
    int i27;
    int i28;
    if (bool1 == bool2)
    {
      bool3 = bool1;

      if (bool3)
      {
        arrayOfByte = LnxqAdd_PPP;
        i24 = 1;
        i25 = 1;
      }
      else
      {
        arrayOfByte = LnxqAdd_NNN;
        i24 = 101;
        i25 = -1;
      }

    }
    else
    {
      int i30 = i14;

      if (i30 == 0)
      {
        i27 = j + 1;
        i28 = m + 1;
        int i29 = j + (i18 < i20 ? i18 : i20);
        while ((i27 <= i29) && (paramArrayOfByte1[i27] + paramArrayOfByte2[i28] == 102))
        {
          i27++;
          i28++;
        }

        if (i27 <= i29)
        {
          i30 = bool1 ? paramArrayOfByte1[i27] + paramArrayOfByte2[i28] - 102 : 102 - (paramArrayOfByte1[i27] + paramArrayOfByte2[i28]);
        }
        else
        {
          i30 = i18 - i20;
        }

      }

      if (i30 == 0)
      {
        return NUMBER._makeZero();
      }

      bool3 = i30 > 0 ? bool1 : bool2;

      if (bool3)
      {
        arrayOfByte = LnxqAdd_PNP;
        i24 = 1;
        i25 = -1;
      }
      else
      {
        arrayOfByte = LnxqAdd_PNN;
        i24 = 101;
        i25 = 1;
      }
    }
    int i22;
    int i3;
    int i4;
    int i7;
    int i8;
    int i23;
    int i13;
    if (i14 >= 0)
    {
      i22 = i19;

      if (i14 + i20 <= i18)
      {
        i15 = i14;
        i16 = i20;
        i17 = i18 - (i14 + i20);

        i3 = j + i15;
        i4 = 1;
        i5 = i3 + i16;
        i6 = m + i20;
        i7 = j + i18;
        i8 = 1;

        i23 = i18;
        i13 = (i17 != 0) && (bool1 != bool3) ? 1 : 0;
      }
      else if (i14 < i18)
      {
        i15 = i14;
        i16 = i18 - i14;
        i17 = i20 - i16;

        i3 = j + i15;
        i4 = 1;
        i5 = j + i18;
        i6 = m + i16;
        i7 = m + i20;
        i8 = 2;

        i23 = i14 + i20;
        i13 = bool2 != bool3 ? 1 : 0;
      }
      else
      {
        i15 = i18;
        i16 = -(i14 - i18);
        i17 = i20;

        i3 = j + i18;
        i4 = 1;
        i7 = m + i20;
        i8 = 2;

        i23 = i14 + i20;
        i13 = bool2 != bool3 ? 1 : 0;
      }

    }
    else
    {
      i22 = i21;

      i14 = -i14;
      if (i14 + i18 <= i20)
      {
        i15 = i14;
        i16 = i18;
        i17 = i20 - (i14 + i18);

        i3 = m + i15;
        i4 = 2;
        i5 = j + i18;
        i6 = i3 + i16;
        i7 = m + i20;
        i8 = 2;

        i23 = i20;
        i13 = (i17 != 0) && (bool2 != bool3) ? 1 : 0;
      }
      else if (i14 < i20)
      {
        i15 = i14;
        i16 = i20 - i14;
        i17 = i18 - i16;

        i3 = m + i15;
        i4 = 2;
        i5 = j + i16;
        i6 = m + i20;
        i7 = j + i18;
        i8 = 1;

        i23 = i14 + i18;
        i13 = bool1 != bool3 ? 1 : 0;
      }
      else
      {
        i15 = i20;
        i16 = -(i14 - i20);
        i17 = i18;

        i3 = m + i20;
        i4 = 2;
        i7 = j + i18;
        i8 = 1;

        i23 = i14 + i18;
        i13 = bool1 != bool3 ? 1 : 0;
      }

    }

    if (i23 > 20)
    {
      if (i14 > 20)
      {
        i16 = 0;
        i17 = 0;
        i23 = i15;
        i13 = 0;
      }
      else
      {
        i9 = 1;
      }

    }

    int i10 = i9 + (i23 - 1);
    int i11 = i10;
    int i12;
    if (i17 != 0)
    {
      i12 = i11 - i17;

      if (i8 == 1)
        arrayOfByte1[i11] = paramArrayOfByte1[i7];
      else
        arrayOfByte1[i11] = paramArrayOfByte2[i7];
      i7--;
      i11--;

      if (i13 != 0)
      {
        while (i11 > i12)
        {
          if (i8 == 1)
            arrayOfByte1[i11] = ((byte)(paramArrayOfByte1[i7] + i25));
          else
            arrayOfByte1[i11] = ((byte)(paramArrayOfByte2[i7] + i25));
          i7--;
          i11--;
        }

      }

      while (i11 > i12)
      {
        if (i8 == 1)
          arrayOfByte1[i11] = paramArrayOfByte1[i7];
        else
          arrayOfByte1[i11] = paramArrayOfByte2[i7];
        i7--;
        i11--;
      }

    }

    if (i16 > 0)
    {
      i12 = i11 - i16;

      int i1 = 0;
      int i2 = i13 != 0 ? i1 + 1 : i1;
      do
      {
        i2 = i1 + paramArrayOfByte1[i5] + paramArrayOfByte2[i6] + arrayOfByte[i2][1];

        arrayOfByte1[i11] = arrayOfByte[i2][0];
        i5--;
        i6--;
        i11--;
      }
      while (i11 > i12);

      i13 = (arrayOfByte[i2][1] & 0x1) == 0 ? 0 : 1;
    }
    else
    {
      i27 = i13 != 0 ? 100 : i25 == 1 ? 2 : i24;

      i12 = i11 + i16;

      while (i11 > i12)
      {
        arrayOfByte1[i11] = ((byte)i27);
        i11--;
      }

    }

    if (i15 != 0)
    {
      i12 = i11 - i15;

      if (i13 != 0)
      {
        i27 = (i25 == 1 ? 100 : 1) + (bool3 ? 0 : 1);

        i28 = (i25 == 1 ? 1 : 100) + (bool3 ? 0 : 1);
        do
        {
          if (i4 == 1)
          {
            i13 = paramArrayOfByte1[i3] == i27 ? 1 : 0;
            arrayOfByte1[i11] = ((byte)(i13 != 0 ? i28 : paramArrayOfByte1[i3] + i25));
          }
          else
          {
            i13 = paramArrayOfByte2[i3] == i27 ? 1 : 0;
            arrayOfByte1[i11] = ((byte)(i13 != 0 ? i28 : paramArrayOfByte2[i3] + i25));
          }

          i3--;
          i11--;
        }
        while ((i13 != 0) && (i11 > i12));
      }

      while (i11 > i12)
      {
        if (i4 == 1)
          arrayOfByte1[i11] = paramArrayOfByte1[i3];
        else
          arrayOfByte1[i11] = paramArrayOfByte2[i3];
        i3--;
        i11--;
      }

    }

    if (i13 != 0)
    {
      if (i22 == 255)
      {
        if (bool3) {
          return NUMBER._makePosInf();
        }
        return NUMBER._makeNegInf();
      }

      i9--;
      arrayOfByte1[i9] = ((byte)(bool3 ? 2 : 100));
      i22++;
      i23++;
    }

    if (arrayOfByte1[i9] == i24)
    {
      do
      {
        i9++;
        i22--;
        i23--;
      }
      while (arrayOfByte1[i9] == i24);

      if (i22 < 128) {
        return NUMBER._makeZero();
      }

    }

    if (i23 > 20)
    {
      i10 = i9 + 19;
      i23 = 20;

      if ((bool3 ? arrayOfByte1[(i10 + 1)] : LnxqNegate[arrayOfByte1[(i10 + 1)]]) > 50)
      {
        i27 = bool3 ? 100 : 2;

        if (i13 == 0) {
          arrayOfByte1[(i9 - 1)] = ((byte)i24);
        }

        while (arrayOfByte1[i10] == i27)
        {
          i10--;
          i23--;
        }

        if (i10 < i9)
        {
          if (i22 == 255)
          {
            if (bool3) {
              return NUMBER._makePosInf();
            }
            return NUMBER._makeNegInf();
          }
          i9--;
          i22++;
          i23 = 1;
        }
        int tmp1810_1808 = i10;
        byte[] tmp1810_1806 = arrayOfByte1; tmp1810_1806[tmp1810_1808] = ((byte)(tmp1810_1806[tmp1810_1808] + (bool3 ? 1 : -1)));
      }

    }

    while (arrayOfByte1[i10] == i24)
    {
      i10--;
      i23--;
    }

    if (i9 != 1)
    {
      byte[] arrayOfByte2 = new byte[41];
      System.arraycopy(arrayOfByte1, i9, arrayOfByte2, 1, i23);
      System.arraycopy(arrayOfByte2, 1, arrayOfByte1, 1, i23);
    }

    int i26 = i23 + 1;

    if (!bool3)
    {
      if (i26 <= 20)
      {
        arrayOfByte1[i26] = 102;
        i26++;
      }
    }

    arrayOfByte1[n] = ((byte)(bool3 ? i22 - 256 : 255 - i22));
    return _setLength(arrayOfByte1, i26);
  }

  public byte[] lnxasin(byte[] paramArrayOfByte)
    throws SQLException
  {
    return lnxqtri(paramArrayOfByte, 1);
  }

  public byte[] lnxatan(byte[] paramArrayOfByte)
    throws SQLException
  {
    return lnxqtri(paramArrayOfByte, 2);
  }

  public byte[] lnxatan2(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
    throws SQLException
  {
    if ((NUMBER._isZero(paramArrayOfByte1)) && (NUMBER._isZero(paramArrayOfByte2)))
    {
      throw new SQLException(CoreException.getMessage((byte)11));
    }

    byte[] arrayOfByte1 = lnxdiv(paramArrayOfByte1, paramArrayOfByte2);
    arrayOfByte1 = lnxatan(arrayOfByte1);

    if (NUMBER._isPositive(paramArrayOfByte2))
    {
      return arrayOfByte1;
    }

    byte[] arrayOfByte2 = NUMBER.pi().shareBytes();

    if (NUMBER._isPositive(paramArrayOfByte1))
    {
      return lnxadd(arrayOfByte1, arrayOfByte2);
    }

    return lnxsub(arrayOfByte1, arrayOfByte2);
  }

  public byte[] lnxbex(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
    throws SQLException
  {
    byte[] arrayOfByte;
    switch (lnxsgn(paramArrayOfByte1))
    {
    case 1:
      arrayOfByte = lnxln(paramArrayOfByte1);
      arrayOfByte = lnxmul(paramArrayOfByte2, arrayOfByte);
      return lnxexp(arrayOfByte);
    case 0:
      if (NUMBER._isZero(paramArrayOfByte2))
      {
        arrayOfByte = new byte[lnxqone.length];
        System.arraycopy(lnxqone, 0, arrayOfByte, 0, lnxqone.length);
        return arrayOfByte;
      }

      return NUMBER._makeZero();
    case -1:
      if (new NUMBER(paramArrayOfByte2).isInt())
      {
        arrayOfByte = lnxneg(paramArrayOfByte1);
        arrayOfByte = lnxln(arrayOfByte);
        arrayOfByte = lnxmul(paramArrayOfByte2, arrayOfByte);
        arrayOfByte = lnxexp(arrayOfByte);

        if (!NUMBER._isZero(lnxmod(paramArrayOfByte2, lnxqtwo)))
        {
          arrayOfByte = lnxneg(arrayOfByte);
        }
        return arrayOfByte;
      }

      return NUMBER._makePosInf();
    }

    return null;
  }

  public byte[] lnxcos(byte[] paramArrayOfByte)
    throws SQLException
  {
    return lnxqtra(paramArrayOfByte, 3);
  }

  public byte[] lnxcsh(byte[] paramArrayOfByte)
    throws SQLException
  {
    return lnxqtra(paramArrayOfByte, 6);
  }

  public byte[] lnxdec(byte[] paramArrayOfByte)
    throws SQLException
  {
    int m = paramArrayOfByte.length;
    byte[] arrayOfByte1 = new byte[22];

    System.arraycopy(paramArrayOfByte, 0, arrayOfByte1, 0, m);

    if (NUMBER._isPositive(arrayOfByte1))
    {
      int i = (byte)((arrayOfByte1[0] & 0xFFFFFF7F) - 65);

      if ((i >= 0) && (i <= 18))
      {
        int j = i + 1;
        int k = m - 1;

        if (j <= k)
        {
          int tmp70_69 = j;
          byte[] tmp70_67 = arrayOfByte1; tmp70_67[tmp70_69] = ((byte)(tmp70_67[tmp70_69] - 1));

          if ((arrayOfByte1[j] == 1) && (j == k))
          {
            m--;
            if (m == 1)
              return NUMBER._makeZero();
          }
        }
        else
        {
          int tmp107_105 = k;
          byte[] tmp107_103 = arrayOfByte1; tmp107_103[tmp107_105] = ((byte)(tmp107_103[tmp107_105] - 1));

          while (j > k)
          {
            arrayOfByte1[j] = 100;
            j--;
          }

          if (arrayOfByte1[1] == 1)
          {
            for (int n = 1; n <= i; n++)
              arrayOfByte1[n] = arrayOfByte1[(n + 1)];
            i--;
          }

          m = i + 2;
        }

        arrayOfByte1[0] = ((byte)(i + 128 + 64 + 1));
        byte[] arrayOfByte2 = new byte[m];
        System.arraycopy(arrayOfByte1, 0, arrayOfByte2, 0, m);

        return arrayOfByte2;
      }

    }

    return NUMBER._makeZero();
  }

  public byte[] lnxdiv(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
    throws SQLException
  {
    byte[] arrayOfByte1 = paramArrayOfByte1;
    int i = arrayOfByte1.length;
    byte[] arrayOfByte2 = paramArrayOfByte2;
    int j = arrayOfByte2.length;
    byte[] arrayOfByte3 = new byte[22];

    int[] arrayOfInt1 = new int[22];

    int[] arrayOfInt2 = new int[10];

    int[] arrayOfInt3 = new int[13];

    int n = arrayOfByte1[0] >> 7 != 0 ? 1 : 0;
    int i1 = arrayOfByte1[0];

    if (n == 0)
    {
      i1 = (byte)(i1 ^ 0xFFFFFFFF);
      if (arrayOfByte1[(i - 1)] == 102) i--;

    }

    int i4 = arrayOfByte2[0] >> 7 != 0 ? 1 : 0;
    int i5 = arrayOfByte2[0];

    if (i4 == 0)
    {
      i5 = (byte)(i5 ^ 0xFFFFFFFF);
      if (arrayOfByte2[(j - 1)] == 102) j--;

    }

    if (((i5 & 0xFF) == 128) && (j == 1))
    {
      if (n == i4)
      {
        return NUMBER._makePosInf();
      }
      return NUMBER._makeNegInf();
    }

    if (((i1 & 0xFF) == 128) && (i == 1))
    {
      return NUMBER._makeZero();
    }
    int i17;
    if (i == 1) i17 = 0; else i17 = 1;
    if ((((i1 & 0xFF) == 255) && ((i == 2) || (arrayOfByte1[i17] == 101))) || ((i == 1) && (arrayOfByte1[0] == 0)))
    {
      if (n == i4)
      {
        return NUMBER._makePosInf();
      }

      return NUMBER._makeNegInf();
    }

    if (j == 1) i17 = 0; else i17 = 1;
    if ((((i5 & 0xFF) == 255) && ((j == 2) || (arrayOfByte2[i17] == 101))) || ((j == 1) && (arrayOfByte2[0] == 0)))
    {
      return NUMBER._makeZero();
    }

    int i2 = i / 2 - 1;
    int i3 = 21;
    int k = i - 2;

    while (i3 > i2)
    {
      arrayOfInt1[i3] = 0;
      i3--;
    }

    if (n != 0)
    {
      if ((i & 0x1) == 0)
      {
        arrayOfInt1[i3] = (arrayOfByte1[(k + 1)] * 100 - 100);
        k--;
        i3--;
      }

      while (k > 0)
      {
        arrayOfInt1[i3] = (arrayOfByte1[k] * 100 + arrayOfByte1[(k + 1)] - 101);
        k -= 2;
        i3--;
      }

    }

    if ((i & 0x1) == 0)
    {
      arrayOfInt1[i3] = (10100 - arrayOfByte1[(k + 1)] * 100);
      k--;
      i3--;
    }

    while (k > 0)
    {
      arrayOfInt1[i3] = (10201 - (arrayOfByte1[k] * 100 + arrayOfByte1[(k + 1)]));
      k -= 2;
      i3--;
    }

    int i6 = j / 2 - 1;
    int i7 = i6;
    int m = j - 2;

    if (i4 != 0)
    {
      if ((j & 0x1) == 0)
      {
        arrayOfInt2[i7] = (arrayOfByte2[(m + 1)] * 100 - 100);
        m--;
        i7--;
      }

      while (m > 0)
      {
        arrayOfInt2[i7] = (arrayOfByte2[m] * 100 + arrayOfByte2[(m + 1)] - 101);
        m -= 2;
        i7--;
      }

    }

    if ((j & 0x1) == 0)
    {
      arrayOfInt2[i7] = (10100 - arrayOfByte2[(m + 1)] * 100);
      m--;
      i7--;
    }

    while (m > 0)
    {
      arrayOfInt2[i7] = (10201 - (arrayOfByte2[m] * 100 + arrayOfByte2[(m + 1)]));
      m -= 2;
      i7--;
    }

    int i12 = 0;
    int i13 = -1;
    int i20;
    if (j <= 3)
    {
      i3 = 0;

      int i18 = arrayOfInt1[0];
      i20 = arrayOfInt2[0];
      do
      {
        int i21 = i18 / i20;

        i3++;
        i18 -= i21 * i20;
        i18 = i18 * 10000 + arrayOfInt1[i3];

        i13++;
        arrayOfInt3[i13] = i21;

        if ((i18 == 0) && (i3 >= i2)) break;
      }
      while (i13 < 10 + (arrayOfInt3[0] == 0 ? 2 : 1));
    }
    else
    {
      int i23 = 0;
      int i24 = i6;

      double d1 = arrayOfInt1[i23] * 10000 + arrayOfInt1[(i23 + 1)];
      double d2 = arrayOfInt2[0] * 10000 + arrayOfInt2[1];
      do
      {
        int i22 = (int)(d1 / d2);

        if (i22 != 0)
        {
          i3 = i23 + 2;
          i7 = 2;
          while (i3 <= i24)
          {
            arrayOfInt1[i3] -= i22 * arrayOfInt2[i7];
            i3++;
            i7++;
          }
        }

        d1 -= i22 * d2;
        d1 = d1 * 10000.0D + arrayOfInt1[(i23 + 2)];

        if (i22 >= 10000)
        {
          i14 = i13;
          while (arrayOfInt3[i14] == 9999)
          {
            arrayOfInt3[i14] = 0;
            i14--;
          }
          arrayOfInt3[i14] += 1;
          i22 -= 10000;
        }

        if (i22 < 0)
        {
          i14 = i13;
          while (arrayOfInt3[i14] == 0)
          {
            arrayOfInt3[i14] = 9999;
            i14--;
          }
          arrayOfInt3[i14] -= 1;
          i22 += 10000;
        }

        i13++;
        arrayOfInt3[i13] = i22;

        if (i23 >= i2) if ((d1 < 0.0D ? -d1 : d1) < 0.1D)
          {
            i3 = i23 + 2;
            while ((i3 <= i24) && (arrayOfInt1[i3] == 0))
            {
              i3++;
            }
            if (i3 > i24)
            {
              break;
            }
          }

        i23++;
        i24++;
      }
      while (i13 < 10 + (arrayOfInt3[0] == 0 ? 2 : 1));
    }

    if (arrayOfInt3[0] == 0)
    {
      i12++;
    }

    while (arrayOfInt3[i13] == 0)
    {
      i13--;
    }

    int i15 = arrayOfInt3[i12] >= 100 ? 1 : 0;
    int i16 = arrayOfInt3[i13] % 100 != 0 ? 1 : 0;

    int i10 = 2 * (i13 - i12) + i15 + i16;

    if (i10 > 20)
    {
      if (i15 > 0)
      {
        i13 = i12 + 9;
        arrayOfInt3[i13] += (arrayOfInt3[(i13 + 1)] >= 5000 ? 1 : 0);
      }
      else
      {
        i13 = i12 + 10;
        arrayOfInt3[i13] = ((arrayOfInt3[i13] + 50) / 100 * 100);
      }

      if (arrayOfInt3[i13] == 10000)
      {
        do
        {
          i13--;
        }
        while (arrayOfInt3[i13] == 9999);
        arrayOfInt3[i13] += 1;
      }

      if (arrayOfInt3[0] != 0)
      {
        i12 = 0;
      }

      while (arrayOfInt3[i13] == 0)
      {
        i13--;
      }

      i15 = arrayOfInt3[i12] >= 100 ? 1 : 0;
      i16 = arrayOfInt3[i13] % 100 != 0 ? 1 : 0;
      i10 = 2 * (i13 - i12) + i15 + i16;
    }

    int i8 = (i1 & 0xFF) - (i5 & 0xFF) - (arrayOfInt3[0] == 0 ? 1 : 0) + 193;

    if (i8 > 255)
    {
      if (n == i4)
      {
        return NUMBER._makePosInf();
      }
      return NUMBER._makeNegInf();
    }

    if (i8 < 128)
    {
      return NUMBER._makeZero();
    }

    int i9 = i10 + 1;
    arrayOfByte3 = new byte[i9];

    int i11 = i10;
    int i14 = i13;

    if (i16 == 0)
    {
      arrayOfByte3[i11] = ((byte)(arrayOfInt3[i14] / 100 + 1));
      i11--;

      i14--;
    }
    int i19;
    while (i11 > 1)
    {
      i19 = arrayOfInt3[i14] / 100;
      i20 = arrayOfInt3[i14] - i19 * 100;
      arrayOfByte3[i11] = ((byte)(i20 + 1));
      i11--;
      arrayOfByte3[i11] = ((byte)(i19 + 1));
      i11--;

      i14--;
    }

    if (i15 == 0)
    {
      arrayOfByte3[i11] = ((byte)(arrayOfInt3[i14] + 1));
    }

    arrayOfByte3[0] = ((byte)i8);

    if (n != i4)
    {
      i9++;
      byte[] arrayOfByte4;
      if (i9 > 20)
      {
        arrayOfByte4 = new byte[21];
        i9 = 21;
      }
      else {
        arrayOfByte4 = new byte[i9];
      }
      arrayOfByte4[0] = ((byte)(i8 ^ 0xFFFFFFFF));
      for (i19 = 0; i19 < i9 - 2; i19++)
      {
        arrayOfByte4[(i19 + 1)] = ((byte)(102 - arrayOfByte3[(i19 + 1)]));
      }
      if (i9 <= 20) {
        arrayOfByte4[(i9 - 1)] = 102;
      }
      else if (arrayOfByte3.length == 20)
        arrayOfByte4[(i9 - 1)] = 102;
      else {
        arrayOfByte4[(i19 + 1)] = ((byte)(102 - arrayOfByte3[(i19 + 1)]));
      }
      return arrayOfByte4;
    }

    return arrayOfByte3;
  }

  public byte[] lnxexp(byte[] paramArrayOfByte)
    throws SQLException
  {
    return lnxqtra(paramArrayOfByte, 9);
  }

  public byte[] lnxflo(byte[] paramArrayOfByte)
    throws SQLException
  {
    byte[] arrayOfByte = lnxtru(paramArrayOfByte, 0);

    if ((NUMBER.compareBytes(arrayOfByte, paramArrayOfByte) != 0) && (!NUMBER._isPositive(paramArrayOfByte))) {
      arrayOfByte = lnxsub(arrayOfByte, lnxqone);
    }
    return arrayOfByte;
  }

  public byte[] lnxceil(byte[] paramArrayOfByte)
    throws SQLException
  {
    byte[] arrayOfByte = lnxtru(paramArrayOfByte, 0);

    if ((NUMBER.compareBytes(arrayOfByte, paramArrayOfByte) != 0) && (NUMBER._isPositive(paramArrayOfByte))) {
      arrayOfByte = lnxadd(arrayOfByte, lnxqone);
    }
    return arrayOfByte;
  }

  public byte[] lnxfpr(byte[] paramArrayOfByte, int paramInt)
    throws SQLException
  {
    int i3 = paramArrayOfByte.length;

    if (NUMBER._isZero(paramArrayOfByte))
    {
      return NUMBER._makeZero();
    }
    if (NUMBER._isNegInf(paramArrayOfByte))
    {
      return NUMBER._makeNegInf();
    }
    if (NUMBER._isPosInf(paramArrayOfByte))
    {
      return NUMBER._makePosInf();
    }

    if (paramInt < 0)
    {
      return NUMBER._makeZero();
    }
    boolean bool;
    int k;
    int m;
    int n;
    int i1;
    int i2;
    if ((bool = NUMBER._isPositive(paramArrayOfByte)))
    {
      paramInt += ((paramArrayOfByte[1] & 0xFF) < 11 ? 2 : 1);
      k = paramInt >> 1;
      m = (paramInt & 0x1) == 1 ? 1 : 0;
      n = 1;
      i1 = 100;
      i2 = 1;
    }
    else
    {
      paramInt += ((paramArrayOfByte[1] & 0xFF) > 91 ? 2 : 1);
      k = paramInt >> 1;
      m = (paramInt & 0x1) == 1 ? 1 : 0;
      n = 101;
      i1 = 2;
      i2 = -1;
      i3 -= ((paramArrayOfByte[(i3 - 1)] & 0xFF) == 102 ? 1 : 0);
    }

    byte[] arrayOfByte = new byte[i3];
    System.arraycopy(paramArrayOfByte, 0, arrayOfByte, 0, i3);

    if ((k > i3 - 1) || ((k == i3 - 1) && ((m != 0) || (LnxqFirstDigit[paramArrayOfByte[k]] == 1))))
    {
      return _setLength(paramArrayOfByte, i3);
    }

    if ((k != 0) || ((m != 0) && (bool ? paramArrayOfByte[1] >= 51 : paramArrayOfByte[1] <= 51))) {
      if ((k != 1) || (m != 0) || (bool ? paramArrayOfByte[1] >= 6 : paramArrayOfByte[1] <= 96));
    }
    else {
      return NUMBER._makeZero();
    }

    if (k == 0)
    {
      if (NUMBER._isInf(paramArrayOfByte))
      {
        if (bool)
        {
          return NUMBER._makePosInf();
        }

        return NUMBER._makeNegInf();
      }

      arrayOfByte[0] = ((byte)(paramArrayOfByte[0] + i2));
      arrayOfByte[1] = ((byte)(n + i2));

      return _setLength(arrayOfByte, 2);
    }

    int i = j = (byte)k;

    if (m != 0)
    {
      if (bool ? paramArrayOfByte[(j + 1)] > 50 : paramArrayOfByte[(j + 1)] < 52)
      {
        arrayOfByte[i] = ((byte)(paramArrayOfByte[j] + i2));
      }
      else
      {
        arrayOfByte[i] = paramArrayOfByte[j];
      }

    }
    else
    {
      arrayOfByte[i] = (bool ? LnxqRound_P[paramArrayOfByte[j]] : LnxqRound_N[paramArrayOfByte[j]]);
    }

    int j = (byte)(j - 1);
    int i4;
    if (arrayOfByte[i] == i1 + i2)
    {
      while ((j > 0) && (paramArrayOfByte[j] == i1))
      {
        j = (byte)(j - 1);
      }

      if (j == 0)
      {
        if (NUMBER._isInf(paramArrayOfByte))
        {
          if (bool)
          {
            return NUMBER._makePosInf();
          }

          return NUMBER._makeNegInf();
        }

        arrayOfByte[0] = ((byte)(paramArrayOfByte[0] + i2));
        arrayOfByte[1] = ((byte)(n + i2));

        return _setLength(arrayOfByte, 2);
      }

      arrayOfByte[j] = ((byte)(paramArrayOfByte[j] + i2));

      i4 = j + 1;

      j = (byte)(j - 1);
    }
    else if (arrayOfByte[i] == n)
    {
      while (paramArrayOfByte[j] == n)
      {
        j = (byte)(j - 1);
      }

      i4 = j + 1;
    }
    else
    {
      i4 = k + 1;
    }

    return _setLength(arrayOfByte, i4);
  }

  public byte[] lnxinc(byte[] paramArrayOfByte)
    throws SQLException
  {
    int n = paramArrayOfByte.length;
    byte[] arrayOfByte1 = new byte[22];

    System.arraycopy(paramArrayOfByte, 0, arrayOfByte1, 0, n);
    int i = 0;

    int m = (byte)((arrayOfByte1[0] & 0xFFFFFF7F) - 65);

    if ((m >= 0) && (m <= 18))
    {
      int j = (byte)(m + 1);
      int k = (byte)(n - 1);

      if (j <= k)
      {
        if (arrayOfByte1[j] < 100)
        {
          int tmp79_78 = j;
          byte[] tmp79_76 = arrayOfByte1; tmp79_76[tmp79_78] = ((byte)(tmp79_76[tmp79_78] + 1));
        }
        else
        {
          arrayOfByte1[i] = 0;
          do
          {
            j = (byte)(j - 1);
          }
          while (arrayOfByte1[j] == 100);

          if (j > i)
          {
            int tmp115_114 = j;
            byte[] tmp115_112 = arrayOfByte1; tmp115_112[tmp115_114] = ((byte)(tmp115_112[tmp115_114] + 1));
          }
          else
          {
            m++;
            j = (byte)(j + 1);
            arrayOfByte1[j] = 2;
          }

          arrayOfByte1[i] = ((byte)(m + 128 + 64 + 1));

          n = j - i + 1;
        }

      }
      else
      {
        arrayOfByte1[j] = 2;

        j = (byte)(j - 1);
        while (j > k)
        {
          arrayOfByte1[j] = 1;
          j = (byte)(j - 1);
        }

        n = m + 2;
      }

    }
    else
    {
      arrayOfByte1[0] = -63;
      arrayOfByte1[1] = 2;

      n = 2;
    }

    byte[] arrayOfByte2 = new byte[n];
    System.arraycopy(arrayOfByte1, 0, arrayOfByte2, 0, n);

    return arrayOfByte2;
  }

  public byte[] lnxln(byte[] paramArrayOfByte)
    throws SQLException
  {
    if (lnxsgn(paramArrayOfByte) <= 0) return NUMBER._makeNegInf();

    if (NUMBER._isPosInf(paramArrayOfByte)) return NUMBER._makePosInf();

    byte[] arrayOfByte1 = new byte[paramArrayOfByte.length];
    System.arraycopy(paramArrayOfByte, 0, arrayOfByte1, 0, paramArrayOfByte.length);

    int i = (arrayOfByte1[0] & 0xFF) - 193;
    arrayOfByte1[0] = -63;
    double d1 = lnxnur(arrayOfByte1);

    double d2 = Math.log(d1);
    byte[] arrayOfByte2 = NUMBER.toBytes(d2);

    byte[] arrayOfByte3 = lnxexp(arrayOfByte2);
    byte[] arrayOfByte4 = lnxdiv(arrayOfByte1, arrayOfByte3);
    arrayOfByte4 = lnxsub(arrayOfByte4, lnxqone);

    byte[] arrayOfByte5 = new byte[arrayOfByte4.length];
    System.arraycopy(arrayOfByte4, 0, arrayOfByte5, 0, arrayOfByte4.length);

    byte[] arrayOfByte6 = lnxmul(arrayOfByte4, arrayOfByte4);

    int j = 1;
    while ((arrayOfByte6[0] & 0xFF) > 172)
    {
      j++;
      arrayOfByte3 = lnxqIDiv(arrayOfByte6, j);
      arrayOfByte5 = lnxsub(arrayOfByte5, arrayOfByte3);
      arrayOfByte6 = lnxmul(arrayOfByte4, arrayOfByte6);
      j++;
      arrayOfByte3 = lnxqIDiv(arrayOfByte6, j);
      arrayOfByte5 = lnxadd(arrayOfByte5, arrayOfByte3);
      arrayOfByte6 = lnxmul(arrayOfByte4, arrayOfByte6);
    }

    i *= 2;

    byte[] arrayOfByte7 = NUMBER.ln10().shareBytes();
    arrayOfByte4 = lnxmin(i);
    arrayOfByte3 = lnxmul(arrayOfByte4, arrayOfByte7);

    arrayOfByte3 = lnxadd(arrayOfByte3, arrayOfByte2);
    return lnxadd(arrayOfByte3, arrayOfByte5);
  }

  public byte[] lnxlog(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
    throws SQLException
  {
    double d = NUMBER.toDouble(paramArrayOfByte2);

    if (d > 0.0D)
    {
      if (d == 10.0D)
      {
        arrayOfByte1 = lnxln(paramArrayOfByte1);
        byte[] arrayOfByte3 = NUMBER.ln10().shareBytes();
        return lnxdiv(arrayOfByte1, arrayOfByte3);
      }

      byte[] arrayOfByte1 = lnxln(paramArrayOfByte1);
      byte[] arrayOfByte2 = lnxln(paramArrayOfByte2);
      return lnxdiv(arrayOfByte1, arrayOfByte2);
    }

    return NUMBER._makeNegInf();
  }

  public byte[] lnxmod(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
    throws SQLException
  {
    byte[] arrayOfByte1 = lnxdiv(paramArrayOfByte1, paramArrayOfByte2);

    byte[] arrayOfByte2 = lnxtru(arrayOfByte1, 0);

    arrayOfByte1 = lnxmul(paramArrayOfByte2, arrayOfByte2);
    byte[] arrayOfByte3 = lnxsub(paramArrayOfByte1, arrayOfByte1);
    return arrayOfByte3;
  }

  public byte[] lnxmul(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
    throws SQLException
  {
    Object localObject1 = paramArrayOfByte1;
    int i = localObject1.length;
    Object localObject2 = paramArrayOfByte2;
    int j = localObject2.length;
    byte[] arrayOfByte1 = new byte[22];

    int[] arrayOfInt1 = new int[10];

    int[] arrayOfInt2 = new int[10];

    int i8 = 0;

    byte[] arrayOfByte2 = new byte[41];
    int i10 = 0;

    int i12 = 0;

    int n = localObject1[0] >> 7 != 0 ? 1 : 0;
    int i1 = localObject1[0];

    if (n == 0)
    {
      i1 = (byte)(i1 ^ 0xFFFFFFFF);
      if (localObject1[(i - 1)] == 102) i--;

    }

    int i4 = localObject2[0] >> 7 != 0 ? 1 : 0;
    int i5 = localObject2[0];

    if (i4 == 0)
    {
      i5 = (byte)(i5 ^ 0xFFFFFFFF);
      if (localObject2[(j - 1)] == 102) j--;

    }

    if ((-i1 == 128) && (i == 1))
    {
      arrayOfByte1 = NUMBER._makeZero();
      return arrayOfByte1;
    }

    if ((-i5 == 128) && (j == 1))
    {
      arrayOfByte1 = NUMBER._makeZero();
      return arrayOfByte1;
    }

    if (((i1 & 0xFF) == 255) && ((i == 1) || (localObject1[1] == 101)))
    {
      if (n == i4)
      {
        arrayOfByte1 = NUMBER._makePosInf();
      }
      else arrayOfByte1 = NUMBER._makeNegInf();

      return arrayOfByte1;
    }
    if (((i5 & 0xFF) == 255) && ((j == 1) || (localObject2[1] == 101)))
    {
      if (n == i4)
      {
        arrayOfByte1 = NUMBER._makePosInf();
      }
      else arrayOfByte1 = NUMBER._makeNegInf();

      return arrayOfByte1;
    }

    if (i > j)
    {
      Object localObject3 = localObject1;
      localObject1 = localObject2;
      localObject2 = localObject3;

      int i14 = i;
      i = j;
      j = i14;

      int i15 = n;
      n = i4;
      i4 = i15;
    }

    int i2 = i / 2 - 1;
    int i3 = i2;
    int k = i - 2;

    if (n != 0)
    {
      if ((i & 0x1) == 0)
      {
        arrayOfInt1[i3] = (localObject1[(k + 1)] * 100 - 100);
        k--;
        i3--;
      }

      while (k > 0)
      {
        arrayOfInt1[i3] = (localObject1[k] * 100 + localObject1[(k + 1)] - 101);
        k -= 2;
        i3--;
      }

    }

    if ((i & 0x1) == 0)
    {
      arrayOfInt1[i3] = (10100 - localObject1[(k + 1)] * 100);
      k--;
      i3--;
    }

    while (k > 0)
    {
      arrayOfInt1[i3] = (10201 - (localObject1[k] * 100 + localObject1[(k + 1)]));
      k -= 2;
      i3--;
    }

    int i6 = j / 2 - 1;
    int i7 = i6;
    int m = j - 2;

    if (i4 != 0)
    {
      if ((j & 0x1) == 0)
      {
        arrayOfInt2[i7] = (localObject2[(m + 1)] * 100 - 100);
        m--;
        i7--;
      }

      while (m > 0)
      {
        arrayOfInt2[i7] = (localObject2[m] * 100 + localObject2[(m + 1)] - 101);
        m -= 2;
        i7--;
      }

    }

    if ((j & 0x1) == 0)
    {
      arrayOfInt2[i7] = (10100 - localObject2[(m + 1)] * 100);
      m--;
      i7--;
    }

    while (m > 0)
    {
      arrayOfInt2[i7] = (10201 - (localObject2[m] * 100 + localObject2[(m + 1)]));
      m -= 2;
      i7--;
    }
    int i9;
    if (arrayOfInt1[0] * arrayOfInt2[0] < 1000000)
    {
      i8 = (short)((i1 & 0xFF) + (i5 & 0xFF) - 193);
      i9 = (i & 0xFE) + (j & 0xFE);
    }
    else
    {
      i8 = (short)((i1 & 0xFF) + (i5 & 0xFF) - 192);
      i9 = (i & 0xFE) + (j & 0xFE) + 1;
    }

    i10 = 1;
    int i11 = i9;

    if (i <= 3)
    {
      i12 = arrayOfInt1[0] * arrayOfInt2[i6];

      i12 = LnxmulSetDigit1(arrayOfByte2, i11, i12); i11 -= 2;

      i7 = i6 - 1;

      while (i7 >= 0)
      {
        i12 += arrayOfInt1[0] * arrayOfInt2[i7];
        i12 = LnxmulSetDigit1(arrayOfByte2, i11, i12); i11 -= 2;

        i7--;
      }

      LnxmulSetDigit2(arrayOfByte2, i11, i12); i11 -= 2;
    }
    else
    {
      i12 += arrayOfInt1[i2] * arrayOfInt2[i6];

      i12 = LnxmulSetDigit1(arrayOfByte2, i11, i12); i11 -= 2;

      i7 = i6 - 1;

      while (i7 > i6 - (i / 2 - 1))
      {
        switch (i6 - i7 + 1) {
        case 9:
          i12 = LnxmulSetSum(arrayOfInt1, arrayOfInt2, i2, i7, 8, i12);
        case 8:
          i12 = LnxmulSetSum(arrayOfInt1, arrayOfInt2, i2, i7, 7, i12);
        case 7:
          i12 = LnxmulSetSum(arrayOfInt1, arrayOfInt2, i2, i7, 6, i12);
        case 6:
          i12 = LnxmulSetSum(arrayOfInt1, arrayOfInt2, i2, i7, 5, i12);
        case 5:
          i12 = LnxmulSetSum(arrayOfInt1, arrayOfInt2, i2, i7, 4, i12);
        case 4:
          i12 = LnxmulSetSum(arrayOfInt1, arrayOfInt2, i2, i7, 3, i12);
        case 3:
          i12 = LnxmulSetSum(arrayOfInt1, arrayOfInt2, i2, i7, 2, i12);
        case 2:
          i12 = LnxmulSetSum(arrayOfInt1, arrayOfInt2, i2, i7, 1, i12);
        }
        i12 = LnxmulSetSum(arrayOfInt1, arrayOfInt2, i2, i7, 0, i12);

        i12 = LnxmulSetDigit1(arrayOfByte2, i11, i12); i11 -= 2;

        i7--;
      }

      do
      {
        switch (i / 2)
        {
        case 10:
          i12 = LnxmulSetSum(arrayOfInt1, arrayOfInt2, i2, i7, 9, i12);
        case 9:
          i12 = LnxmulSetSum(arrayOfInt1, arrayOfInt2, i2, i7, 8, i12);
        case 8:
          i12 = LnxmulSetSum(arrayOfInt1, arrayOfInt2, i2, i7, 7, i12);
        case 7:
          i12 = LnxmulSetSum(arrayOfInt1, arrayOfInt2, i2, i7, 6, i12);
        case 6:
          i12 = LnxmulSetSum(arrayOfInt1, arrayOfInt2, i2, i7, 5, i12);
        case 5:
          i12 = LnxmulSetSum(arrayOfInt1, arrayOfInt2, i2, i7, 4, i12);
        case 4:
          i12 = LnxmulSetSum(arrayOfInt1, arrayOfInt2, i2, i7, 3, i12);
        case 3:
          i12 = LnxmulSetSum(arrayOfInt1, arrayOfInt2, i2, i7, 2, i12);
        case 2:
          i12 = LnxmulSetSum(arrayOfInt1, arrayOfInt2, i2, i7, 1, i12);
        }i12 = LnxmulSetSum(arrayOfInt1, arrayOfInt2, i2, i7, 0, i12);

        i12 = LnxmulSetDigit1(arrayOfByte2, i11, i12); i11 -= 2;

        i7--;
      }

      while (i7 >= 0);

      i3 = i2 - 1;

      while (i3 > 0)
      {
        switch (i3 + 1) {
        case 9:
          i12 = LnxmulSetSum(arrayOfInt1, arrayOfInt2, i3, 0, 8, i12);
        case 8:
          i12 = LnxmulSetSum(arrayOfInt1, arrayOfInt2, i3, 0, 7, i12);
        case 7:
          i12 = LnxmulSetSum(arrayOfInt1, arrayOfInt2, i3, 0, 6, i12);
        case 6:
          i12 = LnxmulSetSum(arrayOfInt1, arrayOfInt2, i3, 0, 5, i12);
        case 5:
          i12 = LnxmulSetSum(arrayOfInt1, arrayOfInt2, i3, 0, 4, i12);
        case 4:
          i12 = LnxmulSetSum(arrayOfInt1, arrayOfInt2, i3, 0, 3, i12);
        case 3:
          i12 = LnxmulSetSum(arrayOfInt1, arrayOfInt2, i3, 0, 2, i12);
        case 2:
          i12 = LnxmulSetSum(arrayOfInt1, arrayOfInt2, i3, 0, 1, i12);
        }i12 = LnxmulSetSum(arrayOfInt1, arrayOfInt2, i3, 0, 0, i12);

        i12 = LnxmulSetDigit1(arrayOfByte2, i11, i12); i11 -= 2;

        i3--;
      }

      i12 += arrayOfInt1[0] * arrayOfInt2[0];

      i12 = LnxmulSetDigit1(arrayOfByte2, i11, i12); i11 -= 2;

      LnxmulSetDigit2(arrayOfByte2, i11, i12); i11 -= 2;
    }

    if (((i9 & 0x1) == 0) && (arrayOfByte2[i11] != 1))
    {
      i8 = (short)(i8 + 1);
      i9++;
      i10--;
    }

    while (arrayOfByte2[(i10 + i9 - 2)] == 1)
    {
      i9--;
    }

    if (i9 > 21)
    {
      i11 = i10 + 19;
      i9 = 21;

      if (arrayOfByte2[(i11 + 1)] > 50)
      {
        while (arrayOfByte2[i11] == 100)
        {
          i11--;
          i9--;
        }

        if (i11 < i10)
        {
          arrayOfByte2[i10] = 2;
          i8 = (short)(i8 + 1);
          i9++;
        }
        int tmp1764_1762 = i11;
        byte[] tmp1764_1760 = arrayOfByte2; tmp1764_1760[tmp1764_1762] = ((byte)(tmp1764_1760[tmp1764_1762] + 1));
      }
      else
      {
        while (arrayOfByte2[(i10 + i9 - 2)] == 1)
        {
          i9--;
        }

      }

    }

    if ((i8 & 0xFFFF) > 255)
    {
      if (n == i4)
      {
        return NUMBER._makePosInf();
      }

      return NUMBER._makeNegInf();
    }

    if ((i8 & 0xFFFF) < 128)
    {
      return NUMBER._makeZero();
    }
    int i13;
    if (n != i4)
    {
      i9++;
      arrayOfByte1 = new byte[i9];
      arrayOfByte1[0] = ((byte)(i8 ^ 0xFFFFFFFF));
      for (i13 = 0; i13 < i9 - 1; i13++)
      {
        arrayOfByte1[(i13 + 1)] = ((byte)(102 - arrayOfByte2[(i10 + i13)]));
      }
      arrayOfByte1[(i9 - 1)] = 102;
    } else {
      arrayOfByte1 = new byte[i9];
      arrayOfByte1[0] = ((byte)i8);
      for (i13 = 0; i13 < i9 - 1; i13++)
      {
        arrayOfByte1[(i13 + 1)] = arrayOfByte2[(i10 + i13)];
      }
    }

    return arrayOfByte1;
  }

  private static int LnxmulSetSum(int[] paramArrayOfInt1, int[] paramArrayOfInt2, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    throws SQLException
  {
    int i = 0;
    try
    {
      i = paramInt4 + paramArrayOfInt1[(paramInt1 - paramInt3)] * paramArrayOfInt2[(paramInt2 + paramInt3)];
    }
    catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException)
    {
      throw new SQLException(CoreException.getMessage((byte)4));
    }

    return i;
  }

  private static int LnxmulSetDigit1(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    int i = paramInt2 / 100;
    int j = paramInt2 / 10000;
    paramInt1 -= 2;
    paramArrayOfByte[(paramInt1 + 1)] = ((byte)(paramInt2 - i * 100 + 1));
    paramArrayOfByte[paramInt1] = ((byte)(i - j * 100 + 1));
    return j;
  }

  private static void LnxmulSetDigit2(byte[] paramArrayOfByte, int paramInt1, int paramInt2) {
    int i = paramInt2 / 100;
    paramInt1 -= 2;
    paramArrayOfByte[paramInt1] = ((byte)(i + 1));
    paramArrayOfByte[(paramInt1 + 1)] = ((byte)(paramInt2 - i * 100 + 1));
  }

  public byte[] lnxneg(byte[] paramArrayOfByte)
    throws SQLException
  {
    if (NUMBER._isZero(paramArrayOfByte))
    {
      return NUMBER._makeZero();
    }
    if (NUMBER._isPosInf(paramArrayOfByte))
    {
      return NUMBER._makeNegInf();
    }
    if (NUMBER._isNegInf(paramArrayOfByte))
    {
      return NUMBER._makePosInf();
    }

    int i = paramArrayOfByte.length;
    if ((!NUMBER._isPositive(paramArrayOfByte)) && (paramArrayOfByte[(i - 1)] == 102)) {
      i--;
    }
    byte[] arrayOfByte = new byte[i];

    System.arraycopy(paramArrayOfByte, 0, arrayOfByte, 0, i);

    _negateNumber(arrayOfByte);

    return _setLength(arrayOfByte, i);
  }

  public byte[] lnxpow(byte[] paramArrayOfByte, int paramInt)
    throws SQLException
  {
    byte[] arrayOfByte1;
    if (paramInt >= 0)
    {
      arrayOfByte1 = new byte[paramArrayOfByte.length];
      System.arraycopy(paramArrayOfByte, 0, arrayOfByte1, 0, paramArrayOfByte.length);
    }
    else
    {
      int i = -2147483648;

      if (paramInt == i)
      {
        arrayOfByte1 = lnxpow(paramArrayOfByte, i + 1);
        return lnxdiv(arrayOfByte1, paramArrayOfByte);
      }

      paramInt = -paramInt;

      arrayOfByte1 = lnxdiv(lnxqone, paramArrayOfByte);
    }

    byte[] arrayOfByte2 = lnxqone;

    while (paramInt > 0)
    {
      if ((paramInt & 0x1) == 1)
      {
        arrayOfByte2 = lnxmul(arrayOfByte2, arrayOfByte1);
      }

      if (paramInt >>= 1 > 0)
      {
        arrayOfByte1 = lnxmul(arrayOfByte1, arrayOfByte1);
      }
    }

    return arrayOfByte2;
  }

  public byte[] lnxrou(byte[] paramArrayOfByte, int paramInt)
    throws SQLException
  {
    int i = paramArrayOfByte.length;
    int j = 0;

    if (i == 1)
    {
      if (paramArrayOfByte[j] == -128) {
        return NUMBER._makeZero();
      }
      return NUMBER._makeNegInf();
    }

    if (i == 2)
    {
      if ((paramArrayOfByte[0] == -1) && (paramArrayOfByte[1] == 101))
      {
        return NUMBER._makePosInf();
      }
    }
    int i6 = paramArrayOfByte[0] < 0 ? 256 + paramArrayOfByte[0] : paramArrayOfByte[0];
    boolean bool;
    int i2;
    int i3;
    int m;
    int n;
    int i1;
    if ((bool = NUMBER._isPositive(paramArrayOfByte)))
    {
      if (paramInt >= 0)
      {
        i2 = i6 + (paramInt + 1 >> 1) - 192;

        i3 = (paramInt & 0x1) == 0 ? 0 : 1;
      }
      else
      {
        paramInt = -paramInt;
        i2 = i6 - (paramInt >> 1) - 192;

        i3 = (paramInt & 0x1) == 0 ? 0 : 1;
      }
      m = 1;
      n = 100;
      i1 = 1;
    }
    else
    {
      if (paramInt >= 0)
      {
        i2 = 63 + (paramInt + 1 >> 1) - i6;

        i3 = (paramInt & 0x1) == 0 ? 0 : 1;
      }
      else
      {
        paramInt = -paramInt;
        i2 = 63 - (paramInt >> 1) - i6;

        i3 = (paramInt & 0x1) == 0 ? 0 : 1;
      }
      m = 101;
      n = 2;
      i1 = -1;

      i -= (paramArrayOfByte[(i - 1)] == 102 ? 1 : 0);
    }

    byte[] arrayOfByte = new byte[i];
    System.arraycopy(paramArrayOfByte, 0, arrayOfByte, 0, i);

    if ((i2 > i - 1) || ((i2 == i - 1) && ((i3 == 0) || (LnxqFirstDigit[paramArrayOfByte[i2]] == 1))))
    {
      return _setLength(paramArrayOfByte, i);
    }

    if ((i2 >= 0) && ((i2 != 0) || ((i3 == 0) && (bool ? paramArrayOfByte[1] >= 51 : paramArrayOfByte[1] <= 51)))) {
      if ((i2 != 1) || (i3 == 0) || (bool ? paramArrayOfByte[1] >= 6 : paramArrayOfByte[1] <= 96));
    }
    else
    {
      return NUMBER._makeZero();
    }

    if (i2 == 0)
    {
      if (bool ? paramArrayOfByte[j] == -1 : paramArrayOfByte[j] == 0)
      {
        if (bool) {
          return NUMBER._makePosInf();
        }
        return NUMBER._makeNegInf();
      }

      arrayOfByte[0] = ((byte)(paramArrayOfByte[j] + i1));
      arrayOfByte[1] = ((byte)(m + i1));

      return _setLength(arrayOfByte, 2);
    }
    int i4;
    int k = i4 = (byte)i2;

    if (i3 != 0)
    {
      arrayOfByte[i4] = (bool ? LnxqRound_P[paramArrayOfByte[k]] : LnxqRound_N[paramArrayOfByte[k]]);
    }
    else if (bool ? paramArrayOfByte[(k + 1)] > 50 : paramArrayOfByte[(k + 1)] < 52)
    {
      arrayOfByte[i4] = ((byte)(paramArrayOfByte[k] + i1));
    }
    else
    {
      arrayOfByte[i4] = paramArrayOfByte[k];
    }

    k = (byte)(k - 1);
    int i5;
    if (arrayOfByte[i4] == n + i1)
    {
      while ((k > j) && (paramArrayOfByte[k] == n)) {
        k = (byte)(k - 1);
      }

      if (k == j)
      {
        if (bool ? paramArrayOfByte[j] == -1 : paramArrayOfByte[j] == 0)
        {
          if (bool) {
            return NUMBER._makePosInf();
          }
          return NUMBER._makeNegInf();
        }

        arrayOfByte[0] = ((byte)(paramArrayOfByte[j] + i1));
        arrayOfByte[1] = ((byte)(m + i1));

        return _setLength(arrayOfByte, 2);
      }

      arrayOfByte[(k - j)] = ((byte)(paramArrayOfByte[k] + i1));

      i5 = k - j + 1;

      k = (byte)(k - 1);
    }
    else if (arrayOfByte[i4] == m)
    {
      while (paramArrayOfByte[k] == m) {
        k = (byte)(k - 1);
      }

      i5 = k - j + 1;
    }
    else
    {
      i5 = i2 + 1;
    }

    return _setLength(arrayOfByte, i5);
  }

  public byte[] lnxsca(byte[] paramArrayOfByte, int paramInt1, int paramInt2, boolean[] paramArrayOfBoolean)
    throws SQLException
  {
    int m = paramArrayOfByte.length;

    if (m != 1)
    {
      int i;
      int j;
      int k;
      if (NUMBER._isPositive(paramArrayOfByte))
      {
        i = (byte)((paramArrayOfByte[0] & 0xFFFFFF7F) - 65);
        j = paramArrayOfByte[1];
        k = paramArrayOfByte[(m - 1)];
      }
      else
      {
        m--;
        i = (byte)(((paramArrayOfByte[0] ^ 0xFFFFFFFF) & 0xFFFFFF7F) - 65);
        j = LnxqNegate[paramArrayOfByte[1]];
        k = LnxqNegate[paramArrayOfByte[(m - 1)]];
      }

      if (2 * (m - i) - (k % 10 == 1 ? 1 : 0) > paramInt2)
      {
        byte[] arrayOfByte1 = lnxrou(paramArrayOfByte, paramInt2);

        if (NUMBER._isPositive(arrayOfByte1))
        {
          i = (byte)((arrayOfByte1[0] & 0xFFFFFF7F) - 65);
          j = arrayOfByte1.length != 1 ? arrayOfByte1[1] : 1;
        }
        else
        {
          i = (byte)(((arrayOfByte1[0] ^ 0xFFFFFFFF) & 0xFFFFFF7F) - 65);
          j = LnxqNegate[arrayOfByte1[1]];
        }

        paramArrayOfBoolean[0] = (2 * (i + 1) - (j < 11 ? 1 : 0) > paramInt1 ? 1 : false);

        return arrayOfByte1;
      }

      int n = paramArrayOfByte.length;

      byte[] arrayOfByte2 = new byte[n];
      System.arraycopy(paramArrayOfByte, 0, arrayOfByte2, 0, n);

      paramArrayOfBoolean[0] = (2 * (i + 1) - (j < 11 ? 1 : 0) > paramInt1 ? 1 : false);

      return arrayOfByte2;
    }

    paramArrayOfBoolean[0] = false;
    return NUMBER._makeZero();
  }

  public byte[] lnxshift(byte[] paramArrayOfByte, int paramInt)
    throws SQLException
  {
    byte[] arrayOfByte1 = paramArrayOfByte;
    int i = arrayOfByte1.length;

    byte[] arrayOfByte3 = new byte[22];

    int j = 0;

    int i3 = 0;

    if (i == 1) i4 = 0; else i4 = 1;
    if ((((arrayOfByte1[0] & 0xFF) == 128) && (i == 1)) || ((i == 2) && ((arrayOfByte1[0] & 0xFF) == 255) && (arrayOfByte1[i4] == 101)) || ((i == 1) && (arrayOfByte1[0] == 0)))
    {
      arrayOfByte2 = new byte[i];
      for (i4 = 0; i4 < i; i4++) arrayOfByte2[i4] = arrayOfByte1[i4];
      return arrayOfByte2;
    }

    int k = arrayOfByte1[0] >> 7 == 0 ? 1 : 0;

    int n = k != 0 ? 255 - arrayOfByte1[0] & 0xFF : arrayOfByte1[0] & 0xFF;
    int i1 = i;

    if ((paramInt & 0x1) > 0)
    {
      byte[][] arrayOfByte4;
      byte[][] arrayOfByte5;
      int i2;
      if (k != 0)
      {
        if (arrayOfByte1[(i1 - 1)] == 102) i1--;
        arrayOfByte4 = LnxqComponents_N;
        arrayOfByte5 = LnxqDigit_N;
        i2 = 101;
      }
      else
      {
        arrayOfByte4 = LnxqComponents_P;
        arrayOfByte5 = LnxqDigit_P;
        i2 = 1;
      }

      if (arrayOfByte4[arrayOfByte1[1]][0] != 0)
      {
        n = paramInt >= 0 ? n + (paramInt / 2 + 1) : n - -paramInt / 2;

        j = i1 - 2;
        i3 = i1 - 1;
        int m;
        if (i1 > 20)
        {
          m = arrayOfByte4[arrayOfByte1[(j + 1)]][1] >= 5 ? 1 : 0;
        }
        else
        {
          arrayOfByte3[(i3 + 1)] = arrayOfByte5[arrayOfByte4[arrayOfByte1[(j + 1)]][1]][0];
          i1++;
          m = 0;
        }

        while (j > 0)
        {
          arrayOfByte3[i3] = arrayOfByte5[arrayOfByte4[arrayOfByte1[(j + 0)]][1]][arrayOfByte4[arrayOfByte1[(j + 1)]][0]];
          j--;
          i3--;
        }

        arrayOfByte3[1] = arrayOfByte5[0][arrayOfByte4[arrayOfByte1[(j + 1)]][0]];

        if (m != 0)
        {
          int i5 = k != 0 ? 2 : 100;
          int i6 = k != 0 ? -1 : 1;

          i3 = 20;
          while (arrayOfByte3[i3] == i5)
          {
            i3--;
            i1--;
          }
          int tmp459_457 = i3;
          byte[] tmp459_455 = arrayOfByte3; tmp459_455[tmp459_457] = ((byte)(tmp459_455[tmp459_457] + i6));
        }

      }
      else
      {
        n = paramInt >= 0 ? n + paramInt / 2 : n - (-paramInt / 2 + 1);

        j = 1;
        i3 = 1;

        while (i3 < i1 - 1)
        {
          arrayOfByte3[i3] = arrayOfByte5[arrayOfByte4[arrayOfByte1[(j + 0)]][1]][arrayOfByte4[arrayOfByte1[(j + 1)]][0]];
          j++;
          i3++;
        }

        arrayOfByte3[i3] = arrayOfByte5[arrayOfByte4[arrayOfByte1[(j + 0)]][1]][0];
      }

      while (arrayOfByte3[(i1 - 1)] == i2)
      {
        i1--;
      }

      if (k != 0)
      {
        i1++;
        arrayOfByte3[(i1 - 1)] = 102;
      }

    }
    else
    {
      n = paramInt >= 0 ? n + paramInt / 2 : n - -paramInt / 2;

      for (i4 = 1; i4 < i1; i4++)
      {
        arrayOfByte3[i4] = arrayOfByte1[i4];
      }

    }

    if (n > 255)
    {
      if (k != 0)
      {
        arrayOfByte2 = new byte[1];
        arrayOfByte2[0] = 0;
      } else {
        arrayOfByte2 = new byte[2];
        arrayOfByte2[0] = -1; arrayOfByte2[1] = 101;
      }
      return arrayOfByte2;
    }
    if (n < 128)
    {
      arrayOfByte2 = new byte[1];
      arrayOfByte2[0] = -128;
      return arrayOfByte2;
    }

    arrayOfByte3[0] = (k != 0 ? (byte)(255 - n) : (byte)n);
    byte[] arrayOfByte2 = new byte[i1];
    for (int i4 = 0; i4 < i1; i4++)
    {
      arrayOfByte2[i4] = arrayOfByte3[i4];
    }
    return arrayOfByte2;
  }

  public byte[] lnxsin(byte[] paramArrayOfByte)
    throws SQLException
  {
    return lnxqtra(paramArrayOfByte, 4);
  }

  public byte[] lnxsnh(byte[] paramArrayOfByte)
    throws SQLException
  {
    return lnxqtra(paramArrayOfByte, 7);
  }

  public byte[] lnxsqr(byte[] paramArrayOfByte)
    throws SQLException
  {
    int i = paramArrayOfByte.length;
    int[] arrayOfInt1 = new int[29];
    int[] arrayOfInt2 = new int[29];

    if (!NUMBER._isPositive(paramArrayOfByte)) {
      return NUMBER._makeNegInf();
    }

    if (NUMBER._isPosInf(paramArrayOfByte)) {
      return NUMBER._makePosInf();
    }

    if (NUMBER._isZero(paramArrayOfByte)) {
      return NUMBER._makeZero();
    }

    int i8 = (paramArrayOfByte[0] & 0xFF) - 193;

    for (int i9 = 1; i9 < i; i9++) {
      paramArrayOfByte[i9] -= 1;
    }

    int i4 = 1;
    int i5 = i4 + 20 + 3;

    if ((i8 + 128 & 0x1) != 0)
    {
      k = ((arrayOfInt1[i4] * 100 + arrayOfInt1[(i4 + 1)]) * 100 + arrayOfInt1[(i4 + 2)]) * 100 + arrayOfInt1[(i4 + 3)];

      i4 += 3;
    }
    else
    {
      k = (arrayOfInt1[i4] * 100 + arrayOfInt1[(i4 + 1)]) * 100 + arrayOfInt1[(i4 + 2)];

      i4 += 2;
    }

    int j = (int)(Math.sqrt(k) * 100.0D);

    arrayOfInt2[1] = (j / 10000);
    arrayOfInt2[2] = (j / 100 % 100);
    arrayOfInt2[3] = (j % 100);

    k -= arrayOfInt2[1] * j;
    int k = k * 100 + arrayOfInt1[(i4 + 1)];
    k -= arrayOfInt2[2] * j;
    k = k * 100 + arrayOfInt1[(i4 + 2)];
    k -= arrayOfInt2[3] * j;

    i4 += 3;

    j *= 2;

    int n = 3;
    int i1 = n + 1;

    while (i4 < i5)
    {
      k = k * 100 + arrayOfInt1[i4];

      int m = k / j;

      k -= m * j;

      arrayOfInt2[i1] = m;

      i2 = n + (i5 - i4) < i1 ? n + (i5 - i4) : i1;
      int i6;
      if (m != 0)
      {
        i6 = i4 + 1;
        int i7 = n + 1;
        while (i7 < i2)
        {
          arrayOfInt1[i6] -= 2 * m * arrayOfInt2[i7];
          i6++;
          i7++;
        }
        if (i6 < i5)
          arrayOfInt1[i6] -= m * m;
      }
      else if (k == 0)
      {
        i6 = i4 + 1;
        while ((i6 < i5) && (arrayOfInt1[i6] == 0)) {
          i6++;
        }
        if (i6 == i5)
          break;
      }
      i4++;
      i1++;
    }

    int i2 = i1;
    i1--;

    arrayOfInt2[0] = 0;

    while (i1 > 0)
    {
      while (arrayOfInt2[i1] > 99)
      {
        arrayOfInt2[i1] -= 100;
        arrayOfInt2[(i1 - 1)] += 1;
      }
      while (arrayOfInt2[i1] < 0)
      {
        arrayOfInt2[i1] += 100;
        arrayOfInt2[(i1 - 1)] -= 1;
      }
      i1--;
    }

    i8 = (i8 - (i8 + 128 & 0x1)) / 2 + 1;

    while (arrayOfInt2[i1] == 0)
    {
      i1++;
      i8--;
      if (i8 < -65) {
        return NUMBER._makeZero();
      }
    }

    do
    {
      i2--;
    }
    while (arrayOfInt2[i2] == 0);

    int i3 = i2 - i1 + 2;
    if (i3 > 21)
    {
      i2 = i1 + 20;
      if (arrayOfInt2[i2] >= 50)
      {
        do
        {
          i2--;
        }
        while (arrayOfInt2[i2] == 99);
        arrayOfInt2[i2] += 1;
      }
      else
      {
        do
        {
          i2--;
        }
        while (arrayOfInt2[i2] == 0);
      }

      if (i2 < i1)
      {
        i1 = i2;
        i8++;
        if (i8 > 62) {
          return NUMBER._makePosInf();
        }
      }
      i3 = i2 - i1 + 2;
    }

    byte[] arrayOfByte = new byte[i3];

    arrayOfByte[0] = ((byte)(i8 - 63));

    for (i9 = i1; i9 <= i2; i9++) {
      arrayOfByte[(i9 - (i1 - 1))] = ((byte)(arrayOfInt2[i9] + 1));
    }

    return arrayOfByte;
  }

  public byte[] lnxsub(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
    throws SQLException
  {
    return lnxadd(paramArrayOfByte1, lnxneg(paramArrayOfByte2));
  }

  public byte[] lnxtan(byte[] paramArrayOfByte)
    throws SQLException
  {
    return lnxqtra(paramArrayOfByte, 5);
  }

  public byte[] lnxtnh(byte[] paramArrayOfByte)
    throws SQLException
  {
    return lnxqtra(paramArrayOfByte, 8);
  }

  public byte[] lnxtru(byte[] paramArrayOfByte, int paramInt)
    throws SQLException
  {
    int i1 = paramArrayOfByte.length;

    if (NUMBER._isZero(paramArrayOfByte))
    {
      return NUMBER._makeZero();
    }
    if (NUMBER._isNegInf(paramArrayOfByte))
    {
      return NUMBER._makeNegInf();
    }
    if (NUMBER._isPosInf(paramArrayOfByte))
    {
      return NUMBER._makePosInf();
    }

    int i3 = paramArrayOfByte[0] < 0 ? 256 + paramArrayOfByte[0] : paramArrayOfByte[0];
    boolean bool;
    int k;
    int m;
    int n;
    if ((bool = NUMBER._isPositive(paramArrayOfByte)))
    {
      if (paramInt >= 0)
      {
        k = i3 + (paramInt + 1 >> 1) - 192;
        m = (paramInt & 0x1) == 1 ? 1 : 0;
      }
      else
      {
        paramInt = -paramInt;
        k = i3 - (paramInt >> 1) - 192;
        m = (paramInt & 0x1) == 1 ? 1 : 0;
      }
      n = 1;
    }
    else
    {
      if (paramInt >= 0)
      {
        k = 63 + (paramInt + 1 >> 1) - i3;
        m = (paramInt & 0x1) == 1 ? 1 : 0;
      }
      else
      {
        paramInt = -paramInt;
        k = 63 - (paramInt >> 1) - i3;
        m = (paramInt & 0x1) == 1 ? 1 : 0;
      }

      n = 101;

      if (paramArrayOfByte[(i1 - 1)] == 102)
      {
        i1--;
      }

    }

    byte[] arrayOfByte = new byte[i1];
    System.arraycopy(paramArrayOfByte, 0, arrayOfByte, 0, i1);

    if ((k > i1 - 1) || ((k == i1 - 1) && ((m != 0) || (LnxqFirstDigit[paramArrayOfByte[k]] == 1))))
    {
      return _setLength(paramArrayOfByte, i1);
    }

    if ((k <= 0) || ((k == 1) && (m != 0) && (bool ? paramArrayOfByte[1] < 11 : paramArrayOfByte[1] > 91)))
    {
      return NUMBER._makeZero();
    }

    int i = j = (byte)k;

    if (m != 0)
    {
      if (bool)
      {
        arrayOfByte[i] = LnxqTruncate_P[paramArrayOfByte[j]];
      }
      else
      {
        arrayOfByte[i] = LnxqTruncate_N[paramArrayOfByte[j]];
      }

    }
    else
    {
      arrayOfByte[i] = paramArrayOfByte[j];
    }

    int j = (byte)(j - 1);
    int i2;
    if (arrayOfByte[i] == n)
    {
      while (paramArrayOfByte[j] == n)
      {
        j = (byte)(j - 1);
      }

      i2 = j + 1;
    }
    else
    {
      i2 = k + 1;
    }

    return _setLength(arrayOfByte, i2);
  }

  public byte[] lnxcpn(String paramString1, boolean paramBoolean1, int paramInt1, boolean paramBoolean2, int paramInt2, String paramString2)
    throws SQLException
  {
    int n = 0;

    int i4 = 0;
    boolean bool = false;
    int i5 = 0;

    int i6 = 0;
    int i7 = 0;
    int i8 = 0;
    int i9 = 0;
    int i10 = 0;
    int i11 = 0;

    int i15 = 0;
    int i16 = 0;
    int i17 = 0;
    int i18 = 0;

    int i27 = 0;
    Locale localLocale;
    if (paramString2 != null)
    {
      int i28 = paramString2.indexOf("_");
      if (i28 == -1)
      {
        localLocale = LxMetaData.getJavaLocale(paramString2, "");
      }
      else
      {
        String str1 = paramString2.substring(0, i28);
        String str2 = paramString2.substring(i28 + 1);
        localLocale = LxMetaData.getJavaLocale(str1, str2);
      }

      if (localLocale == null)
        localLocale = Locale.getDefault();
    }
    else
    {
      localLocale = Locale.getDefault();
    }

    DecimalFormatSymbols localDecimalFormatSymbols = new DecimalFormatSymbols(localLocale);
    int i22 = localDecimalFormatSymbols.getDecimalSeparator();
    int i26 = localDecimalFormatSymbols.getMinusSign();

    char[] arrayOfChar = paramString1.toCharArray();

    int j = 0;
    int k = arrayOfChar.length - 1;

    while ((j <= k) && (Character.isSpaceChar(arrayOfChar[j])))
    {
      j++;
    }

    if ((j <= k) && ((arrayOfChar[j] == i26) || (arrayOfChar[j] == '+')))
    {
      bool = arrayOfChar[j] == i26;
      j++;
    }

    int i = j;

    while ((j <= k) && (arrayOfChar[j] == '0'))
    {
      j++;
    }

    int m = j;

    while ((j <= k) && (Character.isDigit(arrayOfChar[j])))
    {
      j++;
    }

    i6 = j - i;
    i7 = j - m;

    if ((j <= k) && (arrayOfChar[j] == i22))
    {
      j++;

      n = j;

      while ((j <= k) && (Character.isDigit(arrayOfChar[j])))
      {
        j++;
      }

      i8 = j - n;

      i = j;
      do
      {
        j--;
      }
      while ((j >= n) && (arrayOfChar[j] == '0'));

      i9 = j - n + 1;

      j = i;
    }

    if (i6 + i8 != 0)
    {
      if ((j <= k) && ((arrayOfChar[j] == 'E') || (arrayOfChar[j] == 'e')))
      {
        j++;

        if ((j <= k) && ((arrayOfChar[j] == i26) || (arrayOfChar[j] == '+')))
        {
          i5 = arrayOfChar[j] == i26 ? 1 : 0;

          j++;
        }

        i = j;

        while ((j <= k) && (arrayOfChar[j] == '0'))
        {
          j++;
        }

        int i1 = j;

        while ((j <= k) && (Character.isDigit(arrayOfChar[j])))
        {
          i4 = i4 * 10 + (arrayOfChar[j] - '0');
          j++;
        }

        i10 = j - i;
        i11 = j - i1;

        if (i10 != 0)
        {
          if (i5 != 0)
          {
            i4 = -i4;
          }

        }

      }

      while ((j <= k) && (Character.isSpaceChar(arrayOfChar[j])))
      {
        j++;
      }
    }
    int i19;
    int i20;
    int i3;
    if (i7 != 0)
    {
      if (i9 != 0)
      {
        i4 += i7 - 1;

        i19 = i7;
        i20 = i9;

        i3 = m;
      }
      else
      {
        i4 += i7 - 1;

        j = m + (i7 - 1);
        while (arrayOfChar[j] == '0')
        {
          j--;
        }

        i19 = j - m + 1;
        i20 = 0;

        i3 = m;
      }

    }
    else if (i9 != 0)
    {
      j = n;
      while (arrayOfChar[j] == '0')
      {
        j++;
      }

      i4 -= j - n + 1;

      i19 = i9 - (j - n);
      i20 = 0;

      i3 = j;
    }
    else
    {
      return NUMBER._makeZero();
    }

    int i14 = (i4 & 0x1) == 1 ? 40 : 39;

    int i12 = i19 + i20;

    if ((paramBoolean1) || (paramBoolean2))
    {
      if (!paramBoolean1)
      {
        paramInt1 = 2147483647;
      }

      if (!paramBoolean2)
      {
        paramInt2 = 0;
      }

      i13 = i4 + 1 + paramInt2;
    }
    else
    {
      i13 = i12;
    }

    int i13 = Math.min(i13, i14);

    if ((i13 < 0) || ((i13 == 0) && (arrayOfChar[i3] < '5')))
    {
      return NUMBER._makeZero();
    }

    int i21 = 0;

    if (i13 < i12)
    {
      j = i3 + i13 + (i13 < i19 ? 0 : 1);

      if (arrayOfChar[j] < '5')
      {
        do
        {
          j--;

          if (j < i3) break;  } while ((arrayOfChar[j] == '0') || (arrayOfChar[j] == i22));
      }
      else
      {
        do
        {
          j--;
        }
        while ((j >= i3) && ((arrayOfChar[j] == '9') || (arrayOfChar[j] == i22)));

        i21 = 1;
      }

      if (j < i3)
      {
        arrayOfChar[1] = '1';
        arrayOfChar[2] = '0';
        i3 = 1;

        i4++;

        i15 = 0;
        i17 = (i4 & 0x1) == 1 ? 0 : 1;
        i18 = 0;
        i19 = (i4 & 0x1) == 1 ? 2 : 0;
        i20 = 0;
        i21 = 0;
        i27 = 1;
      }
      else if (i20 != 0)
      {
        if (j - i3 < i19)
        {
          i19 = j - i3 + 1;
          i20 = 0;
        }
        else
        {
          i20 = j - i3 - i19;
        }

      }
      else
      {
        i19 = j - i3 + 1;
      }

    }

    if (i27 == 0)
    {
      if (i20 != 0)
      {
        i15 = 1;
        i16 = (i4 & 0x1) == (i19 & 0x1) ? 1 : 0;
        i17 = (i4 & 0x1) == 1 ? 0 : 1;
        i18 = i16 != ((i20 & 0x1) == 1 ? 1 : 0) ? 1 : 0;

        i19 -= (i17 != 0 ? 1 : 0) + (i16 != 0 ? 1 : 0);
        i20 -= (i16 != 0 ? 1 : 0) + (i18 != 0 ? 1 : 0);
      }
      else
      {
        i15 = 0;
        i17 = (i4 & 0x1) == 1 ? 0 : 1;
        i18 = (i4 & 0x1) == (i19 & 0x1) ? 1 : 0;
        i19 -= (i17 != 0 ? 1 : 0) + (i18 != 0 ? 1 : 0);
      }

    }

    if ((i5 == 0) && ((i11 > 3) || (i4 > 125)))
    {
      if (bool)
      {
        return NUMBER._makeNegInf();
      }

      return NUMBER._makePosInf();
    }

    if ((i5 != 0) && ((i11 > 3) || (i4 < -130)))
    {
      return NUMBER._makeZero();
    }

    byte[] arrayOfByte = new byte[22];
    int i25 = 1;
    j = i3;

    if (i17 != 0)
    {
      arrayOfByte[i25] = digitPtr(0, lnxqctn(arrayOfChar[j]), bool);

      i25++;
      j++;
    }
    int i2;
    if (i19 != 0)
    {
      i2 = j + i19;
      while (j < i2)
      {
        arrayOfByte[i25] = digitPtr(lnxqctn(arrayOfChar[j]), lnxqctn(arrayOfChar[(j + 1)]), bool);

        i25++;
        j += 2;
      }

    }

    if (i15 != 0)
    {
      if (i16 != 0)
      {
        arrayOfByte[i25] = digitPtr(lnxqctn(arrayOfChar[j]), lnxqctn(arrayOfChar[(j + 2)]), bool);

        i25++;
        j += 3;
      }
      else
      {
        j++;
      }

    }

    if (i20 != 0)
    {
      i2 = j + i20;
      while (j < i2)
      {
        arrayOfByte[i25] = digitPtr(lnxqctn(arrayOfChar[j]), lnxqctn(arrayOfChar[(j + 1)]), bool);

        i25++;
        j += 2;
      }

    }

    if (i18 != 0)
    {
      arrayOfByte[i25] = digitPtr(lnxqctn(arrayOfChar[j]), 0, bool);

      i25++;
      j++;
    }

    if (i21 != 0)
    {
      int tmp1571_1570 = (i25 - 1);
      byte[] tmp1571_1565 = arrayOfByte; tmp1571_1565[tmp1571_1570] = ((byte)(tmp1571_1565[tmp1571_1570] + (bool ? -1 : 1) * (i18 != 0 ? 10 : 1)));
    }
    int i24;
    if (i4 < 0)
    {
      i24 = (byte)(193 - (1 - i4) / 2);
    }
    else
    {
      i24 = (byte)(193 + i4 / 2);
    }

    int i23 = i25;

    arrayOfByte[0] = ((byte)(bool ? i24 ^ 0xFFFFFFFF : i24));

    return _setLength(arrayOfByte, i23);
  }

  private static byte digitPtr(int paramInt1, int paramInt2, boolean paramBoolean)
  {
    return paramBoolean ? LnxqDigit_N[paramInt1][paramInt2] : LnxqDigit_P[paramInt1][paramInt2];
  }

  private static int lnxqctn(char paramChar)
  {
    return Character.digit(paramChar, 10);
  }

  public byte[] lnxfcn(String paramString1, String paramString2, String paramString3)
    throws SQLException
  {
    if ((paramString3 != null) && (!paramString3.equals("AMERICAN_AMERICAN")))
    {
      throw new SQLException(CoreException.getMessage((byte)12));
    }

    LnxLibThinFormat localLnxLibThinFormat = new LnxLibThinFormat();
    localLnxLibThinFormat.parseFormat(paramString2);

    localLnxLibThinFormat.LNXNFRDX = true;

    int i = localLnxLibThinFormat.lnxnflhd;
    int j = localLnxLibThinFormat.lnxnfrhd;
    int k = localLnxLibThinFormat.lnxnfsiz;
    int m = localLnxLibThinFormat.lnxnfzld;

    if ((localLnxLibThinFormat.LNXNFFDA | localLnxLibThinFormat.LNXNFFED | localLnxLibThinFormat.LNXNFFRN | localLnxLibThinFormat.LNXNFFTM))
    {
      throw new SQLException(CoreException.getMessage((byte)5));
    }

    if ((localLnxLibThinFormat.LNXNFFRC | localLnxLibThinFormat.LNXNFFCH | localLnxLibThinFormat.LNXNFFCT))
    {
      throw new SQLException(CoreException.getMessage((byte)12));
    }

    char[] arrayOfChar1 = paramString1.toCharArray();
    int n = 0;
    int i1 = arrayOfChar1.length - 1;

    int i2 = 0;

    while ((n <= i1) && (Character.isSpaceChar(arrayOfChar1[n])))
    {
      n++;
      i2++;
    }

    if ((localLnxLibThinFormat.LNXNFFBL) && (i2 == k) && (i2 == arrayOfChar1.length))
    {
      return NUMBER._makeZero();
    }

    if (n > i1)
    {
      throw new SQLException(CoreException.getMessage((byte)14));
    }

    char[] arrayOfChar2 = new char['ÿ'];
    int i3 = 0;

    if (localLnxLibThinFormat.LNXNFFSH)
    {
      if ((arrayOfChar1[n] != '-') && (arrayOfChar1[n] != '+'))
      {
        throw new SQLException(CoreException.getMessage((byte)14));
      }

      arrayOfChar2[(i3++)] = arrayOfChar1[(n++)];
    }
    else if (localLnxLibThinFormat.LNXNFFPR)
    {
      if (arrayOfChar1[n] == '<')
      {
        arrayOfChar2[(i3++)] = '-';
        n++;
      }
      else
      {
        arrayOfChar2[(i3++)] = '+';
      }
    }
    else if (localLnxLibThinFormat.LNXNFFPT)
    {
      if (arrayOfChar1[n] == '(')
      {
        arrayOfChar2[(i3++)] = '-';
        n++;
      }
      else
      {
        arrayOfChar2[(i3++)] = '+';
      }
    }
    else if ((!localLnxLibThinFormat.LNXNFFMI) && (!localLnxLibThinFormat.LNXNFFST))
    {
      if (arrayOfChar1[n] == '-')
      {
        arrayOfChar2[(i3++)] = arrayOfChar1[(n++)];
      }

    }
    else
    {
      i3++;
    }

    if (n > i1)
    {
      throw new SQLException(CoreException.getMessage((byte)14));
    }

    if (localLnxLibThinFormat.LNXNFFDS)
    {
      if (arrayOfChar1[n] != '$')
      {
        throw new SQLException(CoreException.getMessage((byte)14));
      }

      n++;

      if (n > i1)
      {
        throw new SQLException(CoreException.getMessage((byte)14));
      }

    }
    else if (localLnxLibThinFormat.LNXNFFCH)
    {
      throw new SQLException(CoreException.getMessage((byte)12));
    }

    byte[] arrayOfByte = new byte[40];
    int i4 = 0;

    int i5 = 0;

    int i6 = 0;
    int i7 = 0;
    int i8;
    while (n <= i1)
    {
      if ((Character.isDigit(arrayOfChar1[n])) || ((localLnxLibThinFormat.LNXNFFHX) && (((arrayOfChar1[n] >= 'a') && (arrayOfChar1[n] <= 'f')) || ((arrayOfChar1[n] >= 'A') && (arrayOfChar1[n] <= 'F')))))
      {
        arrayOfChar2[(i3++)] = arrayOfChar1[(n++)];
        i6++;
      }
      else if (i7 == 0)
      {
        if (localLnxLibThinFormat.LNXNFRDX)
        {
          if (arrayOfChar1[n] == ',')
          {
            n++;
            arrayOfByte[(i4++)] = ((byte)i6);
          }
          else if (arrayOfChar1[n] == '.')
          {
            if ((i6 > i) || (i6 < m))
            {
              throw new SQLException(CoreException.getMessage((byte)14));
            }

            i6 = i - i6;

            i4 = 0;
            while (localLnxLibThinFormat.lnxnfgps[i5] != 0)
            {
              i8 = (byte)(localLnxLibThinFormat.lnxnfgps[i5] & 0x7F);

              if (i8 > i6)
              {
                if (arrayOfByte[i4] != i8 - i6)
                {
                  throw new SQLException(CoreException.getMessage((byte)14));
                }

                i4++;
              }

              i5++;
            }

            i6 = 0;
            i7 = 1;
            arrayOfChar2[i3] = '.';
            i3++;
            n++;
          }

        }
        else
        {
          throw new SQLException(CoreException.getMessage((byte)12));
        }

      }
      else if (((arrayOfChar1[n] == 'E') || (arrayOfChar1[n] == 'e')) && (localLnxLibThinFormat.LNXNFFSN))
      {
        if ((i6 <= 0) && (i7 == 0))
        {
          throw new SQLException(CoreException.getMessage((byte)14));
        }

        arrayOfChar2[(i3++)] = arrayOfChar1[(n++)];
        if (n > i1)
        {
          throw new SQLException(CoreException.getMessage((byte)14));
        }

        if ((arrayOfChar1[n] == '+') || (arrayOfChar1[n] == '-'))
        {
          arrayOfChar2[(i3++)] = arrayOfChar1[(n++)];
        }
        i8 = n;
        while ((n <= i1) && (Character.isDigit(arrayOfChar1[n])))
        {
          arrayOfChar2[(i3++)] = arrayOfChar1[(n++)];
        }

        if (i8 == n)
        {
          throw new SQLException(CoreException.getMessage((byte)14));
        }

      }
      else if (localLnxLibThinFormat.LNXNFFRC)
      {
        throw new SQLException(CoreException.getMessage((byte)12));
      }

    }

    if (i7 == 0)
    {
      i8 = i - i6;
      i4 = 0;

      while (localLnxLibThinFormat.lnxnfgps[i5] != 0)
      {
        int i9 = (byte)(localLnxLibThinFormat.lnxnfgps[i5] & 0x7F);
        if (i9 > i8)
        {
          if (arrayOfByte[i4] != i9 - i8)
          {
            throw new SQLException(CoreException.getMessage((byte)14));
          }

          i4++;
        }
        i5++;
      }

    }

    if (localLnxLibThinFormat.LNXNFFCT)
    {
      throw new SQLException(CoreException.getMessage((byte)12));
    }

    if (localLnxLibThinFormat.LNXNFFST)
    {
      if (n > i1)
      {
        throw new SQLException(CoreException.getMessage((byte)14));
      }

      if ((arrayOfChar1[n] != '-') && (arrayOfChar1[n] != '+'))
      {
        throw new SQLException(CoreException.getMessage((byte)14));
      }

      arrayOfChar2[0] = arrayOfChar1[n];
      n++;
    }
    else if (localLnxLibThinFormat.LNXNFFMI)
    {
      if (n > i1)
      {
        if ((localLnxLibThinFormat.LNXNFFIL) || (n != arrayOfChar1.length))
        {
          throw new SQLException(CoreException.getMessage((byte)14));
        }

        arrayOfChar2[0] = '+';
      }
      else
      {
        arrayOfChar2[0] = (arrayOfChar1[n] == '-' ? 45 : '+');
        n++;
      }
    }
    else if (localLnxLibThinFormat.LNXNFFPR)
    {
      if ((arrayOfChar1[n] == '>') && (arrayOfChar2[0] == '-'))
      {
        n++;
      }
    }
    else if (localLnxLibThinFormat.LNXNFFPT)
    {
      if ((n < arrayOfChar1.length) && (arrayOfChar1[n] == ')') && (arrayOfChar2[0] == '-'))
      {
        n++;
      }

    }

    if (n <= i1)
    {
      throw new SQLException(CoreException.getMessage((byte)14));
    }

    if (i7 != 0)
    {
      if (i6 > j)
      {
        throw new SQLException(CoreException.getMessage((byte)14));
      }

    }
    else
    {
      if (i6 > i)
      {
        throw new SQLException(CoreException.getMessage((byte)14));
      }

      if (i6 < m)
      {
        throw new SQLException(CoreException.getMessage((byte)14));
      }

    }

    if ((localLnxLibThinFormat.LNXNFF05) && (((i7 != 0) && (i6 == j)) || (j == 0)))
    {
      i3--;
      if ((arrayOfChar2[i3] != '0') && (arrayOfChar2[i3] != '5'))
      {
        throw new SQLException(CoreException.getMessage((byte)14));
      }

      i3++;
    }

    if (localLnxLibThinFormat.LNXNFFHX)
    {
      return lnxqh2n(arrayOfChar2);
    }

    return lnxcpn(new String(arrayOfChar2), false, 0, false, 0, paramString3);
  }

  public String lnxnfn(byte[] paramArrayOfByte, String paramString1, String paramString2)
    throws SQLException
  {
    char[] arrayOfChar1 = null;
    int i = 0;
    int j = 0;
    int k = 0;
    int m = 0;
    int n = 0;
    int i1 = 0;
    int i2 = 0;
    int i3 = 0;
    int i4 = 0;
    int i5 = 0;
    int i6 = 0;
    int i7 = 0;
    int i8 = 0;
    int i9 = 0;
    int i10 = 0;
    int i11 = 0;
    int i12 = 0;
    int i13 = 0;

    Object localObject = null;
    boolean[] arrayOfBoolean = new boolean[1];
    int i14 = 0;
    int i15 = 1;

    int i17 = 1;

    String str = null;

    LnxLibThinFormat localLnxLibThinFormat = new LnxLibThinFormat();
    localLnxLibThinFormat.parseFormat(paramString1);

    i = localLnxLibThinFormat.lnxnflhd;
    j = localLnxLibThinFormat.lnxnfrhd;
    k = localLnxLibThinFormat.lnxnfsiz;
    m = localLnxLibThinFormat.lnxnfzld;
    n = localLnxLibThinFormat.lnxnfztr;

    if ((localLnxLibThinFormat.LNXNFFRN) || (localLnxLibThinFormat.LNXNFFHX))
    {
      if (localLnxLibThinFormat.LNXNFFRN)
      {
        throw new SQLException(CoreException.getMessage((byte)1));
      }

      throw new SQLException(CoreException.getMessage((byte)1));
    }
    int i19;
    int i20;
    if (localLnxLibThinFormat.LNXNFFTM)
    {
      i19 = paramArrayOfByte.length;
      int i21;
      int i22;
      int i23;
      if (!NUMBER._isZero(paramArrayOfByte))
      {
        if (NUMBER._isPositive(paramArrayOfByte))
        {
          i20 = i19 - 1;
          i21 = 2 * ((paramArrayOfByte[0] & 0xFF) - 193) + ((paramArrayOfByte[1] & 0xFF) > 10 ? 1 : 0);

          i22 = 2 * i20 - ((paramArrayOfByte[1] & 0xFF) < 11 ? 1 : 0) - LnxqFirstDigit[paramArrayOfByte[i20]];
          i23 = 0;
        }
        else
        {
          if ((paramArrayOfByte[(i19 - 1)] & 0xFF) == 102) {
            i19--;
          }
          i20 = i19 - 1;
          i21 = 2 * (62 - paramArrayOfByte[0]) + ((paramArrayOfByte[1] & 0xFF) < 92 ? 1 : 0);

          i22 = 2 * i20 - ((paramArrayOfByte[1] & 0xFF) > 91 ? 1 : 0) - LnxqFirstDigit[paramArrayOfByte[i20]];
          i23 = 1;
        }
      }
      else
      {
        i21 = 0;
        i22 = 1;
        i23 = 0;
      }

      if (i21 >= 0)
      {
        i23 += (i22 > i21 + 1 ? i22 + 1 : i21 + 1);
      }
      else
      {
        i23 += -(i21 + 1) + i22 + 1;
      }

      if ((!localLnxLibThinFormat.LNXNFFSN) && (i23 > 64))
      {
        localLnxLibThinFormat.LNXNFFSN = true;
      }

      if (localLnxLibThinFormat.LNXNFFSN)
      {
        i = 1;
        j = i22 - 1;
        k = i22 > 1 ? i22 + 7 : 7;
      }
      else
      {
        i = i21 + 1 > 0 ? i21 + 1 : 0;
        j = i22 - (i21 + 1) > 0 ? i22 - (i21 + 1) : 0;
        k = j != 0 ? i + j + 2 : i + 1;
      }

      if (j == 0)
      {
        localLnxLibThinFormat.LNXNFNRD = true;
      }

    }

    int i18 = (paramArrayOfByte[0] & 0xFF) >= 128 ? 1 : 0;
    byte[] arrayOfByte;
    if (localLnxLibThinFormat.LNXNFFSN)
    {
      arrayOfByte = lnxfpr(paramArrayOfByte, j + i);
    }
    else
    {
      arrayOfByte = lnxsca(paramArrayOfByte, i, j, arrayOfBoolean);

      if (arrayOfBoolean[0] != 0)
      {
        throw new SQLException(CoreException.getMessage((byte)4));
      }

    }

    arrayOfChar1 = new char[64];
    i5 = 0;

    if (NUMBER._isZero(arrayOfByte))
    {
      if (localLnxLibThinFormat.LNXNFFBL)
      {
        if (localLnxLibThinFormat.LNXNFFIL)
        {
          arrayOfChar1 = new char[k];

          for (i19 = 0; i19 < k; i19++) {
            arrayOfChar1[i19] = ' ';
          }
          return new String(arrayOfChar1);
        }

        return null;
      }

      i15 = i18;
      i14 = 0;
      i2 = 0;
      i4 = 0;
      i1 = i2;

      i3 = (i > 0) && (n == 0) ? 1 : 0;
    }
    else {
      if ((NUMBER._isNegInf(arrayOfByte)) || (NUMBER._isPosInf(arrayOfByte)))
      {
        throw new SQLException(CoreException.getMessage((byte)4));
      }

      i4 = arrayOfByte.length - 1;
      i15 = (arrayOfByte[0] & 0x80) != 0 ? 1 : 0;

      if (i15 == 0)
      {
        localObject = new byte[i4];
        if (arrayOfByte[i4] == 102) {
          i4--;
        }
        for (i19 = 1; i19 <= i4; i19++) {
          localObject[i19] = ((byte)(102 - arrayOfByte[i19]));
        }
        localObject[0] = ((byte)(arrayOfByte[0] ^ 0xFFFFFFFF));
      }
      else
      {
        localObject = arrayOfByte;
      }

      int i16 = (localObject[i4] & 0xFF) % 10 == 1 ? 1 : 0;
      i1 = 2 * ((localObject[0] & 0xFF) - 192);

      i6 = 1;
      if ((i17 = (localObject[i6] & 0xFF) < 11 ? 1 : 0) != 0)
      {
        i10 = ((localObject[i6] & 0xFF) - 1) / 10;
      }

      if (localLnxLibThinFormat.LNXNFFSN)
      {
        i2 = 2 * i4 - (i17 != 0 ? 1 : 0) - (i16 != 0 ? 1 : 0) - (i3 = 1);
        i1 -= (i17 != 0 ? 1 : 0) + 1;

        if ((i14 = i1 < 0 ? 1 : 0) != 0)
        {
          i1 = -i1;
        }
        if ((i1 < 100) && (localLnxLibThinFormat.LNXNFFIL))
        {
          arrayOfChar1[i5] = ' ';
          i5++;
        }
      }
      else
      {
        i2 = 2 * i4 - i1 - (i16 != 0 ? 1 : 0);
        i3 = i1 - (i17 != 0 ? 1 : 0);

        if ((localLnxLibThinFormat.LNXNFF05) && ((j == 0) || (i2 == j)))
        {
          i19 = i6 + i4 - 1;

          if (i16 == 0)
          {
            i20 = (localObject[i19] & 0xFF) % 10;
            i20 = i20 != 0 ? i20 - 1 : 9;
            if (i20 <= 2)
            {
              localObject[i19] = ((byte)((localObject[i19] & 0xFF) - i20));
            }
            else if (i20 <= 7)
            {
              localObject[i19] = ((byte)((localObject[i19] & 0xFF) + (5 - i20)));
            }

          }
          else
          {
            i20 = (localObject[i19] & 0xFF) / 10;
            if (i20 <= 2)
            {
              localObject[i19] = 1;
            }
            else if (i20 <= 7)
            {
              localObject[i19] = 51;
            }
          }

          if (i20 > 7)
          {
            i6--;
            localObject = lnxrou((byte[])localObject, i2 - 1);

            i4 = localObject.length - 1;
            i16 = (localObject[i6] & 0xFF) % 10 == 1 ? 1 : 0;
            i1 = 2 * ((localObject[i6] & 0xFF) - 192);

            i6++;

            if ((i17 = (localObject[i6] & 0xFF) < 11 ? 1 : 0) != 0)
            {
              i10 = ((localObject[i6] & 0xFF) - 1) / 10;
            }

            i2 = 2 * i4 - i1 - (i16 != 0 ? 1 : 0);
            i3 = i1 - (i17 != 0 ? 1 : 0);

            if (i3 > i)
            {
              throw new SQLException(CoreException.getMessage((byte)4));
            }

          }

        }

      }

    }

    i11 = i - (m > i3 ? m : i3);

    if ((i11 != 0) && (localLnxLibThinFormat.LNXNFFIL))
    {
      i19 = i11 + i5;
      for (i20 = 0; i20 < i19; i5++) {
        arrayOfChar1[i20] = ' ';

        i20++;
      }

    }

    if ((!localLnxLibThinFormat.LNXNFFMI) && (!localLnxLibThinFormat.LNXNFFST))
    {
      if (i15 != 0)
      {
        i19 = localLnxLibThinFormat.LNXNFFSH ? 43 : 32;
      }
      else
      {
        i19 = localLnxLibThinFormat.LNXNFFPT ? 40 : localLnxLibThinFormat.LNXNFFPR ? 60 : 45;
      }

      if ((localLnxLibThinFormat.LNXNFFIL) || (i19 != 32))
      {
        arrayOfChar1[i5] = i19;
        i5++;
      }
    }

    if (localLnxLibThinFormat.LNXNFFIC)
    {
      str = "USD";
    }
    else if (localLnxLibThinFormat.LNXNFFUN)
    {
      str = "$";
    }
    else
    {
      str = "$";
    }

    if (localLnxLibThinFormat.LNXNFFDS)
    {
      arrayOfChar1[i5] = '$';
      i5++;
    }
    else if (localLnxLibThinFormat.LNXNFFCH)
    {
      for (i19 = 0; i19 < str.length(); i5++) {
        arrayOfChar1[i5] = str.charAt(i19);

        i19++;
      }

    }

    i7 = 0;
    while ((i8 = localLnxLibThinFormat.lnxnfgps[i7] & 0x7F) != 0)
    {
      if (i8 > i11)
      {
        break;
      }
      i7++;
    }

    if ((i9 = m - (i3 > 0 ? i3 : 0)) > 0)
    {
      while (i9 > 0)
      {
        arrayOfChar1[i5] = '0';
        i5++;
        i11++;

        while (i11 == i8)
        {
          arrayOfChar1[i5] = ',';
          i5++;
          i7++;

          i8 = localLnxLibThinFormat.lnxnfgps[i7] & 0x7F;
        }
        i9--;
      }
    }

    if (i3 > 0)
    {
      while ((i3 > 0) && (i4 != 0))
      {
        if (i17 != 0)
        {
          i13 = (localObject[i6] & 0xFF) - 1 - i10 * 10;
          i6++;
          i4--;
        }
        else
        {
          i13 = ((localObject[i6] & 0xFF) - 1) / 10;
          i10 = i13;
        }

        arrayOfChar1[i5] = this.lnx_chars[i13];
        i5++;
        i11++;

        while (i11 == i8)
        {
          arrayOfChar1[i5] = ',';
          i5++;
          i7++;
          i8 = localLnxLibThinFormat.lnxnfgps[i7] & 0x7F;
        }
        i3--;
        i17 = i17 == 0 ? 1 : 0;
      }

      while (i3 > 0)
      {
        arrayOfChar1[i5] = '0';
        i5++;
        i11++;

        while (i11 == i8)
        {
          arrayOfChar1[i5] = ',';
          i5++;
          i7++;
          i8 = localLnxLibThinFormat.lnxnfgps[i7] & 0x7F;
        }

        i3--;
      }

    }

    if (!localLnxLibThinFormat.LNXNFNRD)
    {
      if (localLnxLibThinFormat.LNXNFRDX)
      {
        arrayOfChar1[i5] = '.';
        i5++;
      }
      else if (localLnxLibThinFormat.LNXNFFRC)
      {
        for (i19 = 0; i19 < str.length(); i5++) {
          arrayOfChar1[i5] = str.charAt(i19);

          i19++;
        }

      }
      else
      {
        arrayOfChar1[i5] = '.';
        i5++;
      }

    }

    i11 = 0;
    if ((i12 = n - (i2 > 0 ? i2 : 0)) < 0)
    {
      i12 = 0;
    }

    if (i3 != 0)
    {
      i3 = -i3;
      i2 -= i3;
      while (i3 != 0)
      {
        arrayOfChar1[i5] = '0';
        i5++;
        i3--;
      }

    }

    while ((i4 != 0) && (i2 != 0))
    {
      if (i17 != 0)
      {
        i13 = (localObject[i6] & 0xFF) - 1 - i10 * 10;
        i6++;
        i4--;
      }
      else
      {
        i13 = ((localObject[i6] & 0xFF) - 1) / 10;
        i10 = i13;
      }

      arrayOfChar1[i5] = this.lnx_chars[i13];
      i5++;
      i2--;
      i17 = i17 == 0 ? 1 : 0;
    }

    while (i12 != 0)
    {
      arrayOfChar1[i5] = '0';
      i5++;
      i12--;
    }

    if (localLnxLibThinFormat.LNXNFFSN)
    {
      arrayOfChar1[i5] = 'E';
      i5++;

      arrayOfChar1[i5] = (i14 != 0 ? 45 : '+');
      i5++;

      if (i1 > 99)
      {
        arrayOfChar1[i5] = '1';
        i5++;
        i1 -= 100;
      }

      arrayOfChar1[i5] = this.lnx_chars[(i1 / 10)];
      i5++;

      arrayOfChar1[i5] = this.lnx_chars[(i1 % 10)];
      i5++;
    }

    if (localLnxLibThinFormat.LNXNFFCT)
    {
      for (i19 = 0; i19 < str.length(); i5++) {
        arrayOfChar1[i5] = str.charAt(i19);

        i19++;
      }

    }

    if (i15 != 0)
    {
      if (localLnxLibThinFormat.LNXNFFST)
      {
        arrayOfChar1[i5] = '+';
        i5++;
      }
      else if (((localLnxLibThinFormat.LNXNFFPR) || (localLnxLibThinFormat.LNXNFFMI) || (localLnxLibThinFormat.LNXNFFPT)) && (localLnxLibThinFormat.LNXNFFIL))
      {
        arrayOfChar1[i5] = ' ';
        i5++;
      }

    }
    else if (localLnxLibThinFormat.LNXNFFPR)
    {
      arrayOfChar1[i5] = '>';
      i5++;
    }
    else if (localLnxLibThinFormat.LNXNFFPT)
    {
      arrayOfChar1[i5] = ')';
      i5++;
    }
    else if ((localLnxLibThinFormat.LNXNFFMI) || (localLnxLibThinFormat.LNXNFFST))
    {
      arrayOfChar1[i5] = '-';
      i5++;
    }

    i12 = i5;

    if ((localLnxLibThinFormat.LNXNFFIL) && (i12 != k))
    {
      i9 = k - i12;

      char[] arrayOfChar2 = new char[i9];
      for (i20 = 0; i20 < i9; i20++) {
        arrayOfChar2[i20] = ' ';
      }
      StringBuffer localStringBuffer = new StringBuffer();
      localStringBuffer.append(arrayOfChar2);
      localStringBuffer.append(arrayOfChar1, 0, i12);

      return localStringBuffer.toString();
    }

    return new String(arrayOfChar1, 0, i12);
  }

  public String lnxnuc(byte[] paramArrayOfByte, int paramInt, String paramString)
    throws SQLException
  {
    byte[] arrayOfByte1 = new byte[22];

    int i13 = 0;

    if (paramString != null)
    {
      throw new SQLException(CoreException.getMessage((byte)12));
    }

    char[] arrayOfChar1 = this.lnx_chars;
    int i5 = 46;

    if (paramInt == 0)
    {
      throw new SQLException(CoreException.getMessage((byte)13));
    }
    int k;
    if ((k = paramInt >= 0 ? 1 : 0) == 0)
    {
      paramInt = -paramInt;
    }

    char[] arrayOfChar2 = new char[paramInt];
    int i7;
    byte[][] arrayOfByte;
    int i6;
    if ((i7 = !NUMBER._isPositive(paramArrayOfByte) ? 1 : 0) != 0)
    {
      arrayOfByte = LnxqComponents_N;
      i6 = paramInt - 1;
    }
    else
    {
      arrayOfByte = LnxqComponents_P;
      i6 = paramInt;
    }

    int i19 = 1;

    while (i19 != 0)
    {
      int m = k;

      int i20 = paramArrayOfByte.length;
      int i4;
      int i18;
      if (i20 == 1)
      {
        if ((paramArrayOfByte[0] & 0xFF) == 128)
        {
          if (m != 0)
          {
            i4 = paramInt - 1;
            arrayOfChar2[i4] = arrayOfChar1[0];
          }
          else
          {
            if (paramInt < 5)
            {
              throw new SQLException(CoreException.getMessage((byte)13));
            }

            i4 = paramInt - 5;
            arrayOfChar2[i4] = arrayOfChar1[0];
            arrayOfChar2[(i4 + 1)] = arrayOfChar1[41];
            arrayOfChar2[(i4 + 2)] = arrayOfChar1[10];
            arrayOfChar2[(i4 + 3)] = arrayOfChar1[0];
            arrayOfChar2[(i4 + 4)] = arrayOfChar1[0];
          }

          if (i4 != 0) {
            for (i18 = 0; i18 < i4; i18++) arrayOfChar2[i18] = arrayOfChar1[12];
          }
          return new String(arrayOfChar2);
        }

        if (paramInt < 2)
        {
          throw new SQLException(CoreException.getMessage((byte)13));
        }

        i4 = paramInt - 2;
        arrayOfChar2[i4] = arrayOfChar1[11];
        arrayOfChar2[(i4 + 1)] = arrayOfChar1[21];

        if (i4 != 0) {
          for (i18 = 0; i18 < i4; i18++) arrayOfChar2[i18] = arrayOfChar1[12];
        }
        return new String(arrayOfChar2);
      }

      if ((i20 == 2) && ((paramArrayOfByte[0] & 0xFF) == 255) && ((paramArrayOfByte[1] & 0xFF) == 101))
      {
        i4 = paramInt - 1;
        arrayOfChar2[i4] = arrayOfChar1[21];

        if (i4 != 0) {
          for (i18 = 0; i18 < i4; i18++) arrayOfChar2[i18] = arrayOfChar1[12];
        }
        return new String(arrayOfChar2);
      }
      int i2;
      int i8;
      int i3;
      if (i7 != 0)
      {
        if (paramArrayOfByte[(i20 - 1)] == 102) i20--;
        i2 = i20 - 1;
        i8 = 2 * (62 - (paramArrayOfByte[0] & 0xFF)) + ((paramArrayOfByte[1] & 0xFF) < 92 ? 1 : 0);

        i3 = 2 * i2 - ((paramArrayOfByte[1] & 0xFF) > 91 ? 1 : 0) - LnxqFirstDigit[paramArrayOfByte[i2]];
      }
      else
      {
        i2 = i20 - 1;
        i8 = 2 * ((paramArrayOfByte[0] & 0xFF) - 193) + ((paramArrayOfByte[1] & 0xFF) > 10 ? 1 : 0);

        i3 = 2 * i2 - ((paramArrayOfByte[1] & 0xFF) < 11 ? 1 : 0) - LnxqFirstDigit[paramArrayOfByte[i2]];
      }

      if (m != 0)
      {
        if (i8 >= 0)
        {
          m = i8 < i6 ? 1 : 0;
        }
        else
        {
          m = (i8 >= -5) || (i3 - i8 <= i6) || (i6 <= 6) ? 1 : 0;
        }
      }
      int i9;
      int i12;
      int i14;
      int i15;
      int i16;
      int i17;
      int i1;
      int i11;
      int n;
      if (m != 0)
      {
        if (i8 >= 0)
        {
          i9 = i6 <= i8 + 1 ? i6 : i6 - 1;
        }
        else
        {
          i9 = i6 + i8;
        }

        if (i9 < i3)
        {
          paramArrayOfByte = lnxfpr(paramArrayOfByte, i9);
        }
        else
        {
          i19 = 0;

          if (i8 >= 0)
          {
            if (i3 > i8 + 1)
            {
              i4 = i6 - (i3 + 1);
              i12 = 1;
              i13 = 0;
              i14 = (i8 & 0x1) > 0 ? 0 : 1;
              i15 = (i3 - i8 & 0x1) > 0 ? 0 : 1;
              int i21 = 2147483647;
              i16 = i8 + 1 & i21 - 1;
              i17 = i3 - (i8 + 1) & i21 - 1;

              i1 = i4;
            }
            else
            {
              i4 = i6 - (i8 + 1);
              i11 = i8 + 1 - i3;
              i12 = 0;
              i14 = (i8 & 0x1) > 0 ? 0 : 1;
              i15 = (i11 & 0x1) > 0 ? 1 : 0;
              i16 = i3 - (i14 != 0 ? 1 : 0) - (i15 != 0 ? 1 : 0);
              i17 = 0;

              if (i11 != 0)
              {
                n = i4 + (i7 != 0 ? 1 : 0) + i3;
                for (i18 = 0; i18 < i11; i18++) {
                  arrayOfChar2[(n + i18)] = arrayOfChar1[0];
                }
              }

              i1 = i4;
            }

          }
          else
          {
            i4 = i6 - (i3 - i8);
            int i10 = -(i8 + 1);
            i12 = 0;
            i14 = (i10 & 0x1) > 0 ? 1 : 0;
            i15 = (i10 + i3 & 0x1) > 0 ? 1 : 0;
            i16 = 0;
            i17 = i3 - (i14 != 0 ? 1 : 0) - (i15 != 0 ? 1 : 0);

            n = i4;

            if (i7 != 0)
            {
              arrayOfChar2[n] = arrayOfChar1[11];
              n++;
              i7 = 0;
            }

            arrayOfChar2[n] = i5;
            n++;

            if (i10 != 0)
            {
              for (i18 = 0; i18 < i10; i18++)
                arrayOfChar2[(n + i18)] = arrayOfChar1[0];
              n += i10;
            }

            i1 = n;
          }

          if (i4 != 0)
          {
            for (i18 = 0; i18 < i4; i18++) arrayOfChar2[i18] = arrayOfChar1[12];
          }

        }

      }
      else
      {
        i9 = i6 - ((i8 > 99) || (i8 < -99) ? 6 : 5);

        if (i9 < 2)
        {
          throw new SQLException(CoreException.getMessage((byte)13));
        }

        if (i9 < i3)
        {
          paramArrayOfByte = lnxfpr(paramArrayOfByte, i9);
        }
        else
        {
          i19 = 0;

          if (i3 == 1)
          {
            i11 = i9 - 1;
            i12 = 1;
            i13 = (i8 & 0x1) > 0 ? 1 : 0;
            i14 = i13 == 0 ? 1 : 0;
            i15 = 0;
            i16 = 0;
            i17 = 0;
          }
          else
          {
            i11 = i9 - i3;
            i12 = 1;
            i13 = (i8 & 0x1) > 0 ? 1 : 0;
            i14 = i13 == 0 ? 1 : 0;
            i15 = i13 == ((i3 & 0x1) > 0 ? 1 : 0) ? 1 : 0;
            i16 = 0;
            i17 = i3 - (i14 != 0 ? 1 : 2) - (i15 != 0 ? 1 : 0);
          }

          n = (i7 != 0 ? 1 : 0) + 1 + i3;

          if (i11 != 0)
          {
            for (i18 = 0; i18 < i11; i18++)
              arrayOfChar2[(n + i18)] = arrayOfChar1[0];
            n += i11;
          }

          if (i8 < 0)
          {
            i8 = -i8;
            arrayOfChar2[n] = arrayOfChar1[41];
            arrayOfChar2[(n + 1)] = arrayOfChar1[11];
          }
          else
          {
            arrayOfChar2[n] = arrayOfChar1[41];
            arrayOfChar2[(n + 1)] = arrayOfChar1[10];
          }

          if (i8 > 99)
          {
            arrayOfChar2[(n + 2)] = arrayOfChar1[1];
            arrayOfByte1[0] = ((byte)(i8 - 99));
            arrayOfChar2[(n + 3)] = arrayOfChar1[LnxqComponents_P[(arrayOfByte1[0] & 0xFF)][0]];
            arrayOfChar2[(n + 4)] = arrayOfChar1[LnxqComponents_P[(arrayOfByte1[0] & 0xFF)][1]];
          }
          else
          {
            arrayOfByte1[0] = ((byte)(i8 + 1));
            arrayOfChar2[(n + 2)] = arrayOfChar1[LnxqComponents_P[(arrayOfByte1[0] & 0xFF)][0]];
            arrayOfChar2[(n + 3)] = arrayOfChar1[LnxqComponents_P[(arrayOfByte1[0] & 0xFF)][1]];
          }

          i1 = 0;

          int i = 1;
          n = i1;

          if (i7 != 0)
          {
            arrayOfChar2[n] = arrayOfChar1[11];
            n++;
          }

          if (i14 != 0)
          {
            arrayOfChar2[n] = arrayOfChar1[arrayOfByte[paramArrayOfByte[i]][1]];
            n++;
            i++;
          }
          int j;
          if (i16 != 0)
          {
            j = n + i16;
            while (n < j)
            {
              arrayOfChar2[n] = arrayOfChar1[arrayOfByte[paramArrayOfByte[i]][0]];
              n++;
              arrayOfChar2[n] = arrayOfChar1[arrayOfByte[paramArrayOfByte[i]][1]];
              n++;
              i++;
            }

          }

          if (i12 != 0)
          {
            if (i13 != 0)
            {
              arrayOfChar2[n] = arrayOfChar1[arrayOfByte[paramArrayOfByte[i]][0]];
              n++;
              arrayOfChar2[n] = i5;
              n++;
              arrayOfChar2[n] = arrayOfChar1[arrayOfByte[paramArrayOfByte[i]][1]];
              n++;
              i++;
            }
            else
            {
              arrayOfChar2[n] = i5;
              n++;
            }

          }

          if (i17 != 0)
          {
            j = n + i17;
            while (n < j)
            {
              arrayOfChar2[n] = arrayOfChar1[arrayOfByte[paramArrayOfByte[i]][0]];
              n++;
              arrayOfChar2[n] = arrayOfChar1[arrayOfByte[paramArrayOfByte[i]][1]];
              n++;
              i++;
            }

          }

          if (i15 != 0)
          {
            arrayOfChar2[n] = arrayOfChar1[arrayOfByte[paramArrayOfByte[i]][0]];
            n++;
            i++;
          }
        }
      }
    }
    return new String(arrayOfChar2); } 
  public byte[] lnxren(double paramDouble) throws SQLException { // Byte code:
    //   0: bipush 20
    //   2: newarray byte
    //   4: astore_3
    //   5: iconst_0
    //   6: istore 4
    //   8: dload_1
    //   9: dconst_0
    //   10: dcmpg
    //   11: ifge +7 -> 18
    //   14: iconst_0
    //   15: goto +4 -> 19
    //   18: iconst_1
    //   19: istore 6
    //   21: dload_1
    //   22: invokestatic 146	java/lang/Math:abs	(D)D
    //   25: dstore_1
    //   26: dload_1
    //   27: dconst_1
    //   28: dcmpg
    //   29: ifge +75 -> 104
    //   32: iconst_0
    //   33: istore 10
    //   35: iload 10
    //   37: bipush 8
    //   39: if_icmpge +47 -> 86
    //   42: getstatic 147	oracle/sql/LnxLibThin:powerTable	[[D
    //   45: iload 10
    //   47: aaload
    //   48: iconst_2
    //   49: daload
    //   50: dload_1
    //   51: dcmpl
    //   52: iflt +28 -> 80
    //   55: iload 4
    //   57: getstatic 147	oracle/sql/LnxLibThin:powerTable	[[D
    //   60: iload 10
    //   62: aaload
    //   63: iconst_0
    //   64: daload
    //   65: d2i
    //   66: isub
    //   67: istore 4
    //   69: dload_1
    //   70: getstatic 147	oracle/sql/LnxLibThin:powerTable	[[D
    //   73: iload 10
    //   75: aaload
    //   76: iconst_1
    //   77: daload
    //   78: dmul
    //   79: dstore_1
    //   80: iinc 10 1
    //   83: goto -48 -> 35
    //   86: dload_1
    //   87: dconst_1
    //   88: dcmpg
    //   89: ifge +69 -> 158
    //   92: iinc 4 255
    //   95: dload_1
    //   96: ldc2_w 74
    //   99: dmul
    //   100: dstore_1
    //   101: goto +57 -> 158
    //   104: iconst_0
    //   105: istore 10
    //   107: iload 10
    //   109: bipush 8
    //   111: if_icmpge +47 -> 158
    //   114: getstatic 147	oracle/sql/LnxLibThin:powerTable	[[D
    //   117: iload 10
    //   119: aaload
    //   120: iconst_1
    //   121: daload
    //   122: dload_1
    //   123: dcmpg
    //   124: ifgt +28 -> 152
    //   127: iload 4
    //   129: getstatic 147	oracle/sql/LnxLibThin:powerTable	[[D
    //   132: iload 10
    //   134: aaload
    //   135: iconst_0
    //   136: daload
    //   137: d2i
    //   138: iadd
    //   139: istore 4
    //   141: dload_1
    //   142: getstatic 147	oracle/sql/LnxLibThin:powerTable	[[D
    //   145: iload 10
    //   147: aaload
    //   148: iconst_2
    //   149: daload
    //   150: dmul
    //   151: dstore_1
    //   152: iinc 10 1
    //   155: goto -48 -> 107
    //   158: iload 4
    //   160: bipush 62
    //   162: if_icmple +15 -> 177
    //   165: new 20	java/sql/SQLException
    //   168: dup
    //   169: iconst_3
    //   170: invokestatic 21	oracle/core/lmx/CoreException:getMessage	(B)Ljava/lang/String;
    //   173: invokespecial 22	java/sql/SQLException:<init>	(Ljava/lang/String;)V
    //   176: athrow
    //   177: iload 4
    //   179: bipush 191
    //   181: if_icmpge +15 -> 196
    //   184: new 20	java/sql/SQLException
    //   187: dup
    //   188: iconst_2
    //   189: invokestatic 21	oracle/core/lmx/CoreException:getMessage	(B)Ljava/lang/String;
    //   192: invokespecial 22	java/sql/SQLException:<init>	(Ljava/lang/String;)V
    //   195: athrow
    //   196: dload_1
    //   197: ldc2_w 58
    //   200: dcmpl
    //   201: iflt +7 -> 208
    //   204: iconst_0
    //   205: goto +4 -> 209
    //   208: iconst_1
    //   209: istore 5
    //   211: bipush 8
    //   213: istore 7
    //   215: iconst_0
    //   216: istore 9
    //   218: dload_1
    //   219: d2i
    //   220: i2b
    //   221: istore 8
    //   223: iload 9
    //   225: iload 7
    //   227: if_icmpge +30 -> 257
    //   230: aload_3
    //   231: iload 9
    //   233: iload 8
    //   235: bastore
    //   236: dload_1
    //   237: iload 8
    //   239: i2d
    //   240: dsub
    //   241: ldc2_w 74
    //   244: dmul
    //   245: dstore_1
    //   246: dload_1
    //   247: d2i
    //   248: i2b
    //   249: istore 8
    //   251: iinc 9 1
    //   254: goto -31 -> 223
    //   257: bipush 7
    //   259: istore 9
    //   261: iload 5
    //   263: ifeq +22 -> 285
    //   266: iload 8
    //   268: bipush 50
    //   270: if_icmplt +76 -> 346
    //   273: aload_3
    //   274: iload 9
    //   276: dup2
    //   277: baload
    //   278: iconst_1
    //   279: iadd
    //   280: i2b
    //   281: bastore
    //   282: goto +64 -> 346
    //   285: iload 4
    //   287: bipush 62
    //   289: if_icmpne +40 -> 329
    //   292: aload_3
    //   293: iload 9
    //   295: baload
    //   296: iconst_5
    //   297: iadd
    //   298: bipush 10
    //   300: idiv
    //   301: bipush 10
    //   303: imul
    //   304: bipush 100
    //   306: if_icmpne +23 -> 329
    //   309: aload_3
    //   310: iload 9
    //   312: aload_3
    //   313: iload 9
    //   315: baload
    //   316: iconst_5
    //   317: isub
    //   318: bipush 10
    //   320: idiv
    //   321: bipush 10
    //   323: imul
    //   324: i2b
    //   325: bastore
    //   326: goto +20 -> 346
    //   329: aload_3
    //   330: iload 9
    //   332: aload_3
    //   333: iload 9
    //   335: baload
    //   336: iconst_5
    //   337: iadd
    //   338: bipush 10
    //   340: idiv
    //   341: bipush 10
    //   343: imul
    //   344: i2b
    //   345: bastore
    //   346: aload_3
    //   347: iload 9
    //   349: baload
    //   350: bipush 100
    //   352: if_icmpne +39 -> 391
    //   355: iload 9
    //   357: ifne +14 -> 371
    //   360: iinc 4 1
    //   363: aload_3
    //   364: iload 9
    //   366: iconst_1
    //   367: bastore
    //   368: goto +23 -> 391
    //   371: aload_3
    //   372: iload 9
    //   374: iconst_0
    //   375: bastore
    //   376: iinc 9 255
    //   379: aload_3
    //   380: iload 9
    //   382: dup2
    //   383: baload
    //   384: iconst_1
    //   385: iadd
    //   386: i2b
    //   387: bastore
    //   388: goto -42 -> 346
    //   391: bipush 7
    //   393: istore 9
    //   395: iload 9
    //   397: ifeq +23 -> 420
    //   400: aload_3
    //   401: iload 9
    //   403: baload
    //   404: ifne +16 -> 420
    //   407: iload 7
    //   409: iconst_1
    //   410: isub
    //   411: i2b
    //   412: istore 7
    //   414: iinc 9 255
    //   417: goto -22 -> 395
    //   420: iload 7
    //   422: iconst_1
    //   423: iadd
    //   424: newarray byte
    //   426: astore 11
    //   428: aload 11
    //   430: iconst_0
    //   431: iload 4
    //   433: i2b
    //   434: bastore
    //   435: aload_3
    //   436: iconst_0
    //   437: aload 11
    //   439: iconst_1
    //   440: iload 7
    //   442: invokestatic 4	java/lang/System:arraycopy	(Ljava/lang/Object;ILjava/lang/Object;II)V
    //   445: aload 11
    //   447: iload 6
    //   449: invokestatic 148	oracle/sql/NUMBER:_toLnxFmt	([BZ)[B
    //   452: areturn } 
  public byte[] lnxmin(long paramLong) { byte[] arrayOfByte1 = new byte[20];

    byte[] arrayOfByte2 = new byte[20];
    int k = 0;

    if (paramLong == 0L)
    {
      return NUMBER._makeZero();
    }

    boolean bool = paramLong >= 0L;

    for (int i = 0; paramLong != 0L; i++)
    {
      arrayOfByte1[i] = ((byte)(int)Math.abs(paramLong % 100L));
      paramLong /= 100L;
    }

    i--; int j = (byte)i;

    for (int m = j; k <= j; m--) {
      arrayOfByte2[k] = arrayOfByte1[m];

      k = (byte)(k + 1);
    }

    while (i > 0)
    {
      if (arrayOfByte2[(i--)] != 0) {
        break;
      }
      k = (byte)(k - 1);
    }

    byte[] arrayOfByte3 = new byte[k + 1];
    arrayOfByte3[0] = j;
    System.arraycopy(arrayOfByte2, 0, arrayOfByte3, 1, k);

    return NUMBER._toLnxFmt(arrayOfByte3, bool);
  }

  public double lnxnur(byte[] paramArrayOfByte)
  {
    double d1 = 0.0D;

    int j = 1;

    int i1 = 0;

    int i4 = factorTable.length;

    byte[] arrayOfByte = NUMBER._fromLnxFmt(paramArrayOfByte);

    int i2 = arrayOfByte[1] < 10 ? 1 : 0;

    double d3 = factorTable[0][0];
    double d4 = factorTable[0][0] - (i4 - 20);
    int i;
    int i3;
    if ((arrayOfByte[0] > d3) || (arrayOfByte[0] < d4))
    {
      if (arrayOfByte[0] > d3)
      {
        i = -1;
        i3 = (int)(arrayOfByte[0] - d3);
      }
      else
      {
        i = -1 + (i4 - 20);
        i3 = (int)(arrayOfByte[0] - d4);
      }
    }
    else
    {
      i = -1 + (int)(d3 - arrayOfByte[0]);
      i3 = 0;
    }

    int n = arrayOfByte.length - 1;

    if (i2 != 0 ? n > 8 : n >= 8)
    {
      n = 8;
      i1 = 1;
    }
    int m;
    double d2;
    switch (n % 4)
    {
    case 3:
      m = (arrayOfByte[1] * 100 + arrayOfByte[2]) * 100 + arrayOfByte[3];
      i += 3;
      d2 = factorTable[i][1];
      if (d2 < 1.0D)
        d1 = m / factorTable[i][2];
      else
        d1 = m * factorTable[i][1];
      j += 3;
      n -= 3;
      break;
    case 2:
      m = arrayOfByte[1] * 100 + arrayOfByte[2];
      i += 2;
      d2 = factorTable[i][1];
      if (d2 < 1.0D)
        d1 = m / factorTable[i][2];
      else
        d1 = m * factorTable[i][1];
      j += 2;
      n -= 2;
      break;
    case 1:
      m = arrayOfByte[1];
      i++;
      d2 = factorTable[i][1];
      if (d2 < 1.0D)
        d1 = m / factorTable[i][2];
      else
        d1 = m * factorTable[i][1];
      j++;
      n--;
      break;
    default:
      d1 = 0.0D;
    }

    while (n > 0)
    {
      m = ((arrayOfByte[j] * 100 + arrayOfByte[(j + 1)]) * 100 + arrayOfByte[(j + 2)]) * 100 + arrayOfByte[(j + 3)];

      i += 4;
      d2 = factorTable[i][1];
      if (d2 < 1.0D)
        d1 += m / factorTable[i][2];
      else
        d1 += m * factorTable[i][1];
      j += 4;
      n -= 4;
    }

    if (i1 != 0)
    {
      if (i2 != 0)
      {
        if (arrayOfByte[j] > 50)
        {
          m = 1;
          d1 += m * factorTable[i][1];
        }
      }
      else
      {
        j--;
        if (arrayOfByte[j] % 10 >= 5)
        {
          m = (arrayOfByte[j] / 10 + 1) * 10;
        }
        else
        {
          m = arrayOfByte[j] / 10 * 10;
        }
        m -= arrayOfByte[j];
        d1 += m * factorTable[i][1];
      }
    }

    if (i3 != 0)
    {
      int k = 0;
      while (i3 > 0)
      {
        if ((int)powerTable[k][0] <= i3)
        {
          i3 -= (int)powerTable[k][0];
          d1 *= powerTable[k][1];
        }
        k++;
      }
      while (i3 < 0)
      {
        if ((int)powerTable[k][0] <= -i3)
        {
          i3 += (int)powerTable[k][0];
          d1 *= powerTable[k][2];
        }
        k++;
      }
    }

    return NUMBER._isPositive(paramArrayOfByte) ? d1 : -d1;
  }

  public long lnxsni(byte[] paramArrayOfByte) throws SQLException
  {
    long l = 0L;
    byte[] arrayOfByte = NUMBER._fromLnxFmt(paramArrayOfByte);
    int i = arrayOfByte[0];
    int j = (byte)(arrayOfByte.length - 1);

    int k = j > i + 1 ? i + 1 : j;

    for (int m = 0; m < k; m++) {
      l = l * 100L + arrayOfByte[(m + 1)];
    }

    for (m = i - j; m >= 0; m--) {
      l *= 100L;
    }
    return NUMBER._isPositive(paramArrayOfByte) ? l : -l;
  }

  private byte[] lnxqh2n(char[] paramArrayOfChar)
  {
    int i = 0;
    int j = paramArrayOfChar.length;

    long[] arrayOfLong = new long[14];
    int k = 13;
    int m = 13;

    byte[] arrayOfByte1 = new byte[42];
    int i1 = 1;

    while ((j != 0) && (paramArrayOfChar[(j - 1)] == 0))
    {
      j--;
    }

    while ((j != 0) && (paramArrayOfChar[i] == '0'))
    {
      i++;
      j--;
    }

    if (j == 0)
    {
      return NUMBER._makeZero();
    }

    arrayOfLong[m] = 0L;

    switch (j % 3)
    {
    case 0:
      arrayOfLong[m] = LNXQH2N_DIGIT(paramArrayOfChar[i], 8, arrayOfLong[m]);
      i++;
      j--;
    case 2:
      arrayOfLong[m] = LNXQH2N_DIGIT(paramArrayOfChar[i], 4, arrayOfLong[m]);
      i++;
      j--;
    case 1:
      arrayOfLong[m] = LNXQH2N_DIGIT(paramArrayOfChar[i], 0, arrayOfLong[m]);
      i++;
      j--;
      break;
    }

    while (j != 0)
    {
      long l = 0L;
      l = LNXQH2N_DIGIT(paramArrayOfChar[i], 8, l);
      l = LNXQH2N_DIGIT(paramArrayOfChar[(i + 1)], 4, l);
      l = LNXQH2N_DIGIT(paramArrayOfChar[(i + 2)], 0, l);

      for (n = m; n >= k; n--)
      {
        l += (arrayOfLong[n] << 12);
        arrayOfLong[n] = (l % 1000000L);
        l /= 1000000L;
      }

      if (l != 0L)
      {
        k--;
        arrayOfLong[k] = l;
      }

      i += 3;
      j -= 3;
    }

    int i4 = 3 * (m - k) + 1;
    i4 += (arrayOfLong[k] >= 100L ? 1 : 0);
    i4 += (arrayOfLong[k] >= 10000L ? 1 : 0);

    byte[] arrayOfByte3 = new byte[22];
    int i5 = 0;

    arrayOfByte3[i5] = ((byte)(i4 + 192));
    byte[] arrayOfByte2;
    int i3;
    if (i4 > 20)
    {
      arrayOfByte2 = arrayOfByte1;
      i2 = i1;
      i3 = 21;
    }
    else
    {
      arrayOfByte2 = arrayOfByte3;
      i2 = i5 + 1;
      i3 = i4 + 1;
    }

    switch (i4 % 3)
    {
    case 0:
      arrayOfByte2[i2] = ((byte)(int)(arrayOfLong[k] / 10000L + 1L));
      i2++;
    case 2:
      arrayOfByte2[i2] = ((byte)(int)(arrayOfLong[k] % 10000L / 100L + 1L));
      i2++;
    case 1:
      arrayOfByte2[i2] = ((byte)(int)(arrayOfLong[k] % 100L + 1L));
      i2++;
      break;
    }

    for (int n = k + 1; n <= m; n++)
    {
      arrayOfByte2[i2] = ((byte)(int)(arrayOfLong[n] / 10000L + 1L));
      arrayOfByte2[(i2 + 1)] = ((byte)(int)(arrayOfLong[n] % 10000L / 100L + 1L));
      arrayOfByte2[(i2 + 2)] = ((byte)(int)(arrayOfLong[n] % 100L + 1L));

      i2 += 3;
    }

    if (i4 > 20)
    {
      i2 = i1 + 20;

      if (arrayOfByte1[i2] > 50)
      {
        arrayOfByte1[(i1 - 1)] = 1;

        i2--;
        while (arrayOfByte1[i2] == 100)
        {
          i2--;
          i3--;
        }
        int tmp672_670 = i2;
        byte[] tmp672_668 = arrayOfByte1; tmp672_668[tmp672_670] = ((byte)(tmp672_668[tmp672_670] + 1));

        if (i2 < i1)
        {
          i1--;
          int tmp692_690 = i5;
          byte[] tmp692_688 = arrayOfByte3; tmp692_688[tmp692_690] = ((byte)(tmp692_688[tmp692_690] + 1));
          i3 = 2;
        }
      }
      for (int i6 = 0; i6 < i3; i6++) {
        arrayOfByte3[(i5 + 1 + i6)] = arrayOfByte1[(i1 + i6)];
      }

    }

    int i2 = i5 + (i3 - 1);

    while (arrayOfByte2[i2] == 1)
    {
      i2--;
      i3--;
    }

    byte[] arrayOfByte4 = new byte[i3];
    System.arraycopy(arrayOfByte3, 0, arrayOfByte4, 0, i3);

    return arrayOfByte4;
  }

  private long LNXQH2N_DIGIT(char paramChar, int paramInt, long paramLong)
  {
    if ((paramChar >= 'a') && (paramChar <= 'f'))
    {
      l = paramLong + (paramChar - 'a' + 10 << paramInt);
      return l;
    }

    if ((paramChar >= 'A') && (paramChar <= 'F'))
    {
      l = paramLong + (paramChar - 'A' + 10 << paramInt);
      return l;
    }

    long l = paramLong + (paramChar - '0' << paramInt);
    return l;
  }

  private byte[] lnxqtra(byte[] paramArrayOfByte, int paramInt)
    throws SQLException
  {
    byte[] arrayOfByte1 = null;
    byte[] arrayOfByte2 = null;
    byte[] arrayOfByte3 = null;

    byte[] arrayOfByte4 = NUMBER.pi().shareBytes();
    byte[] arrayOfByte5 = lnxmin(-1L);

    long l = 0L;

    if ((paramInt == 3) || (paramInt == 4) || (paramInt == 5))
    {
      arrayOfByte3 = lnxmul(lnxqtwo, arrayOfByte4);

      arrayOfByte1 = lnxabs(paramArrayOfByte);

      arrayOfByte1 = lnxmod(arrayOfByte1, arrayOfByte3);
      if (lnxcmp(arrayOfByte1, arrayOfByte4) > 0)
      {
        arrayOfByte1 = lnxsub(arrayOfByte1, arrayOfByte3);
      }

      if (lnxsgn(paramArrayOfByte) == -1)
      {
        arrayOfByte1 = lnxneg(arrayOfByte1);
      }

      arrayOfByte2 = lnxmul(arrayOfByte1, arrayOfByte1);
    }
    else if (paramInt == 9)
    {
      arrayOfByte1 = lnxmod(paramArrayOfByte, lnxqone);
      arrayOfByte3 = lnxsub(paramArrayOfByte, arrayOfByte1);

      if ((arrayOfByte3[0] & 0xFF) < 60)
      {
        return NUMBER._makeZero();
      }

      if ((arrayOfByte3[0] & 0xFF) > 195)
      {
        return NUMBER._makePosInf();
      }

      l = lnxsni(arrayOfByte3);

      arrayOfByte2 = lnxmul(arrayOfByte1, arrayOfByte1);
    }
    else
    {
      arrayOfByte1 = new byte[paramArrayOfByte.length];
      System.arraycopy(paramArrayOfByte, 0, arrayOfByte1, 0, paramArrayOfByte.length);

      arrayOfByte2 = lnxmul(arrayOfByte1, arrayOfByte1);
    }

    byte[] arrayOfByte6 = null;
    byte[] arrayOfByte7 = null;
    int i;
    if ((paramInt != 4) && (paramInt != 7))
    {
      arrayOfByte3 = lnxqone;
      arrayOfByte6 = lnxqone;
      arrayOfByte7 = NUMBER._makeZero();

      int j = 0;
      while (true)
      {
        arrayOfByte3 = lnxmul(arrayOfByte2, arrayOfByte3);
        i = (j + 1) * (j + 2);
        j += 2;

        arrayOfByte3 = lnxqIDiv(arrayOfByte3, i);

        arrayOfByte7 = lnxadd(arrayOfByte7, arrayOfByte3);
        arrayOfByte3 = lnxmul(arrayOfByte2, arrayOfByte3);
        i = (j + 1) * (j + 2);
        j += 2;

        arrayOfByte3 = lnxqIDiv(arrayOfByte3, i);

        arrayOfByte6 = lnxadd(arrayOfByte6, arrayOfByte3);

        if ((arrayOfByte3[0] & 0xFF) + 20 >= (arrayOfByte6[0] & 0xFF)) if ((arrayOfByte7[0] & 0xFF) == 255)
          {
            break;
          }
      }
    }

    byte[] arrayOfByte8 = null;
    byte[] arrayOfByte9 = null;

    if ((paramInt != 3) && (paramInt != 6))
    {
      arrayOfByte3 = new byte[arrayOfByte1.length];
      System.arraycopy(arrayOfByte1, 0, arrayOfByte3, 0, arrayOfByte1.length);

      arrayOfByte8 = new byte[arrayOfByte1.length];
      System.arraycopy(arrayOfByte1, 0, arrayOfByte8, 0, arrayOfByte1.length);

      arrayOfByte9 = NUMBER._makeZero();

      int k = 1;
      while (true)
      {
        arrayOfByte3 = lnxmul(arrayOfByte2, arrayOfByte3);
        i = (k + 1) * (k + 2);
        k += 2;

        arrayOfByte3 = lnxqIDiv(arrayOfByte3, i);

        arrayOfByte9 = lnxadd(arrayOfByte9, arrayOfByte3);
        arrayOfByte3 = lnxmul(arrayOfByte2, arrayOfByte3);
        i = (k + 1) * (k + 2);
        k += 2;

        arrayOfByte3 = lnxqIDiv(arrayOfByte3, i);

        arrayOfByte8 = lnxadd(arrayOfByte8, arrayOfByte3);

        if (((arrayOfByte3[0] & 0xFF) != 128) || (arrayOfByte3.length != 1))
        {
          if (((arrayOfByte3[0] & 0xFF) < 128) || ((arrayOfByte3[0] & 0xFF) + 20 >= (arrayOfByte8[0] & 0xFF)))
          {
            if (((arrayOfByte3[0] & 0xFF) >= 128) || ((arrayOfByte3[0] & 0xFF) <= (arrayOfByte8[0] & 0xFF) + 20))
            {
              if ((arrayOfByte9[0] & 0xFF) != 255) if ((arrayOfByte9[0] & 0xFF) == 0)
                {
                  break;
                } 
            }
          }
        }
      }
    }
    byte[] arrayOfByte10 = null;
    byte[] arrayOfByte11 = null;

    if ((paramInt == 3) || (paramInt == 4) || (paramInt == 5))
    {
      if ((paramInt == 3) || (paramInt == 5))
      {
        arrayOfByte10 = lnxsub(arrayOfByte6, arrayOfByte7);
        if (lnxcmp(arrayOfByte10, lnxqone) > 0)
        {
          arrayOfByte10 = lnxqone;
        }
        else if (lnxcmp(arrayOfByte10, arrayOfByte5) < 0)
        {
          arrayOfByte10 = arrayOfByte5;
        }

      }

      if (paramInt == 3)
      {
        return arrayOfByte10;
      }

      arrayOfByte11 = lnxsub(arrayOfByte8, arrayOfByte9);
      if (lnxcmp(arrayOfByte11, lnxqone) > 0)
      {
        arrayOfByte11 = lnxqone;
      }
      else if (lnxcmp(arrayOfByte11, arrayOfByte5) < 0)
      {
        arrayOfByte11 = arrayOfByte5;
      }

      if (paramInt == 4)
      {
        return arrayOfByte11;
      }

      return lnxdiv(arrayOfByte11, arrayOfByte10);
    }

    if (paramInt == 6)
    {
      return lnxadd(arrayOfByte6, arrayOfByte7);
    }

    if (paramInt == 7)
    {
      return lnxadd(arrayOfByte8, arrayOfByte9);
    }

    arrayOfByte11 = lnxadd(arrayOfByte8, arrayOfByte9);
    arrayOfByte10 = lnxadd(arrayOfByte6, arrayOfByte7);

    if (paramInt == 8)
    {
      return lnxdiv(arrayOfByte11, arrayOfByte10);
    }

    byte[] arrayOfByte12 = NUMBER.e().shareBytes();

    byte[] arrayOfByte13 = lnxadd(arrayOfByte10, arrayOfByte11);
    arrayOfByte1 = lnxpow(arrayOfByte12, (int)l);

    arrayOfByte13 = lnxmul(arrayOfByte13, arrayOfByte1);

    return arrayOfByte13;
  }

  private byte[] lnxqtri(byte[] paramArrayOfByte, int paramInt)
    throws SQLException
  {
    Object localObject = null;
    byte[] arrayOfByte1 = null;
    byte[] arrayOfByte2 = null;

    byte[] arrayOfByte3 = NUMBER.pi().shareBytes();
    byte[] arrayOfByte4 = lnxdiv(arrayOfByte3, lnxqtwo);

    if (paramInt == 2)
    {
      if (NUMBER._isPosInf(paramArrayOfByte))
      {
        return arrayOfByte4;
      }
      if (NUMBER._isNegInf(paramArrayOfByte))
      {
        return lnxneg(arrayOfByte4);
      }

    }

    byte[] arrayOfByte5 = lnxabs(paramArrayOfByte);

    if ((paramInt == 1) || (paramInt == 0))
    {
      if (lnxcmp(arrayOfByte5, lnxqone) > 0)
      {
        throw new SQLException(CoreException.getMessage((byte)11));
      }

      if ((arrayOfByte5[0] & 0xFF) <= 183)
      {
        if (paramInt == 1)
        {
          byte[] arrayOfByte6 = new byte[paramArrayOfByte.length];
          System.arraycopy(paramArrayOfByte, 0, arrayOfByte6, 0, paramArrayOfByte.length);
          return arrayOfByte6;
        }

        return lnxsub(arrayOfByte4, paramArrayOfByte);
      }

      localObject = lnxsub(lnxqone, arrayOfByte5);
      arrayOfByte1 = lnxadd(lnxqone, arrayOfByte5);
      arrayOfByte5 = lnxdiv((byte[])localObject, arrayOfByte1);

      arrayOfByte5 = lnxsqr(arrayOfByte5);
    }
    int i;
    if ((i = lnxcmp(arrayOfByte5, lnxqone)) > 0)
    {
      arrayOfByte5 = lnxdiv(lnxqone, arrayOfByte5);
    }

    localObject = new byte[arrayOfByte5.length];
    System.arraycopy(arrayOfByte5, 0, localObject, 0, arrayOfByte5.length);

    int j = 1;
    while (true)
    {
      arrayOfByte1 = lnxtan((byte[])localObject);
      arrayOfByte2 = lnxsub(arrayOfByte5, arrayOfByte1);
      arrayOfByte1 = lnxmul(arrayOfByte1, arrayOfByte1);
      arrayOfByte1 = lnxadd(arrayOfByte1, lnxqone);
      arrayOfByte1 = lnxdiv(arrayOfByte2, arrayOfByte1);

      int k = (arrayOfByte1[0] & 0xFF) >= 128 ? (arrayOfByte1[0] & 0xFF) - 193 : 62 - (arrayOfByte1[0] & 0xFF);

      int m = (localObject[0] & 0xFF) >= 128 ? (localObject[0] & 0xFF) - 193 : 62 - (localObject[0] & 0xFF);

      if (((arrayOfByte1[0] & 0xFF) == 128) && (arrayOfByte1.length == 1))
      {
        break;
      }

      if ((k & 0xFF) + 15 < (m & 0xFF))
      {
        break;
      }

      if (j > 15)
      {
        break;
      }
      localObject = lnxadd((byte[])localObject, arrayOfByte1);
      j++;
    }

    if (i > 0)
    {
      localObject = lnxsub(arrayOfByte4, (byte[])localObject);
    }

    if ((localObject[0] & 0xFF) < 128)
    {
      localObject = NUMBER._makeZero();
    }

    if (lnxcmp((byte[])localObject, arrayOfByte4) > 0)
    {
      localObject = arrayOfByte4;
    }

    if ((paramInt == 1) || (paramInt == 0))
    {
      localObject = lnxmul((byte[])localObject, lnxqtwo);
    }

    switch (paramInt)
    {
    case 1:
      if (NUMBER._isPositive(paramArrayOfByte))
      {
        return lnxsub(arrayOfByte4, (byte[])localObject);
      }

      return lnxsub((byte[])localObject, arrayOfByte4);
    case 0:
      if (NUMBER._isPositive(paramArrayOfByte))
      {
        return localObject;
      }

      return lnxsub(arrayOfByte3, (byte[])localObject);
    case 2:
      if (NUMBER._isPositive(paramArrayOfByte))
      {
        return localObject;
      }

      return lnxneg((byte[])localObject);
    }

    throw new SQLException(CoreException.getMessage((byte)11));
  }

  private int lnxcmp(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
  {
    return NUMBER.compareBytes(paramArrayOfByte1, paramArrayOfByte2);
  }

  private int lnxsgn(byte[] paramArrayOfByte)
  {
    if (NUMBER._isZero(paramArrayOfByte)) return 0;
    if (NUMBER._isPositive(paramArrayOfByte)) {
      return 1;
    }
    return -1;
  }

  private byte[] lnxqIDiv(byte[] paramArrayOfByte, int paramInt)
    throws SQLException
  {
    byte[] arrayOfByte = lnxmin(paramInt);

    return lnxdiv(paramArrayOfByte, arrayOfByte);
  }

  private static void _negateNumber(byte[] paramArrayOfByte)
  {
    for (int i = paramArrayOfByte.length - 1; i > 0; i--)
    {
      paramArrayOfByte[i] = LnxqNegate[paramArrayOfByte[i]];
    }
    paramArrayOfByte[0] = ((byte)(paramArrayOfByte[0] ^ 0xFFFFFFFF));
  }

  private static byte[] _setLength(byte[] paramArrayOfByte, int paramInt)
  {
    boolean bool = NUMBER._isPositive(paramArrayOfByte);
    byte[] arrayOfByte;
    if (bool)
    {
      arrayOfByte = new byte[paramInt];
    }
    else if ((paramInt <= 20) && (paramArrayOfByte[(paramInt - 1)] != 102))
    {
      arrayOfByte = new byte[paramInt + 1];
      arrayOfByte[paramInt] = 102;
    }
    else
    {
      arrayOfByte = new byte[paramInt];
    }

    System.arraycopy(paramArrayOfByte, 0, arrayOfByte, 0, paramInt);
    return arrayOfByte;
  }

  private static class lnxqc
  {
    static final int LNXQC0 = 0;
    static final int LNXQC1 = 1;
    static final int LNXQC2 = 2;
    static final int LNXQC3 = 3;
    static final int LNXQC4 = 4;
    static final int LNXQC5 = 5;
    static final int LNXQC6 = 6;
    static final int LNXQC7 = 7;
    static final int LNXQC8 = 8;
    static final int LNXQC9 = 9;
    static final int LNXQCPLUS = 10;
    static final int LNXQCMINUS = 11;
    static final int LNXQCSPACE = 12;
    static final int LNXQCDOT = 13;
    static final int LNXQCCOMMA = 14;
    static final int LNXQCDOLLR = 15;
    static final int LNXQCLT = 16;
    static final int LNXQCGRT = 17;
    static final int LNXQCLPT = 18;
    static final int LNXQCRPT = 19;
    static final int LNXQCHASH = 20;
    static final int LNXQCTILDE = 21;
    static final int LNXQCASML = 22;
    static final int LNXQCBSML = 23;
    static final int LNXQCCSML = 24;
    static final int LNXQCDSML = 25;
    static final int LNXQCESML = 26;
    static final int LNXQCFSML = 27;
    static final int LNXQCGSML = 28;
    static final int LNXQCISML = 29;
    static final int LNXQCLSML = 30;
    static final int LNXQCMSML = 31;
    static final int LNXQCPSML = 32;
    static final int LNXQCRSML = 33;
    static final int LNXQCSSML = 34;
    static final int LNXQCTSML = 35;
    static final int LNXQCVSML = 36;
    static final int LNXQCALRG = 37;
    static final int LNXQCBLRG = 38;
    static final int LNXQCCLRG = 39;
    static final int LNXQCDLRG = 40;
    static final int LNXQCELRG = 41;
    static final int LNXQCFLRG = 42;
    static final int LNXQCILRG = 43;
    static final int LNXQCLLRG = 44;
    static final int LNXQCMLRG = 45;
    static final int LNXQCPLRG = 46;
    static final int LNXQCRLRG = 47;
    static final int LNXQCSLRG = 48;
    static final int LNXQCTLRG = 49;
  }
}