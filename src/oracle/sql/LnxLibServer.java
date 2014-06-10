package oracle.sql;

import java.sql.SQLException;

class LnxLibServer
  implements LnxLib
{
  public native byte[] lnxabs(byte[] paramArrayOfByte)
    throws SQLException;

  public native byte[] lnxacos(byte[] paramArrayOfByte)
    throws SQLException;

  public native byte[] lnxadd(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
    throws SQLException;

  public native byte[] lnxasin(byte[] paramArrayOfByte)
    throws SQLException;

  public native byte[] lnxatan(byte[] paramArrayOfByte)
    throws SQLException;

  public native byte[] lnxatan2(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
    throws SQLException;

  public native byte[] lnxbex(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
    throws SQLException;

  public native byte[] lnxcos(byte[] paramArrayOfByte)
    throws SQLException;

  public native byte[] lnxcsh(byte[] paramArrayOfByte)
    throws SQLException;

  public native byte[] lnxdec(byte[] paramArrayOfByte)
    throws SQLException;

  public native byte[] lnxdiv(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
    throws SQLException;

  public native byte[] lnxexp(byte[] paramArrayOfByte)
    throws SQLException;

  public native byte[] lnxflo(byte[] paramArrayOfByte)
    throws SQLException;

  public native byte[] lnxceil(byte[] paramArrayOfByte)
    throws SQLException;

  public native byte[] lnxfpr(byte[] paramArrayOfByte, int paramInt)
    throws SQLException;

  public native byte[] lnxinc(byte[] paramArrayOfByte)
    throws SQLException;

  public native byte[] lnxln(byte[] paramArrayOfByte)
    throws SQLException;

  public native byte[] lnxlog(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
    throws SQLException;

  public native byte[] lnxmod(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
    throws SQLException;

  public native byte[] lnxmul(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
    throws SQLException;

  public native byte[] lnxneg(byte[] paramArrayOfByte)
    throws SQLException;

  public native byte[] lnxpow(byte[] paramArrayOfByte, int paramInt)
    throws SQLException;

  public native byte[] lnxrou(byte[] paramArrayOfByte, int paramInt)
    throws SQLException;

  public native byte[] lnxsca(byte[] paramArrayOfByte, int paramInt1, int paramInt2, boolean[] paramArrayOfBoolean)
    throws SQLException;

  public native byte[] lnxshift(byte[] paramArrayOfByte, int paramInt)
    throws SQLException;

  public native byte[] lnxsin(byte[] paramArrayOfByte)
    throws SQLException;

  public native byte[] lnxsnh(byte[] paramArrayOfByte)
    throws SQLException;

  public native byte[] lnxsqr(byte[] paramArrayOfByte)
    throws SQLException;

  public native byte[] lnxsub(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
    throws SQLException;

  public native byte[] lnxtan(byte[] paramArrayOfByte)
    throws SQLException;

  public native byte[] lnxtnh(byte[] paramArrayOfByte)
    throws SQLException;

  public native byte[] lnxtru(byte[] paramArrayOfByte, int paramInt)
    throws SQLException;

  public native byte[] lnxcpn(String paramString1, boolean paramBoolean1, int paramInt1, boolean paramBoolean2, int paramInt2, String paramString2)
    throws SQLException;

  public native byte[] lnxfcn(String paramString1, String paramString2, String paramString3)
    throws SQLException;

  public native String lnxnfn(byte[] paramArrayOfByte, String paramString1, String paramString2)
    throws SQLException;

  public native String lnxnuc(byte[] paramArrayOfByte, int paramInt, String paramString)
    throws SQLException;

  public native byte[] lnxren(double paramDouble)
    throws SQLException;

  public native double lnxnur(byte[] paramArrayOfByte);

  public native byte[] lnxmin(long paramLong);

  public native long lnxsni(byte[] paramArrayOfByte)
    throws SQLException;

  static
  {
    LoadCorejava.init();
  }
}