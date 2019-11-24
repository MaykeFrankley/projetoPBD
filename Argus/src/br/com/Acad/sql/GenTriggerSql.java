package br.com.Acad.sql;
import java.sql.*;

public class GenTriggerSql {

	private static String HISTORY_TABLE = "LogSistema";
	private static String HISTORY_TABLE_COLUMN = "alteracao";
	private static Connection con = null;

	public GenTriggerSql() {
		try {

			con = ConnectionReserva.createConnection();

			DatabaseMetaData md = con.getMetaData();
			String[] types = {"TABLE"};
			ResultSet rst = md.getTables("argus", null, "%", types);

			Statement stmt;
			stmt = con.createStatement();
			stmt.execute(genCreateHistoryTable());//Create history table
			while (rst.next()) {
				if (rst.getString("TABLE_NAME").equals(HISTORY_TABLE_COLUMN))
					continue;
				stmt.execute(genDropTriggerSql("`"+rst.getString("TABLE_NAME") + "_u`"));//Delete 'update' trigger
				stmt.execute(genDropTriggerSql("`"+rst.getString("TABLE_NAME") + "_d`"));//Delete 'delete' trigger
				stmt.execute(genDropTriggerSql("`"+rst.getString("TABLE_NAME") + "_i`"));//Delete 'insert' trigger
				genTrigger(stmt, rst.getString("TABLE_NAME"));//Create trigger
			}

		} catch (Exception e) {
			System.out.print("MYSQL ERROR:" + e.getMessage());
		}

	}

	/**
	 * generate 'create table' sql
	 *
	 * @return
	 */
	private static String genCreateHistoryTable() {
		String sqlExe =
				"CREATE TABLE IF NOT EXISTS " + HISTORY_TABLE + "(id BIGINT PRIMARY KEY AUTO_INCREMENT,"
						+ " usuario VARCHAR(100) NOT NULL,"
						+ " data TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,"
						+ " tipo_alteracao VARCHAR(50) NOT NULL,"
						+ " tabela VARCHAR(100) NOT NULL,"
						+ " " + HISTORY_TABLE_COLUMN + " VARCHAR(5000));";
		System.out.println(sqlExe);
		return sqlExe;
	}

	/**
	 * Create Triggers
	 *
	 * @param stmt
	 * @param tableName
	 * @throws SQLException
	 */
	private static void genTrigger(Statement stmt, String tableName) throws SQLException {
		ResultSet rs = stmt.executeQuery("SELECT * FROM " + "`"+tableName+"`");
		ResultSetMetaData rsmd = rs.getMetaData();
		String sqlUExe = genUTriggerSql(tableName, rsmd);
		System.out.print(sqlUExe);
		stmt.execute(sqlUExe);
		String sqlDExe = genDTriggerSql(tableName, rsmd);
		System.out.print(sqlDExe);
		stmt.execute(sqlDExe);

		String sqlIExe = genINTriggerSql(tableName, rsmd);
		System.out.print(sqlIExe);
		stmt.execute(sqlIExe);
	}

	private static String genDropTriggerSql(String triggerName) {
		String sqlExe = "DROP TRIGGER IF EXISTS " + triggerName + ";";
		System.out.println(sqlExe);
		return sqlExe;
	}

	/**
	 * Generate 'update trigger' sql
	 *
	 * @param originTable
	 * @param rsmd
	 * @return
	 * @throws SQLException
	 */
	private static String genUTriggerSql(String originTable, ResultSetMetaData rsmd) throws SQLException {
		String sqlexe = "CREATE TRIGGER " + "`"+originTable + "_u` AFTER UPDATE ON " + "`"+originTable+"`" + " FOR EACH ROW \n" +
				"BEGIN \n" +
				"IF ";
		for (int i = 1; i <= rsmd.getColumnCount(); i++) {
			sqlexe += genExeConditionSql(rsmd.getColumnName(i)) + "OR ";
		}

		sqlexe += "false THEN \n" +
				"INSERT INTO " + HISTORY_TABLE + " (id, usuario, tipo_alteracao, tabela, "+ HISTORY_TABLE_COLUMN + ") \n" +
				"VALUES(id, USER(), 'UPDATE', '"+originTable+"', " +
				"CONCAT('OLD::', ";


		for (int i = 1; i <= rsmd.getColumnCount(); i++) {
			sqlexe += getRow(rsmd.getColumnName(i)) + ",";
		}

		for (int i = 1; i <= rsmd.getColumnCount(); i++) {

			sqlexe += genItemUpdateSql(rsmd.getColumnName(i)) + ",";
		}
		sqlexe += "'') );\n" +
				"END IF;\n" +
				"END;\n";

		return sqlexe;
	}

	/**
	 * Generate 'delete trigger' sql
	 * @param originTable
	 * @param rsmd
	 * @return
	 * @throws SQLException
	 */
	private static String genDTriggerSql(String originTable, ResultSetMetaData rsmd) throws SQLException {
		String sqlexe = "CREATE TRIGGER " + "`"+originTable + "_d` BEFORE DELETE ON " + "`"+originTable+"`" + " FOR EACH ROW \n" +
				"BEGIN \n" +
				"INSERT INTO " + HISTORY_TABLE + " (usuario, tipo_alteracao, tabela, "+ HISTORY_TABLE_COLUMN + ") \n" +
				"VALUES(USER(), 'DELETE', '" +originTable+"', "+
				"CONCAT(";

		for (int i = 1; i <= rsmd.getColumnCount(); i++) {
			if(i < rsmd.getColumnCount()){
				sqlexe += genItemDeleteSql(rsmd.getColumnName(i)) + ", ";
			}
			else{
				sqlexe += genItemDeleteSql(rsmd.getColumnName(i));
			}
		}
		sqlexe += "));\n" +
				"END;\n";
		return sqlexe;
	}

	private static String genINTriggerSql(String originTable, ResultSetMetaData rsmd) throws SQLException {
		String sqlexe = "CREATE TRIGGER " + "`"+originTable + "_i` BEFORE INSERT ON " + "`"+originTable+"`" + " FOR EACH ROW \n" +
				"BEGIN \n" +
				"INSERT INTO " + HISTORY_TABLE + " (usuario, tipo_alteracao, tabela, "+ HISTORY_TABLE_COLUMN + ") \n" +
				"VALUES(USER(), 'INSERT', '" +originTable+"', "+
				"CONCAT(";

		for (int i = 1; i <= rsmd.getColumnCount(); i++) {
			if(i < rsmd.getColumnCount()){
				sqlexe += genItemInsertSql(rsmd.getColumnName(i)) + ", ";
			}
			else{
				sqlexe += genItemInsertSql(rsmd.getColumnName(i));
			}
		}
		sqlexe += "));\n" +
				"END;\n";
		return sqlexe;
	}

	private static String genExeConditionSql(String targetColumn) {
		return "(OLD." + targetColumn + " IS NOT NULL AND NOT OLD." + targetColumn + " <=> NEW." + targetColumn + ") ";
	}

	private static String getRow(String targetColumn) {
		return "'"+targetColumn+"::', IFNULL(OLD." + targetColumn+", ''),'|'";
	}

	private static String genItemUpdateSql(String targetColumn) {
		return " IF(NOT OLD." + targetColumn + " <=> NEW." + targetColumn + ",CONCAT('NEW::',CONCAT_WS('->',CONCAT('" + targetColumn + "::',IFNULL(OLD." + targetColumn + ",'')),CONCAT(IFNULL(NEW." + targetColumn + ",''),'|'))" + ",''),'')";    }

	private static String genItemDeleteSql(String targetColumn) {
		return "'|', CONCAT('" + targetColumn + "::', OLD." + targetColumn + ")";
	}

	private static String genItemInsertSql(String targetColumn) {
		if(targetColumn.equals("arquivoPdf")){
			return "'|', CONCAT('" + targetColumn + "::', 'boletoEmPdf')";
		}
		return "'|', CONCAT('" + targetColumn + "::',IFNULL(NEW." + targetColumn + ",''))";
	}
}
