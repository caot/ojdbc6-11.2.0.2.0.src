package oracle.net.jdbc.nl.mesg;

import java.util.ListResourceBundle;

public class NLSR_tr extends ListResourceBundle
{
  static final Object[][] contents = { { "NoFile-04600", "TNS-04600: Dosya bulunamadı: {0}" }, { "FileReadError-04601", "TNS-04601: Parametre dosyasını okurken hata: {0}, hata: {1}" }, { "SyntaxError-04602", "TNS-04602: Geçersiz sözdizim hatası: \"{0}\" değeri {1} değerinden önce veya onunla birlikte beklendi" }, { "UnexpectedChar-04603", "TNS-04603: Geçersiz sözdizim hatası: \"{0}\" karakteri {1} ayrıştırılırken beklenmiyordu" }, { "ParseError-04604", "TNS-04604: Ayrıştırma nesnesi başlatılmadı" }, { "UnexpectedChar-04605", "TNS-04605: Geçersiz sözdizim hatası: \"{0}\" karakter veya LITERAL değeri {1} öncesinde veya onunla birlikte beklenmiyordu" }, { "NoLiterals-04610", "TNS-04610: Kalan sabit metin yok, NV çiftinin sonuna erişildi" }, { "InvalidChar-04611", "TNS-04611: Açıklama sonrasında geçersiz devam karakteri" }, { "NullRHS-04612", "TNS-04612: \"{0}\" için boş RHS" }, { "Internal-04613", "TNS-04613: Dahili hata: {0}" }, { "NoNVPair-04614", "TNS-04614: NV Çifti {0} bulunamadı" }, { "InvalidRHS-04615", "TNS-04615: {0} için geçersiz RHS" } };

  public Object[][] getContents()
  {
    return contents;
  }
}