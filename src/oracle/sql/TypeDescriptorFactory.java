package oracle.sql;

import java.sql.SQLException;
import oracle.jdbc.driver.DatabaseError;
import oracle.jdbc.internal.OracleConnection;
import oracle.jdbc.oracore.PickleContext;

class TypeDescriptorFactory
  implements ORADataFactory
{
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  public ORAData create(Datum paramDatum, int paramInt)
    throws SQLException
  {
    if (paramDatum == null) return null;

    if (paramInt == 2007)
    {
      OPAQUE localObject = (OPAQUE)paramDatum;
      byte[] arrayOfByte = ((OPAQUE)localObject).getBytesValue();
      short[] arrayOfShort = new short[1];
      TypeDescriptor localTypeDescriptor = TypeDescriptor.unpickleOpaqueTypeImage(new PickleContext(arrayOfByte, 0L), ((OPAQUE)localObject).getPhysicalConnection(), arrayOfShort);

      return localTypeDescriptor;
    }

    SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 1);
    sqlexception.fillInStackTrace();
    throw sqlexception;
  }

  protected OracleConnection getConnectionDuringExceptionHandling()
  {
    return null;
  }
}