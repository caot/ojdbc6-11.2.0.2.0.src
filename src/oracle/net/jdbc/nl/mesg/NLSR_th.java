package oracle.net.jdbc.nl.mesg;

import java.util.ListResourceBundle;

public class NLSR_th extends ListResourceBundle
{
  static final Object[][] contents = { { "NoFile-04600", "TNS-04600: ไม่พบไฟล์: {0}" }, { "FileReadError-04601", "TNS-04601: เกิดข้อผิดพลาดขณะอ่านไฟล์พารามิเตอร์: {0} ข้อผิดพลาด: {1}" }, { "SyntaxError-04602", "TNS-04602: รูปแบบคำสั่งไม่ถูกต้อง: ต้องการค่า \"{0}\" ก่อนหรือที่ {1}" }, { "UnexpectedChar-04603", "TNS-04603: รูปแบบคำสั่งไม่ถูกต้อง: มีอักขระ \"{0}\" ที่ไม่คาดหมายขณะพาร์ซ {1}" }, { "ParseError-04604", "TNS-04604: ยังไม่ได้เริ่มต้นการพาร์ซออบเจกต์" }, { "UnexpectedChar-04605", "TNS-04605: รูปแบบคำสั่งไม่ถูกต้อง: มีอักขระหรือลิเทอรัล \"{0}\" ที่ไม่คาดหมายก่อนหรือที่ {1}" }, { "NoLiterals-04610", "TNS-04610: ไม่มีลิเทอรัลเหลืออยู่ ถึงจุดสิ้นสุดของคู่ NV" }, { "InvalidChar-04611", "TNS-04611: อักขระการทำงานต่อซึ่งอยู่หลังความเห็นไม่ถูกต้อง" }, { "NullRHS-04612", "TNS-04612: RHS ของ \"{0}\" เป็นนัล" }, { "Internal-04613", "TNS-04613: ข้อผิดพลาดภายใน: {0}" }, { "NoNVPair-04614", "TNS-04614: ไม่พบคู่ NV {0}" }, { "InvalidRHS-04615", "TNS-04615: RHS ไม่ถูกต้องสำหรับ {0}" } };

  public Object[][] getContents()
  {
    return contents;
  }
}