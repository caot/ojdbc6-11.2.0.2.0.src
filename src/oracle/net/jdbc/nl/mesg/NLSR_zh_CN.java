package oracle.net.jdbc.nl.mesg;

import java.util.ListResourceBundle;

public class NLSR_zh_CN extends ListResourceBundle
{
  static final Object[][] contents = { { "NoFile-04600", "TNS-04600: 找不到文件: {0}" }, { "FileReadError-04601", "TNS-04601: 读取参数文件时出错: {0}, 错误为: {1}" }, { "SyntaxError-04602", "TNS-04602: 无效语法错误: 要求 \"{0}\" 在 {1} 的前面或之中" }, { "UnexpectedChar-04603", "TNS-04603: 无效语法错误: 对 {1} 进行语法分析时遇到意外的字符 \"{0}\"" }, { "ParseError-04604", "TNS-04604: 未初始化语法分析对象" }, { "UnexpectedChar-04605", "TNS-04605: 无效语法错误: {1} 之前或之中出现意外的字符或文字 \"{0}\" " }, { "NoLiterals-04610", "TNS-04610: 没有其他文字, 已到达 NV 对结尾" }, { "InvalidChar-04611", "TNS-04611: 备注后存在无效的续行符" }, { "NullRHS-04612", "TNS-04612: \"{0}\" 的 RHS 为空" }, { "Internal-04613", "TNS-04613: 内部错误: {0}" }, { "NoNVPair-04614", "TNS-04614: 找不到 NV 对 {0}" }, { "InvalidRHS-04615", "TNS-04615: {0} 的 RHS 无效" } };

  public Object[][] getContents()
  {
    return contents;
  }
}