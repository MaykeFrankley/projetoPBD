package br.com.Acad.sql;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import org.apache.commons.codec.digest.DigestUtils;

public class FillDataBase {

	public CheckSQL sql;
	public Connection con;
	public PreparedStatement stmt;

	public boolean checkDB() throws SQLException, FileNotFoundException, IOException {

		sql = new CheckSQL();

		if(!sql.checkTableExists("pessoas")){
			con = ConnectionClass.createConnection();

			ScriptRunner runner = new ScriptRunner(con, false, false);
			File file = new File(getClass().getResource("/scripts/createDB.sql").getFile());

			runner.runScript(new BufferedReader(new FileReader(file)));

			File file2 = new File(getClass().getResource("/scripts/PopulatePessoasRandom.sql").getFile());

			String admin = "INSERT INTO `Pessoas` VALUES (codPessoa, 'Mayke Frankley', 'Serra Talhada', '1997-08-08', '116.734.914-80', 'Ativo');";
			stmt = con.prepareStatement(admin);
			stmt.execute();

			String selectAdmin = "SELECT codPessoa, CPF, Status FROM Pessoas;";
			ResultSet rs = con.createStatement().executeQuery(selectAdmin);

			if(rs.next()){
				String hashPass = DigestUtils.md5Hex("admin9612");
				stmt = con.prepareStatement("INSERT INTO Usuarios VALUES(?,?,?,?,?,?)");
				stmt.setInt(1, rs.getInt("codPessoa"));
				stmt.setString(2, rs.getString("CPF"));
				stmt.setString(3, "admin");
				stmt.setString(4, hashPass);
				stmt.setString(5, "Admin");
				stmt.setString(6, "Ativo");

				stmt.execute();
			}

			runner.runScript(new BufferedReader(
					   new InputStreamReader(
			                      new FileInputStream(file2), "UTF8")));
			rs.close();

			String hashPass = DigestUtils.md5Hex("admin9612");
			stmt = con.prepareStatement("CREATE USER ?@'localhost' IDENTIFIED BY ?;");
			stmt.setString(1, "admin");
			stmt.setString(2, hashPass);

			stmt.execute();

			stmt = con.prepareStatement("grant all privileges on argus.* to admin@localhost;");
			stmt.execute();

			stmt.close();
			con.close();



			return false;
		}
		return true;

	}

}
