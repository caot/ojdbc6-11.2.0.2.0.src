package oracle.net.jdbc.nl;

public class NVFactory
{
  public NVPair createNVPair(String paramString)
    throws InvalidSyntaxException, NLException
  {
    NVTokens localNVTokens = new NVTokens();
    localNVTokens.parseTokens(paramString);
    return _readTopLevelNVPair(localNVTokens);
  }

  private NVPair _readTopLevelNVPair(NVTokens paramNVTokens)
    throws InvalidSyntaxException, NLException
  {
    int i = paramNVTokens.getToken();
    paramNVTokens.eatToken();
    if (i != 1)
    {
      localObject = new Object[] { "(", getContext(paramNVTokens) };
      throw new InvalidSyntaxException("SyntaxError-04602", (Object[])localObject);
    }
    Object localObject = _readNVLiteral(paramNVTokens);
    NVPair localNVPair = new NVPair((String)localObject);
    if ((i = paramNVTokens.getToken()) == 3)
    {
      while ((i == 8) || (i == 3))
      {
        localObject = (String)localObject + paramNVTokens.popLiteral();
        i = paramNVTokens.getToken();
      }
      localNVPair.setName((String)localObject);
      return _readRightHandSide(localNVPair, paramNVTokens);
    }
    return _readRightHandSide(localNVPair, paramNVTokens);
  }

  private NVPair _readNVPair(NVTokens paramNVTokens)
    throws InvalidSyntaxException, NLException
  {
    int i = paramNVTokens.getToken();
    paramNVTokens.eatToken();
    if ((i != 1) && (i != 3))
    {
      localObject = new Object[] { "( or ,", getContext(paramNVTokens) };
      throw new InvalidSyntaxException("SyntaxError-04602", (Object[])localObject);
    }
    Object localObject = _readNVLiteral(paramNVTokens);
    NVPair localNVPair = new NVPair((String)localObject);
    return _readRightHandSide(localNVPair, paramNVTokens);
  }

  private NVPair _readRightHandSide(NVPair paramNVPair, NVTokens paramNVTokens)
    throws InvalidSyntaxException, NLException
  {
    Object localObject;
    switch (i = paramNVTokens.getToken())
    {
    case 4:
      paramNVTokens.eatToken();
      i = paramNVTokens.getToken();
      if (i == 8)
      {
        localObject = _readNVLiteral(paramNVTokens);
        paramNVPair.setAtom((String)localObject);
      }
      else
      {
        _readNVList(paramNVTokens, paramNVPair);
      }
      break;
    case 2:
    case 3:
      paramNVPair.setAtom(paramNVPair.getName());
      break;
    default:
      localObject = new Object[] { "=", getContext(paramNVTokens) };
      throw new InvalidSyntaxException("SyntaxError-04602", (Object[])localObject);
    }
    int i = paramNVTokens.getToken();
    if (i == 2)
    {
      paramNVTokens.eatToken();
    }
    else if (i != 3)
    {
      localObject = new Object[] { paramNVTokens.getLiteral(), getContext(paramNVTokens) };
      throw new InvalidSyntaxException("UnexpectedChar-04605", (Object[])localObject);
    }
    return paramNVPair;
  }

  private String _readNVLiteral(NVTokens paramNVTokens)
    throws InvalidSyntaxException, NLException
  {
    int i = paramNVTokens.getToken();
    if (i != 8)
    {
      Object[] arrayOfObject = { "LITERAL", getContext(paramNVTokens) };
      throw new InvalidSyntaxException("SyntaxError-04602", arrayOfObject);
    }
    return paramNVTokens.popLiteral();
  }

  private void _readNVList(NVTokens paramNVTokens, NVPair paramNVPair)
    throws InvalidSyntaxException, NLException
  {
    int i = paramNVTokens.getToken();
    if ((i != 1) && (i != 3))
      return;
    NVPair localNVPair = _readNVPair(paramNVTokens);
    paramNVPair.addListElement(localNVPair);
    if (((i == 3) || (localNVPair.getName() == localNVPair.getAtom())) && (paramNVPair.getListType() != NVPair.LIST_COMMASEP))
      paramNVPair.setListType(NVPair.LIST_COMMASEP);
    _readNVList(paramNVTokens, paramNVPair);
  }

  private String getContext(NVTokens paramNVTokens)
    throws NLException
  {
    return " " + paramNVTokens.popLiteral() + " " + paramNVTokens.popLiteral() + " " + paramNVTokens.popLiteral();
  }
}