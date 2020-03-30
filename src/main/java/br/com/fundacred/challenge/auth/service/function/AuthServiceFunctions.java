package br.com.fundacred.challenge.auth.service.function;

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import br.com.fundacred.challenge.auth.controller.dto.RestBodyRequest;
import br.com.fundacred.challenge.auth.controller.dto.RestBodyResponse;
import br.com.fundacred.challenge.auth.controller.dto.SignupRestBodyRequest;
import br.com.fundacred.challenge.auth.controller.dto.UserRestBodyResponse;
import br.com.fundacred.challenge.auth.service.exception.BadRequestException;
import br.com.fundacred.challenge.auth.service.exception.EmailConflictRequestException;
import br.com.fundacred.challenge.auth.service.exception.EmailNotFoundRequestException;
import br.com.fundacred.challenge.auth.service.exception.InvalidCredencialsRequestException;
import br.com.fundacred.challenge.controller.dto.builder.UserRestBodyResponseBuilder;
import br.com.fundacred.challenge.helper.BCryptHelper;
import br.com.fundacred.challenge.helper.JWTTokenHelper;
import br.com.fundacred.challenge.model.User;
import br.com.fundacred.challenge.model.mapper.ModelMapperHelper;
import br.com.fundacred.challenge.user.service.UserService;

/**
 * 
 * @author luisbsl
 *
 */
public interface AuthServiceFunctions {
	
	public static Function<RestBodyRequest, RestBodyRequest> validateEmailConflict(final UserService userService) {
		return restBodyRequest -> {
			var userRestBodyRequest = (SignupRestBodyRequest) restBodyRequest;
			userService.findByEmail().apply(userRestBodyRequest.getEmail()).ifPresent(user -> {
				throw new EmailConflictRequestException();
			});
			return restBodyRequest;
		};
	}

	public static Function<RestBodyRequest, RestBodyRequest> validateModelConstraints() {
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

	public static Function<RestBodyRequest, User> getUserFromDatabase(final UserService userService, final String email) {
		return signinRestBodyRequest -> {
			Optional<User> userOptional = userService.findByEmail().apply(email);
			return userOptional.orElseThrow(EmailNotFoundRequestException::new);
		};
	}
	
	public static Function<User, User> validatePasswordMatching(final String password) {
		return user -> {
			if (!BCryptHelper.checkHash(password, user.getPassword())) {
				throw new InvalidCredencialsRequestException();
			}
			return user;
		};
	}
	
	public static Function<User, User> updateUser() {
		return user -> {
			User copiedUser = ModelMapperHelper.cloneUser(user);
			var now = new Date();
			copiedUser.setModified(now);
			copiedUser.setLastLogin(now);
			copiedUser.setToken(JWTTokenHelper.generateUserToken(copiedUser));
			return copiedUser;
		};
	}

	public static Function<RestBodyRequest, User> generateUser() {
		return ModelMapperHelper::getUserFromUserRequest;
	}

	public static Function<User, UserRestBodyResponse> generateRegisteredUserBodyResponse() {
		return user -> new UserRestBodyResponseBuilder()
									.withId(user.getId().toString())
									.withCreated(Optional.of(user.getCreated()))
									.withModified(Optional.of(user.getModified()))
									.withToken(user)
									.build();
	}
	
	public static Function<User, UserRestBodyResponse> generateModifiedUserBodyResponse() {
		return user -> new UserRestBodyResponseBuilder()
									.withId(user.getId().toString())
									.withCreated(Optional.of(user.getCreated()))
									.withModified(Optional.ofNullable(user.getModified()))
									.withLastLogin(Optional.ofNullable(user.getLastLogin()))
									.withToken(user)
									.build();
	}
	
	public static Function<User, UserRestBodyResponse> generateUmodifiedUserBodyResponse() {
		return user -> new UserRestBodyResponseBuilder()
									.withId(user.getId().toString())
									.withCreated(Optional.of(user.getCreated()))
									.withModified(Optional.of(user.getCreated()))
									.withLastLoginOrNull(Optional.ofNullable(user.getLastLogin()))
									.withToken(user)
									.build();
	}

}
