package br.com.fundacred.challenge.user.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.fundacred.challenge.model.Phone;
import br.com.fundacred.challenge.model.User;
import br.com.fundacred.challenge.util.BCryptHelper;
import br.com.fundacred.challenge.util.JWTTokenHelper;

@SpringBootTest
@DisplayName("Unit tests for UserService")
public class UserServiceTests {

	@Autowired
	UserService userService;

	@Test
	void save_shouldReturn_RegisteredUser() {
		User user = new User();
		user.setName("Jane");
		user.setEmail("jane@mail.com");
		user.setPhones(Set.of(new Phone("51", "911112222")));
		user.setPassword(BCryptHelper.generateHash("pass"));
		user.setToken(JWTTokenHelper.generateUserToken(user));

		final User registeredUser = userService.save(user);

		assertNotNull(registeredUser);
		assertNotNull(registeredUser.getId());
	}

	@Test
	void findByEmail_shouldReturn_OptinalUser_ByEmail() {
		final Optional<User> registerdUserOptional = userService.findByEmail("jane@mail.com");
		final User registeredUser = registerdUserOptional.get();

		assertNotNull(registeredUser);
		assertNotNull(registeredUser.getId());
		assertEquals("jane@mail.com", registeredUser.getEmail());
	}

	public static void main(String[] args) throws JsonMappingException, JsonProcessingException {
		User user = new User();
		user.setName("Jane");
		user.setEmail("jane@mail.com");
		user.setPhones(Set.of(new Phone("51", "911112222")));
		user.setPassword(BCryptHelper.generateHash("pass"));
		user.setToken(JWTTokenHelper.generateUserToken(user));

		ObjectMapper objectMapper = new ObjectMapper();
		User deepCopy = objectMapper.readValue(objectMapper.writeValueAsString(user), User.class);
		deepCopy.setName("Jane Joe");
		
		System.out.println(user.getName());
		System.out.println(deepCopy.getName());
	}

}
