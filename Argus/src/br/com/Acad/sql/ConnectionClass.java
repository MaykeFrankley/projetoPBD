package br.com.Acad.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;


public class ConnectionClass {

	public static Connection connection = null;
	public static boolean dbExists = false;

	public final static String URL = "jdbc:mysql://127.0.0.1:3306?useUnicode=yes&characterEncoding=UTF-8&autoReconnect=true&useSSL=true&"
			+ "&allowPublicKeyRetrieval=true&useTimezone=true&serverTimezone=UTC&useLegacyDatetimeCode=false";
	public final static String USERNAME = "root";
	public final static String PASSWORD = "9612";

	public static Connection createConnection(){

		if(!dbExists){
			dbExists = true;
			createSchema();
		}

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			if(connection == null || connection.isClosed()){
				connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
			}

		} catch (ClassNotFoundException e) {
			Logger.getLogger(ConnectionClass.class.getName()).log(Level.SEVERE, null, e);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, null, "Erro ao contactar banco de dados!", JOptionPane.ERROR_MESSAGE);
			Logger.getLogger(ConnectionClass.class.getName()).log(Level.SEVERE, null, e);
		}
		return connection;

	}

	private static void createSchema(){
		try {
			Connection con = createConnection();
			Statement Stmt = con.createStatement();
			Stmt.execute("CREATE SCHEMA IF NOT EXISTS `argus` DEFAULT CHARACTER SET utf8 ;");
			Stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

//	public static void runSqlScriptRuntime(){
//
//		System.out.println("Running Script");
//
//		String cmd = ("cmd/ /c cd C:\\\\"+"\\Program Files\\MySQL\\MySQL Server 8.0\\bin&mysql --host=127.0.0.1 --port=3306 --force --user=root --password=9612 --execute=\"CREATE DATABASE IF NOT EXISTS Argus;\"");
//
//	    try {
//	    	Runtime.getRuntime().exec(cmd);
//		} catch (IOException e1) {
//			e1.printStackTrace();
//			System.err.println("Error running command: " + e1.getMessage());
//		}
//
//	}

}
