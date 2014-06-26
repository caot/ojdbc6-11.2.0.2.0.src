package oracle.jdbc.rowset;

import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Stack;
import javax.sql.RowSet;
import javax.sql.RowSetInternal;
import javax.sql.rowset.WebRowSet;
import javax.sql.rowset.spi.SyncProvider;
import oracle.jdbc.driver.DatabaseError;
import oracle.jdbc.internal.OracleConnection;

class OracleWebRowSetXmlWriterImpl
  implements OracleWebRowSetXmlWriter
{
  private Writer xmlWriter;
  private Stack xmlTagStack;
  private static final String WEBROWSET_ELEMENT = "webRowSet";
  private static final String PROPERTIES_ELEMENT = "properties";
  private static final String METADATA_ELEMENT = "metadata";
  private static final String DATA_ELEMENT = "data";
  private static final String PROPERTY_COMMAND = "command";
  private static final String PROPERTY_CONCURRENCY = "concurrency";
  private static final String PROPERTY_DATASOURCE = "datasource";
  private static final String PROPERTY_ESCAPEPROCESSING = "escape-processing";
  private static final String PROPERTY_FETCHDIRECTION = "fetch-direction";
  private static final String PROPERTY_FETCHSIZE = "fetch-size";
  private static final String PROPERTY_ISOLATIONLEVEL = "isolation-level";
  private static final String PROPERTY_KEYCOLUMNS = "key-columns";
  private static final String PROPERTY_MAP = "map";
  private static final String PROPERTY_MAXFIELDSIZE = "max-field-size";
  private static final String PROPERTY_MAXROWS = "max-rows";
  private static final String PROPERTY_QUERYTIMEOUT = "query-timeout";
  private static final String PROPERTY_READONLY = "read-only";
  private static final String PROPERTY_ROWSETTYPE = "rowset-type";
  private static final String PROPERTY_SHOWDELETED = "show-deleted";
  private static final String PROPERTY_TABLENAME = "table-name";
  private static final String PROPERTY_URL = "url";
  private static final String PROPERTY_SYNCPROVIDER = "sync-provider";
  private static final String PROPERTY_NULL = "null";
  private static final String PROPERTY_KC_COLUMN = "column";
  private static final String PROPERTY_MAP_TYPE = "type";
  private static final String PROPERTY_MAP_CLASS = "class";
  private static final String PROPERTY_S_PROVIDERNAME = "sync-provider-name";
  private static final String PROPERTY_S_PROVIDERVENDOR = "sync-provider-vendor";
  private static final String PROPERTY_S_PROVIDERVERSION = "sync-provider-version";
  private static final String PROPERTY_S_PROVIDERGRADE = "sync-provider-grade";
  private static final String PROPERTY_S_DATASOURCELOCK = "data-source-lock";
  private static final String METADATA_COLUMNCOUNT = "column-count";
  private static final String METADATA_COLUMNDEFINITION = "column-definition";
  private static final String METADATA_COLUMNINDEX = "column-index";
  private static final String METADATA_AUTOINCREMENT = "auto-increment";
  private static final String METADATA_CASESENSITIVE = "case-sensitive";
  private static final String METADATA_CURRENCY = "currency";
  private static final String METADATA_NULLABLE = "nullable";
  private static final String METADATA_SIGNED = "signed";
  private static final String METADATA_SEARCHABLE = "searchable";
  private static final String METADATA_COLUMNDISPLAYSIZE = "column-display-size";
  private static final String METADATA_COLUMNLABEL = "column-label";
  private static final String METADATA_COLUMNNAME = "column-name";
  private static final String METADATA_SCHEMANAME = "schema-name";
  private static final String METADATA_COLUMNPRECISION = "column-precision";
  private static final String METADATA_COLUMNSCALE = "column-scale";
  private static final String METADATA_TABLENAME = "table-name";
  private static final String METADATA_CATALOGNAME = "catalog-name";
  private static final String METADATA_COLUMNTYPE = "column-type";
  private static final String METADATA_COLUMNTYPENAME = "column-type-name";
  private static final String METADATA_NULL = "null";
  private static final String DATA_CURRENTROW = "currentRow";
  private static final String DATA_INSERTROW = "insertRow";
  private static final String DATA_DELETEROW = "deleteRow";
  private static final String DATA_MODIFYROW = "modifyRow";
  private static final String DATA_COLUMNVALUE = "columnValue";
  private static final String DATA_UPDATEVALUE = "updateValue";
  private static final String DATA_NULL = "null";
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  public void writeXML(WebRowSet paramWebRowSet, Writer paramWriter)
    throws SQLException
  {
    if (!(paramWebRowSet instanceof OracleWebRowSet))
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 359);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    this.xmlTagStack = new Stack();
    this.xmlWriter = paramWriter;
    writeRowSet((OracleWebRowSet)paramWebRowSet);
  }

  public boolean writeData(RowSetInternal paramRowSetInternal)
    throws SQLException
  {
    return false;
  }

  private void writeRowSet(OracleWebRowSet paramOracleWebRowSet)
    throws SQLException
  {
    try
    {
      writeHeaderAndStartWebRowSetElement();
      writeProperties(paramOracleWebRowSet);
      writeMetaData(paramOracleWebRowSet);
      writeData(paramOracleWebRowSet);
      endWebRowSetElement();
    }
    catch (IOException localIOException)
    {
      throw new SQLException("IOException: " + localIOException.getMessage());
    }
  }

  private void writeHeaderAndStartWebRowSetElement()
    throws IOException
  {
    this.xmlWriter.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
    this.xmlWriter.write("\n");

    setCurrentTag("webRowSet");

    this.xmlWriter.write("<webRowSet xmlns=\"http://java.sun.com/xml/ns/jdbc\"\n");
    this.xmlWriter.write("           xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n");
    this.xmlWriter.write("           xsi:schemaLocation=\"http://java.sun.com/xml/ns/jdbc ");
    this.xmlWriter.write("http://java.sun.com/xml/ns/jdbc/webrowset.xsd");
    this.xmlWriter.write("\">\n");
  }

  private void endWebRowSetElement()
    throws IOException
  {
    endTag("webRowSet");
  }

  private void startElement(String paramString)
    throws IOException
  {
    startTag(paramString);
    this.xmlWriter.write("\n");
  }

  private void endElement(String paramString)
    throws IOException
  {
    writeIndent(this.xmlTagStack.size());
    endTag(paramString);
  }

  private void endElement()
    throws IOException
  {
    writeIndent(this.xmlTagStack.size());
    String str = getCurrentTag();
    this.xmlWriter.write("</" + str + ">\n");
    this.xmlWriter.flush();
  }

  private void startTag(String paramString)
    throws IOException
  {
    setCurrentTag(paramString);
    writeIndent(this.xmlTagStack.size());
    this.xmlWriter.write("<" + paramString + ">");
  }

  private void endTag(String paramString)
    throws IOException
  {
    String str = getCurrentTag();
    if (paramString.equals(str))
      this.xmlWriter.write("</" + str + ">\n");
    this.xmlWriter.flush();
  }

  private void setCurrentTag(String paramString)
  {
    this.xmlTagStack.push(paramString);
  }

  private String getCurrentTag()
  {
    return (String)this.xmlTagStack.pop();
  }

  private void writeEmptyElement(String paramString)
    throws IOException
  {
    this.xmlWriter.write("<" + paramString + "/>");
  }

  private void writeProperties(OracleWebRowSet paramOracleWebRowSet)
    throws IOException
  {
    startElement("properties");
    try
    {
      writeElementString("command", paramOracleWebRowSet.getCommand());
      writeElementInteger("concurrency", paramOracleWebRowSet.getConcurrency());
      writeElementString("datasource", paramOracleWebRowSet.getDataSourceName());
      writeElementBoolean("escape-processing", paramOracleWebRowSet.getEscapeProcessing());
      writeElementInteger("fetch-direction", paramOracleWebRowSet.getFetchDirection());
      writeElementInteger("fetch-size", paramOracleWebRowSet.getFetchSize());
      writeElementInteger("isolation-level", paramOracleWebRowSet.getTransactionIsolation());

      startElement("key-columns");
      int[] arrayOfInt = paramOracleWebRowSet.getKeyColumns();
      for (int i = 0; (arrayOfInt != null) && (i < arrayOfInt.length); i++)
        writeElementInteger("column", arrayOfInt[i]);
      endElement("key-columns");

      startElement("map");
      Map localMap = paramOracleWebRowSet.getTypeMap();
      if (localMap != null)
      {
        Set<Map.Entry> localSet = localMap.entrySet();
        for (Map.Entry localEntry : localSet)
        {
          writeElementString("type", (String)localEntry.getKey());
          writeElementString("class", ((Class)localEntry.getValue()).getName());
        }
      }
      endElement("map");

      writeElementInteger("max-field-size", paramOracleWebRowSet.getMaxFieldSize());
      writeElementInteger("max-rows", paramOracleWebRowSet.getMaxRows());
      writeElementInteger("query-timeout", paramOracleWebRowSet.getQueryTimeout());
      writeElementBoolean("read-only", paramOracleWebRowSet.isReadOnly());
      writeElementInteger("rowset-type", paramOracleWebRowSet.getType());
      writeElementBoolean("show-deleted", paramOracleWebRowSet.getShowDeleted());
      writeElementString("table-name", paramOracleWebRowSet.getTableName());
      writeElementString("url", paramOracleWebRowSet.getUrl());

      startElement("sync-provider");

      SyncProvider localSyncProvider = paramOracleWebRowSet.getSyncProvider();
      writeElementString("sync-provider-name", localSyncProvider.getProviderID());
      writeElementString("sync-provider-vendor", localSyncProvider.getVendor());
      writeElementString("sync-provider-version", localSyncProvider.getVersion());
      writeElementInteger("sync-provider-grade", localSyncProvider.getProviderGrade());
      writeElementInteger("data-source-lock", localSyncProvider.getDataSourceLock());

      endElement("sync-provider");
    }
    catch (SQLException localSQLException)
    {
      throw new IOException("SQLException: " + localSQLException.getMessage());
    }

    endElement("properties");
  }

  private void writeMetaData(OracleWebRowSet paramOracleWebRowSet)
    throws IOException
  {
    startElement("metadata");
    try
    {
      ResultSetMetaData localResultSetMetaData = paramOracleWebRowSet.getMetaData();

      int i = localResultSetMetaData.getColumnCount();
      writeElementInteger("column-count", i);

      for (int j = 1; j <= i; j++)
      {
        startElement("column-definition");

        writeElementInteger("column-index", j);
        writeElementBoolean("auto-increment", localResultSetMetaData.isAutoIncrement(j));
        writeElementBoolean("case-sensitive", localResultSetMetaData.isCaseSensitive(j));
        writeElementBoolean("currency", localResultSetMetaData.isCurrency(j));
        writeElementInteger("nullable", localResultSetMetaData.isNullable(j));
        writeElementBoolean("signed", localResultSetMetaData.isSigned(j));
        writeElementBoolean("searchable", localResultSetMetaData.isSearchable(j));
        writeElementInteger("column-display-size", localResultSetMetaData.getColumnDisplaySize(j));
        writeElementString("column-label", localResultSetMetaData.getColumnLabel(j));
        writeElementString("column-name", localResultSetMetaData.getColumnName(j));
        writeElementString("schema-name", localResultSetMetaData.getSchemaName(j));
        writeElementInteger("column-precision", localResultSetMetaData.getPrecision(j));
        writeElementInteger("column-scale", localResultSetMetaData.getScale(j));
        writeElementString("table-name", localResultSetMetaData.getTableName(j));
        writeElementString("catalog-name", localResultSetMetaData.getCatalogName(j));
        writeElementInteger("column-type", localResultSetMetaData.getColumnType(j));
        writeElementString("column-type-name", localResultSetMetaData.getColumnTypeName(j));

        endElement("column-definition");
      }
    }
    catch (SQLException localSQLException)
    {
      throw new IOException("SQLException: " + localSQLException.getMessage());
    }

    endElement("metadata");
  }

  private void writeElementBoolean(String paramString, boolean paramBoolean)
    throws IOException
  {
    startTag(paramString);
    writeBoolean(paramBoolean);
    endTag(paramString);
  }

  private void writeElementInteger(String paramString, int paramInt)
    throws IOException
  {
    startTag(paramString);
    writeInteger(paramInt);
    endTag(paramString);
  }

  private void writeElementString(String paramString1, String paramString2)
    throws IOException
  {
    startTag(paramString1);
    writeString(paramString2);
    endTag(paramString1);
  }

  private void writeData(OracleWebRowSet paramOracleWebRowSet)
    throws IOException
  {
    try
    {
      ResultSetMetaData localResultSetMetaData = paramOracleWebRowSet.getMetaData();
      int i = localResultSetMetaData.getColumnCount();

      startElement("data");

      paramOracleWebRowSet.beforeFirst();
      paramOracleWebRowSet.setShowDeleted(true);
      for (; paramOracleWebRowSet.next(); endElement())
      {
        if ((paramOracleWebRowSet.rowDeleted()) && (paramOracleWebRowSet.rowInserted()))
          startElement("modifyRow");
        else if (paramOracleWebRowSet.rowDeleted())
          startElement("deleteRow");
        else if (paramOracleWebRowSet.rowInserted())
          startElement("insertRow");
        else
          startElement("currentRow");
        for (int j = 1; j <= i; j++)
        {
          if (paramOracleWebRowSet.columnUpdated(j))
          {
            ResultSet localResultSet = paramOracleWebRowSet.getOriginalRow();
            localResultSet.next();

            startTag("columnValue");
            writeValue(j, (RowSet)localResultSet);
            endTag("columnValue");

            startTag("updateValue");
            writeValue(j, paramOracleWebRowSet);
            endTag("updateValue");
          }
          else
          {
            startTag("columnValue");
            writeValue(j, paramOracleWebRowSet);
            endTag("columnValue");
          }
        }
      }

      endElement("data");
    }
    catch (SQLException localSQLException)
    {
      throw new IOException("SQLException: " + localSQLException.getMessage());
    }
  }

  private void writeBigDecimal(BigDecimal paramBigDecimal)
    throws IOException
  {
    if (paramBigDecimal != null)
      this.xmlWriter.write(paramBigDecimal.toString());
    else
      writeEmptyElement("null");
  }

  private void writeBoolean(boolean paramBoolean)
    throws IOException
  {
    this.xmlWriter.write(Boolean.valueOf(paramBoolean).toString());
  }

  private void writeDouble(double paramDouble)
    throws IOException
  {
    this.xmlWriter.write(Double.toString(paramDouble));
  }

  private void writeFloat(float paramFloat)
    throws IOException
  {
    this.xmlWriter.write(Float.toString(paramFloat));
  }

  private void writeInteger(int paramInt)
    throws IOException
  {
    this.xmlWriter.write(Integer.toString(paramInt));
  }

  private void writeLong(long paramLong)
    throws IOException
  {
    this.xmlWriter.write(Long.toString(paramLong));
  }

  private void writeNull()
    throws IOException
  {
    writeEmptyElement("null");
  }

  private void writeShort(short paramShort)
    throws IOException
  {
    this.xmlWriter.write(Short.toString(paramShort));
  }

  private void writeBytes(byte[] paramArrayOfByte)
    throws IOException
  {
    this.xmlWriter.write(new String(paramArrayOfByte));
  }

  private void writeString(String paramString)
    throws IOException
  {
    if (paramString != null)
      this.xmlWriter.write(paramString);
    else
      this.xmlWriter.write("");
  }

  private void writeIndent(int paramInt)
    throws IOException
  {
    for (int i = 1; i < paramInt; i++)
      this.xmlWriter.write("  ");
  }

  private void writeValue(int paramInt, RowSet paramRowSet)
    throws IOException
  {
    try
    {
      int i = paramRowSet.getMetaData().getColumnType(paramInt);
      switch (i)
      {
      case -7:
      case 5:
        short s = paramRowSet.getShort(paramInt);
        if (paramRowSet.wasNull())
          writeNull();
        else
          writeShort(s);
        break;
      case 4:
        int j = paramRowSet.getInt(paramInt);
        if (paramRowSet.wasNull())
          writeNull();
        else
          writeInteger(j);
        break;
      case -5:
        long l = paramRowSet.getLong(paramInt);
        if (paramRowSet.wasNull())
          writeNull();
        else
          writeLong(l);
        break;
      case 6:
      case 7:
        float f = paramRowSet.getFloat(paramInt);
        if (paramRowSet.wasNull())
          writeNull();
        else
          writeFloat(f);
        break;
      case 8:
        double d = paramRowSet.getDouble(paramInt);
        if (paramRowSet.wasNull())
          writeNull();
        else
          writeDouble(d);
        break;
      case 2:
      case 3:
        BigDecimal localBigDecimal = paramRowSet.getBigDecimal(paramInt);
        if (paramRowSet.wasNull())
          writeNull();
        else
          writeBigDecimal(localBigDecimal);
        break;
      case 91:
        Date localDate = paramRowSet.getDate(paramInt);
        if (paramRowSet.wasNull())
          writeNull();
        else
          writeLong(localDate.getTime());
        break;
      case 92:
        Time localTime = paramRowSet.getTime(paramInt);
        if (paramRowSet.wasNull())
          writeNull();
        else
          writeLong(localTime.getTime());
        break;
      case 93:
        Timestamp localTimestamp = paramRowSet.getTimestamp(paramInt);
        if (paramRowSet.wasNull())
          writeNull();
        else
          writeLong(localTimestamp.getTime());
        break;
      case -4:
      case -3:
      case -2:
      case 2004:
        byte[] arrayOfByte = paramRowSet.getBytes(paramInt);
        if (paramRowSet.wasNull())
          writeNull();
        else
          writeBytes(arrayOfByte);
        break;
      case -15:
      case -9:
      case -1:
      case 1:
      case 12:
      case 2005:
      case 2011:
        String str = paramRowSet.getString(paramInt);
        if (paramRowSet.wasNull())
          writeNull();
        else
          writeString(str);
        break;
      default:
        throw new SQLException("The type " + i + " is not supported currently.");
      }
    }
    catch (SQLException localSQLException)
    {
      throw new IOException("Failed to writeValue: " + localSQLException.getMessage());
    }
  }

  protected OracleConnection getConnectionDuringExceptionHandling()
  {
    return null;
  }
}