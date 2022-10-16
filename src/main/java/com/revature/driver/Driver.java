package com.revature.driver;

import java.sql.Connection;

import java.util.List;

import com.revature.model.Ticket;
import com.revature.model.TicketUser;
import com.revature.repository.TicketRepository;
import com.revature.repository.UserRepository;

import io.javalin.Javalin;
import io.javalin.http.Context;

public class Driver {

	static Connection conn = null;

	public static void main(String[] args) {
		Javalin app = Javalin.create().start(8000);

		//------------------------------------------------------
		// generate a new user
		app.post("/register", (Context ctx) -> {
			
			TicketUser newUser = ctx.bodyAsClass(TicketUser.class);
			
			if (!UserRepository.availableUserName(newUser.getUserName())) {
				ctx.result("user_name unavailable");
			}else if(UserRepository.isValidUserObj(newUser)){
					UserRepository.addUser(newUser);
					ctx.result("User Created");
			}else {
					ctx.result("Invalid Input");
				}
			
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
			/*
			 * validate using session to get employee id
			 * get employee id
			 * query all tickets using validated employee id
			 * ctx json collection object with all tickets under the id 
			 */
			int id = 2; //*******IMPLEMENT USING SESSION TO GET ID
			if (UserRepository.validateUserId(id)) {
				ctx.json(TicketRepository.listByUser(id));
			}else {
				ctx.result("Bad request").status(404);
			}
			
			
			System.out.println("employee view");

		});
		//------------------------------------------------------
		// create tickets
		app.post("/tickets/create", (Context ctx) -> {
			
			Ticket newTicket = ctx.bodyAsClass(Ticket.class);
			
			if (TicketRepository.isValidTicket(newTicket)) {
				TicketRepository.addTicket(newTicket);
				ctx.result("Ticket Created");
			}else {
				ctx.result("Invalid Ticket");
			}
			
			System.out.println("create ticket");
		});
		//------------------------------------------------------
		// view all tickets
		app.get("tickets/view", (Context ctx) -> {

			List<Ticket> ticketList = TicketRepository.listAll();

			
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
			List<Ticket> ticketList = TicketRepository.listPending();
			
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

			List<Ticket> ticketList = TicketRepository.listApproved();

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

			List<Ticket> ticketList = TicketRepository.listDenied();

			System.out.println("denied view");

			if (ticketList.size() == 0) {
				ctx.result("No results");
			}else {
				ctx.json(ticketList);
			}
		});
		//-------------------------------------------------------------------
		// update ticket status
		// query with a key of status must be affixed to url. ("approved" or "denied")
		app.put("tickets/{ticket_id}", (Context ctx) -> {
			int ticketID = Integer.parseInt(ctx.pathParam("ticket_id"));
			String queryStatus = ctx.queryParam("status");
			
			
			if (TicketRepository.validateTicketId(ticketID) && TicketRepository.validateUpdateStatus(queryStatus)) { 
				Ticket ticket = TicketRepository.findTicketById(ticketID);
				
				if (TicketRepository.validateTicketStatus(ticket)) {
					ticket = TicketRepository.updateStatus(ticket, queryStatus);
					ctx.json(ticket);
				}else {
					ctx.result("Ticket Status Immutable");
				}
				
				
			}else {
				ctx.result("Invalid input");
			}
			System.out.println("update ticket");
		});
	}

}
