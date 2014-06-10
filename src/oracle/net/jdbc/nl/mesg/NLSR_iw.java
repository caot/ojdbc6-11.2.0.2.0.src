package oracle.net.jdbc.nl.mesg;

import java.util.ListResourceBundle;

public class NLSR_iw extends ListResourceBundle
{
  static final Object[][] contents = { { "NoFile-04600", "TNS-04600: הקובץ לא נמצא: {0}" }, { "FileReadError-04601", "TNS-04601: שגיאה בעת קריאת קובץ פרמטרים: {0}, שגיאה: {1}" }, { "SyntaxError-04602", "TNS-04602: שגיאת תחביר לא תקף: צפוי \"{0}\" לפני או ב- {1}" }, { "UnexpectedChar-04603", "TNS-04603: שגיאת תחביר לא תקף: תו בלתי צפוי \"{0}\" בעת הניתוח של {1}" }, { "ParseError-04604", "TNS-04604: אובייקט לניתוח לא אותחל" }, { "UnexpectedChar-04605", "TNS-04605: שגיאת תחביר לא תקף: תו או ליטרל בלתי צפויים ב\"{0}\" לפני או ב- {1}" }, { "NoLiterals-04610", "TNS-04610: לא נותרו ליטרלים, המערכת הגיעה לסוף זוג NV" }, { "InvalidChar-04611", "TNS-04611: תו המשך לא תקף לאחר הערה" }, { "NullRHS-04612", "TNS-04612: תוכן צד ימין ריק עבור \"{0}\"" }, { "Internal-04613", "TNS-04613: שגיאה פנימית: {0}" }, { "NoNVPair-04614", "TNS-04614: לא נמצא זוג NV {0}" }, { "InvalidRHS-04615", "TNS-04615: תוכן צד ימין לא תקף עבור {0}" } };

  public Object[][] getContents()
  {
    return contents;
  }
}