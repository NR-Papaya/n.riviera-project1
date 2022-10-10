package com.revature.project1;

import java.util.Objects;

public class Ticket {
	private float amount;
	private String description;
	private String status = "pending";
	private String user_id;
	
	
	
	public Ticket(float amount, String description, String user_id) {
		super();
		this.amount = amount;
		this.description = description;
		this.user_id = user_id;
	}

	public Ticket(float amount, String description, String status, String user_id) {
		super();
		this.amount = amount;
		this.description = description;
		this.status = status;
		this.user_id = user_id;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUserName() {
		return user_id;
	}

	public void setUserName(String user_id) {
		this.user_id = user_id;
	}

	@Override
	public String toString() {
		return "Ticket [amount=" + amount + ", description=" + description + ", status=" + status + ", user_id="
				+ user_id + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(amount, description, status, user_id);
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
		return Float.floatToIntBits(amount) == Float.floatToIntBits(other.amount)
				&& Objects.equals(description, other.description) && Objects.equals(status, other.status)
				&& Objects.equals(user_id, other.user_id);
	}
	
	
}
