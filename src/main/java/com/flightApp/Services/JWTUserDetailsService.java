package com.flightApp.Services;

import java.util.ArrayList;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.flightApp.DTOs.UserDto;
import com.flightApp.Entities.AuthEntity;
import com.flightApp.exception.UserAlreadyExistException;
import com.flightApp.repositories.AuthRepository;

import lombok.extern.log4j.Log4j2;

/**
 * JWTUserDetailsService get and set the users
 *
 */
@Service
@Log4j2
public class JWTUserDetailsService implements UserDetailsService {

	private final AuthRepository authRepository;

	private final ModelMapper modelMapper;

	public JWTUserDetailsService(AuthRepository authRepository, ModelMapper modelMapper) {
		this.authRepository = authRepository;
		this.modelMapper = modelMapper;
	}

	/**
	 * JWTUserDetailsService get and set the users
	 *
	 */
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		log.info("called loadUserByUsername");
		Optional<AuthEntity> resp = authRepository.findByUserName(userName);
		if (resp.isPresent()) {
			AuthEntity user = resp.get();
			return new User(user.getUserName(), user.getPassword(), new ArrayList<>());
		} else {
			throw new UsernameNotFoundException("User not found with username: " + userName);
		}

	}

	/**
	 * bcryptPassword
	 *
	 */
	public static String bcryptPassword(String password) {
		log.info("called bcryptPassword");
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(password);
	}

	/**
	 * find user from DB
	 *
	 */
	public UserDto findUser(String userName) {
		log.info("called findUser");
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		Optional<AuthEntity> authEntity = authRepository.findByUserName(userName);

		if (authEntity.isPresent()) {
			return modelMapper.map(authEntity.get(), UserDto.class);
		} else {
			log.error("Error fetching user");
			return null;
		}
	}

	/**
	 * register new user
	 */
	public String registerUser(UserDto userDto) {
		if (checkIfUserExist(userDto.getUserName())) {
			throw new UserAlreadyExistException("User already exists for this userName");
		}
		AuthEntity authEntity = new AuthEntity();
		authEntity.setFirstName(userDto.getFirstName());
		authEntity.setLastName(userDto.getLastName());
		authEntity.setRole("USER");
		authEntity.setUserName(userDto.getUserName());
		authEntity.setPassword(bcryptPassword(userDto.getPassword()));
		authRepository.save(authEntity);
		return "user Created Successfully";
	}

	/**
	 * check is user already registered
	 */
	public boolean checkIfUserExist(String userName) {
		Optional<AuthEntity> user =  authRepository.findByUserName(userName);
		return user.isPresent() ? true :false;
	}
}
