package oracle.net.jdbc.nl.mesg;

import java.util.ListResourceBundle;

public class NLSR_ja extends ListResourceBundle
{
  static final Object[][] contents = { { "NoFile-04600", "TNS-04600: ファイルが見つかりません: {0}" }, { "FileReadError-04601", "TNS-04601: パラメータ・ファイルの読込み中にエラーが発生しました。ファイル: {0}、エラー: {1}" }, { "SyntaxError-04602", "TNS-04602: 無効な構文によるエラー: {1}以前に\"{0}\"が必要です" }, { "UnexpectedChar-04603", "TNS-04603: 無効な構文によるエラー: {1}の解析中に予期しない文字\"{0}\"が見つかりました" }, { "ParseError-04604", "TNS-04604: 解析オブジェクトが初期化されていません" }, { "UnexpectedChar-04605", "TNS-04605: 無効な構文によるエラー: {1}以前に予期しない文字またはリテラル\"{0}\"があります" }, { "NoLiterals-04610", "TNS-04610: リテラルが残っておらず、NVの対の終わりに達しました" }, { "InvalidChar-04611", "TNS-04611: コメントの後に無効な継続文字があります" }, { "NullRHS-04612", "TNS-04612: \"{0}\"のRHSがNULLです" }, { "Internal-04613", "TNS-04613: 内部エラー: {0}" }, { "NoNVPair-04614", "TNS-04614: NVの対{0}が見つかりませんでした" }, { "InvalidRHS-04615", "TNS-04615: {0}のRHSが無効です" } };

  public Object[][] getContents()
  {
    return contents;
  }
}