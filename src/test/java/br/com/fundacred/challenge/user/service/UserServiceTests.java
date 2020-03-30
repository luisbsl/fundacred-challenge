package br.com.fundacred.challenge.user.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import br.com.fundacred.challenge.helper.BCryptHelper;
import br.com.fundacred.challenge.helper.JWTTokenHelper;
import br.com.fundacred.challenge.model.Phone;
import br.com.fundacred.challenge.model.User;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Unit tests for UserService")
public class UserServiceTests {

	@Autowired
	UserService userService;

	private static final String TEST_EMAIL = "janejoe@mail.com";

	@Order(1)
	@Test
	void save_shouldReturnRegisteredUser() {
		User user = new User();
		user.setName("Jane");
		user.setEmail(TEST_EMAIL);
		user.setPhones(Set.of(new Phone("51", "911112222")));
		user.setPassword(BCryptHelper.generateHash("pass"));
		user.setToken(JWTTokenHelper.generateUserToken(user));

		final User registeredUser = userService.save().apply(user);

		assertNotNull(registeredUser);
		assertNotNull(registeredUser.getId());
	}

	@Order(2)
	@Test
	void findByEmail_shouldReturnOptinalUser() {
		final Optional<User> registerdUserOptional = userService.findByEmail().apply(TEST_EMAIL);
		final User registeredUser = registerdUserOptional.get();

		assertNotNull(registeredUser);
		assertNotNull(registeredUser.getId());
		assertEquals(TEST_EMAIL, registeredUser.getEmail());
	}

}
