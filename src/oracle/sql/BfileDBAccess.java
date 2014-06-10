package oracle.sql;

import java.io.InputStream;
import java.io.Reader;
import java.sql.SQLException;

public abstract interface BfileDBAccess
{
  public abstract long length(BFILE paramBFILE)
    throws SQLException;

  public abstract long position(BFILE paramBFILE, byte[] paramArrayOfByte, long paramLong)
    throws SQLException;

  public abstract long position(BFILE paramBFILE1, BFILE paramBFILE2, long paramLong)
    throws SQLException;

  public abstract int getBytes(BFILE paramBFILE, long paramLong, int paramInt, byte[] paramArrayOfByte)
    throws SQLException;

  public abstract String getName(BFILE paramBFILE)
    throws SQLException;

  public abstract String getDirAlias(BFILE paramBFILE)
    throws SQLException;

  public abstract void openFile(BFILE paramBFILE)
    throws SQLException;

  public abstract boolean isFileOpen(BFILE paramBFILE)
    throws SQLException;

  public abstract boolean fileExists(BFILE paramBFILE)
    throws SQLException;

  public abstract void closeFile(BFILE paramBFILE)
    throws SQLException;

  public abstract void open(BFILE paramBFILE, int paramInt)
    throws SQLException;

  public abstract void close(BFILE paramBFILE)
    throws SQLException;

  public abstract boolean isOpen(BFILE paramBFILE)
    throws SQLException;

  public abstract InputStream newInputStream(BFILE paramBFILE, int paramInt, long paramLong)
    throws SQLException;

  public abstract InputStream newConversionInputStream(BFILE paramBFILE, int paramInt)
    throws SQLException;

  public abstract Reader newConversionReader(BFILE paramBFILE, int paramInt)
    throws SQLException;
}