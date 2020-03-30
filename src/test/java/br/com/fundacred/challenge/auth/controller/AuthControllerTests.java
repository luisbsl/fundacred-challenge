package br.com.fundacred.challenge.auth.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import br.com.fundacred.challenge.auth.controller.dto.PhoneRestBodyRequest;
import br.com.fundacred.challenge.auth.controller.dto.SigninRestBodyRequest;
import br.com.fundacred.challenge.auth.controller.dto.SignupRestBodyRequest;
import br.com.fundacred.challenge.auth.controller.dto.UserRestBodyResponse;
import br.com.fundacred.challenge.model.User;
import br.com.fundacred.challenge.user.service.UserService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("Unit tests for AuthController")
public class AuthControllerTests {

	@LocalServerPort
	protected int port;

	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	UserService userService;

	@Test
	@Order(1)
	void signup_givenSignupRestBodyRequest_shouldReturnUserRestBodyResponse() {
		ResponseEntity<UserRestBodyResponse> result = signup();

		final UserRestBodyResponse userRestBodyResponse = result.getBody();

		assertNotNull(userRestBodyResponse);
		assertNotNull(userRestBodyResponse.getId());
		cleanup();
	}

	@Test
	@Order(2)
	void signin_givenSigninRestBodyRequest_shouldReturnUserRestBodyResponse() {
		signup();
		SigninRestBodyRequest signin = new SigninRestBodyRequest("janejoe@mail.com", "pass");

		ResponseEntity<UserRestBodyResponse> result = restTemplate
				.postForEntity("http://localhost:" + port + "/api/v1/auth/signin", signin, UserRestBodyResponse.class);

		final UserRestBodyResponse userRestBodyResponse = result.getBody();

		assertNotNull(userRestBodyResponse);
		assertNotNull(userRestBodyResponse.getId());
		assertNotNull(userRestBodyResponse.getToken());
		cleanup();
	}

	private ResponseEntity<UserRestBodyResponse> signup() {
		SignupRestBodyRequest signup = new SignupRestBodyRequest("Jane Joe", "janejoe@mail.com", "pass",
				Set.of(new PhoneRestBodyRequest("51", "911112222")));
		return restTemplate.postForEntity("http://localhost:" + port + "/api/v1/auth/signup", signup,
				UserRestBodyResponse.class);
	}
	
	private void cleanup() {
		Optional<User> userOptional = userService.findByEmail().apply("janejoe@mail.com");
		userOptional.ifPresent(user -> {
			userService.delete().accept(user);
		});
	}

}
