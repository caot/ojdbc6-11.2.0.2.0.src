package oracle.sql.converter;

abstract interface InternalCharacterSetMetaData
{
  public abstract boolean isFixedWidth(int paramInt);

  public abstract int getMaxCharLength(int paramInt);
}