 /**
 * 
 */
package com.epood.model.domain;

/**
 * @author J
 *
 */
public class Customer {

	private int customer;
	
	private String firstName;
	
	private String lastName;
	
	private String username;
	
	private String password;

	/**
	 * @return the customer
	 */
	public int getCustomerId() {
		return customer;
	}

	/**
	 * @param customer the customer to set
	 */
	public void setCustomerId(int customer) {
		this.customer = customer;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
