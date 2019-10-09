package br.com.Acad.util;

import br.com.Acad.controller.MainTelaController;
import br.com.Acad.dao.DaoUsuarios;
import br.com.Acad.model.Curriculo;
import br.com.Acad.model.LogSistema;
import br.com.Acad.model.Usuario;

public class SysLog {

	public static String message(String message){
		String s = "O usu�rio \""+MainTelaController.user.getUser()+"\""+message;
		return s;
	}

	public static String createUser(int cod){
		String s = "Um administrador \""+MainTelaController.user.getUser()+"\" cadastrou um novo usu�rio de c�digo: "+cod;
		return s;
	}

	public static String createCurriculo(Curriculo c){
		String s = "Um administrador \""+MainTelaController.user.getUser()+"\" cadastrou um novo curr�culo de c�digo: "+c.getId().getCodCurriculo()+" e ano letivo: "+c.getId().getAnoLetivo();
		return s;
	}

	public static String createTipoPessoa(String tipo, int cod){
		String s = "O usu�rio \""+MainTelaController.user.getUser()+"\" cadastrou um novo "+tipo+" de c�digo: "+cod;
		return s;
	}

	public static String updatePessoas(String updated, int cod){
		String s = "Um administrador \""+MainTelaController.user.getUser()+"\" alterou "+updated+" da pessoa de c�digo: "+cod;
		return s;
	}

	public static String createPessoas(int cod){
		String s = "Um administrador \""+MainTelaController.user.getUser()+"\" adicionou uma nova pessoa de c�digo: "+cod;
		return s;
	}

	public static String deletePessoas(int cod){
		String s = "Um administrador \""+MainTelaController.user.getUser()+"\" deletou a pessoa de c�digo: "+cod;
		return s;
	}

	public static String createDados(String created, int cod){
		String s = "Um administrador \""+MainTelaController.user.getUser()+"\" adicionou um novo "+created+" da pessoa de c�digo: "+cod;
		return s;
	}

	public static String passWordChange(String user){
		String s = "Um administrador \""+MainTelaController.user.getUser()+"\" autorizou troca de senha do usu�rio :"+user;
		return s;
	}

	public static String passWordChangeRequest(String cpf){
		DaoUsuarios daoUsuarios = new DaoUsuarios();
		Usuario u = daoUsuarios.getUsuario(cpf);
		String r = "Usu�rio \""+u.getUser()+"\" solicitou troca de senha.";
		return r;
	}

	public static String login(String user){
		String r = "O usu�rio \""+user+"\" fez o login!";
		return r;
	}

	public static void addLog(String... args){
		for(String action : args){
			LogSistema ls = Util.prepareLog();
			ls.setAcao(action);
			UtilDao.persist(ls);
		}
	}

}
