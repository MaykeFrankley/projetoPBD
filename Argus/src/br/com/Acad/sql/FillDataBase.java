package br.com.Acad.sql;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.commons.codec.digest.DigestUtils;

import br.com.Acad.model.Usuario;
import br.com.Acad.util.ScriptRunner;
import br.com.Acad.util.Util;

public class FillDataBase {

	public CheckSQL sql;
	public Connection con;
	public PreparedStatement stmt;

	public boolean checkDB() throws SQLException, FileNotFoundException, IOException, InterruptedException {

		sql = new CheckSQL();

		if(!sql.checkTableExists("pessoas")){
			con = ConnectionClass.createConnection();

			ScriptRunner runner = new ScriptRunner(con, false, false);

			BufferedReader in = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/scripts/createDB.sql"), "UTF8"));
			runner.runScript(in);

			String admin = "INSERT INTO `Pessoas` VALUES (codPessoa, 'Mayke Frankley', 'Serra Talhada', '1997-08-08', '000.000.000-00', 'Ativo');";
			stmt = con.prepareStatement(admin);
			stmt.execute();

			String hashPass = DigestUtils.md5Hex("1");
			stmt = con.prepareStatement("INSERT INTO Usuarios VALUES(1, '000.000.000-00', 'admin', ?, 'Admin', 'Ativo')");
			stmt.setString(1, hashPass);
			stmt.execute();

			stmt = con.prepareStatement("INSERT INTO enderecos VALUES(1, 'testeRua', 10, 'teste', 'teste', 'teste', 'teste')");
			stmt.execute();

			BufferedReader in2 = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/scripts/PopulatePessoasRandom.sql"), "UTF8"));

			runner.runScript(in2);

			Thread.sleep(1000);

			stmt = con.prepareStatement("CREATE USER ?@'localhost' IDENTIFIED BY ?;");
			stmt.setString(1, "admin");
			stmt.setString(2, hashPass);
			stmt.execute();

			stmt = con.prepareStatement("grant all privileges on argus.* to admin@localhost;");
			stmt.execute();

			//////////////////////////////////////
			String user2 = "INSERT INTO `Pessoas` VALUES (2, 'José Padilha Silva', 'Serra Talhada', '1980-05-16', '116.738.742-50', 'Ativo');";
			stmt = con.prepareStatement(user2);
			stmt.execute();

			stmt = con.prepareStatement("INSERT INTO Usuarios VALUES(2, '116.738.742-50', 'JoseSilva', ?, 'Secretaria', 'Ativo');");
			stmt.setString(1, hashPass);
			stmt.execute();

			stmt = con.prepareStatement("INSERT INTO enderecos VALUES(2, 'testeRua', 10, 'teste', 'teste', 'teste', 'teste');");
			stmt.execute();

			Usuario u1 = new Usuario();
			u1.setUser("JoseSilva");
			u1.setTipo("Secretaria");
			u1.setSenha(hashPass);
			Util.setPrivileges(u1);


			//////////////////////////////////////
			String user3 = "INSERT INTO `Pessoas` VALUES (codPessoa, 'Maria Silva', 'Serra Talhada', '1980-05-16', '121.141.131-11', 'Ativo');";
			stmt = con.prepareStatement(user3);
			stmt.execute();

			stmt = con.prepareStatement("INSERT INTO enderecos VALUES(last_insert_id(), 'testeRua', 10, 'teste', 'teste', 'teste', 'teste')");
			stmt.execute();

			stmt = con.prepareStatement("INSERT INTO Usuarios VALUES(103, '121.141.131-11', 'MariaSilva', ?, 'Direção', 'Ativo')");
			stmt.setString(1, hashPass);
			stmt.execute();

			Usuario u2 = new Usuario();
			u2.setUser("MariaSilva");
			u2.setTipo("Direção");
			u2.setSenha(hashPass);
			Util.setPrivileges(u2);

			/////////////////////////////////////

			String user4 = "INSERT INTO `Pessoas` VALUES (codPessoa, 'João Cleber Silva', 'Serra Talhada', '1980-05-16', '211.151.411-81', 'Ativo');";
			stmt = con.prepareStatement(user4);
			stmt.execute();

			stmt = con.prepareStatement("INSERT INTO enderecos VALUES(last_insert_id(), 'testeRua', 10, 'teste', 'teste', 'teste', 'teste')");
			stmt.execute();

			stmt = con.prepareStatement("INSERT INTO Usuarios VALUES(104, '211.151.411-81', 'JoaoSilva', ?, 'Pedagogo', 'Ativo')");
			stmt.setString(1, hashPass);
			stmt.execute();

			Usuario u3 = new Usuario();
			u3.setUser("JoaoSilva");
			u3.setTipo("Direção");
			u3.setSenha(hashPass);
			Util.setPrivileges(u3);

			return false;
		}
		return true;

	}

}
