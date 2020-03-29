package br.com.fundacred.challenge.auth.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fundacred.challenge.auth.controller.dto.RestBodyRequest;
import br.com.fundacred.challenge.auth.controller.dto.RestBodyResponse;
import br.com.fundacred.challenge.auth.controller.dto.SigninRestBodyRequest;
import br.com.fundacred.challenge.auth.controller.dto.SignupRestBodyRequest;
import br.com.fundacred.challenge.auth.controller.dto.UserRestBodyResponse;
import br.com.fundacred.challenge.auth.service.exception.BadRequestException;
import br.com.fundacred.challenge.auth.service.exception.EmailConflictRequestException;
import br.com.fundacred.challenge.auth.service.exception.EmailNotFoundRequestException;
import br.com.fundacred.challenge.auth.service.exception.InvalidCredencialsRequestException;
import br.com.fundacred.challenge.controller.dto.builder.UserRestBodyResponseBuilder;
import br.com.fundacred.challenge.model.User;
import br.com.fundacred.challenge.model.mapper.ModelMapperHelper;
import br.com.fundacred.challenge.user.service.UserService;
import br.com.fundacred.challenge.util.BCryptHelper;

@Service
public class AuthService {

	@Autowired
	private UserService userService;
	
	public UserRestBodyResponse signup(final SignupRestBodyRequest signupRestBodyRequest) {
		return validateModelConstraints()
					.andThen(validateEmailConflict())
					.andThen(generateUser())
					.andThen(save())
					.andThen(generateRegisteredUserBodyResponse())
					.apply(signupRestBodyRequest);
	}

	public UserRestBodyResponse signin(final SigninRestBodyRequest signinRestBodyRequest) {		
		var user = validateModelConstraints()
					.andThen(getUserFromDatabase(signinRestBodyRequest.getEmail()))
					.apply(signinRestBodyRequest);
		
		return validatePasswordMatching(signinRestBodyRequest.getPassword())
					.andThen(generateModifiedUserBodyResponse())
					.apply(user);
	}
	
	public Function<RestBodyRequest, RestBodyRequest> validateEmailConflict() {
		return restBodyRequest -> {
			var userRestBodyRequest = (SignupRestBodyRequest) restBodyRequest;
			userService.findByEmail(userRestBodyRequest.getEmail()).ifPresent(user -> {
				throw new EmailConflictRequestException();
			});
			return restBodyRequest;
		};
	}

	public Function<RestBodyRequest, RestBodyRequest> validateModelConstraints() {
		return signinRestBodyRequest -> {
			ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
			Validator validator = factory.getValidator();

			Set<ConstraintViolation<RestBodyRequest>> violations = validator.validate(signinRestBodyRequest);
			Set<String> errors = new HashSet<>();
			if (!violations.isEmpty()) {
				for (ConstraintViolation<RestBodyRequest> violation : violations) {
					errors.add(violation.getMessage());
				}
				throw new BadRequestException(new RestBodyResponse(errors));
			}
			return signinRestBodyRequest;
		};
	}

	public Function<RestBodyRequest, User> getUserFromDatabase(final String email) {
		return signinRestBodyRequest -> {
			Optional<User> userOptional = userService.findByEmail(email);
			return userOptional.orElseThrow(EmailNotFoundRequestException::new);
		};
	}
	
	public Function<User, User> validatePasswordMatching(final String password) {
		return user -> {
			if (!BCryptHelper.checkHash(password, user.getPassword())) {
				throw new InvalidCredencialsRequestException();
			}
			return user;
		};
	}

	public Function<RestBodyRequest, User> generateUser() {
		return ModelMapperHelper::getUserFromUserRequest;
	}

	public Function<User, User> save() {
		return user -> userService.save(user);
	}

	public Function<User, UserRestBodyResponse> generateRegisteredUserBodyResponse() {
		return user -> new UserRestBodyResponseBuilder()
									.withId(user.getId().toString())
									.withCreated(Optional.empty())
									.withLastLogin(Optional.empty())
									.withToken(user)
									.build();
	}
	
	public Function<User, UserRestBodyResponse> generateModifiedUserBodyResponse() {
		return user -> new UserRestBodyResponseBuilder()
									.withId(user.getId().toString())
									.withCreated(Optional.of(user.getCreated()))
									.withLastLogin(Optional.empty())
									.withModified(Optional.empty())
									.withToken(user)
									.build();
	}

}
