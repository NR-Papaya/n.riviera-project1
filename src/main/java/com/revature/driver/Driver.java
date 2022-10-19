package com.revature.driver;

import java.security.Key;
import java.sql.Connection;

import java.util.List;

import org.eclipse.jetty.http.HttpStatus;

import com.revature.model.Ticket;
import com.revature.model.User;
import com.revature.repository.TicketRepository;
import com.revature.repository.UserRepository;
import com.revature.utils.JwtFactory;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;

public class Driver {

	static Connection conn = null;

	public static void main(String[] args) {
		Javalin app = Javalin.create().start(8000);

		// ------------------------------------------------------
		// generate a new user
		app.post("/register", (Context ctx) -> {

			User newUser = ctx.bodyAsClass(User.class);

			if (!UserRepository.availableUserName(newUser.getUserName())) {
				ctx.result("user_name unavailable");
			} else if (UserRepository.isValidUserObj(newUser)) {
				UserRepository.addUser(newUser);
				ctx.result("User Created");
			} else {
				ctx.result("Invalid Input");
			}
			System.out.println("register");
		});
		// ------------------------------------------------------
		// create session instance
		app.post("/login", (Context ctx) -> {

			User loginUser = ctx.bodyAsClass(User.class);

			User authenticatedUser = UserRepository.authenticateUser(loginUser);

			if (authenticatedUser.getUser_id() > 0) {
				ctx.cookie("jwt", JwtFactory.jwsStringFactory(authenticatedUser)).result("Login Successful");
			} else {
				ctx.result("Invalid Credentials").status(HttpStatus.UNAUTHORIZED_401);
			}
			System.out.println("login");
		});
		// ------------------------------------------------------
		// remove session instance
		app.get("/logout", (Context ctx) -> {
			Cookie[] cookies = ctx.req().getCookies();

			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("jwt")) {
					cookie.setMaxAge(0);
					ctx.res().addCookie(cookie);
					ctx.result("Logout Successful");
					break;
				}
			}
			System.out.println("logout");
		});
		// ------------------------------------------------------
		// get tickets for a specific session user
		app.get("/tickets/employee", (Context ctx) -> {
			String idString = JwtFactory.parseJwtBody(ctx.cookie("jwt"), "user_id");
			String roleString = JwtFactory.parseJwtBody(ctx.cookie("jwt"), "role");
			if (!idString.equals("invalid") && roleString.equals("Employee")) {
				int id = Integer.parseInt(idString);
				if (UserRepository.validateUserId(id)) {
					ctx.json(TicketRepository.listByUser(id));
				} else {
					ctx.result("Bad request").status(404);
				}
			} else {
				ctx.result("Unauthorized").status(HttpStatus.UNAUTHORIZED_401);
			}
			System.out.println("employee view");
		});
		// ------------------------------------------------------
		// create tickets
		app.post("/tickets/create", (Context ctx) -> {

			String roleString = JwtFactory.parseJwtBody(ctx.cookie("jwt"), "role");
			String idString = JwtFactory.parseJwtBody(ctx.cookie("jwt"), "user_id");
			
			if (roleString.equals("Employee")) {
				Ticket newTicket = ctx.bodyAsClass(Ticket.class);
				newTicket.setTicket_user_id(Integer.parseInt(idString));

				if (TicketRepository.isValidTicket(newTicket)) {
					TicketRepository.addTicket(newTicket);
					ctx.result("Ticket Created").status(HttpStatus.CREATED_201);
				} else {
					ctx.result("Invalid Ticket").status(HttpStatus.NOT_ACCEPTABLE_406);
				}
			} else {
				ctx.result("Unauthorized").status(HttpStatus.UNAUTHORIZED_401);
			}

			System.out.println("create ticket");
		});
		// ------------------------------------------------------
		// view all tickets
		app.get("tickets/view", (Context ctx) -> {
			String roleString = JwtFactory.parseJwtBody(ctx.cookie("jwt"), "role");
			if (!roleString.equals("invalid") && roleString.equals("Manager")) {
				List<Ticket> ticketList = TicketRepository.listAll();

				if (ticketList.size() == 0) {
					ctx.result("No results");
				} else {
					ctx.json(ticketList);
				}
			} else {
				ctx.result("Unauthorized").status(HttpStatus.UNAUTHORIZED_401);
			}
			System.out.println("master view");
		});
		// --------------------------------------------------------
		// view all pending tickets
		app.get("tickets/view/pending", (Context ctx) -> {

			String roleString = JwtFactory.parseJwtBody(ctx.cookie("jwt"), "role");
			if (!roleString.equals("invalid") && roleString.equals("Manager")) {
				List<Ticket> ticketList = TicketRepository.listPending();

				if (ticketList.size() == 0) {
					ctx.result("No results");
				} else {
					ctx.json(ticketList);
				}
			} else {
				ctx.result("Unauthorized").status(HttpStatus.UNAUTHORIZED_401);
			}
			System.out.println("pending view");
		});
		// -------------------------------------------------------------------------
		// view all approved tickets
		app.get("tickets/view/approved", (Context ctx) -> {

			String roleString = JwtFactory.parseJwtBody(ctx.cookie("jwt"), "role");
			if (!roleString.equals("invalid") && roleString.equals("Manager")) {
				List<Ticket> ticketList = TicketRepository.listApproved();

				if (ticketList.size() == 0) {
					ctx.result("No results");
				} else {
					ctx.json(ticketList);
				}
			} else {
				ctx.result("Unauthorized").status(HttpStatus.UNAUTHORIZED_401);
			}
			System.out.println("approved view");
		});
		// -------------------------------------------------------------------
		// view all denied tickets
		app.get("tickets/view/denied", (Context ctx) -> {

			String roleString = JwtFactory.parseJwtBody(ctx.cookie("jwt"), "role");
			if (!roleString.equals("invalid") && roleString.equals("Manager")) {
				List<Ticket> ticketList = TicketRepository.listDenied();

				if (ticketList.size() == 0) {
					ctx.result("No results");
				} else {
					ctx.json(ticketList);
				}
			} else {
				ctx.result("Unauthorized").status(HttpStatus.UNAUTHORIZED_401);
			}
			System.out.println("denied view");
		});
		// -------------------------------------------------------------------
		// update ticket status
		// query with a key of status must be affixed to url. ("approved" or "denied")
		app.put("tickets/{ticket_id}", (Context ctx) -> {

			String roleString = JwtFactory.parseJwtBody(ctx.cookie("jwt"), "role");

			if (!roleString.equals("invalid") && roleString.equals("Manager")) {
				int ticketID = Integer.parseInt(ctx.pathParam("ticket_id"));
				String queryStatus = ctx.queryParam("status");

				if (TicketRepository.validateTicketId(ticketID) && TicketRepository.validateUpdateStatus(queryStatus)) {
					Ticket ticket = TicketRepository.findTicketById(ticketID);

					if (TicketRepository.validateTicketStatus(ticket)) {
						ticket = TicketRepository.updateStatus(ticket, queryStatus);
						ctx.json(ticket).status(HttpStatus.OK_200);
					} else {
						ctx.result("Ticket Status Immutable").status(HttpStatus.BAD_REQUEST_400);
					}
				} else {
					ctx.result("Invalid input").status(HttpStatus.BAD_REQUEST_400);
				}

			} else {
				ctx.result("Unauthorized").status(HttpStatus.UNAUTHORIZED_401);
			}
			System.out.println("update ticket");
		});
	}

}
