package oracle.sql;

import java.sql.SQLException;

abstract interface LnxLib
{
  public abstract byte[] lnxabs(byte[] paramArrayOfByte)
    throws SQLException;

  public abstract byte[] lnxacos(byte[] paramArrayOfByte)
    throws SQLException;

  public abstract byte[] lnxadd(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
    throws SQLException;

  public abstract byte[] lnxasin(byte[] paramArrayOfByte)
    throws SQLException;

  public abstract byte[] lnxatan(byte[] paramArrayOfByte)
    throws SQLException;

  public abstract byte[] lnxatan2(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
    throws SQLException;

  public abstract byte[] lnxbex(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
    throws SQLException;

  public abstract byte[] lnxcos(byte[] paramArrayOfByte)
    throws SQLException;

  public abstract byte[] lnxcsh(byte[] paramArrayOfByte)
    throws SQLException;

  public abstract byte[] lnxdec(byte[] paramArrayOfByte)
    throws SQLException;

  public abstract byte[] lnxdiv(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
    throws SQLException;

  public abstract byte[] lnxexp(byte[] paramArrayOfByte)
    throws SQLException;

  public abstract byte[] lnxflo(byte[] paramArrayOfByte)
    throws SQLException;

  public abstract byte[] lnxceil(byte[] paramArrayOfByte)
    throws SQLException;

  public abstract byte[] lnxfpr(byte[] paramArrayOfByte, int paramInt)
    throws SQLException;

  public abstract byte[] lnxinc(byte[] paramArrayOfByte)
    throws SQLException;

  public abstract byte[] lnxln(byte[] paramArrayOfByte)
    throws SQLException;

  public abstract byte[] lnxlog(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
    throws SQLException;

  public abstract byte[] lnxmod(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
    throws SQLException;

  public abstract byte[] lnxmul(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
    throws SQLException;

  public abstract byte[] lnxneg(byte[] paramArrayOfByte)
    throws SQLException;

  public abstract byte[] lnxpow(byte[] paramArrayOfByte, int paramInt)
    throws SQLException;

  public abstract byte[] lnxrou(byte[] paramArrayOfByte, int paramInt)
    throws SQLException;

  public abstract byte[] lnxsca(byte[] paramArrayOfByte, int paramInt1, int paramInt2, boolean[] paramArrayOfBoolean)
    throws SQLException;

  public abstract byte[] lnxshift(byte[] paramArrayOfByte, int paramInt)
    throws SQLException;

  public abstract byte[] lnxsin(byte[] paramArrayOfByte)
    throws SQLException;

  public abstract byte[] lnxsnh(byte[] paramArrayOfByte)
    throws SQLException;

  public abstract byte[] lnxsqr(byte[] paramArrayOfByte)
    throws SQLException;

  public abstract byte[] lnxsub(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
    throws SQLException;

  public abstract byte[] lnxtan(byte[] paramArrayOfByte)
    throws SQLException;

  public abstract byte[] lnxtnh(byte[] paramArrayOfByte)
    throws SQLException;

  public abstract byte[] lnxtru(byte[] paramArrayOfByte, int paramInt)
    throws SQLException;

  public abstract byte[] lnxcpn(String paramString1, boolean paramBoolean1, int paramInt1, boolean paramBoolean2, int paramInt2, String paramString2)
    throws SQLException;

  public abstract byte[] lnxfcn(String paramString1, String paramString2, String paramString3)
    throws SQLException;

  public abstract String lnxnfn(byte[] paramArrayOfByte, String paramString1, String paramString2)
    throws SQLException;

  public abstract String lnxnuc(byte[] paramArrayOfByte, int paramInt, String paramString)
    throws SQLException;

  public abstract byte[] lnxren(double paramDouble)
    throws SQLException;

  public abstract double lnxnur(byte[] paramArrayOfByte);

  public abstract byte[] lnxmin(long paramLong);

  public abstract long lnxsni(byte[] paramArrayOfByte)
    throws SQLException;
}