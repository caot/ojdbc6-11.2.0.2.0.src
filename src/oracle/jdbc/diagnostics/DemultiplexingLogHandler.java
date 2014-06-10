package oracle.jdbc.diagnostics;

import java.io.IOException;
import java.util.Hashtable;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;

public class DemultiplexingLogHandler extends FileHandler
{
  static final String PROPERTY_PATTERN = "oracle.jdbc.diagnostics.DemultiplexingLogHandler.pattern";
  static final String PROPERTY_LIMIT = "oracle.jdbc.diagnostics.DemultiplexingLogHandler.limit";
  static final String PROPERTY_COUNT = "oracle.jdbc.diagnostics.DemultiplexingLogHandler.count";
  static final String PROPERTY_APPEND = "oracle.jdbc.diagnostics.DemultiplexingLogHandler.append";
  static final String DEFAULT_PATTERN = "%h/ojdbc_%s.trc";
  static final String DEFAULT_APPEND = String.valueOf(false);
  static final String DEFAULT_LIMIT = String.valueOf(2147483647);
  static final String DEFAULT_COUNT = String.valueOf(1);
  String localPattern;
  boolean localAppend;
  int localLimit;
  int localCount;
  Hashtable<Object, Handler> handlerList = new Hashtable(50);

  public DemultiplexingLogHandler()
    throws IOException
  {
    super(getFilename(getProperty("oracle.jdbc.diagnostics.DemultiplexingLogHandler.pattern", "%h/ojdbc_%s.trc"), "MAIN"), Integer.parseInt(getProperty("oracle.jdbc.diagnostics.DemultiplexingLogHandler.limit", DEFAULT_LIMIT)), Integer.parseInt(getProperty("oracle.jdbc.diagnostics.DemultiplexingLogHandler.count", DEFAULT_COUNT)), Boolean.getBoolean(getProperty("oracle.jdbc.diagnostics.DemultiplexingLogHandler.append", DEFAULT_APPEND)));
  }

  public DemultiplexingLogHandler(String paramString)
    throws IOException
  {
    super(getFilename(paramString, "MAIN"), Integer.parseInt(getProperty("oracle.jdbc.diagnostics.DemultiplexingLogHandler.limit", DEFAULT_LIMIT)), Integer.parseInt(getProperty("oracle.jdbc.diagnostics.DemultiplexingLogHandler.count", DEFAULT_COUNT)), Boolean.getBoolean(getProperty("oracle.jdbc.diagnostics.DemultiplexingLogHandler.append", DEFAULT_APPEND)));
  }

  public DemultiplexingLogHandler(String paramString, boolean paramBoolean)
    throws IOException
  {
    super(getFilename(paramString, "MAIN"), Integer.parseInt(getProperty("oracle.jdbc.diagnostics.DemultiplexingLogHandler.limit", DEFAULT_LIMIT)), Integer.parseInt(getProperty("oracle.jdbc.diagnostics.DemultiplexingLogHandler.count", DEFAULT_COUNT)), paramBoolean);
  }

  public DemultiplexingLogHandler(String paramString, int paramInt1, int paramInt2)
    throws IOException
  {
    super(getFilename(paramString, "MAIN"), paramInt1, paramInt2, Boolean.getBoolean(getProperty("oracle.jdbc.diagnostics.DemultiplexingLogHandler.append", DEFAULT_APPEND)));
  }

  public DemultiplexingLogHandler(String paramString, int paramInt1, int paramInt2, boolean paramBoolean)
    throws IOException
  {
    super(getFilename(paramString, "MAIN"), paramInt1, paramInt2, paramBoolean);
  }

  void initValues()
  {
    this.localPattern = getProperty("oracle.jdbc.diagnostics.DemultiplexingLogHandler.pattern", "%h/ojdbc_%s.trc");
    this.localLimit = Integer.parseInt(getProperty("oracle.jdbc.diagnostics.DemultiplexingLogHandler.limit", DEFAULT_LIMIT));
    this.localCount = Integer.parseInt(getProperty("oracle.jdbc.diagnostics.DemultiplexingLogHandler.count", DEFAULT_COUNT));
    this.localAppend = Boolean.getBoolean(getProperty("oracle.jdbc.diagnostics.DemultiplexingLogHandler.append", DEFAULT_APPEND));
  }

  static final String getFilename(String paramString1, String paramString2)
  {
    if (paramString1 == null) {
      paramString1 = "%h/ojdbc_%s.trc";
    }
    if (paramString1.contains("%s"))
    {
      return paramString1.replaceAll("%s", paramString2);
    }

    return paramString1 + "." + paramString2;
  }

  static String getProperty(String paramString1, String paramString2)
  {
    String str = LogManager.getLogManager().getProperty(paramString1);
    return str != null ? str : paramString2;
  }

  public void publish(LogRecord paramLogRecord)
  {
    Object[] arrayOfObject = paramLogRecord.getParameters();
    if ((arrayOfObject != null) && (arrayOfObject.length > 0))
    {
      Object localObject = (Handler)this.handlerList.get(arrayOfObject[0]);
      if (localObject == null)
      {
        if (this.localPattern == null)
          initValues();
        try
        {
          localObject = new FileHandler(getFilename(this.localPattern, (String)arrayOfObject[0]), this.localLimit, this.localCount, this.localAppend);

          ((Handler)localObject).setFormatter(getFormatter());
          ((Handler)localObject).setFilter(getFilter());
          ((Handler)localObject).setLevel(getLevel());
          ((Handler)localObject).setEncoding(getEncoding());
          ((Handler)localObject).setErrorManager(getErrorManager());
        }
        catch (IOException localIOException)
        {
          reportError("Unable open FileHandler", localIOException, 0);
        }

        this.handlerList.put(arrayOfObject[0], localObject);
      }
      ((Handler)localObject).publish(paramLogRecord);
    }
    else {
      super.publish(paramLogRecord);
    }
  }

  public void close()
  {
    for (Handler localHandler : this.handlerList.values()) {
      localHandler.close();
    }
    super.close();
  }
}