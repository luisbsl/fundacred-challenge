package br.com.fundacred.challenge.auth.service;

import static br.com.fundacred.challenge.auth.service.function.AuthServiceFunctions.generateModifiedUserBodyResponse;
import static br.com.fundacred.challenge.auth.service.function.AuthServiceFunctions.generateRegisteredUserBodyResponse;
import static br.com.fundacred.challenge.auth.service.function.AuthServiceFunctions.generateUser;
import static br.com.fundacred.challenge.auth.service.function.AuthServiceFunctions.getUserFromDatabase;
import static br.com.fundacred.challenge.auth.service.function.AuthServiceFunctions.updateUser;
import static br.com.fundacred.challenge.auth.service.function.AuthServiceFunctions.validateEmailConflict;
import static br.com.fundacred.challenge.auth.service.function.AuthServiceFunctions.validateModelConstraints;
import static br.com.fundacred.challenge.auth.service.function.AuthServiceFunctions.validatePasswordMatching;
import static br.com.fundacred.challenge.user.service.function.UserServiceFunctions.save;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fundacred.challenge.auth.controller.dto.SigninRestBodyRequest;
import br.com.fundacred.challenge.auth.controller.dto.SignupRestBodyRequest;
import br.com.fundacred.challenge.auth.controller.dto.UserRestBodyResponse;
import br.com.fundacred.challenge.user.service.UserService;

/**
 * 
 * @author luisbsl
 *
 */
@Service
public class AuthService {

	@Autowired
	private UserService userService;
	
	public UserRestBodyResponse signup(final SignupRestBodyRequest signupRestBodyRequest) {
		return validateModelConstraints()
					.andThen(validateEmailConflict(userService))
					.andThen(generateUser())
					.andThen(save(userService))
					.andThen(generateRegisteredUserBodyResponse())
					.apply(signupRestBodyRequest);
	}

	public UserRestBodyResponse signin(final SigninRestBodyRequest signinRestBodyRequest) {		
		var user = validateModelConstraints()
					.andThen(getUserFromDatabase(userService, signinRestBodyRequest.getEmail()))
					.apply(signinRestBodyRequest);
		
		return validatePasswordMatching(signinRestBodyRequest.getPassword())
					.andThen(generateModifiedUserBodyResponse().compose(updateUser().andThen(save(userService))))
					.apply(user);
	}

}
