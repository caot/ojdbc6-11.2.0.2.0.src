package oracle.jdbc.rowset;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.rowset.WebRowSet;
import oracle.jdbc.driver.DatabaseError;

public class OracleWebRowSet extends OracleCachedRowSet
  implements WebRowSet
{
  static final long serialVersionUID = 617253792409477080L;
  private transient OracleWebRowSetXmlReader xmlReader;
  private transient OracleWebRowSetXmlWriter xmlWriter;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  public OracleWebRowSet()
    throws SQLException
  {
    this.xmlReader = new OracleWebRowSetXmlReaderImpl();
    this.xmlWriter = new OracleWebRowSetXmlWriterImpl();

    setReadOnly(false);
  }

  public void readXml(Reader paramReader)
    throws SQLException
  {
    if (this.xmlReader != null)
    {
      this.xmlReader.readXML(this, paramReader);
    }
    else
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 355);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  public void writeXml(Writer paramWriter)
    throws SQLException
  {
    if (this.xmlWriter != null)
    {
      this.xmlWriter.writeXML(this, paramWriter);
    }
    else
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 356);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }
  }

  public void writeXml(ResultSet paramResultSet, Writer paramWriter)
    throws SQLException
  {
    populate(paramResultSet);
    writeXml(paramWriter);
  }

  public void readXml(InputStream paramInputStream)
    throws SQLException
  {
    readXml(new InputStreamReader(paramInputStream));
  }

  public void writeXml(OutputStream paramOutputStream)
    throws SQLException
  {
    writeXml(new OutputStreamWriter(paramOutputStream));
  }

  public void writeXml(ResultSet paramResultSet, OutputStream paramOutputStream)
    throws SQLException
  {
    writeXml(paramResultSet, new OutputStreamWriter(paramOutputStream));
  }
}