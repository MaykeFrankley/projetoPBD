package br.com.Acad.util;

import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.Acad.app.Main;

public class SetDbUser extends TimerTask {

	private String user;
	private String pass;

	public SetDbUser(String user, String pass) {
		this.user = user;
		this.pass = pass;
	}

	@Override
	public void run() {
		Map<String, String> configOverrides = new HashMap<String, String>();
		configOverrides.put("hibernate.connection.user", user);
		configOverrides.put("hibernate.connection.password", pass);

		EntityManagerFactory fac = Persistence.createEntityManagerFactory("argusDB", configOverrides);
		Main.factory = fac;

	}

}
