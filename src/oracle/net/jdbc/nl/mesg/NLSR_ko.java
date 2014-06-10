package oracle.net.jdbc.nl.mesg;

import java.util.ListResourceBundle;

public class NLSR_ko extends ListResourceBundle
{
  static final Object[][] contents = { { "NoFile-04600", "TNS-04600: 파일을 찾을 수 없음: {0}" }, { "FileReadError-04601", "TNS-04601: 매개변수 파일 {0}을(를) 읽는 중 오류 발생. 오류: {1}" }, { "SyntaxError-04602", "TNS-04602: 부적합한 구문 오류: {1} 앞 또는 해당 위치에 \"{0}\"이(가) 와야 함" }, { "UnexpectedChar-04603", "TNS-04603: 부적합한 구문 오류: {1}의 구문을 분석하는 중 예상치 않은 문자 \"{0}\" 발생" }, { "ParseError-04604", "TNS-04604: 구문 분석 객체가 초기화되지 않음" }, { "UnexpectedChar-04605", "TNS-04605: 부적합한 구문 오류: {1} 앞 또는 해당 위치에 예상치 않은 문자 또는 리터럴 \"{0}\"이(가) 있음" }, { "NoLiterals-04610", "TNS-04610: 남아 있는 리터럴 없음. NV 쌍의 끝에 도달함" }, { "InvalidChar-04611", "TNS-04611: 설명 뒤에 부적합한 연속 문자가 있음" }, { "NullRHS-04612", "TNS-04612: \"{0}\"에 대해 널인 RHS" }, { "Internal-04613", "TNS-04613: 내부 오류: {0}" }, { "NoNVPair-04614", "TNS-04614: NV 쌍 {0}을(를) 찾을 수 없음" }, { "InvalidRHS-04615", "TNS-04615: {0}에 대해 부적합한 RHS" } };

  public Object[][] getContents()
  {
    return contents;
  }
}