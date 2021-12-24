package com.flightApp.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * user registration/login exception handling
 */
@AllArgsConstructor
@Getter
@Setter
public class UserAlreadyExistException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5381238530158969646L;
	private String message;

}
