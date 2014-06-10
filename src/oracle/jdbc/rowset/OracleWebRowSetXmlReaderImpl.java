package oracle.jdbc.rowset;

import java.io.PrintStream;
import java.io.Reader;
import java.sql.SQLException;
import javax.sql.RowSetInternal;
import javax.sql.rowset.WebRowSet;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;

class OracleWebRowSetXmlReaderImpl
  implements OracleWebRowSetXmlReader
{
  private static final String JAVA_SAXPARSER_PROPERTY = "javax.xml.parsers.SAXParserFactory";
  private static final String JAVA_DOMPARSER_PROPERTY = "javax.xml.parsers.DocumentBuilderFactory";
  private static final String ORACLE_JAXP_SAXPARSER_FACTORY = "oracle.xml.jaxp.JXSAXParserFactory";
  private static final String ORACLE_JAXP_DOMPARSER_FACTORY = "oracle.xml.jaxp.JXDocumentBuilderFactory";
  private static final String JAXP_SCHEMA_LANGUAGE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
  private static final String JAXP_SCHEMA_SOURCE = "http://java.sun.com/xml/jaxp/properties/schemaSource";
  private static final String W3C_XML_SCHEMA = "http://www.w3.org/2001/XMLSchema";
  private static final String WEBROWSET_SCHEMA = "http://java.sun.com/xml/ns/jdbc/webrowset.xsd";
  private Document document;
  private String parserStr;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  OracleWebRowSetXmlReaderImpl()
  {
    this.document = null;
    this.parserStr = null;
  }

  public void readXML(WebRowSet paramWebRowSet, Reader paramReader)
    throws SQLException
  {
    this.parserStr = getSystemProperty("javax.xml.parsers.SAXParserFactory");
    if (this.parserStr != null)
    {
      readXMLSax((OracleWebRowSet)paramWebRowSet, paramReader);
    }
    else
    {
      this.parserStr = getSystemProperty("javax.xml.parsers.DocumentBuilderFactory");
      if (this.parserStr != null)
      {
        readXMLDom((OracleWebRowSet)paramWebRowSet, paramReader);
      }
      else
        throw new SQLException("No valid JAXP parser property specified");
    }
  }

  public void readData(RowSetInternal paramRowSetInternal)
    throws SQLException
  {
  }

  private void readXMLSax(OracleWebRowSet paramOracleWebRowSet, Reader paramReader)
    throws SQLException
  {
    try
    {
      InputSource localInputSource = new InputSource(paramReader);
      OracleWebRowSetXmlReaderContHandler localOracleWebRowSetXmlReaderContHandler = new OracleWebRowSetXmlReaderContHandler(paramOracleWebRowSet);

      SAXParserFactory localSAXParserFactory = SAXParserFactory.newInstance();

      localSAXParserFactory.setNamespaceAware(true);
      localSAXParserFactory.setValidating(true);
      SAXParser localSAXParser = localSAXParserFactory.newSAXParser();

      localSAXParser.setProperty("http://java.sun.com/xml/jaxp/properties/schemaLanguage", "http://www.w3.org/2001/XMLSchema");
      localSAXParser.setProperty("http://java.sun.com/xml/jaxp/properties/schemaSource", "http://java.sun.com/xml/ns/jdbc/webrowset.xsd");

      XMLReader localXMLReader = localSAXParser.getXMLReader();
      localXMLReader.setContentHandler(localOracleWebRowSetXmlReaderContHandler);

      localXMLReader.parse(localInputSource);
    }
    catch (SAXParseException localSAXParseException)
    {
      System.out.println("** Parsing error, line " + localSAXParseException.getLineNumber() + ", uri " + localSAXParseException.getSystemId());

      System.out.println("   " + localSAXParseException.getMessage());
      localSAXParseException.printStackTrace();
      throw new SQLException(localSAXParseException.getMessage());
    }
    catch (SAXNotRecognizedException localSAXNotRecognizedException)
    {
      localSAXNotRecognizedException.printStackTrace();
      throw new SQLException("readXMLSax: SAXNotRecognizedException: " + localSAXNotRecognizedException.getMessage());
    }
    catch (SAXException localSAXException)
    {
      localSAXException.printStackTrace();
      throw new SQLException("readXMLSax: SAXException: " + localSAXException.getMessage());
    }
    catch (FactoryConfigurationError localFactoryConfigurationError)
    {
      localFactoryConfigurationError.printStackTrace();
      throw new SQLException("readXMLSax: Parser factory config: " + localFactoryConfigurationError.getMessage());
    }
    catch (ParserConfigurationException localParserConfigurationException)
    {
      localParserConfigurationException.printStackTrace();
      throw new SQLException("readXMLSax: Parser config: " + localParserConfigurationException.getMessage());
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      throw new SQLException("readXMLSax: " + localException.getMessage());
    }
  }

  private void readXMLDom(OracleWebRowSet paramOracleWebRowSet, Reader paramReader)
    throws SQLException
  {
    try
    {
      InputSource localInputSource = new InputSource(paramReader);
      OracleWebRowSetXmlReaderDomHandler localOracleWebRowSetXmlReaderDomHandler = new OracleWebRowSetXmlReaderDomHandler(paramOracleWebRowSet);

      DocumentBuilderFactory localDocumentBuilderFactory = DocumentBuilderFactory.newInstance();

      localDocumentBuilderFactory.setNamespaceAware(true);
      localDocumentBuilderFactory.setValidating(true);

      localDocumentBuilderFactory.setAttribute("http://java.sun.com/xml/jaxp/properties/schemaLanguage", "http://www.w3.org/2001/XMLSchema");
      localDocumentBuilderFactory.setAttribute("http://java.sun.com/xml/jaxp/properties/schemaSource", "http://java.sun.com/xml/ns/jdbc/webrowset.xsd");
      DocumentBuilder localDocumentBuilder = localDocumentBuilderFactory.newDocumentBuilder();

      this.document = localDocumentBuilder.parse(localInputSource);

      localOracleWebRowSetXmlReaderDomHandler.readXMLDocument(this.document);
    }
    catch (SAXException localSAXException)
    {
      localSAXException.printStackTrace();
      throw new SQLException("readXMLDom: SAXException: " + localSAXException.getMessage());
    }
    catch (FactoryConfigurationError localFactoryConfigurationError)
    {
      localFactoryConfigurationError.printStackTrace();
      throw new SQLException("readXMLDom: Parser factory config: " + localFactoryConfigurationError.getMessage());
    }
    catch (ParserConfigurationException localParserConfigurationException)
    {
      localParserConfigurationException.printStackTrace();
      throw new SQLException("readXMLDom: Parser config: " + localParserConfigurationException.getMessage());
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      throw new SQLException("readXMLDom: " + localException.getMessage());
    }
  }

  private String getSystemProperty(String paramString)
  {
    String str = null;
    try
    {
      str = System.getProperty(paramString);
    }
    catch (SecurityException localSecurityException)
    {
      str = null;
    }

    return str;
  }
}