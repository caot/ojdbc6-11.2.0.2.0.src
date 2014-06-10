package oracle.sql;

import java.sql.SQLException;
import java.util.Map;
import oracle.jdbc.internal.OracleConnection;

public class SQLUtil
{
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  public static Object SQLToJava(OracleConnection paramOracleConnection, byte[] paramArrayOfByte, int paramInt, String paramString, Class paramClass, Map paramMap)
    throws SQLException
  {
    return oracle.jdbc.driver.SQLUtil.SQLToJava(paramOracleConnection, paramArrayOfByte, paramInt, paramString, paramClass, paramMap);
  }

  public static CustomDatum SQLToJava(OracleConnection paramOracleConnection, byte[] paramArrayOfByte, int paramInt, String paramString, CustomDatumFactory paramCustomDatumFactory)
    throws SQLException
  {
    return oracle.jdbc.driver.SQLUtil.SQLToJava(paramOracleConnection, paramArrayOfByte, paramInt, paramString, paramCustomDatumFactory);
  }

  public static ORAData SQLToJava(OracleConnection paramOracleConnection, byte[] paramArrayOfByte, int paramInt, String paramString, ORADataFactory paramORADataFactory)
    throws SQLException
  {
    return oracle.jdbc.driver.SQLUtil.SQLToJava(paramOracleConnection, paramArrayOfByte, paramInt, paramString, paramORADataFactory);
  }

  public static Object SQLToJava(OracleConnection paramOracleConnection, Datum paramDatum, Class paramClass, Map paramMap)
    throws SQLException
  {
    return oracle.jdbc.driver.SQLUtil.SQLToJava(paramOracleConnection, paramDatum, paramClass, paramMap);
  }

  public static byte[] JavaToSQL(OracleConnection paramOracleConnection, Object paramObject, int paramInt, String paramString)
    throws SQLException
  {
    return oracle.jdbc.driver.SQLUtil.JavaToSQL(paramOracleConnection, paramObject, paramInt, paramString);
  }

  public static Datum makeDatum(OracleConnection paramOracleConnection, byte[] paramArrayOfByte, int paramInt1, String paramString, int paramInt2)
    throws SQLException
  {
    return oracle.jdbc.driver.SQLUtil.makeDatum(paramOracleConnection, paramArrayOfByte, paramInt1, paramString, paramInt2);
  }

  public static Datum makeDatum(OracleConnection paramOracleConnection, Object paramObject, int paramInt, String paramString)
    throws SQLException
  {
    return oracle.jdbc.driver.SQLUtil.makeDatum(paramOracleConnection, paramObject, paramInt, paramString);
  }

  public static Object getTypeDescriptor(String paramString, OracleConnection paramOracleConnection)
    throws SQLException
  {
    return oracle.jdbc.driver.SQLUtil.getTypeDescriptor(paramString, paramOracleConnection);
  }

  public static boolean checkDatumType(Datum paramDatum, int paramInt, String paramString)
    throws SQLException
  {
    return oracle.jdbc.driver.SQLUtil.checkDatumType(paramDatum, paramInt, paramString);
  }

  public static boolean implementsInterface(Class paramClass1, Class paramClass2)
  {
    return oracle.jdbc.driver.SQLUtil.implementsInterface(paramClass1, paramClass2);
  }

  public static Datum makeOracleDatum(OracleConnection paramOracleConnection, Object paramObject, int paramInt, String paramString)
    throws SQLException
  {
    return oracle.jdbc.driver.SQLUtil.makeOracleDatum(paramOracleConnection, paramObject, paramInt, paramString);
  }

  public static int getInternalType(int paramInt)
    throws SQLException
  {
    return oracle.jdbc.driver.SQLUtil.getInternalType(paramInt);
  }

  /** @deprecated */
  public static int get_internal_type(int paramInt)
    throws SQLException
  {
    return getInternalType(paramInt);
  }
}