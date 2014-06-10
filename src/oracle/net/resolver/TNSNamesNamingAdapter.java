package oracle.net.resolver;

import java.io.File;
import java.io.IOException;
import oracle.net.jdbc.nl.NLException;
import oracle.net.jdbc.nl.NLParamParser;
import oracle.net.jdbc.nl.NVPair;
import oracle.net.ns.NetException;

public class TNSNamesNamingAdapter
  implements NamingAdapterInterface
{
  private NLParamParser tnsEntriesHdl;
  private String tnsDir;
  private long lastModifiedTime;
  private long nextPollTime;
  private static final String TNSFILE = "tnsnames.ora";
  private static final long REFRESH_INTERVAL = 0L;

  public TNSNamesNamingAdapter(String paramString)
  {
    this.tnsDir = paramString;

    resetAttr();
  }

  private void resetAttr()
  {
    this.tnsEntriesHdl = null;
    this.lastModifiedTime = 0L;
    this.nextPollTime = 0L;
  }

  public String resolve(String paramString)
    throws NetException
  {
    NVPair localNVPair = null;

    synchronized (this)
    {
      checkAndReload();

      localNVPair = this.tnsEntriesHdl.getNLPListElement(paramString);
    }

    if (localNVPair == null)
    {
      throw new NetException(122, "\"" + paramString + "\"");
    }

    return localNVPair.valueToString();
  }

  private void loadFile()
    throws NetException
  {
    File localFile = new File(this.tnsDir, "tnsnames.ora");
    String str = localFile.getAbsolutePath();

    if ((!localFile.isFile()) || (!localFile.canRead()))
    {
      resetAttr();

      throw new NetException(123, ": " + str);
    }

    long l = localFile.lastModified();
    if (this.lastModifiedTime != l)
    {
      try
      {
        this.tnsEntriesHdl = new NLParamParser(str, (byte)1);

        this.lastModifiedTime = l;
      }
      catch (IOException localIOException)
      {
        resetAttr();

        throw new NetException(123, ": " + str);
      }
      catch (NLException localNLException)
      {
      }
    }
  }

  private void checkAndReload()
    throws NetException
  {
    long l = System.currentTimeMillis();

    if (l > this.nextPollTime)
    {
      this.nextPollTime = (l + 0L);
      loadFile();
    }
  }
}