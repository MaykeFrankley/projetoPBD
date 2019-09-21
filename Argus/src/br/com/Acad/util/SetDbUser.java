package br.com.Acad.util;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Persistence;

import br.com.Acad.app.Main;

public class SetDbUser {

	public SetDbUser(String user, String pass) {
		Map<String, Object> configOverrides = new HashMap<String, Object>();
		configOverrides.put("hibernate.connection.user", user);
		configOverrides.put("hibernate.connection.password", pass);

		Main.factory = Persistence.createEntityManagerFactory("argusDB", configOverrides);
	}

}
