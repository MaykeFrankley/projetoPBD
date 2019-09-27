package br.com.Acad.exceptions;

import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.SQLSyntaxErrorException;

import org.hibernate.exception.ExceptionUtils;

import br.com.Acad.util.Util;
import javafx.application.Platform;

public class HandleSQLException {

	public HandleSQLException(Throwable e) {
		Throwable[] t = ExceptionUtils.getThrowables(e);
		for (Throwable throwable : t) {
			if(throwable instanceof SQLSyntaxErrorException){
				if(((SQLSyntaxErrorException) throwable).getErrorCode() == 1142){
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							Util.Alert("Acesso negado!\nContate o administrador");
						}
					});
				}
			}
			else if(throwable instanceof SQLIntegrityConstraintViolationException){
				if(((SQLIntegrityConstraintViolationException) throwable).getErrorCode() == 1062){
					Util.Alert("O registro já está no sistema!");
				}
			}
		}
	}

}
