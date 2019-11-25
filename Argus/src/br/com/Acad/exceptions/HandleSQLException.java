package br.com.Acad.exceptions;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import org.hibernate.exception.ExceptionUtils;

import com.mysql.cj.exceptions.MysqlErrorNumbers;

import br.com.Acad.util.Util;
import javafx.application.Platform;

public class HandleSQLException {

	public HandleSQLException(Throwable e){
		handle(e);
	}

	private void handle(Throwable e){
		Throwable[] t = ExceptionUtils.getThrowables(e);
		for (int i = 0; i < t.length; i++) {
			Throwable throwable = t[i];

			if(throwable instanceof SQLException){
				switch (((SQLException) throwable).getErrorCode()) {

				case MysqlErrorNumbers.ER_ACCESS_DENIED_ERROR:
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							Util.Alert("Acesso negado!\nContate o administrador");
							return;
						}
					});
					break;
				
				case MysqlErrorNumbers.ER_TABLEACCESS_DENIED_ERROR:
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							Util.Alert("Acesso negado!\nContate o administrador");
							return;
						}
					});
					break;

				case MysqlErrorNumbers.ER_DUP_ENTRY:
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							Util.Alert("O registro já está no sistema!");
							return;
						}
					});

					break;

				default:
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							Util.Alert("Ocorreu um erro!\nContate o administrador");
							return;
						}
					});
					printExeception(e);
					break;
				}

			}

			else{
				if(i == t.length-1){
					printExeception(throwable);
				}
			}
		}
	}

	private void printExeception(Throwable e){
		e.printStackTrace();
		FileWriter fw = null;
		try {
			fw = new FileWriter("execp.txt", true);
			PrintWriter pw = new PrintWriter (fw);
	        e.printStackTrace (pw);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

}
