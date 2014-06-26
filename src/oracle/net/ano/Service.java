package oracle.net.ano;

import java.io.IOException;
import java.util.Vector;
import oracle.net.ns.NetException;
import oracle.net.ns.SQLnetDef;
import oracle.net.ns.SessionAtts;

public abstract class Service
  implements SQLnetDef
{
  static final String[] a = { "", "SupervisorService", "AuthenticationService", "EncryptionService", "DataIntegrityService" };
  protected Ano b;
  protected AnoComm c;
  protected SessionAtts d;
  protected int e;
  protected int[] f;
  protected byte[] g;
  protected int h;
  protected short i;

  int a(SessionAtts paramSessionAtts)
    throws NetException
  {
    this.d = paramSessionAtts;
    this.b = paramSessionAtts.ano;
    this.c = paramSessionAtts.ano.b;
    this.e = 0;
    this.g = new byte[0];
    return 1;
  }

  final void b(int paramInt)
    throws NetException, IOException
  {
    this.c.c(this.h);
    this.c.c(paramInt);
    this.c.b(0L);
  }

  void b()
    throws NetException, IOException
  {
    b(2);
    this.c.c();
    this.c.a(this.g);
  }

  int c()
  {
    return 12 + this.g.length;
  }

  abstract void a(int paramInt)
    throws NetException, IOException;

  abstract void d()
    throws NetException, IOException;

  void e()
    throws NetException, IOException
  {
  }

  final int f()
  {
    return 8 + c();
  }

  static int[] a(AnoComm paramAnoComm)
    throws NetException, IOException
  {
    int j = paramAnoComm.n();
    int k = paramAnoComm.n();
    int m = (int)paramAnoComm.o();
    int[] arrayOfInt;
    (arrayOfInt = new int[3])[0] = j;
    arrayOfInt[1] = k;
    arrayOfInt[2] = m;
    return arrayOfInt;
  }

  final void c(int paramInt)
    throws NetException, IOException
  {
    a(paramInt);
    d();
  }

  static int[] a(int[] paramArrayOfInt, int paramInt)
    throws NetException
  {
    int[] arrayOfInt = null;
    int j;
    switch (paramInt)
    {
    case 0:
      (arrayOfInt = new int[paramArrayOfInt.length + 1])[0] = 0;
      for (j = 0; j < paramArrayOfInt.length; j++)
        arrayOfInt[(j + 1)] = paramArrayOfInt[j];
      break;
    case 1:
      (arrayOfInt = new int[1])[0] = 0;
      break;
    case 2:
      j = 0;
      arrayOfInt = new int[paramArrayOfInt.length + 1];
      for (j = 0; j < paramArrayOfInt.length; j++)
        arrayOfInt[j] = paramArrayOfInt[j];
      arrayOfInt[j] = 0;
      break;
    case 3:
      arrayOfInt = paramArrayOfInt;
      break;
    default:
      throw new NetException(304);
    }
    return arrayOfInt;
  }

  static String[] a(String[] paramArrayOfString1, String[] paramArrayOfString2)
    throws NetException
  {
    if ((paramArrayOfString1 == null) || (paramArrayOfString1.length == 0))
      if (paramArrayOfString2[0] == "")
      {
        paramArrayOfString1 = new String[paramArrayOfString2.length - 1];
        for (int j = 0; j < paramArrayOfString1.length; j++)
          paramArrayOfString1[j] = paramArrayOfString2[(j + 1)];
      }
      else
      {
        paramArrayOfString1 = paramArrayOfString2;
      }
    Vector localVector = new Vector(10);
    int k;
    for (k = 0; k < paramArrayOfString1.length; k++)
    {
      if (paramArrayOfString1[k].equals(""))
        throw new NetException(303);
      int m;
      for (m = 0; m < paramArrayOfString2.length; m++)
        if (paramArrayOfString2[m].equals(paramArrayOfString1[k]))
        {
          localVector.addElement(paramArrayOfString2[m]);
          break;
        }
      if (m == paramArrayOfString2.length)
        throw new NetException(303);
    }
    String[] arrayOfString = new String[k = localVector.size()];
    for (int m = 0; m < k; m++)
      arrayOfString[m] = ((String)localVector.elementAt(m));
    return arrayOfString;
  }

  static byte a(String[] paramArrayOfString, String paramString)
    throws NetException
  {
    byte b1 = 0;
    if (paramString.equals(paramArrayOfString[b1]))
      return b1;
    throw new NetException(309);
  }

  public static String getLevelString(int paramInt)
    throws NetException
  {
    String str;
    switch (paramInt)
    {
    case 0:
      str = "ACCEPTED";
      break;
    case 1:
      str = "REJECTED";
      break;
    case 2:
      str = "REQUESTED";
      break;
    case 3:
      str = "REQUIRED";
      break;
    default:
      throw new NetException(322);
    }
    return str;
  }

  public static String getServiceName(int paramInt)
    throws NetException
  {
    String str;
    switch (paramInt)
    {
    case 1:
      str = "AUTHENTICATION";
      break;
    case 2:
      str = "ENCRYPTION";
      break;
    case 3:
      str = "DATAINTEGRITY";
      break;
    case 4:
      str = "SUPERVISOR";
      break;
    default:
      throw new NetException(323);
    }
    return str;
  }

  public boolean isActive()
  {
    return false;
  }
}