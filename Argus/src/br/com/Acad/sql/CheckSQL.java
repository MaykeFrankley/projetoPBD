package br.com.Acad.sql;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CheckSQL {

	public Connection con;
	public ResultSet rs;
	public PreparedStatement stmt;

	public boolean checkTableExists(String table) throws SQLException{

		this.con = ConnectionClass.createConnection();
		DatabaseMetaData dbm = con.getMetaData();
		ResultSet tables = dbm.getTables(null, null, table, null);
		if (tables.next()) {
			return true;
		}

		this.con.close();

		return false;
	}

}
