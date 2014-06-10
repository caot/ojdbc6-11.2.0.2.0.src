package oracle.sql;

class CharacterSetFactoryThin extends CharacterSetFactory
{
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  public CharacterSet make(int paramInt)
  {
    if (paramInt == -1)
    {
      paramInt = 31;
    }

    if (paramInt == 2000)
    {
      return new CharacterSetAL16UTF16(paramInt);
    }
    if ((paramInt == 870) || (paramInt == 871))
    {
      return new CharacterSetUTF(paramInt);
    }
    if (paramInt == 873)
    {
      return new CharacterSetAL32UTF8(paramInt);
    }
    if (paramInt == 872)
    {
      return new CharacterSetUTFE(paramInt);
    }
    if (paramInt == 2002)
    {
      return new CharacterSetAL16UTF16LE(paramInt);
    }

    CharacterSet localCharacterSet = CharacterSetWithConverter.getInstance(paramInt);

    if (localCharacterSet != null)
    {
      return localCharacterSet;
    }

    return new CharacterSetUnknown(paramInt);
  }
}