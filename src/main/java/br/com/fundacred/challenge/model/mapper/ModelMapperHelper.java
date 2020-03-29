package br.com.fundacred.challenge.model.mapper;

import java.util.Date;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.fundacred.challenge.auth.controller.dto.RestBodyRequest;
import br.com.fundacred.challenge.helper.BCryptHelper;
import br.com.fundacred.challenge.helper.JWTTokenHelper;
import br.com.fundacred.challenge.model.User;

/**
 * 
 * @author luisbsl
 *
 */
public final class ModelMapperHelper {

	private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(ModelMapperHelper.class);

	private static final ModelMapper MAPPER = new ModelMapper();

	private ModelMapperHelper() {
	}

	public static <T extends RestBodyRequest> User  getUserFromUserRequest(final T userRestBodyRequest) {
		User user = MAPPER.map(userRestBodyRequest, User.class);
		user.setPassword(BCryptHelper.generateHash(user.getPassword()));
		var now = new Date();
		user.setCreated(now);
		user.setModified(now);
		user.setToken(JWTTokenHelper.generateUserToken(user));
		return user;
	}

	public static User cloneUser(final User user) {
		ObjectMapper objectMapper = new ObjectMapper();
		User copedUser = new User();
		try {
			copedUser = objectMapper.readValue(objectMapper.writeValueAsString(user), User.class);
		} catch (JsonProcessingException e) {
			LOGGER.error("Faltal error:", e);
		}
		return copedUser;
	}

}
