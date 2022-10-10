package com.revature.javalin;

import java.util.*;

import org.eclipse.jetty.http.HttpStatus;

import com.revature.model.Person;

import io.javalin.Javalin;
import io.javalin.http.Context;

public class Driver {
	public static void main(String[] args) {
		//starts server on default port of 8080
		Javalin app = Javalin.create().start(8000); // start(port#)
		
		//Handler 
		
		// path,
		app.get("/person/{id}", (Context ctx) ->{
//			ctx.res().getWriter().write("Hello, Client");
			Set<Person> people = new HashSet<>();
			Person person = new Person(1,"nahia",34);
			Person person1 = new Person(2,"Radwa",39);
			people.add(person);
			people.add(person1);
			
			Person selectedPerson = null;
			
			for (Person p:people) {
				if (p.getId() == Integer.parseInt(ctx.pathParam("id"))) {
					selectedPerson = p;
				}
			}
			
			ctx.json(selectedPerson);
		});
		
		app.post("/new-item", (ctx)->{
			Person receivedPerson = ctx.bodyAsClass(Person.class);
			System.out.println(receivedPerson);
			ctx.status(HttpStatus.CREATED_201);
		});
	}
}
