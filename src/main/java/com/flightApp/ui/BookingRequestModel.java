package com.flightApp.ui;

import java.util.ArrayList;
import java.util.List;

import com.flightApp.DTOs.Passengers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * BookingRequestModel receive from user for ticket booking
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class BookingRequestModel {

	private String flightName;

	private String flightId;

	private String date;

	private String source;

	private String destination;

	private String startTime;

	private String endTime;

	private String name;

	private String email;

	private int totalSeats;

	private String mealType;

	private List<Passengers> passengers = new ArrayList<>();

}
