package oracle.jdbc.xa;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Vector;

public class OracleMultiPhaseArgs
{
  int action = 0;
  int nsites = 0;
  Vector dbLinks = null;
  Vector xids = null;

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  public OracleMultiPhaseArgs()
  {
  }

  public OracleMultiPhaseArgs(int paramInt1, int paramInt2, Vector paramVector1, Vector paramVector2)
  {
    if (paramInt2 <= 1)
    {
      this.action = 0;
      this.nsites = 0;
      this.dbLinks = null;
      this.xids = null;
    }
    else if ((!paramVector1.isEmpty()) && (!paramVector2.isEmpty()) && (paramVector2.size() == paramInt2) && (paramVector1.size() == 3 * paramInt2))
    {
      this.action = paramInt1;
      this.nsites = paramInt2;
      this.xids = paramVector1;
      this.dbLinks = paramVector2;
    }
  }

  public OracleMultiPhaseArgs(byte[] paramArrayOfByte)
  {
    ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(paramArrayOfByte);
    DataInputStream localDataInputStream = new DataInputStream(localByteArrayInputStream);

    this.xids = new Vector();
    this.dbLinks = new Vector();
    try
    {
      this.action = localDataInputStream.readInt();
      this.nsites = localDataInputStream.readInt();

      int i = localDataInputStream.readInt();
      int j = localDataInputStream.readInt();
      byte[] arrayOfByte1 = new byte[j];
      int k = localDataInputStream.read(arrayOfByte1, 0, j);

      for (int m = 0; m < this.nsites; m++)
      {
        int n = localDataInputStream.readInt();
        byte[] arrayOfByte2 = new byte[n];
        int i1 = localDataInputStream.read(arrayOfByte2, 0, n);

        this.xids.addElement(Integer.valueOf(i));
        this.xids.addElement(arrayOfByte1);
        this.xids.addElement(arrayOfByte2);

        String str = localDataInputStream.readUTF();

        this.dbLinks.addElement(str);
      }
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
  }

  public byte[] toByteArray()
  {
    return toByteArrayOS().toByteArray();
  }

  public ByteArrayOutputStream toByteArrayOS()
  {
    Object localObject = null;
    int i = 0;

    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    DataOutputStream localDataOutputStream = new DataOutputStream(localByteArrayOutputStream);
    try
    {
      localDataOutputStream.writeInt(this.action);
      localDataOutputStream.writeInt(this.nsites);

      for (int j = 0; j < this.nsites; j++)
      {
        String str = (String)this.dbLinks.elementAt(j);
        int k = ((Integer)this.xids.elementAt(j * 3)).intValue();
        byte[] arrayOfByte1 = (byte[])this.xids.elementAt(j * 3 + 1);
        byte[] arrayOfByte2 = (byte[])this.xids.elementAt(j * 3 + 2);

        if (j == 0)
        {
          i = k;
          localObject = arrayOfByte1;

          localDataOutputStream.writeInt(k);
          localDataOutputStream.writeInt(arrayOfByte1.length);
          localDataOutputStream.write(arrayOfByte1, 0, arrayOfByte1.length);
        }

        localDataOutputStream.writeInt(arrayOfByte2.length);
        localDataOutputStream.write(arrayOfByte2, 0, arrayOfByte2.length);
        localDataOutputStream.writeUTF(str);
      }
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }

    return localByteArrayOutputStream;
  }

  public int getAction()
  {
    return this.action;
  }

  public int getnsite()
  {
    return this.nsites;
  }

  public Vector getdbLinks()
  {
    return this.dbLinks;
  }

  public Vector getXids()
  {
    return this.xids;
  }

  public void printMPArgs()
  {
    for (int i = 0; i < this.nsites; i++)
    {
      String str = (String)this.dbLinks.elementAt(i);
      int j = ((Integer)this.xids.elementAt(i * 3)).intValue();
      byte[] arrayOfByte1 = (byte[])this.xids.elementAt(i * 3 + 1);
      byte[] arrayOfByte2 = (byte[])this.xids.elementAt(i * 3 + 2);

      printByteArray(arrayOfByte1);

      printByteArray(arrayOfByte2);
    }
  }

  private void printByteArray(byte[] paramArrayOfByte)
  {
    StringBuffer localStringBuffer = new StringBuffer();

    for (int i = 0; i < paramArrayOfByte.length; i++)
      localStringBuffer.append(paramArrayOfByte[i] + " ");
  }
}