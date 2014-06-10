package oracle.net.jdbc.nl.mesg;

import java.util.ListResourceBundle;

public class NLSR_ru extends ListResourceBundle
{
  static final Object[][] contents = { { "NoFile-04600", "TNS-04600: Файл не найден: {0}" }, { "FileReadError-04601", "TNS-04601: Ошибка при чтении файла параметров: {0}, ошибка: {1}" }, { "SyntaxError-04602", "TNS-04602: Синтаксическая ошибка: Перед или рядом с {1} ожидается \"{0}\"" }, { "UnexpectedChar-04603", "TNS-04603: Синтаксическая ошибка: Непредвиденный символ \"{0}\" при синтаксическом разборе {1}" }, { "ParseError-04604", "TNS-04604: Объект разбора не инициализирован" }, { "UnexpectedChar-04605", "TNS-04605: Синтаксическая ошибка: Непредвиденный символ или LITERAL \"{0}\" перед или рядом с {1}" }, { "NoLiterals-04610", "TNS-04610: Другие литералы отcутствуют. Достигнут конец пары NV" }, { "InvalidChar-04611", "TNS-04611: Недопустимый символ продолжения после комментария" }, { "NullRHS-04612", "TNS-04612: Неопределенная правая часть для \"{0}\"" }, { "Internal-04613", "TNS-04613: Внутренняя ошибка: {0}" }, { "NoNVPair-04614", "TNS-04614: Пара NV {0} не найдена" }, { "InvalidRHS-04615", "TNS-04615: Недопустимая правая часть для {0}" } };

  public Object[][] getContents()
  {
    return contents;
  }
}