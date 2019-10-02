package br.com.Acad.util;

import java.util.Timer;
import java.util.TimerTask;

import br.com.Acad.controller.MainTelaController;
import br.com.Acad.dao.DaoLog;
import br.com.Acad.dao.DaoUsuarios;
import br.com.Acad.model.Curriculo;
import br.com.Acad.model.LogSistema;
import br.com.Acad.model.Usuario;

public class SysLog {

	private static DaoLog daoLog = new DaoLog();

	private static int ordem = 0;

	public static String message(String message){
		String s = "O usuário \""+MainTelaController.user.getUser()+"\""+message;
		return s;
	}

	public static String createUser(int cod){
		String s = "Um administrador \""+MainTelaController.user.getUser()+"\" cadastrou um novo usuário de código: "+cod;
		return s;
	}

	public static String createCurriculo(Curriculo c){
		String s = "Um administrador \""+MainTelaController.user.getUser()+"\" cadastrou um novo currículo de código: "+c.getId().getCodCurriculo()+" e ano letivo: "+c.getId().getAnoLetivo();
		return s;
	}

	public static String createTipoPessoa(String tipo, int cod){
		String s = "O usuário \""+MainTelaController.user.getUser()+"\" cadastrou um novo "+tipo+" de código: "+cod;
		return s;
	}

	public static String updatePessoas(String updated, int cod){
		String s = "Um administrador \""+MainTelaController.user.getUser()+"\" alterou "+updated+" da pessoa de código: "+cod;
		return s;
	}

	public static String createPessoas(int cod){
		String s = "Um administrador \""+MainTelaController.user.getUser()+"\" adicionou uma nova pessoa de código: "+cod;
		return s;
	}

	public static String deletePessoas(int cod){
		String s = "Um administrador \""+MainTelaController.user.getUser()+"\" deletou a pessoa de código: "+cod;
		return s;
	}

	public static String createDados(String created, int cod){
		String s = "Um administrador \""+MainTelaController.user.getUser()+"\" adicionou um novo "+created+" da pessoa de código: "+cod;
		return s;
	}

	public static String passWordChange(String user){
		String s = "Um administrador \""+MainTelaController.user.getUser()+"\" autorizou troca de senha do usuário :"+user;
		return s;
	}

	public static String passWordChangeRequest(String cpf){
		DaoUsuarios daoUsuarios = new DaoUsuarios();
		Usuario u = daoUsuarios.getUsuario(cpf);
		String r = "Usuário \""+u.getUser()+"\" solicitou troca de senha.";
		return r;
	}

	public static String login(String user){
		String r = "O usuário \""+user+"\" fez o login!";
		return r;
	}

	public static void addLog(String... args){
		for(String action : args){
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					LogSistema ls = Util.prepareLog();
		    		ls.setAcao(action);
		    		try {
						daoLog.addLog(ls);
					} catch (Exception e2) {
						e2.printStackTrace();
					}

				}
			}, ordem);

			ordem += 1000;

		}
	}

	public static void complete(){
		ordem = 0;
//	    Platform.runLater(timeline::play);

	}

}
