package oracle.jdbc.rowset;

import java.io.PrintStream;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;
import javax.sql.RowSet;
import javax.sql.RowSetMetaData;
import oracle.jdbc.driver.DatabaseError;
import oracle.jdbc.internal.OracleConnection;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

class OracleWebRowSetXmlReaderContHandler extends DefaultHandler
{
  private OracleWebRowSet wrset;
  private RowSetMetaData rsetMetaData;
  private Vector updatesToRowSet;
  private Vector keyCols;
  private String columnValue;
  private String propertyValue;
  private String metadataValue;
  private boolean isNullValue;
  private int columnIndex;
  private Hashtable propertyNameTagMap;
  private Hashtable metadataNameTagMap;
  private Hashtable dataNameTagMap;
  protected static final String WEBROWSET_ELEMENT_NAME = "webRowSet";
  protected static final String PROPERTIES_ELEMENT_NAME = "properties";
  protected static final String METADATA_ELEMENT_NAME = "metadata";
  protected static final String DATA_ELEMENT_NAME = "data";
  private int state;
  private static final int INITIAL_STATE = 0;
  private static final int PROPERTIES_STATE = 1;
  private static final int METADATA_STATE = 2;
  private static final int DATA_STATE = 3;
  private int tag;
  private static final int NO_TAG = -1;
  private String[] propertyNames = { "command", "concurrency", "datasource", "escape-processing", "fetch-direction", "fetch-size", "isolation-level", "key-columns", "map", "max-field-size", "max-rows", "query-timeout", "read-only", "rowset-type", "show-deleted", "table-name", "url", "sync-provider", "null", "column", "type", "class", "sync-provider-name", "sync-provider-vendor", "sync-provider-version", "sync-provider-grade", "data-source-lock" };
  private boolean readReadOnlyValue;
  private static final int PROPERTY_COMMAND_TAG = 0;
  private static final int PROPERTY_CONCURRENCY_TAG = 1;
  private static final int PROPERTY_DATASOURCETAG = 2;
  private static final int PROPERTY_ESCAPEPROCESSING_TAG = 3;
  private static final int PROPERTY_FETCHDIRECTION_TAG = 4;
  private static final int PROPERTY_FETCHSIZE_TAG = 5;
  private static final int PROPERTY_ISOLATIONLEVEL_TAG = 6;
  private static final int PROPERTY_KEYCOLUMNS_TAG = 7;
  private static final int PROPERTY_MAP_TAG = 8;
  private static final int PROPERTY_MAXFIELDSIZE_TAG = 9;
  private static final int PROPERTY_MAXROWS_TAG = 10;
  private static final int PROPERTY_QUERYTIMEOUT_TAG = 11;
  private static final int PROPERTY_READONLY_TAG = 12;
  private static final int PROPERTY_ROWSETTYPE_TAG = 13;
  private static final int PROPERTY_SHOWDELETED_TAG = 14;
  private static final int PROPERTY_TABLENAME_TAG = 15;
  private static final int PROPERTY_URL_TAG = 16;
  private static final int PROPERTY_SYNCPROVIDER_TAG = 17;
  private static final int PROPERTY_NULL_TAG = 18;
  private static final int PROPERTY_COLUMN_TAG = 19;
  private static final int PROPERTY_TYPE_TAG = 20;
  private static final int PROPERTY_CLASS_TAG = 21;
  private static final int PROPERTY_SYNCPROVIDERNAME_TAG = 22;
  private static final int PROPERTY_SYNCPROVIDERVENDOR_TAG = 23;
  private static final int PROPERTY_SYNCPROVIDERVERSION_TAG = 24;
  private static final int PROPERTY_SYNCPROVIDERGRADE_TAG = 25;
  private static final int PROPERTY_DATASOURCELOCK_TAG = 26;
  private String[] metadataNames = { "column-count", "column-definition", "column-index", "auto-increment", "case-sensitive", "currency", "nullable", "signed", "searchable", "column-display-size", "column-label", "column-name", "schema-name", "column-precision", "column-scale", "table-name", "catalog-name", "column-type", "column-type-name", "null" };
  private static final int METADATA_COLUMNCOUNT_TAG = 0;
  private static final int METADATA_COLUMNDEFINITION_TAG = 1;
  private static final int METADATA_COLUMNINDEX_TAG = 2;
  private static final int METADATA_AUTOINCREMENT_TAG = 3;
  private static final int METADATA_CASESENSITIVE_TAG = 4;
  private static final int METADATA_CURRENCY_TAG = 5;
  private static final int METADATA_NULLABLE_TAG = 6;
  private static final int METADATA_SIGNED_TAG = 7;
  private static final int METADATA_SEARCHABLE_TAG = 8;
  private static final int METADATA_COLUMNDISPLAYSIZE_TAG = 9;
  private static final int METADATA_COLUMNLABEL_TAG = 10;
  private static final int METADATA_COLUMNNAME_TAG = 11;
  private static final int METADATA_SCHEMANAME_TAG = 12;
  private static final int METADATA_COLUMNPRECISION_TAG = 13;
  private static final int METADATA_COLUMNSCALE_TAG = 14;
  private static final int METADATA_TABLENAME_TAG = 15;
  private static final int METADATA_CATALOGNAME_TAG = 16;
  private static final int METADATA_COLUMNTYPE_TAG = 17;
  private static final int METADATA_COLUMNTYPENAME_TAG = 18;
  private static final int METADATA_NULL_TAG = 19;
  private String[] dataNames = { "currentRow", "insertRow", "deleteRow", "modifyRow", "columnValue", "updateValue", "null" };
  private static final int DATA_CURRENTROW_TAG = 0;
  private static final int DATA_INSERTROW_TAG = 1;
  private static final int DATA_DELETEROW_TAG = 2;
  private static final int DATA_MODIFYROW_TAG = 3;
  private static final int DATA_COLUMNVALUE_TAG = 4;
  private static final int DATA_UPDATEVALUE_TAG = 5;
  private static final int DATA_NULL_TAG = 6;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  OracleWebRowSetXmlReaderContHandler(RowSet paramRowSet)
  {
    this.wrset = ((OracleWebRowSet)paramRowSet);

    initialize();
  }

  public void characters(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    throws SAXException
  {
    String str = new String(paramArrayOfChar, paramInt1, paramInt2);
    processElement(str);
  }

  public void endDocument()
    throws SAXException
  {
    try
    {
      if (this.readReadOnlyValue)
      {
        this.wrset.setReadOnly(this.readReadOnlyValue);
      }

    }
    catch (SQLException localSQLException)
    {
      throw new SAXException(localSQLException.getMessage());
    }
  }

  public void endElement(String paramString1, String paramString2, String paramString3)
    throws SAXException
  {
    String str = (paramString2 == null) || (paramString2.equals("")) ? paramString3 : paramString2;

    switch (getState())
    {
    case 1:
      if (str.equals("properties"))
      {
        this.state = 0;
      }
      else
      {
        try
        {
          int i = ((Integer)this.propertyNameTagMap.get(str)).intValue();

          switch (i)
          {
          case 7:
            if (this.keyCols != null)
            {
              int[] arrayOfInt = new int[this.keyCols.size()];

              for (int k = 0; k < arrayOfInt.length; k++) {
                arrayOfInt[k] = Integer.parseInt((String)this.keyCols.elementAt(k));
              }
              this.wrset.setKeyColumns(arrayOfInt);
            }

            break;
          }

          setPropertyValue(this.propertyValue);
        }
        catch (SQLException localSQLException1)
        {
          throw new SAXException(localSQLException1.getMessage());
        }

        this.propertyValue = "";

        setNullValue(false);

        setTag(-1);
      }

      break;
    case 2:
      if (str.equals("metadata"))
      {
        try
        {
          this.wrset.setMetaData(this.rsetMetaData);

          this.state = 0;
        }
        catch (SQLException localSQLException2)
        {
          throw new SAXException("Error setting metadata in WebRowSet: " + localSQLException2.getMessage());
        }

      }
      else
      {
        try
        {
          setMetaDataValue(this.metadataValue);
        }
        catch (SQLException localSQLException3)
        {
          throw new SAXException("Error setting metadata value: " + localSQLException3.getMessage());
        }

        this.metadataValue = "";

        setNullValue(false);

        setTag(-1);
      }

      break;
    case 3:
      if (str.equals("data"))
      {
        this.state = 0;
        return;
      }

      int j = ((Integer)this.dataNameTagMap.get(str)).intValue();

      switch (j)
      {
      default:
        break;
      case 4:
        try
        {
          this.columnIndex += 1;

          insertValue(this.columnValue);

          this.columnValue = "";

          setNullValue(false);

          setTag(-1);
        }
        catch (SQLException localSQLException4)
        {
          throw new SAXException("Error inserting column values: " + localSQLException4.getMessage());
        }

      case 0:
        try
        {
          this.wrset.insertRow();
          this.wrset.moveToCurrentRow();
          this.wrset.next();

          OracleRow localOracleRow1 = this.wrset.getCurrentRow();
          localOracleRow1.setInsertedFlag(false);
          applyUpdates();
        }
        catch (SQLException localSQLException5)
        {
          throw new SAXException("Error constructing current row: " + localSQLException5.getMessage());
        }

      case 2:
        try
        {
          this.wrset.insertRow();
          this.wrset.moveToCurrentRow();
          this.wrset.next();

          OracleRow localOracleRow2 = this.wrset.getCurrentRow();
          localOracleRow2.setInsertedFlag(false);
          localOracleRow2.setRowDeleted(true);
          applyUpdates();
        }
        catch (SQLException localSQLException6)
        {
          throw new SAXException("Error constructing deleted row: " + localSQLException6.getMessage());
        }

      case 1:
        try
        {
          this.wrset.insertRow();
          this.wrset.moveToCurrentRow();
          this.wrset.next();

          applyUpdates();
        }
        catch (SQLException localSQLException7)
        {
          throw new SAXException("Error constructing inserted row: " + localSQLException7.getMessage());
        }

      case 3:
        try
        {
          this.wrset.insertRow();
          this.wrset.moveToCurrentRow();
          this.wrset.next();

          OracleRow localOracleRow3 = this.wrset.getCurrentRow();
          localOracleRow3.setRowDeleted(true);
          applyUpdates();
        }
        catch (SQLException localSQLException8)
        {
          throw new SAXException("Error constructing modified row: " + localSQLException8.getMessage());
        }
      }
      break;
    }
  }

  public void startElement(String paramString1, String paramString2, String paramString3, Attributes paramAttributes)
    throws SAXException
  {
    String str = (paramString2 == null) || (paramString2.equals("")) ? paramString3 : paramString2;

    switch (getState())
    {
    case 1:
      int i = ((Integer)this.propertyNameTagMap.get(str)).intValue();

      if (i == 18)
      {
        setNullValue(true);
        this.propertyValue = null;
      }
      else
      {
        setTag(i);
      }

      break;
    case 2:
      int j = ((Integer)this.metadataNameTagMap.get(str)).intValue();

      if (j == 19)
      {
        setNullValue(true);
        this.metadataValue = null;
      }
      else
      {
        setTag(j);
      }

      break;
    case 3:
      int k = ((Integer)this.dataNameTagMap.get(str)).intValue();

      if (k == 6)
      {
        setNullValue(true);
        this.columnValue = null;
      }
      else
      {
        setTag(k);

        if ((k == 0) || (k == 1) || (k == 2) || (k == 3))
        {
          this.columnIndex = 0;
          try
          {
            this.wrset.moveToInsertRow();
          } catch (SQLException localSQLException) {
          }
        }
      }
      break;
    default:
      setState(str);
    }
  }

  public void error(SAXParseException paramSAXParseException)
    throws SAXParseException
  {
    throw paramSAXParseException;
  }

  public void warning(SAXParseException paramSAXParseException)
    throws SAXParseException
  {
    System.out.println("** Warning, line " + paramSAXParseException.getLineNumber() + ", uri " + paramSAXParseException.getSystemId());

    System.out.println("   " + paramSAXParseException.getMessage());
  }

  private void initialize()
  {
    this.propertyNameTagMap = new Hashtable(30);
    int i = this.propertyNames.length;
    for (int j = 0; j < i; j++) {
      this.propertyNameTagMap.put(this.propertyNames[j], Integer.valueOf(j));
    }
    this.metadataNameTagMap = new Hashtable(30);
    i = this.metadataNames.length;
    for (j = 0; j < i; j++) {
      this.metadataNameTagMap.put(this.metadataNames[j], Integer.valueOf(j));
    }
    this.dataNameTagMap = new Hashtable(10);
    i = this.dataNames.length;
    for (j = 0; j < i; j++) {
      this.dataNameTagMap.put(this.dataNames[j], Integer.valueOf(j));
    }
    this.updatesToRowSet = new Vector();
    this.columnValue = "";
    this.propertyValue = "";
    this.metadataValue = "";
    this.isNullValue = false;
    this.columnIndex = 0;

    this.readReadOnlyValue = false;
  }

  protected void processElement(String paramString)
    throws SAXException
  {
    try
    {
      switch (getState())
      {
      case 1:
        this.propertyValue = paramString;
        break;
      case 2:
        this.metadataValue = paramString;
        break;
      case 3:
        setDataValue(paramString);
      }

    }
    catch (SQLException localSQLException)
    {
      throw new SAXException("processElement: " + localSQLException.getMessage());
    }
  }

  private BigDecimal getBigDecimalValue(String paramString)
  {
    return new BigDecimal(paramString);
  }

  private byte[] getBinaryValue(String paramString)
  {
    return paramString.getBytes();
  }

  private boolean getBooleanValue(String paramString)
  {
    return Boolean.valueOf(paramString).booleanValue();
  }

  private byte getByteValue(String paramString)
  {
    return Byte.parseByte(paramString);
  }

  private Date getDateValue(String paramString)
  {
    return new Date(getLongValue(paramString));
  }

  private double getDoubleValue(String paramString)
  {
    return Double.parseDouble(paramString);
  }

  private float getFloatValue(String paramString)
  {
    return Float.parseFloat(paramString);
  }

  private int getIntegerValue(String paramString)
  {
    return Integer.parseInt(paramString);
  }

  private long getLongValue(String paramString)
  {
    return Long.parseLong(paramString);
  }

  private boolean getNullValue()
  {
    return this.isNullValue;
  }

  private short getShortValue(String paramString)
  {
    return Short.parseShort(paramString);
  }

  private String getStringValue(String paramString)
  {
    return paramString;
  }

  private Time getTimeValue(String paramString)
  {
    return new Time(getLongValue(paramString));
  }

  private Timestamp getTimestampValue(String paramString)
  {
    return new Timestamp(getLongValue(paramString));
  }

  private Blob getBlobValue(String paramString)
    throws SQLException
  {
    return new OracleSerialBlob(paramString.getBytes());
  }

  private Clob getClobValue(String paramString)
    throws SQLException
  {
    return new OracleSerialClob(paramString.toCharArray());
  }

  private void applyUpdates()
    throws SAXException
  {
    if (this.updatesToRowSet.size() > 0)
    {
      try
      {
        Iterator localIterator = this.updatesToRowSet.iterator();
        while (localIterator.hasNext())
        {
          Object[] arrayOfObject = (Object[])localIterator.next();
          this.columnIndex = ((Integer)arrayOfObject[0]).intValue();
          insertValue((String)arrayOfObject[1]);
        }

        this.wrset.updateRow();
      }
      catch (SQLException localSQLException)
      {
        throw new SAXException("Error updating row: " + localSQLException.getMessage());
      }

      this.updatesToRowSet.removeAllElements();
    }
  }

  private void insertValue(String paramString)
    throws SQLException
  {
    if ((getNullValue()) || (paramString == null))
    {
      this.wrset.updateNull(this.columnIndex);
      return;
    }

    int i = this.wrset.getMetaData().getColumnType(this.columnIndex);

    switch (i)
    {
    case -7:
      this.wrset.updateByte(this.columnIndex, getByteValue(paramString));
      break;
    case 5:
      this.wrset.updateShort(this.columnIndex, getShortValue(paramString));
      break;
    case 4:
      this.wrset.updateInt(this.columnIndex, getIntegerValue(paramString));
      break;
    case -5:
      this.wrset.updateLong(this.columnIndex, getLongValue(paramString));
      break;
    case 6:
    case 7:
      this.wrset.updateFloat(this.columnIndex, getFloatValue(paramString));
      break;
    case 8:
      this.wrset.updateDouble(this.columnIndex, getDoubleValue(paramString));
      break;
    case 2:
    case 3:
      this.wrset.updateObject(this.columnIndex, getBigDecimalValue(paramString));
      break;
    case -4:
    case -3:
    case -2:
      this.wrset.updateBytes(this.columnIndex, getBinaryValue(paramString));
      break;
    case 91:
      this.wrset.updateDate(this.columnIndex, getDateValue(paramString));
      break;
    case 92:
      this.wrset.updateTime(this.columnIndex, getTimeValue(paramString));
      break;
    case 93:
      this.wrset.updateTimestamp(this.columnIndex, getTimestampValue(paramString));
      break;
    case -15:
    case -9:
    case -1:
    case 1:
    case 12:
      this.wrset.updateString(this.columnIndex, getStringValue(paramString));
      break;
    case 2004:
      this.wrset.updateBlob(this.columnIndex, getBlobValue(paramString));
      break;
    case 2005:
      this.wrset.updateClob(this.columnIndex, getClobValue(paramString));
      break;
    case 2011:
      this.wrset.updateNClob(this.columnIndex, (NClob)getClobValue(paramString));
      break;
    default:
      throw new SQLException("The type " + i + " is not supported currently.");
    }
  }

  private void setPropertyValue(String paramString)
    throws SQLException
  {
    boolean bool = getNullValue();
    SQLException localSQLException1;
    SQLException localSQLException2;
    switch (getTag()) { case 7:
    case 8:
    case 17:
    case 18:
    case 20:
    case 21:
    default:
      break;
    case 0:
      if (bool)
      {
        this.wrset.setCommand(null);
      }
      else
      {
        this.wrset.setCommand(paramString);
      }

      break;
    case 1:
      if (bool)
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 357);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      this.wrset.setConcurrency(getIntegerValue(paramString));
      break;
    case 2:
      if (bool)
      {
        this.wrset.setDataSourceName(null);
      }
      else
      {
        this.wrset.setDataSourceName(paramString);
      }

      break;
    case 3:
      if (bool)
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 357);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      this.wrset.setEscapeProcessing(getBooleanValue(paramString));
      break;
    case 4:
      if (bool)
      {
        localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 357);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      int i = this.wrset.getType();
      if (i != 1005)
      {
        this.wrset.setFetchDirection(getIntegerValue(paramString)); } break;
    case 5:
      if (bool)
      {
        localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 357);
        localSQLException2.fillInStackTrace();
        throw localSQLException2;
      }

      this.wrset.setFetchSize(getIntegerValue(paramString));
      break;
    case 6:
      if (bool)
      {
        localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 357);
        localSQLException2.fillInStackTrace();
        throw localSQLException2;
      }

      this.wrset.setTransactionIsolation(getIntegerValue(paramString));
      break;
    case 19:
      if (this.keyCols == null)
      {
        this.keyCols = new Vector();
      }

      this.keyCols.add(paramString);
      break;
    case 9:
      if (bool)
      {
        localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 357);
        localSQLException2.fillInStackTrace();
        throw localSQLException2;
      }

      this.wrset.setMaxFieldSize(getIntegerValue(paramString));
      break;
    case 10:
      if (bool)
      {
        localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 357);
        localSQLException2.fillInStackTrace();
        throw localSQLException2;
      }

      this.wrset.setMaxRows(getIntegerValue(paramString));
      break;
    case 11:
      if (bool)
      {
        localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 357);
        localSQLException2.fillInStackTrace();
        throw localSQLException2;
      }

      this.wrset.setQueryTimeout(getIntegerValue(paramString));
      break;
    case 12:
      if (bool)
      {
        localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 357);
        localSQLException2.fillInStackTrace();
        throw localSQLException2;
      }

      this.readReadOnlyValue = getBooleanValue(paramString);

      break;
    case 13:
      if (bool)
      {
        localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 357);
        localSQLException2.fillInStackTrace();
        throw localSQLException2;
      }

      this.wrset.setType(getIntegerValue(paramString));

      break;
    case 14:
      if (bool)
      {
        localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 357);
        localSQLException2.fillInStackTrace();
        throw localSQLException2;
      }

      this.wrset.setShowDeleted(getBooleanValue(paramString));
      break;
    case 15:
      if (bool)
      {
        this.wrset.setTableName(null);
      }
      else
      {
        this.wrset.setTableName(paramString);
      }

      break;
    case 16:
      if (bool)
      {
        this.wrset.setUrl(null);
      }
      else
      {
        this.wrset.setUrl(paramString);
      }

      break;
    case 22:
      if (bool)
      {
        this.wrset.setSyncProvider(null);
      }
      else
      {
        this.wrset.setSyncProvider(paramString);
      }
      break;
    }
  }

  private void setMetaDataValue(String paramString)
    throws SQLException
  {
    boolean bool = getNullValue();
    SQLException localSQLException2;
    switch (getTag())
    {
    case 1:
    default:
      break;
    case 0:
      if (bool)
      {
        SQLException localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 358);
        localSQLException1.fillInStackTrace();
        throw localSQLException1;
      }

      int i = getIntegerValue(paramString);

      this.rsetMetaData = new OracleRowSetMetaData(i);

      this.columnIndex = 0;

      break;
    case 2:
      this.columnIndex += 1;
      break;
    case 3:
      if (bool)
      {
        localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 358);
        localSQLException2.fillInStackTrace();
        throw localSQLException2;
      }

      this.rsetMetaData.setAutoIncrement(this.columnIndex, getBooleanValue(paramString));
      break;
    case 4:
      if (bool)
      {
        localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 358);
        localSQLException2.fillInStackTrace();
        throw localSQLException2;
      }

      this.rsetMetaData.setCaseSensitive(this.columnIndex, getBooleanValue(paramString));
      break;
    case 5:
      if (bool)
      {
        localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 358);
        localSQLException2.fillInStackTrace();
        throw localSQLException2;
      }

      this.rsetMetaData.setCurrency(this.columnIndex, getBooleanValue(paramString));
      break;
    case 6:
      if (bool)
      {
        localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 358);
        localSQLException2.fillInStackTrace();
        throw localSQLException2;
      }

      this.rsetMetaData.setNullable(this.columnIndex, getIntegerValue(paramString));
      break;
    case 7:
      if (bool)
      {
        localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 358);
        localSQLException2.fillInStackTrace();
        throw localSQLException2;
      }

      this.rsetMetaData.setSigned(this.columnIndex, getBooleanValue(paramString));
      break;
    case 8:
      if (bool)
      {
        localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 358);
        localSQLException2.fillInStackTrace();
        throw localSQLException2;
      }

      this.rsetMetaData.setSearchable(this.columnIndex, getBooleanValue(paramString));
      break;
    case 9:
      if (bool)
      {
        localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 358);
        localSQLException2.fillInStackTrace();
        throw localSQLException2;
      }

      this.rsetMetaData.setColumnDisplaySize(this.columnIndex, getIntegerValue(paramString));
      break;
    case 10:
      if (bool)
      {
        this.rsetMetaData.setColumnLabel(this.columnIndex, null);
      }
      else
      {
        this.rsetMetaData.setColumnLabel(this.columnIndex, paramString);
      }

      break;
    case 11:
      if (bool)
      {
        this.rsetMetaData.setColumnName(this.columnIndex, null);
      }
      else
      {
        this.rsetMetaData.setColumnName(this.columnIndex, paramString);
      }

      break;
    case 12:
      if (bool)
      {
        this.rsetMetaData.setSchemaName(this.columnIndex, null);
      }
      else
      {
        this.rsetMetaData.setSchemaName(this.columnIndex, paramString);
      }

      break;
    case 13:
      if (bool)
      {
        localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 358);
        localSQLException2.fillInStackTrace();
        throw localSQLException2;
      }

      this.rsetMetaData.setPrecision(this.columnIndex, getIntegerValue(paramString));
      break;
    case 14:
      if (bool)
      {
        localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 358);
        localSQLException2.fillInStackTrace();
        throw localSQLException2;
      }

      this.rsetMetaData.setScale(this.columnIndex, getIntegerValue(paramString));
      break;
    case 15:
      if (bool)
      {
        this.rsetMetaData.setTableName(this.columnIndex, null);
      }
      else
      {
        this.rsetMetaData.setTableName(this.columnIndex, paramString);
      }

      break;
    case 16:
      if (bool)
      {
        this.rsetMetaData.setCatalogName(this.columnIndex, null);
      }
      else
      {
        this.rsetMetaData.setCatalogName(this.columnIndex, paramString);
      }

      break;
    case 17:
      if (bool)
      {
        localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 358);
        localSQLException2.fillInStackTrace();
        throw localSQLException2;
      }

      this.rsetMetaData.setColumnType(this.columnIndex, getIntegerValue(paramString));
      break;
    case 18:
      if (bool)
      {
        this.rsetMetaData.setColumnTypeName(this.columnIndex, null);
      }
      else
      {
        this.rsetMetaData.setColumnTypeName(this.columnIndex, paramString);
      }
      break;
    }
  }

  private void setDataValue(String paramString)
    throws SQLException
  {
    switch (getTag())
    {
    case 1:
    case 2:
    case 3:
    default:
      break;
    case 4:
      this.columnValue = paramString;
      break;
    case 5:
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[1] = paramString;
      arrayOfObject[0] = Integer.valueOf(this.columnIndex);
      this.updatesToRowSet.add(arrayOfObject);
    }
  }

  protected void setNullValue(boolean paramBoolean)
  {
    this.isNullValue = paramBoolean;
  }

  private int getState()
  {
    return this.state;
  }

  private int getTag()
  {
    return this.tag;
  }

  private void setState(String paramString)
    throws SAXException
  {
    if (paramString.equals("webRowSet")) {
      this.state = 0;
    }
    else if (paramString.equals("properties"))
    {
      if (this.state != 1)
        this.state = 1;
      else {
        this.state = 0;
      }
    }
    else if (paramString.equals("metadata"))
    {
      if (this.state != 2)
        this.state = 2;
      else {
        this.state = 0;
      }
    }
    else if (paramString.equals("data"))
      if (this.state != 3)
        this.state = 3;
      else
        this.state = 0;
  }

  private void setTag(int paramInt)
  {
    this.tag = paramInt;
  }

  protected OracleConnection getConnectionDuringExceptionHandling()
  {
    return null;
  }
}