package com.revature.project1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.revature.model.Person;

import io.javalin.Javalin;
import io.javalin.http.Context;

public class Main {
	
	static Connection conn = null;
	
	public static void main(String[] args) {
		Javalin app = Javalin.create().start(8000);
		
		
		app.before((Context ctx) ->{
			try {
				conn = DriverManager.getConnection(System.getenv("db_url"), System.getenv("db_username"), System.getenv("db_password"));
			}catch(SQLException e) {
				e.printStackTrace();
			}
		});
		app.after((Context ctx)->{
			try {
				conn.close();
				conn = null;
			}catch(SQLException e) {
				e.printStackTrace();
			}
		});
		//------------------------------------------------------
		// generate a new user
		app.post("/register", (Context ctx) -> {
			
			System.out.println("register");
		});
		//------------------------------------------------------
		// create session instance
		app.get("/login", (Context ctx) -> {
			System.out.println("login");
		});
		//------------------------------------------------------
		// remove session instance
		app.get("/logout", (Context ctx) -> {
			System.out.println("logout");
		});
		//------------------------------------------------------
		// get tickets for a specific session user
		app.get("/tickets/employee", (Context ctx) -> {
			System.out.println("employee view");
			
		});
		//------------------------------------------------------
		// create tickets
		app.post("/tickets/create", (Context ctx) -> {
			System.out.println("create ticket");
		});
		//------------------------------------------------------
		// view all tickets
		app.get("tickets/view", (Context ctx) -> {
			Statement stmt = null;
			ResultSet set = null;
			
			List<Ticket> ticketList = new ArrayList<>();
			
			try {
				stmt = conn.createStatement();
				set = stmt.executeQuery("select * from tickets order by ticket_id asc");
				set.next();
				
				while (true) {
					Ticket newTicket = new Ticket(
							set.getInt("ticket_id"),
							set.getFloat("ticket_amount"),
							set.getString("ticket_description"),
							set.getString("ticket_status"),
							set.getInt("ticket_user_id"),
							set.getString("ticket_create_date")
							);
					ticketList.add(newTicket);
					
					if (set.isLast()) {
						break;
					}else {
						set.next();
					}
				}
					
			}catch(SQLException e) {
				
			}finally {
				try {
					stmt.close();
					set.close();
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
			
			System.out.println("master view");
			
			if (ticketList.size() == 0) {
				ctx.result("No results");
			}else {
				ctx.json(ticketList);
			}
		});
		//--------------------------------------------------------
		// view all pending tickets
		app.get("tickets/view/pending", (Context ctx) -> {
			Statement stmt = null;
			ResultSet set = null;
			
			List<Ticket> ticketList = new ArrayList<>();
			
			try {
				stmt = conn.createStatement();
				set = stmt.executeQuery("select * from tickets where ticket_status = 'pending' order by ticket_id asc");
				set.next();
				
				while (true) {
					Ticket newTicket = new Ticket(
							set.getInt("ticket_id"),
							set.getFloat("ticket_amount"),
							set.getString("ticket_description"),
							set.getString("ticket_status"),
							set.getInt("ticket_user_id"),
							set.getString("ticket_create_date")
							);
					ticketList.add(newTicket);
					
					if (set.isLast()) {
						break;
					}else {
						set.next();
					}
				}
					
			}catch(SQLException e) {
				
			}finally {
				try {
					stmt.close();
					set.close();
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
			
			System.out.println("pending view");
			
			if (ticketList.size() == 0) {
				ctx.result("No results");
			}else {
				ctx.json(ticketList);
			}
		});
		//-------------------------------------------------------------------------
		// view all approved tickets
		app.get("tickets/view/approved", (Context ctx) -> {
			Statement stmt = null;
			ResultSet set = null;
			
			List<Ticket> ticketList = new ArrayList<>();
			
			try {
				stmt = conn.createStatement();
				set = stmt.executeQuery("select * from tickets where ticket_status = 'approved' order by ticket_id asc");
				set.next();
				
				while (true) {
					Ticket newTicket = new Ticket(
							set.getInt("ticket_id"),
							set.getFloat("ticket_amount"),
							set.getString("ticket_description"),
							set.getString("ticket_status"),
							set.getInt("ticket_user_id"),
							set.getString("ticket_create_date")
							);
					ticketList.add(newTicket);
					
					if (set.isLast()) {
						break;
					}else {
						set.next();
					}
				}
					
			}catch(SQLException e) {
				
			}finally {
				try {
					stmt.close();
					set.close();
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
			
			System.out.println("approved view");
			
			if (ticketList.size() == 0) {
				ctx.result("No results");
			}else {
				ctx.json(ticketList);
			}
			
			
		});
		//-------------------------------------------------------------------
		// view all denied tickets
		app.get("tickets/view/denied", (Context ctx) -> {
			Statement stmt = null;
			ResultSet set = null;
			
			List<Ticket> ticketList = new ArrayList<>();
			
			try {
				stmt = conn.createStatement();
				set = stmt.executeQuery("select * from tickets where ticket_status = 'denied' order by ticket_id asc");
				set.next();
				
				while (true) {
					Ticket newTicket = new Ticket(
							set.getInt("ticket_id"),
							set.getFloat("ticket_amount"),
							set.getString("ticket_description"),
							set.getString("ticket_status"),
							set.getInt("ticket_user_id"),
							set.getString("ticket_create_date")
							);
					ticketList.add(newTicket);
					
					if (set.isLast()) {
						break;
					}else {
						set.next();
					}
				}
					
			}catch(SQLException e) {
				
			}finally {
				try {
					stmt.close();
					set.close();
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
			
			System.out.println("denied view");
			
			if (ticketList.size() == 0) {
				ctx.result("No results");
			}else {
				ctx.json(ticketList);
			}
		});
		//-------------------------------------------------------------------
		// update ticket status
		app.put("tickets/{ticket_id}", (Context ctx) -> {
			System.out.println("update ticket");
		});
	}
	
}
