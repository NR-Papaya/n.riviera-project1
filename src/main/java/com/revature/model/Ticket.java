package com.revature.model;

import java.util.Objects;

public class Ticket {
	private int ticket_id;
	private float ticket_amount;
	private String ticket_description;
	private String ticket_status = "pending";
	private int ticket_user_id;
	private String ticket_create_date = null;
	
	
	public Ticket() {
		super();
	}
	
	
	
	public Ticket(float ticket_amount, String ticket_description) {
		super();
		this.ticket_amount = ticket_amount;
		this.ticket_description = ticket_description;
	}



	public Ticket(float ticket_amount, String ticket_description, int ticket_user_id) {
		super();
		this.ticket_amount = ticket_amount;
		this.ticket_description = ticket_description;
		this.ticket_user_id = ticket_user_id;
	}



	public Ticket(float ticket_amount, String ticket_description, String ticket_status, int ticket_user_id) {
		super();
		this.ticket_amount = ticket_amount;
		this.ticket_description = ticket_description;
		this.ticket_status = ticket_status;
		this.ticket_user_id = ticket_user_id;
	}

	public Ticket(int ticket_id, float ticket_amount, String ticket_description, String ticket_status,
			int ticket_user_id, String ticket_create_date) {
		super();
		this.ticket_id = ticket_id;
		this.ticket_amount = ticket_amount;
		this.ticket_description = ticket_description;
		this.ticket_status = ticket_status;
		this.ticket_user_id = ticket_user_id;
		this.ticket_create_date = ticket_create_date;
	}

	public int getTicket_id() {
		return ticket_id;
	}

	public void setTicket_id(int ticket_id) {
		this.ticket_id = ticket_id;
	}

	public float getTicket_amount() {
		return ticket_amount;
	}

	public void setTicket_amount(float ticket_amount) {
		this.ticket_amount = ticket_amount;
	}

	public String getTicket_description() {
		return ticket_description;
	}

	public void setTicket_description(String ticket_description) {
		this.ticket_description = ticket_description;
	}

	public String getTicket_status() {
		return ticket_status;
	}

	public void setTicket_status(String ticket_status) {
		this.ticket_status = ticket_status;
	}

	public int getTicket_user_id() {
		return ticket_user_id;
	}

	public void setTicket_user_id(int ticket_user_id) {
		this.ticket_user_id = ticket_user_id;
	}

	public String getTicket_create_date() {
		return ticket_create_date;
	}

	public void setTicket_create_date(String ticket_create_date) {
		this.ticket_create_date = ticket_create_date;
	}

	@Override
	public int hashCode() {
		return Objects.hash(ticket_amount, ticket_create_date, ticket_description, ticket_id, ticket_status,
				ticket_user_id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ticket other = (Ticket) obj;
		return Float.floatToIntBits(ticket_amount) == Float.floatToIntBits(other.ticket_amount)
				&& Objects.equals(ticket_create_date, other.ticket_create_date)
				&& Objects.equals(ticket_description, other.ticket_description) && ticket_id == other.ticket_id
				&& Objects.equals(ticket_status, other.ticket_status)
				&& Objects.equals(ticket_user_id, other.ticket_user_id);
	}

	@Override
	public String toString() {
		return "Ticket [ticket_id=" + ticket_id + ", ticket_amount=" + ticket_amount + ", ticket_description="
				+ ticket_description + ", ticket_status=" + ticket_status + ", ticket_user_id=" + ticket_user_id
				+ ", ticket_create_date=" + ticket_create_date + "]";
	}


}
