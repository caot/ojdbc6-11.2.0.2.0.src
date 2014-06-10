package oracle.sql.converter;

import oracle.i18n.text.converter.CharacterConverter;
import oracle.i18n.text.converter.CharacterConverterOGS;

public class CharacterConverterFactoryOGS extends CharacterConverterFactory
{
  public JdbcCharacterConverters make(int paramInt)
  {
    CharacterConverter localCharacterConverter = CharacterConverterOGS.getInstance(paramInt);
    return localCharacterConverter == null ? null : new I18CharacterConvertersWrapper(localCharacterConverter);
  }
}