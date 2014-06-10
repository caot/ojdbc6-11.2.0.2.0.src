package oracle.net.jdbc.nl.mesg;

import java.util.ListResourceBundle;

public class NLSR_ar extends ListResourceBundle
{
  static final Object[][] contents = { { "NoFile-04600", "TNS-04600: لم يتم العثور على الملف: {0}" }, { "FileReadError-04601", "TNS-04601: حدث خطأ أثناء قراءة ملف المعلمة: {0}، الخطأ هو: {1}" }, { "SyntaxError-04602", "TNS-04602: خطأ صياغة غير صالح: \"{0}\" متوقع قبل أو عند {1}" }, { "UnexpectedChar-04603", "TNS-04603: خطأ صياغة غير صالح: ظهرت قيمة حرفية رقمية غير متوقعة \"{0}\" أثناء تحليل {1} لغويًا" }, { "ParseError-04604", "TNS-04604: لم تتم تهيئة كائن التحليل اللغوي" }, { "UnexpectedChar-04605", "TNS-04605: خطأ صياغة غير صالح: قيمة حرفية رقمية أو LITERAL غير متوقعة \"{0}\" قبل أو عند {1}" }, { "NoLiterals-04610", "TNS-04610: لم تتبقَّ أحرف، تم الوصول إلى نهاية زوج NV" }, { "InvalidChar-04611", "TNS-04611: حرف استمرار غير صالح بعد التعليق" }, { "NullRHS-04612", "TNS-04612: RHS خالي لـ \"{0}\"" }, { "Internal-04613", "TNS-04613: خطأ داخلي: {0}" }, { "NoNVPair-04614", "TNS-04614: لم يتم العثور على زوج NV {0}" }, { "InvalidRHS-04615", "TNS-04615: RHS غير صالح لـ {0}" } };

  public Object[][] getContents()
  {
    return contents;
  }
}