package oracle.sql;

import java.sql.SQLException;
import oracle.jdbc.driver.DatabaseError;
import oracle.jdbc.internal.OracleConnection;
import oracle.jdbc.oracore.PickleContext;

class Kotad
{
  static final int KOTADSIG = -1365573631;
  static final int KOTPDSIG = -1365442559;
  static final int KOTRDSIG = -1365377023;
  static final int KOTCDSIG = -1365311487;
  static final int KOTODSIG = -1365307391;
  static final int KOTADXSIG = -1365303295;
  static final int KOTADPRV = 1;
  static final int KOTADPUB = 2;
  static final int KOTADCNT = 4;
  static final int KOTADCFM = 248;
  static final int KOTADSUB = 256;
  static final int KOTADPTR = 16384;
  static final int KOTADREF = 32768;
  static final int KOTADCNN = 65536;
  static final int KOTADCFN = 131072;
  static final int KOTADCVN = 262144;
  static final int KOTADTRN = 512;
  static final int KOTADCPT = 4096;
  static final int KOTADIN = 256;
  static final int KOTADOUT = 512;
  static final int KOTADCBR = 1024;
  static final int KOTADREQ = 2048;
  static final int KOTADNCP = 1048576;
  private int kotadkvn;
  private byte[] kotadnam;
  private byte[] kotadtrf;
  private short kotadtvn;
  private short kotadid;
  private byte[] kotadprf;
  private short kotadpvn;
  private int kotadflg;
  private long kotadpre;
  private int kotadcid;
  private byte kotadscl;
  private int kotadcne;
  private byte[] kotaddft;
  private long kotadtyp;
  private byte[] kotadadd;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  private Kotad()
    throws SQLException
  {
  }

  private static Kotad unpickleKotad(PickleContext paramPickleContext)
    throws SQLException
  {
    Kotad localKotad = new Kotad();
    paramPickleContext.skipBytes(2);
    long l = paramPickleContext.readLength(true) - 2;
    paramPickleContext.skipBytes(1);
    localKotad.kotadkvn = ((int)paramPickleContext.readUB4());
    localKotad.kotadnam = paramPickleContext.readDataValue();
    localKotad.kotadtrf = paramPickleContext.readDataValue();
    paramPickleContext.skipBytes(1);
    localKotad.kotadtvn = ((short)paramPickleContext.readUB2());
    paramPickleContext.skipBytes(1);
    localKotad.kotadid = ((short)paramPickleContext.readUB2());
    localKotad.kotadprf = paramPickleContext.readDataValue();
    paramPickleContext.skipBytes(1);
    localKotad.kotadpvn = ((short)paramPickleContext.readUB2());
    paramPickleContext.skipBytes(1);
    localKotad.kotadflg = ((int)paramPickleContext.readUB4());
    paramPickleContext.skipBytes(1);
    localKotad.kotadpre = paramPickleContext.readUB4();
    paramPickleContext.skipBytes(1);
    localKotad.kotadcid = paramPickleContext.readUB2();
    paramPickleContext.skipBytes(1);
    localKotad.kotadscl = paramPickleContext.readByte();
    paramPickleContext.skipBytes(1);
    localKotad.kotadcne = ((int)paramPickleContext.readUB4());
    localKotad.kotaddft = paramPickleContext.readDataValue();
    paramPickleContext.skipBytes(1);
    localKotad.kotadtyp = paramPickleContext.readUB4();
    localKotad.kotadadd = paramPickleContext.readDataValue();
    return localKotad;
  }

  static final TypeDescriptor unpickleTypeDescriptorImage(PickleContext paramPickleContext)
    throws SQLException
  {
    Kotad localKotad = unpickleKotad(paramPickleContext);
    if (localKotad.kotadkvn != -1365311487)
    {
      SQLException sqlexception = DatabaseError.createSqlException(null, 179);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    TypeDescriptor typedescriptor = constructPredefinedTypeDescriptor(localKotad);
    return typedescriptor;
  }

  static final AttributeDescriptor unpickleAttributeImage(boolean paramBoolean, PickleContext paramPickleContext)
    throws SQLException
  {
    Kotad localKotad = unpickleKotad(paramPickleContext);
    if (localKotad.kotadkvn != -1365573631)
    {
      SQLException sqlexception = DatabaseError.createSqlException(null, 179);
      sqlexception.fillInStackTrace();
      throw sqlexception;
    }

    Object localObject = null;

    if (paramBoolean) {
      localObject = constructPredefinedTypeDescriptor(localKotad);
    }
    AttributeDescriptor localAttributeDescriptor = new AttributeDescriptor(new String(localKotad.kotadnam), localKotad.kotadid, localKotad.kotadflg, (TypeDescriptor)localObject);

    return localAttributeDescriptor;
  }

  private static final TypeDescriptor constructPredefinedTypeDescriptor(Kotad paramKotad)
    throws SQLException
  {
    if (paramKotad.kotadtrf.length != 36)
    {
      SQLException localSQLException = DatabaseError.createSqlException(null, 180);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    for (short s = 4; s < 18; s++)
    {
      if (paramKotad.kotadtrf[s] != 0)
      {
        SQLException sqlexception = DatabaseError.createSqlException(null, 180);
        sqlexception.fillInStackTrace();
        throw sqlexception;
      }
    }

    short s = TypeDescriptor.OID_TO_TYPECODE[paramKotad.kotadtrf[19]];
    TypeDescriptor typedescriptor = new TypeDescriptor(s);
    typedescriptor.setPrecision(paramKotad.kotadpre);
    typedescriptor.setScale(paramKotad.kotadscl);
    return typedescriptor;
  }

  protected OracleConnection getConnectionDuringExceptionHandling()
  {
    return null;
  }
}