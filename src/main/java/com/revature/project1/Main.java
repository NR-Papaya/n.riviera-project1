package com.revature.project1;

import io.javalin.Javalin;
import io.javalin.http.Context;

public class Main {

	public static void main(String[] args) {
		Javalin app = Javalin.create().start(8000);
		
		//generate a new user
		app.post("/register", (Context ctx) -> {
			System.out.println("register");
		});

		//create session instance
		app.get("/login", (Context ctx) -> {
			System.out.println("login");
		});
		
		//remove session instance
		app.get("/logout", (Context ctx) -> {
			System.out.println("logout");
		});

		//get tickets for a specific session user
		app.get("/tickets/employee", (Context ctx) -> {
			System.out.println("employee view");
		});
		
		// create tickets
		app.post("/tickets/create", (Context ctx) -> {
			System.out.println("create ticket");
		});

		// view all pending tickets
		app.get("tickets/view", (Context ctx) -> {
			System.out.println("master view");
		});

		// update ticket status
		app.put("tickets/{ticket_id}", (Context ctx) -> {
			System.out.println("update ticket");
		});
	}
}
