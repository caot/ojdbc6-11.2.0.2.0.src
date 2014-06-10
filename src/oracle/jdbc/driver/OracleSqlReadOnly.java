package oracle.jdbc.driver;

class OracleSqlReadOnly
{
  private static final int BASE = 0;
  private static final int BASE_1 = 1;
  private static final int BASE_2 = 2;
  private static final int B_STRING = 3;
  private static final int B_NAME = 4;
  private static final int B_C_COMMENT = 5;
  private static final int B_C_COMMENT_1 = 6;
  private static final int B_COMMENT = 7;
  private static final int PARAMETER = 8;
  private static final int TOKEN = 9;
  private static final int B_EGIN = 10;
  private static final int BE_GIN = 11;
  private static final int BEG_IN = 12;
  private static final int BEGI_N = 13;
  private static final int BEGIN_ = 14;
  private static final int C_ALL = 15;
  private static final int CA_LL = 16;
  private static final int CAL_L = 17;
  private static final int CALL_ = 18;
  private static final int D_Eetc = 19;
  private static final int DE_etc = 20;
  private static final int DEC_LARE = 21;
  private static final int DECL_ARE = 22;
  private static final int DECLA_RE = 23;
  private static final int DECLAR_E = 24;
  private static final int DECLARE_ = 25;
  private static final int DEL_ETE = 26;
  private static final int DELE_TE = 27;
  private static final int DELET_E = 28;
  private static final int DELETE_ = 29;
  private static final int I_NSERT = 30;
  private static final int IN_SERT = 31;
  private static final int INS_ERT = 32;
  private static final int INSE_RT = 33;
  private static final int INSER_T = 34;
  private static final int INSERT_ = 35;
  private static final int S_ELECT = 36;
  private static final int SE_LECT = 37;
  private static final int SEL_ECT = 38;
  private static final int SELE_CT = 39;
  private static final int SELEC_T = 40;
  private static final int SELECT_ = 41;
  private static final int U_PDATE = 42;
  private static final int UP_DATE = 43;
  private static final int UPD_ATE = 44;
  private static final int UPDA_TE = 45;
  private static final int UPDAT_E = 46;
  private static final int UPDATE_ = 47;
  private static final int M_ERGE = 48;
  private static final int ME_RGE = 49;
  private static final int MER_GE = 50;
  private static final int MERG_E = 51;
  private static final int MERGE_ = 52;
  private static final int W_ITH = 53;
  private static final int WI_TH = 54;
  private static final int WIT_H = 55;
  private static final int WITH_ = 56;
  private static final int KNOW_KIND = 57;
  private static final int KNOW_KIND_1 = 58;
  private static final int KNOW_KIND_2 = 59;
  private static final int K_STRING = 60;
  private static final int K_NAME = 61;
  private static final int K_C_COMMENT = 62;
  private static final int K_C_COMMENT_1 = 63;
  private static final int K_COMMENT = 64;
  private static final int K_PARAMETER = 65;
  private static final int TOKEN_KK = 66;
  private static final int W_HERE = 67;
  private static final int WH_ERE = 68;
  private static final int WHE_RE = 69;
  private static final int WHER_E = 70;
  private static final int WHERE_ = 71;
  private static final int O_RDER_BY = 72;
  private static final int OR_DER_BY = 73;
  private static final int ORD_ER_BY = 74;
  private static final int ORDE_R_BY = 75;
  private static final int ORDER__BY = 76;
  private static final int ORDER_xBY = 77;
  private static final int ORDER_B_Y = 78;
  private static final int ORDER_BY_ = 79;
  private static final int ORDER_xBY_CC_1 = 80;
  private static final int ORDER_xBY_CC_2 = 81;
  private static final int ORDER_xBY_CC_3 = 82;
  private static final int ORDER_xBY_C_1 = 83;
  private static final int ORDER_xBY_C_2 = 84;
  private static final int F_OR_UPDATE = 85;
  private static final int FO_R_UPDATE = 86;
  private static final int FOR__UPDATE = 87;
  private static final int FOR_xUPDATE = 88;
  private static final int FOR_U_PDATE = 89;
  private static final int FOR_UP_DATE = 90;
  private static final int FOR_UPD_ATE = 91;
  private static final int FOR_UPDA_TE = 92;
  private static final int FOR_UPDAT_E = 93;
  private static final int FOR_UPDATE_ = 94;
  private static final int FOR_xUPDATE_CC_1 = 95;
  private static final int FOR_xUPDATE_CC_2 = 96;
  private static final int FOR_xUPDATE_CC_3 = 97;
  private static final int FOR_xUPDATE_C_1 = 98;
  private static final int FOR_xUPDATE_C_2 = 99;
  private static final int B_N_tick = 100;
  private static final int B_NCHAR = 101;
  private static final int K_N_tick = 102;
  private static final int K_NCHAR = 103;
  private static final int K_NCHAR_tick = 104;
  private static final int B_Q_tickDelimiterCharDelimiterTick = 105;
  private static final int B_QTick_delimiterCharDelimiterTick = 106;
  private static final int B_QTickDelimiter_charDelimiterTick = 107;
  private static final int B_QTickDelimiterChar_delimiterTick = 108;
  private static final int B_QTickDelimiterCharDelimiter_tick = 109;
  private static final int K_Q_tickDelimiterCharDelimiterTick = 110;
  private static final int K_QTick_delimiterCharDelimiterTick = 111;
  private static final int K_QTickDelimiter_charDelimiterTick = 112;
  private static final int K_QTickDelimiterChar_delimiterTick = 113;
  private static final int K_QTickDelimiterCharDelimiter_tick = 114;
  private static final int K_EscEtc = 115;
  private static final int K_EscQuestion = 116;
  private static final int K_EscC_ALL = 117;
  private static final int K_EscCA_LL = 118;
  private static final int K_EscCAL_L = 119;
  private static final int K_EscCALL_ = 120;
  private static final int K_EscT = 121;
  private static final int K_EscTS_ = 122;
  private static final int K_EscD_ = 123;
  private static final int K_EscE_SCAPE = 124;
  private static final int K_EscES_CAPE = 125;
  private static final int K_EscESC_APE = 126;
  private static final int K_EscESCA_PE = 127;
  private static final int K_EscESCAP_E = 128;
  private static final int K_EscESCAPE_ = 129;
  private static final int K_EscF_N = 130;
  private static final int K_EscFN_ = 131;
  private static final int K_EscO_J = 132;
  private static final int K_EscOJ_ = 133;
  private static final int SKIP_PARAMETER_WHITESPACE = 134;
  private static final int LAST_STATE = 135;
  private static final int EOKTSS_LAST_STATE = 135;
  public static final String[] PARSER_STATE_NAME = { "BASE", "BASE_1", "BASE_2", "B_STRING", "B_NAME", "B_C_COMMENT", "B_C_COMMENT_1", "B_COMMENT", "PARAMETER", "TOKEN", "B_EGIN", "BE_GIN", "BEG_IN", "BEGI_N", "BEGIN_", "C_ALL", "CA_LL", "CAL_L", "CALL_", "D_Eetc", "DE_etc", "DEC_LARE", "DECL_ARE", "DECLA_RE", "DECLAR_E", "DECLARE_", "DEL_ETE", "DELE_TE", "DELET_E", "DELETE_", "I_NSERT", "IN_SERT", "INS_ERT", "INSE_RT", "INSER_T", "INSERT_", "S_ELECT", "SE_LECT", "SEL_ECT", "SELE_CT", "SELEC_T", "SELECT_", "U_PDATE", "UP_DATE", "UPD_ATE", "UPDA_TE", "UPDAT_E", "UPDATE_", "M_ERGE", "ME_RGE", "MER_GE", "MERG_E", "MERGE_", "W_ITH", "WI_TH", "WIT_H", "WITH_", "KNOW_KIND", "KNOW_KIND_1", "KNOW_KIND_2", "K_STRING", "K_NAME", "K_C_COMMENT", "K_C_COMMENT_1", "K_COMMENT", "K_PARAMETER", "TOKEN_KK", "W_HERE", "WH_ERE", "WHE_RE", "WHER_E", "WHERE_", "O_RDER_BY", "OR_DER_BY", "ORD_ER_BY", "ORDE_R_BY", "ORDER__BY", "ORDER_xBY", "ORDER_B_Y", "ORDER_BY_", "ORDER_xBY_CC_1", "ORDER_xBY_CC_2", "ORDER_xBY_CC_3", "ORDER_xBY_C_1 ", "ORDER_xBY_C_2 ", "F_OR_UPDATE", "FO_R_UPDATE", "FOR__UPDATE", "FOR_xUPDATE", "FOR_U_PDATE", "FOR_UP_DATE", "FOR_UPD_ATE", "FOR_UPDA_TE", "FOR_UPDAT_E", "FOR_UPDATE_", "FOR_xUPDATE_CC_1", "FOR_xUPDATE_CC_2", "FOR_xUPDATE_CC_3", "FOR_xUPDATE_C_1 ", "FOR_xUPDATE_C_2 ", "B_N_tick", "B_NCHAR", "K_N_tick", "K_NCHAR", "K_NCHAR_tick", "B_Q_tickDelimiterCharDelimiterTick", "B_QTick_delimiterCharDelimiterTick", "B_QTickDelimiter_charDelimiterTick", "B_QTickDelimiterChar_delimiterTick", "B_QTickDelimiterCharDelimiter_tick", "K_Q_tickDelimiterCharDelimiterTick", "K_QTick_delimiterCharDelimiterTick", "K_QTickDelimiter_charDelimiterTick", "K_QTickDelimiterChar_delimiterTick", "K_QTickDelimiterCharDelimiter_tick", "K_EscEtc", "K_EscQuestion", "K_EscC_ALL", "K_EscCA_LL", "K_EscCAL_L", "K_EscCALL_", "K_EscT", "K_EscTS_", "K_EscD_", "K_EscE_SCAPE", "K_EscES_CAPE", "K_EscESC_APE", "K_EscESCA_PE", "K_EscESCAP_E", "K_EscESCAPE_", "K_EscF_N", "K_EscFN_", "K_EscO_J", "K_EscOJ_", "SKIP_PARAMETER_WHITESPACE", "LAST_STATE" };

  static final int[][] TRANSITION = new int[''][];
  static final int NO_ACTION = 0;
  static final int DELETE_ACTION = 1;
  static final int INSERT_ACTION = 2;
  static final int MERGE_ACTION = 3;
  static final int UPDATE_ACTION = 4;
  static final int PLSQL_ACTION = 5;
  static final int CALL_ACTION = 6;
  static final int SELECT_ACTION = 7;
  static final int OTHER_ACTION = 8;
  static final int WHERE_ACTION = 9;
  static final int ORDER_ACTION = 10;
  static final int ORDER_BY_ACTION = 11;
  static final int FOR_ACTION = 12;
  static final int FOR_UPDATE_ACTION = 13;
  static final int QUESTION_ACTION = 14;
  static final int PARAMETER_ACTION = 15;
  static final int END_PARAMETER_ACTION = 16;
  static final int START_NCHAR_LITERAL_ACTION = 17;
  static final int END_NCHAR_LITERAL_ACTION = 18;
  static final int SAVE_DELIMITER_ACTION = 19;
  static final int LOOK_FOR_DELIMITER_ACTION = 20;
  public static final String[] CBI_ACTION_NAME = { "NO_ACTION", "DELETE_ACTION", "INSERT_ACTION", "MERGE_ACTION", "UPDATE_ACTION", "PLSQL_ACTION", "CALL_ACTION", "SELECT_ACTION", "OTHER_ACTION", "WHERE_ACTION", "ORDER_ACTION", "ORDER_BY_ACTION", "FOR_ACTION", "FOR_UPDATE_ACTION", "QUESTION_ACTION", "PARAMETER_ACTION", "END_PARAMETER_ACTION", "START_NCHAR_LITERAL_ACTION", "END_NCHAR_LITERAL_ACTION", "SAVE_DELIMITER_ACTION", "LOOK_FOR_DELIMITER_ACTION" };

  static final int[][] ACTION = new int[''][];
  static final int INITIAL_STATE = 0;
  static final int RESTART_STATE = 66;
  static final ODBCAction[][] ODBC_ACTION = new ODBCAction[''][];
  static final int cMax = 127;
  private static final int cMaxLength = 128;

  private static final int[] copy(int[] paramArrayOfInt)
  {
    int[] arrayOfInt = new int[paramArrayOfInt.length];
    System.arraycopy(paramArrayOfInt, 0, arrayOfInt, 0, paramArrayOfInt.length);
    return arrayOfInt;
  }

  private static final ODBCAction[] copy(ODBCAction[] paramArrayOfODBCAction)
  {
    ODBCAction[] arrayOfODBCAction = new ODBCAction[paramArrayOfODBCAction.length];
    System.arraycopy(paramArrayOfODBCAction, 0, arrayOfODBCAction, 0, paramArrayOfODBCAction.length);
    return arrayOfODBCAction;
  }

  private static final int[] newArray(int paramInt1, int paramInt2)
  {
    int[] arrayOfInt = new int[paramInt1];

    for (int i = 0; i < paramInt1; i++) {
      arrayOfInt[i] = paramInt2;
    }
    return arrayOfInt;
  }

  private static final ODBCAction[] newArray(int paramInt, ODBCAction paramODBCAction)
  {
    ODBCAction[] arrayOfODBCAction = new ODBCAction[paramInt];

    for (int i = 0; i < paramInt; i++) {
      arrayOfODBCAction[i] = paramODBCAction;
    }
    return arrayOfODBCAction;
  }

  private static final int[] copyReplacing(int[] paramArrayOfInt, int paramInt1, int paramInt2)
  {
    int[] arrayOfInt = new int[paramArrayOfInt.length];

    for (int i = 0; i < arrayOfInt.length; i++)
    {
      int j = paramArrayOfInt[i];

      if (j == paramInt1)
        arrayOfInt[i] = paramInt2;
      else {
        arrayOfInt[i] = j;
      }
    }
    return arrayOfInt;
  }

  private static final ODBCAction[] copyReplacing(ODBCAction[] paramArrayOfODBCAction, ODBCAction paramODBCAction1, ODBCAction paramODBCAction2)
  {
    ODBCAction[] arrayOfODBCAction = new ODBCAction[paramArrayOfODBCAction.length];

    for (int i = 0; i < arrayOfODBCAction.length; i++)
    {
      ODBCAction localODBCAction = paramArrayOfODBCAction[i];

      if (localODBCAction == paramODBCAction1)
        arrayOfODBCAction[i] = paramODBCAction2;
      else {
        arrayOfODBCAction[i] = localODBCAction;
      }
    }
    return arrayOfODBCAction;
  }

  static
  {
    try
    {
      int[] arrayOfInt1 = { 57, 57, 57, 57, 57, 57, 57, 57, 57, 57, 57, 57, 57, 57, 57, 57, 57, 57, 57, 57, 57, 57, 57, 57, 57, 57, 57, 57, 57, 57, 57, 57, 57, 57, 57, 9, 9, 57, 57, 57, 57, 57, 57, 57, 57, 57, 57, 57, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 57, 57, 57, 57, 57, 57, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 57, 57, 57, 57, 9, 57, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 57, 57, 57, 57, 57 };

      int[] arrayOfInt2 = copy(arrayOfInt1);
      arrayOfInt2[34] = 4;
      arrayOfInt2[39] = 3;
      arrayOfInt2[45] = 2;
      arrayOfInt2[47] = 1;
      arrayOfInt2[58] = 8;
      arrayOfInt2[123] = 115;

      int[] arrayOfInt3 = copyReplacing(arrayOfInt2, 57, 0);
      arrayOfInt3[66] = 10;
      arrayOfInt3[98] = 10;
      arrayOfInt3[67] = 15;
      arrayOfInt3[99] = 15;
      arrayOfInt3[68] = 19;
      arrayOfInt3[100] = 19;
      arrayOfInt3[73] = 30;
      arrayOfInt3[105] = 30;
      arrayOfInt3[109] = 48;
      arrayOfInt3[77] = 48;
      arrayOfInt3[78] = 100;
      arrayOfInt3[110] = 100;
      arrayOfInt3[81] = 105;
      arrayOfInt3[113] = 105;
      arrayOfInt3[83] = 36;
      arrayOfInt3[115] = 36;
      arrayOfInt3[85] = 42;
      arrayOfInt3[117] = 42;
      arrayOfInt3[87] = 53;
      arrayOfInt3[119] = 53;

      int[] arrayOfInt4 = copyReplacing(arrayOfInt2, 9, 66);
      arrayOfInt4[34] = 61;
      arrayOfInt4[39] = 60;
      arrayOfInt4[45] = 59;
      arrayOfInt4[47] = 58;
      arrayOfInt4[58] = 134;
      arrayOfInt4[32] = 57;
      arrayOfInt4[32] = 57;
      arrayOfInt4[9] = 57;
      arrayOfInt4[10] = 57;
      arrayOfInt4[13] = 57;
      arrayOfInt4[61] = 57;

      int[] arrayOfInt5 = copyReplacing(arrayOfInt4, 9, 66);
      arrayOfInt5[78] = 102;
      arrayOfInt5[110] = 102;
      arrayOfInt5[81] = 110;
      arrayOfInt5[113] = 110;
      arrayOfInt5[87] = 67;
      arrayOfInt5[119] = 67;
      arrayOfInt5[79] = 72;
      arrayOfInt5[111] = 72;
      arrayOfInt5[70] = 85;
      arrayOfInt5[102] = 85;

      int[] arrayOfInt6 = copyReplacing(arrayOfInt5, 57, 115);

      arrayOfInt6[63] = 116;
      arrayOfInt6[99] = 117;
      arrayOfInt6[67] = 117;
      arrayOfInt6[116] = 121;
      arrayOfInt6[84] = 121;
      arrayOfInt6[100] = 123;
      arrayOfInt6[68] = 123;
      arrayOfInt6[101] = 124;
      arrayOfInt6[69] = 124;
      arrayOfInt6[102] = 130;
      arrayOfInt6[70] = 130;
      arrayOfInt6[111] = 132;
      arrayOfInt6[79] = 132;

      TRANSITION[0] = arrayOfInt3;
      TRANSITION[1] = copy(arrayOfInt3);
      TRANSITION[1][42] = 5;
      TRANSITION[2] = copy(arrayOfInt3);
      TRANSITION[2][45] = 7;
      TRANSITION[3] = newArray(128, 3);
      TRANSITION[3][39] = 0;
      TRANSITION[100] = copy(arrayOfInt2);
      TRANSITION[100][39] = 101;
      TRANSITION[101] = newArray(128, 101);
      TRANSITION[101][39] = 0;

      TRANSITION[105] = copy(arrayOfInt2);
      TRANSITION[105][39] = 106;
      TRANSITION[106] = newArray(128, 107);
      TRANSITION[107] = newArray(128, 107);

      TRANSITION[108] = newArray(128, 109);
      TRANSITION[109] = newArray(128, 107);
      TRANSITION[109][39] = 0;

      TRANSITION[4] = newArray(128, 4);
      TRANSITION[4][34] = 0;
      TRANSITION[5] = newArray(128, 5);
      TRANSITION[5][42] = 6;
      TRANSITION[6] = newArray(128, 5);
      TRANSITION[6][42] = 6;
      TRANSITION[6][47] = 0;
      TRANSITION[7] = newArray(128, 7);
      TRANSITION[7][10] = 0;
      TRANSITION[8] = copyReplacing(arrayOfInt2, 9, 8);
      TRANSITION[9] = arrayOfInt2;
      TRANSITION[10] = copy(arrayOfInt2);
      TRANSITION[10][69] = 11;
      TRANSITION[10][101] = 11;
      TRANSITION[11] = copy(arrayOfInt2);
      TRANSITION[11][71] = 12;
      TRANSITION[11][103] = 12;
      TRANSITION[12] = copy(arrayOfInt2);
      TRANSITION[12][73] = 13;
      TRANSITION[12][105] = 13;
      TRANSITION[13] = copy(arrayOfInt2);
      TRANSITION[13][78] = 14;
      TRANSITION[13][110] = 14;
      TRANSITION[14] = arrayOfInt5;
      TRANSITION[15] = copy(arrayOfInt2);
      TRANSITION[15][65] = 16;
      TRANSITION[15][97] = 16;
      TRANSITION[16] = copy(arrayOfInt2);
      TRANSITION[16][76] = 17;
      TRANSITION[16][108] = 17;
      TRANSITION[17] = copy(arrayOfInt2);
      TRANSITION[17][76] = 18;
      TRANSITION[17][108] = 18;
      TRANSITION[18] = arrayOfInt5;
      TRANSITION[19] = copy(arrayOfInt2);
      TRANSITION[19][69] = 20;
      TRANSITION[19][101] = 20;
      TRANSITION[20] = copy(arrayOfInt2);
      TRANSITION[20][67] = 21;
      TRANSITION[20][99] = 21;
      TRANSITION[20][76] = 26;
      TRANSITION[20][108] = 26;
      TRANSITION[21] = copy(arrayOfInt2);
      TRANSITION[21][76] = 22;
      TRANSITION[21][108] = 22;
      TRANSITION[22] = copy(arrayOfInt2);
      TRANSITION[22][65] = 23;
      TRANSITION[22][97] = 23;
      TRANSITION[23] = copy(arrayOfInt2);
      TRANSITION[23][82] = 24;
      TRANSITION[23][114] = 24;
      TRANSITION[24] = copy(arrayOfInt2);
      TRANSITION[24][69] = 25;
      TRANSITION[24][101] = 25;
      TRANSITION[25] = arrayOfInt5;
      TRANSITION[26] = copy(arrayOfInt2);
      TRANSITION[26][69] = 27;
      TRANSITION[26][101] = 27;
      TRANSITION[27] = copy(arrayOfInt2);
      TRANSITION[27][84] = 28;
      TRANSITION[27][116] = 28;
      TRANSITION[28] = copy(arrayOfInt2);
      TRANSITION[28][69] = 29;
      TRANSITION[28][101] = 29;
      TRANSITION[29] = arrayOfInt5;
      TRANSITION[30] = copy(arrayOfInt2);
      TRANSITION[30][78] = 31;
      TRANSITION[30][110] = 31;
      TRANSITION[31] = copy(arrayOfInt2);
      TRANSITION[31][83] = 32;
      TRANSITION[31][115] = 32;
      TRANSITION[32] = copy(arrayOfInt2);
      TRANSITION[32][69] = 33;
      TRANSITION[32][101] = 33;
      TRANSITION[33] = copy(arrayOfInt2);
      TRANSITION[33][82] = 34;
      TRANSITION[33][114] = 34;
      TRANSITION[34] = copy(arrayOfInt2);
      TRANSITION[34][84] = 35;
      TRANSITION[34][116] = 35;
      TRANSITION[35] = arrayOfInt5;
      TRANSITION[36] = copy(arrayOfInt2);
      TRANSITION[36][69] = 37;
      TRANSITION[36][101] = 37;
      TRANSITION[37] = copy(arrayOfInt2);
      TRANSITION[37][76] = 38;
      TRANSITION[37][108] = 38;
      TRANSITION[38] = copy(arrayOfInt2);
      TRANSITION[38][69] = 39;
      TRANSITION[38][101] = 39;
      TRANSITION[39] = copy(arrayOfInt2);
      TRANSITION[39][67] = 40;
      TRANSITION[39][99] = 40;
      TRANSITION[40] = copy(arrayOfInt2);
      TRANSITION[40][84] = 41;
      TRANSITION[40][116] = 41;
      TRANSITION[41] = arrayOfInt5;
      TRANSITION[42] = copy(arrayOfInt2);
      TRANSITION[42][80] = 43;
      TRANSITION[42][112] = 43;
      TRANSITION[43] = copy(arrayOfInt2);
      TRANSITION[43][68] = 44;
      TRANSITION[43][100] = 44;
      TRANSITION[44] = copy(arrayOfInt2);
      TRANSITION[44][65] = 45;
      TRANSITION[44][97] = 45;
      TRANSITION[45] = copy(arrayOfInt2);
      TRANSITION[45][84] = 46;
      TRANSITION[45][116] = 46;
      TRANSITION[46] = copy(arrayOfInt2);
      TRANSITION[46][69] = 47;
      TRANSITION[46][101] = 47;
      TRANSITION[47] = arrayOfInt5;
      TRANSITION[48] = copy(arrayOfInt2);
      TRANSITION[48][69] = 49;
      TRANSITION[48][101] = 49;
      TRANSITION[49] = copy(arrayOfInt2);
      TRANSITION[49][82] = 50;
      TRANSITION[49][114] = 50;
      TRANSITION[50] = copy(arrayOfInt2);
      TRANSITION[50][71] = 51;
      TRANSITION[50][103] = 51;
      TRANSITION[51] = copy(arrayOfInt2);
      TRANSITION[51][69] = 52;
      TRANSITION[51][101] = 52;
      TRANSITION[52] = arrayOfInt5;
      TRANSITION[53] = copy(arrayOfInt2);
      TRANSITION[53][73] = 54;
      TRANSITION[53][105] = 54;
      TRANSITION[54] = copy(arrayOfInt2);
      TRANSITION[54][84] = 55;
      TRANSITION[54][116] = 55;
      TRANSITION[55] = copy(arrayOfInt2);
      TRANSITION[55][72] = 56;
      TRANSITION[55][104] = 56;
      TRANSITION[56] = arrayOfInt5;
      TRANSITION[66] = arrayOfInt4;
      TRANSITION[58] = copy(arrayOfInt5);
      TRANSITION[58][42] = 62;
      TRANSITION[59] = copy(arrayOfInt5);
      TRANSITION[59][45] = 64;
      TRANSITION[62] = newArray(128, 62);
      TRANSITION[62][42] = 63;
      TRANSITION[63] = newArray(128, 62);
      TRANSITION[63][42] = 63;
      TRANSITION[63][47] = 57;
      TRANSITION[64] = newArray(128, 64);
      TRANSITION[64][10] = 57;
      TRANSITION[61] = newArray(128, 61);
      TRANSITION[61][34] = 57;
      TRANSITION[60] = newArray(128, 60);
      TRANSITION[60][39] = 57;
      TRANSITION[65] = copyReplacing(arrayOfInt4, 66, 65);
      TRANSITION[''] = copyReplacing(arrayOfInt4, 66, 65);
      TRANSITION[''][32] = '';
      TRANSITION[''][10] = '';
      TRANSITION[''][13] = '';
      TRANSITION[''][9] = '';

      TRANSITION[57] = arrayOfInt5;
      TRANSITION[67] = copy(arrayOfInt4);
      TRANSITION[67][72] = 68;
      TRANSITION[67][104] = 68;
      TRANSITION[68] = copy(arrayOfInt4);
      TRANSITION[68][69] = 69;
      TRANSITION[68][101] = 69;
      TRANSITION[69] = copy(arrayOfInt4);
      TRANSITION[69][82] = 70;
      TRANSITION[69][114] = 70;
      TRANSITION[70] = copy(arrayOfInt4);
      TRANSITION[70][69] = 71;
      TRANSITION[70][101] = 71;
      TRANSITION[71] = arrayOfInt5;

      TRANSITION[72] = copy(arrayOfInt4);
      TRANSITION[72][82] = 73;
      TRANSITION[72][114] = 73;
      TRANSITION[73] = copy(arrayOfInt4);
      TRANSITION[73][68] = 74;
      TRANSITION[73][100] = 74;
      TRANSITION[74] = copy(arrayOfInt4);
      TRANSITION[74][69] = 75;
      TRANSITION[74][101] = 75;
      TRANSITION[75] = copy(arrayOfInt4);
      TRANSITION[75][82] = 76;
      TRANSITION[75][114] = 76;
      TRANSITION[76] = copyReplacing(arrayOfInt5, 57, 77);
      TRANSITION[76][47] = 80;
      TRANSITION[76][45] = 83;

      TRANSITION[77] = copyReplacing(arrayOfInt5, 57, 77);
      TRANSITION[77][47] = 80;
      TRANSITION[80] = copy(arrayOfInt5);
      TRANSITION[80][42] = 81;
      TRANSITION[81] = newArray(128, 81);
      TRANSITION[81][42] = 82;
      TRANSITION[82] = newArray(128, 81);
      TRANSITION[82][47] = 77;

      TRANSITION[77][45] = 83;
      TRANSITION[83] = copy(arrayOfInt5);
      TRANSITION[83][45] = 84;
      TRANSITION[84] = newArray(128, 84);
      TRANSITION[84][10] = 77;

      TRANSITION[77][66] = 78;
      TRANSITION[77][98] = 78;
      TRANSITION[78] = copy(arrayOfInt4);
      TRANSITION[78][89] = 79;
      TRANSITION[78][121] = 79;
      TRANSITION[79] = arrayOfInt5;

      TRANSITION[85] = copy(arrayOfInt5);
      TRANSITION[85][79] = 86;
      TRANSITION[85][111] = 86;
      TRANSITION[86] = copy(arrayOfInt5);
      TRANSITION[86][82] = 87;
      TRANSITION[86][114] = 87;
      TRANSITION[87] = copyReplacing(arrayOfInt4, 57, 88);
      TRANSITION[87][47] = 95;
      TRANSITION[87][45] = 98;

      TRANSITION[88] = copyReplacing(arrayOfInt4, 57, 88);
      TRANSITION[88][47] = 95;
      TRANSITION[95] = copy(arrayOfInt5);
      TRANSITION[95][42] = 96;
      TRANSITION[96] = newArray(128, 96);
      TRANSITION[96][42] = 97;
      TRANSITION[97] = newArray(128, 96);
      TRANSITION[97][47] = 88;

      TRANSITION[88][45] = 98;
      TRANSITION[98] = copy(arrayOfInt5);
      TRANSITION[98][45] = 99;
      TRANSITION[99] = newArray(128, 99);
      TRANSITION[99][10] = 88;

      TRANSITION[88][85] = 89;
      TRANSITION[88][117] = 89;
      TRANSITION[89] = copy(arrayOfInt5);
      TRANSITION[89][80] = 90;
      TRANSITION[89][112] = 90;
      TRANSITION[90] = copy(arrayOfInt5);
      TRANSITION[90][68] = 91;
      TRANSITION[90][100] = 91;
      TRANSITION[91] = copy(arrayOfInt5);
      TRANSITION[91][65] = 92;
      TRANSITION[91][97] = 92;
      TRANSITION[92] = copy(arrayOfInt5);
      TRANSITION[92][84] = 93;
      TRANSITION[92][116] = 93;
      TRANSITION[93] = copy(arrayOfInt5);
      TRANSITION[93][69] = 94;
      TRANSITION[93][101] = 94;
      TRANSITION[94] = arrayOfInt5;

      TRANSITION[102] = copy(arrayOfInt4);
      TRANSITION[102][39] = 103;
      TRANSITION[103] = newArray(128, 103);
      TRANSITION[103][39] = 104;

      TRANSITION[104] = newArray(128, 57);
      TRANSITION[104][39] = 103;

      TRANSITION[110] = copy(arrayOfInt5);
      TRANSITION[110][39] = 111;
      TRANSITION[111] = newArray(128, 112);
      TRANSITION[112] = newArray(128, 112);

      TRANSITION[113] = newArray(128, 114);
      TRANSITION[114] = newArray(128, 112);
      TRANSITION[114][39] = 57;

      TRANSITION[115] = arrayOfInt6;
      TRANSITION[116] = arrayOfInt5;
      TRANSITION[117] = copy(arrayOfInt5);
      TRANSITION[117][97] = 118;
      TRANSITION[117][65] = 118;
      TRANSITION[118] = copy(arrayOfInt5);
      TRANSITION[118][108] = 119;
      TRANSITION[118][76] = 119;
      TRANSITION[119] = copy(arrayOfInt5);
      TRANSITION[119][108] = 120;
      TRANSITION[119][76] = 120;
      TRANSITION[120] = arrayOfInt5;
      TRANSITION[121] = copy(arrayOfInt5);
      TRANSITION[121][115] = 122;
      TRANSITION[121][83] = 122;
      TRANSITION[122] = arrayOfInt5;
      TRANSITION[123] = arrayOfInt5;
      TRANSITION[124] = copy(arrayOfInt5);
      TRANSITION[124][115] = 125;
      TRANSITION[124][83] = 125;
      TRANSITION[125] = copy(arrayOfInt5);
      TRANSITION[125][99] = 126;
      TRANSITION[125][67] = 126;
      TRANSITION[126] = copy(arrayOfInt5);
      TRANSITION[126][97] = 127;
      TRANSITION[126][65] = 127;
      TRANSITION[127] = copy(arrayOfInt5);
      TRANSITION[127][112] = '';
      TRANSITION[127][80] = '';
      TRANSITION[''] = copy(arrayOfInt5);
      TRANSITION[''][101] = '';
      TRANSITION[''][69] = '';
      TRANSITION[''] = arrayOfInt5;
      TRANSITION[''] = copy(arrayOfInt5);
      TRANSITION[''][110] = '';
      TRANSITION[''][78] = '';
      TRANSITION[''] = arrayOfInt5;
      TRANSITION[''] = copy(arrayOfInt5);
      TRANSITION[''][106] = '';
      TRANSITION[''][74] = '';
      TRANSITION[''] = arrayOfInt5;

      Object localObject1 = newArray(128, 0);

      Object localObject2 = copy((int[])localObject1);
      localObject2[63] = 14;

      Object localObject3 = copy((int[])localObject2);
      localObject3[123] = 5;

      Object localObject4 = new int[''];

      for (int i = 0; i < localObject4.length; i++) {
        if (TRANSITION[8][i] == 8)
          localObject4[i] = 15;
        else
          localObject4[i] = 16;
      }
      Object localObject5 = new int[''];

      for (int j = 0; j < localObject5.length; j++) {
        if (TRANSITION[65][j] == 65)
          localObject5[j] = 15;
        else
          localObject5[j] = 16;
      }
      Object localObject6 = copy((int[])localObject5);
      localObject6[32] = 0;
      localObject6[10] = 0;
      localObject6[9] = 0;
      localObject6[13] = 0;

      Object localObject7 = copy((int[])localObject1);

      for (int k = 0; k < localObject7.length; k++) {
        if (arrayOfInt5[k] != 66)
          localObject7[k] = 5;
      }
      Object localObject8 = copyReplacing((int[])localObject7, 5, 6);
      int[] arrayOfInt7 = copyReplacing((int[])localObject7, 5, 1);
      int[] arrayOfInt8 = copyReplacing((int[])localObject7, 5, 2);
      int[] arrayOfInt9 = copyReplacing((int[])localObject7, 5, 3);
      int[] arrayOfInt10 = copyReplacing((int[])localObject7, 5, 4);
      int[] arrayOfInt11 = copyReplacing((int[])localObject7, 5, 7);
      int[] arrayOfInt12 = copyReplacing((int[])localObject7, 5, 8);
      arrayOfInt12[123] = 6;

      int[] arrayOfInt13 = copyReplacing((int[])localObject7, 5, 10);

      for (int n = 0; n < arrayOfInt13.length; n++) {
        if (arrayOfInt13[n] == 8) {
          arrayOfInt13[n] = 0;
        }
      }
      int[] arrayOfInt14 = copyReplacing(arrayOfInt13, 10, 11);
      int[] arrayOfInt15 = copyReplacing(arrayOfInt13, 10, 9);
      int[] arrayOfInt16 = copyReplacing(arrayOfInt13, 10, 12);
      int[] arrayOfInt17 = copyReplacing(arrayOfInt13, 10, 13);

      int[] arrayOfInt18 = copy((int[])localObject1);
      arrayOfInt18[39] = 17;

      int[] arrayOfInt19 = copyReplacing((int[])localObject1, 0, 18);
      arrayOfInt19[39] = 0;

      int[] arrayOfInt20 = copyReplacing((int[])localObject1, 0, 19);

      int[] arrayOfInt21 = copyReplacing((int[])localObject1, 0, 20);

      int[] arrayOfInt22 = copy(arrayOfInt21);
      arrayOfInt22[39] = 0;

      ACTION[0] = localObject3;
      ACTION[1] = localObject3;
      ACTION[2] = localObject3;
      ACTION[3] = localObject1;
      ACTION[4] = localObject1;
      ACTION[5] = localObject1;
      ACTION[6] = localObject1;
      ACTION[7] = localObject1;
      ACTION[8] = localObject4;
      ACTION[''] = localObject6;
      ACTION[100] = arrayOfInt18;
      ACTION[101] = arrayOfInt19;
      ACTION[105] = localObject1;
      ACTION[106] = arrayOfInt20;
      ACTION[107] = arrayOfInt21;
      ACTION[108] = null;
      ACTION[109] = arrayOfInt22;
      ACTION[9] = arrayOfInt12;
      ACTION[10] = arrayOfInt12;
      ACTION[11] = arrayOfInt12;
      ACTION[12] = arrayOfInt12;
      ACTION[13] = arrayOfInt12;
      ACTION[14] = localObject7;
      ACTION[15] = arrayOfInt12;
      ACTION[16] = arrayOfInt12;
      ACTION[17] = arrayOfInt12;
      ACTION[18] = localObject8;
      ACTION[19] = arrayOfInt12;
      ACTION[20] = arrayOfInt12;
      ACTION[21] = arrayOfInt12;
      ACTION[22] = arrayOfInt12;
      ACTION[23] = arrayOfInt12;
      ACTION[24] = arrayOfInt12;
      ACTION[25] = localObject7;
      ACTION[26] = arrayOfInt12;
      ACTION[27] = arrayOfInt12;
      ACTION[28] = arrayOfInt12;
      ACTION[29] = arrayOfInt7;
      ACTION[30] = arrayOfInt12;
      ACTION[31] = arrayOfInt12;
      ACTION[32] = arrayOfInt12;
      ACTION[33] = arrayOfInt12;
      ACTION[34] = arrayOfInt12;
      ACTION[35] = arrayOfInt8;
      ACTION[36] = arrayOfInt12;
      ACTION[37] = arrayOfInt12;
      ACTION[38] = arrayOfInt12;
      ACTION[39] = arrayOfInt12;
      ACTION[40] = arrayOfInt12;
      ACTION[41] = arrayOfInt11;
      ACTION[42] = arrayOfInt12;
      ACTION[43] = arrayOfInt12;
      ACTION[44] = arrayOfInt12;
      ACTION[45] = arrayOfInt12;
      ACTION[46] = arrayOfInt12;
      ACTION[47] = arrayOfInt10;
      ACTION[48] = arrayOfInt12;
      ACTION[49] = arrayOfInt12;
      ACTION[50] = arrayOfInt12;
      ACTION[51] = arrayOfInt12;
      ACTION[52] = arrayOfInt9;
      ACTION[53] = arrayOfInt12;
      ACTION[54] = arrayOfInt12;
      ACTION[55] = arrayOfInt12;
      ACTION[56] = arrayOfInt11;
      ACTION[66] = localObject2;
      ACTION[58] = localObject2;
      ACTION[59] = localObject2;
      ACTION[60] = localObject1;
      ACTION[61] = localObject1;
      ACTION[62] = localObject1;
      ACTION[63] = localObject1;
      ACTION[64] = localObject1;
      ACTION[65] = localObject5;
      ACTION[102] = arrayOfInt18;
      ACTION[103] = localObject1;
      ACTION[104] = arrayOfInt19;
      ACTION[110] = localObject1;
      ACTION[111] = arrayOfInt20;
      ACTION[112] = arrayOfInt21;
      ACTION[113] = null;
      ACTION[114] = arrayOfInt22;

      ACTION[57] = localObject2;

      ACTION[67] = localObject1;
      ACTION[68] = localObject1;
      ACTION[69] = localObject1;
      ACTION[70] = localObject1;
      ACTION[71] = arrayOfInt15;

      ACTION[72] = localObject1;
      ACTION[73] = localObject1;
      ACTION[74] = localObject1;
      ACTION[75] = localObject1;
      ACTION[76] = arrayOfInt13;

      ACTION[77] = localObject1;
      ACTION[78] = localObject1;
      ACTION[79] = arrayOfInt14;

      ACTION[80] = localObject1;
      ACTION[81] = localObject1;
      ACTION[82] = localObject1;
      ACTION[83] = localObject1;
      ACTION[84] = localObject1;

      ACTION[85] = localObject1;
      ACTION[86] = localObject1;
      ACTION[87] = arrayOfInt16;

      ACTION[88] = localObject2;
      ACTION[89] = localObject1;
      ACTION[90] = localObject1;
      ACTION[91] = localObject1;
      ACTION[92] = localObject1;
      ACTION[93] = localObject1;
      ACTION[94] = arrayOfInt17;

      ACTION[95] = localObject1;
      ACTION[96] = localObject1;
      ACTION[97] = localObject1;
      ACTION[98] = localObject1;
      ACTION[99] = localObject1;

      ACTION[115] = copy((int[])localObject1);
      ACTION[115][63] = 14;
      ACTION[116] = localObject1;
      ACTION[117] = localObject1;
      ACTION[118] = localObject1;
      ACTION[119] = localObject1;
      ACTION[120] = localObject1;
      ACTION[121] = localObject1;
      ACTION[122] = localObject1;
      ACTION[123] = localObject1;
      ACTION[124] = localObject1;
      ACTION[125] = localObject1;
      ACTION[126] = localObject1;
      ACTION[127] = localObject1;
      ACTION[''] = localObject1;
      ACTION[''] = localObject1;
      ACTION[''] = localObject1;
      ACTION[''] = localObject1;
      ACTION[''] = localObject1;
      ACTION[''] = localObject1;

      localObject1 = newArray(128, ODBCAction.NONE);

      localObject2 = newArray(128, ODBCAction.COPY);

      localObject3 = copy((ODBCAction[])localObject2);
      localObject3[123] = ODBCAction.NONE;
      localObject3[63] = ODBCAction.QUESTION;
      localObject3[125] = ODBCAction.END_ODBC_ESCAPE;
      localObject3[44] = ODBCAction.COMMA;
      localObject3[40] = ODBCAction.OPEN_PAREN;
      localObject3[41] = ODBCAction.CLOSE_PAREN;

      localObject4 = copyReplacing((ODBCAction[])localObject2, ODBCAction.COPY, ODBCAction.SAVE_DELIMITER);

      localObject5 = copyReplacing((ODBCAction[])localObject2, ODBCAction.COPY, ODBCAction.LOOK_FOR_DELIMITER);

      localObject6 = copy((ODBCAction[])localObject5);
      localObject6[39] = ODBCAction.COPY;

      localObject7 = newArray(128, ODBCAction.UNKNOWN_ESCAPE);

      localObject8 = copy((ODBCAction[])localObject1);
      for (int m = 0; m < arrayOfInt1.length; m++) {
        if (arrayOfInt1[m] == 9) localObject7[m] = ODBCAction.UNKNOWN_ESCAPE;
      }

      ODBC_ACTION[0] = localObject3;
      ODBC_ACTION[1] = localObject3;
      ODBC_ACTION[2] = localObject3;
      ODBC_ACTION[3] = localObject2;
      ODBC_ACTION[4] = localObject3;
      ODBC_ACTION[5] = localObject2;
      ODBC_ACTION[6] = localObject2;
      ODBC_ACTION[7] = localObject2;
      ODBC_ACTION[8] = localObject3;
      ODBC_ACTION[''] = localObject3;
      ODBC_ACTION[100] = localObject2;
      ODBC_ACTION[101] = localObject2;
      ODBC_ACTION[105] = localObject2;
      ODBC_ACTION[106] = localObject4;
      ODBC_ACTION[107] = localObject5;
      ODBC_ACTION[108] = null;
      ODBC_ACTION[109] = localObject6;
      ODBC_ACTION[9] = localObject3;
      ODBC_ACTION[10] = localObject3;
      ODBC_ACTION[11] = localObject3;
      ODBC_ACTION[12] = localObject3;
      ODBC_ACTION[13] = localObject3;
      ODBC_ACTION[14] = localObject3;
      ODBC_ACTION[15] = localObject3;
      ODBC_ACTION[16] = localObject3;
      ODBC_ACTION[17] = localObject3;
      ODBC_ACTION[18] = localObject3;
      ODBC_ACTION[19] = localObject3;
      ODBC_ACTION[20] = localObject3;
      ODBC_ACTION[21] = localObject3;
      ODBC_ACTION[22] = localObject3;
      ODBC_ACTION[23] = localObject3;
      ODBC_ACTION[24] = localObject3;
      ODBC_ACTION[25] = localObject3;
      ODBC_ACTION[26] = localObject3;
      ODBC_ACTION[27] = localObject3;
      ODBC_ACTION[28] = localObject3;
      ODBC_ACTION[29] = localObject3;
      ODBC_ACTION[30] = localObject3;
      ODBC_ACTION[31] = localObject3;
      ODBC_ACTION[32] = localObject3;
      ODBC_ACTION[33] = localObject3;
      ODBC_ACTION[34] = localObject3;
      ODBC_ACTION[35] = localObject3;
      ODBC_ACTION[36] = localObject3;
      ODBC_ACTION[37] = localObject3;
      ODBC_ACTION[38] = localObject3;
      ODBC_ACTION[39] = localObject3;
      ODBC_ACTION[40] = localObject3;
      ODBC_ACTION[41] = localObject3;
      ODBC_ACTION[42] = localObject3;
      ODBC_ACTION[43] = localObject3;
      ODBC_ACTION[44] = localObject3;
      ODBC_ACTION[45] = localObject3;
      ODBC_ACTION[46] = localObject3;
      ODBC_ACTION[47] = localObject3;
      ODBC_ACTION[48] = localObject3;
      ODBC_ACTION[49] = localObject3;
      ODBC_ACTION[50] = localObject3;
      ODBC_ACTION[51] = localObject3;
      ODBC_ACTION[52] = localObject3;
      ODBC_ACTION[53] = localObject3;
      ODBC_ACTION[54] = localObject3;
      ODBC_ACTION[55] = localObject3;
      ODBC_ACTION[56] = localObject3;
      ODBC_ACTION[66] = localObject3;
      ODBC_ACTION[58] = localObject3;
      ODBC_ACTION[59] = localObject3;
      ODBC_ACTION[60] = localObject2;
      ODBC_ACTION[61] = localObject2;
      ODBC_ACTION[62] = localObject2;
      ODBC_ACTION[63] = localObject2;
      ODBC_ACTION[64] = localObject2;
      ODBC_ACTION[65] = localObject3;
      ODBC_ACTION[102] = localObject2;
      ODBC_ACTION[103] = localObject2;
      ODBC_ACTION[104] = localObject2;
      ODBC_ACTION[110] = localObject2;
      ODBC_ACTION[111] = localObject4;
      ODBC_ACTION[112] = localObject5;
      ODBC_ACTION[113] = null;
      ODBC_ACTION[114] = localObject6;

      ODBC_ACTION[57] = localObject3;

      ODBC_ACTION[67] = localObject3;
      ODBC_ACTION[68] = localObject3;
      ODBC_ACTION[69] = localObject3;
      ODBC_ACTION[70] = localObject3;
      ODBC_ACTION[71] = localObject3;

      ODBC_ACTION[72] = localObject3;
      ODBC_ACTION[73] = localObject3;
      ODBC_ACTION[74] = localObject3;
      ODBC_ACTION[75] = localObject3;
      ODBC_ACTION[76] = localObject3;

      ODBC_ACTION[77] = localObject3;
      ODBC_ACTION[78] = localObject3;
      ODBC_ACTION[79] = localObject3;

      ODBC_ACTION[80] = localObject3;
      ODBC_ACTION[81] = localObject3;
      ODBC_ACTION[82] = localObject3;
      ODBC_ACTION[83] = localObject3;
      ODBC_ACTION[84] = localObject3;

      ODBC_ACTION[85] = localObject3;
      ODBC_ACTION[86] = localObject3;
      ODBC_ACTION[87] = localObject3;

      ODBC_ACTION[88] = localObject3;
      ODBC_ACTION[89] = localObject3;
      ODBC_ACTION[90] = localObject3;
      ODBC_ACTION[91] = localObject3;
      ODBC_ACTION[92] = localObject3;
      ODBC_ACTION[93] = localObject3;
      ODBC_ACTION[94] = localObject3;

      ODBC_ACTION[95] = localObject3;
      ODBC_ACTION[96] = localObject3;
      ODBC_ACTION[97] = localObject3;
      ODBC_ACTION[98] = localObject3;
      ODBC_ACTION[99] = localObject3;

      ODBC_ACTION[115] = copy((ODBCAction[])localObject8);
      ODBC_ACTION[115][63] = ODBCAction.NONE;
      ODBC_ACTION[115][99] = ODBCAction.NONE;
      ODBC_ACTION[115][67] = ODBCAction.NONE;
      ODBC_ACTION[115][116] = ODBCAction.NONE;
      ODBC_ACTION[115][84] = ODBCAction.NONE;
      ODBC_ACTION[115][100] = ODBCAction.NONE;
      ODBC_ACTION[115][68] = ODBCAction.NONE;
      ODBC_ACTION[115][101] = ODBCAction.NONE;
      ODBC_ACTION[115][69] = ODBCAction.NONE;
      ODBC_ACTION[115][102] = ODBCAction.NONE;
      ODBC_ACTION[115][70] = ODBCAction.NONE;
      ODBC_ACTION[115][111] = ODBCAction.NONE;
      ODBC_ACTION[115][79] = ODBCAction.NONE;
      ODBC_ACTION[116] = newArray(128, ODBCAction.FUNCTION);
      ODBC_ACTION[117] = copy((ODBCAction[])localObject7);
      ODBC_ACTION[117][97] = ODBCAction.NONE;
      ODBC_ACTION[117][65] = ODBCAction.NONE;
      ODBC_ACTION[118] = copy((ODBCAction[])localObject7);
      ODBC_ACTION[118][108] = ODBCAction.NONE;
      ODBC_ACTION[118][76] = ODBCAction.NONE;
      ODBC_ACTION[119] = copy((ODBCAction[])localObject7);
      ODBC_ACTION[119][108] = ODBCAction.NONE;
      ODBC_ACTION[119][76] = ODBCAction.NONE;
      ODBC_ACTION[120] = copyReplacing((ODBCAction[])localObject8, ODBCAction.NONE, ODBCAction.CALL);
      ODBC_ACTION[121] = copyReplacing((ODBCAction[])localObject8, ODBCAction.NONE, ODBCAction.TIME);
      ODBC_ACTION[121][115] = ODBCAction.NONE;
      ODBC_ACTION[121][83] = ODBCAction.NONE;
      ODBC_ACTION[122] = copyReplacing((ODBCAction[])localObject8, ODBCAction.NONE, ODBCAction.TIMESTAMP);
      ODBC_ACTION[123] = copyReplacing((ODBCAction[])localObject8, ODBCAction.NONE, ODBCAction.DATE);
      ODBC_ACTION[124] = copy((ODBCAction[])localObject7);
      ODBC_ACTION[124][115] = ODBCAction.NONE;
      ODBC_ACTION[124][83] = ODBCAction.NONE;
      ODBC_ACTION[125] = copy((ODBCAction[])localObject7);
      ODBC_ACTION[125][99] = ODBCAction.NONE;
      ODBC_ACTION[125][67] = ODBCAction.NONE;
      ODBC_ACTION[126] = copy((ODBCAction[])localObject7);
      ODBC_ACTION[126][97] = ODBCAction.NONE;
      ODBC_ACTION[126][65] = ODBCAction.NONE;
      ODBC_ACTION[127] = copy((ODBCAction[])localObject7);
      ODBC_ACTION[127][112] = ODBCAction.NONE;
      ODBC_ACTION[127][80] = ODBCAction.NONE;
      ODBC_ACTION[''] = copy((ODBCAction[])localObject7);
      ODBC_ACTION[''][101] = ODBCAction.NONE;
      ODBC_ACTION[''][69] = ODBCAction.NONE;
      ODBC_ACTION[''] = copyReplacing((ODBCAction[])localObject8, ODBCAction.NONE, ODBCAction.ESCAPE);
      ODBC_ACTION[''] = copy((ODBCAction[])localObject7);
      ODBC_ACTION[''][110] = ODBCAction.NONE;
      ODBC_ACTION[''][78] = ODBCAction.NONE;
      ODBC_ACTION[''] = copyReplacing((ODBCAction[])localObject8, ODBCAction.NONE, ODBCAction.SCALAR_FUNCTION);
      ODBC_ACTION[''] = copy((ODBCAction[])localObject7);
      ODBC_ACTION[''][106] = ODBCAction.NONE;
      ODBC_ACTION[''][74] = ODBCAction.NONE;
      ODBC_ACTION[''] = copyReplacing((ODBCAction[])localObject8, ODBCAction.NONE, ODBCAction.OUTER_JOIN);
    }
    catch (Throwable localThrowable) {
      localThrowable.printStackTrace();
    }
  }

  static enum ODBCAction
  {
    NONE, 
    COPY, 
    QUESTION, 
    SAVE_DELIMITER, 
    LOOK_FOR_DELIMITER, 
    FUNCTION, 
    CALL, 
    TIME, 
    TIMESTAMP, 
    DATE, 
    ESCAPE, 
    SCALAR_FUNCTION, 
    OUTER_JOIN, 
    UNKNOWN_ESCAPE, 
    END_ODBC_ESCAPE, 
    COMMA, 
    OPEN_PAREN, 
    CLOSE_PAREN;
  }
}