package com.flightApp.ui;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * return model if exception occured
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ErrorResponseModel {

	private HttpStatus code;

	private String message;
	
    private Long errorReportingTime;


}
