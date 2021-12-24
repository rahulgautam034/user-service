package com.flightApp.DTOs;

import java.io.Serializable;

/**
 * authentication modal
 *
 */
public class JwtRequest implements Serializable {

	private static final long serialVersionUID = 5926468583005150707L;

	private String username;
	private String password;

	/**
	 * need default constructor for JSON Parsing
	 */
	public JwtRequest() {

	}

	/**
	 * all args constructor
	 *
	 */
	public JwtRequest(String username, String password) {
		this.setUsername(username);
		this.setPassword(password);
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}