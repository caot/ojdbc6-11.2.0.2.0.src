package oracle.sql;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.sql.Connection;
import java.sql.SQLException;

public abstract interface ClobDBAccess
{
  public abstract long length(CLOB paramCLOB)
    throws SQLException;

  public abstract long position(CLOB paramCLOB, String paramString, long paramLong)
    throws SQLException;

  public abstract long position(CLOB paramCLOB1, CLOB paramCLOB2, long paramLong)
    throws SQLException;

  public abstract int getChars(CLOB paramCLOB, long paramLong, int paramInt, char[] paramArrayOfChar)
    throws SQLException;

  public abstract int putChars(CLOB paramCLOB, long paramLong, char[] paramArrayOfChar, int paramInt1, int paramInt2)
    throws SQLException;

  public abstract int getChunkSize(CLOB paramCLOB)
    throws SQLException;

  public abstract void trim(CLOB paramCLOB, long paramLong)
    throws SQLException;

  public abstract CLOB createTemporaryClob(Connection paramConnection, boolean paramBoolean, int paramInt, short paramShort)
    throws SQLException;

  public abstract void freeTemporary(CLOB paramCLOB, boolean paramBoolean)
    throws SQLException;

  public abstract boolean isTemporary(CLOB paramCLOB)
    throws SQLException;

  public abstract void open(CLOB paramCLOB, int paramInt)
    throws SQLException;

  public abstract void close(CLOB paramCLOB)
    throws SQLException;

  public abstract boolean isOpen(CLOB paramCLOB)
    throws SQLException;

  public abstract InputStream newInputStream(CLOB paramCLOB, int paramInt, long paramLong)
    throws SQLException;

  public abstract OutputStream newOutputStream(CLOB paramCLOB, int paramInt, long paramLong, boolean paramBoolean)
    throws SQLException;

  public abstract Reader newReader(CLOB paramCLOB, int paramInt, long paramLong)
    throws SQLException;

  public abstract Reader newReader(CLOB paramCLOB, int paramInt, long paramLong1, long paramLong2)
    throws SQLException;

  public abstract Writer newWriter(CLOB paramCLOB, int paramInt, long paramLong, boolean paramBoolean)
    throws SQLException;

  public abstract char[] getCharBufferSync(int paramInt);

  public abstract void cacheBufferSync(char[] paramArrayOfChar);
}