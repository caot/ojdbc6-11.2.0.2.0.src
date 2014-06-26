package oracle.jdbc.driver;

import java.io.IOException;
import java.sql.SQLException;

final class T4CTTIoscid extends T4CTTIfun
{
  static final int KPDUSR_CID_RESET = 1;
  static final int KPDUSR_PROXY_RESET = 2;
  static final int KPDUSR_PROXY_TKTSENT = 4;
  static final int KPDUSR_MODULE_RESET = 8;
  static final int KPDUSR_ACTION_RESET = 16;
  static final int KPDUSR_EXECID_RESET = 32;
  static final int KPDUSR_EXECSQ_RESET = 64;
  static final int KPDUSR_COLLCT_RESET = 128;
  static final int KPDUSR_CLINFO_RESET = 256;
  private byte[] cidcid = null;
  private byte[] cidmod = null;
  private byte[] cidact = null;
  private byte[] cideci = null;

  private boolean[] endToEndHasChanged = null;
  private String[] endToEndValues = null;
  private int endToEndECIDSequenceNumber;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  T4CTTIoscid(T4CConnection paramT4CConnection)
  {
    super(paramT4CConnection, (byte)17);
    setFunCode((short)135);
  }

  void doOSCID(boolean[] paramArrayOfBoolean, String[] paramArrayOfString, int paramInt)
    throws IOException, SQLException
  {
    this.endToEndHasChanged = paramArrayOfBoolean;
    this.endToEndValues = paramArrayOfString;
    this.endToEndECIDSequenceNumber = paramInt;

    if (this.endToEndValues[1] != null) {
      this.cidcid = this.meg.conv.StringToCharBytes(this.endToEndValues[1]);
    }
    else {
      this.cidcid = null;
    }
    if (this.endToEndValues[3] != null) {
      this.cidmod = this.meg.conv.StringToCharBytes(this.endToEndValues[3]);
    }
    else {
      this.cidmod = null;
    }
    if (this.endToEndValues[0] != null) {
      this.cidact = this.meg.conv.StringToCharBytes(this.endToEndValues[0]);
    }
    else {
      this.cidact = null;
    }
    if (this.endToEndValues[2] != null) {
      this.cideci = this.meg.conv.StringToCharBytes(this.endToEndValues[2]);
    }
    else
      this.cideci = null;
    doPigRPC();
  }

  void marshal()
    throws IOException
  {
    int i = 64;

    if (this.endToEndHasChanged[0]) {
      i |= 16;
    }
    if (this.endToEndHasChanged[1]) {
      i |= 1;
    }
    if (this.endToEndHasChanged[2]) {
      i |= 32;
    }
    if (this.endToEndHasChanged[3]) {
      i |= 8;
    }

    this.meg.marshalNULLPTR();
    this.meg.marshalNULLPTR();
    this.meg.marshalUB4(i);

    int j = 0; int k = 0;
    int m = 0; int n = 0;

    if (this.endToEndHasChanged[1])
    {
      this.meg.marshalPTR();

      if (this.cidcid != null)
        this.meg.marshalUB4(this.cidcid.length);
      else
        this.meg.marshalUB4(0L);
      j = 1;
    }
    else
    {
      this.meg.marshalNULLPTR();
      this.meg.marshalUB4(0L);
    }

    if (this.endToEndHasChanged[3])
    {
      this.meg.marshalPTR();

      if (this.cidmod != null)
        this.meg.marshalUB4(this.cidmod.length);
      else
        this.meg.marshalUB4(0L);
      k = 1;
    }
    else
    {
      this.meg.marshalNULLPTR();
      this.meg.marshalUB4(0L);
    }

    if (this.endToEndHasChanged[0])
    {
      this.meg.marshalPTR();
      if (this.cidact != null)
        this.meg.marshalUB4(this.cidact.length);
      else
        this.meg.marshalUB4(0L);
      m = 1;
    }
    else
    {
      this.meg.marshalNULLPTR();
      this.meg.marshalUB4(0L);
    }

    if (this.endToEndHasChanged[2])
    {
      this.meg.marshalPTR();

      if (this.cideci != null)
        this.meg.marshalUB4(this.cideci.length);
      else
        this.meg.marshalUB4(0L);
      n = 1;
    }
    else
    {
      this.meg.marshalNULLPTR();
      this.meg.marshalUB4(0L);
    }

    this.meg.marshalUB2(0);
    this.meg.marshalUB2(this.endToEndECIDSequenceNumber);
    this.meg.marshalNULLPTR();
    this.meg.marshalUB4(0L);
    this.meg.marshalNULLPTR();
    this.meg.marshalUB4(0L);
    this.meg.marshalNULLPTR();
    this.meg.marshalUB4(0L);

    if ((j != 0) && (this.cidcid != null)) {
      this.meg.marshalCHR(this.cidcid);
    }
    if ((k != 0) && (this.cidmod != null)) {
      this.meg.marshalCHR(this.cidmod);
    }
    if ((m != 0) && (this.cidact != null)) {
      this.meg.marshalCHR(this.cidact);
    }
    if ((n != 0) && (this.cideci != null))
      this.meg.marshalCHR(this.cideci);
  }
}