package oracle.jdbc.driver;

import java.io.IOException;
import java.sql.SQLException;

final class T4CTTIokeyval extends T4CTTIfun
{
  static final byte KVASET_KPDUSR = 1;
  static final byte KVACLA_KPDUSR = 2;
  private byte[] namespaceByteArr;
  private char[] charArr;
  private byte[][] attrArr;
  private int[] attrArrSize;
  private byte[][] valueArr;
  private int[] valueArrSize;
  private byte[] kvalflg;
  private int nbNamespaceBytes;
  private int nbKeyVal;
  private boolean clear;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  T4CTTIokeyval(T4CConnection paramT4CConnection)
  {
    super(paramT4CConnection, (byte)17);

    setFunCode((short)154);

    this.namespaceByteArr = new byte[100];
    this.charArr = new char[100];

    this.attrArr = new byte[10][];
    this.attrArrSize = new int[10];
    this.valueArr = new byte[10][];
    this.valueArrSize = new int[10];

    this.kvalflg = new byte[10];
  }

  void doOKEYVAL(Namespace paramNamespace)
    throws IOException, SQLException
  {
    String str1 = paramNamespace.name;
    String[] arrayOfString1 = paramNamespace.keys;
    String[] arrayOfString2 = paramNamespace.values;
    this.clear = paramNamespace.clear;
    this.nbKeyVal = paramNamespace.nbPairs;

    int i = str1.length() * this.meg.conv.cMaxCharSize;
    if (i > this.namespaceByteArr.length)
      this.namespaceByteArr = new byte[i];
    if (str1.length() > this.charArr.length)
      this.charArr = new char[str1.length()];
    str1.getChars(0, str1.length(), this.charArr, 0);
    this.nbNamespaceBytes = this.meg.conv.javaCharsToCHARBytes(this.charArr, 0, this.namespaceByteArr, 0, str1.length());

    if (this.nbKeyVal > 0)
    {
      if (this.nbKeyVal > this.attrArr.length)
      {
        this.attrArr = new byte[this.nbKeyVal][];
        this.attrArrSize = new int[this.nbKeyVal];
        this.valueArr = new byte[this.nbKeyVal][];
        this.valueArrSize = new int[this.nbKeyVal];
        this.kvalflg = new byte[this.nbKeyVal];
      }

      for (int j = 0; j < this.nbKeyVal; j++)
      {
        String str2 = arrayOfString1[j];
        String str3 = arrayOfString2[j];

        int k = str2.length() * this.meg.conv.cMaxCharSize;
        if ((this.attrArr[j] == null) || (this.attrArr[j].length < k)) {
          this.attrArr[j] = new byte[k];
        }
        int m = str3.length() * this.meg.conv.cMaxCharSize;
        if ((this.valueArr[j] == null) || (this.valueArr[j].length < m)) {
          this.valueArr[j] = new byte[m];
        }
        if (str2.length() > this.charArr.length)
          this.charArr = new char[str2.length()];
        str2.getChars(0, str2.length(), this.charArr, 0);
        this.attrArrSize[j] = this.meg.conv.javaCharsToCHARBytes(this.charArr, 0, this.attrArr[j], 0, str2.length());

        if (str3.length() > this.charArr.length)
          this.charArr = new char[str3.length()];
        str3.getChars(0, str3.length(), this.charArr, 0);
        this.valueArrSize[j] = this.meg.conv.javaCharsToCHARBytes(this.charArr, 0, this.valueArr[j], 0, str3.length());
      }

    }

    doPigRPC();
  }

  void marshal()
    throws IOException
  {
    this.meg.marshalPTR();
    this.meg.marshalUB4(this.nbNamespaceBytes);
    if (this.nbKeyVal > 0)
      this.meg.marshalPTR();
    else
      this.meg.marshalNULLPTR();
    this.meg.marshalUB4(this.nbKeyVal);
    int i = 0;
    if (this.nbKeyVal > 0)
      i = 1;
    if (this.clear)
      i |= 2;
    this.meg.marshalUB2(i);
    this.meg.marshalNULLPTR();

    this.meg.marshalCHR(this.namespaceByteArr, 0, this.nbNamespaceBytes);
    if (this.nbKeyVal > 0)
      this.meg.marshalKEYVAL(this.attrArr, this.attrArrSize, this.valueArr, this.valueArrSize, this.kvalflg, this.nbKeyVal);
  }
}