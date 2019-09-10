package br.com.Acad.sql;


import java.io.IOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionClass {
	
	public static Connection connection = null;
	public static boolean dbExists = false;
	
	public final static String URL = "jdbc:mysql://localhost:3306/Argus?autoReconnect=true&useSSL=false&useTimezone=true&serverTimezone=UTC&useLegacyDatetimeCode=false";
	public final static String USERNAME = "root";
	public final static String PASSWORD = "9612";
	
	public static Connection createConnection(){
		
		if(!dbExists)runSqlScriptRuntime();
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			if(connection == null || connection.isClosed()){
				connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
			}
						
		} catch (ClassNotFoundException e) {
			Logger.getLogger(ConnectionClass.class.getName()).log(Level.SEVERE, null, e);
		} catch (SQLException e) {
			Logger.getLogger(ConnectionClass.class.getName()).log(Level.SEVERE, null, e);
		}
		return connection;
	
	}
	
	public static void runSqlScriptRuntime(){
		
		String cmd = ("cmd/ /c cd C:\\\\"+"\\Program Files\\MySQL\\MySQL Server 8.0\\bin&mysql --host=127.0.0.1 --port=3306 --force --user=root --password=9612 --execute=\"CREATE DATABASE IF NOT EXISTS Argus;\"");

	    try {	    	
	    	Runtime.getRuntime().exec(cmd);
	    	dbExists = true;
		} catch (IOException e1) {
			e1.printStackTrace();
			System.err.println("Error running command: " + e1.getMessage());
		}

	}
	
}
