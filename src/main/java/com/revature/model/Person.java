package com.revature.model;

import java.util.Objects;

public class Person {

	private int person_id;
	private String person_name;
	private String person_state;
	private String person_age;
	private int age;
	
	public Person() {
		super();
	}

	public Person(int person_id, String person_name, String person_state, String person_age, int age) {
		super();
		this.person_id = person_id;
		this.person_name = person_name;
		this.person_state = person_state;
		this.person_age = person_age;
		this.age = age;
	}

	public int getPerson_id() {
		return person_id;
	}

	public void setPerson_id(int person_id) {
		this.person_id = person_id;
	}

	public String getPerson_name() {
		return person_name;
	}

	public void setPerson_name(String person_name) {
		this.person_name = person_name;
	}

	public String getPerson_state() {
		return person_state;
	}

	public void setPerson_state(String person_state) {
		this.person_state = person_state;
	}

	public String getPerson_age() {
		return person_age;
	}

	public void setPerson_age(String person_age) {
		this.person_age = person_age;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public int hashCode() {
		return Objects.hash(age, person_age, person_id, person_name, person_state);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		return age == other.age && Objects.equals(person_age, other.person_age) && person_id == other.person_id
				&& Objects.equals(person_name, other.person_name) && Objects.equals(person_state, other.person_state);
	}

	@Override
	public String toString() {
		return "Person [person_id=" + person_id + ", person_name=" + person_name + ", person_state=" + person_state
				+ ", person_age=" + person_age + ", age=" + age + "]";
	}
	
	
	
}
