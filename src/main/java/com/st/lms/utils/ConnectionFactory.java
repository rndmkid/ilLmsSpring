package com.st.lms.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {

	static Properties prop = new Properties();
	static InputStream input = null;
	public static String dbURL;
	public static String user;
	public static String pass;
	
	public static Connection getMyConnection() {
		try {
			input = new FileInputStream("C:\\Users\\Vienhang\\eclipse-smoothstack\\ilLmsSpring\\src\\main\\resources\\static\\.config");
			prop.load(input);
			
			dbURL = prop.getProperty("url");
			user = prop.getProperty("user");
			pass = prop.getProperty("password");
		}
		catch(IOException e) {
			System.err.println("Unable to find config file!");
		}
		
		Connection dbConnection = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver"); 
			dbConnection = DriverManager.getConnection(dbURL, user, pass);
			dbConnection.setAutoCommit(false);
			return dbConnection;
		} 
		catch (SQLException | ClassNotFoundException e) {
			throw new RuntimeException("Error connecting to the database!", e);
		}
	}
}