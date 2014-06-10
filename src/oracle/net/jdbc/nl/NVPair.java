package oracle.net.jdbc.nl;

import java.io.PrintStream;
import java.util.Vector;

public final class NVPair
{
  public static int RHS_NONE = 0;
  public static int RHS_ATOM = 1;
  public static int RHS_LIST = 2;
  public static int LIST_REGULAR = 3;
  public static int LIST_COMMASEP = 4;
  private String _name;
  private int _rhsType;
  private String _atom;
  private Vector _list;
  private int _listType;
  private NVPair _parent;
  private String _linesep = System.getProperty("line.separator");

  public NVPair(String paramString)
  {
    this._name = paramString;
    this._atom = null;
    this._list = null;
    this._listType = LIST_REGULAR;
    this._parent = null;
    this._rhsType = RHS_NONE;
  }

  public NVPair(String paramString1, String paramString2)
    throws InvalidSyntaxException
  {
    this(paramString1);
    setAtom(paramString2);
  }

  public NVPair(String paramString, NVPair paramNVPair)
  {
    this(paramString);
    addListElement(paramNVPair);
  }

  public String getName()
  {
    return this._name;
  }

  public void setName(String paramString)
  {
    this._name = paramString;
  }

  public NVPair getParent()
  {
    return this._parent;
  }

  private void _setParent(NVPair paramNVPair)
  {
    this._parent = paramNVPair;
  }

  public int getRHSType()
  {
    return this._rhsType;
  }

  public int getListType()
  {
    return this._listType;
  }

  public void setListType(int paramInt)
  {
    this._listType = paramInt;
  }

  public String getAtom()
  {
    return this._atom;
  }

  public void setAtom(String paramString)
    throws InvalidSyntaxException
  {
    if ((this._name.indexOf("COMMENT") == -1) && (containsComment(paramString)))
    {
      Object[] arrayOfObject = { "#", getName() };
      throw new InvalidSyntaxException("UnexpectedChar-04603", arrayOfObject);
    }
    this._rhsType = RHS_ATOM;
    this._atom = paramString;
    this._list = null;
  }

  private boolean containsComment(String paramString)
  {
    for (int i = 0; i < paramString.length(); i++)
      if (paramString.charAt(i) == '#')
        if (i != 0)
        {
          if (paramString.charAt(i - 1) != '\\')
            return true;
        }
        else
          return true;
    return false;
  }

  public int getListSize()
  {
    if (this._list == null)
      return 0;
    return this._list.size();
  }

  public NVPair getListElement(int paramInt)
  {
    if (this._list == null)
      return null;
    return (NVPair)this._list.elementAt(paramInt);
  }

  public void addListElement(NVPair paramNVPair)
  {
    if (this._list == null)
    {
      this._rhsType = RHS_LIST;
      this._list = new Vector(3, 5);
      this._atom = null;
    }
    this._list.addElement(paramNVPair);
    paramNVPair._setParent(this);
  }

  public void removeListElement(int paramInt)
  {
    if (this._list != null)
    {
      this._list.removeElementAt(paramInt);
      if (getListSize() == 0)
      {
        this._list = null;
        this._rhsType = RHS_NONE;
      }
    }
  }

  private String space(int paramInt)
  {
    String str = new String("");
    for (int i = 0; i < paramInt; i++)
      str = str + " ";
    return str;
  }

  public String trimValueToString()
  {
    String str = valueToString().trim();
    return str.substring(1, str.length() - 1);
  }

  public String valueToString()
  {
    String str = "";
    if (this._rhsType == RHS_ATOM)
    {
      str = str + this._atom;
    }
    else if (this._rhsType == RHS_LIST)
    {
      int i;
      if (this._listType == LIST_REGULAR)
        for (i = 0; i < getListSize(); i++)
          str = str + getListElement(i).toString();
      else if (this._listType == LIST_COMMASEP)
        for (i = 0; i < getListSize(); i++)
        {
          NVPair localNVPair = getListElement(i);
          str = str + localNVPair.getName();
          if (i != getListSize() - 1)
            str = str + ", ";
        }
    }
    return str;
  }

  public String toString()
  {
    String str = "(" + this._name + "=";
    if (this._rhsType == RHS_ATOM)
    {
      str = str + this._atom;
    }
    else if (this._rhsType == RHS_LIST)
    {
      int i;
      if (this._listType == LIST_REGULAR)
      {
        for (i = 0; i < getListSize(); i++)
          str = str + getListElement(i).toString();
      }
      else if (this._listType == LIST_COMMASEP)
      {
        str = str + " (";
        for (i = 0; i < getListSize(); i++)
        {
          NVPair localNVPair = getListElement(i);
          str = str + localNVPair.getName();
          if (i != getListSize() - 1)
            str = str + ", ";
        }
        str = str + ")";
      }
    }
    str = str + ")";
    return str;
  }

  public String toString(int paramInt, boolean paramBoolean)
  {
    String str1 = "";
    String str2 = new String(this._name);
    if (this._rhsType == RHS_LIST)
    {
      if (this._listType == LIST_REGULAR)
      {
        String str3 = "";
        for (int j = 0; j < getListSize(); j++)
          if ((str2.equalsIgnoreCase("ADDRESS")) || (str2.equalsIgnoreCase("RULE")))
            str3 = str3 + getListElement(j).toString(paramInt + 1, false);
          else
            str3 = str3 + getListElement(j).toString(paramInt + 1, true);
        if (!str3.equals(""))
        {
          if ((str2.equalsIgnoreCase("ADDRESS")) || (str2.equalsIgnoreCase("RULE")))
            str1 = str1 + space(paramInt * 2) + "(" + this._name + " = ";
          else
            str1 = str1 + space(paramInt * 2) + "(" + this._name + " =" + this._linesep;
          str1 = str1 + str3;
          if ((str2.equalsIgnoreCase("ADDRESS")) || (str2.equalsIgnoreCase("RULE")))
            str1 = str1 + ")" + this._linesep;
          else if (paramInt == 0)
            str1 = str1 + ")";
          else if (paramInt == 1)
            str1 = str1 + space(paramInt * 2) + ")";
          else
            str1 = str1 + space(paramInt * 2) + ")" + this._linesep;
        }
      }
      else if (this._listType == LIST_COMMASEP)
      {
        str1 = str1 + "(" + this._name + "=" + " (";
        for (int i = 0; i < getListSize(); i++)
        {
          NVPair localNVPair = getListElement(i);
          str1 = str1 + localNVPair.getName();
          if (i != getListSize() - 1)
            str1 = str1 + ", ";
        }
        str1 = str1 + ")" + ")";
      }
    }
    else if (this._rhsType == RHS_ATOM)
      if (paramInt == 0)
      {
        if (str2.indexOf("COMMENT") != -1)
        {
          this._atom = modifyCommentString(this._atom);
          str1 = str1 + "(" + this._atom + ")";
        }
        else
        {
          str1 = str1 + "(" + this._name + " = " + this._atom + ")";
        }
      }
      else if (str2.indexOf("COMMENT") != -1)
      {
        this._atom = modifyCommentString(this._atom);
        str1 = str1 + this._atom + this._linesep;
      }
      else if (!paramBoolean)
      {
        str1 = str1 + "(" + this._name + " = " + this._atom + ")";
      }
      else
      {
        str1 = str1 + space(paramInt * 2) + "(" + this._name + " = " + this._atom + ")";
        str1 = str1 + this._linesep;
      }
    return str1;
  }

  public String modifyCommentString(String paramString)
  {
    String str = "";
    int i = 0;
    while (i < paramString.length())
    {
      int j = paramString.charAt(i);
      switch (j)
      {
      case 92:
        if ((paramString.charAt(i + 1) == '(') || (paramString.charAt(i + 1) == '=') || (paramString.charAt(i + 1) == ')') || (paramString.charAt(i + 1) == ',') || (paramString.charAt(i + 1) == '\\'))
          i++;
        break;
      }
      str = str + paramString.charAt(i++);
    }
    return str;
  }

  public void println()
  {
    System.out.println(toString());
  }

  public void println(PrintStream paramPrintStream)
  {
    if (this._rhsType == RHS_ATOM)
      paramPrintStream.println("          (" + this._name + " = " + this._atom + ")");
    else if (this._rhsType == RHS_LIST)
      for (int i = 0; i < getListSize(); i++)
        getListElement(i).println(paramPrintStream);
  }
}