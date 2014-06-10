package oracle.sql.converter;

import java.util.List;
import oracle.i18n.text.OraBoot;

class Orai18nCharacterSetMetaData
  implements InternalCharacterSetMetaData
{
  OraBoot oraBoot = OraBoot.getInstance();

  public boolean isFixedWidth(int paramInt)
  {
    String str = this.oraBoot.getCharSetName("" + paramInt);
    return this.oraBoot.getCharSetIsFixed().contains(str);
  }

  public int getMaxCharLength(int paramInt)
  {
    String str = this.oraBoot.getCharsetMaxCharLen("" + paramInt);
    return str != null ? Integer.parseInt(str) & 0xFF : 0;
  }
}