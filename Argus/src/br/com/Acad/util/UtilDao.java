package br.com.Acad.util;

import br.com.Acad.dao.DaoAlunos;
import br.com.Acad.dao.DaoContatos;
import br.com.Acad.dao.DaoCurriculo;
import br.com.Acad.dao.DaoDisciplina;
import br.com.Acad.dao.DaoEndereco;
import br.com.Acad.dao.DaoLog;
import br.com.Acad.dao.DaoMudarSenhas;
import br.com.Acad.dao.DaoPessoa;
import br.com.Acad.dao.DaoProfessor;
import br.com.Acad.dao.DaoResponsaveis;
import br.com.Acad.dao.DaoUsuarios;
import br.com.Acad.model.Aluno;
import br.com.Acad.model.Contato;
import br.com.Acad.model.Curriculo;
import br.com.Acad.model.CurriculoDisciplina;
import br.com.Acad.model.CurriculoID;
import br.com.Acad.model.Disciplina;
import br.com.Acad.model.DisciplinaProfessor;
import br.com.Acad.model.Endereco;
import br.com.Acad.model.LogSistema;
import br.com.Acad.model.LogSistemaID;
import br.com.Acad.model.MudarSenha;
import br.com.Acad.model.Pessoa;
import br.com.Acad.model.Professor;
import br.com.Acad.model.ResponsavelFinanceiro;
import br.com.Acad.model.ResponsavelFinanceiroID;
import br.com.Acad.model.Usuario;
import br.com.Acad.model.ViewAluno;
import br.com.Acad.model.ViewProfessor;
import br.com.Acad.model.ViewResponsavelFinanceiro;
import br.com.Acad.model.ViewUsuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UtilDao {

	private static final DaoCurriculo daoCurriculo = new DaoCurriculo();;
	private static final DaoDisciplina daoDisciplina = new DaoDisciplina();
	private static final DaoContatos daoContatos = new DaoContatos();
	private static final DaoEndereco daoEnderecos = new DaoEndereco();
	private static final DaoLog daoLog = new DaoLog();
	private static final DaoMudarSenhas daoMudarSenhas = new DaoMudarSenhas();
	private static final DaoPessoa daoPessoa = new DaoPessoa();
	private static final DaoProfessor daoProfessor = new DaoProfessor();
	private static final DaoUsuarios daoUsuarios = new DaoUsuarios();
	private static final DaoAlunos daoAlunos = new DaoAlunos();
	private static final DaoResponsaveis daoResponsaveis = new DaoResponsaveis();

	public static int persist(Object obj){
		if(obj instanceof Pessoa){
			return daoPessoa.addPessoa((Pessoa) obj);
		}
		else if(obj instanceof Endereco){
			daoEnderecos.addEndereco((Endereco) obj);
		}
		else if(obj instanceof Contato){
			daoContatos.addContato((Contato) obj);
		}
		else if(obj instanceof Usuario){
			daoUsuarios.addUsuario((Usuario) obj);
		}
		else if(obj instanceof LogSistema){
			daoLog.addLog((LogSistema) obj);
		}
		else if(obj instanceof Curriculo){
			daoCurriculo.addCurriculo((Curriculo) obj);
		}
		else if(obj instanceof Disciplina){
			daoDisciplina.addDisciplina((Disciplina) obj);
		}
		else if(obj instanceof Professor){
			daoProfessor.addProfessor((Professor) obj);
		}
		else if(obj instanceof MudarSenha){
			daoMudarSenhas.addRequest((MudarSenha) obj);
		}
		else if(obj instanceof CurriculoDisciplina){
			daoCurriculo.addDisciplinaToCurriculo((CurriculoDisciplina) obj);
		}
		else if(obj instanceof DisciplinaProfessor){
			daoProfessor.addDisciplinaToProfessor((DisciplinaProfessor) obj);
		}
		else if(obj instanceof Aluno){
			daoAlunos.addAluno((Aluno) obj);
		}
		else if(obj instanceof ResponsavelFinanceiro){
			daoResponsaveis.addResponsavel((ResponsavelFinanceiro) obj);
		}
		return 0;

	}

	public static void update(Object obj){
		if(obj instanceof Pessoa){
			daoPessoa.UpdatePessoa((Pessoa) obj);
		}
		else if(obj instanceof Endereco){
			daoEnderecos.UpdateEndereco((Endereco) obj);
		}
		else if(obj instanceof Contato){
			daoContatos.UpdateContato((Contato) obj);
		}
		else if(obj instanceof Usuario){
			daoUsuarios.updateUsuario((Usuario) obj);
		}
		else if(obj instanceof Curriculo){
			daoCurriculo.updateCurriculo((Curriculo) obj);
		}
		else if(obj instanceof Disciplina){
			daoDisciplina.updateDisciplina((Disciplina) obj);
		}
		else if(obj instanceof Professor){
			daoProfessor.updateProfessor((Professor) obj);
		}
		else if(obj instanceof Aluno){
			daoAlunos.updateAluno((Aluno) obj);
		}
		else if(obj instanceof ResponsavelFinanceiro){
			daoResponsaveis.updateResponsavel((ResponsavelFinanceiro) obj);
		}
	}

	public static void remove(Object obj){
		if(obj instanceof Pessoa){
			daoPessoa.deletarPessoa((Pessoa) obj);
		}
		else if(obj instanceof Usuario){
			daoUsuarios.deletarUsuario((Usuario) obj);
		}
		else if(obj instanceof Curriculo){
			daoCurriculo.removeCurriculo((Curriculo) obj);
		}
		else if(obj instanceof Disciplina){
			daoDisciplina.removeDisciplina((Disciplina) obj);
		}
		else if(obj instanceof DisciplinaProfessor){
			daoProfessor.removeDisciplinaProfessor((DisciplinaProfessor) obj);
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T find(Class<?> c, Object id){
		if(c == Pessoa.class){
			Pessoa p = daoPessoa.getPessoa((Integer) id);
			return (T) p;
		}
		else if(c == Contato.class){
			Contato co = daoContatos.getContato((Integer) id);
			return (T) co;
		}
		else if(c == Endereco.class){
			Endereco e = daoEnderecos.getEndereco((Integer) id);
			return (T) e;
		}
		else if(c == Disciplina.class){
			Disciplina d = daoDisciplina.getDisciplina((String) id);
			return (T) d;
		}
		else if(c == Curriculo.class){
			Curriculo cr = daoCurriculo.getCurriculo((CurriculoID) id);
			return (T) cr;
		}
		else if(c == LogSistema.class){
			LogSistema l = daoLog.getLog((LogSistemaID) id);
			return (T) l;
		}
		else if(c == MudarSenha.class){
			MudarSenha md = daoMudarSenhas.getRequest((String) id);
			return (T) md;
		}
		else if(c == Professor.class){
			Professor pr = daoProfessor.getProfessor((int) id);
			return (T) pr;
		}
		else if(c == Usuario.class){
			Usuario u = daoUsuarios.getUsuario((String) id);
			return (T) u;
		}
		else if(c == Aluno.class){
			Aluno u = daoAlunos.getAluno((int) id);
			return (T) u;
		}

		else if(c == ResponsavelFinanceiro.class){
			ResponsavelFinanceiro res = daoResponsaveis.getResponsavelFinanceiro((ResponsavelFinanceiroID) id);
			return (T) res;
		}

		else if(c == ViewResponsavelFinanceiro.class){
			ViewResponsavelFinanceiro rf = daoAlunos.getResponsavel((int) id);
			return (T) rf;
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	public static <T> T find(Class<?> c, Object id, Object id2){
		if(c == Usuario.class){
			Usuario u = daoUsuarios.getUsuario((String) id, (String) id2);
			return (T) u;
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	public static <T> T getLists(Class<?> c){
		ObservableList<?> oblist = FXCollections.observableArrayList();

		if(c == Pessoa.class){
			oblist = daoPessoa.getAllPessoa();
		}
		else if(c == Contato.class){
			oblist = daoContatos.getAllContato();
		}
		else if(c == Endereco.class){
			oblist = daoEnderecos.getAllEndereco();
		}
		else if(c == Disciplina.class){
			oblist = daoDisciplina.getAllDisciplinas();
		}
		else if(c == Curriculo.class){
			oblist = daoCurriculo.getAllCurriculo();
		}
		else if(c == LogSistema.class){
			oblist = daoLog.getAllLogs();
		}
		else if(c == MudarSenha.class){
			oblist = daoMudarSenhas.getAllRequests();
		}
		else if(c == Professor.class){
			oblist = daoProfessor.getAllProfessores();
		}
		else if(c == Usuario.class){
			oblist = daoUsuarios.getAllUsuarios();
		}
		else if(c == ViewUsuario.class){
			oblist = daoUsuarios.getAllUsuariosView();
		}
		else if(c == ViewProfessor.class){
			oblist = daoProfessor.getAllProfessoresView();
		}
		else if(c == ViewAluno.class){
			oblist = daoAlunos.getAllAlunosView();
		}
		else if(c == ViewResponsavelFinanceiro.class){
			oblist = daoResponsaveis.getAllViewResponsavel();
		}

		return (T) oblist;
	}

	@SuppressWarnings("unchecked")
	public static <T> T getLists(Class<?> c, Object id){
		ObservableList<?> oblist = FXCollections.observableArrayList();

		if(c == CurriculoDisciplina.class){
			oblist = daoCurriculo.getAllDisciplinas((String) id);
		}
		else if(c == DisciplinaProfessor.class){
			oblist = daoProfessor.getDisciplinaOfProfessor((int) id);
		}

		return (T) oblist;
	}

}
