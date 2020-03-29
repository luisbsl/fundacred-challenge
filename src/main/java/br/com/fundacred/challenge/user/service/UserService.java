package br.com.fundacred.challenge.user.service;

import static br.com.fundacred.challenge.auth.service.function.AuthServiceFunctions.generateUmodifiedUserBodyResponse;
import static br.com.fundacred.challenge.user.service.function.UserServiceFunctions.getUserFromDatabase;
import static br.com.fundacred.challenge.user.service.function.UserServiceFunctions.validateToken;

import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fundacred.challenge.auth.controller.dto.UserRestBodyResponse;
import br.com.fundacred.challenge.model.User;
import br.com.fundacred.challenge.user.repository.UserRepository;

/**
 * 
 * @author luisbsl
 *
 */
@Service
public class UserService {

	@Autowired
	UserRepository userRepository;
	
	public UserRestBodyResponse profile(final String id, final String token) {
		return 
				getUserFromDatabase(userRepository)
				.andThen(validateToken(token))
				.andThen(generateUmodifiedUserBodyResponse())
				.apply(id);
	}
	
	public Function<User, User> save() {
		return userRepository::save;
	}

	public Optional<User> findByEmail(final String email) {
		return userRepository.findByEmail(email);
	}
}
