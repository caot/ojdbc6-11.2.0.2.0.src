package oracle.net.aso;

import java.io.IOException;

public final class s extends IOException
{
  private int a;

  public s(int paramInt)
  {
    this.a = paramInt;
  }

  public final String getMessage()
  {
    return Integer.toString(this.a);
  }
}