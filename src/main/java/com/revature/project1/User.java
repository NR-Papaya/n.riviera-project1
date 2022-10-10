package com.revature.project1;

import java.util.Objects;

public class User {
	private String user_name;
	private String role = "Employee";
	private String password;
	private String f_name;
	private String l_name;
	
	public User(String user_name, String password, String f_name, String l_name) {
		super();
		this.user_name = user_name;
		this.password = password;
		this.f_name = f_name;
		this.l_name = l_name;
	}

	public User(String user_name, String role, String password, String f_name, String l_name) {
		super();
		this.user_name = user_name;
		this.role = role;
		this.password = password;
		this.f_name = f_name;
		this.l_name = l_name;
	}

	public String getUserName() {
		return user_name;
	}

	public void setUserName(String user_name) {
		this.user_name = user_name;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getF_name() {
		return f_name;
	}

	public void setF_name(String f_name) {
		this.f_name = f_name;
	}

	public String getL_name() {
		return l_name;
	}

	public void setL_name(String l_name) {
		this.l_name = l_name;
	}

	@Override
	public int hashCode() {
		return Objects.hash(f_name, l_name, password, role, user_name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(f_name, other.f_name) && Objects.equals(l_name, other.l_name)
				&& Objects.equals(password, other.password) && Objects.equals(role, other.role)
				&& Objects.equals(user_name, other.user_name);
	}

	@Override
	public String toString() {
		return "User [user_name=" + user_name + ", role=" + role + ", f_name=" + f_name + ", l_name=" + l_name + "]";
	}
	

	

	
}
