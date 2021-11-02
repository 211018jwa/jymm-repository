package com.revature.models;

import java.util.Objects;

public class Clients {
	
	private int id;
	private String firstName;
	private String lastName;
	
	public Clients() {
		super();
	}

	public Clients(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Clients(int id, String firstName, String lastName) {
		this(firstName, lastName);
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public int hashCode() {
		return Objects.hash(firstName, id, lastName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Clients other = (Clients) obj;
		return Objects.equals(firstName, other.firstName) && id == other.id && Objects.equals(lastName, other.lastName);
	}

	@Override
	public String toString() {
		return "Clients [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + "]";
	}
	
	
	

}
