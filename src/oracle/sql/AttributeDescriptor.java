package oracle.sql;

public class AttributeDescriptor
{
  String attributeName;
  short attributeIdentifier;
  int attributeFlag;
  TypeDescriptor td;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  AttributeDescriptor(String paramString, short paramShort, int paramInt, TypeDescriptor paramTypeDescriptor)
  {
    this.attributeName = paramString;
    this.attributeIdentifier = paramShort;
    this.attributeFlag = paramInt;
    this.td = paramTypeDescriptor;
  }

  void setTypeDescriptor(TypeDescriptor paramTypeDescriptor)
  {
    this.td = paramTypeDescriptor;
  }

  public TypeDescriptor getTypeDescriptor()
  {
    return this.td;
  }

  public String getAttributeName()
  {
    return this.attributeName;
  }
}