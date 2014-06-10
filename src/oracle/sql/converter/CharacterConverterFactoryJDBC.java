package oracle.sql.converter;

public class CharacterConverterFactoryJDBC extends CharacterConverterFactory
{
  public JdbcCharacterConverters make(int paramInt)
  {
    return CharacterConverterJDBC.getInstance(paramInt);
  }
}