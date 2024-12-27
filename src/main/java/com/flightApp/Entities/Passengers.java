package com.flightApp.Entities;

import jakarta.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * for passengers table
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Embeddable

public class Passengers {

	private String name;

	private int age;

	private String gender;
	
}