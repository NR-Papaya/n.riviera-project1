package com.revature.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
//********************* CREATE METHODS ***********************************************
	
//********************* READ METHODS ***********************************************
	
//********************* UPDATE METHODS ***********************************************
	
//********************* DELETE METHODS ***********************************************
	
	
}
