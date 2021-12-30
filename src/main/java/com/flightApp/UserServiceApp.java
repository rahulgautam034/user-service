package com.flightApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * main class
 *
 */

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class UserServiceApp {
	
	/**
	 * main method
	 *
	 */
	public static void main(String[] args) {
		SpringApplication.run(UserServiceApp.class, args);
	}

}
