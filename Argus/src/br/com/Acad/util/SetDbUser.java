package br.com.Acad.util;

import java.util.Properties;
import java.util.TimerTask;

import javax.persistence.EntityManagerFactory;

import org.hibernate.ejb.Ejb3Configuration;

import br.com.Acad.app.Main;
import br.com.Acad.model.Aluno;
import br.com.Acad.model.AlunoMedia;
import br.com.Acad.model.AlunoNota;
import br.com.Acad.model.AlunoTurma;
import br.com.Acad.model.Contato;
import br.com.Acad.model.Curriculo;
import br.com.Acad.model.CurriculoDisciplina;
import br.com.Acad.model.Disciplina;
import br.com.Acad.model.DisciplinaProfessor;
import br.com.Acad.model.Endereco;
import br.com.Acad.model.LogSistema;
import br.com.Acad.model.MudarSenha;
import br.com.Acad.model.Pessoa;
import br.com.Acad.model.Professor;
import br.com.Acad.model.ResponsavelFinanceiro;
import br.com.Acad.model.Turma;
import br.com.Acad.model.Usuario;
import br.com.Acad.model.ViewAluno;
import br.com.Acad.model.ViewProfessor;
import br.com.Acad.model.ViewResponsavelFinanceiro;
import br.com.Acad.model.ViewTurma;
import br.com.Acad.model.ViewUsuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SetDbUser extends TimerTask {

	private String user;
	private String pass;
	public static EntityManagerFactory fac;

	public SetDbUser(String user, String pass) {
		this.user = user;
		this.pass = pass;
	}

	@Override
	@SuppressWarnings("rawtypes")
	public void run() {
		try {
			Properties pros = new Properties();

			pros.setProperty("hibernate.jdbc.time_zone" ,"UTC");
			pros.setProperty("hibernate.archive.autodetection" ,"class");
			pros.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
			pros.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
			pros.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/argus?useSSL=true&useTimezone=true&serverTimezone=UTC&allowPublicKeyRetrieval=true&characterEncoding=UTF-8");
			pros.setProperty("hibernate.connection.user", user);
			pros.setProperty("hibernate.connection.password", pass);
			pros.setProperty("hibernate.hbm2ddl.auto", "update");
			pros.setProperty("hibernate.temp.use_jdbc_metadata_defaults", "false");
			pros.setProperty("hibernate.show_sql", "false");
			pros.setProperty("hibernate.format_sql", "false");
			pros.setProperty("use_sql_comments", "true");

			Ejb3Configuration cfg = new Ejb3Configuration();

			ObservableList<Class> classes = FXCollections.observableArrayList();
			classes.setAll(Aluno.class, AlunoNota.class, AlunoMedia.class, AlunoTurma.class, Contato.class, Curriculo.class, CurriculoDisciplina.class, Disciplina.class,
					DisciplinaProfessor.class, Endereco.class, LogSistema.class, MudarSenha.class, Pessoa.class, Professor.class, ResponsavelFinanceiro.class,
					Turma.class, Usuario.class, ViewAluno.class, ViewProfessor.class, ViewResponsavelFinanceiro.class, ViewTurma.class, ViewUsuario.class);

			for (Class clazz : classes) {
				cfg.addAnnotatedClass(clazz);
			}

//			EntityManagerFactory fac = Persistence.createEntityManagerFactory("argusDB", configOverrides);

			fac = cfg.addProperties(pros).buildEntityManagerFactory();
			Main.factory = fac;
		} catch (Throwable e) {
			e.printStackTrace();
			Util.Alert("Ocorreu um erro!\nReinicie o sistema ou contate o administrador!");
		}


	}

}
