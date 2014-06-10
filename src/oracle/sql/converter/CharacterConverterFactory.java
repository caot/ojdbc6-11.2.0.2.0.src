package oracle.sql.converter;

public abstract class CharacterConverterFactory
{
  public abstract JdbcCharacterConverters make(int paramInt);
}