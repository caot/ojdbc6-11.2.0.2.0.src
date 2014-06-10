package oracle.security.o5logon;

import java.io.IOException;

public final class a extends IOException
{
  private int a;

  public a(int paramInt)
  {
    this.a = paramInt;
  }

  public final String getMessage()
  {
    return Integer.toString(this.a);
  }
}