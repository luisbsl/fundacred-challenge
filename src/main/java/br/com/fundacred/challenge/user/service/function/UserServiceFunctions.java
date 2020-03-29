package br.com.fundacred.challenge.user.service.function;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

import br.com.fundacred.challenge.auth.service.exception.InvalidTokenRequestException;
import br.com.fundacred.challenge.auth.service.exception.UserNotFoundRequestException;
import br.com.fundacred.challenge.helper.JWTTokenHelper;
import br.com.fundacred.challenge.model.User;
import br.com.fundacred.challenge.user.repository.UserRepository;
import br.com.fundacred.challenge.user.service.UserService;

/**
 * 
 * @author luisbsl
 *
 */
public interface UserServiceFunctions {
	
	public static Function<String, User> getUserFromDatabase(final UserRepository userRepository) {
		return id -> {
			Optional<User> userOptional = userRepository.findById(UUID.fromString(id));
			return userOptional.orElseThrow(UserNotFoundRequestException::new);
		};
	}

	public static Function<User, User> validateToken(final String token) {
		return user -> {
			try {
				var isTokenValido = user.getToken().equals(token)
						&& JWTTokenHelper.decodeToken(user.getToken()).getBody().getExpiration().after(new Date());
				if (!isTokenValido) {
					throw new InvalidTokenRequestException();
				}
			} catch (Exception ex) {
				throw new InvalidTokenRequestException();
			}
			return user;
		};
	}
	
	public static Function<User, User> save(final UserService userService) {
		return userService.save();
	}

}
