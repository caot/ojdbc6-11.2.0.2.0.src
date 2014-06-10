package oracle.sql;

import java.sql.SQLException;

abstract interface LdxLib
{
  public abstract byte[] ldxadm(byte[] paramArrayOfByte, int paramInt)
    throws SQLException;

  public abstract byte[] ldxads(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws SQLException;

  public abstract int ldxchk(byte[] paramArrayOfByte)
    throws SQLException;

  public abstract byte[] ldxdfd(int paramInt1, int paramInt2)
    throws SQLException;

  public abstract void ldxdtd(byte[] paramArrayOfByte, int[] paramArrayOfInt1, int[] paramArrayOfInt2)
    throws SQLException;

  public abstract String ldxdts(byte[] paramArrayOfByte, String paramString1, String paramString2)
    throws SQLException;

  public abstract String ldxdts(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, String paramString)
    throws SQLException;

  public abstract byte[] ldxsto(String paramString1, String paramString2)
    throws SQLException;

  public abstract byte[] ldxdyf(byte[] paramArrayOfByte)
    throws SQLException;

  public abstract void ldxftd(byte[] paramArrayOfByte, int[] paramArrayOfInt1, int[] paramArrayOfInt2)
    throws SQLException;

  public abstract byte[] ldxgdt()
    throws SQLException;

  public abstract byte[] ldxldd(byte[] paramArrayOfByte)
    throws SQLException;

  public abstract byte[] ldxnxd(byte[] paramArrayOfByte, int paramInt)
    throws SQLException;

  public abstract byte[] ldxrnd(byte[] paramArrayOfByte, String paramString)
    throws SQLException;

  public abstract byte[] ldxsbm(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
    throws SQLException;

  public abstract void ldxsub(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int[] paramArrayOfInt1, int[] paramArrayOfInt2)
    throws SQLException;

  public abstract byte[] ldxstd(String paramString1, String paramString2, String paramString3)
    throws SQLException;

  public abstract byte[] ldxtrn(byte[] paramArrayOfByte, String paramString)
    throws SQLException;
}