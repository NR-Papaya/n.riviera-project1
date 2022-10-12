package com.revature.jdbc;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import com.revature.model.Person;

public class Driver {

	/*
	 * As JDBC provides an interface against which developers must program, the
	 * basic types you encounter are typically interfaces that are built into java.
	 * these include:
	 * 
	 * Connection (a class, not an interface) Statement PreparedStatement ResultSet
	 * SQLException (this is a class)
	 */

	public static void main(String[] args) {
		/*
		 * lets do a standard JDBC workflow in which we connect to our DB, pull out a
		 * single record, and then close the connection.
		 * 
		 * We'll always need to start by getting a connection to our DB. This entails
		 * authenticating ourselves to a specific DB. the connection interface
		 * represents this connection to a DB. It is through our connection that we are
		 * allowed to execute statements against our DB.
		 * 
		 * we use our DriverManager to obtain a connection. The DriverManager is also
		 * responsible for allowing us to set driver-specific properties.
		 * 
		 * 
		 */

		// using properties file
//		Properties props = new Properties();
//		try(FileReader reader = new FileReader("src/main/resources/application.properties")){
//			props.load(reader);
//		}catch(IOException e) {
//			e.printStackTrace();
//		}
//		
//		Connection conn = null;
//		
//		try {
//			conn = DriverManager.getConnection(
//				props.getProperty("url"), 
//				props.getProperty("username"), 
//				props.getProperty("password")
//				);
//		}catch(SQLException e){
//			e.printStackTrace();
//		}

		Statement stmt = null;
		ResultSet set = null;
		final String sql = "select * from person where person_id = 2";

		// try with resources syntax automatically closes the connection

		try (Connection conn = DriverManager.getConnection(System.getenv("db_url"), System.getenv("db_username"),
				System.getenv("db_password"))) {
			/*
			 * now that we have a connection, wed like to execute a statement against our
			 * db. so lets do a simple query of the person table. in order to execute a
			 * statement, we use the Statement (or PreparedStatement) interface. This is an
			 * object representation of a SQL statement.
			 */
			
			stmt = conn.createStatement();

			/*
			 * A ResultSet is a representation of the records that are returned after
			 * executing a query.
			 */

			set = stmt.executeQuery(sql);
			// .next() move the cursor to the first record; it should be the only record
			// since person_id is specified
			set.next();

			/*
			 * we have to extract the data from each row in a ResultSet in order to use it.
			 */
			Person retrievedPerson = new Person(
					set.getInt(1),
					set.getString(2),
					set.getString(3),
					set.getString(4),
					set.getInt(5)
					);
			
			System.out.println(retrievedPerson.toString());

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				set.close();
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		System.out.println("~~~~~~~~~~~~~~~~~~~");
		updateRecord();
		System.out.println("~~~~~~~~~~~~~~~~~~~");
		printRecord();
	}
	//------------------------------UPDATE RECORD----------------------------------------
	public static void updateRecord() {
		Statement stmt = null;
		String sql = "update person set person_name = 'John' where person_id=3";

		try (Connection conn = DriverManager.getConnection(System.getenv("db_url"),
				System.getenv("db_username"),
				System.getenv("db_password"))){

			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			System.out.println("UPDATED");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	

	private static Connection conn = null;
	
	private static void openConnection(){
		try {
			conn = DriverManager.getConnection(
					System.getenv("db_url"),
					System.getenv("db_username"),
					System.getenv("db_password"));
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	private static void closeConnection(){
		try {
			conn.close();
			conn = null;
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	

	
	public static void printRecord() {
		openConnection();
		Statement stmt = null;
		ResultSet set = null;
		
		try {
			stmt = conn.createStatement();
			set = stmt.executeQuery("Select * from person");
			set.next();
			
			while (set != null) {
				Person retrievedPerson = new Person(
						set.getInt(1),
						set.getString(2),
						set.getString(3),
						set.getString(4),
						set.getInt(5)
						);
				
				System.out.println(retrievedPerson.toString());
				
				if (set.isLast()) {
					break;
				}else {
					set.next();
				}
				
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				stmt.close();
				set.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		closeConnection();
	}
}
