package com.flightApp.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * used to return current logged in user response
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDto {
	
	private Integer id;

	private String firstName;

	private String lastName;

	private String role;
	
	private String userName;
	
	private String password;

}
