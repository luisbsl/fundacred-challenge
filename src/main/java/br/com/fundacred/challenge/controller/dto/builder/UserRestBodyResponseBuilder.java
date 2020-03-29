package br.com.fundacred.challenge.controller.dto.builder;

import java.util.Date;
import java.util.Optional;

import br.com.fundacred.challenge.auth.controller.dto.UserRestBodyResponse;
import br.com.fundacred.challenge.model.User;

/**
 * 
 * @author luisbsl
 *
 */
public final class UserRestBodyResponseBuilder {

	private UserRestBodyResponse userRestBodyResponse;

	public UserRestBodyResponseBuilder() {
		userRestBodyResponse = new UserRestBodyResponse();
	}

	public UserRestBodyResponseBuilder withId(final String id) {
		userRestBodyResponse.setId(id);
		return this;
	}

	public UserRestBodyResponseBuilder withCreated(Optional<Date> createdOptional) {
		userRestBodyResponse.setCreated(createdOptional.orElseGet(Date::new));
		return this;
	}

	public UserRestBodyResponseBuilder withModified(Optional<Date> modified) {
		userRestBodyResponse.setModified(modified.orElseGet(Date::new));
		return this;
	}

	public UserRestBodyResponseBuilder withLastLogin(Optional<Date> lastLoginOptional) {
		userRestBodyResponse.setLastLogin(lastLoginOptional.orElseGet(Date::new));
		return this;
	}
	
	public UserRestBodyResponseBuilder withLastLoginOrNull(Optional<Date> lastLoginOptional) {
		userRestBodyResponse.setLastLogin(lastLoginOptional.orElseGet(() -> null));
		return this;
	}

	public UserRestBodyResponseBuilder withToken(final User user) {
		userRestBodyResponse.setToken(user.getToken());
		return this;
	}

	public UserRestBodyResponse build() {
		return userRestBodyResponse;
	}

}
