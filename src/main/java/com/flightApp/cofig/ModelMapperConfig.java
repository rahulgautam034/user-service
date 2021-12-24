package com.flightApp.cofig;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * model mapper configuration 
 *
 */
@Configuration
public class ModelMapperConfig {
	
	/**
	 * model mapper bean 
	 * @return bean
	 */
	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper;
	}
}
