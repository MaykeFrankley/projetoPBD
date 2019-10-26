package br.com.Acad.sql;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.commons.codec.digest.DigestUtils;

import br.com.Acad.util.ScriptRunner;

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

			String admin = "INSERT INTO `Pessoas` VALUES (codPessoa, 'Mayke Frankley', 'Serra Talhada', '1997-08-08', '116.734.914-80', 'Ativo');";
			stmt = con.prepareStatement(admin);
			stmt.execute();

			String hashPass = DigestUtils.md5Hex("1");
			stmt = con.prepareStatement("INSERT INTO Usuarios VALUES(1, '116.734.914-80', 'admin', ?, 'Admin', 'Ativo')");
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

			stmt = con.prepareStatement("CREATE USER ?@'localhost' IDENTIFIED BY ?;");
			stmt.setString(1, "JoseSilva");
			stmt.setString(2, hashPass);
			stmt.execute();

			stmt = con.prepareStatement("grant select on argus.ViewAluno to ?@localhost;");
    	    stmt.setString(1, "JoseSilva");
			stmt.execute();

			stmt = con.prepareStatement("grant select on argus.ViewTurma to ?@localhost;");
    	    stmt.setString(1, "JoseSilva");
			stmt.execute();

			stmt = con.prepareStatement("grant execute on PROCEDURE argus.getAlunos to ?@localhost;");
    	    stmt.setString(1, "JoseSilva");
			stmt.execute();

			stmt = con.prepareStatement("grant execute on PROCEDURE argus.generateNotas to ?@localhost;");
    	    stmt.setString(1, "JoseSilva");
			stmt.execute();

			stmt = con.prepareStatement("grant execute on PROCEDURE argus.getResponsaveis to ?@localhost;");
    	    stmt.setString(1, "JoseSilva");
			stmt.execute();

			stmt = con.prepareStatement("grant execute on PROCEDURE argus.getResponsavelDoAluno to ?@localhost;");
    	    stmt.setString(1, "JoseSilva");
			stmt.execute();

			stmt = con.prepareStatement("grant select on argus.curriculo to ?@localhost;");
    	    stmt.setString(1, "JoseSilva");
			stmt.execute();

			stmt = con.prepareStatement("grant select on argus.`curriculo-disciplina`to ?@localhost;");
    	    stmt.setString(1, "JoseSilva");
			stmt.execute();

			stmt = con.prepareStatement("grant select on argus.curriculo to ?@localhost;");
    	    stmt.setString(1, "JoseSilva");
			stmt.execute();

			stmt = con.prepareStatement("grant select, insert, update on argus.Turmas to ?@localhost;");
    	    stmt.setString(1, "JoseSilva");
			stmt.execute();

			stmt = con.prepareStatement("grant select, insert, update on argus.`Aluno-Turma` to ?@localhost;");
    	    stmt.setString(1, "JoseSilva");
			stmt.execute();

			stmt = con.prepareStatement("grant select, insert, update on argus.Alunos to ?@localhost;");
    	    stmt.setString(1, "JoseSilva");
			stmt.execute();

			stmt = con.prepareStatement("grant select, insert, update on argus.Pessoas to ?@localhost;");
    	    stmt.setString(1, "JoseSilva");
			stmt.execute();

			stmt = con.prepareStatement("grant select, insert, update on argus.enderecos to ?@localhost;");
    	    stmt.setString(1, "JoseSilva");
			stmt.execute();

			stmt = con.prepareStatement("grant select, insert, update on argus.contatos to ?@localhost;");
    	    stmt.setString(1, "JoseSilva");
			stmt.execute();

			stmt = con.prepareStatement("grant select, insert, update on argus.Disciplinas to ?@localhost;");
    	    stmt.setString(1, "JoseSilva");
			stmt.execute();

			stmt = con.prepareStatement("grant select, insert, update on argus.Notas to ?@localhost;");
    	    stmt.setString(1, "JoseSilva");
			stmt.execute();

			stmt = con.prepareStatement("grant select, insert, update on argus.Notas to ?@localhost;");
    	    stmt.setString(1, "JoseSilva");
			stmt.execute();

			stmt = con.prepareStatement("grant insert on argus.LogSistema to ?@localhost;");
    	    stmt.setString(1, "JoseSilva");
			stmt.execute();

			//////////////////////////////////////
			String user3 = "INSERT INTO `Pessoas` VALUES (codPessoa, 'Maria Silva', 'Serra Talhada', '1980-05-16', '111.111.111-11', 'Ativo');";
			stmt = con.prepareStatement(user3);
			stmt.execute();

			stmt = con.prepareStatement("INSERT INTO enderecos VALUES(last_insert_id(), 'testeRua', 10, 'teste', 'teste', 'teste', 'teste')");
			stmt.execute();

			stmt = con.prepareStatement("INSERT INTO Usuarios VALUES(103, '111.111.111-11', 'MariaSilva', ?, 'Direção', 'Ativo')");
			stmt.setString(1, hashPass);
			stmt.execute();

			stmt = con.prepareStatement("CREATE USER ?@'localhost' IDENTIFIED BY ?;");
			stmt.setString(1, "MariaSilva");
			stmt.setString(2, hashPass);
			stmt.execute();

			stmt = con.prepareStatement("grant select on argus.* to ?@localhost;");
    	    stmt.setString(1, "MariaSilva");
			stmt.execute();

			stmt.close();
			con.close();

			return false;
		}
		return true;

	}

}
