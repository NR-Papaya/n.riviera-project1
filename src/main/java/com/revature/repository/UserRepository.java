package com.revature.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.model.Ticket;
import com.revature.model.TicketUser;
import com.revature.utils.ConnectionFactory;

public class UserRepository {

//********************* VALIDATION METHODS ***********************************************
	// Validates USER ID: True = Valid -- false = invalid
		public static boolean validateUserId(int id) {
			PreparedStatement pstmt = null;
			ResultSet set = null;
			boolean validStatus = false;

			try (Connection conn = ConnectionFactory.getConnection()) {
				String SQL = "Select * from users where user_id=?";
				pstmt = conn.prepareStatement(SQL);
				pstmt.setInt(1, id);
				set = pstmt.executeQuery();
				if (set.next()) {
					validStatus = true;
				}

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					pstmt.close();
					set.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			return validStatus;
		}
		//----------------------------------------------------
		public static boolean isValidUserObj(TicketUser user) {
			
			boolean isValid = false;
			
			if (
				user.getUserName().length() > 0 &&
				user.getPassword().length() > 0 &&
				user.getF_name().length() > 0 &&
				user.getL_name().length() > 0 &&
				(user.getRole().equals("Employee") ||
						user.getRole().equals("Manager"))
					) {
				isValid = true;
			}
			
			
			return isValid;
		}
		//------------------------------------------------------
		public static boolean availableUserName(String userName) {
			boolean isValid = true;
			
			PreparedStatement pstmt = null;
			ResultSet set = null;

			try (Connection conn = ConnectionFactory.getConnection()) {
				String SQL = "Select * from users where user_name=?";
				pstmt = conn.prepareStatement(SQL);
				pstmt.setString(1, userName);
				set = pstmt.executeQuery();
				if (set.next()) {
					isValid = false;
				}

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					pstmt.close();
					set.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			
			return isValid;
		}
		
//********************* CREATE METHODS ***********************************************
		public static void addUser(TicketUser user) {

			PreparedStatement pstmt = null;
			
			try(Connection conn = ConnectionFactory.getConnection()){
				String SQL = "insert into users(user_id,user_name,password,f_name,l_name,role)"
						+ " values (default,?,?,?,?,?)";
				pstmt = conn.prepareStatement(SQL);
				pstmt.setString(1, user.getUserName());
				pstmt.setString(2, user.getPassword());
				pstmt.setString(3, user.getF_name());
				pstmt.setString(4, user.getL_name());
				pstmt.setString(5, user.getRole());
				pstmt.execute();
			}catch(SQLException e) {
				e.printStackTrace();
			}finally{
				try {
					pstmt.close();
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
		}
//********************* READ METHODS ***********************************************
	
//********************* UPDATE METHODS ***********************************************
	
//********************* DELETE METHODS ***********************************************
	
	
}
