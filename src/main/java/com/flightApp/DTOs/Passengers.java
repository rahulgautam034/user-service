package com.flightApp.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * travellers detail modal
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class Passengers {
	
	private String name;

	private int age;

	private String gender;
	
}