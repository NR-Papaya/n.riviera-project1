package com.revature.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.model.Ticket;
import com.revature.utils.ConnectionFactory;
import com.revature.repository.UserRepository;

public class TicketRepository {

//********************* VALIDATION METHODS ***********************************************

	// Validates Ticket ID: True = Valid -- false = invalid
	public static boolean validateTicketId(int id) {
		PreparedStatement pstmt = null;
		ResultSet set = null;
		boolean validStatus = false;

		try (Connection conn = ConnectionFactory.getConnection()) {
			String SQL = "Select * from tickets where ticket_id=?";
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
	
	// ---------------------------------------------------------------------
	// Validates query string status value: True = valid -- False = invalid
	public static boolean validateUpdateStatus(String status) {
		boolean validStatus = false;
		
		if (status.equals("approved") || status.equals("denied")) {
			validStatus = true;
		}
		return validStatus;
	}
	
	// ---------------------------------------------------------------------
	// Validate ticket status is pending
	public static boolean validateTicketStatus(Ticket ticket) {
		boolean validTicketStatus = false;
		
		if (ticket.getTicket_status().equals("pending")) {
			validTicketStatus = true;
		}
		
		return validTicketStatus;
	}
	
	public static boolean isValidTicket(Ticket ticket) {
		boolean isValid = false;
		if (ticket.getTicket_amount() > 0 && 
				ticket.getTicket_description().length() > 0 &&
				ticket.getTicket_status().equals("pending") &&
				UserRepository.validateUserId(ticket.getTicket_user_id())
				) {
			isValid = true;
		}
		
		
		return isValid;
	}

//********************* CREATE METHODS ***************************************************

	public static void addTicket(Ticket ticket) {

		PreparedStatement pstmt = null;
		
		try(Connection conn = ConnectionFactory.getConnection()){
			String SQL = "insert into tickets(ticket_id,ticket_amount,ticket_description,ticket_status,ticket_create_date,ticket_user_id)"
					+ " values (default,?,?,?,default,?)";
			pstmt = conn.prepareStatement(SQL);
			pstmt.setFloat(1, ticket.getTicket_amount());
			pstmt.setString(2, ticket.getTicket_description());
			pstmt.setString(3,ticket.getTicket_status());
			pstmt.setInt(4, ticket.getTicket_user_id());
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
	
//********************* READ METHODS *****************************************************

	// ---------------------------------------------------------------------
	public static Ticket findTicketById(int id) {
		PreparedStatement pstmt = null;
		ResultSet set = null;
		Ticket returnTicket = null;

		try (Connection conn = ConnectionFactory.getConnection()) {
			String SQL = "Select * from tickets where ticket_id=?";
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, id);
			set = pstmt.executeQuery();
			set.next();
			returnTicket = new Ticket(set.getInt("ticket_id"), set.getFloat("ticket_amount"),
					set.getString("ticket_description"), set.getString("ticket_status"), set.getInt("ticket_user_id"),
					set.getString("ticket_create_date"));

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
		return returnTicket;
	}

	// ---------------------------------------------------------------------
	public static List<Ticket> listByUser(int id) {
		Statement stmt = null;
		ResultSet set = null;

		List<Ticket> ticketList = new ArrayList<>();

		try (Connection conn = ConnectionFactory.getConnection()) {
			stmt = conn.createStatement();
			set = stmt.executeQuery("select * from tickets where ticket_user_id="+id+" order by ticket_id asc");

			while (set.next()) {
				Ticket newTicket = new Ticket(set.getInt("ticket_id"), set.getFloat("ticket_amount"),
						set.getString("ticket_description"), set.getString("ticket_status"),
						set.getInt("ticket_user_id"), set.getString("ticket_create_date"));

				ticketList.add(newTicket);

			}

		} catch (SQLException e) {

		} finally {
			try {
				stmt.close();
				set.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return ticketList;
	}

	// ---------------------------------------------------------------------
	public static List<Ticket> listAll() {
		Statement stmt = null;
		ResultSet set = null;

		List<Ticket> ticketList = new ArrayList<>();

		try (Connection conn = ConnectionFactory.getConnection()) {
			stmt = conn.createStatement();
			set = stmt.executeQuery("select * from tickets order by ticket_id asc");

			while (set.next()) {
				Ticket newTicket = new Ticket(set.getInt("ticket_id"), set.getFloat("ticket_amount"),
						set.getString("ticket_description"), set.getString("ticket_status"),
						set.getInt("ticket_user_id"), set.getString("ticket_create_date"));

				ticketList.add(newTicket);

			}

		} catch (SQLException e) {

		} finally {
			try {
				stmt.close();
				set.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return ticketList;
	}

	// ---------------------------------------------------------------------
	public static List<Ticket> listPending() {
		Statement stmt = null;
		ResultSet set = null;

		List<Ticket> ticketList = new ArrayList<>();

		try (Connection conn = ConnectionFactory.getConnection()) {
			stmt = conn.createStatement();
			set = stmt.executeQuery("select * from tickets where ticket_status = 'pending' order by ticket_id asc");
			boolean hasSet = set.next();
			while (hasSet) {
				Ticket newTicket = new Ticket(set.getInt("ticket_id"), set.getFloat("ticket_amount"),
						set.getString("ticket_description"), set.getString("ticket_status"),
						set.getInt("ticket_user_id"), set.getString("ticket_create_date"));
				ticketList.add(newTicket);

				hasSet = set.next();
			}

		} catch (SQLException e) {

		} finally {
			try {
				stmt.close();
				set.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return ticketList;
	}

	// ---------------------------------------------------------------------
	public static List<Ticket> listApproved() {
		Statement stmt = null;
		ResultSet set = null;

		List<Ticket> ticketList = new ArrayList<>();

		try (Connection conn = ConnectionFactory.getConnection()) {
			stmt = conn.createStatement();
			set = stmt.executeQuery("select * from tickets where ticket_status = 'approved' order by ticket_id asc");
			boolean hasSet = set.next();
			while (hasSet) {
				Ticket newTicket = new Ticket(set.getInt("ticket_id"), set.getFloat("ticket_amount"),
						set.getString("ticket_description"), set.getString("ticket_status"),
						set.getInt("ticket_user_id"), set.getString("ticket_create_date"));

				ticketList.add(newTicket);

				hasSet = set.next();
			}

		} catch (SQLException e) {

		} finally {
			try {
				stmt.close();
				set.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return ticketList;
	}

	// ---------------------------------------------------------------------
	public static List<Ticket> listDenied() {
		Statement stmt = null;
		ResultSet set = null;

		List<Ticket> ticketList = new ArrayList<>();

		try (Connection conn = ConnectionFactory.getConnection()) {
			stmt = conn.createStatement();
			set = stmt.executeQuery("select * from tickets where ticket_status = 'denied' order by ticket_id asc");
			boolean hasSet = set.next();
			while (hasSet) {
				Ticket newTicket = new Ticket(set.getInt("ticket_id"), set.getFloat("ticket_amount"),
						set.getString("ticket_description"), set.getString("ticket_status"),
						set.getInt("ticket_user_id"), set.getString("ticket_create_date"));
				ticketList.add(newTicket);

				hasSet = set.next();
			}

		} catch (SQLException e) {

		} finally {
			try {
				stmt.close();
				set.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return ticketList;
	}
//********************* UPDATE METHODS ******************************************************

	public static Ticket updateStatus(Ticket ticketToUpdate,String status) {

		PreparedStatement pstmt = null;
		int ticketId = ticketToUpdate.getTicket_id();
		
		try(Connection conn = ConnectionFactory.getConnection()){
			String SQL = "update tickets set ticket_status = ? where ticket_id="+ticketId;
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, status);
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
		Ticket updatedTicket = TicketRepository.findTicketById(ticketId);
		
		return updatedTicket;
	}

//********************* DELETE METHODS ******************************************************

}
