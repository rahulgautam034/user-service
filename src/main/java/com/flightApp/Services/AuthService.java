package com.flightApp.Services;

import com.flightApp.DTOs.AuthDTO;
import com.flightApp.Entities.AuthEntity;

/**
 * authentication methods
 *
 */
public interface AuthService {

	public AuthEntity validateUser(AuthDTO user);

}
