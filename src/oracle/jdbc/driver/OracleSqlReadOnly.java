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

  static final int[][] TRANSITION = new int[135][];
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

  static final int[][] ACTION = new int[135][];
  static final int INITIAL_STATE = 0;
  static final int RESTART_STATE = 66;
  static final ODBCAction[][] ODBC_ACTION = new ODBCAction[135][];
  static final int cMax = 127;
  private static final int cMaxLength = 128;

  private static final int[] copy(int[] a)
  {
    int[] r = new int[a.length];
    System.arraycopy(a, 0, r, 0, a.length);
    return r;
  }

  private static final ODBCAction[] copy(ODBCAction[] a)
  {
    ODBCAction[] r = new ODBCAction[a.length];
    System.arraycopy(a, 0, r, 0, a.length);
    return r;
  }

  private static final int[] newArray(int length, int value)
  {
    int[] r = new int[length];

    for (int i = 0; i < length; i++) {
      r[i] = value;
    }
    return r;
  }

  private static final ODBCAction[] newArray(int length, ODBCAction value)
  {
    ODBCAction[] r = new ODBCAction[length];

    for (int i = 0; i < length; i++) {
      r[i] = value;
    }
    return r;
  }

  private static final int[] copyReplacing(int[] a, int source, int target)
  {
    int[] r = new int[a.length];

    for (int i = 0; i < r.length; i++)
    {
      int j = a[i];

      if (j == source)
        r[i] = target;
      else {
        r[i] = j;
      }
    }
    return r;
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
      int[] token0 = { 57, 57, 57, 57, 57, 57, 57, 57, 57, 57, 57, 57, 57, 57, 57, 57, 57, 57, 57, 57, 57, 57, 57, 57, 57, 57, 57, 57, 57, 57, 57, 57, 57, 57, 57, 9, 9, 57, 57, 57, 57, 57, 57, 57, 57, 57, 57, 57, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 57, 57, 57, 57, 57, 57, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 57, 57, 57, 57, 9, 57, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 57, 57, 57, 57, 57 };

      int[] token = copy(token0);
      token[34] = 4;
      token[39] = 3;
      token[45] = 2;
      token[47] = 1;
      token[58] = 8;
      token[123] = 115;

      int[] base = copyReplacing(token, 57, 0);
      base[66] = 10;
      base[98] = 10;
      base[67] = 15;
      base[99] = 15;
      base[68] = 19;
      base[100] = 19;
      base[73] = 30;
      base[105] = 30;
      base[109] = 48;
      base[77] = 48;
      base[78] = 100;
      base[110] = 100;
      base[81] = 105;
      base[113] = 105;
      base[83] = 36;
      base[115] = 36;
      base[85] = 42;
      base[117] = 42;
      base[87] = 53;
      base[119] = 53;

      int[] found = copyReplacing(token, 9, 66);
      found[34] = 61;
      found[39] = 60;
      found[45] = 59;
      found[47] = 58;
      found[58] = 134;
      found[32] = 57;
      found[32] = 57;
      found[9] = 57;
      found[10] = 57;
      found[13] = 57;
      found[61] = 57;

      int[] known = copyReplacing(found, 9, 66);
      known[78] = 102;
      known[110] = 102;
      known[81] = 110;
      known[113] = 110;
      known[87] = 67;
      known[119] = 67;
      known[79] = 72;
      known[111] = 72;
      known[70] = 85;
      known[102] = 85;

      int[] arrayOfInt6 = copyReplacing(known, 57, 115);

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

      TRANSITION[0] = base;
      TRANSITION[1] = copy(base);
      TRANSITION[1][42] = 5;
      TRANSITION[2] = copy(base);
      TRANSITION[2][45] = 7;
      TRANSITION[3] = newArray(128, 3);
      TRANSITION[3][39] = 0;
      TRANSITION[100] = copy(token);
      TRANSITION[100][39] = 101;
      TRANSITION[101] = newArray(128, 101);
      TRANSITION[101][39] = 0;

      TRANSITION[105] = copy(token);
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
      TRANSITION[8] = copyReplacing(token, 9, 8);
      TRANSITION[9] = token;
      TRANSITION[10] = copy(token);
      TRANSITION[10][69] = 11;
      TRANSITION[10][101] = 11;
      TRANSITION[11] = copy(token);
      TRANSITION[11][71] = 12;
      TRANSITION[11][103] = 12;
      TRANSITION[12] = copy(token);
      TRANSITION[12][73] = 13;
      TRANSITION[12][105] = 13;
      TRANSITION[13] = copy(token);
      TRANSITION[13][78] = 14;
      TRANSITION[13][110] = 14;
      TRANSITION[14] = known;
      TRANSITION[15] = copy(token);
      TRANSITION[15][65] = 16;
      TRANSITION[15][97] = 16;
      TRANSITION[16] = copy(token);
      TRANSITION[16][76] = 17;
      TRANSITION[16][108] = 17;
      TRANSITION[17] = copy(token);
      TRANSITION[17][76] = 18;
      TRANSITION[17][108] = 18;
      TRANSITION[18] = known;
      TRANSITION[19] = copy(token);
      TRANSITION[19][69] = 20;
      TRANSITION[19][101] = 20;
      TRANSITION[20] = copy(token);
      TRANSITION[20][67] = 21;
      TRANSITION[20][99] = 21;
      TRANSITION[20][76] = 26;
      TRANSITION[20][108] = 26;
      TRANSITION[21] = copy(token);
      TRANSITION[21][76] = 22;
      TRANSITION[21][108] = 22;
      TRANSITION[22] = copy(token);
      TRANSITION[22][65] = 23;
      TRANSITION[22][97] = 23;
      TRANSITION[23] = copy(token);
      TRANSITION[23][82] = 24;
      TRANSITION[23][114] = 24;
      TRANSITION[24] = copy(token);
      TRANSITION[24][69] = 25;
      TRANSITION[24][101] = 25;
      TRANSITION[25] = known;
      TRANSITION[26] = copy(token);
      TRANSITION[26][69] = 27;
      TRANSITION[26][101] = 27;
      TRANSITION[27] = copy(token);
      TRANSITION[27][84] = 28;
      TRANSITION[27][116] = 28;
      TRANSITION[28] = copy(token);
      TRANSITION[28][69] = 29;
      TRANSITION[28][101] = 29;
      TRANSITION[29] = known;
      TRANSITION[30] = copy(token);
      TRANSITION[30][78] = 31;
      TRANSITION[30][110] = 31;
      TRANSITION[31] = copy(token);
      TRANSITION[31][83] = 32;
      TRANSITION[31][115] = 32;
      TRANSITION[32] = copy(token);
      TRANSITION[32][69] = 33;
      TRANSITION[32][101] = 33;
      TRANSITION[33] = copy(token);
      TRANSITION[33][82] = 34;
      TRANSITION[33][114] = 34;
      TRANSITION[34] = copy(token);
      TRANSITION[34][84] = 35;
      TRANSITION[34][116] = 35;
      TRANSITION[35] = known;
      TRANSITION[36] = copy(token);
      TRANSITION[36][69] = 37;
      TRANSITION[36][101] = 37;
      TRANSITION[37] = copy(token);
      TRANSITION[37][76] = 38;
      TRANSITION[37][108] = 38;
      TRANSITION[38] = copy(token);
      TRANSITION[38][69] = 39;
      TRANSITION[38][101] = 39;
      TRANSITION[39] = copy(token);
      TRANSITION[39][67] = 40;
      TRANSITION[39][99] = 40;
      TRANSITION[40] = copy(token);
      TRANSITION[40][84] = 41;
      TRANSITION[40][116] = 41;
      TRANSITION[41] = known;
      TRANSITION[42] = copy(token);
      TRANSITION[42][80] = 43;
      TRANSITION[42][112] = 43;
      TRANSITION[43] = copy(token);
      TRANSITION[43][68] = 44;
      TRANSITION[43][100] = 44;
      TRANSITION[44] = copy(token);
      TRANSITION[44][65] = 45;
      TRANSITION[44][97] = 45;
      TRANSITION[45] = copy(token);
      TRANSITION[45][84] = 46;
      TRANSITION[45][116] = 46;
      TRANSITION[46] = copy(token);
      TRANSITION[46][69] = 47;
      TRANSITION[46][101] = 47;
      TRANSITION[47] = known;
      TRANSITION[48] = copy(token);
      TRANSITION[48][69] = 49;
      TRANSITION[48][101] = 49;
      TRANSITION[49] = copy(token);
      TRANSITION[49][82] = 50;
      TRANSITION[49][114] = 50;
      TRANSITION[50] = copy(token);
      TRANSITION[50][71] = 51;
      TRANSITION[50][103] = 51;
      TRANSITION[51] = copy(token);
      TRANSITION[51][69] = 52;
      TRANSITION[51][101] = 52;
      TRANSITION[52] = known;
      TRANSITION[53] = copy(token);
      TRANSITION[53][73] = 54;
      TRANSITION[53][105] = 54;
      TRANSITION[54] = copy(token);
      TRANSITION[54][84] = 55;
      TRANSITION[54][116] = 55;
      TRANSITION[55] = copy(token);
      TRANSITION[55][72] = 56;
      TRANSITION[55][104] = 56;
      TRANSITION[56] = known;
      TRANSITION[66] = found;
      TRANSITION[58] = copy(known);
      TRANSITION[58][42] = 62;
      TRANSITION[59] = copy(known);
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
      TRANSITION[65] = copyReplacing(found, 66, 65);
      TRANSITION[134] = copyReplacing(found, 66, 65);
      TRANSITION[134][32] = 134;
      TRANSITION[134][10] = 134;
      TRANSITION[134][13] = 134;
      TRANSITION[134][9] = 134;

      TRANSITION[57] = known;
      TRANSITION[67] = copy(found);
      TRANSITION[67][72] = 68;
      TRANSITION[67][104] = 68;
      TRANSITION[68] = copy(found);
      TRANSITION[68][69] = 69;
      TRANSITION[68][101] = 69;
      TRANSITION[69] = copy(found);
      TRANSITION[69][82] = 70;
      TRANSITION[69][114] = 70;
      TRANSITION[70] = copy(found);
      TRANSITION[70][69] = 71;
      TRANSITION[70][101] = 71;
      TRANSITION[71] = known;

      TRANSITION[72] = copy(found);
      TRANSITION[72][82] = 73;
      TRANSITION[72][114] = 73;
      TRANSITION[73] = copy(found);
      TRANSITION[73][68] = 74;
      TRANSITION[73][100] = 74;
      TRANSITION[74] = copy(found);
      TRANSITION[74][69] = 75;
      TRANSITION[74][101] = 75;
      TRANSITION[75] = copy(found);
      TRANSITION[75][82] = 76;
      TRANSITION[75][114] = 76;
      TRANSITION[76] = copyReplacing(known, 57, 77);
      TRANSITION[76][47] = 80;
      TRANSITION[76][45] = 83;

      TRANSITION[77] = copyReplacing(known, 57, 77);
      TRANSITION[77][47] = 80;
      TRANSITION[80] = copy(known);
      TRANSITION[80][42] = 81;
      TRANSITION[81] = newArray(128, 81);
      TRANSITION[81][42] = 82;
      TRANSITION[82] = newArray(128, 81);
      TRANSITION[82][47] = 77;

      TRANSITION[77][45] = 83;
      TRANSITION[83] = copy(known);
      TRANSITION[83][45] = 84;
      TRANSITION[84] = newArray(128, 84);
      TRANSITION[84][10] = 77;

      TRANSITION[77][66] = 78;
      TRANSITION[77][98] = 78;
      TRANSITION[78] = copy(found);
      TRANSITION[78][89] = 79;
      TRANSITION[78][121] = 79;
      TRANSITION[79] = known;

      TRANSITION[85] = copy(known);
      TRANSITION[85][79] = 86;
      TRANSITION[85][111] = 86;
      TRANSITION[86] = copy(known);
      TRANSITION[86][82] = 87;
      TRANSITION[86][114] = 87;
      TRANSITION[87] = copyReplacing(found, 57, 88);
      TRANSITION[87][47] = 95;
      TRANSITION[87][45] = 98;

      TRANSITION[88] = copyReplacing(found, 57, 88);
      TRANSITION[88][47] = 95;
      TRANSITION[95] = copy(known);
      TRANSITION[95][42] = 96;
      TRANSITION[96] = newArray(128, 96);
      TRANSITION[96][42] = 97;
      TRANSITION[97] = newArray(128, 96);
      TRANSITION[97][47] = 88;

      TRANSITION[88][45] = 98;
      TRANSITION[98] = copy(known);
      TRANSITION[98][45] = 99;
      TRANSITION[99] = newArray(128, 99);
      TRANSITION[99][10] = 88;

      TRANSITION[88][85] = 89;
      TRANSITION[88][117] = 89;
      TRANSITION[89] = copy(known);
      TRANSITION[89][80] = 90;
      TRANSITION[89][112] = 90;
      TRANSITION[90] = copy(known);
      TRANSITION[90][68] = 91;
      TRANSITION[90][100] = 91;
      TRANSITION[91] = copy(known);
      TRANSITION[91][65] = 92;
      TRANSITION[91][97] = 92;
      TRANSITION[92] = copy(known);
      TRANSITION[92][84] = 93;
      TRANSITION[92][116] = 93;
      TRANSITION[93] = copy(known);
      TRANSITION[93][69] = 94;
      TRANSITION[93][101] = 94;
      TRANSITION[94] = known;

      TRANSITION[102] = copy(found);
      TRANSITION[102][39] = 103;
      TRANSITION[103] = newArray(128, 103);
      TRANSITION[103][39] = 104;

      TRANSITION[104] = newArray(128, 57);
      TRANSITION[104][39] = 103;

      TRANSITION[110] = copy(known);
      TRANSITION[110][39] = 111;
      TRANSITION[111] = newArray(128, 112);
      TRANSITION[112] = newArray(128, 112);

      TRANSITION[113] = newArray(128, 114);
      TRANSITION[114] = newArray(128, 112);
      TRANSITION[114][39] = 57;

      TRANSITION[115] = arrayOfInt6;
      TRANSITION[116] = known;
      TRANSITION[117] = copy(known);
      TRANSITION[117][97] = 118;
      TRANSITION[117][65] = 118;
      TRANSITION[118] = copy(known);
      TRANSITION[118][108] = 119;
      TRANSITION[118][76] = 119;
      TRANSITION[119] = copy(known);
      TRANSITION[119][108] = 120;
      TRANSITION[119][76] = 120;
      TRANSITION[120] = known;
      TRANSITION[121] = copy(known);
      TRANSITION[121][115] = 122;
      TRANSITION[121][83] = 122;
      TRANSITION[122] = known;
      TRANSITION[123] = known;
      TRANSITION[124] = copy(known);
      TRANSITION[124][115] = 125;
      TRANSITION[124][83] = 125;
      TRANSITION[125] = copy(known);
      TRANSITION[125][99] = 126;
      TRANSITION[125][67] = 126;
      TRANSITION[126] = copy(known);
      TRANSITION[126][97] = 127;
      TRANSITION[126][65] = 127;
      TRANSITION[127] = copy(known);
      TRANSITION[127][112] = 128;
      TRANSITION[127][80] = 128;
      TRANSITION[128] = copy(known);
      TRANSITION[128][101] = 129;
      TRANSITION[128][69] = 129;
      TRANSITION[129] = known;
      TRANSITION[130] = copy(known);
      TRANSITION[130][110] = 131;
      TRANSITION[130][78] = 131;
      TRANSITION[131] = known;
      TRANSITION[132] = copy(known);
      TRANSITION[132][106] = 133;
      TRANSITION[132][74] = 133;
      TRANSITION[133] = known;

      int[] none = newArray(128, 0);

      int[] localObject2 = copy((int[])none);
      localObject2[63] = 14;

      int[] localObject3 = copy((int[])localObject2);
      localObject3[123] = 5;

      int[] localObject4 = new int[128];

      for (int i = 0; i < localObject4.length; i++) {
        if (TRANSITION[8][i] == 8)
          localObject4[i] = 15;
        else
          localObject4[i] = 16;
      }
      int[] localObject5 = new int[128];

      for (int j = 0; j < localObject5.length; j++) {
        if (TRANSITION[65][j] == 65)
          localObject5[j] = 15;
        else
          localObject5[j] = 16;
      }
      int[] localObject6 = copy((int[])localObject5);
      localObject6[32] = 0;
      localObject6[10] = 0;
      localObject6[9] = 0;
      localObject6[13] = 0;

      int[] localObject7 = copy((int[])none);

      for (int k = 0; k < localObject7.length; k++) {
        if (known[k] != 66)
          localObject7[k] = 5;
      }
      int[] localObject8 = copyReplacing((int[])localObject7, 5, 6);
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

      int[] arrayOfInt18 = copy((int[])none);
      arrayOfInt18[39] = 17;

      int[] arrayOfInt19 = copyReplacing((int[])none, 0, 18);
      arrayOfInt19[39] = 0;

      int[] arrayOfInt20 = copyReplacing((int[])none, 0, 19);

      int[] arrayOfInt21 = copyReplacing((int[])none, 0, 20);

      int[] arrayOfInt22 = copy(arrayOfInt21);
      arrayOfInt22[39] = 0;

      ACTION[BASE] = localObject3;
      ACTION[BASE_1] = localObject3;
      ACTION[BASE_2] = localObject3;
      ACTION[3] = none;
      ACTION[4] = none;
      ACTION[5] = none;
      ACTION[6] = none;
      ACTION[7] = none;
      ACTION[8] = localObject4;
      ACTION[134] = localObject6;
      ACTION[100] = arrayOfInt18;
      ACTION[101] = arrayOfInt19;
      ACTION[105] = none;
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
      ACTION[60] = none;
      ACTION[61] = none;
      ACTION[62] = none;
      ACTION[63] = none;
      ACTION[64] = none;
      ACTION[65] = localObject5;
      ACTION[102] = arrayOfInt18;
      ACTION[103] = none;
      ACTION[104] = arrayOfInt19;
      ACTION[110] = none;
      ACTION[111] = arrayOfInt20;
      ACTION[112] = arrayOfInt21;
      ACTION[113] = null;
      ACTION[114] = arrayOfInt22;

      ACTION[57] = localObject2;

      ACTION[67] = none;
      ACTION[68] = none;
      ACTION[69] = none;
      ACTION[70] = none;
      ACTION[71] = arrayOfInt15;

      ACTION[72] = none;
      ACTION[73] = none;
      ACTION[74] = none;
      ACTION[75] = none;
      ACTION[76] = arrayOfInt13;

      ACTION[77] = none;
      ACTION[78] = none;
      ACTION[79] = arrayOfInt14;

      ACTION[80] = none;
      ACTION[81] = none;
      ACTION[82] = none;
      ACTION[83] = none;
      ACTION[84] = none;

      ACTION[85] = none;
      ACTION[86] = none;
      ACTION[87] = arrayOfInt16;

      ACTION[88] = localObject2;
      ACTION[89] = none;
      ACTION[90] = none;
      ACTION[91] = none;
      ACTION[92] = none;
      ACTION[93] = none;
      ACTION[94] = arrayOfInt17;

      ACTION[95] = none;
      ACTION[96] = none;
      ACTION[97] = none;
      ACTION[98] = none;
      ACTION[99] = none;

      ACTION[115] = copy((int[])none);
      ACTION[115][63] = 14;
      ACTION[116] = none;
      ACTION[117] = none;
      ACTION[118] = none;
      ACTION[119] = none;
      ACTION[120] = none;
      ACTION[121] = none;
      ACTION[122] = none;
      ACTION[123] = none;
      ACTION[124] = none;
      ACTION[125] = none;
      ACTION[126] = none;
      ACTION[127] = none;
      ACTION[128] = none;
      ACTION[129] = none;
      ACTION[130] = none;
      ACTION[131] = none;
      ACTION[132] = none;
      ACTION[133] = none;

      ODBCAction[] none_odbcaction = newArray(128, ODBCAction.NONE);

      ODBCAction[] localObject2_odbcaction = newArray(128, ODBCAction.COPY);

      ODBCAction[] localObject3_odbcaction = copy((ODBCAction[])localObject2_odbcaction);
      localObject3_odbcaction[123] = ODBCAction.NONE;
      localObject3_odbcaction[63] = ODBCAction.QUESTION;
      localObject3_odbcaction[125] = ODBCAction.END_ODBC_ESCAPE;
      localObject3_odbcaction[44] = ODBCAction.COMMA;
      localObject3_odbcaction[40] = ODBCAction.OPEN_PAREN;
      localObject3_odbcaction[41] = ODBCAction.CLOSE_PAREN;

      ODBCAction[] localObject4_odbcaction = copyReplacing((ODBCAction[])localObject2_odbcaction, ODBCAction.COPY, ODBCAction.SAVE_DELIMITER);

      ODBCAction[] localObject5_odbcaction = copyReplacing((ODBCAction[])localObject2_odbcaction, ODBCAction.COPY, ODBCAction.LOOK_FOR_DELIMITER);

      ODBCAction[] localObject6_odbcaction = copy((ODBCAction[])localObject5_odbcaction);
      localObject6_odbcaction[39] = ODBCAction.COPY;

      ODBCAction[] localObject7_odbcaction = newArray(128, ODBCAction.UNKNOWN_ESCAPE);

      ODBCAction[] localObject8_odbcaction = copy((ODBCAction[])none_odbcaction);
      for (int m = 0; m < token0.length; m++) {
        if (token0[m] == 9) localObject7_odbcaction[m] = ODBCAction.UNKNOWN_ESCAPE;
      }

      ODBC_ACTION[0] = localObject3_odbcaction;
      ODBC_ACTION[1] = localObject3_odbcaction;
      ODBC_ACTION[2] = localObject3_odbcaction;
      ODBC_ACTION[3] = localObject2_odbcaction;
      ODBC_ACTION[4] = localObject3_odbcaction;
      ODBC_ACTION[5] = localObject2_odbcaction;
      ODBC_ACTION[6] = localObject2_odbcaction;
      ODBC_ACTION[7] = localObject2_odbcaction;
      ODBC_ACTION[8] = localObject3_odbcaction;
      ODBC_ACTION[134] = localObject3_odbcaction;
      ODBC_ACTION[100] = localObject2_odbcaction;
      ODBC_ACTION[101] = localObject2_odbcaction;
      ODBC_ACTION[105] = localObject2_odbcaction;
      ODBC_ACTION[106] = localObject4_odbcaction;
      ODBC_ACTION[107] = localObject5_odbcaction;
      ODBC_ACTION[108] = null;
      ODBC_ACTION[109] = localObject6_odbcaction;
      ODBC_ACTION[9] = localObject3_odbcaction;
      ODBC_ACTION[10] = localObject3_odbcaction;
      ODBC_ACTION[11] = localObject3_odbcaction;
      ODBC_ACTION[12] = localObject3_odbcaction;
      ODBC_ACTION[13] = localObject3_odbcaction;
      ODBC_ACTION[14] = localObject3_odbcaction;
      ODBC_ACTION[15] = localObject3_odbcaction;
      ODBC_ACTION[16] = localObject3_odbcaction;
      ODBC_ACTION[17] = localObject3_odbcaction;
      ODBC_ACTION[18] = localObject3_odbcaction;
      ODBC_ACTION[19] = localObject3_odbcaction;
      ODBC_ACTION[20] = localObject3_odbcaction;
      ODBC_ACTION[21] = localObject3_odbcaction;
      ODBC_ACTION[22] = localObject3_odbcaction;
      ODBC_ACTION[23] = localObject3_odbcaction;
      ODBC_ACTION[24] = localObject3_odbcaction;
      ODBC_ACTION[25] = localObject3_odbcaction;
      ODBC_ACTION[26] = localObject3_odbcaction;
      ODBC_ACTION[27] = localObject3_odbcaction;
      ODBC_ACTION[28] = localObject3_odbcaction;
      ODBC_ACTION[29] = localObject3_odbcaction;
      ODBC_ACTION[30] = localObject3_odbcaction;
      ODBC_ACTION[31] = localObject3_odbcaction;
      ODBC_ACTION[32] = localObject3_odbcaction;
      ODBC_ACTION[33] = localObject3_odbcaction;
      ODBC_ACTION[34] = localObject3_odbcaction;
      ODBC_ACTION[35] = localObject3_odbcaction;
      ODBC_ACTION[36] = localObject3_odbcaction;
      ODBC_ACTION[37] = localObject3_odbcaction;
      ODBC_ACTION[38] = localObject3_odbcaction;
      ODBC_ACTION[39] = localObject3_odbcaction;
      ODBC_ACTION[40] = localObject3_odbcaction;
      ODBC_ACTION[41] = localObject3_odbcaction;
      ODBC_ACTION[42] = localObject3_odbcaction;
      ODBC_ACTION[43] = localObject3_odbcaction;
      ODBC_ACTION[44] = localObject3_odbcaction;
      ODBC_ACTION[45] = localObject3_odbcaction;
      ODBC_ACTION[46] = localObject3_odbcaction;
      ODBC_ACTION[47] = localObject3_odbcaction;
      ODBC_ACTION[48] = localObject3_odbcaction;
      ODBC_ACTION[49] = localObject3_odbcaction;
      ODBC_ACTION[50] = localObject3_odbcaction;
      ODBC_ACTION[51] = localObject3_odbcaction;
      ODBC_ACTION[52] = localObject3_odbcaction;
      ODBC_ACTION[53] = localObject3_odbcaction;
      ODBC_ACTION[54] = localObject3_odbcaction;
      ODBC_ACTION[55] = localObject3_odbcaction;
      ODBC_ACTION[56] = localObject3_odbcaction;
      ODBC_ACTION[66] = localObject3_odbcaction;
      ODBC_ACTION[58] = localObject3_odbcaction;
      ODBC_ACTION[59] = localObject3_odbcaction;
      ODBC_ACTION[60] = localObject2_odbcaction;
      ODBC_ACTION[61] = localObject2_odbcaction;
      ODBC_ACTION[62] = localObject2_odbcaction;
      ODBC_ACTION[63] = localObject2_odbcaction;
      ODBC_ACTION[64] = localObject2_odbcaction;
      ODBC_ACTION[65] = localObject3_odbcaction;
      ODBC_ACTION[102] = localObject2_odbcaction;
      ODBC_ACTION[103] = localObject2_odbcaction;
      ODBC_ACTION[104] = localObject2_odbcaction;
      ODBC_ACTION[110] = localObject2_odbcaction;
      ODBC_ACTION[111] = localObject4_odbcaction;
      ODBC_ACTION[112] = localObject5_odbcaction;
      ODBC_ACTION[113] = null;
      ODBC_ACTION[114] = localObject6_odbcaction;

      ODBC_ACTION[57] = localObject3_odbcaction;

      ODBC_ACTION[67] = localObject3_odbcaction;
      ODBC_ACTION[68] = localObject3_odbcaction;
      ODBC_ACTION[69] = localObject3_odbcaction;
      ODBC_ACTION[70] = localObject3_odbcaction;
      ODBC_ACTION[71] = localObject3_odbcaction;

      ODBC_ACTION[72] = localObject3_odbcaction;
      ODBC_ACTION[73] = localObject3_odbcaction;
      ODBC_ACTION[74] = localObject3_odbcaction;
      ODBC_ACTION[75] = localObject3_odbcaction;
      ODBC_ACTION[76] = localObject3_odbcaction;

      ODBC_ACTION[77] = localObject3_odbcaction;
      ODBC_ACTION[78] = localObject3_odbcaction;
      ODBC_ACTION[79] = localObject3_odbcaction;

      ODBC_ACTION[80] = localObject3_odbcaction;
      ODBC_ACTION[81] = localObject3_odbcaction;
      ODBC_ACTION[82] = localObject3_odbcaction;
      ODBC_ACTION[83] = localObject3_odbcaction;
      ODBC_ACTION[84] = localObject3_odbcaction;

      ODBC_ACTION[85] = localObject3_odbcaction;
      ODBC_ACTION[86] = localObject3_odbcaction;
      ODBC_ACTION[87] = localObject3_odbcaction;

      ODBC_ACTION[88] = localObject3_odbcaction;
      ODBC_ACTION[89] = localObject3_odbcaction;
      ODBC_ACTION[90] = localObject3_odbcaction;
      ODBC_ACTION[91] = localObject3_odbcaction;
      ODBC_ACTION[92] = localObject3_odbcaction;
      ODBC_ACTION[93] = localObject3_odbcaction;
      ODBC_ACTION[94] = localObject3_odbcaction;

      ODBC_ACTION[95] = localObject3_odbcaction;
      ODBC_ACTION[96] = localObject3_odbcaction;
      ODBC_ACTION[97] = localObject3_odbcaction;
      ODBC_ACTION[98] = localObject3_odbcaction;
      ODBC_ACTION[99] = localObject3_odbcaction;

      ODBC_ACTION[115] = copy((ODBCAction[])localObject8_odbcaction);
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
      ODBC_ACTION[117] = copy((ODBCAction[])localObject7_odbcaction);
      ODBC_ACTION[117][97] = ODBCAction.NONE;
      ODBC_ACTION[117][65] = ODBCAction.NONE;
      ODBC_ACTION[118] = copy((ODBCAction[])localObject7_odbcaction);
      ODBC_ACTION[118][108] = ODBCAction.NONE;
      ODBC_ACTION[118][76] = ODBCAction.NONE;
      ODBC_ACTION[119] = copy((ODBCAction[])localObject7_odbcaction);
      ODBC_ACTION[119][108] = ODBCAction.NONE;
      ODBC_ACTION[119][76] = ODBCAction.NONE;
      ODBC_ACTION[120] = copyReplacing((ODBCAction[])localObject8_odbcaction, ODBCAction.NONE, ODBCAction.CALL);
      ODBC_ACTION[121] = copyReplacing((ODBCAction[])localObject8_odbcaction, ODBCAction.NONE, ODBCAction.TIME);
      ODBC_ACTION[121][115] = ODBCAction.NONE;
      ODBC_ACTION[121][83] = ODBCAction.NONE;
      ODBC_ACTION[122] = copyReplacing((ODBCAction[])localObject8_odbcaction, ODBCAction.NONE, ODBCAction.TIMESTAMP);
      ODBC_ACTION[123] = copyReplacing((ODBCAction[])localObject8_odbcaction, ODBCAction.NONE, ODBCAction.DATE);
      ODBC_ACTION[124] = copy((ODBCAction[])localObject7_odbcaction);
      ODBC_ACTION[124][115] = ODBCAction.NONE;
      ODBC_ACTION[124][83] = ODBCAction.NONE;
      ODBC_ACTION[125] = copy((ODBCAction[])localObject7_odbcaction);
      ODBC_ACTION[125][99] = ODBCAction.NONE;
      ODBC_ACTION[125][67] = ODBCAction.NONE;
      ODBC_ACTION[126] = copy((ODBCAction[])localObject7_odbcaction);
      ODBC_ACTION[126][97] = ODBCAction.NONE;
      ODBC_ACTION[126][65] = ODBCAction.NONE;
      ODBC_ACTION[127] = copy((ODBCAction[])localObject7_odbcaction);
      ODBC_ACTION[127][112] = ODBCAction.NONE;
      ODBC_ACTION[127][80] = ODBCAction.NONE;
      ODBC_ACTION[128] = copy((ODBCAction[])localObject7_odbcaction);
      ODBC_ACTION[128][101] = ODBCAction.NONE;
      ODBC_ACTION[128][69] = ODBCAction.NONE;
      ODBC_ACTION[129] = copyReplacing((ODBCAction[])localObject8_odbcaction, ODBCAction.NONE, ODBCAction.ESCAPE);
      ODBC_ACTION[130] = copy((ODBCAction[])localObject7_odbcaction);
      ODBC_ACTION[130][110] = ODBCAction.NONE;
      ODBC_ACTION[130][78] = ODBCAction.NONE;
      ODBC_ACTION[131] = copyReplacing((ODBCAction[])localObject8_odbcaction, ODBCAction.NONE, ODBCAction.SCALAR_FUNCTION);
      ODBC_ACTION[132] = copy((ODBCAction[])localObject7_odbcaction);
      ODBC_ACTION[132][106] = ODBCAction.NONE;
      ODBC_ACTION[132][74] = ODBCAction.NONE;
      ODBC_ACTION[133] = copyReplacing((ODBCAction[])localObject8_odbcaction, ODBCAction.NONE, ODBCAction.OUTER_JOIN);
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