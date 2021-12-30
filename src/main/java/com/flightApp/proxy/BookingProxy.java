package com.flightApp.proxy;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.flightApp.DTOs.BookingDto;
import com.flightApp.DTOs.FlightDto;

/**
 * used to connect FLIGHT-WS
 *
 */
@FeignClient(value = "FLIGHT-WS")
public interface BookingProxy {

	/**
	 * book new flight
	 * @param bookingDto
	 * @return
	 */
	@PostMapping("/booking")
	public BookingDto bookFlight(@RequestBody BookingDto bookingDto);

	/**
	 * search flight based on given param's
	 * @param source
	 * @param destination
	 * @param departureDate
	 * @param seatType
	 * @return
	 */
	@GetMapping("/airline/search/")
	public List<FlightDto> searchFlights(@RequestParam String source, @RequestParam String destination,
			@RequestParam String departureDate, @RequestParam String seatType);

	
	/**
	 * cancel ticket using pnr number
	 * @param pnrNumber
	 * @return
	 */
	@PutMapping("/booking/cancel/{pnrNumber}")
	public BookingDto cancelTicket(@PathVariable String pnrNumber);

	/**
	 * find ticket using pnr number
	 * @param pnrNumber
	 * @return
	 */
	@GetMapping("/airline/ticket/{pnrNumber}")
	public BookingDto findTicket(@PathVariable String pnrNumber);

	/**
	 * find all ticket using registered email
	 * @param email
	 * @return
	 */
	@GetMapping("/booking/{email}")
	public List<BookingDto> getUserTickets(@PathVariable String email);

	/**
	 * update flight using given parameters
	 * @param flightId
	 * @param seats
	 * @param seatType
	 * @return
	 */
	@PutMapping("/airline/flight")
	public FlightDto updateFlight(@RequestParam String flightId, @RequestParam int seats,@RequestParam String seatType);
}
