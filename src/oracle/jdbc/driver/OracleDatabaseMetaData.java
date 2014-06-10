package oracle.jdbc.driver;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.internal.OracleResultSet;

class OracleDatabaseMetaData extends oracle.jdbc.OracleDatabaseMetaData
{
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  public OracleDatabaseMetaData(oracle.jdbc.internal.OracleConnection paramOracleConnection)
  {
    super(paramOracleConnection);
  }

  public OracleDatabaseMetaData(OracleConnection paramOracleConnection)
  {
    this(paramOracleConnection);
  }

  public synchronized ResultSet getColumns(String paramString1, String paramString2, String paramString3, String paramString4)
    throws SQLException
  {
    boolean bool = this.connection.getIncludeSynonyms();
    if ((bool) && (paramString2 != null) && (!hasSqlWildcard(paramString2)) && (paramString3 != null) && (!hasSqlWildcard(paramString3)))
    {
      return getColumnsNoWildcards(stripSqlEscapes(paramString2), stripSqlEscapes(paramString3), paramString4);
    }

    return getColumnsWithWildcards(paramString2, paramString3, paramString4, bool);
  }

  ResultSet getColumnsNoWildcards(String paramString1, String paramString2, String paramString3)
    throws SQLException
  {
    String str = getColumnsNoWildcardsPlsql();
    CallableStatement localCallableStatement = this.connection.prepareCall(str);
    localCallableStatement.setString(1, paramString1);
    localCallableStatement.setString(2, paramString2);
    localCallableStatement.setString(3, paramString3 == null ? "%" : paramString3);
    localCallableStatement.registerOutParameter(4, -10);
    localCallableStatement.execute();
    ResultSet localResultSet = ((OracleCallableStatement)localCallableStatement).getCursor(4);
    ((OracleResultSet)localResultSet).closeStatementOnClose();
    return localResultSet;
  }

  ResultSet getColumnsWithWildcards(String paramString1, String paramString2, String paramString3, boolean paramBoolean)
    throws SQLException
  {
    int i = this.connection.getVersionNumber();

    String str1 = "SELECT ";
    String str2 = " NULL AS table_cat,\n";

    String str3 = "";
    if (((i >= 10200 ? 1 : 0) & (i < 11100 ? 1 : 0) & paramBoolean) != 0) str3 = "/*+ CHOOSE */";

    String str4 = "       t.owner AS table_schem,\n       t.table_name AS table_name,\n";

    String str5 = "       DECODE(s.owner, NULL, t.owner, s.owner)\n              AS table_schem,\n       DECODE(s.synonym_name, NULL, t.table_name, s.synonym_name)\n              AS table_name,\n";

    String str6 = "         DECODE (t.data_type, 'CHAR', t.char_length,                   'VARCHAR', t.char_length,                   'VARCHAR2', t.char_length,                   'NVARCHAR2', t.char_length,                   'NCHAR', t.char_length,                   'NUMBER', 0,           t.data_length),";

    String str7 = new StringBuilder().append("       t.column_name AS column_name,\n       DECODE (t.data_type, 'CHAR', 1, 'VARCHAR2', 12, 'NUMBER', 3,\n               'LONG', -1, 'DATE', ").append(((PhysicalConnection)this.connection).mapDateToTimestamp ? "93" : "91").append(", 'RAW', -3, 'LONG RAW', -4,  \n").append("               'BLOB', 2004, 'CLOB', 2005, 'BFILE', -13, 'FLOAT', 6, \n").append("               'TIMESTAMP(6)', 93, 'TIMESTAMP(6) WITH TIME ZONE', -101, \n").append("               'TIMESTAMP(6) WITH LOCAL TIME ZONE', -102, \n").append("               'INTERVAL YEAR(2) TO MONTH', -103, \n").append("               'INTERVAL DAY(2) TO SECOND(6)', -104, \n").append("               'BINARY_FLOAT', 100, 'BINARY_DOUBLE', 101, \n").append("               'XMLTYPE', 2007, \n").append("               1111)\n").append("              AS data_type,\n").append("       t.data_type AS type_name,\n").append("       DECODE (t.data_precision, null, ").append(i > 9000 ? str6 : "t.data_length,").append("         t.data_precision)\n").append("              AS column_size,\n").append("       0 AS buffer_length,\n").append("       DECODE (t.data_type, ").append("               'NUMBER', DECODE (t.data_precision, ").append("                                 null, -127, ").append("                                 t.data_scale), ").append("               t.data_scale) AS decimal_digits,\n").append("       10 AS num_prec_radix,\n").append("       DECODE (t.nullable, 'N', 0, 1) AS nullable,\n").toString();

    String str8 = "       c.comments AS remarks,\n";

    String str9 = "       NULL AS remarks,\n";

    String str10 = "       t.data_default AS column_def,\n       0 AS sql_data_type,\n       0 AS sql_datetime_sub,\n       t.data_length AS char_octet_length,\n       t.column_id AS ordinal_position,\n       DECODE (t.nullable, 'N', 'NO', 'YES') AS is_nullable\n";

    String str11 = "FROM all_tab_columns t";

    String str12 = ", all_synonyms s";
    String str13 = ", all_col_comments c";

    String str14 = "WHERE t.owner LIKE :1 ESCAPE '/'\n  AND t.table_name LIKE :2 ESCAPE '/'\n  AND t.column_name LIKE :3 ESCAPE '/'\n";

    String str15 = "WHERE (t.owner LIKE :4 ESCAPE '/' OR\n       (s.owner LIKE :5 ESCAPE '/' AND t.owner = s.table_owner))\n  AND (t.table_name LIKE :6 ESCAPE '/' OR\n       s.synonym_name LIKE :7 ESCAPE '/')\n  AND t.column_name LIKE :8 ESCAPE '/'\n";

    String str16 = "  AND t.owner = c.owner (+)\n  AND t.table_name = c.table_name (+)\n  AND t.column_name = c.column_name (+)\n";

    String str17 = "  AND s.table_name (+) = t.table_name\n  AND ((DECODE(s.owner, t.owner, 'OK',\n                       'PUBLIC', 'OK',\n                       NULL, 'OK',\n                       'NOT OK') = 'OK') OR\n       (t.owner LIKE :9 AND t.owner = s.table_owner) OR\n       (s.owner LIKE :10 AND t.owner = s.table_owner))";

    String str18 = "ORDER BY table_schem, table_name, ordinal_position\n";

    String str19 = new StringBuilder().append(str1).append(str3).append(str2).toString();

    if (paramBoolean)
      str19 = new StringBuilder().append(str19).append(str5).toString();
    else {
      str19 = new StringBuilder().append(str19).append(str4).toString();
    }
    str19 = new StringBuilder().append(str19).append(str7).toString();

    if (this.connection.getRemarksReporting())
      str19 = new StringBuilder().append(str19).append(str8).toString();
    else {
      str19 = new StringBuilder().append(str19).append(str9).toString();
    }
    str19 = new StringBuilder().append(str19).append(str10).append(str11).toString();

    if (this.connection.getRemarksReporting()) {
      str19 = new StringBuilder().append(str19).append(str13).toString();
    }
    if (paramBoolean) {
      str19 = new StringBuilder().append(str19).append(str12).toString();
    }
    if (paramBoolean)
      str19 = new StringBuilder().append(str19).append("\n").append(str15).toString();
    else {
      str19 = new StringBuilder().append(str19).append("\n").append(str14).toString();
    }
    if (this.connection.getRemarksReporting()) {
      str19 = new StringBuilder().append(str19).append(str16).toString();
    }
    if (this.connection.getIncludeSynonyms()) {
      str19 = new StringBuilder().append(str19).append(str17).toString();
    }
    str19 = new StringBuilder().append(str19).append("\n").append(str18).toString();

    PreparedStatement localPreparedStatement = this.connection.prepareStatement(str19);

    if (paramBoolean)
    {
      localPreparedStatement.setString(1, paramString1 == null ? "%" : paramString1);
      localPreparedStatement.setString(2, paramString1 == null ? "%" : paramString1);
      localPreparedStatement.setString(3, paramString2 == null ? "%" : paramString2);
      localPreparedStatement.setString(4, paramString2 == null ? "%" : paramString2);
      localPreparedStatement.setString(5, paramString3 == null ? "%" : paramString3);
      localPreparedStatement.setString(6, paramString1 == null ? "%" : paramString1);
      localPreparedStatement.setString(7, paramString1 == null ? "%" : paramString1);
    }
    else
    {
      localPreparedStatement.setString(1, paramString1 == null ? "%" : paramString1);
      localPreparedStatement.setString(2, paramString2 == null ? "%" : paramString2);
      localPreparedStatement.setString(3, paramString3 == null ? "%" : paramString3);
    }

    OracleResultSet localOracleResultSet = (OracleResultSet)localPreparedStatement.executeQuery();

    localOracleResultSet.closeStatementOnClose();

    return localOracleResultSet;
  }

  public ResultSet getTypeInfo()
    throws SQLException
  {
    Statement localStatement = this.connection.createStatement();
    int i = this.connection.getVersionNumber();

    String str1 = new StringBuilder().append("union select\n 'CHAR' as type_name, 1 as data_type, ").append(i >= 8100 ? 2000 : 255).append(" as precision,\n").append(" '''' as literal_prefix, '''' as literal_suffix, NULL as create_params,\n").append(" 1 as nullable, 1 as case_sensitive, 3 as searchable,\n").append(" 0 as unsigned_attribute, 0 as fixed_prec_scale, 0 as auto_increment,\n").append(" 'CHAR' as local_type_name, 0 as minimum_scale, 0 as maximum_scale,\n").append(" NULL as sql_data_type, NULL as sql_datetime_sub, 10 as num_prec_radix\n").append("from dual\n").toString();

    String str2 = new StringBuilder().append("union select\n 'VARCHAR2' as type_name, 12 as data_type, ").append(i >= 8100 ? 4000 : 2000).append(" as precision,\n").append(" '''' as literal_prefix, '''' as literal_suffix, NULL as create_params,\n").append(" 1 as nullable, 1 as case_sensitive, 3 as searchable,\n").append(" 0 as unsigned_attribute, 0 as fixed_prec_scale, 0 as auto_increment,\n").append(" 'VARCHAR2' as local_type_name, 0 as minimum_scale, 0 as maximum_scale,\n").append(" NULL as sql_data_type, NULL as sql_datetime_sub, 10 as num_prec_radix\n").append("from dual\n").toString();

    String str3 = new StringBuilder().append("union select\n 'DATE' as type_name, ").append(((PhysicalConnection)this.connection).mapDateToTimestamp ? "93" : "91").append("as data_type, 7 as precision,\n").append(" NULL as literal_prefix, NULL as literal_suffix, NULL as create_params,\n").append(" 1 as nullable, 0 as case_sensitive, 3 as searchable,\n").append(" 0 as unsigned_attribute, 0 as fixed_prec_scale, 0 as auto_increment,\n").append(" 'DATE' as local_type_name, 0 as minimum_scale, 0 as maximum_scale,\n").append(" NULL as sql_data_type, NULL as sql_datetime_sub, 10 as num_prec_radix\n").append("from dual\n").toString();

    String str4 = new StringBuilder().append("union select\n 'RAW' as type_name, -3 as data_type, ").append(i >= 8100 ? 2000 : 255).append(" as precision,\n").append(" '''' as literal_prefix, '''' as literal_suffix, NULL as create_params,\n").append(" 1 as nullable, 0 as case_sensitive, 3 as searchable,\n").append(" 0 as unsigned_attribute, 0 as fixed_prec_scale, 0 as auto_increment,\n").append(" 'RAW' as local_type_name, 0 as minimum_scale, 0 as maximum_scale,\n").append(" NULL as sql_data_type, NULL as sql_datetime_sub, 10 as num_prec_radix\n").append("from dual\n").toString();

    String str5 = "-1";

    String str6 = new StringBuilder().append("union select\n 'BLOB' as type_name, 2004 as data_type, ").append(str5).append(" as precision,\n").append(" null as literal_prefix, null as literal_suffix, NULL as create_params,\n").append(" 1 as nullable, 0 as case_sensitive, 0 as searchable,\n").append(" 0 as unsigned_attribute, 0 as fixed_prec_scale, 0 as auto_increment,\n").append(" 'BLOB' as local_type_name, 0 as minimum_scale, 0 as maximum_scale,\n").append(" NULL as sql_data_type, NULL as sql_datetime_sub, 10 as num_prec_radix\n").append("from dual\n").toString();

    String str7 = new StringBuilder().append("union select\n 'CLOB' as type_name, 2005 as data_type, ").append(str5).append(" as precision,\n").append(" '''' as literal_prefix, '''' as literal_suffix, NULL as create_params,\n").append(" 1 as nullable, 1 as case_sensitive, 0 as searchable,\n").append(" 0 as unsigned_attribute, 0 as fixed_prec_scale, 0 as auto_increment,\n").append(" 'CLOB' as local_type_name, 0 as minimum_scale, 0 as maximum_scale,\n").append(" NULL as sql_data_type, NULL as sql_datetime_sub, 10 as num_prec_radix\n").append("from dual\n").toString();

    String str8 = new StringBuilder().append("select\n 'NUMBER' as type_name, 2 as data_type, 38 as precision,\n NULL as literal_prefix, NULL as literal_suffix, NULL as create_params,\n 1 as nullable, 0 as case_sensitive, 3 as searchable,\n 0 as unsigned_attribute, 1 as fixed_prec_scale, 0 as auto_increment,\n 'NUMBER' as local_type_name, -84 as minimum_scale, 127 as maximum_scale,\n NULL as sql_data_type, NULL as sql_datetime_sub, 10 as num_prec_radix\nfrom dual\n").append(str1).append(str2).append(str3).append("union select\n 'DATE' as type_name, 92 as data_type, 7 as precision,\n NULL as literal_prefix, NULL as literal_suffix, NULL as create_params,\n 1 as nullable, 0 as case_sensitive, 3 as searchable,\n 0 as unsigned_attribute, 0 as fixed_prec_scale, 0 as auto_increment,\n 'DATE' as local_type_name, 0 as minimum_scale, 0 as maximum_scale,\n NULL as sql_data_type, NULL as sql_datetime_sub, 10 as num_prec_radix\nfrom dual\n").append("union select\n 'TIMESTAMP' as type_name, 93 as data_type, 11 as precision,\n NULL as literal_prefix, NULL as literal_suffix, NULL as create_params,\n 1 as nullable, 0 as case_sensitive, 3 as searchable,\n 0 as unsigned_attribute, 0 as fixed_prec_scale, 0 as auto_increment,\n 'TIMESTAMP' as local_type_name, 0 as minimum_scale, 0 as maximum_scale,\n NULL as sql_data_type, NULL as sql_datetime_sub, 10 as num_prec_radix\nfrom dual\n").append("union select\n 'TIMESTAMP WITH TIME ZONE' as type_name, -101 as data_type, 13 as precision,\n NULL as literal_prefix, NULL as literal_suffix, NULL as create_params,\n 1 as nullable, 0 as case_sensitive, 3 as searchable,\n 0 as unsigned_attribute, 0 as fixed_prec_scale, 0 as auto_increment,\n 'TIMESTAMP WITH TIME ZONE' as local_type_name, 0 as minimum_scale, 0 as maximum_scale,\n NULL as sql_data_type, NULL as sql_datetime_sub, 10 as num_prec_radix\nfrom dual\n").append("union select\n 'TIMESTAMP WITH LOCAL TIME ZONE' as type_name, -102 as data_type, 11 as precision,\n NULL as literal_prefix, NULL as literal_suffix, NULL as create_params,\n 1 as nullable, 0 as case_sensitive, 3 as searchable,\n 0 as unsigned_attribute, 0 as fixed_prec_scale, 0 as auto_increment,\n 'TIMESTAMP WITH LOCAL TIME ZONE' as local_type_name, 0 as minimum_scale, 0 as maximum_scale,\n NULL as sql_data_type, NULL as sql_datetime_sub, 10 as num_prec_radix\nfrom dual\n").append("union select\n 'INTERVALYM' as type_name, -103 as data_type, 5 as precision,\n NULL as literal_prefix, NULL as literal_suffix, NULL as create_params,\n 1 as nullable, 0 as case_sensitive, 3 as searchable,\n 0 as unsigned_attribute, 0 as fixed_prec_scale, 0 as auto_increment,\n 'INTERVALYM' as local_type_name, 0 as minimum_scale, 0 as maximum_scale,\n NULL as sql_data_type, NULL as sql_datetime_sub, 10 as num_prec_radix\nfrom dual\n").append("union select\n 'INTERVALDS' as type_name, -104 as data_type, 4 as precision,\n NULL as literal_prefix, NULL as literal_suffix, NULL as create_params,\n 1 as nullable, 0 as case_sensitive, 3 as searchable,\n 0 as unsigned_attribute, 0 as fixed_prec_scale, 0 as auto_increment,\n 'INTERVALDS' as local_type_name, 0 as minimum_scale, 0 as maximum_scale,\n NULL as sql_data_type, NULL as sql_datetime_sub, 10 as num_prec_radix\nfrom dual\n").append(str4).append("union select\n 'LONG' as type_name, -1 as data_type, 2147483647 as precision,\n '''' as literal_prefix, '''' as literal_suffix, NULL as create_params,\n 1 as nullable, 1 as case_sensitive, 0 as searchable,\n 0 as unsigned_attribute, 0 as fixed_prec_scale, 0 as auto_increment,\n 'LONG' as local_type_name, 0 as minimum_scale, 0 as maximum_scale,\n NULL as sql_data_type, NULL as sql_datetime_sub, 10 as num_prec_radix\nfrom dual\n").append("union select\n 'LONG RAW' as type_name, -4 as data_type, 2147483647 as precision,\n '''' as literal_prefix, '''' as literal_suffix, NULL as create_params,\n 1 as nullable, 0 as case_sensitive, 0 as searchable,\n 0 as unsigned_attribute, 0 as fixed_prec_scale, 0 as auto_increment,\n 'LONG RAW' as local_type_name, 0 as minimum_scale, 0 as maximum_scale,\n NULL as sql_data_type, NULL as sql_datetime_sub, 10 as num_prec_radix\nfrom dual\n").append("union select 'NUMBER' as type_name, -7 as data_type, 1 as precision,\nNULL as literal_prefix, NULL as literal_suffix, \n'(1)' as create_params, 1 as nullable, 0 as case_sensitive, 3 as searchable,\n0 as unsigned_attribute, 1 as fixed_prec_scale, 0 as auto_increment,\n'NUMBER' as local_type_name, -84 as minimum_scale, 127 as maximum_scale,\nNULL as sql_data_type, NULL as sql_datetime_sub, 10 as num_prec_radix\nfrom dual\n").append("union select 'NUMBER' as type_name, -6 as data_type, 3 as precision,\nNULL as literal_prefix, NULL as literal_suffix, \n'(3)' as create_params, 1 as nullable, 0 as case_sensitive, 3 as searchable,\n0 as unsigned_attribute, 1 as fixed_prec_scale, 0 as auto_increment,\n'NUMBER' as local_type_name, -84 as minimum_scale, 127 as maximum_scale,\nNULL as sql_data_type, NULL as sql_datetime_sub, 10 as num_prec_radix\nfrom dual\n").append("union select 'NUMBER' as type_name, 5 as data_type, 5 as precision,\nNULL as literal_prefix, NULL as literal_suffix, \n'(5)' as create_params, 1 as nullable, 0 as case_sensitive, 3 as searchable,\n0 as unsigned_attribute, 1 as fixed_prec_scale, 0 as auto_increment,\n'NUMBER' as local_type_name, -84 as minimum_scale, 127 as maximum_scale,\nNULL as sql_data_type, NULL as sql_datetime_sub, 10 as num_prec_radix\nfrom dual\n").append("union select 'NUMBER' as type_name, 4 as data_type, 10 as precision,\nNULL as literal_prefix, NULL as literal_suffix, \n'(10)' as create_params, 1 as nullable, 0 as case_sensitive, 3 as searchable,\n0 as unsigned_attribute, 1 as fixed_prec_scale, 0 as auto_increment,\n'NUMBER' as local_type_name, -84 as minimum_scale, 127 as maximum_scale,\nNULL as sql_data_type, NULL as sql_datetime_sub, 10 as num_prec_radix\nfrom dual\n").append("union select 'NUMBER' as type_name, -5 as data_type, 38 as precision,\nNULL as literal_prefix, NULL as literal_suffix, \nNULL as create_params, 1 as nullable, 0 as case_sensitive, 3 as searchable,\n0 as unsigned_attribute, 1 as fixed_prec_scale, 0 as auto_increment,\n'NUMBER' as local_type_name, -84 as minimum_scale, 127 as maximum_scale,\nNULL as sql_data_type, NULL as sql_datetime_sub, 10 as num_prec_radix\nfrom dual\n").append("union select 'FLOAT' as type_name, 6 as data_type, 63 as precision,\nNULL as literal_prefix, NULL as literal_suffix, \nNULL as create_params, 1 as nullable, 0 as case_sensitive, 3 as searchable,\n0 as unsigned_attribute, 1 as fixed_prec_scale, 0 as auto_increment,\n'FLOAT' as local_type_name, -84 as minimum_scale, 127 as maximum_scale,\nNULL as sql_data_type, NULL as sql_datetime_sub, 10 as num_prec_radix\nfrom dual\n").append("union select 'REAL' as type_name, 7 as data_type, 63 as precision,\nNULL as literal_prefix, NULL as literal_suffix, \nNULL as create_params, 1 as nullable, 0 as case_sensitive, 3 as searchable,\n0 as unsigned_attribute, 1 as fixed_prec_scale, 0 as auto_increment,\n'REAL' as local_type_name, -84 as minimum_scale, 127 as maximum_scale,\nNULL as sql_data_type, NULL as sql_datetime_sub, 10 as num_prec_radix\nfrom dual\n").append(i >= 8100 ? new StringBuilder().append(str6).append(str7).append("union select\n 'REF' as type_name, 2006 as data_type, 0 as precision,\n '''' as literal_prefix, '''' as literal_suffix, NULL as create_params,\n 1 as nullable, 1 as case_sensitive, 0 as searchable,\n 0 as unsigned_attribute, 0 as fixed_prec_scale, 0 as auto_increment,\n 'REF' as local_type_name, 0 as minimum_scale, 0 as maximum_scale,\n NULL as sql_data_type, NULL as sql_datetime_sub, 10 as num_prec_radix\nfrom dual\n").append("union select\n 'ARRAY' as type_name, 2003 as data_type, 0 as precision,\n '''' as literal_prefix, '''' as literal_suffix, NULL as create_params,\n 1 as nullable, 1 as case_sensitive, 0 as searchable,\n 0 as unsigned_attribute, 0 as fixed_prec_scale, 0 as auto_increment,\n 'ARRAY' as local_type_name, 0 as minimum_scale, 0 as maximum_scale,\n NULL as sql_data_type, NULL as sql_datetime_sub, 10 as num_prec_radix\nfrom dual\n").append("union select\n 'STRUCT' as type_name, 2002 as data_type, 0 as precision,\n '''' as literal_prefix, '''' as literal_suffix, NULL as create_params,\n 1 as nullable, 1 as case_sensitive, 0 as searchable,\n 0 as unsigned_attribute, 0 as fixed_prec_scale, 0 as auto_increment,\n 'STRUCT' as local_type_name, 0 as minimum_scale, 0 as maximum_scale,\n NULL as sql_data_type, NULL as sql_datetime_sub, 10 as num_prec_radix\nfrom dual\n").toString() : "").append("order by data_type\n").toString();

    OracleResultSet localOracleResultSet = (OracleResultSet)localStatement.executeQuery(str8);

    localOracleResultSet.closeStatementOnClose();

    return localOracleResultSet;
  }

  String getColumnsNoWildcardsPlsql()
    throws SQLException
  {
    String str1 = "declare\n  in_owner varchar2(32) := null;\n  in_name varchar2(32) := null;\n  my_user_name varchar2(32) := null;\n  cnt number := 0;\n  out_owner varchar2(32) := null;\n  out_name  varchar2(32):= null;\n  loc varchar2(32) := null;\n  xxx SYS_REFCURSOR;\nbegin\n  in_owner := ?;\n  in_name := ?;\n  select user into my_user_name from dual;\n  if( my_user_name = in_owner ) then\n    select count(*) into cnt from user_tables where table_name = in_name;\n    if( cnt = 1 ) then\n      out_owner := in_owner;\n      out_name := in_name;\n      loc := 'USER_TABLES';\n    else\n      begin\n        select table_owner, table_name into out_owner, out_name from user_synonyms where synonym_name = in_name;\n      exception\n        when NO_DATA_FOUND then\n        out_owner := null;\n        out_name := null;\n      end;\n      if( not(out_name is null) ) then\n        loc := 'USER_SYNONYMS';\n      end if;\n    end if;\n  else\n    select count(*) into cnt from all_tables where owner = in_owner and table_name = in_name;\n    if( cnt = 1 ) then\n      out_owner := in_owner;\n      out_name := in_name;\n      loc := 'ALL_TABLES';\n    else\n      begin\n        select table_owner, table_name into out_owner, out_name from all_synonyms \n          where  owner = in_owner and synonym_name = in_name;\n      exception\n        when NO_DATA_FOUND then\n          out_owner := null;\n          out_name := null;\n      end;\n      if( not(out_owner is null) ) then\n        loc := 'ALL_SYNONYMS';\n      end if;\n    end if;\n  end if;\n";

    int i = this.connection.getVersionNumber();
    String str2 = "open xxx for SELECT NULL AS table_cat,\n";

    String str3 = "       in_owner AS table_schem,\n       in_name AS table_name,\n";

    String str4 = "         DECODE (t.data_type, 'CHAR', t.char_length,                   'VARCHAR', t.char_length,                   'VARCHAR2', t.char_length,                   'NVARCHAR2', t.char_length,                   'NCHAR', t.char_length,                   'NUMBER', 0,           t.data_length),";

    String str5 = new StringBuilder().append("       t.column_name AS column_name,\n       DECODE (t.data_type, 'CHAR', 1, 'VARCHAR2', 12, 'NUMBER', 3,\n               'LONG', -1, 'DATE', ").append(this.connection.getMapDateToTimestamp() ? "93" : "91").append(", 'RAW', -3, 'LONG RAW', -4,  \n").append("               'BLOB', 2004, 'CLOB', 2005, 'BFILE', -13, 'FLOAT', 6, \n").append("               'TIMESTAMP(6)', 93, 'TIMESTAMP(6) WITH TIME ZONE', -101, \n").append("               'TIMESTAMP(6) WITH LOCAL TIME ZONE', -102, \n").append("               'INTERVAL YEAR(2) TO MONTH', -103, \n").append("               'INTERVAL DAY(2) TO SECOND(6)', -104, \n").append("               'BINARY_FLOAT', 100, 'BINARY_DOUBLE', 101, \n").append("               'XMLTYPE', 2007, \n").append("               1111)\n").append("              AS data_type,\n").append("       t.data_type AS type_name,\n").append("       DECODE (t.data_precision, null, ").append(i > 9000 ? str4 : "t.data_length,").append("         t.data_precision)\n").append("              AS column_size,\n").append("       0 AS buffer_length,\n").append("       DECODE (t.data_type, ").append("               'NUMBER', DECODE (t.data_precision, ").append("                                 null, -127, ").append("                                 t.data_scale), ").append("               t.data_scale) AS decimal_digits,\n").append("       10 AS num_prec_radix,\n").append("       DECODE (t.nullable, 'N', 0, 1) AS nullable,\n").toString();

    String str6 = "       c.comments AS remarks,\n";

    String str7 = "       NULL AS remarks,\n";

    String str8 = "       t.data_default AS column_def,\n       0 AS sql_data_type,\n       0 AS sql_datetime_sub,\n       t.data_length AS char_octet_length,\n       t.column_id AS ordinal_position,\n       DECODE (t.nullable, 'N', 'NO', 'YES') AS is_nullable\n";

    String str9 = "FROM all_tab_columns t";

    String str10 = ", all_col_comments c";

    String str11 = "WHERE t.owner = out_owner \n  AND t.table_name = out_name\n AND t.column_name LIKE ? ESCAPE '/'\n";

    String str12 = "  AND t.owner = c.owner (+)\n  AND t.table_name = c.table_name (+)\n  AND t.column_name = c.column_name (+)\n";

    String str13 = "ORDER BY table_schem, table_name, ordinal_position\n";

    String str14 = str2;

    str14 = new StringBuilder().append(str14).append(str3).toString();

    str14 = new StringBuilder().append(str14).append(str5).toString();

    if (this.connection.getRemarksReporting())
      str14 = new StringBuilder().append(str14).append(str6).toString();
    else {
      str14 = new StringBuilder().append(str14).append(str7).toString();
    }
    str14 = new StringBuilder().append(str14).append(str8).append(str9).toString();

    if (this.connection.getRemarksReporting()) {
      str14 = new StringBuilder().append(str14).append(str10).toString();
    }
    str14 = new StringBuilder().append(str14).append("\n").append(str11).toString();

    if (this.connection.getRemarksReporting()) {
      str14 = new StringBuilder().append(str14).append(str12).toString();
    }

    str14 = new StringBuilder().append(str14).append("\n").append(str13).toString();

    String str15 = "; \n ? := xxx;\n end;";

    String str16 = new StringBuilder().append(str1).append(str14).append(str15).toString();
    return str16;
  }
}