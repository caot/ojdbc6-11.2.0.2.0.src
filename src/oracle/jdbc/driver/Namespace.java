package oracle.jdbc.driver;

class Namespace
{
  static final int ATTRIBUTE_MAX_LENGTH = 30;
  static final int VALUE_MAX_LENGTH = 4000;
  String name;
  boolean clear;
  String[] keys;
  String[] values;
  int nbPairs;

  Namespace(String paramString)
  {
    if (paramString == null) {
      throw new NullPointerException();
    }
    this.name = paramString;
    this.clear = false;
    this.nbPairs = 0;
    this.keys = new String[5];
    this.values = new String[5];
  }

  void clear()
  {
    this.clear = true;

    for (int i = 0; i < this.nbPairs; i++)
    {
      this.keys[i] = null;
      this.values[i] = null;
    }
    this.nbPairs = 0;
  }

  void setAttribute(String paramString1, String paramString2)
  {
    if ((paramString1 == null) || (paramString2 == null) || (paramString1.equals(""))) {
      throw new NullPointerException();
    }
    if (this.nbPairs == this.keys.length)
    {
      String[] arrayOfString1 = new String[this.keys.length * 2];
      String[] arrayOfString2 = new String[this.keys.length * 2];
      System.arraycopy(this.keys, 0, arrayOfString1, 0, this.keys.length);
      System.arraycopy(this.values, 0, arrayOfString2, 0, this.values.length);
      this.keys = arrayOfString1;
      this.values = arrayOfString2;
    }
    this.keys[this.nbPairs] = paramString1;
    this.values[this.nbPairs] = paramString2;
    this.nbPairs += 1;
  }
}