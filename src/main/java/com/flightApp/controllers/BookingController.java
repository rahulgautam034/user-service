package com.flightApp.controllers;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.flightApp.DTOs.BookingDto;
import com.flightApp.DTOs.FlightDto;
import com.flightApp.proxy.BookingProxy;
import com.flightApp.ui.BookingRequestModel;
import com.flightApp.ui.BookingResponseModel;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.log4j.Log4j2;

/**
 * for ticket booking api's
 *
 */
@RestController
@RequestMapping("/api/user/v1.0/flight")
@CrossOrigin(origins = "*")
@Log4j2
public class BookingController {

	private final ModelMapper modelMapper;

	private final BookingProxy bookingProxy;

	@Autowired
	public BookingController(ModelMapper modelMapper, BookingProxy bookingProxy) {
		this.modelMapper = modelMapper;
		this.bookingProxy = bookingProxy;
	}

	/**
	 * search Flights based on given parameters
	 *
	 */
	@GetMapping("/search/")
	@CircuitBreaker(name = "myCircuitBreaker", fallbackMethod = "myTestFallBack")
	public ResponseEntity<List<FlightDto>> searchFlights(@RequestParam String source, @RequestParam String destination,
			@RequestParam String date, @RequestParam String seatType) {
		log.info("called searchFlights");
		List<FlightDto> searchedFlights = bookingProxy.searchFlights(source, destination, date, seatType);
		return ResponseEntity.status(HttpStatus.OK).body(searchedFlights);

	}

	/**
	 * update flight after book ticket
	 *
	 */
	@PutMapping("/")
	@CircuitBreaker(name = "myCircuitBreaker", fallbackMethod = "myTestFallBack")
	public ResponseEntity<FlightDto> updateFlight(@RequestParam String flightId, @RequestParam int seats,
			@RequestParam String seatType) {
		log.info("called updateFlight");
		System.out.println("called searchFlights");
		FlightDto res = bookingProxy.updateFlight(flightId, seats, seatType);
		return ResponseEntity.status(HttpStatus.OK).body(res);

	}

	/**
	 * book flight for user
	 *
	 */
	@PostMapping("/booking")
	@CircuitBreaker(name = "myCircuitBreaker", fallbackMethod = "myTestFallBack")
	public ResponseEntity<BookingResponseModel> bookFlight(@RequestBody BookingRequestModel bookingRequestModel) {
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		log.info("called bookFlight");

		BookingDto bookingDto = modelMapper.map(bookingRequestModel, BookingDto.class);
		BookingDto res = bookingProxy.bookFlight(bookingDto);

		return ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(res, BookingResponseModel.class));

	}

	/**
	 * get tickets by email id
	 *
	 */
	@GetMapping("/booking/{email}")
	@CircuitBreaker(name = "myCircuitBreaker", fallbackMethod = "myTestFallBack")
	public ResponseEntity<List<BookingDto>> getUserTickets(@PathVariable String email) {
		log.info("called getUserTickets");

		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		List<BookingDto> res = bookingProxy.getUserTickets(email);

		return ResponseEntity.status(HttpStatus.OK).body(res);

	}

	/**
	 * cancel ticket by pnr number
	 *
	 */
	@PutMapping("/booking/cancel/{pnrNumber}")
	@CircuitBreaker(name = "myCircuitBreaker", fallbackMethod = "myTestFallBack")
	public ResponseEntity<BookingDto> cancelTicket(@PathVariable String pnrNumber) {
		log.info("called cancelTicket");
		BookingDto res = bookingProxy.cancelTicket(pnrNumber);
		return ResponseEntity.status(HttpStatus.OK).body(res);

	}

	/**
	 * find ticket by pnr Number
	 *
	 */
	@GetMapping("/ticket/{pnrNumber}")
	@CircuitBreaker(name = "myCircuitBreaker", fallbackMethod = "myTestFallBack")
	public ResponseEntity<BookingDto> findTicket(@PathVariable String pnrNumber) {
		log.info("called findTicket");
		BookingDto res = bookingProxy.findTicket(pnrNumber);
		return ResponseEntity.status(HttpStatus.OK).body(res);

	}

	/**
	 * return error message if external service is down or not started
	 *
	 */
	public ResponseEntity<?> myTestFallBack(Exception e) {
		log.info("called myTestFallBack");
		return ResponseEntity.ok("within myTestFallBack method. FLIGHT-WS is down" + e.toString());
	}
}
