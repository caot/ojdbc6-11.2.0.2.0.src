package oracle.jdbc.driver;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLXML;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stax.StAXResult;
import javax.xml.transform.stax.StAXSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import oracle.sql.DatumWithConnection;
import oracle.sql.OPAQUE;
import oracle.sql.OpaqueDescriptor;
import oracle.xdb.XMLType;
import oracle.xml.parser.v2.XMLDocument;
import oracle.xml.parser.v2.XMLSAXSerializer;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

final class OracleSQLXML extends DatumWithConnection
  implements SQLXML, Opaqueable
{
  private XMLType xdb;
  private boolean isReadable = false;
  private boolean isWriteable = false;

  private DOMResult domResult = null;
  private XMLSAXSerializer serializer = null;
  private ByteArrayOutputStream oStream = null;
  static final int INITIAL_BUFFER_SIZE = 16384;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  OracleSQLXML(Connection paramConnection)
    throws SQLException
  {
    setPhysicalConnectionOf(paramConnection);
    this.isReadable = false;
    this.isWriteable = true;
  }

  OracleSQLXML(Connection paramConnection, OPAQUE paramOPAQUE)
    throws SQLException
  {
    setPhysicalConnectionOf(paramConnection);
    this.isReadable = true;
    this.isWriteable = false;
    if ((paramOPAQUE instanceof XMLType))
      this.xdb = ((XMLType)paramOPAQUE);
    else
      this.xdb = new XMLType(paramOPAQUE.getDescriptor(), getInternalConnection(), paramOPAQUE.getBytesValue());
  }

  OracleSQLXML(OpaqueDescriptor paramOpaqueDescriptor, Connection paramConnection, byte[] paramArrayOfByte) throws SQLException
  {
    this(paramConnection, new OPAQUE(paramOpaqueDescriptor, paramArrayOfByte, paramConnection));
  }

  OracleSQLXML(Connection paramConnection, InputStream paramInputStream) throws SQLException
  {
    setPhysicalConnectionOf(paramConnection);
    this.isReadable = true;
    this.isWriteable = false;
    this.xdb = new XMLType(getInternalConnection(), paramInputStream);
  }

  OracleSQLXML(Connection paramConnection, XMLType paramXMLType)
    throws SQLException
  {
    setPhysicalConnectionOf(paramConnection);
    this.isReadable = true;
    this.isWriteable = false;
    this.xdb = paramXMLType;
  }

  public OPAQUE toOpaque()
    throws SQLException
  {
    return getXMLTypeInternal();
  }

  XMLType getXMLTypeInternal()
    throws SQLException
  {
    if (this.isWriteable) {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 260);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
    Object localObject2;
    if (this.serializer != null)
      try {
        this.serializer.flush();
      }
      catch (IOException localIOException1)
      {
        localObject2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localIOException1);
        ((SQLException)localObject2).fillInStackTrace();
        throw ((Throwable)localObject2);
      }
      finally
      {
        this.serializer = null;
      }
    Object localObject1;
    if (this.oStream != null) {
      try {
        this.oStream.close();
      }
      catch (IOException localIOException2)
      {
        localObject2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localIOException2);
        ((SQLException)localObject2).fillInStackTrace();
        throw ((Throwable)localObject2);
      }

      this.xdb = XMLType.createXML(getInternalConnection(), new ByteArrayInputStream(this.oStream.toByteArray()));
      this.oStream = null;
    }
    else if (this.domResult != null) {
      localObject1 = this.domResult.getNode();
      localObject2 = null;
      if ((localObject1 instanceof Document)) { localObject2 = (Document)localObject1;
      } else {
        localObject2 = new XMLDocument();
        localObject1 = ((Document)localObject2).importNode((Node)localObject1, true);
        ((Document)localObject2).insertBefore((Node)localObject1, null);
      }
      this.xdb = XMLType.createXML(getInternalConnection(), (Document)localObject2);
      this.domResult = null;
    }
    if (this.xdb == null)
    {
      localObject1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 260);
      ((SQLException)localObject1).fillInStackTrace();
      throw ((Throwable)localObject1);
    }
    return this.xdb;
  }

  public byte[] getBytes()
  {
    return null;
  }

  public boolean isConvertibleTo(Class paramClass)
  {
    return false;
  }

  public Object toJdbc()
    throws SQLException
  {
    return this;
  }

  public Object makeJdbcArray(int paramInt)
  {
    return null;
  }

  public void free()
    throws SQLException
  {
    this.isReadable = false;
    this.isWriteable = false;
    this.oStream = null;
    this.domResult = null;
    if (this.xdb != null) this.xdb.close();
    this.xdb = null;
  }

  InputStream getInputStream()
    throws SQLException
  {
    return new ByteArrayInputStream(this.xdb.getStringVal().getBytes());
  }

  public InputStream getBinaryStream()
    throws SQLException
  {
    if (!this.isReadable) {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 261);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
    this.isReadable = false;
    return getInputStream();
  }

  public Reader getCharacterStream()
    throws SQLException
  {
    if (!this.isReadable) {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 261);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
    this.isReadable = false;
    return new StringReader(this.xdb.getStringVal());
  }

  public <T extends Source> T getSource(Class<T> paramClass)
    throws SQLException
  {
    Object localObject1;
    if (!this.isReadable) {
      localObject1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 261);
      ((SQLException)localObject1).fillInStackTrace();
      throw ((Throwable)localObject1);
    }
    this.isReadable = false;
    if (paramClass == DOMSource.class) {
      localObject1 = this.xdb.getDocument();
      return new DOMSource((Node)localObject1);
    }
    if (paramClass == SAXSource.class) {
      localObject1 = new InputSource(getInputStream());
      return new SAXSource((InputSource)localObject1);
    }
    if (paramClass == StAXSource.class) {
      try {
        localObject1 = XMLInputFactory.newInstance();
        localObject2 = ((XMLInputFactory)localObject1).createXMLStreamReader(getInputStream());
        return new StAXSource((XMLStreamReader)localObject2);
      }
      catch (XMLStreamException localXMLStreamException)
      {
        Object localObject2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localXMLStreamException);
        ((SQLException)localObject2).fillInStackTrace();
        throw ((Throwable)localObject2);
      }
    }

    if (paramClass == StreamSource.class) {
      return new StreamSource(getInputStream());
    }

    this.isReadable = true;

    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 264);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }

  public String getString()
    throws SQLException
  {
    if (!this.isReadable) {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 261);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
    this.isReadable = false;
    return this.xdb.getStringVal();
  }

  protected OutputStream getOutputStream()
    throws SQLException
  {
    if (this.oStream != null) throw new SQLException("Internal Error");
    this.oStream = new ByteArrayOutputStream(16384);
    return this.oStream;
  }

  public OutputStream setBinaryStream()
    throws SQLException
  {
    if (!this.isWriteable) {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 262);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
    this.isWriteable = false;
    return getOutputStream();
  }

  public Writer setCharacterStream()
    throws SQLException
  {
    if (!this.isWriteable) {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 262);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
    this.isWriteable = false;
    return new OutputStreamWriter(getOutputStream());
  }

  public <T extends Result> T setResult(Class<T> paramClass)
    throws SQLException
  {
    Object localObject;
    if (!this.isWriteable) {
      localObject = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 262);
      ((SQLException)localObject).fillInStackTrace();
      throw ((Throwable)localObject);
    }
    this.isWriteable = false;
    if (paramClass == DOMResult.class) {
      this.domResult = new DOMResult();
      return this.domResult;
    }
    if (paramClass == SAXResult.class) {
      this.serializer = new XMLSAXSerializer(getOutputStream());
      return new SAXResult(this.serializer);
    }
    if (paramClass == StAXResult.class) {
      try {
        localObject = XMLOutputFactory.newInstance();
        return new StAXResult(((XMLOutputFactory)localObject).createXMLStreamWriter(getOutputStream()));
      }
      catch (XMLStreamException localXMLStreamException)
      {
        SQLException localSQLException2 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), localXMLStreamException);
        localSQLException2.fillInStackTrace();
        throw localSQLException2;
      }
    }

    if (paramClass == StreamResult.class) {
      return new StreamResult(getOutputStream());
    }

    this.isWriteable = true;

    SQLException localSQLException1 = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 263);
    localSQLException1.fillInStackTrace();
    throw localSQLException1;
  }

  public void setString(String paramString)
    throws SQLException
  {
    if (!this.isWriteable) {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 262);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
    this.isWriteable = false;
    this.xdb = new XMLType(getInternalConnection(), paramString);
  }
}