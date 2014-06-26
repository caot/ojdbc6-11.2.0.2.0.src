package oracle.jdbc.rowset;

import javax.sql.RowSet;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

class OracleWebRowSetXmlReaderDomHandler extends OracleWebRowSetXmlReaderContHandler
{
  OracleWebRowSetXmlReaderDomHandler(RowSet paramRowSet)
  {
    super(paramRowSet);
  }

  void readXMLDocument(Document paramDocument)
    throws SAXException
  {
    Element localElement = paramDocument.getDocumentElement();
    startElement(null, null, "webRowSet", null);

    Node localNode1 = localElement.getElementsByTagName("properties").item(0);

    startElement(null, null, "properties", null);

    NodeList localNodeList1 = localNode1.getChildNodes();
    int i = localNodeList1.getLength();

    for (int j = 0; j < i; j++)
    {
      Node localNode2 = localNodeList1.item(j);

      if (!(localNode2 instanceof Text))
      {
        String str1 = localNode2.getNodeName();
        startElement(null, null, str1, null);

        if (localNode2.hasChildNodes())
        {
          processElement(localNode2.getFirstChild().getNodeValue());
        }
        else
        {
          processElement("");
        }

        endElement(null, null, str1);
      }
    }
    endElement(null, null, "properties");

    Node localNode3 = localElement.getElementsByTagName("metadata").item(0);

    startElement(null, null, "metadata", null);

    Node localNode4 = localNode3.getFirstChild().getNextSibling();

    String str2 = localNode4.getNodeName();
    startElement(null, null, str2, null);

    processElement(localNode4.getFirstChild().getNodeValue());
    endElement(null, null, str2);

    NodeList localNodeList2 = localNode3.getChildNodes();
    int k = localNodeList2.getLength();
    Object localObject2;
    Object localObject3;
    for (int m = 3; m < k; m++)
    {
      Node localObject1 = localNodeList2.item(m);

      NodeList localNodeList3 = ((Node)localObject1).getChildNodes();
      int i1 = localNodeList3.getLength();

      for (int i2 = 0; i2 < i1; i2++)
      {
        localObject2 = localNodeList3.item(i2);

        if (!(localObject2 instanceof Text))
        {
          localObject3 = ((Node)localObject2).getNodeName();
          startElement(null, null, (String)localObject3, null);

          if (((Node)localObject2).hasChildNodes())
          {
            processElement(((Node)localObject2).getFirstChild().getNodeValue());
          }
          else
          {
            processElement("");
          }

          endElement(null, null, (String)localObject3);
        }
      }
    }
    endElement(null, null, "metadata");

    Node localNode5 = localElement.getElementsByTagName("data").item(0);
    startElement(null, null, "data", null);

    Object localObject1 = localNode5.getChildNodes();
    int n = ((NodeList)localObject1).getLength();

    for (int i1 = 0; i1 < n; i1++)
    {
      Node localNode6 = ((NodeList)localObject1).item(i1);

      if (!(localNode6 instanceof Text))
      {
        localObject2 = localNode6.getNodeName();
        startElement(null, null, (String)localObject2, null);

        localObject3 = localNode6.getChildNodes();
        int i3 = ((NodeList)localObject3).getLength();

        for (int i4 = 0; i4 < i3; i4++)
        {
          Node localNode7 = ((NodeList)localObject3).item(i4);

          if (!(localNode7 instanceof Text))
          {
            String str3 = localNode7.getNodeName();
            startElement(null, null, str3, null);
            String str4;
            if (localNode7.hasChildNodes())
            {
              str4 = localNode7.getFirstChild().getNodeValue();
              if (str4 == null)
              {
                startElement(null, null, "null", null);
              }

            }
            else
            {
              str4 = "";
            }

            processElement(str4);

            endElement(null, null, str3);
          }

        }

        endElement(null, null, (String)localObject2);
      }
    }

    endElement(null, null, "data");

    endElement(null, null, "webRowSet");

    endDocument();
  }
}