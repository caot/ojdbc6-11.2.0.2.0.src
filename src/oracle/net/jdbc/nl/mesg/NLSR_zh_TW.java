package oracle.net.jdbc.nl.mesg;

import java.util.ListResourceBundle;

public class NLSR_zh_TW extends ListResourceBundle
{
  static final Object[][] contents = { { "NoFile-04600", "TNS-04600: 找不到檔案: {0}" }, { "FileReadError-04601", "TNS-04601: 讀取參數檔時發生錯誤: {0}, 錯誤是: {1}" }, { "SyntaxError-04602", "TNS-04602: 無效的語法錯誤: 在 {1} 或它之前應該要有 \"{0}\"" }, { "UnexpectedChar-04603", "TNS-04603: 無效的語法錯誤: 剖析 {1} 時出現未預期的字元 \"{0}\"" }, { "ParseError-04604", "TNS-04604: 剖析物件未起始" }, { "UnexpectedChar-04605", "TNS-04605: 無效的語法錯誤: 在 {1} 或它之前有未預期的字元或 LITERAL \"{0}\" " }, { "NoLiterals-04610", "TNS-04610: 沒有其他常值, 已經達到 NV 組的結束" }, { "InvalidChar-04611", "TNS-04611: 註解之後的繼續字元無效" }, { "NullRHS-04612", "TNS-04612: \"{0}\" 的 RHS 為空值" }, { "Internal-04613", "TNS-04613: 內部錯誤: {0}" }, { "NoNVPair-04614", "TNS-04614: 找不到 NV 組 {0}" }, { "InvalidRHS-04615", "TNS-04615: {0} 的 RHS 無效" } };

  public Object[][] getContents()
  {
    return contents;
  }
}