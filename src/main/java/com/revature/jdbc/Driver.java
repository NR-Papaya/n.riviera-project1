package com.revature.jdbc;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Driver {
	
	/*
	 * As JDBC provides an interface against which developers must program,
	 * the basic types you encounter are typically interfaces that are built
	 * into java. these include:
	 * 
	 * Connection (a class, not an interface)
	 * Statement
	 * PreparedStatement
	 * ResultSet
	 * SQLException (this is a class)
	 */
	
	
	public static void main(String[] args) {
		/*
		 * lets do a standard JDBC workflow in which we connect to our DB,
		 * pull out a single record, and then close the connection.
		 * 
		 * We'll always need to start by getting a connection to our DB. This entails
		 * authenticating ourselves to a specific DB. the connection interface represents
		 * this connection to a DB.
		 * It is through our connection that we are allowed to execute statements against our DB.
		 * 
		 * we use our DriverManager to obtain a connection. The DriverManager
		 * is also responsible for allowing us to set driver-specific properties.
		 * 
		 * 
		 */
		
		Properties props = new Properties();
		try(FileReader reader = new FileReader("src/main/resources/application.properties")){
			props.load(reader);
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		Connection conn = null;
		
		try {
			conn = DriverManager.getConnection(
				props.getProperty("url"), 
				props.getProperty("username"), 
				props.getProperty("password")
				);
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
}
