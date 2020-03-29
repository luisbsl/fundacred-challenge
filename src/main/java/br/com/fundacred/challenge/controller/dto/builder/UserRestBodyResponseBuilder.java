package br.com.fundacred.challenge.controller.dto.builder;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Optional;

import br.com.fundacred.challenge.auth.controller.dto.UserRestBodyResponse;
import br.com.fundacred.challenge.model.User;
import br.com.fundacred.challenge.util.DateHelper;
import br.com.fundacred.challenge.util.JWTTokenHelper;

public final class UserRestBodyResponseBuilder {

	private UserRestBodyResponse userRestBodyResponse;
	private ZonedDateTime now;

	public UserRestBodyResponseBuilder() {
		userRestBodyResponse = new UserRestBodyResponse();
		now = ZonedDateTime.now();
	}

	public UserRestBodyResponseBuilder withId(final String id) {
		userRestBodyResponse.setId(id);
		return this;
	}

	public UserRestBodyResponseBuilder withCreated(Optional<Date> createdOptional) {
		userRestBodyResponse.setCreated(createdOptional.orElseGet(() -> DateHelper.fromZonedDateTime(now)));
		return this;
	}

	public UserRestBodyResponseBuilder withModified(Optional<Date> modified) {
		userRestBodyResponse.setModified(modified.orElseGet(() -> DateHelper.fromZonedDateTime(now)));
		return this;
	}

	public UserRestBodyResponseBuilder withLastLogin(Optional<Date> lastLoginOptional) {
		userRestBodyResponse.setLastLogin(lastLoginOptional.orElseGet(() -> DateHelper.fromZonedDateTime(now)));
		return this;
	}

	public UserRestBodyResponseBuilder withToken(final User user) {
		userRestBodyResponse.setToken(JWTTokenHelper.generateUserToken(user));
		return this;
	}

	public UserRestBodyResponse build() {
		return userRestBodyResponse;
	}

}
